package tw.edu.chit.struts.action.military;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.RegistrationCard;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.Student;
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
		session.setMaxInactiveInterval(-1);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", sterm);
		aForm.set("year", tw.edu.chit.struts.action.score.ReportPrintAction
				.getYearArray(Toolket.getNextYearTerm().get(
						IConstants.PARAMETER_SCHOOL_YEAR).toString()));
		setContentPage(session, "military/ReportPrint.jsp");
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
		aForm.set("sterm", request.getParameter("st"));
		aForm.set("printOpt", request.getParameter("p"));
		aForm.set("campusInCharge2", request.getParameter("c"));
		aForm.set("schoolInCharge2", request.getParameter("s"));
		aForm.set("deptInCharge2", request.getParameter("d"));
		aForm.set("classInCharge2", request.getParameter("cl"));
		aForm.set("printInterClass", request.getParameter("printInterClass"));
		String printOpt = aForm.getString("printOpt");
		String sterm = (String) aForm.get("sterm");
		request.getSession(false).setMaxInactiveInterval(-1);

		if ("ClassStudentsRegistrationCard".equals(printOpt)) {
			// 列印新生學籍卡
			printClassStudentsRegistrationCard(mapping, aForm, request,
					response, sterm);
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
						Toolket.setCellValue(workbook, sheet, 4, 3, student
								.getBirthday() == null ? "未填入生日"
								: printNativeDate(df.format(student
										.getBirthday())), fontSize11,
								HSSFCellStyle.ALIGN_LEFT, true, null, null);
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

	private String printNativeDate(String date) {
		return String.valueOf(Integer.parseInt(StringUtils
				.substring(date, 0, 4))
				- Global.NativeYearBase)
				+ StringUtils.substring(date, 4).replaceAll("0", "");
	}

}
