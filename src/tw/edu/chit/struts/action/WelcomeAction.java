package tw.edu.chit.struts.action;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.util.Toolket;

public class WelcomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		HttpSession session = request.getSession(false);		
		if (session != null) { session.invalidate(); }		
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("rememberme", "");
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("ChitUser")) {
					aForm.set("username", URLDecoder.decode(cookies[i].getValue(), "UTF-8"));
					aForm.set("rememberme", "on");
					break;
				}
			}
		}
		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
				
		return mapping.findForward("continue");
		*/
		response.sendRedirect("/tis/");
		return null;
	}
}
