package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
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

/**
 * 選課對應
 * 
 * @author JOHN
 * 
 */
public class ElearningSel extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=ElearningSel.xls");
		HttpSession session = request.getSession(false);

		List dtimeList = (List) session.getAttribute("dtimeList");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		out.println("<table>");
		// out.println("<c:if test='${not empty dtimeList}'>"); //???
		out.println("<tr>");
		out.println("<td>");

		out
				.println("			<table border='1' align='left' cellpadding='0' cellspacing='1' bgcolor='#f0fcd7' width='100%'>");
		out.println("");
		out.println("				<tr height='20'>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>科目代碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學生帳號</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>必選修</td>");

		out.println("				</tr>");
		int schoolYear = manager.getSchoolYear();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		System.out.println(c.get(Calendar.MONTH));
		if (c.get(Calendar.MONTH) > 7 && c.get(Calendar.MONTH) < 8) {
			schoolYear = schoolYear + 1;
		}
		String schoolTerm = (String) session.getAttribute("xterm");
		for (int i = 0; i < dtimeList.size(); i++) {

			List students = manager
					.ezGetBy("SELECT d.Oid, cs.Oid as csnOid, cl.Oid as clOid, s.student_no "
							+ "FROM Dtime d, Seld s, Csno cs, Class cl WHERE d.Oid=s.Dtime_oid AND "
							+ "d.cscode=cs.cscode AND cl.ClassNo=d.depart_class AND d.Oid="
							+ ((Map) dtimeList.get(i)).get("oid"));

			for (int j = 0; j < students.size(); j++) {
				
				out.println("<tr height='20'>");
				
				// 舊版以班代+課代以獲得最大長度
				// out.println("<td
				// align='center'>"+schoolYear+schoolTerm+((Map)students.get(j)).get("csnOid")+((Map)students.get(j)).get("clOid")+"</td>");
				String Oid = ((Map) students.get(j)).get("Oid").toString();
				int tmp = Oid.length();

				try {
					for (int k = 0; k < 5 - tmp; k++) {
						Oid = "0" + Oid;
					}
				} catch (Exception e) {
					Oid = "ERROR";
				}

				out.println("<td align='center'>" + schoolYear + schoolTerm
						+ Oid + "</td>");
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ ((Map) students.get(j)).get("student_no") + "</td>");

				out.println("<td align='center'>1</td>");
				out.println("</tr>");

			}

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
