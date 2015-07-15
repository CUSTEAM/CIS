package tw.edu.chit.struts.action.portfolio.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.EpsVitae;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class EditVitaeAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();//取得使用者帳號
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		
		request.setAttribute("content", manager.ezGetMap("SELECT * FROM Eps_vitae WHERE student_no='"+Uid+"'"));		
		setContentPage(request.getSession(false), "portfolio/student/EditVitae.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();//取得使用者帳號
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		
		
		DynaActionForm aForm = (DynaActionForm) form;		
		EpsVitae vitae;
		if(manager.ezGetInt("SELECT COUNT(*) FROM Eps_vitae WHERE student_no='"+Uid+"'")>0){
			//update
			try{
				vitae=(EpsVitae) manager.hqlGetBy("FROM EpsVitae WHERE student_no='"+Uid+"'").get(0);
				vitae.setContentEn(aForm.getString("en"));
				vitae.setContentZh(aForm.getString("ch"));
				manager.updateObject(vitae);
			}catch(Exception e){
				e.printStackTrace();
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "儲存失敗，請檢查不正確的文字內容"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}			
		}else{
			//insert
			try{
				vitae=new EpsVitae();
				System.out.println("uid="+Uid);
				vitae.setStudentNo(Uid);
				vitae.setContentEn(aForm.getString("en"));
				vitae.setContentZh(aForm.getString("ch"));
				manager.updateObject(vitae);
			}catch(Exception e){
				e.printStackTrace();
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "儲存失敗，請檢查不正確的文字內容"));
				saveErrors(request, error);
				return mapping.findForward("Main");
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
