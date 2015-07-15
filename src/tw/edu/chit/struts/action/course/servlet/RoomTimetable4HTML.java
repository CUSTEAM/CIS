package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class RoomTimetable4HTML extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		ServletContext context = request.getSession().getServletContext();
		String schoolName = (String) context.getAttribute("SchoolName_ZH");
		
		
	
		
		
		String term = manager.getSchoolTerm().toString();
		out.println("<html>");
		
			
		out.println("<head><title>" + schoolName + " - 教室課表</title>"
				+ "<style>" + "BODY { " + "FONT-FAMILY: Arial;" + "}" +
				"td {" + "FONT-SIZE: 10px;" + "}" + "" + "</style>"
				+ "<MEAT HTTP-EQUIV='Pragma' CONTENT='no-cache'>" + "</head>");

		out.println("<body>");

		out.println("<table width='100%' cellpadding='0' cellspacing='1' bgcolor='#999999'>");
		out.println("<tr height='30' bgcolor='#ffffff'>");
		for (int i = 1; i <= 7; i++) {
			out.println("<td width=14% align=center style='font-size:16px;'>");
			out.println("星期" + manager.getWeekChar(i));
			out.println("</td>");
		}
		
		//System.out.println(room_id);

		for (int i = 1; i <= 14; i++) {
			out.println("<tr height='60' bgcolor='#ffffff'>");

			for (int j = 1; j <= 7; j++) {
				out.println("<td width=14%>");
				
				//教室課表
				if(request.getParameter("room_id")!=null){
					out.println(getInfo(term, request.getParameter("room_id"), j + "", i + "", i + ""));
				}

				//班級課表
				if(request.getParameter("getCt")!=null){
					out.println(getCt(term, request.getParameter("getCt"), j + "", i + "", i + ""));
				}

				//教師課表
				if(request.getParameter("getTt")!=null){
					out.println(getTt(term, request.getParameter("getTt"), j + "", i + "", i + ""));
				}
				
				
				
				out.println("</td>");
			}

			out.println("</tr>");
			if (i == 4) {
				out.println("<tr height=20><td bgColor='#ffffff' colspan='7' align='center' style='font-size:16px;'>下午課程</td></tr>");
			}
			if (i == 10) {
				out.println("<tr height=20><td bgColor='#ffffff' colspan='7' align='center' style='font-size:16px;'>晚間課程</td></tr>");
			}
		}

		out.println("</table>");
			
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		out.println("</body>");
		out.println("</html>");
		out.close();
	}
	
	/**
	 * 教室課表
	 * @param term
	 * @param room_id
	 * @param week
	 * @param begin
	 * @param end
	 * @return
	 */
	private String getInfo(String term, String room_id, String week, String begin, String end) {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		Map map = new HashMap();

		List it = manager.ezGetBy("SELECT DISTINCT cl.ClassName, cl.ClassNo, d.Oid, c51.name as bname, d.cscode, c5.name as c5Name, e.cname, n.room_id, c.chi_name, n.name2 as name "
		+ ", dc.place FROM Dtime d LEFT OUTER JOIN empl e ON e.idno=d.techid, Csno c, Class cl, code5 c5,(Dtime_class dc LEFT OUTER JOIN Nabbr n ON n.room_id=dc.place) "
		+ "LEFT OUTER JOIN code5 c51 ON c51.idno=n.building AND c51.category='building' WHERE d.cscode=c.cscode AND c5.category='Campus' AND "
		+ "c5.idno=cl.CampusNo AND d.Sterm='"+term+ "' AND cl.ClassNo=d.depart_class AND dc.Dtime_oid=d.Oid AND dc.week='"+ week
		+ "' AND (dc.begin<="+ begin+ " && dc.end>="+ end + ") AND " + "dc.place='" + room_id + "'");

		StringBuilder sb = new StringBuilder();
		if (it.size() > 0) {
			sb.append(((Map) it.get(0)).get("ClassName") + "<br>");
			sb.append(((Map) it.get(0)).get("chi_name") + ""+ ((Map) it.get(0)).get("Oid") + "<br>");
			sb.append(((Map) it.get(0)).get("bname") + room_id + "<br>");

			if (((Map) it.get(0)).get("cname") != null) {
				sb.append(((Map) it.get(0)).get("cname") + "老師<br>");
			} else {
				sb.append("未指定老師<br>");
			}

		}
		return sb.toString();
	}
	
	/**
	 * 班級課表
	 * @param term
	 * @param room_id
	 * @param week
	 * @param begin
	 * @param end
	 * @return
	 */
	private String getCt(String term, String room_id, String week, String begin, String end) {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		Map map = new HashMap();

		List it = manager.ezGetBy("SELECT DISTINCT cl.ClassName, cl.ClassNo, d.Oid, c51.name as bname, d.cscode, c5.name as c5Name, e.cname, n.room_id, c.chi_name, n.name2 as name "
		+ ", dc.place FROM Dtime d LEFT OUTER JOIN empl e ON e.idno=d.techid, Csno c, Class cl, code5 c5,(Dtime_class dc LEFT OUTER JOIN Nabbr n ON n.room_id=dc.place) "
		+ "LEFT OUTER JOIN code5 c51 ON c51.idno=n.building AND c51.category='building' WHERE d.cscode=c.cscode AND c5.category='Campus' AND "
		+ "c5.idno=cl.CampusNo AND d.Sterm='"+term+ "' AND cl.ClassNo=d.depart_class AND dc.Dtime_oid=d.Oid AND dc.week='"+ week
		+ "' AND (dc.begin<="+ begin+ " && dc.end>="+ end + ") AND " + "d.depart_class='" + room_id + "'");
		
	
		

		StringBuilder sb = new StringBuilder();
		if (it.size() > 0) {
			sb.append(((Map) it.get(0)).get("ClassName") + "<br>");
			sb.append(((Map) it.get(0)).get("chi_name") + ""+ ((Map) it.get(0)).get("Oid") + "<br>");
			sb.append(((Map) it.get(0)).get("bname") +""+ ((Map) it.get(0)).get("place") + "<br>");

			if (((Map) it.get(0)).get("cname") != null) {
				sb.append(((Map) it.get(0)).get("cname") + "老師<br>");
			} else {
				sb.append("未指定老師<br>");
			}

		}
		return sb.toString();
	}
	
	/**
	 * 教師課表
	 * @param term
	 * @param room_id
	 * @param week
	 * @param begin
	 * @param end
	 * @return
	 */
	private String getTt(String term, String room_id, String week, String begin, String end) {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		Map map = new HashMap();

		List it = manager.ezGetBy("SELECT DISTINCT cl.ClassName, cl.ClassNo, d.Oid, c51.name as bname, d.cscode, c5.name as c5Name, e.cname, n.room_id, c.chi_name, n.name2 as name "
		+ ", dc.place FROM Dtime d LEFT OUTER JOIN empl e ON e.idno=d.techid, Csno c, Class cl, code5 c5,(Dtime_class dc LEFT OUTER JOIN Nabbr n ON n.room_id=dc.place) "
		+ "LEFT OUTER JOIN code5 c51 ON c51.idno=n.building AND c51.category='building' WHERE d.cscode=c.cscode AND c5.category='Campus' AND "
		+ "c5.idno=cl.CampusNo AND d.Sterm='"+term+ "' AND cl.ClassNo=d.depart_class AND dc.Dtime_oid=d.Oid AND dc.week='"+ week
		+ "' AND (dc.begin<="+ begin+ " && dc.end>="+ end + ") AND " + "d.techid='" + room_id + "'");

		StringBuilder sb = new StringBuilder();
		if (it.size() > 0) {
			sb.append(((Map) it.get(0)).get("ClassName") + "<br>");
			sb.append(((Map) it.get(0)).get("chi_name") + ""+ ((Map) it.get(0)).get("Oid") + "<br>");
			sb.append(((Map) it.get(0)).get("bname") +""+ ((Map) it.get(0)).get("place") + "<br>");

			if (((Map) it.get(0)).get("cname") != null) {
				sb.append(((Map) it.get(0)).get("cname") + "老師<br>");
			} else {
				sb.append("未指定老師<br>");
			}

		}
		return sb.toString();
	}
	
	/**
	 * 核心能力
	 * @param term
	 * @param room_id
	 * @param week
	 * @param begin
	 * @param end
	 * @return
	 */
	private String core(String term, String room_id, String week, String begin, String end) {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		Map map = new HashMap();

		List it = manager.ezGetBy("SELECT DISTINCT cl.ClassName, cl.ClassNo, d.Oid, c51.name as bname, d.cscode, c5.name as c5Name, e.cname, n.room_id, c.chi_name, n.name2 as name "
		+ "FROM Dtime d LEFT OUTER JOIN empl e ON e.idno=d.techid, Csno c, Class cl, code5 c5,(Dtime_class dc LEFT OUTER JOIN Nabbr n ON n.room_id=dc.place) "
		+ "LEFT OUTER JOIN code5 c51 ON c51.idno=n.building AND c51.category='building' WHERE d.cscode=c.cscode AND c5.category='Campus' AND "
		+ "c5.idno=cl.CampusNo AND d.Sterm='"+term+ "' AND cl.ClassNo=d.depart_class AND dc.Dtime_oid=d.Oid AND dc.week='"+ week
		+ "' AND (dc.begin<="+ begin+ " && dc.end>="+ end + ") AND " + "dc.place='" + room_id + "'");

		StringBuilder sb = new StringBuilder();
		if (it.size() > 0) {
			sb.append(((Map) it.get(0)).get("ClassName") + "<br>");
			sb.append(((Map) it.get(0)).get("chi_name") + ""+ ((Map) it.get(0)).get("cscode") + "<br>");
			sb.append(((Map) it.get(0)).get("bname") + room_id + "<br>");

			if (((Map) it.get(0)).get("cname") != null) {
				sb.append(((Map) it.get(0)).get("cname") + "老師<br>");
			} else {
				sb.append("未指定老師<br>");
			}

		}
		return sb.toString();
	}
	

}
