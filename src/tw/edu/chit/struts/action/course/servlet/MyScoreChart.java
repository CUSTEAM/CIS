package tw.edu.chit.struts.action.course.servlet;

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

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;

public class MyScoreChart extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);

		UserCredential credential = (UserCredential) session
				.getAttribute("Credential");
		String termNow = manager.getNowBy("School_term");// 現在學期

		String studentNo = credential.getStudent().getStudentNo();
		List list = manager
				.ezGetBy("SELECT s.*, c.chi_name FROM Seld s, Dtime d, Csno c WHERE c.cscode=d.cscode AND s.Dtime_oid=d.Oid AND d.Sterm='"
						+ termNow
						+ "' AND "
						+ "s.student_no='"
						+ studentNo
						+ "' AND (s.score!=''&&s.score is not null)AND (s.score2!=''&&s.score2 is not null)");

		out.println("<chart>");
		out
				.println("	<axis_category size='12' font='arial, 新細明體' color='555555' alpha='0' />");
		out
				.println("	<axis_ticks value_ticks='' category_ticks='' font='arial, 新細明體'/>");
		out
				.println("	<axis_value alpha='0' min='0' max='100' font='arial, 新細明體' />");
		out.println("	");
		out
				.println("	<chart_border bottom_thickness='0' left_thickness='0' />");
		out.println("	<chart_data>");
		out.println("		<row>");
		out.println("			<null/>");
		for (int i = 0; i < list.size(); i++) {
			out.println("			<string>" + (((Map) list.get(i)).get("chi_name"))
					+ "</string>");
		}
		out.println("		</row>");
		out.println("		<row>");
		out.println("			<string>期中成績</string>");
		for (int i = 0; i < list.size(); i++) {
			out.println("			<number>" + (((Map) list.get(i)).get("score2"))
					+ "</number>");
		}
		out.println("		</row>");
		out.println("		<row>");
		out.println("			<string>期末成績</string>");
		for (int i = 0; i < list.size(); i++) {
			out.println("			<number>" + (((Map) list.get(i)).get("score"))
					+ "</number>");
		}
		out.println("		</row>");
		out.println("	</chart_data>");
		out
				.println("	<chart_grid_h alpha='20' color='000000' thickness='1' type='dashed' />");
		out
				.println("	<chart_grid_v alpha='5' color='000000' thickness='20' type='solid' />");
		out
				.println("	<chart_pref point_shape='circle' point_size='8' fill_shape='true' grid='circular' />");
		out
				.println("	<chart_rect bevel='bevel1' x='60' y='40' width='350' height='225' positive_color='008888' positive_alpha='25' />");
		out
				.println("	<chart_transition type='zoom' delay='0.5' duration='0.5' order='series' />");
		out.println("	<chart_type>polar</chart_type>");
		out.println("	");
		out.println("	<draw>");
		// out.println(" <text layer='background' shadow='shadow1'
		// transition='slide_right' delay='0' duration='3' color='000000'
		// width='500' alpha='8' size='95' x='0' y='-30'>Bright</text>");
		// out.println(" <text shadow='shadow1' alpha='75' font='arial, 新細明體'
		// transition='slide_right' delay='0' duration='5' color='000000'
		// alpha='5' size='48' x='450' y='75'>97學年</text>");
		// out.println(" <text shadow='shadow1' alpha='75' font='arial, 新細明體'
		// transition='slide_right' delay='0' duration='10' color='000000'
		// alpha='4' size='36' x='400' y='150'>第1學期</text>");
		out.println("	</draw>");
		out.println("	<filter>");
		out
				.println("		<shadow id='shadow1' distance='2' angle='45' color='0' alpha='100' blurX='10' blurY='10' />");
		out
				.println("		<shadow id='shadow2' distance='1' angle='45' color='0' alpha='50' blurX='3' blurY='3' />");
		out
				.println("		<bevel id='bevel1' angle='45' blurX='10' blurY='10' distance='5' highlightAlpha='25' highlightColor='ffffff' shadowAlpha='35' type='outer' />");
		out.println("	</filter>");
		out.println("	");
		out
				.println("	<legend_label layout='vertical' bullet='line' size='12' font='arial, 新細明體' color='555555' alpha='90' />");
		out
				.println("	<legend_rect x='475' y='0' width='20' height='70' margin='5' fill_alpha='10' />");
		out
				.println("	<legend shadow='shadow2' x='20' y='100' width='20' height='40' margin='3' fill_alpha='0' layout='vertical' bullet='circle' size='12' color='4e627c' alpha='75' />");
		out.println("	");
		out.println("	<series_color>");
		out.println("		<color>ff4400</color>");
		out.println("		<color>4e627c</color>");
		out.println("	</series_color>");
		out.println("	");
		out.println("</chart>");
		out.println("");
		out.println(" ");
		out.println("");

	}

}