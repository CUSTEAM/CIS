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

import tw.edu.chit.model.Rcconf;
import tw.edu.chit.model.Rchono;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class RchonoAction extends BaseLookupDispatchAction {
	
	/**
	 * 初始畫面資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "rchono");		
		
		String teacherId = user.getMember().getAccount();		
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));  //取得作者順序

		setContentPage(request.getSession(false), "teacher/Rchono.jsp");
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
		Rchono rchono = new Rchono();
		
		String idno = user.getMember().getAccount();
		String schoolYear = aForm.getString("schoolYear");
		String projno = aForm.getString("projno");
		String title = aForm.getString("title");
		String authorno = aForm.getString("authorno");
		String nation = aForm.getString("nation");
		String inst = aForm.getString("inst");
		String bdate = aForm.getString("bdate");
		String intor = aForm.getString("intor");
		
		rchono.setIdno(idno);
		rchono.setSchoolYear(Short.parseShort(schoolYear));
		rchono.setProjno(projno);
		rchono.setTitle(title);
		rchono.setAuthorno(authorno);
		rchono.setNation(nation);
		rchono.setInst(inst);
		rchono.setBdate(bdate);
		rchono.setIntor(intor);
		rchono.setLastModified(new Date());
		rchono.setApprove("96");            //預設存入狀態=處理中  Rccode.Oid=96 
				
		manager.updateObject(rchono);
			
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "新增完成"));
		saveMessages(request, messages);			
		
		session.removeAttribute("RchonoList");
		return mapping.findForward("Main");
	}
	
	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		Toolket.resetCheckboxCookie(response, "rchono");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		
		
		String teacherName = user.getMember().getName();
		String teacherId = user.getMember().getAccount();
		String teacherUnit = user.getMember().getUnit2();
		Integer schoolYear = manager.getSchoolYear();
		String school_Year = aForm.getString("schoolYear");
		
		List<Rchono> rchono = manager.ezGetBy(
				" Select R.Oid, R.idno, R.school_year, R.projno, R.title, R.authorno, R.nation, R.inst, R.bdate, R.intor, Rc.name"+
				" From Rchono R, Rccode Rc" +
				" Where R.approve = Rc.Oid" +
				  " And R.idno = '"+ teacherId +"'"+
				  " And school_year Like ('%"+school_Year+"%')");
				
		
		session.setAttribute("RchonoList", rchono);
		
		setContentPage(request.getSession(false), "teacher/Rchono.jsp");
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
		//Toolket.resetCheckboxCookie(response, "rchono");		
		
		String teacherId = user.getMember().getAccount();		
		String oids = Toolket.getSelectedIndexFromCookie(request, "rchono");
		String oid_s = oids.substring(1, oids.length()-1);
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));  //取得作者順序
		
		session.setAttribute("oid_s", oid_s);
		session.setAttribute("idno", manager.ezGetString("Select idno From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("school_year", manager.ezGetString("Select school_year From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("projno", manager.ezGetString("Select projno From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("title", manager.ezGetString("Select title From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("authorno_v", manager.ezGetString("Select authorno From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("nation", manager.ezGetString("Select nation From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("inst", manager.ezGetString("Select inst From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("bdate", manager.ezGetString("Select bdate From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("intor", manager.ezGetString("Select intor From Rchono Where Oid='"+oid_s+"'"));
		session.setAttribute("approve", manager.ezGetString("Select approve From Rchono Where Oid = '"+oid_s+"'"));
		session.setAttribute("approveTemp", manager.ezGetString("Select approveTemp From Rchono Where Oid = '"+oid_s+"'"));
		
		setContentPage(request.getSession(false), "teacher/Rchono_View.jsp");
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
