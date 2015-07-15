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

import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.dao.StudAffairJdbcDAO;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class TimeOffDataExchangeAction extends BaseLookupDispatchAction {
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
		

		
		setContentPage(session, "studaffair/TimeOffDataExchange.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		setContentPage(request.getSession(false), "studaffair/TimeOffDataExchange.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward update(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("Bonuspenalty.save called!");
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		StudAffairJdbcDAO dao = (StudAffairJdbcDAO)getBean("studAffairJdbcDAO");
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
		String DateEnd  = aForm.getString("DateEnd");
		String pmode  = aForm.getString("pmode");
		String depart = "";
		String clazzFilter = "";

		Map initMap = new HashMap();
		initMap.put("campus", campus);
		initMap.put("school", school);
		initMap.put("dept", dept);
		initMap.put("departClass", departClass);
		initMap.put("DateEnd", DateEnd);
		session.setAttribute("TimeOffDataExchangeInit", initMap);
		
		ActionMessages msgs = new ActionMessages();
		
		if(!Toolket.isValidDate(DateEnd)){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "結束日期輸入錯誤!"));					
		}

		if("All".equals(campus)||"All".equals(school)){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "班級選擇範圍過大"));
		}
		
		if(!msgs.isEmpty()){
			saveMessages(request, msgs);
			session.setMaxInactiveInterval(sessionInterval); // recovery for session timeout
			setContentPage(session, "studaffair/TimeOffDataExchange.jsp");
			return mapping.findForward("Main");
			
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
			
			int studs = dao.getRecordsCount("Select count(*) From stmd s Where depart_class like '" + clazzFilter +"%'");
			if(studs > 1000){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "本次轉檔學生超過1000人,請縮小選擇轉檔班級範圍!!!"));
				saveMessages(request, msgs);
				session.setMaxInactiveInterval(sessionInterval); // recovery for session timeout
				setContentPage(session, "studaffair/TimeOffDataExchange.jsp");
				return mapping.findForward("Main");
			}
			
			log.debug("TimeOffDataExchange->clazzFilter:" + clazzFilter);
			//System.out.println(clazzFilter); ==================>>>>>>>>>>>>>>>>>>
			try {
				msgs = sm.timeOffDataExchange(clazzFilter, DateEnd, pmode);
				
				
				// Just just = justList.get(0);
				if (!msgs.isEmpty()) {
					saveErrors(request, msgs);
					session.setMaxInactiveInterval(sessionInterval); // recovery for session timeout

					setContentPage(session, "studaffair/TimeOffDataExchange.jsp");
					return mapping.findForward("Main");
				}

			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setMaxInactiveInterval(sessionInterval); // recovery for session timeout
				setContentPage(session, "studaffair/TimeOffDataExchange.jsp");
				return mapping.findForward("Main");
			}
		}

		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", clazzFilter + " 曠缺資料轉換完成!!!"));
		saveMessages(request, msgs);
		session.setMaxInactiveInterval(sessionInterval);	//recovery for session timeout

		setContentPage(session, "studaffair/TimeOffDataExchange.jsp");
		return mapping.findForward("Main");

	}
		

}
