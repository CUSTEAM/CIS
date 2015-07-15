package tw.edu.chit.struts.action.teacher;

import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;

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
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class TeacherStayTimeSearchAction extends BaseLookupDispatchAction {

	public static final String EMPL_LIST = "emplList";
	public static final String ORDER_INFO_HOLIDAY = "orderInfoHoliday";
	public static final String ORDER_INFO = "orderInfo";
	public static final String MEMBER_INFO = "memberInfo";
	public static final String LOCATION_INFO = "locationInfo";
	public static final String STAY_TIME_INFO = "stayTimeInfo";

	/**
	 * 第一次進入查詢教師留校時間
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

		Toolket.resetCheckboxCookie(response, EMPL_LIST);
		((DynaActionForm) form).initialize(mapping);
		request.getSession(false).removeAttribute(EMPL_LIST);
		request.getSession(false).removeAttribute(STAY_TIME_INFO);
		setContentPage(request.getSession(false),
				"teacher/TeacherStayTimeSearch.jsp");
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
				"teacher/TeacherStayTimeSearch.jsp");
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
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String year = cm.getNowBy("School_year");
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		session.setAttribute("year", year);
		session.setAttribute("term", term);

		ActionMessages messages = new ActionMessages();
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		request.setAttribute(ORDER_INFO, TeachStayTimeAction.getOrderInfo());
		request.setAttribute(ORDER_INFO_HOLIDAY, TeachStayTimeAction
				.getOrderInfo4Holiday());
		List<Empl> empls = getSeldDataListByIndex(request);
		request.setAttribute(LOCATION_INFO, empls.get(0).getLocation());
		List<String[]> temp = TeachStayTimeAction.processStayTime(mm
				.findStayTimeByEmplOid(empls.get(0).getOid(), year, term));
		request.setAttribute(STAY_TIME_INFO, temp);
		messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
				"Course.setCourseName.serachComplete"));
		saveMessages(request, messages);

		setContentPage(request.getSession(false),
				"teacher/TeacherStayTimeSearch.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 處理點選取消之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("teacher.teacherStayTimeSearch.btn.search", "searchTeacher"); // 教師姓名關鍵字查詢
		map.put("teacher.teacherStayTimeSearch.btn.searchStayTime",
				"searchStayTime"); // 查詢留校時間
		map.put("Cancel", "cancel");
		return map;
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
