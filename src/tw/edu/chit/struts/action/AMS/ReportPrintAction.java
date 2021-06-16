package tw.edu.chit.struts.action.AMS;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang3.ArrayUtils;
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
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import tw.edu.chit.model.AmsDocApply;
import tw.edu.chit.model.AmsMeeting;
import tw.edu.chit.model.AmsMeetingAskLeave;
import tw.edu.chit.model.AmsPersonalVacation;
import tw.edu.chit.model.AmsShiftTime;
import tw.edu.chit.model.AmsWorkdateData;
import tw.edu.chit.model.AmsWorkdateInfo;
import tw.edu.chit.model.CodeEmpl;
import tw.edu.chit.model.Empl;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.AmsManager;
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
		session.setMaxInactiveInterval(-1);
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
		setContentPage(session, "AMS/ReportPrint.jsp");
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
		aForm.set("startDate", request.getParameter("sd"));
		aForm.set("endDate", request.getParameter("ed"));
		aForm.set("unitCode", request.getParameter("uc"));
		aForm.set("cname2", request.getParameter("cnm").toUpperCase());
		aForm.set("conditionCode", request.getParameter("cc"));
		aForm.set("conditionTimes", request.getParameter("ct"));
		String printOpt = aForm.getString("printOpt");
		String sterm = (String) aForm.get("sterm");
		request.getSession(false).setMaxInactiveInterval(-1);

		if ("AmsWorkdataList".equals(printOpt)) {
			// 刷卡紀錄清單
			printAmsWorkdataList(mapping, aForm, request, response, sterm);
		} else if ("AmsWorkdateList".equals(printOpt)) {
			// 應上班人員班表
			printAmsWorkdateList(mapping, aForm, request, response, sterm);
		} else if ("DocApplyList".equals(printOpt)) {
			// 請假紀錄清單
			printDocApplyList(mapping, aForm, request, response, sterm);
		} else if ("MeetingDocApplyList".equals(printOpt)) {
			// 重要集會請假紀錄清單
			printMeetingDocApplyList(mapping, aForm, request, response, sterm);
		} else if ("EmplWorkdateList".equals(printOpt)) {
			// 員工請假紀錄
			printEmplWorkdateList(mapping, aForm, request, response, sterm);
		} else if ("EmplWorkdateListE".equals(printOpt)) {
			// 員工差勤統計
			printEmplWorkdateListE(mapping, aForm, request, response, sterm);
		} else if ("EmplAmsWorkdataList".equals(printOpt)) {
			// 個人刷卡紀錄清單
			printEmplAmsWorkdataList(mapping, aForm, request, response, sterm);
		} else if ("QueryByList".equals(printOpt)) {
			// 查核報表
			printQueryByList(mapping, aForm, request, response, sterm);
		} else if ("VacationList".equals(printOpt)) {
			// 休假統計表
			printVacationList(mapping, aForm, request, response, sterm);
		}
	}

	/**
	 * 刷卡紀錄清單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printAmsWorkdataList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		AmsManager ams = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd kk:mm", Locale.TAIWAN);
		DateFormat dt = new SimpleDateFormat("kk:mm", Locale.TAIWAN);
		Date from = Toolket.parseNativeDate(form.getString("startDate"));
		Date to = Toolket.parseNativeDate(form.getString("endDate"));
		String unitCode = form.getString("unitCode");

		AmsWorkdateData aw = new AmsWorkdateData();
		// 由ResultSet轉成DynaBean
		List<DynaBean> beans = ams.findAmsWorkdateBy(aw, from, to);

		if (!beans.isEmpty()) {

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/AmsWorkdataList.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);

			HSSFFont font10InRed = workbook.createFont(); // 異常
			font10InRed.setFontHeightInPoints((short) 10);
			font10InRed.setFontName("Arial Unicode MS");
			font10InRed.setColor(HSSFColor.RED.index);

			Toolket.setCellValue(sheet, 0, 0, year + "學年度第" + term + "學期"
					+ df.format(from) + "至" + df.format(to) + "刷卡紀錄清單");

			int index = 2;
			Empl empl = null;
			String idno = null;
			StringBuffer buffer = null;
			Time setIn = null, setOut = null, realIn = null, realOut = null;
			AmsDocApply amsDocApply = null;
			List<AmsDocApply> docApplys = null;

			for (DynaBean bean : beans) {

				if (idno == null) {
					idno = (String) bean.get("idno");
					empl = mm.findEmplByIdno(idno);
				} else if (!idno.equalsIgnoreCase((String) bean.get("idno"))) {
					idno = (String) bean.get("idno");
					empl = mm.findEmplByIdno(idno);
				}

				if (empl != null) {
					amsDocApply = new AmsDocApply();
					amsDocApply.setIdno(empl.getIdno().trim());
					docApplys = ams.findAmsDocApplyBy(amsDocApply, (Date) bean
							.get("wdate"));
				}

				if ("A".equalsIgnoreCase(unitCode)
						|| (empl != null && unitCode.equalsIgnoreCase(empl
								.getUnit()))) {
					// 以下只是為印專任與全體軍護職員工用的
					// if ("A".equalsIgnoreCase(unitCode)
					// || (empl != null && (empl.getCategory() != null && ("1"
					// .equalsIgnoreCase(empl.getCategory())
					// || "3".equalsIgnoreCase(empl.getCategory()) || "4"
					// .equals(empl.getCategory()))))) {

					if (empl != null) {
						Toolket.setCellValue(sheet, index, 0, Toolket
								.getEmpUnit(StringUtils.defaultIfEmpty(empl
										.getUnit(), "")));
						Toolket.setCellValue(sheet, index, 1, empl.getCname());
						Toolket.setCellValue(sheet, index, 2, df
								.format((Date) bean.get("wdate")));
						Toolket.setCellValue(sheet, index, 3, ((String) bean
								.get("date_type")).equalsIgnoreCase("w") ? "上班"
								: "非上班");
						setIn = StringUtils
								.isBlank((String) bean.get("set_in")) ? null
								: new java.sql.Time(dt.parse(
										(String) bean.get("set_in")).getTime());
						if (setIn != null)
							setIn.setTime(setIn.getTime()
									+ (IConstants.AMS_SET_IN_RANGE * 60 * 1000)
									+ (59 * 1000)); // 要以08:21才算遲到
						setOut = StringUtils.isBlank((String) bean
								.get("set_out")) ? null : new java.sql.Time(dt
								.parse((String) bean.get("set_out")).getTime());

						if (setOut != null)
							setOut
									.setTime(setOut.getTime()
											+ (IConstants.AMS_SET_OUT_RANGE * 60 * 1000));

						realIn = StringUtils.isBlank((String) bean
								.get("real_in")) ? null : new java.sql.Time(dt
								.parse((String) bean.get("real_in")).getTime());
						realOut = StringUtils.isBlank((String) bean
								.get("real_out")) ? null
								: new java.sql.Time(dt.parse(
										(String) bean.get("real_out"))
										.getTime());

						Toolket.setCellValue(sheet, index, 4, (String) bean
								.get("set_in"));
						Toolket.setCellValue(sheet, index, 5, (String) bean
								.get("set_out"));

						if (setIn != null && realIn != null
								&& realIn.after(setIn)) {
							Toolket.setCellValue(workbook, sheet, index, 6,
									(String) bean.get("real_in"), font10InRed,
									HSSFCellStyle.ALIGN_CENTER, false, null);
						} else {
							Toolket.setCellValue(sheet, index, 6, (String) bean
									.get("real_in"));
						}

						if (setOut != null && realOut != null
								&& setOut.after(realOut)) {
							Toolket.setCellValue(workbook, sheet, index, 7,
									(String) bean.get("real_out"), font10InRed,
									HSSFCellStyle.ALIGN_CENTER, false, null);
						} else {
							Toolket.setCellValue(sheet, index, 7, (String) bean
									.get("real_out"));
						}

						Toolket.setCellValue(sheet, index, 8,
								"ai".equalsIgnoreCase((String) bean
										.get("aitype")) ? "是" : "");
						if (docApplys != null && !docApplys.isEmpty()) {
							buffer = new StringBuffer();
							for (AmsDocApply ada : docApplys) {
								buffer.append(
										Toolket.getAmsAskLeave(ada
												.getAskLeaveType())).append(
										df1.format(ada.getStartDate())).append(
										"~").append(
										df1.format(ada.getEndDate())).append(
										", ");
							}
							Toolket.setCellValue(sheet, index, 9, buffer
									.toString());
						} else {
							if (IConstants.AMS_NO.equals(empl.getWorkShift())) {
								Toolket.setCellValue(sheet, index, 9, "");
							} else {
								if (((String) bean.get("date_type"))
										.equalsIgnoreCase("w")
										&& realIn == null && realOut == null) {
									Toolket.setCellValue(workbook, sheet,
											index, 9, "曠職", font10InRed,
											HSSFCellStyle.ALIGN_LEFT, false,
											null);
								}
							}
						}

						index++;
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

			File output = new File(tempDir, "AmsWorkdataList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();

		}
	}

	/**
	 * 列印應上班人員班表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printAmsWorkdateList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		AmsManager ams = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		List<AmsShiftTime> asts = ams.findAmsShiftTimeBy(new AmsShiftTime());

		String[] needToWork = new String[0]; // 要刷卡班別
		Map<String, AmsShiftTime> map = new HashMap<String, AmsShiftTime>();
		for (AmsShiftTime ast : asts) {
			if (ast.getIn1() != null || ast.getIn2() != null
					|| ast.getIn3() != null || ast.getIn4() != null
					|| ast.getIn5() != null || ast.getIn6() != null
					|| ast.getIn7() != null) {
				needToWork = (String[]) ArrayUtils.add(needToWork, ast.getId());
				map.put(ast.getId(), ast);
			}
		}

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFFont fontSize14 = workbook.createFont();
		fontSize14.setFontHeightInPoints((short) 14);
		fontSize14.setFontName("Arial Unicode MS");

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFSheet sheet = workbook.createSheet("應上班人員列表");
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 5000);
		sheet.setColumnWidth(8, 5000);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
		Toolket.setCellValue(workbook, sheet, 0, 0, "應上班人員列表清單", fontSize14,
				HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 0, "姓名", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "單位", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "職稱", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "班別", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "起始日", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 5, "截止日", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 6, "應上時間", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 7, "應下時間", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 8, "備註", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
		DateFormat dt = new SimpleDateFormat("kk:mm", Locale.TAIWAN);
		List<Empl> empls = mm.findEmplByWorkShift(needToWork);
		List<AmsWorkdateInfo> awi = null;
		List<AmsShiftTime> shiftTimes = null;
		SimpleExpression expression = null;
		String begin = "", end = "";
		int index = 2;
		for (Empl empl : empls) {

			awi = ams.findAmsWorkdateDataBy(empl, year);
			if (!awi.isEmpty()) {
				begin = df.format(awi.get(0).getWdate());
				end = df.format(awi.get(awi.size() - 1).getWdate());
			}

			expression = Restrictions.eq("id", empl.getWorkShift().trim());
			shiftTimes = (List<AmsShiftTime>) ams.findSQLWithCriteria(
					AmsShiftTime.class, expression);
			Toolket.setCellValue(workbook, sheet, index, 0, empl.getCname()
					+ " " + empl.getWorkShift(), fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, index, 1, Toolket
					.getEmpUnit(empl.getUnit()), fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, index, 2, empl.getSname(),
					fontSize12, HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, index, 3, shiftTimes
					.isEmpty() ? "" : shiftTimes.get(0).getName().trim(),
					fontSize12, HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, index, 4, begin, fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, index, 5, end, fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, index, 6, map.get(
					empl.getWorkShift()).getIn1() == null ? "" : dt.format(map
					.get(empl.getWorkShift()).getIn1()), fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, index, 7, map.get(
					empl.getWorkShift()).getOut1() == null ? "" : dt.format(map
					.get(empl.getWorkShift()).getOut1()), fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, index++, 8, "", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd")
						.format(new java.util.Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "AmsWorkdateList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 請假紀錄清單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printDocApplyList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		AmsManager ams = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date from = Toolket.parseNativeDate(form.getString("startDate"));
		Date to = Toolket.parseNativeDate(form.getString("endDate"));

		List<AmsDocApply> docs = ams.findAmsDocApplyBy(from, to);

		if (!docs.isEmpty()) {

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/AmsDocApplyList.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);

			HSSFFont fontSize14 = workbook.createFont();
			fontSize14.setFontHeightInPoints((short) 14);
			fontSize14.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
			Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學" + year
					+ "學年度第" + term + "學期" + df.format(from) + "至"
					+ df.format(to) + "請假紀錄清單", fontSize14,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			int index = 2;
			String totalDay = null;
			Empl empl = null;
			for (AmsDocApply doc : docs) {

				try{
					empl = mm.findEmplByIdno(doc.getIdno().toUpperCase().trim());
				}catch(Exception ex) {
					
				}
				if (empl != null) {
					Toolket.setCellValue(workbook, sheet, index, 0, empl
							.getCname().trim(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 1, Toolket
							.getDocType(doc.getDocType().trim()), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 2, Toolket
							.getAmsAskLeave(doc.getAskLeaveType()), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					
					
					try{
						Toolket.setCellValue(workbook, sheet, index, 3, doc.getReason().trim().toUpperCase().trim(),fontSize12, HSSFCellStyle.ALIGN_LEFT, true, 35.0F,null);
					}catch(Exception ex) {
						Toolket.setCellValue(workbook, sheet, index, 3, "",fontSize12, HSSFCellStyle.ALIGN_LEFT, true, 35.0F,null);
					}
					
					
					
					Toolket.setCellValue(workbook, sheet, index, 4, doc
							.getStartDate() == null ? "" : df1.format(doc
							.getStartDate()), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 5, doc
							.getEndDate() == null ? "" : df1.format(doc
							.getEndDate()), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					totalDay = doc.getTotalDay() == null ? "" : doc
							.getTotalDay().toString()
							+ "天";
					totalDay += doc.getTotalHour() == null ? "" : doc
							.getTotalHour().toString()
							+ "小時";
					totalDay += doc.getTotalMinute() == null ? "" : doc
							.getTotalMinute().toString()
							+ "分";
					Toolket.setCellValue(workbook, sheet, index, 6, totalDay,
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 7, doc
							.getTeachPeriod() == null ? "" : doc
							.getTeachPeriod().toString(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					empl = doc.getAgent() == null ? null
							: mm.findEmplByIdno(doc.getAgent().toUpperCase()
									.trim());
					Toolket.setCellValue(workbook, sheet, index, 8,
							empl == null ? "" : empl.getCname(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 9, Toolket
							.getDocProcess(doc.getStatus()), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 10, doc
							.getSn(), fontSize12, HSSFCellStyle.ALIGN_CENTER,
							true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 11, doc
							.getCreateDate() == null ? "" : df1.format(doc
							.getCreateDate()), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index++, 12, doc
							.getMemo() == null ? "" : doc.getMemo(),
							fontSize12, HSSFCellStyle.ALIGN_LEFT, true, 35.0F,
							null);
				}

			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new java.util.Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "AmsDocApplyList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 重要集會請假紀錄清單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printMeetingDocApplyList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		AmsManager ams = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date from = Toolket.parseNativeDate(form.getString("startDate"));
		Date to = Toolket.parseNativeDate(form.getString("endDate"));

		List<AmsMeeting> meetings = ams.findAmsMeetingsBy(from, to);

		if (!meetings.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();

			HSSFFont fontSize14 = workbook.createFont();
			fontSize14.setFontHeightInPoints((short) 14);
			fontSize14.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFSheet sheet = workbook.createSheet("重要集會請假紀錄清單");
			sheet.setColumnWidth(0, 8000);
			sheet.setColumnWidth(1, 2500);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 6000);
			sheet.setColumnWidth(5, 2500);
			sheet.setColumnWidth(6, 12000);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
			Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學" + year
					+ "學年度第" + term + "學期" + df.format(from) + "至"
					+ df.format(to) + "重要集會請假紀錄清單", fontSize14,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 0, "重要集會名稱", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "請假人", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "假別", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "請假單號", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "申請日期", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "審查狀態", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 6, "事由", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);

			int index = 2;
			Empl empl = null;
			AmsMeetingAskLeave a = null;
			List<AmsMeetingAskLeave> askLeaves = null;

			for (AmsMeeting meeting : meetings) {
				a = new AmsMeetingAskLeave();
				a.setMeetingOid(meeting.getOid());
				askLeaves = ams.findAmsMeetingAskLeavesBy(a);

				for (AmsMeetingAskLeave askLeave : askLeaves) {

					Toolket.setCellValue(workbook, sheet, index, 0, meeting
							.getName().trim(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					empl = mm.findEmplByIdno(askLeave.getUserIdno()
							.toUpperCase().trim());
					if (empl != null) {
						Toolket.setCellValue(workbook, sheet, index, 1, empl
								.getCname().trim(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 2, Toolket
								.getAmsAskLeave(askLeave.getAskleaveId()),
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								35.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 3,
								askLeave.getAskCode(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 4,
								askLeave.getProcessDate() == null ? "" : df1
										.format(askLeave.getProcessDate()),
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								35.0F, null);
						Toolket
								.setCellValue(workbook, sheet, index, 5,
										StringUtils.isBlank(askLeave
												.getStatus()) ? ""
												: ("1".equals(askLeave
														.getStatus()) ? "核准"
														: "未核准"), fontSize12,
										HSSFCellStyle.ALIGN_CENTER, true,
										35.0F, null);
						Toolket.setCellValue(workbook, sheet, index++, 6,
								StringUtils.defaultIfEmpty(askLeave.getTemp()
										.trim(), ""), fontSize12,
								HSSFCellStyle.ALIGN_LEFT, true, 35.0F, null);
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

			File output = new File(tempDir, "AmsDocApplyList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 員工請假紀錄
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printEmplWorkdateList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		AmsManager amsm = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
		Date from = Toolket.parseNativeDate(form.getString("startDate"));
		Date to = Toolket.parseNativeDate(form.getString("endDate"));

		Empl empl = new Empl();
		empl.setUnit("A".equalsIgnoreCase(form.getString("unitCode")) ? null
				: form.getString("unitCode"));
		List<Empl> empls = mm.findEmplsBy(empl);
		if (!empls.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();

			HSSFFont fontSize14 = workbook.createFont();
			fontSize14.setFontHeightInPoints((short) 14);
			fontSize14.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFSheet sheet = null;
			Map<String, Object> ret = null;
			List<AmsDocApply> applys = null;
			List<AmsMeeting> meetings = null;
			List<AmsMeetingAskLeave> meetingAskLeaves = null;
			AmsWorkdateData aw = null;
			AmsMeetingAskLeave meetingAskLeave = null;
			int meetingHours = 0;
			Example example = null;
			AmsDocApply apply = null;
			Empl agentEmpl = null;
			Criterion between = Restrictions.between("startDate", from, to);
			StringBuffer buffer = null;

			for (Empl e : empls) {

				aw = new AmsWorkdateData();
				aw.setIdno(e.getIdno().toUpperCase().trim());
				ret = amsm.findAmsWorkdateDataBy(e, aw, from, to, Integer
						.parseInt(year));
				if (!ret.isEmpty()) {
					sheet = workbook.createSheet(e.getCname().trim() + "("
							+ e.getUnit() + ")");

					sheet.setColumnWidth(0, 3000);
					sheet.setColumnWidth(1, 2000);
					sheet.setColumnWidth(2, 6000);
					sheet.setColumnWidth(3, 6000);
					sheet.setColumnWidth(4, 10000);
					sheet.setColumnWidth(5, 4000);
					sheet.setColumnWidth(6, 1500);
					sheet.setColumnWidth(7, 1500);
					sheet.setColumnWidth(8, 1500);
					sheet.setColumnWidth(9, 1500);
					sheet.setColumnWidth(10, 6000);
					sheet.setColumnWidth(11, 6000);
					sheet.setColumnWidth(12, 4000);

					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
					// Header
					Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學" + year
							+ "學年度第" + term + "學期員工請假紀錄表 (" + df1.format(from)
							+ " ~ " + df1.format(to) + ")", fontSize14,
							HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

					buffer = new StringBuffer();
					buffer.append("遲到次數 : ").append(ret.get("delayCounts"))
							.append(" 次     早退次數 : ").append(
									ret.get("earlyCounts")).append(
									" 次      遲到與早退扣分 : ").append(
									ret.get("delayEarlysScore")).append(" 分");
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
					Toolket.setCellValue(workbook, sheet, 1, 0, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					// buffer.append("遲到次數 : ").append(ret.get("delayCounts"))
					// .append(" 次     早退次數 : ").append(
					// ret.get("earlyCounts")).append(
					// " 次      遲到與早退扣分 : ").append(
					// ret.get("delayEarlysScore")).append(" 分");
					sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
					Toolket.setCellValue(workbook, sheet, 2, 0, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("事假次數 : ").append(ret.get("businessCounts"))
							.append(" 次     事假時數 : ").append(
									ret.get("businessTotalHours")).append(
									"   事假扣分 : ").append(
									ret.get("businessCountsScore"))
							.append(" 分");
					sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));
					Toolket.setCellValue(workbook, sheet, 3, 0, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("病假次數 : ").append(ret.get("sickCounts"))
							.append(" 次     病假時數 : ").append(
									ret.get("sickTotalHours")).append(
									"   病假扣分 : ").append(
									ret.get("sickCountsScore")).append(" 分");
					sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
					Toolket.setCellValue(workbook, sheet, 4, 0, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("生理假次數 : ")
							.append(ret.get("womenSickCounts")).append(
									" 次     生理假時數 : ").append(
									ret.get("womenSickTotalHours")).append(
									"   生理假扣分 : ").append(
									ret.get("womenSickCountsScore")).append(
									" 分");
					sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));
					Toolket.setCellValue(workbook, sheet, 5, 0, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("公差假次數 : ").append(ret.get("leave3")).append(
							" 次     公差假時數 : ").append(ret.get("leave3Hours"));
					sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));
					Toolket.setCellValue(workbook, sheet, 6, 0, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("婚假次數 : ").append(ret.get("leave4")).append(
							" 次     婚假時數 : ").append(ret.get("leave4Hours"));
					sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 5));
					Toolket.setCellValue(workbook, sheet, 7, 0, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("產假次數 : ").append(ret.get("leave5")).append(
							" 次     產假時數 : ").append(ret.get("leave5Hours"));
					sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 5));
					Toolket.setCellValue(workbook, sheet, 8, 0, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("喪假次數 : ").append(ret.get("leave6")).append(
							" 次     喪假時數 : ").append(ret.get("leave6Hours"));
					sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 5));
					Toolket.setCellValue(workbook, sheet, 9, 0, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("陪產假次數 : ").append(ret.get("leave10"))
							.append(" 次     陪產假時數 : ").append(
									ret.get("leave10Hours"));
					sheet.addMergedRegion(new CellRangeAddress(10, 10, 0, 5));
					Toolket.setCellValue(workbook, sheet, 10, 0, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("差勤扣分總計 : ").append(ret.get("totalScores"))
							.append(" 分");
					sheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 5));
					Toolket.setCellValue(workbook, sheet, 11, 0, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("無出勤紀錄次數 : ").append(
							ret.get("noRecordCounts")).append(
							" 次    無出勤紀錄扣分 : ").append(
							ret.get("noRecordCountsScore")).append(" 分");
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 12));
					Toolket.setCellValue(workbook, sheet, 1, 6, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					// buffer.append("遲到次數 : ").append(ret.get("delayCounts"))
					// .append(" 次     早退次數 : ").append(
					// ret.get("earlyCounts")).append(
					// " 次      遲到與早退扣分 : ").append(
					// ret.get("delayEarlysScore")).append(" 分");
					sheet.addMergedRegion(new CellRangeAddress(2, 2, 6, 12));
					Toolket.setCellValue(workbook, sheet, 2, 0, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("補登次數 : ").append(
							ret.get("amsDocApplyCounts")).append(" 次");
					sheet.addMergedRegion(new CellRangeAddress(3, 3, 6, 12));
					Toolket.setCellValue(workbook, sheet, 3, 6, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("年假天數 : ").append(ret.get("yearVacation"))
							.append(" 天     年假剩餘天數 : ").append(
									ret.get("yearVacationRemain")).append(" 天");
					sheet.addMergedRegion(new CellRangeAddress(4, 4, 6, 12));
					Toolket.setCellValue(workbook, sheet, 4, 6, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("特休假天數 : ")
							.append(ret.get("specialVacation")).append(
									" 天     特休假剩餘天數 : ").append(
									ret.get("specialVacationRemain")).append(
									" 天");
					sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 12));
					Toolket.setCellValue(workbook, sheet, 5, 6, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("年假次數 : ").append(ret.get("leave7")).append(
							" 天     年假時數 : ").append(ret.get("leave7Hours"));
					sheet.addMergedRegion(new CellRangeAddress(6, 6, 6, 12));
					Toolket.setCellValue(workbook, sheet, 6, 6, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("補休假次數 : ").append(ret.get("leave9")).append(
							" 天     補休假時數 : ").append(ret.get("leave9Hours"));
					sheet.addMergedRegion(new CellRangeAddress(7, 7, 6, 12));
					Toolket.setCellValue(workbook, sheet, 7, 6, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("特休次數 : ").append(ret.get("leave12")).append(
							" 天     特休時數 : ").append(ret.get("leave12Hours"));
					sheet.addMergedRegion(new CellRangeAddress(8, 8, 6, 12));
					Toolket.setCellValue(workbook, sheet, 8, 6, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					buffer = new StringBuffer();
					buffer.append("產前假次數 : ").append(ret.get("leave13"))
							.append(" 天     產前假時數 : ").append(
									ret.get("leave13Hours"));
					sheet.addMergedRegion(new CellRangeAddress(9, 9, 6, 12));
					Toolket.setCellValue(workbook, sheet, 9, 6, buffer
							.toString(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
							false, 25.0F, null);

					// Columns
					Toolket.setCellValue(workbook, sheet, 13, 0, "類型",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 13, 1, "假別",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 13, 2, "開始日期",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 13, 3, "結束日期",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 13, 4, "請假事由",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 13, 5, "代理人",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 13, 6, "天",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 13, 7, "時",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 13, 8, "時",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 13, 9, "節數",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 13, 10, "建立日期",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 13, 11, "處理日期",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, 13, 12, "處理狀態",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);

					apply = new AmsDocApply();
					apply.setIdno(e.getIdno().toUpperCase().trim());
					example = Example.create(apply).ignoreCase().enableLike(
							MatchMode.ANYWHERE);

					applys = amsm.findSQLWithCriteria(AmsDocApply.class,
							example, between);
					int index = 14;

					for (AmsDocApply a : applys) {

						Toolket.setCellValue(workbook, sheet, index, 0, Toolket
								.getAmsDocType(a.getDocType()), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 1, Toolket
								.getAmsAskLeave(a.getAskLeaveType()),
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								25.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 2, a
								.getStartDate() == null ? "" : df.format(a
								.getStartDate()), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 3, a
								.getEndDate() == null ? "" : df.format(a
								.getEndDate()), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
						
						try{
							Toolket.setCellValue(workbook, sheet, index, 4, a.getReason().trim(), fontSize12,HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
						}catch(Exception ex) {
							Toolket.setCellValue(workbook, sheet, index, 4, a.getReason().trim(), fontSize12,HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
						}
						
						try {
							agentEmpl = mm.findEmplByIdno(a.getAgent().toUpperCase().trim());
						}catch(Exception ex) {
							agentEmpl=new Empl();
						}
						
						
						
						
						Toolket.setCellValue(workbook, sheet, index, 5,
								agentEmpl == null ? "" : agentEmpl.getCname(),
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								25.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 6, a
								.getTotalDay().toString(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 7, a
								.getTotalHour().toString(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 8, a
								.getTotalMinute().toString(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 9, a
								.getTeachPeriod() == null ? "" : a
								.getTeachPeriod().toString(), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 10, df
								.format(a.getCreateDate()), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
						Toolket.setCellValue(workbook, sheet, index, 11, a
								.getProcessDate() == null ? "" : df.format(a
								.getProcessDate()), fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
						Toolket.setCellValue(workbook, sheet, index++, 12,
								Toolket.getDocProcess(a.getStatus()),
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								25.0F, null);
					}

					meetings = amsm.findAmsMeetingsBy(from, to);
					if (!meetings.isEmpty()) {

						meetingAskLeave = new AmsMeetingAskLeave();
						meetingAskLeave.setUserIdno(e.getIdno().toUpperCase()
								.trim());
						for (AmsMeeting m : meetings) {

							meetingHours = m.getEndNode() - m.getStartNode()
									+ 1;
							meetingAskLeave.setMeetingOid(m.getOid());
							meetingAskLeaves = amsm
									.findAmsMeetingAskLeavesBy(meetingAskLeave);
							if (!meetingAskLeaves.isEmpty()) {
								for (AmsMeetingAskLeave amal : meetingAskLeaves) {
									Toolket.setCellValue(workbook, sheet,
											index, 0, "請假", fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											25.0F, null);
									Toolket.setCellValue(workbook, sheet,
											index, 1, Toolket
													.getAmsAskLeave(amal
															.getAskleaveId()),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											25.0F, null);
									Toolket.setCellValue(workbook, sheet,
											index, 2, df.format(m
													.getMeetingDate()),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											25.0F, null);
									Toolket.setCellValue(workbook, sheet,
											index, 3, df.format(m
													.getMeetingDate()),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											25.0F, null);
									Toolket.setCellValue(workbook, sheet,
											index, 4, amal.getTemp().trim(),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											25.0F, null);
									Toolket.setCellValue(workbook, sheet,
											index, 5, "", fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											25.0F, null);
									Toolket.setCellValue(workbook, sheet,
											index, 6, "", fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											25.0F, null);
									Toolket.setCellValue(workbook, sheet,
											index, 7, String
													.valueOf(meetingHours),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											25.0F, null);
									Toolket.setCellValue(workbook, sheet,
											index, 8, "", fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											25.0F, null);
									Toolket.setCellValue(workbook, sheet,
											index, 9, "", fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											25.0F, null);
									Toolket.setCellValue(workbook, sheet,
											index, 10,
											amal.getProcessDate() == null ? ""
													: df.format(amal
															.getProcessDate()),
											fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											25.0F, null);
									Toolket.setCellValue(workbook, sheet,
											index++, 11, "0".equals(amal
													.getStatus()) ? "未核准"
													: "已核准", fontSize12,
											HSSFCellStyle.ALIGN_CENTER, true,
											25.0F, null);
								}
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

			File output = new File(tempDir, "EmplWorkdateList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 員工差勤統計
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printEmplWorkdateListE(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		AmsManager amsm = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");
		DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
		Date from = Toolket.parseNativeDate(form.getString("startDate"));
		
		String emplCategory=request.getParameter("cg");
		
		Date to = Toolket.parseNativeDate(form.getString("endDate"));

		Empl empl = new Empl();
		empl.setUnit("A".equalsIgnoreCase(form.getString("unitCode")) ? null
				: form.getString("unitCode"));
		
		
		//empl.setCategory(category);
		
		List<Empl> empls = mm.findEmplsBy(empl);
		File tempDir = new File(context
				.getRealPath("/WEB-INF/reports/temp/"
						+ getUserCredential(session).getMember().getIdno()
						+ (new SimpleDateFormat("yyyyMMdd")
								.format(new java.util.Date()))));
		
		if (!empls.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();

			HSSFFont fontSize14 = workbook.createFont();
			fontSize14.setFontHeightInPoints((short) 14);
			fontSize14.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFSheet sheet = workbook.createSheet("員工差勤統計");
			sheet.protectSheet(IConstants.SHEET_PASSWORD);

			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 4000);
			sheet.setColumnWidth(2, 3000);
			sheet.setColumnWidth(3, 3700);
			sheet.setColumnWidth(4, 3700);
			sheet.setColumnWidth(5, 3000);
			sheet.setColumnWidth(6, 3000);
			sheet.setColumnWidth(7, 3000);
			sheet.setColumnWidth(8, 3000);
			sheet.setColumnWidth(9, 3000);
			sheet.setColumnWidth(10, 3000);
			sheet.setColumnWidth(11, 3000);
			sheet.setColumnWidth(12, 4000);
			sheet.setColumnWidth(13, 3000);
			sheet.setColumnWidth(14, 3000);
			sheet.setColumnWidth(15, 3000);
			sheet.setColumnWidth(16, 3000);
			sheet.setColumnWidth(17, 3000);
			sheet.setColumnWidth(18, 3000);
			sheet.setColumnWidth(19, 5000);
			sheet.setColumnWidth(20, 5000);
			sheet.setColumnWidth(21, 5500);
			sheet.setColumnWidth(22, 5500);
			sheet.setColumnWidth(23, 3000);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 22));
			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學" + year
					+ "學年度第" + term + "學期員工差勤統計表 (" + df1.format(from) + " ~ "
					+ df1.format(to) + ")", fontSize14,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			// Columns
			Toolket.setCellValue(workbook, sheet, 1, 0, "單位", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "職稱", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "姓名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "身分證字號", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "逾時(次)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "遲到(次)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 6, "早退(次)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 7, "曠職(時)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 8, "事假(時)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 9, "病假(時)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 10, "生理假(時)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 11, "補登(次)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 12, "重要會議(次)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 13, "婚假(日)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 14, "喪假(日)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 15, "產假(日)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 16, "產前假(日)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 17, "陪產假(日)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 18, "公假(時)", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 19, "重要會議事假(時)",
					fontSize12, HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 20, "重要會議病假(時)",
					fontSize12, HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 21, "重要會議生理假(時)",
					fontSize12, HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 22, "假單未處理次數(次)",
					fontSize12, HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 23, "差勤扣分", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);

			Map<String, Object> ret = null;
			AmsWorkdateData aw = null;
			int index = 2;

			for (Empl e : empls) {
				if(!e.getCategory().equals(emplCategory))continue;//非指定範圍不統計，否則會timeout
				aw = new AmsWorkdateData();
				aw.setIdno(e.getIdno().toUpperCase().trim());
				ret = amsm.findAmsWorkdateDataBy(e, aw, from, to, Integer
						.parseInt(year));
				if (!ret.isEmpty()) {

					Toolket.setCellValue(workbook, sheet, index, 0, Toolket
							.getEmpUnit(e.getUnit()), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 1, e
							.getSname().trim(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 2, e
							.getCname(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 3, e.getIdno()
							.toUpperCase().trim(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 4,
							((Integer) ret.get("betweenCounts")).toString(),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 5,
							((Integer) ret.get("delayCounts")).toString(),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					
					Toolket.setCellValue(workbook, sheet, index, 6,
							((Integer) ret.get("earlyCounts")).toString(),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 7,
							String.valueOf(((Integer) ret.get("noRecordCounts")) * 8),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 8,
							((Float) ret.get("businessTotalHours")).toString(),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 9,
							((Float) ret.get("sickTotalHours")).toString(),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket
							.setCellValue(workbook, sheet, index, 10,
									((Float) ret.get("womenSickTotalHours"))
											.toString(), fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, 35.0F,
									null);
					Toolket.setCellValue(workbook, sheet, index, 11,
									((Integer) ret.get("amsDocApplyCounts"))
											.toString(), fontSize12,
									HSSFCellStyle.ALIGN_CENTER, true, 35.0F,
									null);
					Toolket.setCellValue(workbook, sheet, index, 12,
							((Integer) ret.get("meetingAbsentCounts"))
									.toString(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 13, Toolket
							.calHoursToDate(((Integer) ret.get("leave4Hours")))
							.toString()
							+ " (" + (Integer) ret.get("leave4Hours") + ")",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 14, Toolket
							.calHoursToDate(((Integer) ret.get("leave6Hours")))
							.toString()
							+ " (" + (Integer) ret.get("leave6Hours") + ")",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 15, Toolket
							.calHoursToDate(((Integer) ret.get("leave5Hours")))
							.toString()
							+ " (" + (Integer) ret.get("leave5Hours") + ")",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 16,
							Toolket.calHoursToDate(
									((Integer) ret.get("leave13Hours")))
									.toString()
									+ " ("
									+ (Integer) ret.get("leave13Hours")
									+ ")", fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 17,
							Toolket.calHoursToDate(
									((Integer) ret.get("leave10Hours")))
									.toString()
									+ " ("
									+ (Integer) ret.get("leave10Hours")
									+ ")", fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 18,
							((Float) ret.get("leave3Hours")).toString(),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 19,
							((Integer) ret.get("meetingBusinessTotalHours"))
									.toString(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 20,
							((Integer) ret.get("meetingSickTotalHours"))
									.toString(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 21,
							((Integer) ret.get("meetingWomenSickTotalHours"))
									.toString(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 22,
							((Integer) ret.get("vacationNoStatus")).toString(),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
					Toolket.setCellValue(workbook, sheet, index++, 23,
							((String) ret.get("totalScores")).toString(),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
							35.0F, null);
				}
			}

			
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "EmplWorkdateListE.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}else {
			
			File output = new File(tempDir, "EmplWorkdateListE.xls");
			FileOutputStream fos = new FileOutputStream(output);
			HSSFWorkbook workbook = new HSSFWorkbook();
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 個人刷卡紀錄清單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printEmplAmsWorkdataList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		AmsManager amsm = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		Date from = Toolket.parseNativeDate(form.getString("startDate"));
		Date to = Toolket.parseNativeDate(form.getString("endDate"));
		String term = form.getString("sterm");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat dt = new SimpleDateFormat("kk:mm", Locale.TAIWAN);

		Empl empl = mm.findEmplByIdno(aForm.getString("cname2"));
		if (empl != null) {
			AmsWorkdateData aw = new AmsWorkdateData();
			aw.setIdno(empl.getIdno());
			Map<String, Object> ret = amsm.findAmsWorkdateDataBy(empl, aw,
					from, to, Integer.parseInt(year));

			if (ret != null) {
				List<AmsWorkdateData> awds = (List<AmsWorkdateData>) ret
						.get("recordData");
				File templateXLS = new File(context
						.getRealPath("/WEB-INF/reports/AmsWorkdataList.xls"));
				HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
				HSSFSheet sheet = workbook.getSheetAt(0);

				Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第"
						+ term + "學期" + df.format(from) + "至" + df.format(to)
						+ "刷卡紀錄清單");

				int index = 2;
				for (AmsWorkdateData awd : awds) {
					Toolket.setCellValue(sheet, index, 0, Toolket
							.getEmpUnit(StringUtils.defaultIfEmpty(empl
									.getUnit(), "")));
					Toolket.setCellValue(sheet, index, 1, empl.getCname());
					Toolket.setCellValue(sheet, index, 2, df.format(awd
							.getWdate()));
					Toolket.setCellValue(sheet, index, 3, (awd.getDateType()
							.equalsIgnoreCase("w") ? "上班" : "非上班"));
					Toolket.setCellValue(sheet, index, 4,
							awd.getSetIn() == null ? "" : dt.format(awd
									.getSetIn()));
					Toolket.setCellValue(sheet, index, 5,
							awd.getSetOut() == null ? "" : dt.format(awd
									.getSetOut()));
					Toolket.setCellValue(sheet, index, 6,
							awd.getRealIn() == null ? "" : dt.format(awd
									.getRealIn()));
					Toolket.setCellValue(sheet, index++, 7,
							awd.getRealOut() == null ? "" : dt.format(awd
									.getRealOut()));
				}

				File tempDir = new File(context
						.getRealPath("/WEB-INF/reports/temp/"
								+ getUserCredential(session).getMember()
										.getIdno()
								+ (new SimpleDateFormat("yyyyMMdd")
										.format(new java.util.Date()))));
				if (!tempDir.exists())
					tempDir.mkdirs();

				File output = new File(tempDir, "EmplAmsWorkdataList.xls");
				FileOutputStream fos = new FileOutputStream(output);
				workbook.write(fos);
				fos.close();

				JasperReportUtils.printXlsToFrontEnd(response, output);
				output.delete();
				tempDir.delete();
			}
		} else {
			Map<String, String> parameters = new HashMap<String, String>();
			File image = new File(context
					.getRealPath("/pages/reports/2002chitS.jpg"));
			parameters.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager.runReportToPdf(JasperReportUtils
					.getNoResultReport(context), parameters,
					new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}
	}

	/**
	 * 查核報表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printQueryByList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		Date from = Toolket.parseNativeDate(form.getString("startDate"));
		Date to = Toolket.parseNativeDate(form.getString("endDate"));
		// 1:事假, 2:病假, 3:公假, ai:補登
		// 曠缺查核......沒說一定要
		String conditionCode = StringUtils.leftPad(aForm
				.getString("conditionCode"), 2, '0');
		//int conditionTimes = Integer
		//		.parseInt(aForm.getString("conditionTimes"));
		Long conditionTimes = Long.parseLong(aForm.getString("conditionTimes"));
		String term = form.getString("sterm"), hql = null;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		List<Object> ret = null;

		if (StringUtils.isNumeric(conditionCode)) {
			hql = "SELECT COUNT(*), a.idno FROM AmsDocApply a WHERE a.docType = '1' "
					+ "AND a.askLeaveType = ? AND a.startDate BETWEEN ? AND ? "
					+ "GROUP BY a.idno HAVING COUNT(*) >= ? ORDER BY a.idno";
			ret = am.find(hql, new Object[] { conditionCode, from, to,
					conditionTimes });
		} else {
			hql = "SELECT COUNT(*), a.id.idno FROM AmsWorkdate a WHERE a.type = 'ai' "
					+ "AND a.id.wdate BETWEEN ? AND ? "
					+ "GROUP BY a.id.idno HAVING COUNT(*) >= ? ORDER BY a.id.idno";
			ret = am.find(hql, new Object[] { from, to, conditionTimes });
		}

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFFont fontSize14 = workbook.createFont();
		fontSize14.setFontHeightInPoints((short) 14);
		fontSize14.setFontName("Arial Unicode MS");

		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("Arial Unicode MS");

		HSSFSheet sheet = workbook.createSheet("差勤查核");
		sheet.setColumnWidth(0, 8000);
		sheet.setColumnWidth(1, 2500);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學" + year + "學年度第"
				+ term + "學期" + df.format(from) + "~" + df.format(to) + "差勤查核",
				fontSize14, HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 0, "單位", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "申請人", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "職稱", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "請別", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "次數", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);

		int index = 2, counts = 0;
		String idno = null;
		Object[] data = null;
		Empl empl = null;

		for (Object o : ret) {
			data = (Object[]) o;
			//counts = ((Integer) data[0]);
			counts = Integer.parseInt(String.valueOf(data[0]));
			idno = (String) data[1];
			empl = mm.findEmplByIdno(idno);
			if (empl != null) {
				Toolket.setCellValue(workbook, sheet, index, 0, Toolket
						.getEmpUnit(StringUtils.defaultIfEmpty(empl.getUnit(),
								"")), fontSize12, HSSFCellStyle.ALIGN_CENTER,
						true, 35.0F, null);
				Toolket.setCellValue(workbook, sheet, index, 1, empl.getCname()
						.trim(), fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
						35.0F, null);
				Toolket.setCellValue(workbook, sheet, index, 2, empl.getSname()
						.trim(), fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
						35.0F, null);
				Toolket.setCellValue(workbook, sheet, index, 3, "ai"
						.equalsIgnoreCase(conditionCode) ? "補登" : Toolket
						.getAmsAskLeave(conditionCode), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
				Toolket.setCellValue(workbook, sheet, index++, 4, String
						.valueOf(counts), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd")
						.format(new java.util.Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "EmplAmsWorkdataList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();

	}

	/**
	 * 休假統計表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printVacationList(ActionMapping mapping, DynaActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String sterm) throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		AmsManager amsm = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		HttpSession session = request.getSession(false);
		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = form.getString("sterm");
		Date from = Toolket.parseNativeDate(form.getString("startDate"));
		Date to = Toolket.parseNativeDate(form.getString("endDate"));
		DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");

		Empl empl = new Empl();
		empl.setUnit("A".equalsIgnoreCase(form.getString("unitCode")) ? null
				: form.getString("unitCode"));
		List<Empl> empls = mm.findEmplsBy(empl);
		if (!empls.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();

			HSSFFont fontSize14 = workbook.createFont();
			fontSize14.setFontHeightInPoints((short) 14);
			fontSize14.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			HSSFSheet sheet = workbook.createSheet("休假統計表");

			sheet.setColumnWidth(0, 4000);
			sheet.setColumnWidth(1, 4000);
			sheet.setColumnWidth(2, 3000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 4000);
			sheet.setColumnWidth(5, 4000);
			sheet.setColumnWidth(6, 4300);
			sheet.setColumnWidth(7, 4300);
			sheet.setColumnWidth(8, 4000);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學" + year
					+ "學年度第" + term + "學期員工休假統計表 (" + df1.format(from) + " ~ "
					+ df1.format(to) + ")", fontSize14,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 0, "單位", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "職稱", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "姓名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "年假天數", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "年假剩餘天數", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "特休假天數", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 6, "特休假剩餘天數", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 7, "加班單核准次數", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			Toolket.setCellValue(workbook, sheet, 1, 8, "加班可休天數", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
			
			
			
			int index = 2;
			Example example = null;
			AmsDocApply amsDocApply = null;
			AmsPersonalVacation apv = null;
			List<AmsPersonalVacation> apvs = null;
			List<AmsDocApply> amsDocApplys4AMSDocOverTime = null;
			float yearVacationDays = 0.0f, yearVacationRemain = 0.0f;
			float specialVacation = 0.0f, specialVacationRemain = 0.0f;
			short totalDays = 0, totalHours = 0;
			Criterion in = Restrictions.in("status", new Object[] {
					IConstants.AMSCodeDocProcessOK,
					IConstants.AMSCodeDocProcessAllRest,
					IConstants.AMSCodeDocProcessHalfRest });
			Criterion between = Restrictions.between("startDate", from, to);

			for (Empl e : empls) {
				
				try{
					yearVacationDays = 0.0f;
					yearVacationRemain = 0.0f;
					specialVacation = 0.0f;
					specialVacationRemain = 0.0f;
					totalDays = 0;
					totalHours = 0;

					// 查詢年假與特休假
					apv = new AmsPersonalVacation();
					apv.setIdno(e.getIdno());
					apv.setVyear(Integer.valueOf(year));
					apvs = amsm.findAmsPersonalVacationBy(apv);
					if (!apvs.isEmpty()) {
						for (AmsPersonalVacation apvTemp : apvs) {
							if ("1".equals(apvTemp.getVtype())) {
								yearVacationDays = apvTemp.getDays();
								yearVacationRemain = apvTemp.getRemain();
							} else if ("2".equals(apvTemp.getVtype())) {
								specialVacation = apvTemp.getDays();
								specialVacationRemain = apvTemp.getRemain();
							}
						}
					}

					amsDocApply = new AmsDocApply();
					amsDocApply.setIdno(e.getIdno());
					amsDocApply.setDocType(IConstants.AMSDocOverTime); // 加班
					example = Example.create(amsDocApply).ignoreCase().enableLike(
							MatchMode.ANYWHERE);
					amsDocApplys4AMSDocOverTime = (List<AmsDocApply>) amsm
							.findSQLWithCriteria(AmsDocApply.class, example, in,
									between);
					for (AmsDocApply ada : amsDocApplys4AMSDocOverTime) {
						if (IConstants.AMSCodeDocProcessOK.equals(ada.getStatus())) {
							totalDays += ada.getTotalDay();
							totalHours += ada.getTotalHour();
						}
					}

					Toolket.setCellValue(workbook, sheet, index, 0, Toolket
							.getEmpUnit(e.getUnit()), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 1, e.getSname(),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true, 35.0F,
							null);
					Toolket.setCellValue(workbook, sheet, index, 2, e.getCname(),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true, 35.0F,
							null);
					Toolket.setCellValue(workbook, sheet, index, 3, String
							.valueOf(yearVacationDays), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 4, String
							.valueOf(yearVacationRemain), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 5, String
							.valueOf(specialVacation), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 6, String
							.valueOf(specialVacationRemain), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, 35.0F, null);
					Toolket.setCellValue(workbook, sheet, index, 7, String
							.valueOf(amsDocApplys4AMSDocOverTime.size()),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true, 35.0F,
							null);
					Toolket
							.setCellValue(
									workbook,
									sheet,
									index++,
									8,
									(totalDays + (totalHours / 8))
											+ "."
											+ Math
													.round(((float) (totalHours % 8) / 8f) * 10),
									fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
									35.0F, null);
					
				}catch(Exception e1){
					
					
				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new java.util.Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "VacationList.xls");
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
