package tw.edu.chit.struts.action.student;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_YEAR;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.StdLoan;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

public class StudentLoanInputAction extends BaseLookupDispatchAction {

	public static final String STUDENT_INFO = "stdInfo";

	private static Calendar beginUp = null;
	private static Calendar endUp = null;

	private static Calendar beginDown = null;
	private static Calendar endDown = null;

	private static String LOAN_TERM = null;

	static {
		// 上學期: 8/1~9/5
		beginUp = Calendar.getInstance();
		beginUp.set(Calendar.MONTH, Calendar.AUGUST);
		beginUp.set(Calendar.DAY_OF_MONTH, 1);

		endUp = Calendar.getInstance();
		endUp.set(Calendar.MONTH, Calendar.SEPTEMBER);
		endUp.set(Calendar.DAY_OF_MONTH, 5);

		// 下學期: 1/15~2/24
		beginDown = Calendar.getInstance();
		beginDown.set(Calendar.MONTH, Calendar.JANUARY);
		beginDown.set(Calendar.DAY_OF_MONTH, 15);

		endDown = Calendar.getInstance();
		endDown.set(Calendar.MONTH, Calendar.FEBRUARY);
		endDown.set(Calendar.DAY_OF_MONTH, 24);
	}

	/**
	 * 學生輸入就學貸款相關資料
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

		ActionMessages errors = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm) form;
		Date now = new Date();
		boolean bFlag = false;
		if ((now.after(beginUp.getTime()) && now.before(endUp.getTime()))) {
			LOAN_TERM = "1";
			bFlag = true;
		} else if ((now.after(beginDown.getTime()) && now.before(endDown
				.getTime()))) {
			LOAN_TERM = "2";
			bFlag = true;
		}

		if (bFlag) {

			AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
			int year = Integer.parseInt(am.findTermBy(PARAMETER_SCHOOL_YEAR));
			String[] years = null, yearsName = null;
			for (int i = year; i < year + 8; i++) {
				years = (String[]) ArrayUtils.add(years, String.valueOf(i));
				yearsName = (String[]) ArrayUtils
						.add(yearsName, "民國" + i + "年");
			}
			aForm.set("gradeYears", years);
			aForm.set("gradeYearsName", yearsName);

			HttpSession session = request.getSession(false);
			Student student = (Student) getUserCredential(session).getStudent();
			setForm(student, aForm);

			request.setAttribute(STUDENT_INFO, student);
			request.setAttribute("VALID", "0");
			setContentPage(session, "student/StudentLoanInput.jsp");

		} else {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "目前時間尚未開放,請參見課指組網站公告,謝謝"));
			saveMessages(request, errors);
			request.setAttribute("VALID", "1");
		}

		return mapping.findForward(IConstants.ACTION_MAIN_NAME);

	}

	/**
	 * 儲存學生輸入就學貸款相關資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		// AdminManager am = (AdminManager)
		// getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		Student student = getUserCredential(session).getStudent();
		DynaActionForm aForm = (DynaActionForm) form;
		// String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		// String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);

		StdLoan loan = new StdLoan();
		loan.setStudentNo(student.getStudentNo());
		// loan.setSchoolYear(year);
		loan.setSchoolTerm(LOAN_TERM); // TODO 要改成LOAN_TERM
		List<StdLoan> stdLoans = cm.findStdLoanBy(loan);
		if (!stdLoans.isEmpty() && stdLoans.size() == 1) {
			loan = stdLoans.get(0);
			cm.deleteStdLoan(loan);
		}

		loan = processForm(aForm, student, LOAN_TERM); // TODO 要改成LOAN_TERM
		ActionMessages messages = new ActionMessages();
		try {
			cm.saveStdLoan(loan);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "新增成功"));
			saveMessages(request, messages);
		} catch (Exception e) {
			e.printStackTrace();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "新增資料失敗,請洽電算中心,謝謝."));
			saveErrors(request, messages);
			// return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Save", "save");
		return map;
	}

	private void setForm(Student student, DynaActionForm form) {
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		StdLoan loan = new StdLoan();
		loan.setStudentNo(student.getStudentNo());
		loan.setSchoolTerm(LOAN_TERM); // TODO 要改成LOAN_TERM
		List<StdLoan> stdLoans = cm.findStdLoanBy(loan);
		if (!stdLoans.isEmpty() && stdLoans.size() == 1) {
			loan = stdLoans.get(0);
//			form.set("wifeName", loan.getWifeName());
//			form.set("fatherName", loan.getFatherName());
//			form.set("motherName", loan.getMotherName());
//			form.set("wifeIdno", loan.getWifeIdno());
//			form.set("fatherIdno", loan.getFatherIdno());
//			form.set("motherIdno", loan.getMotherIdno());
//			form.set("amount", loan.getAmount().toString());
//			form.set("address", loan.getAddress());
//			form.set("phone", loan.getPhone());
//			form.set("sponsorName1", loan.getSponsorName1());
//			form.set("sponsorIdno1", loan.getSponsorIdno1());
//			form.set("sponsorRelation1", loan.getSponsorRelation1());
//			form.set("sponsorPhone1", loan.getSponsorPhone1());
//			form.set("sponsorName2", loan.getSponsorName2());
//			form.set("sponsorIdno2", loan.getSponsorIdno2());
//			form.set("sponsorRelation2", loan.getSponsorRelation2());
//			form.set("sponsorPhone2", loan.getSponsorPhone2());
//			form.set("gradeYear", loan.getGradeYear());
//			form.set("gradeMonth", loan.getGradeMonth());
		} else {
			form.set("address", student.getPermAddr());
		}
	}

	private StdLoan processForm(DynaActionForm form, Student student,
			String schoolTerm) {
		StdLoan loan = new StdLoan();
		loan.setStudentNo(student.getStudentNo());
		loan.setSchoolTerm(schoolTerm);
//		loan
//				.setWifeIdno(StringUtils.isNotBlank(form.getString("wifeIdno")) ? form
//						.getString("wifeIdno").trim().toUpperCase()
//						: null);
//		loan
//				.setWifeName(StringUtils.isNotBlank(form.getString("wifeName")) ? form
//						.getString("wifeName").trim()
//						: null);
		loan
				.setFatherIdno(StringUtils.isNotBlank(form
						.getString("fatherIdno")) ? form
						.getString("fatherIdno").trim().toUpperCase() : null);
		loan
				.setFatherName(StringUtils.isNotBlank(form
						.getString("fatherName")) ? form
						.getString("fatherName").trim() : null);
		loan
				.setMotherIdno(StringUtils.isNotBlank(form
						.getString("motherIdno")) ? form
						.getString("motherIdno").trim().toUpperCase() : null);
		loan
				.setMotherName(StringUtils.isNotBlank(form
						.getString("motherName")) ? form
						.getString("motherName").trim() : null);
//		loan
//				.setAmount(StringUtils.isNotBlank(form.getString("amount")) ? Integer
//						.valueOf(form.getString("amount").trim())
//						: 0);
//		loan
//				.setAddress(StringUtils.isNotBlank(form.getString("address")) ? form
//						.getString("address").trim()
//						: null);
//		loan.setPhone(StringUtils.isNotBlank(form.getString("phone")) ? form
//				.getString("phone").trim() : null);
//		loan.setSponsorIdno1(StringUtils.isNotBlank(form
//				.getString("sponsorIdno1")) ? form.getString("sponsorIdno1")
//				.trim().toUpperCase() : null);
//		loan.setSponsorName1(StringUtils.isNotBlank(form
//				.getString("sponsorName1")) ? form.getString("sponsorName1")
//				.trim() : null);
//		loan.setSponsorPhone1(StringUtils.isNotBlank(form
//				.getString("sponsorPhone1")) ? form.getString("sponsorPhone1")
//				.trim() : null);
//		loan.setSponsorRelation1(StringUtils.isNotBlank(form
//				.getString("sponsorRelation1")) ? form.getString(
//				"sponsorRelation1").trim() : null);
//		loan.setSponsorIdno2(StringUtils.isNotBlank(form
//				.getString("sponsorIdno2")) ? form.getString("sponsorIdno2")
//				.trim().toUpperCase() : null);
//		loan.setSponsorName2(StringUtils.isNotBlank(form
//				.getString("sponsorName2")) ? form.getString("sponsorName2")
//				.trim() : null);
//		loan.setSponsorPhone2(StringUtils.isNotBlank(form
//				.getString("sponsorPhone2")) ? form.getString("sponsorPhone2")
//				.trim() : null);
//		loan.setSponsorRelation2(StringUtils.isNotBlank(form
//				.getString("sponsorRelation2")) ? form.getString(
//				"sponsorRelation2").trim() : null);
//		loan
//				.setGradeYear(StringUtils.isNotBlank(form
//						.getString("gradeYear")) ? form.getString("gradeYear")
//						: null);
//		loan
//				.setGradeMonth(StringUtils.isNotBlank(form
//						.getString("gradeMonth")) ? form
//						.getString("gradeMonth") : null);
		loan.setLastModified(new Date());
		return loan;
	}

}
