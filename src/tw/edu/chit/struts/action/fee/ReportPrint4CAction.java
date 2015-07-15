package tw.edu.chit.struts.action.fee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Dipost;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class ReportPrint4CAction extends BaseLookupDispatchAction {

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
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", sterm);
		aForm.set("year", tw.edu.chit.struts.action.score.ReportPrintAction
				.getYearArray(Toolket.getNextYearTerm().get(
						IConstants.PARAMETER_SCHOOL_YEAR).toString()));
		setContentPage(session, "fee/ReportPrint4C.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 列印作業
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public void printReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("syear", request.getParameter("year"));
		aForm.set("sterm", request.getParameter("st"));
		aForm.set("printOpt", request.getParameter("p"));
		aForm.set("campusInCharge2", request.getParameter("c"));
		aForm.set("schoolInCharge2", request.getParameter("s"));
		aForm.set("deptInCharge2", request.getParameter("d"));
		aForm.set("classInCharge2", request.getParameter("cl"));
		aForm.set("printInterClass", request.getParameter("printInterClass"));
		aForm.set("endDate", request.getParameter("ed"));
		aForm.set("campusCode", request.getParameter("cc"));
		aForm.set("schoolType", request.getParameter("sch"));
		aForm.set("feeKind", request.getParameter("fk"));
		aForm.set("emailTo", request.getParameter("eto"));
		String printOpt = aForm.getString("printOpt");
		String sterm = (String) aForm.get("sterm");

		if ("StdTransferAccountFile".equals(printOpt)) {
			// 轉帳資料製作
			snedStdTransferAccountFile(mapping, aForm, request, response, sterm);
		}

	}

	/**
	 * 轉帳資料製作
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	private void snedStdTransferAccountFile(ActionMapping mapping,
			DynaActionForm form, HttpServletRequest request,
			HttpServletResponse response, String sterm) throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		String thisYear = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String thisTerm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		String emailTo = form.getString("emailTo").trim();
		String transDate = Toolket.Date2Str1(Toolket.parseNativeDate(form
				.getString("endDate")));

		ServletContext context = request.getSession().getServletContext();
		StringBuffer fileName = new StringBuffer("/WEB-INF/reports/")
				.append("psbp-pay.txt");
		File file = new File(context.getRealPath(fileName.toString()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file)));

		Dipost dipost = new Dipost();
		dipost.setSchoolYear(thisYear);
		dipost.setSchoolTerm(thisTerm);
		List<Dipost> diposts = sam.findDipostsBy(dipost);

		StringBuffer buffer = null;
		Student student = null;
		Graduate graduate = null;
		List<Student> students = null;
		List<Graduate> graduates = null;

		for (Dipost d : diposts) {

			student = new Student();
			student.setStudentNo(d.getStudentNo().toUpperCase().trim());
			students = mm.findStudentsBy(student);

			if (!students.isEmpty() && students.size() == 1) {

				student = students.get(0);
				buffer = new StringBuffer(85);
				buffer.append("033");
				buffer.append("0002612"); // 經辦局
				buffer.append(StringUtils.rightPad("", 6, ' ')); // 保留欄
				buffer.append(StringUtils.rightPad("00188665", 8, ' ')); // 委存帳號
				buffer.append(StringUtils.leftPad(transDate, 7, '0')); // 轉存日期
				buffer.append(StringUtils.rightPad(d.getOfficeNo().trim(), 7,
						' ')); // 立帳局號
				buffer.append(StringUtils
						.rightPad(d.getAcctNo().trim(), 7, ' ')); // 立帳帳號
				buffer.append(StringUtils.rightPad(student.getIdno()
						.toUpperCase().trim(), 10, ' ')); // IDNO
				buffer.append(StringUtils.leftPad(d.getMoney().toString(), 8,
						'0')); // 存款金額
				buffer.append(StringUtils.rightPad("", 17, ' ')).append("\n"); // 保留欄

				bw.write(buffer.toString());
			} else {
				graduate = new Graduate();
				graduate.setStudentNo(d.getStudentNo().toUpperCase().trim());
				graduates = mm.findGraduatesBy(graduate);
				if (!graduates.isEmpty() && graduates.size() == 1) {
					graduate = graduates.get(0);
					buffer = new StringBuffer(85);
					buffer.append("033");
					buffer.append("0002612"); // 經辦局
					buffer.append(StringUtils.rightPad("", 6, ' ')); // 保留欄
					buffer.append(StringUtils.rightPad("00188665", 8, ' ')); // 委存帳號
					buffer.append(StringUtils.leftPad(transDate, 7, '0')); // 轉存日期
					buffer.append(StringUtils.rightPad(d.getOfficeNo().trim(),
							7, ' ')); // 立帳局號
					buffer.append(StringUtils.rightPad(d.getAcctNo().trim(), 7,
							' ')); // 立帳帳號
					buffer.append(StringUtils.rightPad(graduate.getIdno()
							.toUpperCase().trim(), 10, ' ')); // IDNO
					buffer.append(StringUtils.leftPad(d.getMoney().toString(),
							8, '0')); // 存款金額
					buffer.append(StringUtils.rightPad("", 17, ' ')).append(
							"\n"); // 保留欄

					bw.write(buffer.toString());
				}
			}
		}

		bw.close();

		List<File> files = new LinkedList<File>();
		files.add(file);
		new SendEmail(files, emailTo, false).run();

		Map<String, String> param = new HashMap<String, String>();
		File image = new File(context
				.getRealPath("/pages/images/2002chitS.jpg"));
		param.put("IMAGE", image.getAbsolutePath());
		byte[] bytes = JasperRunManager.runReportToPdf(JasperReportUtils
				.getProcessOkReport(context), param, new JREmptyDataSource());
		JasperReportUtils.printPdfToFrontEnd(response, bytes);

	}

	/**
	 * Date Object轉成民國年
	 * 
	 * @commend 會捕0,無-
	 * @param date
	 * @return
	 */
	static String Date2Str1(Date date) {
		if (date == null)
			return "";
		String ret = "";
		Calendar aDate = Calendar.getInstance();
		aDate.setTime(date);
		ret += String.valueOf(aDate.get(Calendar.YEAR) - 1911);
		ret += (aDate.get(Calendar.MONTH) + 1) < 10 ? "0"
				+ (aDate.get(Calendar.MONTH) + 1)
				: aDate.get(Calendar.MONTH) + 1;
		ret += aDate.get(Calendar.DATE) >= 10 ? aDate.get(Calendar.DATE) : "0"
				+ aDate.get(Calendar.DATE);
		return ret;
	}

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

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("preview", "printReport");
		return map;
	}

}
