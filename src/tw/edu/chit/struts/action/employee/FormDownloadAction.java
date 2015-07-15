package tw.edu.chit.struts.action.employee;

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

public class FormDownloadAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		MemberDAO dao = (MemberDAO) getBean("memberDAO");
		// List<Module> modules =
		// dao.findModulesByParentName("Registration/BatchProcess");
		// UserCredential user =
		// (UserCredential)session.getAttribute("Credential");
		UserCredential user = getUserCredential(session);
		List<Module> modules = dao.findModulesByParentNameMember(
				"EmpEvaluate/FormDownload", user.getMember().getOid());
		session.setAttribute("ModuleList", modules);
		session.setAttribute("DirectoryBanner", "考 核 流 程 圖 及 自 評 表");
		setContentPage(session, "Directory2.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}
}
