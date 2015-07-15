package tw.edu.chit.struts.action.midp.teacher;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Member;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class TeacherPointListAction extends BaseLookupDispatchAction {

	/**
	 * 依據資訊取得教學班級資料
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

		String no = request.getParameter("no");
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		String term = am.findTermBy(PARAMETER_SCHOOL_TERM);
		Member member = new Member();
		member.setAccount(no);
		List<Object[]> aList = cm.getDtimeByTeacher(member, term);
		if (!aList.isEmpty()) {
			StringBuffer buffer = new StringBuffer("OK:");
			for (Object[] o : aList) {
				buffer.append(o[2]).append(","); // Dtime Oid
				buffer.append(o[0]).append(","); // Class Name
				buffer.append(o[1]).append(",:"); // Course Name
			}
			Toolket.sendPlainMessageForMIDP(request, response, buffer.toString());
		} else
			Toolket.sendPlainMessageForMIDP(request, response, "NORESULT");

		return null;
	}

	/**
	 * 查詢指定班級的選課學生
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward searchStudents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String oid = request.getParameter("oid");
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		List<Student> students = cm.findSeldStudentByDtimeOid(Integer
				.valueOf(oid));
		if (!students.isEmpty()) {
			StringBuffer buffer = new StringBuffer("OK:");
			for (Student student : students) {
				buffer.append(student.getOid()).append(",");
				buffer.append(student.getStudentNo()).append(",");
				buffer.append(student.getStudentName()).append(",");
				buffer.append(student.getSex()).append(":");
			}
			Toolket.sendPlainMessageForMIDP(request, response, buffer.toString());
		} else
			Toolket.sendPlainMessageForMIDP(request, response, "NORESULT");
		return null;
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchStudents", "searchStudents"); // 查詢選課學生
		return map;
	}

}
