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
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class QueryRcactAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		Toolket.resetCheckboxCookie(response, "rcact");
		//setContentPage(request.getSession(false), "teacher/QueryRcact.jsp");
		return mapping.findForward("QueryRcact");
	}
	
	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Toolket.resetCheckboxCookie(response, "rcact");
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
		
		
		List<Rcact> rcact = manager.ezGetBy(
				"Select R.Oid Oid, R.school_year, E.cname idno, R.actname, R.sponoff, R.bdate, R.edate"+
				  " From Rcact R, empl E"+
				  " Where R.idno = E.idno" +
				  //"   And R.approve = '97'"+          //僅提供核可後的資料
				      SelIdno + SelYear
		);
		session.setAttribute("RcactList", rcact);
				
		//setContentPage(request.getSession(false), "teacher/QueryRcact.jsp");
		return mapping.findForward("QueryRcact");
	}
	
	/**
	 * 檢視資料
	 */
	public ActionForward View(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Toolket.resetCheckboxCookie(response, "rcact");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		//UserCredential user = (UserCredential) session.getAttribute("Credential");
		//ActionMessages messages = new ActionMessages();

		//String teacherId = user.getMember().getAccount();
		String oids = Toolket.getSelectedIndexFromCookie(request, "rcact");
		String oid_s = oids.substring(1, oids.length() - 1);

		String tchIdno = manager.ezGetString("Select idno From Rcact Where Oid='" + oid_s + "'");
		String UnitIdno = manager.ezGetString("Select unit From empl Where idno='" + tchIdno+ "'");
		String kind = manager.ezGetString("Select kindid From Rcact Where Oid='" + oid_s+ "'");
		String type = manager.ezGetString("Select typeid From Rcact Where Oid='" + oid_s+ "'");
		String place = manager.ezGetString("Select placeid From Rcact Where Oid='" + oid_s+ "'");
		String join = manager.ezGetString("Select joinid From Rcact Where Oid='" + oid_s+ "'");
		String certyn = manager.ezGetString("Select certyn From Rcact Where Oid='" + oid_s+ "'");
		if (certyn.equals("Y")) {
			certyn = "有";
		} else if (certyn.equals("N")) {
			certyn = "無";
		}

		session.setAttribute("oid_s", oid_s);
		session.setAttribute("school_year", manager.ezGetString("Select school_year From Rcact Where Oid='"+ oid_s + "'"));
		session.setAttribute("TeacherName", manager.ezGetString("Select cname From empl Where idno='" + tchIdno+ "'"));
		session.setAttribute("TeacherUnit", manager.ezGetString("Select name From CodeEmpl Where idno='"+ UnitIdno + "'"));
		session.setAttribute("actname", manager.ezGetString("Select actname From Rcact Where Oid='" + oid_s+ "'"));
		session.setAttribute("sponoff", manager.ezGetString("Select sponoff From Rcact Where Oid='" + oid_s+ "'"));
		session.setAttribute("kindid",manager.ezGetString("Select name From Rccode Where Oid='"+ kind + "'"));
		session.setAttribute("typeid",manager.ezGetString("Select name From Rccode Where Oid='"+ type + "'"));
		session.setAttribute("placeid", manager.ezGetString("Select name From Rccode Where Oid='" + place+ "'"));
		session.setAttribute("joinid",manager.ezGetString("Select name From Rccode Where Oid='"+ join + "'"));
		session.setAttribute("bdate", manager.ezGetString("Select bdate From Rcact Where Oid='" + oid_s+ "'"));
		session.setAttribute("edate", manager.ezGetString("Select edate From Rcact Where Oid='" + oid_s+ "'"));
		session.setAttribute("hour",manager.ezGetString("Select hour From Rcact Where Oid='"+ oid_s + "'"));
		session.setAttribute("certno", manager.ezGetString("Select certno From Rcact Where Oid='" + oid_s+ "'"));
		session.setAttribute("schspon", manager.ezGetString("Select schspon From Rcact Where Oid='" + oid_s+ "'"));
		session.setAttribute("certyn", certyn);
		session.setAttribute("intor", manager.ezGetString("Select intor From Rcact Where Oid='" + oid_s+ "'"));

		//setContentPage(request.getSession(false),"teacher/QueryRcact_View.jsp");
		return mapping.findForward("QueryRcact");
	}

	/**
	 * 返回
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		

		//setContentPage(request.getSession(false), "teacher/Rcact_Query.jsp");
		return mapping.findForward("QueryRcact");

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
