package tw.edu.chit.struts.action;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.student.StudentOnlineAddDelCourseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class OpenCourseSearchAction extends BaseLookupDispatchAction {

	/**
	 * 進入班級課表查詢介面
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("index");
	}

	/**
	 * 處理班級課表查詢
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String campusInCharge = request.getParameter("campusInCharge");
		String schoolInCharge = request.getParameter("schoolInCharge");
		String departClass = null;
		if ("All".equalsIgnoreCase(campusInCharge))
			departClass = "All";
		else if (("1".equals(campusInCharge) || "2".equals(campusInCharge))
				&& "All".equalsIgnoreCase(schoolInCharge))
			departClass = "'" + campusInCharge + "%'";
		else
			departClass = "'" + campusInCharge + schoolInCharge + "%'";

		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		// FIXME
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		List<Map> list = cm.findOpenCourse(departClass, term);
		for (Map m : list) {
			m.put("stu_select", cm.countSelect(((Integer) m.get("oid"))
					.toString()));
		}
		request.setAttribute("literacyClassList", doSomethingForView(list));

		return mapping.findForward("index");
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("course.courseSearch.btn.search", "search");
		return map;
	}

	@SuppressWarnings("unchecked")
	private List<Map> doSomethingForView(List<Map> list) {
		for (Map map : list) {
			map.put("opt2", Toolket.getCourseOpt((String) map.get("opt")));
			String time = StudentOnlineAddDelCourseAction.getClassTimeInfo(
					(Integer) map.get("week"), (String) map.get("begin"),
					(String) map.get("end"));
			map.put("time", time);
		}
		return list;
	}

}
