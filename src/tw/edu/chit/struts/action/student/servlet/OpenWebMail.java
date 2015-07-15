package tw.edu.chit.struts.action.student.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;

/**
 * 修改密碼並登入電子郵件
 * 
 * @author JOHN
 * 
 */
public class OpenWebMail extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");// 澄入資訊

		String account = "CIS";// 登入帳號
		String password = "chit!@#";// 登入密碼
		String newAccount = "CISADMIN";// 切換使用者的帳號
		String newPassword = "chit!@#";// 切換使用者的密碼
		String ChAccount = "s" + c.getMember().getAccount().toLowerCase();// 要變更的帳號
		String ChPassword = c.getMember().getPassword();// 要變更的密碼
		String host = "192.168.1.243";
		String path = "http://ccs.chit.edu.tw/cgi-bin/openwebmail/openwebmail.pl";
		if (manager
				.ezGetString(
						"SELECT c.CampusNo FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.student_no='"
								+ c.getMember().getAccount() + "'").equals("2")) {
			host = host = manager
					.ezGetString("SELECT Value FROM Parameter WHERE name='mailServer' AND Category='hc' AND type='S'");
			path = "http://ccs.hc.chit.edu.tw/cgi-bin/openwebmail/openwebmail.pl";
		}

		// System.out.println(host);
		// System.out.println(path);

		System.out.println(manager.SSHChangeMailPassword4One(host, account,
				password, newAccount, newPassword, ChAccount, ChPassword));

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head></head>");
		out.println("<body>");

		out.println("<form method='post' action='" + path
				+ "' enctype='multipart/form-data' name='login'>");
		out.println("");
		out.println("<BR>");
		out
				.println("<table align=center border=0 cellspacing=2 cellpadding=0 style='display:none;'>");
		out.println("<tr>");
		out
				.println("<td align='right' nowrap>帳號: </td><td><input type='text' name='loginname'  value='"
						+ ChAccount + "' /></td>");
		out.println("</tr><tr>");
		out
				.println("<td align='right' nowrap>密碼: </td><td><input type='password' name='password'  value='"
						+ ChPassword + "' /></td>");
		out.println("");
		out
				.println("<INPUT TYPE='hidden' NAME='logindomain' VALUE='ccs.chit.edu.tw'>");
		out.println("</tr><tr>");
		out.println("<td align=center colspan=2>");
		out.println("");
		out.println("<table><tr>");
		out
				.println("<td align=center><input type='submit' name='loginbutton' value='登入' /></td>");
		out.println("<td align=center>");
		out.println("<table><tr>");
		out
				.println("<td><label><input type='checkbox' name='httpcompress' value='1' checked='checked' onclick='httpcompresshelp()' /></label></td><td><font size=1>網頁資料<br>壓縮傳送</font></td>");
		out
				.println("<td><label><input type='checkbox' name='autologin' value='1' onclick='autologinhelp()' /></label></td><td><font size=1>自動<br>登入</font></td>");
		out.println("</tr></table>");
		out.println("</td>");
		out.println("</tr></table>");
		out.println("");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<BR>");
		out.println("</td>");
		out
				.println("<div><input type='hidden' name='.cgifields' value='httpcompress'  /><input type='hidden' name='.cgifields' value='autologin'  /></div>");
		out.println("</form>");

		out.println("<script>document.forms[0].submit();</script>");

		out.println("</body>");
		out.println("</html>");

		out.close();

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
	}

}
