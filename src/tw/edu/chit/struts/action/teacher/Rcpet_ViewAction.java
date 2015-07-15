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
import tw.edu.chit.model.Rcpet;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class Rcpet_ViewAction extends BaseLookupDispatchAction {
	
	/**
	 * 更新作業
	 */
	public ActionForward Update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
				
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "rcpet");
		ActionMessages messages = new ActionMessages();
	
		String oids = aForm.getString("oid_s");
        String Table = "Rcpet";
		
		manager.removeRcTableBy(Table, oids);
		
		Rcpet rcpet = new Rcpet();
		
		String idno = user.getMember().getAccount();
		String schoolYear = aForm.getString("schoolYear");
		String projno = aForm.getString("projno");
		String title = aForm.getString("title");
		String authorno = aForm.getString("authorno");
		String area = aForm.getString("area");
		String petType = aForm.getString("petType");
		String schedule = aForm.getString("schedule");
		String bdate = aForm.getString("bdate");
		String edate = aForm.getString("edate");
		String inst = aForm.getString("inst");
		String certno = aForm.getString("certno");
		String depute = aForm.getString("depute");
		String intor = aForm.getString("intor");
		String score = aForm.getString("score");
		String proposer = aForm.getString("proposer");
		String proposerType = aForm.getString("proposerType");
		String deputeMoney = aForm.getString("deputeMoney");
		String deputeBusiness = aForm.getString("deputeBusiness");
		String deputeSdate = aForm.getString("deputeSdate");
		String deputeEdate = aForm.getString("deputeEdate");
		String approve = aForm.getString("approve");
		String approveTemp = aForm.getString("approveTemp");
		
		rcpet.setIdno(idno);
		rcpet.setSchoolYear(Short.parseShort(schoolYear));
		rcpet.setProjno(projno);
		rcpet.setTitle(title);
		rcpet.setAuthorno(authorno);
		rcpet.setArea(area);
		rcpet.setPetType(petType);
		rcpet.setSchedule(schedule);
		rcpet.setBdate(bdate);
		rcpet.setEdate(edate);
		rcpet.setInst(inst);
		rcpet.setCertno(certno);
		rcpet.setDepute(depute);
		rcpet.setIntor(intor);
		rcpet.setScore(score);
		rcpet.setProposer(proposer);
		rcpet.setProposerType(proposerType);
		rcpet.setDeputeMoney(deputeMoney);
		rcpet.setLastModified(new Date());
		rcpet.setApprove(approve);                    //預設存入狀態=處理中  Rccode.Oid=96
		rcpet.setApproveTemp(approveTemp);
		rcpet.setDeputeBusiness(deputeBusiness);         //99年新增欄位
		rcpet.setDeputeSdate(deputeSdate);               //99年新增欄位
		rcpet.setDeputeEdate(deputeEdate);               //99年新增欄位
				
		manager.updateObject(rcpet);
				
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "更新完成"));
		saveMessages(request, messages);
			
		session.removeAttribute("RcpetList");
		setContentPage(request.getSession(false), "teacher/Rcpet.jsp");
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
		Toolket.resetCheckboxCookie(response, "rcpet");		
	
		String oids = aForm.getString("oid_s");
		String Table = "Rcpet";
		
		manager.removeRcTableBy(Table, oids);
		
		session.removeAttribute("RcpetList");
		setContentPage(request.getSession(false), "teacher/Rcpet.jsp");
		return mapping.findForward("Main");
		
	}
		
	/**
	 * 返回Rcpet.jsp
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		setContentPage(request.getSession(false), "teacher/Rcpet.jsp");
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
		session.setAttribute("area", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("area")));
		session.setAttribute("petType", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("petType")));
		session.setAttribute("score", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("score")));
		session.setAttribute("schedule", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("schedule")));
		session.setAttribute("authorno", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("authorno")));		
		session.setAttribute("proposer", aForm.get("proposer"));
		session.setAttribute("proposerType", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("proposerType")));
		session.setAttribute("bdate", aForm.get("bdate"));
		session.setAttribute("edate", aForm.get("edate"));
		session.setAttribute("inst", aForm.get("inst"));
		session.setAttribute("certno", aForm.get("certno"));
		session.setAttribute("depute", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("depute")));
		session.setAttribute("deputeMoney", aForm.get("deputeMoney"));
		session.setAttribute("intor", aForm.get("intor"));
		session.setAttribute("deputeBusiness", aForm.get("deputeBusiness"));
		session.setAttribute("deputeSdate", aForm.get("deputeSdate"));
		session.setAttribute("deputeEdate", aForm.get("deputeEdate"));
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "如果沒有反應,請檢查是否阻擋彈跳視窗"));
		saveMessages(request, messages);
		
		session.removeAttribute("RcpetList");
		setContentPage(request.getSession(false), "teacher/Rcpet_Out.jsp");
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
