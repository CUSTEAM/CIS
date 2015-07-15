package tw.edu.chit.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.gui.Menu;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.InvalidAccountException;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class InetLoginAction extends BaseAction {
		
	public ActionForward execute(ActionMapping mapping,
								 ActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response)
			throws Exception {
		
		try {
			DynaActionForm aForm = (DynaActionForm)form;
			
			String username = aForm.getString("username");
			String password = aForm.getString("password");
			
			log.debug("username = '" + username + "'");
			// MOVE request parameter TO DynaActionForm FOR checkbox
			if (!"on".equals(request.getParameter("rememberme"))) {
				aForm.set("rememberme", "");
			}
			// END OF MOVE
			if ("".equals(username)) {
				//aForm.set("username", "guest");
				//request.setAttribute("NeoUser", "quest");
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE,
						   new ActionMessage("InputMissing",
								   			 Toolket.getBundle(request).getString("Account")));
				saveErrors(request, errors);
				return mapping.findForward("failure");
			} else {
				MemberManager mm = (MemberManager)getBean("memberManager");
				try {
					UserCredential user = mm.createInetUserCredential(username, password);
					Menu menu = null;
					if (UserCredential.PrioEmployee.equals(user.getMember().getPriority())) {	// An employee login
						menu = mm.createInetMenuByMember(user.getMember());
					} else if (UserCredential.PrioStudent.equals(user
							.getMember().getPriority())) { // A student login
						CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
						String campusNo = cm.findCampusNoByClassNo(user
								.getStudent().getDepartClass());
						String schoolType = null;
						if (IConstants.HSIN_CHU_CAMPUS.equals(campusNo))
							schoolType = IConstants.HSIN_CHU_CAMPUS;
						else
							schoolType = cm.findSchoolTypeByClassNo(user
									.getStudent().getDepartClass());
						// 須以schoolType去判斷,是否Disabled網路選課Menu
						menu = mm.createMenuForStudent(schoolType);
					}
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
					//aForm.set("username", "guest");
					ActionMessages errors = new ActionMessages();
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("LoginInvalid", username));
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

