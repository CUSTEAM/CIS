package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class CountStudentByClass extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		String dept=request.getParameter("dept");

		if(dept.equals("all")){
			dept="%";
		}

		List countList=manager.CountStudentBy(dept);
		ServletContext context = request.getSession().getServletContext();
		String schoolName=(String) context.getAttribute("SchoolName_ZH");
		out.println("<html>");
		out.println("<head><title>"+schoolName+" - 學生統計</title>" +
				"<style>" +
				"BODY { " +
				"FONT-FAMILY: Arial;" +
				"}" +

				"td {" +
				"FONT-SIZE: 10px;" +
				"}" +
				"" +
				"</style>" +
				"<MEAT HTTP-EQUIV='Pragma' CONTENT='no-cache'>" +
				"</head>");

		out.println("<body onload='resizeBy(10, 10)'>");
		out.println("<table><tr><td>查到了 "+countList.size()+" 個班, ? 個人</td></tr></table>");
		out.println("<table>");
		//out.println("<c:if test='${not empty delStudents}'>");// ????
		out.println("	<tr>");
		out.println("		<td>");
		out.println("			<table border='0' align='left' cellpadding='0' cellspacing='1' bgcolor='#CFE69F' width='100%'>");
		out.println("				");
		out.println("				<tr height='20'>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>&nbsp;班級&nbsp;</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>&nbsp;人數&nbsp;</td>");
		out.println("				</tr>");
		for(int i=0; i<countList.size(); i++){
			out.println("				<tr height='20'>");
			out.println("					<td align='center'");
			if(i%2==1){
				out.print("bgcolor='#f0fcd7'>");
				}else{
				out.print("bgcolor='ffffff'>");
				}
			out.print(((Map)countList.get(i)).get("ClassName")+"</td>");

			out.println("					<td align='center'");
			if(i%2==1){
				out.print("bgcolor='#f0fcd7'>");
				}else{
				out.print("bgcolor='ffffff'>");
				}
			out.print(((Map)countList.get(i)).get("COUNT(*)")+"</td>");
			out.println("				</tr>");




		}
		out.println("						</table>");



		out.println("</body>");
		out.println("</html>");
		out.close();
	}

}
