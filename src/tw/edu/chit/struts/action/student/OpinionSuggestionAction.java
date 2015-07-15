package tw.edu.chit.struts.action.student;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.EmplOpinionSuggestion;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.StdOpinionSuggestion;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.SuggestionDept;
import tw.edu.chit.model.SuggestionEmail;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class OpinionSuggestionAction extends BaseLookupDispatchAction {

	/**
	 * 進入學生意見反映介面
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
		Student student = getUserCredential(session).getStudent();
		session.setAttribute("studentInfo", student);
		DynaActionForm aForm = (DynaActionForm) form;
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		SuggestionDept sd = new SuggestionDept();
		sd.setCampus(StringUtils.substring(student.getDepartClass(), 0, 1));
		List<SuggestionDept> sds = am.findSuggestionDeptBy(sd);
		if (!sds.isEmpty()) {
			List<String> who = new LinkedList<String>();
			List<String> whoOid = new LinkedList<String>();
			for (SuggestionDept s : sds) {
				who.add(s.getName());
				whoOid.add(s.getOid().toString());
			}
			aForm.set("who", who.toArray(new String[0]));
			aForm.set("whoOidData", whoOid.toArray(new String[0]));
		}
		setContentPage(session, "student/OpinionSuggestion.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 送出反映內容
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	/*
	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		DynaActionForm aform = (DynaActionForm) form;
		HttpSession session = request.getSession(false);
		String thisYear = Toolket
				.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String thisTerm = Toolket
				.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		session.setAttribute("studentInfo", getUserCredential(session)
				.getStudent());
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		Student student = getUserCredential(session).getStudent();
		String schoolType = cm
				.findSchoolTypeByClassNo(student.getDepartClass());

		StdOpinionSuggestion sos = new StdOpinionSuggestion();
		sos.setSchoolYear(thisYear);
		sos.setSchoolTerm(thisTerm);
		sos.setStudentOid(student.getOid());
		// 查詢10分鐘內的資料量
		List<StdOpinionSuggestion> soss = sam.findStdOpinionSuggestionsBy(sos,
				new Date());

		if (soss.size() <= 3) {
			sos = processRequest(student, aform, schoolType);
			sos.setSchoolYear(thisYear);
			sos.setSchoolTerm(thisTerm);
			try {
				sam.txAddStdOpinionSuggestion(sos);
				new SendEmail(aform, student, schoolType, am, false).run();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1", "意見反映已寄發至相關單位,謝謝指教"));
				saveMessages(request, messages);
			} catch (Exception e) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1",
						"對不起,儲存或寄發Email資料時發生錯誤,請重新輸入相關資訊,謝謝"));
				saveErrors(request, messages);
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "避免大量發送信件,請稍後10分鐘再發送信件,謝謝指教"));
			saveMessages(request, messages);
		}

		setContentPage(session, "student/OpinionSuggestion.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}*/

	/**
	 * 取消送出
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Send", "send");
		map.put("Cancel", "cancel");
		return map;
	}

	
	/*private static class SendEmail implements Runnable {

		Logger log = Logger.getLogger(SendEmail.class);

		private String RETURN = "\n";
		private String SPACE = " ";
		private DynaActionForm form;
		private Student student;
		private String schoolType;
		private AdminManager am = null;
		private boolean isDebug = false;

		SendEmail(DynaActionForm form, Student student, String schoolType,
				AdminManager am, boolean isDebug) {
			this.form = form;
			this.student = student;
			this.schoolType = schoolType;
			this.am = am;
			this.isDebug = isDebug;
		}

		public void run() {

			String studentEmail = StringUtils.isNotBlank(form
					.getString("email")) ? form.getString("email").trim()
					: Toolket.getDefaultStudentEmail(student, schoolType);

			SimpleEmail email = new SimpleEmail();
			email.setCharset("big5");
			email.setSentDate(new Date());
			// email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_WWW);
			// email.setAuthentication("cc@www.chit.edu.tw", "577812");
			email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_NO_AUTHEN);
			email.setSubject("學生意見反映");
			email.setDebug(isDebug);
			try {
				String suggestionDeptOid = form.getString("whoOid");
				SuggestionDept sd = am.findSuggestionDeptBy(Integer
						.valueOf(suggestionDeptOid));
				
				
				if (sd != null) {
					for (SuggestionEmail se : sd.getSuggestionEmail()) {
						email.addTo(se.getEmail(), sd.getName() + " "
								+ se.getName());
					}
				}

				// email.addBcc(IConstants.EMAIL_ELECTRIC_COMPUTER, "電算中心");
				// 避免被SpamMail過濾掉
				email.setFrom("cc@www.cust.edu.tw", student.getStudentName()
						.trim());
				// email.setFrom(studentEmail, student.getStudentName().trim());
				email.addReplyTo(studentEmail, student.getStudentName().trim());
				StringBuffer content = new StringBuffer();
				content.append("學生意見反映內容:").append(RETURN).append(RETURN);
				content.append("學生姓名:").append(SPACE).append(
						student.getStudentName()).append(RETURN);
				content.append("學生學號:").append(SPACE).append(
						student.getStudentNo()).append(RETURN);
				content.append("學生班級:").append(SPACE).append(
						Toolket.getClassFullName(student.getDepartClass()))
						.append(RETURN);
				content.append("學生Email:").append(SPACE).append(studentEmail)
						.append(RETURN);
				content.append("反映主旨:").append(SPACE).append(
						form.getString("topic")).append(RETURN);
				content.append("反映日期:").append(SPACE).append(
						form.getString("date")).append(RETURN);
				content.append("反映對象:").append(SPACE).append(sd.getName())
						.append(RETURN);
				content.append("反映地點:").append(SPACE).append(
						form.getString("place")).append(RETURN);
				content.append("反映內容:").append(SPACE).append(
						form.getString("suggestion")).append(RETURN);
				email.setContent(content.toString(), SimpleEmail.TEXT_PLAIN
						+ "; charset=big5");
				email.send();
			} catch (EmailException ee) {
				log.error(ee.getMessage(), ee);
			}
		}

	}
	*/

	
	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;		
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ActionMessages messages = new ActionMessages();
		
		String UserIdno = user.getMember().getIdno();    // 取得使用者身分證字號
		int UserOid = user.getMember().getOid();
		String UserName = user.getMember().getName();    // 取得使用者姓名
		
		String schoolYear = manager.getNowBy("School_year");                       // 取得學年度             
		String schoolTerm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);       // 取得學期
		Date lastModified = new Date();
		
		String StudName = aForm.getString("name");
		String UserEmail = aForm.getString("email");
		String Topic = aForm.getString("topic");
		//String UserDate = aForm.getString("date");
		String UserDate = "0000-00-00";
		String WhoOid = aForm.getString("whoOid");
		String Place = aForm.getString("place");
		String Suggestion = aForm.getString("suggestion");		
		
		List mailList = manager.ezGetBy("Select se.Email Email, se.Name cname From SuggestionEmail se Where ParentOid='"+WhoOid+"'");
		//System.out.println(mailList);
			
		StringBuffer content = new StringBuffer();
		content.append("學生意見反映內容: <br>");
		content.append("學生姓名:"+UserName+"<br>");
		content.append("學生Email: "+UserEmail+"<br>");
		content.append("反映主旨: "+Topic+"<br>");
		content.append("反映日期: "+UserDate+"<br>");
		content.append("反映對象: "+manager.ezGetString("Select Name From SuggestionDept Where Oid='"+WhoOid+"'")+"<br>");
		content.append("反映地點: "+Place+"<br>");
		content.append("反映內容: "+Suggestion+"<br>");
		
		FileDataSource[] file = null;
		Map map=manager.getCISMailServerInfo();
		//Date adate=new Date();
		InternetAddress addr;
		List tmp=new ArrayList();
		int s=0;
		for(int i=0; i<mailList.size(); i++){
			tmp.add(mailList.get(i));					
		}
		InternetAddress address[] = new InternetAddress[tmp.size()];
		if(tmp.size()>0){
			
			for(int i=0; i<tmp.size(); i++){				
				try{
					addr=new InternetAddress(((Map)tmp.get(i)).get("Email").toString(), ((Map)tmp.get(i)).get("cname").toString(), "BIG5");
					address[i]=addr;						
				}catch(Exception e){
					e.printStackTrace();
				}
			}				
		}
		
		manager.sendMail(
				map.get("username").toString(), 
				map.get("password").toString(), 
				map.get("mailServer").toString(), 
				map.get("mailAddress").toString(), 
				"學生意見反映通知", 
				lastModified, 
				UserName+"-"+Topic, 
				content.toString(), 
				address, 
				file
		);	
		
		manager.executeSql(
				"Insert Into StdOpinionSuggestion(" +
				"       SchoolYear, SchoolTerm, StudentOid, Email, Topic, Date, Target, Place, Content) " +
				"Values('"+schoolYear+"','"+schoolTerm+"','"+UserOid+"','"+UserEmail+"','"+Topic+"','"+UserDate+"','"+WhoOid+"'," +
					   "'"+Place+"','"+Suggestion+"')");
		 
		
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Course.messageN1", "意見反映已寄發給相關單位,謝謝指教"));
		saveMessages(request, messages);
		setContentPage(request.getSession(false), "OpinionSuggestion.jsp");	
		return unspecified(mapping, form, request, response);
		
	}
	/*
	private StdOpinionSuggestion processRequest(Student student,
			DynaActionForm form, String schoolType) {
		StdOpinionSuggestion sos = new StdOpinionSuggestion();
		sos.setStudentOid(student.getOid());
		sos.setEmail(StringUtils.isNotBlank(form.getString("email")) ? form
				.getString("email") : Toolket.getDefaultStudentEmail(student,
				schoolType));
		sos.setTopic(form.getString("topic"));
		Date date = StringUtils.isNotBlank(form.getString("date")) ? Toolket
				.parseDate(form.getString("date")) : null;
		sos.setDate(date);
		sos.setTarget(form.getString("whoOid"));
		sos.setPlace(form.getString("place"));
		sos.setContent(form.getString("suggestion"));
		sos.setLastModified(new Date());
		return sos;
	}*/

}
