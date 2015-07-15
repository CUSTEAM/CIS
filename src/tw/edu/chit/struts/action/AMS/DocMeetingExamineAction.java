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
import tw.edu.chit.model.AmsMeetingAskLeave;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AmsManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class DocMeetingExamineAction  extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK",		"examine");
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
						
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		AmsManager ams = (AmsManager)getBean("amsManager");
		
		if(!sn.equals("")){
			return examine(mapping, form, request, response);
		}
		
		setContentPage(request, "AMS/DocMeetingExamine.jsp");
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
		
		if(!sn.trim().equals("")){// && sn.length()>=14){
			//sn = sn.substring(0, 14);
			AmsMeetingAskLeave doc = ams.getDocMeetingApplyBySn(sn);
			if(doc == null){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","找不到這張申請單，請重新輸入申請單條碼編號!"));
				saveErrors(request, messages);
			}else if(doc.getStatus().equals("1")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","單號:" + sn + ", 這張申請單已經處理過了!"));
				saveErrors(request, messages);
			}else{
				messages = ams.DocMeetingExamine(doc);
				
				if(!messages.isEmpty()){
					saveErrors(request, messages);
				}else{
					ams.setDocMeetingAskLeaveExtraData(doc);
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","審查完成!"));
					saveMessages(request, messages);
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
		List<AmsMeetingAskLeave> docList = ams.getDocMeetingAskLeaveByStatus("1", today, tonight);
		for(AmsMeetingAskLeave doc:docList){
			ams.setDocMeetingAskLeaveExtraData(doc);
		}
		session.setAttribute("meetingExamined", docList);
		setContentPage(request, "AMS/DocMeetingExamine.jsp");
		return mapping.findForward("Main");				
	}
	
}
