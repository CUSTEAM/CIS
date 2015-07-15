package tw.edu.chit.struts.action.AMS.doc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class LeaveDocManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		request.setAttribute("leaders", manager.ezGetBy("SELECT a.Oid as aOid," +
				"(SELECT COUNT(*)FROM empl WHERE empl.unit=c.idno)as cnt," +
				"c.name as uname, c.idno as uidno,  e.cname, e.idno FROM " +
				"(CodeEmpl c LEFT OUTER JOIN AMS_AskHandler a ON c.idno=a.unit_id) " +
				"LEFT OUTER JOIN empl e ON e.idno=a.empl_id WHERE c.category='Unit' OR " +
				"c.category='UnitTeach' ORDER BY c.category, c.sequence"));
		
		
		setContentPage(request.getSession(false), "AMS/doc/LeaveDocManager.jsp");
		return mapping.findForward("Main");
	}
	
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//TODO 不處理自動新增單位，預計近期設計新的差勤系統
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm eForm = (DynaActionForm) form;
		
		String Oid[]=eForm.getStrings("Oid");
		String empl_id[]=eForm.getStrings("empl_id");
		
		
		for(int i=0; i<Oid.length; i++){
				manager.executeSql("UPDATE AMS_AskHandler SET empl_id='"+empl_id[i]+"' WHERE Oid='"+Oid[i]+"'");
		}
		
		return unspecified(mapping, form, request,  response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Save", "save");
		return map;
	}

}
