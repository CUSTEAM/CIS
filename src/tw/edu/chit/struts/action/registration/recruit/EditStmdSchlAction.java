package tw.edu.chit.struts.action.registration.recruit;

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

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class EditStmdSchlAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		//DynaActionForm cForm = (DynaActionForm) form;
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","待處理學生仍有"+
		manager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE schl_code IS NULL")+"人, 包含部份無法批次處理(顯示)的學生"));
		saveMessages(request, msg);
		
		
		request.setAttribute("schools", manager.ezGetBy("SELECT schl_name, COUNT(*) cnt FROM stmd WHERE schl_code IS NULL GROUP BY schl_name ORDER BY cnt desc LIMIT 10"));
		
		setContentPage(request.getSession(false), "registration/recruit/EditStmdSchl.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm sForm = (DynaActionForm) form;
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String schl_name[]=sForm.getStrings("schl_name");
		String schl_name_new[]=sForm.getStrings("schl_name_new");
		String schl_code_new[]=sForm.getStrings("schl_code_new");
		
		
		for(int i=0; i<schl_name.length; i++){		
			
			if(schl_code_new[i].length()>3){
				manager.executeSql("UPDATE stmd SET schl_code='"+schl_code_new[i]+"', schl_name='"+schl_name_new[i]+"' WHERE schl_name='"+schl_name[i]+"'");
				//manager.executeSql("UPDATE stmd SET schl_name=null, schl_code=null WHERE schl_code='"+schl_code_new[i]+"'");
			}			
		}		
		//setContentPage(request.getSession(false), "registration/recruit/EditStmdSchl.jsp");
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Save", "save");
		return map;
	}

}
