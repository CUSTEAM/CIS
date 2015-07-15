package tw.edu.chit.struts.action.course;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class CourseManageAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		
		setContentPage(request.getSession(false), "course/CourseManager.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		return null;
	}

}
