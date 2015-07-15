package tw.edu.chit.struts.action.course;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class SetToExamAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		
		
			
		setContentPage(request.getSession(false), "course/SetToExam.jsp");
		return mapping.findForward("Main");
		
	}

	@Override
	protected Map getKeyMethodMap() {
		return null;
	}

}
