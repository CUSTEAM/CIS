package tw.edu.chit.struts.action.teacher;

import java.util.Date;
import java.util.HashMap;
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

public class Rcbook_ViewAction extends BaseLookupDispatchAction {
	
	/**
	 * 更新作業
	 */
	public ActionForward Update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
				
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "rcbook");
		ActionMessages messages = new ActionMessages();
	
		String oids = aForm.getString("oid_s");
        String Table = "Rcbook";
		
		manager.removeRcTableBy(Table, oids);
		
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
		String approve = aForm.getString("approve");
		String approveTemp = aForm.getString("approveTemp");
		
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
		rcbook.setApprove(approve);                    //預設存入狀態=處理中  Rccode.Oid=96
		rcbook.setApproveTemp(approveTemp);
		
		manager.updateObject(rcbook);
			
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "更新完成"));
		saveMessages(request, messages);
			
		session.removeAttribute("RcbookList");
		setContentPage(request.getSession(false), "teacher/Rcbook.jsp");
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
	
		String oids = aForm.getString("oid_s");
		String Table = "Rcbook";
		
		manager.removeRcTableBy(Table, oids);
		
		session.removeAttribute("RcbookList");
		setContentPage(request.getSession(false), "teacher/Rcbook.jsp");
		return mapping.findForward("Main");
		
	}
		
	/**
	 * 返回Rcbook.jsp
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		setContentPage(request.getSession(false), "teacher/Rcbook.jsp");
		return mapping.findForward("Main");
		
	}
	
	/**
	 * 匯出/列印
	 */
	public ActionForward PrintOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		
		String COM_authorno = aForm.getString("COM_authorno");
		if (COM_authorno.equals("Y")){
			COM_authorno = "是";
		}else if (COM_authorno.equals("N")){
			COM_authorno = "否";
		}
		
		//session.setAttribute("idno", aForm.get("idno"));
		session.setAttribute("school_year", aForm.get("schoolYear"));
		session.setAttribute("projno", aForm.get("projno"));
		session.setAttribute("type", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("type")));
		session.setAttribute("title", aForm.get("title"));
		session.setAttribute("authorno", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("authorno")));
		session.setAttribute("COM_authorno", COM_authorno);
		session.setAttribute("language", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("language")));
		session.setAttribute("publisher", aForm.get("publisher"));
		session.setAttribute("pdate", aForm.get("pdate"));
		session.setAttribute("isbn", aForm.get("isbn"));
		session.setAttribute("intor", aForm.get("intor"));
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "如果沒有反應,請檢查是否阻擋彈跳視窗"));
		saveMessages(request, messages);
		
		session.removeAttribute("RcbookList");
		setContentPage(request.getSession(false), "teacher/Rcbook_Out.jsp");
		return mapping.findForward("Main");
		
	}
	
	@Override
	protected Map<String, String> getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("Update", "Update");
		map.put("Delete", "Delete");		
		map.put("Back", "Back");
		map.put("PrintOut", "PrintOut");
		
		return map;
	}

}
