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

import tw.edu.chit.model.Rcconf;
import tw.edu.chit.model.Rcpet;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class Rcpet_QueryAction extends BaseLookupDispatchAction {
	
	/**
	 * 初始畫面資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "rcpet");		
		
		String teacherId = user.getMember().getAccount();		
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));  //取得作者順序
		session.setAttribute("area", manager.ezGetBy("Select Oid, name From Rccode Where type='13'"));  //取得專利區域
		session.setAttribute("petType", manager.ezGetBy("Select Oid, name From Rccode Where type='14'"));  //取得專利類型
		session.setAttribute("schedule", manager.ezGetBy("Select Oid, name From Rccode Where type='15'"));  //取得進度狀況
		session.setAttribute("depute", manager.ezGetBy("Select Oid, name From Rccode Where type='16'"));  //取得授權與否
		session.setAttribute("score", manager.ezGetBy("Select Oid, name From Rccode Where type='18'"));  //取得報告分數
		session.setAttribute("Tch_Unit", manager.ezGetBy("Select idno, name From CodeEmpl Where category='UnitTeach'")); // 取得系所

		setContentPage(request.getSession(false), "teacher/Rcpet_Query.jsp");
		return mapping.findForward("Main");
	}	
	
	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		Toolket.resetCheckboxCookie(response, "rcpet");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		
		String teacherId = aForm.getString("fsidno");
		String teacherUnit = user.getMember().getUnit2();
		Integer schoolYear = manager.getSchoolYear();
		String school_Year = aForm.getString("schoolYear");	
		
		String SelUnit = " And E.unit = '"+ aForm.getString("Tch_Unit") + "'";
		String SelYear = " And R.school_year Like ('%"+ school_Year +"%')";
		String SelTitle = " And R.title Like ('%"+aForm.getString("title") +"%')";
		String SelArea = " And R.area Like ('%"+ aForm.getString("area")+"%')";
		String SelType = " And R.petType Like ('%"+ aForm.getString("petType")+"%')";
		String SelScore = " And R.score Like ('%"+ aForm.getString("score")+"%')";
		String SelSch = " And R.schedule Like ('%"+ aForm.getString("schedule")+"%')";
		String SelAut = " And R.authorno Like ('%"+ aForm.getString("authorno") +"%')";
		String SelIntor = " And R.intor Like ('%"+ aForm.getString("intor") +"%')";
		String SelIdno = " And R.idno Like ('%"+ teacherId +"%')";
		
		if(aForm.getString("Tch_Unit").equals("")){
			SelUnit = "";
		} 
		if(school_Year.equals("")){
			SelYear = "";
		}
		if(aForm.getString("title").equals("")){
			SelTitle = "";
		}
		if(aForm.getString("area").equals("")){
			SelArea = "";
		}
		if(aForm.getString("petType").equals("")){
			SelType = "";
		}
		if(aForm.getString("score").equals("")){
			SelScore = "";
		}
		if(aForm.getString("schedule").equals("")){
			SelSch = "";
		}
		if(aForm.getString("authorno").equals("")){
			SelAut = "";
		}
		if(aForm.getString("intor").equals("")){
			SelIntor = "";
		}
		if(teacherId.equals("")){
			SelIdno = "";
		}
		
		if(aForm.getString("select_type").equals("U")){
			
			List<Rcpet> rcpet = manager.ezGetBy(
					" Select R.Oid Oid, R.school_year, CE.name Uname, E.cname idno, R.title, RCR.id area, RCP.id petType, RCS.id score, RCU.id schedule," +
					       " RCA.id authorno, R.proposer, R.bdate, R.edate, R.inst, R.certno, RCD.id depute, R.projno"+
                    " From Rcpet R, empl E, CodeEmpl CE, Rccode RCR, Rccode RCP, Rccode RCS, Rccode RCU, Rccode RCA, Rccode RCD " +
					" Where R.idno = E.idno " +
					  " And E.unit = CE.idno" +
					  " And R.area = RCR.Oid" +
					  " And R.petType = RCP.Oid" +
					  " And R.score = RCS.Oid" +
					  " And R.schedule = RCU.Oid" +
					  " And R.authorno = RCA.Oid" +
					  " And R.depute = RCD.Oid"+
					  SelUnit + SelYear + SelTitle + SelArea + SelType + SelScore + SelSch + SelAut + SelIntor
            );
			
			session.setAttribute("RcpetList", rcpet);
			
		}else{
			
			List<Rcpet> rcpet = manager.ezGetBy(
					" Select R.Oid Oid, R.school_year, CE.name Uname, E.cname idno, R.title, RCR.id area, RCP.id petType, RCS.id score, RCU.id schedule," +
				           " RCA.id authorno, R.proposer, R.bdate, R.edate, R.inst, R.certno, RCD.id depute, R.projno"+
                    " From Rcpet R, empl E, CodeEmpl CE, Rccode RCR, Rccode RCP, Rccode RCS, Rccode RCU, Rccode RCA, Rccode RCD " +
				    " Where R.idno = E.idno " +
				      " And E.unit = CE.idno" +
				      " And R.area = RCR.Oid" +
				      " And R.petType = RCP.Oid" +
				      " And R.score = RCS.Oid" +
				      " And R.schedule = RCU.Oid" +
				      " And R.authorno = RCA.Oid" +
				      " And R.depute = RCD.Oid"+
				      SelIdno + SelYear + SelTitle + SelArea + SelType + SelScore + SelSch + SelAut + SelIntor
            );
			
			session.setAttribute("RcpetList", rcpet);
			
		}
		
		setContentPage(request.getSession(false), "teacher/Rcpet_Query.jsp");
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
		Toolket.resetCheckboxCookie(response, "rcpet");				
		
		String teacherId = user.getMember().getAccount();		
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
		
		
		setContentPage(request.getSession(false), "teacher/Rcpet_Query_View.jsp");
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
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));  //取得作者順序
		session.setAttribute("area", manager.ezGetBy("Select Oid, name From Rccode Where type='13'"));  //取得專利區域
		session.setAttribute("petType", manager.ezGetBy("Select Oid, name From Rccode Where type='14'"));  //取得專利類型
		session.setAttribute("schedule", manager.ezGetBy("Select Oid, name From Rccode Where type='15'"));  //取得進度狀況
		session.setAttribute("depute", manager.ezGetBy("Select Oid, name From Rccode Where type='16'"));  //取得授權與否
		session.setAttribute("score", manager.ezGetBy("Select Oid, name From Rccode Where type='18'"));  //取得報告分數
		session.setAttribute("Tch_Unit", manager.ezGetBy("Select idno, name From CodeEmpl Where category='UnitTeach'")); // 取得系所

		setContentPage(request.getSession(false), "teacher/Rcpet_Query.jsp");
		return mapping.findForward("Main");

	}
	
	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		
		map.put("Query", "Query");
		map.put("View", "View");
		map.put("Back", "Back");
		
		return map;
	}

}
