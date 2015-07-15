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
 * 產生pdf學生選課失敗通知
 * @author JOHN
 *
 */
public class FilterAdvice extends HttpServlet{
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

		Document document = new Document(PageSize.A4, 72,72,35,35);
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
		
		//Image image=Image.getInstance("http://cap.chit.edu.tw/CIS/pages/images/2002chitWatermark.gif");
		//Watermark wamark=new Watermark(image, ((PageSize.A4).width()-image.plainHeight())/2, ((PageSize.A4).height()-image.plainHeight())/2);
		//document.add(wamark);
		
		Phrase now=doEncode("課程管理系統 "+dateFormat.format(date));
		HeaderFooter header=new HeaderFooter(now,false);
		document.setHeader(header);
		document.setFooter(header);

		//Image logo=Image.getInstance("http://192.192.231.32:8080/CIS/pages/images/art.gif");
		document.open();


		for(int i=0; i<students.size(); i++){

			String buf[]={((Map)students.get(i)).get("student_no").toString()};
			List tmp=manager.getSeldStuFilterBy(buf, false);


			if(i%2!=0){
				document.add(new Paragraph(doEncode("\n")));
				document.add(new Paragraph(doEncode("\n")));
				document.add(new Paragraph(doEncode("\n")));
			}

			document.add(new Paragraph(""));

			Table table=new Table(6);
			table.setWidth(100);

			table.setBorderWidth(0);
			table.setPadding(5);
			//table.setSpacing(5);
			//table.setDefaultHorizontalAlignment(table.ALIGN_CENTER);
			//table.setDefaultHorizontalAlignment(table.ALIGN_MIDDLE);

			//table.setDefaultCellBorderColor(new Color(255, 255, 255));

			Cell cell=new Cell();
			cell.add(doEncode(year));
			cell.add(doEncode("學年 第"));
			cell.add(doEncode(term));
			cell.add(doEncode("學期 網路預選失敗通知單"));
			//cell.addElement(logo);
			cell.setColspan(6);
			cell.setHorizontalAlignment(cell.ALIGN_CENTER);
			cell.setBorderColor(new Color(255, 255, 255));
			table.addCell(cell);

			//((Map)students.get(i)).get("ClassName2");

			cell=new Cell();
			//cell.add(doEncode("學生班級: "));
			cell.add(doEncode(((Map)students.get(i)).get("ClassName").toString()));
			cell.add(doEncode("\t學號: "));
			cell.add(doEncode(((Map)students.get(i)).get("student_no").toString()));
			cell.add(doEncode("\t姓名: "));
			cell.add(doEncode(((Map)students.get(i)).get("student_name").toString()));

			cell.setColspan(6);
			cell.setHorizontalAlignment(cell.ALIGN_LEFT);
			cell.setBorderColor(new Color(255, 255, 255));
			table.addCell(cell);

			cell=new Cell();
			cell.add(doEncode("您在第一階段網路選課所選的下列課程並未成功，請按照規定時間進行網路第二階段選課，以免影響您的權益。"));

			cell.setColspan(6);
			cell.setHorizontalAlignment(cell.ALIGN_LEFT);
			cell.setBorderColor(new Color(255, 255, 255));
			table.addCell(cell);

			document.add(table);

			float[] widths = {0.2f, 0.3f, 0.1f, 0.1f, 0.1f, 0.2f};
			PdfPTable tables = new PdfPTable(widths);
			tables.setWidthPercentage(100);

			PdfPCell cells = new PdfPCell(doEncode("開課班級"));
		    tables.addCell(cells);

		    cells = new PdfPCell(doEncode("科目名稱"));
		    tables.addCell(cells);
		    cells = new PdfPCell(doEncode("選別"));
		    tables.addCell(cells);
		    cells = new PdfPCell(doEncode("學分"));
		    tables.addCell(cells);
		    cells = new PdfPCell(doEncode("時數"));
		    tables.addCell(cells);
		    cells = new PdfPCell(doEncode("上課時間"));
		    tables.addCell(cells);

			for(int j=0; j<tmp.size(); j++){
				cells = new PdfPCell();
				cells = new PdfPCell(doEncode(((Map)tmp.get(j)).get("ClassName2").toString()));
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncode(((Map)tmp.get(j)).get("chi_name").toString()));
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncode(getOpt(((Map)tmp.get(j)).get("opt").toString())));
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncode(((Map)tmp.get(j)).get("credit").toString()));
			    tables.addCell(cells);
			    cells = new PdfPCell(doEncode(((Map)tmp.get(j)).get("thour").toString()));
			    tables.addCell(cells);

			    List dcTmp=manager.getDtimeClassListForOpenCourse(((Map)tmp.get(j)).get("dtimeOid").toString());

			    StringBuffer strBuf=new StringBuffer();
			    DtimeClass dtimeClass;
			    for(int x=0; x<dcTmp.size(); x++){
			    	dtimeClass=(DtimeClass) dcTmp.get(x);
			    	strBuf.append("週"+dtimeClass.getWeek()+":");
			    	strBuf.append(dtimeClass.getBegin()+"~"+dtimeClass.getEnd()+" ");
			    }

			    cells = new PdfPCell(doEncode(strBuf.toString()));
			    tables.addCell(cells);
			}

			 for(int k=13; k>tmp.size(); k--){
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
				    cells = new PdfPCell(doEncode("\t"));
				    tables.addCell(cells);
				}

			document.add(tables);

			if(i%2==1){
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


}
