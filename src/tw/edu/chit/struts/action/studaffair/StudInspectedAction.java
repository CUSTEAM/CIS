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
import tw.edu.chit.model.Just;
import tw.edu.chit.model.Keep;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class StudInspectedAction  extends BaseLookupDispatchAction{
	
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create","Add");
		//map.put("Forward.InspectedAdd","Add");
		map.put("Query","Query");
		//map.put("Forward.InspectedQuery", "Query");
		map.put("Delete","Delete");
		map.put("DeleteConfirm", "DelConfirm");
		map.put("Cancel", "cancel");
		map.put("Modify","Modify");
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
			
		setContentPage(session, "studaffair/StudInspected.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward Add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		session.removeAttribute("StudInspectedEdit");
		setContentPage(session, "studaffair/StudInspectedAdd.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		//Toolket.resetCheckboxCookie(response, "StudConduct");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		String studentNo  = aForm.getString("studentNo");
		String studentName  = aForm.getString("studentName");
		String clazz  = aForm.getString("clazz");
		String downYear  = aForm.getString("downYear");
		
		Map stuMap = new HashMap();
		stuMap.put("studentNo", studentNo);
		
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ActionMessages msgs = new ActionMessages();

		if(studentNo.trim().equals("") && downYear.trim().equals("") &&
				studentName.trim().equals("") && clazz.trim().equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "學號or學生姓名or班級or定察學年"));
			this.saveMessages(request, msgs);
			setContentPage(session, "studaffair/StudInspected.jsp");
			return mapping.findForward("Main");

		}
			
		List<Keep> keepList= sm.findStudInspectedByForm(aForm.getMap());
		
			
		session.setAttribute("StudInspectedList", keepList);
 		setContentPage(session, "studaffair/StudInspected.jsp");

		return mapping.findForward("Main");
	}

	public ActionForward Delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		
		List<Keep> selKeeps = (List<Keep>)getInspectedSelectedList(request);	//Records selected for delete
		if(selKeeps.isEmpty()){
	 		setContentPage(session, "studaffair/StudInspected.jsp");
	 		return mapping.findForward("Main");
		}
		
		session.setAttribute("StudInspectedDelete", selKeeps);
		setContentPage(session, "studaffair/StudInspectedDelete.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
		
	}
	
	public ActionForward DelConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		List<Keep> selKeeps = (List<Keep>)session.getAttribute("StudInspectedDelete");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ActionMessages errs = sm.delStudInspected(selKeeps, Toolket.getBundle(request, "messages.studaffair"));
		session.removeAttribute("StudInspectedDelete");
		// no undeleteScores will happen even if delete failure
		if (errs.isEmpty()) {
			session.removeAttribute("StudInspectedList");
			setContentPage(session, "studaffair/StudInspected.jsp");
			return mapping.findForward("Main");
		} else {
			saveErrors(request, errs);
			setContentPage(session, "studaffair/StudInspected.jsp");
			return mapping.findForward("Main");
		}
		
	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		request.getSession(false).removeAttribute("StudInspectedDelete");
		setContentPage(request.getSession(false), "studaffair/StudInspected.jsp");
		return mapping.findForward("Main");
	}

	
	public ActionForward Modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		List<Keep> selKeeps = (List<Keep>)getInspectedSelectedList(request);
		log.debug("StudInspected==>selKeeps.size():" + selKeeps.size());
		
		session.removeAttribute("StudInspectedModify");
		
		session.setAttribute("StudInspectedInEdit", selKeeps.get(0));
		setContentPage(session, "studaffair/StudInspectedModify.jsp");
		return mapping.findForward("Main");

	}

	
	//Private Method Here ============================>>
	private List getInspectedSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "StudInspected");
		List<Keep> keeps = (List<Keep>)session.getAttribute("StudInspectedList");
		List<Keep> selKeeps = new ArrayList<Keep>();
		Keep keep;
		
		StudAffairDAO dao =(StudAffairDAO)getBean("studAffairDAO");
		for (Iterator<Keep> keepIter = keeps.iterator(); keepIter.hasNext();) {
			keep = keepIter.next();
			if (Toolket.isValueInCookie(keep.getOid().toString(), oids)) {
				selKeeps.add(keep);
			}
		}
		return selKeeps;
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
