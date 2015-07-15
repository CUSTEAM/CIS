package tw.edu.chit.struts.action.AMS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;


import tw.edu.chit.model.AmsAskLeave;
import tw.edu.chit.model.Rcact;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class AskLeaveAction extends BaseLookupDispatchAction{

	/**
	 * 初始資料,帶出假別資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "AskLeave");		
		
		List<AmsAskLeave> AskLeave = manager.ezGetBy("Select ID, Name, Score From AMS_AskLeave");    //取得所有假別資料
		
		session.setAttribute("AL_List", AskLeave);		
		
		setContentPage(request.getSession(false), "AMS/AskLeave.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 啟動新增
	 */
	public ActionForward Create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
				
		session.setAttribute("myOpen", "open");		
		session.setAttribute("AL_id", "");
		session.setAttribute("AL_name", "");
		session.setAttribute("score", "");
		
		setContentPage(request.getSession(false), "AMS/AskLeave.jsp");
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
		//Toolket.resetCheckboxCookie(response, "rcact");		
		
		String teacherId = user.getMember().getAccount();		
		String ids = Toolket.getSelectedIndexFromCookie(request, "AskLeave");
		String id_s = ids.substring(1, ids.length()-1);		
		
		session.setAttribute("myOpen", "open");
		session.setAttribute("AL_id", id_s);
		session.setAttribute("AL_name", manager.ezGetString("Select Name From AMS_AskLeave Where ID='"+id_s+"'"));
		session.setAttribute("score", manager.ezGetString("Select Score From AMS_AskLeave Where ID='"+id_s+"'"));
		
		setContentPage(request.getSession(false), "AMS/AskLeave.jsp");
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
		
		String idno = user.getMember().getAccount();
		session.setAttribute("myOpen", "close");
		String id = aForm.getString("AL_id");
		String name = aForm.getString("AL_name");
		String score = aForm.getString("score");		
		String myID = manager.ezGetString("Select ID From AMS_AskLeave Where ID='"+id+"'");		//檢查資料表中是否有重複的ID
		
		if(myID==""){  //如果沒有重複,則執行新增資料
			
			manager.executeSql("Insert Into AMS_AskLeave(ID,Name,Score) " +
	                   "Values('" +id+"','"+name+"','"+score+"')");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "新增完成"));
			
		}else{         //如果重複,則執行更新
			
			manager.executeSql("Update AMS_AskLeave " +
			           "Set Name='"+name+"', Score='"+score+"' " +
	                   "Where ID='"+myID+"'");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "修改完成"));
			
		}		
		
		saveMessages(request, messages);
		setContentPage(request.getSession(false), "AMS/AskLeave.jsp");	
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
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();			
		
		String teacherId = user.getMember().getAccount();		
		String ids = Toolket.getSelectedIndexFromCookie(request, "AskLeave");
		String id_s = ids.substring(1, ids.length()-1);		
		
		manager.executeSql("DELETE FROM AMS_AskLeave Where ID='"+id_s+"'");		
		
		setContentPage(request.getSession(false), "AMS/AskLeave.jsp");
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
