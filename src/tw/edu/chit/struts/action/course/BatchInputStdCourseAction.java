package tw.edu.chit.struts.action.course;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Seld;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.impl.exception.SeldException;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

public class BatchInputStdCourseAction extends BaseLookupDispatchAction {

	public static final int ROW_COUNT = 60;

	/**
	 * 首次進入批次作業 
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		((DynaActionForm) form).set("sterm", term);
		setContentPage(request.getSession(false), "course/BatchAddStudent.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 批次新增開放選修學生時之處理
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward batchAddStd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		List<Seld> selds = processParameters(request, aForm);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String sterm = aForm.getString("sterm");
		try {
			String msg = cm.txBatchAddSelectedSeld(selds, sterm);
			if (StringUtils.isEmpty(msg)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.CreateSuccessful"));
				aForm.initialize(mapping);
			} else
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1", msg));
			saveMessages(request, messages);

		} catch (SeldException se) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", se.getMessage()));
			saveMessages(request, errors);
		}

		setContentPage(request.getSession(false), "course/BatchAddStudent.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 處理取消按鈕事件
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		setContentPage(request.getSession(false), "course/BatchAddStudent.jsp");
		return mapping.findForward("Main");

	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("batch.courseAdd.btn.add", "batchAddStdOpen"); // 進入批次輸入開放選修學生JSP
		map.put("makeSure", "batchAddStd"); // 批次新增開放選修學生
		map.put("Cancel", "cancel");
		return map;
	}

	// 將Request做處理
	private List<Seld> processParameters(HttpServletRequest request,
			DynaActionForm form) {
		String credit = form.getString("creditId");
		String opt = form.getString("optId");
		String dtimeOid = form.getString("oid");
		String classNo = form.getString("classNo");
		List<Seld> ret = new LinkedList<Seld>();
		Seld seld = null;
		for (int i = 0; i < ROW_COUNT; i++) {
			if (StringUtils.isNotBlank(request.getParameter("stdNo" + i))) {
				seld = new Seld();
				seld.setDtimeOid(Integer.valueOf(dtimeOid));
				seld.setStudentNo(request.getParameter("stdNo" + i)
						.toUpperCase());
				seld.setDepartClass(classNo);
				seld.setOpt(opt);
				seld.setCredit(Float.valueOf(credit));
				ret.add(seld);
			}
		}
		return ret;
	}

}
