package tw.edu.chit.struts.action.teacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.teacher.TeachIntroductionAction.TeachDtimeInfo;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class TeacherBonusPenaltySearchAction extends BaseLookupDispatchAction {

	public static final String TEACHER_DTIME_NAME1 = "teacherDtime1";
	public static final String TEACHER_DTIME_NAME2 = "teacherDtime2";
	public static final String TEACHER_COOKIE_NAME = "TeachDtimeInfo";
	public static final String STUDENT_LIST_NAME = "stdList";

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
			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			List<Object[]> aList = cm.getDtimeByTeacher(credential.getMember());
			aList.addAll(cm.findDtimeTeacherByTeacher(credential.getMember()));
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
					if ("1".equals((String) o[5]))
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
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		setContentPage(request.getSession(false),
				"teacher/TeacherBonusPenaltySearch.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 統計班級學生曠缺課紀錄
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

		HttpSession session = request.getSession(false);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		StudAffairManager sm = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		List<TeachDtimeInfo> aList = getDatasInfoByIndex(request);
		List dilgList = null;
		Dilg dilg = null;
		DynaBean bean = null;

		if (!aList.isEmpty()) {

			List<Student> students = cm.findSeldStudentByDtimeOid(aList.get(0)
					.getOid());
			List<DynaBean> ret = new LinkedList<DynaBean>();
			for (Student student : students) {
				// "all"代表曠缺課紀錄查詢
				dilgList = sm
						.findDilgByStudentNo(student.getStudentNo(), "all");
				// 2:曠課, 3:病假, 4:事假, 5:遲到, 6:公假, 7:喪假, 8:早退
				int[] records = new int[15];
				for (int i = 0; i < dilgList.size(); i++) {
					dilg = (Dilg) dilgList.get(i);
					if (dilg.getAbs1() != null && dilg.getAbs1() != 0)
						records[dilg.getAbs1()]++;
					if (dilg.getAbs2() != null && dilg.getAbs2() != 0)
						records[dilg.getAbs2()]++;
					if (dilg.getAbs3() != null && dilg.getAbs3() != 0)
						records[dilg.getAbs3()]++;
					if (dilg.getAbs4() != null && dilg.getAbs4() != 0)
						records[dilg.getAbs4()]++;
					if (dilg.getAbs5() != null && dilg.getAbs5() != 0)
						records[dilg.getAbs5()]++;
					if (dilg.getAbs6() != null && dilg.getAbs6() != 0)
						records[dilg.getAbs6()]++;
					if (dilg.getAbs7() != null && dilg.getAbs7() != 0)
						records[dilg.getAbs7()]++;
					if (dilg.getAbs8() != null && dilg.getAbs8() != 0)
						records[dilg.getAbs8()]++;
					if (dilg.getAbs9() != null && dilg.getAbs9() != 0)
						records[dilg.getAbs9()]++;
					if (dilg.getAbs10() != null && dilg.getAbs10() != 0)
						records[dilg.getAbs10()]++;
					if (dilg.getAbs11() != null && dilg.getAbs11() != 0)
						records[dilg.getAbs11()]++;
					if (dilg.getAbs12() != null && dilg.getAbs12() != 0)
						records[dilg.getAbs12()]++;
					if (dilg.getAbs13() != null && dilg.getAbs13() != 0)
						records[dilg.getAbs13()]++;
					if (dilg.getAbs14() != null && dilg.getAbs14() != 0)
						records[dilg.getAbs14()]++;
					if (dilg.getAbs15() != null && dilg.getAbs15() != 0)
						records[dilg.getAbs15()]++;
				}

				bean = new LazyDynaBean();
				bean.set("studentNo", student.getStudentNo());
				bean.set("studentName", student.getStudentName());
				bean.set("cutClass", records[2]);
				bean.set("sick", records[3]);
				bean.set("business", records[6]);
				bean.set("reason", records[4]);
				bean.set("funeral", records[7]);
				bean.set("late", records[5]);
				bean.set("early", records[8]);
				ret.add(bean);
			}

			session.setAttribute(STUDENT_LIST_NAME, ret);
			setContentPage(session, "teacher/TeacherBonusPenaltySearch.jsp");
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else
			return unspecified(mapping, form, request, response);
	}

	/**
	 * 查詢學生曠缺課紀錄
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward readDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		String studentNo = request.getParameter("no");
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		Student student = mm.findStudentByNo(studentNo);
		student.setDepartClass2(Toolket.getClassFullName(student
				.getDepartClass()));
		StudAffairManager sm = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		// "all"代表曠缺課紀錄查詢
		List dilgList = sm.findDilgByStudentNo(studentNo, "all");
		request
				.setAttribute("actionName",
						"/Teacher/TeacherBonusPenaltySearch");
		request.setAttribute("studentInfo", student);
		request.setAttribute("displayName",
				"teacher.teacherBonusPenaltySearch.banner");
		request.setAttribute("dilgList", dilgList);
		setContentPage(session, "teacher/TeacherBonusPenaltyDetail.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 查詢學生曠缺課扣考紀錄
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward readSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		String studentNo = request.getParameter("no");
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		Student student = mm.findStudentByNo(studentNo);
		student.setDepartClass2(Toolket.getClassFullName(student
				.getDepartClass()));
		StudAffairManager sm = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		// "subject"代表曠缺課扣考紀錄查詢
		List subjectList = sm.findDilgByStudentNo(studentNo, "subject");
		request
				.setAttribute("actionName",
						"/Teacher/TeacherBonusPenaltySearch");
		request.setAttribute("studentInfo", student);
		request.setAttribute("displayName",
				"teacher.tutorBonusPenaltySubjectSearch.banner");
		session.setAttribute("subjectList", subjectList);
		setContentPage(session, "teacher/TeacherBonusPenaltySubjectDetail.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 查詢科目曠缺紀錄
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward readCoursePenalty(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		Integer oid = Integer.valueOf(request.getParameter("oid"));
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		Dtime dtime = cm.findDtimeBy(oid);
		List<Student> students = cm.findSeldStudentByDtimeOid(oid);
		List<Map> subjectList = new LinkedList<Map>();
		List aList = null;
		Map info = null;
		Seld seld = null;
		int i = 0;
		
		for (Student student : students) {
			aList = sam.findDilgByStudentNoAndCscode(student
					.getStudentNo(), dtime.getCscode());
			if (i == 0) {
				request.setAttribute("CoursePenaltyInfo", aList.get(0));
				i++;
			}
			
			info = (Map) aList.get(0);
			info.put("studentNo", student.getStudentNo());
			info.put("studentName", student.getStudentName());
			info.put("departClass", Toolket.getClassFullName(student
					.getDepartClass()));
			seld = sm.findSeld(oid, student.getStudentNo());
			info.put("mid", seld.getScore2() != null ? seld.getScore2()
					.toString() : "");
			info.put("final", seld.getScore() != null ? seld.getScore()
					.toString() : "");
			subjectList.add(info);
		}
		request
				.setAttribute("actionName",
						"/Teacher/TeacherBonusPenaltySearch");
		request.setAttribute("displayName",
				"teacher.tutorBonusPenaltySubjectSearch.banner");
		session.setAttribute("subjectList", subjectList);
		setContentPage(session, "teacher/TeacherBonusPenaltySubjectSummary.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 執行返回
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

		return search(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("teacher.teacherBonusPenaltySearch.btn.search", "search");
		map.put("readDetail", "readDetail"); // 曠缺明細
		map.put("readSubject", "readSubject"); // 扣考查詢
		map.put("readCoursePenalty", "readCoursePenalty"); // 科目曠缺紀錄
		map.put("Back", "back");
		return map;
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
		session.removeAttribute(STUDENT_LIST_NAME);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		form.set("term", term);
		Toolket.resetCheckboxCookie(response, TEACHER_COOKIE_NAME);
	}

}
