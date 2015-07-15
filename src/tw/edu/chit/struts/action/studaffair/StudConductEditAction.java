package tw.edu.chit.struts.action.studaffair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import tw.edu.chit.model.Just;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class StudConductEditAction  extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "save");
		map.put("Back", "cancel");
		map.put("Cancel", "cancel");
		return map;
	}
	
	/**
	 * @comment Action預設之執行方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map StudBonusPenaltyInfo = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);

		String opmode = dynForm.getString("opmode");
		log.debug("StudConductEdit.opmode:" + opmode);
		if(opmode.equals("ok")) return save(mapping,form,request,response);
		else if(opmode.equals("cancel"))  return cancel(mapping,form,request,response);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");

		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		setContentPage(session, "studaffair/StudConductEdit.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudConductEdit");
		setContentPage(request.getSession(false), "studaffair/StudConduct.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward save(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("Bonuspenalty.save called!");
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		DynaActionForm form = (DynaActionForm)aform;
		
		//int number = Integer.parseInt(form.get("number").toString().trim());
		
		String classInCharge = credential.getClassInChargeSqlFilterSAF();

		int num;
		
		ActionMessages messages = validateInput(form);

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			
			session.setAttribute("StudConductEdit", fillFormProp(form));
			setContentPage(session, "studaffair/StudConductEdit.jsp");
			return mapping.findForward("Main");
		} else {
			try {
		  				  			
				messages = sm.createJustByFormProp(form, classInCharge);
				if (!messages.isEmpty()) {
					saveErrors(request, messages);
					session.setAttribute("StudConductEdit", fillFormProp(form));
					return mapping.findForward("Main");
				}
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.CreateSuccessful"));
				saveMessages(request, messages);

				form.initialize(mapping);
				session.removeAttribute("StudConductEdit");
				setContentPage(session, "studaffair/StudConductEdit.jsp");
				return mapping.findForward("Main");
			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("StudConductEdit", fillFormProp(form));
				setContentPage(session, "studaffair/StudConductEdit.jsp");
				return mapping.findForward("Main");
			}
		}
	}
		
	private ActionMessages validateInput(DynaActionForm form) {
		ActionMessages msgs = new ActionMessages();

		String studentNo = form.getString("studentNo");
		String teacherScore = form.getString("teacherScore").trim();
		String deptheaderScore = form.getString("deptheaderScore").trim();
  		String militaryScore = form.get("militaryScore").toString().trim();
  		String meetingScore = form.get("meetingScore").toString().trim();
  		String comCode1 = form.get("comCode1").toString();
  		String comCode1Sel = form.get("comCode1Sel").toString();
  		String comCode2 = form.get("comCode2").toString();
  		String comCode2Sel = form.get("comCode2Sel").toString();
  		String comCode3 = form.get("comCode3").toString();
  		String comCode3Sel = form.get("comCode3Sel").toString();			
		
		if(!teacherScore.equals("") && !isNumeric(teacherScore)){
			
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InputNumericOnly", "導師加減分"));
			return msgs;
		}
		if(!deptheaderScore.equals("") && !isNumeric(deptheaderScore)){
			
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InputNumericOnly", "系主任加減分"));
			return msgs;
		}
		if(!militaryScore.equals("") && !isNumeric(militaryScore)){
			
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InputNumericOnly", "教官加減分"));
			return msgs;
		}
		if(!meetingScore.equals("") && !isNumeric(meetingScore)){
			
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InputNumericOnly", "評審會加減分"));
			return msgs;
		}
		
		if(!comCode1.equals(comCode1Sel) || !comCode2.equals(comCode2Sel) || !comCode3.equals(comCode3Sel)) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.ConductMarkErr"));
			return msgs;
		}
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
			if(studentNo.trim().equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.StudentNoMustInput"));
				return msgs;

			}

			Student student;
			student = scm.findStudentByStudentNo(studentNo);
			if(student == null) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.StudentNotFound",studentNo));
				return msgs;
			}

		return msgs;
	}

	private Map fillFormProp(DynaActionForm dForm) {
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		
		Map edMap = dForm.getMap();
		String studentNo = dForm.getString("studentNo");
		Student student = scm.findStudentByStudentNo(studentNo);
		
		double[] scores = sm.calConductScoreOfDilgDesd(studentNo);
		
		edMap.put("studentName", student.getStudentName());
		edMap.put("departClass", student.getDepartClass());
		edMap.put("deptClassName", Toolket.getClassFullName(student.getDepartClass()));
		edMap.put("dilgScore", scores[0]);
		edMap.put("desdScore", scores[1]);
		
		return edMap;
	}
	
	private boolean isNumeric(String doublStr) {
		String[] splitStr = StringUtils.split(doublStr, ".");
		//log.debug("isNumeric->splitStr.length" + splitStr.length);
		if(splitStr.length > 2) return false;
		for(int i =0; i<splitStr.length; i++){
			if(!StringUtils.isNumeric(splitStr[i])) return false;
		}
		return true;
	}

}
