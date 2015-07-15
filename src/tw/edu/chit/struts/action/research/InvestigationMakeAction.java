package tw.edu.chit.struts.action.research;

import java.util.Collection;
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
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Investigation;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class InvestigationMakeAction extends BaseLookupDispatchAction {

	/**
	 * 
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		session.removeAttribute("investigationList");

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		aForm.set("year", year);
		aForm.set("sterm", sterm);
		aForm.set("years", Toolket.getYearArray1(Toolket.getNextYearTerm().get(
				IConstants.PARAMETER_SCHOOL_YEAR).toString(), 3));

		setContentPage(session, "research/InvestigationMake.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 建立應屆畢業生資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward createInves(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		String year = aForm.getString("year");
		// String sterm = aForm.getString("sterm");

		String hql = "FROM Investigation i WHERE i.schoolYear = ?";
		Collection<Investigation> inveses = (List<Investigation>) am.find(hql,
				new Object[] { year });
		if (!inveses.isEmpty())
			am.deleteAll(inveses);

		Clazz clazz = new Clazz();
		Criterion cri = Restrictions.in("type", new String[] { "P", "E" }); // P實體班級,E延修班級
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("classNo"));
		Example example = Example.create(clazz).enableLike(MatchMode.EXACT);
		List<Clazz> clazzes = (List<Clazz>) am.findSQLWithCriteria(Clazz.class,
				example, null, orders, cri);

		List<Student> students = null;
		List<Investigation> saveOfInveses = new LinkedList<Investigation>();
		Investigation inves = null;
		Empl tutor = null;
		String classNo = null;

		for (Clazz c : clazzes) {
			if (!Toolket.isLiteracyClass(c.getClassNo())
					&& Toolket.isGraduateClass(c.getClassNo())) {
				classNo = c.getClassNo();
				students = mm.findStudentsByClassNo(classNo);
				tutor = Toolket.findTutorOfClass(classNo);

				if (students.isEmpty())
					continue;

				for (Student student : students) {

					inves = new Investigation();
					inves.setSchoolYear(year);
					// inves.setSchoolTerm(sterm);
					// inves.setStudent(student);
					inves.setStudentNo(student.getStudentNo());
					if (tutor != null)
						inves.setTutorIdno(tutor.getIdno().toUpperCase());

					inves.setLastModified(new Date());
					// student.setInvestigation(inves);

					saveOfInveses.add(inves);
				}
			}
		}

		try {
			am.saveOrUpdateAll(saveOfInveses);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "應屆畢業生資料已建立完成"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "應屆畢業生資料建立失敗"));
			saveErrors(request, messages);
		}

		setContentPage(session, "research/InvestigationMake.jsp");
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
		// String sterm = aForm.getString("sterm");

		String hql = "SELECT s, e FROM Student s, Investigation i, Empl e "
				+ "WHERE s.studentNo = i.studentNo AND i.tutorIdno = e.idno AND i.schoolYear = ? "
				+ "ORDER By s.departClass, s.studentNo";
		List<Object> ret = (List<Object>) am.find(hql, new Object[] { year });

		if (!ret.isEmpty()) {
			Object[] o = null;
			Student student = null;
			Empl tutor = null;
			List<DynaBean> beans = new LinkedList<DynaBean>();
			DynaBean bean = null;

			for (Object obj : ret) {
				o = (Object[]) obj;
				student = (Student) o[0];
				tutor = (Empl) o[1];

				bean = new LazyDynaBean();
				bean.set("studentNo", student.getStudentNo());
				bean.set("studentName", student.getStudentName());
				bean.set("departClass", Toolket.getClassFullName(student
						.getDepartClass()));
				bean.set("tutorName", tutor.getCname());

				beans.add(bean);
			}

			session.setAttribute("investigationList", beans);
		}

		setContentPage(session, "research/InvestigationMake.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("createInves", "createInves");
		map.put("course.onlineAddRemoveCourse.query", "query");
		return map;
	}

}
