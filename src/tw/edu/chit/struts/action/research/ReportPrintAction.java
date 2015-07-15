package tw.edu.chit.struts.action.research;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Investigation;
import tw.edu.chit.model.Student;
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
				.getYearArray(cm.getNowBy("School_year")));
		session.setAttribute("editMode", false);
		setContentPage(session, "research/ReportPrint.jsp");
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
		String printOpt = (String) aForm.get("printOpt");
		String sterm = (String) aForm.get("sterm");
		request.getSession(false).setMaxInactiveInterval(-1);

		if ("GstmdList".equals(printOpt)) {
			printGstmdList(mapping, aForm, request, response, sterm);
		} else if ("GstmdList4Ntnu".equals(printOpt)) {
			// 應屆畢業生基本資料(師大格式)
			printGstmdList4Ntnu(mapping, aForm, request, response, sterm);
		}

	}

	/**
	 * 列印清單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printGstmdList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		LinkedHashMap<String, Integer> deptCode = new LinkedHashMap<String, Integer>();
		deptCode.put("1", 0);
		deptCode.put("2", 1);
		deptCode.put("3", 2);
		deptCode.put("4", 3);
		deptCode.put("5", 4);
		deptCode.put("6", 5);
		deptCode.put("7", 6);
		deptCode.put("8", 7);
		deptCode.put("9", 8);
		deptCode.put("A", 9);
		deptCode.put("B", 10);
		deptCode.put("C", 11);
		deptCode.put("D", 12);
		deptCode.put("E", 13);
		deptCode.put("F", 14);
		deptCode.put("H", 15);
		deptCode.put("I", 16);
		deptCode.put("J", 17);
		deptCode.put("K", 18);
		deptCode.put("L", 19);
		deptCode.put("M", 20);
		deptCode.put("N", 21);
		deptCode.put("P", 22);
		deptCode.put("R", 23);
		deptCode.put("V", 24);

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = session.getServletContext();

		String year = request.getParameter("year");
		int nextYear = Integer.parseInt(year) + 1;
		Graduate graduate = null;
		List<Graduate> graduates = null, graduates4NextYear = null;
		int rowIndex = 0, sheetIndex = 0;
		HSSFSheet sheet = null;
		Set<String> keySet = deptCode.keySet();

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/GstmdList.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		String header = year + "年歷屆畢業生通訊調查表";

		for (String key : keySet) {

			graduate = new Graduate();
			graduate.setOccurYear(Short.valueOf(year));
			graduate.setOccurStatus("6"); // Only畢業
			graduate.setDepartClass("___" + key + "__");
			graduates = mm.findGraduatesBy(graduate);

			// 把延修畢業生也抓出來
			// 但只針對延修一年的學生
			graduate = new Graduate();
			graduate.setOccurYear((short) nextYear);
			graduate.setOccurStatus("6"); // Only畢業
			graduate.setDepartClass("___" + key + "__");
			graduates4NextYear = mm.findGraduatesBy(graduate);
			String tmpClassNo = null;
			for (Graduate g : graduates4NextYear) {
				tmpClassNo = StringUtils.substring(g.getDepartClass(), 0, 4)
						+ (Integer.parseInt(StringUtils.substring(g
								.getDepartClass(), 4, 5)) - 1)
						+ StringUtils.substring(g.getDepartClass(), 5);
				if (!Toolket.isDelayClass(tmpClassNo)
						&& Toolket.isGraduateClass(tmpClassNo)) {
					graduates.add(g);
					// System.out.println(tmpClassNo + " " + g.getStudentNo());
				} else {
					// System.out.println("N:" + tmpClassNo + " "
					// + g.getStudentNo());
				}
			}

			if (!graduates.isEmpty()) {

				sheet = workbook.getSheetAt(sheetIndex);
				workbook.setSheetName(sheetIndex++, Toolket.getDept(key));
				Toolket.setCellValue(sheet, 0, 0, header);
				List<Investigation> inveses = null;
				Investigation inves = null;
				Example example = null;
				DateFormat df = new SimpleDateFormat("yyyy/MM");
				rowIndex = 2;

				for (Graduate g : graduates) {

//					if (!Toolket.isDelayClass(g.getDepartClass())) {

						Toolket.setCellValue(sheet, rowIndex, 0, year);
						Toolket.setCellValue(sheet, rowIndex, 1, g
								.getStudentNo());
						Toolket.setCellValue(sheet, rowIndex, 2, g
								.getStudentName());
						Toolket.setCellValue(sheet, rowIndex, 3, Toolket
								.getClassFullName(g.getDepartClass()));
						Toolket.setCellValue(sheet, rowIndex, 4, Toolket
								.getSex(g.getSex()));

						inves = new Investigation();
						inves.setStudentNo(g.getStudentNo().trim()
								.toUpperCase());
						example = Example.create(inves).ignoreCase()
								.enableLike(MatchMode.START);
						inveses = (List<Investigation>) am.findSQLWithCriteria(
								Investigation.class, example, null, null);
						if (!inveses.isEmpty()) {
							inves = inveses.get(0);
							Toolket.setCellValue(sheet, rowIndex, 5,
									("0".equals(inves.getForeignOrNot()) ? "國內"
											: "1".equals(inves
													.getForeignOrNot()) ? "國外"
													: ""));
							Toolket.setCellValue(sheet, rowIndex, 6, inves
									.getSchoolName());
							Toolket.setCellValue(sheet, rowIndex, 7, inves
									.getCanpus());
							Toolket.setCellValue(sheet, rowIndex, 8, inves
									.getLevel());

							Toolket.setCellValue(sheet, rowIndex, 9, inves
									.getCompany());
							Toolket.setCellValue(sheet, rowIndex, 10, inves
									.getForeignOrNot());
							Toolket
									.setCellValue(
											sheet,
											rowIndex,
											11,
											StringUtils
													.defaultIfEmpty(
															inves
																	.getCompanyPhone(),
															"").trim());
							Toolket.setCellValue(sheet, rowIndex, 12,
									StringUtils.defaultIfEmpty(
											inves.getBossName(), "").trim());
							Toolket.setCellValue(sheet, rowIndex, 13,
									StringUtils.defaultIfEmpty(
											inves.getBossEmail(), "").trim());
							Toolket.setCellValue(sheet, rowIndex, 14,
									StringUtils.defaultIfEmpty(
											inves.getBossAddr(), "").trim());
							Toolket.setCellValue(sheet, rowIndex, 15, Toolket
									.getWorkNature(inves.getWorkNature()));
							Toolket.setCellValue(sheet, rowIndex, 16, Toolket
									.getWorkDuty(inves.getWorkDuty()));
							Toolket.setCellValue(sheet, rowIndex, 17, inves
									.getWorkTitle());
							Toolket.setCellValue(sheet, rowIndex, 18, (inves
									.getWorkStart() == null ? "" : df
									.format(inves.getWorkStart())));
							Toolket.setCellValue(sheet, rowIndex, 19, Toolket
									.getSalaryRange(inves.getSalaryRange()));
							Toolket.setCellValue(sheet, rowIndex, 20, Toolket
									.getArmyType(inves.getArmyType()));
							Toolket.setCellValue(sheet, rowIndex, 21,
									StringUtils.defaultIfEmpty(
											inves.getArmyName(), "").trim());
							Toolket.setCellValue(sheet, rowIndex, 22,
									StringUtils.defaultIfEmpty(
											inves.getArmyLevel(), "").trim());
						}

						Toolket.setCellValue(sheet, rowIndex, 23, g.getEmail());
						Toolket.setCellValue(sheet, rowIndex, 24, g
								.getCellPhone());
						Toolket.setCellValue(sheet, rowIndex, 25, g
								.getTelephone());
						Toolket.setCellValue(sheet, rowIndex, 26, g
								.getCurrPost());
						Toolket.setCellValue(sheet, rowIndex, 27, g
								.getCurrAddr());
						rowIndex++;
//					} else if (nextYear == Integer.parseInt(g.getOccurYear()
//							.toString())) {
//						Toolket.setCellValue(sheet, rowIndex, 0, year);
//						Toolket.setCellValue(sheet, rowIndex, 1, g
//								.getStudentNo());
//						Toolket.setCellValue(sheet, rowIndex, 2, g
//								.getStudentName());
//						tmpClassNo = StringUtils.substring(g.getDepartClass(),
//								0, 4)
//								+ (Integer.parseInt(StringUtils.substring(g
//										.getDepartClass(), 4, 5)) - 1)
//								+ StringUtils.substring(g.getDepartClass(), 5);
//						Toolket.setCellValue(sheet, rowIndex, 3, Toolket
//								.getClassFullName(tmpClassNo));
//						Toolket.setCellValue(sheet, rowIndex, 4, Toolket
//								.getSex(g.getSex()));
//
//						inves = new Investigation();
//						inves.setStudentNo(g.getStudentNo().trim()
//								.toUpperCase());
//						example = Example.create(inves).ignoreCase()
//								.enableLike(MatchMode.START);
//						inveses = (List<Investigation>) am.findSQLWithCriteria(
//								Investigation.class, example, null, null);
//						if (!inveses.isEmpty()) {
//							inves = inveses.get(0);
//							Toolket.setCellValue(sheet, rowIndex, 5,
//									("0".equals(inves.getForeignOrNot()) ? "國內"
//											: "1".equals(inves
//													.getForeignOrNot()) ? "國外"
//													: ""));
//							Toolket.setCellValue(sheet, rowIndex, 6, inves
//									.getSchoolName());
//							Toolket.setCellValue(sheet, rowIndex, 7, inves
//									.getCanpus());
//							Toolket.setCellValue(sheet, rowIndex, 8, inves
//									.getLevel());
//
//							Toolket.setCellValue(sheet, rowIndex, 9, inves
//									.getCompany());
//							Toolket.setCellValue(sheet, rowIndex, 10, inves
//									.getForeignOrNot());
//							Toolket
//									.setCellValue(
//											sheet,
//											rowIndex,
//											11,
//											StringUtils
//													.defaultIfEmpty(
//															inves
//																	.getCompanyPhone(),
//															"").trim());
//							Toolket.setCellValue(sheet, rowIndex, 12,
//									StringUtils.defaultIfEmpty(
//											inves.getBossName(), "").trim());
//							Toolket.setCellValue(sheet, rowIndex, 13,
//									StringUtils.defaultIfEmpty(
//											inves.getBossEmail(), "").trim());
//							Toolket.setCellValue(sheet, rowIndex, 14,
//									StringUtils.defaultIfEmpty(
//											inves.getBossAddr(), "").trim());
//							Toolket.setCellValue(sheet, rowIndex, 15, Toolket
//									.getWorkNature(inves.getWorkNature()));
//							Toolket.setCellValue(sheet, rowIndex, 16, Toolket
//									.getWorkDuty(inves.getWorkDuty()));
//							Toolket.setCellValue(sheet, rowIndex, 17, inves
//									.getWorkTitle());
//							Toolket.setCellValue(sheet, rowIndex, 18, (inves
//									.getWorkStart() == null ? "" : df
//									.format(inves.getWorkStart())));
//							Toolket.setCellValue(sheet, rowIndex, 19, Toolket
//									.getSalaryRange(inves.getSalaryRange()));
//							Toolket.setCellValue(sheet, rowIndex, 20, Toolket
//									.getArmyType(inves.getArmyType()));
//							Toolket.setCellValue(sheet, rowIndex, 21,
//									StringUtils.defaultIfEmpty(
//											inves.getArmyName(), "").trim());
//							Toolket.setCellValue(sheet, rowIndex, 22,
//									StringUtils.defaultIfEmpty(
//											inves.getArmyLevel(), "").trim());
//						}
//
//						Toolket.setCellValue(sheet, rowIndex, 23, g.getEmail());
//						Toolket.setCellValue(sheet, rowIndex, 24, g
//								.getCellPhone());
//						Toolket.setCellValue(sheet, rowIndex, 25, g
//								.getTelephone());
//						Toolket.setCellValue(sheet, rowIndex, 26, g
//								.getCurrPost());
//						Toolket.setCellValue(sheet, rowIndex, 27, g
//								.getCurrAddr());
//						rowIndex++;
//					}
				}
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "GstmdList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
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
	private void printGstmdList4Ntnu(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String thisYear = cm.getNowBy("School_year");
		List<Clazz> clazzes = sm.findClassBy(new Clazz(processClassInfo(form)),
				getUserCredential(session).getClassInChargeAry(), false);

		String departClass = null;
		List<Student> students = null;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		if (!clazzes.isEmpty()) {
			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/GstmdList4Ntnu.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);
			workbook.setSheetName(0, thisYear + "學年度應屆畢業生欄位");

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("標楷體");

			int index = 2;

			for (Clazz clazz : clazzes) {
				departClass = clazz.getClassNo();
				if (Toolket.isGraduateClass(departClass)
						|| Toolket.isDelayClass(departClass)) {

					students = mm.findStudentsByClassNo(departClass);
					for (Student student : students) {
						Toolket.setCellValue(sheet, index, 0, "1061");
						Toolket.setCellValue(sheet, index, 1, student
								.getStudentNo());
						Toolket.setCellValue(sheet, index, 2, Toolket
								.getDeptCode4Ntnu(student.getDepartClass()));
						Toolket.setCellValue(sheet, index, 3, Toolket
								.getDepartName(student.getDepartClass()));
						Toolket
								.setCellValue(sheet, index, 4, student
										.getDivi());
						Toolket.setCellValue(sheet, index, 5, Toolket
								.getClassFullName(student.getDepartClass()));
						Toolket.setCellValue(sheet, index, 6, Toolket
								.getCampCode4Ntnu(student.getDepartClass()));
						Toolket.setCellValue(sheet, index, 7, student
								.getStudentName());
						Toolket
								.setCellValue(sheet, index, 8, student
										.getIdno());
						Toolket.setCellValue(sheet, index, 9, df.format(student
								.getBirthday()));
						Toolket
								.setCellValue(sheet, index, 10, student
										.getSex());
						Toolket.setCellValue(sheet, index, 11, StringUtils
								.isBlank(student.getEmail()) ? Toolket
								.getDefaultStudentEmail(student, "") : student
								.getEmail());
						Toolket.setCellValue(sheet, index, 12, student
								.getCurrPost());
						Toolket.setCellValue(sheet, index, 13, student
								.getCurrAddr());
						Toolket.setCellValue(sheet, index, 14, "3"
								.equals(student.getIdent())
								|| "E".equals(student.getIdent()) ? "1" : "0");
						Toolket.setCellValue(sheet, index, 15, "5"
								.equals(student.getIdent()) ? "2" : "1");
						Toolket.setCellValue(sheet, index, 16, "0");
						Toolket.setCellValue(sheet, index++, 17, Toolket
								.isDelayClass(student.getDepartClass()) ? "1"
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
