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
import tw.edu.chit.util.Toolket;

/**
 * 遠距人員對應表
 * 
 * @author JOHN
 * 
 */
public class ElearningOrg extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=ElearningOrg.xls");
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
		out.println("					<td align='center' bgcolor='#f0fcd7'>身份</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>所屬組織代碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>帳號</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>姓名</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>密碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>性別</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>電子郵件</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>身分證字號</td>");

		out.println("				</tr>");
		// String schoolYear=manager.getSchoolYear().toString();
		// String schoolTerm=manager.getSchoolTerm().toString();
		/*
		 * StringBuilder sb=new StringBuilder("SELECT st.email, st.sex, st.idno,
		 * st.student_name, c.Oid, s.student_no " + "FROM Dtime d, Seld s, Class
		 * c, stmd st " + "WHERE d.Oid=s.Dtime_oid AND c.ClassNo=st.depart_class
		 * AND " + "st.student_no=s.student_no AND d.Oid IN(");
		 * 
		 * for(int i=0; i<dtimeList.size(); i++){
		 * sb.append("'"+((Map)dtimeList.get(i)).get("oid")+"', "); }
		 * sb.delete(sb.length()-2, sb.length()); sb.append(") GROUP BY
		 * st.student_no");
		 */

		List students = manager
				.ezGetBy("SELECT st.student_no, st.email, st.sex, st.idno, st.student_name, c.Oid "
						+ "FROM stmd st, Class c Where st.depart_class=c.ClassNo ORDER BY st.depart_class");
		for (int j = 0; j < students.size(); j++) {
			if (j % 2 == 1) {
				out.println("<tr height='20'>");
			} else {
				out.println("<tr height='20' bgColor='#ffffff'>");
			}

			out.println("<td align='center'>4</td>");
			out.println("<td align='center'>"
					+ ((Map) students.get(j)).get("Oid") + "</td>");
			out.println("<td align='center' style='mso-number-format:\\@'>"
					+ ((Map) students.get(j)).get("student_no") + "</td>");
			out.println("<td align='center'>"
					+ ((Map) students.get(j)).get("student_name") + "</td>");
			out.println("<td align='center'></td>");
			out.println("<td align='center'>"
					+ Toolket.getSex(((Map) students.get(j)).get("sex")
							.toString()) + "</td>");

			if (((Map) students.get(j)).get("email") == null) {
				out.println("<td align='center'></td>");
			} else {
				out.println("<td align='center'>"
						+ ((Map) students.get(j)).get("email") + "</td>");
			}

			out.println("<td align='center'>"
					+ ((Map) students.get(j)).get("idno") + "</td>");
			out.println("</tr>");

		}

		out.print("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</body>");

		out.println("</html>");
		out.close();
	}
}
