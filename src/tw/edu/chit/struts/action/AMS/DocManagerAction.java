package tw.edu.chit.struts.action.AMS;

import java.util.ArrayList;
import java.util.Calendar;
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

import tw.edu.chit.dao.AmsDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.AmsAskLeave;
import tw.edu.chit.model.AmsDocApply;
import tw.edu.chit.model.AmsMeeting;
import tw.edu.chit.model.Code2;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Desd;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AmsManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class DocManagerAction  extends BaseLookupDispatchAction{
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Query",	"query");
		map.put("Add",		"add");
		map.put("Revoke",	"revoke");
		map.put("Modify",	"modify");
		map.put("Delete",	"delete");
		map.put("DeleteConfirm", "delConfirm");
		map.put("Save",		"save");
		map.put("Cancel", 	"cancel");
		map.put("Back", 	"back");
		return map;		
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
	throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		String opmode = dynForm.getString("opmode");
		String docType = dynForm.getString("docType");
				
		Map initValue = new HashMap();
		initValue.put("docType", docType);
		
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		if(Toolket.isPureTeacher(user.getMember().getIdno())){
			session.setAttribute("AmsIsTeacher", "1");
		}else{
			session.setAttribute("AmsIsTeacher", "0");
		}
		
		AmsManager ams = (AmsManager)getBean("amsManager");
		List<AmsDocApply> docList = ams.getDocApplyByIdno(user.getMember().getIdno(), docType);
		
		if(!docList.isEmpty()){
			for(AmsDocApply doc:docList){
				ams.setDocExtraData(doc);
			}
		}
		
		if(docType.equals(IConstants.AMSDocAskLeave)){
			Toolket.resetCheckboxCookie(response, "DocAskLeave");
			session.setAttribute("DocAskLeaveInit", initValue);
			session.setAttribute("DocAskLeaveList", docList);
			setContentPage(session, "AMS/DocAskLeave.jsp");
		}else if(docType.equals(IConstants.AMSDocOverTime)){
			Toolket.resetCheckboxCookie(response, "DocOverTime");
			session.setAttribute("DocOverTimeInit", initValue);
			session.setAttribute("DocOverTimeList", docList);
			setContentPage(session, "AMS/DocOverTime.jsp");
		}else if(docType.equals(IConstants.AMSDocRepair)){
			Toolket.resetCheckboxCookie(response, "DocRepair");
			session.setAttribute("DocRepairInit", initValue);
			session.setAttribute("DocRepairList", docList);
			setContentPage(session, "AMS/DocRepair.jsp");
		}else if(docType.equals(IConstants.AMSDocRevoke)){
			Toolket.resetCheckboxCookie(response, "DocRevoke");
			session.setAttribute("DocRevokeInit", initValue);
			session.setAttribute("DocRevokeList", docList);
			setContentPage(session, "AMS/DocRevoke.jsp");
		}
		return mapping.findForward("Main");				
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		String docType = aForm.getString("docType");
		String opmode = aForm.getString("opmode");
		
		Map initValue = new HashMap();
		//if(opmode.equals("revoke")){
		//	initValue.put("docType", IConstants.AMSDocAskLeave);
		//	docType = IConstants.AMSDocAskLeave;
		//}else{
			initValue.put("docType", docType);
		//}
		
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		AmsManager ams = (AmsManager)getBean("amsManager");
		List<AmsDocApply> docList = ams.getDocApplyByIdno(user.getMember().getIdno(), docType);
		
		if(!docList.isEmpty()){
			for(AmsDocApply doc:docList){
				ams.setDocExtraData(doc);
			}
		}

		if(docType.equals(IConstants.AMSDocAskLeave)){
			Toolket.resetCheckboxCookie(response, "DocAskLeave");
			session.setAttribute("DocAskLeaveInit", initValue);
			session.setAttribute("DocAskLeaveList", docList);
			setContentPage(session, "AMS/DocAskLeave.jsp");
		}else if(docType.equals(IConstants.AMSDocOverTime)){
			Toolket.resetCheckboxCookie(response, "DocOverTime");
			session.setAttribute("DocOverTimeInit", initValue);
			session.setAttribute("DocOverTimeList", docList);
			setContentPage(session, "AMS/DocOverTime.jsp");
		}else if(docType.equals(IConstants.AMSDocRepair)){
			Toolket.resetCheckboxCookie(response, "DocRepair");
			session.setAttribute("DocRepairInit", initValue);
			session.setAttribute("DocRepairList", docList);
			setContentPage(session, "AMS/DocRepair.jsp");
		}else if(docType.equals(IConstants.AMSDocRevoke)){
			//if(opmode.equals("revoke")){
			//	session.setAttribute("DocAskLeaveInit", initValue);
			//	session.setAttribute("DocAskLeaveList", docList);
			//	setContentPage(request, "AMS/DocAskLeave.jsp");
			//}else{
				Toolket.resetCheckboxCookie(response, "DocRevoke");
				session.setAttribute("DocRevokeInit", initValue);
				session.setAttribute("DocRevokeList", docList);
				setContentPage(session, "AMS/DocRevoke.jsp");
			//}
		}
		return mapping.findForward("Main");				
	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map DocApplyInit = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		
		String docType = dynForm.getString("docType");
		
		AmsManager ams = (AmsManager)getBean("amsManager");
						
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String idno = credential.getMember().getIdno();
		
		DocApplyInit.put("opmode", "add");
		DocApplyInit.put("docType", docType);
				
		session.removeAttribute("DocError");
		//session.removeAttribute("DocApplyInEdit");
		if(docType.equals(IConstants.AMSDocAskLeave)){
			List<AmsAskLeave> askList = ams.getAllAskLeave();
			session.setAttribute("askLeaveList", askList);
			session.setAttribute("DocAskLeaveEditInit", DocApplyInit);
			setContentPage(request, "AMS/DocAskLeaveEdit.jsp");
		}else if(docType.equals(IConstants.AMSDocOverTime)){
			session.setAttribute("DocOverTimeEditInit", DocApplyInit);
			setContentPage(request, "AMS/DocOverTimeEdit.jsp");
		}else if(docType.equals(IConstants.AMSDocRepair)){
			session.setAttribute("DocRepairEditInit", DocApplyInit);
			setContentPage(request, "AMS/DocRepairEdit.jsp");
		}else if(docType.equals(IConstants.AMSDocRevoke)){
			Calendar today = Calendar.getInstance();
			Calendar start = Calendar.getInstance();
			List<AmsDocApply> askLeaves = new ArrayList<AmsDocApply>();
			List<AmsDocApply> docs = ams.getDocApplyByIdno(idno, IConstants.AMSDocAskLeave);
			for(AmsDocApply doc:docs){
				if(doc.getStatus()!=null){
					if(doc.getStatus().equals(IConstants.AMSCodeDocProcessOK)){
						start.setTimeInMillis(doc.getStartDate().getTime());
						start.set(Calendar.HOUR_OF_DAY, 23);
						start.set(Calendar.MINUTE, 59);
						start.set(Calendar.SECOND, 59);
						start.add(Calendar.DAY_OF_MONTH, 7);
						if(start.compareTo(today) >= 0){
							ams.setDocExtraData(doc);
							askLeaves.add(doc);
						}
					}
				}
			}
			session.setAttribute("askLeaves", askLeaves);
			session.setAttribute("DocRevokeEditInit", DocApplyInit);
			setContentPage(request, "AMS/DocRevokedChoose.jsp");
		}
		return mapping.findForward("Main");
	}

	public ActionForward revoke(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map DocApplyInit = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		
		String docType = IConstants.AMSDocRevoke;
		String opmode = dynForm.getString("opmode").trim();
		
		AmsManager ams = (AmsManager)getBean("amsManager");
						
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		List<AmsDocApply> selDocs = getRevokeSelectedList(request, docType);
		if(dynForm.getString("docType").equals(IConstants.AMSDocAskLeave)){
			selDocs = getRevokeSelectedList(request, "DocAskLeave");
		}else if(opmode.equals("add")){
			selDocs = getRevokeSelectedList(request, "DocAskLeaveC");
		}
		AmsDocApply doc;
		doc = selDocs.get(0);
		ActionMessages messages = new ActionMessages();
		if(doc.getStatus()!=null){
			if(doc.getStatus().equals(IConstants.AMSCodeDocRevokeOK)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","該假單已銷假!"));
				saveErrors(request, messages);
				return query(mapping, (ActionForm)form, request, response);
			}else if(!doc.getStatus().equals(IConstants.AMSCodeDocProcessOK)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","該假單並未核准!無須銷假!"));
				saveErrors(request, messages);
				return query(mapping, (ActionForm)form, request, response);
			} 
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","該假單尚無處理狀態!無須銷假!"));
			saveErrors(request, messages);
			
			return query(mapping, (ActionForm)form, request, response);
		}
		
		if(ams.getRevokedDocByAskLeaveDoc(doc.getOid()) != null){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","該假單已建立過銷假單了!"));
			saveErrors(request, messages);
			
			return query(mapping, (ActionForm)form, request, response);
		}
		
		Calendar today = Calendar.getInstance();
		Calendar start = Calendar.getInstance();
		start.setTimeInMillis(doc.getStartDate().getTime());
		start.add(Calendar.DAY_OF_MONTH, 8);
		if(start.compareTo(today) < 0){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","該假單已超過銷假期限!"));
			saveErrors(request, messages);
			
			return query(mapping, (ActionForm)form, request, response);
		}
		ams.setDocExtraData(doc);
		Map initValue = new HashMap();
		DocApplyInit.put("opmode", "revoke");
		DocApplyInit.put("docType", IConstants.AMSDocRevoke);
				
		session.setAttribute("DocRevokeEditInit", DocApplyInit);
		session.setAttribute("Doc4Revoke", doc);
		setContentPage(request, "AMS/DocRevokeEdit.jsp");
		return mapping.findForward("Main");

	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		String docType = aForm.getString("docType");

		HttpSession session = request.getSession(false);
		AmsManager ams = (AmsManager)getBean("amsManager");
		MemberManager mm = (MemberManager)getBean("memberManager");
		
		session.removeAttribute("DocError");
		List<AmsDocApply> selDocs = getDocSelectedList(request, docType);
		AmsDocApply doc;
		List editList = new ArrayList();
		
		doc = selDocs.get(0);
		
		if(doc.getStatus() == null){
			ams.setDocExtraData(doc);
			if(docType.equals(IConstants.AMSDocRevoke)){
				ams.setDocExtraData(doc.getRevokedDoc());
			}
			
			Map initValue = new HashMap();
			initValue.put("opmode", "modify");
			initValue.put("docType", docType);
			
			if(docType.equals(IConstants.AMSDocAskLeave)){
				List<AmsAskLeave> askList = ams.getAllAskLeave();
				session.setAttribute("askLeaveList", askList);
				session.setAttribute("DocAskLeaveModifyInit", initValue);
				session.setAttribute("DocAskLeaveInEdit", doc);
				setContentPage(request, "AMS/DocAskLeaveModify.jsp");
			}else if(docType.equals(IConstants.AMSDocOverTime)){
				session.setAttribute("DocOverTimeModifyInit", initValue);
				session.setAttribute("DocOverTimeInEdit", doc);
				setContentPage(request, "AMS/DocOverTimeModify.jsp");
			}else if(docType.equals(IConstants.AMSDocRepair)){
				session.setAttribute("DocRepairModifyInit", initValue);
				session.setAttribute("DocRepairInEdit", doc);
				setContentPage(request, "AMS/DocRepairModify.jsp");
			}else if(docType.equals(IConstants.AMSDocRevoke)){
				session.setAttribute("DocRevokeModifyInit", initValue);
				session.setAttribute("DocRevokeInEdit", doc);
				setContentPage(request, "AMS/DocRevokeModify.jsp");
			}
		}else{
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","該申請單已經人事室處理過了!無法再修改!"));
			saveErrors(request, messages);
			
			return query(mapping, (ActionForm)form, request, response);
		}
		return mapping.findForward("Main");

	}

	public ActionForward save(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("Bonuspenalty.save called!");
		HttpSession session = request.getSession(false);
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		AmsManager ams = (AmsManager)getBean("amsManager");
		DynaActionForm form = (DynaActionForm)aform;
		
		String docType = form.getString("docType");
		
		Map DocApplyInfo = (Map)session.getAttribute("DocApplyInfo");

		ActionMessages messages = new ActionMessages();
		String opmode = form.getString("opmode");
		
		AmsDocApply doc = new AmsDocApply();
		if(opmode.equals("modify")){
			doc = ams.getDocApplyByOid(Integer.parseInt(form.getString("oid").trim()));
		}
		 
		if(opmode.equals("modify")){
			if(docType.equals(IConstants.AMSDocAskLeave)){
				setContentPage(request, "AMS/DocAskLeaveModify.jsp");
			}else if(docType.equals(IConstants.AMSDocOverTime)){
				setContentPage(request, "AMS/DocOverTimeModify.jsp");
			}else if(docType.equals(IConstants.AMSDocRepair)){
				setContentPage(request, "AMS/DocRepairModify.jsp");
			}else if(docType.equals(IConstants.AMSDocRevoke)){
				setContentPage(request, "AMS/DocRevokeModify.jsp");
			}
			if(docType.equals(IConstants.AMSDocAskLeave)){
				session.setAttribute("DocAskLeaveModifyForm", form.getMap());
			}else if(docType.equals(IConstants.AMSDocOverTime)){
				session.setAttribute("DocOverTimeModifyForm", form.getMap());
			}else if(docType.equals(IConstants.AMSDocRepair)){
				session.setAttribute("DocRepairModifyForm", form.getMap());
			}else if(docType.equals(IConstants.AMSDocRevoke)){
				session.setAttribute("DocRevokeModifyForm", form.getMap());
			}
		}else if(opmode.equals("add")){
			if(docType.equals(IConstants.AMSDocAskLeave)){
				setContentPage(request, "AMS/DocAskLeaveEdit.jsp");
			}else if(docType.equals(IConstants.AMSDocOverTime)){
				setContentPage(request, "AMS/DocOverTimeEdit.jsp");
			}else if(docType.equals(IConstants.AMSDocRepair)){
				setContentPage(request, "AMS/DocRepairEdit.jsp");
			}
			if(docType.equals(IConstants.AMSDocAskLeave)){
				session.setAttribute("DocAskLeaveEditForm", form.getMap());
			}else if(docType.equals(IConstants.AMSDocOverTime)){
				session.setAttribute("DocOverTimeEditForm", form.getMap());
			}else if(docType.equals(IConstants.AMSDocRepair)){
				session.setAttribute("DocRepairEditForm", form.getMap());
			}
		}else if(opmode.equals("revoke")){
			setContentPage(request, "AMS/DocRevokeEdit.jsp");
			session.setAttribute("DocRevokeEditForm", form.getMap());
		}
		
		//計算請假日數
		String startYear = form.getString("startYear").trim();
		String startMonth = form.getString("startMonth").trim();
		String startDay = form.getString("startDay").trim();
		String startHour = form.getString("startHour").trim();
		String startMinute = form.getString("startMinute").trim();

		String endYear = form.getString("endYear").trim();
		String endMonth = form.getString("endMonth").trim();
		String endDay = form.getString("endDay").trim();
		String endHour = form.getString("endHour").trim();
		String endMinute = form.getString("endMinute").trim();

		Calendar startCal = Calendar.getInstance();
		startCal.clear();
		if (!(startYear.equals("") || startMonth.equals("")
				|| startDay.equals("") || startHour.equals("") || startMinute
				.equals(""))) {
			startCal.set(Integer.parseInt(startYear), Integer
					.parseInt(startMonth) - 1, Integer.parseInt(startDay),
					Integer.parseInt(startHour), Integer.parseInt(startMinute),
					0);
		}

		Calendar endCal = Calendar.getInstance();
		endCal.clear();
		if (!(endYear.equals("") || endMonth.equals("") || endDay.equals("")
				|| endHour.equals("") || endMinute.equals(""))) {
			endCal.set(Integer.parseInt(endYear),
					Integer.parseInt(endMonth) - 1, Integer.parseInt(endDay),
					Integer.parseInt(endHour), Integer.parseInt(endMinute), 0);
		}

		Map chkMap = new HashMap();
		String idno = user.getMember().getIdno();
		boolean isTeacher = Toolket.isPureTeacher(idno);
		int days[] = {0,0,0,0};
		if(docType.equals(IConstants.AMSDocAskLeave) && !isTeacher){
			String askLeaveType = form.getString("askLeaveType").trim();
			chkMap = ams.calAskLeaveDays(user.getMember().getIdno(), askLeaveType, startCal, endCal);
			StringBuffer errors = new StringBuffer();
			if(chkMap.get("err1")!=null || chkMap.get("err2")!=null ||
				chkMap.get("err3")!=null ||chkMap.get("err4")!=null){
				if(chkMap.get("err1")!=null){
					errors.append((String)chkMap.get("err1"));
				}
				if(chkMap.get("err2")!=null){
					errors.append((String)chkMap.get("err2"));
				}
				if(chkMap.get("err3")!=null){
					errors.append((String)chkMap.get("err3"));
				}
				if(chkMap.get("err4")!=null){
					errors.append((String)chkMap.get("err4"));
				}
				
			}
			if(errors.length() > 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1",errors));
				saveErrors(request, messages);
				return mapping.findForward("Main");
			}else{
				days = (int[])chkMap.get("days");
				form.set("totalDay", "" + days[0]);
				form.set("totalHour", "" + days[1]);
				form.set("totalMinute", "" + days[2]);
			}
		}else if(docType.equals(IConstants.AMSDocOverTime) && !isTeacher){
			chkMap = ams.calOverTimeDays(user.getMember().getIdno(), startCal, endCal);
			StringBuffer errors = new StringBuffer();
			if(chkMap.get("err1")!=null || chkMap.get("err2")!=null ||
				chkMap.get("err3")!=null ||chkMap.get("err4")!=null){
				if(chkMap.get("err1")!=null){
					errors.append((String)chkMap.get("err1"));
				}
				if(chkMap.get("err2")!=null){
					errors.append((String)chkMap.get("err2"));
				}
				if(chkMap.get("err3")!=null){
					errors.append((String)chkMap.get("err3"));
				}
				if(chkMap.get("err4")!=null){
					errors.append((String)chkMap.get("err4"));
				}
				
			}
			if(errors.length() > 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1",errors));
				saveErrors(request, messages);
				return mapping.findForward("Main");
			}else{
				days = (int[])chkMap.get("days");
				if(days[0]==0 && days[1]==0 && days[2]==0){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","加班時數未達4小時!請調整加班起迄時間!"));
					saveErrors(request, messages);
					return mapping.findForward("Main");
				}
				if(days[1]==4){
					form.set("totalDay", "0.5");
					form.set("totalHour", "0");
					form.set("totalMinute", "0");
				}else{
					form.set("totalDay", "" + days[0]);
					form.set("totalHour", "" + days[1]);
					form.set("totalMinute", "" + days[2]);
				}
			}
		}
		
		messages = validateInput(form, session);

		if (!messages.isEmpty()) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","輸入結果為:" + form.getString("totalDay") + "日" + 
					form.getString("totalHour") + "時" + 
					form.getString("totalMinute") + "分"));
			saveErrors(request, messages);
		} else {
			try {
				messages = ams.saveDocByForm(form, user);
		  		if(messages.isEmpty()){
		  			if(opmode.equals("add") || opmode.equals("revoke")){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.CreateSuccessful"));
		  			}else if(opmode.equals("modify")){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.ModifySuccessful"));
		  			}
					if(docType.equals(IConstants.AMSDocAskLeave)){
						//messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						//"MessageN1", ", 請假日數:" + days[0] + "日" + days[1] + "時" + days[2] + "分"));
						//check if important meeting
						
						List<AmsMeeting> meetings = ams.findAmsMeetingBy(startCal, endCal);
						if(!meetings.isEmpty()){
							session.setAttribute("importantMeeting", true);
						}
						session.removeAttribute("DocAskLeaveEditForm");
						session.removeAttribute("DocAskLeaveModifyForm");
						session.removeAttribute("DocAskLeaveInEdit");
					}else if(docType.equals(IConstants.AMSDocOverTime)){
						session.removeAttribute("DocOverTimeEditForm");
						session.removeAttribute("DocOverTimeModifyForm");
						session.removeAttribute("DocOverTimeInEdit");
					}else if(docType.equals(IConstants.AMSDocRepair)){
						session.removeAttribute("DocRepairEditForm");
						session.removeAttribute("DocRepairModifyForm");
						session.removeAttribute("DocRepairInEdit");
					}else if(docType.equals(IConstants.AMSDocRevoke)){
						session.removeAttribute("DocRevokeEditForm");
						session.removeAttribute("DocRevokeModifyForm");
						session.removeAttribute("DocRevokeInEdit");
					}
					saveMessages(request, messages);

					return query(mapping, (ActionForm)form, request, response);
		  		}else{
					saveErrors(request, messages);
		  		}
			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				
			}
		}
		return mapping.findForward("Main");
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionMessages messages = new ActionMessages();
		DynaActionForm dynForm = (DynaActionForm)form;
		
		String docType = dynForm.getString("docType");
		
		AmsManager ams = (AmsManager)getBean("amsManager");
		HttpSession session = request.getSession(false);
		Map initValue = new HashMap();
		
		initValue.put("opmode", "delete");
		initValue.put("docType", docType);
						
		List<AmsDocApply> selDocs = getDocSelectedList(request, docType);	//Records selected for delete
		for(AmsDocApply doc:selDocs){
			if(doc.getStatus() != null){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","該申請單已經人事室處理過了!無法刪除!"));
				saveErrors(request, messages);
				
				return query(mapping, (ActionForm)form, request, response);
			}
		}
		
		for(AmsDocApply doc:selDocs){
			ams.setDocExtraData(doc);
			if(docType.equals(IConstants.AMSDocRevoke)){
				ams.setDocExtraData(doc.getRevokedDoc());
			}
			
		}
		
		if(docType.equals(IConstants.AMSDocAskLeave)){
			Toolket.setAllCheckboxCookie(response, "DocAskLeaveDelete", selDocs.size());	//Set cookie of record count for delete
			session.setAttribute("DocAskLeaveDelete", selDocs);
			session.setAttribute("DocAskLeaveDelInit", initValue);
			setContentPage(session, "AMS/DocAskLeaveDelete.jsp");
		}else if(docType.equals(IConstants.AMSDocOverTime)){
			Toolket.setAllCheckboxCookie(response, "DocOverTimeDelete", selDocs.size());	//Set cookie of record count for delete
			session.setAttribute("DocOverTimeDelete", selDocs);
			session.setAttribute("DocOverTimeDelInit", initValue);
			setContentPage(session, "AMS/DocOverTimeDelete.jsp");
		}else if(docType.equals(IConstants.AMSDocRepair)){
			Toolket.setAllCheckboxCookie(response, "DocRepairDelete", selDocs.size());	//Set cookie of record count for delete
			session.setAttribute("DocRepairDelete", selDocs);
			session.setAttribute("DocRepairDelInit", initValue);
			setContentPage(session, "AMS/DocRepairDelete.jsp");
		}else if(docType.equals(IConstants.AMSDocRevoke)){
			Toolket.setAllCheckboxCookie(response, "DocRevokeDelete", selDocs.size());	//Set cookie of record count for delete
			session.setAttribute("DocRevokeDelete", selDocs);
			session.setAttribute("DocRevokeDelInit", initValue);
			setContentPage(session, "AMS/DocRevokeDelete.jsp");
		}
		return new ActionForward(mapping.findForward("Main").getPath(), true);
		
	}
	
	public ActionForward delConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		AmsManager ams = (AmsManager)getBean("amsManager");
		
		String docType = dynForm.getString("docType");
		
		List<AmsDocApply> selDocs = new ArrayList<AmsDocApply>();
		
		if(docType.equals(IConstants.AMSDocAskLeave)){
			selDocs = (List<AmsDocApply>)session.getAttribute("DocAskLeaveDelete");
		}else if(docType.equals(IConstants.AMSDocOverTime)){
			selDocs = (List<AmsDocApply>)session.getAttribute("DocOverTimeDelete");
		}else if(docType.equals(IConstants.AMSDocRepair)){
			selDocs = (List<AmsDocApply>)session.getAttribute("DocRepairDelete");
		}else if(docType.equals(IConstants.AMSDocRevoke)){
			selDocs = (List<AmsDocApply>)session.getAttribute("DocRevokeDelete");
		}
		
		List<AmsDocApply> undeletedRecs = ams.delDocApply(selDocs);
		if(docType.equals(IConstants.AMSDocAskLeave)){
			session.removeAttribute("DocAskLeaveDelete");
		}else if(docType.equals(IConstants.AMSDocOverTime)){
			session.removeAttribute("DocOverTimeDelete");
		}else if(docType.equals(IConstants.AMSDocRepair)){
			session.removeAttribute("DocRepairDelete");
		}else if(docType.equals(IConstants.AMSDocRevoke)){
			session.removeAttribute("DocRevokeDelete");
		}

		// no undeleteScores will happen even if delete failure
		if (undeletedRecs.size() == 0) {
			if(docType.equals(IConstants.AMSDocAskLeave)){
				session.removeAttribute("DocAskLeaveList");
				Map initMap = (Map)session.getAttribute("DocAskLeaveInit");
			}else if(docType.equals(IConstants.AMSDocOverTime)){
				session.removeAttribute("DocOverTimeList");
				Map initMap = (Map)session.getAttribute("DocOverTimeInit");
			}else if(docType.equals(IConstants.AMSDocRepair)){
				session.removeAttribute("DocRepairList");
				Map initMap = (Map)session.getAttribute("DocRepairInit");
			}else if(docType.equals(IConstants.AMSDocRevoke)){
				session.removeAttribute("DocRevokeList");
				Map initMap = (Map)session.getAttribute("DocRevokeInit");
			}
			// dynForm.set("mode", initMap.get("mode"));
			
			return query(mapping, (ActionForm)dynForm, request, response);
		} else {
			if(docType.equals(IConstants.AMSDocAskLeave)){
				session.setAttribute("UndeletedDocAskLeave", undeletedRecs);
				setContentPage(session, "AMS/DocApplyUndelete.jsp");
			}else if(docType.equals(IConstants.AMSDocOverTime)){
				session.setAttribute("UndeletedDocOverTime", undeletedRecs);
				setContentPage(session, "AMS/DocOverTimeUndelete.jsp");
			}else if(docType.equals(IConstants.AMSDocRepair)){
				session.setAttribute("UndeletedDocRepair", undeletedRecs);
				setContentPage(session, "AMS/DocRepairUndelete.jsp");
			}else if(docType.equals(IConstants.AMSDocRevoke)){
				session.setAttribute("UndeletedDocRevoke", undeletedRecs);
				setContentPage(session, "AMS/DocRevokeUndelete.jsp");
			}
			return mapping.findForward("Main");
		}
		
	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		
		String docType = dynForm.getString("docType");
		
		if(docType.equals(IConstants.AMSDocAskLeave)){
			session.removeAttribute("DocAskLeaveDelete");
			session.removeAttribute("DocAskLeaveModify");
			session.removeAttribute("DocAskLeaveEditForm");
			session.removeAttribute("DocAskLeaveModifyForm");
			session.removeAttribute("DocAskLeaveInEdit");
			Toolket.resetCheckboxCookie(response, "DocAskLeave");
			setContentPage(session, "AMS/DocAskLeave.jsp");
		}else if(docType.equals(IConstants.AMSDocOverTime)){
			session.removeAttribute("DocOverTimeDelete");
			session.removeAttribute("DocOverTimeModify");
			session.removeAttribute("DocOverTimeEditForm");
			session.removeAttribute("DocOverTimeModifyForm");
			session.removeAttribute("DocOverTimeInEdit");
			Toolket.resetCheckboxCookie(response, "DocOverTime");
			setContentPage(session, "AMS/DocOverTime.jsp");
		}else if(docType.equals(IConstants.AMSDocRepair)){
			session.removeAttribute("DocRepairDelete");
			session.removeAttribute("DocRepairModify");
			session.removeAttribute("DocRepairEditForm");
			session.removeAttribute("DocRepairModifyForm");
			session.removeAttribute("DocRepairInEdit");
			Toolket.resetCheckboxCookie(response, "DocRepair");
			setContentPage(session, "AMS/DocRepair.jsp");
		}else if(docType.equals(IConstants.AMSDocRevoke)){
			session.removeAttribute("DocRevokeDelete");
			session.removeAttribute("DocRevokeModify");
			session.removeAttribute("DocRevokeEditForm");
			session.removeAttribute("DocRevokeModifyForm");
			session.removeAttribute("DocRevokeInEdit");
			Toolket.resetCheckboxCookie(response, "DocRevoke");
			setContentPage(session, "AMS/DocRevoke.jsp");
		}
		
		return query(mapping, (ActionForm)dynForm, request, response);
	}

	public ActionForward back(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		
		return mapping.findForward("back");		
	}

	//Private Method Here ============================>>
	private List getDocSelectedList(HttpServletRequest request, String docType) {

		HttpSession session = request.getSession(false);
		String oids = "";
		AmsDAO dao =(AmsDAO)getBean("amsDAO");
		List<AmsDocApply> docs = new ArrayList<AmsDocApply>();
		
		if(docType.equals(IConstants.AMSDocAskLeave)){
			oids = Toolket.getSelectedIndexFromCookie(request, "DocAskLeave");
			docs = (List<AmsDocApply>)session.getAttribute("DocAskLeaveList");
		}else if(docType.equals(IConstants.AMSDocOverTime)){
			oids = Toolket.getSelectedIndexFromCookie(request, "DocOverTime");
			docs = (List<AmsDocApply>)session.getAttribute("DocOverTimeList");
		}else if(docType.equals(IConstants.AMSDocRepair)){
			oids = Toolket.getSelectedIndexFromCookie(request, "DocRepair");
			docs = (List<AmsDocApply>)session.getAttribute("DocRepairList");
		}else if(docType.equals(IConstants.AMSDocRevoke)){
			oids = Toolket.getSelectedIndexFromCookie(request, "DocRevoke");
			docs = (List<AmsDocApply>)session.getAttribute("DocRevokeList");
		}
		List<AmsDocApply> selDocs = new ArrayList<AmsDocApply>();
		for (AmsDocApply doc:docs ) {
			if (Toolket.isValueInCookie(doc.getOid().toString(), oids)) {
				dao.reload(doc);
				selDocs.add(doc);
			}
		}
		return selDocs;
	}

	private List getRevokeSelectedList(HttpServletRequest request, String cookiName) {

		HttpSession session = request.getSession(false);
		String oids = "";
		AmsDAO dao =(AmsDAO)getBean("amsDAO");
		List<AmsDocApply> docs = new ArrayList<AmsDocApply>();
		
		if(cookiName.equals("DocAskLeave")){
			oids = Toolket.getSelectedIndexFromCookie(request, "DocAskLeave");
			docs = (List<AmsDocApply>)session.getAttribute("DocAskLeaveList");
		}else if(cookiName.equals("DocAskLeaveC")){
			oids = Toolket.getSelectedIndexFromCookie(request, "DocAskLeaveC");
			docs = (List<AmsDocApply>)session.getAttribute("askLeaves");
		}
		
		List<AmsDocApply> selDocs = new ArrayList<AmsDocApply>();
		for (AmsDocApply doc:docs ) {
			if (Toolket.isValueInCookie(doc.getOid().toString(), oids)) {
				dao.reload(doc);
				selDocs.add(doc);
			}
		}
		return selDocs;
	}

	private ActionMessages validateInput(DynaActionForm form, HttpSession session) {
		ActionMessages msgs = new ActionMessages();
		ActionMessages errs = new ActionMessages();
		AmsManager ams = (AmsManager)getBean("amsManager");
		MemberManager mm = (MemberManager)getBean("memberManager");

		AmsDocApply modify = new AmsDocApply();
		
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		String opmode = form.getString("opmode").trim();
		String idno = user.getMember().getIdno();
		String docType = form.getString("docType").trim();
		String reason = form.getString("reason").trim();
		
		String startYear = form.getString("startYear").trim();
		String startMonth = form.getString("startMonth").trim();
		String startDay = form.getString("startDay").trim();
		String startHour = form.getString("startHour").trim();
		String startMinute = form.getString("startMinute").trim();

		String endYear = form.getString("endYear").trim();
		String endMonth = form.getString("endMonth").trim();
		String endDay = form.getString("endDay").trim();
		String endHour = form.getString("endHour").trim();
		String endMinute = form.getString("endMinute").trim();

		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.set(Calendar.SECOND, 59);
		
		boolean isValidStartCal = false;
		boolean isValidEndCal = false;
		
		if(docType.equals(IConstants.AMSDocRevoke)){
			if(reason.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請輸入銷假原因!"));
			}
			
			errs.add(msgs);
		}else if(docType.equals(IConstants.AMSDocOverTime)){
			
			String totalDay = form.getString("totalDay").trim();
			
			if(startYear.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","加班日期之起始年,不得為空白!"));
			}else if(!StringUtils.isNumeric(startYear)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之起始年,必須輸入數字!"));
			}
			if(startMonth.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","加班日期之起始月,不得為空白!"));
			}else if(!StringUtils.isNumeric(startMonth)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之起始月,必須輸入數字!"));
			}
			if(startDay.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","加班日期之起始日,不得為空白!"));
			}else if(!StringUtils.isNumeric(startDay)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之起始日,必須輸入數字!"));
			}
			if(startHour.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","加班日期之起始小時,不得為空白!"));
			}else if(!StringUtils.isNumeric(startHour)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之起始小時,必須輸入數字!"));
			}else if(Integer.parseInt(startHour)>23){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之起始小時,不得大於23!"));
			}
			if(startMinute.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","加班日期之起始分,不得為空白!"));
			}else if(!StringUtils.isNumeric(startMinute)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之起始分,必須輸入數字!"));
			}else if(Integer.parseInt(startMinute)>59){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之起始分,不得大於59!"));
			}
			
			if(msgs.isEmpty()){
				if(!Toolket.isValidFullDate(startYear + "-" + startMonth + "-" + startDay)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","加班之起始日期為無效之日期!"));
				}else{
					startCal.set(Integer.parseInt(startYear), Integer.parseInt(startMonth)-1, 
							Integer.parseInt(startDay), Integer.parseInt(startHour), Integer.parseInt(startMinute));
					isValidStartCal = true;
				}
			}
			
			errs.add(msgs);
			msgs = new ActionMessages();
			
			if(endYear.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","加班日期之結束年,不得為空白!"));
			}else if(!StringUtils.isNumeric(endYear)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之結束年,必須輸入數字!"));
			}
			if(endMonth.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","加班日期之結束月,不得為空白!"));
			}else if(!StringUtils.isNumeric(endMonth)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之結束月,必須輸入數字!"));
			}
			if(endDay.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","加班日期之結束日,不得為空白!"));
			}else if(!StringUtils.isNumeric(endDay)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之結束日,必須輸入數字!"));
			}
			if(endHour.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","加班日期之結束小時,不得為空白!"));
			}else if(!StringUtils.isNumeric(endHour)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之結束小時,必須輸入數字!"));
			}else if(Integer.parseInt(endHour)>23){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之結束小時,不得大於23!"));
			}
			if(endMinute.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","加班日期之結束分,不得為空白!"));
			}else if(!StringUtils.isNumeric(endMinute)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之結束分,必須輸入數字!"));
			}else if(Integer.parseInt(endMinute)>59){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","加班日期之結束分,不得大於59!"));
			}
			if(msgs.isEmpty()){
				if(!Toolket.isValidFullDate(endYear + "-" + endMonth + "-" + endDay)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","加班之結束日期為無效之日期!"));
				}else{
					endCal.set(Integer.parseInt(endYear), Integer.parseInt(endMonth)-1, 
							Integer.parseInt(endDay), 23, 59, 59);
					endCal.add(Calendar.DATE, 3);
					if(endCal.compareTo(today) < 0){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","加班日期已經超過申請期限!"));
					}else{
						isValidEndCal = true;
					}
					endCal.add(Calendar.DATE, -3);
				}
			}

			if(isValidStartCal && isValidEndCal){
				if(startCal.compareTo(endCal) > 0){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","加班之開始時間不得晚於結束時間!"));
				}
				List<AmsDocApply> cmpDocs = ams.getDocApplyByDateRange(idno, docType, startCal, endCal);
				if(!cmpDocs.isEmpty()){
					if(opmode.equals("add")){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","同一時間之加班單已存在!"));
					}else if(opmode.equals("modify")){
						modify = (AmsDocApply)session.getAttribute("DocOverTimeInEdit");
						for(AmsDocApply cmpDoc:cmpDocs){
							if(modify.getOid().intValue()!=cmpDoc.getOid().intValue()){
								msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
										"MessageN1","同一時間之加班單已存在!"));
								break;
							}
						}
					}
				}
				int[] diff = Toolket.timeTransfer(startCal, endCal);
				if(diff[0]>1){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","一張加班單只能加班一日,請調整加班起迄日期時間!"));
				}
			}
			
			if(totalDay.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請輸入加班日數!"));
			}else{
				if(!Toolket.isNumeric(totalDay)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","加班日數,必須輸入數字!"));
				}
				/*
				else{
					if(Integer.parseInt(totalDay) != 4 || Integer.parseInt(totalHour)!= 8){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","加班時數,必須為4或8小時!"));
					}
				}
				*/
			}
			if(reason.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請輸入加班原因!"));
			}
			errs.add(msgs);
						
		}else if(docType.equals(IConstants.AMSDocRepair)){
			int cnt = 0;
			
			if(!(startYear.equals("") || startMonth.equals("") || startDay.equals("") ||
					startHour.equals("") || startMinute.equals("") || endYear.equals("") ||
					endMonth.equals("") || endDay.equals("") ||	endHour.equals("") || endMinute.equals(""))){
				if(!StringUtils.isNumeric(startYear)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班年,必須輸入數字!"));
				}
				if(!StringUtils.isNumeric(startMonth)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班月,必須輸入數字!"));
				}
				if(!StringUtils.isNumeric(startDay)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班日,必須輸入數字!"));
				}
				if(!StringUtils.isNumeric(startHour)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班小時,必須輸入數字!"));
				}else if(Integer.parseInt(startHour)>23){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班小時,不得大於23!"));
				}
				if(!StringUtils.isNumeric(startMinute)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班分,必須輸入數字!"));
				}else if(Integer.parseInt(startMinute)>59){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班分,不得大於59!"));
				}
				if(!StringUtils.isNumeric(endYear)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班年,必須輸入數字!"));
				}
				if(!StringUtils.isNumeric(endMonth)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班月,必須輸入數字!"));
				}
				if(!StringUtils.isNumeric(endDay)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班日,必須輸入數字!"));
				}
				if(!StringUtils.isNumeric(endHour)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班小時,必須輸入數字!"));
				}else if(Integer.parseInt(endHour)>23){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班小時,不得大於23!"));
				}
				if(!StringUtils.isNumeric(endMinute)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班分,必須輸入數字!"));
				}else if(Integer.parseInt(endMinute)>59){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班分,不得大於59!"));
				}
				if(msgs.isEmpty()){
					if(!startYear.equals(endYear) && !startMonth.equals(endMonth) && !startDay.equals(endDay)){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","未刷卡補登之上下班日期須為同一日!"));
					}
					if(Toolket.isValidFullDate(startYear + "-" + startMonth + "-" + startDay)){
						startCal.set(Integer.parseInt(startYear), Integer.parseInt(startMonth)-1, 
								Integer.parseInt(startDay), Integer.parseInt(startHour), Integer.parseInt(startMinute));
						isValidStartCal = true;
					}
					if(Toolket.isValidFullDate(endYear + "-" + endMonth + "-" + endDay)){
						endCal.set(Integer.parseInt(endYear), Integer.parseInt(endMonth)-1, 
								Integer.parseInt(endDay), Integer.parseInt(endHour), Integer.parseInt(endMinute));
						isValidEndCal = true;
					}
					if(isValidStartCal && isValidEndCal){
						if(startCal.compareTo(endCal) > 0){
							msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
									"MessageN1","未刷卡補登之上班時間不得晚於下班時間!"));
						}
					}
				}
			}else if(!(startYear.equals("") || startMonth.equals("") || startDay.equals("") ||
					startHour.equals("") || startMinute.equals(""))){

				if(!StringUtils.isNumeric(startYear)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班年,必須輸入數字!"));
				}
				if(!StringUtils.isNumeric(startMonth)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班月,必須輸入數字!"));
				}
				if(!StringUtils.isNumeric(startDay)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班日,必須輸入數字!"));
				}
				if(!StringUtils.isNumeric(startHour)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班小時,必須輸入數字!"));
				}else if(Integer.parseInt(startHour)>23){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班小時,不得大於23!"));
				}
				if(!StringUtils.isNumeric(startMinute)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班分,必須輸入數字!"));
				}else if(Integer.parseInt(startMinute)>59){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之上班分,不得大於59!"));
				}
				endCal = null;
			}else if(!(endYear.equals("") || endMonth.equals("") || endDay.equals("") ||
					endHour.equals("") || endMinute.equals(""))){
				if(!StringUtils.isNumeric(endYear)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班年,必須輸入數字!"));
				}
				if(!StringUtils.isNumeric(endMonth)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班月,必須輸入數字!"));
				}
				if(!StringUtils.isNumeric(endDay)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班日,必須輸入數字!"));
				}
				if(!StringUtils.isNumeric(endHour)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班小時,必須輸入數字!"));
				}else if(Integer.parseInt(endHour)>23){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班小時,不得大於23!"));
				}
				if(!StringUtils.isNumeric(endMinute)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班分,必須輸入數字!"));
				}else if(Integer.parseInt(endMinute)>59){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登日期之下班分,不得大於59!"));
				}
				startCal = null;
			}else{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","未刷卡補登上下班日期時間輸入不完整!"));
			}
			
			if(!(startYear.equals("")|| startMonth.equals("") || startDay.equals("")) &&
					(StringUtils.isNumeric(startYear) && StringUtils.isNumeric(startMonth) && 
							StringUtils.isNumeric(startDay) )){
				if(!Toolket.isValidFullDate(startYear + "-" + startMonth + "-" + startDay)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登之上班日期為無效之日期!"));
				}else{
					startCal.set(Integer.parseInt(startYear), Integer.parseInt(startMonth)-1, 
							Integer.parseInt(startDay), 23, 59, 59);
					
					cnt = ams.getRepairDocCount(idno, startCal);
					if(cnt >= 3){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","未刷卡補登次數已達3次!"));
					}

					startCal.add(Calendar.DAY_OF_MONTH, 7);
					if(startCal.compareTo(today) < 0){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","未刷卡補登日期已經超過申請期限!"));
					}
					startCal.add(Calendar.DAY_OF_MONTH, -7);
				}
			}
			
			if(!(endYear.equals("")|| endMonth.equals("") || endDay.equals("")) &&
					(StringUtils.isNumeric(endYear) && StringUtils.isNumeric(endMonth) && 
							StringUtils.isNumeric(endDay) )){
				if(!Toolket.isValidFullDate(endYear + "-" + endMonth + "-" + endDay)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","未刷卡補登之下班日期為無效之日期!"));
				}else{
					endCal.set(Integer.parseInt(endYear), Integer.parseInt(endMonth)-1, 
							Integer.parseInt(endDay), 23, 59, 59);
					
					cnt = ams.getRepairDocCount(idno, endCal);
					if(cnt >= 3){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","未刷卡補登次數已達3次!"));
					}

					endCal.add(Calendar.DAY_OF_MONTH, 7);
					if(endCal.compareTo(today) < 0){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","未刷卡補登日期已經超過申請期限!"));
					}
					endCal.add(Calendar.DAY_OF_MONTH, -7);
				}
			}

			if(msgs.isEmpty()){
				List<AmsDocApply> cmpDocs = ams.getDocApplyByDateRange(idno, docType, startCal, endCal);
				if(!cmpDocs.isEmpty()){
					if(opmode.equals("add")){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","同一時間之補登單已存在!"));
					}else if(opmode.equals("modify")){
						modify = (AmsDocApply)session.getAttribute("DocRepairInEdit");
						for(AmsDocApply cmpDoc:cmpDocs){
							if(modify.getOid().intValue()!=cmpDoc.getOid().intValue()){
								msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
										"MessageN1","同一時間之補登單已存在!"));
								break;
							}
						}
					}
				}
			}
			
			if(reason.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請輸入未刷卡補登原因!"));
			}

			errs.add(msgs);
			
		}else if(docType.equals(IConstants.AMSDocAskLeave)){
			String askLeaveType = form.getString("askLeaveType").trim();
			AmsAskLeave askLeave = ams.getAskLeaveName(askLeaveType);
			String askLeaveName = askLeave.getName();
			String agent = form.getString("agent").trim();
			String agentName = form.getString("fscname").trim();
			String teachPeriod = form.getString("teachPeriod").trim();
			String totalDay = form.getString("totalDay").trim();
			String totalHour = form.getString("totalHour").trim();
			String totalMinute = form.getString("totalMinute").trim();
			float ivacations = 0f;
			
			if(reason.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請輸入請假原因!"));
			}
			if(askLeaveType.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請輸入請假類別!"));
			}
			if(agentName.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請輸入請假期間職務代理人!"));
			}else{
				Empl emp = mm.findEmplByName(agentName);
				if (emp == null) {
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","找不到這個職務代理人!"));
				}
			}
			if(Toolket.isTeaching(user.getMember().getIdno())){
				if(teachPeriod.equals("")){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","老師請輸入請假期間授課節數!"));
				}else if(!StringUtils.isNumeric(teachPeriod)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","授課節數必須輸入數字!"));
				}
			}
			
			errs.add(msgs);
			msgs = new ActionMessages();
			
			// TODO: check 請假時數之規則限制
			int itotalDay = 0, itotalHour = 0, itotalMinute = 0;
			if(!totalDay.equals("")){
				if(!StringUtils.isNumeric(totalDay)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","請假日數,必須輸入數字!"));
				}else{
					itotalDay = Integer.parseInt(totalDay);
				}
			}
			
			if(!totalHour.equals("")){
				if(!StringUtils.isNumeric(totalHour)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","請假時數,必須輸入數字!"));
				}else{
					itotalHour = Integer.parseInt(totalHour);
				}
			}
			
			if(!totalMinute.equals("")){
				if(!StringUtils.isNumeric(totalMinute)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","請假分鐘數,必須輸入數字!"));
				}else{
					itotalMinute = Integer.parseInt(totalMinute);
				}
			}
			
			if(itotalDay == 0 && itotalHour == 0 && itotalMinute == 0){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請輸入請假之總日時數!"));
			}

			if (askLeaveType.equals("07") || askLeaveType.equals("09") || askLeaveType.equals("12")) {
				// 休假至少為半天
				if (itotalDay == 0) {
					if (itotalHour != 4) {
						msgs.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("MessageN1", "請 ["
												+ askLeaveName
												+ "] 時數,至少必須為4小時(半天)!"));
					}
				}else{
					if (!(itotalHour == 4 || itotalHour == 0)) {
						msgs.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("MessageN1", "請 ["
												+ askLeaveName
												+ "] 時數,以半天為基本單位,小時部分必須為(4小時)或(0小時)!"));
					}
				}
				if(itotalMinute != 0){
					msgs.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("MessageN1", "請 ["
									+ askLeaveName
									+ "] 時數,以半天為基本單位,分鐘數部分請調整為零(0)!"));
				}
				
			} else {
				// 其他假別至少需請假30分鐘以上
				if (itotalDay == 0 && itotalHour == 0) {
					if (itotalMinute != 30) {
						msgs.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("MessageN1", "請 ["
										+ askLeaveName + "] 假時數至少為30分鐘!"));
					}
				}
				/*
				else if (itotalDay > 0 && itotalHour > 0
						&& itotalMinute > 0 && itotalMinute != 30) {
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "請調整 [" + askLeaveName
									+ "] 假時數[分鐘]為30分鐘!"));
				} else {
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "請調整 [" + askLeaveName
									+ "] 假時數[分鐘]為30分鐘!"));
				}
				*/
			}

			
			if(startYear.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請假日期之起始年,不得為空白!"));
			}else if(!StringUtils.isNumeric(startYear)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之起始年,必須輸入數字!"));
			}
			if(startMonth.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請假日期之起始月,不得為空白!"));
			}else if(!StringUtils.isNumeric(startMonth)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之起始月,必須輸入數字!"));
			}
			if(startDay.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請假日期之起始日,不得為空白!"));
			}else if(!StringUtils.isNumeric(startDay)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之起始日,必須輸入數字!"));
			}
			if(startHour.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請假日期之起始小時,不得為空白!"));
			}else if(!StringUtils.isNumeric(startHour)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之起始小時,必須輸入數字!"));
			}else if(Integer.parseInt(startHour)>23){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之起始小時,不得大於23!"));
			}
			if(startMinute.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請假日期之起始分,不得為空白!"));
			}else if(!StringUtils.isNumeric(startMinute)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之起始分,必須輸入數字!"));
			}else if(Integer.parseInt(startMinute)>59){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之起始分,不得大於59!"));
			}
			
			if(msgs.isEmpty()){
				if(!Toolket.isValidFullDate(startYear + "-" + startMonth + "-" + startDay)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","請假之起始日期為無效之日期!"));
				}else{
					startCal.set(Integer.parseInt(startYear), Integer.parseInt(startMonth)-1, 
							Integer.parseInt(startDay), Integer.parseInt(startHour), Integer.parseInt(startMinute));
					isValidStartCal = true;
				}
			}
			
			errs.add(msgs);
			msgs = new ActionMessages();
			
			if(endYear.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請假日期之結束年,不得為空白!"));
			}else if(!StringUtils.isNumeric(endYear)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之結束年,必須輸入數字!"));
			}
			if(endMonth.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請假日期之結束月,不得為空白!"));
			}else if(!StringUtils.isNumeric(endMonth)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之結束月,必須輸入數字!"));
			}
			if(endDay.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請假日期之結束日,不得為空白!"));
			}else if(!StringUtils.isNumeric(endDay)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之結束日,必須輸入數字!"));
			}
			if(endHour.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請假日期之結束小時,不得為空白!"));
			}else if(!StringUtils.isNumeric(endHour)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之結束小時,必須輸入數字!"));
			}else if(Integer.parseInt(endHour)>23){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之結束小時,不得大於23!"));
			}
			if(endMinute.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請假日期之結束分,不得為空白!"));
			}else if(!StringUtils.isNumeric(endMinute)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之結束分,必須輸入數字!"));
			}else if(Integer.parseInt(endMinute)>59){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請假日期之結束分,不得大於59!"));
			}
			
			if(msgs.isEmpty()){
				if(!Toolket.isValidFullDate(endYear + "-" + endMonth + "-" + endDay)){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","請假之結束日期為無效之日期!"));
				}else{
					endCal.set(Integer.parseInt(endYear), Integer.parseInt(endMonth)-1, 
							Integer.parseInt(endDay), Integer.parseInt(endHour), Integer.parseInt(endMinute));
					isValidEndCal = true;
				}
			}

			if(isValidStartCal && isValidEndCal){
				List<AmsDocApply> cmpDocs = ams.getDocApplyByDateRange(idno, docType, startCal, endCal);
				if(!cmpDocs.isEmpty()){
					if(opmode.equals("add")){
						for(AmsDocApply cmpDoc:cmpDocs){
							if(!cmpDoc.getStatus().equals(IConstants.AMSCodeDocRevokeOK)){
								msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
										"MessageN1","同一時間之假單已存在!"));
								break;
							}
						}
					}else if(opmode.equals("modify")){
						modify = (AmsDocApply)session.getAttribute("DocAskLeaveInEdit");
						for(AmsDocApply cmpDoc:cmpDocs){
							if(modify.getOid().intValue()!=cmpDoc.getOid().intValue() && 
									!cmpDoc.getStatus().equals(IConstants.AMSCodeDocRevokeOK)){
								msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
										"MessageN1","同一時間之假單已存在!"));
								break;
							}
						}
					}
				}

				if(startCal.compareTo(endCal) > 0){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","請假之開始時間不得晚於結束時間!"));
				}				
				if (askLeaveType.equals("07") || askLeaveType.equals("09") || askLeaveType.equals("12")) {
					ivacations = ams.checkHaveVacation(idno, askLeaveType, startCal, endCal);
					if(ivacations == 0f){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1", "您沒有 [" + askLeaveName
										+ "] 可請了!"));
					}else if(itotalDay > ivacations){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1", "您只剩下：" + ivacations + "天 [" + askLeaveName
										+ "] 可請了!"));
					}else if(itotalHour > (ivacations-itotalDay)*8 ){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1", "您只剩下：" + ivacations + "天 [" + askLeaveName
										+ "] 可請了!"));
					}else if(itotalMinute > (((ivacations-itotalDay)*8 - itotalHour))*30){
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1", "您只剩下：" + ivacations + "天 [" + askLeaveName
										+ "] 可請了!"));
					}
				}
				endCal.set(Integer.parseInt(endYear), Integer.parseInt(endMonth)-1, 
						Integer.parseInt(endDay), 23, 59, 59);
				endCal.add(Calendar.DAY_OF_MONTH, 7);
				if(endCal.compareTo(today) < 0){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","請假日期已經超過申請期限!"));
				}
				endCal.add(Calendar.DAY_OF_MONTH, -7);
			}
			//errs.add(msgs);
			
			errs.add(msgs);
		}
		
		return errs;
	}


}
