package tw.edu.chit.struts.action.score;

import java.util.ArrayList;
import java.util.Calendar;
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

import tw.edu.chit.model.Optime1;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ScoreSetDateModifyAction  extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "save");
		//map.put("Back", "cancel");
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
		
		setContentPage(session, "score/ScoreSetDateModify.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("ScoreSetDateModify");
		session.removeAttribute("ScoreSetDateInEdit");
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
  		List<Optime1> inEditList = (List<Optime1>)session.getAttribute("ScoreSetDateInEdit"); 	

		ActionMessages messages = validateInput(form);

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			session.setAttribute("ScoreSetDateModify", form2properties(inEditList,form));
			return mapping.findForward("Main");
		} else {
			try {
				messages = sm.modifyUploadDateByForm(inEditList, form);
				if (!messages.isEmpty()) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, 
							new ActionMessage("Message.CreateFailure"));
					saveErrors(request, messages);
					session.setAttribute("ScoreSetDateModify", form2properties(inEditList,form));
					return mapping.findForward("Main");
				}
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.ModifySuccessful"));
				saveMessages(request, messages);

				session.removeAttribute("ScoreSetDateModify");
				session.removeAttribute("ScoreSetDateInEdit");
				setContentPage(request.getSession(false), "score/ScoreSetDate.jsp");
				return mapping.findForward("Main");
			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("ScoreSetDateModify", form2properties(inEditList,form));
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
		int hour = t;
		
		t= Integer.parseInt(sTime[1]);
		if(t<0||t>59) return false;
		else if(hour==24 && t>0) return false;
		
		t= Integer.parseInt(sTime[2]);
		if(t<0||t>59) return false;
		else if(hour==24 && t>0) return false;
		
		return true;
	}
	
	private ActionMessages validateInput(DynaActionForm form) {
		ActionMessages msgs = new ActionMessages();

		int i = 0;
		String[] beginDate = form.getStrings("beginDate");
		String[] beginTime = form.getStrings("beginTime");
		String[] endDate = form.getStrings("endDate");
		String[] endTime = form.getStrings("endTime");

		for(i=0; i<beginDate.length; i++){
			String[] tDate = beginDate[i].split("/");
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
			
			tDate = endDate[i].split("/");
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
			
			if(!isValidTime(beginTime[i])){
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.InvalidTimeInput"));
				return msgs;
			}
			
			if(!isValidTime(endTime[i])){
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.InvalidTimeInput"));
				return msgs;
			}
			
		}
		
		return msgs;
	}

	private List form2properties(List<Optime1> edList,DynaActionForm form){
		List<Map> fList = new ArrayList<Map>();
		
		int i = 0;
		String[] beginDate = form.getStrings("beginDate");
		String[] beginTime = form.getStrings("beginTime");
		String[] endDate = form.getStrings("endDate");
		String[] endTime = form.getStrings("endTime");

		for(Optime1 opt:edList){
			Map<String,String> fMap = new HashMap<String, String>();
			fMap.put("level", opt.getLevel());
			fMap.put("depart", opt.getDepartName());
			fMap.put("levelName", opt.getLevelName());
			fMap.put("departName", opt.getDepart());
			fMap.put("beginDate", beginDate[i]);
			fMap.put("beginTime", beginTime[i]);
			fMap.put("endDate", endDate[i]);
			fMap.put("endTime", endTime[i]);
			fList.add(fMap);
			i++;
 		}
		
		return fList;
	}
}
