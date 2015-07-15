package tw.edu.chit.util;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Investigation;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;

public class InvestigationTask extends TimerTask {

	private Logger log = Logger.getLogger(InvestigationTask.class);

	/**
	 * 將應屆畢業生資料轉入Investigation資料表內
	 */
	@Override
	public void run() {

		AdminManager am = (AdminManager) Global.context
				.getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) Global.context
				.getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		String schoolYear = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		List<Clazz> classes = mm.findAllClasses();
		List<Student> students = null;
		Investigation investigation = null;
		try {
			for (Clazz clazz : classes) {
				if (!Toolket.isLiteracyClass(clazz.getClassNo())
						&& Toolket.isGraduateClass(clazz.getClassNo())) {
					students = mm.findStudentsByClassNo(clazz.getClassNo());
					if (!students.isEmpty()) {
						for (Student student : students) {
							investigation = new Investigation();
							investigation.setSchoolYear(schoolYear);
							// investigation.setStudent(student);
							investigation.setStudentNo(student.getStudentNo());
							// student.setInvestigation(investigation);
							mm.txUpdateInvestigation(investigation);
						}
					}
				}
			}
			completeNotify(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			completeNotify(false);
		}
	}

	private void completeNotify(boolean flag) {
		SimpleEmail email = new SimpleEmail();
		email.setCharset("big5");
		email.setSentDate(new Date());
		// email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_WWW);
		// email.setAuthentication("cc@www.chit.edu.tw", "577812");
		email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_NO_AUTHEN);
		email.setSubject("電算中心-InvestigationTask報告通知...");
		email.setDebug(false);
		try {
			// email.addTo(IConstants.EMAIL_ELECTRIC_COMPUTER, "電算中心");
			// email.setFrom(IConstants.EMAIL_ELECTRIC_COMPUTER, "電算中心");
			StringBuffer content = new StringBuffer();
			if (flag)
				content.append("Investigation資料表已建立應屆畢業生資料...");
			else
				content.append("Investigation資料表建立應屆畢業生資料失敗...");
			email.setContent(content.toString(), SimpleEmail.TEXT_PLAIN
					+ "; charset=big5");
			email.send();
		} catch (EmailException ee) {
			log.error(ee.getMessage(), ee);
		}
	}

}
