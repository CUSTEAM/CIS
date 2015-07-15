package tw.edu.chit.struts.action.course;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_NEXT_TERM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class BatchProcessAction extends BaseLookupDispatchAction {

	public static final String DTIME_COOKIE = "dtimes";
	public static final String DTIME_LIST = "dtimeList";
	public static final String DTIME_SELECTED_LIST = "selDtimes";

	public static final byte OPEN_TRUE = (byte) 1;
	public static final byte OPEN_FALSE = (byte) 0;
	public static final String[] REJECT = { "50000", "T0001", "T0002" };

	/**
	 * 首次進入批次作業
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.getSession(false).removeAttribute(DTIME_LIST);
		setContentPage(request.getSession(false), "course/BatchProcess.jsp");
		return mapping.findForward("Main");

	}

	/**
	 * 列表
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm setUpDtime = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, DTIME_COOKIE);

		String campusInCharge2 = (String) setUpDtime.get("campusInCharge2"); // area
		String schoolInCharge2 = (String) setUpDtime.get("schoolInCharge2"); // rule
		String deptInCharge2 = (String) setUpDtime.get("deptInCharge2"); // dept
		String departClass = (String) setUpDtime.get("classInCharge2"); // class
		String cscode = (String) setUpDtime.get("courseNumber");
		String techid = (String) setUpDtime.get("teacherId");
		String term = (String) setUpDtime.get("sterm");
		String choseType = (String) setUpDtime.get("choseType");

		UserCredential credential = (UserCredential) session
				.getAttribute("Credential");
		Clazz[] classes = credential.getClassInChargeAry();
		CourseManager manager = (CourseManager) getBean("courseManager");
		if (schoolInCharge2.equals("All")) {
			schoolInCharge2 = "%";
		}
		if (deptInCharge2.equals("All")) {
			deptInCharge2 = "%";
		}
		if (departClass.equals("All")) {
			departClass = "%";
		}
		// if area == All
		if (campusInCharge2.equals("All")) {
			// 儲存資料庫回傳物件
			session.setAttribute(DTIME_LIST, manager.getDtimeForOpenCsAll(
					classes, cscode, techid, term, choseType));

		} else {
			// 儲存資料庫回傳物件
			session.setAttribute(DTIME_LIST, manager.getDtimeForOpenCs(classes,
					campusInCharge2, schoolInCharge2, deptInCharge2,
					departClass, cscode, techid, term, choseType));
		}

		setContentPage(request.getSession(false), "course/BatchProcess.jsp");
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
				"Course.setCourseName.serachComplete"));
		saveMessages(request, msg);

		return mapping.findForward("Main");
	}

	/**
	 * 處理開放選修資料查詢
	 * 
	 * @commend 注意Session Scope需設定mode變數
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward openChoose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		List<Map> dtimeList = getDtimeListByParams(request, response,
				(DynaActionForm) form, null);
		session.setAttribute(DTIME_LIST, dtimeList);
		Toolket.setCheckboxCookie(response, DTIME_COOKIE, createCookieChecked(
				dtimeList, OPEN_TRUE), dtimeList.size());
		setContentPage(request.getSession(false), "course/BatchProcess.jsp");
		session.setAttribute("mode", "open");
		// ActionMessages msg = new ActionMessages();
		// msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
		// "Course.setCourseName.serachComplete"));
		// saveMessages(request, msg);
		return mapping.findForward("Main");
	}

	/**
	 * 顯示開放選修資料清單作為更新確定
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listUpdateOpen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		List<Map> selDtimes = getDtimEditList(request);
		if (!selDtimes.isEmpty()) {
			request.getSession(false).setAttribute(DTIME_SELECTED_LIST,
					selDtimes);
			// messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			// "Course.onlineAddRemoveCourse.unselected"));
			// saveErrors(request, messages);
			setContentPage(request.getSession(false),
					"course/UpdateOpenList.jsp");
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.onlineAddRemoveCourse.unselected"));
			saveErrors(request, messages);
		}
		return mapping.findForward("Main");
	}

	/**
	 * 處理確定更新開放選修
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward updateOpen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		List<Map> selDtimes = (List<Map>) session
				.getAttribute(DTIME_SELECTED_LIST);
		List<Map> allSelDtimes = (List<Map>) session.getAttribute(DTIME_LIST);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		// 先將所有已查詢Dtime清單全更新為"不開放"
		// 再將所選之Dtime清單做更新為"開放"
		cm.txUpdateOpenByDtimeList(allSelDtimes, Byte.valueOf("0"));
		cm.txUpdateOpenByDtimeList(selDtimes, Byte.valueOf("1"));
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
				"Course.setCourseName.modifyComplete"));
		saveMessages(request, msg);
		return openChoose(mapping, form, request, response);
	}

	/**
	 * 處理確定更新不開放選修
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward updateNotOpen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		List<Map> selDtimes = (List<Map>) session
				.getAttribute(DTIME_SELECTED_LIST);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		cm.txUpdateOpenByDtimeList(selDtimes, Byte.valueOf("0"));
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
				"Course.setCourseName.modifyComplete"));
		saveMessages(request, msg);
		return openChoose(mapping, form, request, response);
	}

	/**
	 * 處理學生基本選課資料查詢
	 * 
	 * @commend 預設CheckBox為unchecked,且只顯示不開放課程
	 * @commend 注意Session Scope需設定mode變數
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward createBaselect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.setAttribute(DTIME_LIST, getDtimeListByParams(request,
				response, (DynaActionForm) form, null));
		setContentPage(session, "course/BatchProcess.jsp");
		session.setAttribute("mode", "baseSel");
		return mapping.findForward("Main");
	}

	/**
	 * 處理確定產生/刪除學生基本選課資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward updateBaselect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		boolean addFlag = false;
		DynaActionForm aForm = (DynaActionForm) form;
		String mode = aForm.getString("mode");
		String sterm = aForm.getString("sterm"); // 使用者選擇的學期
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		// 如果Term為1,代表新學期開始,在未建立學生升級機制下,
		// 只好先以Code自動將學生升級
		String term = am.findTermBy(PARAMETER_NEXT_TERM);
		boolean bFlag = false;
		// sterm=2代表可能要做本學期之學生基本選課資料
		if ("1".equals(sterm) && "1".equals(term))
			bFlag = true;
		List<Map> selDtimes = getDtimEditList(request);
		String tempClassNo = null;
		List<Student> students = null;
		ActionMessages msg = new ActionMessages();
		int i = 0;
		for (Map content : selDtimes) {
			if (i++ == 0) {
				// 避免多次查詢
				tempClassNo = content.get("ClassNo").toString();
				if (bFlag)
					tempClassNo = Toolket.processClassNoDown(tempClassNo);
				students = mm.findStudentsByClassNo(tempClassNo);
			}

			if (!content.get("ClassNo").toString().equals(tempClassNo)) {
				tempClassNo = content.get("ClassNo").toString();
				if (bFlag)
					tempClassNo = Toolket.processClassNoDown(tempClassNo);
				students = mm.findStudentsByClassNo(tempClassNo);
			}
			if ("add".equals(mode)) {
				cm.txCreateBaseSelectedForStudents(content, students);
				addFlag = true;
			} else if ("del".equals(mode)) {
				cm.txDeleteBaseSelectedForStudents(content, students);
			}
		}

		if (addFlag)
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"Course.setCourseName.saveComplete"));
		else
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"Course.setCourseName.removeComplete"));

		saveMessages(request, msg);
		return createBaselect(mapping, form, request, response);
	}

	/**
	 * 處理取消按鈕事件
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

		setContentPage(request.getSession(false), "course/BatchProcess.jsp");
		return mapping.findForward("Main");

	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query", "list");
		map.put("Clear", "clear");
		map.put("OpenChoose", "openChoose");
		map.put("listUpdateOpen", "listUpdateOpen");
		map.put("updateOpen", "updateOpen"); // 設定為開放
		map.put("updateNotOpen", "updateNotOpen"); // 設定為不開放
		map.put("ClearTimes", "clearTimes");
		map.put("ClearTeachers", "clearTeachers");
		map.put("ClearExam", "clearExam");
		map.put("CreateBaselect", "createBaselect"); // 建立基本選課資料
		map.put("updateBaselect", "updateBaselect"); // 更新基本選課資料
		map.put("deleteBaselect", "updateBaselect"); // 刪除基本選課資料
		map.put("Cancel", "cancel");
		return map;
	}

	// 使用Cookie將使用者所選課程由取出
	@SuppressWarnings("unchecked")
	private List<Map> getDtimEditList(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, DTIME_COOKIE);
		List<Map> dtimes = (List<Map>) session.getAttribute(DTIME_LIST);
		List<Map> selDtimes = new ArrayList<Map>();
		for (Map dtime : dtimes) {
			if (Toolket.isValueInCookie(dtime.get("oid").toString(), oids)
					&& !ArrayUtils.contains(REJECT, (String) dtime
							.get("cscode"))) {
				selDtimes.add(dtime);
			}
		}
		return selDtimes;
	}

	// 處理以Request Parameters作為查詢Dtime物件之用
	// open為null時,則不會將Dtime內open欄位作Filtering
	@SuppressWarnings("unchecked")
	private List<Map> getDtimeListByParams(HttpServletRequest request,
			HttpServletResponse response, DynaActionForm form, Byte open) {

		HttpSession session = request.getSession(false);
		Toolket.resetCheckboxCookie(response, DTIME_COOKIE);

		String campusInCharge2 = (String) form.get("campusInCharge2");
		String schoolInCharge2 = (String) form.get("schoolInCharge2");
		String deptInCharge2 = (String) form.get("deptInCharge2");
		String departClass = (String) form.get("classInCharge2");
		String term = (String) form.get("sterm"); // term
		String choseType = (String) form.get("choseType"); // opt

		UserCredential credential = (UserCredential) session
				.getAttribute("Credential");
		Clazz[] classes = credential.getClassInChargeAry();
		CourseManager cm = (CourseManager) getBean("courseManager");
		if ("All".equals(schoolInCharge2)) {
			schoolInCharge2 = "%";
		}
		if ("All".equals(deptInCharge2)) {
			deptInCharge2 = "%";
		}
		if ("All".equals(departClass)) {
			departClass = "%";
		}

		List<Map> dtimeList = null;
		if (campusInCharge2.equals("All")) {
			dtimeList = cm.getDtimeForOpenCsAll(classes, term, choseType, open);
		} else {
			dtimeList = cm.getDtimeForOpenCs(classes, campusInCharge2,
					schoolInCharge2, deptInCharge2, departClass, term,
					choseType, open);
		}
		return dtimeList;
	}

	// 將Dtime清單內Open欄位值為1者,加入Cookie Index中
	private String createCookieChecked(List<Map> dtimeList, byte open) {
		StringBuffer cookieValue = new StringBuffer("|");
		for (Map dtime : dtimeList) {
			if ((Boolean) dtime.get("open") == true) {
				cookieValue.append(dtime.get("oid").toString() + "|");
			}
		}
		return cookieValue.toString();
	}

}
