package tw.edu.chit.struts.action.registration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Student;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

public class ImageBrowserAction extends BaseLookupDispatchAction {

	/**
	 * 進入照片瀏覽作業
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

		setContentPage(session, "registration/ImageBrowser.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 進入照片瀏覽作業
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward preview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		String classNo = processClassInfo(aForm);
		List<Student> students = mm.findStudentsByClassNo(classNo);
		if (students.isEmpty()) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "查無學生資料,或請以班級為單位查詢,謝謝!"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		request.setAttribute("studentList", students);
		setContentPage(session, "registration/ImageBrowser.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Preview", "preview");
		return map;
	}

	private String processClassInfo(DynaActionForm form) {
		String campusInCharge2 = (String) form.get("campusInCharge2");
		String schoolInCharge2 = (String) form.get("schoolInCharge2");
		String deptInCharge2 = (String) form.get("deptInCharge2");
		String departClass = (String) form.get("classInCharge2");
		schoolInCharge2 = schoolInCharge2.equalsIgnoreCase("All") ? ""
				: schoolInCharge2;
		deptInCharge2 = deptInCharge2.equalsIgnoreCase("All") ? ""
				: deptInCharge2;
		departClass = departClass.equalsIgnoreCase("All") ? "" : departClass;
		return departClass.length() == 6 ? departClass : campusInCharge2
				+ schoolInCharge2 + deptInCharge2;
	}

}
