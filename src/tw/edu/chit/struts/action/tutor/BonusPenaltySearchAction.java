package tw.edu.chit.struts.action.tutor;

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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class BonusPenaltySearchAction extends BaseLookupDispatchAction {

	public static final String STUDENT_LIST_NAME = "stdList";

	/**
	 * 查詢導師班級學生曠缺紀錄
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
		UserCredential credential = getUserCredential(session);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		List<Clazz> clazzs = mm.findClassInChargeByMemberAuthority(credential
				.getMember().getOid(), UserCredential.AuthorityOnTutor);
		List<Student> students = new LinkedList<Student>();
		for (Clazz clazz : clazzs) {
			students.addAll(mm.findStudentsByClassNo(clazz.getClassNo()));
		}
		List<DynaBean> ret = new LinkedList<DynaBean>();
		for (Student student : students) {
			// "all"代表曠缺課紀錄查詢
			List dilgList = sam.findDilgByStudentNo(student.getStudentNo(),
					"all");
			// 2:曠課, 3:病假, 4:事假, 5:遲到, 6:公假, 7:喪假, 8:早退
			int[] records = new int[15];
			for (int i = 0; i < dilgList.size(); i++) {
				Dilg dilg = (Dilg) dilgList.get(i);
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
			DynaBean bean = new LazyDynaBean();
			bean.set("departClass", Toolket.getClassFullName(student
					.getDepartClass()));
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
		setContentPage(session, "teacher/TutorBonusPenaltySearch.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
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
		StudAffairManager sm = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		Student student = mm.findStudentByNo(studentNo);
		student.setDepartClass2(Toolket.getClassFullName(student
				.getDepartClass()));
		// "all"代表曠缺課紀錄查詢
		List dilgList = sm.findDilgByStudentNo(studentNo, "all");
		request.setAttribute("actionName", "/Teacher/Tutor/BonusPenaltySearch");
		request.setAttribute("studentInfo", student);
		request.setAttribute("displayName",
				"teacher.tutorBonusPenaltySearch.banner");
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
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		Student student = mm.findStudentByNo(studentNo);
		student.setDepartClass2(Toolket.getClassFullName(student
				.getDepartClass()));

		// "subject"代表曠缺課扣考紀錄查詢
		List subjectList = sam.findDilgByStudentNo(studentNo, "subject");
		Map c = null;
		Seld seld = null;
		for (Object o : subjectList) {
			c = (Map) o;
			Integer oid = (Integer) c.get("dtimeOid");
			seld = sm.findSeld(oid, student.getStudentNo());
			c.put("mid", seld.getScore2() != null ? seld.getScore2().toString()
					: "");
			c.put("final", seld.getScore() != null ? seld.getScore().toString()
					: ""); // 學期成績
		}

		request.setAttribute("actionName", "/Teacher/Tutor/BonusPenaltySearch");
		request.setAttribute("studentInfo", student);
		request.setAttribute("displayName",
				"teacher.tutorBonusPenaltySubjectSearch.banner");
		request.setAttribute("subjectList", subjectList);
		setContentPage(session, "teacher/TeacherBonusPenaltySubjectDetail.jsp");
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

		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("readDetail", "readDetail");
		map.put("readSubject", "readSubject");
		map.put("Back", "back");
		return map;
	}

}
