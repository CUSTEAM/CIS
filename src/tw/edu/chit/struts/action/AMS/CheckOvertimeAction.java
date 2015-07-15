package tw.edu.chit.struts.action.AMS;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class CheckOvertimeAction extends BaseLookupDispatchAction{
	
	//SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		request.setAttribute("shiftime", manager.ezGetBy("SELECT id, name FROM AMS_ShiftTime"));
		setContentPage(request.getSession(false), "AMS/CheckOvertime.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm sForm = (DynaActionForm) form;		
		String begin=sForm.getString("begin");
		String end=sForm.getString("end");		
		
		if(begin.equals("")||end.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "資料完整才能繼續"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		begin=manager.convertDate(begin);
		end=manager.convertDate(end);
		
		List list=manager.ezGetBy("SELECT e.WorkShift, c.name, e.cname, e.idno,  " +
				"(SELECT COUNT(*)FROM AMS_DocApply d WHERE d.startDate>='"+begin+"' AND d.endDate<='"+end+"' AND d.idno=e.idno AND docType='2')as app " +
				"FROM AMS_Workdate w, empl e, CodeEmpl c WHERE " +
				"c.idno=e.unit AND (c.category='Unit' OR c.category='UnitTeach') AND e.idno=w.idno AND " +
				"w.wdate>='"+begin+"' AND w.wdate<='"+end+"' GROUP BY e.idno ORDER BY e.unit");
		
		Map total;
		int ih, im, oh, om, all;
		for(int i=0; i<list.size(); i++){
			
			try{
				/*
				total=Integer.parseInt(manager.ezGetString("SELECT SUM((HOUR(timediff(w.set_in,w.real_in))+" +
						"HOUR(timediff(w.real_out, w.set_out)))*60 +" +
						"(MINUTE(timediff(w.set_in,w.real_in))+" +
						"MINUTE(timediff(w.real_out, w.set_out))))as overtime " +
						"FROM `AMS_Workdate` w WHERE w.real_in IS NOT NULL AND " +
						"w.real_out IS NOT NULL AND w.`wdate`>'"+begin+"' AND w.wdate<'"+end+"' " +
						"AND w.idno='"+((Map)list.get(i)).get("idno")+"' LIMIT 1"));
				*/
				
				total=manager.ezGetMap("SELECT SUM((HOUR(timediff(w.set_in,w.real_in))) )*60 " +
						"as ih,SUM(MINUTE(timediff(w.set_in,w.real_in)))as im, " +
						"SUM((HOUR(timediff(w.set_out,w.real_out))) )*60 as oh," +
						"SUM(MINUTE(timediff(w.set_out,w.real_out)))as om FROM AMS_Workdate w WHERE " +
						"w.real_in IS NOT NULL AND w.real_out IS NOT NULL AND " +
						"w.`wdate`>'"+begin+"' AND w.wdate<'"+end+"' AND w.idno='"+((Map)list.get(i)).get("idno")+"' LIMIT 1");
				
				ih=Integer.parseInt(total.get("ih").toString());
				im=Integer.parseInt(total.get("im").toString());
				oh=Integer.parseInt(total.get("oh").toString());
				om=Integer.parseInt(total.get("om").toString());
				all=ih+im+oh+om;
				
			}catch(Exception e){
				
				ih=0; im=0; oh=0; om=0; all=0;
			}
			
			((Map)list.get(i)).put("in", ih+im);
			//((Map)list.get(i)).put("im", im);
			((Map)list.get(i)).put("out", oh+om);
			//((Map)list.get(i)).put("om", om);
			((Map)list.get(i)).put("all", all);
			((Map)list.get(i)).put("total", all/60+"小時"+all%60+"分鐘");
			
		}
		
		
		session.setAttribute("result", list);		
		return unspecified(mapping, form, request, response);

	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();		
		map.put("Search", "search");	
		return map;
	}

}
