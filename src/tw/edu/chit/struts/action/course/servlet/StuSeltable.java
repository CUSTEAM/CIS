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
 * 學生選課清單
 * @author JOHN
 *
 */
public class StuSeltable extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);		
		
		String term=session.getAttribute("xterm").toString();//現在學期
		String year=manager.getNowBy("School_year");//現在學年
		
		String classLess=(String)session.getAttribute("classLess");//頁面取得的
		
		List dtimeList=(List)session.getAttribute("dtimeList");
		
		List classes=new ArrayList();
		
		//如果沒有結果就認定是延修班
		if(dtimeList.size()<1){
			
			classes=manager.ezGetBy("SELECT depart_class FROM stmd WHERE depart_class LIKE '"+classLess+"%' GROUP BY depart_class");
		//有結果則是正常班
		}else{
			
			
			//取得所有開課班級
			StringBuilder sb=new StringBuilder("SELECT d.depart_class FROM Dtime d, Class c WHERE c.ClassNo=d.depart_class AND d.depart_class NOT LIKE '%0%'" +
					"AND d.depart_class IN(");
			
			for(int i=0; i<dtimeList.size(); i++){
				sb.append("'"+((Map)dtimeList.get(i)).get("departClass")+"', " );
			}
			
			sb.delete(sb.length()-2, sb.length()-1);
			sb.append(") GROUP BY d.depart_class ORDER BY d.depart_class");
			classes=manager.ezGetBy(sb.toString());
			
		}
		
		float myCredit;
		int myThour;
		
		Document document = new Document(PageSize.A4, 30,30,30,30);
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		
		Date date=new Date();
		DateFormat dateFormat=new SimpleDateFormat("yyyy年 MM月 dd日 HH:mm");
		
		
		//第二學期6月後學年+1
		/*2011/7/15反應不爽所以取消
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		if(c.get(Calendar.MONTH)>=6 && c.get(Calendar.MONTH)<=7 && term.equals("2")){
			int tmp=Integer.parseInt(year)+1;
			year=tmp+"";
		}
		*/
		float[] widths = {1.5f, 2.0f, 1.0f, 0.5f, 0.5f, 2.0f, 1.5f };
		try{
			
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			//Image image=Image.getInstance("http://cap.chit.edu.tw/CIS/pages/images/2002chitWatermark.gif");
			//Watermark wamark=new Watermark(image, ((PageSize.A4).width()-image.plainHeight())/2, ((PageSize.A4).height()-image.plainHeight())/2);
			//document.add(wamark);
			Phrase now=doEncode("課程管理系統 "+dateFormat.format(date), 10);
			HeaderFooter header=new HeaderFooter(now,false);
			document.setHeader(header);
			document.setFooter(header);
			document.open();
			
			//班級陣
			for(int i=0; i<classes.size(); i++){
				
				List students=manager.ezGetBy("SELECT st.student_no, st.student_name, cl.ClassName FROM stmd st, Class cl " +
						"WHERE st.depart_class=cl.ClassNo AND st.depart_class LIKE '"+((Map)classes.get(i)).get("depart_class")+"'");
				
				//學生陣
				for(int j=0; j<students.size(); j++){
					
					myCredit=0.0f;
					myThour=0;
					
					//表頭
					Table table=new Table(6);
					table.setWidth(100);
					//table.setDefaultCellBorderColor(new Color(255, 255, 255));
					Cell cell=new Cell();
					
					cell.add(doEncodeKaiu(year+"學年第", 12));
					cell.add(doEncodeKaiu(term, 12));
					cell.add(doEncodeKaiu("學期 選課清單", 12));
					//cell.addElement(logo);
					cell.setColspan(6);
					cell.setHorizontalAlignment(cell.ALIGN_CENTER);
					cell.setBorderColor(new Color(255, 255, 255));
					table.addCell(cell);
					table.setBorderWidth(0);
					table.setPadding(5);
					document.add(table);
					
					document.add(new Paragraph(doEncode(
							((Map)students.get(j)).get("ClassName").toString()+" "+
							((Map)students.get(j)).get("student_no").toString()+" "+
							((Map)students.get(j)).get("student_name").toString(), 10)));		
					document.add(new Paragraph(doEncode("\n", 4)));
					//表身
					PdfPTable tables = new PdfPTable(widths);
					tables.setWidthPercentage(100);

				    PdfPCell cells = new PdfPCell(doEncodeKaiu("開課班級", 10));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
				    
				    cells = new PdfPCell(doEncodeKaiu("科目名稱/代碼", 10));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
					
				    cells = new PdfPCell(doEncodeKaiu("選別", 10));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
				    
				    cells = new PdfPCell(doEncodeKaiu("學分", 10));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
				    
				    cells = new PdfPCell(doEncodeKaiu("時數", 10));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
				    
				    cells = new PdfPCell(doEncodeKaiu("上課時間", 10));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
					
				    cells = new PdfPCell(doEncodeKaiu("上課地點", 10));
				    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
				    tables.addCell(cells);
				    
				    
				    List myCs=manager.getMyCs(((Map)students.get(j)).get("student_no").toString(), String.valueOf(session.getAttribute("xterm")));
				    for(int k=0; k<myCs.size(); k++ ){
				    	cells = new PdfPCell(doEncode(((Map)myCs.get(k)).get("ClassName").toString() , 10));
					    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
					    tables.addCell(cells);
					    
					    
					    if(((Map)myCs.get(k)).get("chi_name").toString().length()>9){
					    	 cells = new PdfPCell(doEncode(((Map)myCs.get(k)).get("chi_name").toString(), 7));
					    }else{
					    	 cells = new PdfPCell(doEncode(((Map)myCs.get(k)).get("chi_name").toString(), 10));
					    }
					    
					    cells.setHorizontalAlignment(cells.ALIGN_LEFT);
					    tables.addCell(cells);
						
					    cells = new PdfPCell(doEncode(((Map)myCs.get(k)).get("opt").toString(), 10));
					    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
					    tables.addCell(cells);
					    
					    cells = new PdfPCell(doEncode(((Map)myCs.get(k)).get("credit").toString(), 10));					    
					    myCredit=myCredit+Float.parseFloat(((Map)myCs.get(k)).get("credit").toString());
					    
					    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
					    tables.addCell(cells);
					    
					    cells = new PdfPCell(doEncode(((Map)myCs.get(k)).get("thour").toString(), 10));
					    myThour=myThour+Integer.parseInt(((Map)myCs.get(k)).get("thour").toString());
					    
					    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
					    tables.addCell(cells);
					    
					    StringBuilder myTime=new StringBuilder();
					    List myPlace=(List)((Map)myCs.get(k)).get("dtimeClass");
					    for(int x=0; x<myPlace.size(); x++){
					    	myTime.append( ChWeek(Integer.parseInt(((Map)myPlace.get(x)).get("week").toString())));
					    	myTime.append(((Map)myPlace.get(x)).get("begin")+"~");
					    	myTime.append(((Map)myPlace.get(x)).get("end"));
					    }
						
					    cells = new PdfPCell(doEncode(myTime.toString(), 10));
					    cells.setHorizontalAlignment(cells.ALIGN_LEFT);
					    tables.addCell(cells);
						
					    
					    myTime=new StringBuilder();
					    if(myPlace.size()>0){
					    	if(((Map)myPlace.get(0)).get("name")!=null){
					    		
					    		myTime.append(((Map)myPlace.get(0)).get("name").toString());
					    	}
					    	if(((Map)myPlace.get(0)).get("buildName")!=null){
					    		myTime.append(((Map)myPlace.get(0)).get("buildName") );
					    	}
					    	try{
					    		myTime.append(((Map)myPlace.get(0)).get("place").toString());
					    	}catch(Exception e){
					    		myTime.append("未指定");
					    	}
					    	
					    }
					    
					    
					    if(myTime.length()>7){
					    	cells = new PdfPCell(doEncode(myTime.toString(), 6));
					    }else{
					    	cells = new PdfPCell(doEncode(myTime.toString(), 10));
					    }
					    
					    
					    
					    
					    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
					    tables.addCell(cells);
				    	
				    }
				    
				    for(int k=15; k>myCs.size(); k--){
				    	cells = new PdfPCell(doEncode("\t", 10));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode("\t", 10));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode("\t", 10));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode("\t", 10));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode("\t", 10));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode("\t", 10));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode("\t", 10));
					    tables.addCell(cells);
					}
					
					document.add(tables);					
					document.add(new Paragraph(doEncodeKaiu("每週計有 "+myCs.size()+"門課, "+myThour+"小時, 本學期已選"+myCredit+"學分", 10)));
					if(j%2==1){
						document.newPage();
					}else{
						
						document.add(new Paragraph(doEncode("\n", 20)));
						document.add(new Paragraph(doEncode("\n", 12)));
						document.add(new Paragraph(doEncode("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - "+
						"- - - - - - - - - - -"		, 12)));
						
					}
						
				}
				
			}
			
			document.close();
			response.setContentType("text/html; charset=UTF-8");
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment;filename=TechTimetable.pdf");
			response.setContentLength(ba.size());
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			ba.close();
			out.flush();
			out.close();
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
	
	
	
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	private String ChWeek(int i){
		
		switch (i){
		case 1: return "一";
		case 2: return "二";
		case 3: return "三";
		case 4: return "四";
		case 5: return "五";
		case 6: return "六";
		case 7: return "日";
		default: return "某一節";
		}
	}
}
