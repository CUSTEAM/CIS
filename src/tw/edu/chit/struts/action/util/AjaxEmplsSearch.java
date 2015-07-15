package tw.edu.chit.struts.action.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Empl;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.util.IConstants;

public class AjaxEmplsSearch extends HttpServlet {

	private static final long serialVersionUID = 2022871713256998144L;

	@Override
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(this.getServletContext());
		AdminManager am = (AdminManager) ac
				.getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);

		String idnoOrname = new String(request.getParameter("idno").getBytes(
				"ISO-8859-1"), "UTF-8");
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		if (StringUtils.isNotBlank(idnoOrname)) {

			Empl empl = new Empl();
			empl.setCname(idnoOrname);
			Example example = Example.create(empl).ignoreCase().enableLike(
					MatchMode.ANYWHERE);
			List<Empl> empls = (List<Empl>) am.findSQLWithCriteria(Empl.class,
					example, null, null);

			if (empls.isEmpty()) {
				empl = new Empl();
				empl.setIdno(idnoOrname);
				example = Example.create(empl).ignoreCase().enableLike(
						MatchMode.ANYWHERE);
				empls = (List<Empl>) am.findSQLWithCriteria(Empl.class,
						example, null, null);
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buffer.append("<message>");
			for (Empl e : empls) {
				buffer.append("<name>")
						.append(e.getIdno().trim().toUpperCase()).append(" ")
						.append(e.getCname().trim()).append("</name>");
			}

			buffer.append("</message>");
			response.getWriter().write(buffer.toString());
		} else {
			response.getWriter().write("");
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
