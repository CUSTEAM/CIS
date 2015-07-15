package tw.edu.chit.struts.action.AMS;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.AmsWorkdateData;
import tw.edu.chit.model.Empl;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.AmsManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class EmplAmsInfoAction extends BaseLookupDispatchAction {

	private static String AMS_IFNO = "amsInfo";

	/**
	 * 進入差勤紀錄作業
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

		HttpSession session = request.getSession(false);
		session.removeAttribute(AMS_IFNO);
		((DynaActionForm) form).initialize(mapping);

		String value = request.getParameter("txtUserValue");
		if (StringUtils.isBlank(value) && !"oscar".equals(value)) {
			setContentPage(session, "AMS/EmplAmsInfo.jsp");
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {
			return query(mapping, form, request, response);
		}

	}

	/**
	 * 進入差勤紀錄作業
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		AmsManager amsm = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		// DynaActionForm aForm = (DynaActionForm) form;
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String idno = request.getParameter("txtUserInput").split(" ")[0]
				.toUpperCase();
		Empl empl = mm.findEmplByIdno(idno);
		Date from = Toolket.parseNativeDate(request.getParameter("startDate"));
		Date to = Toolket.parseNativeDate(request.getParameter("endDate"));
		session.setAttribute("emplInfo", request.getParameter("txtUserInput"));
		session.setAttribute("emplStartDate", request.getParameter("startDate"));
		session.setAttribute("emplEndDate", request.getParameter("endDate"));
		DateFormat df = new SimpleDateFormat("yyyy年M月d日", Locale.TAIWAN);
		ActionMessages messages = new ActionMessages();

		if (empl != null) {

			AmsWorkdateData aw = new AmsWorkdateData();
			aw.setIdno(empl.getIdno());
			session.setAttribute("fromDate", df.format(from.getTime()));
			session.setAttribute("toDate", df.format(to.getTime()));

			Map<String, Object> ret = amsm.findAmsWorkdateDataBy(empl, aw,
					from, to, Integer.parseInt(year));
			if (ret != null) {
				session.setAttribute(AMS_IFNO, (List<AmsWorkdateData>) ret
						.get("recordData"));
				session.setAttribute("betweenCounts", ret.get("betweenCounts"));
				session.setAttribute("delayCounts", ret.get("delayCounts"));
				session.setAttribute("earlyCounts", ret.get("earlyCounts"));
				session.setAttribute("delayEarlysScore", ret
						.get("delayEarlysScore"));
				session.setAttribute("totalDelayButNoRecordHours", ret
						.get("totalDelayButNoRecordHours"));
				session.setAttribute("totalDelayButNoRecordScore", ret
						.get("totalDelayButNoRecordScore"));
				session.setAttribute("totalEarlyButNoRecordHours", ret
						.get("totalEarlyButNoRecordHours"));
				session.setAttribute("totalEarlyButNoRecordScore", ret
						.get("totalEarlyButNoRecordScore"));
				session.setAttribute("noRecordCounts", ret
						.get("noRecordCounts"));
				session.setAttribute("noRecordCountsScore", ret
						.get("noRecordCountsScore"));
				session.setAttribute("businessCounts", ret
						.get("businessCounts"));
				session.setAttribute("businessTotalHours", ret
						.get("businessTotalHours"));
				session.setAttribute("businessCountsScore", ret
						.get("businessCountsScore"));
				session.setAttribute("sickCounts", ret.get("sickCounts"));
				session.setAttribute("sickTotalHours", ret
						.get("sickTotalHours"));
				session.setAttribute("sickCountsScore", ret
						.get("sickCountsScore"));
				session.setAttribute("womenSickCounts", ret
						.get("womenSickCounts"));
				session.setAttribute("womenSickTotalHours", ret
						.get("womenSickTotalHours"));
				session.setAttribute("womenSickCountsScore", ret
						.get("womenSickCountsScore"));
				session.setAttribute("workOnHolidayCounts", ret
						.get("workOnHolidayCounts"));
				session.setAttribute("yearVacation", ret.get("yearVacation"));
				session.setAttribute("yearVacationRemain", ret
						.get("yearVacationRemain"));
				session.setAttribute("specialVacation", ret
						.get("specialVacation"));
				session.setAttribute("specialVacationRemain", ret
						.get("specialVacationRemain"));
				session.setAttribute("amsDocApplyCounts", ret
						.get("amsDocApplyCounts"));
				session.setAttribute("amsDocOverTimeCounts", ret
						.get("amsDocOverTimeCounts"));
				session.setAttribute("amsDocOverTimeTimes", ret
						.get("amsDocOverTimeTimes"));
				session.setAttribute("meetingAbsentCounts", ret
						.get("meetingAbsentCounts"));
				session.setAttribute("meetingAbsentScores", ret
						.get("meetingAbsentScores"));
				session.setAttribute("vacationNoStatus", ret
						.get("vacationNoStatus"));
				session.setAttribute("vacationNoStatusScore", ret
						.get("vacationNoStatusScore"));
				session.setAttribute("meetingBusinessCounts", ret
						.get("meetingBusinessCounts"));
				session.setAttribute("meetingBusinessTotalHours", ret
						.get("meetingBusinessTotalHours"));
				session.setAttribute("meetingBusinessScore", ret
						.get("meetingBusinessScore"));
				session.setAttribute("meetingSickCounts", ret
						.get("meetingSickCounts"));
				session.setAttribute("meetingSickTotalHours", ret
						.get("meetingSickTotalHours"));
				session.setAttribute("meetingSickScore", ret
						.get("meetingSickScore"));
				session.setAttribute("meetingWomenSickCounts", ret
						.get("meetingWomenSickCounts"));
				session.setAttribute("meetingWomenSickTotalHours", ret
						.get("meetingWomenSickTotalHours"));
				session.setAttribute("meetingWomenSickScore", ret
						.get("meetingWomenSickScore"));
				session.setAttribute("totalScores", ret.get("totalScores"));

				session.setAttribute("leave3", ret.get("leave3"));
				session.setAttribute("leave3Hours", ret.get("leave3Hours"));
				session.setAttribute("leave4", ret.get("leave4"));
				session.setAttribute("leave4Hours", ret.get("leave4Hours"));
				session.setAttribute("leave5", ret.get("leave5"));
				session.setAttribute("leave5Hours", ret.get("leave5Hours"));
				session.setAttribute("leave6", ret.get("leave6"));
				session.setAttribute("leave6Hours", ret.get("leave6Hours"));
				session.setAttribute("leave7", ret.get("leave7"));
				session.setAttribute("leave7Hours", ret.get("leave7Hours"));
				session.setAttribute("leave9", ret.get("leave9"));
				session.setAttribute("leave9Hours", ret.get("leave9Hours"));
				session.setAttribute("leave10", ret.get("leave10"));
				session.setAttribute("leave10Hours", ret.get("leave10Hours"));
				session.setAttribute("leave12", ret.get("leave12"));
				session.setAttribute("leave12Hours", ret.get("leave12Hours"));
				session.setAttribute("leave13", ret.get("leave13"));
				session.setAttribute("leave13Hours", ret.get("leave13Hours"));
			} else {
				session.setAttribute("betweenCounts", 0);
				session.setAttribute("delayCounts", 0);
				session.setAttribute("earlyCounts", 0);
				session.setAttribute("delayEarlysScore", 0.0);
				session.setAttribute("totalDelayButNoRecordHours", 0.0);
				session.setAttribute("totalDelayButNoRecordScore", 0.0);
				session.setAttribute("totalEarlyButNoRecordHours", 0.0);
				session.setAttribute("totalEarlyButNoRecordScore", 0.0);
				session.setAttribute("noRecordCounts", 0);
				session.setAttribute("noRecordCountsScore", 0.0);
				session.setAttribute("businessCounts", 0);
				session.setAttribute("businessTotalHours", 0.0);
				session.setAttribute("businessCountsScore", 0.0);
				session.setAttribute("sickCounts", 0);
				session.setAttribute("sickTotalHours", 0.0);
				session.setAttribute("sickCountsScore", 0.0);
				session.setAttribute("womenSickCounts", 0);
				session.setAttribute("womenSickTotalHours", 0.0);
				session.setAttribute("womenSickCountsScore", 0.0);
				session.setAttribute("workOnHolidayCounts", 0);
				session.setAttribute("yearVacation", 0);
				session.setAttribute("yearVacationRemain", 0);
				session.setAttribute("specialVacation", 0);
				session.setAttribute("specialVacationRemain", 0);
				session.setAttribute("amsDocApplyCounts", 0);
				session.setAttribute("amsDocOverTimeCounts", 0);
				session.setAttribute("amsDocOverTimeTimes", 0);
				session.setAttribute("meetingAbsentCounts", 0);
				session.setAttribute("meetingAbsentScores", 0.0);
				session.setAttribute("vacationNoStatus", 0);
				session.setAttribute("vacationNoStatusScore", 0.0);
				session.setAttribute("meetingBusinessCounts", 0);
				session.setAttribute("meetingBusinessTotalHours", 0.0);
				session.setAttribute("meetingBusinessScore", 0.0);
				session.setAttribute("meetingSickCounts", 0);
				session.setAttribute("meetingSickTotalHours", 0.0);
				session.setAttribute("meetingSickScore", 0.0);
				session.setAttribute("meetingWomenSickCounts", 0);
				session.setAttribute("meetingWomenSickTotalHours", 0.0);
				session.setAttribute("meetingWomenSickScore", 0.0);
				session.setAttribute("totalScores", 0);

				session.setAttribute("leave3", 0);
				session.setAttribute("leave3Hours", 0.0);
				session.setAttribute("leave4", 0);
				session.setAttribute("leave4Hours", 0.0);
				session.setAttribute("leave5", 0);
				session.setAttribute("leave5Hours", 0.0);
				session.setAttribute("leave6", 0);
				session.setAttribute("leave6Hours", 0.0);
				session.setAttribute("leave7", 0);
				session.setAttribute("leave7Hours", 0.0);
				session.setAttribute("leave9", 0);
				session.setAttribute("leave9Hours", 0.0);
				session.setAttribute("leave10", 0);
				session.setAttribute("leave10Hours", 0.0);
				session.setAttribute("leave12", 0);
				session.setAttribute("leave12Hours", 0.0);
				session.setAttribute("leave13", 0);
				session.setAttribute("leave13Hours", 0.0);
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "查無員工資料"));
			saveMessages(request, messages);
		}

		setContentPage(session, "AMS/EmplAmsInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("course.onlineAddRemoveCourse.query", "query");
		return map;
	}

}
