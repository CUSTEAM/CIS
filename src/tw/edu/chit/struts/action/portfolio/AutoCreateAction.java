package tw.edu.chit.struts.action.portfolio;

import java.text.SimpleDateFormat;
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

import tw.edu.chit.model.EpsActParameter;
import tw.edu.chit.model.EpsUser;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class AutoCreateAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		CourseManager manager = (CourseManager) getBean("courseManager");	
		
		request.setAttribute("AllCampus", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Campus'"));
		request.setAttribute("AllSchool", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='School'"));
		request.setAttribute("AllDept", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Dept'"));
		request.setAttribute("AllSchoolType", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Stype'"));
		
		setContentPage(request.getSession(false), "portfolio/AutoCreate.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm sForm = (DynaActionForm) form;
		
		//HttpSession session = request.getSession(false);
		
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
		
		request.setAttribute("students", students);
		request.setAttribute("portfolioServer", portfolioServer);
			
		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {	
		DynaActionForm sForm = (DynaActionForm) form;
		String CampusNo=sForm.getString("CampusNo"); 
		String SchoolNo=sForm.getString("SchoolNo"); 
		String DeptNo=sForm.getString("DeptNo"); 
		String Grade=sForm.getString("Grade"); 
		String ClassNo=sForm.getString("ClassNo");
		CourseManager manager = (CourseManager) getBean("courseManager");
		//EpsUser user;
				List list=manager.ezGetBy("SELECT s.student_no, s.student_name FROM stmd s, " +
						"Class c WHERE s.depart_class=c.ClassNo AND s.student_no NOT IN(SELECT Uid FROM Eps_user) " +
						"AND s.depart_class LIKE'"+CampusNo+SchoolNo+DeptNo+Grade+ClassNo+"%' " +
						"ORDER BY c.DeptNo, c.Grade, s.student_no");
				
				for(int i=0; i<list.size(); i++){			
					
					manager.checkNewPortfolio(((Map)list.get(i)).get("student_no").toString(), 
					((Map)list.get(i)).get("student_name").toString()+"的學習歷程檔案", 
					"", ((Map)list.get(i)).get("student_no").toString());
					
				}
		
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立完成"));
		saveMessages(request, msg);	//完成
		return query(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Query", "query");
		return map;
	}

}
