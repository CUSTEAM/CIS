package tw.edu.chit.struts.action.course;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class SetExamDateAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session=request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("ExamDateList", manager.getExamDate());
		// TODO 中止開發試務系統

		setContentPage(request.getSession(false), "course/SetExamDate.jsp");
		return mapping.findForward("Main");

	}

	@Override
	protected Map getKeyMethodMap() {

		return null;
	}

}
