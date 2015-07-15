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

import tw.edu.chit.model.Rcbook;
import tw.edu.chit.model.Rcconf;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class RcbookAction extends BaseLookupDispatchAction {
	
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
		
		session.setAttribute("school_Year", manager.getSchoolYear());      //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());   //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));  //取得作者順序
		session.setAttribute("language", manager.ezGetBy("Select Oid, name From Rccode Where type='12'"));  //取得使用語言
		session.setAttribute("type", manager.ezGetBy("Select Oid, name From Rccode Where type='17'"));  //取得專書類別

		setContentPage(request.getSession(false), "teacher/Rcbook.jsp");
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
		Rcbook rcbook = new Rcbook();
		
		String idno = user.getMember().getAccount();
		String schoolYear = aForm.getString("schoolYear");
		String projno = aForm.getString("projno");
		String type = aForm.getString("type");
		String title = aForm.getString("title");
		String authorno = aForm.getString("authorno");
		String comAuthorno = aForm.getString("COM_authorno");
		String language = aForm.getString("language");
		String pdate = aForm.getString("pdate");
		String publisher = aForm.getString("publisher");
		String isbn = aForm.getString("isbn");
		String intor = aForm.getString("intor");
		
		rcbook.setIdno(idno);
		rcbook.setSchoolYear(Short.parseShort(schoolYear));
		rcbook.setProjno(projno);
		rcbook.setType(type);
		rcbook.setTitle(title);
		rcbook.setAuthorno(authorno);
		rcbook.setComAuthorno(comAuthorno);
		rcbook.setLanguage(language);
		rcbook.setPdate(pdate);
		rcbook.setPublisher(publisher);
		rcbook.setIsbn(isbn);
		rcbook.setIntor(intor);
		rcbook.setLastModified(new Date());
		rcbook.setApprove("96");            //預設存入狀態=處理中  Rccode.Oid=96 
		
		manager.updateObject(rcbook);
			
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "新增完成"));
		saveMessages(request, messages);
		
		session.removeAttribute("RcbookList");
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
		
		
		String teacherName = user.getMember().getName();
		String teacherId = user.getMember().getAccount();
		String teacherUnit = user.getMember().getUnit2();
		Integer schoolYear = manager.getSchoolYear();
		String school_Year = aForm.getString("schoolYear");
		
		List<Rcbook> rcbook = manager.ezGetBy(
				" Select R.Oid, R.idno, R.school_year, R.projno, R.title, R.authorno, R.language, R.pdate, R.publisher," +
				       " R.isbn, R.intor, Rc.name"+
				" From Rcbook R, Rccode Rc" +
				" Where R.approve = Rc.Oid" +
				  " And R.idno = '"+ teacherId +"'"+
				  " And R.school_year Like ('%"+school_Year+"%')");
				
		
		session.setAttribute("RcbookList", rcbook);
		
		setContentPage(request.getSession(false), "teacher/Rcbook.jsp");
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
		//Toolket.resetCheckboxCookie(response, "rcbook");		
		
		String teacherId = user.getMember().getAccount();		
		String oids = Toolket.getSelectedIndexFromCookie(request, "rcbook");
		String oid_s = oids.substring(1, oids.length()-1);
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));  //取得作者順序
		session.setAttribute("language", manager.ezGetBy("Select Oid, name From Rccode Where type='12'"));  //取得使用語言
		session.setAttribute("type", manager.ezGetBy("Select Oid, name From Rccode Where type='17'"));  //取得專書類別
		
		session.setAttribute("oid_s", oid_s);
		session.setAttribute("idno", manager.ezGetString("Select idno From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("school_year", manager.ezGetString("Select school_year From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("projno", manager.ezGetString("Select projno From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("title", manager.ezGetString("Select title From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("type_v", manager.ezGetString("Select type From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("authorno_v", manager.ezGetString("Select authorno From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("COMauthorno", manager.ezGetString("Select COM_authorno From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("language_v", manager.ezGetString("Select language From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("pdate", manager.ezGetString("Select pdate From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("publisher", manager.ezGetString("Select publisher From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("isbn", manager.ezGetString("Select isbn From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("intor", manager.ezGetString("Select intor From Rcbook Where Oid='"+oid_s+"'"));
		session.setAttribute("approve", manager.ezGetString("Select approve From Rcbook Where Oid = '"+oid_s+"'"));
		session.setAttribute("approveTemp", manager.ezGetString("Select approveTemp From Rcbook Where Oid = '"+oid_s+"'"));
		
		setContentPage(request.getSession(false), "teacher/Rcbook_View.jsp");
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
