package tw.edu.chit.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import tw.edu.chit.model.StudDocApply;
import tw.edu.chit.model.StudPublicLeave;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;

public class StudAbsenceApplyNotConfirm extends TimerTask {

	private Logger log = Logger.getLogger(StudAbsenceApplyNotConfirm.class);

	/**
	 * 取得1日前已送核之學生假單，但導師尚未審核名單，並寄發e-mail, cc給學務單位
	 */
	@Override
	public void run() {

		StudAffairManager sm = (StudAffairManager) Global.context.getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		StudAffairDAO dao = (StudAffairDAO) Global.context.getBean("studAffairDAO");
		ScoreManager scm = (ScoreManager) Global.context.getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) Global.context.getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		
		String email = "";
		String tel = "";
		Calendar start = null;
		Calendar end = null;
		
		List<String> depts = sm.findCampusDepartment();
		String hql = "";
		String err = "";
		Calendar[] termDate = {null, null};
		
		//Send email for Department Tutors not confirm the Absence Apply Documents which was send by students
		start = Calendar.getInstance();
		start.add(Calendar.DATE, -1);
		boolean havemsg = false;
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		for(String dept:depts){
			//if(dept.substring(0, 2).equals("11"))	continue;	//Department test!
			StringBuffer msgs = new StringBuffer();
			msgs.append("<br>");
			msgs.append(dept.substring(2) + ":" + Toolket.FullDate2Str(start.getTime()) + " 學生假單 導師未審核明細表:<br>");
			msgs.append("<table><tr><th>導師</th><th>班級</th><th>申請人</th><th>假別</th><th>請假起日</th><th>請假迄日</th><th>電話</th><th>備註</th></tr>");
			
			hql = "select c From Code5 c Where category='SchoolType' And name= '" + dept + "'";
			List<Code5> campList = dao.submitQuery(hql);
			start = Calendar.getInstance();
			end = Calendar.getInstance();
			end.add(Calendar.DATE, -1);
			havemsg = false;
			for(Code5 camp:campList){
				termDate = Toolket.getDateOfWeek(camp.getIdno(), 1);
				start.setTimeInMillis(termDate[0].getTimeInMillis());
				List<Map> tfList = sm.getAbsenceApplyNotConfirm(camp.getIdno() , "", start, end, "1");
				if(!tfList.isEmpty()){
					havemsg = true;
				}
				for(Map notupMap:tfList){
					List<Object> docs = (List<Object>)notupMap.get("docs");
					
					for(Object obj:docs){
						if(notupMap.get("teacherId") != null){
							msgs.append("<tr><td>").append(notupMap.get("teacherName").toString()).append("</td>");
						}else{
							msgs.append("<tr><td><font color=red><b>").append(notupMap.get("沒有導師")).append("</b></font></td>");
						}
						if(obj instanceof StudDocApply){
							StudDocApply doc = (StudDocApply)obj;
							msgs.append("<td>").append(Toolket.getClassFullName(doc.getDepartClass())).append("</td>");
							msgs.append("<td>").append(doc.getStudentName()).append("</td>");
							msgs.append("<td>").append(Toolket.getTimeOff(doc.getAskLeaveType()).substring(1)).append("</td>");
							msgs.append("<td>").append(sf.format(doc.getStartDate())).append("</td>");
							msgs.append("<td>").append(sf.format(doc.getEndDate())).append("</td>");
						}else if(obj instanceof StudPublicLeave){
							StudPublicLeave doc = (StudPublicLeave)obj;
							msgs.append("<td>").append(Toolket.getClassFullName(doc.getDepartment())).append("</td>");
							msgs.append("<td>").append(Toolket.getEmplName(doc.getApplyId())).append("</td>");
							msgs.append("<td>").append("公假").append("</td>");
							msgs.append("<td>").append(sf.format(doc.getStartDate())).append("</td>");
							msgs.append("<td>").append(sf.format(doc.getEndDate())).append("</td>");
						}
						if(notupMap.get("teacherId") != null){
							tel = notupMap.get("tel").toString();
							msgs.append("<td>").append(tel).append("</td>");
							email = notupMap.get("email").toString();
							if(notupMap.get("email") == null){
								msgs.append("<td>").append("沒有E-mail").append("</td>");
							}else if(!Toolket.isValidEmail(email)){
								msgs.append("<td>").append("無效的E-mail").append("</td>");
							}else{
								msgs.append("<td>").append(email).append("</td>");
							}
						}else{
							msgs.append("<td>").append("").append("</td>");
							msgs.append("<td>").append("沒有導師").append("</td>");
						}
						msgs.append("</tr>");
					}
				}
			}
			if(havemsg){
				msgs.append("</table>");
				msgs.append("<br> ");
				//TODO:Testing
				sendDeptEmail(dept, msgs.toString());
			}
		}

		//end email for Department Tutors not confirm the Absence Apply Documents which was send by students
		for(String dept:depts){
			//if(dept.substring(0, 2).equals("11"))	continue;	//Department test!
			hql = "select c From Code5 c Where category='SchoolType' And name= '" + dept + "'";
			List<Code5> campList = dao.submitQuery(hql);
			for(Code5 camp:campList){
				termDate = Toolket.getDateOfWeek(camp.getIdno(), 1);
				start.setTimeInMillis(termDate[0].getTimeInMillis());
				List<Map> tfList = sm.getAbsenceApplyNotConfirm(camp.getIdno() , "", start, end, "1");
				for(Map notupMap:tfList){
					List<Object> docs = (List<Object>)notupMap.get("docs");
					
					for(Object obj:docs){
						email = "";
						if(notupMap.get("teacherId") != null){
							if(notupMap.get("email") != null){
								email = notupMap.get("email").toString();
								if(!Toolket.isValidEmail(email)){
									email = "";
								}
							}
						}
					}
					
					
					if(!email.equals("")){
						//if(notupMap.get("teacherId").toString().equalsIgnoreCase("D120148934")||
							//	notupMap.get("teacherId").toString().equalsIgnoreCase("F120603503")){
							//log.debug("email:" + email + ",is valid: " + Toolket.isValidEmail(email));
						err = this.sendEmail(notupMap);
						err += err;
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
		//TODO:測試
		//if(!teacherId.trim().equalsIgnoreCase("K121069581")) return "";
		
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		
		String teacherName = notupMap.get("teacherName").toString();
		List<Object> docs = (List<Object>)notupMap.get("docs");
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, -1);
		String nows = Toolket.Date2Str(now.getTime());
		String[] weeks = {"(一)","(二)","(三)","(四)","(五)","(六)","(日)"};
		
		InternetAddress[] address = null;
		boolean sessionDebug = false;
		String mailserver = IConstants.MAILSERVER_DOMAIN_NAME_WWW;
		String From = "cc@www.cust.edu.tw";
		String to = notupMap.get("email").toString();
		String sysAdmin = "jason034@cc.cust.edu.tw";
		//String to = "jason034@cc.cust.edu.tw";
		String Subject = "中華科技大學 假單未審核通知! 日期:" + nows;
		String type = "text/html";
		
		StringBuffer message = new StringBuffer();
		message.append("<b>" + teacherName + "&nbsp;老師  您好:<br>");
		message.append("您於:" + nows + " ，尚未審核假單如下:<br>");
		
		message.append("<table><tr><th>班級</th><th>申請人</th><th>假別</th><th>請假起日</th><th>請假迄日</th></tr>");

		for(Object obj:docs){
			message.append("<tr>");
			if(obj instanceof StudDocApply){
				StudDocApply doc = (StudDocApply)obj;
				message.append("<td>").append(Toolket.getClassFullName(doc.getDepartClass())).append("</td>");
				message.append("<td>").append(doc.getStudentName()).append("</td>");
				message.append("<td>").append(Toolket.getTimeOff(doc.getAskLeaveType()).substring(1)).append("</td>");
				message.append("<td>").append(sf.format(doc.getStartDate())).append("</td>");
				message.append("<td>").append(sf.format(doc.getEndDate())).append("</td>");
			}else if(obj instanceof StudPublicLeave){
				StudPublicLeave doc = (StudPublicLeave)obj;
				message.append("<td>").append(Toolket.getClassFullName(doc.getDepartment())).append("</td>");
				message.append("<td>").append(Toolket.getEmplName(doc.getApplyId())).append("</td>");
				message.append("<td>").append("公假").append("</td>");
				message.append("<td>").append(sf.format(doc.getStartDate())).append("</td>");
				message.append("<td>").append(sf.format(doc.getEndDate())).append("</td>");
			}
			message.append("</tr>");
		}
		message.append("</table><br>");

		message.append("<br><font color=blue>這封E-mail是由學生線上請假系統發送的，請勿回覆!!!</font><br>");
		message.append("<font color=\"#0000FF\" face=\"新細明體\" style=\"font-weight:500;\">中華科技大學 學務單位</font>");
		
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
			//TODO:Testing
			//msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(sysAdmin, false));
			
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			//mbp.setContent(message, type+";charset=MS950");
			mbp.setContent(message.toString(), type+";charset=UTF-8");
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
		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, -1);
		String nows = Toolket.Date2Str(now.getTime());
		InternetAddress[] address = null;
		boolean sessionDebug = false;
		String mailserver = IConstants.MAILSERVER_DOMAIN_NAME_WWW;
		String From = "cc@www.cust.edu.tw";
		String Subject = "中華科技大學 假單未審核列表! 日期:" + nows;
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
				Subject = "假單未審核導師名單,E-mail傳送失敗訊息! 日期:" + Toolket.FullDate2Str(Calendar.getInstance().getTime());
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
