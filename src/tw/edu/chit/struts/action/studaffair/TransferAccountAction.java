package tw.edu.chit.struts.action.studaffair;

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

import tw.edu.chit.model.Dipost;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class TransferAccountAction extends BaseLookupDispatchAction {

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
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session
				.getAttribute("Credential");

		session.removeAttribute("");
		Toolket.resetCheckboxCookie(response, "");
		// UserCredential credential = getUserCredential(session);
		// Clazz[] clazz = credential.getClassInChargeAryT();

		String UserId = user.getMember().getAccount(); // 登入者編號
		session.setAttribute("school_Year", manager.getSchoolYear()); // 取得學年度
		session.setAttribute("school_Term", manager.getSchoolTerm()); // 取得學期
		session.setAttribute("TeacherName", user.getMember().getName()); // 取得登入者姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2()); // 取得登入者單位

		setContentPage(request.getSession(false),
				"studaffair/TransferAccount.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 開啟新增
	 */
	public ActionForward Create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Toolket.resetCheckboxCookie(response, "dipost");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session
				.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		session.removeAttribute("DipostList");

		String kind = aForm.getString("type");
		String studentNo = aForm.getString("studentNo");

		if (manager.ezGetString(
				"Select Oid From stmd Where student_no='" + studentNo + "'")
				.equals("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "此學生已經離校"));
			saveMessages(request, messages);
			// session.removeAttribute("RcactList");
			return mapping.findForward("Main");
		} else {
			session.setAttribute("school_Year", manager.getSchoolYear()); // 取得學年度
			session.setAttribute("school_Term", manager.getSchoolTerm()); // 取得學期
			session.setAttribute("studentNo", studentNo);
			session
					.setAttribute(
							"studentName",
							manager
									.ezGetString("Select student_name From stmd Where student_no='"
											+ studentNo + "'"));
			session.setAttribute("kind", kind);
			session.setAttribute("officeNo", manager
					.ezGetString(" Select officeNo From Dipost Where Oid="
							+ "(Select max(Oid) From Dipost Where kind='"
							+ kind + "' And studentNo='" + studentNo
							+ "' Group By studentNo)"));
			session.setAttribute("acctNo", manager
					.ezGetString(" Select acctNo From Dipost Where Oid="
							+ "(Select max(Oid) From Dipost Where kind='"
							+ kind + "' And studentNo='" + studentNo
							+ "' Group By studentNo)"));

			setContentPage(request.getSession(false),
					"studaffair/TransferAccount/TA_Insert.jsp");
			return mapping.findForward("Main");
		}
	}

	/**
	 * 存檔
	 */
	public ActionForward Save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session
				.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();

		String oids = aForm.getString("oid_s");
		if (!oids.equals("")) {
			manager.removeRcTableBy("Dipost", oids);
		}
		String idno = user.getMember().getAccount();

		String kind = aForm.getString("type");
		String schoolYear = aForm.getString("schoolYear");
		if (kind.equals("3")) {
			schoolYear = aForm.getString("year");
		}
		String schoolTerm = aForm.getString("schoolTerm");
		if (kind.equals("3")) {
			schoolTerm = aForm.getString("month");
		}
		String studentNo = aForm.getString("studentNo");
		String officeNo = aForm.getString("officeNo");
		String acctNo = aForm.getString("accountNo");
		String pMoney = aForm.getString("money");

		// String leotest =
		// manager.ezGetBy("Select Oid From stmd Where student_no='"+studentNo+"'");

		if (manager.ezGetString(
				"Select Oid From stmd Where student_no='" + studentNo + "'")
				.equals("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "此學生已經離校"));
			saveMessages(request, messages);
			// session.removeAttribute("RcactList");
			return mapping.findForward("Main");
		} else {
			Dipost dipost = new Dipost();

			dipost.setKind(kind);
			dipost.setSchoolYear(schoolYear);
			dipost.setSchoolTerm(schoolTerm);
			dipost.setStudentNo(studentNo);
			dipost.setOfficeNo(officeNo);
			dipost.setAcctNo(acctNo);
			dipost.setMoney(Integer.parseInt(pMoney));
			dipost.setLastModified(new Date());

			manager.updateObject(dipost);

			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "資料存檔完成"));
			saveMessages(request, messages);

			session.removeAttribute("DipostList");
			setContentPage(request.getSession(false),
					"studaffair/TransferAccount.jsp");
			return mapping.findForward("Main");
		}
	}

	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Toolket.resetCheckboxCookie(response, "dipost");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session
				.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;

		String kind = aForm.getString("type");
		String schoolYear = aForm.getString("schoolYear");
		if (kind.equals("3")) {
			schoolYear = aForm.getString("year");
		}
		String schoolTerm = aForm.getString("schoolTerm");
		if (kind.equals("3")) {
			schoolTerm = aForm.getString("month");
		}
		String studentNo = aForm.getString("studentNo");

		String sqlYear = "And D.schoolYear like ('%" + schoolYear + "%')";
		String sqlTerm = "And D.schoolTerm like ('%" + schoolTerm + "%')";
		String sqlStmdNo = "And D.studentNo like ('%" + studentNo + "%')";

		if (schoolYear.equals("")) {
			sqlYear = "";
		}
		if (schoolTerm.equals("")) {
			sqlTerm = "";
		}
		if (studentNo.equals("")) {
			sqlStmdNo = "";
		}

		List<Dipost> dipost = manager
				.ezGetBy(" Select D.Oid, D.`schoolYear`, D.`schoolTerm`, C.`ClassName`, D.`studentNo`, S.`student_name`, "
						+ " D.`officeNo`, D.`acctNo`, D.`pMoney`, S.idno "
						+ " From `Dipost` D, stmd S, Class C "
						+ " Where D.`studentNo` = S.`student_no` "
						+ " And S.`depart_class` = C.`ClassNo` "
						+ " And D.kind like ('%"
						+ kind
						+ "%') "
						+ sqlYear
						+ sqlTerm + sqlStmdNo);

		session.setAttribute("DipostList", dipost);
		session.setAttribute("Tab", "	");
		session
				.setAttribute(
						"My_No",
						manager
								.ezGetString("Select Count(D.Oid) From `Dipost` D, stmd S, Class C  "
										+ "Where D.`studentNo` = S.`student_no` "
										+ "  And S.`depart_class` = C.`ClassNo` "
										+ "  And D.kind like ('%"
										+ kind
										+ "%') "
										+ sqlYear
										+ sqlTerm
										+ sqlStmdNo));
		session
				.setAttribute(
						"Money",
						manager
								.ezGetString("Select Sum(D.pMoney) From `Dipost` D, stmd S, Class C  "
										+ "Where D.`studentNo` = S.`student_no` "
										+ "  And S.`depart_class` = C.`ClassNo` "
										+ "  And D.kind like ('%"
										+ kind
										+ "%') "
										+ sqlYear
										+ sqlTerm
										+ sqlStmdNo));
		session.setAttribute("kind", kind);

		setContentPage(request.getSession(false),
				"studaffair/TransferAccount.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 返回
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session
				.getAttribute("Credential");

		session.removeAttribute("");
		Toolket.resetCheckboxCookie(response, "");

		session.setAttribute("school_Year", manager.getSchoolYear()); // 取得學年度
		session.setAttribute("school_Term", manager.getSchoolTerm()); // 取得學期

		setContentPage(request.getSession(false),
				"studaffair/TransferAccount.jsp");
		return mapping.findForward("Main");

	}

	/**
	 * 開啟修改
	 */
	public ActionForward Modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session
				.getAttribute("Credential");
		session.removeAttribute("DipostList");

		String oids = Toolket.getSelectedIndexFromCookie(request, "dipost");
		String oid_s = oids.substring(1, oids.length() - 1);

		String studentNo = manager
				.ezGetString("Select studentNo From Dipost Where Oid='" + oid_s
						+ "'");

		session.setAttribute("oid_s", oid_s);
		session.setAttribute("kind", manager
				.ezGetString("Select kind From Dipost Where Oid='" + oid_s
						+ "'"));
		session.setAttribute("studentNo", studentNo);
		session.setAttribute("studentName", manager
				.ezGetString("Select student_name From stmd Where student_no='"
						+ studentNo + "'"));
		session.setAttribute("school_Year", manager
				.ezGetString("Select schoolYear From Dipost Where Oid='"
						+ oid_s + "'"));
		session.setAttribute("school_Term", manager
				.ezGetString("Select schoolTerm From Dipost Where Oid='"
						+ oid_s + "'"));
		session.setAttribute("officeNo", manager
				.ezGetString("Select officeNo From Dipost Where Oid='" + oid_s
						+ "'"));
		session.setAttribute("acctNo", manager
				.ezGetString("Select acctNo From Dipost Where Oid='" + oid_s
						+ "'"));
		session.setAttribute("money", manager
				.ezGetString("Select pMoney From Dipost Where Oid='" + oid_s
						+ "'"));

		setContentPage(request.getSession(false),
				"studaffair/TransferAccount/TA_Insert.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 刪除
	 */
	public ActionForward Delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session
				.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "dipost");

		String oids = Toolket.getSelectedIndexFromCookie(request, "dipost");
		String oid_s = oids.substring(1, oids.length() - 1);

		manager.removeRcTableBy("Dipost", oid_s);

		session.removeAttribute("DipostList");
		setContentPage(request.getSession(false),
				"studaffair/TransferAccount.jsp");
		return mapping.findForward("Main");

	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create", "Create");
		map.put("Save", "Save");
		map.put("Query", "Query");
		map.put("Modify", "Modify");
		map.put("Back", "Back");
		map.put("Modify", "Modify");
		map.put("Delete", "Delete");
		return map;
	}

}
