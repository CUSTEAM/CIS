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
import tw.edu.chit.model.Empl;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class EmployeeInfoAction extends BaseLookupDispatchAction {

	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {
		
		//DynaActionForm aForm = (DynaActionForm)form;
		//log.debug("=======> campusInCharge=" + aForm.getString("campusInCharge"));
		request.getSession(false).removeAttribute("EmployeeInfoList");
		setContentPage(request.getSession(false), "personnel/EmployeeInfo.jsp");
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

		Toolket.resetCheckboxCookie(response, "EmployeeInfo");
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		//UserCredential credential = getUserCredential(session);
		MemberManager mm = (MemberManager)getBean("memberManager");
		List<Empl> employees = mm.findEmplsByEmployeeInfoForm(aForm.getMap());
		log.debug("=======> employees.size()=" + employees.size());
		/*
		for (Empl employee : employees) {
			employee.setSex2(Toolket.getSex(employee.getSex(), request));
			employee.setCategory2(Toolket.getEmpCategory(employee.getCategory()));
			employee.setUnit2(Toolket.getEmpUnit(employee.getUnit()));
			employee.setPcode2(Toolket.getEmpRole(employee.getPcode()));
			employee.setStatus2(Toolket.getEmpStatus(employee.getStatus(), true));
		}
		*/
		session.setAttribute("EmployeeInfoList", employees);
		setContentPage(session, "personnel/EmployeeInfo.jsp");
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
		session.setAttribute("EmployeeInfoEdit", initValue);
		setContentPage(session, "personnel/EmployeeInfoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward modify(ActionMapping mapping,
								ActionForm form,
								HttpServletRequest request,
								HttpServletResponse response)
			throws Exception {

		List<Empl> selEmployees = getEmployeeSelectedList(request);
		Empl employee = selEmployees.get(0);
		//Map initValue = new HashMap();
		//initValue.put("mode", "Modify");
		employee.setMode("Modify");
		setEmployeeInitValue(employee);
		HttpSession session = request.getSession(false);
		session.setAttribute("EmployeeInfoEdit", employee);
		session.setAttribute("EmployeeInEdit", employee);
		setContentPage(session, "personnel/EmployeeInfoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward view(ActionMapping mapping,
							  ActionForm form,
							  HttpServletRequest request,
							  HttpServletResponse response)
			throws Exception {

		List<Empl> selEmployees = getEmployeeSelectedList(request);
		Empl employee = selEmployees.get(0);
		/*
		Map initValue = new HashMap();
		initValue.put("mode", "View");
		setStudentInitValue(student, initValue);
		HttpSession session = request.getSession(false);
		session.setAttribute("StudentInfoEdit", initValue);
		session.setAttribute("StudentInEdit", student);
		setContentPage(session, "registration/StudentInfoEdit.jsp");
		*/
		employee.setMode("View");
		setEmployeeInitValue(employee);
		HttpSession session = request.getSession(false);
		session.setAttribute("EmployeeInfoEdit", employee);
		session.setAttribute("EmployeeInEdit", employee);
		setContentPage(session, "personnel/EmployeeInfoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward deleteVerify(ActionMapping mapping,
									  ActionForm form,
									  HttpServletRequest request,
									  HttpServletResponse response)
			throws Exception {

		List<Empl> selEmployees = getEmployeeSelectedList(request);
		Toolket.setAllCheckboxCookie(response, "EmployeeDelete", selEmployees.size());
		HttpSession session = request.getSession(false);
		session.setAttribute("EmployeeInfoDelete", selEmployees);
		setContentPage(session, "personnel/EmployeeInfoDelete.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
	}
	
	public ActionForward deleteConfirm(ActionMapping mapping,
			  						   ActionForm form,
			  						   HttpServletRequest request,
			  						   HttpServletResponse response)
			throws Exception {

		List<Empl> selEmployees = getEmployeeDeletedList(request);
		MemberManager mm = (MemberManager)getBean("memberManager");
		mm.deleteEmployees(selEmployees, Toolket.getBundle(request, "messages.registration"));
		HttpSession session = request.getSession(false);
		session.removeAttribute("EmployeeInfoDelete");
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
		
		request.getSession(false).removeAttribute("EmployeeInfoDelete");
		Toolket.resetCheckboxCookie(response, "EmployeeInfo");
		Toolket.resetCheckboxCookie(response, "EmployeeDelete");
		setContentPage(request.getSession(false), "personnel/EmployeeInfo.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	private List<Empl> getEmployeeSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "EmployeeInfo");
		List<Empl> employees = (List<Empl>)session.getAttribute("EmployeeInfoList");
		List<Empl> selEmployees = new ArrayList<Empl>();
		MemberDAO dao =(MemberDAO)getBean("memberDAO");
		for (Empl employee : employees) {
			if (Toolket.isValueInCookie(employee.getOid().toString(), oids)) {
				dao.reload(employee);
				selEmployees.add(employee);
			}
		}
		return selEmployees;
	}
	
	@SuppressWarnings("unchecked")
	private List<Empl> getEmployeeDeletedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "EmployeeDelete");
		List<Empl> employees = (List<Empl>)session.getAttribute("EmployeeInfoDelete");
		List<Empl> selEmployees = new ArrayList<Empl>();
		for (Empl emp : employees) {
			if (Toolket.isValueInCookie(emp.getOid().toString(), oids)) {
				selEmployees.add(emp);
			}
		}
		return selEmployees;
	}
	
	private void setEmployeeInitValue(Empl employee) {
		
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
	}

}
