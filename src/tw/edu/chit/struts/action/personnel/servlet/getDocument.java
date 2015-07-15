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

import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.portfolio.FtpClient;
/**
 * 取得職員照片
 * @author JOHN
 *
 */
public class getDocument extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		
		//String folder=request.getParameter("folder");
		String folder="AAA_LEO_992";
		String FTPHost= "kk.cust.edu.tw";
		String username= "kk";
		String password= "2010";
		//String ServerDir= "/home/kk/ftp/File/"+FN_Name;
		String ServerDir="/home/kk/ftp/File/"+"AAA_LEO_992"+"/";
		
		try{
			FtpClient ftp=new FtpClient(FTPHost, username, password, "", "");
			ftp.connect();
			ftp.setServerDir(ServerDir);						
			ftp.setBinaryTransfer(true);
			
			
			
			
			
			
			
			
			
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			//ftp.disconnect();
			
		}	
		
		/*FTPClient ftp = new FTPClient();
		//FTPClientConfig config=new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		FtpClient ftp=new FtpClient(host, username, password, "", "");
		ftp.connect();
		ftp.configure(config);
		ftp.connect(host);
		ftp.login(username, password);
		ftp.setFileType(ftp.BINARY_FILE_TYPE);//為2進位
		ftp.setServerDir(ServerDir);	
		//String fileName=idno+".jpg";
		String fileName="Chapter3.pdf";
		
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
		ftp.disconnect();*/
		
		//String FTPHost= "kk.cust.edu.tw";
		//String username= "kk";
		//String password= "2010";
		//String ServerDir= "/home/kk/ftp/File/"+FN_Name;
		//String ServerDir="/home/kk/ftp/File/"+ftpDir+"/";
		//System.out.println(ServerDir);
			
		
		
	}
}
