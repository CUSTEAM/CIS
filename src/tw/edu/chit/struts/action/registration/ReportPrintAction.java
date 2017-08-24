package tw.edu.chit.struts.action.registration;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_YEAR;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.DeptCode4Yun;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Gmark;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.LicenseCode;
import tw.edu.chit.model.LifeCounseling;
import tw.edu.chit.model.MasterData;
import tw.edu.chit.model.RegistrationCard;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Stavg;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.StdSkill;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.TeacherStayTime;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class ReportPrintAction extends BaseLookupDispatchAction {

	/**
	 * 進入報表列印作業
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
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
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", sterm);
		aForm.set("year", tw.edu.chit.struts.action.score.ReportPrintAction
				.getYearArray(cm.getNowBy("School_year")));
		setContentPage(session, "registration/ReportPrint.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 列印作業
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @exception java.lang.Exception
	 */
	public void printReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ActionForm是在struts-config-PSN.xml內,非struts-config-REG.xml內???
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", request.getParameter("st"));
		aForm.set("printOpt", request.getParameter("p"));
		aForm.set("campusInCharge2", request.getParameter("c"));
		aForm.set("schoolInCharge2", request.getParameter("s"));
		aForm.set("deptInCharge2", request.getParameter("d"));
		aForm.set("classInCharge2", request.getParameter("cl"));
		aForm.set("printInterClass", request.getParameter("printInterClass"));
		aForm.set("deptCodeOpt", request.getParameter("dcp"));
		aForm.set("licenseValidDateStart", request.getParameter("sd"));
		aForm.set("licenseValidDateEnd", request.getParameter("ed"));
		aForm.set("nodeCode", request.getParameter("nc"));
		aForm.set("dayCode", request.getParameter("dc"));
		aForm.set("campusCode", request.getParameter("cc"));
		aForm.set("schoolType", request.getParameter("stc"));
		String printOpt = aForm.getString("printOpt");
		String sterm = (String) aForm.get("sterm");
		request.getSession(false).setMaxInactiveInterval(-1);

		if ("RegisterList".equals(printOpt)) {
			// 列印註冊會辦單
			printRegisterList(mapping, aForm, request, response, sterm);
		} else if ("ClassStudentsScoreHistory".equals(printOpt)) {
			// 列印全班歷年成績表
			printClassStudentsScoreHistory(mapping, aForm, request, response,
					sterm);
		} else if ("ClassStudentsRegistrationCard".equals(printOpt)) {
			// 列印新生學籍卡
			printClassStudentsRegistrationCard(mapping, aForm, request,
					response, sterm);
		} else if ("IdnoCheck".equals(printOpt)) {
			// 學生身分證字號檢核
			printIdnoCheckErrorStudentsList(mapping, aForm, request, response,
					sterm);
		} else if ("ClassStudentsScoreHistory4CreditClass".equals(printOpt)) {
			// 列印全班歷年成績表(學院專用)
			printClassStudentsScoreHistory4CreditClass(mapping, aForm, request,
					response, sterm);
		} else if ("StayTimePrint".equalsIgnoreCase(printOpt)) {
			// 列印專任教師留校時間
			printStayTimePrint(mapping, aForm, request, response, sterm);
		} else if ("Calculate".equals(printOpt)) {
			// 跨系選課人數
			printCalculate(mapping, aForm, request, response, sterm);
		} else if ("GstmdList4Ntnu".equals(printOpt)) {
			// 應屆畢業生基本資料(師大格式)
			printGstmdList4Ntnu(mapping, aForm, request, response, sterm);
		} else if ("GstmdList4Ntnu1".equals(printOpt)) {
			// 1,3年級學生基本資料(師大格式)
			printGstmdList4Ntnu1(mapping, aForm, request, response, sterm);
		} else if ("Listing4-1".equals(printOpt)) {
			// 表4-1 畢業人數統計
			printListing41(mapping, aForm, request, response, sterm);
		} else if ("Listing4-2".equals(printOpt)) {
			// 表4-2 實際在校學生人數
			printListing42(mapping, aForm, request, response, sterm);
		} else if ("Listing4-4-1".equals(printOpt)) {
			// 表4-4-1 休退學人數及原因
			printListing441(mapping, aForm, request, response, sterm);
		} else if ("StmdUnSeld".equals(printOpt)) {
			// 本學期學生未選課清單
			printStmdUnSeld(mapping, aForm, request, response, sterm);
		} else if ("StdSkillList".equals(printOpt)) {
			// 證照資料列印
			printStdSkillList(mapping, aForm, request, response, sterm);
		} else if ("GstmdCreditAvg".equals(printOpt)) {
			// 畢業班畢業學分數資料
			printGstmdCreditAvg(mapping, aForm, request, response, sterm);
		} else if ("RegisterManagerList".equals(printOpt)) {
			// 註冊檔資料清單
			printRegisterManagerList(mapping, aForm, request, response, sterm);
		} else if ("UploadNewStmdTemplate".equals(printOpt)) {
			// 下載新生基本資料樣本檔(未編班編學號)
			printUploadNewStmdTemplate(mapping, aForm, request, response, sterm);
		} else if ("UploadNewStmdTemplate1".equals(printOpt)) {
			// 下載新生基本資料樣本檔(已編班編學號)
			printUploadNewStmdTemplate1(mapping, aForm, request, response,
					sterm);
		}

	}

	/**
	 * 列印註冊會辦單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printRegisterList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = cm.getNowBy("School_year");
		String term = form.getString("sterm");
		String header = "中華科技大學YEAR學年度第TERM學期各班集體註冊會辦單"
				.replaceAll("YEAR", year).replaceAll("TERM", term);
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);
		if (!clazzes.isEmpty()) {
			List<Student> students = null;
			HSSFSheet sheet = null;
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("Arial Unicode MS");
			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");
			for (Clazz clazz : clazzes) {
				if (Toolket.isDelayClass(clazz.getClassNo()))
					continue;

				students = mm.findStudentsByClassNo(clazz.getClassNo());
				if (!students.isEmpty()) {
					sheet = workbook.createSheet(clazz.getClassNo());
					sheet.setColumnWidth(0, 3000);
					sheet.setColumnWidth(1, 3000);
					sheet.setColumnWidth(2, 5000);
					sheet.setColumnWidth(3, 3500);
					sheet.setColumnWidth(4, 3500);
					sheet.setColumnWidth(5, 3500);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
					// Header
					Toolket.setCellValue(workbook, sheet, 0, 0, header,
							fontSize16, HSSFCellStyle.ALIGN_CENTER, false,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 1, 0, "班級:"
							+ Toolket.getClassFullName(clazz.getClassNo())
							+ "                       班代簽名：", fontSize12,
							HSSFCellStyle.ALIGN_LEFT, false, null, null);

					// Column Header
					Toolket.setCellValue(workbook, sheet, 2, 0, "學號",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 2, 1, "姓名",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 2, 2, "學雜費銀行繳費單",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 2, 3, "學雜費減免單",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 2, 4, "助學貸款",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, 2, 5, "學生證",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);

					int index = 3;
					for (Student student : students) {
						Toolket.setCellValue(workbook, sheet, index, 0, student
								.getStudentNo(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 1, student
								.getStudentName().trim().replaceAll("　", ""),
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								null);
						Toolket.setCellValue(workbook, sheet, index, 2, "",
								null, HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 3, "",
								null, HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 4, "",
								null, HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index++, 5, "",
								null, HSSFCellStyle.ALIGN_CENTER, true, null);
						if (39 == index) { // 分頁
							Toolket.setCellValue(workbook, sheet, index, 0,
									"學號", fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 1,
									"姓名", fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 2,
									"學雜費銀行繳費單", fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 3,
									"學雜費減免單", fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 4,
									"助學貸款", fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index++, 5,
									"學生證", fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
						}
					}

					for (int i = 0; i <= 5; i++) {
						if (i == 0) {
							Toolket.setCellValue(workbook, sheet, index, i,
									"合計張數", fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							continue;
						}
						Toolket.setCellValue(workbook, sheet, index, i, "",
								null, HSSFCellStyle.ALIGN_CENTER, true, null);
					}

					sheet.addMergedRegion(new CellRangeAddress(++index, index,
							0, 5));
					// Toolket.setCellValue(workbook, sheet, index, 0,
					// " 班代簽名：",
					// fontSize12, HSSFCellStyle.ALIGN_LEFT, false, null);
				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "RegisterList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(context
					.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager
					.runReportToPdf(JasperReportUtils
							.getNoResultReport(context), param,
							new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}
	}

	/**
	 * 列印全班歷年成績表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printClassStudentsScoreHistory(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		boolean isIncludeThisTermScore = "true".equalsIgnoreCase(request
				.getParameter("tt"));

		// String year = cm.getNowBy("School_year");
		// String term = form.getString("sterm");
		String studentNo = null;
		Clazz clazz = null;
		ScoreHist scoreHist = null, hist = null;
		List<Student> students = null;
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);

		if (!clazzes.isEmpty() && clazzes.size() > 1) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("",
					""));
			saveMessages(request, messages);
			// return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			ServletContext context = request.getSession().getServletContext();
			Map<String, String> parameters = new HashMap<String, String>();
			File image = new File(context
					.getRealPath("/pages/reports/2002chitS.jpg"));
			parameters.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager.runReportToPdf(JasperReportUtils
					.getNoResultReport(context), parameters,
					new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		} else {

			clazz = clazzes.get(0);
			students = mm.findStudentsByClassNo(clazz.getClassNo());
			if (!students.isEmpty()) {

				ServletContext context = request.getSession()
						.getServletContext();
				String[] excep = { "11G332" }; // 春季班學年要+1
				String[] formYear = { "一", "二", "三", "四", "五", "六", "七", "八",
						"九" };

				short firstYear = 0;
				short lastYear = 0;

				double justScore = 0.0D;

				int sheetIndex = 0;
				int k = 0, j = 0, rowIndex = 0, nextPageRowIndex = 0;
				int kk = 0, rowIndexx = 0;

				String departClass = clazz.getClassNo();
				String departCode = "", title = "", evgrType = "", historyDepartCode = "", opt = "", cscode = "";

				// 碩士班70分及格
				float passScore = Toolket
						.getPassScoreByDepartClass(departClass);

				Graduate graduate = null;
				MasterData md = null;
				Just just = null;
				ScoreHist target = null;
				Dtime dtime = null;
				List csnos = null;
				Csno csno = null;
				Gmark gmark = null;

				Map<String, String> position = null;

				Float passCreditsSum = 0.0F;
				Float totalPassCredits = 0.0F;

				List<Seld> selds = null;
				List<ScoreHist> scoreHistList = null;
				List<Stavg> stavgs = null;
				List<Gmark> gmarks = null;

				boolean isOver = false; // (lastYear - firstYear + 1) > 4;
				boolean isDelay = Toolket.isDelayClass(departClass);
				boolean isSpringClass = false;
				// 雙主修
				boolean isDoubleMajor = false;
				boolean isAssist = false;
				// 9608前入學成績單要Show完整平均成績及操行,但之後入學的只Show'抵'字,且平均成績及操行不Show
				boolean is96Entrance = false;
				boolean isMaster = Toolket.isMasterClass(departClass);
				boolean nextPage = false, hasPassRecord = false, rowOver = false;
				boolean isNotExemptAndMend = false; // 判斷不是為抵免或待補?
				boolean isExempt = false; // 判斷是否為抵免?
				boolean isMend = false; // 判斷是否為待補?
				boolean isAppend = false; // 判斷是否為隨班?
				boolean isNotSameDepartCode = false;
				boolean isOver15Char = false, isOver10Char = false;

				int studentCounts = students.size();
				String fileName = "";

				DecimalFormat df = new DecimalFormat(",##0.0");

				File templateXLS = null;

				if (!isMaster) {

					/*if (studentCounts <= 30)
						fileName = "ClassStudentsScoreHistoryOver30.xls";
					else if (studentCounts > 31 && studentCounts <= 40)
						fileName = "ClassStudentsScoreHistoryOver40.xls";
					else if (studentCounts > 40 && studentCounts <= 45)
						fileName = "ClassStudentsScoreHistoryOver45.xls";
					else if (studentCounts > 45 && studentCounts <= 50)
						fileName = "ClassStudentsScoreHistoryOver50.xls";
					else if (studentCounts > 51 && studentCounts <= 55)
						fileName = "ClassStudentsScoreHistoryOver55.xls";
					else
						fileName = "ClassStudentsScoreHistoryOver60.xls";
					*/
					fileName = "ClassStudentsScoreHistoryOver60.xls";
					//System.out.println(fileName);
					title = Toolket.getSchoolFormalName(departClass);
					if (isDelay) {
						// Only 20 Sheets
						templateXLS = new File(
								context
										.getRealPath("/WEB-INF/reports/ClassStudentsScoreHistoryOver.xls"));
					} else {
						templateXLS = new File(context
								.getRealPath("/WEB-INF/reports/" + fileName));
					}
				} else {

					if (studentCounts <= 15)
						fileName = "ClassStudentsScoreHistoryMaster15.xls";
					else if (studentCounts > 15 && studentCounts <= 20)
						fileName = "ClassStudentsScoreHistoryMaster20.xls";
					else
						fileName = "ClassStudentsScoreHistoryMaster25.xls";

					String masterId = StringUtils.substring(departClass, 1, 3);
					if ("1G".equals(masterId))
						title = "日間部碩士班";
					else
						title = "進修部碩士在職專班";
					templateXLS = new File(context
							.getRealPath("/WEB-INF/reports/" + fileName));
				}

				HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

				HSSFFont colorFont = workbook.createFont();
				colorFont.setColor(HSSFColor.RED.index);
				colorFont.setFontHeightInPoints((short) 12);
				colorFont.setFontName("Arial Unicode MS");

				HSSFFont fontSize12 = workbook.createFont();
				fontSize12.setFontHeightInPoints((short) 12);
				fontSize12.setFontName("Arial Unicode MS");

				HSSFFont fontSize10 = workbook.createFont();
				fontSize10.setFontHeightInPoints((short) 10);
				fontSize10.setFontName("Arial Unicode MS");

				HSSFFont fontSize9 = workbook.createFont();
				fontSize9.setFontHeightInPoints((short) 9);
				fontSize9.setFontName("Arial Unicode MS");

				HSSFSheet sheet = null;

				for (Student student : students) {

					studentNo = student.getStudentNo();
					//硬性規定人數上限為80
					if(sheetIndex>79)sheetIndex=79;
					sheet = workbook.getSheetAt(sheetIndex);
					workbook.setSheetName(sheetIndex++, studentNo.toUpperCase());
					if (student == null) {
						graduate = mm.findGraduateByStudentNo(studentNo);
						student = new Student();
						BeanUtils.copyProperties(student, graduate);
					}

					// 雙主修
					isDoubleMajor = "雙修".equals(student.getExtraStatus());
					isAssist = "輔修".equals(student.getExtraStatus());

					// departClass = student.getDepartClass();
					// 9608前入學成績單要Show完整平均成績及操行,但之後入學的只Show'抵'字,且平均成績及操行不Show
					is96Entrance = student.getEntrance() == null ? false
							: student.getEntrance() >= (short) 9608;
					scoreHist = new ScoreHist(studentNo);
					scoreHistList = sm.findScoreHistBy(scoreHist);
					if (scoreHistList.isEmpty()) {
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Course.messageN1",
										"查無歷年成績資料"));
						saveErrors(request, messages);
						log.error("No Score History... " + studentNo);
						sheetIndex--;
						continue;
					} else {

						firstYear = Short.parseShort(scoreHistList.get(0)
								.getSchoolYear().toString());
						lastYear = Short.parseShort(scoreHistList.get(
								scoreHistList.size() - 1).getSchoolYear()
								.toString());
						isOver = (lastYear - firstYear + 1) > 4;
						if (isOver)
							; // log.error("It is Over... " + studentNo);
						departCode = "";

						Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + title
								+ "學生歷年成績表");
						Toolket.setCellValue(sheet, 1, 1, "學號：" + studentNo);
						Toolket.setCellValue(sheet, 1, 7, "姓名："
								+ student.getStudentName());
						Toolket.setCellValue(sheet, 1, 9, "系所科別："
								+ (isMaster ? Toolket
										.getMasterDepartName(departClass)
										: Toolket.getDepartName(departClass))
								+ (isDoubleMajor || isAssist ? "("
										+ student.getExtraStatus() + ":"
										+ student.getExtraDept() + ")" : ""));
						Toolket.setCellValue(sheet, 1, 19, "身分證字號："
								+ student.getIdno());

						// 印2次,下面的code會刪除
						Toolket.setCellValue(sheet, 43, 0, "中華科技大學" + title
								+ "學生歷年成績表");
						Toolket.setCellValue(sheet, 44, 1, "學號：" + studentNo);
						Toolket.setCellValue(sheet, 44, 7, "姓名："
								+ student.getStudentName());
						Toolket.setCellValue(sheet, 44, 9, "系所科別："
								+ (isMaster ? Toolket
										.getMasterDepartName(departClass)
										: Toolket.getDepartName(departClass))
								+ (isDoubleMajor || isAssist ? "("
										+ student.getExtraStatus() + ":"
										+ student.getExtraDept() + ")" : ""));
						Toolket.setCellValue(sheet, 44, 19, "身分證字號："
								+ student.getIdno());

						if (isDelay) {
							Toolket.setCellValue(sheet, 43, 0, "中華科技大學" + title
									+ "學生歷年成績表");
							Toolket.setCellValue(sheet, 44, 1, "學號："
									+ studentNo);
							Toolket.setCellValue(sheet, 44, 7, "姓名："
									+ student.getStudentName());
							Toolket
									.setCellValue(
											sheet,
											44,
											9,
											"系所科別："
													+ (isMaster ? Toolket
															.getMasterDepartName(departClass)
															: Toolket
																	.getDepartName(departClass))
													+ (isDoubleMajor
															|| isAssist ? "("
															+ student
																	.getExtraStatus()
															+ ":"
															+ student
																	.getExtraDept()
															+ ")"
															: ""));
							Toolket.setCellValue(sheet, 44, 19, "身分證字號："
									+ student.getIdno());
						}

						if (isDoubleMajor || isAssist) {
							Toolket.setCellValue(sheet, 42, 0,
									" 符號說明 => *:不及格   #:輔系   &:雙主修   ");
							departCode = StringUtils.substring(student
									.getDepartClass(), 3, 4);
						}

						if (isOver) {
							Toolket.setCellValue(sheet, 43, 0, "中華科技大學" + title
									+ "學生歷年成績表");
							Toolket.setCellValue(sheet, 44, 1, "學號："
									+ studentNo);
							Toolket.setCellValue(sheet, 44, 7, "姓名："
									+ student.getStudentName());
							Toolket
									.setCellValue(
											sheet,
											44,
											9,
											"系所科別："
													+ (isMaster ? Toolket
															.getMasterDepartName(departClass)
															: Toolket
																	.getDepartName(departClass)));
							Toolket.setCellValue(sheet, 44, 19, "身分證字號："
									+ student.getIdno());
							if (isDoubleMajor || isAssist)
								Toolket.setCellValue(sheet, 85, 0,
										" 符號說明 => *:不及格   #:輔系   &:雙主修   ");
						}

						if (isMaster) {
							md = sm.findMasterByStudentNo(studentNo);
							if (md != null) {
								Toolket.setCellValue(sheet, 38, 0, "論文題目："
										+ md.getThesesChiname());
								Toolket.setCellValue(sheet, 39, 0, "          "
										+ md.getThesesEngname());
								Toolket.setCellValue(sheet, 38, 17, md
										.getRemark());
								Toolket.setCellValue(sheet, 40, 0, "學位考試成績："
										+ md.getThesesScore());
								Toolket.setCellValue(sheet, 40, 4,
										"學位考試成績(50%)：" + md.getThesesScore()
												/ 2);
								Toolket.setCellValue(sheet, 40, 8, "學業平均成績："
										+ md.getEvgr1Score());
								Toolket
										.setCellValue(sheet, 40, 14,
												"學業平均成績(50%)："
														+ md.getEvgr1Score()
														/ 2);
								Toolket.setCellValue(sheet, 40, 19, "總成績："
										+ md.getGraduateScore());
							}
						}

						if (Arrays.binarySearch(excep, departClass) >= 0) {
							lastYear++;
							isSpringClass = true;
							Toolket.setCellValue(sheet, 39, 17, "日期："
									+ (lastYear + 1) + ".1");
						}

						k = 0;
						j = 0;
						rowIndex = 6;
						nextPageRowIndex = 43;
						passCreditsSum = 0.0F;
						nextPage = false;
						hasPassRecord = false;
						rowOver = false;
						// List<Stavg> stavgs = null;
						// Just just = null;
						// String[] formYear = { "一", "二", "三", "四", "五", "六",
						// "七", "八" };
						for (short year = firstYear; year <= lastYear; year++) {
							// 判斷是否有歷年資料?可以9316D078為例測試
							// 不做判斷會出現歷年中間有空白資料
							scoreHist.setSchoolYear(year);
							scoreHist.setSchoolTerm("1");
							hasPassRecord = !sm.findScoreHistBy(scoreHist)
									.isEmpty();
							if (!hasPassRecord) {
								scoreHist.setSchoolTerm("2");
								hasPassRecord = !sm.findScoreHistBy(scoreHist)
										.isEmpty();
								if (!hasPassRecord)
									continue;
							}

							if (isSpringClass && year == lastYear) {
								just = sam.findJustByStudentNo(studentNo);
								justScore = 0.0D;
								if (just == null
										|| just.getTotalScore() == 0.0D) {
									target = new ScoreHist(studentNo);
									target.setSchoolYear(new Short(year));
									target.setSchoolTerm("2");
									target.setCscode("99999");
									scoreHistList = sm.findScoreHistBy(target);
									if (!scoreHistList.isEmpty()) {
										scoreHist = sm.findScoreHistBy(
												scoreHist).get(0);
										justScore = scoreHist.getScore();
									}
								} else
									justScore = just.getTotalScore();

								Toolket
										.setCellValue(
												sheet,
												35 + (isMaster ? -2 : 0),
												k * 6 + 3,
												justScore == 0.0D ? ""
														: String
																.valueOf(Math
																		.round(justScore)));
							}

							if (rowOver) {
								Toolket.setCellValue(workbook, sheet,
										2 + (nextPage ? nextPageRowIndex : 0),
										k * 6, "第    "
												+ formYear[(nextPage ? k + 4
														: k)] + "    學    年",
										fontSize12, HSSFCellStyle.ALIGN_CENTER,
										true, null);
							} else
								Toolket.setCellValue(sheet,
										2 + (nextPage ? nextPageRowIndex : 0),
										k * 6, "第    "
												+ formYear[(nextPage ? k + 4
														: k)] + "    學    年");
							position = new HashMap<String, String>();
							for (int term = 1; term <= 2; term++) {
								scoreHist.setSchoolYear(year);
								scoreHist.setSchoolTerm(String.valueOf(term));
								// scoreHist.setEvgrType("1"); // 正常選課
								scoreHistList = sm.findScoreHistBy(scoreHist);
								if (!scoreHistList.isEmpty()) {
									Toolket.setCellValue(sheet,
											3 + (nextPage ? nextPageRowIndex
													: 0), k * 6, "" + year
													+ " 年  9  月  至  "
													+ (year + 1) + " 年  7  月");
									stavgs = sm.findStavgBy(new Stavg(
											studentNo, year, String
													.valueOf(term)));
									totalPassCredits = 0.0F;
									// for (ScoreHist hist : scoreHistList) {
									// 判斷i != scoreHistList.size() -
									// 1是避免剛好一學年印完,結果多印一個全空的學年
									for (int i = 0; i < scoreHistList.size(); i++) {

										hist = scoreHistList.get(i);
										if (rowIndex == 32
												&& i != scoreHistList.size() - 1) {
											rowIndex = 6;
											k++;
											Toolket
													.setCellValue(
															sheet,
															2 + (nextPage ? nextPageRowIndex
																	: 0),
															k * 6,
															"第    "
																	+ formYear[k - 1]
																	+ "    學    年");
											Toolket
													.setCellValue(
															sheet,
															3 + (nextPage ? nextPageRowIndex
																	: 0),
															k * 6,
															""
																	+ year
																	+ " 年  9  月  至  "
																	+ (year + 1)
																	+ " 年  7  月");
											rowOver = true;
										}

										evgrType = hist.getEvgrType();
										isNotExemptAndMend = !"6"
												.equals(evgrType)
												&& !"5".equals(evgrType); // 判斷不是為抵免或待補?
										isExempt = "6".equals(evgrType); // 判斷是否為抵免?
										isMend = "5".equals(evgrType); // 判斷是否為待補?
										isAppend = "2".equals(evgrType); // 判斷是否為隨班?
										historyDepartCode = StringUtils
												.isBlank(hist
														.getStdepartClass()) ? ""
												: StringUtils.substring(hist
														.getStdepartClass(), 3,
														4);
										isNotSameDepartCode = !departCode
												.equals(historyDepartCode)
												&& !historyDepartCode
														.equals("0");
										opt = "";
										if ("3".equals(evgrType))
											opt = "暑";
										else
											opt = StringUtils.substring(
													Toolket.getCourseOpt(hist
															.getOpt()), 0, 1)
													+ (isAppend ? "隨"
															: (isMend ? "待"
																	: (!isNotExemptAndMend ? "抵"
																			: "")));

										cscode = hist.getCscode();
										if ("GA035".equals(cscode)
												|| "".equals("GB033"))
											continue; // 論文與技術報告2科目要排除

										isOver15Char = false;
										isOver10Char = false;
										if (!"99999".equals(cscode)) { // 排除操行
											String courseName = cm
													.findCourseInfoByCscode(cscode) == null ? "查無科目名稱"
													: cm
															.findCourseInfoByCscode(
																	cscode)
															.getChiName()
															.trim();
											isOver15Char = courseName.length() > 15;
											isOver10Char = courseName.length() > 10;
											Float credit = hist.getCredit();
											Float score = hist.getScore();
											boolean pass = (score == null ? 0.0F
													: score) >= passScore;
											// ((正常選課&&及格)||是抵免)&&不能是待補
											if (((isNotExemptAndMend && pass) || isExempt)
													&& !isMend)
												// if (!isNotExemptAndMend ||
												// !isMend ||
												// bFlag1)
												// if (!isMend && bFlag1)
												if (isMaster
														&& "G"
																.equalsIgnoreCase(StringUtils
																		.substring(
																				hist
																						.getStdepartClass(),
																				2,
																				3))) {
													// 如果碩士班學生去修其他非碩士班科目,不于加總學分數
													totalPassCredits += credit;
												} else
													totalPassCredits += credit;

											if (1 == term) {
												if (opt.length() == 1
														|| is96Entrance)
													Toolket
															.setCellValue(
																	sheet,
																	rowIndex,
																	k * 6,
																	(is96Entrance ? StringUtils
																			.substring(
																					opt,
																					0,
																					1)
																			: opt));
												else
													Toolket
															.setCellValue(
																	workbook,
																	sheet,
																	rowIndex,
																	k * 6,
																	opt,
																	fontSize12,
																	HSSFCellStyle.ALIGN_CENTER,
																	true, null);
												if (isOver15Char)
													Toolket
															.setCellValue(
																	workbook,
																	sheet,
																	rowIndex,
																	k * 6 + 1,
																	courseName,
																	fontSize9,
																	HSSFCellStyle.ALIGN_LEFT,
																	true, null);
												else if (isOver10Char)
													Toolket
															.setCellValue(
																	workbook,
																	sheet,
																	rowIndex,
																	k * 6 + 1,
																	courseName,
																	fontSize10,
																	HSSFCellStyle.ALIGN_LEFT,
																	true, null);
												else
													Toolket.setCellValue(sheet,
															rowIndex,
															k * 6 + 1,
															courseName);
												position.put(courseName, String
														.valueOf(rowIndex));

												if (isNotExemptAndMend) {
													// 各科學分
													String creditStr = credit
															.toString();
													if (!isMend && !isExempt)
														creditStr = pass ? creditStr
																: creditStr
																		+ "*";
													if (isDoubleMajor
															&& isNotSameDepartCode)
														creditStr += "&";
													if (isAssist
															&& isNotSameDepartCode)
														creditStr += "#";
													if (StringUtils.indexOf(
															creditStr, "*") == StringUtils.INDEX_NOT_FOUND)
														Toolket.setCellValue(
																sheet,
																rowIndex,
																k * 6 + 2,
																creditStr);
													else
														Toolket
																.setCellValue(
																		workbook,
																		sheet,
																		rowIndex,
																		k * 6 + 2,
																		creditStr,
																		colorFont,
																		HSSFCellStyle.ALIGN_CENTER,
																		true,
																		null);
													// 各科成績
													Toolket
															.setCellValue(
																	sheet,
																	rowIndex++,
																	k * 6 + 3,
																	(isMend ? "待"
																			: (score == null ? ""
																					: StringUtils
																							.substringBefore(
																									score
																											.toString(),
																									"."))));
													// 歷年平均成績
													Toolket
															.setCellValue(
																	sheet,
																	32
																			+ (isMaster ? -2
																					: 0)
																			+ (nextPage ? nextPageRowIndex
																					: 0),
																	k * 6 + 3,
																	(stavgs
																			.isEmpty() ? ""
																			: df
																					.format(stavgs
																							.get(
																									0)
																							.getScore() + 0.001D)));
												} else {
													// 各科學分
													String creditStr = credit
															.toString();
													Toolket.setCellValue(sheet,
															rowIndex,
															k * 6 + 2,
															creditStr);
													if (is96Entrance)
														Toolket.setCellValue(
																sheet,
																rowIndex++,
																k * 6 + 3,
																(isMend ? "待"
																		: "抵"));
													else {
														// 歷年平均成績
														Toolket
																.setCellValue(
																		sheet,
																		32
																				+ (isMaster ? -2
																						: 0)
																				+ (nextPage ? nextPageRowIndex
																						: 0),
																		k * 6 + 3,
																		(stavgs
																				.isEmpty() ? ""
																				: df
																						.format(stavgs
																								.get(
																										0)
																								.getScore() + 0.001D)));
														Toolket
																.setCellValue(
																		sheet,
																		rowIndex++,
																		k * 6 + 3,
																		score == null ? ""
																				: StringUtils
																						.substringBefore(
																								score
																										.toString(),
																								"."));
													}
												}
											} else {

												String endString = StringUtils
														.substring(
																courseName,
																courseName
																		.length() - 1);
												boolean duplicatedCourseName = "二"
														.equals(endString);
												boolean duplicatedCourseName1 = "四"
														.equals(endString);
												boolean duplicatedCourseName2 = "下"
														.equals(endString);

												if (duplicatedCourseName
														|| duplicatedCourseName1
														|| duplicatedCourseName2) {

													String courseIndex = StringUtils
															.substring(
																	courseName,
																	0,
																	courseName
																			.length() - 1);
													String row = "", maybeSameCourseNameOptInfo = "";
													if (duplicatedCourseName) {
														row = position
																.get(courseIndex
																		+ "一");
														if (row != null)
															maybeSameCourseNameOptInfo = Toolket
																	.getCellValue(
																			sheet,
																			Integer
																					.parseInt(row),
																			(rowOver ? k - 1
																					: k) * 6);
													} else if (duplicatedCourseName1) {
														row = position
																.get(courseIndex
																		+ "三");
														if (row != null)
															maybeSameCourseNameOptInfo = Toolket
																	.getCellValue(
																			sheet,
																			Integer
																					.parseInt(row),
																			(rowOver ? k - 1
																					: k) * 6);
													} else if (duplicatedCourseName2) {
														row = position
																.get(courseIndex
																		+ "上");
														if (row != null)
															maybeSameCourseNameOptInfo = Toolket
																	.getCellValue(
																			sheet,
																			Integer
																					.parseInt(row),
																			(rowOver ? k - 1
																					: k) * 6);
													}

													// 必須相同選別才可合併
													boolean isAllowMerge = (StringUtils
															.contains(opt, "必") && StringUtils
															.contains(
																	maybeSameCourseNameOptInfo,
																	"必"))
															|| (StringUtils
																	.contains(
																			opt,
																			"選") && StringUtils
																	.contains(
																			maybeSameCourseNameOptInfo,
																			"選"));
													if (row != null
															&& isAllowMerge) {
														int rowNum = Integer
																.parseInt(row);
														Toolket
																.setCellValue(
																		sheet,
																		rowNum,
																		(rowOver ? k - 1
																				: k) * 6 + 1,
																		Toolket
																				.getCellValue(
																						sheet,
																						rowNum,
																						(rowOver ? k - 1
																								: k) * 6 + 1)
																				+ "、"
																				+ (duplicatedCourseName ? "二"
																						: duplicatedCourseName1 ? "四"
																								: "下"));
														if (isNotExemptAndMend) {
															// 各科學分
															String creditStr = credit
																	.toString();
															if (!isMend
																	&& !isExempt)
																creditStr = pass ? creditStr
																		: creditStr
																				+ "*";
															if (isDoubleMajor
																	&& isNotSameDepartCode)
																creditStr += "&";
															if (isAssist
																	&& isNotSameDepartCode)
																creditStr += "#";
															if (StringUtils
																	.indexOf(
																			creditStr,
																			"*") == StringUtils.INDEX_NOT_FOUND)
																Toolket
																		.setCellValue(
																				sheet,
																				rowNum,
																				(rowOver ? k - 1
																						: k) * 6 + 4,
																				creditStr);
															else
																Toolket
																		.setCellValue(
																				workbook,
																				sheet,
																				rowNum,
																				(rowOver ? k - 1
																						: k) * 6 + 4,
																				creditStr,
																				colorFont,
																				HSSFCellStyle.ALIGN_CENTER,
																				true,
																				null);

															Toolket
																	.setCellValue(
																			sheet,
																			rowNum,
																			(rowOver ? k - 1
																					: k) * 6 + 5,
																			(isMend ? "待"
																					: (score == null ? ""
																							: StringUtils
																									.substringBefore(
																											score
																													.toString(),
																											"."))));
															Toolket
																	.setCellValue(
																			sheet,
																			32
																					+ (isMaster ? -2
																							: 0)
																					+ (nextPage ? nextPageRowIndex
																							: 0),
																			(rowOver ? k - 1
																					: k) * 6 + 5,
																			(stavgs
																					.isEmpty() ? ""
																					: df
																							.format(stavgs
																									.get(
																											0)
																									.getScore() + 0.001D)));
														} else {
															// 各科學分
															String creditStr = credit
																	.toString();
															Toolket
																	.setCellValue(
																			sheet,
																			rowNum,
																			(rowOver ? k - 1
																					: k) * 6 + 4,
																			creditStr);
															if (is96Entrance)
																Toolket
																		.setCellValue(
																				sheet,
																				rowNum,
																				(rowOver ? k - 1
																						: k) * 6 + 5,
																				(isMend ? "待"
																						: "抵"));
															else {
																Toolket
																		.setCellValue(
																				sheet,
																				32 + (isMaster ? -2
																						: 0),
																				(rowOver ? k - 1
																						: k) * 6 + 5,
																				(stavgs
																						.isEmpty() ? ""
																						: df
																								.format(stavgs
																										.get(
																												0)
																										.getScore() + 0.001D)));
																Toolket
																		.setCellValue(
																				sheet,
																				rowNum,
																				(rowOver ? k - 1
																						: k) * 6 + 5,
																				score == null ? ""
																						: StringUtils
																								.substringBefore(
																										score
																												.toString(),
																										"."));
															}
														}
													} else {
														if (opt.length() == 1
																|| is96Entrance)
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex,
																			k * 6,
																			(is96Entrance ? StringUtils
																					.substring(
																							opt,
																							0,
																							1)
																					: opt));
														else
															Toolket
																	.setCellValue(
																			workbook,
																			sheet,
																			rowIndex,
																			k * 6,
																			opt,
																			fontSize12,
																			HSSFCellStyle.ALIGN_CENTER,
																			true,
																			null);
														Toolket.setCellValue(
																sheet,
																rowIndex,
																k * 6 + 1,
																courseName);
														position
																.put(
																		courseName,
																		String
																				.valueOf(rowIndex));

														if (isNotExemptAndMend) {
															// 各科學分
															String creditStr = credit
																	.toString();
															if (!isMend
																	&& !isExempt)
																creditStr = pass ? creditStr
																		: creditStr
																				+ "*";
															if (isDoubleMajor
																	&& isNotSameDepartCode)
																creditStr += "&";
															if (isAssist
																	&& isNotSameDepartCode)
																creditStr += "#";
															if (StringUtils
																	.indexOf(
																			creditStr,
																			"*") == StringUtils.INDEX_NOT_FOUND)
																Toolket
																		.setCellValue(
																				sheet,
																				rowIndex,
																				k * 6 + 4,
																				creditStr);
															else
																Toolket
																		.setCellValue(
																				workbook,
																				sheet,
																				rowIndex,
																				k * 6 + 4,
																				creditStr,
																				colorFont,
																				HSSFCellStyle.ALIGN_CENTER,
																				true,
																				null);

															// 各科成績
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex++,
																			k * 6 + 5,
																			(isMend ? "待"
																					: (score == null ? ""
																							: StringUtils
																									.substringBefore(
																											score
																													.toString(),
																											"."))));
															// 歷年平均成績
															Toolket
																	.setCellValue(
																			sheet,
																			32 + (isMaster ? -2
																					: 0),
																			k * 6 + 5,
																			(stavgs
																					.isEmpty() ? ""
																					: df
																							.format(stavgs
																									.get(
																											0)
																									.getScore() + 0.001D)));
														} else {
															// 各科學分
															String creditStr = credit
																	.toString();
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex,
																			k * 6 + 4,
																			creditStr);
															if (is96Entrance)
																Toolket
																		.setCellValue(
																				sheet,
																				rowIndex++,
																				k * 6 + 5,
																				(isMend ? "待"
																						: "抵"));
															else {
																// 歷年平均成績
																Toolket
																		.setCellValue(
																				sheet,
																				32 + (isMaster ? -2
																						: 0),
																				k * 6 + 5,
																				(stavgs
																						.isEmpty() ? ""
																						: df
																								.format(stavgs
																										.get(
																												0)
																										.getScore() + 0.001D)));
																Toolket
																		.setCellValue(
																				sheet,
																				rowIndex++,
																				k * 6 + 5,
																				score == null ? ""
																						: StringUtils
																								.substringBefore(
																										score
																												.toString(),
																										"."));
															}
														}
													}
												} else {
													if (opt.length() == 1
															|| is96Entrance)
														Toolket
																.setCellValue(
																		sheet,
																		rowIndex,
																		k * 6,
																		(is96Entrance ? StringUtils
																				.substring(
																						opt,
																						0,
																						1)
																				: opt));
													else
														Toolket
																.setCellValue(
																		workbook,
																		sheet,
																		rowIndex,
																		k * 6,
																		opt,
																		fontSize12,
																		HSSFCellStyle.ALIGN_CENTER,
																		true,
																		null);
													Toolket.setCellValue(sheet,
															rowIndex,
															k * 6 + 1,
															courseName);
													// 各科學分
													String creditStr = credit
															.toString();
													if (!isMend && !isExempt)
														creditStr = pass ? creditStr
																: creditStr
																		+ "*";
													if (isDoubleMajor
															&& isNotSameDepartCode)
														creditStr += "&";
													if (isAssist
															&& isNotSameDepartCode)
														creditStr += "#";
													if (StringUtils.indexOf(
															creditStr, "*") == StringUtils.INDEX_NOT_FOUND)
														Toolket.setCellValue(
																sheet,
																rowIndex,
																k * 6 + 4,
																creditStr);
													else
														Toolket
																.setCellValue(
																		workbook,
																		sheet,
																		rowIndex,
																		k * 6 + 4,
																		creditStr,
																		colorFont,
																		HSSFCellStyle.ALIGN_CENTER,
																		true,
																		null);

													if (isNotExemptAndMend) {
														Toolket
																.setCellValue(
																		sheet,
																		rowIndex++,
																		k * 6 + 5,
																		(isMend ? "待"
																				: (score == null ? ""
																						: StringUtils
																								.substringBefore(
																										score
																												.toString(),
																										"."))));
														Toolket
																.setCellValue(
																		sheet,
																		32 + (isMaster ? -2
																				: 0),
																		k * 6 + 5,
																		(stavgs
																				.isEmpty() ? ""
																				: df
																						.format(stavgs
																								.get(
																										0)
																								.getScore() + 0.001D)));
													} else {
														if (is96Entrance)
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex++,
																			k * 6 + 5,
																			(isMend ? "待"
																					: "抵"));
														else {
															Toolket
																	.setCellValue(
																			sheet,
																			32 + (isMaster ? -2
																					: 0),
																			k * 6 + 5,
																			(stavgs
																					.isEmpty() ? ""
																					: df
																							.format(stavgs
																									.get(
																											0)
																									.getScore() + 0.001D)));
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex++,
																			k * 6 + 5,
																			score == null ? ""
																					: StringUtils
																							.substringBefore(
																									score
																											.toString(),
																									"."));
														}
													}
												}

											}
										} else {
											// 操行
											if (1 == term) {
												if (isNotExemptAndMend) {
													Toolket
															.setCellValue(
																	sheet,
																	35
																			+ (isMaster ? -2
																					: 0)
																			+ (nextPage ? nextPageRowIndex
																					: 0),
																	k * 6 + 3,
																	String
																			.valueOf(Math
																					.round(hist
																							.getScore())));
												} else {
													if (!is96Entrance)
														Toolket
																.setCellValue(
																		sheet,
																		35 + (isMaster ? -2
																				: 0),
																		k * 6 + 3,
																		hist
																				.getScore() == null ? ""
																				: String
																						.valueOf(Math
																								.round((hist
																										.getScore()))));
												}
											} else {
												if (isNotExemptAndMend) {
													Toolket
															.setCellValue(
																	sheet,
																	35
																			+ (isMaster ? -2
																					: 0)
																			+ (nextPage ? nextPageRowIndex
																					: 0),
																	k * 6 + 5,
																	String
																			.valueOf(Math
																					.round(hist
																							.getScore())));
												} else {
													if (!is96Entrance)
														Toolket
																.setCellValue(
																		sheet,
																		35 + (isMaster ? -2
																				: 0),
																		k * 6 + 5,
																		hist
																				.getScore() == null ? ""
																				: String
																						.valueOf(Math
																								.round(hist
																										.getScore())));
												}
											}
										}

									}

									// 處理學分數與累計學分數
									if (1 == term) {
										Toolket.setCellValue(sheet, 33
												+ (isMaster ? -2 : 0)
												+ (nextPage ? nextPageRowIndex
														: 0), k * 6 + 2,
												totalPassCredits.toString());
										Toolket
												.setCellValue(
														sheet,
														34
																+ (isMaster ? -2
																		: 0)
																+ (nextPage ? nextPageRowIndex
																		: 0),
														k * 6 + 2,
														(passCreditsSum += totalPassCredits)
																.toString());
									} else {
										Toolket.setCellValue(sheet, 33
												+ (isMaster ? -2 : 0)
												+ (nextPage ? nextPageRowIndex
														: 0), k * 6 + 4,
												totalPassCredits.toString());
										Toolket
												.setCellValue(
														sheet,
														34
																+ (isMaster ? -2
																		: 0)
																+ (nextPage ? nextPageRowIndex
																		: 0),
														k * 6 + 4,
														(passCreditsSum += totalPassCredits)
																.toString());
									}
								}
								// k--;
							}

							gmark = new Gmark();
							gmark.setSchoolYear(year);
							gmark.setStudentNo(studentNo);
							gmarks = cm.findGMarkBy(gmark);
							if (!gmarks.isEmpty()) {
								StringBuilder builder = new StringBuilder();
								for (Gmark gm : gmarks)
									builder.append(gm.getRemark()).append(
											"\n      ");
								int remarkCols = 0;
								switch (k) {
									case 0:
										remarkCols = 0;
										break;
									case 1:
										remarkCols = 6;
										break;
									case 2:
										remarkCols = 12;
										break;
									case 3:
										remarkCols = 18;
										break;
								}
								Toolket.setCellValue(sheet, 36, remarkCols,
										"附註：" + builder.toString());
							}

							if (rowOver) {
								Toolket.setCellValue(sheet, 32,
										(k - 1) * 6 + 5, "");
								Toolket.setCellValue(sheet, 35, k * 6 + 5,
										Toolket.getCellValue(sheet, 35,
												(k - 1) * 6 + 5));
								Toolket.setCellValue(sheet, 35,
										(k - 1) * 6 + 5, "");
							}

							kk = k;
							rowIndexx = rowIndex;
							k++;
							j++;
							rowIndex = j >= 4 ? 49 : 6;
							// rowIndexx = j >= 4 ? 49 : 6;
							if (j == 4) {
								// rowIndex = 49;
								k = 0;
								nextPage = true;
							}
						}

					}

					if (isIncludeThisTermScore) {
						// Exception Students : 92148059, 92146011, 93148066,
						// 9144037, 914C009, 96164016, 9214D093
						AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
						Integer year = Integer.valueOf(am
								.findTermBy(PARAMETER_SCHOOL_YEAR));
						boolean isSameYear = lastYear == year;
						// 2010.04.02將isNeedCheck1由true改成false
						// 因為不改成false,全班歷年成績中154D41的931AD026最後一學期會錯???
						// 忘記為何當初設定為true
						boolean isNeedCheck = true, isNeedCheck1 = true;
						if (!isSameYear) {
							isNeedCheck1 = false;
							kk++;
							if (nextPage) {
								if (kk == 4)
									kk = 0;
							}

							Toolket.setCellValue(sheet, (nextPage ? 45 : 2),
									kk * 6, "第    "
											+ formYear[(nextPage ? k + 4 : k)]
											+ "    學    年");
							Toolket.setCellValue(sheet, (nextPage ? 46 : 3),
									kk * 6, "" + year + " 年  9  月  至  "
											+ (year + 1) + " 年  7  月");
							rowIndexx = nextPage ? 49 : 6;
						}

						if (!nextPage && rowIndexx >= 32) {
							// 有學生會超過,例:96164016
							Toolket
									.setCellValue(
											sheet,
											(nextPage ? 45 : 2),
											(kk + 1) * 6,
											"第    "
													+ formYear[(nextPage ? kk + 4
															: kk)]
													+ "    學    年");
							Toolket.setCellValue(sheet, (nextPage ? 46 : 3),
									(kk + 1) * 6, "" + year + " 年  9  月  至  "
											+ (year + 1) + " 年  7  月");
							rowIndexx = nextPage ? 49 : 6;
							kk++;
						}

						isNeedCheck = true;
						// passCreditsSum = 0.0F;
						totalPassCredits = 0.0F;
						selds = cm.findSeldByStudentNoAndTerm(studentNo, sterm);
						// boolean isNewSchoolYear = "1".equals(sterm);
						if (!selds.isEmpty()) {
							for (Seld seld : selds) {
								dtime = cm.findDtimeBy(seld.getDtimeOid());
								csnos = cm.getCsnameBy(dtime.getCscode());
								if (!csnos.isEmpty())
									csno = (Csno) csnos.get(0);
								Toolket.setCellValue(sheet, rowIndexx, kk * 6,
										StringUtils.substring(Toolket
												.getCourseOpt(dtime.getOpt()),
												0, 1));
								Toolket.setCellValue(sheet, rowIndexx,
										kk * 6 + 1, csno == null ? "" : csno
												.getChiName());
								totalPassCredits += dtime.getCredit();
								if ("1".equals(sterm)) {
									Toolket.setCellValue(sheet, rowIndexx,
											kk * 6 + 2, dtime.getCredit()
													.toString());
									Toolket.setCellValue(sheet, rowIndexx++,
											kk * 6 + 3, String
													.valueOf(passScore));
								} else {
									Toolket.setCellValue(sheet, rowIndexx,
											kk * 6 + 4, dtime.getCredit()
													.toString());
									Toolket.setCellValue(sheet, rowIndexx++,
											kk * 6 + 5, String.valueOf(Math
													.round(passScore)));
								}

								if (isNeedCheck && isNeedCheck1 && !isSameYear
										&& rowIndexx >= 32) {
									isNeedCheck = false;
									kk++;
									if (kk % 4 == 0) {
										kk = 0;
										rowIndexx = nextPage ? 49 : 6;
									}

									// Toolket.setCellValue(sheet, 43, 0,
									// "中華科技大學"
									// + title + "學生歷年成績表");
									// Toolket.setCellValue(sheet, 44, 1, "學號："
									// + studentNo);
									// Toolket.setCellValue(sheet, 44, 7, "姓名："
									// + student.getStudentName());
									// Toolket
									// .setCellValue(
									// sheet,
									// 44,
									// 9,
									// "系所科別："
									// + (isMaster ? Toolket
									// .getMasterDepartName(departClass)
									// : Toolket
									// .getDepartName(departClass))
									// + (isDoubleMajor
									// || isAssist ? "("
									// + student
									// .getExtraStatus()
									// + ":"
									// + student
									// .getExtraDept()
									// + ")"
									// : ""));
									// Toolket.setCellValue(sheet, 44, 19,
									// "身份證字號："
									// + student.getIdno());
									Toolket
											.setCellValue(
													sheet,
													(nextPage ? 45 : 2),
													kk * 6,
													"第    "
															+ formYear[(nextPage ? kk + 4
																	: kk)]
															+ "    學    年");
									Toolket.setCellValue(sheet, (nextPage ? 46
											: 3), kk * 6, "" + year
											+ " 年  9  月  至  " + (year + 1)
											+ " 年  7  月");
									// rowIndexx = 6;
									// kk++;
								}
							}

							passCreditsSum += totalPassCredits;
							if ("1".equals(sterm)) {
								Toolket.setCellValue(sheet, (nextPage && j >= 4
										&& rowIndexx >= 49 ? 76 : 33)
										+ (isMaster ? -2 : 0), kk * 6 + 2,
										totalPassCredits.toString());
								Toolket.setCellValue(sheet, (nextPage && j >= 4
										&& rowIndexx >= 49 ? 77 : 34)
										+ (isMaster ? -2 : 0), kk * 6 + 2,
										passCreditsSum.toString());
							} else {
								Toolket.setCellValue(sheet, (nextPage && j >= 4
										&& rowIndexx >= 49 ? 76 : 33)
										+ (isMaster ? -2 : 0), kk * 6 + 4,
										totalPassCredits.toString());
								Toolket.setCellValue(sheet, (nextPage && j >= 4
										&& rowIndexx >= 49 ? 77 : 34)
										+ (isMaster ? -2 : 0), kk * 6 + 4,
										passCreditsSum.toString());
							}

							gmark = new Gmark();
							gmark.setSchoolYear(Short.valueOf(year.toString()));
							gmark.setStudentNo(studentNo);
							gmarks = cm.findGMarkBy(gmark);
							if (!gmarks.isEmpty()) {
								StringBuilder builder = new StringBuilder();
								for (Gmark gm : gmarks)
									builder.append(gm.getRemark()).append(
											"\n      ");
								int remarkCols = 0;
								switch (k) {
									case 0:
										remarkCols = 0;
										break;
									case 1:
										remarkCols = 6;
										break;
									case 2:
										remarkCols = 12;
										break;
									case 3:
										remarkCols = 18;
										break;
								}
								Toolket.setCellValue(sheet, 36, remarkCols,
										"附註：" + builder.toString());
							}

							String[] excluded = { "15", "64", "54", "A4", "B4" };
							boolean hasInclude = ArrayUtils.contains(excluded,
									StringUtils.substring(departClass, 1, 3));
							if (!isDelay && !hasInclude && rowIndexx <= 42)
								Toolket.removeRow(sheet, 43, 45);
						}
					} else {
						String[] excluded = { "15", "64", "54", "A4", "B4" };
						boolean hasInclude = ArrayUtils.contains(excluded,
								StringUtils.substring(departClass, 1, 3));
						if (!isDelay && !hasInclude && rowIndexx <= 42)
							Toolket.removeRow(sheet, 43, 45);
					}
				}

				File tempDir = new File(context
						.getRealPath("/WEB-INF/reports/temp/"
								+ getUserCredential(session).getMember()
										.getIdno()
								+ (new SimpleDateFormat("yyyyMMdd")
										.format(new Date()))));
				if (!tempDir.exists())
					tempDir.mkdirs();

				File output = new File(tempDir, departClass + ".xls");
				FileOutputStream fos = new FileOutputStream(output);
				workbook.write(fos);
				fos.close();

				JasperReportUtils.printXlsToFrontEnd(response, output);
				output.delete();
				tempDir.delete();
				// return null;
			} else {
				ServletContext context = request.getSession()
						.getServletContext();
				Map<String, String> parameters = new HashMap<String, String>();
				File image = new File(context
						.getRealPath("/pages/reports/2002chitS.jpg"));
				parameters.put("IMAGE", image.getAbsolutePath());
				byte[] bytes = JasperRunManager.runReportToPdf(
						JasperReportUtils.getNoResultReport(context),
						parameters, new JREmptyDataSource());
				JasperReportUtils.printPdfToFrontEnd(response, bytes);
			}

		}
	}

	/**
	 * 只是為了幫註冊組可以列印96年資料而用
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	protected void printClassStudentsScoreHistory4CreditClass(
			ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		boolean isIncludeThisTermScore = "true".equalsIgnoreCase(request
				.getParameter("tt"));

		// String year = cm.getNowBy("School_year");
		// String term = form.getString("sterm");
		String studentNo = null;
		Clazz clazz = null;
		ScoreHist scoreHist = null, hist = null;
		List<Student> students = null;
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);

		if (!clazzes.isEmpty() && clazzes.size() > 1) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("",
					""));
			saveMessages(request, messages);
			// return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			ServletContext context = request.getSession().getServletContext();
			Map<String, String> parameters = new HashMap<String, String>();
			File image = new File(context
					.getRealPath("/pages/reports/2002chitS.jpg"));
			parameters.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager.runReportToPdf(JasperReportUtils
					.getNoResultReport(context), parameters,
					new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		} else {

			clazz = clazzes.get(0);
			students = mm.findStudentsByClassNo(clazz.getClassNo());
			if (!students.isEmpty()) {

				ServletContext context = request.getSession()
						.getServletContext();
				String[] excep = { "11G332" }; // 春季班學年要+1
				String[] formYear = { "一", "二", "三", "四", "五", "六", "七", "八" };

				short firstYear = 0;
				short lastYear = 0;

				double justScore = 0.0D;

				int sheetIndex = 0;
				int k = 0, j = 0, rowIndex = 0, nextPageRowIndex = 0;
				int kk = 0, rowIndexx = 0;

				String departClass = clazz.getClassNo();
				String departCode = "", title = "", evgrType = "", historyDepartCode = "", opt = "", cscode = "";

				// 碩士班70分及格
				float passScore = Toolket
						.getPassScoreByDepartClass(departClass);

				Graduate graduate = null;
				MasterData md = null;
				Just just = null;
				ScoreHist target = null;
				Dtime dtime = null;
				List csnos = null;
				Csno csno = null;
				Gmark gmark = null;

				Map<String, String> position = null;

				Float passCreditsSum = 0.0F;
				Float totalPassCredits = 0.0F;

				List<Seld> selds = null;
				List<ScoreHist> scoreHistList = null;
				List<Stavg> stavgs = null;
				List<Gmark> gmarks = null;

				boolean isOver = false; // (lastYear - firstYear + 1) > 4;
				boolean isDelay = Toolket.isDelayClass(departClass);
				boolean isSpringClass = false;
				// 雙主修
				boolean isDoubleMajor = false;
				boolean isAssist = false;
				// 9608前入學成績單要Show完整平均成績及操行,但之後入學的只Show'抵'字,且平均成績及操行不Show
				boolean is96Entrance = false;
				boolean isMaster = Toolket.isMasterClass(departClass);
				boolean nextPage = false, hasPassRecord = false, rowOver = false;
				boolean isNotExemptAndMend = false; // 判斷不是為抵免或待補?
				boolean isExempt = false; // 判斷是否為抵免?
				boolean isMend = false; // 判斷是否為待補?
				boolean isAppend = false; // 判斷是否為隨班?
				boolean isNotSameDepartCode = false;

				int studentCounts = students.size();
				String fileName = "";

				DecimalFormat df = new DecimalFormat(",##0.0");

				File templateXLS = null;

				if (!isMaster) {

					/*if (studentCounts <= 30)
						fileName = "ClassStudentsScoreHistoryOver30.xls";
					else if (studentCounts > 31 && studentCounts <= 40)
						fileName = "ClassStudentsScoreHistoryOver40.xls";
					else if (studentCounts > 40 && studentCounts <= 45)
						fileName = "ClassStudentsScoreHistoryOver45.xls";
					else if (studentCounts > 45 && studentCounts <= 50)
						fileName = "ClassStudentsScoreHistoryOver50.xls";
					else if (studentCounts > 51 && studentCounts <= 55)
						fileName = "ClassStudentsScoreHistoryOver55.xls";
					else*/
						fileName = "ClassStudentsScoreHistoryOver60.xls";

					title = Toolket.getSchoolFormalName(departClass);
					if (isDelay) {
						// Only 20 Sheets
						templateXLS = new File(
								context
										.getRealPath("/WEB-INF/reports/ClassStudentsScoreHistoryOver.xls"));
					} else {
						templateXLS = new File(context
								.getRealPath("/WEB-INF/reports/" + fileName));
					}
				} else {

					if (studentCounts <= 15)
						fileName = "ClassStudentsScoreHistoryMaster15.xls";
					else if (studentCounts > 15 && studentCounts <= 20)
						fileName = "ClassStudentsScoreHistoryMaster20.xls";
					else
						fileName = "ClassStudentsScoreHistoryMaster25.xls";

					String masterId = StringUtils.substring(departClass, 1, 3);
					if ("1G".equals(masterId))
						title = "日間部碩士班";
					else
						title = "進修部碩士在職專班";
					templateXLS = new File(context
							.getRealPath("/WEB-INF/reports/" + fileName));
				}

				HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

				HSSFFont colorFont = workbook.createFont();
				colorFont.setColor(HSSFColor.RED.index);
				colorFont.setFontHeightInPoints((short) 12);
				colorFont.setFontName("Arial Unicode MS");

				HSSFFont fontSize12 = workbook.createFont();
				fontSize12.setFontHeightInPoints((short) 12);
				fontSize12.setFontName("Arial Unicode MS");
				HSSFSheet sheet = null;

				HSSFFont fontSize10 = workbook.createFont();
				fontSize10.setFontHeightInPoints((short) 10);
				fontSize10.setFontName("Arial Unicode MS");

				for (Student student : students) {

					studentNo = student.getStudentNo();
					sheet = workbook.getSheetAt(sheetIndex);
					workbook
							.setSheetName(sheetIndex++, studentNo.toUpperCase());
					if (student == null) {
						graduate = mm.findGraduateByStudentNo(studentNo);
						student = new Student();
						BeanUtils.copyProperties(student, graduate);
					}

					// 雙主修
					isDoubleMajor = "雙修".equals(student.getExtraStatus());
					isAssist = "輔修".equals(student.getExtraStatus());

					// departClass = student.getDepartClass();
					// 9608前入學成績單要Show完整平均成績及操行,但之後入學的只Show'抵'字,且平均成績及操行不Show
					is96Entrance = student.getEntrance() == null ? false
							: student.getEntrance() >= (short) 9608;
					scoreHist = new ScoreHist(studentNo);
					scoreHistList = sm.findScoreHistBy(scoreHist);
					if (scoreHistList.isEmpty()) {
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Course.messageN1",
										"查無歷年成績資料"));
						saveErrors(request, messages);
						log.error("No Score History... " + studentNo);
						sheetIndex--;
						continue;
					} else {

						firstYear = Short.parseShort(scoreHistList.get(0)
								.getSchoolYear().toString());
						lastYear = Short.parseShort(scoreHistList.get(
								scoreHistList.size() - 1).getSchoolYear()
								.toString());
						isOver = (lastYear - firstYear + 1) > 4;
						if (isOver)
							; // log.error("It is Over... " + studentNo);
						departCode = "";

						Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + title
								+ "學生歷年成績表");
						Toolket.setCellValue(sheet, 1, 1, "學號：" + studentNo);
						Toolket.setCellValue(sheet, 1, 7, "姓名："
								+ student.getStudentName());
						Toolket.setCellValue(sheet, 1, 9, "系所科別："
								+ (isMaster ? Toolket
										.getMasterDepartName(departClass)
										: Toolket.getDepartName(departClass))
								+ (isDoubleMajor || isAssist ? "("
										+ student.getExtraStatus() + ":"
										+ student.getExtraDept() + ")" : ""));
						Toolket.setCellValue(sheet, 1, 19, "身分證字號："
								+ student.getIdno());

						// 印2次,下面的code會刪除
						Toolket.setCellValue(sheet, 43, 0, "中華科技大學" + title
								+ "學生歷年成績表");
						Toolket.setCellValue(sheet, 44, 1, "學號：" + studentNo);
						Toolket.setCellValue(sheet, 44, 7, "姓名："
								+ student.getStudentName());
						Toolket.setCellValue(sheet, 44, 9, "系所科別："
								+ (isMaster ? Toolket
										.getMasterDepartName(departClass)
										: Toolket.getDepartName(departClass))
								+ (isDoubleMajor || isAssist ? "("
										+ student.getExtraStatus() + ":"
										+ student.getExtraDept() + ")" : ""));
						Toolket.setCellValue(sheet, 44, 19, "身分證字號："
								+ student.getIdno());

						if (isDelay) {
							Toolket.setCellValue(sheet, 43, 0, "中華科技大學" + title
									+ "學生歷年成績表");
							Toolket.setCellValue(sheet, 44, 1, "學號："
									+ studentNo);
							Toolket.setCellValue(sheet, 44, 7, "姓名："
									+ student.getStudentName());
							Toolket
									.setCellValue(
											sheet,
											44,
											9,
											"系所科別："
													+ (isMaster ? Toolket
															.getMasterDepartName(departClass)
															: Toolket
																	.getDepartName(departClass))
													+ (isDoubleMajor
															|| isAssist ? "("
															+ student
																	.getExtraStatus()
															+ ":"
															+ student
																	.getExtraDept()
															+ ")"
															: ""));
							Toolket.setCellValue(sheet, 44, 19, "身分證字號："
									+ student.getIdno());
						}

						if (isDoubleMajor || isAssist) {
							Toolket.setCellValue(sheet, 42, 0,
									" 符號說明 => *:不及格   #:輔系   &:雙主修   ");
							departCode = StringUtils.substring(student
									.getDepartClass(), 3, 4);
						}

						if (isOver) {
							Toolket.setCellValue(sheet, 43, 0, "中華科技大學" + title
									+ "學生歷年成績表");
							Toolket.setCellValue(sheet, 44, 1, "學號："
									+ studentNo);
							Toolket.setCellValue(sheet, 44, 7, "姓名："
									+ student.getStudentName());
							Toolket
									.setCellValue(
											sheet,
											44,
											9,
											"系所科別："
													+ (isMaster ? Toolket
															.getMasterDepartName(departClass)
															: Toolket
																	.getDepartName(departClass)));
							Toolket.setCellValue(sheet, 44, 19, "身分證字號："
									+ student.getIdno());
							if (isDoubleMajor || isAssist)
								Toolket.setCellValue(sheet, 85, 0,
										" 符號說明 => *:不及格   #:輔系   &:雙主修   ");
						}

						if (isMaster) {
							md = sm.findMasterByStudentNo(studentNo);
							if (md != null) {
								Toolket.setCellValue(sheet, 38, 0, "論文題目："
										+ md.getThesesChiname());
								Toolket.setCellValue(sheet, 39, 0, "          "
										+ md.getThesesEngname());
								Toolket.setCellValue(sheet, 38, 17, md
										.getRemark());
								Toolket.setCellValue(sheet, 40, 0, "學位考試成績："
										+ md.getThesesScore());
								Toolket.setCellValue(sheet, 40, 4,
										"學位考試成績(50%)：" + md.getThesesScore()
												/ 2);
								Toolket.setCellValue(sheet, 40, 8, "學業平均成績："
										+ md.getEvgr1Score());
								Toolket
										.setCellValue(sheet, 40, 14,
												"學業平均成績(50%)："
														+ md.getEvgr1Score()
														/ 2);
								Toolket.setCellValue(sheet, 40, 19, "總成績："
										+ md.getGraduateScore());
							}
						}

						if (Arrays.binarySearch(excep, departClass) >= 0) {
							lastYear++;
							isSpringClass = true;
							Toolket.setCellValue(sheet, 39, 17, "日期："
									+ (lastYear + 1) + ".1");
						}

						k = 0;
						j = 0;
						rowIndex = 6;
						nextPageRowIndex = 43;
						passCreditsSum = 0.0F;
						nextPage = false;
						hasPassRecord = false;
						rowOver = false;
						// List<Stavg> stavgs = null;
						// Just just = null;
						// String[] formYear = { "一", "二", "三", "四", "五", "六",
						// "七", "八" };

						// 先將所有抵免的Show在第一年
						scoreHist.setEvgrType("6");
						scoreHistList = sm.findScoreHistBy(scoreHist);
						Float creditSum1 = 0.0F, creditSum2 = 0.0F;
						String courseName = null;
						if (!scoreHistList.isEmpty()) {

							rowIndex = 6;
							// position = new HashMap<String, String>();
							// position1 = new HashMap<String, String>();

							Toolket.setCellValue(sheet,
									2 + (nextPage ? nextPageRowIndex : 0),
									k * 6, "抵  免  成  績");
							for (int i = 0; i < scoreHistList.size(); i++) {
								hist = scoreHistList.get(i);
								Toolket.setCellValue(sheet, rowIndex, 0,
										StringUtils.substring(Toolket
												.getCourseOpt(hist.getOpt()),
												0, 1));
								courseName = cm.findCourseInfoByCscode(hist
										.getCscode()) == null ? "查無科目名稱" : cm
										.findCourseInfoByCscode(
												hist.getCscode()).getChiName()
										.trim();
								if (courseName.length() > 10)
									Toolket.setCellValue(workbook, sheet,
											rowIndex, 1, courseName,
											fontSize10,
											HSSFCellStyle.ALIGN_LEFT, true,
											null);
								else
									Toolket.setCellValue(sheet, rowIndex, 1,
											courseName);

								if (hist.getSchoolTerm().equals("1")) {
									Toolket.setCellValue(sheet, rowIndex, 2,
											hist.getCredit().toString());
									creditSum1 += hist.getCredit();
									Toolket.setCellValue(sheet, rowIndex, 3,
											"抵");
								} else {
									Toolket.setCellValue(sheet, rowIndex, 4,
											hist.getCredit().toString());
									creditSum2 += hist.getCredit();
									Toolket.setCellValue(sheet, rowIndex, 5,
											"抵");
								}
								rowIndex++;
							}
							Toolket.setCellValue(sheet, 33, 2, String
									.valueOf(creditSum1));
							Toolket.setCellValue(sheet, 33, 4, String
									.valueOf(creditSum2));
							Toolket.setCellValue(sheet, 34, 2, String
									.valueOf(creditSum1));
							Toolket.setCellValue(sheet, 34, 4, String
									.valueOf(creditSum1 + creditSum2));
						}

						rowIndex = 6;
						passCreditsSum = creditSum1 + creditSum2;
						scoreHist.setEvgrType(null); // 免得只查到抵免的
						k = 1; // 歷年成績從第2大欄開始
						for (short year = firstYear; year <= lastYear; year++) {
							// 判斷是否有歷年資料?可以9316D078為例測試
							// 不做判斷會出現歷年中間有空白資料
							scoreHist.setSchoolYear(year);
							scoreHist.setSchoolTerm("1");
							hasPassRecord = !sm.findScoreHistBy(scoreHist)
									.isEmpty();
							if (!hasPassRecord) {
								scoreHist.setSchoolTerm("2");
								hasPassRecord = !sm.findScoreHistBy(scoreHist)
										.isEmpty();
								if (!hasPassRecord)
									continue;
							}

							if (isSpringClass && year == lastYear) {
								just = sam.findJustByStudentNo(studentNo);
								justScore = 0.0D;
								if (just == null
										|| just.getTotalScore() == 0.0D) {
									target = new ScoreHist(studentNo);
									target.setSchoolYear(new Short(year));
									target.setSchoolTerm("2");
									target.setCscode("99999");
									scoreHistList = sm.findScoreHistBy(target);
									if (!scoreHistList.isEmpty()) {
										scoreHist = sm.findScoreHistBy(
												scoreHist).get(0);
										justScore = scoreHist.getScore();
									}
								} else
									justScore = just.getTotalScore();

								Toolket
										.setCellValue(
												sheet,
												35 + (isMaster ? -2 : 0),
												k * 6 + 3,
												justScore == 0.0D ? ""
														: String
																.valueOf(Math
																		.round(justScore)));
							}

							if (rowOver) {
								Toolket.setCellValue(workbook, sheet,
										2 + (nextPage ? nextPageRowIndex : 0),
										k * 6, "第    "
												+ formYear[(nextPage ? k + 4
														: k)] + "    學    年",
										fontSize12, HSSFCellStyle.ALIGN_CENTER,
										true, null);
							} else
								Toolket.setCellValue(sheet,
										2 + (nextPage ? nextPageRowIndex : 0),
										k * 6, "第    "
												+ formYear[(nextPage ? k + 4
														: k)] + "    學    年");
							position = new HashMap<String, String>();
							for (int term = 1; term <= 2; term++) {
								scoreHist.setSchoolYear(year);
								scoreHist.setSchoolTerm(String.valueOf(term));
								// scoreHist.setEvgrType("1"); // 正常選課
								scoreHistList = sm.findScoreHistBy(scoreHist);
								if (!scoreHistList.isEmpty()) {
									Toolket.setCellValue(sheet,
											3 + (nextPage ? nextPageRowIndex
													: 0), k * 6, "" + year
													+ " 年  9  月  至  "
													+ (year + 1) + " 年  7  月");
									stavgs = sm.findStavgBy(new Stavg(
											studentNo, year, String
													.valueOf(term)));
									totalPassCredits = 0.0F;
									// for (ScoreHist hist : scoreHistList) {
									// 判斷i != scoreHistList.size() -
									// 1是避免剛好一學年印完,結果多印一個全空的學年
									for (int i = 0; i < scoreHistList.size(); i++) {

										hist = scoreHistList.get(i);
										if (hist.getEvgrType().equals("6"))
											continue; // 不印抵免的

										if (rowIndex == 32
												&& i != scoreHistList.size() - 1) {
											rowIndex = 6;
											k++;
											Toolket
													.setCellValue(
															sheet,
															2 + (nextPage ? nextPageRowIndex
																	: 0),
															k * 6,
															"第    "
																	+ formYear[k - 1]
																	+ "    學    年");
											Toolket
													.setCellValue(
															sheet,
															3 + (nextPage ? nextPageRowIndex
																	: 0),
															k * 6,
															""
																	+ year
																	+ " 年  9  月  至  "
																	+ (year + 1)
																	+ " 年  7  月");
											rowOver = true;
										}

										evgrType = hist.getEvgrType();
										isNotExemptAndMend = !"6"
												.equals(evgrType)
												&& !"5".equals(evgrType); // 判斷不是為抵免或待補?
										isExempt = "6".equals(evgrType); // 判斷是否為抵免?
										isMend = "5".equals(evgrType); // 判斷是否為待補?
										isAppend = "2".equals(evgrType); // 判斷是否為隨班?
										historyDepartCode = StringUtils
												.isBlank(hist
														.getStdepartClass()) ? ""
												: StringUtils.substring(hist
														.getStdepartClass(), 3,
														4);
										isNotSameDepartCode = !departCode
												.equals(historyDepartCode)
												&& !historyDepartCode
														.equals("0");
										opt = "";
										if ("3".equals(evgrType))
											opt = "暑";
										else
											opt = StringUtils.substring(
													Toolket.getCourseOpt(hist
															.getOpt()), 0, 1)
													+ (isAppend ? "隨"
															: (isMend ? "待"
																	: (!isNotExemptAndMend ? "抵"
																			: "")));

										cscode = hist.getCscode();
										if ("GA035".equals(cscode)
												|| "".equals("GB033"))
											continue; // 論文與技術報告2科目要排除

										if (!"99999".equals(cscode)) { // 排除操行
											courseName = cm
													.findCourseInfoByCscode(cscode) == null ? "查無科目名稱"
													: cm
															.findCourseInfoByCscode(
																	cscode)
															.getChiName()
															.trim();
											Float credit = hist.getCredit();
											Float score = hist.getScore();
											boolean pass = (score == null ? 0.0F
													: score) >= passScore;
											// ((正常選課&&及格)||是抵免)&&不能是待補
											if (((isNotExemptAndMend && pass) || isExempt)
													&& !isMend)
												// if (!isNotExemptAndMend ||
												// !isMend ||
												// bFlag1)
												// if (!isMend && bFlag1)
												if (isMaster
														&& "G"
																.equalsIgnoreCase(StringUtils
																		.substring(
																				hist
																						.getStdepartClass(),
																				2,
																				3))) {
													// 如果碩士班學生去修其他非碩士班科目,不于加總學分數
													totalPassCredits += credit;
												} else
													totalPassCredits += credit;

											if (1 == term) {
												if (opt.length() == 1
														|| is96Entrance)
													Toolket
															.setCellValue(
																	sheet,
																	rowIndex,
																	k * 6,
																	(is96Entrance ? StringUtils
																			.substring(
																					opt,
																					0,
																					1)
																			: opt));
												else
													Toolket
															.setCellValue(
																	workbook,
																	sheet,
																	rowIndex,
																	k * 6,
																	opt,
																	fontSize12,
																	HSSFCellStyle.ALIGN_CENTER,
																	true, null);
												Toolket.setCellValue(sheet,
														rowIndex, k * 6 + 1,
														courseName);
												position.put(courseName, String
														.valueOf(rowIndex));

												if (isNotExemptAndMend) {
													// 各科學分
													String creditStr = credit
															.toString();
													if (!isMend && !isExempt)
														creditStr = pass ? creditStr
																: creditStr
																		+ "*";
													if (isDoubleMajor
															&& isNotSameDepartCode)
														creditStr += "&";
													if (isAssist
															&& isNotSameDepartCode)
														creditStr += "#";
													if (StringUtils.indexOf(
															creditStr, "*") == StringUtils.INDEX_NOT_FOUND)
														Toolket.setCellValue(
																sheet,
																rowIndex,
																k * 6 + 2,
																creditStr);
													else
														Toolket
																.setCellValue(
																		workbook,
																		sheet,
																		rowIndex,
																		k * 6 + 2,
																		creditStr,
																		colorFont,
																		HSSFCellStyle.ALIGN_CENTER,
																		true,
																		null);
													// 各科成績
													Toolket
															.setCellValue(
																	sheet,
																	rowIndex++,
																	k * 6 + 3,
																	(isMend ? "待"
																			: (score == null ? ""
																					: StringUtils
																							.substringBefore(
																									score
																											.toString(),
																									"."))));
													// 歷年平均成績
													Toolket
															.setCellValue(
																	sheet,
																	32
																			+ (isMaster ? -2
																					: 0)
																			+ (nextPage ? nextPageRowIndex
																					: 0),
																	k * 6 + 3,
																	(stavgs
																			.isEmpty() ? ""
																			: df
																					.format(stavgs
																							.get(
																									0)
																							.getScore() + 0.001D)));
												} else {
													// 各科學分
													String creditStr = credit
															.toString();
													Toolket.setCellValue(sheet,
															rowIndex,
															k * 6 + 2,
															creditStr);
													if (is96Entrance)
														Toolket.setCellValue(
																sheet,
																rowIndex++,
																k * 6 + 3,
																(isMend ? "待"
																		: "抵"));
													else {
														// 歷年平均成績
														Toolket
																.setCellValue(
																		sheet,
																		32
																				+ (isMaster ? -2
																						: 0)
																				+ (nextPage ? nextPageRowIndex
																						: 0),
																		k * 6 + 3,
																		(stavgs
																				.isEmpty() ? ""
																				: df
																						.format(stavgs
																								.get(
																										0)
																								.getScore() + 0.001D)));
														Toolket
																.setCellValue(
																		sheet,
																		rowIndex++,
																		k * 6 + 3,
																		score == null ? ""
																				: StringUtils
																						.substringBefore(
																								score
																										.toString(),
																								"."));
													}
												}
											} else {

												String endString = StringUtils
														.substring(
																courseName,
																courseName
																		.length() - 1);
												boolean duplicatedCourseName = "二"
														.equals(endString);
												boolean duplicatedCourseName1 = "四"
														.equals(endString);
												boolean duplicatedCourseName2 = "下"
														.equals(endString);

												if (duplicatedCourseName
														|| duplicatedCourseName1
														|| duplicatedCourseName2) {

													String courseIndex = StringUtils
															.substring(
																	courseName,
																	0,
																	courseName
																			.length() - 1);
													String row = "", maybeSameCourseNameOptInfo = "";
													if (duplicatedCourseName) {
														row = position
																.get(courseIndex
																		+ "一");
														if (row != null)
															maybeSameCourseNameOptInfo = Toolket
																	.getCellValue(
																			sheet,
																			Integer
																					.parseInt(row),
																			(rowOver ? k - 1
																					: k) * 6);
													} else if (duplicatedCourseName1) {
														row = position
																.get(courseIndex
																		+ "三");
														if (row != null)
															maybeSameCourseNameOptInfo = Toolket
																	.getCellValue(
																			sheet,
																			Integer
																					.parseInt(row),
																			(rowOver ? k - 1
																					: k) * 6);
													} else if (duplicatedCourseName2) {
														row = position
																.get(courseIndex
																		+ "上");
														if (row != null)
															maybeSameCourseNameOptInfo = Toolket
																	.getCellValue(
																			sheet,
																			Integer
																					.parseInt(row),
																			(rowOver ? k - 1
																					: k) * 6);
													}

													// 必須相同選別才可合併
													boolean isAllowMerge = (StringUtils
															.contains(opt, "必") && StringUtils
															.contains(
																	maybeSameCourseNameOptInfo,
																	"必"))
															|| (StringUtils
																	.contains(
																			opt,
																			"選") && StringUtils
																	.contains(
																			maybeSameCourseNameOptInfo,
																			"選"));
													if (row != null
															&& isAllowMerge) {
														int rowNum = Integer
																.parseInt(row);
														Toolket
																.setCellValue(
																		sheet,
																		rowNum,
																		(rowOver ? k - 1
																				: k) * 6 + 1,
																		Toolket
																				.getCellValue(
																						sheet,
																						rowNum,
																						(rowOver ? k - 1
																								: k) * 6 + 1)
																				+ "、"
																				+ (duplicatedCourseName ? "二"
																						: duplicatedCourseName1 ? "四"
																								: "下"));
														if (isNotExemptAndMend) {
															// 各科學分
															String creditStr = credit
																	.toString();
															if (!isMend
																	&& !isExempt)
																creditStr = pass ? creditStr
																		: creditStr
																				+ "*";
															if (isDoubleMajor
																	&& isNotSameDepartCode)
																creditStr += "&";
															if (isAssist
																	&& isNotSameDepartCode)
																creditStr += "#";
															if (StringUtils
																	.indexOf(
																			creditStr,
																			"*") == StringUtils.INDEX_NOT_FOUND)
																Toolket
																		.setCellValue(
																				sheet,
																				rowNum,
																				(rowOver ? k - 1
																						: k) * 6 + 4,
																				creditStr);
															else
																Toolket
																		.setCellValue(
																				workbook,
																				sheet,
																				rowNum,
																				(rowOver ? k - 1
																						: k) * 6 + 4,
																				creditStr,
																				colorFont,
																				HSSFCellStyle.ALIGN_CENTER,
																				true,
																				null);

															Toolket
																	.setCellValue(
																			sheet,
																			rowNum,
																			(rowOver ? k - 1
																					: k) * 6 + 5,
																			(isMend ? "待"
																					: (score == null ? ""
																							: StringUtils
																									.substringBefore(
																											score
																													.toString(),
																											"."))));
															Toolket
																	.setCellValue(
																			sheet,
																			32
																					+ (isMaster ? -2
																							: 0)
																					+ (nextPage ? nextPageRowIndex
																							: 0),
																			(rowOver ? k - 1
																					: k) * 6 + 5,
																			(stavgs
																					.isEmpty() ? ""
																					: df
																							.format(stavgs
																									.get(
																											0)
																									.getScore() + 0.001D)));
														} else {
															// 各科學分
															String creditStr = credit
																	.toString();
															Toolket
																	.setCellValue(
																			sheet,
																			rowNum,
																			(rowOver ? k - 1
																					: k) * 6 + 4,
																			creditStr);
															if (is96Entrance)
																Toolket
																		.setCellValue(
																				sheet,
																				rowNum,
																				(rowOver ? k - 1
																						: k) * 6 + 5,
																				(isMend ? "待"
																						: "抵"));
															else {
																Toolket
																		.setCellValue(
																				sheet,
																				32 + (isMaster ? -2
																						: 0),
																				(rowOver ? k - 1
																						: k) * 6 + 5,
																				(stavgs
																						.isEmpty() ? ""
																						: df
																								.format(stavgs
																										.get(
																												0)
																										.getScore() + 0.001D)));
																Toolket
																		.setCellValue(
																				sheet,
																				rowNum,
																				(rowOver ? k - 1
																						: k) * 6 + 5,
																				score == null ? ""
																						: StringUtils
																								.substringBefore(
																										score
																												.toString(),
																										"."));
															}
														}
													} else {
														if (opt.length() == 1
																|| is96Entrance)
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex,
																			k * 6,
																			(is96Entrance ? StringUtils
																					.substring(
																							opt,
																							0,
																							1)
																					: opt));
														else
															Toolket
																	.setCellValue(
																			workbook,
																			sheet,
																			rowIndex,
																			k * 6,
																			opt,
																			fontSize12,
																			HSSFCellStyle.ALIGN_CENTER,
																			true,
																			null);
														Toolket.setCellValue(
																sheet,
																rowIndex,
																k * 6 + 1,
																courseName);
														position
																.put(
																		courseName,
																		String
																				.valueOf(rowIndex));

														if (isNotExemptAndMend) {
															// 各科學分
															String creditStr = credit
																	.toString();
															if (!isMend
																	&& !isExempt)
																creditStr = pass ? creditStr
																		: creditStr
																				+ "*";
															if (isDoubleMajor
																	&& isNotSameDepartCode)
																creditStr += "&";
															if (isAssist
																	&& isNotSameDepartCode)
																creditStr += "#";
															if (StringUtils
																	.indexOf(
																			creditStr,
																			"*") == StringUtils.INDEX_NOT_FOUND)
																Toolket
																		.setCellValue(
																				sheet,
																				rowIndex,
																				k * 6 + 4,
																				creditStr);
															else
																Toolket
																		.setCellValue(
																				workbook,
																				sheet,
																				rowIndex,
																				k * 6 + 4,
																				creditStr,
																				colorFont,
																				HSSFCellStyle.ALIGN_CENTER,
																				true,
																				null);

															// 各科成績
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex++,
																			k * 6 + 5,
																			(isMend ? "待"
																					: (score == null ? ""
																							: StringUtils
																									.substringBefore(
																											score
																													.toString(),
																											"."))));
															// 歷年平均成績
															Toolket
																	.setCellValue(
																			sheet,
																			32 + (isMaster ? -2
																					: 0),
																			k * 6 + 5,
																			(stavgs
																					.isEmpty() ? ""
																					: df
																							.format(stavgs
																									.get(
																											0)
																									.getScore() + 0.001D)));
														} else {
															// 各科學分
															String creditStr = credit
																	.toString();
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex,
																			k * 6 + 4,
																			creditStr);
															if (is96Entrance)
																Toolket
																		.setCellValue(
																				sheet,
																				rowIndex++,
																				k * 6 + 5,
																				(isMend ? "待"
																						: "抵"));
															else {
																// 歷年平均成績
																Toolket
																		.setCellValue(
																				sheet,
																				32 + (isMaster ? -2
																						: 0),
																				k * 6 + 5,
																				(stavgs
																						.isEmpty() ? ""
																						: df
																								.format(stavgs
																										.get(
																												0)
																										.getScore() + 0.001D)));
																Toolket
																		.setCellValue(
																				sheet,
																				rowIndex++,
																				k * 6 + 5,
																				score == null ? ""
																						: StringUtils
																								.substringBefore(
																										score
																												.toString(),
																										"."));
															}
														}
													}
												} else {
													if (opt.length() == 1
															|| is96Entrance)
														Toolket
																.setCellValue(
																		sheet,
																		rowIndex,
																		k * 6,
																		(is96Entrance ? StringUtils
																				.substring(
																						opt,
																						0,
																						1)
																				: opt));
													else
														Toolket
																.setCellValue(
																		workbook,
																		sheet,
																		rowIndex,
																		k * 6,
																		opt,
																		fontSize12,
																		HSSFCellStyle.ALIGN_CENTER,
																		true,
																		null);
													Toolket.setCellValue(sheet,
															rowIndex,
															k * 6 + 1,
															courseName);
													// 各科學分
													String creditStr = credit
															.toString();
													if (!isMend && !isExempt)
														creditStr = pass ? creditStr
																: creditStr
																		+ "*";
													if (isDoubleMajor
															&& isNotSameDepartCode)
														creditStr += "&";
													if (isAssist
															&& isNotSameDepartCode)
														creditStr += "#";
													if (StringUtils.indexOf(
															creditStr, "*") == StringUtils.INDEX_NOT_FOUND)
														Toolket.setCellValue(
																sheet,
																rowIndex,
																k * 6 + 4,
																creditStr);
													else
														Toolket
																.setCellValue(
																		workbook,
																		sheet,
																		rowIndex,
																		k * 6 + 4,
																		creditStr,
																		colorFont,
																		HSSFCellStyle.ALIGN_CENTER,
																		true,
																		null);

													if (isNotExemptAndMend) {
														Toolket
																.setCellValue(
																		sheet,
																		rowIndex++,
																		k * 6 + 5,
																		(isMend ? "待"
																				: (score == null ? ""
																						: StringUtils
																								.substringBefore(
																										score
																												.toString(),
																										"."))));
														Toolket
																.setCellValue(
																		sheet,
																		32 + (isMaster ? -2
																				: 0),
																		k * 6 + 5,
																		(stavgs
																				.isEmpty() ? ""
																				: df
																						.format(stavgs
																								.get(
																										0)
																								.getScore() + 0.001D)));
													} else {
														if (is96Entrance)
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex++,
																			k * 6 + 5,
																			(isMend ? "待"
																					: "抵"));
														else {
															Toolket
																	.setCellValue(
																			sheet,
																			32 + (isMaster ? -2
																					: 0),
																			k * 6 + 5,
																			(stavgs
																					.isEmpty() ? ""
																					: df
																							.format(stavgs
																									.get(
																											0)
																									.getScore() + 0.001D)));
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex++,
																			k * 6 + 5,
																			score == null ? ""
																					: StringUtils
																							.substringBefore(
																									score
																											.toString(),
																									"."));
														}
													}
												}

											}
										} else {
											// 操行
											if (1 == term) {
												if (isNotExemptAndMend) {
													Toolket
															.setCellValue(
																	sheet,
																	35
																			+ (isMaster ? -2
																					: 0)
																			+ (nextPage ? nextPageRowIndex
																					: 0),
																	k * 6 + 3,
																	String
																			.valueOf(Math
																					.round(hist
																							.getScore())));
												} else {
													if (!is96Entrance)
														Toolket
																.setCellValue(
																		sheet,
																		35 + (isMaster ? -2
																				: 0),
																		k * 6 + 3,
																		hist
																				.getScore() == null ? ""
																				: String
																						.valueOf(Math
																								.round((hist
																										.getScore()))));
												}
											} else {
												if (isNotExemptAndMend) {
													Toolket
															.setCellValue(
																	sheet,
																	35
																			+ (isMaster ? -2
																					: 0)
																			+ (nextPage ? nextPageRowIndex
																					: 0),
																	k * 6 + 5,
																	String
																			.valueOf(Math
																					.round(hist
																							.getScore())));
												} else {
													if (!is96Entrance)
														Toolket
																.setCellValue(
																		sheet,
																		35 + (isMaster ? -2
																				: 0),
																		k * 6 + 5,
																		hist
																				.getScore() == null ? ""
																				: String
																						.valueOf(Math
																								.round(hist
																										.getScore())));
												}
											}
										}

									}

									// 處理學分數與累計學分數
									if (1 == term) {
										Toolket.setCellValue(sheet, 33
												+ (isMaster ? -2 : 0)
												+ (nextPage ? nextPageRowIndex
														: 0), k * 6 + 2,
												totalPassCredits.toString());
										Toolket
												.setCellValue(
														sheet,
														34
																+ (isMaster ? -2
																		: 0)
																+ (nextPage ? nextPageRowIndex
																		: 0),
														k * 6 + 2,
														(passCreditsSum += totalPassCredits)
																.toString());
									} else {
										Toolket.setCellValue(sheet, 33
												+ (isMaster ? -2 : 0)
												+ (nextPage ? nextPageRowIndex
														: 0), k * 6 + 4,
												totalPassCredits.toString());
										Toolket
												.setCellValue(
														sheet,
														34
																+ (isMaster ? -2
																		: 0)
																+ (nextPage ? nextPageRowIndex
																		: 0),
														k * 6 + 4,
														(passCreditsSum += totalPassCredits)
																.toString());
									}
								}
								// k--;
							}

							gmark = new Gmark();
							gmark.setSchoolYear(year);
							gmark.setStudentNo(studentNo);
							gmarks = cm.findGMarkBy(gmark);
							if (!gmarks.isEmpty()) {
								StringBuilder builder = new StringBuilder();
								for (Gmark gm : gmarks)
									builder.append(gm.getRemark()).append(
											"\n      ");
								int remarkCols = 0;
								switch (k) {
									case 0:
										remarkCols = 0;
										break;
									case 1:
										remarkCols = 6;
										break;
									case 2:
										remarkCols = 12;
										break;
									case 3:
										remarkCols = 18;
										break;
								}
								Toolket.setCellValue(sheet, 36, remarkCols,
										"附註：" + builder.toString());
							}

							if (rowOver) {
								Toolket.setCellValue(sheet, 32,
										(k - 1) * 6 + 5, "");
								Toolket.setCellValue(sheet, 35, k * 6 + 5,
										Toolket.getCellValue(sheet, 35,
												(k - 1) * 6 + 5));
								Toolket.setCellValue(sheet, 35,
										(k - 1) * 6 + 5, "");
							}

							kk = k;
							rowIndexx = rowIndex;
							k++;
							j++;
							rowIndex = j >= 4 ? 49 : 6;
							// rowIndexx = j >= 4 ? 49 : 6;
							if (j == 4) {
								// rowIndex = 49;
								k = 0;
								nextPage = true;
							}
						}

					}

					if (isIncludeThisTermScore) {
						// Exception Students : 92148059, 92146011, 93148066,
						// 9144037, 914C009, 96164016, 9214D093
						AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
						Integer year = Integer.valueOf(am
								.findTermBy(PARAMETER_SCHOOL_YEAR));
						boolean isSameYear = lastYear == year;
						boolean isNeedCheck = true, isNeedCheck1 = true;
						if (!isSameYear) {
							isNeedCheck1 = false;
							kk++;
							if (nextPage) {
								if (kk == 4)
									kk = 0;
							}

							Toolket.setCellValue(sheet, (nextPage ? 45 : 2),
									kk * 6, "第    "
											+ formYear[(nextPage ? k + 4 : k)]
											+ "    學    年");
							Toolket.setCellValue(sheet, (nextPage ? 46 : 3),
									kk * 6, "" + year + " 年  9  月  至  "
											+ (year + 1) + " 年  7  月");
							rowIndexx = nextPage ? 49 : 6;
						}

						if (!nextPage && rowIndexx >= 32) {
							// 有學生會超過,例:96164016
							Toolket
									.setCellValue(
											sheet,
											(nextPage ? 45 : 2),
											(kk + 1) * 6,
											"第    "
													+ formYear[(nextPage ? kk + 4
															: kk)]
													+ "    學    年");
							Toolket.setCellValue(sheet, (nextPage ? 46 : 3),
									(kk + 1) * 6, "" + year + " 年  9  月  至  "
											+ (year + 1) + " 年  7  月");
							rowIndexx = nextPage ? 49 : 6;
							kk++;
						}

						isNeedCheck = true;
						// passCreditsSum = 0.0F;
						totalPassCredits = 0.0F;
						selds = cm.findSeldByStudentNoAndTerm(studentNo, sterm);
						// boolean isNewSchoolYear = "1".equals(sterm);
						if (!selds.isEmpty()) {
							for (Seld seld : selds) {
								dtime = cm.findDtimeBy(seld.getDtimeOid());
								csnos = cm.getCsnameBy(dtime.getCscode());
								if (!csnos.isEmpty())
									csno = (Csno) csnos.get(0);
								Toolket.setCellValue(sheet, rowIndexx, kk * 6,
										StringUtils.substring(Toolket
												.getCourseOpt(dtime.getOpt()),
												0, 1));
								Toolket.setCellValue(sheet, rowIndexx,
										kk * 6 + 1, csno == null ? "" : csno
												.getChiName());
								totalPassCredits += dtime.getCredit();
								if ("1".equals(sterm)) {
									Toolket.setCellValue(sheet, rowIndexx,
											kk * 6 + 2, dtime.getCredit()
													.toString());
									Toolket.setCellValue(sheet, rowIndexx++,
											kk * 6 + 3, String
													.valueOf(passScore));
								} else {
									Toolket.setCellValue(sheet, rowIndexx,
											kk * 6 + 4, dtime.getCredit()
													.toString());
									Toolket.setCellValue(sheet, rowIndexx++,
											kk * 6 + 5, String.valueOf(Math
													.round(passScore)));
								}

								if (isNeedCheck && isNeedCheck1
										&& rowIndexx >= 32) {
									isNeedCheck = false;
									kk++;
									if (kk % 4 == 0) {
										kk = 0;
										rowIndexx = nextPage ? 49 : 6;
									}

									// Toolket.setCellValue(sheet, 43, 0,
									// "中華科技大學"
									// + title + "學生歷年成績表");
									// Toolket.setCellValue(sheet, 44, 1, "學號："
									// + studentNo);
									// Toolket.setCellValue(sheet, 44, 7, "姓名："
									// + student.getStudentName());
									// Toolket
									// .setCellValue(
									// sheet,
									// 44,
									// 9,
									// "系所科別："
									// + (isMaster ? Toolket
									// .getMasterDepartName(departClass)
									// : Toolket
									// .getDepartName(departClass))
									// + (isDoubleMajor
									// || isAssist ? "("
									// + student
									// .getExtraStatus()
									// + ":"
									// + student
									// .getExtraDept()
									// + ")"
									// : ""));
									// Toolket.setCellValue(sheet, 44, 19,
									// "身份證字號："
									// + student.getIdno());
									Toolket
											.setCellValue(
													sheet,
													(nextPage ? 45 : 2),
													kk * 6,
													"第    "
															+ formYear[(nextPage ? kk + 4
																	: kk)]
															+ "    學    年");
									Toolket.setCellValue(sheet, (nextPage ? 46
											: 3), kk * 6, "" + year
											+ " 年  9  月  至  " + (year + 1)
											+ " 年  7  月");
									// rowIndexx = 6;
									// kk++;
								}
							}

							passCreditsSum += totalPassCredits;
							if ("1".equals(sterm)) {
								Toolket.setCellValue(sheet, (nextPage && j >= 4
										&& rowIndexx >= 49 ? 76 : 33)
										+ (isMaster ? -2 : 0), kk * 6 + 2,
										totalPassCredits.toString());
								Toolket.setCellValue(sheet, (nextPage && j >= 4
										&& rowIndexx >= 49 ? 77 : 34)
										+ (isMaster ? -2 : 0), kk * 6 + 2,
										passCreditsSum.toString());
							} else {
								Toolket.setCellValue(sheet, (nextPage && j >= 4
										&& rowIndexx >= 49 ? 76 : 33)
										+ (isMaster ? -2 : 0), kk * 6 + 4,
										totalPassCredits.toString());
								Toolket.setCellValue(sheet, (nextPage && j >= 4
										&& rowIndexx >= 49 ? 77 : 34)
										+ (isMaster ? -2 : 0), kk * 6 + 4,
										passCreditsSum.toString());
							}

							String[] excluded = { "15", "64", "54", "A4", "B4" };
							boolean hasInclude = ArrayUtils.contains(excluded,
									StringUtils.substring(departClass, 1, 3));
							if (!isDelay && !hasInclude && rowIndexx <= 42)
								Toolket.removeRow(sheet, 43, 45);
						}
					} else {
						String[] excluded = { "15", "64", "54", "A4", "B4" };
						boolean hasInclude = ArrayUtils.contains(excluded,
								StringUtils.substring(departClass, 1, 3));
						if (!isDelay && !hasInclude && rowIndexx <= 42)
							Toolket.removeRow(sheet, 43, 45);
					}
				}

				File tempDir = new File(context
						.getRealPath("/WEB-INF/reports/temp/"
								+ getUserCredential(session).getMember()
										.getIdno()
								+ (new SimpleDateFormat("yyyyMMdd")
										.format(new Date()))));
				if (!tempDir.exists())
					tempDir.mkdirs();

				File output = new File(tempDir, departClass + ".xls");
				FileOutputStream fos = new FileOutputStream(output);
				workbook.write(fos);
				fos.close();

				JasperReportUtils.printXlsToFrontEnd(response, output);
				output.delete();
				tempDir.delete();
				// return null;
			} else {
				ServletContext context = request.getSession()
						.getServletContext();
				Map<String, String> parameters = new HashMap<String, String>();
				File image = new File(context
						.getRealPath("/pages/reports/2002chitS.jpg"));
				parameters.put("IMAGE", image.getAbsolutePath());
				byte[] bytes = JasperRunManager.runReportToPdf(
						JasperReportUtils.getNoResultReport(context),
						parameters, new JREmptyDataSource());
				JasperReportUtils.printPdfToFrontEnd(response, bytes);
			}

		}

	}

	/**
	 * 只是為了幫註冊組可以列印96年資料而用
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	protected void printClassStudentsScoreHistory1(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		boolean isIncludeThisTermScore = "true".equalsIgnoreCase(request
				.getParameter("tt"));

		String studentNo = null;
		Clazz clazz = null;
		ScoreHist scoreHist = null, hist = null;
		List<Student> students = null;
		List<Graduate> graduates = null;
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);

		if (!clazzes.isEmpty() && clazzes.size() > 1) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("",
					""));
			saveMessages(request, messages);
			ServletContext context = request.getSession().getServletContext();
			Map<String, String> parameters = new HashMap<String, String>();
			File image = new File(context
					.getRealPath("/pages/reports/2002chitS.jpg"));
			parameters.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager.runReportToPdf(JasperReportUtils
					.getNoResultReport(context), parameters,
					new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		} else {

			clazz = clazzes.get(0);
			graduates = mm.findGraduatesByClassNo(clazz.getClassNo()); // 還要加96.2畢業的
			String no = StringUtils.substring(clazz.getClassNo(), 0, 4)
					+ (Integer.parseInt(StringUtils.substring(clazz
							.getClassNo(), 4)) + 10);
			students = mm.findStudentsByClassNo(no);
			for (Student student : students) {
				Graduate g = new Graduate();
				BeanUtils.copyProperties(g, student);
				graduates.add(g);
			}

			if (!graduates.isEmpty()) {

				ServletContext context = request.getSession()
						.getServletContext();
				String[] excep = { "11G332" }; // 春季班學年要+1
				String[] formYear = { "一", "二", "三", "四", "五", "六", "七", "八" };

				short firstYear = 0;
				short lastYear = 0;

				double justScore = 0.0D;

				int sheetIndex = 0;
				int k = 0, j = 0, rowIndex = 0, nextPageRowIndex = 0;
				int kk = 0, rowIndexx = 0;

				String departClass = clazz.getClassNo();
				String departCode = "", title = "", evgrType = "", historyDepartCode = "", opt = "", cscode = "";

				// 碩士班70分及格
				float passScore = Toolket
						.getPassScoreByDepartClass(departClass);

				MasterData md = null;
				Just just = null;
				ScoreHist target = null;
				Dtime dtime = null;
				List csnos = null;
				Csno csno = null;
				Gmark gmark = null;

				Map<String, String> position = null;

				Float passCreditsSum = 0.0F;
				Float totalPassCredits = 0.0F;

				List<Seld> selds = null;
				List<ScoreHist> scoreHistList = null;
				List<Stavg> stavgs = null;
				List<Gmark> gmarks = null;

				boolean isOver = false; // (lastYear - firstYear + 1) > 4;
				boolean isDelay = Toolket.isDelayClass(departClass);
				boolean isSpringClass = false;
				// 雙主修
				boolean isDoubleMajor = false;
				boolean isAssist = false;
				// 9608前入學成績單要Show完整平均成績及操行,但之後入學的只Show'抵'字,且平均成績及操行不Show
				boolean is96Entrance = false;
				boolean isMaster = Toolket.isMasterClass(departClass);
				boolean nextPage = false, hasPassRecord = false, rowOver = false;
				boolean isNotExemptAndMend = false; // 判斷不是為抵免或待補?
				boolean isExempt = false; // 判斷是否為抵免?
				boolean isMend = false; // 判斷是否為待補?
				boolean isAppend = false; // 判斷是否為隨班?
				boolean isNotSameDepartCode = false;

				int studentCounts = graduates.size();
				String fileName = "";

				DecimalFormat df = new DecimalFormat(",##0.0");

				File templateXLS = null;

				if (!isMaster) {

					/*if (studentCounts <= 30)
						fileName = "ClassStudentsScoreHistoryOver30.xls";
					else if (studentCounts > 31 && studentCounts <= 40)
						fileName = "ClassStudentsScoreHistoryOver40.xls";
					else if (studentCounts > 40 && studentCounts <= 45)
						fileName = "ClassStudentsScoreHistoryOver45.xls";
					else if (studentCounts > 45 && studentCounts <= 50)
						fileName = "ClassStudentsScoreHistoryOver50.xls";
					else if (studentCounts > 51 && studentCounts <= 55)
						fileName = "ClassStudentsScoreHistoryOver55.xls";
					else*/
						fileName = "ClassStudentsScoreHistoryOver60.xls";

					title = Toolket.getSchoolFormalName(departClass);
					if (isDelay) {
						// Only 20 Sheets
						templateXLS = new File(
								context
										.getRealPath("/WEB-INF/reports/ClassStudentsScoreHistoryOver.xls"));
					} else {
						templateXLS = new File(context
								.getRealPath("/WEB-INF/reports/" + fileName));
					}
				} else {

					if (studentCounts <= 15)
						fileName = "ClassStudentsScoreHistoryMaster15.xls";
					else if (studentCounts > 15 && studentCounts <= 20)
						fileName = "ClassStudentsScoreHistoryMaster20.xls";
					else
						fileName = "ClassStudentsScoreHistoryMaster25.xls";

					String masterId = StringUtils.substring(departClass, 1, 3);
					if ("1G".equals(masterId))
						title = "日間部碩士班";
					else
						title = "進修部碩士在職專班";
					templateXLS = new File(context
							.getRealPath("/WEB-INF/reports/" + fileName));
				}

				HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

				HSSFFont colorFont = workbook.createFont();
				colorFont.setColor(HSSFColor.RED.index);
				colorFont.setFontHeightInPoints((short) 12);
				colorFont.setFontName("Arial Unicode MS");

				HSSFFont fontSize12 = workbook.createFont();
				fontSize12.setFontHeightInPoints((short) 12);
				fontSize12.setFontName("Arial Unicode MS");
				HSSFSheet sheet = null;

				for (Graduate graduate : graduates) {

					studentNo = graduate.getStudentNo();
					sheet = workbook.getSheetAt(sheetIndex);
					workbook
							.setSheetName(sheetIndex++, studentNo.toUpperCase());
					// if (student == null) {
					// graduate = mm.findGraduateByStudentNo(studentNo);
					// student = new Student();
					// BeanUtils.copyProperties(student, graduate);
					// }

					// 雙主修
					isDoubleMajor = "雙修".equals(graduate.getExtraStatus());
					isAssist = "輔修".equals(graduate.getExtraStatus());

					// departClass = student.getDepartClass();
					// 9608前入學成績單要Show完整平均成績及操行,但之後入學的只Show'抵'字,且平均成績及操行不Show
					is96Entrance = graduate.getEntrance() == null ? false
							: graduate.getEntrance() >= (short) 9608;
					scoreHist = new ScoreHist(studentNo);
					scoreHistList = sm.findScoreHistBy(scoreHist);
					if (scoreHistList.isEmpty()) {
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Course.messageN1",
										"查無歷年成績資料"));
						saveErrors(request, messages);
						log.error("No Score History... " + studentNo);
						sheetIndex--;
						continue;
					} else {

						firstYear = Short.parseShort(scoreHistList.get(0)
								.getSchoolYear().toString());
						lastYear = Short.parseShort(scoreHistList.get(
								scoreHistList.size() - 1).getSchoolYear()
								.toString());
						isOver = (lastYear - firstYear + 1) > 4;
						if (isOver)
							; // log.error("It is Over... " + studentNo);
						departCode = "";

						Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + title
								+ "學生歷年成績表");
						Toolket.setCellValue(sheet, 1, 1, "學號：" + studentNo);
						Toolket.setCellValue(sheet, 1, 7, "姓名："
								+ graduate.getStudentName());
						Toolket.setCellValue(sheet, 1, 9, "系所科別："
								+ (isMaster ? Toolket
										.getMasterDepartName(departClass)
										: Toolket.getDepartName(departClass))
								+ (isDoubleMajor || isAssist ? "("
										+ graduate.getExtraStatus() + ":"
										+ graduate.getExtraDept() + ")" : ""));
						Toolket.setCellValue(sheet, 1, 19, "身分證字號："
								+ graduate.getIdno());

						// 印2次,下面的code會刪除
						Toolket.setCellValue(sheet, 43, 0, "中華科技大學" + title
								+ "學生歷年成績表");
						Toolket.setCellValue(sheet, 44, 1, "學號：" + studentNo);
						Toolket.setCellValue(sheet, 44, 7, "姓名："
								+ graduate.getStudentName());
						Toolket.setCellValue(sheet, 44, 9, "系所科別："
								+ (isMaster ? Toolket
										.getMasterDepartName(departClass)
										: Toolket.getDepartName(departClass))
								+ (isDoubleMajor || isAssist ? "("
										+ graduate.getExtraStatus() + ":"
										+ graduate.getExtraDept() + ")" : ""));
						Toolket.setCellValue(sheet, 44, 19, "身分證字號："
								+ graduate.getIdno());

						if (isDelay) {
							Toolket.setCellValue(sheet, 43, 0, "中華科技大學" + title
									+ "學生歷年成績表");
							Toolket.setCellValue(sheet, 44, 1, "學號："
									+ studentNo);
							Toolket.setCellValue(sheet, 44, 7, "姓名："
									+ graduate.getStudentName());
							Toolket
									.setCellValue(
											sheet,
											44,
											9,
											"系所科別："
													+ (isMaster ? Toolket
															.getMasterDepartName(departClass)
															: Toolket
																	.getDepartName(departClass))
													+ (isDoubleMajor
															|| isAssist ? "("
															+ graduate
																	.getExtraStatus()
															+ ":"
															+ graduate
																	.getExtraDept()
															+ ")"
															: ""));
							Toolket.setCellValue(sheet, 44, 19, "身分證字號："
									+ graduate.getIdno());
						}

						if (isDoubleMajor || isAssist) {
							Toolket.setCellValue(sheet, 42, 0,
									" 符號說明 => *:不及格   #:輔系   &:雙主修   ");
							departCode = StringUtils.substring(graduate
									.getDepartClass(), 3, 4);
						}

						if (isOver) {
							Toolket.setCellValue(sheet, 43, 0, "中華科技大學" + title
									+ "學生歷年成績表");
							Toolket.setCellValue(sheet, 44, 1, "學號："
									+ studentNo);
							Toolket.setCellValue(sheet, 44, 7, "姓名："
									+ graduate.getStudentName());
							Toolket
									.setCellValue(
											sheet,
											44,
											9,
											"系所科別："
													+ (isMaster ? Toolket
															.getMasterDepartName(departClass)
															: Toolket
																	.getDepartName(departClass)));
							Toolket.setCellValue(sheet, 44, 19, "身分證字號："
									+ graduate.getIdno());
							if (isDoubleMajor || isAssist)
								Toolket.setCellValue(sheet, 85, 0,
										" 符號說明 => *:不及格   #:輔系   &:雙主修   ");
						}

						if (isMaster) {
							md = sm.findMasterByStudentNo(studentNo);
							if (md != null) {
								Toolket.setCellValue(sheet, 38, 0, "論文題目："
										+ md.getThesesChiname());
								Toolket.setCellValue(sheet, 39, 0, "          "
										+ md.getThesesEngname());
								Toolket.setCellValue(sheet, 38, 17, md
										.getRemark());
								Toolket.setCellValue(sheet, 40, 0, "學位考試成績："
										+ md.getThesesScore());
								Toolket.setCellValue(sheet, 40, 4,
										"學位考試成績(50%)：" + md.getThesesScore()
												/ 2);
								Toolket.setCellValue(sheet, 40, 8, "學業平均成績："
										+ md.getEvgr1Score());
								Toolket
										.setCellValue(sheet, 40, 14,
												"學業平均成績(50%)："
														+ md.getEvgr1Score()
														/ 2);
								Toolket.setCellValue(sheet, 40, 19, "總成績："
										+ md.getGraduateScore());
							}
						}

						if (Arrays.binarySearch(excep, departClass) >= 0) {
							lastYear++;
							isSpringClass = true;
							Toolket.setCellValue(sheet, 39, 17, "日期："
									+ (lastYear + 1) + ".1");
						}

						k = 0;
						j = 0;
						rowIndex = 6;
						nextPageRowIndex = 43;
						passCreditsSum = 0.0F;
						nextPage = false;
						hasPassRecord = false;
						rowOver = false;
						// List<Stavg> stavgs = null;
						// Just just = null;
						// String[] formYear = { "一", "二", "三", "四", "五", "六",
						// "七", "八" };
						for (short year = firstYear; year <= lastYear; year++) {
							// 判斷是否有歷年資料?可以9316D078為例測試
							// 不做判斷會出現歷年中間有空白資料
							scoreHist.setSchoolYear(year);
							scoreHist.setSchoolTerm("1");
							hasPassRecord = !sm.findScoreHistBy(scoreHist)
									.isEmpty();
							if (!hasPassRecord) {
								scoreHist.setSchoolTerm("2");
								hasPassRecord = !sm.findScoreHistBy(scoreHist)
										.isEmpty();
								if (!hasPassRecord)
									continue;
							}

							if (isSpringClass && year == lastYear) {
								just = sam.findJustByStudentNo(studentNo);
								justScore = 0.0D;
								if (just == null
										|| just.getTotalScore() == 0.0D) {
									target = new ScoreHist(studentNo);
									target.setSchoolYear(new Short(year));
									target.setSchoolTerm("2");
									target.setCscode("99999");
									scoreHistList = sm.findScoreHistBy(target);
									if (!scoreHistList.isEmpty()) {
										scoreHist = sm.findScoreHistBy(
												scoreHist).get(0);
										justScore = scoreHist.getScore();
									}
								} else
									justScore = just.getTotalScore();

								Toolket
										.setCellValue(
												sheet,
												35 + (isMaster ? -2 : 0),
												k * 6 + 3,
												justScore == 0.0D ? ""
														: String
																.valueOf(Math
																		.round(justScore)));
							}

							if (rowOver) {
								Toolket.setCellValue(workbook, sheet,
										2 + (nextPage ? nextPageRowIndex : 0),
										k * 6, "第    "
												+ formYear[(nextPage ? k + 4
														: k)] + "    學    年",
										fontSize12, HSSFCellStyle.ALIGN_CENTER,
										true, null);
							} else
								Toolket.setCellValue(sheet,
										2 + (nextPage ? nextPageRowIndex : 0),
										k * 6, "第    "
												+ formYear[(nextPage ? k + 4
														: k)] + "    學    年");
							position = new HashMap<String, String>();
							for (int term = 1; term <= 2; term++) {
								scoreHist.setSchoolYear(year);
								scoreHist.setSchoolTerm(String.valueOf(term));
								// scoreHist.setEvgrType("1"); // 正常選課
								scoreHistList = sm.findScoreHistBy(scoreHist);
								if (!scoreHistList.isEmpty()) {
									Toolket.setCellValue(sheet,
											3 + (nextPage ? nextPageRowIndex
													: 0), k * 6, "" + year
													+ " 年  9  月  至  "
													+ (year + 1) + " 年  7  月");
									stavgs = sm.findStavgBy(new Stavg(
											studentNo, year, String
													.valueOf(term)));
									totalPassCredits = 0.0F;
									// for (ScoreHist hist : scoreHistList) {
									// 判斷i != scoreHistList.size() -
									// 1是避免剛好一學年印完,結果多印一個全空的學年
									for (int i = 0; i < scoreHistList.size(); i++) {

										hist = scoreHistList.get(i);
										if (rowIndex == 32
												&& i != scoreHistList.size() - 1) {
											rowIndex = 6;
											k++;
											Toolket
													.setCellValue(
															sheet,
															2 + (nextPage ? nextPageRowIndex
																	: 0),
															k * 6,
															"第    "
																	+ formYear[k - 1]
																	+ "    學    年");
											Toolket
													.setCellValue(
															sheet,
															3 + (nextPage ? nextPageRowIndex
																	: 0),
															k * 6,
															""
																	+ year
																	+ " 年  9  月  至  "
																	+ (year + 1)
																	+ " 年  7  月");
											rowOver = true;
										}

										evgrType = hist.getEvgrType();
										isNotExemptAndMend = !"6"
												.equals(evgrType)
												&& !"5".equals(evgrType); // 判斷不是為抵免或待補?
										isExempt = "6".equals(evgrType); // 判斷是否為抵免?
										isMend = "5".equals(evgrType); // 判斷是否為待補?
										isAppend = "2".equals(evgrType); // 判斷是否為隨班?
										historyDepartCode = StringUtils
												.isBlank(hist
														.getStdepartClass()) ? ""
												: StringUtils.substring(hist
														.getStdepartClass(), 3,
														4);
										isNotSameDepartCode = !departCode
												.equals(historyDepartCode)
												&& !historyDepartCode
														.equals("0");
										opt = "";
										if ("3".equals(evgrType))
											opt = "暑";
										else
											opt = StringUtils.substring(
													Toolket.getCourseOpt(hist
															.getOpt()), 0, 1)
													+ (isAppend ? "隨"
															: (isMend ? "待"
																	: (!isNotExemptAndMend ? "抵"
																			: "")));

										cscode = hist.getCscode();
										if ("GA035".equals(cscode)
												|| "".equals("GB033"))
											continue; // 論文與技術報告2科目要排除

										if (!"99999".equals(cscode)) { // 排除操行
											String courseName = cm
													.findCourseInfoByCscode(cscode) == null ? "查無科目名稱"
													: cm
															.findCourseInfoByCscode(
																	cscode)
															.getChiName()
															.trim();
											Float credit = hist.getCredit();
											Float score = hist.getScore();
											boolean pass = (score == null ? 0.0F
													: score) >= passScore;
											// ((正常選課&&及格)||是抵免)&&不能是待補
											if (((isNotExemptAndMend && pass) || isExempt)
													&& !isMend)
												// if (!isNotExemptAndMend ||
												// !isMend ||
												// bFlag1)
												// if (!isMend && bFlag1)
												if (isMaster
														&& "G"
																.equalsIgnoreCase(StringUtils
																		.substring(
																				hist
																						.getStdepartClass(),
																				2,
																				3))) {
													// 如果碩士班學生去修其他非碩士班科目,不于加總學分數
													totalPassCredits += credit;
												} else
													totalPassCredits += credit;

											if (1 == term) {
												if (opt.length() == 1
														|| is96Entrance)
													Toolket
															.setCellValue(
																	sheet,
																	rowIndex,
																	k * 6,
																	(is96Entrance ? StringUtils
																			.substring(
																					opt,
																					0,
																					1)
																			: opt));
												else
													Toolket
															.setCellValue(
																	workbook,
																	sheet,
																	rowIndex,
																	k * 6,
																	opt,
																	fontSize12,
																	HSSFCellStyle.ALIGN_CENTER,
																	true, null);
												Toolket.setCellValue(sheet,
														rowIndex, k * 6 + 1,
														courseName);
												position.put(courseName, String
														.valueOf(rowIndex));

												if (isNotExemptAndMend) {
													// 各科學分
													String creditStr = credit
															.toString();
													if (!isMend && !isExempt)
														creditStr = pass ? creditStr
																: creditStr
																		+ "*";
													if (isDoubleMajor
															&& isNotSameDepartCode)
														creditStr += "&";
													if (isAssist
															&& isNotSameDepartCode)
														creditStr += "#";
													if (StringUtils.indexOf(
															creditStr, "*") == StringUtils.INDEX_NOT_FOUND)
														Toolket.setCellValue(
																sheet,
																rowIndex,
																k * 6 + 2,
																creditStr);
													else
														Toolket
																.setCellValue(
																		workbook,
																		sheet,
																		rowIndex,
																		k * 6 + 2,
																		creditStr,
																		colorFont,
																		HSSFCellStyle.ALIGN_CENTER,
																		true,
																		null);
													// 各科成績
													Toolket
															.setCellValue(
																	sheet,
																	rowIndex++,
																	k * 6 + 3,
																	(isMend ? "待"
																			: (score == null ? ""
																					: StringUtils
																							.substringBefore(
																									score
																											.toString(),
																									"."))));
													// 歷年平均成績
													Toolket
															.setCellValue(
																	sheet,
																	32
																			+ (isMaster ? -2
																					: 0)
																			+ (nextPage ? nextPageRowIndex
																					: 0),
																	k * 6 + 3,
																	(stavgs
																			.isEmpty() ? ""
																			: df
																					.format(stavgs
																							.get(
																									0)
																							.getScore() + 0.001D)));
												} else {
													// 各科學分
													String creditStr = credit
															.toString();
													Toolket.setCellValue(sheet,
															rowIndex,
															k * 6 + 2,
															creditStr);
													if (is96Entrance)
														Toolket.setCellValue(
																sheet,
																rowIndex++,
																k * 6 + 3,
																(isMend ? "待"
																		: "抵"));
													else {
														// 歷年平均成績
														Toolket
																.setCellValue(
																		sheet,
																		32
																				+ (isMaster ? -2
																						: 0)
																				+ (nextPage ? nextPageRowIndex
																						: 0),
																		k * 6 + 3,
																		(stavgs
																				.isEmpty() ? ""
																				: df
																						.format(stavgs
																								.get(
																										0)
																								.getScore() + 0.001D)));
														Toolket
																.setCellValue(
																		sheet,
																		rowIndex++,
																		k * 6 + 3,
																		score == null ? ""
																				: StringUtils
																						.substringBefore(
																								score
																										.toString(),
																								"."));
													}
												}
											} else {

												String endString = StringUtils
														.substring(
																courseName,
																courseName
																		.length() - 1);
												boolean duplicatedCourseName = "二"
														.equals(endString);
												boolean duplicatedCourseName1 = "四"
														.equals(endString);
												boolean duplicatedCourseName2 = "下"
														.equals(endString);

												if (duplicatedCourseName
														|| duplicatedCourseName1
														|| duplicatedCourseName2) {

													String courseIndex = StringUtils
															.substring(
																	courseName,
																	0,
																	courseName
																			.length() - 1);
													String row = "", maybeSameCourseNameOptInfo = "";
													if (duplicatedCourseName) {
														row = position
																.get(courseIndex
																		+ "一");
														if (row != null)
															maybeSameCourseNameOptInfo = Toolket
																	.getCellValue(
																			sheet,
																			Integer
																					.parseInt(row),
																			(rowOver ? k - 1
																					: k) * 6);
													} else if (duplicatedCourseName1) {
														row = position
																.get(courseIndex
																		+ "三");
														if (row != null)
															maybeSameCourseNameOptInfo = Toolket
																	.getCellValue(
																			sheet,
																			Integer
																					.parseInt(row),
																			(rowOver ? k - 1
																					: k) * 6);
													} else if (duplicatedCourseName2) {
														row = position
																.get(courseIndex
																		+ "上");
														if (row != null)
															maybeSameCourseNameOptInfo = Toolket
																	.getCellValue(
																			sheet,
																			Integer
																					.parseInt(row),
																			(rowOver ? k - 1
																					: k) * 6);
													}

													// 必須相同選別才可合併
													boolean isAllowMerge = (StringUtils
															.contains(opt, "必") && StringUtils
															.contains(
																	maybeSameCourseNameOptInfo,
																	"必"))
															|| (StringUtils
																	.contains(
																			opt,
																			"選") && StringUtils
																	.contains(
																			maybeSameCourseNameOptInfo,
																			"選"));
													if (row != null
															&& isAllowMerge) {
														int rowNum = Integer
																.parseInt(row);
														Toolket
																.setCellValue(
																		sheet,
																		rowNum,
																		(rowOver ? k - 1
																				: k) * 6 + 1,
																		Toolket
																				.getCellValue(
																						sheet,
																						rowNum,
																						(rowOver ? k - 1
																								: k) * 6 + 1)
																				+ "、"
																				+ (duplicatedCourseName ? "二"
																						: duplicatedCourseName1 ? "四"
																								: "下"));
														if (isNotExemptAndMend) {
															// 各科學分
															String creditStr = credit
																	.toString();
															if (!isMend
																	&& !isExempt)
																creditStr = pass ? creditStr
																		: creditStr
																				+ "*";
															if (isDoubleMajor
																	&& isNotSameDepartCode)
																creditStr += "&";
															if (isAssist
																	&& isNotSameDepartCode)
																creditStr += "#";
															if (StringUtils
																	.indexOf(
																			creditStr,
																			"*") == StringUtils.INDEX_NOT_FOUND)
																Toolket
																		.setCellValue(
																				sheet,
																				rowNum,
																				(rowOver ? k - 1
																						: k) * 6 + 4,
																				creditStr);
															else
																Toolket
																		.setCellValue(
																				workbook,
																				sheet,
																				rowNum,
																				(rowOver ? k - 1
																						: k) * 6 + 4,
																				creditStr,
																				colorFont,
																				HSSFCellStyle.ALIGN_CENTER,
																				true,
																				null);

															Toolket
																	.setCellValue(
																			sheet,
																			rowNum,
																			(rowOver ? k - 1
																					: k) * 6 + 5,
																			(isMend ? "待"
																					: (score == null ? ""
																							: StringUtils
																									.substringBefore(
																											score
																													.toString(),
																											"."))));
															Toolket
																	.setCellValue(
																			sheet,
																			32
																					+ (isMaster ? -2
																							: 0)
																					+ (nextPage ? nextPageRowIndex
																							: 0),
																			(rowOver ? k - 1
																					: k) * 6 + 5,
																			(stavgs
																					.isEmpty() ? ""
																					: df
																							.format(stavgs
																									.get(
																											0)
																									.getScore() + 0.001D)));
														} else {
															// 各科學分
															String creditStr = credit
																	.toString();
															Toolket
																	.setCellValue(
																			sheet,
																			rowNum,
																			(rowOver ? k - 1
																					: k) * 6 + 4,
																			creditStr);
															if (is96Entrance)
																Toolket
																		.setCellValue(
																				sheet,
																				rowNum,
																				(rowOver ? k - 1
																						: k) * 6 + 5,
																				(isMend ? "待"
																						: "抵"));
															else {
																Toolket
																		.setCellValue(
																				sheet,
																				32 + (isMaster ? -2
																						: 0),
																				(rowOver ? k - 1
																						: k) * 6 + 5,
																				(stavgs
																						.isEmpty() ? ""
																						: df
																								.format(stavgs
																										.get(
																												0)
																										.getScore() + 0.001D)));
																Toolket
																		.setCellValue(
																				sheet,
																				rowNum,
																				(rowOver ? k - 1
																						: k) * 6 + 5,
																				score == null ? ""
																						: StringUtils
																								.substringBefore(
																										score
																												.toString(),
																										"."));
															}
														}
													} else {
														if (opt.length() == 1
																|| is96Entrance)
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex,
																			k * 6,
																			(is96Entrance ? StringUtils
																					.substring(
																							opt,
																							0,
																							1)
																					: opt));
														else
															Toolket
																	.setCellValue(
																			workbook,
																			sheet,
																			rowIndex,
																			k * 6,
																			opt,
																			fontSize12,
																			HSSFCellStyle.ALIGN_CENTER,
																			true,
																			null);
														Toolket.setCellValue(
																sheet,
																rowIndex,
																k * 6 + 1,
																courseName);
														position
																.put(
																		courseName,
																		String
																				.valueOf(rowIndex));

														if (isNotExemptAndMend) {
															// 各科學分
															String creditStr = credit
																	.toString();
															if (!isMend
																	&& !isExempt)
																creditStr = pass ? creditStr
																		: creditStr
																				+ "*";
															if (isDoubleMajor
																	&& isNotSameDepartCode)
																creditStr += "&";
															if (isAssist
																	&& isNotSameDepartCode)
																creditStr += "#";
															if (StringUtils
																	.indexOf(
																			creditStr,
																			"*") == StringUtils.INDEX_NOT_FOUND)
																Toolket
																		.setCellValue(
																				sheet,
																				rowIndex,
																				k * 6 + 4,
																				creditStr);
															else
																Toolket
																		.setCellValue(
																				workbook,
																				sheet,
																				rowIndex,
																				k * 6 + 4,
																				creditStr,
																				colorFont,
																				HSSFCellStyle.ALIGN_CENTER,
																				true,
																				null);

															// 各科成績
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex++,
																			k * 6 + 5,
																			(isMend ? "待"
																					: (score == null ? ""
																							: StringUtils
																									.substringBefore(
																											score
																													.toString(),
																											"."))));
															// 歷年平均成績
															Toolket
																	.setCellValue(
																			sheet,
																			32 + (isMaster ? -2
																					: 0),
																			k * 6 + 5,
																			(stavgs
																					.isEmpty() ? ""
																					: df
																							.format(stavgs
																									.get(
																											0)
																									.getScore() + 0.001D)));
														} else {
															// 各科學分
															String creditStr = credit
																	.toString();
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex,
																			k * 6 + 4,
																			creditStr);
															if (is96Entrance)
																Toolket
																		.setCellValue(
																				sheet,
																				rowIndex++,
																				k * 6 + 5,
																				(isMend ? "待"
																						: "抵"));
															else {
																// 歷年平均成績
																Toolket
																		.setCellValue(
																				sheet,
																				32 + (isMaster ? -2
																						: 0),
																				k * 6 + 5,
																				(stavgs
																						.isEmpty() ? ""
																						: df
																								.format(stavgs
																										.get(
																												0)
																										.getScore() + 0.001D)));
																Toolket
																		.setCellValue(
																				sheet,
																				rowIndex++,
																				k * 6 + 5,
																				score == null ? ""
																						: StringUtils
																								.substringBefore(
																										score
																												.toString(),
																										"."));
															}
														}
													}
												} else {
													if (opt.length() == 1
															|| is96Entrance)
														Toolket
																.setCellValue(
																		sheet,
																		rowIndex,
																		k * 6,
																		(is96Entrance ? StringUtils
																				.substring(
																						opt,
																						0,
																						1)
																				: opt));
													else
														Toolket
																.setCellValue(
																		workbook,
																		sheet,
																		rowIndex,
																		k * 6,
																		opt,
																		fontSize12,
																		HSSFCellStyle.ALIGN_CENTER,
																		true,
																		null);
													Toolket.setCellValue(sheet,
															rowIndex,
															k * 6 + 1,
															courseName);
													// 各科學分
													String creditStr = credit
															.toString();
													if (!isMend && !isExempt)
														creditStr = pass ? creditStr
																: creditStr
																		+ "*";
													if (isDoubleMajor
															&& isNotSameDepartCode)
														creditStr += "&";
													if (isAssist
															&& isNotSameDepartCode)
														creditStr += "#";
													if (StringUtils.indexOf(
															creditStr, "*") == StringUtils.INDEX_NOT_FOUND)
														Toolket.setCellValue(
																sheet,
																rowIndex,
																k * 6 + 4,
																creditStr);
													else
														Toolket
																.setCellValue(
																		workbook,
																		sheet,
																		rowIndex,
																		k * 6 + 4,
																		creditStr,
																		colorFont,
																		HSSFCellStyle.ALIGN_CENTER,
																		true,
																		null);

													if (isNotExemptAndMend) {
														Toolket
																.setCellValue(
																		sheet,
																		rowIndex++,
																		k * 6 + 5,
																		(isMend ? "待"
																				: (score == null ? ""
																						: StringUtils
																								.substringBefore(
																										score
																												.toString(),
																										"."))));
														Toolket
																.setCellValue(
																		sheet,
																		32 + (isMaster ? -2
																				: 0),
																		k * 6 + 5,
																		(stavgs
																				.isEmpty() ? ""
																				: df
																						.format(stavgs
																								.get(
																										0)
																								.getScore() + 0.001D)));
													} else {
														if (is96Entrance)
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex++,
																			k * 6 + 5,
																			(isMend ? "待"
																					: "抵"));
														else {
															Toolket
																	.setCellValue(
																			sheet,
																			32 + (isMaster ? -2
																					: 0),
																			k * 6 + 5,
																			(stavgs
																					.isEmpty() ? ""
																					: df
																							.format(stavgs
																									.get(
																											0)
																									.getScore() + 0.001D)));
															Toolket
																	.setCellValue(
																			sheet,
																			rowIndex++,
																			k * 6 + 5,
																			score == null ? ""
																					: StringUtils
																							.substringBefore(
																									score
																											.toString(),
																									"."));
														}
													}
												}

											}
										} else {
											// 操行
											if (1 == term) {
												if (isNotExemptAndMend) {
													Toolket
															.setCellValue(
																	sheet,
																	35
																			+ (isMaster ? -2
																					: 0)
																			+ (nextPage ? nextPageRowIndex
																					: 0),
																	k * 6 + 3,
																	String
																			.valueOf(Math
																					.round(hist
																							.getScore())));
												} else {
													if (!is96Entrance)
														Toolket
																.setCellValue(
																		sheet,
																		35 + (isMaster ? -2
																				: 0),
																		k * 6 + 3,
																		hist
																				.getScore() == null ? ""
																				: String
																						.valueOf(Math
																								.round((hist
																										.getScore()))));
												}
											} else {
												if (isNotExemptAndMend) {
													Toolket
															.setCellValue(
																	sheet,
																	35
																			+ (isMaster ? -2
																					: 0)
																			+ (nextPage ? nextPageRowIndex
																					: 0),
																	k * 6 + 5,
																	String
																			.valueOf(Math
																					.round(hist
																							.getScore())));
												} else {
													if (!is96Entrance)
														Toolket
																.setCellValue(
																		sheet,
																		35 + (isMaster ? -2
																				: 0),
																		k * 6 + 5,
																		hist
																				.getScore() == null ? ""
																				: String
																						.valueOf(Math
																								.round(hist
																										.getScore())));
												}
											}
										}

									}

									// 處理學分數與累計學分數
									if (1 == term) {
										Toolket.setCellValue(sheet, 33
												+ (isMaster ? -2 : 0)
												+ (nextPage ? nextPageRowIndex
														: 0), k * 6 + 2,
												totalPassCredits.toString());
										Toolket
												.setCellValue(
														sheet,
														34
																+ (isMaster ? -2
																		: 0)
																+ (nextPage ? nextPageRowIndex
																		: 0),
														k * 6 + 2,
														(passCreditsSum += totalPassCredits)
																.toString());
									} else {
										Toolket.setCellValue(sheet, 33
												+ (isMaster ? -2 : 0)
												+ (nextPage ? nextPageRowIndex
														: 0), k * 6 + 4,
												totalPassCredits.toString());
										Toolket
												.setCellValue(
														sheet,
														34
																+ (isMaster ? -2
																		: 0)
																+ (nextPage ? nextPageRowIndex
																		: 0),
														k * 6 + 4,
														(passCreditsSum += totalPassCredits)
																.toString());
									}
								}
								// k--;
							}

							gmark = new Gmark();
							gmark.setSchoolYear(year);
							gmark.setStudentNo(studentNo);
							gmarks = cm.findGMarkBy(gmark);
							if (!gmarks.isEmpty()) {
								StringBuilder builder = new StringBuilder();
								for (Gmark gm : gmarks)
									builder.append(gm.getRemark()).append(
											"\n      ");
								int remarkCols = 0;
								switch (k) {
									case 0:
										remarkCols = 0;
										break;
									case 1:
										remarkCols = 6;
										break;
									case 2:
										remarkCols = 12;
										break;
									case 3:
										remarkCols = 18;
										break;
								}
								Toolket.setCellValue(sheet, 36, remarkCols,
										"附註：" + builder.toString());
							}

							if (rowOver) {
								Toolket.setCellValue(sheet, 32,
										(k - 1) * 6 + 5, "");
								Toolket.setCellValue(sheet, 35, k * 6 + 5,
										Toolket.getCellValue(sheet, 35,
												(k - 1) * 6 + 5));
								Toolket.setCellValue(sheet, 35,
										(k - 1) * 6 + 5, "");
							}

							kk = k;
							rowIndexx = rowIndex;
							k++;
							j++;
							rowIndex = j >= 4 ? 49 : 6;
							// rowIndexx = j >= 4 ? 49 : 6;
							if (j == 4) {
								// rowIndex = 49;
								k = 0;
								nextPage = true;
							}
						}

					}

					if (isIncludeThisTermScore) {
						// Exception Students : 92148059, 92146011, 93148066,
						// 9144037, 914C009, 96164016, 9214D093
						AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
						Integer year = Integer.valueOf(am
								.findTermBy(PARAMETER_SCHOOL_YEAR));
						boolean isSameYear = lastYear == year;
						boolean isNeedCheck = true, isNeedCheck1 = true;
						if (!isSameYear) {
							isNeedCheck1 = false;
							if (nextPage) {
								kk++;
								if (kk == 4)
									kk = 0;
							}

							Toolket.setCellValue(sheet, (nextPage ? 45 : 2),
									kk * 6, "第    "
											+ formYear[(nextPage ? k + 4 : k)]
											+ "    學    年");
							Toolket.setCellValue(sheet, (nextPage ? 46 : 3),
									kk * 6, "" + year + " 年  9  月  至  "
											+ (year + 1) + " 年  7  月");
							rowIndexx = nextPage ? 49 : 6;
							// kk++;
						}

						if (!nextPage && rowIndexx >= 32) {
							// 有學生會超過,例:96164016
							Toolket
									.setCellValue(
											sheet,
											(nextPage ? 45 : 2),
											(kk + 1) * 6,
											"第    "
													+ formYear[(nextPage ? kk + 4
															: kk)]
													+ "    學    年");
							Toolket.setCellValue(sheet, (nextPage ? 46 : 3),
									(kk + 1) * 6, "" + year + " 年  9  月  至  "
											+ (year + 1) + " 年  7  月");
							rowIndexx = nextPage ? 49 : 6;
							kk++;
						}

						isNeedCheck = true;
						// passCreditsSum = 0.0F;
						totalPassCredits = 0.0F;
						selds = cm.findSeldByStudentNoAndTerm(studentNo, sterm);
						// boolean isNewSchoolYear = "1".equals(sterm);
						if (!selds.isEmpty()) {
							for (Seld seld : selds) {
								dtime = cm.findDtimeBy(seld.getDtimeOid());
								csnos = cm.getCsnameBy(dtime.getCscode());
								if (!csnos.isEmpty())
									csno = (Csno) csnos.get(0);
								Toolket.setCellValue(sheet, rowIndexx, kk * 6,
										StringUtils.substring(Toolket
												.getCourseOpt(dtime.getOpt()),
												0, 1));
								Toolket.setCellValue(sheet, rowIndexx,
										kk * 6 + 1, csno == null ? "" : csno
												.getChiName());
								totalPassCredits += dtime.getCredit();
								if ("1".equals(sterm)) {
									Toolket.setCellValue(sheet, rowIndexx,
											kk * 6 + 2, dtime.getCredit()
													.toString());
									Toolket.setCellValue(sheet, rowIndexx++,
											kk * 6 + 3, String
													.valueOf(passScore));
								} else {
									Toolket.setCellValue(sheet, rowIndexx,
											kk * 6 + 4, dtime.getCredit()
													.toString());
									Toolket.setCellValue(sheet, rowIndexx++,
											kk * 6 + 5, String.valueOf(Math
													.round(passScore)));
								}

								if (isNeedCheck && isNeedCheck1
										&& rowIndexx >= 32) {
									isNeedCheck = false;
									kk++;
									if (kk % 4 == 0) {
										kk = 0;
										rowIndexx = nextPage ? 49 : 6;
									}

									// Toolket.setCellValue(sheet, 43, 0,
									// "中華科技大學"
									// + title + "學生歷年成績表");
									// Toolket.setCellValue(sheet, 44, 1, "學號："
									// + studentNo);
									// Toolket.setCellValue(sheet, 44, 7, "姓名："
									// + student.getStudentName());
									// Toolket
									// .setCellValue(
									// sheet,
									// 44,
									// 9,
									// "系所科別："
									// + (isMaster ? Toolket
									// .getMasterDepartName(departClass)
									// : Toolket
									// .getDepartName(departClass))
									// + (isDoubleMajor
									// || isAssist ? "("
									// + student
									// .getExtraStatus()
									// + ":"
									// + student
									// .getExtraDept()
									// + ")"
									// : ""));
									// Toolket.setCellValue(sheet, 44, 19,
									// "身份證字號："
									// + student.getIdno());
									Toolket
											.setCellValue(
													sheet,
													(nextPage ? 45 : 2),
													kk * 6,
													"第    "
															+ formYear[(nextPage ? kk + 4
																	: kk)]
															+ "    學    年");
									Toolket.setCellValue(sheet, (nextPage ? 46
											: 3), kk * 6, "" + year
											+ " 年  9  月  至  " + (year + 1)
											+ " 年  7  月");
									// rowIndexx = 6;
									// kk++;
								}
							}

							passCreditsSum += totalPassCredits;
							if ("1".equals(sterm)) {
								Toolket.setCellValue(sheet, (nextPage && j >= 4
										&& rowIndexx >= 49 ? 76 : 33)
										+ (isMaster ? -2 : 0), kk * 6 + 2,
										totalPassCredits.toString());
								Toolket.setCellValue(sheet, (nextPage && j >= 4
										&& rowIndexx >= 49 ? 77 : 34)
										+ (isMaster ? -2 : 0), kk * 6 + 2,
										passCreditsSum.toString());
							} else {
								Toolket.setCellValue(sheet, (nextPage && j >= 4
										&& rowIndexx >= 49 ? 76 : 33)
										+ (isMaster ? -2 : 0), kk * 6 + 4,
										totalPassCredits.toString());
								Toolket.setCellValue(sheet, (nextPage && j >= 4
										&& rowIndexx >= 49 ? 77 : 34)
										+ (isMaster ? -2 : 0), kk * 6 + 4,
										passCreditsSum.toString());
							}

							String[] excluded = { "15", "64", "54", "A4", "B4" };
							boolean hasInclude = ArrayUtils.contains(excluded,
									StringUtils.substring(departClass, 1, 3));
							if (!isDelay && !hasInclude && rowIndexx <= 42)
								Toolket.removeRow(sheet, 43, 45);
						}
					} else {
						String[] excluded = { "15", "64", "54", "A4", "B4" };
						boolean hasInclude = ArrayUtils.contains(excluded,
								StringUtils.substring(departClass, 1, 3));
						if (!isDelay && !hasInclude && rowIndexx <= 42)
							Toolket.removeRow(sheet, 43, 45);
					}
				}

				File tempDir = new File(context
						.getRealPath("/WEB-INF/reports/temp/"
								+ getUserCredential(session).getMember()
										.getIdno()
								+ (new SimpleDateFormat("yyyyMMdd")
										.format(new Date()))));
				if (!tempDir.exists())
					tempDir.mkdirs();

				File output = new File(tempDir, departClass + ".xls");
				FileOutputStream fos = new FileOutputStream(output);
				workbook.write(fos);
				fos.close();

				JasperReportUtils.printXlsToFrontEnd(response, output);
				output.delete();
				tempDir.delete();
				// return null;
			} else {
				ServletContext context = request.getSession()
						.getServletContext();
				Map<String, String> parameters = new HashMap<String, String>();
				File image = new File(context
						.getRealPath("/pages/reports/2002chitS.jpg"));
				parameters.put("IMAGE", image.getAbsolutePath());
				byte[] bytes = JasperRunManager.runReportToPdf(
						JasperReportUtils.getNoResultReport(context),
						parameters, new JREmptyDataSource());
				JasperReportUtils.printPdfToFrontEnd(response, bytes);
			}

		}
	}

	/**
	 * 列印新生學籍卡
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printClassStudentsRegistrationCard(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = cm.getNowBy("School_year"); // 可以選入學學年嗎???
		// String term = form.getString("sterm"); // 不用參考
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);
		if (!clazzes.isEmpty()) {
			List<Student> students = null;
			HSSFSheet sheet = null;
			String departClass = null;
			RegistrationCard rc = null;
			StdImage image = null;
			String studentNo = null;
			int sheetIndex = 0, pictureIndex = 0;
			byte[] bytes = null;
			// CreationHelper helper = null;
			Drawing drawing = null;
			// ClientAnchor anchor = null;
			HSSFClientAnchor anchor1 = null;
			Picture pict = null;
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

			File templateXLS = new File(
					context
							.getRealPath("/WEB-INF/reports/ClassStudentsRegistrationCard.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFFont fontSize18 = workbook.createFont();
			fontSize18.setFontHeightInPoints((short) 18);
			fontSize18.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFFont fontSize11 = workbook.createFont();
			fontSize11.setFontHeightInPoints((short) 11);
			fontSize11.setFontName("Arial Unicode MS");

			HSSFFont fontSize8 = workbook.createFont();
			fontSize8.setFontHeightInPoints((short) 8);
			fontSize8.setFontName("Arial Unicode MS");

			for (Clazz clazz : clazzes) {
				if (!Toolket.isNewStudentClass(clazz.getClassNo()))
					continue;

				departClass = clazz.getClassNo();
				students = mm.findStudentsByClassNo(departClass);
				if (!students.isEmpty()) {

					sheetIndex = 0;
					for (Student student : students) {
						studentNo = student.getStudentNo();
						sheet = workbook.getSheetAt(sheetIndex);
						workbook.setSheetName(sheetIndex++, studentNo
								.toUpperCase());
						// Header
						Toolket.setCellValue(workbook, sheet, 1, 0, Toolket
								.getCellValue(sheet, 1, 0).replaceAll("YEAR",
										year), fontSize18,
								HSSFCellStyle.ALIGN_CENTER, false, null, null);
						Toolket.setCellValue(workbook, sheet, 1, 13, Toolket
								.getCellValue(sheet, 1, 13).replaceAll("YEAR",
										year), fontSize18,
								HSSFCellStyle.ALIGN_CENTER, false, null, null);

						// Columns
						Toolket.setCellValue(workbook, sheet, 2, 1, student
								.getStudentNo(), fontSize12,
								HSSFCellStyle.ALIGN_LEFT, true, null, null);
						Toolket.setCellValue(workbook, sheet, 2, 4, student
								.getStudentName(), fontSize12,
								HSSFCellStyle.ALIGN_LEFT, true, null, null);
						Toolket.setCellValue(workbook, sheet, 2, 8, Toolket
								.getClassFullName(student.getDepartClass()),
								fontSize12, HSSFCellStyle.ALIGN_LEFT, true,
								null, null);
						Toolket.setCellValue(workbook, sheet, 3, 1, StringUtils
								.isBlank(student.getStudentEname()) ? ""
								: student.getStudentEname(), fontSize12,
								HSSFCellStyle.ALIGN_LEFT, true, false, null,
								null);
						Toolket.setCellValue(workbook, sheet, 3, 9, student
								.getIdno(), fontSize12,
								HSSFCellStyle.ALIGN_LEFT, true, null, null);
						Toolket.setCellValue(workbook, sheet, 4, 1, Toolket
								.getSex(student.getSex()), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, false, null,
								null);
						// Toolket.setCellValue(workbook, sheet, 4, 3, df
						// .format(student.getBirthday()), fontSize11,
						// HSSFCellStyle.ALIGN_LEFT, true, null, null);
						Toolket.setCellValue(workbook, sheet, 4, 3,
								printNativeDate(df
										.format(student.getBirthday())),
								fontSize11, HSSFCellStyle.ALIGN_LEFT, true,
								null, null);
						Toolket.setCellValue(workbook, sheet, 4, 9, StringUtils
								.isBlank(student.getIdent()) ? "" : Toolket
								.getIdentity(student.getIdent()), fontSize12,
								HSSFCellStyle.ALIGN_LEFT, true, false, null,
								null);
						Toolket
								.setCellValue(
										workbook,
										sheet,
										9,
										1,
										(StringUtils.isBlank(student
												.getPermPost()) ? "" : student
												.getPermPost())
												+ "  "
												+ (StringUtils.isBlank(student
														.getPermAddr()) ? ""
														: student.getPermAddr()),
										fontSize12, HSSFCellStyle.ALIGN_LEFT,
										true, null, null);
						Toolket.setCellValue(workbook, sheet, 9, 9, student
								.getTelephone(), fontSize12,
								HSSFCellStyle.ALIGN_LEFT, true, false, null,
								null);
						Toolket.setCellValue(workbook, sheet, 10, 9, student
								.getCellPhone(), fontSize12,
								HSSFCellStyle.ALIGN_LEFT, true, false, null,
								null);
						Toolket.setCellValue(workbook, sheet, 11, 2, student
								.getParentName(), fontSize12,
								HSSFCellStyle.ALIGN_LEFT, true, false, null,
								null);
						Toolket.setCellValue(workbook, sheet, 12, 2, student
								.getCurrPost()
								+ " " + student.getCurrAddr(), fontSize8,
								HSSFCellStyle.ALIGN_LEFT, true, null, null);

						image = new StdImage();
						image.setStudentNo(student.getStudentNo());
						image = mm.findStdImageBy(image);
						if (image != null) {
							bytes = image.getImage().getBytes(1l,
									(int) image.getImage().length());
							// Image im =
							// java.awt.Toolkit.getDefaultToolkit().getImage(filename);
							try {
								pictureIndex = workbook.addPicture(bytes,
										Workbook.PICTURE_TYPE_JPEG);
							} catch (Exception e) {
								try {
									pictureIndex = workbook.addPicture(bytes,
											Workbook.PICTURE_TYPE_PNG);
								} catch (Exception e1) {
									pictureIndex = workbook.addPicture(bytes,
											Workbook.PICTURE_TYPE_PICT);
								}
							}

							// helper = workbook.getCreationHelper();
							drawing = sheet.createDrawingPatriarch();
							// anchor = helper.createClientAnchor();
							anchor1 = new HSSFClientAnchor(0, 0, 400, 100,
									(short) 13, 19, (short) 17, 29);
							anchor1.setAnchorType(0);
							// anchor.setCol1(13);
							// anchor.setRow1(19);
							pict = drawing.createPicture(anchor1, pictureIndex);
							try {
								pict.resize(); // 154611怪怪的
								// pict.resize(0.5);
							} catch (Exception e) {
								e.printStackTrace();
								log.error(e.getMessage(), e);
							}
						}

						if (student.getRegistrationCard() != null) {
							rc = student.getRegistrationCard();
							Toolket.setCellValue(workbook, sheet, 2, 8, Toolket
									.getCellValue(sheet, 2, 8)
									+ " "
									+ (rc.getDiviName() == null ? "" : rc
											.getDiviName().trim()), fontSize12,
									HSSFCellStyle.ALIGN_LEFT, true, false,
									null, null);

							Toolket
									.setCellValue(workbook, sheet, 3, 5,
											StringUtils.isBlank(rc
													.getBirthCountry()) ? ""
													: rc.getBirthCountry()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_LEFT, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 4, 5,
											StringUtils.isBlank(rc
													.getBirthPlace()) ? "" : rc
													.getBirthPlace().trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_LEFT, true,
											false, null, null);
							Toolket.setCellValue(workbook, sheet, 5, 2,
									StringUtils.isBlank(rc.getAborigine()) ? ""
											: rc.getAborigine().trim(),
									fontSize12, HSSFCellStyle.ALIGN_LEFT, true,
									false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 5, 5,
											StringUtils.isBlank(rc
													.getForeignPlace()) ? ""
													: rc.getForeignPlace()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_LEFT, true,
											false, null, null);
							Toolket.setCellValue(workbook, sheet, 5, 9, rc
									.getArmyIn() == null ? "入伍日期：" : "入伍日期："
									+ df.format(rc.getArmyIn()), fontSize12,
									HSSFCellStyle.ALIGN_LEFT, true, false,
									null, null);
							Toolket.setCellValue(workbook, sheet, 6, 9, rc
									.getArmyOut() == null ? "退伍日期：" : "退伍日期："
									+ df.format(rc.getArmyOut()), fontSize12,
									HSSFCellStyle.ALIGN_LEFT, true, false,
									null, null);
							Toolket
									.setCellValue(workbook, sheet, 7, 3,
											StringUtils.isBlank(rc
													.getBeforeSchool()) ? ""
													: rc.getBeforeSchool()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_LEFT, true,
											false, null, null);
							Toolket.setCellValue(workbook, sheet, 7, 9,
									StringUtils.isBlank(rc.getGradeYear()) ? ""
											: rc.getGradeYear(), fontSize12,
									HSSFCellStyle.ALIGN_LEFT, true, false,
									null, null);
							Toolket.setCellValue(workbook, sheet, 7, 11, rc
									.getGradeType() == null ? "" : rc
									.getGradeType().toString(), fontSize12,
									HSSFCellStyle.ALIGN_LEFT, true, false,
									null, null);
							Toolket
									.setCellValue(workbook, sheet, 8, 3,
											StringUtils.isBlank(rc
													.getBeforeDept()) ? "" : rc
													.getBeforeDept().trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_LEFT, true,
											false, null, null);
							Toolket.setCellValue(workbook, sheet, 11, 5,
									StringUtils.isBlank(rc.getParentAge()) ? ""
											: rc.getParentAge().trim(),
									fontSize12, HSSFCellStyle.ALIGN_CENTER,
									true, false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 11, 7,
											StringUtils.isBlank(rc
													.getParentCareer()) ? ""
													: rc.getParentCareer()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_LEFT, true,
											false, null, null);
							Toolket.setCellValue(workbook, sheet, 11, 11,
									StringUtils.isBlank(rc
											.getParentRelationship()) ? "" : rc
											.getParentRelationship().trim(),
									fontSize12, HSSFCellStyle.ALIGN_LEFT, true,
									false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 12, 10,
											StringUtils.isBlank(rc
													.getEmergentPhone()) ? ""
													: rc.getEmergentPhone()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_LEFT, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 13, 10,
											StringUtils.isBlank(rc
													.getEmergentCell()) ? ""
													: rc.getEmergentCell()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_LEFT, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 15, 1,
											StringUtils.isBlank(rc
													.getMemberTitle1()) ? ""
													: rc.getMemberTitle1()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 15, 2,
											StringUtils.isBlank(rc
													.getMemberName1()) ? ""
													: rc.getMemberName1()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 15, 4,
											StringUtils.isBlank(rc
													.getMemberAge1()) ? "" : rc
													.getMemberAge1().trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 15, 5,
											StringUtils.isBlank(rc
													.getMemberCareer1()) ? ""
													: rc.getMemberCareer1()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);

							Toolket
									.setCellValue(workbook, sheet, 15, 7,
											StringUtils.isBlank(rc
													.getMemberTitle2()) ? ""
													: rc.getMemberTitle2()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 15, 8,
											StringUtils.isBlank(rc
													.getMemberName2()) ? ""
													: rc.getMemberName2()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 15, 10,
											StringUtils.isBlank(rc
													.getMemberAge2()) ? "" : rc
													.getMemberAge2().trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 15, 11,
											StringUtils.isBlank(rc
													.getMemberCareer2()) ? ""
													: rc.getMemberCareer2()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);

							Toolket
									.setCellValue(workbook, sheet, 16, 1,
											StringUtils.isBlank(rc
													.getMemberTitle3()) ? ""
													: rc.getMemberTitle3()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 16, 2,
											StringUtils.isBlank(rc
													.getMemberName3()) ? ""
													: rc.getMemberName3()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 16, 4,
											StringUtils.isBlank(rc
													.getMemberAge3()) ? "" : rc
													.getMemberAge3().trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 16, 5,
											StringUtils.isBlank(rc
													.getMemberCareer3()) ? ""
													: rc.getMemberCareer3()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);

							Toolket
									.setCellValue(workbook, sheet, 16, 7,
											StringUtils.isBlank(rc
													.getMemberTitle4()) ? ""
													: rc.getMemberTitle4()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 16, 8,
											StringUtils.isBlank(rc
													.getMemberName4()) ? ""
													: rc.getMemberName4()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 16, 10,
											StringUtils.isBlank(rc
													.getMemberAge4()) ? "" : rc
													.getMemberAge4().trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
							Toolket
									.setCellValue(workbook, sheet, 16, 11,
											StringUtils.isBlank(rc
													.getMemberCareer4()) ? ""
													: rc.getMemberCareer4()
															.trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											false, null, null);
						}

					}

				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "ClassStudentRegistrationCard.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(context
					.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager
					.runReportToPdf(JasperReportUtils
							.getNoResultReport(context), param,
							new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}
	}

	/**
	 * 學生身分證字號檢核
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printIdnoCheckErrorStudentsList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);
		if (!clazzes.isEmpty()) {

			List<Student> students = null;
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("身分證字號驗證結果學生清單");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 3500);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "身分證字號驗證結果學生清單",
					fontSize16, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "學號", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "姓名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "班級", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "身分證字號", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;

			for (Clazz clazz : clazzes) {
				if (Toolket.isDelayClass(clazz.getClassNo()))
					continue;

				students = mm.findStudentsByClassNo(clazz.getClassNo());
				if (!students.isEmpty()) {
					for (Student student : students) {
						if (!Toolket.checkIdno(student.getIdno())) {
							Toolket.setCellValue(workbook, sheet, index, 0,
									student.getStudentNo(), fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 1,
									student.getStudentName(), fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 2,
									Toolket.getClassFullName(student
											.getDepartClass()), null,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index++, 3,
									student.getIdno(), null,
									HSSFCellStyle.ALIGN_CENTER, true, null);
						}
					}

				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "RegisterList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(context
					.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager
					.runReportToPdf(JasperReportUtils
							.getNoResultReport(context), param,
							new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}
	}

	/**
	 * 列印專任教師留校時間
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param sterm
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void printStayTimePrint(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Integer year = Integer.valueOf(cm.getNowBy("School_year"));
		String term = form.getString("sterm");
		// Short dayCode = Short.valueOf(form.getString("dayCode"));
		// String nodeCode = form.getString("nodeCode");
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), true);

		if (!clazzes.isEmpty()) {

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/TeachSchedAll.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFSheet sheet = null;
			int sheetIndex = 0, colOffset = 1, col = 0;
			boolean isLocationNull = false;
			String departClass = null;
			Dtime dtime = null;
			Empl empl = null;
			Set<String> idnoSet = new HashSet<String>();
			Short colorForStayTime = HSSFColor.AUTOMATIC.index;
			Short colorForLifeCounseling = HSSFColor.LIGHT_GREEN.index;
			List<TeacherStayTime> tsts = null;
			List<LifeCounseling> lcs = null;
			List<Dtime> dtimes = null;
			List<Map> map = null;
			Map content = null;

			for (Clazz clazz : clazzes) {
				departClass = clazz.getClassNo();

				dtime = new Dtime();
				dtime.setDepartClass(departClass);
				dtime.setSterm(sterm);
				dtimes = cm.findDtimeBy(dtime, "cscode");
				if (!dtimes.isEmpty()) {
					for (Dtime d : dtimes) {
						if (StringUtils.isNotBlank(d.getTechid()))
							idnoSet.add(d.getTechid());
					}
				}
			}

			for (String idno : idnoSet) {
				empl = mm.findEmplByIdno(idno);
				if (empl != null && "1".equalsIgnoreCase(empl.getCategory())) {

					sheet = workbook.getSheetAt(sheetIndex);
					workbook.setSheetName(sheetIndex++, empl.getCname());
					isLocationNull = empl.getLocation() == null;

					Toolket.setCellValue(sheet, 0, 1, year
							+ "學年度"
							+ term
							+ "學期"
							+ empl.getCname()
							+ "老師授課時間表"
							+ " (分機:"
							+ (isLocationNull ? "" : StringUtils
									.defaultIfEmpty(empl.getLocation()
											.getExtension(), ""))
							+ " 辦公室位置:"
							+ (isLocationNull ? "" : StringUtils
									.defaultIfEmpty(empl.getLocation()
											.getRoomId(), "")) + ")");
					map = cm.findCourseByTeacherTermWeekdaySched(
							empl.getIdno(), term.toString());

					for (int i = 0; i < 14; i++) {
						for (int j = 0; j < 7; j++) {
							content = map.get(j * 15 + i);
							if (!CollectionUtils.isEmpty(content)) {
								Toolket
										.setCellValue(
												sheet,
												i + 2,
												j + 2,
												(String) content
														.get("ClassName")
														+ "\n"
														+ (String) content
																.get("chi_name")
														+ "\n"
														+ (String) content
																.get("place"));
							}
						}
					}

					tsts = empl.getStayTime();
					for (TeacherStayTime tst : tsts) {
						col = tst.getWeek() + colOffset;
						if (tst.getNode1() != null && tst.getNode1() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									2, col)))
								Toolket.setCellValue(workbook, sheet, 2, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode2() != null && tst.getNode2() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									3, col)))
								Toolket.setCellValue(workbook, sheet, 3, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode3() != null && tst.getNode3() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									4, col)))
								Toolket.setCellValue(workbook, sheet, 4, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode4() != null && tst.getNode4() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									5, col)))
								Toolket.setCellValue(workbook, sheet, 5, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode5() != null && tst.getNode5() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									6, col)))
								Toolket.setCellValue(workbook, sheet, 6, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode6() != null && tst.getNode6() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									7, col)))
								Toolket.setCellValue(workbook, sheet, 7, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode7() != null && tst.getNode7() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									8, col)))
								Toolket.setCellValue(workbook, sheet, 8, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode8() != null && tst.getNode8() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									9, col)))
								Toolket.setCellValue(workbook, sheet, 9, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode9() != null && tst.getNode9() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									10, col)))
								Toolket.setCellValue(workbook, sheet, 10, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode10() != null && tst.getNode10() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									11, col)))
								Toolket.setCellValue(workbook, sheet, 11, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode11() != null && tst.getNode11() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									12, col)))
								Toolket.setCellValue(workbook, sheet, 12, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode12() != null && tst.getNode12() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									13, col)))
								Toolket.setCellValue(workbook, sheet, 13, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode13() != null && tst.getNode13() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									14, col)))
								Toolket.setCellValue(workbook, sheet, 14, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (tst.getNode14() != null && tst.getNode14() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									15, col)))
								Toolket.setCellValue(workbook, sheet, 15, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
					}

					lcs = empl.getLifeCounseling();
					colOffset = 1;
					col = 0;
					for (LifeCounseling lc : lcs) {
						col = lc.getWeek() + colOffset;
						if (lc.getNode1() != null && lc.getNode1() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									2, col)))
								Toolket.setCellValue(workbook, sheet, 2, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode2() != null && lc.getNode2() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									3, col)))
								Toolket.setCellValue(workbook, sheet, 3, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode3() != null && lc.getNode3() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									4, col)))
								Toolket.setCellValue(workbook, sheet, 4, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode4() != null && lc.getNode4() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									5, col)))
								Toolket.setCellValue(workbook, sheet, 5, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode5() != null && lc.getNode5() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									6, col)))
								Toolket.setCellValue(workbook, sheet, 6, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode6() != null && lc.getNode6() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									7, col)))
								Toolket.setCellValue(workbook, sheet, 7, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode7() != null && lc.getNode7() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									8, col)))
								Toolket.setCellValue(workbook, sheet, 8, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode8() != null && lc.getNode8() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									9, col)))
								Toolket.setCellValue(workbook, sheet, 9, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode9() != null && lc.getNode9() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									10, col)))
								Toolket.setCellValue(workbook, sheet, 10, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode10() != null && lc.getNode10() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									11, col)))
								Toolket.setCellValue(workbook, sheet, 11, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode11() != null && lc.getNode11() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									12, col)))
								Toolket.setCellValue(workbook, sheet, 12, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode12() != null && lc.getNode12() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									13, col)))
								Toolket.setCellValue(workbook, sheet, 13, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode13() != null && lc.getNode13() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									14, col)))
								Toolket.setCellValue(workbook, sheet, 14, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						if (lc.getNode14() != null && lc.getNode14() == 1) {
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									15, col)))
								Toolket.setCellValue(workbook, sheet, 15, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
					}

				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "StayTimeList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();

		}
	}

	/**
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param sterm
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void printCalculate(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String thisYear = cm.getNowBy("School_year");
		String thisTerm = am.findTermBy(PARAMETER_SCHOOL_TERM);
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);

		int thisTermCounts = 0, lastTermCounts = 0;
		String departClass = null, deptCode = null, histDeptCode = null, currentDeptCode = null, chiName = null;
		ScoreHist scoreHist = null;
		List<Student> students = null;
		List<ScoreHist> scoreHistList = null;
		List<Map> seldInfo = null;
		List csnos = null;

		if (!clazzes.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("跨系選課人數");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 5000);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("Arial Unicode MS");

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("Arial Unicode MS");

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "跨系選課人數", fontSize16,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "學號", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "姓名", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "班級", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "選修班級", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "選修科目", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;

			for (Clazz clazz : clazzes) {
				departClass = clazz.getClassNo();
				deptCode = clazz.getDeptNo();
				if (Toolket.isDelayClass(departClass)
						|| Toolket.isLiteracyClass(departClass))
					continue;

				students = mm.findStudentsByClassNo(departClass);
				if (!students.isEmpty()) {

					if (thisYear.equals(request.getParameter("year"))
							&& thisTerm.equals(sterm)) {
						// 查詢本學期(所以用Seld判斷)
						for (Student student : students) {
							seldInfo = cm.findStudentSeldCourse(student
									.getStudentNo(), sterm);
							if (!seldInfo.isEmpty()) {
								for (Map m : seldInfo) {

									if (IConstants.CSCODE_BEHAVIOR
											.equals((String) m.get("cscode")))
										continue;

									currentDeptCode = StringUtils.substring(
											(String) m.get("depart_class"), 3,
											4);
									if (!deptCode
											.equalsIgnoreCase(currentDeptCode)
											&& !Toolket
													.isLiteracyClass((String) m
															.get("depart_class"))) {
										thisTermCounts++;

										Toolket.setCellValue(workbook, sheet,
												index, 0, student
														.getStudentNo(),
												fontSize10,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
										Toolket.setCellValue(workbook, sheet,
												index, 1, student
														.getStudentName(),
												fontSize10,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
										Toolket
												.setCellValue(
														workbook,
														sheet,
														index,
														2,
														Toolket
																.getClassFullName(student
																		.getDepartClass()),
														fontSize10,
														HSSFCellStyle.ALIGN_CENTER,
														true, null);
										Toolket
												.setCellValue(
														workbook,
														sheet,
														index,
														3,
														Toolket
																.getClassFullName((String) m
																		.get("depart_class")),
														fontSize10,
														HSSFCellStyle.ALIGN_CENTER,
														true, null);
										Toolket.setCellValue(workbook, sheet,
												index++, 4, (String) m
														.get("chi_name"),
												fontSize10,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
										// break;
									}
								}
							}
						}
					} else {
						// 查詢非本學期(所以用ScoreHist判斷)
						for (Student student : students) {
							scoreHist = new ScoreHist(student.getStudentNo());
							scoreHist.setSchoolYear((short) Integer
									.parseInt(request.getParameter("year")));
							scoreHist.setSchoolTerm(sterm);
							scoreHistList = sm.findScoreHistBy(scoreHist);
							// HIST: {
							if (!scoreHistList.isEmpty()) {
								for (ScoreHist hist : scoreHistList) {

									if (IConstants.CSCODE_BEHAVIOR.equals(hist
											.getCscode()))
										continue;

									if (StringUtils.isNotBlank(hist
											.getStdepartClass())
											&& !Toolket.isLiteracyClass(hist
													.getStdepartClass())) {
										histDeptCode = StringUtils.substring(
												hist.getStdepartClass(), 3, 4);
										if (!deptCode
												.equalsIgnoreCase(histDeptCode)) {

											lastTermCounts++;

											Toolket.setCellValue(workbook,
													sheet, index, 0, student
															.getStudentNo(),
													fontSize10,
													HSSFCellStyle.ALIGN_CENTER,
													true, null);
											Toolket.setCellValue(workbook,
													sheet, index, 1, student
															.getStudentName(),
													fontSize10,
													HSSFCellStyle.ALIGN_CENTER,
													true, null);
											Toolket
													.setCellValue(
															workbook,
															sheet,
															index,
															2,
															Toolket
																	.getClassFullName(student
																			.getDepartClass()),
															fontSize10,
															HSSFCellStyle.ALIGN_CENTER,
															true, null);
											Toolket
													.setCellValue(
															workbook,
															sheet,
															index,
															3,
															Toolket
																	.getClassFullName(hist
																			.getStdepartClass()),
															fontSize10,
															HSSFCellStyle.ALIGN_CENTER,
															true, null);

											csnos = cm.getCsnameBy(hist
													.getCscode());
											if (!csnos.isEmpty())
												chiName = ((Csno) csnos.get(0))
														.getChiName();
											else
												chiName = "";

											Toolket.setCellValue(workbook,
													sheet, index++, 4, chiName,
													fontSize10,
													HSSFCellStyle.ALIGN_CENTER,
													true, null);
											// break HIST;
										}
									}
								}
							}
							// }
						}
					}
				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "Calculate.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 應屆畢業生基本資料(師大格式)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param sterm
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void printGstmdList4Ntnu(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String thisYear = cm.getNowBy("School_year");
		// String thisTerm = am.findTermBy(PARAMETER_SCHOOL_TERM);
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);

		String departClass = null;
		List<Student> students = null;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Example example = null;
		Gmark gmark = null;
		boolean flag = false;
		Calendar now = Calendar.getInstance();
		String year = String.valueOf(now.get(Calendar.YEAR) - 1911);

		if (!clazzes.isEmpty()) {
			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/GstmdList4Ntnu.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);
			workbook.setSheetName(0, thisYear + "學年度應屆畢業生欄位");

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("Arial Unicode MS");

			int index = 2;
			String entrance = null;

			for (Clazz clazz : clazzes) {
				departClass = clazz.getClassNo();
				if (Toolket.isGraduateClass(departClass)
						|| Toolket.isDelayClass(departClass)) {

					students = mm.findStudentsByClassNo(departClass);
					for (Student student : students) {
						Toolket.setCellValue(sheet, index, 0, "1061");
						Toolket.setCellValue(sheet, index, 1, student
								.getStudentNo());

						entrance = student.getEntrance() == null ? "" : student
								.getEntrance().toString();
						if (StringUtils.isNotBlank(entrance)) {
							Toolket.setCellValue(sheet, index, 2, StringUtils
									.substring(entrance, 0, 2));
							Toolket.setCellValue(sheet, index, 3,
									StringUtils.substring(entrance, 2)
											.startsWith("0") ? StringUtils
											.substring(entrance, 3)
											: StringUtils
													.substring(entrance, 2));
						} else {

							Toolket.setCellValue(sheet, index, 2, "");
							Toolket.setCellValue(sheet, index, 3, "");
						}

						Toolket.setCellValue(sheet, index, 4, year); // 抓當時的年
						Toolket.setCellValue(sheet, index, 5, "6"); // 李小姐說的

						Toolket.setCellValue(sheet, index, 6, Toolket
								.getDeptCode4Ntnu(student.getDepartClass()));
						Toolket.setCellValue(sheet, index, 7, Toolket
								.getDepartName(student.getDepartClass()));
						Toolket
								.setCellValue(sheet, index, 8, student
										.getDivi());
						Toolket.setCellValue(sheet, index, 9, Toolket
								.getClassFullName(student.getDepartClass()));
						Toolket.setCellValue(sheet, index, 10, Toolket
								.getCampCode4Ntnu(student.getDepartClass()));
						Toolket.setCellValue(sheet, index, 11, student
								.getStudentName());
						Toolket.setCellValue(sheet, index, 12, student
								.getIdno());
						Toolket.setCellValue(sheet, index, 13, df
								.format(student.getBirthday()));
						Toolket
								.setCellValue(sheet, index, 14, student
										.getSex());
						Toolket.setCellValue(sheet, index, 15, StringUtils
								.isBlank(student.getEmail()) ? Toolket
								.getDefaultStudentEmail(student, "") : student
								.getEmail());
						Toolket.setCellValue(sheet, index, 16, student
								.getCurrPost());
						Toolket.setCellValue(sheet, index, 17, student
								.getCurrAddr());
						Toolket.setCellValue(sheet, index, 18, "3"
								.equals(student.getIdent())
								|| "E".equals(student.getIdent()) ? "1" : "0");
						Toolket.setCellValue(sheet, index, 19, "5"
								.equals(student.getIdent()) ? "2" : "1");
						Toolket.setCellValue(sheet, index, 20, "0");
						// 碩士班都要是0(李小姐說的)
						Toolket.setCellValue(sheet, index, 21, !Toolket
								.isMasterClass(student.getDepartClass())
								&& Toolket.isDelayClass(student
										.getDepartClass()) ? "1" : "0");

						if ("3".equals(student.getOccurStatus())
								&& !Toolket.isMasterClass(student
										.getDepartClass()))
							Toolket.setCellValue(sheet, index, 22, "8"); // 轉學或插班考試
						else
							Toolket.setCellValue(sheet, index, 22, Toolket
									.get4IdentNtnu(student.getIdent(), Toolket
											.isMasterClass(student
													.getDepartClass())));

						gmark = new Gmark();
						gmark.setStudentNo(student.getStudentNo());
						gmark.setOccurStatus("5");
						example = Example.create(gmark);
						flag = !((List<Gmark>) am.findSQLWithCriteria(
								Gmark.class, example, null, null)).isEmpty();

						Toolket
								.setCellValue(sheet, index, 23, flag ? "1"
										: "0");
						Toolket.setCellValue(sheet, index, 24, "1"
								.equals(student.getExtraStatus())
								|| "E".equals(student.getExtraStatus()) ? "1"
								: "0");
						Toolket.setCellValue(sheet, index, 25, "2"
								.equals(student.getExtraStatus())
								|| "T".equals(student.getExtraStatus()) ? "1"
								: "0");

						gmark.setOccurStatus("1");
						example = Example.create(gmark);
						flag = !((List<Gmark>) am.findSQLWithCriteria(
								Gmark.class, example, null, null)).isEmpty();
						Toolket.setCellValue(sheet, index++, 26, flag ? "1"
								: "0");
					}
				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "GstmdList4Ntnu.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 1,3年級學生基本資料(師大格式)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param sterm
	 * @throws Exception
	 */
	private void printGstmdList4Ntnu1(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);

		String departClass = null;
		List<Student> students = null;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		if (!clazzes.isEmpty()) {
			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/GstmdList4Ntnu1.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);
			workbook.setSheetName(0, "1與3年級學生欄位");

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("Arial Unicode MS");

			int index = 2;
			String[] includeGrade = { "1", "3" }; // 年級
			String[] excludeIdent = { "7", "8" }; // 僑生, 外籍

			for (Clazz clazz : clazzes) {
				departClass = clazz.getClassNo();
				if (ArrayUtils.contains(includeGrade, clazz.getGrade())) {

					students = mm.findStudentsByClassNo(departClass);
					for (Student student : students) {
						if (!ArrayUtils.contains(excludeIdent, student
								.getIdent())) {
							Toolket.setCellValue(sheet, index, 0, "1061");
							Toolket.setCellValue(sheet, index, 1, String
									.valueOf(ArrayUtils.indexOf(includeGrade,
											clazz.getGrade()) + 1));
							Toolket.setCellValue(sheet, index, 2, student
									.getStudentNo());
							Toolket
									.setCellValue(sheet, index, 3, Toolket
											.getDeptCode4Ntnu(student
													.getDepartClass()));
							Toolket.setCellValue(sheet, index, 4, Toolket
									.getDepartName(student.getDepartClass()));
							Toolket.setCellValue(sheet, index, 5, student
									.getDivi());
							Toolket
									.setCellValue(sheet, index, 6, Toolket
											.getCampCode4Ntnu(student
													.getDepartClass()));
							Toolket.setCellValue(sheet, index, 7, student
									.getStudentName());
							Toolket.setCellValue(sheet, index, 8, student
									.getIdno());
							Toolket.setCellValue(sheet, index, 9, df
									.format(student.getBirthday()));
							Toolket.setCellValue(sheet, index, 10, student
									.getSex());
							Toolket.setCellValue(sheet, index, 11, Toolket
									.getDefaultStudentEmail(student, ""));
							Toolket.setCellValue(sheet, index, 12, StringUtils
									.trimToEmpty(student.getEmail()));
							// 用通訊地址
							Toolket.setCellValue(sheet, index, 13, student
									.getCurrPost());
							Toolket.setCellValue(sheet, index, 14, student
									.getCurrAddr());
							Toolket.setCellValue(sheet, index, 15, "0");
							Toolket.setCellValue(sheet, index, 16, "5"
									.equals(student.getIdent()) ? "1" : "0");
							Toolket.setCellValue(sheet, index, 17, "0");
							Toolket.setCellValue(sheet, index, 18, "3"
									.equals(student.getOccurStatus()) ? "8"
									: Toolket.get4IdentNtnu(student.getIdent(),
											false));
							Toolket.setCellValue(sheet, index++, 19, "0");
						}
					}
				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "GstmdList4Ntnu1.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 表4-1
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param sterm
	 * @throws Exception
	 */
	private void printListing41(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Map<String, Integer> info = Toolket.getPreviousYearTerm();
		String lastYear = info.get(IConstants.PARAMETER_SCHOOL_YEAR).toString();
		String lastTerm = info.get(IConstants.PARAMETER_SCHOOL_TERM).toString();
		List<DeptCode4Yun> deptCodes = mm
				.findDeptCode4YunBy(new DeptCode4Yun());

		if (!deptCodes.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("表4-1");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 3000);
			sheet.setColumnWidth(5, 3000);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("Arial Unicode MS");

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("Arial Unicode MS");

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, lastYear + "學年度第"
					+ lastTerm + "學期畢業生人數", fontSize16,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "科系代碼", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "學制代碼", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "科系", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "學制", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "男生數", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "女生數", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;
			Graduate graduate = new Graduate();
			graduate.setOccurYear(Short.valueOf(lastYear));
			graduate.setOccurTerm(lastTerm);
			graduate.setOccurStatus("6"); // 畢業
			graduate.setBirthDate(null);
			graduate.setBirthday2(null);
			graduate.setDepartClass2(null);
			graduate.setIdno(null);
			graduate.setInformixPass(null);
			graduate.setOccurStatus2(null);
			graduate.setPassword(null);
			graduate.setPriority(null);
			graduate.setSex2(null);
			graduate.setUndeleteReason(null);
			graduate.setUnit2(null);

			for (DeptCode4Yun code : deptCodes) {

				Toolket.setCellValue(workbook, sheet, index, 0, code
						.getDeptCode(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, code
						.getCampusCode(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 2, code
						.getDeptName(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, index, 3, code
						.getCampusName(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);

				graduate.setDepartClass(code.getClassNo());
				graduate.setSex("1");
				Toolket.setCellValue(workbook, sheet, index, 4, String
						.valueOf(mm.findGraduatesBy(graduate).size()),
						fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
				graduate.setSex("2");
				Toolket.setCellValue(workbook, sheet, index++, 5, String
						.valueOf(mm.findGraduatesBy(graduate).size()),
						fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "List41Ntnu.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 表4-2
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param sterm
	 * @throws Exception
	 */
	private void printListing42(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String thisYear = cm.getNowBy("School_year");
		String thisTerm = am.findTermBy(PARAMETER_SCHOOL_TERM);
		List<DeptCode4Yun> deptCodes = mm
				.findDeptCode4YunBy(new DeptCode4Yun());

		if (!deptCodes.isEmpty()) {

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/List42Ntnu.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);

			// Header
			Toolket.setCellValue(sheet, 0, 0, thisYear + "學年度第" + thisTerm
					+ "實際在校學生人數");

			int index = 2;
			Student student = new Student();
			// student.setIdent("1"); // 一般生
			student.setBirthDate(null);
			student.setBirthday2(null);
			student.setDepartClass2(null);
			student.setIdno(null);
			student.setInformixPass(null);
			student.setOccurStatus2(null);
			student.setPassword(null);
			student.setPriority(null);
			student.setSex2(null);
			student.setUndeleteReason(null);
			student.setUnit2(null);

			for (DeptCode4Yun code : deptCodes) {

				for (int i = 1; i <= code.getGradeYear(); i++) {

					Toolket.setCellValue(sheet, index, 0, code.getDeptCode());
					Toolket.setCellValue(sheet, index, 1, code.getCampusCode());
					Toolket.setCellValue(sheet, index, 2, code.getDeptName());
					Toolket.setCellValue(sheet, index, 3, code.getCampusName());
					Toolket.setCellValue(sheet, index, 4, String.valueOf(i));

					student.setDepartClass(code.getClassNo() + i);
					student.setSex("1");
					Toolket.setCellValue(sheet, index, 5, String.valueOf(mm
							.findStudentsBy(student).size()));
					student.setSex("2");
					Toolket.setCellValue(sheet, index++, 6, String.valueOf(mm
							.findStudentsBy(student).size()));
				}

			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "List42Ntnu.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}

	}

	/**
	 * 表4-4-1
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param sterm
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void printListing441(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		request.getSession(false).setMaxInactiveInterval(-1);
		Map<String, Integer> info = Toolket.getPreviousYearTerm();
		String lastYear = info.get(IConstants.PARAMETER_SCHOOL_YEAR).toString();
		String lastTerm = info.get(IConstants.PARAMETER_SCHOOL_TERM).toString();
		List<DeptCode4Yun> deptCodes = mm
				.findDeptCode4YunBy(new DeptCode4Yun());

		if (!deptCodes.isEmpty()) {

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/List441Ntnu.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);

			// Header
			Toolket.setCellValue(sheet, 0, 0, lastYear + "學年度第" + lastTerm
					+ "學期休退學人數及原因");

			int index = 2, counts = 0, boyCounts = 0, girlCounts = 0;
			String departClass = null;
			List<Object> ret = null;
			Object[] o = null;
			String hql1 = "SELECT g.sex, COUNT(g.sex) AS counts FROM Graduate g "
					+ "WHERE g.occurYear = ? AND g.occurTerm = ? "
					+ "AND g.occurStatus = ? AND g.departClass LIKE ? "
					+ "AND g.occurCause IN ('001', '006', '007', '008', '009', '010', '038') "
					+ "GROUP BY g.sex";
			String hql2 = "SELECT g.sex, COUNT(g.sex) AS counts FROM Graduate g "
					+ "WHERE g.occurYear = ? AND g.occurTerm = ? "
					+ "AND g.occurStatus = ? AND g.departClass LIKE ? "
					+ "AND g.occurCause IN ('002', '011', '012', '013', '014') "
					+ "GROUP BY g.sex";
			String hql3 = "SELECT g.sex, COUNT(g.sex) AS counts FROM Graduate g "
					+ "WHERE g.occurYear = ? AND g.occurTerm = ? "
					+ "AND g.occurStatus = ? AND g.departClass LIKE ? "
					+ "AND g.occurCause IN ('003', '016', '017', '018', '019') "
					+ "GROUP BY g.sex";
			String hql4 = "SELECT g.sex, COUNT(g.sex) AS counts FROM Graduate g "
				+ "WHERE g.occurYear = ? AND g.occurTerm = ? "
				+ "AND g.occurStatus = ? AND g.departClass LIKE ? "
				+ "AND g.occurCause IN ('025') "
				+ "GROUP BY g.sex";
			String hql5 = "SELECT g.sex, COUNT(g.sex) AS counts FROM Graduate g "
					+ "WHERE g.occurYear = ? AND g.occurTerm = ? "
					+ "AND g.occurStatus = ? AND g.departClass LIKE ? "
					+ "AND g.occurCause IN ('022', '030', '031', '037', '039') "
					+ "GROUP BY g.sex";
			String hql6 = "SELECT g.sex, COUNT(g.sex) AS counts FROM Graduate g "
					+ "WHERE g.occurYear = ? AND g.occurTerm = ? "
					+ "AND g.occurStatus = ? AND g.departClass LIKE ? "
					+ "AND g.occurCause IN ('022', '035') " + "GROUP BY g.sex";
			String hql7 = "SELECT g.sex, COUNT(g.sex) AS counts FROM Graduate g "
					+ "WHERE g.occurYear = ? AND g.occurTerm = ? "
					+ "AND g.occurStatus = ? AND g.departClass LIKE ? "
					+ "AND g.occurCause IN ('025', '029') " + "GROUP BY g.sex";
			String hql8 = "SELECT COUNT(*) FROM Graduate g "
					+ "WHERE g.occurYear = ? AND g.occurTerm = ? "
					+ "AND g.occurStatus = ? AND g.sex = ? "
					+ "AND g.departClass LIKE ?";
			for (DeptCode4Yun code : deptCodes) {

				for (int i = 1; i <= code.getGradeYear(); i++) {

					if (StringUtils.isBlank(Toolket.getClassFullName(code
							.getClassNo()
							+ i + "1")))
						continue;

					Toolket.setCellValue(sheet, index, 0, code.getDeptCode());
					Toolket.setCellValue(sheet, index, 1, code.getCampusCode());
					Toolket.setCellValue(sheet, index, 2, code.getDeptName());
					Toolket.setCellValue(sheet, index, 3, code.getCampusName());
					Toolket.setCellValue(sheet, index, 4, String.valueOf(i));

					departClass = code.getClassNo() + i + "_";
					ret = (List<Object>) am.find(hql1,
							new Object[] { Short.valueOf(lastYear), lastTerm,
									"2", departClass });

					if (!ret.isEmpty()) {
						for (Object obj : ret) {
							o = (Object[]) obj;
							if ("1".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 5,
										((Integer) o[1]).toString());
							} else if ("2".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 6,
										((Integer) o[1]).toString());
							}
						}
					}

					ret = (List<Object>) am.find(hql2,
							new Object[] { Short.valueOf(lastYear), lastTerm,
									"2", departClass });
					if (!ret.isEmpty()) {
						for (Object obj : ret) {
							o = (Object[]) obj;
							if ("1".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 7,
										((Integer) o[1]).toString());
							} else if ("2".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 8,
										((Integer) o[1]).toString());
							}
						}
					}
					
					ret = (List<Object>) am.find(hql3,
							new Object[] { Short.valueOf(lastYear), lastTerm,
									"2", departClass });
					if (!ret.isEmpty()) {
						for (Object obj : ret) {
							o = (Object[]) obj;
							if ("1".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 9,
										((Integer) o[1]).toString());
							} else if ("2".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 10,
										((Integer) o[1]).toString());
							}
						}
					}
					
					ret = (List<Object>) am.find(hql4,
							new Object[] { Short.valueOf(lastYear), lastTerm,
									"2", departClass });
					if (!ret.isEmpty()) {
						for (Object obj : ret) {
							o = (Object[]) obj;
							if ("1".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 11,
										((Integer) o[1]).toString());
							} else if ("2".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 12,
										((Integer) o[1]).toString());
							}
						}
					}
					
					ret = (List<Object>) am.find(hql5,
							new Object[] { Short.valueOf(lastYear), lastTerm,
									"2", departClass });
					if (!ret.isEmpty()) {
						for (Object obj : ret) {
							o = (Object[]) obj;
							if ("1".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 13,
										((Integer) o[1]).toString());
							} else if ("2".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 14,
										((Integer) o[1]).toString());
							}
						}
					}
					
					boyCounts = 0;
					girlCounts = 0;
					ret = (List<Object>) am.find(hql6,
							new Object[] { Short.valueOf(lastYear), lastTerm,
									"1", departClass });
					if (!ret.isEmpty()) {
						for (Object obj : ret) {
							o = (Object[]) obj;
							if ("1".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 15,
										((Integer) o[1]).toString());
								boyCounts += (Integer) o[1];
							} else if ("2".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 16,
										((Integer) o[1]).toString());
								girlCounts += (Integer) o[1];
							}
						}
					}
					
					ret = (List<Object>) am.find(hql7,
							new Object[] { Short.valueOf(lastYear), lastTerm,
									"1", departClass });
					if (!ret.isEmpty()) {
						for (Object obj : ret) {
							o = (Object[]) obj;
							if ("1".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 17,
										((Integer) o[1]).toString());
								boyCounts += (Integer) o[1];
							} else if ("2".equals((String) o[0])) {
								Toolket.setCellValue(sheet, index, 18,
										((Integer) o[1]).toString());
								girlCounts += (Integer) o[1];
							}
						}
					}
					
					ret = (List<Object>) am.find(hql8, new Object[] {
							Short.valueOf(lastYear), lastTerm, "1", "1",
							departClass });
					counts = (Integer) ret.get(0) - boyCounts;
					Toolket.setCellValue(sheet, index, 19, 0 == counts ? ""
							: String.valueOf(counts));

					ret = (List<Object>) am.find(hql8, new Object[] {
							Short.valueOf(lastYear), lastTerm, "1", "2",
							departClass });
					counts = (Integer) ret.get(0) - girlCounts;
					Toolket.setCellValue(sheet, index, 20, 0 == counts ? ""
							: String.valueOf(counts));
					
					index++;
				}

			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "List441Ntnu.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}

	}

	/**
	 * 表4-4-1
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param sterm
	 * @throws Exception
	 */
	void printListing441Bak(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		request.getSession(false).setMaxInactiveInterval(-1);
		Map<String, Integer> info = Toolket.getPreviousYearTerm();
		String lastYear = info.get(IConstants.PARAMETER_SCHOOL_YEAR).toString();
		String lastTerm = info.get(IConstants.PARAMETER_SCHOOL_TERM).toString();
		List<DeptCode4Yun> deptCodes = mm
				.findDeptCode4YunBy(new DeptCode4Yun());

		if (!deptCodes.isEmpty()) {

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/List441Ntnu.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);

			// Header
			Toolket.setCellValue(sheet, 0, 0, lastYear + "學年度第" + lastTerm
					+ "學期休退學人數及原因");

			int index = 2, counts = 0, boyCounts = 0, girlCounts = 0;
			Graduate graduate = new Graduate();
			graduate.setOccurYear(Short.valueOf(lastYear));
			graduate.setOccurTerm(lastTerm);
			graduate.setIdno(null);
			graduate.setGradSchlName(null);
			graduate.setIdentBasicName(null);
			graduate.setBirthDate(null);
			graduate.setBirthday2(null);
			graduate.setDepartClass2(null);
			graduate.setIdno(null);
			graduate.setInformixPass(null);
			graduate.setOccurStatus2(null);
			graduate.setPassword(null);
			graduate.setPriority(null);
			graduate.setSex2(null);
			graduate.setUndeleteReason(null);
			graduate.setUnit2(null);
			Example example = null;

			Criterion boy = Restrictions.eq("sex", "1");
			Criterion girl = Restrictions.eq("sex", "2");

			// 001 學業成績, 006 全部考試不及格, 007 一學期曠課達45小時
			// 008 達學期 2/3學分不及格, 009 連續兩學期 1/2學分不及格
			// 010 修業年限屆滿, 038 一學期曠課達36小時
			Criterion occurCause1 = Restrictions.in("occurCause", new String[] {
					"001", "006", "007", "008", "009", "010", "038" });

			// 002 操行, 011 考試舞弊, 012 賭博, 013 犯法, 014 操行不及格
			Criterion occurCause2 = Restrictions.in("occurCause", new String[] {
					"002", "011", "012", "013", "014" });

			// 003 志趣不合, 016 重考, 017 轉學, 018 逾期未註冊, 019 休學逾期未復學
			Criterion occurCause3 = Restrictions.in("occurCause", new String[] {
					"003", "016", "017", "018", "019" });

			// 025 經濟困難
			Criterion occurCause4 = Restrictions.in("occurCause",
					new String[] { "025" });

			// 022 生病, 030 其它, 031 死亡, 037 自退, 039 休學期滿
			Criterion occurCause5 = Restrictions.in("occurCause", new String[] {
					"022", "030", "031", "037", "039" });

			// 022 生病, 035 車禍
			Criterion occurCause6 = Restrictions.in("occurCause", new String[] {
					"022", "035" });

			// 025 經濟困難, 029 工作
			Criterion occurCause7 = Restrictions.in("occurCause", new String[] {
					"025", "029" });

			for (DeptCode4Yun code : deptCodes) {

				for (int i = 1; i <= code.getGradeYear(); i++) {

					Toolket.setCellValue(sheet, index, 0, code.getDeptCode());
					Toolket.setCellValue(sheet, index, 1, code.getCampusCode());
					Toolket.setCellValue(sheet, index, 2, code.getDeptName());
					Toolket.setCellValue(sheet, index, 3, code.getCampusName());
					Toolket.setCellValue(sheet, index, 4, String.valueOf(i));

					/*
					 * 退學原因： 因學業成績：指學業成績不及格達退學標準、曠課逾規定時間、延長修業年限屆滿等原因。
					 * 因操行成績：指考試作弊、賭博、犯法、操行不及格等原因。
					 * 因志趣不合：指重考、轉學、逾期未註冊、休學逾期未復學…等原因等。 因經濟因素：指考量家庭經濟狀況而退學者。
					 * 其他原因 ：包括死亡、開除學籍其他原因。 休學原因： 因病：指因為身心狀況不佳而休學者。
					 * 因經濟因素：指考量家庭經濟狀況而休學者。 其他原因：非屬上述原因者歸之。
					 */

					if (StringUtils.isBlank(Toolket.getClassFullName(code
							.getClassNo()
							+ i + "1")))
						continue;

					graduate.setDepartClass(code.getClassNo() + i + "_");
					graduate.setOccurStatus("2"); // 退學
					example = Example.create(graduate).ignoreCase().enableLike(
							MatchMode.EXACT);

					// occurCause = Restrictions.in("occurCause", new String[] {
					// "001", "006", "007", "008", "009", "010", "038" });
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							boy, occurCause1).size();
					Toolket.setCellValue(sheet, index, 5, 0 == counts ? ""
							: String.valueOf(counts));
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							girl, occurCause1).size();
					Toolket.setCellValue(sheet, index, 6, 0 == counts ? ""
							: String.valueOf(counts));

					// occurCause = Restrictions.in("occurCause", new String[] {
					// "002", "011", "012", "013", "014" });
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							boy, occurCause2).size();
					Toolket.setCellValue(sheet, index, 7, 0 == counts ? ""
							: String.valueOf(counts));
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							girl, occurCause2).size();
					Toolket.setCellValue(sheet, index, 8, 0 == counts ? ""
							: String.valueOf(counts));

					// occurCause = Restrictions.in("occurCause", new String[] {
					// "003", "016", "017", "018", "019" });
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							boy, occurCause3).size();
					Toolket.setCellValue(sheet, index, 9, 0 == counts ? ""
							: String.valueOf(counts));
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							girl, occurCause3).size();
					Toolket.setCellValue(sheet, index, 10, 0 == counts ? ""
							: String.valueOf(counts));

					// occurCause = Restrictions.in("occurCause",
					// new String[] { "025" });
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							boy, occurCause4).size();
					Toolket.setCellValue(sheet, index, 11, 0 == counts ? ""
							: String.valueOf(counts));
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							girl, occurCause4).size();
					Toolket.setCellValue(sheet, index, 12, 0 == counts ? ""
							: String.valueOf(counts));

					// occurCause = Restrictions.in("occurCause", new String[] {
					// "022", "030", "031", "037", "039" });
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							boy, occurCause5).size();
					Toolket.setCellValue(sheet, index, 13, 0 == counts ? ""
							: String.valueOf(counts));
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							girl, occurCause5).size();
					Toolket.setCellValue(sheet, index, 14, 0 == counts ? ""
							: String.valueOf(counts));

					graduate.setOccurStatus("1"); // 休學
					example = Example.create(graduate).ignoreCase().enableLike(
							MatchMode.EXACT);
					boyCounts = 0;
					girlCounts = 0;

					// occurCause = Restrictions.in("occurCause", new String[] {
					// "022", "035" });
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							boy, occurCause6).size();
					boyCounts += counts;
					Toolket.setCellValue(sheet, index, 15, 0 == counts ? ""
							: String.valueOf(counts));
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							girl, occurCause6).size();
					girlCounts += counts;
					Toolket.setCellValue(sheet, index, 16, 0 == counts ? ""
							: String.valueOf(counts));

					// occurCause = Restrictions.in("occurCause", new String[] {
					// "025", "029" });
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							boy, occurCause7).size();
					boyCounts += counts;
					Toolket.setCellValue(sheet, index, 17, 0 == counts ? ""
							: String.valueOf(counts));
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							girl, occurCause7).size();
					girlCounts += counts;
					Toolket.setCellValue(sheet, index, 18, 0 == counts ? ""
							: String.valueOf(counts));

					counts = mm.findSQLWithCriteria(Graduate.class, example,
							boy).size()
							- boyCounts;
					Toolket.setCellValue(sheet, index, 19, 0 == counts ? ""
							: String.valueOf(counts));
					counts = mm.findSQLWithCriteria(Graduate.class, example,
							girl).size()
							- girlCounts;
					Toolket.setCellValue(sheet, index++, 20, 0 == counts ? ""
							: String.valueOf(counts));

				}

			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "List441Ntnu.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}

	}

	/**
	 * 本學期學生未選課清單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param sterm
	 * @throws Exception
	 */
	private void printStmdUnSeld(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);
		String thisYear = cm.getNowBy("School_year");
		String thisTerm = am.findTermBy(PARAMETER_SCHOOL_TERM);

		if (!clazzes.isEmpty()) {

			List<Student> students = null;
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(thisYear + "學年度第" + thisTerm
					+ "學期學生未選課清單");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 3500);
			sheet.setColumnWidth(4, 3500);
			sheet.setColumnWidth(5, 3500);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "本學期學生未選課清單",
					fontSize16, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "學號", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "姓名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "班級", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "連絡電話", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "連絡手機", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "Email", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;
			List<Seld> selds = null;

			for (Clazz clazz : clazzes) {

				students = mm.findStudentsByClassNo(clazz.getClassNo());
				if (!students.isEmpty()) {
					for (Student student : students) {
						selds = cm.findSeldByStudentNoAndTerm(student
								.getStudentNo(), thisTerm);

						if (selds.isEmpty()) {

							Toolket.setCellValue(workbook, sheet, index, 0,
									student.getStudentNo(), fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 1,
									student.getStudentName(), fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 2,
									Toolket.getClassFullName(student
											.getDepartClass()), fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 3,
									StringUtils.defaultIfEmpty(student
											.getTelephone(), ""), fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 4,
									StringUtils.defaultIfEmpty(student
											.getCellPhone(), ""), fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index++, 5,
									StringUtils.defaultIfEmpty(student
											.getEmail(), ""), fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, null);
						}
					}

				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "StmdUnSeld.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 證照資料列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printStdSkillList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		ServletContext context = request.getSession().getServletContext();

		StdSkill skill = new StdSkill();
		skill.setAmount(null); // 避免被查詢
		Example example = Example.create(skill).ignoreCase().enableLike(
				MatchMode.ANYWHERE);
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("schoolYear"));
		orders.add(Order.asc("schoolTerm"));
		orders.add(Order.asc("studentNo"));

		List<Criterion> cris = new LinkedList<Criterion>();
		Criterion cri = null;

		if (aForm.getStrings("year").length != 0) {
			cri = Restrictions.eq("schoolYear", aForm.getStrings("year")[0]);
			cris.add(cri);
		}

		if (StringUtils.isNotBlank(aForm.getString("sterm"))) {
			cri = Restrictions.eq("schoolTerm", aForm.getString("sterm"));
			cris.add(cri);
		}

		if (StringUtils.isNotBlank(aForm.getString("deptCodeOpt"))) {
			cri = Restrictions.eq("deptNo", aForm.getString("deptCodeOpt"));
			cris.add(cri);
		}

		if (StringUtils.isNotBlank(aForm.getString("licenseValidDateStart"))
				|| StringUtils.isNotBlank(aForm
						.getString("licenseValidDateEnd"))) {
			Date from = StringUtils.isBlank(aForm
					.getString("licenseValidDateStart")) ? null : Toolket
					.parseNativeDate(aForm.getString("licenseValidDateStart"));
			// 沒結束就預設今天
			Date to = StringUtils.isBlank(aForm
					.getString("licenseValidDateEnd")) ? Calendar.getInstance()
					.getTime() : Toolket.parseNativeDate(aForm
					.getString("licenseValidDateEnd"));

			if (from != null) {
				cri = Restrictions.between("licenseValidDate", from, to);
				cris.add(cri);
			}
		}

		List<StdSkill> skills = (List<StdSkill>) am.findSQLWithCriteria(
				StdSkill.class, example, null, orders, cris);

		int index = 2;
		List<LicenseCode> codes = null;
		Student student = null;
		Graduate graduate = null;
		Empl empl = null;
		DEmpl dempl = null;
		Csno csno = null;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat df1 = new SimpleDateFormat("yyyy/MM");

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/DeptStdSkillList.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		for (StdSkill ss : skills) {
			codes = (List<LicenseCode>) am.findLicenseCodesBy(new LicenseCode(
					Integer.valueOf(ss.getLicenseCode())));

			if (!codes.isEmpty())
				ss.setLicense(codes.get(0));

			student = mm.findStudentByNo(ss.getStudentNo().trim());
			if (student == null) {
				graduate = mm.findGraduateByStudentNo(ss.getStudentNo().trim());
				if (graduate != null) {
					ss.setStudentName(graduate.getStudentName().trim());
					ss.setDepartClass(Toolket.getClassFullName(graduate
							.getDepartClass()));
				}
			} else {
				ss.setStudentName(student.getStudentName().trim());
				ss.setDepartClass(Toolket.getClassFullName(student
						.getDepartClass()));
			}

			Toolket.setCellValue(sheet, index, 0, ss.getSchoolYear() + "."
					+ ss.getSchoolTerm());
			Toolket.setCellValue(sheet, index, 1, ss.getStudentNo()
					.toUpperCase());
			Toolket.setCellValue(sheet, index, 2, ss.getStudentName());
			Toolket.setCellValue(sheet, index, 3, ss.getDepartClass());
			Toolket.setCellValue(sheet, index, 4, ss.getLicense().getCode()
					.toString());
			Toolket.setCellValue(sheet, index, 5, ss.getLicense().getName()
					.trim());
			Toolket.setCellValue(sheet, index, 6, ss.getLicense().getLocale()
					.toString());
			Toolket.setCellValue(sheet, index, 7, ss.getLicense().getLevel()
					.trim());
			Toolket.setCellValue(sheet, index, 8, ss.getLicense().getType()
					.toString());
			Toolket.setCellValue(sheet, index, 9, ss.getAmount().toString());
			Toolket.setCellValue(sheet, index, 10, Toolket.getAmountType(ss
					.getAmountType()));
			Toolket.setCellValue(sheet, index, 11,
					ss.getAmountDate() == null ? "" : df1.format(ss
							.getAmountDate()));
			Toolket.setCellValue(sheet, index, 12, ss.getLicenseNo());
			Toolket.setCellValue(sheet, index, 13, df.format(ss
					.getLicenseValidDate()));

			if (StringUtils.isBlank(ss.getCscode())) {
				csno = cm.findCourseInfoByCscode(ss.getCscode());
				if (csno != null)
					Toolket.setCellValue(sheet, index, 14, csno.getChiName()
							.trim());
			} else
				Toolket.setCellValue(sheet, index, 14, "");

			if (StringUtils.isBlank(ss.getTechIdno())) {
				empl = mm.findEmplByIdno(ss.getTechIdno());
				if (empl != null)
					Toolket.setCellValue(sheet, index, 15, empl.getEname()
							.trim());
				else {
					dempl = mm.findDEmplByIdno(ss.getTechIdno());
					if (dempl != null)
						Toolket.setCellValue(sheet, index, 15, dempl.getEname()
								.trim());
				}
			} else
				Toolket.setCellValue(sheet, index, 15, "");

			Toolket.setCellValue(sheet, index, 16, ss.getSerialNo());
			Toolket.setCellValue(sheet, index, 17, Toolket.getCustomNo(ss
					.getCustomNo()));
			Toolket.setCellValue(sheet, index++, 18, Toolket.getPass(ss
					.getPass()));
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "StdSkillList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 畢業班畢業學分數資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printGstmdCreditAvg(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		ServletContext context = request.getSession().getServletContext();

		String year = aForm.getStrings("year")[0];
		Graduate graduate = new Graduate();
		graduate.setOccurYear(Short.valueOf(year));
		graduate.setOccurTerm(sterm);
		graduate.setOccurStatus("6"); // 畢業
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("departClass"));
		Example example = Example.create(graduate).ignoreCase().enableLike(
				MatchMode.EXACT);
		List<Graduate> gstmds = (List<Graduate>) am.findSQLWithCriteria(
				Graduate.class, example, null, orders);

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/GstmdCreditAvgList.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + sterm
				+ "學期畢業班畢業學分數資料");

		int index = 5, anoGstmdCounts = 0, anoGstmdCollegeCounts = 0;
		Float passScore = 60F, totalCredits = 0.0F, sumCredits = 0.0F;
		Float anoDeptCredits = 0.0F, sumAnoDeptCredits = 0.0F;
		Float anoCollegeCredits = 0.0F, sumAnoCollegeCredits = 0.0F;
		String hql = "SELECT s.credit, s.score, s.cscode, s.stdepartClass "
				+ "FROM ScoreHist s WHERE s.studentNo = ? "
				+ "AND s.score >= ? AND s.cscode NOT IN ('99999')";
		String gstmdDeptCode = null, deptCode = null, stdepartClass = null;
		String gstmdCollegeCode = null, collegeCode = null;
		List<Object> scoreHist = null;
		Object[] data = null;
		NumberFormat nf = new DecimalFormat("#,###,##.##");
		// 國文 中華人文 通識課程 英文(做很趕只好...)
		String[] denied = { "50001", "S0880", "S0881", "S0882", "T0880",
				"T0881", "T0882", "T0E30", "T0002", "50002", "S0350", "S0351",
				"S0352", "S0353", "S0354", "T0003", "T0004", "T0007", "T0350",
				"T0351", "T0352" };

		if (!gstmds.isEmpty()) {
			for (Graduate gstmd : gstmds) {
				if (Toolket.isMasterClass(gstmd.getDepartClass()))
					passScore = 70F;
				else
					passScore = 60F;

				scoreHist = (List<Object>) am.find(hql, new Object[] {
						gstmd.getStudentNo(), passScore });

				if (!scoreHist.isEmpty()) {
					gstmdDeptCode = StringUtils.substring(gstmd
							.getDepartClass(), 3, 4);
					gstmdCollegeCode = Toolket.getCollegeCode(gstmdDeptCode);
					totalCredits = 0.0F;
					anoDeptCredits = 0.0F;
					anoCollegeCredits = 0.0F;
					for (Object o : scoreHist) {
						data = (Object[]) o;
						totalCredits += (Float) data[0];
						stdepartClass = data[3] == null ? "" : (String) data[3];
						deptCode = StringUtils.isBlank(stdepartClass) ? ""
								: StringUtils.substring(stdepartClass, 3, 4);
						collegeCode = Toolket.getCollegeCode(deptCode);

						if (StringUtils.isNotBlank(stdepartClass)
								&& !Toolket.isLiteracyClass(stdepartClass)
								&& data[2] != null
								&& !ArrayUtils.contains(denied,
										(String) data[2])
								&& !gstmdDeptCode.equals(deptCode))
							anoDeptCredits += (Float) data[0];

						if (StringUtils.isNotBlank(stdepartClass)
								&& !Toolket.isLiteracyClass(stdepartClass)
								&& data[2] != null
								&& !ArrayUtils.contains(denied,
										(String) data[2])
								&& !gstmdCollegeCode.equals(collegeCode))
							anoCollegeCredits += (Float) data[0];
					}

					Toolket.setCellValue(sheet, index, 0, gstmd.getStudentNo()
							.toUpperCase().trim());
					Toolket.setCellValue(sheet, index, 1, gstmd
							.getStudentName().toUpperCase().trim());
					Toolket.setCellValue(sheet, index, 2, Toolket
							.getClassFullName(gstmd.getDepartClass()));
					Toolket.setCellValue(sheet, index, 3, Float
							.toString(totalCredits));
					Toolket.setCellValue(sheet, index, 4, Float
							.toString(anoDeptCredits));
					Toolket.setCellValue(sheet, index++, 5, Float
							.toString(anoCollegeCredits));

					sumCredits += totalCredits;
					if (anoDeptCredits > 0.0F) {
						sumAnoDeptCredits += anoDeptCredits;
						anoGstmdCounts++;
					}

					if (anoCollegeCredits > 0.0F) {
						sumAnoCollegeCredits += anoCollegeCredits;
						anoGstmdCollegeCounts++;
					}
				}
			}

			StringBuilder builder = new StringBuilder("");
			builder.append("總人數 : ").append(gstmds.size())
					.append(" 人, 總學分數 : ");
			builder.append(nf.format(sumCredits)).append(", 平均 : ");
			builder.append(nf.format(sumCredits / (float) gstmds.size()))
					.append("/人");
			Toolket.setCellValue(sheet, 2, 0, builder.toString());

			builder = new StringBuilder();
			builder.append("跨系總人數 : ").append(anoGstmdCounts).append(" 人, ");
			builder.append("總學分數 : ").append(sumAnoDeptCredits);
			builder.append(" 平均 : ").append(
					nf.format(sumAnoDeptCredits / (float) anoGstmdCounts))
					.append("/人");
			Toolket.setCellValue(sheet, 3, 0, builder.toString());

			builder = new StringBuilder();
			builder.append("跨院總人數 : ").append(anoGstmdCollegeCounts).append(
					" 人, ");
			builder.append("總學分數 : ").append(sumAnoCollegeCredits);
			builder.append(" 平均 : ").append(
					nf.format(sumAnoCollegeCredits
							/ (float) anoGstmdCollegeCounts)).append("/人");
			Toolket.setCellValue(sheet, 4, 0, builder.toString());
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "GstmdCreditAvgList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();

	}

	/**
	 * 註冊檔資料清單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printRegisterManagerList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		ServletContext context = request.getSession().getServletContext();

		String year = aForm.getStrings("year")[0];
		String campusCode = aForm.getString("campusCode");
		String schoolType = aForm.getString("schoolType");

		String hql = "SELECT r.*, s.*, r.idno AS id "
				+ "FROM Register r LEFT JOIN stmd s ON r.idno = s.idno "
				+ "WHERE r.schoolYear = ? AND r.schoolTerm = ? "
				+ "AND r.campusCode = ? AND r.schoolType = ? "
				+ "ORDER BY realClassNo";
		List<Map> ret = am.findBySQL(hql, new Object[] { year, sterm,
				campusCode, schoolType });

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/RegisterManagerList.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + sterm
				+ "學期註冊檔資料清單");

		int index = 2;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		if (!ret.isEmpty()) {

			Graduate graduate = null;
			for (Map obj : ret) {

				Toolket.setCellValue(sheet, index, 0, (String) obj
						.get("studentName"));
				Toolket.setCellValue(sheet, index, 1, StringUtils
						.trimToEmpty((String) obj.get("realClassNo")));
				Toolket.setCellValue(sheet, index, 2, StringUtils
						.trimToEmpty(StringUtils.trimToEmpty((String) obj
								.get("realStudentNo"))));
				Toolket.setCellValue(sheet, index, 3, StringUtils
						.trimToEmpty((String) obj.get("virClassNo")));
				Toolket.setCellValue(sheet, index, 4, StringUtils
						.trimToEmpty((String) obj.get("virStudentNo")));
				Toolket.setCellValue(sheet, index, 5, ((Integer) obj
						.get("tuitionFee")).toString());
				Toolket.setCellValue(sheet, index, 6, (String) obj
						.get("tuitionAccountNo"));
				Toolket.setCellValue(sheet, index, 7, ((Integer) obj
						.get("tuitionAmount")).toString());
				Toolket.setCellValue(sheet, index, 8,
						obj.get("tuitionDate") == null ? "" : df
								.format((Date) obj.get("tuitionDate")));
				Toolket.setCellValue(sheet, index, 9, ((Integer) obj
						.get("agencyFee")).toString());
				Toolket.setCellValue(sheet, index, 10, (String) obj
						.get("agencyAccountNo"));
				Toolket.setCellValue(sheet, index, 11, ((Integer) obj
						.get("agencyAmount")).toString());
				Toolket.setCellValue(sheet, index, 12,
						obj.get("agencyDate") == null ? "" : df
								.format((Date) obj.get("agencyDate")));
				Toolket.setCellValue(sheet, index, 13, ((Integer) obj
						.get("reliefTuitionAmount")).toString());
				Toolket.setCellValue(sheet, index, 14, ((Integer) obj
						.get("loanAmount")).toString());
				Toolket.setCellValue(sheet, index, 15, ((Integer) obj
						.get("vulnerableAmount")).toString());
				Toolket.setCellValue(sheet, index, 16, (String) obj
						.get("newStudentReg"));
				Toolket.setCellValue(sheet, index, 17, (String) obj
						.get("isRegist"));
				if (obj.get("depart_class") == null) {
					graduate = mm.findGraduateByIdno(((String) obj.get("id"))
							.toUpperCase().trim());
					if (graduate != null)
						Toolket.setCellValue(sheet, index, 18, Toolket
								.getStatus(graduate.getOccurStatus(), true));
				}

				index++;
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "RegisterManagerList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();

	}

	/**
	 * 下載新生基本資料樣本檔(未編班編學號)
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printUploadNewStmdTemplate(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/UploadNewStmdTemplate.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "UploadNewStmdTemplate.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 下載新生基本資料樣本檔(已編班編學號)
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printUploadNewStmdTemplate1(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/UploadNewStmdTemplate1.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "UploadNewStmdTemplate1.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("preview", "printReport");
		return map;
	}

	private String processClassInfo(DynaActionForm form) {
		String campusInCharge2 = (String) form.get("campusInCharge2");
		String schoolInCharge2 = (String) form.get("schoolInCharge2");
		String deptInCharge2 = (String) form.get("deptInCharge2");
		String departClass = (String) form.get("classInCharge2");
		if(departClass!=null)return departClass;
		schoolInCharge2 = schoolInCharge2.equalsIgnoreCase("All") ? ""
				: schoolInCharge2;
		deptInCharge2 = deptInCharge2.equalsIgnoreCase("All") ? ""
				: deptInCharge2;
		departClass = departClass.equalsIgnoreCase("All") ? "" : departClass;
		return departClass.length() == 6 ? departClass : campusInCharge2
				+ schoolInCharge2 + deptInCharge2;
	}

	private String printNativeDate(String date) {
		return String.valueOf(Integer.parseInt(StringUtils
				.substring(date, 0, 4))
				- Global.NativeYearBase)
				+ StringUtils.substring(date, 4).replaceAll("0", "");
	}

}
