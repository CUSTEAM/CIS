package tw.edu.chit.struts.action.course;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class CreateAccountBySelfAction extends BaseLookupDispatchAction{
	
	public ActionForward checkMyAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		DynaActionForm crForm = (DynaActionForm)form;
		String username = crForm.getString("username");
		HttpSession session = request.getSession(false);
		
		int st=summerManager.ezGetInt("SELECT COUNT(*) FROM stmd WHERE student_no='"+username+"'");
		int te=summerManager.ezGetInt("SELECT COUNT(*) FROM empl WHERE idno='"+username+"'");
		System.out.println(username);
		StringBuffer report=new StringBuffer("帳號"+username+"測試結果:<br>");
		if(st>0){
			report.append("學籍存在, ");
			int stpass=summerManager.ezGetInt("SELECT COUNT(*) FROM wwpass WHERE username='"+username+"'");
			if(stpass>0){
				report.append("帳號存在! <br>請不要拿自己或同學開玩笑!<br>");
			}else{
				report.append("帳號不存在!");
				//TODO 建立帳號
			}
		}else{
			report.append("學籍不存在<br>");
		}
		
		if(te>0){
			report.append("教職員工存在, ");
			int stpass=summerManager.ezGetInt("SELECT COUNT(*) FROM wwpass WHERE username='"+username+"'");
			if(stpass>0){
				report.append("帳號存在! <br>請不要拿自己或同事開玩笑!<br>");
			}else{
				report.append("帳號不存在!");
				//TODO 建立帳號
			}
		}else{
			report.append("教職員工不存在");
		}
		
		session.setAttribute("report", report.toString());
		return mapping.findForward("CreateAccountBySelf");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("CheckMyAccount", "checkMyAccount");
		return map;
	}

}
