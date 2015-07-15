package tw.edu.chit.struts.action.teacher;

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
	 * @comment 取得教師相關公告事項訊息
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @exception java.lang.Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		
		
		if (Toolket.TriggerMenuCollapse(session, "Teacher")) {
			session.setAttribute("directory", "Teacher");
			setContentPage(session, "BulletinBoard.jsp");
		}
		
		
		
		AdminManager manager = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		List messageList = manager.findMessagesByCategory("Teacher");
		request.setAttribute("MessageList", messageList);
		return mapping.findForward("Main");
	}
}
