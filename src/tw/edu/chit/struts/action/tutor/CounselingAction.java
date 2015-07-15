package tw.edu.chit.struts.action.tutor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import org.json.JSONArray;

import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.CounselingCode;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.StudCounseling;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class CounselingAction  extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create","add");
		map.put("Forward.Add","add");
		map.put("Query","query");
		map.put("Forward.Query", "query");
		map.put("Delete","delete");
		map.put("DeleteConfirm", "delConfirm");
		map.put("Cancel", "cancel");
		map.put("Modify","modify");
		//map.put("BackOneLevel","backOne");
		
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

		Map<String, String> CounselingInit = new HashMap<String, String>();
		DynaActionForm aForm = (DynaActionForm)form;
		
		ActionMessages messages = new ActionMessages();
		
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterT();
		
		String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		
		String studentNo = aForm.getString("studentNo").trim().toUpperCase();
		String departClass = aForm.getString("departClass").trim();	//導師負責班級(也是學生所在班級)
		
		String idno = credential.getMember().getIdno();
		Student student = Toolket.getStudentByNo(studentNo);
		if(studentNo.equals("") || student==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","該學號學生不存在!"));
			saveMessages(request, messages);
			setContentPage(session, "teacher/ClassCounselingT.jsp");
			return mapping.findForward("Main");
		}
		
		List<Map> classStudents = (List<Map>)session.getAttribute("ClassCounselingsT");
		if(classStudents == null || classStudents.isEmpty()){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","輔導資料錯誤,請重新操作!"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		}
		
		boolean isMyStudent = false;
		List<Map> students = new ArrayList<Map>();
		Map classMap = new HashMap();
		for(Map subjMap:classStudents){
			classMap = (Map)subjMap.get("subject");
			if(departClass.equalsIgnoreCase(classMap.get("departClass").toString())){
				students = (List<Map>)subjMap.get("students");
				for(Map stud:students){
					if(studentNo.equalsIgnoreCase(stud.get("studentNo").toString())){
						isMyStudent = true;
					}
				}
				break;
			}
		}
		
		if(!isMyStudent){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","該學生您沒有輔導過!"));
			saveMessages(request, messages);
			setContentPage(session, "teacher/ClassCounselingT.jsp");
			return mapping.findForward("Main");
		}
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		List<CounselingCode> codesL = sm.findCounselingCode("L");
		List<CounselingCode> codesT = sm.findCounselingCode("T");
		session.setAttribute("StudCounselCodeT", codesT);
		session.setAttribute("StudCounselCodeL", codesL);
		JSONArray codesLArray = new JSONArray(codesL, false);
		JSONArray codesTArray = new JSONArray(codesT, false);
		String codeTString = codesTArray.toString();
		session.setAttribute("StudCounselCodeArrayT", codesTArray.toString());
		session.setAttribute("StudCounselCodeArrayL", codesLArray.toString());
		
		List<StudCounseling> counselsT = sm.findCounselingByInput(schoolYear, schoolTerm, "T", idno, studentNo, "", null, null);
		List<StudCounseling> counselsU = sm.findCounselingByInput(schoolYear, schoolTerm, "U", idno, studentNo, "", null, null);
		List<StudCounseling> counsels = new ArrayList<StudCounseling>();
		counsels.addAll(counselsT);
		counsels.addAll(counselsU);
		//列出所有該學生的被輔導記錄 2011-03-21
		List<StudCounseling> allCounsels = new ArrayList<StudCounseling>();
		counselsT = sm.findCounselingByInput("", "", "T", "", studentNo, "", null, null);
		counselsU = sm.findCounselingByInput("", "", "U", "", studentNo, "", null, null);
		allCounsels.addAll(counselsT);
		allCounsels.addAll(counselsU);
		session.setAttribute("StudAllCounselingsT", allCounsels);
		Collections.sort(allCounsels, new yearTermComp());
		
		 //log.debug("=======> students=" + students.size());
		Map<String, String> studentInfo = new HashMap<String, String>();
		studentInfo.put("studentName", student.getStudentName());
		studentInfo.put("studentNo", studentNo);
		studentInfo.put("departClass", student.getDepartClass());
		studentInfo.put("className", Toolket.getClassFullName(student.getDepartClass()));
		studentInfo.put("cscode", classMap.get("cscode").toString());
		studentInfo.put("courseName", classMap.get("courseName").toString());
		studentInfo.put("courseClass", departClass);
		
		session.setAttribute("StudCounselStudentT", studentInfo);

		session.setAttribute("StudCounselingsT", counsels);
		
		setContentPage(session, "teacher/CounselingT.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map initValue = new HashMap();
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		List<CounselingCode> codes = sm.findCounselingCode("T");
		session.setAttribute("StudCounselCodeT", codes);
		
		Calendar now = Calendar.getInstance();
		String nows = "" + (now.get(Calendar.YEAR)-1911) 
					+ "-" + (now.get(Calendar.MONTH) + 1) 
					+ "-" + now.get(Calendar.DATE); 

		//initValue.put("cdate", nows);
		//initValue.put("cYear", (now.get(Calendar.YEAR)-1911));
		//initValue.put("cMonth", (now.get(Calendar.MONTH) + 1));
		//initValue.put("cDay", now.get(Calendar.DATE));
		
		//session.setAttribute("StudCounselingInfo", initValue);
		//session.removeAttribute("StudCounselingsT");
		session.removeAttribute("StudCounselingTEdit");
		session.removeAttribute("StudCounselingTInEdit");
		setContentPage(session, "teacher/CounselingEditT.jsp");
		return mapping.findForward("Main");

	}
	
	/*
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Toolket.resetCheckboxCookie(response, "CounselingsT");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		
		String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String campusInChargeT = aForm.getString("campusInChargeT").trim();
		String schoolInChargeT = aForm.getString("schoolInChargeT").trim();
		String deptInChargeT = aForm.getString("deptInChargeT").trim();
		String departClass = aForm.getString("classInChargeT").trim();
		String studentNo  = aForm.getString("studentNo").trim();
		String dateStart = aForm.getString("dateStart").trim();
		String dateEnd = aForm.getString("dateEnd").trim();
		
		Map<String, String> StudCounselingInit = new HashMap<String, String>();
		StudCounselingInit.put("campusInChargeT", campusInChargeT);
		StudCounselingInit.put("schoolInChargeT", schoolInChargeT);
		StudCounselingInit.put("deptInChargeT", deptInChargeT);
		StudCounselingInit.put("classInChargeT", departClass);
		StudCounselingInit.put("studentNo", studentNo);
		StudCounselingInit.put("dateStart", dateStart);
		StudCounselingInit.put("dateEnd", dateEnd);
		
		//session.setAttribute("StudCounselingInit", StudCounselingInit);
		session.setAttribute("StudCounselingQuery", StudCounselingInit);

		ActionMessages messages = new ActionMessages();
		
		Calendar sCal = null;
		Calendar eCal = null;
		if(Toolket.isValidFullDate(dateStart)){
			Date tdate = Toolket.parseFullDate(dateStart);
			if(tdate != null)
			sCal = Calendar.getInstance();
			sCal.setTime(tdate);
		}
		if(Toolket.isValidFullDate(dateEnd)){
			Date tdate = Toolket.parseFullDate(dateEnd);
			if(tdate != null)
			eCal = Calendar.getInstance();
			eCal.setTime(tdate);
		}
		
		if(sCal != null && eCal != null){
			if(sCal.compareTo(eCal) > 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","起始日期應小於或等於結束日期!"));
				saveMessages(request, messages);
				setContentPage(session, "teacher/CounselingT.jsp");
				return mapping.findForward("Main");
			}
		}
		
		List<StudCounseling> counselingList = new ArrayList<StudCounseling>();
		if(studentNo.equals("")) {
			if(departClass.equalsIgnoreCase("all")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","請選擇您要查詢的輔導班級!"));
				saveMessages(request, messages);
				setContentPage(session, "teacher/CounselingT.jsp");
				return mapping.findForward("Main");
			}
		}
		counselingList = sm.findCounselingByInput(schoolYear, schoolTerm, "T", credential.getMember().getIdno(), studentNo, departClass, sCal, eCal);
				
		session.setAttribute("StudCounselingsT", counselingList);
		setContentPage(session, "teacher/CounselingT.jsp");
		return mapping.findForward("Main");
	}
	*/
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		List<StudCounseling> selList = getSelectedList(request);	//Records selected for delete
		Toolket.setAllCheckboxCookie(response, "StudCounselDeleteT", selList.size());	//Set cookie of record count for delete
		HttpSession session = request.getSession(false);
		session.setAttribute("StudCounselDeleteT", selList);
		setContentPage(session, "teacher/CounselingDelT.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
		
	}
	
	public ActionForward delConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionMessages messages = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		List<StudCounseling> selList = (List<StudCounseling>)session.getAttribute("StudCounselDeleteT");
		String studentNo  = selList.get(0).getStudentNo().trim();
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ActionMessages err = sm.delStudCounselings(selList);
		
		for(int i=0; i<selList.size(); i++) {
			String studentNo1 = selList.get(i).getStudentNo().trim();
			String cdate  = selList.get(i).getCdate().toString();
			String content = selList.get(i).getContent().trim();
			String Oid = manager.ezGetString("SELECT hq.Oid FROM HighCareList hl, HighCareRec hq WHERE hl.Oid=hq.ListOid AND hl.StudentNo='"+studentNo+"' AND hq.Rdate='"+cdate+"' AND hq.QuesOid='4' AND hq.CareRecord='"+content+"' limit 1");
			manager.executeSql("DELETE FROM HighCareRec WHERE Oid='"+Oid+"'");
			//System.out.println("日期="+cdate);
			//System.out.println("DELETE FROM HighCareRec WHERE Oid='"+Oid+"'");
		}
		
		if(!err.isEmpty()){
			messages.add(err);
			session.removeAttribute("StudCounselDeleteT");
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			"Message.DeleteSuccessful"));
			session.removeAttribute("StudCounselDeleteT");
			String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
			String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
			UserCredential credential = (UserCredential)session.getAttribute("Credential");
			String idno = credential.getMember().getIdno();
			
			List<StudCounseling> counselsT = sm.findCounselingByInput(schoolYear, schoolTerm, "T", idno, studentNo, "", null, null);
			List<StudCounseling> counselsU = sm.findCounselingByInput(schoolYear, schoolTerm, "U", idno, studentNo, "", null, null);
			List<StudCounseling> counsels = new ArrayList<StudCounseling>();
			counsels.addAll(counselsT);
			counsels.addAll(counselsU);
			session.setAttribute("StudCounselingsT", counsels);
			
			//列出所有該學生的被輔導記錄 2011-03-21
			List<StudCounseling> allCounsels = new ArrayList<StudCounseling>();
			counselsT = sm.findCounselingByInput("", "", "T", "", studentNo, "", null, null);
			counselsU = sm.findCounselingByInput("", "", "U", "", studentNo, "", null, null);
			allCounsels.addAll(counselsT);
			allCounsels.addAll(counselsU);
			session.setAttribute("StudAllCounselingsT", allCounsels);
			Collections.sort(allCounsels, new yearTermComp());

			setContentPage(request.getSession(false), "teacher/CounselingT.jsp");
			return mapping.findForward("Main");
		}
		
			setContentPage(session, "teacher/CounselingT.jsp");
			return mapping.findForward("Main");
		
	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudCounselDeleteT");
		setContentPage(request.getSession(false), "teacher/CounselingT.jsp");
		return mapping.findForward("Main");
	}

	
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		List<StudCounseling> selList = getSelectedList(request);	//Records selected for modify
		StudCounseling counsel = selList.get(0);
		Map initValue = new HashMap();
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		List<CounselingCode> codesL = sm.findCounselingCode("L");
		List<CounselingCode> codesT = sm.findCounselingCode("T");
		session.setAttribute("StudCounselCodeT", codesT);
		session.setAttribute("StudCounselCodeL", codesL);
		initValue.put("mode", "Modify");
		
		session.setAttribute("StudCounselEditInfo", initValue);
		session.setAttribute("StudCounselInEdit", counsel);
		setContentPage(session, "teacher/CounselingModifyT.jsp");
		return mapping.findForward("Main");

	}

/*	public ActionForward backOne(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		return mapping.findForward("BackOneLevel");
	}
*/
	
	//Private Method Here ============================>>
	private List getSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "CounselingsT");
		List<StudCounseling> counsels = (List<StudCounseling>)session.getAttribute("StudCounselingsT");
		List<StudCounseling> selCounsels = new ArrayList<StudCounseling>();
		StudCounseling counsel;
		StudAffairDAO dao =(StudAffairDAO)getBean("studAffairDAO");
		for (Iterator<StudCounseling> counselIter = counsels.iterator(); counselIter.hasNext();) {
			counsel = counselIter.next();
			if (Toolket.isValueInCookie(counsel.getOid().toString(), oids)) {
				dao.reload(counsel);
				selCounsels.add(counsel);
			}
		}
		return selCounsels;
	}
	
	class yearTermComp implements Comparator {
		public int compare(Object ob1, Object ob2){
			StudCounseling obj1 = (StudCounseling)ob1;
			StudCounseling obj2 = (StudCounseling)ob2;
			short sy1 = obj1.getSchoolYear();
			short sy2 = obj2.getSchoolYear();
			short st1 = obj1.getSchoolTerm();
			short st2 = obj2.getSchoolTerm();
			//學年學期由大至小排列
			if( sy1 < sy2){
				return 1;
			}else if(sy1 == sy2){
				if(st1 < st2){
					return 1;
				}else if(st1 == st2){
					return 0;
				}else{
					return -1;
				}
			}
			else return -1;
		}

		public boolean equals(Object obj){
			return super.equals(obj);
		}
	}
	

}
