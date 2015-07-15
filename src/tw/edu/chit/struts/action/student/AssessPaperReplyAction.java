package tw.edu.chit.struts.action.student;

import java.util.HashMap;
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

import tw.edu.chit.model.AssessPaper;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

public class AssessPaperReplyAction  extends BaseLookupDispatchAction{
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Save",			"save");
		map.put("Cancel", 		"cancel");
		return map;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;

		String[] scoreName = {"非常不滿意", "不滿意", "普通", "滿意", "非常滿意" };
		session.setAttribute("scoreName", scoreName);
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		setContentPage(session, "personnel/AssessPaperReply.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form , HttpServletRequest request,
			HttpServletResponse response){
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		String idno = user.getMember().getIdno();
		
		String serviceNo = aForm.getString("serviceNo").trim();
		String score = aForm.getString("score").trim();
		String suggestion = aForm.getString("suggestion").trim();
		String srvTime = aForm.getString("srvTime").trim();
		String srvEvent = aForm.getString("srvEvent").trim();
		String description = aForm.getString("description").trim();
		String sdate = aForm.getString("sdate").trim();
		String[] scoreName = {"非常不滿意", "不滿意", "普通", "滿意", "非常滿意" };
		
		ActionMessages messages = new ActionMessages();

		if(srvEvent.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請輸入洽辦事項！"));
		}

		if((score.equals("1") || score.equals("2") || score.equals("5")) && description.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","滿意度：" + scoreName[Integer.parseInt(score)-1] + "，請輸入具體事實，謝謝！"));
		}
		
		if((score.equals("1") || score.equals("2")) && suggestion.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","滿意度：" + scoreName[Integer.parseInt(score)-1] + "，請輸入建議事項，謝謝！"));
		}
		
		if(sdate.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請選擇服務日期！"));
		}
		
		AssessPaper paper = mm.findAssessPaperByNo(serviceNo);
		
		if(paper == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","找不到服務編號為：" + serviceNo + "的表單，請注意服務編號中沒有英文字母O"));
		}else if(paper.getScore() != null || paper.getServiceDate()!= null){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","服務編號為：" + serviceNo + "的表單，已經填寫過了！"));
		}else if(paper.getIdno().equalsIgnoreCase(idno)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請不要為自己填寫服務滿意度調查表！"));
		}
		
		if(!messages.isEmpty()){
			session.setAttribute("AssessPaperEdit", aForm.getMap());
			saveMessages(request, messages);
			setContentPage(request, "personnel/AssessPaperReply.jsp");
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		
		ActionMessages msgs = mm.saveAssessPaperReply(user, paper, aForm);
		if(!msgs.isEmpty()){
			session.setAttribute("AssessPaperEdit", aForm.getMap());
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","很抱歉！系統資料儲存失敗，原因：" + msgs.toString()));
			saveErrors(request, messages);
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","謝謝您撥冗填寫滿意度調查表！"));
			saveMessages(request, messages);
			session.removeAttribute("AssessPaperEdit");
		}
		//setContentPage(request, "personnel/AssessPaperReply.jsp");
		return mapping.findForward("back");
		
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;

		UserCredential user = (UserCredential)session.getAttribute("Credential");
		setContentPage(session, "personnel/AssessPaperReply.jsp");
		return mapping.findForward("Main");
	}
	

}
