package tw.edu.chit.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.service.CourseManager;

public class getStdCreditAction extends BaseAction{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		try{
			Clazz c=(Clazz) manager.hqlGetBy("FROM Clazz WHERE ClassNo='"+request.getParameter("ClassNo")+"'").get(0);		
			int year=manager.getSchoolYear();		
			request.setAttribute("StmdGrdeCredit", manager.ezGetBy("SELECT c.* FROM StmdGrdeCredit c WHERE " +
			"c.SchoolNo='64' AND c.DeptNo='D' AND c.start_year>="+(year-6)+" AND c.end_year<="+year+1));		
			request.setAttribute("DeptName", manager.ezGetMap("SELeCT school_name, fname FROM dept d WHERE d.no='"+c.getCampusNo()+c.getSchoolNo()+c.getDeptNo()+"'"));
			
		}catch(Exception e){
			request.setAttribute("DeptName", "沒有設定學分規劃");
		}
		
		return mapping.findForward("index");
	}

}
