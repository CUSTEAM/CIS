package tw.edu.chit.midp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import tw.edu.chit.service.AdminManager;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -1449184225329807438L;
	private final Logger log = Logger.getLogger(LoginServlet.class);

	public void init() throws ServletException {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String no = request.getParameter("no");
		String password = request.getParameter("pw");
		log.info("Username: " + no);
		log.info("Password: " + password);
		String message = "ERROR";
		ApplicationContext ac = Global.context;

		try {
			AdminManager am = (AdminManager) ac
					.getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
			message = "OK";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			message = "ERROR";
		}
		
		Toolket.sendPlainMessageForMIDP(request, response, message);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
