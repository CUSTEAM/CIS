package tw.edu.chit.struts.action.studaffair;

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

import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.StudDocApply;
import tw.edu.chit.model.StudDocDetail;
import tw.edu.chit.model.StudDocExamine;
import tw.edu.chit.model.StudDocUpload;
import tw.edu.chit.model.StudPublicDocExam;
import tw.edu.chit.model.StudPublicDocUpload;
import tw.edu.chit.model.StudPublicLeave;
import tw.edu.chit.model.domain.PeriodInfo;
import tw.edu.chit.model.domain.UploadFileInfo;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class StudPublicAbsenceExamAction  extends BaseLookupDispatchAction{
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Examine",			"view");
		map.put("ExamOK",			"setExam");
		map.put("Reject",			"setExam");
		map.put("Cancel", 			"cancel");
		map.put("Back", 			"back");
		return map;
	}

	/**
	 * 查核類別:	PB111:公假系主任審核規則
	 * 		 	PB112:公假學務組審核規則
	 * 			PB113:公假假學務長或分部主任審核規則
	 * 
	 * 假單狀態 送核中[A]:(1)系主任核准[B] (2)學務單位核准[4] (3)學務長或分部主任核准[5]
	 * 		 未送核[N]
	 * 		 已核准[1]
	 * 		 未核准[0]:(1)系主任未核准[C] (2)學務單位未核准[8] (3)學務長或分部主任未核准[9]
	 */

	public ActionForward unspecified(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response){

		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		String ruleNo = dynForm.getString("examMode");
						
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		int empOid = user.getMember().getOid();
		
		/*	classInCharge referred to UserCredential
		 * 	Authority On Chairman or DeptAssistant: 87 , 系主任(系助理)
		 * 
		 */
		
		List<Clazz> clazzT = new ArrayList<Clazz>();
		List<Map> deptInCharge = new ArrayList<Map>();
		
		List<StudPublicLeave> docs = new ArrayList<StudPublicLeave>();
		List<Map> depts = Toolket.getCollegeDepartment(false);
		
		if(ruleNo.equals("PB111")){
			//請公假系主任審核規則;查詢系主任負責班級,, 2010/07/05 全校學務會議修改,公假單原由系主任審核改由導師初審
			//deptInCharge = sm.findDeptInCharge(empOid);
			clazzT = mm.findClassInChargeByMemberAuthority(empOid, UserCredential.AuthorityOnTutor);
		}else if(ruleNo.equals("PB112") || ruleNo.equals("PB113")){
			clazzT = mm.findClassInChargeByMemberAuthority(empOid, UserCredential.AuthorityOnStudAffair);
		}
		
		List<StudPublicLeave> doctmp = new ArrayList<StudPublicLeave>();
		for(Clazz clazz:clazzT){
			docs.addAll(sm.getPublicDoc4ExamByClazz(clazz.getClassNo(), ruleNo));
		}
		String statusName = "";
		Empl applier = null;
		for(StudPublicLeave doc:docs){
			applier = mm.findEmplByIdno(doc.getApplyId());
			if(applier != null){
				doc.setApplierName(applier.getCname());
				doc.setApplierUnit(mm.findEmplUnitByIdno(doc.getApplyId()));
			}else{
				doc.setApplierName("");
				doc.setApplierUnit("");
			}
			StudPublicDocExam examine = sm.getStudPublicDocLastExam(doc.getOid());
			statusName = sm.getExamineStatusName(doc.getStatus());
			if(examine!=null){
				if(!examine.getExamStatus().equalsIgnoreCase(doc.getStatus())){
					statusName += sm.getExamineStatusName(examine.getExamStatus());
				}
				doc.setProcessDate(examine.getExamDate());
			}else{
				doc.setProcessDate(null);
			}

			doc.setStatusName(statusName);
			doc.setAskLeaveName(Toolket.getTimeOff("6").substring(1));
			doc.setDeptClassName(Toolket.getClassFullName(doc.getDepartment()));
			
			/*
			List<StudDocUpload> files = sm.getAbsenceDocAttach(doc.getOid());
			List<UploadFileInfo> finfos = new ArrayList<UploadFileInfo>();
			if(!files.isEmpty()){
				for(StudDocUpload upload:files){
					UploadFileInfo finfo = new UploadFileInfo(upload.getOid(), upload.getFileName());
					finfos.add(finfo);
				}
				doc.setUploadFileInfo(finfos);
			}else{
				doc.setUploadFileInfo(null);
			}
			*/
		}
		
		List<StudPublicLeave> docExamed = sm.getPublicDocExamedByRule(ruleNo, user.getMember().getIdno());
		for(StudPublicLeave docd:docExamed){
			//docd.setAskLeaveName(Toolket.getTimeOff(docd.getAskLeaveType()).substring(1));
			docd.setDeptClassName(Toolket.getClassFullName(docd.getDepartment()));
			applier = mm.findEmplByIdno(docd.getApplyId());
			if(applier != null){
				docd.setApplierName(applier.getCname());
				docd.setApplierUnit(mm.findEmplUnitByIdno(docd.getApplyId()));
			}else{
				docd.setApplierName("");
				docd.setApplierUnit("");
			}
		}
		Toolket.resetCheckboxCookie(response, "StudPublicDocExam");
		Map init = new HashMap();
		init.put("examMode", ruleNo);
		session.setAttribute("StudPublicDocExamInit", init);
		session.setAttribute("StudPublicDocs4Exam", docs);
		session.setAttribute("StudPublicDocsExamed", docExamed);
		setContentPage(session, "studaffair/StudPublicDocExamine.jsp");
		return mapping.findForward("Main");				
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map DocApplyInit = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		ActionMessages messages = new ActionMessages();
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		String ruleNo = dynForm.getString("examMode");
		
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
						
		Map initValue = new HashMap();
		
		initValue.put("examMode", ruleNo);
						
				
		StudPublicLeave doc = this.getDocSelected(request);
		if(doc != null){
			Empl applier = mm.findEmplByIdno(doc.getApplyId());
			if(applier != null){
				doc.setApplierName(applier.getCname());
				doc.setApplierUnit(mm.findEmplUnitByIdno(doc.getApplyId()));
			}else{
				doc.setApplierName("");
				doc.setApplierUnit("");
			}
			String sdate = "";
			String edate = "";
			List<StudPublicDocUpload> files = sm.getPublicDocAttach(doc.getOid());
			List<UploadFileInfo> finfos = new ArrayList<UploadFileInfo>();
			StringBuffer buf = new StringBuffer();
			if(!files.isEmpty()){
				for(StudPublicDocUpload upload:files){
					UploadFileInfo finfo = new UploadFileInfo(upload.getOid(), upload.getFileName());
					finfos.add(finfo);
					buf.append(upload.getOid()).append("|");
				}
			}
			doc.setUploadFileInfo(finfos);
				
			//取得公假學生資料
			List<StudDocApply> stdocs = sm.getPublicDocStuds(doc.getOid());
			for(StudDocApply stdoc:stdocs){
				stdoc.setDeptClassName(Toolket.getClassFullName(stdoc.getDepartClass()));

				//-- 暫時不顯示 ---- 將請假節次資料轉換為 PeriodInfo 以方便 JSP 處理
				/*
				Calendar scal = Calendar.getInstance();
				Calendar ecal = Calendar.getInstance();
				scal.setTime(doc.getStartDate());
				ecal.setTime(doc.getEndDate());
				stdoc.setSimpleStartDate(Toolket.Date2Str(scal.getTime()));
				stdoc.setSimpleEndDate(Toolket.Date2Str(ecal.getTime()));
				List<PeriodInfo> details = new ArrayList<PeriodInfo>();
				while(scal.compareTo(ecal)<=0){
					List<StudDocDetail> docds = sm.getStudDocDetailByDate(studentNo, Toolket.FullDate2Str(scal.getTime()));
					String[] infos = new String[]{"","","","","","","","","","","","","","",""};
					if(!docds.isEmpty()){
						for(StudDocDetail docd:docds){
							infos[docd.getPeriod()] = "1";
						}
					}
					PeriodInfo pinfo = new PeriodInfo("", Toolket.Date2Str(scal.getTime()), infos);
					details.add(pinfo);
					scal.add(Calendar.DATE, 1);
				}
				stdoc.setDetails(details);
				*/
			}
			doc.setStudDoc(stdocs);
			//set simple date yy-mm-dd
			doc.setSimpleStartDate(Toolket.Date2Str(doc.getStartDate()));
			doc.setSimpleEndDate(Toolket.Date2Str(doc.getEndDate()));
			
			session.setAttribute("StudPublicDocInExam", doc);
			session.setAttribute("StudPublicDocExamInit", initValue);
			setContentPage(session, "studaffair/StudPublicDocInExam.jsp");
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請勾選您要審核的假單!"));
			saveErrors(request, messages);
			setContentPage(session, "studaffair/StudPublicDocExamine.jsp");
		}
		
		return mapping.findForward("Main");
	}
	

	public ActionForward setExam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		Calendar today = Calendar.getInstance();
		Calendar tonight = Calendar.getInstance();
		
		UserCredential user = (UserCredential)session.getAttribute("Credential");
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		ActionMessages messages = new ActionMessages();
		String ruleNo = dynForm.getString("examMode");
		String docOid = dynForm.getString("docOid");
		String description = dynForm.getString("description");
		String status = dynForm.getString("status");
		
		StudPublicLeave doc = sm.findStudPublicLeaveByOid(Integer.parseInt(docOid));
		
		if(doc != null){
			if(status.equals("0") && description.trim().equals("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請詳填未核准原因!"));
				saveErrors(request, messages);
				setContentPage(request, "studaffair/StudPublicDocInExam.jsp");
				return mapping.findForward("Main");
			}
			messages = sm.setStudPublicDocExamStatus(doc, ruleNo, status, description, user.getMember().getIdno());
			
			
			//取代sm.modifyJustDilgScore(studentNo, justScore)該元件執行有誤  Leo20120308 ==================>>>>>>>>>>
			List<StudDocApply> stdocs = sm.getPublicDocStuds(doc.getOid());
			double dilgScore = 0d;
			for(StudDocApply stdoc:stdocs){				
			dilgScore = sm.calDilgScoreByStudent(stdoc.getStudentNo(), "0");
			CourseManager manager = (CourseManager) getBean("courseManager");
			String sqlstudent_no = manager.ezGetString("Select student_no From just Where student_no='" + stdoc.getStudentNo() + "' ");
			//double dilgScore = Double.parseDouble(manager.ezGetString("Select total_score From just Where student_no='" + stdoc.getStudentNo() + "' "));
			double SeltotalScore = Double.parseDouble(manager.ezGetString("Select total_score From just Where student_no='" + stdoc.getStudentNo() + "' "));
			double totalScore = SeltotalScore+dilgScore;
			//String myScore = Double.toString(totalScore);
			int myScore= (int)(totalScore+0.5);
			if(sqlstudent_no.equals("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.MessageN1", "找不到[" + stdoc.getStudentNo() + "]該學生的操行成績!"));
			}else{
				//System.out.println(myScore);
				manager.executeSql("Update just Set total_score="+myScore+" Where student_no='"+stdoc.getStudentNo()+"'");
			}
			//System.out.println("Leo_OK");
			}
			//=======================================================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	
			if(!messages.isEmpty()){
				saveErrors(request, messages);
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","審查完成!"));
				saveMessages(request, messages);
			}
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","系統有誤! 找不到該筆請假資料!"));
			saveErrors(request, messages);
		}
		
		Toolket.resetCheckboxCookie(response, "StudPublicDocExam");
		session.removeAttribute("StudPublicDocInExam");
		session.removeAttribute("StudPublicDocs4Exam");
		session.removeAttribute("StudPublicDocsExamed");
		return unspecified(mapping, (ActionForm)dynForm, request, response);
	}

	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		
		setContentPage(session, "studaffair/StudPublicDocExamine.jsp");
		return mapping.findForward("Main");		
	}

	public ActionForward back(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		String examMode = dynForm.getString("examMode");
		
		if(examMode.equals("PB111")){	//導師審核
			return mapping.findForward("back2Tutor");		
		}
		return mapping.findForward("back");		
	}


	
	private StudPublicLeave getDocSelected(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = "";
		StudAffairDAO dao =(StudAffairDAO)getBean("studAffairDAO");
		List<StudPublicLeave> docs = new ArrayList<StudPublicLeave>();
		
		oids = Toolket.getSelectedIndexFromCookie(request, "StudPublicDocExam");
		docs = (List<StudPublicLeave>)session.getAttribute("StudPublicDocs4Exam");
		StudPublicLeave selDoc = null;
		for (StudPublicLeave doc:docs ) {
			if (Toolket.isValueInCookie(doc.getOid().toString(), oids)) {
				dao.reload(doc);
				selDoc = doc;
			}
		}
		return selDoc;
	}


}
