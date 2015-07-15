package tw.edu.chit.struts.action.student;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_YEAR;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.CourseSyllabus;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DepartmentInfo;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.course.CourseSearchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class StudentCourseSearchAction extends BaseLookupDispatchAction {

	public static final String COURSE_INFO = "courseInfo";
	public static final String COURSE_SYLLABUS_INFO = "courseSyllabusInfo";
	public static final String COURSE_SYLLABUS_LIST = "courseSyllabusList";

	/**
	 * 處理學生第一次進入時動作
	 * 
	 * @commend 取得Student物件
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		String term = am.findTermBy(PARAMETER_SCHOOL_TERM);
		processRequest(request, term);
		request.setAttribute("readCourseIntro", "/Student/StudentCourseSearch");
		request.setAttribute("readCourseSyllabus",
				"/Student/StudentCourseSearch");
		setContentPage(session, "student/StudentCourseSearch.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 取得課程中英文簡介
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward readCourseIntro(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		Integer year = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
		// 本學期課表,所以School Term不用轉換
		Integer term = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_TERM));
		processReadCourseIntro(request, year, term);
		request.setAttribute("actionName", "/Student/StudentCourseSearch");
		setContentPage(request.getSession(false), "student/ViewCourseIntro.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 取得課程大綱
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward readCourseSyllabus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		Integer year = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
		// 本學期課表,所以School Term不用轉換
		Integer term = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_TERM));
		processReadCourseSyllabus(request, year, term);
		request.setAttribute("actionName", "/Student/StudentCourseSearch");
		setContentPage(request.getSession(false),
				"student/TeachSyllabusInfo.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 處理點選"返回"時動作
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("readCourseIntro", "readCourseIntro");
		map.put("readCourseSyllabus", "readCourseSyllabus");
		map.put("Back", "back");
		return map;
	}

	@SuppressWarnings("unchecked")
	protected void processRequest(HttpServletRequest request, String term) {
		HttpSession session = request.getSession(false);
		Student student = getUserCredential(session).getStudent();
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		List<Map> seldInfo = cm.findStudentSeldCourse(student.getStudentNo(),
				term);
		List<Map> list = cm.findCourseByStudentTermWeekdaySched(student, term);
		List<Map> t0001And50000List = cm.findT0001And50000ByStudent(student,
				term);
		int index = 0, seldCount = 0;
		// 即時計算選課人數
		for (Map m : seldInfo) {
			seldCount = cm.findSeldCountByDtimeOid((Integer) m.get("oid"));
			m.put("stu_select", seldCount);
		}
		
		// 加入班會與系時間
		for (Map map : t0001And50000List) {
			int week = (Integer) map.get("week");
			int begin = Integer.parseInt((String) map.get("begin"));
			index = ((week - 1) * 10) + begin - 1 + ((week - 1) * 5);
			list.set(index, map);
		}
		
		request.setAttribute(CourseSearchAction.COURSE_LIST, list);
		request.setAttribute(CourseSearchAction.WEEKDAY_LIST,
				CourseSearchAction.getWeekdayInfo());
		
		// 不管了,全Show出來課表
//		if (IConstants.HSIN_CHU_CAMPUS.equals(cm.findCampusNoByClassNo(student
//				.getDepartClass()))) {
//			request.setAttribute(CourseSearchAction.NODE_LIST,
//					CourseSearchAction.getNodeInfoForHsinChu());
//			request.setAttribute("rowsCols", CourseSearchAction
//					.getRowColInfo(IConstants.HSIN_CHU.charAt(0)));
//		} else {
//			String schoolType = cm.findSchoolTypeByClassNo(student
//					.getDepartClass());
//			if (IConstants.DAY.equalsIgnoreCase(schoolType)) {
//				request.setAttribute(CourseSearchAction.NODE_LIST,
//						CourseSearchAction.getNodeInfoForDay());
//				request.setAttribute("rowsCols", CourseSearchAction
//						.getRowColInfo(schoolType.charAt(0)));
//			} else if (IConstants.NIGHT.equalsIgnoreCase(schoolType)) {
//				request.setAttribute(CourseSearchAction.NODE_LIST,
//						CourseSearchAction.getNodeInfoForNight());
//				request.setAttribute("rowsCols", CourseSearchAction
//						.getRowColInfo(schoolType.charAt(0)));
//			} else if (IConstants.HOLIDAY.equalsIgnoreCase(schoolType)) {
//				request.setAttribute(CourseSearchAction.NODE_LIST,
//						CourseSearchAction.getNodeInfoForHoliday());
//				request.setAttribute("rowsCols", CourseSearchAction
//						.getRowColInfo(schoolType.charAt(0)));
//			}
//		}
		
		request.setAttribute(CourseSearchAction.NODE_LIST, CourseSearchAction
				.getNodeInfoForAll());
		request.setAttribute("rowsCols", CourseSearchAction.getRowColInfo('X'));
		
		if (!seldInfo.isEmpty()) {
			session
					.setAttribute(
							COURSE_INFO,
							doSomethingForDuplicate(doSomethingForCollect(doSomethingForView(seldInfo))));
		} else
			session.removeAttribute(COURSE_INFO);

	}

	protected void processReadCourseIntro(HttpServletRequest request,
			Integer year, Integer term) {
		Integer dtimeOid = Integer.valueOf(request.getParameter("oid"));
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		DynaBean bean = doSomethingForExtInfo(cm, dtimeOid, year, term, request);
		request.setAttribute("courseDetail", bean);
		Clazz clazz = getUserCredential(request.getSession(false))
				.getStudentClass();
		DepartmentInfo di = cm.findDepartmentInfoByCategory(clazz.getDeptNo());
		request.setAttribute("departmentInfo", di);
	}

	protected void processReadCourseSyllabus(HttpServletRequest request,
			Integer year, Integer term) {

		Integer dtimeOid = Integer.valueOf(request.getParameter("oid"));
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		CourseSyllabus cs = cm
				.getCourseSyllabusByDtimeOid(dtimeOid, year, term);
		if (cs != null) {
			cs = cm.findCourseSyllabusByOid(cs.getOid());
			request.setAttribute(COURSE_SYLLABUS_INFO, cs);
			request.setAttribute(COURSE_SYLLABUS_LIST, cs.getSyllabuses());
			// 判斷需要多少Syllabus輸入欄位
			request.setAttribute("counts", 18 - cs.getSyllabuses().size());
		}

		Dtime d = (Dtime) cm.getDtimeBy(dtimeOid.toString()).get(0);
		request.setAttribute("csnoInfo", cm
				.findCourseInfoByCscode(d.getCscode()));
	}

	@SuppressWarnings("unchecked")
	protected List<Map> doSomethingForView(List<Map> list) {
		for (Map map : list) {
			map.put("opt2", Toolket.getCourseOpt((String) map.get("opt")));
			String time = StudentOnlineAddDelCourseAction.getClassTimeInfo(
					(Integer) map.get("week"), (String) map.get("begin"),
					(String) map.get("end"));
			map.put("time", time);
		}
		return list;
	}

	// 將相同Dtime Oid集中,將上課時間作處理
	@SuppressWarnings("unchecked")
	protected List<Map> doSomethingForCollect(List<Map> list) {
		List<Map> ret = new LinkedList<Map>();
		Map temp = list.get(0);
		ret.add(temp);
		for (int i = 1; i < list.size(); i++) {
			Map map = list.get(i);
			if (temp.get("oid").toString().equals(map.get("oid").toString())) {
				temp.put("time", temp.get("time") + "," + map.get("time"));
				ret.add(temp);
			} else {
				ret.add(map);
				temp = map;
			}
		}
		return ret;
	}

	// 排除多餘Dtime Oid之清單
	@SuppressWarnings("unchecked")
	protected List<Map> doSomethingForDuplicate(List<Map> list) {
		List<Map> ret = new LinkedList<Map>();
		List<Integer> forCheck = new LinkedList<Integer>();
		forCheck.add((Integer) list.get(0).get("oid"));
		ret.add(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			Map map = list.get(i);
			if (!forCheck.contains((Integer) map.get("oid"))) {
				ret.add(map);
				forCheck.add((Integer) map.get("oid"));
			}
		}
		return ret;
	}

	private DynaBean doSomethingForExtInfo(CourseManager cm, Integer dtimeOid,
			Integer year, Integer term, HttpServletRequest request) {
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);

		DynaBean ret = new LazyDynaBean();
		Dtime dtime = cm.findDtimeBy(dtimeOid);
		String schoolType = cm.findSchoolTypeByClassNo(dtime.getDepartClass());
		Csno csno = cm.findCourseInfoByCscode(dtime.getCscode());
		Member member = mm.findMemberByAccount(dtime.getTechid());
		List<CourseIntroduction> cis = cm.getCourseIntroByDtimeOid(dtimeOid,
				year, term);
		CourseIntroduction ci = cis.isEmpty() ? new CourseIntroduction() : cis
				.get(0);
		request.setAttribute("chiCourse", csno.getChiName());
		request.setAttribute("engCourse", StringUtils.isNotBlank(csno
				.getEngName()) ? csno.getEngName() : "&nbsp;");
		request.setAttribute("gradeChi", StudentOnlineAddDelCourseAction
				.getFormalGrade(dtime.getOpt()));
		request.setAttribute("gradeEng", StudentOnlineAddDelCourseAction
				.getEngFormalGrade(dtime.getOpt()));
		request.setAttribute("credit", dtime.getCredit());
		request.setAttribute("thour", dtime.getThour());
		request.setAttribute("teacherChiName", member == null ? "&nbsp;"
				: member.getName());
		request.setAttribute("teacherEngName", "&nbsp;");
		request.setAttribute("chiIntro", ci.getChiIntro());
		request.setAttribute("engIntro", ci.getEngIntro());
		request.setAttribute("chiDivision", StudentOnlineAddDelCourseAction
				.getForamlDivition(schoolType));
		request.setAttribute("engDivision", StudentOnlineAddDelCourseAction
				.getEngFormalDivision(schoolType));
		request.setAttribute("chiProgram", StudentOnlineAddDelCourseAction
				.getFormalProgram(dtime.getDepartClass().substring(1, 3)));
		request.setAttribute("engProgram", StudentOnlineAddDelCourseAction
				.getEngFormalProgram(dtime.getDepartClass().substring(1, 3)));
		return ret;
	}

}
