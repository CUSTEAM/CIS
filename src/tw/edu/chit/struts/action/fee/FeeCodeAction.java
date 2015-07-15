package tw.edu.chit.struts.action.fee;

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

import tw.edu.chit.model.FeeCode;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class FeeCodeAction extends BaseLookupDispatchAction {

	private static String FEE_CODE_INFO = "FeeCodeInfo";

	/**
	 * 
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
		Toolket.resetCheckboxCookie(response, FEE_CODE_INFO);
		List<FeeCode> codes = am.findFeeCodeBy(new FeeCode());
		session.setAttribute("feeCodes", codes);

		setContentPage(session, "fee/FeeCodeInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward chooseAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		((DynaActionForm) form).initialize(mapping);
		setContentPage(request.getSession(false), "fee/FeeCodeAdd.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		FeeCode code = new FeeCode();
		code.setNo(aForm.getString("feeNo").toUpperCase().trim());
		List<FeeCode> codes = am.findFeeCodeBy(code);

		if (codes.isEmpty()) {
			code.setName(aForm.getString("feeName").toUpperCase().trim());
			try {
				am.saveObject(code);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新增成功"));
				saveMessages(request, messages);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新增失敗"));
				saveErrors(request, messages);
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "輸入的代碼重複,請重新輸入,謝謝"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

		return unspecified(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String[] oids = Toolket.getSelectedIndexFromCookie(request,
				FEE_CODE_INFO).split("\\|");
		ActionMessages messages = new ActionMessages();

		try {
			for (String oid : oids) {
				if (StringUtils.isNotBlank(oid))
					am.removeObject(FeeCode.class, Integer.valueOf(oid));
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

		return unspecified(mapping, form, request, response);
	}

	public ActionForward chooseUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String oid = Toolket.getSelectedIndexFromCookie(request, FEE_CODE_INFO)
				.replaceAll("\\|", "");
		FeeCode code = (FeeCode) am.getObject(FeeCode.class, Integer
				.valueOf(oid));
		session.setAttribute("feeCode4U", code);

		setContentPage(session, "fee/FeeCodeUpdate.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		String oid = aForm.getString("feeOid");
		ActionMessages messages = new ActionMessages();

		FeeCode code = (FeeCode) am.getObject(FeeCode.class, Integer
				.valueOf(oid));
		code.setName(aForm.getString("feeName").toUpperCase().trim());
		try {
			am.saveObject(code);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新成功"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新失敗"));
			saveErrors(request, messages);
		}

		return unspecified(mapping, form, request, response);
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fee.add.choose", "chooseAdd");
		map.put("fee.add.sure", "add");
		map.put("fee.update.choose", "chooseUpdate");
		map.put("fee.update.sure", "update");
		map.put("fee.delete", "delete");
		map.put("fee.back", "back");
		return map;
	}

}
