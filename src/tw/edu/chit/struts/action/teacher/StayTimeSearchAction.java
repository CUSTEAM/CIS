package tw.edu.chit.struts.action.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Member;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

public class StayTimeSearchAction extends BaseLookupDispatchAction {

	public static final String ORDER_INFO = "orderInfo";
	public static final String ORDER_INFO_HOLIDAY = "orderInfoHoliday";
	public static final String MEMBER_INFO = "memberInfo";
	public static final String LOCATION_INFO = "locationInfo";
	public static final String STAY_TIME_INFO = "stayTimeInfo";

	/**
	 * 依據老師登入資訊取得留校時間,個人資料與辦公室位置等資料
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
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String year = cm.getNowBy("School_year");
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		session.setAttribute("year", year);
		session.setAttribute("term", term);

		request.setAttribute(ORDER_INFO, TeachStayTimeAction.getOrderInfo());
		request.setAttribute(ORDER_INFO_HOLIDAY, TeachStayTimeAction
				.getOrderInfo4Holiday());
		Member member = (Member) getUserCredential(session).getMember();
		MemberManager mm = (MemberManager) getBean("memberManager");
		Empl empl = mm.findEmplByOid(member.getOid());
		request.setAttribute(MEMBER_INFO, member);
		request.setAttribute(LOCATION_INFO, empl.getLocation());
		request.setAttribute("EmplOid", empl.getOid());
		List<String[]> temp = TeachStayTimeAction.processStayTime(mm
				.findStayTimeByEmplOid(member.getOid(), year, term));
		request.setAttribute(STAY_TIME_INFO, temp);
		setContentPage(request.getSession(false),
				"teacher/TeachStayTimeSearch.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		return map;
	}

}
