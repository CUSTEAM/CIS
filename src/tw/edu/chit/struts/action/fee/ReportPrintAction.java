package tw.edu.chit.struts.action.fee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import tw.edu.chit.model.BankFeePay;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.FeePay;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class ReportPrintAction extends BaseLookupDispatchAction {

	static String[] master = { "1G", "8G", "CG", "DG", "FG", "HG" }; // 碩士
	static String[] type1 = { "12", "22", "32", "92", "A2", "B2" }; // 二專
	static String[] type2 = { "15" }; // 五專
	static String[] type3 = {}; // 大學
	static String[] type4 = { "54", "64", "A4", "B4" }; // 四技
	static String[] type5 = { "42", "52", "72", "82", "B3", "C2", "D2", "F2",
			"H2" }; // 二技
	static String[][] all = { master, type1, type2, type3, type4, type5 };

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
		session.setMaxInactiveInterval(-1);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", sterm);
		aForm.set("year", tw.edu.chit.struts.action.score.ReportPrintAction
				.getYearArray(Toolket.getNextYearTerm().get(
						IConstants.PARAMETER_SCHOOL_YEAR).toString()));
		setContentPage(session, "fee/ReportPrint.jsp");
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
		aForm.set("syear", request.getParameter("year"));
		aForm.set("sterm", request.getParameter("st"));
		aForm.set("printOpt", request.getParameter("p"));
		aForm.set("campusInCharge2", request.getParameter("c"));
		aForm.set("schoolInCharge2", request.getParameter("s"));
		aForm.set("deptInCharge2", request.getParameter("d"));
		aForm.set("classInCharge2", request.getParameter("cl"));
		aForm.set("printInterClass", request.getParameter("printInterClass"));
		aForm.set("endDate", request.getParameter("ed"));
		aForm.set("campusCode", request.getParameter("cc"));
		aForm.set("schoolType", request.getParameter("sch"));
		aForm.set("feeKind", request.getParameter("fk"));
		aForm.set("emailTo", request.getParameter("eto"));
		String printOpt = aForm.getString("printOpt");
		String sterm = (String) aForm.get("sterm");

		if ("StmdFeePayList".equals(printOpt)) {
			// 收費檔學生名冊
			printStmdFeePayList(mapping, aForm, request, response, sterm);
		} else if ("File4Yun".equals(printOpt)) {
			// 製作財政部扣繳稅額資料
			snedFile4Yun(mapping, aForm, request, response, sterm);
		} else if ("ClassFeePayList".equals(printOpt)) {
			// 班級學雜費明細資料
			printClassFeePayList(mapping, aForm, request, response, sterm);
		} else if ("RegisterUpdate".equals(printOpt)) {
			// 下載匯入更新註冊檔表格
			printRegisterUpdate(mapping, aForm, request, response, sterm);
		}

	}

	/**
	 * 收費檔學生名冊
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings(value = { "unchecked" })
	private void printStmdFeePayList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		DynaActionForm aForm = (DynaActionForm) form;
		String year = aForm.getString("syear");
		String term = aForm.getString("sterm");
		String campusCode = form.getString("campusCode");
		String schoolType = form.getString("schoolType");
		String feeKind = form.getString("feeKind");
		String[] schoolTypes = "D".equalsIgnoreCase(schoolType) ? IConstants.DEPT_DAY
				: ("N".equalsIgnoreCase(schoolType) ? IConstants.DEPT_NIGHT
						: IConstants.DEPT_HOLIDAY);

		String departClass = null;
		FeePay pay = new FeePay();
		pay.setKind(feeKind);
		Example example = null;
		List<Student> students = null;
		List<FeePay> paies = null;
		int totalMoney = 0;

		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.asc("kind"));
		orders.add(Order.asc("fcode"));

		List<Clazz> clazzes = new ArrayList<Clazz>();
		for (String st : schoolTypes) {
			clazzes.addAll(sm.findClassBy(new Clazz(campusCode + st + "___"),
					getUserCredential(session).getClassInChargeAry(), false));
		}

		if (!clazzes.isEmpty()) {

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/StmdFeePayList.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("Arial Unicode MS");

			String endDate = Toolket.Date2Str1(Toolket.parseNativeDate(form
					.getString("endDate")));
			int index = 2, colIndex = 13;

			Set<String> fcodes = new HashSet<String>();
			for (Clazz clazz : clazzes) {
				pay.setDepartClass(clazz.getClassNo());
				example = Example.create(pay).ignoreCase().enableLike(
						MatchMode.ANYWHERE);
				paies = (List<FeePay>) am.findSQLWithCriteria(FeePay.class,
						example, null, orders);

				for (FeePay fp : paies)
					fcodes.add(fp.getFcode());
			}

			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String fp : fcodes) {
				if (!"0".equals(fp)) {
					map.put(fp, colIndex);
					Toolket.setCellValue(workbook, sheet, 1, colIndex++,
							Toolket.getFeeCode(fp), fontSize10,
							HSSFCellStyle.ALIGN_CENTER_SELECTION, true, null,
							null);
				}
			}

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colIndex));
			// Header 學年期要以行政人員選擇為主
			Toolket.setCellValue(sheet, 0, 0, "中華科技大學"
					+ year
					+ "學年度第"
					+ term
					+ "學期"
					+ ("D".equalsIgnoreCase(schoolType) ? "日間部" : ("N"
							.equalsIgnoreCase(schoolType) ? "進修部" : "進修學院"))
					+ Toolket.getFeeKind(feeKind) + "收費檔學生名冊");
			Toolket.setCellValue(workbook, sheet, 1, colIndex, "收費總額",
					fontSize10, HSSFCellStyle.ALIGN_CENTER_SELECTION, true,
					null, null);

			for (Clazz clazz : clazzes) {
				departClass = clazz.getClassNo();
				if (Toolket.isDelayClass(departClass)
						|| Toolket.isLiteracyClass(departClass))
					continue;

				students = mm.findStudentsByClassNo(departClass);
				if (!students.isEmpty()) {

					// term=1代表是要作下學期資料,所以班級需要升級
					if ("1".equals(term))
						departClass = Toolket.processClassNoUp(departClass);

					if (Toolket.isDelayClass(departClass))
						continue;

					pay.setDepartClass(departClass);
					example = Example.create(pay).ignoreCase().enableLike(
							MatchMode.ANYWHERE);
					paies = (List<FeePay>) am.findSQLWithCriteria(FeePay.class,
							example, null, orders);

					for (Student student : students) {
						Toolket.setCellValue(sheet, index, 0, Toolket
								.isHsinChuStudent(departClass) ? "新竹" : "台北");
						Toolket.setCellValue(sheet, index, 1, StringUtils
								.substring(departClass, 0, 4));
						Toolket.setCellValue(sheet, index, 2, Toolket
								.getDepartName(departClass));
						Toolket.setCellValue(sheet, index, 3, departClass);
						Toolket.setCellValue(sheet, index, 4, Toolket
								.getClassFullName(departClass));
						Toolket.setCellValue(sheet, index, 5, student
								.getStudentNo());
						Toolket
								.setCellValue(sheet, index, 6, student
										.getIdno());
						Toolket.setCellValue(sheet, index, 7, student
								.getStudentName());
						Toolket.setCellValue(sheet, index, 8, student
								.getParentName());
						Toolket.setCellValue(sheet, index, 9, student
								.getCurrPost());
						Toolket.setCellValue(sheet, index, 10, student
								.getCurrAddr());
						Toolket.setCellValue(sheet, index, 11, student
								.getTelephone());
						Toolket.setCellValue(sheet, index, 12, endDate);

						if (!paies.isEmpty()) {
							totalMoney = 0;
							for (FeePay p : paies) {
								try {
									Toolket.setCellValue(sheet, index, map
											.get(p.getFcode()), p.getMoney()
											.toString());
									totalMoney += p.getMoney();
								} catch (Exception e) {
									// 因為Fcode會有0,導致Exception,但還是要繼續跑
									continue;
								}
							}

							Toolket.setCellValue(sheet, index, colIndex, String
									.valueOf(totalMoney));
						} else {
							Toolket.setCellValue(sheet, index, colIndex, String
									.valueOf(0));
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

			File output = new File(tempDir, "StmdFeePayList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}

	}

	/**
	 * 製作財政部扣繳稅額資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void snedFile4Yun(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		Map<String, Integer> previous = Toolket.getPreviousYearTerm();
		String previousYear = previous.get(IConstants.PARAMETER_SCHOOL_YEAR)
				.toString();
		String previousTerm = previous.get(IConstants.PARAMETER_SCHOOL_TERM)
				.toString();
		String thisYear = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String thisTerm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		String emailTo = form.getString("emailTo").trim();

		ServletContext context = request.getSession().getServletContext();
		StringBuilder fileName = new StringBuilder("/WEB-INF/reports/").append(
				StringUtils.leftPad(thisYear, 3, '0')).append(".13.03812404");
		File file = new File(context.getRealPath(fileName.toString()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "BIG5")); // 一定要BIG5

		Student student = null;
		FeePay feePay = null;
		List<Student> students = null;
		List<FeePay> feePaies = null;
		int totalMoney = 0;
		// int counts = 0;
		StringBuilder builder = null;
		String studentName = null;
		Map<String, Integer> classFee = new HashMap<String, Integer>();

		BankFeePay bankFeePay = new BankFeePay();
		bankFeePay.setSchoolYear(previousYear);
		bankFeePay.setSchoolTerm(previousTerm);
		List<BankFeePay> paies = am.findBankFeePayBy(bankFeePay);

		bankFeePay.setSchoolYear(thisYear);
		bankFeePay.setSchoolTerm(thisTerm);
		paies.addAll(am.findBankFeePayBy(bankFeePay));

		for (BankFeePay fp : paies) {

			if (StringUtils.isBlank(fp.getStudentNo()))
				continue;

			student = new Student();
			student.setStudentNo(fp.getStudentNo().toUpperCase().trim());
			students = mm.findStudentsBy(student);

			if (!students.isEmpty() && students.size() == 1) {

				student = students.get(0);
				// 7,8 外籍與僑外生要除外
				if (!Toolket.isLiteracyClass(student.getDepartClass())) {
					if (!"7".equalsIgnoreCase(student.getIdent())
							&& !"8".equalsIgnoreCase(student.getIdent())) {

						// TODO
						// counts++;
						// if (counts >= 100)
						// break;

						builder = new StringBuilder(115);
						builder.append("391061");
						builder.append(StringUtils.rightPad(student
								.getStudentNo().toUpperCase().trim(), 12, ' '));
						builder.append(student.getIdno().toUpperCase().trim());
						builder.append(StringUtils.leftPad(Date2Str1(fp
								.getPayDate()), 7, '0'));
						studentName = StringUtils.deleteWhitespace(
								student.getStudentName().trim()).replaceAll(
								"　", "").replaceAll(" ", "");
						studentName = StringUtils.contains(studentName, "?") ? ""
								: studentName;
						builder.append(StringUtils.rightPad(StringUtils
								.deleteWhitespace(student.getStudentName()
										.trim().replaceAll("　", "")),
								12 - StringUtils.deleteWhitespace(
										student.getStudentName().trim()
												.replaceAll("　", "")).length(),
								' '));
						builder
								.append(
										StringUtils.leftPad(fp.getSchoolYear(),
												3, '0')).append(
										fp.getSchoolTerm());
						builder.append(StringUtils.rightPad(
								getSchoolNo(StringUtils.substring(student
										.getDepartClass(), 1, 3)), 2, ' ')); // 學制
						builder.append(StringUtils.leftPad(StringUtils
								.substring(student.getDepartClass(), 4, 5), 2,
								'0'));

						if (!classFee.containsKey(student.getDepartClass())) {
							feePay = new FeePay();
							feePay.setDepartClass(student.getDepartClass());
							feePaies = am.findFeePayBy(feePay);

							totalMoney = 0;
							for (FeePay f : feePaies)
								totalMoney += f.getMoney();

							classFee.put(student.getDepartClass(), totalMoney);
						} else
							totalMoney = classFee.get(student.getDepartClass());

						// 金額為0可以不用轉了
						if (totalMoney > 0) {
							builder.append(StringUtils.leftPad(String
									.valueOf(totalMoney), 10, '0')); // 繳費金額
							builder.append(StringUtils.leftPad("", 10, '0'));
							builder.append(StringUtils.leftPad("", 30, ' '))
									.append("\n");

							bw.write(builder.toString());
						}
					}
				}
			}
		}

		bw.close();

		List<File> files = new LinkedList<File>();
		files.add(file);
		files.add(new File(context
				.getRealPath("/WEB-INF/reports/SchoolCode.txt")));
		files.add(processCampusNo(context));
		new SendEmail(files, emailTo, false).run();

		Map<String, String> param = new HashMap<String, String>();
		File image = new File(context
				.getRealPath("/pages/images/2002chitS.jpg"));
		param.put("IMAGE", image.getAbsolutePath());
		byte[] bytes = JasperRunManager.runReportToPdf(JasperReportUtils
				.getProcessOkReport(context), param, new JREmptyDataSource());
		JasperReportUtils.printPdfToFrontEnd(response, bytes);
	}

	/**
	 * 班級學雜費明細資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings(value = { "unchecked" })
	private void printClassFeePayList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		DynaActionForm aForm = (DynaActionForm) form;
		String year = aForm.getString("syear");
		String term = aForm.getString("sterm");
		String campusCode = form.getString("campusCode");
		String schoolType = form.getString("schoolType");
		String[] schoolTypes = "D".equalsIgnoreCase(schoolType) ? IConstants.DEPT_DAY
				: ("N".equalsIgnoreCase(schoolType) ? IConstants.DEPT_NIGHT
						: IConstants.DEPT_HOLIDAY);

		String departClass = null;
		FeePay pay = new FeePay();
		pay.setKind("1"); // 只管學雜費
		Example example = null;
		List<FeePay> paies = null;
		int maxFeeItemCounts = 0;

		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.asc("kind"));
		orders.add(Order.asc("fcode"));

		List<Clazz> clazzes = new ArrayList<Clazz>();
		for (String st : schoolTypes) {
			clazzes.addAll(sm.findClassBy(new Clazz(campusCode + st + "___"),
					getUserCredential(session).getClassInChargeAry(), false));
		}

		if (!clazzes.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("班級學雜費明細資料");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 5000);

			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("Arial Unicode MS");

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("Arial Unicode MS");

			int index = 2, colIndex = 3;

			for (Clazz clazz : clazzes) {
				pay.setDepartClass(clazz.getClassNo());
				example = Example.create(pay).ignoreCase().enableLike(
						MatchMode.ANYWHERE);
				paies = (List<FeePay>) am.findSQLWithCriteria(FeePay.class,
						example, null, orders);
				if (maxFeeItemCounts < paies.size()) {
					maxFeeItemCounts = paies.size();
				}
			}

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
					2 + (maxFeeItemCounts * 2)));
			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學班級學雜費明細資料",
					fontSize16, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 3,
					2 + (maxFeeItemCounts * 2)));

			// Header 學年期要以行政人員選擇為主
			Toolket.setCellValue(sheet, 0, 0, "中華科技大學"
					+ year
					+ "學年度第"
					+ term
					+ "學期"
					+ ("D".equalsIgnoreCase(schoolType) ? "日間部" : ("N"
							.equalsIgnoreCase(schoolType) ? "進修部" : "進修學院"))
					+ "學雜費明細資料");

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "校區", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "班級代碼", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "班級名稱", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "收費明細", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, false, null);

			for (Clazz clazz : clazzes) {
				departClass = clazz.getClassNo();
				if (Toolket.isDelayClass(departClass)
						|| Toolket.isLiteracyClass(departClass))
					continue;

				colIndex = 3;
				pay.setDepartClass(departClass);
				example = Example.create(pay).ignoreCase().enableLike(
						MatchMode.ANYWHERE);
				paies = (List<FeePay>) am.findSQLWithCriteria(FeePay.class,
						example, null, orders);

				if (!paies.isEmpty()) {
					Toolket.setCellValue(workbook, sheet, index, 0, Toolket
							.isHsinChuStudent(departClass) ? "新竹" : "台北",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 1,
							departClass, fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 2, Toolket
							.getClassFullName(departClass), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, null);

					for (FeePay fp : paies) {
						Toolket.setCellValue(workbook, sheet, index,
								colIndex++, Toolket.getFeeCode(fp.getFcode()),
								fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
								null);
						Toolket.setCellValue(workbook, sheet, index,
								colIndex++, fp.getMoney().toString(),
								fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
								null);
					}

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

			File output = new File(tempDir, "ClassFeePayList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}

	}

	/**
	 * 下載匯入更新註冊檔表格
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printRegisterUpdate(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/RegisterUpdate.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));

		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "RegisterUpdate.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	private File processCampusNo(ServletContext context) throws Exception {
		File file = new File(context
				.getRealPath("/WEB-INF/reports/CampusNo.txt"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "UTF-8"));
		StringBuilder builder = null;
		for (String[] type : all) {
			for (String code : type) {
				builder = new StringBuilder(55);
				builder.append(StringUtils.rightPad(getSchoolNo(code), 2, ' '));
				builder.append(StringUtils.rightPad(Toolket.getSchool(code),
						20, ' '));
				builder.append(StringUtils.leftPad("", 30, ' ')).append("\n");
				bw.write(builder.toString());
			}
		}

		bw.close();
		return file;
	}

	private static class SendEmail implements Runnable {

		Logger log = Logger.getLogger(SendEmail.class);

		private List<File> files = null;
		private String to = null;
		private String RETURN = "\n";
		// private String SPACE = " ";
		private boolean isDebug = false;

		SendEmail(List<File> files, String to, boolean isDebug) {
			this.files = files;
			this.to = to;
			this.isDebug = isDebug;
		}

		public void run() {

			MultiPartEmail email = new MultiPartEmail();
			email.setCharset("big5");
			email.setSentDate(new Date());
			// email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_WWW);
			// email.setAuthentication("cc@www.chit.edu.tw", "577812");
			email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_NO_AUTHEN);
			email.setSubject("財政部扣繳稅額資料");
			email.setDebug(isDebug);
			EmailAttachment attachment = null;
			try {

				email.addTo(to);
				// email.addBcc(IConstants.EMAIL_ELECTRIC_COMPUTER, "電算中心");
				// 避免被SpamMail過濾掉
				email.setFrom("cc@www.cust.edu.tw", "電算中心軟體開發組");
				email.addReplyTo("oscarwei168@cc.cust.edu.tw", "電算中心軟體開發組");
				StringBuilder content = new StringBuilder();
				content.append("財政部扣繳稅額資料:").append(RETURN).append(RETURN);
				// email.setContent(content.toString(),
				// MultiPartEmail.ATTACHMENTS
				// + "; charset=big5");

				for (File file : files) {

					attachment = new EmailAttachment();
					attachment.setPath(file.getAbsolutePath());
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					attachment.setDescription("");
					attachment.setName(file.getName());
					email.attach(attachment);
				}

				email.send();
			} catch (EmailException ee) {
				log.error(ee.getMessage(), ee);
			}
		}
	}

	String processClassInfo(DynaActionForm form) {
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

	/**
	 * Date Object轉成民國年
	 * 
	 * @commend 會捕0,無-
	 * @param uDate
	 * @return
	 */
	private static String Date2Str1(Date uDate) {
		if (uDate == null)
			return "";
		String ret = "";
		Calendar aDate = Calendar.getInstance();
		aDate.setTime(uDate);
		ret += String.valueOf(aDate.get(Calendar.YEAR) - 1911);
		ret += (aDate.get(Calendar.MONTH) + 1) < 10 ? "0"
				+ (aDate.get(Calendar.MONTH) + 1)
				: aDate.get(Calendar.MONTH) + 1;
		ret += aDate.get(Calendar.DATE) >= 10 ? aDate.get(Calendar.DATE) : "0"
				+ aDate.get(Calendar.DATE);
		return ret;
	}

	private static String getSchoolNo(String code) {

		if (StringUtils.isBlank(code))
			return "";

		if (ArrayUtils.contains(master, code))
			return "M";
		else if (ArrayUtils.contains(type1, code))
			return "2";
		else if (ArrayUtils.contains(type2, code))
			return "5";
		else if (ArrayUtils.contains(type3, code))
			return "B1";
		else if (ArrayUtils.contains(type4, code))
			return "B2";
		else if (ArrayUtils.contains(type5, code))
			return "C";
		else
			return "";
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("preview", "printReport");
		return map;
	}

}
