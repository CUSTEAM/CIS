package tw.edu.chit.struts.action.score;

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

import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ScoreHistEditAction extends BaseLookupDispatchAction {
	
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("OK", 		"save");
		map.put("Cancel", 	"cancel");
		map.put("Back", 	"cancel");
		return map;		
	}

	public ActionForward unspecified(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
			throws Exception {

			HttpSession session = request.getSession(false);
			DynaActionForm aForm = (DynaActionForm)form;
			String studentNo = aForm.getString("student_no").trim();
			String cscode = aForm.getString("cscode").trim();
			String stdepartClass = aForm.getString("stdepart_class").trim();
			Map map = new HashMap();
			map.put("school_year", aForm.getString("school_year"));
			map.put("school_term", aForm.getString("school_term"));
			map.put("credit", aForm.getString("credit"));
			map.put("score", aForm.getString("score"));
			map.put("mode", aForm.getString("mode"));
			
			if (!"".equals(studentNo) || !"".equals(cscode) || !"".equals(stdepartClass)) {
				map.put("student_no", studentNo);
				map.put("cscode", cscode);
				map.put("stdepart_class", stdepartClass);
				ActionMessages errors = new ActionMessages();
				if (!"".equals(studentNo)) {
					if (lookupStudent(studentNo, map, session, errors) != null) {
						if (!errors.isEmpty()) {
							saveErrors(request, errors);
						}
					} else if (lookupGraduate(studentNo, map, session, errors) != null) {
						if (!errors.isEmpty()) {
							saveErrors(request, errors);
						}
					}
				}
				if (!"".equals(cscode)) {
					lookupCscode(cscode, map, session, errors);
					if (!errors.isEmpty()) {
						saveErrors(request, errors);
					}
				}
				if (!"".equals(stdepartClass)) {
					lookupClazzName(stdepartClass, map, session, errors);
					if (!errors.isEmpty()) {
						saveErrors(request, errors);
					}
				}
				
				request.setAttribute("ScoreHistEdit", map);
			}
			setContentPage(session, "score/ScoreHistEdit.jsp");
			return mapping.findForward("Main");
	}

	
	public ActionForward cancel(ActionMapping mapping,
			  					ActionForm form,
			  					HttpServletRequest request,
			  					HttpServletResponse response)
			throws Exception {
		
			HttpSession session = request.getSession(false);
			((DynaActionForm)form).initialize(mapping);
			session.removeAttribute("ScoreHistEdit");
			setContentPage(session, "score/ScoreHist.jsp");
			return mapping.findForward("Main");
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
		throw new Exception("'mode' property of ScoreHistEditForm is illegal.");
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
			session.setAttribute("ScoreHistEdit", form.getMap());
			return mapping.findForward("Main");
		} else {	
			try {
				ScoreManager sm = (ScoreManager)getBean("scoreManager");
				UserCredential credential = (UserCredential)session.getAttribute("Credential");
				String classInCharge = credential.getClassInChargeSqlFilter();

				messages = sm.createScoreHist(form.getMap(), classInCharge);
				if(! messages.isEmpty()){
					saveErrors(request, messages);
					session.setAttribute("ScoreHistEdit", form.getMap());
					return mapping.findForward("Main");
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.CreateSuccessful"));
					saveMessages(request, messages);
					
					// 要保留學號,學年及學期作批次新增
					String studentNo = form.getString("student_no");
					String year = form.getString("school_year");
					String term = form.getString("school_term");
					form.initialize(mapping);
					Map<String, String> initValue = new HashMap<String, String>();
					initValue.put("mode", "Create");
					initValue.put("student_no", studentNo);
					initValue.put("school_year", year);
					initValue.put("school_term", term);
					session.setAttribute("ScoreHistEdit", initValue);
					//request.getParameterMap().clear();
					//request.getParameterMap().remove("name");
					return mapping.findForward("success_create");
				}
			} catch(Exception e) {
				e.printStackTrace();
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("ScoreHistEdit", form.getMap());
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
		ScoreHist score = (ScoreHist)session.getAttribute("ScoreInEdit");
		ActionMessages messages = validateInputForModify(form, score, Toolket.getBundle(request));
		
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			session.setAttribute("ScoreHistEdit", form.getMap());
			return mapping.findForward("Main");
		} else {	
			try {
				ScoreManager sm = (ScoreManager)getBean("scoreManager");
				sm.modifyScoreHist(form.getMap(), score);
				if (Toolket.isMasterStudent(form.getMap().get("student_no")
						.toString().trim()))
					// 碩士生要更新學業平均成績
					sm.updateMasterData(form.getMap());
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ModifySuccessful"));
				saveMessages(request, messages);
				form.initialize(mapping);
				session.removeAttribute("ScoreHistEdit");
				//request.getParameterMap().clear();
				//request.getParameterMap().remove("name");
				return mapping.findForward("success_modify");	
			} catch(Exception e) {
				e.printStackTrace();
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("ScoreHistEdit", form.getMap());
				return mapping.findForward("Main");
			}
		}
	}	
	
	private ActionMessages validateInputForCreate(DynaActionForm form, ResourceBundle bundle) {
		
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		sm.validateScoreHistCreate(form.getMap(), errors, bundle);
		return errors;
	}
	
	private ActionMessages validateInputForModify(DynaActionForm form, ScoreHist score, ResourceBundle bundle) {
		// score:old data , form: new fill data, form->studentNo,cscode,school_year,school_term can't modify
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		sm.validateScoreHistModify(form.getMap(), score, errors, bundle);
		return errors;
	}
	
	private void validateFieldFormat(DynaActionForm form, ActionMessages errors, ResourceBundle bundle) {

		String buff;
		
		buff = form.getString("student_no").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("StudentNo")));
		}
		
		buff = form.getString("school_year").trim();
		if (!"".equals(buff)) {
			try {
				Short.parseShort(buff);		
			} catch(Exception e) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidYearFormat", 
																			bundle.getString("Message.ScoreHist.SchoolYearInvalid")));							
			}
		}
		
		buff = form.getString("school_term").trim();
		if (!"".equals(buff)) {
			try {
				Short.parseShort(buff);		
			} catch(Exception e) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidYearFormat", 
																			bundle.getString("Message.ScoreHist.SchoolTermInvalid")));							
			}
		}
		
		
		buff = form.getString("cscode").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("Message.ScoreHist.Cscode")));
		}
		
		buff = form.getString("credit").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("Message.ScoreHist.Credit")));
		} else {
			try {
				Float.parseFloat(buff);		
			} catch(Exception e) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreHist.InvalidFloatFormat", 
																			bundle.getString("Message.ScoreHist.Credit")));							
			}
		}
		
		buff = form.getString("score").trim();
		if ("".equals(buff) && !form.getString("evgr_type").equals("5") && !form.getString("evgr_type").equals("6")) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("Message.ScoreHist.Score")));
		} else {
			try {
				if (!"".equals(buff))
					Float.parseFloat(buff);		
			} catch(Exception e) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreHist.InvalidFloatFormat", 
																			bundle.getString("Message.ScoreHist.Score")));							
			}
		}
		
		/**
		buff   = form.getString("stdepart_class").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", 
																		bundle.getString("Message.ScoreHist.stdepartClass")));			
		}
		*/
	}

	private Student lookupStudent(String studentNo, Map result, HttpSession session, ActionMessages errors) {
		
		ScoreManager manager = (ScoreManager)getBean("scoreManager");
		//ActionMessages errors = new ActionMessages();
		Student student = manager.findStudentByStudentNo(studentNo);
		String classNo = "";
		if (student == null) {
			result.put("studentfound", false);
		} else {
			result.put("studentfound", true);
			if (student != null) {
				result.put("student_name",  student.getStudentName());
				result.put("student_class", student.getDepartClass());
				result.put("student_classname", student.getDepartClass2());				
				classNo = student.getDepartClass().trim();
			}
			if (!getUserCredential(session).isClassInCharge(classNo)) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoRight"));
			//} else if (!StatusQuit.equals(grad.getOccurStatus())) {
			//	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoQuitRecord"));
			//} else {
			//	populateFormProperties(result, grad);
			//	session.setAttribute("GraduateInEdit", grad);
			}
		}
		return student;
	}

	private Graduate lookupGraduate(String studentNo, Map result, HttpSession session, ActionMessages errors) {
		
		ScoreManager manager = (ScoreManager)getBean("scoreManager");
		//ActionMessages errors = new ActionMessages();
		Graduate graduate = manager.findGraduateByStudentNo(studentNo);
		String classNo;
		
		if (graduate == null) {
			result.put("studentfound", false);
		} else {
			result.put("studentfound", true);
			result.put("student_name",  graduate.getStudentName());
			result.put("student_class", graduate.getDepartClass());
			result.put("student_classname", graduate.getDepartClass2());				
			classNo = graduate.getDepartClass().trim();
			if (!getUserCredential(session).isClassInCharge(classNo)) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoRight"));
			}
		}
		return graduate;
	}

	private String lookupCscode(String cscode, Map result, HttpSession session, ActionMessages errors) {
		
		ScoreManager manager = (ScoreManager)getBean("scoreManager");
		//ActionMessages errors = new ActionMessages();
		String cscodeName = manager.findCourseName(cscode);
		
		if (cscodeName == null) {
			result.put("cscodefound", false);
		} else {
			result.put("cscodefound", true);
			result.put("cscode_name",  cscodeName);
		}
		return cscodeName;
	}

	private String lookupClazzName(String stdepartClass, Map result, HttpSession session, ActionMessages errors) {
		
		ScoreManager manager = (ScoreManager)getBean("scoreManager");
		//ActionMessages errors = new ActionMessages();
		String stdepClassName = manager.findClassName(stdepartClass);
		
		if (stdepClassName.equals("")) {
			result.put("classfound", false);
		} else {
			result.put("classfound", true);
			result.put("stdeptclass_name",  stdepClassName);
		}
		return stdepClassName;
	}

}
