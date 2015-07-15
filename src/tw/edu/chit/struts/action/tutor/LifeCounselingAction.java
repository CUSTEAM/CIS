package tw.edu.chit.struts.action.tutor;

import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.LifeCounseling;
import tw.edu.chit.model.Member;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.teacher.TeachStayTimeAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class LifeCounselingAction extends BaseLookupDispatchAction {

	public static final String ORDER_INFO = "orderInfo";
	public static final String ORDER_INFO_HOLIDAY = "orderInfoHoliday";
	public static final String MEMBER_INFO = "memberInfo";
	public static final String LOCATION_INFO = "locationInfo";
	public static final String LIFE_COUNSELING_INFO = "lifeCounselingInfo";
	public static final String STAY_TIME_INFO = "stayTimeInfo";

	/**
	 * 依據導師登入資訊取得生活輔導時間資料
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
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.setAttribute(ORDER_INFO, TeachStayTimeAction.getOrderInfo());
		session.setAttribute(ORDER_INFO_HOLIDAY, TeachStayTimeAction
				.getOrderInfo4Holiday());
		Member member = (Member) getUserCredential(session).getMember();
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);

		String year = cm.getNowBy("School_year");
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		session.setAttribute("year", year);
		session.setAttribute("term", term);

		Empl empl = mm.findEmplByOid(member.getOid());
		empl.setUnit2(Toolket.getEmpUnit(empl.getUnit()));
		session.setAttribute(MEMBER_INFO, empl);
		session.setAttribute(LOCATION_INFO, empl.getLocation());

		List<String[]> stays = TeachStayTimeAction.processStayTime(mm
				.findStayTimeByEmplOid(member.getOid(), year, term));
		session.setAttribute(STAY_TIME_INFO, stays);

		List<String[]> lcs = processLifeCounseling(mm
				.findLifeCounselingByEmplOid(member.getOid()));
		session.setAttribute(LIFE_COUNSELING_INFO, lcs);

		List<Map> courseList = cm.findCourseByTeacherTermWeekdaySched(empl
				.getIdno(), term);
		session.setAttribute("courseList", courseList);

		setContentPage(session, "teacher/LifeCounseling.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 新增/更新生活輔導時間時處理之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Member member = getUserCredential(session).getMember();
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ActionMessages messages = new ActionMessages();
		try {
			MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
			Empl empl = mm.findEmplByOid(member.getOid());
			//mm.txDeleteLifeCounseling(empl.getLifeCounseling());   //---Leo_20120220 將其段點註記，影響程式運行
			List<LifeCounseling> lcs = processRequest(request, empl);
			cm.executeSql("DELETE FROM LifeCounseling Where ParentOid='"+empl.getOid()+"'");  //--Leo_20120224 增加清除舊有資料
			mm.txSaveLifeCounseling(lcs);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.ModifySuccessful"));
			saveMessages(request, messages);
			return unspecified(mapping, form, request, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", "資料更新時程式發生錯誤,請稍後再試或洽電算中心,謝謝."));
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
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

	// 將ResultSet轉成Array,方便前端顯示使用
	public static List<String[]> processLifeCounseling(List<LifeCounseling> lcs) {
		String[][] ret = new String[14][7];
		for (String[] tmp : ret) {
			Arrays.fill(tmp, String.valueOf("0"));
		}
		for (LifeCounseling lc : lcs) {
			for (int i = 0; i < 14; i++) {
				ret[i][lc.getWeek() - (short) 1] = getNodeValue(lc, i)
						.toString();
			}
		}
		return Arrays.asList(ret);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("teacher.stayTime.btn.update", "update");
		map.put("Cancel", "cancel");
		return map;
	}

	private static Short getNodeValue(LifeCounseling lc, int index) {
		switch (index) {
			case 0:
				return lc.getNode1();
			case 1:
				return lc.getNode2();
			case 2:
				return lc.getNode3();
			case 3:
				return lc.getNode4();
			case 4:
				return lc.getNode5();
			case 5:
				return lc.getNode6();
			case 6:
				return lc.getNode7();
			case 7:
				return lc.getNode8();
			case 8:
				return lc.getNode9();
			case 9:
				return lc.getNode10();
			case 10:
				return lc.getNode11();
			case 11:
				return lc.getNode12();
			case 12:
				return lc.getNode13();
			case 13:
				return lc.getNode14();
			default:
				return (short) 0;
		}
	}

	// 處理Request勾選資料
	private List<LifeCounseling> processRequest(HttpServletRequest request,
			Empl empl) {

		int week = 7;
		String[] weekArr = { "mon", "tue", "wed", "thu", "fri", "sat", "sun" };
		List<LifeCounseling> lcs = new LinkedList<LifeCounseling>();
		LifeCounseling lc = null;
		for (int i = 0; i < week; i++) { // start of Week
			lc = new LifeCounseling();
			lc.setWeek(Short.valueOf(String.valueOf(i + 1)));
			lc.setPos(Integer.valueOf(i));
			lc.setNode1(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 0)) ? "1" : "0"));
			lc.setNode2(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 1)) ? "1" : "0"));
			lc.setNode3(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 2)) ? "1" : "0"));
			lc.setNode4(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 3)) ? "1" : "0"));
			lc.setNode5(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 4)) ? "1" : "0"));
			lc.setNode6(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 5)) ? "1" : "0"));
			lc.setNode7(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 6)) ? "1" : "0"));
			lc.setNode8(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 7)) ? "1" : "0"));
			lc.setNode9(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 8)) ? "1" : "0"));
			lc.setNode10(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 9)) ? "1" : "0"));
			lc.setNode11(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 10)) ? "1" : "0"));
			lc.setNode12(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 11)) ? "1" : "0"));
			lc.setNode13(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 12)) ? "1" : "0"));
			lc.setNode14(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 13)) ? "1" : "0"));
			lcs.add(lc);
		} // end of Week

		return processRemove(lcs, empl);
	}

	// 將各星期值均為'0'剔除掉,並與原資料做比對做增減
	private List<LifeCounseling> processRemove(List<LifeCounseling> datas,
			Empl empl) {

		List<LifeCounseling> ret = new LinkedList<LifeCounseling>();
		int i = 0;
		for (LifeCounseling lc : datas) {
			if (lc.getNode1().toString().equals("1")
					|| lc.getNode2().toString().equals("1")
					|| lc.getNode3().toString().equals("1")
					|| lc.getNode4().toString().equals("1")
					|| lc.getNode5().toString().equals("1")
					|| lc.getNode6().toString().equals("1")
					|| lc.getNode7().toString().equals("1")
					|| lc.getNode8().toString().equals("1")
					|| lc.getNode9().toString().equals("1")
					|| lc.getNode10().toString().equals("1")
					|| lc.getNode11().toString().equals("1")
					|| lc.getNode12().toString().equals("1")
					|| lc.getNode13().toString().equals("1")
					|| lc.getNode14().toString().equals("1")) {
				lc.setEmpl(empl);
				lc.setPos(Integer.valueOf(i++));
				ret.add(lc);
			}
		}
		return ret;
	}

}
