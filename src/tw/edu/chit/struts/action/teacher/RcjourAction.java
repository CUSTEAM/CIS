package tw.edu.chit.struts.action.teacher;

import java.util.Date;
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

import tw.edu.chit.model.Rcact;
import tw.edu.chit.model.Rcjour;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class RcjourAction extends BaseLookupDispatchAction {
	
	/**
	 * 初始界面資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "rcjour");		
		
		String teacherId = user.getMember().getAccount();		
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));  //取得作者順序
		session.setAttribute("kindid", manager.ezGetBy("Select Oid, name From Rccode Where type='11'"));    //取得收錄分類
		session.setAttribute("type",manager.ezGetBy("Select Oid, name From Rccode Where type='17'"));       //取得發表形式
		session.setAttribute("place",manager.ezGetBy("Select Oid, name From Rccode Where type='National'"));       //取得出刊地點

		setContentPage(request.getSession(false), "teacher/Rcjour.jsp");
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
		String projno = aForm.getString("projno");
		String title = aForm.getString("title");
		String authorno = aForm.getString("authorno");
		String comAuthorno = aForm.getString("COM_authorno");
		String kindid = aForm.getString("kindid");
		String jname = aForm.getString("jname");
		String type = aForm.getString("type");
		String volume = aForm.getString("volume");
		String period = aForm.getString("period");
		String pmonth = aForm.getString("pmonth");
		String pyear = aForm.getString("pyear");
		String intor = aForm.getString("intor");
		String place = aForm.getString("place");
		
		
		Rcjour rcjour = new Rcjour();		
		
		rcjour.setIdno(idno);
		rcjour.setSchoolYear(Short.parseShort(schoolYear));
		rcjour.setProjno(projno);
		rcjour.setTitle(title);
		rcjour.setAuthorno(authorno);
		rcjour.setComAuthorno(comAuthorno);
		rcjour.setKindid(kindid);
		rcjour.setJname(jname);
		rcjour.setVolume(volume);
		rcjour.setPeriod(period);
		rcjour.setPmonth(Short.parseShort(pmonth));
		rcjour.setPyear(Short.parseShort(pyear));
		rcjour.setIntor(intor);
		rcjour.setType(type);
		rcjour.setLastModified(new Date());
		rcjour.setPlace(place);
		rcjour.setApprove("96");            //預設存入狀態=處理中  Rccode.Oid=96 
		
		manager.updateObject(rcjour);
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "新增完成"));
		saveMessages(request, messages);
				
		session.removeAttribute("RcjourList");
		return mapping.findForward("Main");
	}
	
	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		Toolket.resetCheckboxCookie(response, "rcjour");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		
		
		String teacherName = user.getMember().getName();
		String teacherId = user.getMember().getAccount();
		String teacherUnit = user.getMember().getUnit2();
		Integer schoolYear = manager.getSchoolYear();
		String school_Year = aForm.getString("schoolYear");
		
		List<Rcjour> rcjour = manager.ezGetBy(
				" Select R.Oid, R.idno, R.school_year, R.projno, R.title, R.authorno, R.kindid, R.jname, R.volume," +
				      " R.period, R.pmonth, R.pyear, R.intor, R.COM_authorno, Rc.name"+
				" From Rcjour R, Rccode Rc" +
				" Where R.approve = Rc.Oid" +
				  " And idno = '"+ teacherId +"'"+
				  " And school_year Like ('%"+school_Year+"%')");
				
		
		session.setAttribute("RcjourList", rcjour);
		
		setContentPage(request.getSession(false), "teacher/Rcjour.jsp");
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
		String oids = Toolket.getSelectedIndexFromCookie(request, "rcjour");
		String oid_s = oids.substring(1, oids.length()-1);
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));  //取得作者順序
		session.setAttribute("kindid", manager.ezGetBy("Select Oid, name From Rccode Where type='11'"));    //取得收錄分類
		session.setAttribute("type",manager.ezGetBy("Select Oid, name From Rccode Where type='17'"));       //取得發表形式
		session.setAttribute("place",manager.ezGetBy("Select Oid, name From Rccode Where type='National'"));       //取得出刊地點
		
		session.setAttribute("oid_s", oid_s);
		session.setAttribute("idno", manager.ezGetString("Select idno From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("school_year", manager.ezGetString("Select school_year From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("projno", manager.ezGetString("Select projno From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("title", manager.ezGetString("Select title From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("authorno_v", manager.ezGetString("Select authorno From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("COMauthorno", manager.ezGetString("Select COM_authorno From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("kindid_v", manager.ezGetString("Select kindid From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("jname", manager.ezGetString("Select jname From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("volume", manager.ezGetString("Select volume From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("period", manager.ezGetString("Select period From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("pmonth", manager.ezGetString("Select pmonth From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("pyear", manager.ezGetString("Select pyear From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("intor", manager.ezGetString("Select intor From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("type_v", manager.ezGetString("Select type From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("place_v", manager.ezGetString("Select place From Rcjour Where Oid='"+oid_s+"'"));
		session.setAttribute("approve", manager.ezGetString("Select approve From Rcjour Where Oid = '"+oid_s+"'"));
		session.setAttribute("approveTemp", manager.ezGetString("Select approveTemp From Rcjour Where Oid = '"+oid_s+"'"));
		
		setContentPage(request.getSession(false), "teacher/Rcjour_View.jsp");
		return mapping.findForward("Main");
	}
	
	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("Create", "Save");
		map.put("Query", "Query");
		map.put("View", "View");
		return map;
	}

}
