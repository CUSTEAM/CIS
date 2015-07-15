package tw.edu.chit.struts.action.examine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.Toolket;

public class DirectoryActionExamine extends BaseAction {

	public ActionForward execute(ActionMapping mapping,
			 					 ActionForm form,
			 					 HttpServletRequest request,
			 					 HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		if (Toolket.TriggerMenuCollapse(session, "Examine")) {
			setContentPage(session, "examine/Directory.jsp");
			session.setAttribute("directory", "Examine");
		}
		return mapping.findForward("Main");
	}
}
