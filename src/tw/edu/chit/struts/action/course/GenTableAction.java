package tw.edu.chit.struts.action.course;

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

import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 快速加退選
 * @author JOHN
 *
 */
public class GenTableAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		
		//session.setAttribute("allGroup", manager.ezGetBy("SELECT * FROM CsGroup"));		
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","學程管理"));
		//saveMessages(request, msg);

		setContentPage(request.getSession(false), "course/GenTable.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm af = (DynaActionForm) form;
		
		String student_no=af.getString("student_no");
		String Dtime_oid=af.getString("Dtime_oid");
		
		
		
		
		
		return mapping.findForward("Main");
	}
	
	
	
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		return map;
	}

}
