package tw.edu.chit.struts.action.studaffair.racing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Desd;
import tw.edu.chit.model.RuleTran;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;
import tw.edu.chit.util.Global;

public class BonusPenaltySelAction  extends BaseLookupDispatchAction {

	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Cancel", "cancel");
		map.put("Query","query");
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
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		setContentPage(session, "studaffair/racing/BonusPenaltySel.jsp");
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

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);

		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");

		String startDate = aForm.getString("start_date");
		String endDate = aForm.getString("end_date");
		
		List<Desd> desdList = sm.findDesdByDateRange(startDate, endDate);
		
		session.setAttribute("DesdSelList", desdList);
		log.debug("DesdSelList:" + desdList.size());
		setContentPage(session, "studaffair/racing/BonusPenaltySel.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		List<Desd> desdList = (List<Desd>)session.getAttribute("DesdSelList");

		String[] sels = aForm.getStrings("actIllegal");
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");

		ActionMessages msgs = sm.updateDesd4Racing(desdList, sels);
		
		if(!msgs.isEmpty()){
			this.addErrors(request, msgs);
			session.setAttribute("DesdSelInEdit", aForm.getMap());
		}else{
			session.removeAttribute("DesdSelInEdit");
		}
		
		setContentPage(session, "studaffair/racing/BonusPenaltySel.jsp");
		return mapping.findForward("Main");

	}

}
