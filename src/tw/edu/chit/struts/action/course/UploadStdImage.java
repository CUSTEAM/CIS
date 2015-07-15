package tw.edu.chit.struts.action.course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.struts.action.BaseAction;

public class UploadStdImage extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		setContentPage(request.getSession(false), "course/UploadStdImage.jsp");						
		return mapping.findForward("Main");
	}
}
