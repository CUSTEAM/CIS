package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Dtime;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;

public class List4Course35 extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		SummerManager summerManager = (SummerManager) ctx
				.getBean("summerManager");// 匯入暑修方法
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=List4Course35.xls");

		Object pecode9[] = manager.getPecode9().toArray();
		Object pecode11[] = manager.getPecode11().toArray();
		HttpSession session = request.getSession(false);
		List dtimeList = (List) session.getAttribute("dtimeList");

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><style>body{font-size:150%;}</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<table border='1'>");
		out.println("<tr>");
		out.println("<td>");

		// SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
		// String date=sf.format(new Date());
		String school;

		out.println("<table border='1'>");
		out.println("<tr>");
		out.println("<td align='center'>當期課號</td>");
		out.println("<td align='center'>課程名稱</td>");
		out.println("<td align='center'>修別</td>");
		out.println("<td align='center'>科目類別</td>");
		out.println("<td align='center'>講授時數</td>");
		out.println("<td align='center'>實習時數</td>");
		out.println("<td align='center'>開課學分數</td>");
		out.println("<td align='center'>第一次上課日期</td>");
		out.println("<td align='center'>修課人數(男)</td>");
		out.println("<td align='center'>修課人數(女)</td>");
		out.println("<td align='center'>主要授課語言</td>");
		out.println("<td align='center'>畢業班課程</td>");
		out.println("<td align='center'>寒暑別</td>");
		out.println("<td align='center'>全程使用外語</td>");
		out.println("<td align='center'>身份識別種類</td>");
		out.println("<td align='center'>身份識別號</td>");
		out.println("<td align='center'>授課時數</td>");
		out.println("<td align='center'>系所代碼</td>");
		out.println("<td>學制代碼</td>");
		out.println("</tr>");

		List dtimeTeacherTmp;

		Dtime dtime;
		for (int i = 0; i < dtimeList.size(); i++) {
			
			
			dtime=(Dtime) manager.hqlGetBy("FROM Dtime WHERE Oid='"+((Map) dtimeList.get(i)).get("oid")+"'").get(0);

			


			if (((Map) dtimeList.get(i)).get("cscode").equals("50000")
					|| ((Map) dtimeList.get(i)).get("cscode").equals("T0001")
					|| ((Map) dtimeList.get(i)).get("cscode").equals("T0002")) {continue;}
			
			


			out.println("  <tr>");
			// 課碼
			out.println("<td align='center'>");
			out.println(((Map) dtimeList.get(i)).get("cscode"));
			out.println("</td>");
			// 課名
			out.println("<td align='left'>");
			out.println(((Map) dtimeList.get(i)).get("chiName3"));
			out.println("</td>");
			// 選別
			out.println("<td align='left'>");
			out.println(((Map) dtimeList.get(i)).get("opt2"));
			out.println("</td>");
			// 科目類別
			/*
			 * out.println("<td align='left'>"); String type="專業核心科目";
			 * if(dtime.getOpt().equals("3")){ type="通識科目"; }
			 * if(dtime.getDepartClass().charAt(3)=='0'){ type="共同科目/一般科目"; }
			 */

			out.println("<td align='left'>");
			out.println("");
			out.println("</td>");
			// 時數
			out.println("<td align='center'>");
			out.println(((Map) dtimeList.get(i)).get("thour"));
			out.println("</td>");
			// 實習
			out.println("<td align='center'>");
			out.println("0");
			out.println("</td>");
			// 學分
			out.println("<td align='center'>");
			out.println(((Map) dtimeList.get(i)).get("credit"));
			out.println("</td>");
			// 第一次上課日期
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date d = new Date();
			try {
				d = sd.parse(((Map) manager.ezGetBy(
						"SELECT wdate FROM week WHERE daynite='"
								+ dtime.getDepartClass().substring(0, 2)
								+ "'").get(0)).get("wdate").toString());
			} catch (Exception e) {
				System.out.println(e);
			}
			c.setTime(d);
			List week = manager
					.ezGetBy("SELECT week FROM Dtime_class WHERE Dtime_oid='"
							+ dtime.getOid() + "' ORDER BY week ASC");
			Integer first = 2;
			if (week.size() > 0) {
				first = Integer.parseInt(((Map) week.get(0)).get("week")
						.toString()) + 1;
				if (first > 7) {
					first = 1;
				}
			}

			c.set(Calendar.DAY_OF_WEEK, first);
			d = c.getTime();
			out.println("<td align='center'>");
			out.println(sd.format(d));
			out.println();
			out.println("</td>");
			
			// 學生人數(男)
			out.println("<td align='center'>");
			out.println(summerManager
					.ezGetInt("SELECT COUNT(*)FROM Seld, stmd WHERE Seld.student_no=stmd.student_no and stmd.sex='1' and Seld.Dtime_oid='"
							+ dtime.getOid() + "'"));
			out.println("</td>");
			
			// 學生人數(女)
			out.println("<td align='center'>");
			out.println(summerManager
					.ezGetInt("SELECT COUNT(*)FROM Seld, stmd WHERE Seld.student_no=stmd.student_no and stmd.sex='2' and Seld.Dtime_oid='"
							+ dtime.getOid() + "'"));
			out.println("</td>");

			// 主要授課語言
			String language = "國語";
			if ((((Map) dtimeList.get(i)).get("chiName2").toString()
					.indexOf("日文") >= 0 || ((Map) dtimeList.get(i)).get(
					"chiName2").toString().indexOf("日語") >= 0)) {
				language = "日語";
			} else if ((((Map) dtimeList.get(i)).get("chiName2").toString()
					.indexOf("英文") >= 0 || ((Map) dtimeList.get(i)).get(
					"chiName2").toString().indexOf("英語") >= 0)) {
				language = "英語";
			}
			out.println("<td align='center'>");
			out.println(language);
			out.println("</td>");

			// 畢業班課程
			String graduate = "否";
			if (dtime.getDepartClass().charAt(2) == dtime.getDepartClass()
					.charAt(4)) {
				graduate = "是";
			}
			out.println("<td align='center'>");
			out.println(graduate);
			out.println("</td>");

			// 寒暑別
			out.println("<td align='center'>");
			out.println("無");
			out.println("</td>");

			// 全程使用外語
			out.println("<td align='center'>");
			out.println("否");
			out.println("</td>");

			// 身份識別種類
			out.println("<td align='center'>");
			if (dtime.getTechid() != null && !dtime.getTechid().equals(""))
				out.println("I");
			out.println("</td>");

			// 身份識別號
			out.println("<td align='center'>");
			out.println(dtime.getTechid());
			out.println("</td>");

			// 授課時數
			out.println("<td align='center'>");
			out.println(dtime.getThour());
			out.println("</td>");

			// 系所代碼
			out.println("<td align='center'>");

			for (int k = 0; k < pecode9.length; k++) {

				// 若是碩班
				if (dtime.getDepartClass().indexOf("G") > -1) {
					if (((Map) pecode9[k]).get("id1").toString().charAt(0) == dtime
							.getDepartClass().toString().charAt(3)
							&& ((Map) pecode9[k]).get("id2").equals("G")) {
						out.println(((Map) pecode9[k]).get("id"));
					}
				} else {

					if (((Map) pecode9[k]).get("id1").toString().charAt(0) == dtime
							.getDepartClass().toString().charAt(3)
							&& !((Map) pecode9[k]).get("id2").equals("G")) {
						out.println(((Map) pecode9[k]).get("id"));
					}
				}
			}
			out.println("</td>");

			// 學制代碼
			school = manager
					.ezGetString("SELECT SchoolNo FROM Class WHERE ClassNo='"
							+ dtime.getDepartClass() + "'");
			out
					.println("<td align='center'>"
							+ manager
									.ezGetString("SELECT id FROM Pecode11 WHERE id1='"
											+ school + "'") + "</td>");
			out.println("</tr>");

			// 若有一科目多教師
			dtimeTeacherTmp = manager.ezGetBy("SELECT * FROM Dtime_teacher WHERE Dtime_oid='"+ ((Map) dtimeList.get(i)).get("oid") + "'");
			
			try{
				if (dtimeTeacherTmp.size() > 0) {

					for (int j = 0; j < dtimeTeacherTmp.size(); j++) {
						if (!((Map) dtimeList.get(i)).get("techid").equals(((Map) dtimeList.get(i)).get("teach_id"))) {

							dtime = (Dtime) manager.hqlGetBy(
									"FROM Dtime WHERE Oid='"
											+ ((Map) dtimeList.get(i))
													.get("oid") + "'").get(0);
							if (i % 2 == 1) {
								out.println("  <tr bgcolor='#f0fcd7'>");
							} else {
								out.println("  <tr>");
							}
							// 課碼
							out.println("<td align='center'>");
							out.println(((Map) dtimeList.get(i)).get("cscode"));
							out.println("</td>");
							// 課名
							out.println("<td align='left'>");
							out.println(((Map) dtimeList.get(i))
									.get("chiName2"));
							out.println("</td>");
							// 選別
							out.println("<td align='left'>");
							out.println(((Map) dtimeList.get(i)).get("opt2"));
							out.println("</td>");
							// 科目類別
							/*
							 * out.println("<td align='left'>");
							 * type="專業核心科目"; if(dtime.getOpt().equals("3")){
							 * type="通識科目"; }
							 * if(dtime.getDepartClass().charAt(3)=='0'){
							 * type="共同科目/一般科目"; }
							 */
							out.println("<td align='left'>");
							out.println();
							out.println("</td>");
							// 時數
							out.println("<td align='center'>");
							out.println(((Map) dtimeList.get(i)).get("thour"));
							out.println("</td>");
							// 實習
							out.println("<td align='center'>");
							out.println("0");
							out.println("</td>");
							// 學分
							out.println("<td align='center'>");
							out.println(((Map) dtimeList.get(i)).get("credit"));
							out.println("</td>");
							// 第一次上課日期
							sd = new SimpleDateFormat("yyyy-MM-dd");
							c = Calendar.getInstance();
							d = new Date();
							try {
								d = sd.parse(((Map) manager.ezGetBy(
										"SELECT wdate FROM week WHERE daynite='"
												+ dtime.getDepartClass()
														.substring(0, 2) + "'")
										.get(0)).get("wdate").toString());
							} catch (Exception e) {
								System.out.println(e);
							}
							c.setTime(d);
							week = manager
									.ezGetBy("SELECT week FROM Dtime_class WHERE Dtime_oid='"
											+ dtime.getOid()
											+ "' ORDER BY week ASC");
							first = 2;
							if (week.size() > 0) {
								first = Integer.parseInt(((Map) week.get(0))
										.get("week").toString()) + 1;
								if (first > 7) {
									first = 1;
								}
							}

							c.set(Calendar.DAY_OF_WEEK, first);
							d = c.getTime();
							out.println("<td align='center'>");
							out.println(sd.format(d));
							out.println();
							out.println("</td>");
							
							// 學生人數(男)
							out.println("<td align='center'>");
							out.println(summerManager
									.ezGetInt("SELECT COUNT(*)FROM Seld, stmd WHERE Seld.student_no=stmd.student_no and stmd.sex='1' and Seld.Dtime_oid='"
											+ dtime.getOid() + "'"));
							out.println("</td>");
							
							// 學生人數(女)
							out.println("<td align='center'>");
							out.println(summerManager
									.ezGetInt("SELECT COUNT(*)FROM Seld, stmd WHERE Seld.student_no=stmd.student_no and stmd.sex='2' and Seld.Dtime_oid='"
											+ dtime.getOid() + "'"));
							out.println("</td>");

							// 主要授課語言
							language = "國語";
							if ((((Map) dtimeList.get(i)).get("chiName2")
									.toString().indexOf("日語") >= 0 || ((Map) dtimeList
									.get(i)).get("chiName2").toString()
									.indexOf("日語") >= 0)) {
								language = "日語";
							} else if ((((Map) dtimeList.get(i))
									.get("chiName2").toString().indexOf("英語") >= 0 || ((Map) dtimeList
									.get(i)).get("chiName2").toString()
									.indexOf("英語") >= 0)) {
								language = "英語";
							}
							out.println("<td align='center'>");
							out.println(language);
							out.println("</td>");

							// 畢業班課程
							graduate = "否";
							if (dtime.getDepartClass().charAt(2) == dtime
									.getDepartClass().charAt(4)) {
								graduate = "是";
							}
							out.println("<td align='center'>");
							out.println(graduate);
							out.println("</td>");

							// 寒暑別
							out.println("<td align='center'>");
							out.println("無");
							out.println("</td>");

							// 全程使用外語
							out.println("<td align='center'>");
							out.println("否");
							out.println("</td>");

							// 身份識別種類
							out.println("<td align='center'>");
							if (dtime.getTechid() != null
									&& !dtime.getTechid().equals(""))
								out.println("I");
							out.println("</td>");

							// 身份識別號
							out.println("<td align='center'>");
							out.println(((Map) dtimeTeacherTmp.get(j))
									.get("teach_id"));
							out.println("</td>");

							// 授課時數
							out.println("<td align='center'>");
							out.println(dtime.getThour());
							out.println("</td>");

							// 系所代碼
							out.println("<td align='center'>");
							for (int k = 0; k < pecode9.length; k++) {

								// 若是碩班
								if (dtime.getDepartClass().indexOf("G") > -1) {
									if (((Map) pecode9[k]).get("id1")
											.toString().charAt(0) == dtime
											.getDepartClass().toString()
											.charAt(3)
											&& ((Map) pecode9[k]).get("id2")
													.equals("G")) {
										out.println(((Map) pecode9[k])
												.get("id"));
									}
								} else {

									if (((Map) pecode9[k]).get("id1")
											.toString().charAt(0) == dtime
											.getDepartClass().toString()
											.charAt(3)
											&& !((Map) pecode9[k]).get("id2")
													.equals("G")) {
										out.println(((Map) pecode9[k])
												.get("id"));
									}
								}
							}
							out.println("</td>");

							// 學制代碼
							school = manager
									.ezGetString("SELECT SchoolNo FROM Class WHERE ClassNo='"
											+ dtime.getDepartClass() + "'");
							out
									.println("<td align='center'>"
											+ manager
													.ezGetString("SELECT id FROM Pecode11 WHERE id1='"
															+ school + "'")
											+ "</td>");

							out.println("</tr>");
						}
					}
				}
			}catch(Exception e){
				System.out.println("王八蛋");
			}
			
		
			
			
		}
		out.println("</table>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</body>");

		out.println("</html>");
		out.close();
	}
}
