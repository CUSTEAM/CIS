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

/**
 * 圓餅的
 * 
 * @author JOHN
 * 
 */
public class OnlineServiceChartColumn extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String unit = (String) session.getAttribute("target");
		String sdate = (String) session.getAttribute("sdate");
		String edate = (String) session.getAttribute("edate");

		StringBuilder sb = new StringBuilder();
		if (!sdate.equals("")) {
			sb.append(" AND stime>'" + sdate + "'");
		}
		if (!edate.equals("")) {
			sb.append(" AND stime<'" + edate + "'");
		}

		List list = manager
				.ezGetBy("SELECT o.Oid, o.service_name, COUNT(o.Oid) as total FROM OnlineServices o, "
						+ "OnlineServiceDoc ocd WHERE o.Oid=ocd.service_oid AND o.unit='"
						+ unit + "'" + sb.toString() + " GROUP BY o.Oid");

		response.setContentType("text/xml; charset=UTF-8");

		out.println("");
		out.println("<chart>");
		out.println("");
		out.println("	<chart_data>");
		out.println("		<row>");
		out.println("			<null/>");

		for (int i = 0; i < list.size(); i++) {
			out
					.println("			<string>"
							+ ((Map) list.get(i)).get("service_name")
							+ " 共"
							+ manager
									.ezGetInt("SELECT COUNT(*)FROM OnlineServiceDoc WHERE service_oid='"
											+ ((Map) list.get(i)).get("Oid")
											+ "'") + "件</string>");

		}

		out.println("		</row>");
		out.println("		<row>");
		out.println("			<string></string>");

		for (int i = 0; i < list.size(); i++) {
			out
					.println("			<number>"
							+ manager
									.ezGetInt("SELECT COUNT(*)FROM OnlineServiceDoc osd, OnlineServices o WHERE "
											+ "o.Oid=osd.service_oid AND o.Oid='"
											+ ((Map) list.get(i)).get("Oid")
											+ "'") + "</number>");
		}
		out.println("		</row>");
		out.println("	</chart_data>");
		out
				.println("	<chart_label as_percentage='1' size='9' color='ffffff' alpha='90' />");
		out.println("	<chart_pref rotation_x='55' drag='false' />");// 角度
		out
				.println("	<chart_rect x='95' y='120' width='180' height='180' positive_alpha='0' />");
		out
				.println("	<chart_transition type='dissolve' delay='0.1' duration='0.3' order='category' />");
		out.println("	<chart_type>3d pie</chart_type>");
		out.println("");
		out.println("	<filter>");
		out
				.println("		<shadow id='shadow1' distance='1' angle='45' color='0' alpha='35' blurX='4' blurY='4' />");
		out.println("	</filter>");
		out.println("	");
		out
				.println("	<legend shadow='shadow1' x='-50' y='150' width='20' height='40' fill_alpha='0' layout='vertical' size='12' font='新細明體' bold='false' color='454545' alpha='90' />");
		out.println("	");
		out.println("	<series_color>");

		out.println("		<color>cc6600</color>");
		out.println("		<color>cccc44</color>");
		out.println("		<color>7700ee</color>");
		out.println("		<color>666666</color>");
		out.println("		<color>4488cc</color>");
		out.println("		<color>dddddd</color>");

		out.println("	</series_color>");
		out.println("	<series_explode>");

		out.println("		<number>0</number>");
		out.println("		<number>5</number>");
		out.println("		<number>0</number>");
		out.println("		<number>10</number>");
		out.println("		<number>0</number>");
		out.println("		<number>15</number>");

		out.println("	</series_explode>");
		out.println("</chart>");
		out.println("");

		out.flush();
		out.close();
	}
}
