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
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import tw.edu.chit.service.CourseManager;

public class Score4Personal extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		System.out.println("work?");
		
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
		
		Document document = new Document(PageSize.A4, 20, 20, 0, 10);
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		 
		StringBuilder sb=new StringBuilder("SELECT s.depart_class, c.ClassName FROM stmd s, Class c WHERE c.ClassNo=s.depart_class AND s.student_no IN(");
		
		for(int i=0; i<students.size(); i++){
			sb.append("'"+((Map)students.get(i)).get("student_no")+"', " );
		}
		sb.delete(sb.length()-2, sb.length());
		sb.append(")GROUP BY s.depart_class");
		
		List classes=manager.ezGetBy(sb.toString());//班級數		
		
		try {
			
			//Image image=Image.getInstance("http://cap.chit.edu.tw/CIS/pages/images/2002chitWatermark_reg_d.jpg");
			Image image=Image.getInstance("http://192.192.237.19/CIS/pages/images/art.gif");
			//BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, 
			        //BaseFont.WINANSI, BaseFont.EMBEDDED);
			image.setAbsolutePosition(100, 100);
			//image.setWidthPercentage(50);
			
			
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			Phrase now=doEncode(schoolName+"學籍管理系統 "+dateFormat.format(date), 10);
			HeaderFooter header=new HeaderFooter(now,false);
			document.setFooter(header);
			document.open();
			
				
			
		    
			for(int i=0; i<500; i++){
							
			    writer.getDirectContentUnder().addImage(image);
			    
			    Table table=new Table(1);
				table.setWidth(100);
				Cell cell=new Cell();
				
				cell.add(doEncodeKaiu(year+"學年第 ", 20));
				cell.add(doEncodeKaiu(term+"學期 ", 20));
				
				
				try{
					cell.add(doEncodeKaiu(((Map)classes.get(i)).get("ClassName").toString(), 20));
				}catch(Exception e){
					cell.add(doEncodeKaiu(year, 20));
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
			    
				for(int j=0; j<35; j++){
					
					
					document.add(doEncodeKaiu("\n", 12));
					
				}
				
			    //writer.getDirectContentUnder().addImage(image);
			    
			    
			    
			    
				document.newPage();
			}
			
			
			
			
			
			
			document.close();
			response.setContentType("text/html; charset=UTF-8");
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment;filename=Score4Personal.pdf");
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
		//HttpSession session = request.getSession(false);
		
		List students=new ArrayList();
		Map st;
		
		for(int j=0; j<list.size(); j++){
			st=new HashMap();
			st.put("student_no", ((Map)list.get(j)).get("student_no"));
			st.put("student_name", ((Map)list.get(j)).get("student_name"));
			float myCreidt=0;//實修學分
			float myCreditTotal=0;//應修學分
			float avg=0;//平均分數
			
			List myScore=manager.ezGetBy("SELECT s.score, d.credit FROM Seld s, Dtime d WHERE s.Dtime_oid=d.Oid AND " +
			"s.student_no='"+((Map)list.get(j)).get("student_no")+"' AND d.Sterm='"+term+"'");
			for(int k=0; k<myScore.size(); k++){
				
				if(((Map)myScore.get(k)).get("score")!=null){
					myCreditTotal=myCreditTotal+Float.parseFloat(((Map)myScore.get(k)).get("credit").toString());
					avg=avg+Float.parseFloat(((Map)myScore.get(k)).get("score").toString());
					if(Float.parseFloat(((Map)myScore.get(k)).get("score").toString())>=60){
						myCreidt=myCreidt+Float.parseFloat(((Map)myScore.get(k)).get("credit").toString());
					}
				}
			}
			st.put("myCredit", myCreidt);
			st.put("myCreditTotal", myCreditTotal);
			st.put("avg", manager.roundOff(avg/myScore.size(), 1));
			st.put("just", manager.ezGetInt("SELECT total_score FrOM just WHERE student_no='"+((Map)list.get(j)).get("student_no")+"'"));
			
			students.add(st);
		}		
		return students;
	}
	
	/**
	 * 排名次
	 * @param list
	 * @return
	 */
	private List countAvg(List list) {
		List l = new ArrayList();
		
		for (int i = 0; i < list.size(); i++) {
			l.add(new Person(
					((Map) list.get(i)).get("student_no").toString(), 
					Float.parseFloat(((Map) list.get(i)).get("avg").toString()), 
					((Map) list.get(i)).get("student_name").toString(), 
					Float.parseFloat(((Map) list.get(i)).get("myCredit").toString()), 
					Float.parseFloat(((Map) list.get(i)).get("myCreditTotal").toString()), 
					Integer.parseInt(((Map) list.get(i)).get("just").toString())));
		}
		
		Comparator comp = new Mycomparator();
		Collections.sort(l, comp);
		
		List ok=new ArrayList();
		Map map;
		int x=1;
		for (int i = 0; i < l.size(); i++) {
			Person p = (Person) l.get(i);
			
			map=new HashMap();
			map.put("student_no", p.getStudent_no());
			map.put("student_name", p.getStudent_name());
			map.put("myCredit", p.getMyCredit());
			map.put("myCreditTotal", p.getMyCreditTotal());
			map.put("just", p.getJust());
			map.put("score", p.getScore());
			map.put("x", x);
			x=x+1;
			ok.add(map);
		}
		return ok;
	}
	
	/**
	 * 組合1名學生的成績
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

		public Person(String student_no, Float score, String student_name, 
				Float myCredit, Float myCreditTotal, int just) {
			
			this.student_no = student_no;
			this.score = score;
			this.student_name=student_name;
			this.myCredit=myCredit;
			this.myCreditTotal=myCreditTotal;
			this.just=just;

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

	}
	
	/**
	 * 比較成績
	 * @author JOHN
	 *
	 */
	class Mycomparator implements Comparator {

		public int compare(Object o1, Object o2) {
			Person p1 = (Person) o1;
			Person p2 = (Person) o2;
			if (p1.score < p2.score)
				return 1;
			else
				return 0;
		}

	}
	

}