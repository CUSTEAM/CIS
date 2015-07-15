package tw.edu.chit.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.LookupDispatchAction;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.domain.UserCredential;

public abstract class BaseLookupDispatchAction extends LookupDispatchAction {

	private   ApplicationContext ac = null;
	protected final Log log = LogFactory.getLog(getClass());

	protected Object getBean(String name) {
		return ac.getBean(name);
	}

	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		ac = WebApplicationContextUtils.getWebApplicationContext(
					actionServlet.getServletContext());
	}

	protected void setContentPage(HttpServletRequest request, String page) {
		request.setAttribute("contentPage", page);
	}

	protected void setContentPage(HttpSession session, String page) {
		session.setAttribute("contentPage", page);
	}

	protected UserCredential getUserCredential(HttpSession session) {
		return (UserCredential)session.getAttribute("Credential");
	}
}
