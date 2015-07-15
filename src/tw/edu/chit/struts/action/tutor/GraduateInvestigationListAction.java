package tw.edu.chit.struts.action.tutor;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import tw.edu.chit.model.Investigation;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class GraduateInvestigationListAction extends BaseAction {

	/**
	 * 畢業班學生清冊下載
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ServletContext context = this.servlet.getServletContext();
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String year = ((CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME))
				.getNowBy("School_year");
		HttpSession session = request.getSession(false);

		DateFormat df = new SimpleDateFormat("yyyy/MM");
		HSSFSheet sheet = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFFont fontSize16 = workbook.createFont();
		fontSize16.setFontHeightInPoints((short) 16);
		fontSize16.setFontName("標楷體");
		HSSFFont fontSize12 = workbook.createFont();
		fontSize12.setFontHeightInPoints((short) 12);
		fontSize12.setFontName("標楷體");
		String type = request.getParameter("t"); // i:Idno d:deprtClass
		String tutorOrDepartClass = request.getParameter("no");
		String header = "應屆畢業生通訊調查表";

		sheet = workbook.createSheet("應屆畢業生通訊調查表");
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 2000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 5000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 5000);
		sheet.setColumnWidth(8, 5000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 3000);
		sheet.setColumnWidth(11, 7000);
		sheet.setColumnWidth(12, 6000);
		sheet.setColumnWidth(13, 4500);
		sheet.setColumnWidth(14, 4500);
		sheet.setColumnWidth(15, 4000);
		sheet.setColumnWidth(16, 4000);
		sheet.setColumnWidth(17, 4000);
		sheet.setColumnWidth(18, 4000);
		sheet.setColumnWidth(19, 4000);
		sheet.setColumnWidth(20, 5000);
		sheet.setColumnWidth(21, 5000);
		sheet.setColumnWidth(22, 2500);
		sheet.setColumnWidth(23, 2500);
		sheet.setColumnWidth(24, 5000);
		sheet.setColumnWidth(25, 5000);
		sheet.setColumnWidth(26, 4000);
		sheet.setColumnWidth(27, 2000);
		sheet.setColumnWidth(28, 15000);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 28));
		// Header
		Toolket.setCellValue(workbook, sheet, 0, 0, header, fontSize16,
				HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

		// Column Header
		Toolket.setCellValue(workbook, sheet, 1, 0, "學年度", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 1, "學號", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 2, "姓名", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 3, "性別", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 4, "升學/國內或國外", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 5, "升學/學校名稱", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 6, "升學/系所名稱", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 7, "升學/學位類別", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 8, "就業/公司名稱", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 9, "就業/公司電話", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 10, "就業/主管姓名", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 11, "就業/聯絡方式:E-Mail",
				fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 12, "就業/聯絡方式:地址", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 13, "就業/工作性質", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 14, "就業/職務性質", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 15, "就業/職稱", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 16, "就業/起始時間", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 17, "就業/結束時間", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 18, "就業/年薪", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 19, "服兵役/役別", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 20, "服兵役/單位名稱", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 21, "服兵役/軍種階級", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 22, "準備考試", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 23, "待業", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 24, "電郵信箱", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 25, "手機號碼", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 26, "住家電話", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 27, "通訊郵區", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);
		Toolket.setCellValue(workbook, sheet, 1, 28, "通訊地址", fontSize12,
				HSSFCellStyle.ALIGN_CENTER, true, null);

		// UserCredential credential = getUserCredential(session);
		String schoolYear = cm.getNowBy("School_year");
		List<Object> ret = null;

		if ("i".equals(type)) {
			String hql = "SELECT s, i FROM Investigation i, Student s "
					+ "WHERE i.studentNo = s.studentNo AND i.schoolYear = ? "
					+ "AND i.tutorIdno = ? ORDER BY i.studentNo";
			ret = am.find(hql, new Object[] { schoolYear, tutorOrDepartClass });
		} else if ("d".equals(type)) {
			String hql = "SELECT s, i FROM Investigation i, Student s "
					+ "WHERE i.studentNo = s.studentNo AND i.schoolYear = ? "
					+ "AND s.departClass = ? ORDER BY s.departClass";
			ret = am.find(hql, new Object[] { schoolYear, tutorOrDepartClass });
		}
		
		try{
			ret.addAll(am.find("SELECT s, i FROM Investigation i, Graduate s "
					+ "WHERE i.studentNo = s.studentNo AND i.schoolYear = ? "
					+ "AND i.tutorIdno = ? ORDER BY i.studentNo", new Object[] { schoolYear, tutorOrDepartClass }));
		}catch(Exception e){
			e.printStackTrace();
		}
		

		if (ret != null && !ret.isEmpty()) {
			Object[] o = null;
			Student student = null;
			Investigation inves = null;
			int rowIndex = 2;

			for (Object obj : ret) {

				o = (Object[]) obj;
				student = (Student) o[0];
				inves = (Investigation) o[1];

				Toolket.setCellValue(workbook, sheet, rowIndex, 0, year,
						fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 1, student
						.getStudentNo(), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 2, student
						.getStudentName(), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 3, Toolket
						.getSex(student.getSex()), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);

				Toolket.setCellValue(workbook, sheet, rowIndex, 4, ("0"
						.equals(inves.getForeignOrNot()) ? "國內" : "1"
						.equals(inves.getForeignOrNot()) ? "國外" : ""),
						fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 5, inves
						.getSchoolName(), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 6, inves
						.getCanpus(), fontSize12, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 7, inves
						.getLevel(), fontSize12, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 8, inves
						.getCompany(), fontSize12, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 9, StringUtils
						.defaultIfEmpty(inves.getCompanyPhone(), "").trim(),
						fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 10, StringUtils
						.defaultIfEmpty(inves.getBossName(), "").trim(),
						fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 11, StringUtils
						.defaultIfEmpty(inves.getBossEmail(), "").trim(),
						fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 12, StringUtils
						.defaultIfEmpty(inves.getBossAddr(), "").trim(),
						fontSize12, HSSFCellStyle.ALIGN_LEFT, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 13, Toolket
						.getWorkNature(inves.getWorkNature()), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 14, Toolket
						.getWorkDuty(inves.getWorkDuty()), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 15, inves
						.getWorkTitle(), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 16, inves
						.getWorkStart() == null ? "" : df.format(inves
						.getWorkStart()), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 17, inves
						.getWorkEnd() == null ? "" : df.format(inves
						.getWorkEnd()), fontSize12, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 18, Toolket
						.getSalaryRange(inves.getSalaryRange()), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 19, Toolket
						.getArmyType(inves.getArmyType()), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 20, StringUtils
						.defaultIfEmpty(inves.getArmyName(), "").trim(),
						fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 21, StringUtils
						.defaultIfEmpty(inves.getArmyLevel(), "").trim(),
						fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 22, inves
						.getExam().toString(), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 23, inves
						.getOther().toString(), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 24, StringUtils
						.isBlank(inves.getEmail()) ? student.getEmail() : inves
						.getEmail(), fontSize12, HSSFCellStyle.ALIGN_LEFT,
						true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 25, StringUtils
						.isBlank(inves.getCellPhone()) ? student.getCellPhone()
						: inves.getCellPhone(), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 26, StringUtils
						.isBlank(inves.getPhone()) ? student.getTelephone()
						: inves.getPhone(), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 27, StringUtils
						.isBlank(inves.getPost()) ? student.getCurrPost()
						: inves.getPost(), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, rowIndex, 28, StringUtils
						.isBlank(inves.getAddress()) ? student.getCurrAddr()
						: inves.getAddress(), fontSize12,
						HSSFCellStyle.ALIGN_LEFT, true, null);
				rowIndex++;
			}
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "RegisterList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();

		return null;
	}
}
