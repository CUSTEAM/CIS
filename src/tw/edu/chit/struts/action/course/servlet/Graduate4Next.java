package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

/**
 * 畢業資格審查尋找下五筆
 * @author JOHN
 *
 */
public class Graduate4Next extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		
		int plus=Integer.parseInt(request.getParameter("index"));
		
		session.setAttribute("begin", plus);
		session.setAttribute("end", plus+4);
		
		response.sendRedirect("/CIS/Registration/CheckGraduate.do?index="+plus);
		
	}
	
	
}
