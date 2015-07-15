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

public class List4SummerRat extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=list4SummerRat.xls");

		String dtimeOid = request.getParameter("dtimeOid");
		String cscode = request.getParameter("cscode");
		String departClass = request.getParameter("departClass");

		// List list=manager.getSeld4SportBy(dtimeOid);
		List list = manager
				.ezGetBy("SELECT cl2.ClassName, s.*, st.student_name "
						+ "FROM Sdtime d, Csno c, Sseld s, Sabbr cl, stmd st, Class cl2 "
						+ "WHERE st.student_no=s.student_no AND st.depart_class=cl2.ClassNo AND "
						+ "d.cscode=c.cscode AND d.depart_class=cl.no AND s.cscode=d.cscode AND "
						+ "d.depart_class=s.csdepart_class AND " + "s.cscode='"
						+ cscode + "' AND " + "s.csdepart_class='"
						+ departClass + "' ORDER BY s.student_no");

		PrintWriter out = response.getWriter();
		out.println("<html>");

		// Map map=manager.getdtimeBy(dtimeOid);

		List title = manager
				.ezGetBy("SELECT cl.name, c.chi_name, e.cname FROM Sdtime d, Csno c, empl e, Sabbr cl WHERE "
						+ "d.cscode=c.cscode AND d.techid=e.idno AND  cl.no=d.depart_class AND d.cscode='"
						+ cscode + "' AND d.depart_class='" + departClass + "'");
		Map map = (Map) title.get(0);
		out.println("<table width='100%'>  ");
		out.println("  <tr>");
		out.println("		<td align='center'>開課班級: </td>");
		out.println("		<td align='left'>" + map.get("name") + "</td>");
		out.println("  </tr>");
		out.println("  <tr>");
		out.println("		<td align='center'>課程名稱: </td>");
		out.println("		<td align='left'>" + map.get("chi_name") + "</td>");
		out.println("  </tr>");
		out.println("  <tr>");
		out.println("		<td align='center'>任課教師: </td>");
		out.println("		<td align='left'>" + map.get("cname") + " 簽章:</td>");
		out.println("  </tr>");
		out.println("  <tr>");
		out.println("		<td align='center'>&nbsp;</td>");
		out.println("		<td align='left'>&nbsp;</td>");
		out.println("  </tr>");
		out.println("</table>");

		out.println("<table width='100%' border='1'>  ");
		out.println("  <tr>");
		out.println("		<td align='center'>班級</td>");
		out.println("		<td align='center'>學號</td>");
		out.println("		<td align='center'>姓名</td>");
		out.println("		<td align='center'>平時</td>");
		out.println("		<td align='center'>平時</td>");
		out.println("		<td align='center'>平時</td>");
		out.println("		<td align='center'>平時</td>");
		out.println("		<td align='center'>平時成績</td>");
		out.println("		<td align='center'>期中考</td>");
		out.println("		<td align='center'>期末考</td>");
		out.println("		<td align='center'>總成績</td>");
		out.println("	</tr>");

		for (int i = 0; i < list.size(); i++) {

			if (i % 2 == 1) {
				out.println("  <tr>");
			} else {
				out.println("  <tr bgcolor='#f0fcd7'>");
			}
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("ClassName") + "</td>");
			out.println("		<td align='center' style='mso-number-format:\\@'>"
					+ ((Map) list.get(i)).get("student_no") + "</td>");
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("student_name") + "</td>");
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("sc1") + "</td>");
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("sc2") + "</td>");
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("sc3") + "</td>");
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("sc4") + "</td>");
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("free_score") + "</td>");
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("med_score") + "</td>");
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("end_score") + "</td>");
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("score") + "</td>");
			out.println("	</tr>");

		}
		out.println("</table>");
		out.println("</html>");
		out.close();
	}

}
