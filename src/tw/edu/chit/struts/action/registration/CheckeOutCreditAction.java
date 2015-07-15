package tw.edu.chit.struts.action.registration;

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

import tw.edu.chit.model.Member;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class CheckeOutCreditAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		

		
		setContentPage(request.getSession(false), "registration/CheckOutCredit.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm cForm = (DynaActionForm) form;
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String start_year[]=cForm.getStrings("start_year");
		String end_year[]=cForm.getStrings("end_year");
		
		String opt1[]=cForm.getStrings("opt1");
		String opt2[]=cForm.getStrings("opt2");
		String opt3[]=cForm.getStrings("opt3");		
		
		String SchoolNo[]=cForm.getStrings("SchoolNo");
		String DeptNo[]=cForm.getStrings("DeptNo");
		
		StringBuilder sb=new StringBuilder("SELECT * FROM StmdGrdeCredit s WHERE " +
				//"s.CampusNo LIKE'"+CampusNo[0]+"%' AND " +
				"SchoolNo LIKE'"+SchoolNo[0]+"%' AND " +
				"DeptNo LIKE'"+DeptNo[0]+"%' ");
		
		
		if(!start_year[0].trim().equals("")){
			sb.append(" AND (s.start_year>="+start_year[0]+" OR s.start_year IS NULL)");
		}
		if(!end_year[0].trim().equals("")){
			sb.append(" AND s.end_year<="+end_year[0]);
		}
		
		
		sb.append(" AND opt1 LIKE '"+opt1[0]+"%'");		
		sb.append(" AND opt2 LIKE '"+opt2[0]+"%'");
		sb.append(" AND opt3 LIKE '"+opt3[0]+"%' ORDER BY SchoolNo, DeptNo");		
		
		List list=manager.ezGetBy(sb.toString());
		
		for(int i=0; i<list.size(); i++){
			try{
				((Map)list.get(i)).put("editorName", manager.getEmplOrDempl(((Map)list.get(i)).get("editor").toString()).get("cname"));
			}catch(Exception e){
				((Map)list.get(i)).put("editorName", "");
			}
			
		}
		
		session.setAttribute("result", list);
		
		
		return mapping.findForward("Main");
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		DynaActionForm cForm = (DynaActionForm) form;
		Member me = getUserCredential(request.getSession(false)).getMember();
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String start_year[]=cForm.getStrings("start_year");
		String end_year[]=cForm.getStrings("end_year");
		
		String opt1[]=cForm.getStrings("opt1");
		String opt2[]=cForm.getStrings("opt2");
		String opt3[]=cForm.getStrings("opt3");
		
		String SchoolNo[]=cForm.getStrings("SchoolNo");
		String DeptNo[]=cForm.getStrings("DeptNo");
		if(start_year[0].trim().equals("")&&end_year[0].trim().equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "建立失敗, 開始或結束的學年至少要有1個"));
			saveErrors(request, error);
		}
		
		if(
			//!start_year[0].trim().equals("")&&
			//!end_year[0].trim().equals("")&&
			!opt1[0].trim().equals("")&&
			!opt2[0].trim().equals("")&&
			!opt3[0].trim().equals("")&&
			
			!SchoolNo[0].trim().equals("")&&
			!DeptNo[0].trim().equals("")){
			
			StringBuilder sb=new StringBuilder("INSERT INTO StmdGrdeCredit(");			
			//System.out.println(start_year[0]);
			//System.out.println(end_year[0]);
			if(!start_year[0].equals("")){
				sb.append("start_year, ");
			}
			
			if(!end_year[0].equals("")){
				sb.append("end_year, ");
			}			
			
			sb.append("opt1, opt2, opt3, SchoolNo, DeptNo, editor)VALUES(");
			if(!start_year[0].equals("")){
				sb.append("'"+start_year[0]+"', ");
			}
			if(!end_year[0].equals("")){
				sb.append("'"+end_year[0]+"', ");
			}
					
										
			sb.append(
			"'"+opt1[0].trim()+"', "+
			"'"+opt2[0].trim()+"', "+
			"'"+opt3[0].trim()+"', "+
			
			"'"+SchoolNo[0].trim()+"', "+
			"'"+DeptNo[0].trim()+"', "+
			"'"+me.getIdno()+"') ");
			//System.out.println(sb);
			manager.executeSql(sb.toString());
			
		}else{
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "建立失敗, 請檢查欄位空白以及是否與既有的重複"));
			saveErrors(request, error);
		}
		
		return query(mapping, form, request, response);

	}
	
	public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm cForm = (DynaActionForm) form;
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		Member me = getUserCredential(request.getSession(false)).getMember();
		
		String start_year[]=cForm.getStrings("start_year");
		String end_year[]=cForm.getStrings("end_year");	
		
		String opt1[]=cForm.getStrings("opt1");
		String opt2[]=cForm.getStrings("opt2");
		String opt3[]=cForm.getStrings("opt3");
		
		String SchoolNo[]=cForm.getStrings("SchoolNo");
		String DeptNo[]=cForm.getStrings("DeptNo");	
		
		String Oid[]=cForm.getStrings("Oid");
		
		
		StringBuilder sb = null;
		for(int i=0; i<Oid.length; i++){
			
			if(!Oid[i].equals("")){
				
				if(
						//!start_year[i].trim().equals("")&&
						//!end_year[i].trim().equals("")&&
						!opt1[i].trim().equals("")&&
						!opt2[i].trim().equals("")&&
						!opt3[i].trim().equals("")&&
						!SchoolNo[i].trim().equals("")&&
						!DeptNo[i].trim().equals(""))
				
				sb=new StringBuilder("UPDATE StmdGrdeCredit SET " +
						"SchoolNo='"+SchoolNo[i]+"', " +
						"DeptNo='"+DeptNo[i]+"', ");
				
				if(!end_year[i].equals("")){
					sb.append("end_year='"+end_year[i]+"', ");
				}else{
					sb.append("end_year=null, ");
				}
				if(!start_year[i].equals("")){
					sb.append("start_year='"+start_year[i]+"', ");
				}else{
					sb.append("start_year=null, ");
				}
				sb.append("opt1='"+opt1[i]+"', " +
						"opt2='"+opt2[i]+"', " +
						"opt3='"+opt3[i]+"', " +
						
						"editor='"+me.getIdno()+"' " +
						
						"WHERE Oid='"+Oid[i]+"'");
				System.out.println(sb);
				manager.executeSql(sb.toString());
			}
			
		}
		
		return query(mapping, form, request, response);

	}
	
	public ActionForward clear(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm cForm = (DynaActionForm) form;
		HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		//Member me = getUserCredential(request.getSession(false)).getMember();		
		cForm.set("start_year", null);
		cForm.set("end_year", null);
		cForm.set("opt1", null);
		cForm.set("opt2", null);
		cForm.set("opt3", null);
		cForm.set("SchoolNo", null);
		cForm.set("DeptNo", null);		
		session.removeAttribute("result");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Create", "create");
		map.put("Modify", "modify");
		map.put("Clear", "clear");
		return map;
	}

}
