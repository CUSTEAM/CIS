package tw.edu.chit.struts.action.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class MyScoreHistory extends BaseLookupDispatchAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		setContentPage(request.getSession(false), "student/myScoreHistory.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {

		return null;
	}
	
	private void removeData(HttpServletRequest request){
		request.removeAttribute("pass");
		request.removeAttribute("term2Score");
		request.removeAttribute("OpenViewMid");
		request.removeAttribute("OpenViewEnd");
		request.removeAttribute("OpenViewGra");
		request.removeAttribute("studentNo");
		request.removeAttribute("termNow");
		request.removeAttribute("yearNow");
	}

}
