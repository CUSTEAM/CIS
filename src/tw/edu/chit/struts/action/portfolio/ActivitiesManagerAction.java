package tw.edu.chit.struts.action.portfolio;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.EpsActParameter;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class ActivitiesManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		//List list=manager.ezGetBy("SELECT * FROM Eps_Act_parameter");		
		request.setAttribute("allAct", manager.changeListDate(manager.ezGetBy("SELECT * FROM Eps_Act_parameter"), new String[]{"sign_start","sign_end", "judge_start", "judge_end", "end"}));
		setContentPage(request.getSession(false), "portfolio/ActivitiesManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm aForm = (DynaActionForm) form;
		String Oid[]=aForm.getStrings("Oid");
		String name[]=aForm.getStrings("name");
		String sign_start[]=aForm.getStrings("sign_start");
		String sign_end[]=aForm.getStrings("sign_end");
		String judge_start[]=aForm.getStrings("judge_start");
		String judge_end[]=aForm.getStrings("judge_end");
		String end[]=aForm.getStrings("end");
		
		String delOid[]=aForm.getStrings("delOid");
		ActionMessages error = new ActionMessages();
		
		ActionMessages msg = new ActionMessages();		
		EpsActParameter eap;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=1; i<Oid.length; i++){
			
			if(name[i].trim().equals("") || !delOid[i].equals("")){				
				manager.executeSql("DELETE FROM Eps_Act_parameter WHERE Oid='"+Oid[i]+"'");
				manager.executeSql("DELETE FROM Eps_Act_evaluator WHERE act_oid='"+Oid[i]+"'");
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","已刪除"));
				saveMessages(request, msg);	//完成				
			}else{				
				if(	!sign_start[i].trim().equals("")&&
					!sign_end[i].trim().equals("")&&
					!judge_start[i].trim().equals("")&&
					!judge_end[i].trim().equals("")){					
					eap=(EpsActParameter) manager.hqlGetBy("FROM EpsActParameter WHERE Oid='"+Oid[i]+"'").get(0);
					eap.setJudgeEnd(sf.parse(manager.convertDate(judge_end[i])));
					eap.setJudgeStart(sf.parse(manager.convertDate(judge_start[i])));
					eap.setName(name[i]);
					eap.setSignEnd(sf.parse(manager.convertDate(sign_end[i])));
					eap.setSignStart(sf.parse(manager.convertDate(sign_start[i])));	
					eap.setEnd(sf.parse(manager.convertDate(end[i])));
					manager.updateObject(eap);					
				}else{					
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", name[i]+"期間不完整無法修改"));					
				}
				if(!error.isEmpty()){
					saveErrors(request, error);
				}				
			}		
		}		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm aForm = (DynaActionForm) form;
		//String Oid[]=aForm.getStrings("Oid");
		String name[]=aForm.getStrings("name");
		String sign_start[]=aForm.getStrings("sign_start");
		String sign_end[]=aForm.getStrings("sign_end");
		String judge_start[]=aForm.getStrings("judge_start");
		String judge_end[]=aForm.getStrings("judge_end");
		String end[]=aForm.getStrings("end");
		
		if(name[0].trim().equals("")|| 
				sign_start[0].trim().equals("")||
				sign_end[0].trim().equals("")||
				judge_start[0].trim().equals("")||
				judge_end[0].trim().equals("")){
				
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "期間不完整"));
				saveErrors(request, error);
				return unspecified(mapping, form, request, response);
			}
			
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");			
			EpsActParameter eap=new EpsActParameter();
			eap.setJudgeEnd(sf.parse(manager.convertDate(judge_end[0])));
			eap.setJudgeStart(sf.parse(manager.convertDate(judge_start[0])));
			eap.setName(name[0]);
			eap.setSignEnd(sf.parse(manager.convertDate(sign_end[0])));
			eap.setSignStart(sf.parse(manager.convertDate(sign_start[0])));
			eap.setEnd(sf.parse(manager.convertDate(end[0])));
			manager.updateObject(eap);
			
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立完成"));
			saveMessages(request, msg);	//完成
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Modify", "modify");
		return map;
	}

}
