package tw.edu.chit.struts.action.portfolio.student;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.Toolket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * ucan sso
 * @author shawn
 *
 */
public class UcanLoginAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {return null;}
			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType){}
			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType){}
		} };

		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e3) {
			return mapping.findForward("Main");
		}
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		URL url = new URL("https://ucan.moe.edu.tw/bin/index.php?Plugin=o_hdu&Action=ohduschoolssogettoken&username=1145"+c.getMember().getAccount()+"&school=1145");

		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		String tmp2 = "";
		while (br.ready()) {
			tmp2 = br.readLine();
		}

		request.setAttribute("token", tmp2);
		request.setAttribute("username", c.getMember().getAccount());
		request.setAttribute("school", "1145");

		//System.out.println(tmp2);
		//System.out.println(c.getMember().getAccount());

		return mapping.findForward("ucanlogin");
	}

}