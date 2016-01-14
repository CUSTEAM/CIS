package tw.edu.chit.struts.action.report.teacher;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Dtime;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

public class SpoRat extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		CourseManager manager = (CourseManager) getBean("courseManager");		
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=SpoRat.xls");
		
		
		
		
		
		String dtimeOid = request.getParameter("dtimeOid");

		List list = manager.getSeld4SportBy(dtimeOid);

		PrintWriter out = response.getWriter();
		out.println("<html>");

		Map map = manager.getdtimeBy(dtimeOid);		
		
		
		out.println("<table width='100%'>  ");
		out.println("  <tr>");
		out.println("		<td align='center'>開課班級: </td>");
		out.println("		<td align='left' colspan='6'>" + map.get("ClassName")
				+ "</td>");
		out.println("  </tr>");
		out.println("  <tr>");
		out.println("		<td align='center'>課程名稱: </td>");
		out.println("		<td align='left' colspan='6'>" + map.get("chi_name")
				+ "</td>");
		out.println("  </tr>");
		out.println("  <tr>");
		out.println("		<td align='center'>任課教師: </td>");
		out.println("		<td align='left' colspan='6'>" + map.get("cname")
				+ " 簽章:</td>");
		out.println("  </tr>");
		out.println("  <tr>");
		out.println("		<td align='center'>&nbsp;</td>");

		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		out.println("		<td align='right' colspan='6'>"
				+  sf.format(new Date()) + "</td>");
		out.println("  </tr>");
		out.println("</table>");

		out.println("<table width='100%' border='1'>  ");
		out.println("  <tr>");
		out.println("		<td align='center'>班級</td>");
		out
				.println("		<td align='center'>&nbsp;&nbsp;&nbsp;&nbsp;學號&nbsp;&nbsp;&nbsp;&nbsp;</td>");
		out.println("		<td align='center'>姓名</td>");
		out.println("		<td align='center'>術科成績(50%)</td>");
		out.println("		<td align='center'>平時成績(40%)</td>");
		out.println("		<td align='center'>學科成績(10%)</td>");
		out.println("		<td align='center'>學期成績(100%)</td>");
		out.println("	</tr>");

		for (int i = 0; i < list.size(); i++) {

			if (i % 2 == 1) {
				out.println("  <tr>");
			} else {
				out.println("  <tr bgcolor='#f0fcd7'>");
			}
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("ClassName") + "</td>");
			out.println("		<td align='left' style='mso-number-format:\\@'>"
					+ ((Map) list.get(i)).get("student_no") + "</td>");
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("student_name") + "</td>");

			if (((Map) list.get(i)).get("score1") == null) {
				out.println("		<td align='center'></td>");
			} else {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score1") + "</td>");
			}

			if (((Map) list.get(i)).get("score2") == null) {
				out.println("		<td align='center'></td>");
			} else {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score2") + "</td>");
			}

			if (((Map) list.get(i)).get("score3") == null) {
				out.println("		<td align='center'></td>");
			} else {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score3") + "</td>");
			}

			if (((Map) list.get(i)).get("score") == null) {
				out.println("		<td align='center'></td>");
			} else {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score") + "</td>");
			}

			out.println("	</tr>");

		}
		out.println("</table>");
		out.println("</html>");
		out.close();
		return null;
	}
	
}