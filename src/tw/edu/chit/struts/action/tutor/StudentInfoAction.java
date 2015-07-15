package tw.edu.chit.struts.action.tutor;

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
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.course.CourseSearchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class StudentInfoAction extends BaseLookupDispatchAction {
	
	public static final String CLASS_FULL_NAME = "classFullName";
	public static final String COURSE_LIST = "courseList";
	public static final String WEEKDAY_LIST = "weekdayList";
	public static final String NODE_LIST = "nodeList";

	public static final String DAY = "D";
	public static final String NIGHT = "N";
	public static final String HOLIDAY = "H";
	public static final String HSIN_CHU = "S";
	public static final String HSIN_CHU_CAMPUS = "2";

	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {

		//DynaActionForm aForm = (DynaActionForm)form;
		//log.debug("=======> campusInCharge=" + aForm.getString("campusInCharge"));
		Toolket.resetCheckboxCookie(response, "StudentInfo");
		request.getSession(false).removeAttribute("StudentInfoListT");
		setContentPage(request.getSession(false), "studaffair/StudentInfoT.jsp");
		return mapping.findForward("Main");
	}

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query",		"list");
		map.put("Forward.List",	"list");
		map.put("Continue",		"list");
		map.put("Details",		"view");
		map.put("Back", 		"back");
		map.put("scoreHist",    "scoreHist");
		map.put("ClassCourse",  "classCourse");
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
		
		String campusNo = aForm.getString("campusInChargeT");
		// String schoolNo = aForm.getString("schoolInChargeT");
		// String deptNo   = aForm.getString("deptInChargeT");
		String classNo  = aForm.getString("classInChargeT");
		log.debug("=======> campusNo=" + campusNo);
		log.debug("=======> classNo=" + classNo);
		
		//UserCredential credential = (UserCredential)session.getAttribute("Credential");
		UserCredential credential = getUserCredential(session);
		MemberManager mm = (MemberManager)getBean("memberManager");
		session.setAttribute("classCourseNo", classNo);
		//List<Student> students = mm.findStudentsInChargeByUnits(campusNo, schoolNo, deptNo, classNo, credential.getClassInChargeSqlFilter());
		List<Student> students = mm.findStudentsInChargeByStudentInfoFormT(aForm.getMap(), credential.getClassInChargeSqlFilterT());
		log.debug("=======> students.size()=" + students.size());
		Student student;
		for (Iterator<Student> stuIter = students.iterator(); stuIter.hasNext();) {
			student = stuIter.next();
			student.setSex2(Toolket.getSex(student.getSex(), request));
			student.setDepartClass2(Toolket.getClassFullName(student.getDepartClass()));
			student.setOccurStatus2(Toolket.getStatus(student.getOccurStatus(), true));
		}
		session.setAttribute("StudentInfoListT", students);
		setContentPage(session, "studaffair/StudentInfoT.jsp");
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
		session.setAttribute("OriginAction", "/Teacher/Tutor/StudentInfo");
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
		setContentPage(session, "studaffair/StudentInfoT.jsp");
		return mapping.findForward("Main");
		//} else {
		//log.debug("======>" + request.getContextPath());
		//backpage = backpage.substring(request.getContextPath().length());
		//return new ActionForward(backpage);
		//}
	}
	
	/**
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward scoreHist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		String studentNo = request.getParameter("no");
		Csno csno = null;
		ScoreHist scoreHist = new ScoreHist(studentNo);
		List<ScoreHist> scoreHistList = sm.findScoreHistBy(scoreHist);
		for (ScoreHist hist : scoreHistList) {
			hist.setOptName(Toolket.getCourseOpt(hist.getOpt()));
			csno = cm.findCourseInfoByCscode(hist.getCscode());
			hist.setCscodeName(csno.getChiName());
		}
		request.setAttribute("scoreHistList", scoreHistList);
		request.setAttribute("actionName", "/Teacher/Tutor/StudentInfo");
		setContentPage(session, "teacher/StudentScoreHistDetail.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}
	
	/**
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward classCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);

		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		String departClass = (String) session.getAttribute("classCourseNo");

		List<Map> list = cm.findCourseByClassNoAndTerm(departClass, term);
		request.setAttribute(COURSE_LIST, list);
		request.setAttribute(WEEKDAY_LIST, CourseSearchAction.getWeekdayInfo());
		if (IConstants.HSIN_CHU_CAMPUS.equals(cm
				.findCampusNoByClassNo(departClass))) {
			request.setAttribute(NODE_LIST, CourseSearchAction
					.getNodeInfoForHsinChu());
			request.setAttribute("rowsCols", CourseSearchAction
					.getRowColInfo(HSIN_CHU.charAt(0)));
		} else {
			String schoolType = cm.findSchoolTypeByClassNo(departClass);
			if (IConstants.DAY.equalsIgnoreCase(schoolType)) {
				request.setAttribute(NODE_LIST, CourseSearchAction
						.getNodeInfoForDay());
				request.setAttribute("rowsCols", CourseSearchAction
						.getRowColInfo(schoolType.charAt(0)));
			} else if (IConstants.NIGHT.equalsIgnoreCase(schoolType)) {
				// 182311是假日上課,所以要以"H"處理
				if ("182311".equalsIgnoreCase(departClass)) {
					request.setAttribute(NODE_LIST, CourseSearchAction
							.getNodeInfoForHoliday());
					request.setAttribute("rowsCols", CourseSearchAction
							.getRowColInfo('H'));
				} else {
					request.setAttribute(NODE_LIST, CourseSearchAction
							.getNodeInfoForNight());
					request.setAttribute("rowsCols", CourseSearchAction
							.getRowColInfo(schoolType.charAt(0)));
				}
			} else if (IConstants.HOLIDAY.equalsIgnoreCase(schoolType)) {
				request.setAttribute(NODE_LIST, CourseSearchAction
						.getNodeInfoForHoliday());
				request.setAttribute("rowsCols", CourseSearchAction
						.getRowColInfo(schoolType.charAt(0)));
			}
		}

		setContentPage(session, "teacher/ClassCourse.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}
	
	@SuppressWarnings("unchecked")
	private List getStudentSelectedList(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "StudentInfo");
		List<Student> students = (List<Student>)session.getAttribute("StudentInfoListT");
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
	
	@SuppressWarnings("unchecked")
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
		
