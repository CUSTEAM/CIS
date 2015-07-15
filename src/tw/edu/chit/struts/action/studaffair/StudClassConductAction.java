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
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class StudClassConductAction extends BaseLookupDispatchAction {
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		//map.put("Create","classConductAdd");
		//map.put("Forward.conductAdd","conductAdd");
		map.put("Query","classConductQuery");
		//map.put("Forward.conductQuery", "conductQuery");
		map.put("Delete","classConductDelete");
		map.put("DeleteConfirm", "classConductDelConfirm");
		map.put("Cancel", "cancel");
		map.put("Modify","classConductModify");
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
		
			
		setContentPage(session, "studaffair/StudClassConduct.jsp");
		return mapping.findForward("Main");

	}
	
/*
	public ActionForward classConductAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		String departClass = dynForm.getString("departClass");
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		List<Student> students = sm.findStudentsByClass(departClass);
		
		session.removeAttribute("StudConductEdit");
		session.setAttribute("StudentInClass", students);
		setContentPage(session, "studaffair/StudClassConductEdit.jsp");
		return mapping.findForward("Main");

	}
*/
	
	public ActionForward classConductQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionMessages messages = new ActionMessages();
		//Toolket.resetCheckboxCookie(response, "StudConduct");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		String campus = aForm.getString("campusInChargeSAF");
		String school = aForm.getString("schoolInChargeSAF");
		String dept = aForm.getString("deptInChargeSAF");
		String departClass  = aForm.getString("classInChargeSAF");

		Toolket.resetCheckboxCookie(response, "StudClassConduct");
		Map classMap = new HashMap();
		classMap.put("campus", campus);
		classMap.put("school", school);
		classMap.put("dept", dept);
		classMap.put("departClass", departClass);
		classMap.put("depClassName", Toolket.getClassFullName(departClass));

		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		
		List<Just> justList = new ArrayList();
		if(departClass.equals("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.MustInput","班級代碼"));
			saveMessages(request, messages);
			
			//session.setAttribute("StudConductInit", stuMap);
			setContentPage(session, "studaffair/StudClassConduct.jsp");
			return mapping.findForward("Main");
		} else {
			if(classInCharge.indexOf(departClass) != -1){
				justList = sm.findJustByClass(departClass);
			}else {
				//不是你負責的班級
				log.debug("StudCalssConduct->departClass:classInCharge:" + departClass + "::" + classInCharge);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.YouDontHavePermission"));
				saveMessages(request, messages);
				session.removeAttribute("StudClassConductList");
				setContentPage(session, "studaffair/StudClassConduct.jsp");
			}
		}
						
		if (justList.isEmpty()) {
			//can't not find any Just records (just)
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.StudConduct.noJustsFound"));
			saveMessages(request, messages);
			session.removeAttribute("StudClassConductList");
			setContentPage(session, "studaffair/StudClassConduct.jsp");
		} else {					
			session.setAttribute("StudClassConductList", justList);
 			setContentPage(session, "studaffair/StudClassConduct.jsp");
		}
		
		session.setAttribute("StudClassConductInit", classMap);

		//log.debug("After ReQuery tfYear=" + aForm.getString("tfYear"));
		return mapping.findForward("Main");
	}

	public ActionForward classConductDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		
		List<Just> selJusts = (List<Just>)getConductSelectedList(request);	//Records selected for delete
		session.setAttribute("StudClassConductDelete", selJusts);
		setContentPage(session, "studaffair/StudClassConductDelete.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
		
	}
	
	public ActionForward conductDelConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		List<Just> selJusts = (List<Just>)getConductSelectedList(request);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ActionMessages errs = sm.delStudConduct(selJusts, Toolket.getBundle(request, "messages.studaffair"));
		session.removeAttribute("StudClassConductDelete");
		// no undeleteScores will happen even if delete failure
		if (errs.isEmpty()) {
			session.removeAttribute("StudClassConductList");
			Map initMap = (Map)session.getAttribute("StudClassConductInit");
			DynaActionForm dynForm = (DynaActionForm)form;
			dynForm.set("departClass", initMap.get("departClass"));
			//log.debug("delete Just success, bpYear=" + dynForm.getString("bpYear"));
			
			return classConductQuery(mapping, (ActionForm)dynForm, request, response);
		} else {
			saveErrors(request, errs);
			setContentPage(session, "studaffair/StudClassConduct.jsp");
			return mapping.findForward("Main");
		}
		
	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {
		
		request.getSession(false).removeAttribute("StudClassConductDelete");
		setContentPage(request.getSession(false), "studaffair/StudClassConduct.jsp");
		return mapping.findForward("Main");
	}

	
	public ActionForward classConductModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		List<Just> selJusts = (List<Just>)getConductSelectedList(request);
		log.debug("StudClassConduct==>selJusts.size():" + selJusts.size());
		
		session.removeAttribute("StudClassConductModify");
		
		session.setAttribute("StudClassConductInEdit", selJusts);
		setContentPage(session, "studaffair/StudClassConductModify.jsp");
		return mapping.findForward("Main");

	}

	
	//Private Method Here ============================>>
	private List getConductSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "StudClassConduct");
		List<Just> justs = (List<Just>)session.getAttribute("StudClassConductList");
		List<Just> selJusts = new ArrayList<Just>();
		Just just;
		double[] scores = new double[2];
		
		StudAffairDAO dao =(StudAffairDAO)getBean("studAffairDAO");
		for (Iterator<Just> justIter = justs.iterator(); justIter.hasNext();) {
			just = justIter.next();
			if (Toolket.isValueInCookie(just.getOid().toString(), oids)) {
				/*
				scores[0] = just.getDilgScore();
				scores[1] = just.getDesdScore();
				dao.reload(just);
				just.setDilgScore(scores[0]);
				just.setDesdScore(scores[1]);
				 */
				selJusts.add(just);
			}
		}
		return selJusts;
	}
	
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
