package tw.edu.chit.struts.action.registration;

import java.text.SimpleDateFormat;
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

import tw.edu.chit.model.Dept;
import tw.edu.chit.model.Member;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class DeptNameManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession(false);
		//DynaActionForm dForm = (DynaActionForm) form;
		
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//ActionMessages msg = new ActionMessages();

		CourseManager manager = (CourseManager) getBean("courseManager");
		List list=manager.ezGetBy("SELECT * FROM dept ORDER BY no");
		Map map;
		List depts=new ArrayList();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String cname;
		for(int i=0; i<list.size(); i++){
			map=new HashMap();
			map.put("no", ((Map)list.get(i)).get("no"));
			map.put("sname", ((Map)list.get(i)).get("sname"));
			map.put("fname", ((Map)list.get(i)).get("fname"));
			map.put("dname", ((Map)list.get(i)).get("dname"));
			
			map.put("engname", ((Map)list.get(i)).get("engname"));
			map.put("Oid", ((Map)list.get(i)).get("Oid"));
			map.put("school_name", ((Map)list.get(i)).get("school_name"));
			
			if(((Map)list.get(i)).get("last_edit_user")!=null){
				cname=new String();
				cname=manager.ezGetString("SELECT cname FROM empl WHERE idno='"+((Map)list.get(i)).get("last_edit_user")+"'");
				if(cname==null || cname.equals("")){
					cname=manager.ezGetString("SELECT cname FROM dempl WHERE idno='"+((Map)list.get(i)).get("last_edit_user")+"'");
				}
				map.put("last_edit_user", "*"+cname);
			}
			
			
			
			if(((Map)list.get(i)).get("last_edit_time")!=null){
				map.put("last_edit_time", sf.format(sf.parse(((Map)list.get(i)).get("last_edit_time").toString())));
			}
			
			depts.add(map);
		}
		
		
		session.setAttribute("depts", depts);

		
		setContentPage(request.getSession(false), "registration/DeptNameManager.jsp");
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
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		//HttpSession session = request.getSession(false);
		DynaActionForm dForm = (DynaActionForm) form;		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		ActionMessages msg = new ActionMessages();
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		
		String no[]=dForm.getStrings("no");
		String sname[]=dForm.getStrings("sname");
		String school_name[]=dForm.getStrings("school_name");
		String fname[]=dForm.getStrings("fname");
		String dname[]=dForm.getStrings("dname");
		String engname[]=dForm.getStrings("engname");
		
		if(no[0].equals("")|| school_name[0].equals("") || fname[0].equals("")){
			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "某些必要欄位不得空白"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		Member me = getUserCredential(request.getSession(false)).getMember();
		Dept dept=new Dept();
		dept.setDname(dname[0]);
		dept.setEngname(engname[0]);
		dept.setFname(fname[0]);
		dept.setLastEditUser(me.getIdno());
		dept.setNo(no[0]);
		dept.setSchoolName(school_name[0]);
		dept.setLastEditTime(new Date());
		dept.setSname(sname[0]);
		
		try{
			manager.updateObject(dept);
		}catch(Exception e){
			e.printStackTrace();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "儲存失敗"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		return unspecified(mapping, form, request, response);

	}
	
	/**
	 * 修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward ok(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm dForm = (DynaActionForm) form;		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		ActionMessages msg = new ActionMessages();
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		String no[]=dForm.getStrings("no");
		String sname[]=dForm.getStrings("sname");
		String school_name[]=dForm.getStrings("school_name");
		String fname[]=dForm.getStrings("fname");
		String dname[]=dForm.getStrings("dname");
		String engname[]=dForm.getStrings("engname");
		String Oid[]=dForm.getStrings("Oid");
		String editCheck[]=dForm.getStrings("editCheck");
		
		Member me = getUserCredential(request.getSession(false)).getMember();
		for(int i=1; i<Oid.length; i++){
			//System.out.println("editCheck["+i+"]="+editCheck[i]);
			if(!editCheck[i].equals("")){
				
				if(!no[i].equals("")){
					
					Dept dept=(Dept)manager.hqlGetBy("FROM Dept WHERE Oid='"+Oid[i]+"'").get(0);
					dept.setDname(dname[i]);
					dept.setEngname(engname[i]);
					dept.setFname(fname[i]);
					dept.setLastEditUser(me.getIdno());
					dept.setNo(no[i]);
					dept.setSchoolName(school_name[i]);
					dept.setLastEditTime(new Date());
					dept.setSname(sname[i]);
					
					try{
						manager.updateObject(dept);
					}catch(Exception e){
						e.printStackTrace();
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", dept.getFname()+"修改失敗"));
												
					}
				}
			}
			
			
			
			
		}
		
		if(!error.isEmpty()){
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}else{
			//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "修改完成"));
			saveMessages(request, msg);
		}
		return unspecified(mapping, form, request, response);

	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("OK", "ok");
		return map;
	}

}
