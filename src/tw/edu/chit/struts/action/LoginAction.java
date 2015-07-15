package tw.edu.chit.struts.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import tw.edu.chit.gui.Menu;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class LoginAction extends BaseAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
		HttpServletRequest request, HttpServletResponse response)throws Exception {
		//System.out.println("LoginAction?");
		DynaActionForm aForm = (DynaActionForm)form;
		MemberManager mm = (MemberManager)getBean("memberManager");
		try {
			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			UserCredential user = mm.createUserCredential(aForm.getString("username"), aForm.getString("password").replaceAll("/", ""));
			if(user==null){
				ActionMessages error = new ActionMessages();
				error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("LoginInvalid", aForm.getString("username")));
				saveErrors(request, error);
				return mapping.findForward("failure");
			}
			
			Menu menu=new Menu();
			//教職員登入
			if (UserCredential.PrioEmployee.equals(user.getMember().getPriority())) {				
				menu=cm.putPriorityUniteMenu("A", menu);//加入教職員群組特定功能 TODO 是否細分為:教師或職員?
				
				List list=mm.createMenuByMember(user.getMember()).getItems();
				for(int i=0; i<list.size(); i++){
					menu.addItem(mm.createMenuByMember(user.getMember()).getItem(i));
				}
				
				menu=cm.putUniteMenu("A", menu);//加入教職員群組特定功能 TODO 是否細分為:教師或職員?
				menu=cm.putPrivateMenu(user.getMember().getAccount(), menu);//加入教職員個人特定功能	
								
			//學生登入
			} else if (UserCredential.PrioStudent.equals(user.getMember().getPriority())) {
				//String departClass = user.getStudent().getDepartClass();
				//boolean is32 = "32".equals(user.getStudentClass().getSchoolNo());// 新竹夜間部視同日間部(進專沒參加線上選課^_^")
				// 須以schoolType去判斷,是否Disabled網路選課Menu
				//String schoolType = is32 ? "" : (Toolket.isHsinChuStudent(departClass) ? "D" : cm.findSchoolTypeByClassNo(departClass));				
				//menu = mm.createMenuForStudent(schoolType);			
				
				String departClass = user.getStudent().getDepartClass();				
				String schoolType = Toolket.isHsinChuStudent(departClass) ? "D":cm.findSchoolTypeByClassNo(departClass);// 新竹夜間部視同日間部				
				menu = mm.createMenuForStudent(schoolType);// 須以schoolType去判斷,是否Disabled網路選課Menu
				menu=cm.putPrivateMenu(user.getStudent().getStudentNo(), menu);//加入個人特定功能				
				menu=cm.putUniteMenu("C", menu);//加入學生群組特定功能
				
				//menu=cm.putPrivateMenu(user.getStudent().getStudentNo(), menu);//加入學生個人特定功能(工讀生)
				//menu=cm.putUniteMenu("C", menu);//加入學生特定功能(所有)
				
			//校友登入
			}else if(UserCredential.PrioGraduate.equals(user.getMember().getPriority())){
				menu=new Menu();				
				menu=cm.putUniteMenu("L", menu);//加入校友群組特定功能(所有)
				//2009-7-30被告知某些校友仍然要使用特定功能??????
				//menu=cm.putPrivateMenu(user.getMember().getAccount(), menu);//加入學生個人特定功能(工讀生)
			}
									
			HttpSession session=request.getSession(false);			
			//if (session != null){
				//session.invalidate();
			//}			
			
			session = request.getSession(true);
			session.setAttribute("Credential", user);
			session.setAttribute("Menu", menu);			
			SaveMoreInfomation(request, session, user);//載入資訊
			
			if (menu.getItems().size() > 0) {				
				return new ActionForward(menu.getItem(0).getModule().getAction(), false);						
			} else {
				setContentPage(request.getSession(false), "BulletinBoard.jsp");						
				return mapping.findForward("Main");
			}
			
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
	private void SaveMoreInfomation(HttpServletRequest request, HttpSession session, UserCredential user)throws ParseException{
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		CourseManager manager = (CourseManager) getBean("courseManager");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		Date date=new Date();
		Date start;
		Date end;
		
		//載入快速選單
		request.setAttribute("fastmenu", manager.ezGetBy("SELECT m.Action, m.Icon, m.Label FROM Module m, User_Module_hist um WHERE " +
				"m.Oid=um.ModuleOid AND username='"+user.getMember().getAccount()+"' ORDER BY um.Oid DESC"));
		
		String type="同學";
		if(user.getMember().getPriority().equals("A")){//若是同仁
			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",user.getMember().getName()+"同仁您好, 歡迎使用!"));
			if(session.getAttribute("myOnlineWork")==null){//線上服務
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
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "<div nowrap>已得必修"+map.get("opt1")+"學分, "));
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "選修"+map.get("opt2")+"學分, "));
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "通識"+map.get("opt3")+"學分, 查看系所 " +
				"<a href='#' onClick=\"window.open('/CIS/getStdCredit.do?ClassNo="+
				user.getStudent().getDepartClass()+"', 'preview', 'height=480,width=640, toolbar=0, location=0, menubar=0', 'false');\">" +
				"學分規劃</a></div>"));
				
				//語言檢定
				try{
					if(user.getStudent().getDepartClass().substring(1,3).equals("64")&&
							user.getStudent().getDepartClass().substring(4,5).equals("4")){
						
						if(manager.ezGetString("SELECT language FROM gradresu WHERE student_no='"+user.getStudent().getStudentNo()+"'").equals("Y")){
							msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "您已通過語言檢定<br>"));
						}else{
							msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "您目前尚未通過語言檢定<br>"));
						}
					}
				}catch(Exception e){
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("您目前尚未通過語言檢定<br>"));
				}
				
				//證照
				try{
					
					List list=manager.ezGetBy("SELECT l.Name FROM StdSkill s, LicenseCode l WHERE " +
							"s.StudentNo='"+user.getStudent().getStudentNo()+"' AND l.Code=s.LicenseCode");
					if(list.size()==0){
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "您目前尚未提出證照<br>"));
					}else{
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "您目前取得證照<br>"));
					}
					for(int i=0; i<list.size(); i++){
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", ((Map)list.get(i)).get("Name").toString()+"<br>"));
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","<font size=-1><b>註<sup>1</sup></b> " +
				"學分數採及格成績即時統計,後續須經過畢業資格審查, <br><b>註<sup>2</sup></b> 學分規劃如變動仍以權責單位公佈為準</font>"));
			}
			//教學評量
			start=sf.parse(manager.ezGetString("SELECT Value FROM Parameter WHERE Name='CoanswStart'"));//教學評量開始日
			end=sf.parse(manager.ezGetString("SELECT Value FROM Parameter WHERE Name='CoanswEnd'"));//教學評量結束日
			manager.setCoansFoRm(date, session, user, start, end);
		}
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
