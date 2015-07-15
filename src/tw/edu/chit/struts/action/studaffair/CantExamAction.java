package tw.edu.chit.struts.action.studaffair;

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

import tw.edu.chit.model.Just;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class CantExamAction extends BaseLookupDispatchAction {
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Query", "query");
		map.put("Back", "cancel");
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

		Map initMap = new HashMap();
		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		session.removeAttribute("CantExamInit");
		session.removeAttribute("CantExamList");
		setContentPage(session, "studaffair/CantExam.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("CantExamList");
		setContentPage(request.getSession(false), "Directory.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionMessages messages = new ActionMessages();
		//Toolket.resetCheckboxCookie(response, "StudConduct");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		
		String campus = aForm.getString("campusInChargeSAF");
		String school = aForm.getString("schoolInChargeSAF");
		String dept = aForm.getString("deptInChargeSAF");
		String departClass  = aForm.getString("classInChargeSAF");
		
		String scope = aForm.getString("scope");
		String sorttype = aForm.getString("sorttype");
		String range = aForm.getString("range");
		int irange = 0;
		
		log.debug("CantExam->campus:" + campus + ",school:" + school + ",depart:" + dept + ",departClass:" + departClass);
		
		if(!range.trim().equals("")){
			if(StringUtils.isNumeric(range)){
				irange = Integer.parseInt(range);
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InputNumericOnly", "->差幾節"));
				saveMessages(request, messages);
				session.removeAttribute("CantExamList");
				setContentPage(session, "studaffair/CantExam.jsp");
				return mapping.findForward("Main");
			}
		}
		
		if(campus.trim().equalsIgnoreCase("All")||school.trim().equalsIgnoreCase("All")){
		//if(campus.trim().equalsIgnoreCase("All")||school.trim().equalsIgnoreCase("All")||dept.trim().equalsIgnoreCase("All")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NeedSelectTo", "部制"));
			//messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NeedSelectTo", "系別"));
			saveMessages(request, messages);
			session.removeAttribute("CantExamList");
			setContentPage(session, "studaffair/CantExam.jsp");
			return mapping.findForward("Main");
		}
		/*
		if(departClass.trim().equalsIgnoreCase("All")) {
				departClass = campus + school + dept + "%";
		}
		*/
		if(dept.trim().equalsIgnoreCase("All")){
			departClass = campus + school + "%";
		}else{
			if(departClass.trim().equalsIgnoreCase("All")){
				departClass = campus + school + dept + "%";
			}
		}

		Map initMap = new HashMap();
		initMap.put("sorttype", sorttype);
		initMap.put("campus", campus);
		initMap.put("school", school);
		initMap.put("dept", dept);
		initMap.put("departClass", departClass);
		session.setAttribute("CantExamInit", initMap);
		
		if(!departClass.trim().equals("")) {
			//departClass = departClass.replace('*', '%');
			
			List noExam = sm.findCantExamStudByDepartClass(departClass, irange, scope, sorttype);
			session.setAttribute("CantExamList", noExam);
			setContentPage(session, "studaffair/CantExam.jsp");
		} else {
			//log.debug("CantExam->departClass is null");
			if(departClass.trim().equals("")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.MustInput", "Class"));
			}
			saveMessages(request, messages);
			session.removeAttribute("CantExamList");
			setContentPage(session, "studaffair/CantExam.jsp");
			
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
