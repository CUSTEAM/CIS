package tw.edu.chit.struts.action.report.registration.csgroup;

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

public class CsGroupStdCount extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		//WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		//CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		CourseManager manager = (CourseManager) getBean("courseManager");
		//HttpSession session = request.getSession(false);
		PrintWriter out=response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=CsGroupStdCount.xls");
		
		HttpSession session = request.getSession(false);
		List list=(List)session.getAttribute("relult");
		
		
		
		
		out.println("<table border='1'>");
		out.println("  <tr>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("  </tr>");
		out.println("  <tr>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("    <td></td>");
		out.println("  </tr>");
		out.println("</table>");
		
		return null;
	}

}
