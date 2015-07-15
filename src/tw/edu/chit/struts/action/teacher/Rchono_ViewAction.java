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

import tw.edu.chit.model.Rcconf;
import tw.edu.chit.model.Rchono;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class Rchono_ViewAction extends BaseLookupDispatchAction {
	
	/**
	 * 更新作業
	 */
	public ActionForward Update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
				
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "rchono");
		ActionMessages messages = new ActionMessages();
	
		String oids = aForm.getString("oid_s");
        String Table = "Rchono";
		
		manager.removeRcTableBy(Table, oids);
		
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
		String approve = aForm.getString("approve");
		String approveTemp = aForm.getString("approveTemp");
		
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
		rchono.setApprove(approve);                    //預設存入狀態=處理中  Rccode.Oid=96
		rchono.setApproveTemp(approveTemp);
				
		manager.updateObject(rchono);
			
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "新增完成"));
		saveMessages(request, messages);			
		
		session.removeAttribute("RchonoList");
		setContentPage(request.getSession(false), "teacher/Rchono.jsp");
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
		Toolket.resetCheckboxCookie(response, "rchono");		
	
		String oids = aForm.getString("oid_s");
		String Table = "Rchono";
		
		manager.removeRcTableBy(Table, oids);
		
		session.removeAttribute("RchonoList");
		setContentPage(request.getSession(false), "teacher/Rchono.jsp");
		return mapping.findForward("Main");
		
	}
		
	/**
	 * 返回Rchono.jsp
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		setContentPage(request.getSession(false), "teacher/Rchono.jsp");
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
		
		//session.setAttribute("idno", aForm.get("idno"));
		session.setAttribute("school_year", aForm.get("schoolYear"));
		session.setAttribute("projno", aForm.get("projno"));
		session.setAttribute("title", aForm.get("title"));		
		session.setAttribute("authorno", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("authorno")));		
		session.setAttribute("nation", aForm.get("nation"));
		session.setAttribute("inst", aForm.get("inst"));
		session.setAttribute("bdate", aForm.get("bdate"));
		session.setAttribute("intor", aForm.get("intor"));
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "如果沒有反應,請檢查是否阻擋彈跳視窗"));
		saveMessages(request, messages);
		
		session.removeAttribute("RchonoList");
		setContentPage(request.getSession(false), "teacher/Rchono_Out.jsp");
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
