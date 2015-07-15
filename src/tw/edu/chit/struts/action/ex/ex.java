package tw.edu.chit.struts.action.ex;

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

import tw.edu.chit.model.ClassEx;
import tw.edu.chit.model.Empl;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.impl.DefaultManagerImpl;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class ex extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		
		HttpSession session = request.getSession(false);			
		setContentPage(request.getSession(false), "ex/FormExample.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm eForm = (DynaActionForm) form;
		
		
		String column1=eForm.getString("cscode");
		String column2=eForm.getString("chi_name");
		
		DefaultManagerImpl manager = (DefaultManagerImpl) getBean("defaultManager");
		List list=manager.sqlGet("SELECT cscode, chi_name FROM Csno_Ex LIMIT 100");
		
		
		
		request.setAttribute("result", 
				manager.sqlGet("SELECT cscode, chi_name FROM Csno_Ex LIMIT 100"));
		
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		
		
		return query(mapping, form, request,  response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Save", "save");
		return map;
	}
}
