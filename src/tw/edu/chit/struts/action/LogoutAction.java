package tw.edu.chit.struts.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogoutAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		if(session!=null){
			session.invalidate();
		}
		Cookie cookie = new Cookie("userid", "deleted");
		cookie.setMaxAge(0);
    	cookie.setDomain(".cust.edu.tw");
    	cookie.setPath("/");
    	response.addCookie(cookie);
		response.sendRedirect("/ssos/Logout");
		return null;
	}
}
