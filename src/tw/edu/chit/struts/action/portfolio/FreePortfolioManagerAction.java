package tw.edu.chit.struts.action.portfolio;

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

import tw.edu.chit.model.EpsContent;
import tw.edu.chit.model.EpsFree;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 自訂學習歷程管理
 * @author JOHN
 *
 */
public class FreePortfolioManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {

		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		
		String table_oid=request.getParameter("table_oid");
		if(table_oid!=null)
		if(table_oid.equals("")){
			table_oid=null;
		}
		//DynaActionForm aForm = (DynaActionForm) form;
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();//取得使用者帳號	
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		request.setAttribute("Uid", Uid);
		
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
			return mapping.findForward("Main");
		}
		
		//有建立的情況
		//存入我預覽網址
		request.setAttribute("myUrl", manager.myPortfolioUrl(Uid));
		
		//取得所有類別
		String category="C";
		if(c.getMember().getPriority().equals("A")){
			category="A";
		}
		List allTable=manager.ezGetBy("SELECT * FROM Eps_table WHERE sys='1' AND category='"+category+"' ORDER BY sys, sequence");
		List bllTable=manager.ezGetBy("SELECT * FROM Eps_table WHERE sys='0' AND category='"+category+"' ORDER BY sys, sequence");
		request.setAttribute("allTable", allTable);
		request.setAttribute("bllTable", bllTable);
		request.setAttribute("cllTable", manager.ezGetBy("SELECT tag FROM Eps_free WHERE Uid='"+Uid+"' GROUP BY tag"));
		
		
		request.setCharacterEncoding("UTF-8");		
		String tag=(String)request.getAttribute("tag");		
		if(tag==null){
			tag=request.getParameter("tag");
		}
		session.setAttribute("tag", tag);
		request.setAttribute("freeTag", manager.ezGetBy("SELECT tag FROM Eps_free WHERE Uid='"+Uid+"' GROUP BY tag"));
		request.setAttribute("free", manager.ezGetBy("SELECT * FROM Eps_free WHERE Uid='"+Uid+"' AND tag='"+tag+"'"));
		
		setContentPage(request.getSession(false), "portfolio/EPortfolioManager.jsp");
		return mapping.findForward("Main");
	}	
	
	/**
	 * 新增
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		
		String Uid=c.getMember().getAccount();
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		DynaActionForm aForm = (DynaActionForm) form;
		
		String tag[]=aForm.getStrings("tag");
		String newTag[]=aForm.getStrings("newTag");
		String title[]=aForm.getStrings("title");
		String content[]=aForm.getStrings("content");
		
		
		if(!content[0].trim().equals("")&& tag[0].equals("")&& newTag[0].trim().equals("")){
			newTag[0]="歷程主要名稱未設定";
		}
		
		if(!tag[0].trim().equals("")|| !newTag[0].trim().equals("")){
			if(title[0].trim().equals("")){
				title[0]="歷程細節名稱未設定";
			}
		}
		
		String s=tag[0];
		if(s.trim().equals("")){
			s=newTag[0];
		}			
		if(!title[0].trim().equals("") && !content[0].trim().equals("") && !s.trim().equals("")){			
			EpsFree ef=new EpsFree();
			ef.setContent(content[0]);
			ef.setTag(s);
			ef.setTitle(title[0]);
			ef.setEditime(new Date());
			ef.setUid(Uid);
			manager.updateObject(ef);
		}		
		request.setAttribute("tag", s);
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 更新
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		HttpSession session = request.getSession(false);
		
		DynaActionForm aForm = (DynaActionForm) form;		
		String Oid[]=aForm.getStrings("Oid");
		String tag[]=aForm.getStrings("tag");
		String title[]=aForm.getStrings("title");
		String content[]=aForm.getStrings("content");
		//String file[]=aForm.getStrings("file");
		String newTag[]=aForm.getStrings("newTag");
		
		String PageTag=(String)session.getAttribute("tag");//頁面帶入			
		String s;
		for(int i=0; i<Oid.length; i++){			
			s=tag[i];
			if(s.trim().equals("")){
				s=newTag[i];
			}
			if(content[i].trim().equals("") || s.trim().equals("") || title[i].trim().equals("")){//刪除
				manager.executeSql("DELETE FROM Eps_free WHERE Oid='"+Oid[i]+"'");				
			}else{
				EpsFree ef=(EpsFree)manager.hqlGetBy("FROM EpsFree WHERE Oid='"+Oid[i]+"'").get(0);
				ef.setContent(content[i]);
				ef.setTag(s);
				ef.setTitle(title[i]);
				//ef.setFile(file[i]);
				manager.updateObject(ef);
			}			
		}
		//System.out.println("PageTag="+PageTag);
		//如果刪或改到該類別沒了，給新建類別頁面
		if(manager.ezGetInt("SELECT COUNT(*)FROM Eps_free WHERE tag='"+PageTag+"'")<1){			
			request.setAttribute("tag", "newTag");
		}else{
			request.setAttribute("tag", PageTag);
		}
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Update", "update");
		return map;
	}

}
