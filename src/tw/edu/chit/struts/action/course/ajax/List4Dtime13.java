package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import tw.edu.chit.service.CourseManager;

public class List4Dtime13 extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=list4dtime13.xls");
		Object pecode9[] = manager.getPecode9().toArray();
		Object pecode11[] = manager.getPecode11().toArray();
		// Map map;
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		out.println("<table>");
		// out.println("<c:if test='${not empty dtimeList}'>"); //???
		out.println("	<tr>");
		out.println("		<td>");

		/*
		 * SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm"); String
		 * date=sf.format(new Date());
		 * 
		 * out.println(""); out.println("<table border='0' align='left'
		 * cellpadding='0' cellspacing='1' width='100%'>"); out.println(" <tr>");
		 * out.println(" <td colspan='13' align='center'>"); out.println("
		 * <font face='標楷體' size='+2'>表一之三</font>"); out.println(" </td>");
		 * out.println(" </tr>"); out.println(" <tr>"); out.println("
		 * <td colspan='13' align='right'>"); out.println(" <font
		 * size='-2'>課程管理系統 "+date+"</font>"); out.println(" </td>");
		 * out.println(" </tr>"); out.println("</table>");
		 */

		out
				.println("			<table border='1' align='left' cellpadding='0' cellspacing='1' bgcolor='#CFE69F' width='100%'>");
		out.println("");
		out.println("				<tr height='20'>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學年</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學期</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>主聘系所</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>身份識別號</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>教師姓名</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>授課系所</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>授課學制</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>課程名稱</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>通職課程</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>寒暑期開課</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>授課時數</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>開課學分</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>畢業班課程</td>");
		out.println("				</tr>");

		HttpSession session = request.getSession(false);
		List dtimeList = (List) session.getAttribute("dtimeList");
		String year = manager.getSchoolYear().toString();
		String term = manager.getSchoolTerm().toString();
		for (int i = 0; i < dtimeList.size(); i++) {
			if (((Map) dtimeList.get(i)).get("cscode").equals("50000")
					|| ((Map) dtimeList.get(i)).get("cscode").equals("T0001")
					|| ((Map) dtimeList.get(i)).get("cscode").equals("T0002")) {
				continue;
			}
			String departClass = ((Map) dtimeList.get(i)).get("departClass")
					.toString();
			String tmpDepartClass = departClass.toString().substring(0, 3);
			if (i % 2 == 1) {
				out.println("				<tr height='20' bgcolor='#f0fcd7'>");
			} else {
				out.println("				<tr height='20'>");
			}
			out.println("					<td align='center'>" + year + "</td>");
			out.println("					<td align='center'" + term + "</td>");
			out.println("					<td align='center'>");

			// 取得主聘系所
			for (int j = 0; j < pecode9.length; j++) {

				if (((Map) pecode9[j]).get("id1").equals(
						((Map) dtimeList.get(i)).get("idno2"))) {
					out.println(((Map) pecode9[j]).get("id"));
				}
			}
			out.print("</td>");

			out.println("<td align='center'>"
					+ ((Map) dtimeList.get(i)).get("techid") + "</td>");
			out.println("					<td align='center'>"
					+ ((Map) dtimeList.get(i)).get("techName") + "</td>");
			out.println("					<td align='center'>");

			// TODO 陳組長說不用管碩士班
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

			// 取得開課系所
			for (int k = 0; k < pecode9.length; k++) {
				// System.out.println(((Map)dtimeList.get(i)).get("departClass").toString().charAt(3));

				if (((Map) pecode9[k]).get("id1").toString().charAt(0) == departClass
						.toString().charAt(3)) {
					out.println(((Map) pecode9[k]).get("id"));

				}
			}
			out.println("</td>");

			out.println("					<td align='center'>");

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

			out.println("					<td>" + ((Map) dtimeList.get(i)).get("chiName2")
					+ "</td>");

			out.println("					<td align='center'>");

			// 取得通識課程
			if (((Map) dtimeList.get(i)).get("opt2").equals("通識")
					|| departClass.toString().charAt(3) == '0') {
				out.print("Y");
			} else {
				out.print("N");
			}

			out.print("</td");
			out.println("					<td align='center'>N</td>");

			// 判斷畢業班囉
			String graduate = "N";
			char grade = '0';
			String tmpDepart = departClass.substring(0, 3);
			if (tmpDepartClass.equals("112")) {
				grade = '2';
			} else if (tmpDepartClass.equals("115")) {
				grade = '2';
			} else if (tmpDepartClass.equals("11G")) {
				grade = '2';
			} else if (tmpDepartClass.equals("122")) {
				grade = '2';
			} else if (tmpDepartClass.equals("132")) {
				grade = '2';
			} else if (tmpDepartClass.equals("142")) {
				grade = '2';
			} else if (tmpDepartClass.equals("152")) {
				grade = '2';
			} else if (tmpDepartClass.equals("154")) {
				grade = '4';
			} else if (tmpDepartClass.equals("164")) {
				grade = '4';
			} else if (tmpDepartClass.equals("172")) {
				grade = '2';
			} else if (tmpDepartClass.equals("182")) {
				grade = '2';
			} else if (tmpDepartClass.equals("18G")) {
				grade = '2';
			} else if (tmpDepartClass.equals("192")) {
				grade = '2';
			}
			// System.out.println("departClass.charAt(4)="+departClass.charAt(4)+"
			// departClass="+departClass);
			if (departClass.charAt(4) == grade) {
				graduate = "Y";
			}

			out.println("					<td align='center'>"
					+ Integer.parseInt(((Map) dtimeList.get(i)).get("thour")
							.toString()) * 18 + "</td>");
			out.println("					<td align='center'>"
					+ ((Map) dtimeList.get(i)).get("credit") + "</td>");
			out.println("					<td align='center'>" + graduate + "</td>");

		}
		out.println("	</tr>");
		out.println("</table>");
		out.println("</body>");

		out.println("</html>");
		out.close();
	}
}
