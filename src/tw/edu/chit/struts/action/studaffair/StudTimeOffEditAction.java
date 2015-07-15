package tw.edu.chit.struts.action.studaffair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.text.DateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Regs;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;
import tw.edu.chit.util.Global;

public class StudTimeOffEditAction extends BaseLookupDispatchAction {
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "save");
		map.put("Cancel", "cancel");
		return map;
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Map StudTimeoffInit = (Map)session.getAttribute("StudTimeOffEditInfo");
		DynaActionForm dynForm = (DynaActionForm) form;

		String daynite = dynForm.getString("daynite");
		String studentNo = dynForm.getString("studentNo");
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();

		if (daynite.equals(""))
			StudTimeoffInit.put("daynite", "1");
		if (!studentNo.equals("")) {
			ScoreManager scm = (ScoreManager) getBean("scoreManager");
			Student student = new Student();
			student = scm.findStudentByStudentNoInCharge(studentNo, classInCharge);
			
			if (student!=null) {
				//log.debug("StudTimeOffEdit, unspecified:findStudent=" + student.getStudentName());
				StudTimeoffInit.put("studentNo", studentNo);
				StudTimeoffInit.put("studentName", student.getStudentName());
				StudTimeoffInit.put("deptClassName", student.getDepartClass2());
			} else {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.StudentNotFound", studentNo));
				saveErrors(request, errors);
			}
		}
		session.setAttribute("StudTimeOffEditInfo", StudTimeoffInit);

		setContentPage(session, "studaffair/StudTimeOffEdit.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudTimeOffEdit");
		session.removeAttribute("StudTimeOffInEdit");
		setContentPage(request.getSession(false), "studaffair/StudTimeOff.jsp");
		return mapping.findForward("Main");
	}

	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		DynaActionForm aform = (DynaActionForm) form;
		
		ActionMessages messages = validateInputForCreate(aform, Toolket
				.getBundle(request), classInCharge);

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			session.setAttribute("StudTimeOffEdit", aform.getMap());
			return mapping.findForward("Main");
		} else {
			try {
				String studentNo = aform.getString("studentNo").toUpperCase();
				int tfYear = Integer.parseInt(aform.get("tfYear").toString()) + 1911;
				int tfMonth = Integer.parseInt(aform.get("tfMonth").toString()) - 1;
				int tfDay = Integer.parseInt(aform.get("tfDay").toString());
				Calendar ddate = Calendar.getInstance(); 
				ddate.set(tfYear, tfMonth, tfDay, 0, 0, 0);
				Date sddate = ddate.getTime();
				
				ScoreManager scm = (ScoreManager)getBean("scoreManager");
				
				Student stu = scm.findStudentByStudentNo(studentNo);
				StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
				sm.createStudTimeOff(aform.getMap(), stu.getDepartClass());
				
				double justScore = sm.calDilgScoreByStudent(studentNo, "0");
				
				//messages = sm.modifyJustDilgScore(studentNo, justScore);
				
				//取代sm.modifyJustDilgScore(studentNo, justScore)該元件執行有誤  Leo20120307
				CourseManager manager = (CourseManager) getBean("courseManager");
				String sqlstudent_no = manager.ezGetString("Select student_no From just Where student_no='" + studentNo + "' ");
				double SeltotalScore = Double.parseDouble(manager.ezGetString("Select total_score From just Where student_no='" + studentNo + "' "));
				double totalScore = SeltotalScore+justScore;			
				if(sqlstudent_no.equals("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"Message.MessageN1", "找不到[" + studentNo + "]該學生的操行成績!"));
				}else{				
					manager.executeSql("Update just Set total_score="+totalScore+" Where student_no='"+studentNo+"'");
				}
				//=======================================================================
				
				
				
				if(messages.isEmpty()){
					messages = sm.modifySeldDilgPeriod(studentNo, sddate);
					if(!messages.isEmpty()){
						messages.add(messages);
					}else{
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.CreateSuccessful"));
					}
				}else{
					messages.add(messages);
				}

				saveMessages(request, messages);
				aform.set("tfYear", "");
				aform.set("tfMonth", "");
				aform.set("tfDay", "");
				aform.set("studentNo", "");
				log.debug("After modify tfYear=" + aform.getString("tfYear"));
				aform.initialize(mapping);
				session.removeAttribute("StudTimeOffEdit");
				session.removeAttribute("StudTimeOffInEdit");
				session.removeAttribute("StudTimeOffList");
				//session.removeAttribute("StudTimeoffInit");
				// request.getParameterMap().clear();
				// request.getParameterMap().remove("name");
				return mapping.findForward("success_create");
			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("StudTimeOffEdit", aform.getMap());
				return mapping.findForward("Main");
			}
		}
	}
	
	
	private ActionMessages validateInputForCreate(DynaActionForm form,
			ResourceBundle bundle, String classInCharge) {

		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		
		
		String studentNo = form.getString("studentNo");
		Student student = new Student();
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		student = scm.findStudentByStudentNoInCharge(studentNo, classInCharge);
		if (student.equals(null)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.StudentNotFound", studentNo));
		}

		
		if(errors.isEmpty()) {
			StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
			sm.validateStudTimeOffCreate(form.getMap(), errors, bundle);
		}
		return errors;
	}
	
	
	private void validateFieldFormat(DynaActionForm form,
			ActionMessages errors, ResourceBundle bundle) {

		String buff;

		buff = form.getString("studentNo").trim();
		if ("".equals(buff)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.FieldCantEmpty", bundle.getString("StudentNo")));
		}
		
		String tfYear = form.getString("tfYear").trim();
		String tfMonth = form.getString("tfMonth").trim();
		String tfDay =form.getString("tfDay").trim();
		
		if (tfYear.equals("") || tfMonth.equals("") || tfDay.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.InvalidDateInput",
								bundle.getString("Message.TimeOffDateInvalid")));
				return;
		}
		
		if (!(form.getString("tfYear").trim().equals("")
				|| form.getString("tfMonth").trim().equals("") 
				|| form.getString("tfDay").trim().equals(""))) {

			int itfYear = Integer.parseInt(form.getString("tfYear").trim()) + 1911;
			int itfMonth = Integer.parseInt(form.getString("tfMonth").trim()) - 1;
			int itfDay = Integer.parseInt(form.getString("tfDay").trim());
			log.debug("Validate StudTimeOff => tfYear:" + itfYear + " tfMonth:" + itfMonth + " tfDay:" + itfDay);
			Calendar tfdate = Calendar.getInstance();
			tfdate.clear();
			tfdate.set(itfYear,itfMonth,itfDay, 0, 0, 0);
			log.debug("Validate StudTimeOff => caYear:" + tfdate.get(Calendar.YEAR)+ " caMonth:"
					+ tfdate.get(Calendar.MONTH) + " caDay:" + tfdate.get(Calendar.DAY_OF_MONTH));

			if (tfdate.get(Calendar.YEAR) != itfYear
					|| tfdate.get(Calendar.MONTH) != itfMonth
					|| tfdate.get(Calendar.DAY_OF_MONTH) != itfDay) {

				errors.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Message.InvalidDateInput",
										bundle.getString("Message.TimeOffDateInvalid")));
			}
		}
		
	}

}
