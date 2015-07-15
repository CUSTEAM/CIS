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

public class getProject_leader extends HttpServlet{
	
	//CourseManager manager = (CourseManager)getBean("courseManager");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		String project_leader=request.getParameter("project_leader");
		project_leader=new String(project_leader.getBytes("iso-8859-1"), "utf-8");
		List empl = manager.ezGetBy("SELECT e.cname, p.projname FROM empl e, Rcproj p WHERE e.idno=p.idno AND e.cname LIKE'"+project_leader+"%'");
		
		response.setContentType("text/xml; charset=utf-8");
		PrintWriter out=response.getWriter();
		
		out.println("<pront>");		
		if(empl.size()<1){
			out.println("<project_leader>查無資料</project_leader>");
		}else{
			for(int i=0; i<empl.size(); i++){
				out.println("<id>lid"+i+"</id>");
				out.println("<project_leader>"+((Map)empl.get(i)).get("cname")+"</project_leader>");
				out.println("<project_name>"+((Map)empl.get(i)).get("projname")+"</project_name>");
			}
		}
		
		out.println("</pront>");
		out.close();
		
	}

}
