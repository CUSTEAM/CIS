package tw.edu.chit.struts.action.portfolio.servlet;

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

public class getPrivateModule extends HttpServlet{
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
			CourseManager manager=(CourseManager) ctx.getBean("courseManager");
			
			//System.out.println("work!");
			//String nameOrNumber=request.getParameter("query");
			//String EnameOrNumber=new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8");
			String Oid=request.getParameter("Oid");
				
			List list=manager.ezGetBy("SELECT m.name, mp.content FROM module_private mp, module m WHERE m.Oid=mp.Mid AND mp.Oid='"+Oid+"'");
			
			//List list=manager.ezGetBy(sql.toString());		
			
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter out=response.getWriter();
			
			out.println("<pront>");
			if(list.size()<1){
				out.println("<name>內容有錯誤</name>");
				out.println("<content>沒有設定內容</content>");
				}else{
			
				for(int i=0; i<list.size(); i++){
					if(((Map)list.get(i)).get("content")==null||((Map)list.get(i)).get("content").equals("")){
						out.println("<name>內容有錯誤</name>");
						out.println("<content>沒有設定內容</content>");
					}else{
						out.println("<name>"+((Map)list.get(i)).get("name")+"</name>");
						out.println("<content>"+((Map)list.get(i)).get("content")+"</content>");
					}
				}			
			}	
			out.println("</pront>");
			out.close();
			
			
			
		}
}
