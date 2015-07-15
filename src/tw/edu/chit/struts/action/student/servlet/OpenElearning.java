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
public class OpenElearning extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");// 澄入資訊

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>" +
		// "<meta http-equiv='refresh'
		// content='0;url=http://192.192.230.45/tl5sso/gototl5.asp?uid="+c.getMember().getAccount()+"'
		// />" +
				"</head>");
		out.println("<body>");

		out
				.println("<iframe src='http://192.192.230.45/tl5sso/gototl5.asp?uid="
						+ c.getMember().getAccount()
						+ "' height='100%' border='0' frameborder='0' width='100%'>");

		out.println("</body>");
		out.println("</html>");
		out.close();
	}

}
