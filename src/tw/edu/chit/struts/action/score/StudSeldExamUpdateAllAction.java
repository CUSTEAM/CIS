package tw.edu.chit.struts.action.score;

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

import tw.edu.chit.model.Code5;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class StudSeldExamUpdateAllAction extends BaseLookupDispatchAction {
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "update");
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

		Map StudBonusPenaltyInfo = new HashMap();
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
		
		String opmode = dynForm.getString("opmode");
		//log.debug("StudConductEdit.opmode:" + opmode);
		if(opmode.equalsIgnoreCase("ok")) return update(mapping,form,request,response);
		else if(opmode.equalsIgnoreCase("cancel"))  return cancel(mapping,form,request,response);
		

		
		setContentPage(session, "score/StudSeldExamUpdateAll.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		setContentPage(request.getSession(false), "score/StudSeldExamUpdateAll.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward update(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("Bonuspenalty.save called!");
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		DynaActionForm aForm = (DynaActionForm)aform;
		
		//int number = Integer.parseInt(form.get("number").toString().trim());
		session.setAttribute("sessionInterval", session.getMaxInactiveInterval());
		int sessionInterval = session.getMaxInactiveInterval();
		session.setMaxInactiveInterval(-1);	//unlimited for session timeout

		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		String campus = aForm.getString("campusInChargeSAF");
		String school = aForm.getString("schoolInChargeSAF");
		String dept = aForm.getString("deptInChargeSAF");
		String departClass  = aForm.getString("classInChargeSAF");
		String depart = "";
		String clazzFilter = "";
		
		Map initMap = new HashMap();
		initMap.put("campus", campus);
		initMap.put("school", school);
		initMap.put("dept", dept);
		initMap.put("departClass", departClass);
		session.setAttribute("SeldExamUpdateInit", initMap);
		
		ActionMessages msgs = new ActionMessages();

		if("All".equals(campus)||"All".equals(school)){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "班級選擇範圍過大"));
			saveMessages(request, msgs);
		}else{
			session.setMaxInactiveInterval(-1);	//seconds , -1 : never session timeout

			if(departClass.equalsIgnoreCase("All")){
				if("All".equals(dept)) clazzFilter = campus + school;
				else clazzFilter = campus + school + dept;
			}else if("All".equals(dept)){
				clazzFilter = campus + school;
			}else{
				clazzFilter = departClass;
			}

			log.debug("StudConductUpdateAll->clazzFilter:" + clazzFilter);
			try {
				msgs = sm.seldExamUpdate(clazzFilter);
				// Just just = justList.get(0);
				if (!msgs.isEmpty()) {
					saveErrors(request, msgs);
					session.setMaxInactiveInterval(sessionInterval); // recovery for session timeout

					setContentPage(session, "score/StudSeldExamUpdateAll.jsp");
					return mapping.findForward("Main");
				}

			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setMaxInactiveInterval(sessionInterval); // recovery for session timeout
				setContentPage(session, "score/StudSeldExamUpdateAll.jsp");
				return mapping.findForward("Main");
			}
		}

		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", clazzFilter + " 扣考紀錄SeldExam更新完成!!!"));
		saveMessages(request, msgs);
		session.setMaxInactiveInterval(sessionInterval);	//recovery for session timeout

		setContentPage(session, "score/StudSeldExamUpdateAll.jsp");
		return mapping.findForward("Main");

	}
		

}
