package tw.edu.chit.struts.action.studaffair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Keep;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class StudInspectedModifyAction  extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("OK","save");
		map.put("Cancel", "cancel");
		return map;		
	}
	
	/**
	 * @comment Action預設之執行方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dForm = (DynaActionForm)form;
		String opmode = dForm.getString("opmode");
		if(opmode.equalsIgnoreCase("OK")){
			return save(mapping,form,request,response);
		}else if(opmode.equalsIgnoreCase("Cancel")){
			return cancel(mapping,form,request,response);
		}
		
		HttpSession session = request.getSession(false);		
		session.removeAttribute("StudInspectedInEdit");
		session.removeAttribute("StudInspectedModify");
			
		setContentPage(session, "studaffair/StudInspectedModify.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm dForm = (DynaActionForm)form;
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		
		String downYear  = dForm.getString("downYear");
		String downTerm  = dForm.getString("downTerm");
		Map InspMap = new HashMap();
		
		ActionMessages msgs = new ActionMessages();
		
		Keep keep = (Keep)session.getAttribute("StudInspectedInEdit");;
		String studentNo  = keep.getStudentNo();
		String studentName = keep.getStudentName();
		String departClass = keep.getDepartClass();
		String deptClassName = keep.getDeptClassName();
		

		if(downYear.trim().equals("")||downTerm.trim().equals("")){
			if(downYear.trim().equals("")) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "定察學年"));
			}
			if(downTerm.trim().equals("")) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "定察學期"));
			}
			this.saveErrors(request, msgs);
			
			InspMap.putAll(dForm.getMap());
			InspMap.put("studentName", studentName);
			InspMap.put("departClass", departClass);
			InspMap.put("deptClassName", deptClassName);
			
			session.setAttribute("StudInspectedModify", InspMap);
			setContentPage(session, "studaffair/StudInspectedModify.jsp");
			return mapping.findForward("Main");

		}
		
		msgs = sm.updateInspectedByForm(keep, dForm.getMap());
				
		if(!msgs.isEmpty()){
			saveErrors(request, msgs);
			
			InspMap.putAll(dForm.getMap());
			InspMap.put("studentName", studentName);
			InspMap.put("departClass", departClass);
			InspMap.put("deptClassName", deptClassName);
			
			session.setAttribute("StudInspectedModify", InspMap);
			setContentPage(session, "studaffair/StudInspectedModify.jsp");
		}else{
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.ModifySuccessful"));
			saveMessages(request, msgs);
			session.removeAttribute("StudInspectedInEdit");
			session.removeAttribute("StudInspectedModify");
			setContentPage(session, "studaffair/StudInspected.jsp");
		}
		return mapping.findForward("Main");

	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudInspectedInEdit");
		session.removeAttribute("StudInspectedModify");
		setContentPage(request.getSession(false), "studaffair/StudInspected.jsp");
		return mapping.findForward("Main");
	}

}
