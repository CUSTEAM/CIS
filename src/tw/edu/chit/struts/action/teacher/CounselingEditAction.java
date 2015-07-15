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

public class CounselingEditAction  extends BaseLookupDispatchAction{

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
		
		setContentPage(session, "teacher/CounselingEditL.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		ActionMessages messages = new ActionMessages();
		
		String campusInCharge = aForm.getString("campusInCharge").trim();
		String schoolInCharge = aForm.getString("schoolInCharge").trim();
		String deptInCharge = aForm.getString("deptInCharge").trim();
		String departClass = aForm.getString("classInCharge").trim();
		String studentNo  = aForm.getString("studentNo").trim();
		String cdate = aForm.getString("cdate").trim();
		String itemNo = aForm.getString("itemNo").trim();
		String content = aForm.getString("content").trim();

		if(!Toolket.isValidFullDate(cdate)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","輔導日期輸入錯誤!"));
			saveMessages(request, messages);
			session.setAttribute("StudCounselingEditL", aForm.getMap());
			setContentPage(session, "teacher/CounselingEditL.jsp");
			return mapping.findForward("Main");
		}
		
		if(!itemNo.equals("")){
			String Oid = manager.ezGetString("select Oid from HighCareList where SchoolYear='"+schoolYear+"' and SchoolTerm='"+schoolTerm+"' and StudentNo='"+studentNo+"'");
			if (!Oid.equals("")) { //QuesOid目前預設是0
				manager.executeSql("insert into HighCareRec (ListOid,Idno,Rdate,QuesOid,CareRecord)values('"+Oid+"','"+credential.getMember().getIdno()+"','"+cdate+"','4','"+content+"');");
				//System.out.println("insert into HighCareRec (ListOid,Idno,Rdate,QuesOid,CareRecord)values('"+Oid+"','"+credential.getMember().getIdno()+"','"+cdate+"','4','"+content+"');");
			}
			//System.out.println(credential.getMember().getIdno()+","+schoolYear+","+schoolTerm+","+studentNo+","+cdate+","+content);	
		}
		
		ActionMessages err = sm.saveStudCounselingByForm(credential.getMember().getIdno(), aForm, "L");
		
		if(!err.isEmpty()){
			saveErrors(request, err);
			session.setAttribute("StudCounselingEditL", aForm.getMap());
			return mapping.findForward("Main");
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		"Message.CreateSuccessful"));
		saveMessages(request, messages);
		session.removeAttribute("StudCounselingEditL");
		session.removeAttribute("StudCounselingsL");
		
		setContentPage(session, "teacher/CounselingEditL.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String idno = credential.getMember().getIdno();
		String studentNo  = aForm.getString("studentNo").trim();
		String cscode  = aForm.getString("cscode").trim();
		
		List<StudCounseling> counsels = sm.findCounselingByInput(schoolYear, schoolTerm, "L", idno, studentNo, "", null, null);
		List<StudCounseling> counselList = new ArrayList<StudCounseling>();
		
		if(!counsels.isEmpty()){
			for(StudCounseling counsel:counsels){
				if(cscode.equalsIgnoreCase(counsel.getCscode())){
					counselList.add(counsel);
				}
			}
		}
		session.removeAttribute("StudCounselingEditL");
		session.setAttribute("StudCounselingsL", counselList);
		setContentPage(request.getSession(false), "teacher/CounselingL.jsp");
		return mapping.findForward("Main");
	}


}
