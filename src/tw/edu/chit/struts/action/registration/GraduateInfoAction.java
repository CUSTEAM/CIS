package tw.edu.chit.struts.action.registration;

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
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class GraduateInfoAction extends BaseLookupDispatchAction {

	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {
		
		//DynaActionForm aForm = (DynaActionForm)form;
		//log.debug("=======> campusInCharge=" + aForm.getString("campusInCharge"));
		setContentPage(request.getSession(false), "registration/GraduateInfo.jsp");
		return mapping.findForward("Main");
	}
	
	protected Map getKeyMethodMap() {
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

		Toolket.resetCheckboxCookie(response, "GraduateInfo");
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		//log.debug("======>dynaClass=" + aForm.getDynaClass().getName());
		//if (!aForm.getDynaClass().getName().equals("GraduateListForm")) {
		//	aForm = (DynaActionForm)session.getAttribute("GraduateListForm");
		//}
		
		String campusNo = aForm.getString("campusInCharge3");
		// String schoolNo = aForm.getString("schoolInCharge3");
		// String deptNo   = aForm.getString("deptInCharge3");
		String classNo  = aForm.getString("classInCharge3");
		log.debug("=======> campusNo=" + campusNo);
		log.debug("=======> classNo=" + classNo);
		
		//UserCredential credential = (UserCredential)session.getAttribute("Credential");
		UserCredential credential = getUserCredential(session);
		MemberManager mm = (MemberManager)getBean("memberManager");
		//List<Graduate> students = mm.findStudentsInChargeByUnits(campusNo, schoolNo, deptNo, classNo, credential.getClassInChargeSqlFilter());
		List<Graduate> students = mm.findGraduatesInChargeByGraduateInfoForm(aForm.getMap(), credential.getClassInChargeSqlFilter());
		log.debug("=======> students.size()=" + students.size());
		Graduate student;
		for (Iterator<Graduate> stuIter = students.iterator(); stuIter.hasNext();) {
			student = stuIter.next();
			student.setSex2(Toolket.getSex(student.getSex(), request));
			student.setDepartClass2(Toolket.getClassFullName(student.getDepartClass()));
			student.setOccurStatus2(Toolket.getStatus(student.getOccurStatus(), true));
		}
		session.setAttribute("GraduateInfoList", students);
		setContentPage(session, "registration/GraduateInfo.jsp");
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
		session.setAttribute("GraduateInfoEdit", initValue);
		setContentPage(session, "registration/GraduateInfoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward modify(ActionMapping mapping,
								ActionForm form,
								HttpServletRequest request,
								HttpServletResponse response)
			throws Exception {

		List<Graduate> selGraduates = getGraduateSelectedList(request);
		Graduate student = selGraduates.get(0);
		Map initValue = new HashMap();
		initValue.put("mode", "Modify");
		setGraduateInitValue(student, initValue);
		HttpSession session = request.getSession(false);
		session.setAttribute("GraduateInfoEdit", initValue);
		session.setAttribute("GraduateInEdit", student);
		setContentPage(session, "registration/GraduateInfoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward view(ActionMapping mapping,
							  ActionForm form,
							  HttpServletRequest request,
							  HttpServletResponse response)
			throws Exception {

		List<Graduate> selGraduates = getGraduateSelectedList(request);
		Graduate student = selGraduates.get(0);
		Map initValue = new HashMap();
		initValue.put("mode", "View");
		setGraduateInitValue(student, initValue);
		HttpSession session = request.getSession(false);
		session.setAttribute("GraduateInfoEdit", initValue);
		session.setAttribute("GraduateInEdit", student);
		setContentPage(session, "registration/GraduateInfoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward deleteVerify(ActionMapping mapping,
									  ActionForm form,
									  HttpServletRequest request,
									  HttpServletResponse response)
			throws Exception {

		List<Graduate> selGraduates = getGraduateSelectedList(request);
		Toolket.setAllCheckboxCookie(response, "GraduateDelete", selGraduates.size());
		HttpSession session = request.getSession(false);
		session.setAttribute("GraduateInfoDelete", selGraduates);
		setContentPage(session, "registration/GraduateInfoDelete.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward deleteConfirm(ActionMapping mapping,
			  						   ActionForm form,
			  						   HttpServletRequest request,
			  						   HttpServletResponse response)
			throws Exception {

		List<Graduate> selGraduates = getGraduateDeletedList(request);
		MemberManager mm = (MemberManager)getBean("memberManager");
		List undeletedGraduates = mm.deleteGraduates(selGraduates, Toolket.getBundle(request, "messages.registration"));
		HttpSession session = request.getSession(false);
		session.removeAttribute("GraduateInfoDelete");
		if (undeletedGraduates.size() == 0) {
			return list(mapping, form, request, response);
		} else {
			session.setAttribute("UndeletedGraduateInfo", undeletedGraduates);
			setContentPage(session, "registration/GraduateInfoUndelete.jsp");
			return mapping.findForward("Main");
		}
	}

	public ActionForward deleteCancel(ActionMapping mapping,
			  						  ActionForm form,
			  						  HttpServletRequest request,
			  						  HttpServletResponse response)
			throws Exception {
		
		request.getSession(false).removeAttribute("GraduateInfoDelete");
		Toolket.resetCheckboxCookie(response, "GraduateInfo");
		Toolket.resetCheckboxCookie(response, "GraduateDelete");
		setContentPage(request.getSession(false), "registration/GraduateInfo.jsp");
		return mapping.findForward("Main");
	}

	@SuppressWarnings("unchecked")
	private List getGraduateSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "GraduateInfo");
		List<Graduate> students = (List<Graduate>)session.getAttribute("GraduateInfoList");
		List<Graduate> selGraduates = new ArrayList<Graduate>();
		Graduate stu;
		MemberDAO dao =(MemberDAO)getBean("memberDAO");
		for (Iterator<Graduate> stuIter = students.iterator(); stuIter.hasNext();) {
			stu = stuIter.next();
			if (Toolket.isValueInCookie(stu.getOid().toString(), oids)) {
				dao.reload(stu);
				selGraduates.add(stu);
			}
		}
		return selGraduates;
	}
	
	@SuppressWarnings("unchecked")
	private List getGraduateDeletedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "GraduateDelete");
		List<Graduate> students = (List<Graduate>)session.getAttribute("GraduateInfoDelete");
		List<Graduate> selGraduates = new ArrayList<Graduate>();
		Graduate stu;
		for (Iterator<Graduate> stuIter = students.iterator(); stuIter.hasNext();) {
			stu = stuIter.next();
			if (Toolket.isValueInCookie(stu.getOid().toString(), oids)) {
				selGraduates.add(stu);
			}
		}
		return selGraduates;
	}
	
	@SuppressWarnings("unchecked")
	private void setGraduateInitValue(Graduate student, Map initValue) {
		
		initValue.put("name", student.getStudentName());
		initValue.put("studentNo", student.getStudentNo());
		MemberDAO dao = (MemberDAO)getBean("memberDAO");
		Clazz clazz = dao.findClassByClassNo(student.getDepartClass());
		if (clazz != null) {
			initValue.put("campusInCharge", clazz.getCampusNo());
			initValue.put("schoolInCharge", clazz.getSchoolNo());
			initValue.put("deptInCharge", clazz.getDeptNo());										
			initValue.put("classInCharge", clazz.getClassNo());
		}
		initValue.put("sex", student.getSex());
		if (student.getBirthday() != null) {
			initValue.put("birthDate", Toolket.printNativeDate(student.getBirthday()));
		}
		initValue.put("idNo", student.getIdno());
		if (student.getEntrance() != null) {
			initValue.put("entrance", Toolket.Serial2YearMonth(student.getEntrance()));
		}
		if (student.getGradyear() != null) {
			initValue.put("gradYear", String.valueOf(student.getGradyear()));
		}
		initValue.put("entranceIdentity", student.getIdent());
		initValue.put("group", student.getDivi());
		initValue.put("basicIdentity", student.getIdentBasic());
		initValue.put("commZip", student.getCurrPost());
		initValue.put("commAddress", student.getCurrAddr());
		initValue.put("gradSchool", student.getSchlCode());
		initValue.put("gradOrNot", student.getGraduStatus());
		initValue.put("parentName", student.getParentName());
		initValue.put("phone", student.getTelephone());
		initValue.put("permZip", student.getPermPost());
		initValue.put("permAddress", student.getPermAddr());
		initValue.put("status", student.getOccurStatus());
		if (student.getOccurDate() != null) {
			initValue.put("statusDate", Toolket.printNativeDate(student.getOccurDate()));
		}
		if (student.getOccurYear() != null) {
			initValue.put("statusYear", String.valueOf(student.getOccurYear()));
		}
		initValue.put("statusTerm", student.getOccurTerm());
		initValue.put("statusCause", student.getOccurCause());
		initValue.put("extraStatus", student.getExtraStatus());
		initValue.put("extraDept", student.getExtraDept());
		initValue.put("docNo", student.getOccurDocno());
		initValue.put("graduateNo", student.getOccurGraduateNo());
		initValue.put("email", student.getEmail());
		initValue.put("cellPhone", student.getCellPhone());
		initValue.put("identityRemark", student.getIdentRemark());
		initValue.put("ename", student.getStudentEname());
	}

}

