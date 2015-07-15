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

public class Rcact_ViewAction extends BaseLookupDispatchAction {
			
	/**
	 * 更新作業
	 */
	public ActionForward Update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
				
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "rcact");
		ActionMessages messages = new ActionMessages();
	
		String oids = aForm.getString("oid_s");
        String Table = "Rcact";
		
		manager.removeRcTableBy(Table, oids);
		
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
		String approve = aForm.getString("approve");
		String approveTemp = aForm.getString("approveTemp");
		
		Rcact rcact = new Rcact();
		
		if (actname.equals(null) || actname == "" || 
		    sponoff.equals(null) || sponoff == "" || 
		    kindid.equals(null) || kindid == "" || 
		    typeid.equals(null) || typeid == "" || 
		    placeid.equals(null) || placeid == "" || 
		    joinid.equals(null) || joinid == "" || 
		    bdate.equals(null) || bdate == "" || 
		    edate.equals(null) || edate == "" || 
		    hour.equals(null) || hour == "" || 
		    schspon.equals(null) || schspon == "" || 
		    certyn.equals(null) || certyn == "" || 
		    certno.equals(null) || certno == "") {
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "除了剛要簡述外,所有欄位不可空白 " , ","));
			saveMessages(request, messages);
			return mapping.findForward("Main");
			
		} else {
						
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
			rcact.setApprove(approve);                    //預設存入狀態=處理中  Rccode.Oid=96
			rcact.setApproveTemp(approveTemp);
			
			manager.updateObject(rcact);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "更新完成"));
			saveMessages(request, messages);
			
		}
		session.removeAttribute("RcactList");
		setContentPage(request.getSession(false), "teacher/Rcact.jsp");
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
		Toolket.resetCheckboxCookie(response, "rcact");		
	
		String oids = aForm.getString("oid_s");
		String Table = "Rcact";
		
		manager.removeRcTableBy(Table, oids);
		
		session.removeAttribute("RcactList");
		setContentPage(request.getSession(false), "teacher/Rcact.jsp");
		return mapping.findForward("Main");
		
	}
		
	/**
	 * 返回Rcact.jsp
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		
		setContentPage(request.getSession(false), "teacher/Rcact.jsp");
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
		
		String Kind = manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("kindid"));
		String Type = manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("typeid"));
		String Place = manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("placeid"));
		String Join = manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("joinid"));
		String certyn = aForm.getString("certyn") ;
		if(certyn.equals("Y")){
			certyn = "有";
		}else if (certyn.equals("N")){
			certyn = "無";
		}
		
		//session.setAttribute("idno", aForm.get("idno"));
		session.setAttribute("school_year", aForm.get("schoolYear"));
		session.setAttribute("actname", aForm.get("actname"));
		session.setAttribute("sponoff", aForm.get("sponoff"));
		session.setAttribute("kindid", Kind);
		session.setAttribute("typeid", Type);
		session.setAttribute("placeid", Place);
		session.setAttribute("joinid", Join);
		session.setAttribute("bdate", aForm.get("bdate"));
		session.setAttribute("edate", aForm.get("edate"));		
		session.setAttribute("hour", aForm.get("hour"));
		session.setAttribute("schspon", aForm.get("schspon"));
		session.setAttribute("certyn", certyn);
		session.setAttribute("certno", aForm.get("certno"));
		session.setAttribute("intor", aForm.get("intor"));
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "如果沒有反應,請檢查是否阻擋彈跳視窗"));
		saveMessages(request, messages);
		
		session.removeAttribute("RcprojList");
		setContentPage(request.getSession(false), "teacher/Rcact_Out.jsp");
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
