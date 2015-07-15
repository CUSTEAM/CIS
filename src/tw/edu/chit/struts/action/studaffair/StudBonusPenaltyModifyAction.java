package tw.edu.chit.struts.action.studaffair;

import java.util.Calendar;
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

import tw.edu.chit.model.Code2;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Desd;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.Toolket;

public class StudBonusPenaltyModifyAction   extends BaseLookupDispatchAction{
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
		log.debug("StudBonusPenalty.opmode:" + opmode);
		if(opmode.equals("ok")) return save(mapping,form,request,response);
		else if(opmode.equals("cancel"))  return cancel(mapping,form,request,response);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");

		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		setContentPage(session, "studaffair/StudBonusPenaltyModify.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudBonusPenaltyInfo");
		//session.removeAttribute("StudBonusPenaltyInfo");
		setContentPage(request.getSession(false), "studaffair/StudBonusPenalty.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward save(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("Bonuspenalty.save called!");
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		DynaActionForm form = (DynaActionForm)aform;
		
		//int number = Integer.parseInt(form.get("number").toString().trim());
		
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		Map StudBonusPenaltyInfo = (Map)session.getAttribute("StudBonusPenaltyInfo");

		int num;
		
		ActionMessages messages = validateInput(form);

		if (!messages.isEmpty()) {
			//messages.add(fillFormProperty(form, classBookList));
			saveErrors(request, messages);
			session.setAttribute("StudBonusPenaltyModify", form.getMap());
			return mapping.findForward("Main");
		} else {
			List<Desd> desdInEdit = (List<Desd>)session.getAttribute("StudBonusPenaltyInEdit");
			
			try {
				//log.debug("StudBonusPenalty.studentNo[].length()=" + form.getStrings("studentNo").length);
						  		
				messages = sm.modifyDesdByForm(desdInEdit, form.getMap());
				if (!messages.isEmpty()) {
					saveErrors(request, messages);
					session.setAttribute("StudBonusPenaltyModify", form.getMap());
					return mapping.findForward("Main");
				}else{
					ActionMessages msgs = new ActionMessages();
					double desdScore = 0d;

					for(Desd desd:desdInEdit){
						desdScore = sm.calDesdScoreByStudent(desd.getStudentNo());
						//System.out.println("desdScore="+desdScore);
						//msgs = sm.modifyJustDesdScore(desd.getStudentNo(), desdScore);
						
						//取代sm.modifyJustDilgScore(studentNo, justScore)該元件執行有誤  Leo20120307
						CourseManager manager = (CourseManager) getBean("courseManager");
						String sqlstudent_no = manager.ezGetString(
								"Select student_no From just Where student_no='" + desd.getStudentNo() + "' ");
						double SeltotalScore = Double.parseDouble(
								manager.ezGetString("Select total_score From just Where student_no='" + desd.getStudentNo() + "' "));
						double totalScore = SeltotalScore+desdScore;			
						if(sqlstudent_no.equals("")){
							msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
									"Message.MessageN1", "找不到[" + desd.getStudentNo() + "]該學生的操行成績!"));
						}else{				
							manager.executeSql("Update just Set total_score="+totalScore+" Where student_no='"+desd.getStudentNo()+"'");
						}
						//=======================================================================
						
						if(!msgs.isEmpty()){
							messages.add(msgs);
						}
					}
				}
				
				if(messages.isEmpty()){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.ModifySuccessful"));
				}
				saveMessages(request, messages);

				session.removeAttribute("StudBonusPenaltyInEdit");
				session.removeAttribute("StudBonusPenaltyModify");
				session.removeAttribute("StudBonusPenaltyList");
				setContentPage(session, "studaffair/StudBonusPenalty.jsp");
				return mapping.findForward("Main");
			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("StudBonusPenaltyEdit", form.getMap());
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
	
	private ActionMessages validateInput(DynaActionForm form) {
		ActionMessages msgs = new ActionMessages();

		//String[] studentNos = form.getStrings("studentNo");
		String[] bpYear = form.getStrings("bpYear");
		String[] bpMonth = form.getStrings("bpMonth");
		String[] bpDay = form.getStrings("bpDay");
		String[] docNo = form.getStrings("docNo");
		String[] reason = form.getStrings("reason");
		//String reasonSel = form.getString("reasonSel");
		String[] kind1 = form.getStrings("kind1");
		String[] kind2 = form.getStrings("kind2");
		String[] cnts1 = form.getStrings("cnt1");
		String[] cnts2 = form.getStrings("cnt2");
		//ScoreManager scm = (ScoreManager)getBean("scoreManager");

		int cnt1 = 0,cnt2 = 0;
		for(int k=0; k<cnts1.length; k++){
			//Student student = scm.findStudentByStudentNo(studentNos[k]);
			if(!cnts1[k].equals("")) {
				if(kind1[k].equals("")){
					msgs.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.BonusPenaltyMustInput"));
					return msgs;

				}
				cnt1= Integer.parseInt(cnts1[k]);
				if(cnt1==0){
					msgs.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.BonusPenaltyCountMustLarge0"));
					return msgs;
				}
			}
			if(!cnts2[k].equals("")) {
				if(kind2[k].equals("")){
					msgs.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.BonusPenaltyMustInput"));
					return msgs;

				}
				cnt2= Integer.parseInt(cnts2[k]);
				if(cnt2==0){
					msgs.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.BonusPenaltyCountMustLarge0"));
					return msgs;
				}

			}
			
			if((cnt1==0 && cnt2==0)) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.BonusPenaltyMustInput"));
				return msgs;
			}
			
			if(docNo[k].equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.BonusPenaltyDocNoMustInput"));
				return msgs;
			}
		}
		
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		List<Code2> BonusPenaltyReasonList = sm.findBonusPenaltyReason("");
		boolean isfound = false;
		
		for(int j=0; j<reason.length; j++){
			isfound = false;
			if(reason.equals("")) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.BonusPenaltyReason"));
				return msgs;
			} else {
				for(Iterator<Code2> c2Iter=BonusPenaltyReasonList.iterator(); c2Iter.hasNext();){
					Code2 code2=c2Iter.next();
					if(reason[j].equals(code2.getNo())) {
						isfound = true;
						break;
					}
				}
				if(!isfound) {
					msgs.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.BonusPenaltyReason"));
					return msgs;
				}
			}
			
		}
		
		for(int i=0; i<bpYear.length; i++){
			if(!isValidDate(bpYear[i], bpMonth[i], bpDay[i])) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InvalidDateInput"));
				return msgs;
			}
		}

		return msgs;
	}

}
