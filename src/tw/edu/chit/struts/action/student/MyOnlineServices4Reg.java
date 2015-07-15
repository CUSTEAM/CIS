package tw.edu.chit.struts.action.student;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
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

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import tw.edu.chit.model.OnlineService4reg;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class MyOnlineServices4Reg extends BaseLookupDispatchAction{
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");//澄入資訊
		
		String student_no;
		if(c.getStudent()!=null){//在校生
			session.setAttribute("me", c.getStudent());
			student_no=c.getStudent().getStudentNo();
		}else{//離校生
			session.setAttribute("me", c.getGstudent());
			student_no=c.getGstudent().getStudentNo();
		}
		
		boolean noScore=true;
		if(manager.ezGetInt("SELECT COUNT(*)FROM ScoreHist WHERE student_no='"+student_no+"'")<1){
			noScore=false;
		}
		session.setAttribute("noScore", noScore);
		
		List services=manager.ezGetBy("SELECT * FROM OnlineService4Reg WHERE student_no='"+student_no+"' AND " +
				"(status<>'R' AND status<>'C')");
		if(services.size()>0){
			Map myServices=new HashMap();
			myServices.put("countServ", services);
			for(int i=0; i<services.size(); i++){
				
				if(((Map)services.get(i)).get("get_method").equals("1")){
					((Map)services.get(i)).put("get_method", "台北校區");
				}
				if(((Map)services.get(i)).get("get_method").equals("2")){
					((Map)services.get(i)).put("get_method", "新竹校區");
				}
				if(((Map)services.get(i)).get("get_method").equals("M")){
					((Map)services.get(i)).put("get_method", "郵寄");
				}
			}
			session.setAttribute("myServices", myServices);
		}
		
		/*
		ActionMessages msg=new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "自 97年12月1日 至 12月10日系統測試期間, 申請無效"));
		saveMessages(request, msg);
		*/
		setContentPage(request.getSession(false), "student/MyOnlineServices4Reg.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 繼續
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward Continue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;

		String telphone=sForm.getString("telphone");
		String cellphone=sForm.getString("cellphone");
		//String curr_post=sForm.getString("curr_post");
		String curr_addr=sForm.getString("curr_addr");
		String email=sForm.getString("email");
		
		String tcv=sForm.getString("tcv");
		String tcv_army=sForm.getString("tcv_army");
		String tcvigr=sForm.getString("tcvigr");
		String gcr=sForm.getString("gcr");
		String cscna=sForm.getString("cscna");
		String tev=sForm.getString("tev");
		String gcev=sForm.getString("gcev");
		
		String tev_n=sForm.getString("tev_n");
		//String tev_ak=sForm.getString("tev_ak");
		//String tev_pb=sForm.getString("tev_pb");
		
		//String expect_time=sForm.getString("expect_time");
		//String complete_time=sForm.getString("complete_time");		
		
		
		if(
				tcv.equals("")&& 
				tcvigr.equals("")&& 
				gcr.equals("")&& 
				tcv_army.equals("")&&
				cscna.equals("")&& 
				tev.equals("")&& 
				gcev.equals("")
			){			
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "請問要申請哪一項服務? 需要幾份?"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		
		
		if(telphone.trim().equals("")||
		cellphone.trim().equals("")||
		curr_addr.trim().equals("")||
		email.trim().equals("")){
			
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "基本資料不可有任何空白"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		//若申請英文
		if(!tev.equals("")|| !gcev.equals("")){			
			if(tev_n.trim().equals("")//||
			//tev_ak.trim().equals("") //別名不需要
			//tev_pb.trim().equals("")//出生地
			){				
				ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "英文必填欄位不可有任何空白"));
				saveErrors(request, error);
				return mapping.findForward("Main");					
			}
		}
		
		session.setAttribute("OnlineService4Reg", counTimeMoney(sForm.getMap()));
		return mapping.findForward("Main");
	}
	
	/**
	 * 完成
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward complete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		//UserCredential c = (UserCredential) session.getAttribute("Credential");//澄入資訊
		DynaActionForm sForm = (DynaActionForm) form;		
		
		Map os4r=(Map)session.getAttribute("OnlineService4Reg");
		//System.out.println();
		if(Integer.parseInt(String.valueOf(sForm.getString("total_pay")))>1000){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "請再次確認您的數量"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		String schoolYear=String.valueOf(manager.getSchoolYear());
		String yearSize=String.valueOf(manager.ezGetInt("SELECT COUNT(*)FROM " +
				"OnlineService4Reg WHERE doc_no LIKE'"+manager.getSchoolYear()+"%'"));
		
		//算文號
		StringBuilder sb=new StringBuilder(String.valueOf(manager.getSchoolYear()));
		for(int i=0; i<10-(schoolYear.length()+yearSize.length()); i++){
			sb.append("0");
		}
		sb.append(yearSize);
		Date d=new Date();
		OnlineService4reg os=new OnlineService4reg();
		os.setStudentNo(String.valueOf(os4r.get("student_no")));
		os.setStatus("W");
		os.setTelphone(String.valueOf(os4r.get("telphone")));
		os.setCellphone(String.valueOf(os4r.get("cellphone")));
		os.setAddress(String.valueOf(os4r.get("curr_addr")));
		os.setEmail(String.valueOf(os4r.get("email")));
		os.setDocNo(sb.toString());
		os.setSendTime(d);
		
		if(!os4r.get("tcv").equals("")){
			os.setTcv(Short.parseShort(String.valueOf(os4r.get("tcv"))));
		}
		if(!os4r.get("tcvigr").equals("")){
			os.setTcvigr(Short.parseShort(String.valueOf(os4r.get("tcvigr"))));
		}
		if(!os4r.get("gcr").equals("")){
			os.setGcr(Short.parseShort(String.valueOf(os4r.get("gcr"))));
		}
		if(!os4r.get("cscna").equals("")){
			os.setCscna(Short.parseShort(String.valueOf(os4r.get("cscna"))));
		}
		if(!os4r.get("tev").equals("")){
			os.setTev(Short.parseShort(os4r.get("tev").toString()));
			os.setTevN(String.valueOf(os4r.get("tev_n")));
			os.setTevAk(String.valueOf(os4r.get("tev_ak")));
			os.setTevPb(String.valueOf(os4r.get("tev_pb")));
		}
		if(!os4r.get("gcev").equals("")){
			os.setGcev(Short.parseShort(os4r.get("gcev").toString()));
		}
		if(!os4r.get("tcv_army").equals("")){
			os.setTcvArmy(Short.parseShort(os4r.get("tcv_army").toString()));
		}
		
		os.setTotalPay(Integer.parseInt(String.valueOf(sForm.getString("total_pay"))));
		Calendar c=Calendar.getInstance();
		c.setTime(d);
		//c.add(Calendar.DAY_OF_MONTH, 1);
		c.roll(Calendar.DATE, Integer.parseInt(String.valueOf(os4r.get("day"))));
		os.setExpectTime(c.getTime());
		os.setGetMethod(sForm.getString("get_method"));
		os.setConnects(Short.valueOf("0"));
		manager.updateObject(os);
		
		ServletContext context = request.getSession().getServletContext();
		String schoolName=(String) context.getAttribute("SchoolName_ZH");
		
		//登入郵件伺服器帳號
		String username=manager.ezGetString("SELECT Value FROM Parameter WHERE Name='username' AND Category='smtp'");
		//登入郵件伺服器密碼
		String password=manager.ezGetString("SELECT Value FROM Parameter WHERE Name='password' AND Category='smtp'");
		//郵件伺服器
		String mailServer =manager.ezGetString("SELECT Value FROM Parameter WHERE Name='mailServer' AND category='smtp'");
		//系統郵件地址
		String mailAddress=manager.ezGetString("SELECT Value FROM Parameter WHERE Name='mailAddress' AND category='smtp'");// 寄件伺服器
		FileDataSource files[]=null;
		
		String s="http://ap.cust.edu.tw/CIS/getOnlineServiceDoc?docNo="+os.getDocNo();
		if(!manager.testOnlineServer()){
			s="http://192.192.237.19/CIS/getOnlineServiceDoc?docNo="+os.getDocNo();
		}
		
		
		try{			
			URL url = new URL(s);
			URLConnection uc = url.openConnection();
			byte[] buffer = new byte[8192];
			int bytesRead = 0;
			File f=new File(os.getDocNo()+".doc");
			InputStream stream=uc.getInputStream();			 
			OutputStream bos=new FileOutputStream(f);	 
			uc.getInputStream();
			while((bytesRead = stream.read(buffer, 0, 8192)) != -1){
				bos.write(buffer, 0, bytesRead);
			}
			files=new FileDataSource[]{new FileDataSource(f)};
			stream.close();
			bos.close();
		}catch(Exception e){
			e.printStackTrace();
		}		
		
		//寄件給參與同仁
		List list=manager.getEmpl(String.valueOf(os4r.get("student_no")));
		StringBuilder content=new StringBuilder("各位");
		if(list.size()>1){
			content.append("同仁們您好!");
		}else{
			try{
				content.append(((Map)list.get(0)).get("cname")+" 您好!");
			}catch(Exception e){
				content.append("敬啟者");
			}
			
		}
		
		InternetAddress[] address;
		//若為正式伺服機
		if(manager.testOnlineServer()){
			address=new InternetAddress[list.size()];
			for(int i=0; i<list.size(); i++){
				address[i]=new InternetAddress( ((Map)list.get(i)).get("Email").toString(), ((Map)list.get(i)).get("cname").toString(),"Big5");		
			}
		}else{//若為測試機就只寄我
			address=new InternetAddress[]{new InternetAddress("hsiao@cc.cust.edu.tw", "蕭國裕","Big5")};
		}
		
		//寄
		if(!
		manager.sendMail(
		username, 
		password, 
		mailServer, 
		mailAddress, 
		schoolName+"校務資訊系統", 
		d, 
		"案件編號"+sb.toString()+" 已提出申請", 
		content.toString()+"<br><br>案件編號"+sb.toString()+" 已提出申請", 
		address, 
		files)){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "無法連接郵件主機"));
			saveErrors(request, error);
		}
		
		String student_name="";
		if(manager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE student_no='"+String.valueOf(os4r.get("student_no"))+"'")>0){
			student_name=manager.ezGetString("SELECT student_name FROM stmd WHERE student_no='"+String.valueOf(os4r.get("student_no"))+"'");
		}else{
			student_name=manager.ezGetString("SELECT student_name FROM Gstmd WHERE student_no='"+String.valueOf(os4r.get("student_no"))+"'");
		}
		
		//寄給學生
		address=new InternetAddress[]{new InternetAddress( String.valueOf(os4r.get("email")), student_name)};
		//寄
		if(!manager.sendMail(
		username, 
		password, 
		mailServer, 
		mailAddress, 
		schoolName+"校務資訊系統", 
		d, 
		"案件編號"+sb.toString()+" 已提出申請", 
		"<font face='標楷體' size='+2'>"+student_name+"同學 / 校友您好</font><br><br>" +
		"<font face='標楷體' size='+1'>本校相關單位已收到您的申請，編號"+sb+"號，正在等待處理中<br><br>"+manager.getSchInfo(String.valueOf(os4r.get("student_no"))), 
		address, 
		files)){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "無法連接郵件主機"));
			saveErrors(request, error);
		}
		
		session.removeAttribute("OnlineService4Reg");
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","申請完成"));
		saveMessages(request, msg);
		return unspecified(mapping, form, request, response);
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
		HttpSession session = request.getSession(false);
		session.removeAttribute("OnlineService4Reg");
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","已取消"));
		saveMessages(request, msg);
		return unspecified(mapping, form, request, response);
	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Continue", "Continue");
		map.put("OK", "complete");
		map.put("Modify", "cancel");
		return map;
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	private Map counTimeMoney(Map map){
		
		int tcv_m=10;//成績單
		int tcvigr_m=15;//成績單附排名
		int gcr_m=50;//畢業證書
		int cscna_m=50;//修業證書
		int tev_m=40;//英文成績單
		int gcev_m=50;//英文畢業證書
		
		int total=0;
		int day=0;
		
		int tcv_t=1;
		int tcv_army_t=1;
		int tcvigr_t=5;
		int gcr_t=1;
		int cscna_t=1;
		int tev_t=5;
		int gcev_t=5;
		
		int x;
		if(!map.get("tcv").equals("")){
			x=Integer.parseInt(map.get("tcv").toString())*tcv_m;
			total=total+x;
			map.put("tcv_m", x);
			//total=total+tcv_m;
			day=tcv_t;
		}
		if(!map.get("tcv_army").equals("")){
			x=Integer.parseInt(map.get("tcv_army").toString())*tcv_m;
			total=total+x;
			map.put("tcv_army_m", x);
			//total=total+tcv_m;
			day=tcv_army_t;
		}
		if(!map.get("gcr").equals("")){
			x=Integer.parseInt(map.get("gcr").toString())*gcr_m;
			total=total+x;
			map.put("gcr_m", x);			
			if(tcvigr_t>day){
				day=gcr_t;
			}
		}		
		if(!map.get("tcvigr").equals("")){
			x=Integer.parseInt(map.get("tcvigr").toString())*tcvigr_m;
			total=total+x;
			map.put("tcvigr_m", x);			
			if(tcvigr_t>day){
				day=tcvigr_t;
			}
		}		
		if(!map.get("cscna").equals("")){
			x=Integer.parseInt(map.get("cscna").toString())*cscna_m;
			total=total+x;
			map.put("cscna_m", x);			
			if(cscna_t>day){
				day=cscna_t;
			}
		}		
		if(!map.get("tev").equals("")){
			x=Integer.parseInt(map.get("tev").toString())*tev_m;
			map.put("tev_m", x);
			total=total+x;
			if(tev_t>day){
				day=tev_t;
			}
		}
		if(!map.get("gcev").equals("")){
			x=Integer.parseInt(map.get("gcev").toString())*gcev_m;
			map.put("gcev_m", x);
			total=total+x;
			if(gcev_t>day){
				day=gcev_t;
			}
		}
		map.put("total", total);
		map.put("day", day);
		
		return map;
	}
}
