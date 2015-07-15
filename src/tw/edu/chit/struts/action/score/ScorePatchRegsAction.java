package tw.edu.chit.struts.action.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ScorePatchRegsAction extends BaseLookupDispatchAction {
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK","patch");
		map.put("Cancel", "cancel");
		return map;		
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
			throws Exception {

			HttpSession session = request.getSession(false);
			DynaActionForm aForm = (DynaActionForm)form;
			
			String opmode = aForm.getString("opmode");
			//log.debug("StudConductEdit.opmode:" + opmode);
			if(opmode.equalsIgnoreCase("ok")) return patch(mapping,form,request,response);
			else if(opmode.equalsIgnoreCase("cancel"))  return cancel(mapping,form,request,response);
			
			
			session.setMaxInactiveInterval(-1);	//seconds , -1 : never session timeout
			setContentPage(session, "score/ScorePatchRegs.jsp");
			return mapping.findForward("Main");
	}

	

	public ActionForward patch(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		
		String sterm = aForm.getString("sterm");
		ActionMessages errors = new ActionMessages();
		
		if (sterm.equals("")) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.generic", "必須指定學期!"));
			saveErrors(request, errors);
			
			return mapping.findForward("Main");
		} else {
			try {
				ScoreManager sm = (ScoreManager)getBean("scoreManager");
				sm.setRunStatus("PatchAllRegs", "start!" ,0 ,0, 0d, "no");
				
				errors = sm.patchAllSeldRegs(sterm);
				if (!errors.isEmpty()) {
					
					saveErrors(request, errors);
					return mapping.findForward("Main");
				}
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.PatchSuccessful"));
				saveMessages(request, errors);
				aForm.initialize(mapping);
				
				return mapping.findForward("Main");	
			} catch(Exception e) {
				session.setMaxInactiveInterval(1800);	//1800 seconds = 30 min. , -1 : never session timeout
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward("Main");
			}
		}
	}

	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		setContentPage(request.getSession(false), "score/ScorePatchRegs.jsp");
		return mapping.findForward("Main");
	}

	
	
	//Private Method Here ============================>>

}
