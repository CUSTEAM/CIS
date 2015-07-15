package tw.edu.chit.struts.action.registration;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Register;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class MakeOldRegisterAction extends BaseLookupDispatchAction {

	/**
	 * 
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
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		session.removeAttribute("registerList");

		Map<String, Integer> m = Toolket.getNextYearTerm();
		Integer year = m.get(IConstants.PARAMETER_SCHOOL_YEAR);
		Integer sterm = m.get(IConstants.PARAMETER_SCHOOL_TERM);
		aForm.set("year", year.toString());
		aForm.set("sterm", sterm.toString());
		aForm.set("years", Toolket.getYearArray1(Toolket.getNextYearTerm().get(
				IConstants.PARAMETER_SCHOOL_YEAR).toString(), 3));

		setContentPage(session, "registration/MakeOldRegister.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward makeOld(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		String year = aForm.getString("year");
		String sterm = aForm.getString("sterm");
		String campusCode = aForm.getString("campusCode");
		String schoolType = aForm.getString("schoolType");

		String hql = "FROM Register r WHERE r.schoolYear = ? AND r.schoolTerm = ? "
				+ "AND r.campusCode = ? AND r.schoolType = ?";
		Collection<Register> regs = (List<Register>) am.find(hql, new Object[] {
				year, sterm, campusCode, schoolType });
		if (!regs.isEmpty())
			am.deleteAll(regs);
		hql = "SELECT SUM(f.money) FROM FeePay f WHERE f.kind = ? AND f.departClass = ?";

		Clazz clazz = new Clazz();
		clazz.setCampusNo(campusCode);
		clazz.setSchoolType(schoolType);
		Criterion cri = Restrictions.in("type", new String[] { "P", "E" }); // P實體班級,E延修班級
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("classNo"));
		Example example = Example.create(clazz).enableLike(MatchMode.EXACT);
		List<Clazz> clazzes = (List<Clazz>) am.findSQLWithCriteria(Clazz.class,
				example, null, orders, cri);

		List<Student> students = null;
		List<Register> saveOfRegs = new LinkedList<Register>();
		Register reg = null;
		String classNo = null, modifier = getUserCredential(session)
				.getMember().getIdno();
		int tuitionFee = 0, agencyFee = 0;
		Object o = null;
		boolean isNeedUpgrade = "1".equals(sterm);

		for (Clazz c : clazzes) {
			if (!Toolket.isLiteracyClass(c.getClassNo())) {
				classNo = c.getClassNo();
				students = mm.findStudentsByClassNo(classNo);

				if (students.isEmpty())
					continue;

				// System.out.println(c.getClassNo());
				if (isNeedUpgrade)
					classNo = Toolket.processClassNoUp(classNo);

				tuitionFee = 0;
				o = am.find(hql, new Object[] { "1", classNo }).get(0);
				tuitionFee = o == null ? 0 : ((Integer) o).intValue();

				agencyFee = 0;
				o = am.find(hql, new Object[] { "2", classNo }).get(0);
				agencyFee = o == null ? 0 : ((Integer) o).intValue();
				for (Student student : students) {
					reg = new Register();
					reg.setSchoolYear(year);
					reg.setSchoolTerm(sterm);
					reg.setIdno(student.getIdno().toUpperCase().trim());
					reg.setCampusCode(campusCode);
					reg.setSchoolType(schoolType);
					reg.setStudentName(student.getStudentName().trim());
					reg.setRealClassNo(classNo);
					reg.setRealStudentNo(student.getStudentNo().toUpperCase()
							.trim());
					reg.setVirClassNo(classNo);
					reg.setVirStudentNo(student.getStudentNo().toUpperCase()
							.trim());
					reg.setTuitionFee(tuitionFee);
					reg.setAgencyFee(agencyFee);
					reg.setType("O"); // O:舊生
					reg.setModifier(modifier);
					reg.setLastModified(new Date());

					saveOfRegs.add(reg);
				}
			}
		}

		try {
			am.saveOrUpdateAll(saveOfRegs);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "註冊檔資料已建立完成"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "註冊檔資料已建立失敗"));
			saveErrors(request, messages);
		}

		setContentPage(session, "registration/MakeOldRegister.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 
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

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;

		String year = aForm.getString("year");
		String sterm = aForm.getString("sterm");
		String campusCode = aForm.getString("campusCode");
		String schoolType = aForm.getString("schoolType");

		Register reg = new Register();
		reg.setSchoolYear(year);
		reg.setSchoolTerm(sterm);
		reg.setCampusCode(campusCode);
		reg.setSchoolType(schoolType);
		reg.setTuitionFee(null);
		reg.setTuitionAmount(null);
		reg.setAgencyFee(null);
		reg.setAgencyAmount(null);
		reg.setReliefTuitionAmount(null);
		reg.setLoanAmount(null);
		reg.setVulnerableAmount(null);
		Example example = Example.create(reg).enableLike(MatchMode.EXACT);
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("realClassNo"));
		List<Register> regs = (List<Register>) am.findSQLWithCriteria(
				Register.class, example, null, orders);
		session.setAttribute("registerList", regs);

		setContentPage(session, "registration/MakeOldRegister.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("createReg", "makeOld");
		map.put("course.onlineAddRemoveCourse.query", "query");
		return map;
	}

}
