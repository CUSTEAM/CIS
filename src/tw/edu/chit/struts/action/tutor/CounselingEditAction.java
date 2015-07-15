package tw.edu.chit.struts.action.tutor;

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
		
		setContentPage(session, "teacher/CounselingEditT.jsp");
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
				
		String campusInChargeT = aForm.getString("campusInChargeT").trim();
		String schoolInChargeT = aForm.getString("schoolInChargeT").trim();
		String deptInChargeT = aForm.getString("deptInChargeT").trim();
		String departClass = aForm.getString("classInChargeT").trim();
		String studentNo  = aForm.getString("studentNo").trim();
		String cdate = aForm.getString("cdate").trim();
		String ctype = aForm.getString("ctype").trim();
		String itemNo = aForm.getString("itemNo").trim();
		String content = aForm.getString("content").trim();
		
		if(!Toolket.isValidFullDate(cdate)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","輔導日期輸入錯誤!"));
			saveMessages(request, messages);
			session.setAttribute("StudCounselingEdit", aForm.getMap());
			setContentPage(session, "teacher/CounselingEditT.jsp");
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
		
		ActionMessages err = sm.saveStudCounselingByForm(credential.getMember().getIdno(), aForm, ctype);
		
		if(!err.isEmpty()){
			saveErrors(request, err);
			session.setAttribute("StudCounselingEdit", aForm.getMap());
			return mapping.findForward("Main");
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		"Message.CreateSuccessful"));
		saveMessages(request, messages);
		session.removeAttribute("StudCounselingEdit");
		session.removeAttribute("StudCounselingsT");
		
		setContentPage(session, "teacher/CounselingEditT.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudCounselingEdit");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		DynaActionForm aForm = (DynaActionForm)form;

		String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String idno = credential.getMember().getIdno();
		String studentNo  = aForm.getString("studentNo").trim();
		
		List<StudCounseling> counselsT = sm.findCounselingByInput(schoolYear, schoolTerm, "T", idno, studentNo, "", null, null);
		List<StudCounseling> counselsU = sm.findCounselingByInput(schoolYear, schoolTerm, "U", idno, studentNo, "", null, null);
		List<StudCounseling> counsels = new ArrayList<StudCounseling>();
		counsels.addAll(counselsT);
		counsels.addAll(counselsU);
		session.setAttribute("StudCounselingsT", counsels);

		setContentPage(request.getSession(false), "teacher/CounselingT.jsp");
		return mapping.findForward("Main");
	}


}
