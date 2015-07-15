package tw.edu.chit.struts.action.personnel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
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
 * 表1-2-2
 * 
 * @author JOHN
 * 
 */
public class List4Emp122 extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=List4Student.xls");

		Iterator it = ((List) session.getAttribute("empls")).iterator();
		StringBuilder sb = new StringBuilder(
				"SELECT * FROM Empl_licence WHERE idno IN(");
		while (it.hasNext()) {
			sb.append("'" + ((Map) it.next()).get("idno") + "', ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append(")");
		// sb.append(") AND school_year='"+manager.getSchoolYear()+"' AND
		// school_term='"+manager.getSchoolTerm()+"'");

		List list = manager.ezGetBy(sb.toString());
		PrintWriter out = response.getWriter();
		out.println("<table width='100%' border='1'>  ");
		out.println("<tr bgcolor='#f0fcd7'>");

		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>學年度/學期</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>主聘系所</td>");// 問題
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>教師</td>");// 問題
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>證照類型</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>證照名稱</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>發照機構</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>證照字號</td>");

		out.println("</tr>");

		for (int i = 0; i < list.size(); i++) {
			out.println("<tr>");

			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) list.get(i)).get("school_year")
							+ "/"
							+ ((Map) list.get(i)).get("school_term") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) list.get(i)).get("deptNo") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) list.get(i)).get("idno") + "</td>");// 教師
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) list.get(i)).get("licence_type") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) list.get(i)).get("licence_name") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) list.get(i)).get("organ") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) list.get(i)).get("licence_no") + "</td>");

			out.println("</tr>");
		}
		out.println("</table>  ");
		out.close();
	}

}
