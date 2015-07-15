package tw.edu.chit.struts.action.course.ajax.msg;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.WwPass;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;


public class getMsg extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("work!");
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		
		try{
			UserCredential c = (UserCredential) session.getAttribute("Credential");
			SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
			manager.executeSql("UPDATE wwpass SET online='"+sf.format(new Date())+"' WHERE username='"+c.getMember().getAccount()+"'");
			out.println("<pront>");
			out.println("<ctMsg>"+manager.ezGetString("SELECT COUNT(*)FROM OnlineMsg WHERE receiver='"+c.getMember().getAccount()+"'")+"</ctMsg>");
			out.println("</pront>");
			out.close();
		}catch(Exception e){
			//return null;
		}
		
	}

}
