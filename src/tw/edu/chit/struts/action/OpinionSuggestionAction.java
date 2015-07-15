package tw.edu.chit.struts.action;

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
import tw.edu.chit.model.SuggestionDept;
import tw.edu.chit.model.SuggestionEmail;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class OpinionSuggestionAction extends BaseLookupDispatchAction {

	/**
	 * 進入教職員意見反映介面
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
		session.setAttribute("memberInfo", getUserCredential(session)
				.getMember());
		DynaActionForm aForm = (DynaActionForm) form;
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		SuggestionDept sd = new SuggestionDept();
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
		setContentPage(session, "OpinionSuggestion.jsp");
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
		Member member = getUserCredential(session).getMember();
		session.setAttribute("memberInfo", member);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		EmplOpinionSuggestion eos = processRequest(member, aform);
		sam.txAddEmplOpinionSuggestion(eos);
		new SendEmail(aform, member, am, false).run();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Course.messageN1", "意見反映已寄發給相關單位,謝謝指教"));
		saveMessages(request, messages);
		setContentPage(session, "OpinionSuggestion.jsp");
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

	/*
	private static class SendEmail implements Runnable {

		Logger log = Logger.getLogger(SendEmail.class);

		private String RETURN = "\n";
		private String SPACE = " ";
		private DynaActionForm form;
		private Member member;
		private AdminManager am = null;
		private boolean isDebug = false;

		SendEmail(DynaActionForm form, Member member, AdminManager am,
				boolean isDebug) {
			this.form = form;
			this.member = member;
			this.am = am;
			this.isDebug = isDebug;
		}

		public void run() {

			String studentEmail = form.getString("email").trim();

			SimpleEmail email = new SimpleEmail();
			email.setCharset("big5");
			email.setSentDate(new Date());
			// email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_WWW);
			// email.setAuthentication("cc@www.chit.edu.tw", "577812");
			email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_NO_AUTHEN);
			email.setSubject("教職員意見反映");
			email.setDebug(isDebug);
			try {
				String suggestionDeptOid = form.getString("whoOid");
				SuggestionDept sd = am.findSuggestionDeptBy(Integer
						.valueOf(suggestionDeptOid));
				
				System.out.println(sd);
				if (sd != null) {
					for (SuggestionEmail se : sd.getSuggestionEmail()) {
					
						email.addTo(se.getEmail(), sd.getName() + " "
								+ se.getName());
					}
				}

				// email.addBcc(IConstants.EMAIL_ELECTRIC_COMPUTER, "電算中心");
				// 避免被SpamMail過濾掉
				email.setFrom("cc@www.cust.edu.tw", member.getName().trim());
				// email.setFrom(studentEmail, student.getStudentName().trim());
				email.addReplyTo(studentEmail, member.getName().trim());
				StringBuffer content = new StringBuffer();
				content.append("教職員意見反映內容:").append(RETURN).append(RETURN);
				content.append("教職員姓名:").append(SPACE).append(member.getName())
						.append(RETURN);
				content.append("教職員Email:").append(SPACE).append(studentEmail)
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

	}*/

	
	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;		
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		
		String UserIdno = user.getMember().getIdno();    // 取得使用者身分證字號
		String UserName = user.getMember().getName();    // 取得使用者姓名
		Date lastModified = new Date();
		
		String UserEmail = aForm.getString("email");
		String Topic = aForm.getString("topic");
		String UserDate = aForm.getString("date");
		String WhoOid = aForm.getString("whoOid");
		String Place = aForm.getString("place");
		String Suggestion = aForm.getString("suggestion");
		
		
		List mailList = manager.ezGetBy("Select se.Email Email, se.Name cname From SuggestionEmail se Where ParentOid='"+WhoOid+"'");//, SuggestionDept sd Where se.ParentOid=sd.Oid And ");
		//System.out.println(mailList);
			
		StringBuffer content = new StringBuffer();
		content.append("教職員意見反映內容: <br>");
		content.append("教職員姓名:"+UserName+"<br>");
		content.append("教職員Email: "+UserEmail+"<br>");
		content.append("反映主旨: "+Topic+"<br>");
		content.append("反映日期: "+UserDate+"<br>");
		content.append("反映對象: "+manager.ezGetString("Select Name From SuggestionDept Where Oid='"+WhoOid+"'")+"<br>");
		content.append("反映地點: "+Place+"<br>");
		content.append("反映內容: "+Suggestion+"<br>");
		
		FileDataSource[] file = null;
		Map map=manager.getCISMailServerInfo();
		Date adate=new Date();
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
				"教職員意見反映通知", 
				adate, 
				UserName+"-"+Topic, 
				content.toString(), 
				address, 
				file
		);
		
		//Member member = getUserCredential(session).getMember();
		//EmplOpinionSuggestion eos = processRequest(member, aForm);
		
		/*

CREATE TABLE `EmplOpinionSuggestion` (
  `Oid` int(11) NOT NULL auto_increment,
  `Idno` varchar(10) collate utf8_unicode_ci NOT NULL,
  `Email` varchar(45) collate utf8_unicode_ci default NULL,
  `Topic` varchar(45) collate utf8_unicode_ci default NULL COMMENT '對應到SuggestionDept的Oid',
  `Date` datetime default NULL,
  `Target` varchar(45) collate utf8_unicode_ci default NULL,
  `Place` varchar(45) collate utf8_unicode_ci default NULL,
  `Content` text collate utf8_unicode_ci,
  `LastModified` datetime NOT NULL,
  PRIMARY KEY  (`Oid`)
) ENGINE=InnoDB AUTO_INCREMENT=2362 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;		

		*/
		
		manager.executeSql(
				"Insert Into EmplOpinionSuggestion(" +
				"       Idno, Email, Topic, Date, Target, Place, Content) " +
				"Values('"+UserIdno+"','"+UserEmail+"','"+Topic+"','"+UserDate+"','"+WhoOid+"'," +
					   "'"+Place+"','"+Suggestion+"')");
		
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Course.messageN1", "意見反映已寄發給相關單位,謝謝指教"));
		saveMessages(request, messages);
		setContentPage(request.getSession(false), "OpinionSuggestion.jsp");	
		return unspecified(mapping, form, request, response);
		
	}
	/*
	private EmplOpinionSuggestion processRequest(Member member,
			DynaActionForm form) {
		EmplOpinionSuggestion sos = new EmplOpinionSuggestion();
		sos.setIdno(member.getIdno());
		sos.setEmail(form.getString("email").trim());
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
