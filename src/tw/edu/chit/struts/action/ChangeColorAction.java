package tw.edu.chit.struts.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import tw.edu.chit.service.CourseManager;
/**
 * 模版管理
 * @author JOHN
 *
 */
public class ChangeColorAction extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		
		String color=request.getParameter("color");
		String home="home_blue";
		String decorate="home_blue";
		
		Cookie cookie=new Cookie("home", home);
		cookie.setMaxAge(0);
		
		cookie=new Cookie("decorate", decorate);
		cookie.setMaxAge(0);
		
		if(color.equals("blue")){
			home="home_blue";
			decorate="decorate_blue";
		}
		
		if(color.equals("pink")){
			home="home_pink";
			decorate="decorate_pink";
		}
		
		if(color.equals("green")){
			home="home";
			decorate="decorate";
		}
		
		if(color.equals("orange")){
			home="home_orange";
			decorate="decorate_orange";
		}
		
		if(color.equals("grey")){
			home="home_grey";
			decorate="decorate_grey";
		}
		
		session.setAttribute("home", home);
		session.setAttribute("decorate", decorate);
		
		cookie=new Cookie("home", home);
		cookie.setMaxAge(3600*24*365);
		response.addCookie(cookie);
		
		cookie=new Cookie("decorate", decorate);
		cookie.setMaxAge(3600*24*365);
		response.addCookie(cookie);
		
		/*
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",color+"!"));
		saveMessages(request, msg);
		*/
		
		if( session.getAttribute("Menu")!=null){
			return mapping.findForward("Main");
		}
		
		return mapping.getInputForward();
	}
}
