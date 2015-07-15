package tw.edu.chit.struts.action.portfolio;

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

public class ActivitiesDir extends BaseAction {

	public ActionForward execute(ActionMapping mapping,
			 					 ActionForm form,
			 					 HttpServletRequest request,
			 					 HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		MemberDAO dao = (MemberDAO)getBean("memberDAO");
		UserCredential user = getUserCredential(session);
		List<Module> modules = dao.findModulesByParentNameMember("Portfolio/ActivitiesDir", user.getMember().getOid());
		session.setAttribute("ModuleList", modules);
		//session.setAttribute("DirectoryBanner", "<p align=left>成 績 批 次 處 理 作 業</p>");
		
		session.setAttribute("DirectoryBanner", "<table align=left>"+
									"<tr>"+
										"<td align=left>&nbsp;&nbsp;<img src=images/icon/folder_home.gif></td>"+
										"<td><blink>e - Portfolio 活動管理</blink></td>"+
									"</tr>"+
								"</table>");
		
		
		setContentPage(session, "Directory.jsp");
		return mapping.findForward("Main");
	}
}