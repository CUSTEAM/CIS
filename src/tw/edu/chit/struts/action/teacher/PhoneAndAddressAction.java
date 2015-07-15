package tw.edu.chit.struts.action.teacher;

import java.text.SimpleDateFormat;
import java.util.HashMap;
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

import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Member;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

public class PhoneAndAddressAction extends BaseLookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Save", "save");
		map.put("Cancel", "cancel");
		return map;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Member member = getUserCredential(session).getMember();
		Employee employee = (Employee) getUserCredential(session).getMember();
		member.setBirthDate(new SimpleDateFormat("yyyy/MM/dd").format(employee
				.getBdate()));
		request.setAttribute("member", member);
		request.setAttribute("PhoneAndAddress", employee);
		setContentPage(session, "teacher/PhoneAndAddress.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return unspecified(mapping, form, request, response);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		HttpSession session = request.getSession(false);
		Employee employee = (Employee) getUserCredential(session).getMember();
		ActionMessages messages = new ActionMessages();
		try {
			MemberManager mm = (MemberManager) getBean("memberManager");
			mm.modifyEmployeePhoneAndAddress(aForm.getMap(), employee);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.ModifySuccessful"));
			saveMessages(request, messages);
		} catch (Exception e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			saveErrors(request, messages);
		}
		request.setAttribute("PhoneAndAddress", aForm.getMap());
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

}
