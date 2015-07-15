package tw.edu.chit.struts.action.course.ajax;

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
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;

/**
 * 技職
 * 
 * @author JOHN
 * 
 */
public class List4DtimeG extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=list4DtimeG.xls");
		HttpSession session = request.getSession(false);
		// Object pecode9[]=manager.getPecode9().toArray();
		// Object pecode11[]=manager.getPecode11().toArray();

		Date d = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
		String date = sf.format(d);
		UserCredential user = (UserCredential) session
				.getAttribute("Credential");

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		out.println("<table>");
		// out.println("<c:if test='${not empty dtimeList}'>"); //???
		out.println("<tr>");
		out.println("<td>");

		// SimpleDateFormat s=new SimpleDateFormat("yyyy/MM/dd HH:mm");
		// String e1=s.format(new Date());
		/*
		 * out.println(""); out.println("<table border='0' align='left'
		 * cellpadding='0' cellspacing='1' width='100%'>"); out.println(" <tr>");
		 * out.println(" <td colspan='41' align='center'>"); out.println("
		 * <font face='標楷體' size='+2'>技職</font>"); out.println(" </td>");
		 * out.println(" </tr>"); out.println(" <tr>"); out.println("
		 * <td colspan='41' align='right'>"); out.println(" <font
		 * size='-2'>課程管理系統 "+e1+"</font>"); out.println(" </td>");
		 * out.println(" </tr>"); out.println("</table>");
		 */

		out.println("<table width='100%' border='1'>");
		out.println("");
		out.println("<tr bgcolor='#f0fcd7'>");
		out.println("<td>ID</td>");
		out.println("<td align='center'>年度</td>");
		out.println("<td align='center'>學期</td>");
		out.println("<td align='center'>職類</td>");
		out.println("<td align='center'>學程</td>");
		out.println("<td align='center'>科目類別</td>");
		out.println("<td align='center'>部別</td>");
		out.println("<td align='center'>學制 </td>");
		out.println("<td align='center'>課程名稱</td>");
		out.println("<td align='center'>科系</td>");
		out.println("<td align='center'>組別</td>");
		out.println("<td align='center'>部校訂</td>");
		out.println("<td align='center'>半全年</td>");
		out.println("<td align='center'>必選修</td>");
		out.println("<td align='center'>學分數</td>");
		out.println("<td align='center'>上課時數</td>");
		out.println("<td align='center'>實習時數</td>");
		out.println("<td align='center'>年級</td>");
		out.println("<td align='center'>班級</td>");
		out.println("<td align='center'>課程摘要</td>");
		out.println("<td align='center'>課程大綱</td>");
		out.println("<td align='center'>課程連結</td>");
		out.println("<td align='center'>開課老師</td>");
		out.println("<td align='center'>備註</td>");
		out.println("<td align='center'>已選人數</td>");
		out.println("<td align='center'>填表時間</td>");
		out.println("<td align='center'>上課地點</td>");
		out.println("<td align='center'>填表人</td>");
		out.println("<td align='center'>授課語言1</td>");
		out.println("<td align='center'>授課語言2</td>");
		out.println("<td align='center'>報部文號</td>");
		out.println("<td align='center'>證照1</td>");
		out.println("<td align='center'>證照2</td>");
		out.println("<td align='center'>教育部承辦人</td>");
		out.println("<td align='center'>學校承辦人</td>");
		out.println("<td align='center'>科系代碼</td>");
		out.println("<td align='center'>英文課程名稱</td>");
		out.println("<td align='center'>管轄單位</td>");
		out.println("<td align='center'>回文文號</td>");
		out.println("<td align='center'>全外語教學</td>");
		out.println("<td align='center'>教學型態</td>");
		out.println("<td align='center'>師資來源</td>");
		out.println("<td align='center'>男</td>");
		out.println("<td align='center'>女</td>");
		out.println("<td align='center'>科系英文名稱</td>");
		out.println("</tr>");

		List dtimeList = (List) session.getAttribute("dtimeList");
		List csGroup;
		StringBuilder sb;
		List dtimeTeacher;
		for (int i = 0; i < dtimeList.size(); i++) {

			// 不可以是班會或通識
			if (!((Map) dtimeList.get(i)).get("cscode").equals("50000")
					&& !((Map) dtimeList.get(i)).get("cscode").equals("T0001")
					&& !((Map) dtimeList.get(i)).get("cscode").equals("T0002")) {

				String departClass = ((Map) dtimeList.get(i))
						.get("departClass").toString();
				String tmpDepartClass = departClass.toString().substring(0, 3);

				String schoolTerm; // 學期轉型
				if (manager.getSchoolTerm() == 1) {
					schoolTerm = "0";
				} else {
					schoolTerm = "1";
				}
				// 職類
				char dept = ((Map) manager.ezGetBy(
						"SELECT DeptNo FROM Class WHERE ClassNo='"
								+ departClass + "'").get(0)).get("DeptNo")
						.toString().charAt(0);
				// System.out.println(dept);
				String pro = "15";
				try {
					pro = manager
							.ezGetString("SELECT id5 FROM Pecode9 WHERE id1='"
									+ dept + "' LIMIT 1");
				} catch (Exception e) {
					pro = "?";
				}

				// 部制
				String schoolName = ((Map) dtimeList.get(i)).get("departClass")
						.toString().substring(1, 3);
				String schoolType = "";
				if (schoolName.equals("12")) {
					schoolName = "0";
					schoolType = "1002";
				} else if (schoolName.equals("15")) {
					schoolType = "1003";
					schoolName = "0";
				} else if (schoolName.equals("1G")) {
					schoolType = "1006";
					schoolName = "0";
				} else if (schoolName.equals("22")) {
					schoolType = "1002";
					schoolName = "1";
				} else if (schoolName.equals("32")) {
					schoolName = "2";
					schoolType = "1002";
				} else if (schoolName.equals("42")) {
					schoolType = "1001";
					schoolName = "0";
				} else if (schoolName.equals("52")) {
					schoolType = "1001";
					schoolName = "1";
				} else if (schoolName.equals("54")) {
					schoolType = "1005";
					schoolName = "1";
				} else if (schoolName.equals("64")) {
					schoolType = "1005";
					schoolName = "0";
				} else if (schoolName.equals("72")) {
					schoolType = "1001";
					schoolName = "2";
				} else if (schoolName.equals("82")) {
					schoolType = "1001";
					schoolName = "1";
				} else if (schoolName.equals("8G")) {
					schoolType = "1006";
					schoolName = "1";
				} else if (schoolName.equals("92")) {
					schoolType = "1002";
					schoolName = "1";
				}

				if (i % 2 == 1) {
					out.println("<tr bgcolor='#f0fcd7'>");
				} else {
					out.println("<tr>");
				}

				out.print("<td>" + ((Map) dtimeList.get(i)).get("oid")
						+ "</td>");// 流水號囧?
				out.print("<td align='center'>" + manager.getSchoolYear()
						+ "</td>");// 學年
				out.print("<td align='center'>" + schoolTerm + "</td>");// 學期
				out.print("<td align='center'>" + pro + "</td>");// 職類

				csGroup = manager
						.ezGetBy("SELECT cg.cname FROM CsGroupSet cgs, CsGroup cg WHERE "
								+ "cgs.group_oid=cg.Oid AND cgs.cscode='"
								+ ((Map) dtimeList.get(i)).get("cscode")
								+ "' GROUP BY cg.Oid");
				sb = new StringBuilder();
				if (csGroup.size() > 0) {
					for (int j = 0; j < csGroup.size(); j++) {
						sb.append(((Map) csGroup.get(j)).get("cname") + ", ");
					}
				}
				out.print("<td align='left'>" + sb + "</td>");

				out.print("<td align='center'></td>");

				out.print("<td align='center'>" + schoolName + "</td>");// 部制
				out.print("<td align='center'>" + schoolType + "</td>");// 學制
				out.print("<td>" + ((Map) dtimeList.get(i)).get("chiName3")
						+ "</td>");// 課程名稱

				out
						.print("<td align='center'>"
								+ manager
										.ezGetString("SELECT fname FROM dept WHERE no='"
												+ departClass.substring(0, 4)
												+ "'") + "</td>");

				out.print("<td align='center'></td>");// 組別
				out.print("<td align='center'>1</td>");// 校部訂

				String allYear = "0";
				String cscode = ((Map) dtimeList.get(i)).get("cscode")
						.toString();
				if (cscode.charAt(cscode.length() - 1) != '0') {
					allYear = "1";
				}
				// 有連號的課程為全年
				out.print("<td align='center'>" + allYear + "</td>");// 全半年
				if (((Map) dtimeList.get(i)).get("opt2").equals("必修")) {
					// System.out.println(((Map)dtimeList.get(i)));
					// if(((Map)dtimeList.get(i)).get("opt").equals("1")){
					out.print("<td align='center'>0</td>");
				} else {
					out.print("<td align='center'>1</td>");
				}

				out.print("<td align='center'>"
						+ ((Map) dtimeList.get(i)).get("credit") + "</td>");
				out.print("<td align='center'>"
						+ Float.parseFloat(((Map) dtimeList.get(i))
								.get("thour").toString()) + "</td>");
				boolean practical = false;
				if (((Map) dtimeList.get(i)).get("chiName2").toString()
						.indexOf("實習") == -1) {
					out.print("<td align='center'>0</td>");
				} else {
					out.print("<td>"
							+ Float.parseFloat(((Map) dtimeList.get(i)).get(
									"thour").toString()) * 18 + "</td>");
					practical = true;
				}

				out.print("<td align='center'>"
						+ ((Map) dtimeList.get(i)).get("departClass")
								.toString().charAt(4) + "</td>");
				out.print("<td align='center'>"
						+ ((Map) dtimeList.get(i)).get("departClass")
								.toString().charAt(5) + "</td>");
				out
						.print("<td nowrap>http://www.cust.edu.tw/www/info/intro_en.php?coursenum="
								+ ((Map) dtimeList.get(i)).get("oid") + "</td>");
				out
						.print("<td nowrap>http://www.cust.edu.tw/www/info/intro_en.php?coursenum="
								+ ((Map) dtimeList.get(i)).get("oid") + "</td>");
				out
						.print("<td nowrap>http://www.cust.edu.tw/www/info/intro_en.php?coursenum="
								+ ((Map) dtimeList.get(i)).get("oid") + "</td>");

				csGroup = manager
						.ezGetBy("SELECT cg.cname FROM CsGroupSet cgs, CsGroup cg WHERE "
								+ "cgs.group_oid=cg.Oid AND cgs.cscode='"
								+ ((Map) dtimeList.get(i)).get("cscode")
								+ "' GROUP BY cg.Oid");
				sb = new StringBuilder();
				if (csGroup.size() > 0) {
					for (int j = 0; j < csGroup.size(); j++) {
						sb.append(((Map) csGroup.get(j)).get("cname") + ", ");
					}
				}

				sb = new StringBuilder();
				dtimeTeacher = manager
						.ezGetBy("SELECT e.cname FROM empl e, Dtime_teacher d WHERE e.idno=d.teach_id AND d.Dtime_oid='"
								+ ((Map) dtimeList.get(i)).get("oid") + "'");
				if (dtimeTeacher.size() > 0) {
					for (int j = 0; j < dtimeTeacher.size(); j++) {
						sb.append(((Map) dtimeTeacher.get(j)).get("cname")
								+ ", ");
					}
					out.print("<td align='center'>"
							+ ((Map) dtimeList.get(i)).get("techName") + ", "
							+ sb.toString() + "</td>");
				} else {
					out.print("<td align='center'>"
							+ ((Map) dtimeList.get(i)).get("techName")
							+ "</td>");
				}

				out.print("<td align='center'></td>");
				// out.print("<td
				// align='center'>"+((Map)dtimeList.get(i)).get("Select_Limit")+"</td>");
				out.print("<td align='center'>"+ manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE Dtime_oid='"
												+ ((Map) dtimeList.get(i))
														.get("oid") + "'")
								+ "</td>");
				out.print("<td align='center' nowrap>" + date + "</td>");

				String place = "台北校區";
				char area = departClass.charAt(0);
				// System.out.println("area="+area);
				if (area == '2') {
					place = "新竹校區";
				}
				out.print("<td align='center' nowrap>" + place + "</td>");
				out.print("<td align='center' nowrap>"
						+ user.getMember().getName() + "</td>");

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
				out.print("<td align='center' nowrap>" + language + "</td>");
				String language1 = "";
				if (!language.equals("國語")) {
					language1 = "國語";
				}
				out.print("<td align='center' nowrap>" + language1 + "</td>");
				out.print("<td align='center'></td>");
				out.print("<td align='center'></td>");
				out.print("<td align='center'></td>");
				out.print("<td align='center'>汪佳佩</td>");
				out.print("<td align='center'>" + user.getMember().getName()+ "</td>");
				out.print("<td align='center'>" + dept + "</td>");
				out.print("<td nowrap>"+ ((Map) dtimeList.get(i)).get("eng_name") + "</td>");
				out.print("<td align='center'>技職司</td>");
				out.print("<td align='center'></td>");
				out.print("<td align='center'>0</td>");

				String type = "1";
				if (((Map) dtimeList.get(i)).get("elearningName").equals("遠距")) {
					type = "4";
				}
				if (((Map) dtimeList.get(i)).get("elearningName").equals("輔助")) {
					type = "7";
				}
				if (practical) {
					type = "2";
				}

				out.print("<td align='center'>" + type + "</td>");
				if (manager.ezGetString("SELECT unit FROM empl WHERE idno='"+((Map)dtimeList.get(i)).get("techid")+"'").equals("121")) {
					out.println("<td align='center'>2</td>");
				} else {
					out.println("<td align='center'>1</td>");
				}

				// System.out.println(((Map)dtimeList.get(i)));

				/*
				 * if(
				 * ((Map)dtimeList.get(i)).get("chiName2").toString().indexOf("性")>=0//||
				 * //((Map)dtimeList.get(i)).get("chiName2").toString().indexOf("性別")>=0||
				 * //((Map)dtimeList.get(i)).get("chiName2").toString().indexOf("男性")>=0||
				 * //((Map)dtimeList.get(i)).get("chiName2").toString().indexOf("女性")>=0||
				 * //((Map)dtimeList.get(i)).get("chiName2").toString().indexOf("性教")>=0) ){
				 */
				out.println("<td align='center'>"+ manager.ezGetInt("SELECT COUNT(*)FROM Seld s, Dtime d, stmd st WHERE "
				+ "s.Dtime_oid=d.Oid AND s.student_no=st.student_no AND st.sex='1' AND d.Oid='"
				+ ((Map) dtimeList.get(i)).get("oid") + "'")
								+ "</td>");
				out.println("<td align='center'>"
								+ manager
										.ezGetInt("SELECT COUNT(*)FROM Seld s, Dtime d, stmd st WHERE "
												+ "s.Dtime_oid=d.Oid AND s.student_no=st.student_no AND st.sex='2' AND d.Oid='"
												+ ((Map) dtimeList.get(i))
														.get("oid") + "'")
								+ "</td>");
				out.println("<td align='center'>"
						+ manager.ezGetString("Select dp.engname From dept dp, Dtime d, Class c " +
								           "Where d.depart_class=c.ClassNo And c.Dept=dp.no " +
								           "  And d.Oid='"+ ((Map) dtimeList.get(i)).get("oid") + "'")
						+ "</td>");

				/*
				 * }else{ out.println("<td align='center'>-</td>");
				 * out.println("<td align='center'>-</td>"); }
				 * 
				 */

				out.println("	</tr>");
			}

		}

		out.println("</table>");
		out.println("</body>");

		out.println("</html>");
		out.close();
	}
}
