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

public class SchoolManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		//request.setAttribute("schools", manager.ezGetBy("SELECT * FROM Recruit_school ORDER BY classNo, localNo, typeNo"));
		setContentPage(request.getSession(false), "registration/recruit/SchoolManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		request.setAttribute("schools", manager.ezGetBy("SELECT * FROM Recruit_school ORDER BY classNo, localNo, typeNo"));		
		setContentPage(request.getSession(false), "registration/recruit/SchoolManager.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
	
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm sForm = (DynaActionForm) form;		
		String no=sForm.getString("no");
		String name=sForm.getString("name");
		
		//System.out.println("SELECT * FROM Recruit_school WHERE no LIKE'"+no+"%' AND name LIKE'"+name+"%' ORDER BY typeNo, localNo");
		request.setAttribute("school", manager.ezGetBy("SELECT * FROM Recruit_school WHERE no LIKE'"+no+"%' AND name LIKE'%"+name+"%' ORDER BY typeNo, localNo"));
		
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;	
		
		String zip[]=sForm.getStrings("zip");
		String address[]=sForm.getStrings("address");
		String connector[]=sForm.getStrings("connector");
		String url[]=sForm.getStrings("url");
		String tel[]=sForm.getStrings("tel");
		String Oid[]=sForm.getStrings("Oid");		
		for(int i=0; i<Oid.length; i++){			
			if(!Oid.equals("")){				
				try{
					manager.executeSql("UPDATE Recruit_school set zip='"+zip[i]+"', address='"+address[i]+"', " +
							"connector='"+connector[i]+"', url='"+url[i]+"', tel='"+tel[i]+"' WHERE Oid='"+Oid[i]+"'");
				}catch(Exception e){
					e.printStackTrace();
				}				
			}
		}		
		//setContentPage(request.getSession(false), "registration/recruit/SchoolManager.jsp");
		return query(mapping, form, request, response);
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
