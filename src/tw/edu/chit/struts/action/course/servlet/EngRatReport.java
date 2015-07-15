package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

/**
 * 英文成績冊
 * 
 * @author JOHN
 * 
 */
public class EngRatReport extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=list4SportRat.xls");

		String dtimeOid = request.getParameter("dtimeOid");

		// boolean b=false;
		// if(manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE
		// Dtime_oid='"+dtimeOid+"' AND (score11<>'' OR score11<>null)")>0){
		// b=true;
		// }

		PrintWriter out = response.getWriter();
		out.println("<html>");

		Map map = manager.getdtimeBy(dtimeOid);

		out.println("<style>");
		out.println("<!--table");
		out.println("@page");
		out.println("	{margin:.98in .2in .98in .2in;");
		out.println("	mso-header-margin:.51in;");
		out.println("	mso-footer-margin:.51in;}");
		out.println("-->");
		out.println("</style>");

		String x = "14";
		// if(b){
		// x="17";
		// }

		List list = manager
				.ezGetBy("SELECT cl.ClassName, st.student_no, st.student_name, "
						+ "s.* "
						+ "FROM Seld s, stmd st, Class cl WHERE s.student_no=st.student_no AND "
						+ "cl.ClassNo=st.depart_class AND s.Dtime_oid='"
						+ dtimeOid + "' ORDER BY st.student_no");

		int pass = 0;
		int page = 1;
		float total = 0;
		for (int i = 0; i < list.size(); i++) {

			if (i % 39 == 0) {

				out.println("<table border=1>  ");
				out.println("  <tr>");
				out.println("		<td align='center'>開課班級: </td>");
				out.println("		<td align='left' colspan='" + x + "'>"
						+ map.get("ClassName") + "</td>");
				out.println("  </tr>");
				out.println("  <tr>");
				out.println("		<td align='center'>課程名稱: </td>");
				out.println("		<td align='left' colspan='" + x + "'>"
						+ map.get("chi_name") + "</td>");
				out.println("  </tr>");
				out.println("  <tr>");
				out.println("		<td align='center'>任課教師: </td>");
				out.println("		<td align='left' colspan='" + x + "'>"
						+ map.get("cname") + " 簽章:</td>");
				out.println("  </tr>");
				out.println("  <tr>");
				out.println("		<td align='center'>&nbsp;</td>");
				SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				out.println("		<td align='right' colspan='" + x + "'>列印日期 "
						+ sf.format(new Date()) + ", 第" + page + "頁*</td>");
				out.println("  </tr>");
				// out.println("</table>");
				page = page + 1;
				// out.println("<table width='100%' border='1'> ");
				out.println("  <tr>");

				out.println("		<td align='center'>學號</td>");
				out.println("		<td align='center'>姓名</td>");

				// if(b){
				// out.println(" <td align='center' colspan='4'>線上成績</td>");
				// out.println(" <td align='center'>線上</td>");
				// out.println(" <td align='center'>線上</td>");
				// out.println(" <td align='center'>線上</td>");
				// out.println(" <td align='center'>線上</td>");
				// }

				// out.println(" <td align='center' colspan='6'>平時</td>");
				out.println("		<td align='center'>平時</td>");
				out.println("		<td align='center'>平時</td>");
				out.println("		<td align='center'>平時</td>");
				out.println("		<td align='center'>平時</td>");
				out.println("		<td align='center'>平時</td>");
				out.println("		<td align='center'>平時</td>");

				out.println("		<td align='center'>平時成績</td>");
				out.println("		<td align='center'>補救教學</td>");
				out.println("		<td align='center'>期中考</td>");
				out.println("		<td align='center'>英檢成績</td>");
				out.println("		<td align='center'>期末考</td>");
				out.println("		<td align='center'>活動</td>");

				out.println("		<td align='center'>總成績</td>");
				out.println("	</tr>");

			}

			if (i % 2 == 1) {
				out.println("  <tr>");
			} else {
				out.println("  <tr bgcolor='#f0fcd7'>");
			}
			// out.println(" <td
			// align='center'>"+((Map)list.get(i)).get("ClassName")+"</td>");

			out.println("		<td align='left' style='mso-number-format:\\@'>"
					+ ((Map) list.get(i)).get("student_no") + "</td>");
			out.println("		<td align='center'>"
					+ ((Map) list.get(i)).get("student_name") + "</td>");

			/*
			 * if(b){ float score11=0; float score12=0; float score13=0; float
			 * score14=0;
			 * 
			 * if(((Map)list.get(i)).get("score11")!=null){
			 * score11=(Float.parseFloat(((Map)list.get(i)).get("score11").toString())/240)*100; }
			 * if(((Map)list.get(i)).get("score12")!=null){
			 * score12=(Float.parseFloat(((Map)list.get(i)).get("score12").toString())/240)*100; }
			 * if(((Map)list.get(i)).get("score13")!=null){
			 * score13=(Float.parseFloat(((Map)list.get(i)).get("score13").toString())/240)*100; }
			 * if(((Map)list.get(i)).get("score14")!=null){
			 * score14=(Float.parseFloat(((Map)list.get(i)).get("score14").toString())/240)*100; }
			 * try{ //out.println(" <td align='center'>"+(int)(score11+score12+score13+score14)/4+"</td>");
			 * out.println(" <td align='center'>"+(int)score11+"</td>");
			 * out.println(" <td align='center'>"+(int)score12+"</td>");
			 * out.println(" <td align='center'>"+(int)score13+"</td>");
			 * out.println(" <td align='center'>"+(int)score14+"</td>");
			 * }catch(Exception e){ out.println(" <td align='center'>-</td>");
			 * out.println(" <td align='center'>-</td>"); out.println("
			 * <td align='center'>-</td>"); out.println(" <td align='center'>-</td>"); } }
			 */

			// 平時6次
			if (((Map) list.get(i)).get("score01") != null
					&& !((Map) list.get(i)).get("score01").equals("")) {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score01") + "</td>");
			} else {
				out.println("		<td align='center'>-</td>");
			}
			if (((Map) list.get(i)).get("score02") != null
					&& !((Map) list.get(i)).get("score02").equals("")) {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score02") + "</td>");
			} else {
				out.println("		<td align='center'>-</td>");
			}
			if (((Map) list.get(i)).get("score03") != null
					&& !((Map) list.get(i)).get("score03").equals("")) {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score03") + "</td>");
			} else {
				out.println("		<td align='center'>-</td>");
			}
			if (((Map) list.get(i)).get("score04") != null
					&& !((Map) list.get(i)).get("score04").equals("")) {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score04") + "</td>");
			} else {
				out.println("		<td align='center'-></td>");
			}
			if (((Map) list.get(i)).get("score05") != null
					&& !((Map) list.get(i)).get("score05").equals("")) {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score05") + "</td>");
			} else {
				out.println("		<td align='center'>-</td>");
			}
			if (((Map) list.get(i)).get("score06") != null
					&& !((Map) list.get(i)).get("score06").equals("")) {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score06") + "</td>");
			} else {
				out.println("		<td align='center'>-</td>");
			}

			if (((Map) list.get(i)).get("score1") != null) {
				out.println("		<td align='right'>"
						+ ((Map) list.get(i)).get("score1") + "</td>");
			} else {
				out.println("		<td align='center'></td>");
			}

			if (((Map) list.get(i)).get("score08") != null) {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score08") + "</td>");
			} else {
				out.println("		<td align='center'>-</td>");
			}

			if (((Map) list.get(i)).get("score2") != null) {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score2") + "</td>");
			} else {
				out.println("		<td align='center'>-</td>");
			}

			if (((Map) list.get(i)).get("score1") != null) {
				out.println("		<td align='right'>"
						+ ((Map) list.get(i)).get("score16") + "</td>");
			} else {
				out.println("		<td align='center'>-</td>");
			}

			if (((Map) list.get(i)).get("score3") != null) {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score3") + "</td>");
			} else {
				out.println("		<td align='center'>-</td>");
			}

			if (((Map) list.get(i)).get("score09") != null) {
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("score09") + "</td>");
			} else {
				out.println("		<td align='center'>-</td>");
			}

			if (((Map) list.get(i)).get("score") != null) {
				try {

					total = total
							+ Float.parseFloat(((Map) list.get(i)).get("score")
									.toString());
					if (Float.parseFloat(((Map) list.get(i)).get("score")
							.toString()) >= 60) {
						pass = pass + 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				float f = Float.parseFloat(((Map) list.get(i)).get("score")
						.toString());
				out.println("		<td align='right'>" + manager.roundOff(f, 0)
						+ "</td>");
			} else {
				out.println("		<td align='center'>-</td>");
			}

			out.println("	</tr>");

		}

		out.println("</table>");
		out.println("		<td align='center'>全班人數:" + list.size() + ", 不及格人數:"
				+ (list.size() - pass) + ", 平均成績:" + (total / list.size())
				+ "</td>");
		out.println("</html>");
		out.close();
	}

}
