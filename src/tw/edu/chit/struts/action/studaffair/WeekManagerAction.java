package tw.edu.chit.struts.action.studaffair;

import java.util.HashMap;
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

import tw.edu.chit.model.Member;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class WeekManagerAction  extends BaseLookupDispatchAction {
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK","ok");
		return map;		
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager)getBean("courseManager");		
		request.setAttribute("weeks", manager.ezGetBy("SELECT w.*, e.cname FROM week w LEFT OUTER JOIN empl e ON w.lastedit=e.idno"));		
		HttpSession session = request.getSession(false);
		setContentPage(session, "studaffair/WeekManager.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward ok(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm dForm = (DynaActionForm)form;		
		CourseManager manager = (CourseManager)getBean("courseManager");
		
		String wdate[]=dForm.getStrings("wdate");
		String daynite[]=dForm.getStrings("daynite");
		String check[]=dForm.getStrings("check");
		
		Member me = getUserCredential(request.getSession(false)).getMember();
		
		for(int i=0; i<wdate.length; i++){
			try{
				if(!check[i].equals("")){
					manager.executeSql("UPDATE week SET wdate='"+wdate[i]+"', " +
					"lastedit='"+me.getIdno()+"' WHERE daynite='"+daynite[i]+"'");
				}
			}catch(Exception e){
				ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請檢查日期"));
				saveErrors(request, error);
				return unspecified(mapping, form,request, response);
			}
		}
		
		ActionMessages msg=new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已儲存"));
		saveMessages(request, msg);
		return unspecified(mapping, form,request, response);
	}

}
