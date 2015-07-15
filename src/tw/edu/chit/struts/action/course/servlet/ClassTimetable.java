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
import java.util.Map;

import javax.servlet.ServletContext;
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
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 班級課表
 * @author JOHN
 *
 */
public class ClassTimetable extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);		
		
		List dtimeList=(List)session.getAttribute("dtimeList");		
		String schoolTerm=session.getAttribute("xterm").toString();
		
		StringBuilder sb=new StringBuilder("SELECT c.CampusNo, c.SchoolNo, c.ClassName, c.ClassNo FROM Dtime d, Class c WHERE c.ClassNo=d.depart_class AND d.depart_class NOT LIKE '%0%'" +
				"AND d.depart_class IN(");
		for(int i=0; i<dtimeList.size(); i++){
			sb.append("'"+((Map)dtimeList.get(i)).get("departClass")+"', " );
		}
		sb.delete(sb.length()-2, sb.length()-1);
		sb.append(") GROUP BY c.Oid ORDER BY c.ClassNo, c.DeptNo, c.SchoolNo");		
		List classes=manager.ezGetBy(sb.toString());
		
		Date date=new Date();
		DateFormat dateFormat=new SimpleDateFormat("yyyy年 MM月 dd日 HH:mm");
		
		String year=manager.getNowBy("School_year");//現在學年	
		
		//第二學期4月後學年+1
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		if(c.get(Calendar.MONTH)>=3 && c.get(Calendar.MONTH)<7 && schoolTerm.equals("1")){
			int tmp=Integer.parseInt(year)+1;
			year=tmp+"";
		}
		ServletContext context = request.getSession().getServletContext();
		String schoolName=(String) context.getAttribute("SchoolName_ZH");
		Document document = new Document(PageSize.A4, 20, 20, 0, 10);
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			//Image image=Image.getInstance("http://cap.chit.edu.tw/CIS/pages/images/2002chitWatermark.gif");
			//Watermark wamark=new Watermark(image, ((PageSize.A4).width()-image.plainHeight())/2, ((PageSize.A4).height()-image.plainHeight())/2);
			//document.add(wamark);
			Phrase now=doEncode(schoolName+"課程管理系統 "+dateFormat.format(date), 10);
			HeaderFooter header=new HeaderFooter(now,false);
			//document.setHeader(header);
			document.setFooter(header);
			document.open();
			
			float[] widths = {0.25f, 1.3f, 1.3f, 1.3f, 1.3f, 1.3f, 1.3f, 1.3f, 0.25f};
			
			for(int i=0; i<classes.size(); i++){
				
				
				Table table=new Table(6);
				table.setWidth(100);				
				table.setBorderWidth(0);
				Cell cell=new Cell();
				try{
					cell.add(doEncodeKaiu(((Map)classes.get(i)).get("ClassName").toString()+" "+year, 20));
				}catch(Exception e){
					cell.add(doEncodeKaiu("囧 "+year, 20));
				}
				
				cell.add(doEncodeKaiu("學年第", 20));
				cell.add(doEncodeKaiu(schoolTerm, 20));
				cell.add(doEncodeKaiu("學期 授課時間表", 20));
				//cell.addElement(logo);
				cell.setColspan(6);
				cell.setHorizontalAlignment(cell.ALIGN_CENTER);
				cell.setBorderColor(new Color(255, 255, 255));
				table.addCell(cell);
				table.setBorderWidth(0);
				table.setPadding(5);
				document.add(table);
				
				document.add(new Paragraph(doEncode("\n", 6)));
				
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
			    List list;
			    int pages=0;
			    //內容
				for(int j=1; j<=14; j++){
					//cells = new PdfPCell(doEncodeKaiu(ChWeekN(j), 10));
					cells = new PdfPCell(doEncodeKaiu(ChWeekN(j), 10));
				    tables.addCell(cells);
				    
				    //周1
				    map=new HashMap();
				    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "1", j, j, schoolTerm);
				    list=(List)map.get("classes");
				    if((Integer)map.get("pages")>pages){
				    	pages=(Integer)map.get("pages");
				    }				    
				    if(list.size()>0){
				    	tables.addCell(writeCells(((Map)list.get(0))));
				    }else{
				    	tables.addCell(doEncode("", 12));
				    }				    
				    
				    //周2
				    map=new HashMap();
				    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "2", j, j, schoolTerm);
				    list=(List)map.get("classes");
				    
				    if((Integer)map.get("pages")>pages){
				    	pages=(Integer)map.get("pages");
				    }
				    
				    if(list.size()>0){
				    	tables.addCell(writeCells(((Map)list.get(0))));
				    }else{
				    	tables.addCell(doEncode("", 12));
				    }
				    
				    //周3
				    map=new HashMap();
				    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "3", j, j, schoolTerm);
				    list=(List)map.get("classes");
				    if((Integer)map.get("pages")>pages){
				    	pages=(Integer)map.get("pages");
				    }
				    if(list.size()>0){
				    	tables.addCell(writeCells(((Map)list.get(0))));
				    }else{
				    	tables.addCell(doEncode("", 12));
				    }
				    
				    //周4
				    map=new HashMap();
				    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "4", j, j, schoolTerm);
				    list=(List)map.get("classes");				    
				    if((Integer)map.get("pages")>pages){
				    	pages=(Integer)map.get("pages");
				    }
				    if(list.size()>0){
				    	tables.addCell(writeCells(((Map)list.get(0))));
				    }else{
				    	tables.addCell(doEncode("", 12));
				    }
				    
				    //周5
				    map=new HashMap();
				    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "5", j, j, schoolTerm);
				    list=(List)map.get("classes");
				    
				    if((Integer)map.get("pages")>pages){
				    	pages=(Integer)map.get("pages");
				    }
				    if(list.size()>0){
				    	tables.addCell(writeCells(((Map)list.get(0))));
				    }else{
				    	tables.addCell(doEncode("", 12));
				    }
				    
				    //周六
				    map=new HashMap();
				    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "6", j, j, schoolTerm);
				    list=(List)map.get("classes");				    
				    if((Integer)map.get("pages")>pages){
				    	pages=(Integer)map.get("pages");
				    }				    
				    if(list.size()>0){
				    	tables.addCell(writeCells(((Map)list.get(0))));
				    }else{
				    	tables.addCell(doEncode("", 12));
				    }
				    
				    //周日
				    map=new HashMap();
				    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "7", j, j, schoolTerm);
				    list=(List)map.get("classes");
				    if((Integer)map.get("pages")>pages){
				    	pages=(Integer)map.get("pages");
				    }
				    if(list.size()>0){
				    	tables.addCell(writeCells(((Map)list.get(0))));
				    }else{
				    	tables.addCell(doEncode("", 12));
				    }
				    
				    //?
				    cells = new PdfPCell(doEncodeKaiu(ChWeekH(j), 10));
				    tables.addCell(cells);
				    
				    if(j==4 || j==10){
				    	cells=new PdfPCell(doEncodeKaiu("", 12));
				    	cells.setColspan(9);
				    	tables.addCell(cells);
				    }
				}
				document.add(tables);
				
				sb=new StringBuilder(); //該班所屬學制的上課時間
				
				List dList=manager.ezGetBy("SELECT * FROM Dtimestamp WHERE Cidno='"+((Map)classes.get(i)).get("CampusNo")+"' AND sidno='"+((Map)classes.get(i)).get("SchoolNo")+"' GROUP BY DSvalue ORDER BY DSreal");
				for(int x=0; x<dList.size(); x++){
					sb.append( ((Map)dList.get(x)).get("DsValue")+""+((Map)dList.get(x)).get("DsBegin").toString().substring(0, 5)+"至"+((Map)dList.get(x)).get("DsEnd").toString().substring(0,5)+", " );
				}
				
				try{
					sb.delete(sb.length()-2, sb.length()-1);
					Paragraph parag=new Paragraph(doEncodeKaiu(sb.toString(), 10));
				}catch(Exception e){
					sb.append("");
					
				}
				Paragraph parag=new Paragraph(doEncodeKaiu(sb.toString(), 10));
				document.add(parag);
				
				document.newPage();				
				
				for(int j=1; j<pages; j++){
					table=new Table(6);
					table.setWidth(100);
					
					cell=new Cell();
					try{
						cell.add(doEncodeKaiu(((Map)classes.get(i)).get("ClassName").toString()+" "+year, 20));
					}catch(Exception e){
						cell.add(doEncodeKaiu("老師 "+year, 20));
					}
					
					cell.add(doEncodeKaiu("學年第", 20));
					cell.add(doEncodeKaiu(schoolTerm, 20));
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
					tables = new PdfPTable(widths);
					tables.setWidthPercentage(100);
					
					cells = new PdfPCell(doEncodeKaiu("", 13));
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
				    
				    //內容
					for(int k=1; k<=15; k++){
						cells = new PdfPCell(doEncodeKaiu(ChWeekN(k), 10));
					    tables.addCell(cells);
					    
					    map=new HashMap();
					    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "1", k, k, schoolTerm);
					    list=(List)map.get("classes");
					    if(list.size()>0){
					    	
					    	try{
					    		cells=new PdfPCell(doEncode(
						    			((Map)list.get(j)).get("chi_name").toString()+"\n"+
						    			((Map)list.get(j)).get("c5Name").toString()+
						    			((Map)list.get(j)).get("name").toString()+"\n"+
						    			((Map)list.get(j)).get("cname").toString()+"\n"+
						    			((Map)list.get(j)).get("room_id").toString(), fontSize(((Map)list.get(j)).get("chi_name").toString())));
					    	}catch(Exception e){
					    		cells = new PdfPCell(doEncodeKaiu("", 12));
					    	}
					    }else{
					    	cells = new PdfPCell(doEncodeKaiu("", 12));
					    }
					    tables.addCell(cells);
					    
					    map=new HashMap();
					    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "2", k, k, schoolTerm);
					    list=(List)map.get("classes");
					    if(list.size()>0){
					    	
					    	try{
					    		cells=new PdfPCell(doEncode(
						    			((Map)list.get(j)).get("chi_name").toString()+"\n"+
						    			((Map)list.get(j)).get("c5Name").toString()+
						    			((Map)list.get(j)).get("name").toString()+"\n"+
						    			((Map)list.get(j)).get("cname").toString()+"\n"+
						    			((Map)list.get(j)).get("room_id").toString(), fontSize(((Map)list.get(j)).get("chi_name").toString())));
					    	}catch(Exception e){
					    		cells = new PdfPCell(doEncodeKaiu("", 12));
					    	}
					    }else{
					    	cells = new PdfPCell(doEncodeKaiu("", 12));
					    }
					    tables.addCell(cells);
					    
					    map=new HashMap();
					    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "3", k, k, schoolTerm);
					    list=(List)map.get("classes");
					    if(list.size()>0){
					    	
					    	try{
					    		cells=new PdfPCell(doEncode(
						    			((Map)list.get(j)).get("chi_name").toString()+"\n"+
						    			((Map)list.get(j)).get("c5Name").toString()+
						    			((Map)list.get(j)).get("name").toString()+"\n"+
						    			((Map)list.get(j)).get("cname").toString()+"\n"+
						    			((Map)list.get(j)).get("room_id").toString(), fontSize(((Map)list.get(j)).get("chi_name").toString())));
					    	}catch(Exception e){
					    		cells = new PdfPCell(doEncodeKaiu("", 12));
					    	}
					    }else{
					    	cells = new PdfPCell(doEncodeKaiu("", 12));
					    }
					    tables.addCell(cells);
					    
					    map=new HashMap();
					    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "4", k, k, schoolTerm);
					    list=(List)map.get("classes");
					    if(list.size()>0){
					    	
					    	try{
					    		cells=new PdfPCell(doEncode(
						    			((Map)list.get(j)).get("chi_name").toString()+((Map)list.get(j)).get("cscode").toString()+"\n"+
						    			((Map)list.get(j)).get("c5Name").toString()+
						    			((Map)list.get(j)).get("name").toString()+"\n"+
						    			((Map)list.get(j)).get("cname").toString()+"\n"+
						    			((Map)list.get(j)).get("room_id").toString(), fontSize(((Map)list.get(j)).get("chi_name").toString())));
					    	}catch(Exception e){
					    		cells = new PdfPCell(doEncodeKaiu("", 12));
					    	}
					    }else{
					    	cells = new PdfPCell(doEncodeKaiu("", 12));
					    }
					    tables.addCell(cells);				    
					    
					    map=new HashMap();
					    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "5", k, k, schoolTerm);
					    list=(List)map.get("classes");
					    if(list.size()>0){
					    	
					    	try{
					    		cells=new PdfPCell(doEncode(
						    			((Map)list.get(j)).get("chi_name").toString()+"\n"+
						    			((Map)list.get(j)).get("c5Name").toString()+
						    			((Map)list.get(j)).get("name").toString()+"\n"+
						    			((Map)list.get(j)).get("cname").toString()+"\n"+
						    			((Map)list.get(j)).get("room_id").toString(), fontSize(((Map)list.get(j)).get("chi_name").toString())));
					    	}catch(Exception e){
					    		cells = new PdfPCell(doEncodeKaiu("", 12));
					    	}
					    }else{
					    	cells = new PdfPCell(doEncodeKaiu("", 12));
					    }
					    tables.addCell(cells);				    
					    
					    map=new HashMap();
					    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "6", k, k, schoolTerm);
					    list=(List)map.get("classes");
					    if(list.size()>0){
					    	
					    	try{
					    		cells=new PdfPCell(doEncode(
						    			((Map)list.get(j)).get("chi_name").toString()+"\n"+
						    			((Map)list.get(j)).get("c5Name").toString()+
						    			((Map)list.get(j)).get("name").toString()+"\n"+
						    			((Map)list.get(j)).get("cname").toString()+"\n"+
						    			((Map)list.get(j)).get("room_id").toString(), fontSize(((Map)list.get(j)).get("chi_name").toString())));
					    	}catch(Exception e){
					    		cells = new PdfPCell(doEncodeKaiu("", 12));
					    	}
					    }else{
					    	cells = new PdfPCell(doEncodeKaiu("", 12));
					    }
					    tables.addCell(cells);				    
					    
					    map=new HashMap();
					    map=getClassCsInfo(((Map)classes.get(i)).get("ClassNo").toString(), "7", k, k, schoolTerm);
					    list=(List)map.get("classes");
					    if(list.size()>0){
					    	try{
					    		cells=new PdfPCell(doEncode(
						    			((Map)list.get(j)).get("chi_name").toString()+"\n"+
						    			((Map)list.get(j)).get("c5Name").toString()+
						    			((Map)list.get(j)).get("name").toString()+"\n"+
						    			((Map)list.get(j)).get("cname").toString()+"\n"+
						    			((Map)list.get(j)).get("room_id").toString(), fontSize(((Map)list.get(j)).get("chi_name").toString())));
					    	}catch(Exception e){
					    		cells = new PdfPCell(doEncodeKaiu("", 12));
					    	}
					    }else{
					    	cells = new PdfPCell(doEncodeKaiu("", 12));
					    }
					    tables.addCell(cells);
					    
					    cells = new PdfPCell(doEncodeKaiu(ChWeekH(k), 10));
					    tables.addCell(cells);
					    
					    if(k==4 || k==10){
					    	cells=new PdfPCell(doEncodeKaiu("", 12));
					    	cells.setColspan(9);
					    	tables.addCell(cells);
					    }
					}
					document.add(tables);
					document.newPage();
				}				
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
			bf = BaseFont.createFont("/kaiu.ttf", "Identity-H", BaseFont.NOT_EMBEDDED);
			//bf = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
			font = new Font(bf, fontSize ,0);
			p=new Phrase(nowBy, font);
		}catch (Exception e) {
			bf=null;
			font=null;
			e.printStackTrace();
		}
		bf=null;
		font=null;
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
	private Map getClassCsInfo(String departClass, String week, int j, int j2, String schoolTerm){
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");		
		
		Map map=new HashMap();
		/*
		List it=manager.ezGetBy("SELECT DISTINCT d.Oid, c51.name as bname, d.cscode, c5.name as c5Name, e.cname, n.room_id, c.chi_name, n.name2 as name " +
				"FROM Dtime d LEFT OUTER JOIN empl e ON e.idno=d.techid, Csno c, Class cl, code5 c5,(Dtime_class dc LEFT OUTER JOIN Nabbr n ON n.room_id=dc.place) " +
				"LEFT OUTER JOIN code5 c51 ON c51.idno=n.building AND c51.category='building' WHERE d.cscode=c.cscode AND c5.category='Campus' AND " +
				"c5.idno=cl.CampusNo AND d.Sterm='"+schoolTerm+"' AND cl.ClassNo=d.depart_class AND dc.Dtime_oid=d.Oid AND dc.week='"+week+"' AND (dc.begin<="+j+" && dc.end>="+j2+") AND " +
				"d.depart_class='"+departClass+"'");
		*/
		List it=manager.ezGetBy("SELECT DISTINCT d.Oid, c51.name as bname, d.cscode, c51.sequence as c5Name, e.cname, n.room_id, c.chi_name, n.name2 as name " +
				"FROM Dtime d LEFT OUTER JOIN empl e ON e.idno=d.techid, Csno c, Class cl, (Dtime_class dc LEFT OUTER JOIN Nabbr n ON n.room_id=dc.place) " +
				"LEFT OUTER JOIN code5 c51 ON c51.idno=n.building AND c51.category='building' WHERE d.cscode=c.cscode AND " +
				"d.Sterm='"+schoolTerm+"' AND cl.ClassNo=d.depart_class AND dc.Dtime_oid=d.Oid AND dc.week='"+week+"' AND (dc.begin<="+j+" && dc.end>="+j2+") AND " +
				"d.depart_class='"+departClass+"'");
		
		
		List list=new ArrayList();
		Map myMap;
		for(int i=0; i<it.size(); i++){
			myMap=new HashMap();
			if(((Map)it.get(i)).get("c5Name")!=null){
				myMap.put("c5Name", manager.ezGetString("SELECT name FROM code5 WHERE idno='"+((Map)it.get(i)).get("c5Name")+"' AND category='Campus'"));
			}else{
				myMap.put("c5Name", "");
			}
			
			if(((Map)it.get(i)).get("cname")!=null){
				myMap.put("cname",  ((Map)it.get(i)).get("cname"));
			}else{
				myMap.put("cname", "未指定");
			}
			
			if(((Map)it.get(i)).get("room_id")!=null){
				myMap.put("room_id", ((Map)it.get(i)).get("room_id") );
			}else{
				myMap.put("room_id", "n區");
			}
			
			if(((Map)it.get(i)).get("chi_name")!=null){
				myMap.put("chi_name", ((Map)it.get(i)).get("chi_name") );
			}else{
				myMap.put("chi_name", "未指定");
			}
			
			if(((Map)it.get(i)).get("name")!=null){
				myMap.put("name", ((Map)it.get(i)).get("name") );
			}else{
				myMap.put("name", "");
			}
			
			if(((Map)it.get(i)).get("cscode")!=null){
				myMap.put("cscode", ((Map)it.get(i)).get("Oid") );
			}else{
				myMap.put("name", "");
			}
			
			if(((Map)it.get(i)).get("bname")!=null){
				myMap.put("bname", ((Map)it.get(i)).get("bname") );
			}else{
				myMap.put("bname", "未指定校");
			}
			myMap.put("dtimeOid", ((Map)it.get(i)).get("Oid"));
			list.add(myMap);
		}
		
		map.put("classes", list);
		map.put("pages", list.size());
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
		//System.out.println(map);
		PdfPCell cells=new PdfPCell();
		String line1=map.get("chi_name").toString()+map.get("cscode").toString();   
    	cells.addElement(doEncode(line1, fontSize(line1)));
    	
    	if(manager.ezGetInt("SELECT COUNT(*)FROM Dtime_teacher WHERE Dtime_oid='"+map.get("dtimeOid")+"'")>0){//為1科目多教師
    		cells.addElement(doEncode("多位任課教師..",8));
    	}else{
    		if(!map.get("cname").toString().trim().equals("")){
        		cells.addElement(doEncode(map.get("cname").toString()+" 老師",8));
        	}
    	}
    	
    	
    	
		cells.addElement(doEncode(map.get("c5Name").toString()+
    			map.get("bname").toString()+
    			map.get("room_id").toString().substring(1), 8));
		
		return cells;
	}
	
	private int fontSize(String chiName){
		if(chiName.length()>=10){
			return 6;
		}
		return 9;
	}
}
