package tw.edu.chit.struts.action.fee;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Dipost;
import tw.edu.chit.model.FeePay;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

import com.ibm.icu.text.NumberFormat;

public class ReportPrintFeeAction extends BaseLookupDispatchAction {

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
		setContentPage(session, "fee/ReportPrintFee.jsp");
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
		aForm.set("kindCode", request.getParameter("kc"));
		String printOpt = aForm.getString("printOpt");
		String sterm = (String) aForm.get("sterm");

		if ("StdTransferAccountList".equals(printOpt)) {
			// 學生轉帳帳號資料列印
			printStdTransferAccountList(mapping, aForm, request, response,
					sterm);
		} else if ("ClassFeePay2List".equals(printOpt)) {
			// 班級代辦費資料列印
			printClassFeePay2List(mapping, aForm, request, response, sterm);
		} else if ("RegisterUpdate".equals(printOpt)) {
			// 下載更新註冊檔檔案
			printRegisterUpdate(mapping, aForm, request, response, sterm);
		}

	}

	/**
	 * 學生轉帳帳號資料列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printStdTransferAccountList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		DynaActionForm aForm = (DynaActionForm) form;

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		String idno = getUserCredential(session).getMember().getIdno();
		String kindCode = aForm.getString("kindCode");

		Dipost dipost = new Dipost();
		dipost.setSchoolYear(year);
		dipost.setSchoolTerm(term);
		dipost.setKind(kindCode);
		dipost.setModifier(idno.trim().toUpperCase());
		Example example = Example.create(dipost).ignoreCase().enableLike(
				MatchMode.EXACT);
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("studentNo"));
		List<Dipost> diposts = (List<Dipost>) am.findSQLWithCriteria(
				Dipost.class, example, null, orders, 20000);

		if (!diposts.isEmpty()) {

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/StdTransferAccountList.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("Arial Unicode MS");

			Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + term
					+ "學期轉帳清冊");
			Toolket.setCellValue(sheet, 0, 7, Toolket.printNativeDate(
					new Date(), new SimpleDateFormat("yy/MM/dd")));
			int index = 2, peopleCounts = 0, moneyCounts = 0;
			Student student = null;
			List<Student> students = null;
			NumberFormat nf = NumberFormat.getInstance();
			System.out.println(diposts.size());
			for (Dipost d : diposts) {

				student = new Student();
				student.setStudentNo(d.getStudentNo().toUpperCase().trim());
				students = mm.findStudentsBy(student);

				if (!students.isEmpty()) {

					student = students.get(0);
					Toolket.setCellValue(sheet, index, 0, Toolket
							.getClassFullName(student.getDepartClass()));
					Toolket.setCellValue(sheet, index, 1, d.getStudentNo());
					Toolket.setCellValue(sheet, index, 2, student
							.getStudentName());
					Toolket.setCellValue(sheet, index, 3, d.getOfficeNo()
							.trim());
					Toolket.setCellValue(sheet, index, 4, d.getAcctNo().trim());
					Toolket.setCellValue(sheet, index, 5, student.getIdno());
					Toolket.setCellValue(sheet, index, 6, nf.format(d
							.getMoney()));
					Toolket.setCellValue(sheet, index++, 7, Toolket
							.getTransferKind(d.getKind()));
					peopleCounts++;
					moneyCounts += d.getMoney();
				} else {
					Graduate graduate = mm.findGraduateByStudentNo(d.getStudentNo().toUpperCase().trim());
					if(graduate==null)continue;
					Toolket.setCellValue(sheet, index, 0, Toolket .getClassFullName(graduate.getDepartClass()));
					Toolket.setCellValue(sheet, index, 1, d.getStudentNo());
					Toolket.setCellValue(sheet, index, 2, graduate
							.getStudentName());
					Toolket.setCellValue(sheet, index, 3, d.getOfficeNo()
							.trim());
					Toolket.setCellValue(sheet, index, 4, d.getAcctNo().trim());
					Toolket.setCellValue(sheet, index, 5, graduate.getIdno());
					Toolket.setCellValue(sheet, index, 6, nf.format(d
							.getMoney()));
					Toolket.setCellValue(sheet, index++, 7, Toolket
							.getTransferKind(d.getKind()));
					peopleCounts++;
					moneyCounts += d.getMoney();
				}
			}

			index++;
			sheet.addMergedRegion(new CellRangeAddress(index, index, 0, 2));
			Toolket.setCellValue(sheet, index, 0, "轉帳人數：" + peopleCounts
					+ "人     轉帳金額：$" + nf.format(moneyCounts) + "元");

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "StdTransferAccountList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();
			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 班級代辦費資料列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printClassFeePay2List(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		Integer year = Integer.valueOf(cm.getNowBy("School_year"));
		String term = form.getString("sterm");
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), true);

		if (!clazzes.isEmpty()) {

			String departClass = null;
			FeePay feePay = new FeePay();
			feePay.setKind("2"); // 代辦費
			List<FeePay> feePaies = null;

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("班級代辦費資料");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 4000);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

			HSSFFont fontSize14 = workbook.createFont();
			fontSize14.setFontHeightInPoints((short) 14);
			fontSize14.setFontName("Arial Unicode MS");

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("Arial Unicode MS");

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學" + year
					+ "學年度第" + term + "學期班級代辦費資料", fontSize14,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "班級代碼", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "班級名稱", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "收費名稱", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "收費金額", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;

			for (Clazz clazz : clazzes) {
				departClass = clazz.getClassNo();
				if (Toolket.isDelayClass(departClass))
					continue;

				feePay.setDepartClass(departClass);
				feePaies = am.findFeePayBy(feePay);
				if (!feePaies.isEmpty()) {
					for (FeePay p : feePaies) {

						Toolket.setCellValue(workbook, sheet, index, 0,
								departClass, fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 1, Toolket
								.getClassFullName(departClass), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 2, Toolket
								.getFeeCode(p.getFcode()), fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index++, 3, p
								.getMoney().toString(), fontSize10,
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

			File output = new File(tempDir, "ClassFeePay2List.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 下載更新註冊檔檔案
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
				.getRealPath("/WEB-INF/reports/RegisterUpdate1.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));

		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "RegisterUpdate1.xls");
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
		schoolInCharge2 = schoolInCharge2.equalsIgnoreCase("All") ? ""
				: schoolInCharge2;
		deptInCharge2 = deptInCharge2.equalsIgnoreCase("All") ? ""
				: deptInCharge2;
		departClass = departClass.equalsIgnoreCase("All") ? "" : departClass;
		return departClass.length() == 6 ? departClass : campusInCharge2
				+ schoolInCharge2 + deptInCharge2;
	}

}
