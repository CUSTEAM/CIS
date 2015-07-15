package tw.edu.chit.struts.action.studaffair;
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

public class StudAbsenceManagerAction extends BaseAction {
		
		public static final String MODULE_LIST_NAME = "ModuleList";
		
		/**
		 * 
		 */	
		@Override
		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			HttpSession session = request.getSession(false);
			MemberDAO dao = (MemberDAO) getBean("memberDAO");
			UserCredential user = this.getUserCredential(session);
			List<Module> modules = dao.findModulesByParentNameMember(
					"StudAbsence", user.getMember().getOid());

			session.setAttribute(MODULE_LIST_NAME, modules);
			//session.removeAttribute("TchScoreUploadPrint");
			//session.removeAttribute("TchScoreBlankPrint");
			session.setAttribute("DirectoryBanner", "學 生 請 假 管 理 系 統 ");
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
