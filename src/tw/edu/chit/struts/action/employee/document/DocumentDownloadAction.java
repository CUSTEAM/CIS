package tw.edu.chit.struts.action.employee.document;

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

public class DocumentDownloadAction extends BaseAction {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		MemberDAO dao = (MemberDAO) getBean("memberDAO");
		UserCredential user = getUserCredential(session);
		List<Module> modules = dao.findModulesByParentNameMember(
				"OtherDocument/Document", user.getMember().getOid());
		session.setAttribute("ModuleList", modules);
		session.setAttribute("DirectoryBanner", "教 職 員 其 他 相 關 文 件");
		setContentPage(session, "Directory2.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

}
