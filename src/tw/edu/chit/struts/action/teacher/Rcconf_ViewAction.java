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

import tw.edu.chit.model.Rcact;
import tw.edu.chit.model.Rcconf;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class Rcconf_ViewAction extends BaseLookupDispatchAction {
	
	/**
	 * 更新作業
	 */
	public ActionForward Update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
				
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "rcconf");
		ActionMessages messages = new ActionMessages();
	
		String oids = aForm.getString("oid_s");
        String Table = "Rcconf";
		
		manager.removeRcTableBy(Table, oids);
		
		String idno = user.getMember().getAccount();
		String schoolYear = aForm.getString("schoolYear");
		String projno = aForm.getString("projno");
		String title = aForm.getString("title");
		String authorno = aForm.getString("authorno");
		String comAuthorno = aForm.getString("COM_authorno");
		String jname = aForm.getString("jname");
		String nation = aForm.getString("nation");
		String city = aForm.getString("city");
		String bdate = aForm.getString("bdate");
		String edate = aForm.getString("edate");
		String pyear = aForm.getString("pyear");
		String intor = aForm.getString("intor");
		String approve = aForm.getString("approve");
		String approveTemp = aForm.getString("approveTemp");
		
		Rcconf rcconf = new Rcconf();
		
		rcconf.setIdno(idno);
		rcconf.setSchoolYear(Short.parseShort(schoolYear));
		rcconf.setProjno(projno);
		rcconf.setTitle(title);
		rcconf.setAuthorno(authorno);
		rcconf.setComAuthorno(comAuthorno);
		rcconf.setJname(jname);
		rcconf.setNation(nation);
		rcconf.setCity(city);
		rcconf.setBdate(bdate);
		rcconf.setEdate(edate);
		rcconf.setPyear(Short.parseShort(pyear));
		rcconf.setIntor(intor);
		rcconf.setLastModified(new Date());
		rcconf.setApprove(approve);                    //預設存入狀態=處理中  Rccode.Oid=96
		rcconf.setApproveTemp(approveTemp);
		
		manager.updateObject(rcconf);
			
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "更新完成"));
		saveMessages(request, messages);
			
		session.removeAttribute("RcconfList");
		setContentPage(request.getSession(false), "teacher/Rcconf.jsp");
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
		Toolket.resetCheckboxCookie(response, "rcconf");		
	
		String oids = aForm.getString("oid_s");
		String Table = "Rcconf";
		
		manager.removeRcTableBy(Table, oids);
		
		session.removeAttribute("RcconfList");
		setContentPage(request.getSession(false), "teacher/Rcconf.jsp");
		return mapping.findForward("Main");
		
	}
		
	/**
	 * 返回Rcconf.jsp
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		setContentPage(request.getSession(false), "teacher/Rcconf.jsp");
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
		session.setAttribute("title", aForm.get("title"));
		session.setAttribute("authorno", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("authorno")));
		session.setAttribute("COM_authorno", COM_authorno);
		session.setAttribute("jname", aForm.get("jname"));
		session.setAttribute("nation", aForm.get("nation"));
		session.setAttribute("city", aForm.get("city"));
		session.setAttribute("bdate", aForm.get("bdate"));
		session.setAttribute("edate", aForm.get("edate"));
		session.setAttribute("pyear", aForm.get("pyear"));
		session.setAttribute("intor", aForm.get("intor"));
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "如果沒有反應,請檢查是否阻擋彈跳視窗"));
		saveMessages(request, messages);
		
		session.removeAttribute("RcjourList");
		setContentPage(request.getSession(false), "teacher/Rcconf_Out.jsp");
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
