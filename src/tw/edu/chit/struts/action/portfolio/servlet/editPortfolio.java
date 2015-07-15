package tw.edu.chit.struts.action.portfolio.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;



public class editPortfolio extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		HttpSession session = request.getSession(false);	
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		
		String Pid="";
		//在校生或離校生
		try{
			Pid=c.getStudent().getStudentNo();
		}catch(Exception e){
			Pid=c.getGstudent().getStudentNo();
		}
		 
		
		String content[]=request.getParameterValues("content");
		String Oid[]=request.getParameterValues("Oid");
		
		String field_name[]=request.getParameterValues("field_name");
		String table_name=request.getParameter("table_name");
		
		request.setAttribute("table_name", table_name);
		if(Oid.length>0)
		for(int i=0; i<Oid.length; i++){
			
			if(content[i].trim().equals("")&& !Oid[i].equals("")){
				manager.executeSql("DELETE FROM Eps_content WHERE Oid='"+Oid[i]+"' AND field_name='"+field_name[i]+"'");
			}else{
				manager.executeSql("UPDATE Eps_content SET content='"+content[i]+"' WHERE Oid='"+Oid[i]+"' AND field_name='"+field_name[i]+"'");
			}
			
			if(!content[i].trim().equals("")&& Oid[i].equals("")){
				manager.executeSql("INSERT INTO Eps_content (Uid, table_name, field_name, content) VALUES " +
						"('"+Pid+"', '"+table_name+"', '"+field_name[i]+"', '"+content[i]+"');");
			}
		}		
		
		List list=manager.ezGetBy("SELECT DISTINCT c.*, f.note, efd.sys FROM Eps_format f, Eps_content c, Eps_format_descript efd WHERE efd.table_name=f.table_name AND " +
				"f.field_name=c.field_name AND c.table_name='"+table_name+"' AND c.Uid='"+Pid+"'");
		List format=manager.ezGetBy("SELECT * FROM Eps_format WHERE table_name='"+table_name+"'");	
		
		request.setAttribute("table_name", table_name);
		request.setAttribute("myProtfolio", list);
		request.setAttribute("format", format);
		
		
		
		RequestDispatcher rd = request.getRequestDispatcher("Portfolio/PortfolioManager");		
	    rd.forward(request, response);

	}

}
