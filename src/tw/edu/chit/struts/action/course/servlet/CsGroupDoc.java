package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class CsGroupDoc extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);

		response.setContentType("application/vnd.ms-word; charset=UTF-8");
		response.setHeader("Content-disposition",
				"attachment; filename=document" + (int) (Math.random() * 49)
						+ ".doc");
		Calendar n = Calendar.getInstance();
		n.setTime(new Date());

		PrintWriter out = response.getWriter();

		out.println("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
		out.println("<?mso-application progid='Word.Document'?>");
		out
				.println("<w:wordDocument xmlns:aml='http://schemas.microsoft.com/aml/2001/core' xmlns:dt='uuid:C2F41010-65B3-11d1-A29F-00AA00C14882' xmlns:ve='http://schemas.openxmlformats.org/markup-compatibility/2006' xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:v='urn:schemas-microsoft-com:vml' xmlns:w10='urn:schemas-microsoft-com:office:word' xmlns:w='http://schemas.microsoft.com/office/word/2003/wordml' xmlns:wx='http://schemas.microsoft.com/office/word/2003/auxHint' xmlns:wsp='http://schemas.microsoft.com/office/word/2003/wordml/sp2' xmlns:sl='http://schemas.microsoft.com/schemaLibrary/2003/core' w:macrosPresent='no' w:embeddedObjPresent='no' w:ocxPresent='no' xml:space='preserve'>");
		out
				.println("  <w:ignoreSubtree w:val='http://schemas.microsoft.com/office/word/2003/wordml/sp2'/>");
		out.println("  <o:DocumentProperties>");
		out.println("    <o:Author>Shawn</o:Author>");
		out.println("    <o:LastAuthor>Shawn</o:LastAuthor>");
		out.println("    <o:Revision>1</o:Revision>");
		out.println("    <o:TotalTime>1</o:TotalTime>");
		out.println("    <o:Created>2010-09-13T07:14:00Z</o:Created>");
		out.println("    <o:LastSaved>2010-09-13T07:15:00Z</o:LastSaved>");
		out.println("    <o:Pages>1</o:Pages>");
		out.println("    <o:Words>31</o:Words>");
		out.println("    <o:Characters>178</o:Characters>");
		out.println("    <o:Lines>1</o:Lines>");
		out.println("    <o:Paragraphs>1</o:Paragraphs>");
		out.println("    <o:CharactersWithSpaces>208</o:CharactersWithSpaces>");
		out.println("    <o:Version>12</o:Version>");
		out.println("  </o:DocumentProperties>");
		out.println("  <w:fonts>");
		out
				.println("    <w:defaultFonts w:ascii='Calibri' w:fareast='新細明體' w:h-ansi='Calibri' w:cs='Times New Roman'/>");
		out.println("    <w:font w:name='Times New Roman'>");
		out.println("      <w:panose-1 w:val='02020603050405020304'/>");
		out.println("      <w:charset w:val='00'/>");
		out.println("      <w:family w:val='Roman'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out
				.println("      <w:sig w:usb-0='E0002AFF' w:usb-1='C0007841' w:usb-2='00000009' w:usb-3='00000000' w:csb-0='000001FF' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='新細明體'>");
		out.println("      <w:altName w:val='PMingLiU'/>");
		out.println("      <w:panose-1 w:val='02020500000000000000'/>");
		out.println("      <w:charset w:val='88'/>");
		out.println("      <w:family w:val='Roman'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out
				.println("      <w:sig w:usb-0='A00002FF' w:usb-1='28CFFCFA' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='Cambria Math'>");
		out.println("      <w:panose-1 w:val='02040503050406030204'/>");
		out.println("      <w:charset w:val='01'/>");
		out.println("      <w:family w:val='Roman'/>");
		out.println("      <w:notTrueType/>");
		out.println("      <w:pitch w:val='variable'/>");
		out
				.println("      <w:sig w:usb-0='00000000' w:usb-1='00000000' w:usb-2='00000000' w:usb-3='00000000' w:csb-0='00000000' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='Arial Unicode MS'>");
		out.println("      <w:panose-1 w:val='020B0604020202020204'/>");
		out.println("      <w:charset w:val='88'/>");
		out.println("      <w:family w:val='Swiss'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out
				.println("      <w:sig w:usb-0='F7FFAFFF' w:usb-1='E9DFFFFF' w:usb-2='0000003F' w:usb-3='00000000' w:csb-0='003F01FF' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='Calibri'>");
		out.println("      <w:panose-1 w:val='020F0502020204030204'/>");
		out.println("      <w:charset w:val='00'/>");
		out.println("      <w:family w:val='Swiss'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out
				.println("      <w:sig w:usb-0='E10002FF' w:usb-1='4000ACFF' w:usb-2='00000009' w:usb-3='00000000' w:csb-0='0000019F' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='標楷體'>");
		out.println("      <w:panose-1 w:val='03000509000000000000'/>");
		out.println("      <w:charset w:val='88'/>");
		out.println("      <w:family w:val='Script'/>");
		out.println("      <w:pitch w:val='fixed'/>");
		out
				.println("      <w:sig w:usb-0='00000003' w:usb-1='080E0000' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='@標楷體'>");
		out.println("      <w:panose-1 w:val='03000509000000000000'/>");
		out.println("      <w:charset w:val='88'/>");
		out.println("      <w:family w:val='Script'/>");
		out.println("      <w:pitch w:val='fixed'/>");
		out
				.println("      <w:sig w:usb-0='00000003' w:usb-1='080E0000' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='@新細明體'>");
		out.println("      <w:panose-1 w:val='02020500000000000000'/>");
		out.println("      <w:charset w:val='88'/>");
		out.println("      <w:family w:val='Roman'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out
				.println("      <w:sig w:usb-0='A00002FF' w:usb-1='28CFFCFA' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='@Arial Unicode MS'>");
		out.println("      <w:panose-1 w:val='020B0604020202020204'/>");
		out.println("      <w:charset w:val='88'/>");
		out.println("      <w:family w:val='Swiss'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out
				.println("      <w:sig w:usb-0='F7FFAFFF' w:usb-1='E9DFFFFF' w:usb-2='0000003F' w:usb-3='00000000' w:csb-0='003F01FF' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("  </w:fonts>");
		out.println("  <w:styles>");
		out.println("    <w:versionOfBuiltInStylenames w:val='7'/>");
		out
				.println("    <w:latentStyles w:defLockedState='off' w:latentStyleCount='267'>");
		out.println("      <w:lsdException w:name='Normal'/>");
		out.println("      <w:lsdException w:name='heading 1'/>");
		out.println("      <w:lsdException w:name='heading 2'/>");
		out.println("      <w:lsdException w:name='heading 3'/>");
		out.println("      <w:lsdException w:name='heading 4'/>");
		out.println("      <w:lsdException w:name='heading 5'/>");
		out.println("      <w:lsdException w:name='heading 6'/>");
		out.println("      <w:lsdException w:name='heading 7'/>");
		out.println("      <w:lsdException w:name='heading 8'/>");
		out.println("      <w:lsdException w:name='heading 9'/>");
		out.println("      <w:lsdException w:name='toc 1'/>");
		out.println("      <w:lsdException w:name='toc 2'/>");
		out.println("      <w:lsdException w:name='toc 3'/>");
		out.println("      <w:lsdException w:name='toc 4'/>");
		out.println("      <w:lsdException w:name='toc 5'/>");
		out.println("      <w:lsdException w:name='toc 6'/>");
		out.println("      <w:lsdException w:name='toc 7'/>");
		out.println("      <w:lsdException w:name='toc 8'/>");
		out.println("      <w:lsdException w:name='toc 9'/>");
		out.println("      <w:lsdException w:name='caption'/>");
		out.println("      <w:lsdException w:name='Title'/>");
		out.println("      <w:lsdException w:name='Default Paragraph Font'/>");
		out.println("      <w:lsdException w:name='Subtitle'/>");
		out.println("      <w:lsdException w:name='Strong'/>");
		out.println("      <w:lsdException w:name='Emphasis'/>");
		out.println("      <w:lsdException w:name='Table Grid'/>");
		out.println("      <w:lsdException w:name='Placeholder Text'/>");
		out.println("      <w:lsdException w:name='No Spacing'/>");
		out.println("      <w:lsdException w:name='Light Shading'/>");
		out.println("      <w:lsdException w:name='Light List'/>");
		out.println("      <w:lsdException w:name='Light Grid'/>");
		out.println("      <w:lsdException w:name='Medium Shading 1'/>");
		out.println("      <w:lsdException w:name='Medium Shading 2'/>");
		out.println("      <w:lsdException w:name='Medium List 1'/>");
		out.println("      <w:lsdException w:name='Medium List 2'/>");
		out.println("      <w:lsdException w:name='Medium Grid 1'/>");
		out.println("      <w:lsdException w:name='Medium Grid 2'/>");
		out.println("      <w:lsdException w:name='Medium Grid 3'/>");
		out.println("      <w:lsdException w:name='Dark List'/>");
		out.println("      <w:lsdException w:name='Colorful Shading'/>");
		out.println("      <w:lsdException w:name='Colorful List'/>");
		out.println("      <w:lsdException w:name='Colorful Grid'/>");
		out.println("      <w:lsdException w:name='Light Shading Accent 1'/>");
		out.println("      <w:lsdException w:name='Light List Accent 1'/>");
		out.println("      <w:lsdException w:name='Light Grid Accent 1'/>");
		out
				.println("      <w:lsdException w:name='Medium Shading 1 Accent 1'/>");
		out
				.println("      <w:lsdException w:name='Medium Shading 2 Accent 1'/>");
		out.println("      <w:lsdException w:name='Medium List 1 Accent 1'/>");
		out.println("      <w:lsdException w:name='Revision'/>");
		out.println("      <w:lsdException w:name='List Paragraph'/>");
		out.println("      <w:lsdException w:name='Quote'/>");
		out.println("      <w:lsdException w:name='Intense Quote'/>");
		out.println("      <w:lsdException w:name='Medium List 2 Accent 1'/>");
		out.println("      <w:lsdException w:name='Medium Grid 1 Accent 1'/>");
		out.println("      <w:lsdException w:name='Medium Grid 2 Accent 1'/>");
		out.println("      <w:lsdException w:name='Medium Grid 3 Accent 1'/>");
		out.println("      <w:lsdException w:name='Dark List Accent 1'/>");
		out
				.println("      <w:lsdException w:name='Colorful Shading Accent 1'/>");
		out.println("      <w:lsdException w:name='Colorful List Accent 1'/>");
		out.println("      <w:lsdException w:name='Colorful Grid Accent 1'/>");
		out.println("      <w:lsdException w:name='Light Shading Accent 2'/>");
		out.println("      <w:lsdException w:name='Light List Accent 2'/>");
		out.println("      <w:lsdException w:name='Light Grid Accent 2'/>");
		out
				.println("      <w:lsdException w:name='Medium Shading 1 Accent 2'/>");
		out
				.println("      <w:lsdException w:name='Medium Shading 2 Accent 2'/>");
		out.println("      <w:lsdException w:name='Medium List 1 Accent 2'/>");
		out.println("      <w:lsdException w:name='Medium List 2 Accent 2'/>");
		out.println("      <w:lsdException w:name='Medium Grid 1 Accent 2'/>");
		out.println("      <w:lsdException w:name='Medium Grid 2 Accent 2'/>");
		out.println("      <w:lsdException w:name='Medium Grid 3 Accent 2'/>");
		out.println("      <w:lsdException w:name='Dark List Accent 2'/>");
		out
				.println("      <w:lsdException w:name='Colorful Shading Accent 2'/>");
		out.println("      <w:lsdException w:name='Colorful List Accent 2'/>");
		out.println("      <w:lsdException w:name='Colorful Grid Accent 2'/>");
		out.println("      <w:lsdException w:name='Light Shading Accent 3'/>");
		out.println("      <w:lsdException w:name='Light List Accent 3'/>");
		out.println("      <w:lsdException w:name='Light Grid Accent 3'/>");
		out
				.println("      <w:lsdException w:name='Medium Shading 1 Accent 3'/>");
		out
				.println("      <w:lsdException w:name='Medium Shading 2 Accent 3'/>");
		out.println("      <w:lsdException w:name='Medium List 1 Accent 3'/>");
		out.println("      <w:lsdException w:name='Medium List 2 Accent 3'/>");
		out.println("      <w:lsdException w:name='Medium Grid 1 Accent 3'/>");
		out.println("      <w:lsdException w:name='Medium Grid 2 Accent 3'/>");
		out.println("      <w:lsdException w:name='Medium Grid 3 Accent 3'/>");
		out.println("      <w:lsdException w:name='Dark List Accent 3'/>");
		out
				.println("      <w:lsdException w:name='Colorful Shading Accent 3'/>");
		out.println("      <w:lsdException w:name='Colorful List Accent 3'/>");
		out.println("      <w:lsdException w:name='Colorful Grid Accent 3'/>");
		out.println("      <w:lsdException w:name='Light Shading Accent 4'/>");
		out.println("      <w:lsdException w:name='Light List Accent 4'/>");
		out.println("      <w:lsdException w:name='Light Grid Accent 4'/>");
		out
				.println("      <w:lsdException w:name='Medium Shading 1 Accent 4'/>");
		out
				.println("      <w:lsdException w:name='Medium Shading 2 Accent 4'/>");
		out.println("      <w:lsdException w:name='Medium List 1 Accent 4'/>");
		out.println("      <w:lsdException w:name='Medium List 2 Accent 4'/>");
		out.println("      <w:lsdException w:name='Medium Grid 1 Accent 4'/>");
		out.println("      <w:lsdException w:name='Medium Grid 2 Accent 4'/>");
		out.println("      <w:lsdException w:name='Medium Grid 3 Accent 4'/>");
		out.println("      <w:lsdException w:name='Dark List Accent 4'/>");
		out
				.println("      <w:lsdException w:name='Colorful Shading Accent 4'/>");
		out.println("      <w:lsdException w:name='Colorful List Accent 4'/>");
		out.println("      <w:lsdException w:name='Colorful Grid Accent 4'/>");
		out.println("      <w:lsdException w:name='Light Shading Accent 5'/>");
		out.println("      <w:lsdException w:name='Light List Accent 5'/>");
		out.println("      <w:lsdException w:name='Light Grid Accent 5'/>");
		out
				.println("      <w:lsdException w:name='Medium Shading 1 Accent 5'/>");
		out
				.println("      <w:lsdException w:name='Medium Shading 2 Accent 5'/>");
		out.println("      <w:lsdException w:name='Medium List 1 Accent 5'/>");
		out.println("      <w:lsdException w:name='Medium List 2 Accent 5'/>");
		out.println("      <w:lsdException w:name='Medium Grid 1 Accent 5'/>");
		out.println("      <w:lsdException w:name='Medium Grid 2 Accent 5'/>");
		out.println("      <w:lsdException w:name='Medium Grid 3 Accent 5'/>");
		out.println("      <w:lsdException w:name='Dark List Accent 5'/>");
		out
				.println("      <w:lsdException w:name='Colorful Shading Accent 5'/>");
		out.println("      <w:lsdException w:name='Colorful List Accent 5'/>");
		out.println("      <w:lsdException w:name='Colorful Grid Accent 5'/>");
		out.println("      <w:lsdException w:name='Light Shading Accent 6'/>");
		out.println("      <w:lsdException w:name='Light List Accent 6'/>");
		out.println("      <w:lsdException w:name='Light Grid Accent 6'/>");
		out
				.println("      <w:lsdException w:name='Medium Shading 1 Accent 6'/>");
		out
				.println("      <w:lsdException w:name='Medium Shading 2 Accent 6'/>");
		out.println("      <w:lsdException w:name='Medium List 1 Accent 6'/>");
		out.println("      <w:lsdException w:name='Medium List 2 Accent 6'/>");
		out.println("      <w:lsdException w:name='Medium Grid 1 Accent 6'/>");
		out.println("      <w:lsdException w:name='Medium Grid 2 Accent 6'/>");
		out.println("      <w:lsdException w:name='Medium Grid 3 Accent 6'/>");
		out.println("      <w:lsdException w:name='Dark List Accent 6'/>");
		out
				.println("      <w:lsdException w:name='Colorful Shading Accent 6'/>");
		out.println("      <w:lsdException w:name='Colorful List Accent 6'/>");
		out.println("      <w:lsdException w:name='Colorful Grid Accent 6'/>");
		out.println("      <w:lsdException w:name='Subtle Emphasis'/>");
		out.println("      <w:lsdException w:name='Intense Emphasis'/>");
		out.println("      <w:lsdException w:name='Subtle Reference'/>");
		out.println("      <w:lsdException w:name='Intense Reference'/>");
		out.println("      <w:lsdException w:name='Book Title'/>");
		out.println("      <w:lsdException w:name='Bibliography'/>");
		out.println("      <w:lsdException w:name='TOC Heading'/>");
		out.println("    </w:latentStyles>");
		out
				.println("    <w:style w:type='paragraph' w:default='on' w:styleId='a'>");
		out.println("      <w:name w:val='Normal'/>");
		out.println("      <wx:uiName wx:val='內文'/>");
		out.println("      <w:rsid w:val='002F4E65'/>");
		out.println("      <w:pPr>");
		out.println("        <w:widowControl w:val='off'/>");
		out.println("      </w:pPr>");
		out.println("      <w:rPr>");
		out
				.println("        <w:rFonts w:ascii='Times New Roman' w:h-ansi='Times New Roman'/>");
		out.println("        <wx:font wx:val='Times New Roman'/>");
		out.println("        <w:kern w:val='2'/>");
		out.println("        <w:sz w:val='24'/>");
		out.println("        <w:sz-cs w:val='24'/>");
		out
				.println("        <w:lang w:val='EN-US' w:fareast='ZH-TW' w:bidi='AR-SA'/>");
		out.println("      </w:rPr>");
		out.println("    </w:style>");
		out
				.println("    <w:style w:type='character' w:default='on' w:styleId='a0'>");
		out.println("      <w:name w:val='Default Paragraph Font'/>");
		out.println("      <wx:uiName wx:val='預設段落字型'/>");
		out.println("    </w:style>");
		out
				.println("    <w:style w:type='table' w:default='on' w:styleId='a1'>");
		out.println("      <w:name w:val='Normal Table'/>");
		out.println("      <wx:uiName wx:val='表格內文'/>");
		out.println("      <w:rPr>");
		out.println("        <wx:font wx:val='Calibri'/>");
		out
				.println("        <w:lang w:val='EN-US' w:fareast='ZH-TW' w:bidi='AR-SA'/>");
		out.println("      </w:rPr>");
		out.println("      <w:tblPr>");
		out.println("        <w:tblInd w:w='0' w:type='dxa'/>");
		out.println("        <w:tblCellMar>");
		out.println("          <w:top w:w='0' w:type='dxa'/>");
		out.println("          <w:left w:w='108' w:type='dxa'/>");
		out.println("          <w:bottom w:w='0' w:type='dxa'/>");
		out.println("          <w:right w:w='108' w:type='dxa'/>");
		out.println("        </w:tblCellMar>");
		out.println("      </w:tblPr>");
		out.println("    </w:style>");
		out
				.println("    <w:style w:type='list' w:default='on' w:styleId='a2'>");
		out.println("      <w:name w:val='No List'/>");
		out.println("      <wx:uiName wx:val='無清單'/>");
		out.println("    </w:style>");
		out.println("  </w:styles>");
		out.println("  <w:shapeDefaults>");
		out.println("    <o:shapedefaults v:ext='edit' spidmax='2050'/>");
		out.println("    <o:shapelayout v:ext='edit'>");
		out.println("      <o:idmap v:ext='edit' data='1'/>");
		out.println("    </o:shapelayout>");
		out.println("  </w:shapeDefaults>");
		out.println("  <w:docPr>");
		out.println("    <w:view w:val='print'/>");
		out.println("    <w:zoom w:percent='100'/>");
		out.println("    <w:doNotEmbedSystemFonts/>");
		out.println("    <w:bordersDontSurroundHeader/>");
		out.println("    <w:bordersDontSurroundFooter/>");
		out.println("    <w:proofState w:spelling='clean' w:grammar='clean'/>");
		out.println("    <w:defaultTabStop w:val='480'/>");
		out.println("    <w:displayHorizontalDrawingGridEvery w:val='0'/>");
		out.println("    <w:displayVerticalDrawingGridEvery w:val='2'/>");
		out.println("    <w:punctuationKerning/>");
		out
				.println("    <w:characterSpacingControl w:val='CompressPunctuation'/>");
		out.println("    <w:optimizeForBrowser/>");
		out.println("    <w:validateAgainstSchema/>");
		out.println("    <w:saveInvalidXML w:val='off'/>");
		out.println("    <w:ignoreMixedContent w:val='off'/>");
		out.println("    <w:alwaysShowPlaceholderText w:val='off'/>");
		out.println("    <w:compat>");
		out.println("      <w:spaceForUL/>");
		out.println("      <w:balanceSingleByteDoubleByteWidth/>");
		out.println("      <w:doNotLeaveBackslashAlone/>");
		out.println("      <w:ulTrailSpace/>");
		out.println("      <w:doNotExpandShiftReturn/>");
		out.println("      <w:adjustLineHeightInTable/>");
		out.println("      <w:breakWrappedTables/>");
		out.println("      <w:snapToGridInCell/>");
		out.println("      <w:wrapTextWithPunct/>");
		out.println("      <w:useAsianBreakRules/>");
		out.println("      <w:dontGrowAutofit/>");
		out.println("      <w:useFELayout/>");
		out.println("    </w:compat>");
		out.println("    <wsp:rsids>");
		out.println("      <wsp:rsidRoot wsp:val='002F4E65'/>");
		out.println("      <wsp:rsid wsp:val='002F4E65'/>");
		out.println("      <wsp:rsid wsp:val='00CC44F5'/>");
		out.println("    </wsp:rsids>");
		out.println("  </w:docPr>");
		out.println("  <w:body>");

		List list = (List) session.getAttribute("relult");

		List group;
		for (int i = 0; i < list.size(); i++) {

			out
					.println("    <w:p wsp:rsidR='002F4E65' wsp:rsidRPr='00F902BF' wsp:rsidRDefault='002F4E65' wsp:rsidP='002F4E65'>");
			out.println("      <w:pPr>");
			out.println("        <w:jc w:val='center'/>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='32'/>");
			out.println("          <w:sz-cs w:val='32'/>");
			out.println("        </w:rPr>");
			out.println("      </w:pPr>");
			out.println("      <w:r>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='32'/>");
			out.println("          <w:sz-cs w:val='32'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>中華科技大學</w:t>");
			out.println("      </w:r>");
			out.println("      <w:r wsp:rsidRPr='00F902BF'>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='32'/>");
			out.println("          <w:sz-cs w:val='32'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>學生</w:t>");
			out.println("      </w:r>");
			out.println("      <w:proofErr w:type='gramStart'/>");
			out.println("      <w:r wsp:rsidRPr='00F902BF'>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='32'/>");
			out.println("          <w:sz-cs w:val='32'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>修讀跨領域</w:t>");
			out.println("      </w:r>");
			out.println("      <w:proofErr w:type='gramEnd'/>");
			out.println("      <w:r wsp:rsidRPr='00F902BF'>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='32'/>");
			out.println("          <w:sz-cs w:val='32'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>學程意願書</w:t>");
			out.println("      </w:r>");
			out.println("    </w:p>");
			out
					.println("    <w:p wsp:rsidR='002F4E65' wsp:rsidRDefault='002F4E65' wsp:rsidP='002F4E65'>");
			out.println("      <w:pPr>");
			out.println("        <w:rPr>");
			out.println("          <w:rFonts w:hint='fareast'/>");
			out.println("        </w:rPr>");
			out.println("      </w:pPr>");
			out.println("    </w:p>");
			out
					.println("    <w:p wsp:rsidR='002F4E65' wsp:rsidRDefault='002F4E65' wsp:rsidP='002F4E65'>");
			out.println("      <w:pPr>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("      </w:pPr>");
			out.println("      <w:r wsp:rsidRPr='00F902BF'>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>　　學</w:t>");
			out.println("      </w:r>");
			out.println("      <w:r>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>生"
					+ ((Map) list.get(i)).get("student_name") + "</w:t>");
			out.println("      </w:r>");
			out.println("      <w:proofErr w:type='gramStart'/>");
			out.println("      <w:r>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>（</w:t>");
			out.println("      </w:r>");
			out.println("      <w:proofErr w:type='gramEnd'/>");
			out.println("      <w:r>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>學號："
					+ ((Map) list.get(i)).get("student_no") + "）為日間部"
					+ ((Map) list.get(i)).get("ClassName")
					+ "學生，擬修讀下列跨領域學程，請學校教務行政單位協助輔導學生</w:t>");
			out.println("      </w:r>");
			out.println("      <w:proofErr w:type='gramStart'/>");
			out.println("      <w:r>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>修讀跨領域</w:t>");
			out.println("      </w:r>");
			out.println("      <w:proofErr w:type='gramEnd'/>");
			out.println("      <w:r>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>學程。目前有意修讀之跨領域學程如下：</w:t>");
			out.println("      </w:r>");
			out.println("    </w:p>");
			// System.out.println(list.get(i));
			group = (List) ((Map) list.get(i)).get("biGroup");

			for (int j = 0; j < group.size(); j++) {

				if (Float.parseFloat(((Map) group.get(j)).get("opt1")
						.toString()) > 0
						|| Float.parseFloat(((Map) group.get(j)).get("opt2")
								.toString()) > 0) {
					// System.out.println(((Map)group.get(j)));
					out
							.println("    <w:p wsp:rsidR='002F4E65' wsp:rsidRPr='00B40C74' wsp:rsidRDefault='002F4E65' wsp:rsidP='002F4E65'>");
					out.println("      <w:pPr>");
					out.println("        <w:rPr>");
					out.println("          <w:rFonts w:fareast='標楷體'/>");
					out.println("          <w:sz w:val='28'/>");
					out.println("          <w:sz-cs w:val='28'/>");
					out.println("        </w:rPr>");
					out.println("      </w:pPr>");
					out.println("      <w:r wsp:rsidRPr='00B40C74'>");
					out.println("        <w:rPr>");
					out.println("          <w:rFonts w:fareast='細明體'/>");
					out.println("          <w:sz w:val='48'/>");
					out.println("          <w:sz-cs w:val='28'/>");
					out.println("        </w:rPr>");
					out.println("        <w:t>□ </w:t>");
					out.println("      </w:r>");
					out.println("      <w:r>");
					out.println("        <w:rPr>");
					out
							.println("          <w:rFonts w:fareast='標楷體' w:hint='fareast'/>");
					out.println("          <w:sz w:val='28'/>");
					out.println("          <w:sz-cs w:val='28'/>");
					out.println("        </w:rPr>");
					out.println("        <w:t></w:t>");
					out.println("      </w:r>");
					out.println("      <w:proofErr w:type='spellStart'/>");
					out.println("      <w:proofErr w:type='gramStart'/>");
					out.println("      <w:r>");
					out.println("        <w:rPr>");
					out
							.println("          <w:rFonts w:fareast='標楷體' w:hint='fareast'/>");
					out.println("          <w:sz w:val='28'/>");
					out.println("          <w:sz-cs w:val='28'/>");
					out.println("        </w:rPr>");
					out.println("        <w:t>"
							+ ((Map) group.get(j)).get("cname") + "</w:t>");
					out.println("      </w:r>");
					out.println("      <w:proofErr w:type='spellEnd'/>");
					out.println("      <w:proofErr w:type='gramEnd'/>");
					out.println("    </w:p>");
				}

			}

			out
					.println("    <w:p wsp:rsidR='002F4E65' wsp:rsidRDefault='002F4E65' wsp:rsidP='002F4E65'>");
			out.println("      <w:pPr>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("      </w:pPr>");
			out.println("      <w:r>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>　　　　　　　　　　　　　　　　學生　　　　　　　　（簽名）</w:t>");
			out.println("      </w:r>");
			out.println("    </w:p>");
			out
					.println("    <w:p wsp:rsidR='002F4E65' wsp:rsidRDefault='002F4E65' wsp:rsidP='002F4E65'>");
			out.println("      <w:pPr>");
			out.println("        <w:ind w:left-chars='1875' w:left='4500'/>");
			out.println("        <w:jc w:val='distribute'/>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("      </w:pPr>");
			out.println("      <w:r>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>中華民國     年    月    日</w:t>");
			out.println("      </w:r>");
			out.println("    </w:p>");
			out
					.println("    <w:p wsp:rsidR='002F4E65' wsp:rsidRDefault='002F4E65' wsp:rsidP='002F4E65'>");
			out.println("      <w:pPr>");
			out.println("        <w:ind w:left-chars='1875' w:left='4500'/>");
			out.println("        <w:jc w:val='distribute'/>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("      </w:pPr>");
			out.println("    </w:p>");
			out
					.println("    <w:p wsp:rsidR='002F4E65' wsp:rsidRDefault='002F4E65' wsp:rsidP='002F4E65'>");
			out.println("      <w:pPr>");
			out.println("        <w:ind w:left-chars='1875' w:left='4500'/>");
			out.println("        <w:jc w:val='distribute'/>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("      </w:pPr>");
			out.println("    </w:p>");
			out
					.println("    <w:p wsp:rsidR='002F4E65' wsp:rsidRPr='00B40C74' wsp:rsidRDefault='002F4E65' wsp:rsidP='002F4E65'>");
			out.println("      <w:pPr>");
			out.println("        <w:ind w:left-chars='1875' w:left='4500'/>");
			out.println("        <w:jc w:val='distribute'/>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='Arial Unicode MS' w:fareast='Arial Unicode MS' w:h-ansi='Arial Unicode MS' w:cs='Arial Unicode MS' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='Arial Unicode MS'/>");
			out.println("          <w:sz w:val='28'/>");
			out.println("          <w:sz-cs w:val='28'/>");
			out.println("        </w:rPr>");
			out.println("      </w:pPr>");
			out.println("    </w:p>");
			out
					.println("    <w:p wsp:rsidR='00CC44F5' wsp:rsidRPr='002F4E65' wsp:rsidRDefault='00CC44F5'/>");
			out
					.println("    <w:sectPr wsp:rsidR='00CC44F5' wsp:rsidRPr='002F4E65' wsp:rsidSect='009D4A39'>");
			out.println("      <w:pgSz w:w='11906' w:h='16838'/>");
			out
					.println("      <w:pgMar w:top='1134' w:right='1701' w:bottom='1134' w:left='1701' w:header='851' w:footer='992' w:gutter='0'/>");
			out.println("      <w:cols w:space='425'/>");
			out.println("      <w:docGrid w:type='lines' w:line-pitch='360'/>");
			out.println("    </w:sectPr>");

			out.println("    <w:br w:type='page'/>");
		}

		out.println("  </w:body>");
		out.println("</w:wordDocument>");

		out.close();
	}
}
