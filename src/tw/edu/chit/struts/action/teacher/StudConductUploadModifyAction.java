package tw.edu.chit.struts.action.teacher;

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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code1;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class StudConductUploadModifyAction extends BaseLookupDispatchAction {
	/*
	 * 注意:前台JSP的botton屬性如果是用Submit,則會直接使用MethodMap指定的方法
	 *     若是為控制前台tab index則submit botton 的屬性設為botton,
	 *     此時須經由 request parameter : opmode 在 unspecified 方法中處理
	 *     
	 * @see org.apache.struts.actions.LookupDispatchAction#getKeyMethodMap()
	 */
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("StartUpload", "save");
		map.put("Back", "back");
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
		if(opmode.equals("StartUpload")) return save(mapping,form,request,response);
		else if(opmode.equals("cancel"))  return cancel(mapping,form,request,response);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		List<Code1> ConductMarkList = sm.findConductMark("");
		session.setAttribute("ConductMark", ConductMarkList);
		
		setContentPage(session, "teacher/ConductClassChoose.jsp");
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
		String uName = credential.getMember().getName();
				
		//int number = Integer.parseInt(form.get("number").toString().trim());
		Map initMap = (Map)session.getAttribute("StudConductUploadModifyInit");
		String uploadMode = initMap.get("uploadMode").toString();
		
		String classInCharge = credential.getClassInChargeSqlFilterSAF();

		int num;
		
		ActionMessages messages = validateInput(form, uploadMode);
		List<Just> justList = (List<Just>)session.getAttribute("StudConductUploadInEdit");
		List mapList = new ArrayList();
		mapList = fillFormProp(form, justList, uploadMode);
		
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			//mapList = fillFormProp(form, justList, uploadMode);
			session.setAttribute("StudConductUploadModify", mapList);
			return mapping.findForward("Main");
		} else {
			try {
				messages = sm.modifyUploadJustByFormProp(form, justList, classInCharge, uploadMode);
				if (!messages.isEmpty()) {
					saveErrors(request, messages);
					//mapList = fillFormProp(form, justList, uploadMode);
					session.setAttribute("StudConductUploadModify", mapList);
					return mapping.findForward("Main");
				}
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.UploadSuccessful"));
				saveMessages(request, messages);

				//form.initialize(mapping);
				Map pmap = new HashMap();
				pmap.putAll(initMap);
				//pmap.put("opmode", "Print");
				pmap.put("schoolYear", Toolket.getSysParameter("School_year"));
				pmap.put("schoolTerm", Toolket.getSysParameter("School_term"));
				pmap.put("Name", uName);
				pmap.put("Conduct2Print", mapList);
				session.setAttribute("ConductUploadPrint", pmap);
				
				session.removeAttribute("StudConductUploadModify");
				session.removeAttribute("StudConductUploadInEdit");
				session.setMaxInactiveInterval(Integer.parseInt(session.getAttribute("sessionInterval").toString()));
				setContentPage(session, "teacher/ConductClassChoose.jsp");
				return mapping.findForward("Main");
			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				//mapList = fillFormProp(form, justList, uploadMode);
				session.setAttribute("StudConductUploadModify", mapList);
				return mapping.findForward("Main");
			}
		}
	}

	public ActionForward back(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("ConductUploadPrint");
		session.removeAttribute("StudConductUploadInEdit");
		session.removeAttribute("StudConductUploadModify");

		setContentPage(request.getSession(false), "teacher/ConductClassChoose.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		//session.removeAttribute("StudConductUploadInit");
		session.removeAttribute("ConductUploadPrint");
		session.removeAttribute("StudConductUploadInEdit");
		session.removeAttribute("StudConductUploadModify");
		session.setMaxInactiveInterval(Integer.parseInt(session.getAttribute("sessionInterval").toString()));
		setContentPage(request.getSession(false), "teacher/ConductClassChoose.jsp");

		//setContentPage(request.getSession(false), "teacher/StudConduct.jsp");
		return mapping.findForward("Main");
	}


	private ActionMessages validateInput(DynaActionForm form, String mode) {
		ActionMessages msgs = new ActionMessages();
	  	double score;

  		if(mode.equalsIgnoreCase("Teacher")) {
  			String[] teacherScore = form.getStrings("teacherScore");
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
	  	  		}else if(!teacherScore[i].trim().equals("") && isNumeric(teacherScore[i])){
	  	  			score = Double.parseDouble(teacherScore[i].trim());
	  	  			if(score > 4d || score < -4.01d){
		  	  			msgs.add(ActionMessages.GLOBAL_MESSAGE,
		  	  					new ActionMessage("Message.generic", "導師加減分最多為4分!"));
		  	  			return msgs;
	  	  			}
	  	  		}
	  	  		
	  			if(!comCode1[i].equals(comCode1Sel[i]) || !comCode2[i].equals(comCode2Sel[i]) || !comCode3[i].equals(comCode3Sel[i])) {
	  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
	  						new ActionMessage("Message.ConductMarkErr"));
	  				return msgs;
	  			}
  	  		}
  		}else if(mode.equalsIgnoreCase("Chairman")) {
  			String[] deptheaderScore = form.getStrings("deptheaderScore");
  	  		for(int i=0; i<deptheaderScore.length; i++){
	  			if(!deptheaderScore[i].trim().equals("") && !isNumeric(deptheaderScore[i])){
	  				
	  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
	  						new ActionMessage("Message.InputNumericOnly", "->系主任加減分"));
	  				return msgs;
	  			}else if(deptheaderScore[i].trim().equals("")){
	  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
	  						new ActionMessage("Message.MustInput", "->系主任加減分"));
	  				return msgs;
	  			}else if(!deptheaderScore[i].trim().equals("") && isNumeric(deptheaderScore[i])){
	  				score = Double.parseDouble(deptheaderScore[i].trim());
	  				if(score > 2d || score < -2.01d){
	  					msgs.add(ActionMessages.GLOBAL_MESSAGE,
	  	  					new ActionMessage("Message.generic", "系主任加減分最多為2分!"));
	  					return msgs;
	  				}
	  			}
  	  		}
  		}else if(mode.equalsIgnoreCase("Drillmaster")){
  	  		String[] militaryScore = form.getStrings("militaryScore");
  	  		for(int i=0; i<militaryScore.length; i++){
	  	  		if(!militaryScore[i].trim().equals("") && !isNumeric(militaryScore[i])){
	  				
	  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
	  						new ActionMessage("Message.InputNumericOnly", "->教官加減分"));
	  				return msgs;
	  			}else if(militaryScore[i].trim().equals("")){
	  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
	  						new ActionMessage("Message.MustInput", "->教官加減分"));
	  				return msgs;
	  	  		}else if(!militaryScore[i].trim().equals("") && isNumeric(militaryScore[i])){
	  	  			score = Double.parseDouble(militaryScore[i].trim());
	  	  			if(score > 4d || score < -4.01d){
		  	  			msgs.add(ActionMessages.GLOBAL_MESSAGE,
		  	  					new ActionMessage("Message.generic", "教官加減分最多為4分!"));
		  	  			return msgs;
	  	  			}
	  	  		}
  	  		}
  			
  		}
		
		return msgs;
	}
	
	private List<Map> fillFormProp(DynaActionForm dForm, List<Just> justs, String mode){
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		String[] teacherScore = dForm.getStrings("teacherScore");
		String[] deptheaderScore = dForm.getStrings("deptheaderScore");
  		String[] militaryScore = dForm.getStrings("militaryScore");
  		String[] meetingScore = dForm.getStrings("meetingScore");
  		String[] totalScore = dForm.getStrings("totalScore");
  		String[] comCode1 = new String[teacherScore.length];
  		String[] comCode2 = new String[teacherScore.length];
  		String[] comCode3 = new String[teacherScore.length];
  		String comCodeName = "";
  		List<Code1> comCodeList;
  		
  		if(mode.equalsIgnoreCase("Teacher")) {
  	  		comCode1 = dForm.getStrings("comCode1");
  	  		comCode2 = dForm.getStrings("comCode2");
  	  		comCode3 = dForm.getStrings("comCode3");
  		}
  		
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
	  		if(mode.equalsIgnoreCase("Teacher")) {
				fMap.put("comCode1", comCode1[i]);
				if(!comCode1[i].trim().equals("")) {
					comCodeList = sm.findConductMark(comCode1[i]);
					if(!comCodeList.isEmpty()) {
						fMap.put("comName1", comCodeList.get(0).getName());
					}
				}else {
					fMap.put("comName1", "");
				}
				fMap.put("comCode2", comCode2[i]);
				if(!comCode2[i].trim().equals("")) {
					comCodeList = sm.findConductMark(comCode2[i]);
					if(!comCodeList.isEmpty()) {
						fMap.put("comName2", comCodeList.get(0).getName());
					}
				}else {
					fMap.put("comName2", "");
				}
				fMap.put("comCode3", comCode3[i]);
				if(!comCode3[i].trim().equals("")) {
					comCodeList = sm.findConductMark(comCode3[i]);
					if(!comCodeList.isEmpty()) {
						fMap.put("comName3", comCodeList.get(0).getName());
					}
				}else {
					fMap.put("comName3", "");
				}
	  		} else {
				fMap.put("comCode1", just.getComCode1());
				fMap.put("comName1", just.getComName1());
				fMap.put("comCode2", just.getComCode2());
				fMap.put("comName2", just.getComName2());
				fMap.put("comCode3", just.getComCode3());
				fMap.put("comName3", just.getComName3());
	  		}
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
