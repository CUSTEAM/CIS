package tw.edu.chit.struts.action.fee;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

import tw.edu.chit.model.Dipost;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class StdTransferAccountAction extends BaseLookupDispatchAction {

	private static String DIPOST_LIST = "diposts";
	private static String DIPOST_INFO = "dipostInfo";

	/**
	 * 進入轉帳資料輸入畫面
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

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		session.removeAttribute(DIPOST_LIST);
		Toolket.resetCheckboxCookie(response, DIPOST_INFO);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		aForm.set("schoolYear", year);
		aForm.set("schoolTerm", term);
		aForm.set("years", Toolket.getYearArray(year, 5));

		setContentPage(session, "fee/StdTransferAccount.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		Toolket.resetCheckboxCookie(response, DIPOST_INFO);
		DynaActionForm aForm = (DynaActionForm) form;

		String year = StringUtils.defaultIfEmpty(aForm.getString("schoolYear"),
				null);
		String term = StringUtils.defaultIfEmpty(aForm.getString("schoolTerm"),
				null);
		boolean isYearAndTermNull = StringUtils.isBlank(year)
				&& StringUtils.isBlank(term);

		String hql = "SELECT d.*, s.student_name AS NAME1, g.student_name AS NAME2, "
				+ "s.depart_class AS DC1, g.depart_class AS DC2 "
				+ "FROM Dipost d LEFT JOIN stmd s ON d.StudentNo = s.student_no "
				+ "LEFT JOIN Gstmd g ON d.StudentNo = g.student_no "
				+ "WHERE (s.depart_class LIKE ? OR g.depart_class LIKE ?) ";
		Object[] params = { processClassInfo(aForm) + "%",
				processClassInfo(aForm) + "%" };

		if (StringUtils.isNotBlank(aForm.getString("kind"))) {
			hql += "AND d.Kind = ? ";
			params = (Object[]) ArrayUtils.add(params, aForm.getString("kind"));
		}

		if (!isYearAndTermNull) {
			if (StringUtils.isNotBlank(year)) {
				hql += "AND d.SchoolYear = ? ";
				params = (Object[]) ArrayUtils.add(params, year);
			}

			if (StringUtils.isNotBlank(term)) {
				hql += "AND d.SchoolTerm = ? ";
				params = (Object[]) ArrayUtils.add(params, term);
			}
		}

		List<Map> results = am.findBySQL(hql, params);
		List<Dipost> diposts = new LinkedList<Dipost>();

		Dipost dipost = null;
		String studentName = null, departClass = null;
		for (Map data : results) {
			dipost = new Dipost();
			dipost.setOid(Integer.valueOf(((Long) data.get("Oid")).toString()));
			dipost.setStudentNo((String) data.get("StudentNo"));
			studentName = data.get("NAME1") != null ? (String) data
					.get("NAME1") : (String) data.get("NAME2");
			departClass = data.get("DC1") != null ? (String) data.get("DC1")
					: (String) data.get("DC2");
			dipost.setStudentName(studentName);
			dipost.setDepartClass(Toolket.getClassFullName(departClass));
			dipost.setOfficeNo((String) data.get("OfficeNo"));
			dipost.setAcctNo((String) data.get("AcctNo"));
			dipost.setMoney((Integer) data.get("Money"));
			dipost.setLastModified((Date) data.get("LastModified"));
			dipost.setKindName(Toolket.getTransferKind((String) data
					.get("Kind")));
			diposts.add(dipost);
		}
		
		
//		if (!results.isEmpty()) {
//
//			Dipost dipost = null;
//			Student student = null;
//			for (Object[] o : results) {
//				dipost = (Dipost) o[0];
//				student = (Student) o[1];
//
//				dipost.setStudentName(student.getStudentName());
//				dipost.setDepartClass(Toolket.getClassFullName(student
//						.getDepartClass()));
//				dipost.setKindName(Toolket.getTransferKind(dipost.getKind()));
//				diposts.add(dipost);
//			}
//		} else {
//			hql = "FROM Dipost d, Graduate s WHERE d.studentNo = s.studentNo "
//					+ "AND s.departClass LIKE ? ";
//			Object[] params4G = { processClassInfo(aForm) + "%" };
//
//			if (StringUtils.isNotBlank(aForm.getString("kind"))) {
//				hql += "AND d.kind = ? ";
//				params4G = (Object[]) ArrayUtils.add(params4G, aForm
//						.getString("kind"));
//			}
//
//			if (!isYearAndTermNull) {
//				if (StringUtils.isNotBlank(year)) {
//					hql += "AND d.schoolYear = ? ";
//					params4G = (Object[]) ArrayUtils.add(params4G, year);
//				}
//
//				if (StringUtils.isNotBlank(term)) {
//					hql += "AND d.schoolTerm = ? ";
//					params4G = (Object[]) ArrayUtils.add(params4G, term);
//				}
//			}
//
//			results = am.find(hql, params);
//			if (!results.isEmpty()) {
//
//				Dipost dipost = null;
//				Graduate graduate = null;
//				for (Object[] o : results) {
//					dipost = (Dipost) o[0];
//					graduate = (Graduate) o[1];
//
//					dipost.setStudentName(graduate.getStudentName());
//					dipost.setDepartClass(Toolket.getClassFullName(graduate
//							.getDepartClass()));
//					dipost.setKindName(Toolket
//							.getTransferKind(dipost.getKind()));
//					diposts.add(dipost);
//				}
//			}
//		}
		session.setAttribute(DIPOST_LIST, diposts);

		setContentPage(session, "fee/StdTransferAccount.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward chooseAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		setContentPage(request.getSession(false),
				"fee/StdTransferAccountAdd.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		// DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		String studentNo = request.getParameter("txtUserInput").split(" ")[0]
				.toUpperCase();

		Student student = mm.findStudentByNo(studentNo);
		if (student != null) {
			Dipost dipost = new Dipost();
			dipost.setSchoolYear(year);
			dipost.setSchoolTerm(term);
			dipost.setStudentNo(studentNo);
			dipost.setOfficeNo(request.getParameter("officeNo").trim()
					.toUpperCase());
			dipost.setAcctNo(request.getParameter("accountNo").trim()
					.toUpperCase());
			dipost.setMoney(Integer.parseInt(request.getParameter("money")
					.trim()));
			dipost.setKind(request.getParameter("type"));
			dipost.setModifier(getUserCredential(request.getSession(false))
					.getMember().getIdno());
			dipost.setLastModified(new Date());

			try {

				am.saveObject(dipost);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新增成功"));
				saveMessages(request, messages);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新增失敗,請檢查是否重複輸入,或請電洽電算中心,謝謝"));
				saveErrors(request, messages);
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "查無學生基本資料"));
			saveMessages(request, messages);
			return chooseAdd(mapping, form, request, response);
		}

		return search(mapping, form, request, response);
	}

	public ActionForward chooseUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String oid = Toolket.getSelectedIndexFromCookie(request, DIPOST_INFO)
				.replaceAll("\\|", "");
		Dipost dipost = (Dipost) am.getObject(Dipost.class, Integer
				.valueOf(oid));
		session.setAttribute("dipost4U", dipost);

		setContentPage(session, "fee/StdTransferAccountUpdate.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		String oid = aForm.getString("dipostOid");
		ActionMessages messages = new ActionMessages();

		Dipost dipost = (Dipost) am.getObject(Dipost.class, Integer
				.valueOf(oid));
		dipost.setOfficeNo(aForm.getString("officeNo").trim().toUpperCase());
		dipost.setAcctNo(aForm.getString("accountNo").trim().toUpperCase());
		dipost.setMoney(Integer.parseInt(aForm.getString("money").trim()));
		dipost.setKind(aForm.getString("type"));
		dipost.setModifier(getUserCredential(request.getSession(false))
				.getMember().getIdno());
		dipost.setLastModified(new Date());

		try {
			am.saveObject(dipost);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新成功"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新失敗"));
			saveErrors(request, messages);
		}

		return search(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String[] oids = Toolket
				.getSelectedIndexFromCookie(request, DIPOST_INFO).split("\\|");
		ActionMessages messages = new ActionMessages();

		try {
			for (String oid : oids) {
				if (StringUtils.isNotBlank(oid)) {
					am.removeObject(Dipost.class, Integer.valueOf(oid));
				}
			}

			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "刪除成功"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "刪除失敗"));
			saveErrors(request, messages);
		}

		return search(mapping, form, request, response);
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return search(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fee.class.search", "search");
		map.put("fee.add.choose", "chooseAdd");
		map.put("fee.add.sure", "add");
		map.put("fee.update.choose", "chooseUpdate");
		map.put("fee.update.sure", "update");
		map.put("fee.delete", "delete");
		map.put("fee.back", "back");
		return map;
	}

	private String processClassInfo(DynaActionForm form) {

		String campusInCharge2 = (String) form.get("campusInCharge2");
		String schoolInCharge2 = (String) form.get("schoolInCharge2");
		String deptInCharge2 = (String) form.get("deptInCharge2");
		String departClass = (String) form.get("classInCharge2");
		schoolInCharge2 = schoolInCharge2.equalsIgnoreCase("All") ? "": schoolInCharge2;
		deptInCharge2 = deptInCharge2.equalsIgnoreCase("All") ? "": deptInCharge2;
		departClass = departClass.equalsIgnoreCase("All") ? "" : departClass;
		return departClass.length() == 6 ? departClass : campusInCharge2+ schoolInCharge2 + deptInCharge2;
	}

}
