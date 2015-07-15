package tw.edu.chit.struts.action.portfolio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.struts.action.BaseAction;
/**
 * OT sso
 * @author shawn
 *
 */
public class REDirectoryOTAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		
		request.setAttribute("username", c.getMember().getAccount());
		request.setAttribute("password", c.getMember().getPassword());

		
		return mapping.findForward("REDirectoryOT");
	}
}
