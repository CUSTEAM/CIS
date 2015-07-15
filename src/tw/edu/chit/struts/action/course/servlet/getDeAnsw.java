package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;

public class getDeAnsw extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");

		List list = manager
				.ezGetBy("SELECT c.name as deptName, SUM(Score) as score, COUNT(*) as students "
						+ "FROM DeAnsw d, code5 c WHERE d.Depart_class=c.idno AND c.category='Dept' GROUP BY Depart_class ORDER BY Score DESC LIMIT 5");

		out.println("<chart>");
		out.println("");
		// out.println(" <axis_value min='0' bold='false' size='8' font='arial,
		// 細明體' color='000000' alpha='50' steps='4' prefix='' suffix=''
		// decimals='0' separator='' show_min='true' />");
		out
				.println("	<axis_category size='10' bold='false' font='arial, 新細明體' color='555555' alpha='50'/>");
		// out.println(" <chart_border color='000000' top_thickness='1'
		// bottom_thickness='2' left_thickness='0' right_thickness='0' />");
		out.println("");
		out.println("	<chart_data >");

		out.println("		<row>");
		out.println("			<null/>");

		for (int i = 0; i < list.size(); i++) {
			out.println("			<string>" + ((Map) list.get(i)).get("deptName")
					+ "</string>");
		}

		out.println("		</row>");

		out.println("		<row>");
		out.println("			<string>得分點數</string>");

		for (int i = 0; i < list.size(); i++) {

			out.println("			<number>"
					+ Float.parseFloat(((Map) list.get(i)).get("score")
							.toString()) / 5 + "</number>");
		}
		out.println("		</row>");

		out.println("		<row>");
		out.println("			<string>參與人數</string>");
		for (int i = 0; i < list.size(); i++) {

			out.println("			<number>" + ((Map) list.get(i)).get("students")
					+ "</number>");
		}
		out.println("		</row>");
		out.println("	</chart_data>");

		out
				.println("	<chart_grid_h alpha='20' color='000000' thickness='1' type='dashed' />");
		out
				.println("	<chart_rect x='50' y='50' width='300' height='200' positive_color='000066' negative_color='000000' positive_alpha='10' negative_alpha='30' />");
		out
				.println("	<chart_transition type='scale' delay='0.1' duration='0.1' order='series' />");
		out
				.println("	<chart_value color='ffffff' alpha='85' font='arial, 新細明體' bold='true' size='10' position='middle' prefix='' suffix='' decimals='0' separator='' as_percentage='false' />");
		out.println("");
		out.println("	<draw>");
		out
				.println("		<text color='000000' alpha='10' font='arial, 新細明體' rotation='-90' bold='true' size='75' x='-20' y='300' width='300' height='200' h_align='left' v_align='top'>revenue</text>");
		out
				.println("		<text color='666666' alpha='0' font='arial, 新細明體' rotation='-90' bold='true' size='18' x='0' y='0' width='300' height='50' h_align='left' v_align='top'>TOP5</text>");

		for (int i = 0; i < list.size(); i++) {
			float tmp = (Float.parseFloat(((Map) list.get(i)).get("score")
					.toString()) / 5)
					/ Integer.parseInt(((Map) list.get(i)).get("students")
							.toString());
			// int x=i+5+100+36;
			out.println("		<text color='666666' alpha='0' font='arial, 新細明體' "
					+ "rotation='0' bold='false' size='10' x='180' y='"
					+ (i + 7) + 5 + "' width='300' height='150' "
					+ "h_align='left' v_align='top'>"
					+ ((Map) list.get(i)).get("deptName") + ", 平均 " + tmp +

					"</text>");

		}

		// out.println(" <text color='eeeeee' alpha='80' font='arial, 新細明體'
		// rotation='-90' bold='true' size='16' x='1' y='1' width='300'
		// height='50' h_align='left' v_align='top'>TOP5</text>");
		out.println("	</draw>");
		out.println("");
		out
				.println("	<legend_label layout='horizontal' font='arial, 新細明體' bold='true' size='12' color='333355' alpha='90' />");
		out
				.println("	<legend_rect x='50' y='27' width='300' height='20' margin='5' fill_color='000066' fill_alpha='8' line_color='000000' line_alpha='0' line_thickness='0' />");
		out.println("");
		out.println("	<series_color>");
		out.println("		<color>768bb3</color>");
		out.println("		<color>666666</color>");
		out.println("	</series_color>");
		out.println("	<series_gap set_gap='40' bar_gap='-25' />");
		out.println("	");
		out.println("</chart>");
		out.println("");
		out.println("");

		out.close();

	}

}