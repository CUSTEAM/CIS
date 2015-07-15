package tw.edu.chit.struts.action.student;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 有包含申論題的教學評量
 * 已於96學年廢止
 * @author JOHN
 *
 */
public class Coansw extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		DynaActionForm setUpDtime = (DynaActionForm) form;
		
		*/		
		setContentPage(request.getSession(false), "student/Coansw.jsp");
		return mapping.findForward("Main");		
	}
	
	
	public ActionForward ok(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		DynaActionForm setUpDtime = (DynaActionForm) form;
		*/
		
		return unspecified(mapping, form, request, response);
	}


	@Override
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "ok");
		return map;
	}
	
	
	
}
