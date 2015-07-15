package tw.edu.chit.struts.action.portfolio.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class ListMyTeachersAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		//有選取課程
		if(request.getParameter("cscode")==null){
			//取課程資訊
			UserCredential c = (UserCredential) session.getAttribute("Credential");
			String Uid=c.getMember().getAccount();//取得使用者帳號
			String school_year;
			String student_no;
			Map teacher;
			
			List alldtime=manager.ezGetBy("SELECT school_year FROM ScoreHist WHERE student_no='"+Uid+"' GROUP BY school_year ORDER BY school_year DESC");
			List dtimes=new ArrayList();
			Map map;
			List teachers;
			for(int i=0; i<alldtime.size(); i++){
				school_year=((Map)alldtime.get(i)).get("school_year").toString();
				//student_no=((Map)alldtime.get(i)).get("student_no").toString();
				map=new HashMap();
				
				dtimes= manager.ezGetBy("SELECT s.*, c.chi_name FROM ScoreHist s, Csno c WHERE c.cscode=s.cscode AND " +
						"s.school_year='"+school_year+"' AND s.student_no='"+Uid+"' ORDER BY school_term DESC");			
				
				
				for(int j=0; j<dtimes.size(); j++){
					//teacher=new HashMap();
					teacher=manager.ezGetMap("SELECT e.cname, e.idno, e.Email FROM Savedtime d LEFT OUTER JOIN empl e ON d.techid=e.idno WHERE d.school_year='"+school_year+"' " +
					"AND d.school_term='"+((Map)dtimes.get(j)).get("school_term")+"' AND cscode='"+((Map)dtimes.get(j)).get("cscode")+"' AND " +
							"depart_class='"+((Map)dtimes.get(j)).get("stdepart_class")+"' LIMIT 1" );
					
					try{
						teacher.put("path", manager.ezGetString("SELECT path FROM Eps_user WHERE Uid='"+teacher.get("idno")+"'"));
					}catch(Exception e){
						
					}
					
					
					request.setAttribute("server", manager.ezGetString("SELECT Value FROM Parameter WHERE name='portfolioServer'"));
					((Map)dtimes.get(j)).put("teacher", teacher);
					
				}
				
				
				((Map)alldtime.get(i)).put("courses",dtimes);
			}
			request.setAttribute("alldtime", alldtime);
			
		}else{
			//取班級學生資訊
			String cscode=request.getParameter("cscode");
			String depart_class=request.getParameter("depart_class");
			String year=request.getParameter("year");
			String term=request.getParameter("term");
			
			
			
			
			request.setAttribute("server", manager.ezGetString("SELECT Value FROM Parameter WHERE name='portfolioServer'"));
			//request.setAttribute("students", students);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		setContentPage(request.getSession(false), "portfolio/student/ListMyTeachers.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();
		
		
		DynaActionForm aForm = (DynaActionForm) form;
		
		
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		return map;
	}

}
