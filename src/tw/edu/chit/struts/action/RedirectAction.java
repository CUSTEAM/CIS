package tw.edu.chit.struts.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 跨專案重新導向
 * @author John
 *
 */
public class RedirectAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		/*
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		
		*/
		//HttpSession session = request.getSession(false);
		//session.setAttribute("xuser", "蕭國裕");
		//System.out.println(session.getAttribute("xuser"));
		response.sendRedirect(request.getParameter("path"));
		return null;
	}
}
