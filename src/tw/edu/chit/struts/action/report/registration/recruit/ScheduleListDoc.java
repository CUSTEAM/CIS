package tw.edu.chit.struts.action.report.registration.recruit;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

public class ScheduleListDoc extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		response.setContentType("text/xml; charset=UTF-8");		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		PrintWriter out=response.getWriter();		
		response.setHeader("Content-disposition","attachment;filename=ScheduleDoc.doc");		
		
		out.println ("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
		out.println ("<?mso-application progid='Word.Document'?>");
		out.println ("<w:wordDocument xmlns:w='http://schemas.microsoft.com/office/word/2003/wordml' xmlns:v='urn:schemas-microsoft-com:vml' xmlns:w10='urn:schemas-microsoft-com:office:word' xmlns:sl='http://schemas.microsoft.com/schemaLibrary/2003/core' xmlns:aml='http://schemas.microsoft.com/aml/2001/core' xmlns:wx='http://schemas.microsoft.com/office/word/2003/auxHint' xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:dt='uuid:C2F41010-65B3-11d1-A29F-00AA00C14882' xmlns:wsp='http://schemas.microsoft.com/office/word/2003/wordml/sp2' xmlns:st1='urn:schemas-microsoft-com:office:smarttags' w:macrosPresent='no' w:embeddedObjPresent='no' w:ocxPresent='no' xml:space='preserve'>");
		out.println ("  <w:ignoreElements w:val='http://schemas.microsoft.com/office/word/2003/wordml/sp2'/>");
		out.println ("  <o:SmartTagType o:namespaceuri='urn:schemas-microsoft-com:office:smarttags' o:name='chsdate'/>");
		out.println ("  <o:DocumentProperties>");
		out.println ("    <o:Title>系別</o:Title>");
		out.println ("    <o:Author>JOHN</o:Author>");
		out.println ("    <o:LastAuthor>JOHN</o:LastAuthor>");
		out.println ("    <o:Revision>2</o:Revision>");
		out.println ("    <o:TotalTime>0</o:TotalTime>");
		out.println ("    <o:Created>2010-01-11T12:01:00Z</o:Created>");
		out.println ("    <o:LastSaved>2010-01-11T12:01:00Z</o:LastSaved>");
		out.println ("    <o:Pages>1</o:Pages>");
		out.println ("    <o:Words>27</o:Words>");
		out.println ("    <o:Characters>155</o:Characters>");
		out.println ("    <o:Company>CMT</o:Company>");
		out.println ("    <o:Lines>1</o:Lines>");
		out.println ("    <o:Paragraphs>1</o:Paragraphs>");
		out.println ("    <o:CharactersWithSpaces>181</o:CharactersWithSpaces>");
		out.println ("    <o:Version>11.0000</o:Version>");
		out.println ("  </o:DocumentProperties>");
		out.println ("  <w:fonts>");
		out.println ("    <w:defaultFonts w:ascii='Times New Roman' w:fareast='新細明體' w:h-ansi='Times New Roman' w:cs='Times New Roman'/>");
		out.println ("    <w:font w:name='新細明體'>");
		out.println ("      <w:altName w:val='PMingLiU'/>");
		out.println ("      <w:panose-1 w:val='02020300000000000000'/>");
		out.println ("      <w:charset w:val='88'/>");
		out.println ("      <w:family w:val='Roman'/>");
		out.println ("      <w:pitch w:val='variable'/>");
		out.println ("      <w:sig w:usb-0='00000003' w:usb-1='082E0000' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println ("    </w:font>");
		out.println ("    <w:font w:name='標楷體'>");
		out.println ("      <w:panose-1 w:val='03000509000000000000'/>");
		out.println ("      <w:charset w:val='88'/>");
		out.println ("      <w:family w:val='Script'/>");
		out.println ("      <w:pitch w:val='fixed'/>");
		out.println ("      <w:sig w:usb-0='00000003' w:usb-1='082E0000' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println ("    </w:font>");
		out.println ("    <w:font w:name='@標楷體'>");
		out.println ("      <w:panose-1 w:val='03000509000000000000'/>");
		out.println ("      <w:charset w:val='88'/>");
		out.println ("      <w:family w:val='Script'/>");
		out.println ("      <w:pitch w:val='fixed'/>");
		out.println ("      <w:sig w:usb-0='00000003' w:usb-1='082E0000' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println ("    </w:font>");
		out.println ("    <w:font w:name='@新細明體'>");
		out.println ("      <w:panose-1 w:val='02020300000000000000'/>");
		out.println ("      <w:charset w:val='88'/>");
		out.println ("      <w:family w:val='Roman'/>");
		out.println ("      <w:pitch w:val='variable'/>");
		out.println ("      <w:sig w:usb-0='00000003' w:usb-1='082E0000' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println ("    </w:font>");
		out.println ("  </w:fonts>");
		out.println ("  <w:styles>");
		out.println ("    <w:versionOfBuiltInStylenames w:val='4'/>");
		out.println ("    <w:latentStyles w:defLockedState='off' w:latentStyleCount='156'/>");
		out.println ("    <w:style w:type='paragraph' w:default='on' w:styleId='a'>");
		out.println ("      <w:name w:val='Normal'/>");
		out.println ("      <wx:uiName wx:val='內文'/>");
		out.println ("      <w:pPr>");
		out.println ("        <w:widowControl w:val='off'/>");
		out.println ("      </w:pPr>");
		out.println ("      <w:rPr>");
		out.println ("        <wx:font wx:val='Times New Roman'/>");
		out.println ("        <w:kern w:val='2'/>");
		out.println ("        <w:sz w:val='24'/>");
		out.println ("        <w:sz-cs w:val='24'/>");
		out.println ("        <w:lang w:val='EN-US' w:fareast='ZH-TW' w:bidi='AR-SA'/>");
		out.println ("      </w:rPr>");
		out.println ("    </w:style>");
		out.println ("    <w:style w:type='character' w:default='on' w:styleId='a0'>");
		out.println ("      <w:name w:val='Default Paragraph Font'/>");
		out.println ("      <wx:uiName wx:val='預設段落字型'/>");
		out.println ("      <w:semiHidden/>");
		out.println ("    </w:style>");
		out.println ("    <w:style w:type='table' w:default='on' w:styleId='a1'>");
		out.println ("      <w:name w:val='Normal Table'/>");
		out.println ("      <wx:uiName wx:val='表格內文'/>");
		out.println ("      <w:semiHidden/>");
		out.println ("      <w:rPr>");
		out.println ("        <wx:font wx:val='Times New Roman'/>");
		out.println ("      </w:rPr>");
		out.println ("      <w:tblPr>");
		out.println ("        <w:tblInd w:w='0' w:type='dxa'/>");
		out.println ("        <w:tblCellMar>");
		out.println ("          <w:top w:w='0' w:type='dxa'/>");
		out.println ("          <w:left w:w='108' w:type='dxa'/>");
		out.println ("          <w:bottom w:w='0' w:type='dxa'/>");
		out.println ("          <w:right w:w='108' w:type='dxa'/>");
		out.println ("        </w:tblCellMar>");
		out.println ("      </w:tblPr>");
		out.println ("    </w:style>");
		out.println ("    <w:style w:type='list' w:default='on' w:styleId='a2'>");
		out.println ("      <w:name w:val='No List'/>");
		out.println ("      <wx:uiName wx:val='無清單'/>");
		out.println ("      <w:semiHidden/>");
		out.println ("    </w:style>");
		out.println ("    <w:style w:type='character' w:styleId='1'>");
		out.println ("      <w:name w:val='內文1'/>");
		out.println ("      <w:basedOn w:val='a0'/>");
		out.println ("      <w:rsid w:val='00341655'/>");
		out.println ("      <w:rPr>");
		out.println ("        <w:rFonts w:fareast='標楷體'/>");
		out.println ("        <w:sz w:val='28'/>");
		out.println ("        <w:sz-cs w:val='28'/>");
		out.println ("      </w:rPr>");
		out.println ("    </w:style>");
		out.println ("    <w:style w:type='paragraph' w:styleId='a3'>");
		out.println ("      <w:name w:val='header'/>");
		out.println ("      <wx:uiName wx:val='頁首'/>");
		out.println ("      <w:basedOn w:val='a'/>");
		out.println ("      <w:rsid w:val='00234081'/>");
		out.println ("      <w:pPr>");
		out.println ("        <w:pStyle w:val='a3'/>");
		out.println ("        <w:tabs>");
		out.println ("          <w:tab w:val='center' w:pos='4153'/>");
		out.println ("          <w:tab w:val='right' w:pos='8306'/>");
		out.println ("        </w:tabs>");
		out.println ("        <w:snapToGrid w:val='off'/>");
		out.println ("      </w:pPr>");
		out.println ("      <w:rPr>");
		out.println ("        <wx:font wx:val='Times New Roman'/>");
		out.println ("        <w:sz w:val='20'/>");
		out.println ("        <w:sz-cs w:val='20'/>");
		out.println ("      </w:rPr>");
		out.println ("    </w:style>");
		out.println ("    <w:style w:type='paragraph' w:styleId='a4'>");
		out.println ("      <w:name w:val='footer'/>");
		out.println ("      <wx:uiName wx:val='頁尾'/>");
		out.println ("      <w:basedOn w:val='a'/>");
		out.println ("      <w:rsid w:val='00234081'/>");
		out.println ("      <w:pPr>");
		out.println ("        <w:pStyle w:val='a4'/>");
		out.println ("        <w:tabs>");
		out.println ("          <w:tab w:val='center' w:pos='4153'/>");
		out.println ("          <w:tab w:val='right' w:pos='8306'/>");
		out.println ("        </w:tabs>");
		out.println ("        <w:snapToGrid w:val='off'/>");
		out.println ("      </w:pPr>");
		out.println ("      <w:rPr>");
		out.println ("        <wx:font wx:val='Times New Roman'/>");
		out.println ("        <w:sz w:val='20'/>");
		out.println ("        <w:sz-cs w:val='20'/>");
		out.println ("      </w:rPr>");
		out.println ("    </w:style>");
		out.println ("    <w:style w:type='table' w:styleId='a5'>");
		out.println ("      <w:name w:val='Table Grid'/>");
		out.println ("      <wx:uiName wx:val='表格格線'/>");
		out.println ("      <w:basedOn w:val='a1'/>");
		out.println ("      <w:rsid w:val='00234081'/>");
		out.println ("      <w:pPr>");
		out.println ("        <w:widowControl w:val='off'/>");
		out.println ("      </w:pPr>");
		out.println ("      <w:rPr>");
		out.println ("        <wx:font wx:val='Times New Roman'/>");
		out.println ("      </w:rPr>");
		out.println ("      <w:tblPr>");
		out.println ("        <w:tblInd w:w='0' w:type='dxa'/>");
		out.println ("        <w:tblBorders>");
		out.println ("          <w:top w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
		out.println ("          <w:left w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
		out.println ("          <w:bottom w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
		out.println ("          <w:right w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
		out.println ("          <w:insideH w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
		out.println ("          <w:insideV w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
		out.println ("        </w:tblBorders>");
		out.println ("        <w:tblCellMar>");
		out.println ("          <w:top w:w='0' w:type='dxa'/>");
		out.println ("          <w:left w:w='108' w:type='dxa'/>");
		out.println ("          <w:bottom w:w='0' w:type='dxa'/>");
		out.println ("          <w:right w:w='108' w:type='dxa'/>");
		out.println ("        </w:tblCellMar>");
		out.println ("      </w:tblPr>");
		out.println ("    </w:style>");
		out.println ("  </w:styles>");
		out.println ("  <w:shapeDefaults>");
		out.println ("    <o:shapedefaults v:ext='edit' spidmax='2050'/>");
		out.println ("    <o:shapelayout v:ext='edit'>");
		out.println ("      <o:idmap v:ext='edit' data='1'/>");
		out.println ("    </o:shapelayout>");
		out.println ("  </w:shapeDefaults>");
		out.println ("  <w:docPr>");
		out.println ("    <w:view w:val='print'/>");
		out.println ("    <w:zoom w:val='best-fit' w:percent='121'/>");
		out.println ("    <w:doNotEmbedSystemFonts/>");
		out.println ("    <w:bordersDontSurroundHeader/>");
		out.println ("    <w:bordersDontSurroundFooter/>");
		out.println ("    <w:attachedTemplate w:val=''/>");
		out.println ("    <w:defaultTabStop w:val='480'/>");
		out.println ("    <w:displayHorizontalDrawingGridEvery w:val='0'/>");
		out.println ("    <w:displayVerticalDrawingGridEvery w:val='2'/>");
		out.println ("    <w:punctuationKerning/>");
		out.println ("    <w:characterSpacingControl w:val='CompressPunctuation'/>");
		out.println ("    <w:optimizeForBrowser/>");
		out.println ("    <w:validateAgainstSchema/>");
		out.println ("    <w:saveInvalidXML w:val='off'/>");
		out.println ("    <w:ignoreMixedContent w:val='off'/>");
		out.println ("    <w:alwaysShowPlaceholderText w:val='off'/>");
		out.println ("    <w:footnotePr>");
		out.println ("      <w:footnote w:type='separator'>");
		out.println ("        <w:p wsp:rsidR='00E03338' wsp:rsidRDefault='00E03338'>");
		out.println ("          <w:r>");
		out.println ("            <w:separator/>");
		out.println ("          </w:r>");
		out.println ("        </w:p>");
		out.println ("      </w:footnote>");
		out.println ("      <w:footnote w:type='continuation-separator'>");
		out.println ("        <w:p wsp:rsidR='00E03338' wsp:rsidRDefault='00E03338'>");
		out.println ("          <w:r>");
		out.println ("            <w:continuationSeparator/>");
		out.println ("          </w:r>");
		out.println ("        </w:p>");
		out.println ("      </w:footnote>");
		out.println ("    </w:footnotePr>");
		out.println ("    <w:endnotePr>");
		out.println ("      <w:endnote w:type='separator'>");
		out.println ("        <w:p wsp:rsidR='00E03338' wsp:rsidRDefault='00E03338'>");
		out.println ("          <w:r>");
		out.println ("            <w:separator/>");
		out.println ("          </w:r>");
		out.println ("        </w:p>");
		out.println ("      </w:endnote>");
		out.println ("      <w:endnote w:type='continuation-separator'>");
		out.println ("        <w:p wsp:rsidR='00E03338' wsp:rsidRDefault='00E03338'>");
		out.println ("          <w:r>");
		out.println ("            <w:continuationSeparator/>");
		out.println ("          </w:r>");
		out.println ("        </w:p>");
		out.println ("      </w:endnote>");
		out.println ("    </w:endnotePr>");
		out.println ("    <w:compat>");
		out.println ("      <w:spaceForUL/>");
		out.println ("      <w:balanceSingleByteDoubleByteWidth/>");
		out.println ("      <w:doNotLeaveBackslashAlone/>");
		out.println ("      <w:ulTrailSpace/>");
		out.println ("      <w:doNotExpandShiftReturn/>");
		out.println ("      <w:adjustLineHeightInTable/>");
		out.println ("      <w:breakWrappedTables/>");
		out.println ("      <w:snapToGridInCell/>");
		out.println ("      <w:wrapTextWithPunct/>");
		out.println ("      <w:useAsianBreakRules/>");
		out.println ("      <w:dontGrowAutofit/>");
		out.println ("      <w:useFELayout/>");
		out.println ("    </w:compat>");
		out.println ("    <wsp:rsids>");
		out.println ("      <wsp:rsidRoot wsp:val='00CA1418'/>");
		out.println ("      <wsp:rsid wsp:val='000073E2'/>");
		out.println ("      <wsp:rsid wsp:val='00024FFE'/>");
		out.println ("      <wsp:rsid wsp:val='00026B8A'/>");
		out.println ("      <wsp:rsid wsp:val='000279E1'/>");
		out.println ("      <wsp:rsid wsp:val='00030067'/>");
		out.println ("      <wsp:rsid wsp:val='000369F3'/>");
		out.println ("      <wsp:rsid wsp:val='0004150D'/>");
		out.println ("      <wsp:rsid wsp:val='00043BDE'/>");
		out.println ("      <wsp:rsid wsp:val='00074BAD'/>");
		out.println ("      <wsp:rsid wsp:val='0008425E'/>");
		out.println ("      <wsp:rsid wsp:val='00093910'/>");
		out.println ("      <wsp:rsid wsp:val='000B2698'/>");
		out.println ("      <wsp:rsid wsp:val='000B306A'/>");
		out.println ("      <wsp:rsid wsp:val='000B4E5C'/>");
		out.println ("      <wsp:rsid wsp:val='000C4CF1'/>");
		out.println ("      <wsp:rsid wsp:val='000D6353'/>");
		out.println ("      <wsp:rsid wsp:val='000E25D2'/>");
		out.println ("      <wsp:rsid wsp:val='000E682C'/>");
		out.println ("      <wsp:rsid wsp:val='000E7268'/>");
		out.println ("      <wsp:rsid wsp:val='00104B04'/>");
		out.println ("      <wsp:rsid wsp:val='001212A6'/>");
		out.println ("      <wsp:rsid wsp:val='00123558'/>");
		out.println ("      <wsp:rsid wsp:val='00126C3A'/>");
		out.println ("      <wsp:rsid wsp:val='0013388F'/>");
		out.println ("      <wsp:rsid wsp:val='00137F91'/>");
		out.println ("      <wsp:rsid wsp:val='00145E08'/>");
		out.println ("      <wsp:rsid wsp:val='00154DB3'/>");
		out.println ("      <wsp:rsid wsp:val='00162353'/>");
		out.println ("      <wsp:rsid wsp:val='00162729'/>");
		out.println ("      <wsp:rsid wsp:val='00163ABE'/>");
		out.println ("      <wsp:rsid wsp:val='001668FE'/>");
		out.println ("      <wsp:rsid wsp:val='001873E8'/>");
		out.println ("      <wsp:rsid wsp:val='00187FC2'/>");
		out.println ("      <wsp:rsid wsp:val='001B0AA9'/>");
		out.println ("      <wsp:rsid wsp:val='001B1F3B'/>");
		out.println ("      <wsp:rsid wsp:val='001B2DFD'/>");
		out.println ("      <wsp:rsid wsp:val='001D5382'/>");
		out.println ("      <wsp:rsid wsp:val='001E3A94'/>");
		out.println ("      <wsp:rsid wsp:val='001F2C11'/>");
		out.println ("      <wsp:rsid wsp:val='0021160B'/>");
		out.println ("      <wsp:rsid wsp:val='00226954'/>");
		out.println ("      <wsp:rsid wsp:val='00234081'/>");
		out.println ("      <wsp:rsid wsp:val='002354B7'/>");
		out.println ("      <wsp:rsid wsp:val='00241244'/>");
		out.println ("      <wsp:rsid wsp:val='002548F5'/>");
		out.println ("      <wsp:rsid wsp:val='00267E27'/>");
		out.println ("      <wsp:rsid wsp:val='0027028E'/>");
		out.println ("      <wsp:rsid wsp:val='00271F99'/>");
		out.println ("      <wsp:rsid wsp:val='00281485'/>");
		out.println ("      <wsp:rsid wsp:val='002872E9'/>");
		out.println ("      <wsp:rsid wsp:val='00290807'/>");
		out.println ("      <wsp:rsid wsp:val='0029093F'/>");
		out.println ("      <wsp:rsid wsp:val='00292640'/>");
		out.println ("      <wsp:rsid wsp:val='00294CEF'/>");
		out.println ("      <wsp:rsid wsp:val='00296FF4'/>");
		out.println ("      <wsp:rsid wsp:val='002B37E3'/>");
		out.println ("      <wsp:rsid wsp:val='002B5E9C'/>");
		out.println ("      <wsp:rsid wsp:val='002C1FAE'/>");
		out.println ("      <wsp:rsid wsp:val='002C7FDB'/>");
		out.println ("      <wsp:rsid wsp:val='002D42D6'/>");
		out.println ("      <wsp:rsid wsp:val='002E0C8D'/>");
		out.println ("      <wsp:rsid wsp:val='002E20BD'/>");
		out.println ("      <wsp:rsid wsp:val='002E47B6'/>");
		out.println ("      <wsp:rsid wsp:val='002F3490'/>");
		out.println ("      <wsp:rsid wsp:val='00311BD6'/>");
		out.println ("      <wsp:rsid wsp:val='00314F8D'/>");
		out.println ("      <wsp:rsid wsp:val='00315D1D'/>");
		out.println ("      <wsp:rsid wsp:val='00317D0D'/>");
		out.println ("      <wsp:rsid wsp:val='003258CA'/>");
		out.println ("      <wsp:rsid wsp:val='00325E13'/>");
		out.println ("      <wsp:rsid wsp:val='00332913'/>");
		out.println ("      <wsp:rsid wsp:val='0033671F'/>");
		out.println ("      <wsp:rsid wsp:val='00341655'/>");
		out.println ("      <wsp:rsid wsp:val='00345B49'/>");
		out.println ("      <wsp:rsid wsp:val='00345E03'/>");
		out.println ("      <wsp:rsid wsp:val='00352F73'/>");
		out.println ("      <wsp:rsid wsp:val='003623F7'/>");
		out.println ("      <wsp:rsid wsp:val='00362BC1'/>");
		out.println ("      <wsp:rsid wsp:val='0037317E'/>");
		out.println ("      <wsp:rsid wsp:val='0037376E'/>");
		out.println ("      <wsp:rsid wsp:val='00383F70'/>");
		out.println ("      <wsp:rsid wsp:val='003847C5'/>");
		out.println ("      <wsp:rsid wsp:val='00394A9F'/>");
		out.println ("      <wsp:rsid wsp:val='003A6CD2'/>");
		out.println ("      <wsp:rsid wsp:val='003A7BD0'/>");
		out.println ("      <wsp:rsid wsp:val='003C1899'/>");
		out.println ("      <wsp:rsid wsp:val='003C4533'/>");
		out.println ("      <wsp:rsid wsp:val='003D057D'/>");
		out.println ("      <wsp:rsid wsp:val='003E15FA'/>");
		out.println ("      <wsp:rsid wsp:val='003E6C61'/>");
		out.println ("      <wsp:rsid wsp:val='003F2661'/>");
		out.println ("      <wsp:rsid wsp:val='004015C0'/>");
		out.println ("      <wsp:rsid wsp:val='00412B27'/>");
		out.println ("      <wsp:rsid wsp:val='00421EAE'/>");
		out.println ("      <wsp:rsid wsp:val='00425A48'/>");
		out.println ("      <wsp:rsid wsp:val='00426803'/>");
		out.println ("      <wsp:rsid wsp:val='0043321B'/>");
		out.println ("      <wsp:rsid wsp:val='00440471'/>");
		out.println ("      <wsp:rsid wsp:val='0044571A'/>");
		out.println ("      <wsp:rsid wsp:val='00451571'/>");
		out.println ("      <wsp:rsid wsp:val='00455BC9'/>");
		out.println ("      <wsp:rsid wsp:val='0045648A'/>");
		out.println ("      <wsp:rsid wsp:val='00465996'/>");
		out.println ("      <wsp:rsid wsp:val='00493134'/>");
		out.println ("      <wsp:rsid wsp:val='00497F59'/>");
		out.println ("      <wsp:rsid wsp:val='004A24B5'/>");
		out.println ("      <wsp:rsid wsp:val='004A4AA7'/>");
		out.println ("      <wsp:rsid wsp:val='004B1525'/>");
		out.println ("      <wsp:rsid wsp:val='004B2881'/>");
		out.println ("      <wsp:rsid wsp:val='004B571C'/>");
		out.println ("      <wsp:rsid wsp:val='004C356B'/>");
		out.println ("      <wsp:rsid wsp:val='004C55F7'/>");
		out.println ("      <wsp:rsid wsp:val='004C596F'/>");
		out.println ("      <wsp:rsid wsp:val='004E52AB'/>");
		out.println ("      <wsp:rsid wsp:val='004F3090'/>");
		out.println ("      <wsp:rsid wsp:val='0050158D'/>");
		out.println ("      <wsp:rsid wsp:val='005151DF'/>");
		out.println ("      <wsp:rsid wsp:val='0052571B'/>");
		out.println ("      <wsp:rsid wsp:val='00525F82'/>");
		out.println ("      <wsp:rsid wsp:val='0053660C'/>");
		out.println ("      <wsp:rsid wsp:val='00545058'/>");
		out.println ("      <wsp:rsid wsp:val='00546262'/>");
		out.println ("      <wsp:rsid wsp:val='00552457'/>");
		out.println ("      <wsp:rsid wsp:val='00566DCE'/>");
		out.println ("      <wsp:rsid wsp:val='00594AC5'/>");
		out.println ("      <wsp:rsid wsp:val='005A7DF2'/>");
		out.println ("      <wsp:rsid wsp:val='005B44B7'/>");
		out.println ("      <wsp:rsid wsp:val='005B50F1'/>");
		out.println ("      <wsp:rsid wsp:val='005C7874'/>");
		out.println ("      <wsp:rsid wsp:val='005E0107'/>");
		out.println ("      <wsp:rsid wsp:val='005E73EF'/>");
		out.println ("      <wsp:rsid wsp:val='005F7039'/>");
		out.println ("      <wsp:rsid wsp:val='006004B9'/>");
		out.println ("      <wsp:rsid wsp:val='006037A1'/>");
		out.println ("      <wsp:rsid wsp:val='00603A75'/>");
		out.println ("      <wsp:rsid wsp:val='00603E90'/>");
		out.println ("      <wsp:rsid wsp:val='00610245'/>");
		out.println ("      <wsp:rsid wsp:val='00612865'/>");
		out.println ("      <wsp:rsid wsp:val='00612FC0'/>");
		out.println ("      <wsp:rsid wsp:val='00617793'/>");
		out.println ("      <wsp:rsid wsp:val='00624543'/>");
		out.println ("      <wsp:rsid wsp:val='00626E54'/>");
		out.println ("      <wsp:rsid wsp:val='00633A96'/>");
		out.println ("      <wsp:rsid wsp:val='00640BBD'/>");
		out.println ("      <wsp:rsid wsp:val='006555B4'/>");
		out.println ("      <wsp:rsid wsp:val='00672169'/>");
		out.println ("      <wsp:rsid wsp:val='006814B5'/>");
		out.println ("      <wsp:rsid wsp:val='00692EA1'/>");
		out.println ("      <wsp:rsid wsp:val='00693DC9'/>");
		out.println ("      <wsp:rsid wsp:val='006A2646'/>");
		out.println ("      <wsp:rsid wsp:val='006A333D'/>");
		out.println ("      <wsp:rsid wsp:val='006A4F24'/>");
		out.println ("      <wsp:rsid wsp:val='006A5935'/>");
		out.println ("      <wsp:rsid wsp:val='006B4933'/>");
		out.println ("      <wsp:rsid wsp:val='006C3049'/>");
		out.println ("      <wsp:rsid wsp:val='006C4E8C'/>");
		out.println ("      <wsp:rsid wsp:val='006D3761'/>");
		out.println ("      <wsp:rsid wsp:val='006E05D1'/>");
		out.println ("      <wsp:rsid wsp:val='006E316E'/>");
		out.println ("      <wsp:rsid wsp:val='006F1B84'/>");
		out.println ("      <wsp:rsid wsp:val='007133D6'/>");
		out.println ("      <wsp:rsid wsp:val='00721612'/>");
		out.println ("      <wsp:rsid wsp:val='00724C62'/>");
		out.println ("      <wsp:rsid wsp:val='007276D8'/>");
		out.println ("      <wsp:rsid wsp:val='00731A43'/>");
		out.println ("      <wsp:rsid wsp:val='007322DD'/>");
		out.println ("      <wsp:rsid wsp:val='0074166E'/>");
		out.println ("      <wsp:rsid wsp:val='00762F07'/>");
		out.println ("      <wsp:rsid wsp:val='00763CD2'/>");
		out.println ("      <wsp:rsid wsp:val='007740E7'/>");
		out.println ("      <wsp:rsid wsp:val='0077597D'/>");
		out.println ("      <wsp:rsid wsp:val='007912BD'/>");
		out.println ("      <wsp:rsid wsp:val='007A0832'/>");
		out.println ("      <wsp:rsid wsp:val='007A1D6F'/>");
		out.println ("      <wsp:rsid wsp:val='007A1E81'/>");
		out.println ("      <wsp:rsid wsp:val='007A4998'/>");
		out.println ("      <wsp:rsid wsp:val='007A643C'/>");
		out.println ("      <wsp:rsid wsp:val='007B2206'/>");
		out.println ("      <wsp:rsid wsp:val='007D6F01'/>");
		out.println ("      <wsp:rsid wsp:val='007E4CB1'/>");
		out.println ("      <wsp:rsid wsp:val='007E5A30'/>");
		out.println ("      <wsp:rsid wsp:val='007F5602'/>");
		out.println ("      <wsp:rsid wsp:val='007F6387'/>");
		out.println ("      <wsp:rsid wsp:val='00804DA3'/>");
		out.println ("      <wsp:rsid wsp:val='00806075'/>");
		out.println ("      <wsp:rsid wsp:val='008177F8'/>");
		out.println ("      <wsp:rsid wsp:val='00824931'/>");
		out.println ("      <wsp:rsid wsp:val='008440C8'/>");
		out.println ("      <wsp:rsid wsp:val='00846061'/>");
		out.println ("      <wsp:rsid wsp:val='008507CA'/>");
		out.println ("      <wsp:rsid wsp:val='008666B7'/>");
		out.println ("      <wsp:rsid wsp:val='0088287B'/>");
		out.println ("      <wsp:rsid wsp:val='008A2148'/>");
		out.println ("      <wsp:rsid wsp:val='008A3038'/>");
		out.println ("      <wsp:rsid wsp:val='008A3682'/>");
		out.println ("      <wsp:rsid wsp:val='008B15F9'/>");
		out.println ("      <wsp:rsid wsp:val='008B4F93'/>");
		out.println ("      <wsp:rsid wsp:val='008B71F7'/>");
		out.println ("      <wsp:rsid wsp:val='008C22E2'/>");
		out.println ("      <wsp:rsid wsp:val='008C2B0E'/>");
		out.println ("      <wsp:rsid wsp:val='008D4626'/>");
		out.println ("      <wsp:rsid wsp:val='008D4F3C'/>");
		out.println ("      <wsp:rsid wsp:val='008E0B5C'/>");
		out.println ("      <wsp:rsid wsp:val='008E6299'/>");
		out.println ("      <wsp:rsid wsp:val='008E6403'/>");
		out.println ("      <wsp:rsid wsp:val='008E6C9C'/>");
		out.println ("      <wsp:rsid wsp:val='008E7E3F'/>");
		out.println ("      <wsp:rsid wsp:val='008F1A7A'/>");
		out.println ("      <wsp:rsid wsp:val='008F2CD8'/>");
		out.println ("      <wsp:rsid wsp:val='008F569E'/>");
		out.println ("      <wsp:rsid wsp:val='009017BD'/>");
		out.println ("      <wsp:rsid wsp:val='00911992'/>");
		out.println ("      <wsp:rsid wsp:val='00930F46'/>");
		out.println ("      <wsp:rsid wsp:val='00945AE1'/>");
		out.println ("      <wsp:rsid wsp:val='00952D72'/>");
		out.println ("      <wsp:rsid wsp:val='00971012'/>");
		out.println ("      <wsp:rsid wsp:val='00972B34'/>");
		out.println ("      <wsp:rsid wsp:val='009750E9'/>");
		out.println ("      <wsp:rsid wsp:val='00983E4B'/>");
		out.println ("      <wsp:rsid wsp:val='009860A1'/>");
		out.println ("      <wsp:rsid wsp:val='00991E95'/>");
		out.println ("      <wsp:rsid wsp:val='0099451D'/>");
		out.println ("      <wsp:rsid wsp:val='009A666F'/>");
		out.println ("      <wsp:rsid wsp:val='009B0B3E'/>");
		out.println ("      <wsp:rsid wsp:val='009B3DB2'/>");
		out.println ("      <wsp:rsid wsp:val='009B7578'/>");
		out.println ("      <wsp:rsid wsp:val='009B7FAC'/>");
		out.println ("      <wsp:rsid wsp:val='009C241E'/>");
		out.println ("      <wsp:rsid wsp:val='009C68AE'/>");
		out.println ("      <wsp:rsid wsp:val='009D25A4'/>");
		out.println ("      <wsp:rsid wsp:val='009D2865'/>");
		out.println ("      <wsp:rsid wsp:val='009D3574'/>");
		out.println ("      <wsp:rsid wsp:val='009D696C'/>");
		out.println ("      <wsp:rsid wsp:val='00A07648'/>");
		out.println ("      <wsp:rsid wsp:val='00A110E8'/>");
		out.println ("      <wsp:rsid wsp:val='00A214BD'/>");
		out.println ("      <wsp:rsid wsp:val='00A2798C'/>");
		out.println ("      <wsp:rsid wsp:val='00A515FF'/>");
		out.println ("      <wsp:rsid wsp:val='00A569BD'/>");
		out.println ("      <wsp:rsid wsp:val='00A601CC'/>");
		out.println ("      <wsp:rsid wsp:val='00A70BD5'/>");
		out.println ("      <wsp:rsid wsp:val='00A71367'/>");
		out.println ("      <wsp:rsid wsp:val='00A8129E'/>");
		out.println ("      <wsp:rsid wsp:val='00A85DFD'/>");
		out.println ("      <wsp:rsid wsp:val='00A86F1F'/>");
		out.println ("      <wsp:rsid wsp:val='00A92F3D'/>");
		out.println ("      <wsp:rsid wsp:val='00AA0969'/>");
		out.println ("      <wsp:rsid wsp:val='00AA5AB5'/>");
		out.println ("      <wsp:rsid wsp:val='00AB3BB7'/>");
		out.println ("      <wsp:rsid wsp:val='00AD1E72'/>");
		out.println ("      <wsp:rsid wsp:val='00AD26E3'/>");
		out.println ("      <wsp:rsid wsp:val='00AE3862'/>");
		out.println ("      <wsp:rsid wsp:val='00AF0CF1'/>");
		out.println ("      <wsp:rsid wsp:val='00B14BC5'/>");
		out.println ("      <wsp:rsid wsp:val='00B25BB0'/>");
		out.println ("      <wsp:rsid wsp:val='00B26861'/>");
		out.println ("      <wsp:rsid wsp:val='00B3566E'/>");
		out.println ("      <wsp:rsid wsp:val='00B43C38'/>");
		out.println ("      <wsp:rsid wsp:val='00B46519'/>");
		out.println ("      <wsp:rsid wsp:val='00B76368'/>");
		out.println ("      <wsp:rsid wsp:val='00B80E46'/>");
		out.println ("      <wsp:rsid wsp:val='00B85D1B'/>");
		out.println ("      <wsp:rsid wsp:val='00BA27F9'/>");
		out.println ("      <wsp:rsid wsp:val='00BB4603'/>");
		out.println ("      <wsp:rsid wsp:val='00BC0619'/>");
		out.println ("      <wsp:rsid wsp:val='00BC261B'/>");
		out.println ("      <wsp:rsid wsp:val='00BC5E99'/>");
		out.println ("      <wsp:rsid wsp:val='00BD6B91'/>");
		out.println ("      <wsp:rsid wsp:val='00BE2045'/>");
		out.println ("      <wsp:rsid wsp:val='00BE21A5'/>");
		out.println ("      <wsp:rsid wsp:val='00BE415C'/>");
		out.println ("      <wsp:rsid wsp:val='00BF271A'/>");
		out.println ("      <wsp:rsid wsp:val='00BF4019'/>");
		out.println ("      <wsp:rsid wsp:val='00C0639A'/>");
		out.println ("      <wsp:rsid wsp:val='00C15716'/>");
		out.println ("      <wsp:rsid wsp:val='00C16DE6'/>");
		out.println ("      <wsp:rsid wsp:val='00C17C3D'/>");
		out.println ("      <wsp:rsid wsp:val='00C4386F'/>");
		out.println ("      <wsp:rsid wsp:val='00C449C1'/>");
		out.println ("      <wsp:rsid wsp:val='00C621F8'/>");
		out.println ("      <wsp:rsid wsp:val='00C65580'/>");
		out.println ("      <wsp:rsid wsp:val='00C76902'/>");
		out.println ("      <wsp:rsid wsp:val='00C82C3A'/>");
		out.println ("      <wsp:rsid wsp:val='00C94B23'/>");
		out.println ("      <wsp:rsid wsp:val='00CA1418'/>");
		out.println ("      <wsp:rsid wsp:val='00CA35B9'/>");
		out.println ("      <wsp:rsid wsp:val='00CA754D'/>");
		out.println ("      <wsp:rsid wsp:val='00CB4467'/>");
		out.println ("      <wsp:rsid wsp:val='00CB448E'/>");
		out.println ("      <wsp:rsid wsp:val='00CB44AB'/>");
		out.println ("      <wsp:rsid wsp:val='00CC08A0'/>");
		out.println ("      <wsp:rsid wsp:val='00CC740A'/>");
		out.println ("      <wsp:rsid wsp:val='00CD0F4F'/>");
		out.println ("      <wsp:rsid wsp:val='00CE0070'/>");
		out.println ("      <wsp:rsid wsp:val='00CF3F81'/>");
		out.println ("      <wsp:rsid wsp:val='00D02A93'/>");
		out.println ("      <wsp:rsid wsp:val='00D05FF4'/>");
		out.println ("      <wsp:rsid wsp:val='00D1619F'/>");
		out.println ("      <wsp:rsid wsp:val='00D25D81'/>");
		out.println ("      <wsp:rsid wsp:val='00D25E31'/>");
		out.println ("      <wsp:rsid wsp:val='00D33B76'/>");
		out.println ("      <wsp:rsid wsp:val='00D363FA'/>");
		out.println ("      <wsp:rsid wsp:val='00D54E64'/>");
		out.println ("      <wsp:rsid wsp:val='00D5667C'/>");
		out.println ("      <wsp:rsid wsp:val='00D624C2'/>");
		out.println ("      <wsp:rsid wsp:val='00D7016D'/>");
		out.println ("      <wsp:rsid wsp:val='00D75C21'/>");
		out.println ("      <wsp:rsid wsp:val='00D81462'/>");
		out.println ("      <wsp:rsid wsp:val='00D843BF'/>");
		out.println ("      <wsp:rsid wsp:val='00DA6215'/>");
		out.println ("      <wsp:rsid wsp:val='00DB0991'/>");
		out.println ("      <wsp:rsid wsp:val='00DB1ADF'/>");
		out.println ("      <wsp:rsid wsp:val='00DB6BC0'/>");
		out.println ("      <wsp:rsid wsp:val='00DC1223'/>");
		out.println ("      <wsp:rsid wsp:val='00DD202B'/>");
		out.println ("      <wsp:rsid wsp:val='00DE76BF'/>");
		out.println ("      <wsp:rsid wsp:val='00E00703'/>");
		out.println ("      <wsp:rsid wsp:val='00E03338'/>");
		out.println ("      <wsp:rsid wsp:val='00E03854'/>");
		out.println ("      <wsp:rsid wsp:val='00E039C3'/>");
		out.println ("      <wsp:rsid wsp:val='00E04D60'/>");
		out.println ("      <wsp:rsid wsp:val='00E123AD'/>");
		out.println ("      <wsp:rsid wsp:val='00E13B5A'/>");
		out.println ("      <wsp:rsid wsp:val='00E154B1'/>");
		out.println ("      <wsp:rsid wsp:val='00E203A4'/>");
		out.println ("      <wsp:rsid wsp:val='00E20BA9'/>");
		out.println ("      <wsp:rsid wsp:val='00E33452'/>");
		out.println ("      <wsp:rsid wsp:val='00E368C4'/>");
		out.println ("      <wsp:rsid wsp:val='00E44714'/>");
		out.println ("      <wsp:rsid wsp:val='00E515C6'/>");
		out.println ("      <wsp:rsid wsp:val='00E63F77'/>");
		out.println ("      <wsp:rsid wsp:val='00E725D5'/>");
		out.println ("      <wsp:rsid wsp:val='00E740F9'/>");
		out.println ("      <wsp:rsid wsp:val='00E775A1'/>");
		out.println ("      <wsp:rsid wsp:val='00E80F42'/>");
		out.println ("      <wsp:rsid wsp:val='00EA2559'/>");
		out.println ("      <wsp:rsid wsp:val='00EA45AB'/>");
		out.println ("      <wsp:rsid wsp:val='00EB078A'/>");
		out.println ("      <wsp:rsid wsp:val='00EC0E00'/>");
		out.println ("      <wsp:rsid wsp:val='00EC3F0E'/>");
		out.println ("      <wsp:rsid wsp:val='00EE0007'/>");
		out.println ("      <wsp:rsid wsp:val='00EE2EB4'/>");
		out.println ("      <wsp:rsid wsp:val='00EE6A39'/>");
		out.println ("      <wsp:rsid wsp:val='00F0467F'/>");
		out.println ("      <wsp:rsid wsp:val='00F117E0'/>");
		out.println ("      <wsp:rsid wsp:val='00F16657'/>");
		out.println ("      <wsp:rsid wsp:val='00F275E1'/>");
		out.println ("      <wsp:rsid wsp:val='00F3126B'/>");
		out.println ("      <wsp:rsid wsp:val='00F3200F'/>");
		out.println ("      <wsp:rsid wsp:val='00F32A4B'/>");
		out.println ("      <wsp:rsid wsp:val='00F33666'/>");
		out.println ("      <wsp:rsid wsp:val='00F3723C'/>");
		out.println ("      <wsp:rsid wsp:val='00F37549'/>");
		out.println ("      <wsp:rsid wsp:val='00F50001'/>");
		out.println ("      <wsp:rsid wsp:val='00F52085'/>");
		out.println ("      <wsp:rsid wsp:val='00F813B0'/>");
		out.println ("      <wsp:rsid wsp:val='00F840A4'/>");
		out.println ("      <wsp:rsid wsp:val='00FA05F4'/>");
		out.println ("      <wsp:rsid wsp:val='00FC284B'/>");
		out.println ("      <wsp:rsid wsp:val='00FC7284'/>");
		out.println ("      <wsp:rsid wsp:val='00FE36FF'/>");
		out.println ("      <wsp:rsid wsp:val='00FF34FB'/>");
		out.println ("      <wsp:rsid wsp:val='00FF7056'/>");
		out.println ("    </wsp:rsids>");
		out.println ("  </w:docPr>");
		out.println ("  <w:body>");
		out.println ("    <wx:sect>");
		out.println ("      <w:tbl>");
		out.println ("        <w:tblPr>");
		out.println ("          <w:tblStyle w:val='a5'/>");
		out.println ("          <w:tblW w:w='14328' w:type='dxa'/>");
		out.println ("          <w:tblLayout w:type='Fixed'/>");
		out.println ("          <w:tblLook w:val='01E0'/>");
		out.println ("        </w:tblPr>");
		out.println ("        <w:tblGrid>");
		out.println ("          <w:gridCol w:w='2088'/>");
		out.println ("          <w:gridCol w:w='2700'/>");
		out.println ("          <w:gridCol w:w='1080'/>");
		out.println ("          <w:gridCol w:w='2520'/>");
		out.println ("          <w:gridCol w:w='3060'/>");
		out.println ("          <w:gridCol w:w='2880'/>");
		out.println ("        </w:tblGrid>");
		
		
		
		
		
		
		
		
		
		
		printColumn(out);//表頭
		
		
		
		
		
		
		
		String ScheduleOid;
		
		StringBuilder sb;
		
		//String units;
		
		
		HttpSession session = request.getSession(false);
		List s=(List)session.getAttribute("scheduleChecks");
		
		for(int i=0; i<s.size(); i++){
		
		
		
		out.println ("        <w:tr wsp:rsidR='00972B34' wsp:rsidRPr='00626E54' wsp:rsidTr='00972B34'>");
		out.println ("          <w:trPr>");
		out.println ("            <w:trHeight w:val='296'/>");
		out.println ("          </w:trPr>");
		out.println ("          <w:tc>");
		out.println ("            <w:tcPr>");
		out.println ("              <w:tcW w:w='2088' w:type='dxa'/>");
		out.println ("            </w:tcPr>");
		out.println ("            <w:p wsp:rsidR='00972B34' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00972B34' wsp:rsidP='00602825'>");
		out.println ("              <w:pPr>");
		out.println ("                <w:jc w:val='both'/>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:hint='fareast'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		out.println ("              </w:pPr>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		
		
		ScheduleOid=((Map)s.get(i)).get("Oid").toString();
		List list=manager.ezGetBy("SELECT c.name FROM Recruit_schedule_unit rsu, code5 c WHERE c.idno=rsu.unit_id AND " +
				"(c.category='DeptGroup' OR c.category='Dept')AND rsu.schedule_oid="+ScheduleOid);
		sb=new StringBuilder();
		for(int j=0; j<list.size(); j++){
			sb.append(  ((Map)list.get(j)).get("name").toString());
		}		
		if(sb.length()<=8){
			out.println ("                <w:t>"+sb+"</w:t>");
		}else{
			out.println ("                <w:t>"+sb.substring(0, 8)+"</w:t>");
		}	
		
		out.println ("              </w:r>");
		out.println ("              <w:r>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		out.println ("                <w:t>…</w:t>");
		out.println ("              </w:r>");
		out.println ("            </w:p>");
		out.println ("          </w:tc>");
		out.println ("          <w:tc>");
		out.println ("            <w:tcPr>");
		out.println ("              <w:tcW w:w='2700' w:type='dxa'/>");
		out.println ("            </w:tcPr>");
		out.println ("            <w:p wsp:rsidR='00972B34' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00972B34' wsp:rsidP='00602825'>");
		out.println ("              <w:pPr>");
		out.println ("                <w:jc w:val='both'/>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:hint='fareast'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		out.println ("              </w:pPr>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		
		sb=new StringBuilder();
		try{
			sb.append(((Map)s.get(i)).get("itemName").toString());
		}catch(Exception e){
			sb.append("");
		}
		if(sb.length()<=10){
			out.println ("                <w:t>"+sb+"</w:t>");
		}else{
			out.println ("                <w:t>"+sb.substring(0, 10)+"</w:t>");
		}		
		
		out.println ("              </w:r>");
		out.println ("              <w:r>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		out.println ("                <w:t>…</w:t>");
		out.println ("              </w:r>");
		out.println ("            </w:p>");
		out.println ("          </w:tc>");
		out.println ("          <w:tc>");
		out.println ("            <w:tcPr>");
		out.println ("              <w:tcW w:w='1080' w:type='dxa'/>");
		out.println ("            </w:tcPr>");
		out.println ("            <w:p wsp:rsidR='00972B34' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00972B34' wsp:rsidP='00602825'>");
		out.println ("              <w:pPr>");
		out.println ("                <w:jc w:val='center'/>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		out.println ("              </w:pPr>");
		out.println ("              <st1:chsdate Year='1998' Month='12' Day='31' IsLunarDate='False' IsROCDate='False' w:st='on'>");
		out.println ("                <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                  <w:rPr>");
		out.println ("                    <w:rFonts w:fareast='標楷體'/>");
		out.println ("                    <w:sz w:val='20'/>");
		out.println ("                    <w:sz-cs w:val='20'/>");
		out.println ("                  </w:rPr>");		
		
		try{
			out.println ("                  <w:t>"+manager.convertDate(((Map)s.get(i)).get("someday").toString())+"</w:t>");
		}catch(Exception e){
			out.println ("                  <w:t>日期未定</w:t>");
		}		
		
		out.println ("                </w:r>");
		out.println ("              </st1:chsdate>");
		out.println ("            </w:p>");
		out.println ("          </w:tc>");
		out.println ("          <w:tc>");
		out.println ("            <w:tcPr>");
		out.println ("              <w:tcW w:w='2520' w:type='dxa'/>");
		out.println ("            </w:tcPr>");
		out.println ("            <w:p wsp:rsidR='00972B34' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00972B34' wsp:rsidP='00602825'>");
		out.println ("              <w:pPr>");
		out.println ("                <w:jc w:val='both'/>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		out.println ("              </w:pPr>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		
		
		
		
		
		
		sb=new StringBuilder();
		try{
			sb.append(((Map)s.get(i)).get("schoolName").toString());
		}catch(Exception e){
			sb.append("");
		}
		if(sb.length()<=9){
			out.println ("                <w:t>"+sb+"</w:t>");
		}else{
			out.println ("                <w:t>"+sb.substring(0, 9)+"</w:t>");
		}
		
		//out.println ("                <w:t>"+sb+"</w:t>");
		
		
		
		
		out.println ("              </w:r>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		out.println ("                <w:t>...</w:t>");
		out.println ("              </w:r>");
		out.println ("            </w:p>");
		out.println ("          </w:tc>");
		out.println ("          <w:tc>");
		out.println ("            <w:tcPr>");
		out.println ("              <w:tcW w:w='3060' w:type='dxa'/>");
		out.println ("            </w:tcPr>");
		out.println ("            <w:p wsp:rsidR='00972B34' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00972B34' wsp:rsidP='00602825'>");
		out.println ("              <w:pPr>");
		out.println ("                <w:jc w:val='both'/>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:hint='fareast'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		out.println ("              </w:pPr>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:hint='fareast'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		
		
		sb=new StringBuilder();
		try{
			sb.append(((Map)s.get(i)).get("staff").toString());
		}catch(Exception e){
			sb.append("");
		}
		if(sb.length()<=12){
			out.println ("                <w:t>"+sb+"</w:t>");
		}else{
			out.println ("                <w:t>"+sb.substring(0, 12)+"</w:t>");
		}		
		
		//out.println ("                <w:t>一二三四五六七八九十一二三</w:t>");
		
		
		out.println ("              </w:r>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		out.println ("                <w:t>…</w:t>");
		out.println ("              </w:r>");
		out.println ("            </w:p>");
		out.println ("          </w:tc>");
		out.println ("          <w:tc>");
		out.println ("            <w:tcPr>");
		out.println ("              <w:tcW w:w='2880' w:type='dxa'/>");
		out.println ("            </w:tcPr>");
		out.println ("            <w:p wsp:rsidR='00972B34' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00972B34' wsp:rsidP='00602825'>");
		out.println ("              <w:pPr>");
		out.println ("                <w:jc w:val='both'/>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:hint='fareast'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		out.println ("              </w:pPr>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:hint='fareast'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		
		sb=new StringBuilder();
		try{
			sb.append(((Map)s.get(i)).get("work_descript").toString());
		}catch(Exception e){
			sb.append("");
		}
		if(sb.length()<=11){
			out.println ("                <w:t>"+sb+"</w:t>");
		}else{
			out.println ("                <w:t>"+sb.substring(0, 11)+"</w:t>");
		}		
		
		
		
		
		out.println ("              </w:r>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體'/>");
		out.println ("                  <w:sz w:val='20'/>");
		out.println ("                  <w:sz-cs w:val='20'/>");
		out.println ("                </w:rPr>");
		out.println ("                <w:t>…</w:t>");
		out.println ("              </w:r>");
		out.println ("            </w:p>");
		out.println ("          </w:tc>");
		out.println ("        </w:tr>");
		
		
		
		
		
		
		
		
		
		
		
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		out.println ("      </w:tbl>");
		out.println ("      <w:p wsp:rsidR='00A85DFD' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00A85DFD' wsp:rsidP='00234081'>");
		out.println ("        <w:pPr>");
		out.println ("          <w:tabs>");
		out.println ("            <w:tab w:val='left' w:pos='1800'/>");
		out.println ("          </w:tabs>");
		out.println ("          <w:rPr>");
		out.println ("            <w:rFonts w:fareast='標楷體' w:hint='fareast'/>");
		out.println ("          </w:rPr>");
		out.println ("        </w:pPr>");
		out.println ("      </w:p>");
		out.println ("      <w:sectPr wsp:rsidR='00A85DFD' wsp:rsidRPr='00626E54' wsp:rsidSect='00CA1418'>");
		out.println ("        <w:hdr w:type='odd'>");
		out.println ("          <w:p wsp:rsidR='00043BDE' wsp:rsidRPr='00234081' wsp:rsidRDefault='00043BDE' wsp:rsidP='00234081'>");
		out.println ("            <w:pPr>");
		out.println ("              <w:pStyle w:val='a3'/>");
		out.println ("              <w:jc w:val='center'/>");
		out.println ("              <w:rPr>");
		out.println ("                <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
		out.println ("                <w:b/>");
		out.println ("                <w:sz w:val='44'/>");
		out.println ("                <w:sz-cs w:val='44'/>");
		out.println ("              </w:rPr>");
		out.println ("            </w:pPr>");
		out.println ("            <w:r wsp:rsidRPr='00234081'>");
		out.println ("              <w:rPr>");
		out.println ("                <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                <wx:font wx:val='標楷體'/>");
		out.println ("                <w:b/>");
		out.println ("                <w:sz w:val='44'/>");
		out.println ("                <w:sz-cs w:val='44'/>");
		out.println ("              </w:rPr>");
		out.println ("              <w:t>招生活動統計表</w:t>");
		out.println ("            </w:r>");
		out.println ("          </w:p>");
		out.println ("          <w:p wsp:rsidR='00043BDE' wsp:rsidRPr='00234081' wsp:rsidRDefault='00043BDE' wsp:rsidP='00234081'>");
		out.println ("            <w:pPr>");
		out.println ("              <w:pStyle w:val='a3'/>");
		out.println ("              <w:jc w:val='center'/>");
		out.println ("              <w:rPr>");
		out.println ("                <w:rFonts w:fareast='標楷體'/>");
		out.println ("                <w:b/>");
		out.println ("                <w:sz w:val='32'/>");
		out.println ("                <w:sz-cs w:val='32'/>");
		out.println ("              </w:rPr>");
		out.println ("            </w:pPr>");
		out.println ("            <w:r>");
		out.println ("              <w:rPr>");
		out.println ("                <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
		out.println ("                <w:b/>");
		out.println ("                <w:sz w:val='36'/>");
		out.println ("                <w:sz-cs w:val='36'/>");
		out.println ("              </w:rPr>");
		out.println ("              <w:tab wx:wTab='4155' wx:tlc='none' wx:cTlc='45'/>");
		out.println ("            </w:r>");
		out.println ("            <w:r>");
		out.println ("              <w:rPr>");
		out.println ("                <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
		out.println ("                <w:b/>");
		out.println ("                <w:sz w:val='36'/>");
		out.println ("                <w:sz-cs w:val='36'/>");
		out.println ("              </w:rPr>");
		out.println ("              <w:tab wx:wTab='4155' wx:tlc='none' wx:cTlc='45'/>");
		out.println ("            </w:r>");
		out.println ("            <w:r>");
		out.println ("              <w:rPr>");
		out.println ("                <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
		out.println ("                <w:b/>");
		out.println ("                <w:sz w:val='36'/>");
		out.println ("                <w:sz-cs w:val='36'/>");
		out.println ("              </w:rPr>");
		out.println ("              <w:tab wx:wTab='330' wx:tlc='none' wx:cTlc='3'/>");
		out.println ("            </w:r>");
		out.println ("            <w:r>");
		out.println ("              <w:rPr>");
		out.println ("                <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
		out.println ("                <w:b/>");
		out.println ("                <w:sz w:val='36'/>");
		out.println ("                <w:sz-cs w:val='36'/>");
		out.println ("              </w:rPr>");
		out.println ("              <w:tab wx:wTab='480' wx:tlc='none' wx:cTlc='4'/>");
		out.println ("            </w:r>");
		out.println ("            <w:r wsp:rsidRPr='00234081'>");
		out.println ("              <w:rPr>");
		out.println ("                <w:rFonts w:fareast='標楷體'/>");
		out.println ("                <w:b/>");
		out.println ("                <w:sz w:val='32'/>");
		out.println ("                <w:sz-cs w:val='32'/>");
		out.println ("              </w:rPr>");
		out.println ("              <w:t>(</w:t>");
		out.println ("            </w:r>");
		out.println ("            <w:r wsp:rsidRPr='00234081'>");
		out.println ("              <w:rPr>");
		out.println ("                <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                <wx:font wx:val='標楷體'/>");
		out.println ("                <w:b/>");
		out.println ("                <w:sz w:val='32'/>");
		out.println ("                <w:sz-cs w:val='32'/>");
		out.println ("              </w:rPr>");
		out.println ("              <w:t>期間</w:t>");
		out.println ("            </w:r>");
		out.println ("            <w:r wsp:rsidRPr='00234081'>");
		out.println ("              <w:rPr>");
		out.println ("                <w:rFonts w:fareast='標楷體'/>");
		out.println ("                <w:b/>");
		out.println ("                <w:sz w:val='32'/>");
		out.println ("                <w:sz-cs w:val='32'/>");
		out.println ("              </w:rPr>");
		
		
		String startDay=session.getAttribute("startDay").toString();
		String endDay=session.getAttribute("endDay").toString();
		if(!startDay.trim().equals("")){
			startDay=manager.convertDate(startDay);
		}
		if(!endDay.trim().equals("")){
			endDay=manager.convertDate(endDay);
		}		
		out.println ("              <w:t>"+startDay+" ~ "+endDay+")</w:t>");
		out.println ("            </w:r>");
		out.println ("          </w:p>");
		out.println ("        </w:hdr>");
		out.println ("        <w:pgSz w:w='16838' w:h='11906' w:orient='landscape'/>");
		out.println ("        <w:pgMar w:top='1797' w:right='1440' w:bottom='1797' w:left='1440' w:header='851' w:footer='992' w:gutter='0'/>");
		out.println ("        <w:cols w:space='425'/>");
		out.println ("        <w:docGrid w:type='lines' w:line-pitch='360'/>");
		out.println ("      </w:sectPr>");
		out.println ("    </wx:sect>");
		out.println ("  </w:body>");
		out.println ("</w:wordDocument>");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return null;
	}
	
	private void printColumn(PrintWriter out){		
		out.println ("        <w:tr wsp:rsidR='00626E54' wsp:rsidRPr='00626E54' wsp:rsidTr='00626E54'>");
		out.println ("          <w:tc>");
		out.println ("            <w:tcPr>");
		out.println ("              <w:tcW w:w='2088' w:type='dxa'/>");
		out.println ("            </w:tcPr>");
		out.println ("            <w:p wsp:rsidR='00234081' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00234081' wsp:rsidP='00234081'>");
		out.println ("              <w:pPr>");
		out.println ("                <w:jc w:val='center'/>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體'/>");
		out.println ("                </w:rPr>");
		out.println ("              </w:pPr>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                </w:rPr>");
		out.println ("                <w:t>系別</w:t>");
		out.println ("              </w:r>");
		out.println ("            </w:p>");
		out.println ("          </w:tc>");
		out.println ("          <w:tc>");
		out.println ("            <w:tcPr>");
		out.println ("              <w:tcW w:w='2700' w:type='dxa'/>");
		out.println ("            </w:tcPr>");
		out.println ("            <w:p wsp:rsidR='00234081' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00234081' wsp:rsidP='00234081'>");
		out.println ("              <w:pPr>");
		out.println ("                <w:jc w:val='center'/>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體'/>");
		out.println ("                </w:rPr>");
		out.println ("              </w:pPr>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                </w:rPr>");
		out.println ("                <w:t>招生項目</w:t>");
		out.println ("              </w:r>");
		out.println ("            </w:p>");
		out.println ("          </w:tc>");
		out.println ("          <w:tc>");
		out.println ("            <w:tcPr>");
		out.println ("              <w:tcW w:w='1080' w:type='dxa'/>");
		out.println ("            </w:tcPr>");
		out.println ("            <w:p wsp:rsidR='00234081' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00234081' wsp:rsidP='00234081'>");
		out.println ("              <w:pPr>");
		out.println ("                <w:jc w:val='center'/>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體'/>");
		out.println ("                </w:rPr>");
		out.println ("              </w:pPr>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                </w:rPr>");
		out.println ("                <w:t>日期</w:t>");
		out.println ("              </w:r>");
		out.println ("            </w:p>");
		out.println ("          </w:tc>");
		out.println ("          <w:tc>");
		out.println ("            <w:tcPr>");
		out.println ("              <w:tcW w:w='2520' w:type='dxa'/>");
		out.println ("            </w:tcPr>");
		out.println ("            <w:p wsp:rsidR='00234081' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00234081' wsp:rsidP='00234081'>");
		out.println ("              <w:pPr>");
		out.println ("                <w:jc w:val='center'/>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體'/>");
		out.println ("                </w:rPr>");
		out.println ("              </w:pPr>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                </w:rPr>");
		out.println ("                <w:t>生源學校</w:t>");
		out.println ("              </w:r>");
		out.println ("            </w:p>");
		out.println ("          </w:tc>");
		out.println ("          <w:tc>");
		out.println ("            <w:tcPr>");
		out.println ("              <w:tcW w:w='3060' w:type='dxa'/>");
		out.println ("            </w:tcPr>");
		out.println ("            <w:p wsp:rsidR='00234081' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00234081'>");
		out.println ("              <w:pPr>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體'/>");
		out.println ("                </w:rPr>");
		out.println ("              </w:pPr>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                </w:rPr>");
		out.println ("                <w:t>參與人員</w:t>");
		out.println ("              </w:r>");
		out.println ("            </w:p>");
		out.println ("          </w:tc>");
		out.println ("          <w:tc>");
		out.println ("            <w:tcPr>");
		out.println ("              <w:tcW w:w='2880' w:type='dxa'/>");
		out.println ("            </w:tcPr>");
		out.println ("            <w:p wsp:rsidR='00234081' wsp:rsidRPr='00626E54' wsp:rsidRDefault='00234081'>");
		out.println ("              <w:pPr>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體'/>");
		out.println ("                </w:rPr>");
		out.println ("              </w:pPr>");
		out.println ("              <w:r wsp:rsidRPr='00626E54'>");
		out.println ("                <w:rPr>");
		out.println ("                  <w:rFonts w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println ("                  <wx:font wx:val='標楷體'/>");
		out.println ("                </w:rPr>");
		out.println ("                <w:t>具體內容</w:t>");
		out.println ("              </w:r>");
		out.println ("            </w:p>");
		out.println ("          </w:tc>");
		out.println ("        </w:tr>");
	}
}
