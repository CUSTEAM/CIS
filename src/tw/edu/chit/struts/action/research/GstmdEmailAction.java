package tw.edu.chit.struts.action.research;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import tw.edu.chit.model.Graduate;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class GstmdEmailAction extends BaseLookupDispatchAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		aForm.set("years", Toolket.getYearArray(year, 10));

		setContentPage(session, "research/GstmdEmail.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@SuppressWarnings("unchecked")
	public ActionForward sendEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		FormFile image = (FormFile) aForm.get("image");
		String replyTo = aForm.getString("replyTo");
		String replyToName = aForm.getString("replyToName");
		String[] VALID_IMAGE_TYPE = { "doc", "pdf", "xls" };
		ActionMessages messages = new ActionMessages();

		if (image != null && StringUtils.isNotBlank(image.getFileName())) {

			if (ArrayUtils.contains(VALID_IMAGE_TYPE, StringUtils
					.substringAfter(image.getFileName(), ".").toLowerCase())) {

				ServletContext context = session.getServletContext();
				File tempDir = new File(context
						.getRealPath("/WEB-INF/reports/temp/"
								+ getUserCredential(session).getMember()
										.getIdno()
								+ (new SimpleDateFormat("yyyyMMdd")
										.format(new Date()))));
				
				if (!tempDir.exists())
					tempDir.mkdirs();
				
				File file = new File(tempDir, image.getFileName());
				FileOutputStream fos = new FileOutputStream(file);
				IOUtils.copy(image.getInputStream(), fos);
				fos.close();
				
				// 測試用
//				Graduate graduate = null;
//				List<Graduate> gs = new LinkedList<Graduate>();
//				for (int i = 0; i < 1; i++) {
//					graduate = new Graduate();
//					graduate.setStudentName("魏組長");
//					graduate.setEmail("oscarwei168@cc.cust.edu.tw");
//					gs.add(graduate);
//				}
//				for (Graduate g : gs) {
//					if (StringUtils.isNotBlank(g.getEmail())) {
//						new SendEmail(g, aForm, file, false).run();
//					}
//				}

				Graduate graduate = new Graduate();
				graduate.setOccurStatus("6"); // 畢業
				graduate
						.setOccurYear(Short.parseShort(aForm.getString("year")));
				Example example = Example.create(graduate).ignoreCase()
						.enableLike(MatchMode.START);
				List<Order> orders = new LinkedList<Order>();
				orders.add(Order.asc("departClass"));
				List<Graduate> gs = (List<Graduate>) am.findSQLWithCriteria(
						Graduate.class, example, null, orders);
				
				// [^A-Za-z0-9\\.\\@_\\-~#]+ 太嚴格
				Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
				Matcher m = null;

				for (Graduate g : gs) {
					if (StringUtils.isNotBlank(g.getEmail())) {
						m = p.matcher(g.getEmail().toLowerCase().trim());
						if (m.matches()) {
							try {
								new SendEmail(g, aForm, file, replyTo,
										replyToName, false).run();
							} catch (Exception e) {
								log.error(e.getMessage(), e);
								continue;
							}
						} else {
							System.out.println(g.getStudentNo() + " "
									+ g.getEmail());
						}
					}
				}

				tempDir.delete();
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "非指定上傳格式:DOC、XLS或PDF"));
				saveMessages(request, messages);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "上傳資料不正確,請重新選擇檔案,謝謝"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Course.errorN1", "伺服器已於背景程式中開始寄送檔案..."));
		saveMessages(request, messages);
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public static class SendEmail implements Runnable {

		Logger log = Logger.getLogger(SendEmail.class);

		private DynaActionForm form;
		private Graduate graduate;
		private File attachFile;
		private String replyTo;
		private String replyToName;
		private boolean isDebug = false;

		SendEmail(Graduate graduate, DynaActionForm form, File attachFile,
				String replyTo, String replyToName, boolean isDebug) {
			this.graduate = graduate;
			this.form = form;
			this.attachFile = attachFile;
			this.replyTo = replyTo;
			this.replyToName = replyToName;
			this.isDebug = isDebug;
		}

		public void run() {

			MultiPartEmail email = new MultiPartEmail();
			email.setCharset("UTF-8");
			email.setSentDate(new Date());
			// email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_WWW);
			// email.setAuthentication("cc@www.chit.edu.tw", "577812");
			email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_NO_AUTHEN);
			email.setSubject(form.getString("subject"));
			email.setDebug(isDebug);

			try {
				email.addTo(graduate.getEmail().trim(), graduate
						.getStudentName().trim());
				email.setFrom("cc@www.chit.edu.tw", replyToName);
				email.addReplyTo(replyTo, replyToName);
				// email.setMsg("Test");
				EmailAttachment attachment = new EmailAttachment();
				attachment.setPath(attachFile.getPath());
				attachment.setName(MimeUtility.encodeText(attachFile.getName(),
						"UTF-8", null));
				attachment.setDescription("中華科技大學 就業輔導組");
				attachment.setDisposition(EmailAttachment.ATTACHMENT);
				email.attach(attachment);

				email.send();
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fee.add.courseFeeAdd", "sendEmail");
		return map;
	}

}
