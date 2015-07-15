package tw.edu.chit.struts.action.sysadmin;

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

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;
import tw.edu.chit.model.Clazz;

public class TermScore2ScoreHistAction extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("StartTransfer","transfer");
		map.put("Cancel", 		"cancel");
		return map;		
	}
	
	private boolean flag_scr2hist = false;

	public ActionForward unspecified(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
	throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);

		String opmode = dynForm.getString("opmode");
		String schoolYear = Toolket.getSysParameter("School_year");
		String schoolTerm = Toolket.getSysParameter("School_term");
				
		Map initValue = new HashMap();
		initValue.put("schoolYear", schoolYear);
		initValue.put("schoolTerm", schoolTerm);
		session.setAttribute("Score2HistInit", initValue);
		
		//log.debug("StudConductEdit.opmode:" + opmode);
		if(opmode.equalsIgnoreCase("StartTransfer")) return transfer(mapping,form,request,response);
		else if(opmode.equalsIgnoreCase("cancel"))  return cancel(mapping,form,request,response);
		setContentPage(request, "sysadmin/TermScore2ScoreHist.jsp");
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
		String tmode = dynForm.getString("tmode");
		
		schoolYear = dynForm.getString("schoolYear");
		schoolTerm = dynForm.getString("schoolTerm");
		
		Map initValue = new HashMap();
		initValue.put("campus", campus);
		initValue.put("school", school);
		initValue.put("dept", dept);
		initValue.put("clazz", departClass);
		initValue.put("schoolYear", schoolYear);
		initValue.put("schoolTerm", schoolTerm);
		initValue.put("tmode", tmode);
		session.setAttribute("Score2HistInit", initValue);
		
		//depart = dynForm.getString("depart");
		
		ActionMessages errors = new ActionMessages();
		ActionMessages msgs = new ActionMessages();
		
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		
		if(flag_scr2hist) {
			errors.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.Score2HistIsRunning"));
		}
		else {
			flag_scr2hist = true;
		}
		int sessionInterval = session.getMaxInactiveInterval();
		
		if(schoolYear.trim().equals("") || schoolTerm.trim().equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "學年，學期必須輸入資料!"));
		}else{
			if(!StringUtils.isNumeric(schoolYear)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1", "學年必須為數字!"));
			}
			if(!StringUtils.isNumeric(schoolTerm)){
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1", "學期必須為數字!"));
			}
		}
		
		//if("All".equals(campus)||"All".equals(school)){
			//msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					//"Course.messageN1", "班級選擇範圍過大"));
		//}
		
		if(!msgs.isEmpty()){
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
			try {
				List<Clazz> classes = Toolket.findAllClasses(clazzFilter);
				int len =  classes.size();
				int count = 0;
				sm.setRunStatus("TermScore2ScoreHist", "1->Start:", 0, 0, 0d, "Start!");
				for(Clazz clazz:classes){
					log.debug("TermScore transfer class:" + clazz.getClassNo());
					if(tmode.equals("0") || 
						(tmode.equals("1") && Toolket.isGraduateClass(clazz.getClassNo()))||
						(tmode.equals("2") && !Toolket.isGraduateClass(clazz.getClassNo()))){
						log.debug("transfered class:" + clazz.getClassNo());
						errors = sm.txTermScore2ScoreHist(schoolYear, schoolTerm, clazz.getClassNo());
					}
					if(!errors.isEmpty()) {
						saveErrors(request, errors);
						break;
					}else{
						errors = sm.txUpdateAvgScore(schoolYear, schoolTerm, clazz.getClassNo());
						if(!errors.isEmpty()) {
							saveErrors(request, errors);
							break;
						}					
					}
					count++;
					sm.setRunStatus("TermScore2ScoreHist", "Insert Into Score History:",
							count, len, (double)count/len, clazz.getClassNo() + 
							"[" + clazz.getClassName() + "]," +count + "/" + len);
				}

				if (!errors.isEmpty()) {
					session.setAttribute("TermScore2HistForm", dynForm.getMap());
					flag_scr2hist = false;
					// session.setAttribute("ScoreInput",
					// aForm.getStrings("scrinput"));
					session.setMaxInactiveInterval(sessionInterval);
					setContentPage(request, "sysadmin/TermScore2ScoreHist.jsp");
					return mapping.findForward("Main");
				}
				sm.setRunStatus("TermScore2ScoreHist", "Finished:", 100, 100, 100d, "yes");
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1", clazzFilter + ":成績轉歷史檔OK!"));
				saveMessages(request, msgs);

			} catch(Exception e){
				flag_scr2hist = false;
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("TermScore2HistForm", dynForm.getMap());
				session.setMaxInactiveInterval(sessionInterval);
				return mapping.findForward("Main");

			}
		}
		
		flag_scr2hist = false;
		setContentPage(request, "sysadmin/TermScore2ScoreHist.jsp");
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
		session.setAttribute("Score2HistInit", initValue);
		
		session.removeAttribute("TermScore2HistForm");
		setContentPage(request, "sysadmin/TermScore2ScoreHist.jsp");
		return mapping.findForward("Main");				
	 }


}
