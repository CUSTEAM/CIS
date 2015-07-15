package tw.edu.chit.struts.action.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.AdminManager;
import tw.edu.chit.util.IConstants;

public class AjaxStmdsSearch extends HttpServlet {

	private static final long serialVersionUID = 2022871713256998146L;

	@Override
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(this.getServletContext());
		AdminManager am = (AdminManager) ac
				.getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);

		String studentNoOrName = new String(request.getParameter("idno")
				.getBytes("ISO-8859-1"), "UTF-8").replaceAll("\\|", "");
		boolean bFlag = "true".equals(request.getParameter("c"));
		int limit = StringUtils.isBlank(request.getParameter("l")) ? 1000
				: Integer.parseInt(request.getParameter("l"));
		boolean includeGraduate = "o".equalsIgnoreCase(request
				.getParameter("ig"));
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		if (StringUtils.isNotBlank(studentNoOrName)) {

			String hql = "SELECT s.studentNo, s.studentName, s.idno FROM Student s "
					+ "WHERE s.studentNo LIKE ? ORDER BY s.studentNo";
			List ret = am.find(hql,
					new Object[] { "%" + studentNoOrName + "%" }, limit);

			if (ret.isEmpty()) {
				hql = "SELECT s.studentNo, s.studentName, s.idno FROM Student s "
						+ "WHERE s.studentName LIKE ? ORDER BY s.studentNo";
				ret = am.find(hql,
						new Object[] { "%" + studentNoOrName + "%" }, limit);
			}

			if (ret.isEmpty()) {
				hql = "SELECT s.studentNo, s.studentName, s.idno FROM Student s "
						+ "WHERE s.idno LIKE ? ORDER BY s.studentNo";
				ret = am.find(hql, new Object[] { "%"
						+ studentNoOrName.toUpperCase() + "%" }, limit);
			}

			if (ret.isEmpty() && includeGraduate) {
				hql = "SELECT s.studentNo, s.studentName, s.idno FROM Graduate s "
						+ "WHERE s.studentNo LIKE ? ORDER BY s.studentNo";
				ret = am.find(hql, new Object[] { "%"
						+ studentNoOrName.toUpperCase() + "%" }, limit);

				if (ret.isEmpty()) {
					hql = "SELECT s.studentNo, s.studentName, s.idno FROM Graduate s "
							+ "WHERE s.studentName LIKE ? ORDER BY s.studentNo";
					ret = am.find(hql, new Object[] { "%"
							+ studentNoOrName.toUpperCase() + "%" }, limit);
				}

				if (ret.isEmpty()) {
					hql = "SELECT s.studentNo, s.studentName, s.idno FROM Graduate s "
							+ "WHERE s.idno LIKE ? ORDER BY s.studentNo";
					ret = am.find(hql, new Object[] { "%"
							+ studentNoOrName.toUpperCase() + "%" }, limit);
				}
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buffer.append("<message>");
			String tmpStr = null;
			if (bFlag) {
				for (Object o : ret) {
					Object[] data = (Object[]) o;
					tmpStr = String.format("%s %s %s", String.valueOf(data[0])
							.trim().toUpperCase(), String.valueOf(data[1])
							.trim().replaceAll("　", ""), String
							.valueOf(data[2]).trim().toUpperCase());
					buffer.append("<name>").append(tmpStr).append("</name>");
				}
			} else {
				for (Object o : ret) {
					Object[] data = (Object[]) o;
					tmpStr = String.format("%s %s", String.valueOf(data[0])
							.trim().toUpperCase(), String.valueOf(data[1])
							.trim().replaceAll("　", ""));
					buffer.append("<name>").append(tmpStr).append("</name>");
				}
			}

			buffer.append("</message>");
			response.getWriter().write(buffer.toString());
		} else {
			response.getWriter().write("");
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
