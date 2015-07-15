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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Rcproj;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class Rcproj_QueryAction extends BaseLookupDispatchAction {
	
	/**
	 * 初始畫面取得
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "rcporj");
		
		String teacherId = user.getMember().getAccount();		
		
		//session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		//session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		//session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("kindid", manager.ezGetBy("Select Oid, name From Rccode " +
                                                       "Where type='6' And sequence = '0' Order By name"));                 //取得專案類型
		session.setAttribute("jobid", manager.ezGetBy("Select Oid, name From Rccode Where type='8'"));  //取得工作類別
		session.setAttribute("budgetid1", manager.ezGetBy("Select Oid, name From Rccode Where type='9'"));  //取得主要經濟來源
		session.setAttribute("Tch_Unit", manager.ezGetBy("Select idno, name From CodeEmpl Where category='UnitTeach'")); // 取得系所
		
		setContentPage(request.getSession(false), "teacher/Rcproj_Query.jsp");
		return mapping.findForward("Main");
	}	
	
	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		Toolket.resetCheckboxCookie(response, "rcporj");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		
		String teacherId = aForm.getString("fsidno");
		String teacherUnit = user.getMember().getUnit2();
		Integer schoolYear = manager.getSchoolYear();
		String school_Year = aForm.getString("schoolYear");	
		
		String SelUnit = " And E.unit = '"+ aForm.getString("Tch_Unit")+ "'";
		String SelYear = " And R.school_year Like ('%"+school_Year+"%')";
		String SelProjname = " And R.projname Like ('%"+aForm.getString("projname") +"%')";
		String SelKind = " And R.kindid Like ('%"+ aForm.getString("kindid") +"%')";
		String SelJob = " And R.jobid Like ('%"+ aForm.getString("jobid") +"%')";
		String SelBudg = " And R.budgetid1 Like ('%"+ aForm.getString("budgetid1") +"%')";
		String SelIntor = " And R.intor Like ('%"+ aForm.getString("intor") +"%')";
		String SelIdno = " And R.idno Like ('%"+ teacherId +"%')";
		
		if(aForm.getString("Tch_Unit").equals("")){
			SelUnit = "";
		} 
		if(school_Year.equals("")){
			SelYear = "";
		}
		if(aForm.getString("projname").equals("")){
			SelProjname = "";
		}
		if(aForm.getString("kindid").equals("")){
			SelKind = "";
		}
		if(aForm.getString("jobid").equals("")){
			SelJob = "";
		}
		if(aForm.getString("budgetid1").equals("")){
			SelBudg = "";
		}
		if(aForm.getString("intor").equals("")){
			SelIntor = "";
		}
		if(teacherId.equals("")){
			SelIdno = "";
		}	
		
		if(aForm.getString("select_type").equals("U")){				
			List<Rcproj> rcproj = manager.ezGetBy(
					" Select R.Oid Oid, R.school_year, CE.name Uname, E.cname idno, R.projno, R.projname, RCK.id kindid, R.bdate, R.edate, RCJ.id jobid, " +
					       " R.money, R.G_money, B_money, R.O_money, R.S_money, RCB.id budgetid1, R.unitname, R.budgetid2, R.favorunit, R.authorunit1, " +
					       " R.authorunit2, R.coopunit1, R.coopunit2, R.FullTime, R.PartTime, R.G_trainee, R.B_trainee, R.O_trainee "+
					" From Rcproj R, empl E, CodeEmpl CE, Rccode RCK, Rccode RCJ, Rccode RCB " +
					" Where R.idno = E.idno " +
					  " And E.unit = CE.idno" +
					  " And R.kindid = RCK.Oid" +
					  " And R.jobid = RCJ.Oid" +
					  " And R.budgetid1 = RCB.Oid"+
					  SelUnit + SelYear + SelProjname + SelKind + SelJob + SelBudg + SelIntor
			);						
			session.setAttribute("RcprojList", rcproj);			
		}else{
			List<Rcproj> rcproj = manager.ezGetBy(
					" Select R.Oid Oid, R.school_year, CE.name Uname, E.cname idno, R.projno, R.projname, RCK.id kindid, R.bdate, R.edate, RCJ.id jobid, " +
				           " R.money, R.G_money, B_money, R.O_money, R.S_money, RCB.id budgetid1, R.unitname, R.budgetid2, R.favorunit, R.authorunit1, " +
				           " R.authorunit2, R.coopunit1, R.coopunit2, R.FullTime, R.PartTime, R.G_trainee, R.B_trainee, R.O_trainee "+
				    " From Rcproj R, empl E, CodeEmpl CE, Rccode RCK, Rccode RCJ, Rccode RCB " +
				    " Where R.idno = E.idno " +
				      " And E.unit = CE.idno" +
				      " And R.kindid = RCK.Oid" +
				      " And R.jobid = RCJ.Oid" +
				      " And R.budgetid1 = RCB.Oid"+
				      SelIdno + SelYear + SelProjname + SelKind + SelJob + SelBudg + SelIntor
			);						
			session.setAttribute("RcprojList", rcproj);			
		}
		
		setContentPage(request.getSession(false), "teacher/Rcproj_Query.jsp");
		return mapping.findForward("Main");
	}	
	
	/**
	 * 檢視資料
	 */
	public ActionForward View(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		Toolket.resetCheckboxCookie(response, "rcproj");
		
		String teacherId = user.getMember().getAccount();
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
		String Transfer = manager.ezGetString("Select Transfer From Rcproj Where Oid='"+oid_s+"'");
		
		session.setAttribute("oid_s", oid_s);
		session.setAttribute("school_year", manager.ezGetString("Select school_year From Rcproj Where Oid='"+ oid_s + "'"));
		session.setAttribute("TeacherName", manager.ezGetString("Select cname From empl Where idno='" + tchIdno+ "'"));
		session.setAttribute("TeacherUnit", manager.ezGetString("Select name From CodeEmpl Where idno='"+ UnitIdno + "'"));	
		
		session.setAttribute("projno", manager.ezGetString("Select projno From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("projname", manager.ezGetString("Select projname From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("kind", kind);
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
		//------↓↓↓國科會資料區↓↓↓------------------------------------------------------------------------------------------------
		session.setAttribute("Transfer", manager.ezGetString("Select name From Rccode Where Oid='"+Transfer+"'"));
		session.setAttribute("Income", manager.ezGetString("Select Income From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("Storage", manager.ezGetString("Select Storage From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec1", manager.ezGetString("Select chec1 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec2", manager.ezGetString("Select chec2 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec3", manager.ezGetString("Select chec3 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec4", manager.ezGetString("Select chec4 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec5", manager.ezGetString("Select chec5 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec6", manager.ezGetString("Select chec6 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec1text", manager.ezGetString("Select chec1text From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec2text", manager.ezGetString("Select chec2text From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec3text", manager.ezGetString("Select chec3text From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec4text", manager.ezGetString("Select chec4text From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec5text", manager.ezGetString("Select chec5text From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec6text", manager.ezGetString("Select chec6text From Rcproj Where Oid='"+oid_s+"'"));
		//------↑↑↑國科會資料區↑↑↑------------------------------------------------------------------------------------------------
		
		setContentPage(request.getSession(false), "teacher/Rcproj_Query_View.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 返回
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Toolket.resetCheckboxCookie(response, "rcporj");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		
		String teacherId = user.getMember().getAccount();		
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("kindid", manager.ezGetBy("Select Oid, name From Rccode " +
                                                       "Where type='6' And sequence = '0' Order By name"));                 //取得專案類型
		session.setAttribute("jobid", manager.ezGetBy("Select Oid, name From Rccode Where type='8'"));  //取得工作類別
		session.setAttribute("budgetid1", manager.ezGetBy("Select Oid, name From Rccode Where type='9'"));  //取得主要經濟來源
		session.setAttribute("Tch_Unit", manager.ezGetBy("Select idno, name From CodeEmpl Where category='UnitTeach'")); // 取得系所
		
		setContentPage(request.getSession(false), "teacher/Rcproj_Query.jsp");
		return mapping.findForward("Main");

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
