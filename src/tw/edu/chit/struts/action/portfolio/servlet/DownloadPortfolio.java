package tw.edu.chit.struts.action.portfolio.servlet;

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

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;

public class DownloadPortfolio extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/msword");
		response.setHeader("Content-disposition","attachment;filename=students.doc");
		
		PrintWriter out=response.getWriter();
		out.println ("<html xmlns='http://www.w3.org/TR/REC-html40'>");
		out.println ("  <head>");
		out.println ("    <meta http-equiv=Content-Type content='text/html; charset=UTF-8'>");
		out.println ("  </head>");
		out.println ("  <body bgcolor='#FFFFFF' lang=ZH-TW>");
		
		
		
		
		
		
		if(request.getParameter("student_no")==null && request.getParameter("depart_class")==null){
			HttpSession session = request.getSession(false);
			UserCredential c = (UserCredential) session.getAttribute("Credential");
			String Uid=c.getMember().getAccount();//取得使用者帳號
			if(Uid==null){
				Uid=c.getGstudent().getStudentNo();
			}
			if(Uid==null){
				Uid=c.getGstudent().getStudentNo();
			}
			
			Map map=manager.ezGetMap("SELECT * FROM Eps_vitae WHERE student_no='"+Uid+"'");
			
			
			if(map!=null){
				out.println(map.get("content_zh"));
				out.println(map.get("content_en"));
			}else{
				out.println("尚未建立履歷表");
			}
		}
		
		
		if(request.getParameter("depart_class")!=null){			
			
			List list=manager.ezGetBy("SELECT s.student_no as no, e.*, s.student_name FROM Seld d, stmd s LEFT OUTER JOIN Eps_vitae e " +
					"ON e.student_no=s.student_no WHERE d.student_no=s.student_no AND d.Dtime_oid='"+request.getParameter("depart_class")+"'");
			
			Map map;
			for(int i=0; i<list.size(); i++){
				
				map=((Map)list.get(i));
				if(map.get("content_zh")!=null){
					out.println("<div>"+map.get("content_zh")+"</div>");
					out.println("<div>"+map.get("content_en")+"</div>");
					out.println ("<br clear='all' style='page-break-before:always;'/>");
				}else{
					out.println ("<br clear='all' style='page-break-before:always;'/>");
					out.println(map.get("no")+""+map.get("student_name")+"尚未建立履歷表, ");
					out.println ("<br clear='all' style='page-break-before:always;'/>");
				}
			}
					
		}
		
		if(request.getParameter("student_no")!=null){
			
			
			String Uid=request.getParameter("student_no").toString();
			Map map=manager.ezGetMap("SELECT * FROM Eps_vitae WHERE student_no='"+Uid+"'");
			if(map!=null){
				out.println(map.get("content_zh"));
				out.println(map.get("content_en"));
			}else{
				out.println ("<br clear='all' style='page-break-before:always;'/>");
				out.println(Uid+"尚未建立履歷表");
				out.println ("<br clear='all' style='page-break-before:always;'/>");
			}
			
			
		}
		
		out.println ("  </body>");
		out.println ("</html>");
		out.close();
		out.flush();
		/*
		 * ServletOutputStream stream = response.getOutputStream();
		ServletContext context = this.getServletContext();
		File file = new File(context.getRealPath("/WEB-INF/reports/portfolio/Eportfolio.xml"));
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));		
		byte[]buff=new byte[248];
		try {
			String Variable = "";
			while(in.read(buff, 0, buff.length)!=-1) {
				Variable=Variable+new String(buff, "UTF-8");
				buff = new byte[248];
			}
			byte[] tmp = Variable.trim().getBytes("UTF-8");
			stream.write(tmp);
		} finally {
			in.close();
			stream.close();
		}
		*/
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
