package tw.edu.chit.struts.action.deptassist;

import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

import tw.edu.chit.model.CourseSyllabus;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Member;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.teacher.TeachSyllabusAction.TeachDtimeInfo;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class AssistantTeachSyllabusAction extends BaseLookupDispatchAction {

	public static final String TEACHER_LIST = "teacherList";
	public static final String ASSISTANT_EMPL = "assistantEmpl";
	public static final String TEACHER_COOKIE_NAME = "teacherList";
	public static final String TEACHER_DTIME_NAME1 = "teacherDtime1";
	public static final String TEACHER_DTIME_NAME2 = "teacherDtime2";
	public static final String TEACHER_COURSE_INFOS = "courseInfos";
	public static final String TEACHER_COURSE_INFO = "courseInfo";
	public static final String COURSE_SYLLABUS_INFO = "courseSyllabusInfo";
	public static final String COURSE_SYLLABUS_LIST = "courseSyllabusList";

	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/MM/dd");

	private static final String SCHOOL_YEAR = "schoolYear";

	/**
	 * 依據系助理登入資訊取得系所教師清單資料
	 * 
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

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession();
		Integer year = Integer.valueOf(am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR));
		// Integer term = "1".equals(am.findTermBy(PARAMETER_SCHOOL_TERM)) ? 2 :
		// 1;
		Integer term = Integer.valueOf(am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM));
//		Map<String, Integer> nextYearTerm = Toolket.getNextYearTerm();
//		Integer year = nextYearTerm.get(IConstants.PARAMETER_SCHOOL_YEAR);
//		Integer term = nextYearTerm.get(IConstants.PARAMETER_SCHOOL_TERM);
		session.setAttribute(SCHOOL_YEAR, year);

		clearData(request, response, (DynaActionForm) form, term);
		try {
			Member member = (Member) getUserCredential(session).getMember();
			MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
			Empl empl = mm.findEmplByOid(member.getOid());
			empl.setUnit2(Toolket.getEmpUnit(empl.getUnit()));
			List<Empl> empls = mm.findTeacherByUnit(empl.getUnit());
			session.setAttribute(TEACHER_LIST, empls);
			session.setAttribute(ASSISTANT_EMPL, empl);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("Main");
		}

		setContentPage(request.getSession(false),
				"assistant/AssistantTeachSyllabus.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 依據系助理勾選取得教師課程清單資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Toolket.resetCheckboxCookie(response, "TeachDtimeInfo");
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		Integer year = (Integer) session.getAttribute(SCHOOL_YEAR);
		Empl empl = getEmplInfoByIndex(request);
		session.setAttribute("emplInfo", empl);
		Member member = mm.findMemberByAccount(empl.getIdno());
		List<Object[]> aList = cm.getDtimeByTeacher(member);
		List<TeachDtimeInfo> td1 = null, td2 = null;
		if (!aList.isEmpty()) {
			td1 = new ArrayList<TeachDtimeInfo>();
			td2 = new ArrayList<TeachDtimeInfo>();
			TeachDtimeInfo info = null;
			int pos = 0;
			for (Object[] o : aList) {
				info = new TeachDtimeInfo();
				info.setPos(pos++);
				info.setClassName((String) o[0]);
				info.setChiName((String) o[1]);
				info.setOid((Integer) o[2]); // Dtime Oid
				info.setCscode((String) o[3]);
				info.setEngName((String) o[6]);

				if ("1".equals(((String) o[5]))) {
					td1.add(info);
					// TODO 前台只管當學期
					CourseSyllabus cs = cm.getCourseSyllabusByDtimeOid(
							(Integer) o[2], year, 1);
					if (cs != null) {
						info.setLastModified(cs.getLastModified());
						info.setDateFormat(sdf.format(cs.getLastModified()));
						info.setCiOid(cs.getOid());
					}
				} else {
					td2.add(info);
					// TODO 前台只管當學期
					CourseSyllabus cs = cm.getCourseSyllabusByDtimeOid(
							(Integer) o[2], year - 1, 2);
					if (cs != null) {
						info.setLastModified(cs.getLastModified());
						info.setDateFormat(sdf.format(cs.getLastModified()));
						info.setCiOid(cs.getOid());
					}
				}
			}

		} else {
			td1 = Collections.EMPTY_LIST;
			td2 = Collections.EMPTY_LIST;
		}

		session.setAttribute(TEACHER_DTIME_NAME1, td1);
		session.setAttribute(TEACHER_DTIME_NAME2, td2);
		setContentPage(request.getSession(false), "assistant/TeachSyllabus.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 處理點選新增/修改課程大綱
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward addSyllabus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		List<TeachDtimeInfo> tdis = getDatasInfoByIndex(request);
		session.setAttribute(TEACHER_COURSE_INFOS, tdis);
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		for (TeachDtimeInfo tdi : tdis) {
			if (tdi.getCiOid() != null) {
				session.setAttribute(TEACHER_COURSE_INFO, tdi);
				request.setAttribute("csnoInfo", cm.findCourseInfoByCscode(tdi
						.getCscode()));
				// 取得CourseSyllabus物件
				CourseSyllabus cs = cm.findCourseSyllabusByOid(tdi.getCiOid());
				request.setAttribute(COURSE_SYLLABUS_INFO, cs);
				request.setAttribute(COURSE_SYLLABUS_LIST, cs.getSyllabuses());
				// 判斷需要多少Syllabus輸入欄位
				request.setAttribute("counts", 18 - cs.getSyllabuses().size());
				break;
			}
		}

		request.setAttribute("actionName",
				"/DeptAssistant/AssistantTeachSyllabus");
		setContentPage(session, "teacher/AddTeachSyllabus.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 處理新增/修改課程大綱
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		// TODO 
		Integer year = Integer.valueOf(am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR));
		// Integer term = "1".equals(am.findTermBy(PARAMETER_SCHOOL_TERM)) ? 2 :
		// 1;
		Integer term = Integer.valueOf(am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM));
		// Integer term = Integer.valueOf(aForm.getString("term"));
//		Map<String, Integer> nextYearTerm = Toolket.getNextYearTerm();
//		Integer year = nextYearTerm.get(IConstants.PARAMETER_SCHOOL_YEAR);
//		Integer term = nextYearTerm.get(IConstants.PARAMETER_SCHOOL_TERM);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		try {
//			String[] courseEName = (String[]) aForm.get("courseEName");
			String officeHour = aForm.getString("officeHour");
			String requisites = aForm.getString("requisites");
			String objectives = aForm.getString("objectives");
			String syllabuses = aForm.getString("syllabus");
			String[] topics = aForm.getStrings("topic");
			String[] contents = aForm.getStrings("content");
			String[] hours = aForm.getStrings("hours");
			String[] weekNos = aForm.getStrings("weekNo");
			String[] remarks = aForm.getStrings("remark");
			List<TeachDtimeInfo> tdis = (List<TeachDtimeInfo>) session
					.getAttribute(TEACHER_COURSE_INFOS);
			CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
//			int index = 0;
			for (TeachDtimeInfo tdi : tdis) {
//				Csno csno = cm.getCourseInfoByCscode(tdi.getCscode());
//				csno.setEngName(Toolket
//						.processEnglishCourseName(courseEName[index++].trim()));
//				cm.updateCourseNameBy(csno);
				Dtime dtime = cm.findDtimeBy(tdi.getOid());
				if (tdi.getCiOid() != null) {
					cm.txUpdateCourseSyllabus(tdi.getCiOid(), year, term, dtime
							.getDepartClass(), dtime.getCscode(), officeHour,
							requisites, objectives, syllabuses, topics,
							contents, hours, weekNos, remarks);

				} else {
					cm.txAddCourseSyllabus(tdi.getOid(), year, term, dtime
							.getDepartClass(), dtime.getCscode(), officeHour,
							requisites, objectives, syllabuses, topics,
							contents, hours, weekNos, remarks);
				}
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.CreateSuccessful"));
			saveMessages(request, messages);
			return unspecified(mapping, form, request, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			saveErrors(request, messages);
			return mapping.findForward("Main");
		}
	}

	/**
	 * 處理返回
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

	/**
	 * 處理取消
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return search(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("teacher.syllabusSearch.btn.search", "search");
		map.put("teacher.syllabus.btn.add", "addSyllabus");
		map.put("Save", "save");
		map.put("Back", "back");
		map.put("Cancel", "cancel");
		return map;
	}

	private void clearData(HttpServletRequest request,
			HttpServletResponse response, DynaActionForm form, Integer term) {

		HttpSession session = request.getSession(false);
		session.removeAttribute(TEACHER_LIST);
		form.set("term", String.valueOf(term));
		Toolket.resetCheckboxCookie(response, TEACHER_LIST);
	}

	// 以Oid值取得Empl物件
	@SuppressWarnings("unchecked")
	private Empl getEmplInfoByIndex(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, TEACHER_LIST);
		List<Empl> empls = (List<Empl>) session.getAttribute(TEACHER_LIST);
		for (Empl empl : empls) {
			if (Toolket.isValueInCookie(empl.getOid().toString(), oids)) {
				return empl;
			}
		}
		return null;
	}

	// 以Oid值取得TeachDtimeInfo物件
	@SuppressWarnings("unchecked")
	private List<TeachDtimeInfo> getDatasInfoByIndex(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request,
				"TeachDtimeInfo");
		List<TeachDtimeInfo> dataList1 = (List<TeachDtimeInfo>) session
				.getAttribute(TEACHER_DTIME_NAME1);
		List<TeachDtimeInfo> dataList2 = (List<TeachDtimeInfo>) session
				.getAttribute(TEACHER_DTIME_NAME2);
		List<TeachDtimeInfo> aList = new ArrayList<TeachDtimeInfo>();
		for (TeachDtimeInfo tdi : dataList1) {
			if (Toolket.isValueInCookie(tdi.getOid().toString(), oids)) {
				aList.add(tdi);
			}
		}
		for (TeachDtimeInfo tdi : dataList2) {
			if (Toolket.isValueInCookie(tdi.getOid().toString(), oids)) {
				aList.add(tdi);
			}
		}
		return aList;
	}

}
