package tw.edu.chit.struts.action.registration.recruit;

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

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class ScheduleCheckAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");		
		request.setAttribute("depts", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Dept' OR category='DeptGroup' ORDER BY sequence"));
		setContentPage(request.getSession(false), "registration/recruit/ScheduleCheck.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		HttpSession session = request.getSession(false);
		DynaActionForm sForm = (DynaActionForm) form;
		
		String DeptNo=sForm.getString("DeptNo");
		String startDay=sForm.getString("startDay");
		String endDay=sForm.getString("endDay");
		
		/*
		StringBuilder sb=new StringBuilder("SELECT s.*, s.name as schoolName, rs.*, ri.name as itemName FROM Recruit_schedule_unit rsu, " +
				"Recruit_school s, Recruit_schedule rs LEFT OUTER JOIN Recruit_item ri ON ri.id=rs.item " +
				"WHERE rsu.schedule_oid=rs.Oid AND s.no=rs.school_code AND rsu.unit_id LIKE '"+DeptNo+"%' ");		
		
		sb.append(" ORDER BY rs.someday DESC");
		
		System.out.println(sb);
		*/
		StringBuilder sb=new StringBuilder("SELECT rs.Oid as rsOid, s.*, s.name as schoolName, rs.*, ri.name as itemName " +
				"FROM Recruit_school s, Recruit_schedule rs LEFT OUTER JOIN Recruit_item ri ON ri.id=rs.item WHERE s.no=rs.school_code ");
		
		if(startDay.trim().length()>=4){
			sb.append("AND rs.someday>='"+startDay+"' ");
		}		
		if(endDay.trim().length()>=4){
			sb.append("AND rs.someday<='"+endDay+"' ");
		}
		
		sb.append("ORDER BY rs.someday DESC");
		
		Map map;	
		List list=manager.ezGetBy(sb.toString());		
		List tmp;
		List scheduleChecks=new ArrayList();
		for(int i=0; i<list.size(); i++){			
			
			tmp=manager.ezGetBy("SELECT rsu.unit_id, c.name as deptName FROM Recruit_schedule_unit rsu " +
					"LEFT OUTER JOIN  code5 c ON c.idno=rsu.unit_id AND " +
					"(c.category='Dept' OR c.category='DeptGroup') WHERE " +
					"rsu.schedule_oid='"+((Map)list.get(i)).get("rsOid")+"' AND rsu.unit_id LIKE'"+DeptNo+"%'");		
			
			for(int j=0; j<tmp.size(); j++){
				((Map)list.get(i)).put("deptName", null);
				
				map=new HashMap();
				map.putAll(((Map)list.get(i)));
				map.put("deptName", ((Map)tmp.get(j)).get("deptName"));
				map.put("link", "<a href='../Print/registration/ScheduleDoc.do?Oid="+map.get("Oid")+"&dept="+((Map)tmp.get(j)).get("unit_id")+"'>活動明細</a>");
				scheduleChecks.add(map);
				
			}			
		}
		
		
		session.setAttribute("scheduleChecks", scheduleChecks);		
		session.setAttribute("startDay", startDay);
		session.setAttribute("endDay", endDay);
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		return map;
	}

}
