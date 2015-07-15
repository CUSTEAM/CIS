package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

public class LeaveHistList extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		HttpSession session = request.getSession(false);
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=List4Student.xls");
		
		List myHist;
		StringBuilder sb;
		List list=(List)session.getAttribute("students");
		PrintWriter out=response.getWriter();
		
		out.println("<table border='1'>  ");
		
		out.println("<tr>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級代碼</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級名稱</td>");
		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>科系</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>年級</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>學號</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>性別</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>入學</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>修課學期</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>歷程</td>");
		
		out.println("	</tr>");
		
		
		
		Map map;
		List tmp;
		for(int i=0; i<list.size(); i++){
			map=new HashMap();
			try{
				map=(Map)manager.ezGetBy("SELECT s.*, c.ClassName, c.DeptNo, c.Grade, c5.name, c51.name as team, c3.name as county, " +
						"c31.name as province, c52.name as status_name, c53.name as caurse_name, " +
						"c54.name as ident_name2 FROM " +
						"(((((((Gstmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo)LEFT OUTER JOIN " +
						"code5 c5 ON c5.idno=s.ident AND c5.category='Identity')LEFT OUTER JOIN " +
						"code5 c51 ON c51.idno=s.divi AND c51.category='GROUP')LEFT OUTER JOIN " +
						"code3 c3 ON s.birth_county=c3.no)LEFT OUTER JOIN " +
						"code3 c31 ON c31.no=s.birth_province)LEFT OUTER JOIN " +
						"code5 c52 ON s.occur_status=c52.idno AND c52.category='Status')LEFT OUTER JOIN " +
						"code5 c53 ON s.occur_cause=c53.idno AND c53.category='Cause')LEFT OUTER JOIN " +
						"code5 c54 ON s.ident_basic=c54.idno AND c53.category='Identity' WHERE student_no='"+((Map)list.get(i)).get("student_no")+"'").get(0);
				
			}catch(Exception e){
				map=(Map)manager.ezGetBy("SELECT s.*, c.ClassName, c.DeptNo, c.Grade, c5.name, c51.name as team, c3.name as county, " +
						"c31.name as province, c52.name as status_name, c53.name as caurse_name, " +
						"c54.name as ident_name2 FROM " +
						"(((((((stmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo)LEFT OUTER JOIN " +
						"code5 c5 ON c5.idno=s.ident AND c5.category='Identity')LEFT OUTER JOIN " +
						"code5 c51 ON c51.idno=s.divi AND c51.category='GROUP')LEFT OUTER JOIN " +
						"code3 c3 ON s.birth_county=c3.no)LEFT OUTER JOIN " +
						"code3 c31 ON c31.no=s.birth_province)LEFT OUTER JOIN " +
						"code5 c52 ON s.occur_status=c52.idno AND c52.category='Status')LEFT OUTER JOIN " +
						"code5 c53 ON s.occur_cause=c53.idno AND c53.category='Cause')LEFT OUTER JOIN " +
						"code5 c54 ON s.ident_basic=c54.idno AND c53.category='Identity' WHERE student_no='"+((Map)list.get(i)).get("student_no")+"'").get(0);
			}
			
			myHist=manager.ezGetBy("SELECT * FROM QuitResume WHERE student_no='"+((Map)list.get(i)).get("student_no")+"'");
			
			if(myHist.size()>0){
				
				out.println("<tr>");
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+map.get("depart_class")+"</td>");
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+map.get("ClassName")+"</td>");
				
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+map.get("DeptNo")+"</td>");
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+map.get("Grade")+"</td>");
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+map.get("student_name")+"</td>");
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+map.get("student_no")+"</td>");
				
				if(map.get("sex").equals("1")){
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>男</td>");
				}else{
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>女</td>");
				}				
				
				out.println("<td align='center' style='mso-number-format:\\@'>"+map.get("entrance")+"</td>");				
				tmp=manager.ezGetBy("SELECT COUNT(*) FROM ScoreHist WHERE student_no='"+map.get("student_no")+"' GROUP BY school_year, school_term");				
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+tmp.size()+"</td>");
				
				out.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
				
				out.println("	</tr>");
				
			}
		}
		
		
		out.println("</table>");
		out.close();
	}
	

}
