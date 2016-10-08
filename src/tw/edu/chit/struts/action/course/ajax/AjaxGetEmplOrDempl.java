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

/**
 * 處理empl、dempl的混合自動完成
 * @author JOHN
 *
 */
public class AjaxGetEmplOrDempl extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		String type=request.getParameter("type");
		//String table=request.getParameter("table");
		
		String nameOrNumber=request.getParameter("query");
				
		StringBuffer gSql=new StringBuffer();
		StringBuffer sSql=new StringBuffer();
		
		
		
		gSql.append("SELECT idno as no, cname as name FROM dempl WHERE ");
		if(type.trim().equals("name")){
			gSql.append("cname LIKE '"+nameOrNumber+"%' GROUP BY idno");
		}else{
			gSql.append("idno LIKE '"+nameOrNumber+"%' GROUP BY idno");
		}
		
		sSql.append("SELECT idno as no, cname as name FROM empl WHERE ");
		if(type.trim().equals("name")){
			sSql.append("cname LIKE '"+nameOrNumber+"%' GROUP BY idno");
		}else{
			sSql.append("idno LIKE '"+nameOrNumber+"%' GROUP BY idno");
		}
		
		List list=manager.ezGetBy(gSql.toString());
		list.addAll(manager.ezGetBy(sSql.toString()));
		
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		out.println("<pront>");
		if(list.size()<1){
			out.println("<name>新同仁</name>");
			out.println("<no>"+nameOrNumber+"</no>");
		}else{
		
			for(int i=0; i<list.size(); i++){
				out.println("<name>"+((Map)list.get(i)).get("name")+"</name>");
				out.println("<no>"+((Map)list.get(i)).get("no")+"</no>");
			}			
		}	
		out.println("</pront>");
		out.close();
		
	}

}
