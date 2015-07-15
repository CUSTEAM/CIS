package tw.edu.chit.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AccessControlFilter implements Filter {

	private static Log log = LogFactory.getLog(AccessControlFilter.class);
	
	protected String encoding = "UTF-8";
	protected FilterConfig filterConfig = null;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("=======> Entering AccessControlFilter.init() ...");
		this.filterConfig = filterConfig;
		//this.encoding = filterConfig.getInitParameter("encoding");
	}

	public void doFilter(ServletRequest request,
						 ServletResponse response,
						 FilterChain chain)
			throws IOException, ServletException {
		
		//long startTime = System.currentTimeMillis();
		request.setCharacterEncoding(encoding);
		// Check if a session was there
		if (((HttpServletRequest)request).getSession(false) == null) {
			String uri = ((HttpServletRequest)request).getServletPath();
			if (uri.indexOf("/index.jsp") == -1 
			 && uri.indexOf("/Welcome.do") == -1
			 && uri.indexOf("/Login.do") == -1
			 && uri.indexOf("/Logout.do") == -1
			 && uri.indexOf("/inetIndex.jsp") == -1
			 && uri.indexOf("/InetWelcome.do") == -1
			 && uri.indexOf("/InetLogin.do") == -1
			 && uri.indexOf("/InetLogout.do") == -1
			 && uri.indexOf("/inetIndex4Parent.jsp") == -1
			 && uri.indexOf("/InetWelcome4Parent.do") == -1
			 && uri.indexOf("/InetLogin4Parent.do") == -1
			 && uri.indexOf("/InetLogout4Parent.do") == -1
			 && uri.indexOf("/ClassCourseSearch.do") == -1
			 && uri.indexOf("/TeacherCourseSearch.do") == -1
			 && uri.indexOf("/QueryByCourseName.do") == -1
			 && uri.indexOf("/QueryByWeekTime.do") == -1
			 && uri.indexOf("/LiteracyClassSearch.do") == -1
			 && uri.indexOf("/CountStudent") == -1
			 && uri.indexOf("/OpenCourseSearch.do") == -1) {

				try {
					Thread.sleep(3000);
				} catch(Exception e) {}
				throw new ServletException(uri + " ===> 警告!!! 請勿入侵");
			}
		}
		// Pass control on to the next filter
		chain.doFilter(request, response);
		
		//long stopTime = System.currentTimeMillis();
		//request.setAttribute("ExecTime", stopTime - startTime);
		//Toolket.setCookie((HttpServletResponse)response, "ExecTime", String.valueOf(stopTime - startTime));
	}

	public void destroy() {
		this.filterConfig = null;
	}

}
