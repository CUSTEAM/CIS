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
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import tw.edu.chit.model.Register;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

public class ReliefVulnerableAmountAction extends BaseLookupDispatchAction {

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
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		session.removeAttribute("regInfo");
		session.removeAttribute("registerList");

		setContentPage(session, "fee/ReliefVulnerableAmount.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

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
	@SuppressWarnings("unchecked")
	public ActionForward preview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);

		String year = request.getParameter("year");
		String sterm = request.getParameter("sterm");
		String idno = request.getParameter("txtUserInput").split(" ")[0];

		Register reg = new Register();
		reg.setSchoolYear(year);
		reg.setSchoolTerm(sterm);
		reg.setIdno(idno);
		reg.setTuitionFee(null);
		reg.setTuitionAmount(null);
		reg.setAgencyFee(null);
		reg.setAgencyAmount(null);
		reg.setReliefTuitionAmount(null);
		reg.setLoanAmount(null);
		reg.setVulnerableAmount(null);
		Example example = Example.create(reg).enableLike(MatchMode.EXACT);
		List<Register> regs = (List<Register>) am.findSQLWithCriteria(
				Register.class, example, null, null);

		if (!regs.isEmpty() && regs.size() == 1) {
			session.setAttribute("regInfo", regs.get(0));
			setContentPage(session, "fee/ReliefVulnerableAmountUpdate.jsp");
		}

		session.setAttribute("registerList", regs);
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

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
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		String regOid = aForm.getString("regOid");
		Register reg = (Register) am.getObject(Register.class, Integer
				.parseInt(regOid));

		reg.setReliefTuitionAmount(Integer.valueOf(StringUtils.defaultIfEmpty(
				aForm.getString("reliefTuitionAmount"), "0")));
		reg.setVulnerableAmount(Integer.valueOf(StringUtils.defaultIfEmpty(
				aForm.getString("vulnerableAmount"), "0")));

		try {
			am.saveObject(reg);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新成功"));
			saveMessages(request, messages);
		} catch (Exception e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新失敗"));
			saveErrors(request, messages);
		}

		setContentPage(session, "fee/ReliefVulnerableAmount.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

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
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("preview", "preview");
		map.put("fee.update.sure", "update");
		map.put("fee.back", "back");
		return map;
	}

}
