package tw.edu.chit.struts.action.student;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.teacher.TeachStayTimeAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class TeacherStayTimeSearchAction extends BaseLookupDispatchAction {

	public static final String COURSE_INFO = "courseInfo";
	public static final String EMPL_LIST = "emplList";
	public static final String ORDER_INFO = "orderInfo";
	public static final String ORDER_INFO_HOLIDAY = "orderInfoHoliday";
	public static final String MEMBER_INFO = "memberInfo";
	public static final String LOCATION_INFO = "locationInfo";
	public static final String STAY_TIME_INFO = "stayTimeInfo";

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

		Toolket.resetCheckboxCookie(response, EMPL_LIST);
		((DynaActionForm) form).initialize(mapping);
		HttpSession session = request.getSession(false);
		session.removeAttribute(COURSE_INFO);
		session.removeAttribute(EMPL_LIST);
		Student student = getUserCredential(session).getStudent();
		if (student != null) {
			// 避免Student Object查無資料
			AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
			String term = am.findTermBy(PARAMETER_SCHOOL_TERM);
			CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
			List<Map> seldInfo = cm.findStudentSeldCourse(student
					.getStudentNo(), term);
			session.setAttribute(COURSE_INFO, doSomethingForView(seldInfo));
			setContentPage(session, "student/TeacherStayTimeSearch.jsp");
		}
		return mapping.findForward("Main");
	}

	/**
	 * 處理教師姓名關鍵字查詢
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward searchTeacher(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		// 符合findEmplsByEmployeeInfoForm()方法所需參數
		aForm.set("idNo2", "");
		aForm.set("category2", "");
		Toolket.resetCheckboxCookie(response, EMPL_LIST);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		List<Empl> empls = mm.findEmplsByEmployeeInfoForm(aForm.getMap());
		request.getSession(false).setAttribute(EMPL_LIST, empls);
		ActionMessages messages = new ActionMessages();
		messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
				"Course.setCourseName.serachComplete"));
		saveMessages(request, messages);
		setContentPage(request.getSession(false),
				"student/TeacherStayTimeSearch.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 處理教師留校時間查詢
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward searchStayTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		ActionMessages messages = new ActionMessages();
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String year = cm.getNowBy("School_year");
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		session.setAttribute("year", year);
		session.setAttribute("term", term);
		request.setAttribute(ORDER_INFO, TeachStayTimeAction.getOrderInfo());
		request.setAttribute(ORDER_INFO_HOLIDAY, TeachStayTimeAction
				.getOrderInfo4Holiday());
		List<Empl> empls = getSeldDataListByIndex(request);
		request.setAttribute(LOCATION_INFO, empls.isEmpty() ? "" : empls.get(0)
				.getLocation());
		List<String[]> temp = TeachStayTimeAction.processStayTime(mm
				.findStayTimeByEmplOid(empls.get(0).getOid(), year, term));
		request.setAttribute(STAY_TIME_INFO, temp);
		messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
				"Course.setCourseName.serachComplete"));
		saveMessages(request, messages);

		setContentPage(request.getSession(false),
				"student/TeacherStayTimeSearch.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 處理學生選課之教師留校時間查詢
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward readStayTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String year = cm.getNowBy("School_year");
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		session.setAttribute("year", year);
		session.setAttribute("term", term);

		ActionMessages messages = new ActionMessages();
		Integer eoid = null;
		// 不知啥原因會是null???
		if (StringUtils.isNotBlank(request.getParameter("eoid")))
			eoid = Integer.valueOf(request.getParameter("eoid"));
		else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "查無該老師資料"));
			saveMessages(request, messages);
			return mapping.findForward("Main");
		}
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		Empl empl = mm.findEmplByOid(eoid);
		request.setAttribute(ORDER_INFO, TeachStayTimeAction.getOrderInfo());
		request.setAttribute(ORDER_INFO_HOLIDAY, TeachStayTimeAction
				.getOrderInfo4Holiday());
		request.setAttribute(LOCATION_INFO, empl.getLocation());
		List<String[]> temp = TeachStayTimeAction.processStayTime(mm
				.findStayTimeByEmplOid(eoid, year, term));
		request.setAttribute(STAY_TIME_INFO, temp);
		messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
				"Course.setCourseName.serachComplete"));
		saveMessages(request, messages);

		setContentPage(request.getSession(false),
				"student/TeacherStayTimeSearch.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 處理點選"返回"時動作
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

		setContentPage(request.getSession(false),
				"student/OnlineAddDelCourse.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("teacher.teacherStayTimeSearch.btn.search", "searchTeacher"); // 教師姓名關鍵字查詢
		map.put("teacher.teacherStayTimeSearch.btn.searchStayTime",
				"searchStayTime"); // 查詢留校時間
		map.put("readStayTime", "readStayTime"); // 查詢留校時間(URL連結)
		map.put("Back", "back");
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

	@SuppressWarnings("unchecked")
	private List<Empl> getSeldDataListByIndex(HttpServletRequest request) {

		List<Empl> aList = new ArrayList<Empl>();
		HttpSession session = request.getSession(false);
		String index = Toolket.getSelectedIndexFromCookie(request, EMPL_LIST);
		List<Empl> empls = (List<Empl>) session.getAttribute(EMPL_LIST);
		if (empls != null) {
			for (Empl empl : empls) {
				if (Toolket.isValueInCookie(empl.getOid().toString(), index)) {
					aList.add(empl);
				}
			}
		}

		return aList;
	}

}
