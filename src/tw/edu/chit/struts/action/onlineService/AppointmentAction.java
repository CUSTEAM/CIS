package tw.edu.chit.struts.action.onlineService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class AppointmentAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		HttpSession session = request.getSession(false);
		UserCredential user = (UserCredential) session.getAttribute("Credential");		
		CourseManager manager = (CourseManager) getBean("courseManager");			
		request.setAttribute("myAccount", user.getMember().getAccount());
		
		Calendar c=Calendar.getInstance();		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sft=new SimpleDateFormat("HH:mm");		
		int count=0;		
		List table=new ArrayList();
		Map map;
		String tmpDate;
		
		//取最近2周(共4次符合)
		while(count<=4){			
			c.add(Calendar.DAY_OF_MONTH, 1);
			tmpDate=sf.format(c.getTime());
			map=new HashMap();
			//目前開放預約的是周一:3，周五:6
			if(c.get(Calendar.DAY_OF_WEEK)==6 || c.get(Calendar.DAY_OF_WEEK)==3){
				map.put("date4tw", manager.convertDate(tmpDate));
				map.put("date4ad", sf.format(c.getTime()));
				map.put("weekDay", manager.ChWeekOfDay(c.get(Calendar.DAY_OF_WEEK), "星期"));
				//探測是否為假日
				if(manager.ezGetInt("SELECT COUNT(*)FROM AMS_Holiday WHERE Date='"+tmpDate+"'")<1){
					count=count+1;//計數器+1
					table.add(map);
				}
			}			
		}
		
		List tmpList;
		Map tmpMap;
		
		for(int i=0; i<table.size(); i++){
			
			c.set(Calendar.HOUR_OF_DAY, 12);
			c.set(Calendar.MINUTE, 30);
			
			tmpDate=((Map)table.get(i)).get("date4ad").toString();			
			tmpList=new ArrayList();
			for(int j=1; j<=6; j++){				
				tmpMap=new HashMap();
				tmpMap.put("time", sft.format(c.getTime()));//裝入時間
				try{
					//已預約的傢伙
					tmpMap.putAll(manager.ezGetMap("SELECT * FROM ClinicService WHERE cdate='"+tmpDate+"' AND sequence="+j));
				}catch(Exception e){
					//不曉得要幹麻
				}
				tmpMap.put("sequence", j);
				tmpList.add(tmpMap);
				c.add(Calendar.MINUTE, 10);//間隔10分鐘
			}			
			((Map)table.get(i)).put("list", tmpList);			
		}	
		
		request.setAttribute("table", table);		
		setContentPage(request.getSession(false), "onlineService/Appointment.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 取消
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm sForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");	
		manager.executeSql("DELETE FROM ClinicService WHERE Oid="+sForm.getString("Oid"));
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 掛號
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward registerClinic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm sForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		
		String cdate=sForm.getString("cdate");
		String sequence=sForm.getString("sequence");
		
		try{
			manager.executeSql("INSERT INTO ClinicService(cdate, patient, sequence)VALUES('"+manager.convertDate(cdate)+"', '"+
					user.getMember().getAccount()+"', '"+sequence+"')");
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","掛號完成"));
			saveMessages(request, msg);
		}catch(Exception e){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "掛號失敗"));
			saveErrors(request, error);
		}		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("RegisterClinic", "registerClinic");
		map.put("Cancel", "cancel");
		return map;
	}
	
	/**
	 * 錢
	 * @param student_no
	 * @param docNo
	 * @param money
	 * @param connects
	 
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
	
	
	//退回
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
	
	//三缺一
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
	
	//完成/現場/郵寄
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
	
	
	 	//取郵件伺服器ate Map MailInfo(){
		CourseManager manager = (CourseManager) getBean("courseManager");
		Map map=new HashMap();		
		map.put("username", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='username' AND Category='smtp'"));
		map.put("password", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='password' AND Category='smtp'"));
		map.put("mailServer", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='mailServer' AND Category='smtp'"));
		map.put("mailAddress", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='mailAddress' AND Category='smtp'"));
		return map;
	}
	
	
	//取學生名字
	private String studentName(String student_no){
		CourseManager manager = (CourseManager) getBean("courseManager");
		if(manager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE student_no='"+student_no+"'")>0){
			return manager.ezGetString("SELECT student_name FROM stmd WHERE student_no='"+student_no+"'");
		}else{
			return manager.ezGetString("SELECT student_name FROM Gstmd WHERE student_no='"+student_no+"'");
		}
	}
	*/
}
