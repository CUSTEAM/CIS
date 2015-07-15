package tw.edu.chit.struts.action.report.course;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

public class ListSeld4Class extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		//CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=CountClasses.xls");
		
		List ListSeld4Class=(List)session.getAttribute("ListSeld4Class");
		
		PrintWriter out=response.getWriter();
		
		out.println("<table width='100%' border='1'>  ");
		
		out.println("<tr>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級代碼</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>學生班級</td>");		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>學號</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>開課班級</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>課程名稱</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>選別</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>學分</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>時數</td>");	
		out.println("	</tr>");	
		out.println("	</tr>");
			
			for(int i=0; i<ListSeld4Class.size(); i++){				
				if(i%2==0){
					out.println("<tr bgcolor='#f0fcd7'>");
				}else{
					out.println("<tr>");
				}					
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)ListSeld4Class.get(i)).get("ClassNo")+"</td>");
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)ListSeld4Class.get(i)).get("ClassName")+"</td>");		
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)ListSeld4Class.get(i)).get("student_no")+"</td>");
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)ListSeld4Class.get(i)).get("student_name")+"</td>");
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)ListSeld4Class.get(i)).get("depart_class")+"</td>");
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)ListSeld4Class.get(i)).get("chi_name")+"</td>");
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)ListSeld4Class.get(i)).get("opt")+"</td>");
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)ListSeld4Class.get(i)).get("credit")+"</td>");
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)ListSeld4Class.get(i)).get("thour")+"</td>");	
				out.println("	</tr>");
			}
			out.println("	</table>");
			out.close();
		
		return null;
	}

}
