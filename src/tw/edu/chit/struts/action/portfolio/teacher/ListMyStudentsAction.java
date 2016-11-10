package tw.edu.chit.struts.action.portfolio.teacher;

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

public class ListMyStudentsAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		//有選取課程
		if(request.getParameter("cscode")==null && request.getParameter("depart_class")==null && request.getParameter("type")==null){
			//取課程資訊
			UserCredential c = (UserCredential) session.getAttribute("Credential");
			String Uid=c.getMember().getAccount();//取得使用者帳號
			String school_year;
			
			List alldtime=manager.ezGetBy("SELECT school_year FROM Savedtime WHERE techid='"+Uid+"' GROUP BY school_year ORDER BY school_year DESC");
			List dtimes=new ArrayList();
			Map map;
			//List students;
			
			for(int i=0; i<alldtime.size(); i++){
				school_year=((Map)alldtime.get(i)).get("school_year").toString();
				map=new HashMap();
				
				dtimes= manager.ezGetBy(
				"SELECT s.school_term, c.ClassName, cs.chi_name, s.depart_class, s.cscode, s.credit, s.thour FROM " +
				"Savedtime s, Csno cs, Class c WHERE s.depart_class=c.ClassNo AND s.cscode=cs.cscode AND " +
				"s.school_year='"+school_year+"' AND s.techid='"+Uid+"' ORDER BY school_term DESC");
				
				((Map)alldtime.get(i)).put("courses",dtimes);
			}
			
			List thisdtime=manager.ezGetBy("SELECT s.Oid, c.ClassName, cs.chi_name, s.depart_class, s.cscode, s.credit, s.thour FROM " +
					"Dtime s, Csno cs, Class c WHERE s.depart_class=c.ClassNo AND s.cscode=cs.cscode AND s.techid='"+Uid+"' AND Sterm='"+manager.getSchoolTerm()+"'");
			
			
			List tutor=manager.ezGetBy("SELECT cl.ClassName, cl.ClassNo FROM ClassInCharge c, empl e, Class cl WHERE " +
					"e.Oid=c.EmpOid AND c.ModuleOids LIKE '%86%' AND cl.ClassNo=c.ClassNo AND e.idno='"+Uid+"' GROUP BY c.ClassNo");
			
			request.setAttribute("tutor", tutor);
			request.setAttribute("thisdtime", thisdtime);
			
			
			request.setAttribute("alldtime", alldtime);
		}else{
			//未指明學生，全班學生資訊
			if(request.getParameter("type")==null){
				
				
				//System.out.println();
				
				
				//未指明學生
				List students;
				//取班級學生資訊			
				if(request.getParameter("cscode")!=null){//歷史授課
					String cscode=request.getParameter("cscode");
					String depart_class=request.getParameter("depart_class");
					String year=request.getParameter("year");
					String term=request.getParameter("term");
					
					students=manager.ezGetBy("SELECT ev.file_name, e.path, s.student_no, st.student_name, st.Email, st.entrance, st.schl_code FROM " +
							"ScoreHist s, (stmd st LEFT OUTER JOIN Eps_user e ON e.Uid=st.student_no)LEFT OUTER JOIN Eps_vitae ev ON st.student_no=ev.student_no " +
							"WHERE s.student_no=st.student_no AND s.school_year='"+year+"' AND s.school_term='"+term+"' AND " +
							"s.stdepart_class='"+depart_class+"' AND cscode='"+cscode+"' ORDER BY st.student_no");				
					
					students.addAll(manager.ezGetBy("SELECT ev.file_name, e.path, s.student_no, st.student_name, st.Email, st.entrance, st.schl_code FROM " +
							"ScoreHist s, (Gstmd st LEFT OUTER JOIN Eps_user e ON e.Uid=st.student_no)LEFT OUTER JOIN Eps_vitae ev ON st.student_no=ev.student_no " +
							"WHERE s.student_no=st.student_no AND s.school_year='"+year+"' AND s.school_term='"+term+"' AND " +
							"s.stdepart_class='"+depart_class+"' AND cscode='"+cscode+"' ORDER BY st.student_no"));
					
				}else{//以開課
					String depart_class=request.getParameter("depart_class");					
					if(request.getParameter("tutor")==null){
						students=manager.ezGetBy("SELECT ev.file_name, e.path, st.student_no, st.student_name, st.Email, st.entrance, st.schl_code FROM " +
								"(stmd st LEFT OUTER JOIN Eps_user e ON e.Uid=st.student_no)LEFT OUTER JOIN Eps_vitae ev ON st.student_no=ev.student_no, Seld d WHERE d.student_no=st.student_no AND " +
								"d.Dtime_oid='"+depart_class+"' ORDER BY st.student_no");
					}else{
						//以導師
						students=manager.ezGetBy("SELECT ev.file_name,e.path, st.student_no, st.student_name, st.Email, st.entrance, st.schl_code FROM " +
								"(stmd st LEFT OUTER JOIN Eps_user e ON e.Uid=st.student_no)LEFT OUTER JOIN Eps_vitae ev ON st.student_no=ev.student_no WHERE st.depart_class='"+depart_class+"' ORDER BY st.student_no");
						
					}					
				}
				request.setAttribute("server", manager.ezGetString("SELECT Value FROM Parameter WHERE name='portfolioServer'"));
				request.setAttribute("students", students);
			}else{
				//有指明學生，單一學生的情況
				String type=request.getParameter("type");
				String student_no=request.getParameter("student_no");
				request.setAttribute("type", type);
				Map aStudent=manager.ezGetMap("SELECT st.student_name, c.ClassName FROM " +
				"stmd st, Class c WHERE st.depart_class=c.ClassNo AND st.student_no='"+student_no+"'");
				if(aStudent==null){
					aStudent=manager.ezGetMap("SELECT st.student_name, c.ClassName FROM " +
							"Gstmd st, Class c WHERE st.depart_class=c.ClassNo AND st.student_no='"+student_no+"'");
				}
				request.setAttribute("aStudent", aStudent);
				
				if(type.equals("select")){
					//取選課
					request.setAttribute("mySelected", manager.ezGetBy(
							"SELECT cl.ClassName, c.chi_name, d.credit, d.thour, d.opt FROM " +
							"Seld s, stmd st, Dtime d, Csno c, Class cl WHERE " +
							"c.cscode=d.cscode AND cl.ClassNo=d.depart_class AND " +
							"s.student_no=st.student_no AND d.Oid=s.Dtime_oid AND " +
							"d.Sterm='"+manager.getSchoolTerm()+"' AND s.student_no='"+student_no+"' ORDER BY d.opt"));
				}
				//歷年成績
				if(type.equals("score")){
					List score=manager.ezGetBy(
					"SELECT cl.ClassName, c.chi_name, sh.opt, sh.credit, sh.score FROM " +
					"((ScoreHist sh LEFT OUTER JOIN Csno c ON c.cscode=sh.cscode) " +
					"LEFT OUTER JOIN Class cl ON cl.ClassNo=sh.stdepart_class), " +
					"stmd st WHERE sh.student_no=st.student_no AND sh.student_no='"+student_no+
					" 'ORDER BY sh.school_year, sh.school_term");
					if(score.isEmpty()){
						score=manager.ezGetBy(
								"SELECT cl.ClassName, c.chi_name, sh.opt, sh.credit, sh.score FROM " +
								"((ScoreHist sh LEFT OUTER JOIN Csno c ON c.cscode=sh.cscode) " +
								"LEFT OUTER JOIN Class cl ON cl.ClassNo=sh.stdepart_class), " +
								"Gstmd st WHERE sh.student_no=st.student_no AND sh.student_no='"+student_no+
								" 'ORDER BY sh.school_year, sh.school_term");
					}
					request.setAttribute("score", score);
				}
				
				//核心能力
				if(type.equals("core")){
					request.setAttribute("student_no", student_no);
				}				
			}			
		}
		
		setContentPage(request.getSession(false), "portfolio/teacher/ListMyStudents.jsp");
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
