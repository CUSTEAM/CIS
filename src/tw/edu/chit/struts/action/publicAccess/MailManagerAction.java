package tw.edu.chit.struts.action.publicAccess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import sun.misc.BASE64Encoder;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class MailManagerAction extends BaseLookupDispatchAction{
	BASE64Encoder enc = new sun.misc.BASE64Encoder();
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");

		//自動完成員工分類
		request.setAttribute("allCategory", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='EmpCategory' ORDER BY sequence"));
		//自動完成所有單位
		request.setAttribute("allUnit", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE (category='Unit' OR category='UnitTeach') ORDER BY sequence"));
		//自動完成所有職級
		List allPcode=manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='TeacherRole' ORDER BY sequence");		
		allPcode.addAll(manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='EmplRoleStaff' ORDER BY sequence"));
		request.setAttribute("allPcode", allPcode);
		//自動完成所有導師
		request.setAttribute("allTutor", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='Tutor' ORDER BY sequence"));
		//自動完成兼任職級
		request.setAttribute("allDirector", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='EmplRoleDirector' ORDER BY sequence"));		
		
		UserCredential cr=(UserCredential) session.getAttribute("Credential");
		
		request.setAttribute("group", manager.ezGetBy("SELECT * FROM TxtGroup WHERE account='All'"));
		request.setAttribute("myGroup", manager.ezGetBy("SELECT * FROM TxtGroup WHERE account='"+cr.getMember().getAccount()+"'"));
		setContentPage(request.getSession(false), "publicAccess/MailManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward sendMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm mailForm = (DynaActionForm) form;
		Member me = getUserCredential(request.getSession(false)).getMember();
		String fileHome="/home/tmp/";
		if(!manager.testOnlineServer()){
			fileHome="tmp/";
		}
		
		File home=new File(fileHome);		
		home.mkdirs();
		
		//登入郵件伺服器
		String username=manager.ezGetString("SELECT Value FROM Parameter WHERE Name='username' AND Category='smtp'");
		String password=manager.ezGetString("SELECT Value FROM Parameter WHERE Name='password' AND Category='smtp'");
		String server =manager.ezGetString("SELECT Value FROM Parameter WHERE Name='mailServer' AND category='smtp'");
		String subject=mailForm.getString("subject");//主旨
		String content=mailForm.getString("content");//內容
		String receiver=mailForm.getString("receiver");//收件者
		String from=manager.ezGetString("SELECT Email FROM empl WHERE idno='"+me.getIdno()+"'");
		
		FormFile file1 = (FormFile) mailForm.get("file1");
		FormFile file2 = (FormFile) mailForm.get("file2");
		FormFile file3 = (FormFile) mailForm.get("file3");
		FormFile file4 = (FormFile) mailForm.get("file4");
		FormFile file5 = (FormFile) mailForm.get("file5");
		
		File f1=null;
		File f2=null;
		File f3=null;
		File f4=null;
		File f5=null;
		
		InputStream stream=null;
		OutputStream bos=null;
		byte[] buffer = new byte[8192];
		int bytesRead = 0;	
		FileDataSource files[]=new FileDataSource[5];
		try{
			f1=new File(fileHome+file1.getFileName());
			stream  =  file1.getInputStream();			 
			bos=new FileOutputStream(f1);	 
			file1.getInputStream();
			while((bytesRead = stream.read(buffer, 0, 8192)) != -1){
				bos.write(buffer, 0, bytesRead);
			}
			files[0]=new FileDataSource(f1);
			stream.close();
			bos.close();
		}catch(Exception e){
			//e.printStackTrace();
		}		
							
		try{
			f2=new File(fileHome+file2.getFileName());
			stream  =  file2.getInputStream();			 
			bos=new FileOutputStream(f2);	 
			file2.getInputStream();
			while((bytesRead = stream.read(buffer, 0, 8192)) != -1){
				bos.write(buffer, 0, bytesRead);				
			}
			files[1]=new FileDataSource(f2);
			stream.close();
			bos.close();
		}catch(Exception e){
			//e.printStackTrace();
		}	
						
		try{
			f3=new File(fileHome+file3.getFileName());	
			stream  =  file3.getInputStream();			 
			bos=new FileOutputStream(f3);	 
			file3.getInputStream();
			while((bytesRead = stream.read(buffer, 0, 8192)) != -1){
				bos.write(buffer, 0, bytesRead);
			}
			files[2]=new FileDataSource(f3);
			stream.close();
			bos.close();
		}catch(Exception e){
			//e.printStackTrace();
		}	
						
		try{
			f4=new File(fileHome+file4.getFileName());
			stream  =  file4.getInputStream();			 
			bos=new FileOutputStream(f4);	 
			file4.getInputStream();
			while((bytesRead = stream.read(buffer, 0, 8192)) != -1){
				bos.write(buffer, 0, bytesRead);
			}
			files[3]=new FileDataSource(f4);
			stream.close();
			bos.close();
		}catch(Exception e){
			//e.printStackTrace();
		}			
						
		try{
			f5=new File(fileHome+file5.getFileName());
			stream  =  file5.getInputStream();			 
			bos=new FileOutputStream(f5);	 
			file5.getInputStream();
			while((bytesRead = stream.read(buffer, 0, 8192)) != -1){
				bos.write(buffer, 0, bytesRead);
			}
			files[4]=new FileDataSource(f5);
			stream.close();
			bos.close();
		}catch(Exception e){
			//e.printStackTrace();
		}		
		
		
		ServletContext context = request.getSession().getServletContext();
		String schoolName=(String) context.getAttribute("SchoolName_ZH");		
		manager.sendMailSimple(username, password, server, from, me.getName(), subject, content, receiver, files);
		f1.delete();
		f2.delete();
		f3.delete();
		f4.delete();
		f5.delete();
		home.delete();		
		
		return unspecified(mapping, form, request, response);
	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("FindMamber", "findMamber");
		map.put("SendMail", "sendMail");
		map.put("SendCancel", "sendCancel");
		return map;
	}

}


