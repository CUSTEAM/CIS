package tw.edu.chit.struts.action.fee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Dipost;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

import com.ibm.icu.text.NumberFormat;

public class StdTransferAccountCheckAction extends BaseLookupDispatchAction {

	private static final String TRANS_INFO = "trans";
	private static final String TRANS_COUNT = "transCount";
	private static final String TRANS_MONEY = "transMoney";

	/**
	 * 學生轉帳資料查核
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
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		session.removeAttribute(TRANS_INFO);
		session.removeAttribute("transCount");
		session.removeAttribute("transMoney");
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		aForm.set("sterm", sterm);
		aForm.set("year", tw.edu.chit.struts.action.score.ReportPrintAction
				.getYearArray(Toolket.getNextYearTerm().get(
						IConstants.PARAMETER_SCHOOL_YEAR).toString()));
		
		setContentPage(session, "fee/StdTransferAccountCheck.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		session.removeAttribute(TRANS_COUNT);
		session.removeAttribute(TRANS_MONEY);
		session.removeAttribute(TRANS_INFO);
		DynaActionForm aForm = (DynaActionForm) form;

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = aForm.getString("sterm");
		request.setAttribute("transDate", aForm.getString("endDate"));
		String kindCode = aForm.getString("kindCode").trim();
		String campusCode = aForm.getString("campusCode");
		String schoolType = aForm.getString("schoolType");
		String[] schoolTypes = new String[] {};
		StringBuffer in = new StringBuffer();
		if ("A".equalsIgnoreCase(schoolType)) {
			schoolTypes = (String[]) ArrayUtils.addAll(schoolTypes,
					IConstants.DEPT_DAY);
			schoolTypes = (String[]) ArrayUtils.addAll(schoolTypes,
					IConstants.DEPT_NIGHT);
			schoolTypes = (String[]) ArrayUtils.addAll(schoolTypes,
					IConstants.DEPT_HOLIDAY);
			for (String st : schoolTypes) {
				in.append("s.departClass LIKE ").append(
						"'" + campusCode + st + "%' ").append("OR ");
			}
		} else {
			schoolTypes = "D".equalsIgnoreCase(schoolType) ? IConstants.DEPT_DAY
					: ("N".equalsIgnoreCase(schoolType) ? IConstants.DEPT_NIGHT
							: IConstants.DEPT_HOLIDAY);
			for (String st : schoolTypes) {
				in.append("s.departClass LIKE ").append(
						"'" + campusCode + st + "%' ").append("OR ");
			}
		}

		String hql = "SELECT d, s FROM Dipost d, Student s WHERE d.studentNo = s.studentNo "
				+ "AND d.schoolYear = ? AND d.schoolTerm = ? "
				+ "AND d.kind = ? AND ("
				+ StringUtils.substringBeforeLast(in.toString(), "OR ")
				+ ") ORDER BY d.studentNo";
		List<Object> rets = am.find(hql, new Object[] { year, term, kindCode });

		List<DynaBean> beans = new ArrayList<DynaBean>();
		Object[] data = null;
		Student student = null;
		Graduate graduate = null;
		Dipost dipost = null;
		DynaBean bean = null;
		int transMoney = 0;

		for (Object o : rets) {
			data = (Object[]) o;
			dipost = (Dipost) data[0];
			student = (Student) data[1];
			dipost.setKindName(Toolket.getTransferKind(dipost.getKind()));
			bean = new LazyDynaBean();
			bean.set("className", Toolket.getClassFullName(student
					.getDepartClass()));
			bean.set("studentNo", dipost.getStudentNo().toUpperCase().trim());
			bean.set("studentName", student.getStudentName().trim());
			bean.set("idno", StringUtils.rightPad(StringUtils.substring(student
					.getIdno().toUpperCase(), 0, 6), 10, "*"));
			bean.set("idnoFull", student.getIdno().toUpperCase());
			bean.set("officeNo", dipost.getOfficeNo());
			bean.set("acctNo", dipost.getAcctNo());
			bean.set("money", dipost.getMoney().toString());
			bean.set("kindName", dipost.getKindName());
			transMoney += dipost.getMoney();
			beans.add(bean);
		}

		hql = "SELECT d, s FROM Dipost d, Graduate s WHERE d.studentNo = s.studentNo "
				+ "AND d.schoolYear = ? AND d.schoolTerm = ? "
				+ "AND d.kind = ? AND ("
				+ StringUtils.substringBeforeLast(in.toString(), "OR ")
				+ ") ORDER BY d.studentNo";
		rets = am.find(hql, new Object[] { year, term, kindCode });

		for (Object o : rets) {
			data = (Object[]) o;
			dipost = (Dipost) data[0];
			graduate = (Graduate) data[1];
			dipost.setKindName(Toolket.getTransferKind(dipost.getKind()));
			bean = new LazyDynaBean();
			bean.set("className", Toolket.getClassFullName(graduate
					.getDepartClass()));
			bean.set("studentNo", dipost.getStudentNo().toUpperCase().trim());
			bean.set("studentName", graduate.getStudentName().trim());
			bean.set("idno", StringUtils.rightPad(StringUtils.substring(
					graduate.getIdno().toUpperCase(), 0, 6), 10, "*"));
			bean.set("idnoFull", graduate.getIdno().toUpperCase());
			bean.set("officeNo", dipost.getOfficeNo());
			bean.set("acctNo", dipost.getAcctNo());
			bean.set("money", dipost.getMoney().toString());
			bean.set("kindName", dipost.getKindName());
			transMoney += dipost.getMoney();
			beans.add(bean);
		}

		session.setAttribute(TRANS_COUNT, beans.size());
		session.setAttribute(TRANS_MONEY, transMoney);
		session.setAttribute(TRANS_INFO, beans);

		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@SuppressWarnings("unchecked")
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = aForm.getString("sterm");
		//String emailTo = aForm.getString("emailTo").trim();
		String transDate = Toolket.Date2Str1(Toolket.parseNativeDate(aForm.getString("endDate")));
		request.setAttribute("transDate", aForm.getString("endDate"));
		String kindCode = aForm.getString("kindCode").trim();
		String campusCode = aForm.getString("campusCode");
		String schoolType = aForm.getString("schoolType");

		
		
		//ServletContext context = request.getSession().getServletContext();
		//StringBuffer fileName = new StringBuffer("/WEB-INF/reports/").append("psbp-pay.txt");
		//File file = new File(context.getRealPath(fileName.toString()));
		//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

		String[] schoolTypes = new String[] {};
		StringBuffer in = new StringBuffer();
		if ("A".equalsIgnoreCase(schoolType)) {
			schoolTypes = (String[]) ArrayUtils.addAll(schoolTypes,
					IConstants.DEPT_DAY);
			schoolTypes = (String[]) ArrayUtils.addAll(schoolTypes,
					IConstants.DEPT_NIGHT);
			schoolTypes = (String[]) ArrayUtils.addAll(schoolTypes,
					IConstants.DEPT_HOLIDAY);
			for (String st : schoolTypes) {
				in.append("s.departClass LIKE ").append(
						"'" + campusCode + st + "%' ").append("OR ");
			}
		} else {
			schoolTypes = "D".equalsIgnoreCase(schoolType) ? IConstants.DEPT_DAY
					: ("N".equalsIgnoreCase(schoolType) ? IConstants.DEPT_NIGHT
							: IConstants.DEPT_HOLIDAY);
			for (String st : schoolTypes) {
				in.append("s.departClass LIKE ").append(
						"'" + campusCode + st + "%' ").append("OR ");
			}
		}
		
		

		String hql = "SELECT d, s FROM Dipost d, Student s WHERE d.studentNo = s.studentNo "
				+ "AND d.schoolYear = ? AND d.schoolTerm = ? "
				+ "AND d.kind = ? AND ("
				+ StringUtils.substringBeforeLast(in.toString(), "OR ")
				+ ") ORDER BY d.studentNo";
		
		
		
		List<Object> rets = am.find(hql, new Object[] { year, term, kindCode });		
		
		Object[] data = null;
		Student student = null;
		Graduate graduate = null;
		Dipost dipost = null;
		StringBuffer buffer = new StringBuffer();
		int xx = 0;

		for (Object o : rets) {
			data = (Object[]) o;
			dipost = (Dipost) data[0];
			student = (Student) data[1];
			//buffer = new StringBuffer(85);

			buffer.append("033");
			buffer.append("0002612"); // 經辦局
			buffer.append(StringUtils.rightPad("", 6, ' ')); // 保留欄
			buffer.append(StringUtils.rightPad("00188665", 8, ' ')); // 委存帳號
			buffer.append(StringUtils.leftPad(transDate, 7, '0')); // 轉存日期
			buffer.append(StringUtils.rightPad(dipost.getOfficeNo().trim(), 7, ' ')); // 立帳局號
			buffer.append(StringUtils.rightPad(dipost.getAcctNo().trim(), 7, ' ')); // 立帳帳號
			buffer.append(StringUtils.rightPad(student.getIdno().toUpperCase().trim(), 10, ' ')); // IDNO
			buffer.append(StringUtils.leftPad(String
					.valueOf(dipost.getMoney() * 100), 10, '0')); // 存款金額(含角分)
			xx += dipost.getMoney();			
			buffer.append(StringUtils.rightPad("", 15, ' ')).append("\n"); // 保留欄
			//bw.write(buffer.toString());
		}
		
		
		
		hql = "SELECT d, s FROM Dipost d, Graduate s WHERE d.studentNo = s.studentNo "
				+ "AND d.schoolYear = ? AND d.schoolTerm = ? "
				+ "AND d.kind = ? AND ("
				+ StringUtils.substringBeforeLast(in.toString(), "OR ")
				+ ") ORDER BY d.studentNo";
		rets = am.find(hql, new Object[] { year, term, kindCode });

		for (Object o : rets) {

			data = (Object[]) o;
			dipost = (Dipost) data[0];
			graduate = (Graduate) data[1];
			//buffer = new StringBuffer(85);

			buffer.append("033");
			buffer.append("0002612"); // 經辦局
			buffer.append(StringUtils.rightPad("", 6, ' ')); // 保留欄
			buffer.append(StringUtils.rightPad("00188665", 8, ' ')); // 委存帳號
			buffer.append(StringUtils.leftPad(transDate, 7, '0')); // 轉存日期
			buffer.append(StringUtils.rightPad(dipost.getOfficeNo().trim(), 7,
					' ')); // 立帳局號
			buffer.append(StringUtils.rightPad(dipost.getAcctNo().trim(), 7,
					' ')); // 立帳帳號
			buffer.append(StringUtils.rightPad(graduate.getIdno().toUpperCase()
					.trim(), 10, ' ')); // IDNO
			buffer.append(StringUtils.leftPad(String
					.valueOf(dipost.getMoney() * 100), 10, '0')); // 存款金額(含角分)
			xx += dipost.getMoney();
			buffer.append(StringUtils.rightPad("", 15, ' ')).append("\n"); // 保留欄
			//bw.write(buffer.toString());
			//System.out.println("work");
		}
		
		
		
		
		//System.out.println(buffer);
		
		
		response.setContentType("text/xml; charset=UTF-8");		
		
		
		
		
		
		//CourseManager manager = (CourseManager) getBean("courseManager");
		//HttpSession session = request.getSession(false);
		
		
		
		PrintWriter out=response.getWriter();		
		response.setHeader("Content-disposition","attachment;filename=psbp-pay.txt");	
		
		
		out.println (buffer);
		
		out.close();
		//bw.close();
		
		//List<File> files = new LinkedList<File>();
		//files.add(file);
		//new SendEmail(files, emailTo, false).run();

		//ActionMessages messages = new ActionMessages();
		//messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				//"Course.errorN1", "資料已寄送至指定之Email,謝謝"));
		//saveMessages(request, messages);
		
		//return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		return null;
	}

	@SuppressWarnings("unchecked")
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = request.getSession().getServletContext();
		DynaActionForm aForm = (DynaActionForm) form;

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = aForm.getString("sterm");
		String kindCode = request.getParameter("kc");
		// String campusCode = request.getParameter("cc");
		// String schoolType = request.getParameter("sch");

		// TODO 用該方法不好
		List<DynaBean> beans = (List<DynaBean>) session
				.getAttribute(TRANS_INFO);

		if (!beans.isEmpty()) {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("轉帳清冊");
			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 2500);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 3000);
			sheet.setColumnWidth(5, 3500);
			sheet.setColumnWidth(6, 2500);

			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("Arial Unicode MS");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("Arial Unicode MS");

			int index = 2, peopleCounts = 0, moneyCounts = 0;
			NumberFormat nf = NumberFormat.getInstance();
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學" + year
					+ "學年度第" + term + "學期" + Toolket.getTransferKind(kindCode)
					+ "轉帳清冊", fontSize16, HSSFCellStyle.ALIGN_CENTER, false,
					35.0F, null);
			Toolket.setCellValue(workbook, sheet, 0, 6, Toolket
					.printNativeDate(new Date(), new SimpleDateFormat(
							"yy/MM/dd")), fontSize12,
					HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);

			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "班 級", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "學 號", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "姓 名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "立帳局號", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "儲戶帳號", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "身分證號", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 6, "轉帳金額", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);

			for (DynaBean bean : beans) {

				Toolket.setCellValue(workbook, sheet, index, 0, (String) bean
						.get("className"), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
				Toolket.setCellValue(workbook, sheet, index, 1, (String) bean
						.get("studentNo"), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
				Toolket.setCellValue(workbook, sheet, index, 2, (String) bean
						.get("studentName"), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
				Toolket.setCellValue(workbook, sheet, index, 3, (String) bean
						.get("officeNo"), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
				Toolket.setCellValue(workbook, sheet, index, 4, (String) bean
						.get("acctNo"), fontSize12, HSSFCellStyle.ALIGN_CENTER,
						false, 35.0F, null);
				Toolket.setCellValue(workbook, sheet, index, 5, (String) bean
						.get("idnoFull"), fontSize12,
						HSSFCellStyle.ALIGN_CENTER, false, 35.0F, null);
				Toolket.setCellValue(workbook, sheet, index++, 6, nf
						.format(Long.parseLong((String) bean.get("money"))),
						fontSize12, HSSFCellStyle.ALIGN_CENTER, false, 35.0F,
						null);
				peopleCounts++;
				moneyCounts += Integer.parseInt((String) bean.get("money"));
			}

			index++;
			sheet.addMergedRegion(new CellRangeAddress(index, index, 0, 3));
			Toolket.setCellValue(workbook, sheet, index, 0, "轉帳人數："
					+ peopleCounts + "人     轉帳金額：$" + nf.format(moneyCounts)
					+ "元", fontSize12, HSSFCellStyle.ALIGN_CENTER, false,
					35.0F, null);

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "StdTransferAccountList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		}

		return null;
	}
	
	/*
	private static class SendEmail implements Runnable {

		Logger log = Logger.getLogger(SendEmail.class);

		private List<File> files = null;
		private String to = null;
		private boolean isDebug = false;

		SendEmail(List<File> files, String to, boolean isDebug) {
			this.files = files;
			this.to = to;
			this.isDebug = isDebug;
		}
		
		
		public void run() {

			MultiPartEmail email = new MultiPartEmail();
			email.setCharset("big5");
			email.setSentDate(new Date());
			// email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_WWW);
			// email.setAuthentication("cc@www.chit.edu.tw", "577812");
			email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_NO_AUTHEN);
			email.setSubject("學生轉帳資料");
			email.setDebug(isDebug);

			EmailAttachment attachment = null;
			try {

				email.addTo(to);
				// email.addBcc(IConstants.EMAIL_ELECTRIC_COMPUTER, "電算中心");
				// 避免被SpamMail過濾掉
				email.setFrom("cc@www.cust.edu.tw", "電算中心軟體開發組");
				email.addReplyTo("oscarwei168@cc.cust.edu.tw", "電算中心軟體開發組");
				email.setMsg("學生轉帳資料-電算中心軟體開發組");

				for (File file : files) {

					attachment = new EmailAttachment();
					attachment.setPath(file.getAbsolutePath());
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					attachment.setDescription("");
					attachment.setName(file.getName());
					email.attach(attachment);
				}

				email.send();
			} catch (EmailException ee) {
				log.error(ee.getMessage(), ee);
			}
		}
	}
	*/

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fee.class.search", "search");
		map.put("fee.trans.create", "create");
		map.put("preview", "print");
		return map;
	}

}
