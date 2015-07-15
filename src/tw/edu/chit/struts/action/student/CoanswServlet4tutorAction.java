package tw.edu.chit.struts.action.student;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import tw.edu.chit.model.Coansw;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 新版教學評量
 * @author Shawn
 *
 */
public class CoanswServlet4tutorAction extends BaseAction{

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager"); 
		HttpSession session = request.getSession(false);
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		UserCredential user=(UserCredential) session.getAttribute("Credential");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Date coanswStart=sf.parse(manager.ezGetString("SELECT Value FROM Parameter WHERE Name='CoanswStart'"));
		Date coanswEnd=sf.parse(manager.ezGetString("SELECT Value FROM Parameter WHERE Name='CoanswEnd'"));
		DynaActionForm aForm = (DynaActionForm)form;
		
		
		String tAnswer[]=aForm.getStrings("tAnswer");
		int x=manager.ezGetInt("SELECT COUNT(*) FROM CoQuestion4tutor WHERE type='M' ORDER BY sequence");
		StringBuilder sb=new StringBuilder();
		
		String techid=manager.ezGetString("SELECT e.idno FROM ClassInCharge cc, empl e WHERE e.Oid=cc.EmpOid AND " +
				"cc.ModuleOids LIKE '|88|%' AND cc.ClassNo='"+user.getStudent().getDepartClass()+"' LIMIT 1");
		
		for(int i=0; i<tAnswer.length; i++){
			sb=sb.append(tAnswer[i]);
		}		
		if(sb.length()==x){
			try{
				manager.executeSql("INSERT INTO Coansw4tutor(answer, student_no, depart_class, techid)VALUES" +
						"('"+sb+"', '"+user.getMember().getAccount()+"', '"+user.getStudent().getDepartClass()+"','"+techid+"')");
			}catch(Exception e){
				e.printStackTrace();
			}
		}		
		
		Date date=new Date();
		manager.setCoansFoRm(date, session, user, coanswStart, coanswEnd); //重新判斷是否再塞教學評量
		return mapping.findForward("Main");
	}
}


