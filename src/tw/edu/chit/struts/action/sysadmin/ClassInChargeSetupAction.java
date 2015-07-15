package tw.edu.chit.struts.action.sysadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

/**
 * Base class for Class-incharge setup.
 * There are two indepentent function groups for the authority, 
 * one for TeachAffair(教務) and one for StudAffair(學務).
 * The setup tasks must be separated into to distinct child classes.
 * 
 * @author James F. Chiang
 *
 */
public class ClassInChargeSetupAction extends BaseLookupDispatchAction {
	
	protected String authorityTarget = null;

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Query",		"list");
		map.put("Cancel", 		"cancel");
		map.put("OK",			"save");
		map.put("BatchInsert",  "batchInsert");
		return map;		
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {

		String target = "";
		if (UserCredential.AuthorityOnTeachAffair.equals(authorityTarget)) {
			target = "TeachAffair";
		} else if (UserCredential.AuthorityOnStudAffair.equals(authorityTarget)) {
			target = "StudAffair";
		} else if (UserCredential.AuthorityOnTutor.equals(authorityTarget)) {
			target = "Tutor";
		}
		HttpSession session = request.getSession(false);
		Map map = new HashMap();
		map.put("baseDir", "SysAdmin");
		map.put("authority", target);
		session.setAttribute("ClassInChargeSetup", map);
		
		try {
			DynaActionForm aForm = (DynaActionForm)form;
			String idno = aForm.getString("idno").trim();
			if (!"".equals(idno)) {
				return list(mapping, form, request, response);
			} else {
				setContentPage(request, "sysadmin/ClassInChargeSetup.jsp");
				return mapping.findForward("Main");				
			}
		} catch(Exception e) {
			setContentPage(request, "sysadmin/ClassInChargeSetup.jsp");
			return mapping.findForward("Main");
		}
	}

	public ActionForward list(ActionMapping mapping,
			 				  ActionForm form,
			 				  HttpServletRequest request,
			 				  HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		String idno = aForm.getString("idno").trim();
		String target = "";
		if (UserCredential.AuthorityOnTeachAffair.equals(authorityTarget)) {
			target = "TeachAffair";
		} else if (UserCredential.AuthorityOnStudAffair.equals(authorityTarget)) {
			target = "StudAffair";
		} else if (UserCredential.AuthorityOnTutor.equals(authorityTarget)) {
			target = "Tutor";
		}
		Map map = aForm.getMap();
		map.put("baseDir", "SysAdmin");
		map.put("authority", target);
		MemberManager manager = (MemberManager)getBean("memberManager");
		Employee emp = manager.findEmployeeByIdno(idno);
		if (emp != null) {
			map.put("found", true);
			map.put("empOid", emp.getOid());
			map.put("name",   emp.getName());
			List<Clazz> classes = manager.findAllClasses();
			session.setAttribute("ClassList", classes);
			List<Clazz> classInCharge = manager.findClassInChargeByMemberAuthority(emp.getOid(), authorityTarget);
			StringBuffer cookieValue = new StringBuffer("|");
			for (Clazz clazz : classInCharge) {
				cookieValue.append(clazz.getOid().toString() + "|");
			}
			//log.debug("====> cookie=" + cookieValue.toString());
			log.debug("====> cookie length=" + cookieValue.length());
			//Toolket.resetCheckboxCookie(response, "ClassesInCharge", request);
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
		setContentPage(session, "sysadmin/ClassInChargeSetup.jsp");
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
		manager.txResetClassInCharge(empOid, classOids, authorityTarget);
		return mapping.findForward("Main");
	}
	
	public ActionForward batchInsert(ActionMapping mapping,
			  						 ActionForm form,
			  						 HttpServletRequest request,
			  						 HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		String classNo = aForm.getString("classNo").trim();
		if ("".equals(classNo) || "*".equals(classNo)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ClassNoMustInput"));
			saveErrors(request, errors);
			return mapping.findForward("Main");
		} else {
			Integer empOid = (Integer)aForm.get("empOid");
			MemberManager manager = (MemberManager)getBean("memberManager");
			manager.batchInsertClassInCharge(empOid, classNo, authorityTarget);
			return list(mapping, form, request, response);
		}
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
