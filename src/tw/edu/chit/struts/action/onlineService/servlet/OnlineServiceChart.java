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
 * 油條的
 * 
 * @author JOHN
 * 
 */
public class OnlineServiceChart extends HttpServlet {
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

		out.println("<chart>");
		out
				.println("	<axis_category size='12' shadow='shadow1' font='新細明體' bold='false' color='454545'/>");
		out.println("	<axis_ticks value_ticks='' category_ticks=''/>");
		out.println("	<axis_value alpha='0'/>");
		out.println("	");
		out
				.println("	<chart_border bottom_thickness='0' left_thickness='0' />");
		out.println("	<chart_data>");
		out.println("		<row>");
		out.println("			<null/>");

		List list = manager
				.ezGetBy("SELECT o.Oid, o.service_name, COUNT(o.Oid) as total FROM OnlineServices o, "
						+ "OnlineServiceDoc ocd WHERE o.Oid=ocd.service_oid AND o.unit='"
						+ unit + "'" + sb.toString() + " GROUP BY o.Oid");

		for (int i = 0; i < list.size(); i++) {
			((Map) list.get(i)).put("on", manager
					.ezGetInt("SELECT COUNT(*)FROM OnlineServiceDoc osd WHERE "
							+ "osd.service_oid='"
							+ ((Map) list.get(i)).get("Oid")
							+ "' AND osd.status<>'o'" + sb.toString()));
			((Map) list.get(i)).put("off", manager
					.ezGetInt("SELECT COUNT(*)FROM OnlineServiceDoc osd WHERE "
							+ "osd.service_oid='"
							+ ((Map) list.get(i)).get("Oid")
							+ "' AND osd.status='o'" + sb.toString()));
		}

		for (int i = 0; i < list.size(); i++) {
			out.println("			<string>" + ((Map) list.get(i)).get("service_name")
					+ "</string>");
		}

		out.println("		</row>");
		out.println("		<row>");
		out
				.println("			<string>完成"
						+ manager
								.ezGetInt("SELECT COUNT(*)FROM OnlineServiceDoc osd, OnlineServices o WHERE "
										+ "o.Oid=osd.service_oid AND o.unit='"
										+ unit + "' AND osd.status='c'")
						+ "件</string>");

		for (int i = 0; i < list.size(); i++) {
			out.println("			<number tooltip='60'>"
					+ ((Map) list.get(i)).get("on") + "</number>");
		}
		out.println("		</row>");
		out.println("		<row>");
		out
				.println("			<string>未完成"
						+ manager
								.ezGetInt("SELECT COUNT(*)FROM OnlineServiceDoc osd, OnlineServices o WHERE "
										+ "o.Oid=osd.service_oid AND o.unit='"
										+ unit + "' AND osd.status<>'c'")
						+ "件</string>");
		for (int i = 0; i < list.size(); i++) {
			out.println("			<number tooltip='60'>"
					+ ((Map) list.get(i)).get("off") + "</number>");
		}
		out.println("		</row>");
		out.println("	</chart_data>");
		out.println("	<chart_grid_h alpha='0' />");
		out.println("	<chart_grid_v alpha='0' />");
		out.println("	<chart_pref rotation_x='65' drag='false' />"); // 角度
		out
				.println("	<chart_rect x='60' y='30' width='400' height='250' positive_alpha='0' />");
		out
				.println("	<chart_transition type='zoom' delay='0.1' duration='0.5' order='series' />");
		out.println("	<chart_type>3d column</chart_type>");
		out.println("	");
		out.println("	<draw>");
		java.util.Random rand;
		rand = new java.util.Random();
		out
				.println("		<image url='course/export/new_charts.swf?library_path=/CIS/pages/course/export/new_charts_library&xml_source=/CIS/OnlineServiceChartColumn?"
						+ rand + "'/>");
		// out.println(" <text layer='background' color='676767' alpha='85'
		// shadow='shadow1' font='新細明體' bold='false' size='12' x='8'
		// y='22'>服務狀況</text>");
		out.println("	</draw>");
		out.println("	<filter>");
		out
				.println("		<shadow id='shadow1' distance='1' angle='45' color='0' alpha='35' blurX='4' blurY='4' />");
		out.println("	</filter>");
		out.println("	");
		out
				.println("<legend layout='horizontal' bullet='circle' font='新細明體' bold='false' size='12' color='000000' alpha='85' />");
		out
				.println("<legend_label layout='horizontal' bullet='circle' font='新細明體' bold='false' size='12' color='454545' alpha='85' shadow='shadow1' />");
		// out.println(" <tooltip color='cc9944' alpha='85' size='12'
		// background_color='cc9944' background_alpha='85' shadow='shadow1'
		// />");
		out.println("");
		out.println("	<series_color>");
		out.println("		<color>cc9944</color>");
		out.println("		<color>556688</color>");
		out.println("	</series_color>");
		out.println("");
		out.println("</chart>");
		out.println("");
		out.println("");
		out.println("");

		out.flush();
		out.close();
	}
}
