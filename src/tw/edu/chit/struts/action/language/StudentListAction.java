package tw.edu.chit.struts.action.language;

import java.io.File;
import java.io.FileOutputStream;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.RecruitSchool;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class StudentListAction extends BaseAction {

	/**
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @exception java.lang.Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		ServletContext context = this.servlet.getServletContext();
		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/DeptAssistantStudentList.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("標楷體");
		List<Student> students = (List<Student>) session
				.getAttribute("StudentInfoListC");
		int rowIndex = 1;
		for (Student student : students) {
			Toolket.setCellValue(workbook, sheet, rowIndex, 0, Toolket
					.getClassFullName(student.getDepartClass()), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, rowIndex, 1, student
					.getStudentName(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
					true, null);
			Toolket.setCellValue(workbook, sheet, rowIndex, 2, student
					.getStudentNo(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
					true, null);
			Toolket.setCellValue(workbook, sheet, rowIndex, 3, student
					.getSex2(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
					null);

			if (StringUtils.isNotBlank(student.getSchlCode()))
				Toolket.setCellValue(workbook, sheet, rowIndex, 4,
						mm
								.findRecruitSchoolsBy(
										new RecruitSchool(student.getSchlCode()
												.trim())).get(0).getName()
								.trim(), fontSize10,
						HSSFCellStyle.ALIGN_CENTER, true, null);
			else
				Toolket.setCellValue(workbook, sheet, rowIndex, 4, "",
						fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, rowIndex, 5, student
					.getGradDept(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
					true, null);
			Toolket.setCellValue(workbook, sheet, rowIndex, 6, Toolket
					.getIdentity(student.getIdent()), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, rowIndex++, 7, student
					.getOccurStatus2(), fontSize10, HSSFCellStyle.ALIGN_CENTER,
					true, null);
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "DeptAssistantStudentList.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
		return null;
	}

}
