package tw.edu.chit.struts.action.deptassist;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.CodeEmpl;
import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.CourseSyllabus;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.DeptCode4Yun;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.LicenseCode;
import tw.edu.chit.model.LicenseCode961;
import tw.edu.chit.model.LicenseType;
import tw.edu.chit.model.LifeCounseling;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Rcact;
import tw.edu.chit.model.Rcbook;
import tw.edu.chit.model.Rcconf;
import tw.edu.chit.model.Rchono;
import tw.edu.chit.model.Rcjour;
import tw.edu.chit.model.Rcpet;
import tw.edu.chit.model.Rcproj;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.StdAbility;
import tw.edu.chit.model.StdSkill;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.Syllabus;
import tw.edu.chit.model.TeacherStayTime;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
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
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		Employee employee = (Employee) getUserCredential(session).getMember();
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", sterm);
		aForm.set("year", tw.edu.chit.struts.action.score.ReportPrintAction
				.getYearArray(cm.getNowBy("School_year")));

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		aForm.set("calendarYear", String.valueOf(year - Global.NativeYearBase));
		aForm.set("calendarYears",
				tw.edu.chit.struts.action.score.ReportPrintAction
						.getYearArray(String.valueOf(year + 1
								- Global.NativeYearBase)));

		List<CodeEmpl> codeEmpls = mm.findCodeEmplByCategory("UnitTeach");
		String[] unitCodes = new String[0];
		String[] unitNames = new String[0];
		unitCodes = (String[]) ArrayUtils.add(unitCodes, "");
		unitNames = (String[]) ArrayUtils.add(unitNames, "");
		for (CodeEmpl codeEmpl : codeEmpls) {
			unitCodes = (String[]) ArrayUtils.add(unitCodes, codeEmpl.getIdno()
					.trim());
			unitNames = (String[]) ArrayUtils.add(unitNames, codeEmpl.getName()
					.trim());
		}
		aForm.set("unitCodes", unitCodes);
		aForm.set("unitNames", unitNames);
		aForm.set("unitCode", employee.getUnit());

		setContentPage(session, "assistant/ReportPrint.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 列印作業
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public void printReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("calendarYear", request.getParameter("year"));
		aForm.set("sterm", request.getParameter("st"));
		aForm.set("printOpt", request.getParameter("p"));
		aForm.set("campusInCharge2", request.getParameter("c"));
		aForm.set("schoolInCharge2", request.getParameter("s"));
		aForm.set("deptInCharge2", request.getParameter("d"));
		aForm.set("classInCharge2", request.getParameter("cl"));
		aForm.set("printInterClass", request.getParameter("printInterClass"));
		aForm.set("licenseValidDateStart", request.getParameter("sd"));
		aForm.set("licenseValidDateEnd", request.getParameter("ed"));
		aForm.set("amountDateType", request.getParameter("adt"));
		String printOpt = aForm.getString("printOpt");
		String sterm = (String) aForm.get("sterm");

		if ("Syllabus".equals(printOpt)) {
			// 綱要列印
			printSyllabus(mapping, aForm, request, response, sterm);
		} else if ("Introduction".equals(printOpt)) {
			// 簡介列印
			printIntroduction(mapping, aForm, request, response, sterm);
		} else if ("Calculate".equals(printOpt)) {
			// 跨系選課人數
			printCalculate(mapping, aForm, request, response, sterm);
		} else if ("StayTimePrint".equalsIgnoreCase(printOpt)) {
			// 專任教師留校時間列印
			printStayTimePrint(mapping, aForm, request, response, sterm);
		} else if ("IdnoCheck".equals(printOpt)) {
			// 學生身分證字號檢核
			printIdnoCheckErrorStudentsList(mapping, aForm, request, response,
					sterm);
		} else if ("AbilityList".equals(printOpt)) {
			// 證照資料列印
			printAbilityList(mapping, aForm, request, response, sterm);
		} else if ("StdSkillList".equals(printOpt)) {
			// 報部證照資料列印
			printStdSkillList(mapping, aForm, request, response, sterm);
		} else if ("Rc1-7".equals(printOpt)) {
			// 1-7學術活動列印
			printRc17(mapping, aForm, request, response, sterm);
		} else if ("Rc1-8".equals(printOpt)) {
			// 1-8承接政府部門計劃與產學案列印
			printRc18(mapping, aForm, request, response, sterm);
		} else if ("Rc1-9".equals(printOpt)) {
			// 1-9期刊論文發表列印
			printRc19(mapping, aForm, request, response, sterm);
		} else if ("Rc1-10".equals(printOpt)) {
			// 1-10研討會論文發表列印
			printRc110(mapping, aForm, request, response, sterm);
		} else if ("Rc1-11".equals(printOpt)) {
			// 1-11專書(篇章)列印
			printRc111(mapping, aForm, request, response, sterm);
		} else if ("Rc1-12".equals(printOpt)) {
			// 1-12專利列印
			printRc112(mapping, aForm, request, response, sterm);
		} else if ("Rc1-13".equals(printOpt)) {
			// 1-13獲獎與榮譽列印
			printRc113(mapping, aForm, request, response, sterm);
		} else if ("LicenseCodes".equals(printOpt)) {
			// 962至今報部證照代碼對照表列印
			printLicenseCodes(mapping, aForm, request, response, sterm);
		} else if ("LicenseCodes961".equals(printOpt)) {
			// 961以前(含)報部證照代碼對照表列印
			printLicenseCodes961(mapping, aForm, request, response, sterm);
		} else if ("CscodeList".equals(printOpt)) {
			// 科目代碼表列印
			printCscodeList(mapping, aForm, request, response, sterm);
		} else if ("DeptStdSkillList".equals(printOpt)) {
			// 系所學生證照列印(匯入資料驗證用)
			printDeptStdSkillList(mapping, aForm, request, response, sterm);
		} else if ("DeptStdSkill4EnglishList".equals(printOpt)) {
			// 系所學生英文證照列印
			DeptStdSkill4EnglishList(mapping, aForm, request, response, sterm);
		} else if ("DeptGStdSkillList".equals(printOpt)) {
			// 系所休退畢轉學生證照列印
			printDeptGStdSkillList(mapping, aForm, request, response, sterm);
		} else if ("DeptStdSkillList-1".equals(printOpt)) {
			// 專業證照目次表
			printDeptStdSkillList1(mapping, aForm, request, response, sterm);
		} else if ("DeptStdSkillList-2".equals(printOpt)) {
			// 學生考取專業證照報名費補助清冊
			printDeptStdSkillList2(mapping, aForm, request, response, sterm);
		} else if ("DeptStdSkillList-3".equals(printOpt)) {
			// 系所證照類張數表
			printDeptStdSkillList3(mapping, aForm, request, response, sterm);
		} else if ("DeptStdSkillList-4".equals(printOpt)) {
			// 學生考取專業證照報名費補助總表
			printDeptStdSkillList4(mapping, aForm, request, response, sterm);
		} else if ("DeptStdSkillList-5".equals(printOpt)) {
			// 教師輔導學生取得證照一覽表
			printDeptStdSkillList5(mapping, aForm, request, response, sterm);
		} else if ("DeptStdSkillList-6".equals(printOpt)) {
			// 學生通過技能檢定證照人數明細表
			printDeptStdSkillList6(mapping, aForm, request, response, sterm);
		} else if ("DeptCode4Yun".equals(printOpt)) {
			// 雲科大學制科系代碼表列印
			printDeptCode4Yun(mapping, aForm, request, response, sterm);
		}
	}

	/**
	 * 列印綱要
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printSyllabus(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Integer year = Integer.valueOf(cm.getNowBy("School_year"));
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), true);

		if (!clazzes.isEmpty()) {

			File templateXLS = new File(
					context
							.getRealPath("/WEB-INF/reports/CourseSyllabusListPrint.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = null;
			int sheetIndex = 0;
			String departClass = null, emplName = null;
			Dtime dtime = null;
			DEmpl de = null;
			Csno csno = null;
			CourseSyllabus cs = null;
			Employee employee = null;
			List<Dtime> dtimes = null;
			List<Syllabus> syllabus = null;

			for (Clazz clazz : clazzes) {
				departClass = clazz.getClassNo();
				if (Toolket.isDelayClass(departClass))
					continue;

				dtime = new Dtime();
				dtime.setDepartClass(departClass);
				dtime.setSterm(sterm);
				dtimes = cm.findDtimeBy(dtime, "cscode");
				if (!dtimes.isEmpty()) {

					for (Dtime d : dtimes) {
						cs = cm.getCourseSyllabusByDtimeOid(d.getOid(), year,
								Integer.valueOf(sterm));

						if (cs == null)
							continue;

						csno = cm.findCourseInfoByCscode(d.getCscode());
						employee = mm.findEmployeeByIdno(d.getTechid());
						if (employee == null) {
							if (StringUtils.isBlank(d.getTechid())) {
								emplName = "";
							} else {
								if (StringUtils.isBlank(d.getTechid())) {
									emplName = "";
								} else {
									de = mm.findDEmplBy(
											new DEmpl(d.getTechid())).get(0);
									emplName = de.getCname();
								}
							}
						} else
							emplName = employee.getName();

						sheet = workbook.getSheetAt(sheetIndex);
						workbook.setSheetName(sheetIndex++, departClass
								+ " "
								+ csno.getChiName().replaceAll("/", " ")
										.replaceAll("：", " "));

						Toolket.setCellValue(sheet, 2, 0, Toolket.getCellValue(
								sheet, 2, 0).replaceAll("TECH", emplName)
								.replaceAll(
										"DEPT",
										Toolket.getClassFullName(d
												.getDepartClass())));
						Toolket.setCellValue(sheet, 3, 0, Toolket.getCellValue(
								sheet, 3, 0).replaceAll("HOUR",
								cs.getOfficeHours()));
						Toolket.setCellValue(sheet, 4, 0, Toolket.getCellValue(
								sheet, 4, 0).replaceAll("TITLE",
								csno.getChiName()));
						Toolket.setCellValue(sheet, 5, 0, Toolket.getCellValue(
								sheet, 5, 0).replaceAll(
								"ENG",
								StringUtils.isBlank(csno.getEngName()) ? ""
										: csno.getEngName()));
						Toolket.setCellValue(sheet, 6, 0, Toolket.getCellValue(
								sheet, 6, 0).replaceAll("YEAR",
								cs.getSchoolYear().toString()).replaceAll(
								"TERM", d.getSterm()).replaceAll("CREDIT",
								d.getCredit().toString()));
						Toolket.setCellValue(sheet, 7, 0, Toolket.getCellValue(
								sheet, 7, 0).replaceAll("PRE",
								cs.getPrerequisites().trim()));
						Toolket.setCellValue(sheet, 9, 0, Toolket.getCellValue(
								sheet, 9, 0).replaceAll("OBJ",
								cs.getObjectives().trim()));

						syllabus = cs.getSyllabuses();
						if (!syllabus.isEmpty()) {
							int index = 12;
							for (Syllabus s : syllabus) {
								Toolket.setCellValue(sheet, index, 0,
										(s == null || s.getTopic() == null ? ""
												: s.getTopic().trim()));
								Toolket
										.setCellValue(
												sheet,
												index,
												1,
												(s == null
														|| s.getContent() == null ? ""
														: s.getContent().trim()));
								Toolket.setCellValue(sheet, index, 2,
										(s == null || s.getHours() == null ? ""
												: s.getHours().trim()));
								Toolket.setCellValue(sheet, index, 3,
										(s == null || s.getWeek() == null ? ""
												: s.getWeek().trim()));
								Toolket
										.setCellValue(
												sheet,
												index++,
												4,
												(s == null
														|| s.getRemarks() == null ? ""
														: s.getRemarks().trim()));
							}
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

			File output = new File(tempDir, "CourseSyllabus.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}

	}

	/**
	 * 列印簡介
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printIntroduction(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Integer year = Integer.valueOf(cm.getNowBy("School_year"));
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), true);

		if (!clazzes.isEmpty()) {

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/CourseIntrosListPrint.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = null;
			int sheetIndex = 0;
			String departClass = null, emplName = null;
			Dtime dtime = null;
			DEmpl de = null;
			Csno csno = null;
			CourseIntroduction ci = null;
			Employee employee = null;
			List<Dtime> dtimes = null;
			List<CourseIntroduction> cis = null;

			for (Clazz clazz : clazzes) {
				departClass = clazz.getClassNo();
				if (Toolket.isDelayClass(departClass))
					continue;

				dtime = new Dtime();
				dtime.setDepartClass(departClass);
				dtime.setSterm(sterm);
				dtimes = cm.findDtimeBy(dtime, "cscode");
				if (!dtimes.isEmpty()) {

					for (Dtime d : dtimes) {
						cis = cm.getCourseIntroByDtimeOid(d.getOid(), year,
								Integer.valueOf(sterm));

						if (cis.isEmpty())
							continue;

						ci = cis.get(0);
						csno = cm.findCourseInfoByCscode(d.getCscode());
						employee = mm.findEmployeeByIdno(d.getTechid());
						if (employee == null) {
							if (StringUtils.isBlank(d.getTechid())) {
								emplName = "";
							} else {
								if (StringUtils.isBlank(d.getTechid())) {
									emplName = "";
								} else {
									de = mm.findDEmplBy(
											new DEmpl(d.getTechid())).get(0);
									emplName = de.getCname();
								}
							}
						} else
							emplName = employee.getName();

						sheet = workbook.getSheetAt(sheetIndex);
						workbook.setSheetName(sheetIndex++, departClass
								+ " "
								+ StringUtils.replace(csno.getChiName()
										.replaceAll("/", " "), "：", ""));

						Toolket.setCellValue(sheet, 2, 0, Toolket.getCellValue(
								sheet, 2, 0).replaceAll("TECH", emplName)
								.replaceAll(
										"DEPT",
										Toolket.getClassFullName(d
												.getDepartClass())));
						Toolket.setCellValue(sheet, 3, 0, Toolket.getCellValue(
								sheet, 3, 0).replaceAll("TITLE",
								csno.getChiName()));
						Toolket.setCellValue(sheet, 4, 0, Toolket.getCellValue(
								sheet, 4, 0).replaceAll(
								"ENG",
								StringUtils.isBlank(csno.getEngName()) ? ""
										: csno.getEngName()));
						Toolket.setCellValue(sheet, 5, 0, Toolket.getCellValue(
								sheet, 5, 0).replaceAll("YEAR",
								ci.getSchoolYear().toString()).replaceAll(
								"TERM", d.getSterm()).replaceAll("CREDIT",
								d.getCredit().toString()));
						Toolket.setCellValue(sheet, 7, 0, Toolket.getCellValue(
								sheet, 7, 0).replaceAll(
								"CHIINTRO",
								StringUtils.isBlank(ci.getChiIntro()) ? "" : ci
										.getChiIntro()));
						Toolket.setCellValue(sheet, 20, 0,
								Toolket.getCellValue(sheet, 20, 0)
										.replaceAll(
												"ENGINTRO",
												StringUtils.isBlank(ci
														.getEngIntro()) ? ""
														: ci.getEngIntro()));
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

			File output = new File(tempDir, "CourseIntros.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}

	}

	/**
	 * 計算跨系選課人數
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
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
				deptCode = StringUtils.substring(departClass, 3, 4);
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
										break;
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
							HIST: {
								if (!scoreHistList.isEmpty()) {
									for (ScoreHist hist : scoreHistList) {
										if (StringUtils.isNotBlank(hist
												.getStdepartClass())
												&& !Toolket
														.isLiteracyClass(hist
																.getStdepartClass())) {
											histDeptCode = StringUtils
													.substring(
															hist
																	.getStdepartClass(),
															3, 4);
											if (!deptCode
													.equalsIgnoreCase(histDeptCode)) {

												lastTermCounts++;

												Toolket
														.setCellValue(
																workbook,
																sheet,
																index,
																0,
																student
																		.getStudentNo(),
																fontSize10,
																HSSFCellStyle.ALIGN_CENTER,
																true, null);
												Toolket
														.setCellValue(
																workbook,
																sheet,
																index,
																1,
																student
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
													chiName = ((Csno) csnos
															.get(0))
															.getChiName();
												else
													chiName = "";

												Toolket
														.setCellValue(
																workbook,
																sheet,
																index++,
																4,
																chiName,
																fontSize10,
																HSSFCellStyle.ALIGN_CENTER,
																true, null);
												break HIST;
											}
										}
									}
								}
							}
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
			System.out.println("This Term: " + thisTermCounts);
			System.out.println("Last Term: " + lastTermCounts);
		}

	}

	/**
	 * 專任教師留校時間列印
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

					Toolket.setCellValue(
							sheet, 0, 1, year+ "學年度"+ term+ "學期"+ empl.getCname()	+ "老師授課時間表"+ " (分機:"
							+ (isLocationNull ? "" : StringUtils.defaultIfEmpty(empl.getLocation().getExtension(), ""))
							+ " 辦公室位置:"+ (isLocationNull ? "" : StringUtils.defaultIfEmpty(empl.getLocation().getRoomId(), "")) + ")"
					);
					
					map = cm.findCourseByTeacherTermWeekdaySched(empl.getIdno(), term.toString());

					for (int i = 0; i < 14; i++) {
						for (int j = 0; j < 7; j++) {
							content = map.get(j * 15 + i);
							if (!CollectionUtils.isEmpty(content)) {
								Toolket	.setCellValue(
										sheet, i + 2,	j + 2,	
										(String) content.get("ClassName")+"\n"+(String) content.get("chi_name")+"\n"+(String) content.get("place")
								);
							}
						}
					}

					//tsts = empl.getStayTime();
					List<TeacherStayTime> myTsts= cm.ezGetBy(
							" Select Week, Node1, Node2, Node3, Node4, Node5, Node6, Node7, Node8, Node9, Node10, " +
							"        Node11, Node12, Node13, Node14 " +
							" From TeacherStayTime "+
							" Where SchoolYear='"+ year +"'" +
							"   And SchoolTerm='"+ term +"' " +
							"   And parentOid='"+ empl.getOid() +"'");			
					
					List myTsts2 = new ArrayList();
					
					for(int i=0; i<myTsts.size();i++){
					//for (TeacherStayTime tst : tsts) {												
						myTsts2.add(myTsts.get(i));
						String s = myTsts2.get(i).toString();						
						col = Integer.parseInt(s.substring(6, 7)) + colOffset; //Week	
						//col = tst.getWeek() + colOffset;
						//if (tst.getNode1() != null && tst.getNode1() == 1) {						
						if (Integer.parseInt(s.substring(15, 16)) == 1) {        //Node1
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									2, col)))
								Toolket.setCellValue(workbook, sheet, 2, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode2() != null && tst.getNode2() == 1) {
						if (Integer.parseInt(s.substring(24, 25)) == 1) {        //Node2
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									3, col)))
								Toolket.setCellValue(workbook, sheet, 3, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode3() != null && tst.getNode3() == 1) {
						if (Integer.parseInt(s.substring(33, 34)) == 1) {        //Node3
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									4, col)))
								Toolket.setCellValue(workbook, sheet, 4, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode4() != null && tst.getNode4() == 1) {
						if (Integer.parseInt(s.substring(42, 43)) == 1) {        //Node4
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									5, col)))
								Toolket.setCellValue(workbook, sheet, 5, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode5() != null && tst.getNode5() == 1) {
						if (Integer.parseInt(s.substring(51, 52)) == 1) {        //Node5
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									6, col)))
								Toolket.setCellValue(workbook, sheet, 6, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode6() != null && tst.getNode6() == 1) {
						if (Integer.parseInt(s.substring(60, 61)) == 1) {        //Node6
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									7, col)))
								Toolket.setCellValue(workbook, sheet, 7, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode7() != null && tst.getNode7() == 1) {
						if (Integer.parseInt(s.substring(69, 70)) == 1) {        //Node7
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									8, col)))
								Toolket.setCellValue(workbook, sheet, 8, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode8() != null && tst.getNode8() == 1) {
						if (Integer.parseInt(s.substring(78, 79)) == 1) {        //Node8
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									9, col)))
								Toolket.setCellValue(workbook, sheet, 9, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode9() != null && tst.getNode9() == 1) {
						if (Integer.parseInt(s.substring(87, 88)) == 1) {        //Node9
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									10, col)))
								Toolket.setCellValue(workbook, sheet, 10, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode10() != null && tst.getNode10() == 1) {
						if (Integer.parseInt(s.substring(97, 98)) == 1) {        //Node10
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									11, col)))
								Toolket.setCellValue(workbook, sheet, 11, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode11() != null && tst.getNode11() == 1) {
						if (Integer.parseInt(s.substring(107, 108)) == 1) {        //Node11
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									12, col)))
								Toolket.setCellValue(workbook, sheet, 12, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode12() != null && tst.getNode12() == 1) {
						if (Integer.parseInt(s.substring(117, 118)) == 1) {        //Node12
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									13, col)))
								Toolket.setCellValue(workbook, sheet, 13, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode13() != null && tst.getNode13() == 1) {
						if (Integer.parseInt(s.substring(127, 128)) == 1) {        //Node13
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									14, col)))
								Toolket.setCellValue(workbook, sheet, 14, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						//if (tst.getNode14() != null && tst.getNode14() == 1) {
						if (Integer.parseInt(s.substring(137, 138)) == 1) {        //Node14
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									15, col)))
								Toolket.setCellValue(workbook, sheet, 15, col,
										"留校時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
					}				
					
					//lcs = empl.getLifeCounseling();
					List<LifeCounseling> myLcs= cm.ezGetBy(
							" Select Week, Node1, Node2, Node3, Node4, Node5, Node6, Node7, Node8, Node9, Node10, " +
							"        Node11, Node12, Node13, Node14 " +
							" From LifeCounseling Where ParentOid='"+ empl.getOid() +"'");						
					List myLcs2 = new ArrayList();	
					
					colOffset = 1;
					col = 0;
					//for (LifeCounseling lc : lcs) {
					for(int y=0; y<myLcs.size();y++){						
						myLcs2.add(myLcs.get(y));
						String st = myLcs2.get(y).toString();
						col = Integer.parseInt(st.substring(6, 7)) + colOffset;	
						//col = lc.getWeek() + colOffset;											
						//if (lc.getNode1() != null && lc.getNode1() == 1) {
						if (Integer.parseInt(st.substring(15, 16)) == 1) {        //Node1
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									2, col)))
								Toolket.setCellValue(workbook, sheet, 2, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode2() != null && lc.getNode2() == 1) {
						if (Integer.parseInt(st.substring(24, 25)) == 1) {        //Node2
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									3, col)))
								Toolket.setCellValue(workbook, sheet, 3, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode3() != null && lc.getNode3() == 1) {
						if (Integer.parseInt(st.substring(33, 34)) == 1) {        //Node3
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									4, col)))
								Toolket.setCellValue(workbook, sheet, 4, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode4() != null && lc.getNode4() == 1) {
						if (Integer.parseInt(st.substring(42, 43)) == 1) {        //Node4
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									5, col)))
								Toolket.setCellValue(workbook, sheet, 5, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode5() != null && lc.getNode5() == 1) {
						if (Integer.parseInt(st.substring(51, 52)) == 1) {        //Node5
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									6, col)))
								Toolket.setCellValue(workbook, sheet, 6, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode6() != null && lc.getNode6() == 1) {
						if (Integer.parseInt(st.substring(60, 61)) == 1) {        //Node6
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									7, col)))
								Toolket.setCellValue(workbook, sheet, 7, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode7() != null && lc.getNode7() == 1) {
						if (Integer.parseInt(st.substring(69, 70)) == 1) {        //Node7
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									8, col)))
								Toolket.setCellValue(workbook, sheet, 8, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode8() != null && lc.getNode8() == 1) {
						if (Integer.parseInt(st.substring(78, 79)) == 1) {        //Node8
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									9, col)))
								Toolket.setCellValue(workbook, sheet, 9, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode9() != null && lc.getNode9() == 1) {
						if (Integer.parseInt(st.substring(87, 88)) == 1) {        //Node9
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									10, col)))
								Toolket.setCellValue(workbook, sheet, 10, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode10() != null && lc.getNode10() == 1) {
						if (Integer.parseInt(st.substring(97, 98)) == 1) {        //Node10
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									11, col)))
								Toolket.setCellValue(workbook, sheet, 11, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode11() != null && lc.getNode11() == 1) {
						if (Integer.parseInt(st.substring(107, 108)) == 1) {        //Node11
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									12, col)))
								Toolket.setCellValue(workbook, sheet, 12, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode12() != null && lc.getNode12() == 1) {
						if (Integer.parseInt(st.substring(117, 118)) == 1) {        //Node12
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									13, col)))
								Toolket.setCellValue(workbook, sheet, 13, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode13() != null && lc.getNode13() == 1) {
						if (Integer.parseInt(st.substring(127, 128)) == 1) {        //Node13
							if (StringUtils.isEmpty(Toolket.getCellValue(sheet,
									14, col)))
								Toolket.setCellValue(workbook, sheet, 14, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode14() != null && lc.getNode14() == 1) {
						if (Integer.parseInt(st.substring(137, 138)) == 1) {        //Node14
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
	 * 證照資料列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printAbilityList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);
		if (!clazzes.isEmpty()) {

			List<Student> students = null;
			List<StdAbility> stdAbilities = null;
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			String abilityName = null;

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("證照資料列印");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 6000);
			sheet.setColumnWidth(5, 3500);
			sheet.setColumnWidth(6, 6000);
			sheet.setColumnWidth(7, 4000);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("Arial Unicode MS");

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "證照資料列印", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "學號", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "姓名", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "班級", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "項目別", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "名稱說明", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "級次說明", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 6, "單位說明", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 7, "最後更新時間", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;

			for (Clazz clazz : clazzes) {
				if (Toolket.isDelayClass(clazz.getClassNo())
						|| Toolket.isLiteracyClass(clazz.getClassNo()))
					continue;

				students = mm.findStudentsByClassNo(clazz.getClassNo());
				for (Student student : students) {
					stdAbilities = mm
							.findStudentAbilityByStudentNoAndAbilityNo(student
									.getStudentNo(), null); // null表示不管系所還是語言中心的都查出來

					for (StdAbility sa : stdAbilities) {
						Toolket.setCellValue(workbook, sheet, index, 0, student
								.getStudentNo(), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 1, student
								.getStudentName(), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 2, Toolket
								.getClassFullName(student.getDepartClass()),
								fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
								null);
						abilityName = "4".equals(sa.getAbilityNo().toString()) ? "系所認定專業證照" //根據101.02.06貞伶提供之新證照列表修正 1->4
								: ("2".equals(sa.getAbilityNo().toString()) ? "英語檢定"    //根據101.02.06貞伶提供之新證照列表修正 3->2
										: "");
						Toolket.setCellValue(workbook, sheet, index, 3,
								abilityName, fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 4, sa
								.getDescription(), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 5, sa
								.getLevelDesc(), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 6, sa
								.getDeptDesc(), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index++, 7, df
								.format(sa.getLastModified()), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
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

			File output = new File(tempDir, "AbilityList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 報部證照資料列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printStdSkillList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);

		if (!clazzes.isEmpty()) {

			List<Student> students = null;
			List<StdSkill> skills = null;
			LicenseCode code = null;
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("中華科技大學報部證照資料列印");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 2500);
			sheet.setColumnWidth(2, 2500);
			sheet.setColumnWidth(3, 4000);
			sheet.setColumnWidth(4, 6000);
			sheet.setColumnWidth(5, 3500);
			sheet.setColumnWidth(6, 2000);
			sheet.setColumnWidth(7, 3500);
			sheet.setColumnWidth(8, 2500);
			sheet.setColumnWidth(9, 2500);
			sheet.setColumnWidth(10, 2500);
			sheet.setColumnWidth(11, 4000);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("Arial Unicode MS");

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "報部證照資料", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "學年度/學期", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "姓名", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "學號", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "班級", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "證照名稱", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "證照單位", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 6, "報名費", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 7, "證書編號", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 8, "國內/國外", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 9, "級數", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 10, "證照類別", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 11, "證照生效日期", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;

			for (Clazz clazz : clazzes) {
				if (Toolket.isDelayClass(clazz.getClassNo())
						|| Toolket.isLiteracyClass(clazz.getClassNo()))
					continue;

				students = mm.findStudentsByClassNo(clazz.getClassNo());
				for (Student student : students) {

					skills = mm.findStdSkillsBy(form.getString("calendarYear"),
							form.getString("sterm"), student.getStudentNo(),
							null); // null表示都查出來

					for (StdSkill ss : skills) {
						Toolket.setCellValue(workbook, sheet, index, 0, ss
								.getSchoolYear()
								+ "/" + ss.getSchoolTerm(), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 1, student
								.getStudentName(), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 2, student
								.getStudentNo(), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 3, Toolket
								.getClassFullName(student.getDepartClass()),
								fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
								null);
						Toolket.setCellValue(workbook, sheet, index, 6, ss
								.getAmount() == null ? "" : ss.getAmount()
								.toString(), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 7,
								StringUtils.isBlank(ss.getLicenseNo()) ? ""
										: ss.getLicenseNo(), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 11, ss
								.getLicenseValidDate() == null ? "" : df
								.format(ss.getLicenseValidDate()), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);

						code = null;
						if (StringUtils.isBlank(ss.getLicenseCode()))
							code = am.findLicenseCodesBy(
									new LicenseCode(String.valueOf(ss
											.getLicenseCode()))).get(0);
						if (code != null) {
							Toolket.setCellValue(workbook, sheet, index, 4,
									code.getName(), fontSize10,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 5,
									code.getDeptName(), fontSize10,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 8,
									code.getLocale().toString(), fontSize10,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 9,
									code.getLevel(), fontSize10,
									HSSFCellStyle.ALIGN_CENTER, true, null);
							Toolket.setCellValue(workbook, sheet, index, 10,
									code.getType().toString(), fontSize10,
									HSSFCellStyle.ALIGN_CENTER, true, null);
						}

						index++;
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

			File output = new File(tempDir, "StdSkillList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 1-7學術活動列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printRc17(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Employee employee = (Employee) getUserCredential(session).getMember();
		List<Empl> empls = mm.findTeacherByUnit(employee.getUnit());
		String calendarYear = form.getString("calendarYear");

		Rcact rcact = null;
		List<Rcact> rcacts = null;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("1-7學術活動");
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 12000);
		sheet.setColumnWidth(3, 8000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 3500);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(employee.getUnit()) + "1-7學術活動",
				fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "年度", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "教師姓名", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "活動名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "主辦單位", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "開始日期", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 5, "結束日期", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		int index = 2;

		for (Empl e : empls) {

			rcact = new Rcact();
			rcact.setIdno(e.getIdno().toUpperCase());
			rcact.setSchoolYear(Short.valueOf(calendarYear));

			rcacts = mm.findRcactsBy(rcact);
			for (Rcact rc : rcacts) {
				Toolket.setCellValue(workbook, sheet, index, 0, rc
						.getSchoolYear().toString(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, e.getCname()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 2, rc.getActname()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_LEFT, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 3, rc.getSponoff()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 4, rc.getBdate()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index++, 5, rc.getEdate()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "printRc17.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 1-8承接政府部門計劃與產學案列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printRc18(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Employee employee = (Employee) getUserCredential(session).getMember();
		List<Empl> empls = mm.findTeacherByUnit(employee.getUnit());
		String calendarYear = form.getString("calendarYear");

		Rcproj rcproj = null;
		List<Rcproj> rcprojs = null;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("1-8承接政府部門計劃與產學案");
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 8000);
		sheet.setColumnWidth(3, 12000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 3000);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(employee.getUnit()) + "1-8承接政府部門計劃與產學案",
				fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "年度", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "教師姓名", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "專案案號", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "專案名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "開始日期", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 5, "結束日期", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		int index = 2;

		for (Empl e : empls) {

			rcproj = new Rcproj();
			rcproj.setIdno(e.getIdno().toUpperCase());
			rcproj.setSchoolYear(Short.valueOf(calendarYear));

			rcprojs = mm.findRcprojsBy(rcproj);
			for (Rcproj rc : rcprojs) {
				Toolket.setCellValue(workbook, sheet, index, 0, rc
						.getSchoolYear().toString(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, e.getCname()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 2, rc.getProjno()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 3, rc
						.getProjname().trim(), fontSize10,
						HSSFCellStyle.ALIGN_LEFT, true, null);
				Toolket.setCellValue(workbook, sheet, index, 4, rc.getBdate()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index++, 5, rc.getEdate()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "printRc18.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 1-9期刊論文發表列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printRc19(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Employee employee = (Employee) getUserCredential(session).getMember();
		List<Empl> empls = mm.findTeacherByUnit(employee.getUnit());
		String calendarYear = form.getString("calendarYear");

		Rcjour rcjour = null;
		List<Rcjour> rcjours = null;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("1-9期刊論文發表");
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 12000);
		sheet.setColumnWidth(3, 12000);
		sheet.setColumnWidth(4, 3000);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(employee.getUnit()) + "1-9期刊論文發表",
				fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "年度", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "教師姓名", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "論文名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "刊物名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "發表年份", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		int index = 2;

		for (Empl e : empls) {

			rcjour = new Rcjour();
			rcjour.setIdno(e.getIdno().toUpperCase());
			rcjour.setSchoolYear(Short.valueOf(calendarYear));

			rcjours = mm.findRcjoursBy(rcjour);
			for (Rcjour rc : rcjours) {
				Toolket.setCellValue(workbook, sheet, index, 0, rc
						.getSchoolYear().toString(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, e.getCname()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 2, rc.getTitle()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_LEFT, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 3, rc.getJname()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_LEFT, true,
						null);
				Toolket.setCellValue(workbook, sheet, index++, 4, rc.getPyear()
						.toString(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
						true, null);
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "printRc19.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 1-10研討會論文發表列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printRc110(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Employee employee = (Employee) getUserCredential(session).getMember();
		List<Empl> empls = mm.findTeacherByUnit(employee.getUnit());
		String calendarYear = form.getString("calendarYear");

		Rcconf rcconf = null;
		List<Rcconf> rcconfs = null;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("1-10研討會論文發表");
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 12000);
		sheet.setColumnWidth(3, 12000);
		sheet.setColumnWidth(4, 3000);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(employee.getUnit()) + "1-10研討會論文發表",
				fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "年度", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "教師姓名", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "論文名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "研討會名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "發表年份", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		int index = 2;

		for (Empl e : empls) {

			rcconf = new Rcconf();
			rcconf.setIdno(e.getIdno().toUpperCase());
			rcconf.setSchoolYear(Short.valueOf(calendarYear));

			rcconfs = mm.findRcconfsBy(rcconf);
			for (Rcconf rc : rcconfs) {
				Toolket.setCellValue(workbook, sheet, index, 0, rc
						.getSchoolYear().toString(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, e.getCname()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 2, rc.getTitle()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_LEFT, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 3, rc.getJname()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_LEFT, true,
						null);
				Toolket.setCellValue(workbook, sheet, index++, 4, rc.getPyear()
						.toString(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
						true, null);
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "printRc110.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 1-11專書(篇章)列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printRc111(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Employee employee = (Employee) getUserCredential(session).getMember();
		List<Empl> empls = mm.findTeacherByUnit(employee.getUnit());
		String calendarYear = form.getString("calendarYear");

		Rcbook rcbook = null;
		List<Rcbook> rcbooks = null;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("1-11專書或篇章");
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 12000);
		sheet.setColumnWidth(3, 8000);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(employee.getUnit()) + "1-11專書(篇章)",
				fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "年度", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "教師姓名", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "專書名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "出版社", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		int index = 2;

		for (Empl e : empls) {

			rcbook = new Rcbook();
			rcbook.setIdno(e.getIdno().toUpperCase());
			rcbook.setSchoolYear(Short.valueOf(calendarYear));

			rcbooks = mm.findRcbooksBy(rcbook);
			for (Rcbook rc : rcbooks) {
				Toolket.setCellValue(workbook, sheet, index, 0, rc
						.getSchoolYear().toString(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, e.getCname()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 2, rc.getTitle()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_LEFT, true,
						null);
				Toolket.setCellValue(workbook, sheet, index++, 3, rc
						.getPublisher().trim(), fontSize10,
						HSSFCellStyle.ALIGN_LEFT, true, null);
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "printRc111.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 1-12專利列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printRc112(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Employee employee = (Employee) getUserCredential(session).getMember();
		List<Empl> empls = mm.findTeacherByUnit(employee.getUnit());
		String calendarYear = form.getString("calendarYear");

		Rcpet rcpet = null;
		List<Rcpet> rcpets = null;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("1-12專利");
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 12000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 6000);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(employee.getUnit()) + "1-12專利",
				fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "年度", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "教師姓名", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "專利名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "開始日期", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "結束日期", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 5, "發照機關", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		int index = 2;

		for (Empl e : empls) {

			rcpet = new Rcpet();
			rcpet.setIdno(e.getIdno().toUpperCase());
			rcpet.setSchoolYear(Short.valueOf(calendarYear));

			rcpets = mm.findRcpetsBy(rcpet);
			for (Rcpet rc : rcpets) {
				Toolket.setCellValue(workbook, sheet, index, 0, rc
						.getSchoolYear().toString(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, e.getCname()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 2, rc.getTitle()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_LEFT, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 3, rc.getBdate()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 4, rc.getEdate()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index++, 5, rc.getInst()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_LEFT, true,
						null);
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "printRc112.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 1-13獲獎與榮譽列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printRc113(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Employee employee = (Employee) getUserCredential(session).getMember();
		List<Empl> empls = mm.findTeacherByUnit(employee.getUnit());
		String calendarYear = form.getString("calendarYear");

		Rchono rchono = null;
		List<Rchono> rchonos = null;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("1-13獲獎與榮譽");
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 12000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 10000);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(employee.getUnit()) + "1-13獲獎與榮譽",
				fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "年度", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "教師姓名", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "獲獎名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "頒獎國別", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "頒獎機構", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		int index = 2;

		for (Empl e : empls) {

			rchono = new Rchono();
			rchono.setIdno(e.getIdno().toUpperCase());
			rchono.setSchoolYear(Short.valueOf(calendarYear));

			rchonos = mm.findRchonosBy(rchono);
			for (Rchono rc : rchonos) {
				Toolket.setCellValue(workbook, sheet, index, 0, rc
						.getSchoolYear().toString(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, e.getCname()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 2, rc.getTitle()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_LEFT, true,
						null);
				Toolket.setCellValue(workbook, sheet, index, 3, rc.getNation()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
						null);
				Toolket.setCellValue(workbook, sheet, index++, 4, rc.getInst()
						.trim(), fontSize10, HSSFCellStyle.ALIGN_LEFT, true,
						null);
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "printRc113.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 系所學生證照列印(匯入資料驗證用)
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printDeptStdSkillList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		CourseManager manager = (CourseManager) getBean("courseManager");

		Member member = (Member) getUserCredential(session).getMember();
		Empl empl = mm.findEmplByOid(member.getOid());
		ServletContext context = request.getSession().getServletContext();

		CodeEmpl codeEmpl = new CodeEmpl();
		codeEmpl.setIdno(empl.getUnit());
		Example example4CodeEmpl = Example.create(codeEmpl).ignoreCase()
				.enableLike(MatchMode.START);
		List<CodeEmpl> codeEmpls = (List<CodeEmpl>) am.findSQLWithCriteria(
				CodeEmpl.class, example4CodeEmpl, null, null);

		List<StdSkill> skills = null;
		StdSkill skill = new StdSkill();
		skill.setAmount(null); // 避免被查詢
		Example example = Example.create(skill).ignoreCase().enableLike(
				MatchMode.ANYWHERE);
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("schoolYear"));
		orders.add(Order.asc("schoolTerm"));
		orders.add(Order.asc("studentNo"));

		Criterion deptNo = Restrictions.eq("deptNo", codeEmpls.get(0).getIdno2().trim()); // 系所代碼

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		if (StringUtils.isNotBlank(form.getString("licenseValidDateStart"))
				|| StringUtils
						.isNotBlank(form.getString("licenseValidDateEnd"))) {

			Calendar cal = Calendar.getInstance();
			Calendar cal1 = (Calendar) cal.clone();
			Calendar cal2 = (Calendar) cal.clone();

			Date from = StringUtils.isBlank(form
					.getString("licenseValidDateStart")) ? null : Toolket
					.parseNativeDate(form.getString("licenseValidDateStart"));
					// 沒結束就預設今天
			Date to = StringUtils
					.isBlank(form.getString("licenseValidDateEnd")) ? Calendar
					.getInstance().getTime() : Toolket.parseNativeDate(form
					.getString("licenseValidDateEnd"));

			cal1.setTime(from);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			cal2.setTime(to);
			cal2.set(Calendar.HOUR_OF_DAY, 23);
			cal2.set(Calendar.MINUTE, 59);
			cal2.set(Calendar.SECOND, 59);
			cal2.set(Calendar.MILLISECOND, 999);

			Criterion licenseValidDate = Restrictions.between(
					"licenseValidDate", cal1.getTime(), cal2.getTime());
			skills = (List<StdSkill>) am.findSQLWithCriteria(StdSkill.class,
					example, null, orders, deptNo, licenseValidDate);
		} else
			skills = (List<StdSkill>) am.findSQLWithCriteria(StdSkill.class,
					example, null, orders, deptNo);

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/DeptStdSkillList.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Toolket.setCellValue(sheet, 0, 0, "系所學生證照列印(匯入資料驗證用)");

		int index = 2;
		List<LicenseCode> codes = null;
		Student student = null;
		Graduate graduate = null;
		DEmpl dempl = null;
		Csno csno = null;
		DateFormat df1 = new SimpleDateFormat("yyyy/MM");
		for (StdSkill ss : skills) {
			codes = (List<LicenseCode>) am.findLicenseCodesBy(new LicenseCode(
					String.valueOf(ss.getLicenseCode())));

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
			Toolket.setCellValue(sheet, index, 4, ss.getLicense().getCode().trim());
			Toolket.setCellValue(sheet, index, 5, ss.getLicense().getDeptName().trim());
			Toolket.setCellValue(sheet, index, 6, ss.getLicense().getName().trim());
			//Toolket.setCellValue(sheet, index, 7, ss.getLicense().getLocale()
			//		.toString());
			String locale = manager.ezGetString("SELECT Locale FROM LicenseCode WHERE Code='"+ss.getLicenseCode()+"'");
			String localename="";
			if(locale.equals("1")) localename="國內";
			if(locale.equals("2")) localename="國外";
			if(locale.equals("3")) localename="大陸地區（含港澳）";
			Toolket.setCellValue(sheet, index, 7, localename);
			Toolket.setCellValue(sheet, index, 8, ss.getLicense().getLevel()
					.trim());
			//Toolket.setCellValue(sheet, index, 9, ss.getLicense().getType()
			//		.toString());
			String Type = manager.ezGetString("SELECT Type FROM LicenseCode WHERE Code='"+ss.getLicenseCode()+"'");
			String Typename="";
			if(Type.equals("1")) Typename="國際認証";
			if(Type.equals("2")) Typename="政府機關";
			if(Type.equals("3")) Typename="英文證照";
			if(Type.equals("4")) Typename="其它";
			Toolket.setCellValue(sheet, index, 9, Typename);
			Toolket.setCellValue(sheet, index, 10, ss.getAmount().toString());
			Toolket.setCellValue(sheet, index, 11, Toolket.getAmountType(ss
					.getAmountType()));
			Toolket.setCellValue(sheet, index, 12,
					ss.getAmountDate() == null ? "" : df1.format(ss
							.getAmountDate()));
			Toolket.setCellValue(sheet, index, 13, ss.getLicenseNo());
			Toolket.setCellValue(sheet, index, 14, df.format(ss
					.getLicenseValidDate()));

			if (StringUtils.isNotBlank(ss.getCscode())) {
				csno = cm.findCourseInfoByCscode(ss.getCscode());
				if (csno != null)
					Toolket.setCellValue(sheet, index, 15, csno.getChiName()
							.trim());
			} else
				Toolket.setCellValue(sheet, index, 15, "");

			if (StringUtils.isNotBlank(ss.getTechIdno())) {
				empl = mm.findEmplByIdno(ss.getTechIdno());
				if (empl != null)
					Toolket.setCellValue(sheet, index, 16, empl.getCname()
							.trim());
				else {
					dempl = mm.findDEmplByIdno(ss.getTechIdno());
					if (dempl != null)
						Toolket.setCellValue(sheet, index, 16, dempl.getCname()
								.trim());
				}
			} else
				Toolket.setCellValue(sheet, index, 16, "");

			Toolket.setCellValue(sheet, index, 17, ss.getSerialNo());
			Toolket.setCellValue(sheet, index, 18, Toolket.getCustomNo(ss
					.getCustomNo()));
			Toolket.setCellValue(sheet, index, 19, Toolket
					.getPass(ss.getPass()));
			Toolket.setCellValue(sheet, index++, 20, Toolket.getApplyType(ss
					.getApplyType()));
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "DeptStdSkillList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 系所學生英文證照列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void DeptStdSkill4EnglishList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);

		Member member = (Member) getUserCredential(session).getMember();
		Empl empl = mm.findEmplByOid(member.getOid());
		ServletContext context = request.getSession().getServletContext();

		CodeEmpl codeEmpl = new CodeEmpl();
		codeEmpl.setIdno(empl.getUnit());
		Example example4CodeEmpl = Example.create(codeEmpl).ignoreCase()
				.enableLike(MatchMode.START);
		List<CodeEmpl> codeEmpls = (List<CodeEmpl>) am.findSQLWithCriteria(
				CodeEmpl.class, example4CodeEmpl, null, null);
		List<Object> ret = null;

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		if (StringUtils.isNotBlank(form.getString("licenseValidDateStart"))
				|| StringUtils
						.isNotBlank(form.getString("licenseValidDateEnd"))) {

			String hql = "SELECT ss, s FROM StdSkill ss, Student s "
					+ "WHERE ss.deptNo = '0' AND ss.studentNo = s.studentNo "
					+ "AND s.departClass LIKE ? AND ss.licenseValidDate BETWEEN ? AND ? "
					+ "ORDER BY s.departClass";

			Calendar cal = Calendar.getInstance();
			Calendar cal1 = (Calendar) cal.clone();
			Calendar cal2 = (Calendar) cal.clone();

			Date from = StringUtils.isBlank(form
					.getString("licenseValidDateStart")) ? null : Toolket
					.parseNativeDate(form.getString("licenseValidDateStart"));
			// 沒結束就預設今天
			Date to = StringUtils
					.isBlank(form.getString("licenseValidDateEnd")) ? Calendar
					.getInstance().getTime() : Toolket.parseNativeDate(form
					.getString("licenseValidDateEnd"));

			cal1.setTime(from);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			cal2.setTime(to);
			cal2.set(Calendar.HOUR_OF_DAY, 23);
			cal2.set(Calendar.MINUTE, 59);
			cal2.set(Calendar.SECOND, 59);
			cal2.set(Calendar.MILLISECOND, 999);

			ret = (List<Object>) am.find(hql,
					new Object[] {
							"___" + codeEmpls.get(0).getIdno2().trim() + "__",
							from, to });
		} else {
			String hql = "SELECT ss, s FROM StdSkill ss, Student s "
					+ "WHERE ss.deptNo = '0' AND ss.studentNo = s.studentNo "
					+ "AND s.departClass LIKE ? ORDER BY s.departClass";
			ret = (List<Object>) am.find(hql, new Object[] { "___"
					+ codeEmpls.get(0).getIdno2().trim() + "__", });
		}

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/DeptStdSkillList.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Toolket.setCellValue(sheet, 0, 0, "系所學生英文證照列印");

		int index = 2;
		List<LicenseCode> codes = null;
		Student student = null;
		Graduate graduate = null;
		DEmpl dempl = null;
		Csno csno = null;
		DateFormat df1 = new SimpleDateFormat("yyyy/MM");
		Object[] o = null;
		StdSkill ss = null;

		if (!ret.isEmpty()) {
			for (Object obj : ret) {
				o = (Object[]) obj;
				ss = (StdSkill) o[0];
				student = (Student) o[1];

				codes = (List<LicenseCode>) am
						.findLicenseCodesBy(new LicenseCode(String.valueOf(ss
								.getLicenseCode())));
				if (!codes.isEmpty())
					ss.setLicense(codes.get(0));

				if (student == null) {
					graduate = mm.findGraduateByStudentNo(ss.getStudentNo()
							.trim());
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
						.trim());
				Toolket.setCellValue(sheet, index, 5, ss.getLicense()
						.getDeptName().trim());
				Toolket.setCellValue(sheet, index, 6, ss.getLicense().getName()
						.trim());
				Toolket.setCellValue(sheet, index, 7, ss.getLicense()
						.getLocale().toString());
				Toolket.setCellValue(sheet, index, 8, ss.getLicense()
						.getLevel().trim());
				Toolket.setCellValue(sheet, index, 9, ss.getLicense().getType()
						.toString());
				Toolket.setCellValue(sheet, index, 10, ss.getAmount()
						.toString());
				Toolket.setCellValue(sheet, index, 11, Toolket.getAmountType(ss
						.getAmountType()));
				Toolket.setCellValue(sheet, index, 12,
						ss.getAmountDate() == null ? "" : df1.format(ss
								.getAmountDate()));
				Toolket.setCellValue(sheet, index, 13, ss.getLicenseNo());
				Toolket.setCellValue(sheet, index, 14, df.format(ss
						.getLicenseValidDate()));

				if (StringUtils.isNotBlank(ss.getCscode())) {
					csno = cm.findCourseInfoByCscode(ss.getCscode());
					if (csno != null)
						Toolket.setCellValue(sheet, index, 15, csno
								.getChiName().trim());
				} else
					Toolket.setCellValue(sheet, index, 15, "");

				if (StringUtils.isNotBlank(ss.getTechIdno())) {
					empl = mm.findEmplByIdno(ss.getTechIdno());
					if (empl != null)
						Toolket.setCellValue(sheet, index, 16, empl.getCname()
								.trim());
					else {
						dempl = mm.findDEmplByIdno(ss.getTechIdno());
						if (dempl != null)
							Toolket.setCellValue(sheet, index, 16, dempl
									.getCname().trim());
					}
				} else
					Toolket.setCellValue(sheet, index, 16, "");

				Toolket.setCellValue(sheet, index, 17, ss.getSerialNo());
				Toolket.setCellValue(sheet, index, 18, Toolket.getCustomNo(ss
						.getCustomNo()));
				Toolket.setCellValue(sheet, index, 19, Toolket.getPass(ss
						.getPass()));
				Toolket.setCellValue(sheet, index++, 20, Toolket
						.getApplyType(ss.getApplyType()));
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "DeptStdSkill4EnglishList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 系所休退畢轉學生證照列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printDeptGStdSkillList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);

		Member member = (Member) getUserCredential(session).getMember();
		Empl empl = mm.findEmplByOid(member.getOid());
		ServletContext context = request.getSession().getServletContext();

		CodeEmpl codeEmpl = new CodeEmpl();
		codeEmpl.setIdno(empl.getUnit());
		Example example4CodeEmpl = Example.create(codeEmpl).ignoreCase()
				.enableLike(MatchMode.START);
		List<CodeEmpl> codeEmpls = (List<CodeEmpl>) am.findSQLWithCriteria(
				CodeEmpl.class, example4CodeEmpl, null, null);

		List<StdSkill> skills = null;
		StdSkill skill = new StdSkill();
		skill.setAmount(null); // 避免被查詢
		Example example = Example.create(skill).ignoreCase().enableLike(
				MatchMode.ANYWHERE);
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("schoolYear"));
		orders.add(Order.asc("schoolTerm"));
		orders.add(Order.asc("studentNo"));

		Criterion deptNo = Restrictions.eq("deptNo", codeEmpls.get(0)
				.getIdno2().trim()); // 系所代碼

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		if (StringUtils.isNotBlank(form.getString("licenseValidDateStart"))
				|| StringUtils
						.isNotBlank(form.getString("licenseValidDateEnd"))) {

			Calendar cal = Calendar.getInstance();
			Calendar cal1 = (Calendar) cal.clone();
			Calendar cal2 = (Calendar) cal.clone();

			Date from = StringUtils.isBlank(form
					.getString("licenseValidDateStart")) ? null : Toolket
					.parseNativeDate(form.getString("licenseValidDateStart"));
				// 沒結束就預設今天
			Date to = StringUtils
					.isBlank(form.getString("licenseValidDateEnd")) ? Calendar
					.getInstance().getTime() : Toolket.parseNativeDate(form
					.getString("licenseValidDateEnd"));

			cal1.setTime(from);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			cal2.setTime(to);
			cal2.set(Calendar.HOUR_OF_DAY, 23);
			cal2.set(Calendar.MINUTE, 59);
			cal2.set(Calendar.SECOND, 59);
			cal2.set(Calendar.MILLISECOND, 999);

			Criterion licenseValidDate = Restrictions.between(
					"licenseValidDate", cal1.getTime(), cal2.getTime());
			skills = (List<StdSkill>) am.findSQLWithCriteria(StdSkill.class,
					example, null, orders, deptNo, licenseValidDate);
		} else
			skills = (List<StdSkill>) am.findSQLWithCriteria(StdSkill.class,
					example, null, orders, deptNo);

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/DeptStdSkillList.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Toolket.setCellValue(sheet, 0, 0, "系所休退畢轉學生證照列印");

		int index = 2;
		List<LicenseCode> codes = null;
		Student student = null;
		Graduate graduate = null;
		DEmpl dempl = null;
		Csno csno = null;
		DateFormat df1 = new SimpleDateFormat("yyyy/MM");
		for (StdSkill ss : skills) {
			codes = (List<LicenseCode>) am.findLicenseCodesBy(new LicenseCode(
					String.valueOf(ss.getLicenseCode())));

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
					.trim());
			Toolket.setCellValue(sheet, index, 5, ss.getLicense().getDeptName()
					.trim());
			Toolket.setCellValue(sheet, index, 6, ss.getLicense().getName()
					.trim());
			Toolket.setCellValue(sheet, index, 7, ss.getLicense().getLocale()
					.toString());
			Toolket.setCellValue(sheet, index, 8, ss.getLicense().getLevel()
					.trim());
			Toolket.setCellValue(sheet, index, 9, ss.getLicense().getType()
					.toString());
			Toolket.setCellValue(sheet, index, 10, ss.getAmount().toString());
			Toolket.setCellValue(sheet, index, 11, Toolket.getAmountType(ss
					.getAmountType()));
			Toolket.setCellValue(sheet, index, 12,
					ss.getAmountDate() == null ? "" : df1.format(ss
							.getAmountDate()));
			Toolket.setCellValue(sheet, index, 13, ss.getLicenseNo());
			Toolket.setCellValue(sheet, index, 14, df.format(ss
					.getLicenseValidDate()));

			if (StringUtils.isNotBlank(ss.getCscode())) {
				csno = cm.findCourseInfoByCscode(ss.getCscode());
				if (csno != null)
					Toolket.setCellValue(sheet, index, 15, csno.getChiName()
							.trim());
			} else
				Toolket.setCellValue(sheet, index, 15, "");

			if (StringUtils.isNotBlank(ss.getTechIdno())) {
				empl = mm.findEmplByIdno(ss.getTechIdno());
				if (empl != null)
					Toolket.setCellValue(sheet, index, 16, empl.getCname()
							.trim());
				else {
					dempl = mm.findDEmplByIdno(ss.getTechIdno());
					if (dempl != null)
						Toolket.setCellValue(sheet, index, 16, dempl.getCname()
								.trim());
				}
			} else
				Toolket.setCellValue(sheet, index, 16, "");

			Toolket.setCellValue(sheet, index, 17, ss.getSerialNo());
			Toolket.setCellValue(sheet, index, 18, Toolket.getCustomNo(ss
					.getCustomNo()));
			Toolket.setCellValue(sheet, index, 19, Toolket
					.getPass(ss.getPass()));
			Toolket.setCellValue(sheet, index++, 20, Toolket.getApplyType(ss
					.getApplyType()));
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "DeptStdSkillList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 專業證照目次表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printDeptStdSkillList1(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);

		Member member = (Member) getUserCredential(session).getMember();
		Empl empl = mm.findEmplByOid(member.getOid());
		ServletContext context = request.getSession().getServletContext();

		CodeEmpl codeEmpl = new CodeEmpl();
		codeEmpl.setIdno(empl.getUnit());
		Example example4CodeEmpl = Example.create(codeEmpl).ignoreCase()
				.enableLike(MatchMode.START);
		List<CodeEmpl> codeEmpls = (List<CodeEmpl>) am.findSQLWithCriteria(
				CodeEmpl.class, example4CodeEmpl, null, null);

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = (Calendar) cal.clone();
		Calendar cal2 = (Calendar) cal.clone();
		if (StringUtils.isNotBlank(form.getString("licenseValidDateStart"))
				|| StringUtils
						.isNotBlank(form.getString("licenseValidDateEnd"))) {
			Date from = StringUtils.isBlank(form
					.getString("licenseValidDateStart")) ? null : Toolket
					.parseNativeDate(form.getString("licenseValidDateStart"));
			// 沒結束就預設今天
			Date to = StringUtils
					.isBlank(form.getString("licenseValidDateEnd")) ? Calendar
					.getInstance().getTime() : Toolket.parseNativeDate(form
					.getString("licenseValidDateEnd"));

			cal1.setTime(from);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			cal2.setTime(to);
			cal2.set(Calendar.HOUR_OF_DAY, 23);
			cal2.set(Calendar.MINUTE, 59);
			cal2.set(Calendar.SECOND, 59);
			cal2.set(Calendar.MILLISECOND, 999);
		}

		String hql = "SELECT count(*), s FROM StdSkill s WHERE s.deptNo = ? "
				+ "AND s.licenseValidDate BETWEEN ? AND ? "
				+ "GROUP BY s.licenseCode";
		
		List<Object> ret = (List<Object>) am.find(hql, new Object[] {
				//codeEmpls.get(0).getIdno2().trim(), cal1.getTime(),cal2.getTime() });
				empl.getUnit(), cal1.getTime(),cal2.getTime() });

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("專業證照目次表");
		sheet.setColumnWidth(0, 1500);
		sheet.setColumnWidth(1, 10000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 1800);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(empl.getUnit()) + "專業證照目次表  ("
				+ df.format(cal1.getTime()) + "~" + df.format(cal2.getTime())
				+ ")", fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F,
				null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "序號", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "證照名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "級數/分數", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "證照類別", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "發照單位", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 5, "數量", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		int index = 2, totals = 0;
		StdSkill skill = null;
		List<LicenseCode> codes = null;
		List<LicenseCode961> code961s = null;
		LicenseCode code = null;
		LicenseCode961 code961 = null;
		Object[] data = null;

		for (Object o : ret) {

			data = (Object[]) o;
			totals += (Long) data[0];
			skill = (StdSkill) data[1];

			Toolket.setCellValue(workbook, sheet, index, 0, String
					.valueOf(index - 1), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index, 5, ((Long) data[0])
					.toString(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
					null);

			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			LicenseCode c=(LicenseCode)cm.hqlGetBy("FROM LicenseCode WHERE code='"+skill.getLicenseCode()+"'").get(0);
			
			//System.out.println("FROM LicenseCode WHERE code='"+skill.getLicenseCode()+"'");
			codes = (List<LicenseCode>) am.findLicenseCodesBy(c);
			
			LicenseType type=new LicenseType();
			
			if (!codes.isEmpty()) {
				code = codes.get(0);
				
				
				Toolket.setCellValue(workbook, sheet, index, 1, code.getName(),
						fontSize10, HSSFCellStyle.ALIGN_LEFT, true, null);
				Toolket.setCellValue(workbook, sheet, index, 2,
						code.getLevel(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				try{
			
					Toolket.setCellValue(workbook, sheet, index, 3, code.getType(), fontSize10, HSSFCellStyle.ALIGN_CENTER,true, null);
					Toolket.setCellValue(workbook, sheet, index, 4, code.getDeptName(), fontSize10, HSSFCellStyle.ALIGN_LEFT, true, null);
				}catch(Exception e){
					e.printStackTrace();
					Toolket.setCellValue(workbook, sheet, index, 3, null, fontSize10, HSSFCellStyle.ALIGN_CENTER,true, null);
					Toolket.setCellValue(workbook, sheet, index, 4, null, fontSize10, HSSFCellStyle.ALIGN_LEFT, true, null);
				}
				
			} else {
				code961s = (List<LicenseCode961>) am
						.findLicenseCode961sBy(new LicenseCode961(String
								.valueOf(skill.getLicenseCode())));
				if (!code961s.isEmpty()) {
					code961 = code961s.get(0);
					Toolket.setCellValue(workbook, sheet, index, 1, code961
							.getName(), fontSize10, HSSFCellStyle.ALIGN_LEFT,
							true, null);
					Toolket.setCellValue(workbook, sheet, index, 2, code961
							.getLevel(), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 3, code961
							.getType().toString(), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 4, code961
							.getDeptName(), fontSize10,
							HSSFCellStyle.ALIGN_LEFT, true, null);
				}
			}

			index++;
		}

		sheet.addMergedRegion(new CellRangeAddress(index, index, 0, 4));
		Toolket.setCellValue(workbook, sheet, index, 0, "合計", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, index, 5, String.valueOf(totals),
				fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "DeptStdSkillList1.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 學生考取專業證照報名費補助清冊
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printDeptStdSkillList2(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);

		Member member = (Member) getUserCredential(session).getMember();
		Empl empl = mm.findEmplByOid(member.getOid());
		ServletContext context = request.getSession().getServletContext();

		CodeEmpl codeEmpl = new CodeEmpl();
		codeEmpl.setIdno(empl.getUnit());
		Example example4CodeEmpl = Example.create(codeEmpl).ignoreCase()
				.enableLike(MatchMode.START);
		List<CodeEmpl> codeEmpls = (List<CodeEmpl>) am.findSQLWithCriteria(
				CodeEmpl.class, example4CodeEmpl, null, null);

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = (Calendar) cal.clone();
		Calendar cal2 = (Calendar) cal.clone();
		if (StringUtils.isNotBlank(form.getString("licenseValidDateStart"))
				|| StringUtils
						.isNotBlank(form.getString("licenseValidDateEnd"))) {
			Date from = StringUtils.isBlank(form
					.getString("licenseValidDateStart")) ? null : Toolket
					.parseNativeDate(form.getString("licenseValidDateStart"));
			// 沒結束就預設今天
			Date to = StringUtils
					.isBlank(form.getString("licenseValidDateEnd")) ? Calendar
					.getInstance().getTime() : Toolket.parseNativeDate(form
					.getString("licenseValidDateEnd"));

			cal1.setTime(from);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			cal2.setTime(to);
			cal2.set(Calendar.HOUR_OF_DAY, 23);
			cal2.set(Calendar.MINUTE, 59);
			cal2.set(Calendar.SECOND, 59);
			cal2.set(Calendar.MILLISECOND, 999);
		}

		// Y代表已補助
		boolean flag = form.getString("amountDateType").equalsIgnoreCase("y");
		String hql = "";
		if (flag)
			hql = "FROM StdSkill s WHERE s.amountDate IS NOT NULL AND s.deptNo = ? "
					+ "AND s.licenseValidDate BETWEEN ? AND ? ORDER BY s.studentNo";
		else
			// 休退畢轉學生不需顯示
			hql = "FROM StdSkill s WHERE s.amountDate IS NULL AND s.deptNo = ? "
					+ "AND s.licenseValidDate BETWEEN ? AND ? ORDER BY s.studentNo";
		/*
		List<StdSkill> skills = (List<StdSkill>) am.find(hql, new Object[] {
				codeEmpls.get(0).getIdno2().trim(), cal1.getTime(),
				cal2.getTime() });
		*/
		codeEmpl.setIdno(empl.getUnit());
		List<StdSkill> skills = (List<StdSkill>) am.find(hql, new Object[] {
				codeEmpl.getIdno().trim(), cal1.getTime(),
				cal2.getTime() });

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/DeptStdSkillList2.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		// Header
		Toolket.setCellValue(sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(empl.getUnit()) + "學生考取專業證照報名費補助清冊 ("
				+ df.format(cal1.getTime()) + "~" + df.format(cal2.getTime())
				+ ") - " + (flag ? "已補助" : "未補助"));

		int index = 2;
		Student student = null;
		Graduate graduate = null;
		List<LicenseCode> codes = null;
		List<LicenseCode961> code961s = null;
		LicenseCode code = null;
		LicenseCode961 code961 = null;
		String checkStr1 = "□學生證書正、反影本\n■專業證照證明文件影印本\n□報名費收據影印本乙份";
		String checkStr2 = "■學生未支領特種獎學金\n□學生支領特種獎學金";

		for (StdSkill skill : skills) {

			student = mm.findStudentByNo(skill.getStudentNo());
			if (student == null) {
				graduate = mm.findGraduateByStudentNo(skill.getStudentNo());
				if (graduate != null) {
					Toolket.setCellValue(sheet, index, 13, Toolket.getStatus(
							graduate.getOccurStatus(), true));
					Toolket.setCellValue(sheet, index, 14, df.format(graduate
							.getOccurDate()));
					student = new Student();
					BeanUtils.copyProperties(graduate, student);
				}
			}

			if (student != null || graduate != null) {
				Toolket
						.setCellValue(sheet, index, 0, String
								.valueOf(index - 1));
				Toolket.setCellValue(sheet, index, 1, StringUtils
						.isBlank(student.getDepartClass()) ? "" : Toolket
						.getSchoolName(student.getDepartClass()));
				Toolket.setCellValue(sheet, index, 2, Toolket
						.getClassFullName(student.getDepartClass()));
				Toolket.setCellValue(sheet, index, 3, student.getStudentNo());
				Toolket.setCellValue(sheet, index, 4, student.getStudentName());
				Toolket.setCellValue(sheet, index, 5, student.getIdno());
				Toolket.setCellValue(sheet, index, 9, String.valueOf(skill
						.getAmount()));
				Toolket.setCellValue(sheet, index, 10, "");
				Toolket.setCellValue(sheet, index, 11, checkStr1);
				Toolket.setCellValue(sheet, index, 12, checkStr2);

				codes = (List<LicenseCode>) am
						.findLicenseCodesBy(new LicenseCode(String
								.valueOf(skill.getLicenseCode())));

				if (!codes.isEmpty()) {
					code = codes.get(0);
					Toolket.setCellValue(sheet, index, 6, code.getName());
					Toolket.setCellValue(sheet, index, 7, code.getDeptName());
					Toolket.setCellValue(sheet, index, 8, code.getLevel());
				} else {
					code961s = (List<LicenseCode961>) am
							.findLicenseCode961sBy(new LicenseCode961(String
									.valueOf(skill.getLicenseCode())));
					if (!code961s.isEmpty()) {
						code961 = code961s.get(0);
						Toolket
								.setCellValue(sheet, index, 6, code961
										.getName());
						Toolket.setCellValue(sheet, index, 7, code961
								.getDeptName());
						Toolket.setCellValue(sheet, index, 8, code961
								.getLevel());
					}
				}

				index++;
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "DeptStdSkillList2.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 系所證照類張數表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printDeptStdSkillList3(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);

		Member member = (Member) getUserCredential(session).getMember();
		Empl empl = mm.findEmplByOid(member.getOid());
		ServletContext context = request.getSession().getServletContext();

		CodeEmpl codeEmpl = new CodeEmpl();
		codeEmpl.setIdno(empl.getUnit());
		Example example4CodeEmpl = Example.create(codeEmpl).ignoreCase()
				.enableLike(MatchMode.START);
		List<CodeEmpl> codeEmpls = (List<CodeEmpl>) am.findSQLWithCriteria(
				CodeEmpl.class, example4CodeEmpl, null, null);
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = (Calendar) cal.clone();
		Calendar cal2 = (Calendar) cal.clone();
		if (StringUtils.isNotBlank(form.getString("licenseValidDateStart"))
				|| StringUtils
						.isNotBlank(form.getString("licenseValidDateEnd"))) {
			Date from = StringUtils.isBlank(form
					.getString("licenseValidDateStart")) ? null : Toolket
					.parseNativeDate(form.getString("licenseValidDateStart"));
			// 沒結束就預設今天
			Date to = StringUtils
					.isBlank(form.getString("licenseValidDateEnd")) ? Calendar
					.getInstance().getTime() : Toolket.parseNativeDate(form
					.getString("licenseValidDateEnd"));

			cal1.setTime(from);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			cal2.setTime(to);
			cal2.set(Calendar.HOUR_OF_DAY, 23);
			cal2.set(Calendar.MINUTE, 59);
			cal2.set(Calendar.SECOND, 59);
			cal2.set(Calendar.MILLISECOND, 999);
		}

		String hql = "SELECT COUNT(*) CT, ss.licenseValidDate LVD, ss.licenseCode LC, "
				+ "s.depart_class DC1, s.sex S1, gs.depart_class DC2, gs.sex S2 "
				+ "FROM StdSkill ss LEFT JOIN stmd s ON ss.studentNo = s.student_no "
				+ "LEFT JOIN Gstmd gs ON ss.studentNo = gs.student_no "
				+ "WHERE ss.deptNo = ? AND ss.licenseValidDate >= ? AND ss.licenseValidDate <= ? "
				+ "GROUP BY ss.licenseValidDate, s.depart_class, s.sex, "
				+ "gs.depart_class, gs.sex "
				+ "ORDER BY ss.licenseValidDate, s.depart_class, "
				+ "s.sex, gs.depart_class, gs.sex";	

		List<Map> ret = (List<Map>) am.findBySQL(hql, new Object[] {
				//codeEmpls.get(0).getIdno().trim(), cal1.getTime(),cal2.getTime() });	
				empl.getUnit(), cal1.getTime(),cal2.getTime() });	

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("系所證照類張數表");
		sheet.setColumnWidth(0, 1800);
		sheet.setColumnWidth(1, 2000);
		sheet.setColumnWidth(2, 2000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 8000);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 2200);
		sheet.setColumnWidth(10, 2200);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(empl.getUnit()) + "證照類張數表  ("
				+ df.format(cal1.getTime()) + "~" + df.format(cal2.getTime())
				+ ")", fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F,
				null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "序號", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "科系代碼", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "學制代碼", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "學制", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "班級", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 5, "證照代碼", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 6, "證照名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 7, "級別", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 8, "生效日期", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 9, "張數(男)", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 10, "張數(女)", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		// Toolket.setCellValue(workbook, sheet, 1, 11, "合計", fontSize10,
		// HSSFCellStyle.ALIGN_CENTER, true, null);

		int index = 2;
		boolean isGraduate = false;
		String departClass = null;
		List<LicenseCode> codes = null;
		List<LicenseCode961> code961s = null;
		List<DeptCode4Yun> yuns = null;
		LicenseCode code = null;
		LicenseCode961 code961 = null;
		DeptCode4Yun yun = null;
		Example example = null;

		for (Map data : ret) {

			Toolket.setCellValue(workbook, sheet, index, 0, String
					.valueOf(index - 1), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);

			if (data.get("DC1") != null) {
				departClass = (String) data.get("DC1");
				isGraduate = false;
			} else {
				departClass = (String) data.get("DC2");
				isGraduate = true;
			}

			yun = new DeptCode4Yun();
			yun.setClassNo(StringUtils.substring(departClass, 0, 4));
			example = Example.create(yun).ignoreCase().enableLike(
					MatchMode.START);
			yuns = (List<DeptCode4Yun>) am.findSQLWithCriteria(
					DeptCode4Yun.class, example, null, null);
			if (!yuns.isEmpty() && yuns.size() == 1) {
				yun = yuns.get(0);

				Toolket.setCellValue(workbook, sheet, index, 1, yun
						.getDeptCode(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, index, 2, yun
						.getCampusCode(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 3, yun
						.getCampusName(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
			}

			Toolket.setCellValue(workbook, sheet, index, 4, Toolket
					.getClassFullName(departClass), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index, 5, (String) data
					.get("LC"), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
					null);
									
			codes = (List<LicenseCode>) am.findLicenseCodesBy(new LicenseCode(String.valueOf((String) data.get("LC"))));
					
			if (!codes.isEmpty()) {
				code = codes.get(0);
				Toolket.setCellValue(workbook, sheet, index, 6, code.getName(),
						fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 7,
						code.getLevel(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
			} else {
				code961s = (List<LicenseCode961>) am
						.findLicenseCode961sBy(new LicenseCode961(String
								.valueOf((String) data.get("LC"))));
				if (!code961s.isEmpty()) {
					code961 = code961s.get(0);
					Toolket.setCellValue(workbook, sheet, index, 6, code961
							.getName(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
							true, null);
					Toolket.setCellValue(workbook, sheet, index, 7, code961
							.getLevel(), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
				}
			}

			Toolket.setCellValue(workbook, sheet, index, 8, df
					.format((Date) data.get("LVD")), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);

			if (!isGraduate) {
				if ("1".equals((String) data.get("S1"))) {
					Toolket.setCellValue(workbook, sheet, index, 9,
							((Long) data.get("CT")).toString(), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 10, "",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
				} else if ("2".equals((String) data.get("S1"))) {
					Toolket.setCellValue(workbook, sheet, index, 9, "",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 10,
							((Long) data.get("CT")).toString(), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
				}
			} else {
				if ("1".equals((String) data.get("S2"))) {
					Toolket.setCellValue(workbook, sheet, index, 9,
							((Long) data.get("CT")).toString(), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 10, "",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
				} else if ("2".equals((String) data.get("S2"))) {
					Toolket.setCellValue(workbook, sheet, index, 9, "",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 10,
							((Long) data.get("CT")).toString(), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
				}
			}

			index++;
			// Toolket.setCellValue(workbook, sheet, index++, 11, ((Long) m
			// .get("CT")).toString(), fontSize10,
			// HSSFCellStyle.ALIGN_CENTER, true, null);
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "DeptStdSkillList3.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 學生考取專業證照報名費補助總表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printDeptStdSkillList4(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);

		Member member = (Member) getUserCredential(session).getMember();
		Empl empl = mm.findEmplByOid(member.getOid());
		ServletContext context = request.getSession().getServletContext();

		CodeEmpl codeEmpl = new CodeEmpl();
		codeEmpl.setIdno(empl.getUnit());
		Example example4CodeEmpl = Example.create(codeEmpl).ignoreCase()
				.enableLike(MatchMode.START);
		List<CodeEmpl> codeEmpls = (List<CodeEmpl>) am.findSQLWithCriteria(
				CodeEmpl.class, example4CodeEmpl, null, null);

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = (Calendar) cal.clone();
		Calendar cal2 = (Calendar) cal.clone();
		if (StringUtils.isNotBlank(form.getString("licenseValidDateStart"))
				|| StringUtils
						.isNotBlank(form.getString("licenseValidDateEnd"))) {
			Date from = StringUtils.isBlank(form
					.getString("licenseValidDateStart")) ? null : Toolket
					.parseNativeDate(form.getString("licenseValidDateStart"));
			// 沒結束就預設今天
			Date to = StringUtils
					.isBlank(form.getString("licenseValidDateEnd")) ? Calendar
					.getInstance().getTime() : Toolket.parseNativeDate(form
					.getString("licenseValidDateEnd"));

			cal1.setTime(from);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			cal2.setTime(to);
			cal2.set(Calendar.HOUR_OF_DAY, 23);
			cal2.set(Calendar.MINUTE, 59);
			cal2.set(Calendar.SECOND, 59);
			cal2.set(Calendar.MILLISECOND, 999);
		}

		// Y代表已補助
		boolean flag = form.getString("amountDateType").equalsIgnoreCase("y");
		String hql = "";
		if (flag)
			hql = "SELECT COUNT(*), SUM(s.amount), s FROM StdSkill s "
					+ "WHERE s.amountDate IS NOT NULL AND s.deptNo = ? "
					+ "AND s.amountDate IS NOT NULL AND s.licenseValidDate BETWEEN ? AND ? "
					+ "GROUP BY s.licenseCode";
		else
			// 休退畢轉學生不需顯示
			hql = "SELECT COUNT(*), SUM(s.amount), s FROM StdSkill s "
					+ "WHERE s.amountDate IS NULL AND s.deptNo = ? "
					+ "AND s.amountDate IS NULL AND s.licenseValidDate BETWEEN ? AND ? "
					+ "GROUP BY s.licenseCode";
		
		List<Object> ret = (List<Object>) am.find(hql, new Object[] {
				codeEmpls.get(0).getIdno2().trim(), cal1.getTime(),
				cal2.getTime() });

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("學生考取專業證照報名費補助總表");
		sheet.setColumnWidth(0, 1500);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 10000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 2400);
		sheet.setColumnWidth(6, 8000);
		sheet.setColumnWidth(7, 1800);
		sheet.setColumnWidth(8, 2200);
		sheet.setColumnWidth(9, 2400);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(empl.getUnit()) + "學生考取專業證照報名費補助總表 ("
				+ df.format(cal1.getTime()) + "~" + df.format(cal2.getTime())
				+ ") - " + (flag ? "已補助" : "未補助"), fontSize12,
				HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "序號", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "證照代碼", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "證照名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "國內/國外", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "級數/分數", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 5, "證照類別", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 6, "發照單位", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 7, "張數", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 8, "補助金額", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 9, "合計", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);

		int index = 2, totals = 0, sum = 0;
		StdSkill skill = null;
		List<LicenseCode> codes = null;
		List<LicenseCode961> code961s = null;
		LicenseCode code = null;
		LicenseCode961 code961 = null;
		Object[] data = null;

		for (Object o : ret) {

			data = (Object[]) o;
			totals += (Integer) data[0];
			sum += (Integer) data[1];
			skill = (StdSkill) data[2];

			Toolket.setCellValue(workbook, sheet, index, 0, String
					.valueOf(index - 1), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index, 7, ((Integer) data[0])
					.toString(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
					null);
			Toolket.setCellValue(workbook, sheet, index, 8, skill.getAmount()
					.toString(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
					null);
			Toolket.setCellValue(workbook, sheet, index, 9, ((Integer) data[1])
					.toString(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
					null);

			codes = (List<LicenseCode>) am.findLicenseCodesBy(new LicenseCode(
					String.valueOf(skill.getLicenseCode())));

			if (!codes.isEmpty()) {
				code = codes.get(0);
				Toolket.setCellValue(workbook, sheet, index, 1, code.getCode()
						, fontSize10, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, index, 2, code.getName(),
						fontSize10, HSSFCellStyle.ALIGN_LEFT, true, null);
				Toolket.setCellValue(workbook, sheet, index, 3, code
						.getLocale().toString(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 4,
						code.getLevel(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 5, code.getType()
						.toString(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, index, 6, code
						.getDeptName(), fontSize10, HSSFCellStyle.ALIGN_LEFT,
						true, null);
			} else {
				code961s = (List<LicenseCode961>) am
						.findLicenseCode961sBy(new LicenseCode961(String
								.valueOf(skill.getLicenseCode())));
				if (!code961s.isEmpty()) {
					code961 = code961s.get(0);
					Toolket.setCellValue(workbook, sheet, index, 1, code961
							.getCode().toString(), fontSize10,
							HSSFCellStyle.ALIGN_LEFT, true, null);
					Toolket.setCellValue(workbook, sheet, index, 2, code961
							.getName(), fontSize10, HSSFCellStyle.ALIGN_LEFT,
							true, null);
					Toolket.setCellValue(workbook, sheet, index, 3, code961
							.getLocale().toString(), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 4, code961
							.getLevel(), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 5, code961
							.getType().toString(), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 6, code961
							.getDeptName(), fontSize10,
							HSSFCellStyle.ALIGN_LEFT, true, null);
				}
			}

			index++;
		}

		Toolket.setCellValue(workbook, sheet, index, 6, "合計", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, index, 7, String.valueOf(totals),
				fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, index, 9, String.valueOf(sum),
				fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "DeptStdSkillList4.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 教師輔導學生取得證照一覽表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printDeptStdSkillList5(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);

		Member member = (Member) getUserCredential(session).getMember();
		Empl empl = mm.findEmplByOid(member.getOid());
		ServletContext context = request.getSession().getServletContext();

		CodeEmpl codeEmpl = new CodeEmpl();
		codeEmpl.setIdno(empl.getUnit());
		Example example4CodeEmpl = Example.create(codeEmpl).ignoreCase()
				.enableLike(MatchMode.START);
		List<CodeEmpl> codeEmpls = (List<CodeEmpl>) am.findSQLWithCriteria(
				CodeEmpl.class, example4CodeEmpl, null, null);

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = (Calendar) cal.clone();
		Calendar cal2 = (Calendar) cal.clone();
		if (StringUtils.isNotBlank(form.getString("licenseValidDateStart"))
				|| StringUtils
						.isNotBlank(form.getString("licenseValidDateEnd"))) {
			Date from = StringUtils.isBlank(form
					.getString("licenseValidDateStart")) ? null : Toolket
					.parseNativeDate(form.getString("licenseValidDateStart"));
			// 沒結束就預設今天
			Date to = StringUtils
					.isBlank(form.getString("licenseValidDateEnd")) ? Calendar
					.getInstance().getTime() : Toolket.parseNativeDate(form
					.getString("licenseValidDateEnd"));

			cal1.setTime(from);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			cal2.setTime(to);
			cal2.set(Calendar.HOUR_OF_DAY, 23);
			cal2.set(Calendar.MINUTE, 59);
			cal2.set(Calendar.SECOND, 59);
			cal2.set(Calendar.MILLISECOND, 999);
		}

		String hql = "SELECT COUNT(*), s.techIdno, s.licenseCode FROM StdSkill s "
				+ "WHERE s.deptNo = ? AND s.techIdno IS NOT NULL "
				+ "AND s.licenseValidDate BETWEEN ? AND ? "
				+ "GROUP BY s.techIdno, s.licenseCode";

		List<Object> ret = (List<Object>) am.find(hql, new Object[] {
				codeEmpls.get(0).getIdno2().trim(), cal1.getTime(),
				cal2.getTime() });

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("教師輔導學生取得證照一覽表");
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 4000);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(empl.getUnit()) + "教師輔導學生取得證照一覽表 ("
				+ df.format(cal1.getTime()) + "~" + df.format(cal2.getTime())
				+ ")", fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F,
				null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "輔導之專任教師", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "專業證照", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "加分", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "輔導人次", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "總分", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);

		int index = 2;
		Object[] data = null;
		DEmpl dempl = null;

		for (Object o : ret) {

			data = (Object[]) o;

			sheet.addMergedRegion(new CellRangeAddress(index, index + 2, 0, 0));
			empl = mm.findEmplByIdno((String) data[1]);
			if (empl == null) {
				dempl = mm.findDEmplByIdno((String) data[1]);
				//Toolket.setCellValue(workbook, sheet, index, 0, dempl
				//		.getCname(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
				//		true, null);
			} else {
				Toolket.setCellValue(workbook, sheet, index, 0,
						empl.getCname(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
			}

			Toolket.setCellValue(workbook, sheet, index + 1, 0, "", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index + 2, 0, "", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);

			Toolket.setCellValue(workbook, sheet, index, 1, "國家性甲級",
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index + 1, 1, "國家性乙級",
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index + 2, 1, "其他證照",
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);

			Toolket.setCellValue(workbook, sheet, index, 2, "10分", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index + 1, 2, "5分",
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index + 2, 2, "2分",
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);

			Toolket.setCellValue(workbook, sheet, index, 3, "", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index + 1, 3, "", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			
			//((Integer) data[0]).toString() 換成 "" 即可
			Toolket.setCellValue(workbook, sheet, index + 2, 3, "", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			/*取代上面二句
			Toolket.setCellValue(workbook, sheet, index + 2, 3,
					((Integer) data[0]).toString(), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			*/
			
			Toolket.setCellValue(workbook, sheet, index, 4, "", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index + 1, 4, "", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index + 2, 4, "", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);

			index += 3;
		}

		index++;
		sheet.addMergedRegion(new CellRangeAddress(index, index, 0, 1));
		Toolket.setCellValue(workbook, sheet, index, 0, "註 : 最高上限20分",
				fontSize10, HSSFCellStyle.ALIGN_LEFT, false, null);
		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "DeptStdSkillList5.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 學生通過技能檢定證照人數明細表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printDeptStdSkillList6(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);

		Member member = (Member) getUserCredential(session).getMember();
		Empl empl = mm.findEmplByOid(member.getOid());
		ServletContext context = request.getSession().getServletContext();

		CodeEmpl codeEmpl = new CodeEmpl();
		codeEmpl.setIdno(empl.getUnit());
		Example example4CodeEmpl = Example.create(codeEmpl).ignoreCase()
				.enableLike(MatchMode.START);
		List<CodeEmpl> codeEmpls = (List<CodeEmpl>) am.findSQLWithCriteria(
				CodeEmpl.class, example4CodeEmpl, null, null);

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = (Calendar) cal.clone();
		Calendar cal2 = (Calendar) cal.clone();
		if (StringUtils.isNotBlank(form.getString("licenseValidDateStart"))
				|| StringUtils
						.isNotBlank(form.getString("licenseValidDateEnd"))) {
			Date from = StringUtils.isBlank(form
					.getString("licenseValidDateStart")) ? null : Toolket
					.parseNativeDate(form.getString("licenseValidDateStart"));
			// 沒結束就預設今天
			Date to = StringUtils
					.isBlank(form.getString("licenseValidDateEnd")) ? Calendar
					.getInstance().getTime() : Toolket.parseNativeDate(form
					.getString("licenseValidDateEnd"));

			cal1.setTime(from);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			cal2.setTime(to);
			cal2.set(Calendar.HOUR_OF_DAY, 23);
			cal2.set(Calendar.MINUTE, 59);
			cal2.set(Calendar.SECOND, 59);
			cal2.set(Calendar.MILLISECOND, 999);
		}

		DeptCode4Yun yun = new DeptCode4Yun();
		yun.setClassNo("___" + codeEmpls.get(0).getIdno2().trim());
		Example example = Example.create(yun).ignoreCase().enableLike(
				MatchMode.START);
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("classNo"));
		List<DeptCode4Yun> yuns = (List<DeptCode4Yun>) am.findSQLWithCriteria(
				DeptCode4Yun.class, example, null, orders);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("學生通過技能檢定證照人數明細表");
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 6000);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(empl.getUnit()) + "學生通過技能檢定證照人數明細表 ("
				+ df.format(cal1.getTime()) + "~" + df.format(cal2.getTime())
				+ ")", fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F,
				null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "序號", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "學制", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "所、系、科名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "通過人數", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "備註", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);

		int index = 2;
		String sql = "SELECT s.student_no ST1, gs.student_no ST2 "
				+ "FROM StdSkill ss LEFT JOIN stmd s ON ss.studentNo = s.student_no "
				+ "LEFT JOIN Gstmd gs ON ss.studentNo = gs.student_no "
				+ "WHERE ss.deptNo = ? AND ss.licenseValidDate BETWEEN ? AND ? "
				+ "AND (s.depart_class LIKE ? OR gs.depart_class LIKE ?)";
		String classNo = null;
		List<Map> ret = null;
		Set<String> set = new HashSet<String>();

		for (DeptCode4Yun y : yuns) {
			classNo = y.getClassNo() + "%";
			ret = (List<Map>) am.findBySQL(sql, new Object[] {
					codeEmpls.get(0).getIdno2().trim(), cal1.getTime(),
					cal2.getTime(), classNo, classNo });

			set = new HashSet<String>();
			if (!ret.isEmpty()) {
				for (Map o : ret) {
					if (o.get("ST1") != null)
						set.add((String) o.get("ST1"));
					else if (o.get("ST2") != null)
						set.add((String) o.get("ST2"));
				}

				if (!set.isEmpty()) {
					Toolket.setCellValue(workbook, sheet, index, 0, String
							.valueOf(index - 1), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 1, y
							.getCampusName(), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 2, y
							.getDeptName(), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 3, String
							.valueOf(set.size()), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index++, 4, "",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
				}
			}
		}

		index++;
		Toolket.setCellValue(workbook, sheet, index, 4, "以上統計包括英文證照",
				fontSize10, HSSFCellStyle.ALIGN_RIGHT, false, null);

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "DeptStdSkillList6.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 962至今報部證照代碼對照表列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printLicenseCodes(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Example example = Example.create(new LicenseCode());
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("code"));
		List<LicenseCode> codes = (List<LicenseCode>) am.findSQLWithCriteria(
				LicenseCode.class, example, null, orders, 20000);

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/LicenseCodes.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		int index = 2;

		for (LicenseCode code : codes) {
			Toolket.setCellValue(sheet, index, 0, code.getCode().toString());
			Toolket.setCellValue(sheet, index, 1, code.getName());
			Toolket.setCellValue(sheet, index, 2, code.getLocale().toString());
			Toolket.setCellValue(sheet, index, 3, code.getLevel());
			Toolket.setCellValue(sheet, index, 4, code.getType().toString());
			Toolket.setCellValue(sheet, index++, 5, code.getDeptName());
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "LicenseCodes.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();

	}

	/**
	 * 961以前(含)報部證照代碼對照表列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printLicenseCodes961(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Example example = Example.create(new LicenseCode961());
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("code"));
		List<LicenseCode961> codes961 = (List<LicenseCode961>) am
				.findSQLWithCriteria(LicenseCode961.class, example, null,
						orders);

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/LicenseCodes.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		int index = 2;

		for (LicenseCode961 code961 : codes961) {
			Toolket.setCellValue(sheet, index, 0, code961.getCode().toString());
			Toolket.setCellValue(sheet, index, 1, code961.getName());
			Toolket.setCellValue(sheet, index, 2, code961.getLocale()
					.toString());
			Toolket.setCellValue(sheet, index, 3, code961.getLevel());
			Toolket.setCellValue(sheet, index, 4, code961.getType().toString());
			Toolket.setCellValue(sheet, index++, 5, code961.getDeptName());
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "LicenseCodes961.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 科目代碼表列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printCscodeList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Example example = Example.create(new Csno());
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("cscode"));
		List<Csno> csnos = (List<Csno>) am.findSQLWithCriteria(Csno.class,
				example, null, orders);

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/Csnos.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		int index = 2;

		for (Csno csno : csnos) {
			Toolket.setCellValue(sheet, index, 0, csno.getCscode().trim()
					.toUpperCase());
			Toolket.setCellValue(sheet, index, 1, csno.getChiName().trim());
			Toolket.setCellValue(sheet, index++, 2, StringUtils.defaultIfEmpty(
					csno.getEngName(), "").trim());
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "Csnos.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 雲科大學制科系代碼表列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printDeptCode4Yun(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Example example = Example.create(new DeptCode4Yun());
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("classNo"));
		List<DeptCode4Yun> deptCode4Yuns = (List<DeptCode4Yun>) am
				.findSQLWithCriteria(DeptCode4Yun.class, example, null, orders);

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/DeptCode4Yun.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		int index = 2;

		for (DeptCode4Yun code : deptCode4Yuns) {
			Toolket.setCellValue(sheet, index, 0, code.getClassNo());
			Toolket.setCellValue(sheet, index, 1, code.getDeptCode());
			Toolket.setCellValue(sheet, index, 2, code.getDeptName());
			Toolket.setCellValue(sheet, index, 3, code.getCampusCode());
			Toolket.setCellValue(sheet, index, 4, code.getCampusName());
			Toolket.setCellValue(sheet, index++, 5, code.getGradeYear()
					.toString());
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "DeptCode4Yun.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
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
		return departClass.length() == 6 ? departClass : campusInCharge2
				+ schoolInCharge2 + deptInCharge2;
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("preview", "printReport");
		return map;
	}

}
