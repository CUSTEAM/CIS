package tw.edu.chit.struts.action.teacher;

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
		//map.put("Query","query");
		//map.put("Forward.Query", "query");
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
		
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		
		String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		
		String studentNo = aForm.getString("studentNo").trim().toUpperCase();
		String departClass = aForm.getString("departClass").trim();	//老師授課班級(非學生所在班級)
		String cscode = aForm.getString("cscode").trim();
		
		String idno = credential.getMember().getIdno();
		Student student = Toolket.getStudentByNo(studentNo);
		if(studentNo.equals("") || student==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","該學號學生不存在!"));
			saveMessages(request, messages);
			setContentPage(session, "teacher/ClassCounselingL.jsp");
			return mapping.findForward("Main");
		}
		
		List<Map> classStudents = (List<Map>)session.getAttribute("ClassCounselingsL");
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
			if(departClass.equalsIgnoreCase(classMap.get("departClass").toString()) &&
					cscode.equalsIgnoreCase(classMap.get("cscode").toString())){
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
			setContentPage(session, "teacher/ClassCounselingL.jsp");
			return mapping.findForward("Main");
		}
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		List<CounselingCode> codes = sm.findCounselingCode("L");
		
		
		List<StudCounseling> counsels = sm.findCounselingByInput(schoolYear, schoolTerm, "L", idno, studentNo, "", null, null);
		 //log.debug("=======> students=" + students.size());
		List<StudCounseling> counselList = new ArrayList<StudCounseling>();
		
		if(!counsels.isEmpty()){
			for(StudCounseling counsel:counsels){
				if(cscode.equalsIgnoreCase(counsel.getCscode())){
					counselList.add(counsel);
				}
			}
		}
		Map<String, String> studentInfo = new HashMap<String, String>();
		studentInfo.put("studentName", student.getStudentName());
		studentInfo.put("studentNo", studentNo);
		studentInfo.put("departClass", student.getDepartClass());
		studentInfo.put("className", Toolket.getClassFullName(student.getDepartClass()));
		studentInfo.put("cscode", cscode);
		studentInfo.put("courseName", classMap.get("courseName").toString());
		studentInfo.put("courseClass", departClass);
		
		session.setAttribute("StudCounselStudentL", studentInfo);
		session.setAttribute("StudCounselCodeL", codes);
		session.setAttribute("StudCounselingsL", counselList);
		
		setContentPage(session, "teacher/CounselingL.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map initValue = new HashMap();
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		List<CounselingCode> codes = sm.findCounselingCode("L");
		session.setAttribute("StudCounselCodeL", codes);
		
		Calendar now = Calendar.getInstance();
		String nows = "" + (now.get(Calendar.YEAR)-1911) 
					+ "-" + (now.get(Calendar.MONTH) + 1) 
					+ "-" + now.get(Calendar.DATE); 

		//initValue.put("cdate", nows);
		//initValue.put("cYear", (now.get(Calendar.YEAR)-1911));
		//initValue.put("cMonth", (now.get(Calendar.MONTH) + 1));
		//initValue.put("cDay", now.get(Calendar.DATE));
		
		//session.setAttribute("StudCounselingInfo", initValue);
		//session.removeAttribute("StudCounselingsL");
		session.removeAttribute("StudCounselingEditL");
		session.removeAttribute("StudCounselingInEditL");
		setContentPage(session, "teacher/CounselingEditL.jsp");
		return mapping.findForward("Main");

	}
	/*
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Toolket.resetCheckboxCookie(response, "CounselingsL");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		
		String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String campusInCharge = aForm.getString("campusInCharge").trim();
		String schoolInCharge = aForm.getString("schoolInCharge").trim();
		String deptInCharge = aForm.getString("deptInCharge").trim();
		String departClass = aForm.getString("classInCharge").trim();
		String studentNo  = aForm.getString("studentNo").trim();
		String dateStart = aForm.getString("dateStart").trim();
		String dateEnd = aForm.getString("dateEnd").trim();
		
		Map<String, String> StudCounselingInit = new HashMap<String, String>();
		StudCounselingInit.put("campusInCharge", campusInCharge);
		StudCounselingInit.put("schoolInCharge", schoolInCharge);
		StudCounselingInit.put("deptInCharge", deptInCharge);
		StudCounselingInit.put("classInCharge", departClass);
		StudCounselingInit.put("studentNo", studentNo);
		StudCounselingInit.put("dateStart", dateStart);
		StudCounselingInit.put("dateEnd", dateEnd);
		
		//session.setAttribute("StudCounselingInit", StudCounselingInit);
		session.setAttribute("StudCounselingQuery", StudCounselingInit);

		ActionMessages messages = new ActionMessages();
		
		if(schoolInCharge.equalsIgnoreCase("All")){
			if(campusInCharge.equalsIgnoreCase("All")){
				departClass = "";
			}else{
				departClass = campusInCharge;
			}
		}else if(deptInCharge.equalsIgnoreCase("All")){
			departClass = campusInCharge + schoolInCharge;
		}else if(departClass.equalsIgnoreCase("All")){
			departClass = campusInCharge + schoolInCharge + deptInCharge;
		}
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
				setContentPage(session, "teacher/CounselingL.jsp");
				return mapping.findForward("Main");
			}
		}
		
		List<StudCounseling> counselingList = new ArrayList<StudCounseling>();
		
		if(!studentNo.equals("")) {
			departClass = "";
		}
		counselingList = sm.findCounselingByInput(schoolYear, schoolTerm, "L", credential.getMember().getIdno(), studentNo, departClass, sCal, eCal);
				
		session.setAttribute("StudCounselingsL", counselingList);
		setContentPage(session, "teacher/CounselingL.jsp");
		return mapping.findForward("Main");
	}
	*/
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		List<StudCounseling> selList = getSelectedList(request);	//Records selected for delete
		Toolket.setAllCheckboxCookie(response, "StudCounselDeleteL", selList.size());	//Set cookie of record count for delete
		HttpSession session = request.getSession(false);
		session.setAttribute("StudCounselDeleteL", selList);
		setContentPage(session, "teacher/CounselingDelL.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
		
	}
	
	public ActionForward delConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		List<StudCounseling> selList = (List<StudCounseling>)session.getAttribute("StudCounselDeleteL");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		String studentNo  = selList.get(0).getStudentNo().trim();
		String cscode  = selList.get(0).getCscode().trim();
		
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
			session.removeAttribute("StudCounselDeleteL");
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			"Message.DeleteSuccessful"));
			session.removeAttribute("StudCounselDeleteL");
			String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
			String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
			UserCredential credential = (UserCredential)session.getAttribute("Credential");
			String idno = credential.getMember().getIdno();
			
			List<StudCounseling> counsels = sm.findCounselingByInput(schoolYear, schoolTerm, "L", idno, studentNo, "", null, null);

			List<StudCounseling> counselList = new ArrayList<StudCounseling>();
			
			if(!counsels.isEmpty()){
				for(StudCounseling counseling:counsels){
					if(cscode.equalsIgnoreCase(counseling.getCscode())){
						counselList.add(counseling);
					}
				}
			}
			session.setAttribute("StudCounselingsL", counselList);
			session.removeAttribute("StudCounselDeleteL");
			setContentPage(request.getSession(false), "teacher/CounselingL.jsp");
			return mapping.findForward("Main");
		}
		
			setContentPage(session, "teacher/CounselingL.jsp");
			return mapping.findForward("Main");
		
	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudCounselDeleteL");
		setContentPage(request.getSession(false), "teacher/CounselingL.jsp");
		return mapping.findForward("Main");
	}

	
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		List<StudCounseling> selList = getSelectedList(request);	//Records selected for delete
		StudCounseling counsel = selList.get(0);
		Map initValue = new HashMap();
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		List<CounselingCode> codes = sm.findCounselingCode("L");
		session.setAttribute("StudCounselCodeL", codes);
		initValue.put("mode", "Modify");
		
		session.setAttribute("StudCounselEditInfoL", initValue);
		session.setAttribute("StudCounselInEditL", counsel);
		setContentPage(session, "teacher/CounselingModifyL.jsp");
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
		String oids = Toolket.getSelectedIndexFromCookie(request, "CounselingsL");
		List<StudCounseling> counsels = (List<StudCounseling>)session.getAttribute("StudCounselingsL");
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
	
}
