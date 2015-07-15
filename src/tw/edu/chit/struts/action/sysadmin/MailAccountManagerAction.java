package tw.edu.chit.struts.action.sysadmin;

import java.security.MessageDigest;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.sysadmin.sshtools.SSHClient;

public class MailAccountManagerAction extends BaseLookupDispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		request.setAttribute("servers", manager.ezGetBy("SELECT * FROM Parameter p WHERE p.Name='mailServer'"));
		
		String account="CIS";// 登入帳號
		String password="chit!@#";// 登入密碼
		String newAccount="CISADMIN";// 切換使用者的帳號
		String newPassword="chit!@#";//切換使用者的密碼		
		String ChAccount="test";// 要變更的帳號		
		String ChPassword="test";//要變更的密碼
		
		
		System.out.println(manager.SSHChangeMailPassword4One("192.168.1.243", account, password, newAccount, newPassword, ChAccount, ChPassword));
				
		
		
		
		setContentPage(request.getSession(false), "sysadmin/MailAccountManager.jsp");
		return mapping.findForward("Main");

	}

	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		/*
		String newPass="chit168spring";			
		try {
	         java.security.MessageDigest md = null; 
	         md = MessageDigest.getInstance("MD5");
	         String result = new String(md.digest(newPass.getBytes()));
	         newPass=(new sun.misc.BASE64Encoder()).encode( result.getBytes()); 
	      } catch (Exception e) {
	        
	      }
		ssh.switchUser("CISADMIN", "chit!@#", "Password: ");//有時會改
		System.out.println("準備執行: mkdir test");
		ssh.execute("mkdir test");
		System.out.println("mkdir test 執行完成");
		ssh.execute("whoami");
		ssh.execute("cd /home/CISADMIN");
		ssh.execute("pwd");
		ssh.execute("ls");
		
		System.out.println("準備執行: ./addtest test test");
		ssh.execute("./addtest test test");
		System.out.println("./addtest test test 執行完成");
		
		System.out.println("準備執行: userdel -r test");
		ssh.execute("userdel -r test");
		System.out.println("userdel -r test 執行完成");
		*/
		return null;
	}

}
