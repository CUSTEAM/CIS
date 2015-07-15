package tw.edu.chit.struts.action.course;

import java.util.ArrayList;
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

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class CourseHistAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		request.setAttribute("allyear", manager.ezGetBy("SELECT school_year FROM Savedtime GROUP BY school_year"));
		
		
		setContentPage(request.getSession(false), "course/CourseHist.jsp");		
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm f = (DynaActionForm) form;
		
		
		
		request.setAttribute("sdtim", manager.ezGetBy("SELECT LENGTH(Introduction) as intro, LENGTH(Syllabi) as syll, LENGTH(Syllabi_sub) as syll_sub,d.thour, d.credit, d.opt, d.cscode, d.Oid, e.cname, " +
		"c.ClassName, cs.chi_name, d.samples, d.avg FROM Savedtime d LEFT OUTER JOIN empl e ON " +
		"d.techid=e.idno, Class c, Csno cs WHERE cs.cscode=d.cscode AND d.depart_class=c.ClassNo AND " +
		"d.techid LIKE '"+f.getString("techid")+"%' AND d.depart_class LIKE '"+f.getString("depart_class")+"%' AND " +
		"d.cscode LIKE '"+f.getString("cscode")+"%' AND d.school_year='"+f.getString("school_year")+"' " +
		"AND d.school_term='"+f.getString("school_term")+"'"));	
		return unspecified(mapping, form, request, response);
	}
	
	
	
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		DynaActionForm cForm = (DynaActionForm) form;
		cForm.set("ClassNo", "");
		cForm.set("ClassName", "");
		cForm.set("CampusNo", "");
		cForm.set("SchoolNo", "");
		cForm.set("DeptNo", "");
		cForm.set("Grade", "");
		cForm.set("Type", "");
		cForm.set("ShortName", "");
		cForm.set("SchoolType", "");		
		
		session.removeAttribute("classes");
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Clear", "clear");
		return map;
	}

}
