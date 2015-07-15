package tw.edu.chit.struts.action.onlineService;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.OnlineService4reg;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class CaseManagerAction extends BaseLookupDispatchAction{
	
	//final String a=""; 
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential) session.getAttribute("Credential");		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		//重取線上		
		List list=manager.ezGetBy("SELECT os.*, st.student_name FROM OnlineService4Reg os, Gstmd st, " +
				"empl e, ClassInCharge c, UnitBelong u WHERE e.Oid=u.EmpOid AND u.UnitNO " +
				"IN('B1', 'B3', 'B5', 'B4') AND os.student_no=st.student_no AND e.Oid=c.EmpOid AND " +
				"c.ClassNo=st.depart_class AND (os.status='W' OR os.status='O') AND c.ModuleOids<>'|87|' AND e.idno='"+credential.getMember().getIdno()+"' ORDER BY os.doc_no");
		list.addAll(manager.ezGetBy("SELECT os.*, st.student_name FROM OnlineService4Reg os, stmd st, " +
				"empl e, ClassInCharge c, UnitBelong u WHERE e.Oid=u.EmpOid AND u.UnitNO " +
				"IN('B1', 'B3', 'B5', 'B4') AND os.student_no=st.student_no AND e.Oid=c.EmpOid AND " +
				"c.ClassNo=st.depart_class AND (os.status='W' OR os.status='O') AND c.ModuleOids<>'|87|' AND e.idno='"+credential.getMember().getIdno()+"' ORDER BY os.doc_no"));
		
		Date send_time;
		Date expect_time;
		Date now=new Date();
		
		for(int i=0; i<list.size(); i++){
			
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
		session.setAttribute("myOnlineWork", list);
		
		//取離線
		list=manager.ezGetBy("SELECT os.*, st.student_name FROM OnlineService4Reg os, Gstmd st, " +
				"empl e, ClassInCharge c, UnitBelong u WHERE e.Oid=u.EmpOid AND u.UnitNO " +
				"IN('B1', 'B3', 'B5', 'B4') AND os.student_no=st.student_no AND e.Oid=c.EmpOid AND " +
				"c.ClassNo=st.depart_class AND os.status IN('B', 'F') AND c.ModuleOids<>'|87|' AND e.idno='"+credential.getMember().getIdno()+"'");
		list.addAll(manager.ezGetBy("SELECT os.*, st.student_name FROM OnlineService4Reg os, stmd st, " +
				"empl e, ClassInCharge c, UnitBelong u WHERE e.Oid=u.EmpOid AND u.UnitNO " +
				"IN('B1', 'B3', 'B5', 'B4') AND os.student_no=st.student_no AND e.Oid=c.EmpOid AND " +
				"c.ClassNo=st.depart_class AND os.status IN('B', 'F') AND c.ModuleOids<>'|87|' AND e.idno='"+credential.getMember().getIdno()+"'"));
		
		for(int i=0; i<list.size(); i++){		
			
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
		session.setAttribute("myOffLineWork", list);		
		session.setAttribute("countMySrvs", manager.ezGetInt("SELECT COUNT(*)FROM OnlineService4Reg WHERE terminator='"+credential.getMember().getIdno()+"'"));
		
		list=null;
		send_time=null;
		expect_time=null;
		now=null;
		credential=null;
		System.gc();
		
		setContentPage(request.getSession(false), "onlineService/CaseManager.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 完成所有
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward complete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		DynaActionForm sForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		String schoolName=(String) context.getAttribute("SchoolName_ZH");
		UserCredential c = (UserCredential) session.getAttribute("Credential");//澄入資訊
		OnlineService4reg os4g;
		String Oid[]=sForm.getStrings("Oid");
		String status[]=sForm.getStrings("status");
		String note[]=sForm.getStrings("note");
		String checkOid[]=sForm.getStrings("checkOid");
		
		String passport[]=sForm.getStrings("passport");
		String idCard[]=sForm.getStrings("idCard");
		String familyBook[]=sForm.getStrings("familyBook");
		String payMoney[]=sForm.getStrings("payMoney");
		Map mailInfo=MailInfo();
		
		for(int i=0; i<Oid.length; i++){
			boolean send=false;
			//有要執行通知(修改痕跡)的
			if(!checkOid[i].equals("")){				
				os4g=(OnlineService4reg) manager.hqlGetBy("FROM OnlineService4reg WHERE Oid='"+Oid[i]+"'").get(0);//找到這筆
				
				if(!status[i].equals("")){
					os4g.setStatus(status[i]);
				}				
				os4g.setNote(note[i]);				
				//已完成並寄出的情形: status="M"
				if(status[i].equals("M")){					
					os4g.setCompleteTime(new Date());
					os4g.setTerminator(c.getMember().getIdno());//存結案人
					mailInfo.put("type", "M");
					mailInfo.put("note", note[i]);
					finWork(os4g.getStudentNo(), os4g.getDocNo(), os4g.getEmail(), mailInfo, schoolName);//寄					
					send=true;
				}	
				
				//已完成並來現場 status="F"
				if(status[i].equals("F")){
					mailInfo.put("note", note[i]);
					os4g.setCompleteTime(new Date());
					os4g.setTerminator(c.getMember().getIdno());//存結案人
					mailInfo.put("type", "F");
					mailInfo.put("getMethod", os4g.getGetMethod());
					finWork(os4g.getStudentNo(), os4g.getDocNo(), os4g.getEmail(), mailInfo, schoolName);//寄					
					send=true;
				}
				
				//直接退件的情形 status="B"
				if(status[i].equals("B")){	
					backCase(os4g.getStudentNo(), os4g.getDocNo(), mailInfo, os4g.getEmail(), note[i], schoolName);
					os4g.setCompleteTime(new Date());
					os4g.setTerminator(c.getMember().getIdno());//存結案人
					send=true;
				}
				
				//缺件 pass......familyBook!=""
				if(!passport[i].equals("")||!idCard[i].equals("")||!familyBook[i].equals("")){
					mailInfo.put("note", note[i]);
					if(!passport[i].equals("")){//缺護照
						mailInfo.put("passport", "N");	//N=缺				
					}else{
						mailInfo.put("passport", "Y");
					}
					
					if(!idCard[i].equals("")){//缺身份證
						mailInfo.put("idCard", "N");					
					}else{
						mailInfo.put("idCard", "Y");
					}
					
					if(!familyBook[i].equals("")){//缺戶口名
						mailInfo.put("familyBook", "N");					
					}else{
						mailInfo.put("familyBook", "Y");
					}
					if(!payMoney[i].equals("")){//缺錢
						mailInfo.put("payMoney", payMoney[i]);
					}
					noPaper(os4g.getStudentNo(), os4g.getDocNo(), mailInfo, os4g.getEmail(), schoolName);
					os4g.setTerminator(c.getMember().getIdno());//存結案人
					os4g.setNote(note[i]);
					send=true;
				}				
								
				//郵匯通知
				if(!payMoney[i].equals("")){
					mailInfo.put("note", note[i]);
					noPay(os4g.getStudentNo(), os4g.getDocNo(), payMoney[i], os4g.getConnects(), mailInfo, os4g.getEmail(), schoolName);
					//os4g.setCompleteTime(new Date());
					os4g.setTerminator(c.getMember().getIdno());//存結案人
					os4g.setNote(note[i]);
					send=true;
				}
				if(!send){
					normal(os4g.getStudentNo(), os4g.getDocNo(), mailInfo, os4g.getEmail(), note[i], status[i], schoolName);					
				}
				short x=(short) (Short.parseShort("1")+os4g.getConnects());
				os4g.setConnects(x);
				manager.updateObject(os4g);//儲存
				
			}
			
			else{//全部儲存
				
				//if(!note[i].equals("")){//若有填寫備註
				os4g=(OnlineService4reg) manager.hqlGetBy("FROM OnlineService4reg WHERE Oid='"+Oid[i]+"'").get(0);//找到這筆
				os4g.setNote(note[i]);
				os4g.setStatus(status[i]);
				manager.updateObject(os4g);
				//}
				
			}
		}
		
		
		
		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Complete", "complete");
		map.put("Cancle", "cancel");
		return map;
	}
	
	/**
	 * 錢
	 * @param student_no
	 * @param docNo
	 * @param money
	 * @param connects
	 */
	private void noPay(String student_no, String docNo, String money, int connects, Map paper, String mail, String schoolName){
		//SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd");
		//String date=sf.format(new Date());
		String student_name=studentName(student_no);
		CourseManager manager = (CourseManager) getBean("courseManager");
		StringBuilder sb=new StringBuilder("<font face='標楷體' size='+2'>"+student_name+"同學 / 校友您好</font><br><br>" +
				"<font face='標楷體' size='+1'>玆收到您的申請，編號"+docNo+"號，");		
		
		if(connects==0){//告知
			sb.append("已進入辦理階段，<br>請依下列方式處理<br><br>請您將申請費用"+money+"元 (購買郵局匯票抬頭"+schoolName+")、<br>A4回郵信封並註明您的申請編號: "+docNo+"<br>於3日內寄回學校<br><br>");
			sb.append(manager.getSchInfo(student_no));
			
		}else{//催
			sb.append("已辦理完成，<br>截至今日未收到您的申請費用"+money+"，請儘速處理，謝謝！<br><br>");			
			sb.append(manager.getSchInfo(student_no));
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
		
		sb.append(manager.getSchInfo(student_no));
		InternetAddress address[] = new InternetAddress[1];	
		Date date=new Date();
		try {
			address = new InternetAddress[]{new InternetAddress(mail, student_name, "BIG5")};
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		manager.sendMail(
		paper.get("username").toString(), 
		paper.get("password").toString(), 
		paper.get("mailServer").toString(), 
		paper.get("mailAddress").toString(), 
		schoolName+"務資訊系統", 
		date, 
		"案件編號"+docNo+"申請狀況通知", 
		sb.toString(), 
		address, 
		null);
		//備份
		manager.saveMail("system", "連合服務中心", "案件編號"+docNo+"申請狀況通知", address, sb.toString(), date, paper.get("mailAddress").toString());
	}
	
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
		
		if(!note.trim().equals("")){
			sb.append("備註: "+note);
		}
		
		
		sb.append(manager.getSchInfo(student_no));
		InternetAddress address[] = new InternetAddress[1];
		Date date=new Date();
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
	 * 三缺一
	 * @param student_no
	 * @param docNo
	 * @param paper
	 */
	private void noPaper(String student_no, String docNo, Map paper, String mail, String schoolName){
		String student_name=studentName(student_no);
		CourseManager manager = (CourseManager) getBean("courseManager");
		StringBuilder sb=new StringBuilder(
		"<font face='標楷體' size='+2'>"+student_name+"同學 / 校友您好</font><br><br>" +
		"<font face='標楷體' size='+1'>玆收到您的申請，編號"+docNo+"號，");
		
		try{
			sb.append("已進入辦理階段，尚缺少下列文件<br><br>");
			if(paper.get("passport").equals("N")){//缺護照
				sb.append("護照影本<br>");
			}
			if(paper.get("idCard").equals("N")){//缺護照
				sb.append("身分證影本<br>");
			}
			if(paper.get("familyBook").equals("N")){//缺護照
				sb.append("戶籍謄本<br>");
			}
			if(paper.get("payMoney")!=null){
				sb.append("郵政匯票"+paper.get("payMoney")+"元(購買郵局匯票抬頭"+schoolName+")<br>");
			}
		}catch(Exception e){
			sb.append("已重新進入辦理階段。<br>");
		}
		if(paper.get("note")!=null && !paper.get("note").equals("")){
			sb.append("<br><br>備註: "+paper.get("note"));
		}
		sb.append(manager.getSchInfo(student_no));
		InternetAddress address[] = new InternetAddress[1];
		Date date=new Date();
		try {
			address = new InternetAddress[]{new InternetAddress(mail, student_name, "BIG5")};
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
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 完成/現場/郵寄
	 * @param student_no
	 * @param docNo
	 * @param paper
	 */
	private void finWork(String student_no, String docNo, String mail, Map paper, String schoolName){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd");
		String date=sf.format(new Date());
		String student_name=studentName(student_no);
		CourseManager manager = (CourseManager) getBean("courseManager");
		StringBuilder sb=new StringBuilder("<font face='標楷體' size='+2'>"+student_name+"同學 / 校友您好</font><br><br>" +
				"<font face='標楷體' size='+1'>您的申請，編號"+docNo+"號，已辦理完成，");
		
		if(paper.get("type").equals("M")){//郵寄
			sb.append("並於"+date+"寄出");
		}else{
			sb.append("請於"+date+"起，1個月之內至 ");
			if(paper.get("getMethod").equals("1")){
				sb.append("台北校區聯合服務中心領取。");
			}
			if(paper.get("getMethod").equals("2")){
				sb.append("新竹校區聯合服務中心領取。");
			}
		}
		if(paper.get("note")!=null && !paper.get("note").equals("")){
			sb.append("</font><br><br>備註: "+paper.get("note"));
		}
		sb.append(manager.getSchInfo(student_no));
		InternetAddress address[] = new InternetAddress[1];
		Date newdate=new Date();
		try {
			address = new InternetAddress[]{new InternetAddress(mail, student_name, "BIG5")};
			manager.sendMail(
			paper.get("username").toString(), 
			paper.get("password").toString(), 
			paper.get("mailServer").toString(), 
			paper.get("mailAddress").toString(), 
			schoolName+"校務資訊系統", 
			newdate, 
			"案件編號"+docNo+"申請完成通知", 
			sb.toString(), 
			address, 
			null);		
			//備份
			manager.saveMail("system", "連合服務中心", "案件編號"+docNo+"申請狀況通知", address, sb.toString(), newdate, paper.get("mailAddress").toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		map.put("mailAddress", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='mailAddress' AND Category='smtp'"));
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
