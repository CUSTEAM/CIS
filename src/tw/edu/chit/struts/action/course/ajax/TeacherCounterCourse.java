package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import tw.edu.chit.service.SummerManager;

/**
 * 科目教師對照表
 * @author JOHN
 *
 */
public class TeacherCounterCourse extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		HttpSession session = request.getSession(false);
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		SummerManager summerManager=(SummerManager) ctx.getBean("summerManager");
		response.setContentType("text/html; charset=BIG5");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=TeacherCounterCourse.xls");
		PrintWriter out=response.getWriter();
		
		List dtimeList=(List)session.getAttribute("dtimeList");
		String sterm=manager.getSchoolTerm().toString();
		
		StringBuilder sb=new StringBuilder("SELECT d.Oid, c.chi_name, d.credit, d.thour, cl.ClassName, e.cname FROM Dtime d, Csno c, Class cl, empl e " +
				"WHERE d.cscode=c.cscode AND cl.ClassNo=d.depart_class AND e.idno=d.techid AND d.cscode NOT IN('50000', 'T0001', 'T0002') AND " +
				"d.Oid IN(");
		
		for(int i=0; i<dtimeList.size(); i++){
			sb.append("'"+((Map)dtimeList.get(i)).get("oid")+"',");
		}
		sb.delete(sb.length()-1, sb.length());
		sb.append(") ORDER BY c.chi_name");
		
		List newDtimeList=manager.ezGetBy(sb.toString());
		List list=new ArrayList();
		Map map;
		
		for(int i=0; i<newDtimeList.size(); i++){
			map=new HashMap();
			map.put("chi_name", ((Map)newDtimeList.get(i)).get("chi_name"));
			map.put("credit", ((Map)newDtimeList.get(i)).get("credit"));
			map.put("thour", ((Map)newDtimeList.get(i)).get("thour"));
			map.put("ClassName", ((Map)newDtimeList.get(i)).get("ClassName"));
			
			StringBuffer teachers=new StringBuffer(((Map)newDtimeList.get(i)).get("cname").toString());
			map.put("cname", ((Map)newDtimeList.get(i)).get("cname"));
			
			if(summerManager.ezGetInt("SELECT COUNT(*)FROM Dtime_teacher WHERE Dtime_oid='"+((Map)newDtimeList.get(i)).get("Oid")+"'")>0){
				List tmp=manager.ezGetBy("SELECT e.cname FROM Dtime_teacher dt, empl e " +
						"WHERE dt.teach_id=e.idno AND dt.Dtime_oid='"+((Map)newDtimeList.get(i)).get("Oid")+"'");
				for(int j=0; j<tmp.size(); j++){
					teachers.append(", "+((Map)tmp.get(j)).get("cname").toString());
				}
				map.put("cname", teachers);
			}	
			list.add(map);
		}
		
		out.println("<table border='0'>");
		out.println("<tr>");
		out.println("<td>");
		
		
		
		/*
		SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String date=sf.format(new Date());

		out.println("");
		out.println("<table border='0' align='left' cellpadding='0' cellspacing='1' width='100%'>");
		out.println("	<tr>");
		out.println("		<td colspan='5' align='center'>");
		out.println("		<font face='標楷體' size='+2'>科目教師對照</font>");
		out.println("		</td>");
		out.println("	</tr>");
		out.println("	<tr>");
		out.println("		<td colspan='5' align='right'>");
		out.println("		<font size='-2'>課程管理系統 "+date+"</font>");
		out.println("		</td>");
		out.println("	</tr>");
		out.println("</table>");
		*/
		
		
		
		
		
		
		
		
		
		out.println("<table border='1'>");
		out.println("<tr bgcolor='#f0fcd7'>");
		out.println("<td>科目名稱</td>");
		out.println("<td>學分</td>");
		out.println("<td>時數</td>");
		out.println("<td>開課班級</td>");
		out.println("<td>任課教師</td>");
		out.println("</tr>");
		for(int i=0; i<list.size(); i++){
			if(i%2==1){
				out.println("<tr bgcolor='#f0fcd7'>");
			}else{
				out.println("<tr>");
			}			
			out.println("<td style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("chi_name")+"</td>");
			out.println("<td style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("credit")+"</td>");
			out.println("<td style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("thour")+"</td>");
			out.println("<td style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("ClassName")+"</td>");
			out.println("<td style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("cname")+"</td>");
			out.println("</tr>");
			
		}
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.close();
	}

}
