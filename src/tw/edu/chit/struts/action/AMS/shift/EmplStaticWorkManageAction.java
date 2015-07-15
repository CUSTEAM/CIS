package tw.edu.chit.struts.action.AMS.shift;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Empl;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class EmplStaticWorkManageAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("allUnit", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE (category='Unit' OR category='UnitTeach') ORDER BY sequence"));
		session.setAttribute("allShift", manager.ezGetBy("SELECT id, name FROM AMS_ShiftTime GROUP BY id"));		
		setContentPage(request.getSession(false), "AMS/shift/EmplStaticWorkManage.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String sid=eForm.getString("sid");//班別
		String sidno=eForm.getString("sidno");//員編
		String sunit=eForm.getString("sunit");
		String fscname=eForm.getString("fscname");
		
		String sb="AND e.WorkShift ='"+sid+"' ";
		
		//沒指定班別
		if(sid.equals("")){
			sb="AND (e.WorkShift LIKE'%' OR e.WorkShift IS NULL) ";
		}
		/*
		if(!sid.equals("")){
			sb="AND e.WorkShift LIKE'%' ";
		}
		*/
		
		request.setAttribute("emplShifts", manager.ezGetBy("SELECT e.*, c.name FROM empl e, CodeEmpl c WHERE e.unit=c.idno AND (c.category='Unit' OR c.category='UnitTeach') AND " +
				"e.idno LIKE'"+sidno+"%' AND e.unit LIKE'"+sunit+"%' AND e.sname!='兼任講師' "+sb+
				"AND e.cname LIKE'"+fscname+"%' ORDER BY unit"));
		
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm eForm = (DynaActionForm) form;
		
		String WorkShift[]=eForm.getStrings("WorkShift");
		String idno[]=eForm.getStrings("idno");
		
		//Empl e;
		for(int i=0; i<idno.length; i++){
			//if(!WorkShift[i].equals("")){
				manager.executeSql("UPDATE empl SET WorkShift='"+WorkShift[i]+"' WHERE idno='"+idno[i]+"'");
			//}
		}
		
		return query(mapping, form, request,  response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Save", "save");
		return map;
	}

}
