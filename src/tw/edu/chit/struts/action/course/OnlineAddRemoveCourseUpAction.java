package tw.edu.chit.struts.action.course;

import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.SCORE_MANAGER_BEAN_NAME;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Csno;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.impl.exception.SeldException;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.score.ScoreMasterAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class OnlineAddRemoveCourseUpAction extends BaseLookupDispatchAction {

	public static final String ACTION_NAME = "actionName";
	public static final String SELD_LIST_NAME = "seldList";
	public static final String SELD_LIST_COUNT = "seldCount";
	public static final String SELD_LIST_HOURS = "seldHours";
	public static final String SELD_LIST_CREDITS = "seldCredit";
	public static final String SELD_DATA_INFO = "seldData";

	private static final Pattern pattern = Pattern.compile("[|](.*?)[|]",
			Pattern.DOTALL);

	/**
	 * 處理第一次進入加退選線上選課畫面
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

		((DynaActionForm) form).initialize(mapping);
		HttpSession session = request.getSession(false);
		session.removeAttribute(SELD_LIST_NAME);
		session.removeAttribute(ScoreMasterAction.STUDENT_INFO_AJAX);
		session.setAttribute(ACTION_NAME, "/Course/OnlineAddRemoveCourseUp");
		setContentPage(request.getSession(false),
				"course/OnlineAddRemoveCourse.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 處理以學號查詢學生選課清單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		HttpSession session = request.getSession(false);
		Toolket.resetCheckboxCookie(response, SELD_LIST_NAME);
		// 確實清除變數"seldList"內所存資料,因學生可能無選課資料
		session.removeAttribute(SELD_LIST_NAME);
		ActionMessages messages = validateInputForUpdate(aForm, Toolket
				.getBundle(request));
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {
			try {
				CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
				log.info("Student NO : " + aForm.getString("stdNo"));
				List<Map> seldList = doForDuplicate(cm.getSeldDataByStudentNo(
						aForm.getString("stdNo"), "1"));
				if (!seldList.isEmpty()) {
					session.setAttribute("NO", aForm.getString("stdNo")
							.toUpperCase());
					int hours = 0, position = 0;
					float credit = 0.0F;
					List<SeldDataInfo> result = new ArrayList<SeldDataInfo>();
					SeldDataInfo info = null;
					for (Map content : seldList) {
						info = new SeldDataInfo();
						Integer dtimeOid = (Integer) content.get("oid");
						info.setClassNo((String) content.get("classNo"));
						info.setClassName((String) content.get("className"));
						info.setCsCode((String) content.get("cscode"));
						info.setCsName((String) content.get("chi_Name"));
						info.setStuSelect(String.valueOf(cm
								.findSeldCountByDtimeOid(dtimeOid)));
						info.setSelectLimit(((Integer) content
								.get("select_Limit")).toString());
						info.setHour(Short.valueOf(((Integer) content
								.get("thour")).toString()));
						info.setCredit((Float) content.get("credit"));
						info.setSeldOid((Integer) content.get("soid")); // Seld Oid
						String opt = (String) content.get("opt");
						info.setOpt(opt);
						info.setOptName(Toolket.getCourseOpt(opt));
						info.setDtimeOid(dtimeOid); // Dtime Oid
						info.setPosition(Integer.valueOf(position++));
						info.setTerm((String) content.get("sterm"));
						hours += ((Integer) content.get("thour")).intValue();
						credit += ((Float) content.get("credit")).floatValue();
						result.add(info);
					}
					session.setAttribute(SELD_LIST_COUNT, Integer
							.valueOf(seldList.size()));
					session.setAttribute(SELD_LIST_HOURS, Integer
							.valueOf(hours));
					session.setAttribute(SELD_LIST_CREDITS, Float
							.valueOf(new DecimalFormat("0.0").format(credit)));
					session.setAttribute(SELD_LIST_NAME, result);
					session.setAttribute("mode", "ALL");
				} else {
					// 查無資料則回傳空白List
					session
							.setAttribute(SELD_LIST_NAME,
									Collections.EMPTY_LIST);
					session.setAttribute("mode", "NONE");
					ActionMessages msg = new ActionMessages();
					msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"Course.messageN1", "查無任何選課資料!!"));
					saveMessages(request, msg);
				}

			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}

		}

		setContentPage(request.getSession(false),
				"course/OnlineAddRemoveCourse.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 處理選擇修改學生選課內容之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = validateInputForUpdate(aForm, Toolket
				.getBundle(request));
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {
			String studentNo = aForm.getString("stdNo");
			ScoreManager sm = (ScoreManager) getBean(SCORE_MANAGER_BEAN_NAME);
			Student student = sm.findStudentByStudentNo(studentNo);
			aForm.set("stdName", student.getStudentName());
			aForm.set("stdClassName", Toolket.getClassFullName(student
					.getDepartClass()));
			SeldDataInfo sdi = getSeldDataInfoByIndex(request);
			if (sdi == null) {
				messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.onlineAddRemoveCourse.unselected"));
				saveErrors(request, messages);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}
			request.setAttribute(SELD_DATA_INFO, sdi);
		}

		setContentPage(request.getSession(false), "course/ModifyCourse.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 處理確定加選學生選課內容之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		Seld seld = processSeldByForm(aForm);
		session.setAttribute("seldInfoForOnline", seld);
		Dtime dtime = cm.findDtimeBy(seld.getDtimeOid());

		ActionMessages messages = validateInputForUpdate(aForm, Toolket
				.getBundle(request));
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {
			try {
				// 會以紙本作業完成加選,無需考慮衝堂問題
				// 選課人數上線於前端JavaScript判斷
				// 跨選設定不允許須阻擋並顯示訊息
				// 會顯示衝堂訊息
				MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
				ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
				Student student = mm.findStudentByNo(seld.getStudentNo());
				ScoreHist scoreHist = new ScoreHist(student.getStudentNo());
				List<ScoreHist> scoreHistList = sm.findScoreHistBy(scoreHist);
				String[] cscodeHist = new String[0];
				Float[] scoreList = new Float[0];
				float passScore = Toolket.getPassScoreByDepartClass(student
						.getDepartClass());
				for (ScoreHist hist : scoreHistList) {
					cscodeHist = (String[]) ArrayUtils.add(cscodeHist, hist
							.getCscode().toUpperCase());
					// 抵免要給分數,不然就會被當做無修課記錄而被加選成功
					if ("6".equals(hist.getEvgrType()))
						scoreList = (Float[]) ArrayUtils.add(scoreList, hist
								.getScore() != null ? hist.getScore()
								: passScore);
					else
						scoreList = (Float[]) ArrayUtils.add(scoreList, hist
								.getScore());
				}
				
				int ind = 0, startIndex = 0;
				boolean isHist = false;
				do {
					ind = ArrayUtils.indexOf(cscodeHist, seld.getCscode()
							.toUpperCase(), startIndex);
					startIndex = ind + 1;
					// 判斷是否選過且及格
					isHist = ind != StringUtils.INDEX_NOT_FOUND
							&& scoreList[ind] != null
							&& scoreList[ind] >= passScore;
				} while (!isHist && ind != StringUtils.INDEX_NOT_FOUND);		

				// 特殊班級(跨校生等)無條件加選
				String[] specialDepartClass = { "1152A", "1220", "122A",
						"122B", "2220" };
				String[] addGrade = { "42", "52" }; // 2技學生年級要+2
				int stuGrade = ArrayUtils.contains(specialDepartClass, student
						.getDepartClass()) ? 9 : Integer.parseInt(StringUtils
						.substring(student.getDepartClass(), 4, 5));
				stuGrade = ArrayUtils.contains(addGrade, StringUtils.substring(
						student.getDepartClass(), 1, 3)) ? stuGrade + 2
						: stuGrade;
				int dtimeGrade = Integer.parseInt(StringUtils.substring(dtime
						.getDepartClass(), 4, 5));
				
				if (isHist) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Course.errorN1",
									"歷年資料查詢到已修過該科目，請確認，謝謝!!"));
					saveErrors(request, messages);
				} else if (stuGrade >= dtimeGrade) {
					// 判斷學生年級與課程所開班級年級
					cm.txAddSelectedSeld(seld, student, "1", true);
					String idno = getUserCredential(request.getSession(false))
							.getMember().getIdno();
					cm.txSaveAdcdHistory(seld.getDtimeOid(), student
							.getStudentNo().toUpperCase(), idno, "A");
					if (ind != StringUtils.INDEX_NOT_FOUND)
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Course.errorN1",
										"該科目於歷年資料有查詢到，但該科目未及格，所以加選成功。"));
					else
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Message.CreateSuccessful"));
					saveMessages(request, messages);
				} else {
					messages
							.add(
									ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage(
											"Course.messageN1",
											"注意：學生低修高年級課程，加選作業尚未完成！<br/>"
													+ "&nbsp;&nbsp;&nbsp;&nbsp;按下[再次確定]鍵後課程才會加入學生選課資料中。"));
					saveErrors(request, messages);
					setContentPage(request.getSession(false),
							"course/OnlineAddHigherCourse.jsp");
					return mapping.findForward(IConstants.ACTION_MAIN_NAME);
				}

				Toolket.resetCheckboxCookie(response, SELD_LIST_NAME);
				return list(mapping, form, request, response);
			} catch (SeldException se) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", se.getMessage()));
				saveErrors(request, errors);
				if (se.getMessage().indexOf("衝堂") != StringUtils.INDEX_NOT_FOUND) {
					// 目前會拒絕衝堂課程進行加選
					dtime = cm.findDtimeBy(seld.getDtimeOid());
					Csno csno = cm.findCourseInfoByCscode(dtime.getCscode());
					request.setAttribute("csnoInfo", csno);
					request.setAttribute("classInfo", Toolket
							.getClassFullName(dtime.getDepartClass()));
					setContentPage(request.getSession(false),
							"course/ConflictList.jsp");
					return list(mapping, form, request, response);
				} else
					return list(mapping, form, request, response);
			}
		}
	}

	/**
	 * 處理確定加選學生低修高選課內容之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward addHigherCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		ActionMessages messages = new ActionMessages();
		Seld seld = (Seld) request.getSession(false).getAttribute(
				"seldInfoForOnline");

		try {
			Student student = mm.findStudentByNo(seld.getStudentNo());
			cm.txAddSelectedSeld(seld, student, "1", true);
			String idno = getUserCredential(request.getSession(false))
					.getMember().getIdno();
			cm.txSaveAdcdHistory(seld.getDtimeOid(), student.getStudentNo()
					.toUpperCase(), idno, "A");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.CreateSuccessful"));
			saveMessages(request, messages);
			Toolket.resetCheckboxCookie(response, SELD_LIST_NAME);
			return list(mapping, form, request, response);

		} catch (SeldException se) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", se.getMessage()));
			saveErrors(request, errors);
			if (se.getMessage().indexOf("衝堂") != StringUtils.INDEX_NOT_FOUND) {
				// 目前會拒絕衝堂課程進行加選
				Dtime dtime = cm.findDtimeBy(seld.getDtimeOid());
				Csno csno = cm.findCourseInfoByCscode(dtime.getCscode());
				request.setAttribute("csnoInfo", csno);
				request.setAttribute("classInfo", Toolket
						.getClassFullName(dtime.getDepartClass()));
				setContentPage(request.getSession(false),
						"course/ConflictList.jsp");
				return list(mapping, form, request, response);
			} else
				return list(mapping, form, request, response);
		}

	}

	/**
	 * 處理確定更新學生選課內容之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = validateInputForUpdate(aForm, Toolket
				.getBundle(request));
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {
			try {
				Short selectLimit = (Short) aForm.get("selectLimit");
				CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
				cm.txUpdateDtimeSelLimit(aForm.getString("dtimeOid"),
						selectLimit);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.ModifySuccessful"));
				saveMessages(request, messages);
				Toolket.resetCheckboxCookie(response, SELD_LIST_NAME);
				return mapping.findForward(IConstants.ACTION_SUCCESS_NAME);
			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}
		}

	}

	/**
	 * 處理學生加選線上選課紀錄之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward addCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = validateInputForUpdate(aForm, Toolket
				.getBundle(request));
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {

			String stdNo = aForm.getString("stdNo");
			ScoreManager sm = (ScoreManager) getBean(SCORE_MANAGER_BEAN_NAME);
			Student student = sm.findStudentByStudentNo(stdNo);
			if (student != null) {
				aForm.set("stdName", student.getStudentName());
				aForm.set("stdClassName", Toolket.getClassFullName(student
						.getDepartClass()));
				aForm.set("sterm", "1");
				aForm.set("classNo", "");
				aForm.set("csCode", "");
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "查無此人個人資料"));
				saveErrors(request, messages);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}

		}

		setContentPage(request.getSession(false), "course/OnlineAddCourse.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 處理學生退選線上選課紀錄之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward deleteCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = validateInputForUpdate(aForm, Toolket
				.getBundle(request));
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {

			List<SeldDataInfo> aList = getSeldDataListByIndex(request);
			if (!aList.isEmpty()) {

				CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
				// 取得選課欲退選之Oid列表
				StringBuffer seldBuf = new StringBuffer();
				for (SeldDataInfo info : aList) {
					seldBuf.append(info.getSeldOid()).append(",");
				}
				String inSyntax = StringUtils.substringBeforeLast(seldBuf
						.toString(), ",");
				log.info("Seld Oid SQL IN Syntax : " + inSyntax);
				String studentNo = aForm.getString("stdNo").toUpperCase();
				MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
				String classNo = mm.findStudentByNo(studentNo).getDepartClass();
				// 只有第一階段不會檢查選課人數下限
				cm.txRemoveSelectedSeld(studentNo, classNo, 1, inSyntax, false);
				String idno = getUserCredential(request.getSession(false))
						.getMember().getIdno();
				for (SeldDataInfo info : aList) {
					cm.txSaveAdcdHistory(info.getDtimeOid(), studentNo
							.toUpperCase(), idno, "D");
				}
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.DeleteSuccessful"));
				saveMessages(request, messages);
				// aForm.initialize(mapping);
				Toolket.resetCheckboxCookie(response, SELD_LIST_NAME);
				setContentPage(request.getSession(false),
						"course/OnlineAddRemoveCourse.jsp");
			} else {
				messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.onlineAddRemoveCourse.unselected"));
				saveErrors(request, messages);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}

		}

		return list(mapping, form, request, response);
	}

	/**
	 * 處理學生建立基本選課之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward createdBaseCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String stdNo = aForm.getString("stdNo").toUpperCase();
		ScoreManager sm = (ScoreManager) getBean(SCORE_MANAGER_BEAN_NAME);
		Student student = sm.findStudentByStudentNo(stdNo);
		cm.txCreateBaseSelectedForStudent(student, "1");
		return list(mapping, form, request, response);
	}

	/**
	 * 處理學生課程衝突後確定加選之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward makeSureAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String stdNo = aForm.getString("stdNo");
		ScoreManager sm = (ScoreManager) getBean(SCORE_MANAGER_BEAN_NAME);
		Student student = sm.findStudentByStudentNo(stdNo);
		cm.txCreateBaseSelectedForStudent(student, "1");
		return list(mapping, form, request, response);
	}

	/**
	 * 處理學生課程衝突後確定加選之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward conflictAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 選課人數上線於前端JavaScript判斷
		// 跨選設定不允許須阻擋並顯示訊息
		Seld seld = (Seld) request.getSession(false).getAttribute(
				"seldInfoForOnline");
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		Student student = mm.findStudentByNo(seld.getStudentNo());
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		// false代表不做衝堂判斷
		cm.txAddSelectedSeld(seld, student, "1", false);
		cm.txAddSeldConflictInfo(seld, getUserCredential(
				request.getSession(false)).getMember(), student, "1");
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.CreateSuccessful"));
		saveMessages(request, messages);
		Toolket.resetCheckboxCookie(response, SELD_LIST_NAME);
		return list(mapping, form, request, response);
	}

	/**
	 * 處理學生取消退選線上選課紀錄之方法
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

		return list(mapping, form, request, response);
	}

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query", "list");
		map.put("course.onlineAddRemoveCourse.modifySelectLimit", "modify");
		map.put("course.onlineAddRemoveCourse.addCourse", "addCourse"); // 加選
		map.put("course.onlineAddRemoveCourse.deleteCourse", "deleteCourse"); // 退選
		map.put("course.onlineAddRemoveCourse.createdBaseCourse",
				"createdBaseCourse"); // 建立基本選課
		map.put("course.onlineAddRemoveCourse.conflictAdd", "conflictAdd"); // 衝突後確定加選
		map.put("Cancel", "cancel");
		map.put("course.onlineAddRemoveCourse.makeSure", "add"); // 加選確定
		map.put("course.onlineAddRemoveCourse.makeSure1", "addHigherCourse"); // 低修高確定
		map.put("course.onlineAddRemoveCourse.update", "update");
		map.put("course.onlineAddRemoveCourse.makeSureAdd", "makeSureAdd"); // 衝突後確定加選
		return map;
	}

	public static class SeldDataInfo implements Serializable {

		private static final long serialVersionUID = -3094286571986269332L;

		private Integer seldOid; // Seld Oid
		private Integer position;
		private String classNo;
		private String className;
		private String csCode;
		private String csName;
		private String stuSelect;
		private String selectLimit;
		private Short hour;
		private Float credit;
		private String opt;
		private String elearning;
		private String elearningName;
		private String term;
		private String optName;
		private Integer dtimeOid; // Dtime Oid
		private String errorMessage;
		private String canChoose;
		private String time2;
		private String literacyType;
		private String literacyTypeName;

		public String getOpt() {
			return opt;
		}

		public void setOpt(String opt) {
			this.opt = opt;
		}

		public String getElearning() {
			return elearning;
		}

		public void setElearning(String elearning) {
			this.elearning = elearning;
		}

		public String getElearningName() {
			return elearningName;
		}

		public void setElearningName(String elearningName) {
			this.elearningName = elearningName;
		}

		public String getTerm() {
			return term;
		}

		public void setTerm(String term) {
			this.term = term;
		}

		public String getOptName() {
			return optName;
		}

		public void setOptName(String optName) {
			this.optName = optName;
		}

		public Integer getPosition() {
			return position;
		}

		public void setPosition(Integer position) {
			this.position = position;
		}

		public String getClassNo() {
			return classNo;
		}

		public void setClassNo(String classNo) {
			this.classNo = classNo;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getCsCode() {
			return csCode;
		}

		public void setCsCode(String csCode) {
			this.csCode = csCode;
		}

		public String getCsName() {
			return csName;
		}

		public void setCsName(String csName) {
			this.csName = csName;
		}

		public String getSelectLimit() {
			return selectLimit;
		}

		public void setSelectLimit(String selectLimit) {
			this.selectLimit = selectLimit;
		}

		public String getStuSelect() {
			return stuSelect;
		}

		public void setStuSelect(String stuSelect) {
			this.stuSelect = stuSelect;
		}

		public Integer getSeldOid() {
			return seldOid;
		}

		public void setSeldOid(Integer seldOid) {
			this.seldOid = seldOid;
		}

		public Float getCredit() {
			return credit;
		}

		public void setCredit(Float credit) {
			this.credit = credit;
		}

		public Short getHour() {
			return hour;
		}

		public void setHour(Short hour) {
			this.hour = hour;
		}

		public Integer getDtimeOid() {
			return dtimeOid;
		}

		public void setDtimeOid(Integer dtimeOid) {
			this.dtimeOid = dtimeOid;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getCanChoose() {
			return canChoose;
		}

		public void setCanChoose(String canChoose) {
			this.canChoose = canChoose;
		}

		public String getTime2() {
			return time2;
		}

		public void setTime2(String time2) {
			this.time2 = time2;
		}

		public String getLiteracyType() {
			return literacyType;
		}

		public void setLiteracyType(String literacyType) {
			this.literacyType = literacyType;
		}

		public String getLiteracyTypeName() {
			return literacyTypeName;
		}

		public void setLiteracyTypeName(String literacyTypeName) {
			this.literacyTypeName = literacyTypeName;
		}

	}

	@SuppressWarnings("unchecked")
	private SeldDataInfo getSeldDataInfoByIndex(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String index = Toolket.getSelectedIndexFromCookie(request,
				SELD_LIST_NAME);
		List seldDataList = (List) session.getAttribute(SELD_LIST_NAME);
		Matcher matcher = pattern.matcher(index);
		SeldDataInfo sdi = null;
		if (matcher.matches())
			sdi = (SeldDataInfo) seldDataList.get(Integer.parseInt(matcher
					.group(1)));

		return sdi;
	}

	@SuppressWarnings("unchecked")
	private List<SeldDataInfo> getSeldDataListByIndex(HttpServletRequest request) {

		List<SeldDataInfo> aList = new ArrayList<SeldDataInfo>();
		HttpSession session = request.getSession(false);
		String index = Toolket.getSelectedIndexFromCookie(request,
				SELD_LIST_NAME);
		List<SeldDataInfo> seldDataList = (List<SeldDataInfo>) session
				.getAttribute(SELD_LIST_NAME);
		for (SeldDataInfo sdi : seldDataList) {
			if (Toolket.isValueInCookie(sdi.getPosition().toString(), index)) {
				aList.add(sdi);
			}
		}

		return aList;
	}

	private ActionMessages validateInputForUpdate(DynaActionForm form,
			ResourceBundle bundle) {
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		return errors;
	}

	private void validateFieldFormat(DynaActionForm form,
			ActionMessages errors, ResourceBundle bundle) {
		// TODO 未完成Form Validation
	}

	private Seld processSeldByForm(DynaActionForm form) {
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		Seld seld = new Seld();
		Dtime dtime = cm.findDtimeBy(Integer.valueOf(form.getString("oid")));
		seld.setDtimeOid(Integer.valueOf(form.getString("oid")));
		seld.setStudentNo(form.getString("stdNo").toUpperCase());
		seld.setOpt(form.getString("optId"));
		seld.setCredit((Float) (form.get("credit")));
		seld.setDepartClass(dtime.getDepartClass());
		seld.setDepartClassName(Toolket
				.getClassFullName(dtime.getDepartClass()));
		seld.setCscode(dtime.getCscode().toUpperCase());
		seld.setCscodeName(cm.findCourseInfoByCscode(
				dtime.getCscode().toUpperCase()).getChiName());
		return seld;
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
