package tw.edu.chit.struts.action.research;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class DirectoryAction extends BaseAction {

	/**
	 * 進入研究發展處介面
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		if (Toolket.TriggerMenuCollapse(session, "Research")) {
			setContentPage(session, "BulletinBoard.jsp");
			session.setAttribute("directory", "Score");
		}
		AdminManager manager = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		List messageList = manager.findMessagesByCategory("Research");
		request.setAttribute("MessageList", messageList);
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

}
