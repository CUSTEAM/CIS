package tw.edu.chit.struts.action.portfolio;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.EpsActEvaluator;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 評審管理
 * @author JOHN
 *
 */
public class EvaluatorManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		//System.out.println("SELECT Oid, name FROM Eps_Act_parameter");
		request.setAttribute("allAction", manager.ezGetBy("SELECT Oid, name FROM Eps_Act_parameter"));
		setContentPage(request.getSession(false), "portfolio/EvaluatorManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		String act_oid=aForm.getString("act_oid");
		String techid=aForm.getString("techid");
		
		request.setAttribute("evaluators", manager.ezGetBy("SELECT ea.Oid, e.idno, e.cname, a.name FROM " +
				"Eps_Act_evaluator ea, empl e, Eps_Act_parameter a WHERE a.Oid=ea.act_oid AND " +
				"e.idno=ea.techid AND ea.techid LIKE'"+techid+"%' AND ea.act_oid='"+act_oid+"'"));
		
		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");	
		
		DynaActionForm aForm = (DynaActionForm) form;
		String act_oid=aForm.getString("act_oid");
		String techid=aForm.getString("techid");
		
		if(!techid.trim().equals("")){
			EpsActEvaluator eae=new EpsActEvaluator();
			eae.setActOid(Integer.parseInt(act_oid));
			eae.setTechid(techid);
			try{
				manager.updateObject(eae);
			}catch(Exception e){
				
			}
			
		}
		
		aForm.set("techid", "");
		return query(mapping, form, request, response);
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm aForm = (DynaActionForm) form;
		String Oid[]=aForm.getStrings("Oid");
		for(int i=0; i<Oid.length; i++){	
			if(!Oid[i].equals("")){
				manager.executeSql("DELETE FROM Eps_Act_evaluator WHERE Oid='"+Oid[i]+"'");
			}			
		}
		
		return query(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Create", "create");
		map.put("Delete", "delete");
		return map;
	}

}
