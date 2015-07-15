package tw.edu.chit.struts.action.teacher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import tw.edu.chit.model.CounselingCode;
import tw.edu.chit.model.StudCounseling;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class CounselingModifyAction  extends BaseLookupDispatchAction{

	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("OK","save");
		map.put("Cancel", "cancel");
		return map;		
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, String> CounselingInit = new HashMap<String, String>();
		DynaActionForm dynForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterT();
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		setContentPage(session, "teacher/CounselingModifyL.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
				
		String cdate = aForm.getString("cdate").trim();
		String content = aForm.getString("content").trim();
				
		if(!Toolket.isValidFullDate(cdate)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","輔導日期輸入錯誤!"));
			saveMessages(request, messages);
			session.setAttribute("StudCounselingModifyL", aForm.getMap());
			setContentPage(session, "teacher/CounselingModifyL.jsp");
			return mapping.findForward("Main");
		}
		StudCounseling counsel = (StudCounseling)session.getAttribute("StudCounselInEditL");
		String studentNo  = counsel.getStudentNo().trim();
		String cscode  = counsel.getCscode().trim();
		String content1  = counsel.getContent().trim();
		
		ActionMessages err = sm.saveStudCounselingModify(counsel, aForm);
		
		String Oid = manager.ezGetString("SELECT hq.Oid FROM HighCareList hl, HighCareRec hq WHERE hl.Oid=hq.ListOid AND hl.StudentNo='"+studentNo+"' AND hq.Idno='"+credential.getMember().getIdno()+"' AND hq.Rdate='"+cdate+"' AND hq.CareRecord='"+content1+"' limit 1");
		//System.out.println(Oid+"---"+credential.getMember().getIdno());
		if (!Oid.equals("")) { //QuesOid目前預設是0
			manager.executeSql("UPDATE HighCareRec SET Rdate='"+cdate+"', CareRecord='"+content+"' WHERE Oid='"+Oid+"'");
			//System.out.println("UPDATE HighCareRec SET Rdate='"+cdate+"', CareRecord='"+content+"' WHERE Oid='"+Oid+"'");
		}
		
		if(!err.isEmpty()){
			saveErrors(request, err);
			session.setAttribute("StudCounselingModifyL", aForm.getMap());
			return mapping.findForward("Main");
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		"Message.ModifySuccessful"));
		saveMessages(request, messages);
		session.removeAttribute("StudCounselingModifyL");
		session.removeAttribute("StudCounselInEditL");
		
		String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
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
		
		setContentPage(session, "teacher/CounselingL.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudCounselingModifyL");
		session.removeAttribute("StudCounselInEditL");
		// session.removeAttribute("StudCounselingsT");
		setContentPage(request.getSession(false), "teacher/CounselingL.jsp");
		return mapping.findForward("Main");
	}


}
