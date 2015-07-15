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
 * 收/退費清單
 * @author JOHN
 *
 */
public class PayBack4Student extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);		
		ServletContext context = request.getSession().getServletContext();
		String schoolName=(String) context.getAttribute("SchoolName_ZH");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;Filename=PayBack4Student.xls");
		
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
        
        
        Integer schoolYear=manager.getSchoolYear();
        Integer schoolTerm=manager.getSchoolTerm();
        
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
        
        
        footer.setRight( "課程管理系統 "+sf.format(new Date()) );
        footer.setCenter("會計室:");
        footer.setLeft("                                     教務組:");
        
        
        //樣式設定
        HSSFCellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平置中
        //contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);//水平置中
        contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        
        contentStyle.setWrapText(false);//欄位視內容自動調整
        
        
        contentStyle.setWrapText(true);    
        
       
        
        HSSFFont contentFont = workbook.createFont();
        contentFont.setFontName("Arial");
        contentFont.setFontHeightInPoints((short)10);
        contentStyle.setFont(contentFont);
        
        HSSFFont font = wb.createFont();
        font.setColor(HSSFFont.SS_NONE);
        // font.setFontHeightInPoints((short)24);
        //font.setFontName("標楷體");
        // font.setItalic(true);
        // font.setStrikeout(true);//刪除線
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        contentStyle.setFont(font);
		       
        if(request.getParameter("type").equals("back")){
        	header.setCenter(HSSFHeader.font("標楷體", "Normal")+
        	HSSFHeader.fontSize((short) 12)+schoolName+" "+schoolYear+"學年度第"+schoolTerm+"學期\n學生網路選課時數統計表暨應退費用明細表");
        }else{
        	header.setCenter(HSSFHeader.font("標楷體", "Normal")+
        	HSSFHeader.fontSize((short) 12)+schoolName+" "+schoolYear+"學年度第"+schoolTerm+"學期\n學生網路選課時數統計表暨應收費用明細表");
        }
		//header.setLeft(manager.getClassInfo(((Map)students.get(0)).get("depart_class").toString(), 's'));
    	
    	//查核功具自帶資訊
    	int payMoney=Integer.parseInt(session.getAttribute("payMoney").toString());
    	int payHour=Integer.parseInt(session.getAttribute("payHour").toString());
    	int extraPayMoney=Integer.parseInt(session.getAttribute("extraPay").toString());
    	int insurance=Integer.parseInt(session.getAttribute("insurance").toString());

    	int myPayHour;
    	int myPayMoney;
    	int sumThour;
    	int sumElearning;
    	String Extrapay_Kind;
 	
    	int totalsum=0;
    	int totalPay=0;
    	int totalHour=0;
    	List allSelds=(List)session.getAttribute("allSelds");
    	boolean extrapay;
    	
    	int tmp=0;
    	
    	
    	
    	/**
    	 * 退費部份
    	 */
    	if(request.getParameter("type").equals("back")){//若有標明 back
    		
    		for(int i=0; i<allSelds.size(); i++){    		
        		sumThour=Integer.parseInt(((Map)allSelds.get(i)).get("sumThour").toString());
        		Extrapay_Kind=String.valueOf(((Map)allSelds.get(i)).get("Extrapay_Kind").toString());
        		if(payHour>sumThour||(payHour==sumThour&&Extrapay_Kind!="Y")){
        			
        			myPayHour=0;
            		myPayMoney=0;
            		if(tmp%26==0|| tmp==1){
            			
            			HSSFRichTextString c;
        	            HSSFRow row = sheet.createRow((short) tmp);
        	            row.setHeight((short) 550);
        	            
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
                    	
                    	c=new HSSFRichTextString("時數");
                    	cell=row.createCell((short) 3);
                    	cell.setCellValue(c);
                    	cell.setCellStyle(contentStyle);
                    	
                    	c=new HSSFRichTextString("電腦實習費");
                    	cell=row.createCell((short) 4);
                    	cell.setCellValue(c);
                    	cell.setCellStyle(contentStyle);
                    	
                    	c=new HSSFRichTextString("保險費");
                    	cell=row.createCell((short) 5);
                    	cell.setCellValue(c);
                    	cell.setCellStyle(contentStyle);
                    	
                    	c=new HSSFRichTextString("應退金額");
                    	cell=row.createCell((short) 6);
                    	cell.setCellValue(c);
                    	cell.setCellStyle(contentStyle);
                    	
                    	c=new HSSFRichTextString("簽領");
                    	cell=row.createCell((short) 7);
                    	cell.setCellValue(c);
                    	cell.setCellStyle(contentStyle);
                    	
                    	tmp=tmp+1;    			
            		}
            		
            		HSSFRichTextString c;
                    HSSFRow row = sheet.createRow((short) tmp);
                    row.setHeight((short) 550);
            		
            		c=new HSSFRichTextString(((Map)allSelds.get(i)).get("ClassName").toString());
            		HSSFCell cell=row.createCell((short) 0);
            		cell.setCellStyle(contentStyle);
                	cell.setCellValue(c);
                	
                	c=new HSSFRichTextString(((Map)allSelds.get(i)).get("student_no").toString());
                	cell=row.createCell((short) 1);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString(((Map)allSelds.get(i)).get("student_name").toString());
                	cell=row.createCell((short) 2);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	myPayHour=payHour-sumThour;
                	c=new HSSFRichTextString(myPayHour+"");
                	totalHour=totalHour+myPayHour;
                	cell=row.createCell((short) 3);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	myPayMoney=myPayHour*payMoney+insurance;
                	//先判斷實習費再用 實習費加學分費(退費明細)
                	if(extraPayMoney<0){		//不足學分及未收實習費
                		if(Extrapay_Kind=="Y"){	//有上電腦課，要減實習費
                			c=new HSSFRichTextString(extraPayMoney+"");
                			totalsum=myPayMoney-Math.abs(extraPayMoney);
                		}else{					//沒上電腦課，不收不退
                			c=new HSSFRichTextString(0+"");
                			totalsum=myPayMoney;
                		}
                	}else{						//不足學分及預收實習費
                		if(Extrapay_Kind=="Y"){	//有上電腦課不收不退
                			c=new HSSFRichTextString(0+"");
                			totalsum=myPayMoney;
                		}else{					//沒上電腦課加實習費
                			c=new HSSFRichTextString(extraPayMoney+"");
                			totalsum=myPayMoney+extraPayMoney;
                		}
                	}
                	
                	totalPay=totalPay+totalsum;      	
                	cell=row.createCell((short) 4);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString(insurance+"");
                	cell=row.createCell((short) 5);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString(totalsum+"");
                	cell=row.createCell((short) 6);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString();
                	cell=row.createCell((short) 7);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
            		
                	tmp=tmp+1;
                	
        			//換頁行數
                    if(tmp%26==0){
                        sheet.setRowBreak(tmp-1);
                    }
            		
            	}
        			
        	}
    	
    	/**
    	 * 收費部份
    	 */
    	}else{//若無標明pay back    		
    		
    		for(int i=0; i<allSelds.size(); i++){    		
        		sumThour=Integer.parseInt(((Map)allSelds.get(i)).get("sumThour").toString());
        		Extrapay_Kind=String.valueOf(((Map)allSelds.get(i)).get("Extrapay_Kind").toString());
        		if(payHour<sumThour||(payHour==sumThour&&Extrapay_Kind=="Y")){
        			
        			myPayHour=0;
            		myPayMoney=0;
            		if(tmp%26==0|| tmp==1){
            			
            			HSSFRichTextString c;
        	            HSSFRow row = sheet.createRow((short) tmp);
        	            row.setHeight((short) 550);
        	            
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
                    	
                    	c=new HSSFRichTextString("時數");
                    	cell=row.createCell((short) 3);
                    	cell.setCellValue(c);
                    	cell.setCellStyle(contentStyle);
                    	
                    	c=new HSSFRichTextString("電腦實習費");
                    	cell=row.createCell((short) 4);
                    	cell.setCellValue(c);
                    	cell.setCellStyle(contentStyle);
                    	
                    	c=new HSSFRichTextString("保險費");
                    	cell=row.createCell((short) 5);
                    	cell.setCellValue(c);
                    	cell.setCellStyle(contentStyle);
                    	
                    	c=new HSSFRichTextString("應收金額");
                    	cell=row.createCell((short) 6);
                    	cell.setCellValue(c);
                    	cell.setCellStyle(contentStyle);
                    	
                    	c=new HSSFRichTextString("簽領");
                    	cell=row.createCell((short) 7);
                    	cell.setCellValue(c);
                    	cell.setCellStyle(contentStyle);
                    	
                    	tmp=tmp+1;    			
            		}
            		
            		HSSFRichTextString c;
                    HSSFRow row = sheet.createRow((short) tmp);
                    row.setHeight((short) 550);
            		
            		c=new HSSFRichTextString(((Map)allSelds.get(i)).get("ClassName").toString());
            		HSSFCell cell=row.createCell((short) 0);
            		cell.setCellStyle(contentStyle);
                	cell.setCellValue(c);
                	
                	c=new HSSFRichTextString(((Map)allSelds.get(i)).get("student_no").toString());
                	cell=row.createCell((short) 1);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString(((Map)allSelds.get(i)).get("student_name").toString());
                	cell=row.createCell((short) 2);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);

                	myPayHour=sumThour-payHour;
                	c=new HSSFRichTextString(myPayHour+"");
                	totalHour=totalHour+myPayHour;
                	cell=row.createCell((short) 3);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	myPayMoney=myPayHour*payMoney+insurance;
                	//先判斷實習費再用 實習費加學分費(收費明細)
                	if(extraPayMoney<0){		//多學分及未收實習費
                		if(Extrapay_Kind=="Y"){	//有上電腦課，要加收實習費
                			c=new HSSFRichTextString(Math.abs(extraPayMoney)+"");
                			totalsum=myPayMoney+Math.abs(extraPayMoney);
                		}else{					//沒上電腦課，要不收不退
                			c=new HSSFRichTextString(0+"");
                			totalsum=myPayMoney;
                		}
                	}else{						//多學分及預收實習費
                		if(Extrapay_Kind=="Y"){	//有上電腦課，要不收不退
                			c=new HSSFRichTextString(0+"");
                			totalsum=myPayMoney;
                		}else{					//沒上電腦課，要減實習費
                			c=new HSSFRichTextString(-extraPayMoney+"");
                			totalsum=myPayMoney-extraPayMoney;
                		}
                	}
                	
                	totalPay=totalPay+totalsum;
                	cell=row.createCell((short) 4);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString(insurance+"");
                	cell=row.createCell((short) 5);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString(totalsum+"");
                	cell=row.createCell((short) 6);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
                	
                	c=new HSSFRichTextString();
                	cell=row.createCell((short) 7);
                	cell.setCellValue(c);
                	cell.setCellStyle(contentStyle);
            		
                	tmp=tmp+1;
                	
        			//換頁行數
                    if(tmp%26==0){
                        sheet.setRowBreak(tmp-1);
                    }
            		
            	}
        			
        	}
    			
    	}	
    		
    	HSSFRichTextString c;
        HSSFRow row = sheet.createRow((short) tmp);
        row.setHeight((short) 550);
        
        c=new HSSFRichTextString();
		HSSFCell cell=row.createCell((short) 0);
		cell.setCellStyle(contentStyle);
    	cell.setCellValue(c);      
    	
    	c=new HSSFRichTextString();
    	cell=row.createCell((short) 1);
    	cell.setCellValue(c);
    	cell.setCellStyle(contentStyle);
    	
    	c=new HSSFRichTextString("時數");
    	cell=row.createCell((short) 2);
    	cell.setCellValue(c);
    	cell.setCellStyle(contentStyle);
    	
    	c=new HSSFRichTextString(totalHour+"");
    	cell=row.createCell((short) 3);
    	cell.setCellValue(c);
    	cell.setCellStyle(contentStyle);
    	
    	c=new HSSFRichTextString();
    	cell=row.createCell((short) 4);
    	cell.setCellValue(c);
    	cell.setCellStyle(contentStyle);
    	
    	c=new HSSFRichTextString("總計金額");
    	cell=row.createCell((short) 5);
    	cell.setCellValue(c);
    	cell.setCellStyle(contentStyle);
    	
    	c=new HSSFRichTextString(totalPay+"");
    	cell=row.createCell((short) 6);
    	cell.setCellValue(c);
    	cell.setCellStyle(contentStyle);
    	
    	c=new HSSFRichTextString();
    	cell=row.createCell((short) 7);
    	cell.setCellValue(c);
    	cell.setCellStyle(contentStyle);
    	
    	/*創建斜線
    	HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
    	HSSFClientAnchor a = new HSSFClientAnchor(0, 0, 1023, 255, (short)0, 0, (short)0, 0);
    	HSSFSimpleShape shape1 = patriarch.createSimpleShape( a );
    	shape1.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE); 
    	*/	
    	
    	sheet.setColumnWidth((short)0, (short)4000);
		sheet.setColumnWidth((short)1, (short)4000);
		sheet.setColumnWidth((short)2, (short)3000);
		sheet.setColumnWidth((short)3, (short)2000);
		sheet.setColumnWidth((short)4, (short)3000);
		sheet.setColumnWidth((short)5, (short)3000);
		sheet.setColumnWidth((short)6, (short)3000);
		sheet.setColumnWidth((short)7, (short)5000);
    	
		
    	workbook.write(outStream);		
		outStream.flush(); 
		outStream.close();
	}

}
