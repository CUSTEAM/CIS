package tw.edu.chit.struts.action.personnel.servlet;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.StdImage;
import tw.edu.chit.service.CourseManager;

import com.lowagie.text.Image;

public class EmplCard4PartTime extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);

		List empls = (List) session.getAttribute("empls");

		response.setHeader("Content-Disposition",
				"attachment;filename=Diploma.doc");
		response.setContentType("application/vnd.ms-word; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("  <head>");
		out
				.println("    <meta http-equiv=Content-Type content='text/html; charset=UTF-8'>");
		out.println("  </head>");
		out.println("<body>");

		List list = new ArrayList();
		for (int i = 0; i < empls.size(); i++) {

			if (((Map) empls.get(i)).get("sname").toString().indexOf("雇") > -1) {
				list.add(((Map) empls.get(i)));
			}

		}

		out.println("<table cellspacing='5'>");
		for (int i = 0; i < list.size(); i++) {

			if (i == 0 || i % 2 == 0) {
				out.println("    	<tr>");
			}

			out.println("    	<td>");
			out
					.println("        <table cellspacing='5' style='border: 1px solid #000000;'>");
			out.println("            <tr>");
			out.println("                <td>");
			out.println("                <table width='100%' align='left'>");
			out.println("                  <tr>");
			out
					.println("                    <td colspan='3' rowspan='4'><img width=84' height=108 src='http://ap.cust.edu.tw/CIS/Personnel/getFTPhoto4Empl?idno="
							+ ((Map) list.get(i)).get("idno") + "'></td>");
			out
					.println("                    <td width='240'><font face='標楷體'>單位:"
							+ ((Map) list.get(i)).get("unitName")
							+ "</font></td>");
			out.println("                  </tr>");
			out.println("                  <tr>");
			out.println("                    <td><font face='標楷體'>姓名:"
					+ ((Map) list.get(i)).get("cname") + "</font></td>");
			out.println("                  </tr>");
			out.println("                  <tr>");
			out
					.println("                    <td><font face='標楷體'>職稱:"
							+ manager
									.ezGetString("SELECT name FROM CodeEmpl WHERE idno='"
											+ ((Map) list.get(i)).get("pcode")
											+ "'") + "</font></td>");
			out.println("                  </tr>");
			out.println("                  <tr>");
			out
					.println("                    <td align='center'><img width=156 height=24 src='http://ap.cust.edu.tw/CIS/getBarcode39?no="
							+ ((Map) list.get(i)).get("idno") + "'></td>");
			out.println("                  </tr>");
			out.println("                </table>");

			out.println("                </td>");
			out.println("            </tr>");
			out.println("        </table>");
			out.println("        </td>");

			if (i % 2 == 1)
				out.println("      </tr>");

		}

		out.println("    </table>");
		// out.println (" </td>");

		// out.println ("</tr>");
		// out.println ("</table>");

		out.println("<br clear='all' style='page-break-before:always;'/>");

		out.println("</body></html>");
		out.close();
		out.flush();

	}

	/**
	 * 生成照片
	 * 
	 * @param stdImage
	 * @return
	 */
	private Image getImage(StdImage stdImage) {
		Graphics2D g2d = null;
		Blob b = stdImage.getImage();
		long size;
		try {
			size = b.length();
			byte[] bs = b.getBytes(1, (int) size);
			return Image.getInstance(bs);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}