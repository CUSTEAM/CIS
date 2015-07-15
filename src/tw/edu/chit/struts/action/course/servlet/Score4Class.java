package tw.edu.chit.struts.action.course.servlet;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
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
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import tw.edu.chit.service.CourseManager;

public class Score4Class extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		String schoolName=(String) context.getAttribute("SchoolName_ZH");
		List students=(List)session.getAttribute("students");
		String term=manager.getSchoolTerm().toString();
		
		Date date=new Date();
		DateFormat dateFormat=new SimpleDateFormat("yyyy年 MM月 dd日 HH:mm");
		
		
		String year=manager.getNowBy("School_year");//現在學年
		//第二學期6月後學年+1
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		if(c.get(Calendar.MONTH)>=6 && c.get(Calendar.MONTH)<=7 && term.equals("2")){
			int tmp=Integer.parseInt(year)+1;
			year=tmp+"";
		}
		
		Document document = new Document(PageSize.A4.rotate(), 20, 20, 0, 10);
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		
		
		StringBuilder sb=new StringBuilder("SELECT s.depart_class, c.ClassName FROM stmd s, Class c WHERE c.ClassNo=s.depart_class AND s.student_no IN(");
		
		for(int i=0; i<students.size(); i++){
			sb.append("'"+((Map)students.get(i)).get("student_no")+"', " );
		}
		sb.delete(sb.length()-2, sb.length());
		sb.append(")GROUP BY s.depart_class");
		
		List classes=manager.ezGetBy(sb.toString());//班級數		
		
		try {
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			Phrase now=doEncode(schoolName+"學籍管理系統 "+dateFormat.format(date), 10);
			HeaderFooter header=new HeaderFooter(now,false);
			document.setFooter(header);
			document.open();
			
			float[] widths = {0.75f, 0.25f};
			float[] countCourse={0.08f, 0.08f, 0.06f, 0.06f, 0.06f, 0.06f, 0.06f, 0.06f, 0.06f, 0.06f, 0.06f, 0.06f, 0.06f, 0.06f, 0.06f, 0.06f};
			float[] total={0.16f, 0.16f, 0.16f, 0.16f, 0.16f, 0.2f};
			int t=19;
			for(int i=0; i<classes.size(); i++){
				
				List list=manager.ezGetBy("SELECT student_no, student_name " +
				"FROm stmd WHERE depart_class='"+((Map)classes.get(i)).get("depart_class")+"' ORDER BY student_no");//班級學生
				
				students=getMyScore(list, term);
				students=countAvg(students);
				
				//if(((Map)classes.get(i)).get("depart_class"))
				List courses=manager.ezGetBy("SELECT d.Oid, d.credit, d.opt, c.chi_name " +//班級課程
				"FROM Dtime d, Csno c WHERE d.cscode=c.cscode AND d.depart_class='"+((Map)classes.get(i)).get("depart_class")+"' AND " +
				"Sterm='"+term+"' AND d.cscode<>'50000' AND c.chi_name NOT LIKE'通識%' AND c.chi_name NOT LIKE'%論文%' AND c.chi_name NOT LIKE'%體育%' " +
				"GROUP BY d.cscode");
				
				
				
				//List studenTemp=new ArrayList(); //本班學生暫存器
				//Map myScore;//暫存容器
				//StringBuilder mySb;//暫存文字
				
				Table table=new Table(1);
				table.setWidth(100);
				Cell cell=new Cell();
				
				cell.add(doEncodeKaiu(year+"學年第 ", 20));
				cell.add(doEncodeKaiu(term+"學期 ", 20));
				
				
				try{
					cell.add(doEncodeKaiu(((Map)classes.get(i)).get("ClassName").toString(), 20));
				}catch(Exception e){
					cell.add(doEncodeKaiu("囧 "+year, 20));
					e.printStackTrace();// 無此班級...囧?
				}				
				
				cell.add(doEncodeKaiu(" 成績總表", 20));
				//cell.addElement(logo);
				//cell.setColspan(6);
				cell.setHorizontalAlignment(cell.ALIGN_CENTER);
				cell.setBorderColor(Color.white);
				table.addCell(cell);
				table.setBorderWidth(0);
				//table.setPadding(5);
				document.add(table);
				document.add(new Paragraph(doEncode("\n", 6)));
			    
				PdfPTable tables;
				PdfPTable tableSub;
				PdfPCell cells;
				PdfPCell cellSub;
				
				//全部100%
				tables=new PdfPTable(widths);
				tables.setWidthPercentage(100);
				
			    //左邊70%
				tableSub=new PdfPTable(countCourse);
				
				tableSub.setWidthPercentage(100);
				cellSub=new PdfPCell(doEncodeKaiu("學號", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);
				cellSub=new PdfPCell(doEncodeKaiu("姓名", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);				
				
				
				int tmp=0;
				for(int j=0; j<courses.size(); j++){
					cellSub=new PdfPCell(doEncode(((Map)courses.get(j)).get("chi_name").toString(), 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
					tmp=tmp+1;
				}
				
				for(int j=0; j<16-tmp; j++){
					cellSub=new PdfPCell(doEncodeKaiu("", 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				cells=new PdfPCell(tableSub);
				cells.setBorderColor(Color.white);
				tables.addCell(cells);
				
				
				//右邊30%
				tableSub=new PdfPTable(total);
				//tableSub.addCell(cellSub);
				
				
				cellSub=new PdfPCell(doEncodeKaiu("修習學分總數", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);
					
				cellSub=new PdfPCell(doEncodeKaiu("實得學分總數", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);
				
				cellSub=new PdfPCell(doEncodeKaiu("平均", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);
				
				cellSub=new PdfPCell(doEncodeKaiu("名次", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);
				
				cellSub=new PdfPCell(doEncodeKaiu("操行", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);
				
				cellSub=new PdfPCell(doEncodeKaiu("狀態", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);
				
				
				cells=new PdfPCell(tableSub);
				cells.setBorderColor(Color.white);
				tables.addCell(cells);
				
				
				//資料集
			    //左邊70%				
				for(int j=0; j<students.size(); j++){
					
					//myScore=new HashMap();
					sb=new StringBuilder();//過濾器
					//int myCredit=0;//學分器
					tableSub=new PdfPTable(countCourse);
					tableSub.setWidthPercentage(100);
					
					cellSub=new PdfPCell(doEncodeKaiu(((Map)students.get(j)).get("student_no").toString(), 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);//學號
					
					if(((Map)students.get(j)).get("student_name")!=null){
						cellSub=new PdfPCell(doEncodeKaiu(((Map)students.get(j)).get("student_name").toString(), 8));
					}else{
						cellSub=new PdfPCell(doEncodeKaiu("???", 8));
					}
					
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);//姓名
					
					//各科成績
					tmp=0;
					for(int k=0; k<courses.size(); k++){						
						String score=manager.ezGetString("SELECT score FROM Seld WHERE Dtime_oid='"+((Map)courses.get(k)).get("Oid")+"' " +
						"AND student_no='"+((Map)students.get(j)).get("student_no")+"'");
						sb.append("'"+((Map)courses.get(k)).get("Oid")+"', ");//要排除的部份
						//if(Integer.parseInt(score)>=60){
							//myCredit=myCredit+Integer.parseInt(((Map)courses.get(k)).get("credit").toString());
						//}
						if(score==null){
							score="";
						}
						if(score.trim().equals("")){
							cellSub=new PdfPCell(doEncode("/", 8));
							cellSub.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						}else{
							cellSub=new PdfPCell(doEncode(score, 8));
						}
						
						cellSub.setBorderColor(Color.white);
						tableSub.addCell(cellSub);
						tmp=tmp+1;
					}
					for(int k=0; k<16-tmp; k++){
						cellSub=new PdfPCell(doEncode("", 8));
						cellSub.setBorderColor(Color.white);
						tableSub.addCell(cellSub);
					}
					
					
					cells=new PdfPCell(tableSub);
					cells.setBorderColor(Color.white);
					tables.addCell(cells);				

					
					//右邊30%
					tableSub=new PdfPTable(total);
					//tableSub.addCell(cellSub);
					
					cellSub=new PdfPCell(doEncode(((Map)students.get(j)).get("myCreditTotal").toString(), 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
						
					cellSub=new PdfPCell(doEncode(((Map)students.get(j)).get("myCredit").toString(), 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
					
					cellSub=new PdfPCell(doEncode(((Map)students.get(j)).get("score").toString(), 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
					
					cellSub=new PdfPCell(doEncode(((Map)students.get(j)).get("x").toString(), 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
					
					cellSub=new PdfPCell(doEncode(((Map)students.get(j)).get("just").toString(), 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
					
					cellSub=new PdfPCell(doEncodeKaiu("", 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
					
					
					cells=new PdfPCell(tableSub);
					cells.setBorderColor(Color.white);
					tables.addCell(cells);					
					
					if(j%t==0&& j!=0&& students.size()>26){
												
						t=t+21;

					    //左邊70%
						tableSub=new PdfPTable(countCourse);
						
						tableSub.setWidthPercentage(100);
						cellSub=new PdfPCell(doEncodeKaiu("學號", 8));
						cellSub.setBorderColor(Color.white);
						tableSub.addCell(cellSub);
						cellSub=new PdfPCell(doEncodeKaiu("姓名", 8));
						cellSub.setBorderColor(Color.white);
						tableSub.addCell(cellSub);				
						
						tmp=0;
						for(int k=0; k<courses.size(); k++){
							cellSub=new PdfPCell(doEncode(((Map)courses.get(k)).get("chi_name").toString(), 8));
							cellSub.setBorderColor(Color.white);
							tableSub.addCell(cellSub);
							tmp=tmp+1;
						}
						for(int k=0; k<16-tmp; k++){
							cellSub=new PdfPCell(doEncodeKaiu("", 8));
							cellSub.setBorderColor(Color.white);
							tableSub.addCell(cellSub);
						}
						
						cells=new PdfPCell(tableSub);
						cells.setBorderColor(Color.white);
						tables.addCell(cells);
						
						
						//右邊30%
						tableSub=new PdfPTable(total);
						//tableSub.addCell(cellSub);
						
						
						cellSub=new PdfPCell(doEncodeKaiu("修習學分總數", 8));
						cellSub.setBorderColor(Color.white);
						tableSub.addCell(cellSub);
							
						cellSub=new PdfPCell(doEncodeKaiu("實得學分總數", 8));
						cellSub.setBorderColor(Color.white);
						tableSub.addCell(cellSub);
						
						cellSub=new PdfPCell(doEncodeKaiu("平均", 8));
						cellSub.setBorderColor(Color.white);
						tableSub.addCell(cellSub);
						
						cellSub=new PdfPCell(doEncodeKaiu("名次", 8));
						cellSub.setBorderColor(Color.white);
						tableSub.addCell(cellSub);
						
						cellSub=new PdfPCell(doEncodeKaiu("操行", 8));
						cellSub.setBorderColor(Color.white);
						tableSub.addCell(cellSub);
						
						cellSub=new PdfPCell(doEncodeKaiu("狀態", 8));
						cellSub.setBorderColor(Color.white);
						tableSub.addCell(cellSub);
						
						
						cells=new PdfPCell(tableSub);
						cells.setBorderColor(Color.white);
						tables.addCell(cells);
						
					}
					
					//加1條100% 多餘的課 
					//StringBuilder more=new StringBuilder();
					if(sb.length()>2 ){
						sb.delete(sb.length()-2, sb.length());
						
						
						List moreCs=manager.ezGetBy("SELECT c.chi_name, s.score, d.credit FROM Seld s, Csno c, Dtime d " +
						"WHERE s.Dtime_oid=d.Oid AND d.cscode=c.cscode AND d.Sterm='" +term+"' AND "+
						"s.student_no='"+((Map)students.get(j)).get("student_no")+"' AND d.Oid NOT IN("+sb+")");
						sb=new StringBuilder();
						for(int k=0; k<moreCs.size(); k++){
							sb.append( ((Map)moreCs.get(k)).get("chi_name")+"("+((Map)moreCs.get(k)).get("credit")+") "+((Map)moreCs.get(k)).get("score")+", " );
						}
						if(moreCs.size()>0){
							sb.delete(sb.length()-2, sb.length());
							cells=new PdfPCell(doEncodeKaiu("加選科目:\t"+sb.toString(), 8));
							cells.setBorderColor(Color.white);
						}else{
							cells=new PdfPCell(doEncodeKaiu("", 8));
							cells.setBorderColor(Color.white);
						}
						
					}else{
						//cells=new PdfPCell(doEncodeKaiu("", 8));
						//cells.setBorderColor(Color.white);
						List moreCs=manager.ezGetBy("SELECT c.chi_name, s.score, d.credit FROM Seld s, Csno c, Dtime d " +
						"WHERE s.Dtime_oid=d.Oid AND d.cscode=c.cscode AND d.Sterm='" +term+"' AND "+
						"s.student_no='"+((Map)students.get(j)).get("student_no")+"'");
												
						sb=new StringBuilder();
						for(int k=0; k<moreCs.size(); k++){
							sb.append( ((Map)moreCs.get(k)).get("chi_name")+"("+((Map)moreCs.get(k)).get("credit")+") "+((Map)moreCs.get(k)).get("score")+", " );
						}
						if(moreCs.size()>0){
							sb.delete(sb.length()-2, sb.length());
							cells=new PdfPCell(doEncodeKaiu("加選科目:\t"+sb.toString(), 8));
							cells.setBorderColor(Color.white);
						}else{
							cells=new PdfPCell(doEncodeKaiu("", 8));
							cells.setBorderColor(Color.white);
						}
						
					}
					
					cells.setColspan(2);
					tables.addCell(cells);
					
				}				
				
				//及格人數
			    //左邊70%
				tableSub=new PdfPTable(countCourse);
				
				tableSub.setWidthPercentage(100);
				cellSub=new PdfPCell(doEncodeKaiu("", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);
				cellSub=new PdfPCell(doEncodeKaiu("及格人數", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);				
				
				tmp=0;
				for(int j=0; j<courses.size(); j++){
					cellSub=new PdfPCell(doEncode(manager.ezGetString("SELECT COUNT(*)FROM Seld WHERE score>=60 AND Dtime_oid="+((Map)courses.get(j)).get("Oid")), 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
					tmp=tmp+1;
				}
				for(int j=0; j<16-tmp; j++){
					cellSub=new PdfPCell(doEncodeKaiu("", 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
				}
				
				cells=new PdfPCell(tableSub);
				cells.setBorderColor(Color.white);
				tables.addCell(cells);

				//右邊30%
				cells=new PdfPCell(set30PdfPTable(total));
				cells.setBorderColor(Color.white);
				tables.addCell(cells);				
				
				//不及格人數
			    //左邊70%
				tableSub=new PdfPTable(countCourse);
				
				tableSub.setWidthPercentage(100);
				cellSub=new PdfPCell(doEncodeKaiu("", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);
				cellSub=new PdfPCell(doEncodeKaiu("不及格人數", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);				
				
				tmp=0;
				for(int j=0; j<courses.size(); j++){
					cellSub=new PdfPCell(doEncode(manager.ezGetString("SELECT COUNT(*)FROM Seld WHERE score<60 AND Dtime_oid="+((Map)courses.get(j)).get("Oid")), 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
					tmp=tmp+1;
				}
				for(int j=0; j<16-tmp; j++){
					cellSub=new PdfPCell(doEncodeKaiu("", 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
				}
				
				cells=new PdfPCell(tableSub);
				cells.setBorderColor(Color.white);
				tables.addCell(cells);
				
				//右邊30%
				cells=new PdfPCell(set30PdfPTable(total));
				cells.setBorderColor(Color.white);
				tables.addCell(cells);
				
				//平均分數
			    //左邊70%
				tableSub=new PdfPTable(countCourse);
				
				tableSub.setWidthPercentage(100);
				cellSub=new PdfPCell(doEncodeKaiu("", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);
				cellSub=new PdfPCell(doEncodeKaiu("平均分數", 8));
				cellSub.setBorderColor(Color.white);
				tableSub.addCell(cellSub);				
				
				tmp=0;
				for(int j=0; j<courses.size(); j++){
					float bg=0;
					List ms=manager.ezGetBy("SELECT score FROM Seld WHERE Dtime_oid="+((Map)courses.get(j)).get("Oid"));
					for(int k=0; k<ms.size(); k++){
						
						//TODO null的成績
						if(((Map)ms.get(k)).get("score")!=null)
						try{
							bg=bg+Float.parseFloat( ((Map)ms.get(k)).get("score").toString());
						}catch(Exception e){
							e.printStackTrace();
						}
						
					}
					
					cellSub=new PdfPCell(doEncode(manager.roundOff((bg/students.size()), 1)+"", 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
					tmp=tmp+1;
				}
				for(int j=0; j<16-tmp; j++){
					cellSub=new PdfPCell(doEncodeKaiu("", 8));
					cellSub.setBorderColor(Color.white);
					tableSub.addCell(cellSub);
				}
				
				cells=new PdfPCell(tableSub);
				cells.setBorderColor(Color.white);
				tables.addCell(cells);
				
				//右邊30%
				cells=new PdfPCell(set30PdfPTable(total));
				cells.setBorderColor(Color.white);
				tables.addCell(cells);
				
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
	 * 右邊30%(空白)
	 * @param total
	 * @return
	 */
	private PdfPTable set30PdfPTable(float total[]){
		
		PdfPTable tableSub=new PdfPTable(total);
		//tableSub.addCell(cellSub);
		
		
		PdfPCell cellSub=new PdfPCell(doEncodeKaiu("", 8));
		cellSub.setBorderColor(Color.white);
		tableSub.addCell(cellSub);
			
		cellSub=new PdfPCell(doEncodeKaiu("", 8));
		cellSub.setBorderColor(Color.white);
		tableSub.addCell(cellSub);
		
		cellSub=new PdfPCell(doEncodeKaiu("", 8));
		cellSub.setBorderColor(Color.white);
		tableSub.addCell(cellSub);
		
		cellSub=new PdfPCell(doEncodeKaiu("", 8));
		cellSub.setBorderColor(Color.white);
		tableSub.addCell(cellSub);
		
		cellSub=new PdfPCell(doEncodeKaiu("", 8));
		cellSub.setBorderColor(Color.white);
		tableSub.addCell(cellSub);
		
		cellSub=new PdfPCell(doEncodeKaiu("", 8));
		cellSub.setBorderColor(Color.white);
		tableSub.addCell(cellSub);
		
		
		return tableSub;
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
			//bf = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
			font = new Font(bf, fontSize ,0);
			p=new Phrase(nowBy, font);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	/**
	 * 取得進階資訊
	 * @param list
	 * @param term
	 * @return
	 */
	private List getMyScore(List list, String term){
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		List students=new ArrayList();
		Map st;
		
		for(int j=0; j<list.size(); j++){
			st=new HashMap();
			st.put("student_no", ((Map)list.get(j)).get("student_no"));
			st.put("student_name", ((Map)list.get(j)).get("student_name"));
			float myCreidt=0;//實修學分
			float myCreditTotal=0;//應修學分
			float avg=0;//平均分數
			
			List myScore=manager.ezGetBy("SELECT s.score, d.credit FROM Csno c, Seld s, Dtime d WHERE s.Dtime_oid=d.Oid AND " +
			"c.cscode=d.cscode AND c.chi_name NOT LIKE'%論文%' AND s.student_no='"+((Map)list.get(j)).get("student_no")+"' AND d.Sterm='"+term+"'");
			int rc=0;//TODO null成績不計算或是直接給予鴨蛋????
			for(int k=0; k<myScore.size(); k++){
				
				if(((Map)myScore.get(k)).get("score")!=null){
					rc=rc+1;
					myCreditTotal=myCreditTotal+Float.parseFloat(((Map)myScore.get(k)).get("credit").toString());
					//學分數加成？
					//avg=(avg+Float.parseFloat(((Map)myScore.get(k)).get("score").toString())* Float.parseFloat(((Map)myScore.get(k)).get("credit").toString()));
					avg=(avg+Float.parseFloat(((Map)myScore.get(k)).get("score").toString()));
					if(Float.parseFloat(((Map)myScore.get(k)).get("score").toString())>=60){
						myCreidt=myCreidt+Float.parseFloat(((Map)myScore.get(k)).get("credit").toString());
					}
				}
			}
			st.put("myCredit", myCreidt);
			st.put("myCreditTotal", myCreditTotal);
			st.put("avg", manager.roundOff(avg/rc, 1));
			
			try{
				st.put("just", manager.ezGetInt("SELECT total_score FrOM just WHERE student_no='"+((Map)list.get(j)).get("student_no")+"'"));
			}catch(Exception e){
				st.put("just", "0");
			}
			
			students.add(st);
		}		
		return students;
	}
	
	/**
	 * 排名次的實作
	 * @param list
	 * @return
	 */
	private List countAvg(List list) {
		//System.out.println("list.size="+list.size());
		List l = new ArrayList();
		
		for (int i = 0; i < list.size(); i++) {
			l.add(new Person(
					((Map) list.get(i)).get("student_no").toString(), 
					Float.parseFloat(((Map) list.get(i)).get("avg").toString()), 
					((Map) list.get(i)).get("student_name").toString(), 
					Float.parseFloat(((Map) list.get(i)).get("myCredit").toString()), 
					Float.parseFloat(((Map) list.get(i)).get("myCreditTotal").toString()), 
					Integer.parseInt(((Map) list.get(i)).get("just").toString()), i, 0));
		}
		
		ScoreComparator scoreComp = new ScoreComparator(); //排分數用的
		Collections.sort(l, scoreComp);
		
		List ok=new ArrayList();
		int x=1;//名次...
		for (int i = 0; i < l.size(); i++) {
			Person p = (Person) l.get(i);
			
			p.setPlace(x);
			x=x+1;
			ok.add(p);
		}
		
		NoComparator noComp = new NoComparator(); //排學號用的
		Collections.sort(ok, noComp);
		
		List fine=new ArrayList();
		Map map;
		for (int i = 0; i < ok.size(); i++) {
			Person p = (Person) ok.get(i);
			//System.out.println(p.getStudent_no()+": "+p.getScore());
			
			map=new HashMap();
			map.put("student_no", p.getStudent_no());
			map.put("student_name", p.getStudent_name());
			map.put("myCredit", p.getMyCredit());
			map.put("myCreditTotal", p.getMyCreditTotal());
			map.put("just", p.getJust());
			map.put("score", p.getScore());
			map.put("x", p.getPlace());
			
			//System.out.println(map);
			fine.add(map);
		}
		
		
		
		
		//System.out.println("ok.size="+ok.size());
		return fine;
	}
	
	/**
	 * 暫存用的學生成績物件
	 * @author JOHN
	 *
	 */
	class Person {
		String student_no;
		Float score;		
		String student_name;
		Float myCredit;
		Float myCreditTotal;
		int just;
		int no;
		int place;

		public Person(String student_no, Float score, String student_name, 
				Float myCredit, Float myCreditTotal, int just, int no, int place) {
			
			this.student_no = student_no;
			this.score = score;
			this.student_name=student_name;
			this.myCredit=myCredit;
			this.myCreditTotal=myCreditTotal;
			this.just=just;
			this.no=no;
			this.place=place;

		}
		
		public Float getScore() {
			return score;
		}

		public void setScore(Float score) {
			this.score = score;
		}

		public String getStudent_no() {
			return student_no;
		}

		public void setStudent_no(String student_no) {
			this.student_no = student_no;
		}

		public String getStudent_name() {
			return student_name;
		}

		public void setStudent_name(String student_name) {
			this.student_name = student_name;
		}

		public Float getMyCredit() {
			return myCredit;
		}

		public void setMyCredit(Float myCredit) {
			this.myCredit = myCredit;
		}

		public Float getMyCreditTotal() {
			return myCreditTotal;
		}

		public void setMyCreditTotal(Float myCreditTotal) {
			this.myCreditTotal = myCreditTotal;
		}

		public int getJust() {
			return just;
		}

		public void setJust(int just) {
			this.just = just;
		}
		
		public int getNo() {
			return no;
		}

		public void setNo(int no) {
			this.no = no;
		}
		
		public int getPlace() {
			return place;
		}

		public void setPlace(int place) {
			this.place = place;
		}

	}
	
	/**
	 * 分數排序
	 * @author JOHN
	 *
	 */
	class ScoreComparator implements Comparator {

		public int compare(Object o1, Object o2) {
			Person p1 = (Person) o1;
			Person p2 = (Person) o2;
			if (p1.score < p2.score)
				return 1;
			else
				return 0;
		}

	}
	
	/**
	 * 學號排序
	 * @author JOHN
	 *
	 */
	class NoComparator implements Comparator {

		public int compare(Object o1, Object o2) {
			Person p1 = (Person) o1;
			Person p2 = (Person) o2;
			if (p1.no > p2.no)
				return 1;
			else
				return 0;
		}

	}

}