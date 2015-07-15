package tw.edu.chit.struts.action.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;


import tw.edu.chit.model.Desd;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class RewardAction extends BaseLookupDispatchAction{
	
	/**
	 * 初始資料,帶出獎懲紀錄資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		
		String userID = user.getMember().getAccount();
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("userName", user.getMember().getName());  //取得登入者姓名		
		
		List<Desd> desd = manager.ezGetBy(
				"Select K1.Oid, K1.ddate, K1.no, K1.name, K1.name1, K1.cnt1, K2.name2, K2.cnt2 " +
				"From (Select D.`Oid`, D.ddate, D.no, C.name, C5.name name1, D.cnt1 From desd D, code2 C, code5 C5 " +
				"      Where D.reason = C.no And D.kind1 = C5.idno And C5.category = 'BonusPenalty' And D.`student_no` = '"+userID+"') K1 " +
				"LEFT OUTER JOIN (Select D.`Oid`, C5.name name2, D.cnt2 From desd D, code2 C, code5 C5" +
				"                 Where D.reason = C.no And D.kind2 = C5.idno And C5.category = 'BonusPenalty' And D.`student_no` = '"+userID+"') K2" +
				" On K1.Oid = K2.Oid"
		);  		
		
		session.setAttribute("Desd_List", desd);
		
		setContentPage(request.getSession(false), "student/Reward.jsp");
		return mapping.findForward("Main");
	}
	
	@Override
	protected Map<String, String> getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		
		return map;
	}

}
