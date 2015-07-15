package tw.edu.chit.struts.action.personnel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class getModuleHelp extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		//HttpSession session = request.getSession(false);
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/xml; charset=UTF-8");		
		String ModuleOid=request.getParameter("ModuleOid");		
		
		//Map map = manager.ezGetMap("SELECT mh.*, e.cname, ce.name as unitName FROM ModuleHelp mh, empl e, CodeEmpl ce WHERE " +
		//"ce.category='Unit' AND ce.idno=e.unit AND e.idno=mh.Author AND mh.ModuleOid='"+ModuleOid+"'");
		
		Map map = manager.ezGetMap("SELECT mh.*, e.cname FROM ModuleHelp mh, empl e WHERE " +
				"e.idno=mh.Author AND mh.ModuleOid='"+ModuleOid+"'");
		
		
		PrintWriter out=response.getWriter();		
		out.println("<pront>");		
		if(map==null){
			out.println("<UnitName>未知單位</UnitName>");
			out.println("<Content>尚未編輯</Content>");
			out.println("<EditStamp>0000-00-00</EditStamp>");
		}else{
			out.println("<UnitName>"+map.get("unitName")+"</UnitName>");
			out.println("<Content>"+map.get("Content")+"</Content>");
			out.println("<EditStamp>"+map.get("EditStamp")+"</EditStamp>");
		}
		
		out.println("</pront>");
		out.close();
		
	}

}
