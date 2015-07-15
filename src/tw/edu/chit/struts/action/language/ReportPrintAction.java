package tw.edu.chit.struts.action.language;

import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import tw.edu.chit.model.CodeEmpl;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.DeptCode4Yun;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.LicenseCode;
import tw.edu.chit.model.LicenseCode961;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.StdSkill;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
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

		setContentPage(session, "language/ReportPrint.jsp");
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
		String printOpt = aForm.getString("printOpt");
		String sterm = (String) aForm.get("sterm");

		if ("LicenseCodes".equals(printOpt)) {
			// 962至今報部證照代碼對照表列印
			printLicenseCodes(mapping, aForm, request, response, sterm);
		} else if ("LicenseCodes961".equals(printOpt)) {
			// 961以前(含)報部證照代碼對照表列印
			printLicenseCodes961(mapping, aForm, request, response, sterm);
		} else if ("CscodeList".equals(printOpt)) {
			// 科目代碼表列印
			printCscodeList(mapping, aForm, request, response, sterm);
		} else if ("DeptStdSkillList".equals(printOpt)) {
			// 系所學生證照列印
			printDeptStdSkillList(mapping, aForm, request, response, sterm);
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
			// 已取得證照列表
			printDeptStdSkillList4(mapping, aForm, request, response, sterm);
		} else if ("DeptStdSkillList-5".equals(printOpt)) {
			// 教師輔導學生取得證照一覽表
			printDeptStdSkillList5(mapping, aForm, request, response, sterm);
		} else if ("DeptCode4Yun".equals(printOpt)) {
			// 雲科大學制科系代碼表列印
			printDeptCode4Yun(mapping, aForm, request, response, sterm);
		}
	}

	/**
	 * 系所學生證照列印
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

		Member member = (Member) getUserCredential(session).getMember();
		Empl empl = mm.findEmplByOid(member.getOid());
		ServletContext context = request.getSession().getServletContext();

		StdSkill skill = new StdSkill();
		skill.setAmount(null); // 避免被查詢
		Example example = Example.create(skill).ignoreCase().enableLike(
				MatchMode.ANYWHERE);
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("schoolYear"));
		orders.add(Order.asc("schoolTerm"));
		orders.add(Order.asc("studentNo"));

		Criterion cri = Restrictions.eq("deptNo", "0"); // 語言中心
		List<StdSkill> skills = (List<StdSkill>) am.findSQLWithCriteria(
				StdSkill.class, example, null, orders, cri);

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/DeptStdSkillList.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		int index = 2;
		List<LicenseCode> codes = null;
		Student student = null;
		Graduate graduate = null;
		DEmpl dempl = null;
		Csno csno = null;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat df1 = new SimpleDateFormat("yyyy/MM");
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

		List<Object> ret = (List<Object>) am.find(hql, new Object[] { "0",
				cal1.getTime(), cal2.getTime() }); // 語言中心

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
			totals += (Integer) data[0];
			skill = (StdSkill) data[1];

			Toolket.setCellValue(workbook, sheet, index, 0, String
					.valueOf(index - 1), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index, 5, ((Integer) data[0])
					.toString(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
					null);

			codes = (List<LicenseCode>) am.findLicenseCodesBy(new LicenseCode(
					Integer.valueOf(skill.getLicenseCode())));

			if (!codes.isEmpty()) {
				code = codes.get(0);
				Toolket.setCellValue(workbook, sheet, index, 1, code.getName(),
						fontSize10, HSSFCellStyle.ALIGN_LEFT, true, null);
				Toolket.setCellValue(workbook, sheet, index, 2,
						code.getLevel(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 3, code.getType()
						.toString(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, index, 4, code
						.getDeptName(), fontSize10, HSSFCellStyle.ALIGN_LEFT,
						true, null);
			} else {
				code961s = (List<LicenseCode961>) am
						.findLicenseCode961sBy(new LicenseCode961(Integer
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

		String hql = "FROM StdSkill s WHERE s.amountDate IS NOT NULL AND s.deptNo = ? "
				+ "AND s.licenseValidDate BETWEEN ? AND ? ORDER BY s.studentNo";

		List<StdSkill> skills = (List<StdSkill>) am.find(hql, new Object[] {
				"0", cal1.getTime(), cal2.getTime() }); // 語言中心

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/DeptStdSkillList2.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		// Header
		Toolket.setCellValue(sheet, 0, 0, "中華科技大學"
				+ Toolket.getEmpUnit(empl.getUnit()) + "學生考取專業證照報名費補助清冊 ("
				+ df.format(cal1.getTime()) + "~" + df.format(cal2.getTime())
				+ ")");

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
						.findLicenseCodesBy(new LicenseCode(Integer
								.valueOf(skill.getLicenseCode())));

				if (!codes.isEmpty()) {
					code = codes.get(0);
					Toolket.setCellValue(sheet, index, 6, code.getName());
					Toolket.setCellValue(sheet, index, 7, code.getDeptName());
					Toolket.setCellValue(sheet, index, 8, code.getLevel());
				} else {
					code961s = (List<LicenseCode961>) am
							.findLicenseCode961sBy(new LicenseCode961(Integer
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
				+ "WHERE ss.deptNo = ? AND ss.licenseValidDate BETWEEN ? AND ? "
				+ "GROUP BY ss.licenseValidDate, s.depart_class, s.sex, "
				+ "gs.depart_class, gs.sex "
				+ "ORDER BY ss.licenseValidDate, s.depart_class, "
				+ "s.sex, gs.depart_class, gs.sex";

		List<Map> ret = (List<Map>) am.findBySQL(hql, new Object[] { "0",
				cal1.getTime(), cal2.getTime() }); // 語言中心

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
			codes = (List<LicenseCode>) am.findLicenseCodesBy(new LicenseCode(
					Integer.valueOf((String) data.get("LC"))));
			if (!codes.isEmpty()) {
				code = codes.get(0);
				Toolket.setCellValue(workbook, sheet, index, 6, code.getName(),
						fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 7,
						code.getLevel(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
			} else {
				code961s = (List<LicenseCode961>) am
						.findLicenseCode961sBy(new LicenseCode961(Integer
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
	 * 已取得證照列表
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

		String hql = "SELECT COUNT(*), SUM(s.amount), s FROM StdSkill s WHERE s.deptNo = ? "
				+ "AND s.amountDate IS NOT NULL AND s.licenseValidDate BETWEEN ? AND ? "
				+ "GROUP BY s.licenseCode";

		List<Object> ret = (List<Object>) am.find(hql, new Object[] { "0",
				cal1.getTime(), cal2.getTime() }); // 語言中心

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("已取得證照列表");
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
				+ Toolket.getEmpUnit(empl.getUnit()) + "已取得證照列表 ("
				+ df.format(cal1.getTime()) + "~" + df.format(cal2.getTime())
				+ ")", fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F,
				null);

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
					Integer.valueOf(skill.getLicenseCode())));

			if (!codes.isEmpty()) {
				code = codes.get(0);
				Toolket.setCellValue(workbook, sheet, index, 1, code.getCode()
						.toString(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
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
						.findLicenseCode961sBy(new LicenseCode961(Integer
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

		List<Object> ret = (List<Object>) am.find(hql, new Object[] { "0",
				cal1.getTime(), cal2.getTime() }); // 語言中心

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
				Toolket.setCellValue(workbook, sheet, index, 0, dempl
						.getCname(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
						true, null);
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
			Toolket.setCellValue(workbook, sheet, index + 2, 3,
					((Integer) data[0]).toString(), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);

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
				LicenseCode.class, example, null, orders);

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

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("preview", "printReport");
		return map;
	}

}
