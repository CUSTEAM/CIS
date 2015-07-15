package tw.edu.chit.struts.action.registration.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;
/**
 * 取得學生照片
 * @author JOHN
 *
 */
@SuppressWarnings("serial")
public class OldStudentInfo extends HttpServlet{
	@SuppressWarnings("static-access")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		String studentNo = request.getParameter("studentNo");
		
		response.setContentType("image/jpeg");
		response.setHeader("Content-disposition","attachment;filename=studentinfo.jpg");
		
		
		String host=manager.ezGetString("SELECT Value FROM Parameter WHERE Category='ftp' AND Name='host'");
		String username=manager.ezGetString("SELECT Value FROM Parameter WHERE Category='ftp' AND Name='username'");
		String password=manager.ezGetString("SELECT Value FROM Parameter WHERE Category='ftp' AND Name='password'");
		String folder=manager.ezGetString("SELECT Value FROM Parameter WHERE Category='ftp' AND Name='folder'");
		
		FTPClient ftp = new FTPClient();
		FTPClientConfig config=new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		
		ftp.configure(config);
		ftp.connect(host);
		ftp.login(username, password);
		ftp.setFileType(ftp.BINARY_FILE_TYPE);//為2進位
		String schoolYear=studentNo.substring(0, 2);		
		
		String fileName="";
		if(request.getParameter("type")==null){//學卡
			fileName=studentNo+"-1.jpg"; 
		}else{//成績單
			fileName=studentNo+"-2.jpg"; 
		}
		
		OutputStream out=response.getOutputStream();
		ftp.enterLocalPassiveMode();
		
		if(ftp.changeWorkingDirectory(folder+"/"+schoolYear)){//資料夾存在			 			 
	    	 
			//System.out.println("開始證明資料夾存在");
	    	FTPFile[] files = ftp.listFiles();		    	 
	    	for (int i = 0; i< files.length; i++){		    		
	    		System.out.println(files[i].getName());
	    	}
	    	//System.out.println("開始證明檔案存在");
	    	ftp.retrieveFile(fileName, out);
	    	 
	     }else{
	    	 //System.out.println("開始證明資料夾存在");
	    	 ftp.changeWorkingDirectory(folder);
	    	 FTPFile[] files = ftp.listFiles();		    	 
	    	 for (int i = 0; i< files.length; i++){		    		
	    		 System.out.println(files[i].getName());
	    	 }
	    	 //System.out.println("開始證明檔案存在");
	    	 ftp.retrieveFile("notFound.jpg", out);
	    	 out.close();
	    	 
	     }
		
		response.setHeader("Content-disposition","attachment;filename=test.jpg");
		ftp.disconnect();
	}
}
