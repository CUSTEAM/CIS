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
 * 處理gstmd、stmd的混合自動完成
 * @author JOHN
 *
 */
public class AjaxGetStmdOrGstmd extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml; charset=utf8");
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		PrintWriter out=response.getWriter();
		
		String type=request.getParameter("type");
		String nameOrNumber=request.getParameter("query");
		
		//System.out.println(nameOrNumber);
				
		StringBuffer gSql=new StringBuffer();
		StringBuffer sSql=new StringBuffer();
		
		//以身分證學號混合查詢的情況
		if(request.getParameter("table").equals("gstmdId")){			
			List<Map>list=manager.ezGetBy("SELECT student_no as no, student_name as name FROM stmd WHERE student_no LIKE'"+nameOrNumber+"%' OR idno LIKE '"+nameOrNumber+"%' LIMIT 10");
			list.addAll(manager.ezGetBy("SELECT student_no as no, student_name as name FROM Gstmd WHERE student_no LIKE'"+nameOrNumber+"%' OR idno LIKE '"+nameOrNumber+"%' LIMIT 10"));
			out.println("<pront>");
			if(list.size()<1){
				out.println("<name>新同學</name>");
				out.println("<no>"+nameOrNumber+"</no>");
			}else{
			
				for(int i=0; i<list.size(); i++){
					out.println("<name>"+list.get(i).get("name")+"</name>");
					out.println("<no>"+list.get(i).get("no")+"</no>");
				}			
			}	
			out.println("</pront>");
			out.close();			
		}
		
		
		
		
		
		
		//一般查詢學號
		gSql.append("SELECT student_no as no, student_name as name FROM Gstmd WHERE ");
		if(type.trim().equals("name")){
			gSql.append("student_name LIKE '"+nameOrNumber+"%'");
		}else{
			gSql.append("student_no LIKE '"+nameOrNumber+"%'");
		}
		
		sSql.append("SELECT student_no as no, student_name as name FROM stmd WHERE ");
		if(type.trim().equals("name")){
			sSql.append("student_name LIKE '"+nameOrNumber+"%'");
		}else{
			sSql.append("student_no LIKE '"+nameOrNumber+"%'");
		}
		
		sSql.append("LIMIT 10");
		gSql.append("LIMIT 10");
		
		List<Map>list=manager.ezGetBy(gSql.toString());
		list.addAll(manager.ezGetBy(sSql.toString()));
		
		
		
		out.println("<pront>");
		if(list.size()<1){
			out.println("<name>新同學</name>");
			out.println("<no>"+nameOrNumber+"</no>");
		}else{
		
			for(int i=0; i<list.size(); i++){
				//System.out.println(list.get(i));
				out.println("<name>"+list.get(i).get("name")+"</name>");
				out.println("<no>"+list.get(i).get("no")+"</no>");
			}			
		}	
		out.println("</pront>");
		out.close();
		
	}

}
