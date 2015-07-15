package tw.edu.chit.struts.action.course.ajax;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.util.Toolket;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

/**
 * 產生pdf學生選課失敗簽收
 * @author JOHN
 *
 */
public class FilterAdvice1 extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);

		List selectFilterCouList=(List)session.getAttribute("selectFilterCouList");

		Date date=new Date();
		DateFormat dateFormat=new SimpleDateFormat("yyyy年 MM月 dd日 HH:mm");

		String stuTmp[] = new String[selectFilterCouList.size()];
		List students=new ArrayList();

		for(int i=0; i<selectFilterCouList.size(); i++){
			stuTmp[i]=((Map)selectFilterCouList.get(i)).get("dtimeOid").toString();
		}
		students=manager.getSeldStuFilterBy(stuTmp, true);

		Document document = new Document(PageSize.A4, 30,30,15,15);
		ByteArrayOutputStream ba = new ByteArrayOutputStream();

		String year=manager.getNowBy("School_year");
		String term=manager.getNowBy("School_term");
		
		//如果是下學期, 學年要+1
		if(Integer.parseInt(term)==2){
			Integer tmpYear=(Integer.parseInt(year)+1);
			year=tmpYear.toString();
			term="1";
		}else{
			term="2";
		}

		try	{

			PdfWriter writer = PdfWriter.getInstance(document, ba);
			//TODO 路徑
			//Image image=Image.getInstance("http://localhost/CIS/pages/images/2002chitWatermark.gif");
			//Image image=Image.getInstance("http://cap.chit.edu.tw/CIS/pages/images/2002chitWatermark.gif");
			//Watermark wamark=new Watermark(image, ((PageSize.A4).width()-image.plainHeight())/2, ((PageSize.A4).height()-image.plainHeight())/2);
			//document.add(wamark);
			Phrase now=doEncode("課程管理系統 "+dateFormat.format(date));
			HeaderFooter header=new HeaderFooter(now,false);
			document.setFooter(header);
			document.open();



			List departClass=manager.getFiltDep(selectFilterCouList);

			List tmpList;
			for(int i=0; i<departClass.size(); i++){
				tmpList=manager.getFiltStu(((Map)departClass.get(i)).get("depart_class").toString());
				
				if(tmpList.size()>0){
					
					
					Table table=new Table(6);
					table.setWidth(100);

					table.setBorderWidth(0);
					table.setPadding(5);
					//table.setDefaultCellBorderColor(new Color(255, 255, 255));

					Cell cell=new Cell();
					cell.add(doEncode(year));
					cell.add(doEncode("學年 第"));
					cell.add(doEncode(term));
					cell.add(doEncode("學期 網路預選失敗簽收單"));
					cell.setColspan(6);
					cell.setHorizontalAlignment(cell.ALIGN_CENTER);
					cell.setBorderColor(new Color(255, 255, 255));
					table.addCell(cell);

					document.add(table);
					document.add(new Paragraph(doEncode(((Map)departClass.get(i)).get("ClassName").toString())));
					document.add(new Paragraph("\n"));

					PdfPCell cells = new PdfPCell();
					//PdfPTable tables = new PdfPTable(6);
					float[] widths = {0.15f, 0.15f, 0.4f, 0.1f, 0.2f};
					PdfPTable tables = new PdfPTable(widths);
					tables.setWidthPercentage(100);

					    cells = new PdfPCell(doEncode("學號"));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode("姓名"));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode("課程名稱"));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode("原因"));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode("簽收"));
					    tables.addCell(cells);
					
					for(int j=0; j<tmpList.size(); j++){
						cells = new PdfPCell(doEncode(((Map)tmpList.get(j)).get("student_no").toString()));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode(((Map)tmpList.get(j)).get("student_name").toString()));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode(((Map)tmpList.get(j)).get("chi_name").toString()));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode(getFlt(((Map)tmpList.get(j)).get("type").toString())));
					    tables.addCell(cells);
					    cells = new PdfPCell(doEncode("\t"));
					    tables.addCell(cells);
					}


					 for(int k=45; k>tmpList.size(); k--){
					    	cells = new PdfPCell(doEncode("\t"));
						    tables.addCell(cells);
						    cells = new PdfPCell(doEncode("\t"));
						    tables.addCell(cells);
						    cells = new PdfPCell(doEncode("\t"));
						    tables.addCell(cells);
						    cells = new PdfPCell(doEncode("\t"));
						    tables.addCell(cells);
						    cells = new PdfPCell(doEncode("\t"));
						    tables.addCell(cells);
						}
					document.add(tables);
					document.newPage();					
				}
			}
			}catch(DocumentException de){
			de.printStackTrace();
			System.err.println("A Document error:" +de.getMessage());
		}
		document.close();
		response.setContentType("application/pdf");
		response.setContentLength(ba.size());
		ServletOutputStream out = response.getOutputStream();
		ba.writeTo(out);
		ba.close();
		out.flush();
		out.close();
	}

	private Phrase doEncode(String nowBy) {

		BaseFont bf;
		Font font = null;
		Phrase p = null;
		try {

			bf = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
			font = new Font(bf, 10 ,0);
			p=new Phrase(nowBy, font);

		}catch (Exception e) {

			e.printStackTrace();
		}
		return p;
	}

	private String getOpt(String opt){
		if(opt.trim().equals("1")){
			return "必修";
		}
		if(opt.trim().equals("2")){
			return "選修";
		}
		if(opt.trim().equals("3")){
			return "通識";
		}
		return "這是啥？";
	}

	private String getFlt(String flt){
		if(flt.trim().equals("F")){
			return "篩選";
		}
		if(flt.trim().equals("D")){
			return "刪除";
		}
		if(flt.trim().equals("O")){
			return "其它";
		}
		return "這是啥？";
	}


}
