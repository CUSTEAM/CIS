package tw.edu.chit.struts.action.onlineService.servlet;

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

public class List4OnlineServices extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");

		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment;Filename=Excel.xls");
		PrintWriter out = response.getWriter();
		out.println("<table width='100%' border='1'>  ");
		out.println("<tr bgcolor='#f0fcd7'>");

		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>狀態</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>編號</td>");

		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>學號</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");

		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>電話</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>手機</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>地址</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>電郵</td>");

		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>中文成績單</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>役期成績單</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>成績附排名</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業證書</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>修業證書</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>英文成績單</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>英文畢業證書</td>");

		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>申請日期</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>到期日</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>完成日</td>");

		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>總價</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>取件</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>經辦</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>連絡</td>");

		out.println("</tr>");

		List cases = (List) session.getAttribute("cases");
		for (int i = 0; i < cases.size(); i++) {
			if (i % 2 == 0) {
				out.println("<tr>");
			} else {
				out.println("<tr bgcolor='#f0fcd7'>");
			}

			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("status") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("doc_no") + "</td>");

			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("student_no") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("student_name")
							+ "</td>");
			out
					.println("<td align='left' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("telphone") + "</td>");
			out
					.println("<td align='left' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("cellphone") + "</td>");
			out
					.println("<td align='left' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("address") + "</td>");
			out
					.println("<td align='left' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("email") + "</td>");

			if (((Map) cases.get(i)).get("tcv") == null) {
				out
						.println("<td align='center' style='mso-number-format:@' nowrap></td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:@' nowrap>"
								+ ((Map) cases.get(i)).get("tcv") + "</td>");
			}
			if (((Map) cases.get(i)).get("tcv_army") == null) {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) cases.get(i)).get("tcv_army")
								+ "</td>");
			}
			if (((Map) cases.get(i)).get("tcvigr") == null) {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) cases.get(i)).get("tcvigr") + "</td>");
			}
			if (((Map) cases.get(i)).get("gcr") == null) {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) cases.get(i)).get("gcr") + "</td>");
			}
			if (((Map) cases.get(i)).get("cscna") == null) {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) cases.get(i)).get("cscna") + "</td>");
			}
			if (((Map) cases.get(i)).get("tev") == null) {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) cases.get(i)).get("tev") + "</td>");
			}
			if (((Map) cases.get(i)).get("gcev") == null) {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) cases.get(i)).get("gcev") + "</td>");
			}

			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("send_time") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("expect_time") + "</td>");

			if (((Map) cases.get(i)).get("complete_time") == null) {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) cases.get(i)).get("complete_time")
								+ "</td>");
			}

			out
					.println("<td align='right' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("total_pay") + "</td>");
			out
					.println("<td align='left' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("get_method") + "</td>");

			if (((Map) cases.get(i)).get("terminator") == null) {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ manager
										.ezGetString("SELECT cname FROM empl WHERE idno='"
												+ ((Map) cases.get(i))
														.get("terminator")
												+ "'") + "</td>");
			}
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) cases.get(i)).get("connects") + "</td>");

			out.println("</tr>");

		}

		out.println("</table>");
		out.close();
		// return null;
	}
}
