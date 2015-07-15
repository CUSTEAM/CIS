package tw.edu.chit.struts.action.course.ajax;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class CountStudents4StudentManager1 extends HttpServlet {

	private static final long serialVersionUID = 3096065850800075198L;

	@Override
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		ServletContext context = request.getSession().getServletContext();
		HttpSession session = request.getSession(false);
		CourseManager cm = (CourseManager)ctx.getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		SummerManager sm = (SummerManager)ctx.getBean(IConstants.SUMMER_MANAGER_BEAN_NAME);
		List students = (List) session.getAttribute("students");
		StringBuilder sb = new StringBuilder(
				"SELECT c.ClassNo, c.ClassName FROM Class c, stmd s "
						+ "WHERE s.depart_class = c.ClassNo AND s.student_no IN(");
		for (int i = 0; i < students.size(); i++) {
			sb.append("'" + ((Map) students.get(i)).get("student_no") + "',");
		}
		sb.delete(sb.length() - 1, sb.length());		
		//sb.append(") GROUP BY c.ClassNo ORDER BY c.DeptNo, c.ClassNo");//2010/1/5要求不列入1220,  122A
		sb.append(") AND(s.depart_class<>'122A' AND s.depart_class<>'1220') GROUP BY c.ClassNo ORDER BY c.DeptNo, c.ClassNo");
		List classes = cm.ezGetBy(sb.toString());
		int totalBoys = 0;
		int totalGirls = 0;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("人數統計");

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		for (int i = 0; i < 5; i++) {
			sheet.setColumnWidth((short) (0 + (4 * i)), (short) 4000);
			sheet.setColumnWidth((short) (1 + (4 * i)), (short) 1800);
			sheet.setColumnWidth((short) (2 + (4 * i)), (short) 1800);
			sheet.setColumnWidth((short) (3 + (4 * i)), (short) 1800);
			Toolket.setCellValue(workbook, sheet, 0, (0 + (4 * i)), "班別",
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 0, (1 + (4 * i)), "男生",
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 0, (2 + (4 * i)), "女生",
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 0, (3 + (4 * i)), "合計",
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
		}

		int studentsCount = 0, boys = 0, girls = 0;
		int row = 1, row1 = 1, row2 = 1, row3 = 1, row4 = 1, row5 = 1, col = 0;
		String classNo = null, className = null;
		char g = ' ';
		for (int i = 0; i < classes.size(); i++) {
			classNo = (String) ((Map) classes.get(i)).get("ClassNo");
			className = (String) ((Map) classes.get(i)).get("ClassName");
			g = StringUtils.substring(classNo, 4, 5).charAt(0);
			if(classNo.length()==7){
				g = StringUtils.substring(classNo, 5, 6).charAt(0);
			}
			studentsCount = sm
					.ezGetInt("SELECT COUNT(*) FROM stmd WHERE depart_class = '"
							+ classNo + "'");
			boys = sm
					.ezGetInt("SELECT COUNT(*) FROM stmd WHERE depart_class = '"
							+ classNo + "' AND sex = '1'");
			girls = studentsCount - boys;
			totalBoys += boys;
			totalGirls += girls;
			switch (g) {
				case '1':
					row = row1++;
					col = 0;
					break;

				case '2':
					row = row2++;
					col = 4;
					break;

				case '3':
					row = row3++;
					col = 8;
					break;

				case '4':
					row = row4++;
					col = 12;
					break;

				case '5':
					row = row5++;
					col = 16;
					break;

				default:
					break;
			}

			Toolket.setCellValue(workbook, sheet, row, col, className,
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, row, col + 1, String
					.valueOf(boys), fontSize10, HSSFCellStyle.ALIGN_CENTER,
					true, null);
			Toolket.setCellValue(workbook, sheet, row, col + 2, String
					.valueOf(girls), fontSize10, HSSFCellStyle.ALIGN_CENTER,
					true, null);
			Toolket.setCellValue(workbook, sheet, row, col + 3, String
					.valueOf(studentsCount), fontSize10,
					HSSFCellStyle.ALIGN_CENTER, true, null);
		}
		
		int maxRow = max(new int[] { row1, row2, row3, row4, row5 }) + 1;
		Toolket.setCellValue(workbook, sheet, maxRow, 0, "本次查詢結果:", fontSize10,
				HSSFCellStyle.ALIGN_RIGHT, false, null);
		Toolket.setCellValue(workbook, sheet, maxRow, 1, "男:" + totalBoys,
				fontSize10, HSSFCellStyle.ALIGN_CENTER, false, null);
		Toolket.setCellValue(workbook, sheet, maxRow, 2, "女:" + totalGirls,
				fontSize10, HSSFCellStyle.ALIGN_CENTER, false, null);
		Toolket.setCellValue(workbook, sheet, maxRow, 3, "共:"
				+ (totalBoys + totalGirls), fontSize10,
				HSSFCellStyle.ALIGN_CENTER, false, null);

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ (new SimpleDateFormat("yyyyMMdd").format(new Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "CountStudents4StudentManager1.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
	}
	
	private int max(int[] values) {
		int ret = Math.max(values[0], values[1]);
		for (int i = 2; i < values.length; i++)
			ret = Math.max(ret, values[i]);
		return ret;
	}
}