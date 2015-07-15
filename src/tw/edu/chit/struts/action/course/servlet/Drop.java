package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

public class Drop extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=Drop.xls");

		int schoolYear = manager.getSchoolYear();
		int schoolTerm = manager.getSchoolTerm();

		String drop = request.getParameter("drop");
		String sql = "";

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		out.println("<table>");
		out.println("<tr>");
		out.println("<td>");

		out
				.println("			<table border='1' align='left' cellpadding='0' cellspacing='1' bgcolor='#f0fcd7' width='100%'>");
		out.println("");
		out.println("				<tr height='20'>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學號</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>身分證</td>");
		if (drop.equals("out")) {
			out.println("					<td align='center' bgcolor='#f0fcd7'>離校日期</td>");
			sql = "SELECT student_no, idno, occur_date FROM Gstmd WHERE occur_year='"
					+ schoolYear + "' AND occur_term='" + schoolTerm + "'";

		} else {
			out.println("					<td align='center' bgcolor='#f0fcd7'>入校日期</td>");
			sql = "SELECT student_no, idno, occur_date FROM stmd WHERE occur_year='"
					+ schoolYear + "' AND occur_term='" + schoolTerm + "'";
		}

		out.println("				</tr>");

		List students = manager.ezGetBy(sql);
		for (int i = 0; i < students.size(); i++) {
			if (i % 2 == 1) {
				out.println("				<tr bgcolor='#ffffff'>");
			} else {
				out.println("				<tr bgcolor='#f0fcd7'>");
			}

			out.println("					<td align='left'>"
					+ ((Map) students.get(i)).get("student_no") + "</td>");
			out.println("					<td align='left'>"
					+ ((Map) students.get(i)).get("idno") + "</td>");
			out.println("					<td align='left'>"
					+ ((Map) students.get(i)).get("occur_date") + "</td>");
			out.println("				</tr>");
		}

		out.println("</table>");
		out.print("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</body>");

		out.println("</html>");
		out.close();
	}
}
