package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Member;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;

public class CoanswForTechE extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(1);
		BigDecimal big;

		Member me = (Member) ((UserCredential) session
				.getAttribute("Credential")).getMember();
		String color[] = { "60a1d4", "f79e27", "ebd836", "bc4e32", "4c566f",
				"6c6642", "bcd1be", "974b57", "a9c2ea", "c48bcf", "fff585",
				"bbc1d7", "c3bda5", "ffeac2", "92b37c", "fbcbdb", "556538",
				"f0737b", "ed6442", "CDE1EB" };
		// 課程
		List myCsOid = manager
				.ezGetBy("SELECT cl.ClassName, d.Oid, cs.chi_name FROM Class cl, Csno cs, Dtime d, Coansw c WHERE "
						+ "cs.cscode=d.cscode AND d.Oid=c.Dtime_oid AND cl.ClassNo=d.depart_class AND d.techid='"
						+ me.getIdno()
						+ "' AND (d.elearning='1' OR d.elearning='2') GROUP BY d.Oid");
		// 題目
		List coqN = manager
				.ezGetBy("SELECT options FROm CoQuestion WHERE (textValue='1' OR textValue='2') AND type='M'");

		List myCoansw = new ArrayList();
		Map map;
		for (int i = 0; i < myCsOid.size(); i++) {
			map = new HashMap();
			map.put("dtimeOid", ((Map) myCsOid.get(i)).get("Oid"));
			map.put("ClassName", ((Map) myCsOid.get(i)).get("ClassName"));
			map.put("chi_name", ((Map) myCsOid.get(i)).get("chi_name"));

			float score[] = new float[coqN.size()];
			List tmp = manager
					.ezGetBy("SELECT answer FROM Coansw WHERE Dtime_oid='"
							+ ((Map) myCsOid.get(i)).get("Oid") + "'");// 該科所有問卷

			for (int j = 0; j < tmp.size(); j++) {// 該科所有問卷為長度
				String tmpScore = ((Map) tmp.get(j)).get("answer").toString();// 問卷答案
				// 散裝並加總
				for (int k = 0; k < tmpScore.length(); k++) {
					score[k] = score[k]
							+ Integer.parseInt(tmpScore.substring(k, k + 1)); // 循序加總
				}
			}
			map.put("myScore", score); // 總成績未整
			map.put("count", tmp.size()); // 問卷數
			myCoansw.add(map);
		}

		out.println("<chart>");
		out
				.println("	<axis_category size='12' font='arial, 新細明體' color='555555' alpha='90'/>");
		out
				.println("	<axis_ticks value_ticks='1' category_ticks='1' major_color='444444' major_thickness='2' minor_count='0' />");
		out
				.println("	<axis_value max='100' size='12' alpha='90' color='ffffff' show_min='1' background_color='446688' />");
		out.println("	");
		out
				.println("	<chart_border bottom_thickness='2' left_thickness='2' color='444444' />");

		out.println("	<chart_data>");
		out.println("		<row>");
		out.println("			<null/>");

		// 右側列表
		for (int i = 0; i < coqN.size(); i++) {
			out.println("			<string>" + ((Map) coqN.get(i)).get("options")
					+ "</string>");
		}
		out.println("		</row>");

		// 課程評分狀態
		for (int i = 0; i < myCoansw.size(); i++) {

			float score[] = (float[]) ((Map) myCoansw.get(i)).get("myScore");
			int count = Integer.parseInt(((Map) myCoansw.get(i)).get("count")
					.toString());

			out.println("		<row>");

			if (myCoansw.size() <= 8) {
				// out.println("
				// <string>\n"+((Map)myCoansw.get(i)).get("ClassName")+"
				// "+((Map)myCoansw.get(i)).get("chi_name")+"\n"+count+"份問卷,總平均"+myScore+"\n"+"</string>");
				out.println("			<string>\n"
						+ ((Map) myCoansw.get(i)).get("ClassName") + " "
						+ ((Map) myCoansw.get(i)).get("chi_name") + "\n"
						+ count + "份問卷\n" + "</string>");
			} else {
				// out.println("
				// <string>"+((Map)myCoansw.get(i)).get("ClassName")+"
				// "+((Map)myCoansw.get(i)).get("chi_name")+"\n"+count+"份問卷,總平均"+myScore+"\n"+"</string>");
				out.println("			<string>"
						+ ((Map) myCoansw.get(i)).get("ClassName") + " "
						+ ((Map) myCoansw.get(i)).get("chi_name") + "\n"
						+ count + "份問卷\n" + "</string>");
			}

			for (int j = 0; j < score.length; j++) {
				// out.println(" <number>"+f+"</number>");
				// out.println(" <number>"+(float)score[j]/count+"</number>");
				big = new BigDecimal((float) score[j] / (float) count * 20);
				out.println("			<number>"
						+ big.setScale(1, BigDecimal.ROUND_UP) + "</number>");
			}
			out.println("		</row>");
		}

		out.println("	</chart_data>");
		out
				.println("	<chart_grid_h alpha='20' color='000000' thickness='1' type='solid' />");
		out
				.println("	<chart_grid_v alpha='10' color='000000' thickness='2' type='dotted' />");
		out
				.println("	<chart_pref point_shape='none' point_size='0' fill_shape='' line_thickness='2' type='line' grid='circular' />");
		out
				.println("	<chart_rect x='105' y='65' width='300' height='250' positive_color='000000' positive_alpha='10' />");
		out.println("	<chart_type>polar</chart_type>");
		out.println("	");
		out.println("	<draw>");
		out
				.println("		<text font='arial, 新細明體' color='888888' rotation='0' size='20' x='5' y='5' width='390'  height='295' h_align='left' v_align='top'>遠距/輔助 課程</text>");
		// out.println(" <circle layer='background' x='255' y='140' radius='120'
		// fill_alpha='0' line_color='000000' line_alpha='5' line_thickness='20'
		// />");
		out
				.println("		<circle layer='background' x='255' y='190' radius='150' fill_alpha='0' line_color='000000' line_alpha='5' line_thickness='20' />");

		out.println("	</draw>");
		out.println("	");
		out
				.println("	<legend_label layout='vertical' bullet='line' size='12' font='arial, 新細明體' color='555555' alpha='90' />");
		out
				.println("	<legend_rect x='580' y='0' width='20' height='70' margin='5' fill_alpha='10' />");

		out.println("	<series_color>");
		for (int i = 0; i < myCsOid.size(); i++) {
			out.println("		<color>" + color[i] + "</color>");
		}
		out.println("	</series_color>");
		out.println("</chart>");
		out.close();
	}

}