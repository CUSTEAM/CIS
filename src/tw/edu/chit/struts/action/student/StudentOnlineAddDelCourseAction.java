package tw.edu.chit.struts.action.student;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_YEAR;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.CourseSyllabus;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DepartmentInfo;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Nabbr;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.StdAdcd;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.impl.exception.SeldException;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.course.OnlineAddRemoveCourseUpAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Parameters;
import tw.edu.chit.util.Toolket;

public class StudentOnlineAddDelCourseAction extends BaseLookupDispatchAction {

	public static final String SELD_LIST_NAME1 = "seldDataInfoOpt1";
	public static final String SELD_LIST_NAME2 = "seldDataInfoOpt2";
	public static final String OPENCS_LIST_NAME = "opencsList";
	public static final String OPENCS_COOKIE_NAME = "opencsInfo";
	public static final String OPT_COUNT = "optCount";
	public static final String SELD_ADCD_LIST = "seldAdcdList";
	public static final String HAS_LITERACY_SELD = "hasLiteracyClassSeld";
	public static final String HAS_ARMY_SELD = "hasArmyClassSeld";
	public static final String SUGGEST_COURSE = "suggestCourse";

	private static final int LITERACY_MIN_COUNT = 20;

	/**
	 * 取得學生班級必/選修課程資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		//String departClass = credential.getStudent().getDepartClass();
		//String schoolType = Toolket.isHsinChuStudent(departClass) ? "2" : cm.findSchoolTypeByClassNo(departClass);
		//if (!isDateValide(schoolType, 3))
			//throw new NullPointerException("請勿亂搞，謝謝。");

		clearData(session, response);
		ActionMessages errors = null;

		try {
			session.setAttribute("OnlineAddDelCourseForm", setFormProps(
					(DynaActionForm) form, credential));
			Clazz clazz = credential.getStudentClass();
			if (clazz != null) {
				// 跨選規則有改變,如果單以年級來看,如果使用者設定為3年級,則只有3與4年級可以選
				// 2年級不可選
				AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
				// 第三階段選課都是開學後開始,所以要以本學期來看
				String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
				// 選課人數下限, 專科*25, 大學*20, 研究所*5, 通識*20
				int selectedStudentMinCounts = cm.findMinCountBySchoolNo(clazz
						.getSchoolNo());
				Student student = credential.getStudent();
				List selCourseList = cm.getSeldDataByStudentNo(student
						.getStudentNo(), term);
				// Map<String, List<OnlineAddRemoveCourseUpAction.SeldDataInfo>>
				// seldDataInfoList = processSeldCourse(
				// selCourseList, selectedStudentMinCounts, cm, student,
				// term);
				Map<String, Object> seldDataInfoList = processSeldCourse(
						selCourseList, selectedStudentMinCounts, cm, student,
						term, clazz.getSchoolType());
				calCreditHours(request, seldDataInfoList);
				session.setAttribute(HAS_LITERACY_SELD,
						(Boolean) seldDataInfoList.get(HAS_LITERACY_SELD));
				session.setAttribute(HAS_ARMY_SELD, (Boolean) seldDataInfoList
						.get(HAS_ARMY_SELD));
				session.setAttribute(SELD_LIST_NAME1, seldDataInfoList
						.get(SELD_LIST_NAME1));
				session.setAttribute(SELD_LIST_NAME2, seldDataInfoList
						.get(SELD_LIST_NAME2));

			} else {
				errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", "登入資料有錯,請重新登入,謝謝!"));
				saveErrors(request, errors);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

		setContentPage(session, "student/OnlineAddDelCourse.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 處理學生退選課程資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> selds = getSeldDataListByIndex(request);
		if (!selds.isEmpty()) {
			float minus = 0.0f; // 
			CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
			// 取得選課欲退選之Oid列表
			StringBuffer seldBuf = new StringBuffer();
			for (OnlineAddRemoveCourseUpAction.SeldDataInfo info : selds) {
				seldBuf.append(info.getSeldOid()).append(",");
				minus += info.getCredit();
			}
			String inSyntax = StringUtils.substringBeforeLast(seldBuf
					.toString(), ",");
			log.info("Seld Oid SQL IN Syntax : " + inSyntax);
			UserCredential credential = getUserCredential(request
					.getSession(false));
			Student student = credential.getStudent();
			Clazz clazz = credential.getStudentClass();
			// 取得學分數上下限
			Map<String, String> minMax = null;
			if ("8".equals(student.getOccurStatus())
					|| Toolket.isDelayClass(student.getDepartClass())) {
				// 研修生無限制
				minMax = new HashMap<String, String>();
				minMax.put("min", "0");
				minMax.put("max", "1000");
			} else
				minMax = getCreditMinMax(clazz.getSchoolNo(), clazz.getGrade());

			boolean isDelayStudent = StringUtils.contains(Toolket
					.getClassFullName(student.getDepartClass()), "延修");
			float credits = (Float) request.getSession(false).getAttribute(
					"totalCredits");
			if (!isDelayStudent
					&& (credits - minus) < Float.parseFloat(minMax.get("min"))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "退選科目後之學分總數，已低於下限  (<"
								+ minMax.get("min") + ")，因此無法退選。"));
				saveErrors(request, messages);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}

			try {
				// true代表學生身分
				cm.txRemoveSelectedSeld(student.getStudentNo(), student
						.getDepartClass(), 3, inSyntax, true);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "退選完成"));
				saveMessages(request, messages);
			} catch (SeldException se) {
				// log.error(se.getMessage(), se);
				// 發生選課下限Exception
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", se.getMessage()));
				saveErrors(request, messages);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.onlineAddRemoveCourse.unselected"));
			saveMessages(request, messages);
		}

		return unspecified(mapping, form, request, response);
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

		Integer dtimeOid = Integer.valueOf(request.getParameter("oid"));
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		DynaBean bean = doSomethingForExtInfo(cm, dtimeOid, request);
		request.setAttribute("courseDetail", bean);
		Clazz clazz = getUserCredential(request.getSession(false))
				.getStudentClass();
		DepartmentInfo di = cm.findDepartmentInfoByCategory(clazz.getDeptNo());
		request.setAttribute("departmentInfo", di);
		request.setAttribute("actionName", "/Student/OnlineAddDelCourse");
		setContentPage(request.getSession(false), "student/ViewCourseIntro.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
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

		processReadCourseSyllabus(request);
		request.setAttribute("actionName", "/Student/OnlineAddDelCourse");
		setContentPage(request.getSession(false),
				"student/TeachSyllabusInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 取得建議選課清單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward suggest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		Student student = credential.getStudent();
		String departClass = student.getDepartClass();
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		Dtime dtime = new Dtime(departClass, term);
		Csno csno = null;
		List<Object> os = cm.findDtimeCsnoBy(dtime, "cscode");
		List<DynaBean> ret = new LinkedList<DynaBean>();
		DynaBean db = null;
		for (Object o : os) {
			db = new LazyDynaBean();
			dtime = (Dtime) ((Object[]) o)[0];
			csno = (Csno) ((Object[]) o)[1];
			if (!"50000".equals(csno.getCscode())
					&& !"T0001".equals(csno.getCscode())
					&& !"T0002".equals(csno.getCscode())) {
				db.set("className", Toolket.getClassFullName(dtime
						.getDepartClass()));
				db.set("classNo", dtime.getDepartClass());
				db.set("csnoName", csno.getChiName());
				db.set("cscode", csno.getCscode());
				db.set("optName", Toolket.getCourseOpt(dtime.getOpt()));
				db.set("credit", dtime.getCredit().toString());
				db.set("hour", dtime.getThour().toString());
				ret.add(db);
			}
		}

		request.setAttribute(SUGGEST_COURSE, ret);
		request.setAttribute("actionName", "/Student/OnlineAddDelCourse");
		setContentPage(request.getSession(false), "student/StdSuggestList.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 查詢加退選歷程
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward addDelList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Student student = getUserCredential(session).getStudent();
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		request.getSession(false)
				.setAttribute(
						SELD_ADCD_LIST,
						doSomethingForList(cm.findStudentAdcdByStudentNo(
								student.getStudentNo(), am
										.findTermBy(PARAMETER_SCHOOL_TERM))));
		request.setAttribute("actionName", "/Student/OnlineAddDelCourse");
		setContentPage(request.getSession(false), "student/StdAdcdList.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 查詢加退選結果
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward addDelResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Student student = getUserCredential(session).getStudent();
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		request.getSession(false)
				.setAttribute(
						SELD_ADCD_LIST,
						doSomethingForResult(cm.findAdcdByStudentNo(student
								.getStudentNo(), am
								.findTermBy(PARAMETER_SCHOOL_TERM))));
		request.setAttribute("actionName", "/Student/OnlineAddDelCourse");
		setContentPage(request.getSession(false), "student/StdAdcdResult.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 開放選修查詢
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward chooseAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		// 3部制不允許跨選,不允許跨系
		Clazz clazz = getUserCredential(session).getStudentClass();
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String term = am.findTermBy(PARAMETER_SCHOOL_TERM);
		String grade = clazz.getGrade();
		// if ("1".equals(term)) // 新學年選課
		// grade = String.valueOf(Integer.parseInt(grade) + 1);

		List<Map> contents = processRoom(cm.findOpencsByCondition(clazz
				.getCampusNo(), clazz.getSchoolNo(), clazz.getDeptNo(), grade,
				clazz.getClassNo(), term));

		String schoolType = cm.findSchoolTypeByClassNo(credential.getStudent()
				.getDepartClass());
		session
				.setAttribute(
						"opencsInfo",
						doSomethingForDuplicate(doSomethingForCollect(doSomethingForOpencs(
								credential.getStudent(),
								contents,
								cm.findMinCountBySchoolNo(clazz.getSchoolNo()),
								schoolType,
								(session.getAttribute(HAS_LITERACY_SELD) == null ? Boolean.FALSE
										: (Boolean) session
												.getAttribute(HAS_LITERACY_SELD)),
								(session.getAttribute(HAS_ARMY_SELD) == null ? Boolean.FALSE
										: (Boolean) session
												.getAttribute(HAS_ARMY_SELD))))));
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
				"Course.setCourseName.serachComplete"));
		saveMessages(request, msg);
		setContentPage(request.getSession(false),
				"student/StudentOnlineAddCourse.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 開放選修加選
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward addOpencs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages error = new ActionMessages();
		UserCredential user = getUserCredential(request.getSession(false));
		Student student = user.getStudent();
		Clazz clazz = user.getStudentClass();
		String classFullName = Toolket.getClassFullName(clazz.getClassNo());
		List<Map> contents = getOpencsList(request);
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		String term = am.findTermBy(PARAMETER_SCHOOL_TERM);

		int minCount = cm.findMinCountBySchoolNo(getUserCredential(
				request.getSession(false)).getStudentClass().getSchoolNo());
		List selCourseList = cm.getSeldDataByStudentNo(student.getStudentNo(),
				term);
		// Map<String, List<OnlineAddRemoveCourseUpAction.SeldDataInfo>>
		// seldDataInfoList = processSeldCourse(
		// selCourseList, minCount, cm, student, term);
		// sumCreditHours(request, seldDataInfoList);
		Map<String, Object> seldDataInfoList = processSeldCourse(selCourseList,
				minCount, cm, student, term, clazz.getSchoolType());
		calCreditHours(request, seldDataInfoList);
		Float credits = (Float) request.getSession(false).getAttribute(
				"totalCredits");
		List<Seld> selds = new LinkedList<Seld>();
		// 取得學分數上下限
		Map<String, String> minMax = null;
		if (Toolket.isDelayClass(student.getDepartClass())
				|| "8".equals(student.getOccurStatus())
				|| classFullName.indexOf("延修") != StringUtils.INDEX_NOT_FOUND) {
			// 研修生無限制
			minMax = new HashMap<String, String>();
			minMax.put("min", "0");
			minMax.put("max", "1000");
		} else
			minMax = getCreditMinMax(clazz.getSchoolNo(), clazz.getGrade());

		int opt3Count = 0;
		String opt = null, cscode = null;
		Seld seld = null;
		List<String> chkCscode = new LinkedList<String>();
		for (Map map : contents) {
			seld = new Seld();
			seld.setDtimeOid((Integer) map.get("oid"));
			seld.setStudentNo(student.getStudentNo());

			// 避免當學期選了相同但不同時段科目
			cscode = (String) map.get("cscode");
			if (!chkCscode.contains(cscode))
				chkCscode.add(cscode);
			else {
				error
						.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"Course.errorN1", "加選科目時，所選科目["
										+ (String) map.get("chi_name")
										+ "]已重複，因此無法加選。"));
				saveErrors(request, error);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}

			opt = (String) map.get("opt");
			seld.setOpt(opt);
			// 避免同時加選2門以上通識課程
			if ("3".equals(opt))
				opt3Count++;

			seld.setCredit((Float) map.get("credit"));
			selds.add(seld);
			credits += (Float) map.get("credit");
			if (credits > Float.parseFloat(minMax.get("max"))) {
				error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "加選科目時，學分總數已超過上限(>"
								+ minMax.get("max") + ")，因此無法加選。"));
				saveErrors(request, error);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}
		}

		if (opt3Count > 1) {
			error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "不可同時加選2門或以上的通識課程，請重新加選，謝謝。"));
			saveErrors(request, error);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {
			for (Seld s : selds) {
				try {
					cm
							.txAddSelectedSeldForOneStudent(s, student, term,
									true, 3);
				} catch (SeldException se) {
					// log.error(se.getMessage(), se);
					error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"Course.errorN1", se.getMessage()));
					saveErrors(request, error);
					return mapping.findForward(IConstants.ACTION_MAIN_NAME);
				}
			}
		}

		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
				"Course.errorN1", "加選完成[" + selds.size() + "]門科目"));
		saveMessages(request, msg);
		return unspecified(mapping, form, request, response);
	}

	/**
	 * 返回主畫面
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
		map.put("course.onlineAddRemoveCourse.chooseAddCourse", "chooseAdd"); // 選擇加選鈕
		map.put("course.onlineAddRemoveCourse.deleteCourse", "delete"); // 退選
		map.put("course.onlineAddRemoveCourse.deleteCourseList", "addDelList"); // 學生加退選歷程
		map.put("course.onlineAddRemoveCourse.deleteCourseResult",
				"addDelResult"); // 學生加退選結果
		map.put("course.courseSearch.btn.opencsSearch", "opencsSearch"); // 開放選修課程查詢
		map.put("course.courseSearch.btn.addOpencs", "addOpencs"); // 開放選修課程加選
		map.put("readCourseIntro", "readCourseIntro"); // 中英文簡介
		map.put("readCourseSyllabus", "readCourseSyllabus"); // 教學大綱
		map.put("course.onlineAddRemoveCourse.suggest", "suggest"); // 建議選課清單
		map.put("Back", "back"); // 返回
		return map;
	}

	@SuppressWarnings("unchecked")
	protected List<OnlineAddRemoveCourseUpAction.SeldDataInfo> getSeldDataListByIndex(
			HttpServletRequest request) {

		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		int minCount = cm.findMinCountBySchoolNo(getUserCredential(
				request.getSession(false)).getStudentClass().getSchoolNo());
		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> aList = new ArrayList<OnlineAddRemoveCourseUpAction.SeldDataInfo>();
		HttpSession session = request.getSession(false);
		String index = Toolket.getSelectedIndexFromCookie(request,
				SELD_LIST_NAME2);
		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> seldDataList = (List<OnlineAddRemoveCourseUpAction.SeldDataInfo>) session
				.getAttribute(SELD_LIST_NAME2);
		int stuSelect = 0;
		if (seldDataList != null) {
			for (OnlineAddRemoveCourseUpAction.SeldDataInfo sdi : seldDataList) {
				if (Toolket.isValueInCookie(sdi.getSeldOid().toString(), index)) {
					// 必須即時計算目前選課人數,免的退選人數過少的課
					synchronized (sdi) {
						stuSelect = cm.countSelect((sdi.getDtimeOid())
								.toString());
						if (!"3".equals(sdi.getOpt()) && minCount < stuSelect)
							aList.add(sdi);
						else if (LITERACY_MIN_COUNT < stuSelect) // 通識下限限制
							aList.add(sdi);
					}
				}
			}
		}

		return aList;
	}

	protected Map<String, String> getCreditMinMax(String schoolNo, String grade) {

		Map<String, String> minMax = new HashMap<String, String>();
		if ("12".equals(schoolNo) || "A2".equals(schoolNo)) {
			// 日二專與日產攜二專
			minMax.put("min", "12");
			minMax.put("max", "28");
		} else if ("22".equals(schoolNo) || "B2".equals(schoolNo)) {
			// 夜二專與夜產攜二專
			minMax.put("min", "9");
			minMax.put("max", "28");
		} else if ("64".equals(schoolNo) || "A4".equals(schoolNo)) {
			// 日四技與日產攜四技
			// 97.2改為只有1,2年級(原本有3年級)
			if ("1".equals(grade) || "2".equals(grade))
				minMax.put("min", "16");
			else
				minMax.put("min", "9");
			minMax.put("max", "22");
		} else if ("54".equals(schoolNo) || "B4".equals(schoolNo)) {
			// 夜四技與夜產攜四技
			minMax.put("min", "9");
			minMax.put("max", "22");
		} else if ("42".equals(schoolNo) || "C2".equals(schoolNo)
				|| "E2".equals(schoolNo)) {
			// 日二技,日產業二技與日產業二技春
			if ("1".equals(grade))
				minMax.put("min", "16");
			else
				minMax.put("min", "9");
			minMax.put("max", "22");
		} else if ("52".equals(schoolNo) || "D2".equals(schoolNo)
				|| "F2".equals(schoolNo)) {
			// 夜二技,夜產業二技與夜產業二技春
			minMax.put("min", "9");
			minMax.put("max", "22");
		} else if ("1G".equals(schoolNo) || "8G".equals(schoolNo)
				|| "CG".equals(schoolNo) || "DG".equals(schoolNo)
				|| "FG".equals(schoolNo) || "HG".equals(schoolNo)) {
			// 研究生
			minMax.put("min", "3");
			// 99.09.10日間部洪小姐說學分無上限
			minMax.put("max", "1000");
		} else if ("82".equals(schoolNo)) {
			// 二技在職專班(進修部)
			minMax.put("min", "9");
			minMax.put("max", "22");
		} else if ("92".equals(schoolNo)) {
			// 二專在職專班(進修部)
			minMax.put("min", "9");
			minMax.put("max", "28");
		} else if ("32".equals(schoolNo) || "72".equals(schoolNo)) {
			// 進修專校與學院
			minMax.put("min", "9");
			minMax.put("max", "22");
		}

		return minMax;
	}

	protected boolean isDateValide(int phase) {
		Date now = new Date();
		if (1 == phase) {
			return now.after(Parameters.PHASE_1_BEGIN.getTime())
					&& now.before(Parameters.PHASE_1_DEADLINE.getTime());
		} else if (2 == phase) {
			return now.after(Parameters.PHASE_2_BEGIN.getTime())
					&& now.before(Parameters.PHASE_2_DEADLINE.getTime());
		} else if (3 == phase) {
			return now.after(Parameters.PHASE_3_BEGIN.getTime())
					&& now.before(Parameters.PHASE_3_DEADLINE.getTime());
		}

		return false;
	}

	protected boolean isDateValide(String schoolType, int phase) {
		Date now = new Date();
		if (1 == phase) {
			// 第一階段日間部
			boolean bDay1 = now.after(Parameters.DAY_PHASE_1_BEGIN.getTime())
					&& now.before(Parameters.DAY_PHASE_1_DEADLINE.getTime());
			// 第一階段進修推廣部
			boolean bNight1 = now.after(Parameters.NIGHT_PHASE_1_BEGIN
					.getTime())
					&& now.before(Parameters.NIGHT_PHASE_1_DEADLINE.getTime());
			// 第一階段新竹日夜間部
			boolean hDay1 = now.after(Parameters.HSIN_CHU_PHASE_1_BEGIN
					.getTime())
					&& now.before(Parameters.HSIN_CHU_PHASE_1_DEADLINE
							.getTime());
			// 第一階段學院
			boolean hoDay1 = now.after(Parameters.HOLIDAY_PHASE_1_BEGIN
					.getTime())
					&& now
							.before(Parameters.HOLIDAY_PHASE_1_DEADLINE
									.getTime());
			switch (schoolType.charAt(0)) {
				case 'D':
					return bDay1;
				case 'N':
					return bNight1;
				case '2':
					return hDay1;
				case 'H':
					return hoDay1;
			}

		} else if (2 == phase) {
			// 第二階段日間部
			boolean bDay2 = now.after(Parameters.DAY_PHASE_2_BEGIN.getTime())
					&& now.before(Parameters.DAY_PHASE_2_DEADLINE.getTime());
			// 第二階段進修推廣部
			boolean bNight2 = now.after(Parameters.NIGHT_PHASE_2_BEGIN
					.getTime())
					&& now.before(Parameters.NIGHT_PHASE_2_DEADLINE.getTime());
			// 第二階段新竹日夜間部
			boolean hDay2 = now.after(Parameters.HSIN_CHU_PHASE_2_BEGIN
					.getTime())
					&& now.before(Parameters.HSIN_CHU_PHASE_2_DEADLINE
							.getTime());
			// 第二階段學院
			boolean hoDay2 = now.after(Parameters.HOLIDAY_PHASE_2_BEGIN
					.getTime())
					&& now
							.before(Parameters.HOLIDAY_PHASE_2_DEADLINE
									.getTime());
			switch (schoolType.charAt(0)) {
				case 'D':
					return bDay2;
				case 'N':
					return bNight2;
				case '2':
					return hDay2;
				case 'H':
					return hoDay2;
			}

		} else if (3 == phase) {
			// 第三階段
			boolean bDay3 = now.after(Parameters.DAY_PHASE_3_BEGIN.getTime())
					&& now.before(Parameters.DAY_PHASE_3_DEADLINE.getTime());
			boolean bNight3 = now.after(Parameters.NIGHT_PHASE_3_BEGIN
					.getTime())
					&& now.before(Parameters.NIGHT_PHASE_3_DEADLINE.getTime());
			boolean hDay3 = now.after(Parameters.HSIN_CHU_PHASE_3_BEGIN
					.getTime())
					&& now.before(Parameters.HSIN_CHU_PHASE_3_DEADLINE
							.getTime());
			// TODO 進修學院有第三階段選課嗎?
			boolean hoDay3 = false;
			switch (schoolType.charAt(0)) {
				case 'D':
					return bDay3;
				case 'N':
					return bNight3;
				case '2':
					return hDay3;
				case 'H':
					return hoDay3;
			}

		}

		return false;
	}

	// 使用Cookie將使用者所選課程由取出
	@SuppressWarnings("unchecked")
	private List<Map> getOpencsList(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		List<Integer> forCheck = new LinkedList<Integer>();
		boolean bFlag = true;
		String oids = Toolket.getSelectedIndexFromCookie(request,
				SELD_LIST_NAME2);
		List<Map> dtimes = (List<Map>) session.getAttribute("opencsInfo");
		List<Map> selDtimes = new ArrayList<Map>();

		if (dtimes != null) {
			// 防止多個相同Dtime Oid被選入
			for (Map dtime : dtimes) {
				Integer oid = (Integer) dtime.get("oid");
				if (bFlag) {
					bFlag = false;
					if (Toolket.isValueInCookie(dtime.get("oid").toString(),
							oids)) {
						selDtimes.add(dtime);
						forCheck.add(oid);
					}
				} else {
					if (!forCheck.contains(oid)) {
						if (Toolket.isValueInCookie(
								dtime.get("oid").toString(), oids)) {
							selDtimes.add(dtime);
							forCheck.add(oid);
						}
					}
				}
			}
		}
		return selDtimes;
	}

	private List<DynaBean> doSomethingForList(List<StdAdcd> stdAdcds) {
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		List<DynaBean> ret = new LinkedList<DynaBean>();
		for (StdAdcd stdAdcd : stdAdcds) {
			DynaBean db = new LazyDynaBean();
			Dtime dtime = (Dtime) cm.getDtimeBy(
					stdAdcd.getDtimeOid().toString()).get(0);
			db.set("courseName", cm.findCourseInfoByCscode(dtime.getCscode())
					.getChiName());
			db.set("className", Toolket.getClassFullName((dtime
					.getDepartClass())));
			db.set("optName", Toolket.getCourseOpt(dtime.getOpt()));
			db.set("lastModified", Global.TWDateFormat.format(stdAdcd
					.getLastModified()));
			db.set("adddraw", stdAdcd.getAdddraw().equals("A") ? "加選" : "退選");
			ret.add(db);
		}
		return ret;
	}

	private List<DynaBean> doSomethingForResult(List<Adcd> adcds) {
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		List<DynaBean> ret = new LinkedList<DynaBean>();
		for (Adcd stdAdcd : adcds) {
			DynaBean db = new LazyDynaBean();
			Dtime dtime = (Dtime) cm.getDtimeBy(
					stdAdcd.getDtimeOid().toString()).get(0);
			db.set("courseName", cm.findCourseInfoByCscode(dtime.getCscode())
					.getChiName());
			db.set("className", Toolket.getClassFullName((dtime
					.getDepartClass())));
			db.set("optName", Toolket.getCourseOpt(dtime.getOpt()));
			db.set("adddraw", stdAdcd.getAdddraw().equals("A") ? "加選" : "退選");
			ret.add(db);
		}
		return ret;
	}

	// 處理選修人數上限....等擋修問題
	@SuppressWarnings("unchecked")
	protected List<Map> doSomethingForOpencs(Student student,
			List<Map> contents, int minCount, String schoolType,
			boolean hasLiteracySeld, boolean hasArmySeld) {

		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		Map<String, Integer> nextYearTerm = Toolket.getNextYearTerm();
		List<Map> ret = new LinkedList<Map>();
		List<CourseIntroduction> cis = null;
		CourseIntroduction ci = null;
		ScoreHist scoreHist = new ScoreHist(student.getStudentNo());
		List<ScoreHist> scoreHistList = sm.findScoreHistBy(scoreHist);
		String[] cscodeHist = new String[0];
		String[] cscodeCurrent = new String[0];
		Float[] scoreList = new Float[0];
		for (ScoreHist hist : scoreHistList) {
			cscodeHist = (String[]) ArrayUtils
					.add(cscodeHist, hist.getCscode());
			// 抵免分數為null
			scoreList = (Float[]) ArrayUtils.add(scoreList, hist == null
					|| hist.getScore() == null ? 0.0F : hist.getScore());
		}

		String term = nextYearTerm.get(IConstants.PARAMETER_SCHOOL_TERM) == 1 ? "2"
				: "1";
		// 避免本學期還在修課,但下學期又選
		List<Map> seldInfo = cm.findStudentSeldCourse(student.getStudentNo(),
				term);
		for (Map map : seldInfo) {
			cscodeCurrent = (String[]) ArrayUtils.add(cscodeCurrent,
					(String) map.get("cscode"));
		}

		float passScore = Toolket.getPassScoreByDepartClass(student
				.getDepartClass());

		int seldCount = 0, ind = 0, startIndex = 0, selLimit = 0;
		Integer stuSel = 0;
		boolean is64 = false, isHist = false;
		String cscode = null, opt = null, departClass = null, literacyType = null;

		for (Map map : contents) {
			seldCount = cm.findSeldCountByDtimeOid((Integer) map.get("oid"));
			map.put("stu_select", Integer.valueOf(seldCount));
			opt = (String) map.get("opt");
			map.put("opt2", Toolket.getCourseOpt(opt));
			map.put("elearning", Toolket.getElearning(((String) map
					.get("elearning")).charAt(0)));
			map.put("time2", getClassTimeInfo((Integer) map.get("week"),
					(String) map.get("begin"), (String) map.get("end")));
			departClass = (String) map.get("depart_class");
			is64 = "64".equals(StringUtils.substring(departClass, 1, 3));
			cscode = (String) map.get("cscode");
			literacyType = map.get("literacyType") == null ? "" : (String) map
					.get("literacyType");
			map.put("literacyTypeName", StringUtils.isBlank(literacyType) ? ""
					: Toolket.getLiteracyType(literacyType));

			// 新規定:未輸入科目英文名稱之選修課,學生不得加選(但體育與軍訓不列入在內)
			if ("2".equals((String) map.get("opt"))
					&& !ArrayUtils.contains(IConstants.COURSE_ARMY, cscode)
					&& StringUtils.indexOf((String) map.get("chi_name"), "體育") == StringUtils.INDEX_NOT_FOUND) {
				if (StringUtils.isBlank(((String) map.get("eng_name")))) {
					// 第二階段不能用nextYearTerm,但目前不改,等有發生案例再說
					cis = cm.getCourseIntroByDtimeOid((Integer) map.get("oid"),
							nextYearTerm.get(IConstants.PARAMETER_SCHOOL_YEAR),
							nextYearTerm.get(IConstants.PARAMETER_SCHOOL_TERM));
					if (!cis.isEmpty()) {
						ci = cis.get(0);
						if (StringUtils.isBlank(ci.getEngName())) {
							map.put("canChoose", "1");
							map.put("errorMessage", "該科目之英文科目名稱未輸入,所以不得加選...");
							ret.add(map);
							continue;
						}
					}
				}
			}

			ind = 0;
			startIndex = 0;
			do {
				ind = ArrayUtils.indexOf(cscodeHist, cscode, startIndex);
				startIndex = ind + 1;
				// 選過且及格
				isHist = ind != StringUtils.INDEX_NOT_FOUND
						&& scoreList[ind] >= passScore;
			} while (!isHist && ind != StringUtils.INDEX_NOT_FOUND);

			if (isHist) {
				map.put("canChoose", "1");
				map.put("errorMessage", "該科目被認定為重複選修已修習及格之課程,所以不得加選...");
				// ret.add(map);
			} else if (ArrayUtils.indexOf(cscodeCurrent, cscode) != StringUtils.INDEX_NOT_FOUND) {
				map.put("canChoose", "1");
				map.put("errorMessage",
						"該科目被認定為本學期正在選修之課程,所以不得加選...如有必要,請於新學期選課開始時辦理人工加退選.");
				ret.add(map);
			} else {
				selLimit = ((Integer) map.get("Select_Limit")).intValue();
				if (0 == selLimit) {

					// 第二三階段才會有此判斷,行政單位會將人數保留後刪除,
					// 並設定選課上限為0
					if (IConstants.DAY.equals(schoolType)
							&& ArrayUtils.contains(
									IConstants.COURSE_SPORT_DENIED, cscode)) {
						map.put("canChoose", "1");
						map.put("errorMessage",
								"本課程需先至體育室繳交校外場地費及保險費，再憑繳費收據，至課務組選課，方為選課成功。");
						ret.add(map);
					} else {
						// 併班會導致選課人數上限為0
						map.put("canChoose", "1");
						map.put("errorMessage", "該科目選課人數上限設定為0,所以不得加選...");
						ret.add(map);
					}
				} else {
					boolean bFlag = false, bFlag1 = false, bFlag2 = false, bFlag3 = false;
					// 日4技的通識自97.1開始會將課程開至星期四的5~6與7~8
					// 所以要限制學生只可以選其中一個時段
					if (IConstants.DAY.equals(schoolType) && is64
							&& "3".equals(opt) && hasLiteracySeld)
						bFlag1 = true;

					// 部份軍訓科目不可同時選
					if ((ArrayUtils.contains(IConstants.COURSE_ARMY_DENIED_1,
							cscode) || ArrayUtils.contains(
							IConstants.COURSE_ARMY_DENIED_2, cscode))
							&& hasArmySeld) {
						bFlag2 = true;
					}

					// 第二三階段才會有此判斷,行政單位會將人數保留後刪除,
					// 並設定選課上限為0
					if (IConstants.DAY.equals(schoolType)
							&& ArrayUtils.contains(
									IConstants.COURSE_SPORT_DENIED, cscode))
						bFlag3 = true;

					stuSel = map.get("stu_select") == null ? 0 : (Integer) map
							.get("stu_select");
					bFlag = stuSel.intValue() >= selLimit;
					if (bFlag) {
						map.put("canChoose", "1");
						map.put("errorMessage", "已達該科目選課人數上限 (>="
								+ (Integer) map.get("Select_Limit") + ")");
					} else if (bFlag1) {
						map.put("canChoose", "1");
						map.put("errorMessage", "通識課程不可同時加選2門(含)以上...");
					} else if (bFlag2) {
						map.put("canChoose", "1");
						map.put("errorMessage", "部分選修軍訓及通識軍訓科目不可同時選...");
					} else if (bFlag3) {
						map.put("canChoose", "1");
						map.put("errorMessage",
								"本課程需先至體育室繳交校外場地費及保險費，再憑繳費收據，至課務組選課，方為選課成功。");
					} else {
						map.put("canChoose", "0");
						map.put("errorMessage", "");
					}

					ret.add(map);
				}
			}
		}

		return ret;
	}

	// 將相同Dtime Oid集中,將上課時間作處理
	@SuppressWarnings("unchecked")
	protected List<Map> doSomethingForCollect(List<Map> list) {
		List<Map> ret = new LinkedList<Map>();
		Map map = null;
		if (list != null && !list.isEmpty()) {
			Map temp = list.get(0);
			ret.add(temp);
			for (int i = 1; i < list.size(); i++) {
				map = list.get(i);
				if (temp.get("oid").toString()
						.equals(map.get("oid").toString())) {
					temp.put("time2", temp.get("time2") + "，"
							+ map.get("time2"));
					ret.add(temp);
				} else {
					ret.add(map);
					temp = map;
				}
			}
		}
		return ret;
	}

	// 排除多餘Dtime Oid之清單
	@SuppressWarnings("unchecked")
	protected List<Map> doSomethingForDuplicate(List<Map> list) {
		List<Map> ret = new LinkedList<Map>();
		List<Integer> forCheck = new LinkedList<Integer>();
		Map map = null;
		if (list != null && !list.isEmpty()) {
			forCheck.add((Integer) list.get(0).get("oid"));
			ret.add(list.get(0));
			for (int i = 1; i < list.size(); i++) {
				map = list.get(i);
				if (!forCheck.contains((Integer) map.get("oid"))) {
					ret.add(map);
					forCheck.add((Integer) map.get("oid"));
				}
			}
		}
		return ret;
	}

	private DynaBean doSomethingForExtInfo(CourseManager cm, Integer dtimeOid,
			HttpServletRequest request) {
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		// 第3階段Year與Term不需轉換
		Integer year = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
		Integer term = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_TERM));
		DynaBean ret = new LazyDynaBean();
		Dtime dtime = cm.findDtimeBy(dtimeOid);
		String schoolType = cm.findSchoolTypeByClassNo(dtime.getDepartClass());
		Csno csno = cm.findCourseInfoByCscode(dtime.getCscode());
		Member member = null;
		if (dtime != null && StringUtils.isNotBlank(dtime.getTechid()))
			member = mm.findMemberByAccount(dtime.getTechid());

		List<CourseIntroduction> cis = cm.getCourseIntroByDtimeOid(dtimeOid,
				year, term);
		CourseIntroduction ci = cis.isEmpty() ? new CourseIntroduction() : cis
				.get(0);
		request.setAttribute("chiCourse", csno.getChiName());
		request.setAttribute("engCourse", StringUtils.isNotBlank(csno
				.getEngName()) ? csno.getEngName() : "&nbsp;");
		request.setAttribute("gradeChi", getFormalGrade(dtime.getOpt()));
		request.setAttribute("gradeEng", getEngFormalGrade(dtime.getOpt()));
		request.setAttribute("credit", dtime.getCredit());
		request.setAttribute("thour", dtime.getThour());
		request.setAttribute("teacherChiName", member == null ? "&nbsp;"
				: member.getName());
		request.setAttribute("teacherEngName", "&nbsp;");
		request.setAttribute("chiIntro", ci.getChiIntro());
		request.setAttribute("engIntro", ci.getEngIntro());
		request.setAttribute("chiDivision", getForamlDivition(schoolType));
		request.setAttribute("engDivision", getEngFormalDivision(schoolType));
		request.setAttribute("chiProgram", getFormalProgram(dtime
				.getDepartClass().substring(1, 3)));
		request.setAttribute("engProgram", getEngFormalProgram(dtime
				.getDepartClass().substring(1, 3)));
		return ret;
	}

	protected void processReadCourseSyllabus(HttpServletRequest request) {
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		Integer year = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
		Integer term = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_TERM));
		Integer dtimeOid = Integer.valueOf(request.getParameter("oid"));
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		CourseSyllabus cs = cm
				.getCourseSyllabusByDtimeOid(dtimeOid, year, term);
		if (cs != null) {
			cs = cm.findCourseSyllabusByOid(cs.getOid());
			request.setAttribute("courseSyllabusInfo", cs);
			request.setAttribute("courseSyllabusList", cs.getSyllabuses());
			// 判斷需要多少Syllabus輸入欄位
			request.setAttribute("counts", 18 - (cs.getSyllabuses() == null ? 0
					: cs.getSyllabuses().size()));
		}

		Dtime d = (Dtime) cm.getDtimeBy(dtimeOid.toString()).get(0);
		request.setAttribute("csnoInfo", cm.findCourseInfoByCscode(d
				.getCscode()));
	}

	// 將Credential物件內學生Student物件資料填入至Form物件內
	protected DynaActionForm setFormProps(DynaActionForm form,
			UserCredential credential) {
		Student student = credential.getStudent();
		Clazz clazz = credential.getStudentClass();
		form.set("name", student.getStudentName());
		form.set("stdNo", student.getStudentNo());
		form.set("idNo", student.getIdno());
		if (clazz != null) {
			form.set("campusNo", clazz.getCampusNo());
			form.set("schoolNo", clazz.getSchoolNo());
			form.set("deptNo", clazz.getDeptNo());
			form.set("classNo", clazz.getClassNo());
			form.set("deptClassName", student.getDepartClass2());
		}
		return form;
	}

	// 將DtimeClass資料表內Week, Begin與End轉為有意義訊息
	public static String getClassTimeInfo(Integer week, String begin, String end) {
		StringBuffer timeInfo = new StringBuffer("星期");
		if (week == null) {
			return "";
		} else {
			switch (week.intValue()) {
				case 1:
					timeInfo.append("一 : ");
					break;
				case 2:
					timeInfo.append("二 : ");
					break;
				case 3:
					timeInfo.append("三 : ");
					break;
				case 4:
					timeInfo.append("四 : ");
					break;
				case 5:
					timeInfo.append("五 : ");
					break;
				case 6:
					timeInfo.append("六 : ");
					break;
				case 7:
					timeInfo.append("日 : ");
					break;
			}
			timeInfo.append(getNodeString(begin)).append(" ~ ").append(
					getNodeString(end));
			return timeInfo.toString();
		}
	}

	protected static String getNodeString(String node) {
		if (node.equals("011"))
			return "夜1";
		else if (node.equals("012"))
			return "夜2";
		else if (node.equals("013"))
			return "夜3";
		else if (node.equals("014"))
			return "夜4";
		else
			return node;
	}

	protected static String getFormalGrade(String grade) {
		switch (Integer.parseInt(grade)) {
			case 1:
				return "一";
			case 2:
				return "二";
			case 3:
				return "三";
			case 4:
				return "四";
			case 5:
				return "五";
			default:
				return "未定義";
		}
	}

	protected static String getEngFormalGrade(String grade) {
		switch (Integer.parseInt(grade)) {
			case 1:
				return "Grade 1";
			case 2:
				return "Grade 2";
			case 3:
				return "Grade 3";
			case 4:
				return "Grade 4";
			case 5:
				return "Grade 5";
			default:
				return "未定義";
		}
	}

	protected static String getForamlDivition(String schoolType) {
		String ret = "";
		if ("D".equals(schoolType))
			ret = "日間部";
		else if ("N".equals(schoolType))
			ret = "夜間部";
		else
			ret = "進修學院/專校";
		return ret;
	}

	protected static String getEngFormalDivision(String schoolType) {
		String ret = "";
		if ("D".equals(schoolType))
			ret = "Day Division";
		else if ("N".equals(schoolType))
			ret = "Continuing Educatuinb Division";
		else
			ret = "Affiliated Institute/Junior College of Continuing Education";
		return ret;
	}

	protected static String getFormalProgram(String schoolNo) {
		String ret = "";
		if ("54".equals(schoolNo) || "64".equals(schoolNo))
			ret = "四技";
		else if ("42".equals(schoolNo) || "52".equals(schoolNo)
				|| "82".equals(schoolNo))
			ret = "二技";
		else if ("12".equals(schoolNo) || "22".equals(schoolNo)
				|| "92".equals(schoolNo))
			ret = "二專";
		else
			ret = "研究所";
		return ret;
	}

	protected static String getEngFormalProgram(String schoolNo) {
		String ret = "";
		if ("54".equals(schoolNo) || "64".equals(schoolNo))
			ret = "4-Year College";
		else if ("42".equals(schoolNo) || "52".equals(schoolNo)
				|| "82".equals(schoolNo))
			ret = "2-Year Senior College";
		else if ("12".equals(schoolNo) || "22".equals(schoolNo)
				|| "92".equals(schoolNo))
			ret = "2-Year Junior College";
		else
			ret = "Graduate School";
		return ret;
	}

	protected void clearData(HttpSession session, HttpServletResponse response) {
		session.removeAttribute(SELD_LIST_NAME1);
		session.removeAttribute(SELD_LIST_NAME2);
		Toolket.resetCheckboxCookie(response, OPENCS_COOKIE_NAME);
		Toolket.resetCheckboxCookie(response, SELD_LIST_NAME2);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> processSeldCourse(List<Map> seldList,
			int minCount, CourseManager cm, Student student, String term,
			String schoolType) {

		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> resultOpt1 = new ArrayList<OnlineAddRemoveCourseUpAction.SeldDataInfo>();
		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> resultOpt2 = new ArrayList<OnlineAddRemoveCourseUpAction.SeldDataInfo>();
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put(HAS_LITERACY_SELD, Boolean.FALSE); // 預設是沒有加選通識課程
		ret.put(HAS_ARMY_SELD, Boolean.FALSE); // 

		Map obj = null, oo = null;
		String cscode = null, stuSelect = null, time2 = "";
		String departClass = null, courseClass = null, opt = null;
		boolean bFlag = false, bFlag1 = false, isNeedCheck = true;
		boolean isNeedCheck1 = true, isExcludedCourse = false;
		String[] armyCourseCheck = null; // 98.1新規定:部份軍訓不可重複
		List<Adcd> adcds = null;

		if ("1".equals(term))
			armyCourseCheck = IConstants.COURSE_ARMY_DENIED_1;
		else
			armyCourseCheck = IConstants.COURSE_ARMY_DENIED_2;

		if (!seldList.isEmpty()) {

			OnlineAddRemoveCourseUpAction.SeldDataInfo content = null;
			for (int i = 0; i < seldList.size(); i++) {
				content = new OnlineAddRemoveCourseUpAction.SeldDataInfo();
				obj = (Map) seldList.get(i);
				courseClass = (String) obj.get("classNo");
				opt = (String) obj.get("opt");
				// isLiteracyClass = Toolket.isLiteracyClass(courseClass);
				// 判斷是否已有加選通識課程?
				// 98.9.11改成以選別判斷
				if ("3".equals(opt) && isNeedCheck) {
					ret.put(HAS_LITERACY_SELD, Boolean.TRUE);
					isNeedCheck = false;
				}
				content.setClassNo(courseClass);
				content.setClassName((String) obj.get("className"));
				content.setElearning(Toolket.getElearning(((String) obj
						.get("elearning")).charAt(0)));
				cscode = (String) obj.get("cscode");
				isExcludedCourse = ArrayUtils.contains(
						IConstants.ENGLISH_EXCLUDED_COURSES, cscode);

				// 檢查指定軍訓課程是否有選?
				if (isNeedCheck1
						&& ArrayUtils.contains(armyCourseCheck, cscode)) {
					ret.put(HAS_ARMY_SELD, Boolean.TRUE);
					isNeedCheck1 = false;
				}

				content.setCsCode(cscode);
				content.setCsName((String) obj.get("chi_Name"));
				// 即時計算選課人數
				stuSelect = String.valueOf(cm.countSelect(((Integer) obj
						.get("oid")).toString()));
				content.setStuSelect(stuSelect);
				content.setSelectLimit(((Integer) obj.get("select_Limit"))
						.toString());
				content.setHour(Short.valueOf(((Integer) obj.get("thour"))
						.toString()));
				content.setCredit((Float) obj.get("credit"));
				content.setSeldOid((Integer) obj.get("soid")); // Seld Oid
				content.setOpt(opt);
				content.setOptName(Toolket.getCourseOpt(opt));
				content.setElearningName(Toolket.getElearning(((String) obj
						.get("elearning")).charAt(0)));
				content.setDtimeOid((Integer) obj.get("oid"));
				content.setLiteracyType(obj.get("literacyType") == null ? null
						: (String) obj.get("literacyType"));
				content
						.setLiteracyTypeName(obj.get("literacyType") == null ? ""
								: Toolket.getLiteracyType((String) obj
										.get("literacyType")));

				// 將相同課程但不同上課時間做整理
				if (i < seldList.size() - 1) {
					bFlag = false;
					bFlag1 = false; // 是否需要處理開課分為3個時段課程?
					time2 = "";
					oo = (Map) seldList.get(i + 1);
					if (((Integer) oo.get("oid")).toString().equals(
							((Integer) obj.get("oid")).toString())) {
						bFlag = true;
						time2 = getClassTimeInfo((Integer) obj.get("week"),
								(String) obj.get("begin"), (String) obj
										.get("end"))
								+ "，"
								+ getClassTimeInfo((Integer) oo.get("week"),
										(String) oo.get("begin"), (String) oo
												.get("end"));
						content.setTime2(time2);
						i++;
					} else
						content.setTime2(getClassTimeInfo((Integer) obj
								.get("week"), (String) obj.get("begin"),
								(String) obj.get("end")));
					// 處理開課分為3個時段課程
					// 新竹四技航電三甲(264B31),飛機通訊與導航系統(TC400)
					// 學生9424B043, 9324B003
					if (bFlag) {
						if (i + 1 < seldList.size()) { // 避免超過index
							oo = (Map) seldList.get(i + 1);
							if (((Integer) oo.get("oid")).toString().equals(
									((Integer) obj.get("oid")).toString())) {
								bFlag1 = true;
								time2 += "，"
										+ getClassTimeInfo((Integer) oo
												.get("week"), (String) oo
												.get("begin"), (String) oo
												.get("end"));
								content.setTime2(time2);
								i++;
							} else
								content.setTime2(time2);
						}
					}

					// 處理第四個上課時段
					if (bFlag1) {
						if (i + 1 < seldList.size()) { // 避免超過index
							oo = (Map) seldList.get(i + 1);
							if (((Integer) oo.get("oid")).toString().equals(
									((Integer) obj.get("oid")).toString())) {
								time2 += "，"
										+ getClassTimeInfo((Integer) oo
												.get("week"), (String) oo
												.get("begin"), (String) oo
												.get("end"));
								content.setTime2(time2);
								i++;
							} else
								content.setTime2(time2);
						}
					}
				} else
					content.setTime2(getClassTimeInfo(
							(Integer) obj.get("week"), (String) obj
									.get("begin"), (String) obj.get("end")));

				departClass = student.getDepartClass();
				// if ("1".equals(term)) departClass =
				// Toolket.processClassNoUp(departClass);
				// 第三階段不需年級再+1
				adcds = cm.findExistedAdcd(student.getStudentNo(), departClass,
						((Integer) obj.get("oid")).toString(), "A");
				// 將選別分離,學生該班必修不可退選,加選須可以退
				if (!adcds.isEmpty() || "2".equals(opt)
						|| ArrayUtils.contains(IConstants.COURSE_SPORT, cscode)) {
					// 一年級所開的課不限制人數下限,否則高年級不能退選
					// 98.12.25日間部課務組張小姐說先關閉科目開在1年級無下限規定
					// if (minCount >= Integer.parseInt(stuSelect)
					// && !"1".equals(courseClass.substring(4, 5))) {
					if (minCount >= Integer.parseInt(stuSelect)) {
						content
								.setErrorMessage("已達選課人數下限 (<=" + minCount
										+ ")");
						content.setCanChoose("1");
					} else {
						content.setErrorMessage("");
						content.setCanChoose("0");
					}

					// 99.03.16語言中心彭組長提出,林組長與教務長同意部份英文不可退選
					// 只有第三階段做判斷,因為寒暑假才會能力分班
					// 只有日間部會有能力分班,進修推廣沒
					if (isExcludedCourse && "D".equals(schoolType))
						resultOpt1.add(content);
					else
						resultOpt2.add(content);
				} else if ("1".equals(opt)
						&& departClass.equalsIgnoreCase(courseClass)) {
					content.setErrorMessage("");
					content.setCanChoose("0");
					resultOpt1.add(content);
				} else if ("3".equals(opt)) {
					// 第三階段通識選課人數下限為20人
					minCount = LITERACY_MIN_COUNT;
					if (minCount >= Integer.parseInt(stuSelect)) {
						content
								.setErrorMessage("已達選課人數下限 (<=" + minCount
										+ ")");
						content.setCanChoose("1");
					} else {
						content.setErrorMessage("");
						content.setCanChoose("0");
					}

					// 99.03.16語言中心彭組長提出,林組長與教務長同意部份英文不可退選
					// 只有第三階段做判斷,因為寒暑假才會能力分班
					// 只有日間部會有能力分班,進修推廣沒
					if (isExcludedCourse && "D".equals(schoolType))
						resultOpt1.add(content);
					else
						resultOpt2.add(content);
				} else {
					content.setErrorMessage("");
					content.setCanChoose("0");
					resultOpt2.add(content);
				}
			}

		} else {
			// 查無資料則回傳空白List
			resultOpt1 = Collections.EMPTY_LIST;
			resultOpt2 = Collections.EMPTY_LIST;
		}

		// Map<String, List<OnlineAddRemoveCourseUpAction.SeldDataInfo>> ret =
		// new HashMap<String,
		// List<OnlineAddRemoveCourseUpAction.SeldDataInfo>>();
		ret.put(SELD_LIST_NAME1, resultOpt1);
		ret.put(SELD_LIST_NAME2, resultOpt2);
		return ret;
	}

	@SuppressWarnings("unchecked")
	protected Map<String, List<OnlineAddRemoveCourseUpAction.SeldDataInfo>> processSeldCourse(
			List seldList, int minCount, CourseManager cm, Student student) {

		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> resultOpt1 = new ArrayList<OnlineAddRemoveCourseUpAction.SeldDataInfo>();
		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> resultOpt2 = new ArrayList<OnlineAddRemoveCourseUpAction.SeldDataInfo>();
		if (!seldList.isEmpty()) {

			OnlineAddRemoveCourseUpAction.SeldDataInfo content = null;
			for (Object o : seldList) {
				content = new OnlineAddRemoveCourseUpAction.SeldDataInfo();
				// Object[] obj = (Object[]) o;
				ListOrderedMap map = (ListOrderedMap) o;
				// content.setClassNo((String) obj[0]);
				// content.setClassName((String) obj[1]);
				// content.setCsCode((String) obj[2]);
				// content.setCsName((String) obj[3]);
				content.setClassNo((String) map.get("classNo"));
				content.setClassName((String) map.get("className"));
				content.setCsCode((String) map.get("cscode"));
				content.setCsName((String) map.get("chi_Name"));
				// 即時計算選課人數
				// String stuSelect = String.valueOf(cm
				// .countSelect(((Integer) obj[10]).toString()));
				String stuSelect = String.valueOf(cm.countSelect(((Integer) map
						.get("oid")).toString()));
				content.setStuSelect(stuSelect);
				// content.setSelectLimit(((Short) obj[5]).toString());
				content.setSelectLimit(((Integer) map.get("select_Limit"))
						.toString());
				// content.setHour((Short) obj[6]);
				// content.setCredit((Float) obj[7]);
				// content.setOid((Integer) obj[8]); // Seld Oid
				// content.setOpt((String) obj[9]);
				content.setHour(Short.valueOf(((Integer) map.get("thour"))
						.toString()));
				content.setCredit((Float) map.get("credit"));
				content.setSeldOid((Integer) map.get("soid")); // Seld Oid
				content.setOpt((String) map.get("opt"));
				content.setOptName(Toolket
						.getCourseOpt((String) map.get("opt")));
				content.setElearningName(Toolket.getElearning(((String) map
						.get("elearning")).charAt(0)));
				content.setDtimeOid((Integer) map.get("oid"));
				List<Adcd> adcds = cm.findExistedAdcd(student.getStudentNo(),
						student.getDepartClass(), ((Integer) map.get("oid"))
								.toString(), "A");
				// TODO 請修改PhaseOneAddDelCourseAction.processSeldCourse()
				// 將選別分離,必修不可退選,加選須可以退
				if (!adcds.isEmpty() || "2".equals((String) map.get("opt"))) {
					if (minCount >= Integer.parseInt(stuSelect)) {
						content.setErrorMessage("已達該科目選課人數下限 (<=" + minCount
								+ ")");
						content.setCanChoose("1");
					} else {
						content.setErrorMessage("");
						content.setCanChoose("0");
					}
					resultOpt2.add(content);
				} else if ("1".equals((String) map.get("opt"))) {
					content.setErrorMessage("");
					content.setCanChoose("0");
					resultOpt1.add(content);
				} else if ("3".equals((String) map.get("opt"))) {
					minCount = IConstants.LITERACY_CLASS; // 通識選課人數下限為20人
					if (minCount >= Integer.parseInt(stuSelect)) {
						content.setErrorMessage("已達該科目選課人數下限(<=" + minCount
								+ ")");
						content.setCanChoose("1");
					} else {
						content.setErrorMessage("");
						content.setCanChoose("0");
					}
					resultOpt2.add(content);
				}
			}

		} else {
			// 查無資料則回傳空白List
			resultOpt1 = Collections.EMPTY_LIST;
			resultOpt2 = Collections.EMPTY_LIST;
		}

		Map<String, List<OnlineAddRemoveCourseUpAction.SeldDataInfo>> ret = new HashMap<String, List<OnlineAddRemoveCourseUpAction.SeldDataInfo>>();
		ret.put(SELD_LIST_NAME1, resultOpt1);
		ret.put(SELD_LIST_NAME2, resultOpt2);
		return ret;
	}

	@SuppressWarnings("unchecked")
	private void calCreditHours(HttpServletRequest request,
			Map<String, Object> selCourseList) {
		float credits = 0.0f;
		short hours = (short) 0;
		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> resultOpt1 = (List<OnlineAddRemoveCourseUpAction.SeldDataInfo>) selCourseList
				.get(SELD_LIST_NAME1);
		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> resultOpt2 = (List<OnlineAddRemoveCourseUpAction.SeldDataInfo>) selCourseList
				.get(SELD_LIST_NAME2);
		for (OnlineAddRemoveCourseUpAction.SeldDataInfo sdi : resultOpt1) {
			credits += sdi.getCredit();
			hours += sdi.getHour();
		}
		for (OnlineAddRemoveCourseUpAction.SeldDataInfo sdi : resultOpt2) {
			credits += sdi.getCredit();
			hours += sdi.getHour();
		}

		request.setAttribute("totalCourses", resultOpt1.size()
				+ resultOpt2.size());
		request.getSession(false).setAttribute("totalCredits", credits);
		request.setAttribute("totalHours", hours);
	}

	protected void sumCreditHours(
			HttpServletRequest request,
			Map<String, List<OnlineAddRemoveCourseUpAction.SeldDataInfo>> selCourseList) {
		float credits = 0.0f;
		short hours = (short) 0;
		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> resultOpt1 = selCourseList
				.get(SELD_LIST_NAME1);
		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> resultOpt2 = selCourseList
				.get(SELD_LIST_NAME2);
		for (OnlineAddRemoveCourseUpAction.SeldDataInfo sdi : resultOpt1) {
			credits += sdi.getCredit();
			hours += sdi.getHour();
		}
		for (OnlineAddRemoveCourseUpAction.SeldDataInfo sdi : resultOpt2) {
			credits += sdi.getCredit();
			hours += sdi.getHour();
		}

		request.setAttribute("totalCourses", resultOpt1.size()
				+ resultOpt2.size());
		request.getSession(false).setAttribute("totalCredits", credits);
		request.setAttribute("totalHours", hours);
	}

	@SuppressWarnings("unchecked")
	protected List<Map> processRoom(List<Map> contents) {
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		List<Map> ret = new LinkedList<Map>();
		String roomId = null, building = null;
		Nabbr nabbr = null;
		Code5 code5 = null;

		try {
			for (Map c : contents) {
				roomId = (String) c.get("room_id");
				if (roomId != null) {
					nabbr = (Nabbr) cm.hqlGetBy(
							"FROM Nabbr WHERE roomId = '" + roomId + "'")
							.get(0);
					building = nabbr.getBuilding();
					code5 = Toolket.findBuildingBy(building);
					c.put("name2", code5 != null ? code5.getName() : "");
					ret.add(c);
				} else
					ret.add(c);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}

		return ret;
	}

	// 供前端清單顯示使用之JavaBean物件
	public static class OpencsInfo implements Serializable {

		private static final long serialVersionUID = -5301694151821606636L;

		private int index;
		private Integer oid;
		private String departClass;
		private String className;
		private String cscode;
		private String chiName;
		private String engName;
		private String techName;
		private String techEngName;
		private String classTime;
		private String opt;
		private String optName;
		private Float credit;
		private Short hour;
		private Short stdSelected;
		private Short stdLimit;
		private Integer dtimeOid;
		private String grade;
		private String engGrade;
		private String deptName;

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getEngName() {
			return engName;
		}

		public void setEngName(String engName) {
			this.engName = engName;
		}

		public String getTechEngName() {
			return techEngName;
		}

		public void setTechEngName(String techEngName) {
			this.techEngName = techEngName;
		}

		public Integer getOid() {
			return oid;
		}

		public void setOid(Integer oid) {
			this.oid = oid;
		}

		public String getChiName() {
			return chiName;
		}

		public void setChiName(String chiName) {
			this.chiName = chiName;
		}

		public Float getCredit() {
			return credit;
		}

		public void setCredit(Float credit) {
			this.credit = credit;
		}

		public String getCscode() {
			return cscode;
		}

		public void setCscode(String cscode) {
			this.cscode = cscode;
		}

		public String getDepartClass() {
			return departClass;
		}

		public void setDepartClass(String departClass) {
			this.departClass = departClass;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public Short getHour() {
			return hour;
		}

		public void setHour(Short hour) {
			this.hour = hour;
		}

		public String getOpt() {
			return opt;
		}

		public void setOpt(String opt) {
			this.opt = opt;
		}

		public String getOptName() {
			return optName;
		}

		public void setOptName(String optName) {
			this.optName = optName;
		}

		public Short getStdLimit() {
			return stdLimit;
		}

		public void setStdLimit(Short stdLimit) {
			this.stdLimit = stdLimit;
		}

		public Short getStdSelected() {
			return stdSelected;
		}

		public void setStdSelected(Short stdSelected) {
			this.stdSelected = stdSelected;
		}

		public String getTechName() {
			return techName;
		}

		public void setTechName(String techName) {
			this.techName = techName;
		}

		public String getClassTime() {
			return classTime;
		}

		public void setClassTime(String classTime) {
			this.classTime = classTime;
		}

		public Integer getDtimeOid() {
			return dtimeOid;
		}

		public void setDtimeOid(Integer dtimeOid) {
			this.dtimeOid = dtimeOid;
		}

		public String getGrade() {
			return grade;
		}

		public void setGrade(String grade) {
			this.grade = grade;
		}

		public String getEngGrade() {
			return engGrade;
		}

		public void setEngGrade(String engGrade) {
			this.engGrade = engGrade;
		}

		public String getDeptName() {
			return deptName;
		}

		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}

	}

}
