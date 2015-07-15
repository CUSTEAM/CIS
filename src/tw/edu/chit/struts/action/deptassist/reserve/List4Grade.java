package tw.edu.chit.struts.action.deptassist.reserve;

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

public class List4Grade extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String year = request.getParameter("year");
		String dept = request.getParameter("dept");
		CourseManager manager = (CourseManager) getBean("courseManager");
		List list = manager
				.ezGetBy("SELECT d.*, c.chi_name FROM Dtime_reserve d, Csno c WHERE d.cscode=c.cscode AND d.year='"
						+ year
						+ "' AND d.depart_class='"
						+ dept
						+ "' ORDER BY d.grade, d.year, d.term, d.opt");

		response.setContentType("application/vnd.ms-excel;charset=big5");

		PrintWriter out = response.getWriter();
		response.setHeader("Content-disposition",
				"attachment;filename=CountClasses.xls");

		out
				.println("<html><head></head><body><table width='100%' border='1'>  ");

		out.println("<tr>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>年級</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>學期</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>選別</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>課程代碼</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>課程名稱</td>");

		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>學分</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>時數</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>電腦實習</td>");
		out
				.println("<td align='center' style='mso-number-format:\\@' nowrap>類型</td>");
		out.println("	</tr>");

		for (int i = 0; i < list.size(); i++) {

			out.println("<tr>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) list.get(i)).get("grade") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) list.get(i)).get("term") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) list.get(i)).get("opt") + "</td>");
			out
					.println("<td align='center' style='mso-number-format:\\@' nowrap>"
							+ ((Map) list.get(i)).get("cscode") + "</td>");
			out
					.println("<td align='left' style='mso-number-format:\\@' nowrap>"
							+ ((Map) list.get(i)).get("chi_name") + "</td>");

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
							+ ((Map) list.get(i)).get("additionType") + "</td>");
			out.println("	</tr>");

		}

		out.println("</table></body></html>");
		out.close();
		return null;
	}

}
