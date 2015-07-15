package tw.edu.chit.struts.action.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 退費管理
 * @author JOHN
 *
 */
public class RefundManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//HttpSession session = request.getSession(false);
		setContentPage(request.getSession(false), "course/RefundManager.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 設定範圍
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm refundForm = (DynaActionForm) form;
		
		int sterm=manager.getSchoolTerm();
		
		String depart_class=refundForm.getString("depart_class");
		if(depart_class.trim().length()<5){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必須是整個年級"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		
		List list=manager.ezGetBy("SELECT * FROM Class WHERE ClassNo LIKE'"+depart_class+"%'");
		Map map;
		List refunds=new ArrayList();
		int countStu;//學生人數
		int countCou;//課程數
		for(int i=0; i<list.size(); i++){
			countStu=manager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE depart_class='"+((Map)list.get(i)).get("ClassNo")+"'");
			countCou=manager.ezGetInt("SELECT COUNT(*)FROM Dtime WHERE depart_class='"+((Map)list.get(i)).get("ClassNo")+"' AND Sterm='"+sterm+"'");
			if(countStu>0){//人數大於0
				map=new HashMap();
				map.put("ClassNo", ((Map)list.get(i)).get("ClassNo"));
				map.put("ClassName", ((Map)list.get(i)).get("ClassName"));
				map.put("countStu", countStu);
				map.put("countCou", countCou);
				refunds.add(map);
			}
			
		}
		session.setAttribute("refunds", refunds);
		
		setContentPage(request.getSession(false), "course/RefundManager.jsp");
		return mapping.findForward("Main");

	}
	
	
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);

		session.removeAttribute("refunds");

		setContentPage(request.getSession(false), "course/RefundManager.jsp");
		return mapping.findForward("Main");

	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Clear", "clear");
		return map;
	}

}
