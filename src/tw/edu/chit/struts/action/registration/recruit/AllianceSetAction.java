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

public class AllianceSetAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");		
		if(request.getParameter("dOid")!=null){
			manager.executeSql("DELETE FROM Recruit_alliance WHERE Oid="+request.getParameter("dOid"));
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "刪除完成"));
			saveMessages(request, msg);
			
			//懶
			DynaActionForm sForm = (DynaActionForm) form;
			String school_year[]=sForm.getStrings("school_year");
			String SchoolNo[]=sForm.getStrings("SchoolNo");
			String DeptNo[]=sForm.getStrings("DeptNo");		
			
			List alls=manager.ezGetBy("SELECT a.* FROM " +
					"Recruit_alliance a WHERE a.school_year LIKE '"+school_year[0]+"%' AND " +
					"a.SchoolNo LIKE '"+SchoolNo[0]+"%' AND a.DeptNo LIKE '"+DeptNo[0]+"%'");
			
			float x;//報名
			float y;//錄取
			for(int i=0; i<alls.size(); i++){			
				try{
					x=Integer.parseInt(((Map)alls.get(i)).get("enroll").toString());
					y=Integer.parseInt(((Map)alls.get(i)).get("realin").toString());				
					((Map)alls.get(i)).put("engage", manager.roundOff((y/x)*100, 2));//錄取率
					((Map)alls.get(i)).put("SchoolName", manager.ezGetString("SELECT name FROM code5 WHERE idno='"+((Map)alls.get(i)).get("SchoolNo")+"' AND category='School'"));//學制
					((Map)alls.get(i)).put("deptName", manager.ezGetString("SELECT name FROM code5 WHERE idno='"+((Map)alls.get(i)).get("DeptNo")+"' AND category='Dept'"));//科系
					
				}catch(Exception e){
					((Map)alls.get(i)).put("engage", 0);//錄取率
				}			
			}		
			request.setAttribute("alls", alls);
			//懶
		}		
		request.setAttribute("schools", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='School' ORDER BY sequence"));
		request.setAttribute("depts", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Dept' ORDER BY sequence"));
		setContentPage(request.getSession(false), "registration/recruit/AllianceSet.jsp");
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
		
		List alls=manager.ezGetBy("SELECT a.* FROM " +
				"Recruit_alliance a WHERE a.school_year LIKE '"+school_year[0]+"%' AND " +
				"a.SchoolNo LIKE '"+SchoolNo[0]+"%' AND a.DeptNo LIKE '"+DeptNo[0]+"%'");
		
		float x;//報名
		float y;//錄取
		for(int i=0; i<alls.size(); i++){			
			try{
				x=Integer.parseInt(((Map)alls.get(i)).get("enroll").toString());
				y=Integer.parseInt(((Map)alls.get(i)).get("realin").toString());				
				((Map)alls.get(i)).put("engage", manager.roundOff((y/x)*100, 2));//錄取率
				((Map)alls.get(i)).put("SchoolName", manager.ezGetString("SELECT name FROM code5 WHERE idno='"+((Map)alls.get(i)).get("SchoolNo")+"' AND category='School'"));//學制
				((Map)alls.get(i)).put("deptName", manager.ezGetString("SELECT name FROM code5 WHERE idno='"+((Map)alls.get(i)).get("DeptNo")+"' AND category='Dept'"));//科系
				
			}catch(Exception e){
				((Map)alls.get(i)).put("engage", 0);//錄取率
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
		String enroll[]=sForm.getStrings("enroll");
		String real[]=sForm.getStrings("real");		
		
		try{
			if(Integer.parseInt(real[0])>Integer.parseInt(enroll[0])){
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "報名和錄取人數不正確"));
				saveErrors(request, error);
				return query(mapping, form, request, response);
			}
			manager.executeSql("INSERT INTO Recruit_alliance(school_year, SchoolNo, DeptNo, realin, enroll)" +
					"VALUES('"+school_year[0]+"', '"+SchoolNo[0]+"', '"+DeptNo[0]+"', '"+real[0]+"', '"+enroll[0]+"')");
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
		String enroll[]=sForm.getStrings("enroll");
		String real[]=sForm.getStrings("real");
		
		
		
		ActionMessages error = new ActionMessages();
		for(int i=1; i<Oid.length; i++){
			
			
			//if(!school_year[i].equals("") && !SchoolNo[i].equals("") && !DeptNo[i].equals("") && !enroll[i].equals("") && !real[i].equals("")){
				try{
					manager.executeSql("UPDATE Recruit_alliance SET school_year='"+school_year[i]+"', SchoolNo='"+SchoolNo[i]+"', " +
							"DeptNo='"+DeptNo[i]+"', enroll='"+enroll[i]+"', realin='"+real[i]+"' WHERE Oid='"+Oid[i]+"'");
				}catch(Exception e){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "修改第"+i+"筆時發生錯誤"));				
				}	
			//}else{
				//error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "修改第"+i+"筆時發現欄位錯誤"));
			//}
					
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
		map.put("Create", "create");
		map.put("Query", "query");
		map.put("Save", "save");
		return map;
	}

}
