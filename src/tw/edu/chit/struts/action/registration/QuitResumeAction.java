package tw.edu.chit.struts.action.registration;

import java.util.Date;
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

import tw.edu.chit.model.Graduate;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class QuitResumeAction extends BaseLookupDispatchAction {
	
	public static final String StatusQuit   = "1";
	public static final String StatusResume = "4";

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("OK", 		"save");
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		String studentNo = aForm.getString("studentNo").trim();
		if (!"".equals(studentNo)) {
			Map map = new HashMap();
			map.put("studentNo", studentNo);
			ActionMessages errors = new ActionMessages();
			lookupGraduate(studentNo, map, session, errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
			request.setAttribute("QuitResume", map);
		}
		setContentPage(session, "registration/QuitResume.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward save(ActionMapping mapping,
			 				  ActionForm form,
			 				  HttpServletRequest request,
			 				  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		String studentNo = aForm.getString("studentNo").trim();
		if (!"".equals(studentNo)) {
			//Map map = new HashMap();
			//map.put("studentNo", studentNo);
			Map map = aForm.getMap();
			ActionMessages errors = new ActionMessages();
			Graduate graduate = lookupGraduate(studentNo, map, session, errors);
			if (!errors.isEmpty() || graduate == null) {
				saveErrors(request, errors);
				request.setAttribute("QuitResume", map);
			} else {
				validateInput(aForm, errors);
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					request.setAttribute("QuitResume", map);					
				} else {
					MemberManager manager = (MemberManager)getBean("memberManager");
					manager.processQuitResume(graduate, aForm.getMap());
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ModifySuccessful"));
					saveMessages(request, errors);
				}
			}
		}
		setContentPage(session, "registration/QuitResume.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	private Graduate lookupGraduate(String studentNo, Map result, HttpSession session, ActionMessages errors) {
		
		MemberManager manager = (MemberManager)getBean("memberManager");
		//ActionMessages errors = new ActionMessages();
		Graduate grad = manager.findGraduateByStudentNo(studentNo);
		if (grad == null) {
			result.put("found", false);
		} else {
			result.put("found", true);
			result.put("name",  grad.getStudentName());
			result.put("class", grad.getDepartClass2());
			String classNo = grad.getDepartClass();
			if (!getUserCredential(session).isClassInCharge(classNo)) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoRight"));
			} else if (!StatusQuit.equals(grad.getOccurStatus())) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoQuitRecord"));
			} else {
				populateFormProperties(result, grad);
				session.setAttribute("GraduateInEdit", grad);
			}
		}
		return grad;
	}
	
	private void validateInput(DynaActionForm form, ActionMessages errors) {
		
		String buff;
		
		buff = form.getString("year2").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "復學年度"));
		} else {
			try {
				Short.parseShort(buff);		
			} catch(Exception e) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidYearFormat", "復學年度"));							
			}
		}	
		
		buff = form.getString("term2").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "復學學期"));
		}
		
		buff = form.getString("date2").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "復學日期"));
		} else if (Toolket.parseDate(buff) == null) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidDateFormat", "復學日期"));
		}
	}
	
	@SuppressWarnings("unchecked")
	private void populateFormProperties(Map formProperties, Graduate grad) {
		
		Object buff;
		
		buff = grad.getOccurYear();
		if (buff != null) {
			formProperties.put("year1", buff.toString());
		}
		
		buff = grad.getOccurTerm();
		if (buff != null) {
			formProperties.put("term1", buff);
		}
		
		buff = grad.getOccurDate();
		if (buff != null) {
			formProperties.put("date1", Toolket.printDate((Date)buff));
		}
		
		buff = grad.getOccurDocno();
		if (buff != null) {
			formProperties.put("docNo1", buff);
		}
	}
}
