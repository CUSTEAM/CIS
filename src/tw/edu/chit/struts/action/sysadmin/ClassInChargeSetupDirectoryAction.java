package tw.edu.chit.struts.action.sysadmin;

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

public class ClassInChargeSetupDirectoryAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping,
			 					 ActionForm form,
			 					 HttpServletRequest request,
			 					 HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		MemberDAO dao = (MemberDAO)getBean("memberDAO");
		UserCredential user = getUserCredential(session);
		List<Module> modules = dao.findModulesByParentNameMember("SysAdmin/ClassInChargeSetup", user.getMember().getOid());
		session.setAttribute("ModuleList", modules);
		session.setAttribute("DirectoryBanner", "使 用 者 負 責 班 級 設 定");
		setContentPage(session, "Directory.jsp");
		return mapping.findForward("Main");
	}
}