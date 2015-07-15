package tw.edu.chit.struts.action.report.portfolio;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

public class List4UserManager extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		//CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=CountClasses.xls");
		
		List students=(List)session.getAttribute("students");
		System.out.println(students.size());
		PrintWriter out=response.getWriter();
		
		out.println("<table width='100%' border='1'>  ");
		
		out.println("<tr>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級代碼</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級名稱</td>");		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>學號</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");		
		out.println("	</tr>");
			
		for(int i=0; i<students.size(); i++){
			
			try{
				if(i%2==0){
					out.println("<tr bgcolor='#f0fcd7'>");
				}else{
					out.println("<tr>");
				}					
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)students.get(i)).get("ClassNo")+"</td>");
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)students.get(i)).get("ClassName")+"</td>");		
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)students.get(i)).get("student_no")+"</td>");
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)students.get(i)).get("student_name")+"</td>");		
				out.println("	</tr>");
			}catch(Exception e){
				
			}				
		}					
					
		out.println("	</table>");
		out.close();
		return null;
	}

}
