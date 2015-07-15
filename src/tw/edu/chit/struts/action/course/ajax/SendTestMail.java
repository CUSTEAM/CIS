package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;
/**
 * 郵件釣魚
 * @author JOHN
 *
 */
public class SendTestMail extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");	
		
		InternetAddress addr;
		InternetAddress address[] = new InternetAddress[1];
		addr=new InternetAddress("cc@www.cust.edu.tw", "cust", "BIG5");
		address[0]=addr;
		
		manager.sendMail("", "", "www.cust.edu.tw", "test@abc.com.tw", "王小明", new Date(), "測試", "<img src='http://192.192.237.19/CIS/CountImage?userid=77'>", address, null);
		
		
		
		
		//response.setContentType("image/jpeg");		
		//response.sendRedirect("/CIS/pages/images/transparent.gif");
	}
}
