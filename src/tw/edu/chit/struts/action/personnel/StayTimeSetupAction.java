package tw.edu.chit.struts.action.personnel;

import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;

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

import tw.edu.chit.model.Code5;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

public class StayTimeSetupAction extends BaseLookupDispatchAction {

	/**
	 * 進入差勤紀錄作業
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
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		List<Code5> code5s = mm.findStayTime();
		List<Code5> code5d = mm.findStayTimeDead();
		
		DynaActionForm aForm = (DynaActionForm) form;

		for (Code5 code5 : code5s) {
			if ("B".equalsIgnoreCase(code5.getIdno())) {
				aForm.set("begin", code5.getName());
			} else if ("E".equalsIgnoreCase(code5.getIdno())) {
				aForm.set("end", code5.getName());
			}
		}
		
		for (Code5 code5 : code5d) {
			if ("D".equalsIgnoreCase(code5.getIdno())) {
				aForm.set("dead", code5.getName());
			}
		}

		setContentPage(session, "personnel/StayTimeSetup.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		List<Code5> code5s = mm.findStayTime();
		List<Code5> code5d = mm.findStayTimeDead();
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			for (Code5 code5 : code5s) {
				if ("B".equalsIgnoreCase(code5.getIdno())) {
					code5.setName(aForm.getString("begin"));
				} else if ("E".equalsIgnoreCase(code5.getIdno())) {
					code5.setName(aForm.getString("end"));
				}
			}
			for (Code5 code5 : code5d) {
				if ("D".equalsIgnoreCase(code5.getIdno())) {
					code5.setName(aForm.getString("dead"));
				}
			}
			
			mm.updatStayTime(code5s, code5d);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新成功"));
			saveMessages(request, messages);
		} catch (Exception e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新失敗,請洽電算中心,謝謝."));
			saveErrors(request, messages);
		}
		setContentPage(session, "personnel/StayTimeSetup.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("course.onlineAddRemoveCourse.update", "update");
		return map;
	}

}
