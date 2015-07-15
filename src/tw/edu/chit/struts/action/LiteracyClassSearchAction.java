package tw.edu.chit.struts.action;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.student.StudentOnlineAddDelCourseAction;
import tw.edu.chit.util.Toolket;

public class LiteracyClassSearchAction extends BaseLookupDispatchAction {

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

		ActionMessages messages = new ActionMessages();
		String campusInCharge = request.getParameter("campusInCharge");
		String schoolInCharge = request.getParameter("schoolInCharge");
		if (!"".equals(schoolInCharge)
				&& !"All".equalsIgnoreCase(schoolInCharge)) {
			AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
			String term = am.findTermBy(PARAMETER_SCHOOL_TERM);
			CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
			List<Map> list = cm.findLiteracyClass(campusInCharge
					+ schoolInCharge + "0__", term);
			for (Map m : list) {
				m.put("stu_select", cm.countSelect(((Integer) m.get("Oid"))
						.toString()));
			}
			
			request.setAttribute("literacyClassList", doSomethingForView(list));
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "請選擇有效班級!!"));
			saveErrors(request, messages);
		}
		
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
