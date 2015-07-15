package tw.edu.chit.struts.action.personnel;

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

/**
 * 時數管理
 * @author shawn
 *
 */
public class TechlimitManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		request.setAttribute("codes", manager.ezGetBy("SELECT * FROM CodeEmpl WHERE category='TeacherRole'"));
		setContentPage(request.getSession(false), "personnel/TechlimitManager.jsp");		
		return mapping.findForward("Main");
	}
	
	/**
	 * 查詢
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 不可遺漏教職員
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String idno[]=eForm.getStrings("idno");
		String codeEmpl[]=eForm.getStrings("codeEmpl");
		
		String sql="SELECT t.Oid, t.time, t.time_over, c.name, e.idno, e.sname, e.cname, e.unit, c5.name as unitname FROM " +
				"((empl e LEFT OUTER JOIN Empl_techlimit t ON e.idno=t.idno) LEFT OUTER JOIN CodeEmpl c " +
				"ON e.pcode=c.idno)LEFT OUTER JOIN CodeEmpl c5 ON c5.idno=e.unit AND c5.category in('UnitTeach','Unit')" +
				"WHERE e.idno LIKE '"+idno[0]+"%' ";
		
		if(codeEmpl[0].trim().equals("")){
			//all category
			sql=sql+" ORDER BY c5.sequence, c.Oid";
		}else{
			sql=sql+"AND c.idno = '"+codeEmpl[0]+"' ORDER BY c5.sequence, c.Oid";
		}
		
		List list=manager.ezGetBy(sql);		
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "共 "+list.size()+"位同仁"));
		saveMessages(request, msg);		
		session.setAttribute("empls", list);		
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 儲存
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 已建立者update, 未建立者insert, 空白者delete
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String Oid[]=eForm.getStrings("Oid");
		String time[]=eForm.getStrings("time");
		String time_over[]=eForm.getStrings("time_over");
		String idno[]=eForm.getStrings("idno");
		
		for(int i=1; i<Oid.length; i++){
			if(!Oid[i].trim().equals("") || !time[i].trim().equals(""))//1 in 2 empty
			if(!Oid[i].trim().equals("")){
				if(time[i].trim().equals("")){
					//delete
					manager.executeSql("DELETE FROM Empl_techlimit WHERE Oid="+Oid[i]);
				}else{
					//update
					if(time_over[i].trim().equals("")){
						manager.executeSql("UPDATE Empl_techlimit SET time='"+time[i]+"', time_over='0' WHERE Oid="+Oid[i]);
					}else{
						manager.executeSql("UPDATE Empl_techlimit SET time='"+time[i]+"', time_over='"+time_over[i]+"' WHERE Oid="+Oid[i]);
					}
				}
			}			
			else{
				//insert
				manager.executeSql("INSERT INTO Empl_techlimit(idno, time, time_over)VALUES('"+idno[i]+"', '"+time[i]+"', '"+time_over[i]+"');");
			}			
		}		
		return query(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Save", "save");
		return map;
	}
}
