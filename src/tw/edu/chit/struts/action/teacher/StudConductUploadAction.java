package tw.edu.chit.struts.action.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code1;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class StudConductUploadAction extends BaseLookupDispatchAction {
	/*
	 * 注意:前台JSP的botton屬性如果是用Submit,則會直接使用MethodMap指定的方法
	 *     若是為控制前台tab index則submit botton 的屬性設為botton,
	 *     此時須經由 request parameter : opmode 在 unspecified 方法中處理
	 *     
	 * @see org.apache.struts.actions.LookupDispatchAction#getKeyMethodMap()
	 */
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "edit");
		map.put("Back", "back");
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

		Map StudBonusPenaltyInfo = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		session.setAttribute("sessionInterval", session.getMaxInactiveInterval());

		//String opmode = dynForm.getString("opmode");
		//log.debug("StudConductEdit.opmode:" + opmode);
		//if(opmode.equals("StartUpload")) return save(mapping,form,request,response);
		//else if(opmode.equals("cancel"))  return cancel(mapping,form,request,response);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		MemberManager mm = (MemberManager)getBean("memberManager");
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		int empOid = credential.getMember().getOid();
		
		/*	classInCharge refered to UserCredential
		 * 	AuthorityOnTutor: 86 , 導師
		 * 	AuthorityOnChairman: 87 , 系主任
		 *  AuthorityOnDrillmaster: 88 , 教官
		 */
		
		List<Clazz> clazzC = mm.findClassInChargeByMemberAuthority(empOid, UserCredential.AuthorityOnChairman);
		List<Clazz> clazzT = mm.findClassInChargeByMemberAuthority(empOid, UserCredential.AuthorityOnTutor);
		List<Clazz> clazzM = mm.findClassInChargeByMemberAuthority(empOid, UserCredential.AuthorityOnDrillmaster);
		log.debug("StudConductUpload->empOid:clazzT:clazzC:clazzM=" + empOid + ":" + clazzT.size() + ":" + clazzC.size() + ":" + clazzM.size());
		List clazzA = new ArrayList();
		
		
		if(!clazzT.isEmpty()){
			for(Clazz clazz: clazzT){
				Map initMap = new HashMap();
				initMap.put("kind", "Tutor");
				initMap.put("clazzNo", (clazz.getClassNo() + "T"));
				initMap.put("clazzName", Toolket.getClassFullName(clazz.getClassNo()));
				clazzA.add(initMap);
			}
		}
		if(!clazzC.isEmpty()){
			for(Clazz clazz: clazzC){
				Map initMap = new HashMap();
				initMap.put("kind", "Chairman");
				initMap.put("clazzNo", (clazz.getClassNo() + "C"));
				initMap.put("clazzName", Toolket.getClassFullName(clazz.getClassNo()));
				clazzA.add(initMap);
			}
		}
		if(!clazzM.isEmpty()){
			for(Clazz clazz: clazzM){
				Map initMap = new HashMap();
				initMap.put("kind", "Drillmaster");
				initMap.put("clazzNo", (clazz.getClassNo() + "M"));
				initMap.put("clazzName", Toolket.getClassFullName(clazz.getClassNo()));
				clazzA.add(initMap);
			}
		}
		log.debug("StudConductUpload->clazzA=" + clazzA.size());
		
		session.setAttribute("studConductUploadInit", clazzA);
		session.removeAttribute("StudConductUploadModify");
		session.removeAttribute("StudConductUploadInEdit");
		
		List<Code1> ConductMarkList = sm.findConductMark("");
		session.setAttribute("ConductMark", ConductMarkList);
		setContentPage(session, "teacher/ConductClassChoose.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward edit(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm dForm = (DynaActionForm)aform;
		HttpSession session = request.getSession(false);
		
		Map initMap = new HashMap();
		//initMap = (Map)session.getAttribute("studConductUploadInit");
		
		String selclass = dForm.getString("selclass");
		log.debug("ConductUpload->class:" + selclass);
		String departClass = selclass.substring(0, selclass.length()-1);
		
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		ActionMessages messages = scm.chkTchScoreUploadOpened("5", departClass);
		if(!messages.isEmpty()) {
			saveMessages(request, messages);
			setContentPage(session, "teacher/ConductClassChoose.jsp");
			return mapping.findForward("Main");
		} 
		
		char upLoadMode = selclass.charAt( selclass.length()-1);
		switch(upLoadMode){
		case 'T':	//老師
			initMap.put("uploadMode", "Teacher");
			break;
		case 'C':	//系主任
			initMap.put("uploadMode", "Chairman");
			break;
		case 'M':	//教官
			initMap.put("uploadMode", "Drillmaster");
			break;			
		}
		initMap.put("departClass", departClass);
		initMap.put("deptClassName", Toolket.getClassFullName(departClass));
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		List<Student> studList = sm.findStudentsByClass(departClass);
		List<Just> justList = sm.findJustByClass(departClass);
		
		//該班學生人數與操行成績檔學生人數不一致
		if(studList.size() != justList.size()) {
			ActionMessages errs = new ActionMessages();
			errs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.NumberOfStudensNonEqual"));
			saveErrors(request, errs);
		}
		
		if(justList.isEmpty()){
			ActionMessages errs = new ActionMessages();
			errs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.NoRecordFound"));
			saveErrors(request, errs);
		}
		
		//傳回該班操行成績資料
		/*
		double[] scores = new double[2];
		double total = 0d;
		
		for(Just just: justList){
			scores = sm.calConductScoreOfDilgDesd(just.getStudentNo());
			just.setDilgScore(scores[0]);
			just.setDesdScore(scores[1]);
			total = 82d + just.getTeacherScore() + just.getDeptheaderScore() + just.getMilitaryScore()
					+ just.getMilitaryScore() + scores[0] + scores[1];
			if(total > 95d) total = 95.0d;
			just.setTotalScore(total);
			
		}
		*/
		
		session.setAttribute("StudConductUploadModifyInit", initMap);
		session.setAttribute("StudConductUploadInEdit", justList);
		setContentPage(request.getSession(false), "teacher/ConductUpload.jsp");
		session.setMaxInactiveInterval(7200);	//2Hr. for session timeout
		
		return mapping.findForward("Main");
	}
	
	

	public ActionForward back(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudConductUploadInEdit");
		session.removeAttribute("StudConductUploadModify");

		setContentPage(request.getSession(false), "teacher/ConductClassChoose.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudConductUploadInit");
		session.removeAttribute("StudConductUploadInEdit");
		session.removeAttribute("StudConductUploadModify");
		setContentPage(request.getSession(false), "Directory.jsp");

		//setContentPage(request.getSession(false), "teacher/StudConduct.jsp");
		return mapping.findForward("Main");
	}


}
