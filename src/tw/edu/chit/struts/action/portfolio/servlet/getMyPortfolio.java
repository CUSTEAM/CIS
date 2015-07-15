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

public class getMyPortfolio extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);

		String table = request.getParameter("table");
		UserCredential c = (UserCredential) session.getAttribute("Credential");

		String Pid = "";
		// 在校生或離校生
		try {
			Pid = c.getStudent().getStudentNo();
		} catch (Exception e) {
			Pid = c.getGstudent().getStudentNo();
		}

		List list = manager
				.ezGetBy("SELECT DISTINCT c.*, f.note FROM Eps_format f, Eps_content c WHERE "
						+ "f.field_name=c.field_name AND c.table_name='"
						+ table
						+ "' AND c.Uid='"
						+ Pid
						+ "' AND f.table_name='" + table + "'");

		List format = manager
				.ezGetBy("SELECT * FROM Eps_format WHERE table_name='" + table
						+ "'");

		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<pront>");
		if (list.size() < 1) {

			for (int i = 0; i < format.size(); i++) {
				out.println("<Oid>new</Oid>");
				out.println("<Pid>new</Pid>");
				out.println("<table_name>"
						+ ((Map) format.get(i)).get("table_name")
						+ "</table_name>");
				out.println("<field_name>"
						+ ((Map) format.get(i)).get("field_name")
						+ "</field_name>");
				out.println("<content>new</content>");
				out.println("<sequence>new</sequence>");
				out.println("<note>" + ((Map) format.get(i)).get("note")
						+ "</note>");
			}

		} else {

			for (int i = 0; i < list.size(); i++) {
				if (((Map) list.get(i)).get("Oid") == null
						|| ((Map) list.get(i)).get("Oid").equals("")) {
					out.println("<Oid>內容有錯誤</Oid>");
					out.println("<Pid>內容有錯誤</Pid>");
					out.println("<table_name>內容有錯誤</table_name>");
					out.println("<field_name>內容有錯誤</field_name>");
					out.println("<content>內容有錯誤</content>");
					out.println("<sequence>內容有錯誤</sequence>");
					out.println("<sequence>內容有錯誤</sequence>");
				} else {
					out.println("<Oid>" + ((Map) list.get(i)).get("Oid")
							+ "</Oid>");
					out.println("<Pid>" + ((Map) list.get(i)).get("Pid")
							+ "</Pid>");
					out.println("<table_name>"
							+ ((Map) list.get(i)).get("table_name")
							+ "</table_name>");
					out.println("<field_name>"
							+ ((Map) list.get(i)).get("field_name")
							+ "</field_name>");
					out
							.println("<content>"
									+ ((Map) list.get(i)).get("content")
									+ "</content>");
					out.println("<sequence>"
							+ ((Map) list.get(i)).get("sequence")
							+ "</sequence>");
					out.println("<note>" + ((Map) list.get(i)).get("note")
							+ "</note>");
				}
			}

			for (int i = 0; i < format.size(); i++) {
				out.println("<Oid>new</Oid>");
				out.println("<Pid>new</Pid>");
				out.println("<table_name>"
						+ ((Map) format.get(i)).get("table_name")
						+ "</table_name>");
				out.println("<field_name>"
						+ ((Map) format.get(i)).get("field_name")
						+ "</field_name>");
				out.println("<content>new</content>");
				out.println("<sequence>new</sequence>");
				out.println("<note>" + ((Map) format.get(i)).get("note")
						+ "</note>");
			}

		}
		out.println("</pront>");
		out.close();

	}
}
