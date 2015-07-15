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

public class Rcjour_ViewAction extends BaseLookupDispatchAction {
	
	/**
	 * 更新作業
	 */
	public ActionForward Update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
				
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "rcjour");
		ActionMessages messages = new ActionMessages();
	
		String oids = aForm.getString("oid_s");
        String Table = "Rcjour";
		
		manager.removeRcTableBy(Table, oids);
		
		String idno = user.getMember().getAccount();
		String schoolYear = aForm.getString("schoolYear");
		String projno = aForm.getString("projno");
		String title = aForm.getString("title");
		String authorno = aForm.getString("authorno");
		String comAuthorno = aForm.getString("COM_authorno");
		String kindid = aForm.getString("kindid");
		String jname = aForm.getString("jname");
		String volume = aForm.getString("volume");
		String period = aForm.getString("period");
		String pmonth = aForm.getString("pmonth");
		String pyear = aForm.getString("pyear");
		String intor = aForm.getString("intor");
		String type = aForm.getString("type");
		String place = aForm.getString("place");
		String approve = aForm.getString("approve");
		String approveTemp = aForm.getString("approveTemp");
		
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
		rcjour.setApprove(approve);                    //預設存入狀態=處理中  Rccode.Oid=96
		rcjour.setApproveTemp(approveTemp);
		
        manager.updateObject(rcjour);
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "更新完成"));
		saveMessages(request, messages);
				
		session.removeAttribute("RcjourList");
		
		setContentPage(request.getSession(false), "teacher/Rcjour.jsp");
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
		Toolket.resetCheckboxCookie(response, "rcjour");		
	
		String oids = aForm.getString("oid_s");
		String Table = "Rcjour";
		
		manager.removeRcTableBy(Table, oids);
		
		session.removeAttribute("RcjourList");
		setContentPage(request.getSession(false), "teacher/Rcjour.jsp");
		return mapping.findForward("Main");
		
	}
		
	/**
	 * 返回
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		setContentPage(request.getSession(false), "teacher/Rcjour.jsp");
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
		session.setAttribute("kindid", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("kindid")));
		session.setAttribute("authorno", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("authorno")));
		session.setAttribute("COM_authorno", COM_authorno);
		session.setAttribute("jname", aForm.get("jname"));
		session.setAttribute("type",  manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("type")));
		session.setAttribute("place",  manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("place")));
		session.setAttribute("volume", aForm.get("volume"));
		session.setAttribute("period", aForm.get("period"));
		session.setAttribute("pyear", aForm.get("pyear"));
		session.setAttribute("pmonth", aForm.get("pmonth"));
		session.setAttribute("intor", aForm.get("intor"));
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "如果沒有反應,請檢查是否阻擋彈跳視窗"));
		saveMessages(request, messages);
		
		session.removeAttribute("RcjourList");
		setContentPage(request.getSession(false), "teacher/Rcjour_Out.jsp");
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
