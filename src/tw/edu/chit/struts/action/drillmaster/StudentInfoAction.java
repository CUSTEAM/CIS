package tw.edu.chit.struts.action.drillmaster;

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
		setContentPage(request.getSession(false), "studaffair/StudentInfoAll.jsp");
		return mapping.findForward("Main");
	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Query",		"list");
		map.put("Forward.List",	"list");
		map.put("Continue",		"list");
		map.put("Details",		"view");
		map.put("Back", 		"back");
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
		List<Student> students = mm.findStudentsByStudentInfoForm(aForm.getMap());
		log.debug("=======> students.size()=" + students.size());
		Student student;
		for (Iterator<Student> stuIter = students.iterator(); stuIter.hasNext();) {
			student = stuIter.next();
			student.setSex2(Toolket.getSex(student.getSex(), request));
			student.setDepartClass2(Toolket.getClassFullName(student.getDepartClass()));
			student.setOccurStatus2(Toolket.getStatus(student.getOccurStatus(), true));
		}
		session.setAttribute("StudentInfoList", students);
		setContentPage(session, "studaffair/StudentInfoAll.jsp");
		return mapping.findForward("Main");
		}
		
		
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
		session.setAttribute("OriginAction", "/Teacher/Drillmaster/StudentInfo");
		setContentPage(session, "studaffair/StudentInfoView.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward back(ActionMapping mapping,
							  ActionForm form,
							  HttpServletRequest request,
							  HttpServletResponse response)
			throws Exception {
		
		//String backpage = Toolket.getCookie(request, "BackPage");
		//log.debug("BackPage=" + backpage);
		//if ("".equals(backpage)) {
		HttpSession session = request.getSession(false);
		//((DynaActionForm)form).initialize(mapping);
		session.removeAttribute("StudentInfoEdit");
		Toolket.resetCheckboxCookie(response, "StudentInfo");
		setContentPage(session, "studaffair/StudentInfoAll.jsp");
		return mapping.findForward("Main");
		//} else {
		//log.debug("======>" + request.getContextPath());
		//backpage = backpage.substring(request.getContextPath().length());
		//return new ActionForward(backpage);
		//}
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
				break;		// one is enough
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
		initValue.put("departClass2", student.getDepartClass2());
		initValue.put("sex", student.getSex());
		if (student.getBirthday() != null) {
			//initValue.put("birthDate", Toolket.printNativeDate(student.getBirthday()));
			initValue.put("birthDate", Toolket.printMaskedNativeDate(student.getBirthday()));
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
		
