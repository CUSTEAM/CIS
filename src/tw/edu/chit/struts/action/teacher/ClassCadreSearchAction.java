package tw.edu.chit.struts.action.teacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.ClassCadre;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.teacher.TeachIntroductionAction.TeachDtimeInfo;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class ClassCadreSearchAction extends BaseLookupDispatchAction {

	public static final String TEACHER_DTIME_NAME1 = "teacherDtime1";
	public static final String TEACHER_DTIME_NAME2 = "teacherDtime2";
	public static final String TEACHER_COOKIE_NAME = "TeachDtimeInfo";
	public static final String ROLE_INFO = "roleInfo";
	public static final String CLASS_CADRE_INFO = "classCadreInfo";

	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/MM/dd");

	/**
	 * 依據老師登入資訊取得教學班級資料
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

		HttpSession session = request.getSession();
		clearData(request, response, (DynaActionForm) form);
		try {
			UserCredential credential = getUserCredential(session);
			CourseManager cm = (CourseManager) getBean("courseManager");
			List<Object[]> aList = cm.getDtimeByTeacher(credential.getMember());
			List<TeachDtimeInfo> td1 = null, td2 = null;
			if (!aList.isEmpty()) {
				td1 = new ArrayList<TeachDtimeInfo>();
				td2 = new ArrayList<TeachDtimeInfo>();
				TeachDtimeInfo info = null;
				int pos = 0;
				for (Object[] o : aList) {
					info = new TeachDtimeInfo();
					info.setPos(pos++);
					info.setClassName((String) o[0]);
					info.setChiName((String) o[1]);
					info.setOid((Integer) o[2]);
					info.setCscode((String) o[3]);
					info.setClassNo((String) o[4]);
					if ("1".equals(((String) o[5])))
						td1.add(info);
					else
						td2.add(info);
				}

			} else {
				td1 = Collections.EMPTY_LIST;
				td2 = Collections.EMPTY_LIST;
			}

			session.setAttribute(TEACHER_DTIME_NAME1, td1);
			session.setAttribute(TEACHER_DTIME_NAME2, td2);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("Main");
		}
		setContentPage(request.getSession(false),
				"teacher/ClassCadreSearch.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 依據班級代碼查詢學生
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean("memberManager");
		Member member = (Member) getUserCredential(session).getMember();
		Empl empl = mm.findEmplByOid(member.getOid());
		request.setAttribute(ROLE_INFO, getRoleInfo());
		List<TeachDtimeInfo> aList = getDatasInfoByIndex(request);
		List<ClassCadre> ccs = subsetClassCadre(mm.findClassCadreByEmpl(empl),
				aList.get(0).getClassNo());
		if (ccs.isEmpty()) {
			ActionMessages msg = new ActionMessages();
			msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "查無班級幹部名單資料!!"));
			saveMessages(request, msg);
		} else
			request.setAttribute(CLASS_CADRE_INFO, ccs);

		setContentPage(session, "teacher/ClassCadreSearch.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("teacher.stdInfo.btn.search", "search");
		return map;
	}

	private List<ClassCadre> subsetClassCadre(List<ClassCadre> ccs,
			String classNo) {
		List<ClassCadre> ret = new ArrayList<ClassCadre>();
		for (ClassCadre cc : ccs) {
			if (cc.getClassNo().trim().equals(classNo))
				ret.add(cc);
		}
		return ret;
	}

	// 以Oid值取得TeachDtimeInfo物件
	@SuppressWarnings("unchecked")
	private List<TeachDtimeInfo> getDatasInfoByIndex(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request,
				TEACHER_COOKIE_NAME);
		List<TeachDtimeInfo> dataList1 = (List<TeachDtimeInfo>) session
				.getAttribute(TEACHER_DTIME_NAME1);
		List<TeachDtimeInfo> dataList2 = (List<TeachDtimeInfo>) session
				.getAttribute(TEACHER_DTIME_NAME2);
		List<TeachDtimeInfo> aList = new ArrayList<TeachDtimeInfo>();
		for (TeachDtimeInfo tdi : dataList1) {
			if (Toolket.isValueInCookie(tdi.getOid().toString(), oids)) {
				aList.add(tdi);
			}
		}
		for (TeachDtimeInfo tdi : dataList2) {
			if (Toolket.isValueInCookie(tdi.getOid().toString(), oids)) {
				aList.add(tdi);
			}
		}
		return aList;
	}

	// 清除Cookie
	private void clearData(HttpServletRequest request,
			HttpServletResponse response, DynaActionForm form) {
		HttpSession session = request.getSession(false);
		session.removeAttribute(TEACHER_DTIME_NAME1);
		session.removeAttribute(TEACHER_DTIME_NAME2);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		form.set("term", term);
		Toolket.resetCheckboxCookie(response, TEACHER_COOKIE_NAME);
	}

	private List<String> getRoleInfo() {
		List<String> orders = new ArrayList<String>();
		for (int i = 0; i < 9; i++)
			orders.add(getRoleString(i));
		return orders;
	}

	private String getRoleString(int index) {
		switch (index) {
			case 0:
				return "班  代  表";
			case 1:
				return "副 班 代 表";
			case 2:
				return "輔 導 股 長";
			case 3:
				return "總 務 股 長";
			case 4:
				return "康 樂 股 長";
			case 5:
				return "學 藝 股 長";
			case 6:
				return "學 藝 幹 事";
			case 7:
				return "衛 生 股 長";
			case 8:
				return "衛 生 幹 事";
			default:
				return "";
		}
	}

}
