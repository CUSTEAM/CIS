package tw.edu.chit.struts.action.studaffair.racing;

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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Clean;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class CleanningAction  extends BaseLookupDispatchAction {

	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create","add");
		map.put("AddSubmit","addsubmit");
		map.put("Query","query");
		map.put("Cancel", "cancel");
		map.put("Modify","modify");
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

		DynaActionForm dynForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		session.removeAttribute("ClazzList");
		session.removeAttribute("CleanAddInEdit");
		session.removeAttribute("CleanList");
		session.removeAttribute("CleanInEdit");		
		
		setContentPage(session, "studaffair/racing/Cleanning.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		List<Clazz> cList = sm.findClass4Racing();
		
		session.setAttribute("ClazzList", cList);
		setContentPage(session, "studaffair/racing/CleanningAdd.jsp");
		return mapping.findForward("Main");

	}

	public ActionForward addsubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");

		String weekNo = aForm.getString("week_no");
		String[] clazzes = aForm.getStrings("depart_class");
		String[] scores = aForm.getStrings("score");
		
		ActionMessages msgs = this.validateInput(aForm);
		if(!msgs.isEmpty()){
			this.addErrors(request, msgs);
			session.setAttribute("CleanAddInEdit", aForm.getMap());
			setContentPage(session, "studaffair/racing/CleanningAdd.jsp");
			return mapping.findForward("Main");
		}else{
			msgs = sm.createCleans(weekNo, clazzes, scores);
			
			if(!msgs.isEmpty()){
				this.addErrors(request, msgs);
				session.setAttribute("CleanAddInEdit", aForm.getMap());
			}else{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "新增成功!"));
				this.saveMessages(session, msgs);
				session.removeAttribute("CleanAddInEdit");
			}
		}
				
		setContentPage(session, "studaffair/racing/Cleanning.jsp");
		return mapping.findForward("Main");

	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		String weekNo = aForm.getString("week_no");
		
		session.removeAttribute("CleanList");
		session.removeAttribute("CleanInEdit");

		List<Clean> clList = sm.findClean(weekNo);
		
		session.setAttribute("CleanList", clList);
		setContentPage(session, "studaffair/racing/Cleanning.jsp");
		return mapping.findForward("Main");

	}

	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		request.getSession(false).removeAttribute("RuleTranList");
		setContentPage(request.getSession(false), "/pages/Directory.jsp");
		return mapping.findForward("Main");
	}

	
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		List<Clean> clList = (List<Clean>)session.getAttribute("CleanList");

		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");

		String[] weekNos = aForm.getStrings("week_nos");
		String[] clazzes = aForm.getStrings("depart_class");
		String[] scores = aForm.getStrings("score");
		
		ActionMessages msgs = this.validateInput(aForm);
		if(!msgs.isEmpty()){
			this.addErrors(request, msgs);
			session.setAttribute("CleanInEdit", aForm.getMap());
		}else{
			msgs = sm.updateCleans(weekNos, clazzes, scores);
			
			if(!msgs.isEmpty()){
				this.addErrors(request, msgs);
				session.setAttribute("CleanInEdit", aForm.getMap());
			}else{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "修改成功!"));
				this.saveMessages(session, msgs);
				session.removeAttribute("CleanInEdit");
			}
		}
		
		clList = sm.findClean(weekNos[0]);
		session.setAttribute("CleanList", clList);
		
		setContentPage(session, "studaffair/racing/Cleanning.jsp");
		return mapping.findForward("Main");

	}

	private ActionMessages validateInput(DynaActionForm aForm){
		ActionMessages msgs = new ActionMessages();
		String[] scores = aForm.getStrings("score");
		
		for(int i=0; i<scores.length; i++){
			if(!scores[i].trim().equals("")){
				if(!Toolket.isNumeric(scores[i])){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "成績必須為數字!"));
					break;
				}else if(Float.parseFloat(scores[i])>100f){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "成績不得大於100分!"));
					break;
				}
			}
		}
		return msgs;
	}

}
