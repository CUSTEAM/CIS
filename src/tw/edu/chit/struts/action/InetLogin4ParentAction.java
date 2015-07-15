package tw.edu.chit.struts.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.gui.Menu;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.InvalidAccountException;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.util.Toolket;

public class InetLogin4ParentAction extends BaseAction {
		
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		try {
			DynaActionForm aForm = (DynaActionForm)form;
			
			String idno = aForm.getString("idno");
			String birthdate = aForm.getString("birthdate");
			
			if ("".equals(idno)) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("InputMissing", Toolket.getBundle(request).getString("Account")));
				saveErrors(request, errors);
				return mapping.findForward("failure");
			} else {
				MemberManager mm = (MemberManager)getBean("memberManager");
				try {
					UserCredential user = mm.createInetUserCredential4Parent(idno, birthdate, null);
					Menu menu = mm.createMenuForParent();
					
					HttpSession session = request.getSession(false);
					if (session != null) session.invalidate();
					session = request.getSession(true);
					session.setAttribute("Credential", user);
					session.setAttribute("Menu", menu);
					if (menu.getItems().size() > 0) {
						return new ActionForward(menu.getItem(0).getModule().getAction(), false);
					} else {
						setContentPage(request.getSession(false), "BulletinBoard.jsp");
						return mapping.findForward("Main");
					}
					
				} catch(InvalidAccountException ie) {
					ActionMessages errors = new ActionMessages();
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("LoginInvalid", idno));
					saveErrors(request, errors);
					return mapping.findForward("failure");					
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			log.debug("Exception: "+e.getMessage());
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("failure");
		}
	}

}


