package tw.edu.chit.struts.action.tutor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Exam;
import tw.edu.chit.model.Investigation;
import tw.edu.chit.model.Military;
import tw.edu.chit.model.Other;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.WorkDuty;
import tw.edu.chit.model.WorkNature;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class GraduateInvestigationAction extends BaseLookupDispatchAction {

	static Calendar BEGIN;
	static Calendar END;
	static Calendar INFO_END;

	private static final String STD_LIST = "StudentInfoListT";

	static {
		// 出路:每年3月1日~9月30日
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.MARCH);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		BEGIN = cal;
		cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 30);
		END = INFO_END = cal;

		// 通訊:每年3月10日~6月30日
		// Calendar cal1 = Calendar.getInstance();
		// cal1.set(Calendar.MONTH, Calendar.JUNE);
		// cal1.set(Calendar.DAY_OF_MONTH, 30);
		// INFO_END = cal1;
	}

	/**
	 * 進入學生調查資料畫面
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
		session.removeAttribute(STD_LIST);
		Toolket.resetCheckboxCookie(response, "StudentInfo");
		UserCredential credential = getUserCredential(session);
		Clazz[] clazz = credential.getClassInChargeAryT();
		Date now = new Date();
		boolean isAllow = now.after(BEGIN.getTime())
				&& now.before(END.getTime());

		if (isAllow) {
			if (clazz == null || clazz.length == 0) {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "您並非隸屬於某班級之導師身分,因此無法使用本功能,對不起"));
				saveMessages(request, messages);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			} else {
				Clazz c = clazz[0];
				DynaActionForm aForm = (DynaActionForm) form;
				aForm.set("campusInChargeT", c.getCampusNo());
				aForm.set("schoolInChargeT", c.getSchoolNo());
				aForm.set("deptInChargeT", c.getDeptNo());
				aForm.set("classInChargeT", c.getClassNo());
				aForm.set("campusInCharge3", c.getCampusNo());
				aForm.set("schoolInCharge3", c.getSchoolNo());
				aForm.set("deptInCharge3", c.getDeptNo());
				aForm.set("classInCharge3", c.getClassNo());
				session.setAttribute("NO", c.getClassNo());
				return search(mapping, form, request, response);
			}
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "可輸入時間為：" + sdf.format(BEGIN.getTime())
							+ " ～ " + sdf.format(END.getTime())));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

	}

	/**
	 * 查詢導生資料
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

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("name3", ""); // 避免Exception
		UserCredential credential = getUserCredential(session);
		// String departClass = processClassInfo(aForm);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		session.setAttribute("InvesBegin", sdf.format(BEGIN.getTime()));
		session.setAttribute("InvesEnd", sdf.format(END.getTime()));
		session.setAttribute("InvesInfoEnd", sdf.format(INFO_END.getTime()));

		String schoolYear = cm.getNowBy("School_year");
		// String schoolTerm = cm.getNowBy("School_term");

		String hql = "SELECT s, i FROM Investigation i, Student s "
				+ "WHERE i.studentNo = s.studentNo AND i.schoolYear = ? "
				+ "AND i.tutorIdno = ? ORDER BY i.studentNo";
		List<Object> ret = am.find(hql, new Object[] { schoolYear,
				credential.getMember().getIdno().toUpperCase() });

		if (!ret.isEmpty()) {
			List<DynaBean> beans = new LinkedList<DynaBean>();
			DynaBean bean = null;
			Object[] o = null;

			for (Object obj : ret) {

				o = (Object[]) obj;
				bean = new LazyDynaBean();
				bean.set("student", (Student) o[0]);
				bean.set("investigation", (Investigation) o[1]);

				beans.add(bean);
			}

			session.setAttribute("NO", credential.getMember().getIdno()
					.toUpperCase());
			session.setAttribute(STD_LIST, beans);
		}

		setContentPage(session, "studaffair/GraduateInvestigation.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 查詢導生詳細資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("schoolName", "");
		aForm.set("canpus", "");
		aForm.set("level", "");
		aForm.set("invesType", null);
		aForm.set("workNatureCode", "0");
		aForm.set("workDutyCode", "0");
		aForm.set("workStartYear", "");
		aForm.set("workStartMonth", "");
		aForm.set("salaryRange", "");
		aForm.set("armyType", "");
		aForm.set("exam", "0");
		aForm.set("other", "0");
		aForm.set("companyType", "0");

		Date now = new Date();
		boolean isAllow = now.after(BEGIN.getTime())
				&& now.before(INFO_END.getTime());
		session.setAttribute("isAllow", isAllow);

		List<WorkNature> workNatures = am.findWorkNatureBy(new WorkNature());
		List<WorkDuty> workDutys = am.findWorkDutyBy(new WorkDuty());

		String[] codes = new String[workNatures.size()];
		String[] names = new String[workNatures.size()];
		for (int i = 0; i < workNatures.size(); i++) {
			codes[i] = workNatures.get(i).getCode();
			names[i] = workNatures.get(i).getName();
		}
		aForm.set("workNatureCodes", codes);
		aForm.set("workNatureNames", names);

		codes = new String[workDutys.size()];
		names = new String[workDutys.size()];
		for (int i = 0; i < workDutys.size(); i++) {
			codes[i] = workDutys.get(i).getCode();
			names[i] = workDutys.get(i).getName();
		}
		aForm.set("workDutyCodes", codes);
		aForm.set("workDutyNames", names);

		aForm
				.set(
						"years",
						getYearArray(((CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME))
								.getNowBy("School_year")));

		String invesOid = Toolket.getSelectedIndexFromCookie(request,
				"StudentInfo").replaceAll("\\|", "");
		session.setAttribute("stdOid", invesOid);
		Investigation inves = (Investigation) am.getObject(Investigation.class,
				Integer.valueOf(invesOid));
		Student student = mm.findStudentByNo(inves.getStudentNo());
		session.setAttribute("xxxxx", inves);
		session.setAttribute("StudentInfoEdit", student);
		student.setDepartClass2(Toolket.getClassFullName(student
				.getDepartClass()));
		student.setSex2(Toolket.getSex(student.getSex()));

		// 不可更改stmd內資料
		student.setCurrPost(StringUtils.isNotBlank(inves.getPost()) ? inves
				.getPost() : student.getCurrPost());
		student.setCurrAddr(StringUtils.isNotBlank(inves.getAddress()) ? inves
				.getAddress() : student.getCurrAddr());
		student.setEmail(StringUtils.isNotBlank(inves.getEmail()) ? inves
				.getEmail() : student.getEmail());
		student
				.setCellPhone(StringUtils.isNotBlank(inves.getCellPhone()) ? inves
						.getCellPhone()
						: student.getCellPhone());
		student.setTelephone(StringUtils.isNotBlank(inves.getPhone()) ? inves
				.getPhone() : student.getTelephone());
		if (StringUtils.isBlank(inves.getInvesType())) {
			aForm.set("invesType", "0");
			inves.setInvesType("0");
		} else {
			aForm.set("invesType", inves.getInvesType());
			inves.setInvesType(inves.getInvesType());
		}

		if (StringUtils.isNotBlank(inves.getInvesType())) {
			switch (Integer.parseInt(inves.getInvesType())) {
				case 0:
					aForm.set("foreignOrNot", inves.getForeignOrNot());
					aForm.set("schoolName", inves.getSchoolName());
					aForm.set("canpus", inves.getCanpus());
					aForm.set("level", inves.getLevel());
					break;

				case 1:
					aForm.set("company", inves.getCompany());
					aForm.set("companyType", inves.getCompanyType());
					aForm.set("workNatureCode", inves.getWorkNature());
					aForm.set("workDutyCode", inves.getWorkDuty());
					aForm.set("companyPhone", inves.getCompanyPhone());
					aForm.set("workTitle", inves.getWorkTitle());
					aForm.set("bossName", inves.getBossName());
					aForm.set("bossEmail", inves.getBossEmail());
					aForm.set("bossAddr", inves.getBossAddr());
					if (inves.getWorkStart() != null) {
						Calendar workStartDate = Calendar.getInstance();
						workStartDate.setTime(inves.getWorkStart());
						aForm.set("workStartYear", String.valueOf(workStartDate
								.get(Calendar.YEAR) - 1911));
						aForm.set("workStartMonth", String
								.valueOf(workStartDate.get(Calendar.MONTH)));
					}
					aForm.set("salaryRange", inves.getSalaryRange());
					break;

				case 2:
					// inves.setMilitary(Military.YES);
					aForm.set("armyType", inves.getArmyType());
					aForm.set("armyName", inves.getArmyName());
					aForm.set("armyLevel", inves.getArmyLevel());
					break;

				case 3:
					aForm.set("exam", "Y");
					break;
					
				case 4:
					aForm.set("other", "Y");
					break;
			}
		}

		setContentPage(session, "studaffair/GraduateInvestigationDetail.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 新增/更新導生詳細資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		String invesOid = request.getParameter("stdOid");
		Investigation inves = (Investigation) am.getObject(Investigation.class,
				Integer.valueOf(invesOid));
		inves = processInfo(inves, aForm);

		try {
			mm.txUpdateInvestigation(inves);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新成功"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新失敗"));
			saveErrors(request, messages);
		}

		return search(mapping, form, request, response);
	}

	/**
	 * 導生詳細資料
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
		map.put("Query", "search");
		map.put("Details", "detail");
		map.put("teacher.stayTime.btn.update", "update");
		map.put("Back", "back");
		return map;
	}

	private Investigation processInfo(Investigation inves, DynaActionForm form) {
		inves.setPost(form.getString("zip").trim());
		inves.setAddress(form.getString("addr").trim());
		inves.setEmail(form.getString("email").trim());
		inves.setCellPhone(form.getString("cellPhone").trim());
		inves.setPhone(form.getString("phone").trim());

		inves.setInvesType(form.getString("invesType"));
		switch (Integer.parseInt(form.getString("invesType"))) {
			case 0:
				inves.setForeignOrNot(form.getString("foreignOrNot"));
				inves.setSchoolName(form.getString("schoolName").trim());
				inves.setCanpus(form.getString("canpus").trim());
				inves.setLevel(form.getString("level").trim());
				break;

			case 1:
				inves.setCompany(form.getString("company").trim());
				inves.setCompanyType(form.getString("companyType"));
				inves.setWorkDuty(form.getString("workDutyCode"));
				inves.setWorkNature(form.getString("workNatureCode"));
				inves.setCompanyPhone(form.getString("companyPhone").trim());
				inves.setWorkTitle(form.getString("workTitle").trim());
				inves.setBossName(form.getString("bossName").trim());
				inves.setBossEmail(form.getString("bossEmail").trim());
				inves.setBossAddr(form.getString("bossAddr").trim());
				String workStartYear = form.getString("workStartYear");
				String workStartMonth = form.getString("workStartMonth");
				if (StringUtils.isNotBlank(workStartYear)) {
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, 1911 + Integer
							.parseInt(workStartYear));
					cal.set(Calendar.MONTH, Integer.parseInt(workStartMonth));
					cal.set(Calendar.DAY_OF_MONTH, 1);
					inves.setWorkStart(cal.getTime());
				}
				inves.setSalaryRange(form.getString("salaryRange"));
				break;

			case 2:
				inves.setArmyType(form.getString("armyType"));
				inves.setArmyName(form.getString("armyName").trim());
				inves.setArmyLevel(form.getString("armyLevel").trim());
				inves.setMilitary(Military.YES);
				break;
				
			case 3:
				inves.setExam(Exam.YES);
				break;

			case 4:
				// if (form.getString("other").equalsIgnoreCase("O"))
				// 有選就當作"是"
				inves.setOther(Other.YES);
				break;
		}

		inves.setLastModified(new Date());
		return inves;
	}

	static String[] getYearArray(String schoolYear) {
		int baseYear = 80;
		int year = Integer.parseInt(schoolYear) + 1;
		String[] years = new String[year - baseYear + 2];
		years[years.length - 1] = String.valueOf(baseYear);
		years[0] = "";
		for (int i = 1; i < years.length; i++)
			years[i] = String.valueOf((year--));
		return years;
	}

}
