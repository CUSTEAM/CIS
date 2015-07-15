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

public class StudentSel extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=list4Persons.xls");
		//response.setContentType("text/html; charset=UTF-8");
		//String dtimeOid=request.getParameter("Oid");
		//String dtimeOid[]={request.getParameter("Oid")};
		List stuList=manager.getSeldBy(request.getParameter("Oid"));


		PrintWriter out=response.getWriter();

		out.println("<table>");
		//out.println("<c:if test='${not empty delStudents}'>");// ????
		out.println("	<tr>");
		out.println("		<td>");
		out.println("			<table border='1' align='left' cellpadding='0' cellspacing='1' bgcolor='#f0fcd7' width='100%'>");
		out.println("				");
		out.println("				<tr height='20'>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學生學號</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學生姓名</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>性別</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學生班級代碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學生所屬班級</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>開課班級代碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>開課班級名稱</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>課程代碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>課程名稱</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學分</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>時數</td>");
		out.println("				</tr>");
		for(int i=0; i<stuList.size(); i++){
			
			if(i%2==1){
				out.println("				<tr height='20'");
			}else{
				out.println("				<tr height='20' bgcolor='#ffffff'>");
			}
			out.println("<td align='center' style='mso-number-format:\\@'>"+((Map)stuList.get(i)).get("student_no")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@'>"+((Map)stuList.get(i)).get("student_name")+"</td>");
			
			if(((Map)stuList.get(i)).get("sex")!=null){
				if(((Map)stuList.get(i)).get("sex").equals("1")){
					out.println("<td align='center'>男</td>");
				}else{
					out.println("<td align='center'>女</td>");
				}				
			}else{
				out.println("<td align='center'>?</td>");
			}
			
			out.println("<td align='center' style='mso-number-format:\\@'>"+((Map)stuList.get(i)).get("ClassNo")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@'>"+((Map)stuList.get(i)).get("ClassName")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@'>"+((Map)stuList.get(i)).get("depart_class")+"</td>");
			out.println("<td align='center'>"+((Map)stuList.get(i)).get("ClassName2")+"</td>");
			out.println("<td align='center'>"+((Map)stuList.get(i)).get("cscode")+"</td>");
			out.println("<td align='center'>"+((Map)stuList.get(i)).get("chi_name")+"</td>");
			out.println("<td align='center'>"+((Map)stuList.get(i)).get("credit")+"</td>");
			out.println("<td align='center'>"+((Map)stuList.get(i)).get("thour")+"</td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.close();
	}

}
