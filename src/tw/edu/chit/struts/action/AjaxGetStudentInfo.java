package tw.edu.chit.struts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.util.Toolket;

public class AjaxGetStudentInfo extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		ScoreManager scm = (ScoreManager)wac.getBean("scoreManager");
		
		HttpSession session = request.getSession(false);
		
		
		String studentNo = request.getParameter("studentno");
		/*
		if(studentNo == null) {
			studentNo = credential.getStudent().getStudentNo();
		} else if(studentNo.trim().equals("")) {
			studentNo = credential.getStudent().getStudentNo();
		}
		*/
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		if(!studentNo.trim().equals("")) {
			Student student = scm.findStudentByStudentNo(studentNo);
			Graduate graduate = scm.findGraduateByStudentNo(studentNo);
			
			if(student != null){
				String studentName = student.getStudentName();
				String departClass = student.getDepartClass();
				String deptClassName = student.getDepartClass2();
				
				System.out.println("AjaxGetStudentInfo studentNo=" + student.getStudentNo());
		
				out.println("<stmdPrompt>");
				out.println("<studentNo>" + studentNo + "</studentNo>");
				out.println("<studentName>" + studentName + "</studentName>");
				out.println("<departClass>" + departClass + "</departClass>");
				out.println("<deptClassName>" + deptClassName + "</deptClassName>");
				out.println("</stmdPrompt>");
				out.close();
			}else if(graduate != null){
				out.println("<stmdPrompt>");
				out.println("<studentNo>" + studentNo + "</studentNo>");
				out.println("<studentName>" + graduate.getStudentName() + "</studentName>");
				out.println("<departClass>" + graduate.getDepartClass() + "</departClass>");
				out.println("<deptClassName>" + graduate.getDepartClass2() + "</deptClassName>");
				out.println("</stmdPrompt>");
				out.close();
				
			}else{
				out.println("<stmdPrompt>");
				out.println("<studentNo>" + studentNo + "</studentNo>");
				out.println("<studentName>notFound</studentName>");
				out.println("<departClass>notFound</departClass>");
				out.println("<deptClassName>notFound</deptClassName>");
				out.println("</stmdPrompt>");
				out.close();
				
			}
		}else{
			out.println("<stmdPrompt>");
			out.println("<studentNo> </studentNo>");
			out.println("<studentName>notFound</studentName>");
			out.println("<departClass>notFound</departClass>");
			out.println("<deptClassName>notFound</deptClassName>");
			out.println("</stmdPrompt>");
			out.close();
		}
	}

}
