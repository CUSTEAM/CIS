package tw.edu.chit.struts.action.student;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_NEXT_TERM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
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
import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.CourseSyllabus;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DepartmentInfo;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.StdAdcd;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.impl.exception.SeldException;
import tw.edu.chit.struts.action.course.OnlineAddRemoveCourseUpAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class PhaseTwoAddDelCourseAction extends StudentOnlineAddDelCourseAction {

	public static final String SELD_LIST_NAME1 = "seldDataInfoOpt1"; // 不可退選
	public static final String SELD_LIST_NAME2 = "seldDataInfoOpt2"; // 可退選
	public static final String OPENCS_LIST_NAME = "opencsList";
	public static final String OPENCS_COOKIE_NAME = "opencsInfo";
	public static final String OPT_COUNT = "optCount";
	public static final String SELD_ADCD_LIST = "seldAdcdList";

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
		String departClass = credential.getStudent().getDepartClass();
		String schoolType = Toolket.isHsinChuStudent(departClass) ? "2" : cm
				.findSchoolTypeByClassNo(departClass);
		if (!isDateValide(schoolType, 2))
			throw new NullPointerException("請勿亂搞，謝謝。");

		clearData(session, response);
		ActionMessages errors = null;

		try {
			session.setAttribute("OnlineAddDelCourseForm", setFormProps(
					(DynaActionForm) form, credential));
			Clazz clazz = credential.getStudentClass();
			if (clazz != null) {
				// 跨選規則有改變,如果單以年級來看,如果使用者設定為3年級,則只有3與4年級可以選
				// 2年級以下不可選
				AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
				String nextTerm = am.findTermBy(PARAMETER_NEXT_TERM);
				// 第二階段有人數上下限限制
				// 選課人數下限, 專科*25, 大學*20, 研究所*5, 通識*20
				int selectedStudentMinCounts = cm.findMinCountBySchoolNo(clazz
						.getSchoolNo());
				Student student = credential.getStudent();
				List<Map> selCourseList = cm.getSeldDataByStudentNo(student
						.getStudentNo(), nextTerm);
				Map<String, Object> seldDataInfoList = processSeldCourse(
						selCourseList, selectedStudentMinCounts, cm, student,
						nextTerm);
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

		setContentPage(session, "student/PhaseTwoAddDelCourse.jsp");
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
			float minus = 0.0f;
			CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
			AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
			String term = am.findTermBy(PARAMETER_NEXT_TERM);
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
			// 第二階段有學分數上下限限制
			// 取得學分數上下限
			String grade = clazz.getGrade();
			String departClass = student.getDepartClass();
			if ("1".equals(term)) {
				grade = String.valueOf(Integer.parseInt(grade) + 1);
				departClass = Toolket.processClassNoUp(departClass);
			}
			Map<String, String> minMax = null;
			if ("8".equals(student.getOccurStatus())
					|| Toolket.isDelayClass(departClass)) {
				// 研修生無限制
				minMax = new HashMap<String, String>();
				minMax.put("min", "0");
				minMax.put("max", "1000");
			} else
				minMax = getCreditMinMax(clazz.getSchoolNo(), grade);

			float credits = (Float) request.getSession(false).getAttribute(
					"totalCredits");
			if ((credits - minus) < Float.parseFloat(minMax.get("min"))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "退選科目後之學分總數，已低於下限 (<"
								+ minMax.get("min") + ")，因此無法退選。"));
				saveErrors(request, messages);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}

			try {
				// true代表學生身分
				cm.txRemoveSelectedSeld(student.getStudentNo(), departClass, 2,
						inSyntax, true);
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
		DynaBean bean = doForExtInfo(cm, dtimeOid, request);
		request.setAttribute("courseDetail", bean);
		Clazz clazz = getUserCredential(request.getSession(false))
				.getStudentClass();
		DepartmentInfo di = cm.findDepartmentInfoByCategory(clazz.getDeptNo());
		request.setAttribute("departmentInfo", di);
		request.setAttribute("actionName", "/Student/PhaseTwoAddDelCourse");
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
		request.setAttribute("actionName", "/Student/PhaseTwoAddDelCourse");
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
		Student student = getUserCredential(session).getStudent();
		String departClass = student.getDepartClass();
		String nextTerm = am.findTermBy(PARAMETER_NEXT_TERM);
		if ("1".equals(nextTerm)) // 新學年選課
			departClass = Toolket.processClassNoUp(departClass);

		Dtime dtime = new Dtime(departClass, nextTerm);
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
		request.setAttribute("actionName", "/Student/PhaseTwoAddDelCourse");
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
		request.getSession(false).setAttribute(
				SELD_ADCD_LIST,
				doForList(cm.findStudentAdcdByStudentNo(student.getStudentNo(),
						am.findTermBy(PARAMETER_NEXT_TERM))));
		request.setAttribute("actionName", "/Student/PhaseTwoAddDelCourse");
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
		request.getSession(false).setAttribute(
				SELD_ADCD_LIST,
				doForResult(cm.findAdcdByStudentNo(student.getStudentNo(), am
						.findTermBy(PARAMETER_NEXT_TERM))));
		request.setAttribute("actionName", "/Student/PhaseTwoAddDelCourse");
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
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		String term = am.findTermBy(PARAMETER_NEXT_TERM);
		String grade = clazz.getGrade();
		if ("1".equals(term)) // 新學年選課
			grade = String.valueOf(Integer.parseInt(grade) + 1);

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
				"student/PhaseTwoStudentOnlineAddCourse.jsp");
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
		String term = am.findTermBy(PARAMETER_NEXT_TERM);
		float credits = sumCredits(doForDuplicate(cm.getSeldDataByStudentNo(
				student.getStudentNo(), term)));
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
						"Course.errorN1", "加選科目時，學分總數已超過上限 (>"
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
									true, 2);
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
					// 前端已判斷過,此處還需判斷一次?
					synchronized (sdi) {
						if (!"1".equals(StringUtils.substring(sdi.getClassNo(),
								4, 5))) {
							// 非新生學生
							stuSelect = cm.countSelect((sdi.getDtimeOid())
									.toString());
							if (!"3".equals(sdi.getOpt())
									&& minCount < stuSelect)
								aList.add(sdi);
							else if (LITERACY_MIN_COUNT < stuSelect)
								aList.add(sdi);
						} else {
							// 一年級所開的課不限制人數下限,否則高年級不能退選
							aList.add(sdi);
						}
					}
				}
			}
		}

		return aList;
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

	private List<DynaBean> doForList(List<StdAdcd> stdAdcds) {
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		List<DynaBean> ret = new LinkedList<DynaBean>();
		DynaBean db = null;
		Dtime dtime = null;
		for (StdAdcd stdAdcd : stdAdcds) {
			db = new LazyDynaBean();
			dtime = (Dtime) cm.getDtimeBy(stdAdcd.getDtimeOid().toString())
					.get(0);
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

	@SuppressWarnings("unchecked")
	private List<DynaBean> doForResult(List<Adcd> adcds) {
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		List<DynaBean> ret = new LinkedList<DynaBean>();
		List dtimes = null;
		Dtime dtime = null;
		DynaBean db = null;
		for (Adcd stdAdcd : adcds) {
			db = new LazyDynaBean();
			dtimes = cm.getDtimeBy(stdAdcd.getDtimeOid().toString());
			if (!dtimes.isEmpty()) {
				dtime = (Dtime) cm.getDtimeBy(stdAdcd.getDtimeOid().toString())
						.get(0);
				db.set("courseName", cm.findCourseInfoByCscode(
						dtime.getCscode()).getChiName());
				db.set("className", Toolket.getClassFullName((dtime
						.getDepartClass())));
				db.set("optName", Toolket.getCourseOpt(dtime.getOpt()));
				db.set("adddraw", stdAdcd.getAdddraw().equals("A") ? "加選"
						: "退選");
				ret.add(db);
			}
		}
		return ret;
	}

	// 將相同Dtime Oid集中,將上課時間作處理
	@SuppressWarnings("unchecked")
	@Override
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

	// 排除多餘Dtime Oid之清單與通識課程T0002
	@Override
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
				if (!forCheck.contains((Integer) map.get("oid"))
						&& !"T0002".equals((String) map.get("cscode"))) {
					ret.add(map);
					forCheck.add((Integer) map.get("oid"));
				}
			}
		}
		return ret;
	}

	private DynaBean doForExtInfo(CourseManager cm, Integer dtimeOid,
			HttpServletRequest request) {
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		DynaBean ret = new LazyDynaBean();
		Dtime dtime = cm.findDtimeBy(dtimeOid);
		String schoolType = cm.findSchoolTypeByClassNo(dtime.getDepartClass());
		Csno csno = cm.findCourseInfoByCscode(dtime.getCscode());
		Member member = null;
		if (dtime != null && StringUtils.isNotBlank(dtime.getTechid()))
			member = mm.findMemberByAccount(dtime.getTechid());

		// 第2階段Year與Term不需轉換
		Map<String, Integer> yearTerm = Toolket.getNextYearTerm();
		// Integer year = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
		// Integer term = "1".equals(am.findTermBy(PARAMETER_SCHOOL_TERM)) ? 2 :
		// 1;
		Integer year = yearTerm.get(IConstants.PARAMETER_SCHOOL_YEAR);
		Integer term = yearTerm.get(IConstants.PARAMETER_SCHOOL_TERM);
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
		Map<String, Integer> yearTerm = Toolket.getNextYearTerm();
		// Integer year = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
		// Integer term = "1".equals(am.findTermBy(PARAMETER_SCHOOL_TERM)) ? 2 :
		// 1;
		Integer year = yearTerm.get(IConstants.PARAMETER_SCHOOL_YEAR);
		Integer term = yearTerm.get(IConstants.PARAMETER_SCHOOL_TERM);
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

	@SuppressWarnings("unchecked")
	protected Map<String, Object> processSeldCourse(List<Map> seldList,
			int minCount, CourseManager cm, Student student, String nextTerm) {

		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> resultOpt1 = new ArrayList<OnlineAddRemoveCourseUpAction.SeldDataInfo>();
		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> resultOpt2 = new ArrayList<OnlineAddRemoveCourseUpAction.SeldDataInfo>();
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put(HAS_LITERACY_SELD, Boolean.FALSE); // 預設是沒有加選通識課程
		ret.put(HAS_ARMY_SELD, Boolean.FALSE); // 

		Map obj = null, oo = null;
		String cscode = null, stuSelect = null, time2 = "", opt = null, courseClass = null;
		boolean bFlag = false, bFlag1 = false, isNeedCheck = true, isNeedCheck1 = true;
		String[] armyCourseCheck = null; // 98.1新規定:部份軍訓不可重複
		if ("1".equals(nextTerm))
			armyCourseCheck = IConstants.COURSE_ARMY_DENIED_1;
		else
			armyCourseCheck = IConstants.COURSE_ARMY_DENIED_2;

		if (!seldList.isEmpty()) {

			OnlineAddRemoveCourseUpAction.SeldDataInfo content = null;
			for (int i = 0; i < seldList.size(); i++) {
				content = new OnlineAddRemoveCourseUpAction.SeldDataInfo();
				obj = (Map) seldList.get(i);
				courseClass = (String) obj.get("classNo");
				content.setClassNo(courseClass);
				opt = (String) obj.get("opt");
				// isLiteracyClass = Toolket.isLiteracyClass((String) obj
				// .get("classNo"));
				// 判斷是否已有加選通識課程?
				// 98.9.11改成以選別判斷
				if ("3".equals(opt) && isNeedCheck) {
					ret.put(HAS_LITERACY_SELD, Boolean.TRUE);
					isNeedCheck = false;
				}
				content.setClassName((String) obj.get("className"));
				cscode = (String) obj.get("cscode");

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
				} else
					content.setTime2(getClassTimeInfo(
							(Integer) obj.get("week"), (String) obj
									.get("begin"), (String) obj.get("end")));

				// 處理第四個上課時段
				if (bFlag1) {
					if (i + 1 < seldList.size()) { // 避免超過index
						oo = (Map) seldList.get(i + 1);
						if (((Integer) oo.get("oid")).toString().equals(
								((Integer) obj.get("oid")).toString())) {
							time2 += "，"
									+ getClassTimeInfo(
											(Integer) oo.get("week"),
											(String) oo.get("begin"),
											(String) oo.get("end"));
							content.setTime2(time2);
							i++;
						} else
							content.setTime2(time2);
					}
				}

				String departClass = student.getDepartClass();
				if ("1".equals(nextTerm))
					departClass = Toolket.processClassNoUp(departClass);
				List<Adcd> adcds = cm
						.findExistedAdcd(student.getStudentNo(), departClass,
								((Integer) obj.get("oid")).toString(), "A");
				// 將選別分離,學生該班必修不可退選,加選須可以退
				if ((!adcds.isEmpty() && !"3".equals(opt)) || "2".equals(opt)
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
					resultOpt2.add(content);
				} else if ("1".equals(opt)
						&& departClass.equalsIgnoreCase(courseClass)) {
					content.setErrorMessage("");
					content.setCanChoose("0");
					resultOpt1.add(content);
				} else if ("3".equals(opt)) {
					// 第二階段通識選課人數下限為20人
					minCount = LITERACY_MIN_COUNT;
					if (minCount >= Integer.parseInt(stuSelect)) {
						content.setErrorMessage("已達該科目選課人數下限 (<=" + minCount
								+ ")");
						content.setCanChoose("1");
					} else {
						content.setErrorMessage("");
						content.setCanChoose("0");
					}
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
	protected Map<String, List<OnlineAddRemoveCourseUpAction.SeldDataInfo>> processSeldCourseBak(
			List<Map> seldList, int minCount, CourseManager cm,
			Student student, String term) {

		// 通識課程與體育興趣選項,有必修與選修
		String[] array = { "T0T90", "T0U20", "T0860", "T0U10", "T0S20",
				"T0S10", "T0850", "T0870", "T0T70", "T0U00", "T0T80", "T0002" };
		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> resultOpt1 = new ArrayList<OnlineAddRemoveCourseUpAction.SeldDataInfo>();
		List<OnlineAddRemoveCourseUpAction.SeldDataInfo> resultOpt2 = new ArrayList<OnlineAddRemoveCourseUpAction.SeldDataInfo>();
		Map obj = null, oo = null;
		String cscode = null, stuSelect = null, time2 = "";
		boolean bFlag = false;

		if (!seldList.isEmpty()) {

			OnlineAddRemoveCourseUpAction.SeldDataInfo content = null;
			for (int i = 0; i < seldList.size(); i++) {
				content = new OnlineAddRemoveCourseUpAction.SeldDataInfo();
				obj = (Map) seldList.get(i);
				String courseClass = (String) obj.get("classNo");
				content.setClassNo(courseClass);
				content.setClassName((String) obj.get("className"));
				cscode = (String) obj.get("cscode");
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
				String opt = (String) obj.get("opt");
				content.setOpt(opt);
				content.setOptName(Toolket.getCourseOpt(opt));
				content.setDtimeOid((Integer) obj.get("oid"));
				// 將相同課程但不同上課時間做整理
				if (i < seldList.size() - 1) {
					bFlag = false;
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

				String departClass = student.getDepartClass();
				if ("1".equals(term))
					departClass = Toolket.processClassNoUp(departClass);
				List<Adcd> adcds = cm
						.findExistedAdcd(student.getStudentNo(), departClass,
								((Integer) obj.get("oid")).toString(), "A");
				// 將選別分離,學生該班必修不可退選,加選須可以退
				if (!adcds.isEmpty() || "2".equals(opt)
						|| ArrayUtils.contains(array, cscode)) {
					// 一年級所開的課不限制人數下限,否則高年級不能退選
					if (minCount >= Integer.parseInt(stuSelect)
							&& !"1".equals(courseClass.substring(4, 5))) {
						content.setErrorMessage("已達該科目選課人數下限 (<=" + minCount
								+ ")");
						content.setCanChoose("1");
					} else {
						content.setErrorMessage("");
						content.setCanChoose("0");
					}
					resultOpt2.add(content);
				} else if ("1".equals(opt)) {
					content.setErrorMessage("");
					content.setCanChoose("0");
					resultOpt1.add(content);
				} else if ("3".equals(opt)) {
					// 第一階段通識選課人數下限為0人
					minCount = LITERACY_MIN_COUNT;
					if (minCount >= Integer.parseInt(stuSelect)) {
						content.setErrorMessage("已達該科目選課人數下限 (<=" + minCount
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
	private float sumCredits(List<Map> seldDataByStudentNo) {
		float credits = 0.0f;
		for (Map o : seldDataByStudentNo) {
			credits += (Float) o.get("credit");
		}
		return credits;
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
	private List<Map> doForDuplicate(List<Map> seldList) {
		List<Integer> dtimeOidCheck = new ArrayList<Integer>();
		List<Map> ret = new LinkedList<Map>();
		int index = 0;
		for (Map content : seldList) {
			Integer dtimeOid = (Integer) content.get("oid");
			if (index == 0) {
				dtimeOidCheck.add(dtimeOid);
				ret.add(content);
				index++;
			} else {
				if (dtimeOidCheck.contains(dtimeOid))
					continue;
				else {
					dtimeOidCheck.add(dtimeOid);
					ret.add(content);
				}
			}
		}
		return ret;
	}

}
