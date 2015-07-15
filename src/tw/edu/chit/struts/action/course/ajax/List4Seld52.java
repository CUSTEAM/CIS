package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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

public class List4Seld52 extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=List4Seld52.xls");

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		out.println("<table>");
		out.println("	<tr>");
		out.println("		<td>");

		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String date = sf.format(new Date());

		out.println("");
		out
				.println("<table border='0' align='left' cellpadding='0' cellspacing='1' width='100%'>");
		out.println("	<tr>");
		out.println("		<td colspan='5' align='center'>");
		out.println("		<font face='標楷體' size='+2'>任教時數統計表</font>");
		out.println("		</td>");
		out.println("	</tr>");
		out.println("	<tr>");
		out.println("		<td colspan='5' align='right'>");
		out.println("		<font size='-2'>課程管理系統 " + date + "</font>");
		out.println("		</td>");
		out.println("	</tr>");
		out.println("</table>");

		out
				.println("			<table border='0' align='left' cellpadding='0' cellspacing='1' bgcolor='#CFE69F' width='100%'>");
		out.println("");
		out.println("				<tr height='20'>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學年</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學期</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學校名稱</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學號</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學分數</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>雙主修</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>輔系</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學程</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>校際選修</td>");
		out.println("				</tr>");

		HttpSession session = request.getSession(false);

		String sterm = (String) session.getAttribute("xterm");
		String classLess = (String) session.getAttribute("xless");

		// List dtimeList=(List)session.getAttribute("dtimeList");
		List students = manager
				.ezGetBy("SELECT * FROM Seld s, Dtime d WHERE d.Oid=s.Dtime_oid AND d.Sterm LIKE '"
						+ sterm
						+ "%' "
						+ "AND d.depart_class LIKE '"
						+ classLess + "%' GROUP BY s.student_no");

		System.out.println(students.size());

		out.println("</table>");
		out.println("</body>");

		out.println("</html>");

		out.close();

	}

}
