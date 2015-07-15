package tw.edu.chit.struts.action.portfolio;

import java.util.Date;
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

import tw.edu.chit.model.EpsPages;
import tw.edu.chit.model.EpsUser;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
/**
 * 建立網站
 * @author JOHN
 *
 */
public class SiteManagerAction extends BaseLookupDispatchAction{
	
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
			//ActionMessages msg = new ActionMessages();
			//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "歡迎啟用歷程檔案"));			
			//saveMessages(request, msg);	
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
	
	/**
	 * 立即儲存網站
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		
		DynaActionForm aForm = (DynaActionForm) form;
		String Oid=aForm.getString("Oid");
		String siteDescript=aForm.getString("siteDescript").trim();
		String siteName=aForm.getString("siteName").trim();
		String index=aForm.getString("index").trim();
		//TODO
		String path=aForm.getString("path").replaceAll("[^a-zA-Z]", "");
		//String path=aForm.getString("path");
		String Uid=c.getMember().getAccount();//取得使用者帳號	
		
		
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		
		
		
		if(siteName.equals("")){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","系統自動產生名稱, "));
			siteName="我的e-Portfolio";
		}
		if(siteDescript.equals("") ){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","系統自動產生敘述, "));
			siteDescript="歡迎參觀我的網站";
		}
		
		EpsUser user;
		//全新的網站
		if(Oid.equals("")){
			
			if(manager.ezGetInt("SELECT COUNT(*)FROM Eps_user WHERE path='"+path+"'")>0){
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "「"+path+"」 網站路徑已被使用"));
				saveErrors(request, error);
				return unspecified(mapping, form, request, response);
			}			
			if(path.equals("")){				
				path=c.getMember().getOid().toString();
			}
			
			manager.checkNewPortfolio(Uid, siteName, siteDescript, path);
			return unspecified(mapping, form, request, response);
			
			
			
			
		}else{
			//舊有的網站
			String headerStyle=aForm.getString("headerStyle");//header
			String footStyle=aForm.getString("footStyle");//header
			String bannerTemp=aForm.getString("bannerTemp");//bannerTemp
			String banner=aForm.getString("banner");//header
			String timer=aForm.getString("timer");//header
			
			user=(EpsUser)manager.hqlGetBy("FROM EpsUser WHERE Oid='"+Oid+"'").get(0);
			user.setSiteDescript(siteDescript);
			user.setSiteName(siteName);
			user.setHeaderStyle(Integer.parseInt(headerStyle));
			user.setFootStyle(Integer.parseInt(footStyle));
			//user.setCounter(0);
			if(!timer.equals("")){
				user.setTimer(timer);
			}			
			
			if(bannerTemp.equals("")){//若自行編輯橫幅
				user.setBanner(banner);				
			}else{
				user.setBanner(manager.ezGetString("SELECT template FROM Eps_template WHERE Oid='"+bannerTemp+"'"));
			}
			manager.updateObject(user);	
			
			//存首頁
			EpsPages page=(EpsPages)manager.hqlGetBy("FROM EpsPages WHERE title='index' AND Uid='"+Uid+"'").get(0);
			if(index.length()<1){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","您輸入的歡迎頁面字數太少或空白以致產生了一些問題, "));
			}
			page.setContent(index);
			manager.updateObject(page);
		}
		//System.out.println("index="+index);
		
		//System.out.println(page.getContent());
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","網站編輯完成"));
		saveMessages(request, msg);	//完成	
		setContentPage(request.getSession(false), "portfolio/SiteManager.jsp");
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();//取得使用者帳號
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		manager.executeSql("DELETE FROM Eps_content WHERE Uid='"+Uid+"'");
		manager.executeSql("DELETE FROM Eps_free WHERE Uid='"+Uid+"'");
		manager.executeSql("DELETE FROM Eps_pages WHERE Uid='"+Uid+"'");
		manager.executeSql("DELETE FROM Eps_user WHERE Uid='"+Uid+"'");
		//TODO 刪除FTP上的檔案
		return unspecified(mapping, form, request, response);	
	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Save", "save");
		map.put("Delete", "delete");
		return map;
	}

}
