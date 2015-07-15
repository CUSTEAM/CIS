package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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

public class List4SummerSseld extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=list4SportRat.xls");
		HttpSession session = request.getSession(false);

		List SdtimeList = (List) session.getAttribute("SdtimeList");

		List allStudent = new ArrayList();
		Map map;
		List single;
		String sql;
		for (int i = 0; i < SdtimeList.size(); i++) {

			sql = "SELECT d.status, d.seqno, sa.name, sa.no, cs.chi_name, cs.cscode, st.student_name, st.student_no, "
					+ "cl.ClassNo, cl.ClassName, d.opt, d.thour, d.credit, s.score FROM Sseld s, stmd st, Sdtime d, "
					+ "Sabbr sa, Class cl, Csno cs WHERE s.student_no=st.student_no AND d.cscode=s.cscode AND "
					+ "d.depart_class=s.csdepart_class AND sa.no=d.depart_class AND cl.ClassNo=st.depart_class AND "
					+ "cs.cscode=d.cscode AND "
					+ "s.csdepart_class='"
					+ ((Map) SdtimeList.get(i)).get("depart_class")
					+ "' AND "
					+ "s.cscode='"
					+ ((Map) SdtimeList.get(i)).get("cscode")
					+ "'";

			//System.out.println(sql);
			single = manager.ezGetBy(sql);

			for (int j = 0; j < single.size(); j++) {
				map = new HashMap();
				map.put("seqno", ((Map) single.get(j)).get("seqno"));
				map.put("name", ((Map) single.get(j)).get("name"));
				map.put("no", ((Map) single.get(j)).get("no"));
				map.put("chi_name", ((Map) single.get(j)).get("chi_name"));
				map.put("cscode", ((Map) single.get(j)).get("cscode"));
				map.put("student_name", ((Map) single.get(j))
						.get("student_name"));
				map.put("student_no", ((Map) single.get(j)).get("student_no"));
				map.put("ClassName", ((Map) single.get(j)).get("ClassName"));
				map.put("ClassNo", ((Map) single.get(j)).get("ClassNo"));
				map.put("opt", ((Map) single.get(j)).get("opt"));
				map.put("thour", ((Map) single.get(j)).get("thour"));
				map.put("credit", ((Map) single.get(j)).get("credit"));
				map.put("score", ((Map) single.get(j)).get("score"));
				if (((Map) single.get(j)).get("status").equals("1")) {
					map.put("status", "退費");
				} else {
					map.put("status", "");
				}

				allStudent.add(map);
			}

		}
		
		//System.out.println(allStudent.size());
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<table border='1' width='100%'>");
		out.println("				<tr>");
		
		out.println("					<td align='center'>梯次</td>");
		out.println("					<td align='center'>開課班級</td>");
		out.println("					<td align='center'>班級代碼</td>");
		out.println("					<td align='center'>課程名稱</td>");
		out.println("					<td align='center'>課程代碼</td>");
		out.println("					<td align='center'>學生姓名</td>");
		out.println("					<td align='center'>學號</td>");
		out.println("					<td align='center'>學生班級</td>");
		out.println("					<td align='center'>班級代碼</td>");
		out.println("					<td align='center'>選別</td>");
		out.println("					<td align='center'>時數</td>");
		out.println("					<td align='center'>學分數</td>");
		out.println("					<td align='center'>成績</td>");
		out.println("				</tr>");

		for (int i = 0; i < allStudent.size(); i++) {			
			
			out.println("				<tr>");
			//out.println("					<td align='center'>"+ ((Map) allStudent.get(i)).get("status") + "</td>");
			out.println("					<td align='center'>"
					+ ((Map) allStudent.get(i)).get("seqno") + "</td>");
			out.println("					<td align='center'>"
					+ ((Map) allStudent.get(i)).get("name") + "</td>");
			out
					.println("					<td align='center' style='mso-number-format:\\@'>"
							+ ((Map) allStudent.get(i)).get("no") + "</td>");
			out.println("					<td align='center'>"
					+ ((Map) allStudent.get(i)).get("chi_name") + "</td>");
			out.println("					<td align='center'>"
					+ ((Map) allStudent.get(i)).get("cscode") + "</td>");
			out.println("					<td align='center'>"
					+ ((Map) allStudent.get(i)).get("student_name") + "</td>");
			out
					.println("					<td align='center' style='mso-number-format:\\@'>"
							+ ((Map) allStudent.get(i)).get("student_no")
							+ "</td>");
			out.println("					<td align='center'>"
					+ ((Map) allStudent.get(i)).get("ClassName") + "</td>");
			out
					.println("					<td align='center' style='mso-number-format:\\@'>"
							+ ((Map) allStudent.get(i)).get("ClassNo")
							+ "</td>");
			out.println("					<td align='center'>"
					+ ((Map) allStudent.get(i)).get("opt") + "</td>");
			out.println("					<td align='center'>"
					+ ((Map) allStudent.get(i)).get("thour") + "</td>");
			out.println("					<td align='center'>"
					+ ((Map) allStudent.get(i)).get("credit") + "</td>");
			out.println("					<td align='center'>"
					+ ((Map) allStudent.get(i)).get("score") + "</td>");
			out.println("				</tr>");
			
			
			//out.close();
		}
		
		out.println("				</table>");
		out.println("				</body>");
		out.println("				</html>");

	}

}
