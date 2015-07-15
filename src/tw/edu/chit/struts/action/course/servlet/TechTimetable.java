package tw.edu.chit.struts.action.course.servlet;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

/**
 * 教師授課表
 * @author JOHN
 *
 */
public class TechTimetable extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		List dtimeList=(List)session.getAttribute("dtimeList");
		
		String term=session.getAttribute("xterm").toString();//現在學期
		String year=manager.getNowBy("School_year");//現在學年
		//第二學期6月~8月學年+1
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		if(c.get(Calendar.MONTH)>=6 &&c.get(Calendar.MONTH)<=8 && term.equals("2")){
			int tmp=Integer.parseInt(year)+1;
			year=tmp+"";
		}
		
		//TODO 所屬單位需合併資料表
		StringBuilder sb=new StringBuilder("SELECT e.unit, e.cname, e.idno, c.name FROM empl e LEFT OUTER JOIN CodeEmpl c ON e.unit=c.idno AND c.category='UnitTeach' " +
				"WHERE e.idno IN(");
		for(int i=0; i<dtimeList.size(); i++){
			sb.append("'"+((Map)dtimeList.get(i)).get("techid")+"', " );
		}
		sb.delete(sb.length()-2, sb.length()-1);
		sb.append(") ORDER BY c.Oid");
		
		List teachers=manager.ezGetBy(sb.toString());
		Date date=new Date();
		DateFormat dateFormat=new SimpleDateFormat("yyyy年 MM月 dd日 HH:mm");
		
		Document document = new Document(PageSize.A4, 20, 20, 10, 10);
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			//Image image=Image.getInstance("http://cap.chit.edu.tw/CIS/pages/images/2002chitWatermark.gif");
			//Watermark wamark=new Watermark(image, ((PageSize.A4).width()-image.plainHeight())/2, ((PageSize.A4).height()-image.plainHeight())/2);
			//document.add(wamark);
			Phrase now=doEncode("課程管理系統 "+dateFormat.format(date), 10);
			HeaderFooter header=new HeaderFooter(now,false);
			//document.setHeader(header);
			document.setFooter(header);
			document.open();
			
			float[] widths = {0.25f, 1.3f, 1.3f, 1.3f, 1.3f, 1.3f, 1.3f, 1.3f, 0.25f};
			
			for(int i=0; i<teachers.size(); i++){
				//表頭部份
				Table table=new Table(6);
				table.setWidth(100);
				//table.setDefaultCellBorderColor(new Color(255, 255, 255));
				Cell cell=new Cell();
				
				try{
					cell.add(doEncodeKaiu(((Map)teachers.get(i)).get("name").toString()+" "+((Map)teachers.get(i)).get("cname").toString()+"老師 "+year, 20));
				}catch(Exception e){
					cell.add(doEncodeKaiu(((Map)teachers.get(i)).get("cname").toString()+"老師 "+year, 20));
				}
				
				cell.add(doEncodeKaiu("學年第", 20));
				cell.add(doEncodeKaiu(term, 20));
				cell.add(doEncodeKaiu("學期 授課時間表", 20));
				//cell.addElement(logo);
				cell.setColspan(6);
				cell.setHorizontalAlignment(cell.ALIGN_CENTER);
				cell.setBorderColor(new Color(255, 255, 255));
				table.addCell(cell);
				table.setBorderWidth(0);
				table.setPadding(5);
				document.add(table);
				
				document.add(new Paragraph(doEncode("\n", 8)));
				
				//表身
				PdfPTable tables = new PdfPTable(widths);
				tables.setWidthPercentage(100);
				
				PdfPCell cells = new PdfPCell(doEncodeKaiu("", 13));
			    tables.addCell(cells);

			    cells = new PdfPCell(doEncodeKaiu("星期一", 12));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncodeKaiu("星期二", 12));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncodeKaiu("星期三", 12));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncodeKaiu("星期四", 12));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncodeKaiu("星期五", 12));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncodeKaiu("星期六", 12));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncodeKaiu("星期日", 12));
			    cells.setHorizontalAlignment(cells.ALIGN_CENTER);
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncodeKaiu("", 12));
			    tables.addCell(cells);
				
			    Map map;
			    //內容
				for(int j=1; j<=14; j++){
					//cells = new PdfPCell(doEncodeKaiu(ChWeekN(j), 10));
					cells = new PdfPCell(doEncodeKaiu(ChWeekN(j), 10));
				    tables.addCell(cells);
				    
				    map=new HashMap();
				    map=getMyCsInfo(((Map)teachers.get(i)).get("idno").toString(), "1", j, j, term);
				    if(map!=null){
				    	cells = writeCells(map);
				    }else{
				    	cells = new PdfPCell(doEncodeKaiu("", 12));
				    }
				    tables.addCell(cells);
				    
				    map=new HashMap();
				    map=getMyCsInfo(((Map)teachers.get(i)).get("idno").toString(), "2", j, j, term);
				    if(map!=null){				    	
				    	cells = writeCells(map);		    	
				    }else{
				    	cells = new PdfPCell(doEncodeKaiu("", 12));
				    }
				    tables.addCell(cells);
				    
				    map=new HashMap();
				    map=getMyCsInfo(((Map)teachers.get(i)).get("idno").toString(), "3", j, j, term);
				    if(map!=null){
				    	cells = writeCells(map);
				    }else{
				    	cells = new PdfPCell(doEncodeKaiu("", 12));
				    }
				    tables.addCell(cells);
				    
				    map=new HashMap();
				    map=getMyCsInfo(((Map)teachers.get(i)).get("idno").toString(), "4", j, j, term);
				    if(map!=null){
				    	cells = writeCells(map);
				    }else{
				    	cells = new PdfPCell(doEncodeKaiu("", 12));
				    }
				    tables.addCell(cells);				    
				    
				    map=new HashMap();
				    map=getMyCsInfo(((Map)teachers.get(i)).get("idno").toString(), "5", j, j, term);
				    if(map!=null){
				    	cells = writeCells(map);
				    }else{
				    	cells = new PdfPCell(doEncodeKaiu("", 12));
				    }
				    tables.addCell(cells);				    
				    
				    map=new HashMap();
				    map=getMyCsInfo(((Map)teachers.get(i)).get("idno").toString(), "6", j, j, term);
				    if(map!=null){
				    	cells = writeCells(map);
				    }else{
				    	cells = new PdfPCell(doEncodeKaiu("", 12));
				    }
				    tables.addCell(cells);				    
				    
				    map=new HashMap();
				    map=getMyCsInfo(((Map)teachers.get(i)).get("idno").toString(), "7", j, j, term);
				    if(map!=null){
				    	cells = writeCells(map);
				    }else{
				    	cells = new PdfPCell(doEncodeKaiu("", 12));
				    }
				    tables.addCell(cells);
				    
				    cells = new PdfPCell(doEncodeKaiu(ChWeekH(j), 10));
				    tables.addCell(cells);
				    
				    if(j==4 || j==10){
				    	cells=new PdfPCell(doEncodeKaiu("", 12));
				    	cells.setColspan(9);
				    	tables.addCell(cells);
				    }
				}
				document.add(tables);
				
				
				/*
				sb=new StringBuilder(); //該班所屬學制的上課時間					
				List dList=manager.ezGetBy("SELECT * FROM Dtimestamp WHERE sidno='64' GROUP BY DSvalue ORDER BY DSreal");					
				sb.append("日間部上課時間:");
				Paragraph parag=new Paragraph(doEncodeKaiu(sb.toString()+"", 8));
				
				sb=new StringBuilder();
				
				for(int y=0; y<dList.size(); y++){
					sb.append( ((Map)dList.get(y)).get("DsValue")+""+((Map)dList.get(y)).get("DsBegin").toString().substring(0,5)+"至"+((Map)dList.get(y)).get("DsEnd").toString().substring(0,5)+", " );
				}
				sb.delete(sb.length()-2, sb.length()-1);
				parag.add(doEncode(sb.toString(), 6));
				document.add(parag);					
				
				
				
				parag=new Paragraph(doEncodeKaiu(" ", 4));
				document.add(parag);
				
				
				sb=new StringBuilder(); //該班所屬學制的上課時間					
				dList=manager.ezGetBy("SELECT * FROM Dtimestamp WHERE sidno='54' GROUP BY DSvalue ORDER BY DSreal");					
				sb.append("進修(夜間)部上課時間:");
				parag=new Paragraph(doEncodeKaiu(sb.toString()+"", 8));
				
				sb=new StringBuilder();
				
				for(int y=0; y<dList.size(); y++){
					sb.append( ((Map)dList.get(y)).get("DsValue")+""+((Map)dList.get(y)).get("DsBegin").toString().substring(0,5)+"至"+((Map)dList.get(y)).get("DsEnd")+", " );
				}
				sb.delete(sb.length()-2, sb.length()-1);
				parag.add(doEncode(sb.toString(), 6));
				document.add(parag);
				
				
				
				parag=new Paragraph(doEncodeKaiu(" ", 4));
				document.add(parag);				
				
				sb=new StringBuilder(); //該班所屬學制的上課時間					
				dList=manager.ezGetBy("SELECT * FROM Dtimestamp WHERE sidno='32' GROUP BY DSvalue ORDER BY DSreal");					
				sb.append("進修學院/專校(假日)上課時間:");
				parag=new Paragraph(doEncodeKaiu(sb.toString()+"", 8));
				
				sb=new StringBuilder();
				
				for(int y=0; y<dList.size(); y++){
					sb.append( ((Map)dList.get(y)).get("DsValue")+""+((Map)dList.get(y)).get("DsBegin").toString().substring(0,5)+"至"+((Map)dList.get(y)).get("DsEnd").toString().substring(0,5)+", " );
				}
				sb.delete(sb.length()-2, sb.length()-1);
				parag.add(doEncode(sb.toString(), 6));
				document.add(parag);
				*/
				
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
			
		} catch (DocumentException e) {
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
			bf = BaseFont.createFont("/kaiu.ttf", "Identity-H", BaseFont.NOT_EMBEDDED);
			font = new Font(bf, fontSize ,0);
			p=new Phrase(nowBy, font);
			bf=null;
			font=null;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	private String ChWeekN(int i){
		switch (i){
		case 1: return "第一節\n\n";
		case 2: return "第二節\n\n";
		case 3: return "第三節\n\n";
		case 4: return "第四節\n\n";
		case 5: return "第五節\n\n";
		case 6: return "第六節\n\n";
		case 7: return "第七節\n\n";
		case 8: return "第八節\n\n";
		case 9: return "第九節\n\n";
		case 10: return "第十節\n\n";
		
		case 11: return "夜一節\n\n";
		case 12: return "夜二節\n\n";
		case 13: return "夜三節\n\n";
		case 14: return "夜四節\n\n";
		
		case 15: return "第十五節";
		
		default: return "某一節";
		}
	}
	
	private String ChWeekH(int i){
		
		
		switch (i){
		case 1: return "第一節";
		case 2: return "第二節";
		case 3: return "第三節";
		case 4: return "第四節";
		case 5: return "第五節";
		case 6: return "第六節";
		case 7: return "第七節";
		case 8: return "第八節";
		case 9: return "第九節";
		case 10: return "第十節";
		case 11: return "第十一節";
		case 12: return "第十二節";
		case 13: return "第十三節";
		case 14: return "第十四節";
		case 15: return "第十五節";
		default: return "某一節";
		}
	}
	
	/**
	 * 拿課程資訊
	 * @param idno 教師
	 * @param week 周幾
	 * @param j 開始節次
	 * @param j2 結束節次
	 * @return 全部
	 */
	private Map getMyCsInfo(String idno, String week, int j, int j2, String term){
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		//TODO 一科目多教師衝堂囧?
		Map map=manager.ezGetMap("SELECT c51.name as bname, d.Oid, c5.name as c5Name, cl.ClassNo, cl.ClassName, n.room_id, c.chi_name, c.cscode, n.name2 as name FROM " +
				"Dtime d, Csno c, Class cl, code5 c5, (Dtime_class dc LEFT OUTER JOIN Nabbr n ON n.room_id=dc.place) " +
				"LEFT OUTER JOIN code5 c51 ON c51.idno=n.building AND c51.category='building' " +
				"WHERE d.cscode=c.cscode AND c5.category='Campus' AND c5.idno=cl.CampusNo AND " +
				"d.Sterm='"+term+"' AND cl.ClassNo=d.depart_class AND "+
				"dc.Dtime_oid=d.Oid AND " +
				"dc.week='"+week+"' AND (" +
				"dc.begin<="+j+" && " +
				"dc.end>="+j2+") AND " +
				"d.techid='"+idno+"' limit 1");
		
		if(map!=null){
			
			if(map.get("name")==null){
				map.put("name", "");
			}
			if(map.get("c5Name")==null){
				map.put("c5Name", "");
			}
			if(map.get("room_id")==null){
				map.put("room_id", "");
			}else{
				map.put("room_id", map.get("room_id").toString().substring(1));
			}
			map.put("stuSel", "上課人數 "+manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE Dtime_oid="+map.get("Oid")));
		}
		
		return map;
	}
	
	/**
	 * 寫格子幹
	 * @param map
	 * @return
	 */
	private PdfPCell writeCells(Map map){
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");	
		
		PdfPCell cells=new PdfPCell();
		String line1=map.get("chi_name").toString()+map.get("cscode").toString();   
    	
		if(line1.length()>10){
			line1=line1.substring(0, 10)+"..";
		}
		cells.addElement(doEncode(line1, fontSize(line1)));
    	
    	cells.addElement(doEncode(map.get("ClassName").toString()+map.get("ClassNo").toString(), 7));
    	
    	if(map.get("bname")!=null){
    		cells.addElement(doEncode(map.get("c5Name").toString()+
        			map.get("bname").toString()+
        			map.get("room_id").toString(), 8));
    	}else{
    		cells.addElement(doEncode(map.get("c5Name").toString()+"校區", 8));
    	}
		
		return cells;
	}
	
	private int fontSize(String chiName){
		if(chiName.length()>=10){
			return 6;
		}
		return 9;
	}
}
