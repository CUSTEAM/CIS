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

import tw.edu.chit.service.CourseManager;

public class TeacherCounterTime extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		HttpSession session = request.getSession(false);
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		response.setContentType("text/html; charset=BIG5");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=TeacherCounterTime.xls");
		List dtimeList=(List)session.getAttribute("dtimeList");
		StringBuffer strbuf=new StringBuffer("SELECT sum(d.thour) total, e.cname " +
				"FROM Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno WHERE d.Oid IN(");
		// Leo20120402 因出表時total出現亂碼，將此段與法修為程式碼32~33行		
		//StringBuffer strbuf=new StringBuffer("SELECT ifnull(sum(thour),0)AS total, e.cname " +
		//		"FROM Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno WHERE d.Oid IN(");
		for(int i=0; i<dtimeList.size(); i++){
			strbuf.append("'"+((Map)dtimeList.get(i)).get("oid")+"',");
		}		
		strbuf.delete(strbuf.length()-1, strbuf.length());
		strbuf.append(")GROUP BY d.techid");
		List myDtimeList=manager.ezGetBy(strbuf.toString());		
		PrintWriter out=response.getWriter();
		
		//SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
		//String date=sf.format(new Date());

		
		out.println("<table>");
		out.println("<tr>");
		out.println("<td>");
		
		out.println("<table width='100%' border='1'>");
		out.println("<tr bgcolor='#f0fcd7'>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>時數</td>");
		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>時數</td>");
		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>時數</td>");
		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>時數</td>");
		
		out.println("</tr>");
		int tmp=0;
		for(int i=0; i<myDtimeList.size(); i++){			
			if(i>=tmp){
				tmp=tmp+4;
				out.println("<tr>");
			}
			if(((Map)myDtimeList.get(i)).get("cname")==null){
				out.println("<td align='center' style='mso-number-format:\\@'>未排定</td>");//name
			}else{
				out.println("<td align='center' style='mso-number-format:\\@'>"+((Map)myDtimeList.get(i)).get("cname")+"</td>");//name
			}
			
			out.println("<td align='center' style='mso-number-format:\\@'>"+((Map)myDtimeList.get(i)).get("total")+"</td>");//total thour
			//System.out.println("tr");
			
			if(i>=tmp){
				out.println("</tr>");
			}
						
		}
		out.println("</table>");
		
		
		
		
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.flush();
		out.close();
	}
}
