package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Csno;
import tw.edu.chit.service.CourseManager;

public class AjaxGetTaxPay extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("I'm Working!");
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		Integer real_tax=Integer.parseInt(request.getParameter("taxPay"));
		Integer family_no=Integer.parseInt(request.getParameter("family_no"));		
		
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();		
		
		out.println("<pront>");
		//System.out.println("SELECT p"+family_no+" FROM Salary_taxtable WHERE max>="+real_tax+" and mini<="+real_tax);
		try{
			//System.out.println("<tax>"+manager.ezGetInt("SELECT p"+family_no+" FROM Salary_taxtable WHERE max>="+real_tax+" and mini<="+real_tax)+"</tax>");
			out.println("<tax>"+manager.ezGetInt("SELECT p"+family_no+" FROM Salary_taxtable WHERE max>="+real_tax+" and mini<="+real_tax)+"</tax>");
		}catch(Exception e){
			//System.out.println("<tax>0</tax>");
			out.println("<tax>0</tax>");
		}
		out.println("</pront>");
		out.close();
		
	}
}
