package tw.edu.chit.struts.action.registration.recruit;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class MailListManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		if(request.getParameter("delOid")!=null){
			CourseManager manager = (CourseManager) getBean("courseManager");
			manager.executeSql("DELETE FROM MailGroup WHERE Oid="+request.getParameter("delOid"));
			return query(mapping, form, request, response);
		}
		setContentPage(request.getSession(false), "registration/recruit/MailListManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		//DynaActionForm cForm = (DynaActionForm) form;
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","待處理學生仍有"+
		//manager.ezGetInt("SELECT COUNT(*)FROM Recruit_stmd_tmp WHERE schl_name IS NOT NULL")+"人, 包含部份無法批次處理(顯示)的學生"));
		//saveMessages(request, msg);
		DynaActionForm sForm = (DynaActionForm) form;
		String name=sForm.getString("name");
		request.setAttribute("mails", manager.ezGetBy("SELECT * FROM MailGroup WHERE name LIKE'"+name+"%'"));		
		//setContentPage(request.getSession(false), "registration/recruit/MailListManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		//DynaActionForm cForm = (DynaActionForm) form;
		//HttpSession session = request.getSession(false);
		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;
		String name=sForm.getString("name");
		String mailaddress=sForm.getString("mailaddress");
		
		if(!name.trim().equals("") && !mailaddress.trim().equals("")){
			
			try{
				manager.executeSql("INSERT INTO MailGroup(ModuleOid, name, mailaddress)VALUES('6314', '"+name+"', '"+mailaddress+"')");
			}catch(Exception e){
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立失敗"));
				saveErrors(request, error);
			}
			
		}else{
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立失敗"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立完成"));
		saveMessages(request, msg);
		
		
		return query(mapping, form, request, response);
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		//DynaActionForm cForm = (DynaActionForm) form;
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","待處理學生仍有"+
		//manager.ezGetInt("SELECT COUNT(*)FROM Recruit_stmd_tmp WHERE schl_name IS NOT NULL")+"人, 包含部份無法批次處理(顯示)的學生"));
		//saveMessages(request, msg);		
		request.setAttribute("mails", manager.ezGetBy("SELECT m.*, e.cname FROM MailGroup m, empl e WHERE m.idno=e.idno"));		
		//setContentPage(request.getSession(false), "registration/recruit/MailListManager.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Create", "create");
		map.put("Save", "save");
		return map;
	}

}
