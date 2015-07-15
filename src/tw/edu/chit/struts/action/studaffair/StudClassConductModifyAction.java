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
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class StudClassConductModifyAction extends BaseLookupDispatchAction {
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

		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);

		String opmode = dynForm.getString("opmode");
		//log.debug("StudConductEdit.opmode:" + opmode);
		if(opmode.equals("ok")) return save(mapping,form,request,response);
		else if(opmode.equals("cancel"))  return cancel(mapping,form,request,response);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");

		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		setContentPage(session, "studaffair/StudClassConductModify.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Toolket.resetCheckboxCookie(response, "StudClassConduct");
		//Toolket.resetCheckboxCookie(response, "StudClassConduct", request);
		session.removeAttribute("StudClassConductInEdit");
		session.removeAttribute("StudClassConductModify");
		setContentPage(request.getSession(false), "studaffair/StudClassConduct.jsp");
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
		
		List<Just> justList = (List<Just>)session.getAttribute("StudClassConductInEdit");
		List mapList = new ArrayList();

		ActionMessages messages = validateInput(form);
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			mapList = fillFormProp(form, justList);
			session.setAttribute("StudClassConductModify", mapList);
			setContentPage(session, "studaffair/StudClassConductModify.jsp");
			return mapping.findForward("Main");
		} else {
			try {
		  		//Just just = justList.get(0);
				messages = sm.modifyClassJustByFormProp(form, justList, classInCharge);
				if (!messages.isEmpty()) {
					saveErrors(request, messages);
					mapList = fillFormProp(form, justList);
					session.setAttribute("StudClassConductModify", mapList);
					return mapping.findForward("Main");
				}
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.ModifySuccessful"));
				saveMessages(request, messages);

				//form.initialize(mapping);
				Toolket.resetCheckboxCookie(response, "StudClassConduct");
				//session.removeAttribute("StudClassConductList");
				session.removeAttribute("StudClassConductModify");
				session.removeAttribute("StudClassConductInEdit");
				setContentPage(session, "studaffair/StudClassConduct.jsp");
				return mapping.findForward("Main");
			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				mapList = fillFormProp(form, justList);
				session.setAttribute("StudClassConductModify", mapList);
				return mapping.findForward("Main");
			}
		}
	}
		
	private ActionMessages validateInput(DynaActionForm form) {
		ActionMessages msgs = new ActionMessages();

		String[] teacherScore = form.getStrings("teacherScore");
		String[] deptheaderScore = form.getStrings("deptheaderScore");
  		String[] militaryScore = form.getStrings("militaryScore");
  		String[] meetingScore = form.getStrings("meetingScore");
  		String[] comCode1 = form.getStrings("comCode1");
  		String[] comCode1Sel = form.getStrings("comCode1Sel");
  		String[] comCode2 = form.getStrings("comCode2");
  		String[] comCode2Sel = form.getStrings("comCode2Sel");
  		String[] comCode3 = form.getStrings("comCode3");
  		String[] comCode3Sel = form.getStrings("comCode3Sel");			
		
  		for(int i=0; i<teacherScore.length; i++){
		if(!teacherScore[i].trim().equals("") && !isNumeric(teacherScore[i])){
			log.debug("Conduct->teacherScore:" + teacherScore);
			
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InputNumericOnly", "->導師加減分"));
			return msgs;
		}else if(teacherScore[i].trim().equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "->導師加減分"));
			return msgs;
		}
		
		if(!deptheaderScore[i].trim().equals("") && !isNumeric(deptheaderScore[i])){
			
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InputNumericOnly", "->系主任加減分"));
			return msgs;
		}else if(deptheaderScore[i].trim().equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "->系主任加減分"));
			return msgs;
		}
		
		if(!militaryScore[i].trim().equals("") && !isNumeric(militaryScore[i])){
			
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InputNumericOnly", "->教官加減分"));
			return msgs;
		}else if(militaryScore[i].trim().equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "->教官加減分"));
			return msgs;
		}
		
		if(!meetingScore[i].trim().equals("") && !isNumeric(meetingScore[i])){
			
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InputNumericOnly", "->評審會加減分"));
			return msgs;
		}else if(meetingScore[i].trim().equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "->評審會加減分"));
			return msgs;
		}
		
		if(!comCode1[i].equals(comCode1Sel[i]) || !comCode2[i].equals(comCode2Sel[i]) || !comCode3[i].equals(comCode3Sel[i])) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.ConductMarkErr"));
			return msgs;
		}
		
  		}
		return msgs;
	}
	
	private List<Map> fillFormProp(DynaActionForm dForm, List<Just> justs){
		String[] teacherScore = dForm.getStrings("teacherScore");
		String[] deptheaderScore = dForm.getStrings("deptheaderScore");
  		String[] militaryScore = dForm.getStrings("militaryScore");
  		String[] meetingScore = dForm.getStrings("meetingScore");
  		String[] totalScore = dForm.getStrings("totalScore");
  		String[] comCode1 = dForm.getStrings("comCode1");
  		//String comCode1Sel = dForm.get("comCode1Sel").toString();
  		String[] comCode2 = dForm.getStrings("comCode2");
  		//String comCode2Sel = dForm.get("comCode2Sel").toString();
  		String[] comCode3 = dForm.getStrings("comCode3");
  		//String comCode3Sel = dForm.get("comCode3Sel").toString();			
  		
  		List<Map> mapList = new ArrayList();
  		int i = 0;
  		for(Iterator<Just> justIter=justs.iterator(); justIter.hasNext();) {
			Just just = justIter.next();
			Map fMap = new HashMap();
			
			fMap.put("studentNo", just.getStudentNo());
			fMap.put("studentName", just.getStudentName());
			fMap.put("departClass", just.getDepartClass());
			fMap.put("deptClassName", just.getDeptClassName());
			fMap.put("teacherScore", teacherScore[i]);
			fMap.put("deptheaderScore", deptheaderScore[i]);
			fMap.put("militaryScore", militaryScore[i]);
			fMap.put("meetingScore", meetingScore[i]);
			fMap.put("dilgScore", just.getDilgScore());
			fMap.put("desdScore", just.getDesdScore());
			fMap.put("totalScore", totalScore[i]);
			fMap.put("comCode1", comCode1[i]);
			fMap.put("comCode2", comCode2[i]);
			fMap.put("comCode3", comCode3[i]);
			
			mapList.add(fMap);
			i++;
		}
		
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
