package tw.edu.chit.struts.action.student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.AmsDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Aborigine;
import tw.edu.chit.model.AmsAskLeave;
import tw.edu.chit.model.AmsDocApply;
import tw.edu.chit.model.AmsMeeting;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.StudDocApply;
import tw.edu.chit.model.StudDocApplyBase;
import tw.edu.chit.model.StudDocDetail;
import tw.edu.chit.model.StudDocExamine;
import tw.edu.chit.model.StudDocUpload;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.PeriodInfo;
import tw.edu.chit.model.domain.UploadFileInfo;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.AmsManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class StudAbsenceApplyAction extends BaseLookupDispatchAction {
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
	 * 假單狀態 送核中[A]:(1)導師核准[2] (2)系教官核准[3] (3)學務單位核准[4] (4)學務長或分部主任核准[5]
	 * 		 未送核[N]
	 * 		 已核准[1]
	 * 		 未核准[0]:(1)導師未核准[6] (2)系教官未核准[7] (3)學務單位未核准[8] (4)學務長或分部主任未核准[9]
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);

		Student student = (Student) getUserCredential(session).getStudent();
		student = mm.findStudentByNo(student.getStudentNo());
		student.setDepartClass2(Toolket.getClassFullName(student
				.getDepartClass()));
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		List<StudDocApply> docs = sm.findStudDocApplyByNo(student.getStudentNo());

		String statusName = "";
		for(ListIterator<StudDocApply> DocIter=docs.listIterator(); DocIter.hasNext();){
			StudDocApply doc = DocIter.next();
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
			if(!doc.getStatus().equals("D") && !doc.getAskLeaveType().equals("6")){
				StudDocExamine examine = sm.getStudDocLastExamine(doc.getOid());
				statusName = sm.getExamineStatusName(doc.getStatus());
				if(examine!=null){
					if( !doc.getStatus().equals("1") && !examine.getExamStatus().equalsIgnoreCase(doc.getStatus())){
						statusName += ", " + sm.getExamineStatusName(examine.getExamStatus());
					}
					doc.setProcessDate(examine.getExamDate());
				}else{
					doc.setProcessDate(null);
				}

				doc.setStatusName(statusName);
				doc.setAskLeaveName(Toolket.getTimeOff(doc.getAskLeaveType()).substring(1));
				
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
			}else{	//Doc had deleted!
				DocIter.remove();
			}
		}
		Toolket.resetCheckboxCookie(response, "StudAskLeave");
		session.setAttribute("studentNo", student.getStudentNo());
		session.setAttribute("schoolYear", syear);
		session.setAttribute("StudAskLeaves", docs);
		setContentPage(session, "student/StudAbsenceApply.jsp");
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

		Student student = (Student) getUserCredential(session).getStudent();
		student = mm.findStudentByNo(student.getStudentNo());
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		List<StudDocApply> docs = sm.findStudDocApplyByNo(student.getStudentNo());
		String statusName = "";
		
		for(ListIterator<StudDocApply> DocIter=docs.listIterator(); DocIter.hasNext();){
			StudDocApply doc = DocIter.next();
			if(!doc.getStatus().equals("D") && !doc.getAskLeaveType().equals("6")){	
				StudDocExamine examine = sm.getStudDocLastExamine(doc.getOid());
				statusName = sm.getExamineStatusName(doc.getStatus());
				if(examine!=null){
					//if(!examine.getExamStatus().equalsIgnoreCase(doc.getStatus())){
					if( !doc.getStatus().equals("1") && !examine.getExamStatus().equalsIgnoreCase(doc.getStatus())){
						statusName += sm.getExamineStatusName(examine.getExamStatus());
					}
					doc.setProcessDate(examine.getExamDate());
				}else{
					doc.setProcessDate(null);
				}

				doc.setStatusName(statusName);
				doc.setAskLeaveName(Toolket.getTimeOff(doc.getAskLeaveType()).substring(1));
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
			}else{	//Doc had deleted!
				DocIter.remove();
			}
		}
		
		Toolket.resetCheckboxCookie(response, "StudAskLeave");
		session.setAttribute("studentNo", student.getStudentNo());
		session.setAttribute("schoolYear", syear);
		session.setAttribute("StudAskLeaves", docs);
		setContentPage(request, "student/StudAbsenceApply.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map DocApplyInit = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		ActionMessages messages = new ActionMessages();
		
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
						
		Student student = (Student) getUserCredential(session).getStudent();
		String studentNo = student.getStudentNo();
		
		DocApplyInit.put("opmode", "add");
		
		Calendar now = Calendar.getInstance();
		Calendar[] cals = Toolket.getDateOfWeek(student.getDepartClass().substring(0, 2), 18);
		Calendar[] calss = Toolket.getDateOfWeek(student.getDepartClass().substring(0, 2), 1);
		if(now.compareTo(cals[0]) > 0){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","學期請假期間已截止，如須請假請洽學務單位!"));
		}
		if(now.compareTo(calss[0]) < 0){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","學期尚未開始!"));
		}
		if(!messages.isEmpty()){
			saveErrors(request, messages);
			setContentPage(request, "student/StudAbsenceApply.jsp");
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		
		session.removeAttribute("DocError");
		//session.removeAttribute("DocApplyInEdit");
		List<Code5> tfTypeList = new ArrayList<Code5>();
		tfTypeList.addAll(Global.TimeOffList);
		for(ListIterator<Code5> typeIter=tfTypeList.listIterator(); typeIter.hasNext();){
			//delete useless type
			String idno = typeIter.next().getIdno();
			if(idno.equals("0") || idno.equals("2") ||idno.equals("5") ||idno.equals("6")){
				typeIter.remove();
			}
		}
		session.setAttribute("askLeaveTypes", tfTypeList);
		session.setAttribute("StudAskLeaveEditInit", DocApplyInit);
		setContentPage(request, "student/StudAbsenceEdit.jsp");
		return mapping.findForward("Main");

	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map DocApplyInit = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		ActionMessages messages = new ActionMessages();
		
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		
		Student student = (Student) getUserCredential(session).getStudent();
		String studentNo = student.getStudentNo();
		
		DocApplyInit.put("opmode", "modify");
				
		session.removeAttribute("DocError");
		//session.removeAttribute("DocApplyInEdit");
		
		Calendar now = Calendar.getInstance();
		Calendar[] cals = Toolket.getDateOfWeek(student.getDepartClass().substring(0, 2), 18);
		Calendar[] calss = Toolket.getDateOfWeek(student.getDepartClass().substring(0, 2), 1);
		if(now.compareTo(cals[0]) > 0){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","學期請假期間已截止，如須修改假單請洽學務單位!"));
		}
		if(now.compareTo(calss[0]) < 0){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","學期尚未開始!"));
		}
		if(!messages.isEmpty()){
			saveErrors(request, messages);
			setContentPage(request, "student/StudAbsenceApply.jsp");
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		
		List<StudDocApply> docs = (List<StudDocApply>)this.getDocSelectedList(request);
		if(!docs.isEmpty()){
			StudDocApply doc = docs.get(0);
			StudDocExamine examine = sm.getStudDocLastExamine(doc.getOid());
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
							"MessageN1","假單已經進入審核流程，請勿修改!"));

				}else if(doc.getStatus().equalsIgnoreCase("1")){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","假單已經核准了，不可修改!"));
				}else if(doc.getStatus().equalsIgnoreCase("D")){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","假單已刪除，不可修改!"));
				}else if(doc.getAskLeaveType().equalsIgnoreCase("6")){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","公假假單，不可修改!"));
				}
				saveErrors(request, messages);
				setContentPage(request, "student/StudAbsenceApply.jsp");
				return mapping.findForward("Main");
			}
			List<StudDocUpload> files = sm.getAbsenceDocAttach(doc.getOid());
			List<UploadFileInfo> finfos = new ArrayList<UploadFileInfo>();
			StringBuffer buf = new StringBuffer();
			if(!files.isEmpty()){
				for(StudDocUpload upload:files){
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
			
			List<Code5> tfTypeList = new ArrayList<Code5>();
			tfTypeList.addAll(Global.TimeOffList);
			for(ListIterator<Code5> typeIter=tfTypeList.listIterator(); typeIter.hasNext();){
				//delete useless type
				String idno = typeIter.next().getIdno();
				if(idno.equals("0") || idno.equals("2") ||idno.equals("5") ||idno.equals("6")){
					typeIter.remove();
				}
			}
			session.setAttribute("askLeaveTypes", tfTypeList);
			
			session.setAttribute("StudAskLeaveEditInit", DocApplyInit);
			session.setAttribute("StudAskLeaveInEdit", doc);
			setContentPage(request, "student/StudAbsenceModify.jsp");
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請勾選您要修改的假單!"));
			saveErrors(request, messages);
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
		
		Map initMap = (Map)session.getAttribute("StudAskLeaveEditInit");

		String opmode = form.getString("opmode");
		String oid = form.getString("oid");
		String reason = form.getString("reason");
		String askLeaveType = form.getString("askLeaveType").trim();
		String sdate = form.getString("startDate");
		String edate = form.getString("endDate");
		String fileOids = form.getString("fileOids");
		//String[] adate = sdate.split("-");
		String memo = form.getString("memo");
		String[] studentNos = form.getStrings("studentNo");
		String studentNo = studentNos[0];
		//String[] abs0 = form.getStrings("st0");
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
		
		Map fMap = new HashMap();
		fMap.put("reason", reason);
		fMap.put("askLeaveType", askLeaveType);
		fMap.put("startDate", sdate);
		fMap.put("endDate", edate);
		fMap.put("fileOids", fileOids);
		fMap.put("memo", memo);
		fMap.put("studentNo", studentNo);
		//fMap.put("abs0", abs0);
		fMap.put("abs1", abs1);
		fMap.put("abs2", abs2);
		fMap.put("abs3", abs3);
		fMap.put("abs4", abs4);
		fMap.put("abs5", abs5);
		fMap.put("abs6", abs6);
		fMap.put("abs7", abs7);
		fMap.put("abs8", abs8);
		fMap.put("abs9", abs9);
		fMap.put("abs10", abs10);
		fMap.put("abs11", abs11);
		fMap.put("abs12", abs12);
		fMap.put("abs13", abs13);
		fMap.put("abs14", abs14);
		fMap.put("abs15", abs15);
		
		if(opmode.equals("add")){
  			setContentPage(request, "student/StudAbsenceEdit.jsp");
  		}else if(opmode.equals("modify")){
  			setContentPage(request, "student/StudAbsenceModify.jsp");
  		}
		
		//check upload file exit !
		List<UploadFileInfo> finfos = new ArrayList<UploadFileInfo>();
		String[] foids = fileOids.split("\\|");
		int foid = 0;
		if(!fileOids.trim().equals("")){
			StudDocUpload upload = null;
			for(int k=0; k<foids.length; k++){
				foid = Integer.parseInt(foids[k]);
				upload = sm.getAbsenceDocUpload(foid);
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
			List<StudDocUpload> uploads = sm.getAbsenceDocAttach(Integer.parseInt(oid));
			for(StudDocUpload upload:uploads){
				isDel = true;
				for(int k=0; k<foids.length; k++){
					foid = Integer.parseInt(foids[k]);
					if(upload.getOid()==foid){
						isDel = false;
						break;
					}
				}
				if(isDel){
					sm.delAbsenceDocAttach(upload);
				}
			}
		}
		
		fMap.put("attachs", finfos);
		
		if(reason.trim().equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請輸入請假原因!"));
		}
		
		if(askLeaveType.trim().equals("0") || askLeaveType.trim().equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請選擇假別!"));
		}
		
		
		if(sdate.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請點選請假開始日期!"));
		}
		if(edate.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請點選請假結束日期!"));
		}
		
		//驗證輸入&計算請假日數
		int days = 0;
		for(int i=0; i<abs1.length; i++){
			if(abs1[i].equals("1") || abs2[i].equals("1") ||abs3[i].equals("1") ||abs4[i].equals("1") ||
				abs5[i].equals("1") ||abs6[i].equals("1") ||abs7[i].equals("1") ||abs8[i].equals("1") ||
				abs9[i].equals("1") ||abs10[i].equals("1") ||abs11[i].equals("1") ||abs12[i].equals("1") ||
				abs13[i].equals("1") ||abs14[i].equals("1") ||abs15[i].equals("1")){
				days++;
			}
		}
		if(days == 0){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請勾選您要請假的日期節次!"));
		}

		List<StudDocDetail> docds = parseForm4DocDetail(form);
		boolean isDuplicate = false;
		Map result = new HashMap();
		if(opmode.equals("add")){
			//檢查是否有重複日期的假單
			result = sm.checkAbsenceApplyDuplicate(studentNo, docds, 0);
			session.setAttribute("StudAskLeaveEditForm", fMap);
  		}else if(opmode.equals("modify")){
  			result = sm.checkAbsenceApplyDuplicate(studentNo, docds, Integer.parseInt(oid));
  			session.setAttribute("StudAskLeaveModifyForm", fMap);
  		}
		isDuplicate = (Boolean)(result.get("isDupl"));
		if(isDuplicate){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", result.get("msg").toString() + "同一請假期間之假單已存在!請檢查是否重複請假"));
		}
		
		Calendar now = Calendar.getInstance();
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(Toolket.parseDate(sdate));
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.SECOND, 0);
		startCal.add(Calendar.DATE, 9);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(Toolket.parseDate(edate));
		endCal.set(Calendar.HOUR_OF_DAY, 23);
		endCal.set(Calendar.MINUTE, 59);
		endCal.set(Calendar.SECOND, 59);
		if(endCal.compareTo(startCal) > 0){
			//messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			//		"MessageN1","假單請假開始至結束日期不得超過8天，如需請假超過8天，請洽學務單位以書面方式請假!"));
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","假單請假開始至結束日期不得超過8天!"));
		}

		
		if(!messages.isEmpty()){
			saveErrors(request, messages);
			return mapping.findForward("Main");
		}

		
		//try {
		messages = sm.saveStudAbsenceApplyByForm(form, days);
  		if(messages.isEmpty()){
  			if(opmode.equals("add")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.CreateSuccessful"));
  			}else if(opmode.equals("modify")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.ModifySuccessful"));
  			}
			session.removeAttribute("StudAskLeaveEditForm");
			session.removeAttribute("StudAskLeaveModifyForm");
			session.removeAttribute("StudAskLeaveInEdit");
			saveMessages(request, messages);

			return query(mapping, (ActionForm)form, request, response);
  		}else{
			saveErrors(request, messages);
  		}
		if(opmode.equals("add")){
  			setContentPage(request, "student/StudAbsenceEdit.jsp");
  		}else if(opmode.equals("modify")){
  			setContentPage(request, "student/StudAbsenceModify.jsp");
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
						
		Student student = (Student) getUserCredential(session).getStudent();
		String studentNo = student.getStudentNo();
		Map initValue = new HashMap();
		
		initValue.put("opmode", "view");
						
				
		List<StudDocApply> docs = (List<StudDocApply>)this.getDocSelectedList(request);
		if(!docs.isEmpty()){
			String sdate = "";
			String edate = "";
			for(StudDocApply doc:docs){				
				List<StudDocUpload> files = sm.getAbsenceDocAttach(doc.getOid());
				List<UploadFileInfo> finfos = new ArrayList<UploadFileInfo>();
				StringBuffer buf = new StringBuffer();
				if(!files.isEmpty()){
					for(StudDocUpload upload:files){
						UploadFileInfo finfo = new UploadFileInfo(upload.getOid(), upload.getFileName());
						finfos.add(finfo);
						buf.append(upload.getOid()).append("|");
					}
				}
				doc.setUploadFileInfo(finfos);
				
				//將請假節次資料轉換為 PeriodInfo 以方便 JSP 處理
				Calendar scal = Calendar.getInstance();
				Calendar ecal = Calendar.getInstance();
				scal.setTime(doc.getStartDate());
				ecal.setTime(doc.getEndDate());
				doc.setSimpleStartDate(Toolket.Date2Str(scal.getTime()));
				doc.setSimpleEndDate(Toolket.Date2Str(ecal.getTime()));
				List<PeriodInfo> details = new ArrayList<PeriodInfo>();
				while(scal.compareTo(ecal)<=0){
					List<StudDocDetail> docds = sm.getStudDocDetailByDate(studentNo, Toolket.FullDate2Str(scal.getTime()));
					String[] infos = new String[]{"","","","","","","","","","","","","","",""};
					if(!docds.isEmpty()){
						for(StudDocDetail docd:docds){
							if(docd.getDocOid().intValue()==doc.getOid().intValue())
								infos[docd.getPeriod()-1] = "1";
						}
						PeriodInfo pinfo = new PeriodInfo("", Toolket.Date2Str(scal.getTime()), infos);
						details.add(pinfo);
					}
					scal.add(Calendar.DATE, 1);
				}
				doc.setDetails(details);
				
				String statusName = "";
				StudDocExamine examine = sm.getStudDocLastExamine(doc.getOid());
				statusName = sm.getExamineStatusName(doc.getStatus());
				if(examine!=null){
					if(!examine.getExamStatus().equalsIgnoreCase(doc.getStatus())){
						statusName += ", " + sm.getExamineStatusName(examine.getExamStatus());
					}
					doc.setProcessDate(examine.getExamDate());
				}else{
					doc.setProcessDate(null);
				}
				doc.setStatusName(statusName);
				doc.setExam(examine);
				
			}
			
			session.setAttribute("StudAskLeaveView", docs);
			session.setAttribute("StudAskLeaveViewInit", initValue);
			setContentPage(request, "student/StudAbsenceView.jsp");
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請勾選您要檢視的假單!"));
			saveErrors(request, messages);
			setContentPage(request, "student/StudAbsenceApply.jsp");
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
						
		Student student = (Student) getUserCredential(session).getStudent();
		String studentNo = student.getStudentNo();
		Map initValue = new HashMap();
		
		initValue.put("opmode", "delete");
		
		Calendar now = Calendar.getInstance();
		Calendar[] cals = Toolket.getDateOfWeek(student.getDepartClass().substring(0, 2), 18);
		Calendar[] calss = Toolket.getDateOfWeek(student.getDepartClass().substring(0, 2), 1);
		if(now.compareTo(cals[0]) > 0){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","學期請假期間已截止，假單刪除功能已關閉!"));
		}
		if(now.compareTo(calss[0]) < 0){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","學期尚未開始!"));
		}
		if(!messages.isEmpty()){
			saveErrors(request, messages);
			setContentPage(request, "student/StudAbsenceApply.jsp");
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		
		List<StudDocApply> docs = (List<StudDocApply>)this.getDocSelectedList(request);
		if(!docs.isEmpty()){
			String sdate = "";
			String edate = "";
			for(StudDocApply doc:docs){
				if(doc.getStatus().equalsIgnoreCase("A") || doc.getStatus().equalsIgnoreCase("1")){
					sdate = Toolket.Date2Str(doc.getStartDate());
					edate = Toolket.Date2Str(doc.getStartDate());
					if(doc.getStatus().equalsIgnoreCase("A")){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1",sdate + "~" + edate + "的假單已經進入審核流程，請勿刪除!"));
					}else if(doc.getStatus().equalsIgnoreCase("1")){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1",sdate + "~" + edate + "的假單已經核准了，無法刪除!"));
					}else if(doc.getAskLeaveType().equalsIgnoreCase("6")){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","公假假單，不可刪除!"));
					}
				}
				if(!messages.isEmpty()){
					saveErrors(request, messages);
					return mapping.findForward("Main");
				}
				
				List<StudDocUpload> files = sm.getAbsenceDocAttach(doc.getOid());
				List<UploadFileInfo> finfos = new ArrayList<UploadFileInfo>();
				StringBuffer buf = new StringBuffer();
				if(!files.isEmpty()){
					for(StudDocUpload upload:files){
						UploadFileInfo finfo = new UploadFileInfo(upload.getOid(), upload.getFileName());
						finfos.add(finfo);
						buf.append(upload.getOid()).append("|");
					}
				}
				doc.setUploadFileInfo(finfos);
				
				//將請假節次資料轉換為 PeriodInfo 以方便 JSP 處理
				Calendar scal = Calendar.getInstance();
				Calendar ecal = Calendar.getInstance();
				scal.setTime(doc.getStartDate());
				ecal.setTime(doc.getEndDate());
				doc.setSimpleStartDate(Toolket.Date2Str(scal.getTime()));
				doc.setSimpleEndDate(Toolket.Date2Str(ecal.getTime()));
				List<PeriodInfo> details = new ArrayList<PeriodInfo>();
				while(scal.compareTo(ecal)<=0){
					List<StudDocDetail> docds = sm.getStudDocDetailByDate(studentNo, Toolket.FullDate2Str(scal.getTime()));
					String[] infos = new String[]{"","","","","","","","","","","","","","",""};
					if(!docds.isEmpty()){
						for(StudDocDetail docd:docds){
							if(docd.getDocOid()==doc.getOid())
								infos[docd.getPeriod()-1] = "1";
						}
						PeriodInfo pinfo = new PeriodInfo("", Toolket.Date2Str(scal.getTime()), infos);
						details.add(pinfo);
					}
					scal.add(Calendar.DATE, 1);
				}
				doc.setDetails(details);
			}
			
			session.setAttribute("StudAskLeaveDelete", docs);
			session.setAttribute("StudAskLeaveDelInit", initValue);
			setContentPage(request, "student/StudAbsenceDelete.jsp");
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請勾選您要刪除的假單!"));
			saveErrors(request, messages);
			setContentPage(request, "student/StudAbsenceApply.jsp");
		}
		
		return mapping.findForward("Main");
	}
	
	public ActionForward delConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		
		List<StudDocApply> selDocs = new ArrayList<StudDocApply>();
		
		selDocs = (List<StudDocApply>)session.getAttribute("StudAskLeaveDelete");
		
		List<StudDocApply> undeletedRecs = sm.delStudDocApply(selDocs);
		session.removeAttribute("StudAskLeaveDelete");

		// no undelete will happen even if delete failure
		if (undeletedRecs.size() == 0) {
			//session.removeAttribute("StudAskLeaves");
			//Map initMap = (Map)session.getAttribute("StudAskLeaveInit");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			"Message.DeleteSuccessful"));
			saveMessages(request, messages);
		} else {
			StringBuffer msg = new StringBuffer();
			for(StudDocApply doc:undeletedRecs){
				msg.append(Toolket.Date2Str(doc.getStartDate()) + " ~ " + Toolket.Date2Str(doc.getEndDate()) + " , ");
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", msg.toString() + "的假單無法刪除,請重新查詢再執行刪除!"));
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
		
		List<StudDocApply> selDocs = new ArrayList<StudDocApply>();
		
		List<StudDocApply> docs = (List<StudDocApply>)this.getDocSelectedList(request);
		if(!docs.isEmpty()){
			StudDocApply doc = docs.get(0);
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
			messages = sm.setStudDoc4Examine(doc);
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
		
		List<StudDocApply> selDocs = new ArrayList<StudDocApply>();
		
		List<StudDocApply> docs = (List<StudDocApply>)this.getDocSelectedList(request);
		if(!docs.isEmpty()){
			StudDocApply doc = docs.get(0);
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
				StudDocExamine doce = sm.getStudDocLastExamine(doc.getOid());
				String statusE = doce.getExamStatus();
				if(!statusE.equals(IConstants.STUDDocCodeSendOut)){
					if(statusE.equals(IConstants.STUDDocCodeDrillmasterOK)){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","假單處理狀態:送核中:系教官已核准,無法取消假單送核!"));
					}else if(statusE.equals(IConstants.STUDDocCodeTutorOK)){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1","假單處理狀態:送核中:導師已核准,無法取消假單送核!"));
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
			
			messages = sm.resetStudDoc4Examine(doc);
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
		
		session.removeAttribute("StudAskLeaveDelete");
		session.removeAttribute("StudAskLeaveModify");
		session.removeAttribute("StudAskLeaveEditForm");
		session.removeAttribute("StudAskLeaveModifyForm");
		session.removeAttribute("StudAskLeaveInEdit");
		Toolket.resetCheckboxCookie(response, "StudAskLeave");
		setContentPage(session, "student/StudAbsenceApply.jsp");
		
		return query(mapping, (ActionForm)dynForm, request, response);
	}

	public ActionForward back(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		
		setContentPage(request, "student/StudAbsenceApply.jsp");
		return mapping.findForward("Main");		
	}

	//Private Method Here ============================>>
	private List getDocSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = "";
		StudAffairDAO dao =(StudAffairDAO)getBean("studAffairDAO");
		List<StudDocApply> docs = new ArrayList<StudDocApply>();
		
		oids = Toolket.getSelectedIndexFromCookie(request, "StudAskLeave");
		docs = (List<StudDocApply>)session.getAttribute("StudAskLeaves");
		List<StudDocApply> selDocs = new ArrayList<StudDocApply>();
		for (StudDocApply doc:docs ) {
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
