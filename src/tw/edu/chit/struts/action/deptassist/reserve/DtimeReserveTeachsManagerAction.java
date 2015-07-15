package tw.edu.chit.struts.action.deptassist.reserve;

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

import tw.edu.chit.model.DtimeReserve;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 時數管理
 * @author shawn
 *
 */
public class DtimeReserveTeachsManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {setContentPage(request.getSession(false), "assistant/DtimeReserve.jsp");		
		return mapping.findForward("Main");
	}	
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String year=dForm.getString("year");
		String depart_class=dForm.getString("classLess");
		String optType=dForm.getString("optType");
		
		
		
		return query(mapping, form, request, response);
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String Oid[]=dForm.getStrings("Oid");
		String cscode[]=dForm.getStrings("cscode");
		String techid[]=dForm.getStrings("techid");
		
		return query(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();		
		map.put("Save", "save");				
		return map;
	}
}
