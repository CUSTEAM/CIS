package tw.edu.chit.struts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;


public class StudTimeOffQueryAction  extends BaseAction {
	public ActionForward execute (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession(false);
		setContentPage(session, "/pages/StudTimeOffQuery.jsp");
		return mapping.findForward("Main");

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		
		HttpSession session = request.getSession(false);
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		
		String studentNo = credential.getMember().getAccount();
		Student student = scm.findStudentByStudentNo(studentNo);
		String studentName = student.getStudentName();
		String departClass = student.getDepartClass();
		String deptClassName = student.getDepartClass2();
		String daynite = sm.chkStudentDepart(studentNo);
		
		String mode = request.getParameter("mode");
		if(mode == null) mode = "all";
		
		List dilgList = sm.findDilgByStudentNo(studentNo, mode);
		Dilg dilg;
		
		// Map result = new HashMap();
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<dilgPrompt>");
		out.println("<studentNo>" + studentNo + "</studentNo>");
		out.println("<studentName>" + studentName + "</studentName>");
		out.println("<departClass>" + departClass + "</departClass>");
		out.println("<deptClassName>" + deptClassName + "</deptClassName>");
		out.println("<daynite>" + daynite + "</daynite>");

		if(mode.equals("all")) {
			if(dilgList.size() > 0){
				for(Iterator<Dilg> dilgIter = dilgList.iterator(); dilgIter.hasNext();) {
					dilg = dilgIter.next();
					out.println("<dilgInfo>");
					out.println("<ddate>" + dilg.getDdate() + "</ddate>");
					out.println("<abs0>" + dilg.getAbsName0() + "</abs0>");
					out.println("<abs1>" + dilg.getAbsName0() + "</abs1>");
					out.println("<abs2>" + dilg.getAbsName0() + "</abs2>");
					out.println("<abs3>" + dilg.getAbsName0() + "</abs3>");
					out.println("<abs4>" + dilg.getAbsName0() + "</abs4>");
					out.println("<abs5>" + dilg.getAbsName0() + "</abs5>");
					out.println("<abs6>" + dilg.getAbsName0() + "</abs6>");
					out.println("<abs7>" + dilg.getAbsName0() + "</abs7>");
					out.println("<abs8>" + dilg.getAbsName0() + "</abs8>");
					out.println("<abs9>" + dilg.getAbsName0() + "</abs9>");
					out.println("<abs10>" + dilg.getAbsName0() + "</abs10>");
					out.println("<abs11>" + dilg.getAbsName0() + "</abs11>");
					out.println("<abs12>" + dilg.getAbsName0() + "</abs12>");
					out.println("<abs13>" + dilg.getAbsName0() + "</abs13>");
					out.println("<abs14>" + dilg.getAbsName0() + "</abs14>");
					out.println("<abs15>" + dilg.getAbsName0() + "</abs15>");
					out.println("</dilgInfo>");
					
				}
			} else {
				out.println("<dilgInfo></dilgInfo>");
			}
			
		} else if(mode.equals("subject")) {
			if(dilgList.size() > 0){
				for(Iterator dilgIter = dilgList.iterator(); dilgIter.hasNext();) {
					Map dilgMap = (Map)dilgIter.next();
					out.println("<dilgInfo>");
					out.println("<subjectName>" + dilgMap.get("subjectName") + "</subjectName>");
					out.println("<period>" + dilgMap.get("period") + "</period>");
					out.println("<tfLimit>" + dilgMap.get("tfLimit") + "</tfLimit>");
					out.println("<timeOff>" + dilgMap.get("timeOff") + "</tfWarn>");
					out.println("<warnning>" + dilgMap.get("warnning") + "</warnning>");
					out.println("<elearn>" + dilgMap.get("elearnDilg") + "</elearn>");
					out.println("</dilgInfo>");
					
				}
			} else {
				out.println("<dilgInfo></dilgInfo>");
			}
			

		}
		
		
		out.println("</dilgPrompt>");
		out.close();
	}

}
