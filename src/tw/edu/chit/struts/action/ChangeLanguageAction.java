package tw.edu.chit.struts.action;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import tw.edu.chit.service.CourseManager;
/**
 * 語言管理
 * @author JOHN
 *
 */
public class ChangeLanguageAction extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("COU.Login.LanguageSel"));
		
		saveMessages(request, msg);
		*/
		String l=request.getParameter("locale");
		Locale locale = new Locale(l);
		request.getSession().setAttribute(Globals.LOCALE_KEY,locale);
	    HttpSession session = request.getSession(true);
	    session.setAttribute("locale", l);	    
	    
	    Cookie cookie=new Cookie("language", l);
	    cookie.setMaxAge(3600*24*365);
		response.addCookie(cookie);
		
	    if(session.getAttribute("Credential")==null){
	    	return mapping.findForward("Welcome");
	    }
		return mapping.findForward("Main");
	    //return mapping.getInputForward();
	}
}
