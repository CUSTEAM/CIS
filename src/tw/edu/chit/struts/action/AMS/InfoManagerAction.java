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

public class InfoManagerAction extends BaseAction{

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
				"AMS/InfoManager", user.getMember().getOid());
		//System.out.println(dao);
		session.setAttribute(MODULE_LIST_NAME, modules);
		//session.removeAttribute("TchScoreUploadPrint");
		//session.removeAttribute("TchScoreBlankPrint");
		session.setAttribute("DirectoryBanner", "差 勤 設 定 維 護");
		setContentPage(session, "Directory1.jsp");

		return mapping.findForward("Main");
	}

}
