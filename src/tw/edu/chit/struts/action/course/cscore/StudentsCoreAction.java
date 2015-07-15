package tw.edu.chit.struts.action.course.cscore;

import java.util.ArrayList;
import java.util.Date;
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

import tw.edu.chit.model.CsCore;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;

/**
 * 系核心
 * @author JOHN
 *
 */
public class StudentsCoreAction extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//HttpSession session = request.getSession(false);
		//session.setAttribute("DeptList", Global.DeptList);		
		
		request.setAttribute("DeptList", Global.DeptList);
		
		setContentPage(request.getSession(false), "course/CsCore/DeptCore.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm cForm = (DynaActionForm) form;		
		
		String deptNo=cForm.getString("deptNo");
		
		request.setAttribute("dcores", manager.ezGetBy("SELECT * FROM CsCore WHERE deptNo='"+deptNo+"'"));	
		request.setAttribute("DeptNo", deptNo);
		request.setAttribute("DeptName", manager.ezGetString("SELECT name FROM code5 WHERE category='Dept' AND idno='"+deptNo+"'"));
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		return map;
	}	
	
}