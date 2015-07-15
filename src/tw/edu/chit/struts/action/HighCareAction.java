package tw.edu.chit.struts.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.util.Toolket;

public class HighCareAction extends BaseAction{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		Map initMap = new HashMap();
		
		String userName = credential.getMember().getAccount();
		String informixPass = credential.getMember().getInformixPass();
		String url = "pages/HighCareLogin.jsp";//高關懷學生輔導系統
		String linkurl = "";
		
		if (UserCredential.PrioEmployee.equals(credential.getMember().getPriority())) {	// An employee login
			linkurl = "wwwteacher";
		} else if (UserCredential.PrioStudent.equals(credential.getMember().getPriority())) {	// A student login
			linkurl = "wwwstudent";
		}

		url = url + "?username=" + userName + "&password=" + informixPass + "&linkurl=" + linkurl;
		initMap.put("username", userName);
		initMap.put("password", informixPass);
		initMap.put("linkurl", linkurl);
		initMap.put("url", url);
		log.debug("InformixLogin:url->" + url);
		session.setAttribute("InformixLogin", initMap);
		setContentPage(session, "InformixLogin.jsp");
		return mapping.findForward("Main");
	}

}
