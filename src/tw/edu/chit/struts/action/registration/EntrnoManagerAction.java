package tw.edu.chit.struts.action.registration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Entrno;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.SeldCouFilter;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;
/**
 * 文號管理
 * @author JOHN
 *
 */
public class EntrnoManagerAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);		
		setContentPage(request.getSession(false), "registration/EntrnoManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm eForm = (DynaActionForm) form;
		String classLess=eForm.getString("classLess");
		String first_stno=eForm.getString("first_stno");
		String second_stno=eForm.getString("second_stno");
		String permission_no=eForm.getString("permission_no");
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		if(permission_no.trim().equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請檢查文號"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		if(!classLess.trim().equals("")){
			
			List list=manager.ezGetBy("SELECT ClassNo FROM Class WHERE ClassNo LIKE'"+classLess+"%'");
			
			String start;
			String end;
			List stds;
			Entrno entrno;
			for(int i=0; i<list.size(); i++){
				stds=manager.ezGetBy("SELECT student_no FROM stmd WHERE depart_class='"+((Map)list.get(i)).get("ClassNo")+"'");
				try{					
					entrno=new Entrno();
					entrno.setFirstStno(((Map)stds.get(0)).get("student_no").toString());
					entrno.setSecondStno(((Map)stds.get(stds.size()-1)).get("student_no").toString());
					entrno.setPermissionNo(permission_no);
					manager.updateObject(entrno);
				}catch(Exception e){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",
							"建立失敗:"+((Map)list.get(i)).get("ClassNo")+"<br>"));
					continue;					
				}				
			}			
		}else{
			if(first_stno.equals("")||second_stno.equals("")){
				return unspecified(mapping, form, request, response);
			}
			try{
				Entrno e=new Entrno();
				e.setFirstStno(first_stno);
				e.setSecondStno(second_stno);
				e.setPermissionNo(permission_no);
				manager.updateObject(e);
			}catch(Exception e){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請檢查文號或學號重複"));
			}
			
			
		}
		
		if(error.isEmpty()){
			error=null;
		}else{
			saveErrors(request, error);
		}
		
		
		return search(mapping, form, request, response);
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm eForm = (DynaActionForm) form;
		String Oid[]=eForm.getStrings("Oid");
		CourseManager manager = (CourseManager) getBean("courseManager");
		try{
			for(int i=0; i<Oid.length; i++){
				if(!Oid[i].equals("")){
					manager.executeSql("DELETE FROM entrno WHERE Oid="+Oid[i]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		//HttpSession session = request.getSession(false);		
		//setContentPage(request.getSession(false), "registration/EntrnoManager.jsp");
		return search(mapping, form, request, response);
	}
	
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm eForm = (DynaActionForm) form;
		String permission_no=eForm.getString("permission_no");
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		
		request.setAttribute("entrnos", manager.ezGetBy("SELECT * FROM entrno " +
		"WHERE permission_no LIKE '"+permission_no+"%' ORDER BY Oid DESC"));
		
		
		return unspecified(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Search", "search");
		map.put("Create", "create");
		map.put("Delete", "delete");
		return map;
	}

}
