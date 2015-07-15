package tw.edu.chit.struts.action.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;

public class StudentDocumentationAction extends BaseAction {

	/**
	 * 
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.setAttribute("DirectoryBanner", " 相 關 文 件 下 載 ");
		setContentPage(session, "student/StudentDocumentation.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

}
