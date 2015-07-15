package tw.edu.chit.struts.action.personnel.servlet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
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
import javax.swing.SwingConstants;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public class EmplCard extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
				
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		
		List empls=(List)session.getAttribute("empls");
		
		Document document = new Document(PageSize.A6.rotate(), 5,5,5,5);		
		ByteArrayOutputStream ba = new ByteArrayOutputStream();		
		
		try {
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			document.open();
			
			String jobName;
			String unitName;
			//String showPath=manager.ezGetString("SELECT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='showPath'");
			
			Empl empl;
			//URL url;
			Image image;
			PdfContentByte cb = writer.getDirectContent();
			
			for(int i=0; i<empls.size(); i++){				
				empl=(Empl)manager.hqlGetBy("FROM Empl WHERE idno='"+((Map)empls.get(i)).get("idno")+"'").get(0);
				unitName="";
				jobName=manager.ezGetString("SELECT name FROM CodeEmpl WHERE idno='"+empl.getPcode()+"'");
				if(jobName.indexOf("雇")>=0){
					//continue;
				}
				if(jobName.trim().equals("")||jobName==null){
					jobName="無職務";
					//continue;
				}
				//System.out.println(jobName);
				String Unit=empl.getUnit();
				if(Unit.trim().equals("")){
					Unit=empl.getUnit_module();
				}
				
				unitName=manager.ezGetString("SELECT idno2 FROM CodeEmpl WHERE category='unit' AND idno='"+Unit+"'");
				
				if(unitName.trim().equals("")){
					//System.out.println("SELECT name FROM CodeEmpl WHERE category='UnitTeach' AND idno='"+empl.getUnit()+"'");
					unitName=manager.ezGetString("SELECT name FROM CodeEmpl WHERE category='UnitTeach' AND idno='"+Unit+"'");	
				}
				if(unitName.trim().equals("")){
					unitName="無單位";
					continue;
				}
				
				//照片
				//url=new URL(showPath+empl.getIdno());
				//System.out.println(url);
				try{
					image=getImage(empl);
				}catch(Exception e){
					continue;
				}
							
				image.scaleAbsolute(54.94f, 74.37f);
				//image.setAbsolutePosition(17.5f, 160);	
				image.setAbsolutePosition(18f, 166);				
				
				//條碼
				Barcode39 code39 = new Barcode39();
				code39.setCode(empl.getIdno());
				code39.setBarHeight(20); 
				code39.setX(0.80f);//寬
				
				code39.setStartStopText(false);
				code39.setGuardBars(false);
				code39.setExtended(false);
				code39.setChecksumText(false);
				code39.setSize(-1f);
				Image imageCode39 = code39.createImageWithBarcode(cb, null, null);
				
				imageCode39.setAbsolutePosition(95, 150f);
				
				cb.setColorStroke(Color.white);
				cb.rectangle(18, 150, 54.94f, 9);//30
				cb.setLineWidth(10);
				cb.stroke();
				
				cb.closePath();
				cb.closePathEoFillStroke();
				cb.closePathFillStroke();
				cb.closePathStroke();
				
				//文字
				BaseFont bf = BaseFont.createFont("/kaiu.ttf", "Identity-H", BaseFont.EMBEDDED);
				cb.beginText();//單位
				if(unitName.trim().length()>8){
					cb.setFontAndSize(bf, 8);
				}else{
					cb.setFontAndSize(bf, 12);
				}
				
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, unitName, 118, 230, 0);
				cb.endText();
				
				cb.beginText();//姓名
				cb.setFontAndSize(bf, 12);
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, empl.getCname(), 118, 209, 0);
				cb.endText();
				
				cb.beginText();//職稱
				cb.setFontAndSize(bf, 12);
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, jobName, 118, 188, 0);
				cb.endText();
				
				
				document.add(image);
				document.add(imageCode39);
				document.newPage();
			}	
			
			document.close();
			writer.close();
			document=null;
			writer=null;
			
			response.setHeader("Content-Disposition","attachment;filename=EmplCard"+Math.random()*10+".pdf");
			response.setContentLength(ba.size());
			response.flushBuffer();
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			document.close();			
			document=null;
			response.setContentType("text/html; charset=UTF-8");
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment;filename=TechTimetable.pdf");
			response.setContentLength(ba.size());
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			out.flush();		
		}		
	}
	
	/**
	 * 生成照片
	 * @param stdImage
	 * @return
	 */
	private Image getImage(Empl empl){
		Graphics2D g2d = null;		
		Blob b=empl.getPortrait();
		long size;
		try {
			size = b.length();
			byte[] bs = b.getBytes(1, (int)size);
			return Image.getInstance(bs);
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}	
	
}