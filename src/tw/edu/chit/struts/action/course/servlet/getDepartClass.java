package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class getDepartClass extends HttpServlet {

	// CourseManager manager = (CourseManager)getBean("courseManager");
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");

		String CampusNo = request.getParameter("CampusNo");
		String SchoolNo = request.getParameter("SchoolNo");
		String DeptNo = request.getParameter("DeptNo");
		String Grade = request.getParameter("Grade");
		// String ClassNo=request.getParameter("ClassNo");除非有1天要我出資料才用到

		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<pront>");

		List Campuses;
		List Schools;
		List Depts;
		List Grades;
		List Classes;
		if (!CampusNo.equals("")) {
			// 取校區和學制
			Campuses = manager
					.ezGetBy("SELECT c5.idno, c5.name FROM Class c, code5 c5 WHERE "
							+ "c.CampusNo=c5.idno AND c5.category='Campus' AND c.Type='P' GROUP BY c.CampusNo");
			Schools = manager
					.ezGetBy("SELECT c5.idno, c5.name FROM Class c, code5 c5 WHERE "
							+ "c.SchoolNo=c5.idno AND c5.category='School' AND c.CampusNo='"
							+ CampusNo + "' AND c.Type='P' GROUP BY c5.idno");

			if (Campuses.size() < 1) {
				out.println("<CampusNo>?</CampusNo>");
				out.println("<CampusName>無資料</CampusName>");
			} else {
				for (int i = 0; i < Campuses.size(); i++) {
					out.println("<CampusNo>"
							+ ((Map) Campuses.get(i)).get("idno")
							+ "</CampusNo>");
					out.println("<CampusName>"
							+ ((Map) Campuses.get(i)).get("name")
							+ "</CampusName>");
				}
			}

			if (Schools.size() < 1) {
				out.println("<SchoolNo>?</SchoolNo>");
				out.println("<SchoolName>無資料</SchoolName>");
			} else {
				for (int i = 0; i < Schools.size(); i++) {
					out.println("<SchoolNo>"
							+ ((Map) Schools.get(i)).get("idno")
							+ "</SchoolNo>");
					out.println("<SchoolName>"
							+ ((Map) Schools.get(i)).get("name")
							+ "</SchoolName>");
				}
			}

		} else {
			// 只取校區
			Campuses = manager
					.ezGetBy("SELECT c5.idno, c5.name FROM Class c, code5 c5 WHERE "
							+ "c.CampusNo=c5.idno AND c5.category='Campus' AND c5.idno AND c.Type='P' GROUP BY c5.idno");

			if (Campuses.size() < 1) {
				out.println("<CampusNo>?</CampusNo>");
				out.println("<CampusName>無資料</CampusName>");
			} else {
				for (int i = 0; i < Campuses.size(); i++) {
					out.println("<CampusNo>"
							+ ((Map) Campuses.get(i)).get("idno")
							+ "</CampusNo>");
					out.println("<CampusName>"
							+ ((Map) Campuses.get(i)).get("name")
							+ "</CampusName>");
				}
			}
		}

		// 取科系
		if (!SchoolNo.equals("")) {
			Depts = manager
					.ezGetBy("SELECT c5.idno, c5.name FROM Class c, code5 c5 WHERE "
							+ "c.DeptNo=c5.idno AND c5.category='Dept' AND c.SchoolNo='"
							+ SchoolNo
							+ "' AND c.CampusNo='"
							+ CampusNo
							+ "' AND c.Type='P' GROUP BY c5.idno");

			if (Depts.size() < 1) {
				out.println("<DeptNo>?</DeptNo>");
				out.println("<DeptName>無資料</DeptName>");
			} else {
				for (int i = 0; i < Depts.size(); i++) {
					out.println("<DeptNo>" + ((Map) Depts.get(i)).get("idno")
							+ "</DeptNo>");
					out.println("<DeptName>" + ((Map) Depts.get(i)).get("name")
							+ "</DeptName>");
				}
			}
		}

		// 取年級
		if (!DeptNo.equals("")) {
			Grades = manager.ezGetBy("SELECT Grade FROM Class WHERE "
					+ "CampusNo='" + CampusNo + "' AND SchoolNo='" + SchoolNo
					+ "' AND DeptNo='" + DeptNo
					+ "' AND Type='P' GROUP BY Grade");

			if (Grades.size() < 1) {
				System.out.println("gradeSize=" + Grades.size());
				out.println("<Grade>?</Grade>");
				out.println("<GradeName>無資料</GradeName>");
			} else {
				for (int i = 0; i < Grades.size(); i++) {
					out.println("<Grade>" + ((Map) Grades.get(i)).get("Grade")
							+ "</Grade>");
					out.println("<GradeName>"
							+ ((Map) Grades.get(i)).get("Grade")
							+ "年級</GradeName>");
				}
			}
		}

		// 取班級
		if (!Grade.equals("")) {
			Classes = manager
					.ezGetBy("SELECT ClassNo, ClassName FROM Class WHERE "
							+ "CampusNo='" + CampusNo + "' AND SchoolNo='"
							+ SchoolNo + "' AND DeptNo='" + DeptNo
							+ "' AND Grade='" + Grade + "' AND Type='P'");

			if (Classes.size() < 1) {
				out.println("<ClassNo>?</ClassNo>");
				out.println("<ClassName>無資料</ClassName>");
			} else {
				for (int i = 0; i < Classes.size(); i++) {
					out.println("<ClassNo>"
							+ ((Map) Classes.get(i)).get("ClassNo")
							+ "</ClassNo>");
					out.println("<ClassName>"
							+ ((Map) Classes.get(i)).get("ClassName")
							+ "</ClassName>");
				}
			}
		}

		out.println("</pront>");
		out.close();

	}

}
