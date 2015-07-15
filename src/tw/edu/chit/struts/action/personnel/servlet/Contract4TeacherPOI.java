package tw.edu.chit.struts.action.personnel.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 將word模版轉變換特定值後拋出
 * @author JOHN
 * TODO 解決office2000含以下無法正確解析xml格式文件
 * 
 */
public class Contract4TeacherPOI extends HttpServlet {
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		//CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		//HttpSession session = request.getSession(false);
		
		response.setContentType("application/vnd.ms-excel");
		
		
		
		
		
		
		ServletContext context = this.getServletContext();
		POIFSFileSystem fs =new POIFSFileSystem(new FileInputStream(context.getRealPath("/WEB-INF/reports/personnel/people.xls"))); 
		
		HSSFWorkbook wb = new HSSFWorkbook(fs);		
		//HSSFSheet sheet= wb.createSheet("new sheet");
		
		HSSFSheet sheet= wb.getSheetAt(0);
		HSSFRow row= sheet.createRow((short)0);
		
		
		HSSFCell cell= row.createCell((short)0);		
		cell.setCellValue(1);
		
		
		String[] A = new String[] { "#name#", "#cNo#", "#sy#", "#sm#" };
		String[] B = new String[] { "黃超彥", "123456", "97", "6" };

		
	
		setProcess(A,B,"/WEB-INF/reports/personnel/contract4teacher.xml",response,request);
		setProcess(A,B,"/WEB-INF/reports/personnel/contract4teacher.xml",response,request);
	

	}
	
	
	
	
	public synchronized void setProcess(String[] A, String[] B, String filename, HttpServletResponse response, HttpServletRequest request) {
	 
	    try {
	      /*
	       * 檢查傳入的A長度與B長度是否一致
	       */
	      if (A.length != B.length)
	        throw new ReplaceWordException();
	 
	      /*
	       * 設定成用msword打開
	       */
	      response.setContentType("application/msword");
	      response.setHeader("Content-disposition",
	          "attachment; filename=document.doc");
	 
	      /*
	       * 設定Servlet輸出
	       */
	      ServletOutputStream stream = response.getOutputStream();
	 
	      /*
	       * 讀取xml檔
	       */
	      ServletContext context = this.getServletContext();
	      File file = new File(context.getRealPath(filename));
	      BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
	 
	      /*
	       * 從串流中讀取248個位元組(byte)
	       */
	      byte[] buff = new byte[248];
	 
	      try {
	        String Variable = "";
	        while (in.read(buff, 0, buff.length) != -1) {
	          /*
	           * 把讀入的byte轉成UTF-8 再轉成String
	           */
	          Variable = Variable + new String(buff, "UTF-8");
	          
	          /*
	           * 把剛剛放在buff的內容清空
	           */
	          buff = new byte[248];
	        }
	 
	        /*
	         * 開始進行轉換...
	         */
	        for (int i = 0; i < A.length; i++) {
	          Variable = Variable.replaceAll(A[i], B[i]);
	        }
	        
	        /*
	         * 把轉換完的字串去掉前後空白(trim())再轉成UTF-8 bytes
	         */
	        byte[] tmp = Variable.trim().getBytes("UTF-8");
	 
	        /*
	         * 開始寫入檔案
	         */
	        stream.write(tmp);
	      } finally {
	        in.close();
	        stream.close();
	      }
	 
	    } catch (ReplaceWordException e1) {
	      System.out.println("傳入的A陣列或B陣列發生錯誤");
	    } catch (FileNotFoundException e2) {
	      e2.printStackTrace();
	    } catch (IOException e3) {
	      e3.printStackTrace();
	    } catch(Exception e){
	      e.printStackTrace();
	    }
	  }
	
	
	public class ReplaceWordException extends Exception {

	}
	
}
