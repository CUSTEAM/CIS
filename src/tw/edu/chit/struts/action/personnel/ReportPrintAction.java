package tw.edu.chit.struts.action.personnel;

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
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import tw.edu.chit.model.CodeEmpl;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.LifeCounseling;
import tw.edu.chit.model.TeacherStayTime;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
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
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		List<CodeEmpl> codeEmpls = mm.findAllUnit();
		String[] unitCodes = new String[0];
		String[] unitNames = new String[0];
		for (CodeEmpl codeEmpl : codeEmpls) {
			unitCodes = (String[]) ArrayUtils.add(unitCodes, codeEmpl.getIdno()
					.trim());
			unitNames = (String[]) ArrayUtils.add(unitNames, codeEmpl.getName()
					.trim());
		}
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", sterm);
		aForm.set("unitCodes", unitCodes);
		aForm.set("unitNames", unitNames);
		setContentPage(session, "personnel/ReportPrint.jsp");
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
		aForm.set("unitCode", request.getParameter("uc"));
		aForm.set("dayCode", request.getParameter("dc"));
		aForm.set("nodeCode", request.getParameter("nc"));
		aForm.set("month", request.getParameter("mn"));
		String printOpt = aForm.getString("printOpt");
		String sterm = (String) aForm.get("sterm");
		request.getSession(false).setMaxInactiveInterval(-1);

		if ("NoneStayTimeList".equals(printOpt)) {
			// 專任教師未填寫留校時間清單
			printNoneStayTimeList(mapping, aForm, request, response, sterm);
		} else if ("EmpRetire4BankList".equals(printOpt)) {
			// 教職員退休撫恤檔案
			printEmpRetire4BankList(mapping, aForm, request, response, sterm);
		} else if ("StayTimeList".equals(printOpt)) {
			// 專任教師留校時間清單
			printStayTimeList(mapping, aForm, request, response, sterm);
		} else if ("StayTimeListReport".equals(printOpt)) {
			// 專任教師留校時間表
			printStayTimeListReport(mapping, aForm, request, response, sterm);
		} else if ("StayTimeOverCounts".equals(printOpt)) {
			// 專任教師變更留校時間次數清單
			printStayTimeOverCounts(mapping, aForm, request, response, sterm);
		} else if ("EmpBirthList".equals(printOpt)) {
			// 教職員工慶生名單
			printEmpBirthList(mapping, aForm, request, response, sterm);
		}
	}

	/**
	 * 列印專任教師未填寫留校時間清單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printNoneStayTimeList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = cm.getNowBy("School_year");
		String term = form.getString("sterm");

		List<Empl> empls = mm.findAllTeacher(term);
		if (!empls.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();

			HSSFFont fontSize14 = workbook.createFont();
			fontSize14.setFontHeightInPoints((short) 14);
			fontSize14.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFSheet sheet = workbook.createSheet("未填寫留校時間教師清單");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 8000);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
			Toolket.setCellValue(workbook, sheet, 0, 0, year + "學年度第" + term
					+ "學期專任教師未填寫留校時間清單", fontSize14,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 0, "教師姓名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "職稱", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "行動電話", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "Email", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			int index = 2;
			for (Empl empl : empls) {				
				// Leo20120313 修正如下 empl.getStayTime() 無法執行，所以用myList取代之  =============>>>>>>>>>>>>>>
				List myList = cm.ezGetBy(
						"Select * From TeacherStayTime Where parentOid = '"+empl.getOid().toString().trim()+"' " +
						"And SchoolYear ='"+year+"' And SchoolTerm = '"+term+"'");
				//if ("1".equalsIgnoreCase(empl.getCategory())
				//		&& empl.getStayTime().isEmpty()) {
				if ("1".equalsIgnoreCase(empl.getCategory())
						&& myList.isEmpty()) {
					Toolket.setCellValue(workbook, sheet, index, 0, empl
							.getCname(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 1, empl
							.getSname(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 2, empl
							.getTelephone(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index++, 3, empl
							.getEmail(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
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
			fontSize12.setFontName("Arial Unicode MS");

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
			String unitCode = form.getString("unitCode");

			for (Empl empl : empls) {

				if (!"%".equalsIgnoreCase(unitCode)
						&& !empl.getUnit().equals(unitCode))
					continue;

				// 專任
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

					// empl.getStayTime() 無法執行，註解掉的地方為原程式碼，其他修改如下  Leo_20120301
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
					//-------------------------------------------------------------------------------

					// if (sheetIndex % 100 == 0)
					// break;
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
	 * 列印專任教師留校時間表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printStayTimeListReport(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = cm.getNowBy("School_year");
		String term = form.getString("sterm");
		Short dayCode = Short.valueOf(form.getString("dayCode"));
		String nodeCode = form.getString("nodeCode");

		TeacherStayTime stayTime = new TeacherStayTime();
		stayTime.setSchoolYear(year);
		stayTime.setSchoolTerm(term);
		stayTime.setWeek(dayCode);

		if (StringUtils.isNotBlank(nodeCode)) {
			if ("1".equals(nodeCode))
				stayTime.setNode1((short) 1);
			else if ("2".equals(nodeCode))
				stayTime.setNode2((short) 1);
			else if ("3".equals(nodeCode))
				stayTime.setNode3((short) 1);
			else if ("4".equals(nodeCode))
				stayTime.setNode4((short) 1);
			else if ("5".equals(nodeCode))
				stayTime.setNode5((short) 1);
			else if ("6".equals(nodeCode))
				stayTime.setNode6((short) 1);
			else if ("7".equals(nodeCode))
				stayTime.setNode7((short) 1);
			else if ("8".equals(nodeCode))
				stayTime.setNode8((short) 1);
			else if ("9".equals(nodeCode))
				stayTime.setNode9((short) 1);
			else if ("10".equals(nodeCode))
				stayTime.setNode10((short) 1);
			else if ("N1".equals(nodeCode))
				stayTime.setNode11((short) 1);
			else if ("N2".equals(nodeCode))
				stayTime.setNode12((short) 1);
			else if ("N3".equals(nodeCode))
				stayTime.setNode13((short) 1);
			else if ("N4".equals(nodeCode))
				stayTime.setNode14((short) 1);
		}

		Example example = Example.create(stayTime).ignoreCase().enableLike(
				MatchMode.START);
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("pos"));
		List<TeacherStayTime> times = (List<TeacherStayTime>) am
				.findSQLWithCriteria(TeacherStayTime.class, example, null,
						orders);

		if (!times.isEmpty()) {
			HSSFWorkbook workbook = new HSSFWorkbook();

			HSSFFont fontSize14 = workbook.createFont();
			fontSize14.setFontHeightInPoints((short) 14);
			fontSize14.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFSheet sheet = workbook.createSheet("專任教師留校時間表");
			sheet.setColumnWidth(0, 4000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 6000);
			sheet.setColumnWidth(5, 8000);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
			Toolket.setCellValue(workbook, sheet, 0, 0, year + "學年度第" + term
					+ "學期專任教師留校節次表 (星期" + getDayValue(dayCode) + ")",
					fontSize14, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 0, "單位", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "姓名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "分機", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "辦公室位置", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "留校節次", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "其他留校日", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);

			int index = 2;
			Empl empl = null;
			StringBuilder builder = null;
			for (TeacherStayTime time : times) {
				empl = time.getEmpl();
				
				if (empl != null) {
					try{
						Toolket.setCellValue(workbook, sheet, index, 0, Toolket
								.getEmpUnit(empl.getUnit()), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 1, empl
								.getCname(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
						
						
						Toolket.setCellValue(workbook, sheet, index, 2, empl.getLocation().getExtension(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 3, empl
								.getLocation().getRoomId(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 4,
								getNodeValue(time), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
						
					}catch(Exception e){
						continue;
					}

					builder = new StringBuilder();
					
					List<TeacherStayTime> myTsts= cm.ezGetBy(
							" Select Week, Node1, Node2, Node3, Node4, Node5, Node6, Node7, Node8, Node9, Node10, " +
							"        Node11, Node12, Node13, Node14 " +
							" From TeacherStayTime "+
							" Where SchoolYear='"+ year +"'" +
							"   And SchoolTerm='"+ term +"' " +
							"   And parentOid='"+ empl.getOid() +"'");	
					
					//for (TeacherStayTime t : empl.getStayTime())
					// 原迴圈中的empl.getStayTime() 無法執行，故而修改如下 Leo_20120301					
					List myTsts2 = new ArrayList();
					for(int i=0; i<myTsts.size();i++){
						myTsts2.add(myTsts.get(i));
					    String s = myTsts2.get(i).toString();
						builder.append(getDayValue((short) Integer.parseInt(s.substring(6, 7)))).append(",");
					}
					//--------------------------------------------------------

					Toolket.setCellValue(workbook, sheet, index++, 5, "星期"
							+ StringUtils.substring(builder.toString(), 0,
									builder.toString().length() - 1),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, false,
							35.0F, null);
					
				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "StayTimeListReport.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 列印教職員退休撫恤檔案
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printEmpRetire4BankList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);

		Empl e = new Empl();
		Example example = Example.create(e).ignoreCase();
		List<Order> orders = new ArrayList<Order>();
		Order o = Order.asc("category");
		orders.add(o);
		// 排除兼任教師及雇員(包括排除方案與專案雇員)
		Criterion in = Restrictions.in("category",
				new Object[] { "1", "3", "4" });
		Criterion notIn = Restrictions.not(Restrictions.in("pcode",
				new Object[] { "60", "601", "602" }));
		List<Empl> empls = (List<Empl>) am.findSQLWithCriteria(Empl.class,
				example, null, orders, in, notIn);

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/EmpRetire4BankList.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);

		int index = 2;
		for (Empl empl : empls) {

			Toolket.setCellValue(sheet, index, 0, empl.getIdno().toUpperCase()
					.trim());
			Toolket.setCellValue(sheet, index, 1, empl.getCname().trim());
			Toolket.setCellValue(sheet, index, 2, StringUtils.leftPad(Toolket
					.Date2Str1(empl.getBdate()), 7, '0'));
			Toolket.setCellValue(sheet, index, 3, StringUtils.defaultIfEmpty(
					empl.getCzip(), ""));
			Toolket.setCellValue(sheet, index, 4, empl.getCaddr().trim());
			Toolket.setCellValue(sheet, index, 5, StringUtils.defaultIfEmpty(
					empl.getTelephone(), ""));
			Toolket.setCellValue(sheet, index, 6, "");
			Toolket.setCellValue(sheet, index, 7, StringUtils.defaultIfEmpty(
					empl.getCellPhone().replaceAll("-", ""), ""));
			Toolket.setCellValue(sheet, index, 8, StringUtils.defaultIfEmpty(
					empl.getEmail(), ""));
			Toolket.setCellValue(sheet, index++, 9, "");
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "EmpRetire4BankList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 列印專任教師變更留校時間次數清單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printStayTimeOverCounts(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String year = cm.getNowBy("School_year");
		String term = form.getString("sterm");
		CourseManager manager = (CourseManager) getBean("courseManager");

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
		int counts = StringUtils.isBlank(request.getParameter("stcc")) ? 0
				: Integer.parseInt(request.getParameter("stcc"));

		List<Empl> empls = mm.findAllTeacher(term);

		if (!empls.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();

			HSSFFont fontSize14 = workbook.createFont();
			fontSize14.setFontHeightInPoints((short) 14);
			fontSize14.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFSheet sheet = workbook.createSheet("學期專任教師變更留校時間次數清單");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 5000);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
			Toolket.setCellValue(workbook, sheet, 0, 0, year + "學年度第" + term
					+ "學期專任教師變更留校時間次數清單", fontSize14,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 0, "姓名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "單位", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "職稱", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "更新次數", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "最後更新時間", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			int index = 2;
			for (Empl empl : empls) {
				if ("1".equalsIgnoreCase(empl.getCategory())) {
					int cnt = manager.ezGetInt("SELECT COUNT(*) FROM TeacherStayTimeModify WHERE ParentOid='"+empl.getOid()+"' AND SchoolYear='"+year+"' AND SchoolTerm='"+term+"'");
					//if (empl.getModify().size() >= counts) {
					if (cnt >= counts) {
						Toolket.setCellValue(workbook, sheet, index, 0, empl
								.getCname(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 1, Toolket
								.getEmpUnit(empl.getUnit()), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 2, empl
								.getSname(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 3, 
								//String.valueOf(empl.getModify().size()), fontSize12,
								String.valueOf(cnt), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
						String lastdate = manager.ezGetString("SELECT LastModified FROM TeacherStayTimeModify WHERE ParentOid='"+empl.getOid()+"' AND SchoolYear='"+year+"' AND SchoolTerm='"+term+"' ORDER BY LastModified DESC LIMIT 1");
						//if (!empl.getModify().isEmpty()) {
						if(!lastdate.equals("")){
							Toolket.setCellValue(workbook, sheet, index++, 4,
									//df.format(empl.getModify().iterator()
									//		.next().getLastModified()),
									String.valueOf(lastdate),
									fontSize12, HSSFCellStyle.ALIGN_CENTER,
									false, 35.0F, null);
						} else
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

			File output = new File(tempDir, "RegisterList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}

	}

	/**
	 * 列印教職員工慶生名單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printEmpBirthList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String year = cm.getNowBy("School_year");
		String term = form.getString("sterm");
		int selMonth = Integer.parseInt(form.getString("month"));

		List<Empl> empls = mm.findEmplsBy(new Empl());

		if (!empls.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();

			HSSFFont fontSize14 = workbook.createFont();
			fontSize14.setFontHeightInPoints((short) 14);
			fontSize14.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFSheet sheet = workbook.createSheet("教職員工慶生名單");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 3000);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
			Toolket.setCellValue(workbook, sheet, 0, 0, year + "學年度第" + term
					+ "學期教職員工慶生名單", fontSize14, HSSFCellStyle.ALIGN_CENTER,
					false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 0, "姓名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "單位", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "職稱", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "出生月日", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);

			int index = 2, month = -1;
			Calendar cal = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("MM/dd");
			for (Empl empl : empls) {

				// 排除兼差教師
				if (!"2".equals(empl.getCategory())) {
					cal.setTime(empl.getBdate());
					month = cal.get(Calendar.MONTH);
					if (selMonth == month) {
						Toolket.setCellValue(workbook, sheet, index, 0, empl
								.getCname(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 1, Toolket
								.getEmpUnit(empl.getUnit()), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 2, empl
								.getSname(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
						Toolket.setCellValue(workbook, sheet, index++, 3, df
								.format(empl.getBdate()), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
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

			File output = new File(tempDir, "EmpBirthList.xls");
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

	public String getNodeValue(TeacherStayTime stayTime) {
		StringBuilder builder = new StringBuilder();
		if (stayTime.getNode1() == 1)
			builder.append("1,");
		if (stayTime.getNode2() == 1)
			builder.append("2,");
		if (stayTime.getNode3() == 1)
			builder.append("3,");
		if (stayTime.getNode4() == 1)
			builder.append("4,");
		if (stayTime.getNode5() == 1)
			builder.append("5,");
		if (stayTime.getNode6() == 1)
			builder.append("6,");
		if (stayTime.getNode7() == 1)
			builder.append("7,");
		if (stayTime.getNode8() == 1)
			builder.append("8,");
		if (stayTime.getNode9() == 1)
			builder.append("9,");
		if (stayTime.getNode10() == 1)
			builder.append("10,");
		if (stayTime.getNode11() == 1)
			builder.append("夜1,");
		if (stayTime.getNode12() == 1)
			builder.append("夜2,");
		if (stayTime.getNode13() == 1)
			builder.append("夜3,");
		if (stayTime.getNode14() == 1)
			builder.append("夜4,");

		return StringUtils.substring(builder.toString(), 0, builder.toString()
				.length() - 1);
	}

	private String getDayValue(Short dayCode) {
		if (null == dayCode)
			return "";

		switch (dayCode) {
			case 1:
				return "一";

			case 2:
				return "二";

			case 3:
				return "三";

			case 4:
				return "四";

			case 5:
				return "五";

			case 6:
				return "六";

			case 7:
				return "日";

			default:
				return "";
		}
	}
}
