package tw.edu.chit.struts.action.personnel.servlet;

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

public class List4EmplGeneral extends HttpServlet {
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

		List list = (List) session.getAttribute("empls");
		StringBuilder sb = new StringBuilder(
				"SELECT e.pcode, e.idno, e.cname, e.insno, e.ename, e.sex, e.bdate, e.unit, "
						+ "ce1.name as categoryName, e.sname, e.telephone, e.pzip, e.paddr, e.czip, e.caddr, e.StartDate, "
						+ "e.EndDate, ce2.name as StatusCauseName, ce3.name as degreeName, ce4.name as StatusName, "
						+ "e.CellPhone, e.Email, ce5.name as TutorName, ce6.name as directorName, e.TeachStartDate, "
						+ "e.Remark FROM ((((((empl e  "
						+ "LEFT OUTER JOIN CodeEmpl ce1 ON ce1.idno=e.category AND ce1.category='EmpCategory') "
						+ "LEFT OUTER JOIN CodeEmpl ce2 ON ce2.idno=e.StatusCause AND ce2.category='StatusCause') "
						+ "LEFT OUTER JOIN CodeEmpl ce3 ON ce3.idno=e.Degree AND ce3.category='Degree') "
						+ "LEFT OUTER JOIN CodeEmpl ce4 ON ce4.idno=e.Status AND ce4.category='EmpStatus') "
						+ "LEFT OUTER JOIN CodeEmpl ce5 ON ce5.idno=e.Tutor AND ce5.category='Tutor') "
						+ "LEFT OUTER JOIN CodeEmpl ce6 ON ce6.idno=e.Director AND ce6.category='EmplRoleDirector') "
						+ "WHERE e.idno IN(");
		for (int i = 0; i < list.size(); i++) {
			sb.append("'" + ((Map) list.get(i)).get("idno") + "', ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append(")ORDER BY e.unit");

		List all = manager.ezGetBy(sb.toString());

		sb = new StringBuilder(
				"SELECT e.pcode, e.idno, e.cname, e.insno, e.ename, e.sex, e.bdate, e.unit, "
						+ "ce1.name as categoryName, e.sname, e.telephone, e.pzip, e.paddr, e.czip, e.caddr, e.StartDate, "
						+ "e.EndDate, ce2.name as StatusCauseName, ce3.name as degreeName, ce4.name as StatusName, "
						+ "e.CellPhone, e.Email, ce5.name as TutorName, ce6.name as directorName, e.TeachStartDate, "
						+ "e.Remark FROM ((((((dempl e  "
						+ "LEFT OUTER JOIN CodeEmpl ce1 ON ce1.idno=e.category AND ce1.category='EmpCategory') "
						+ "LEFT OUTER JOIN CodeEmpl ce2 ON ce2.idno=e.StatusCause AND ce2.category='StatusCause') "
						+ "LEFT OUTER JOIN CodeEmpl ce3 ON ce3.idno=e.Degree AND ce3.category='Degree') "
						+ "LEFT OUTER JOIN CodeEmpl ce4 ON ce4.idno=e.Status AND ce4.category='EmpStatus') "
						+ "LEFT OUTER JOIN CodeEmpl ce5 ON ce5.idno=e.Tutor AND ce5.category='Tutor') "
						+ "LEFT OUTER JOIN CodeEmpl ce6 ON ce6.idno=e.Director AND ce6.category='EmplRoleDirector') "
						+ "WHERE e.idno IN(");

		for (int i = 0; i < list.size(); i++) {
			sb.append("'" + ((Map) list.get(i)).get("idno") + "', ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append(")");

		all.addAll(manager.ezGetBy(sb.toString()));

		PrintWriter out = response.getWriter();
		out.println("<table width='100%' border='1'>  ");
		out.println("<tr bgcolor='#f0fcd7'>");

		out
				.println("<td align='center' style='mso-number-format:@' nowrap>身分證號</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>中文姓名</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>保險證號</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>英文姓名</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>性別</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>生日</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>單位</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>職稱1</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>職稱2</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>本校職稱</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>電話</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>戶籍區號</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>戶籍地址</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>現居區號</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>現居地址</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>任職日期</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>離職日期</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>離職原因</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>學歷</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>狀態</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>行動電話</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>電子郵件</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>兼任導師</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>兼任主管</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>任教日期</td>");
		out
				.println("<td align='center' style='mso-number-format:@' nowrap>備註</td>");

		for (int i = 0; i < all.size(); i++) {
			if (i % 2 == 1) {
				out.println("  <tr bgcolor='#f0fcd7'>");
			} else {
				out.println("  <tr>");
			}

			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("idno") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("cname") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("insno") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("ename") + "</td>");

			if (((Map) all.get(i)).get("sex").equals("1")) {
				out
						.println("<td align='center' style='mso-number-format:@' nowrap>男</td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:@' nowrap>女</td>");
			}

			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("bdate") + "</td>");

			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ manager
									.ezGetString(""
											+ "SELECT name FROM CodeEmpl WHERE idno='"
											+ ((Map) all.get(i)).get("unit")
											+ "' AND "
											+ "(category='Unit' OR category='UnitTeach')")
							+ "</td>");

			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("categoryName") + "</td>");

			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ manager
									.ezGetString(""
											+ "SELECT name FROM CodeEmpl WHERE idno='"
											+ ((Map) all.get(i)).get("pcode")
											+ "' AND "
											+ "(category='TeacherRole' OR category='EmplRoleStaff')")
							+ "</td>");

			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("sname") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("telephone") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("pzip") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("paddr") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("czip") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("caddr") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("StartDate") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("EndDate") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("StatusCauseName")
							+ "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("degreeName") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("StatusName") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("CellPhone") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("Email") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("TutorName") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("DirectorName") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("TeachStartDate")
							+ "</td>");
			out
					.println("<td align='center' style='mso-number-format:@' nowrap>"
							+ ((Map) all.get(i)).get("Remark") + "</td>");

			out.println("	</tr>");
		}

		out.println("</table>");

		out.close();

	}

}
