package tw.edu.chit.struts.action.studaffair;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.springframework.util.CollectionUtils;

import tw.edu.chit.model.ClassInCharge;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.LifeCounseling;
import tw.edu.chit.model.TeacherStayTime;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class ReportPrint4SAAction extends BaseLookupDispatchAction {

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
		setContentPage(session, "studaffair/ReportPrint4SA.jsp");
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
		aForm.set("campusInCharge2", request.getParameter("c"));
		aForm.set("schoolInCharge2", request.getParameter("s"));
		aForm.set("deptInCharge2", request.getParameter("d"));
		aForm.set("classInCharge2", request.getParameter("cl"));
		aForm.set("printInterClass", request.getParameter("printInterClass"));
		String printOpt = aForm.getString("printOpt");
		String sterm = (String) aForm.get("sterm");

		if ("StayTimeList".equals(printOpt)) {
			// 列印導師留校時間列印
			printStayTimeList(mapping, aForm, request, response, sterm);
		}

	}

	/**
	 * 列印導師留校時間列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printStayTimeList(ActionMapping mapping, DynaActionForm form,
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
			fontSize12.setFontName("標楷體");

			HSSFSheet sheet = null;
			int sheetIndex = 0, colOffset = 1, col = 0;
			String departClass = null;
			Empl e = null;
			Short colorForStayTime = HSSFColor.AUTOMATIC.index;
			Short colorForLifeCounseling = HSSFColor.LIGHT_GREEN.index;
			List<TeacherStayTime> tsts = null;
			List<LifeCounseling> lcs = null;
			List<Map> map = null;
			Map content = null;
			List<ClassInCharge> cics = null;
			ClassInCharge cic = null;
			Set<Empl> empls = new HashSet<Empl>();

			for (Clazz clazz : clazzes) {
				departClass = clazz.getClassNo();
				if (!Toolket.isLiteracyClass(departClass)) {
					cic = new ClassInCharge();
					cic.setClassNo(departClass);
					cic.setModuleOids("|" + UserCredential.AuthorityOnTutor
							+ "|");
					cics = mm.findClassInChargeBy(cic);
					if (!cics.isEmpty()) {
						e = mm.findEmplByOid(cics.get(0).getEmpOid());
						if (e != null)
							empls.add(e);
					}
				}
			}

			for (Empl empl : empls) {
				if (empl != null && "1".equalsIgnoreCase(empl.getCategory())) {

					sheet = workbook.getSheetAt(sheetIndex);
					// System.out.println(empl.getCname());
					workbook.setSheetName(sheetIndex++, empl.getCname());

					Toolket.setCellValue(sheet, 0, 1, year + "學年度" + term
							+ "學期 " + empl.getCname() + " 老師授課時間表");
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

			File output = new File(tempDir, "RegisterList.xls");
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

	// 沒再用了
	String calGradeYear(int grade, Short entrance) {
		String tmpEntrance = String.valueOf(entrance);
		tmpEntrance = tmpEntrance.length() == 4 ? StringUtils.substring(
				tmpEntrance, 0, 2) : StringUtils.substring(tmpEntrance, 0, 3);
		return String.valueOf(grade + Integer.parseInt(tmpEntrance) - 1);
	}

}
