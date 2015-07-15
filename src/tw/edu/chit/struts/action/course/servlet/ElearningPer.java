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
 * 遠距組織對應表
 * 
 * @author JOHN
 * 
 */
public class ElearningPer extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=ElearningPer.xls");
		HttpSession session = request.getSession(false);

		/*
		 * List dtimeList=(List)session.getAttribute("dtimeList");
		 * 
		 * StringBuilder sb=new StringBuilder("SELECT depart_class FROM Dtime
		 * WHERE Oid IN("); for(int i=0; i<dtimeList.size(); i++){
		 * sb.append("'"+((Map)dtimeList.get(i)).get("oid")+"', "); }
		 * sb.delete(sb.length()-2, sb.length()); sb.append(") GROUP BY
		 * depart_class"); List dtimes=manager.ezGetBy(sb.toString());
		 */

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
		out.println("					<td align='center' bgcolor='#f0fcd7'>組織代碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>組織名稱</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>組織主管帳號</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>組織代理人帳號</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>上層組織代碼</td>");

		out.println("				</tr>");
		// String schoolYear=manager.getSchoolYear().toString();
		// String schoolTerm=manager.getSchoolTerm().toString();
		List classes = manager
				.ezGetBy("SELECT cl.Oid, cl.ClassName FROM stmd st, Class cl WHERE st.depart_class=cl.ClassNo GROUP BY st.depart_class");
		for (int j = 0; j < classes.size(); j++) {

			if (j % 2 == 1) {
				out.println("<tr height='20'>");
			} else {
				out.println("<tr height='20' bgColor='#ffffff'>");
			}

			out.println("<td align='center'>"
					+ ((Map) classes.get(j)).get("Oid") + "</td>");
			out.println("<td align='center'>"
					+ ((Map) classes.get(j)).get("ClassName") + "</td>");
			out.println("<td align='center'></td>");
			out.println("<td align='center'>fff</td>");
			out.println("<td align='center'></td>");

			out.println("</tr>");

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
