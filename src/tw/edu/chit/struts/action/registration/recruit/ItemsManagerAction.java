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

public class ItemsManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		request.setAttribute("items", manager.ezGetBy("SELECT * FROM Recruit_item ORDER BY id"));
		
		
		setContentPage(request.getSession(false), "registration/recruit/ItemsManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm iForm = (DynaActionForm) form;
		
		String id[]=iForm.getStrings("id");
		String name[]=iForm.getStrings("name");
		
		
		if(id[0].trim().equals("") || name[0].trim().equals("")){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立失敗"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}
		
		try{
			manager.executeSql("INSERT INTO Recruit_item(id, name)VALUES('"+id[0]+"', '"+name[0]+"')");
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立完成"));
			saveMessages(request, msg);
		}catch(Exception e){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立失敗"));
			saveErrors(request, error);
		}		
		return unspecified(mapping, form, request, response);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
	
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm iForm = (DynaActionForm) form;
		String id[]=iForm.getStrings("id");
		String name[]=iForm.getStrings("name");
		
		for(int i=1; i<id.length; i++){			
			if(!name[i].trim().equals("")){				
				try{
					manager.executeSql("UPDATE Recruit_item SET name='"+name[i]+"' WHERE id='"+id[i]+"'");
				}catch(Exception e){
					
				}				
			}	
		}
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Save", "save");
		map.put("Create", "create");		
		return map;
	}

}
