package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
/**
 * 取得學生照片
 * @author JOHN
 *
 */
public class ShowImage extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		String studentNo = request.getParameter("studentNo");
		response.setContentType("image/jpeg");
		HttpSession session=request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		//System.out.println(session);
		if(c==null){
			response.sendRedirect("/CIS/pages/images/notFound.gif");
		}else{
			List list=manager.hqlGetBy("FROM StdImage WHERE studentNo='"+studentNo+"'");
			if(list.size()>0){
				
				StdImage stdImage=(StdImage)list.get(0);
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
		*/
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		
		
		
		String studentNo = request.getParameter("studentNo");
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
			   ipAddress = request.getRemoteAddr();  
		   }
		manager.executeSql("INSERT INTO getHackers(ip,log)VALUES('"+ipAddress+"', '取"+studentNo+"照片')");
		
		
		String dir;
		
		if(studentNo.indexOf("10")==0){
			dir=studentNo.substring(0, 3);
		}else{
			dir=studentNo.substring(0, 2);
		}
		
		String target="host_runtime";
		if(!manager.testOnlineServer())target="host_debug";
		
		Map<String, String>StdImage=manager.ezGetMap("SELECT "+target+" as host, username, password, path FROM SYS_HOST WHERE useid='StdImage'");
		
		
		response.setContentType("image/jpeg");
		response.setHeader("Content-disposition","attachment;filename=studentinfo.jpg");
		
		
		String host=StdImage.get("host");
		String username=StdImage.get("username");
		String password=StdImage.get("password");
		String path=StdImage.get("path");
		
		FTPClient ftp = new FTPClient();
		FTPClientConfig config=new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		
		ftp.configure(config);
		ftp.connect(host);
		ftp.login(username, password);
		ftp.setFileType(ftp.BINARY_FILE_TYPE);//為2進位
		String schoolYear=studentNo.substring(0, 2);		
		
		String fileName=studentNo+".jpg";
		
		
		OutputStream out=response.getOutputStream();
		ftp.enterLocalPassiveMode();
		
		if(ftp.changeWorkingDirectory(path+"/"+dir)){//資料夾存在			 			 
	    	 
			/*System.out.println("開始證明資料夾存在");
	    	FTPFile[] files = ftp.listFiles();		    	 
	    	for (int i = 0; i< files.length; i++){		    		
	    		System.out.println(files[i].getName());
	    	}
	    	//System.out.println("開始證明檔案存在");*/
	    	ftp.retrieveFile(fileName, out);
	    	 
	     }
		
		/*
		else{
	    	 
	    	 ftp.changeWorkingDirectory(folder);
	    	 FTPFile[] files = ftp.listFiles();		    	 
	    	 for (int i = 0; i< files.length; i++){		    		
	    		 System.out.println(files[i].getName());
	    	 }
	    	 
	    	 ftp.retrieveFile("notFound.jpg", out);
	    	 out.close();
	    	 
	     }
		*/
		
		response.setHeader("Content-disposition","attachment;filename="+studentNo+".jpg");
		ftp.disconnect();
		
		
		
		
	}
}
