package tw.edu.chit.struts.action.summer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Sabbr;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

/**
 * 暑修班級管理
 * @author JOHN
 *
 */
public class ClassManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		session.setAttribute("sabbrs", manager.ezGetBy("SELECT * FROM Sabbr ORDER BY no"));
		
		setContentPage(request.getSession(false), "summer/ClassManager.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sform = (DynaActionForm) form;

		String no[]=sform.getStrings("no");
		String name[]=sform.getStrings("name");
		String Oid[]=sform.getStrings("Oid");
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		
		for(int i=0; i<Oid.length; i++){
			
			if(no[i].trim().equals("")){
				if(manager.ezGetInt("SELECT COUNT(*)FROM Sdtime WHERE depart_class='"+manager.ezGetString("SELECT no FROM Sabbr WHERE Oid="+Oid[i])+"'")>0){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", no[i]+"班級已開課無法刪除"));
				}else{
					System.out.println("delete");
					manager.executeSql("DELETE FROM Sabbr WHERE Oid='"+Oid[i]+"'");
				}
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", no[i]+name[i]+"已刪除"));
			}else{
				manager.executeSql("UPDATE Sabbr SET no='"+no[i]+"', name='"+name[i]+"' WHERE Oid='"+Oid[i]+"'");
			}
		}
		if(!msg.isEmpty()){
			saveMessages(request, msg);
		}
		return unspecified(mapping, form, request, response);

	}
	
	/**
	 * 新增
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sform = (DynaActionForm) form;
		
		String aNo=sform.getString("aNo");
		String aName=sform.getString("aName");
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		if(aNo.trim().length()<6){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "班級代碼長度為6碼"));
		}
		if(aName.trim().length()<2){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "班級名稱至少2個字"));
		}
		if(manager.ezGetInt("SELECT COUNT(*)FROM Sabbr WHERE no='"+aNo+"'")>0){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "班級已存在"));
		}
		if(!error.isEmpty()){
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		Sabbr sabbr=new Sabbr();
		sabbr.setName(aName);
		sabbr.setNo(aNo);
		manager.updateObject(sabbr);
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", aNo+aName+"建立完成"));
		saveMessages(request, msg);
		
		return unspecified(mapping, form, request, response);
	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Modify", "modify");
		map.put("Create", "create");		
		return map;
	}

}
