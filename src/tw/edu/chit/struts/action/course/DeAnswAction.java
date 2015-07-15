package tw.edu.chit.struts.action.course;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

public class DeAnswAction extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		AdminManager amanager = (AdminManager)getBean("adminManager");
		DynaActionForm dForm = (DynaActionForm)form;
		UserCredential credential = (UserCredential) session.getAttribute("Credential");
		
		String studentNo=credential.getStudent().getStudentNo();
		String dept=manager.ezGetString("SELECT DeptNo FROM Class c, stmd s WHERE c.ClassNo=s.depart_class AND s.student_no='"+studentNo+"'");		
		manager.executeSql("INSERT INTO DeAnsw (Student_no, Depart_class, Score) VALUES " +
				"('"+studentNo+"', '"+dept+"', "+(
				Integer.parseInt(dForm.getString("Q1"))+
				Integer.parseInt(dForm.getString("Q2"))+
				Integer.parseInt(dForm.getString("Q3"))+
				Integer.parseInt(dForm.getString("Q4"))+
				Integer.parseInt(dForm.getString("Q5")))+");");
		studentNo=null;
		dept=null;
		
		session.setAttribute("directory", "Student");
		session.removeAttribute("deQuest");
		List messageList = amanager.findMessagesByCategory("Student");
		request.setAttribute("MessageList", messageList);
		setContentPage(request.getSession(false), "BulletinBoard.jsp");						
		return mapping.findForward("Main");
	}
}
