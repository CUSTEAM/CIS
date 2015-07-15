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

public class CounselingLAction  extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Add", "add");
		map.put("Save", "save");
		map.put("BatchInsert", "batchInsert");
		map.put("BatchSave", "batchSave");
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

		Map<String, String> CounselingInit = new HashMap<String, String>();
		DynaActionForm dynForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterT();
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		List<CounselingCode> codes = sm.findCounselingCode("L");
		
		List<Map> classStudents = sm.findCounselingStudentInClass("L", credential); 	//L:教師學習輔導
		
		session.setAttribute("StudCounselCodeL", codes);
		session.setAttribute("ClassCounselingsL", classStudents);
		
		setContentPage(session, "teacher/ClassCounselingL.jsp");
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
		session.removeAttribute("StudCounselingsL");
		session.removeAttribute("StudCounselingLEdit");
		session.removeAttribute("StudCounselingLInEdit");
		setContentPage(session, "teacher/CounselingNewOtherL.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
				
		String studentNo  = aForm.getString("studentNo").trim();
		String cdate = aForm.getString("cdate").trim();
				
		if(studentNo.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","請選擇接受輔導的學生!"));
		}

		if(!Toolket.isValidFullDate(cdate)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","輔導日期輸入錯誤!"));
		}
		
		if(!messages.isEmpty()){
			saveMessages(request, messages);
			session.setAttribute("StudCounselingNewOtherL", aForm.getMap());
			setContentPage(session, "teacher/CounselingNewOtherL.jsp");
			return mapping.findForward("Main");
		}
		
		ActionMessages err = sm.saveStudCounselingByForm(credential.getMember().getIdno(), aForm, "L");
		
		if(!err.isEmpty()){
			saveErrors(request, err);
			session.setAttribute("StudCounselingNewOtherL", aForm.getMap());
			return mapping.findForward("Main");
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		"Message.CreateSuccessful"));
		saveMessages(request, messages);
		session.removeAttribute("StudCounselingNewOtherL");
		session.removeAttribute("ClassCounselingsL");
		
		//setContentPage(session, "teacher/CounselingEditL.jsp");
		//return mapping.findForward("Success");
		return this.unspecified(mapping, form, request, response);
	}

	public ActionForward batchInsert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		Map initValue = new HashMap();
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		List<CounselingCode> codes = sm.findCounselingCode("L");
		session.setAttribute("StudCounselCodeL", codes);
		String students  = aForm.getString("students").trim();
		ActionMessages messages = new ActionMessages();
		
		if(students.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","請勾選接受輔導的學生!"));
		}
		if(!messages.isEmpty()){
			saveMessages(request, messages);
			setContentPage(session, "teacher/ClassCounselingT.jsp");
			return mapping.findForward("Main");
		}
		List<Student> studs = new ArrayList<Student>();
		String[] stEcts = students.split("\\|");
		String[] sts = new String[stEcts.length];
		//String[] clazzs = new String[stEcts.length];
		//String[] cscodes = new String[stEcts.length];
		String[] ectcode = null;
		int cnt = 0;
		for(String ects:stEcts){
			ectcode = ects.split("_");
			sts[cnt] = ectcode[0];
			//clazzs[cnt] = ectcode[1];
			//cscodes[cnt] = ectcode[2];
			cnt++;
		}
		for(String st:sts){
			Student student = sm.findStudentByStudentNo(st);
			if(student != null){
				student.setDepartClass2(Toolket.getClassFullName(student.getDepartClass()));
				studs.add(student);
			}
		}
		session.removeAttribute("StudCounselingsL");
		session.removeAttribute("StudCounselingLEdit");
		session.removeAttribute("StudCounselingLInEdit");
		
		session.setAttribute("CounselingLStuds", studs);
		session.setAttribute("CounselingLStEcts", students);
		setContentPage(session, "teacher/CounselingBatchEditL.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward batchSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		ActionMessages messages = new ActionMessages();
				
		List<Student> students  = (List<Student>)session.getAttribute("CounselingLStuds");
		String stEcts = session.getAttribute("CounselingLStEcts").toString();
		String cdate = aForm.getString("cdate").trim();
		String itemNo  = aForm.getString("itemNo").trim();
		String content  = aForm.getString("content").trim();
				
		if(!Toolket.isValidFullDate(cdate)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","輔導日期輸入錯誤!"));
		}
		if(itemNo.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","請選擇輔導項目!"));
		}
		if(content.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","請填寫輔導內容!"));
		}
		
		if(!messages.isEmpty()){
			saveMessages(request, messages);
			session.setAttribute("CounselingBatchEdit4L", aForm.getMap());
			setContentPage(session, "teacher/CounselingBatchEditL.jsp");
			return mapping.findForward("Main");
		}
		
		if(!itemNo.equals("")){
			for(Student stud:students){
				String studentNo=stud.getStudentNo();
				String Oid = manager.ezGetString("select Oid from HighCareList where SchoolYear='"+schoolYear+"' and SchoolTerm='"+schoolTerm+"' and StudentNo='"+studentNo+"'");
				if (!Oid.equals("")) { //QuesOid目前預設是0
					manager.executeSql("insert into HighCareRec (ListOid,Idno,Rdate,QuesOid,CareRecord)values('"+Oid+"','"+credential.getMember().getIdno()+"','"+cdate+"','4','"+content+"');");
					//System.out.println("insert into HighCareRec (ListOid,Idno,Rdate,QuesOid,CareRecord)values('"+Oid+"','"+credential.getMember().getIdno()+"','"+cdate+"','4','"+content+"');");
				}
				//System.out.println(credential.getMember().getIdno()+","+schoolYear+","+schoolTerm+","+studentNo+","+cdate+","+content);
			}
		}
		
		ActionMessages err = sm.saveStudCounselingBatchLByForm(credential.getMember().getIdno(), 
				stEcts, aForm);
		
		if(!err.isEmpty()){
			saveErrors(request, err);
			session.setAttribute("CounselingBatchEdit4L", aForm.getMap());
			return mapping.findForward("Main");
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		"Message.CreateSuccessful"));
		saveMessages(request, messages);
		session.removeAttribute("CounselingBatchEdit4L");
		session.removeAttribute("CounselingLStuds");
		session.removeAttribute("CounselingLStEcts");
		
		//setContentPage(session, "teacher/CounselingEditL.jsp");
		//return mapping.findForward("Success");
		return this.unspecified(mapping, form, request, response);
	}

	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		//session.removeAttribute("StudCounselDeleteL");
		setContentPage(request.getSession(false), "teacher/ClassCounselingL.jsp");
		return mapping.findForward("Main");
	}
	
}
