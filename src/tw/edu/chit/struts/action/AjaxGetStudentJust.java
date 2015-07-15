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
import tw.edu.chit.model.Just;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.util.Toolket;

public class AjaxGetStudentJust extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		ScoreManager scm = (ScoreManager)wac.getBean("scoreManager");
		StudAffairManager sm = (StudAffairManager)wac.getBean("studAffairManager");
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		
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
		double[] ddScore = new double[2];
		if(!studentNo.trim().equals("")) {
			Student student = scm.findStudentByStudentNo(studentNo);
			List<Just> justList = sm.findJustByStudentNo(studentNo, classInCharge);
			if(student != null){
				String studentName = student.getStudentName();
				String departClass = student.getDepartClass();
				String deptClassName = student.getDepartClass2();
				
				System.out.println("AjaxGetStudentJust studentNo=" + student.getStudentNo());
				out.println("<stmdPrompt>");
				out.println("<studentNo>" + studentNo + "</studentNo>");
				out.println("<studentName>" + studentName + "</studentName>");
				out.println("<departClass>" + departClass + "</departClass>");
				out.println("<deptClassName>" + deptClassName + "</deptClassName>");
				if(justList.isEmpty()) {
					ddScore = sm.calConductScoreOfDilgDesd(studentNo);
					out.println("<teacherScore>0.0</teacherScore>");
					out.println("<deptheaderScore>0.0</deptheaderScore>");
					out.println("<militaryScore>0.0</militaryScore>");
					out.println("<meetingScore>0.0</meetingScore>");
					out.println("<dilgScore>" + ddScore[0] + "</dilgScore>");
					out.println("<desdScore>" + ddScore[1] + "</desdScore>");
					out.println("<totalScore>0.0</totalScore>");
				}else{
					Just just = justList.get(0);
					out.println("<teacherScore>" + just.getTeacherScore() + "</teacherScore>");
					out.println("<deptheaderScore>" + just.getDeptheaderScore() + "</deptheaderScore>");
					out.println("<militaryScore>" + just.getMilitaryScore() + "</militaryScore>");
					out.println("<meetingScore>" + just.getMeetingScore() + "</meetingScore>");
					out.println("<dilgScore>" + ddScore[0] + "</dilgScore>");
					out.println("<desdScore>" + ddScore[1] + "</desdScore>");
					out.println("<totalScore>" + just.getTotalScore() + "</totalScore>");
				}
				out.println("</stmdPrompt>");
				out.close();
			}else{
				out.println("<stmdPrompt>");
				out.println("<studentNo>" + studentNo + "</studentNo>");
				out.println("<studentName>notFound</studentName>");
				out.println("<departClass>notFound</departClass>");
				out.println("<deptClassName>notFound</deptClassName>");
				out.println("<dilgScore>0.0</dilgScore>");
				out.println("<desdScore>0.0</desdScore>");
				out.println("<totalScore>0.0</totalScore>");
				out.println("</stmdPrompt>");
				out.close();
				
			}
		}else{
			out.println("<stmdPrompt>");
			out.println("<studentNo> </studentNo>");
			out.println("<studentName>notFound</studentName>");
			out.println("<departClass>notFound</departClass>");
			out.println("<deptClassName>notFound</deptClassName>");
			out.println("<dilgScore>0.0</dilgScore>");
			out.println("<desdScore>0.0</desdScore>");
			out.println("<totalScore>0.0</totalScore>");
			out.println("</stmdPrompt>");
			out.close();
		}
	}

}
