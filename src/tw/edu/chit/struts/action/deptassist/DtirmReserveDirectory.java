package tw.edu.chit.struts.action.deptassist;

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

public class DtirmReserveDirectory extends BaseAction {

	public ActionForward execute(ActionMapping mapping,
			 					 ActionForm form,
			 					 HttpServletRequest request,
			 					 HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		if (Toolket.TriggerMenuCollapse(session, "DeptAssistant/DtirmReserve")) {
			setContentPage(session, "BulletinBoard.jsp");
			session.setAttribute("directory", "DeptAssistant/DtirmReserve");
		}
		
		AdminManager manager = (AdminManager)getBean("adminManager");
		List messageList = manager.findMessagesByCategory("DeptAssistant/DtirmReserve");
		request.setAttribute("MessageList", messageList);
		
		return mapping.findForward("Main");
	}
}
