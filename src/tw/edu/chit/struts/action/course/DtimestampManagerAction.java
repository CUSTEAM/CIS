package tw.edu.chit.struts.action.course;

import java.util.HashMap;
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

import tw.edu.chit.model.Dtimestamp;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class DtimestampManagerAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		session.setAttribute("allSchool", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='School'"));
		session.setAttribute("allCampus", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='Campus'"));		
		setContentPage(request.getSession(false), "course/DtimestampManage.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		String Cidno=aForm.getString("Cidno");
		String Sidno=aForm.getString("Sidno");
		String Sweek=aForm.getString("Sweek");
		
		if(!Cidno.equals("")&&!Sidno.equals("")&&!Sweek.equals("")){
			Dtimestamp ds=new Dtimestamp();
			ds.setCidno(Cidno);
			ds.setSidno(Sidno);
			ds.setDsweek(Integer.parseInt(Sweek));
			ds.setUpdater(c.getMember().getIdno());
			try{
				manager.updateObject(ds);
			}catch(Exception e){
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存失敗, 可能有重複時段"));
				saveErrors(request, error);
			}
		}else{
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存失敗, 基本資料不可有空白"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		
		return search(mapping, form, request, response);

	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		
		String DSvalue[]=aForm.getStrings("DSvalue");
		String DSreal[]=aForm.getStrings("DSreal");
		String DSbegin[]=aForm.getStrings("DSbegin");
		String DSend[]=aForm.getStrings("DSend");
		String Oid[]=aForm.getStrings("Oid");
		String checked[]=aForm.getStrings("checked");	
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		for(int i=0; i<Oid.length; i++){
			
			Dtimestamp ds;
			if(!checked[i].equals("")){
				if(!DSbegin[i].equals("")&&!DSend.equals("")){
					
					ds=(Dtimestamp)manager.hqlGetBy("FROM Dtimestamp WHERE Oid='"+Oid[i]+"'").get(0);
					ds.setDsbegin(DSbegin[i]);
					ds.setDsend(DSend[i]);
					ds.setDsvalue(DSvalue[i]);
					ds.setUpdater(c.getMember().getIdno());	
					
					if(!DSreal[i].equals("")){
						ds.setDsreal(Integer.parseInt(DSreal[i]));
					}
					try{
						manager.updateObject(ds);
					}catch(Exception e){
						ActionMessages error = new ActionMessages();
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存失敗, 可能有重複時段"));
						saveErrors(request, error);
					}
					
				}else{					
					manager.executeSql("DELETE FROM Dtimestamp WHERE Oid='"+Oid[i]+"'");
				}
			}
		}		
		return search(mapping, form, request, response);
	}
	
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		
		DynaActionForm aForm = (DynaActionForm) form;
		String Cidno=aForm.getString("Cidno");
		String Sidno=aForm.getString("Sidno");
		String Sweek=aForm.getString("Sweek");
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		
		request.setAttribute("times", manager.ezGetBy("SELECT d.*, e.cname FROM Dtimestamp d LEFT OUTER JOIN empl e ON d.updater=e.idno " +
				"WHERE d.Cidno LIKE '"+Cidno+"%' AND d.Sidno LIKE '"+Sidno+"%' AND d.Dsweek LIKE '"+Sweek+"%'"));
		
		//ActionMessages msg = new ActionMessages();
		//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查詢完成"));
		//saveMessages(request, msg);
		
		return mapping.findForward("Main");

	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Save", "save");
		map.put("Search", "search");
		return map;
	}

}
