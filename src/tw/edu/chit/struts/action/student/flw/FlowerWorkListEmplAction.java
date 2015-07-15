package tw.edu.chit.struts.action.student.flw;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import tw.edu.chit.model.FlwStmdWorkData;
import tw.edu.chit.model.FlwWork;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class FlowerWorkListEmplAction extends BaseLookupDispatchAction {

	@Override
	@SuppressWarnings("unchecked")
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();

		FlwWork work = new FlwWork();
		Example example = Example.create(work).ignoreCase().enableLike(
				MatchMode.START);
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("oid"));
		List<FlwWork> works = (List<FlwWork>) am.findSQLWithCriteria(
				FlwWork.class, example, null, orders);

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/FlwWorkListEmpl.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);
		// Short colorForFlw = HSSFColor.LIGHT_GREEN.index;

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		HSSFFont fontSize10R = workbook.createFont();
		fontSize10R.setFontHeightInPoints((short) 10);
		fontSize10R.setFontName("Arial Unicode MS");
		fontSize10R.setColor(HSSFColor.RED.index);

		HSSFFont fontSize8 = workbook.createFont();
		fontSize8.setFontHeightInPoints((short) 8);
		fontSize8.setFontName("Arial Unicode MS");

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Calendar cal = Calendar.getInstance();

		Toolket.setCellValue(sheet, 0, 0, "花博園藝志工班次預約表");
		Toolket.setCellValue(sheet, 0, 12, "下載時間 : "
				+ df1.format(cal.getTime()));

		int index = 2;
		// int counts = 0;
		FlwStmdWorkData fswd = null;
		List<FlwStmdWorkData> fswds = null;
		Student student = null;
		// orders.clear();
		// orders.add(Order.asc("lastModified"));

		for (FlwWork fw : works) {

			cal.setTime(fw.getDate());
			Toolket.setCellValue(sheet, index, 0, df.format(fw.getDate())
					+ " (" + Toolket.getWeekInfo(fw.getDate()) + ")");
			Toolket.setCellValue(sheet, index, 1, fw.getClassNo());
			Toolket.setCellValue(sheet, index, 2, fw.getAreaNo());
			Toolket.setCellValue(sheet, index, 3, fw.getCounts().toString());

			fswd = new FlwStmdWorkData();
			fswd.setParentOid(fw.getOid());
			example = Example.create(fswd);
			fswds = (List<FlwStmdWorkData>) am.findSQLWithCriteria(
					FlwStmdWorkData.class, example, null, orders);
			// counts = fswds.size();

			int i = 0;
			for (FlwStmdWorkData f : fswds) {
				student = mm.findStudentByNo(f.getStudentNo());
				if (i < fw.getCounts()) {
					Toolket.setCellValue(workbook, sheet, index, 4 + (i++),
							f.getStudentNo()
									+ "\n"
									+ (student == null ? "" : student
											.getStudentName()), fontSize10,
							HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
				} else {
					Toolket.setCellValue(workbook, sheet, index, 4 + (i++),
							f.getStudentNo()
									+ "\n"
									+ (student == null ? "" : student
											.getStudentName()), fontSize10R,
							HSSFCellStyle.ALIGN_CENTER, true, null);
				}
			}

			// for (int i = 1; i <= fw.getCounts(); i++)
			// if (i <= counts) {
			// fswd = fswds.get(i - 1);
			// student = mm.findStudentByNo(fswd.getStudentNo());
			// Toolket.setCellValue(workbook, sheet, index, 4 + (i - 1),
			// fswd.getStudentNo()
			// + "\n"
			// + (student == null ? "" : student
			// .getStudentName()), fontSize8,
			// HSSFCellStyle.ALIGN_CENTER, true, null);
			// } else
			// Toolket.setCellValue(workbook, sheet, index, 4 + (i - 1),
			// "", fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
			// 25.0F, null);

			index++;
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/flw"
				+ (new SimpleDateFormat("yyyyMMdd")
						.format(new java.util.Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "FlwWorkListEmpl.xls");
		FileOutputStream fos = new FileOutputStream(output);
		workbook.write(fos);
		fos.close();

		JasperReportUtils.printXlsToFrontEnd(response, output);
		output.delete();
		tempDir.delete();
		return null;
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		return map;
	}

}
