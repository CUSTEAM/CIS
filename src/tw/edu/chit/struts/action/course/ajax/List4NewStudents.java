package tw.edu.chit.struts.action.course.ajax;

import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_YEAR;

import java.io.IOException;
import java.io.PrintWriter;
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
import tw.edu.chit.util.IConstants;

public class List4NewStudents extends HttpServlet {

	private static final long serialVersionUID = 4423168587301777724L;

	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) ctx
				.getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=List4Student.xls");

		CourseManager cm = (CourseManager) ctx
				.getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String year = cm.getNowBy(PARAMETER_SCHOOL_YEAR);
		String term = cm.getNowBy(PARAMETER_SCHOOL_TERM);
		List pageList = (List) session.getAttribute("students");
		Map map = null;
		PrintWriter out = response.getWriter();
		out.println("<table width='100%' border='1'>  ");
		out.println("<tr bgcolor='#f0fcd7'>");
		out
				.println("<td colspan='12' align='center' style='mso-number-format:\\@' nowrap><font size='+1'>中華科技大學  "
						+ year + "學年度第" + term + "學期 新生名冊</font></td>");
		out.println("</tr>");
		out.println("<tr bgcolor='#f0fcd7'>");

		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>編碼</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>系(科)組</td>");
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
				.println("<td align='center' style='mso-number-format:\\@' nowrap>身份</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業學校名稱</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業科系</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>備註</td>");
		out.println("</tr>");

		for (int i = 0; i < pageList.size(); i++) {
			
			out.println("  <tr>");
			

			map = new HashMap();
			map = (Map) manager
					.ezGetBy(
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
									+ ((Map) pageList.get(i)).get("student_no")
									+ "'").get(0);

			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ String.valueOf(i + 1) + "</td>");
			out.println("<td align='center' style='mso-number-format:\\@'>"
					+ map.get("ClassName") + "</td>");

			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ map.get("student_name") + "</td>");
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

			out.println("<td align='center' style='mso-number-format:\\@'>"
					+ map.get("entrance") + "</td>");
			out.println("<td align='center' style='mso-number-format:\\@'>"
					+ map.get("name") + "</td>");
			out.println("<td align='center' style='mso-number-format:\\@'>"
					+ map.get("schl_name") + "</td>");
			out.println("<td align='center' style='mso-number-format:\\@'>"
					+ map.get("grad_dept") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@'></td>");
			out.println("	</tr>");
		}

		out.println("</table>");
		out.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
