package tw.edu.chit.struts.action.studaffair;

import java.sql.SQLException;
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

import tw.edu.chit.model.Keep;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class StudInspectedCreateAction extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "create");
		map.put("Back", "cancel");
		map.put("Cancel", "cancel");
		return map;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map initMap = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		List c5List = sm.findCampusDepartment();
		
		List deptList = new ArrayList();
		
		String c5Name = "";
		for(Iterator c5Iter = c5List.iterator(); c5Iter.hasNext();){
			c5Name = c5Iter.next().toString();
			Map deptMap = new HashMap();
			deptMap.put("no", c5Name.substring(0, 2));
			deptMap.put("name", c5Name);
			deptList.add(deptMap);
		}
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		
		session.setAttribute("CampusDepartment", deptList);
		session.removeAttribute("StudTermInspectedList");
		
		setContentPage(session, "studaffair/StudInspectedCreate.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudTermInpectedList");
		setContentPage(request.getSession(false), "Directory.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		//Toolket.resetCheckboxCookie(response, "StudConduct");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		
		String campus = aForm.getString("campus");
		String sYear = Toolket.getSysParameter("School_year");
		String sTerm = Toolket.getSysParameter("School_term");
				
		/*
		Map initMap = new HashMap();
		initMap.put("filtertype", filtertype);
		initMap.put("school_year", Toolket.getSysParameter("School_year"));
		initMap.put("school_term", Toolket.getSysParameter("School_term"));
		session.setAttribute("ScoreNotUploadInit", initMap);
		*/
		
		ActionMessages msgs = new ActionMessages();
		if(!campus.trim().equals("")){
			List<Keep> studInsp = new ArrayList();
			try {
				studInsp = sm.createInspectedStudents(campus, sYear, sTerm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(!msgs.isEmpty()){
				saveMessages(request, msgs);
				session.removeAttribute("StudTermInspectedList");
				setContentPage(session, "studaffair/StudInspectedCreate.jsp");
			}
			session.setAttribute("StudTermInspectedList", studInsp);
			setContentPage(session, "studaffair/StudInspectedCreate.jsp");
		} else {
			//log.debug("CantExam->departClass is " + departClass);
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.MustInput", "部制"));
			saveMessages(request, msgs);
			session.removeAttribute("StudTermInspectedList");
			setContentPage(session, "studaffair/StudInspectedCreate.jsp");
			
		}
		
		return mapping.findForward("Main");
	}

	
	private boolean isNumeric(String doublStr) {
		String[] splitStr = StringUtils.split(doublStr, ".");
		//log.debug("isNumeric->splitStr.length" + splitStr.length);
		if(splitStr.length > 2) return false;
		for(int i =0; i<splitStr.length; i++){
			if(i==0){
				if(splitStr[0].trim().indexOf("-")==0) {
					splitStr[0] = splitStr[0].trim().substring(1);
				}
			}
			if(!StringUtils.isNumeric(splitStr[i])) return false;
		}
		return true;
	}


}
