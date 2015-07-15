package tw.edu.chit.struts.action.AMS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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

import tw.edu.chit.model.AmsDocApply;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AmsManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class DocExamineAction  extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK",		"examine");
		map.put("ForceExam",	"force");
		map.put("Cancel", 	"cancel");
		return map;		
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
	throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		String sn = dynForm.getString("sn");
		String force = dynForm.getString("force");
						
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		AmsManager ams = (AmsManager)getBean("amsManager");
		
		if(!sn.equals("")){
			if(force.equals("")){
				return examine(mapping, form, request, response);
			}else{
				return force(mapping, form, request, response);
			}
		}
		
		Map init = new HashMap();
		init.put("force", force);
		session.setAttribute("DocExamInit", init);
		setContentPage(request, "AMS/DocExamine.jsp");
		return mapping.findForward("Main");				
	}

	public ActionForward examine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		Calendar today = Calendar.getInstance();
		Calendar tonight = Calendar.getInstance();
		String sn = aForm.getString("sn");
		String docType = "";
		Map initValue = new HashMap();
			initValue.put("docType", IConstants.AMSDocAskLeave);
		
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		AmsManager ams = (AmsManager)getBean("amsManager");
		ActionMessages messages = new ActionMessages();
		
		if(!sn.trim().equals("") && sn.length()>=13){
			sn = sn.substring(0, 13);
			AmsDocApply doc = ams.getDocApplyBySn(sn);
			if(doc == null){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","找不到這張申請單，請重新輸入申請單條碼編號!"));
				saveErrors(request, messages);
			}else if(doc.getStatus()!=null){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","單號:" + sn + ", 這張申請單已經處理過了!"));
				saveErrors(request, messages);
			}else{
				messages = ams.DocExamine(doc, false);
				
				if(!messages.isEmpty()){
					saveErrors(request, messages);
				}else{
					ams.setDocExtraData(doc);
					if(doc.getDocType().equals(IConstants.AMSDocRevoke)){
						ams.setDocExtraData(doc.getRevokedDoc());
					}
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","審查完成!"));
					saveMessages(request, messages);
				}
				docType = doc.getDocType();
				ams.setDocExtraData(doc);
				
				if(docType.equals(IConstants.AMSDocRevoke)){
					ams.setDocExtraData(doc.getRevokedDoc());
				}
				
			}
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","單號:" + sn + " 有誤! 請重新輸入申請單條碼編號!"));
			saveErrors(request, messages);
		}
		
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		tonight.set(Calendar.HOUR_OF_DAY, 23);
		tonight.set(Calendar.MINUTE, 59);
		tonight.set(Calendar.SECOND, 59);
		List<AmsDocApply> docList = ams.getDocApplyByStatus(IConstants.AMSCodeDocProcessOK, today, tonight);
		List<AmsDocApply> docList2 = ams.getDocApplyByStatus(IConstants.AMSCodeDocProcessForceOK, today, tonight);
		docList.addAll(docList2);
		for(AmsDocApply doc:docList){
			ams.setDocExtraData(doc);
		}
		List<AmsDocApply> ndocList = ams.getDocApplyByStatus(IConstants.AMSCodeDocProcessReject, today, tonight);
		for(AmsDocApply doc:ndocList){
			ams.setDocExtraData(doc);
		}
		session.setAttribute("examined", docList);
		session.setAttribute("rejected", ndocList);
		setContentPage(request, "AMS/DocExamine.jsp");
		return mapping.findForward("Main");				
	}
	
	public ActionForward force(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		Calendar today = Calendar.getInstance();
		Calendar tonight = Calendar.getInstance();
		String sn = aForm.getString("sn");
		String docType = "";
		Map initValue = new HashMap();
			initValue.put("docType", IConstants.AMSDocAskLeave);
		
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		AmsManager ams = (AmsManager)getBean("amsManager");
		ActionMessages messages = new ActionMessages();
		
		if(!sn.trim().equals("") && sn.length()>=13){
			sn = sn.substring(0, 13);
			AmsDocApply doc = ams.getDocApplyBySn(sn);
			if(doc == null){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","找不到這張申請單，請重新輸入申請單條碼編號!"));
				saveErrors(request, messages);
			}else{
				messages = ams.DocExamine(doc, true);
				
				if(!messages.isEmpty()){
					saveErrors(request, messages);
				}else{
					ams.setDocExtraData(doc);
					if(doc.getDocType().equals(IConstants.AMSDocRevoke)){
						ams.setDocExtraData(doc.getRevokedDoc());
					}
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","審查完成!"));
					saveMessages(request, messages);
				}
				docType = doc.getDocType();
				ams.setDocExtraData(doc);
				
				if(docType.equals(IConstants.AMSDocRevoke)){
					ams.setDocExtraData(doc.getRevokedDoc());
				}
				
			}
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","單號:" + sn + " 有誤! 請重新輸入申請單條碼編號!"));
			saveErrors(request, messages);
		}
		
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		tonight.set(Calendar.HOUR_OF_DAY, 23);
		tonight.set(Calendar.MINUTE, 59);
		tonight.set(Calendar.SECOND, 59);
		List<AmsDocApply> docList = ams.getDocApplyByStatus(IConstants.AMSCodeDocProcessOK, today, tonight);
		List<AmsDocApply> docList2 = ams.getDocApplyByStatus(IConstants.AMSCodeDocProcessForceOK, today, tonight);
		docList2.addAll(docList);
		for(AmsDocApply doc:docList2){
			ams.setDocExtraData(doc);
		}
		List<AmsDocApply> ndocList = ams.getDocApplyByStatus(IConstants.AMSCodeDocProcessReject, today, tonight);
		for(AmsDocApply doc:ndocList){
			ams.setDocExtraData(doc);
		}
		session.setAttribute("examined", docList);
		session.setAttribute("rejected", ndocList);
		setContentPage(request, "AMS/DocExamine.jsp");
		return mapping.findForward("Main");				
	}

}
