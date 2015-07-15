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

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Code2;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Desd;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.Toolket;

public class StudBonusPenaltyAction extends BaseLookupDispatchAction {
	
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create","deservedAdd");
		map.put("Forward.deservedAdd","deservedAdd");
		map.put("Query","deservedQuery");
		map.put("Forward.deservedQuery", "deservedQuery");
		map.put("Delete","deservedDelete");
		map.put("DeleteConfirm", "deservedDelConfirm");
		map.put("Cancel", "cancel");
		map.put("Modify","deservedModify");
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
		
		List<Code2> BonusPenaltyReasonList = sm.findBonusPenaltyReason("");
		List<Code5> BonusPenaltyCodeList = Global.BonusPenaltyCodeList;
		

		session.setAttribute("BonusPenaltyReason", BonusPenaltyReasonList);
		session.setAttribute("BonusPenaltyCode", BonusPenaltyCodeList);
		//log.debug("StudBonusPenalty.CodeList.size()" +  BonusPenaltyCodeList.size());
		
		setContentPage(session, "studaffair/StudBonusPenalty.jsp");
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
	public ActionForward deservedAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map StudBonusPenaltyInfo = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
						
		List<Code2> BonusPenaltyReasonList = sm.findBonusPenaltyReason("");
		List<Code5> BonusPenaltyCodeList = Global.BonusPenaltyCodeList;
		

		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		
		session.setAttribute("BonusPenaltyReason", BonusPenaltyReasonList);
		session.setAttribute("BonusPenaltyCode", BonusPenaltyCodeList);
		session.setAttribute("StudBonusPenaltyInfo", StudBonusPenaltyInfo);
				
		session.removeAttribute("StudBonusPenaltyEdit");
		session.removeAttribute("StudBonusPenaltyInEdit");
		setContentPage(session, "studaffair/StudBonusPenaltyEdit.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward deservedQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Toolket.resetCheckboxCookie(response, "StudBonusPenalty");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		String bpYear = aForm.getString("bpYear");
		String bpMonth = aForm.getString("bpMonth");
		String bpDay   = aForm.getString("bpDay");
		String studentNo  = aForm.getString("studentNo");
		String ddate = "";
		Map stuMap = new HashMap();
		
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		
		//log.debug("TimeOffQuery, bpYear=" + bpYear + " bpMonth=" + bpMonth + " bpDay=" + bpDay);
		if(studentNo.equals("")) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.MustInput","學號"));
			saveMessages(request, messages);
			setContentPage(session, "studaffair/StudBonusPenalty.jsp");
			return mapping.findForward("Main");
		} else {
			Student stu = new Student();
			
			stu = scm.findStudentByStudentNo(studentNo);
			if(stu != null) {
				stuMap.put("studentNo", studentNo);
				stuMap.put("studentName", stu.getStudentName());
				stuMap.put("departClass", stu.getDepartClass());
				stuMap.put("depClassName", Toolket.getClassFullName(stu.getDepartClass()));
			} else {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.StudentNotFound",studentNo));
				saveMessages(request, messages);
				setContentPage(session, "studaffair/StudBonusPenalty.jsp");
				return mapping.findForward("Main");
			}
			

		}
		
		if(!bpYear.equals("") && !bpMonth.equals("") && !bpDay.equals("")) {
			int intYear = Integer.parseInt(bpYear) + 1911;			
			ddate = intYear + "/" + bpMonth + "/" + bpDay;
		}
		
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		List<Desd> desds = sm.findDesdByFormInfo(ddate, studentNo, classInCharge);
		 //log.debug("=======> students=" + students.size());
		log.debug("=======> Dilgs=" + desds.size());
		
		if (desds.size()==0) {
			//can't not find any TimeOff records (dilgs)
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.StudBonusPenalty.noDesdsFound"));
			saveMessages(request, messages);
			session.removeAttribute("StudBonusPenaltyList");
			session.removeAttribute("BonusPenaltyStuMap");
			setContentPage(session, "studaffair/StudBonusPenalty.jsp");
		}
		else if (desds.size() > 0){
			Desd desd = new Desd();
			

			for(Iterator<Desd> desdIter = desds.iterator(); desdIter.hasNext();) {
				desd = desdIter.next();
				//log.debug("TimeOffQuery->" + dilg.getOid() + ":" + dilg.getStudentName() + ":" + dilg.getDdate() + ":" + dilg.getAbs2());
				desd.setSddate(Toolket.printDate(desd.getDdate()));
				//desd.setDeptClassName(Toolket.getClassFullName(dilg.getDepartClass()));
				if(desd.getKind1()!= null && desd.getKind1()!= "0")
					desd.setDesdName1(Toolket.getBonusPenalty(desd.getKind1()));
				if(desd.getKind2()!= null && desd.getKind2()!= "0")
					desd.setDesdName2(Toolket.getBonusPenalty(desd.getKind2()));
			}
			
			List<Code5> BonusPenaltyCodeList = Global.BonusPenaltyCodeList;

			int stubps[] = new int[BonusPenaltyCodeList.size()];
			for(int i = 0; i < stubps.length; i++) {
				stubps[i] = 0;
			}
			

			for(Iterator<Desd> desdIter = desds.iterator(); desdIter.hasNext();) {
				desd = desdIter.next();
				if(desd.getKind1()!= null && desd.getKind1()!= "0" && !desd.getKind1().equals(""))
					stubps[Integer.parseInt(desd.getKind1())] = stubps[Integer.parseInt(desd.getKind1())] + desd.getCnt1();
				if(desd.getKind2()!= null && desd.getKind2()!= "0" && !desd.getKind2().equals(""))
					stubps[Integer.parseInt(desd.getKind2())] = stubps[Integer.parseInt(desd.getKind2())] + desd.getCnt2();
			}

			stuMap.put("bpCal" , stubps);

			session.setAttribute("BonusPenaltyStuMap", stuMap);
			
			session.setAttribute("StudBonusPenaltyList", desds);
 			setContentPage(session, "studaffair/StudBonusPenalty.jsp");
		}
		
		Map<String, String> StudBonusPenaltyInit = new HashMap<String, String>();
		StudBonusPenaltyInit.put("bpYear", bpYear);
		StudBonusPenaltyInit.put("bpMonth", bpMonth);
		StudBonusPenaltyInit.put("bpDay", bpDay);
		StudBonusPenaltyInit.put("studentNo", studentNo);
		
		session.setAttribute("StudBonusPenaltyInit", StudBonusPenaltyInit);

		
		aForm.set("bpYear", "");
		aForm.set("bpMonth", "");
		aForm.set("bpDay", "");
		aForm.set("studentNo", "");
		aForm.initialize(mapping);
		//log.debug("After ReQuery tfYear=" + aForm.getString("tfYear"));
		return mapping.findForward("Main");
	}

	public ActionForward deservedDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		List<Desd> selDesds = getBonusPenaltySelectedList(request);	//Records selected for delete
		Toolket.setAllCheckboxCookie(response, "BonusPenaltyDelete", selDesds.size());	//Set cookie of record count for delete
		HttpSession session = request.getSession(false);
		session.setAttribute("StudBonusPenaltyDelete", selDesds);
		setContentPage(session, "studaffair/StudBonusPenaltyDelete.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
		
	}
	
	public ActionForward deservedDelConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(false);
		List<Desd> selDesds = (List<Desd>)session.getAttribute("StudBonusPenaltyDelete");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		List<Desd> undeletedRecs = sm.delStudBonusPenalty(selDesds, Toolket.getBundle(request, "messages.studaffair"));
		session.removeAttribute("StudBonusPenaltyDelete");

		if(!undeletedRecs.isEmpty()){
			for(Desd desd:selDesds){
				for(Desd undelete:undeletedRecs){
					if(desd.getOid()==undelete.getOid()){
						selDesds.remove(desd);
						break;
					}
				}
			}
		}
		
		ActionMessages msgs = new ActionMessages();
		double desdScore = 0d;
		String studentNo="";
		for(Desd desd:selDesds){
			studentNo = desd.getStudentNo();
			desdScore = sm.calDesdScoreByStudent(studentNo);
			//msgs = sm.modifyJustDesdScore(studentNo, desdScore);
			
			//取代sm.modifyJustDilgScore(studentNo, justScore)該元件執行有誤  Leo20120307
			CourseManager manager = (CourseManager) getBean("courseManager");
			String sqlstudent_no = manager.ezGetString(
					"Select student_no From just Where student_no='" + studentNo + "' ");
			double SeltotalScore = Double.parseDouble(
					manager.ezGetString("Select total_score From just Where student_no='" + studentNo + "' "));
			double totalScore = SeltotalScore+desdScore;			
			if(sqlstudent_no.equals("")){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.MessageN1", "找不到[" + studentNo + "]該學生的操行成績!"));
			}else{				
				manager.executeSql("Update just Set total_score="+totalScore+" Where student_no='"+studentNo+"'");
			}
			//=======================================================================
			
			if(!msgs.isEmpty()){
				messages.add(msgs);
			}
		}
		if(messages.isEmpty()){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			"Message.DeleteSuccessful"));
		}
		
		saveMessages(request, msgs);
		// no undeleteScores will happen even if delete failure
		if (undeletedRecs.size() == 0) {
			session.removeAttribute("StudBonusPenaltyList");
			Map initMap = (Map)session.getAttribute("StudBonusPenaltyInit");
			DynaActionForm dynForm = (DynaActionForm)form;
			dynForm.set("bpYear", initMap.get("bpYear"));
			dynForm.set("bpMonth", initMap.get("bpMonth"));
			dynForm.set("bpDay", initMap.get("bpDay"));
			dynForm.set("studentNo", initMap.get("studentNo"));
			log.debug("delete Desd success, bpYear=" + dynForm.getString("bpYear"));
			
			return deservedQuery(mapping, (ActionForm)dynForm, request, response);
		} else {
			session.setAttribute("UndeletedBonusPenaltys", undeletedRecs);
			setContentPage(session, "studaffair/StudBonusPenaltyUndelete.jsp");
			return mapping.findForward("Main");
		}
		
	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		request.getSession(false).removeAttribute("StudBonusPenaltyDelete");
		setContentPage(request.getSession(false), "studaffair/StudBonusPenalty.jsp");
		return mapping.findForward("Main");
	}

	
	public ActionForward deservedModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		List<Desd> selDesds = getBonusPenaltySelectedList(request);
		log.debug("StudBonusPenalty==>selDesds.size():" + selDesds.size());
		Desd desd;
		List editList = new ArrayList();
		
		//Multiple Record Modify
		/*for(Iterator<Dilg> dilgIter=selDilgs.iterator(); dilgIter.hasNext();) {
			dilg = dilgIter.next();
			Map dilgMap = new HashMap();
			setDilgInitValue(dilg, dilgMap);
			editList.add(dilgMap);
		}
		*/
		desd = selDesds.get(0);
		Map initValue = new HashMap();
		
		initValue.put("mode", "Modify");
		initValue.put("studentNo", desd.getStudentNo());
		initValue.put("studentName", desd.getStudentName());
		initValue.put("departClass", desd.getDepartClass());
		initValue.put("deptClassName", desd.getDeptClassName());
	
		//List tfTypeList = Global.TimeOffList;
		//session.setAttribute("StudTimeOffType", tfTypeList);
		
		//setDilgInitValue(dilg, initValue);
		session.setAttribute("StudBonusPenaltyEditInfo", initValue);
		session.setAttribute("StudBonusPenaltyInEdit", selDesds);
		setContentPage(session, "studaffair/StudBonusPenaltyModify.jsp");
		return mapping.findForward("Main");

	}

	
	//Private Method Here ============================>>
	private List getBonusPenaltySelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "StudBonusPenalty");
		List<Desd> desds = (List<Desd>)session.getAttribute("StudBonusPenaltyList");
		List<Desd> selDesds = new ArrayList<Desd>();
		Desd desd;
		StudAffairDAO dao =(StudAffairDAO)getBean("studAffairDAO");
		for (Iterator<Desd> desdIter = desds.iterator(); desdIter.hasNext();) {
			desd = desdIter.next();
			if (Toolket.isValueInCookie(desd.getOid().toString(), oids)) {
				dao.reload(desd);
				selDesds.add(desd);
			}
		}
		return selDesds;
	}
	
	private List getBonusPenaltyDeletedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "StudBonusPenaltyDel");
		List<Desd> desds = (List<Desd>)session.getAttribute("StudBonusPenaltyDelete");
		List<Desd> selDesds = new ArrayList<Desd>();
		Desd desd;
		for (Iterator<Desd> desdIter = desds.iterator(); desdIter.hasNext();) {
			desd = desdIter.next();
			if (Toolket.isValueInCookie(desd.getOid().toString(), oids)) {
				selDesds.add(desd);
			}
		}
		return selDesds;
	}
	

}
