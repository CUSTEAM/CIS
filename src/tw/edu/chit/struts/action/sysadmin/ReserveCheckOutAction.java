package tw.edu.chit.struts.action.sysadmin;

import java.util.HashMap;
import java.util.List;
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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.DtimeReserve;
import tw.edu.chit.model.DtimeReserveOpencs;
import tw.edu.chit.model.DtimeReserveReady;
import tw.edu.chit.model.DtimeReserveTeacher;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 課程轉歷年
 * @author shawn
 */
public class ReserveCheckOutAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		setContentPage(request.getSession(false), "sysadmin/ReserveCheckOut.jsp");		
		
		
		
		
		
		return mapping.findForward("Main");
	}
	
	public ActionForward ok(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		
		
		return mapping.findForward("Main");
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("OK", "ok");	
		return map;
	}
}
