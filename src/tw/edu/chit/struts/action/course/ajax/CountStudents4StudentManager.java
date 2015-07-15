package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;

/**
 * 人數統計表
 * @author JOHN
 *
 */
public class CountStudents4StudentManager extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		HttpSession session = request.getSession(false);
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		SummerManager summerManager=(SummerManager) ctx.getBean("summerManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=CountStudents4StudentManager.xls");
		
		PrintWriter out=response.getWriter();
		
		List students=(List)session.getAttribute("students");
		
		StringBuilder sb=new StringBuilder("SELECT c.ClassNo, c.ClassName FROM Class c, stmd s WHERE s.depart_class=c.ClassNo AND s.student_no IN(");
		for(int i=0; i<students.size(); i++){
			sb.append("'"+((Map)students.get(i)).get("student_no")+"',");
		}
		sb.delete(sb.length()-1, sb.length());
		//sb.append(") GROUP BY c.ClassNo ORDER BY c.DeptNo, c.ClassNo");
		//sb.append(") GROUP BY c.ClassNo ORDER BY c.DeptNo, c.ClassNo");//2010/1/5要求不列入1220,  122A
		sb.append(") AND(s.depart_class<>'122A' AND s.depart_class<>'1220') GROUP BY c.ClassNo ORDER BY c.DeptNo, c.ClassNo");
		
		List classes=manager.ezGetBy(sb.toString());
		List list=new ArrayList();
		Map map;
		int totalMen=0;
		int totalGirls=0;
		
		out.println("<table width='100%' border='0'>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<table width='100%' border='1'>");
		out.println("<tr bgcolor='#f0fcd7'>");
		out.println("<td>班別</td>");
		out.println("<td>男生</td>");
		out.println("<td>女生</td>");
		out.println("<td>合計</td>");
		
		out.println("<td>班別</td>");
		out.println("<td>男生</td>");
		out.println("<td>女生</td>");
		out.println("<td>合計</td>");
		
		out.println("<td>班別</td>");
		out.println("<td>男生</td>");
		out.println("<td>女生</td>");
		out.println("<td>合計</td>");
		
		out.println("<td>班別</td>");
		out.println("<td>男生</td>");
		out.println("<td>女生</td>");
		out.println("<td>合計</td>");
		out.println("</tr>");
		
		int tmp=0;
		
		for(int i=0; i<classes.size(); i++){
			int total=summerManager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE depart_class='"+((Map)classes.get(i)).get("ClassNo")+"'");
			int men=summerManager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE depart_class='"+((Map)classes.get(i)).get("ClassNo")+"' AND sex='1'");
			int girls=total-men;
			totalMen=totalMen+men;
			totalGirls=totalGirls+girls;
			
			if(i>=tmp){
				tmp=tmp+4;
				out.println("<tr>");
			}
			
			out.println("<td>"+((Map)classes.get(i)).get("ClassName")+"</td>");
			out.println("<td>"+men+"</td>");
			out.println("<td>"+girls+"</td>");
			out.println("<td>"+total+"</td>");
			
			if(i>=tmp){
				out.println("</tr>");
			}
		}		
		out.println("</table>");
		
		out.println("<table width='100%' border='0'>");
		out.println("<tr>");
		out.println("<td colspan='16'>");
		out.println("<table width='100%'>");
		out.println("<tr>");
		out.println("<td>本次查詢結果:</td>");
		out.println("<td>男:"+totalMen+"</td>");
		out.println("<td>女:"+totalGirls+"</td>");
		out.println("<td>共:"+(totalGirls+totalMen)+"</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.close();
	}
}
