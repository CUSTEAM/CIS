package tw.edu.chit.struts.action.course.ajax;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
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

public class AdviceOfSummer extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		SummerManager summerManager=(SummerManager) ctx.getBean("summerManager");
		HttpSession session = request.getSession(false);
		response.setHeader("Content-Disposition", "attachment;filename=callteachers.pdf");
		Document document = new Document(PageSize.A4, 72,72,35,35);
		ByteArrayOutputStream ba = new ByteArrayOutputStream();

		List SdtimeList=(List)session.getAttribute("SdtimeList");
		String year=manager.getNowBy("School_year");
		String term=manager.getNowBy("School_term");		
		
		StringBuffer sql=new StringBuffer("SELECT idno, cname FROM empl WHERE idno IN(");
		for(int i=0; i<SdtimeList.size(); i++){
			sql.append("'"+((Map)SdtimeList.get(i)).get("techid")+"',");
		}
		sql.delete(sql.length()-1, sql.length()-0);
		sql.append(")");
		
		List teachers=manager.ezGetBy(sql.toString());
		
		try {
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			
			Date date=new Date();
			DateFormat dateFormat=new SimpleDateFormat("yyyy年 MM月 dd日 HH:mm");
			
			//Image image=Image.getInstance("http://192.192.231.32:8080/CIS/pages/images/2002chitWatermark.gif");
			//Watermark wamark=new Watermark(image, ((PageSize.A4).width()-image.plainHeight())/2, ((PageSize.A4).height()-image.plainHeight())/2);
			//document.add(wamark);
			Phrase now=doEncode(dateFormat.format(date), 10);
			HeaderFooter header=new HeaderFooter(now,false);
			document.setHeader(header);
			document.setFooter(header);
			document.open();			
			
			for(int i=0; i<teachers.size(); i++){
				
				
				
				
				
				Table table=new Table(6);
				Cell cell=new Cell();
				
				//table.setDefaultCellBorderColor(new Color(255, 255, 255));
				table.setBorderColor(new Color(255, 255, 255));
				cell.add(doEncode(year, 12));
				cell.add(doEncode("學年 第", 12));
				cell.add(doEncode(term, 12));
				cell.add(doEncode("學期 暑修開課通知\n\n", 12));
				//cell.addElement(logo);
				cell.setColspan(6);
				cell.setHorizontalAlignment(cell.ALIGN_CENTER);
				table.addCell(cell);

				//((Map)students.get(i)).get("ClassName2");

				cell=new Cell();
				cell.add(doEncode(((Map)teachers.get(i)).get("cname").toString(), 12));

				cell.add(doEncode("", 12));
				cell.add(doEncode("", 12));
				cell.add(doEncode(" 老師 您好", 12));
				cell.add(doEncode("", 12));

				cell.setColspan(6);
				cell.setHorizontalAlignment(cell.ALIGN_LEFT);
				table.addCell(cell);

				cell=new Cell();
				cell.add(doEncode("以下為您在暑修期間的開課資訊。\n\n", 12));

				cell.setColspan(6);
				cell.setHorizontalAlignment(cell.ALIGN_LEFT);
				table.addCell(cell);

				document.add(table);
				
				sql=new StringBuffer("SELECT d.seqno, cl.name, c.chi_name, d.thour, d.clascode, day1, day2, day3, " +
						"day4, day5, day6, day7, d.depart_class FROM Sdtime d, Sabbr cl, Csno c " +
						"WHERE cl.no=d.depart_class AND c.cscode=d.cscode AND d.techid='"+((Map)teachers.get(i)).get("idno")+"'");
				
				if(!session.getAttribute("seqno").equals(""))
				sql.append("AND d.seqno='"+session.getAttribute("seqno")+"'");
				
				List classInfo=manager.ezGetBy(sql.toString());				

				float[] widths = {0.1f, 0.2f, 0.3f, 0.1f, 0.2f, 0.1f};
				PdfPTable tables = new PdfPTable(widths);
				tables.setWidthPercentage(100);

				PdfPCell cells = new PdfPCell(doEncode("梯次(日期)", 8));
			    tables.addCell(cells);

			    cells = new PdfPCell(doEncode("班級名稱", 12));
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncode("課程名稱", 12));
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncode("時數(天數)", 8));
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncode("上課時間", 12));
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncode("地點", 12));
			    tables.addCell(cells);
				
			    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			    
				for(int j=0; j<classInfo.size(); j++){
					String wdate="";
					try{
						wdate=((Map)manager.ezGetBy("SELECT wdate FROM Sweek WHERE seqno='"+
								((Map)classInfo.get(j)).get("seqno")+"' AND daynite='"+((Map)classInfo.get(j)).get("depart_class").toString().substring(0, 2)+"'").get(0)).get("wdate").toString();
					}catch(Exception e){
						wdate="1978-7-8";
					}
					
					
					Calendar cal=Calendar.getInstance();
					try {
						cal.setTimeInMillis(df.parse(wdate).getTime());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					 String firstday="";
					    String lastday="";
					    String firstclass="";
					    String lastclass="";
					    String everyday="";
					    
					    String classes[]=new String[7];
					    for(int x=1; x<=classes.length; x++){
					    	
					    	if( ((Map)classInfo.get(j)).get("day"+x)!=null&&!((Map)classInfo.get(j)).get("day"+x).equals("")&& firstday.equals("")){
					    		//classes[x]=((Map)classInfo.get(j)).get("day"+x).toString();
					    		firstday=x+"";
					    	}
					    	
					    	if(((Map)classInfo.get(j)).get("day"+x)!=null&&!((Map)classInfo.get(j)).get("day"+x).equals("")){
					    		//lastday=((Map)classInfo.get(j)).get("day"+x).toString();
					    		lastday=x+"";
					    		everyday=((Map)classInfo.get(j)).get("day"+x).toString();
					    	}
					    	
					    	for(int y=0; y<everyday.length(); y++){
					    		if(firstclass.equals("")){
					    			firstclass=everyday.substring(y, y+1);
					    		}
					    		lastclass=everyday.substring(y, y+1);
					    	}
					    	
					    }
					String thour=((Map)classInfo.get(j)).get("thour").toString();
					cells = new PdfPCell(doEncode(((Map)classInfo.get(j)).get("seqno").toString()+"("+(cal.get(Calendar.MONTH)+1)+"/"+cal.get((Calendar.DAY_OF_MONTH))+")", 12));
				    tables.addCell(cells);
				    cells = new PdfPCell(doEncode(((Map)classInfo.get(j)).get("name").toString(), 12));
				    tables.addCell(cells);
				    cells = new PdfPCell(doEncode(((Map)classInfo.get(j)).get("chi_name").toString(), 12));
				    tables.addCell(cells);
				    try{
				    	cells = new PdfPCell(doEncode(thour+"時("+Integer.parseInt(thour)/everyday.length()+"天)", 8));
				    }catch(Exception e){
				    	cells = new PdfPCell(doEncode("設定不正確", 8));
				    }
				    
				    tables.addCell(cells);
				    cells = new PdfPCell(doEncode("週"+firstday+"~週"+lastday+", "+firstclass+"~"+lastclass+"節", 10));
				    tables.addCell(cells);
				    
				    if(((Map)classInfo.get(j)).get("clascode")!=null){
				    	cells = new PdfPCell(doEncode(((Map)classInfo.get(j)).get("clascode").toString(), 12));
				    }else{
				    	cells = new PdfPCell(doEncode("", 12));
				    }
				    tables.addCell(cells);						
				}
				
				for(int k=11; k>classInfo.size(); k--){
			    	cells = new PdfPCell(doEncode("\t", 12));
				    tables.addCell(cells);
				    cells = new PdfPCell(doEncode("\t", 12));
				    tables.addCell(cells);
				    cells = new PdfPCell(doEncode("\t", 12));
				    tables.addCell(cells);
				    cells = new PdfPCell(doEncode("\t", 12));
				    tables.addCell(cells);
				    cells = new PdfPCell(doEncode("\t", 12));
				    tables.addCell(cells);
				    cells = new PdfPCell(doEncode("\t", 12));
				    tables.addCell(cells);
				}

			document.add(tables);				
				if(i%2==1){
					document.newPage();
				}else{
					document.add(new Paragraph(doEncode("\n", 12)));
					document.add(new Paragraph(doEncode("\n", 12)));
					document.add(new Paragraph(doEncode("\n", 12)));
				}			
			}			
			
			document.close();
			response.setContentType("application/pdf");
			response.setContentLength(ba.size());
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			ba.close();
			out.flush();
			out.close();
			
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 轉碼 特殊字級 (自定義)
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

}
