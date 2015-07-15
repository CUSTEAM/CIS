package tw.edu.chit.struts.action.student;

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

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 我的學程
 * @author JOHN
 *
 */
public class MyCsGroupAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String student_no=c.getMember().getAccount();
		
		Map aStudent=manager.ezGetMap("SELECT st.*, c.*, c5.name as schoolName, c51.name as deptName FROM " +
				"((stmd st LEFT JOIN Class c ON st.depart_class=c.ClassNo)LEFT JOIN " +
				"code5 c5 ON c5.idno=c.SchoolNo)LEFT JOIN code5 c51 ON c51.idno=c.DeptNo WHERE " +
				"c5.category='School' AND c51.category='Dept' AND st.student_no='"+student_no+"'");
		if(aStudent==null){
			aStudent=manager.ezGetMap("SELECT st.*, c.*, c5.name as schoolName, c51.name as deptName FROM " +
					"((Gstmd st LEFT JOIN Class c ON st.depart_class=c.ClassNo)LEFT JOIN " +
					"code5 c5 ON c5.idno=c.SchoolNo)LEFT JOIN code5 c51 ON c51.idno=c.DeptNo WHERE " +
					"c5.category='School' AND c51.category='Dept' AND st.student_no='"+student_no+"'");
		}
		
		request.setAttribute("aStudent", aStudent);			
		request.setAttribute("biGroup", manager.CsGroup4One(student_no, aStudent, false));		
		setContentPage(request.getSession(false), "student/myCsGroup.jsp");		
		return mapping.findForward("Main");
	}
	
	/**
	 * 之前用來比對的
	 * @param myScore
	 * @param aGroup
	 * @return
	 */
	private boolean checkCs(Map myScore, Map aGroup){		
		
		if(myScore.get("DeptNo").equals(aGroup.get("deptNo"))&&	
			myScore.get("cscode").equals(aGroup.get("cscode"))&&
			myScore.get("credit").equals(aGroup.get("credit"))){
			//System.out.println(myScore.get("DeptNo")+"="+aGroup.get("deptNo"));
			//System.out.println(myScore.get("cscode")+"="+aGroup.get("cscode"));
			//System.out.println(myScore.get("credit")+"="+aGroup.get("credit"));
			return true;
		}
		
		return false;
	}
	
	private List getGroupSet(String Oid){
		
		
		return null;
	}
	
	

}
