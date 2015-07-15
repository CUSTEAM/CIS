package tw.edu.chit.struts.action;

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

public class QueryByCourseNameAction extends BaseLookupDispatchAction{
	
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
		Map QBCourseNameInit = new HashMap();
		QBCourseNameInit.put("campus", campus);
		QBCourseNameInit.put("campusName", campusName);
		QBCourseNameInit.put("school", school);
		
		session.setAttribute("QBCourseNameInit", QBCourseNameInit);
		return mapping.findForward("index");
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Map initMap = (Map)session.getAttribute("QBCourseNameInit");
		
		DynaActionForm dynForm = (DynaActionForm)form;
		String campus = dynForm.getString("campusInCharge").trim();
		String school = dynForm.getString("schoolInCharge").trim();
		String courseName = dynForm.getString("courseName").trim();
		
		String campusName = Toolket.getCampus(campus);
		initMap.put("campusName", campusName);
		
		List courseList = new ArrayList();
		
		// Map QBCourseNameInit = new HashMap();
		initMap.put("campus", campus);
		initMap.put("school", school);
		initMap.put("courseName", courseName);
		
		CourseManager cm = (CourseManager) getBean("courseManager");

		try {
		courseList = cm.findCourseByName(campus, school, courseName);
		} catch(Exception e) {
			e.printStackTrace();
		}
		//System.out.println(courseList);
		session.setAttribute("QBCourseNameResult", courseList);
		session.setAttribute("QBCourseNameInit", initMap);
		return mapping.findForward("index");
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);

		session.removeAttribute("QBCourseResult");
		//session.removeAttribute("QBCourseNameInit");
		return mapping.findForward("index");
	}

}
