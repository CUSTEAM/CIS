package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class List4Dtime32 extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=List4Dtime32.xls");

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		out.println("<table>");
		// out.println("<c:if test='${not empty dtimeList}'>"); //???
		out.println("	<tr>");
		out.println("		<td>");

		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String date = sf.format(new Date());

		out.println("");

		out
				.println("			<table border='1' align='left' cellpadding='0' cellspacing='1' bgcolor='#CFE69F' width='100%'>");
		out.println("");
		out.println("				<tr height='20'>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學年</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學期</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>開課學院</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>開課系所</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學制</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學期課號</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>課程名稱</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>修別</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>講授時數(每週)</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>實習時數(每週)</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>總學分數</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>授課教師</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>修課人數</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>外語授課</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>授課語言</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>專業課程</td>");
		out.println("				</tr>");

		HttpSession session = request.getSession(false);
		List dtimeList = (List) session.getAttribute("dtimeList");
		Object pecode9[] = manager.getPecode9().toArray();
		Object pecode11[] = manager.getPecode11().toArray();

		for (int i = 0; i < dtimeList.size(); i++) {

			String departClass = ((Map) dtimeList.get(i)).get("departClass")
					.toString();
			String tmpDepartClass = departClass.toString().substring(0, 3);
			// TODO 尚未定義學期劃分方法
			out.println("				<tr height='20'>");
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			out.print("95</td>");

			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			out.print("2</td>");

			// 開課學院
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			ServletContext context = request.getSession().getServletContext();
			out.print((String) context.getAttribute("SchoolName_ZH") + "</td>");

			// 開課系所

			// 開課系所
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}

			// 陳組長說不用管碩士班
			/*
			 * if(
			 * ((Map)dtimeList.get(i)).get("departClass").toString().charAt(2)=='G' ){
			 * 
			 * for(int k=0; k<pecode9.length; k++){
			 * 
			 * if(
			 * ((Map)pecode9[k]).get("id1").equals(((Map)dtimeList.get(i)).get("idno2"))&&
			 * ((Map)pecode9[k]).get("id2").equals("G") ){
			 * out.println(((Map)pecode9[k]).get("id"));
			 *  } }
			 */
			for (int k = 0; k < pecode9.length; k++) {
				// System.out.println(((Map)dtimeList.get(i)).get("departClass").toString().charAt(3));

				if (((Map) pecode9[k]).get("id1").toString().charAt(0) == departClass
						.toString().charAt(3)) {
					out.println(((Map) pecode9[k]).get("id"));

				}
			}
			out.println("</td>");

			// 學制
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}

			// 取得授課學制
			for (int l = 0; l < pecode11.length; l++) {

				if ((((Map) pecode11[l]).get("id1").toString().length() >= 3 && ((Map) pecode11[l])
						.get("id2").toString().length() >= 3)) {
					String tmpPcode111 = ((Map) pecode11[l]).get("id1")
							.toString();
					String tmpPcode112 = ((Map) pecode11[l]).get("id2")
							.toString();

					if (tmpPcode111.equals(tmpDepartClass)
							|| tmpPcode112.equals(tmpDepartClass)) {
						out.println(((Map) pecode11[l]).get("id"));
					}
				}

			}

			out.print("</td>");

			// 課程代碼
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			out.print(((Map) dtimeList.get(i)).get("cscode") + "</td>");

			// 課程名稱
			out.println("					<td");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			out.print(((Map) dtimeList.get(i)).get("chiName2") + "</td>");

			// 修別
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			out.print(((Map) dtimeList.get(i)).get("opt2") + "</td>");

			// 時數
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			out.print(((Map) dtimeList.get(i)).get("thour") + "</td>");

			// 實習時數
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			out.print("0</td>");

			// 總學分數
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			out.print(((Map) dtimeList.get(i)).get("credit") + "</td>");

			// 授課教師
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			out.print(((Map) dtimeList.get(i)).get("techName") + "</td>");

			// 修課人數
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			out.print(((Map) dtimeList.get(i)).get("stuSelect") + "</td>");

			// 外語授課
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}

			if (((Map) dtimeList.get(i)).get("chiName2").toString().indexOf(
					"日文") > 0) {
				System.out.println("Japan");
			}

			String language = "國語";
			if ((((Map) dtimeList.get(i)).get("chiName2").toString().indexOf(
					"日文") >= 0 || ((Map) dtimeList.get(i)).get("chiName2")
					.toString().indexOf("日語") >= 0)) {
				language = "日語";
				out.print("是</td>");
			} else if ((((Map) dtimeList.get(i)).get("chiName2").toString()
					.indexOf("英文") >= 0 || ((Map) dtimeList.get(i)).get(
					"chiName2").toString().indexOf("英語") >= 0)) {
				language = "英語";
				out.print("是</td>");
			} else {
				out.print("否</td>");
			}

			// 授課語言
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			out.print(language + "</td>");

			// 專業課程
			out.println("					<td align='center'");
			if (i % 2 == 1) {
				out.print("bgcolor='#f0fcd7'>");
			} else {
				out.print("bgcolor='ffffff'>");
			}
			out.print("</td>");

		}

		out.println("	</tr>");
		out.println("</table>");
		out.println("</body>");

		out.println("</html>");

		out.close();
	}
}