package tw.edu.chit.struts.action.student;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import tw.edu.chit.model.Aborigine;
import tw.edu.chit.model.RegistrationCard;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class StudentRegistrationInputAction extends BaseLookupDispatchAction {

	public static final String STUDENT_INFO = "stdInfo";
	public static final String STUDENT_REGI = "stdRegi";

	private static DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	private static DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 查詢新生學籍卡
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

		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);

		Student student = (Student) getUserCredential(session).getStudent();
		student = mm.findStudentByNo(student.getStudentNo());
		student.setDepartClass2(Toolket.getClassFullName(student
				.getDepartClass()));
		aForm.set("gradeType",
				StringUtils.isBlank(student.getGraduStatus()) ? ""
						: String.valueOf(Integer.parseInt(student
								.getGraduStatus()) - 1));
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		List<Aborigine> abors = mm.findAborigineBy(new Aborigine());
		String[] aborCodes = new String[0];
		String[] aborNames = new String[0];
		for (Aborigine abor : abors) {
			aborCodes = (String[]) ArrayUtils.add(aborCodes, abor.getCode());
			aborNames = (String[]) ArrayUtils.add(aborNames, abor.getName());
		}
		aForm.set("aborigineCodes", aborCodes);
		aForm.set("aborigineNames", aborNames);

		if (!Toolket.isNewStudentClass(student.getDepartClass())
				|| "2".equals(sterm)) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "對不起，必須是上學期且為新生入學者才可輸入學籍卡資料"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else if ("164".equalsIgnoreCase(StringUtils.substring(student
				.getDepartClass(), 0, 3))
				|| "142".equalsIgnoreCase(StringUtils.substring(student
						.getDepartClass(), 0, 3))) { // 因為日2技與日4技學生以填完學籍卡
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "對不起，日間部二技與四技新生不可修改學籍卡資料"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

		session.setAttribute("schoolYear", year);
		student.setSex2(Toolket.getSex(student.getSex()));
		student.setBirthday2(df.format(student.getBirthday()));
		request.setAttribute(STUDENT_INFO, student);
		request.setAttribute(STUDENT_REGI, processCard(student
				.getRegistrationCard()));
		setContentPage(session, "student/StudentRegistrationInput.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 新增/更新新生學籍卡
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward makeSure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		Student student = (Student) getUserCredential(session).getStudent();
		student = mm.findStudentByNo(student.getStudentNo());
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		DynaActionForm aForm = (DynaActionForm) form;
		if (student.getRegistrationCard() != null)
			mm.txDeleteRegistrationCard(student.getRegistrationCard());
		student = processForm(aForm, student, year);
		mm.txUpdateRegistrationCard(student);
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Course.errorN1", "更新完成"));
		saveMessages(request, messages);
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("student.registrationCard.makeSure", "makeSure"); // 確定
		return map;
	}

	private Student processForm(DynaActionForm form, Student student,
			String schoolYear) {

		String studentEName = form.getString("studentEName"); // 英文姓名
		String permPost = form.getString("permPost"); // 戶籍郵遞區號
		String permAddr = form.getString("permAddr"); // 戶籍地址
		String telephone = form.getString("telephone"); // 電話
		String cellPhone = form.getString("cellPhone"); // 手機(學生)
		String parentName = form.getString("parentName"); // 家長姓名
		String parentPost = form.getString("parentPost"); // 家長通訊郵遞區號
		String addr = form.getString("addr"); // 通訊地址
		String email = form.getString("email"); // 電郵信箱

		String gradeType = form.getString("gradeType"); // 畢業 OR 肄業 OR 結業
		String divi = form.getString("divi"); // 組別
		String gradeYear = form.getString("gradeYear"); // 畢業年月
		String birthCountry = form.getString("birthCountry"); // 國籍
		String birthPlace = form.getString("birthPlace"); // 出生地
		String inWay = form.getString("inWay"); // 入學方式
		String armyIn = form.getString("armyIn"); // 入伍日期
		armyIn = StringUtils.isBlank(armyIn) ? null : Toolket
				.parseNativeString(armyIn);
		String armyOut = form.getString("armyOut"); // 退伍日期
		armyOut = StringUtils.isBlank(armyOut) ? null : Toolket
				.parseNativeString(armyOut);
		String aborigine = form.getString("aborigine"); // 原住民族籍
		String foreignPlace = form.getString("foreignPlace"); // 僑生僑居地
		String foreignNo = form.getString("foreignNo"); // 外僑居留證統一證號
		String beforeSchool = form.getString("beforeSchool"); // 入學前學校
		String beforeDept = form.getString("beforeDept"); // 入學前科系
		String parentAge = form.getString("parentAge"); // 家長年齡
		String parentCareer = form.getString("parentCareer"); // 家長職業
		String parentRelationship = form.getString("parentRelationship"); // 家長關係
		String emergentPhone = form.getString("emergentPhone"); // 緊急連絡電話
		String emergentCell = form.getString("emergentCell"); // 緊急連絡手機
		String workPlace1 = form.getString("workPlace1"); // 服務機關
		String workTitle1 = form.getString("workTitle1"); // 職務
		String workBegin1 = form.getString("workBegin1"); // 起迄時間
		workBegin1 = StringUtils.isBlank(workBegin1) ? null : Toolket
				.parseNativeString(workBegin1);
		String workEnd1 = form.getString("workEnd1"); // 起迄時間
		workEnd1 = StringUtils.isBlank(workEnd1) ? null : Toolket
				.parseNativeString(workEnd1);
		String workPlace2 = form.getString("workPlace2");
		String workTitle2 = form.getString("workTitle2");
		String workBegin2 = form.getString("workBegin2");
		workBegin2 = StringUtils.isBlank(workBegin2) ? null : Toolket
				.parseNativeString(workBegin2);
		String workEnd2 = form.getString("workEnd2");
		workEnd2 = StringUtils.isBlank(workEnd2) ? null : Toolket
				.parseNativeString(workEnd2);
		String memberTitle1 = form.getString("memberTitle1"); // 稱謂
		String memberName1 = form.getString("memberName1"); // 姓名
		String memberAge1 = form.getString("memberAge1"); // 年齡
		String memberCareer1 = form.getString("memberCareer1"); // 職業
		String memberTitle2 = form.getString("memberTitle2");
		String memberName2 = form.getString("memberName2");
		String memberAge2 = form.getString("memberAge2");
		String memberCareer2 = form.getString("memberCareer2");
		String memberTitle3 = form.getString("memberTitle3");
		String memberName3 = form.getString("memberName3");
		String memberAge3 = form.getString("memberAge3");
		String memberCareer3 = form.getString("memberCareer3");
		String memberTitle4 = form.getString("memberTitle4");
		String memberName4 = form.getString("memberName4");
		String memberAge4 = form.getString("memberAge4");
		String memberCareer4 = form.getString("memberCareer4");

		student.setStudentEname(StringUtils.isBlank(studentEName) ? null
				: studentEName);
		student.setPermPost(StringUtils.isBlank(permPost) ? null : permPost
				.trim());
		student.setPermAddr(StringUtils.isBlank(permAddr) ? null : permAddr
				.trim());
		student.setParentName(StringUtils.isBlank(parentName) ? null
				: parentName.trim());
		student.setTelephone(StringUtils.isBlank(telephone) ? null : telephone
				.trim());
		student.setCellPhone(StringUtils.isBlank(cellPhone) ? null : cellPhone
				.trim());
		student.setEmail(StringUtils.isBlank(email) ? null : email.trim());
		student.setCurrPost(StringUtils.isBlank(parentPost) ? null : parentPost
				.trim());
		student.setCurrAddr(StringUtils.isBlank(addr) ? null : addr.trim());
		student.setGradDept(StringUtils.isBlank(beforeDept) ? null : beforeDept
				.trim());

		RegistrationCard rc = new RegistrationCard();
		rc.setSchoolYear(schoolYear); // 學年
		rc.setStudentNo(student.getStudentNo()); // 學號
		rc.setGradeType(gradeType);
		rc.setDiviName(StringUtils.isBlank(divi) ? null : divi.trim());
		rc.setGradeYear(StringUtils.isBlank(gradeYear) ? null : gradeYear
				.trim());
		rc.setBirthCountry(StringUtils.isBlank(birthCountry) ? null
				: birthCountry.trim());
		rc.setBirthPlace(StringUtils.isBlank(birthPlace) ? null : birthPlace
				.trim());
		rc.setInWay(inWay);
		try {
			rc.setArmyIn(StringUtils.isBlank(armyIn) ? null : java.sql.Date
					.valueOf(armyIn));
		} catch (Exception e) {
			rc.setArmyIn(null);
		}

		try {
			rc.setArmyOut(StringUtils.isBlank(armyOut) ? null : java.sql.Date
					.valueOf(armyOut));
		} catch (Exception e) {
			rc.setArmyOut(null);
		}
		rc.setAborigine(StringUtils.isBlank(aborigine) ? null : aborigine
				.trim());
		rc.setForeignPlace(StringUtils.isBlank(foreignPlace) ? null
				: foreignPlace.trim());
		rc.setForeignNo(StringUtils.isBlank(foreignNo) ? null : foreignNo
				.trim());
		rc.setBeforeSchool(StringUtils.isBlank(beforeSchool) ? null
				: beforeSchool.trim());
		// rc.setBeforeDept(StringUtils.isBlank(beforeDept) ? null : beforeDept
		// .trim()); // 換成stmd的grad_dept
		rc.setParentAge(StringUtils.isBlank(parentAge) ? null : (!StringUtils
				.isNumeric(parentAge) ? null : parentAge.trim()));
		rc.setParentCareer(StringUtils.isBlank(parentCareer) ? null
				: parentCareer.trim());
		rc.setParentRelationship(StringUtils.isBlank(parentRelationship) ? null
				: parentRelationship.trim());
		rc.setEmergentPhone(StringUtils.isBlank(emergentPhone) ? null
				: emergentPhone.trim());
		rc.setEmergentCell(StringUtils.isBlank(emergentCell) ? null
				: emergentCell.trim());
		rc.setWorkPlace1(StringUtils.isBlank(workPlace1) ? null : workPlace1
				.trim());
		rc.setWorkTitle1(StringUtils.isBlank(workTitle1) ? null : workTitle1
				.trim());
		try {
			rc.setWorkBegin1(StringUtils.isBlank(workBegin1) ? null
					: java.sql.Date.valueOf(workBegin1));
		} catch (Exception e) {
			rc.setWorkBegin1(null);
		}
		try {
			rc.setWorkEnd1(StringUtils.isBlank(workEnd1) ? null : java.sql.Date
					.valueOf(workEnd1));
		} catch (Exception e) {
			rc.setWorkEnd1(null);
		}
		rc.setWorkPlace2(StringUtils.isBlank(workPlace2) ? null : workPlace2
				.trim());
		rc.setWorkTitle2(StringUtils.isBlank(workTitle2) ? null : workTitle2
				.trim());
		try {
			rc.setWorkBegin2(StringUtils.isBlank(workBegin2) ? null
					: java.sql.Date.valueOf(workBegin2));
		} catch (Exception e) {
			rc.setWorkBegin2(null);
		}
		try {
			rc.setWorkEnd2(StringUtils.isBlank(workEnd2) ? null : java.sql.Date
					.valueOf(workEnd2));
		} catch (Exception e) {
			rc.setWorkEnd2(null);
		}
		rc.setMemberTitle1(StringUtils.isBlank(memberTitle1) ? null
				: memberTitle1.trim());
		rc.setMemberName1(StringUtils.isBlank(memberName1) ? null : memberName1
				.trim());
		rc.setMemberAge1(StringUtils.isBlank(memberAge1) ? null : (!StringUtils
				.isNumeric(memberAge1) ? null : memberAge1.trim()));
		rc.setMemberCareer1(StringUtils.isBlank(memberCareer1) ? null
				: memberCareer1.trim());
		rc.setMemberTitle2(StringUtils.isBlank(memberTitle2) ? null
				: memberTitle2.trim());
		rc.setMemberName2(StringUtils.isBlank(memberName2) ? null : memberName2
				.trim());
		rc.setMemberAge2(StringUtils.isBlank(memberAge2) ? null : (!StringUtils
				.isNumeric(memberAge2) ? null : memberAge2.trim()));
		rc.setMemberCareer2(StringUtils.isBlank(memberCareer2) ? null
				: memberCareer2.trim());
		rc.setMemberTitle3(StringUtils.isBlank(memberTitle3) ? null
				: memberTitle3.trim());
		rc.setMemberName3(StringUtils.isBlank(memberName3) ? null : memberName3
				.trim());
		rc.setMemberAge3(StringUtils.isBlank(memberAge3) ? null : (!StringUtils
				.isNumeric(memberAge3) ? null : memberAge3.trim()));
		rc.setMemberCareer3(StringUtils.isBlank(memberCareer3) ? null
				: memberCareer3.trim());
		rc.setMemberTitle4(StringUtils.isBlank(memberTitle4) ? null
				: memberTitle4.trim());
		rc.setMemberName4(StringUtils.isBlank(memberName4) ? null : memberName4
				.trim());
		rc.setMemberAge4(StringUtils.isBlank(memberAge4) ? null : (!StringUtils
				.isNumeric(memberAge4) ? null : memberAge4.trim()));
		rc.setMemberCareer4(StringUtils.isBlank(memberCareer4) ? null
				: memberCareer4.trim());
		//rc.setStudent(student);
		student.setRegistrationCard(rc);
		rc.setLastModified(new Date());
		return student;
	}

	private RegistrationCard processCard(RegistrationCard card) {
		if (card != null) {
			/*
			if (card.getArmyIn() != null)
				card.setArmyIn(processDateFormat(card.getArmyIn()));
			if (card.getArmyOut() != null)
				card.setArmyOutDateFormat(processDateFormat(card.getArmyOut()));
			if (card.getWorkBegin1() != null)
				card.setWorkBegin1DateFormat(processDateFormat(card.getWorkBegin1()));
			if (card.getWorkEnd1() != null)
				card.setWorkEnd1DateFormat(processDateFormat(card.getWorkEnd1()));
			if (card.getWorkBegin2() != null)
				card.setWorkBegin2DateFormat(processDateFormat(card.getWorkBegin2()));
			if (card.getWorkEnd2() != null)
				card.setWorkEnd2DateFormat(processDateFormat(card.getWorkEnd2()));
			if (card.getGradeType() != null) {

			}
			*/
		}
		return card;
	}

	private String processDateFormat(Date d) {
		String ret = df1.format(d);
		return String.valueOf(Integer
				.parseInt(StringUtils.substring(ret, 0, 4)) - 1911)
				+ StringUtils.substring(ret, 4);
	}

}
