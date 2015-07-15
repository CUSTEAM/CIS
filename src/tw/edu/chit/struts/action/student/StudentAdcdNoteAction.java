package tw.edu.chit.struts.action.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;

public class StudentAdcdNoteAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		setContentPage(request, "student/StudentAdcdNote.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

}
