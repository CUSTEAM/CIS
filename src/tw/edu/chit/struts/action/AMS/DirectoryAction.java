package tw.edu.chit.struts.action.AMS;

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
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		MemberDAO dao = (MemberDAO) getBean(IConstants.MEMBER_DAO_BEAN_NAME);
		UserCredential user = this.getUserCredential(session);
		List<Module> modules = dao.findModulesByParentNameMember("AMS", user
				.getMember().getOid());

		session.setAttribute(IConstants.ACTION_MODULE_LIST_NAME, modules);
		session.removeAttribute("TchScoreUploadPrint");
		session.removeAttribute("TchScoreBlankPrint");
		session.setAttribute("DirectoryBanner", "差  勤  系  統 ");
		setContentPage(session, "Directory1.jsp");

		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}
	
	/*
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {

		HttpSession session = request.getSession(false);
		if (Toolket.TriggerMenuCollapse(session, "AMS")) {
			setContentPage(session, "BulletinBoard.jsp");
			session.setAttribute("directory", "AMS");
		}
		
		AdminManager manager = (AdminManager)getBean("adminManager");
		List messageList = manager.findMessagesByCategory("AMS");
		request.setAttribute("MessageList", messageList);
		return mapping.findForward("Main");
	}
	*/

}
