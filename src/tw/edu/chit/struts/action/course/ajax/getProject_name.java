package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class getProject_name extends HttpServlet{
	
	//CourseManager manager = (CourseManager)getBean("courseManager");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		String project_name=request.getParameter("project_name");
		
		
		List project = manager.ezGetBy("SELECT projname FROM Rcproj WHERE projname LIKE '"+project_name+"%'");
		
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		out.println("<pront>");		
		if(project.size()<1){
			out.println("<project_name>查無資料</project_name>");
		}else{
			for(int i=0; i<project.size(); i++){
				out.println("<id>nid"+i+"</id>");
				out.println("<project_name>"+((Map)project.get(i)).get("projname")+"</project_name>");
				
			}
		}
		
		out.println("</pront>");
		out.close();
		
	}

}
