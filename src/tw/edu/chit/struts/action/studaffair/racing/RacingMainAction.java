package tw.edu.chit.struts.action.studaffair.racing;

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

public class RacingMainAction  extends BaseAction {
	public static final String MODULE_LIST_NAME = "ModuleList";

	/**
	 * @comment 負責取得學務批次作業子Modules並顯示
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		MemberDAO dao = (MemberDAO) getBean("memberDAO");
		UserCredential user = this.getUserCredential(session);
		// Level 2之uri為Score/ScoreMain
		List<Module> modules = dao.findModulesByParentNameMember(
				"StudAffair/Racing", user.getMember().getOid());
		session.setAttribute(MODULE_LIST_NAME, modules);
		session.setAttribute("DirectoryBanner", "生 活 教 育 競 賽");
		setContentPage(session, "Directory.jsp");

		return mapping.findForward("Main");
	}

}
