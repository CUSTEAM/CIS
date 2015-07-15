package tw.edu.chit.struts.action.course.ajax;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;

/**
 * 試卷封袋
 * @author JOHN
 *
 */
public class ExamPacketFace extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		//SummerManager summerManager=(SummerManager) ctx.getBean("summerManager");
		HttpSession session = request.getSession(false);
		String year=manager.getNowBy("School_year");
		String term=manager.getNowBy("School_term");
		List dtimeList=(List)session.getAttribute("dtimeList");
		
		Date date=new Date();
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		int month=c.get(Calendar.MONTH)+1;
		String title="(期中 / 期末)";
		switch(month){
			case 10:
				title="期中";
			break;
			case 11:
				title="期中";
			break;
			case 3:
				title="期中";
			break;
			case 4:
				title="期中";
			break;
			case 12:
				title="期末";
			break;
			case 1:
				title="期末";
			break;
			case 5:
				title="期末";
			break;
			case 6:
				title="期末";
			break;
			
		
		}
		
		Document document = new Document(PageSize.A4, 72,72, 0, 0);
		
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try {			
			
			/*
			PdfReader reader = new PdfReader("HelloWorldRead.pdf");
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					"HelloWorldStamper.pdf"));
			*/
			
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			//Image image=Image.getInstance("http://cap.chit.edu.tw/CIS/pages/images/2002chitWatermark.gif");
			//image.setAbsolutePosition(200, 400);
			//Watermark wamark=new Watermark(image, ((PageSize.A4).width()-image.plainHeight())/2, ((PageSize.A4).height()-image.plainHeight())/2);
			
			
			//document.add(image);			
			document.open();
			document.add(doEncodeKaiu("\n", 32));
			int tmp=1;//?????????????????????????????????????????????
			for(int i=0; i<dtimeList.size(); i++){
				String cscode=((Map)dtimeList.get(i)).get("cscode").toString();
				if(!cscode.equals("50000")&& !cscode.equals("T0001")&& !cscode.equals("T0002") ){
					tmp=tmp+1;
					
					List students=manager.ezGetBy("SELECT st.student_name, st.student_no FROM stmd st, Seld s WHERE st.student_no=s.student_no AND " +
							"s.dtime_oid ='"+((Map)dtimeList.get(i)).get("oid")+"'");
					
					Table table=new Table(6);
					Cell cell=new Cell();
					
					//table.setDefaultCellBorderColor(new Color(255, 255, 255));
					table.setBorderColor(new Color(255, 255, 255));
					cell.add(doEncodeKaiu(year, 12));
					cell.add(doEncodeKaiu("學年 第", 12));
					cell.add(doEncodeKaiu(term, 12));
					cell.add(doEncodeKaiu("學期 "+title+"考 試卷封袋\n\n", 12));
					cell.setColspan(6);
					cell.setHorizontalAlignment(cell.ALIGN_CENTER);
					cell.setBorderColor(new Color(255, 255, 255));
					table.addCell(cell);
					
					cell=new Cell();
					cell.setColspan(6);
					
					cell=new Cell();
					cell.setColspan(6);
					cell.add(doEncodeKaiu("考試班級: ", 9));
					cell.add(doEncode(((Map)dtimeList.get(i)).get("departClass2").toString(), 9));
					cell.setBorderColor(new Color(255, 255, 255));
					table.addCell(cell);
					
					String teachName="";
					try{
						teachName=((Map)manager.ezGetBy("SELECT empl.cname FROM empl LEFT OUTER JOIN Dtime ON empl.idno=Dtime.techid " +
								"WHERE Dtime.Oid='"+((Map)dtimeList.get(i)).get("oid")+"'").get(0)).get("cname").toString();						
					}catch(Exception e){
						teachName="未指定教師";
					}
					
					cell.setColspan(6);
					cell=new Cell();
					cell.setColspan(6);
					cell.add(doEncodeKaiu("任課教師: ", 9));
					cell.add(doEncode(teachName, 9));
					cell.setBorderColor(new Color(255, 255, 255));
					table.addCell(cell);
					
					cell=new Cell();
					cell.setColspan(6);
					cell.add(doEncodeKaiu("考試科目: ", 9));
					cell.add(doEncode(((Map)dtimeList.get(i)).get("chiName3").toString(), 9));
					cell.add(doEncodeKaiu(" 課程編號: ", 9));
					cell.add(doEncode(((Map)dtimeList.get(i)).get("oid").toString(), 9));
					
					cell.setBorderColor(new Color(255, 255, 255));
					table.addCell(cell);
					
					cell=new Cell();
					cell.setColspan(6);
					cell.add(doEncodeKaiu("實到人數: ____ / "+students.size(), 9));
					cell.setBorderColor(new Color(255, 255, 255));
					table.addCell(cell);

					document.add(table);
					
					document.add(new Paragraph(doEncode("\n", 10)));
					
					float[] widths = {0.2f, 0.2f, 0.2f, 0.2f, 0.2f};
					PdfPTable tables = new PdfPTable(widths);
					tables.setWidthPercentage(100);					
					
					for(int j=0; j<students.size(); j++){						
						PdfPCell cells = new PdfPCell(doEncode(((Map)students.get(j)).get("student_no").toString()+((Map)students.get(j)).get("student_name").toString(), 9));
						cells.setBorder(0);
						tables.addCell(cells);
						//System.out.println(((Map)students.get(j)).get("student_name"));
					}
					
					//if(students.size()<60){
						for(int j=0; j<5-(students.size()%5); j++){
							PdfPCell cells = new PdfPCell(doEncode(" ", 9));
							cells.setBorder(0);
							tables.addCell(cells); 
						}
					//}
					
					document.add(tables);
					
					//一堆廢話
					table=new Table(6);
					cell=new Cell();
					//table.setDefaultCellBorderColor(new Color(255, 255, 255));
					
					table.setBorderColor(new Color(255, 255, 255));
					cell=new Cell();
					cell.setColspan(6);
					cell.setHorizontalAlignment(cell.ALIGN_LEFT);
					
					cell=new Cell();
					cell.setColspan(6);
					cell.add(doEncodeKaiu("加考學生: __________________________________________________________________", 9));
					//cell.add(doEncode(((Map)dtimeList.get(i)).get("departClass2").toString(), 10));
					cell.setBorderColor(new Color(255, 255, 255));
					table.addCell(cell);
					
					cell.setColspan(6);
					cell=new Cell();
					cell.setColspan(6);
					cell.add(doEncodeKaiu("缺考學生: __________________________________________________________________", 9));
					cell.setBorderColor(new Color(255, 255, 255));
					table.addCell(cell);
					
					cell=new Cell();
					cell.setColspan(6);
					cell.add(doEncodeKaiu("違規學生: __________________________________________________________________", 9));
					cell.setBorderColor(new Color(255, 255, 255));
					table.addCell(cell);
					
					cell=new Cell();
					cell.setColspan(6);
					cell.add(doEncodeKaiu("監考教師: __________________________________________________________________", 9));
					cell.setBorderColor(new Color(255, 255, 255));
					table.addCell(cell);					
					document.add(table);
					document.add(doEncodeKaiu("\n", 32));					
					//if					
					//換頁列印
					//if(tmp%2==1){						
					document.newPage();
					//}					
				}					
			}
			
			document.close();
			response.setContentType("text/html; charset=UTF-8");
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment;filename=ExamStudentList.pdf");
			response.setContentLength(ba.size());
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			ba.close();
			out.flush();
			
		} catch (Exception e) {			
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

			bf = BaseFont.createFont("/pl.ttf", "Identity-H", BaseFont.NOT_EMBEDDED);
			font = new Font(bf, fontSize ,0);
			p=new Phrase(nowBy, font);

		}catch (Exception e) {

			e.printStackTrace();
		}
		bf=null;
		return p;
	}
}
