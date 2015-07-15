package tw.edu.chit.struts.action.report.publicAccess;

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

public class AppointmentView extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		//HttpSession session = request.getSession(false);
		response.setContentType("text/html; charset=big5");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=CountClasses.xls");
		
		//List students=(List)session.getAttribute("students");
		
		PrintWriter out=response.getWriter();
		
		List list=manager.ezGetBy("SELECT s.student_no, s.student_name, cs.ClassName, c.* FROM ClinicService c, stmd s, Class cs WHERE " +
				"cs.ClassNO=s.depart_class AND c.patient=s.student_no");
		
		
		out.println("<table><tr><td>");
		
		out.println("<table width='50%' border='1'>  ");
		
		out.println("<tr>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>日期</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>順序</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級名稱</td>");		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>學號</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");		
		out.println("	</tr>");
			
		for(int i=0; i<list.size(); i++){
			
			out.println("<tr>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("cdate")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("sequence")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("ClassName")+"</td>");		
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("student_no")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("student_name")+"</td>");		
			out.println("	</tr>");			
		}					
		out.println("	</table>");
		
		out.println("</td><td>");
		
		
		list=manager.ezGetBy("SELECT e.sname, e.cname, c.name, l.* FROM empl e, CodeEmpl c, ClinicService l WHERE " +
				"l.patient=e.idno AND e.unit=c.idno ANd (c.category='Unit' OR c.category='UnitTeach')");
		out.println("<table width='50%' border='1'>  ");
		
		out.println("<tr>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>日期</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>順序</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>單位</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>職稱</td>");		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");		
		out.println("	</tr>");
			
		for(int i=0; i<list.size(); i++){
			
			out.println("<tr>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("cdate")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("sequence")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("name")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("sname")+"</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("cname")+"</td>");		
			out.println("	</tr>");			
		}					
		out.println("	</table>");
		
		
		out.println("</td></tr></table>");
		out.close();
		return null;
	}

}
