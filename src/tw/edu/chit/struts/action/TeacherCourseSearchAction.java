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
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class TeacherCourseSearchAction extends BaseLookupDispatchAction {

	public static final String EMPL_LIST = "emplList";
	public static final String COURSE_LIST = "courseList";
	public static final String WEEKDAY_LIST = "weekdayList";
	public static final String NODE_LIST = "nodeList";

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

		Toolket.resetCheckboxCookie(response, EMPL_LIST);
		request.getSession(false).removeAttribute(EMPL_LIST);
		return mapping.findForward("index");
	}

	/**
	 * 處理教師查詢
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward searchTeacher(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(false);
		session.removeAttribute(EMPL_LIST);
		// 符合findEmplsByEmployeeInfoForm()方法所需參數
		aForm.set("idNo2", "");
		aForm.set("category2", "");
		// aForm.set("category2", "1"); // 1=專任(改Code要說喔,不然category=4教官會查不到)
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		List<Empl> empls = mm.findEmplsByEmployeeInfoForm(aForm.getMap());
		// aForm.set("category2", "2"); // 2=兼任
		// empls.addAll(mm.findEmplsByEmployeeInfoForm(aForm.getMap()));
		if (empls.size() == 1) {
			AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
			List<Map> list = cm.findCourseByTeacherTermWeekdaySched(empls
					.get(0).getIdno(), term);
			request.setAttribute(COURSE_LIST, list);
			request.setAttribute(WEEKDAY_LIST, getWeekdayInfo());
			request.setAttribute(NODE_LIST, getNodeInfoForDay());
			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"Course.setCourseName.serachComplete"));
			saveMessages(request, messages);
			return mapping.findForward("index");
		} else if (empls.isEmpty()) {
			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "查無此人"));
			saveMessages(request, messages);
			return mapping.findForward("index");
		}
		session.setAttribute(EMPL_LIST, empls);
		messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
				"Course.setCourseName.serachComplete"));
		saveMessages(request, messages);
		return mapping.findForward("index");
	}

	/**
	 * 處理教師課表查詢
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward searchCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		List<Empl> empls = getSeldDataListByIndex(request);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		if (!empls.isEmpty()) {
			List<Map> list = cm.findCourseByTeacherTermWeekdaySched(empls
					.get(0).getIdno(), term);

			request.setAttribute(COURSE_LIST, list);
			request.setAttribute(WEEKDAY_LIST, getWeekdayInfo());
			request.setAttribute(NODE_LIST, getNodeInfoForDay());
			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"Course.setCourseName.serachComplete"));
			saveMessages(request, messages);
		}
		return mapping.findForward("index");
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("teacher.classCadreSearch.btn.searchTeacher", "searchTeacher");
		map.put("teacher.classCadreSearch.btn.searchCourse", "searchCourse");
		return map;
	}

	protected List<String> getWeekdayInfo() {
		List<String> weekday = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			weekday.add(getWeekdayString(i));
		}
		return weekday;
	}

	private String getWeekdayString(int index) {
		switch (index) {
			case 0:
				return "星 期 一";
			case 1:
				return "星 期 二";
			case 2:
				return "星 期 三";
			case 3:
				return "星 期 四";
			case 4:
				return "星 期 五";
			case 5:
				return "星 期 六";
			case 6:
				return "星 期 日";
			default:
				return "";
		}
	}

	protected List<String> getNodeInfoForDay() {
		List<String> node = new ArrayList<String>();
		for (int i = 0; i < 15; i++) {
			node.add(getNodeDayString(i));
		}
		return node;
	}

	private String getNodeDayString(int index) {
		switch (index) {
			case 0:
				return "第 一 節";
			case 1:
				return "第 二 節";
			case 2:
				return "第 三 節";
			case 3:
				return "第 四 節";
			case 4:
				return "第 五 節";
			case 5:
				return "第 六 節";
			case 6:
				return "第 七 節";
			case 7:
				return "第 八 節";
			case 8:
				return "第 九 節";
			case 9:
				return "第 十 節";
			case 10:
				return "夜 一 節";
			case 11:
				return "夜 二 節";
			case 12:
				return "夜 三 節";
			case 13:
				return "夜 四 節";
			case 14:
				return "第 十 五 節";
			default:
				return "";
		}
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
