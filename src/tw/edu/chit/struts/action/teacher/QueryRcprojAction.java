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
import tw.edu.chit.model.Rcproj;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class QueryRcprojAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		Toolket.resetCheckboxCookie(response, "rcproj");
		//setContentPage(request.getSession(false), "teacher/QueryRcact.jsp");
		return mapping.findForward("QueryRcproj");
	}
	
	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		Toolket.resetCheckboxCookie(response, "rcproj");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm aForm = (DynaActionForm) form;
		
		String teacherId = aForm.getString("fscname");
		String school_Year = aForm.getString("schoolYear");		
		
		String SelYear = " And R.school_year Like ('%"+aForm.getString("schoolYear")+"%')";		
		String SelIdno = " And E.cname Like ('%"+ aForm.getString("fscname") +"%')";		
		
		if(aForm.getString("schoolYear").equals("")){
			SelYear = "";
		}
		if(aForm.getString("fscname").equals("")){
			SelIdno = "";
		}
        
		List<Rcproj> rcproj = manager.ezGetBy(
				" Select R.Oid Oid, R.school_year, E.cname idno, R.projno, R.projname, R.bdate, R.edate " +
                " From Rcproj R, empl E " +
				" Where R.idno = E.idno " +
				//"   And R.approve = '97'" +          //僅圖供核可後的資料		      
				    SelIdno + SelYear
	    );
		
		session.setAttribute("RcprojList", rcproj);		
		
		//setContentPage(request.getSession(false), "teacher/Rcproj_Query.jsp");
		return mapping.findForward("QueryRcproj");
	}
	
	/**
	 * 檢視資料
	 */
	public ActionForward View(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		//UserCredential user = (UserCredential) session.getAttribute("Credential");
		//ActionMessages messages = new ActionMessages();
		Toolket.resetCheckboxCookie(response, "rcproj");
		
		//String teacherId = user.getMember().getAccount();
		String oids = Toolket.getSelectedIndexFromCookie(request, "rcproj");
		String oid_s = oids.substring(1, oids.length()-1);		
		
		String tchIdno = manager.ezGetString("Select idno From Rcproj Where Oid='" + oid_s + "'");
		String UnitIdno = manager.ezGetString("Select unit From empl Where idno='" + tchIdno+ "'");
		
		String kind =  manager.ezGetString("Select kindid From Rcproj Where Oid='"+oid_s+"'");
		String job =  manager.ezGetString("Select jobid From Rcproj Where Oid='"+oid_s+"'");
		String budgetid1 = manager.ezGetString("Select budgetid1 From Rcproj Where Oid='"+oid_s+"'");
		String turnIn =  manager.ezGetString("Select turnIn From Rcproj Where Oid='"+oid_s+"'");
		String turnOut =  manager.ezGetString("Select turnOut From Rcproj Where Oid='"+oid_s+"'");
		String budgetidState =  manager.ezGetString("Select budgetidState From Rcproj Where Oid='"+oid_s+"'");
		
		session.setAttribute("oid_s", oid_s);
		session.setAttribute("school_year", manager.ezGetString("Select school_year From Rcproj Where Oid='"+ oid_s + "'"));
		session.setAttribute("TeacherName", manager.ezGetString("Select cname From empl Where idno='" + tchIdno+ "'"));
		session.setAttribute("TeacherUnit", manager.ezGetString("Select name From CodeEmpl Where idno='"+ UnitIdno + "'"));	
		
		session.setAttribute("projno", manager.ezGetString("Select projno From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("projname", manager.ezGetString("Select projname From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("kindid", manager.ezGetString("Select name From Rccode Where Oid='"+kind+"'"));
		session.setAttribute("bdate", manager.ezGetString("Select bdate From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("edate", manager.ezGetString("Select edate From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("jobid", manager.ezGetString("Select name From Rccode Where Oid='"+job+"'"));		
		session.setAttribute("money", manager.ezGetString("Select money From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("G_money", manager.ezGetString("Select G_money From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("B_money", manager.ezGetString("Select B_money From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("O_money", manager.ezGetString("Select O_money From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("S_money", manager.ezGetString("Select S_money From Rcproj Where Oid='"+oid_s+"'"));		
		session.setAttribute("budgetid1", manager.ezGetString("Select name From Rccode Where Oid='"+budgetid1+"'"));		
		session.setAttribute("unitname", manager.ezGetString("Select unitname From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("budgetid2", manager.ezGetString("Select budgetid2 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("favorunit", manager.ezGetString("Select favorunit From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("authorunit1", manager.ezGetString("Select authorunit1 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("authorunit2", manager.ezGetString("Select authorunit2 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("coopunit1", manager.ezGetString("Select coopunit1 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("coopunit2", manager.ezGetString("Select coopunit2 From Rcproj Where Oid='"+oid_s+"'"));
		/*
		session.setAttribute("FullTime", manager.ezGetString("Select FullTime From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("PartTime", manager.ezGetString("Select PartTime From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("G_trainee", manager.ezGetString("Select G_trainee From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("B_trainee", manager.ezGetString("Select B_trainee From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("O_trainee", manager.ezGetString("Select O_trainee From Rcproj Where Oid='"+oid_s+"'"));
		*/
		session.setAttribute("intor", manager.ezGetString("Select intor From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnIn", manager.ezGetString("Select name From Rccode Where Oid='"+turnIn+"'"));
		session.setAttribute("turnOut", manager.ezGetString("Select name From Rccode Where Oid='"+turnOut+"'"));
		session.setAttribute("turnIn_G", manager.ezGetString("Select turnIn_G From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnIn_B", manager.ezGetString("Select turnIn_B From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnIn_O", manager.ezGetString("Select turnIn_O From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnOut_G", manager.ezGetString("Select turnOut_G From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnOut_B", manager.ezGetString("Select turnOut_B From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnOut_O", manager.ezGetString("Select turnOut_O From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("budgetidState", manager.ezGetString("Select name From Rccode Where Oid='"+budgetidState+"'"));
		
		//setContentPage(request.getSession(false), "teacher/Rcproj_Query_View.jsp");
		return mapping.findForward("QueryRcproj");
	}
	
	protected Map<String, String> getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query", "Query");
		map.put("View", "View");
		map.put("Back", "Back");
		return map;
	}

}
