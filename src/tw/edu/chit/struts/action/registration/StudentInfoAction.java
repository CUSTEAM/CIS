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
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class StudentInfoAction extends BaseLookupDispatchAction {

	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {
		
		//DynaActionForm aForm = (DynaActionForm)form;
		//log.debug("=======> campusInCharge=" + aForm.getString("campusInCharge"));
		setContentPage(request.getSession(false), "registration/StudentInfo.jsp");
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

		Toolket.resetCheckboxCookie(response, "StudentInfo");
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		//log.debug("======>dynaClass=" + aForm.getDynaClass().getName());
		//if (!aForm.getDynaClass().getName().equals("StudentListForm")) {
		//	aForm = (DynaActionForm)session.getAttribute("StudentListForm");
		//}
		
		String campusNo = aForm.getString("campusInCharge2");
		String schoolNo = aForm.getString("schoolInCharge2");
		String deptNo   = aForm.getString("deptInCharge2");
		String classNo  = aForm.getString("classInCharge2");
		log.debug("=======> campusNo=" + campusNo);
		log.debug("=======> classNo=" + classNo);
		
		//UserCredential credential = (UserCredential)session.getAttribute("Credential");
		UserCredential credential = getUserCredential(session);
		MemberManager mm = (MemberManager)getBean("memberManager");
		//List<Student> students = mm.findStudentsInChargeByUnits(campusNo, schoolNo, deptNo, classNo, credential.getClassInChargeSqlFilter());
		List<Student> students = mm.findStudentsInChargeByStudentInfoForm(aForm.getMap(), credential.getClassInChargeSqlFilter());
		log.debug("=======> students.size()=" + students.size());
		Student student;
		for (Iterator<Student> stuIter = students.iterator(); stuIter.hasNext();) {
			student = stuIter.next();
			student.setSex2(Toolket.getSex(student.getSex(), request));
			student.setDepartClass2(Toolket.getClassFullName(student.getDepartClass()));
			student.setOccurStatus2(Toolket.getStatus(student.getOccurStatus(), true));
		}
		session.setAttribute("StudentInfoList", students);
		/*
		List table = new ArrayList();
		Map  row;
		Student stu;
		for (Iterator<Student> stuIter = students.iterator(); stuIter.hasNext();) {
			stu = stuIter.next();
			row = new HashMap();
			row.put("oid", stu.getOid());
			row.put("name", stu.getStudentName());
			row.put("no", stu.getStudentNo());
			row.put("sex", Toolket.getSex(stu.getSex(), request));
			row.put("class", Toolket.getClassFullName(stu.getDepartClass()));
			row.put("idno", stu.getIdno());
			row.put("status", Toolket.getStatus(stu.getOccurStatus(), true));
			table.add(row);
		}
		session.setAttribute("StudentInfoList", table);
		*/
		setContentPage(session, "registration/StudentInfo.jsp");
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
		session.setAttribute("StudentInfoEdit", initValue);
		setContentPage(session, "registration/StudentInfoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward modify(ActionMapping mapping,
								ActionForm form,
								HttpServletRequest request,
								HttpServletResponse response)
			throws Exception {

		List<Student> selStudents = getStudentSelectedList(request);
		Student student = selStudents.get(0);
		Map initValue = new HashMap();
		initValue.put("mode", "Modify");
		setStudentInitValue(student, initValue);
		HttpSession session = request.getSession(false);
		session.setAttribute("StudentInfoEdit", initValue);
		session.setAttribute("StudentInEdit", student);
		setContentPage(session, "registration/StudentInfoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward view(ActionMapping mapping,
							  ActionForm form,
							  HttpServletRequest request,
							  HttpServletResponse response)
			throws Exception {

		List<Student> selStudents = getStudentSelectedList(request);
		Student student = selStudents.get(0);
		Map initValue = new HashMap();
		initValue.put("mode", "View");
		setStudentInitValue(student, initValue);
		HttpSession session = request.getSession(false);
		session.setAttribute("StudentInfoEdit", initValue);
		session.setAttribute("StudentInEdit", student);
		setContentPage(session, "registration/StudentInfoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward deleteVerify(ActionMapping mapping,
									  ActionForm form,
									  HttpServletRequest request,
									  HttpServletResponse response)
			throws Exception {

		List<Student> selStudents = getStudentSelectedList(request);
		Toolket.setAllCheckboxCookie(response, "StudentDelete", selStudents.size());
		HttpSession session = request.getSession(false);
		session.setAttribute("StudentInfoDelete", selStudents);
		setContentPage(session, "registration/StudentInfoDelete.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward deleteConfirm(ActionMapping mapping,
			  						   ActionForm form,
			  						   HttpServletRequest request,
			  						   HttpServletResponse response)
			throws Exception {

		List<Student> selStudents = getStudentDeletedList(request);
		MemberManager mm = (MemberManager)getBean("memberManager");
		List undeletedStudents = mm.deleteStudents(selStudents, Toolket.getBundle(request, "messages.registration"));
		HttpSession session = request.getSession(false);
		session.removeAttribute("StudentInfoDelete");
		if (undeletedStudents.size() == 0) {
			return list(mapping, form, request, response);
		} else {
			session.setAttribute("UndeletedStudentInfo", undeletedStudents);
			setContentPage(session, "registration/StudentInfoUndelete.jsp");
			return mapping.findForward("Main");
		}
	}

	public ActionForward deleteCancel(ActionMapping mapping,
			  						  ActionForm form,
			  						  HttpServletRequest request,
			  						  HttpServletResponse response)
			throws Exception {
		
		request.getSession(false).removeAttribute("StudentInfoDelete");
		Toolket.resetCheckboxCookie(response, "StudentInfo");
		Toolket.resetCheckboxCookie(response, "StudentDelete");
		setContentPage(request.getSession(false), "registration/StudentInfo.jsp");
		return mapping.findForward("Main");
	}

	private List getStudentSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "StudentInfo");
		List<Student> students = (List<Student>)session.getAttribute("StudentInfoList");
		List<Student> selStudents = new ArrayList<Student>();
		Student stu;
		MemberDAO dao =(MemberDAO)getBean("memberDAO");
		for (Iterator<Student> stuIter = students.iterator(); stuIter.hasNext();) {
			stu = stuIter.next();
			if (Toolket.isValueInCookie(stu.getOid().toString(), oids)) {
				dao.reload(stu);
				selStudents.add(stu);
			}
		}
		return selStudents;
	}
	
	private List getStudentDeletedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "StudentDelete");
		List<Student> students = (List<Student>)session.getAttribute("StudentInfoDelete");
		List<Student> selStudents = new ArrayList<Student>();
		Student stu;
		for (Iterator<Student> stuIter = students.iterator(); stuIter.hasNext();) {
			stu = stuIter.next();
			if (Toolket.isValueInCookie(stu.getOid().toString(), oids)) {
				selStudents.add(stu);
			}
		}
		return selStudents;
	}
	
	private void setStudentInitValue(Student student, Map initValue) {
		
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
		initValue.put("gradSchool", student.getSchlName());
		initValue.put("gradDept", student.getGradDept());
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
