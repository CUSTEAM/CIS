package tw.edu.chit.struts.action.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class CourseSearchAction extends BaseLookupDispatchAction {

	public static final String CLASS_FULL_NAME = "classFullName";
	public static final String COURSE_LIST = "courseList";
	public static final String WEEKDAY_LIST = "weekdayList";
	public static final String NODE_LIST = "nodeList";

	/**
	 * 首次進入班級課表查詢課表
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

		setContentPage(request.getSession(false), "course/CourseSearch.jsp");
		return mapping.findForward("Main");
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
		DynaActionForm aForm = (DynaActionForm) form;
		String departClass = (String) aForm.get("classInCharge2");
		String term = (String) aForm.get("sterm");
		if (!"".equals(departClass) && !"All".equalsIgnoreCase(departClass)) {
			request.setAttribute(CLASS_FULL_NAME, Toolket
					.getClassFullName(departClass));
			CourseManager cm = (CourseManager) getBean("courseManager");
			List<Map> list = cm.findCourseByClassNoAndTerm(departClass, term);			
			request.setAttribute(COURSE_LIST, list);
			request.setAttribute(WEEKDAY_LIST, getWeekdayInfo());
			if (IConstants.HSIN_CHU_CAMPUS.equals(cm
					.findCampusNoByClassNo(departClass))) {
				request.setAttribute(NODE_LIST, getNodeInfoForHsinChu());
				request.setAttribute("rowsCols",
						getRowColInfo(IConstants.HSIN_CHU.charAt(0)));
			} else {
				String schoolType = cm.findSchoolTypeByClassNo(departClass);
				if (IConstants.DAY.equalsIgnoreCase(schoolType)) {
					request.setAttribute(NODE_LIST, getNodeInfoForDay());
					request.setAttribute("rowsCols", getRowColInfo(schoolType
							.charAt(0)));
				} else if (IConstants.NIGHT.equalsIgnoreCase(schoolType)) {
					request.setAttribute(NODE_LIST, getNodeInfoForNight());
					request.setAttribute("rowsCols", getRowColInfo(schoolType
							.charAt(0)));
				} else if (IConstants.HOLIDAY.equalsIgnoreCase(schoolType)) {
					request.setAttribute(NODE_LIST, getNodeInfoForHoliday());
					request.setAttribute("rowsCols", getRowColInfo(schoolType
							.charAt(0)));
				}
			}

			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"Course.setCourseName.serachComplete"));
			saveMessages(request, messages);
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "請選擇有效班級!!"));
			saveErrors(request, messages);
		}

		setContentPage(request.getSession(false), "course/CourseSearch.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("course.courseSearch.btn.search", "search");
		return map;
	}

	public static List<String> getWeekdayInfo() {
		List<String> weekday = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			weekday.add(getWeekdayString(i));
		}
		return weekday;
	}

	private static String getWeekdayString(int index) {
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

	public static List<String> getNodeInfoForDay() {
		List<String> node = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			node.add(getNodeDayString1(i));
		}
		return node;
	}
	
	public static List<String> getNodeInfoForAll() {
		List<String> node = new ArrayList<String>();
		for (int i = 0; i < 14; i++) {
			node.add(getNodeDayString1(i));
		}
		return node;
	}

	// 日間部
	static String getNodeDayString(int index) {
		switch (index) {
			case 0:
				return "第 一 節<br/>08:20 ~ 09:10";
			case 1:
				return "第 二 節<br/>09:20 ~ 10:10";
			case 2:
				return "第 三 節<br/>10:20 ~ 11:10";
			case 3:
				return "第 四 節<br/>11:20 ~ 12:10";
			case 4:
				return "第 五 節<br/>13:20 ~ 14:10";
			case 5:
				return "第 六 節<br/>14:20 ~ 15:10";
			case 6:
				return "第 七 節<br/>15:20 ~ 16:10";
			case 7:
				return "第 八 節<br/>16:20 ~ 17:10";
			case 8:
				return "第 九 節<br/>17:20 ~ 18:10";
			case 9:
				return "第 十 節<br/>18:20 ~ 19:10";
			case 10:
				return "第 十一 節<br/>18:30 ~ 19:15";
			case 11:
				return "第 十二 節<br/>19:20 ~ 20:05";
			case 12:
				return "第 十 三節<br/>20:15 ~ 21:00";
			case 13:
				return "第 十四 節<br/>21:05 ~ 21:50";
			default:
				return "";
		}
	}
	
	// 日間部
	static String getNodeDayString1(int index) {
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
				return "第 十一 節";
			case 11:
				return "第 十二 節";
			case 12:
				return "第 十 三節";
			case 13:
				return "第 十四 節";
			default:
				return "";
		}
	}

	public static List<String> getNodeInfoForNight() {
		List<String> node = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			node.add(getNodeNightString1(i));
		}
		return node;
	}

	// 進修推廣部
	static String getNodeNightString(int index) {
		switch (index) {
			case 0:
				return "第 六 節<br/>13:45 ~ 14:30";
			case 1:
				return "第 七 節<br/>14:40 ~ 15:25";
			case 2:
				return "第 八 節<br/>15:30 ~ 16:15";
			case 3:
				return "第 九 節<br/>16:25 ~ 17:30";
			case 4:
				return "第 十 節<br/>17:15 ~ 18:00";
			case 5:
				return "夜 一 節<br/>18:30 ~ 19:15";
			case 6:
				return "夜 二 節<br/>19:20 ~ 20:05";
			case 7:
				return "夜 三 節<br/>20:15 ~ 21:00";
			case 8:
				return "夜 四 節<br/>21:05 ~ 21:50";
			default:
				return "";
		}
	}
	
	// 進修推廣部
	static String getNodeNightString1(int index) {
		switch (index) {
			case 0:
				return "第 六 節";
			case 1:
				return "第 七 節";
			case 2:
				return "第 八 節";
			case 3:
				return "第 九 節";
			case 4:
				return "第 十 節";
			case 5:
				return "夜 一 節";
			case 6:
				return "夜 二 節";
			case 7:
				return "夜 三 節";
			case 8:
				return "夜 四 節";
			default:
				return "";
		}
	}

	public static List<String> getNodeInfoForHoliday() {
		List<String> node = new ArrayList<String>();
		for (int i = 0; i < 12; i++) {
			node.add(getNodeHolidayString1(i));
		}
		return node;
	}

	// 學院/進專
	static String getNodeHolidayString(int index) {
		switch (index) {
			case 0:
				return "第 一 節<br/>08:30 ~ 09:15";
			case 1:
				return "第 二 節<br/>09:20 ~ 10:05";
			case 2:
				return "第 三 節<br/>10:15 ~ 11:00";
			case 3:
				return "第 四 節<br/>11:05 ~ 11:50";
			case 4:
				return "第 五 節<br/>12:55 ~ 13:40";
			case 5:
				return "第 六 節<br/>13:45 ~ 14:30";
			case 6:
				return "第 七 節<br/>14:40 ~ 15:25";
			case 7:
				return "第 八 節<br/>15:30 ~ 16:15";
			case 8:
				return "第 九 節<br/>16:25 ~ 17:10";
			case 9:
				return "第 十 節<br/>17:15 ~ 18:00";
			case 10:
				return "第 十一 節<br/>18:05 ~ 18:50";
			default:
				return "";
		}
	}
	
	// 學院/進專
	static String getNodeHolidayString1(int index) {
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
				return "第 十一 節";
			case 11:
				return "第 十二 節";
			default:
				return "";
		}
	}

	public static List<String> getNodeInfoForHsinChu() {
		List<String> node = new ArrayList<String>();
		for (int i = 0; i < 14; i++) {
			node.add(getNodeHsinChuString1(i));
		}
		return node;
	}

	// 新竹
	static String getNodeHsinChuString(int index) {
		switch (index) {
			case 0:
				return "第 一 節<br/>08:40 ~ 09:30";
			case 1:
				return "第 二 節<br/>09:35 ~ 10:25";
			case 2:
				return "第 三 節<br/>10:35 ~ 11:25";
			case 3:
				return "第 四 節<br/>11:30 ~ 12:20";
			case 4:
				return "第 五 節<br/>13:20 ~ 14:10";
			case 5:
				return "第 六 節<br/>14:20 ~ 15:10";
			case 6:
				return "第 七 節<br/>15:20 ~ 16:10";
			case 7:
				return "第 八 節<br/>16:20 ~ 17:10";
			case 8:
				return "第 九 節<br/>17:20 ~ 18:10";
			case 9:
				return "第 十 節<br/>18:20 ~ 19:10";
			case 10:
				return "夜 一 節<br/>18:30 ~ 19:15";
			case 11:
				return "夜 二 節<br/>19:20 ~ 20:05";
			case 12:
				return "夜 三 節<br/>20:15 ~ 21:00";
			case 13:
				return "夜 四 節<br/>21:05 ~ 21:50";
			default:
				return "";
		}
	}
	
	// 新竹
	static String getNodeHsinChuString1(int index) {
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
			default:
				return "";
		}
	}

	public static Map<String, String> getRowColInfo(char schoolNo) {
		Map<String, String> ret = new HashMap<String, String>();
		switch (schoolNo) {
			case 'D':
			case 'd':
				ret.put("mode", "D");
				ret.put("begin", "0");
				ret.put("rows", "9");
				ret.put("cols", "4");
				break;

			case 'N':
			case 'n':
				ret.put("mode", "N");
				ret.put("begin", "4");
				ret.put("rows", "8");
				ret.put("cols", "5");
				break;

			case 'H':
			case 'h':
				ret.put("mode", "H");
				ret.put("begin", "0");
				ret.put("rows", "11");
				ret.put("cols", "6");
				break;

			case 'S':
			case 's':
				ret.put("mode", "S");
				ret.put("begin", "0");
				ret.put("rows", "13");
				ret.put("cols", "5");
				break;
				
			default:
				ret.put("mode", "D");
				ret.put("begin", "0");
				ret.put("rows", "13");
				ret.put("cols", "6");
				break;
		}
		
		return ret;
	}

}
