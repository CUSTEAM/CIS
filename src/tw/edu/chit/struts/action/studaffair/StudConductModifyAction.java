package tw.edu.chit.struts.action.studaffair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

public class StudConductModifyAction extends BaseLookupDispatchAction  {
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
		//log.debug("StudConductEdit.opmode:" + opmode);
		if(opmode.equals("ok")) return save(mapping,form,request,response);
		else if(opmode.equals("cancel"))  return cancel(mapping,form,request,response);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");

		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		setContentPage(session, "studaffair/StudConductModify.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudConductInEdit");
		session.removeAttribute("StudConductModify");
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
		List<Just> justList = (List<Just>)session.getAttribute("StudConductInEdit");
		List mapList = new ArrayList();

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			mapList = fillFormProp(form, justList);
			session.setAttribute("StudConductModify", mapList);
			setContentPage(session, "studaffair/StudConductModify.jsp");
			return mapping.findForward("Main");
		} else {
			try {
		  		Just just = justList.get(0);
				messages = sm.modifyJustByFormProp(form, just, classInCharge);
				if (!messages.isEmpty()) {
					saveErrors(request, messages);
					mapList = fillFormProp(form, justList);
					session.setAttribute("StudConductModify", mapList);
					return mapping.findForward("Main");
				}
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.ModifySuccessful"));
				saveMessages(request, messages);

				//form.initialize(mapping);
				session.removeAttribute("StudConductModify");
				session.removeAttribute("StudConductInEdit");
				setContentPage(session, "studaffair/StudConduct.jsp");
				return mapping.findForward("Main");
			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				mapList = fillFormProp(form, justList);
				session.setAttribute("StudConductModify", mapList);
				setContentPage(session, "studaffair/StudConduct.jsp");
				return mapping.findForward("Main");
			}
		}
	}
		
	private ActionMessages validateInput(DynaActionForm form) {
		ActionMessages msgs = new ActionMessages();

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
		
		if(!teacherScore.trim().equals("") && !isNumeric(teacherScore)){
			log.debug("Conduct->teacherScore:" + teacherScore);
			
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InputNumericOnly", "->導師加減分"));
			return msgs;
		}else if(teacherScore.trim().equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "->導師加減分"));
			return msgs;
		} 
		
		if(!deptheaderScore.trim().equals("") && !isNumeric(deptheaderScore)){
			
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InputNumericOnly", "->系主任加減分"));
			return msgs;
		}else if(deptheaderScore.trim().equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "->系主任加減分"));
			return msgs;
		} 
		
		if(!militaryScore.trim().equals("") && !isNumeric(militaryScore)){
			
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InputNumericOnly", "->教官加減分"));
			return msgs;
		}else if(militaryScore.trim().equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "->教官加減分"));
			return msgs;
		} 
		
		if(!meetingScore.trim().equals("") && !isNumeric(meetingScore)){
			
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InputNumericOnly", "->評審會加減分"));
			return msgs;
		}else if(meetingScore.trim().equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "->評審會加減分"));
			return msgs;
		} 
		
		if(!comCode1.equals(comCode1Sel) || !comCode2.equals(comCode2Sel) || !comCode3.equals(comCode3Sel)) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.ConductMarkErr"));
			return msgs;
		}
		

		return msgs;
	}
	
	private List<Map> fillFormProp(DynaActionForm dForm, List<Just> justs){
		String teacherScore = dForm.getString("teacherScore").trim();
		String deptheaderScore = dForm.getString("deptheaderScore").trim();
  		String militaryScore = dForm.get("militaryScore").toString().trim();
  		String meetingScore = dForm.get("meetingScore").toString().trim();
  		String totalScore = dForm.get("totalScore").toString().trim();
  		String comCode1 = dForm.get("comCode1").toString();
  		//String comCode1Sel = dForm.get("comCode1Sel").toString();
  		String comCode2 = dForm.get("comCode2").toString();
  		//String comCode2Sel = dForm.get("comCode2Sel").toString();
  		String comCode3 = dForm.get("comCode3").toString();
  		//String comCode3Sel = dForm.get("comCode3Sel").toString();			
  		
  		List<Map> mapList = new ArrayList();
  		
  		//for(Iterator<Just> justIter=justs.iterator(); justIter.hasNext();) {
			Just just = justs.get(0);
			Map fMap = new HashMap();
			
			fMap.put("studentNo", just.getStudentNo());
			fMap.put("studentName", just.getStudentName());
			fMap.put("departClass", just.getDepartClass());
			fMap.put("deptClassName", just.getDeptClassName());
			fMap.put("teacherScore", teacherScore);
			fMap.put("deptheaderScore", deptheaderScore);
			fMap.put("militaryScore", militaryScore);
			fMap.put("meetingScore", meetingScore);
			fMap.put("dilgScore", just.getDilgScore());
			fMap.put("desdScore", just.getDesdScore());
			fMap.put("totalScore", totalScore);
			fMap.put("comCode1", comCode1);
			fMap.put("comCode2", comCode2);
			fMap.put("comCode3", comCode3);
			
			mapList.add(fMap);
		//}
		
		return mapList;
			
	}

	private boolean isNumeric(String doublStr) {
		String[] splitStr = StringUtils.split(doublStr, ".");
		//log.debug("isNumeric->splitStr.length" + splitStr.length);
		if(splitStr.length > 2) return false;
		for(int i =0; i<splitStr.length; i++){
			if(i==0){
				if(splitStr[0].trim().indexOf("-")==0) {
					splitStr[0] = splitStr[0].trim().substring(1);
				}
			}
			if(!StringUtils.isNumeric(splitStr[i])) return false;
		}
		return true;
	}

}
