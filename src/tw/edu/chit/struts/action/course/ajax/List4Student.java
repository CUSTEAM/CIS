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

public class List4Student extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=List4Student.xls");

		List pageList = (List) session.getAttribute("students");
		//List studentList = new ArrayList();
		Map map;
		Map rmark;
		PrintWriter out = response.getWriter();
		out.println("<style>td{font-size:18px; font-family:微軟正黑體;}</style>");

		out.println("<table width='100%' border='1'>  ");

		out.println("<tr bgcolor='#f0fcd7'>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>班級名稱</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>班級代碼</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>學號</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>性別</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>身分證字號</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>出生日期</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>入學年月</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>前學程畢業</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>身份</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>組別</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>出生省縣</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>出生鄉鎮市</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>電話</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>行動電話</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>現居郵編</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>現居地址</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>永久郵編</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>永久地址</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業學校代碼</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業學校名稱</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業科系</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業狀態</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>家長姓名</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更學年</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更學期</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更狀態</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更原因</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更日期</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更文號</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業號</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>輔系/雙主修</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>輔系/雙主修科系</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>電子郵件</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>身份備註</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>英譯姓名</td>");
		out.println("</tr>");

		for (int i = 0; i < pageList.size(); i++) {
			if (i % 2 == 1) {
				out.println("  <tr bgcolor='#f0fcd7'>");
			} else {
				out.println("  <tr>");
			}

			map = map=manager.ezGetMap(
					"SELECT s.*, c.ClassName, c5.name, c51.name as team, c3.name as county, "
							+ "c31.name as province, c52.name as status_name, c53.name as caurse_name, "
							+ "c54.name as ident_name2 FROM "
							+ "(((((((stmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo)LEFT OUTER JOIN "
							+ "code5 c5 ON c5.idno=s.ident AND c5.category='Identity')LEFT OUTER JOIN "
							+ "code5 c51 ON c51.idno=s.divi AND c51.category='GROUP')LEFT OUTER JOIN "
							+ "code3 c3 ON s.birth_county=c3.no)LEFT OUTER JOIN "
							+ "code3 c31 ON c31.no=s.birth_province)LEFT OUTER JOIN "
							+ "code5 c52 ON s.occur_status=c52.idno AND c52.category='Status')LEFT OUTER JOIN "
							+ "code5 c53 ON s.occur_cause=c53.idno AND c53.category='Cause')LEFT OUTER JOIN "
							+ "code5 c54 ON s.ident_basic=c54.idno AND c53.category='Identity' WHERE student_no='"
							+ ((Map) pageList.get(i)).get("student_no") + "'");
			
			if (map==null){
				map=manager.ezGetMap(
						"SELECT s.*, c.ClassName,c5.name, c51.name as team, c3.name as county, "
								+ "c31.name as province, c52.name as status_name, c53.name as caurse_name, "
								+ "c54.name as ident_name2 FROM "
								+ "(((((((Gstmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo)LEFT OUTER JOIN "
								+ "code5 c5 ON c5.idno=s.ident AND c5.category='Identity')LEFT OUTER JOIN "
								+ "code5 c51 ON c51.idno=s.divi AND c51.category='GROUP')LEFT OUTER JOIN "
								+ "code3 c3 ON s.birth_county=c3.no)LEFT OUTER JOIN "
								+ "code3 c31 ON c31.no=s.birth_province)LEFT OUTER JOIN "
								+ "code5 c52 ON s.occur_status=c52.idno AND c52.category='Status')LEFT OUTER JOIN "
								+ "code5 c53 ON s.occur_cause=c53.idno AND c53.category='Cause')LEFT OUTER JOIN "
								+ "code5 c54 ON s.ident_basic=c54.idno AND c53.category='Identity' WHERE student_no='"
								+ ((Map) pageList.get(i)).get("student_no") + "'");
			}
			
			if(map==null){
				continue;
			}

			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+ map.get("depart_class") + "</td>");
			out.println("<td align='center' style='mso-number-format:\\@'>"+ map.get("ClassName") + "</td>");

			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+ map.get("student_name") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ map.get("student_no") + "</td>");

			// 性別
			if (map.get("sex").equals("1")) {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>男</td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>女</td>");
			}
			
			
			out.println("<td align='center' style='mso-number-format:\\@'>"
					+ map.get("idno") + "</td>");// 身份證
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ map.get("birthday") + "</td>"); // 生日
			if (map.get("entrance") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("entrance") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("gradyear") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("gradyear") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("name") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("name") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("team") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("team") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("province") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("province") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("county") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("county") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("telephone") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"
						+ map.get("telephone") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("CellPhone") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"
						+ map.get("CellPhone") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("curr_post") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("curr_post") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("curr_addr") != null) {
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ map.get("curr_addr") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("perm_post") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("perm_post") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("perm_addr") != null) {
				out
						.println("<td align='left' style='mso-number-format:@' nowrap>"
								+ map.get("perm_addr") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("schl_code") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("schl_code") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("schl_name") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"
						+ map.get("schl_name") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("grad_dept") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"
						+ map.get("grad_dept") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("gradu_status") != null) {
				if (map.get("gradu_status").equals("1")) {
					out
							.println("<td align='center' style='mso-number-format:\\@'>畢</td>");
				} else {
					out
							.println("<td align='center' style='mso-number-format:\\@'>肆</td>");
				}
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@'>啥</td>");
			}

			if (map.get("parent_name") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("parent_name") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("occur_year") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("occur_year") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("occur_term") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("occur_term") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("status_name") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("status_name") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("caurse_name") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("caurse_name") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("occur_date") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("occur_date") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("occur_docno") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("occur_docno") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("occur_graduate_no") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("occur_graduate_no") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("ExtraStatus") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("ExtraStatus") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("ExtraDept") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ map.get("ExtraDept") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("Email") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"
						+ map.get("Email") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			// out.println("<td align='left'
			// style='mso-number-format:\\@'>"+map.get("ident_remark")+"</td>");
			rmark = manager.ezGetMap("SELECT * FROM Rmark WHERE student_no='"
					+ map.get("student_no") + "'");
			if (rmark != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"
						+ rmark.get("remark_name") + rmark.get("military")
						+ rmark.get("certificate") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (map.get("student_ename") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"
						+ map.get("student_ename") + "</td>");
			} else {
				out
						.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			out.println("	</tr>");
		}

		out.println("</table>");
		out.close();
	}

}
