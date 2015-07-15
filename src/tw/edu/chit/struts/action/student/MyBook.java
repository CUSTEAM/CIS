package tw.edu.chit.struts.action.student;

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

public class MyBook extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");

		UserCredential credential = (UserCredential) session.getAttribute("Credential");


		String termNow=manager.getNowBy("School_term");
		String yearNow=manager.getNowBy("School_year");
		String studentNo=credential.getStudent().getStudentNo();

		session.setAttribute("termNow", termNow);	// 取得現在學期
		session.setAttribute("yearNow", yearNow);	// 取得現在學年
		session.setAttribute("studentNo", studentNo); // 取得學號


		session.setAttribute("bookList", manager.getMyBookBy(studentNo, yearNow, termNow));


		setContentPage(request.getSession(false), "student/myBook.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {

		return null;
	}

}
