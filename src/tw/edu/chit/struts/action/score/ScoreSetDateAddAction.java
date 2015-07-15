package tw.edu.chit.struts.action.score;

import java.util.Calendar;
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
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ScoreSetDateAddAction extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "save");
		map.put("Back", "cancel");
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

		Map StudBonusPenaltyInfo = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);

		String opmode = dynForm.getString("opmode");
		if(opmode.equals("ok")) return save(mapping,form,request,response);
		else if(opmode.equals("cancel"))  return cancel(mapping,form,request,response);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");

		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		setContentPage(session, "score/ScoreSetDateAdd.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		session.removeAttribute("ScoreSetDateEdit");
		//String level = session.getAttribute("ScoreSetDateLevel").toString();
		//session.setAttribute("ScoreSetDate", sm.findOptime(level));
		setContentPage(request.getSession(false), "score/ScoreSetDate.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward save(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		StudAffairManager sam = (StudAffairManager)getBean("studAffairManager");
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		DynaActionForm form = (DynaActionForm)aform;
		
		String classInCharge = credential.getClassInChargeSqlFilterSAF();

		ActionMessages messages = validateInput(form);

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			session.setAttribute("ScoreSetDateEdit", form.getMap());
			return mapping.findForward("Main");
		} else {
			try {
				String level = form.getString("level");
				String levelSel = form.getString("levelSel");
				String depart = form.getString("depart");
				String departSel = form.getString("departSel");
				String beginDate = form.getString("beginDate");
				String beginTime = form.getString("beginTime");
				String endDate = form.getString("endDate");
				String endTime = form.getString("endTime");
		  		
		  			
				messages = sm.createUploadDateByForm(form);
				if (!messages.isEmpty()) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, 
							new ActionMessage("Message.CreateFailure"));
					saveErrors(request, messages);
					session.setAttribute("ScoreSetDateEdit", form.getMap());
					return mapping.findForward("Main");
				}
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.CreateSuccessful"));
				saveMessages(request, messages);

				session.removeAttribute("ScoreSetDateEdit");
				return mapping.findForward("Main");
			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("ScoreSetDateEdit", form.getMap());
				return mapping.findForward("Main");
			}
		}
	}
	
	
	private boolean isValidDate(String vYear, String vMonth, String vDay) {
		if (vYear.equals("") || vMonth.equals("") || vDay.equals("")) {
			return false;
		} else if (!(vYear.equals("") || vMonth.equals("") || vDay.equals(""))) {

			int itfYear = Integer.parseInt(vYear.trim());
			int itfMonth = Integer.parseInt(vMonth.trim()) - 1;
			int itfDay = Integer.parseInt(vDay.trim());

			Calendar tfdate = Calendar.getInstance();
			tfdate.clear();
			tfdate.set(itfYear,itfMonth,itfDay, 0, 0, 0);

			if (tfdate.get(Calendar.YEAR) != itfYear
				|| tfdate.get(Calendar.MONTH) != itfMonth
				|| tfdate.get(Calendar.DAY_OF_MONTH) != itfDay) {

				return false;
			} else 
				return true;
		}
		return false;
	}
	
	private boolean isValidTime(String tTime){
		String[] sTime = tTime.split(":");
		
		if(sTime.length != 3){
			return false;
		}
		if(!Toolket.isNumeric(sTime[0])||!Toolket.isNumeric(sTime[1])||
				!Toolket.isNumeric(sTime[2])){
			return false;
		}
			
		int t = Integer.parseInt(sTime[0]);
		if(t<0 || t>24) return false;
		
		t= Integer.parseInt(sTime[1]);
		if(t<0||t>59) return false;
		
		t= Integer.parseInt(sTime[2]);
		if(t<0||t>59) return false;
		
		return true;
	}
	
	private ActionMessages validateInput(DynaActionForm form) {
		ActionMessages msgs = new ActionMessages();

		String level = form.getString("level");
		String levelSel = form.getString("levelSel");
		String depart = form.getString("depart");
		String departSel = form.getString("departSel");
		String beginDate = form.getString("beginDate");
		String beginTime = form.getString("beginTime");
		String endDate = form.getString("endDate");
		String endTime = form.getString("endTime");
		log.debug("depart:" + depart + ", departSel:" + departSel);
		if(!level.equals(levelSel)) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.ScoreUploadLevelErr"));
			return msgs;
		}
		
		if(!depart.equals(departSel)) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.DepartmentInputErr"));
			return msgs;
		}

		String[] tDate = beginDate.split("/");
		if(tDate.length != 3){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InvalidDateInput"));
			return msgs;
		}else{
			if(!Toolket.isNumeric(tDate[0])||!Toolket.isNumeric(tDate[1])||
					!Toolket.isNumeric(tDate[2])){
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.InvalidDateInput"));
				return msgs;
			}
			
			tDate[0] = "" + Integer.parseInt(tDate[0]) + 1911;
		}
		
		if(!isValidDate(tDate[0], tDate[1], tDate[2])) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InvalidDateInput"));
			return msgs;
		}
		
		tDate = endDate.split("/");
		if(tDate.length != 3){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InvalidDateInput"));
			return msgs;
		}else{
			if(!Toolket.isNumeric(tDate[0])||!Toolket.isNumeric(tDate[1])||
					!Toolket.isNumeric(tDate[2])){
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.InvalidDateInput"));
				return msgs;
			}
			
			tDate[0] = "" + Integer.parseInt(tDate[0]) + 1911;
		}
		
		if(!isValidDate(tDate[0], tDate[1], tDate[2])) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InvalidDateInput"));
			return msgs;
		}
		
		if(!isValidTime(beginTime)){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InvalidTimeInput"));
			return msgs;
		}
		
		if(!isValidTime(endTime)){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InvalidTimeInput"));
			return msgs;
		}
		
		return msgs;
	}

}
