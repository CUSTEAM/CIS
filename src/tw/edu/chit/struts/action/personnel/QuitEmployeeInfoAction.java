package tw.edu.chit.struts.action.personnel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

/**
 * 
 * 已停用
 *
 */
public class QuitEmployeeInfoAction extends BaseLookupDispatchAction {

	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {
		
		//DynaActionForm aForm = (DynaActionForm)form;
		//log.debug("=======> campusInCharge=" + aForm.getString("campusInCharge"));
		setContentPage(request.getSession(false), "personnel/QuitEmployeeInfo.jsp");
		return mapping.findForward("Main");
	}
	
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query",		"list");
		map.put("Forward.List",	"list");
		map.put("Create", 		"create");
		map.put("Delete", 		"deleteVerify");
		map.put("DeleteConfirm","deleteConfirm");
		map.put("Cancel", 		"deleteCancel");
		map.put("Continue",		"list");
		map.put("Modify",		"modify");
		map.put("View",			"view");
		return map;		
	}
	
	public ActionForward list(ActionMapping mapping,
			 				  ActionForm form,
			 				  HttpServletRequest request,
			 				  HttpServletResponse response)
			throws Exception {

		Toolket.resetCheckboxCookie(response, "QuitEmployeeInfo");
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		//UserCredential credential = getUserCredential(session);
		MemberManager mm = (MemberManager)getBean("memberManager");
		//List<DEmpl> quitEmployees = mm.findDEmplsByQuitEmployeeInfoForm(aForm.getMap());
		//log.debug("=======> quitEmployees.size()=" + quitEmployees.size());
		/*
		for (DEmpl employee : quitEmployees) {
			employee.setSex2(Toolket.getSex(employee.getSex(), request));
			employee.setCategory2(Toolket.getEmpCategory(employee.getCategory()));
			employee.setUnit2(Toolket.getEmpUnit(employee.getUnit()));
			employee.setPcode2(Toolket.getEmpRole(employee.getPcode()));
			employee.setStatus2(Toolket.getEmpStatus(employee.getStatus(), true));
		}
		*/
		//session.setAttribute("QuitEmployeeInfoList", quitEmployees);
		setContentPage(session, "personnel/QuitEmployeeInfo.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward create(ActionMapping mapping,
			  					ActionForm form,
			  					HttpServletRequest request,
			  					HttpServletResponse response)
			throws Exception {
		
		Map initValue = new HashMap();
		initValue.put("mode", "Create");
		HttpSession session = request.getSession(false);
		session.setAttribute("QuitEmployeeInfoEdit", initValue);
		setContentPage(session, "personnel/QuitEmployeeInfoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward modify(ActionMapping mapping,
								ActionForm form,
								HttpServletRequest request,
								HttpServletResponse response)
			throws Exception {

		List<DEmpl> selQuitEmployees = getQuitEmployeeSelectedList(request);
		DEmpl quitEmployee = selQuitEmployees.get(0);
		//Map initValue = new HashMap();
		//initValue.put("mode", "Modify");
		//quitEmployee.setMode("Modify");
		setQuitEmployeeInitValue(quitEmployee);
		HttpSession session = request.getSession(false);
		session.setAttribute("QuitEmployeeInfoEdit", quitEmployee);
		session.setAttribute("QuitEmployeeInEdit", quitEmployee);
		setContentPage(session, "personnel/QuitEmployeeInfoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward view(ActionMapping mapping,
							  ActionForm form,
							  HttpServletRequest request,
							  HttpServletResponse response)
			throws Exception {

		List<DEmpl> selEmployees = getQuitEmployeeSelectedList(request);
		DEmpl employee = selEmployees.get(0);
		/*
		Map initValue = new HashMap();
		initValue.put("mode", "View");
		setStudentInitValue(student, initValue);
		HttpSession session = request.getSession(false);
		session.setAttribute("StudentInfoEdit", initValue);
		session.setAttribute("StudentInEdit", student);
		setContentPage(session, "registration/StudentInfoEdit.jsp");
		*/
		//employee.setMode("View");
		setQuitEmployeeInitValue(employee);
		HttpSession session = request.getSession(false);
		session.setAttribute("QuitEmployeeInfoEdit", employee);
		session.setAttribute("QuitEmployeeInEdit", employee);
		setContentPage(session, "personnel/QuitEmployeeInfoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward deleteVerify(ActionMapping mapping,
									  ActionForm form,
									  HttpServletRequest request,
									  HttpServletResponse response)
			throws Exception {

		List<DEmpl> selEmployees = getQuitEmployeeSelectedList(request);
		Toolket.setAllCheckboxCookie(response, "QuitEmployeeDelete", selEmployees.size());
		HttpSession session = request.getSession(false);
		session.setAttribute("QuitEmployeeInfoDelete", selEmployees);
		setContentPage(session, "personnel/QuitEmployeeInfoDelete.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
	}
	
	public ActionForward deleteConfirm(ActionMapping mapping,
			  						   ActionForm form,
			  						   HttpServletRequest request,
			  						   HttpServletResponse response)
			throws Exception {

		List<DEmpl> selEmployees = getQuitEmployeeDeletedList(request);
		MemberManager mm = (MemberManager)getBean("memberManager");
		mm.deleteQuitEmployees(selEmployees, Toolket.getBundle(request, "messages.registration"));
		HttpSession session = request.getSession(false);
		session.removeAttribute("QuitEmployeeInfoDelete");
		return list(mapping, form, request, response);
		/*
		if (undeletedStudents.size() == 0) {
			return list(mapping, form, request, response);
		} else {
			session.setAttribute("UndeletedStudentInfo", undeletedStudents);
			setContentPage(session, "registration/StudentInfoUndelete.jsp");
			return mapping.findForward("Main");
		}
		*/
	}

	public ActionForward deleteCancel(ActionMapping mapping,
			  						  ActionForm form,
			  						  HttpServletRequest request,
			  						  HttpServletResponse response)
			throws Exception {
		
		request.getSession(false).removeAttribute("QuitEmployeeInfoDelete");
		Toolket.resetCheckboxCookie(response, "QuitEmployeeInfo");
		Toolket.resetCheckboxCookie(response, "QuitEmployeeDelete");
		setContentPage(request.getSession(false), "personnel/QuitEmployeeInfo.jsp");
		return mapping.findForward("Main");
	}

	@SuppressWarnings("unchecked")
	private List<DEmpl> getQuitEmployeeSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "QuitEmployeeInfo");
		List<DEmpl> employees = (List<DEmpl>)session.getAttribute("QuitEmployeeInfoList");
		List<DEmpl> selEmployees = new ArrayList<DEmpl>();
		MemberDAO dao =(MemberDAO)getBean("memberDAO");
		for (DEmpl employee : employees) {
			if (Toolket.isValueInCookie(employee.getOid().toString(), oids)) {
				dao.reload(employee);
				selEmployees.add(employee);
			}
		}
		return selEmployees;
	}
	
	@SuppressWarnings("unchecked")
	private List<DEmpl> getQuitEmployeeDeletedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "QuitEmployeeDelete");
		List<DEmpl> employees = (List<DEmpl>)session.getAttribute("QuitEmployeeInfoDelete");
		List<DEmpl> selEmployees = new ArrayList<DEmpl>();
		for (DEmpl emp : employees) {
			if (Toolket.isValueInCookie(emp.getOid().toString(), oids)) {
				selEmployees.add(emp);
			}
		}
		return selEmployees;
	}
	
	private void setQuitEmployeeInitValue(DEmpl employee) {
		/*
		if (employee.getBdate() != null) {
			employee.setBdate2(Toolket.printNativeDate(employee.getBdate()));
		}
		if (employee.getStartDate() != null) {
			employee.setStartDate2(Toolket.printNativeDate(employee.getStartDate()));
		}		
		if (employee.getEndDate() != null) {
			employee.setEndDate2(Toolket.printNativeDate(employee.getEndDate()));
		}		
		if (employee.getBdate() != null) {
			employee.setBdate2(Toolket.printNativeDate(employee.getBdate()));
		}
		*/
	}

}
