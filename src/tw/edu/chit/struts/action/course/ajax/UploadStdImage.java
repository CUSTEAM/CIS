package tw.edu.chit.struts.action.course.ajax;



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.hibernate.Hibernate;

import tw.edu.chit.model.StdImage;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.portfolio.FtpClient;
import tw.edu.chit.struts.action.portfolio.ImageManager;

public class UploadStdImage extends BaseAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		/*
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("allUnit", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE (category='Unit' OR category='UnitTeach') ORDER BY sequence"));
		session.setAttribute("allShift", manager.ezGetBy("SELECT id, name FROM AMS_ShiftTime GROUP BY id"));
		*/
		setContentPage(request.getSession(false), "AMS/shift/EmplStaticShiftManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		
		DynaActionForm Form = (DynaActionForm) form;
	    FormFile upimage = (FormFile) Form.get("Filedata");	    		
	    StringBuffer studentNo=new StringBuffer(upimage.getFileName());
		studentNo.delete(studentNo.indexOf("."), studentNo.length());
		
		if(manager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE student_no='"+studentNo+"'")<1)return null;
		
		//上傳至本機
		String fullpath=session.getServletContext().getRealPath("/UserFiles/")+"/";		
		if(upimage!=null){  
           OutputStream fos=null;  
            try {              	
                fos=new FileOutputStream(fullpath+upimage.getFileName()); 
                fos.write(upimage.getFileData(),0,upimage.getFileSize());  
                fos.flush();  
            } catch (Exception e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }finally{  
                try{  
                	fos.close();  
                }catch(Exception e){}  
            }  
        }  
	    
		//改變大小
		ImageManager img=new ImageManager(fullpath+upimage.getFileName());		
		int height=img.getHeight();
		int width=img.getWidth();
		if(width>140){
			int ratio=img.getHeight()/140;
			img.reduceImg(fullpath+upimage.getFileName(), height/ratio, width/ratio);			
		}
		
		String folder;
		if(studentNo.indexOf("10")==0){
			folder=studentNo.substring(0, 3);
		}else{
			folder=studentNo.substring(0, 2);
		}
		
		try{
			String target="host_runtime";
			if(!manager.testOnlineServer())target="host_debug";			
			Map<String, String>ftpinfo=manager.ezGetMap("SELECT "+target+" as host, username, password, path FROM SYS_HOST WHERE useid='StdImage'");
			fullpath=fullpath.replace("\\", "/");
			FtpClient ftp=new FtpClient(ftpinfo.get("host"), ftpinfo.get("username"), ftpinfo.get("password"), null, null);				
			ftp.connect();				
			ftp.setLocalDir(fullpath+"/");			
			ftp.setServerDir(ftpinfo.get("path")+"/"+folder+"/");			
			ftp.put(upimage.getFileName(), true);
						
		}catch(Exception e){
			e.printStackTrace();				
		}
		return null;			
	}
	
	private boolean uploadImage2FTPServer(String FTPHost, String username, String password, String ServerDir, String LocalDir, String fileName){		
		
		
		//String FTPHost=ezGetString("SELeCT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='host'");
		//String username=ezGetString("SELeCT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='username'");
		//String password=ezGetString("SELeCT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='password'");
		//String ServerDir=ezGetString("SELeCT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='folder'");
		
		//System.out.println("fileName="+fileName);
		try{
			FtpClient ftp=new FtpClient(FTPHost, username, password, "", "");			
			ftp.connect();
			ftp.setLocalDir(LocalDir+"/");
			ftp.setServerDir(ServerDir+"/");						
			ftp.setBinaryTransfer(true);
			ftp.put(fileName, true);
			ftp.disconnect();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			//ftp.disconnect();
			return false;
		}		
	}
	
	
	
}
