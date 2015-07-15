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

import tw.edu.chit.model.RuleTran;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class RuleTransmitAction  extends BaseLookupDispatchAction {

	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Cancel", "cancel");
		map.put("OK","modify");
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

		Map<String, String> StudTimeoffInit = new HashMap<String, String>();
		DynaActionForm dynForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		List<RuleTran> rtList = sm.findRuleTran();
		if(rtList.isEmpty()){
			rtList = sm.createRuleTran();
		}
		
		session.setAttribute("RuleTranList", rtList);
		setContentPage(session, "studaffair/racing/RuleTran.jsp");
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
		List<RuleTran> rtList = (List<RuleTran>)session.getAttribute("RuleTranList");

		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");

		String[] clazzes = aForm.getStrings("depart_class");
		String[] scores = aForm.getStrings("score");
		String[] mscores = aForm.getStrings("mscore");
		
		ActionMessages msgs = sm.updateRuleTrans(clazzes, scores, mscores);
		
		if(!msgs.isEmpty()){
			this.addErrors(request, msgs);
			session.setAttribute("RuleTranInEdit", aForm.getMap());
		}else{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "新增修改成功!"));
			this.saveMessages(session, msgs);
			session.removeAttribute("RuleTranInEdit");
		}
		
		rtList = sm.findRuleTran();
		session.setAttribute("RuleTranList", rtList);
		
		setContentPage(session, "studaffair/racing/RuleTran.jsp");
		return mapping.findForward("Main");

	}

}
