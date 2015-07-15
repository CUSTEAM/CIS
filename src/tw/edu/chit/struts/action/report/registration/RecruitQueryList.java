package tw.edu.chit.struts.action.report.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;
/**
 * @author JOHN
 *
 */
import tw.edu.chit.struts.action.BaseAction;
public class RecruitQueryList extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		//CourseManager manager = (CourseManager) getBean("courseManager");		
		HttpSession session = request.getSession(false);
		List list=(List)session.getAttribute("stds");
		
		
		
		
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=CountClasses.xls");

		
		
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<head><meta http-equiv='content-type' content='text/html; charset=utf-8' /></head>");
		

		out.println("<style>");
		out.println("<!--table");
		out.println("@page");
		out.println("	{margin:.98in .2in .98in .2in;");
		out.println("	mso-header-margin:.51in;");
		out.println("	mso-footer-margin:.51in;}");
		out.println("-->");
		out.println("</style>");		
		
		out.println("<table border=1>  ");
		for(int i=0; i<list.size(); i++){
			
			if(i%43==0){				
				out.println("  <tr>");
				out.println("		<td align='center'>入學年月</td>");				
				out.println("		<td align='center'>班級</td>");		
				out.println("		<td align='center'>學號</td>");
				out.println("		<td>姓名</td>");	
				out.println("		<td>性別</td>");
				out.println("		<td>畢業學校</td>");
				out.println("		<td>畢業科系</td>");				
				out.println("		<td>入學身份</td>");
				out.println("  </tr>");
			}
			out.println("  <tr>");
			out.println("		<td align='center'>"+((Map)list.get(i)).get("entrance")+"</td>");				
			out.println("		<td align='center' align='left'>"+((Map)list.get(i)).get("ClassName")+"</td>");
			out.println("		<td align='center'>"+((Map)list.get(i)).get("student_no")+"</td>");
			out.println("		<td align='center'>"+((Map)list.get(i)).get("student_name")+"</td>");
			
			if(((Map)list.get(i)).get("sex").equals("1")){
				out.println("		<td align='center'>男</td>");
			}else{
				out.println("		<td align='center'>女</td>");
			}
			
			out.println("		<td align='center' align='left'>"+((Map)list.get(i)).get("SchoolName")+"</td>");
			out.println("		<td align='center' align='left'>"+((Map)list.get(i)).get("grad_dept")+"</td>");
			out.println("		<td align='center' align='left'>"+((Map)list.get(i)).get("IdName")+"</td>");
			out.println("  </tr>");

		}
		
		out.println("</table>");
		out.println("</html>");	
		
		out.close();
		return null;
	}

}
