package tw.edu.chit.struts.action.sport;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_YEAR;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class DeptCourseInfoAction extends BaseLookupDispatchAction {

	private static final String SCHOOL_YEAR = "schoolYear";
	private static final String DTIME_RESULT = "dtimeResult";
	private static final String COOKIE_NAME = "resultData";
	private static final String STUDENTS_RESULT = "stdResult";

	/**
	 * 第一次進入系所選課狀況
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

		((DynaActionForm) form).initialize(mapping);
		Toolket.resetCheckboxCookie(response, COOKIE_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession();
		session.removeAttribute(STUDENTS_RESULT);
		session.removeAttribute(DTIME_RESULT);
		Integer year = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
		// Integer term = "1".equals(am.findTermBy(PARAMETER_SCHOOL_TERM)) ? 2 :
		// 1;
		Integer term = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_TERM));
		session.setAttribute(SCHOOL_YEAR, year);
		clearData(request, response, (DynaActionForm) form, term);

		setContentPage(request.getSession(false),
				"assistant/DeptCourseInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 查詢系所選課狀況
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

		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		session.removeAttribute(STUDENTS_RESULT);
		DynaActionForm aForm = (DynaActionForm) form;
		String campusInCharge = aForm.getString("campusInChargeC");
		String schoolInCharge = aForm.getString("schoolInChargeC");
		String deptInCharge = aForm.getString("deptInChargeC");
		String departClass = aForm.getString("classInChargeC");
		String classLess = "All".equalsIgnoreCase(departClass) ? campusInCharge
				+ schoolInCharge + deptInCharge : departClass;
		String term = aForm.getString("term");
		session.setAttribute("TermForSyllabus", term);
		UserCredential credential = getUserCredential(request.getSession(false));
		List list = cm.getDtimeBy(credential.getClassAryC(), "", "", term, "%",
				"%", "%", classLess, "", "");
		session.setAttribute(DTIME_RESULT, list);
		setContentPage(session, "assistant/DeptCourseInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 檢視系所選課狀況
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		Map ret = getSelectedDataByIndex(request);
		List<Student> students = processStudents(cm
				.findSeldStudentByDtimeOid(new Integer(ret.get("oid")
						.toString())));
		session.removeAttribute(DTIME_RESULT);
		session.setAttribute(STUDENTS_RESULT, students);
		setContentPage(session, "assistant/DeptCourseInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 返回系所選課狀況
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return search(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Search", "search");
		map.put("View", "view");
		map.put("Back", "back");
		return map;
	}

	@SuppressWarnings("unchecked")
	private Map getSelectedDataByIndex(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String index = Toolket.getSelectedIndexFromCookie(request, COOKIE_NAME);
		List<Map> result = (List<Map>) session.getAttribute(DTIME_RESULT);
		Map ret = null;
		for (Map bean : result) {
			if (Toolket.isValueInCookie(bean.get("oid").toString(), index)) {
				ret = bean;
				break;
			}
		}
		return ret;
	}

	private List<Student> processStudents(List<Student> students) {
		for (Student student : students) {
			student.setSex2(Toolket.getSex(student.getSex()));
			student.setDepartClass2(Toolket.getClassFullName(student
					.getDepartClass()));
		}
		return students;
	}

	// 清除Cookie
	private void clearData(HttpServletRequest request,
			HttpServletResponse response, DynaActionForm form, Integer term) {

		form.set("term", String.valueOf(term));
	}

}
