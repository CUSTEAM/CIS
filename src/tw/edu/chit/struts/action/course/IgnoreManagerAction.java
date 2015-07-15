package tw.edu.chit.struts.action.course;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class IgnoreManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				
		
		
		
		
		
		setContentPage(request.getSession(false), "course/IgnoreManager.jsp");
		return mapping.findForward("Main");
	}
	
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				
		
		
		
		
		
		
		return mapping.findForward("Main");
	}
	
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				
		
		
		
		
		
		
		return mapping.findForward("Main");
	}
	

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Modify", "modify");		
		return map;
	}

}
