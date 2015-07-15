package tw.edu.chit.struts.action.studaffair;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Example;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.RegistrationCard;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
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
		session.setMaxInactiveInterval(-1);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", sterm);
		setContentPage(session, "studaffair/ReportPrint.jsp");
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

		if ("ArmyList1".equals(printOpt)) {
			// 儘後召集第二款申請處理名冊
			printArmyList1(mapping, aForm, request, response, sterm);
		} else if ("ArmyList2".equals(printOpt)) {
			// 儘後召集第二款延長修業申請處理名冊
			printArmyList2(mapping, aForm, request, response, sterm);
		} else if ("ArmyList3".equals(printOpt)) {
			// 儘後召集第二款原因消滅名冊
			printArmyList3(mapping, aForm, request, response, sterm);
		} else if ("ArmyList4".equals(printOpt)) {
			// 離校學生緩徵原因消滅名冊
			printArmyList4(mapping, aForm, request, response, sterm);
		} else if ("ArmyList5".equals(printOpt)) {
			// 申請緩徵學生名冊
			printArmyList5(mapping, aForm, request, response, sterm);
		} else if ("ArmyList6".equals(printOpt)) {
			// 延長修業年限學生名冊
			printArmyList6(mapping, aForm, request, response, sterm);
		} else if ("ArmyList7".equals(printOpt)) {
			// 僑生離校名冊
			printArmyList7(mapping, aForm, request, response, sterm);
		}

	}

	/**
	 * 儘後召集第二款申請處理名冊
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printArmyList1(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");

		List<Clazz> clazzes = sm.findClassBy(
				new Clazz(processClassInfo(aForm)), credential
						.getClassInChargeAry(), false);
		if (!clazzes.isEmpty()) {

			List<Student> students = null;
			List<Code5> code5s = null;
			RegistrationCard card = null;
			Code5 code5 = new Code5();
			code5.setCategory("GradYear");
			Example example = null;
			int index = 7, no = 1, gradeYear = 0;

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/ArmyList1.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);

			Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + term
					+ "學期儘後召集第二款申請處理名冊");

			HSSFFont font10InRed = workbook.createFont(); // 異常
			font10InRed.setFontHeightInPoints((short) 10);
			font10InRed.setFontName("Arial Unicode MS");
			font10InRed.setColor(HSSFColor.RED.index);

			for (Clazz clazz : clazzes) {
				if (!Toolket.isLiteracyClass(clazz.getClassNo())) {
					students = mm.findStudentsByClassNo(clazz.getClassNo());
					if (students.isEmpty())
						continue;

					// 計算預計畢業年
					code5.setIdno(clazz.getSchoolNo());
					example = Example.create(code5);
					code5s = (List<Code5>) am.findSQLWithCriteria(Code5.class,
							example, null, null);

					if (!Toolket.isDelayClass(clazz.getClassNo()))
						gradeYear = Integer.parseInt(year)
								+ Integer.parseInt(code5s.get(0).getName())
								- Integer.parseInt(clazz.getGrade());
					else
						gradeYear = Integer.parseInt(year);

					for (Student student : students) {
						card = student.getRegistrationCard();
						// 應該還有ArmyType,但都沒填
						if (card != null
								&& (card.getArmyIn() != null || card
										.getArmyOut() != null)) {

							Toolket.setCellValue(sheet, index, 0, String
									.valueOf(no++)
									+ "\n"
									+ student.getStudentNo().toUpperCase()
											.trim()
									+ "\n"
									+ Toolket.Date2Str(student.getBirthday())
											.replaceAll("-", "/"));
							Toolket.setCellValue(sheet, index, 1, student
									.getIdno().toUpperCase().trim()
									+ "\n"
									+ student.getStudentName().trim()
									+ "\n" + " ");
							Toolket.setCellValue(sheet, index, 2, "");
							Toolket.setCellValue(sheet, index, 3, StringUtils
									.defaultIfEmpty(card.getBeforeSchool(), "")
									+ "\n"
									+ StringUtils.defaultIfEmpty(card
											.getBeforeDept(), "")
									+ "\n"
									+ StringUtils.defaultIfEmpty(card
											.getGradeYear(), ""));
							Toolket.setCellValue(sheet, index, 4, Toolket
									.getClassFullName(student.getDepartClass())
									+ "\n"
									+ clazz.getGrade()
									+ "\n"
									+ student.getEntrance());
							Toolket.setCellValue(sheet, index, 5, code5s.get(0)
									.getName()
									+ "\n"
									+ ""
									+ "\n"
									+ (student.getEntrance() == null ? ""
											: gradeYear + "/6/30"));

							index++;
						}
					}
				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new java.util.Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "ArmyList1.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 儘後召集第二款延長修業申請處理名冊
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printArmyList2(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/ArmyList2.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + term
				+ "學期儘後召集第二款延長修業申請處理名冊");

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd")
						.format(new java.util.Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "ArmyList2.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 儘後召集第二款原因消滅名冊
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printArmyList3(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/ArmyList3.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + term
				+ "學期儘後召集第二款原因消滅名冊");

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd")
						.format(new java.util.Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "ArmyList3.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 離校學生緩徵原因消滅名冊
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printArmyList4(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/ArmyList4.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + term
				+ "學期離校學生緩徵原因消滅名冊");

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd")
						.format(new java.util.Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "ArmyList4.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 申請緩徵學生名冊
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printArmyList5(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");
		DynaActionForm aForm = (DynaActionForm) form;

		List<Clazz> clazzes = sm.findClassBy(
				new Clazz(processClassInfo(aForm)), credential
						.getClassInChargeAry(), false);
		if (!clazzes.isEmpty()) {

			List<Student> students = null;
			List<Code5> code5s = null;
			RegistrationCard card = null;
			Code5 code5 = new Code5();
			code5.setCategory("GradYear");
			Example example = null;
			int index = 3, gradeYear = 0;

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/ArmyList5.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);

			Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + term
					+ "學期申請緩徵學生名冊");

			HSSFFont font10InRed = workbook.createFont(); // 異常
			font10InRed.setFontHeightInPoints((short) 10);
			font10InRed.setFontName("Arial Unicode MS");
			font10InRed.setColor(HSSFColor.RED.index);

			for (Clazz clazz : clazzes) {
				if (!Toolket.isLiteracyClass(clazz.getClassNo())) {
					students = mm.findStudentsByClassNo(clazz.getClassNo());
					if (students.isEmpty())
						continue;

					// 計算預計畢業年
					code5.setIdno(clazz.getSchoolNo());
					example = Example.create(code5);
					code5s = (List<Code5>) am.findSQLWithCriteria(Code5.class,
							example, null, null);

					if (!Toolket.isDelayClass(clazz.getClassNo()))
						gradeYear = Integer.parseInt(year)
								+ Integer.parseInt(code5s.get(0).getName())
								- Integer.parseInt(clazz.getGrade());
					else
						gradeYear = Integer.parseInt(year);

					for (Student student : students) {

						if ("1".equals(student.getSex())) {

							// 男生且是沒當兵
							card = student.getRegistrationCard();
							if (card != null && card.getArmyIn() == null
									&& card.getArmyOut() == null) {

								Toolket.setCellValue(sheet, index, 0, Toolket
										.getClassFullName(student
												.getDepartClass()));
								Toolket.setCellValue(sheet, index, 1, student
										.getStudentNo().toUpperCase().trim());
								Toolket.setCellValue(sheet, index, 2, student
										.getStudentName().trim());
								Toolket.setCellValue(sheet, index, 3, student
										.getIdno().toUpperCase().trim());
								Toolket.setCellValue(sheet, index, 4, Toolket
										.Date2Str(student.getBirthday())
										.replaceAll("-", "/"));
								Toolket.setCellValue(sheet, index, 5, "");
								Toolket.setCellValue(sheet, index, 6, Toolket
										.getSchoolName(clazz.getClassNo()));
								Toolket.setCellValue(sheet, index, 7, String
										.valueOf(student.getEntrance()));
								Toolket.setCellValue(sheet, index++, 8,
										gradeYear + "/6/30");
							} else {
								Toolket.setCellValue(sheet, index, 0, Toolket
										.getClassFullName(student
												.getDepartClass()));
								Toolket.setCellValue(sheet, index, 1, student
										.getStudentNo().toUpperCase().trim());
								Toolket.setCellValue(sheet, index, 2, student
										.getStudentName().trim());
								Toolket.setCellValue(sheet, index, 3, student
										.getIdno().toUpperCase().trim());
								Toolket.setCellValue(sheet, index, 4, Toolket
										.Date2Str(student.getBirthday())
										.replaceAll("-", "/"));
								Toolket.setCellValue(sheet, index, 5, "");
								Toolket.setCellValue(sheet, index, 6, Toolket
										.getSchoolName(clazz.getClassNo()));
								Toolket.setCellValue(sheet, index, 7, String
										.valueOf(student.getEntrance()));
								Toolket.setCellValue(sheet, index++, 8,
										gradeYear + "/6/30");
							}
						}
					}
				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new java.util.Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "ArmyList5.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 延長修業年限學生名冊
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printArmyList6(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/ArmyList6.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + term
				+ "學期延長修業年限學生名冊");

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd")
						.format(new java.util.Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "ArmyList6.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 僑生離校名冊
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printArmyList7(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/ArmyList7.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + term
				+ "學期僑生離校名冊");

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd")
						.format(new java.util.Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "ArmyList7.xls");
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

	// 沒再用了
	String calGradeYear(int grade, Short entrance) {
		String tmpEntrance = String.valueOf(entrance);
		tmpEntrance = tmpEntrance.length() == 4 ? StringUtils.substring(
				tmpEntrance, 0, 2) : StringUtils.substring(tmpEntrance, 0, 3);
		return String.valueOf(grade + Integer.parseInt(tmpEntrance) - 1);
	}

}
