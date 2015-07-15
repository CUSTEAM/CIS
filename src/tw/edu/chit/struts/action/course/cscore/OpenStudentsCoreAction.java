package tw.edu.chit.struts.action.course.cscore;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;

/**
 * 系核心
 * @author JOHN
 *
 */
public class OpenStudentsCoreAction extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//CourseManager manager = (CourseManager) getBean("courseManager");
		
		request.setAttribute("DeptList", Global.DeptList);
		
		//setContentPage(request.getSession(false), "course/CsCore/OpenDeptCore.jsp");
		return mapping.findForward("OpenStudentsCore");
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//System.out.println("ssssss");
		
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