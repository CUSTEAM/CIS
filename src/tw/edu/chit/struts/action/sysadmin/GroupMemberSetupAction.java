package tw.edu.chit.struts.action.sysadmin;

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

import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class GroupMemberSetupAction extends BaseLookupDispatchAction {
	
	protected String authorityTarget = null;

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Add",		"add");
		map.put("Remove", 	"remove");
		return map;		
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Toolket.resetCheckboxCookie(response, "GroupMember");
		MemberManager manager = (MemberManager)getBean("memberManager");
		List<Code5> groups = (List<Code5>)session.getAttribute("UserGroups");
		if (groups == null) {
			groups = manager.findAllUnits();
			session.setAttribute("UserGroups", groups);
		}
		DynaActionForm aForm = (DynaActionForm)form;
		String group = aForm.getString("group");
		String empIdno = aForm.getString("empIdno").trim();
		if (!"".equals(group)) {
			if ("".equals(empIdno)) {
				aForm.set("empIdno", "");
				return list(mapping, form, request, response);
			} else {
				return add(mapping, form, request, response);
			}
		} else {
			session.setAttribute("GroupMemberSetup", aForm.getMap());
			setContentPage(session, "sysadmin/GroupMemberSetup.jsp");
			return mapping.findForward("Main");	
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward list(ActionMapping mapping,
			 				  ActionForm form,
			 				  HttpServletRequest request,
			 				  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		String group = aForm.getString("group");
		Map map = aForm.getMap();
		session.setAttribute("GroupMemberSetup", map);
		MemberManager manager = (MemberManager)getBean("memberManager");
		List<Empl> members = manager.findEmplByGroup(group);
		session.setAttribute("MemberList", members);
		setContentPage(session, "sysadmin/GroupMemberSetup.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward add(ActionMapping mapping,
			  				 ActionForm form,
			  				 HttpServletRequest request,
			  				 HttpServletResponse response)
			throws Exception {
		
		//HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		String empIdno = aForm.getString("empIdno").trim();
		MemberManager manager = (MemberManager)getBean("memberManager");
		Employee emp = manager.findEmployeeByIdno(empIdno);
		if (emp == null) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.PeopleNotFound"));
			saveErrors(request, errors);
		} else {
			manager.addEmployeeToGroup(emp.getOid(), aForm.getString("group"));
			aForm.set("empIdno", "");
		}		
		return list(mapping, form, request, response);
	}
	
	public ActionForward remove(ActionMapping mapping,
			  					ActionForm form,
			  					HttpServletRequest request,
			  					HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		String group = aForm.getString("group");
		String empOids = Toolket.getSelectedIndexFromCookie(request, "GroupMember");
		String[] empOid = empOids.substring(1, empOids.length()-1).split("\\|");
		MemberManager manager = (MemberManager)getBean("memberManager");
		for (String aOid : empOid) {
			manager.removeEmployeeFromGroup(Integer.valueOf(aOid), group);
		}
		Toolket.resetCheckboxCookie(response, "GroupMember");
		return list(mapping, form, request, response);
	}
	
	/*
	private List<Clazz> getClassSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "ClassesInCharge");
		List<Clazz> classes = (List<Clazz>)session.getAttribute("ClassList");
		List<Clazz> selClasses = new ArrayList<Clazz>();
		MemberDAO dao =(MemberDAO)getBean("memberDAO");
		for (Clazz clazz : classes) {
			if (Toolket.isValueInCookie(clazz.getOid().toString(), oids)) {
				selClasses.add(clazz);
			}
		}
		return selClasses;
	}
	*/
}
