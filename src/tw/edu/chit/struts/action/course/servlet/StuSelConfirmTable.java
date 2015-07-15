package tw.edu.chit.struts.action.course.servlet;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.util.Global;

/**
 * 學生選課簽收單
 * @author JOHN
 *
 */
public class StuSelConfirmTable extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);		
		List dtimeList=(List)session.getAttribute("dtimeList");
		
		//取得所有開課班級
		StringBuilder sb=new StringBuilder("SELECT d.depart_class, c.ClassName FROM Dtime d, Class c WHERE c.ClassNo=d.depart_class AND d.depart_class NOT LIKE '%0%'" +
				"AND d.depart_class IN(");
		for(int i=0; i<dtimeList.size(); i++){
			sb.append("'"+((Map)dtimeList.get(i)).get("departClass")+"', " );
		}
		
		sb.delete(sb.length()-2, sb.length()-1);
		sb.append(") GROUP BY d.depart_class ORDER BY d.depart_class");
		List classes=manager.ezGetBy(sb.toString());
		
		List myCs;
		float myCredit;
		int myThour;
		int myCss;
		
		Document document = new Document(PageSize.A4, 30,30,10,10);
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		
		Date date=new Date();
		DateFormat dateFormat=new SimpleDateFormat("yyyy年 MM月 dd日 HH:mm");
		
		String term=session.getAttribute("xterm").toString();//現在學期
		String year=manager.getNowBy("School_year");//現在學年		
		
		//第二學期6月後學年+1
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		if(c.get(Calendar.MONTH)>=6 && c.get(Calendar.MONTH)<=7 && term.equals("2")){
			int tmp=Integer.parseInt(year)+1;
			year=tmp+"";
		}
		float[] widths = {0.2f, 0.2f, 0.1f, 0.1f, 0.1f, 0.3f};
		try{
			
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			//Image image=Image.getInstance("http://cap.chit.edu.tw/CIS/pages/images/2002chitWatermark.gif");
			//Watermark wamark=new Watermark(image, ((PageSize.A4).width()-image.plainHeight())/2, ((PageSize.A4).height()-image.plainHeight())/2);
			//document.add(wamark);
			//Phrase now=doEncode("課程管理系統 "+dateFormat.format(date), 10);
			//HeaderFooter header=new HeaderFooter(now,false);
			//document.setHeader(header);
			//document.setFooter(header);
			document.open();
			
			
			
			for(int i=0; i<classes.size(); i++){
				Table table=new Table(6);
				table.setWidth(100);
				//table.setDefaultCellBorderColor(new Color(255, 255, 255));
				Cell cell=new Cell();
				
				cell.add(doEncodeKaiu(year+"學年第", 12));
				cell.add(doEncodeKaiu(term, 12));
				cell.add(doEncodeKaiu("學期 "+((Map)classes.get(i)).get("ClassName")+"選課簽收單", 12));
				//cell.addElement(logo);
				cell.setColspan(6);
				cell.setHorizontalAlignment(cell.ALIGN_CENTER);
				cell.setBorderColor(new Color(255, 255, 255));
				table.addCell(cell);
				table.setBorderWidth(0);
				table.setPadding(5);
				document.add(table);
				List students=manager.ezGetBy("SELECT st.student_no, st.student_name, cl.ClassName FROM stmd st, Class cl " +
						"WHERE st.depart_class=cl.ClassNo AND st.depart_class LIKE '"+((Map)classes.get(i)).get("depart_class")+"'");
				
				
				PdfPTable tables = new PdfPTable(widths);
				tables.setWidthPercentage(100);

			    PdfPCell cells = new PdfPCell(doEncodeKaiu("學號", 10));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
			    
			    cells = new PdfPCell(doEncodeKaiu("姓名", 10));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
				
			    cells = new PdfPCell(doEncodeKaiu("課程數", 10));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
			    
			    cells = new PdfPCell(doEncodeKaiu("時數", 10));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
			    
			    cells = new PdfPCell(doEncodeKaiu("學分數", 10));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
			    
			    cells = new PdfPCell(doEncodeKaiu("簽名", 10));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
				
				
				
				//學生陣
				for(int j=0; j<students.size(); j++){
					
					myCredit=0.0f;
					myThour=0;
					myCss=0;

					//表身
					cells = new PdfPCell(doEncode(((Map)students.get(j)).get("student_no").toString(), 9));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
				    
				    cells = new PdfPCell(doEncode(((Map)students.get(j)).get("student_name").toString(), 9));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
					
				    myCs=manager.ezGetBy("SELECT * FROM Dtime d, Seld s " +
				    		"WHERE d.Oid=s.Dtime_oid AND d.Sterm='"+term+"' " +
				    		"AND s.student_no='"+((Map)students.get(j)).get("student_no")+"'");
				    for(int k=0; k<myCs.size(); k++){
				    	try{
				    		myCredit=myCredit+Float.parseFloat(((Map)myCs.get(k)).get("credit").toString());
					    	myThour=myThour+Integer.parseInt(((Map)myCs.get(k)).get("thour").toString());
				    	}catch(Exception e){
				    		
				    	}
				    	
				    }
				    
				    /*
				    myCs=manager.ezGetBy("SELECT d.thour, d.credit FROM Dtime d, Seld s " +
				    		"WHERE d.Oid=s.Dtime_oid AND d.Sterm='2' " +
				    		"AND s.student_no='"+((Map)students.get(j)).get("student_no")+"'").iterator();				    
				    while(myCs.hasNext()){
				    	myCredit=myCredit+Float.parseFloat(((Map)myCs.next()).get("credit").toString());
				    	myCss=myCss+1;
					    myThour=myThour+Integer.parseInt(((Map)myCs.next()).get("thour").toString());
					}
				    */
				    
				    cells = new PdfPCell(doEncode(myCs.size()+"", 9));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
				    
				    cells = new PdfPCell(doEncode(myThour+"", 9));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
				    
				    cells = new PdfPCell(doEncode(myCredit+"", 9));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
				    
				    cells = new PdfPCell(doEncode("", 9));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
					
					
					
					
					
					
					
				}
				document.add(tables);
				document.newPage();
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			document.close();
			response.setContentType("text/html; charset=UTF-8");
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment;filename=TechTimetable.pdf");
			response.setContentLength(ba.size());
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 轉碼 特殊字級 (細明體)
	 */
	private Phrase doEncode(String nowBy, int fontSize) {

		BaseFont bf;
		Font font = null;
		Phrase p = null;
		try {

			bf = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
			font = new Font(bf, fontSize ,0);
			p=new Phrase(nowBy, font);

		}catch (Exception e) {

			e.printStackTrace();
		}
		return p;
	}

	/**
	 * 轉碼 特殊字級 (標楷體)
	 */
	private Phrase doEncodeKaiu(String nowBy, int fontSize) {

		BaseFont bf;
		Font font = null;
		Phrase p = null;
		try {
			bf = BaseFont.createFont("/pl.ttf", "Identity-H", BaseFont.EMBEDDED);
			font = new Font(bf, fontSize ,0);
			p=new Phrase(nowBy, font);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
}