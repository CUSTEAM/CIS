package tw.edu.chit.struts.action.registration;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.StdLoan;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class StudentLoanReportAction extends BaseLookupDispatchAction {

	/**
	 * 學生就學貸款報表
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
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", sterm);
		setContentPage(session, "registration/StdLoanReportPrint.jsp");
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

		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", request.getParameter("st"));
		aForm.set("printOpt", request.getParameter("p"));
		String printOpt = aForm.getString("printOpt");
		String sterm = (String) aForm.get("sterm");

		if ("StdLoanList".equals(printOpt)) {
			printStdLoanList(mapping, aForm, request, response, sterm);
		}
	}

	/**
	 * 匯出就學貸款報表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printStdLoanList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String term = form.getString("sterm");

		StdLoan loan = new StdLoan();
		loan.setSchoolTerm(term);
		List<StdLoan> stdLoans = cm.findStdLoanBy(loan);
		if (!stdLoans.isEmpty()) {

			UserCredential credential = getUserCredential(request
					.getSession(false));
			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ credential.getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();
			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/StdLoanReport.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);

			Toolket.setCellValue(sheet, 0, 12, sterm.equals("1") ? "上學期"
					: "下學期");
			Toolket.setCellValue(sheet, 1, 12, new SimpleDateFormat(
					"yyyy/MM/dd").format(new Date()));
			Toolket.setCellValue(sheet, 2, 12, "1"); // 頁次

			HSSFFont fontSize8 = workbook.createFont();
			fontSize8.setFontHeightInPoints((short) 8);
			fontSize8.setFontName("標楷體");

			Student student = null;
			Graduate graduate = null;
			String studentName = null, idno = null, studentNo = null;
			String departClass = null, schoolType = null;

			int row = 5;
			for (StdLoan stdLoan : stdLoans) {
				student = mm.findStudentByNo(stdLoan.getStudentNo());
				if (student == null) {
					graduate = mm.findGraduateByStudentNo(stdLoan
							.getStudentNo());
					studentName = graduate.getStudentName();
					idno = graduate.getIdno();
					studentNo = graduate.getStudentNo();
					departClass = graduate.getDepartClass();
				} else {
					studentName = student.getStudentName();
					idno = student.getIdno();
					studentNo = student.getStudentNo();
					departClass = student.getDepartClass();
				}

				Toolket.setCellValue(sheet, row, 0, idno.toUpperCase());
				Toolket.setCellValue(sheet, row, 1, studentName);
				Toolket.setCellValue(sheet, row, 12, studentNo.toUpperCase());
				Toolket.setCellValue(sheet, row, 13, stdLoan.getSchoolTerm()
						.equals("1") ? "上" : "下"); // 學期

				schoolType = cm.findSchoolTypeByClassNo(departClass);
				Toolket.setCellValue(sheet, row, 10, schoolType
						.equals(IConstants.DAY) ? "日" : (schoolType
						.equals(IConstants.NIGHT) ? "夜" : "")); // 日夜
				Toolket.setCellValue(sheet, row, 11, StringUtils.substring(
						departClass, 4, 5)); // 年級

//				Toolket.setCellValue(sheet, row, 2,
//						stdLoan.getWifeIdno() == null ? "" : stdLoan
//								.getWifeIdno().trim().toUpperCase());
//				Toolket.setCellValue(sheet, row, 3,
//						stdLoan.getWifeName() == null ? "" : stdLoan
//								.getWifeName().trim());
				Toolket.setCellValue(sheet, row, 4,
						stdLoan.getFatherIdno() == null ? "" : stdLoan
								.getFatherIdno().trim().toUpperCase());
				Toolket.setCellValue(sheet, row, 5,
						stdLoan.getFatherName() == null ? "" : stdLoan
								.getFatherName().trim());
				Toolket.setCellValue(sheet, row, 6,
						stdLoan.getMotherIdno() == null ? "" : stdLoan
								.getMotherIdno().trim().toUpperCase());
				Toolket.setCellValue(sheet, row, 7,
						stdLoan.getMotherName() == null ? "" : stdLoan
								.getMotherName().trim());

				Toolket.setCellValue(sheet, row, 8, stdLoan.getAmount()
						.toString());
				Toolket.setCellValue(sheet, row, 9, Toolket
						.getDepartName(departClass));

				row++;
				sheet
						.addMergedRegion(new Region(row, (short) 0, row,
								(short) 4));
//				Toolket.setCellValue(workbook, sheet, row, 0, stdLoan
//						.getAddress() == null ? "戶籍地址：" : "戶籍地址："
//						+ stdLoan.getAddress().trim(), fontSize8,
//						HSSFCellStyle.ALIGN_LEFT, false, null, null);

				sheet
						.addMergedRegion(new Region(row, (short) 5, row,
								(short) 7));
				Toolket.setCellValue(workbook, sheet, row, 5, stdLoan
						.getPhone() == null ? "通訊電話：" : "通訊電話："
						+ stdLoan.getPhone().trim(), fontSize8,
						HSSFCellStyle.ALIGN_LEFT, false, null, null);

				sheet
						.addMergedRegion(new Region(row, (short) 8, row,
								(short) 9));
//				Toolket.setCellValue(workbook, sheet, row, 8, stdLoan
//						.getGradeYear() == null ? "畢業年月：" : "畢業年月："
//						+ stdLoan.getGradeYear().trim()
//						+ stdLoan.getGradeMonth(), fontSize8,
//						HSSFCellStyle.ALIGN_LEFT, false, null, null);
//
//				row++;
//				sheet
//						.addMergedRegion(new Region(row, (short) 0, row,
//								(short) 6));
//				Toolket.setCellValue(workbook, sheet, row, 0, stdLoan
//						.getGradeYear() == null ? "保證人一：" : "保證人一："
//						+ stdLoan.getSponsorIdno1().trim() + "  "
//						+ stdLoan.getSponsorName1().trim() + "  "
//						+ stdLoan.getSponsorRelation1().trim() + "  "
//						+ stdLoan.getSponsorPhone1(), fontSize8,
//						HSSFCellStyle.ALIGN_LEFT, false, null, null);
//
//				sheet.addMergedRegion(new Region(row, (short) 7, row,
//						(short) 13));
//				Toolket.setCellValue(workbook, sheet, row, 7, stdLoan
//						.getGradeYear() == null ? "保證人二：" : "保證人二："
//						+ stdLoan.getSponsorIdno2().trim() + "  "
//						+ stdLoan.getSponsorName2().trim() + "  "
//						+ stdLoan.getSponsorRelation2().trim() + "  "
//						+ stdLoan.getSponsorPhone2(), fontSize8,
//						HSSFCellStyle.ALIGN_LEFT, false, null, null);
				row++;
			}

			File output = new File(tempDir, "StdLoanReport.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}

	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("preview", "printReport");
		return map;
	}

}
