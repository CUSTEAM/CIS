package tw.edu.chit.struts.action.secretary;

import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.LicenseCode;
import tw.edu.chit.model.LifeCounseling;
import tw.edu.chit.model.StdOpinionSuggestion;
import tw.edu.chit.model.StdSkill;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.TeacherStayTime;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.util.AuthenticationException;

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
		setContentPage(session, "secretary/ReportPrint.jsp");
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
		if (StringUtils.isNotBlank(request.getParameter("sd"))
				|| StringUtils.isNotBlank(request.getParameter("ed"))) {
			aForm.set("licenseValidDateStart", request.getParameter("sd"));
			aForm.set("licenseValidDateEnd", request.getParameter("ed"));
		}

		if (StringUtils.isNotBlank(request.getParameter("sd1"))
				|| StringUtils.isNotBlank(request.getParameter("ed1"))) {
			aForm.set("licenseValidDateStart", request.getParameter("sd1"));
			aForm.set("licenseValidDateEnd", request.getParameter("ed1"));
		}

		String printOpt = (String) aForm.get("printOpt");
		String sterm = (String) aForm.get("sterm");
		request.getSession(false).setMaxInactiveInterval(-1);

		if ("StayTimeList".equals(printOpt)) {
			// 專任教師留校時間清單
			printStayTimeList(mapping, aForm, request, response, sterm);
		} else if ("LicenseCodes".equals(printOpt)) {
			// 報部證照代碼對照表
			printLicenseCodes(mapping, aForm, request, response, sterm);
		} else if ("StdSkillList".equals(printOpt)) {
			// 證照資料列印
			printStdSkillList(mapping, aForm, request, response, sterm);
		} else if ("StdSkillList-1".equals(printOpt)) {
			// 系所證照統計列印
			printStdSkill1List(mapping, aForm, request, response, sterm);
		} else if ("CalendarList".equals(printOpt)) {
			// 行事曆列印
			printCalendarList(mapping, aForm, request, response, sterm);
		} else if ("OpinionList".equals(printOpt)) {
			// 學生意見反映統計列印
			printOpinionList(mapping, aForm, request, response, sterm);
		} else if ("OpinionDetailList".equals(printOpt)) {
			// 學生意見反映明細列印
			printOpinionDetailList(mapping, aForm, request, response, sterm);
		}

	}

	/**
	 * 列印專任教師留校時間清單
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
		ServletContext context = request.getSession().getServletContext();
		String year = cm.getNowBy("School_year");
		String term = form.getString("sterm");

		List<Empl> empls = mm.findAllTeacher(term);
		if (!empls.isEmpty()) {

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/TeachSchedAll.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("標楷體");

			int sheetIndex = 0;
			int colOffset = 1, col = 0;
			boolean isLocationNull = false;
			HSSFSheet sheet = null;
			List<Map> map = null;
			List<TeacherStayTime> tsts = null;
			List<LifeCounseling> lcs = null;
			Map content = null;
			Short colorForStayTime = HSSFColor.AUTOMATIC.index;
			Short colorForLifeCounseling = HSSFColor.LIGHT_GREEN.index;

			for (Empl empl : empls) {

				if ("1".equalsIgnoreCase(empl.getCategory())) {

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
	 * 報部證照代碼對照表
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
		List<LicenseCode> codes = (List<LicenseCode>) am.findSQLWithCriteria(
				LicenseCode.class, example, null, null);

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

		File output = new File(tempDir, "StdSkillList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 系所證照統計列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printStdSkill1List(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();

		String hql = "SELECT COUNT(*), s.deptNo FROM StdSkill s "
				+ "GROUP BY s.deptNo ORDER BY s.deptNo";

		List<Object> skills = (List<Object>) am.find(hql, null);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("系所證照統計列印");
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 3000);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

		HSSFFont fontSize16 = workbook.createFont();
		fontSize16.setFontHeightInPoints((short) 16);
		fontSize16.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "系所證照統計列印", fontSize16,
				HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "系所名稱", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "證照張數", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);

		Object[] obj = null;
		int index = 2;

		for (Object o : skills) {

			obj = (Object[]) o;
			Toolket.setCellValue(workbook, sheet, index, 0, "0"
					.equals(((String) obj[1])) ? "語言中心" : Toolket
					.getDept((String) obj[1]), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, index++, 1,
					((Integer) obj[0]).toString(), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "StdSkill1List.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 行事曆列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printCalendarList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		ServletContext context = request.getSession().getServletContext();
		ActionMessages messages = new ActionMessages();

		Date from = null, to = null;
		if (StringUtils.isNotBlank(aForm.getString("licenseValidDateStart"))
				|| StringUtils.isNotBlank(aForm
						.getString("licenseValidDateEnd"))) {
			from = StringUtils
					.isBlank(aForm.getString("licenseValidDateStart")) ? null
					: Toolket.parseNativeDate(aForm
							.getString("licenseValidDateStart"));
			// 沒結束就預設今天
			to = StringUtils.isBlank(aForm.getString("licenseValidDateEnd")) ? Calendar
					.getInstance().getTime()
					: Toolket.parseNativeDate(aForm
							.getString("licenseValidDateEnd"));
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(to);
		cal.add(Calendar.DAY_OF_MONTH, 1);

		try {
			IConstants.GOOGLE_SERVICES.setUserCredentials(
					IConstants.GOOGLE_EMAIL_USERNAME,
					IConstants.GOOGLE_EMAIL_PASSWORD);
		} catch (AuthenticationException ae) {
			log.error(ae.getMessage(), ae);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "遠端認證資訊錯誤,請稍後再試,或電洽電算中心人員,謝謝!"));
			saveMessages(request, messages);
		}

		DateTime rangeFrom = Toolket.parseDateToGoogleDateTime(from);
		DateTime rangeTo = Toolket.parseDateToGoogleDateTime(cal.getTime());

		CalendarEventEntry[] entries = am.findCalendarEventBy(
				IConstants.GOOGLE_SERVICES, rangeFrom, rangeTo);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("秘書室行事曆");
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 8000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 8000);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

		HSSFFont fontSize16 = workbook.createFont();
		fontSize16.setFontHeightInPoints((short) 16);
		fontSize16.setFontName("Arial Unicode MS");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, "秘書室行事曆", fontSize16,
				HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "開會日期", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "開會時間", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "主持人", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "事項", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "地點", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 5, "承辦單位", fontSize10,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		int index = 2;

		List<ExtendedProperty> props = null;
		boolean flag = false;
		DateFormat dt = new SimpleDateFormat("kk:mm");
		DateTime d = null;
		Calendar c = null;

		if (entries.length != 0) {
			for (CalendarEventEntry entry : entries) {

				props = entry.getExtendedProperty();
				flag = false;
				for (ExtendedProperty prop : props) {
					if ("host".equalsIgnoreCase(prop.getName())) {
						Toolket.setCellValue(workbook, sheet, index, 2,
								StringUtils.trimToEmpty(prop.getValue()),
								fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
								null);
						flag = true;
					}
				}

				if (!flag)
					Toolket.setCellValue(workbook, sheet, index, 2, "",
							fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);

				d = entry.getTimes().get(0).getStartTime();
				c = Calendar.getInstance();
				c.setTimeInMillis(d.getValue());
				Toolket.setCellValue(workbook, sheet, index, 0, Toolket
						.printNativeDate(c.getTime()), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, dt.format(c
						.getTime()), fontSize10, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, index, 3, entry
						.getTitle().getPlainText(), fontSize10,
						HSSFCellStyle.ALIGN_LEFT, true, null);
				Toolket.setCellValue(workbook, sheet, index, 4, StringUtils
						.defaultIfEmpty(entry.getLocations().get(0)
								.getValueString(), ""), fontSize10,
						HSSFCellStyle.ALIGN_LEFT, true, null);
				if (StringUtils.contains(entry.getPlainTextContent(), "主持人"))
					Toolket.setCellValue(workbook, sheet, index++, 5,
							StringUtils.substringAfter(entry
									.getPlainTextContent().trim(), "\n"),
							fontSize10, HSSFCellStyle.ALIGN_LEFT, true, null);
				else
					Toolket.setCellValue(workbook, sheet, index++, 5, entry
							.getPlainTextContent().trim(), fontSize10,
							HSSFCellStyle.ALIGN_LEFT, true, null);

			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "CalendarList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 學生意見反映統計列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printOpinionList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		ServletContext context = request.getSession().getServletContext();

		String year = aForm.getStrings("year")[0];
		String term = aForm.getString("sterm");

		String hql = "SELECT COUNT(*), s.target FROM StdOpinionSuggestion s "
				+ "WHERE s.schoolYear = ? AND s.schoolTerm = ? GROUP BY s.target";
		List<Object> ret = (List<Object>) am.find(hql, new Object[] { year,
				term });

		if (!ret.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("學生意見反映統計");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 2000);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("Arial Unicode MS");

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("Arial Unicode MS");

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "學生意見反映統計", fontSize16,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "學年", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "學期", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "單位", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "筆數", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;
			Object[] obj = null;
			for (Object o : ret) {
				obj = (Object[]) o;

				Toolket.setCellValue(workbook, sheet, index, 0, year,
						fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, term,
						fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 2, Toolket
						.getOpinionDeptName((String) obj[1]), fontSize10,
						HSSFCellStyle.ALIGN_LEFT, true, null);
				Toolket.setCellValue(workbook, sheet, index++, 3,
						((Integer) obj[0]).toString(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "OpinionList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}

	}

	/**
	 * 學生意見反映明細列印
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printOpinionDetailList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		ServletContext context = request.getSession().getServletContext();

		String year = aForm.getStrings("year")[0];
		String term = aForm.getString("sterm");
		String deptCode = request.getParameter("odc");

		List<StdOpinionSuggestion> ret = null;
		if (StringUtils.isNotBlank(deptCode)) {
			String hql = "FROM StdOpinionSuggestion s "
					+ "WHERE s.schoolYear = ? AND s.schoolTerm = ? "
					+ "AND s.target = ? ORDER BY s.lastModified";
			ret = (List<StdOpinionSuggestion>) am.find(hql, new Object[] {
					year, term, deptCode });
		} else {
			String hql = "FROM StdOpinionSuggestion s "
					+ "WHERE s.schoolYear = ? AND s.schoolTerm = ? "
					+ "ORDER BY s.lastModified";
			ret = (List<StdOpinionSuggestion>) am.find(hql, new Object[] {
					year, term });
		}

		if (!ret.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("學生意見反映明細");
			sheet.setColumnWidth(0, 2500);
			sheet.setColumnWidth(1, 2500);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 6000);
			sheet.setColumnWidth(5, 6000);
			sheet.setColumnWidth(6, 3000);
			sheet.setColumnWidth(7, 3000);
			sheet.setColumnWidth(8, 12000);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("Arial Unicode MS");

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("Arial Unicode MS");

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "學生意見反映明細", fontSize16,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "學年", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "學期", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "反映單位", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "學生姓名", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "學生Email", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "反映主旨", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 6, "反映地點", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 7, "反映時間", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 8, "反映內容", fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;
			Student student = null;
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm");

			for (StdOpinionSuggestion s : ret) {

				Toolket.setCellValue(workbook, sheet, index, 0, year,
						fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, term,
						fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 2, Toolket
						.getOpinionDeptName(s.getTarget()), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);

				student = mm.findStudentByOid(s.getStudentOid());
				Toolket.setCellValue(workbook, sheet, index, 3,
						student == null ? "" : student.getStudentName(),
						fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 4, s.getEmail(),
						fontSize10, HSSFCellStyle.ALIGN_LEFT, true, null);
				Toolket.setCellValue(workbook, sheet, index, 5, s.getTopic(),
						fontSize10, HSSFCellStyle.ALIGN_LEFT, true, null);
				Toolket.setCellValue(workbook, sheet, index, 6, s.getPlace(),
						fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 7, df.format(s
						.getLastModified()), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index++, 8, s
						.getContent(), fontSize10, HSSFCellStyle.ALIGN_LEFT,
						true, null);
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "OpinionDetailList.xls");
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
