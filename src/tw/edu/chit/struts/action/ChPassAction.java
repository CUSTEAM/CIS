package tw.edu.chit.struts.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;

public class ChPassAction extends BaseAction {
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
		HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		HttpSession session=request.getSession(false);		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm cForm = (DynaActionForm)form;
		UserCredential c = (UserCredential) session.getAttribute("Credential");	
		
		String password = cForm.getString("password").trim();
		String oldPassword=manager.ezGetString("SELECT password FROM wwpass WHERE username='"+c.getMember().getAccount()+"'");
		
		
		if(password.length()<6||password.equals(oldPassword)){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","依規定不可與舊密碼相同")); 
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca=Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.MONTH, 6);//給6個月好了
		
		
		
		manager.executeSql("UPDATE wwpass SET password='"+password+"' WHERE username='"+c.getMember().getAccount()+"'");
		session.setAttribute("chpass", manager.checkPassUpdate(c.getMember().getAccount()));
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","更新完成！新密碼有效期限至"+manager.convertDate(sf.format(ca.getTime()))));
		saveMessages(request, msg);
		
		
		//setContentPage(request.getSession(false), "BulletinBoard.jsp");						
		return mapping.findForward("Main");
	}

}
