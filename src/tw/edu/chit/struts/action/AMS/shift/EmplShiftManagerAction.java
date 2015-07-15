package tw.edu.chit.struts.action.AMS.shift;

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

public class EmplShiftManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("allShift", manager.ezGetBy("SELECT id, name FROM AMS_ShiftTime GROUP BY id"));		
		setContentPage(request.getSession(false), "AMS/shift/EmplShiftManager.jsp");
		//session.setAttribute("DirectoryPath", manager.ezGetString("SELECT Action FROM Module m WHERE m.Oid=(SELECT ParentOid FROM Module WHERE Action='/AMS/EmplShiftManager.do')"));
		return mapping.findForward("Main");

	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String sidno=eForm.getString("sidno");
		String fscname=eForm.getString("fscname");
		
		String startDate=eForm.getString("startDate");
		String endDate=eForm.getString("endDate");
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		if(manager.ezGetInt("SELECT COUNT(*)FROM empl WHERE idno='"+sidno.trim()+"'")!=1){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請指定1位在職人員"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		if(startDate.trim().equals("")||endDate.trim().equals("")){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請指定排班期間, "));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
				
		//if(!error.isEmpty()){
			//saveErrors(request, error);
			//return mapping.findForward("Main");
		//}
		
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
			map=manager.ezGetMap("SELECT * FROM AMS_Workdate WHERE wdate='"+sf.format(d)+"' AND idno='"+sidno+"'");
			
			if(map!=null){
				map.put("week", manager.ChWeekOfDay(sdate.get(Calendar.DAY_OF_WEEK), ""));
				map.put("cdate", manager.convertDate(map.get("wdate").toString()));
				myShift.add(map);
			}else{
				map=new HashMap();
				map.put("idno", sidno);
				map.put("cdate", manager.convertDate(sf.format(d)));
				map.put("wdate", sf.format(d));
				map.put("week", manager.ChWeekOfDay(sdate.get(Calendar.DAY_OF_WEEK), "")+"(當天尚未排班)");
				myShift.add(map);
			}			
			sdate.add(Calendar.DAY_OF_MONTH, 1);			
		}		
		
		//startDate=manager.convertDate(startDate);
		//endDate=manager.convertDate(endDate);
		//List myShift=manager.ezGetBy("SELECT * FROM AMS_Workdate WHERE wdate>='"+startDate+"' AND wdate<='"+endDate+"' AND idno='"+sidno+"'");
		/*
		Date aday;
		Calendar c=Calendar.getInstance();
		String wdate;
		for(int i=0; i<myShift.size(); i++){
			wdate=((Map)myShift.get(i)).get("wdate").toString();
			aday=sf.parse(wdate);
			c.setTime(aday);			
			((Map)myShift.get(i)).put("cdate", manager.convertDate(wdate));
			((Map)myShift.get(i)).put("week", manager.ChWeekOfDay(c.get(Calendar.DAY_OF_WEEK), ""));			
		}
		*/
		request.setAttribute("myShift", myShift);		
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String idno=eForm.getString("idno");
		//String shift[]=eForm.getStrings("shift");
		
		String wdate[]=eForm.getStrings("wdate");
		String set_in[]=eForm.getStrings("set_in");
		String set_out[]=eForm.getStrings("set_out");
		
		Map someday;
		for(int i=1; i<wdate.length; i++){			
			
			someday=manager.ezGetMap("SELECT * FROM AMS_Workdate WHERE idno='"+idno+"' AND wdate='"+wdate[i]+"'");
			if(someday!=null){
				//如果已排班
				if(!set_in[i].trim().equals("")&& !set_out[i].trim().equals("")){
					//當天不是他的假日
					manager.executeSql("UPDATE AMS_Workdate SET set_in='"+set_in[i]+"', set_out='"+set_out[i]+"', date_type='w', shift='S' "+
							"WHERE idno='"+idno+"' AND wdate='"+wdate[i]+"'");
				}else{
					manager.executeSql("UPDATE AMS_Workdate SET set_in=null, set_out=null, date_type='h' , shift='S' "+
							"WHERE idno='"+idno+"' AND wdate='"+wdate[i]+"'");
				}				
			}else{
				//如果未排班
				if(!set_in[i].trim().equals("")&& !set_out[i].trim().equals("")){
					//當天不是他的假日
					manager.executeSql("INSERT INTO AMS_Workdate(idno, date_type, wdate, set_in, set_out, shift)VALUES" +
							"('"+idno+"', 'w', '"+wdate[i]+"', '"+set_in[i]+"', '"+set_out[i]+"', 'S');");
				}else{
					//當天是假日
					try{
						manager.executeSql("INSERT INTO AMS_Workdate(idno, date_type, wdate, set_in, set_out, shift)VALUES" +
								"('"+idno+"', 'h', '"+wdate[i]+"', '"+set_in[i]+"', '"+set_out[i]+"', 'S');");
					}catch(Exception e){
						
					}
					
				}
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
