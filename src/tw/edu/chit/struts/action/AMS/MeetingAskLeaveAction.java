package tw.edu.chit.struts.action.AMS;

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


import tw.edu.chit.model.AmsAskLeave;
import tw.edu.chit.model.Rcact;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class MeetingAskLeaveAction extends BaseLookupDispatchAction{

	/**
	 * 初始資料,帶出假別資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "MeetingAskLeave");		
		
		String userIdno = user.getMember().getAccount();
		
		List MeetingAskLeave = 
			manager.ezGetBy("Select MAL.`Oid`, MAL.askCode, M.`Name` MN, A.`Name` AN, MAL.`Status` " +
					        "From `AMS_MeetingAskLeave` MAL, `AMS_Meeting` M, `AMS_AskLeave` A " +
					        "Where MAL.`meetingOid` = M.`Oid` " +
					        "  And MAL.`askleaveId` = A.`ID` " +
					        "  And userIdno = '"+userIdno+"'");    //取得所有假別資料
		
		session.setAttribute("MAL_List", MeetingAskLeave);		
		
		setContentPage(request.getSession(false), "AMS/MeetingAskLeave.jsp");
		//return unspecified(mapping, form, request, response);
		return mapping.findForward("Main");
	}
	
	/**
	 * 啟動新增
	 */
	@SuppressWarnings("unchecked")
	public ActionForward Create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		// UserCredential user = (UserCredential) session.getAttribute("Credential");
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		Toolket.resetCheckboxCookie(response, "MeetingAskLeave");
		
		String schoolYear = manager.getNowBy("School_year");                       // 取得學年度             
		String schoolTerm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);       // 取得學期
		
		// 因為98.2有人要請99.1自強月會,所以必須將下學期重要集會納入(Oscar)
		Map<String, Integer> nextYearTerm = Toolket.getNextYearTerm();
		Integer nextYear = nextYearTerm.get(IConstants.PARAMETER_SCHOOL_YEAR);
		Integer nextTerm = nextYearTerm.get(IConstants.PARAMETER_SCHOOL_TERM);
		
		List meetingOids = manager.ezGetBy("Select Oid, Name From AMS_Meeting " +
                "Where SchoolYear = '"+schoolYear+"' And SchoolTerm = '"+schoolTerm+"'");
		meetingOids.addAll(manager.ezGetBy("Select Oid, Name From AMS_Meeting " +
                "Where SchoolYear = '"+nextYear+"' And SchoolTerm = '"+nextTerm+"'"));
				
		session.setAttribute("myOpen", "open");		
		session.setAttribute("meetingOid", meetingOids);
		session.setAttribute("askleaveId", manager.ezGetBy("Select ID, Name From AMS_AskLeave Where ID Not In('07','09','12')"));
		session.setAttribute("Temp", "");
		
		setContentPage(request.getSession(false), "AMS/MeetingAskLeave.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 啟動修改
	 */
	public ActionForward Modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		Toolket.resetCheckboxCookie(response, "MeetingAskLeave");
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		String userIdno = user.getMember().getAccount();
		String schoolYear = manager.getNowBy("School_year");                       // 取得學年度             
		String schoolTerm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);       // 取得學期
				
		String ids = Toolket.getSelectedIndexFromCookie(request, "MeetingAskLeave");
		String id_s = ids.substring(1, ids.length()-1);	
		
		String myStatus = manager.ezGetString("Select Status From AMS_MeetingAskLeave Where Oid='"+id_s+"'");		
		if(myStatus.equals("1")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "此單據已經過人事室審核處理,無法進行修改!!"));
			saveErrors(request, error);
			return mapping.findForward("Main");        	
		}
		
		session.setAttribute("myOpen", "open");
		session.setAttribute("MAL_Oid", id_s);		
		session.setAttribute("meetingOid", manager.ezGetBy("Select Oid, Name From AMS_Meeting " +
                                                           "Where SchoolYear = '"+schoolYear+"' And SchoolTerm = '"+schoolTerm+"'"));
        session.setAttribute("askleaveId", manager.ezGetBy("Select ID, Name From AMS_AskLeave Where ID Not In('07','09','12')"));
		session.setAttribute("sel_meetingOid", manager.ezGetString("Select meetingOid From AMS_MeetingAskLeave Where Oid='"+id_s+"'"));
		session.setAttribute("sel_askleaveId", manager.ezGetString("Select askleaveId From AMS_MeetingAskLeave Where Oid='"+id_s+"'"));
		session.setAttribute("Temp", manager.ezGetString("Select Temp From AMS_MeetingAskLeave Where Oid='"+id_s+"'"));
		
		
		setContentPage(request.getSession(false), "AMS/MeetingAskLeave.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 儲存資料
	 */
	public ActionForward Save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息

		//Date myDate = new Date();
		int number = 1;
		
		String userIdno = user.getMember().getAccount();
		String schoolYear = manager.getNowBy("School_year");                       // 取得學年度             
		String schoolTerm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);       // 取得學期
		
		String Oid = aForm.getString("Oid");  
		String meetingOid = aForm.getString("meetingOid");
		String askleaveId = aForm.getString("askleaveId");
		String Temp = aForm.getString("Temp");		
		String askCode = "9" + userIdno.substring(6) + meetingOid + schoolYear +"001";		
				
		String MD_status = manager.ezGetString("Select Status From AMS_MeetingData Where Idno='"+userIdno+"' And MeetingOid='"+meetingOid+"'");
		System.out.println("Select Status From AMS_MeetingData Where Idno='"+userIdno+"' And MeetingOid='"+meetingOid+"'");
        if(MD_status != null && !MD_status.equals("99") && !MD_status.equals("A")){
        	if(MD_status.equals("")){        		
        		error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "您未列於該會議名單,請洽詢人事室!!"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}else {				
				//System.out.println("MD_status="+MD_status);
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "該會議您已有請假紀錄"));
				saveErrors(request, error);
				return mapping.findForward("Main");
            }
        	
		}
			
		
		
		List mySql = manager.ezGetBy("Select askCode From AMS_MeetingAskLeave " +
                "Where askCode Like '9"+ userIdno.substring(6) + meetingOid + schoolYear +"%' " +
                
                "Order By askCode DESC");
		
		if (!mySql.isEmpty()) {
			String Max_askCode = ((Map) mySql.get(0)).get("askCode").toString();
			number = Integer.parseInt(Max_askCode.substring(9)) + 1;
			
			String newNo = ""+number;
			String myCode = newNo;
			for(int i=0; i<3-newNo.length(); i++){
				myCode = "0"+myCode;
			}			
			askCode = "9" + userIdno.substring(6) + meetingOid + schoolYear + myCode;
		}		
		
		String myOID = manager.ezGetString("Select Oid From AMS_MeetingAskLeave Where Oid='"+ Oid +"'");		//檢查資料表中是否有重複的ID
		
		if(myOID==""){  //如果沒有重複,則執行新增資料			
			manager.executeSql("Insert Into AMS_MeetingAskLeave(meetingOid, userIdno, askleaveId, Temp, Status, askCode) " +
	                   "Values('"+meetingOid+"','"+userIdno+"','"+askleaveId+"','"+Temp+"','0','"+askCode+"')");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "新增完成"));			
		}else{         //如果重複,則執行更新			
			manager.executeSql("Update AMS_MeetingAskLeave " +
			                   "Set meetingOid='"+meetingOid+"', askleaveId='"+askleaveId+"', Temp='"+Temp+"', askCode='"+askCode+"' " +
	                           "Where Oid='"+Oid+"'");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "修改完成"));			
		}		
		
		session.setAttribute("myOpen", "close");
		saveMessages(request, messages);
		setContentPage(request.getSession(false), "AMS/MeetingAskLeave.jsp");	
		return unspecified(mapping, form, request, response);
		//return mapping.findForward("Main");	
	}
	
	/**
	 * 刪除資料
	 */
	public ActionForward Delete(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	       throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;		
		ActionMessages messages = new ActionMessages();
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			
		String ids = Toolket.getSelectedIndexFromCookie(request, "MeetingAskLeave");
		String id_s = ids.substring(1, ids.length()-1);		
		
		String myStatus = manager.ezGetString("Select Status From AMS_MeetingAskLeave Where Oid='"+id_s+"'");		
		if(myStatus.equals("1")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "此單據已經過人事室審核處理,無法進行刪除!!"));
			saveErrors(request, error);
			return mapping.findForward("Main");        	
		}
		
		manager.executeSql("DELETE FROM AMS_MeetingAskLeave Where Oid='"+id_s+"'");		
		
		setContentPage(request.getSession(false), "AMS/MeetingAskLeave.jsp");
		return unspecified(mapping, form, request, response);
		//return mapping.findForward("Main");	
		
	}

	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub		
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create", "Create");		
		map.put("Modify", "Modify");
		map.put("Delete", "Delete");
		map.put("Save", "Save");
		return map;
	}

}
