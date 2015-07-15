package tw.edu.chit.struts.action.midp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.WwPass;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class LoginAction extends BaseAction {

	private static final String PrioEmployee = "A";

	/**
	 * 手機版點名系統教師登入
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String no = request.getParameter("no");
		String password = request.getParameter("pw");
		log.info("Username: " + no);
		log.info("Password: " + password);
		String message = "ERROR";

		try {
			MemberDAO dao = (MemberDAO) getBean("memberDAO");
			WwPass user = dao.findWWPassByAccountPassword(no, password);
			if (user != null) {
				MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
				Member empl = mm.findMemberByAccount(no);
				if (empl != null && PrioEmployee.equals(empl.getPriority())) {
					StringBuffer buffer = new StringBuffer("OK,");
					buffer.append(empl.getOid()).append(",");
					buffer.append(empl.getBirthDate()); // 民國年月日
					message = buffer.toString();
				} else {
					message = "ERROR";
				}

			} else {
				message = "ERROR";
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			message = "ERROR";
		}

		Toolket.sendPlainMessageForMIDP(request, response, message);
		return null;
	}

}
