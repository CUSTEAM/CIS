package tw.edu.chit.struts.action.registration.recruit;

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

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class RecruitQueryAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
			
		request.setAttribute("schools", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='School' ORDER BY sequence"));
		request.setAttribute("depts", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Dept' ORDER BY sequence"));
		setContentPage(request.getSession(false), "registration/recruit/RecruitQuery.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;		
		String school_year=sForm.getString("school_year");
		String DeptNo=sForm.getString("DeptNo");
		String SchoolNo=sForm.getString("SchoolNo");
		String school_code=sForm.getString("school_code");
		HttpSession session = request.getSession(false);
		
		
		List stds=manager.ezGetBy("SELECT s.grad_dept, s.entrance, c.*, s.student_no, s.student_name, s.sex, rs.name as SchoolName, s.ident, c5.name as IdName " +
				"FROM ((stmd s LEFT OUTER JOIN code5 c5 ON s.ident=c5.idno AND c5.category='Identity')LEFT OUTER JOIN Recruit_school rs " +
				"ON rs.no=s.schl_code)LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo WHERE c.SchoolNo LIKE'"+SchoolNo+"%' AND " +
				"c.DeptNo LIKE '"+DeptNo+"%' AND s.entrance LIKE '"+school_year+"%' AND s.schl_code LIKE'"+school_code+"%' ORDER BY s.depart_class");
		
		
		session.setAttribute("stds", stds);		
		setContentPage(request.getSession(false), "registration/recruit/RecruitQuery.jsp");
		
		
		
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "共 "+stds.size()+"名可辨識的學生"));
		saveMessages(request, msg);
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		return map;
	}

}
