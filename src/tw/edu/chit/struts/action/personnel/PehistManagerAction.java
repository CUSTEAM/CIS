package tw.edu.chit.struts.action.personnel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.EmplContract;
import tw.edu.chit.model.EmplGrdschl;
import tw.edu.chit.model.EmplPehist;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 實務經驗管理
 * @author JOHN
 *
 */
public class PehistManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward editPestud(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");	
		//DynaActionForm cForm = (DynaActionForm) form;
		return mapping.findForward("Main");
	}
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("EditPestud", "editPestud");
		return map;
	}

}
