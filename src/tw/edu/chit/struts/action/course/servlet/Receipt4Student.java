package tw.edu.chit.struts.action.course.servlet;

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

/**
 * 付款單/收據
 * 
 * @author JOHN
 * 
 */
public class Receipt4Student extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		String schoolName = (String) context.getAttribute("SchoolName_ZH");

		String year = manager.getSchoolYear().toString();
		String term = manager.getSchoolTerm().toString();
		List allSelds = (List) session.getAttribute("allSelds");

		int payMoney = Integer.parseInt(session.getAttribute("payMoney")
				.toString());
		int payHour = Integer.parseInt(session.getAttribute("payHour")
				.toString());

		int extraPayMoney = Integer.parseInt(session.getAttribute("extraPay")
				.toString());

		int insurance = Integer.parseInt((String) session
				.getAttribute("insurance"));

		response.setHeader("Content-Disposition",
				"attachment;filename=Receipt4Student.doc");
		response.setContentType("application/vnd.ms-word; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out
				.println("<meta http-equiv=Content-Type content='text/html; charset=UTF-8'>");
		out.println("<meta name=ProgId content=Word.Document>");
		out.println("<meta name=Generator content='Microsoft Word 9'>");
		out.println("<meta name=Originator content='Microsoft Word 9'>");
		out.println("</head>");
		out.println("<body> ");

		int myHour;
		String Extrapay_Kind;
		List myAdcd;
		StringBuilder sb;
		boolean extrapay;
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf1 = new SimpleDateFormat("HH:mm");

		String today = manager.convertDate(sf.format(date));
		String now = sf1.format(date);
		String studentNo = null;
		String departClass = null;
		int myInsurance;
		for (int i = 0; i < allSelds.size(); i++) {
			studentNo = ((Map) allSelds.get(i)).get("student_no").toString();
			myHour = Integer.parseInt(((Map) allSelds.get(i)).get("sumThour").toString());// 取時數
			Extrapay_Kind=String.valueOf(((Map)allSelds.get(i)).get("Extrapay_Kind").toString());
			myInsurance = 0;
			extrapay = false;
			departClass = ((Map) allSelds.get(i)).get("ClassNo").toString();
			if (myHour > payHour || (payHour==myHour&&Extrapay_Kind=="Y")) {// 超過時數要付錢

				// 取電腦實習費
				// 若加選科目有
				if (manager.getExtrapay4Adcd(((Map) allSelds.get(i)).get(
						"student_no").toString(), term, 'A')) {
					
					myAdcd = manager
							.ezGetBy("SELECT a.Dtime_oid FROM AddDelCourseData a, Dtime d "
									+ "WHERE a.Dtime_oid=d.Oid AND "
									+ "d.Sterm='"
									+ term
									+ "' AND student_no='"
									+ studentNo + "'");

					sb = new StringBuilder(
							"SELECT COUNT(*) FROM Seld s, Dtime d WHERE s.Dtime_oid=d.Oid AND d.extrapay<>'0' AND "
									+ "d.Sterm='"
									+ term
									+ "' AND s.student_no='"
									+ studentNo
									+ "' AND d.Oid NOT IN(");
					for (int j = 0; j < myAdcd.size(); j++) {
						sb.append("'" + ((Map) myAdcd.get(j)).get("Dtime_oid")
								+ "', ");
					}
					sb.delete(sb.length() - 2, sb.length());
					sb.append(")");

					if (manager.ezGetInt(sb.toString()) < 1) {// 查到有電腦實習費要繳錢
						extrapay = true;
					}
					
					
				}


				for (int j = 1; j <= 3; j++) {

					out
							.println("<table width='100%' style='BORDER-bottom: #000000 2px dotted;'>");
					out.println("	<tr>");
					out.println("		<td>");
					out.println("		<table width='100%'>");
					out.println("			<tr>");
					out.println("				<td noWrap>");
					out.println("				<font size='+1' face='標楷體'>" + schoolName + " " + year + "學年度 第 " + term + "學期 學分費補繳收據</font>");
					out.println("				</td>");
					out.println("				<td width='200' align='right'>");

					if (j == 1) {
						out.println("				<font size='-2' face='標楷體'>學生存查(甲聯)</font>");
					}
					if (j == 2) {
						out.println("				<font size='-2' face='標楷體'>出納組存查(乙聯)</font>");
					}
					if (j == 3) {
						out.println("				<font size='-2' face='標楷體'>教務組存查(丙聯)</font>");
					}

					out.println("				</td>");
					out.println("			</tr>");
					out.println("		</table>");
					out.println("		</td>");
					out.println("	</tr>");
					out.println("	<tr>");
					out.println("		<td>");
					out.println("		<table style='BORDER-bottom: #000000 1px solid;'>");
					out.println("			<tr>");
					out.println("				<td noWrap><font face='標楷體'>班級:</font></td>");
					out.println("				<td width='200'>"+ ((Map) allSelds.get(i)).get("ClassName"));
					out.println("				</td>");
					out.println("				<td noWrap><font face='標楷體'>學號:</font></td>");
					out.println("				<td width='200'>" + studentNo);
					out.println("				</td>");
					out.println("				<td noWrap><font face='標楷體'>姓名:</font></td>");
					out.println("				<td width='200'>" + ((Map) allSelds.get(i)).get("student_name"));
					out.println("				</td>				");
					out.println("			</tr>");
					out.println("		</table>	");
					out.println("		");
					out.println("		</td>");
					out.println("	</tr>");
					out.println("	");
					out.println("	<tr>");
					out.println("		<td>");
					out.println("		<table width='100%'>");
					out.println("			<tr>");
					out.println("				<td width='50%'>");
					out.println("				<table>");
					out.println("					<tr>");
					out.println("						<td nowarp><font face='標楷體'>本班時數:</font></td>");
					out.println("						<td width='175'>" + payHour + "</td>");
					out.println("					</tr>");
					out.println("					<tr>");
					out.println("						<td nowarp><font face='標楷體'>選修時數:</font></td>");
					out.println("						<td>" + ((Map) allSelds.get(i)).get("sumThour") + "</td>");
					out.println("					</tr>");
					out.println("					<tr>");
					out.println("						<td nowarp><font face='標楷體'>電腦實習:</font></td>");

					//if (!extrapay) {
					if(extraPayMoney<0){
						if(Extrapay_Kind=="Y"){				
							out.println("						<td>" + -(extraPayMoney) + "</td>");
						} else {
							out.println("						<td>" + 0 + "</td>");
						}
					}else{
						if(Extrapay_Kind=="Y"){
							out.println("						<td>" + 0 + "</td>");
						} else {
							out.println("						<td>" + -(extraPayMoney) + "</td>");
						}
					}
					out.println("					</tr>");

					out.println("					<tr>");
					// System.out.println(departClass.substring(2, 3));
					// System.out.println(departClass.substring(4, 5));

					out.println("<td><font face='標楷體'>平安保險:</font></td>");

					int nowdept;
					if(departClass.length()==7) nowdept=Integer.parseInt(departClass.substring(5, 6).toString());
					else nowdept=Integer.parseInt(departClass.substring(4, 5).toString());
					
					/*
					if(!departClass.substring(2, 3).toString().equals("G")){
						if (nowdept > Integer.parseInt(departClass.substring(2, 3).toString())){
							myInsurance = insurance;
							out.println("<td>" + myInsurance + "</td>");
						} else {
							out.println("<td>0</td>");
						}
					}else {
						out.println("<td>" + myInsurance + "</td>");
					}
					*/
					myInsurance = insurance;
					out.println("<td>" + myInsurance + "</td>");
					
					out.println("					</tr>");

					out.println("					<tr>");
					out.println("						<td nowarp><font face='標楷體'>補繳金額:</font></td>");
					//if (!extrapay) {
					if(extraPayMoney<0){
						if(Extrapay_Kind=="Y"){
							out.println("						<td>" + ((myHour - payHour) * payMoney	- extraPayMoney + myInsurance) + "</td>");
						} else {
							out.println("						<td>" + ((myHour - payHour) * payMoney + myInsurance)+"</td>");
						}
					}else{
						if(Extrapay_Kind=="Y"){
							out.println("						<td>" + ((myHour - payHour) * payMoney + myInsurance)+ "</td>");
						} else {
							out.println("						<td>" + ((myHour - payHour) * payMoney	- extraPayMoney + myInsurance) + "</td>");
						}
					}

					out.println("					</tr>");

					out.println("				</table>");
					out.println("				</td>				");
					out.println("				");
					out.println("				<td width='50%' valign='top'>");
					out.println("				<table width='95%' cellpadding='0' cellspacing='1' bgColor='#000000' align=left>");
					out.println("					<tr bgColor='#ffffff'>");
					out.println("						<td width='50%' align='center'><font face='標楷體'>出納組</font></td>");
					out.println("						<td width='50%' align='center'><font face='標楷體'>教務組</font></td>");
					out.println("					</tr>");
					out.println("					<tr height='160' bgColor='#ffffff'>");
					out.println("						<td></td>");
					out.println("						<td></td>");
					out.println("					</tr>");
					out.println("				</table>");
					out.println("				</td>");
					out.println("			</tr>");
					out.println("		</table>");

					out.println("<div align='right'><font  size='1'>課程管理系統" + today + " " + now + "</font></div>");
					out.println("		</td>");
					out.println("	</tr>");
					out.println("</table>");

				}

				if (i != allSelds.size() - 1) {
					out.println("<br/>");
					out.println("<br/>");
					out.println("<br/>");
					//out.println("<br/>");
					out.println("<br clear='all' style='page-break-before:always;'/>");
				}

			}
		}

		out.println("</body>");
		out.println("</html>");
		out.close();
	}

}
