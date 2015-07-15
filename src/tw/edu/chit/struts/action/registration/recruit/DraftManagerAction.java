package tw.edu.chit.struts.action.registration.recruit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class DraftManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");		
		if(request.getParameter("dOid")!=null){
			manager.executeSql("DELETE FROM Recruit_draft WHERE Oid="+request.getParameter("dOid"));
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "刪除完成"));
			saveMessages(request, msg);
		}		
		request.setAttribute("schools", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='School' ORDER BY sequence"));
		request.setAttribute("depts", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Dept' ORDER BY sequence"));
		setContentPage(request.getSession(false), "registration/recruit/DraftManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm sForm = (DynaActionForm) form;
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		String school_year[]=sForm.getStrings("school_year");
		String SchoolNo[]=sForm.getStrings("SchoolNo");
		String DeptNo[]=sForm.getStrings("DeptNo");
		String school_code[]=sForm.getStrings("school_code");		
		
		List alls=manager.ezGetBy("SELECT s.name, a.*, c5.name as schoolName, c51.name as deptName FROM " +
				"Recruit_school s, Recruit_draft a, code5 c5, code5 c51 WHERE c51.idno=a.DeptNo AND c51.category='Dept' AND " +
				"c5.idno=a.SchoolNo AND c5.category='School' AND s.no=a.school_code AND a.school_year LIKE '"+school_year[0]+"%' AND " +
				"a.SchoolNo LIKE '"+SchoolNo[0]+"%' AND a.DeptNo LIKE '"+DeptNo[0]+"%' AND a.school_code LIKE'"+school_code[0]+"%'");
		
		float x;//報名人數
		float y;//錄取
		float z;//報到
		for(int i=0; i<alls.size(); i++){			
			try{
				x=Integer.parseInt(((Map)alls.get(i)).get("standard").toString());
				y=Integer.parseInt(((Map)alls.get(i)).get("enroll").toString());
				z=Integer.parseInt(((Map)alls.get(i)).get("realin").toString());
				
				((Map)alls.get(i)).put("engage", manager.roundOff((y/x)*100, 2));//錄取率
				((Map)alls.get(i)).put("checkin", manager.roundOff((z/y)*100, 2));//報到率				
			}catch(Exception e){
				((Map)alls.get(i)).put("engage", 0);//錄取率
				((Map)alls.get(i)).put("checkin", 0);//報到率
			}			
		}
		
		request.setAttribute("alls", alls);
		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		DynaActionForm sForm = (DynaActionForm) form;
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		String school_year[]=sForm.getStrings("school_year");
		String SchoolNo[]=sForm.getStrings("SchoolNo");
		String DeptNo[]=sForm.getStrings("DeptNo");
		String school_code[]=sForm.getStrings("school_code");		
		String standard[]=sForm.getStrings("standard");
		String enroll[]=sForm.getStrings("enroll");
		String real[]=sForm.getStrings("real");		
		try{
			manager.executeSql("INSERT INTO Recruit_draft(school_year, SchoolNo, DeptNo, school_code, standard, enroll, realin)" +
					"VALUES('"+school_year[0]+"', '"+SchoolNo[0]+"', '"+DeptNo[0]+"', '"+school_code[0]+"', '"+standard[0]+"', '"+enroll[0]+"', '"+real[0]+"')");
		}catch(Exception e){
			e.printStackTrace();
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立失敗"));
			saveErrors(request, error);
			return query(mapping, form, request, response);
		}		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立完成"));
		saveMessages(request, msg);
		return query(mapping, form, request, response);
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm sForm = (DynaActionForm) form;
		//HttpSession session = request.getSession(false);		
		CourseManager manager = (CourseManager) getBean("courseManager");	
		String Oid[]=sForm.getStrings("Oid");
		String school_year[]=sForm.getStrings("school_year");
		String SchoolNo[]=sForm.getStrings("SchoolNo");
		String DeptNo[]=sForm.getStrings("DeptNo");
		//String school_code[]=sForm.getStrings("school_code");		
		String standard[]=sForm.getStrings("standard");
		String enroll[]=sForm.getStrings("enroll");
		String real[]=sForm.getStrings("real");		
		
		ActionMessages error = new ActionMessages();
		for(int i=1; i<Oid.length; i++){			
			try{				
				manager.executeSql("UPDATE Recruit_draft SET school_year='"+school_year[i]+"' ," +
						"SchoolNo='"+SchoolNo[i]+"', " +
						"DeptNo='"+DeptNo[i]+"', " +
						"standard='"+standard[i]+"', " +
						"enroll='"+enroll[i]+"', " +
						"realin='"+real[i]+"' WHERE Oid='"+Oid[i]+"'");
			}catch(Exception e){
				e.printStackTrace();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "修改第"+i+"筆時發生錯誤"));
			}			
		}
		
		if(!error.isEmpty()){
			saveErrors(request, error);
		}else{
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "修改完成"));
			saveMessages(request, msg);
		}
		
		return query(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Create", "create");
		map.put("Save", "save");
		return map;
	}

}
