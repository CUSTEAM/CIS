package tw.edu.chit.struts.action.sysadmin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Member;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class UserPasswordResetAction extends BaseLookupDispatchAction {

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query", "query");
		map.put("ResetPassword", "reset");
		map.put("ResetInformixPassword", "resetInformix");
		return map;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			DynaActionForm aForm = (DynaActionForm) form;
			String account = aForm.getString("account").trim();
			if (!"".equals(account)) {
				return query(mapping, form, request, response);
			} else {
				setContentPage(request, "sysadmin/UserPasswordReset.jsp");
				return mapping.findForward("Main");
			}
		} catch (Exception e) {
			setContentPage(request, "sysadmin/UserPasswordReset.jsp");
			return mapping.findForward("Main");
		}
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		String account = aForm.getString("account").trim();

		MemberManager manager = (MemberManager) getBean("memberManager");
		Member member = manager.findMemberByAccount(account);

		if (member != null) {
			request.setAttribute("UserPasswordReset", member);
		} else {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.PeopleNotFound"));
			saveErrors(request, errors);
		}
		/*
		 * HttpSession session = request.getSession(false); Map map =
		 * aForm.getMap(); MemberDAO dao = (MemberDAO)getBean("memberDAO");
		 * WWPass user = dao.findWWPassByAccount(account); if (user != null) {
		 * map.put("found", true); map.put("priority", user.getPriority());
		 * map.put("opassword",user.getPassword()); if
		 * ("A".equals(user.getPriority())) { // Employee Empl emp =
		 * dao.findEmplByIdno(account); map.put("name", emp.getCname());
		 * map.put("unit", Toolket.getEmpUnit(emp.getUnit())); map.put("idno",
		 * emp.getIdno()); map.put("birthDate",
		 * Toolket.printDate(emp.getBdate())); } else if
		 * ("C".equals(user.getPriority())) { // Student Student stud =
		 * dao.findStudentByStudentNo(account); map.put("name",
		 * stud.getStudentName()); map.put("unit",
		 * Toolket.getClassFullName(stud.getDepartClass())); map.put("idno",
		 * stud.getIdno()); map.put("birthDate",
		 * Toolket.printDate(stud.getBirthday())); } } else { map.put("found",
		 * false); ActionMessages errors = new ActionMessages();
		 * errors.add(ActionMessages.GLOBAL_MESSAGE, new
		 * ActionMessage("Message.PeopleNotFound")); saveErrors(request,
		 * errors); } session.setAttribute("UserPasswordReset", map);
		 */
		// log.debug("=======> campusInCharge=" +
		// aForm.getString("campusInCharge"));
		setContentPage(request, "sysadmin/UserPasswordReset.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward reset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		String account = aForm.getString("account").trim();
		// String priority = aForm.getString("priority");
		MemberManager manager = (MemberManager) getBean("memberManager");
		Member member = manager.resetMemberPassword(account);
		if (member != null) {
			request.setAttribute("UserPasswordReset", member);
		} else {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.PeopleNotFound"));
			saveErrors(request, errors);
		}
		setContentPage(request, "sysadmin/UserPasswordReset.jsp");
		return mapping.findForward("Main");
		// return query(mapping, form, request, response);
	}

	public ActionForward resetInformix(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		String account = aForm.getString("account").trim();
		// String priority = aForm.getString("priority");
		MemberManager manager = (MemberManager) getBean("memberManager");
		Member member = manager.resetMemberInformixPassword(account);
		if (member != null) {
			request.setAttribute("UserPasswordReset", member);
		} else {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.PeopleNotFound"));
			saveErrors(request, errors);
		}
		setContentPage(request, "sysadmin/UserPasswordReset.jsp");
		return mapping.findForward("Main");
		// return query(mapping, form, request, response);
	}

}