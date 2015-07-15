package tw.edu.chit.struts.action.report.teacher;

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

public class MyIntor extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		//HttpSession session = request.getSession(false);
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();	
		
		out.println("<pront>");
		
		try{
			String soid=request.getParameter("soid");
			String str=manager.ezGetString("SELECT Introduction FROM Savedtime WHERE Oid="+soid);
			//System.out.println(str);
			Map map=manager.parseIntr(manager.replaceChar4XML(str));
			if(map.get("chi").equals("")){
				out.println("<chi>無資料</chi>");
			}else{
				out.println("<chi>"+map.get("chi")+"</chi>");
			}			
			if(map.get("eng").equals("")){
				out.println("<eng>無資料</eng>");
			}else{
				out.println("<eng>"+map.get("eng")+"</eng>");
			}			
			if(map.get("book").equals("")){
				out.println("<book>無資料</book>");
			}else{
				out.println("<book>"+map.get("book")+"</book>");
			}
		}catch(Exception e){
			e.printStackTrace();
			out.println("<chi>無資料</chi>");
			out.println("<eng>Not found</eng>");
			out.println("<book>Not found</book>");
		}
		out.println("</pront>");
		out.close();
		
		return null;
	}

}
