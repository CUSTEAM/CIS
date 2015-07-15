package tw.edu.chit.util;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Investigation;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;

public class ScoreNotUploadEmailTask extends TimerTask {

	private Logger log = Logger.getLogger(ScoreNotUploadEmailTask.class);
	private String level;
	private String scope;
	private Calendar endCal;
	private String depart;
	
	public ScoreNotUploadEmailTask(String level, String scope, Calendar endCal, String depart){
		this.level = level;
		this.scope = scope;
		this.endCal = endCal;
		this.depart = depart;
	}
	
	/**
	 * 取得成績未上傳老師名單並寄發e-mail, cc給學務單位
	 */
	@SuppressWarnings("unchecked")
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
		
		//Send email for Department that Teacher not upload score data which will expire
		start = Calendar.getInstance();
		start.add(Calendar.DATE, -8);

		List<Map> notUpload = new ArrayList<Map>();
		//Send email for Teacher not upload score data will expired
		try {
			notUpload = scm.findScoreNotUpload(depart, level, scope);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String teachedId = "";
		Empl empl = new Empl();
		DEmpl dempl = new DEmpl();
		int cnt = 0;
		for (Map notupMap : notUpload) {
			teachedId = notupMap.get("teacherID").toString();
			empl = mm.findEmplByIdno(teachedId);
			if (empl != null) {
				if(empl.getEmail()!=null){
					email = empl.getEmail();
				}else{
					email = "";
				}
			} else {
				dempl = mm.findDEmplByIdno(teachedId);
				if(dempl.getEmail()!=null){
					email = dempl.getEmail();
				}else{
					email = "";
				}
			}
			if (!email.equals("")) {
				// if(notupMap.get("teacherId").toString().equalsIgnoreCase("D120148934")||
				// notupMap.get("teacherId").toString().equalsIgnoreCase("F120603503")){
				// log.debug("email:" + email + ",is valid: " +
				// Toolket.isValidEmail(email));
				if (Toolket.isValidEmail(email)) {
					//TODO:test only
					
					if(cnt==0){	
						err = this.sendEmail(notupMap, "jason034@cc.cust.edu.tw");
						cnt++;
						//break;
					}
					
					err = this.sendEmail(notupMap, email);
					err += err;
				}
				// }
			}
		}
		if(!err.equals("")){
			sendDeptEmail("SysAdmin", err);
		}
	}

	private String sendEmail(Map notupMap, String email) {
		String err = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//departClass, deptClassName, cscode, cscodeName, teacherID, teacherName, memo
		//String teacherId = notupMap.get("teacherId").toString();
		String teacherName = notupMap.get("teacherName").toString();
		String deptClassName = notupMap.get("deptClassName").toString();
		String cscodeName = notupMap.get("cscodeName").toString();
		String endDate = df.format(endCal.getTime());
		//List<Map> notupList = (List<Map>)notupMap.get("notUploadList");
		//String[] weeks = {"(一)","(二)","(三)","(四)","(五)","(六)","(日)"};
		
		InternetAddress[] address = null;
		boolean sessionDebug = false;
		String mailserver = IConstants.MAILSERVER_DOMAIN_NAME_WWW;
		String From = "cc@www.cust.edu.tw";
		String to = email;
		String sysAdmin = "jason034@cc.cust.edu.tw";
		//String to = "jason034@cc.cust.edu.tw";
		String Subject = "";
		String type = "text/html";
		
		if(level.equals("1")){
			Subject = "中華科技大學[期中考成績]未上傳到期前三天提醒通知! 截止日期:" + endDate;
		}else if(level.equals("2")){
			if(scope.equals("2")){
				Subject = "中華科技大學[畢業班 期末考成績]未上傳到期前一天提醒通知! 截止日期:" + endDate;
			}else{
				Subject = "中華科技大學[期末考成績]未上傳到期前一天提醒通知! 截止日期:" + endDate;
			}
		}
		String message = "<b>" + teacherName + "&nbsp;老師  您好:<br>";
		message += "您尚有:" + deptClassName + "  " + cscodeName + " ，成績未上傳!!!<br>";
		/*
		for(Map ntMap:notupList){
			message += ntMap.get("deptClassName").toString() + "&nbsp;&nbsp;";
			message += ntMap.get("subjectName").toString() + "<br>";
		}
		*/
		message += "<br><font color=red>請您務必記得於截止日:" + endDate + " 前上傳成績!!!</font><br>";
		message += "<br><font color=blue>這封E-mail是由成績系統發送的，請勿回覆!!!</font><br>";
		message += "<br><font color=\"#0000FF\" face=\"新細明體\" style=\"font-weight:500;\">中華科技大學 教務單位</font>";
		
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
		if(dept.indexOf("11") >= 0){
			to = "jason034@cc.cust.edu.tw";
		}else if(dept.indexOf("12") >= 0){
			to = "jason034@cc.cust.edu.tw";
		}else if(dept.indexOf("13") >= 0){
			to = "jason034@cc.cust.edu.tw";
		}else if(dept.indexOf("21") >= 0){
			to = "jason034@cc.cust.edu.tw";
		}else if(dept.indexOf("22") >= 0){
			to = "jason034@cc.cust.edu.tw";
		}else if(dept.indexOf("23") >= 0){
			to = "jason034@cc.cust.edu.tw";
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

	public void setLevel(String level) {
		this.level = level;
	}
	public String getLevel() {
		return level;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getScope() {
		return scope;
	}

	public void setEndCal(Calendar endCal) {
		this.endCal = endCal;
	}

	public Calendar getEndCal() {
		return endCal;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getDepart() {
		return depart;
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
