package tw.edu.chit.struts.action.registration;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.WwPass;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class StudentInfoEditAction extends BaseLookupDispatchAction {

	private static final String StatusQuit   	= "1";
	private static final String StatusFlunkOut  = "2";
	private static final String StatusTransferIn= "3";
	private static final String StatusResume   	= "4";
	private static final String StatusGraduate  = "6";
	private static final String StatusDelayed  	= "8";
	private static final String StatusInSchool  = "";

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("OK", 		"save");
		map.put("Cancel", 	"cancel");
		map.put("Back", 	"cancel");
		map.put("ModifyWithAutoDeselect", "modifyWithAutoDeselect");
		map.put("CreateWWPass", 	"createWWPass");
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
			session.removeAttribute("StudentInfoEdit");
			Toolket.resetCheckboxCookie(response, "StudentInfo");
			setContentPage(session, "registration/StudentInfo.jsp");
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
		throw new Exception("'mode' property of StudentEditForm is illegal.");
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
			session.setAttribute("StudentInfoEdit", form.getMap());
			return mapping.findForward("Main");
		} else {	
			try {
				MemberManager mm = (MemberManager)getBean("memberManager");
				mm.createStudent(form.getMap());
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.CreateSuccessful"));
				saveMessages(request, messages);
				form.initialize(mapping);
				session.removeAttribute("StudentInfoEdit");
				//request.getParameterMap().clear();
				//request.getParameterMap().remove("name");
				return mapping.findForward("success");	
			} catch(Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("StudentInfoEdit", form.getMap());
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
		Student student = (Student)session.getAttribute("StudentInEdit");
		ActionMessages messages = validateInputForModify(form, student, Toolket.getBundle(request));
		
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			if (student.isSubmitAutoClear()) {
				request.setAttribute("SubmitAutoClear", true);
				student.setSubmitAutoClear(false);
			}
			session.setAttribute("StudentInfoEdit", form.getMap());
			return mapping.findForward("Main");
		} else {	
			try {
				MemberManager mm = (MemberManager)getBean("memberManager");
				mm.txModifyStudent(form.getMap(), student, false);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ModifySuccessful"));
				saveMessages(request, messages);
				form.initialize(mapping);
				session.removeAttribute("StudentInfoEdit");
				Toolket.resetCheckboxCookie(response, "StudentInfo");
				//request.getParameterMap().clear();
				//request.getParameterMap().remove("name");
				return mapping.findForward("success");	
			} catch(Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("StudentInfoEdit", form.getMap());
				return mapping.findForward("Main");
			}
		}
	}	
	
	public ActionForward modifyWithAutoDeselect(ActionMapping mapping,
			 								 	ActionForm form,
			 								 	HttpServletRequest request,
			 								 	HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		Student student = (Student)session.getAttribute("StudentInEdit");
		ActionMessages messages = validateInputForModify(aForm, student, Toolket.getBundle(request));

		if ((!messages.isEmpty() && !student.isSubmitAutoClear()) || (messages.size() > 1 && student.isSubmitAutoClear())) {
			// There are validation errors other than the ones that can be auto-cleared
			saveErrors(request, messages);
			session.setAttribute("StudentInfoEdit", aForm.getMap());
			student.setSubmitAutoClear(false);
			return mapping.findForward("Main");
		} else {	
			try {
				MemberManager mm = (MemberManager)getBean("memberManager");
				mm.txModifyStudent(aForm.getMap(), student, true);
				messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ModifySuccessful"));
				saveMessages(request, messages);
				aForm.initialize(mapping);
				session.removeAttribute("StudentInfoEdit");
				Toolket.resetCheckboxCookie(response, "StudentInfo");
				//request.getParameterMap().clear();
				//request.getParameterMap().remove("name");
				student.setSubmitAutoClear(false);
				return mapping.findForward("success");	
			} catch(Exception e) {
				e.printStackTrace();
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("StudentInfoEdit", aForm.getMap());
				student.setSubmitAutoClear(false);
				return mapping.findForward("Main");
			}
		}
	}	
	
	public ActionForward createWWPass(ActionMapping mapping,
			  						  ActionForm form,
			  						  HttpServletRequest request,
			  						  HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		Student student = (Student)session.getAttribute("StudentInEdit");
		MemberDAO dao = (MemberDAO)getBean("memberDAO");
		WwPass account = dao.findWWPassByAccount(student.getStudentNo());
		
		if (account != null) {		// WWPass already existed
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.AccountAlreadyExisted"));
			saveErrors(request, errors);
			session.setAttribute("StudentInfoEdit", aForm.getMap());
			return mapping.findForward("Main");
		} else {	
			try {
				dao.createWWPass(student.getStudentNo(), student.getIdno(), UserCredential.PrioStudent);
				Toolket.sendWWPassInfoByQueue(student.getStudentNo(), student
						.getIdno(), UserCredential.PrioStudent, StringUtils
						.substring(student.getIdno(), 1),
						IConstants.SYNC_DO_TYPE_INSERT);
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ModifySuccessful"));
				saveMessages(request, messages);
				aForm.initialize(mapping);
				session.removeAttribute("StudentInfoEdit");
				Toolket.resetCheckboxCookie(response, "StudentInfo");
				//request.getParameterMap().clear();
				//request.getParameterMap().remove("name");
				return mapping.findForward("success");	
			} catch(Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("StudentInfoEdit", aForm.getMap());
				return mapping.findForward("Main");
			}
		}
	}
	
	private ActionMessages validateInputForCreate(DynaActionForm form, ResourceBundle bundle) {
		
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		MemberManager mm = (MemberManager)getBean("memberManager");
		mm.validateStudentCreateForm(form.getMap(), errors, bundle);
		return errors;
	}
	
	private ActionMessages validateInputForModify(DynaActionForm form, Student student, ResourceBundle bundle) {
		
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		MemberManager mm = (MemberManager)getBean("memberManager");
		mm.validateStudentModifyForm(form.getMap(), student, errors, bundle);
		return errors;
	}
	
	private void validateFieldFormat(DynaActionForm form, ActionMessages errors, ResourceBundle bundle) {

		String buff;
		
		buff = form.getString("name").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("Name")));
		}
		
		buff = form.getString("studentNo").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("StudentNo")));
		}
		
		buff = form.getString("classInCharge").trim();
		if ("".equals(buff) || "All".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("Class")));
		}
		
		buff = form.getString("sex").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("Sex")));
		}
		
		buff = form.getString("birthDate").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("BirthDate")));
		} else if (Toolket.parseDate(buff) == null) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidDateFormat", 
																		bundle.getString("BirthDate")));
		}
		
		buff = form.getString("idNo").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", "身分證號"));
		}
		
		
		buff   = form.getString("entrance").trim();
		if (!"".equals(buff) && Toolket.parseYearMonth(buff) == null) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidYearMonthFormat", 
																		bundle.getString("EntranceYearMonth")));			
		}
		
		buff  = form.getString("gradYear").trim();
		if (!"".equals(buff)) {
			try {
				Short.parseShort(buff);		
			} catch(Exception e) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidYearFormat", 
																			bundle.getString("HighSchoolGradYear")));							
			}
		}
		
		String codeId  = form.getString("entranceIdentity").trim();
		String codeSel = form.getString("entrIdentSel").trim();
		if (!codeId.equals(codeSel)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", 
																		bundle.getString("EntranceIdentity")));						
		}
		
		codeId  = form.getString("group").trim();
		codeSel = form.getString("groupSel").trim();
		if (!codeId.equals(codeSel)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", 
																		bundle.getString("Group")));						
		}	
		
		codeId  = form.getString("basicIdentity").trim();
		codeSel = form.getString("basicIdentSel").trim();
		if (!codeId.equals(codeSel)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", 
																		bundle.getString("BasicIdentity")));						
		}
		
		buff = form.getString("mode");
		codeId  = form.getString("status").trim();
		codeSel = form.getString("statusSel").trim();
		if (!codeId.equals(codeSel)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", 
																		bundle.getString("Status")));						
		} else if ("Create".equals(buff) && !(StatusInSchool.equals(codeId) || StatusTransferIn.endsWith(codeId))) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidStudentStatus"));
		} else if (!(StatusInSchool.equals(codeId) || StatusDelayed.equals(codeId))) {
			if ("".equals(form.getString("statusDate").trim())) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty",
																			bundle.getString("StatusDate")));
			}
			if ("".equals(form.getString("statusYear").trim())) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty",
																			bundle.getString("StatusYear")));
			}
			if ("".equals(form.getString("statusTerm").trim())) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty",
																			bundle.getString("StatusTerm")));
			}
		}
		
		buff = form.getString("statusDate").trim();
		if (!"".equals(buff) && Toolket.parseDate(buff) == null) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidDateFormat", 
																		bundle.getString("StatusDate")));
		}
		
		buff = form.getString("statusYear").trim();
		if (!"".equals(buff)) {
			try {
				Short.parseShort(buff);		
			} catch(Exception e) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidYearFormat", 
																			bundle.getString("StatusYear")));							
			}
		}
		
		codeId  = form.getString("statusCause").trim();
		codeSel = form.getString("statusCauseSel").trim();
		if (!codeId.equals(codeSel)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", 
																		"狀態原因"));						
		}
	}
	
}
