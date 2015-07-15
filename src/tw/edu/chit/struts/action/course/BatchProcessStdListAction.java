package tw.edu.chit.struts.action.course;

import static tw.edu.chit.util.IConstants.ACTION_MAIN_NAME;
import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_NEXT_TERM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class BatchProcessStdListAction extends BaseLookupDispatchAction {

	public static final String SELD_STD_LIST = "seldStdList";

	/**
	 * 查詢科目選課學生清單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute(SELD_STD_LIST);
		Integer dtimeOid = Integer.valueOf(request.getParameter("oid"));
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		String term = am.findTermBy(PARAMETER_NEXT_TERM);
		List<Student> students = cm.findSeldStudentByDtimeOid(dtimeOid);
		for (Student student : students) {
			String departClass = student.getDepartClass();
			// 如果term為代表新學期,則學生之班級代碼須轉換^__^"
			if ("1".equals(term)) 
				departClass = Toolket.processClassNoUp(departClass);
			
			student.setDepartClass2(Toolket.getClassFullName((departClass)));
			student.setDepartClass(departClass);
			student.setSex2(student.getSex().equals("1") ? "男" : "女");
		}
		request.getSession(false).setAttribute(SELD_STD_LIST, students);
		setContentPage(request.getSession(false), "course/SeldStudentList.jsp");
		return mapping.findForward(ACTION_MAIN_NAME);
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setContentPage(request.getSession(false), "course/BatchProcess.jsp");
		return mapping.findForward(ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Back", "back");
		return map;
	}

}
