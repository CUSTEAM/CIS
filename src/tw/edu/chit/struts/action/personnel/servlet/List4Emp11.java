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

public class List4Emp11 extends HttpServlet {
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
				"SELECT e.sname, e.category, e.Adate, e.pcode, e.idno, e.cname, e.insno, e.ename, e.sex, e.bdate, e.unit, "
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
		sb.append(")");
		System.out.println(sb);
		int schoolYear = manager.getSchoolYear();
		int schoolTerm = manager.getSchoolTerm();

		List all = manager.ezGetBy(sb.toString());

		sb = new StringBuilder(
				"SELECT e.sname, e.category, e.Adate, e.pcode, e.idno, e.cname, e.insno, e.ename, e.sex, e.bdate, e.unit, "
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
				.println("<td align='center' style='mso-number-format:\\@' nowrap>學年度/學期</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>主聘系所</td>");// 問題
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>身份識別種類</td>");// 問題
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>身份識別號</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>國籍</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>中文姓名</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>英文姓名</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>性別</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>出生日期</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>共聘系所</td>");

		// 10個好累
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>電子郵件</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>狀態</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>聘任日期</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>原任單位</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>新任單位</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>停職日期</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>復職日期</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>專兼任</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>專(兼)職業別</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>專(兼)職單位</td>");

		// 30...
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>專(兼)職職務</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>借調否</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>借調目的(來原)</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>借調新(原)單位</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>兼任行政工作</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>行政工作職務</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>最早到校任職日</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>印領清冊頁碼</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>學校</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>科系</td>");

		// 6...
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>學位</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>教師分類</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>職級</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>審定情形</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>聘書字號</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>證書(證照)(公文)字號</td>");

		for (int i = 0; i < all.size(); i++) {
			if (i % 2 == 1) {
				out.println("  <tr bgcolor='#f0fcd7'>");
			} else {
				out.println("  <tr>");
			}

			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ schoolYear + "/" + schoolTerm + "</td>");// 學年度/學期

			out
					.println("<td align='left' style='mso-number-format:\\@' nowrap>"
							+ manager
									.ezGetString(""
											+ // 主聘系所
											"SELECT name FROM CodeEmpl WHERE idno='"
											+ ((Map) all.get(i)).get("unit")
											+ "' AND "
											+ "(category='Unit' OR category='UnitTeach')")
							+ "</td>");

			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ "                  " + "</td>");// 身份識別種類
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) all.get(i)).get("idno") + "</td>");// 身份識別號
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ "                  " + "</td>");// 國籍
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) all.get(i)).get("cname") + "</td>");// 中文姓名
			out
					.println("<td align='left' style='mso-number-format:\\@' nowrap>"
							+ ((Map) all.get(i)).get("ename") + "</td>");// 英文姓名
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) all.get(i)).get("sex") + "</td>");// 性別
			out
					.println("<td align='left' style='mso-number-format:\\@' nowrap>"
							+ ((Map) all.get(i)).get("bdate") + "</td>");// 出生日期
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ "                  " + "</td>");// 共聘系所

			// 10個好累
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) all.get(i)).get("Email") + "</td>");// 電子郵件
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");// 狀態

			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) all.get(i)).get("Adate") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");// 原任單位
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");// 新任單位
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");// 停職日期
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");// 復職日期
			if (((Map) all.get(i)).get("category").equals("1")) {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>專</td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>兼</td>");
			}

			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");// 專(兼)職業別
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");// 專(兼)職單位

			// 20...
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");// 專(兼)職職務
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");// 借調否
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");// 借調目的(來原)
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");// 借調新(原)單位
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) all.get(i)).get("Director") + "</td>");// 兼任行政工作
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) all.get(i)).get("sname") + "</td>");// 行政工作職務
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) all.get(i)).get("Adate") + "</td>");// 最早到校任職日
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");// 印領清冊頁碼

			Map map = manager
					.ezGetMap("SELECT * FROM Empl_grdschl WHERE idno='"
							+ ((Map) all.get(i)).get("idno") + "' limit 1");
			if (map != null) {
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ map.get("school_name") + "</td>");
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ map.get("dept_name") + "</td>");

				// 6...
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ map.get("no") + "</td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");

				// 6...
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
			}

			map = manager.ezGetMap("SELECT * FROM Empl_contract WHERE idno='"
					+ ((Map) all.get(i)).get("idno") + "' limit 1");
			if (map != null) {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ map.get("type") + "</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ map.get("level") + "</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ map.get("markup") + "</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ map.get("contract_no") + "</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ map.get("license_no") + "</td>");
			} else {
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
			}

			out.println("	</tr>");
		}

		out.println("</table>");
		out.close();

	}

}
