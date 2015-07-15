package tw.edu.chit.struts.action.registration.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;
/**
 * 休退學報表
 * @author JOHN
 *
 */
@SuppressWarnings("serial")
public class StudentList4Retire extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		String schoolName=(String) context.getAttribute("SchoolName_ZH");
		String type=request.getParameter("type");
		
		String occur_year="";
		String occur_term="";
		String ClassName="";
		String depart_class="";
		try{
			depart_class=(String)session.getAttribute("classNo");//classNo
		}catch(Exception e){
			depart_class="%";
		}
		
		
		if(!session.getAttribute("occur_year").equals("")){
			occur_year=session.getAttribute("occur_year").toString();
		}//else{
			//occur_year=manager.getSchoolYear().toString();
		//}
		
		if(!session.getAttribute("occur_term").equals("")){
			occur_term=session.getAttribute("occur_term").toString();
		}//else{
			//occur_term=manager.getSchoolTerm().toString();
		//}		
		
		String title="";
		
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;Filename=Excel.xls");
		
		OutputStream outStream = response.getOutputStream();
		HSSFWorkbook wb=new HSSFWorkbook();
		
		HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet=workbook.createSheet();
        
        //設置註解
        HSSFPatriarch patr = sheet.createDrawingPatriarch();
        HSSFComment comment = patr.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short)4, 2, (short) 6, 5));//注解大小
        comment.setString(new HSSFRichTextString("囧n"));// 註解內容
        comment.setAuthor("蕭國裕");//作者
        
        sheet.setAutobreaks(false);//自動換頁 - 關閉
        
        //列印設置
        HSSFPrintSetup hps = sheet.getPrintSetup();        
        hps.setPaperSize((short) 9); // 9=a4紙？
        hps.setLandscape(true); //橫印
        //wb.setPrintArea(0, "$A$1:$C$2");
        
        sheet.setHorizontallyCenter(true); //靠中對齊
        //sheet.setVerticallyCenter(true); // 靠左對齊
        sheet.setPrintGridlines(true);//顯示列印範圍
        //sheet.setMargin(HSSFSheet.TopMargin, 0);
        sheet.setMargin(HSSFSheet.BottomMargin, 0);
        sheet.setMargin(HSSFSheet.LeftMargin, 0);
        sheet.setMargin(HSSFSheet.RightMargin, 0);
        
        sheet.setColumnWidth((short)0, (short)1500);
		sheet.setColumnWidth((short)1, (short)3000);
		sheet.setColumnWidth((short)2, (short)4000);
		sheet.setColumnWidth((short)3, (short)3000);
		sheet.setColumnWidth((short)4, (short)6000);
		sheet.setColumnWidth((short)5, (short)1500);
		sheet.setColumnWidth((short)6, (short)4000);
		sheet.setColumnWidth((short)7, (short)7000);
		sheet.setColumnWidth((short)8, (short)3000);
        
        // 設置表頭(有3種位置)
        HSSFHeader header = sheet.getHeader();
        // header.setRight("Center Header");
        // header.setLeft("Left Header");
        header.setRight(HSSFHeader.font("Stencil-Normal", "Normal")+
        HSSFHeader.fontSize((short) 10)+"第" + HSSFFooter.page() + "頁, 共" + HSSFFooter.numPages()+"頁"); 
        
        // header.endDoubleUnderline();
        header.startUnderline();
        
        HSSFFooter footer = sheet.getFooter();
        //footer.setRight( "Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages() );
        SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
        
        footer.setLeft( schoolName+"學籍管理系統 "+sf.format(new Date()) );
        
        //樣式設定
        HSSFCellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平置中
        //contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);//水平置中
        
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        
        contentStyle.setWrapText(false);//欄位視內容自動調整
        contentStyle.setWrapText(true); 
        
        HSSFFont contentFont = workbook.createFont();
        contentFont.setFontName("Arial");
        contentFont.setFontHeightInPoints((short)12);
        contentStyle.setFont(contentFont);
        
        HSSFFont font = wb.createFont();
        font.setColor(HSSFFont.SS_NONE);
        // font.setFontHeightInPoints((short)24);
        //font.setFontName("標楷體");
        // font.setItalic(true);
        // font.setStrikeout(true);//刪除線
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        contentStyle.setFont(font);
        
        try{        	
        	List students=(List)session.getAttribute("students");        	
        	StringBuilder sb=new StringBuilder();
        	StringBuilder sb1=new StringBuilder();
        	//休學名冊
        	if(type.equals("rer")){
        		/*2010/1/5要求不列出1220, 122A
        		sb.append("SELECT s.depart_class, s.student_no, s.student_name, s.idno, s.entrance, c.Grade, q.occur_year, q.occur_term FROM Gstmd s, " +
        		"QuitResume q, Class c LEFT OUTER JOIN dept d ON c.Dept=d.no WHERE s.student_no=q.student_no AND " +
        		"s.depart_class=c.ClassNo AND q.occur_year LIKE'"+occur_year+"%' AND q.occur_term LIKE'"+occur_term+"%' AND q.depart_class " +
        		"LIKE '"+depart_class+"%'");        		
        		sb1.append("SELECT s.depart_class, s.student_no, s.student_name, s.idno, s.entrance, c.Grade, q.occur_year, q.occur_term FROM stmd s, " +
        		"QuitResume q, Class c LEFT OUTER JOIN dept d ON c.Dept=d.no WHERE s.student_no=q.student_no AND " +
        		"s.depart_class=c.ClassNo AND q.occur_year LIKE'"+occur_year+"%' AND q.occur_term LIKE'"+occur_term+"%' AND q.depart_class " +
        		"LIKE '"+depart_class+"%'");        		
        		title="學期休學生名冊";
        				
        		sb.append("SELECT s.depart_class, s.student_no, s.student_name, s.idno, s.entrance, c.Grade, q.occur_year, q.occur_term FROM Gstmd s, " +
                "QuitResume q, Class c LEFT OUTER JOIN dept d ON c.Dept=d.no WHERE s.student_no=q.student_no AND (s.depart_class<>'122A' AND s.depart_class<>'1220')AND " +
                "s.depart_class=c.ClassNo AND q.occur_year LIKE'"+occur_year+"%' AND q.occur_term LIKE'"+occur_term+"%' AND q.depart_class " +
                "LIKE '"+depart_class+"%'");
        		
                sb1.append("SELECT s.depart_class, s.student_no, s.student_name, s.idno, s.entrance, c.Grade, q.occur_year, q.occur_term FROM stmd s, " +
                "QuitResume q, Class c LEFT OUTER JOIN dept d ON c.Dept=d.no WHERE s.student_no=q.student_no AND (s.depart_class<>'122A' AND s.depart_class<>'1220')AND " +
                "s.depart_class=c.ClassNo AND q.occur_year LIKE'"+occur_year+"%' AND q.occur_term LIKE'"+occur_term+"%' AND q.depart_class " +
                "LIKE '"+depart_class+"%'");
                */
                
        		//2010/1/26指示不再提供列印過去相關記錄，只提供最近一次記錄
        		sb.append("SELECT s.depart_class, s.student_no, s.idno, s.student_name, s.entrance, c.Grade, c5.name, c51.name as fname, e.permission_no, Count(s.student_no) " +
        		"FROM (((Gstmd s LEFT OUTER JOIN  code5 c5 ON c5.category='Dept')LEFT OUTER JOIN code5 c51 ON " +
        		"c51.category='Cause' AND c51.idno=s.occur_cause)LEFT OUTER JOIN entrno e ON e.first_stno<=s.student_no AND " +
        		"second_stno>=s.student_no) ,Class c WHERE c5.idno=c.DeptNo AND " +
        		//"s.occur_year='"+occur_year+"' AND s.occur_term='"+occur_term+"' AND " +
        		"s.occur_status='1' AND c.ClassNo=s.depart_class AND s.student_no IN(");        		
        		for(int i=0; i<students.size(); i++){
        			sb.append("'"+((Map)students.get(i)).get("student_no")+"', " );
        		}
        		sb.delete(sb.length()-2, sb.length());
        		sb.append(") Group By s.student_no");
        		//System.out.print(sb);
                /*
        		sb1.append("SELECT s.depart_class, s.student_no, s.idno, s.student_name, s.entrance, c.Grade, c5.name, c51.name as fname, e.permission_no " +
        		"FROM (((Gstmd s LEFT OUTER JOIN  code5 c5 ON c5.category='Dept')LEFT OUTER JOIN code5 c51 ON " +
        		"c51.category='Cause' AND c51.idno=s.occur_cause)LEFT OUTER JOIN entrno e ON e.first_stno<=s.student_no AND " +
        		"second_stno>=s.student_no) ,Class c WHERE c5.idno=c.DeptNo AND " +
        		//"s.occur_year='"+occur_year+"' AND s.occur_term='"+occur_term+"' AND " +
        		"s.occur_status='1' AND c.ClassNo=s.depart_class AND s.student_no IN(");        		
        		for(int i=0; i<students.size(); i++){
        			sb1.append("'"+((Map)students.get(i)).get("student_no")+"', " );
        		}
        		sb1.delete(sb1.length()-2, sb1.length());
        		sb1.append(")"); 
        		*/
                title="學期休學生名冊";
        	}
        	
        	//退學名冊
        	if(type.equals("rej")){
        		
        		sb.append("SELECT s.depart_class, s.student_no, s.idno, s.student_name, s.entrance, c.Grade, c5.name, c51.name as fname, e.permission_no " +
        		"FROM (((Gstmd s LEFT OUTER JOIN  code5 c5 ON c5.category='Dept')LEFT OUTER JOIN code5 c51 ON " +
        		"c51.category='Cause' AND c51.idno=s.occur_cause)LEFT OUTER JOIN entrno e ON e.first_stno<=s.student_no AND " +
        		"second_stno>=s.student_no) ,Class c WHERE c5.idno=c.DeptNo AND " +
        		//"s.occur_year='"+occur_year+"' AND s.occur_term='"+occur_term+"' AND " +
        		"s.occur_status='2' AND c.ClassNo=s.depart_class AND s.student_no IN(");        		
        		for(int i=0; i<students.size(); i++){
        			sb.append("'"+((Map)students.get(i)).get("student_no")+"', " );
        		}
        		sb.delete(sb.length()-2, sb.length());
        		sb.append(")");  
        		
        		System.out.println(sb);
                title="學期退學生名冊";
        	} 
    		
    		//復學名冊
    		if(type.equals("reb")){
    			
    			sb.append("SELECT s.depart_class, s.student_no, s.student_name, s.idno, s.entrance, c.Grade, q.occur_year, q.occur_term FROM Gstmd s, " +
        		"QuitResume q, Class c LEFT OUTER JOIN dept d ON c.Dept=d.no WHERE s.student_no=q.student_no AND " +
        		"s.depart_class=c.ClassNo AND q.recov_year LIKE'"+occur_year+"%' AND q.recov_term LIKE'"+occur_term+"%' AND q.depart_class " +
        		"LIKE '"+depart_class+"%'");
    	        		
        		sb1.append("SELECT s.depart_class, s.student_no, s.student_name, s.idno, s.entrance, c.Grade, q.occur_year, q.occur_term FROM stmd s, " +
        		"QuitResume q, Class c LEFT OUTER JOIN dept d ON c.Dept=d.no WHERE s.student_no=q.student_no AND " +
        		"s.depart_class=c.ClassNo AND q.recov_year LIKE'"+occur_year+"%' AND q.recov_term LIKE'"+occur_term+"%' AND q.depart_class " +
        		"LIKE '"+depart_class+"%'");
        		
                title="學期復學生名冊";                
        	}
    		
    		
    		
    		students=manager.ezGetBy(sb.toString());   		
    		
    		if(sb1.length()>0){    			
    			students.addAll(manager.ezGetBy(sb1.toString()));
    		}
    		
    		
    		//System.out.println(occur_year);
    		//System.out.println(occur_term);
    		
    		
    		
    		//左上學制名稱
    		if(students.size()>0){//有人畢業的話
    			ClassName=getTitle4Year(((Map)students.get(0)).get("depart_class").toString());
    			
    			header.setCenter(HSSFHeader.font("標楷體", "Normal")+
	    		HSSFHeader.fontSize((short) 18)+schoolName+ClassName+
	    		occur_year+"學年度第"+occur_term+title);
	    		header.setLeft(manager.getClassInfo(((Map)students.get(0)).get("depart_class").toString(), 's'));
    			
    		}else{//沒人畢業
    			HSSFRichTextString c;
	            HSSFRow row = sheet.createRow((short) 0);
	            
				c=new HSSFRichTextString("沒有資料...");
				HSSFCell cell=row.createCell((short) 0);
				//cell.setCellStyle(contentStyle);
            	cell.setCellValue(c);
            	
            	
    		}
    		
    		//報表迴圈
    		
    		String docNo;
            int tmp=0;
    		for(int i=0; i<students.size(); i++){
    			//System.out.println(students.get(i));
                //內容			
    			if(tmp%11==0|| tmp==1){
    				
    				HSSFRichTextString c;
    	            HSSFRow row = sheet.createRow((short) tmp);
    	            
    				c=new HSSFRichTextString("編號");
    				HSSFCell cell=row.createCell((short) 0);
    				cell.setCellStyle(contentStyle);
                	cell.setCellValue(c);                	
                	
                	c=new HSSFRichTextString("學號");
                	cell=row.createCell((short) 1);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("姓名/身分證號");
                	cell=row.createCell((short) 2);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("入學年月");
                	cell=row.createCell((short) 3);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("就讀科系");
                	cell=row.createCell((short) 4);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	
                	
                	c=new HSSFRichTextString("年級");
                	cell=row.createCell((short) 5);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("原因");
                	cell=row.createCell((short) 6);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("入學資格文號");
                	cell=row.createCell((short) 7);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle); 
                	
                	if(type.equals("ready")){//應屆才要簽名
                		c=new HSSFRichTextString("備註");
                    	cell=row.createCell((short) 8);
                    	cell.setCellValue(c);
                    	cell.setCellStyle(contentStyle);            		
                	}                	            	
                	tmp=tmp+1;
    			}
    			
    			HSSFRichTextString c;
                HSSFRow row = sheet.createRow((short) tmp);
    			
                //編號
                c=new HSSFRichTextString(i+1+"");
            	HSSFCell cell=row.createCell((short) 0);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	//學號
            	c=new HSSFRichTextString(((Map)students.get(i)).get("student_no").toString());
            	cell=row.createCell((short) 1);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
                
            	
            	//姓名證號            	
            	c=new HSSFRichTextString( ((Map)students.get(i)).get("student_name").toString()+((Map)students.get(i)).get("idno").toString());
            	cell=row.createCell((short) 2);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle); 
            	
            	//入學年
            	if(((Map)students.get(i)).get("entrance")!=null){
            		c=new HSSFRichTextString(((Map)students.get(i)).get("entrance").toString());
            	}else{
            		c=new HSSFRichTextString("");
            	}
            	cell=row.createCell((short) 3);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	//科系
            	//if(((Map)students.get(i)).get("name")!=null){
            		//System.out.println("SELeCT name FROM Dept d, Class c WHERE d.idno=c.ClassNo WHERE c.ClassNo='"+((Map)students.get(i)).get("depart_class")+"'");
            	try{
            		c=new HSSFRichTextString(getTitle4School(ClassName, manager.ezGetString("SELeCT d.fname FROM dept d, Class c WHERE d.no=c.Dept AND c.ClassNo='"+((Map)students.get(i)).get("depart_class")+"'")));
            	}catch(Exception e){
            		c=new HSSFRichTextString("");
            	}
            	
            	//}else{
            		//c=new HSSFRichTextString("");
            	//}
            	
            	cell=row.createCell((short) 4);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	//年級            	
            	c=new HSSFRichTextString(((Map)students.get(i)).get("Grade").toString());            	
            	cell=row.createCell((short) 5);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	//原因
            	//if(((Map)students.get(i)).get("fname")!=null){
            	/*
            	System.out.println("SELECT c.name FROM code5 c LEFT OUTER JOIN Gmark g ON c.category='Cause' AND " +
        				"CAST(c.idno AS UNSIGNED)=CAST(g.occur_cause AS UNSIGNED) WHERE g.school_year='"+((Map)students.get(i)).get("occur_year")+"' AND " +
        				"g.school_term='"+((Map)students.get(i)).get("occur_term")+"' AND g.student_no='"+((Map)students.get(i)).get("student_no")+"'");
            	*/
            	if(((Map)students.get(i)).get("fname")!=null){
            		c=new HSSFRichTextString(((Map)students.get(i)).get("fname").toString());
            	}else{
            		c=new HSSFRichTextString(manager.ezGetString("SELECT c.name FROM code5 c LEFT OUTER JOIN Gmark g ON c.category='Cause' AND " +
            				"CAST(c.idno AS UNSIGNED)=CAST(g.occur_cause AS UNSIGNED) WHERE g.school_year='"+((Map)students.get(i)).get("occur_year")+"' AND " +
            				"g.school_term='"+((Map)students.get(i)).get("occur_term")+"' AND g.student_no='"+((Map)students.get(i)).get("student_no")+"'"));
            	}
            	
            	
            	
            	
            	//}else{
            		//c=new HSSFRichTextString("");
            	//}
            	cell=row.createCell((short) 6);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	//文號
            	//if(((Map)students.get(i)).get("permission_no")!=null){            	
            	docNo=manager.ezGetString("SELECT e.permission_no FROM  Gstmd s ,entrno e WHERE " +
                		"e.first_stno<=s.student_no AND second_stno>=s.student_no AND s.student_no='"+((Map)students.get(i)).get("student_no")+"'");
            	if(docNo==null){
            		docNo=manager.ezGetString("SELECT e.permission_no FROM  stmd s ,entrno e WHERE " +
                    		"e.first_stno<=s.student_no AND second_stno>=s.student_no AND s.student_no='"+((Map)students.get(i)).get("student_no")+"'");
            	}
            		c=new HSSFRichTextString(docNo);
            	//}else{
            		//c=new HSSFRichTextString("");
            	//}
            	cell=row.createCell((short) 7);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);           	
            	
            	tmp=tmp+1;
            	
    			//換頁行數
                if(tmp%11==0){
                    sheet.setRowBreak(tmp-1);
                }
    		}
    		
    		
    		
    		
        	
        }catch(Exception e){
        	
        	e.printStackTrace();
        	//什麼也不想做
        	
        }
        
		
		
		//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		//cell.setCellValue("...");轉碼範本
		
		//存檔或給檔
		//workbook.write(new   FileOutputStream( "D:/test.xls ")); 
		workbook.write(outStream);
		
		outStream.close(); 
		}
	
	/**
	 * 取系、部
	 * @return
	 */
	private String getTitle4School(String className, String departName){
		StringBuilder sb=new StringBuilder(departName);
		//System.out.println(className);
		
		if(className.indexOf("技")>-1){			
			return departName;
		}
		
		if(className.indexOf("專")>-1){			
			sb.delete(sb.length()-1, sb.length());
			sb.append("科");
			return sb.toString();
		}
		
		if(className.indexOf("碩")>-1){
			
			if(departName.indexOf("機械")>-1 && departName.indexOf("航空")<0){
				return "機電光研究所";
			}else{
				
				departName=departName.replace("系", "研究所");
				if(className.indexOf("機械")>-1 && className.indexOf("航空")<0){
					return "機電光研究所";
				}
				if(departName.indexOf("土木")>-1){
					return "土木防災工程研究所";
				}
				if(departName.indexOf("航空")>-1){
					return "飛機系統工程研究所";
				}
			}			
			
			sb.delete(sb.length()-1, sb.length());
			sb.append("研究所");
			return sb.toString();
		}
		
		return departName;
	}
	
	/**
	 * 取學制年份
	 * @return
	 */
	private String getTitle4Year(String departClass){
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");		
		String schoolNo=manager.ezGetString("SELECT SchoolNo FROM Class WHERE ClassNo='"+departClass+"' LIMIT 1");
		
		return manager.ezGetString("SELECT name FROM code5 WHERE category='School' AND idno='"+schoolNo+"' LIMIT 1");
	}
}
