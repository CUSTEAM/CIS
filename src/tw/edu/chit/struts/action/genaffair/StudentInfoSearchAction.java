package tw.edu.chit.struts.action.genaffair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Student;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class StudentInfoSearchAction extends BaseLookupDispatchAction {

	/**
	 * 第一次進入學生查詢
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.getSession(false).removeAttribute("StudentInfoList");
		setContentPage(request.getSession(false),
				"genaffair/StudentInfoSearch.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 查詢學生資訊
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Map<String, String> params = getParameters(request);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		List<Student> students = mm.findStudentsInChargeByStudentInfo(params);
		Student student;
		for (Iterator<Student> stuIter = students.iterator(); stuIter.hasNext();) {
			student = stuIter.next();
			student.setDepartClass2(Toolket.getClassFullName(student
					.getDepartClass()));
			student.setSex2(Toolket.getSex(student.getSex()));
		}
		if (!students.isEmpty())
			session.setAttribute("StudentInfoList", students);
		else
			session.setAttribute("StudentInfoList", Collections.EMPTY_LIST);
		setContentPage(request.getSession(false),
				"genaffair/StudentInfoSearch.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query", "search");
		return map;
	}

	private Map<String, String> getParameters(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name2", request.getParameter("name2"));
		params.put("studentNo2", request.getParameter("studentNo2"));
		params.put("idNo2", request.getParameter("idNo2"));
		params.put("status2", "");
		params.put("campusInCharge2", StringUtils.defaultIfEmpty(request
				.getParameter("campusInCharge2"), ""));
		params.put("schoolInCharge2", StringUtils.defaultIfEmpty(request
				.getParameter("schoolInCharge2"), ""));
		params.put("deptInCharge2", StringUtils.defaultIfEmpty(request
				.getParameter("deptInCharge2"), ""));
		params.put("classInCharge2", StringUtils.defaultIfEmpty(request
				.getParameter("classInCharge2"), ""));
		return params;
	}

}
