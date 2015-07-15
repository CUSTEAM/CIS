package tw.edu.chit.struts.action.portfolio;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.EpsContent;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 以EportfolioManagerAction取代
 * @author JOHN
 *
 */
public class PortfolioManagerAction extends BaseLookupDispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {

		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);		
		
		String table_oid=request.getParameter("table_oid");
		DynaActionForm aForm = (DynaActionForm) form;
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();//取得使用者帳號	
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
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
		
		request.setAttribute("myUrl", manager.myPortfolioUrl(Uid));
		//取得所有類別
		List allTable=manager.ezGetBy("SELECT * FROM Eps_table WHERE sys='0' ORDER BY sequence");
		request.setAttribute("allTable", allTable);
		if(table_oid!=null){//有點選的話給予點選值
			getFormat(request, table_oid, Uid, false);		
		}else{//給予第1筆
			getFormat(request, ((Map)allTable.get(0)).get("Oid").toString(), Uid, true);
			aForm.set("table_oid", ((Map)allTable.get(0)).get("Oid").toString());
		}
		
		setContentPage(request.getSession(false), "portfolio/PortfolioManager.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 第n筆用的
	 * @param request
	 * @param table_oid
	 * @param Uid
	 */
	private void getFormat(HttpServletRequest request, String table_oid, String Uid, boolean first){
		CourseManager manager = (CourseManager) getBean("courseManager");
		//取得點選的table格式
		request.setAttribute("format", manager.ezGetBy("SELECT ef.*, eft.name as alt " +
				"FROM Eps_field ef, Eps_format eft WHERE ef.type=eft.Oid AND table_oid='" +table_oid+"'"+
				"ORDER BY ef.sequence"));
		
		String table_name=manager.ezGetString("SELECT name FROM Eps_table WHERE Oid='"+table_oid+"'");
		
		//取已存資料筆數		
		List list=manager.ezGetBy("SELECT ef.type, ec.sequence as seq, et.name tableName FROM Eps_content ec, Eps_field ef, " +
				"Eps_format eft, Eps_table et WHERE eft.Oid=ef.type AND et.Oid=ec.table_oid AND " +
				"ec.field_oid=ef.Oid AND ec.table_oid='"+table_oid+"' AND ec.Uid='"+Uid+"'GROUP BY ec.sequence");
		
		if(list.size()>0){//該人員有儲存資料的清況
			for(int i=0; i<list.size(); i++){
				String sql="SELECT ec.Oid as ecOid, ec.content, eft.*, ef.name as fieldName, ef.type, ef.size FROM " +
				"Eps_content ec, Eps_format eft, Eps_field ef WHERE " +
				"ec.table_oid='"+table_oid+"' AND ef.Oid=ec.field_oid AND eft.Oid=ef.type AND " +
				"ec.Uid='"+Uid+"' AND ec.sequence='"+((Map)list.get(i)).get("seq")+"' ORDER BY ef.sequence";
				
				//System.out.println(sql);
				((Map)list.get(i)).put("detail", manager.ezGetBy(sql));
				
				if(!first){
					if(i==list.size()-1){
						request.setAttribute("next", Integer.parseInt(((Map)list.get(i)).get("seq").toString())+1);					
					}
				}else{//若是第1筆
					request.setAttribute("next", Integer.parseInt(((Map)list.get(i)).get("seq").toString())+1);					
				}			
			}
		}else{//該人員無儲存資料的清況			
			request.setAttribute("next", 1);	
		}
		
		
		request.setAttribute("table_name", table_name);
		request.setAttribute("myDate", list);
	}
	
	/**
	 * 更新
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		String contents[]=aForm.getStrings("contents");
		String Oid[]=aForm.getStrings("Oid");
		
		for(int i=0; i<contents.length; i++){
			if(contents[i].trim().equals("")){
				//立刻刪除
				manager.executeSql("DELETE FROM Eps_content WHERE Oid='"+Oid[i]+"'");
			}else{
				manager.executeSql("UPDATE Eps_content SET content='"+contents[i]+"' WHERE Oid='"+Oid[i]+"'");
			}			
		}

		setContentPage(request.getSession(false), "portfolio/PortfolioManager.jsp");
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 新增
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String Uid=c.getMember().getAccount();//取得使用者帳號	
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		DynaActionForm aForm = (DynaActionForm) form;
		String next=aForm.getString("next");
		String table_oid=aForm.getString("table_oid");
		String content[]=aForm.getStrings("aContent");
		String field_oid[]=aForm.getStrings("field_oid");
		
		for(int i=0; i<content.length; i++){
			if(!content[i].trim().equals("")){
				try{
					EpsContent ec=new EpsContent();
					ec.setContent(content[i]);
					ec.setFieldOid(Integer.parseInt(field_oid[i].toString()));
					ec.setSequence(Integer.parseInt(next));
					ec.setTableOid(Integer.parseInt(table_oid));
					ec.setUid(Uid);
					
					manager.updateObject(ec);
				}catch(Exception e){
					e.printStackTrace();
				}				
			}
		}
		return unspecified(mapping, form, request, response);
	}
	
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Update", "update");
		return map;
	}

}
