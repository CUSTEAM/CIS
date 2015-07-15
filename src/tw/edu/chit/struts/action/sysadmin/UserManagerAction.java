package tw.edu.chit.struts.action.sysadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class UserManagerAction extends BaseLookupDispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		request.setAttribute("AllCampus", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Campus'"));
		request.setAttribute("AllSchool", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='School'"));
		request.setAttribute("AllDept", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Dept'"));
		request.setAttribute("AllSchoolType", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Stype'"));
		setContentPage(request.getSession(false), "portfolio/SysPortfolioManager/UserManager.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 查詢使用者
	 */
	public ActionForward established(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		DynaActionForm sForm = (DynaActionForm) form;
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		String CampusNo=sForm.getString("CampusNo"); 
		String SchoolNo=sForm.getString("SchoolNo"); 
		String DeptNo=sForm.getString("DeptNo"); 
		String Grade=sForm.getString("Grade"); 
		String ClassNo=sForm.getString("ClassNo"); 		
		
		String portfolioServer=manager.ezGetString("SELECT Value FROM Parameter WHERE name='portfolioServer'");		
		List students=manager.ezGetBy("SELECT s.student_no, s.student_name, c.ClassNo, c.ClassName FROM stmd s, " +
		"Class c WHERE s.depart_class=c.ClassNo AND s.student_no IN(SELECT Uid FROM Eps_user) " +
		"AND s.depart_class LIKE'"+CampusNo+SchoolNo+DeptNo+Grade+ClassNo+"%' " +
		"ORDER BY c.DeptNo, c.Grade, s.student_no");
		
		session.setAttribute("students", students);
		request.setAttribute("portfolioServer", portfolioServer);		
		return unspecified(mapping, form, request, response);
	}	
	
	/**
	 * 查詢未使用者
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unestablish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		DynaActionForm sForm = (DynaActionForm) form;
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		String CampusNo=sForm.getString("CampusNo"); 
		String SchoolNo=sForm.getString("SchoolNo"); 
		String DeptNo=sForm.getString("DeptNo"); 
		String Grade=sForm.getString("Grade"); 
		String ClassNo=sForm.getString("ClassNo"); 
	
		String portfolioServer=manager.ezGetString("SELECT Value FROM Parameter WHERE name='portfolioServer'");
		List students=manager.ezGetBy("SELECT s.student_no, s.student_name, c.ClassNo, c.ClassName FROM stmd s, " +
				"Class c WHERE s.depart_class=c.ClassNo AND s.student_no NOT IN(SELECT Uid FROM Eps_user) " +
				"AND s.depart_class LIKE'"+CampusNo+SchoolNo+DeptNo+Grade+ClassNo+"%' " +
				"ORDER BY c.DeptNo, c.Grade, s.student_no");
		
		session.setAttribute("students", students);
		request.setAttribute("portfolioServer", portfolioServer);
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Established", "established");
		map.put("Unestablish", "unestablish");
		return map;
	}

}
