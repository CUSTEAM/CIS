package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

/**
 * 加退總表
 * @author JOHN
 *
 */
public class List4Adcd extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("work");
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);		
		ServletContext context = request.getSession().getServletContext();
		String schoolName=(String) context.getAttribute("SchoolName_ZH");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;Filename=Excel.xls");
		
		OutputStream outStream = response.getOutputStream();
		HSSFWorkbook wb=new HSSFWorkbook();
		
		HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet=workbook.createSheet();
        
        //設置註解
        //HSSFPatriarch patr = sheet.createDrawingPatriarch();
        //HSSFComment comment = patr.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short)4, 2, (short) 6, 5));//注解大小
        //comment.setString(new HSSFRichTextString("囧n"));// 註解內容
        //comment.setAuthor("John");//作者
        
        sheet.setAutobreaks(false);//自動換頁 - 關閉
        
        //列印設置
        HSSFPrintSetup hps = sheet.getPrintSetup();        
        hps.setPaperSize((short) 9); // 9=a4紙？
        //hps.setLandscape(true); //橫印
        //wb.setPrintArea(0, "$A$1:$C$2");
        
        sheet.setHorizontallyCenter(true); //靠中對齊
        //sheet.setVerticallyCenter(true); // 靠左對齊
        sheet.setPrintGridlines(true);//顯示列印範圍
        //sheet.setMargin(HSSFSheet.TopMargin, 0);
        sheet.setMargin(HSSFSheet.BottomMargin, 0);
        sheet.setMargin(HSSFSheet.LeftMargin, 0);
        sheet.setMargin(HSSFSheet.RightMargin, 0);
        
        
        int schoolYear=manager.getSchoolYear();
        int schoolTerm=manager.getSchoolTerm();
        
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
        
        footer.setLeft( schoolName+"課程管理系統 "+sf.format(new Date()) );
        
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
		
        header.setCenter(HSSFHeader.font("標楷體", "Normal")+
		HSSFHeader.fontSize((short) 12)+schoolName+" "+schoolYear+"學年度第"+schoolTerm+"學期\n學生加退選明細表");
		//header.setLeft(manager.getClassInfo(((Map)students.get(0)).get("depart_class").toString(), 's'));
		
		
		
        
        String classLess=(String)session.getAttribute("classLess");
        String term=manager.getSchoolTerm().toString();
        
        
        List list=manager.ezGetBy("SELECT c1.ClassName as csClassName, c2.ClassName as stClassName, " +
        		"st.student_no, st.student_name, a.Adddraw, c.cscode, c.chi_name, d.credit " +
        		"FROM AddDelCourseData a, Dtime d, stmd st, Class c1, Class c2, Csno c WHERE c.cscode=d.cscode AND " +
        		"c1.ClassNo=d.depart_class AND c2.ClassNo=st.depart_class AND a.Dtime_oid=d.Oid AND " +
        		"st.student_no=a.student_no AND d.Sterm='"+term+"' AND st.depart_class LIKE '"+classLess+"%'");
        
        List allAdcd=new ArrayList();
        Map map;
        for(int i=0; i<list.size(); i++){
        	map=new HashMap();
        	map.put("stClassName", ((Map)list.get(i)).get("stClassName"));
        	map.put("csClassName", ((Map)list.get(i)).get("csClassName"));
        	map.put("student_no", ((Map)list.get(i)).get("student_no"));
        	map.put("student_name", ((Map)list.get(i)).get("student_name"));
        	
        	
        	if(((Map)list.get(i)).get("Adddraw").equals("A")){
        		map.put("Adddraw", "加選");
        	}else{
        		map.put("Adddraw", "退選");
        	}
        	
        	map.put("cscode", ((Map)list.get(i)).get("cscode"));
        	map.put("chi_name", ((Map)list.get(i)).get("chi_name"));
        	map.put("credit", ((Map)list.get(i)).get("credit"));
        	
        	allAdcd.add(map);
        }
        
        
        
        
        
        
        
        
        
        int tmp=0;
        for(int i=0; i<allAdcd.size(); i++){
        	
        	
        	
        	
        	
        	if(tmp%26==0|| tmp==1){
    			
    			HSSFRichTextString c;
	            HSSFRow row = sheet.createRow((short) tmp);
	            row.setHeight((short) 500);
	            
	            c=new HSSFRichTextString("班級");
        		HSSFCell cell=row.createCell((short) 0);
        		cell.setCellStyle(contentStyle);
            	cell.setCellValue(c);      
            	
            	c=new HSSFRichTextString("學號");
            	cell=row.createCell((short) 1);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	c=new HSSFRichTextString("姓名");
            	cell=row.createCell((short) 2);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	c=new HSSFRichTextString("加退");
            	cell=row.createCell((short) 3);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	c=new HSSFRichTextString("開課班級");
            	cell=row.createCell((short) 4);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	c=new HSSFRichTextString("代碼");
            	cell=row.createCell((short) 5);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	c=new HSSFRichTextString("科目名稱");
            	cell=row.createCell((short) 6);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	c=new HSSFRichTextString("學分");
            	cell=row.createCell((short) 7);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	
            	tmp=tmp+1;    			
    		}
        	
        	
        	
        	
        	HSSFRichTextString c;
            HSSFRow row = sheet.createRow((short) tmp);
            row.setHeight((short) 500);
            
            c=new HSSFRichTextString(((Map)allAdcd.get(i)).get("stClassName").toString());
    		HSSFCell cell=row.createCell((short) 0);
    		cell.setCellStyle(contentStyle);
        	cell.setCellValue(c);      
        	
        	c=new HSSFRichTextString(((Map)allAdcd.get(i)).get("student_no").toString());
        	cell=row.createCell((short) 1);
        	cell.setCellValue(c);
        	cell.setCellStyle(contentStyle);
        	
        	c=new HSSFRichTextString(((Map)allAdcd.get(i)).get("student_name").toString());
        	cell=row.createCell((short) 2);
        	cell.setCellValue(c);
        	cell.setCellStyle(contentStyle);
        	
        	c=new HSSFRichTextString(((Map)allAdcd.get(i)).get("Adddraw").toString());
        	cell=row.createCell((short) 3);
        	cell.setCellValue(c);
        	cell.setCellStyle(contentStyle);
        	
        	c=new HSSFRichTextString(((Map)allAdcd.get(i)).get("csClassName").toString());
        	cell=row.createCell((short) 4);
        	cell.setCellValue(c);
        	cell.setCellStyle(contentStyle);
        	
        	c=new HSSFRichTextString(((Map)allAdcd.get(i)).get("cscode").toString());
        	cell=row.createCell((short) 5);
        	cell.setCellValue(c);
        	cell.setCellStyle(contentStyle);
        	
        	c=new HSSFRichTextString(((Map)allAdcd.get(i)).get("chi_name").toString());
        	cell=row.createCell((short) 6);
        	cell.setCellValue(c);
        	cell.setCellStyle(contentStyle);
        	
        	c=new HSSFRichTextString(((Map)allAdcd.get(i)).get("credit").toString());
        	cell=row.createCell((short) 7);
        	cell.setCellValue(c);
        	cell.setCellStyle(contentStyle);
        	
        	
        	
        	
        	
        	tmp=tmp+1;
        	
			//換頁行數
            if(tmp%26==0){
                sheet.setRowBreak(tmp-1);
            }
        	
        	
        	
        	
        	
        }
        
        
    	
    	
    	
    	
        sheet.setColumnWidth((short)0, (short)4000);
		sheet.setColumnWidth((short)1, (short)3000);
		sheet.setColumnWidth((short)2, (short)2000);
		sheet.setColumnWidth((short)3, (short)1500);
		sheet.setColumnWidth((short)4, (short)4000);
		sheet.setColumnWidth((short)5, (short)2000);
		sheet.setColumnWidth((short)6, (short)6500);
		sheet.setColumnWidth((short)7, (short)2000);
    	
		
    	workbook.write(outStream);		
		outStream.flush();
	}

}
