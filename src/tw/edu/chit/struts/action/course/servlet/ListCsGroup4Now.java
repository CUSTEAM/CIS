package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * 學程報表
 * 
 * @author JOHN
 * 
 */
public class ListCsGroup4Now extends HttpServlet {

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
		out.println("<table border='1'>");

		StringBuilder sb;

		String term = manager.getSchoolTerm().toString();
		List tmp;
		if (request.getParameter("type") == null) {

			// 單一科系
			List aGroupSet = (List) session.getAttribute("aGroupSet");
			Map aGroup = (Map) session.getAttribute("aGroup");

			List list = new ArrayList();

			for (int i = 0; i < aGroupSet.size(); i++) {
				int count = manager
						.ezGetInt("SELECT COUNT(*)FROM Dtime d, Class c WHERE c.ClassNo=d.depart_class AND "
								+ "d.cscode='"
								+ ((Map) aGroupSet.get(i)).get("cscode")
								+ "' AND c.DeptNo='"
								+ ((Map) aGroupSet.get(i)).get("deptNo")
								+ "' AND d.Sterm='" + term + "'");
				if (count > 0) {
					sb = new StringBuilder();
					tmp = manager
							.ezGetBy("SELECT de.school_name FROM Dtime d, Class c, dept de WHERE de.no=c.dept AND c.ClassNo=d.depart_class AND "
									+ "d.cscode='"
									+ ((Map) aGroupSet.get(i)).get("cscode")
									+ "' AND c.DeptNo='"
									+ ((Map) aGroupSet.get(i)).get("deptNo")
									+ "' AND d.Sterm='" + term + "'");
					for (int j = 0; j < tmp.size(); j++) {
						sb.append(((Map) tmp.get(j)).get("school_name") + ", ");
					}
					((Map) aGroupSet.get(i)).put("count", count);
					((Map) aGroupSet.get(i)).put("schools", sb);
					list.add(aGroupSet.get(i));
				}
			}

			for (int i = 0; i < list.size(); i++) {
				if (i % 42 == 0) {
					out.println("<tr>");
					out
							.println("<td colspan='8' align='center' style='mso-number-format:\\@' nowrap><font face='標楷體'>"
									+ aGroup.get("cname") + "跨領域學程</font></td>");
					out.println("	</tr>");
					out.println("<tr>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap>系所</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap width='100'>學制(s)</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap>開課數</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap>課程代碼</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' width='100%' nowrap>課程名稱</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap>選別</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap>學分</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap>適用學年</td>");
					out.println("	</tr>");
				}

				if (i % 2 == 0) {
					out.println("<tr bgcolor='#f0fcd7'>");
				} else {
					out.println("<tr>");
				}

				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("deptName") + "</td>");
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("schools") + "</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("count") + "</td>");
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("cscode") + "</td>");
				out
						.println("<td align='left' style='mso-number-format:\\@' width='100%' nowrap>"
								+ ((Map) list.get(i)).get("chi_name") + "</td>");

				if (((Map) list.get(i)).get("opt").equals("1")) {
					out
							.println("<td align='left' style='mso-number-format:\\@' nowrap>核心必修</td>");
				} else {
					out
							.println("<td align='left' style='mso-number-format:\\@' nowrap>核心選修</td>");
				}
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("credit") + "</td>");
				if (((Map) list.get(i)).get("entrance") == null) {
					out
							.println("<td align='left' style='mso-number-format:\\@' nowrap></td>");
				} else {
					out
							.println("<td align='left' style='mso-number-format:\\@' nowrap>"
									+ ((Map) list.get(i)).get("entrance")
									+ "</td>");
				}

				out.println("</tr>");
			}

		} else {

			System.out
					.println("SELECT cs.deptNo, c.cscode, cs.*, c.chi_name, "
							+ "c5.name as deptName FROM "
							+ "CsGroupSet cs, Csno c, code5 c5 WHERE c5.category='Dept' AND c5.idno=cs.deptNo AND c.cscode=cs.cscode "
							+ "ORDER BY cs.deptNo, cs.opt, cs.cscode");

			// 全域性
			List aGroupSet = manager
					.ezGetBy("SELECT cs.deptNo, c.cscode, cs.*, c.chi_name, "
							+ "c5.name as deptName FROM "
							+ "CsGroupSet cs, Csno c, code5 c5 WHERE c5.category='Dept' AND c5.idno=cs.deptNo AND c.cscode=cs.cscode "
							+ "ORDER BY cs.deptNo, cs.opt, cs.cscode");

			List list = new ArrayList();

			for (int i = 0; i < aGroupSet.size(); i++) {
				int count = manager
						.ezGetInt("SELECT COUNT(*)FROM Dtime d, Class c WHERE c.ClassNo=d.depart_class AND "
								+ "d.cscode='"
								+ ((Map) aGroupSet.get(i)).get("cscode")
								+ "' AND c.DeptNo='"
								+ ((Map) aGroupSet.get(i)).get("deptNo")
								+ "' AND d.Sterm='" + term + "'");
				if (count > 0) {
					sb = new StringBuilder();
					tmp = manager
							.ezGetBy("SELECT de.school_name FROM Dtime d, Class c, dept de WHERE de.no=c.dept AND c.ClassNo=d.depart_class AND "
									+ "d.cscode='"
									+ ((Map) aGroupSet.get(i)).get("cscode")
									+ "' AND c.DeptNo='"
									+ ((Map) aGroupSet.get(i)).get("deptNo")
									+ "' AND d.Sterm='" + term + "'");
					for (int j = 0; j < tmp.size(); j++) {
						sb.append(((Map) tmp.get(j)).get("school_name") + ", ");
					}
					((Map) aGroupSet.get(i)).put("count", count);
					((Map) aGroupSet.get(i)).put("schools", sb);
					list.add(aGroupSet.get(i));
				}
			}

			for (int i = 0; i < list.size(); i++) {
				if (i % 42 == 0) {
					out.println("<tr>");
					out
							.println("<td colspan='8' align='center' style='mso-number-format:\\@' nowrap><font face='標楷體'>跨領域學程</font></td>");
					// out.println("<td colspan='8' align='center'
					// style='mso-number-format:\\@' nowrap><font
					// face='標楷體'>"+aGroup.get("cname")+"跨領域學程</font></td>");
					out.println("	</tr>");
					out.println("<tr>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap>系所</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap width='100'>學制(s)</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap>開課數</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap>課程代碼</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' width='100%' nowrap>課程名稱</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap>選別</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap>學分</td>");
					out
							.println("<td align='center' style='mso-number-format:\\@' nowrap>適用學年</td>");
					out.println("	</tr>");
				}

				if (i % 2 == 0) {
					out.println("<tr bgcolor='#f0fcd7'>");
				} else {
					out.println("<tr>");
				}

				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("deptName") + "</td>");
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("schools") + "</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("count") + "</td>");
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("cscode") + "</td>");
				out
						.println("<td align='left' style='mso-number-format:\\@' width='100%' nowrap>"
								+ ((Map) list.get(i)).get("chi_name") + "</td>");

				if (((Map) list.get(i)).get("opt").equals("1")) {
					out
							.println("<td align='left' style='mso-number-format:\\@' nowrap>核心必修</td>");
				} else {
					out
							.println("<td align='left' style='mso-number-format:\\@' nowrap>核心選修</td>");
				}
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("credit") + "</td>");
				if (((Map) list.get(i)).get("entrance") == null) {
					out
							.println("<td align='left' style='mso-number-format:\\@' nowrap></td>");
				} else {
					out
							.println("<td align='left' style='mso-number-format:\\@' nowrap>"
									+ ((Map) list.get(i)).get("entrance")
									+ "</td>");
				}

				out.println("</tr>");

			}

		}

		out.println("</table>");

		out.close();
	}
}