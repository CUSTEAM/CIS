package tw.edu.chit.struts.action.studaffair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.text.DateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.Code2;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Desd;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;
import tw.edu.chit.util.Global;

public class StudBonusPenaltyOneCaseAction  extends BaseLookupDispatchAction {

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
		
		log.debug("StudBonusPenalty.opmode:" + opmode);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
						
		List<Code2> BonusPenaltyReasonList = sm.findBonusPenaltyReason("");
		List<Code5> BonusPenaltyCodeList = Global.BonusPenaltyCodeList;
		

		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		
		String reason = request.getParameter("reason");
		String kind1 = request.getParameter("kind1");
		String kind2 = request.getParameter("kind2");
		
		StudBonusPenaltyInfo.put("reason", reason);
		StudBonusPenaltyInfo.put("kind1", kind1);
		StudBonusPenaltyInfo.put("kind2", kind2);
		
		session.setAttribute("BonusPenaltyReason", BonusPenaltyReasonList);
		session.setAttribute("BonusPenaltyCode", BonusPenaltyCodeList);
		session.setAttribute("StudBonusPenaltyInfo", StudBonusPenaltyInfo);
		setContentPage(session, "studaffair/StudBonusPenaltyOneCase.jsp");
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
		setContentPage(request.getSession(false), "studaffair/StudBonusPenaltyOneCase.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward save(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.debug("Bonuspenalty.save called!");
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		DynaActionForm form = (DynaActionForm)aform;
		
		//int number = Integer.parseInt(form.get("number").toString().trim());
		String[] studentNos = form.getStrings("studentNo");
		
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		Map StudBonusPenaltyInfo = (Map)session.getAttribute("StudBonusPenaltyInfo");

		int num;
		
		ActionMessages messages = validateInput(form);
		//System.out.println("messages="+messages);

		if (!messages.isEmpty()) {
			//messages.add(fillFormProperty(form, classBookList));
			saveErrors(request, messages);
			session.setAttribute("StudBonusPenaltyEdit", form.getMap());
			
			return mapping.findForward("Main");
		} else {
			try {
				log.debug("StudBonusPenalty.studentNo[].length()=" + form.getStrings("studentNo").length);
				messages = sm.createDesdByForm(form.getMap());
				if (!messages.isEmpty()) {
					saveErrors(request, messages);
					session.setAttribute("StudBonusPenaltyEdit", form.getMap());
					return mapping.findForward("Main");
				}else{
					ActionMessages msgs = new ActionMessages();
					double desdScore = 0d;

					for(int i=0; i<studentNos.length; i++){
						desdScore = sm.calDesdScoreByStudent(studentNos[i]);
						//msgs = sm.modifyJustDesdScore(studentNos[i], desdScore);
						
						//取代sm.modifyJustDilgScore(studentNo, justScore)該元件執行有誤  Leo20120307
						CourseManager manager = (CourseManager) getBean("courseManager");
						String sqlstudent_no = manager.ezGetString(
								"Select student_no From just Where student_no='" + studentNos[i] + "' ");
						double SeltotalScore = Double.parseDouble(
								manager.ezGetString("Select total_score From just Where student_no='" + studentNos[i] + "' "));
						double totalScore = SeltotalScore+desdScore;			
						if(sqlstudent_no.equals("")){
							msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
									"Message.MessageN1", "找不到[" + studentNos[i] + "]該學生的操行成績!"));
						}else{				
							manager.executeSql("Update just Set total_score="+totalScore+" Where student_no='"+studentNos[i]+"'");
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

				form.initialize(mapping);
				//session.removeAttribute("StudTimeOffEdit");
				session.removeAttribute("StudBonusPenaltyEdit");
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
			
			//System.out.println(tfdate);

			if (tfdate.get(Calendar.YEAR) != itfYear
				|| tfdate.get(Calendar.MONTH) != itfMonth
				|| tfdate.get(Calendar.DAY_OF_MONTH) != itfDay) {

				//System.out.println("1:"+tfdate.get(Calendar.YEAR)+"/"+tfdate.get(Calendar.MONTH)+"/"+tfdate.get(Calendar.DAY_OF_MONTH));
				//System.out.println("2:"+itfYear+"/"+itfMonth+"/"+itfDay);
				return false;
			} else 
				return true;
		}
		return false;
	}
	
	private ActionMessages validateInput(DynaActionForm form) {
		ActionMessages msgs = new ActionMessages();

		String[] studentNos = form.getStrings("studentNo");
		String bpYear = form.getString("bpYear");
		String bpMonth = form.getString("bpMonth");
		String bpDay = form.getString("bpDay");
		String docNo = form.getString("docNo");
		String reason = form.getString("reason");
		String reasonSel = form.getString("reasonSel");
		String kind1 = form.getString("kind1");
		String kind1Sel = form.getString("kind1Sel");
		String kind2 = form.getString("kind2");
		String kind2Sel = form.getString("kind2Sel");
		String cnts1 = form.getString("cnt1");
		String cnts2 = form.getString("cnt2");
		
		int cnt1 = 0,cnt2 = 0;
		if(!cnts1.equals("")) {
			cnt1= Integer.parseInt(cnts1);
		}
		if(!cnts2.equals("")) {
			cnt2= Integer.parseInt(cnts2);
		}
		
		if((cnt1==0 && cnt2==0) || !kind1.equals(kind1Sel) || !kind2.equals(kind2Sel)) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.BonusPenaltyMustInput"));
			return msgs;
		}
		
		if(docNo.equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.BonusPenaltyMustInput"));
			return msgs;
		}
		
		if(!reason.equals(reasonSel)) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.BonusPenaltyReason"));
			return msgs;
		}
		
		if(!isValidDate(bpYear, bpMonth, bpDay)) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.InvalidDateInput"));
			return msgs;
		}
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		for(int i=0; i<studentNos.length; i++) {
			Student student;
			student = scm.findStudentByStudentNo(studentNos[i]);
			if(student == null) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.StudentNotFound",studentNos[i]));
				return msgs;
			}
		}

		return msgs;
	}
	
}
