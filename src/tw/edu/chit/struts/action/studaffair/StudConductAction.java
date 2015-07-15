package tw.edu.chit.struts.action.studaffair;

import java.util.ArrayList;
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

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Code1;
import tw.edu.chit.model.Code2;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Desd;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.Toolket;

public class StudConductAction extends BaseLookupDispatchAction {
	
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create","conductAdd");
		//map.put("Forward.conductAdd","conductAdd");
		map.put("Query","conductQuery");
		//map.put("Forward.conductQuery", "conductQuery");
		map.put("Delete","conductDelete");
		map.put("DeleteConfirm", "conductDelConfirm");
		map.put("Cancel", "cancel");
		map.put("Modify","conductModify");
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

		DynaActionForm dynForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		List<Code1> ConductMarkList = sm.findConductMark("");
		session.setAttribute("ConductMark", ConductMarkList);
		
			
		setContentPage(session, "studaffair/StudConduct.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward conductAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		session.removeAttribute("StudConductEdit");
		setContentPage(session, "studaffair/StudConductEdit.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward conductQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		//Toolket.resetCheckboxCookie(response, "StudConduct");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		String studentNo  = aForm.getString("studentNo");
		Map stuMap = new HashMap();
		stuMap.put("studentNo", studentNo);
		
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		
		//log.debug("TimeOffQuery, bpYear=" + bpYear + " bpMonth=" + bpMonth + " bpDay=" + bpDay);
		if(studentNo.equals("")) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.MustInput","學號"));
			saveMessages(request, messages);
			
			session.setAttribute("StudConductInit", stuMap);
			setContentPage(session, "studaffair/StudConduct.jsp");
			return mapping.findForward("Main");
		} else {
			Student stu = new Student();
			
			stu = scm.findStudentByStudentNo(studentNo);
			if(stu != null) {
				stuMap.put("studentName", stu.getStudentName());
				stuMap.put("departClass", stu.getDepartClass());
				stuMap.put("depClassName", Toolket.getClassFullName(stu.getDepartClass()));
			} else {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.StudentNotFound",studentNo));
				saveMessages(request, messages);
				session.removeAttribute("StudConductList");
				setContentPage(session, "studaffair/StudConduct.jsp");
				return mapping.findForward("Main");
			}
		}
				
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		List<Just> justList = sm.findJustByStudentNo(studentNo, classInCharge);
		 //log.debug("=======> students=" + students.size());
		//log.debug("=======> Justs=" + justs.size());
		
		if (justList.isEmpty()) {
			//can't not find any Just records (just)
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.StudConduct.noJustsFound"));
			saveMessages(request, messages);
			session.removeAttribute("StudConductList");
			setContentPage(session, "studaffair/StudConduct.jsp");
		} else {			
			List<Just> justs = new ArrayList();
			justs.add(justList.get(0));
			
			session.setAttribute("StudConductList", justs);
 			setContentPage(session, "studaffair/StudConduct.jsp");
		}
		
		session.setAttribute("ConductStuMap", stuMap);		
		session.setAttribute("StudConductInit", stuMap);

		//log.debug("After ReQuery tfYear=" + aForm.getString("tfYear"));
		return mapping.findForward("Main");
	}

	public ActionForward conductDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		
		List<Just> selJusts = (List<Just>)session.getAttribute("StudConductList");	//Records selected for delete
		session.setAttribute("StudConductDelete", selJusts);
		setContentPage(session, "studaffair/StudConductDelete.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
		
	}
	
	public ActionForward conductDelConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		List<Just> selJusts = (List<Just>)session.getAttribute("StudConductDelete");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ActionMessages errs = sm.delStudConduct(selJusts, Toolket.getBundle(request, "messages.studaffair"));
		session.removeAttribute("StudConductDelete");
		// no undeleteScores will happen even if delete failure
		if (errs.isEmpty()) {
			session.removeAttribute("StudConductList");
			Map initMap = (Map)session.getAttribute("StudConductInit");
			DynaActionForm dynForm = (DynaActionForm)form;
			dynForm.set("studentNo", initMap.get("studentNo"));
			//log.debug("delete Just success, bpYear=" + dynForm.getString("bpYear"));
			
			return conductQuery(mapping, (ActionForm)dynForm, request, response);
		} else {
			saveErrors(request, errs);
			setContentPage(session, "studaffair/StudConduct.jsp");
			return mapping.findForward("Main");
		}
		
	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		request.getSession(false).removeAttribute("StudConductDelete");
		setContentPage(request.getSession(false), "studaffair/StudConduct.jsp");
		return mapping.findForward("Main");
	}

	
	public ActionForward conductModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		List<Just> selJusts = (List<Just>)session.getAttribute("StudConductList");
		log.debug("StudConduct==>selJusts.size():" + selJusts.size());
		
		session.removeAttribute("StudConductModify");
		
		session.setAttribute("StudConductInEdit", selJusts);
		setContentPage(session, "studaffair/StudConductModify.jsp");
		return mapping.findForward("Main");

	}

	
	//Private Method Here ============================>>
	private ActionMessages validateInput(DynaActionForm form) {
		ActionMessages msgs = new ActionMessages();

	    Double teacherScore;
	    Double deptheaderScore;
	    Double militaryScore;
	    Double meetingScore;
	    
	    if(!form.getString("teacherScore").trim().equals(""))
	    	teacherScore = Double.parseDouble(form.getString("teacherScore"));
	    
	    if(!form.getString("deptheaderScore").trim().equals(""))
	    	deptheaderScore = Double.parseDouble(form.getString("deptheaderScore"));
	    
	    if(!form.getString("militaryScore").trim().equals(""))
	    	militaryScore = Double.parseDouble(form.getString("militaryScore"));
	    
	    if(!form.getString("meetingScore").trim().equals(""))
	    	meetingScore = Double.parseDouble(form.getString("meetingScore"));
	    
		String comCode1 = form.getString("comCode1");
		String comCode1Sel = form.getString("comCode1Sel");
		String comCode2 = form.getString("comCode2");
		String comCode2Sel = form.getString("comCode2Sel");
		String comCode3 = form.getString("comCode3");
		String comCode3Sel = form.getString("comCode3Sel");
		
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		List<Code1> ConductMarkList = sm.findConductMark("");
		boolean isfound = false;
		
		if(!comCode1.equals(comCode1Sel)) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.ConductMarkErr"));
			return msgs;
		}

		isfound = false;
		if(comCode1.equals("")) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.ConductMarkErr"));
			return msgs;
		} else {
			for(Iterator<Code1> c1Iter=ConductMarkList.iterator(); c1Iter.hasNext();){
				Code1 code1=c1Iter.next();
				if(comCode1.equals(code1.getNo())) {
					isfound = true;
					break;
				}
			}
			if(!isfound) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.ConductMarkErr"));
				return msgs;
			}
		}	

		return msgs;
	}


}
