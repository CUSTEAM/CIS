package tw.edu.chit.struts.action.personnel;

import java.util.HashMap;
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

import tw.edu.chit.model.DEmpl;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class QuitEmployeeInfoEditAction extends BaseLookupDispatchAction {

	private static final String StatusQuit   			 = "9";	//離職
	private static final String StatusLeaveWithSalary  	 = "1";	//留職留薪
	private static final String StatusLeaveWithoutSalary = "2";	//留職停薪
	private static final String StatusOnJob  			 = "";	//在職

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("OK", 		"save");
		map.put("Cancel", 	"cancel");
		map.put("Back", 	"cancel");
		return map;		
	}

	public ActionForward cancel(ActionMapping mapping,
			  					ActionForm form,
			  					HttpServletRequest request,
			  					HttpServletResponse response)
			throws Exception {
		
		//String backpage = Toolket.getCookie(request, "BackPage");
		//log.debug("BackPage=" + backpage);
		//if ("".equals(backpage)) {
			HttpSession session = request.getSession(false);
			((DynaActionForm)form).initialize(mapping);
			session.removeAttribute("QuitEmployeeInfoEdit");
			Toolket.resetCheckboxCookie(response, "QuitEmployeeInfo");
			setContentPage(session, "personnel/QuitEmployeeInfo.jsp");
			return mapping.findForward("Main");
		//} else {
			//log.debug("======>" + request.getContextPath());
			//backpage = backpage.substring(request.getContextPath().length());
			//return new ActionForward(backpage);
		//}
	}
	
	public ActionForward save(ActionMapping mapping,
							  ActionForm form,
							  HttpServletRequest request,
							  HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		String mode = aForm.getString("mode");
		if ("Create".equals(mode)) {
			return create(mapping, aForm, request, response);
		} else if ("Modify".equals(mode)){
			return modify(mapping, aForm, request, response);
		}
		throw new Exception("'mode' property of QuitEmployeeEditForm is illegal.");
	}
	
	private ActionForward create(ActionMapping mapping,
								 DynaActionForm form,
			  					 HttpServletRequest request,
			  					 HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		ActionMessages messages = validateInputForCreate(form, Toolket.getBundle(request));
		
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			session.setAttribute("QuitEmployeeInfoEdit", form.getMap());
			return mapping.findForward("Main");
		} else {	
			try {
				MemberManager mm = (MemberManager)getBean("memberManager");
				mm.createEmployee(form.getMap());
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.CreateSuccessful"));
				saveMessages(request, messages);
				form.initialize(mapping);
				session.removeAttribute("QuitEmployeeInfoEdit");
				return mapping.findForward("success");	
			} catch(Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("QuitEmployeeInfoEdit", form.getMap());
				return mapping.findForward("Main");
			}
		}
	}
	
	private ActionForward modify(ActionMapping mapping,
								 DynaActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DEmpl employee = (DEmpl)session.getAttribute("QuitEmployeeInEdit");
		ActionMessages messages = validateInputForModify(form, employee, Toolket.getBundle(request));
		
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			session.setAttribute("QuitEmployeeInfoEdit", form.getMap());
			return mapping.findForward("Main");
		} else {	
			try {
				MemberManager mm = (MemberManager)getBean("memberManager");
				mm.txModifyQuitEmployee(form.getMap(), employee);				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ModifySuccessful"));
				saveMessages(request, messages);
				form.initialize(mapping);
				session.removeAttribute("QuitEmployeeInfoEdit");
				Toolket.resetCheckboxCookie(response, "QuitEmployeeInfo");
				//request.getParameterMap().clear();
				//request.getParameterMap().remove("name");
				return mapping.findForward("success");	
			} catch(Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("QuitEmployeeInfoEdit", form.getMap());
				return mapping.findForward("Main");
			}
		}
	}	
	
	private ActionMessages validateInputForCreate(DynaActionForm form, ResourceBundle bundle) {
		
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		MemberManager mm = (MemberManager)getBean("memberManager");
		mm.validateEmployeeCreateForm(form.getMap(), errors, bundle);
		return errors;
	}
	
	private ActionMessages validateInputForModify(DynaActionForm form, DEmpl employee, ResourceBundle bundle) {
		
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		MemberManager mm = (MemberManager)getBean("memberManager");
		mm.validateQuitEmployeeModifyForm(form.getMap(), employee, errors, bundle);
		return errors;
	}
	
	private void validateFieldFormat(DynaActionForm form, ActionMessages errors, ResourceBundle bundle) {

		String buff;
		
		buff = form.getString("cname").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "中文姓名"));
		}

		buff = form.getString("idno").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "身分證號"));
		}
		
		buff = form.getString("sex").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("Sex")));
		}

		buff = form.getString("category").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "員工分類"));
		}
		
		buff = form.getString("bdate2").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("BirthDate")));
		} else if (Toolket.parseDate(buff) == null) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidDateFormat", bundle.getString("BirthDate")));
		}
		
		/*
		buff = form.getString("caddr").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "現居地址"));
		}
		
		buff = form.getString("paddr").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "戶籍地址"));
		}
		*/
				
		String codeId  = form.getString("unit").trim();
		String codeSel = form.getString("unitSel").trim();
		if (!codeId.equals(codeSel)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", "聘任單位"));						
		} else if ("".equals(codeSel)) {
			//errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "聘任單位"));
		}
		
		codeId  = form.getString("pcode").trim();
		codeSel = form.getString("pcodeSel").trim();
		if (!codeId.equals(codeSel)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", "職級"));						
		} else if ("".equals(codeSel)) {
			//errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "職級"));
		}	
		
		codeId  = form.getString("degree").trim();
		codeSel = form.getString("degreeSel").trim();
		if (!codeId.equals(codeSel)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", "學歷"));					
		} else if ("".equals(codeSel)) {
			//errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "學歷"));
		}

		codeId  = form.getString("tutor").trim();
		codeSel = form.getString("tutorSel").trim();
		if (!codeId.equals(codeSel)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", "兼任導師"));					
		}
		
		codeId  = form.getString("director").trim();
		codeSel = form.getString("directorSel").trim();
		if (!codeId.equals(codeSel)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", "兼任主管"));					
		}
		
		buff = form.getString("mode");
		codeId  = form.getString("status").trim();
		if ("Create".equals(buff) && StatusQuit.equals(codeId)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidEmployeeStatus"));
		}
		
		buff = form.getString("startDate2").trim();
		if ("".equals(buff)) {
			//errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "狀態起始日期"));
		} else if (Toolket.parseDate(buff) == null) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidDateFormat", "狀態起始日期"));
		}
		
		codeId  = form.getString("statusCause").trim();
		codeSel = form.getString("statusCauseSel").trim();
		if (!codeId.equals(codeSel)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", "狀態原因"));							
		}
	}
	
}

