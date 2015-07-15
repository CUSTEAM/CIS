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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class UpgradeAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("allDept", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='Dept' AND idno <>'0' ORDER BY sequence"));
		
		setContentPage(request.getSession(false), "registration/Upgrade.jsp");
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
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
		HttpServletResponse response)throws Exception {
		DynaActionForm sForm = (DynaActionForm) form;
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		String campusInCharge2=sForm.getString("campusInCharge2");
		String schoolInCharge2=sForm.getString("schoolInCharge2");
		String deptInCharge2=sForm.getString("deptInCharge2");
		String grade=sForm.getString("grade");
		
		//System.out.println(campusInCharge2+schoolInCharge2+deptInCharge2+grade);
		
		
		
		System.out.println("SELECT s.entrance, c.ClassNo, c.ClassName, s.student_no, s.student_name " +
				"FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.depart_class LIKE '"+
				campusInCharge2+schoolInCharge2+deptInCharge2+grade+"%'");
		request.setAttribute("students", manager.ezGetBy("SELECT s.entrance, c.ClassNo, c.ClassName, s.student_no, s.student_name " +
				"FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.depart_class LIKE '"+
				campusInCharge2+schoolInCharge2+deptInCharge2+grade+"%'"));
		return mapping.findForward("Main");

	}
	
	public ActionForward levelUp(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;
		String campusInCharge2=sForm.getString("campusInCharge2");
		String schoolInCharge2=sForm.getString("schoolInCharge2");
		String deptInCharge2=sForm.getString("deptInCharge2");
		String grade=sForm.getString("grade");
		
		List classes=manager.ezGetBy("SELeCT * FROM Class WHERE ClassNo LIKE '"+campusInCharge2+schoolInCharge2+deptInCharge2+grade+"%' ORDER BY Grade DESC");
		
		ActionMessages error = new ActionMessages();
		Clazz clazz;
		Clazz newClazz;
		for(int i=0; i<classes.size(); i++){			
			try{				
				clazz=(Clazz)manager.hqlGetBy("FROM Clazz WHERE ClassNo='"+((Map)classes.get(i)).get("ClassNo")+"'").get(0);
				newClazz=(Clazz)manager.hqlGetBy("FROM Clazz WHERE ClassNo='"+
						clazz.getCampusNo()+clazz.getSchoolNo()+clazz.getDeptNo()+
						(Integer.parseInt(clazz.getGrade())+1)+clazz.getClassNo().
						charAt(clazz.getClassNo().length()-1)+"'").get(0);
				manager.executeSql("UPDATE stmd SET depart_class='"+newClazz.getClassNo()+"' WHERE depart_class='"+((Map)classes.get(i)).get("ClassNo")+"'");
			}catch(Exception e){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", ((Map)classes.get(i)).get("ClassNo")+", "));				
			}
		}
		
		if(!error.isEmpty()){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "以上班級學生升級不成功"));
			saveErrors(request, error);
		}
		return mapping.findForward("Main");
	}
	
	public ActionForward levelDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;
		String campusInCharge2=sForm.getString("campusInCharge2");
		String schoolInCharge2=sForm.getString("schoolInCharge2");
		String deptInCharge2=sForm.getString("deptInCharge2");
		String grade=sForm.getString("grade");
		
		List classes=manager.ezGetBy("SELeCT * FROM Class WHERE ClassNo LIKE '"+campusInCharge2+schoolInCharge2+deptInCharge2+grade+"%' ORDER BY Grade");
		
		ActionMessages error = new ActionMessages();
		Clazz clazz;
		Clazz newClazz;
		for(int i=0; i<classes.size(); i++){			
			try{
				
				clazz=(Clazz)manager.hqlGetBy("FROM Clazz WHERE ClassNo='"+((Map)classes.get(i)).get("ClassNo")+"'").get(0);
				newClazz=(Clazz)manager.hqlGetBy("FROM Clazz WHERE ClassNo='"+
						clazz.getCampusNo()+clazz.getSchoolNo()+clazz.getDeptNo()+
						(Integer.parseInt(clazz.getGrade())-1)+clazz.getClassNo().
						charAt(clazz.getClassNo().length()-1)+"'").get(0);
				manager.executeSql("UPDATE stmd SET depart_class='"+newClazz.getClassNo()+"' WHERE depart_class='"+((Map)classes.get(i)).get("ClassNo")+"'");
			}catch(Exception e){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", ((Map)classes.get(i)).get("ClassNo")+", "));				
			}
		}
		
		
		if(!error.isEmpty()){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "以上班級學生降級不成功"));
			saveErrors(request, error);
		}
		
		
		
		return mapping.findForward("Main");

	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("LevelUp", "levelUp");
		map.put("LevelDown", "levelDown");
		return map;
	}
}