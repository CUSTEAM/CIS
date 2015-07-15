package tw.edu.chit.struts.action.deptassist.reserve.report;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

public class CheckList extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");

		String term = manager.getSchoolTerm().toString();
		String type = request.getParameter("type");

		String orderby = "d.techid";
		// if(type.equals("t")){
		// orderby="";
		// }
		if (type.equals("c")) {
			orderby = "d.depart_class";
		}

		int year;
		int year1;
		if (term.equals("2")) {
			year = manager.getSchoolYear() + 1;
			term = "1";
		} else {
			year = manager.getSchoolYear();
			term = "2";
		}
		year1 = year - 4;
		List list = manager
				.ezGetBy("SELECT cl.ClassName, e.cname, d.*, c.chi_name FROM Class cl, "
						+ "Dtime_reserve_ready d LEFT OUTER JOIN empl e ON e.idno=d.techid, Csno c "
						+ "WHERE cl.ClassNo=d.depart_class AND d.cscode=c.cscode AND (d.year<='"
						+ year
						+ "' AND d.year>='"
						+ year1
						+ "') AND term='"
						+ term + "'ORDER BY " + orderby + ", d.year, d.opt");

		response.setContentType("application/vnd.ms-excel;charset=big5");
		PrintWriter out = response.getWriter();
		response.setHeader("Content-disposition",
				"attachment;filename=CountClasses.xls");
		out
				.println("<html><head></head><body><table width='100%' border='1'>  ");
		out.println("<tr>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>選別</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>班級</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>課程代碼</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>課程名稱</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>任課教師</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>學分</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>時數</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>電腦實習</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>類型</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>時間地點</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>多教師</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>選課規則</td>");
		out.println("	</tr>");
		List place;
		List teachers;
		List rules;
		StringBuilder sb;
		for (int i = 0; i < list.size(); i++) {

			try {
				out.println("<tr>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("opt") + "</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("ClassName")
								+ "</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("cscode") + "</td>");
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("chi_name") + "</td>");
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("cname") + "</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("credit") + "</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("thour") + "</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("cyber") + "</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ ((Map) list.get(i)).get("additionType")
								+ "</td>");

				place = manager
						.ezGetBy("SELECT * FROM Dtime_reserve_class WHERE Dtime_reserve_ready_oid="
								+ ((Map) list.get(i)).get("Oid"));
				sb = new StringBuilder();
				for (int j = 0; j < place.size(); j++) {
					sb.append("星期" + ((Map) place.get(j)).get("week") + "第"
							+ ((Map) place.get(j)).get("begin") + "~"
							+ ((Map) place.get(j)).get("end") + "節"
							+ ((Map) place.get(j)).get("place") + ", ");
				}
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ sb + "</td>");

				teachers = manager
						.ezGetBy("SELECT cname FROM Dtime_reserve_teacher d, empl e WHERE d.teach_id=e.idno AND d.Dtime_reserve_oid="
								+ ((Map) list.get(i)).get("Oid"));
				sb = new StringBuilder();
				for (int j = 0; j < teachers.size(); j++) {
					sb.append(((Map) teachers.get(j)).get("cname") + ", ");
				}
				out
						.println("<td align='left' style='mso-number-format:\\@' nowrap>"
								+ sb + "</td>");

				rules = manager
						.ezGetBy("SELECT * FROM Dtime_reserve_opencs WHERE Dtime_oid="
								+ ((Map) list.get(i)).get("Oid"));
				sb = new StringBuilder();
				for (int j = 0; j < rules.size(); j++) {
					sb.append("" + ((Map) rules.get(j)).get("Cidno")
							+ ((Map) rules.get(j)).get("Sidno")
							+ ((Map) rules.get(j)).get("Didno")
							+ ((Map) rules.get(j)).get("Grade") + ", ");
				}
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>"
								+ sb + "</td>");
				out.println("</tr>");

			} catch (Exception e) {
				out.println("<tr>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>資</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>料</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>出</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>現</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>異</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>常</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>請</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>檢</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>查</td>");
				out
						.println("<td align='center' style='mso-number-format:\\@' nowrap>！</td>");
				out.println("	</tr>");
			}
		}
		out.println("</table></body></html>");
		out.close();
		return null;
	}

}
