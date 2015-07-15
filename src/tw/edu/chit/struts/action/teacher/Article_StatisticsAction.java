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

public class Article_StatisticsAction extends BaseLookupDispatchAction {

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
		session.setAttribute("Campus", manager.ezGetBy("Select idno, name From code5 Where category = 'Campus'"));         //取得校區
		session.setAttribute("Tch_Unit", manager.ezGetBy("Select idno, name From CodeEmpl Where category='UnitTeach'"));   // 取得系所
		session.setAttribute("myYear", manager.ezGetBy("Select school_year, school_year, count(Oid) From Rcact Group By school_year"));         //取得資料記載年度
		session.setAttribute("approve", manager.ezGetBy("Select Oid, name From Rccode Where type='Approve'"));             // 取得審查狀態

		setContentPage(request.getSession(false), "teacher/Article_Statistics.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;

		String teacherUnit = user.getMember().getUnit2();
		Integer schoolYear = manager.getSchoolYear();
		String Year =  aForm.getString("schoolYear");
		
		
		String SelCampus =  " And C5.idno = '" + aForm.getString("Campus") + "'";          //校區
		String SelUnit =    " And CE.idno = '" + aForm.getString("Tch_Unit") + "'";        //系所單位
		String SelTCHname = " and E.cname Like ('%"+ aForm.getString("TCH_Cname") +"%')";  //教師姓名		
		String SelApprove = " And R.approve = '"+ aForm.getString("approve") +"'";         //審查狀態
		
			
		if(aForm.getString("Campus").equals("")){
			SelCampus = "";
		}		
		if(aForm.getString("Tch_Unit").equals("")){
			SelUnit = "";
		}
		if(aForm.getString("TCH_Cname").equals("")){
			SelTCHname = "";
		}
		if(aForm.getString("approve").equals("")){
			SelApprove = "";
		}

		List articleList = manager.ezGetBy(
				" SELECT CE.name Tch_Unit, E.cname TCH_Cname, Ra.AA Rcact, Rp.AA Rcporj, Rj.AA Rcjour, Rc.AA Rcconf, Rb.AA Rcbook, Re.AA Rcpet, Rh.AA Rchono" +
				" FROM empl E " +
				" JOIN CodeEmpl CE On E.unit = CE.idno And CE.category='UnitTeach' " +
				//" JOIN Class C On CE.idno2 = C.DeptNo " +
				//" JOIN (SELECT idno, name FROM code5 WHERE category = 'Campus') C5 On C.CampusNo = C5.idno " +
				" LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rcact R  WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Ra On E.idno=Ra.ID " +
				" LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rcproj R WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Rp On E.idno=Rp.ID " +
				" LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rcjour R WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Rj On E.idno=Rj.ID " +
				" LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rcconf R WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Rc On E.idno=Rc.ID " +
				" LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rcbook R WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Rb On E.idno=Rb.ID " +
				" LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rcpet R  WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Re On E.idno=Re.ID " +
				" LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rchono R WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Rh On E.idno=Rh.ID " +
				" Where E.unit = CE.idno  And CE.category='UnitTeach' " + SelCampus + SelUnit + SelTCHname +
				" Group by CE.name, E.`cname`" +
				" Order by Ra.AA DESC, Rp.AA DESC, Rj.AA DESC, Rc.AA DESC, Rb.AA DESC, Re.AA DESC, Rh.AA DESC, CE.name"
		);
		session.setAttribute("ArticleList", articleList);
		
		List totalList = manager.ezGetBy(
				" Select sum(Rcact) Ra, sum(Rcporj) Rp, sum(Rcjour) Rj, sum(Rcconf) Rc, sum(Rcbook) Rb, sum(Rcpet) Re, sum(Rchono) Rh" +
				" From("+
				"   SELECT CE.name Tch_Unit, E.cname TCH_Cname, Ra.AA Rcact, Rp.AA Rcporj, Rj.AA Rcjour, Rc.AA Rcconf, Rb.AA Rcbook, Re.AA Rcpet, Rh.AA Rchono" +
				"   FROM empl E " +
				"   JOIN CodeEmpl CE On E.unit = CE.idno And CE.category='UnitTeach' " +
				//" JOIN Class C On CE.idno2 = C.DeptNo " +
				//" JOIN (SELECT idno, name FROM code5 WHERE category = 'Campus') C5 On C.CampusNo = C5.idno " +
				"   LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rcact R  WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Ra On E.idno=Ra.ID " +
				"   LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rcproj R WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Rp On E.idno=Rp.ID " +
				"   LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rcjour R WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Rj On E.idno=Rj.ID " +
				"   LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rcconf R WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Rc On E.idno=Rc.ID " +
				"   LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rcbook R WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Rb On E.idno=Rb.ID " +
				"   LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rcpet R  WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Re On E.idno=Re.ID " +
				"   LEFT OUTER JOIN (SELECT E.idno ID, R.school_year, COUNT(R.Oid) AA FROM empl E, Rchono R WHERE E.idno=R.idno and R.school_year='"+Year+"'"+SelApprove+" Group By E.cname, R.school_year) Rh On E.idno=Rh.ID " +
				"   Where E.unit = CE.idno And CE.category='UnitTeach' " + SelCampus + SelUnit + SelTCHname +
				"   Group by CE.name, E.`cname`" +
				" ) A"				        
		);
		session.setAttribute("total", totalList);				
		session.setAttribute("Year", Year);
		
		setContentPage(request.getSession(false), "teacher/Article_Statistics.jsp");
		return mapping.findForward("Main");
	}

	

	@Override
	protected Map<String, String> getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query", "Query");		
		return map;
	}

}
