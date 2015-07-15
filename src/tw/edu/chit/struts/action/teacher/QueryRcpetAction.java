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
import tw.edu.chit.model.Rcpet;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class QueryRcpetAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		Toolket.resetCheckboxCookie(response, "rcpet");
		//setContentPage(request.getSession(false), "teacher/QueryRcact.jsp");
		return mapping.findForward("QueryRcpet");
	}
	
	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Toolket.resetCheckboxCookie(response, "rcpet");
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
		
		
		List<Rcpet> rcpet = manager.ezGetBy(
				  " Select R.Oid Oid, R.school_year, E.cname idno, R.title, R.bdate, R.edate, R.inst "+
				  " From Rcpet R, empl E"+
				  " Where R.idno = E.idno" +
				  //"   And R.approve = '97'"+          //僅圖供核可後的資料
				      SelIdno + SelYear
		);
		session.setAttribute("RcpetList", rcpet);
				
		//setContentPage(request.getSession(false), "teacher/QueryRcact.jsp");
		return mapping.findForward("QueryRcpet");
	}
	
	/**
	 * 檢視資料
	 */
	public ActionForward View(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "rcpet");		
		//UserCredential user = (UserCredential) session.getAttribute("Credential");
		//ActionMessages messages = new ActionMessages();

		//String teacherId = user.getMember().getAccount();
		String oids = Toolket.getSelectedIndexFromCookie(request, "rcpet");
		String oid_s = oids.substring(1, oids.length()-1);
		
		String tchIdno = manager.ezGetString("Select idno From Rcpet Where Oid='" + oid_s + "'");
		String UnitIdno = manager.ezGetString("Select unit From empl Where idno='" + tchIdno+ "'");
		String area = manager.ezGetString("Select area From Rcpet Where Oid='"+oid_s+"'");
		String petType = manager.ezGetString("Select petType From Rcpet Where Oid='"+oid_s+"'");
		String score = manager.ezGetString("Select score From Rcpet Where Oid='"+oid_s+"'");
		String schedule = manager.ezGetString("Select schedule From Rcpet Where Oid='"+oid_s+"'");
		String authorno = manager.ezGetString("Select authorno From Rcpet Where Oid='"+oid_s+"'");
		String depute = manager.ezGetString("Select depute From Rcpet Where Oid='"+oid_s+"'");
		String proposerType = manager.ezGetString("Select proposerType From Rcpet Where Oid='"+oid_s+"'");
		
		session.setAttribute("oid_s", oid_s);
		session.setAttribute("school_year", manager.ezGetString("Select school_year From Rcpet Where Oid='"+ oid_s + "'"));
		session.setAttribute("TeacherName", manager.ezGetString("Select cname From empl Where idno='" + tchIdno+ "'"));
		session.setAttribute("TeacherUnit", manager.ezGetString("Select name From CodeEmpl Where idno='"+ UnitIdno + "'"));
		session.setAttribute("projno", manager.ezGetString("Select projno From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("title", manager.ezGetString("Select title From Rcpet Where Oid='"+oid_s+"'"));		
		session.setAttribute("area", manager.ezGetString("Select name From Rccode Where Oid='"+area+"'"));
		session.setAttribute("petType", manager.ezGetString("Select name From Rccode Where Oid='"+petType+"'"));
		session.setAttribute("score", manager.ezGetString("Select name From Rccode Where Oid='"+score+"'"));
		session.setAttribute("schedule", manager.ezGetString("Select name From Rccode Where Oid='"+schedule+"'"));
		session.setAttribute("authorno", manager.ezGetString("Select name From Rccode Where Oid='"+authorno+"'"));
		session.setAttribute("proposer", manager.ezGetString("Select proposer From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("bdate", manager.ezGetString("Select bdate From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("edate", manager.ezGetString("Select edate From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("inst", manager.ezGetString("Select inst From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("certno", manager.ezGetString("Select certno From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("depute", manager.ezGetString("Select name From Rccode Where Oid='"+depute+"'"));
		session.setAttribute("intor", manager.ezGetString("Select intor From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("proposerType", manager.ezGetString("Select name From Rccode Where Oid='"+proposerType+"'"));
		session.setAttribute("deputeMoney", manager.ezGetString("Select deputeMoney From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("deputeBusiness", manager.ezGetString("Select deputeBusiness From Rcpet Where Oid = '"+oid_s+"'"));    //99年新增欄位
		session.setAttribute("deputeSdate", manager.ezGetString("Select deputeSdate From Rcpet Where Oid = '"+oid_s+"'"));          //99年新增欄位
		session.setAttribute("deputeEdate", manager.ezGetString("Select deputeEdate From Rcpet Where Oid = '"+oid_s+"'"));          //99年新增欄位

		//setContentPage(request.getSession(false),"teacher/QueryRcact_View.jsp");
		return mapping.findForward("QueryRcpet");
	}

	/**
	 * 返回
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		

		//setContentPage(request.getSession(false), "teacher/Rcact_Query.jsp");
		return mapping.findForward("QueryRcpet");

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
