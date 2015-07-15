package tw.edu.chit.service.filter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.gui.Menu;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.InvalidAccountException;
import tw.edu.chit.service.MemberManager;

/**
 * 動作過濾
 * @author shawn
 *
 */
public class CheckReg implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		
		HttpSession session=((HttpServletRequest) request).getSession(true);
		BeanFactory context=WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) request).getSession().getServletContext());
		CourseManager cm=(CourseManager) context.getBean("courseManager");		
		MemberManager mm=(MemberManager) context.getBean("memberManager");		
		String Action=(((HttpServletRequest) request).getServletPath());
		//登入登出不過濾
		if(Action.indexOf("Welcome")>-1||
				Action.indexOf("Log")>-1||
				Action.indexOf("ListStudent")>-1||
				Action.indexOf("ListCourse")>-1||
				Action.indexOf("TeacherStayTimeSearch")>-1||
				Action.indexOf("Freshman")>-1||
				Action.indexOf("UploadStdImage")>-1||
				Action.indexOf("SylDoc")>-1||
				//Action.indexOf("Query")>-1||
				Action.indexOf("QueryR")>-1||
				Action.indexOf("IntorDoc")>-1){
			chain.doFilter(request, response);
			return;
		}
		
		//處理跳轉使用者UploadStdImage
		if(session.getAttribute("Credential")==null){	
			Cookie[] k = ((HttpServletRequest) request).getCookies();
			Map m;
			for(int i=0; i<k.length; i++){			
	    		if(k[i].getName().equals("userid")){
	    			m=cm.ezGetMap("SELECT username, password FROM wwpass WHERE sessionid='"+k[i].getValue()+"'");
	    			try {
						UserCredential user = mm.createUserCredential(m.get("username").toString(), m.get("password").toString());
						
						if (UserCredential.PrioEmployee.equals(user.getMember().getPriority())) {				
													
						//學生登入
						}else{							
							//教學評量
							SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
							Date start=sf.parse(cm.ezGetString("SELECT Value FROM Parameter WHERE Name='CoanswStart'"));//教學評量開始日
							Date end=sf.parse(cm.ezGetString("SELECT Value FROM Parameter WHERE Name='CoanswEnd'"));//教學評量結束日
							cm.setCoansFoRm(new Date(), session, user, start, end);						
						}
						session.setAttribute("Credential", user);						
						request.setAttribute("fastmenu", cm.ezGetBy("SELECT m.color, m.Action, m.Icon, m.Label FROM Module m, User_Module_hist um WHERE " +
						"m.Oid=um.ModuleOid AND username='"+user.getMember().getAccount()+"' ORDER BY um.Oid DESC"));
					} catch (Exception e) {
						e.printStackTrace();
					}
	    			chain.doFilter(request, response);
	    			return;
	    		}    	
			}
		}		
		
		//若仍無登入資訊
		if(session.getAttribute("Credential")==null){
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect("/CIS/Logout.do");
			return;
		}
		
		chain.doFilter(request, response);
	}

	public void destroy() {}	
}
