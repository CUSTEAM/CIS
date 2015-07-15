package tw.edu.chit.struts.action.course.checkOut;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * 報表2
 * @author John
 *
 */
public class ListCounsling {
	
	public void print(HttpServletResponse response, List<Map>list, String year, String term) throws IOException{
		
		Date date=new Date();
		
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename="+date.getTime()+".xls");			
		
		PrintWriter out=response.getWriter();
		out.println ("<?xml version='1.0'?>");
		out.println ("<?mso-application progid='Excel.Sheet'?>");
		out.println ("<Workbook xmlns='urn:schemas-microsoft-com:office:spreadsheet'");
		out.println (" xmlns:o='urn:schemas-microsoft-com:office:office'");
		out.println (" xmlns:x='urn:schemas-microsoft-com:office:excel'");
		out.println (" xmlns:ss='urn:schemas-microsoft-com:office:spreadsheet'");
		out.println (" xmlns:html='http://www.w3.org/TR/REC-html40'>");
		out.println (" <DocumentProperties xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <Author>John</Author>");
		out.println ("  <LastAuthor>John</LastAuthor>");
		out.println ("  <LastPrinted>2012-12-21T02:00:00Z</LastPrinted>");
		out.println ("  <Created>2012-12-21T00:00:00Z</Created>");
		out.println ("  <LastSaved>2012-12-21T00:00:00Z</LastSaved>");
		out.println ("  <Version>14.00</Version>");
		out.println (" </DocumentProperties>");
		out.println (" <OfficeDocumentSettings xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <AllowPNG/>");
		out.println (" </OfficeDocumentSettings>");
		out.println (" <ExcelWorkbook xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("  <WindowHeight>11880</WindowHeight>");
		out.println ("  <WindowWidth>28035</WindowWidth>");
		out.println ("  <WindowTopX>360</WindowTopX>");
		out.println ("  <WindowTopY>105</WindowTopY>");
		out.println ("  <ProtectStructure>False</ProtectStructure>");
		out.println ("  <ProtectWindows>False</ProtectWindows>");
		out.println (" </ExcelWorkbook>");
		out.println (" <Styles>");
		out.println ("  <Style ss:ID='Default' ss:Name='Normal'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders/>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <Interior/>");
		out.println ("   <NumberFormat/>");
		out.println ("   <Protection/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s76'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s78'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders/>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		
		
		
		out.println ("	<Style ss:ID='s63'>");
		out.println ("   <Alignment ss:Horizontal='Right' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		
		out.println (" </Styles>");			
			
		out.println (" <Worksheet ss:Name='課程匯總'>");			
		out.println ("  <Table ss:ExpandedColumnCount='11' ss:ExpandedRowCount='"+list.size()+100+"' x:FullColumns='1'");		
		
		out.println ("   x:FullRows='1' ss:StyleID='s78' ss:DefaultColumnWidth='20'");
		out.println ("   ss:DefaultRowHeight='25.5'>");
		
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='160'/>");		
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");	
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='100'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='100'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		
		out.println ("   <Row>");		
		out.println ("    <Cell><Data ss:Type='String'>開課班級</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>課程名稱</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>教師</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>選課</Data></Cell>");	
		out.println ("    <Cell><Data ss:Type='String'>期中平均</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>不及格</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>期末平均</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>不及格</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>輔導次數</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>平均次數</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>成功率</Data></Cell>");
		out.println ("   </Row>");			
		
		
		List<Map>care, careinfo;
		int total, cnt;
		StringBuilder sb;
		String s;
		for(int i=0; i<list.size(); i++){	
			total=Integer.parseInt(list.get(i).get("total").toString());
			cnt=Integer.parseInt(list.get(i).get("cnt").toString());
			out.println ("   <Row>");		
			//out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("ClassName")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("ClassName")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("chi_name")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("cname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+total+"</Data></Cell>");	
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("score2")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("nopas")+"</Data></Cell>");
			
			if(list.get(i).get("score")==null){
				out.println ("    <Cell><Data ss:Type='String'></Data></Cell>");
			}else{
				out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("score")+"</Data></Cell>");
			}
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("nopas2")+"</Data></Cell>");
			
			//care=((List)list.get(i).get("care"));
			//out.println ("    <Cell><Data ss:Type='String'>"+care.size()+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+cnt+"</Data></Cell>");
			
			s=String.valueOf((cnt/Float.parseFloat(list.get(i).get("nopas").toString())));
			try{
				out.println ("    <Cell><Data ss:Type='String'>"+s.substring(0, s.indexOf(".")+3)+"</Data></Cell>");
			}catch(Exception e){
				out.println ("    <Cell><Data ss:Type='String'>"+s+"</Data></Cell>");
			}
			
			try{
				s=String.valueOf(   Float.parseFloat(list.get(i).get("nopas2").toString())/Float.parseFloat(list.get(i).get("nopas").toString())   );
				out.println ("    <Cell><Data ss:Type='String'>"+s.substring(0, s.indexOf(".")+3)+"</Data></Cell>");
			}catch(Exception e){
				out.println ("    <Cell><Data ss:Type='String'>-</Data></Cell>");
			}
			
			out.println ("   </Row>");
		}
		
		out.println ("  </Table>");			
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");			
		out.println ("    <Header x:Margin='0.3' x:Data='&amp;C&amp;&quot;-,粗體&quot;&amp;18 "+year+"學年 "+term+"學期  期中不及格輔導明細'/>");
		out.println ("    <Footer x:Margin='0.3' x:Data='&amp;R&amp;10&amp;D - &amp;T&#10;第&amp;P頁 共&amp;N頁'/>");
		out.println ("    <PageMargins x:Bottom='0.75' x:Left='0.7' x:Right='0.7' x:Top='0.75'/>");
		out.println ("   </PageSetup>");
		out.println ("   <FitToPage/>");
		out.println ("   <Print>");
		out.println ("    <FitHeight>0</FitHeight>");
		out.println ("    <ValidPrinterInfo/>");
		out.println ("    <PaperSizeIndex>9</PaperSizeIndex>");
		out.println ("    <Scale>52</Scale>");
		out.println ("    <HorizontalResolution>600</HorizontalResolution>");
		out.println ("    <VerticalResolution>600</VerticalResolution>");
		out.println ("   </Print>");
		out.println ("   <Selected/>");
		out.println ("   <Panes>");
		out.println ("    <Pane>");
		out.println ("     <Number>3</Number>");
		out.println ("     <ActiveRow>16</ActiveRow>");
		out.println ("     <ActiveCol>7</ActiveCol>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		
		//個人明細
		total=0;
		for(int i=0; i<list.size(); i++){
			total=total+((List)list.get(i).get("care")).size();
		}
		out.println (" <Worksheet ss:Name='輔導明細'>");			
		out.println ("  <Table ss:ExpandedColumnCount='9' ss:ExpandedRowCount='"+total+100+"' x:FullColumns='1'");		
		
		out.println ("   x:FullRows='1' ss:StyleID='s78' ss:DefaultColumnWidth='20'");
		out.println ("   ss:DefaultRowHeight='25.5'>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='100'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");		
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");
		
		out.println ("   <Row>");		
		out.println ("    <Cell><Data ss:Type='String'>課程名稱</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>授課教師</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>班級</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>學號</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>姓名</Data></Cell>");		
		out.println ("    <Cell><Data ss:Type='String'>期中成績</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>期末成績</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>輔導次數</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>輔導內容</Data></Cell>");
		out.println ("   </Row>");
		String str;
		for(int i=0; i<list.size(); i++){
			care=((List)list.get(i).get("care"));
			
			for(int j=0; j<care.size(); j++){
				careinfo=(List)care.get(j).get("care");
				out.println ("   <Row>");
				out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("chi_name")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("cname")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+care.get(j).get("ClassName")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+care.get(j).get("student_no")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+care.get(j).get("student_name")+"</Data></Cell>");		
				out.println ("    <Cell><Data ss:Type='String'>"+care.get(j).get("score2")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+care.get(j).get("score")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+care.get(j).get("cnt")+"</Data></Cell>");
				sb=new StringBuilder();
				for(int k=0; k<careinfo.size(); k++){
					
					str=replaceChar4XML(String.valueOf(careinfo.get(k).get("content")));
					sb.append(careinfo.get(k).get("cname")+"於"+careinfo.get(k).get("mdate")+":"+str+", ");
					/*
					 * replaceChar4XML(String str)
					sb.append(careinfo.get(k).get("cname")+"於"+careinfo.get(k).get("mdate")+":"+careinfo.get(k).get("content")+", ");
					*/
				}
				
				out.println ("    <Cell><Data ss:Type='String'>"+sb+"</Data></Cell>");
				out.println ("   </Row>");				
			}			
		}
		
		out.println ("  </Table>");			
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");			
		out.println ("    <Header x:Margin='0.3' x:Data='&amp;C&amp;&quot;-,粗體&quot;&amp;18 "+year+"學年 "+term+"學期  1/2不合格學生名單'/>");
		out.println ("    <Footer x:Margin='0.3' x:Data='&amp;R&amp;10&amp;D - &amp;T&#10;第&amp;P頁 共&amp;N頁'/>");
		out.println ("    <PageMargins x:Bottom='0.75' x:Left='0.7' x:Right='0.7' x:Top='0.75'/>");
		out.println ("   </PageSetup>");
		out.println ("   <FitToPage/>");
		out.println ("   <Print>");
		out.println ("    <FitHeight>0</FitHeight>");
		out.println ("    <ValidPrinterInfo/>");
		out.println ("    <PaperSizeIndex>9</PaperSizeIndex>");
		out.println ("    <Scale>52</Scale>");
		out.println ("    <HorizontalResolution>600</HorizontalResolution>");
		out.println ("    <VerticalResolution>600</VerticalResolution>");
		out.println ("   </Print>");
		out.println ("   <Selected/>");
		out.println ("   <Panes>");
		out.println ("    <Pane>");
		out.println ("     <Number>3</Number>");
		out.println ("     <ActiveRow>16</ActiveRow>");
		out.println ("     <ActiveCol>7</ActiveCol>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		
		out.println ("</Workbook>");	
		out.close();
		out.flush();
		
	}
	
	private String replaceChar4XML(String str){
		try{
			str=str.replaceAll("&", "&amp;");
			str=str.replaceAll("<", "&lt;");
			str=str.replaceAll(">", "&gt;");
			str=str.replaceAll("\"", "&quot;");
			str=str.replaceAll("", "\\n");
			
			//str=str.replaceAll(",", "&cedil;");
			str=str.replaceAll("'", "&apos;");
		}catch(NullPointerException e){
			return str;
		}
		
		return str;
	}

}