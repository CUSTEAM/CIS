package tw.edu.chit.struts.action.deptassist;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;

public class DirectoryAction extends BaseAction {
	
	/**
	 * 進入系務系統介面
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		MemberDAO dao = (MemberDAO) getBean(IConstants.MEMBER_DAO_BEAN_NAME);
		UserCredential user = this.getUserCredential(session);
		List<Module> modules = dao.findModulesByParentNameMember(
				"DeptAssistant", user.getMember().getOid());

		session.setAttribute(IConstants.ACTION_MODULE_LIST_NAME, modules);
		session.removeAttribute("TchScoreUploadPrint");
		session.removeAttribute("TchScoreBlankPrint");
		session.setAttribute("DirectoryBanner", "系 務 系 統 ");
		setContentPage(session, "Directory1.jsp");

		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * @comment 取得成績相關公告事項訊息
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @exception java.lang.Exception
	 */
//	@SuppressWarnings("unchecked")
//	public ActionForward execute1(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//
//		HttpSession session = request.getSession(false);
//		if (Toolket.TriggerMenuCollapse(session, "DeptAssistant")) {
//			setContentPage(session, "BulletinBoard.jsp");
//			session.setAttribute("directory", "DeptAssistant");
//		}
//		//List messageList = Toolket.findMessages("Score");
//		AdminManager manager = (AdminManager)getBean("adminManager");
//		List messageList = manager.findMessagesByCategory("DeptAssistant");
//		request.setAttribute("MessageList", messageList);
//		return mapping.findForward("Main");
//	}
	
}
