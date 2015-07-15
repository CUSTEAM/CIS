package tw.edu.chit.struts.action.AMS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class ArtificialInputAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("allShift", manager.ezGetBy("SELECT id, name FROM AMS_ShiftTime GROUP BY id"));		
		setContentPage(request.getSession(false), "AMS/ArtificialInput.jsp");
		
		return mapping.findForward("Main");

	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String sidno=eForm.getString("sidno");
		String fscname=eForm.getString("fscname");
		
		String startDate=eForm.getString("startDate");
		String endDate=eForm.getString("endDate");
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		if(fscname.trim().length()<2){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請指定1位在職人員"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		if(startDate.trim().equals("")||endDate.trim().equals("")){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請指定日期區間, "));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}				
				
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");		
		Calendar sdate=Calendar.getInstance();		
		sdate.setTime(sf.parse(manager.convertDate(startDate)));		
		Calendar edate=Calendar.getInstance();		
		edate.setTime(sf.parse(manager.convertDate(endDate)));		
		
		if(sdate.after(edate)){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "日期矛盾"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		edate.add(Calendar.DAY_OF_MONTH, 1);//做到設定日, 如果他們不爽時只要改這邊	
		List myShift=new ArrayList();
		Map map;
		Date d;		
		
		while(edate.after(sdate)){			
			
			d=sdate.getTime();			
			map=manager.ezGetMap(" SELECT * FROM AMS_Workdate " +
					             " WHERE wdate='"+sf.format(d)+"' " +
					             "   AND idno='"+sidno+"' ");// +
					            // "   And date_type = 'w' "); //+
					            // "   And (real_in is null Or real_out is null)");
			
			//System.out.println("map="+map);
			
			if(map!=null){
				map.put("week", manager.ChWeekOfDay(sdate.get(Calendar.DAY_OF_WEEK), ""));
				map.put("cdate", manager.convertDate(map.get("wdate").toString()));
				myShift.add(map);
			}else{
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查無未刷卡資料"));
				saveErrors(request, error);
			return mapping.findForward("Main");
			}		
			sdate.add(Calendar.DAY_OF_MONTH, 1);			
		}				
		//System.out.println("myShift="+myShift);
		request.setAttribute("myShift", myShift);		
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		String idno[]=eForm.getStrings("idno");
		String wdate[]=eForm.getStrings("wdate");
		String set_in[]=eForm.getStrings("set_in");
		String set_out[]=eForm.getStrings("set_out");
		String saveType[]=eForm.getStrings("saveType");
		
		Map someday;
		for(int i=1; i<wdate.length; i++){			
				System.out.println(saveType[i]);		
				if(!set_in[i].trim().equals("")&& !set_out[i].trim().equals("")){
					//當天不是他的假日
					manager.executeSql("UPDATE AMS_Workdate SET real_in='"+set_in[i]+"', real_out='"+set_out[i]+"', type='"+saveType[i]+"' "+
							"WHERE idno='"+idno[i]+"' AND wdate='"+wdate[i]+"'");
				}else if(!set_in[i].trim().equals("")&& set_out[i].trim().equals("")){
					manager.executeSql("UPDATE AMS_Workdate SET real_in='"+set_in[i]+"', type='"+saveType[i]+"' "+
							"WHERE idno='"+idno[i]+"' AND wdate='"+wdate[i]+"'");
				}else if(set_in[i].trim().equals("")&& !set_out[i].trim().equals("")){
					manager.executeSql("UPDATE AMS_Workdate SET real_out='"+set_out[i]+"', type='"+saveType[i]+"' "+
							"WHERE idno='"+idno[i]+"' AND wdate='"+wdate[i]+"'");
				}			
			}
		
		return query(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Save", "save");
		return map;
	}
}
