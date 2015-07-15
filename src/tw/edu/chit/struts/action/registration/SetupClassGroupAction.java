package tw.edu.chit.struts.action.registration;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class SetupClassGroupAction extends BaseLookupDispatchAction {

	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("OK", 		"save");
		map.put("Cancel", 	"cancel");
		return map;		
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {

		setContentPage(request.getSession(false), "registration/SetupClassGroup.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping,
			  				  ActionForm form,
			  				  HttpServletRequest request,
			  				  HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		ActionMessages messages = validateInput(aForm, Toolket.getBundle(request));
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			//aForm.getMap().put("inputInvalid", true);			
		} else {
			MemberManager manager = (MemberManager)getBean("memberManager");
			try {
				manager.setupClassGroup(aForm.getString("classInCharge"), aForm.getString("groupSel"));
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ModifySuccessful"));
				saveMessages(request, messages);
				aForm.initialize(mapping);
				//aForm.getMap().put("inputInvalid", false);			
			} catch(Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				//aForm.getMap().put("inputInvalid", true);
				return mapping.findForward("Main");
			}
		}
		request.setAttribute("SetupClassGroup", aForm.getMap());
		return mapping.findForward("Main");
	}
	
	private ActionMessages validateInput(DynaActionForm form, ResourceBundle bundle) {
		
		ActionMessages errors = new ActionMessages();
		
		String clazz = form.getString("classInCharge").trim();
		if ("".equals(clazz) || "All".equals(clazz)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty",
																		bundle.getString("Class")));
		}
		
		String codeId  = form.getString("group").trim();
		String codeSel = form.getString("groupSel").trim();
		if (!codeId.equals(codeSel)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", 
																		bundle.getString("Group")));						
		}	
		return errors;
	}
}
