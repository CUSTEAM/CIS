package tw.edu.chit.struts.action.score;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_YEAR;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.formula.functions.If;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.ClassInCharge;
import tw.edu.chit.model.ClassScoreSummary;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Examine;
import tw.edu.chit.model.FailStudents1;
import tw.edu.chit.model.FailStudents2;
import tw.edu.chit.model.FailStudentsHist;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.MidtermExcluded;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.ScoreStatistic;
import tw.edu.chit.model.ScoreStatus;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Stavg;
import tw.edu.chit.model.StdScore;
import tw.edu.chit.model.StudCounseling;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.TopStudent;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.HibernateQueryResultDataSource;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

public class ReportPrintAction extends BaseLookupDispatchAction {

	private static final String DTIME_LIST1 = "dtimeList1";
	private static final String DTIME_LIST2 = "dtimeList2";
	private static final String[] EXCLUDED_CSCODE = { "GA035", "GB041", "GB042" };

	/**
	 * 進入報表列印作業
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		session.removeAttribute(DTIME_LIST1);
		session.removeAttribute(DTIME_LIST2);
		Toolket.resetCheckboxCookie(response, DTIME_LIST1);
		Toolket.resetCheckboxCookie(response, DTIME_LIST2);
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", sterm);
		aForm.set("year", getYearArray(cm.getNowBy("School_year")));
		session.setAttribute("editMode", false);
		setContentPage(session, "score/ReportPrint.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 預覽作業
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward preview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute(DTIME_LIST1);
		session.removeAttribute(DTIME_LIST2);

		ActionMessages messages = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", request.getParameter("st"));
		aForm.set("printOpt", request.getParameter("p"));
		aForm.set("campusInCharge2", request.getParameter("c"));
		aForm.set("schoolInCharge2", request.getParameter("s"));
		aForm.set("deptInCharge2", request.getParameter("d"));
		aForm.set("classInCharge2", request.getParameter("cl"));
		aForm.set("printInterClass", request.getParameter("printInterClass"));
		// aForm.set("isLiteracy", request.getParameter("il"));
		String printOpt = (String) aForm.get("printOpt");
		String sterm = (String) aForm.get("sterm");
		boolean setupPrinter = false; // 預覽設定印表機為false
		request.getSession(false).setMaxInactiveInterval(-1);

		if ("FinalScoreNotice".equals(printOpt)) {
			// 列印/預覽期末個人成績通知單作業

			System.out.println();
			printFinalScoreNotice(mapping, form, request, response, sterm,
					setupPrinter, false);
			return null;
			/*
			 * try { if (printFinalScoreNotice(mapping, form, request, response,
			 * sterm, setupPrinter, false)) { ServletContext context =
			 * request.getSession().getServletContext(); Map<String, String>
			 * param = new HashMap<String, String>(); File image = new
			 * File(context.getRealPath("/pages/images/2002chitS.jpg"));
			 * param.put("IMAGE", image.getAbsolutePath()); byte[] bytes =
			 * JasperRunManager.runReportToPdf(
			 * JasperReportUtils.getNoResultReport(context), param, new
			 * JREmptyDataSource());
			 * JasperReportUtils.printPdfToFrontEnd(response, bytes); } } catch
			 * (Exception e) { log.error(e.getMessage(), e);
			 * messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			 * "Course.errorN1", e.getMessage())); saveErrors(request,
			 * messages); return
			 * mapping.findForward(IConstants.ACTION_MAIN_NAME); }
			 */

		} else if ("MidtermFinalResult".equals(printOpt)) {
			// 成績總表
			// request.getSession(false).setMaxInactiveInterval(-1);
			// messages = printMidtermFinalResult(mapping, form, request,
			// response, sterm, setupPrinter, true);
			// saveMessages(request, messages);
			if ("yes".equalsIgnoreCase(request.getParameter("il")))
				printMidtermFinalResult4Literacy(mapping, form, request,
						response, sterm);
			else
				printMidtermFinalResult(mapping, form, request, response, sterm);
		} else if ("MidtermFinalAvgResult".equals(printOpt)) {
			// 列印/預覽期中期末全班平均成績作業
			messages = printMidtermFinalAvgResult(mapping, form, request,
					response, sterm, setupPrinter, true);
			saveMessages(request, messages);
		} else if ("MidtermFinalFailStudentsResult".equals(printOpt)) {
			// 預覽期中期末1/2或2/3不及格學生名單作業(全校)
			messages = printMidtermFinalFailStudentsResult(mapping, form,
					request, response, sterm, setupPrinter, true);
			saveMessages(request, messages);
		} else if ("MidtermFinalStatistic".equals(printOpt)) {
			// 預覽期中期末全班成績統計表作業
			messages = printMidtermFinalStatistic(mapping, form, request,
					response, sterm, setupPrinter, true);
			saveMessages(request, messages);
		} else if ("MidtermFinalTopStudentsResult".equals(printOpt)) {
			// 預覽期中期末全班前三名學生名單作業
			messages = printMidtermFinalTopStudentsResult(mapping, form,
					request, response, sterm, setupPrinter, true);
			saveMessages(request, messages);
		} else if ("MidtermFinalFailStudentsByClassResult".equals(printOpt)) {
			// 預覽期中期末1/2或2/3不及格學生名單作業(個人,導師使用)
			messages = printMidtermFinalFailStudentsByClassResult(mapping,
					form, request, response, sterm, setupPrinter, true);
			saveMessages(request, messages);
		} else if ("MidtermFinalFailStudentsTooMuch".equals(printOpt)) {
			// 預覽期中期末教師任教科目不及格人數統計表
			messages = printMidtermFinalFailStudentsTooMuch(mapping, form,
					request, response, sterm, setupPrinter, true);
			saveMessages(request, messages);
		} else if ("MidtermFinalFailTwiceStudentsResult".equals(printOpt)) {
			// 預覽學期成績雙1/2不及格名單
			messages = printMidtermFinalFailTwiceStudentsResult(mapping, form,
					request, response, sterm, setupPrinter, true);
			saveMessages(request, messages);
		} else if ("GraduatingScoreAvg".equals(printOpt)) {
			// 應屆畢業生平均成績表
			printGraduatingScoreAvg(mapping, form, request, response, sterm,
					setupPrinter, true);
		} else if ("ScholarshipStudentsList".equals(printOpt)) {
			// 學優獎學金名冊
			printScholarshipStudentsList(mapping, form, request, response,
					sterm);
		} else if ("StdAdcdList".equals(printOpt)) {
			// 個人加選科目成績清冊
			printStdAdcdList(mapping, form, request, response, sterm);
		} else if ("NoneCounseling".equals(printOpt)) {
			// 未進行輔導紀錄學生清冊
			printNoneCounseling(mapping, form, request, response, sterm);
		}

		return null;
	}

	/**
	 * 查詢納入/不納入期中成績科目清單
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Toolket.resetCheckboxCookie(response, DTIME_LIST1);
		Toolket.resetCheckboxCookie(response, DTIME_LIST2);
		UserCredential credential = (UserCredential) getUserCredential(session);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		String campusInCharge = aForm.getString("campusInCharge2");
		String schoolInCharge = aForm.getString("schoolInCharge2");
		String deptInCharge = aForm.getString("deptInCharge2");
		String departClass = aForm.getString("classInCharge2");
		String classLess = "All".equalsIgnoreCase(departClass) ? campusInCharge
				+ schoolInCharge + deptInCharge : departClass;
		List<Map<String, Object>> dtimeList = cm.getDtimeBy(
				credential.getClassInChargeAry(), "", "",
				aForm.getString("sterm"), "%", "%", "%", classLess, "", "");
		Map<String, List<Map<String, Object>>> map = separatedCourse(excludedT0001And50000(dtimeList));
		session.setAttribute(DTIME_LIST1, map.get(DTIME_LIST1));
		session.setAttribute(DTIME_LIST2, map.get(DTIME_LIST2));
		setContentPage(session, "score/ReportPrint.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 加入不納入期中成績
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward addExclude(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		List<Map<String, Object>> addDtimeList = getExcludedCourseByIndex(
				request, DTIME_LIST2);
		List<MidtermExcluded> mes = processDtimeToMidterExcluded(sm,
				addDtimeList);
		for (MidtermExcluded me : mes)
			sm.txDeleteMidtermExcluded(me);
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Course.messageN1", "新增完成"));
		saveMessages(request, messages);
		return search(mapping, form, request, response);
	}

	/**
	 * 移除不納入期中成績
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward deleteExclude(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ActionMessages messages = new ActionMessages();
		List<Map<String, Object>> delDtimeList = getExcludedCourseByIndex(
				request, DTIME_LIST1);
		List<MidtermExcluded> mes = processDtimeToMidterExcluded(sm,
				delDtimeList);
		sm.txSaveMidtermExcluded(mes);
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Course.messageN1", "移除完成"));
		saveMessages(request, messages);
		return search(mapping, form, request, response);
	}

	/**
	 * 列印/預覽期末個人成績通知單作業
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param schoolTerm
	 *            學期
	 * @param isPrint
	 *            列印與否
	 * @return true if succeed
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	// private boolean printFinalScoreNotice(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response, String schoolTerm,
	// boolean setupPrinter, boolean isPrint) throws Exception {
	private boolean printFinalScoreNotice(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String schoolTerm,
			boolean setupPrinter, boolean isPrint) throws Exception {

		Date date = new Date();

		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Content-disposition",
				"attachment;filename=NabbrBoroDoc" + date.getTime() + ".doc");
		// response.setHeader("Content-disposition","attachment;filename=NabbrBoroDoc"+date.getTime()+".txt");

		DynaActionForm aForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		PrintWriter out = response.getWriter();

		int term = manager.getSchoolTerm();// 淦！為什麼頁面上有拿不到
		int year = manager.getSchoolYear();
		// struts.xml明明就有classLess為什麼get不到？這隻程式全部都要用這種鳥方法拿班級
		String campusInCharge = aForm.getString("campusInCharge2");
		String schoolInCharge = aForm.getString("schoolInCharge2");
		String deptInCharge = aForm.getString("deptInCharge2");
		String departClass = aForm.getString("classInCharge2");
		// System.out.println("classLess="+aForm.getString("classLess"));淦！拿不到

		String classLess = "All".equalsIgnoreCase(departClass) ? campusInCharge
				+ schoolInCharge + deptInCharge : departClass;

		String schoolAddr = "115台北市南港區研究院路三段245號 中華科技大學註冊組(02)2782-1862＃125、126、203";
		if (classLess.indexOf("2") == 0) {
			schoolAddr = "31241 新竹縣橫山鄉中華街200號 中華科技大學新竹分部教務組(03)593-5707＃102";
		}

		List stmds = manager
				.ezGetBy("SELECT s.student_no, s.student_name, t.total_score, s.telephone,"
						+ "c.ClassName, s.curr_addr, s.curr_post, s.parent_name FROM stmd s LEFT OUTER JOIN Just t ON s.student_no=t.student_no, Class c WHERE "
						+ "c.ClassNo=s.depart_class AND s.depart_class LIKE '"
						+ classLess + "%'");

		List selds;
		float total;
		float totalCredit;
		float nopa;
		int pa = 60;
		float score;
		float credit;
		if (classLess.indexOf("G") > 0) {
			pa = 70;
		}
		for (int i = 0; i < stmds.size(); i++) {
			System.out.println("SELECT s.score, c.chi_name, d.credit, d.opt FROM Seld s, Csno c, Dtime d WHERE "
					+ "s.Dtime_oid=d.Oid AND s.Dtime_oid=d.Oid AND c.cscode=d.cscode AND c.chi_name NOT LIKE'%論文%'"
					+ "AND s.student_no='"
					+ ((Map) stmds.get(i)).get("student_no")
					+ "' AND d.Sterm='" + term + "'");
			selds = manager
					.ezGetBy("SELECT s.score, c.chi_name, d.credit, d.opt FROM Seld s, Csno c, Dtime d WHERE "
							+ "s.Dtime_oid=d.Oid AND s.Dtime_oid=d.Oid AND c.cscode=d.cscode AND c.chi_name NOT LIKE'%論文%'"
							+ "AND s.student_no='"
							+ ((Map) stmds.get(i)).get("student_no")
							+ "' AND d.Sterm='" + term + "'");

			total = 0;
			nopa = 0;
			totalCredit = 0;
			for (int j = 0; j < selds.size(); j++) {

				if (((Map) selds.get(j)).get("score") != null) {
					score = Float.parseFloat(((Map) selds.get(j)).get("score")
							.toString());
					credit = Float.parseFloat(((Map) selds.get(j))
							.get("credit").toString());
					total = total + score;
					totalCredit = totalCredit + credit;
					if (score >= pa) {
						nopa = nopa + credit;
					}
				}
			}

			((Map) stmds.get(i)).put("totalCredit", totalCredit);
			((Map) stmds.get(i)).put("nopa", nopa);
			if (total < 1) {
				((Map) stmds.get(i)).put("total", 0);
			} else {
				((Map) stmds.get(i)).put("total", total / selds.size());
			}

			((Map) stmds.get(i)).put("selds", selds);
		}

		stmds = manager.sortListByKeyDESC(stmds, "total");

		// for(int i=0; i<stmds.size(); i++){
		// out.println ( ((Map)stmds.get(i)).get("student_no")+", "+
		// ((Map)stmds.get(i)).get("total"));
		// }

		out.println("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
		out.println("<?mso-application progid='Word.Document'?>");
		out.println("<w:wordDocument xmlns:aml='http://schemas.microsoft.com/aml/2001/core' xmlns:wpc='http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas' xmlns:dt='uuid:C2F41010-65B3-11d1-A29F-00AA00C14882' xmlns:mc='http://schemas.openxmlformats.org/markup-compatibility/2006' xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:v='urn:schemas-microsoft-com:vml' xmlns:w10='urn:schemas-microsoft-com:office:word' xmlns:w='http://schemas.microsoft.com/office/word/2003/wordml' xmlns:wx='http://schemas.microsoft.com/office/word/2003/auxHint' xmlns:wne='http://schemas.microsoft.com/office/word/2006/wordml' xmlns:wsp='http://schemas.microsoft.com/office/word/2003/wordml/sp2' xmlns:sl='http://schemas.microsoft.com/schemaLibrary/2003/core' w:macrosPresent='no' w:embeddedObjPresent='no' w:ocxPresent='no' xml:space='preserve'>");
		out.println("  <w:ignoreSubtree w:val='http://schemas.microsoft.com/office/word/2003/wordml/sp2'/>");
		out.println("  <o:DocumentProperties>");
		out.println("    <o:Title>中華科技大學</o:Title>");
		out.println("    <o:Author>moonwalker</o:Author>");
		out.println("    <o:LastAuthor>USER</o:LastAuthor>");
		out.println("    <o:Revision>10</o:Revision>");
		out.println("    <o:TotalTime>15</o:TotalTime>");
		out.println("    <o:LastPrinted>2013-01-31T02:43:00Z</o:LastPrinted>");
		out.println("    <o:Created>2013-02-01T03:22:00Z</o:Created>");
		out.println("    <o:LastSaved>2013-02-04T02:38:00Z</o:LastSaved>");
		out.println("    <o:Pages>1</o:Pages>");
		out.println("    <o:Words>72</o:Words>");
		out.println("    <o:Characters>411</o:Characters>");
		out.println("    <o:Lines>3</o:Lines>");
		out.println("    <o:Paragraphs>1</o:Paragraphs>");
		out.println("    <o:CharactersWithSpaces>482</o:CharactersWithSpaces>");
		out.println("    <o:Version>14</o:Version>");
		out.println("  </o:DocumentProperties>");
		out.println("  <w:fonts>");
		out.println("    <w:defaultFonts w:ascii='Calibri' w:fareast='新細明體' w:h-ansi='Calibri' w:cs='Times New Roman'/>");
		out.println("    <w:font w:name='Times New Roman'>");
		out.println("      <w:panose-1 w:val='02020603050405020304'/>");
		out.println("      <w:charset w:val='00'/>");
		out.println("      <w:family w:val='Roman'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out.println("      <w:sig w:usb-0='E0002AFF' w:usb-1='C0007841' w:usb-2='00000009' w:usb-3='00000000' w:csb-0='000001FF' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='Arial'>");
		out.println("      <w:panose-1 w:val='020B0604020202020204'/>");
		out.println("      <w:charset w:val='00'/>");
		out.println("      <w:family w:val='Swiss'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out.println("      <w:sig w:usb-0='E0002AFF' w:usb-1='C0007843' w:usb-2='00000009' w:usb-3='00000000' w:csb-0='000001FF' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='新細明體'>");
		out.println("      <w:altName w:val='PMingLiU'/>");
		out.println("      <w:panose-1 w:val='02020500000000000000'/>");
		out.println("      <w:charset w:val='88'/>");
		out.println("      <w:family w:val='Roman'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out.println("      <w:sig w:usb-0='A00002FF' w:usb-1='28CFFCFA' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='新細明體'>");
		out.println("      <w:altName w:val='PMingLiU'/>");
		out.println("      <w:panose-1 w:val='02020500000000000000'/>");
		out.println("      <w:charset w:val='88'/>");
		out.println("      <w:family w:val='Roman'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out.println("      <w:sig w:usb-0='A00002FF' w:usb-1='28CFFCFA' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='Calibri'>");
		out.println("      <w:panose-1 w:val='020F0502020204030204'/>");
		out.println("      <w:charset w:val='00'/>");
		out.println("      <w:family w:val='Swiss'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out.println("      <w:sig w:usb-0='E00002FF' w:usb-1='4000ACFF' w:usb-2='00000001' w:usb-3='00000000' w:csb-0='0000019F' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='標楷體'>");
		out.println("      <w:altName w:val='.D·￠Ae'/>");
		out.println("      <w:panose-1 w:val='03000509000000000000'/>");
		out.println("      <w:charset w:val='88'/>");
		out.println("      <w:family w:val='Script'/>");
		out.println("      <w:pitch w:val='fixed'/>");
		out.println("      <w:sig w:usb-0='00000003' w:usb-1='080E0000' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='@標楷體'>");
		out.println("      <w:panose-1 w:val='03000509000000000000'/>");
		out.println("      <w:charset w:val='88'/>");
		out.println("      <w:family w:val='Script'/>");
		out.println("      <w:pitch w:val='fixed'/>");
		out.println("      <w:sig w:usb-0='00000003' w:usb-1='080E0000' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("    <w:font w:name='@新細明體'>");
		out.println("      <w:panose-1 w:val='02020500000000000000'/>");
		out.println("      <w:charset w:val='88'/>");
		out.println("      <w:family w:val='Roman'/>");
		out.println("      <w:pitch w:val='variable'/>");
		out.println("      <w:sig w:usb-0='A00002FF' w:usb-1='28CFFCFA' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>");
		out.println("    </w:font>");
		out.println("  </w:fonts>");
		out.println("  <w:lists>");
		out.println("    <w:listDef w:listDefId='0'>");
		out.println("      <w:lsid w:val='062C05D2'/>");
		out.println("      <w:plt w:val='Multilevel'/>");
		out.println("      <w:tmpl w:val='90244C14'/>");
		out.println("      <w:lvl w:ilvl='0'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='2'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='3'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='4'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='5'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='6'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='7'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='8'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='1'>");
		out.println("      <w:lsid w:val='07101A03'/>");
		out.println("      <w:plt w:val='Multilevel'/>");
		out.println("      <w:tmpl w:val='59E07994'/>");
		out.println("      <w:lvl w:ilvl='0'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='0'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1'>");
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
		out.println("      <w:lvl w:ilvl='2'>");
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
		out.println("      <w:lvl w:ilvl='3'>");
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
		out.println("      <w:lvl w:ilvl='4'>");
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
		out.println("      <w:lvl w:ilvl='5'>");
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
		out.println("      <w:lvl w:ilvl='6'>");
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
		out.println("      <w:lvl w:ilvl='7'>");
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
		out.println("      <w:lvl w:ilvl='8'>");
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
		out.println("      <w:lsid w:val='0A1A2FCB'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='59E07994'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='0764E92A'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='0'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lsid w:val='11745157'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='9E246E78'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='0409000F'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='480' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='4'>");
		out.println("      <w:lsid w:val='13CA6ADD'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='F446CB5A'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='5E86AAE8'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='0'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lsid w:val='16D04DAE'/>");
		out.println("      <w:plt w:val='Multilevel'/>");
		out.println("      <w:tmpl w:val='90244C14'/>");
		out.println("      <w:lvl w:ilvl='0'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='2'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='3'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='4'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='5'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='6'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='7'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='8'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='6'>");
		out.println("      <w:lsid w:val='23A0776B'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='18DAD676'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='BE681162'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='0'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("    <w:listDef w:listDefId='7'>");
		out.println("      <w:lsid w:val='4F414128'/>");
		out.println("      <w:plt w:val='Multilevel'/>");
		out.println("      <w:tmpl w:val='90244C14'/>");
		out.println("      <w:lvl w:ilvl='0'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='2'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='3'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='4'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='5'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='6'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='7'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='8'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='8'>");
		out.println("      <w:lsid w:val='555143EC'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='E9BED350'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='0E18F0E2'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='0'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("    <w:listDef w:listDefId='9'>");
		out.println("      <w:lsid w:val='57DB41C6'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='0A420322'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='E4005444'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='0'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("    <w:listDef w:listDefId='10'>");
		out.println("      <w:lsid w:val='593A4CF4'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='097C17A4'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='B17C8B8A'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='0'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("    <w:listDef w:listDefId='11'>");
		out.println("      <w:lsid w:val='5A4C4F04'/>");
		out.println("      <w:plt w:val='Multilevel'/>");
		out.println("      <w:tmpl w:val='90244C14'/>");
		out.println("      <w:lvl w:ilvl='0'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='2'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='3'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='4'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='5'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='6'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='7'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='8'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='12'>");
		out.println("      <w:lsid w:val='651B0B06'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='72FC8A7A'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='B520FC52'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("          <w:color w:val='auto'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='13'>");
		out.println("      <w:lsid w:val='66AC2038'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='9C76E196'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='2C7ABE44'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='0'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("    <w:listDef w:listDefId='14'>");
		out.println("      <w:lsid w:val='6ACB40F9'/>");
		out.println("      <w:plt w:val='Multilevel'/>");
		out.println("      <w:tmpl w:val='90244C14'/>");
		out.println("      <w:lvl w:ilvl='0'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='2'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='3'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='4'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='5'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='6'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='7'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='8'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='15'>");
		out.println("      <w:lsid w:val='70901BE2'/>");
		out.println("      <w:plt w:val='Multilevel'/>");
		out.println("      <w:tmpl w:val='90244C14'/>");
		out.println("      <w:lvl w:ilvl='0'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%2、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='960' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='2'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%3.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1440' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='3'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%4.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='1920' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='4'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%5、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2400' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='5'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%6.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='2880' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='6'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%7.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3360' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='7'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='30'/>");
		out.println("        <w:lvlText w:val='%8、'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='3840' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='8'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:nfc w:val='2'/>");
		out.println("        <w:lvlText w:val='%9.'/>");
		out.println("        <w:lvlJc w:val='right'/>");
		out.println("        <w:pPr>");
		out.println("          <w:ind w:left='4320' w:hanging='480'/>");
		out.println("        </w:pPr>");
		out.println("      </w:lvl>");
		out.println("    </w:listDef>");
		out.println("    <w:listDef w:listDefId='16'>");
		out.println("      <w:lsid w:val='77CC1009'/>");
		out.println("      <w:plt w:val='HybridMultilevel'/>");
		out.println("      <w:tmpl w:val='9CACF1A2'/>");
		out.println("      <w:lvl w:ilvl='0' w:tplc='BF56F192'>");
		out.println("        <w:start w:val='1'/>");
		out.println("        <w:lvlText w:val='%1.'/>");
		out.println("        <w:lvlJc w:val='left'/>");
		out.println("        <w:pPr>");
		out.println("          <w:tabs>");
		out.println("            <w:tab w:val='list' w:pos='0'/>");
		out.println("          </w:tabs>");
		out.println("          <w:ind w:left='360' w:hanging='360'/>");
		out.println("        </w:pPr>");
		out.println("        <w:rPr>");
		out.println("          <w:rFonts w:hint='default'/>");
		out.println("        </w:rPr>");
		out.println("      </w:lvl>");
		out.println("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
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
		out.println("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
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
		out.println("    <w:list w:ilfo='1'>");
		out.println("      <w:ilst w:val='3'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='2'>");
		out.println("      <w:ilst w:val='12'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='3'>");
		out.println("      <w:ilst w:val='7'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='4'>");
		out.println("      <w:ilst w:val='8'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='5'>");
		out.println("      <w:ilst w:val='6'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='6'>");
		out.println("      <w:ilst w:val='15'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='7'>");
		out.println("      <w:ilst w:val='13'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='8'>");
		out.println("      <w:ilst w:val='11'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='9'>");
		out.println("      <w:ilst w:val='2'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='10'>");
		out.println("      <w:ilst w:val='1'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='11'>");
		out.println("      <w:ilst w:val='4'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='12'>");
		out.println("      <w:ilst w:val='5'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='13'>");
		out.println("      <w:ilst w:val='9'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='14'>");
		out.println("      <w:ilst w:val='0'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='15'>");
		out.println("      <w:ilst w:val='16'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='16'>");
		out.println("      <w:ilst w:val='14'/>");
		out.println("    </w:list>");
		out.println("    <w:list w:ilfo='17'>");
		out.println("      <w:ilst w:val='10'/>");
		out.println("    </w:list>");
		out.println("  </w:lists>");
		out.println("  <w:styles>");
		out.println("    <w:versionOfBuiltInStylenames w:val='7'/>");
		out.println("    <w:latentStyles w:defLockedState='off' w:latentStyleCount='267'>");
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
		out.println("      <w:lsdException w:name='No Spacing'/>");
		out.println("      <w:lsdException w:name='List Paragraph'/>");
		out.println("      <w:lsdException w:name='Quote'/>");
		out.println("      <w:lsdException w:name='Intense Quote'/>");
		out.println("      <w:lsdException w:name='Subtle Emphasis'/>");
		out.println("      <w:lsdException w:name='Intense Emphasis'/>");
		out.println("      <w:lsdException w:name='Subtle Reference'/>");
		out.println("      <w:lsdException w:name='Intense Reference'/>");
		out.println("      <w:lsdException w:name='Book Title'/>");
		out.println("      <w:lsdException w:name='TOC Heading'/>");
		out.println("    </w:latentStyles>");
		out.println("    <w:style w:type='paragraph' w:default='on' w:styleId='a'>");
		out.println("      <w:name w:val='Normal'/>");
		out.println("      <wx:uiName wx:val='內文'/>");
		out.println("      <w:rsid w:val='00966353'/>");
		out.println("      <w:pPr>");
		out.println("        <w:widowControl w:val='off'/>");
		out.println("      </w:pPr>");
		out.println("      <w:rPr>");
		out.println("        <wx:font wx:val='Calibri'/>");
		out.println("        <w:kern w:val='2'/>");
		out.println("        <w:sz w:val='24'/>");
		out.println("        <w:sz-cs w:val='22'/>");
		out.println("        <w:lang w:val='EN-US' w:fareast='ZH-TW' w:bidi='AR-SA'/>");
		out.println("      </w:rPr>");
		out.println("    </w:style>");
		out.println("    <w:style w:type='character' w:default='on' w:styleId='a0'>");
		out.println("      <w:name w:val='Default Paragraph Font'/>");
		out.println("      <wx:uiName wx:val='預設段落字型'/>");
		out.println("    </w:style>");
		out.println("    <w:style w:type='table' w:default='on' w:styleId='a1'>");
		out.println("      <w:name w:val='Normal Table'/>");
		out.println("      <wx:uiName wx:val='表格內文'/>");
		out.println("      <w:rPr>");
		out.println("        <wx:font wx:val='Calibri'/>");
		out.println("        <w:lang w:val='EN-US' w:fareast='ZH-TW' w:bidi='AR-SA'/>");
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
		out.println("    <w:style w:type='list' w:default='on' w:styleId='a2'>");
		out.println("      <w:name w:val='No List'/>");
		out.println("      <wx:uiName wx:val='無清單'/>");
		out.println("    </w:style>");
		out.println("    <w:style w:type='table' w:styleId='a3'>");
		out.println("      <w:name w:val='Table Grid'/>");
		out.println("      <wx:uiName wx:val='表格格線'/>");
		out.println("      <w:basedOn w:val='a1'/>");
		out.println("      <w:rsid w:val='00A12CDB'/>");
		out.println("      <w:rPr>");
		out.println("        <wx:font wx:val='Calibri'/>");
		out.println("      </w:rPr>");
		out.println("      <w:tblPr>");
		out.println("        <w:tblInd w:w='0' w:type='dxa'/>");
		out.println("        <w:tblBorders>");
		out.println("          <w:top w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
		out.println("          <w:left w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
		out.println("          <w:bottom w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
		out.println("          <w:right w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
		out.println("          <w:insideH w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
		out.println("          <w:insideV w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
		out.println("        </w:tblBorders>");
		out.println("        <w:tblCellMar>");
		out.println("          <w:top w:w='0' w:type='dxa'/>");
		out.println("          <w:left w:w='108' w:type='dxa'/>");
		out.println("          <w:bottom w:w='0' w:type='dxa'/>");
		out.println("          <w:right w:w='108' w:type='dxa'/>");
		out.println("        </w:tblCellMar>");
		out.println("      </w:tblPr>");
		out.println("    </w:style>");
		out.println("    <w:style w:type='paragraph' w:styleId='a4'>");
		out.println("      <w:name w:val='List Paragraph'/>");
		out.println("      <wx:uiName wx:val='清單段落'/>");
		out.println("      <w:basedOn w:val='a'/>");
		out.println("      <w:rsid w:val='006461A7'/>");
		out.println("      <w:pPr>");
		out.println("        <w:ind w:left-chars='200' w:left='200'/>");
		out.println("      </w:pPr>");
		out.println("      <w:rPr>");
		out.println("        <wx:font wx:val='Calibri'/>");
		out.println("      </w:rPr>");
		out.println("    </w:style>");
		out.println("    <w:style w:type='paragraph' w:styleId='a5'>");
		out.println("      <w:name w:val='Balloon Text'/>");
		out.println("      <wx:uiName wx:val='註解方塊文字'/>");
		out.println("      <w:basedOn w:val='a'/>");
		out.println("      <w:rsid w:val='00275EF4'/>");
		out.println("      <w:rPr>");
		out.println("        <w:rFonts w:ascii='Arial' w:h-ansi='Arial'/>");
		out.println("        <wx:font wx:val='Arial'/>");
		out.println("        <w:sz w:val='18'/>");
		out.println("        <w:sz-cs w:val='18'/>");
		out.println("      </w:rPr>");
		out.println("    </w:style>");
		out.println("  </w:styles>");
		out.println("  <w:shapeDefaults>");
		out.println("    <o:shapedefaults v:ext='edit' spidmax='1026'/>");
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
		out.println("    <w:defaultTabStop w:val='480'/>");
		out.println("    <w:drawingGridHorizontalSpacing w:val='120'/>");
		out.println("    <w:displayHorizontalDrawingGridEvery w:val='0'/>");
		out.println("    <w:displayVerticalDrawingGridEvery w:val='2'/>");
		out.println("    <w:punctuationKerning/>");
		out.println("    <w:characterSpacingControl w:val='CompressPunctuation'/>");
		out.println("    <w:optimizeForBrowser/>");
		out.println("    <w:allowPNG/>");
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
		out.println("      <wsp:rsidRoot wsp:val='00CD0BF5'/>");
		out.println("      <wsp:rsid wsp:val='00107FDF'/>");
		out.println("      <wsp:rsid wsp:val='001537BC'/>");
		out.println("      <wsp:rsid wsp:val='0017666E'/>");
		out.println("      <wsp:rsid wsp:val='00227CC9'/>");
		out.println("      <wsp:rsid wsp:val='00244B7A'/>");
		out.println("      <wsp:rsid wsp:val='00260703'/>");
		out.println("      <wsp:rsid wsp:val='00275EF4'/>");
		out.println("      <wsp:rsid wsp:val='002E6B1A'/>");
		out.println("      <wsp:rsid wsp:val='00347F50'/>");
		out.println("      <wsp:rsid wsp:val='003E3A1C'/>");
		out.println("      <wsp:rsid wsp:val='00423950'/>");
		out.println("      <wsp:rsid wsp:val='0059664B'/>");
		out.println("      <wsp:rsid wsp:val='00597E25'/>");
		out.println("      <wsp:rsid wsp:val='006353B7'/>");
		out.println("      <wsp:rsid wsp:val='006461A7'/>");
		out.println("      <wsp:rsid wsp:val='006D5C13'/>");
		out.println("      <wsp:rsid wsp:val='006E4A69'/>");
		out.println("      <wsp:rsid wsp:val='007B6628'/>");
		out.println("      <wsp:rsid wsp:val='0082184F'/>");
		out.println("      <wsp:rsid wsp:val='008F3913'/>");
		out.println("      <wsp:rsid wsp:val='00953866'/>");
		out.println("      <wsp:rsid wsp:val='00966353'/>");
		out.println("      <wsp:rsid wsp:val='00986905'/>");
		out.println("      <wsp:rsid wsp:val='00A12CDB'/>");
		out.println("      <wsp:rsid wsp:val='00A17A28'/>");
		out.println("      <wsp:rsid wsp:val='00AC7BAD'/>");
		out.println("      <wsp:rsid wsp:val='00AE6DEE'/>");
		out.println("      <wsp:rsid wsp:val='00B175F9'/>");
		out.println("      <wsp:rsid wsp:val='00B73482'/>");
		out.println("      <wsp:rsid wsp:val='00BC0BA3'/>");
		out.println("      <wsp:rsid wsp:val='00C000FE'/>");
		out.println("      <wsp:rsid wsp:val='00C51466'/>");
		out.println("      <wsp:rsid wsp:val='00CA0FFF'/>");
		out.println("      <wsp:rsid wsp:val='00CD0BF5'/>");
		out.println("      <wsp:rsid wsp:val='00D43136'/>");
		out.println("      <wsp:rsid wsp:val='00D53B61'/>");
		out.println("      <wsp:rsid wsp:val='00DC77A5'/>");
		out.println("      <wsp:rsid wsp:val='00DE1D69'/>");
		out.println("      <wsp:rsid wsp:val='00DF05E6'/>");
		out.println("      <wsp:rsid wsp:val='00E93993'/>");
		out.println("      <wsp:rsid wsp:val='00F25446'/>");
		out.println("    </wsp:rsids>");
		out.println("  </w:docPr>");
		out.println("  <w:body>");
		out.println("    <wx:sect>");

		// List selds;
		for (int i = 0; i < stmds.size(); i++) {
			out.println("      <w:p wsp:rsidR='00F25446' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='00AE6DEE'>");
			out.println("        <w:pPr>");
			out.println("          <w:jc w:val='center'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r wsp:rsidRPr='00227CC9'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>中華科技大學</w:t>");
			out.println("        </w:r>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00347F50' wsp:rsidP='00AE6DEE'>");
			out.println("        <w:pPr>");
			out.println("          <w:jc w:val='center'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>" + year + "</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00CD0BF5' wsp:rsidRPr='00227CC9'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>學年度第</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>" + term + "</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00CD0BF5' wsp:rsidRPr='00227CC9'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>學期學生學期成績單</w:t>");
			out.println("        </w:r>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='00CD0BF5'>");
			out.println("        <w:pPr>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r wsp:rsidRPr='00227CC9'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>班級：</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00347F50'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>"
					+ ((Map) stmds.get(i)).get("ClassName") + "</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00423950'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t></w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidRPr='00227CC9'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>學號：</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00347F50'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>"
					+ ((Map) stmds.get(i)).get("student_no") + "</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00423950'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t></w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidRPr='00227CC9'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>姓名：</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00347F50'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>"
					+ ((Map) stmds.get(i)).get("student_name") + "</w:t>");
			out.println("        </w:r>");
			out.println("      </w:p>");

			selds = (List) ((Map) stmds.get(i)).get("selds");
			out.println("      <w:tbl>");
			out.println("        <w:tblPr>");
			out.println("          <w:tblW w:w='5000' w:type='pct'/>");
			out.println("          <w:tblLook w:val='04A0'/>");
			out.println("        </w:tblPr>");
			out.println("        <w:tblGrid>");
			out.println("          <w:gridCol w:w='2492'/>");
			out.println("          <w:gridCol w:w='2492'/>");
			out.println("          <w:gridCol w:w='2492'/>");
			out.println("          <w:gridCol w:w='2492'/>");
			out.println("        </w:tblGrid>");
			out.println("        <w:tr wsp:rsidR='0017666E' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>");
			out.println("          <w:trPr>");
			out.println("            <w:trHeight w:val='56'/>");
			out.println("          </w:trPr>");
			out.println("          <w:tc>");
			out.println("            <w:tcPr>");
			out.println("              <w:tcW w:w='1250' w:type='pct'/>");
			out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
			out.println("            </w:tcPr>");
			out.println("            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>");
			out.println("              <w:pPr>");
			out.println("                <w:adjustRightInd w:val='off'/>");
			out.println("                <w:snapToGrid w:val='off'/>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                  <w:sz-cs w:val='24'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("              <w:r wsp:rsidRPr='001537BC'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                  <w:sz-cs w:val='24'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>科目名稱</w:t>");
			out.println("              </w:r>");
			out.println("            </w:p>");
			out.println("          </w:tc>");
			out.println("          <w:tc>");
			out.println("            <w:tcPr>");
			out.println("              <w:tcW w:w='1250' w:type='pct'/>");
			out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
			out.println("            </w:tcPr>");
			out.println("            <w:p wsp:rsidR='00C51466' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>");
			out.println("              <w:pPr>");
			out.println("                <w:adjustRightInd w:val='off'/>");
			out.println("                <w:snapToGrid w:val='off'/>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                  <w:sz-cs w:val='24'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("              <w:r wsp:rsidRPr='001537BC'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                  <w:sz-cs w:val='24'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>成績</w:t>");
			out.println("              </w:r>");
			out.println("            </w:p>");
			out.println("          </w:tc>");
			out.println("          <w:tc>");
			out.println("            <w:tcPr>");
			out.println("              <w:tcW w:w='1250' w:type='pct'/>");
			out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
			out.println("            </w:tcPr>");
			out.println("            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>");
			out.println("              <w:pPr>");
			out.println("                <w:adjustRightInd w:val='off'/>");
			out.println("                <w:snapToGrid w:val='off'/>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                  <w:sz-cs w:val='24'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("              <w:r wsp:rsidRPr='001537BC'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                  <w:sz-cs w:val='24'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>選別</w:t>");
			out.println("              </w:r>");
			out.println("            </w:p>");
			out.println("          </w:tc>");
			out.println("          <w:tc>");
			out.println("            <w:tcPr>");
			out.println("              <w:tcW w:w='1250' w:type='pct'/>");
			out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
			out.println("            </w:tcPr>");
			out.println("            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>");
			out.println("              <w:pPr>");
			out.println("                <w:adjustRightInd w:val='off'/>");
			out.println("                <w:snapToGrid w:val='off'/>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                  <w:sz-cs w:val='24'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("              <w:r wsp:rsidRPr='001537BC'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                  <w:sz-cs w:val='24'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>學分數</w:t>");
			out.println("              </w:r>");
			out.println("            </w:p>");
			out.println("          </w:tc>");
			out.println("        </w:tr>");

			for (int j = 0; j < selds.size(); j++) {

				
				System.out.println(selds.size());
				out.println("        <w:tr wsp:rsidR='0017666E' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>");
				out.println("          <w:trPr>");
				out.println("            <w:trHeight w:val='56'/>");
				out.println("          </w:trPr>");
				out.println("          <w:tc>");
				out.println("            <w:tcPr>");
				out.println("              <w:tcW w:w='1250' w:type='pct'/>");
				out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
				out.println("            </w:tcPr>");
				out.println("            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00347F50' wsp:rsidP='001537BC'>");
				out.println("              <w:pPr>");
				out.println("                <w:adjustRightInd w:val='off'/>");
				out.println("                <w:snapToGrid w:val='off'/>");
				out.println("                <w:rPr>");
				out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
				out.println("                  <wx:font wx:val='標楷體'/>");
				out.println("                  <w:sz-cs w:val='24'/>");
				out.println("                </w:rPr>");
				out.println("              </w:pPr>");
				out.println("              <w:r>");
				out.println("                <w:rPr>");
				out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("                  <wx:font wx:val='標楷體'/>");
				out.println("                  <w:sz-cs w:val='24'/>");
				out.println("                </w:rPr>");
				out.println("                <w:t>"
						+ ((Map) selds.get(j)).get("chi_name") + "</w:t>");
				out.println("              </w:r>");
				out.println("            </w:p>");
				out.println("          </w:tc>");
				out.println("          <w:tc>");
				out.println("            <w:tcPr>");
				out.println("              <w:tcW w:w='1250' w:type='pct'/>");
				out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
				out.println("            </w:tcPr>");
				out.println("            <w:p wsp:rsidR='00C51466' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00347F50' wsp:rsidP='00347F50'>");
				out.println("              <w:pPr>");
				out.println("                <w:adjustRightInd w:val='off'/>");
				out.println("                <w:snapToGrid w:val='off'/>");
				out.println("                <w:rPr>");
				out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
				out.println("                  <wx:font wx:val='標楷體'/>");
				out.println("                  <w:sz-cs w:val='24'/>");
				out.println("                </w:rPr>");
				out.println("              </w:pPr>");
				out.println("              <w:r>");
				out.println("                <w:rPr>");
				out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("                  <wx:font wx:val='標楷體'/>");
				out.println("                  <w:sz-cs w:val='24'/>");
				out.println("                </w:rPr>");
				out.println("                <w:t>"
						+ ((Map) selds.get(j)).get("score") + "</w:t>");
				out.println("              </w:r>");
				out.println("            </w:p>");
				out.println("          </w:tc>");
				out.println("          <w:tc>");
				out.println("            <w:tcPr>");
				out.println("              <w:tcW w:w='1250' w:type='pct'/>");
				out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
				out.println("            </w:tcPr>");
				out.println("            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00347F50' wsp:rsidP='001537BC'>");
				out.println("              <w:pPr>");
				out.println("                <w:adjustRightInd w:val='off'/>");
				out.println("                <w:snapToGrid w:val='off'/>");
				out.println("                <w:rPr>");
				out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
				out.println("                  <wx:font wx:val='標楷體'/>");
				out.println("                  <w:sz-cs w:val='24'/>");
				out.println("                </w:rPr>");
				out.println("              </w:pPr>");
				out.println("              <w:r>");
				out.println("                <w:rPr>");
				out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("                  <wx:font wx:val='標楷體'/>");
				out.println("                  <w:sz-cs w:val='24'/>");
				out.println("                </w:rPr>");
				if (((Map) selds.get(j)).get("opt").equals("1")) {
					out.println("                <w:t>必修</w:t>");
				}
				if (((Map) selds.get(j)).get("opt").equals("2")) {
					out.println("                <w:t>選修</w:t>");
				}
				if (((Map) selds.get(j)).get("opt").equals("3")) {
					out.println("                <w:t>通識</w:t>");
				}
				out.println("              </w:r>");
				out.println("            </w:p>");
				out.println("          </w:tc>");
				out.println("          <w:tc>");
				out.println("            <w:tcPr>");
				out.println("              <w:tcW w:w='1250' w:type='pct'/>");
				out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
				out.println("            </w:tcPr>");
				out.println("            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00347F50' wsp:rsidP='001537BC'>");
				out.println("              <w:pPr>");
				out.println("                <w:adjustRightInd w:val='off'/>");
				out.println("                <w:snapToGrid w:val='off'/>");
				out.println("                <w:rPr>");
				out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
				out.println("                  <wx:font wx:val='標楷體'/>");
				out.println("                  <w:sz-cs w:val='24'/>");
				out.println("                </w:rPr>");
				out.println("              </w:pPr>");
				out.println("              <w:r>");
				out.println("                <w:rPr>");
				out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
				out.println("                  <wx:font wx:val='標楷體'/>");
				out.println("                  <w:sz-cs w:val='24'/>");
				out.println("                </w:rPr>");
				out.println("                <w:t>"
						+ ((Map) selds.get(j)).get("credit") + "</w:t>");
				out.println("              </w:r>");
				out.println("            </w:p>");
				out.println("          </w:tc>");
				out.println("        </w:tr>");
			}

			for (int j = 0; j < 15 - selds.size(); j++) {
				out.println("        <w:tr wsp:rsidR='0017666E' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>");
				out.println("          <w:trPr>");
				out.println("            <w:trHeight w:val='56'/>");
				out.println("          </w:trPr>");
				out.println("          <w:tc>");
				out.println("            <w:tcPr>");
				out.println("              <w:tcW w:w='1250' w:type='pct'/>");
				out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
				out.println("            </w:tcPr>");
				out.println("            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>");
				out.println("              <w:pPr>");
				out.println("                <w:adjustRightInd w:val='off'/>");
				out.println("                <w:snapToGrid w:val='off'/>");
				out.println("                <w:rPr>");
				out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
				out.println("                  <wx:font wx:val='標楷體'/>");
				out.println("                  <w:sz-cs w:val='24'/>");
				out.println("                </w:rPr>");
				out.println("              </w:pPr>");
				out.println("            </w:p>");
				out.println("          </w:tc>");
				out.println("          <w:tc>");
				out.println("            <w:tcPr>");
				out.println("              <w:tcW w:w='1250' w:type='pct'/>");
				out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
				out.println("            </w:tcPr>");
				out.println("            <w:p wsp:rsidR='00C51466' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00C51466' wsp:rsidP='001537BC'>");
				out.println("              <w:pPr>");
				out.println("                <w:adjustRightInd w:val='off'/>");
				out.println("                <w:snapToGrid w:val='off'/>");
				out.println("                <w:rPr>");
				out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
				out.println("                  <wx:font wx:val='標楷體'/>");
				out.println("                  <w:sz-cs w:val='24'/>");
				out.println("                </w:rPr>");
				out.println("              </w:pPr>");
				out.println("            </w:p>");
				out.println("          </w:tc>");
				out.println("          <w:tc>");
				out.println("            <w:tcPr>");
				out.println("              <w:tcW w:w='1250' w:type='pct'/>");
				out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
				out.println("            </w:tcPr>");
				out.println("            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>");
				out.println("              <w:pPr>");
				out.println("                <w:adjustRightInd w:val='off'/>");
				out.println("                <w:snapToGrid w:val='off'/>");
				out.println("                <w:rPr>");
				out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
				out.println("                  <wx:font wx:val='標楷體'/>");
				out.println("                  <w:sz-cs w:val='24'/>");
				out.println("                </w:rPr>");
				out.println("              </w:pPr>");
				out.println("            </w:p>");
				out.println("          </w:tc>");
				out.println("          <w:tc>");
				out.println("            <w:tcPr>");
				out.println("              <w:tcW w:w='1250' w:type='pct'/>");
				out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
				out.println("            </w:tcPr>");
				out.println("            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>");
				out.println("              <w:pPr>");
				out.println("                <w:adjustRightInd w:val='off'/>");
				out.println("                <w:snapToGrid w:val='off'/>");
				out.println("                <w:rPr>");
				out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
				out.println("                  <wx:font wx:val='標楷體'/>");
				out.println("                  <w:sz-cs w:val='24'/>");
				out.println("                </w:rPr>");
				out.println("              </w:pPr>");
				out.println("            </w:p>");
				out.println("          </w:tc>");
				out.println("        </w:tr>");
			}

			out.println("      </w:tbl>");
			out.println("      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='00CD0BF5'>");
			out.println("        <w:pPr>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("      </w:p>");
			out.println("      <w:tbl>");
			out.println("        <w:tblPr>");
			out.println("          <w:tblW w:w='5000' w:type='pct'/>");
			out.println("          <w:tblLook w:val='04A0'/>");
			out.println("        </w:tblPr>");
			out.println("        <w:tblGrid>");
			out.println("          <w:gridCol w:w='4984'/>");
			out.println("          <w:gridCol w:w='4984'/>");
			out.println("        </w:tblGrid>");
			out.println("        <w:tr wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>");
			out.println("          <w:tc>");
			out.println("            <w:tcPr>");
			out.println("              <w:tcW w:w='2500' w:type='pct'/>");
			out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
			out.println("            </w:tcPr>");
			out.println("            <w:p wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00A12CDB' wsp:rsidP='00AE6DEE'>");
			out.println("              <w:pPr>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("              <w:r wsp:rsidRPr='001537BC'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>共修學分數:</w:t>");
			out.println("              </w:r>");
			out.println("              <w:r wsp:rsidR='00347F50'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>"
					+ ((Map) stmds.get(i)).get("totalCredit") + "</w:t>");
			out.println("              </w:r>");
			out.println("            </w:p>");
			out.println("          </w:tc>");
			out.println("          <w:tc>");
			out.println("            <w:tcPr>");
			out.println("              <w:tcW w:w='2500' w:type='pct'/>");
			out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
			out.println("            </w:tcPr>");
			out.println("            <w:p wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00A12CDB' wsp:rsidP='00AE6DEE'>");
			out.println("              <w:pPr>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("              <w:r wsp:rsidRPr='001537BC'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>實得學分數:</w:t>");
			out.println("              </w:r>");
			out.println("              <w:r wsp:rsidR='00347F50'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>"
					+ ((Map) stmds.get(i)).get("totalCredit") + "</w:t>");
			out.println("              </w:r>");
			out.println("            </w:p>");
			out.println("          </w:tc>");
			out.println("        </w:tr>");
			out.println("        <w:tr wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>");
			out.println("          <w:tc>");
			out.println("            <w:tcPr>");
			out.println("              <w:tcW w:w='2500' w:type='pct'/>");
			out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
			out.println("            </w:tcPr>");
			out.println("            <w:p wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00A12CDB' wsp:rsidP='00AE6DEE'>");
			out.println("              <w:pPr>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("              <w:r wsp:rsidRPr='001537BC'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>學業總平均:</w:t>");
			out.println("              </w:r>");
			out.println("              <w:r wsp:rsidR='00347F50'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>"
					+ ((Map) stmds.get(i)).get("total") + "</w:t>");
			out.println("              </w:r>");
			out.println("            </w:p>");
			out.println("          </w:tc>");
			out.println("          <w:tc>");
			out.println("            <w:tcPr>");
			out.println("              <w:tcW w:w='2500' w:type='pct'/>");
			out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
			out.println("            </w:tcPr>");
			out.println("            <w:p wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00A12CDB' wsp:rsidP='00CD0BF5'>");
			out.println("              <w:pPr>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("              <w:r wsp:rsidRPr='001537BC'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>名次:</w:t>");
			out.println("              </w:r>");
			out.println("              <w:r wsp:rsidR='00347F50'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>" + (i + 1) + "</w:t>");
			out.println("              </w:r>");
			out.println("            </w:p>");
			out.println("          </w:tc>");
			out.println("        </w:tr>");
			out.println("        <w:tr wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>");
			out.println("          <w:tc>");
			out.println("            <w:tcPr>");
			out.println("              <w:tcW w:w='2500' w:type='pct'/>");
			out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
			out.println("            </w:tcPr>");
			out.println("            <w:p wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00A12CDB' wsp:rsidP='00AE6DEE'>");
			out.println("              <w:pPr>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("              <w:r wsp:rsidRPr='001537BC'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>操行成績:</w:t>");
			out.println("              </w:r>");
			out.println("              <w:r wsp:rsidR='00347F50'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>"
					+ ((Map) stmds.get(i)).get("total_score") + "</w:t>");
			out.println("              </w:r>");
			out.println("            </w:p>");
			out.println("          </w:tc>");
			out.println("          <w:tc>");
			out.println("            <w:tcPr>");
			out.println("              <w:tcW w:w='2500' w:type='pct'/>");
			out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
			out.println("            </w:tcPr>");
			out.println("            <w:p wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00A12CDB' wsp:rsidP='00CD0BF5'>");
			out.println("              <w:pPr>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("            </w:p>");
			out.println("          </w:tc>");
			out.println("        </w:tr>");
			out.println("      </w:tbl>");
			out.println("      <w:p wsp:rsidR='00597E25' wsp:rsidRDefault='00597E25' wsp:rsidP='00AE6DEE'>");
			out.println("        <w:pPr>");
			out.println("          <w:spacing w:line='280' w:line-rule='exact'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='00AE6DEE'>");
			out.println("        <w:pPr>");
			out.println("          <w:spacing w:line='280' w:line-rule='exact'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r wsp:rsidRPr='00227CC9'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>註：</w:t>");
			out.println("        </w:r>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='00347F50' wsp:rsidRDefault='00986905' wsp:rsidP='00986905'>");
			out.println("        <w:pPr>");
			out.println("          <w:pStyle w:val='a4'/>");
			out.println("          <w:spacing w:line='280' w:line-rule='exact'/>");
			out.println("          <w:ind w:left-chars='0' w:left='0'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>1.</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00CD0BF5' wsp:rsidRPr='00347F50'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>學期學業成績不及格科目之學分數連續兩學期達二分之ㄧ者，應予退學。</w:t>");
			out.println("        </w:r>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='00347F50' wsp:rsidRDefault='00986905' wsp:rsidP='00986905'>");
			out.println("        <w:pPr>");
			out.println("          <w:pStyle w:val='a4'/>");
			out.println("          <w:spacing w:line='280' w:line-rule='exact'/>");
			out.println("          <w:ind w:left-chars='0' w:left='0'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>2.</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00CD0BF5' wsp:rsidRPr='00347F50'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>成績如有錯誤，應於收到本單後</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00953866' wsp:rsidRPr='00347F50'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>的</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00CD0BF5' wsp:rsidRPr='00347F50'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>兩週內</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00AC7BAD' wsp:rsidRPr='00347F50'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>向註冊組</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00CD0BF5' wsp:rsidRPr='00347F50'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>申請更正。</w:t>");
			out.println("        </w:r>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='00347F50' wsp:rsidRDefault='00986905' wsp:rsidP='00986905'>");
			out.println("        <w:pPr>");
			out.println("          <w:pStyle w:val='a4'/>");
			out.println("          <w:spacing w:line='280' w:line-rule='exact'/>");
			out.println("          <w:ind w:left-chars='0' w:left='222' w:hanging-chars='101' w:hanging='222'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>3.</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00CD0BF5' wsp:rsidRPr='00347F50'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>學生在校期間可點選(學生資訊系統之我的成績)，預先瞭解自己取得成績之資訊，做為選課時修、退選之參考。</w:t>");
			out.println("        </w:r>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='00347F50' wsp:rsidRDefault='00986905' wsp:rsidP='00986905'>");
			out.println("        <w:pPr>");
			out.println("          <w:pStyle w:val='a4'/>");
			out.println("          <w:spacing w:line='280' w:line-rule='exact'/>");
			out.println("          <w:ind w:left-chars='0' w:left='0'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>4.</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00CD0BF5' wsp:rsidRPr='00347F50'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>退學生不得參加暑修。</w:t>");
			out.println("        </w:r>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00AC7BAD' wsp:rsidRPr='00347F50' wsp:rsidRDefault='00986905' wsp:rsidP='00986905'>");
			out.println("        <w:pPr>");
			out.println("          <w:pStyle w:val='a4'/>");
			out.println("          <w:spacing w:line='280' w:line-rule='exact'/>");
			out.println("          <w:ind w:left-chars='0' w:left='238' w:hanging-chars='108' w:hanging='238'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>5.</w:t>");
			out.println("        </w:r>");
			out.println("        <w:r wsp:rsidR='00AC7BAD' wsp:rsidRPr='00347F50'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>依本校學生選課辦法第十條規定：凡已修習及格之科目，如再重複選修已修習成績及格且名稱相同之科目者，其原修習名稱相同之科目，應予註銷。</w:t>");
			out.println("        </w:r>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='006461A7' wsp:rsidRPr='006E4A69' wsp:rsidRDefault='006461A7' wsp:rsidP='00AE6DEE'>");
			out.println("        <w:pPr>");
			out.println("          <w:spacing w:line='280' w:line-rule='exact'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("            <w:color w:val='FF0000'/>");
			out.println("            <w:sz w:val='22'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='00AE6DEE'>");
			out.println("        <w:pPr>");
			out.println("          <w:jc w:val='center'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r wsp:rsidRPr='00227CC9'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>--------------------------------------------------------------------------------</w:t>");
			out.println("        </w:r>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='00AE6DEE'>");
			out.println("        <w:pPr>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r wsp:rsidRPr='00227CC9'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>印刷品</w:t>");
			out.println("        </w:r>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='00AC7BAD' wsp:rsidRDefault='00C000FE' wsp:rsidP='00AE6DEE'>");
			out.println("        <w:pPr>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r wsp:rsidRPr='00C000FE'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("          <w:t>" + schoolAddr + "</w:t>");
			out.println("        </w:r>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00C51466' wsp:rsidRDefault='00C51466' wsp:rsidP='00AE6DEE'>");
			out.println("        <w:pPr>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='006461A7' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='006461A7' wsp:rsidP='00AE6DEE'>");
			out.println("        <w:pPr>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("      </w:p>");
			out.println("      <w:tbl>");
			out.println("        <w:tblPr>");
			out.println("          <w:tblW w:w='0' w:type='auto'/>");
			out.println("          <w:tblBorders>");
			out.println("            <w:top w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
			out.println("            <w:left w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
			out.println("            <w:bottom w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
			out.println("            <w:right w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
			out.println("            <w:insideH w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
			out.println("            <w:insideV w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>");
			out.println("          </w:tblBorders>");
			out.println("          <w:tblLook w:val='04A0'/>");
			out.println("        </w:tblPr>");
			out.println("        <w:tblGrid>");
			out.println("          <w:gridCol w:w='2136'/>");
			out.println("        </w:tblGrid>");
			out.println("        <w:tr wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>");
			out.println("          <w:tc>");
			out.println("            <w:tcPr>");
			out.println("              <w:tcW w:w='0' w:type='auto'/>");
			out.println("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>");
			out.println("            </w:tcPr>");
			out.println("            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='00AE6DEE'>");
			out.println("              <w:pPr>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("              <w:r wsp:rsidRPr='001537BC'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>國內郵資已付</w:t>");
			out.println("              </w:r>");
			out.println("            </w:p>");
			out.println("            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='00AE6DEE'>");
			out.println("              <w:pPr>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("              <w:r wsp:rsidRPr='001537BC'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>北區營收股許可證</w:t>");
			out.println("              </w:r>");
			out.println("            </w:p>");
			out.println("            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='00AE6DEE'>");
			out.println("              <w:pPr>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("              </w:pPr>");
			out.println("              <w:r wsp:rsidRPr='001537BC'>");
			out.println("                <w:rPr>");
			out.println("                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>");
			out.println("                  <wx:font wx:val='標楷體'/>");
			out.println("                </w:rPr>");
			out.println("                <w:t>北台字第17573號</w:t>");
			out.println("              </w:r>");
			out.println("            </w:p>");
			out.println("          </w:tc>");
			out.println("        </w:tr>");
			out.println("      </w:tbl>");
			out.println("      <w:p wsp:rsidR='00C51466' wsp:rsidRDefault='00C51466' wsp:rsidP='002E6B1A'>");
			out.println("        <w:pPr>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00597E25' wsp:rsidRDefault='00597E25' wsp:rsidP='002E6B1A'>");
			out.println("        <w:pPr>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='00347F50' wsp:rsidRDefault='00B73482' wsp:rsidP='00DC77A5'>");
			out.println("        <w:pPr>");
			out.println("          <w:ind w:left-chars='827' w:left='1985'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r wsp:rsidRPr='00B73482'>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			try {
				out.println("          <w:t>"
						+ ((Map) stmds.get(i)).get("curr_post")
						+ ((Map) stmds.get(i)).get("curr_addr") + "</w:t>");
			} catch (Exception e) {
				out.println("          <w:t></w:t>");
			}
			out.println("        </w:r>");
			out.println("      </w:p>");
			out.println("      <w:p wsp:rsidR='00C51466' wsp:rsidRPr='00347F50' wsp:rsidRDefault='00C000FE' wsp:rsidP='00DC77A5'>");
			out.println("        <w:pPr>");
			out.println("          <w:ind w:left-chars='827' w:left='1985'/>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			out.println("        </w:pPr>");
			out.println("        <w:r>");
			out.println("          <w:rPr>");
			out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
			out.println("            <wx:font wx:val='標楷體'/>");
			out.println("          </w:rPr>");
			try {
				out.println("          <w:t>"
						+ ((Map) stmds.get(i)).get("parent_name")
						+ " 先生/小姐收</w:t>");
			} catch (Exception e) {
				out.println("          <w:t></w:t>");
			}
			out.println("        </w:r>");
			out.println("      </w:p>");
			if (i != stmds.size() - 1) {
				out.println("    <w:br w:type='page'/>");
			}
		}
		out.println("      <w:p wsp:rsidR='00C51466' wsp:rsidRPr='00597E25' wsp:rsidRDefault='00C51466' wsp:rsidP='006E4A69'>");
		out.println("        <w:pPr>");
		out.println("          <w:rPr>");
		out.println("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>");
		out.println("            <wx:font wx:val='標楷體'/>");
		out.println("          </w:rPr>");
		out.println("        </w:pPr>");
		out.println("      </w:p>");
		out.println("      <w:sectPr wsp:rsidR='00C51466' wsp:rsidRPr='00597E25' wsp:rsidSect='00597E25'>");
		out.println("        <w:pgSz w:w='11906' w:h='16838'/>");
		out.println("        <w:pgMar w:top='567' w:right='1077' w:bottom='567' w:left='1077' w:header='851' w:footer='992' w:gutter='0'/>");
		out.println("        <w:cols w:space='425'/>");
		out.println("        <w:docGrid w:type='lines' w:line-pitch='360'/>");
		out.println("      </w:sectPr>");
		out.println("    </wx:sect>");
		out.println("  </w:body>");
		out.println("</w:wordDocument>");

		out.close();
		return true;

		/*
		 * 舊版成績單 out.println
		 * ("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
		 * out.println ("<?mso-application progid='Word.Document'?>");
		 * out.println (
		 * "<w:wordDocument xmlns:aml='http://schemas.microsoft.com/aml/2001/core' xmlns:wpc='http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas' xmlns:dt='uuid:C2F41010-65B3-11d1-A29F-00AA00C14882' xmlns:mc='http://schemas.openxmlformats.org/markup-compatibility/2006' xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:v='urn:schemas-microsoft-com:vml' xmlns:w10='urn:schemas-microsoft-com:office:word' xmlns:w='http://schemas.microsoft.com/office/word/2003/wordml' xmlns:wx='http://schemas.microsoft.com/office/word/2003/auxHint' xmlns:wne='http://schemas.microsoft.com/office/word/2006/wordml' xmlns:wsp='http://schemas.microsoft.com/office/word/2003/wordml/sp2' xmlns:sl='http://schemas.microsoft.com/schemaLibrary/2003/core' w:macrosPresent='no' w:embeddedObjPresent='no' w:ocxPresent='no' xml:space='preserve'>"
		 * ); out.println (
		 * "  <w:ignoreSubtree w:val='http://schemas.microsoft.com/office/word/2003/wordml/sp2'/>"
		 * ); out.println ("  <o:DocumentProperties>"); out.println
		 * ("    <o:Author>moonwalker</o:Author>"); out.println
		 * ("    <o:LastAuthor>John</o:LastAuthor>"); out.println
		 * ("    <o:Revision>2</o:Revision>"); out.println
		 * ("    <o:TotalTime>0</o:TotalTime>"); out.println
		 * ("    <o:Created>2013-01-25T02:17:00Z</o:Created>"); out.println
		 * ("    <o:LastSaved>2013-01-25T02:17:00Z</o:LastSaved>"); out.println
		 * ("    <o:Pages>1</o:Pages>"); out.println
		 * ("    <o:Words>88</o:Words>"); out.println
		 * ("    <o:Characters>506</o:Characters>"); out.println
		 * ("    <o:Lines>4</o:Lines>"); out.println
		 * ("    <o:Paragraphs>1</o:Paragraphs>"); out.println
		 * ("    <o:CharactersWithSpaces>593</o:CharactersWithSpaces>");
		 * out.println ("    <o:Version>14</o:Version>"); out.println
		 * ("  </o:DocumentProperties>"); out.println ("  <w:fonts>");
		 * out.println (
		 * "    <w:defaultFonts w:ascii='Calibri' w:fareast='新細明體' w:h-ansi='Calibri' w:cs='Times New Roman'/>"
		 * ); out.println ("    <w:font w:name='Times New Roman'>"); out.println
		 * ("      <w:panose-1 w:val='02020603050405020304'/>"); out.println
		 * ("      <w:charset w:val='00'/>"); out.println
		 * ("      <w:family w:val='Roman'/>"); out.println
		 * ("      <w:pitch w:val='variable'/>"); out.println (
		 * "      <w:sig w:usb-0='E0002AFF' w:usb-1='C0007841' w:usb-2='00000009' w:usb-3='00000000' w:csb-0='000001FF' w:csb-1='00000000'/>"
		 * ); out.println ("    </w:font>"); out.println
		 * ("    <w:font w:name='新細明體'>"); out.println
		 * ("      <w:altName w:val='PMingLiU'/>"); out.println
		 * ("      <w:panose-1 w:val='02020500000000000000'/>"); out.println
		 * ("      <w:charset w:val='88'/>"); out.println
		 * ("      <w:family w:val='Roman'/>"); out.println
		 * ("      <w:pitch w:val='variable'/>"); out.println (
		 * "      <w:sig w:usb-0='A00002FF' w:usb-1='28CFFCFA' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>"
		 * ); out.println ("    </w:font>"); out.println
		 * ("    <w:font w:name='新細明體'>"); out.println
		 * ("      <w:altName w:val='PMingLiU'/>"); out.println
		 * ("      <w:panose-1 w:val='02020500000000000000'/>"); out.println
		 * ("      <w:charset w:val='88'/>"); out.println
		 * ("      <w:family w:val='Roman'/>"); out.println
		 * ("      <w:pitch w:val='variable'/>"); out.println (
		 * "      <w:sig w:usb-0='A00002FF' w:usb-1='28CFFCFA' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>"
		 * ); out.println ("    </w:font>"); out.println
		 * ("    <w:font w:name='Calibri'>"); out.println
		 * ("      <w:panose-1 w:val='020F0502020204030204'/>"); out.println
		 * ("      <w:charset w:val='00'/>"); out.println
		 * ("      <w:family w:val='Swiss'/>"); out.println
		 * ("      <w:pitch w:val='variable'/>"); out.println (
		 * "      <w:sig w:usb-0='E00002FF' w:usb-1='4000ACFF' w:usb-2='00000001' w:usb-3='00000000' w:csb-0='0000019F' w:csb-1='00000000'/>"
		 * ); out.println ("    </w:font>"); out.println
		 * ("    <w:font w:name='標楷體'>"); out.println
		 * ("      <w:panose-1 w:val='03000509000000000000'/>"); out.println
		 * ("      <w:charset w:val='88'/>"); out.println
		 * ("      <w:family w:val='Script'/>"); out.println
		 * ("      <w:pitch w:val='fixed'/>"); out.println (
		 * "      <w:sig w:usb-0='00000003' w:usb-1='080E0000' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>"
		 * ); out.println ("    </w:font>"); out.println
		 * ("    <w:font w:name='@標楷體'>"); out.println
		 * ("      <w:panose-1 w:val='03000509000000000000'/>"); out.println
		 * ("      <w:charset w:val='88'/>"); out.println
		 * ("      <w:family w:val='Script'/>"); out.println
		 * ("      <w:pitch w:val='fixed'/>"); out.println (
		 * "      <w:sig w:usb-0='00000003' w:usb-1='080E0000' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>"
		 * ); out.println ("    </w:font>"); out.println
		 * ("    <w:font w:name='@新細明體'>"); out.println
		 * ("      <w:panose-1 w:val='02020500000000000000'/>"); out.println
		 * ("      <w:charset w:val='88'/>"); out.println
		 * ("      <w:family w:val='Roman'/>"); out.println
		 * ("      <w:pitch w:val='variable'/>"); out.println (
		 * "      <w:sig w:usb-0='A00002FF' w:usb-1='28CFFCFA' w:usb-2='00000016' w:usb-3='00000000' w:csb-0='00100001' w:csb-1='00000000'/>"
		 * ); out.println ("    </w:font>"); out.println ("  </w:fonts>");
		 * out.println ("  <w:lists>"); out.println
		 * ("    <w:listDef w:listDefId='0'>"); out.println
		 * ("      <w:lsid w:val='11745157'/>"); out.println
		 * ("      <w:plt w:val='HybridMultilevel'/>"); out.println
		 * ("      <w:tmpl w:val='9E246E78'/>"); out.println
		 * ("      <w:lvl w:ilvl='0' w:tplc='0409000F'>"); out.println
		 * ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:lvlText w:val='%1.'/>"); out.println
		 * ("        <w:lvlJc w:val='left'/>"); out.println ("        <w:pPr>");
		 * out.println ("          <w:ind w:left='480' w:hanging='480'/>");
		 * out.println ("        </w:pPr>"); out.println ("      </w:lvl>");
		 * out.println
		 * ("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:nfc w:val='30'/>"); out.println
		 * ("        <w:lvlText w:val='%2、'/>"); out.println
		 * ("        <w:lvlJc w:val='left'/>"); out.println ("        <w:pPr>");
		 * out.println ("          <w:ind w:left='960' w:hanging='480'/>");
		 * out.println ("        </w:pPr>"); out.println ("      </w:lvl>");
		 * out.println
		 * ("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:nfc w:val='2'/>"); out.println
		 * ("        <w:lvlText w:val='%3.'/>"); out.println
		 * ("        <w:lvlJc w:val='right'/>"); out.println
		 * ("        <w:pPr>"); out.println
		 * ("          <w:ind w:left='1440' w:hanging='480'/>"); out.println
		 * ("        </w:pPr>"); out.println ("      </w:lvl>"); out.println
		 * ("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:lvlText w:val='%4.'/>"); out.println
		 * ("        <w:lvlJc w:val='left'/>"); out.println ("        <w:pPr>");
		 * out.println ("          <w:ind w:left='1920' w:hanging='480'/>");
		 * out.println ("        </w:pPr>"); out.println ("      </w:lvl>");
		 * out.println
		 * ("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:nfc w:val='30'/>"); out.println
		 * ("        <w:lvlText w:val='%5、'/>"); out.println
		 * ("        <w:lvlJc w:val='left'/>"); out.println ("        <w:pPr>");
		 * out.println ("          <w:ind w:left='2400' w:hanging='480'/>");
		 * out.println ("        </w:pPr>"); out.println ("      </w:lvl>");
		 * out.println
		 * ("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:nfc w:val='2'/>"); out.println
		 * ("        <w:lvlText w:val='%6.'/>"); out.println
		 * ("        <w:lvlJc w:val='right'/>"); out.println
		 * ("        <w:pPr>"); out.println
		 * ("          <w:ind w:left='2880' w:hanging='480'/>"); out.println
		 * ("        </w:pPr>"); out.println ("      </w:lvl>"); out.println
		 * ("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:lvlText w:val='%7.'/>"); out.println
		 * ("        <w:lvlJc w:val='left'/>"); out.println ("        <w:pPr>");
		 * out.println ("          <w:ind w:left='3360' w:hanging='480'/>");
		 * out.println ("        </w:pPr>"); out.println ("      </w:lvl>");
		 * out.println
		 * ("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:nfc w:val='30'/>"); out.println
		 * ("        <w:lvlText w:val='%8、'/>"); out.println
		 * ("        <w:lvlJc w:val='left'/>"); out.println ("        <w:pPr>");
		 * out.println ("          <w:ind w:left='3840' w:hanging='480'/>");
		 * out.println ("        </w:pPr>"); out.println ("      </w:lvl>");
		 * out.println
		 * ("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:nfc w:val='2'/>"); out.println
		 * ("        <w:lvlText w:val='%9.'/>"); out.println
		 * ("        <w:lvlJc w:val='right'/>"); out.println
		 * ("        <w:pPr>"); out.println
		 * ("          <w:ind w:left='4320' w:hanging='480'/>"); out.println
		 * ("        </w:pPr>"); out.println ("      </w:lvl>"); out.println
		 * ("    </w:listDef>"); out.println
		 * ("    <w:listDef w:listDefId='1'>"); out.println
		 * ("      <w:lsid w:val='651B0B06'/>"); out.println
		 * ("      <w:plt w:val='HybridMultilevel'/>"); out.println
		 * ("      <w:tmpl w:val='90244C14'/>"); out.println
		 * ("      <w:lvl w:ilvl='0' w:tplc='3326C6D0'>"); out.println
		 * ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:lvlText w:val='%1.'/>"); out.println
		 * ("        <w:lvlJc w:val='left'/>"); out.println ("        <w:pPr>");
		 * out.println ("          <w:ind w:left='360' w:hanging='360'/>");
		 * out.println ("        </w:pPr>"); out.println ("        <w:rPr>");
		 * out.println ("          <w:rFonts w:hint='default'/>"); out.println
		 * ("        </w:rPr>"); out.println ("      </w:lvl>"); out.println
		 * ("      <w:lvl w:ilvl='1' w:tplc='04090019' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:nfc w:val='30'/>"); out.println
		 * ("        <w:lvlText w:val='%2、'/>"); out.println
		 * ("        <w:lvlJc w:val='left'/>"); out.println ("        <w:pPr>");
		 * out.println ("          <w:ind w:left='960' w:hanging='480'/>");
		 * out.println ("        </w:pPr>"); out.println ("      </w:lvl>");
		 * out.println
		 * ("      <w:lvl w:ilvl='2' w:tplc='0409001B' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:nfc w:val='2'/>"); out.println
		 * ("        <w:lvlText w:val='%3.'/>"); out.println
		 * ("        <w:lvlJc w:val='right'/>"); out.println
		 * ("        <w:pPr>"); out.println
		 * ("          <w:ind w:left='1440' w:hanging='480'/>"); out.println
		 * ("        </w:pPr>"); out.println ("      </w:lvl>"); out.println
		 * ("      <w:lvl w:ilvl='3' w:tplc='0409000F' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:lvlText w:val='%4.'/>"); out.println
		 * ("        <w:lvlJc w:val='left'/>"); out.println ("        <w:pPr>");
		 * out.println ("          <w:ind w:left='1920' w:hanging='480'/>");
		 * out.println ("        </w:pPr>"); out.println ("      </w:lvl>");
		 * out.println
		 * ("      <w:lvl w:ilvl='4' w:tplc='04090019' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:nfc w:val='30'/>"); out.println
		 * ("        <w:lvlText w:val='%5、'/>"); out.println
		 * ("        <w:lvlJc w:val='left'/>"); out.println ("        <w:pPr>");
		 * out.println ("          <w:ind w:left='2400' w:hanging='480'/>");
		 * out.println ("        </w:pPr>"); out.println ("      </w:lvl>");
		 * out.println
		 * ("      <w:lvl w:ilvl='5' w:tplc='0409001B' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:nfc w:val='2'/>"); out.println
		 * ("        <w:lvlText w:val='%6.'/>"); out.println
		 * ("        <w:lvlJc w:val='right'/>"); out.println
		 * ("        <w:pPr>"); out.println
		 * ("          <w:ind w:left='2880' w:hanging='480'/>"); out.println
		 * ("        </w:pPr>"); out.println ("      </w:lvl>"); out.println
		 * ("      <w:lvl w:ilvl='6' w:tplc='0409000F' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:lvlText w:val='%7.'/>"); out.println
		 * ("        <w:lvlJc w:val='left'/>"); out.println ("        <w:pPr>");
		 * out.println ("          <w:ind w:left='3360' w:hanging='480'/>");
		 * out.println ("        </w:pPr>"); out.println ("      </w:lvl>");
		 * out.println
		 * ("      <w:lvl w:ilvl='7' w:tplc='04090019' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:nfc w:val='30'/>"); out.println
		 * ("        <w:lvlText w:val='%8、'/>"); out.println
		 * ("        <w:lvlJc w:val='left'/>"); out.println ("        <w:pPr>");
		 * out.println ("          <w:ind w:left='3840' w:hanging='480'/>");
		 * out.println ("        </w:pPr>"); out.println ("      </w:lvl>");
		 * out.println
		 * ("      <w:lvl w:ilvl='8' w:tplc='0409001B' w:tentative='on'>");
		 * out.println ("        <w:start w:val='1'/>"); out.println
		 * ("        <w:nfc w:val='2'/>"); out.println
		 * ("        <w:lvlText w:val='%9.'/>"); out.println
		 * ("        <w:lvlJc w:val='right'/>"); out.println
		 * ("        <w:pPr>"); out.println
		 * ("          <w:ind w:left='4320' w:hanging='480'/>"); out.println
		 * ("        </w:pPr>"); out.println ("      </w:lvl>"); out.println
		 * ("    </w:listDef>"); out.println ("    <w:list w:ilfo='1'>");
		 * out.println ("      <w:ilst w:val='0'/>"); out.println
		 * ("    </w:list>"); out.println ("    <w:list w:ilfo='2'>");
		 * out.println ("      <w:ilst w:val='1'/>"); out.println
		 * ("    </w:list>"); out.println ("  </w:lists>"); out.println
		 * ("  <w:styles>"); out.println
		 * ("    <w:versionOfBuiltInStylenames w:val='7'/>"); out.println
		 * ("    <w:latentStyles w:defLockedState='off' w:latentStyleCount='267'>"
		 * ); out.println ("      <w:lsdException w:name='Normal'/>");
		 * out.println ("      <w:lsdException w:name='heading 1'/>");
		 * out.println ("      <w:lsdException w:name='heading 2'/>");
		 * out.println ("      <w:lsdException w:name='heading 3'/>");
		 * out.println ("      <w:lsdException w:name='heading 4'/>");
		 * out.println ("      <w:lsdException w:name='heading 5'/>");
		 * out.println ("      <w:lsdException w:name='heading 6'/>");
		 * out.println ("      <w:lsdException w:name='heading 7'/>");
		 * out.println ("      <w:lsdException w:name='heading 8'/>");
		 * out.println ("      <w:lsdException w:name='heading 9'/>");
		 * out.println ("      <w:lsdException w:name='toc 1'/>"); out.println
		 * ("      <w:lsdException w:name='toc 2'/>"); out.println
		 * ("      <w:lsdException w:name='toc 3'/>"); out.println
		 * ("      <w:lsdException w:name='toc 4'/>"); out.println
		 * ("      <w:lsdException w:name='toc 5'/>"); out.println
		 * ("      <w:lsdException w:name='toc 6'/>"); out.println
		 * ("      <w:lsdException w:name='toc 7'/>"); out.println
		 * ("      <w:lsdException w:name='toc 8'/>"); out.println
		 * ("      <w:lsdException w:name='toc 9'/>"); out.println
		 * ("      <w:lsdException w:name='caption'/>"); out.println
		 * ("      <w:lsdException w:name='Title'/>"); out.println
		 * ("      <w:lsdException w:name='Default Paragraph Font'/>");
		 * out.println ("      <w:lsdException w:name='Subtitle'/>");
		 * out.println ("      <w:lsdException w:name='Strong'/>"); out.println
		 * ("      <w:lsdException w:name='Emphasis'/>"); out.println
		 * ("      <w:lsdException w:name='Table Grid'/>"); out.println
		 * ("      <w:lsdException w:name='Placeholder Text'/>"); out.println
		 * ("      <w:lsdException w:name='No Spacing'/>"); out.println
		 * ("      <w:lsdException w:name='Light Shading'/>"); out.println
		 * ("      <w:lsdException w:name='Light List'/>"); out.println
		 * ("      <w:lsdException w:name='Light Grid'/>"); out.println
		 * ("      <w:lsdException w:name='Medium Shading 1'/>"); out.println
		 * ("      <w:lsdException w:name='Medium Shading 2'/>"); out.println
		 * ("      <w:lsdException w:name='Medium List 1'/>"); out.println
		 * ("      <w:lsdException w:name='Medium List 2'/>"); out.println
		 * ("      <w:lsdException w:name='Medium Grid 1'/>"); out.println
		 * ("      <w:lsdException w:name='Medium Grid 2'/>"); out.println
		 * ("      <w:lsdException w:name='Medium Grid 3'/>"); out.println
		 * ("      <w:lsdException w:name='Dark List'/>"); out.println
		 * ("      <w:lsdException w:name='Colorful Shading'/>"); out.println
		 * ("      <w:lsdException w:name='Colorful List'/>"); out.println
		 * ("      <w:lsdException w:name='Colorful Grid'/>"); out.println
		 * ("      <w:lsdException w:name='Light Shading Accent 1'/>");
		 * out.println ("      <w:lsdException w:name='Light List Accent 1'/>");
		 * out.println ("      <w:lsdException w:name='Light Grid Accent 1'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Shading 1 Accent 1'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Shading 2 Accent 1'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium List 1 Accent 1'/>");
		 * out.println ("      <w:lsdException w:name='Revision'/>");
		 * out.println ("      <w:lsdException w:name='List Paragraph'/>");
		 * out.println ("      <w:lsdException w:name='Quote'/>"); out.println
		 * ("      <w:lsdException w:name='Intense Quote'/>"); out.println
		 * ("      <w:lsdException w:name='Medium List 2 Accent 1'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 1 Accent 1'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 2 Accent 1'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 3 Accent 1'/>");
		 * out.println ("      <w:lsdException w:name='Dark List Accent 1'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful Shading Accent 1'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful List Accent 1'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful Grid Accent 1'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Light Shading Accent 2'/>");
		 * out.println ("      <w:lsdException w:name='Light List Accent 2'/>");
		 * out.println ("      <w:lsdException w:name='Light Grid Accent 2'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Shading 1 Accent 2'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Shading 2 Accent 2'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium List 1 Accent 2'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium List 2 Accent 2'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 1 Accent 2'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 2 Accent 2'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 3 Accent 2'/>");
		 * out.println ("      <w:lsdException w:name='Dark List Accent 2'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful Shading Accent 2'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful List Accent 2'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful Grid Accent 2'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Light Shading Accent 3'/>");
		 * out.println ("      <w:lsdException w:name='Light List Accent 3'/>");
		 * out.println ("      <w:lsdException w:name='Light Grid Accent 3'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Shading 1 Accent 3'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Shading 2 Accent 3'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium List 1 Accent 3'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium List 2 Accent 3'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 1 Accent 3'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 2 Accent 3'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 3 Accent 3'/>");
		 * out.println ("      <w:lsdException w:name='Dark List Accent 3'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful Shading Accent 3'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful List Accent 3'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful Grid Accent 3'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Light Shading Accent 4'/>");
		 * out.println ("      <w:lsdException w:name='Light List Accent 4'/>");
		 * out.println ("      <w:lsdException w:name='Light Grid Accent 4'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Shading 1 Accent 4'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Shading 2 Accent 4'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium List 1 Accent 4'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium List 2 Accent 4'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 1 Accent 4'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 2 Accent 4'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 3 Accent 4'/>");
		 * out.println ("      <w:lsdException w:name='Dark List Accent 4'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful Shading Accent 4'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful List Accent 4'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful Grid Accent 4'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Light Shading Accent 5'/>");
		 * out.println ("      <w:lsdException w:name='Light List Accent 5'/>");
		 * out.println ("      <w:lsdException w:name='Light Grid Accent 5'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Shading 1 Accent 5'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Shading 2 Accent 5'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium List 1 Accent 5'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium List 2 Accent 5'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 1 Accent 5'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 2 Accent 5'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 3 Accent 5'/>");
		 * out.println ("      <w:lsdException w:name='Dark List Accent 5'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful Shading Accent 5'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful List Accent 5'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful Grid Accent 5'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Light Shading Accent 6'/>");
		 * out.println ("      <w:lsdException w:name='Light List Accent 6'/>");
		 * out.println ("      <w:lsdException w:name='Light Grid Accent 6'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Shading 1 Accent 6'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Shading 2 Accent 6'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium List 1 Accent 6'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium List 2 Accent 6'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 1 Accent 6'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 2 Accent 6'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Medium Grid 3 Accent 6'/>");
		 * out.println ("      <w:lsdException w:name='Dark List Accent 6'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful Shading Accent 6'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful List Accent 6'/>");
		 * out.println
		 * ("      <w:lsdException w:name='Colorful Grid Accent 6'/>");
		 * out.println ("      <w:lsdException w:name='Subtle Emphasis'/>");
		 * out.println ("      <w:lsdException w:name='Intense Emphasis'/>");
		 * out.println ("      <w:lsdException w:name='Subtle Reference'/>");
		 * out.println ("      <w:lsdException w:name='Intense Reference'/>");
		 * out.println ("      <w:lsdException w:name='Book Title'/>");
		 * out.println ("      <w:lsdException w:name='Bibliography'/>");
		 * out.println ("      <w:lsdException w:name='TOC Heading'/>");
		 * out.println ("    </w:latentStyles>"); out.println
		 * ("    <w:style w:type='paragraph' w:default='on' w:styleId='a'>");
		 * out.println ("      <w:name w:val='Normal'/>"); out.println
		 * ("      <wx:uiName wx:val='內文'/>"); out.println ("      <w:pPr>");
		 * out.println ("        <w:widowControl w:val='off'/>"); out.println
		 * ("      </w:pPr>"); out.println ("      <w:rPr>"); out.println
		 * ("        <wx:font wx:val='Calibri'/>"); out.println
		 * ("        <w:kern w:val='2'/>"); out.println
		 * ("        <w:sz w:val='24'/>"); out.println
		 * ("        <w:sz-cs w:val='22'/>"); out.println
		 * ("        <w:lang w:val='EN-US' w:fareast='ZH-TW' w:bidi='AR-SA'/>");
		 * out.println ("      </w:rPr>"); out.println ("    </w:style>");
		 * out.println
		 * ("    <w:style w:type='character' w:default='on' w:styleId='a0'>");
		 * out.println ("      <w:name w:val='Default Paragraph Font'/>");
		 * out.println ("      <wx:uiName wx:val='預設段落字型'/>"); out.println
		 * ("    </w:style>"); out.println
		 * ("    <w:style w:type='table' w:default='on' w:styleId='a1'>");
		 * out.println ("      <w:name w:val='Normal Table'/>"); out.println
		 * ("      <wx:uiName wx:val='表格內文'/>"); out.println ("      <w:rPr>");
		 * out.println ("        <wx:font wx:val='Calibri'/>"); out.println
		 * ("        <w:lang w:val='EN-US' w:fareast='ZH-TW' w:bidi='AR-SA'/>");
		 * out.println ("      </w:rPr>"); out.println ("      <w:tblPr>");
		 * out.println ("        <w:tblInd w:w='0' w:type='dxa'/>"); out.println
		 * ("        <w:tblCellMar>"); out.println
		 * ("          <w:top w:w='0' w:type='dxa'/>"); out.println
		 * ("          <w:left w:w='108' w:type='dxa'/>"); out.println
		 * ("          <w:bottom w:w='0' w:type='dxa'/>"); out.println
		 * ("          <w:right w:w='108' w:type='dxa'/>"); out.println
		 * ("        </w:tblCellMar>"); out.println ("      </w:tblPr>");
		 * out.println ("    </w:style>"); out.println
		 * ("    <w:style w:type='list' w:default='on' w:styleId='a2'>");
		 * out.println ("      <w:name w:val='No List'/>"); out.println
		 * ("      <wx:uiName wx:val='無清單'/>"); out.println ("    </w:style>");
		 * out.println ("    <w:style w:type='table' w:styleId='a3'>");
		 * out.println ("      <w:name w:val='Table Grid'/>"); out.println
		 * ("      <wx:uiName wx:val='表格格線'/>"); out.println
		 * ("      <w:basedOn w:val='a1'/>"); out.println
		 * ("      <w:rsid w:val='00A12CDB'/>"); out.println ("      <w:rPr>");
		 * out.println ("        <wx:font wx:val='Calibri'/>"); out.println
		 * ("      </w:rPr>"); out.println ("      <w:tblPr>"); out.println
		 * ("        <w:tblInd w:w='0' w:type='dxa'/>"); out.println
		 * ("        <w:tblBorders>"); out.println (
		 * "          <w:top w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>"
		 * ); out.println (
		 * "          <w:left w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>"
		 * ); out.println (
		 * "          <w:bottom w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>"
		 * ); out.println (
		 * "          <w:right w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>"
		 * ); out.println (
		 * "          <w:insideH w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>"
		 * ); out.println (
		 * "          <w:insideV w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>"
		 * ); out.println ("        </w:tblBorders>"); out.println
		 * ("        <w:tblCellMar>"); out.println
		 * ("          <w:top w:w='0' w:type='dxa'/>"); out.println
		 * ("          <w:left w:w='108' w:type='dxa'/>"); out.println
		 * ("          <w:bottom w:w='0' w:type='dxa'/>"); out.println
		 * ("          <w:right w:w='108' w:type='dxa'/>"); out.println
		 * ("        </w:tblCellMar>"); out.println ("      </w:tblPr>");
		 * out.println ("    </w:style>"); out.println
		 * ("    <w:style w:type='paragraph' w:styleId='a4'>"); out.println
		 * ("      <w:name w:val='List Paragraph'/>"); out.println
		 * ("      <wx:uiName wx:val='清單段落'/>"); out.println
		 * ("      <w:basedOn w:val='a'/>"); out.println
		 * ("      <w:rsid w:val='006461A7'/>"); out.println ("      <w:pPr>");
		 * out.println ("        <w:ind w:left-chars='200' w:left='480'/>");
		 * out.println ("      </w:pPr>"); out.println ("      <w:rPr>");
		 * out.println ("        <wx:font wx:val='Calibri'/>"); out.println
		 * ("      </w:rPr>"); out.println ("    </w:style>"); out.println
		 * ("  </w:styles>"); out.println ("  <w:shapeDefaults>"); out.println
		 * ("    <o:shapedefaults v:ext='edit' spidmax='1026'/>"); out.println
		 * ("    <o:shapelayout v:ext='edit'>"); out.println
		 * ("      <o:idmap v:ext='edit' data='1'/>"); out.println
		 * ("    </o:shapelayout>"); out.println ("  </w:shapeDefaults>");
		 * out.println ("  <w:docPr>"); out.println
		 * ("    <w:view w:val='print'/>"); out.println
		 * ("    <w:zoom w:percent='100'/>"); out.println
		 * ("    <w:doNotEmbedSystemFonts/>"); out.println
		 * ("    <w:bordersDontSurroundHeader/>"); out.println
		 * ("    <w:bordersDontSurroundFooter/>"); out.println
		 * ("    <w:proofState w:spelling='clean' w:grammar='clean'/>");
		 * out.println ("    <w:defaultTabStop w:val='480'/>"); out.println
		 * ("    <w:drawingGridHorizontalSpacing w:val='120'/>"); out.println
		 * ("    <w:displayHorizontalDrawingGridEvery w:val='0'/>"); out.println
		 * ("    <w:displayVerticalDrawingGridEvery w:val='2'/>"); out.println
		 * ("    <w:punctuationKerning/>"); out.println
		 * ("    <w:characterSpacingControl w:val='CompressPunctuation'/>");
		 * out.println ("    <w:optimizeForBrowser/>"); out.println
		 * ("    <w:allowPNG/>"); out.println
		 * ("    <w:validateAgainstSchema/>"); out.println
		 * ("    <w:saveInvalidXML w:val='off'/>"); out.println
		 * ("    <w:ignoreMixedContent w:val='off'/>"); out.println
		 * ("    <w:alwaysShowPlaceholderText w:val='off'/>"); out.println
		 * ("    <w:compat>"); out.println ("      <w:spaceForUL/>");
		 * out.println ("      <w:balanceSingleByteDoubleByteWidth/>");
		 * out.println ("      <w:doNotLeaveBackslashAlone/>"); out.println
		 * ("      <w:ulTrailSpace/>"); out.println
		 * ("      <w:doNotExpandShiftReturn/>"); out.println
		 * ("      <w:adjustLineHeightInTable/>"); out.println
		 * ("      <w:breakWrappedTables/>"); out.println
		 * ("      <w:snapToGridInCell/>"); out.println
		 * ("      <w:wrapTextWithPunct/>"); out.println
		 * ("      <w:useAsianBreakRules/>"); out.println
		 * ("      <w:dontGrowAutofit/>"); out.println
		 * ("      <w:useFELayout/>"); out.println ("    </w:compat>");
		 * out.println ("    <wsp:rsids>"); out.println
		 * ("      <wsp:rsidRoot wsp:val='00CD0BF5'/>"); out.println
		 * ("      <wsp:rsid wsp:val='001537BC'/>"); out.println
		 * ("      <wsp:rsid wsp:val='0017666E'/>"); out.println
		 * ("      <wsp:rsid wsp:val='00227CC9'/>"); out.println
		 * ("      <wsp:rsid wsp:val='002E6B1A'/>"); out.println
		 * ("      <wsp:rsid wsp:val='00423950'/>"); out.println
		 * ("      <wsp:rsid wsp:val='00597E25'/>"); out.println
		 * ("      <wsp:rsid wsp:val='006461A7'/>"); out.println
		 * ("      <wsp:rsid wsp:val='008F3913'/>"); out.println
		 * ("      <wsp:rsid wsp:val='00953866'/>"); out.println
		 * ("      <wsp:rsid wsp:val='00A12CDB'/>"); out.println
		 * ("      <wsp:rsid wsp:val='00AE6DEE'/>"); out.println
		 * ("      <wsp:rsid wsp:val='00C51466'/>"); out.println
		 * ("      <wsp:rsid wsp:val='00CD0BF5'/>"); out.println
		 * ("      <wsp:rsid wsp:val='00E93993'/>"); out.println
		 * ("      <wsp:rsid wsp:val='00F25446'/>"); out.println
		 * ("    </wsp:rsids>"); out.println ("  </w:docPr>"); out.println
		 * ("  <w:body>"); out.println ("    <wx:sect>");
		 * 
		 * 
		 * 
		 * //List selds; for(int i=0; i<stmds.size(); i++){ out.println (
		 * "      <w:p wsp:rsidR='00F25446' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:jc w:val='center'/>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:r wsp:rsidRPr='00227CC9'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>中華科技大學</w:t>");
		 * out.println ("        </w:r>"); out.println ("      </w:p>");
		 * out.println (
		 * "      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00423950' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:jc w:val='center'/>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:r>"); out.println ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println
		 * ("          <w:t>"+year+"</w:t>"); out.println ("        </w:r>");
		 * out.println ("        <w:proofErr w:type='gramStart'/>"); out.println
		 * ("        <w:r wsp:rsidR='00CD0BF5' wsp:rsidRPr='00227CC9'>");
		 * out.println ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>學年度第</w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:proofErr w:type='gramEnd'/>"); out.println
		 * ("        <w:r>"); out.println ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println
		 * ("          <w:t>"+term+"</w:t>"); out.println ("        </w:r>");
		 * out.println
		 * ("        <w:r wsp:rsidR='00CD0BF5' wsp:rsidRPr='00227CC9'>");
		 * out.println ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println
		 * ("          <w:t>學期學生學期成績單</w:t>"); out.println ("        </w:r>");
		 * out.println ("      </w:p>"); out.println (
		 * "      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='00CD0BF5'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:r wsp:rsidRPr='00227CC9'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>班級：</w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidR='00423950'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println
		 * ("          <w:t>"+((Map)stmds.get(i)).get("ClassName")+" </w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidRPr='00227CC9'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>學號：</w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidR='00423950'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println
		 * ("          <w:t>"+((Map)stmds.get(i)).get("student_no")+" </w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidRPr='00227CC9'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>姓名：</w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidR='00423950'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println
		 * ("          <w:t>"+((Map)stmds.get(i)).get("student_name")+"</w:t>");
		 * out.println ("        </w:r>"); out.println ("      </w:p>");
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * selds=(List) ((Map)stmds.get(i)).get("selds");
		 * 
		 * 
		 * 
		 * out.println ("      <w:tbl>"); out.println ("        <w:tblPr>");
		 * out.println ("          <w:tblW w:w='5000' w:type='pct'/>");
		 * out.println ("          <w:tblLook w:val='04A0'/>"); out.println
		 * ("        </w:tblPr>"); out.println ("        <w:tblGrid>");
		 * out.println ("          <w:gridCol w:w='2492'/>"); out.println
		 * ("          <w:gridCol w:w='2492'/>"); out.println
		 * ("          <w:gridCol w:w='2492'/>"); out.println
		 * ("          <w:gridCol w:w='2492'/>"); out.println
		 * ("        </w:tblGrid>");
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * out.println (
		 * "        <w:tr wsp:rsidR='0017666E' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>"
		 * ); out.println ("          <w:trPr>"); out.println
		 * ("            <w:trHeight w:val='56'/>"); out.println
		 * ("          </w:trPr>");
		 * 
		 * 
		 * 
		 * out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='1250' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:adjustRightInd w:val='off'/>"); out.println
		 * ("                <w:snapToGrid w:val='off'/>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println ("              </w:pPr>");
		 * out.println ("              <w:r wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println
		 * ("                <w:t>科目名稱</w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>");
		 * 
		 * 
		 * 
		 * out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='1250' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00C51466' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:adjustRightInd w:val='off'/>"); out.println
		 * ("                <w:snapToGrid w:val='off'/>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println ("              </w:pPr>");
		 * out.println ("              <w:r wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println
		 * ("                <w:t>成績</w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>"); out.println ("          <w:tc>");
		 * 
		 * 
		 * 
		 * 
		 * out.println ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='1250' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:adjustRightInd w:val='off'/>"); out.println
		 * ("                <w:snapToGrid w:val='off'/>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println ("              </w:pPr>");
		 * out.println ("              <w:r wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println
		 * ("                <w:t>選別</w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>");
		 * 
		 * 
		 * 
		 * 
		 * out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='1250' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:adjustRightInd w:val='off'/>"); out.println
		 * ("                <w:snapToGrid w:val='off'/>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println ("              </w:pPr>");
		 * out.println ("              <w:r wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println
		 * ("                <w:t>學分數</w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>");
		 * 
		 * 
		 * 
		 * out.println ("        </w:tr>"); for(int j=0; j<selds.size(); j++){
		 * 
		 * 
		 * 
		 * 
		 * 
		 * out.println (
		 * "        <w:tr wsp:rsidR='0017666E' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>"
		 * ); out.println ("          <w:trPr>"); out.println
		 * ("            <w:trHeight w:val='56'/>"); out.println
		 * ("          </w:trPr>");
		 * 
		 * 
		 * 
		 * out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='1250' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:adjustRightInd w:val='off'/>"); out.println
		 * ("                <w:snapToGrid w:val='off'/>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println ("              </w:pPr>");
		 * out.println ("              <w:r wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println
		 * ("                <w:t>"+((Map
		 * )selds.get(j)).get("chi_name")+"</w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>");
		 * 
		 * 
		 * 
		 * out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='1250' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00C51466' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:adjustRightInd w:val='off'/>"); out.println
		 * ("                <w:snapToGrid w:val='off'/>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println ("              </w:pPr>");
		 * out.println ("              <w:r wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println
		 * ("                <w:t>"+((Map)selds.get(j)).get("score")+"</w:t>");
		 * out.println ("              </w:r>"); out.println
		 * ("            </w:p>"); out.println ("          </w:tc>");
		 * out.println ("          <w:tc>");
		 * 
		 * 
		 * 
		 * 
		 * out.println ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='1250' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:adjustRightInd w:val='off'/>"); out.println
		 * ("                <w:snapToGrid w:val='off'/>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println ("              </w:pPr>");
		 * out.println ("              <w:r wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>");
		 * 
		 * if(((Map)selds.get(j)).get("opt").equals("1")){ out.println
		 * ("                <w:t>必修</w:t>"); }
		 * 
		 * if(((Map)selds.get(j)).get("opt").equals("2")){ out.println
		 * ("                <w:t>選修</w:t>"); }
		 * 
		 * if(((Map)selds.get(j)).get("opt").equals("3")){ out.println
		 * ("                <w:t>通識</w:t>"); }
		 * 
		 * out.println ("              </w:r>"); out.println
		 * ("            </w:p>"); out.println ("          </w:tc>");
		 * 
		 * out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='1250' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:adjustRightInd w:val='off'/>"); out.println
		 * ("                <w:snapToGrid w:val='off'/>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println ("              </w:pPr>");
		 * out.println ("              <w:r wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println
		 * ("                <w:t>"+((Map)selds.get(j)).get("credit")+"</w:t>");
		 * out.println ("              </w:r>"); out.println
		 * ("            </w:p>"); out.println ("          </w:tc>");
		 * 
		 * 
		 * 
		 * out.println ("        </w:tr>"); }
		 * 
		 * 
		 * 
		 * 
		 * for(int j=0; j<15-selds.size(); j++){
		 * 
		 * 
		 * out.println (
		 * "        <w:tr wsp:rsidR='0017666E' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>"
		 * ); out.println ("          <w:trPr>"); out.println
		 * ("            <w:trHeight w:val='56'/>"); out.println
		 * ("          </w:trPr>");
		 * 
		 * 
		 * 
		 * out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='1250' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:adjustRightInd w:val='off'/>"); out.println
		 * ("                <w:snapToGrid w:val='off'/>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println ("              </w:pPr>");
		 * out.println ("              <w:r wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println
		 * ("                <w:t></w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>");
		 * 
		 * 
		 * 
		 * out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='1250' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00C51466' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:adjustRightInd w:val='off'/>"); out.println
		 * ("                <w:snapToGrid w:val='off'/>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println ("              </w:pPr>");
		 * out.println ("              <w:r wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println
		 * ("                <w:t></w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>"); out.println ("          <w:tc>");
		 * 
		 * 
		 * 
		 * 
		 * out.println ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='1250' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:adjustRightInd w:val='off'/>"); out.println
		 * ("                <w:snapToGrid w:val='off'/>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println ("              </w:pPr>");
		 * out.println ("              <w:r wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println
		 * ("                <w:t></w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>");
		 * 
		 * 
		 * 
		 * 
		 * out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='1250' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='001537BC'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:adjustRightInd w:val='off'/>"); out.println
		 * ("                <w:snapToGrid w:val='off'/>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println ("              </w:pPr>");
		 * out.println ("              <w:r wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                  <w:sz-cs w:val='24'/>"); out.println
		 * ("                </w:rPr>"); out.println
		 * ("                <w:t></w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>");
		 * 
		 * 
		 * 
		 * out.println ("        </w:tr>");
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * out.println ("      </w:tbl>");
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * out.println (
		 * "      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='00CD0BF5'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("      </w:p>"); out.println ("      <w:tbl>"); out.println
		 * ("        <w:tblPr>"); out.println
		 * ("          <w:tblW w:w='5000' w:type='pct'/>"); out.println
		 * ("          <w:tblLook w:val='04A0'/>"); out.println
		 * ("        </w:tblPr>"); out.println ("        <w:tblGrid>");
		 * out.println ("          <w:gridCol w:w='4984'/>"); out.println
		 * ("          <w:gridCol w:w='4984'/>"); out.println
		 * ("        </w:tblGrid>"); out.println (
		 * "        <w:tr wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>"
		 * ); out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='2500' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00A12CDB' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("              </w:pPr>"); out.println
		 * ("              <w:r wsp:rsidRPr='001537BC'>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>共修學分數:</w:t>"); out.println
		 * ("              </w:r>"); out.println
		 * ("              <w:r wsp:rsidR='00423950' wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>"
		 * +((Map)stmds.get(i)).get("totalCredit")+"</w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>"); out.println ("          <w:tc>");
		 * out.println ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='2500' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00A12CDB' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("              </w:pPr>"); out.println
		 * ("              <w:r wsp:rsidRPr='001537BC'>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>實得學分數:</w:t>"); out.println
		 * ("              </w:r>"); out.println
		 * ("              <w:r wsp:rsidR='00423950' wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>"
		 * +((Map)stmds.get(i)).get("totalCredit")+"</w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>"); out.println ("        </w:tr>");
		 * out.println (
		 * "        <w:tr wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>"
		 * ); out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='2500' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00A12CDB' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("              </w:pPr>"); out.println
		 * ("              <w:r wsp:rsidRPr='001537BC'>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>學業總平均:</w:t>"); out.println
		 * ("              </w:r>"); out.println
		 * ("              <w:r wsp:rsidR='00423950' wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>"+((Map)stmds.get(i)).get("total")+"</w:t>");
		 * out.println ("              </w:r>"); out.println
		 * ("            </w:p>"); out.println ("          </w:tc>");
		 * out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='2500' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00A12CDB' wsp:rsidP='00CD0BF5'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("              </w:pPr>"); out.println
		 * ("              <w:r wsp:rsidRPr='001537BC'>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>名次:</w:t>"); out.println
		 * ("              </w:r>"); out.println
		 * ("              <w:r wsp:rsidR='00423950' wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>"+(i+1)+"</w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>"); out.println ("        </w:tr>");
		 * out.println (
		 * "        <w:tr wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>"
		 * ); out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='2500' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00A12CDB' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("              </w:pPr>"); out.println
		 * ("              <w:r wsp:rsidRPr='001537BC'>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>操行成績:</w:t>"); out.println
		 * ("              </w:r>"); out.println
		 * ("              <w:r wsp:rsidR='00423950' wsp:rsidRPr='001537BC'>");
		 * out.println ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>"
		 * +((Map)stmds.get(i)).get("total_score")+"</w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>"); out.println ("          <w:tc>");
		 * out.println ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='2500' w:type='pct'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00A12CDB' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00A12CDB' wsp:rsidP='00CD0BF5'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("              </w:pPr>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>"); out.println ("        </w:tr>");
		 * out.println ("      </w:tbl>"); out.println (
		 * "      <w:p wsp:rsidR='00597E25' wsp:rsidRDefault='00597E25' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:spacing w:line='280' w:line-rule='exact'/>");
		 * out.println ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("      </w:p>"); out.println (
		 * "      <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:spacing w:line='280' w:line-rule='exact'/>");
		 * out.println ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:proofErr w:type='gramStart'/>"); out.println
		 * ("        <w:r wsp:rsidRPr='00227CC9'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>註</w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:proofErr w:type='gramEnd'/>"); out.println
		 * ("        <w:r wsp:rsidRPr='00227CC9'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>：</w:t>");
		 * out.println ("        </w:r>"); out.println ("      </w:p>");
		 * out.println (
		 * "      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='006461A7' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='006461A7'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:pStyle w:val='a4'/>"); out.println
		 * ("          <w:listPr>"); out.println
		 * ("            <w:ilvl w:val='0'/>"); out.println
		 * ("            <w:ilfo w:val='2'/>"); out.println
		 * ("            <wx:t wx:val='1.'/>"); out.println
		 * ("            <wx:font wx:val='Times New Roman'/>"); out.println
		 * ("          </w:listPr>"); out.println
		 * ("          <w:spacing w:line='280' w:line-rule='exact'/>");
		 * out.println ("          <w:ind w:left-chars='0'/>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:r wsp:rsidRPr='006461A7'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println
		 * ("          <w:t>學期學業成績不及格科目之學分數連續兩學期達二分之</w:t>"); out.println
		 * ("        </w:r>"); out.println
		 * ("        <w:proofErr w:type='gramStart'/>"); out.println
		 * ("        <w:r wsp:rsidRPr='006461A7'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>ㄧ</w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:proofErr w:type='gramEnd'/>"); out.println
		 * ("        <w:r wsp:rsidRPr='006461A7'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>者，應予退學。</w:t>");
		 * out.println ("        </w:r>"); out.println ("      </w:p>");
		 * out.println (
		 * "      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='006461A7' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='006461A7'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:pStyle w:val='a4'/>"); out.println
		 * ("          <w:listPr>"); out.println
		 * ("            <w:ilvl w:val='0'/>"); out.println
		 * ("            <w:ilfo w:val='2'/>"); out.println
		 * ("            <wx:t wx:val='2.'/>"); out.println
		 * ("            <wx:font wx:val='Times New Roman'/>"); out.println
		 * ("          </w:listPr>"); out.println
		 * ("          <w:spacing w:line='280' w:line-rule='exact'/>");
		 * out.println ("          <w:ind w:left-chars='0'/>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:r wsp:rsidRPr='006461A7'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println
		 * ("          <w:t>成績如有錯誤，應於收到本單後</w:t>"); out.println
		 * ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidR='00953866' wsp:rsidRPr='006461A7'>");
		 * out.println ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>的</w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidRPr='006461A7'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>兩</w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:proofErr w:type='gramStart'/>"); out.println
		 * ("        <w:r wsp:rsidRPr='006461A7'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>週</w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:proofErr w:type='gramEnd'/>"); out.println
		 * ("        <w:r wsp:rsidRPr='006461A7'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>內申請更正。</w:t>");
		 * out.println ("        </w:r>"); out.println ("      </w:p>");
		 * out.println (
		 * "      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='006461A7' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='006461A7'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:pStyle w:val='a4'/>"); out.println
		 * ("          <w:listPr>"); out.println
		 * ("            <w:ilvl w:val='0'/>"); out.println
		 * ("            <w:ilfo w:val='2'/>"); out.println
		 * ("            <wx:t wx:val='3.'/>"); out.println
		 * ("            <wx:font wx:val='Times New Roman'/>"); out.println
		 * ("          </w:listPr>"); out.println
		 * ("          <w:spacing w:line='280' w:line-rule='exact'/>");
		 * out.println ("          <w:ind w:left-chars='0'/>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:r wsp:rsidRPr='006461A7'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println
		 * ("          <w:t>學生在校期間可點選(學生資訊系統之我的成績)，預先瞭解自己取得成績之資訊，做為</w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:proofErr w:type='gramStart'/>"); out.println
		 * ("        <w:r wsp:rsidRPr='006461A7'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>選課時修</w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:proofErr w:type='gramEnd'/>"); out.println
		 * ("        <w:r wsp:rsidRPr='006461A7'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>、退選之參考。</w:t>");
		 * out.println ("        </w:r>"); out.println ("      </w:p>");
		 * out.println (
		 * "      <w:p wsp:rsidR='00CD0BF5' wsp:rsidRPr='006461A7' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='006461A7'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:pStyle w:val='a4'/>"); out.println
		 * ("          <w:listPr>"); out.println
		 * ("            <w:ilvl w:val='0'/>"); out.println
		 * ("            <w:ilfo w:val='2'/>"); out.println
		 * ("            <wx:t wx:val='4.'/>"); out.println
		 * ("            <wx:font wx:val='Times New Roman'/>"); out.println
		 * ("          </w:listPr>"); out.println
		 * ("          <w:spacing w:line='280' w:line-rule='exact'/>");
		 * out.println ("          <w:ind w:left-chars='0'/>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:r wsp:rsidRPr='006461A7'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println
		 * ("          <w:t>退學生不得參加暑修。</w:t>"); out.println ("        </w:r>");
		 * out.println ("      </w:p>"); out.println (
		 * "      <w:p wsp:rsidR='00597E25' wsp:rsidRPr='006461A7' wsp:rsidRDefault='00CD0BF5' wsp:rsidP='006461A7'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:pStyle w:val='a4'/>"); out.println
		 * ("          <w:listPr>"); out.println
		 * ("            <w:ilvl w:val='0'/>"); out.println
		 * ("            <w:ilfo w:val='2'/>"); out.println
		 * ("            <wx:t wx:val='5.'/>"); out.println
		 * ("            <wx:font wx:val='Times New Roman'/>"); out.println
		 * ("          </w:listPr>"); out.println
		 * ("          <w:spacing w:line='280' w:line-rule='exact'/>");
		 * out.println ("          <w:ind w:left-chars='0'/>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:r wsp:rsidRPr='006461A7'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println (
		 * "          <w:t>依本校大學部學則第十四條規定：學生如重讀已修習成績及格而名稱相同之科目者，其原修習名稱相同之該一科目，應予註銷。</w:t>"
		 * ); out.println ("        </w:r>"); out.println ("      </w:p>");
		 * out.println (
		 * "      <w:p wsp:rsidR='006461A7' wsp:rsidRDefault='006461A7' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:spacing w:line='280' w:line-rule='exact'/>");
		 * out.println ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("            <w:sz w:val='22'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("      </w:p>"); out.println (
		 * "      <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:jc w:val='center'/>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:r wsp:rsidRPr='00227CC9'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println (
		 * "          <w:t>--------------------------------------------------------------------------------</w:t>"
		 * ); out.println ("        </w:r>"); out.println ("      </w:p>");
		 * out.println (
		 * "      <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:r wsp:rsidRPr='00227CC9'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>印刷品</w:t>");
		 * out.println ("        </w:r>"); out.println ("      </w:p>");
		 * 
		 * out.println (
		 * "      <w:p wsp:rsidR='00AE6DEE' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:r wsp:rsidRPr='00227CC9'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>");
		 * 
		 * out.println ("          <w:t>"+schoolAddr+"</w:t>");
		 * 
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidR='00C51466'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t></w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidRPr='00227CC9'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t></w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidR='00C51466'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t></w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidRPr='00227CC9'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t></w:t>");
		 * out.println ("        </w:r>"); out.println ("      </w:p>");
		 * out.println (
		 * "      <w:p wsp:rsidR='00C51466' wsp:rsidRDefault='00C51466' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("      </w:p>"); out.println (
		 * "      <w:p wsp:rsidR='006461A7' wsp:rsidRPr='00227CC9' wsp:rsidRDefault='006461A7' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("      </w:p>"); out.println ("      <w:tbl>"); out.println
		 * ("        <w:tblPr>"); out.println
		 * ("          <w:tblW w:w='0' w:type='auto'/>"); out.println
		 * ("          <w:tblBorders>"); out.println (
		 * "            <w:top w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>"
		 * ); out.println (
		 * "            <w:left w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>"
		 * ); out.println (
		 * "            <w:bottom w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>"
		 * ); out.println (
		 * "            <w:right w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>"
		 * ); out.println (
		 * "            <w:insideH w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>"
		 * ); out.println (
		 * "            <w:insideV w:val='single' w:sz='4' wx:bdrwidth='10' w:space='0' w:color='auto'/>"
		 * ); out.println ("          </w:tblBorders>"); out.println
		 * ("          <w:tblLook w:val='04A0'/>"); out.println
		 * ("        </w:tblPr>"); out.println ("        <w:tblGrid>");
		 * out.println ("          <w:gridCol w:w='2136'/>"); out.println
		 * ("        </w:tblGrid>"); out.println (
		 * "        <w:tr wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidTr='001537BC'>"
		 * ); out.println ("          <w:tc>"); out.println
		 * ("            <w:tcPr>"); out.println
		 * ("              <w:tcW w:w='0' w:type='auto'/>"); out.println
		 * ("              <w:shd w:val='clear' w:color='auto' w:fill='auto'/>"
		 * ); out.println ("            </w:tcPr>"); out.println (
		 * "            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("              </w:pPr>"); out.println
		 * ("              <w:r wsp:rsidRPr='001537BC'>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>國內郵資已付</w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println (
		 * "            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("              </w:pPr>"); out.println
		 * ("              <w:r wsp:rsidRPr='001537BC'>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>北區</w:t>"); out.println
		 * ("              </w:r>"); out.println
		 * ("              <w:proofErr w:type='gramStart'/>"); out.println
		 * ("              <w:r wsp:rsidRPr='001537BC'>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>營收股許可證</w:t>"); out.println
		 * ("              </w:r>"); out.println
		 * ("              <w:proofErr w:type='gramEnd'/>"); out.println
		 * ("            </w:p>"); out.println (
		 * "            <w:p wsp:rsidR='00AE6DEE' wsp:rsidRPr='001537BC' wsp:rsidRDefault='00AE6DEE' wsp:rsidP='00AE6DEE'>"
		 * ); out.println ("              <w:pPr>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("              </w:pPr>"); out.println
		 * ("              <w:r wsp:rsidRPr='001537BC'>"); out.println
		 * ("                <w:rPr>"); out.println (
		 * "                  <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("                  <wx:font wx:val='標楷體'/>");
		 * out.println ("                </w:rPr>"); out.println
		 * ("                <w:t>北台字第17573號</w:t>"); out.println
		 * ("              </w:r>"); out.println ("            </w:p>");
		 * out.println ("          </w:tc>"); out.println ("        </w:tr>");
		 * out.println ("      </w:tbl>"); out.println (
		 * "      <w:p wsp:rsidR='00C51466' wsp:rsidRDefault='00C51466' wsp:rsidP='002E6B1A'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("      </w:p>"); out.println (
		 * "      <w:p wsp:rsidR='00597E25' wsp:rsidRDefault='00597E25' wsp:rsidP='002E6B1A'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("      </w:p>"); out.println (
		 * "      <w:p wsp:rsidR='00AE6DEE' wsp:rsidRDefault='00423950' wsp:rsidP='002E6B1A'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:ind w:left-chars='827' w:left='1985'/>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>"); out.println
		 * ("        <w:r>"); out.println ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); try{ out.println
		 * ("          <w:t>"+((Map)stmds
		 * .get(i)).get("curr_post")+((Map)stmds.get
		 * (i)).get("curr_addr")+"</w:t>"); }catch(Exception e){ out.println
		 * ("          <w:t></w:t>"); }
		 * 
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidR='00C51466'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t></w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidR='00C51466' wsp:rsidRPr='00227CC9'>");
		 * out.println ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("          <w:t>電話</w:t>");
		 * out.println ("        </w:r>"); out.println
		 * ("        <w:r wsp:rsidR='002E6B1A'>"); out.println
		 * ("          <w:rPr>"); out.println (
		 * "            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體' w:hint='fareast'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); try{ out.println
		 * ("          <w:t>"+((Map)stmds.get(i)).get("telephone")+"</w:t>");
		 * }catch(Exception e){ out.println ("          <w:t></w:t>"); }
		 * 
		 * out.println ("        </w:r>"); out.println ("      </w:p>");
		 * out.println (
		 * "      <w:p wsp:rsidR='00C51466' wsp:rsidRPr='00597E25' wsp:rsidRDefault='00C51466' wsp:rsidP='002E6B1A'>"
		 * ); out.println ("        <w:pPr>"); out.println
		 * ("          <w:ind w:left-chars='827' w:left='1985'/>"); out.println
		 * ("          <w:rPr>"); out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); out.println ("        </w:pPr>");
		 * 
		 * out.println ("        <w:r>"); out.println ("          <w:rPr>");
		 * out.println
		 * ("            <w:rFonts w:ascii='標楷體' w:fareast='標楷體' w:h-ansi='標楷體'/>"
		 * ); out.println ("            <wx:font wx:val='標楷體'/>"); out.println
		 * ("          </w:rPr>"); try{ out.println
		 * ("          <w:t>"+((Map)stmds
		 * .get(i)).get("parent_name")+" 先生/小姐收</w:t>"); }catch(Exception e){
		 * out.println ("          <w:t></w:t>"); }
		 * 
		 * out.println ("        </w:r>"); out.println ("      </w:p>");
		 * 
		 * if(i!=stmds.size()-1){ out.println ("    <w:br w:type='page'/>"); } }
		 * out.println (
		 * "      <w:sectPr wsp:rsidR='00C51466' wsp:rsidRPr='00597E25' wsp:rsidSect='00597E25'>"
		 * ); out.println ("        <w:pgSz w:w='11906' w:h='16838'/>");
		 * out.println (
		 * "        <w:pgMar w:top='567' w:right='1077' w:bottom='567' w:left='1077' w:header='851' w:footer='992' w:gutter='0'/>"
		 * ); out.println ("        <w:cols w:space='425'/>"); out.println
		 * ("        <w:docGrid w:type='lines' w:line-pitch='360'/>");
		 * out.println ("      </w:sectPr>"); out.println ("    </wx:sect>");
		 * out.println ("  </w:body>"); out.println ("</w:wordDocument>");
		 * 
		 * out.close(); return true;
		 */

		/*
		 * String reportSourceFile = "/WEB-INF/reports/FinalScoreNotice.jrxml";
		 * String reportCompiledFile =
		 * "/WEB-INF/reports/FinalScoreNotice.jasper"; String reportSourceFileHC
		 * = "/WEB-INF/reports/FinalScoreNoticeHC.jrxml"; String
		 * reportCompiledFileHC = "/WEB-INF/reports/FinalScoreNoticeHC.jasper";
		 * String reportSourceFileHC2 =
		 * "/WEB-INF/reports/FinalScoreNoticeHC2.jrxml"; String
		 * reportCompiledFileHC2 =
		 * "/WEB-INF/reports/FinalScoreNoticeHC2.jasper"; String
		 * reportSourceFileN = "/WEB-INF/reports/FinalScoreNoticeN.jrxml";
		 * String reportCompiledFileN =
		 * "/WEB-INF/reports/FinalScoreNoticeN.jasper"; String reportSourceFileH
		 * = "/WEB-INF/reports/FinalScoreNoticeH.jrxml"; String
		 * reportCompiledFileH = "/WEB-INF/reports/FinalScoreNoticeH.jasper";
		 * HttpSession session = request.getSession(false); DynaActionForm aForm
		 * = (DynaActionForm) form; Map<String, String> map = aForm.getMap();
		 * map.put("name2", ""); map.put("studentNo2", ""); map.put("idNo2",
		 * ""); map.put("status2", ""); // map.put("classInCharge2", "1220");
		 * 
		 * String campusNo = aForm.getString("campusInCharge2").equals("1") ?
		 * "台北" : (aForm.getString("campusInCharge2").equals("5") ? "高雄" :
		 * "新竹"); boolean isPrintInterClass = "yes".equalsIgnoreCase(request
		 * .getParameter("printInterClass")); // 是否列印隨班附讀?預設為false boolean
		 * isHsinChu = "2".equals(aForm.getString("campusInCharge2"));
		 * UserCredential credential = getUserCredential(session); MemberManager
		 * mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		 * CourseManager cm = (CourseManager)
		 * getBean(IConstants.COURSE_MANAGER_BEAN_NAME); ScoreManager sm =
		 * (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		 * StudAffairManager sam = (StudAffairManager)
		 * getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME); AdminManager am =
		 * (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME); List<Student>
		 * students = getStudentsByCondition(mm, aForm.getMap(),
		 * credential.getClassInChargeSqlFilter()); String year =
		 * request.getParameter("year"); String term =
		 * am.findTermBy(PARAMETER_SCHOOL_TERM); Integer schoolYear =
		 * Integer.valueOf(am .findTermBy(PARAMETER_SCHOOL_YEAR)); boolean bFlag
		 * = year.equals(schoolYear.toString()) && term.equals(schoolTerm); //
		 * 檢查是否為本學期進行式?
		 * 
		 * ServletContext context = request.getSession().getServletContext();
		 * File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
		 * + credential.getMember().getIdno() + (new
		 * SimpleDateFormat("yyyyMMdd").format(new Date()))) +
		 * "FinalScoreNotice"); if (!tempDir.exists()) tempDir.mkdirs();
		 * 
		 * File[] garbag = tempDir.listFiles(); if (garbag.length > 0) { for
		 * (File f : garbag) f.delete(); }
		 * 
		 * JasperReportUtils.initJasperReportsClasspath(request); if
		 * (!students.isEmpty()) {
		 * 
		 * String departClass = students.get(0).getDepartClass(); DecimalFormat
		 * df = new DecimalFormat(",##0.0"); File reportFile = null; if
		 * (!isHsinChu) { if (ArrayUtils.contains(IConstants.DEPT_DAY,
		 * StringUtils .substring(departClass, 1, 3))) { reportFile = new
		 * File(context .getRealPath(reportCompiledFile));
		 * JasperReportUtils.compileJasperReports(context
		 * .getRealPath(reportSourceFile)); reportFile = new File(context
		 * .getRealPath(reportCompiledFile)); if (!reportFile.exists()) throw
		 * new JRRuntimeException(
		 * "查無\"FinalScoreNotice.jasper\"檔案，請電洽電算中心，謝謝！！"); } else if
		 * (ArrayUtils.contains(IConstants.DEPT_NIGHT,
		 * StringUtils.substring(departClass, 1, 3))) { reportFile = new
		 * File(context .getRealPath(reportCompiledFileN));
		 * JasperReportUtils.compileJasperReports(context
		 * .getRealPath(reportSourceFileN)); reportFile = new File(context
		 * .getRealPath(reportCompiledFileN)); if (!reportFile.exists()) throw
		 * new JRRuntimeException(
		 * "查無\"FinalScoreNoticeN.jasper\"檔案，請電洽電算中心，謝謝！！"); } else if
		 * (ArrayUtils.contains(IConstants.DEPT_HOLIDAY,
		 * StringUtils.substring(departClass, 1, 3))) { reportFile = new
		 * File(context .getRealPath(reportCompiledFileH));
		 * JasperReportUtils.compileJasperReports(context
		 * .getRealPath(reportSourceFileH)); reportFile = new File(context
		 * .getRealPath(reportCompiledFileH)); if (!reportFile.exists()) throw
		 * new JRRuntimeException(
		 * "查無\"FinalScoreNoticeH.jasper\"檔案，請電洽電算中心，謝謝！！"); } } else { String[]
		 * excluded = { "12", "22" }; if (ArrayUtils.contains(excluded,
		 * StringUtils.substring( departClass, 1, 3))) { reportFile = new
		 * File(context .getRealPath(reportCompiledFileHC2));
		 * JasperReportUtils.compileJasperReports(context
		 * .getRealPath(reportSourceFileHC2)); reportFile = new File(context
		 * .getRealPath(reportCompiledFileHC2)); if (!reportFile.exists()) throw
		 * new JRRuntimeException(
		 * "查無\"FinalScoreNoticeHC2.jasper\"檔案，請電洽電算中心，謝謝！！"); } else {
		 * reportFile = new File(context .getRealPath(reportCompiledFileHC));
		 * JasperReportUtils.compileJasperReports(context
		 * .getRealPath(reportSourceFileHC)); reportFile = new File(context
		 * .getRealPath(reportCompiledFileHC)); if (!reportFile.exists()) throw
		 * new JRRuntimeException(
		 * "查無\"FinalScoreNoticeHC.jasper\"檔案，請電洽電算中心，謝謝！！"); } }
		 * 
		 * if (bFlag) { //aForm.set("classInCharge2", "18GV"); //
		 * 專門給夜校生所用的,要印的時候打開commend,且修改processClassInfo() if(reportFile==null){
		 * reportFile = new File(context.getRealPath(reportSourceFileH)); }
		 * System.out.println("reportFile.getPath()="+reportFile.getPath());
		 * 
		 * ClassScoreSummary summary = new ClassScoreSummary(schoolYear
		 * .toString(), schoolTerm, processClassInfo(aForm), Examine.FINAL);
		 * 
		 * JasperReport jasperReport = (JasperReport) JRLoader
		 * .loadObject(reportFile.getPath()); String[] fields = { "courseName",
		 * "score", "opt", "credit", "interClass" }; // 論文,碩士論文一,二要排除計算
		 * 
		 * float passScore = 0.0F, credits = 0.0f, passCredits = 0.0f; String
		 * className = null, studentNo = null, cscode = null; Integer i = null;
		 * int rankSize = 0; Double score = null;
		 * 
		 * boolean bFlag1 = false;
		 * 
		 * String[] cscodes = null; Integer[] dtimeOids = null;
		 * 
		 * Student student = null; Just just = null; Seld seld = null;
		 * JasperPrint jasperPrint = null;
		 * 
		 * Set<StdScore> sss = null; List<Object> printData = null; List<Map>
		 * seldInfo = null;
		 * 
		 * Map<String, String> parameters = null;
		 * 
		 * List<JasperPrint> jasperPrints = new LinkedList<JasperPrint>();
		 * List<ClassScoreSummary> csss = sm
		 * .findClassScoreSummaryByLikeExpression(summary); for
		 * (ClassScoreSummary css : csss) { // 碩士班70分及格 departClass =
		 * css.getDepartClass(); passScore =
		 * Toolket.getPassScoreByDepartClass(departClass); className =
		 * Toolket.getClassFullName(departClass); i =
		 * Toolket.getMaxyear(departClass); bFlag1 = departClass.length() == 4
		 * || Toolket.isDelayClass(departClass) ||
		 * Integer.parseInt(StringUtils.substring( departClass, 4, 5)) > i; //
		 * 延修生或跨校不比名次
		 * 
		 * // 132D33怪怪的,上面判斷會是true bFlag1 = bFlag1 &&
		 * ("132D33".equals(departClass) || "1B2N31" .equals(departClass)) ?
		 * false : bFlag1;
		 * 
		 * sss = css.getStdScoreSet(); rankSize = sss.size(); for (StdScore ss :
		 * sss) { parameters = new HashMap<String, String>(); printData = new
		 * ArrayList<Object>(); studentNo = ss.getStudentNo(); student =
		 * mm.findStudentByNo(studentNo); if (student == null) continue;
		 * 
		 * just = sam.findJustByStudentNo(studentNo);
		 * parameters.put("PASSSCORE", String.valueOf(passScore));
		 * parameters.put("A", campusNo); parameters.put("B",
		 * schoolYear.toString()); parameters.put("C", schoolTerm);
		 * parameters.put("D", bFlag1 ? "" : String.valueOf(sss .size()));
		 * parameters.put("E", className); parameters.put("F", studentNo);
		 * parameters.put("G", student.getStudentName()); parameters .put("I",
		 * df.format(ss.getAverage() + 0.001D)); parameters.put("L", bFlag1 ? ""
		 * : ss.getPos() .toString() + " / " + rankSize); parameters.put("J",
		 * String.valueOf(Math .round((just == null || just.getTotalScore() ==
		 * null ? 0.0D : just.getTotalScore() >= 95.0D ? 95.0D :
		 * just.getTotalScore())))); parameters.put("M", student.getCurrPost());
		 * parameters.put("N", student.getCurrAddr()); if
		 * (ArrayUtils.contains(IConstants.DEPT_DAY,
		 * StringUtils.substring(student.getDepartClass(), 1, 3)))
		 * parameters.put("O", student.getParentName()); else
		 * parameters.put("O", student.getStudentName());
		 * 
		 * seldInfo = processExcludedThesis(cm
		 * .findStudentSeldCourse1(studentNo, schoolTerm)); cscodes = new
		 * String[0]; dtimeOids = new Integer[0]; credits = 0.0f; passCredits =
		 * 0.0f; for (Map content : seldInfo) { cscode = (String)
		 * content.get("cscode"); // 論文,碩士論文一,二要排除計算 // 多判斷Dtime
		 * Oid是因為有學生同時修相同Cscode但不同DepartClass if ((Arrays.binarySearch(cscodes,
		 * cscode) >= 0 && Arrays .binarySearch(dtimeOids, (Integer) content
		 * .get("oid")) >= 0) || ArrayUtils.contains(EXCLUDED_CSCODE, cscode)) {
		 * continue; } else { cscodes = (String[]) ArrayUtils.add(cscodes,
		 * cscode); dtimeOids = (Integer[]) ArrayUtils .add(dtimeOids, (Integer)
		 * content .get("oid")); Arrays.sort(cscodes); // Search needed to sort
		 * Arrays.sort(dtimeOids); } credits += (Float) content.get("credit");
		 * Object[] obj = new Object[5]; obj[0] = (String)
		 * content.get("chi_name"); seld = sm.findSeld((Integer)
		 * content.get("oid"), studentNo); score = seld == null ||
		 * seld.getScore() == null ? 0.0D : Math.round(seld.getScore()); if
		 * (score >= passScore) passCredits += (Float) content.get("credit");
		 * obj[1] = score.toString(); obj[2] = Toolket.getCourseOpt((String)
		 * content .get("opt")); obj[3] = ((Float)
		 * content.get("credit")).toString(); obj[4] = ""; printData.add(obj); }
		 * 
		 * parameters.put("H", String.valueOf(credits)); parameters.put("K",
		 * String.valueOf(passCredits));
		 * 
		 * jasperPrint = JasperFillManager.fillReport( jasperReport, parameters,
		 * new HibernateQueryResultDataSource(printData, fields));
		 * jasperPrints.add(jasperPrint); jasperPrint = null; } }
		 * 
		 * if (!jasperPrints.isEmpty()) { JRRtfExporter rtfExporter = new
		 * JRRtfExporter(); rtfExporter .setParameter(
		 * JRExporterParameter.JASPER_PRINT_LIST, jasperPrints); rtfExporter
		 * .setParameter( JRExporterParameter.OUTPUT_FILE_NAME, context
		 * .getRealPath("/WEB-INF/reports/FinalScoreNotice.doc"));
		 * rtfExporter.exportReport(); File output = new File( context
		 * .getRealPath("/WEB-INF/reports/FinalScoreNotice.doc"));
		 * JasperReportUtils.printXlsToFrontEnd(response, output); }
		 * 
		 * tempDir.delete(); return true; } else { for (int j = 0; j <
		 * students.size(); j++) { Student student = students.get(j);
		 * departClass = student.getDepartClass(); String studentNo =
		 * student.getStudentNo(); // 須先轉入歷史資料才行 List scoreHistResults =
		 * cm.getScoreHistBy(studentNo, year, schoolTerm); List<Seld> selds =
		 * cm.findSeldByStudentNoAndTerm(studentNo, schoolTerm); // 隨班附讀班級資料
		 * Map<String, String> appendCourses = new HashMap<String, String>(); if
		 * (isPrintInterClass) { for (Seld seld : selds) { Dtime dtime =
		 * cm.findDtimeBy(seld.getDtimeOid()); if
		 * (!departClass.equals(dtime.getDepartClass()))
		 * appendCourses.put(dtime.getCscode(), dtime .getDepartClass()); } }
		 * 
		 * Map<String, String> parameters = new HashMap<String, String>(); float
		 * credits = 0.0f, passCredits = 0.0f; List<Object> printData = new
		 * ArrayList<Object>(); // 碩士班70分及格 float passScore = Toolket
		 * .getPassScoreByDepartClass(departClass); for (int i = 0; i <
		 * scoreHistResults.size(); i++) { Map content = (Map)
		 * scoreHistResults.get(i); // 99999=操行,不列入成績顯示清單內 String cscode =
		 * (String) content.get("cscode"); if ("99999".equals(cscode)) {
		 * parameters .put( "J", (Float) content.get("score") >= 95.0D ? "95.0"
		 * : String .valueOf(Math .round((Float) content .get("score"))));
		 * continue; } credits += (Float) content.get("credit"); if ((Float)
		 * content.get("score") >= passScore) passCredits += (Float)
		 * content.get("credit"); Object[] obj = new Object[5]; obj[0] =
		 * (String) content.get("chi_name"); obj[1] = ((Float)
		 * content.get("score")).toString(); obj[2] = (String)
		 * content.get("opt"); obj[3] = ((Float)
		 * content.get("credit")).toString(); if (isPrintInterClass) { //
		 * 如果不是學生該班課程,則註記附讀班級名稱 obj[4] = appendCourses.containsKey(cscode) ?
		 * Toolket .getClassFullName(appendCourses.get(cscode)) : ""; }
		 * printData.add(obj); }
		 * 
		 * if (printData.isEmpty()) continue;
		 * 
		 * else {
		 * 
		 * JasperReport jasperReport = (JasperReport) JRLoader
		 * .loadObject(reportFile.getPath()); String[] fields = { "courseName",
		 * "score", "opt", "credit", "interClass" }; parameters.put("PASSSCORE",
		 * String.valueOf(passScore)); parameters.put("A", campusNo); // 校區
		 * parameters.put("B", year); // 年度 parameters.put("C", schoolTerm); //
		 * 學期 parameters.put("E", Toolket.getClassFullName(student
		 * .getDepartClass())); // 學生班級 parameters.put("F", studentNo); // 學生學號
		 * parameters.put("G", student.getStudentName()); // 學生姓名
		 * parameters.put("H", String.valueOf(credits)); // 共修學分數 Stavg stavg =
		 * sm.findStavgBy(studentNo, year, schoolTerm); if (stavg != null &&
		 * stavg.getScore() != null) { parameters.put("I", df
		 * .format(stavg.getScore() + 0.001D)); // departClass必須因為查歷年成績而減少年級
		 * parameters.put("L", calRank(sm.calStavgRank(
		 * checkRankDepartClass(departClass, schoolYear, Integer.valueOf(year)),
		 * year, schoolTerm), studentNo)); } parameters.put("K",
		 * String.valueOf(passCredits)); // 實得學分數 parameters.put("M",
		 * student.getCurrPost()); // 郵遞區號 parameters.put("N",
		 * student.getCurrAddr()); // 郵寄地址 if
		 * (ArrayUtils.contains(IConstants.DEPT_DAY,
		 * StringUtils.substring(student.getDepartClass(), 1, 3)))
		 * parameters.put("O", student.getParentName()); // 學生家長姓名 else
		 * parameters.put("O", student.getStudentName()); // 學生姓名
		 * 
		 * JasperPrint jasperPrint = JasperFillManager.fillReport( jasperReport,
		 * parameters, new HibernateQueryResultDataSource(printData, fields));
		 * OutputStream os = new BufferedOutputStream( new FileOutputStream(new
		 * File(tempDir, studentNo + ".pdf")));
		 * JasperExportManager.exportReportToPdfStream( jasperPrint, os);
		 * os.close(); } } }
		 * 
		 * File[] files = tempDir.listFiles(new FilenameFilter() { public
		 * boolean accept(File dir, String name) { return name.endsWith(".pdf");
		 * } });
		 * 
		 * if (files.length > 0) { File printingPdf =
		 * mergePdfsForPrinting(files, new File(System
		 * .getProperty("java.io.tmpdir")), "FinalScoreNoticePDF.pdf");
		 * JasperReportUtils.printPdfToFrontEnd(response, printingPdf);
		 * printingPdf.delete(); } else { Map<String, String> parameters = new
		 * HashMap<String, String>(); File image = new File(context
		 * .getRealPath("/pages/reports/2002chitS.jpg"));
		 * parameters.put("IMAGE", image.getAbsolutePath()); byte[] bytes =
		 * JasperRunManager.runReportToPdf(
		 * JasperReportUtils.getNoResultReport(context), parameters, new
		 * JREmptyDataSource()); JasperReportUtils.printPdfToFrontEnd(response,
		 * bytes); } tempDir.delete(); return true; } else return false;
		 */

	}

	/**
	 * 建立通識班級ClassScoreSummary而已(因為要跑預覽期中期末教師任教科目不及格人數統計表)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param sterm
	 * @throws Exception
	 */
	protected void printMidtermFinalResult4Literacy(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		UserCredential credential = getUserCredential(session);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;

		String year = cm.getNowBy("School_year");
		boolean isFinalResult = "final".equalsIgnoreCase(request
				.getParameter("rt")); // 是否為期末?預設為期末成績

		int[] rankCounts = null;
		float totalScores = 0.0F;
		int pass = 0, notPass = 0;
		Double score = 0.0D;
		Float passScore = 0.0F;
		Object[] o = null;
		String classNo = null, s1 = null;
		Dtime dtime = null;
		ClassScoreSummary summary = null, target = null;
		ScoreStatistic scoreStatistic = null;
		List<Object> dtimeAndCsno = null;
		// List<Student> students = null;
		DecimalFormat df = new DecimalFormat(",##0.0");
		List<Seld> selds = null;
		List<Clazz> clazzes = sm.findClassBy(
				new Clazz(processClassInfo(aForm)),
				credential.getClassInChargeAry(), true);

		if (!clazzes.isEmpty()) {
			for (Clazz clazz : clazzes) {
				classNo = clazz.getClassNo();
				if (Toolket.isLiteracyClass(classNo)) {
					dtimeAndCsno = excludedSomething1(cm.findDtimeCsnoBy(
							new Dtime(classNo, sterm), "cscode")); // 以cscode排序

					if (!dtimeAndCsno.isEmpty()) {
						target = new ClassScoreSummary(year, sterm, classNo,
								(isFinalResult ? Examine.FINAL : Examine.MID));
						sm.deleteClassScoreSummary(sm
								.findClassScoreSummaryByLikeExpression(target));

						if (isFinalResult)
							summary = new ClassScoreSummary(year, sterm,
									classNo, Examine.FINAL);
						else
							summary = new ClassScoreSummary(year, sterm,
									classNo, Examine.MID);

						summary.setAverage(0.0F);
						summary.setSummary1(0);
						summary.setSummary2(0);

						passScore = Toolket.getPassScoreByDepartClass(classNo);

						for (Object content : dtimeAndCsno) {
							o = (Object[]) content;
							dtime = (Dtime) o[0];

							pass = 0;
							notPass = 0;
							totalScores = 0.0F;
							rankCounts = new int[5];
							selds = sm.findSeldsBy(dtime.getOid());
							if (!selds.isEmpty()) {

								for (Seld seld : selds) {
									score = isFinalResult ? seld.getScore()
											: seld.getScore2();
									totalScores += score;

									if (score >= passScore)
										pass++;
									else
										notPass++;

									if (score < 20)
										rankCounts[0]++;
									else if (score >= 20 && score <= 39)
										rankCounts[1]++;
									else if (score >= 40 && score <= 59)
										rankCounts[2]++;
									else if (score >= 60 && score <= 79)
										rankCounts[3]++;
									else if (score >= 80)
										rankCounts[4]++;
								}

								s1 = Double.isNaN(totalScores
										/ (pass + notPass)) ? "0.0"
										: Toolket
												.getRoundFormat(
														df,
														(totalScores / (pass + notPass)),
														0.001D);
								scoreStatistic = new ScoreStatistic();
								scoreStatistic.setCscode(dtime.getCscode());
								scoreStatistic.setAverage(Float.parseFloat(s1));
								scoreStatistic.setPass(pass);
								scoreStatistic.setFail(notPass);
								scoreStatistic.setRange1(rankCounts[0]);
								scoreStatistic.setRange2(rankCounts[1]);
								scoreStatistic.setRange3(rankCounts[2]);
								scoreStatistic.setRange4(rankCounts[3]);
								scoreStatistic.setRange5(rankCounts[4]);
								summary.getScoreStatisticSet().add(
										scoreStatistic);
							}

						}

						summary.setLastModified(new Date());
						sm.txSaveClassScoreSummary(summary);
					}
				}
			}

			Map<String, String> param = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getProcessOkReport(context), param,
					new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		} else {
			Map<String, String> parameters = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/reports/2002chitS.jpg"));
			parameters.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), parameters,
					new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}
	}

	protected void printMidtermFinalResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		// String term = (String) aForm.get("sterm");
		String year = cm.getNowBy("School_year");
		String schoolTerm = cm.getNowBy("School_term");
		boolean isFinalResult = "final".equalsIgnoreCase(request
				.getParameter("rt")); // 是否為期末?預設為期末成績
		boolean isPdf = "pdf".equalsIgnoreCase(request.getParameter("rf"));
		boolean isHistory = !sterm.equals(schoolTerm);
		// 頁面要加推廣(122A)選項
		// 1220 夜跨校生
		// 1152A 日跨校生
		// 要改的地方:ScoreDAOImpl.findClassBy(), this.processClassInfo()
		// Line 792, 794, 799與894
		boolean isPopularizeClass = false; // 1220時要改
		if (isPopularizeClass)
			aForm.set("classInCharge2", "1K1S11"); // 1220時要改

		ServletContext context = request.getSession().getServletContext();
		// 1220時要改true
		// 1X1X11不需改
		List<Clazz> clazzes = sm.findClassBy(
				new Clazz(processClassInfo(aForm)),
				credential.getClassInChargeAry(), false);
		if (!clazzes.isEmpty()) {

			String[] courseNameIndex = { "E", "F", "G", "H", "I", "J", "K",
					"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U" }; // 科目名稱
			String[] optCreditInfos = { "A1", "A2", "A3", "A4", "A5", "A6",
					"A7", "A8", "A9", "A10", "A11", "A12", "A13", "A14", "A15",
					"A16", "A17" }; // 科目必選修及學分數
			String[] passCountsIndex = { "B1", "B2", "B3", "B4", "B5", "B6",
					"B7", "B8", "B9", "B10", "B11", "B12", "B13", "B14", "B15",
					"B16", "B17" }; // 及格人數
			String[] notPassCountsIndex = { "C1", "C2", "C3", "C4", "C5", "C6",
					"C7", "C8", "C9", "C10", "C11", "C12", "C13", "C14", "C15",
					"C16", "C17" }; // 不及格人數
			String[] courseScoreAvgIndex = { "D1", "D2", "D3", "D4", "D5",
					"D6", "D7", "D8", "D9", "D10", "D11", "D12", "D13", "D14",
					"D15", "D16", "D17" }; // 各科平均分數
			String[] fields = { "studentNoF", "nameF", "score1F", "score2F",
					"score3F", "score4F", "score5F", "score6F", "score7F",
					"score8F", "score9F", "score10F", "score11F", "score12F",
					"score13F", "score14F", "score15F", "score16F", "score17F",
					"totalCreditF", "passCreditF", "averageF", "rankF",
					"behaviorF", "statusF", "extraCourseInfo1F",
					"extraCourseInfo2F" };

			File reportFile = new File(
					context.getRealPath("/WEB-INF/reports/MidtermFinalResultMore.jasper"));
			// 需要時再編譯即可
			if (!reportFile.exists()) {
				JasperReportUtils.initJasperReportsClasspath(request);
				JasperReportUtils
						.compileJasperReports(context
								.getRealPath("/WEB-INF/reports/MidtermFinalResultMore.jrxml"));
				reportFile = new File(
						context.getRealPath("/WEB-INF/reports/MidtermFinalResultMore.jasper"));
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"查無\"MidtermFinalResultMore.jasper\"檔案，請電洽電算中心，謝謝！！");
			}

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());

			DecimalFormat df = new DecimalFormat(",##0.0");

			List<Student> students = null;
			List<Object> printData = null;
			List<ScoreHist> scoreHistList = null;
			List<Seld> selds = null;
			List<MidtermExcluded> mids = null;
			List<Object> dtimeAndCsno = null;
			List<JasperPrint> jasperPrints = new LinkedList<JasperPrint>();

			Map<String, String> parameters = null;

			JasperPrint jasperPrint = null;
			Dtime dtime = null, extraDtime = null, tempDtime = null;
			Csno csno = null;
			Just just = null;
			ScoreHist scoreHist = null;
			MidtermExcluded me = null;
			ScoreStatus scoreStatus = null;
			ScoreStatistic scoreStatistic = null;
			ClassScoreSummary summary = null;
			ClassScoreSummary target = null;
			StdScore stdScore = null;

			String scoreString = null, classNo = null, extraClassStr = null, studentScoreAvg = null, s1 = "0.0", couduct = null;
			Float totalCredits = 0.0F, passCredits = 0.0F, passScore = 0.0F;
			Double totalScore = 0.0D, score = 0.0D, tempAvg = 0.0D, s = 0.0D;
			StringBuffer extraClassInfo1 = null, extraClassInfo2 = null;
			double lowOver23 = 1.0D / 3.0D, justScore = 0.0D;
			float credit = 0.0F, classAvgScore = 0.0F;
			int index = 0, studentIndex = 0, dtimesSize = 0, studentCounts = 0, cscodeIndex = 0;
			int notPass21 = 0, notPass32 = 0;
			boolean isLiteracyClass = false;

			// 論文,碩士論文一,二要排除計算
			String[] data = null, cscodes = null, midExcludes = null;
			Integer[] dtimeOids = null;
			float[] dtimeCredits = null;
			double[] avgs = null;
			Object[] obj = null, o = null;
			int[] passScores = new int[dtimesSize];
			int[] notPassScores = new int[dtimesSize];
			int[][] rankCounts = new int[dtimesSize][5];
			float[] totalScores = new float[dtimesSize];

			Double[][] studentScores = null;

			for (Clazz clazz : clazzes) {
				classNo = clazz.getClassNo();
				// 排除班級代碼最後一碼為字母與通識班級
				// 1220時要改(StringUtils.isNumeric(S...要移除)
				// 1X1X11不需改
				if (StringUtils.isNumeric(StringUtils.substring(classNo,
						classNo.length() - 1))
						&& !Toolket.isLiteracyClass(classNo)) {

					students = mm.findStudentsByClassNo(classNo);
					if (students.isEmpty())
						continue;

					// dtimes = excludedSomething(cm.findDtimeBy(new Dtime(
					// classNo, sterm), "cscode")); // 以cscode排序
					dtimeAndCsno = excludedSomething1(cm.findDtimeCsnoBy(
							new Dtime(classNo, sterm), "cscode")); // 以cscode排序
					midExcludes = new String[0];
					if (!isFinalResult) { // 期中要排除不納入計算的科目
						dtimeAndCsno = processExcludedCourse1(sm, dtimeAndCsno);
						me = new MidtermExcluded(classNo);
						mids = sm.findMidtermExcludedBy1(me);
						for (MidtermExcluded mid : mids) {
							midExcludes = (String[]) ArrayUtils.add(
									midExcludes, mid.getCscode());
						}
					}
					if (dtimeAndCsno.isEmpty() || dtimeAndCsno.size() >= 18) {
						// 目前最多只接受17門科目
						// 11G111只可以單獨印(超過20門科目),因為使用的是Excel
						if (dtimeAndCsno.size() >= 18 && clazzes.size() == 1
								&& "11G111".equals(clazzes.get(0).getClassNo())) {
							printMidFinalScoreFor11G111(context, request,
									response, year, sterm, isFinalResult);
							continue;
						} else if (Toolket.isDelayClass(classNo)) {
							// 處理延修班
							jasperPrint = printMidFinalScoreForDelayClass(
									context, request, response, year, sterm,
									classNo, isFinalResult);
							if (jasperPrint != null) {
								jasperPrints.add(jasperPrint);
								jasperPrint = null;
							}
							continue;

						}
						// else
						// continue;
					}

					target = new ClassScoreSummary(year, sterm, classNo,
							(isFinalResult ? Examine.FINAL : Examine.MID));
					sm.deleteClassScoreSummary(sm
							.findClassScoreSummaryByLikeExpression(target));

					parameters = new HashMap<String, String>();
					passScore = Toolket.getPassScoreByDepartClass(classNo);
					parameters.put("PASSSCORE", String.valueOf(passScore));
					parameters.put("A", year);
					parameters.put("B", sterm);
					parameters.put("C",
							isFinalResult ? Examine.FINAL.toString()
									: Examine.MID.toString());
					parameters.put("D", Toolket.getClassFullName(classNo));

					index = 0;
					dtimesSize = dtimeAndCsno.size();
					dtimeOids = new Integer[0];
					cscodes = new String[0];
					dtimeCredits = new float[0];
					for (Object content : dtimeAndCsno) {
						o = (Object[]) content;
						dtime = (Dtime) o[0];
						csno = (Csno) o[1];
						dtimeOids = (Integer[]) ArrayUtils.add(dtimeOids,
								dtime.getOid());
						cscodes = (String[]) ArrayUtils.add(cscodes,
								dtime.getCscode()); // 紀錄科目在Page Header中位置
						dtimeCredits = (float[]) ArrayUtils.add(dtimeCredits,
								dtime.getCredit());
						// csno = cm.getCourseInfoByCscode(dtime.getCscode());
						parameters.put(courseNameIndex[index],
								csno == null ? null : csno.getChiName());
						parameters.put(
								optCreditInfos[index++],
								csno == null ? null : StringUtils.substring(
										Toolket.getCourseOpt(dtime.getOpt()),
										0, 1)
										+ " " + dtime.getCredit());
					}

					printData = new ArrayList<Object>();
					studentIndex = 0;
					studentCounts = students.size();
					// 多2個是要記錄平均(有小數點)及操行
					studentScores = new Double[studentCounts][dtimesSize + 2];
					avgs = new double[0];
					// justs = new double[0];
					for (Student student : students) {
						data = new String[27];
						for (int i = 0; i < dtimesSize; i++)
							data[i + 2] = "/";

						data[0] = student.getStudentNo();
						data[1] = student.getStudentName();
						justScore = 0.0D;
						if (!isHistory) {
							just = sam.findJustByStudentNo(data[0]);
							justScore = just == null ? 0.0d : (just
									.getTotalScore() >= 95.0 ? 95.0D : just
									.getTotalScore());
						} else {
							scoreHist = new ScoreHist(data[0]);
							scoreHist.setSchoolYear(new Short(year));
							scoreHist.setSchoolTerm(sterm);
							scoreHist.setCscode("99999");
							scoreHistList = sm.findScoreHistBy(scoreHist);
							if (!scoreHistList.isEmpty()) {
								scoreHist = sm.findScoreHistBy(scoreHist)
										.get(0);
								justScore = scoreHist.getScore();
							}
						}

						studentScores[studentIndex][dtimesSize + 1] = justScore; // 暫存操性
						couduct = String.valueOf(Math.round(justScore)); // 操性
						data[23] = Float.valueOf(couduct) >= 60.0f ? couduct
								: couduct + "*";
						index = 2;
						selds = sm.findSeld(data[0], sterm);
						totalCredits = 0.0F;
						passCredits = 0.0F;
						totalScore = 0.0D;
						extraClassInfo1 = new StringBuffer(165); // 如果有發生太長時改為155
						extraClassInfo2 = new StringBuffer(165); // 以下165也要改成155
						for (Seld seld : selds) {
							try {
								extraDtime = (Dtime) (cm
										.getDtimeForEditDtime(seld
												.getDtimeOid().toString())
										.get(0));
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}

							// 論文,碩士論文一,二要排除計算
							if (ArrayUtils.contains(EXCLUDED_CSCODE,
									seld.getCscode()))
								continue;

							score = isFinalResult ? seld.getScore() : seld
									.getScore2();
							score = score == null ? 0.0D : Double.valueOf(Math
									.round(score)); // 體育或英文會有小數點
							if (!ArrayUtils.contains(dtimeOids,
									seld.getDtimeOid())) {

								if (extraDtime.getCscode() == null
										|| (!isFinalResult && ArrayUtils
												.contains(midExcludes,
														extraDtime.getCscode())))
									continue; // 排除期中不要的科目(有些會沒有cscode???如:164211)

								if (!isFinalResult) {
									// 也要將跨選或隨班的不列入計算
									if (isDtimeExcluded(sm, extraDtime))
										continue;
								}
								// extraDtime =
								// cm.getDtimeBy(seld.getDtimeOid());
								// csno = cm.getCourseInfoByCscode(extraDtime
								// .getCscode());
								if (ArrayUtils.contains(EXCLUDED_CSCODE,
										extraDtime.getCscode()))
									continue;

								credit = extraDtime.getCredit();
								totalCredits += credit;
								totalScore += credit * score;
								if (score >= passScore)
									passCredits += credit;

								isLiteracyClass = Toolket
										.isLiteracyClass(extraDtime
												.getDepartClass());
								extraClassStr = (!isLiteracyClass ? extraDtime
										.getDepartClass2() : "通識課程")
										+ " "
										+ extraDtime.getChiName2()
										+ "("
										+ credit
										+ "):"
										+ (score < passScore ? score + "*"
												: score) + "   ";
								if ((extraClassStr.length() + extraClassInfo1
										.toString().length()) <= 165)
									extraClassInfo1.append(extraClassStr);
								else
									extraClassInfo2.append(extraClassStr);

								continue;
							}

							// 應該用Dtime內的cscode,而不是Seld的cscode
							cscodeIndex = Arrays.binarySearch(cscodes,
									(extraDtime.getCscode() == null ? ""
											: extraDtime.getCscode())); // 求出科目在Page
							// Header中位置
							if (cscodeIndex <= StringUtils.INDEX_NOT_FOUND) {
								// 防止Seld內沒有Cscode
								tempDtime = cm.findDtimeBy(seld.getDtimeOid());
								cscodeIndex = Arrays.binarySearch(cscodes,
										tempDtime.getCscode());
							}

							if (cscodeIndex >= 0) {
								// credit = seld.getCredit() == null ?
								// dtimeCredits[cscodeIndex]
								// : seld.getCredit();
								credit = dtimeCredits[cscodeIndex];
								totalScore += credit * score;
								totalCredits += credit;
								if (score < passScore) {
									scoreString = score.toString() + "*";
								} else {
									scoreString = score.toString();
									passCredits += credit;
								}
								data[cscodeIndex + index] = scoreString;
								studentScores[studentIndex][cscodeIndex] = Double
										.parseDouble(scoreString.replaceAll(
												"\\*", ""));
							} else
								data[cscodeIndex + index] = "//";
						}

						// data[18] = totalScore.toString();
						data[19] = totalCredits.toString();
						data[20] = passCredits.toString();
						studentScoreAvg = Double.isNaN(totalScore
								/ totalCredits) ? "0.0" : Toolket
								.getRoundFormat(df,
										(totalScore / totalCredits), 0.001D);
						data[21] = Float.parseFloat(studentScoreAvg) < passScore ? studentScoreAvg
								+ "*"
								: studentScoreAvg;
						// data[19] = String.valueOf(totalScore / totalCredits);
						tempAvg = Double.isNaN(totalScore / totalCredits) ? 0.0D
								: totalScore / totalCredits;
						studentScores[studentIndex][dtimesSize] = tempAvg; // 暫存平均
						avgs = (double[]) ArrayUtils.add(avgs, tempAvg);
						// justs = (double[]) ArrayUtils.add(justs, Double
						// .parseDouble(((String) data[23]).replaceAll(
						// "\\*", "")));
						// 延修生>=10學分要計算狀態,一般生>=9要計算狀態
						data[24] = totalCredits < (Toolket
								.isDelayClass(classNo) ? 10.0 : 9.0F) ? ""
								: ((passCredits / totalCredits) <= 0.5D ? ((passCredits / totalCredits) <= lowOver23 ? "2/3"
										: "1/2")
										: "");
						if ("1/2".equals(data[24]))
							notPass21++;
						else if ("2/3".equals(data[24]))
							notPass32++;

						if (StringUtils.isNotBlank(extraClassInfo1.toString()))
							data[25] = extraClassInfo1.toString();
						if (StringUtils.isNotBlank(extraClassInfo2.toString()))
							data[26] = extraClassInfo2.toString();
						studentIndex++;
						printData.add(data);
					}

					if (isFinalResult)
						summary = new ClassScoreSummary(year, sterm, classNo,
								Examine.FINAL);
					else
						summary = new ClassScoreSummary(year, sterm, classNo,
								Examine.MID);

					// 計算名次
					int[] rankData = sortAvgAndJust(studentScores);
					for (int i = 0; i < studentCounts; i++) {
						obj = (Object[]) printData.get(i);
						obj[22] = String.valueOf(rankData[i]);

						if ("1/2".equals((String) obj[24]))
							scoreStatus = ScoreStatus.STATUS_2;
						else if ("2/3".equals((String) obj[24]))
							scoreStatus = ScoreStatus.STATUS_3;
						else
							scoreStatus = ScoreStatus.OK;

						stdScore = new StdScore((String) obj[0],
								Float.parseFloat((String) obj[19]),
								Float.parseFloat((String) obj[20]),
								Float.parseFloat(((String) obj[21]).replaceAll(
										"\\*", "")),
								Double.parseDouble(((String) obj[23])
										.replaceAll("\\*", "")), scoreStatus,
								rankData[i]);
						summary.getStdScoreSet().add(stdScore);
					}

					// 算各科目及格與不及格人數
					passScores = new int[dtimesSize];
					notPassScores = new int[dtimesSize];
					rankCounts = new int[dtimesSize][5];
					totalScores = new float[dtimesSize];
					s = 0.0D;
					for (int i = 0; i < dtimesSize; i++) {
						for (int j = 0; j < studentCounts; j++) {
							s = studentScores[j][i];
							if (s == null)
								continue;

							if (s >= passScore)
								passScores[i]++;
							else
								notPassScores[i]++;
							totalScores[i] += studentScores[j][i];

							if (s < 20)
								rankCounts[i][0]++;
							else if (s >= 20 && s <= 39)
								rankCounts[i][1]++;
							else if (s >= 40 && s <= 59)
								rankCounts[i][2]++;
							else if (s >= 60 && s <= 79)
								rankCounts[i][3]++;
							else if (s >= 80)
								rankCounts[i][4]++;
						}
					}

					// 算各科平均與全班總平均
					float classTotalScores = 0.0F;
					int excludedCounts = 0; // 全部為0的科目不以計算
					for (int i = 0; i < dtimesSize; i++) {
						parameters.put(passCountsIndex[i],
								String.valueOf(passScores[i]));
						parameters.put(notPassCountsIndex[i],
								String.valueOf(notPassScores[i]));
						// s1 = "0.0";
						if (Double.isNaN(totalScores[i]
								/ (passScores[i] + notPassScores[i]))) {
							s1 = "0.0";
							excludedCounts++;
						} else
							s1 = Toolket
									.getRoundFormat(
											df,
											(totalScores[i] / (passScores[i] + notPassScores[i])),
											0.001D);
						classTotalScores += Float.parseFloat(s1);
						parameters.put(courseScoreAvgIndex[i], Float
								.parseFloat(s1) < passScore ? s1 + "*" : s1);

						scoreStatistic = new ScoreStatistic();
						scoreStatistic.setCscode(cscodes[i]);
						scoreStatistic.setAverage(Float.parseFloat(s1));
						scoreStatistic.setPass(passScores[i]);
						scoreStatistic.setFail(notPassScores[i]);
						scoreStatistic.setRange1(rankCounts[i][0]);
						scoreStatistic.setRange2(rankCounts[i][1]);
						scoreStatistic.setRange3(rankCounts[i][2]);
						scoreStatistic.setRange4(rankCounts[i][3]);
						scoreStatistic.setRange5(rankCounts[i][4]);
						summary.getScoreStatisticSet().add(scoreStatistic);
					}

					classAvgScore = classTotalScores
							/ (float) (dtimesSize - excludedCounts);
					parameters.put("Z",
							Toolket.getRoundFormat(df, classAvgScore, 0.001D));

					summary.setAverage(Float.isNaN(classAvgScore) ? 0.0F
							: classAvgScore);
					summary.setSummary1(notPass21);
					summary.setSummary2(notPass32);
					summary.setLastModified(new Date());
					sm.txSaveClassScoreSummary(summary);

					jasperPrint = JasperFillManager.fillReport(jasperReport,
							parameters, new HibernateQueryResultDataSource(
									printData, fields));
					jasperPrints.add(jasperPrint);

					students = null;
					// dtimes = null;
					dtimeAndCsno = null;
					printData = null;
					scoreHistList = null;
					selds = null;
					scoreStatus = null;
					scoreStatistic = null;
					summary = null;
					target = null;
					stdScore = null;

					parameters = null;

					jasperPrint = null;
					extraDtime = null;
					dtime = null;
					csno = null;
					just = null;
					scoreHist = null;

					extraClassInfo1 = null;
					extraClassInfo2 = null;

					// justScore = 0.0D;
					// credit = 0.0F;
					// classAvgScore = 0.0F;
					// index = 0;
					// studentIndex = 0;
					// dtimesSize = 0;
					// studentCounts = 0;
					// cscodeIndex = 0;
					// notPass21 = 0;
					// notPass32 = 0;
					// isLiteracyClass = false;

					data = null;
					cscodes = null;
					dtimeOids = null;
					dtimeCredits = null;
					avgs = null;
					obj = null;

					studentScores = null;
				}
			}

			if (!jasperPrints.isEmpty()) {
				if (isPdf) {
					JRPdfExporter exporter = new JRPdfExporter();
					exporter.setParameter(
							JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
					exporter.setParameter(
							JRExporterParameter.OUTPUT_FILE_NAME,
							context.getRealPath("/WEB-INF/reports/MidtermFinalResult.pdf"));
					exporter.exportReport();
					File output = new File(
							context.getRealPath("/WEB-INF/reports/MidtermFinalResult.pdf"));
					JasperReportUtils.printXlsToFrontEnd(response, output);
					jasperPrints = null;
				} else {
					JExcelApiExporter exporterXLS = new JExcelApiExporter();
					exporterXLS.setParameter(
							JRXlsExporterParameter.JASPER_PRINT_LIST,
							jasperPrints);
					exporterXLS.setParameter(
							JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
							Boolean.TRUE);
					exporterXLS.setParameter(
							JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
							Boolean.FALSE);
					exporterXLS
							.setParameter(
									JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
									Boolean.TRUE);
					exporterXLS.setParameter(
							JRXlsExporterParameter.OUTPUT_STREAM,
							response.getOutputStream());
					response.setHeader("Content-Disposition",
							"inline;filename=" + "FinalScoreNotice.xls");
					response.setContentType("application/vnd.ms-excel");
					exporterXLS.exportReport();
					jasperPrints = null;
					// exporterXLS
					// .setParameter(
					// JRXlsExporterParameter.OUTPUT_FILE_NAME,
					// context
					// .getRealPath("/WEB-INF/reports/FinalScoreNotice.xls"));
					// File output = new File(context
					// .getRealPath("/WEB-INF/reports/FinalScoreNotice.xls"));
					// JasperReportUtils.printXlsToFrontEnd(response, output);
				}
			} else {
				parameters = new HashMap<String, String>();
				File image = new File(
						context.getRealPath("/pages/reports/2002chitS.jpg"));
				parameters.put("IMAGE", image.getAbsolutePath());
				byte[] bytes = JasperRunManager.runReportToPdf(
						JasperReportUtils.getNoResultReport(context),
						parameters, new JREmptyDataSource());
				JasperReportUtils.printPdfToFrontEnd(response, bytes);
			}

		} else {
			Map<String, String> parameters = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/reports/2002chitS.jpg"));
			parameters.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), parameters,
					new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}
	}

	@SuppressWarnings("unchecked")
	protected void printMidFinalScoreFor11G111(ServletContext context,
			HttpServletRequest request, HttpServletResponse response,
			String year, String term, boolean isFinalResult) throws Exception {

		String departClass = "11G111";
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);

		UserCredential credential = getUserCredential(request.getSession(false));

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ credential.getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		ClassScoreSummary target = new ClassScoreSummary(year, term,
				departClass, (isFinalResult ? Examine.FINAL : Examine.MID));
		sm.deleteClassScoreSummary(sm
				.findClassScoreSummaryByLikeExpression(target));

		ClassScoreSummary summary = null;
		if (isFinalResult)
			summary = new ClassScoreSummary(year, term, departClass,
					Examine.FINAL);
		else
			summary = new ClassScoreSummary(year, term, departClass,
					Examine.MID);

		File templateXLS = new File(
				context.getRealPath("/WEB-INF/reports/MidtermFinalResultLarge1.xls"));
		DecimalFormat df = new DecimalFormat(",##0.0");
		DecimalFormat df1 = new DecimalFormat(",##0.000");
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("標楷體");

		List<Student> students = mm.findStudentsByClassNo(departClass);
		List<Dtime> dtimes = excludedSomething(cm.findDtimeBy(new Dtime(
				departClass, term), "cscode")); // 以cscode排序
		String[] cscodes = new String[0];
		Integer[] dtimeOids = new Integer[0];
		Csno csno = null;
		Seld seld = null;
		int index = 2;
		setCellValue(
				sheet,
				0,
				0,
				year
						+ "學年度"
						+ term
						+ "學期"
						+ (isFinalResult ? Examine.FINAL.toString()
								: Examine.MID.toString())
						+ "成績總表 ("
						+ StringUtils.substring(
								Toolket.getClassFullName(departClass), 2) + ")");
		setCellValue(
				sheet,
				0,
				27,
				"日 期 ："
						+ new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN)
								.format(new Date()));
		for (Dtime d : dtimes) {
			cscodes = (String[]) ArrayUtils.add(cscodes, d.getCscode());
			dtimeOids = (Integer[]) ArrayUtils.add(dtimeOids, d.getOid());
			csno = cm.findCourseInfoByCscode(d.getCscode());
			setCellValue(sheet, 1, index, csno.getChiName());
			setCellValue(
					sheet,
					2,
					index++,
					StringUtils.substring(Toolket.getCourseOpt(d.getOpt()), 0,
							1) + "  " + d.getCredit());
		}

		int rowIndex = 4;
		index = 0;
		String studentNo = "", info = "";
		Float totalCredits = 0.0F, passCredits = 0.0F;
		Double totalScores = 0.0D, scoreValue = 0.0D;
		String[] avgs = new String[0];
		StdScore stdScore = null;
		Just just = null;
		List<Map> seldInfo = null;

		float passScore = Toolket.getPassScoreByDepartClass(departClass);
		for (Student student : students) {
			studentNo = student.getStudentNo();
			setCellValue(sheet, rowIndex, index++, studentNo);
			setCellValue(sheet, rowIndex, index++, student.getStudentName());
			for (Dtime d : dtimes) {
				seld = sm.findSeld(d.getOid(), studentNo);
				scoreValue = seld == null ? null : (isFinalResult ? seld
						.getScore() : seld.getScore2());
				String score = seld == null ? "/" : (scoreValue == null ? "0.0"
						: scoreValue.toString());
				setCellValue(sheet, rowIndex, index++, score);
				if (seld != null)
					totalCredits += d.getCredit();
				if (seld != null && scoreValue != null
						&& scoreValue >= passScore) {
					passCredits += d.getCredit();
					totalScores += scoreValue * d.getCredit();
				}
			}

			just = sam.findJustByStudentNo(studentNo);
			index = 31;
			setCellValue(sheet, rowIndex, index++, totalCredits.toString());
			setCellValue(sheet, rowIndex, index++, passCredits.toString());
			String avg = df.format((totalScores / totalCredits) + 0.001D);
			String avgForRank = df1
					.format((totalScores / totalCredits) + 0.00001D);
			setCellValue(sheet, rowIndex, index++, avg);
			avgs = (String[]) ArrayUtils.add(avgs, avgForRank);
			setCellValue(sheet, rowIndex, index++, totalScores.toString());
			setCellValue(sheet, rowIndex, index++, StringUtils.substringBefore(
					(just.getTotalScore() >= 95.0D ? "95.0" : just
							.getTotalScore().toString()), "."));
			// 只是用來比較名次用
			// setCellValue(sheet, rowIndex, index++, avgForRank);
			setCellValue(sheet, rowIndex, index++, "");
			seldInfo = processExcludedThesis(cm.findStudentSeldCourse1(
					studentNo, term));

			int k = 0;
			Integer dtimeOidBySeld = null;
			Dtime d = null;
			for (Map m : seldInfo) {
				dtimeOidBySeld = (Integer) m.get("oid");
				// 處理非本班之加選科目
				if (!ArrayUtils.contains(dtimeOids, dtimeOidBySeld)) {
					rowIndex++;
					if (k == 0) { // 避免多次合併
						sheet.addMergedRegion(new CellRangeAddress(rowIndex,
								rowIndex, 0, 31));
						k++;
					}
					seld = sm.findSeld(dtimeOidBySeld, studentNo);
					scoreValue = seld == null ? null : (isFinalResult ? seld
							.getScore() : seld.getScore2());
					String score = seld == null ? "/"
							: (scoreValue == null ? "0.0" : scoreValue
									.toString());
					d = cm.findDtimeBy(dtimeOidBySeld);
					if (seld != null)
						totalCredits += d.getCredit();
					if (seld != null && scoreValue != null
							&& scoreValue >= passScore) {
						passCredits += d.getCredit();
						totalScores += scoreValue * d.getCredit();
					}

					// 處理加選科目資料
					info = "   "
							+ StringUtils.substring(Toolket.getClassFullName(d
									.getDepartClass()), 2) + " ";
					info += cm.findCourseInfoByCscode(d.getCscode())
							.getChiName() + "(" + d.getCredit() + ")： " + score;
					setCellValue(workbook, sheet, rowIndex, 0, info,
							fontSize10, HSSFCellStyle.ALIGN_LEFT);

					// 處理實得學分等相關欄位資料(處理一次就更新一次)
					setCellValue(sheet, rowIndex - 1, index - 6,
							totalCredits.toString());
					setCellValue(sheet, rowIndex - 1, index - 5,
							passCredits.toString());
					avg = df.format((totalScores / totalCredits) + 0.001D);
					avgForRank = df1
							.format((totalScores / totalCredits) + 0.00001D);
					setCellValue(sheet, rowIndex - 1, index - 4, avg);
					// avgs = Arrays.copyOfRange(avgs, 0, avgs.length - 1);
					avgs = (String[]) ArrayUtils.subarray(avgs, 0,
							avgs.length - 1);
					avgs = (String[]) ArrayUtils.add(avgs, avgForRank);
					setCellValue(sheet, rowIndex - 1, index - 3,
							totalScores.toString());
					// setCellValue(sheet, rowIndex - 1, index - 1,
					// avgForRank);
					setCellValue(sheet, rowIndex - 1, index - 1, "");
				}
			}

			rowIndex++;
			index = 0;
			totalCredits = 0.0F;
			passCredits = 0.0F;
			totalScores = 0.0D;
		}

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("標楷體");

		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 36));

		setCellValue(workbook, sheet, rowIndex++, index,
				StringUtils.repeat("-", 800), HSSFCellStyle.ALIGN_LEFT);
		// 以下code別亂移動
		int summaryRow = rowIndex, summaryRowIndex = rowIndex, counts = 0;
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));
		Toolket.setCellValue(workbook, sheet, rowIndex++, index, "及 格 人 數 ：",
				fontSize12, HSSFCellStyle.ALIGN_RIGHT, false, null);
		// setCellValue(workbook, sheet, rowIndex++, index, "及 格 人 數 ：",
		// HSSFCellStyle.ALIGN_RIGHT);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));
		Toolket.setCellValue(workbook, sheet, rowIndex++, index, "不 及 格 人 數 ：",
				fontSize12, HSSFCellStyle.ALIGN_RIGHT, false, null);
		// setCellValue(workbook, sheet, rowIndex++, index, "不 及 格 人 數 ：",
		// HSSFCellStyle.ALIGN_RIGHT);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));
		Toolket.setCellValue(workbook, sheet, rowIndex++, index, "平 均 分 數 ：",
				fontSize12, HSSFCellStyle.ALIGN_RIGHT, false, null);
		// setCellValue(workbook, sheet, rowIndex++, index, "平 均 分 數 ：",
		// HSSFCellStyle.ALIGN_RIGHT);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));
		Toolket.setCellValue(workbook, sheet, rowIndex++, index, "全 班 總 平 均 ：",
				fontSize12, HSSFCellStyle.ALIGN_RIGHT, false, null);
		// setCellValue(workbook, sheet, rowIndex++, index, "全 班 總 平 均 ：",
		// HSSFCellStyle.ALIGN_RIGHT);

		// 計算各科目及格/不及格人數等
		Float totalAvgs = 0.0F;
		ScoreStatistic statistic = null;
		for (int i = 0; i < dtimes.size(); i++) {
			int pass = 0, fail = 0;
			Float totalScoresForAvg = 0.0F;
			statistic = new ScoreStatistic();
			int studentCounts = students.size();
			for (int j = 0; j < studentCounts; j++) {
				String scoreInfo = "";
				try {
					scoreInfo = getCellValue(sheet, j + 4, i + 2);
				} catch (Exception e) {
					// 讀到加選資料則要+1
					studentCounts++;
					continue;
				}

				try {
					// 讀到"/"則跳過,但不累加studentCounts
					// 因為只是沒加選,並非是加選資料
					if ("/".equals(scoreInfo))
						continue;

					// 讀到非數字則跳過,但累加studentCounts
					// 因為是加選,studentCounts需累加才會對
					Float score = Float.parseFloat(scoreInfo);
					totalScoresForAvg += score;
					if (score < passScore)
						fail++;
					else
						pass++;
				} catch (Exception e) {
					// 讀到加選資料則要+1
					studentCounts++;
					continue;
				}
			}
			setCellValue(sheet, summaryRow++, i + 2, String.valueOf(pass));
			setCellValue(sheet, summaryRow++, i + 2, String.valueOf(fail));
			if (pass == 0 || Float.isNaN((totalScoresForAvg / pass))) {
				setCellValue(sheet, summaryRow++, i + 2, "");
				statistic.setAverage(0.0F);
			} else {
				String avg = df.format((totalScoresForAvg / pass) + 0.001D);
				totalAvgs += Float.parseFloat(avg);
				setCellValue(sheet, summaryRow++, i + 2, avg);
				counts++;
				statistic.setAverage(Float.isNaN(Float.parseFloat(avg)) ? 0.0F
						: Float.parseFloat(avg));
			}
			statistic.setCscode(cscodes[i]);
			statistic.setPass(pass);
			statistic.setFail(fail);
			// statistic.setRange1(range1);
			// statistic.setRange2(range1);
			// statistic.setRange3(range1);
			// statistic.setRange4(range1);
			// statistic.setRange5(range1);
			summary.getScoreStatisticSet().add(statistic);
			summaryRow = summaryRowIndex;
		}

		Float classAvg = totalAvgs / counts;
		setCellValue(sheet, summaryRow + 3, 2, Float.isNaN(classAvg) ? "0.0"
				: df.format(classAvg + 0.001D));

		String[] temp = avgs.clone();
		Arrays.sort(avgs);
		ArrayUtils.reverse(avgs);
		rowIndex = 4;
		// Integer[] rankCheck = { 1, 2, 3 };
		// TopStudent top = null;
		for (int i = 0; i < avgs.length; i++) {
			int rank = ArrayUtils.indexOf(avgs, temp[i]) + 1;
			// if (ArrayUtils.contains(rankCheck, rank)) {
			// System.out.println(rank);
			// top = new TopStudent();
			// }
			try {
				// getCellValue(sheet, rowIndex, 1);
				// 如果是空白,可能是加選資料,所以rowIndex要+1
				if ("".equals(getCellValue(sheet, rowIndex, 33)))
					rowIndex++;

				setCellValue(sheet, rowIndex++, 34, String.valueOf(rank));
			} catch (Exception e) {
				setCellValue(sheet, ++rowIndex, 34, String.valueOf(rank));
				++rowIndex;
				continue;
			}
		}

		int studentCounts = students.size();
		for (int i = 0; i < studentCounts; i++) {
			try {
				studentNo = getCellValue(sheet, i + 4, 0);
				totalCredits = Float.parseFloat(getCellValue(sheet, i + 4, 31)); // 26
				passCredits = Float.parseFloat(getCellValue(sheet, i + 4, 32)); // 27
				Float avg = Float.parseFloat(getCellValue(sheet, i + 4, 33)); // 28
				Integer rank = Integer.valueOf(getCellValue(sheet, i + 4, 34)); // 29
				Double justScore = Double
						.valueOf(getCellValue(sheet, i + 4, 35));
				justScore = justScore == null ? 0.0D : justScore;
				stdScore = new StdScore(studentNo, totalCredits, passCredits,
						avg, justScore, ScoreStatus.OK, rank);
				summary.getStdScoreSet().add(stdScore);
				// if (ArrayUtils.contains(rankCheck, rank)) {
				// top = new TopStudent(studentNo, avg, rank - 1);
				// summary.getTopStudentSet().add(top);
				// }
			} catch (Exception e) {
				studentCounts++;
			}
		}

		summary.setAverage(Float.isNaN(classAvg) ? 0.0F : classAvg);
		summary.setSummary1(0);
		summary.setSummary2(0);
		summary.setLastModified(new Date());
		sm.txSaveClassScoreSummary(summary);

		File output = new File(tempDir, "MidtermFinalResult.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	@SuppressWarnings("unchecked")
	protected JasperPrint printMidFinalScoreForDelayClass(
			ServletContext context, HttpServletRequest request,
			HttpServletResponse response, String year, String term,
			String classNo, boolean isFinalResult) throws Exception {

		// 132041進專選讀,172041學院選讀會有學生
		if (("132041".equals(classNo) || "172041".equals(classNo))
				|| !Toolket.isLiteracyClass(classNo)) {

			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
			ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
			StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);

			List<Student> students = mm.findStudentsByClassNo(classNo);
			if (students.isEmpty())
				return null;

			else {

				Dtime dtime = null;
				Seld seld = null;
				Just just = null;
				ScoreHist scoreHist = null;
				StdScore stdScore = null;

				List<Map> seldInfo = null;
				List<ScoreHist> scoreHistList = null;

				StringBuffer buffer = null;
				StringBuffer extraCourseInfo = null, extraCourseInfo1 = null, extraCourseInfo2 = null, extraCourseInfo3 = null;

				Integer dtimeOidBySeld = null;

				int i = 0;
				int fieldIndex = 0, notPass = 0, notPass1 = 0;
				int studentCount = students.size();

				float totalScore = 0.0F;
				float passScore = Toolket.getPassScoreByDepartClass(classNo);
				float extraCreditTotal = 0.0f;
				float passExtraCreditTotal = 0.0f;
				float extraScoreTotal = 0.0f;
				float credit = 0.0f;

				double justString = 0.0D;

				String[] cscodeArr = null;
				Integer[] dtimeOidArr = null;

				File reportFile = new File(
						context.getRealPath("/WEB-INF/reports/MidtermFinalResultForDelay.jasper"));
				// 需要時再編譯即可
				if (!reportFile.exists()) {
					JasperReportUtils.initJasperReportsClasspath(request);
					JasperReportUtils
							.compileJasperReports(context
									.getRealPath("/WEB-INF/reports/MidtermFinalResultForDelay.jrxml"));
					reportFile = new File(
							context.getRealPath("/WEB-INF/reports/MidtermFinalResultForDelay.jasper"));
					if (!reportFile.exists())
						throw new JRRuntimeException(
								"查無\"MidtermFinalResultForDelay.jasper\"檔案，請電洽電算中心，謝謝！！");
				}

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());

				// JasperReports套表定義的Parameters
				String[] fields = { "studentNoF", "nameF", "totalCreditF",
						"passCreditF", "averageF", "rankF", "behaviorF",
						"statusF", "extraCourseInfo1F", "extraCourseInfo2F",
						"extraCourseInfo3F", "extraCourseInfo4F" };

				// 要顯示的資料
				List<Object> printData = new ArrayList<Object>();
				String[][] data = new String[studentCount][fields.length + 2];
				DecimalFormat df = new DecimalFormat(",##0.0");

				Map<String, String> parameters = new HashMap<String, String>();
				parameters.put("PASSSCORE", String.valueOf(passScore));
				parameters.put("A", year);
				parameters.put("B", term);
				parameters.put("C", isFinalResult ? Examine.FINAL.toString()
						: Examine.MID.toString());
				parameters.put("D", Toolket.getClassFullName(classNo));

				ClassScoreSummary target = new ClassScoreSummary(year, term,
						classNo, (isFinalResult ? Examine.FINAL : Examine.MID));
				sm.deleteClassScoreSummary(sm
						.findClassScoreSummaryByLikeExpression(target));

				ClassScoreSummary summary = new ClassScoreSummary(year, term,
						classNo);
				if (isFinalResult)
					summary.setMidFinal(Examine.FINAL);
				else
					summary.setMidFinal(Examine.MID);

				for (Student student : students) {
					seldInfo = cm.findStudentSeldCourse1(
							student.getStudentNo(), term);
					if (!isFinalResult) // 期中要排除不納入計算的科目
						seldInfo = processExcludedCourse(sm, seldInfo);

					fieldIndex = 8;
					cscodeArr = new String[0];
					dtimeOidArr = new Integer[0];
					extraCreditTotal = 0.0f;
					passExtraCreditTotal = 0.0f;
					extraScoreTotal = 0.0f;
					extraCourseInfo = new StringBuffer();
					extraCourseInfo1 = new StringBuffer();
					extraCourseInfo2 = new StringBuffer();
					extraCourseInfo3 = new StringBuffer();

					data[i][0] = student.getStudentNo();
					data[i][1] = student.getStudentName();
					just = sam.findJustByStudentNo(data[i][0]);
					justString = 0.0D;
					if (just == null || just.getTotalScore() == 0.0D) {
						scoreHist = new ScoreHist(student.getStudentNo());
						scoreHist.setSchoolYear(new Short(year));
						scoreHist.setSchoolTerm(term);
						scoreHist.setCscode("99999");
						scoreHistList = sm.findScoreHistBy(scoreHist);
						if (!scoreHistList.isEmpty()) {
							scoreHist = sm.findScoreHistBy(scoreHist).get(0);
							justString = scoreHist.getScore();
						}
					} else
						justString = just.getTotalScore() >= 95.0d ? 95.0d
								: just.getTotalScore();

					String couduct = String.valueOf(Math.round(justString)); // 操性
					data[i][6] = Float.valueOf(couduct) >= 60.0f ? couduct
							: couduct + "*";
					for (Map seldData : seldInfo) {
						dtimeOidBySeld = (Integer) seldData.get("oid");
						seld = sm.findSeld(dtimeOidBySeld, data[i][0]);
						dtime = cm.findDtimeBy(seld.getDtimeOid());

						// 多判斷Dtime Oid是因為有學生同時修相同Cscode但不同DepartClass
						if (!ArrayUtils.contains(cscodeArr, dtime.getCscode())
								|| !ArrayUtils.contains(dtimeOidArr,
										dtime.getOid())) {

							credit = dtime.getCredit();
							extraCreditTotal += credit;
							cscodeArr = (String[]) ArrayUtils.add(cscodeArr,
									dtime.getCscode());
							dtimeOidArr = (Integer[]) ArrayUtils.add(
									dtimeOidArr, dtime.getOid());

							boolean bFlag1 = Toolket.isLiteracyClass(dtime
									.getDepartClass());

							buffer = new StringBuffer();
							buffer.append(
									!bFlag1 ? (String) seldData
											.get("ClassName") : "通識課程")
									.append(" ")
									.append((String) seldData.get("chi_name"))
									.append("(").append(credit).append(")")
									.append("： ");
							if (isFinalResult) {
								Double score = seld.getScore() == null ? 0.0D
										: seld.getScore();
								score = score == null ? 0.0D : Double
										.valueOf(Math.round(score)); // 體育或英文會有小數點
								if (score >= passScore) {
									buffer.append(score);
									passExtraCreditTotal += credit;
								} else {
									buffer.append(score).append("*");
								}
								extraScoreTotal += score * credit;
							} else {
								Double score2 = seld.getScore2();
								score2 = score2 == null ? 0.0D : Double
										.valueOf(Math.round(score2)); // 體育或英文會有小數點
								boolean bFlag2 = score2 != null;
								if (bFlag2 && score2 >= passScore) {
									buffer.append(score2);
									passExtraCreditTotal += credit;
								} else {
									if (!bFlag2) {
										buffer.append("*");
										score2 = 0.0d;
									} else
										buffer.append(score2).append("*");
								}
								extraScoreTotal += score2 * credit;
							}

							if (extraCourseInfo.length() + buffer.length() <= 100)
								extraCourseInfo.append(buffer).append("   ");
							else if (extraCourseInfo1.length()
									+ buffer.length() <= 100)
								extraCourseInfo1.append(buffer).append("   ");
							else if (extraCourseInfo2.length()
									+ buffer.length() <= 100)
								extraCourseInfo2.append(buffer).append("   ");
							else
								extraCourseInfo3.append(buffer).append("   ");
						}
					}
					data[i][fieldIndex++] = extraCourseInfo.length() == 0 ? null
							: extraCourseInfo.toString();
					data[i][fieldIndex++] = extraCourseInfo1.length() == 0 ? null
							: extraCourseInfo1.toString();
					data[i][fieldIndex++] = extraCourseInfo2.length() == 0 ? null
							: extraCourseInfo2.toString();
					data[i][fieldIndex] = extraCourseInfo3.length() == 0 ? null
							: extraCourseInfo3.toString();
					data[i][2] = String.valueOf(extraCreditTotal);
					data[i][3] = String.valueOf(passExtraCreditTotal);
					Float extraScoreAvg = extraScoreTotal / extraCreditTotal;
					data[i][4] = Float.isNaN(extraScoreAvg) ? "0.0" : df
							.format((extraScoreAvg));
					ScoreStatus scoreStatus = ScoreStatus.OK;
					if (extraCreditTotal >= 10.0F) {
						if ((passExtraCreditTotal / extraCreditTotal) <= (1.0F / 3.0F)) {
							data[i][7] = "2/3";
							scoreStatus = ScoreStatus.STATUS_3;
							summary.getFailStudents2Set()
									.add(new FailStudents2(data[i][0],
											extraCreditTotal,
											passExtraCreditTotal, extraScoreAvg));
							notPass1++;
						} else if ((passExtraCreditTotal / extraCreditTotal) < 0.5F) {
							data[i][7] = "1/2";
							scoreStatus = ScoreStatus.STATUS_2;
							summary.getFailStudents1Set()
									.add(new FailStudents1(data[i][0],
											extraCreditTotal,
											passExtraCreditTotal, extraScoreAvg));
							notPass++;
						}
					}

					stdScore = new StdScore(data[i][0],
							Float.valueOf(data[i][2]),
							Float.valueOf(data[i][3]),
							Float.valueOf(data[i][4]), Double.valueOf(String
									.valueOf(justString)), scoreStatus,
							Integer.parseInt("0"));
					summary.getStdScoreSet().add(stdScore);

					totalScore += Float.valueOf(data[i][4].trim());
					printData.add(data[i]);
					i++;
				}
				summary.setSummary1(notPass);
				summary.setSummary2(notPass1);

				Float avg = totalScore / studentCount;
				summary.setAverage(avg);

				summary.setLastModified(new Date());
				sm.txSaveClassScoreSummary(summary);
				parameters.put("Q", Toolket.getRoundFormat(df, avg, 0.001D));
				return JasperFillManager.fillReport(jasperReport, parameters,
						new HibernateQueryResultDataSource(printData, fields));
			}
		} else
			return null;
	}

	/**
	 * 列印/預覽期中期末全班成績單作業
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @param isPrint
	 *            列印與否
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected ActionMessages printMidtermFinalResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm, boolean setupPrinter,
			boolean isPrint) throws Exception {

		// AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		UserCredential credential = getUserCredential(request.getSession(false));
		DynaActionForm aForm = (DynaActionForm) form;
		String year = cm.getNowBy("School_year");
		String term = aForm.getString("sterm");
		aForm.set("choseType", "ALL"); // 必備條件
		boolean isFinalResult = "final".equalsIgnoreCase(request
				.getParameter("rt")); // 是否為期末?預設為期末成績
		boolean isPopularizeClass = false; // 頁面要加推廣選項
		if (isPopularizeClass)
			aForm.set("classInCharge2", "122A");

		ActionMessages messages = new ActionMessages();

		ServletContext context = request.getSession().getServletContext();
		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ credential.getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File[] garbag = tempDir.listFiles();
		if (garbag.length > 0) {
			for (File f : garbag)
				f.delete();
		}

		String departClass = processClassInfo(aForm);
		if ("11G111".equals(departClass)) {

			ClassScoreSummary summary = null;
			if (isFinalResult)
				summary = new ClassScoreSummary(year, term, departClass,
						Examine.FINAL);
			else
				summary = new ClassScoreSummary(year, term, departClass,
						Examine.MID);

			File templateXLS = new File(
					context.getRealPath("/WEB-INF/reports/MidtermFinalResultLarge.xls"));
			DecimalFormat df = new DecimalFormat(",##0.0");
			DecimalFormat df1 = new DecimalFormat(",##0.000");
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Student> students = mm.findStudentsByClassNo(departClass);
			List<Dtime> dtimes = excludedSomething(cm.findDtimeBy(new Dtime(
					departClass, sterm), "cscode")); // 以cscode排序
			String[] cscodes = new String[0];
			Integer[] dtimeOids = new Integer[0];
			Csno csno = null;
			Seld seld = null;
			int index = 2;
			setCellValue(
					sheet,
					0,
					0,
					year
							+ "學年度"
							+ term
							+ "學期"
							+ (isFinalResult ? Examine.FINAL.toString()
									: Examine.MID.toString())
							+ "成績總表 ("
							+ StringUtils.substring(
									Toolket.getClassFullName(departClass), 2)
							+ ")");
			setCellValue(
					sheet,
					0,
					27,
					"日 期 ："
							+ new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN)
									.format(new Date()));
			for (Dtime d : dtimes) {
				cscodes = (String[]) ArrayUtils.add(cscodes, d.getCscode());
				dtimeOids = (Integer[]) ArrayUtils.add(dtimeOids, d.getOid());
				csno = cm.findCourseInfoByCscode(d.getCscode());
				setCellValue(sheet, 1, index, csno.getChiName());
				setCellValue(
						sheet,
						2,
						index++,
						StringUtils.substring(Toolket.getCourseOpt(d.getOpt()),
								0, 1) + "  " + d.getCredit());
			}

			int rowIndex = 4;
			index = 0;
			String studentNo = "";
			Float totalCredits = 0.0F, passCredits = 0.0F;
			Double totalScores = 0.0D;
			String[] avgs = new String[0];
			StdScore stdScore = null;
			Just just = null;
			float passScore = Toolket.getPassScoreByDepartClass(departClass);
			for (Student student : students) {
				studentNo = student.getStudentNo();
				setCellValue(sheet, rowIndex, index++, studentNo);
				setCellValue(sheet, rowIndex, index++, student.getStudentName());
				for (Dtime d : dtimes) {
					seld = sm.findSeld(d.getOid(), studentNo);
					String score = seld == null ? "/"
							: (seld.getScore() == null ? "0.0" : seld
									.getScore().toString());
					setCellValue(sheet, rowIndex, index++, score);
					if (seld != null)
						totalCredits += d.getCredit();
					if (seld != null && seld.getScore() != null
							&& seld.getScore() >= passScore) {
						passCredits += d.getCredit();
						totalScores += seld.getScore() * d.getCredit();
					}
				}

				just = sam.findJustByStudentNo(studentNo);
				index = 26;
				setCellValue(sheet, rowIndex, index++, totalCredits.toString());
				setCellValue(sheet, rowIndex, index++, passCredits.toString());
				String avg = df.format((totalScores / totalCredits) + 0.001D);
				String avgForRank = df1
						.format((totalScores / totalCredits) + 0.00001D);
				setCellValue(sheet, rowIndex, index++, avg);
				avgs = (String[]) ArrayUtils.add(avgs, avgForRank);
				setCellValue(sheet, rowIndex, index++, totalScores.toString());
				setCellValue(sheet, rowIndex, index++,
						StringUtils.substringBefore(
								(just.getTotalScore() >= 95.0D ? "95.0" : just
										.getTotalScore().toString()), "."));
				// setCellValue(sheet, rowIndex, index++, avgForRank);
				setCellValue(sheet, rowIndex, index++, "");
				List<Map> seldInfo = processExcludedThesis(cm
						.findStudentSeldCourse1(studentNo, term));
				int k = 0;
				for (Map m : seldInfo) {
					Integer dtimeOidBySeld = (Integer) m.get("oid");
					if (!ArrayUtils.contains(dtimeOids, dtimeOidBySeld)) {
						rowIndex++;
						if (k == 0) { // 避免多次合併
							sheet.addMergedRegion(new CellRangeAddress(
									rowIndex, rowIndex, 0, 31));
							k++;
						}
						seld = sm.findSeld(dtimeOidBySeld, studentNo);
						String score = seld == null ? "/"
								: (seld.getScore() == null ? "0.0" : seld
										.getScore().toString());
						Dtime d = cm.findDtimeBy(dtimeOidBySeld);
						if (seld != null)
							totalCredits += d.getCredit();
						if (seld != null && seld.getScore() != null
								&& seld.getScore() >= passScore) {
							passCredits += d.getCredit();
							totalScores += seld.getScore() * d.getCredit();
						}
						String info = "       "
								+ StringUtils.substring(Toolket
										.getClassFullName(d.getDepartClass()),
										2) + " ";
						info += cm.findCourseInfoByCscode(d.getCscode())
								.getChiName()
								+ "("
								+ d.getCredit()
								+ ")： "
								+ score;
						setCellValue(workbook, sheet, rowIndex, 0, info,
								HSSFCellStyle.ALIGN_LEFT);
						setCellValue(sheet, rowIndex - 1, index - 6,
								totalCredits.toString());
						setCellValue(sheet, rowIndex - 1, index - 5,
								passCredits.toString());
						avg = df.format((totalScores / totalCredits) + 0.001D);
						avgForRank = df1
								.format((totalScores / totalCredits) + 0.00001D);
						setCellValue(sheet, rowIndex - 1, index - 4, avg);
						// avgs = Arrays.copyOfRange(avgs, 0, avgs.length - 1);
						avgs = (String[]) ArrayUtils.subarray(avgs, 0,
								avgs.length - 1);
						avgs = (String[]) ArrayUtils.add(avgs, avgForRank);
						setCellValue(sheet, rowIndex - 1, index - 3,
								totalScores.toString());
						// setCellValue(sheet, rowIndex - 1, index - 1,
						// avgForRank);
						setCellValue(sheet, rowIndex - 1, index - 1, "");
					}
				}

				rowIndex++;
				index = 0;
				totalCredits = 0.0F;
				passCredits = 0.0F;
				totalScores = 0.0D;
			}

			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0,
					31));

			setCellValue(workbook, sheet, rowIndex++, index,
					StringUtils.repeat("-", 543), HSSFCellStyle.ALIGN_LEFT);
			// 以下code別亂移動
			int summaryRow = rowIndex, summaryRowIndex = rowIndex, counts = 0;
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));
			setCellValue(workbook, sheet, rowIndex++, index, "及 格 人 數 ：",
					HSSFCellStyle.ALIGN_RIGHT);
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));
			setCellValue(workbook, sheet, rowIndex++, index, "不 及 格 人 數 ：",
					HSSFCellStyle.ALIGN_RIGHT);
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));
			setCellValue(workbook, sheet, rowIndex++, index, "平 均 分 數 ：",
					HSSFCellStyle.ALIGN_RIGHT);
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));
			setCellValue(workbook, sheet, rowIndex++, index, "全 班 總 平 均 ：",
					HSSFCellStyle.ALIGN_RIGHT);

			// 計算各科目及格/不及格人數等
			Float totalAvgs = 0.0F;
			ScoreStatistic statistic = null;
			for (int i = 0; i < dtimes.size(); i++) {
				int pass = 0, fail = 0;
				Float totalScoresForAvg = 0.0F;
				statistic = new ScoreStatistic();
				int studentCounts = students.size();
				for (int j = 0; j < studentCounts; j++) {
					String scoreInfo = "";
					try {
						scoreInfo = getCellValue(sheet, j + 4, i + 2);
					} catch (Exception e) {
						// 讀到加選資料則要+1
						studentCounts++;
						continue;
					}
					try {
						// 讀到非數字則跳過
						Float score = Float.parseFloat(scoreInfo);
						totalScoresForAvg += score;
						if (score < passScore)
							fail++;
						else
							pass++;
					} catch (Exception e) {
					}
				}
				setCellValue(sheet, summaryRow++, i + 2, String.valueOf(pass));
				setCellValue(sheet, summaryRow++, i + 2, String.valueOf(fail));
				if (Float.isNaN((totalScoresForAvg / pass)))
					setCellValue(sheet, summaryRow++, i + 2, "");
				else {
					String avg = df.format((totalScoresForAvg / pass) + 0.001D);
					totalAvgs += Float.parseFloat(avg);
					setCellValue(sheet, summaryRow++, i + 2, avg);
					counts++;
					statistic.setAverage(Float.parseFloat(avg));
				}
				statistic.setCscode(cscodes[i]);
				statistic.setPass(pass);
				statistic.setFail(fail);
				// statistic.setRange1(range1);
				// statistic.setRange2(range1);
				// statistic.setRange3(range1);
				// statistic.setRange4(range1);
				// statistic.setRange5(range1);
				summary.getScoreStatisticSet().add(statistic);
				summaryRow = summaryRowIndex;
			}

			Float classAvg = totalAvgs / counts;
			setCellValue(sheet, summaryRow + 3, 2, df.format(classAvg + 0.001D));

			String[] temp = avgs.clone();
			Arrays.sort(avgs);
			ArrayUtils.reverse(avgs);
			rowIndex = 4;
			Integer[] rankCheck = { 1, 2, 3 };
			TopStudent top = null;
			for (int i = 0; i < avgs.length; i++) {
				int rank = ArrayUtils.indexOf(avgs, temp[i]) + 1;
				// if (ArrayUtils.contains(rankCheck, rank)) {
				// System.out.println(rank);
				// top = new TopStudent();
				// }
				try {
					getCellValue(sheet, rowIndex, 1);
					setCellValue(sheet, rowIndex++, 29, String.valueOf(rank));
				} catch (Exception e) {
					setCellValue(sheet, ++rowIndex, 29, String.valueOf(rank));
					++rowIndex;
					continue;
				}
			}

			int studentCounts = students.size();
			for (int i = 0; i < studentCounts; i++) {
				try {
					studentNo = getCellValue(sheet, i + 4, 0);
					totalCredits = Float.parseFloat(getCellValue(sheet, i + 4,
							26));
					passCredits = Float.parseFloat(getCellValue(sheet, i + 4,
							27));
					Float avg = Float
							.parseFloat(getCellValue(sheet, i + 4, 28));
					Integer rank = Integer.valueOf(getCellValue(sheet, i + 4,
							29));
					Double justScore = Double.valueOf(getCellValue(sheet,
							i + 4, 30));
					stdScore = new StdScore(studentNo, totalCredits,
							passCredits, avg, justScore, rank);
					summary.getStdScoreSet().add(stdScore);
					if (ArrayUtils.contains(rankCheck, rank)) {
						top = new TopStudent(studentNo, avg, rank - 1);
						summary.getTopStudentSet().add(top);
					}
				} catch (Exception e) {
					studentCounts++;
				}
			}

			summary.setAverage(classAvg);
			summary.setSummary1(0);
			summary.setSummary2(0);
			summary.setLastModified(new Date());
			sm.txSaveClassScoreSummary(summary);

			File output = new File(tempDir, "MidtermFinalResult.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
			return null;
		}

		// 所有查詢到的選課資料
		List<Map> allDtimeList = getDtimeListByParams(request, aForm, null);
		if (allDtimeList.isEmpty() || isPopularizeClass) {

			String classNo = (String) aForm.get("classInCharge2");
			// 132041進專選讀會有學生
			if ("132041".equals(classNo) || !Toolket.isLiteracyClass(classNo)) {

				List<Student> students = mm.findStudentsByClassNo(classNo);
				if (!students.isEmpty()) {

					File reportFile = new File(
							context.getRealPath("/WEB-INF/reports/MidtermFinalResultForDelay.jasper"));
					// 需要時再編譯即可
					if (!reportFile.exists()) {
						JasperReportUtils.initJasperReportsClasspath(request);
						JasperReportUtils
								.compileJasperReports(context
										.getRealPath("/WEB-INF/reports/MidtermFinalResultForDelay.jrxml"));
						reportFile = new File(
								context.getRealPath("/WEB-INF/reports/MidtermFinalResultForDelay.jasper"));
						if (!reportFile.exists())
							throw new JRRuntimeException(
									"查無\"MidtermFinalResultForDelay.jasper\"檔案，請電洽電算中心，謝謝！！");
					}

					JasperReport jasperReport = (JasperReport) JRLoader
							.loadObject(reportFile.getPath());

					// JasperReports套表定義的Parameters
					String[] fields = { "studentNoF", "nameF", "totalCreditF",
							"passCreditF", "averageF", "rankF", "behaviorF",
							"statusF", "extraCourseInfo1F",
							"extraCourseInfo2F", "extraCourseInfo3F",
							"extraCourseInfo4F" };

					float passScore = Toolket
							.getPassScoreByDepartClass(classNo);
					int studentCount = students.size();
					// 要顯示的資料
					List<Object> printData = new ArrayList<Object>();
					String[][] data = new String[studentCount][fields.length + 2];
					DecimalFormat df = new DecimalFormat(",##0.0");

					Map<String, String> parameters = new HashMap<String, String>();
					parameters.put("PASSSCORE", String.valueOf(passScore));
					parameters.put("A", year);
					parameters.put("B", sterm);
					parameters.put("C",
							isFinalResult ? Examine.FINAL.toString()
									: Examine.MID.toString());
					parameters.put("D", Toolket.getClassFullName(classNo));
					float totalScore = 0.0F;
					int notPass = 0, notPass1 = 0;

					ClassScoreSummary summary = new ClassScoreSummary(year,
							sterm, classNo);
					if (isFinalResult)
						summary.setMidFinal(Examine.FINAL);
					else
						summary.setMidFinal(Examine.MID);

					int i = 0;
					for (Student student : students) {
						List<Map> seldInfo = cm.findStudentSeldCourse1(
								student.getStudentNo(), term);
						if (!isFinalResult) // 期中要排除不納入計算的科目
							seldInfo = processExcludedCourse(sm, seldInfo);

						int fieldIndex = 8;
						String[] cscodeArr = new String[0];
						float extraCreditTotal = 0.0f;
						float passExtraCreditTotal = 0.0f;
						float extraScoreTotal = 0.0f;
						StringBuffer extraCourseInfo = new StringBuffer(), extraCourseInfo1 = new StringBuffer();
						StringBuffer extraCourseInfo2 = new StringBuffer(), extraCourseInfo3 = new StringBuffer();

						data[i][0] = student.getStudentNo();
						data[i][1] = student.getStudentName();
						Just just = sam.findJustByStudentNo(data[i][0]);
						// Just just = null;
						double justString = 0.0D;
						if (just == null || just.getTotalScore() == 0.0D) {
							ScoreHist scoreHist = new ScoreHist(
									student.getStudentNo());
							scoreHist.setSchoolYear(new Short(year));
							scoreHist.setSchoolTerm(term);
							scoreHist.setCscode("99999");
							List<ScoreHist> scoreHistList = sm
									.findScoreHistBy(scoreHist);
							if (!scoreHistList.isEmpty()) {
								scoreHist = sm.findScoreHistBy(scoreHist)
										.get(0);
								justString = scoreHist.getScore();
							}
						} else
							justString = just.getTotalScore() >= 95.0d ? 95.0d
									: just.getTotalScore();

						// String couduct = String.valueOf(Math
						// .round((just == null
						// || just.getTotalScore() == null ? 0.0D
						// : just.getTotalScore()))); // 操性
						String couduct = String.valueOf(Math.round(justString)); // 操性
						data[i][6] = Float.valueOf(couduct) >= 60.0f ? couduct
								: couduct + "*";
						for (Map seldData : seldInfo) {
							Integer dtimeOidBySeld = (Integer) seldData
									.get("oid");
							Seld seld = sm.findSeld(dtimeOidBySeld, data[i][0]);
							Dtime dtime = cm.findDtimeBy(seld.getDtimeOid());

							if (!ArrayUtils.contains(cscodeArr,
									dtime.getCscode())) {
								float credit = dtime.getCredit();
								extraCreditTotal += credit;
								cscodeArr = (String[]) ArrayUtils.add(
										cscodeArr, dtime.getCscode());

								boolean bFlag1 = Toolket.isLiteracyClass(dtime
										.getDepartClass());

								StringBuffer buffer = new StringBuffer();
								buffer.append(
										!bFlag1 ? (String) seldData
												.get("ClassName") : "通識課程")
										.append(" ")
										.append((String) seldData
												.get("chi_name")).append("(")
										.append(credit).append(")")
										.append("： ");
								if (isFinalResult) {
									Double score = seld.getScore();
									if (score >= passScore) {
										buffer.append(score);
										passExtraCreditTotal += credit;
									} else {
										buffer.append(score).append("*");
									}
									extraScoreTotal += score * credit;
								} else {
									Double score2 = seld.getScore2();
									boolean bFlag2 = score2 != null;
									if (bFlag2 && score2 >= passScore) {
										buffer.append(score2);
										passExtraCreditTotal += credit;
									} else {
										if (!bFlag2) {
											buffer.append("*");
											score2 = 0.0d;
										} else
											buffer.append(score2).append("*");
									}
									extraScoreTotal += score2 * credit;
								}

								if (extraCourseInfo.length() + buffer.length() <= 100)
									extraCourseInfo.append(buffer)
											.append("   ");
								else if (extraCourseInfo1.length()
										+ buffer.length() <= 100)
									extraCourseInfo1.append(buffer).append(
											"   ");
								else if (extraCourseInfo2.length()
										+ buffer.length() <= 100)
									extraCourseInfo2.append(buffer).append(
											"   ");
								else
									extraCourseInfo3.append(buffer).append(
											"   ");
							}
						}
						data[i][fieldIndex++] = extraCourseInfo.length() == 0 ? null
								: extraCourseInfo.toString();
						data[i][fieldIndex++] = extraCourseInfo1.length() == 0 ? null
								: extraCourseInfo1.toString();
						data[i][fieldIndex++] = extraCourseInfo2.length() == 0 ? null
								: extraCourseInfo2.toString();
						data[i][fieldIndex] = extraCourseInfo3.length() == 0 ? null
								: extraCourseInfo3.toString();
						data[i][2] = String.valueOf(extraCreditTotal);
						data[i][3] = String.valueOf(passExtraCreditTotal);
						Float extraScoreAvg = extraScoreTotal
								/ extraCreditTotal;
						data[i][4] = Float.isNaN(extraScoreAvg) ? "0.0" : df
								.format((extraScoreAvg));
						if (extraCreditTotal >= 10.0F) {
							if ((passExtraCreditTotal / extraCreditTotal) <= (1.0F / 3.0F)) {
								data[i][7] = "2/3";
								summary.getFailStudents2Set().add(
										new FailStudents2(data[i][0],
												extraCreditTotal,
												passExtraCreditTotal,
												extraScoreAvg));
								notPass1++;
							} else if ((passExtraCreditTotal / extraCreditTotal) < 0.5F) {
								data[i][7] = "1/2";
								summary.getFailStudents1Set().add(
										new FailStudents1(data[i][0],
												extraCreditTotal,
												passExtraCreditTotal,
												extraScoreAvg));
								notPass++;
							}
						}

						StdScore ss = new StdScore(data[i][0],
								Float.valueOf(data[i][2]),
								Float.valueOf(data[i][3]),
								Float.valueOf(data[i][4]),
								Double.valueOf(String.valueOf(justString)),
								Integer.parseInt("0"));
						summary.getStdScoreSet().add(ss);

						totalScore += Float.valueOf(data[i][4].trim());
						printData.add(data[i]);
						i++;
					}
					summary.setSummary1(notPass);
					summary.setSummary2(notPass1);

					Float avg = totalScore / studentCount;
					summary.setAverage(avg);

					summary.setLastModified(new Date());
					sm.txSaveClassScoreSummary(summary);
					parameters.put("Q", df.format(avg + 0.001D));
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							jasperReport, parameters,
							new HibernateQueryResultDataSource(printData,
									fields));
					OutputStream os = new BufferedOutputStream(
							new FileOutputStream(new File(tempDir, classNo
									+ ".pdf")));
					JasperExportManager
							.exportReportToPdfStream(jasperPrint, os);
					os.close();
				}

				File[] files = tempDir.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.endsWith(".pdf");
					}
				});

				if (files.length > 0) {
					File printingPdf = mergePdfsForPrinting(files, new File(
							System.getProperty("java.io.tmpdir")),
							"ReportPrintingPDF.pdf");
					JasperReportUtils.printPdfToFrontEnd(response, printingPdf);
					printingPdf.delete();
				} else {
					Map<String, String> param = new HashMap<String, String>();
					File image = new File(
							context.getRealPath("/pages/images/2002chitS.jpg"));
					param.put("IMAGE", image.getAbsolutePath());
					byte[] bytes = JasperRunManager.runReportToPdf(
							JasperReportUtils.getNoResultReport(context),
							param, new JREmptyDataSource());
					JasperReportUtils.printPdfToFrontEnd(response, bytes);
				}
				tempDir.delete();
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "查無任何開課資料，請重新選擇開課班級"));
				return messages;
			}
		} else {

			// 分班級
			Map<String, List<Map>> dtimesOfClass = separeteClass(allDtimeList);

			// JasperReports套表定義的欄位名稱
			// JasperReports套表定義的Parameters
			String[] courseNameIndex = { "E", "F", "G", "H", "I", "J", "K",
					"L", "M", "N", "O", "P", "Q", "R" }; // 科目名稱
			String[] optCreditInfos = { "A1", "A2", "A3", "A4", "A5", "A6",
					"A7", "A8", "A9", "A10", "A11", "A12", "A13", "A14" }; // 科目必選修及學分數
			String[] passCountsIndex = { "B1", "B2", "B3", "B4", "B5", "B6",
					"B7", "B8", "B9", "B10", "B11", "B12", "B13", "B14" }; // 及格人數
			String[] notPassCountsIndex = { "C1", "C2", "C3", "C4", "C5", "C6",
					"C7", "C8", "C9", "C10", "C11", "C12", "C13", "C14" }; // 不及格人數
			String[] courseScoreAvgIndex = { "D1", "D2", "D3", "D4", "D5",
					"D6", "D7", "D8", "D9", "D10", "D11", "D12", "D13", "D14" }; // 各科平均分數
			String[] fields = { "studentNoF", "nameF", "score1F", "score2F",
					"score3F", "score4F", "score5F", "score6F", "score7F",
					"score8F", "score9F", "score10F", "score11F", "score12F",
					"score13F", "score14F", "totalCreditF", "passCreditF",
					"averageF", "rankF", "behaviorF", "statusF",
					"extraCourseInfo1F", "extraCourseInfo2F" };

			DecimalFormat df = new DecimalFormat(",##0.0");
			DecimalFormat df1 = new DecimalFormat(",##0.0000");

			File reportFile = new File(
					context.getRealPath("/WEB-INF/reports/MidtermFinalResult.jasper"));
			// 需要時再編譯即可
			if (!reportFile.exists()) {
				JasperReportUtils.initJasperReportsClasspath(request);
				JasperReportUtils
						.compileJasperReports(context
								.getRealPath("/WEB-INF/reports/MidtermFinalResult.jrxml"));
				reportFile = new File(
						context.getRealPath("/WEB-INF/reports/MidtermFinalResult.jasper"));
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"查無\"MidtermFinalResult.jasper\"檔案，請電洽電算中心，謝謝！！");
			}

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
			List<Map> dtimeData = null;
			List<Student> students = null;
			List<Object> printData = null;
			String[][] data = null;
			float[][] extraInfo = null;
			int[] coursePassNum = null, courseNotPassNum = null, seldCounts = null;
			int[][] scoreRanges = null;
			double[] courseScoreTotal = null;
			float[] extraCredit = null, passExtrsCredit = null, extraScore = null;
			// String schoolTerm = am.findTermBy(PARAMETER_SCHOOL_TERM);
			// boolean isHistory = !sterm.equals(schoolTerm);

			for (Iterator iter = dtimesOfClass.keySet().iterator(); iter
					.hasNext();) {

				String classNo = (String) iter.next();
				if (Toolket.isLiteracyClass(classNo))
					continue;

				ClassScoreSummary summary = null;
				if (isFinalResult)
					summary = new ClassScoreSummary(year, sterm, classNo,
							Examine.FINAL);
				else
					summary = new ClassScoreSummary(year, sterm, classNo,
							Examine.MID);

				// 排除通識課程,碩士論文一
				dtimeData = excluded(dtimesOfClass.get(classNo));
				if (!isFinalResult) // 期中要排除不納入計算的科目
					dtimeData = processExcludedCourse(sm, dtimeData);

				if (dtimeData.isEmpty())
					continue; // 無開課資料

				students = mm.findStudentsByClassNo(classNo);
				if (students.isEmpty())
					continue; // 沒學生就換下一班

				// 儲存原班級所有Dtime Oid,比對加選課程並排除
				String[] origDtimeOids = new String[0];
				for (Map dtime : dtimeData)
					origDtimeOids = (String[]) ArrayUtils.add(origDtimeOids,
							((Integer) dtime.get("oid")).toString());

				// 碩士班70分及格
				float passScore = Toolket.getPassScoreByDepartClass(classNo);
				int studentCount = students.size();
				// 要顯示的資料
				printData = new ArrayList<Object>();
				// 要顯示的資料,但因為多位學生,所以以2維表示
				// 多2個是要紀錄如求名次等相關資料
				data = new String[studentCount][fields.length + 3];
				// 紀錄額外資料,如學分總數或實得學分等
				// [0]:學號, [1]:姓名, [16]:學生學分總數, [17]:學生實得學分, [18]:學生個人平均,
				// [19]:名次, [20]:操行, [21]:狀態為1/2或2/3
				// [22][23]:加選科目資料, [24][25]:求名次之暫時分數, [26]:操行(包括小數點以下)
				extraInfo = new float[studentCount][3];
				// [0]:學分總數, [1]:實得學分, [2]:總分
				int fieldCounts = courseNameIndex.length; // 欄位總量
				coursePassNum = new int[fieldCounts]; // 統計各科目及格人數
				courseNotPassNum = new int[fieldCounts]; // 統計各科目不及格人數
				scoreRanges = new int[fieldCounts][5]; // 紀錄各科成績範圍
				String[] cscodes = new String[0]; // 紀錄各科目代碼
				seldCounts = new int[fieldCounts]; // 統計各科目選課人數
				courseScoreTotal = new double[fieldCounts]; // 科目分數加總
				int index = 0; // 各資料欄位索引
				extraCredit = new float[studentCount]; // 非該班之加選課程學分
				passExtrsCredit = new float[studentCount]; // 非該班之加選課程Pass學分
				extraScore = new float[studentCount]; // 非該班之加選課程總分加總
				Map classDtime = null;
				Student student = null;
				Just just = null;
				Seld seld = null;
				Dtime dtime = null;
				List<Map> seldInfo = null;
				boolean isJustHistory = false;
				// boolean isScoreHistory = false;

				Map<String, String> parameters = new HashMap<String, String>();
				parameters.put("PASSSCORE", String.valueOf(passScore));
				parameters.put("A", year);
				parameters.put("B", sterm);
				parameters.put("C", isFinalResult ? Examine.FINAL.toString()
						: Examine.MID.toString());
				parameters.put("D", (String) dtimeData.get(0).get("ClassName"));

				for (int i = 0; i < fieldCounts; i++) {
					if (i < dtimeData.size()) { // 將其他後面欄位填滿空白
						classDtime = dtimeData.get(i);
						String cscode = (String) classDtime.get("cscode");
						cscodes = (String[]) ArrayUtils.add(cscodes, cscode);
						parameters.put(courseNameIndex[index],
								(String) classDtime.get("chi_name"));
						parameters.put(
								optCreditInfos[index],
								StringUtils.substring(
										(String) classDtime.get("opt2"), 0, 1)
										+ " "
										+ (Float) classDtime.get("credit"));
						Integer dtimeOid = (Integer) classDtime.get("oid");
						for (int j = 0; j < studentCount; j++) {
							student = students.get(j);
							String studentNo = student.getStudentNo();
							data[j][0] = studentNo;
							data[j][1] = student.getStudentName();
							// just = sam.findJustByStudentNo(studentNo);
							double justString = 0.0D;
							if (just == null || just.getTotalScore() == 0.0D) {
								isJustHistory = true;
								ScoreHist scoreHist = new ScoreHist(studentNo);
								scoreHist.setSchoolYear(new Short(year));
								scoreHist.setSchoolTerm(term);
								scoreHist.setCscode("99999");
								List<ScoreHist> scoreHistList = sm
										.findScoreHistBy(scoreHist);
								if (!scoreHistList.isEmpty()) {
									scoreHist = sm.findScoreHistBy(scoreHist)
											.get(0);
									justString = scoreHist.getScore();
								}
							} else
								justString = just.getTotalScore() >= 95.0d ? 95.0d
										: just.getTotalScore();

							// String couduct = String
							// .valueOf(Math
							// .round((just == null
							// || just.getTotalScore() == null ? 0.0D
							// : just.getTotalScore()))); // 操性
							String couduct = String.valueOf(Math
									.round(justString)); // 操性
							data[j][20] = Float.valueOf(couduct) >= 60.0F ? couduct
									: couduct + "*";
							data[j][26] = isJustHistory ? String
									.valueOf(justString) : (just == null
									|| just.getTotalScore() == null ? "0.0"
									: just.getTotalScore().toString());

							seld = sm.findSeld(dtimeOid, studentNo);
							boolean bFlag = seld != null;
							if (bFlag) {
								seldCounts[index]++; // 計算選課總人數
								// 未修則不記學分總數
								dtime = cm.findDtimeBy(seld.getDtimeOid());
								extraInfo[j][0] += dtime.getCredit(); // 學分總數
								if (isFinalResult) { // 期末考
									Double score = seld.getScore();
									// seld=null與seld.getScorex()=null意義不同
									// seld=null代表沒選,分數以'/'表示
									// seld.getScorex()=null代表有選但無上傳分數,給'0'
									boolean bFlag1 = bFlag ? (score != null ? true
											: false)
											: false;

									// 不及格或有修但無分數則要以*標示
									data[j][index + 2] = !bFlag ? "0.0*"
											: (bFlag1 && score >= passScore ? score
													.toString()
													: (bFlag1 ? score
															.toString() + "*"
															: "*"));
									if (bFlag1 && score >= passScore) {
										extraInfo[j][1] += dtime.getCredit(); // 實得學分
										coursePassNum[index] += 1; // 及格總人數
										if (score >= 60.0D && score < 80.0D)
											scoreRanges[i][3]++;
										else if (score >= 80.0D)
											scoreRanges[i][4]++;
									} else {
										courseNotPassNum[index] += 1; // 不及格總人數
										if (score == null || score < 20.0D)
											scoreRanges[i][0]++;
										else if (score >= 20.0D
												&& score < 40.0D)
											scoreRanges[i][1]++;
										else
											scoreRanges[i][2]++;
									}

									courseScoreTotal[index] += (!bFlag1 ? 0.0D
											: score);
									extraInfo[j][2] += (!bFlag1 ? 0.0F : score
											* dtime.getCredit()); // 總分
								} else {
									// 不及格要以*標示
									Double score2 = seld.getScore2();
									boolean bFlag1 = bFlag ? (score2 != null ? true
											: false)
											: false;
									data[j][index + 2] = !bFlag1 ? "0.0*"
											: (bFlag1 && score2 >= passScore ? score2
													.toString()
													: (bFlag1 ? score2
															.toString() + "*"
															: "*"));
									if (bFlag1 && score2 >= passScore) {
										extraInfo[j][1] += dtime.getCredit(); // 實得學分
										coursePassNum[index] += 1; // 及格總人數
										if (score2 >= 60.0D && score2 < 80.0D)
											scoreRanges[i][3]++;
										else if (score2 >= 80.0D)
											scoreRanges[i][4]++;
									} else {
										courseNotPassNum[index] += 1; // 不及格總人數
										if (score2 == null || score2 < 20.0D)
											scoreRanges[i][0]++;
										else if (score2 >= 20.0D
												&& score2 < 40.0D)
											scoreRanges[i][1]++;
										else
											scoreRanges[i][2]++;
									}

									courseScoreTotal[index] += (!bFlag1 ? 0.0D
											: score2);
									extraInfo[j][2] += (!bFlag1 ? 0.0F : score2
											* dtime.getCredit()); // 總分
								}
							} else {
								data[j][index + 2] = "/"; // 未選修以/表示
							}

						}
						index++;
					} else {
						// 將其他後面欄位填滿空白
						parameters.put(courseNameIndex[i], null);
						parameters.put(optCreditInfos[i], null);
						index++;
					}
				}

				for (int i = 0; i < studentCount; i++) {

					// 抓出加選之課程
					student = students.get(i);
					String studentNo = student.getStudentNo();
					seldInfo = processExcludedThesis(cm.findStudentSeldCourse1(
							studentNo, term));
					if (!isFinalResult) // 期中要排除不納入計算的科目
						seldInfo = processExcludedCourse(sm, seldInfo);

					int fieldIndex = 22;
					String[] cscodeArr = new String[0];
					float extraCreditTotal = 0.0F;
					float passExtraCreditTotal = 0.0F;
					float extraScoreTotal = 0.0F;
					StringBuffer extraCourseInfo = new StringBuffer(), extraCourseInfo1 = new StringBuffer();
					for (Map seldData : seldInfo) {
						Integer dtimeOidBySeld = (Integer) seldData.get("oid");
						if (!ArrayUtils.contains(origDtimeOids,
								dtimeOidBySeld.toString())) {
							seld = sm.findSeld(dtimeOidBySeld, studentNo);
							dtime = cm.findDtimeBy(seld.getDtimeOid());

							if (!ArrayUtils.contains(cscodeArr,
									dtime.getCscode())) {
								float credit = dtime.getCredit();
								extraCreditTotal += credit;
								cscodeArr = (String[]) ArrayUtils.add(
										cscodeArr, dtime.getCscode());

								boolean bFlag1 = Toolket.isLiteracyClass(dtime
										.getDepartClass());

								StringBuffer buffer = new StringBuffer();
								buffer.append(
										!bFlag1 ? (String) seldData
												.get("ClassName") : "通識課程")
										.append(" ")
										.append((String) seldData
												.get("chi_name")).append("(")
										.append(credit).append(")").append(":");
								if (isFinalResult) {
									Double score = seld.getScore() != null ? seld
											.getScore() : 0.0D;
									if (score >= passScore) {
										buffer.append(score);
										passExtraCreditTotal += credit;
									} else {
										buffer.append(score).append("*");
									}
									extraScoreTotal += score * credit;
								} else {
									Double score2 = seld.getScore2() != null ? seld
											.getScore2() : 0.0D;
									boolean bFlag2 = score2 != null;
									if (bFlag2 && score2 >= passScore) {
										buffer.append(score2);
										passExtraCreditTotal += credit;
									} else {
										if (!bFlag2) {
											buffer.append("*");
											score2 = 0.0D;
										} else
											buffer.append(score2).append("*");
									}
									extraScoreTotal += score2 * credit;
								}

								if (extraCourseInfo.length() + buffer.length() <= 165)
									extraCourseInfo.append(buffer).append("  ");
								else
									extraCourseInfo1.append(buffer)
											.append("  ");
								// else if (extraCourseInfo1.length()
								// + buffer.length() <= 170)
								// extraCourseInfo1.append(buffer).append(
								// " ");
							}
						}
					}
					data[i][fieldIndex++] = extraCourseInfo.length() == 0 ? ""
							: extraCourseInfo.toString();
					data[i][fieldIndex] = extraCourseInfo1.length() == 0 ? ""
							: extraCourseInfo1.toString();

					extraCredit[i] = extraCreditTotal; // 非該班之加選課程學分
					passExtrsCredit[i] = passExtraCreditTotal; // 非該班之加選課程Pass學分
					extraScore[i] = extraScoreTotal; // 非該班之加選課程總分加總
				}

				classDtime = null;
				student = null;
				just = null;
				seld = null;
				dtime = null;
				seldInfo = null;

				// 加入額外的選課資料
				for (int i = 0; i < extraCredit.length; i++) {
					extraInfo[i][0] += extraCredit[i]; // 學分總數
					extraInfo[i][1] += passExtrsCredit[i]; // 實得學分數
					extraInfo[i][2] += extraScore[i]; // 總分
				}

				for (int i = 0; i < fieldCounts; i++) {
					if (coursePassNum[i] != 0) { // 應該不會有及格學生為0現象
						parameters.put(passCountsIndex[i],
								String.valueOf(coursePassNum[i]));
						parameters.put(notPassCountsIndex[i],
								String.valueOf(courseNotPassNum[i]));
						// 科目平均
						float courseAvg = Float
								.parseFloat(df
										.format((courseScoreTotal[i] / (double) (coursePassNum[i] + courseNotPassNum[i])) + 0.001D)
										.trim());
						parameters.put(
								courseScoreAvgIndex[i],
								courseAvg >= passScore ? String
										.valueOf(courseAvg) : courseAvg + "*");
						if (StringUtils.isNotBlank(cscodes[i])) {
							ScoreStatistic statistic = new ScoreStatistic(
									cscodes[i]);
							statistic.setPass(coursePassNum[i]);
							statistic.setFail(courseNotPassNum[i]);
							statistic.setAverage((float) courseScoreTotal[i]
									/ (coursePassNum[i] + courseNotPassNum[i]));
							statistic.setRange1(scoreRanges[i][0]);
							statistic.setRange2(scoreRanges[i][1]);
							statistic.setRange3(scoreRanges[i][2]);
							statistic.setRange4(scoreRanges[i][3]);
							statistic.setRange5(scoreRanges[i][4]);
							summary.getScoreStatisticSet().add(statistic);
						}
					} else {
						// 為讓0顯示出來
						parameters.put(passCountsIndex[i], null);
						parameters.put(notPassCountsIndex[i], null);
						parameters.put(courseScoreAvgIndex[i], null);
					}
				}

				int notPass2 = 0, notPass3 = 0;
				int counts = data.length;
				float[] avgForRange = new float[0]; // 學生平均分數(小數點第3位),Sort較準
				float avgTotal = 0.0F; // 統計所有學生平均分數總和,再做全班總平均
				for (int i = 0; i < counts; i++) {
					data[i][16] = String.valueOf(extraInfo[i][0]); // 學生學分總數
					data[i][17] = String.valueOf(extraInfo[i][1]); // 學生實得學分
					String scoreAvg = df
							.format((extraInfo[i][2] / extraInfo[i][0]) + 0.001D); // 平均(四捨五入)
					String scoreAvgRange = df1
							.format((extraInfo[i][2] / extraInfo[i][0]) + 0.00001D);
					try {
						avgTotal += Float.valueOf(scoreAvg);
					} catch (NumberFormatException nfe) {
						scoreAvg = "0.0";
					}
					try {
						Float.valueOf(scoreAvgRange);
					} catch (NumberFormatException nfe) {
						scoreAvgRange = "0.0";
					}
					// scoreAvgRange要換回scoreAvg
					data[i][18] = StringUtils.isNotEmpty(scoreAvg) ? (Float
							.valueOf(scoreAvg) >= passScore ? scoreAvg
							: scoreAvg + "*") : "0*"; // 學生個人平均
					float creditAvg = Float.parseFloat(data[i][17])
							/ Float.parseFloat(data[i][16]);
					if (Float.parseFloat(data[i][16]) >= 10.0F) // 10學分以上再做判斷
						data[i][21] = creditAvg <= (1F / 3F) ? "2/3"
								: (creditAvg <= (0.5F) ? "1/2" : ""); // 取得狀態為1/2或2/3

					if ("1/2".equals(data[i][21])) {
						notPass2++;
						summary.getFailStudents1Set().add(
								new FailStudents1(data[i][0], extraInfo[i][0],
										extraInfo[i][1], Float
												.valueOf(scoreAvg)));
					} else if ("2/3".equals(data[i][21])) {
						notPass3++;
						summary.getFailStudents2Set().add(
								new FailStudents2(data[i][0], extraInfo[i][0],
										extraInfo[i][1], Float
												.valueOf(scoreAvg)));
					}

					data[i][24] = scoreAvg; // 求名次之暫時分數24
					data[i][25] = scoreAvgRange; // 求名次之暫時分數(小數點第4位)25
					avgForRange = ArrayUtils.add(avgForRange,
							Float.valueOf(scoreAvgRange));
					printData.add(data[i]);
				}

				Set<ScoreStatistic> sss = summary.getScoreStatisticSet();
				int size = sss.size();
				float total = 0.0F;
				for (ScoreStatistic ss : sss)
					total += ss.getAverage();
				parameters.put("S", df.format((total / size) + 0.001D)); // 全班總平均
				summary.setSummary1(notPass2);
				summary.setSummary2(notPass3);
				summary.setAverage(Float.isNaN(((float) (total / size))) ? 0.0F
						: (float) (total / size));

				Arrays.sort(avgForRange);// 先排序
				// 計算名次,會計算到小數點4位
				String[] top3 = new String[] { "1", "2", "3" };
				int[] rangeRec = new int[0]; // 紀錄排名
				// 紀錄符合條件在陣列內的位置
				Map<String, String> rangeRecM = new HashMap<String, String>();
				boolean isMasterClass = Toolket.isMasterClass(summary
						.getDepartClass());
				StdScore ss = null;
				for (int i = 0; i < counts; i++) {
					String[] data1 = (String[]) printData.get(i);
					int rangeIndex = Arrays.binarySearch(avgForRange, Float
							.valueOf(data1[25]).floatValue());
					data1[19] = String.valueOf(counts - rangeIndex); // 學生班級名次
					// 可能同名次
					if ((rangeIndex + 1) < counts
							&& (avgForRange[rangeIndex + 1] == avgForRange[rangeIndex])) {
						if (true) // 如果分數相同,名次要再往前1名
							data1[19] = String.valueOf(Integer
									.parseInt(data1[19]) - 1);
					}

					// 可以同名次,但前3名就要分(以操行)
					// 碩士班或非日間部不用比了
					if (!isMasterClass
							&& ArrayUtils.contains(
									IConstants.DEPT_DAY,
									StringUtils.substring(
											summary.getDepartClass(), 1, 3))
							&& Arrays.binarySearch(top3, data1[19]) >= 0) {
						// 比對有無名次相同者?
						if (Arrays.binarySearch(rangeRec,
								Integer.parseInt(data1[19])) >= 0) {
							// 名次有相同者要開始比對操行
							double b1 = Double.parseDouble(data1[26]); // 目前Index者的操行
							// String[] d = (String[]) printData
							// .get(indexRec[pos]);
							// int b2 = Integer.parseInt(d[20]);
							String[] d = (String[]) printData.get(Integer
									.parseInt(rangeRecM.get(data1[19])));
							double b2 = Double.parseDouble(d[26]); // 要比對者的操行
							if (b1 > b2) {
								// String[] da = (String[]) printData
								// .get(indexRec[pos]);
								d[19] = String
										.valueOf(Integer.parseInt(d[19]) + 1);
							} else if (b1 < b2) {
								data1[19] = String.valueOf(Integer
										.parseInt(data1[19]) + 1);
							} else {
								// data1[19] = String.valueOf(Integer
								// .parseInt(data1[19]) + 1);
								log.error(data1[0]);
								log.error("NO, My GOD!!!");
							}
						} else {
							// 名次不相同者要記錄下來作為比對用
							rangeRec = ArrayUtils.add(rangeRec,
									Integer.parseInt(data1[19]));
							Arrays.sort(rangeRec);
							rangeRecM.put(data1[19], String.valueOf(i));
						}
					}

					ss = new StdScore(data1[0], Float.valueOf(data1[16]),
							Float.valueOf(data1[17]), Float.valueOf(data1[25]),
							Double.valueOf(data1[26]),
							Integer.parseInt(data1[19]));
					summary.getStdScoreSet().add(ss);
				}

				// 找出前3名學生
				if (!isMasterClass) {
					for (int i = 0; i < counts; i++) {
						String[] data1 = (String[]) printData.get(i);
						if (Arrays.binarySearch(top3, data1[19]) >= 0) {
							TopStudent ts = new TopStudent(data1[0],
									Float.valueOf(data1[18].replace('*', ' ')
											.trim()),
									Integer.parseInt(data1[19]) - 1); // Pos從0開始
							ts.setClassScoreSummary(summary);
							summary.getTopStudentSet().add(ts);
						}
					}
				}
				summary.setLastModified(new Date());
				sm.txSaveClassScoreSummary(summary);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, parameters,
						new HibernateQueryResultDataSource(printData, fields));

				OutputStream os = new BufferedOutputStream(
						new FileOutputStream(
								new File(tempDir, classNo + ".pdf")));
				JasperExportManager.exportReportToPdfStream(jasperPrint, os);
				os.close();
			}

			File[] files = tempDir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".pdf");
				}
			});

			if (files.length > 0) {
				File printingPdf = mergePdfsForPrinting(files,
						new File(System.getProperty("java.io.tmpdir")),
						"ReportPrintingPDF.pdf");
				JasperReportUtils.printPdfToFrontEnd(response, printingPdf);
				printingPdf.delete();
			} else {
				Map<String, String> param = new HashMap<String, String>();
				File image = new File(
						context.getRealPath("/pages/images/2002chitS.jpg"));
				param.put("IMAGE", image.getAbsolutePath());
				byte[] bytes = JasperRunManager.runReportToPdf(
						JasperReportUtils.getNoResultReport(context), param,
						new JREmptyDataSource());
				JasperReportUtils.printPdfToFrontEnd(response, bytes);
			}
			tempDir.delete();
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Course.errorN1", "報表製作已完成"));
		return messages;
	}

	/**
	 * 列印/預覽期中期末全班平均成績作業
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @param isPrint
	 *            列印與否
	 * @throws Exception
	 */
	private ActionMessages printMidtermFinalAvgResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm, boolean setupPrinter,
			boolean isPrint) throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		boolean isFinalResult = "final".equalsIgnoreCase(request
				.getParameter("rt")); // 是否為期末?預設為期末成績
		String year = cm.getNowBy("School_year");
		String term = (String) aForm.get("sterm");

		Map<String, String> parameters = new HashMap<String, String>();
		String campusNo = aForm.getString("campusInCharge2").equals("1") ? "台北"
				: "新竹";
		parameters.put("A", campusNo);
		parameters.put("B", year);
		parameters.put("C", sterm);
		parameters.put("D", isFinalResult ? Examine.FINAL.toString()
				: Examine.MID.toString());
		String[] fields = { "className", "avg", "notPass1", "notPass2" };

		DecimalFormat df = new DecimalFormat(",##0.0");

		ServletContext context = request.getSession().getServletContext();
		File reportFile = new File(
				context.getRealPath("/WEB-INF/reports/MidtermFinalAvgResult.jasper"));
		if (!reportFile.exists()) {
			JasperReportUtils.initJasperReportsClasspath(request);
			JasperReportUtils
					.compileJasperReports(context
							.getRealPath("/WEB-INF/reports/MidtermFinalAvgResult.jrxml"));
			reportFile = new File(
					context.getRealPath("/WEB-INF/reports/MidtermFinalAvgResult.jasper"));
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"查無\"MidtermFinalAvgResult.jasper\"檔案，請電洽電算中心，謝謝！！");
		}
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());

		Float totalAvgScore = 0.0F;
		int totalNotPassStudent1 = 0, totalNotPassStudent2 = 0;
		// ClassScoreSummary summary = new ClassScoreSummary(year, term,
		// processClassInfo(aForm));
		ClassScoreSummary summary = null;
		if (isFinalResult)
			summary = new ClassScoreSummary(year, term,
					processClassInfo(aForm), Examine.FINAL);
		else
			summary = new ClassScoreSummary(year, term,
					processClassInfo(aForm), Examine.MID);
		List<ClassScoreSummary> csss = sm
				.findClassScoreSummaryByLikeExpression(summary);
		List<Object> printData = new ArrayList<Object>();
		String[] data = null;
		Float passScore = 0.0F, avgScore = 0.0F;
		Map<ScoreStatus, Integer> failStudents = null;
		for (ClassScoreSummary css : csss) {
			data = new String[4];
			data[0] = Toolket.getClassFullName(css.getDepartClass());
			passScore = Toolket.getPassScoreByDepartClass(css.getDepartClass());
			avgScore = Float.parseFloat(df.format(css.getAverage() + 0.001F));
			data[1] = avgScore < passScore ? String.valueOf(avgScore) + "*"
					: String.valueOf(avgScore);
			failStudents = calFailStudents(css.getStdScoreSet());
			data[2] = failStudents.get(ScoreStatus.STATUS_2).toString();
			data[3] = failStudents.get(ScoreStatus.STATUS_3).toString();
			// data[2] = css.getSummary1().toString();
			// data[3] = css.getSummary2().toString();
			totalNotPassStudent1 += Integer.parseInt(data[2]);
			totalNotPassStudent2 += Integer.parseInt(data[3]);
			totalAvgScore += css.getAverage();
			printData.add(data);
		}

		parameters.put("E", String.valueOf(totalNotPassStudent1));
		parameters.put("F", String.valueOf(totalNotPassStudent2));
		parameters.put("G",
				df.format((float) (totalAvgScore / csss.size() + 0.001F)));
		byte[] bytes = null;
		if (!printData.isEmpty()) {
			bytes = JasperRunManager.runReportToPdf(jasperReport, parameters,
					new HibernateQueryResultDataSource(printData, fields));
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "報表製作已完成"));
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), param,
					new JREmptyDataSource());
		}
		JasperReportUtils.printPdfToFrontEnd(response, bytes);
		return messages;
	}

	/**
	 * 預覽期中期末全班前三名學生名單作業
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @param isPrint
	 *            列印與否
	 * @throws Exception
	 */
	private ActionMessages printMidtermFinalTopStudentsResult(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm, boolean setupPrinter,
			boolean isPrint) throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		boolean isFinalResult = "final".equalsIgnoreCase(request
				.getParameter("rt")); // 是否為期末?預設為期末成績
		String year = cm.getNowBy("School_year");
		String term = (String) aForm.get("sterm");

		Map<String, String> parameters = new HashMap<String, String>();
		String campusNo = aForm.getString("campusInCharge2").equals("1") ? "台北"
				: "新竹";
		parameters.put("A", campusNo);
		parameters.put("B", year);
		parameters.put("C", sterm);
		parameters.put("D", isFinalResult ? Examine.FINAL.toString()
				: Examine.MID.toString());
		String[] fields = { "className", "studentNo1", "studentName1",
				"score1", "studentNo2", "studentName2", "score2", "studentNo3",
				"studentName3", "score3" };
		DecimalFormat df = new DecimalFormat(",##0.0");

		ServletContext context = request.getSession().getServletContext();
		File reportFile = new File(
				context.getRealPath("/WEB-INF/reports/MidtermFinalTop3StudentsResult.jasper"));
		if (!reportFile.exists()) {
			JasperReportUtils.initJasperReportsClasspath(request);
			JasperReportUtils
					.compileJasperReports(context
							.getRealPath("/WEB-INF/reports/MidtermFinalTop3StudentsResult.jrxml"));
			reportFile = new File(
					context.getRealPath("/WEB-INF/reports/MidtermFinalTop3StudentsResult.jasper"));
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"查無\"MidtermFinalTop3StudentsResult.jasper\"檔案，請電洽電算中心，謝謝！！");
		}
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());

		ClassScoreSummary summary = null;
		if (isFinalResult)
			summary = new ClassScoreSummary(year, term,
					processClassInfo(aForm), Examine.FINAL);
		else
			summary = new ClassScoreSummary(year, term,
					processClassInfo(aForm), Examine.MID);

		List<ClassScoreSummary> csss = sm
				.findClassScoreSummaryByLikeExpression(summary);
		List<Object> printData = new ArrayList<Object>();
		String departClass = null, studentName = null;
		int i = 0;
		String className = null;
		String[] data = null;
		StdScore[] s = null, stdScores = null;
		Student student = null;
		Graduate graduate = null;
		for (ClassScoreSummary css : csss) {
			departClass = css.getDepartClass();
			if (!Toolket.isDelayClass(departClass)) {
				i = 0;
				data = new String[10];
				className = StringUtils.substring(
						Toolket.getClassFullName(departClass), 2);
				data[i++] = className;
				stdScores = css.getStdScoreSet().toArray(new StdScore[0]);
				s = (StdScore[]) ArrayUtils.subarray(stdScores, 0, 3);
				for (StdScore stdScore : s) {
					data[i++] = stdScore.getStudentNo();
					student = mm.findStudentByNo(stdScore.getStudentNo());
					if (student == null) {
						graduate = mm.findGraduateByStudentNo(data[1]);
						studentName = graduate.getStudentName();
					} else
						studentName = student.getStudentName();

					data[i++] = studentName.trim();
					data[i++] = df.format(stdScore.getAverage() + 0.001F);
				}
				printData.add(data);
			}
		}

		byte[] bytes = null;
		if (!printData.isEmpty()) {
			bytes = JasperRunManager.runReportToPdf(jasperReport, parameters,
					new HibernateQueryResultDataSource(printData, fields));
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "報表製作已完成"));
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), param,
					new JREmptyDataSource());
		}

		JasperReportUtils.printPdfToFrontEnd(response, bytes);

		return messages;
	}

	/**
	 * 預覽期中期末1/2或2/3不及格學生名單作業(全校)
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @param isPrint
	 *            列印與否
	 * @throws Exception
	 */
	private ActionMessages printMidtermFinalFailStudentsResult(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm, boolean setupPrinter,
			boolean isPrint) throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		boolean isFinalResult = "final".equalsIgnoreCase(request
				.getParameter("rt")); // 是否為期末?預設為期末成績
		boolean isFail1 = "2".equals(request.getParameter("ft"));
		String year = cm.getNowBy("School_year");
		String term = (String) aForm.get("sterm");

		Map<String, String> parameters = new HashMap<String, String>();
		String campusNo = aForm.getString("campusInCharge2").equals("1") ? "台北"
				: "新竹";
		parameters.put("A", campusNo);
		parameters.put("B", year);
		parameters.put("C", sterm);
		String info = isFinalResult ? Examine.FINAL.toString() + " "
				: Examine.MID.toString() + " ";
		info += isFail1 ? "1/2 " : "2/3 ";
		parameters.put("D", info);
		String[] fields = { "className", "studentNo", "studentName",
				"notPass1", "notPass2", "avg" };
		DecimalFormat df = new DecimalFormat(",##0.0");

		ServletContext context = request.getSession().getServletContext();
		File reportFile = new File(
				context.getRealPath("/WEB-INF/reports/MidtermFinalFailStudentsResult.jasper"));
		if (!reportFile.exists()) {
			JasperReportUtils.initJasperReportsClasspath(request);
			JasperReportUtils
					.compileJasperReports(context
							.getRealPath("/WEB-INF/reports/MidtermFinalFailStudentsResult.jrxml"));
			reportFile = new File(
					context.getRealPath("/WEB-INF/reports/MidtermFinalFailStudentsResult.jasper"));
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"查無\"MidtermFinalFailStudentsResult.jasper\"檔案，請電洽電算中心，謝謝！！");
		}
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());

		ClassScoreSummary summary = null;
		if (isFinalResult)
			summary = new ClassScoreSummary(year, term,
					processClassInfo(aForm), Examine.FINAL);
		else
			summary = new ClassScoreSummary(year, term,
					processClassInfo(aForm), Examine.MID);

		List<ClassScoreSummary> csss = sm
				.findClassScoreSummaryByLikeExpression(summary);
		List<Object> printData = new ArrayList<Object>();
		Set<StdScore> sss = null;
		Student student = null;
		Graduate graduate = null;
		String className = null, studentName = "";
		String[] data = null;
		ScoreStatus status = isFail1 ? ScoreStatus.STATUS_2
				: ScoreStatus.STATUS_3;

		for (ClassScoreSummary css : csss) {
			className = Toolket.getClassFullName(css.getDepartClass());
			sss = css.getStdScoreSet();
			for (StdScore ss : sss) {
				if (ss.getStatus() == status) {
					data = new String[6];
					data[0] = className;
					data[1] = ss.getStudentNo();
					studentName = "";
					student = mm.findStudentByNo(data[1]);
					if (student == null) {
						graduate = mm.findGraduateByStudentNo(data[1]);
						studentName = graduate.getStudentName();
					} else
						studentName = student.getStudentName();

					data[2] = studentName;
					data[3] = String.valueOf(ss.getTotalCredits());
					data[4] = String.valueOf(ss.getTotalCredits()
							- ss.getPassCredits());
					data[5] = df.format(ss.getAverage() + 0.001F);
					printData.add(data);
				}
			}
			// if (isFail1) {
			// fs1s = css.getFailStudents1Set();
			// for (FailStudents1 fs1 : fs1s) {
			// data = new String[6];
			// data[0] = className;
			// data[1] = fs1.getStudentNo();
			// studentName = "";
			// student = mm.findStudentByNo(data[1]);
			// if (student == null) {
			// graduate = mm.findGraduateByStudentNo(data[1]);
			// studentName = graduate.getStudentName();
			// } else
			// studentName = student.getStudentName();
			// data[2] = studentName;
			// data[3] = String.valueOf(fs1.getTotalCredits());
			// data[4] = String.valueOf(fs1.getTotalCredits()
			// - fs1.getPassCredits());
			// data[5] = df.format(fs1.getAverage() + 0.001F);
			// printData.add(data);
			// }
			// } else {
			// Set<FailStudents2> fs2s = css.getFailStudents2Set();
			// for (FailStudents2 fs2 : fs2s) {
			// data = new String[6];
			// data[0] = className;
			// data[1] = fs2.getStudentNo();
			// studentName = "";
			// student = mm.findStudentByNo(data[1]);
			// if (student == null) {
			// graduate = mm.findGraduateByStudentNo(data[1]);
			// studentName = graduate.getStudentName();
			// } else
			// studentName = student.getStudentName();
			// data[2] = studentName;
			// data[3] = String.valueOf(fs2.getTotalCredits());
			// data[4] = String.valueOf(fs2.getTotalCredits()
			// - fs2.getPassCredits());
			// data[5] = df.format(fs2.getAverage() + 0.001F);
			// printData.add(data);
			// }
			// }
		}

		parameters.put("E", String.valueOf(printData.size()));
		byte[] bytes = null;
		if (!printData.isEmpty()) {
			bytes = JasperRunManager.runReportToPdf(jasperReport, parameters,
					new HibernateQueryResultDataSource(printData, fields));
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "報表製作已完成"));
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), param,
					new JREmptyDataSource());
		}
		JasperReportUtils.printPdfToFrontEnd(response, bytes);
		return messages;
	}

	/**
	 * 預覽期中期末1/2或2/3不及格學生名單作業(個人,導師使用)
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @param isPrint
	 *            列印與否
	 * @throws Exception
	 */
	private ActionMessages printMidtermFinalFailStudentsByClassResult(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm, boolean setupPrinter,
			boolean isPrint) throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential credential = (UserCredential) getUserCredential(request
				.getSession(false));
		ActionMessages messages = new ActionMessages();
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		boolean isFinalResult = "final".equalsIgnoreCase(request
				.getParameter("rt")); // 是否為期末?預設為期末成績
		String year = cm.getNowBy("School_year");
		String term = (String) aForm.get("sterm");

		Map<String, String> parameters = new HashMap<String, String>();
		String campusNo = aForm.getString("campusInCharge2").equals("1") ? "台北"
				: "新竹";
		parameters.put("A", campusNo);
		parameters.put("B", year);
		parameters.put("C", sterm);
		parameters.put("D", isFinalResult ? Examine.FINAL.toString()
				: Examine.MID.toString());
		String[] fields = { "studentNo", "studentName", "totalCredits",
				"failCredits", "avg", "studentNo1", "studentName1",
				"totalCredits1", "failCredits1", "avg1" };
		DecimalFormat df = new DecimalFormat(",##0.0");

		ServletContext context = request.getSession().getServletContext();
		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ credential.getMember().getIdno() + "FailByClass"
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File[] garbag = tempDir.listFiles();
		if (garbag.length > 0) {
			for (File f : garbag)
				f.delete();
		}

		File reportFile = new File(
				context.getRealPath("/WEB-INF/reports/MidtermFinalFailStudentsByClassResult.jasper"));
		if (!reportFile.exists()) {
			JasperReportUtils.initJasperReportsClasspath(request);
			JasperReportUtils
					.compileJasperReports(context
							.getRealPath("/WEB-INF/reports/MidtermFinalFailStudentsByClassResult.jrxml"));
			reportFile = new File(
					context.getRealPath("/WEB-INF/reports/MidtermFinalFailStudentsByClassResult.jasper"));
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"查無\"MidtermFinalFailStudentsByClassResult.jasper\"檔案，請電洽電算中心，謝謝！！");
		}
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());

		ClassScoreSummary summary = null;
		if (isFinalResult)
			summary = new ClassScoreSummary(year, term,
					processClassInfo(aForm), Examine.FINAL);
		else
			summary = new ClassScoreSummary(year, term,
					processClassInfo(aForm), Examine.MID);

		List<ClassScoreSummary> csss = sm
				.findClassScoreSummaryByLikeExpression(summary);

		String className = null;
		StdScore ss1 = null, ss2 = null;
		List<Object> printData = null;
		Map<ScoreStatus, Set<StdScore>> fails = null;
		StdScore[] ss1s = null, ss2s = null;
		Student student = null;
		int counts = 0;

		for (ClassScoreSummary css : csss) {
			printData = new ArrayList<Object>();
			className = Toolket.getClassFullName(css.getDepartClass());
			parameters.put("E", className);
			fails = calFailStudentsSet(css.getStdScoreSet());
			ss1s = (StdScore[]) fails.get(ScoreStatus.STATUS_2).toArray(
					new StdScore[0]);
			ss2s = (StdScore[]) fails.get(ScoreStatus.STATUS_3).toArray(
					new StdScore[0]);
			counts = ss1s.length >= ss2s.length ? ss1s.length : ss2s.length;
			if (counts == 0)
				continue;

			for (int i = 0; i < counts; i++) {
				String[] data = new String[10];
				try {
					ss1 = ss1s[i];
				} catch (Exception e) {
					ss1 = null;
				}
				try {
					ss2 = ss2s[i];
				} catch (Exception e) {
					ss2 = null;
				}

				if (ss1 != null) {
					data[0] = ss1.getStudentNo();
					student = mm.findStudentByNo(ss1.getStudentNo());
					if (student != null)
						data[1] = student.getStudentName();
					else
						data[1] = mm
								.findGraduateByStudentNo(ss1.getStudentNo())
								.getStudentName();

					data[2] = ss1.getTotalCredits().toString();
					data[3] = String.valueOf(ss1.getTotalCredits()
							- ss1.getPassCredits());
					data[4] = df.format(ss1.getAverage() + 0.001D);
				}

				if (ss2 != null) {
					data[5] = ss2.getStudentNo();
					student = mm.findStudentByNo(ss2.getStudentNo());
					if (student != null)
						data[6] = student.getStudentName();
					else
						data[6] = mm
								.findGraduateByStudentNo(ss2.getStudentNo())
								.getStudentName();
					data[7] = ss2.getTotalCredits().toString();
					data[8] = String.valueOf(ss2.getTotalCredits()
							- ss2.getPassCredits());
					data[9] = df.format(ss2.getAverage() + 0.001D);
				}
				printData.add(data);
			}

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameters,
					new HibernateQueryResultDataSource(printData, fields));
			OutputStream os = new BufferedOutputStream(new FileOutputStream(
					new File(tempDir, css.getDepartClass() + ".pdf")));
			JasperExportManager.exportReportToPdfStream(jasperPrint, os);
			os.close();
		}

		File[] files = tempDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".pdf");
			}
		});

		if (files.length > 0) {
			File printingPdf = mergePdfsForPrinting(files,
					new File(System.getProperty("java.io.tmpdir")),
					"ReportPrintingPDF.pdf");
			JasperReportUtils.printPdfToFrontEnd(response, printingPdf);
			printingPdf.delete();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "報表製作已完成"));
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), param,
					new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}

		tempDir.delete();
		return messages;
	}

	/**
	 * 預覽期中期末1/2或2/3不及格學生名單作業(個人,導師使用)
	 * 
	 * @commend 不用了
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @param isPrint
	 *            列印與否
	 * @throws Exception
	 */
	ActionMessages printMidtermFinalFailStudentsByClassResultBackup(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm, boolean setupPrinter,
			boolean isPrint) throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential credential = (UserCredential) getUserCredential(request
				.getSession(false));
		ActionMessages messages = new ActionMessages();
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		boolean isFinalResult = "final".equalsIgnoreCase(request
				.getParameter("rt")); // 是否為期末?預設為期末成績
		String year = cm.getNowBy("School_year");
		String term = (String) aForm.get("sterm");

		Map<String, String> parameters = new HashMap<String, String>();
		String campusNo = aForm.getString("campusInCharge2").equals("1") ? "台北"
				: "新竹";
		parameters.put("A", campusNo);
		parameters.put("B", year);
		parameters.put("C", sterm);
		parameters.put("D", isFinalResult ? Examine.FINAL.toString()
				: Examine.MID.toString());
		String[] fields = { "studentNo", "studentName", "totalCredits",
				"failCredits", "avg", "studentNo1", "studentName1",
				"totalCredits1", "failCredits1", "avg1" };
		DecimalFormat df = new DecimalFormat(",##0.0");

		ServletContext context = request.getSession().getServletContext();
		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ credential.getMember().getIdno() + "FailByClass"
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File[] garbag = tempDir.listFiles();
		if (garbag.length > 0) {
			for (File f : garbag)
				f.delete();
		}

		File reportFile = new File(
				context.getRealPath("/WEB-INF/reports/MidtermFinalFailStudentsByClassResult.jasper"));
		if (!reportFile.exists()) {
			JasperReportUtils.initJasperReportsClasspath(request);
			JasperReportUtils
					.compileJasperReports(context
							.getRealPath("/WEB-INF/reports/MidtermFinalFailStudentsByClassResult.jrxml"));
			reportFile = new File(
					context.getRealPath("/WEB-INF/reports/MidtermFinalFailStudentsByClassResult.jasper"));
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"查無\"MidtermFinalFailStudentsByClassResult.jasper\"檔案，請電洽電算中心，謝謝！！");
		}
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());

		ClassScoreSummary summary = null;
		if (isFinalResult)
			summary = new ClassScoreSummary(year, term,
					processClassInfo(aForm), Examine.FINAL);
		else
			summary = new ClassScoreSummary(year, term,
					processClassInfo(aForm), Examine.MID);

		List<ClassScoreSummary> csss = sm
				.findClassScoreSummaryByLikeExpression(summary);

		String className = null;
		FailStudents1 fs1 = null;
		FailStudents2 fs2 = null;
		List<Object> printData = null;
		FailStudents1[] fs1s = null;
		FailStudents2[] fs2s = null;
		Student student = null;
		int counts = 0;

		for (ClassScoreSummary css : csss) {
			printData = new ArrayList<Object>();
			className = Toolket.getClassFullName(css.getDepartClass());
			parameters.put("E", className);
			fs1s = (FailStudents1[]) css.getFailStudents1Set().toArray(
					new FailStudents1[0]);
			fs2s = (FailStudents2[]) css.getFailStudents2Set().toArray(
					new FailStudents2[0]);
			counts = fs1s.length >= fs2s.length ? fs1s.length : fs2s.length;
			if (counts == 0)
				continue;

			for (int i = 0; i < counts; i++) {
				String[] data = new String[10];
				try {
					fs1 = fs1s[i];
				} catch (Exception e) {
					fs1 = null;
				}
				try {
					fs2 = fs2s[i];
				} catch (Exception e) {
					fs2 = null;
				}

				if (fs1 != null) {
					data[0] = fs1.getStudentNo();
					student = mm.findStudentByNo(fs1.getStudentNo());
					if (student != null)
						data[1] = student.getStudentName();
					else
						data[1] = mm
								.findGraduateByStudentNo(fs1.getStudentNo())
								.getStudentName();

					data[2] = fs1.getTotalCredits().toString();
					data[3] = String.valueOf(fs1.getTotalCredits()
							- fs1.getPassCredits());
					data[4] = df.format(fs1.getAverage() + 0.001D);
				}

				if (fs2 != null) {
					data[5] = fs2.getStudentNo();
					student = mm.findStudentByNo(fs2.getStudentNo());
					if (student != null)
						data[6] = student.getStudentName();
					else
						data[6] = mm
								.findGraduateByStudentNo(fs2.getStudentNo())
								.getStudentName();
					data[7] = fs2.getTotalCredits().toString();
					data[8] = String.valueOf(fs2.getTotalCredits()
							- fs2.getPassCredits());
					data[9] = df.format(fs2.getAverage() + 0.001D);
				}
				printData.add(data);
			}

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameters,
					new HibernateQueryResultDataSource(printData, fields));
			OutputStream os = new BufferedOutputStream(new FileOutputStream(
					new File(tempDir, css.getDepartClass() + ".pdf")));
			JasperExportManager.exportReportToPdfStream(jasperPrint, os);
			os.close();
		}

		File[] files = tempDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".pdf");
			}
		});

		if (files.length > 0) {
			File printingPdf = mergePdfsForPrinting(files,
					new File(System.getProperty("java.io.tmpdir")),
					"ReportPrintingPDF.pdf");
			JasperReportUtils.printPdfToFrontEnd(response, printingPdf);
			printingPdf.delete();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "報表製作已完成"));
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), param,
					new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}

		tempDir.delete();
		return messages;
	}

	/**
	 * 預覽期中期末教師任教科目不及格人數統計表
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @param isPrint
	 *            列印與否
	 * @throws Exception
	 */
	private ActionMessages printMidtermFinalFailStudentsTooMuch(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm, boolean setupPrinter,
			boolean isPrint) throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		boolean isFinalResult = "final".equalsIgnoreCase(request
				.getParameter("rt")); // 是否為期末?預設為期末成績
		boolean isFail = "2".equals(request.getParameter("ft"));
		String year = cm.getNowBy("School_year");
		String term = (String) aForm.get("sterm");

		Map<String, String> parameters = new HashMap<String, String>();
		String campusNo = aForm.getString("campusInCharge2").equals("1") ? "台北"
				: "新竹";
		parameters.put("A", campusNo);
		parameters.put("B", year);
		parameters.put("C", sterm);
		parameters.put("D", isFinalResult ? Examine.FINAL.toString()
				: Examine.MID.toString());
		parameters.put("E", (isFail ? "1/2" : "2/3"));
		String[] fields = { "teacherName", "courseName", "className", "total",
				"pass", "fail", "avg" };
		DecimalFormat df = new DecimalFormat(",##0.00");

		ServletContext context = request.getSession().getServletContext();
		File reportFile = new File(
				context.getRealPath("/WEB-INF/reports/MidtermFinalFailStudentsTooMuch.jasper"));
		if (!reportFile.exists()) {
			JasperReportUtils.initJasperReportsClasspath(request);
			JasperReportUtils
					.compileJasperReports(context
							.getRealPath("/WEB-INF/reports/MidtermFinalFailStudentsTooMuch.jrxml"));
			reportFile = new File(
					context.getRealPath("/WEB-INF/reports/MidtermFinalFailStudentsTooMuch.jasper"));
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"查無\"MidtermFinalFailStudentsTooMuch.jasper\"檔案，請電洽電算中心，謝謝！！");
		}
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());

		List<ClassScoreSummary> csss = isFinalResult ? sm
				.findClassScoreSummaryByLikeExpression(new ClassScoreSummary(
						year, term, processClassInfo(aForm), Examine.FINAL))
				: sm.findClassScoreSummaryByLikeExpression(new ClassScoreSummary(
						year, term, processClassInfo(aForm), Examine.MID));

		List<Object> printData = new ArrayList<Object>();
		String className = null, percent = null;
		double cal = isFail ? 0.5D : 2D / 3D, score = 0.0d;
		int fail = 0, seldSize = 0;
		float passScore = 0.0f;
		Dtime d = null;
		String[] data = null;
		List<Dtime> dtimes = null;
		List<Seld> selds = null;
		boolean bFlag = false;

		for (ClassScoreSummary css : csss) {
			className = Toolket.getClassFullName(css.getDepartClass());
			passScore = Toolket.getPassScoreByDepartClass(css.getDepartClass());
			d = new Dtime();
			d.setSterm(term);
			d.setDepartClass(css.getDepartClass());
			dtimes = cm.findDtimeBy(d, "cscode");
			if (!isFinalResult)
				dtimes = processExcludedCourse2(sm, dtimes);

			if (!dtimes.isEmpty()) {
				for (Dtime dd : dtimes) {
					fail = 0;
					// 要以所有選課人數計算
					// selds = sm.findSeldByInputForm(dd.getDepartClass(), dd
					// .getCscode());
					selds = sm.findSeldsBy(dd.getOid());
					seldSize = selds.size();
					for (Seld seld : selds) {
						score = isFinalResult ? ((seld == null || seld
								.getScore() == null) ? 0.0d : seld.getScore())
								: ((seld == null || seld.getScore2() == null) ? 0.0d
										: seld.getScore2());
						// totalScore += score;
						if (score < passScore)
							fail++;
					}

					bFlag = ((double) fail / (double) seldSize) > cal;
					if (bFlag) {
						data = new String[7];
						// 沒教師ID就不Show
						if (StringUtils.isBlank(dd.getTechid()))
							continue;

						data[0] = mm.findEmployeeByIdno(dd.getTechid())
								.getName();
						data[1] = ((Csno) cm.getCsnameBy(dd.getCscode()).get(0))
								.getChiName();
						data[2] = className;
						data[3] = String.valueOf(seldSize);
						data[4] = String.valueOf(seldSize - fail);
						data[5] = String.valueOf(fail);
						percent = StringUtils
								.substringBefore(
										String.valueOf(Float.parseFloat(df
												.format(((double) fail / (double) seldSize) + 0.0001D)) * 100),
										".")
								+ "%";
						data[6] = percent;
						printData.add(data);

					}
				}
			}

		}

		byte[] bytes = null;
		if (!printData.isEmpty()) {
			bytes = JasperRunManager.runReportToPdf(jasperReport, parameters,
					new HibernateQueryResultDataSource(printData, fields));
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "報表製作已完成"));
		} else {
			Map<String, String> param = new HashMap<String, String>();
			// File image = new File(context
			// .getRealPath("/pages/images/2002chitS.jpg"));
			// param.put("IMAGE", image.getAbsolutePath());
			bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), param,
					new JREmptyDataSource());
		}

		JasperReportUtils.printPdfToFrontEnd(response, bytes);
		return messages;
	}

	/**
	 * 預覽學期成績雙1/2不及格名單
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @param isPrint
	 *            列印與否
	 * @throws Exception
	 */
	private ActionMessages printMidtermFinalFailTwiceStudentsResult(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm, boolean setupPrinter,
			boolean isPrint) throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		String year = cm.getNowBy("School_year");
		String term = (String) aForm.get("sterm");
		boolean is961 = "96".equals(year) && "1".equals(term);

		Map<String, String> parameters = new HashMap<String, String>();
		String campusNo = aForm.getString("campusInCharge2").equals("1") ? "台北"
				: "新竹";
		parameters.put("A", campusNo);
		parameters.put("B", year);
		parameters.put("C", sterm);
		String[] fields = { "className", "studentNo", "studentName", "total1",
				"notPass1", "total2", "notPass2" };

		ServletContext context = request.getSession().getServletContext();
		File reportFile = new File(
				context.getRealPath("/WEB-INF/reports/MidtermFinalFailTwiceStudentsResult.jasper"));
		if (!reportFile.exists()) {
			JasperReportUtils.initJasperReportsClasspath(request);
			JasperReportUtils
					.compileJasperReports(context
							.getRealPath("/WEB-INF/reports/MidtermFinalFailTwiceStudentsResult.jrxml"));
			reportFile = new File(
					context.getRealPath("/WEB-INF/reports/MidtermFinalFailTwiceStudentsResult.jasper"));
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"查無\"MidtermFinalFailTwiceStudentsResult.jasper\"檔案，請電洽電算中心，謝謝！！");
		}
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());

		String classInfo = processClassInfo(aForm);
		ClassScoreSummary summary = new ClassScoreSummary(year, term,
				classInfo, Examine.FINAL);
		List<ClassScoreSummary> nowFail = sm
				.findClassScoreSummaryByLikeExpression(summary);
		if ("1".equals(term)) {
			summary = new ClassScoreSummary(String.valueOf(Integer
					.parseInt(year) - 1), "2", classInfo, Examine.FINAL);
		} else
			summary = new ClassScoreSummary(year, "1", classInfo, Examine.FINAL);

		List<Object> printData = new ArrayList<Object>();
		String[] studentNos = new String[0];
		Map<String, Float[]> oldData = null; // 保留舊學分資料,免得下面要重查
		if (is961) {
			// 只有96.1才使用,因為只轉了95.2之1/2不及格學生資料
			List<FailStudentsHist> failStudentsHists = sm
					.findFailStudentsHistBy(new FailStudentsHist());
			for (FailStudentsHist hist : failStudentsHists) {
				studentNos = (String[]) ArrayUtils.add(studentNos,
						hist.getStudentNo());
			}
		} else {
			List<ClassScoreSummary> oldFail = sm
					.findClassScoreSummaryByLikeExpression(summary);
			oldData = new HashMap<String, Float[]>();
			Float[] tmp = null;
			for (ClassScoreSummary css : oldFail) {
				for (StdScore ss : css.getStdScoreSet()) {
					if (ss.getStatus() == ScoreStatus.STATUS_2
							|| ss.getStatus() == ScoreStatus.STATUS_3) {
						studentNos = (String[]) ArrayUtils.add(studentNos,
								ss.getStudentNo());
						tmp = new Float[2];
						tmp[0] = ss.getTotalCredits();
						tmp[1] = ss.getPassCredits();
						oldData.put(ss.getStudentNo(), tmp);
					}
				}
			}
		}
		Arrays.sort(studentNos);

		int i = 0;
		String className = null, studentNo = null;
		String[] data = null;
		Float[] oldCredits = null;
		Map<ScoreStatus, Set<StdScore>> failStudents = null;
		Set<StdScore> fs1 = null, fs2 = null;
		Student student = null;

		for (ClassScoreSummary css : nowFail) {
			className = Toolket.getClassFullName(css.getDepartClass());
			failStudents = calFailStudentsSet(css.getStdScoreSet());
			fs1 = failStudents.get(ScoreStatus.STATUS_2);
			fs2 = failStudents.get(ScoreStatus.STATUS_3);
			for (StdScore ss : fs1) {
				studentNo = ss.getStudentNo();
				if (Arrays.binarySearch(studentNos, studentNo) >= 0) {
					data = new String[7];
					data[0] = className;
					data[1] = studentNo;
					student = mm.findStudentByNo(studentNo);
					if (student != null)
						data[2] = student.getStudentName();
					else
						data[2] = mm.findGraduateByStudentNo(studentNo)
								.getStudentName();
					oldCredits = oldData.get(studentNo);
					data[3] = oldCredits[0].toString();
					data[4] = String.valueOf(oldCredits[0].floatValue()
							- (oldCredits[1]).floatValue());
					data[5] = ss.getTotalCredits().toString();
					data[6] = String.valueOf(ss.getTotalCredits().floatValue()
							- ss.getPassCredits().floatValue());
					i++;
					printData.add(data);
				}
			}

			// 本學期2/3學生也要比對
			for (StdScore ss : fs2) {
				studentNo = ss.getStudentNo();
				if (Arrays.binarySearch(studentNos, studentNo) >= 0) {
					data = new String[7];
					data[0] = className;
					data[1] = studentNo;
					student = mm.findStudentByNo(studentNo);
					if (student != null) {
						data[2] = student.getStudentName();
					} else {
						data[2] = mm.findGraduateByStudentNo(studentNo)
								.getStudentName();
					}

					// 原住民
					if (student.getIdent().equals("5")) {
						data[2] = data[2] + "*";
					}

					oldCredits = oldData.get(studentNo);
					data[3] = oldCredits[0].toString();
					data[4] = String.valueOf(oldCredits[0].floatValue()
							- (oldCredits[1]).floatValue());
					data[5] = ss.getTotalCredits().toString();
					data[6] = String.valueOf(ss.getTotalCredits().floatValue()
							- ss.getPassCredits().floatValue());
					i++;
					printData.add(data);
				}
			}
		}
		parameters.put("E", String.valueOf(i));

		byte[] bytes = null;
		if (!printData.isEmpty()) {
			bytes = JasperRunManager.runReportToPdf(jasperReport, parameters,
					new HibernateQueryResultDataSource(printData, fields));
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "報表製作已完成"));
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), param,
					new JREmptyDataSource());
		}
		JasperReportUtils.printPdfToFrontEnd(response, bytes);
		return messages;
	}

	/**
	 * 預覽學期成績雙1/2不及格名單
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @param isPrint
	 *            列印與否
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	ActionMessages printMidtermFinalFailTwiceStudentsResult1(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm, boolean setupPrinter,
			boolean isPrint) throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		String year = cm.getNowBy("School_year");
		String term = (String) aForm.get("sterm");
		boolean is961 = "96".equals(year) && "1".equals(term);

		Map<String, String> parameters = new HashMap<String, String>();
		String campusNo = aForm.getString("campusInCharge2").equals("1") ? "台北"
				: "新竹";
		parameters.put("A", campusNo);
		parameters.put("B", year);
		parameters.put("C", sterm);
		String[] fields = { "className", "studentNo", "studentName", "total1",
				"notPass1", "total2", "notPass2" };

		ServletContext context = request.getSession().getServletContext();
		File reportFile = new File(
				context.getRealPath("/WEB-INF/reports/MidtermFinalFailTwiceStudentsResult.jasper"));
		if (!reportFile.exists()) {
			JasperReportUtils.initJasperReportsClasspath(request);
			JasperReportUtils
					.compileJasperReports(context
							.getRealPath("/WEB-INF/reports/MidtermFinalFailTwiceStudentsResult.jrxml"));
			reportFile = new File(
					context.getRealPath("/WEB-INF/reports/MidtermFinalFailTwiceStudentsResult.jasper"));
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"查無\"MidtermFinalFailTwiceStudentsResult.jasper\"檔案，請電洽電算中心，謝謝！！");
		}
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());

		String classInfo = processClassInfo(aForm);
		ClassScoreSummary summary = new ClassScoreSummary(year, term,
				classInfo, Examine.FINAL);
		List<ClassScoreSummary> nowFail = sm
				.findClassScoreSummaryByLikeExpression(summary);
		if ("1".equals(term)) {
			summary = new ClassScoreSummary(String.valueOf(Integer
					.parseInt(year) - 1), "2", classInfo, Examine.FINAL);
		} else
			summary = new ClassScoreSummary(year, "1", classInfo, Examine.FINAL);

		List<Object> printData = new ArrayList<Object>();
		String[] studentNos = new String[0];
		if (is961) {
			// 只有96.1才使用,因為只轉了95.2之1/2不及格學生資料
			List<FailStudentsHist> failStudentsHists = sm
					.findFailStudentsHistBy(new FailStudentsHist());
			for (FailStudentsHist hist : failStudentsHists) {
				studentNos = (String[]) ArrayUtils.add(studentNos,
						hist.getStudentNo());
			}
		} else {
			List<ClassScoreSummary> oldFail = sm
					.findClassScoreSummaryByLikeExpression(summary);
			for (ClassScoreSummary css : oldFail) {
				for (FailStudents1 fs1 : css.getFailStudents1Set()) {
					studentNos = (String[]) ArrayUtils.add(studentNos,
							fs1.getStudentNo());
				}
				for (FailStudents2 fs2 : css.getFailStudents2Set()) {
					studentNos = (String[]) ArrayUtils.add(studentNos,
							fs2.getStudentNo());
				}
			}
		}
		Arrays.sort(studentNos);

		int i = 0;
		String className = null, studentNo = null;
		String[] data = null;
		FailStudents1 failStd1 = null;
		FailStudents2 failStd2 = null;
		Map<ScoreStatus, Set<StdScore>> failStudents = null;
		Set<StdScore> fs1 = null, fs2 = null;
		List failStudent1 = null;
		Map<String, Object> ret = null;

		for (ClassScoreSummary css : nowFail) {
			className = Toolket.getClassFullName(css.getDepartClass());
			failStudents = calFailStudentsSet(css.getStdScoreSet());
			fs1 = failStudents.get(ScoreStatus.STATUS_2);
			fs2 = failStudents.get(ScoreStatus.STATUS_3);
			for (StdScore ss : fs1) {
				studentNo = ss.getStudentNo();
				if (Arrays.binarySearch(studentNos, studentNo) >= 0) {
					data = new String[7];
					failStd1 = new FailStudents1();
					failStd1.setStudentNo(studentNo);
					failStudent1 = sm.findFailStudentsBy(failStd1);
					if (!failStudent1.isEmpty()) {
						ret = (Map) failStudent1.get(0);
					} else {
						failStd2 = new FailStudents2();
						failStd2.setStudentNo(studentNo);
						failStudent1 = sm.findFailStudentsBy(failStd2);
						if (!failStudent1.isEmpty()) {
							ret = (Map) failStudent1.get(0);
						}
					}

					data[0] = className;
					data[1] = studentNo;
					data[2] = mm.findStudentByNo(studentNo).getStudentName();
					data[3] = ret.get("TotalCredits").toString();
					data[4] = String.valueOf(((Float) ret.get("TotalCredits"))
							.floatValue()
							- ((Float) ret.get("PassCredits")).floatValue());
					data[5] = ss.getTotalCredits().toString();
					data[6] = String.valueOf(ss.getTotalCredits().floatValue()
							- ss.getPassCredits().floatValue());
					i++;
					printData.add(data);
				}
			}

			// 本學期2/3學生也要比對
			for (StdScore ss : fs2) {
				studentNo = ss.getStudentNo();
				if (Arrays.binarySearch(studentNos, studentNo) >= 0) {
					data = new String[7];
					failStd1 = new FailStudents1();
					failStd1.setStudentNo(studentNo);
					failStudent1 = sm.findFailStudentsBy(failStd1);
					if (!failStudent1.isEmpty()) {
						ret = (Map) failStudent1.get(0);
					} else {
						failStd2 = new FailStudents2();
						failStd2.setStudentNo(studentNo);
						failStudent1 = sm.findFailStudentsBy(failStd2);
						if (!failStudent1.isEmpty()) {
							ret = (Map) failStudent1.get(0);
						}
					}

					data[0] = className;
					data[1] = studentNo;
					data[2] = mm.findStudentByNo(studentNo).getStudentName();
					data[3] = ret.get("TotalCredits").toString();
					data[4] = String.valueOf(((Float) ret.get("TotalCredits"))
							.floatValue()
							- ((Float) ret.get("PassCredits")).floatValue());
					data[5] = ss.getTotalCredits().toString();
					data[6] = String.valueOf(ss.getTotalCredits().floatValue()
							- ss.getPassCredits().floatValue());
					i++;
					printData.add(data);
				}
			}

		}
		parameters.put("E", String.valueOf(i));

		byte[] bytes = null;
		if (!printData.isEmpty()) {
			bytes = JasperRunManager.runReportToPdf(jasperReport, parameters,
					new HibernateQueryResultDataSource(printData, fields));
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "報表製作已完成"));
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), param,
					new JREmptyDataSource());
		}
		JasperReportUtils.printPdfToFrontEnd(response, bytes);
		return messages;
	}

	/**
	 * 應屆畢業生平均成績表
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @param isPrint
	 *            列印與否
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private ActionMessages printGraduatingScoreAvg(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm, boolean setupPrinter,
			boolean isPrint) throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		String year = cm.getNowBy("School_year");
		String rankType = request.getParameter("rank"); // 班or系排名
		String num = request.getParameter("tn"); // 要顯示的學期數
		boolean isShowAllTerms = StringUtils.isBlank(num);
		boolean isPdf = "pdf".equalsIgnoreCase(request.getParameter("rf"));
		// GraduatingScoreAvg.xls

		List<Student> students = Collections.EMPTY_LIST;
		List<Stavg> stavgs = null;
		Map<String, String> parameters = null;
		List<Object> printData = null;
		Object[] data = null;
		String[] fields = { "studentNo", "studentName", "score1", "score2",
				"score3", "score4", "score5", "score6", "score7", "score8",
				"totalScore", "avg", "rank", "percent" };
		List<JasperPrint> jasperPrints = new LinkedList<JasperPrint>();
		DecimalFormat df = new DecimalFormat(",##0.00");

		ServletContext context = request.getSession().getServletContext();
		List<Clazz> clazzes = sm.findClassBy(
				new Clazz(processClassInfo(aForm)),
				credential.getClassInChargeAry(), false);
		if (!clazzes.isEmpty()) {

			if (isPdf) {
				File reportFile = new File(
						context.getRealPath("/WEB-INF/reports/GraduatingScoreAvg.jasper"));
				// 需要時再編譯即可
				if (!reportFile.exists()) {
					JasperReportUtils.initJasperReportsClasspath(request);
					JasperReportUtils
							.compileJasperReports(context
									.getRealPath("/WEB-INF/reports/GraduatingScoreAvg.jrxml"));
					reportFile = new File(
							context.getRealPath("/WEB-INF/reports/GraduatingScoreAvg.jasper"));
					if (!reportFile.exists())
						throw new JRRuntimeException(
								"查無\"GraduatingScoreAvg.jasper\"檔案，請電洽電算中心，謝謝！！");
				}
				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());

				String classNo = null, studentNo = null, score = null;
				int index = 0, rank = 0;
				Float totalScore = 0.0F;
				double d = 0.0d;

				String[] avgsForRank = null;
				Object[] data1 = null;
				Double[] avgsForRank1 = null;

				if ("C".equalsIgnoreCase(rankType)) {
					for (Clazz clazz : clazzes) {
						classNo = clazz.getClassNo();
						if (!Toolket.isLiteracyClass(classNo)
								&& Toolket.chkIsGraduateClass(classNo)) {
							students = mm.findStudentsByClassNo(classNo);
							if (students.isEmpty())
								continue;

							parameters = new HashMap<String, String>();
							parameters.put("A", year);
							parameters.put("B", sterm);
							parameters.put("C",
									Toolket.getClassFullName(classNo));
							parameters
									.put("D", String.valueOf(students.size()));

							printData = new ArrayList<Object>();
							avgsForRank = new String[0];
							avgsForRank1 = new Double[0];
							for (Student student : students) {
								data = new Object[14];
								studentNo = student.getStudentNo();
								data[0] = studentNo;
								data[1] = student.getStudentName();
								stavgs = sm.findStavgBy(new Stavg(studentNo));
								index = 2;
								totalScore = 0.0F;
								for (Stavg stavg : stavgs) {
									if (!isShowAllTerms)
										// +2是因為index從2開始
										if ((Integer.parseInt(num) + 2) == index)
											break;

									score = df
											.format(stavg.getScore() + 0.0001F);
									data[index++] = score;
									totalScore += Float.valueOf(score);
								}
								data[10] = df.format(totalScore + 0.0001F);
								if (!isShowAllTerms)
									data[11] = df.format((totalScore / Integer
											.parseInt(num)) + 0.0001F);
								else
									data[11] = df.format((totalScore / stavgs
											.size()) + 0.0001F);
								avgsForRank = (String[]) ArrayUtils.add(
										avgsForRank, data[11]);
								avgsForRank1 = (Double[]) ArrayUtils.add(
										avgsForRank1,
										Double.valueOf((String) data[11]));
								printData.add(data);
							}

							// Arrays.sort(avgsForRank);
							// int studentCount = students.size();
							// for (int i = 0; i < printData.size(); i++) {
							// data1 = (Object[]) printData.get(i);
							// rank = studentCount
							// - Arrays.binarySearch(avgsForRank,
							// (String) data1[11]);
							// data1[12] = String.valueOf(rank);
							// d = ((double) rank / (double) studentCount) * 100
							// +
							// 0.0001D;
							// data1[13] = df.format(d);
							// }

							Arrays.sort(avgsForRank1);
							String[] x = new String[avgsForRank1.length];
							for (int i = 0; i < avgsForRank1.length; i++)
								x[i] = avgsForRank1[i].toString();
							int studentCount = students.size();
							for (int i = 0; i < printData.size(); i++) {
								data1 = (Object[]) printData.get(i);
								String tmpScore = (String) data1[11];
								// 要去掉尾數0,不然判斷有問題
								tmpScore = "0".equals(StringUtils.substring(
										tmpScore, tmpScore.length() - 1)) ? StringUtils
										.substring(tmpScore, 0,
												tmpScore.length() - 1)
										: tmpScore;
								// 明明index=0結果變-,只好寫code變index=0,Arrays.binarySearch
								// BUG
								rank = Math.abs(Arrays
										.binarySearch(x, tmpScore)) == studentCount ? studentCount
										: studentCount
												- Arrays.binarySearch(x,
														tmpScore);
								// rank = studentCount
								// - Arrays.binarySearch(x, tmpScore);
								// rank = studentCount
								// - ArrayUtils.indexOf(avgsForRank1,
								// Double.valueOf((String) data[11]));
								data1[12] = String.valueOf(rank);
								d = ((double) rank / (double) studentCount) * 100 + 0.0001D;
								data1[13] = df.format(d);
							}

							// E要放剛好50%的名次
							parameters.put("E", String.valueOf(Math
									.round((double) studentCount / 2.0D)));
							JasperPrint jasperPrint = JasperFillManager
									.fillReport(jasperReport, parameters,
											new HibernateQueryResultDataSource(
													printData, fields));
							jasperPrints.add(jasperPrint);
						}
					}
				} else {
					List<Student> allDeptStudents = new LinkedList<Student>();
					classNo = null;
					for (Clazz clazz : clazzes) {
						classNo = clazz.getClassNo();
						if (!Toolket.isLiteracyClass(classNo)
								&& Toolket.chkIsGraduateClass(classNo)) {
							allDeptStudents.addAll(mm
									.findStudentsByClassNo(classNo));
						}
					}
					parameters = new HashMap<String, String>();
					parameters.put("A", year);
					parameters.put("B", sterm);
					parameters.put("C", Toolket.getDepartName(classNo));
					parameters.put("D", String.valueOf(allDeptStudents.size()));

					printData = new ArrayList<Object>();
					String[] totals = new String[0];
					for (Student student : allDeptStudents) {
						data = new Object[14];
						studentNo = student.getStudentNo();
						data[0] = studentNo;
						data[1] = student.getStudentName();
						stavgs = sm.findStavgBy(new Stavg(studentNo));
						index = 2;
						totalScore = 0.0F;
						for (Stavg stavg : stavgs) {
							if (!isShowAllTerms)
								// +2是因為index從2開始
								if ((Integer.parseInt(num) + 2) == index)
									break;

							score = df.format(stavg.getScore() + 0.0001F);
							data[index++] = score;
							totalScore += Float.valueOf(score);
						}
						data[10] = df.format(totalScore + 0.0001F);
						if (!isShowAllTerms)
							data[11] = df.format((totalScore / Integer
									.parseInt(num)) + 0.0001F);
						else
							data[11] = df
									.format((totalScore / stavgs.size()) + 0.0001F);

						totals = (String[]) ArrayUtils.add(totals, data[10]);
						printData.add(data);
					}

					Arrays.sort(totals);
					int studentCount = allDeptStudents.size();
					for (int i = 0; i < printData.size(); i++) {
						data1 = (Object[]) printData.get(i);
						rank = studentCount
								- Arrays.binarySearch(totals,
										(String) data1[10]);
						data1[12] = String.valueOf(rank);
						d = ((double) rank / (double) studentCount) * 100 + 0.0001D;
						data1[13] = df.format(d);
					}

					// E要放剛好50%的名次
					parameters.put("E", String.valueOf(Math
							.round((double) studentCount / 2.0D)));
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							jasperReport, parameters,
							new HibernateQueryResultDataSource(printData,
									fields));
					jasperPrints.add(jasperPrint);
				}

				if (!jasperPrints.isEmpty()) {
					JRPdfExporter exporter = new JRPdfExporter();
					exporter.setParameter(
							JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
					exporter.setParameter(
							JRExporterParameter.OUTPUT_FILE_NAME,
							context.getRealPath("/WEB-INF/reports/RankingList.pdf"));
					exporter.exportReport();
					File output = new File(
							context.getRealPath("/WEB-INF/reports/RankingList.pdf"));
					JasperReportUtils.printXlsToFrontEnd(response, output);
				} else {
					parameters = new HashMap<String, String>();
					File image = new File(
							context.getRealPath("/pages/reports/2002chitS.jpg"));
					parameters.put("IMAGE", image.getAbsolutePath());
					byte[] bytes = JasperRunManager.runReportToPdf(
							JasperReportUtils.getNoResultReport(context),
							parameters, new JREmptyDataSource());
					JasperReportUtils.printPdfToFrontEnd(response, bytes);
				}
			} else {
				// EXCEL
				File templateXLS = new File(
						context.getRealPath("/WEB-INF/reports/GraduatingScoreAvg.xls"));
				HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
				HSSFSheet sheet = workbook.getSheetAt(0);

				String classNo = null, score = null, avgScore = null, tmpScore = null;
				int index = 4, colIndex = 2, studentsCount = 0, rank = 0;
				Float totalScore = 0.0F;
				Double d = 0.0D;
				String[] avgsForRank = null;
				Double[] avgsForRank1 = null;

				if ("C".equalsIgnoreCase(rankType)) {
					for (Clazz clazz : clazzes) {

						classNo = clazz.getClassNo();
						if (!Toolket.isLiteracyClass(classNo)
								&& Toolket.chkIsGraduateClass(classNo)) {

							students = mm.findStudentsByClassNo(classNo);
							if (students.isEmpty())
								continue;

							studentsCount = students.size();
							Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year
									+ "學年度第" + sterm + "學期應屆畢業生平均成績表");
							Toolket.setCellValue(
									sheet,
									0,
									12,
									"日期："
											+ new SimpleDateFormat("yyyy/MM/dd")
													.format(new Date()));
							Toolket.setCellValue(sheet, 1, 1,
									"班級：" + Toolket.getClassFullName(classNo));
							Toolket.setCellValue(sheet, 1, 6, "學生人數："
									+ studentsCount);
							Toolket.setCellValue(
									sheet,
									1,
									10,
									"百分之五十："
											+ String.valueOf(Math
													.round((double) students
															.size() / 2.0D)));

							index = 4;
							avgsForRank = new String[0];
							avgsForRank1 = new Double[0];
							for (Student student : students) {

								Toolket.setCellValue(sheet, index, 0, student
										.getStudentNo().toUpperCase());
								Toolket.setCellValue(sheet, index, 1, student
										.getStudentName().trim());

								stavgs = sm.findStavgBy(new Stavg(student
										.getStudentNo()));

								colIndex = 2;
								totalScore = 0.0F;
								for (Stavg stavg : stavgs) {
									if (!isShowAllTerms)
										// +2是因為index從2開始
										if ((Integer.parseInt(num) + 2) == colIndex)
											break;

									score = df
											.format(stavg.getScore() + 0.0001F);
									Toolket.setCellValue(sheet, index,
											colIndex++, score);
									totalScore += Float.valueOf(score);
								}

								Toolket.setCellValue(sheet, index, 10,
										df.format(totalScore + 0.0001F));

								if (!isShowAllTerms) {
									avgScore = df.format((totalScore / Integer
											.parseInt(num)) + 0.0001F);
									Toolket.setCellValue(sheet, index, 11,
											avgScore);
									avgsForRank = (String[]) ArrayUtils.add(
											avgsForRank, avgScore);
									avgsForRank1 = (Double[]) ArrayUtils.add(
											avgsForRank1,
											Double.valueOf(avgScore));
								} else {
									avgScore = df.format((totalScore / stavgs
											.size()) + 0.0001F);
									Toolket.setCellValue(sheet, index, 11,
											avgScore);
									avgsForRank = (String[]) ArrayUtils.add(
											avgsForRank, avgScore);
									avgsForRank1 = (Double[]) ArrayUtils.add(
											avgsForRank1,
											Double.valueOf(avgScore));
								}

								index++;
							}

							index = 4;
							Arrays.sort(avgsForRank1);
							String[] x = new String[avgsForRank1.length];
							for (int i = 0; i < avgsForRank1.length; i++)
								x[i] = avgsForRank1[i].toString();

							for (int i = 0; i < studentsCount; i++) {
								tmpScore = Toolket.getCellValue(sheet, i + 4,
										11);
								// 要去掉尾數0,不然判斷有問題
								tmpScore = "0".equals(StringUtils.substring(
										tmpScore, tmpScore.length() - 1)) ? StringUtils
										.substring(tmpScore, 0,
												tmpScore.length() - 1)
										: tmpScore;
								// 明明index=0結果變-,只好寫code變index=0,Arrays.binarySearch
								// BUG
								rank = Math.abs(Arrays
										.binarySearch(x, tmpScore)) == studentsCount ? studentsCount
										: studentsCount
												- Arrays.binarySearch(x,
														tmpScore);
								Toolket.setCellValue(sheet, index, 12,
										String.valueOf(rank));
								d = ((double) rank / (double) studentsCount) * 100 + 0.0001D;
								Toolket.setCellValue(sheet, index++, 13,
										df.format(d));
							}
						}
					}
				} else {
					List<Student> allDeptStudents = new LinkedList<Student>();
					String studentNo = null;
					classNo = null;
					for (Clazz clazz : clazzes) {
						classNo = clazz.getClassNo();
						if (!Toolket.isLiteracyClass(classNo)
								&& Toolket.chkIsGraduateClass(classNo)) {
							allDeptStudents.addAll(mm
									.findStudentsByClassNo(classNo));
						}
					}

					studentsCount = students.size();
					Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第"
							+ sterm + "學期應屆畢業生平均成績表");
					Toolket.setCellValue(
							sheet,
							0,
							12,
							"日期："
									+ new SimpleDateFormat("yyyy/MM/dd")
											.format(new Date()));
					Toolket.setCellValue(sheet, 1, 1,
							"系所：" + Toolket.getDepartName(classNo));
					Toolket.setCellValue(sheet, 1, 6,
							"學生人數：" + allDeptStudents.size());
					Toolket.setCellValue(
							sheet,
							1,
							10,
							"百分之五十："
									+ String.valueOf(Math
											.round((double) allDeptStudents
													.size() / 2.0D)));
					index = 4;
					String[] totals = new String[0];

					for (Student student : allDeptStudents) {

						studentNo = student.getStudentNo();
						Toolket.setCellValue(sheet, index, 0, student
								.getStudentNo().toUpperCase());
						Toolket.setCellValue(sheet, index, 1, student
								.getStudentName().trim());
						stavgs = sm.findStavgBy(new Stavg(studentNo));

						colIndex = 2;
						totalScore = 0.0F;
						totalScore = 0.0F;
						for (Stavg stavg : stavgs) {
							if (!isShowAllTerms)
								// +2是因為index從2開始
								if ((Integer.parseInt(num) + 2) == colIndex)
									break;

							score = df.format(stavg.getScore() + 0.0001F);
							Toolket.setCellValue(sheet, index, colIndex++,
									score);
							totalScore += Float.valueOf(score);
						}

						Toolket.setCellValue(sheet, index, 10,
								df.format(totalScore + 0.0001F));
						if (!isShowAllTerms) {
							avgScore = df.format((totalScore / Integer
									.parseInt(num)) + 0.0001F);
							Toolket.setCellValue(sheet, index, 11, avgScore);
						} else {
							avgScore = df
									.format((totalScore / stavgs.size()) + 0.0001F);
							Toolket.setCellValue(sheet, index, 11, avgScore);
						}

						totals = (String[]) ArrayUtils.add(totals, avgScore);
						index++;
					}

					index = 4;
					Arrays.sort(totals);
					studentsCount = allDeptStudents.size();
					for (int i = 0; i < studentsCount; i++) {

						rank = studentsCount
								- Arrays.binarySearch(totals,
										Toolket.getCellValue(sheet, i + 4, 11));
						Toolket.setCellValue(sheet, index, 12,
								String.valueOf(rank));
						d = ((double) rank / (double) studentsCount) * 100 + 0.0001D;
						Toolket.setCellValue(sheet, index++, 13, df.format(d));
					}
				}

				File tempDir = new File(
						context.getRealPath("/WEB-INF/reports/temp/"
								+ getUserCredential(session).getMember()
										.getIdno()
								+ (new SimpleDateFormat("yyyyMMdd")
										.format(new java.util.Date()))));
				if (!tempDir.exists())
					tempDir.mkdirs();

				File output = new File(tempDir, "GraduatingScoreAvg.xls");
				FileOutputStream fos = new FileOutputStream(output);
				workbook.write(fos);
				fos.close();

				JasperReportUtils.printXlsToFrontEnd(response, output);
				output.delete();
				tempDir.delete();
			}
		} else {
			parameters = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/reports/2002chitS.jpg"));
			parameters.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), parameters,
					new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}

		return null;
	}

	/**
	 * 預覽期中期末全班成績統計單作業
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @param isPrint
	 *            列印與否
	 * @throws Exception
	 */
	private ActionMessages printMidtermFinalStatistic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm, boolean setupPrinter,
			boolean isPrint) throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		boolean isFinalResult = "final".equalsIgnoreCase(request
				.getParameter("rt")); // 是否為期末?預設為期末成績
		String year = cm.getNowBy("School_year");
		String term = (String) aForm.get("sterm");

		Map<String, String> parameters = new HashMap<String, String>();
		String campusNo = aForm.getString("campusInCharge2").equals("1") ? "台北"
				: "新竹";
		parameters.put("A", campusNo);
		parameters.put("B", year);
		parameters.put("C", sterm);
		parameters.put("D", isFinalResult ? Examine.FINAL.toString()
				: Examine.MID.toString());
		String[] fields = { "className", "courseName", "teacherName", "opt",
				"range1", "range2", "range3", "range4", "range5", "pass",
				"notPass", "avg" };

		ServletContext context = request.getSession().getServletContext();
		File reportFile = new File(
				context.getRealPath("/WEB-INF/reports/MidtermFinalStatistic.jasper"));
		if (!reportFile.exists()) {
			JasperReportUtils.initJasperReportsClasspath(request);
			JasperReportUtils
					.compileJasperReports(context
							.getRealPath("/WEB-INF/reports/MidtermFinalStatistic.jrxml"));
			reportFile = new File(
					context.getRealPath("/WEB-INF/reports/MidtermFinalStatistic.jasper"));
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"查無\"MidtermFinalStatistic.jasper\"檔案，請電洽電算中心，謝謝！！");
		}
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());

		DecimalFormat df = new DecimalFormat(",##0.0");
		ClassScoreSummary summary = null;
		if (isFinalResult)
			summary = new ClassScoreSummary(year, term,
					processClassInfo(aForm), Examine.FINAL);
		else
			summary = new ClassScoreSummary(year, term,
					processClassInfo(aForm), Examine.MID);
		List<ClassScoreSummary> csss = sm
				.findClassScoreSummaryByLikeExpression(summary);
		List<Object> printData = new ArrayList<Object>();
		Dtime d = null;
		Employee employee = null;
		DEmpl dempl = null;
		String teacherName = null, className = null;
		String[] data = null;
		Set<ScoreStatistic> sss = null;

		for (ClassScoreSummary css : csss) {
			className = Toolket.getClassFullName(css.getDepartClass());
			sss = css.getScoreStatisticSet();
			for (ScoreStatistic ss : sss) {
				if (ArrayUtils.contains(EXCLUDED_CSCODE, ss.getCscode()))
					continue;

				// if (ss.getAverage().toString().equals("0.0"))
				// continue;

				d = cm.findDtimeBy(
						new Dtime(css.getDepartClass(), ss.getCscode(), term),
						"cscode").get(0);
				data = new String[12];
				data[0] = className;
				data[1] = ((Csno) cm.getCsnameBy(ss.getCscode()).get(0))
						.getChiName();
				if (StringUtils.isNotBlank(d.getTechid())) {
					employee = mm.findEmployeeByIdno(d.getTechid());
					if (employee == null) {
						if (StringUtils.isBlank(d.getTechid())) {
							teacherName = "";
						} else {
							dempl = mm.findDEmplBy(new DEmpl(d.getTechid()))
									.get(0);
							teacherName = dempl.getCname();
						}
					} else
						teacherName = employee.getName();
				} else
					teacherName = "";

				data[2] = teacherName;
				data[3] = Toolket.getCourseOpt(d.getOpt());
				data[4] = ss.getRange1() == null ? "0" : ss.getRange1()
						.toString();
				data[5] = ss.getRange2() == null ? "0" : ss.getRange2()
						.toString();
				data[6] = ss.getRange3() == null ? "0" : ss.getRange3()
						.toString();
				data[7] = ss.getRange4() == null ? "0" : ss.getRange4()
						.toString();
				data[8] = ss.getRange5() == null ? "0" : ss.getRange5()
						.toString();
				data[9] = ss.getPass().toString();
				data[10] = ss.getFail().toString();
				data[11] = df.format(ss.getAverage() + 0.001D);
				printData.add(data);
			}
		}

		byte[] bytes = null;
		if (!printData.isEmpty()) {
			bytes = JasperRunManager.runReportToPdf(jasperReport, parameters,
					new HibernateQueryResultDataSource(printData, fields));
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "報表製作已完成"));
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), param,
					new JREmptyDataSource());
		}
		JasperReportUtils.printPdfToFrontEnd(response, bytes);

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Course.errorN1", "報表製作已完成"));
		return messages;
	}

	/**
	 * 學優獎學金名冊
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 */
	@SuppressWarnings("unchecked")
	private void printScholarshipStudentsList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		String year = cm.getNowBy("School_year");
		String term = aForm.getString("sterm");
		String scholarshipType = request.getParameter("sst");
		boolean isPdf = "pdf".equalsIgnoreCase(request.getParameter("rf"));

		Map<String, String> parameters = new HashMap<String, String>();
		String campusNo = aForm.getString("campusInCharge2").equals("1") ? "台北"
				: "新竹";
		parameters.put("A", campusNo);
		parameters.put("B", year);
		parameters.put("C", sterm);
		boolean isHistory = !sterm.equals(am
				.findTermBy(IConstants.PARAMETER_SCHOOL_TERM));
		String[] fields = { "className", "studentNo", "studentName", "rank",
				"score", "behavie", "sportScore", "money" };

		ServletContext context = request.getSession().getServletContext();
		File reportFile = new File(
				context.getRealPath("/WEB-INF/reports/ScholarshipStudentsList.jasper"));
		if (!reportFile.exists()) {
			JasperReportUtils.initJasperReportsClasspath(request);
			JasperReportUtils
					.compileJasperReports(context
							.getRealPath("/WEB-INF/reports/ScholarshipStudentsList.jrxml"));
			reportFile = new File(
					context.getRealPath("/WEB-INF/reports/ScholarshipStudentsList.jasper"));
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"查無\"ScholarshipStudentsList.jasper\"檔案，請電洽電算中心，謝謝！！");
		}
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());

		DecimalFormat df = new DecimalFormat(",##0.0");
		DecimalFormat df1 = new DecimalFormat("###,###");
		ClassScoreSummary summary = new ClassScoreSummary(year, term,
				processClassInfo(aForm), Examine.FINAL);
		List<ClassScoreSummary> csss = sm
				.findClassScoreSummaryByLikeExpression(summary);

		List<Object> printData = new ArrayList<Object>();
		StdScore target = null;
		String[] data = null;
		Student student = null;
		Graduate graduate = null;
		Just just = null;
		Seld seld = null;
		Dtime d = null;
		Csno csno = null;
		ScoreHist scoreHist = null;
		List<Dtime> dtimes = null;
		List<ScoreHist> scoreHistList = null;
		Set<StdScore> stdScores = null;
		List<Seld> selds = null;
		String studentNo = null, studentName = null, departClass = null, className = null;
		Double sportScore = null;
		int money = 0, studentCounts = 0, totalMoney = 0, counts = 0, studentsCount = 0, scholarshipCount = 0;
		boolean needMud = false, isMaster = false, isQualify = false, isGrade1, isExcludedGraduateClass = false;
		boolean isGrade2 = false;
		double justScore = 0.0d;

		for (ClassScoreSummary css : csss) {

			if (css.getDepartClass().equals("1220")
					|| css.getDepartClass().equals("122A")
					|| css.getDepartClass().equals("1152A"))
				continue;

			departClass = css.getDepartClass();
			// 新竹四技2年級體育用T0Z13與T0Z14
			isGrade2 = "2".equals(StringUtils.substring(departClass, 4, 5));
			isExcludedGraduateClass = "n".equalsIgnoreCase(scholarshipType)
					&& "2".equals(term);
			if (!isExcludedGraduateClass
					|| !Toolket.chkIsGraduateClass(departClass)) {
				if (Toolket.isDelayClass(departClass))
					continue;

				className = StringUtils.substring(
						Toolket.getClassFullName(departClass), 2);
				target = new StdScore();
				target.setParentOid(css.getOid());
				stdScores = css.getStdScoreSet();
				// 計算學生數
				// 97.2改為學優有名單人數限制,未滿30人取1名;
				// 30~40人取2名;41人以上取3名(不包括研究所與延修生)
				// 學業平均成績相同者,分名次的順序:操行,體育,國文,英文,專業必修科目總平均成績
				studentsCount = stdScores.size();
				if (studentsCount > 40)
					scholarshipCount = 3;
				else if (studentsCount >= 30 && studentsCount <= 40)
					scholarshipCount = 2;
				else
					scholarshipCount = 1;
				// 專科須各減1000元
				needMud = Toolket.isAssocaiteClass(departClass);
				// 碩士只取前2名
				isMaster = Toolket.isMasterClass(departClass);
				counts = 0;
				for (StdScore stdScore : stdScores) {
					if (stdScore.getTotalCredits().floatValue() != stdScore
							.getPassCredits().floatValue())
						continue; // 如果不相同代表有不及格分數,可以跳過去了^__^

					studentNo = stdScore.getStudentNo();
					just = sam.findJustByStudentNo(studentNo);
					justScore = 0.0D;
					if (isHistory || just == null
							|| just.getTotalScore() == 0.0D) {
						scoreHist = new ScoreHist(studentNo);
						scoreHist.setSchoolYear(new Short(year));
						scoreHist.setSchoolTerm(term);
						scoreHist.setCscode("99999");
						scoreHistList = sm.findScoreHistBy(scoreHist);
						if (!scoreHistList.isEmpty()) {
							scoreHist = sm.findScoreHistBy(scoreHist).get(0);
							justScore = scoreHist.getScore();
						}
					} else
						justScore = just.getTotalScore() >= 95.0d ? 95.0d
								: just.getTotalScore();

					// 夜二專體育有分S0Z11(一年級), S0Z12(一年級),
					// S0Z13(二年級), S0Z14(二年級)
					isGrade1 = true;
					if (Toolket.isAssocaiteClass(departClass)) {
						dtimes = cm
								.findDtimeBy(
										new Dtime(departClass, ("1"
												.equals(sterm) ? "S0Z11"
												: "S0Z12"), sterm), "cscode");
						if (dtimes.isEmpty()) {
							isGrade1 = false;
							dtimes = cm.findDtimeBy(
									new Dtime(departClass,
											("1".equals(sterm) ? "S0Z13"
													: "S0Z14"), sterm),
									"cscode");
						}
					} else {
						if (!Toolket.isHsinChuStudent(departClass))
							dtimes = cm
									.findDtimeBy(
											new Dtime(
													departClass,
													("1".equals(sterm) ? (isGrade2 ? "T0Z13"
															: "T0Z11")
															: (isGrade2 ? "T0Z14"
																	: "T0Z12")),
													sterm), "cscode");
						else
							dtimes = cm
									.findDtimeBy(
											new Dtime(
													departClass,
													("1".equals(sterm) ? (isGrade2 ? "T0Z27"
															: "T0Z25")
															: (isGrade2 ? "T0Z28"
																	: "T0Z26")),
													sterm), "cscode");

						// if (dtimes.isEmpty()) {
						// dtimes = cm.findDtimeBy(
						// new Dtime(departClass,
						// ("1".equals(sterm) ? "T0Z18"
						// : "T0Z19"), sterm),
						// "cscode");
						// }
					}

					isQualify = false; // 是否符合條件?
					if (!dtimes.isEmpty()) {
						seld = sm.findSeld(dtimes.get(0).getOid(), studentNo);
						if (seld == null) {
							// 有可能併班選體育課程,所以也要一併檢查
							d = new Dtime();
							d.setSterm(sterm);
							if (Toolket.isAssocaiteClass(departClass)) {
								if (isGrade1)
									d.setCscode("1".equals(sterm) ? "S0Z11"
											: "S0Z12");
								else
									d.setCscode("1".equals(sterm) ? "S0Z13"
											: "S0Z14");
							} else {
								if (!Toolket.isHsinChuStudent(departClass))
									d.setCscode("1".equals(sterm) ? (isGrade2 ? "T0Z13"
											: "T0Z11")
											: (isGrade2 ? "T0Z14" : "T0Z12"));
								else
									d.setCscode("1".equals(sterm) ? (isGrade2 ? "T0Z27"
											: "T0Z25")
											: (isGrade2 ? "T0Z28" : "T0Z26"));
							}
							dtimes = cm.findDtimeBy(d, "cscode");
							if (dtimes.isEmpty()) {
								d.setCscode("1".equals(sterm) ? "T0Z18"
										: "T0Z19");
								dtimes = cm.findDtimeBy(d, "cscode");
							}

							if (!dtimes.isEmpty()) {
								for (Dtime dd : dtimes) {
									seld = sm.findSeld(dd.getOid(), studentNo);
									if (seld != null) {
										isQualify = stdScore.getAverage() >= 80.0F
												&& Math.round(justScore) >= 80.0D
												&& Math.round(seld.getScore()) >= 70.0D;
										break;
									}
								}
							}
						} else {
							isQualify = stdScore.getAverage() >= 80.0F
									&& Math.round(justScore) >= 80.0D
									&& Math.round(seld.getScore()) >= 70.0D;
						}
					} else {
						if ((isGrade2)
								&& "64".equals(StringUtils.substring(
										departClass, 1, 3))) {
							selds = sm.findSeld(studentNo, sterm);
							List a = null;
							for (Seld s : selds) {
								a = cm.hqlGetBy("SELECT c FROM Dtime d, Csno c WHERE d.cscode = c.cscode AND d.sterm = '"
										+ sterm
										+ "' AND d.oid = "
										+ s.getDtimeOid());
								csno = (Csno) a.get(0);
								if (StringUtils
										.indexOf(csno.getChiName(), "體育") != StringUtils.INDEX_NOT_FOUND) {
									isQualify = stdScore.getAverage() >= 80.0F
											&& Math.round(justScore) >= 80.0D
											&& Math.round(s.getScore()) >= 70.0D;
									sportScore = s.getScore();
									break;
								}
							}
						} else
							isQualify = stdScore.getAverage() >= 80.0F
									&& Math.round(justScore) >= 80.0D;
					}

					if (isQualify) {
						counts++;
						if (counts > (isMaster ? 2 : scholarshipCount))
							break;

						studentCounts++;
						data = new String[8];
						data[0] = className;
						data[1] = studentNo;
						student = mm.findStudentByNo(stdScore.getStudentNo());
						if (student == null) {
							graduate = mm.findGraduateByStudentNo(stdScore
									.getStudentNo());
							studentName = graduate.getStudentName();
						} else
							studentName = student.getStudentName();

						data[2] = studentName;
						data[3] = String.valueOf(counts);
						data[4] = df.format(stdScore.getAverage() + 0.001F);
						data[5] = String.valueOf(Math.round(justScore));
						if (Toolket.isAssocaiteClass(departClass)) {
							if (isGrade1) {
								dtimes = cm.findDtimeBy(
										new Dtime(departClass, ("1"
												.equals(sterm) ? "S0Z11"
												: "S0Z12"), sterm), "cscode");
							} else {
								dtimes = cm.findDtimeBy(
										new Dtime(departClass, ("1"
												.equals(sterm) ? "S0Z13"
												: "S0Z14"), sterm), "cscode");
							}
						} else {
							if (!Toolket.isHsinChuStudent(departClass)) {
								dtimes = cm
										.findDtimeBy(
												new Dtime(
														departClass,
														("1".equals(sterm) ? (isGrade2 ? "T0Z13"
																: "T0Z11")
																: (isGrade2 ? "T0Z14"
																		: "T0Z12")),
														sterm), "cscode");

								if (dtimes.isEmpty()) {
									dtimes = cm.findDtimeBy(new Dtime(
											departClass,
											("1".equals(sterm) ? "T0Z18"
													: "T0Z19"), sterm),
											"cscode");
								}
							} else
								dtimes = cm
										.findDtimeBy(
												new Dtime(
														departClass,
														("1".equals(sterm) ? (isGrade2 ? "T0Z27"
																: "T0Z25")
																: (isGrade2 ? "T0Z28"
																		: "T0Z26")),
														sterm), "cscode");
						}

						try {
							if (!dtimes.isEmpty()) {
								data[6] = String.valueOf(Math.round(seld
										.getScore()));
							} else {
								if (isGrade2) {
									data[6] = sportScore == null ? "" : String
											.valueOf(Math.round(sportScore));
								} else
									data[6] = "";
							}
						} catch (Exception e) {

						}

						switch (counts) {
						case 1:
							money = 5000;
							break;
						case 2:
							money = 4000;
							break;
						case 3:
							money = 3000;
							break;
						}
						data[7] = String.valueOf((needMud ? money - 1000
								: money));
						totalMoney += needMud ? money - 1000 : money;
						printData.add(data);
					}
				}
			}
		}
		parameters.put("D", String.valueOf(studentCounts));
		parameters.put("E", df1.format(totalMoney));

		byte[] bytes = null;
		if (!printData.isEmpty()) {
			if (isPdf) {
				bytes = JasperRunManager.runReportToPdf(jasperReport,
						parameters, new HibernateQueryResultDataSource(
								printData, fields));
				JasperReportUtils.printPdfToFrontEnd(response, bytes);
			} else {
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, parameters,
						new HibernateQueryResultDataSource(printData, fields));
				JExcelApiExporter exporterXLS = new JExcelApiExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporterXLS.setParameter(
						JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
						Boolean.TRUE);
				exporterXLS.setParameter(
						JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
						Boolean.FALSE);
				exporterXLS
						.setParameter(
								JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
								Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
						response.getOutputStream());
				response.setHeader("Content-Disposition", "inline;filename="
						+ "FinalScoreNotice.xls");
				response.setContentType("application/vnd.ms-excel");
				exporterXLS.exportReport();
				jasperPrint = null;
			}
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), param,
					new JREmptyDataSource());
		}
	}

	/**
	 * 個人加選科目成績清冊
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @exception If
	 *                an exception occurs
	 */
	public void printStdAdcdList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		DynaActionForm aForm = (DynaActionForm) form;
		ServletContext context = request.getSession().getServletContext();

		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);

		String year = cm.getNowBy("School_year");
		String term = aForm.getString("sterm");
		String header = "YEAR學年度第TERM學期個人加選科目成績".replaceAll("YEAR", year)
				.replaceAll("TERM", term);
		List<Clazz> clazzes = sm.findClassBy(
				new Clazz(processClassInfo(aForm)),
				credential.getClassInChargeAry(), false);

		// PasswordRecord pass = new PasswordRecord();
		// pass.setPassword((short) 1111);
		if (!clazzes.isEmpty()) {

			List<Student> students = null;
			List<Seld> selds = null;
			Dtime dtime = null;
			Csno csno = null;

			HSSFSheet sheet = null;
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("標楷體");
			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("標楷體");

			for (Clazz clazz : clazzes) {
				if (Toolket.isDelayClass(clazz.getClassNo()))
					continue;

				students = mm.findStudentsByClassNo(clazz.getClassNo());
				if (!students.isEmpty()) {

					sheet = workbook.createSheet(clazz.getClassNo());
					sheet.setColumnWidth(0, 2800);
					sheet.setColumnWidth(1, 2500);
					sheet.setColumnWidth(2, 3500);
					sheet.setColumnWidth(3, 3500);
					sheet.setColumnWidth(4, 5000);
					sheet.setColumnWidth(5, 1500);
					sheet.setColumnWidth(6, 1500);
					sheet.setColumnWidth(7, 1500);
					sheet.setColumnWidth(8, 1500);
					// sheet
					// .addMergedRegion(new Region(0, (short) 0, 0,
					// (short) 8));
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
					// Header
					Toolket.setCellValue(workbook, sheet, 0, 0, header,
							fontSize16, HSSFCellStyle.ALIGN_CENTER, false,
							35.0F, null);

					// Column Header
					Toolket.setCellValue(workbook, sheet, 1, 0, "學號",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 1, 1, "姓名",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 1, 2, "班級",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 1, 3, "開課班級",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 1, 4, "科目名稱",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 1, 5, "選別",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 1, 6, "學分",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 1, 7, "時數",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 1, 8, "成績",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);

					int index = 2;
					for (Student student : students) {

						selds = cm.findSeldByStudentNoAndTerm(
								student.getStudentNo(), term);
						if (!selds.isEmpty()) {

							for (Seld seld : selds) {
								dtime = cm.findDtimeBy(seld.getDtimeOid());
								csno = cm.findCourseInfoByCscode(dtime
										.getCscode());
								if (dtime != null) {
									if (!student.getDepartClass()
											.equalsIgnoreCase(
													dtime.getDepartClass())) {
										Toolket.setCellValue(workbook, sheet,
												index, 0,
												student.getStudentNo(),
												fontSize10,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
										Toolket.setCellValue(workbook, sheet,
												index, 1,
												student.getStudentName(),
												fontSize10,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
										Toolket.setCellValue(
												workbook,
												sheet,
												index,
												2,
												Toolket.getClassFullName(student
														.getDepartClass()),
												fontSize10,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
										Toolket.setCellValue(workbook, sheet,
												index, 3,
												Toolket.getClassFullName(dtime
														.getDepartClass()),
												fontSize10,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
										Toolket.setCellValue(workbook, sheet,
												index, 4, csno.getChiName(),
												fontSize10,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
										Toolket.setCellValue(workbook, sheet,
												index, 5, Toolket
														.getCourseOpt(dtime
																.getOpt()),
												fontSize10,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
										Toolket.setCellValue(workbook, sheet,
												index, 6, String.valueOf(dtime
														.getCredit()),
												fontSize10,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
										Toolket.setCellValue(workbook, sheet,
												index, 7, String.valueOf(dtime
														.getThour()),
												fontSize10,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
										Toolket.setCellValue(
												workbook,
												sheet,
												index++,
												8,
												String.valueOf(seld.getScore()),
												fontSize10,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
									}
								}
							}

						}

					}

				}
			}

			File tempDir = new File(
					context.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "StdAdcdList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), param,
					new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}

	}

	/**
	 * 未進行輔導紀錄學生清冊
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            request javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            response javax.servlet.http.HttpServletResponse object
	 * @param sterm
	 *            學期
	 * @exception If
	 *                an exception occurs
	 */
	public void printNoneCounseling(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		DynaActionForm aForm = (DynaActionForm) form;
		ServletContext context = request.getSession().getServletContext();

		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);

		String year = cm.getNowBy("School_year");
		String term = aForm.getString("sterm");
		String header = "YEAR學年度第TERM學期未進行預警補救教學學生清冊".replaceAll("YEAR", year)
				.replaceAll("TERM", term);
		List<Clazz> clazzes = sm.findClassBy(
				new Clazz(processClassInfo(aForm)),
				credential.getClassInChargeAry(), false);

		if (!clazzes.isEmpty()) {

			ClassScoreSummary summary = null;
			List<ClassScoreSummary> csss = null;
			List<ClassInCharge> charges = null;
			Set<StdScore> scores = null;
			StudCounseling counseling = null;
			Student student = null;
			ClassInCharge charge = null;
			Empl empl = null;
			String tutorName = null;

			HSSFSheet sheet = null;
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("標楷體");
			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("標楷體");

			sheet = workbook.createSheet("未進行預警補救教學學生清冊");
			sheet.setColumnWidth(0, 2800);
			sheet.setColumnWidth(1, 2500);
			sheet.setColumnWidth(2, 3500);
			sheet.setColumnWidth(2, 3000);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, header, fontSize16,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "學號", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "姓名", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "班級", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "班級導師", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;

			for (Clazz clazz : clazzes) {
				if (Toolket.isDelayClass(clazz.getClassNo()))
					continue;

				// 取得期中1/2或2/3學生資料
				summary = new ClassScoreSummary(year, sterm,
						clazz.getClassNo(), Examine.MID);
				csss = sm.findClassScoreSummaryByLikeExpression(summary);

				charge = new ClassInCharge();
				charge.setClassNo(clazz.getClassNo());
				charge.setModuleOids("|86|"); // 導師
				charges = mm.findClassInChargeBy(charge);
				if (!charges.isEmpty()) {
					empl = mm.findEmplByOid(charges.get(0).getEmpOid());
					tutorName = empl.getCname();
				} else
					tutorName = "";

				for (ClassScoreSummary css : csss) {
					scores = css.getStdScoreSet();
					for (StdScore score : scores) {
						counseling = new StudCounseling();
						counseling.setSchoolYear(Short.valueOf(year));
						counseling.setSchoolTerm(Short.valueOf(sterm));
						counseling.setStudentNo(score.getStudentNo());
						counseling.setItemNo(Integer.valueOf("29")); // 預警補救教學
						if (sam.findStudCounselingBy(counseling).isEmpty()) {

							student = mm.findStudentByNo(score.getStudentNo());
							Toolket.setCellValue(workbook, sheet, index, 0,
									score.getStudentNo(), fontSize10,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 1,
									student.getStudentName(), fontSize10,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(
									workbook,
									sheet,
									index,
									2,
									Toolket.getClassFullName(clazz.getClassNo()),
									fontSize10, HSSFCellStyle.ALIGN_CENTER,
									true, null);
							Toolket.setCellValue(workbook, sheet, index++, 3,
									tutorName, fontSize10,
									HSSFCellStyle.ALIGN_CENTER, true, null);

						}
					}
				}
			}

			File tempDir = new File(
					context.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "NoneCounseling.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(
					context.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager.runReportToPdf(
					JasperReportUtils.getNoResultReport(context), param,
					new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("PrintOption", "printOption");
		map.put("Preview", "printOption");
		map.put("preview", "preview");
		map.put("score.reportPrint.excludeCourseSearch", "search"); // 查詢不納入計算期中科目
		map.put("score.reportPrint.addExcludeCourse", "addExclude"); // 加入不納入計算期中科目
		map.put("score.reportPrint.deleteExcludeCourse", "deleteExclude"); // 刪除不納入計算期中科目
		return map;
	}

	private List<Student> getStudentsByCondition(MemberManager mm,
			Map<String, String> map, String classFilter) {
		return mm.findStudentsInChargeByStudentInfoForm(map, classFilter);
	}

	private Map<ScoreStatus, Integer> calFailStudents(Set<StdScore> sss) {
		Map<ScoreStatus, Integer> ret = new HashMap<ScoreStatus, Integer>();
		Integer fail2 = 0, fail3 = 0;
		for (StdScore stdScore : sss) {
			if (stdScore.getStatus() == ScoreStatus.STATUS_2)
				fail2++;
			else if (stdScore.getStatus() == ScoreStatus.STATUS_3)
				fail3++;
		}

		ret.put(ScoreStatus.STATUS_2, fail2);
		ret.put(ScoreStatus.STATUS_3, fail3);
		return ret;
	}

	private Map<ScoreStatus, Set<StdScore>> calFailStudentsSet(Set<StdScore> sss) {
		Map<ScoreStatus, Set<StdScore>> ret = new HashMap<ScoreStatus, Set<StdScore>>();
		Set<StdScore> fail2 = new HashSet<StdScore>();
		Set<StdScore> fail3 = new HashSet<StdScore>();
		for (StdScore stdScore : sss) {
			try {
				if (stdScore.getStatus() == ScoreStatus.STATUS_2)
					fail2.add(stdScore);
				else if (stdScore.getStatus() == ScoreStatus.STATUS_3)
					fail3.add(stdScore);
			} catch (Exception e) {

			}

		}

		ret.put(ScoreStatus.STATUS_2, fail2);
		ret.put(ScoreStatus.STATUS_3, fail3);
		return ret;
	}

	private String calRank(List<Stavg> stavgs, String studentNo) {
		int totalCounts = stavgs.size();
		int rank = 0;
		Stavg stavg = null;
		for (int i = 0; i < stavgs.size(); i++) {
			stavg = stavgs.get(i);
			if (studentNo.equalsIgnoreCase(stavg.getStudentNo())) {
				rank = i + 1;
				int j = i - 1;
				while (j >= 0 && stavgs.get(j).getScore() == stavg.getScore()) {
					rank--;
					j--;
				}
				break;
			}
		}
		return String.valueOf(rank) + " / " + String.valueOf(totalCounts);
	}

	@SuppressWarnings("unchecked")
	private Map<String, List<Map>> separeteClass(List<Map> dtimeList) {
		String[] denied = { "50000", "T0001" };
		// List<List<Map>> dtimes = new LinkedList<List<Map>>();
		Map<String, List<Map>> dtimes1 = new HashMap<String, List<Map>>();
		List<Map> temp = null;
		String departClass = null;
		for (int i = 0; i < dtimeList.size(); i++) {
			Map dtime = (Map) dtimeList.get(i);
			boolean bFlag = !ArrayUtils.contains(denied,
					(String) dtime.get("cscode"));
			if (i == 0) {
				departClass = (String) dtime.get("depart_class");
				temp = new LinkedList<Map>();
				if (bFlag)
					temp.add(dtime);
				continue;
			}
			if (departClass
					.equalsIgnoreCase((String) dtime.get("depart_class"))) {
				if (bFlag)
					temp.add(dtime);
			} else {
				// dtimes.add(temp);
				dtimes1.put(departClass, temp);
				temp = new LinkedList<Map>();
				departClass = (String) dtime.get("depart_class");
				if (bFlag)
					temp.add(dtime);
			}
		}
		// dtimes.add(temp);
		dtimes1.put(departClass, temp);
		return dtimes1;
	}

	// 處理以Request Parameters作為查詢Dtime物件之用
	// open為null時,則不會將Dtime內open欄位作Filtering
	@SuppressWarnings("unchecked")
	private List<Map> getDtimeListByParams(HttpServletRequest request,
			DynaActionForm form, Byte open) {

		HttpSession session = request.getSession(false);
		String campusInCharge2 = (String) form.get("campusInCharge2");
		String schoolInCharge2 = (String) form.get("schoolInCharge2");
		String deptInCharge2 = (String) form.get("deptInCharge2");
		String departClass = (String) form.get("classInCharge2");
		String term = (String) form.get("sterm"); // term
		String choseType = (String) form.get("choseType"); // opt

		UserCredential credential = (UserCredential) session
				.getAttribute("Credential");
		Clazz[] classes = credential.getClassInChargeAry();
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		if ("All".equals(schoolInCharge2)) {
			schoolInCharge2 = "%";
		}
		if ("All".equals(deptInCharge2)) {
			deptInCharge2 = "%";
		}
		if ("All".equals(departClass)) {
			departClass = "%";
		}

		List<Map> dtimeList = null;
		if (campusInCharge2.equals("All")) {
			dtimeList = cm.getDtimeForOpenCsAll(classes, term, choseType, open);
		} else {
			dtimeList = cm.getDtimeForOpenCs(classes, campusInCharge2,
					schoolInCharge2, deptInCharge2, departClass, term,
					choseType, open);
		}
		return dtimeList;
	}

	private String processClassInfo(DynaActionForm form) {
		String campusInCharge2 = (String) form.get("campusInCharge2");
		String schoolInCharge2 = (String) form.get("schoolInCharge2");
		String deptInCharge2 = (String) form.get("deptInCharge2");
		String departClass = (String) form.get("classInCharge2");
		schoolInCharge2 = schoolInCharge2.equalsIgnoreCase("All") ? ""
				: schoolInCharge2;
		deptInCharge2 = deptInCharge2.equalsIgnoreCase("All") ? ""
				: deptInCharge2;
		departClass = departClass.equalsIgnoreCase("All") ? "" : departClass;
		// return departClass;
		return departClass.length() == 6 ? departClass : campusInCharge2
				+ schoolInCharge2 + deptInCharge2;
	}

	public static String[] getYearArray(String schoolYear) {
		int yearCal = 10;
		int year = Integer.parseInt(schoolYear);
		String[] years = new String[yearCal];
		years[yearCal - 1] = schoolYear;
		for (int i = yearCal - 2; i > 0; i--)
			years[i] = String.valueOf((year = year - 1));
		return years;
	}

	private String checkRankDepartClass(String departClass, int schoolYear,
			int searchYear) {
		int distance = schoolYear - searchYear;
		departClass = StringUtils.substring(departClass, 0, 4)
				+ (Integer.valueOf(StringUtils.substring(departClass, 4, 5)) - distance)
				+ StringUtils.substring(departClass, 5);
		return departClass;
	}

	private File mergePdfsForPrinting(File[] files, File dir, String filename)
			throws Exception {
		Document document = new Document();
		File printingPdf = new File(dir, filename);
		FileOutputStream fos = new FileOutputStream(printingPdf);
		PdfCopy copy = new PdfCopy(document, fos);
		document.open();
		PdfReader reader = null;
		for (File pdf : files) {
			reader = new PdfReader(pdf.getAbsolutePath(), null);
			for (int i = 1; i <= reader.getNumberOfPages(); i++)
				copy.addPage(copy.getImportedPage(reader, i));
		}
		document.close();

		for (File pdf : files)
			pdf.delete();
		fos.close();
		return printingPdf;
	}

	private List<Map<String, Object>> excludedT0001And50000(
			List<Map<String, Object>> dtimeList) {
		List<Map<String, Object>> ret = new LinkedList<Map<String, Object>>();
		for (Map<String, Object> map : dtimeList) {
			String cscode = (String) map.get("cscode");
			if (!"50000".equals(cscode) && !"T0001".equals(cscode))
				ret.add(map);
		}
		return ret;
	}

	private Map<String, List<Map<String, Object>>> separatedCourse(
			List<Map<String, Object>> dtimeList) {

		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		Map<String, List<Map<String, Object>>> ret = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> includedCourse = new LinkedList<Map<String, Object>>();
		List<Map<String, Object>> excludedCourse = new LinkedList<Map<String, Object>>();
		MidtermExcluded me = null;
		for (Map<String, Object> dtime : dtimeList) {
			me = new MidtermExcluded((String) dtime.get("departClass"),
					(String) dtime.get("cscode"));
			if (sm.findMidtermExcludedBy(me) != null)
				excludedCourse.add(dtime); // 要排除的科目
			else
				includedCourse.add(dtime); // 要納入的科目
		}
		ret.put(DTIME_LIST1, includedCourse);
		ret.put(DTIME_LIST2, excludedCourse);
		return ret;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getExcludedCourseByIndex(
			HttpServletRequest request, String cookieName) {
		List<Map<String, Object>> ret = new LinkedList<Map<String, Object>>();
		HttpSession session = request.getSession(false);
		String index = Toolket.getSelectedIndexFromCookie(request, cookieName);
		List<Map<String, Object>> dtimeList = (List<Map<String, Object>>) session
				.getAttribute(cookieName);
		for (Map<String, Object> dtime : dtimeList) {
			if (Toolket.isValueInCookie(dtime.get("oid").toString(), index)) {
				ret.add(dtime);
			}
		}
		return ret;
	}

	private List<MidtermExcluded> processDtimeToMidterExcluded(ScoreManager sm,
			List<Map<String, Object>> dtimeList) {
		List<MidtermExcluded> ret = new LinkedList<MidtermExcluded>();
		MidtermExcluded me = null;
		MidtermExcluded existMe = null;
		for (Map<String, Object> dtime : dtimeList) {
			me = new MidtermExcluded((String) dtime.get("departClass"),
					(String) dtime.get("cscode"));
			existMe = sm.findMidtermExcludedBy(me);
			if (existMe != null)
				ret.add(existMe); // 已存在的MidtermExcluded
			else
				ret.add(me); // 未存在的MidtermExcluded
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private List<Map> excluded(List<Map> dtimeData) {
		// 通識,碩士論文一
		String[] excludedList = { "T0002", "GA035", "GB041", "GB042" };
		List<Map> ret = new LinkedList<Map>();
		for (Map c : dtimeData) {
			if (!ArrayUtils.contains(excludedList, (String) c.get("cscode")))
				ret.add(c);
		}
		return ret;
	}

	private List<Dtime> excludedSomething(List<Dtime> dtimees) {
		// 系時間,班會,通識,碩士論文一
		String[] excludedList = { "T0001", "50000", "T0002", "GA035", "GB041",
				"GB042" };
		List<Dtime> ret = new LinkedList<Dtime>();
		for (Dtime d : dtimees) {
			if (!ArrayUtils.contains(excludedList, d.getCscode()))
				ret.add(d);
		}
		return ret;
	}

	private List<Object> excludedSomething1(List<Object> dtimeAndCsno) {
		// 系時間,班會,通識,碩士論文一
		String[] excludedList = { "T0001", "50000", "T0002", "GA035", "GB041",
				"GB042" };
		List<Object> ret = new LinkedList<Object>();
		Dtime d = null;
		for (Object content : dtimeAndCsno) {
			d = (Dtime) ((Object[]) content)[0];
			if (!ArrayUtils.contains(excludedList, d.getCscode()))
				ret.add(content);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private List<Map> processExcludedCourse(ScoreManager sm, List<Map> dtimeData) {
		List<Map> ret = new LinkedList<Map>();
		MidtermExcluded me = null;
		for (Map map : dtimeData) {
			me = new MidtermExcluded((String) map.get("depart_class"),
					(String) map.get("cscode"));
			if (sm.findMidtermExcludedBy(me) == null)
				ret.add(map); // 等於null代表不再"不納入期中計算"清單內,所以要保留下來
		}
		return ret;
	}

	// @SuppressWarnings("unchecked")
	// private List<Dtime> processExcludedCourse1(ScoreManager sm,
	// List<Dtime> dtimes) {
	// List<Dtime> ret = new LinkedList<Dtime>();
	// for (Dtime dtime : dtimes) {
	// MidtermExcluded me = new MidtermExcluded(dtime.getDepartClass(),
	// dtime.getCscode());
	// if (sm.findMidtermExcludedBy(me) == null)
	// ret.add(dtime); // 等於null代表不再"不納入期中計算"清單內,所以要保留下來
	// }
	// return ret;
	// }

	private List<Object> processExcludedCourse1(ScoreManager sm,
			List<Object> dtimeAndCsno) {
		List<Object> ret = new LinkedList<Object>();
		MidtermExcluded me = null;
		Dtime dtime = null;
		for (Object content : dtimeAndCsno) {
			dtime = (Dtime) ((Object[]) content)[0];
			me = new MidtermExcluded(dtime.getDepartClass(), dtime.getCscode());
			if (sm.findMidtermExcludedBy(me) == null)
				ret.add(content); // 等於null代表不再"不納入期中計算"清單內,所以要保留下來
		}
		return ret;
	}

	private boolean isDtimeExcluded(ScoreManager sm, Dtime dtime) {
		boolean ret = true;
		MidtermExcluded me = new MidtermExcluded(dtime.getDepartClass(),
				dtime.getCscode());
		if (sm.findMidtermExcludedBy(me) == null)
			ret = false;
		return ret;
	}

	private List<Dtime> processExcludedCourse2(ScoreManager sm,
			List<Dtime> dtimes) {
		List<Dtime> ret = new LinkedList<Dtime>();
		MidtermExcluded me = null;
		for (Dtime dtime : dtimes) {
			me = new MidtermExcluded(dtime.getDepartClass(), dtime.getCscode());
			if (sm.findMidtermExcludedBy(me) == null)
				ret.add(dtime); // 等於null代表不再"不納入期中計算"清單內,所以要保留下來
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private List<Map> processExcludedThesis(List<Map> dtimeData) {
		String[] excludedThesis = { "GA035", "GB041", "GB042" }; //
		List<Map> ret = new LinkedList<Map>();
		for (Map c : dtimeData) {
			if (!ArrayUtils.contains(excludedThesis, (String) c.get("cscode")))
				ret.add(c);
		}
		return ret;
	}

	protected void setCellValue(HSSFSheet sheet, int row, int col, String value) {
		HSSFRow rowData = sheet.getRow(row);
		if (rowData == null)
			rowData = sheet.createRow((short) row);
		HSSFCell cell = rowData.getCell(col);
		if (cell == null)
			cell = rowData.createCell(col);
		cell.setCellValue(new HSSFRichTextString(
				(StringUtils.isBlank(value) ? "" : value)));
	}

	protected void setCellValue(HSSFWorkbook wb, HSSFSheet sheet, int row,
			int col, String value, short align) {
		HSSFRow rowData = sheet.getRow(row);
		if (rowData == null)
			rowData = sheet.createRow((short) row);
		HSSFCell cell = rowData.getCell(col);
		if (cell == null)
			cell = rowData.createCell(col);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(align);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString(
				(StringUtils.isBlank(value) ? "" : value)));
	}

	protected void setCellValue(HSSFWorkbook wb, HSSFSheet sheet, int row,
			int col, String value, HSSFFont font, short align) {
		HSSFRow rowData = sheet.getRow(row);
		if (rowData == null)
			rowData = sheet.createRow((short) row);
		HSSFCell cell = rowData.getCell(col);
		if (cell == null)
			cell = rowData.createCell(col);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		if (font != null)
			cellStyle.setFont(font);

		cellStyle.setAlignment(align);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString(
				(StringUtils.isBlank(value) ? "" : value)));
	}

	protected String getCellValue(HSSFSheet sheet, int row, int col) {
		HSSFRow rowData = sheet.getRow(row);
		HSSFCell cell = rowData.getCell(col);
		return cell.getRichStringCellValue().getString();
	}

	private static class RankComparable implements Comparable<RankComparable> {

		private int index;
		private double avg;
		private double just;

		public RankComparable(int index, Double avg, Double just) {
			this.index = index;
			this.avg = avg.doubleValue();
			this.just = just == null ? 0.0d : just.doubleValue();
		}

		public int compareTo(RankComparable x) {
			if (this.avg > x.avg)
				return 1;
			else if (this.avg == x.avg && this.just > x.just)
				return 1;
			else if (this.avg == x.avg && this.just < x.just)
				return -1;
			else if (this.avg < x.avg)
				return -1;
			else if (this.avg == x.avg && this.just == x.just)
				return 0;
			else
				return -1;
		}

		public int getIndex() {
			return index;
		}

	}

	static class PDFFilter implements FilenameFilter {

		private String filename;

		public PDFFilter(String filename) {
			this.filename = filename;
		}

		public boolean accept(File dir, String name) {
			return name.endsWith(".pdf")
					&& StringUtils.indexOf(name, filename) == StringUtils.INDEX_NOT_FOUND;
		}

	}

	private int[] sortAvgAndJust(Double[][] data) {
		int studentCounts = data.length;
		int len = data[0].length;
		int[] ret = new int[studentCounts];
		int index = 0;
		List<RankComparable> tmp = new LinkedList<RankComparable>();
		RankComparable toDoSort = null;
		for (Double[] d : data) {
			toDoSort = new RankComparable(index++, d[len - 2], d[len - 1]);
			tmp.add(toDoSort);
		}
		Collections.sort(tmp);
		index = 0;
		for (RankComparable doDo : tmp) {
			ret[doDo.getIndex()] = studentCounts - index;
			index++;
		}
		return ret;
	}

	int[] sortAvgAndJust1(Double[][] data) {
		int studentCounts = data.length;
		int len = data[0].length;
		int[] ret = new int[studentCounts];
		int index = 0;
		List<RankComparable> tmp = new LinkedList<RankComparable>();
		RankComparable toDoSort = null;
		for (Double[] d : data) {
			toDoSort = new RankComparable(index++, d[len - 2], d[len - 1]);
			tmp.add(toDoSort);
		}
		Collections.sort(tmp);
		index = 0;
		for (RankComparable doDo : tmp) {
			ret[doDo.getIndex()] = studentCounts - index;
			index++;
		}
		return ret;
	}

}
