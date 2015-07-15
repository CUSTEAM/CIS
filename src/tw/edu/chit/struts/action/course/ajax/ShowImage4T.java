package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.StdImage4t;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
/**
 * 取得新生照片
 * @author JOHN
 *
 */
public class ShowImage4T extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		String Oid = request.getParameter("Oid");
		response.setContentType("image/jpeg"); 
		HttpSession session=request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		if(c==null){			
			response.sendRedirect("/CIS/pages/images/notFound.gif");
		}else{			
			List list=manager.hqlGetBy("FROM StdImage4t WHERE Oid='"+Oid+"'");
			if(list.size()>0){
				
				StdImage4t stdImage=(StdImage4t)list.get(0);
				Blob b=stdImage.getImage();
				long size;
				try {
					size = b.length();
					byte[] bs = b.getBytes(1, (int)size);
					OutputStream outs = response.getOutputStream(); 
					outs.write(bs);
					outs.close();
				} catch (IllegalStateException e) {
					response.sendRedirect("/CIS/pages/images/notFound.gif");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else{
				response.sendRedirect("/CIS/pages/images/notFound.gif");
			}			
		}
		
		
	}
}
