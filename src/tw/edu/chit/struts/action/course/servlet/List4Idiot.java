package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class List4Idiot extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=List4Course35.xls");
		
		
		PrintWriter out=response.getWriter();
		out.println("			<table border='1' align='left' cellpadding='0' cellspacing='1' bgcolor='#CFE69F' width='100%'>");
		out.println("");
		out.println("				<tr height='20'>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學年</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學期</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>開課學院</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>開課系所</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學制</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學期課號</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>課程名稱</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>修別</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>講授時數(每週)</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>實習時數(每週)</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>總學分數</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>授課教師</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>修課人數</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>外語授課</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>授課語言</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>專業課程</td>");
		out.println("				</tr>");
		out.println("			</table>");
		
		out.close();
		
	}
}
