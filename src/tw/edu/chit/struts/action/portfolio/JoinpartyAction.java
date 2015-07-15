package tw.edu.chit.struts.action.portfolio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class JoinpartyAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();//取得使用者帳號
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		request.setAttribute("myUrl", manager.myPortfolioUrl(Uid));
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd");
		String date=sf.format(new Date());
		//決定只開放一個
		//request.setAttribute("contest", manager.ezGetBy("SELECT * FROM Eps_Act_parameter WHERE " +
				//"sign_start<='"+date+"' AND sign_end>='"+date+"'"));
		request.setAttribute("contest", manager.ezGetBy("SELECT * FROM Eps_Act_parameter WHERE " +
				"sign_start<='"+date+"' AND sign_end>='"+date+"' ORDER BY Oid DESC LIMIT 1"));
		
		request.setAttribute("myParty", manager.ezGetBy("SELECT u.*, p.name FROM Eps_Act_user u, Eps_Act_parameter p WHERE u.act_oid=p.Oid AND u.Uid='"+c.getMember().getAccount()+"'"));
		
		
		setContentPage(request.getSession(false), "portfolio/Joinparty.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward joinParty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		DynaActionForm jForm = (DynaActionForm) form;
		
		String addOid=jForm.getString("addOid");
		String delOid[]=jForm.getStrings("delOid");
		
		
		try{
			
			manager.executeSql("INSERT INTO Eps_Act_user(act_oid, Uid)VALUES('"+addOid+"', '"+c.getMember().getAccount()+"');");
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "報名完成"));
			saveMessages(request, msg);	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		ActionMessages error = new ActionMessages();		
		for(int i=0; i<delOid.length; i++){
			if(!delOid[i].equals("")){
				manager.executeSql("DELETE FROM Eps_Act_user WHERE Oid='"+delOid[i]+"'");				
				manager.executeSql("DELETE FROM Eps_Act_vote WHERE EpsActUserOid='"+delOid[i]+"'");				
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已取消第"+(i+1)+"個報名"));
			}		
		}
		
		if(!error.isEmpty()){
			saveErrors(request, error);
		}
		
		return unspecified(mapping, form, request, response);
	}	
	
	public ActionForward leaveParty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		//UserCredential c = (UserCredential) session.getAttribute("Credential");
		DynaActionForm jForm = (DynaActionForm) form;		
		String delOid[]=jForm.getStrings("delOid");		
		ActionMessages error = new ActionMessages();		
		for(int i=0; i<delOid.length; i++){
			if(!delOid[i].equals("")){
				manager.executeSql("DELETE FROM Eps_Act_user WHERE Oid='"+delOid[i]+"'");
				
				manager.executeSql("DELETE FROM Eps_Act_vote WHERE EpsActUid='"+delOid[i]+"'");
				
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已取消第"+(i+1)+"個報名"));
			}		
		}		
		if(!error.isEmpty()){
			saveErrors(request, error);
		}		
		return unspecified(mapping, form, request, response);
	}
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("JoinParty", "joinParty");
		map.put("LeaveParty", "leaveParty");
		return map;
	}

}
