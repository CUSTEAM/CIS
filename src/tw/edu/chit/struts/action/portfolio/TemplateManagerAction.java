package tw.edu.chit.struts.action.portfolio;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.EpsUser;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 模版管理
 * @author JOHN
 *
 */
public class TemplateManagerAction extends BaseLookupDispatchAction{
	/*
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();//取得使用者帳號	
		
		//未建立的提示
		if(!manager.portfolioExist(Uid)){
			request.setAttribute("notExist", true);
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請先建立網站! 系統已自動重新定位至「網站管理」"));
			saveMessages(request, msg);	//完成
			
			Map mySite=manager.ezGetMap("SELECT * FROM Eps_user WHERE Uid='"+Uid+"'");
			if(mySite!=null){
				request.setAttribute("mySite", mySite);
				request.setAttribute("myIndex", manager.ezGetString("SELECT content FROM Eps_pages WHERE title='index' AND Uid='"+Uid+"'"));
			}			
			
			
			request.setAttribute("headers", manager.ezGetBy("SELECT * FROM Eps_template WHERE type='H'"));
			request.setAttribute("footers", manager.ezGetBy("SELECT * FROM Eps_template WHERE type='F'"));
			request.setAttribute("banners", manager.ezGetBy("SELECT * FROM Eps_template WHERE type='B'"));
			
			setContentPage(request.getSession(false), "portfolio/SiteManager.jsp");
			//setContentPage(request.getSession(false), "portfolio/TemplateManager.jsp");
			return mapping.findForward("Main");
		}
		
		request.setAttribute("myTemplate", manager.ezGetMap("SELECT * FROM Eps_user WHERE Uid='"+Uid+"'"));//取得使用者所用模板
		request.setAttribute("templates", manager.ezGetBy("SELECT * FROM Eps_template WHERE type='S'"));//模版列表		
		
		request.setAttribute("myUrl", manager.myPortfolioUrl(Uid));		
		setContentPage(request.getSession(false), "portfolio/TemplateManager.jsp");
		return mapping.findForward("Main");
	}
	*/
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		
		String Uid=c.getMember().getAccount();//取得使用者帳號	
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		request.setAttribute("myUrl", manager.myPortfolioUrl(Uid));
		
		Map mySite=manager.ezGetMap("SELECT * FROM Eps_user WHERE Uid='"+Uid+"'");		
		if(!manager.portfolioExist(Uid)){
			request.setAttribute("notExist", true);
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "歡迎啟用歷程檔案"));
			saveMessages(request, msg);	
		}		
		
		if(mySite!=null){
			request.setAttribute("mySite", mySite);
			request.setAttribute("myIndex", manager.ezGetString("SELECT content FROM Eps_pages WHERE title='index' AND Uid='"+Uid+"'"));
		}
		
		request.setAttribute("headers", manager.ezGetBy("SELECT * FROM Eps_template WHERE type='H'"));
		request.setAttribute("footers", manager.ezGetBy("SELECT * FROM Eps_template WHERE type='F'"));
		request.setAttribute("banners", manager.ezGetBy("SELECT * FROM Eps_template WHERE type='B'"));
		request.setAttribute("timers", manager.ezGetBy("SELECT * FROM Eps_template WHERE type='T'"));
		request.setAttribute("myTemplate", manager.ezGetMap("SELECT * FROM Eps_user WHERE Uid='"+Uid+"'"));//取得使用者所用模板
		request.setAttribute("templates", manager.ezGetBy("SELECT * FROM Eps_template WHERE type='S'"));//模版列表
		setContentPage(request.getSession(false), "portfolio/SiteManager.jsp");
		return mapping.findForward("Main");
	}	
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		//UserCredential c = (UserCredential) session.getAttribute("Credential");
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();//取得使用者帳號	
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		DynaActionForm aForm = (DynaActionForm) form;
		String sysTemplate=aForm.getString("checkTemplate");
		String userTemplate=aForm.getString("userTemplate");
		
		String template;		
		if(sysTemplate.equals("") && userTemplate.trim().equals("")){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "至少要選1種樣式"));
			saveErrors(request, error);			
			return unspecified(mapping, form, request, response);
		}
		
		boolean exist=false;
		//TODO 校友問題需不需要理會？ 學生遲早變校友
		/*tring studentNo="";
		try{
			studentNo=manager.ezGetString("SELECT student_no FROM stmd WHERE student_no='"+c.getStudent().getStudentNo()+"'");
			if(studentNo==null){
				studentNo=manager.ezGetString("SELECT student_no FROM Gstmd WHERE student_no='"+c.getGstudent().getStudentNo()+"'");
			}			
		}catch(Exception e){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "至少要選1種樣式"));
			saveErrors(request, error);			
			return unspecified(mapping, form, request, response);
		}
		*/
		
		try{
			if(manager.ezGetInt("SELECT COUNT(*)FROM Eps_user WHERE Uid='"+Uid+"'")>0){
				exist=true;
			}
			
			if(manager.ezGetInt("SELECT COUNT(*)FROM Eps_user WHERE Uid='"+Uid+"'")>0){
				exist=true;
			}
		}catch(Exception e){
			
		}
		
		
		//存入標準樣式
		if(!sysTemplate.equals("")){
			
			template=manager.ezGetString("SELECT template FROM Eps_template WHERE Oid='"+sysTemplate+"'");
			
			if(exist){
				EpsUser epsuser=(EpsUser) manager.hqlGetBy("FROM EpsUser WHERE Uid='"+Uid+"'").get(0);
				epsuser.setTemplate(template);
				manager.updateObject(epsuser);
			}else{
				EpsUser epsuser=new EpsUser();
				epsuser.setUid(Uid);
				epsuser.setTemplate(template);
				manager.updateObject(epsuser);
			}
			
			
			
		}else{
			
			if(exist){
				EpsUser epsuser=(EpsUser) manager.hqlGetBy("FROM EpsUser WHERE Uid='"+Uid+"'").get(0);
				epsuser.setTemplate(userTemplate);
				manager.updateObject(epsuser);
			}else{
				EpsUser epsuser=new EpsUser();
				epsuser.setUid(Uid);
				epsuser.setTemplate(userTemplate);
				manager.updateObject(epsuser);
			}
		}		
		
		
		
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","樣式編輯完成"));
		saveMessages(request, msg);	//完成
		
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Save", "save");
		return map;
	}

}
