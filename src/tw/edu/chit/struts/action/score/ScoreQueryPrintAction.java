package tw.edu.chit.struts.action.score;

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

public class ScoreQueryPrintAction extends BaseAction {
	public static final String MODULE_LIST_NAME = "ModuleList";

	/**
	 * @comment 負責將成績查詢列印作業內子Module清單取出並顯示
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		MemberDAO dao = (MemberDAO) getBean("memberDAO");
		UserCredential user = this.getUserCredential(session);
		List<Module> modules = dao.findModulesByParentNameMember(
				"Score/ScoreQuery", user.getMember().getOid());
		session.setAttribute(MODULE_LIST_NAME, modules);
		session.setAttribute("DirectoryBanner", "成 績 查 詢 列 印 作 業");
		setContentPage(session, "Directory1.jsp");

		return mapping.findForward("Main");
	}

}
