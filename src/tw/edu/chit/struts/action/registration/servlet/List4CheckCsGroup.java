package tw.edu.chit.struts.action.registration.servlet;

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

import tw.edu.chit.service.CourseManager;

public class List4CheckCsGroup extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		HttpSession session = request.getSession(false);
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=utf8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=List4Student.xls");
		
		PrintWriter out=response.getWriter();
		
		out.println("<table width='100%' border='1'>");
		out.println("<tr>");		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>學號</td>");//問題
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");//問題
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>性別</td>");//問題
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>學程名稱</td>");		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>必修應修</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>選修應修</td>");		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>外系應修</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>必修已得</td>");		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>選修已得</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>外系已得</td>");	
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>狀況</td>");	
		out.println("</tr>");
		
		List list=(List)session.getAttribute("relult");
		Map std;
		for(int i=0; i<list.size(); i++){
			
			std=manager.ezGetMap("SELECT s.student_name, s.sex, c.ClassName FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.student_no='"+((Map)list.get(i)).get("student_no")+"'");
			
			out.println("<tr>");		
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+std.get("ClassName")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("student_no")+"</td>");//問題
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+std.get("student_name")+"</td>");//問題
			
			
			if(std.get("sex").equals("1")){
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>男</td>");//問題
			}else{
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>女</td>");//問題
			}			
			
			out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("cname")+"</td>");		
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("major")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("minor")+"</td>");		
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("outdept")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("opt1")+"</td>");		
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("opt2")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("optOut")+"</td>");
			
			if(((Boolean)((Map)list.get(i)).get("igot")).booleanValue()){
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>已取得</td>");
			}else{
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>進修中</td>");
			}
			
			
			out.println("</tr'>");
		}		
		out.println("</table>");
		out.close();
	}
}
