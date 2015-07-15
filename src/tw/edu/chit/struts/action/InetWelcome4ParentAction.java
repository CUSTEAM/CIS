package tw.edu.chit.struts.action;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class InetWelcome4ParentAction extends BaseAction {
	
	public ActionForward execute(ActionMapping mapping,
			 					 ActionForm form,
			 					 HttpServletRequest request,
			 					 HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		/*
		DynaActionForm aForm = (DynaActionForm)form;
		aForm.set("rememberme", "");
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if (cookies[i].getName().equals("ChitUser")) {
					//request.setAttribute("NeoUser", URLDecoder.decode(cookies[i].getValue(), "UTF-8"));
					//DynaActionForm aForm = (DynaActionForm)form;
					aForm.set("username", URLDecoder.decode(cookies[i].getValue(), "UTF-8"));
					aForm.set("rememberme", "on");
					//log.debug("========> found cookie!");
					//log.debug("========> username=" + URLDecoder.decode(cookies[i].getValue(), "UTF-8"));
					break;
				}
			}
		}
		*/
		return mapping.findForward("continue");		
	}
}
