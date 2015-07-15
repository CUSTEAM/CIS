package tw.edu.chit.struts.action.student;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_YEAR;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.AdminManager;

public class StudentNewTermCourseSearchAction extends StudentCourseSearchAction {

	/**
	 * 處理學生第一次進入時動作
	 * 
	 * @commend 取得Student物件
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		String term = "1".equals(am.findTermBy(PARAMETER_SCHOOL_TERM)) ? "2"
				: "1";
		processRequest(request, term);
		request.setAttribute("readCourseIntro",
				"/Student/StudentNewTermCourseSearch");
		request.setAttribute("readCourseSyllabus",
				"/Student/StudentNewTermCourseSearch");
		setContentPage(session, "student/StudentCourseSearch.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 取得課程中英文簡介
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ActionForward readCourseIntro(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		Integer year = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
		// 下學期課表,所以School Term要轉換
		Integer term = "1".equals(am.findTermBy(PARAMETER_SCHOOL_TERM)) ? 2 : 1;
		if (1 == term)
			year += 1;
		processReadCourseIntro(request, year, term);
		request.setAttribute("actionName",
				"/Student/StudentNewTermCourseSearch");
		setContentPage(request.getSession(false), "student/ViewCourseIntro.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 取得課程大綱
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward readCourseSyllabus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		Integer year = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
		// 下學期課表,所以School Term要轉換
		Integer term = "1".equals(am.findTermBy(PARAMETER_SCHOOL_TERM)) ? 2 : 1;
		if (1 == term)
			year += 1;
		processReadCourseSyllabus(request, year, term);
		request.setAttribute("actionName",
				"/Student/StudentNewTermCourseSearch");
		setContentPage(request.getSession(false),
				"student/TeachSyllabusInfo.jsp");
		return mapping.findForward("Main");
	}

}
