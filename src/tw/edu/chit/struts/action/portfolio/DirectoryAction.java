package tw.edu.chit.struts.action.portfolio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.Toolket;

public class DirectoryAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping,
			 					 ActionForm form,
			 					 HttpServletRequest request,
			 					 HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		if (Toolket.TriggerMenuCollapse(session, "Portfolio")) {
			setContentPage(session, "BulletinBoard.jsp");
			session.setAttribute("directory", "Portfolio");
		}
		AdminManager manager = (AdminManager)getBean("adminManager");
		List messageList = manager.findMessagesByCategory("Portfolio");
		request.setAttribute("MessageList", messageList);
		return mapping.findForward("Main");
	}
}
