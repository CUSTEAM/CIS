package tw.edu.chit.struts.action.course.ajax;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public class StudentCardForOne extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		boolean b=false;
		if(request.getParameter("count")!=null){
			b=true;
		}
		
		List students=(List)session.getAttribute("students");
		
		
		
		ByteArrayOutputStream ba = new ByteArrayOutputStream();	
		
		
		try {
			Document document = new Document(PageSize.A6.rotate(), 5,5,5,5);
			
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			document.open();
			Image image=null;			
			
			Clazz clazz;
			Student stmd;
			String SchoolName;
			String DeptName;
			StringBuilder StudentNo;
			
			Map map;
			Map StmdCardNum;
			
			//writer = PdfWriter.getInstance(document, ba);
			PdfContentByte cb = writer.getDirectContent();
			BaseFont bf = BaseFont.createFont("/kaiu.ttf", "Identity-H", BaseFont.EMBEDDED);
			
			for(int i=0; i<students.size(); i++){
				
				stmd=(Student)manager.hqlGetBy("FROM Student WHERE studentNo='"+((Map)students.get(i)).get("student_no")+"'").get(0);
				clazz=(Clazz)manager.hqlGetBy("FROM Clazz WHERE ClassNo='"+stmd.getDepartClass()+"'").get(0);
				map=manager.ezGetMap("SELECT * FROM dept WHERE no='"+clazz.getClassNo().substring(0, 4)+"'");
				
				try{//系所名
					DeptName=map.get("fname").toString();
				}catch(Exception e){
					DeptName="";
				}
				try{//部制名
					SchoolName=map.get("school_name").toString();
				}catch(Exception e){
					SchoolName="";
				}
				StudentNo=new StringBuilder(stmd.getStudentNo());				
				//List list=manager.hqlGetBy("FROM StdImage WHERE studentNo='"+StudentNo+"'");				
				
				/*
				if(b&&manager.testOnlineServer()){//寫入補發記錄
					try{
						//真實學號
						manager.executeSql("INSERT INTO StmdCardNum (student_no, card_num)VALUES('"+stmd.getStudentNo()+"', 1)");						
					}catch(Exception e){
						manager.executeSql("UPDATE StmdCardNum SET card_num=card_num+1 WHERE student_no='"+stmd.getStudentNo()+"'");
					}
					StudentNo.append(manager.ezGetString("SELECT card_num FROM StmdCardNum WHERE student_no='"+stmd.getStudentNo()+"'"));
					sendMailToLib(stmd, StudentNo.toString(), request);
				}else{
					//檢查是否已發卡
					StmdCardNum=manager.ezGetMap("SELECT * FROM StmdCardNum WHERE student_no='"+stmd.getStudentNo()+"'");
					if(StmdCardNum!=null){
						StudentNo.append(manager.ezGetString("SELECT card_num FROM StmdCardNum WHERE student_no='"+stmd.getStudentNo()+"'"));
					}					
				}
				*/
				
				
				StmdCardNum=manager.ezGetMap("SELECT * FROM StmdCardNum WHERE student_no='");				
				
				// 學號不滿8碼補空白, 未來改為9碼時？
				if(StudentNo.length()<=8){
					for(int j=StudentNo.length(); j<8; j++){
						StudentNo.append(" ");
					}
				}				
				//照片
				try{
					image=getImage(StudentNo.toString());
				}catch(Exception e){
					continue;
				}
				if(image==null)continue;
				
				image.scaleAbsolute(54.94f, 74.37f);
				image.setAbsolutePosition(18, 166);				
				
				//條碼
				Barcode39 code39 = new Barcode39();					
				code39.setCode(StudentNo.toString());
				code39.setBarHeight(20); 
				code39.setX(0.95f);
				
				code39.setStartStopText(false);
				code39.setGuardBars(false);
				code39.setExtended(false);
				code39.setChecksumText(false);
				code39.setSize(-1f);
				Image imageCode39 = code39.createImageWithBarcode(cb, null, null);
				
				imageCode39.setAbsolutePosition(83.15f, 152);
				
				cb.setColorStroke(Color.white);
				cb.rectangle(18, 150, 54.94f, 9);//30
				cb.setLineWidth(10);
				cb.stroke();
				
				cb.closePath();
				cb.closePathEoFillStroke();
				cb.closePathFillStroke();
				cb.closePathStroke();
				
				//文字
				
				cb.beginText();//部制
				cb.setFontAndSize(bf, 10);
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, SchoolName, 118, 229, 0);
				cb.endText();
				
				cb.beginText();//科系
				cb.setFontAndSize(bf, 10);
				if(DeptName.length()>=9){
					cb.setFontAndSize(bf, 8);
				}
				if(DeptName.length()>=14){
					cb.setFontAndSize(bf, 6);
				}
				
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, DeptName, 118, 215, 0);
				cb.endText();
				
				cb.beginText();//學號
				cb.setFontAndSize(bf, 10);
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, stmd.getStudentNo(), 118, 201, 0);
				cb.endText();
				
				cb.beginText();//姓名
				cb.setFontAndSize(bf, 10);
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, stmd.getStudentName(), 118, 187, 0);
				cb.endText();				
				
				document.add(image);
				document.add(imageCode39);
				document.newPage();
				//bf=null;
			}	
			
			
			document.close();
			writer.close();
			
			
			response.setHeader("Content-Disposition","attachment;filename=EmplCard"+Math.random()*10+".pdf");
			response.setContentLength(ba.size());
			response.flushBuffer();
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			ba.close();
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Document document = new Document(PageSize.A6.rotate(), 5,5,5,5);
				PdfWriter writer = PdfWriter.getInstance(document, ba);
				document.open();
				PdfContentByte cb = writer.getDirectContent();
				BaseFont bf = BaseFont.createFont("/kaiu.ttf", "Identity-H", BaseFont.EMBEDDED);
				cb.beginText();//部制
				cb.setFontAndSize(bf, 10);
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "讀取資料有誤:"+e, 120, 226, 0);
				cb.endText();
				//document.add(cb );
				document.close();			
				document=null;
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			response.setContentType("text/html; charset=UTF-8");
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment;filename=TechTimetable.pdf");
			response.setContentLength(ba.size());
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			out.flush();		
		}		
	}	
	
	private Phrase doEncode(String nowBy) {

		BaseFont bf;
		Font font = null;
		Phrase p = null;
		try {
			bf = BaseFont.createFont("/kaiu.ttf", "Identity-H", BaseFont.EMBEDDED);
			//bf = BaseFont.createFont("/kaiu.ttf", "Identity-H", BaseFont.NOT_EMBEDDED);
			//bf = BaseFont.createFont("/pl.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			font = new Font(bf, 12, font.NORMAL);
			p=new Phrase(nowBy, font);
		}catch (Exception e) {
			e.printStackTrace();		}
		return p;
	}
	
	private Phrase doEncode(String nowBy, int size) {
		BaseFont bf;
		Font font = null;
		Phrase p = null;
		try {
			bf = BaseFont.createFont("/kaiu.ttf", "Identity-H", BaseFont.EMBEDDED);
			font = new Font(bf, size, font.NORMAL);
			p=new Phrase(nowBy, font);
		}catch (Exception e) {
			e.printStackTrace();		
		}
		return p;
	}
	
	private Phrase doEncodeSmall(String nowBy) {
		BaseFont bf;
		Font font = null;
		Phrase p = null;
		try {
			bf = BaseFont.createFont("/kaiu.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			font = new Font(bf, 4, font.NORMAL);
			p=new Phrase(nowBy, font);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	/**
	 * 生成照片
	 * @param stdImage
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws BadElementException 
	 */
	private Image getImage(String StudentNo) throws BadElementException, MalformedURLException, IOException{
		/*
		Graphics2D g2d = null;		
		Blob b=stdImage.getImage();
		long size;
		try {
			size = b.length();
			byte[] bs = b.getBytes(1, (int)size);
			return Image.getInstance(bs);			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		*/
		//StdImage stdImage=(StdImage)list.get(0);
		//Blob b=stdImage.getImage();
		/*
		long size;
		try {
			size = b.length();
			byte[] bs = b.getBytes(1, (int)size);
			return Image.getInstance(bs);
		} catch (Exception e) {
			e.printStackTrace();
			//URL url = new URL("http://ap.cust.edu.tw/CIS/pages/images/notFound.gif"); 
			Image image=Image.getInstance("http://ap.cust.edu.tw/CIS/pages/images/notFound.gif"); 
			//response.sendRedirect("/CIS/pages/images/notFound.gif");
			return image;
		}
		*/
		try{
			return Image.getInstance("http://localhost/eis/getStdimage?myStdNo="+StudentNo);
		}catch(Exception e){
			return null;
		}
		
	}
	
	/**
	 * 傳送郵件
	 
	private void sendMailToLib(Student stmd, String cardNo, HttpServletRequest request){
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential) session.getAttribute("Credential");
		
		StringBuilder sb2=new StringBuilder();
		Map map;
		Map dept;
		try{			
			map=manager.ezGetMap("SELECT * FROM stmd WHERE student_no='"+stmd.getStudentNo()+"'");
			dept=manager.ezGetMap("SELECT d.* FROM Class c, dept d WHERE c.Dept=d.no AND c.ClassNo='"+map.get("depart_class")+"'");
			if(dept!=null){
				sb2.append("學制:"+map.get("depart_class")+dept.get("school_name")+dept.get("fname"));
			}else{
				sb2.append("學制:"+map.get("depart_class")+"資料有誤");
			}			
			sb2.append("<br>身分證:"+map.get("idno"));
			sb2.append("<br>出生日期"+map.get("birthday"));
			sb2.append("<br>入學年月"+map.get("entrance"));
			sb2.append("<br>通訊地址"+map.get("curr_post")+map.get("curr_addr"));
			sb2.append("<br>戶籍地址"+map.get("perm_post")+map.get("perm_addr"));
			sb2.append("<br>連絡電話"+map.get("telephone")+map.get("perm_addr"));
			sb2.append("<br>行動電話"+map.get("CellPhone")+map.get("perm_addr"));
		}catch(Exception e){
			sb2.append("很抱歉, 學生資料有誤, 系統無法取得");
		}		
		StringBuilder sb=new StringBuilder("各位同仁您好:<br><br>學生 "+stmd.getStudentNo()+stmd.getStudentName()+"的學生證已補發完成, <br>" +
				"新的條碼編號為: "+cardNo+"<br><br>學生資料如下:<br>"+sb2);		
		List libs=manager.ezGetBy("SELECT e.cname, e.Email FROM UnitBelong u, empl e WHERE u.UnitNo='L0' AND e.Oid=u.EmpOid");
		libs.add(manager.ezGetMap("SELECT cname, Email FROM empl WHERE idno='"+credential.getMember().getIdno()+"'"));
		InternetAddress address[] = new InternetAddress[libs.size()];		
		try {
			for(int i=0; i<libs.size(); i++){
				try{
					address[i]=new InternetAddress(String.valueOf(((Map)libs.get(i)).get("Email")), String.valueOf(((Map)libs.get(i)).get("cname")), "BIG5");
				}catch(Exception e){
					address[i]=null;
				}				
			}			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		map=manager.getCISMailServerInfo();		
		ServletContext context = request.getSession().getServletContext();
		//String schoolName=(String) context.getAttribute("SchoolName_ZH");
		manager.sendMail(
		map.get("username").toString(), 
		map.get("password").toString(), 
		map.get("mailServer").toString(), 
		map.get("mailAddress").toString(), 
		(String) context.getAttribute("SchoolName_ZH")+"校務資訊系統", 
		new Date(), 
		"學生證補發印製完成通知", 
		sb.toString(), 
		address, 
		null);
	}
	*/
}