package tw.edu.chit.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import tw.edu.chit.model.Empl;
import tw.edu.chit.service.AmsManager;

public class AMSWorkDateNotSetEmailTask extends TimerTask {

	private Logger log = Logger.getLogger(AMSWorkDateNotSetEmailTask.class);
	private Calendar startCal;

	public AMSWorkDateNotSetEmailTask(Calendar startCal){
		this.startCal = startCal;
	}
	
	/**
	 * 寄發e-mail
	 */
	@Override
	public void run() {

		AmsManager am = (AmsManager) Global.context.getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		
		//Send email for Ams_Workdate record not set at 5 days later
		List<Empl> empls = am.checkNoWorkDate(startCal, startCal);
		if(!empls.isEmpty()){
			StringBuffer msgs = new StringBuffer();
			msgs.append("<br>");
			msgs.append("員工刷卡資料未設定通知:<br>");
			msgs.append("<table><tr><th>姓名</th><th>身分證字號</th><th>備註</th></tr>");

			for(Empl empl:empls){
				msgs.append("<tr><td>").append(empl.getCname()).append("</td>");
				msgs.append("<td>").append(empl.getIdno()).append("</td>");
				if(empl.getWorkShift()!=null){
					if(empl.getWorkShift().trim().equals("")){
						msgs.append("<td>").append("人事基本資料檔沒有設定班別").append("</td>");
					}
				}else{
					msgs.append("<td>").append("人事基本資料檔沒有設定班別").append("</td>");
				}
				msgs.append("</tr>");
			}
			msgs.append("</table>");
			msgs.append("<br> ");
			sendDeptEmail(msgs.toString());
		}
	}

	private void sendDeptEmail(String msgs) {
		String to = "";
		
		to = "jane@cc.cust.edu.tw, chnbenny@cc.cust.edu.tw";
		
		Calendar cal = Calendar.getInstance();
		InternetAddress[] address = null;
		boolean sessionDebug = false;
		String mailserver = IConstants.MAILSERVER_DOMAIN_NAME_WWW;
		String From = "cc@www.cust.edu.tw";
		String Subject = "中華科技大學 員工刷卡資料未設定通知! 日期:" + Toolket.FullDate2Str(cal.getTime());
		String type = "text/html";
		String sysAdmin = "jason034@cc.cust.edu.tw";
		try{
			Properties props = System.getProperties();
			props.put("mail.smtp.host", mailserver);
			props.put("mail.debug", true);
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.auth", "true");
			Authenticator auth = new SMTPAuthenticator();
			
			javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props, auth);
			mailSession.setDebug(sessionDebug);
			MimeMessage msg = new MimeMessage(mailSession);
			
			msg.setFrom(new InternetAddress(From));
			address = InternetAddress.parse(to, false);
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(sysAdmin, false));
			msg.setSubject(Subject);
			msg.setSentDate(new Date());
			
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			//mbp.setContent(msgs, type+";charset=MS950");
			mbp.setContent(msgs, type+";charset=UTF-8");
			mp.addBodyPart(mbp);
			//msg.setContent(mp, type+";charset=MS950");
			msg.setContent(mp, type+";charset=UTF-8");
			//Store store = mailSession.getStore("pop3");
			//store.connect("seastar.com.tw", "mailer", "emai168mvc");
			Transport transport = mailSession.getTransport();
	        transport.connect();
			Transport.send(msg);
	        transport.close();
			//store.close();
			
		}catch(MessagingException mex){
			mex.printStackTrace();
		}
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication SMTPAuthenticator(){
			return getPasswordAuthentication();
		}
        public PasswordAuthentication getPasswordAuthentication() {
           String username = "cc@www.cust.edu.tw";
           String password = "577812";
           return new PasswordAuthentication(username, password);
        }
    }


}
