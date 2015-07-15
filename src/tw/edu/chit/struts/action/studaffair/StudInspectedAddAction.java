package tw.edu.chit.struts.action.studaffair;

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

import tw.edu.chit.model.Student;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class StudInspectedAddAction extends BaseLookupDispatchAction{
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
			
		setContentPage(session, "studaffair/StudInspectedAdd.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm dForm = (DynaActionForm)form;
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		
		String studentNo  = dForm.getString("studentNo");
		String downYear  = dForm.getString("downYear");
		String downTerm  = dForm.getString("downTerm");
		String studentName = "";
		String departClass = "";
		String deptClassName = "";
		Map InspMap = new HashMap();
		
		ActionMessages msgs = new ActionMessages();
		
		if(studentNo.trim().equals("")|| downYear.trim().equals("")||downTerm.trim().equals("")){
			if(studentNo.trim().equals("")) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "學號"));
			}else{
				Student student = scm.findStudentByStudentNo(studentNo);
				if(student!=null){
					studentName = student.getStudentName();
					departClass = student.getDepartClass();
					deptClassName = Toolket.getClassFullName(student.getDepartClass());
				}
			}
			if(downYear.trim().equals("")) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "定察學年"));
			}else if(!Toolket.isNumeric(downYear)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.InputNumericOnly", "->定察學年"));				
			}
			if(downTerm.trim().equals("")) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "定察學期"));
			}else if(!Toolket.isNumeric(downTerm)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.InputNumericOnly", "->定察學期"));				
			}
			
			InspMap.putAll(dForm.getMap());
			InspMap.put("studentName", studentName);
			InspMap.put("departClass", departClass);
			InspMap.put("deptClassName", deptClassName);
			
			saveErrors(request, msgs);
			session.setAttribute("StudInspectedEdit", InspMap);
			setContentPage(session, "studaffair/StudInspectedAdd.jsp");
			return mapping.findForward("Main");

		}
		
		msgs = sm.createInspectedByForm(dForm.getMap());
		if(!msgs.isEmpty()){
			saveErrors(request, msgs);
			
			Student student = scm.findStudentByStudentNo(studentNo);
			if(student!=null){
				studentName = student.getStudentName();
				departClass = student.getDepartClass();
				deptClassName = Toolket.getClassFullName(student.getDepartClass());
			}
			
			InspMap.putAll(dForm.getMap());
			InspMap.put("studentName", studentName);
			InspMap.put("departClass", departClass);
			InspMap.put("deptClassName", deptClassName);
			
			session.setAttribute("StudInspectedEdit", InspMap);
			setContentPage(session, "studaffair/StudInspectedAdd.jsp");
			return mapping.findForward("Main");
		}else{
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.CreateSuccessful"));
			saveMessages(request, msgs);
			session.removeAttribute("StudInspectedEdit");
			setContentPage(session, "studaffair/StudInspectedAdd.jsp");			
		}
		return mapping.findForward("Main");

	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudInspectedEdit");
		setContentPage(request.getSession(false), "studaffair/StudInspected.jsp");
		return mapping.findForward("Main");
	}

}
