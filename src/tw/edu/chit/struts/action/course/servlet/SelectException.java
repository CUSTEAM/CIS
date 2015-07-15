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

/**
 * 選課異常
 * 
 * @author JOHN
 * 
 */
public class SelectException extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);

		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment;Filename=Excel.xls");
		PrintWriter out = response.getWriter();

		// TODO 有用到ADCD 廢adcd時要連動
		List list = (List) session.getAttribute("students");
		StringBuilder sb = new StringBuilder(
				"SELECT d.cscode, s.student_name, s.student_no, c.chi_name FROM "
						+ "AddDelCourseData a, stmd s, Dtime d, Csno c WHERE a.Adddraw='A' AND c.cscode=d.cscode AND "
						+ "s.student_no=a.Student_no AND a.Dtime_oid=d.Oid AND d.Sterm='2' AND s.student_no IN (");

		for (int i = 0; i < list.size(); i++) {
			sb.append("'" + ((Map) list.get(i)).get("student_no") + "', ");
		}

		sb.delete(sb.length() - 2, sb.length());
		sb.append(")");
		System.out.println(sb);
		list = manager.ezGetBy(sb.toString());

		out.println("<html xmlns='http://www.w3.org/TR/REC-html40'>");
		out.println("  <head>");
		out
				.println("    <meta http-equiv=Content-Type content='text/html; charset=UTF-8'>");
		out.println("  </head>");
		out.println("  <body lang=ZH-TW>");

		out.println("  <table border=1>");

		for (int i = 0; i < list.size(); i++) {
			if (manager.ezGetInt("SELECT COUNT(*)FROM ScoreHist WHERE cscode='"
					+ ((Map) list.get(i)).get("cscode") + "' AND "
					+ "student_no='" + ((Map) list.get(i)).get("student_no")
					+ "' AND score>60") > 0) {

				System.out
						.println("SELECT COUNT(*)FROM ScoreHist WHERE cscode='"
								+ ((Map) list.get(i)).get("cscode") + "' AND "
								+ "student_no='"
								+ ((Map) list.get(i)).get("student_no") + "'");

				if (i % 2 == 1) {
					out.println("  	<tr bgcolor='#f0fcd7'>");
				} else {
					out.println("  	<tr>");
				}

				out.println("  		<td>");
				out.println(((Map) list.get(i)).get("student_no"));
				out.println("  		</td>");
				out.println("  		<td>");
				out.println(((Map) list.get(i)).get("student_name"));
				out.println("  		</td>");
				out.println("  		<td>");
				out.println(((Map) list.get(i)).get("cscode"));
				out.println("  		</td>");

				out.println("  		<td>");
				out.println(((Map) list.get(i)).get("chi_name"));
				out.println("  		</td>");
				out.println("  	</tr>");

			}

		}

		out.println("  </table>");

		out.println("  </body>");
		out.println("</html>");
		out.close();
	}

}
