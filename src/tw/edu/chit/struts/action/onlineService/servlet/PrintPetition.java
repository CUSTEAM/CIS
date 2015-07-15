package tw.edu.chit.struts.action.onlineService.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

//import tw.edu.chit.model.OnlineServiceDoc;
import tw.edu.chit.service.CourseManager;

/**
 * 檢視申請書
 * 
 * @author JOHN
 * 
 */
public class PrintPetition extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);

		String type = request.getParameter("type");// tableName
		String pk = request.getParameter("pk");
		String content = "";

		System.out.println(type);
		System.out.println(pk);

		// 行政端
		if (type.equals("petition")) {// 申請書
			content = manager
					.ezGetString("SELECT petition FROM OnlineServices WHERE Oid='"
							+ pk + "'");
		}
		if (type.equals("note")) {// 申請須知
			content = manager
					.ezGetString("SELECT note FROM OnlineServices WHERE Oid='"
							+ pk + "'");
		}
		if (type.equals("guide")) {// 操作手冊
			content = manager
					.ezGetString("SELECT guide FROM OnlineServices WHERE Oid='"
							+ pk + "'");
		}

		// 學生端
		if (type.equals("st_petition")) {// 申請書
			content = manager
					.ezGetString("SELECT petition FROM OnlineServiceDoc WHERE doc_no='"
							+ pk + "'");
		}
		if (type.equals("st_note")) {// 備註
			content = manager
					.ezGetString("SELECT note FROM OnlineServiceDoc WHERE doc_no='"
							+ pk + "'");
		}

		response.setHeader("Content-Disposition",
				"attachment;filename=Diploma.doc");
		response.setContentType("application/vnd.ms-word; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<html xmlns='http://www.w3.org/TR/REC-html40'>");
		out.println("  <head>");
		out
				.println("    <meta http-equiv=Content-Type content='text/html; charset=UTF-8'>");
		out.println("  </head>");
		out.println("  <body bgcolor='#FFFFFF' lang=ZH-TW>");
		out.println(content);
		out.println("  </body>");
		out.println("</html>");
		out.flush();
		out.close();
	}

}
