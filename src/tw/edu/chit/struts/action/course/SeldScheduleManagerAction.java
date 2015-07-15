package tw.edu.chit.struts.action.course;

import java.util.HashMap;
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

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class SeldScheduleManagerAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		//session.setAttribute("allDept", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='Dept' AND idno <>'0' ORDER BY sequence"));
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","..."));
		//saveMessages(request, msg);
		
		request.setAttribute("slist", manager.ezGetBy("SELECT s.*, e.cname FROM Seld_schedule s LEFT OUTER JOIN empl e ON e.idno=s.idno ORDER BY s.depart, s.grade"));
		
		
		
		
		setContentPage(request.getSession(false), "course/SeldScheduleManage.jsp");
		return mapping.findForward("Main");
	}
	
	
	
	
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm sForm = (DynaActionForm) form;
		String Oid[]=sForm.getStrings("Oid");
		String level[]=sForm.getStrings("level");
		String term[]=sForm.getStrings("term");
		String depart[]=sForm.getStrings("depart");
		String grade[]=sForm.getStrings("grade");
		String begin[]=sForm.getStrings("begin");
		String begin_time[]=sForm.getStrings("begin_time");
		String end[]=sForm.getStrings("end");
		String end_time[]=sForm.getStrings("end_time");
		String max[]=sForm.getStrings("max");
		String min[]=sForm.getStrings("min");
		String nor[]=sForm.getStrings("nor");
		
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");	
		
		//SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(int i=0; i<Oid.length; i++){
			
			
			if(!Oid[i].trim().equals(""))
			try{
				begin[i]=begin[i]+" "+begin_time[i];
				end[i]=end[i]+" "+end_time[i];
				
				manager.executeSql("UPDATE Seld_schedule SET level='"+level[i]+"', " +
						"depart='"+depart[i]+"', grade='"+grade[i]+"', begin='"+begin[i]+"', " +
						"end='"+end[i]+"', max='"+max[i]+"', min='"+min[i]+"', nor='"+nor[i]+"', idno='"+c.getMember().getIdno()+"', term='"+term[i]+"' "+
						"WHERE Oid='"+Oid[i]+"'");
				
			}catch(Exception e){
				e.printStackTrace();
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請檢查欄位"));
				saveErrors(request, error);
				return unspecified(mapping, form, request, response);
			}
			
			
		}
		
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "修改完成"));
		saveMessages(request, msg);
		
			
		return unspecified(mapping, form, request, response);
	}
	
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();		
		map.put("Modify", "edit");
		return map;
	}
}