package tw.edu.chit.struts.action.portfolio;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 個人競賽管理
 * @author Shawn
 *
 */
public class SingleScoreAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");		
		//List list=manager.ezGetBy("SELECT * FROM Eps_Act_parameter");		
		//request.setAttribute("allAct", manager.changeListDate(manager.ezGetBy("SELECT * FROM Eps_Act_parameter"), new String[]{"sign_start","sign_end", "judge_start", "judge_end"}));
		setContentPage(request.getSession(false), "portfolio/SingleScore.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");		
		//List list=manager.ezGetBy("SELECT * FROM Eps_Act_parameter");		
		//request.setAttribute("allAct", manager.changeListDate(manager.ezGetBy("SELECT * FROM Eps_Act_parameter"), new String[]{"sign_start","sign_end", "judge_start", "judge_end"}));
		//setContentPage(request.getSession(false), "portfolio/ActivitiesManager.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		return map;
	}
}
