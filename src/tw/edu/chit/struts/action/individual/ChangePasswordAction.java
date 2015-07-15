package tw.edu.chit.struts.action.individual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ChangePasswordAction extends BaseLookupDispatchAction {

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", 		"save");
		map.put("Cancel", 	"cancel");
		return map;		
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {

		setContentPage(request, "individual/ChangePassword.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping,
			  				  ActionForm form,
			  				  HttpServletRequest request,
			  				  HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		UserCredential user = getUserCredential(session);
		ActionMessages messages = validateInput(aForm, Toolket.getBundle(request), user);
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			//request.setAttribute("ChangePasswordForm", aForm);	
		} else {
			MemberManager manager = (MemberManager)getBean("memberManager");
			try {
				manager.changeMemberPassword(user.getMember(), aForm.getString("newPassword").trim());
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ModifySuccessful"));
				saveMessages(request, messages);
				aForm.initialize(mapping);		
			} catch(Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				//request.setAttribute("ChangePasswordForm", aForm);	
				//return mapping.findForward("Main");
			}
		}
		//request.setAttribute("ChangePasswordForm", aForm);
		setContentPage(request, "individual/ChangePassword.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward cancel(ActionMapping mapping,
			  					ActionForm form,
			  					HttpServletRequest request,
			  					HttpServletResponse response)
			throws Exception {
		
		((DynaActionForm)form).initialize(mapping);
		setContentPage(request, "individual/ChangePassword.jsp");
		return mapping.findForward("Main");
	}
	
	private ActionMessages validateInput(DynaActionForm form, ResourceBundle bundle, UserCredential user) {
		
		ActionMessages errors = new ActionMessages();
		
		String oldPassword  = form.getString("oldPassword").trim();
		String newPassword  = form.getString("newPassword").trim();
		String newPassword2 = form.getString("newPassword2").trim();
		
		if ("".equals(oldPassword)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty",
																		bundle.getString("OriginPassword")));
		}
		
		if ("".equals(newPassword)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty",
																		bundle.getString("NewPassword")));
		}
		
		if ("".equals(newPassword2)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty",
																		bundle.getString("NewPasswordVerify")));
		}
	
		if (!newPassword.equals(newPassword2)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NewPasswordNotEqualToPasswordVerify"));
		}
		
		if (!errors.isEmpty()) return errors;
		
		if (!oldPassword.equals(user.getMember().getPassword())) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidOldPassword"));
			return errors;
		}
		
		if (newPassword.equalsIgnoreCase(getDefaultPassword(user))) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NewPasswordCantEqualToDefault"));
		}
		
		return errors;
	}
	
	private String getDefaultPassword(UserCredential user) {
		
		if (user.isEmployee()) {
			return user.getMember().getBirthDate().replaceAll("-", "");
		} else {
			return user.getMember().getIdno();
		}	
	}
	
}


