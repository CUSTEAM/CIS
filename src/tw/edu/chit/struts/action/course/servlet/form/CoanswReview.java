package tw.edu.chit.struts.action.course.servlet.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class CoanswReview extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		// HttpSession session = request.getSession(false);
		// ServletContext context = request.getSession().getServletContext();
		// String schoolName=(String) context.getAttribute("SchoolName_ZH");
		String year = manager.getNowBy("School_year");// 現在學年
		String term = manager.getSchoolTerm().toString();// 現在學期

		response.setHeader("Content-Disposition",
				"attachment;filename=CoanswReview.doc");
		response.setContentType("application/vnd.ms-word; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String schoolyear = manager.getSchoolYear().toString();
		String schoolterm = manager.getSchoolTerm().toString();

		out.println("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
		out.println("<?mso-application progid='Word.Document'?>");
		out
				.println("<w:wordDocument xmlns:aml='http://schemas.microsoft.com/aml/2001/core' xmlns:dt='uuid:C2F41010-65B3-11d1-A29F-00AA00C14882' xmlns:ve='http://schemas.openxmlformats.org/markup-compatibility/2006' xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:v='urn:schemas-microsoft-com:vml' xmlns:w10='urn:schemas-microsoft-com:office:word' xmlns:w='http://schemas.microsoft.com/office/word/2003/wordml' xmlns:wx='http://schemas.microsoft.com/office/word/2003/auxHint' xmlns:wsp='http://schemas.microsoft.com/office/word/2003/wordml/sp2' xmlns:sl='http://schemas.microsoft.com/schemaLibrary/2003/core' w:macrosPresent='no' w:embeddedObjPresent='no' w:ocxPresent='no' xml:space='preserve'>");
		out
				.println("  <w:ignoreSubtree w:val='http://schemas.microsoft.com/office/word/2003/wordml/sp2'/>");
		out.println("  <o:DocumentProperties>");
		out.println("    <o:Title>新教務-教學評量業務作業規範</o:Title>");
		out.println("    <o:Author>中華技術學院新竹分部教務組(課務)</o:Author>");
		out.println("    <o:LastAuthor>Shawn</o:LastAuthor>");
		out.println("    <o:Revision>2</o:Revision>");
		out.println("    <o:TotalTime>1</o:TotalTime>");
		out.println("    <o:LastPrinted>2009-12-23T06:41:00Z</o:LastPrinted>");
		out.println("    <o:Created>2011-01-08T09:25:00Z</o:Created>");
		out.println("    <o:LastSaved>2011-01-08T09:25:00Z</o:LastSaved>");
		out.println("    <o:Pages>1</o:Pages>");
		out.println("    <o:Words>30</o:Words>");
		out.println("    <o:Characters>175</o:Characters>");
		out.println("    <o:Company>中華技術學院</o:Company>");
		out.println("    <o:Lines>1</o:Lines>");
		out.println("    <o:Paragraphs>1</o:Paragraphs>");
		out.println("    <o:CharactersWithSpaces>204</o:CharactersWithSpaces>");
		out.println("    <o:Version>12</o:Version>");
		out.println("  </o:DocumentProperties>");
		out.println("  <w:fonts>");
		out
				.println("    <w:defaultFonts w:ascii='Times New Roman' w:fareast='新細明體' w:h-ansi='Times New Roman' w:cs='Times New Roman'/>");
		out.println("    <w:font w:name='Times New Roman'>");
		out.println("      <w:panose-1 w:val='02020603050405020304'/>");
		out.println("      <w:charset w:val='00'/>");
		out.println("      <w:family w:val='Roman'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out
				.println("      <w:sig w:usb-0='E0002AFF' w:usb-1='C0007841' w:usb-2='00000009' w:usb-3='00000000' w:csb-0='000001FF' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='Arial'>");
		out.println("      <w:panose-1 w:val='020B0604020202020204'/>");
		out.println("      <w:charset w:val='00'/>");
		out.println("      <w:family w:val='Swiss'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out
				.println("      <w:sig w:usb-0='E0002AFF' w:usb-1='C0007843' w:usb-2='00000009' w:usb-3='00000000' w:csb-0='000001FF' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='Wingdings'>");
		out.println("      <w:panose-1 w:val='05000000000000000000'/>");
		out.println("      <w:charset w:val='02'/>");
		out.println("      <w:family w:val='auto'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out
				.println("      <w:sig w:usb-0='00000000' w:usb-1='10000000' w:usb-2='00000000' w:usb-3='00000000' w:csb-0='80000000' w:csb-1='00000000'/>");
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
		out.println("      <w:charset w:val='00'/>");
		out.println("      <w:family w:val='Roman'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out
				.println("      <w:sig w:usb-0='A00002EF' w:usb-1='420020EB' w:usb-2='00000000' w:usb-3='00000000' w:csb-0='0000019F' w:csb-1='00000000'/>");
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
		out.println("  </w:fonts>");
		out.println("  <w:lists>");
		out.println("    <w:listDef w:listDefId='0'>");
		out.println("      <w:lsid w:val='25F56A37'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='0C4C216E'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='0409000F'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='480'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='480' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='960'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1440'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1920'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2400'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2880'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3360'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3840'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='4320'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='1'>");
		out.println("      <w:lsid w:val='38D02FC3'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='FC308916'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='0409000F'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='480'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='480' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='960'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1440'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1920'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2400'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2880'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3360'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3840'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='4320'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='2'>");
		out.println("      <w:lsid w:val='486D46BC'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='C442CEFA'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='0409000F'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='480'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='480' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='960'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1440'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1920'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2400'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2880'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3360'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3840'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='4320'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='3'>");
		out.println("      <w:lsid w:val='51E86540'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='10E80912'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='0409000F'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='480'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='480' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='960'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1440'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1920'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2400'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2880'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3360'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3840'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='4320'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='4'>");
		out.println("      <w:lsid w:val='5A912745'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='7492758E'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='E2A2165C'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='34'/>");
		out.println("        <w:lvlText w:val='%1、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='405'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='405' w:hanging='405'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='fareast'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1' w:tplc='E1843B30'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='35'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='885'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='885' w:hanging='405'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='fareast'/>");
		out.println("          <w:u w:val='none'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='2' w:tplc='5152256E'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:suff w:val='Space'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1110' w:hanging='150'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1920'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2400'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2880'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3360'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3840'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='4320'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='5'>");
		out.println("      <w:lsid w:val='5BED6F21'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='34D2ECE0'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='0409000F'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='480'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='480' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='960'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1440'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1920'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2400'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2880'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3360'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3840'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='4320'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='6'>");
		out.println("      <w:lsid w:val='63770765'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='60AAE642'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='6966D8CA'>");
		out.println("        <w:start w:val='3'/>");
		out.println("        <w:nfc w:val='23'/>");
		out.println("        <w:lvlText w:val='□'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='360'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out
				.println("          <w:rFonts w:ascii='新細明體' w:fareast='新細明體' w:h-ansi='Times New Roman' w:cs='Times New Roman' w:hint='fareast'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='1' w:tplc='04090003' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='23'/>");
		out.println("        <w:lvlText w:val=''/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='960'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out
				.println("          <w:rFonts w:ascii='Wingdings' w:h-ansi='Wingdings' w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='2' w:tplc='04090005' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='23'/>");
		out.println("        <w:lvlText w:val=''/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1440'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out
				.println("          <w:rFonts w:ascii='Wingdings' w:h-ansi='Wingdings' w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='3' w:tplc='04090001' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='23'/>");
		out.println("        <w:lvlText w:val=''/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='1920'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out
				.println("          <w:rFonts w:ascii='Wingdings' w:h-ansi='Wingdings' w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='4' w:tplc='04090003' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='23'/>");
		out.println("        <w:lvlText w:val=''/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2400'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out
				.println("          <w:rFonts w:ascii='Wingdings' w:h-ansi='Wingdings' w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='5' w:tplc='04090005' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='23'/>");
		out.println("        <w:lvlText w:val=''/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='2880'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out
				.println("          <w:rFonts w:ascii='Wingdings' w:h-ansi='Wingdings' w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='6' w:tplc='04090001' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='23'/>");
		out.println("        <w:lvlText w:val=''/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3360'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out
				.println("          <w:rFonts w:ascii='Wingdings' w:h-ansi='Wingdings' w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='7' w:tplc='04090003' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='23'/>");
		out.println("        <w:lvlText w:val=''/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='3840'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out
				.println("          <w:rFonts w:ascii='Wingdings' w:h-ansi='Wingdings' w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out
				.println("      <w:lvl w:ilvl='8' w:tplc='04090005' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='23'/>");
		out.println("        <w:lvlText w:val=''/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='4320'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out
				.println("          <w:rFonts w:ascii='Wingdings' w:h-ansi='Wingdings' w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='7'>");
		out.println("      <w:lsid w:val='70437879'/>");
		out.println("      <w:plt w:val='SingleLevel'/>");
		out.println("      <w:tmpl w:val='13561018'/>");
		out.println("      <w:lvl w:ilvl='0'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='35'/>");
		out.println("        <w:lvlText w:val='%1、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='480'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='480' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='fareast'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:list w:ilfo='1'>");
		out.println("      <w:ilst w:val='7'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='2'>");
		out.println("      <w:ilst w:val='4'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='3'>");
		out.println("      <w:ilst w:val='5'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='4'>");
		out.println("      <w:ilst w:val='0'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='5'>");
		out.println("      <w:ilst w:val='3'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='6'>");
		out.println("      <w:ilst w:val='1'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='7'>");
		out.println("      <w:ilst w:val='2'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='8'>");
		out.println("      <w:ilst w:val='6'/>");
		out.println("    </w:list>");
		out.println("  </w:lists>");
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
		out.println("      <w:lsdException w:name='caption'/>");
		out.println("      <w:lsdException w:name='Title'/>");
		out.println("      <w:lsdException w:name='Subtitle'/>");
		out.println("      <w:lsdException w:name='Strong'/>");
		out.println("      <w:lsdException w:name='Emphasis'/>");
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
		out.println("      <w:pPr>");
		out.println("        <w:widowControl w:val='off'/>");
		out.println("      </w:pPr>");
		out.println("      <w:rPr>");
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
		out.println("        <wx:font wx:val='Times New Roman'/>");
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
		out.println("    <w:style w:type='paragraph' w:styleId='2'>");
		out.println("      <w:name w:val='Body Text Indent 2'/>");
		out.println("      <wx:uiName wx:val='本文縮排 2'/>");
		out.println("      <w:basedOn w:val='a'/>");
		out.println("      <w:pPr>");
		out.println("        <w:ind w:first-line='480'/>");
		out.println("      </w:pPr>");
		out.println("      <w:rPr>");
		out.println("        <w:rFonts w:fareast='標楷體'/>");
		out.println("        <wx:font wx:val='Times New Roman'/>");
		out.println("        <w:sz-cs w:val='20'/>");
		out.println("      </w:rPr>");
		out.println("    </w:style>");
		out.println("    <w:style w:type='paragraph' w:styleId='a3'>");
		out.println("      <w:name w:val='footer'/>");
		out.println("      <wx:uiName wx:val='頁尾'/>");
		out.println("      <w:basedOn w:val='a'/>");
		out.println("      <w:pPr>");
		out.println("        <w:tabs>");
		out.println("          <w:tab w:val='center' w:pos='4153'/>");
		out.println("          <w:tab w:val='right' w:pos='8306'/>");
		out.println("        </w:tabs>");
		out.println("        <w:snapToGrid w:val='off'/>");
		out.println("      </w:pPr>");
		out.println("      <w:rPr>");
		out.println("        <w:rFonts w:fareast='標楷體'/>");
		out.println("        <wx:font wx:val='Times New Roman'/>");
		out.println("        <w:sz w:val='20'/>");
		out.println("        <w:sz-cs w:val='20'/>");
		out.println("      </w:rPr>");
		out.println("    </w:style>");
		out.println("    <w:style w:type='paragraph' w:styleId='a4'>");
		out.println("      <w:name w:val='Body Text'/>");
		out.println("      <wx:uiName wx:val='本文'/>");
		out.println("      <w:basedOn w:val='a'/>");
		out.println("      <w:rPr>");
		out.println("        <w:rFonts w:fareast='標楷體'/>");
		out.println("        <wx:font wx:val='Times New Roman'/>");
		out.println("        <w:sz w:val='20'/>");
		out.println("        <w:sz-cs w:val='20'/>");
		out.println("      </w:rPr>");
		out.println("    </w:style>");
		out.println("    <w:style w:type='paragraph' w:styleId='a5'>");
		out.println("      <w:name w:val='Balloon Text'/>");
		out.println("      <wx:uiName wx:val='註解方塊文字'/>");
		out.println("      <w:basedOn w:val='a'/>");
		out.println("      <w:rsid w:val='00A02C50'/>");
		out.println("      <w:rPr>");
		out.println("        <w:rFonts w:ascii='Arial' w:h-ansi='Arial'/>");
		out.println("        <wx:font wx:val='Arial'/>");
		out.println("        <w:sz w:val='18'/>");
		out.println("        <w:sz-cs w:val='18'/>");
		out.println("      </w:rPr>");
		out.println("    </w:style>");
		out.println("    <w:style w:type='paragraph' w:styleId='a6'>");
		out.println("      <w:name w:val='header'/>");
		out.println("      <wx:uiName wx:val='頁首'/>");
		out.println("      <w:basedOn w:val='a'/>");
		out.println("      <w:rsid w:val='00E7732C'/>");
		out.println("      <w:pPr>");
		out.println("        <w:tabs>");
		out.println("          <w:tab w:val='center' w:pos='4153'/>");
		out.println("          <w:tab w:val='right' w:pos='8306'/>");
		out.println("        </w:tabs>");
		out.println("        <w:snapToGrid w:val='off'/>");
		out.println("      </w:pPr>");
		out.println("      <w:rPr>");
		out.println("        <wx:font wx:val='Times New Roman'/>");
		out.println("        <w:sz w:val='20'/>");
		out.println("        <w:sz-cs w:val='20'/>");
		out.println("      </w:rPr>");
		out.println("    </w:style>");
		out.println("    <w:style w:type='character' w:styleId='a7'>");
		out.println("      <w:name w:val='page number'/>");
		out.println("      <wx:uiName wx:val='頁碼'/>");
		out.println("      <w:basedOn w:val='a0'/>");
		out.println("      <w:rsid w:val='00E7732C'/>");
		out.println("    </w:style>");
		out.println("    <w:style w:type='paragraph' w:styleId='a8'>");
		out.println("      <w:name w:val='Document Map'/>");
		out.println("      <wx:uiName wx:val='文件引導模式'/>");
		out.println("      <w:basedOn w:val='a'/>");
		out.println("      <w:rsid w:val='005000BC'/>");
		out.println("      <w:pPr>");
		out
				.println("        <w:shd w:val='clear' w:color='auto' w:fill='000080'/>");
		out.println("      </w:pPr>");
		out.println("      <w:rPr>");
		out.println("        <w:rFonts w:ascii='Arial' w:h-ansi='Arial'/>");
		out.println("        <wx:font wx:val='Arial'/>");
		out.println("      </w:rPr>");
		out.println("    </w:style>");
		out.println("  </w:styles>");
		out.println("  <w:shapeDefaults>");
		out.println("    <o:shapedefaults v:ext='edit' spidmax='3074'/>");
		out.println("    <o:shapelayout v:ext='edit'>");
		out.println("      <o:idmap v:ext='edit' data='1'/>");
		out.println("    </o:shapelayout>");
		out.println("  </w:shapeDefaults>");
		out.println("  <w:docPr>");
		out.println("    <w:view w:val='print'/>");
		out.println("    <w:zoom w:percent='100'/>");
		out.println("    <w:bordersDontSurroundHeader/>");
		out.println("    <w:bordersDontSurroundFooter/>");
		out.println("    <w:proofState w:spelling='clean' w:grammar='clean'/>");
		out.println("    <w:stylePaneFormatFilter w:val='3F01'/>");
		out.println("    <w:defaultTabStop w:val='480'/>");
		out.println("    <w:drawingGridHorizontalSpacing w:val='120'/>");
		out.println("    <w:drawingGridVerticalSpacing w:val='210'/>");
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
		out.println("    <w:hdrShapeDefaults>");
		out.println("      <o:shapedefaults v:ext='edit' spidmax='3074'/>");
		out.println("    </w:hdrShapeDefaults>");
		out.println("    <w:footnotePr>");
		out.println("      <w:footnote w:type='separator'>");
		out
				.println("        <w:p wsp:rsidR='00CE11FE' wsp:rsidRDefault='00CE11FE'>");
		out.println("          <w:r>");
		out.println("            <w:separator/>");
		out.println("          </w:r>");
		out.println("        </w:p>");
		out.println("      </w:footnote>");
		out.println("      <w:footnote w:type='continuation-separator'>");
		out
				.println("        <w:p wsp:rsidR='00CE11FE' wsp:rsidRDefault='00CE11FE'>");
		out.println("          <w:r>");
		out.println("            <w:continuationSeparator/>");
		out.println("          </w:r>");
		out.println("        </w:p>");
		out.println("      </w:footnote>");
		out.println("    </w:footnotePr>");
		out.println("    <w:endnotePr>");
		out.println("      <w:endnote w:type='separator'>");
		out
				.println("        <w:p wsp:rsidR='00CE11FE' wsp:rsidRDefault='00CE11FE'>");
		out.println("          <w:r>");
		out.println("            <w:separator/>");
		out.println("          </w:r>");
		out.println("        </w:p>");
		out.println("      </w:endnote>");
		out.println("      <w:endnote w:type='continuation-separator'>");
		out
				.println("        <w:p wsp:rsidR='00CE11FE' wsp:rsidRDefault='00CE11FE'>");
		out.println("          <w:r>");
		out.println("            <w:continuationSeparator/>");
		out.println("          </w:r>");
		out.println("        </w:p>");
		out.println("      </w:endnote>");
		out.println("    </w:endnotePr>");
		out.println("    <w:compat>");
		out.println("      <w:spaceForUL/>");
		out.println("      <w:balanceSingleByteDoubleByteWidth/>");
		out.println("      <w:doNotLeaveBackslashAlone/>");
		out.println("      <w:ulTrailSpace/>");
		out.println("      <w:doNotExpandShiftReturn/>");
		out.println("      <w:adjustLineHeightInTable/>");
		out.println("      <w:dontAllowFieldEndSelect/>");
		out.println("      <w:useWord2002TableStyleRules/>");
		out.println("      <w:useFELayout/>");
		out.println("    </w:compat>");
		out.println("    <wsp:rsids>");
		out.println("      <wsp:rsidRoot wsp:val='00D97829'/>");
		out.println("      <wsp:rsid wsp:val='00010B18'/>");
		out.println("      <wsp:rsid wsp:val='00017556'/>");
		out.println("      <wsp:rsid wsp:val='00045141'/>");
		out.println("      <wsp:rsid wsp:val='00054F05'/>");
		out.println("      <wsp:rsid wsp:val='00062DBF'/>");
		out.println("      <wsp:rsid wsp:val='000705A7'/>");
		out.println("      <wsp:rsid wsp:val='00090149'/>");
		out.println("      <wsp:rsid wsp:val='000A6651'/>");
		out.println("      <wsp:rsid wsp:val='000B69FC'/>");
		out.println("      <wsp:rsid wsp:val='000C7499'/>");
		out.println("      <wsp:rsid wsp:val='000E0FFD'/>");
		out.println("      <wsp:rsid wsp:val='000E62D4'/>");
		out.println("      <wsp:rsid wsp:val='000F36D1'/>");
		out.println("      <wsp:rsid wsp:val='0010055A'/>");
		out.println("      <wsp:rsid wsp:val='001133AF'/>");
		out.println("      <wsp:rsid wsp:val='00115463'/>");
		out.println("      <wsp:rsid wsp:val='001200EC'/>");
		out.println("      <wsp:rsid wsp:val='001430B2'/>");
		out.println("      <wsp:rsid wsp:val='00164E01'/>");
		out.println("      <wsp:rsid wsp:val='00167F2A'/>");
		out.println("      <wsp:rsid wsp:val='00170037'/>");
		out.println("      <wsp:rsid wsp:val='001967CA'/>");
		out.println("      <wsp:rsid wsp:val='001A1540'/>");
		out.println("      <wsp:rsid wsp:val='001B7258'/>");
		out.println("      <wsp:rsid wsp:val='001C2CA1'/>");
		out.println("      <wsp:rsid wsp:val='001C4B72'/>");
		out.println("      <wsp:rsid wsp:val='001E4D3A'/>");
		out.println("      <wsp:rsid wsp:val='001E6329'/>");
		out.println("      <wsp:rsid wsp:val='0021542F'/>");
		out.println("      <wsp:rsid wsp:val='002336B2'/>");
		out.println("      <wsp:rsid wsp:val='002E7B78'/>");
		out.println("      <wsp:rsid wsp:val='002F48F5'/>");
		out.println("      <wsp:rsid wsp:val='00340B50'/>");
		out.println("      <wsp:rsid wsp:val='00351801'/>");
		out.println("      <wsp:rsid wsp:val='003615C3'/>");
		out.println("      <wsp:rsid wsp:val='00366408'/>");
		out.println("      <wsp:rsid wsp:val='003B3393'/>");
		out.println("      <wsp:rsid wsp:val='003B77EA'/>");
		out.println("      <wsp:rsid wsp:val='00420CD3'/>");
		out.println("      <wsp:rsid wsp:val='004275B6'/>");
		out.println("      <wsp:rsid wsp:val='004D0AC3'/>");
		out.println("      <wsp:rsid wsp:val='004D2277'/>");
		out.println("      <wsp:rsid wsp:val='004F7819'/>");
		out.println("      <wsp:rsid wsp:val='005000BC'/>");
		out.println("      <wsp:rsid wsp:val='00512C45'/>");
		out.println("      <wsp:rsid wsp:val='00535E9A'/>");
		out.println("      <wsp:rsid wsp:val='00543E86'/>");
		out.println("      <wsp:rsid wsp:val='00550B75'/>");
		out.println("      <wsp:rsid wsp:val='005600B2'/>");
		out.println("      <wsp:rsid wsp:val='00563B37'/>");
		out.println("      <wsp:rsid wsp:val='00565EFF'/>");
		out.println("      <wsp:rsid wsp:val='00580599'/>");
		out.println("      <wsp:rsid wsp:val='005A1FF9'/>");
		out.println("      <wsp:rsid wsp:val='005A7DAC'/>");
		out.println("      <wsp:rsid wsp:val='005C01C0'/>");
		out.println("      <wsp:rsid wsp:val='005C39E7'/>");
		out.println("      <wsp:rsid wsp:val='005D2A80'/>");
		out.println("      <wsp:rsid wsp:val='006012E4'/>");
		out.println("      <wsp:rsid wsp:val='00603FB3'/>");
		out.println("      <wsp:rsid wsp:val='00621A47'/>");
		out.println("      <wsp:rsid wsp:val='0069108A'/>");
		out.println("      <wsp:rsid wsp:val='006B0F22'/>");
		out.println("      <wsp:rsid wsp:val='006D4977'/>");
		out.println("      <wsp:rsid wsp:val='00715973'/>");
		out.println("      <wsp:rsid wsp:val='0072540A'/>");
		out.println("      <wsp:rsid wsp:val='00740657'/>");
		out.println("      <wsp:rsid wsp:val='0077319F'/>");
		out.println("      <wsp:rsid wsp:val='00775ADB'/>");
		out.println("      <wsp:rsid wsp:val='007828FE'/>");
		out.println("      <wsp:rsid wsp:val='00791BFB'/>");
		out.println("      <wsp:rsid wsp:val='007A3D41'/>");
		out.println("      <wsp:rsid wsp:val='007A793C'/>");
		out.println("      <wsp:rsid wsp:val='007A7C2C'/>");
		out.println("      <wsp:rsid wsp:val='007B09ED'/>");
		out.println("      <wsp:rsid wsp:val='007C4420'/>");
		out.println("      <wsp:rsid wsp:val='008029B9'/>");
		out.println("      <wsp:rsid wsp:val='00811A36'/>");
		out.println("      <wsp:rsid wsp:val='0081587C'/>");
		out.println("      <wsp:rsid wsp:val='00827742'/>");
		out.println("      <wsp:rsid wsp:val='00890D57'/>");
		out.println("      <wsp:rsid wsp:val='00897BA2'/>");
		out.println("      <wsp:rsid wsp:val='008B7187'/>");
		out.println("      <wsp:rsid wsp:val='008C428F'/>");
		out.println("      <wsp:rsid wsp:val='008E390C'/>");
		out.println("      <wsp:rsid wsp:val='008F755A'/>");
		out.println("      <wsp:rsid wsp:val='00910656'/>");
		out.println("      <wsp:rsid wsp:val='00934090'/>");
		out.println("      <wsp:rsid wsp:val='00934FB9'/>");
		out.println("      <wsp:rsid wsp:val='009358D0'/>");
		out.println("      <wsp:rsid wsp:val='0094326B'/>");
		out.println("      <wsp:rsid wsp:val='00952B5E'/>");
		out.println("      <wsp:rsid wsp:val='00982A19'/>");
		out.println("      <wsp:rsid wsp:val='00984208'/>");
		out.println("      <wsp:rsid wsp:val='00991420'/>");
		out.println("      <wsp:rsid wsp:val='009A0BDF'/>");
		out.println("      <wsp:rsid wsp:val='009C495A'/>");
		out.println("      <wsp:rsid wsp:val='009C535E'/>");
		out.println("      <wsp:rsid wsp:val='00A02C50'/>");
		out.println("      <wsp:rsid wsp:val='00A12214'/>");
		out.println("      <wsp:rsid wsp:val='00A343E8'/>");
		out.println("      <wsp:rsid wsp:val='00A44582'/>");
		out.println("      <wsp:rsid wsp:val='00A56393'/>");
		out.println("      <wsp:rsid wsp:val='00AA054E'/>");
		out.println("      <wsp:rsid wsp:val='00AC016C'/>");
		out.println("      <wsp:rsid wsp:val='00AD749C'/>");
		out.println("      <wsp:rsid wsp:val='00B113BA'/>");
		out.println("      <wsp:rsid wsp:val='00B76DC9'/>");
		out.println("      <wsp:rsid wsp:val='00BB60C4'/>");
		out.println("      <wsp:rsid wsp:val='00BC236C'/>");
		out.println("      <wsp:rsid wsp:val='00BD3712'/>");
		out.println("      <wsp:rsid wsp:val='00BE126A'/>");
		out.println("      <wsp:rsid wsp:val='00BE6040'/>");
		out.println("      <wsp:rsid wsp:val='00C51A8B'/>");
		out.println("      <wsp:rsid wsp:val='00C76A16'/>");
		out.println("      <wsp:rsid wsp:val='00CA2A45'/>");
		out.println("      <wsp:rsid wsp:val='00CB4421'/>");
		out.println("      <wsp:rsid wsp:val='00CC2BCA'/>");
		out.println("      <wsp:rsid wsp:val='00CE11FE'/>");
		out.println("      <wsp:rsid wsp:val='00CF40E5'/>");
		out.println("      <wsp:rsid wsp:val='00CF4204'/>");
		out.println("      <wsp:rsid wsp:val='00D1177C'/>");
		out.println("      <wsp:rsid wsp:val='00D53C04'/>");
		out.println("      <wsp:rsid wsp:val='00D77E7F'/>");
		out.println("      <wsp:rsid wsp:val='00D804F0'/>");
		out.println("      <wsp:rsid wsp:val='00D850FE'/>");
		out.println("      <wsp:rsid wsp:val='00D87746'/>");
		out.println("      <wsp:rsid wsp:val='00D97829'/>");
		out.println("      <wsp:rsid wsp:val='00DA45C5'/>");
		out.println("      <wsp:rsid wsp:val='00DA6007'/>");
		out.println("      <wsp:rsid wsp:val='00DA7755'/>");
		out.println("      <wsp:rsid wsp:val='00DB4A1F'/>");
		out.println("      <wsp:rsid wsp:val='00DF7BA8'/>");
		out.println("      <wsp:rsid wsp:val='00E531D5'/>");
		out.println("      <wsp:rsid wsp:val='00E540E4'/>");
		out.println("      <wsp:rsid wsp:val='00E54841'/>");
		out.println("      <wsp:rsid wsp:val='00E7732C'/>");
		out.println("      <wsp:rsid wsp:val='00E865CA'/>");
		out.println("      <wsp:rsid wsp:val='00E86C3F'/>");
		out.println("      <wsp:rsid wsp:val='00ED13F4'/>");
		out.println("      <wsp:rsid wsp:val='00EE0C1C'/>");
		out.println("      <wsp:rsid wsp:val='00F01AF7'/>");
		out.println("      <wsp:rsid wsp:val='00F0407A'/>");
		out.println("      <wsp:rsid wsp:val='00F04518'/>");
		out.println("      <wsp:rsid wsp:val='00F117BA'/>");
		out.println("      <wsp:rsid wsp:val='00F320DA'/>");
		out.println("      <wsp:rsid wsp:val='00F32C04'/>");
		out.println("      <wsp:rsid wsp:val='00F522B9'/>");
		out.println("      <wsp:rsid wsp:val='00F52C4E'/>");
		out.println("      <wsp:rsid wsp:val='00F56C5E'/>");
		out.println("      <wsp:rsid wsp:val='00F8085E'/>");
		out.println("      <wsp:rsid wsp:val='00F86072'/>");
		out.println("      <wsp:rsid wsp:val='00F87C83'/>");
		out.println("      <wsp:rsid wsp:val='00FA4C44'/>");
		out.println("      <wsp:rsid wsp:val='00FD10DD'/>");
		out.println("      <wsp:rsid wsp:val='00FD471A'/>");
		out.println("      <wsp:rsid wsp:val='00FD5879'/>");
		out.println("    </wsp:rsids>");
		out.println("  </w:docPr>");
		out.println("  <w:body>");

		String content;
		List list = manager
				.ezGetBy("SELeCT * FROM CoanswReview WHERE schoolYear='" + year
						+ "' AND schoolTerm='" + term + "'");

		if (list.size() < 1) {
			out
					.println("    <w:p wsp:rsidR='00D87746' wsp:rsidRPr='00AC016C' wsp:rsidRDefault='00164E01' wsp:rsidP='00164E01'>");

			out.println("      <w:r wsp:rsidRPr='00AC016C'>");
			out.println("        <w:rPr>");
			out
					.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("          <wx:font wx:val='標楷體'/>");
			out.println("          <w:sz w:val='32'/>");
			out.println("          <w:sz-cs w:val='32'/>");
			out.println("        </w:rPr>");
			out.println("        <w:t>沒有人填寫</w:t>");
			out.println("      </w:r>");
			out.println("    </w:p>");
		} else {

			for (int i = 0; i < list.size(); i++) {

				out
						.println("    <w:p wsp:rsidR='00D87746' wsp:rsidRPr='00AC016C' wsp:rsidRDefault='00164E01' wsp:rsidP='00164E01'>");
				out.println("      <w:pPr>");
				out
						.println("        <w:spacing w:line='400' w:line-rule='exact'/>");
				out.println("        <w:jc w:val='center'/>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='32'/>");
				out.println("          <w:sz-cs w:val='32'/>");
				out.println("        </w:rPr>");
				out.println("      </w:pPr>");
				out.println("      <w:r wsp:rsidRPr='00AC016C'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='32'/>");
				out.println("          <w:sz-cs w:val='32'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t>中華</w:t>");
				out.println("      </w:r>");
				out
						.println("      <w:r wsp:rsidR='00580599' wsp:rsidRPr='00AC016C'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='32'/>");
				out.println("          <w:sz-cs w:val='32'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t>科技大學</w:t>");
				out.println("      </w:r>");
				out
						.println("      <w:r wsp:rsidR='00D87746' wsp:rsidRPr='00AC016C'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='32'/>");
				out.println("          <w:sz-cs w:val='32'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t>"
						+ ((Map) list.get(i)).get("schoolYear") + "學</w:t>");
				out.println("      </w:r>");
				out.println("      <w:r wsp:rsidRPr='00AC016C'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='32'/>");
				out.println("          <w:sz-cs w:val='32'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t>年度第"
						+ ((Map) list.get(i)).get("schoolTerm") + "學期</w:t>");
				out.println("      </w:r>");
				out.println("    </w:p>");
				out
						.println("    <w:p wsp:rsidR='00164E01' wsp:rsidRPr='00090149' wsp:rsidRDefault='00164E01' wsp:rsidP='00164E01'>");
				out.println("      <w:pPr>");
				out
						.println("        <w:spacing w:line='400' w:line-rule='exact'/>");
				out.println("        <w:jc w:val='center'/>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='32'/>");
				out.println("          <w:sz-cs w:val='32'/>");
				out.println("        </w:rPr>");
				out.println("      </w:pPr>");
				out.println("      <w:r wsp:rsidRPr='00AC016C'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='32'/>");
				out.println("          <w:sz-cs w:val='32'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t>教學評量回應</w:t>");
				out.println("      </w:r>");
				out
						.println("      <w:r wsp:rsidR='00E54841' wsp:rsidRPr='00090149'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='32'/>");
				out.println("          <w:sz-cs w:val='32'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t>具體</w:t>");
				out.println("      </w:r>");
				out.println("      <w:r wsp:rsidRPr='00090149'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='32'/>");
				out.println("          <w:sz-cs w:val='32'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t>改善</w:t>");
				out.println("      </w:r>");
				out
						.println("      <w:r wsp:rsidR='00090149' wsp:rsidRPr='00090149'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='32'/>");
				out.println("          <w:sz-cs w:val='32'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t>回饋</w:t>");
				out.println("      </w:r>");
				out.println("      <w:r wsp:rsidRPr='00090149'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='32'/>");
				out.println("          <w:sz-cs w:val='32'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t>表</w:t>");
				out.println("      </w:r>");
				out.println("    </w:p>");
				out
						.println("    <w:p wsp:rsidR='00AD749C' wsp:rsidRPr='00D87746' wsp:rsidRDefault='00AD749C' wsp:rsidP='00164E01'>");
				out.println("      <w:pPr>");
				out
						.println("        <w:spacing w:line='400' w:line-rule='exact'/>");
				out.println("        <w:jc w:val='center'/>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:b/>");
				out.println("          <w:sz w:val='32'/>");
				out.println("          <w:sz-cs w:val='32'/>");
				out.println("        </w:rPr>");
				out.println("      </w:pPr>");
				out.println("    </w:p>");
				out
						.println("    <w:p wsp:rsidR='00164E01' wsp:rsidRPr='00AD749C' wsp:rsidRDefault='00AD749C' wsp:rsidP='00AD749C'>");
				out.println("      <w:pPr>");
				out
						.println("        <w:spacing w:after-lines='50' w:line='400' w:line-rule='exact'/>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("          <w:u w:val='single'/>");
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
				out
						.println("        <w:t>授課班級："
								+ manager
										.ezGetString("SELECT ClassName FROM Class WHERE ClassNo='"
												+ ((Map) list.get(i))
														.get("depart_class")
												+ "'") + "</w:t>");
				out.println("      </w:r>");
				out.println("      <w:r>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("          <w:u w:val='single'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t></w:t>");
				out.println("      </w:r>");
				out.println("      <w:r wsp:rsidRPr='00AD749C'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("        </w:rPr>");
				out
						.println("        <w:t>科目名稱："
								+ manager
										.ezGetString("SELECT chi_name FROM Csno WHERE cscode='"
												+ ((Map) list.get(i))
														.get("cscode") + "'")
								+ "</w:t>");
				out.println("      </w:r>");
				out.println("      <w:r>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("          <w:u w:val='single'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t></w:t>");
				out.println("      </w:r>");
				out.println("      <w:r wsp:rsidRPr='00AD749C'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("        </w:rPr>");
				out
						.println("        <w:t>授課教師："
								+ manager
										.ezGetString("SELECT cname FROM empl WHERE idno='"
												+ ((Map) list.get(i))
														.get("techid") + "'")
								+ "</w:t>");
				out.println("      </w:r>");
				out.println("      <w:r>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("          <w:u w:val='single'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t></w:t>");
				out.println("      </w:r>");
				out.println("    </w:p>");
				out.println("    <w:tbl>");
				out.println("      <w:tblPr>");
				out.println("        <w:tblW w:w='5000' w:type='pct'/>");
				out.println("        <w:jc w:val='center'/>");
				out.println("        <w:tblBorders>");
				out
						.println("          <w:top w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
				out
						.println("          <w:left w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
				out
						.println("          <w:bottom w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
				out
						.println("          <w:right w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
				out
						.println("          <w:insideH w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
				out
						.println("          <w:insideV w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
				out.println("        </w:tblBorders>");
				out.println("        <w:tblLook w:val='01E0'/>");
				out.println("      </w:tblPr>");
				out.println("      <w:tblGrid>");
				out.println("        <w:gridCol w:w='4643'/>");
				out.println("        <w:gridCol w:w='4644'/>");
				out.println("      </w:tblGrid>");
				out
						.println("      <w:tr wsp:rsidR='00164E01' wsp:rsidTr='008C428F'>");
				out.println("        <w:trPr>");
				out.println("          <w:trHeight w:val='737'/>");
				out.println("          <w:jc w:val='center'/>");
				out.println("        </w:trPr>");
				out.println("        <w:tc>");
				out.println("          <w:tcPr>");
				out.println("            <w:tcW w:w='2500' w:type='pct'/>");
				out.println("            <w:vAlign w:val='center'/>");
				out.println("          </w:tcPr>");
				out
						.println("          <w:p wsp:rsidR='00512C45' wsp:rsidRPr='00090149' wsp:rsidRDefault='00164E01' wsp:rsidP='00791BFB'>");
				out.println("            <w:pPr>");
				out
						.println("              <w:spacing w:line='400' w:line-rule='exact'/>");
				out.println("              <w:jc w:val='center'/>");
				out.println("              <w:rPr>");
				out
						.println("                <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("                <wx:font wx:val='標楷體'/>");
				out.println("                <w:sz w:val='28'/>");
				out.println("                <w:sz-cs w:val='28'/>");
				out.println("              </w:rPr>");
				out.println("            </w:pPr>");
				out.println("            <w:r wsp:rsidRPr='00090149'>");
				out.println("              <w:rPr>");
				out.println("                <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("                <wx:font wx:val='標楷體'/>");
				out.println("                <w:sz w:val='28'/>");
				out.println("                <w:sz-cs w:val='28'/>");
				out.println("              </w:rPr>");
				out.println("              <w:t>授課教師回饋意見</w:t>");
				out.println("            </w:r>");
				out.println("          </w:p>");
				out.println("        </w:tc>");
				out.println("        <w:tc>");
				out.println("          <w:tcPr>");
				out.println("            <w:tcW w:w='2500' w:type='pct'/>");
				out.println("            <w:vAlign w:val='center'/>");
				out.println("          </w:tcPr>");
				
				out.println("          <w:p wsp:rsidR='00164E01' wsp:rsidRDefault='00164E01' wsp:rsidP='00791BFB'>");
				out.println("            <w:pPr>");
				out.println("              <w:spacing w:line='400' w:line-rule='exact'/>");
				out.println("              <w:jc w:val='center'/>");
				out.println("              <w:rPr>");
				out.println("                <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("                <wx:font wx:val='標楷體'/>");
				out.println("                <w:sz w:val='28'/>");
				out.println("                <w:sz-cs w:val='28'/>");
				out.println("              </w:rPr>");
				out.println("            </w:pPr>");

				out.println("            <w:r>");
				out.println("              <w:rPr>");
				out.println("                <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("                <wx:font wx:val='標楷體'/>");
				out.println("                <w:sz w:val='28'/>");
				out.println("                <w:sz-cs w:val='28'/>");
				out.println("              </w:rPr>");
				out.println("              <w:t>系處理情形</w:t>");
				out.println("            </w:r>");
				out.println("          </w:p>");
				out.println("        </w:tc>");
				out.println("      </w:tr>");
				out.println("      <w:tr wsp:rsidR='00F0407A' wsp:rsidTr='00F0407A'>");
				out.println("        <w:trPr>");
				out.println("          <w:trHeight w:val='582'/>");
				out.println("          <w:jc w:val='center'/>");
				out.println("        </w:trPr>");
				out.println("        <w:tc>");
				out.println("          <w:tcPr>");
				out.println("            <w:tcW w:w='2500' w:type='pct'/>");
				out.println("          </w:tcPr>");
				
				out.println("          <w:p wsp:rsidR='00F0407A' wsp:rsidRDefault='00F0407A' wsp:rsidP='00F0407A'>");
				out.println("            <w:pPr>");
				out.println("              <w:spacing w:line='400' w:line-rule='exact'/>");
				out.println("              <w:jc w:val='both'/>");
				out.println("              <w:rPr>");
				out.println("                <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("                <wx:font wx:val='標楷體'/>");
				out.println("              </w:rPr>");
				out.println("            </w:pPr>");
				
				out.println("<w:r>");
				out.println("<w:rPr>");
				out.println("<w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("<wx:font wx:val='標楷體'/>");
				out.println(" </w:rPr>");
				out.println("<w:t>" + ((Map) list.get(i)).get("content_per")+ "</w:t>");
				out.println("</w:r>");
				out.println("          </w:p>");
				
				
				
				out.println("        </w:tc>");
				out.println("        <w:tc>");
				out.println("          <w:tcPr>");
				out.println("            <w:tcW w:w='2500' w:type='pct'/>");
				out.println("          </w:tcPr>");
				out
						.println("          <w:p wsp:rsidR='00F0407A' wsp:rsidRPr='00F0407A' wsp:rsidRDefault='00F0407A' wsp:rsidP='00F0407A'>");
				out.println("            <w:pPr>");
				out
						.println("              <w:spacing w:line='400' w:line-rule='exact'/>");
				out.println("              <w:jc w:val='both'/>");
				out.println("              <w:rPr>");
				out
						.println("                <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("                <wx:font wx:val='標楷體'/>");
				out.println("              </w:rPr>");
				out.println("            </w:pPr>");
				out.println("          </w:p>");
				out.println("        </w:tc>");
				out.println("      </w:tr>");
				out.println("    </w:tbl>");
				out
						.println("    <w:p wsp:rsidR='00164E01' wsp:rsidRDefault='00164E01' wsp:rsidP='00164E01'>");
				out.println("      <w:pPr>");
				out
						.println("        <w:spacing w:line='400' w:line-rule='exact'/>");
				out.println("        <w:jc w:val='center'/>");
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
						.println("    <w:p wsp:rsidR='00715973' wsp:rsidRPr='00715973' wsp:rsidRDefault='00715973' wsp:rsidP='00715973'>");
				out.println("      <w:pPr>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("        </w:rPr>");
				out.println("      </w:pPr>");
				out.println("      <w:r wsp:rsidRPr='00715973'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t>系主任：                   院長:</w:t>");
				out.println("      </w:r>");
				out.println("      <w:r wsp:rsidRPr='00715973'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("          <w:u w:val='single'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t></w:t>");
				out.println("      </w:r>");
				out.println("      <w:r>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("          <w:u w:val='single'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t></w:t>");
				out.println("      </w:r>");
				out.println("      <w:r wsp:rsidRPr='00715973'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("          <w:u w:val='single'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t></w:t>");
				out.println("      </w:r>");
				out.println("      <w:r>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("          <w:u w:val='single'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t></w:t>");
				out.println("      </w:r>");
				out.println("      <w:r wsp:rsidRPr='00715973'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("          <w:u w:val='single'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t></w:t>");
				out.println("      </w:r>");
				out.println("      <w:r>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t></w:t>");
				out.println("      </w:r>");
				out.println("    </w:p>");
				out
						.println("    <w:p wsp:rsidR='000705A7' wsp:rsidRDefault='00715973' wsp:rsidP='000705A7'>");
				out.println("      <w:pPr>");
				out.println("        <w:widowControl/>");
				out.println("        <w:spacing w:before-lines='50'/>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:cs='新細明體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:kern w:val='0'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("        </w:rPr>");
				out.println("      </w:pPr>");
				out.println("      <w:r wsp:rsidRPr='00715973'>");
				out.println("        <w:rPr>");
				out
						.println("          <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:cs='新細明體' w:hint='fareast'/>");
				out.println("          <wx:font wx:val='標楷體'/>");
				out.println("          <w:kern w:val='0'/>");
				out.println("          <w:sz w:val='28'/>");
				out.println("          <w:sz-cs w:val='28'/>");
				out.println("        </w:rPr>");
				out.println("        <w:t>本回饋表的內容係由教師網路填報</w:t>");
				out.println("      </w:r>");
				out.println("    </w:p>");
				
				out.println("    <w:sectPr wsp:rsidR='001A1540' wsp:rsidRPr='00715973' wsp:rsidSect='007A793C'>");
				out.println("      <w:pgSz w:w='11906' w:h='16838' w:code='9'/>");
				out.println("      <w:pgMar w:top='1134' w:right='1134' w:bottom='1440' w:left='1134' w:header='851' w:footer='992' w:gutter='567'/>");
				out.println("      <w:cols w:space='425'/>");
				out.println("      <w:titlePg/>");
				out
						.println("      <w:docGrid w:type='lines' w:line-pitch='420'/>");
				out.println("    </w:sectPr>");
				if (i < list.size() - 1)
					out.println("    <w:br w:type='page'/>");
			}

		}

		out.println("  </w:body>");
		out.println("</w:wordDocument>");
		out.close();

	}
}
