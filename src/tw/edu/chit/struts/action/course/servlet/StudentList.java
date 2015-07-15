package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
 * 名條
 * 
 * @author JOHN
 * 
 */
public class StudentList extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=Test.xls");

		HttpSession session = request.getSession(false);
		List list = (List) session.getAttribute("students");
		String tmp;

		StringBuilder sb = new StringBuilder(
				"SELECT s.depart_class, c.ClassNo, c.DeptNo, c5.name FROM stmd s, Class c, code5 c5 WHERE "
						+ "c5.category='Dept' AND c5.idno=c.DeptNo AND c.ClassNo=s.depart_class AND student_no IN(");

		for (int i = 0; i < list.size(); i++) {
			sb.append("'" + ((Map) list.get(i)).get("student_no") + "', ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append(")GROUP BY c.ClassNo");
		List depts = manager.ezGetBy(sb.toString());

		/*
		 * sb=new StringBuilder("SELECT s.depart_class, c.DeptNo, c5.name FROM
		 * stmd s, Class c, code5 c5 WHERE " + "c5.category='Dept' AND
		 * c5.idno=c.DeptNo AND c.ClassNo=s.depart_class AND student_no IN(");
		 * 
		 * for(int i=0; i<list.size(); i++){
		 * sb.append("'"+((Map)list.get(i)).get("student_no")+"', "); }
		 * sb.delete(sb.length()-2, sb.length()); sb.append(")GROUP BY
		 * c.ClassNo");
		 */
		// List countClass=manager.ezGetBy(sb.toString());
		// int tmp=countClass.size()*70;
		List students;
		List classes;
		// List students;

		PrintWriter out = response.getWriter();
		// out.println ("<html
		// xmlns:o='urn:schemas-microsoft-com:office:office'");
		// out.println ("xmlns:x='urn:schemas-microsoft-com:office:excel'");
		// out.println ("xmlns='http://www.w3.org/TR/REC-html40'>");

		out.println("<html xmlns:x='urn:schemas-microsoft-com:office:excel'");
		out.println("<head>");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		out.println("<style>");
		out.println("<!--");
		out.println("mso-displayed-thousand-separator:'\\,';}");
		out.println("@page{mso-header-data:'" + manager.getSchoolYear() + "學年第"
				+ manager.getSchoolTerm() + "學期班級名條';");
		// out.println ("@page{mso-header-data:'';");
		// out.println
		// ("mso-footer-data:'"+manager.getSchoolYear()+"學年第"+manager.getSchoolTerm()+"+學期班級名條+"+sf.format(new
		// Date())+"';");
		out.println("mso-footer-data:'';");
		out.println("margin: .5in .5in .0in .5in;");
		out.println("mso-header-margin:.25in;");
		out.println("mso-footer-margin:.25in;");
		out.println("mso-page-orientation:portrait;}");

		out.println("-->");
		out.println("</style>");

		out.println("<!--[if gte mso 9]><xml>");
		out.println(" <x:ExcelWorkbook>");
		out.println("  <x:ExcelWorksheets>");
		out.println("   <x:ExcelWorksheet>");
		out.println("    <x:Name>圈圈叉叉</x:Name>");
		out.println("    <x:WorksheetOptions>");

		out.println("<x:DefaultRowHeight>320</x:DefaultRowHeight>");
		out.println("     <x:Print>");
		out.println("      <x:ValidPrinterInfo/>");
		out.println("      <x:PaperSizeIndex>9</x:PaperSizeIndex>");
		out
				.println("      <x:HorizontalResolution>600</x:HorizontalResolution>");
		out.println("      <x:VerticalResolution>600</x:VerticalResolution>");
		out.println("      <x:Gridlines/>");
		out.println("     </x:Print>");

		out.println("     <x:Selected/>");
		out.println("     <x:Panes>");
		out.println("      <x:Pane>");
		out.println("       <x:Number>3</x:Number>");
		out.println("       <x:RangeSelection>A1:C1</x:RangeSelection>");
		out.println("      </x:Pane>");
		out.println("     </x:Panes>");
		out.println("     <x:ProtectContents>False</x:ProtectContents>");
		out.println("     <x:ProtectObjects>False</x:ProtectObjects>");
		out.println("     <x:ProtectScenarios>False</x:ProtectScenarios>");
		out.println("    </x:WorksheetOptions>");

		out.println("    <x:PageBreaks>");
		out.println("     <x:RowBreaks>");

		for (int i = 0; i < depts.size() * 68; i++) {
			if (i % 69 == 0)
				out.println("      <x:RowBreak>");
			out.println("       <x:Row>" + i + "</x:Row>");
			out.println("      </x:RowBreak>");
		}

		out.println("     </x:RowBreaks>");
		out.println("    </x:PageBreaks>");

		out.println("   </x:ExcelWorksheet>");
		out.println("  </x:ExcelWorksheets>");
		out.println("  <x:ProtectStructure>False</x:ProtectStructure>");
		out.println("  <x:ProtectWindows>False</x:ProtectWindows>");
		out.println(" </x:ExcelWorkbook>");
		out.println("</xml><![endif]-->");
		out.println("</head>");

		out.println("<body link=blue vlink=purple>");

		out.println("<table>");
		for (int i = 0; i < depts.size(); i++) {
			sb = new StringBuilder(
					"SELECT ClassNo, ClassName FROM Class WHERE ClassNo='"
							+ ((Map) depts.get(i)).get("ClassNo") + "'");
			classes = manager.ezGetBy(sb.toString());

			if (i % 4 == 0) {

				out.println("<tr>");
				// out.println ("<td colspan='5'>什麼");
				// out.println ("</td>");
				// out.println ("</tr>");
			}

			for (int j = 0; j < classes.size(); j++) {

				students = manager
						.ezGetBy("SELECT student_no, student_name, sex FROM stmd WHERE depart_class='"
								+ ((Map) classes.get(j)).get("ClassNo") + "' ORDER BY student_no");

				out.println("<td style='font-size:8pt;'>");

				out.println("<table>");
				out
						.println("<tr style='border-bottom: solid #000000; height:16pt'>");
				out
						.println("<td colspan='3' style='font-size:10pt;' valign='bottom'>"
								+ ((Map) classes.get(j)).get("ClassName")
								+ "女生"
								+ manager
										.ezGetInt("SELECT COUNT(*)FROM stmd WHERE depart_class='"
												+ ((Map) classes.get(j))
														.get("ClassNo")
												+ "' AND sex='2'") + "人");
				out.println("</td>");
				out.println("</tr>");

				for (int k = 0; k < students.size(); k++) {

					if (k % 5 == 0) {
						tmp = "<td style='BORDER-top: black solid; mso-number-format:\\@; font-size:10pt;'>";
					} else {
						tmp = "<td style='mso-number-format:\\@; font-size:10pt;'>";
					}

					out.println("<tr style='height:12pt'>");

					out
							.println(tmp
									+ ((Map) students.get(k)).get("student_no"));
					out.println("</td>");

					if (((Map) students.get(k)).get("sex").equals("2")) {
						out.println(tmp
								+ ((Map) students.get(k)).get("student_name")
								+ "*");
					} else {
						out.println(tmp
								+ ((Map) students.get(k)).get("student_name"));
					}

					out.println("</td>");
					out.println(tmp
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.println("</td>");
					out.println("</tr>");

				}

				if (students.size() < 67) {
					for (int k = 68; k > students.size(); k--) {
						out.println("<tr style='height:12pt'>");
						out.println("<td style='font-size:8.0pt;'>");
						out.println("</td>");
						out.println("<td style='font-size:8.0pt;'>");
						out.println("</td>");

						out
								.println("<td style='font-size:8.0pt; width:10pt;'>");
						out.println("</td>");

						out.println("</tr>");
					}
				}

				out.println("</table>");
				out.println("</td>");

			}

		}

		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
}
