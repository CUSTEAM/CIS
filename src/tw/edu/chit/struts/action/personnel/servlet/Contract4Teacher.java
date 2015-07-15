package tw.edu.chit.struts.action.personnel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

/**
 * 聘書
 * 
 * @author JOHN
 * 
 */
public class Contract4Teacher extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);

		String year = manager.getNowBy("School_year");// 現在學年
		String term = manager.getSchoolTerm().toString();// 現在學期
		ServletContext context = request.getSession().getServletContext();
		String schoolName = (String) context.getAttribute("SchoolName_ZH");
		response.setContentType("application/vnd.ms-word; charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=list4CheckCredit.doc");
		PrintWriter out = response.getWriter();

		List list = (List) session.getAttribute("oldContract");

		out.println("<html xmlns:v='urn:schemas-microsoft-com:vml'");
		out.println("xmlns:o='urn:schemas-microsoft-com:office:office'");
		out.println("xmlns:w='urn:schemas-microsoft-com:office:word'");
		out.println("xmlns='http://www.w3.org/TR/REC-html40'>");
		out.println("");
		out.println("<head>");
		out
				.println("<meta http-equiv=Content-Type content='text/html; charset=UTF-8'>");
		out.println("<meta name=ProgId content=Word.Document>");
		out.println("<meta name=Generator content='Microsoft Word 9'>");
		out.println("<meta name=Originator content='Microsoft Word 9'>");
		out
				.println("<link rel=File-List href='./contract.files/filelist.xml'>");
		out
				.println("<link rel=Edit-Time-Data href='./contract.files/editdata.mso'>");
		out.println("<!--[if !mso]>");
		out.println("<style>");
		out.println("v\\:* {behavior:url(#default#VML);}");
		out.println("o\\:* {behavior:url(#default#VML);}");
		out.println("w\\:* {behavior:url(#default#VML);}");
		out.println(".shape {behavior:url(#default#VML);}");
		out.println("</style>");
		out.println("<![endif]-->");
		out
				.println("<title>"
						+ schoolName
						+ "聘書存根                                                              ");
		out.println("             </title>");
		out.println("<!--[if gte mso 9]><xml>");
		out.println(" <o:DocumentProperties>");
		out.println("  <o:Author>PEOPLE1</o:Author>");
		out.println("  <o:LastAuthor>JOHN</o:LastAuthor>");
		out.println("  <o:Revision>2</o:Revision>");
		out.println("  <o:TotalTime>4</o:TotalTime>");
		out.println("  <o:LastPrinted>2006-07-26T06:22:00Z</o:LastPrinted>");
		out.println("  <o:Created>2008-05-25T14:12:00Z</o:Created>");
		out.println("  <o:LastSaved>2008-05-25T14:12:00Z</o:LastSaved>");
		out.println("  <o:Pages>1</o:Pages>");
		out.println("  <o:Words>63</o:Words>");
		out.println("  <o:Characters>363</o:Characters>");
		out.println("  <o:Company>CITC</o:Company>");
		out.println("  <o:Bytes>17920</o:Bytes>");
		out.println("  <o:Lines>3</o:Lines>");
		out.println("  <o:Paragraphs>1</o:Paragraphs>");
		out.println("  <o:CharactersWithSpaces>445</o:CharactersWithSpaces>");
		out.println("  <o:Version>9.2812</o:Version>");
		out.println(" </o:DocumentProperties>");
		out.println("</xml><![endif]--><!--[if gte mso 9]><xml>");
		out.println(" <w:WordDocument>");
		out.println("  <w:PunctuationKerning/>");
		out
				.println("  <w:DrawingGridHorizontalSpacing>6 pt</w:DrawingGridHorizontalSpacing>");
		out
				.println("  <w:DrawingGridVerticalSpacing>8.15 pt</w:DrawingGridVerticalSpacing>");
		out
				.println("  <w:DisplayHorizontalDrawingGridEvery>0</w:DisplayHorizontalDrawingGridEvery>");
		out
				.println("  <w:DisplayVerticalDrawingGridEvery>2</w:DisplayVerticalDrawingGridEvery>");
		out.println("  <w:Compatibility>");
		out.println("   <w:SpaceForUL/>");
		out.println("   <w:BalanceSingleByteDoubleByteWidth/>");
		out.println("   <w:DoNotLeaveBackslashAlone/>");
		out.println("   <w:ULTrailSpace/>");
		out.println("   <w:DoNotExpandShiftReturn/>");
		out.println("   <w:UsePrinterMetrics/>");
		out.println("   <w:WW6BorderRules/>");
		out.println("   <w:FootnoteLayoutLikeWW8/>");
		out.println("   <w:ShapeLayoutLikeWW8/>");
		out.println("   <w:AlignTablesRowByRow/>");
		out.println("   <w:ForgetLastTabAlignment/>");
		out.println("   <w:LayoutRawTableWidth/>");
		out.println("   <w:LayoutTableRowsApart/>");
		out.println("   <w:UseFELayout/>");
		out.println("  </w:Compatibility>");
		out
				.println("  <w:NoLineBreaksAfter Lang='ZH-TW'>([{‘“‵〈《「『【〔〝︵︷︹︻︽︿﹁﹃﹙﹛﹝（｛</w:NoLineBreaksAfter>");
		out
				.println("  <w:NoLineBreaksBefore Lang='ZH-TW'>!),.:;?]}·–—’”‥…‧′╴、。〉》」』】〕〞︰︱︳︴︶︸︺︼︾﹀﹂﹄﹏﹐﹑﹒﹔﹕﹖﹗﹚﹜﹞！），．：；？｜｝</w:NoLineBreaksBefore>");
		out.println(" </w:WordDocument>");
		out.println("</xml><![endif]-->");
		out.println("<style>");
		out.println("<!--");
		out.println(" /* Font Definitions */");
		out.println("@font-face");
		out.println("	{font-family:新細明體;");
		out.println("	panose-1:2 2 3 0 0 0 0 0 0 0;");
		out.println("	mso-font-alt:PMingLiU;");
		out.println("	mso-font-charset:136;");
		out.println("	mso-generic-font-family:roman;");
		out.println("	mso-font-pitch:variable;");
		out.println("	mso-font-signature:3 137232384 22 0 1048577 0;}");
		out.println("@font-face");
		out.println("	{font-family:標楷體;");
		out.println("	panose-1:3 0 5 9 0 0 0 0 0 0;");
		out.println("	mso-font-charset:136;");
		out.println("	mso-generic-font-family:script;");
		out.println("	mso-font-pitch:fixed;");
		out.println("	mso-font-signature:3 137232384 22 0 1048577 0;}");
		/*
		 * out.println("@font-face"); out.println(" {font-family:'\\@新細明體';");
		 * out.println(" panose-1:2 2 3 0 0 0 0 0 0 0;"); out.println("
		 * mso-font-charset:136;"); out.println("
		 * mso-generic-font-family:roman;"); out.println("
		 * mso-font-pitch:variable;"); out.println(" mso-font-signature:3
		 * 137232384 22 0 1048577 0;}"); out.println("@font-face");
		 * out.println(" {font-family:'\\@標楷體';"); out.println(" panose-1:3 0 5
		 * 9 0 0 0 0 0 0;"); out.println(" mso-font-charset:136;");
		 * out.println(" mso-generic-font-family:script;"); out.println("
		 * mso-font-pitch:fixed;"); out.println(" mso-font-signature:3 137232384
		 * 22 0 1048577 0;}");
		 */
		out.println(" /* Style Definitions */");
		out.println("p.MsoNormal, li.MsoNormal, div.MsoNormal");
		out.println("	{mso-style-parent:'';");
		out.println("	margin:0cm;");
		out.println("	margin-bottom:.0001pt;");
		out.println("	line-height:18.0pt;");
		out.println("	mso-pagination:none;");
		out.println("	mso-layout-grid-align:none;");
		out.println("	font-size:12.0pt;");
		out.println("	mso-bidi-font-size:10.0pt;");
		out.println("	font-family:'Times New Roman';");
		out.println("	mso-fareast-font-family:新細明體;}");
		out.println("p.MsoDate, li.MsoDate, div.MsoDate");
		out.println("	{mso-style-next:內文;");
		out.println("	margin:0cm;");
		out.println("	margin-bottom:.0001pt;");
		out.println("	text-align:right;");
		out.println("	line-height:18.0pt;");
		out.println("	mso-pagination:none;");
		out.println("	mso-layout-grid-align:none;");
		out.println("	font-size:12.0pt;");
		out.println("	mso-bidi-font-size:10.0pt;");
		out.println("	font-family:'Times New Roman';");
		out.println("	mso-fareast-font-family:新細明體;}");
		out.println(" /* Page Definitions */");
		out.println("@page");
		out.println("	{mso-page-border-surround-header:no;");
		out.println("	mso-page-border-surround-footer:no;");
		out.println("	mso-gutter-position:top;}");
		out.println("@page Section1");
		out.println("	{size:773.95pt 1122.65pt;");
		out.println("	margin:42.55pt 42.55pt 42.55pt 42.55pt;");
		out.println("	mso-header-margin:14.2pt;");
		out.println("	mso-footer-margin:2.85pt;");
		out.println("	mso-paper-source:0;}");
		out.println("div.Section1");
		out.println("	{page:Section1;}");
		out.println("-->");
		out.println("</style>");
		out.println("<!--[if gte mso 9]><xml>");
		out.println(" <o:shapedefaults v:ext='edit' spidmax='2050'/>");
		out.println("</xml><![endif]--><!--[if gte mso 9]><xml>");
		out.println(" <o:shapelayout v:ext='edit'>");
		out.println("  <o:idmap v:ext='edit' data='1'/>");
		out.println(" </o:shapelayout></xml><![endif]-->");
		out.println("</head>");
		out.println("");
		out
				.println("<body lang=ZH-TW style='tab-interval:24.0pt;text-justify-trim:punctuation'>");
		out.println("<div class=Section1>");

		for (int i = 0; i < list.size(); i++) {

			out
					.println("<table border=0 cellspacing=0 cellpadding=0 width=872 style='width:654.0pt;");
			out
					.println(" margin-left:31.4pt;border-collapse:collapse;mso-padding-alt:0cm 1.4pt 0cm 1.4pt'>");
			out.println(" <tr style='height:43.1pt'>");
			out
					.println("  <td width=872 valign=bottom style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;");
			out.println("  height:43.1pt'>");
			out
					.println("  <p class=MsoNormal style='text-indent:24.0pt;mso-char-indent-count:2.0;");
			out
					.println("  mso-char-indent-size:12.0pt;line-height:35.0pt;mso-line-height-rule:exactly'><!--[if gte vml 1]><v:shapetype");
			out
					.println("   id='_x0000_t202' coordsize='21600,21600' o:spt='202' path='m0,0l0,21600,21600,21600,21600,0xe'>");
			out.println("   <v:stroke joinstyle='miter'/>");
			out
					.println("   <v:path gradientshapeok='t' o:connecttype='rect'/>");
			out
					.println("  </v:shapetype><v:shape id='_x0000_s1032' type='#_x0000_t202' style='position:absolute;");
			out
					.println("   margin-left:533.5pt;margin-top:7.2pt;width:125.1pt;height:40.75pt;z-index:1'");
			out.println("   strokecolor='white'>");
			out
					.println("   <v:textbox style='mso-next-textbox:#_x0000_s1032'>");
			out.println("    <![if !mso]>");
			out.println("    <table cellpadding=0 cellspacing=0 width='100%'>");
			out.println("     <tr>");
			out.println("      <td nowrap><![endif]>");
			out.println("      <div>");
			out
					.println("      <p class=MsoNormal align=center style='text-align:center;line-height:");
			out
					.println("      15.0pt;mso-line-height-rule:exactly'><span lang=EN-US style='font-size:");
			out
					.println("      10.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''>96 <span");
			out
					.println("      style='mso-spacerun: yes'>&nbsp;</span>年<span style='mso-spacerun:");
			out
					.println("      yes'>&nbsp; </span><span style='mso-spacerun: yes'>&nbsp;</span>6<span");
			out
					.println("      style='mso-spacerun: yes'>&nbsp; </span>月 <o:p></o:p></span></p>");
			out
					.println("      <p class=MsoNormal align=center style='text-align:center;line-height:");
			out
					.println("      15.0pt;mso-line-height-rule:exactly'><span style='font-size:10.0pt;");
			out
					.println("      font-family:標楷體;mso-hansi-font-family:'Times New Roman''><span");
			out
					.println("      lang=EN-US>${o.contract_no}號<o:p></o:p></span></span></p>");
			out.println("      </div>");
			out.println("      <![if !mso]></td>");
			out.println("     </tr>");
			out.println("    </table>");
			out.println("    <![endif]></v:textbox>");
			out.println("   <w:wrap type='square'/>");
			out
					.println("  </v:shape><![endif]--><![if !vml]><img width=170 height=57");
			out
					.println("  src='./contract.files/image001.gif' align=left hspace=12");
			out
					.println("  alt='文字方塊: 96  年   6  月 &#13;&#10;中華聘字第951378號&#13;&#10;' v:shapes='_x0000_s1032'><![endif]><span");
			out
					.println("  style='font-size:24.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''>"
							+ schoolName + "聘書存根</span><span");
			out
					.println("  style='font-size:10.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''>");
			out.println("  <span lang=EN-US><span style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><o:p></o:p></span></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:30.9pt'>");
			out
					.println("  <td width=872 style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;height:30.9pt'>");
			out
					.println("  <p class=MsoNormal style='margin-top:.5gd;line-height:20.0pt;mso-line-height-rule:");
			out
					.println("  exactly'><span lang=EN-US style='font-family:標楷體;mso-hansi-font-family:'Times New Roman''><span");
			out.println("  style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out
					.println("  </span></span><span lang=EN-US style='font-size:20.0pt;font-family:標楷體;");
			out
					.println("  mso-hansi-font-family:'Times New Roman''><span style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;</span></span><span style='font-size:18.0pt;font-family:標楷體;");
			out
					.println("  mso-hansi-font-family:'Times New Roman''>茲<span lang=EN-US><span");
			out
					.println("  style='mso-spacerun: yes'>&nbsp; </span>聘<span style='mso-spacerun:");
			out.println("  yes'>&nbsp; </span>請<o:p></o:p></span></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:32.0pt'>");
			out
					.println("  <td width=872 style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;height:32.0pt'>");
			out
					.println("  <p class=MsoNormal style='margin-top:4.0pt;text-indent:18.0pt;mso-char-indent-count:");
			out
					.println("  1.0;mso-char-indent-size:18.0pt;tab-stops:36.0pt'><span style='font-size:");
			out
					.println("  18.0pt;mso-bidi-font-size:10.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''>洪志評老師<span");
			out
					.println("  lang=EN-US><span style='mso-spacerun: yes'>&nbsp; </span>為本校<span");
			out
					.println("  style='mso-spacerun: yes'>&nbsp; </span>專任土木工程系助理教授<o:p></o:p></span></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:25.4pt'>");
			out
					.println("  <td width=872 valign=top style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;");
			out.println("  height:25.4pt'>");
			out
					.println("  <p class=MsoNormal><span lang=EN-US style='font-size:18.0pt;mso-bidi-font-size:");
			out
					.println("  10.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''><span");
			out.println("  style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out
					.println("  </span>此<span style='mso-spacerun: yes'>&nbsp; </span>聘<o:p></o:p></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:14.65pt'>");
			out
					.println("  <td width=872 style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;height:14.65pt'>");
			out
					.println("  <p class=MsoNormal style='margin-right:-5.5gd;line-height:12.0pt;mso-line-height-rule:");
			out
					.println("  exactly'><span lang=EN-US style='font-size:18.0pt;mso-bidi-font-size:10.0pt;");
			out
					.println("  font-family:標楷體;mso-hansi-font-family:'Times New Roman''><span");
			out.println("  style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("  </span><span style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span");
			out
					.println("  style='mso-spacerun: yes'>&nbsp;</span></span><span style='font-size:10.0pt;");
			out
					.println("  font-family:標楷體;mso-hansi-font-family:'Times New Roman''>（聘約詳如背面）<span");
			out.println("  lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("  <p class=MsoNormal style='line-height:12.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("  lang=EN-US style='font-size:10.0pt;font-family:標楷體;mso-hansi-font-family:");
			out
					.println("  'Times New Roman''><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("  <p class=MsoNormal style='line-height:12.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("  lang=EN-US style='font-size:10.0pt;font-family:標楷體;mso-hansi-font-family:");
			out
					.println("  'Times New Roman''><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("  <p class=MsoNormal style='line-height:12.0pt;mso-line-height-rule:exactly'><![if !supportEmptyParas]>&nbsp;<![endif]><span");
			out
					.println("  lang=EN-US style='font-size:10.0pt;font-family:標楷體;mso-hansi-font-family:");
			out.println("  'Times New Roman''><o:p></o:p></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:47.85pt'>");
			out
					.println("  <td width=872 style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;height:47.85pt'>");
			out
					.println("  <p class=MsoNormal style='line-height:35.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("  lang=EN-US style='font-size:18.0pt;mso-bidi-font-size:10.0pt;font-family:");
			out
					.println("  標楷體;mso-hansi-font-family:'Times New Roman''><span style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp; </span></span><span lang=EN-US style='font-size:48.0pt;");
			out
					.println("  font-family:標楷體;mso-hansi-font-family:'Times New Roman''><span");
			out
					.println("  style='mso-spacerun: yes'>&nbsp;</span><span style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;</span><span style='mso-spacerun: yes'>&nbsp;&nbsp;</span></span><span");
			out
					.println("  style='font-size:36.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''>聘字第");
			out
					.println("  <span lang=EN-US>9 5 1 3 7 8 號 <o:p></o:p></span></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:40.35pt'>");
			out
					.println("  <td width=872 style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;height:40.35pt'>");
			out
					.println("  <p class=MsoNormal align=center style='margin-right:5.88gd;text-align:center'><span");
			out
					.println("  style='font-size:26.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''>應<span");
			out
					.println("  lang=EN-US><span style='mso-spacerun: yes'>&nbsp; </span>聘<span");
			out
					.println("  style='mso-spacerun: yes'>&nbsp; </span>書<o:p></o:p></span></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:36.35pt'>");
			out
					.println("  <td width=872 style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;height:36.35pt'>");
			out
					.println("  <p class=MsoNormal style='margin-top:.5gd;text-indent:80.0pt;mso-char-indent-count:");
			out
					.println("  4.0;mso-char-indent-size:20.0pt;line-height:20.0pt;mso-line-height-rule:exactly;");
			out
					.println("  tab-stops:119.25pt'><span style='font-size:20.0pt;font-family:標楷體;mso-hansi-font-family:");
			out
					.println("  'Times New Roman''>茲<span lang=EN-US><span style='mso-spacerun: yes'>&nbsp;");
			out
					.println("  </span>應<span style='mso-spacerun: yes'>&nbsp; </span>聘<o:p></o:p></span></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:29.25pt'>");
			out
					.println("  <td width=872 style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;height:29.25pt'>");
			out
					.println("  <p class=MsoNormal><span style='font-size:20.0pt;font-family:標楷體;mso-hansi-font-family:");
			out
					.println("  'Times New Roman''>貴校擔任 專任助理教授自<span lang=EN-US>96年2月1日起至96年7月31日止。<o:p></o:p></span></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:23.6pt'>");
			out
					.println("  <td width=872 valign=bottom style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;");
			out.println("  height:23.6pt'>");
			out
					.println("  <p class=MsoNormal style='margin-top:5.0pt'><!--[if gte vml 1]><v:shape id='_x0000_s1041'");
			out
					.println("   type='#_x0000_t202' style='position:absolute;margin-left:647.95pt;");
			out
					.println("   margin-top:8.25pt;width:30pt;height:130.4pt;z-index:3;");
			out
					.println("   mso-position-horizontal-relative:text;mso-position-vertical-relative:text'");
			out.println("   strokecolor='white'>");
			out
					.println("   <v:textbox style='mso-next-textbox:#_x0000_s1041'/>");
			out
					.println("  </v:shape><![endif]--><![if !vml]><span style='mso-ignore:vglayout;");
			out
					.println("  position:absolute;z-index:2;margin-left:864px;margin-top:11px;width:43px;");
			out.println("  height:177px'>");
			out.println("  <table cellpadding=0 cellspacing=0>");
			out.println("   <tr>");
			out
					.println("    <td width=43 height=177 align=left valign=top bgcolor=white");
			out
					.println("    style='border:.75pt solid white;vertical-align:top;background:white'><![endif]><![if !mso]><span");
			out.println("    style='position:absolute;z-index:3'>");
			out.println("    <table cellpadding=0 cellspacing=0 width='100%'>");
			out.println("     <tr>");
			out.println("      <td><![endif]>");
			out
					.println("      <div v:shape='_x0000_s1041' style='padding:3.6pt 7.2pt 3.6pt 7.2pt'");
			out.println("      class=shape>");
			out
					.println("      <p class=MsoNormal style='line-height:10.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("      style='font-size:10.0pt;font-family:標楷體'>本聯<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("      <p class=MsoNormal style='line-height:10.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("      style='font-size:10.0pt;font-family:標楷體'>請<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("      <p class=MsoNormal style='line-height:10.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("      style='font-size:10.0pt;font-family:標楷體'>逕<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("      <p class=MsoNormal style='line-height:10.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("      style='font-size:10.0pt;font-family:標楷體'>送<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("      <p class=MsoNormal style='line-height:10.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("      style='font-size:10.0pt;font-family:標楷體'>本<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("      <p class=MsoNormal style='line-height:10.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("      style='font-size:10.0pt;font-family:標楷體'>校<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("      <p class=MsoNormal style='line-height:10.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("      style='font-size:10.0pt;font-family:標楷體'>人<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("      <p class=MsoNormal style='line-height:10.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("      style='font-size:10.0pt;font-family:標楷體'>事室收<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("      <p class=MsoNormal style='line-height:10.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("      style='font-size:10.0pt;font-family:標楷體'>存<span lang=EN-US><o:p></o:p></span></span></p>");
			out.println("      </div>");
			out.println("      <![if !mso]></td>");
			out.println("     </tr>");
			out.println("    </table>");
			out
					.println("    </span><![endif]><![if !mso & !vml]>&nbsp;<![endif]><![if !vml]></td>");
			out.println("   </tr>");
			out.println("  </table>");
			out
					.println("  </span><![endif]><span lang=EN-US style='font-size:20.0pt;font-family:標楷體;");
			out
					.println("  mso-hansi-font-family:'Times New Roman''>並願履行聘約規定事項<o:p></o:p></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:19.35pt'>");
			out
					.println("  <td width=872 style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;height:19.35pt'>");
			out
					.println("  <p class=MsoNormal style='text-indent:80.0pt;mso-char-indent-count:4.0;");
			out
					.println("  mso-char-indent-size:20.0pt'><span style='font-size:20.0pt;font-family:標楷體;");
			out
					.println("  mso-hansi-font-family:'Times New Roman''>此<span lang=EN-US><span");
			out
					.println("  style='mso-spacerun: yes'>&nbsp; </span>致<o:p></o:p></span></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:28.15pt'>");
			out
					.println("  <td width=872 valign=top style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;");
			out.println("  height:28.15pt'>");
			out
					.println("  <p class=MsoNormal style='margin-top:.5gd;line-height:20.0pt;mso-line-height-rule:");
			out
					.println("  exactly'><span style='font-size:20.0pt;font-family:標楷體;mso-hansi-font-family:");
			out.println("  'Times New Roman''>" + schoolName
					+ "</span><!--[if gte vml 1]><v:shape id='_x0000_s1038'");
			out
					.println("   type='#_x0000_t202' style='position:absolute;margin-left:601.35pt;");
			out
					.println("   margin-top:.8pt;width:36pt;height:33.65pt;z-index:2;");
			out
					.println("   mso-position-horizontal-relative:text;mso-position-vertical-relative:text'");
			out.println("   strokecolor='white'>");
			out
					.println("   <v:textbox style='mso-next-textbox:#_x0000_s1038'/>");
			out
					.println("  </v:shape><![endif]--><![if !vml]><span style='mso-ignore:vglayout;");
			out
					.println("  position:absolute;z-index:1;margin-left:802px;margin-top:1px;width:51px;");
			out.println("  height:48px'>");
			out.println("  <table cellpadding=0 cellspacing=0>");
			out.println("   <tr>");
			out
					.println("    <td width=51 height=48 align=left valign=top bgcolor=white");
			out
					.println("    style='border:.75pt solid white;vertical-align:top;background:white'><![endif]><![if !mso]><span");
			out.println("    style='position:absolute;z-index:2'>");
			out.println("    <table cellpadding=0 cellspacing=0 width='100%'>");
			out.println("     <tr>");
			out.println("      <td><![endif]>");
			out
					.println("      <div v:shape='_x0000_s1038' style='padding:3.6pt 7.2pt 3.6pt 7.2pt'");
			out.println("      class=shape>");
			out
					.println("      <p class=MsoNormal style='line-height:12.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("      style='font-size:10.0pt;font-family:標楷體'>簽名<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("      <p class=MsoNormal style='line-height:12.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("      style='font-size:10.0pt;font-family:標楷體'>蓋章<span lang=EN-US><o:p></o:p></span></span></p>");
			out.println("      </div>");
			out.println("      <![if !mso]></td>");
			out.println("     </tr>");
			out.println("    </table>");
			out
					.println("    </span><![endif]><![if !mso & !vml]>&nbsp;<![endif]><![if !vml]></td>");
			out.println("   </tr>");
			out.println("  </table>");
			out
					.println("  </span><![endif]><span lang=EN-US style='font-size:18.0pt;mso-bidi-font-size:");
			out
					.println("  10.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''><span");
			out
					.println("  style='mso-spacerun: yes'>&nbsp; </span><span style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span");
			out.println("  style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span");
			out
					.println("  style='font-size:10.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''>應");
			out.println("  聘 人<span lang=EN-US><span style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("  </span><o:p></o:p></span></span></p>");
			out
					.println("  <p class=MsoNormal style='line-height:10.0pt;mso-line-height-rule:exactly'><![if !supportEmptyParas]>&nbsp;<![endif]><span");
			out
					.println("  lang=EN-US style='font-size:18.0pt;mso-bidi-font-size:10.0pt;font-family:");
			out
					.println("  標楷體;mso-hansi-font-family:'Times New Roman''><o:p></o:p></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:34.25pt'>");
			out
					.println("  <td width=872 valign=top style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;");
			out.println("  height:34.25pt'>");
			out
					.println("  <p class=MsoNormal style='margin-top:.5gd;line-height:20.0pt;mso-line-height-rule:");
			out
					.println("  exactly;tab-stops:564.75pt'><span lang=EN-US style='font-size:18.0pt;");
			out
					.println("  mso-bidi-font-size:10.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''><span");
			out
					.println("  style='mso-spacerun: yes'>&nbsp; </span></span><span lang=EN-US");
			out
					.println("  style='font-size:20.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''><span");
			out
					.println("  style='mso-spacerun: yes'>&nbsp;</span>中<span style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp; </span>華<span style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp; </span>民<span style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp; </span>國<span style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>年<span");
			out.println("  style='mso-spacerun:");
			out
					.println("  yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>月<span");
			out
					.println("  style='mso-spacerun: yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("  </span>日<o:p></o:p></span></p>");
			out
					.println("  <p class=MsoNormal style='line-height:20.0pt;mso-line-height-rule:exactly;");
			out
					.println("  tab-stops:564.75pt'><![if !supportEmptyParas]>&nbsp;<![endif]><span");
			out
					.println("  lang=EN-US style='font-size:20.0pt;font-family:標楷體;mso-hansi-font-family:");
			out.println("  'Times New Roman''><o:p></o:p></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println(" <tr style='height:59.65pt'>");
			out
					.println("  <td width=872 valign=top style='width:654.0pt;padding:0cm 1.4pt 0cm 1.4pt;");
			out.println("  height:59.65pt'>");
			out
					.println("  <p class=MsoNormal style='margin-top:2.0gd;text-indent:108.0pt;mso-char-indent-count:");
			out
					.println("  3.0;mso-char-indent-size:36.0pt;line-height:35.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("  style='font-size:36.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''>中華聘字第");
			out
					.println("  <span lang=EN-US>9 5 1 3 7 8號<span style='mso-spacerun: yes'>&nbsp; </span><o:p></o:p></span></span></p>");
			out.println("  </td>");
			out.println(" </tr>");
			out.println("</table>");
			out.println("");
			out
					.println("<p class=MsoNormal style='margin-top:3.5gd;line-height:27.0pt;mso-line-height-rule:");
			out
					.println("exactly'><!--[if gte vml 1]><v:shape id='_x0000_s1053' type='#_x0000_t202'");
			out
					.println(" style='position:absolute;margin-left:36pt;margin-top:42pt;width:666pt;");
			out
					.println(" height:440.1pt;z-index:4;mso-position-horizontal-relative:text;");
			out
					.println(" mso-position-vertical-relative:text' strokeweight='2.25pt'>");
			out
					.println(" <v:textbox style='layout-flow:vertical;mso-layout-flow-alt:bottom-to-top'>");
			out.println("  <![if RotText]><![if !mso]>");
			out.println("  <table cellpadding=0 cellspacing=0 width='100%'>");
			out.println("   <tr>");
			out.println("    <td><![endif]>");
			out.println("    <div>");
			out
					.println("    <p class=MsoNormal style='margin-top:1.5gd;text-indent:72.0pt;mso-char-indent-count:");
			out
					.println("    2.0;mso-char-indent-size:36.0pt;line-height:30.0pt;mso-line-height-rule:");
			out
					.println("    exactly'><span style='font-size:36.0pt;font-family:標楷體;mso-hansi-font-family:");
			out.println("    'Times New Roman''>" + schoolName
					+ "聘書</span><span lang=EN-US style='font-size:26.0pt;");
			out
					.println("    font-family:標楷體;mso-hansi-font-family:'Times New Roman''><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal align=right style='margin-top:1.5gd;text-align:right;");
			out
					.println("    line-height:30.0pt;mso-line-height-rule:exactly;word-break:break-all'><span");
			out
					.println("    lang=EN-US style='font-size:26.0pt;font-family:標楷體;mso-hansi-font-family:");
			out
					.println("    'Times New Roman''><span style='mso-spacerun: yes'>&nbsp;</span></span><span");
			out
					.println("    style='mso-bidi-font-size:12.0pt;font-family:標楷體;mso-hansi-font-family:");
			out
					.println("    'Times New Roman''>中華聘字第<span lang=EN-US>951378號 <o:p></o:p></span></span></p>");
			out
					.println("    <p class=MsoNormal style='text-align:justify;text-justify:inter-ideograph;");
			out
					.println("    line-height:25.0pt;mso-line-height-rule:exactly'><span lang=EN-US");
			out
					.println("    style='mso-bidi-font-size:12.0pt;font-family:標楷體;mso-hansi-font-family:");
			out
					.println("    'Times New Roman''><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal align=center style='text-align:center;line-height:25.0pt;");
			out
					.println("    mso-line-height-rule:exactly'><span style='font-size:24.0pt;font-family:");
			out
					.println("    標楷體;mso-hansi-font-family:'Times New Roman''>茲 敦 聘<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("    <p class=MsoNormal style='text-indent:144.0pt;mso-char-indent-count:6.0;");
			out
					.println("    mso-char-indent-size:24.0pt;line-height:25.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("    lang=EN-US style='font-size:24.0pt;font-family:標楷體;mso-hansi-font-family:");
			out
					.println("    'Times New Roman''><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal align=center style='text-align:center;line-height:35.0pt;");
			out
					.println("    mso-line-height-rule:exactly'><span style='font-size:24.0pt;font-family:");
			out
					.println("    標楷體'>洪志評老師 為本校<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("    <p class=MsoNormal align=center style='text-align:center;line-height:35.0pt;");
			out
					.println("    mso-line-height-rule:exactly'><span style='font-size:24.0pt;font-family:");
			out
					.println("    標楷體;mso-hansi-font-family:'Times New Roman''>專任土木工程系助理教授 </span><span");
			out
					.println("    lang=EN-US style='font-size:24.0pt;font-family:標楷體'><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal style='line-height:35.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("    lang=EN-US style='font-size:24.0pt;font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal align=center style='text-align:center;line-height:30.0pt;");
			out
					.println("    mso-line-height-rule:exactly'><span style='font-size:24.0pt;font-family:");
			out
					.println("    標楷體'>任期自民國玖拾陸年貳月壹日起<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("    <p class=MsoNormal align=center style='text-align:center;line-height:30.0pt;");
			out
					.println("    mso-line-height-rule:exactly'><span style='font-size:24.0pt;font-family:");
			out
					.println("    標楷體'>至玖拾陸年柒月參拾壹日止<span lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("    <p class=MsoNormal align=center style='text-align:center;line-height:25.0pt;");
			out
					.println("    mso-line-height-rule:exactly'><span lang=EN-US style='font-size:24.0pt;");
			out
					.println("    font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal align=center style='text-align:center;line-height:25.0pt;");
			out
					.println("    mso-line-height-rule:exactly'><span lang=EN-US style='font-size:20.0pt;");
			out
					.println("    font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal align=center style='text-align:center;line-height:25.0pt;");
			out
					.println("    mso-line-height-rule:exactly'><span style='font-size:24.0pt;font-family:");
			out
					.println("    標楷體'>此<span lang=EN-US><span style='mso-spacerun: yes'>&nbsp;&nbsp; </span>聘<o:p></o:p></span></span></p>");
			out
					.println("    <p class=MsoNormal align=center style='text-align:center;line-height:10.0pt;");
			out
					.println("    mso-line-height-rule:exactly'><span lang=EN-US style='font-size:20.0pt;");
			out
					.println("    font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal align=center style='text-align:center;line-height:10.0pt;");
			out
					.println("    mso-line-height-rule:exactly'><span lang=EN-US style='font-size:20.0pt;");
			out
					.println("    font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal align=center style='text-align:center'><span");
			out
					.println("    style='mso-bidi-font-size:12.0pt;font-family:標楷體'>附註：聘約詳如背面<span");
			out.println("    lang=EN-US><o:p></o:p></span></span></p>");
			out
					.println("    <p class=MsoNormal style='text-indent:70.0pt;mso-char-indent-count:5.0;");
			out
					.println("    mso-char-indent-size:14.0pt'><span lang=EN-US style='font-size:14.0pt;");
			out
					.println("    font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal style='text-indent:70.0pt;mso-char-indent-count:5.0;");
			out
					.println("    mso-char-indent-size:14.0pt'><span lang=EN-US style='font-size:14.0pt;");
			out
					.println("    font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal style='text-indent:70.0pt;mso-char-indent-count:5.0;");
			out
					.println("    mso-char-indent-size:14.0pt'><span lang=EN-US style='font-size:14.0pt;");
			out
					.println("    font-family:標楷體'><span style='mso-spacerun: yes'>&nbsp;</span><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal style='text-indent:70.0pt;mso-char-indent-count:5.0;");
			out
					.println("    mso-char-indent-size:14.0pt'><span lang=EN-US style='font-size:14.0pt;");
			out
					.println("    font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal style='text-indent:70.0pt;mso-char-indent-count:5.0;");
			out
					.println("    mso-char-indent-size:14.0pt'><span lang=EN-US style='font-size:14.0pt;");
			out
					.println("    font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal style='text-indent:70.0pt;mso-char-indent-count:5.0;");
			out
					.println("    mso-char-indent-size:14.0pt'><span lang=EN-US style='font-size:14.0pt;");
			out
					.println("    font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal style='text-indent:70.0pt;mso-char-indent-count:5.0;");
			out
					.println("    mso-char-indent-size:14.0pt'><span lang=EN-US style='font-size:14.0pt;");
			out
					.println("    font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal style='text-indent:70.0pt;mso-char-indent-count:5.0;");
			out
					.println("    mso-char-indent-size:14.0pt'><span lang=EN-US style='font-size:14.0pt;");
			out
					.println("    font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal style='text-indent:70.0pt;mso-char-indent-count:5.0;");
			out
					.println("    mso-char-indent-size:14.0pt;line-height:10.0pt;mso-line-height-rule:exactly'><span");
			out
					.println("    lang=EN-US style='font-size:14.0pt;font-family:標楷體'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out
					.println("    <p class=MsoNormal align=center style='text-align:center'><span");
			out
					.println("    style='font-size:20.0pt;font-family:標楷體;mso-hansi-font-family:'Times New Roman''>中");
			out
					.println("    <span style='mso-spacerun: yes'>&nbsp;</span>華 <span style='mso-spacerun:");
			out
					.println("    yes'>&nbsp;</span>民<span lang=EN-US><span style='mso-spacerun: yes'>&nbsp;");
			out
					.println("    </span>國 玖拾陸 年<span style='mso-spacerun: yes'>&nbsp; </span>陸 月 <span");
			out
					.println("    style='mso-spacerun: yes'>&nbsp;&nbsp;</span><span style='mso-spacerun:");
			out
					.println("    yes'>&nbsp;</span><span style='mso-spacerun: yes'>&nbsp;</span><span");
			out
					.println("    style='mso-spacerun: yes'>&nbsp;</span><span style='mso-spacerun:");
			out.println("    yes'>&nbsp;</span>日</span></span></p>");
			out.println("    </div>");
			out.println("    <![if !mso]></td>");
			out.println("   </tr>");
			out.println("  </table>");
			out.println("  <![endif]><![endif]></v:textbox>");
			out
					.println("</v:shape><![endif]--><![if !vml]><span style='mso-ignore:vglayout'>");
			out.println("");
			out.println("<table cellpadding=0 cellspacing=0 align=left>");
			out.println(" <tr>");
			out.println("  <td width=47 height=55></td>");
			out.println(" </tr>");
			out.println(" <tr>");
			out.println("  <td></td>");
			out
					.println("  <td><![endif]><![if !RotText]><img width=893 height=592");
			out.println("  src='./contract.files/image002.gif'");
			out
					.println("  alt='文字方塊: "
							+ schoolName
							+ "聘書&#13;&#10; 中華聘字第951378號 &#13;&#10;&#13;&#10;茲 敦 聘&#13;&#10;&#13;&#10;洪志評老師 為本校&#13;&#10;專任土木工程系助理教授 &#13;&#10;&#13;&#10;任期自民國玖拾陸年貳月壹日起&#13;&#10;至玖拾陸年柒月參拾壹日止&#13;&#10;&#13;&#10;&#13;&#10;此   聘&#13;&#10;&#13;&#10;&#13;&#10;附註：聘約詳如背面&#13;&#10;&#13;&#10;&#13;&#10; &#13;&#10;&#13;&#10;&#13;&#10;&#13;&#10;&#13;&#10;&#13;&#10;&#13;&#10;中  華  民  國 玖拾陸 年  陸 月       日&#13;&#10;'");
			out
					.println("  v:shapes='_x0000_s1053' class=shape v:dpi='96'><![endif]><![if !vml]></td>");
			out.println(" </tr>");
			out.println("</table>");
			out.println("<p clear=all style='page-break-before:always'/>");
			out
					.println("</span><![endif]><span lang=EN-US><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");
			out.println("<br clear=all style='page-break-before:always'/>");
			out
					.println("</span><![endif]><span lang=EN-US><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>");

		}
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		out.close();

	}

}