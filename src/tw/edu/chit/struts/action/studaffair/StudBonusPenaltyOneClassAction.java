package tw.edu.chit.struts.action.studaffair;

import java.util.ArrayList;
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

import tw.edu.chit.dao.StudAffairDAO;
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

public class StudBonusPenaltyOneClassAction extends BaseLookupDispatchAction {
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "edit");
		map.put("Save", "save");
		map.put("Back", "cancel");
		map.put("Cancel", "cancel");
		return map;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);

		String opmode = dynForm.getString("opmode");
		log.debug("StudBonusPenalty.opmode:" + opmode);
		if(opmode.equals("ok")) return edit(mapping,form,request,response);
		else if(opmode.equals("save"))  return save(mapping,form,request,response);
		else if(opmode.equals("cancel"))  return cancel(mapping,form,request,response);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
						
		List<Code2> BonusPenaltyReasonList = sm.findBonusPenaltyReason("");
		List<Code5> BonusPenaltyCodeList = Global.BonusPenaltyCodeList;
		

		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		Map classBPInfo = new HashMap();
		classBPInfo.put("mode", "edit");
		
		session.setAttribute("BonusPenaltyReason", BonusPenaltyReasonList);
		session.setAttribute("BonusPenaltyCode", BonusPenaltyCodeList);
		session.setAttribute("classBPInfo", classBPInfo);
		setContentPage(session, "studaffair/StudBonusPenaltyOneClass.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("classBPInfo");
		session.removeAttribute("classBPStudents");
		Toolket.resetCheckboxCookie(response, "classBPStudents");
		
		Map classBPInfo = new HashMap();
		classBPInfo.put("mode", "edit");
		
		session.setAttribute("classBPInfo", classBPInfo);
		setContentPage(request.getSession(false), "studaffair/StudBonusPenaltyOneClass.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		DynaActionForm form = (DynaActionForm)aform;
		ActionMessages messages = new ActionMessages();
		
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
		String campus = form.getString("campusInChargeSAF").trim();
		String school = form.getString("schoolInChargeSAF").trim();
		String dept = form.getString("deptInChargeSAF").trim();
		String myclass = form.getString("classInChargeSAF").trim();
		
		messages = validateInput(form);

		if (!messages.isEmpty()) {
			//messages.add(fillFormProperty(form, classBookList));
			saveErrors(request, messages);
			session.setAttribute("classBPInfo", form.getMap());
			return mapping.findForward("Main");
		} else {
			String reasonName = sm.findBonusPenaltyReason(reason).get(0).getName();
			String classInCharge = credential.getClassInChargeSqlFilterSAF();
			Map classBPInfo = new HashMap();
	
			classBPInfo = form.getMap();
			classBPInfo.put("mode", "save");
			classBPInfo.put("className", Toolket.getClassFullName(myclass));
			classBPInfo.put("reasonName", reasonName);
			classBPInfo.put("kind1Name", Toolket.getBonusPenalty(kind1));
			classBPInfo.put("kind2Name", Toolket.getBonusPenalty(kind2));
			
			List<Student> students = sm.findStudentsByClass(myclass);
			log.debug("StudBonusPenalty.opmode:edit,students:" + myclass + ":" + students.size());
			//log.debug("StudBonusPenalty.opmode:edit,mode:" + classBPInfo.get("mode").toString());
			
			if (myclass.trim().equals("")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.PleaseSelect","班級"));
				saveErrors(request, messages);
				session.setAttribute("classBPInfo", classBPInfo);
			} else {
				session.setAttribute("classBPStudents", students);
				session.setAttribute("classBPInfo", classBPInfo);
			}
		}
		return mapping.findForward("Main");
	}

	public ActionForward save(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		DynaActionForm form = (DynaActionForm)aform;
		ActionMessages messages = new ActionMessages();
		
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		Map sessionMap = (Map)session.getAttribute("classBPInfo");
		sessionMap.put("mode", "edit");
		Map classBPInfo = new HashMap();
		classBPInfo.put("mode", "edit");
		
			
			List<Student> selStuds = getStudentsSelectedList(request);
			
			try {
				messages = sm.createDesdByStudents(sessionMap, selStuds);
				if (!messages.isEmpty()) {
					saveErrors(request, messages);
					session.setAttribute("classBPInfo", sessionMap);
					return mapping.findForward("Main");
				}else{
					ActionMessages msgs = new ActionMessages();
					double desdScore = 0d;
					
					for(Student stud:selStuds){
						desdScore = sm.calDesdScoreByStudent(stud.getStudentNo());
						//msgs = sm.modifyJustDesdScore(stud.getStudentNo(), desdScore);
						
						//取代sm.modifyJustDilgScore(studentNo, justScore)該元件執行有誤  Leo20120307
						CourseManager manager = (CourseManager) getBean("courseManager");
						String sqlstudent_no = manager.ezGetString(
								"Select student_no From just Where student_no='" + stud.getStudentNo() + "' ");
						double SeltotalScore = Double.parseDouble(
								manager.ezGetString("Select total_score From just Where student_no='" + stud.getStudentNo() + "' "));
						double totalScore = SeltotalScore+desdScore;			
						if(sqlstudent_no.equals("")){
							msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
									"Message.MessageN1", "找不到[" + stud.getStudentNo() + "]該學生的操行成績!"));
						}else{				
							manager.executeSql("Update just Set total_score="+totalScore+" Where student_no='"+stud.getStudentNo()+"'");
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
				session.removeAttribute("classBPInfo");
				session.removeAttribute("classBPStudents");
				Toolket.resetCheckboxCookie(response, "classBPStudents");
				session.setAttribute("classBPInfo", classBPInfo);
				
				return mapping.findForward("Main");
			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.removeAttribute("classBPInfo");
				session.removeAttribute("classBPStudents");
				session.setAttribute("classBPInfo", classBPInfo);
				return mapping.findForward("Main");
			}
	}
	
	private List<Student> getStudentsSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "classBPStudents");
		List<Student> studs = (List<Student>)session.getAttribute("classBPStudents");
		List<Student> selStuds = new ArrayList<Student>();
		Student student;
		StudAffairDAO dao =(StudAffairDAO)getBean("studAffairDAO");
		for (Iterator<Student> studIter = studs.iterator(); studIter.hasNext();) {
			student = studIter.next();
			if (Toolket.isValueInCookie(student.getOid().toString(), oids)) {
				dao.reload(student);
				selStuds.add(student);
			}
		}
		return selStuds;
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
		
		return msgs;
	}

}
