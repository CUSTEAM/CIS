package tw.edu.chit.struts.action.personnel;

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
 * 人事查核
 * @author JOHN
 *
 */
public class HRCheckerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		setContentPage(request.getSession(false), "personnel/HRChecker.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 執行
	 */
	public ActionForward executeCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm checkForm = (DynaActionForm)form;
		
		String checkOpt=checkForm.getString("checkOpt");
		
		if(checkOpt.equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","...?"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		List list;//共用容器
		//本學期導師審核
		if(checkOpt.equals("checkTutor")){
			//TODO 導師看ClassInCharge
			list=manager.ezGetBy("SELECT e.idno, e.cname, cc.ClassNo, cl.ClassName FROM " +
					"empl e, UnitBelong ub, ClassInCharge cc, Class cl " +
					"WHERE cl.ClassNo=cc.ClassNo AND ub.EmpOid=e.Oid AND " +
					"cc.EmpOid=e.Oid AND ub.UnitNo='T2' AND cc.ModuleOids LIKE'%86%' GROUP BY cc.ClassNo ORDER BY e.idno DESC");
			session.setAttribute("checkType", "checkTutor");
			session.setAttribute("checkTutor", list);
		}
		
		//身份證字號
		if(checkOpt.equals("checkId")){
			list=manager.ezGetBy("SELECT idno, cname FROM empl");
			List tmp=new ArrayList();
			for(int i=0; i<list.size(); i++){				
				if(!manager.checkIdno(((Map)list.get(i)).get("idno").toString())){
					((Map)list.get(i)).put("check", "錯誤");
					tmp.add(list.get(i));
				}
			}
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "排序檢查結果"));
			saveMessages(request, msg);
			session.setAttribute("checkType", "checkId");
			session.setAttribute("checkId", tmp);
			tmp=null;
		}
		
		
		list=null;
		setContentPage(request.getSession(false), "personnel/HRChecker.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Execute", "executeCheck");
		return map;
	}

}
