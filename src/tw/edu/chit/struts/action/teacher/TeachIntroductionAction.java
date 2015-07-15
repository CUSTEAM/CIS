package tw.edu.chit.struts.action.teacher;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_YEAR;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.Savedtime;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Parameters;
import tw.edu.chit.util.Toolket;

public class TeachIntroductionAction extends BaseLookupDispatchAction {

	public static final String TEACHER_DTIME_NAME = "teacherDtime";
	// public static final String TEACHER_DTIME_NAME2 = "teacherDtime2";
	public static final String TEACHER_COOKIE_NAME = "TeachDtimeInfo";
	public static final String TEACHER_COURSE_INFO = "courseInfo";
	public static final String COURSE_INTRO_INFO = "courseIntroInfo";
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	private static final String SCHOOL_YEAR = "schoolYear";

	/**
	 * 依據老師登入資訊取得教學班級資料
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

		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession();
		Integer year = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
		Integer term = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_TERM));
		// For Introduction Printing
		session.setAttribute(SCHOOL_YEAR, year);
		session.setAttribute("YearForSyllabus", year.toString());
		session.setAttribute("TermForSyllabus", term.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.TRADITIONAL_CHINESE);
		session.setAttribute("begin", sdf.format(Parameters.TEACH_INTRO_BEGIN.getTime()));
		session.setAttribute("end", sdf.format(Parameters.TEACH_INTRO_END.getTime()));

		initData(request, response, (DynaActionForm) form, year, term);
		try {
			UserCredential credential = getUserCredential(session);
			CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
			// 取得Dtime內所有符合資料之Dtime(包括上下學期)
			
			List<Object[]> aList = cm.getDtimeByTeacher(credential.getMember());
			List<TeachDtimeInfo> td1 = null;
			
			if (!aList.isEmpty()) {
				td1 = new ArrayList<TeachDtimeInfo>();
				TeachDtimeInfo info = null;
				CourseIntroduction ci = null;
				List<CourseIntroduction> cis = null;
				int pos = 0;
				String dateFormat = "";
				
				StringBuilder sb=new StringBuilder();
				List tmp;
				DtimeClass d;
				for (Object[] o : aList) {
					info = new TeachDtimeInfo();
					info.setPos(pos++);
					info.setClassName((String) o[0]);
					info.setChiName((String) o[1]);
					info.setOid((Integer) o[2]);
					info.setCscode((String) o[3]);
					info.setClassNo((String) o[4]);
					info.setEngName((String) o[6]);
					
					if (term.toString().equals(((String) o[5]))) {
						td1.add(info);
						cis = cm.getCourseIntroByDtimeOid((Integer) o[2], year, term);
						if (!cis.isEmpty()) {
							ci = cis.get(0);
							info.setLastModified(ci.getLastModified());
							dateFormat = ci.getLastModified() == null ? ""
									: sdf.format(ci.getLastModified());
							info.setDateFormat(dateFormat);
							info.setCiOid(ci.getOid());
							if (StringUtils.isNotBlank(ci.getEngName()))
								info.setEngName(ci.getEngName());
						}
					}
					
					try{
						tmp=cm.hqlGetBy("FROM DtimeClass WHERE Dtime_oid="+o[2]);
						
						for(int i=0; i<tmp.size(); i++){
							sb=new StringBuilder();
							d=(DtimeClass)tmp.get(i);
							sb.append("週"+cm.numtochinese(d.getWeek().toString(), true)+d.getBegin()+"至"+d.getEnd()+"節"+d.getPlace()+", ");
						}
						if(sb.length()>2){
							sb.delete(sb.length()-2, sb.length());
						}
						info.setDtimeClass(sb.toString());
					}catch(Exception e){
						info.setDtimeClass("未指定授課時間");
					}
				}

			} else {
				td1 = Collections.EMPTY_LIST;
			}		
			session.setAttribute(TEACHER_DTIME_NAME, td1);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		setContentPage(session, "teacher/TeachIntroduction.jsp");
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
	public ActionForward addIntroduction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("hist", "");
		List<TeachDtimeInfo> tdis = getDatasInfoByIndex(request);
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		session.setAttribute(TEACHER_COURSE_INFO, tdis);
		for (TeachDtimeInfo tdi : tdis) {
			if (tdi.getCiOid() != null) {
				request.setAttribute(COURSE_INTRO_INFO, cm
						.getCourseIntroByOid(tdi.getCiOid()));
				break;
			}
		}

		Map<String, Integer> nextYearTerm = Toolket.getNextYearTerm();
		Integer nextYear = nextYearTerm.get(IConstants.PARAMETER_SCHOOL_YEAR);
		Integer nextTerm = nextYearTerm.get(IConstants.PARAMETER_SCHOOL_TERM);
		String year = am.findTermBy(PARAMETER_SCHOOL_YEAR);
		String term = am.findTermBy(PARAMETER_SCHOOL_TERM);
		UserCredential credential = getUserCredential(session);
		List<Object[]> aList = null;
		List<String> courseData = new LinkedList<String>();
		List<String> oidData = new LinkedList<String>();
		boolean isHist = true;
		String data = null;

		for (int year1 = 96; year1 <= nextYear; year1++) {
			for (int term1 = 1; term1 <= 2; term1++) {
				if (nextYear == year1 && nextTerm == term1)
					break;
				aList = cm.getDtimeHistByTeacher(credential.getMember(), String
						.valueOf(year1), String.valueOf(term1));

				if (!aList.isEmpty()) {
					for (Object[] o : aList) {
						data = ""
								+ year1
								+ "."
								+ term1
								+ " - "
								+ (String) o[0]
								+ " "
								+ (String) o[1]
								+ " "
								+ (StringUtils.isBlank((String) o[6]) ? ""
										: (String) o[6]);
						courseData.add(data);
						oidData.add((isHist ? "O|" : "N|") + year1 + "|"
								+ term1 + "|" + ((Integer) o[2]).toString());
					}
				}
			}
		}

		// 上面查不到本學期的,所以以下面Code來抓
		aList = cm.getDtimeByTeacher(credential.getMember(), String
				.valueOf(term));
		isHist = false;
		if (!aList.isEmpty()) {
			for (Object[] o : aList) {
				data = ""
						+ year
						+ "."
						+ term
						+ " - "
						+ (String) o[0]
						+ " "
						+ (String) o[1]
						+ " "
						+ (StringUtils.isBlank((String) o[6]) ? ""
								: (String) o[6]);
				courseData.add(data);
				oidData.add((isHist ? "O|" : "N|") + year + "|" + term + "|"
						+ ((Integer) o[2]).toString());
			}
		}

		aForm.set("histData", courseData.toArray(new String[0]));
		aForm.set("histOid", oidData.toArray(new String[0]));

		setContentPage(request.getSession(false), "teacher/AddTeachIntro.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 點選"複製"時處理之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward copyIntroduction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession();
		DynaActionForm aForm = (DynaActionForm) form;
		Integer year = Integer.valueOf(aForm.getString("year"));
		Integer term = Integer.valueOf(aForm.getString("term"));
		String hist = aForm.getString("hist");

		ActionMessages messages = new ActionMessages();
		try {

			if (StringUtils.isBlank(hist)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "未選擇要複製之資料,請重新選擇,謝謝"));
				saveMessages(request, messages);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			} else {

				CourseIntroduction ci = null;
				String[] histData = hist.split("\\|");
				if ("O".equalsIgnoreCase(histData[0])) {
					Savedtime sd = cm.findSavedtimeBy(Integer
							.valueOf(histData[3]));
					ci = new CourseIntroduction();
					ci.setSchoolYear(Integer.valueOf(histData[1]));
					ci.setSchoolTerm(Integer.valueOf(histData[2]));
					ci.setDepartClass(sd.getDepartClass());
					ci.setCscode(sd.getCscode());
					ci = cm.getCourseIntroBy(ci);
				} else if ("N".equalsIgnoreCase(histData[0])) {
					Dtime d = null;
					try {
						d = cm.findDtimeBy(Integer.valueOf(histData[3]));
					} catch (Exception e) {
						log.error(e.getMessage(), e);
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Exception.generic", e
										.getMessage()
										+ " ,請稍候再試,或將問題告知給電算中心進行了解,謝謝."));
						saveErrors(request, messages);
						return mapping.findForward(IConstants.ACTION_MAIN_NAME);
					}
					ci = new CourseIntroduction();
					ci.setSchoolYear(Integer.valueOf(histData[1]));
					ci.setSchoolTerm(Integer.valueOf(histData[2]));
					ci.setDepartClass(d.getDepartClass());
					ci.setCscode(d.getCscode());
					ci = cm.getCourseIntroBy(ci);
				}

				if (ci == null) {
					messages
							.add(
									ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("Course.errorN1",
											"無法查詢到您所要複製的歷年簡介資料,因此無法進行複製,如有疑慮請電至電算中心,謝謝"));
					saveMessages(request, messages);
					return unspecified(mapping, form, request, response);
				} else {
					String[] courseEName = (String[]) aForm.get("courseEName");
					List<TeachDtimeInfo> tdis = (List<TeachDtimeInfo>) session.getAttribute(TEACHER_COURSE_INFO);
					int index = 0;
					for (TeachDtimeInfo tdi : tdis) {
						if (tdi.getCiOid() != null) {
							CourseIntroduction ciForUpdatge = cm.getCourseIntroByOid(tdi.getCiOid());
							
							
							
							ciForUpdatge.setChiIntro(ci == null
									|| ci.getChiIntro() == null ? null : ci
									.getChiIntro());
							ciForUpdatge.setEngIntro(ci == null
									|| ci.getEngIntro() == null ? null : ci
									.getEngIntro());
							ciForUpdatge.setLastModified(new Date());
							cm.txUpdateCourseIntro(ciForUpdatge);
						} else {
							try {
								cm.txAddCourseIntro(
												tdi.getOid(),
												year,
												term,
												tdi.getClassNo(),
												tdi.getCscode(),
												StringUtils.isBlank(courseEName[index]) ? "": courseEName[index],
												ci.getChiIntro() == null ? null: ci.getChiIntro(),
												ci.getEngIntro() == null ? null: ci.getEngIntro());
								index++;
							} catch (Exception e) {
								log.error(e.getMessage(), e);
								messages
										.add(
												ActionMessages.GLOBAL_MESSAGE,
												new ActionMessage(
														"Exception.generic",
														e.getMessage()
																+ " ,請稍候再試,或將問題告知給電算中心進行了解,謝謝."));
								saveErrors(request, messages);
								return mapping
										.findForward(IConstants.ACTION_MAIN_NAME);
							}
						}
					}

				}
			}

			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.CreateSuccessful"));
			return unspecified(mapping, form, request, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()
							+ " ,請稍候再試,或將問題告知給電算中心進行了解,謝謝."));
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
	}

	/**
	 * 點選查詢時處理之方法
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

		DynaActionForm aForm = (DynaActionForm) form;
		Integer year = Integer.valueOf(aForm.getString("year"));
		Integer term = Integer.valueOf(aForm.getString("term"));
		HttpSession session = request.getSession();
		// For Introduction Printing
		session.setAttribute("YearForSyllabus", year.toString());
		// For Introduction Printing
		session.setAttribute("TermForSyllabus", term.toString());
		session.removeAttribute(TEACHER_DTIME_NAME);
		session.setAttribute(SCHOOL_YEAR, year);
		boolean isHistory = false;

		initData(request, response, (DynaActionForm) form, year, term);
		try {
			UserCredential credential = getUserCredential(session);
			CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
			List<Object[]> aList = cm.getDtimeHistByTeacher(credential.getMember(), year.toString(), term.toString());
			if (!aList.isEmpty())
				isHistory = true;
			else if (Toolket.isYearTermTooOver(year, term))
				aList = Collections.EMPTY_LIST;
			else
				aList = cm.getDtimeByTeacher(credential.getMember(), term
						.toString());

			List<TeachDtimeInfo> td1 = null;
			if (!aList.isEmpty()) {
				td1 = new ArrayList<TeachDtimeInfo>();
				TeachDtimeInfo info = null;
				CourseIntroduction ci = null;
				List<CourseIntroduction> cis = null;
				int pos = 0;
				String sterm = "";
				String dateFormat = "";

				for (Object[] o : aList) {
					info = new TeachDtimeInfo();
					info.setPos(pos++);
					info.setClassName((String) o[0]);
					info.setChiName((String) o[1]);
					info.setOid((Integer) o[2]);
					info.setCscode((String) o[3]);
					info.setClassNo((String) o[4]);
					sterm = isHistory ? ((Short) o[5]).toString()
							: (String) o[5];
					info.setEngName((String) o[6]);

					if (term.toString().equals(sterm)) {
						td1.add(info);
						if (isHistory)
							cis = cm.getCourseIntroHistBy(year, term, info
									.getClassNo(), info.getCscode());
						else
							cis = cm.getCourseIntroByDtimeOid((Integer) o[2],
									year, term);
						if (!cis.isEmpty()) {
							ci = cis.get(0);
							info.setLastModified(ci.getLastModified());
							dateFormat = ci.getLastModified() == null ? ""
									: sdf.format(ci.getLastModified());
							info.setDateFormat(dateFormat);
							info.setCiOid(ci.getOid());
							if (StringUtils.isNotBlank(ci.getEngName()))
								info.setEngName(ci.getEngName());

						}
					}
				}

			} else {
				td1 = Collections.EMPTY_LIST;
			}

			session.setAttribute(TEACHER_DTIME_NAME, td1);
			// session.setAttribute(TEACHER_DTIME_NAME2, td1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd",
					Locale.TRADITIONAL_CHINESE);
			session.setAttribute("begin", sdf
					.format(Parameters.TEACH_INTRO_BEGIN.getTime()));
			session.setAttribute("end", sdf.format(Parameters.TEACH_INTRO_END
					.getTime()));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		setContentPage(session, "teacher/TeachIntroduction.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 新增中英文課程簡介時處理之方法
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

		// AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession();
		// Integer year = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
		DynaActionForm aForm = (DynaActionForm) form;
		// Integer year = Integer.valueOf(aForm.getString("year"));
		// Integer term = Integer.valueOf(aForm.getString("term"));

		Integer yearForSyllabus = Integer.valueOf((String) session
				.getAttribute("YearForSyllabus"));
		Integer termForSyllabus = Integer.valueOf((String) session
				.getAttribute("TermForSyllabus"));

		ActionMessages messages = new ActionMessages();
		try {
			String[] courseEName = (String[]) aForm.get("courseEName");
			String chiIntro = aForm.getString("chiIntro");
			String engIntro = aForm.getString("engIntro");
			List<TeachDtimeInfo> tdis = (List<TeachDtimeInfo>) session.getAttribute(TEACHER_COURSE_INFO);
			CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
			int index = 0;
			CourseIntroduction ci = null;

			for (TeachDtimeInfo tdi : tdis) {
				
				System.out.println(tdi.getCiOid());
				// 不改變Csno內資料
				// Csno csno = cm.getCourseInfoByCscode(tdi.getCscode());
				// csno.setEngName(courseEName[index++].trim());
				// cm.updateCourseNameBy(csno);
				// 如果CourseIntroduction Oid存在則Update,反則Add
				if (tdi.getCiOid() != null) {
					ci = cm.getCourseIntroByOid(tdi.getCiOid());
					ci.setSchoolYear(yearForSyllabus);
					ci.setSchoolTerm(termForSyllabus);
					ci.setDepartClass(tdi.getClassNo());
					ci.setCscode(tdi.getCscode());
					ci.setEngName(courseEName[index++].trim());
					ci.setChiIntro(chiIntro);
					ci.setEngIntro(engIntro);
					ci.setLastModified(new Date());
					cm.txUpdateCourseIntro(ci);
				} else {
					//去他x的沒dtime oid存個屌
					UserCredential c = getUserCredential(session);
					Dtime d=(Dtime) cm.hqlGetBy("FROM Dtime WHERE " +
							"departClass='"+tdi.getClassNo()+"' AND cscode='"+tdi.getCscode()+"' " +
							"AND techid='"+c.getMember().getIdno()+"' AND sterm='"+cm.getSchoolTerm()+"'").get(0);
					
					cm.txAddCourseIntro(d.getOid(), yearForSyllabus,
							termForSyllabus, tdi.getClassNo(), tdi.getCscode(),
							courseEName[index++].trim(), chiIntro, engIntro);
				}
			}

			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.CreateSuccessful"));
			return unspecified(mapping, form, request, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

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
		map.put("teacher.introduction.btn.add", "addIntroduction");
		map.put("teacher.introduction.btn.copy", "copyIntroduction");
		map.put("teacher.introductionSearch.btn.search", "search");
		map.put("OK", "save");
		map.put("Cancel", "cancel");
		return map;
	}

	// 清除Cookie
	private void initData(HttpServletRequest request,
			HttpServletResponse response, DynaActionForm form, Integer year,
			Integer term) {
		HttpSession session = request.getSession(false);
		session.removeAttribute(TEACHER_DTIME_NAME);
		form.set("term", String.valueOf(term));
		form.set("year", String.valueOf(year));
		form
				.set(
						"years",
						getYearArray(((CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME))
								.getNowBy("School_year")));
		Toolket.resetCheckboxCookie(response, TEACHER_COOKIE_NAME);
	}

	// 以Oid值取得TeachDtimeInfo物件
	@SuppressWarnings("unchecked")
	private List<TeachDtimeInfo> getDatasInfoByIndex(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request,
				TEACHER_COOKIE_NAME);
		List<TeachDtimeInfo> dataList1 = (List<TeachDtimeInfo>) session
				.getAttribute(TEACHER_DTIME_NAME);
		// List<TeachDtimeInfo> dataList2 = (List<TeachDtimeInfo>) session
		// .getAttribute(TEACHER_DTIME_NAME2);
		List<TeachDtimeInfo> aList = new ArrayList<TeachDtimeInfo>();
		for (TeachDtimeInfo tdi : dataList1) {
			if (Toolket.isValueInCookie(tdi.getOid().toString(), oids)) {
				aList.add(tdi);
			}
		}
		// if (dataList2 != null && !dataList2.isEmpty()) {
		// for (TeachDtimeInfo tdi : dataList2) {
		// if (Toolket.isValueInCookie(tdi.getOid().toString(), oids)) {
		// aList.add(tdi);
		// }
		// }
		// }
		return aList;
	}

	public static class TeachDtimeInfo implements Serializable {

		private static final long serialVersionUID = 1107880697732860874L;

		private Integer oid;
		private String classNo;
		private String className;
		private String cscode;
		private String chiName;
		private String engName;
		private Date lastModified;
		private String dateFormat;
		private Integer ciOid;
		private int pos;
		private String dtimeClass;
		
		public String getDtimeClass() {
			return dtimeClass;
		}

		public void setDtimeClass(String dtimeClass) {
			this.dtimeClass = dtimeClass;
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

		public String getEngName() {
			return engName;
		}

		public void setEngName(String engName) {
			this.engName = engName;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getCscode() {
			return cscode;
		}

		public void setCscode(String cscode) {
			this.cscode = cscode;
		}

		public int getPos() {
			return pos;
		}

		public void setPos(int pos) {
			this.pos = pos;
		}

		public Integer getCiOid() {
			return ciOid;
		}

		public void setCiOid(Integer ciOid) {
			this.ciOid = ciOid;
		}

		public Date getLastModified() {
			return lastModified;
		}

		public void setLastModified(Date lastModified) {
			this.lastModified = lastModified;
		}

		public String getDateFormat() {
			return dateFormat;
		}

		public void setDateFormat(String dateFormat) {
			this.dateFormat = dateFormat;
		}

		public String getClassNo() {
			return classNo;
		}

		public void setClassNo(String classNo) {
			this.classNo = classNo;
		}

	}

	static String[] getYearArray(String schoolYear) {
		// 從96年開始
		int baseYear = 96;
		int year = Integer.parseInt(schoolYear) + 1;
		String[] years = new String[year - baseYear + 1];
		years[years.length - 1] = String.valueOf(baseYear);
		for (int i = 0; i < years.length; i++)
			years[i] = String.valueOf((year--));
		return years;
	}

}
