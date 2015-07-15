package tw.edu.chit.struts.action.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.model.Rcact;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.StdAbility;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class Rcact_ManagerAction extends BaseLookupDispatchAction {

	/**
	 * 初始畫面取得
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "rcact");

		String teacherId = user.getMember().getAccount();

		session.setAttribute("school_Year", manager.getSchoolYear());            // 取得學年度
		session.setAttribute("UserIdno", user.getMember().getAccount());         // 取得登入者編號
		session.setAttribute("UserName", user.getMember().getName());            // 取得登入者姓名
		session.setAttribute("UserUnit", user.getMember().getUnit2());           // 取得登入者單位
		session.setAttribute("kindid", manager.ezGetBy("Select Oid, name From Rccode Where type='2'"));                    // 取得活動種類
		session.setAttribute("typeid", manager.ezGetBy("Select Oid, name From Rccode Where type='3'"));                    // 取得活動類型
		session.setAttribute("placeid", manager.ezGetBy("Select Oid, name From Rccode Where type='4'"));                   // 取得活動地點
		session.setAttribute("joinid", manager.ezGetBy("Select Oid, name From Rccode Where type='5'"));                    // 取得參與情形
		session.setAttribute("Tch_Unit", manager.ezGetBy(
				"Select idno, name From CodeEmpl " +
		        "Where category in ('UnitTeach','Unit') Order by category Desc, idno"));                                   // 取得單位名稱
		session.setAttribute("approve", manager.ezGetBy("Select Oid, name From Rccode Where type='Approve'"));             // 取得審查狀態  

		setContentPage(request.getSession(false), "teacher/Rcact_Manager.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Toolket.resetCheckboxCookie(response, "rcact");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;

		String teacherId = aForm.getString("fsidno");
		String teacherUnit = user.getMember().getUnit2();
		Integer schoolYear = manager.getSchoolYear();
		String school_Year = aForm.getString("schoolYear");
		String category = aForm.getString("select_type");
		
		String SelUnit = " And E.unit = '"+ aForm.getString("Tch_Unit") + "'";
		if(aForm.getString("Tch_Unit").equals("")){
			SelUnit = "";
		}
		String SelYear = " And R.school_year Like ('%"+ school_Year +"%')";
		if(school_Year.equals("")){
			SelYear = "";
		}
		String SelActname = " And R.actname Like ('%"+ aForm.getString("actname") +"%')";
		if(aForm.getString("actname").equals("")){
			SelActname = "";
		}
		String SelSpo = " And R.sponoff Like ('%"+ aForm.getString("sponoff") +"%')";
		if(aForm.getString("sponoff").equals("")){
			SelSpo = "";
		}
		String SelKind = " and R.kindid Like ('%"+ aForm.getString("kindid") +"%')";
		if(aForm.getString("kindid").equals("")){
			SelKind = "";
		}
		String SelType = " and R.typeid Like ('%"+ aForm.getString("typeid") +"%')";
		if(aForm.getString("typeid").equals("")){
			SelType = "";
		}
		String SelPlace = " and R.placeid Like ('%"+ aForm.getString("placeid") +"%')";
		if(aForm.getString("placeid").equals("")){
			SelPlace = "";
		}
		String SelJoin = " and R.joinid Like ('%"+ aForm.getString("joinid") +"%')";
		if(aForm.getString("joinid").equals("")){
			SelJoin = "";
		}
		String SelIntor = " And R.intor Like ('%"+ aForm.getString("intor") +"%')";
		if(aForm.getString("intor").equals("")){
			SelIntor = "";
		}
		String SelIdno = " And R.idno Like ('%"+ teacherId +"%')";
		if(teacherId.equals("")){
			SelIdno = "";
		}
		String SelApprove = " And R.approve Like ('%"+aForm.getString("approve")+"%')";
		if(aForm.getString("approve").equals("")){
			SelApprove = "";
		}
		String SelCategory = " And CE.category = '"+ category +"'";
		if(category.equals("")||category.equals("U")||category.equals("N")){
			SelCategory = "";
		}

		
		if(aForm.getString("select_type").equals("U")){
			List<Rcact> rcact = manager.ezGetBy(
					" Select R.Oid Oid, R.school_year, CE.name Uname, E.cname idno, R.actname, R.sponoff, RCK.id kindid, RCT.id typeid," +
					       " RCP.id placeid, RCJ.id joinid, R.bdate, R.edate, R.hour, R.certyn, R.certno, R.schspon, RCA.name approve"+ 
					" From Rcact R, empl E, CodeEmpl CE, Rccode RCK, Rccode RCT, Rccode RCP, Rccode RCJ, Rccode RCA"+ 
					" Where R.idno = E.idno And CE.category in ('UnitTeach','Unit') "+
					  " And E.unit = CE.idno"+ 
					  " And R.kindid = RCK.Oid"+
					  " And R.typeid = RCT.Oid"+
					  " And R.placeid = RCP.Oid"+
					  " And R.joinid = RCJ.Oid" +
					  " And R.approve = RCA.Oid"+
					  SelUnit + SelYear + SelActname + SelSpo + SelKind + SelType + SelPlace + SelJoin + SelIntor + SelApprove
			);
			session.setAttribute("RcactList", rcact);						
		} else if(aForm.getString("select_type").equals("N")){
			List<Rcact> rcact = manager.ezGetBy(
					" Select R.Oid Oid, R.school_year, CE.name Uname, E.cname idno, R.actname, R.sponoff, RCK.id kindid, RCT.id typeid," +
					       " RCP.id placeid, RCJ.id joinid, R.bdate, R.edate, R.hour, R.certyn, R.certno, R.schspon, RCA.name approve"+ 
				    " From Rcact R, empl E, CodeEmpl CE, Rccode RCK, Rccode RCT, Rccode RCP, Rccode RCJ, Rccode RCA"+ 
				    " Where R.idno = E.idno And CE.category in ('UnitTeach','Unit') "+
				      " And E.unit = CE.idno"+ 
				      " And R.kindid = RCK.Oid"+
				      " And R.typeid = RCT.Oid"+
				      " And R.placeid = RCP.Oid" +
				      " And R.approve = RCA.Oid"+
				      " And R.joinid = RCJ.Oid"+
				      SelIdno + SelYear + SelActname + SelSpo + SelKind + SelType + SelPlace + SelJoin + SelIntor + SelApprove
			);
			session.setAttribute("RcactList", rcact);			
		}else {
			List<Rcact> rcact = manager.ezGetBy(
					" Select R.Oid Oid, R.school_year, CE.name Uname, E.cname idno, R.actname, R.sponoff, RCK.id kindid, RCT.id typeid," +
					       " RCP.id placeid, RCJ.id joinid, R.bdate, R.edate, R.hour, R.certyn, R.certno, R.schspon"+ 
				    " From Rcact R, empl E, CodeEmpl CE, Rccode RCK, Rccode RCT, Rccode RCP, Rccode RCJ"+ 
				    " Where R.idno = E.idno And CE.category in ('UnitTeach','Unit') "+
				      " And E.unit = CE.idno"+ 
				      " And R.kindid = RCK.Oid"+
				      " And R.typeid = RCT.Oid"+
				      " And R.placeid = RCP.Oid"+
				      " And R.joinid = RCJ.Oid"+
				      SelCategory + SelYear + SelActname + SelSpo + SelKind + SelType + SelPlace + SelJoin + SelIntor
			);
			session.setAttribute("RcactList", rcact);			
		}
        
		session.setAttribute("kind",manager.ezGetString("Select name From Rccode Where Oid='"+ aForm.getString("kindid") + "'"));
		session.setAttribute("type",manager.ezGetString("Select name From Rccode Where Oid='"+ aForm.getString("typeid") + "'"));
		session.setAttribute("place",manager.ezGetString("Select name From Rccode Where Oid='"+ aForm.getString("placeid") + "'"));
		session.setAttribute("join",manager.ezGetString("Select name From Rccode Where Oid='"+ aForm.getString("joinid") + "'"));
		session.setAttribute("Approve",manager.ezGetString("Select name From Rccode Where Oid='"+ aForm.getString("approve") + "'"));
		
		
		setContentPage(request.getSession(false), "teacher/Rcact_Manager.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 檢視資料
	 */
	public ActionForward View(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();

		String teacherId = user.getMember().getAccount();
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
		session.setAttribute("approve_v", manager.ezGetString("Select approve From Rcact Where Oid='" + oid_s+ "'"));
		session.setAttribute("approveTemp", manager.ezGetString("Select approveTemp From Rcact Where Oid='" + oid_s+ "'"));

		setContentPage(request.getSession(false),"teacher/Rcact_Manager_View.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 返回
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "rcact");

		String teacherId = user.getMember().getAccount();

		session.setAttribute("school_Year", manager.getSchoolYear()); // 取得學年度
		session.setAttribute("TeacherName", user.getMember().getName()); // 取得教師姓名
		session.setAttribute("UserUnit", user.getMember().getUnit2());           // 取得登入者單位
		session.setAttribute("kindid", manager.ezGetBy("Select Oid, name From Rccode Where type='2'")); // 取得活動種類
		session.setAttribute("typeid", manager.ezGetBy("Select Oid, name From Rccode Where type='3'")); // 取得活動類型
		session.setAttribute("placeid", manager.ezGetBy("Select Oid, name From Rccode Where type='4'")); // 取得活動地點
		session.setAttribute("joinid", manager.ezGetBy("Select Oid, name From Rccode Where type='5'")); // 取得參與情形
		session.setAttribute("approve", manager.ezGetBy("Select Oid, name From Rccode Where type='Approve'"));             // 取得審查狀態 

		setContentPage(request.getSession(false), "teacher/Rcact_Manager.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 刪除刪除
	 */
	public ActionForward Delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
				
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "rcact");		
	
		String oids = Toolket.getSelectedIndexFromCookie(request, "rcact");
		String oid_s = oids.substring(1, oids.length() - 1);
		//String oids = aForm.getString("oid_s");
		String Table = "Rcact";
		
		manager.removeRcTableBy(Table, oid_s);
		
		session.removeAttribute("RcactList");
		setContentPage(request.getSession(false), "teacher/Rcact_Manager.jsp");
		return mapping.findForward("Main");
		
	}
	
	/**
	 * 存檔
	 */
	public ActionForward Save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
				
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		Toolket.resetCheckboxCookie(response, "rcact");

		String teacherId = user.getMember().getAccount();
		String Oid = aForm.getString("oid_s");
		String approve = aForm.getString("approve");
		String approveTemp = aForm.getString("approveTemp");		
		
		manager.executeSql("Update Rcact " +
		           "Set approve='"+approve+"', approveTemp='"+approveTemp+"' " +
                   "Where Oid='"+Oid+"'");
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "完成"));

		session.setAttribute("school_Year", manager.getSchoolYear()); // 取得學年度
		session.setAttribute("TeacherName", user.getMember().getName()); // 取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2()); // 取得教師單位
		session.setAttribute("kindid", manager.ezGetBy("Select Oid, name From Rccode Where type='2'")); // 取得活動種類
		session.setAttribute("typeid", manager.ezGetBy("Select Oid, name From Rccode Where type='3'")); // 取得活動類型
		session.setAttribute("placeid", manager.ezGetBy("Select Oid, name From Rccode Where type='4'")); // 取得活動地點
		session.setAttribute("joinid", manager.ezGetBy("Select Oid, name From Rccode Where type='5'")); // 取得參與情形
		session.setAttribute("approve", manager.ezGetBy("Select Oid, name From Rccode Where type='Approve'"));             // 取得審查狀態 

		setContentPage(request.getSession(false), "teacher/Rcact_Manager.jsp");
		return mapping.findForward("Main");
		
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("Query", "Query");
		map.put("View", "View");
		map.put("Back", "Back");
		map.put("Delete", "Delete");
		map.put("Save", "Save");
		
		return map;
	}

}
