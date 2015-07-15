package tw.edu.chit.struts.action.course;

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
import org.springframework.util.CollectionUtils;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.CourseSyllabus;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeTeacher;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.LicenseCode;
import tw.edu.chit.model.LifeCounseling;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.StdSkill;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.Syllabus;
import tw.edu.chit.model.TeacherStayTime;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
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
				.getYearArray(cm.getSchoolYear().toString()));
		setContentPage(session, "course/ReportPrint.jsp");
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
		aForm.set("year", new String[] { request.getParameter("year") });
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
		} else if ("SyllabusCheck".equals(printOpt)) {
			// 綱要查核
			printSyllabusCheck(mapping, aForm, request, response, sterm);
		} else if ("IntroCheck".equals(printOpt)) {
			// 簡介查核
			printIntroCheck(mapping, aForm, request, response, sterm);
		} else if ("StdSkillList".equals(printOpt)) {
			// 證照資料列印
			printStdSkillList(mapping, aForm, request, response, sterm);
		} else if ("CourseDataPrint".equals(printOpt)) {
			// 課程填報
			printCourseDataPrint(mapping, aForm, request, response, sterm);
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
		Integer year = cm.getSchoolYear();
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
		Integer year = cm.getSchoolYear();
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
		String thisYear = cm.getSchoolYear().toString();
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
	private void printStayTimePrint(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Integer year = cm.getSchoolYear();
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
					List<TeacherStayTime> myTsts= cm.ezGetBy(
							" Select Week, Node1, Node2, Node3, Node4, Node5, Node6, Node7, Node8, Node9, Node10, " +
							"        Node11, Node12, Node13, Node14 " +
							" From TeacherStayTime "+
							" Where SchoolYear='"+ year +"'" +
							"   And SchoolTerm='"+ term +"' " +
							"   And parentOid='"+ empl.getOid() +"'");			
					
					List myTsts2 = new ArrayList();			
					
					//int colOffset = 1, col = 0;
					for(int i=0; i<myTsts.size();i++){
					//for (TeacherStayTime tst : tsts) {				
						myTsts2.add(myTsts.get(i));
						String s = myTsts2.get(i).toString();
						col = Integer.parseInt(s.substring(6, 7)) + colOffset; //Week				
						//if (tst.getNode1() != null && tst.getNode1() == 1) {				
						if (Integer.parseInt(s.substring(15, 16)) == 1) {        //Node1
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 2, col)))
								Toolket.setCellValue(workbook, sheet, 2, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (Integer.parseInt(s.substring(24, 25)) == 1) {        //Node2
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 3, col)))
								Toolket.setCellValue(workbook, sheet, 3, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);					
						}
						if (Integer.parseInt(s.substring(33, 34)) == 1) {        //Node3
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 4, col)))
								Toolket.setCellValue(workbook, sheet, 4, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (Integer.parseInt(s.substring(42, 43)) == 1) {        //Node4
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 5, col)))
								Toolket.setCellValue(workbook, sheet, 5, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (Integer.parseInt(s.substring(51, 52)) == 1) {        //Node5
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 6, col)))
								Toolket.setCellValue(workbook, sheet, 6, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (Integer.parseInt(s.substring(60, 61)) == 1) {        //Node6
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 7, col)))
								Toolket.setCellValue(workbook, sheet, 7, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (Integer.parseInt(s.substring(69, 70)) == 1) {        //Node7
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 8, col)))
								Toolket.setCellValue(workbook, sheet, 8, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (Integer.parseInt(s.substring(78, 79)) == 1) {        //Node8
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 9, col)))
								Toolket.setCellValue(workbook, sheet, 9, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (Integer.parseInt(s.substring(87, 88)) == 1) {        //Node9
							if (StringUtils.isEmpty(Toolket
									.getCellValue(sheet, 10, col)))
								Toolket.setCellValue(workbook, sheet, 10, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (Integer.parseInt(s.substring(97, 98)) == 1) {        //Node10
							if (StringUtils.isEmpty(Toolket
									.getCellValue(sheet, 11, col)))
								Toolket.setCellValue(workbook, sheet, 11, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (Integer.parseInt(s.substring(107, 108)) == 1) {        //Node11
							if (StringUtils.isEmpty(Toolket
									.getCellValue(sheet, 12, col)))
								Toolket.setCellValue(workbook, sheet, 12, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (Integer.parseInt(s.substring(117, 118)) == 1) {        //Node12
							if (StringUtils.isEmpty(Toolket
									.getCellValue(sheet, 13, col)))
								Toolket.setCellValue(workbook, sheet, 13, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (Integer.parseInt(s.substring(127, 128)) == 1) {        //Node13
							if (StringUtils.isEmpty(Toolket
									.getCellValue(sheet, 14, col)))
								Toolket.setCellValue(workbook, sheet, 14, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
						if (Integer.parseInt(s.substring(137, 138)) == 1) {        //Node14
							if (StringUtils.isEmpty(Toolket
									.getCellValue(sheet, 15, col)))
								Toolket.setCellValue(workbook, sheet, 15, col, "留校時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForStayTime);
						}
					}

					//List<LifeCounseling> lcs = empl.getLifeCounseling();
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
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 2, col)))
								Toolket.setCellValue(workbook, sheet, 2, col, "生活輔導時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode2() != null && lc.getNode2() == 1) {
						if (Integer.parseInt(st.substring(24, 25)) == 1) {        //Node2
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 3, col)))
								Toolket.setCellValue(workbook, sheet, 3, col, "生活輔導時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode3() != null && lc.getNode3() == 1) {
						if (Integer.parseInt(st.substring(33, 34)) == 1) {        //Node3
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 4, col)))
								Toolket.setCellValue(workbook, sheet, 4, col, "生活輔導時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode4() != null && lc.getNode4() == 1) {
						if (Integer.parseInt(st.substring(42, 43)) == 1) {        //Node4
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 5, col)))
								Toolket.setCellValue(workbook, sheet, 5, col, "生活輔導時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode5() != null && lc.getNode5() == 1) {
						if (Integer.parseInt(st.substring(51, 52)) == 1) {        //Node5
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 6, col)))
								Toolket.setCellValue(workbook, sheet, 6, col, "生活輔導時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode6() != null && lc.getNode6() == 1) {
						if (Integer.parseInt(st.substring(60, 61)) == 1) {        //Node6
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 7, col)))
								Toolket.setCellValue(workbook, sheet, 7, col, "生活輔導時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode7() != null && lc.getNode7() == 1) {
						if (Integer.parseInt(st.substring(69, 70)) == 1) {        //Node7
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 8, col)))
								Toolket.setCellValue(workbook, sheet, 8, col, "生活輔導時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);					
						}
						//if (lc.getNode8() != null && lc.getNode8() == 1) {
						if (Integer.parseInt(st.substring(78, 79)) == 1) {        //Node8
							if (StringUtils
									.isEmpty(Toolket.getCellValue(sheet, 9, col)))
								Toolket.setCellValue(workbook, sheet, 9, col, "生活輔導時間",
										fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode9() != null && lc.getNode9() == 1) {
						if (Integer.parseInt(st.substring(87, 88)) == 1) {        //Node9
							if (StringUtils.isEmpty(Toolket
									.getCellValue(sheet, 10, col)))
								Toolket.setCellValue(workbook, sheet, 10, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode10() != null && lc.getNode10() == 1) {
						if (Integer.parseInt(st.substring(97, 98)) == 1) {        //Node10
							if (StringUtils.isEmpty(Toolket
									.getCellValue(sheet, 11, col)))
								Toolket.setCellValue(workbook, sheet, 11, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode11() != null && lc.getNode11() == 1) {
						if (Integer.parseInt(st.substring(107, 108)) == 1) {        //Node11
							if (StringUtils.isEmpty(Toolket
									.getCellValue(sheet, 12, col)))
								Toolket.setCellValue(workbook, sheet, 12, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode12() != null && lc.getNode12() == 1) {
						if (Integer.parseInt(st.substring(117, 118)) == 1) {        //Node12
							if (StringUtils.isEmpty(Toolket
									.getCellValue(sheet, 13, col)))
								Toolket.setCellValue(workbook, sheet, 13, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode13() != null && lc.getNode13() == 1) {
						if (Integer.parseInt(st.substring(127, 128)) == 1) {        //Node13
							if (StringUtils.isEmpty(Toolket
									.getCellValue(sheet, 14, col)))
								Toolket.setCellValue(workbook, sheet, 14, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
						//if (lc.getNode14() != null && lc.getNode14() == 1) {
						if (Integer.parseInt(st.substring(137, 138)) == 1) {        //Node14
							if (StringUtils.isEmpty(Toolket
									.getCellValue(sheet, 15, col)))
								Toolket.setCellValue(workbook, sheet, 15, col,
										"生活輔導時間", fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										colorForLifeCounseling);
						}
					}
				}
			}
					//===================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
					/*
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

				}  */
            //=================================>>>>>>>>>>>>>>>>>>>>>

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
	 * 綱要查核
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printSyllabusCheck(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		// 要包含通識班級
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), true);
		String hql = "SELECT COUNT(*) FROM Dtime d WHERE d.techid != '' AND d.techid IS NOT NULL "
				+ "AND d.sterm = ? AND d.departClass LIKE ?";
		List<Object> count = (List<Object>) am.find(hql, new Object[] { sterm,
				processClassInfo(form) + "%" });

		if (!clazzes.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("綱要查核");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 5500);
			sheet.setColumnWidth(2, 3000);
			sheet.setColumnWidth(3, 8000);
			sheet.setColumnWidth(4, 2000);
			sheet.setColumnWidth(5, 5000);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, year + "學年度第" + sterm
					+ "學期綱要未上傳查核", fontSize16, HSSFCellStyle.ALIGN_CENTER,
					false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "班級代碼", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "班級名稱", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "科目代碼", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "科目名稱", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "學分數", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "姓名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;
			List<Object> maps = null;
			Dtime dtime = null;
			Csno csno = null;
			CourseSyllabus cs = null, target = null;
			Empl empl = null;
			String[] excluded = { "50000", "T0001", "T0002" };
			target = new CourseSyllabus();
			target.setSchoolYear(Integer.parseInt(year));
			target.setSchoolTerm(Integer.parseInt(sterm));

			for (Clazz clazz : clazzes) {
				if (Toolket.isDelayClass(clazz.getClassNo()))
					continue;

				//maps = cm.findDtimeCsnoBy(new Dtime(clazz.getClassNo(), sterm),
						//"cscode");

				if (maps != null) {

					for (Object o : maps) {
						dtime = (Dtime) ((Object[]) o)[0];
						csno = (Csno) ((Object[]) o)[1];

						// 部分專題與校務實習不用上傳
						if (!ArrayUtils.contains(excluded, csno.getCscode())
								&& !ArrayUtils.contains(
										IConstants.COURSE_SYLLABUS_INTRO, dtime
												.getCscode())
								&& StringUtils.isNotBlank(dtime.getTechid()
										.trim())) {

							target.setDepartClass(clazz.getClassNo());
							target.setCscode(csno.getCscode());
							cs = cm.findCourseSyllabusBy1(target);
							if (cs == null) {

								empl = mm.findEmplByIdno(dtime.getTechid());
								Toolket.setCellValue(workbook, sheet, index, 0,
										clazz.getClassNo(), fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true, null);
								Toolket.setCellValue(workbook, sheet, index, 1,
										Toolket.getClassFullName(clazz
												.getClassNo()), fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true, null);
								Toolket.setCellValue(workbook, sheet, index, 2,
										csno.getCscode(), fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true, null);
								Toolket.setCellValue(workbook, sheet, index, 3,
										csno.getChiName().trim(), fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true, null);
								Toolket.setCellValue(workbook, sheet, index, 4,
										dtime.getCredit().toString(),
										fontSize12, HSSFCellStyle.ALIGN_CENTER,
										true, null);
								Toolket.setCellValue(workbook, sheet, index++,
										5, (empl == null ? "" : empl.getCname()
												.trim()), fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true, null);
							} else {
								cs = null;
							}
						}

						dtime = null;
						csno = null;
					}
				}
			}

			int xx = index - 2; // 未上傳數量
			int yy = (Integer) count.get(0); // 開課數量
			index++;

			sheet.addMergedRegion(new CellRangeAddress(index, index, 3, 5));
			Toolket.setCellValue(workbook, sheet, index++, 3, "未上傳比例 : " + xx
					+ " / " + yy + " = " + ((float) xx / (float) yy),
					fontSize16, HSSFCellStyle.ALIGN_RIGHT, false, null);

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
	 * 簡介查核
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printIntroCheck(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		// 要包含通識班級
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), true);

		if (!clazzes.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("簡介查核");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 3000);
			sheet.setColumnWidth(3, 8000);
			sheet.setColumnWidth(4, 2000);
			sheet.setColumnWidth(5, 5000);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, year + "學年度第" + sterm
					+ "學期簡介未上傳查核", fontSize16, HSSFCellStyle.ALIGN_CENTER,
					false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "班級代碼", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "班級名稱", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "科目代碼", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "科目名稱", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "學分數", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "教師姓名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;
			List<Object> maps = null;
			Dtime dtime = null;
			Csno csno = null;
			CourseIntroduction ci = null;
			Empl empl = null;
			String[] excluded = { "50000", "T0001", "T0002" };

			for (Clazz clazz : clazzes) {
				if (Toolket.isDelayClass(clazz.getClassNo()))
					continue;

				//maps = cm.findDtimeCsnoBy(new Dtime(clazz.getClassNo(), sterm),
						//"cscode");

				if (maps != null) {

					for (Object o : maps) {
						dtime = (Dtime) ((Object[]) o)[0];
						csno = (Csno) ((Object[]) o)[1];
						
						// 部分專題與校務實習不用上傳
						if (!ArrayUtils.contains(excluded, csno.getCscode())
								&& !ArrayUtils.contains(
										IConstants.COURSE_SYLLABUS_INTRO, dtime
												.getCscode())
								&& StringUtils.isNotBlank(dtime.getTechid()
										.trim())) {

							ci = cm.getCourseIntrosByDtimeOid(dtime.getOid(),
									Integer.parseInt(year), Integer
											.parseInt(sterm));
							if (ci == null) {

								empl = mm.findEmplByIdno(dtime.getTechid());
								Toolket.setCellValue(workbook, sheet, index, 0,
										clazz.getClassNo(), null,
										HSSFCellStyle.ALIGN_CENTER, true, null);
								Toolket.setCellValue(workbook, sheet, index, 1,
										Toolket.getClassFullName(clazz
												.getClassNo()), null,
										HSSFCellStyle.ALIGN_CENTER, true, null);
								Toolket.setCellValue(workbook, sheet, index, 2,
										csno.getCscode(), null,
										HSSFCellStyle.ALIGN_CENTER, true, null);
								Toolket.setCellValue(workbook, sheet, index, 3,
										csno.getChiName().trim(), null,
										HSSFCellStyle.ALIGN_CENTER, true, null);
								Toolket.setCellValue(workbook, sheet, index, 4,
										dtime.getCredit().toString(), null,
										HSSFCellStyle.ALIGN_CENTER, true, null);
								Toolket.setCellValue(workbook, sheet, index++,
										5, (empl == null ? "" : empl.getCname()
												.trim()), null,
										HSSFCellStyle.ALIGN_CENTER, true, null);
							} else {
								ci = null;
							}
						}

						dtime = null;
						csno = null;
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
			Toolket.setCellValue(sheet, index, 4, ss.getLicense().getCode().toString());
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
	 * 課程填報列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printCourseDataPrint(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Integer year = cm.getSchoolYear();
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), true);

		if (!clazzes.isEmpty()) {

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/CourseDataPrint.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFSheet sheet = workbook.getSheetAt(0);
			Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度技職課程填報");
			Toolket.setCellValue(sheet, 0, 30, "下載日期："
					+ new SimpleDateFormat("yyyy/MM/dd").format(new Date()));

			int index = 2, boys = 0, girls = 0;
			boolean flag = false;
			String departClass = null, optValue = null;
			Dtime dtime = null;
			Empl empl = null;
			Csno csno = null;
			List<Object> objs = null;
			List<DtimeTeacher> dtimeTeachers = null;
			List<String> csGroups = null;
			List<Student> stmds = null;
			Object[] data = null;
			String[] no = { "50000", "T0001", "T0002" };
			String[] no1 = { "一", "二", "上", "下", "(一)", "(二)", "(三)", "(四)",
					"(上)", "(下)" };
			String[] no2 = { "體育三", "體育四" };
			String[] english = { "S0351", "S0352", "S0353", "S0391", "T0090",
					"T0350", "T0351", "T0352", "T0360", "T0B20", "TD890",
					"TG321", "TH821", "THQ20", "TP3W0", "TQ090" };
			String[] japan = { "T0161" };
			StringBuffer buffer = new StringBuffer();
			String hql = "SELECT d, cs FROM Dtime d, Csno cs "
					+ "WHERE d.cscode = cs.cscode "
					+ "AND d.sterm = ? AND d.departClass = ?";
			String hql1 = "SELECT DISTINCT cg.cname FROM CsGroup cg, CsGroupSet cs "
					+ "WHERE cg.oid = cs.groupOid AND cs.cscode = ? ORDER BY cg.oid";

			for (Clazz clazz : clazzes) {
				departClass = clazz.getClassNo();
				if (Toolket.isDelayClass(departClass))
					continue;

				objs = am.find(hql, new Object[] { sterm, departClass }, -1);
				for (Object o : objs) {
					data = (Object[]) o;
					dtime = (Dtime) data[0];
					csno = (Csno) data[1];

					stmds = cm.findSeldStudentByDtimeOid(dtime.getOid());
					if (!ArrayUtils.contains(no, dtime.getCscode())
							&& !stmds.isEmpty()) {
						csGroups = am.find(hql1, new Object[] { dtime
								.getCscode() }, -1);
						if (csGroups.isEmpty())
							Toolket.setCellValue(sheet, index, 0, "");
						else {
							buffer = new StringBuffer();
							for (String cg : csGroups)
								buffer.append(cg).append(",");

							Toolket.setCellValue(sheet, index, 0, StringUtils
									.substring(buffer.toString(), 0, buffer
											.toString().length() - 1)); // 學程名稱
						}

						Toolket.setCellValue(sheet, index, 1, ""); // 科目類別碼
						Toolket
								.setCellValue(sheet, index, 2,
										"D".equalsIgnoreCase(clazz
												.getSchoolType()) ? "0" : ("N"
												.equalsIgnoreCase(clazz
														.getSchoolType()) ? "1"
												: "2")); // 部別碼
						Toolket.setCellValue(sheet, index, 3, Toolket
								.getSchoolNoBy(clazz)); // 學制碼/學程碼
						Toolket.setCellValue(sheet, index, 4, StringUtils
								.abbreviate(csno.getChiName(), 64)); // 課程名稱
						Toolket.setCellValue(sheet, index, 5, StringUtils
								.abbreviate(Toolket.getDepartName(departClass),
										25)); // 科系
						Toolket.setCellValue(sheet, index, 6, ""); // 組別
						Toolket.setCellValue(sheet, index, 7, "1"); // 校部訂碼
						flag = false;
						for (String noValue : no1) {
							if (csno.getChiName().endsWith(noValue)) {
								flag = true;
								break;
							}
						}

						if (!flag) {
							for (String noValue : no2) {
								if (StringUtils.contains(csno.getChiName(),
										noValue)) {
									flag = true;
									break;
								}
							}
						}
						Toolket.setCellValue(sheet, index, 8, flag ? "1" : "0"); // 半全年碼

						optValue = "1".equals(dtime.getOpt()) ? "0" : ("2"
								.equals(dtime.getOpt()) ? "1" : "3");
						Toolket.setCellValue(sheet, index, 9, optValue); // 必選修碼
						Toolket.setCellValue(sheet, index, 10, dtime
								.getCredit().toString()); // 學分數
						Toolket.setCellValue(sheet, index, 11, dtime.getThour()
								.toString()); // 每週開課時數
						Toolket.setCellValue(sheet, index, 12, ""); // 每週實習時數
						Toolket
								.setCellValue(sheet, index, 13, clazz
										.getGrade()); // 年級
						Toolket.setCellValue(sheet, index, 14, clazz
								.getClassName()); // 班級
						Toolket.setCellValue(sheet, index, 15,
								"http://www.cust.edu.tw/www/info/intro_en.php?coursenum="
										+ dtime.getOid()); // 課程摘要
						Toolket.setCellValue(sheet, index, 16,
								"http://www.cust.edu.tw/www/info/intro_obj.php?coursenum="
										+ dtime.getOid()); // 課程大綱
						Toolket.setCellValue(sheet, index, 17, ""); // 課程連結

						buffer = new StringBuffer();
						empl = mm.findEmplByIdno(dtime.getTechid());
						if (empl != null)
							buffer.append(empl.getCname()).append(",");

						dtimeTeachers = cm.getDtimeTeacherBy(dtime.getOid()
								.toString());
						for (DtimeTeacher dt : dtimeTeachers)
							buffer.append(dt.getChiName2()).append(",");

						Toolket.setCellValue(sheet, index, 18, StringUtils
								.substring(buffer.toString(), 0, buffer
										.toString().length() - 1)); // 開課老師
						Toolket.setCellValue(sheet, index, 19, ""); // 備註
						Toolket.setCellValue(sheet, index, 20, ""); // 填表人

						if (ArrayUtils.contains(english, csno.getCscode())) {
							Toolket.setCellValue(sheet, index, 21, "英語"); // 授課語言1
							Toolket.setCellValue(sheet, index, 22, "國語"); // 授課語言2
						} else if (ArrayUtils.contains(japan, csno.getCscode())) {
							Toolket.setCellValue(sheet, index, 21, "日語"); // 授課語言1
							Toolket.setCellValue(sheet, index, 22, "國語"); // 授課語言2
						} else {
							Toolket.setCellValue(sheet, index, 21, "國語"); // 授課語言1
							Toolket.setCellValue(sheet, index, 22, ""); // 授課語言2
						}

						Toolket.setCellValue(sheet, index, 23, ""); // 報部文號
						Toolket.setCellValue(sheet, index, 24, ""); // 證照1
						Toolket.setCellValue(sheet, index, 25, ""); // 證照2
						Toolket.setCellValue(sheet, index, 26, StringUtils
								.abbreviate(csno.getEngName(), 255)); // 英文課程名稱
						Toolket.setCellValue(sheet, index, 27, "0"); // 全英語教學碼
						Toolket
								.setCellValue(sheet, index, 28,
										StringUtils.contains(csno.getChiName(),
												"實習") ? "2" : Toolket
												.getCourseStyleBy(dtime
														.getElearning())); // 教學型態碼
						Toolket.setCellValue(sheet, index, 29,
								empl == null ? "" : Toolket
										.getTeacherSourceBy(empl.getUnit())); // 師資來源碼

						boys = 0;
						girls = 0;
						for (Student student : stmds) {
							if ("1".equals(student.getSex()))
								boys++;
							else if ("2".equals(student.getSex()))
								girls++;
						}
						Toolket.setCellValue(sheet, index, 30, String
								.valueOf(boys)); // 男學生修課人數
						Toolket.setCellValue(sheet, index++, 31, String
								.valueOf(girls)); // 女學生修課人數
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

			File output = new File(tempDir, "CourseDataPrint.xls");
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
