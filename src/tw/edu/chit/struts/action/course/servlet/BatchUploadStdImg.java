package tw.edu.chit.struts.action.course.servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;
import org.hibernate.Hibernate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.StdImage;
import tw.edu.chit.service.CourseManager;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class BatchUploadStdImg extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		//HttpSession session = request.getSession(false);		
		//HttpSession session = request.getSession(false);
		System.out.println("work");
		//DynaActionForm Form = (DynaActionForm) form;
	    FormFile upimage = (FormFile) request.getAttribute("Filedata");
		//File file=new File(t);
	    //改變大小
		BufferedImage srcImage, destImage;
		double width, height;
		Graphics2D g2d;		
		int size=139;//證照目前不超出140px
		try {
			srcImage = ImageIO.read(upimage.getInputStream());
			width=srcImage.getWidth();
		    height=srcImage.getHeight();			
		    //長寬比例不合
		    //if(width>height){}					    	
			double y=size/width;
			height=height*y;	
	    	destImage = new BufferedImage(size, (int) height, BufferedImage.TYPE_INT_RGB);
		    g2d = (Graphics2D) destImage.getGraphics();			    
		    g2d.scale(y, y);		    
		    g2d.drawImage(srcImage,0,0,null);		  
				
		}catch (IOException e1) {			
			g2d=null;
		    System.gc();
		    destImage=null;
		    srcImage=null;
			e1.printStackTrace();			
		}	    
	    
		StringBuffer studentNo=new StringBuffer(upimage.getFileName());
		studentNo.delete(studentNo.indexOf("."), studentNo.length());
		//if(studentNo.length()>1){
			//studentNo.delete(7, studentNo.length());
		//}
		
		manager.executeSql("DELETE FROM StdImage WHERE studentNo='"+studentNo+"'"); //無論如何只殺不留
		StdImage image=new StdImage();		
		
		//將BufferedImage轉成byte[]
		ByteArrayOutputStream os = new ByteArrayOutputStream();		
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);		
		encoder.encode(destImage);
		
		//儲存byte[]
		Blob photo = Hibernate.createBlob(os.toByteArray());
		image.setImage(photo);		
		image.setStudentNo(studentNo.toString());
		manager.updateObject(image);//完成
		
		//清除
		g2d.dispose();
	    srcImage.flush();
	    destImage.flush();
	    //System.gc();
		//return mapping.findForward("UploadImage");
		
	}

}
