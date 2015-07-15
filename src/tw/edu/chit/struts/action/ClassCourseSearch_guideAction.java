package tw.edu.chit.struts.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.course.CourseSearchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class ClassCourseSearch_guideAction extends BaseLookupDispatchAction {

	public static final String CLASS_FULL_NAME = "classFullName";
	public static final String COURSE_LIST = "courseList";
	public static final String WEEKDAY_LIST = "weekdayList";
	public static final String NODE_LIST = "nodeList";

	public static final String DAY = "D";
	public static final String NIGHT = "N";
	public static final String HOLIDAY = "H";
	public static final String HSIN_CHU = "S";
	public static final String HSIN_CHU_CAMPUS = "2";

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
		
		
		if(request.getParameter("classInCharge")!=null){
			String departClass = request.getParameter("classInCharge");
			AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
			String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
			request.setAttribute(CLASS_FULL_NAME, Toolket
					.getClassFullName(departClass)
					+ "  (" + departClass + ")");
			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			List<Map> list = cm.findCourseByClassNoAndTerm(departClass, term);
			request.setAttribute(COURSE_LIST, list);
			request.setAttribute(WEEKDAY_LIST, CourseSearchAction
					.getWeekdayInfo());
			if (IConstants.HSIN_CHU_CAMPUS.equals(cm
					.findCampusNoByClassNo(departClass))) {
				request.setAttribute(NODE_LIST, CourseSearchAction
						.getNodeInfoForHsinChu());
				request.setAttribute("rowsCols", CourseSearchAction
						.getRowColInfo(HSIN_CHU.charAt(0)));
			} else {
				String schoolType = cm.findSchoolTypeByClassNo(departClass);
				if (IConstants.DAY.equalsIgnoreCase(schoolType)) {
					request.setAttribute(NODE_LIST, CourseSearchAction
							.getNodeInfoForDay());
					request.setAttribute("rowsCols", CourseSearchAction
							.getRowColInfo(schoolType.charAt(0)));
				} else if (IConstants.NIGHT.equalsIgnoreCase(schoolType)) {
					request.setAttribute(NODE_LIST, CourseSearchAction
							.getNodeInfoForNight());
					request.setAttribute("rowsCols", CourseSearchAction
							.getRowColInfo(schoolType.charAt(0)));
				} else if (IConstants.HOLIDAY.equalsIgnoreCase(schoolType)) {
					request.setAttribute(NODE_LIST, CourseSearchAction
							.getNodeInfoForHoliday());
					request.setAttribute("rowsCols", CourseSearchAction
							.getRowColInfo(schoolType.charAt(0)));
				}
			}
			return mapping.findForward("index");
		}
		
		
		
		

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
		String departClass = request.getParameter("classInCharge");
		
		//System.out.println("AA='"+departClass+"'");
	
		if (StringUtils.isBlank(departClass)) {
			// departClass = null代表使用者未選擇任一班級
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "請選擇有效班級!!"));
			saveErrors(request, messages);
			return mapping.findForward("index");
		}
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		if (!"".equals(departClass) && !"All".equalsIgnoreCase(departClass)) {
			request.setAttribute(CLASS_FULL_NAME, Toolket
					.getClassFullName(departClass)
					+ "  (" + departClass + ")");
			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			List<Map> list = cm.findCourseByClassNoAndTerm(departClass, term);
			request.setAttribute(COURSE_LIST, list);
			request.setAttribute(WEEKDAY_LIST, CourseSearchAction
					.getWeekdayInfo());
			if (IConstants.HSIN_CHU_CAMPUS.equals(cm
					.findCampusNoByClassNo(departClass))) {
				request.setAttribute(NODE_LIST, CourseSearchAction
						.getNodeInfoForHsinChu());
				request.setAttribute("rowsCols", CourseSearchAction
						.getRowColInfo(HSIN_CHU.charAt(0)));
			} else {
				String schoolType = cm.findSchoolTypeByClassNo(departClass);
				if (IConstants.DAY.equalsIgnoreCase(schoolType)) {
					request.setAttribute(NODE_LIST, CourseSearchAction
							.getNodeInfoForDay());
					request.setAttribute("rowsCols", CourseSearchAction
							.getRowColInfo(schoolType.charAt(0)));
				} else if (IConstants.NIGHT.equalsIgnoreCase(schoolType)) {
					request.setAttribute(NODE_LIST, CourseSearchAction
							.getNodeInfoForNight());
					request.setAttribute("rowsCols", CourseSearchAction
							.getRowColInfo(schoolType.charAt(0)));
				} else if (IConstants.HOLIDAY.equalsIgnoreCase(schoolType)) {
					request.setAttribute(NODE_LIST, CourseSearchAction
							.getNodeInfoForHoliday());
					request.setAttribute("rowsCols", CourseSearchAction
							.getRowColInfo(schoolType.charAt(0)));
				}
			}

			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"Course.setCourseName.serachComplete"));
			saveMessages(request, messages);
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "請重新選擇有效的班級!!"));
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

}
