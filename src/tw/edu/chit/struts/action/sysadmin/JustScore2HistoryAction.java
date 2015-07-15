package tw.edu.chit.struts.action.sysadmin;

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

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;
import tw.edu.chit.model.Clazz;

public class JustScore2HistoryAction extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("StartTransfer","transfer");
		map.put("Cancel", 		"cancel");
		return map;		
	}
	
	private boolean flag_just2hist = false;

	public ActionForward unspecified(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
	throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);

		String opmode = dynForm.getString("opmode");
				
		//log.debug("StudConductEdit.opmode:" + opmode);
		if(opmode.equalsIgnoreCase("StartTransfer")) return transfer(mapping,form,request,response);
		else if(opmode.equalsIgnoreCase("cancel"))  return cancel(mapping,form,request,response);
		setContentPage(request, "sysadmin/JustScore2History.jsp");
		return mapping.findForward("Main");				
	}
	
	
	public ActionForward transfer(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;

		String schoolYear = "";
		String schoolTerm = "";
		String clazzFilter = "";
		String campus = dynForm.getString("campusInCharge").trim();
		String school = dynForm.getString("schoolInCharge").trim();
		String dept = dynForm.getString("deptInCharge").trim();
		String departClass = dynForm.getString("classInCharge").trim();
		
		// schoolYear = dynaForm.getString("school_year");
		// schoolTerm = dynaForm.getString("school_term");
		schoolYear = Toolket.getSysParameter("School_year");
		schoolTerm = Toolket.getSysParameter("School_term");
		
		Map initValue = new HashMap();
		initValue.put("campus", campus);
		initValue.put("school", school);
		initValue.put("dept", dept);
		initValue.put("clazz", departClass);
		session.setAttribute("Just2HistInit", initValue);
		
		//depart = dynForm.getString("depart");
		
		ActionMessages errors = new ActionMessages();
		ActionMessages msgs = new ActionMessages();
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		if(flag_just2hist) {
			errors.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.Just2HistIsRunning"));
		}
		else {
			flag_just2hist = true;
		}
		int sessionInterval = session.getMaxInactiveInterval();
		
		if("All".equals(campus)||"All".equals(school)){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "班級選擇範圍過大"));
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
			
			//System.out.println(clazzFilter);
			try {
				List<Clazz> classes = Toolket.findAllClasses(clazzFilter);
				int len =  classes.size();
				log.debug("classes:" + len);
				int count = 0;
				sm.setRunStatus("JustScore2History", "1->Start:", 0, 0, 0d, "Start!");
				for(Clazz clazz:classes){
					System.out.println(schoolYear+","+schoolTerm+","+clazz.getClassNo()); //leo 20120131
					errors = sm.txJustScore2History(schoolYear, schoolTerm, clazz.getClassNo());
					if(!errors.isEmpty()) {
						saveErrors(request, errors);
						//break;
					}
					count++;
					sm.setRunStatus("JustScore2History", "Insert Into Just History:",
							count, len, (double)count/len, clazz.getClassNo() + 
							"[" + clazz.getClassName() + "]," +count + "/" + len);
				}

				if (!errors.isEmpty()) {
					session.setAttribute("Just2HistoryForm", dynForm.getMap());
					flag_just2hist = false;
					// session.setAttribute("ScoreInput",
					// aForm.getStrings("scrinput"));
					session.setMaxInactiveInterval(sessionInterval);
					setContentPage(request, "sysadmin/JustScore2History.jsp");
					return mapping.findForward("Main");
				}
				sm.setRunStatus("JustScore2History", "Finished:", 100, 100, 100d, "yes");
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1", clazzFilter + ":操行轉歷史檔OK!"));
				saveMessages(request, msgs);

			} catch(Exception e){
				flag_just2hist = false;
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("Just2HistoryForm", dynForm.getMap());
				session.setMaxInactiveInterval(sessionInterval);
				return mapping.findForward("Main");

			}
		}
		
		flag_just2hist = false;
		setContentPage(request, "sysadmin/JustScore2History.jsp");
		return mapping.findForward("Main");				
		
	}
	
	public ActionForward cancel(ActionMapping mapping,
	 ActionForm form,
	 HttpServletRequest request,
	 HttpServletResponse response)
	 throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		
		String campus = dynForm.getString("campusInCharge").trim();
		String school = dynForm.getString("schoolInCharge").trim();
		String dept = dynForm.getString("deptInCharge").trim();
		String myclass = dynForm.getString("classInCharge").trim();
		
		Map initValue = new HashMap();
		initValue.put("campus", campus);
		initValue.put("school", school);
		initValue.put("dept", dept);
		initValue.put("clazz", myclass);
		session.setAttribute("Just2HistInit", initValue);
		
		//System.out.println("campus="+campus);
		
		session.removeAttribute("Just2HistoryForm");
		setContentPage(request, "sysadmin/JustScore2History.jsp");
		return mapping.findForward("Main");				
	 }


}
