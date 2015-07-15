package tw.edu.chit.struts.action.personnel.servlet;

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

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
/**
 * 取得職員照片
 * @author JOHN
 *
 */
public class getFTPhoto4Empl extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		String idno = request.getParameter("idno");
		if(idno==null){
			HttpSession session = request.getSession(false);
			UserCredential me=(UserCredential)session.getAttribute("Credential");
			//String techid=me.getMember().getIdno();
			idno=me.getMember().getIdno();
		}
		
		response.setContentType("image/jpeg");
		response.setHeader("Content-disposition","attachment;filename=studentinfo.jpg");
		
		
		String host=manager.ezGetString("SELECT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='host'");
		String username=manager.ezGetString("SELECT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='username'");
		String password=manager.ezGetString("SELECT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='password'");
		String folder=manager.ezGetString("SELECT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='folder'");
		
		FTPClient ftp = new FTPClient();
		FTPClientConfig config=new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		
		ftp.configure(config);
		ftp.connect(host);
		ftp.login(username, password);
		ftp.setFileType(ftp.BINARY_FILE_TYPE);//為2進位		
		String fileName=idno+".jpg";
		
		
		OutputStream out=response.getOutputStream();
		ftp.enterLocalPassiveMode();
		//System.out.println(ftp.retrieveFile(fileName, out));
		if(ftp.changeWorkingDirectory(folder)){//資料夾存在
			if(!ftp.retrieveFile(fileName, out)){
				ftp.retrieveFile("notFound.gif", out);
			}
			
	     }else{
	    	 //System.out.println("開始證明資料夾存在");
	    	 ftp.changeWorkingDirectory(folder);
	    	 //FTPFile[] files = ftp.listFiles();
	    	 //System.out.println("開始證明檔案存在");
	    	 ftp.retrieveFile("notFound.gif", out);
	     }
		
		response.setHeader("Content-disposition","attachment;filename="+idno+".jpg");
		ftp.disconnect();
		*/
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		String idno = request.getParameter("idno");
		response.setContentType("image/jpeg"); 
		
		List list=manager.hqlGetBy("FROM Empl WHERE idno='"+idno+"'");
		if(list.size()>0){
			
			Empl empl=(Empl)list.get(0);
			Blob b=empl.getPortrait();
			long size;
			try {
				size = b.length();
				byte[] bs = b.getBytes(1, (int)size);
				OutputStream outs = response.getOutputStream(); 
				outs.write(bs);
				outs.flush();
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
