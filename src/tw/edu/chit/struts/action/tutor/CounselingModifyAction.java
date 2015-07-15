package tw.edu.chit.struts.action.tutor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import tw.edu.chit.struts.action.tutor.CounselingAction.yearTermComp;
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
		
		setContentPage(session, "teacher/CounselingModifyT.jsp");
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
			session.setAttribute("StudCounselingModify", aForm.getMap());
			setContentPage(session, "teacher/CounselingModifyT.jsp");
			return mapping.findForward("Main");
		}
		StudCounseling counsel = (StudCounseling)session.getAttribute("StudCounselInEdit");
		String studentNo  = counsel.getStudentNo().trim();
		String content1  = counsel.getContent().trim();
		
		ActionMessages err = sm.saveStudCounselingModify(counsel, aForm);
		
		String Oid = manager.ezGetString("SELECT hq.Oid FROM HighCareList hl, HighCareRec hq WHERE hl.Oid=hq.ListOid AND hl.StudentNo='"+studentNo+"' AND hq.Rdate='"+cdate+"' AND hq.QuesOid='4' AND hq.CareRecord='"+content1+"' limit 1");
		if (!Oid.equals("")) { //QuesOid目前預設是0
			manager.executeSql("UPDATE HighCareRec SET Rdate='"+cdate+"', CareRecord='"+content+"' WHERE Oid='"+Oid+"'");
		}
		
		if(!err.isEmpty()){
			saveErrors(request, err);
			session.setAttribute("StudCounselingModify", aForm.getMap());
			return mapping.findForward("Main");
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		"Message.ModifySuccessful"));
		saveMessages(request, messages);
		String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
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
		Collections.sort(allCounsels, new yearTermComp());
		session.setAttribute("StudAllCounselingsT", allCounsels);
		
		session.removeAttribute("StudCounselingModify");
		session.removeAttribute("StudCounselInEdit");
		
		setContentPage(session, "teacher/CounselingT.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudCounselingModify");
		session.removeAttribute("StudCounselInEdit");
		// session.removeAttribute("StudCounselingsT");
		setContentPage(request.getSession(false), "teacher/CounselingT.jsp");
		return mapping.findForward("Main");
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
