package tw.edu.chit.struts.action.course.servlet;

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
 * 轉學生名冊
 * @author JOHN
 *
 */
public class StudentList4Transfer extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		
		ServletContext context = request.getSession().getServletContext();
		String schoolName=(String) context.getAttribute("SchoolName_ZH");
		String type="ready";
		String title="學期轉學生名冊";
		String graduate="";
		
		response.setContentType("application/vnd.ms-excel;charset=GBK");
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
		sheet.setColumnWidth((short)1, (short)3500);
		sheet.setColumnWidth((short)2, (short)2500);
		sheet.setColumnWidth((short)3, (short)3000);
		sheet.setColumnWidth((short)4, (short)1500);
		sheet.setColumnWidth((short)5, (short)3500);
		sheet.setColumnWidth((short)6, (short)3000);
		sheet.setColumnWidth((short)7, (short)2000);
		sheet.setColumnWidth((short)8, (short)5500);
		sheet.setColumnWidth((short)9, (short)4500);
		//sheet.setColumnWidth((short)10, (short)1500);
		sheet.setColumnWidth((short)10, (short)4500);
        
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
        

		//取頁面學年學期
		String occur_year="";
		String occur_term="";
		if(!session.getAttribute("occur_year").equals("")){
			occur_year=session.getAttribute("occur_year").toString();
		}else{
			occur_year=manager.getSchoolYear().toString();
		}
		
		if(!session.getAttribute("occur_term").equals("")){
			occur_term=session.getAttribute("occur_term").toString();
		}else{
			occur_term=manager.getSchoolTerm().toString();
		}
        
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
    		StringBuilder sb=new StringBuilder("SELECT st.gradu_status, cl.Grade, st.grad_dept, st.schl_name, st.depart_class, d.fname, cl.ClassName, st.student_no, st.student_name, st.idno, " +
    				"st.sex, st.birthday, st.entrance, c.name as team, e.permission_no, cl.DeptNo " +
    				"FROM Class cl LEFT OUTER JOIN dept d ON d.no=SUBSTRING(cl.ClassNo, -6, 4),(stmd st LEFT OUTER JOIN code5 c " +
    				"ON st.divi=c.idno AND c.category='Group') LEFT OUTER JOIN entrno e ON " +
    				"(st.student_no>=e.first_stno && st.student_no<=e.second_stno) WHERE st.occur_status='3' AND cl.ClassNo=st.depart_class AND " +
    				"st.occur_year='"+occur_year+"' AND st.occur_term='"+occur_term+"' AND " +
    				"st.student_no IN (");		
    		for(int i=0; i<students.size(); i++){
    			sb.append("'"+((Map)students.get(i)).get("student_no")+"', " );
    		}
    		sb.delete(sb.length()-2, sb.length());
    		sb.append(")");
    		
    		System.out.println(sb);
    		students=manager.ezGetBy(sb.toString());
    		
    		//左上學制名稱
    		if(students.size()>0){//有人畢業的話
    			
    			String year=(((Map)students.get(0)).get("depart_class").toString().substring(2, 3));
    			
    			
    			if(year.equals("2")){
    				year="二";
    			}
    			
    			if(year.equals("4")){
    				year="四";
    			}
    			
    			if(year.equals("G")){    				
    				header.setCenter(HSSFHeader.font("標楷體", "Normal")+
    		    	HSSFHeader.fontSize((short) 18)+schoolName+" "+schoolYear+"學年度第"+schoolTerm+title);
    		    	header.setLeft(manager.getClassInfo(((Map)students.get(0)).get("depart_class").toString(), 's'));
    			}else{
    				header.setCenter(HSSFHeader.font("標楷體", "Normal")+
    	    		HSSFHeader.fontSize((short) 18)+schoolName+year+"年制 "+schoolYear+"學年度第"+schoolTerm+title);
    	    		header.setLeft(manager.getClassInfo(((Map)students.get(0)).get("depart_class").toString(), 's'));
    			}
    			
    		}else{//沒人畢業
    			HSSFRichTextString c;
	            HSSFRow row = sheet.createRow((short) 0);
	            
				c=new HSSFRichTextString("這個查詢結果沒有人能畢業...囧");
				HSSFCell cell=row.createCell((short) 0);
				//cell.setCellStyle(contentStyle);
            	cell.setCellValue(c);
            	
            	
    		}
    		
    		//報表迴圈
            int tmp=0;
    		for(int i=0; i<students.size(); i++){
                //內容			
    			if(tmp%11==0|| tmp==1){
    				
    				HSSFRichTextString c;
    	            HSSFRow row = sheet.createRow((short) tmp);
    	            
    				c=new HSSFRichTextString("編號");
    				HSSFCell cell=row.createCell((short) 0);
    				cell.setCellStyle(contentStyle);
                	cell.setCellValue(c);
                	
                	c=new HSSFRichTextString("系(科)組");
                	cell=row.createCell((short) 1);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("姓名");
                	cell=row.createCell((short) 2);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("學號");
                	cell=row.createCell((short) 3);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("性別");
                	cell=row.createCell((short) 4);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("身分證字號");
                	cell=row.createCell((short) 5);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("生日日期");
                	cell=row.createCell((short) 6);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("入學年");
                	cell=row.createCell((short) 7);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("畢業學校名稱");
                	cell=row.createCell((short) 8);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString("畢業科系");
                	cell=row.createCell((short) 9);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
//                	c=new HSSFRichTextString("年級");
//                	cell=row.createCell((short) 10);
//                	cell.setCellValue(c);
//                	cell.setCellStyle(contentStyle);
                	
                	if(type.equals("ready")){//應屆才要簽名
                		c=new HSSFRichTextString("備註");
                    	cell=row.createCell((short) 10);
                    	cell.setCellValue(c);
                    	cell.setCellStyle(contentStyle);            		
                	}                	            	
                	tmp=tmp+1;
    			}
    			
    			HSSFRichTextString c;
                HSSFRow row = sheet.createRow((short) tmp);
    			
                c=new HSSFRichTextString(i+1+"");
            	HSSFCell cell=row.createCell((short) 0);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
                
            	c=new HSSFRichTextString( ((Map)students.get(i)).get("fname").toString());
            	cell=row.createCell((short) 1);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	c=new HSSFRichTextString( ((Map)students.get(i)).get("student_name").toString());
            	cell=row.createCell((short) 2);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);            	
            	
            	c=new HSSFRichTextString(((Map)students.get(i)).get("student_no").toString());
            	cell=row.createCell((short) 3);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	//c=new HSSFRichTextString(manager.convertDate(((Map)students.get(i)).get("birthday").toString()));
            	if(((Map)students.get(i)).get("sex").toString().equals("1")){
            		c=new HSSFRichTextString("男");
            	}else{
            		c=new HSSFRichTextString("女");
            	}
            	cell=row.createCell((short) 4);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	
            	c=new HSSFRichTextString(((Map)students.get(i)).get("idno").toString());
            	
            	//c=new HSSFRichTextString(fname.toString());
            	cell=row.createCell((short) 5);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	if(((Map)students.get(i)).get("birthday")!=null){
            		c=new HSSFRichTextString(manager.convertDate(((Map)students.get(i)).get("birthday").toString()));
            	}else{
            		c=new HSSFRichTextString("");
            	}
            	cell=row.createCell((short) 6);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	if(((Map)students.get(i)).get("entrance")!=null){//入學文號
            		c=new HSSFRichTextString( ((Map)students.get(i)).get("entrance").toString());
            	}else{
            		c=new HSSFRichTextString("");
            	}
            	cell=row.createCell((short) 7);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	if(((Map)students.get(i)).get("gradu_status").equals("1")){
            		graduate="畢";
            	}else{
            		graduate="肄";
            	}
            	
            	if(((Map)students.get(i)).get("schl_name")!=null){
            		c=new HSSFRichTextString(((Map)students.get(i)).get("schl_name").toString()+graduate);//畢業學校
            	}else{
            		c=new HSSFRichTextString("");//畢業學校
            	}
            	cell=row.createCell((short) 8);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);
            	
            	
            	if(((Map)students.get(i)).get("grad_dept")!=null){
            		c=new HSSFRichTextString( ((Map)students.get(i)).get("grad_dept").toString());//畢業號
            	}else{
            		c=new HSSFRichTextString();//畢業號
            	}            	
            	cell=row.createCell((short) 9);
            	cell.setCellValue(c);
            	cell.setCellStyle(contentStyle);        	
            	
//            	c=new HSSFRichTextString(((Map)students.get(i)).get("Grade").toString());//審查結果
//            	cell=row.createCell((short) 10);
//            	cell.setCellValue(c);
//            	cell.setCellStyle(contentStyle);
            	
            	if(type.equals("ready")){//應屆才要簽名
            		//c=new HSSFRichTextString(graduate);//審查結果
            		c=new HSSFRichTextString();//審查結果
                	cell=row.createCell((short) 10);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
            	}
            	
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
		
		outStream.flush(); 
		}
}
