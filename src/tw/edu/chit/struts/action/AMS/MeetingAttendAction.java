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

import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

public class MeetingAttendAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		
		String schoolYear = manager.getNowBy("School_year");                       // 取得學年度             
		String schoolTerm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);       // 取得學期
		
		session.setAttribute("askLeave", manager.ezGetBy("SELECT ID, Name FROM AMS_AskLeave"));                                      //取得假別選項
		session.setAttribute("allUnit", manager.ezGetBy(" SELECT idno, name FROM CodeEmpl " +
				                                        " WHERE (category='Unit' OR category='UnitTeach') ORDER BY sequence"));      //取得單位選項
		session.setAttribute("smeeting", manager.ezGetBy("Select Oid, Name From AMS_Meeting " +
				                                         "Where SchoolYear = '"+schoolYear+"' And SchoolTerm = '"+schoolTerm+"'"));  //取得重大集會選項
		
		session.setAttribute("SchoolYear", schoolYear);
		session.setAttribute("SchoolTerm", schoolTerm);
		
		setContentPage(request.getSession(false), "AMS/MeetingAttend.jsp");
		
		return mapping.findForward("Main");

	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String meeting=eForm.getString("smeeting");
		String unit=eForm.getString("sunit");		
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		if(meeting.equals("")){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請指定會議名稱"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		String SqlUnit = "   And CE.`idno` = '"+unit+"'";
		if(unit.equals("")){
			SqlUnit = "";
		}
		
		List myMeeting=new ArrayList();				
				
		myMeeting=manager.ezGetBy(" Select MD.`Idno`, E.`cname`, MD.`Status`, MD.`MeetingOid`, CE.name, E.sname " +
				             " From  AMS_MeetingData MD, empl E, CodeEmpl CE " +
				             " Where MD.`Idno` = E.`idno` " +
				             "   And E.`unit` = CE.`idno` " +
				             "   And MD.`MeetingOid` = '"+meeting+"'" + SqlUnit +
				             " Order By CE.`category`, CE.`idno`, E.pcode");		
		
		if(myMeeting==null){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查無人員資料"));
			saveErrors(request, error);
			return mapping.findForward("Main");		
		}			
		
		request.setAttribute("myMeeting", myMeeting);		
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		String Idno[]=eForm.getStrings("Idno");
		String MeetingOid[]=eForm.getStrings("MeetingOid");
		String Status[]=eForm.getStrings("Status");		
		
		for(int i=0; i<Idno.length; i++){
			if(!Status[i].equals("null")){				
				manager.executeSql("UPDATE AMS_MeetingData SET Status='"+Status[i]+"' "+
						           "WHERE Idno='"+Idno[i]+"' AND MeetingOid='"+MeetingOid[i]+"'");				
			}else if(Status[i].equals("null")){
				manager.executeSql(" UPDATE AMS_MeetingData SET Status = null "+
						           " WHERE Idno='"+Idno[i]+"' AND MeetingOid='"+MeetingOid[i]+"'");		
			}						
		}		
		return  mapping.findForward("Main");  //query(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Save", "save");
		return map;
	}
}
