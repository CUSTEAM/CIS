package tw.edu.chit.service.impl.exception;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

public class NullPointerExceptionHandler extends ExceptionHandler {

	private static Logger log = Logger
			.getLogger(NullPointerExceptionHandler.class);

	/**
	 * 多餘的
	 * 負責處理NullPointerException之Handler
	 */
	@Override
	public ActionForward execute(Exception e, ExceptionConfig ec,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		String messageKey = ec.getKey();
		ActionMessage error = new ActionMessage(messageKey);
		if (e instanceof NullPointerException) {
			NullPointerException npe = (NullPointerException) e;
			log.error("NullPointerExceptionHandler->" + npe.getMessage(), npe);
		} else if (e instanceof ServletException) {
			ServletException se = (ServletException) e;
			log.error("NullPointerExceptionHandler->" + se.getMessage(), se);
		} else {
			log.error("NullPointerExceptionHandler->" + e.getMessage(), e);
		}
		ActionForward forward = new ActionForward(ec.getPath());
		forward.setRedirect(true);
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
		storeException(request, error.getKey(), error, forward, ec.getScope());
		return forward;
	}

	@Override
	protected void storeException(HttpServletRequest request, String property,
			ActionMessage error, ActionForward forward, String score) {

		if ("request".equals(score))
			request.setAttribute(ActionMessages.GLOBAL_MESSAGE, error);
		else
			request.getSession(false).setAttribute(
					ActionMessages.GLOBAL_MESSAGE, error);
	}

}
