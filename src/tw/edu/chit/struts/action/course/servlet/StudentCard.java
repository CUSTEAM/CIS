package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

/**
 * 學生證 M$Word格式
 * 
 * @author JOHN
 * 
 */
public class StudentCard extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);

		response
				.setHeader("Content-Disposition",
						"attachment;filename=StudentCard" + Math.random() * 10
								+ ".doc");
		response.setContentType("application/vnd.ms-word; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("");
		out.println("<head>");
		out
				.println("<meta http-equiv=Content-Type content='text/html; charset=big5'>");
		out
				.println("<meta name=Generator content='Microsoft Word 11 (filtered)'>");
		out.println("<style>");
		out.println("<!--");
		out.println(" /* Font Definitions */");
		out.println(" @font-face");
		out.println("	{font-family:新細明體;");
		out.println("	panose-1:2 2 3 0 0 0 0 0 0 0;}");
		out.println("@font-face");
		out.println("	{font-family:'\\@新細明體';");
		out.println("	panose-1:2 2 3 0 0 0 0 0 0 0;}");
		out.println(" /* Style Definitions */");
		out.println(" p.MsoNormal, li.MsoNormal, div.MsoNormal");
		out.println("	{margin:0cm;");
		out.println("	margin-bottom:.0001pt;");
		out.println("	font-size:12.0pt;");
		out.println("	font-family:'Times New Roman';}");
		out.println(" /* Page Definitions */");
		out.println(" @page Section1");
		out.println("	{size:595.3pt 841.9pt;");
		out.println("	margin:0cm 0cm 0cm 0cm;");
		out.println("	layout-grid:18.0pt;}");
		out.println("div.Section1");
		out.println("	{page:Section1;}");
		out.println("-->");
		out.println("</style>");
		out.println("");
		out.println("</head>");
		out.println("");
		out.println("<body lang=ZH-TW style='text-justify-trim:punctuation;'>");
		out.println("");
		out.println("<div class=Section1 style='layout-grid:18.0pt'>");
		out.println("");
		out.println("<p class=MsoNormal><span lang=EN-US>");

		out
				.println("<table cellspacing='0' cellpadding='0' style='text-justify-trim:punctuation;'>");
		out.println("<tr style='height:1cm;'>");
		out.println("<td>");

		// out.println ("testest");

		out.println("</td>");
		out.println("<tr>");
		out.println("<tr>");
		out.println("<td>");

		out
				.println("<img src='http://blog.udn.com/community/img/PSN_ARTICLE/taiwan3355/f_1715227_1.jpg' width='114'>");

		out.println("</td>");
		out.println("<tr>");

		out.println("<table>");

		out.println("</span></p>");

		out.println("</div>");
		out.println("");
		out.println("</body>");
		out.println("");
		out.println("</html>");
		out.println("");
		out.close();
	}

}
