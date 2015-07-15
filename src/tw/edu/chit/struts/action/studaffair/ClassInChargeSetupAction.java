package tw.edu.chit.struts.action.studaffair;

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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ClassInChargeSetupAction extends BaseLookupDispatchAction {
	
	protected String authorityTarget = null;

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query",		"list");
		map.put("Cancel", 		"cancel");
		map.put("OK",			"save");
		return map;		
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {

		String target = "";
		if (UserCredential.AuthorityOnTutor.equals(authorityTarget)) {
			target = "Tutor";
		} else if (UserCredential.AuthorityOnChairman.equals(authorityTarget)) {
			target = "Chairman";
		} else if (UserCredential.AuthorityOnDrillmaster.equals(authorityTarget)) {
			target = "Drillmaster";
		} 
		HttpSession session = request.getSession(false);
		Map<String, String> map = new HashMap<String, String>();
		map.put("baseDir", "StudAffair");
		map.put("authority", target);
		session.setAttribute("ClassInChargeSetup", map);
		
		try {
			DynaActionForm aForm = (DynaActionForm)form;
			String idno = aForm.getString("idno").trim();
			if (!"".equals(idno)) {
				return list(mapping, form, request, response);
			} else {
				setContentPage(request, "studaffair/ClassInChargeSetup.jsp");
				return mapping.findForward("Main");				
			}
		} catch(Exception e) {
			setContentPage(request, "studaffair/ClassInChargeSetup.jsp");
			return mapping.findForward("Main");
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward list(ActionMapping mapping,
			 				  ActionForm form,
			 				  HttpServletRequest request,
			 				  HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		String idno = aForm.getString("idno").trim();
		String target = "";
		if (UserCredential.AuthorityOnTutor.equals(authorityTarget)) {
			target = "Tutor";
		} else if (UserCredential.AuthorityOnChairman.equals(authorityTarget)) {
			target = "Chairman";
		} else if (UserCredential.AuthorityOnDrillmaster.equals(authorityTarget)) {
			target = "Drillmaster";
		} 
		Map map = aForm.getMap();
		map.put("baseDir", "StudAffair");
		map.put("authority", target);
		MemberManager manager = (MemberManager)getBean("memberManager");
		Employee emp = manager.findEmployeeByIdno(idno);
		if (emp != null) {
			map.put("found", true);
			map.put("empOid", emp.getOid());
			map.put("name",   emp.getName());
			/*
			List<Clazz> classes = manager.findClassInChargeByMemberAuthority2(
												getUserCredential(session).getMember().getOid(),
												UserCredential.AuthorityOnStudAffair);
			*/
			Clazz[] classes = getUserCredential(session).getClassInChargeArySAF();
			session.setAttribute("ClassList", classes);
			List<Clazz> classInCharge = manager.findClassInChargeByMemberAuthority(emp.getOid(), authorityTarget);
			StringBuffer cookieValue = new StringBuffer("|");
			for (Clazz clazz : classInCharge) {
				cookieValue.append(clazz.getOid().toString() + "|");
			}
			//log.debug("====> cookie=" + cookieValue.toString());
			Toolket.setCheckboxCookie(response, "ClassesInCharge", cookieValue.toString(), classInCharge.size());
		} else {
			map.put("found", false);
			map.put("empOid", 0);	
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.PeopleNotFound"));
			saveErrors(request, errors);
		}
		session.setAttribute("ClassInChargeSetup", map);
		//log.debug("=======> campusInCharge=" + aForm.getString("campusInCharge"));
		setContentPage(session, "studaffair/ClassInChargeSetup.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping,
			  				  ActionForm form,
			  				  HttpServletRequest request,
			  				  HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		//List<Clazz> selClasses = getClassSelectedList(request);
		String classOids = Toolket.getSelectedIndexFromCookie(request, "ClassesInCharge");
		Integer empOid = (Integer)aForm.get("empOid");
		//List<Clazz> classes = (List<Clazz>)session.getAttribute("ClassList");
		MemberManager manager = (MemberManager)getBean("memberManager");
		manager.txResetStudAffairClassInCharge(empOid, classOids, authorityTarget, getUserCredential(session));
		return mapping.findForward("Main");
	}
	
	public ActionForward cancel(ActionMapping mapping,
			  					ActionForm form,
			  					HttpServletRequest request,
			  					HttpServletResponse response)
			throws Exception {

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

