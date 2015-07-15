package tw.edu.chit.struts.action.studaffair;

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
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;
import tw.edu.chit.util.Global;

public class StudTimeOffAction  extends BaseLookupDispatchAction {

	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create","timeOffAdd");
		map.put("Forward.timeOffAdd","timeOffAdd");
		map.put("Query","timeOffQuery");
		map.put("Forward.timeOffQuery", "timeOffQuery");
		map.put("Delete","timeOffDelete");
		map.put("DeleteConfirm", "timeOffDelConfirm");
		map.put("Cancel", "cancel");
		map.put("Modify","timeOffModify");
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
		
		String daynite = dynForm.getString("daynite");
		log.debug("daynite=" + daynite);
		if(daynite.equals("")) StudTimeoffInit.put("daynite", "1");
		else StudTimeoffInit.put("daynite", daynite);
		HttpSession session = request.getSession(false);
		session.setAttribute("StudTimeoffInit", StudTimeoffInit);
		
		setContentPage(session, "studaffair/StudTimeOff.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward timeOffAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map initValue = new HashMap();
		initValue.put("mode", "Create");
		initValue.put("daynite", ((Map)session.getAttribute("StudTimeoffInit")).get("daynite"));
		
		List<Code5> tfTypeList = Global.TimeOffList;
		
		Calendar now = Calendar.getInstance();
		String nows = "" + (now.get(Calendar.YEAR)-1911) 
					+ "-" + (now.get(Calendar.MONTH) + 1) 
					+ "-" + now.get(Calendar.DATE); 

		initValue.put("sddate", nows);
		initValue.put("tfYear", (now.get(Calendar.YEAR)-1911));
		initValue.put("tfMonth", (now.get(Calendar.MONTH) + 1));
		initValue.put("tfDay", now.get(Calendar.DATE));
		session.setAttribute("StudTimeOffType", tfTypeList);
		
		session.setAttribute("StudTimeOffEditInfo", initValue);
		session.removeAttribute("StudTimeOffEdit");
		session.removeAttribute("StudTimeOffInEdit");
		setContentPage(session, "studaffair/StudTimeOffEdit.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward timeOffQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Toolket.resetCheckboxCookie(response, "StudTimeOff");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		String daynite = aForm.getString("daynite");
		String tfYear = aForm.getString("tfYear");
		String tfMonth = aForm.getString("tfMonth");
		String tfDay   = aForm.getString("tfDay");
		String studentNo  = aForm.getString("studentNo");
		String ddate = "";
		log.debug("TimeOffQuery, tfYear=" + tfYear + " tfMonth=" + tfMonth + " tfDay=" + tfDay);
		/*
		if(!tfYear.equals("") && !tfMonth.equals("") && !tfDay.equals("")) {
			int intYear = Integer.parseInt(tfYear) + 1911;			
			ddate = intYear + "/" + tfMonth + "/" + tfDay;
		} else {
			if(studentNo.equals("")) {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.StudTimeOff.MustInput"));
				saveMessages(request, messages);
				setContentPage(session, "studaffair/StudTimeOff.jsp");
				return mapping.findForward("Main");
			}
		}
		*/
		if(studentNo.equals("")) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.StudTimeOff.MustInput"));
			saveMessages(request, messages);
			setContentPage(session, "studaffair/StudTimeOff.jsp");
			return mapping.findForward("Main");
		}
		
		if(!tfYear.equals("") && !tfMonth.equals("") && !tfDay.equals("")) {
			int intYear = Integer.parseInt(tfYear) + 1911;			
			ddate = intYear + "/" + tfMonth + "/" + tfDay;
		}
		
		
		// log.debug("=======> student_no=" + student_no);
		// log.debug("=======> student_name=" + student_name);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		List<Dilg> dilgs = sm.findDilgByTimeOffInfo(ddate, studentNo, classInCharge);
		 //log.debug("=======> students=" + students.size());
		log.debug("=======> Dilgs=" + dilgs.size());
		
		if (dilgs.size()==0) {
			//can't not find any TimeOff records (dilgs)
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.StudTimeOff.noDilgsFound"));
			saveMessages(request, messages);
			session.removeAttribute("StudTimeOffType");
			session.removeAttribute("StudTimeOffList");
			session.removeAttribute("TimeOffStuMap");
			setContentPage(session, "studaffair/StudTimeOff.jsp");
		}
		else if (dilgs.size() > 0){
			boolean only1stu = true;
			String stuNo = dilgs.get(0).getStudentNo();
			
			/* char dyflag = stuNo.charAt(3);
			log.debug("StudTimeOffQuery->dyflag:" + dyflag);
			switch (dyflag) {
				case '2': case '3': case '4': case '5':
					daynite = "1";
					break;
				
				case '0': case '1': case '8': case '9': case 'A':
					daynite = "2";
					break;
				case '6': case '7':
					daynite = "3";
					break;
				case 'G':
					if(Integer.parseInt(stuNo.substring(5, 6)) <= 4)
						daynite = "1";
					else
						daynite = "2";
					break;
				default:
					daynite = "1";
			
			} */
			
			daynite = sm.chkStudentDepart(stuNo);
			
			//log.debug("StudTimeOffQuery->daynite:" + daynite);
			Dilg dilg = new Dilg();
			
			ScoreManager scm = (ScoreManager)getBean("scoreManager");
			
			Student stu = new Student();
			
			if(dilgs.size() > 0) {
				for(Iterator<Dilg> dilgIter = dilgs.iterator(); dilgIter.hasNext();) {
					dilg = dilgIter.next();
					stu = scm.findStudentByStudentNo(dilg.getStudentNo());
					if(stu != null) {
						dilg.setStudentName(stu.getStudentName());
					} else {
						dilg.setStudentName("");
						dilgIter.remove();
						continue;
					}
					//log.debug("TimeOffQuery->" + dilg.getOid() + ":" + dilg.getStudentName() + ":" + dilg.getDdate() + ":" + dilg.getAbs2());
					dilg.setSddate(Toolket.printDate(dilg.getDdate()));
					dilg.setDeptClassName(Toolket.getClassFullName(dilg.getDepartClass()));
					if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0)
						dilg.setAbsName0(Toolket.getTimeOff(dilg.getAbs0().toString()).substring(1,2));
					if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0)
						//log.debug("TimeOffQuery->Abs1:AbsName1:" + dilg.getAbs1() + ":" + Toolket.getTimeOff(dilg.getAbs1().toString()));
						dilg.setAbsName1(Toolket.getTimeOff(dilg.getAbs1().toString()).substring(1,2));
					if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0)
						dilg.setAbsName2(Toolket.getTimeOff(dilg.getAbs2().toString()).substring(1,2));
					if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0)
						dilg.setAbsName3(Toolket.getTimeOff(dilg.getAbs3().toString()).substring(1,2));
					if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0)
						dilg.setAbsName4(Toolket.getTimeOff(dilg.getAbs4().toString()).substring(1,2));
					if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0)
						dilg.setAbsName5(Toolket.getTimeOff(dilg.getAbs5().toString()).substring(1,2));
					if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0)
						dilg.setAbsName6(Toolket.getTimeOff(dilg.getAbs6().toString()).substring(1,2));
					if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0)
						dilg.setAbsName7(Toolket.getTimeOff(dilg.getAbs7().toString()).substring(1,2));
					if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0)
						dilg.setAbsName8(Toolket.getTimeOff(dilg.getAbs8().toString()).substring(1,2));
					if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0)
						dilg.setAbsName9(Toolket.getTimeOff(dilg.getAbs9().toString()).substring(1,2));
					if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0)
						dilg.setAbsName10(Toolket.getTimeOff(dilg.getAbs10().toString()).substring(1,2));
					if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0)
						dilg.setAbsName11(Toolket.getTimeOff(dilg.getAbs11().toString()).substring(1,2));
					if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0)
						dilg.setAbsName12(Toolket.getTimeOff(dilg.getAbs12().toString()).substring(1,2));
					if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0)
						dilg.setAbsName13(Toolket.getTimeOff(dilg.getAbs13().toString()).substring(1,2));
					if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0)
						dilg.setAbsName14(Toolket.getTimeOff(dilg.getAbs14().toString()).substring(1,2));
					if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0)
						dilg.setAbsName15(Toolket.getTimeOff(dilg.getAbs15().toString()).substring(1,2));
				}
			}

			for(Iterator<Dilg> dilgIter = dilgs.iterator(); dilgIter.hasNext();) {
				dilg = dilgIter.next();
				if(dilg.getStudentNo().equalsIgnoreCase(stuNo)) {
					continue;
				} else {
					only1stu = false;
					//log.debug("StudTimeOff->notOnlyOneStudent!!!" + "," + dilg.getStudentNo() + "," + stuNo);
					break;
				}
			}
			
			List timeOff = Global.TimeOffList;
			session.setAttribute("StudTimeOffType", timeOff);
			
			int stuabs[] = new int[timeOff.size()];
			for(int i = 0; i < stuabs.length; i++) {
				stuabs[i] = 0;
			}
			
			if(only1stu) {
				stu = scm.findStudentByStudentNo(stuNo);
				Map stuMap = new HashMap();
				stuMap.put("studentNo", stuNo);
				stuMap.put("studentName", stu.getStudentName());
				stuMap.put("departClass", stu.getDepartClass());
				stuMap.put("depClassName", Toolket.getClassFullName(stu.getDepartClass()));

				for(Iterator<Dilg> dilgIter = dilgs.iterator(); dilgIter.hasNext();) {
					dilg = dilgIter.next();
					if(dilg.getAbs0() != null && dilg.getAbs0() > 0)
						stuabs[dilg.getAbs0()] = stuabs[dilg.getAbs0()] + 1;
					if(dilg.getAbs1() != null && dilg.getAbs1() > 0)
						stuabs[dilg.getAbs1()] = stuabs[dilg.getAbs1()] + 1;
					if(dilg.getAbs2() != null && dilg.getAbs2() > 0)
						stuabs[dilg.getAbs2()] = stuabs[dilg.getAbs2()] + 1;
					if(dilg.getAbs3() != null && dilg.getAbs3() > 0)
						stuabs[dilg.getAbs3()] = stuabs[dilg.getAbs3()] + 1;
					if(dilg.getAbs4() != null && dilg.getAbs4() > 0)
						stuabs[dilg.getAbs4()] = stuabs[dilg.getAbs4()] + 1;
					if(dilg.getAbs5() != null && dilg.getAbs5() > 0)
						stuabs[dilg.getAbs5()] = stuabs[dilg.getAbs5()] + 1;
					if(dilg.getAbs6() != null && dilg.getAbs6() > 0)
						stuabs[dilg.getAbs6()] = stuabs[dilg.getAbs6()] + 1;
					if(dilg.getAbs7() != null && dilg.getAbs7() > 0)
						stuabs[dilg.getAbs7()] = stuabs[dilg.getAbs7()] + 1;
					if(dilg.getAbs8() != null && dilg.getAbs8() > 0)
						stuabs[dilg.getAbs8()] = stuabs[dilg.getAbs8()] + 1;
					if(dilg.getAbs9() != null && dilg.getAbs9() > 0)
						stuabs[dilg.getAbs9()] = stuabs[dilg.getAbs9()] + 1;
					if(dilg.getAbs10() != null && dilg.getAbs10() > 0)
						stuabs[dilg.getAbs10()] = stuabs[dilg.getAbs10()] + 1;
					if(dilg.getAbs11() != null && dilg.getAbs11() > 0)
						stuabs[dilg.getAbs11()] = stuabs[dilg.getAbs11()] + 1;
					if(dilg.getAbs12() != null && dilg.getAbs12() > 0)
						stuabs[dilg.getAbs12()] = stuabs[dilg.getAbs12()] + 1;
					if(dilg.getAbs13() != null && dilg.getAbs13() > 0)
						stuabs[dilg.getAbs13()] = stuabs[dilg.getAbs13()] + 1;
					if(dilg.getAbs14() != null && dilg.getAbs14() > 0)
						stuabs[dilg.getAbs14()] = stuabs[dilg.getAbs14()] + 1;
					if(dilg.getAbs15() != null && dilg.getAbs15() > 0)
						stuabs[dilg.getAbs15()] = stuabs[dilg.getAbs15()] + 1;
				}

				stuMap.put("tfCal" , stuabs);

				session.setAttribute("TimeOffStuMap", stuMap);
			} else {
				session.removeAttribute("TimeOffStuMap");
			}
			
			session.setAttribute("StudTimeOffList", dilgs);
 			setContentPage(session, "studaffair/StudTimeOff.jsp");
		}
		
		Map<String, String> StudTimeoffInit = new HashMap<String, String>();
		StudTimeoffInit.put("daynite", daynite);
		StudTimeoffInit.put("tfYear", tfYear);
		StudTimeoffInit.put("tfMonth", tfMonth);
		StudTimeoffInit.put("tfDay", tfDay);
		StudTimeoffInit.put("studentNo", studentNo);
		
		session.setAttribute("StudTimeoffInit", StudTimeoffInit);

		
		aForm.set("tfYear", "");
		aForm.set("tfMonth", "");
		aForm.set("tfDay", "");
		aForm.set("studentNo", "");
		aForm.initialize(mapping);
		//log.debug("After ReQuery tfYear=" + aForm.getString("tfYear"));
		return mapping.findForward("Main");
	}

	public ActionForward timeOffDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		List<Dilg> selDilgs = getTimeOffSelectedList(request);	//Records selected for delete
		Toolket.setAllCheckboxCookie(response, "TimeOffDelete", selDilgs.size());	//Set cookie of record count for delete
		HttpSession session = request.getSession(false);
		session.setAttribute("StudTimeOffDelete", selDilgs);
		setContentPage(session, "studaffair/StudTimeOffDelete.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
		
	}
	
	public ActionForward timeOffDelConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(false);
		List<Dilg> selDilgs = (List<Dilg>)session.getAttribute("StudTimeOffDelete");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		CourseManager manager = (CourseManager) getBean("courseManager");
		List<Dilg> undeletedRecs = sm.delStudTimeOffs(selDilgs, Toolket.getBundle(request, "messages.studaffair"));
		
		if(!undeletedRecs.isEmpty()){
			for(Dilg dilg:selDilgs){
				for(Dilg undelete:undeletedRecs){
					if(dilg.getOid()==undelete.getOid()){
						selDilgs.remove(dilg);
						break;
					}
				}
			}
		}
		
		ActionMessages msgs = new ActionMessages();
		Date sddate = new Date();
		double justScore = 0d;
		String studentNo="";
		for(Dilg dilg:selDilgs){
			sddate = dilg.getDdate();
			studentNo = dilg.getStudentNo();
			justScore = sm.calDilgScoreByStudent(studentNo, "0");
			//msgs = sm.modifyJustDilgScore(studentNo, justScore);  // Leo20120307
			
			//取代sm.modifyJustDilgScore(studentNo, justScore)該元件執行有誤  Leo20120307
			String sqlstudent_no = manager.ezGetString("Select student_no From just Where student_no='" + studentNo + "' ");
			double SeltotalScore = Double.parseDouble(manager.ezGetString("Select total_score From just Where student_no='" + studentNo + "' "));
			double totalScore = SeltotalScore+justScore;			
			if(sqlstudent_no.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.MessageN1", "找不到[" + studentNo + "]該學生的操行成績!"));
			}else{				
				manager.executeSql("Update just Set total_score="+totalScore+" Where student_no='"+studentNo+"'");
			}
			//=======================================================================
			
			if(msgs.isEmpty()){
				msgs = sm.modifySeldDilgPeriod(studentNo, sddate);
				if(!msgs.isEmpty()){
					messages.add(msgs);
				}
			}else{
				messages.add(msgs);
				msgs = sm.modifySeldDilgPeriod(studentNo, sddate);
				if(!msgs.isEmpty()){
					messages.add(msgs);
				}
			}
		}
		
		if(messages.isEmpty()){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			"Message.DeleteSuccessful"));
		}
		
		saveMessages(request, msgs);
		session.removeAttribute("StudTimeOffDelete");
		// no undeleteScores will happen even if delete failure
		
		if (undeletedRecs.size() == 0) {
			session.removeAttribute("StudTimeOffList");
			Map initMap = (Map)session.getAttribute("StudTimeoffInit");
			DynaActionForm dynForm = (DynaActionForm)form;
			dynForm.set("daynite", initMap.get("daynite"));
			dynForm.set("tfYear", initMap.get("tfYear"));
			dynForm.set("tfMonth", initMap.get("tfMonth"));
			dynForm.set("tfDay", initMap.get("tfDay"));
			dynForm.set("studentNo", initMap.get("studentNo"));
			log.debug("delete Dilg success, tfYear=" + dynForm.getString("tfYear"));
			
			return timeOffQuery(mapping, (ActionForm)dynForm, request, response);
		} else {
			session.setAttribute("UndeletedTimeOffs", undeletedRecs);
			setContentPage(session, "studaffair/StudTimeOffUndelete.jsp");
			return mapping.findForward("Main");
		}
		
	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		request.getSession(false).removeAttribute("StudTimeOffDelete");
		setContentPage(request.getSession(false), "studaffair/StudTimeOff.jsp");
		return mapping.findForward("Main");
	}

	
	public ActionForward timeOffModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		List<Dilg> selDilgs = getTimeOffSelectedList(request);
		log.debug("StudTimeOff==>selDilgs.size():" + selDilgs.size());
		Dilg dilg;
		List editList = new ArrayList();
		
		//Multiple Record Modify
		/*for(Iterator<Dilg> dilgIter=selDilgs.iterator(); dilgIter.hasNext();) {
			dilg = dilgIter.next();
			Map dilgMap = new HashMap();
			setDilgInitValue(dilg, dilgMap);
			editList.add(dilgMap);
		}
		*/
		dilg = selDilgs.get(0);
		Map initValue = new HashMap();
		
		initValue.put("mode", "Modify");
		initValue.put("studentNo", dilg.getStudentNo());
		initValue.put("studentName", dilg.getStudentName());
		initValue.put("departClass", dilg.getDepartClass());
		initValue.put("deptClassName", dilg.getDeptClassName());
		initValue.put("daynite", dilg.getDaynite());
		
		
		List tfTypeList = Global.TimeOffList;
		session.setAttribute("StudTimeOffType", tfTypeList);
		
		//setDilgInitValue(dilg, initValue);
		session.setAttribute("StudTimeOffEditInfo", initValue);
		session.setAttribute("StudTimeOffInEdit", selDilgs);
		setContentPage(session, "studaffair/StudTimeOffModify.jsp");
		return mapping.findForward("Main");

	}

	
	//Private Method Here ============================>>
	private List getTimeOffSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "StudTimeOff");
		List<Dilg> dilgs = (List<Dilg>)session.getAttribute("StudTimeOffList");
		List<Dilg> selDilgs = new ArrayList<Dilg>();
		Dilg dilg;
		StudAffairDAO dao =(StudAffairDAO)getBean("studAffairDAO");
		for (Iterator<Dilg> dilgIter = dilgs.iterator(); dilgIter.hasNext();) {
			dilg = dilgIter.next();
			if (Toolket.isValueInCookie(dilg.getOid().toString(), oids)) {
				dao.reload(dilg);
				selDilgs.add(dilg);
			}
		}
		return selDilgs;
	}
	
	private List getTimeOffDeletedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "StudTimeOffDel");
		List<Dilg> dilgs = (List<Dilg>)session.getAttribute("StudTimeOffDelete");
		List<Dilg> selDilgs = new ArrayList<Dilg>();
		Dilg dilg;
		for (Iterator<Dilg> dilgIter = dilgs.iterator(); dilgIter.hasNext();) {
			dilg = dilgIter.next();
			if (Toolket.isValueInCookie(dilg.getOid().toString(), oids)) {
				selDilgs.add(dilg);
			}
		}
		return selDilgs;
	}
	
	private void setDilgInitValue(Dilg dilg, Map<String, String> initValue) {
		
		initValue.put("studentNo", dilg.getStudentNo());
		initValue.put("studentName", dilg.getStudentName());
		initValue.put("departClass", dilg.getDepartClass());
		initValue.put("deptClassName", dilg.getDeptClassName());
		initValue.put("sddate", dilg.getSddate());
		if(dilg.getAbs0()!= null)
			initValue.put("abs0", dilg.getAbs0().toString());
		else
			initValue.put("abs1", "");
		if(dilg.getAbs1()!= null)
			initValue.put("abs1", dilg.getAbs1().toString());
		else
			initValue.put("abs2", "");
		if(dilg.getAbs2()!= null)
			initValue.put("abs2", dilg.getAbs2().toString());
		else
			initValue.put("abs3", "");
		if(dilg.getAbs3()!= null)
			initValue.put("abs3", dilg.getAbs3().toString());
		else
			initValue.put("abs3", "");
		if(dilg.getAbs4()!= null)
			initValue.put("abs4", dilg.getAbs4().toString());
		else
			initValue.put("abs4", "");
		if(dilg.getAbs5()!= null)
			initValue.put("abs5", dilg.getAbs5().toString());
		else
			initValue.put("abs5", "");
		if(dilg.getAbs6()!= null)
			initValue.put("abs6", dilg.getAbs6().toString());
		else
			initValue.put("abs6", "");
		if(dilg.getAbs7()!= null)
			initValue.put("abs7", dilg.getAbs7().toString());
		else
			initValue.put("abs7", "");
		if(dilg.getAbs8()!= null)
			initValue.put("abs8", dilg.getAbs8().toString());
		else
			initValue.put("abs8", "");
		if(dilg.getAbs9()!= null)
			initValue.put("abs9", dilg.getAbs9().toString());
		else
			initValue.put("abs9", "");
		if(dilg.getAbs10()!= null)
			initValue.put("abs10", dilg.getAbs10().toString());
		else
			initValue.put("abs10", "");
		if(dilg.getAbs11()!= null)
			initValue.put("abs11", dilg.getAbs11().toString());
		else
			initValue.put("abs11", "");
		if(dilg.getAbs12()!= null)
			initValue.put("abs12", dilg.getAbs12().toString());
		else
			initValue.put("abs12", "");
		if(dilg.getAbs13()!= null)
			initValue.put("abs13", dilg.getAbs13().toString());
		else
			initValue.put("abs13", "");
		if(dilg.getAbs14()!= null)
			initValue.put("abs14", dilg.getAbs14().toString());
		else
			initValue.put("abs14", "");
		if(dilg.getAbs15()!= null)
			initValue.put("abs15", dilg.getAbs15().toString());
		else
			initValue.put("abs15", "");
	}

}
