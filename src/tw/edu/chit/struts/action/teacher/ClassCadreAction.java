package tw.edu.chit.struts.action.teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import tw.edu.chit.model.ClassCadre;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.TeacherOfficeLocation;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class ClassCadreAction extends BaseLookupDispatchAction {

	public static final String EMPL_INFO = "emplInfo";
	public static final String CLASS_INFO = "classInfo";
	public static final String CLASS_LIST_INFO = "classListInfo";
	public static final String TEACHER_COOKIE_NAME = "TeachDtimeInfo";
	public static final String TEACHER_COURSE_INFO = "courseInfo";
	public static final String ROLE_INFO = "roleInfo";
	public static final String STD_INFO = "stdInfo";

	/**
	 * 依據老師登入資訊取得導師班級資料
	 * 
	 * @commend 如果只有一個班級,即時進入班級幹部聯繫資料介面
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

		HttpSession session = request.getSession();
		ActionMessages errors = new ActionMessages();
		request.setAttribute(ROLE_INFO, getRoleInfo());
		clearData(request, response);
		try {
			MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
			Member member = (Member) getUserCredential(session).getMember();
			Empl empl = mm.findEmplByOid(member.getOid());
			session.setAttribute(EMPL_INFO, empl);
			Clazz[] classes = getUserCredential(session).getClassInChargeAryT();
			if (classes.length > 1) {
				// 超過一個班級導師,則顯示班級清單
				request.setAttribute(CLASS_LIST_INFO, Arrays.asList(classes));
				setContentPage(request.getSession(false),
						"teacher/ClassCadre.jsp");
			} else if (classes.length == 1) {
				// 只有單一班導則顯示幹部聯繫資料輸入介面
				session.setAttribute(CLASS_INFO, classes[0]);
				List<ClassCadre> ccs = subsetClassCadre(mm
						.findClassCadreByEmpl(empl), classes[0].getClassNo());
				if (!ccs.isEmpty()) {
					request.setAttribute(STD_INFO, ccs);
					setContentPage(request.getSession(false),
							"teacher/AddClassCadre.jsp");
				} else {
					request.setAttribute(STD_INFO, ccs);
					setContentPage(request.getSession(false),
							"teacher/AddClassCadre.jsp");
				}
			} else {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1", "查無導師班級資料!!"));
				saveMessages(request, errors);
				setContentPage(request.getSession(false),
						"teacher/AddClassCadre.jsp");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 點選"新增"時處理之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward addCadre(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.setAttribute(ROLE_INFO, getRoleInfo());
		HttpSession session = request.getSession(false);
		ActionMessages error = new ActionMessages();
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		List<Clazz> aList = getDatasInfoByIndex(request, mm);
		// 判斷有無勾選
		if (aList.size() > 1) {
			error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.setCourseName.modifyTooMuch"));
			saveErrors(request, error);
		} else if (aList.isEmpty()) {
			error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.onlineAddRemoveCourse.unselected"));
			saveErrors(request, error);
		} else {
			Clazz clazz = aList.get(0);
			session.setAttribute(CLASS_INFO, clazz);
			// 取得ClassCadre Listing
			Empl empl = (Empl) session.getAttribute(EMPL_INFO);
			try {
				List<ClassCadre> ccs = mm.findClassCadreByClassNo(empl, clazz
						.getClassNo());
				request.setAttribute(STD_INFO, ccs);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				new ActionMessages().add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, error);
			}
		}

		if (!error.isEmpty()) {
			setContentPage(request.getSession(false), "teacher/ClassCadre.jsp");
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		setContentPage(request.getSession(false), "teacher/AddClassCadre.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 新增班級幹部聯繫資料時處理之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Clazz clazz = (Clazz) session.getAttribute(CLASS_INFO);
		ActionMessages errors = new ActionMessages();
		try {
			MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
			Empl empl = (Empl) session.getAttribute(EMPL_INFO);
			List<ClassCadre> classCadres = processProps(request, empl, clazz
					.getClassNo(), mm);
			mm.txUpdateClassCadre(empl, classCadres);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.CreateSuccessful"));
			saveMessages(request, errors);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

		return unspecified(mapping, form, request, response);
	}

	/**
	 * 處理點選取消之方法
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

		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("teacher.classCadre.btn.add", "addCadre");
		map.put("Save", "save");
		map.put("Cancel", "cancel");
		return map;
	}

	// 以Index值取得TeachDtimeInfo物件
	private List<Clazz> getDatasInfoByIndex(HttpServletRequest request,
			MemberManager mm) {

		List<Clazz> aList = new ArrayList<Clazz>();
		String index = Toolket.getSelectedIndexFromCookie(request,
				TEACHER_COOKIE_NAME);
		String[] indexs = index.split("[|]");
		// 大於等於1是因為可能包含第一個無效Index值
		if (indexs.length >= 1) {
			for (String i : indexs) {
				if (StringUtils.isBlank(i))
					continue;
				Clazz clazz = mm.findClassByOid(Integer.valueOf(i));
				aList.add(clazz);
			}
		}

		return aList;
	}

	// 處理HTTP Parameters
	private List<ClassCadre> processProps(HttpServletRequest request,
			Empl empl, String classNo, MemberManager mm) {
		List<ClassCadre> ret = new LinkedList<ClassCadre>();
		empl.setTelephone(request.getParameter("homePhone"));
		empl.setCellPhone(request.getParameter("cellPhone"));
		empl.setEmail(request.getParameter("email"));
		empl = processLocation(request, empl);
		List<ClassCadre> ccs = mm.findClassCadreByClassNo(empl, classNo);
		if (!ccs.isEmpty()) {
			int i = 0;
			for (ClassCadre cc : ccs) {
				cc.setClassNo(classNo);
				cc.setStudentNo((request.getParameter("stdNo" + i))
						.toUpperCase());
				cc.setName(request.getParameter("name" + i));
				cc.setEmail(request.getParameter("email" + i));
				cc.setHomePhone(request.getParameter("homePhone" + i));
				cc.setCellPhone(request.getParameter("cellPhone" + i));
				cc.setRemark(request.getParameter("remark" + i));
				cc.setRole(Short.valueOf(String.valueOf(i)));
				cc.setPos(Integer.valueOf(String.valueOf(i++)));
				ret.add(cc);
			}
		} else {
			for (int i = 0; i < 9; i++) {
				ClassCadre cc = new ClassCadre();
				cc.setEmpl(empl);
				cc.setClassNo(classNo);
				cc.setStudentNo((request.getParameter("stdNo" + i))
						.toUpperCase());
				cc.setName(request.getParameter("name" + i));
				cc.setEmail(request.getParameter("email" + i));
				cc.setHomePhone(request.getParameter("homePhone" + i));
				cc.setCellPhone(request.getParameter("cellPhone" + i));
				cc.setRemark(request.getParameter("remark" + i));
				cc.setRole(Short.valueOf(String.valueOf(i)));
				cc.setPos(Integer.valueOf(String.valueOf(i)));
				ret.add(cc);
			}
		}

		return ret;
	}

	private List<ClassCadre> subsetClassCadre(List<ClassCadre> ccs,
			String classNo) {
		List<ClassCadre> ret = new ArrayList<ClassCadre>();
		for (ClassCadre cc : ccs) {
			if (cc.getClassNo().trim().equals(classNo))
				ret.add(cc);
		}
		return ret;
	}

	private Empl processLocation(HttpServletRequest request, Empl empl) {
		if (empl.getLocation() != null) {
			TeacherOfficeLocation tol = empl.getLocation();
			tol.setRoomId(request.getParameter("roomId"));
			tol.setExtension(request.getParameter("extension"));
			empl.setLocation(tol);
		} else {
			TeacherOfficeLocation tol = new TeacherOfficeLocation();
			tol.setRoomId(request.getParameter("roomId"));
			tol.setExtension(request.getParameter("extension"));
			tol.setEmpl(empl);
			empl.setLocation(tol);
		}
		return empl;
	}

	// 清除Cookie
	private void clearData(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession(false).removeAttribute(CLASS_INFO);
		Toolket.resetCheckboxCookie(response, TEACHER_COOKIE_NAME);
	}

	private List<String> getRoleInfo() {
		List<String> orders = new ArrayList<String>();
		for (int i = 0; i < 9; i++)
			orders.add(getRoleString(i));
		return orders;
	}

	private String getRoleString(int index) {
		switch (index) {
			case 0:
				return "班  代  表";
			case 1:
				return "副 班 代 表";
			case 2:
				return "輔 導 股 長";
			case 3:
				return "總 務 股 長";
			case 4:
				return "康 樂 股 長";
			case 5:
				return "學 藝 股 長";
			case 6:
				return "學 藝 幹 事";
			case 7:
				return "衛 生 股 長";
			case 8:
				return "衛 生 幹 事";
			default:
				return "";
		}
	}

}
