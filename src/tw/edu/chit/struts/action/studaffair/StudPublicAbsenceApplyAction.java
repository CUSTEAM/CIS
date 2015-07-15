package tw.edu.chit.struts.action.studaffair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
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
import tw.edu.chit.model.StudDocApply;
import tw.edu.chit.model.StudDocDetail;
import tw.edu.chit.model.StudDocExamine;
import tw.edu.chit.model.StudDocUpload;
import tw.edu.chit.model.StudPublicDocExam;
import tw.edu.chit.model.StudPublicDocUpload;
import tw.edu.chit.model.StudPublicLeave;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.PeriodInfo;
import tw.edu.chit.model.domain.UploadFileInfo;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class StudPublicAbsenceApplyAction  extends BaseLookupDispatchAction {
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query",			"query");
		map.put("Create",			"add");
		map.put("Modify",			"modify");
		map.put("Delete",			"delete");
		map.put("DeleteConfirm", 	"delConfirm");
		map.put("View",				"view");
		map.put("Send4Examine", 	"send4Examine");
		map.put("CancelExamine", 	"cancelExamine");
		map.put("Save",				"save");
		map.put("Cancel", 			"cancel");
		map.put("Back", 			"back");
		return map;
	}

	/**
	 * 假單狀態 送核中[A]:(1)系主任核准[2] (2)學務組核准[3]
	 * 		 未送核[N]
	 * 		 已核准[1]
	 * 		 未核准[0]:(1)系主任未核准[6] (2)學務組未核准[7]
	 * 
	 * 2010/07/05 全校學務會議修改,公假單原由系主任審核改由導師初審
	 * 原來 department 欄位改用來儲存班級, 新增公假單時,依照學生所在班級自動分為多張公假單
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);

		UserCredential user = getUserCredential(session);
		String idno = user.getMember().getIdno();
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		List<StudPublicLeave> docs = sm.findStudPublicLeavesByIdno(idno);

		String statusName = "";
		for(ListIterator<StudPublicLeave> DocIter=docs.listIterator(); DocIter.hasNext();){
			StudPublicLeave doc = DocIter.next();
			statusName = "";
			//is absence apply expired ?
			Calendar expireDate = Calendar.getInstance();
			expireDate.setTime(doc.getEndDate());
			expireDate.set(Calendar.HOUR_OF_DAY, 23);
			expireDate.set(Calendar.MINUTE, 59);
			expireDate.set(Calendar.SECOND, 59);
			expireDate.add(Calendar.DATE, 8);
			if(expireDate.compareTo(Calendar.getInstance()) >= 0){
				doc.setExpired(false);
			}else{
				doc.setExpired(true);
			}
			//set doc status name
			if(!doc.getStatus().equals("D")){
				//doc.setDeptClassName(Toolket.getDept(doc.getDepartment()));
				doc.setDeptClassName(Toolket.getClassFullName(doc.getDepartment()));
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
				
				/* Mysql BLOB query 會很慢,這裡可以不用
				List<StudPublicDocUpload> files = sm.getPublicDocAttach(doc.getOid());
				List<UploadFileInfo> finfos = new ArrayList<UploadFileInfo>();
				if(!files.isEmpty()){
					for(StudPublicDocUpload upload:files){
						UploadFileInfo finfo = new UploadFileInfo(upload.getOid(), upload.getFileName());
						finfos.add(finfo);
					}
					doc.setUploadFileInfo(finfos);
				}else{
					doc.setUploadFileInfo(null);
				}
				*/
			}else{	//Doc had deleted!
				DocIter.remove();
			}
		}
		Toolket.resetCheckboxCookie(response, "StudPubDoc");
		session.setAttribute("StudPublicDocs", docs);
		setContentPage(session, "studaffair/StudPublicAbsenceApply.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);

		UserCredential user = getUserCredential(session);
		String idno = user.getMember().getIdno();
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		List<StudPublicLeave> docs = sm.findStudPublicLeavesByIdno(idno);

		String statusName = "";
		for(ListIterator<StudPublicLeave> DocIter=docs.listIterator(); DocIter.hasNext();){
			StudPublicLeave doc = DocIter.next();
			statusName = "";
			//is absence apply expired ?
			Calendar expireDate = Calendar.getInstance();
			expireDate.setTime(doc.getEndDate());
			expireDate.set(Calendar.HOUR_OF_DAY, 23);
			expireDate.set(Calendar.MINUTE, 59);
			expireDate.set(Calendar.SECOND, 59);
			expireDate.add(Calendar.DATE, 8);
			if(expireDate.compareTo(Calendar.getInstance()) >= 0){
				doc.setExpired(false);
			}else{
				doc.setExpired(true);
			}
			//set doc status name
			if(!doc.getStatus().equals("D")){
				doc.setDeptClassName(Toolket.getClassFullName(doc.getDepartment()));
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
				
				/* Mysql BLOB query 會很慢,這裡可以不用
				List<StudPublicDocUpload> files = sm.getPublicDocAttach(doc.getOid());
				List<UploadFileInfo> finfos = new ArrayList<UploadFileInfo>();
				if(!files.isEmpty()){
					for(StudPublicDocUpload upload:files){
						UploadFileInfo finfo = new UploadFileInfo(upload.getOid(), upload.getFileName());
						finfos.add(finfo);
					}
					doc.setUploadFileInfo(finfos);
				}else{
					doc.setUploadFileInfo(null);
				}
				*/
			}else{	//Doc had deleted!
				DocIter.remove();
			}
		}
		Toolket.resetCheckboxCookie(response, "StudPubDoc");
		session.setAttribute("schoolYear", syear);
		session.setAttribute("StudPublicDocs", docs);
		setContentPage(request, "studaffair/StudPublicAbsenceApply.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map DocApplyInit = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
						
		DocApplyInit.put("opmode", "add");
		
		//List<Map> depts = Toolket.getCollegeDepartment(false);
		//session.setAttribute("depts", depts);
				
		session.setAttribute("StudPubDocEditInit", DocApplyInit);
		setContentPage(request, "studaffair/StudPublicDocEdit.jsp");
		return mapping.findForward("Main");

	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map DocApplyInit = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		ActionMessages messages = new ActionMessages();
		
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		
		DocApplyInit.put("opmode", "modify");
		
		List<Map> depts = Toolket.getCollegeDepartment(false);
		session.setAttribute("depts", depts);
				
		session.removeAttribute("DocError");
		//session.removeAttribute("DocApplyInEdit");
		
		List<StudPublicLeave> docs = (List<StudPublicLeave>)this.getDocSelectedList(request);
		if(!docs.isEmpty()){
			StudPublicLeave doc = docs.get(0);
			StudPublicDocExam examine = sm.getStudPublicDocLastExam(doc.getOid());
			if(doc.getStatus().equalsIgnoreCase("A") || doc.getStatus().equalsIgnoreCase("1") ||
					doc.getStatus().equalsIgnoreCase("D")){
				if(doc.getStatus().equalsIgnoreCase("A")){
					/*
					if(examine.getExamStatus().equals(IConstants.STUDDocCodeTutorOK) || 
							examine.getExamStatus().equals(IConstants.STUDDocCodeDrillmasterOK) || 
							examine.getExamStatus().equals(IConstants.STUDDocCodeStudaffairOK)){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","假單已經進入審核流程，請勿修改!"));
					}
					*/
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","假單已經進入審核流程，請取消審核後再修改!"));

				}else if(doc.getStatus().equalsIgnoreCase("1")){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","假單已經核准了，不可修改!"));
				}else if(doc.getStatus().equalsIgnoreCase("D")){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","假單已刪除，不可修改!"));
				}
				saveErrors(request, messages);
				setContentPage(request, "studaffair/StudPublicAbsenceApply.jsp");
				return mapping.findForward("Main");
			}
			
			doc.setDeptClassName(Toolket.getClassFullName(doc.getDepartment()));
			
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
			if(buf.length()>0){
				doc.setFileOids(buf.substring(0, buf.length()-1));
			}else{
				doc.setFileOids("");
			}
			
			//set simple date yy-mm-dd
			doc.setSimpleStartDate(Toolket.Date2Str(doc.getStartDate()));
			doc.setSimpleEndDate(Toolket.Date2Str(doc.getEndDate()));
			
			//取得公假學生資料
			List<StudDocApply> stdocs = sm.getPublicDocStuds(doc.getOid());
			if(!stdocs.isEmpty()){
				//String[] studTitle = new String[stdocs.size()];
				//int cnt = 0;
				for(StudDocApply stdoc:stdocs){
					stdoc.setDeptClassName(Toolket.getClassFullName(stdoc.getDepartClass()));
					//studTitle[cnt++] =  Toolket.getClassFullName(stdoc.getDepartClass()) + " " + stdoc.getStudentName() +
					//"(" + stdoc.getStudentNo() + ")";
				}
				doc.setStudDoc(stdocs);
			}
			
			session.setAttribute("StudPubDocModifyInit", DocApplyInit);
			session.setAttribute("StudPubDocInEdit", doc);
			setContentPage(request, "studaffair/StudPublicDocModify.jsp");
			
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請勾選您要修改的假單!"));
			saveErrors(request, messages);
			setContentPage(request, "studaffair/StudPublicAbsenceApply.jsp");
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
		String idno = credential.getMember().getIdno();
		
		Map initMap = (Map)session.getAttribute("StudAskLeaveEditInit");

		String opmode = form.getString("opmode");
		String oid = form.getString("oid");
		String reason = form.getString("reason");
		String department = form.getString("department");
		String sdate = form.getString("startDate");
		String edate = form.getString("endDate");
		String startPeriod = form.getString("startPeriod");
		String endPeriod = form.getString("endPeriod");
		String fileOids = form.getString("fileOids");
		//String[] adate = sdate.split("-");
		String memo = form.getString("memo");
		String[] studentNos = form.getStrings("studentNo");
		
		Map fMap = new HashMap();
		fMap.put("reason", reason);
		fMap.put("department", department);
		fMap.put("startDate", sdate);
		fMap.put("endDate", edate);
		fMap.put("startPeriod", startPeriod);
		fMap.put("endPeriod", endPeriod);
		fMap.put("fileOids", fileOids);
		fMap.put("memo", memo);
		String[] studTitle = new String[studentNos.length];
		int cnt = 0;
		for(String studNo:studentNos){
			Student student = Toolket.getStudentByNo(studNo);
			studTitle[cnt++] =  Toolket.getClassFullName(student.getDepartClass()) + " " + student.getStudentName() +
						"(" + studNo + ")";
		}
		fMap.put("studentNos", studentNos);
		fMap.put("studTitle", studTitle);
		
		//check upload file exit !
		List<UploadFileInfo> finfos = new ArrayList<UploadFileInfo>();
		String[] foids = fileOids.split("\\|");
		int foid = 0;
		if(!fileOids.trim().equals("")){
			StudPublicDocUpload upload = null;
			for(int k=0; k<foids.length; k++){
				foid = Integer.parseInt(foids[k]);
				upload = sm.getPublicDocUpload(foid);
				if(upload == null){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","系統找不到您上傳的檔案, oid:" + foid));
				}else{
					UploadFileInfo finfo = new UploadFileInfo(upload.getOid(), upload.getFileName());
					finfos.add(finfo);
				}
			}
		}
		
		//修改模式確認,刪除不需要的上傳檔案(前台刪除)
		if(opmode.equals("modify")){
			boolean isDel = true;
			List<StudPublicDocUpload> uploads = sm.getPublicDocAttach(Integer.parseInt(oid));
			for(StudPublicDocUpload upload:uploads){
				isDel = true;
				for(int k=0; k<foids.length; k++){
					foid = Integer.parseInt(foids[k]);
					if(upload.getOid()==foid){
						isDel = false;
						break;
					}
				}
				if(isDel){
					sm.delPublicDocAttach(upload);
				}
			}
		}
		
		fMap.put("attachs", finfos);
		
		if(reason.trim().equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請輸入公假事由!"));
		}

		if(studentNos.length==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請輸入公假學生!"));
		}
		
		if(sdate.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請點選公假開始日期!"));
		}
		if(edate.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請點選公假結束日期!"));
		}
		
		if(opmode.equals("add")){
  			setContentPage(request, "studaffair/StudPublicDocEdit.jsp");
  			session.setAttribute("StudPubDocEditForm", fMap);
  		}else if(opmode.equals("modify")){
  			setContentPage(request, "studaffair/StudPublicDocModify.jsp");
  			session.setAttribute("StudPubDocModifyForm", fMap);
  		}
		
		if(!messages.isEmpty()){
			saveErrors(request, messages);
			return mapping.findForward("Main");
		}
		
		//驗證輸入&計算請假日數
		Calendar sCal = Calendar.getInstance();
		Calendar eCal = Calendar.getInstance();
		sCal.setTime(Toolket.parseDate(sdate));
		eCal.setTime(Toolket.parseDate(edate));
		int[] dt = Toolket.timeTransfer(sCal, eCal);
		int days = dt[0];
		
		/* 公假暫時不檢查重複假單
		 * 
		List<StudDocDetail> docds = parseForm4DocDetail(form);
		boolean isDuplicate = false;
		Map result = new HashMap();
		
		if(opmode.equals("add")){
			//檢查是否有重複日期的假單
			result = sm.checkPublicDocDuplicate(studentNos, sCal, eCal, startPeriod, endPeriod, 0);
			session.setAttribute("StudAskLeaveEditForm", fMap);
  		}else if(opmode.equals("modify")){
  			result = sm.checkAbsenceApplyDuplicate(studentNo, docds, Integer.parseInt(oid));
  			session.setAttribute("StudAskLeaveModifyForm", fMap);
  		}
		isDuplicate = (Boolean)(result.get("isDupl"));
		if(isDuplicate){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", result.get("msg").toString() + "同一請假期間之假單已存在!請檢查是否重複請假"));
			saveErrors(request, messages);
			return mapping.findForward("Main");
		}
		
		*/

		
		//try {
		messages = sm.saveStudPublicDocApplyByForm(idno, form, days);
  		if(messages.isEmpty()){
  			if(opmode.equals("add")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.CreateSuccessful"));
  			}else if(opmode.equals("modify")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.ModifySuccessful"));
  			}
			session.removeAttribute("StudPubDocEditForm");
			session.removeAttribute("StudPubDocModifyForm");
			session.removeAttribute("StudPubDocInEdit");
			saveMessages(request, messages);

			return query(mapping, (ActionForm)form, request, response);
  		}else{
			saveErrors(request, messages);
  		}
		if(opmode.equals("add")){
  			setContentPage(request, "studaffair/StudPublicDocEdit.jsp");
  		}else if(opmode.equals("modify")){
  			setContentPage(request, "studaffair/StudPublicDocModify.jsp");
  		}
		return mapping.findForward("Main");
	}
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map DocApplyInit = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		ActionMessages messages = new ActionMessages();
		
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
						
		Map initValue = new HashMap();
		
		initValue.put("opmode", "view");
						
				
		List<StudPublicLeave> docs = (List<StudPublicLeave>)this.getDocSelectedList(request);
		if(!docs.isEmpty()){
			String sdate = "";
			String edate = "";
			String statusName = "";
			for(StudPublicLeave doc:docs){				
				
				doc.setDeptClassName(Toolket.getClassFullName(doc.getDepartment()));
				
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
				
				if(buf.length()>0){
					doc.setFileOids(buf.substring(0, buf.length()-1));
				}else{
					doc.setFileOids("");
				}
				
				//set simple date yy-mm-dd
				doc.setSimpleStartDate(Toolket.Date2Str(doc.getStartDate()));
				doc.setSimpleEndDate(Toolket.Date2Str(doc.getEndDate()));
				
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
			}
			
			session.setAttribute("StudPubDocView", docs);
			session.setAttribute("StudPubDocViewInit", initValue);
			setContentPage(request, "studaffair/StudPublicDocView.jsp");
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請勾選您要檢視的假單!"));
			saveErrors(request, messages);
			setContentPage(request, "studaffair/StudPublicAbsenceApply.jsp");
		}
		
		return mapping.findForward("Main");
	}
	

	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map DocApplyInit = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		ActionMessages messages = new ActionMessages();
		
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
						
		Map initValue = new HashMap();
		
		initValue.put("opmode", "delete");
						
				
		List<StudPublicLeave> docs = (List<StudPublicLeave>)this.getDocSelectedList(request);
		if(!docs.isEmpty()){
			String sdate = "";
			String edate = "";
			String statusName = "";

			for(StudPublicLeave doc:docs){
				if(doc.getStatus().equalsIgnoreCase("A") || doc.getStatus().equalsIgnoreCase("1")){
					sdate = Toolket.Date2Str(doc.getStartDate());
					edate = Toolket.Date2Str(doc.getStartDate());
					if(doc.getStatus().equalsIgnoreCase("A")){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1",sdate + "~" + edate + "的公假單已經進入審核流程，請勿刪除!"));
					}else if(doc.getStatus().equalsIgnoreCase("1")){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1",sdate + "~" + edate + "的公假單已經核准了，無法刪除!"));
					}
				}
				if(!messages.isEmpty()){
					saveErrors(request, messages);
					return mapping.findForward("Main");
				}
				
				doc.setDeptClassName(Toolket.getClassFullName(doc.getDepartment()));
				
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
				
				if(buf.length()>0){
					doc.setFileOids(buf.substring(0, buf.length()-1));
				}else{
					doc.setFileOids("");
				}
				
				//set simple date yy-mm-dd
				doc.setSimpleStartDate(Toolket.Date2Str(doc.getStartDate()));
				doc.setSimpleEndDate(Toolket.Date2Str(doc.getEndDate()));
				
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
			}
			
			session.setAttribute("StudPubDocDelete", docs);
			session.setAttribute("StudPubDocDelInit", initValue);
			setContentPage(request, "studaffair/StudPublicDocDelete.jsp");
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請勾選您要刪除的假單!"));
			saveErrors(request, messages);
			setContentPage(request, "studaffair/StudPublicAbsenceApply.jsp");
		}
		
		return mapping.findForward("Main");
	}
	
	public ActionForward delConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		
		List<StudPublicLeave> selDocs = new ArrayList<StudPublicLeave>();
		
		selDocs = (List<StudPublicLeave>)session.getAttribute("StudPubDocDelete");
		
		List<StudPublicLeave> undeletedRecs = sm.delStudPublicDocApply(selDocs);
		session.removeAttribute("StudPubDocDelete");

		// no undelete will happen even if delete failure
		if (undeletedRecs.size() == 0) {
			//session.removeAttribute("StudAskLeaves");
			//Map initMap = (Map)session.getAttribute("StudAskLeaveInit");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			"Message.DeleteSuccessful"));
			saveMessages(request, messages);
		} else {
			StringBuffer msg = new StringBuffer();
			for(StudPublicLeave doc:undeletedRecs){
				msg.append(Toolket.Date2Str(doc.getStartDate()) + " ~ " + Toolket.Date2Str(doc.getEndDate()) + " , ");
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", msg.toString() + "的公假單無法刪除,請重新查詢再執行刪除!"));
			saveErrors(request, messages);
		}
		return query(mapping, (ActionForm)dynForm, request, response);
	}
	

	public ActionForward send4Examine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		
		List<StudPublicLeave> selDocs = new ArrayList<StudPublicLeave>();
		
		List<StudPublicLeave> docs = (List<StudPublicLeave>)this.getDocSelectedList(request);
		if(!docs.isEmpty()){
			StudPublicLeave doc = docs.get(0);
			String status = doc.getStatus();
			if(status.equals(IConstants.STUDDocCodeProcessReject)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","假單處理狀態:未核准,請修改後再送出假單!"));
			}else if(status.equals(IConstants.STUDDocCodeProcessOK)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","假單處理狀態:已核准,無需再送出假單!"));
			}else if(status.equals(IConstants.STUDDocCodeSendOut)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","假單處理狀態:送核中,無需再送出假單!"));
			}
			
			Calendar now = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(doc.getEndDate());
			endCal.set(Calendar.HOUR_OF_DAY, 23);
			endCal.set(Calendar.MINUTE, 59);
			endCal.set(Calendar.SECOND, 59);
			endCal.add(Calendar.DATE, 8);
			if(endCal.compareTo(now) < 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","假單已經超過送核期限(請假迄日8天內)"));
			}
			
			if(!messages.isEmpty()){
				saveErrors(request, messages);
				return mapping.findForward("Main");
			}
			
			doc.setStatus(IConstants.STUDDocCodeSendOut);
			messages = sm.setStudPublicDoc4Examine(doc);
			if(!messages.isEmpty()){
				saveErrors(request, messages);
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "假單已成功送出!"));
				saveMessages(request, messages);
				return query(mapping, (ActionForm)dynForm, request, response);
			}
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請勾選您要送出的假單!"));
			saveErrors(request, messages);
		}
		return mapping.findForward("Main");
	}

	public ActionForward cancelExamine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		
		List<StudPublicLeave> selDocs = new ArrayList<StudPublicLeave>();
		
		List<StudPublicLeave> docs = (List<StudPublicLeave>)this.getDocSelectedList(request);
		if(!docs.isEmpty()){
			StudPublicLeave doc = docs.get(0);
			String status = doc.getStatus();
			if(status.equals(IConstants.STUDDocCodeProcessReject)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","假單處理狀態:未核准,無需取消假單送核!"));
			}else if(status.equals(IConstants.STUDDocCodeProcessOK)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","假單處理狀態:已核准,無法取消假單送核!"));
			}else if(status.equals(IConstants.STUDDocCodeNotSend)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","假單處理狀態:未送核,無需取消假單送核!"));
			}else if(status.equals(IConstants.STUDDocCodeSendOut)){
				StudPublicDocExam doce = sm.getStudPublicDocLastExam(doc.getOid());
				String statusE = doce.getExamStatus();
				if(!statusE.equals(IConstants.STUDDocCodeSendOut)){
					if(statusE.equals(IConstants.STUDDocCodeChairmanOK)){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","假單處理狀態:送核中:系主任已核准,無法取消假單送核!"));
					}else if(statusE.equals(IConstants.STUDDocCodeStudaffairOK)){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","假單處理狀態:送核中:學務單位已核准,無法取消假單送核!"));
					}else if(statusE.equals(IConstants.STUDDocCodeSAFChiefOK)){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","假單處理狀態:送核中:學務長或分部主任已核准,無法取消假單送核!"));
					}
				}
			}
			if(!messages.isEmpty()){
				saveErrors(request, messages);
				return mapping.findForward("Main");
			}
			
			messages = sm.resetStudPublicDoc4Examine(doc);
			if(!messages.isEmpty()){
				saveErrors(request, messages);
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "假單已成功取消送核!"));
				saveMessages(request, messages);
				return query(mapping, (ActionForm)dynForm, request, response);
			}
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請勾選您要取消送核的假單!"));
			saveErrors(request, messages);
		}
		return mapping.findForward("Main");
	}

	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		
		session.removeAttribute("StudPubDocDelete");
		session.removeAttribute("StudPubDocModify");
		session.removeAttribute("StudPubDocEditForm");
		session.removeAttribute("StudPubDocModifyForm");
		session.removeAttribute("StudPubDocInEdit");
		Toolket.resetCheckboxCookie(response, "StudPubDoc");
		setContentPage(session, "studaffair/StudPublicAbsenceApply.jsp");
		
		return query(mapping, (ActionForm)dynForm, request, response);
	}

	public ActionForward back(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		
		setContentPage(request, "studaffair/StudPublicAbsenceApply.jsp");
		return mapping.findForward("Main");		
	}

	//Private Method Here ============================>>
	private List getDocSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = "";
		StudAffairDAO dao =(StudAffairDAO)getBean("studAffairDAO");
		List<StudPublicLeave> docs = new ArrayList<StudPublicLeave>();
		
		oids = Toolket.getSelectedIndexFromCookie(request, "StudPubDoc");
		docs = (List<StudPublicLeave>)session.getAttribute("StudPublicDocs");
		List<StudPublicLeave> selDocs = new ArrayList<StudPublicLeave>();
		for (StudPublicLeave doc:docs ) {
			if (Toolket.isValueInCookie(doc.getOid().toString(), oids)) {
				dao.reload(doc);
				selDocs.add(doc);
			}
		}
		return selDocs;
	}

	private List<StudDocDetail> parseForm4DocDetail(DynaActionForm form){
		List<StudDocDetail> docds = new ArrayList<StudDocDetail>();
		String sdate = form.getString("startDate");
		String edate = form.getString("endDate");
		String[] abs1 = form.getStrings("st1");
		String[] abs2 = form.getStrings("st2");
		String[] abs3 = form.getStrings("st3");
		String[] abs4 = form.getStrings("st4");
		String[] abs5 = form.getStrings("st5");
		String[] abs6 = form.getStrings("st6");
		String[] abs7 = form.getStrings("st7");
		String[] abs8 = form.getStrings("st8");
		String[] abs9 = form.getStrings("st9");
		String[] abs10 = form.getStrings("st10");
		String[] abs11 = form.getStrings("st11");
		String[] abs12 = form.getStrings("st12");
		String[] abs13 = form.getStrings("st13");
		String[] abs14 = form.getStrings("st14");
		String[] abs15 = form.getStrings("st15");
		
		Calendar scal = Calendar.getInstance();
		Calendar ecal = Calendar.getInstance();
		scal.setTime(Toolket.parseDate(sdate));
		ecal.setTime(Toolket.parseDate(edate));
		ecal.set(Calendar.HOUR_OF_DAY, 23);
		
		while(scal.compareTo(ecal)<=0){
			StudDocDetail docd = null;
			for(int i=0; i<abs1.length; i++){
				if(abs1[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)1);
					docds.add(docd);
				}
				if(abs2[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)2);
					docds.add(docd);
				}
				if(abs3[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)3);
					docds.add(docd);
				}
				if(abs4[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)4);
					docds.add(docd);
				}
				if(abs5[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)5);
					docds.add(docd);
				}
				if(abs6[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)6);
					docds.add(docd);
				}
				if(abs7[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)7);
					docds.add(docd);
				}
				if(abs8[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)8);
					docds.add(docd);
				}
				if(abs9[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)9);
					docds.add(docd);
				}
				if(abs10[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)10);
					docds.add(docd);
				}
				if(abs11[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)11);
					docds.add(docd);
				}
				if(abs12[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)12);
					docds.add(docd);
				}
				if(abs13[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)13);
					docds.add(docd);
				}
				if(abs14[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)14);
					docds.add(docd);
				}
				if(abs15[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setAdate(scal.getTime());
					docd.setPeriod((short)15);
					docds.add(docd);
				}
				scal.add(Calendar.DATE, 1);
			}
			
		}
		return docds;
	}

}
