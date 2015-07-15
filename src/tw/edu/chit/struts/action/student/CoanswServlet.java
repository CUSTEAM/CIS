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
public class CoanswServlet extends BaseAction{

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
		
		String dtimeOid[]=aForm.getStrings("dtimeOid");
		String answer[]=aForm.getStrings("answer");
		String ansLength[]=aForm.getStrings("ansLength");
		
		try{
		for(int i=0; i<dtimeOid.length; i++){
			if(answer[i].length()==Integer.parseInt(ansLength[i])){
				Coansw coansw=new Coansw();
				coansw.setAnswer(answer[i]);
				coansw.setDdate(new Date());
				coansw.setDtimeOid(Integer.parseInt(dtimeOid[i]));
				coansw.setStudentNo(user.getStudent().getStudentNo());
				manager.updateObject(coansw);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		Date date=new Date();
		manager.setCoansFoRm(date, session, user, coanswStart, coanswEnd); //重新判斷是否再塞教學評量
		//setContentPage(request.getSession(false), "BulletinBoard.jsp");						
		return mapping.findForward("Main");
	}
}


