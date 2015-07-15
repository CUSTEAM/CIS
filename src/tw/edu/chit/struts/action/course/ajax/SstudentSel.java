package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class SstudentSel extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=list4SummerPigs.xls");
		List tmpSdtime=manager.ezGetBy("SELECT depart_class, cscode FROM Sdtime WHERE Oid="+request.getParameter("Oid"));
		
		String departClass=((Map)tmpSdtime.get(0)).get("depart_class").toString();
		String cscode=((Map)tmpSdtime.get(0)).get("cscode").toString();
		
		List stuList=manager.ezGetBy("SELECT sa.name, s.student_no, st.student_name, cl.classNo, cl.className, " +
				"d.depart_class, c.cscode,c.chi_name, d.credit, d.thour " +
				"FROM Sseld s, stmd st, Class cl, Csno c, Sdtime d, Sabbr sa WHERE " +
				"s.student_no=st.student_no AND sa.no=d.depart_class AND " +
				"st.depart_class=cl.ClassNo AND " +
				"c.cscode=d.cscode AND d.cscode=s.cscode AND d.depart_class=s.csdepart_class AND " +
				"s.csdepart_class='"+departClass+"' AND c.cscode='"+cscode+"'");
		PrintWriter out=response.getWriter();

		out.println("<table>");
		out.println("	<tr>");
		out.println("		<td>");
		out.println("			<table border='0' align='left' cellpadding='0' cellspacing='1' bgcolor='#CFE69F' width='100%'>");
		out.println("				");
		out.println("				<tr height='20'>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學生學號</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學生姓名</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學生班級代碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學生所屬班級</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>開課班級代碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>開課班級名稱</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>課程代碼</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>課程名稱</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>學分</td>");
		out.println("					<td align='center' bgcolor='#f0fcd7'>時數</td>");
		out.println("				</tr>");
		for(int i=0; i<stuList.size(); i++){
			out.println("				<tr height='20'>");
			out.println("					<td align='center' style='mso-number-format:\\@'");
			if(i%2==1){
				out.print("bgcolor='#f0fcd7'>");
				}else{
				out.print("bgcolor='ffffff'>");
				}
			out.print(((Map)stuList.get(i)).get("student_no")+"</td>");
			out.println("					<td align='center'");
			if(i%2==1){
				out.print("bgcolor='#f0fcd7'>");
				}else{
				out.print("bgcolor='ffffff'>");
				}
			out.print(((Map)stuList.get(i)).get("student_name")+"</td>");
			out.println("					<td align='center' style='mso-number-format:\\@'");
			if(i%2==1){
				out.print("bgcolor='#f0fcd7'>");
				}else{
				out.print("bgcolor='ffffff'>");
				}
			out.print(((Map)stuList.get(i)).get("ClassNo")+"</td>");
			out.println("					<td align='center'");
			if(i%2==1){
				out.print("bgcolor='#f0fcd7'>");
				}else{
				out.print("bgcolor='ffffff'>");
				}
			out.print(((Map)stuList.get(i)).get("ClassName")+"</td>");
			out.println("					<td align='center'");
			if(i%2==1){
				out.print("bgcolor='#f0fcd7'>");
				}else{
				out.print("bgcolor='ffffff'>");
				}
			out.print(((Map)stuList.get(i)).get("depart_class")+"</td>");
			out.println("					<td align='center'");
			if(i%2==1){
				out.print("bgcolor='#f0fcd7'>");
				}else{
				out.print("bgcolor='ffffff'>");
				}
			out.print(""+((Map)stuList.get(i)).get("name")+"</td>");
			out.println("					<td align='center'");
			if(i%2==1){
				out.print("bgcolor='#f0fcd7'>");
				}else{
				out.print("bgcolor='ffffff'>");
				}
			out.print(((Map)stuList.get(i)).get("cscode")+"</td>");
			out.println("					<td align='center'");
			if(i%2==1){
				out.print("bgcolor='#f0fcd7'>");
				}else{
				out.print("bgcolor='ffffff'>");
				}
			out.print(((Map)stuList.get(i)).get("chi_name")+"</td>");
			out.println("					<td align='center'");
			if(i%2==1){
				out.print("bgcolor='#f0fcd7'>");
				}else{
				out.print("bgcolor='ffffff'>");
				}
			out.print(((Map)stuList.get(i)).get("credit")+"</td>");
			out.println("					<td align='center'");
			if(i%2==1){
				out.print("bgcolor='#f0fcd7'>");
				}else{
				out.print("bgcolor='ffffff'>");
				}
			out.print(((Map)stuList.get(i)).get("thour")+"</td>");
			out.println("				</tr>");
		}
		out.println("						</table>");
		out.close();
	}

}
