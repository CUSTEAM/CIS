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
import tw.edu.chit.model.AmsDocApply;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.StudDocApply;
import tw.edu.chit.model.StudDocDetail;
import tw.edu.chit.model.StudDocExamine;
import tw.edu.chit.model.StudDocUpload;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.PeriodInfo;
import tw.edu.chit.model.domain.UploadFileInfo;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AmsManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class StudAbsenceExamAction  extends BaseLookupDispatchAction{
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
	 * 查核類別:	SD111:學生請假導師審核規則
	 * 		 	SD112:學生請假系教官審核規則
	 * 			SD113:學生請假生輔組審核規則
	 * 			SD115:學生請假台北進修進院學務組審核規則
	 * 			SD118:學生請假台北進修進院主任審核規則
	 * 			SD119:學生請假學務長或新竹分部主任審核規則
	 * 
	 * 假單狀態 送核中[A]:(1)導師核准[2] (2)系教官核准[3] (3)學務單位核准[4] (4)學務長或分部主任核准[5]
	 * 		 未送核[N]
	 * 		 已核准[1]
	 * 		 未核准[0]:(1)導師未核准[6] (2)系教官未核准[7] (3)學務單位未核准[8] (4)學務長或分部主任未核准[9]
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
		
		/*	classInCharge refered to UserCredential
		 * 	AuthorityOnTutor: 86 , 導師
		 * 	AuthorityOnChairman: 87 , 系主任
		 *  AuthorityOnDrillmaster: 88 , 教官
		 */
		
		List<Clazz> clazzT = new ArrayList<Clazz>();
		
		List<StudDocApply> docs = new ArrayList<StudDocApply>();
		if(ruleNo.equals("SD111")){
			//學生請假導師審核規則;查詢導師負責班級
			clazzT = mm.findClassInChargeByMemberAuthority(empOid, UserCredential.AuthorityOnTutor);
		}else if(ruleNo.equals("SD112")){
			//SD112:學生請假系教官審核規則
			clazzT = mm.findClassInChargeByMemberAuthority(empOid, UserCredential.AuthorityOnDeptDrillmaster);
		}else if(ruleNo.equals("SD113") || ruleNo.equals("SD115") || ruleNo.equals("SD118")
				 || ruleNo.equals("SD119")){
			clazzT = mm.findClassInChargeByMemberAuthority(empOid, UserCredential.AuthorityOnStudAffair);
		}
		
		List<StudDocApply> doctmp = new ArrayList<StudDocApply>();
		for(Clazz clazz:clazzT){
			docs.addAll(sm.getAbsenceApply4ExamByClazz(clazz.getClassNo(), ruleNo));
		}
		String statusName = "";
		for(StudDocApply doc:docs){
			StudDocExamine examine = sm.getStudDocLastExamine(doc.getOid());
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
			doc.setAskLeaveName(Toolket.getTimeOff(doc.getAskLeaveType()).substring(1));
			doc.setDeptClassName(Toolket.getClassFullName(doc.getDepartClass()));
			
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
		
		List<StudDocApply> docExamed = sm.getAbsenceApplyExamedByRule(ruleNo, user.getMember().getIdno());
		for(StudDocApply docd:docExamed){
			docd.setAskLeaveName(Toolket.getTimeOff(docd.getAskLeaveType()).substring(1));
			docd.setDeptClassName(Toolket.getClassFullName(docd.getDepartClass()));
		}
		Toolket.resetCheckboxCookie(response, "StudDocExam");
		Map init = new HashMap();
		init.put("examMode", ruleNo);
		session.setAttribute("StudDocExamInit", init);
		session.setAttribute("StudDocs4Exam", docs);
		session.setAttribute("StudDocsExamed", docExamed);
		setContentPage(session, "studaffair/StudDocExamine.jsp");
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
						
		Map initValue = new HashMap();
		
		initValue.put("examMode", ruleNo);
						
				
		StudDocApply doc = this.getDocSelected(request);
		if(doc != null){
			String sdate = "";
			String edate = "";
			//List<StudDocUpload> files = sm.getAbsenceDocAttach(doc.getOid());
			//List<UploadFileInfo> finfos = new ArrayList<UploadFileInfo>();
			//StringBuffer buf = new StringBuffer();
			//if(!files.isEmpty()){
				//for(StudDocUpload upload:files){
					//UploadFileInfo finfo = new UploadFileInfo(upload.getOid(), upload.getFileName());
					//finfos.add(finfo);
					//buf.append(upload.getOid()).append("|");
				//}
			//}
			//doc.setUploadFileInfo(finfos);
				
			//將請假節次資料轉換為 PeriodInfo 以方便 JSP 處理
			Calendar scal = Calendar.getInstance();
			Calendar ecal = Calendar.getInstance();
			scal.setTime(doc.getStartDate());
			ecal.setTime(doc.getEndDate());
			doc.setSimpleStartDate(Toolket.Date2Str(scal.getTime()));
			doc.setSimpleEndDate(Toolket.Date2Str(ecal.getTime()));
			List<PeriodInfo> details = new ArrayList<PeriodInfo>();
			String studentNo = doc.getStudentNo();
			while(scal.compareTo(ecal)<=0){
					List<StudDocDetail> docds = sm.getStudDocDetailByDate(studentNo, Toolket.FullDate2Str(scal.getTime()));
					String[] infos = new String[]{"","","","","","","","","","","","","","",""};
					if(!docds.isEmpty()){
						for(StudDocDetail docd:docds){
							if(docd.getDocOid().intValue()==doc.getOid().intValue())
								infos[docd.getPeriod()-1] = "1";
						}
					}
					PeriodInfo pinfo = new PeriodInfo("", Toolket.Date2Str(scal.getTime()), infos);
					details.add(pinfo);
					scal.add(Calendar.DATE, 1);
				}
				doc.setDetails(details);
			
			session.setAttribute("StudDocInExam", doc);
			session.setAttribute("StudDocExamInit", initValue);
			setContentPage(request, "studaffair/StudDocInExam.jsp");
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請勾選您要審核的假單!"));
			saveErrors(request, messages);
			setContentPage(request, "studaffair/StudDocExamine.jsp");
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
		
		StudDocApply doc = sm.findStudDocApplyByOid(Integer.parseInt(docOid));
		
		if(doc != null){
			if(status.equals("0") && description.trim().equals("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","請詳填未核准原因!"));
				saveErrors(request, messages);
				setContentPage(request, "studaffair/StudDocInExam.jsp");
				return mapping.findForward("Main");
			}
			messages = sm.setStudDocExamStatus(doc, ruleNo, status, description, user.getMember().getIdno());
				
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
		
		Toolket.resetCheckboxCookie(response, "StudDocExam");
		session.removeAttribute("StudDocInExam");
		session.removeAttribute("StudDocs4Exam");
		session.removeAttribute("StudDocsExamed");
		return unspecified(mapping, (ActionForm)dynForm, request, response);
	}

	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		
		setContentPage(session, "studaffair/StudDocExamine.jsp");
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
		
		if(examMode.equals("SD111")){	//導師審核
			return mapping.findForward("back2Tutor");		
		}
		return mapping.findForward("back");		
	}


	
	private StudDocApply getDocSelected(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = "";
		StudAffairDAO dao =(StudAffairDAO)getBean("studAffairDAO");
		List<StudDocApply> docs = new ArrayList<StudDocApply>();
		
		oids = Toolket.getSelectedIndexFromCookie(request, "StudDocExam");
		docs = (List<StudDocApply>)session.getAttribute("StudDocs4Exam");
		StudDocApply selDoc = null;
		for (StudDocApply doc:docs ) {
			if (Toolket.isValueInCookie(doc.getOid().toString(), oids)) {
				dao.reload(doc);
				selDoc = doc;
			}
		}
		return selDoc;
	}


	
}
