package tw.edu.chit.struts.action.registration;

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

public class ReportPrintListAction extends BaseAction {

	/**
	 * 處理進入學籍列印畫面
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
		UserCredential user = getUserCredential(session);
		List<Module> modules = dao.findModulesByParentNameMember(
				"Registration/ReportPrintList", user.getMember().getOid());
		session.setAttribute("ModuleList", modules);
		session.setAttribute("DirectoryBanner", " 學 籍 列 印 ");
		setContentPage(session, "Directory1.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

}
