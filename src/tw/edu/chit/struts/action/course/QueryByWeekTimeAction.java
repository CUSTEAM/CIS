package tw.edu.chit.struts.action.course;

import java.util.ArrayList;
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

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class QueryByWeekTimeAction extends BaseLookupDispatchAction{
	
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query", "search");
		map.put("Back", "back");
		return map;
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);

		DynaActionForm dynForm = (DynaActionForm)form;
		String campus = dynForm.getString("campusInCharge").trim();
		String school = dynForm.getString("schoolInCharge").trim();
		
		String campusName = Toolket.getCampus(campus);
		Map QBWeekTimeInit = new HashMap();
		QBWeekTimeInit.put("campus", campus);
		QBWeekTimeInit.put("campusName", campusName);
		QBWeekTimeInit.put("school", school);
		//QBCourseNameInit.put("dept", dept);
		//QBCourseNameInit.put("clazz", myclass);
		
		session.setAttribute("QBWeekTimeInit", QBWeekTimeInit);
		setContentPage(session,
				"course/QueryByWeekTime.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Map initMap = (Map)session.getAttribute("QBWeekTimeInit");
		
		DynaActionForm dynForm = (DynaActionForm)form;
		String campus = dynForm.getString("campusInCharge").trim();
		String school = dynForm.getString("schoolInCharge").trim();
		String fweek = dynForm.getString("fweek").trim();
		String period = dynForm.getString("period").trim();
		
		String campusName = Toolket.getCampus(campus);
		CourseManager cm = (CourseManager) getBean("courseManager");

		initMap.put("campusName", campusName);
		log.debug("QueryByCourseName->campusName:" + initMap.get("campusName"));
		
		List courseList = new ArrayList();
		
		// Map QBCourseNameInit = new HashMap();
		initMap.put("campus", campus);
		initMap.put("school", school);
		initMap.put("fweek", fweek);
		initMap.put("period", period);

		ActionMessages msg = new ActionMessages();
		
		if(fweek.trim().equals("")){
			msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "星期必須輸入!"));
		}
		if(period.trim().equals("")){
			msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "節次必須輸入!"));
		}
		
		if(!msg.isEmpty()){
			saveMessages(request, msg);
			session.removeAttribute("QBWeekTimeResult");
		}else{
			try {
			courseList = cm.findCourseByWeekTime(campus, school, fweek, period);
			log.debug("QueryByCourseName->courseList.size():" + courseList.size());
			} catch(Exception e) {
				e.printStackTrace();
			}
			session.setAttribute("QBWeekTimeResult", courseList);
			session.setAttribute("QBWeekTimeInit", initMap);
		}

		setContentPage(session,
				"course/QueryByWeekTime.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);

		session.removeAttribute("QBWeekTimeResult");
		//session.removeAttribute("QBCourseNameInit");
		setContentPage(session,
				"course/QueryByWeekTime.jsp");
		return mapping.findForward("Main");
	}

}
