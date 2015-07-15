package tw.edu.chit.struts.action.sysadmin;

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

import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class RegenAdcdBySeldAction extends BaseLookupDispatchAction {

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", 		"process");
		map.put("Cancel", 	"cancel");
		return map;		
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {
		
		AdminManager manager = (AdminManager)getBean("adminManager");
		Map map = new HashMap();
		map.put("term", manager.findParameterValueByName("School_term"));
		request.setAttribute("RegenAdcdBySeldForm", map);
		setContentPage(request, "sysadmin/RegenAdcdBySeld.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward process(ActionMapping mapping,
			  				  	 ActionForm form,
			  				  	 HttpServletRequest request,
			  				  	 HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;

		AdminManager manager = (AdminManager)getBean("adminManager");
		try {
			manager.regenAdcdBySeld(aForm.getString("term"),
									aForm.getString("campusInCharge"),
									aForm.getString("schoolInCharge"),
									aForm.getString("deptInCharge"),
									aForm.getString("classInCharge"));
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ModifySuccessful"));
			saveMessages(request, messages);
		} catch(Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
			saveErrors(request, errors);
			//aForm.getMap().put("inputInvalid", true);
			return mapping.findForward("Main");
		}
			
		request.setAttribute("RegenAdcdBySeldForm", aForm.getMap());
		setContentPage(request, "sysadmin/RegenAdcdBySeld.jsp");
		return mapping.findForward("Main");
	}
}
