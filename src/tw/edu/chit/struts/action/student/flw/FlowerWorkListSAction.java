package tw.edu.chit.struts.action.student.flw;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;

import tw.edu.chit.model.FlwStmdWorkData;
import tw.edu.chit.model.FlwWork;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class FlowerWorkListSAction extends BaseLookupDispatchAction {

	@Override
	@SuppressWarnings("unchecked")
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		String studentNo = request.getParameter("st");

		FlwStmdWorkData fswd = new FlwStmdWorkData();
		fswd.setStudentNo(studentNo);
		Example example = Example.create(fswd);
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("oid"));
		List<FlwStmdWorkData> fswds = (List<FlwStmdWorkData>) am
				.findSQLWithCriteria(FlwStmdWorkData.class, example, null,
						orders);

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/FlwWorkListS.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);
		sheet.protectSheet(IConstants.SHEET_PASSWORD);

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Toolket.setCellValue(sheet, 0, 0, "花博園藝志工個人預約表(" + studentNo + ")");

		int index = 2;
		FlwWork fw = null;

		for (FlwStmdWorkData fs : fswds) {
			fw = (FlwWork) am.getObject(FlwWork.class, Integer.valueOf(fs
					.getParentOid()));
			Toolket.setCellValue(workbook, sheet, index, 0, df.format(fw
					.getDate())
					+ " (" + Toolket.getWeekInfo(fw.getDate()) + ")",
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, 20.0F, null);
			Toolket.setCellValue(workbook, sheet, index, 1, fw.getClassNo(),
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, 20.0F, null);
			Toolket.setCellValue(workbook, sheet, index++, 2, fw.getAreaNo(),
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, 20.0F, null);
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd")
						.format(new java.util.Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "FlwWorkListS.xls");
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
