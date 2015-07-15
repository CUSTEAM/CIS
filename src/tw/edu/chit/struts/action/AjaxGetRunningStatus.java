package tw.edu.chit.struts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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

import tw.edu.chit.model.Just;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;

public class AjaxGetRunningStatus extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		ScoreManager scm = (ScoreManager)wac.getBean("scoreManager");
		StudAffairManager sm = (StudAffairManager)wac.getBean("studAffairManager");
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");

		String methodName = request.getParameter("process");
		boolean isFound = false;
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		List runStatus = new ArrayList();
		runStatus = sm.chkstatus();
		
		Map sMap = new HashMap();
		
		for(Iterator statIter = runStatus.iterator(); statIter.hasNext();) {
			sMap = (Map)statIter.next();
			if(methodName.equalsIgnoreCase(sMap.get("methodName").toString())){
				isFound=true;
				break;
			}
		}
		//System.out.println("methodName:" + methodName + ", isFound:" + isFound + ",Listsize:" + runStatus.size());
		
		
		if(isFound){
			//System.out.println("methodName:" + methodName + ", step:" + sMap.get("step").toString());
			out.println("<statPrompt>");
			out.println("<methodName>" + sMap.get("methodName").toString() + "</methodName>");
			out.println("<step>" + sMap.get("step").toString() + "</step>");
			out.println("<rcount>" + sMap.get("rcount").toString() + "</rcount>");
			out.println("<total>" + sMap.get("total").toString() + "</total>");
			out.println("<percentage>" + sMap.get("percentage").toString() + "</percentage>");
			out.println("<complete>" + sMap.get("complete").toString() + "</complete>");
			out.println("</statPrompt>");
			out.close();
		}
		
		isFound = false;
		runStatus=scm.chkstatus();
		//System.out.println("ScoreManager + StudAffairManager status.size()" + runStatus.size());
		
		for(Iterator statIter = runStatus.iterator(); statIter.hasNext();) {
			sMap = (Map)statIter.next();
			if(methodName.equalsIgnoreCase(sMap.get("methodName").toString())){
				isFound=true;
				break;
			}
		}
		//System.out.println("methodName:" + methodName + ", isFound:" + isFound + ",Listsize:" + runStatus.size());
		
		response.setContentType("text/xml; charset=UTF-8");
		
		if(isFound){
			//System.out.println("methodName:" + methodName + ", step:" + sMap.get("step").toString());
			out.println("<statPrompt>");
			out.println("<methodName>" + sMap.get("methodName").toString() + "</methodName>");
			out.println("<step>" + sMap.get("step").toString() + "</step>");
			out.println("<rcount>" + sMap.get("rcount").toString() + "</rcount>");
			out.println("<total>" + sMap.get("total").toString() + "</total>");
			out.println("<percentage>" + sMap.get("percentage").toString() + "</percentage>");
			out.println("<complete>" + sMap.get("complete").toString() + "</complete>");
			out.println("</statPrompt>");
			out.close();
		}else {
			out.println("<statPrompt>");
			out.println("<methodName>" + methodName + "</methodName>");
			out.println("<step>none</step>");
			out.println("<rcount>none</rcount>");
			out.println("<total>none</total>");
			out.println("<percentage>none</percentage>");
			out.println("<complete>none</complete>");
			out.println("</statPrompt>");
			out.close();

		}

	}

}
