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
 * 課程對應
 * 
 * @author JOHN
 * 
 */
public class ElearningCou extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=ElearningCou.xls");
		HttpSession session = request.getSession(false);

		List dtimeList = (List) session.getAttribute("dtimeList");
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
		out.println("					<td align='center' bgcolor='#f0fcd7'>科目代碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>科目名稱</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>本校科目代碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>本校班級代碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>開課班級</td>");

		out.println("					<td align='center' bgcolor='#f0fcd7'>授課教師</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>代理教師</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>開放日期</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>結束日期</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>科目簡介</td>");

		out.println("				</tr>");

		int schoolYear = manager.getSchoolYear();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		if (c.get(Calendar.MONTH) > 7 && c.get(Calendar.MONTH) < 8) {
			schoolYear = schoolYear + 1;
		}
		String schoolTerm = (String) session.getAttribute("xterm");

		for (int i = 0; i < dtimeList.size(); i++) {

			List courses = manager
					.ezGetBy("SELECT d.depart_class, d.cscode, d.Oid, e.cname, c.Oid as csnOid, c.chi_name, cl.Oid as clOid, d.techid, cl.ClassName "
							+ "FROM Csno c, Class cl, Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno WHERE cl.ClassNo=d.depart_class AND "
							+ "d.cscode=c.cscode AND d.Oid="
							+ ((Map) dtimeList.get(i)).get("oid"));

			for (int j = 0; j < courses.size(); j++) {
				if (i % 2 == 1) {
					out.println("<tr height='20'>");
				} else {
					out.println("<tr height='20' bgColor='#ffffff'>");
				}
				// 舊版以班代+課代以獲得最大長度
				// out.println("<td
				// align='center'>"+schoolYear+schoolTerm+((Map)courses.get(j)).get("csnOid")+((Map)courses.get(j)).get("clOid")+"</td>");
				String Oid = ((Map) courses.get(j)).get("Oid").toString();
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
				out.println("<td align='left'>"
						+ ((Map) courses.get(j)).get("chi_name") + "("
						+ ((Map) courses.get(j)).get("ClassName") + ")</td>");
				out.println("<td align='center'>"
						+ ((Map) courses.get(j)).get("cscode") + "</td>");
				out.println("<td align='center'>"
						+ ((Map) courses.get(j)).get("depart_class") + "</td>");
				out.println("<td align='center'>"
						+ ((Map) courses.get(j)).get("clOid") + "</td>");
				if (((Map) courses.get(j)).get("cname") != null) {
					out.println("<td align='center'>"
							+ ((Map) courses.get(j)).get("cname") + "</td>");
				} else {
					out.println("<td align='center'></td>");
				}

				out.println("<td align='center'></td>");
				out.println("<td align='center'></td>");
				out.println("<td align='center'></td>");
				out.println("<td align='center'></td>");
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
