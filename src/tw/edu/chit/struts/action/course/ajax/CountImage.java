package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;
/**
 * 取得學生照片
 * @author JOHN
 *
 */
public class CountImage extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());		
			CourseManager manager=(CourseManager) ctx.getBean("courseManager");		
			
			Enumeration e=request.getHeaderNames();		
			
			
			String userid = manager.ezGetString("SELECT idno FROM empl WHERE Oid="+request.getParameter("userid"));
			String ip=request.getHeader("host");
			String browser=request.getHeader("user-agent");		
			String url=request.getRequestURL().toString();
			//String os=request.getHeader("user-agent");
			String lan=request.getHeader("accept-language");
			System.out.println("INSERT INTO CounterStrike(userid, url, ip, lan, browser)VALUES('"+userid+"', '"+url+"', '"+ip+"', '"+lan+"', '"+browser+"')");
			manager.executeSql("INSERT INTO CounterStrike(userid, url, ip, lan, browser)VALUES('"+userid+"', '"+url+"', '"+ip+"', '"+lan+"', '"+browser+"')");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		
		response.setContentType("image/jpeg");		
		response.sendRedirect("/CIS/pages/images/transparent.gif");
	}
}
