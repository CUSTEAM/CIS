package tw.edu.chit.struts.action.course;

import java.util.Date;
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

import tw.edu.chit.model.CsCore;
import tw.edu.chit.model.DtimeReserveClassFixed;
import tw.edu.chit.model.DtimeReserveReady;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;

/**
 * 
 * @author JOHN
 *
 */
public class Seld3ScheduleManagerAction extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)throws Exception {		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");	
		
		request.setAttribute("allRules", manager.ezGetBy("SELECT e.cname, s.* FROM SeldMaxNor s, empl e WHERE s.editor=e.idno and s.editor='"+c.getMember().getIdno()+"'"));		
		request.setAttribute("AllCampus", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Campus'"));
		request.setAttribute("AllSchool", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='School'"));
		request.setAttribute("AllDept", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Dept'"));
		request.setAttribute("AllSchoolType", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Stype'"));
		
		setContentPage(request.getSession(false), "course/Seld3ScheduleManager.jsp");
		return mapping.findForward("Main");
	}	
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm dForm = (DynaActionForm) form;
		
		
		String Oid[]=dForm.getStrings("Oid");
		String Cidno[]=dForm.getStrings("Cidno");
		
		String Sidno[]=dForm.getStrings("Sidno");
		String Didno[]=dForm.getStrings("Didno");
		String Grade[]=dForm.getStrings("Grade");
		String ClassNo[]=dForm.getStrings("ClassNo");
		String max[]=dForm.getStrings("max");
		
		
		Member me = getUserCredential(request.getSession(false)).getMember();
		
		System.out.println(Oid.length);
		if(!Cidno[0].equals("") && !Sidno[0].equals("") && !Sidno[0].equals("")){
			//建立並結束
			
			manager.executeSql("INSERT INTO SeldMaxNor(cidno,sidno,didno,grade,classno,max,depart_class,editor)VALUES" +
					"('"+Cidno[0]+"','"+Sidno[0]+"','"+Didno[0]+"','"+Grade[0]+"','"+ClassNo[0]+"','"+max[0]+"'," +
					"'"+Cidno[0]+Sidno[0]+Didno[0]+Grade[0]+ClassNo[0]+"','"+me.getIdno()+"')");
			
			
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立完成"));
			saveMessages(request, msg);	
			return unspecified(mapping, form, request, response);
		}
		
		
		for(int i=1; i<Oid.length; i++){
			//逐個修改
			System.out.println(Oid[i]+", "+Cidno[i]);
			
			
			if(Cidno[i].trim().equals("")){
				//System.out.println();
				manager.executeSql("DELETE FROM SeldMaxNor WHERE Oid="+Oid[i]);
			}else{
				
				manager.executeSql("UPDATE SeldMaxNor SET cidno='"+Cidno[i]+"', sidno='"+Sidno[i]+"', " +
						"didno='"+Didno[i]+"', grade='"+Grade[i]+"', classno='"+ClassNo[i]+"', max='"+max[i]+"', depart_class='"+Cidno[i]+Sidno[i]+Didno[i]+Grade[i]+ClassNo[i]+"', editor='"+me.getIdno()+"' WHERE Oid="+Oid[i]);
				
				
				
			}
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		return unspecified(mapping, form, request, response);
	}
	
	
	

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Save", "save");
		return map;
	}
}
