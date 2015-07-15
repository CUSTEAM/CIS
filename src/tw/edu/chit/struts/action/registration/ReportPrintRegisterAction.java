package tw.edu.chit.struts.action.registration;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.StudAffairJdbcDAO;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Register;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.TempStmd;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class ReportPrintRegisterAction extends BaseLookupDispatchAction {

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
		String[] years = tw.edu.chit.struts.action.score.ReportPrintAction
						.getYearArray(cm.getNowBy("School_year"));
		String[] years2 = new String[years.length + 1];
		for(int i=0; i < years.length; i ++){
			years2[i] = years[i];
		}
		years2[years.length] = "" +(Integer.parseInt(cm.getNowBy("School_year")) + 1);
		
		aForm.set("sterm", sterm);
		aForm.set("year", years2);
		
		List<Map> depts = Toolket.getCollegeDepartment(false);
		session.setAttribute("depts", depts);

		setContentPage(session, "registration/ReportPrintRegister.jsp");
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
		Map parMap = aForm.getMap();
		aForm.set("sterm", request.getParameter("st"));
		aForm.set("campusInCharge2", request.getParameter("c"));
		aForm.set("schoolInCharge2", request.getParameter("s"));
		aForm.set("deptInCharge2", request.getParameter("d"));
		aForm.set("classInCharge2", request.getParameter("cl"));
		aForm.set("printInterClass", request.getParameter("printInterClass"));
		aForm.set("printOpt", request.getParameter("p"));
		aForm.set("deptCodeOpt", request.getParameter("dcp"));
		aForm.set("campusCode", request.getParameter("cc"));
		aForm.set("schoolType", request.getParameter("stc"));
		// aForm.set("licenseValidDateStart", request.getParameter("sd"));
		// aForm.set("licenseValidDateEnd", request.getParameter("ed"));
		// aForm.set("nodeCode", request.getParameter("nc"));
		// aForm.set("dayCode", request.getParameter("dc"));
		aForm.set("campusCode", request.getParameter("cc"));
		aForm.set("schoolType", request.getParameter("stc"));
		String printOpt = aForm.getString("printOpt");
		String sterm = aForm.getString("sterm");
		request.getSession(false).setMaxInactiveInterval(-1);

		if ("UploadNewStmdTemplate".equals(printOpt)) {
			// 下載新生基本資料樣本檔(未編班編學號)
			printUploadNewStmdTemplate(mapping, aForm, request, response, sterm);
		} else if ("UploadNewStmdTemplate1".equals(printOpt)) {
			// 下載新生基本資料樣本檔(已編班編學號)
			printUploadNewStmdTemplate1(mapping, aForm, request, response,
					sterm);
		} else if ("RegisterManagerList".equals(printOpt)) {
			// 註冊檔資料清單
			printRegisterManagerList(mapping, aForm, request, response, sterm);
		} else if ("RegisterReportList1".equals(printOpt)) {
			// 未啟動註冊資料明細表
			printRegisterReportList1(mapping, aForm, request, response, sterm);
		} else if ("RegisterReportList2".equals(printOpt)) {
			// 未繳/已繳費名單明細表
			printRegisterReportList2(mapping, aForm, request, response, sterm);
		} else if ("RegisterReportListSum".equals(printOpt)) {
			// 未啟動註冊資料統計表
			printRegisterReportListSum(mapping, aForm, request, response, sterm);
		} else if ("RegisterReportNew".equals(printOpt)) {
			// 新生可編班編學號明細表
			printRegisterReportNew(mapping, aForm, request, response, sterm);
		}

	}

	/**
	 * 下載新生基本資料樣本檔(未編班編學號)
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printUploadNewStmdTemplate(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/UploadNewStmdTemplate.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "UploadNewStmdTemplate.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 下載新生基本資料樣本檔(已編班編學號)
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void printUploadNewStmdTemplate1(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/UploadNewStmdTemplate1.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "UploadNewStmdTemplate1.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 註冊檔資料清單
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printRegisterManagerList(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		ServletContext context = request.getSession().getServletContext();

		String year = aForm.getStrings("year")[0];
		String campusCode = aForm.getString("campusCode");
		String schoolType = aForm.getString("schoolType");
		boolean isAll = "A".equalsIgnoreCase(campusCode);
		List<Map> ret = null;

		if (!isAll) {
			String hql = "SELECT r.*, s.*, r.idno AS id "
					+ "FROM Register r LEFT JOIN stmd s ON r.idno = s.idno "
					+ "WHERE r.schoolYear = ? AND r.schoolTerm = ? "
					+ "AND r.campusCode = ? AND r.schoolType = ? "
					+ "ORDER BY realClassNo";
			ret = am.findBySQL(hql, new Object[] { year, sterm, campusCode,
					schoolType });
		} else {
			String hql = "SELECT r.*, s.*, r.idno AS id "
					+ "FROM Register r LEFT JOIN stmd s ON r.idno = s.idno "
					+ "WHERE r.schoolYear = ? AND r.schoolTerm = ? "
					+ "ORDER BY realClassNo";
			ret = am.findBySQL(hql, new Object[] { year, sterm });
		}

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/RegisterManagerList.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + sterm
				+ "學期註冊檔資料清單");

		int index = 2;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		if (!ret.isEmpty()) {

			Graduate graduate = null;
			for (Map obj : ret) {

				Toolket.setCellValue(sheet, index, 0, (String) obj
						.get("studentName"));
				Toolket.setCellValue(sheet, index, 1, StringUtils
						.trimToEmpty((String) (obj.get("realClassNo")==null?"":obj.get("realClassNo"))));
				Toolket.setCellValue(sheet, index, 2, StringUtils
						.trimToEmpty(StringUtils.trimToEmpty((String) (obj.get("realStudentNo")==null?"":obj.get("realStudentNo")))));
				Toolket.setCellValue(sheet, index, 3, StringUtils
						.trimToEmpty((String) (obj.get("virClassNo")==null?"":obj.get("virClassNo"))));
				Toolket.setCellValue(sheet, index, 4, StringUtils
						.trimToEmpty((String) (obj.get("virStudentNo")==null?"":obj.get("virStudentNo"))));
				Toolket.setCellValue(sheet, index, 5, ((Integer) (obj.get("tuitionFee")==null?0:obj.get("tuitionFee"))).toString());
				Toolket.setCellValue(sheet, index, 6, (String) (obj.get("tuitionAccountNo")==null?"":obj.get("tuitionAccountNo")));
				Toolket.setCellValue(sheet, index, 7, ((Integer) (obj.get("tuitionAmount")==null?0:obj.get("tuitionAmount"))).toString());
				Toolket.setCellValue(sheet, index, 8,
						obj.get("tuitionDate") == null ? "" : df
								.format((Date) obj.get("tuitionDate")));
				Toolket.setCellValue(sheet, index, 9, ((Integer) (obj.get("agencyFee")==null?0:obj.get("agencyFee"))).toString());
				Toolket.setCellValue(sheet, index, 10, (String) (obj.get("agencyAccountNo")==null?"":obj.get("agencyAccountNo")));
				Toolket.setCellValue(sheet, index, 11, ((Integer) (obj.get("agencyAmount")==null?0:obj.get("agencyAmount"))).toString());
				Toolket.setCellValue(sheet, index, 12,
						obj.get("agencyDate") == null ? "" : df
								.format((Date) obj.get("agencyDate")));
				Toolket.setCellValue(sheet, index, 13, ((Integer) (obj.get("reliefTuitionAmount")==null?0:obj.get("reliefTuitionAmount"))).toString());
				Toolket.setCellValue(sheet, index, 14, ((Integer) (obj.get("loanAmount")==null?0:obj.get("loanAmount"))).toString());
				Toolket.setCellValue(sheet, index, 15, ((Integer) (obj.get("vulnerableAmount")==null?0:obj.get("vulnerableAmount"))).toString());
				Toolket.setCellValue(sheet, index, 16, (String) (obj.get("newStudentReg")==null?"":obj.get("newStudentReg")));
				Toolket.setCellValue(sheet, index, 17, (String) (obj.get("isRegist")==null?"":obj.get("isRegist")));
				if (obj.get("depart_class") == null) {
					graduate = mm.findGraduateByIdno(((String) obj.get("id"))
							.toUpperCase().trim());
					if (graduate != null)
						Toolket.setCellValue(sheet, index, 18, Toolket
								.getStatus(graduate.getOccurStatus(), true));
				}

				index++;
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "RegisterManagerList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();

	}

	/**
	 * 未啟動註冊資料明細表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printRegisterReportList1(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String campusCode = form.getString("campusCode");
		String schoolType = form.getString("schoolType");
		boolean isAll = "A".equalsIgnoreCase(campusCode);
		String deptCode = form.getString("deptCodeOpt");
		boolean isDept = false;
		if(!deptCode.trim().equals("")){
			isDept = true;
		}
		
		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/RegisterReportList1.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		HSSFSheet sheet = workbook.getSheetAt(0);

		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + sterm
				+ "學期未啟動註冊程序明細表");

		List<TempStmd> tempStmds = null;
		List<Student> students = null;
		if ("1".equals(sterm)) {
			if(isDept){
				String classNo = "___" + deptCode +"__";
				String hql = "SELECT s FROM Register r, TempStmd s "
						+ "WHERE r.idno = s.idno AND r.type = 'N' "
						+ "AND r.schoolYear = ? AND r.schoolTerm = ? "
						+ "AND r.virClassNo like ? "
						+ "AND (r.tuitionAmount IS NULL OR r.tuitionAmount = 0) "
						+ "AND (r.agencyAmount IS NULL OR r.agencyAmount = 0) "
						+ "AND (r.reliefTuitionAmount IS NULL OR r.reliefTuitionAmount = 0) "
						+ "AND (r.loanAmount IS NULL OR r.loanAmount = 0) "
						+ "AND (r.vulnerableAmount IS NULL OR r.vulnerableAmount = 0) "
						+ " AND r.newStudentReg IS NULL ORDER BY r.virClassNo ASC";
				tempStmds = (List<TempStmd>) am.find(hql, new Object[] { year,
						sterm, classNo });
				students = tranTempstmd2Student(year, sterm, tempStmds);
				
				hql = "SELECT s FROM Register r, Student s "
						+ "WHERE r.idno = s.idno AND r.type = 'O' "
						+ "AND r.schoolYear = ? AND r.schoolTerm = ? "
						+ "AND r.realClassNo like ? "
						+ "AND (r.tuitionAmount IS NULL OR r.tuitionAmount = 0) "
						+ "AND (r.agencyAmount IS NULL OR r.agencyAmount = 0) "
						+ "AND (r.reliefTuitionAmount IS NULL OR r.reliefTuitionAmount = 0) "
						+ "AND (r.loanAmount IS NULL OR r.loanAmount = 0) "
						+ "AND (r.vulnerableAmount IS NULL OR r.vulnerableAmount = 0) "
						+ " ORDER BY r.realClassNo ASC, r.realStudentNo ASC";
				students.addAll((List<Student>) am.find(hql, new Object[] {
						year, sterm, classNo }));
				
			}else{
				if (!isAll) {
					String hql = "SELECT s FROM Register r, TempStmd s "
						+ "WHERE r.idno = s.idno AND r.type = 'N' "
						+ "AND r.schoolYear = ? AND r.schoolTerm = ? "
						+ "AND r.campusCode = ? AND r.schoolType = ? "
						+ "AND (r.tuitionAmount IS NULL OR r.tuitionAmount = 0) "
						+ "AND (r.agencyAmount IS NULL OR r.agencyAmount = 0) "
						+ "AND (r.reliefTuitionAmount IS NULL OR r.reliefTuitionAmount = 0) "
						+ "AND (r.loanAmount IS NULL OR r.loanAmount = 0) "
						+ "AND (r.vulnerableAmount IS NULL OR r.vulnerableAmount = 0) "
						+ "AND r.newStudentReg IS NULL ORDER BY r.virClassNo ASC, r.virStudentNo ASC";
					tempStmds = (List<TempStmd>) am.find(hql, new Object[] { year,
						sterm, campusCode, schoolType });
					students = tranTempstmd2Student(year, sterm, tempStmds);
					// 舊生資料
					hql = "SELECT s FROM Register r, Student s "
						+ "WHERE r.idno = s.idno AND r.type = 'O' "
						+ "AND r.schoolYear = ? AND r.schoolTerm = ? "
						+ "AND r.campusCode = ? AND r.schoolType = ? "
						+ "AND (r.tuitionAmount IS NULL OR r.tuitionAmount = 0) "
						+ "AND (r.agencyAmount IS NULL OR r.agencyAmount = 0) "
						+ "AND (r.reliefTuitionAmount IS NULL OR r.reliefTuitionAmount = 0) "
						+ "AND (r.loanAmount IS NULL OR r.loanAmount = 0) "
						+ "AND (r.vulnerableAmount IS NULL OR r.vulnerableAmount = 0) "
						+ "ORDER BY r.realClassNo ASC, r.realStudentNo ASC";
					students.addAll((List<Student>) am.find(hql, new Object[] {
						year, sterm, campusCode, schoolType }));
					
					/* Oscar written: Maybe used after transfer TempStmd to student
					// 新生資料
					String hql = "SELECT s FROM Register r, Student s "
							+ "WHERE r.idno = s.idno AND r.type = 'N' "
							+ "AND r.schoolYear = ? AND r.schoolTerm = ? "
							+ "AND r.campusCode = ? AND r.schoolType = ? "
							+ "AND (r.tuitionAmount IS NULL OR r.tuitionAmount = 0) "
							+ "AND (r.agencyAmount IS NULL OR r.agencyAmount = 0) "
							+ "AND (r.reliefTuitionAmount IS NULL OR r.reliefTuitionAmount = 0) "
							+ "AND (r.loanAmount IS NULL OR r.loanAmount = 0) "
							+ "AND (r.vulnerableAmount IS NULL OR r.vulnerableAmount = 0) "
							+ "AND r.isRegist IS NULL ORDER BY r.realClassNo ASC";
					students = (List<Student>) am.find(hql, new Object[] { year,
							sterm, campusCode, schoolType });
					// 舊生資料
					hql = "SELECT s FROM Register r, Student s "
							+ "WHERE r.idno = s.idno AND r.type = 'O' "
							+ "AND r.schoolYear = ? AND r.schoolTerm = ? "
							+ "AND r.campusCode = ? AND r.schoolType = ? "
							+ "AND (r.tuitionAmount IS NULL OR r.tuitionAmount = 0) "
							+ "AND (r.agencyAmount IS NULL OR r.agencyAmount = 0) "
							+ "AND (r.reliefTuitionAmount IS NULL OR r.reliefTuitionAmount = 0) "
							+ "AND (r.loanAmount IS NULL OR r.loanAmount = 0) "
							+ "AND (r.vulnerableAmount IS NULL OR r.vulnerableAmount = 0) "
							+ "ORDER BY r.realClassNo ASC";
					students.addAll((List<Student>) am.find(hql, new Object[] {
							year, sterm, campusCode, schoolType }));
					*/
				} else {
					// 新生資料
					String hql = "SELECT s FROM Register r, TempStmd s "
							+ "WHERE r.idno = s.idno AND r.type = 'N' "
							+ "AND r.schoolYear = ? AND r.schoolTerm = ? "
							+ "AND (r.tuitionAmount IS NULL OR r.tuitionAmount = 0) "
							+ "AND (r.agencyAmount IS NULL OR r.agencyAmount = 0) "
							+ "AND (r.reliefTuitionAmount IS NULL OR r.reliefTuitionAmount = 0) "
							+ "AND (r.loanAmount IS NULL OR r.loanAmount = 0) "
							+ "AND (r.vulnerableAmount IS NULL OR r.vulnerableAmount = 0) "
							+ "AND r.newStudentReg IS NULL ORDER BY r.realClassNo ASC";
					tempStmds = (List<TempStmd>) am.find(hql, new Object[] { year,
							sterm });
					students = tranTempstmd2Student(year, sterm, tempStmds);
					// 舊生資料
					hql = "SELECT s FROM Register r, Student s "
							+ "WHERE r.idno = s.idno AND r.type = 'O' "
							+ "AND r.schoolYear = ? AND r.schoolTerm = ? "
							+ "AND (r.tuitionAmount IS NULL OR r.tuitionAmount = 0) "
							+ "AND (r.agencyAmount IS NULL OR r.agencyAmount = 0) "
							+ "AND (r.reliefTuitionAmount IS NULL OR r.reliefTuitionAmount = 0) "
							+ "AND (r.loanAmount IS NULL OR r.loanAmount = 0) "
							+ "AND (r.vulnerableAmount IS NULL OR r.vulnerableAmount = 0) "
							+ "ORDER BY r.realClassNo ASC";
					students.addAll((List<Student>) am.find(hql, new Object[] {
							year, sterm }));
				}

			}
		} else if ("2".equals(sterm)) {
			if(isDept){
				String classNo = "___" + deptCode +"__";
				String hql = "SELECT s FROM Register r, Student s "
					+ "WHERE r.idno = s.idno AND r.type = 'O' "
					+ "AND r.schoolYear = ? AND r.schoolTerm = ? "
					+ "AND r.realClassNo like ? "
					+ "AND (r.tuitionAmount IS NULL OR r.tuitionAmount = 0) "
					+ "AND (r.agencyAmount IS NULL OR r.agencyAmount = 0) "
					+ "AND (r.reliefTuitionAmount IS NULL OR r.reliefTuitionAmount = 0) "
					+ "AND (r.loanAmount IS NULL OR r.loanAmount = 0) "
					+ "AND (r.vulnerableAmount IS NULL OR r.vulnerableAmount = 0) "
					+ " ORDER BY r.realClassNo ASC, r.realStudentNo ASC";
				students.addAll((List<Student>) am.find(hql, new Object[] {
						year, sterm, classNo }));
				
			}else{			
				if (!isAll) {
					String hql = "SELECT s FROM Register r, Student s "
							+ "WHERE r.idno = s.idno AND r.schoolYear = ? AND r.schoolTerm = ? "
							+ "AND r.campusCode = ? AND r.schoolType = ? "
							+ "AND (r.tuitionAmount IS NULL OR r.tuitionAmount = 0) "
							+ "AND (r.agencyAmount IS NULL OR r.agencyAmount = 0) "
							+ "AND (r.reliefTuitionAmount IS NULL OR r.reliefTuitionAmount = 0) "
							+ "AND (r.loanAmount IS NULL OR r.loanAmount = 0) "
							+ "AND (r.vulnerableAmount IS NULL OR r.vulnerableAmount = 0) "
							+ "ORDER BY r.realClassNo ASC";
					students = (List<Student>) am.find(hql, new Object[] { year,
							sterm, campusCode, schoolType });
				} else {
					String hql = "SELECT s FROM Register r, Student s "
							+ "WHERE r.idno = s.idno AND r.schoolYear = ? AND r.schoolTerm = ? "
							+ "AND (r.tuitionAmount IS NULL OR r.tuitionAmount = 0) "
							+ "AND (r.agencyAmount IS NULL OR r.agencyAmount = 0) "
							+ "AND (r.reliefTuitionAmount IS NULL OR r.reliefTuitionAmount = 0) "
							+ "AND (r.loanAmount IS NULL OR r.loanAmount = 0) "
							+ "AND (r.vulnerableAmount IS NULL OR r.vulnerableAmount = 0) "
							+ "ORDER BY r.realClassNo ASC";
					students = (List<Student>) am.find(hql, new Object[] { year,
							sterm });
				}
			}
		}

		if (students != null && !students.isEmpty()) {

			int index = 2;
			Graduate graduate = null;
			for (Student student : students) {

				Toolket
						.setCellValue(sheet, index, 0, String
								.valueOf(index - 1));
				Toolket.setCellValue(sheet, index, 1, Toolket
						.getClassFullName(student.getDepartClass()));
				Toolket.setCellValue(sheet, index, 2, student.getStudentNo());
				Toolket.setCellValue(sheet, index, 3, student.getStudentName());
				Toolket.setCellValue(sheet, index, 4, student.getTelephone());
				Toolket.setCellValue(sheet, index, 5, student.getCellPhone());
				Toolket.setCellValue(sheet, index, 6, student.getEmail());
				Toolket.setCellValue(sheet, index, 7, student.getCurrAddr());

				graduate = mm.findGraduateByStudentNo(student.getStudentNo());
				Toolket.setCellValue(sheet, index++, 8, graduate == null ? ""
						: Toolket.getStatus(graduate.getOccurStatus(), true));
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "RegisterReportList1.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}
	}

	/**
	 * 未繳/已繳費名單明細表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printRegisterReportList2(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String campusCode = form.getString("campusCode");
		String schoolType = form.getString("schoolType");
		String reportList = request.getParameter("rl");
		boolean isAll = "A".equalsIgnoreCase(campusCode);
		boolean isPaied = "yes".equalsIgnoreCase(request.getParameter("pt"));
		String reportFile = "";
		String hql = null;
		List<Object> ret = null;

		if (!isAll)
			hql = "SELECT r, s FROM Register r, Student s "
					+ "WHERE r.idno = s.idno AND r.schoolYear = ? AND r.schoolTerm = ? "
					+ "AND r.campusCode = ? AND r.schoolType = ? ";
		else
			hql = "SELECT r, s FROM Register r, Student s "
					+ "WHERE r.idno = s.idno AND r.schoolYear = ? AND r.schoolTerm = ? ";

		if ("1".equals(reportList)) {
			// 學雜費
			if (isPaied)
				hql += "AND (r.tuitionAmount IS NOT NULL AND r.tuitionAmount != 0) ";
			else
				hql += "AND (r.tuitionAmount IS NULL OR r.tuitionAmount = 0) ";

			reportFile = "RegisterReportList2-1.xls";
		} else if ("2".equals(reportList)) {
			// 代辦費
			if (isPaied)
				hql += "AND (r.agencyAmount IS NOT NULL AND r.agencyAmount != 0) ";
			else
				hql += "AND (r.agencyAmount IS NULL OR r.agencyAmount = 0) ";

			reportFile = "RegisterReportList2-2.xls";
		} else if ("3".equals(reportList)) {
			// 減免學雜費
			if (isPaied)
				hql += "AND (r.reliefTuitionAmount IS NOT NULL AND r.reliefTuitionAmount != 0) ";
			else
				hql += "AND (r.reliefTuitionAmount IS NULL OR r.reliefTuitionAmount = 0) ";

			reportFile = "RegisterReportList2-3.xls";
		} else if ("4".equals(reportList)) {
			// 就學貸款費
			if (isPaied)
				hql += "AND (r.loanAmount IS NOT NULL AND r.loanAmount != 0) ";
			else
				hql += "AND (r.loanAmount IS NULL OR r.loanAmount = 0) ";

			reportFile = "RegisterReportList2-3.xls";
		} else if ("5".equals(reportList)) {
			// 弱勢助學費
			if (isPaied)
				hql += "AND (r.vulnerableAmount IS NOT NULL AND r.vulnerableAmount != 0) ";
			else
				hql += "AND (r.vulnerableAmount IS NULL OR r.vulnerableAmount = 0) ";

			reportFile = "RegisterReportList2-3.xls";
		}

		hql += "ORDER BY s.departClass, s.studentNo";
		if (!isAll)
			ret = (List<Object>) am.find(hql, new Object[] { year, sterm,
					campusCode, schoolType });
		else
			ret = (List<Object>) am.find(hql, new Object[] { year, sterm });

		File templateXLS = new File(context.getRealPath("/WEB-INF/reports/"
				+ reportFile));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		HSSFSheet sheet = workbook.getSheetAt(0);

		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + sterm
				+ "學期" + (isPaied ? "已繳" : "未繳") + getFeeName(reportList)
				+ "明細表");

		if (ret != null && !ret.isEmpty()) {

			int index = 2;
			int totals = 0;
			Register reg = null;
			Student student = null;
			Object[] o = null;
			for (Object data : ret) {

				o = (Object[]) data;
				reg = (Register) o[0];
				student = (Student) o[1];
				Toolket
						.setCellValue(sheet, index, 0, String
								.valueOf(index - 1));
				Toolket.setCellValue(sheet, index, 1, Toolket
						.getClassFullName(student.getDepartClass()));
				Toolket.setCellValue(sheet, index, 2, student.getStudentNo());
				Toolket.setCellValue(sheet, index, 3, student.getStudentName());

				if ("1".equals(reportList)) {
					Toolket.setCellValue(sheet, index, 4,
							reg.getTuitionFee() == null ? "" : reg
									.getTuitionFee().toString());
					if (isPaied) {
						Toolket.setCellValue(sheet, index, 8, reg
								.getTuitionAmount() == null ? "" : reg
								.getTuitionAmount().toString());
						Toolket.setCellValue(sheet, index, 9, StringUtils
								.trimToEmpty(reg.getTuitionAccountNo()));
						Toolket.setCellValue(sheet, index, 10, reg
								.getTuitionDate() == null ? "" : df.format(reg
								.getTuitionDate()));
					}

					totals += reg.getTuitionFee();
					Toolket.setCellValue(sheet, index, 5, reg
							.getReliefTuitionAmount() == null ? "" : reg
							.getReliefTuitionAmount().toString());
					Toolket.setCellValue(sheet, index, 6,
							reg.getLoanAmount() == null ? "" : reg
									.getLoanAmount().toString());
					Toolket.setCellValue(sheet, index, 7, reg
							.getVulnerableAmount() == null ? "" : reg
							.getVulnerableAmount().toString());
				} else if ("2".equals(reportList)) {
					Toolket.setCellValue(sheet, index, 4,
							reg.getAgencyFee() == null ? "" : reg
									.getAgencyFee().toString());
					if (isPaied) {
						Toolket.setCellValue(sheet, index, 8, reg
								.getAgencyAmount() == null ? "" : reg
								.getAgencyAmount().toString());
						Toolket.setCellValue(sheet, index, 9, StringUtils
								.trimToEmpty(reg.getAgencyAccountNo()));
						Toolket.setCellValue(sheet, index, 10, reg
								.getAgencyDate() == null ? "" : df.format(reg
								.getAgencyDate()));
					}

					totals += reg.getAgencyFee();
					Toolket.setCellValue(sheet, index, 5, reg
							.getReliefTuitionAmount() == null ? "" : reg
							.getReliefTuitionAmount().toString());
					Toolket.setCellValue(sheet, index, 6,
							reg.getLoanAmount() == null ? "" : reg
									.getLoanAmount().toString());
					Toolket.setCellValue(sheet, index, 7, reg
							.getVulnerableAmount() == null ? "" : reg
							.getVulnerableAmount().toString());
				} else {
					Toolket.setCellValue(sheet, index, 4, reg
							.getTuitionAmount() == null ? "" : reg
							.getAgencyAmount().toString());
					Toolket.setCellValue(sheet, index, 5,
							reg.getAgencyAmount() == null ? "" : reg
									.getAgencyAmount().toString());
					Toolket.setCellValue(sheet, index, 6, reg
							.getReliefTuitionAmount() == null ? "" : reg
							.getReliefTuitionAmount().toString());
					Toolket.setCellValue(sheet, index, 7,
							reg.getLoanAmount() == null ? "" : reg
									.getLoanAmount().toString());
					Toolket.setCellValue(sheet, index, 8, reg
							.getVulnerableAmount() == null ? "" : reg
							.getVulnerableAmount().toString());
				}

				index++;
			}

			index++;
			sheet.addMergedRegion(new CellRangeAddress(index, index, 0, 1));
			Toolket.setCellValue(workbook, sheet, index, 0, "符合條件人數:"
					+ ret.size() + "人", fontSize10, HSSFCellStyle.ALIGN_LEFT,
					false, null);
			sheet.addMergedRegion(new CellRangeAddress(index, index, 2, 4));
			Toolket.setCellValue(workbook, sheet, index, 2, "符合條件總金額:" + totals
					+ "元", fontSize10, HSSFCellStyle.ALIGN_LEFT, false, null);
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "RegisterReportList2.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 未啟動註冊資料統計表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printRegisterReportListSum(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairJdbcDAO dao = (StudAffairJdbcDAO)getBean("studAffairJdbcDAO");
		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String campusCode = form.getString("campusCode");
		String schoolType = form.getString("schoolType");
		boolean isAll = "A".equalsIgnoreCase(campusCode);
		String deptCode = form.getString("deptCodeOpt");
		String newReg = request.getParameter("nr");
		boolean fillRegCard = true;
		
		if(newReg.equals("0")){
			fillRegCard = false;
		}
		
		boolean isDept = false;
		if(!deptCode.trim().equals("")){
			isDept = true;
		}
		
		int totalAll = 0;	//全校應註冊總人數(舊生)
		int total1D = 0;	//台北日間應註冊總人數(舊生)
		int total1N = 0;
		int total1H = 0;
		int total2A = 0;
		int noRegAll = 0, noReg1D = 0, noReg1N = 0, noReg1H = 0, noReg2A = 0;
		int newAll = 0, new1D = 0, new1N = 0, new1H = 0, new2A = 0;
		int newnoAll = 0, newno1D = 0, newno1N = 0, newno1H = 0, newno2A = 0;
		
		String subsql = " AND (TuitionAmount IS NULL OR TuitionAmount = 0) "
			+ "AND (AgencyAmount IS NULL OR AgencyAmount = 0) "
			+ "AND (ReliefTuitionAmount IS NULL OR ReliefTuitionAmount = 0) "
			+ "AND (LoanAmount IS NULL OR LoanAmount = 0) "
			+ "AND (VulnerableAmount IS NULL OR VulnerableAmount = 0) ";

		String subsql2 = subsql + " AND NewStudentReg IS NULL";
			
		
		totalAll = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + "' And Type='O'");
		total1D = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
				"' AND CampusCode='1' AND SchoolType='D' And Type='O'");
		total1N = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='N' And Type='O'");
		total1H = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='H' And Type='O' ");
		total2A = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='2' And Type='O'");
		
		noRegAll = dao.getRecordsCount("Select count(*) From Register Where  SchoolYear='" + year + "' And SchoolTerm='" + sterm + "' And Type='O'"
				+ subsql);
		
		noReg1D = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
				"' AND CampusCode='1' AND SchoolType='D' And Type='O' " + subsql);
		noReg1N = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='N' And Type='O' " + subsql);
		noReg1H = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='H' And Type='O' " + subsql);
		noReg2A = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='2' And Type='O'" + subsql);
		
		newAll = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + "' And Type='N'");
		new1D = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
				"' AND CampusCode='1' AND SchoolType='D' And Type='N'");
		new1N = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='N' And Type='N'");
		new1H = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='H' And Type='N'");
		new2A = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='2' And Type='N'");
		
		newnoAll = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + "' And Type='N'" + (fillRegCard?subsql2:subsql));
		newno1D = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
				"' AND CampusCode='1' AND SchoolType='D' And Type='N'" + (fillRegCard?subsql2:subsql));
		newno1N = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='N' And Type='N'" + (fillRegCard?subsql2:subsql));
		newno1H = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='H' And Type='N'" + (fillRegCard?subsql2:subsql));
		newno2A = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='2' And Type='N'" + (fillRegCard?subsql2:subsql));
		
		totalAll += newAll;
		total1D += new1D;
		total1N += new1N;
		total1H += new1H;
		total2A += new2A;
		noRegAll += newnoAll;
		noReg1D += newno1D;
		noReg1N += newno1N;
		noReg1H += newno1H;
		noReg2A += newno2A;
		
		List<Map> depts = Toolket.getCollegeDepartment(false);
		int deptAll[] = new int[depts.size() + 1];
		int deptNo[] = new int[depts.size() + 1];
		int idx = 0, depnewNo = 0, depoldNo = 0 ;
		for(Map dept:depts){
			idx = depts.indexOf(dept);
			deptAll[idx] = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
					"' And VirClassNo like '___" + dept.get("idno").toString() + "__'");
			depnewNo = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
					"' And VirClassNo like '___" + dept.get("idno").toString() + "__' And Type='N' " + (fillRegCard?subsql2:subsql));
			depoldNo = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
					"' And VirClassNo like '___" + dept.get("idno").toString() + "__' And Type='O'" + subsql);
			deptNo[idx] = depnewNo + depoldNo;
		}
		
		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/RegisterReportListSum.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		HSSFSheet sheet = workbook.getSheetAt(0);

		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + sterm
				+ "學期未啟動註冊程序統計表");

		int index = 2;
		NumberFormat nf = new DecimalFormat("###0.00%");
		Toolket.setCellValueInt(sheet, index, 1, total1D);
		Toolket.setCellValueInt(sheet, index, 2, (total1D - noReg1D));
		Toolket.setCellValueInt(sheet, index, 3, noReg1D);
		Toolket.setCellValueDbl(sheet, index, 4, (noReg1D/Double.parseDouble("" + total1D)));
		Toolket.setCellValueInt(sheet, index, 5, new1D);
		Toolket.setCellValueInt(sheet, index, 6, (new1D - newno1D));
		Toolket.setCellValueInt(sheet, index, 7, newno1D);
		Toolket.setCellValueDbl(sheet, index, 8, (newno1D/Double.parseDouble("" + new1D)));
		index++;
		Toolket.setCellValueInt(sheet, index, 1, total1N);
		Toolket.setCellValueInt(sheet, index, 2, (total1N - noReg1N));
		Toolket.setCellValueInt(sheet, index, 3, noReg1N);
		Toolket.setCellValueDbl(sheet, index, 4, (noReg1N/Double.parseDouble("" + total1N)));
		Toolket.setCellValueInt(sheet, index, 5, new1N);
		Toolket.setCellValueInt(sheet, index, 6, (new1N - newno1N));
		Toolket.setCellValueInt(sheet, index, 7, newno1N);
		Toolket.setCellValueDbl(sheet, index, 8, (newno1N/Double.parseDouble("" + new1N)));
		index++;
		Toolket.setCellValueInt(sheet, index, 1, total1H);
		Toolket.setCellValueInt(sheet, index, 2, (total1H - noReg1H));
		Toolket.setCellValueInt(sheet, index, 3, noReg1H);
		Toolket.setCellValueDbl(sheet, index, 4, (noReg1H/Double.parseDouble("" + total1H)));
		Toolket.setCellValueInt(sheet, index, 5, new1H);
		Toolket.setCellValueInt(sheet, index, 6, (new1H - newno1H));
		Toolket.setCellValueInt(sheet, index, 7, newno1H);
		Toolket.setCellValueDbl(sheet, index, 8, (newno1H/Double.parseDouble("" + new1H)));
		index++;
		Toolket.setCellValueInt(sheet, index, 1, total2A);
		Toolket.setCellValueInt(sheet, index, 2, (total2A - noReg2A));
		Toolket.setCellValueInt(sheet, index, 3, noReg2A);
		Toolket.setCellValueDbl(sheet, index, 4, (noReg2A/Double.parseDouble("" + total2A)));
		Toolket.setCellValueInt(sheet, index, 5, new2A);
		Toolket.setCellValueInt(sheet, index, 6, (new2A - newno2A));
		Toolket.setCellValueInt(sheet, index, 7, newno2A);
		Toolket.setCellValueDbl(sheet, index, 8, (newno2A/Double.parseDouble("" + new2A)));
		index++;
		Toolket.setCellValueInt(sheet, index, 1, totalAll);
		Toolket.setCellValueInt(sheet, index, 2, (totalAll - noRegAll));
		Toolket.setCellValueInt(sheet, index, 3, noRegAll);
		Toolket.setCellValueDbl(sheet, index, 4, (noRegAll/Double.parseDouble("" + totalAll)));
		Toolket.setCellValueInt(sheet, index, 5, newAll);
		Toolket.setCellValueInt(sheet, index, 6, (newAll - newnoAll));
		Toolket.setCellValueInt(sheet, index, 7, newnoAll);
		Toolket.setCellValueDbl(sheet, index, 8, (newnoAll/Double.parseDouble("" + newAll)));
		index++;
		idx = 0;
		int sum = depts.size();
		for(Map dept:depts){
			idx = depts.indexOf(dept);
			Toolket.setCellValue(sheet, index, 0, dept.get("name").toString());
			Toolket.setCellValueInt(sheet, index, 1, deptAll[idx]);
			Toolket.setCellValueInt(sheet, index, 2, (deptAll[idx] - deptNo[idx]));
			Toolket.setCellValueInt(sheet, index, 3, deptNo[idx]);
			if(deptAll[idx] > 0)
				Toolket.setCellValueDbl(sheet, index, 4, (deptNo[idx]/Double.parseDouble("" + deptAll[idx])));
			else
				Toolket.setCellValueDbl(sheet, index, 4, 0.0d);
			deptAll[sum] += deptAll[idx];
			deptNo[sum] += deptNo[idx];
			index++;
		}
		Toolket.setCellValue(sheet, index, 0, "系所總計");
		Toolket.setCellValueInt(sheet, index, 1, deptAll[sum]);
		Toolket.setCellValueInt(sheet, index, 2, (deptAll[sum] - deptNo[sum]));
		Toolket.setCellValueInt(sheet, index, 3, deptNo[sum]);
		Toolket.setCellValueDbl(sheet, index, 4, (deptNo[sum]/Double.parseDouble("" + deptAll[sum])));
		
		
		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "RegisterReportListSum.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}

	/**
	 * 新生可編班編學號明細表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
	private void printRegisterReportNew(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String campusCode = form.getString("campusCode");
		String schoolType = form.getString("schoolType");
		boolean isAll = "A".equalsIgnoreCase(campusCode);
		String deptCode = form.getString("deptCodeOpt");
		boolean isDept = false;
		if(!deptCode.trim().equals("")){
			isDept = true;
		}
		
		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/RegisterReportListNew.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		HSSFSheet sheet = workbook.getSheetAt(0);

		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + sterm
				+ "學期  新生可編班編學號明細表");

		List<Register> registers = null;
		List<Student> students = null;
		if ("1".equals(sterm)) {
				if (!isAll) {
					String hql = "SELECT r FROM Register r "
						+ "WHERE r.type = 'N' "
						+ "AND r.schoolYear = ? AND r.schoolTerm = ? "
						+ "AND r.campusCode = ? AND r.schoolType = ? "
						+ "And (tuitionAmount>0 Or agencyAmount>0 Or reliefTuitionAmount>0 Or loanAmount>0 Or vulnerableAmount>0 Or newStudentReg is not null) "
						+ " ORDER BY r.virClassNo ASC, r.virStudentNo ASC";
					registers = (List<Register>) am.find(hql, new Object[] { year,
						sterm, campusCode, schoolType });
				} else {
					// 新生資料
					String hql = "SELECT s FROM Register r "
							+ "WHERE r.type = 'N' "
							+ "AND r.schoolYear = ? AND r.schoolTerm = ? "
							+ "And (tuitionAmount>0 Or agencyAmount>0 Or reliefTuitionAmount>0 Or loanAmount>0 Or vulnerableAmount>0 Or newStudentReg is not null) "
							+ " ORDER BY r.virClassNo ASC, r.virStudentNo ASC";
					registers = (List<Register>) am.find(hql, new Object[] { year,
							sterm });
				}

		}

		if (registers != null && !registers.isEmpty()) {

			int index = 2;
			for (Register student : registers) {

				Toolket
						.setCellValue(sheet, index, 0, String
								.valueOf(index - 1));
				if(student.getRealClassNo()!=null)
					Toolket.setCellValue(sheet, index, 1, student.getRealClassNo());
				//Toolket.setCellValue(sheet, index, 1, Toolket.getClassFullName(student.getRealClassNo()));
				if(student.getRealStudentNo()!=null)
					Toolket.setCellValue(sheet, index, 2, student.getRealStudentNo());
				Toolket.setCellValue(sheet, index, 3, student.getStudentName());
				Toolket.setCellValue(sheet, index, 4, student.getIdno());
				Toolket.setCellValue(sheet, index, 5, student.getVirClassNo());
				Toolket.setCellValue(sheet, index, 6, student.getVirStudentNo());
				if(student.getTuitionAmount()!=null)
					Toolket.setCellValue(sheet, index, 7, student.getTuitionAmount()>0?"":""+student.getTuitionAmount());
				if(student.getAgencyAmount()!=null)
					Toolket.setCellValue(sheet, index, 8, student.getAgencyAmount()>0?"":""+student.getAgencyAmount());
				if(student.getReliefTuitionAmount()!=null)
					Toolket.setCellValue(sheet, index, 9, student.getReliefTuitionAmount()>0?"":""+student.getReliefTuitionAmount());
				if(student.getLoanAmount()!=null)
					Toolket.setCellValue(sheet, index, 10, student.getLoanAmount()>0?"":""+student.getLoanAmount());
				if(student.getVulnerableAmount()!=null)
					Toolket.setCellValue(sheet, index, 11, student.getVulnerableAmount()>0?"":""+student.getVulnerableAmount());
					Toolket.setCellValue(sheet, index, 12, student.getNewStudentReg()==null?"":"是");
				index++;
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "RegisterReportListNew.xls");
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

	private String getFeeName(String feeCode) {
		if (StringUtils.isBlank(feeCode))
			return "";

		switch (Integer.parseInt(feeCode)) {
			case 1:
				return "學雜費";

			case 2:
				return "代辦費";

			case 3:
				return "減免學雜費";

			case 4:
				return "就學貸款費";

			case 5:
				return "弱勢助學費";

			default:
				return "";

		}
	}
	
	private List<Student> tranTempstmd2Student(String year, String sterm, List<TempStmd> tempStmds){
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		List<Student> students = new ArrayList<Student>();
		String hql = "From Register r Where schoolYear=? And schoolTerm=? And idno=? ";
		String departClass = "";
		String studentNo = "";
		
		for(TempStmd t:tempStmds){
			departClass = "";
			studentNo = "";
			List<Register> regs = (List<Register>)am.find(hql, new Object[] {
					year, sterm, t.getIdno()});
			if(!regs.isEmpty()){
				Register reg = regs.get(0);
				departClass = reg.getVirClassNo();
				studentNo = reg.getVirStudentNo();
			}
			students.add(new Student(departClass, studentNo, t.getStudentName(), 
					t.getSex(), t.getBirthday(), t.getIdno(), t.getEntrance(),
					t.getGradyear(), t.getIdent(), t.getDivi(), t.getBirthProvince(),
					t.getBirthCounty(), t.getCurrPost(), t.getCurrAddr(),
					t.getSchlCode(), t.getGradDept(), t.getGraduStatus(),
					t.getParentName(), t.getTelephone(), t.getPermPost(),
					t.getPermAddr(), t.getOccurStatus(), t.getOccurYear(),
					t.getOccurTerm(), t.getOccurDate(), t.getOccurDocno(),
					t.getOccurCause(), t.getOccurGraduateNo(), t.getIdentBasic(),
					t.getExtraStatus(), t.getExtraDept(), t.getEmail(),
					t.getCellPhone(), t.getIdentRemark(), t.getGradyearOrigin(),
					t.getStudentEname(), t.getSchlName()));
		}
		return students;
	}

}
