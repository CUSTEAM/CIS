package tw.edu.chit.struts.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
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

import tw.edu.chit.gui.Menu;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class LoginReNewAction extends BaseAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
		HttpServletRequest request, HttpServletResponse response)throws Exception {			
		DynaActionForm aForm = (DynaActionForm)form;
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		
		if(aForm.getString("username").trim().length()<3||aForm.getString("password").trim().length()<3){			
			return mapping.findForward("failure");	
		}
		
		Map jumpUser=cm.ezGetMap("SELECT username, priority FROM wwpass WHERE username='"+aForm.getString("username")+"' AND password='"+aForm.getString("password")+"'");
		if(jumpUser==null){			
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","驗證錯誤"));
			saveErrors(request, error);
			return mapping.findForward("failure");
		}
		
		HttpSession session=request.getSession(false);
		//清除舊cookie
		Cookie cookie = new Cookie("userid", null);	    	
		cookie.setMaxAge(0);   	
    	cookie.setDomain(".cust.edu.tw");
    	cookie.setPath("/");
    	response.addCookie(cookie);   	
		
    	//寫cookie
		cookie = new Cookie("userid", request.getSession().getId()+aForm.getString("username").hashCode());	    	
		cookie.setMaxAge(60*60*24); // 暫1天有效	    	
    	cookie.setDomain(".cust.edu.tw");
    	cookie.setPath("/");
    	response.addCookie(cookie);
    	
    	//寫session cookie id	    	
    	cm.executeSql("UPDATE wwpass SET sessionid='"+
    	request.getSession().getId()+aForm.getString("username").hashCode()+
    	"' WHERE username='"+aForm.getString("username")+"'");   
		
    	if(jumpUser.get("priority").equals("C")){
    		response.sendRedirect("/stis/MyCalendar");//轉送至學生系統
    		return null;
    	}
    	
    	if(jumpUser.get("priority").equals("A")){
    		//有教課送到教師系統
    		if(cm.ezGetInt("SELECT COUNT(*)FROM Dtime WHERE techid='"+aForm.getString("username")+"'")>0){
    			response.sendRedirect("/tis/MyCalendar");//轉送至學生系統
    		}else{
    			//沒教課送回舊系統
    			response.sendRedirect("/CIS/Calendar/Directory.do");//轉送至學生系統
    		}    		
    		return null;
    	}
		
    	/**
    	 * 以下沒屁用了
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 */
		MemberManager mm = (MemberManager)getBean("memberManager");
		try {
			
			//CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			UserCredential user = mm.createUserCredential(aForm.getString("username"), aForm.getString("password").replaceAll("/", ""));
			if(user==null){
				ActionMessages error = new ActionMessages();
				error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("LoginInvalid", aForm.getString("username")));
				saveErrors(request, error);
				return mapping.findForward("failure");
			}
			
			Menu menu=new Menu();
			String path="/Redirect.do?path=/tis/MyCalendar";
			//教職員登入
			if (UserCredential.PrioEmployee.equals(user.getMember().getPriority())) {				
				menu=cm.putPriorityUniteMenu("A", menu);//加入教職員群組特定功能 TODO 是否細分為:教師或職員?
				
				List list=mm.createMenuByMember(user.getMember()).getItems();
				for(int i=0; i<list.size(); i++){
					menu.addItem(mm.createMenuByMember(user.getMember()).getItem(i));
				}
				
				menu=cm.putUniteMenu("A", menu);//加入教職員群組特定功能 TODO 是否細分為:教師或職員?
				menu=cm.putPrivateMenu(user.getMember().getAccount(), menu);//加入教職員個人特定功能				
				if(cm.ezGetInt("SELECT COUNT(*)FROM Dtime WHERE techid='"+user.getMember().getIdno()+"'")<1){
					//沒課的死職員死去舊系統
					path="/Calendar/Directory.do";
				}	
								
			//學生登入
			} else if (UserCredential.PrioStudent.equals(user.getMember().getPriority())) {	
				menu=cm.putPrivateMenu(user.getStudent().getStudentNo(), menu);//加入個人特定功能				
				menu=cm.putUniteMenu("C", menu);//加入學生群組特定功能
				path="/Redirect.do?path=/stis/MyCalendar";
			//校友登入
			}else if(UserCredential.PrioGraduate.equals(user.getMember().getPriority())){
				menu=new Menu();				
				menu=cm.putUniteMenu("L", menu);//加入校友群組特定功能(所有)
				//2009-7-30被告知某些校友仍然要使用特定功能??????
				//menu=cm.putPrivateMenu(user.getMember().getAccount(), menu);//加入學生個人特定功能(工讀生)
				path="/CIS/Calendar/Directory.do";
			}
									
			//HttpSession session=request.getSession(false);
			session = request.getSession(true);
			session.setAttribute("Credential", user);
			session.setAttribute("Menu", menu);			
			SaveMoreInfomation(response, request, session, user);//載入資訊
			
			return new ActionForward(path, false);
			
		} catch(Exception ie) {			
			//不為教職員、學生、離校人員			
			ie.printStackTrace();
			ActionMessages error = new ActionMessages();
			error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("LoginInvalid", aForm.getString("username")));
			saveErrors(request, error);
			return mapping.findForward("failure");					
		}		
	}
	
	/**
	 * 載入資訊提供複加服務
	 * @param session
	 * @param user
	 * @throws ParseException 
	 */
	private void SaveMoreInfomation(HttpServletResponse response, HttpServletRequest request, HttpSession session, UserCredential user)throws ParseException{
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		CourseManager manager = (CourseManager) getBean("courseManager");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		Date date=new Date();
		Date start;
		Date end;
		
		//載入快速選單
		request.setAttribute("fastmenu", manager.ezGetBy("SELECT m.color, m.Action, m.Icon, m.Label FROM Module m, User_Module_hist um WHERE " +
		"m.Oid=um.ModuleOid AND username='"+user.getMember().getAccount()+"' ORDER BY um.Oid DESC"));
		
    	//System.out.println("已建立cookie - userid:"+session.getId()+user.getMember().getAccount().hashCode());
    	manager.executeSql("UPDATE wwpass SET sessionid='"+
    	session.getId()+user.getMember().getAccount().hashCode()+
    	"' WHERE username='"+request.getParameter("username")+"'");		
    	
		String type="同學";
		if(user.getMember().getPriority().equals("A")){//若是同仁
			
			//線上服務
			if(session.getAttribute("myOnlineWork")==null){
				session.setAttribute("myOnlineWork", manager.getMyOnlineWork(user.getMember().getIdno()));
			}
			
			//教師評量
			start=sf.parse(manager.ezGetString("SELECT Value FROM Parameter WHERE Name='AQStart'"));//教學評量開始日
			end=sf.parse(manager.ezGetString("SELECT Value FROM Parameter WHERE Name='AQEnd'"));//教學評量結束日
			if((date.getTime()>=start.getTime() && date.getTime()<=end.getTime())){
				
				if(manager.ezGetInt("SELECT COUNT(*)FROM Dtime WHERE techid='"+user.getMember().getIdno()+"'")>0&&
						manager.ezGetString("SELECT category FROM empl WHERE idno='"+user.getMember().getIdno()+"'").equals("1")){//教師評量
					manager.SetAqForm(session,request);
				}
			}
						
			
		}else{//若是同學
			//不為校友			
			if(user.getMember().getPriority().equals("C")){				
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", user.getMember().getName()+"同學您好, 歡迎使用!"));
				Map map=manager.getStdCredit(user.getMember().getAccount());
				//(map.get("opt1")==null) ? System.out.println((map.get("opt1"))): System.out.println("...");			
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", map.get("opt1")!=null?"<br>必修已得"+map.get("opt1")+"學分<br>":""));
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", map.get("opt2")!=null?"選修已得"+map.get("opt2")+"學分<br>":""));
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", map.get("opt3")!=null?"通識已得"+map.get("opt3")+"學分":""));//, 查看系所 " +
				//"<a href='#' onClick=\"window.open('/CIS/getStdCredit.do?ClassNo="+
				//user.getStudent().getDepartClass()+"', 'preview', 'height=480,width=640, toolbar=0, location=0, menubar=0', 'false');\">" +
				//"學分規劃</a></div>"));				
				//語言檢定
				try{
					if(user.getStudent().getDepartClass().substring(1,3).equals("64")&&
							user.getStudent().getDepartClass().substring(4,5).equals("4")){						
						if(manager.ezGetString("SELECT language FROM gradresu WHERE student_no='"+user.getStudent().getStudentNo()+"'").equals("Y")){
							msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "<br>您已通過語言檢定"));
						}else{
							msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "<br>您目前尚未通過語言檢定"));
						}
					}
				}catch(Exception e){
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("<br>您目前尚未通過語言檢定"));
				}
				
				//證照
				try{
					
					List list=manager.ezGetBy("SELECT l.Name FROM StdSkill s, LicenseCode l WHERE " +
							"s.StudentNo='"+user.getStudent().getStudentNo()+"' AND l.Code=s.LicenseCode");
					if(list.size()==0){
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "<br>您目前尚未提出證照"));
					}else{
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "<br>您目前取得證照"));
					}
					for(int i=0; i<list.size(); i++){
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", ((Map)list.get(i)).get("Name").toString()+"<br>"));
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","<br><font size=-1><b>註<sup>1</sup></b> " +
				"學分數採及格成績即時統計,後續須經過畢業資格審查, <br><b>註<sup>2</sup></b> 學分規劃如變動仍以權責單位公佈為準</font>"));
			}
			//教學評量
			start=sf.parse(manager.ezGetString("SELECT Value FROM Parameter WHERE Name='CoanswStart'"));//教學評量開始日
			end=sf.parse(manager.ezGetString("SELECT Value FROM Parameter WHERE Name='CoanswEnd'"));//教學評量結束日
			manager.setCoansFoRm(date, session, user, start, end);
		}
		if(!msg.isEmpty())
		saveMessages(request, msg);		
	}
	
	/**
	 * 警告
	 * @param request
	 * @param msg
	 * @param aForm
	 * @return
	 */
	private ActionMessages getActionMessages(HttpServletRequest request, DynaActionForm aForm){
		ActionMessages msg = new ActionMessages();
		String username = aForm.getString("username");
		String password = aForm.getString("password");
		if (!"on".equals(request.getParameter("rememberme"))) {
			aForm.set("rememberme", "");
		}
		if (username.trim().equals("")) {
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請輸入帳號"));
			saveMessages(request, msg);
		}
		
		if (password.trim().equals("")) {
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請輸入密碼"));
			saveMessages(request, msg);
		}		
		return msg;
	}
	
	/**
	 * 錯誤
	 * @param request
	 * @param error
	 * @param form
	 * @return
	 */
	private ActionMessages getActionError(HttpServletRequest request, DynaActionForm aForm){
		ActionMessages error = new ActionMessages();
		CourseManager manager = (CourseManager) getBean("courseManager");
		String username = aForm.getString("username");
		
		int x=manager.ezGetInt("SELECT COUNT(*) FROM wwpass WHERE username='"+username+"'");
		if(x<1){
			x=manager.ezGetInt("SELECT COUNT(*) FROM Gstmd WHERE student_no='"+username+"'");
		}		
		if(x<1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","無此帳號")); 
			saveErrors(request, error);
		}
		return error;
	}
}
