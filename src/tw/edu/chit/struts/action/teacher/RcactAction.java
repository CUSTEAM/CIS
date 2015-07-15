package tw.edu.chit.struts.action.teacher;

import java.util.ArrayList;
import java.util.Date;
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

public class RcactAction extends BaseLookupDispatchAction {
	
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
		
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("kindid", manager.ezGetBy("Select Oid, name From Rccode Where type='2'"));  //取得活動種類
		session.setAttribute("typeid", manager.ezGetBy("Select Oid, name From Rccode Where type='3'"));  //取得活動類型
		session.setAttribute("placeid", manager.ezGetBy("Select Oid, name From Rccode Where type='4'"));  //取得活動地點
		session.setAttribute("joinid", manager.ezGetBy("Select Oid, name From Rccode Where type='5'"));  //取得參與情形

		setContentPage(request.getSession(false), "teacher/Rcact.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 新增資料
	 */
	public ActionForward Save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		
		String idno = user.getMember().getAccount();
		String schoolYear = aForm.getString("schoolYear");
		String actname = aForm.getString("actname");
		String sponoff = aForm.getString("sponoff");
		String kindid = aForm.getString("kindid");
		String typeid = aForm.getString("typeid");
		String placeid = aForm.getString("placeid");
		String joinid = aForm.getString("joinid");
		String bdate = aForm.getString("bdate");
		String edate = aForm.getString("edate");
		String hour = aForm.getString("hour");
		String schspon = aForm.getString("schspon");
		String certyn = aForm.getString("certyn");
		String certno = aForm.getString("certno");
		String intor = aForm.getString("intor");
		Date lastModified = new Date();
		//String category = manager.ezGetString("Select CE.category From empl E, CodeEmpl CE Where E.unit= CE.idno And E.idno='"+idno+"'");
		
		Rcact rcact = new Rcact();
		
		rcact.setIdno(idno);
		rcact.setSchoolYear(Short.parseShort(schoolYear));
		rcact.setActname(actname);
		rcact.setSponoff(sponoff);
		rcact.setKindid(kindid);
		rcact.setTypeid(typeid);
		rcact.setPlaceid(placeid);
		rcact.setJoinid(joinid);
		rcact.setBdate(bdate);
		rcact.setEdate(edate);
		rcact.setHour(hour);
		rcact.setCertno(certno);
		rcact.setSchspon(schspon);
		rcact.setCertyn(certyn);
		rcact.setIntor(intor);
		rcact.setLastModified(new Date());
		rcact.setApprove("96");            //預設存入狀態=處理中  Rccode.Oid=96 
		//rcact.setApproveTemp("");
		
		manager.updateObject(rcact);
		
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "新增完成"));
		saveMessages(request, messages);
		session.removeAttribute("RcactList");		
		return mapping.findForward("Main");	
	}
	
	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		Toolket.resetCheckboxCookie(response, "rcact");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		
		
		String teacherName = user.getMember().getName();
		String teacherId = user.getMember().getAccount();
		String teacherUnit = user.getMember().getUnit2();
		Integer schoolYear = manager.getSchoolYear();
		String school_Year = aForm.getString("schoolYear");
		
		List<Rcact> rcact = manager.ezGetBy(
				" Select R.Oid, R.idno, R.school_year, R.actname, R.sponoff, R.kindid, R.typeid, R.placeid, R.joinid," +
				       " R.bdate, R.edate, R.hour, R.certno, R.schspon, R.certyn, R.intor, Rc.name" +
				" From Rcact R, Rccode Rc" +
				" Where R.approve = Rc.Oid" +
				"   And R.idno = '"+ teacherId +"'"+
				"   And R.school_year Like ('%"+school_Year+"%')"
		);				
		
		session.setAttribute("RcactList", rcact);
		
		setContentPage(request.getSession(false), "teacher/Rcact.jsp");
		return mapping.findForward("Main");
	}	
	
	/**
	 * 檢視資料
	 */
	public ActionForward View(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		//Toolket.resetCheckboxCookie(response, "rcact");		
		
		String teacherId = user.getMember().getAccount();		
		String oids = Toolket.getSelectedIndexFromCookie(request, "rcact");
		String oid_s = oids.substring(1, oids.length()-1);
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("kindid", manager.ezGetBy("Select Oid, name From Rccode Where type='2'"));  //取得活動種類
		session.setAttribute("typeid", manager.ezGetBy("Select Oid, name From Rccode Where type='3'"));  //取得活動類型
		session.setAttribute("placeid", manager.ezGetBy("Select Oid, name From Rccode Where type='4'"));  //取得活動地點
		session.setAttribute("joinid", manager.ezGetBy("Select Oid, name From Rccode Where type='5'"));  //取得參與情形
		
		session.setAttribute("oid_s", oid_s);
		session.setAttribute("idno", manager.ezGetString("Select idno From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("school_year", manager.ezGetString("Select school_year From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("actname", manager.ezGetString("Select actname From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("sponoff", manager.ezGetString("Select sponoff From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("kindid_v", manager.ezGetString("Select kindid From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("typeid_v", manager.ezGetString("Select typeid From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("placeid_v", manager.ezGetString("Select placeid From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("joinid_v", manager.ezGetString("Select joinid From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("bdate", manager.ezGetString("Select bdate From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("edate", manager.ezGetString("Select edate From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("hour", manager.ezGetString("Select hour From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("certno", manager.ezGetString("Select certno From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("schspon", manager.ezGetString("Select schspon From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("certyn", manager.ezGetString("Select certyn From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("intor", manager.ezGetString("Select intor From Rcact Where Oid='"+oid_s+"'"));
		session.setAttribute("approve", manager.ezGetString("Select approve From Rcact Where Oid = '"+oid_s+"'"));
		session.setAttribute("approveTemp", manager.ezGetString("Select approveTemp From Rcact Where Oid = '"+oid_s+"'"));
		
		setContentPage(request.getSession(false), "teacher/Rcact_View.jsp");
		return mapping.findForward("Main");
	}
	
	
	@Override
	protected Map<String, String> getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create", "Save");
		map.put("Query", "Query");
		map.put("View", "View");
		return map;
	}

}
