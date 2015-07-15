package tw.edu.chit.struts.action.portfolio;

import java.util.ArrayList;
import java.util.Date;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.EpsPages;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class PageManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();//取得使用者帳號	
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		//未建立的提示
		if(!manager.portfolioExist(Uid)){
			request.setAttribute("notExist", true);
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
		request.setAttribute("myTag", manager.ezGetBy("SELECT * FROM Eps_pages WHERE Uid='"+Uid+"' AND tag IS NOT NULL GROUP BY tag"));
		request.setAttribute("myUrl", manager.myPortfolioUrl(Uid));
		List list=manager.ezGetBy("SELECT tag FROM Eps_pages WHERE tag is not null AND Uid='"+Uid+"' GROUP BY tag");
		for(int i=0; i<list.size(); i++){			
			((Map)list.get(i)).put("pages", manager.ezGetBy("SELECT Oid, title, editime FROM Eps_pages WHERE tag='"+
					((Map)list.get(i)).get("tag")+"' AND Uid='"+Uid+"'"));
		}		
		
		//修改動作
		if(request.getParameter("updateId")!=null){
			Map page=manager.ezGetMap("SELECT * FROM Eps_pages WHERE " +
					"Oid='"+request.getParameter("updateId")+"'");
			
			if(page.get("Uid").equals(Uid)){
				request.setAttribute("update", page);
			}else{
				page=null;
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "你怎麼可以去改別人的呢?"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}			
		}
		
		//刪除動作
		if(request.getParameter("delId")!=null){			
			Map page=manager.ezGetMap("SELECT * FROM Eps_pages WHERE " +
					"Oid='"+request.getParameter("delId")+"'");			
			if(page.get("Uid").equals(Uid)){
				manager.executeSql("DELETE FROM Eps_pages WHERE Oid='"+request.getParameter("delId")+"'");
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "文章已刪除"));
				saveMessages(request, msg);		
				list=manager.ezGetBy("SELECT tag FROM Eps_pages WHERE tag is not null AND Uid='"+Uid+"' GROUP BY tag");
				for(int i=0; i<list.size(); i++){			
					((Map)list.get(i)).put("pages", manager.ezGetBy("SELECT Oid, title, editime FROM Eps_pages WHERE tag='"+
							((Map)list.get(i)).get("tag")+"' AND Uid='"+Uid+"'"));
				}
			}else{
				page=null;
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "你怎麼可以去刪別人的呢?"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}
						
		}		
		
		request.setAttribute("myPages", list);
		setContentPage(request.getSession(false), "portfolio/PageManage.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		
		DynaActionForm aForm = (DynaActionForm) form;
		
		String newTag=aForm.getString("newTag");
		String tag=aForm.getString("tag");
		
		String content=aForm.getString("content");
		String title=aForm.getString("title");
		String checkNewTag=aForm.getString("checkNewTag");
		
		if(title.trim().equals("")){
			title="無標題";
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "您沒有設定標題, 已由系統自動產生, "));
		}
		
		if(tag.trim().equals("")){
			tag="無分類";
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "您沒有設定分類, 已由系統自動產生, "));
		}		
		
		EpsPages page;
		//若點選「建立新標籤」
		if(!checkNewTag.trim().equals("")){
			
			page=new EpsPages();
			page.setContent(content);
			page.setTag(newTag);
			page.setTitle(title);
			page.setUid(Uid);
			page.setEditime(new Date());			
			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立完成"));
			saveMessages(request, msg);		
			manager.updateObject(page);
		//在舊有標籤下編輯
		}else{			
			page=new EpsPages();
			page.setContent(content);
			page.setTag(tag);
			page.setTitle(title);
			page.setUid(Uid);
			page.setEditime(new Date());			
			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立完成"));
			saveMessages(request, msg);		
			manager.updateObject(page);			
		}
		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		/*try{//學生或校友？
			Uid=c.getStudent().getStudentNo();
		}catch(Exception e){
			try{
				Uid=c.getGstudent().getStudentNo();
			}catch(Exception e1){
				Uid=c.getGstudent().getAccount();
			}
		}*/
		
		DynaActionForm aForm = (DynaActionForm) form;
		
		String newTag=aForm.getString("newTag");
		String tag=aForm.getString("tag");
		String Oid=aForm.getString("Oid");
		String content=aForm.getString("content");
		String title=aForm.getString("title");
		//String checkNewTag=aForm.getString("checkNewTag");
		
		if(title.trim().equals("")){
			title="無標題";
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "您沒有設定標題, 已由系統自動產生, "));
		}
		
		if(tag.trim().equals("")){
			tag="無分類";
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "您沒有設定分類, 已由系統自動產生, "));
		}		
		
		EpsPages page=(EpsPages)manager.hqlGetBy("FROM EpsPages WHERE Oid='"+Oid+"'").get(0);
		
		
		page.setContent(content);
		//若新增標籤
		if(newTag.equals("")){
			page.setTag(tag);
		}else{
			page.setTag(newTag);
		}		
		page.setTitle(title);
		page.setUid(Uid);
		page.setEditime(new Date());			
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "更新完成"));
		saveMessages(request, msg);		
		manager.updateObject(page);
		
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Update", "update");
		map.put("Create", "create");
		return map;
	}

}
