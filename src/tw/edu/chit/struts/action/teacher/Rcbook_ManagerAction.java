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

import tw.edu.chit.model.Rcbook;
import tw.edu.chit.model.Rcconf;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class Rcbook_ManagerAction extends BaseLookupDispatchAction {
	
	/**
	 * 初始畫面資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "rcbook");		
		
		String teacherId = user.getMember().getAccount();		
		
		session.setAttribute("school_Year", manager.getSchoolYear());            //取得學年度
		session.setAttribute("UserIdno", user.getMember().getAccount());         //取得登入者編號
		session.setAttribute("TeacherName", user.getMember().getName());         //取得登入者姓名
		session.setAttribute("UserUnit", user.getMember().getUnit2());           //取得登入者單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));				 //取得作者順序
		session.setAttribute("language", manager.ezGetBy("Select Oid, name From Rccode Where type='12'"));				 //取得使用語言
		session.setAttribute("type", manager.ezGetBy("Select Oid, name From Rccode Where type='17'"));					 //取得專書類別
		session.setAttribute("Tch_Unit", manager.ezGetBy("Select idno, name From CodeEmpl Where category='UnitTeach'")); // 取得系所
		session.setAttribute("approve", manager.ezGetBy("Select Oid, name From Rccode Where type='Approve'"));           // 取得審查狀態  

		setContentPage(request.getSession(false), "teacher/Rcbook_Manager.jsp");
		return mapping.findForward("Main");
	}	
	
	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		Toolket.resetCheckboxCookie(response, "rcbook");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		
		String teacherId = aForm.getString("fsidno");
		String teacherUnit = user.getMember().getUnit2();
		Integer schoolYear = manager.getSchoolYear();
		String school_Year = aForm.getString("schoolYear");
		
		String SelUnit = " And E.unit = '"+ aForm.getString("Tch_Unit")+ "'";
		if(aForm.getString("Tch_Unit").equals("")){
			SelUnit = "";
		}
		String SelYear = " And R.school_year Like ('%"+school_Year+"%')";
		if(school_Year.equals("")){
			SelYear = "";
		}
		String SelTitle = " And R.title Like ('%"+aForm.getString("title") +"%')";
		if(aForm.getString("title").equals("")){
			SelTitle = "";
		}
		String SelType = " And R.type Like ('%"+ aForm.getString("type") +"%')";
		if(aForm.getString("type").equals("")){
			SelType = "";
		}
		String SelAut = " And R.authorno Like ('%"+ aForm.getString("authorno") +"%')";
		if(aForm.getString("authorno").equals("")){
			SelAut = "";
		}
		String SelCaut = " And R.COM_authorno Like ('%"+ aForm.getString("COM_authorno") +"%')";
		if(aForm.getString("COM_authorno").equals("")){
			SelCaut = "";
		}
		String SelLang = " And R.language Like ('%"+ aForm.getString("language") +"%')";
		if(aForm.getString("language").equals("")){
			SelLang = "";
		}
		String SelPub = " And R.publisher Like ('%"+ aForm.getString("publisher") +"%')";
		if(aForm.getString("publisher").equals("")){
			SelPub = "";
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
		
		if(aForm.getString("select_type").equals("U")){
			
			List<Rcbook> rcbook = manager.ezGetBy(
					" Select R.Oid Oid, R.school_year, CE.name Uname, E.cname idno, RCT.id type, R.title, RCA.id authorno, R.COM_authorno," +
					       " RCL.id language, R.pdate, R.publisher, R.isbn, R.projno, RCa.name approve"+
                    " From Rcbook R, empl E, CodeEmpl CE, Rccode RCT, Rccode RCA, Rccode RCL, Rccode RCa " +
					" Where R.idno = E.idno And CE.category='UnitTeach' " +
				      " And E.unit = CE.idno" +
				      " And R.type = RCT.Oid" +
				      " And R.authorno = RCA.Oid" +
				      " And R.language = RCL.Oid" +
				      " And R.approve = RCa.Oid"+
				      SelUnit + SelYear + SelTitle + SelType + SelAut + SelCaut + SelLang + SelPub + SelIntor + SelApprove
					     
			);
			session.setAttribute("RcbookList", rcbook);
			
		}else{
			
			List<Rcbook> rcbook = manager.ezGetBy(
					" Select R.Oid Oid, R.school_year, CE.name Uname, E.cname idno, RCT.id type, R.title, RCA.id authorno, R.COM_authorno," +
				           " RCL.id language, R.pdate, R.publisher, R.isbn, R.projno, RCa.name approve"+
                    " From Rcbook R, empl E, CodeEmpl CE, Rccode RCT, Rccode RCA, Rccode RCL, Rccode RCa " +
			   		" Where R.idno = E.idno And CE.category='UnitTeach' " +
			          " And E.unit = CE.idno" +
			          " And R.type = RCT.Oid" +
			          " And R.authorno = RCA.Oid" +
			          " And R.language = RCL.Oid" +
			          " And R.approve = RCa.Oid"+
			          SelIdno + SelYear + SelTitle + SelType + SelAut + SelCaut + SelLang + SelPub + SelIntor + SelApprove
			);
			session.setAttribute("RcbookList", rcbook);
			
		}
		
		session.setAttribute("Approve",manager.ezGetString("Select name From Rccode Where Oid='"+ aForm.getString("approve") + "'"));
		setContentPage(request.getSession(false), "teacher/Rcbook_Manager.jsp");
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
		Toolket.resetCheckboxCookie(response, "rcbook");
		
		String teacherId = user.getMember().getAccount();		
		String oids = Toolket.getSelectedIndexFromCookie(request, "rcbook");
		String oid_s = oids.substring(1, oids.length()-1);
		
		String tchIdno = manager.ezGetString("Select idno From Rcbook Where Oid='" + oid_s + "'");
		String UnitIdno = manager.ezGetString("Select unit From empl Where idno='" + tchIdno+ "'");
		String type = manager.ezGetString("Select type From Rcbook Where Oid='"+oid_s+"'");
		String authorno = manager.ezGetString("Select authorno From Rcbook Where Oid='"+oid_s+"'");
		String language = manager.ezGetString("Select language From Rcbook Where Oid = '"+oid_s+"'");
		String COMauthorno = manager.ezGetString("Select COM_authorno From Rcbook Where Oid='"+oid_s+"'");
		if (COMauthorno.equals("Y")) {
			COMauthorno = "是";
		} else if (COMauthorno.equals("N")) {
			COMauthorno = "否";
		}
		
		session.setAttribute("oid_s", oid_s);
		session.setAttribute("school_year", manager.ezGetString("Select school_year From Rcbook Where Oid='"+ oid_s + "'"));
		session.setAttribute("TeacherName", manager.ezGetString("Select cname From empl Where idno='" + tchIdno+ "'"));
		session.setAttribute("TeacherUnit", manager.ezGetString("Select name From CodeEmpl Where category='UnitTeach' And idno='"+ UnitIdno + "'"));
		session.setAttribute("projno", manager.ezGetString("Select projno From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("title", manager.ezGetString("Select title From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("type", manager.ezGetString("Select name From Rccode Where Oid='"+type+"'"));
		session.setAttribute("authorno", manager.ezGetString("Select name From Rccode Where Oid='"+authorno+"'"));
		session.setAttribute("COM_authorno", COMauthorno);
		session.setAttribute("language", manager.ezGetString("Select name From Rccode Where Oid='"+language+"'"));
		session.setAttribute("pdate", manager.ezGetString("Select pdate From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("publisher", manager.ezGetString("Select publisher From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("isbn", manager.ezGetString("Select isbn From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("intor", manager.ezGetString("Select intor From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("approve_v", manager.ezGetString("Select approve From Rcbook Where Oid='" + oid_s+ "'"));
		session.setAttribute("approveTemp", manager.ezGetString("Select approveTemp From Rcbook Where Oid='" + oid_s+ "'"));
		
		setContentPage(request.getSession(false), "teacher/Rcbook_Manager_View.jsp");
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
		
		session.setAttribute("school_Year", manager.getSchoolYear());            //取得學年度
		session.setAttribute("UserIdno", user.getMember().getAccount());         //取得登入者編號
		session.setAttribute("TeacherName", user.getMember().getName());         //取得登入者姓名
		session.setAttribute("UserUnit", user.getMember().getUnit2());           //取得登入者單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));				 //取得作者順序
		session.setAttribute("language", manager.ezGetBy("Select Oid, name From Rccode Where type='12'"));				 //取得使用語言
		session.setAttribute("type", manager.ezGetBy("Select Oid, name From Rccode Where type='17'"));					 //取得專書類別
		session.setAttribute("Tch_Unit", manager.ezGetBy("Select idno, name From CodeEmpl Where category='UnitTeach'")); // 取得系所
		session.setAttribute("approve", manager.ezGetBy("Select Oid, name From Rccode Where type='Approve'"));           // 取得審查狀態  


		setContentPage(request.getSession(false), "teacher/Rcbook_Manager.jsp");
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
		Toolket.resetCheckboxCookie(response, "rcbook");		
	
		String oids = Toolket.getSelectedIndexFromCookie(request, "rcbook");
		String oid_s = oids.substring(1, oids.length() - 1);
		//String oids = aForm.getString("oid_s");
		String Table = "Rcbook";
		
		manager.removeRcTableBy(Table, oid_s);
		
		session.removeAttribute("RcbookList");
		setContentPage(request.getSession(false), "teacher/Rcbook_Manager.jsp");
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
		
		manager.executeSql("Update Rcbook " +
		           "Set approve='"+approve+"', approveTemp='"+approveTemp+"' " +
                   "Where Oid='"+Oid+"'");
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "完成"));

		session.setAttribute("school_Year", manager.getSchoolYear());            //取得學年度
		session.setAttribute("UserIdno", user.getMember().getAccount());         //取得登入者編號
		session.setAttribute("TeacherName", user.getMember().getName());         //取得登入者姓名
		session.setAttribute("UserUnit", user.getMember().getUnit2());           //取得登入者單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));				 //取得作者順序
		session.setAttribute("language", manager.ezGetBy("Select Oid, name From Rccode Where type='12'"));				 //取得使用語言
		session.setAttribute("type", manager.ezGetBy("Select Oid, name From Rccode Where type='17'"));					 //取得專書類別
		session.setAttribute("Tch_Unit", manager.ezGetBy("Select idno, name From CodeEmpl Where category='UnitTeach'")); // 取得系所
		session.setAttribute("approve", manager.ezGetBy("Select Oid, name From Rccode Where type='Approve'"));           // 取得審查狀態  

		setContentPage(request.getSession(false), "teacher/Rcbook_Manager.jsp");
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
