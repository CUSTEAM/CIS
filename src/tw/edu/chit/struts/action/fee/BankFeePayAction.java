package tw.edu.chit.struts.action.fee;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import tw.edu.chit.model.BankFeePay;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class BankFeePayAction extends BaseLookupDispatchAction {

	private static String BANK_FEE_PAY_LIST = "bankFeePays";
	private static String BANK_FEE_PAY_INFO = "BankFeePayInfo";

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, BANK_FEE_PAY_INFO);
		session.removeAttribute(BANK_FEE_PAY_LIST);
		aForm.initialize(mapping);

		setContentPage(session, "fee/BankFeePayInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, BANK_FEE_PAY_INFO);

		String accountNo = StringUtils.defaultIfEmpty(aForm
				.getString("accountNo"), null);
		String studentNo = StringUtils.defaultIfEmpty(aForm
				.getString("studentNo"), null);
		BankFeePay bankFeePay = new BankFeePay();
		bankFeePay.setAccountNo(accountNo);
		bankFeePay.setStudentNo(studentNo);
		Example example = Example.create(bankFeePay).ignoreCase().enableLike(
				MatchMode.ANYWHERE);
		List<BankFeePay> bankFeePaies = (List<BankFeePay>) am
				.findSQLWithCriteria(BankFeePay.class, example, null, null);
		session.setAttribute(BANK_FEE_PAY_LIST, bankFeePaies);

		setContentPage(session, "fee/BankFeePayInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		String accountNo = aForm.getString("accountNo").toUpperCase().trim();
		String studentNo = aForm.getString("studentNo").toUpperCase().trim();
		BankFeePay bankFeePay = new BankFeePay();
		bankFeePay.setAccountNo(accountNo);

		List<BankFeePay> bankFeePaies = am.findBankFeePayBy(bankFeePay);
		if (bankFeePaies.isEmpty() || bankFeePaies.size() > 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "查無此虛擬帳號,請重新輸入,謝謝"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

		Student student = new Student();
		student.setStudentNo(studentNo);
		List<Student> students = mm.findStudentsBy(student);
		if (students.isEmpty() || students.size() > 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "查無此學生學號,請重新輸入,謝謝"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

		try {
			bankFeePay = bankFeePaies.get(0);
			bankFeePay.setStudentNo(studentNo);
			am.saveObject(bankFeePay);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "新增成功"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "新增失敗"));
			saveErrors(request, messages);
		}

		return search(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String[] oids = Toolket.getSelectedIndexFromCookie(request,
				BANK_FEE_PAY_INFO).split("\\|");
		ActionMessages messages = new ActionMessages();

		try {
			for (String oid : oids) {
				if (StringUtils.isNotBlank(oid))
					am.removeObject(BankFeePay.class, Integer.valueOf(oid));
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

	public ActionForward chooseUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;

		String oid = Toolket.getSelectedIndexFromCookie(request,
				BANK_FEE_PAY_INFO).replaceAll("\\|", "");
		BankFeePay pay = (BankFeePay) am.getObject(BankFeePay.class, Integer
				.valueOf(oid));
		aForm.set("schoolYear", pay.getSchoolYear());
		aForm.set("schoolTerm", pay.getSchoolTerm());
		aForm.set("accountNo", pay.getAccountNo());
		aForm.set("studentNo", pay.getStudentNo());
		aForm.set("payDate", new SimpleDateFormat("yyyy/MM/dd").format(pay
				.getPayDate()));
		session.setAttribute("feePay4U", pay);

		setContentPage(session, "fee/BankFeePayUpdate.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		String oid = aForm.getString("feePayOid");
		ActionMessages messages = new ActionMessages();

		Student student = new Student();
		student.setStudentNo(aForm.getString("studentNo").toUpperCase().trim());
		List<Student> students = mm.findStudentsBy(student);
		if (students.isEmpty() || students.size() > 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "查無此學生學號,請重新輸入,謝謝"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

		BankFeePay pay = (BankFeePay) am.getObject(BankFeePay.class, Integer
				.valueOf(oid));
		pay.setStudentNo(students.get(0).getStudentNo().toUpperCase().trim());
		pay.setLastModified(new Date());

		try {
			am.saveObject(pay);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新成功"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新失敗,請檢查是否已有重複資料,謝謝"));
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
		map.put("fee.update.choose", "chooseUpdate");
		map.put("fee.update.sure", "update");
		map.put("fee.delete", "delete");
		map.put("fee.back", "back");
		return map;
	}

}
