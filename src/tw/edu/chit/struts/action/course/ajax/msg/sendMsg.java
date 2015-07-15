package tw.edu.chit.struts.action.course.ajax.msg;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 * 
 */
public class sendMsg extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager=(CourseManager) ctx.getBean("courseManager");

		String studentNo=request.getParameter("studentNo");
		String studentNo1=new String(studentNo.getBytes("iso-8859-1"),"utf-8");
		String schoolYear=request.getParameter("schoolYear");
		String schoolYear1=new String(schoolYear.getBytes("iso-8859-1"),"utf-8");
		
		List scoreHistory=manager.getScoreHistBy(studentNo1, schoolYear1);
		Map map;
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();
		HttpSession session = request.getSession(false);
		out.println("<pront>");
		float should=0;
		float reality=0;
		for(int i=0; i<scoreHistory.size(); i++){
			map=(Map)scoreHistory.get(i);
			out.print("<school_year>"+map.get("school_year")+"</school_year>");
			out.print("<school_term>"+map.get("school_term")+"</school_term>");
			out.print("<student_no>"+map.get("student_no")+"</student_no>");

			if(map.get("score")!=null){
				out.print("<score>"+map.get("score")+"</score>");
			}else{
				out.print("<score>*</score>");
			}
			
			Integer pass=60;
			if(session.getAttribute("master").equals(true)){
				pass=70;
			}


			out.print("<credit>"+map.get("credit")+"</credit>");
			out.print("<opt>"+map.get("opt")+"</opt>");
			out.print("<chi_name>"+map.get("chi_name")+"</chi_name>");
			out.print("<evgr_type>"+map.get("evgr_type")+"</evgr_type>");

			should=should+Float.parseFloat(map.get("credit").toString());

			if(map.get("score")!=null){
				if(Float.parseFloat(map.get("score").toString())>=pass){
					reality=reality+Float.parseFloat(map.get("credit").toString());
				}
			}

		}

		out.println("<should>"+should+"</should>");
		out.println("<reality>"+reality+"</reality>");

		out.println("</pront>");
		out.close();
	}

}
