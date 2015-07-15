package tw.edu.chit.struts.action.calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 班別管理
 * @author JOHN
 *
 */
public class TxtGroupPubManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		//UserCredential cr=(UserCredential) session.getAttribute("Credential");
		//String account=cr.getMember().getAccount();
		CourseManager manager = (CourseManager) getBean("courseManager");
		List list=manager.ezGetBy("SELECT * FROM TxtGroup WHERE account='All'");		
		request.setAttribute("myGroup", list);
		setContentPage(session, "calendar/TxtGroupPubManager.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		//HttpSession session = request.getSession(false);
		//UserCredential cr=(UserCredential) session.getAttribute("Credential");
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm f=(DynaActionForm) form;		
		String name[]=f.getStrings("name");
		String members[]=f.getStrings("members");		
		if(!name[0].equals("") && !members[0].equals("")){			
			manager.executeSql("INSERT INTO TxtGroup(account, name, members)VALUES('All', '"+name[0]+"', '"+members[0]+"');");			
		}		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {		
		//HttpSession session = request.getSession(false);		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm f=(DynaActionForm) form;	
		String delOid[]=f.getStrings("delOid");
		for(int i=1; i<delOid.length; i++){
			if(!delOid[i].equals("")){
				manager.executeSql("DELETE FROM TxtGroup WHERE Oid='"+delOid[i]+"'");
			}
		}
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {		
		HttpSession session = request.getSession(false);		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm f=(DynaActionForm) form;	
		String delOid[]=f.getStrings("delOid");
		String name[]=f.getStrings("name");
		String members[]=f.getStrings("members");
		for(int i=1; i<delOid.length; i++){
			if(!delOid[i].equals("")){
				manager.executeSql("UPDATE TxtGroup SET name='"+name[i]+"', members='"+members[i]+"' WHERE Oid='"+delOid[i]+"'");
			}
		}
		return unspecified(mapping, form, request, response);

	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Delete", "delete");
		map.put("Modify", "modify");
		return map;
	}

}
