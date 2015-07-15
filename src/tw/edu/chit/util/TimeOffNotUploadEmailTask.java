package tw.edu.chit.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Investigation;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;

public class TimeOffNotUploadEmailTask extends TimerTask {

	private Logger log = Logger.getLogger(TimeOffNotUploadEmailTask.class);

	/**
	 * 取得前第8日未點名老師名單並寄發e-mail, cc給學務單位
	 */
	@Override
	public void run() {

		StudAffairManager sm = (StudAffairManager) Global.context.getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		StudAffairDAO dao = (StudAffairDAO) Global.context.getBean("studAffairDAO");
		ScoreManager scm = (ScoreManager) Global.context.getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) Global.context.getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		
		String email = "";
		
		Calendar start = null;
		Calendar end = null;
		
		List<String> depts = sm.findCampusDepartment();
		String hql = "";
		String err = "";
		
		//Send email for Department that Teacher not upload timeoff data which was expired
		start = Calendar.getInstance();
		start.add(Calendar.DATE, -8);
		boolean havemsg = false;
		for(String dept:depts){
			StringBuffer msgs = new StringBuffer();
			msgs.append("<br>");
			msgs.append(dept.substring(2) + ":" + Toolket.FullDate2Str(start.getTime()) + " 老師未點名明細表:<br>");
			msgs.append("<table><tr><th>教師</th><th>上課班級</th><th>課程名稱</th><th>備註</th></tr>");
			
			hql = "select c From Code5 c Where category='SchoolType' And name= '" + dept + "'";
			List<Code5> campList = dao.submitQuery(hql);
			havemsg = false;
			for(Code5 camp:campList){
				start = Calendar.getInstance();
				end = Calendar.getInstance();
				start.add(Calendar.DATE, -8);
				end.add(Calendar.DATE, -8);
				List<Map> tfList = sm.getTeacherTimeOffNotUpload(camp.getIdno() , "", start, end, "1", "0");
				if(!tfList.isEmpty()){
					havemsg = true;
				}
				for(Map notupMap:tfList){
					List<Map> notupList = (List<Map>)notupMap.get("notUploadList");
					if(notupMap.get("email") != null){
						email = notupMap.get("email").toString();
					}else{
						email = "";
					}
					for(Map ntMap:notupList){
						msgs.append("<tr><td>").append(notupMap.get("teacherName").toString()).append("</td>");
						msgs.append("<td>").append(ntMap.get("deptClassName").toString()).append("</td>");
						msgs.append("<td>").append(ntMap.get("subjectName").toString()).append("</td>");
						if(notupMap.get("email") == null){
							msgs.append("<td>").append("沒有E-mail").append("</td>");
						}else if(!Toolket.isValidEmail(email)){
							msgs.append("<td>").append("無效的E-mail").append("</td>");
						}else{
							msgs.append("<td>").append(email).append("</td>");
						}
						msgs.append("</tr>");
					}
				}
			}
			msgs.append("</table>");
			msgs.append("<br> ");
			if(havemsg){
				sendDeptEmail(dept, msgs.toString());
			}
		}

		//Send email for Teacher not upload timeoff data will expired in 3 days
		for(String dept:depts){
			hql = "select c From Code5 c Where category='SchoolType' And name= '" + dept + "'";
			List<Code5> campList = dao.submitQuery(hql);
			for(Code5 camp:campList){
				start = Calendar.getInstance();
				end = Calendar.getInstance();
				start.add(Calendar.DATE, -5);
				end.add(Calendar.DATE, -5);
				List<Map> tfList = sm.getTeacherTimeOffNotUpload(camp.getIdno() , "",  start, end, "1", "0");
				for(Map notupMap:tfList){
					List<Map> notupList = (List<Map>)notupMap.get("notUploadList");
					if(notupMap.get("email") != null){
						email = notupMap.get("email").toString();
					}else{
						email = "";
					}
					if(!email.equals("")){
						//if(notupMap.get("teacherId").toString().equalsIgnoreCase("D120148934")||
							//	notupMap.get("teacherId").toString().equalsIgnoreCase("F120603503")){
							//log.debug("email:" + email + ",is valid: " + Toolket.isValidEmail(email));
							if(Toolket.isValidEmail(email)){
								err = this.sendEmail(notupMap);
								err += err;
							}
						//}
					}
				}
			}
		}
		if(!err.equals("")){
			sendDeptEmail("SysAdmin", err);
		}
	}

	private String sendEmail(Map notupMap) {
		String err = "";
		String teacherId = notupMap.get("teacherId").toString();
		String teacherName = notupMap.get("teacherName").toString();
		String teachDate = notupMap.get("teachDate").toString();
		String teachWeek = notupMap.get("teachWeek").toString();
		List<Map> notupList = (List<Map>)notupMap.get("notUploadList");
		String[] weeks = {"(一)","(二)","(三)","(四)","(五)","(六)","(日)"};
		
		InternetAddress[] address = null;
		boolean sessionDebug = false;
		String mailserver = IConstants.MAILSERVER_DOMAIN_NAME_WWW;
		String From = "cc@www.cust.edu.tw";
		String to = notupMap.get("email").toString();
		String sysAdmin = "jason034@cc.cust.edu.tw";
		//String to = "jason034@cc.cust.edu.tw";
		String Subject = "中華科技大學未點名到期前三天提醒通知! 日期:" + teachDate;
		String type = "text/html";
		
		String message = "<b>" + teacherName + "&nbsp;老師  您好:<br>";
		message += "您於:" + teachDate + weeks[Integer.parseInt(teachWeek)-1] + " ，尚未點名課程如下:<br>";
		for(Map ntMap:notupList){
			message += ntMap.get("deptClassName").toString() + "&nbsp;&nbsp;";
			message += ntMap.get("subjectName").toString() + "<br>";
		}
		message += "<br><font color=red>請您務必記得:全班學生上課無遲到曠缺也要上傳點名資料喔!!!</font><br><br>";
		message += "<br><font color=blue>這封E-mail是由點名系統發送的，請勿回覆!!!</font><br>";
		message += "<font color=\"#0000FF\" face=\"新細明體\" style=\"font-weight:500;\">中華科技大學 學務單位</font>";
		
		try{
			//log.debug("send email from:" + From + "\n To:" + to + "\n: mesg:" + message);
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
			msg.setSubject(Subject);
			msg.setSentDate(new Date());
			//msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(sysAdmin, false));
			
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			//mbp.setContent(message, type+";charset=MS950");
			mbp.setContent(message, type+";charset=UTF-8");
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
			err = mex.toString();
			mex.printStackTrace();
			return err;
		}
		return err;
	}

	private void sendDeptEmail(String dept, String msgs) {
		String to = "";
		
		//TODO: 各部制負責人 E-mail 信箱未設定
		String[] mails = null;
		if(dept.indexOf("11") >= 0){
			mails = IConstants.SAF11Emails;
		}else if(dept.indexOf("12") >= 0){
			mails = IConstants.SAF12Emails;
		}else if(dept.indexOf("13") >= 0){
			mails = IConstants.SAF13Emails;
		}else if(dept.indexOf("21") >= 0){
			mails = IConstants.SAF21Emails;
		}else if(dept.indexOf("22") >= 0){
			mails = IConstants.SAF22Emails;
		}else if(dept.indexOf("23") >= 0){
			mails = IConstants.SAF23Emails;
		}else{
			mails = new String[]{"jason034@cc.cust.edu.tw"};
		}
		if(mails.length > 0){
			for(String mail:mails){
				to += mail + ",";
			}
			to = to.substring(0, to.length()-1);
		}else{
			to = "jason034@cc.cust.edu.tw";
		}
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -8);
		InternetAddress[] address = null;
		boolean sessionDebug = false;
		String mailserver = IConstants.MAILSERVER_DOMAIN_NAME_WWW;
		String From = "cc@www.cust.edu.tw";
		String Subject = "中華科技大學教師逾期未點名列表! 日期:" + Toolket.FullDate2Str(cal.getTime());
		String type = "text/html";
		String sysAdmin = "jason034@cc.cust.edu.tw";
		try{
			//log.debug("send email from:" + From + "\n To:" + to + "\n: mesg:" + message);
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
			msg.setSubject(Subject);
			msg.setSentDate(new Date());
			
			if(dept.equalsIgnoreCase("SysAdmin")){
				Subject = "教師將逾期未上傳點名單,E-mail傳送失敗訊息! 日期:" + Toolket.FullDate2Str(Calendar.getInstance().getTime());
				msg.setRecipients(Message.RecipientType.TO, address);
			}else{
				//msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(sysAdmin, false));
			}
			
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
