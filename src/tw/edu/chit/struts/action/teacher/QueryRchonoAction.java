package tw.edu.chit.struts.action.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Rcact;
import tw.edu.chit.model.Rchono;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class QueryRchonoAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		Toolket.resetCheckboxCookie(response, "rchono");
		//setContentPage(request.getSession(false), "teacher/QueryRcact.jsp");
		return mapping.findForward("QueryRchono");
	}
	
	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Toolket.resetCheckboxCookie(response, "rchono");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm aForm = (DynaActionForm) form;
						
		String SelYear = " And R.school_year Like ('%"+ aForm.getString("schoolYear") +"%')";		
		String SelIdno = " And E.cname Like ('%"+ aForm.getString("fscname") +"%')";
		
		
		if(aForm.getString("schoolYear").equals("")){
			SelYear = "";
		}
		if(aForm.getString("fscname").equals("")){
			SelIdno = "";
		}
		
		
		List<Rchono> rchono = manager.ezGetBy(
				  " Select R.Oid Oid, R.school_year, E.cname idno, R.title, R.nation, R.inst "+
				  " From Rchono R, empl E"+
				  " Where R.idno = E.idno" +
				  //"   And R.approve = '97'"+          //僅圖供核可後的資料
				      SelIdno + SelYear
		);
		session.setAttribute("RchonoList", rchono);
				
		//setContentPage(request.getSession(false), "teacher/QueryRcact.jsp");
		return mapping.findForward("QueryRchono");
	}
	
	/**
	 * 檢視資料
	 */
	public ActionForward View(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		//UserCredential user = (UserCredential) session.getAttribute("Credential");
		//ActionMessages messages = new ActionMessages();

		//String teacherId = user.getMember().getAccount();
		String oids = Toolket.getSelectedIndexFromCookie(request, "rchono");
		String oid_s = oids.substring(1, oids.length()-1);
		
		String tchIdno = manager.ezGetString("Select idno From Rchono Where Oid='" + oid_s + "'");
		String UnitIdno = manager.ezGetString("Select unit From empl Where idno='" + tchIdno+ "'");
		String authorno = manager.ezGetString("Select authorno From Rchono Where Oid='"+oid_s+"'");
		
		
		
		session.setAttribute("oid_s", oid_s);
		session.setAttribute("school_year", manager.ezGetString("Select school_year From Rchono Where Oid='"+ oid_s + "'"));
		session.setAttribute("TeacherName", manager.ezGetString("Select cname From empl Where idno='" + tchIdno+ "'"));
		session.setAttribute("TeacherUnit", manager.ezGetString("Select name From CodeEmpl Where idno='"+ UnitIdno + "'"));
		session.setAttribute("projno", manager.ezGetString("Select projno From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("title", manager.ezGetString("Select title From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("authorno", manager.ezGetString("Select name From Rccode Where Oid='"+authorno+"'"));
		session.setAttribute("nation", manager.ezGetString("Select nation From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("inst", manager.ezGetString("Select inst From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("bdate", manager.ezGetString("Select bdate From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("intor", manager.ezGetString("Select intor From Rchono Where Oid='"+oid_s+"'"));

		//setContentPage(request.getSession(false),"teacher/QueryRcact_View.jsp");
		return mapping.findForward("QueryRchono");
	}

	/**
	 * 返回
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		

		//setContentPage(request.getSession(false), "teacher/Rcact_Query.jsp");
		return mapping.findForward("QueryRchono");

	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query", "Query");
		map.put("View", "View");
		map.put("Back", "Back");
		return map;
	}
	


}
