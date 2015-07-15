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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class FlowerWorkListAction extends BaseLookupDispatchAction {

	@Override
	@SuppressWarnings("unchecked")
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		boolean flag = StringUtils.isBlank(request.getParameter("xx")) ? true
				: true;

		FlwWork work = new FlwWork();
		Example example = Example.create(work).ignoreCase().enableLike(
				MatchMode.START);
		List<Order> orders = new LinkedList<Order>();
		orders.add(Order.asc("oid"));
		List<FlwWork> works = (List<FlwWork>) am.findSQLWithCriteria(
				FlwWork.class, example, null, orders);

		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/FlwWorkList.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFSheet sheet = workbook.getSheetAt(0);
		sheet.protectSheet(IConstants.SHEET_PASSWORD);

		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");
		Short colorForFlw = HSSFColor.LIGHT_GREEN.index;

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Calendar cal = Calendar.getInstance();

		Toolket.setCellValue(sheet, 0, 0, "花博園藝志工班次預約表");
		Toolket.setCellValue(sheet, 0, 12, "下載時間 : "
				+ df1.format(cal.getTime()));

		int index = 2, counts = 0;
		FlwStmdWorkData fswd = null;

		for (FlwWork fw : works) {

			cal.setTime(fw.getDate());
			Toolket.setCellValue(workbook, sheet, index, 0, df.format(fw
					.getDate())
					+ " (" + Toolket.getWeekInfo(fw.getDate()) + ")",
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
			Toolket.setCellValue(workbook, sheet, index, 1, fw.getClassNo(),
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
			Toolket.setCellValue(workbook, sheet, index, 2, fw.getAreaNo(),
					fontSize10, HSSFCellStyle.ALIGN_CENTER, true, 25.0F, null);
			Toolket.setCellValue(workbook, sheet, index, 3, fw.getCounts()
					.toString(), fontSize10, HSSFCellStyle.ALIGN_CENTER, true,
					25.0F, null);

			fswd = new FlwStmdWorkData();
			fswd.setParentOid(fw.getOid());
			example = Example.create(fswd);
			counts = ((List<FlwStmdWorkData>) am.findSQLWithCriteria(
					FlwStmdWorkData.class, example, null, orders)).size();

			if (flag) {
				for (int i = 1; i <= fw.getCounts(); i++)
					if (i <= counts)
						Toolket.setCellValue(workbook, sheet, index,
								4 + (i - 1), "", fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, colorForFlw);
					else
						Toolket.setCellValue(workbook, sheet, index,
								4 + (i - 1), "", fontSize10,
								HSSFCellStyle.ALIGN_CENTER, true, null);
			}

			index++;
		}

		File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"
				+ getUserCredential(session).getMember().getIdno()
				+ (new SimpleDateFormat("yyyyMMdd")
						.format(new java.util.Date()))));
		if (!tempDir.exists())
			tempDir.mkdirs();

		File output = new File(tempDir, "FlowerWorkList.xls");
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
