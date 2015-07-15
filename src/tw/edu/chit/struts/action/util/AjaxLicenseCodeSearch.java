package tw.edu.chit.struts.action.util;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.LicenseCode;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.util.IConstants;

public class AjaxLicenseCodeSearch extends HttpServlet {

	private static final long serialVersionUID = 2022871713256998148L;

	@Override
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(this.getServletContext());
		AdminManager am = (AdminManager) ac
				.getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);

		String licenseNameOrCode = new String(request.getParameter("code")
				.getBytes("ISO-8859-1"), "UTF-8").replaceAll("\\|", "");
		int limit = StringUtils.isBlank(request.getParameter("l")) ? -1
				: Integer.parseInt(request.getParameter("l"));
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		if (StringUtils.isNotBlank(licenseNameOrCode)) {

			LicenseCode code = new LicenseCode();
			code.setName("%" + licenseNameOrCode + "%");
			Example example = Example.create(code).ignoreCase().enableLike(
					MatchMode.ANYWHERE);
			List<Order> orders = new LinkedList<Order>();
			orders.add(Order.asc("code"));
			List<LicenseCode> codes = (List<LicenseCode>) am
					.findSQLWithCriteria(LicenseCode.class, example, null,
							orders, limit);

			if (codes.isEmpty()) {
				code = new LicenseCode();
				try{
					code.setCode(licenseNameOrCode);
					example = Example.create(code).ignoreCase().enableLike(
							MatchMode.START);
					codes = (List<LicenseCode>) am.findSQLWithCriteria(
							LicenseCode.class, example, null, orders, limit);
				}catch(Exception e){
					code.setCode(null);
					example = Example.create(code).ignoreCase().enableLike(
							MatchMode.START);
					codes = (List<LicenseCode>) am.findSQLWithCriteria(
							LicenseCode.class, example, null, orders, limit);
				}
				
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buffer.append("<message>");
			String tmpStr = null;
			for (LicenseCode lc : codes) {
				// 要濾掉"&"才行
				tmpStr = String.format("%s|%s|%s", StringUtils.trimToEmpty(lc
						.getCode().toString()), StringUtils.replace(StringUtils
						.replace(StringUtils.replace(StringUtils.trimToEmpty(lc
								.getName()), "（", "("), "）", ")"), "&", "與"),
						StringUtils.deleteWhitespace(StringUtils.trimToEmpty(lc
								.getLevel())));
				buffer.append("<name>").append(tmpStr).append("</name>");
			}

			buffer.append("</message>");
			//System.out.println(buffer.toString());
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
