package tw.edu.chit.struts.action.onlineService;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.MailStorage;
import tw.edu.chit.model.OnlineService4reg;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class CaseViewAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");//澄入資訊
		session.setAttribute("myPriority", c.getMember().getPriority());		
		setContentPage(request.getSession(false), "onlineService/CaseView.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 查
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {		
		
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;
		
		String student_no=sForm.getString("student_no");
		String status=sForm.getString("aStatus");
		String doc_no=sForm.getString("doc_no");
		String send_time_start=sForm.getString("send_time_start");
		String send_time_end=sForm.getString("send_time_end");
		String expect_time_start=sForm.getString("expect_time_start");
		String expect_time_end=sForm.getString("expect_time_end");
		String complete_time_start=sForm.getString("complete_time_start");
		String complete_time_end=sForm.getString("complete_time_end");
		String get_method=sForm.getString("get_method");		
		
		//非在校生的清單
		List list=new ArrayList();
		StringBuilder sb=new StringBuilder("SELECT os.*, st.student_name FROM OnlineService4Reg os, Gstmd st WHERE " +
		"os.student_no=st.student_no AND os.doc_no LIKE '"+doc_no+"%' AND " +
		"os.student_no LIKE '"+student_no+"%' AND os.get_method LIKE'"+get_method+"%' ");		
		StringBuilder sb1=new StringBuilder();
		
		
		if(!status.equals("")){
			sb1.append("AND os.status ='"+status+"'");
		}
		
		if(!send_time_start.equals("")){
			sb1.append("AND os.send_time>='"+send_time_start+"'");
		}
		if(!send_time_end.equals("")){
			sb1.append("AND os.send_time<='"+send_time_end+"'");
		}
		if(!expect_time_start.equals("")){
			sb1.append("AND os.expect_time>='"+expect_time_start+"'");
		}
		if(!expect_time_end.equals("")){
			sb1.append("AND os.expect_time<='"+expect_time_end+"'");
		}
		if(!complete_time_start.equals("")){
			sb1.append("AND os.complete_time>='"+complete_time_start+"'");
		}
		if(!complete_time_end.equals("")){
			sb1.append("AND os.complete_time<='"+complete_time_end+"'");
		}		
		sb.append(sb1);
		sb.append(" ORDER BY os.doc_no");
		
		
		//追加在校生
		list.addAll(manager.ezGetBy(sb.toString()));		
		sb=new StringBuilder("SELECT os.*, st.student_name FROM OnlineService4Reg os, stmd st WHERE " +
		"os.student_no=st.student_no AND os.doc_no LIKE '"+doc_no+"%' AND " +
		"os.student_no LIKE '"+student_no+"%' AND os.get_method LIKE'"+get_method+"%' ");
		sb.append(sb1);
		sb.append(" ORDER BY os.doc_no");
		list.addAll(manager.ezGetBy(sb.toString()));
		
		Date send_time;
		Date expect_time;
		Date now=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		for(int i=0; i<list.size(); i++){
			
			//if(((Map)list.get(i)).get("status").equals("W")||((Map)list.get(i)).get("status").equals("O")){//若在埶行狀態
			send_time=sf.parse(((Map)list.get(i)).get("send_time").toString());
			expect_time=sf.parse(((Map)list.get(i)).get("expect_time").toString());				
			
			//一般綠色？
			((Map)list.get(i)).put("alert", "<table align=left bgColor=#8fbc8f cellpadding=0 cellspacing=0><tr><td><img src='images/icon/time.gif'/></td></tr></table>");
			
			if(expect_time.getTime()-now.getTime()<86400000){//若現在時間減到期日小於86400000  橘色警告
				((Map)list.get(i)).put("alert", "<table align=left bgColor=#ffa500 cellpadding=0 cellspacing=0><tr><td><img src='images/icon/time.gif'/></td></tr></table>");
			}
			if(now.getTime()>expect_time.getTime()){// 紅色超時
				((Map)list.get(i)).put("alert", "<table align=left bgColor=#dc143c cellpadding=0 cellspacing=0><tr><td><img src='images/icon/time.gif'/></td></tr></table>");
			}
			
			((Map)list.get(i)).put("send_time", manager.convertDate(sf.format(send_time)));
			((Map)list.get(i)).put("expect_time", manager.convertDate(sf.format(expect_time)));	
			//}
						
			if(((Map)list.get(i)).get("get_method").equals("1")){
				((Map)list.get(i)).put("get_method", "台北校區");
			}
			if(((Map)list.get(i)).get("get_method").equals("2")){
				((Map)list.get(i)).put("get_method", "新竹校區");
			}
			if(((Map)list.get(i)).get("get_method").equals("M")){
				((Map)list.get(i)).put("get_method", "郵寄");
			}
		}
		
		/*排序
		Collections.sort(list, new Comparator<Map>() {
            public int compare(Map o1, Map o2) {
                String s1 = o1.get("doc_no").toString();
                String s2 = o2.get("doc_no").toString();
                if (s1 == null && s2 == null) {
                    return 0;
                } else if (s1 == null) {
                    return -1;
                } else if (s2 == null) {
                    return 1;
                } else {
                    return s1.compareTo(s2);
                }
            }
        });
		*/
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		if(list.size()>50){			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢完成, 共 "+list.size()+"筆, 提共前50筆供管理"));			
		}else{
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢完成, 共 "+list.size()+"筆"));
		}
		saveMessages(request, msg);
		
		//session.setAttribute("cases", list);
		session.setAttribute("cases", manager.sortListByKey(list, "doc_no"));
		
		sb=null;
		send_time=null;
		expect_time=null;
		now=null;
		list=null;
		System.gc();
		
		
		setContentPage(request.getSession(false), "onlineService/CaseView.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;		
		session.removeAttribute("cases");		
		sForm.set("student_no", "");
		sForm.set("aStatus", "");
		sForm.set("doc_no", "");
		sForm.set("send_time_start", "");
		sForm.set("send_time_end", "");
		sForm.set("expect_time_start", "");
		sForm.set("expect_time_end", "");
		sForm.set("complete_time_start", "");
		sForm.set("complete_time_end", "");
		sForm.set("get_method", "");
		sForm.set("studentName", "");
		//setContentPage(request.getSession(false), "onlineService/CaseView.jsp");
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward ok(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm sForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");//澄入資訊
		OnlineService4reg os4g;
		String Oid[]=sForm.getStrings("Oid");
		String status[]=sForm.getStrings("status");
		String note[]=sForm.getStrings("note");
		String checkOid[]=sForm.getStrings("checkOid");
		Map mailInfo=MailInfo();
		ServletContext context = request.getSession().getServletContext();
		String schoolName=(String) context.getAttribute("SchoolName_ZH");
		for(int i=0; i<Oid.length; i++){
			boolean send=false;			
			os4g=(OnlineService4reg) manager.hqlGetBy("FROM OnlineService4reg WHERE Oid='"+Oid[i]+"'").get(0);//找到這筆				
			if(!status[i].equals("")){//若不是空白
				os4g.setStatus(status[i]);//若不為空白則存
			}
			os4g.setNote(note[i]);//無論如何都save note			
			
			//有要執行通知(修改痕跡)的
			if(!checkOid[i].equals("")){				
				//直接退件的情形 status="B"
				if(status[i].equals("B")){	
					backCase(os4g.getStudentNo(), os4g.getDocNo(), mailInfo, os4g.getEmail(), note[i], schoolName);
					os4g.setTerminator(c.getMember().getIdno());//存結案人
					os4g.setCloser(c.getMember().getIdno());
					send=true;
				}				
				//結案了
				if(status[i].equals("C")){
					colsed(os4g.getStudentNo(), os4g.getDocNo(), mailInfo, schoolName);
					os4g.setCloser(c.getMember().getIdno());
					send=true;
				}				
				if(!send){
					normal(os4g.getStudentNo(), os4g.getDocNo(), mailInfo, os4g.getEmail(), note[i], status[i], schoolName);					
				}
				short x=(short) (Short.parseShort("1")+os4g.getConnects());
				os4g.setConnects(x);
			}
			
			manager.updateObject(os4g);//儲存
		}
		return search(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Search", "search");
		map.put("Cancel", "cancel");
		map.put("OK", "ok");
		return map;
	}
	
	/**
	 * 退回
	 * @param student_no
	 * @param docNo
	 * @param paper
	 * @param mail
	 * @param note
	 */
	private void backCase(String student_no, String docNo, Map paper, String mail, String note, String schoolName){
		String student_name=studentName(student_no);
		CourseManager manager = (CourseManager) getBean("courseManager");
		StringBuilder sb=new StringBuilder("<font face='標楷體' size='+2'>"+student_name+"同學 / 校友您好</font><br><br>" +
				"<font face='標楷體' size='+1'>玆收到您的申請，編號"+docNo+"號，已暫停辦理，原因是<br><br>");
		
		
		sb.append(note);
		Date date=new Date();
		sb.append(manager.getSchInfo(student_no));
		InternetAddress address[] = new InternetAddress[1];		
		try {
			address = new InternetAddress[]{new InternetAddress(mail, student_name, "BIG5")};
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manager.sendMail(
		paper.get("username").toString(), 
		paper.get("password").toString(), 
		paper.get("mailServer").toString(), 
		paper.get("mailAddress").toString(), 
		schoolName+"校務資訊系統", 
		date, 
		"案件編號"+docNo+"申請狀況通知", 
		sb.toString(), 
		address, 
		null);		
		//備份
		manager.saveMail("system", "連合服務中心", "案件編號"+docNo+"申請狀況通知", address, sb.toString(), date, paper.get("mailAddress").toString());
	}
	
	/**
	 * 未結傳訊
	 * @param student_no
	 * @param docNo
	 * @param paper
	 * @param mail
	 * @param note
	 * @param status
	 */
	private void normal(String student_no, String docNo, Map paper, String mail, String note, String status, String schoolName){
		String student_name=studentName(student_no);
		CourseManager manager = (CourseManager) getBean("courseManager");
		StringBuilder sb=new StringBuilder("<font face='標楷體' size='+2'>"+student_name+"同學 / 校友您好</font><br><br>" +
				"<font face='標楷體' size='+1'>玆收到您的申請，編號"+docNo+"號，");
		if(status.equals("W")){
			sb.append("正等待處理中。</font><br><br>");
		}
		if(status.equals("O")){
			sb.append("已在處理中。</font><br><br>");
		}
		if(status.equals("R")){
			sb.append("很抱歉無法完成您的申請。</font><br><br>");
		}		
		
		if(!note.trim().equals("")){
			sb.append("備註: "+note);
		}
		
		Date date=new Date();
		sb.append(manager.getSchInfo(student_no));
		InternetAddress address[] = new InternetAddress[1];		
		try {
			address = new InternetAddress[]{new InternetAddress(mail, student_name, "BIG5")};
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manager.sendMail(
		paper.get("username").toString(), 
		paper.get("password").toString(), 
		paper.get("mailServer").toString(), 
		paper.get("mailAddress").toString(), 
		schoolName+"校務資訊系統", 
		date, 
		"案件編號"+docNo+"申請狀況通知", 
		sb.toString(), 
		address, 
		null);
		//備份
		manager.saveMail("system", "連合服務中心", "案件編號"+docNo+"申請狀況通知", address, sb.toString(), date, paper.get("mailAddress").toString());
	}
	
	/**
	 * 結案了
	 * @param student_no
	 * @param docNo
	 * @param paper
	 * @param mail
	 * @param note
	 * @param status
	 */
	private void colsed(String student_no, String docNo, Map paper, String schoolName){
		String student_name=studentName(student_no);
		CourseManager manager = (CourseManager) getBean("courseManager");
		Date date=new Date();
		Map empl=manager.ezGetMap("SELECT e.cname, e.Email FROM empl e, OnlineService4Reg o " +
				"WHERE e.idno=o.terminator AND o.doc_no='"+docNo+"'");		
		if(empl!=null){//不正常狀況結案不寄任何
			StringBuilder sb=new StringBuilder(student_name+"同仁您好<br><br>" +
					"申請編號"+docNo+"號，");			
			sb.append("已順利結案<br><br>");		
			sb.append(manager.getSchInfo(student_no));
			InternetAddress address[] = new InternetAddress[1];				
			try {
				address = new InternetAddress[]{new InternetAddress(empl.get("Email").toString(), empl.get("cname").toString(), "BIG5")};
				manager.sendMail(
				paper.get("username").toString(), 
				paper.get("password").toString(), 
				paper.get("mailServer").toString(), 
				paper.get("mailAddress").toString(), 
				schoolName+"校務資訊系統", 
				date, 
				"案件編號"+docNo+"結案通知", 
				sb.toString(), 
				address, 
				null);
				//備份
				manager.saveMail("system", "連合服務中心", "案件編號"+docNo+"申請狀況通知", address, sb.toString(), date, paper.get("mailAddress").toString());
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}
	}	
	
	/**
	 * 取郵件伺服器
	 * @return
	 */
	private Map MailInfo(){
		CourseManager manager = (CourseManager) getBean("courseManager");
		Map map=new HashMap();		
		map.put("username", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='username' AND Category='smtp'"));
		map.put("password", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='password' AND Category='smtp'"));
		map.put("mailServer", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='mailServer' AND Category='smtp'"));
		map.put("mailAddress", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='mailAddress'"));
		return map;
	}
	
	/**
	 * 取學生名字
	 * @param student_no
	 * @return
	 */
	private String studentName(String student_no){
		CourseManager manager = (CourseManager) getBean("courseManager");
		if(manager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE student_no='"+student_no+"'")>0){
			return manager.ezGetString("SELECT student_name FROM stmd WHERE student_no='"+student_no+"'");
		}else{
			return manager.ezGetString("SELECT student_name FROM Gstmd WHERE student_no='"+student_no+"'");
		}
	}
}
