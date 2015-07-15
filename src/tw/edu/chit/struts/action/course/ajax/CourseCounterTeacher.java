package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * 教師科目對照表
 * @author JOHN
 *
 */
public class CourseCounterTeacher extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		HttpSession session = request.getSession(false);
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		response.setContentType("application/vnd.ms-excel");
		response.setContentType("text/html; charset=utf-8");
		
		response.setHeader("Content-disposition","attachment;filename=ooxx.xls");
		PrintWriter out=response.getWriter();
		
		List dtimeList=(List)session.getAttribute("dtimeList");
		//String sterm=manager.getSchoolTerm().toString();
		String sterm=session.getAttribute("xterm").toString();
		StringBuffer strbuf=new StringBuffer("SELECT d.techid, e.cname FROM Dtime d, empl e WHERE " +
				//"d.Sterm='"+sterm+"' AND e.idno=d.techid AND d.Oid IN(");
				"e.idno=d.techid AND d.Oid IN(");
				for(int i=0; i<dtimeList.size(); i++){
					strbuf.append("'"+((Map)dtimeList.get(i)).get("oid")+"',");
				}
				strbuf.delete(strbuf.length()-1, strbuf.length());
				strbuf.append(") GROUP BY d.techid");
				
				List cscodeList=manager.ezGetBy(strbuf.toString());
				
				//String schoolTerm=manager.getSchoolTerm().toString();
				
				//System.out.println(strbuf);
				
				for(int i=0; i<cscodeList.size(); i++){		
					
					
					List myCs=manager.ezGetBy("SELECT cl.ClassName, c.chi_name FROM " +
							"((Dtime d LEFT OUTER JOIN Class cl ON d.depart_class=cl.ClassNo)LEFT OUTER JOIN " +
							"Csno c ON d.cscode=c.cscode) WHERE d.Sterm='"+sterm+"' AND d.techid='"+((Map)cscodeList.get(i)).get("techid")+"'");
					
					out.println("<table border='1' align='left'>");
					out.println("<tr>");
					out.println("<td colspan='2' style='mso-number-format:\\@' nowrap>教師姓名:"+((Map)cscodeList.get(i)).get("cname")+"</td>");
					out.println("</tr>");
					
					int tmp=0;
					
					for(int j=0; j<myCs.size(); j++){
						
						if(j>=tmp){
							tmp=tmp+2;
						out.println("<tr>");
						}
						
						out.println("<td align='right' style='mso-number-format:\\@' nowrap>"+((Map)myCs.get(j)).get("ClassName")+"</td>");
						out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)myCs.get(j)).get("chi_name")+"</td>");
						
						if(j>=tmp){					
							out.println("</tr>");
						}
					}
					
					out.println("</table>");
					
					out.println("<br>");
				}
				
				out.close();
		
	}

}
