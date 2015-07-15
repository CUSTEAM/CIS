package tw.edu.chit.struts.action.teacher;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_YEAR;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class TeachIntroductionSearchAction extends BaseLookupDispatchAction {

	public static final String TEACHER_DTIME_NAME1 = "teacherDtime1";
	public static final String TEACHER_DTIME_NAME2 = "teacherDtime2";
	public static final String TEACHER_COOKIE_NAME = "TeachDtimeInfo";
	public static final String TEACHER_COURSE_INFO = "courseInfo";
	public static final String COURSE_INTRO_INFO = "courseIntroInfo";
	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/MM/dd");

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
		// Integer term = "1".equals(am.findTermBy(PARAMETER_SCHOOL_TERM)) ? 2 :
		// 1;
		Integer term = Integer.valueOf(am.findTermBy(PARAMETER_SCHOOL_TERM));
		session.setAttribute(SCHOOL_YEAR, year);

		clearData(request, response, (DynaActionForm) form, term);
		try {
			UserCredential credential = getUserCredential(session);
			CourseManager cm = (CourseManager) getBean("courseManager");
			List<Object[]> aList = cm.getDtimeByTeacher(credential.getMember());
			List<TeachDtimeInfo> td1 = null, td2 = null;
			List<CourseIntroduction> cis = null;

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
					info.setOid((Integer) o[2]);
					info.setCscode((String) o[3]);
					info.setEngName((String) o[6]);

					if ("1".equals(((String) o[5]))) {
						td1.add(info);
						// FIXME
						cis = cm.getCourseIntroHistBy(year, 1, (String) o[4],
								(String) o[3]);
						if (!cis.isEmpty()) {
							info.setLastModified(cis.get(0).getLastModified());
							String dateFormat = cis.get(0).getLastModified() == null ? ""
									: sdf.format(cis.get(0).getLastModified());
							info.setDateFormat(dateFormat);
							info.setCiOid(cis.get(0).getOid());
						}
					} else {
						td2.add(info);
						// FIXME
						cis = cm.getCourseIntroHistBy(year, 2, (String) o[4],
								(String) o[3]);
						if (!cis.isEmpty()) {
							info.setLastModified(cis.get(0).getLastModified());
							String dateFormat = cis.get(0).getLastModified() == null ? ""
									: sdf.format(cis.get(0).getLastModified());
							info.setDateFormat(dateFormat);
							info.setCiOid(cis.get(0).getOid());
						}
					}
				}

			} else {
				td1 = Collections.EMPTY_LIST;
				td2 = Collections.EMPTY_LIST;
			}

			session.setAttribute(TEACHER_DTIME_NAME1, td1);
			session.setAttribute(TEACHER_DTIME_NAME2, td2);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("Main");
		}
		setContentPage(request.getSession(false),
				"teacher/TeachIntroductionSearch.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 點選"查詢"時處理之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		List<TeachDtimeInfo> aList = getDatasInfoByIndex(request);
		CourseManager cm = (CourseManager) getBean("courseManager");

		TeachDtimeInfo tdi = aList.get(0);
		session.setAttribute(TEACHER_COURSE_INFO, tdi);
		if (tdi.getCiOid() != null)
			request.setAttribute(COURSE_INTRO_INFO, cm.getCourseIntroByOid(tdi
					.getCiOid()));

		setContentPage(request.getSession(false), "teacher/TeachIntroInfo.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 處理點選返回之方法
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
		map.put("teacher.introductionSearch.btn.search", "search");
		map.put("Back", "back");
		return map;
	}

	// 清除Cookie
	private void clearData(HttpServletRequest request,
			HttpServletResponse response, DynaActionForm form, Integer term) {
		HttpSession session = request.getSession(false);
		session.removeAttribute(TEACHER_DTIME_NAME1);
		session.removeAttribute(TEACHER_DTIME_NAME2);
		form.set("term", String.valueOf(term));
		Toolket.resetCheckboxCookie(response, TEACHER_COOKIE_NAME);
	}

	// 以Oid值取得TeachDtimeInfo物件
	@SuppressWarnings("unchecked")
	private List<TeachDtimeInfo> getDatasInfoByIndex(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request,
				TEACHER_COOKIE_NAME);
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

}
