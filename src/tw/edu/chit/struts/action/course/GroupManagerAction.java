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

import tw.edu.chit.model.CsGroup;
import tw.edu.chit.model.CsGroupRule;
import tw.edu.chit.model.CsGroupSet;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 學程管理
 * @author JOHN
 *
 */
public class GroupManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		//載入所有學程
		session.setAttribute("allGroup", manager.ezGetBy("SELECT * FROM CsGroup"));		
				
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","學程管理"));
		saveMessages(request, msg);
		
		session.removeAttribute("aGroup");		
		session.removeAttribute("groupType");

		setContentPage(request.getSession(false), "course/GroupManager.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 建立學程
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm gForm = (DynaActionForm) form;
		
		String cname=gForm.getString("cname");

		//空的問題
		if(cname.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","名稱不得空白")); 
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		//重複的問題
		if(manager.ezGetInt("SELECT COUNT(*)FROM CsGroup WHERE cname LIKE'%"+cname.trim()+"%'")>0){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","學程已存在")); 
			saveErrors(request, error);	//身份證字號必須填寫 - 返回錯誤
			return mapping.findForward("Main");
		}		
		
		CsGroup csGroup=new CsGroup();
		csGroup.setCname(cname.trim());
		try{
			manager.updateObject(csGroup);
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立完成"));
			saveMessages(request, msg);
		}catch(Exception e){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立失敗")); 
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		
		
		return unspecified(mapping, form, request, response);

	}
	
	/**
	 * 修改學程 & 載入學程各種資料
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm gForm = (DynaActionForm) form;
		
		if(session.getAttribute("groupType")==null ||session.getAttribute("groupType").equals("editGroup")){
			session.setAttribute("groupType", "editGroup");
		}
		
		//載入所有學制
		request.setAttribute("allSchool", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='School'"));
		//載入所有系所
		request.setAttribute("allDept", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='Dept'"));

		String groupOid=gForm.getString("checkGroupOid");
		
		//未選取
		if(groupOid.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請選取1門學程")); 
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		//學程基本資料
		session.setAttribute("aGroup", manager.ezGetMap("SELECT * FROM CsGroup WHERE Oid='"+groupOid+"'"));
		
		//學程基本規則
		session.setAttribute("aGroupRule", manager.ezGetBy("SELECT * FROM CsGroupRule WHERE group_oid='"+groupOid+"'"));
		
		//學程課程組合
		session.setAttribute("aGroupSet", manager.ezGetBy("SELECT cs.deptNo, c.cscode, cs.*, c.chi_name, " +
				"c5.name as deptName FROM " +
				"CsGroupSet cs, Csno c, code5 c5 WHERE c5.category='Dept' AND c5.idno=cs.deptNo AND c.cscode=cs.cscode " +
				"AND cs.group_oid='"+groupOid+"' ORDER BY cs.deptNo, cs.opt, cs.cscode"));
		
		//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","..."));
		//saveMessages(request, msg);
		
		setContentPage(request.getSession(false), "course/GroupManager.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 修改基本資料
	 */
	public ActionForward editGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm gForm = (DynaActionForm) form;
		
		session.setAttribute("groupType", "editGroup");
		
		String groupOid=gForm.getStrings("groupOid")[0]; //通用Oid
		
		String cname=gForm.getString("cname");
		String ename=gForm.getString("ename");
		String groupEnd=gForm.getString("groupEnd");
		String note=gForm.getString("note");
		
		CsGroup csGroup=(CsGroup) manager.hqlGetBy("FROM CsGroup WHERE Oid="+groupOid).get(0);
		csGroup.setCname(cname.trim());
		csGroup.setEname(ename.trim());
		csGroup.setNote(note.trim());
		if(!groupEnd.trim().equals("")){//非空白則存
			csGroup.setEntrance(groupEnd.trim());
		}
		
		
		try{
			manager.updateObject(csGroup);
			session.setAttribute("aGroup", manager.ezGetMap("SELECT * FROM CsGroup WHERE Oid='"+groupOid+"'"));
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","基本資料修改完成"));
			saveMessages(request, msg);
			
		}catch(Exception e){
			e.printStackTrace();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","修改失敗, 請再試一次")); 
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		return modify(mapping, form, request, response);
		//return mapping.findForward("Main");
	}
	
	/**
	 * 修改規則
	 */
	public ActionForward editGroupRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm gForm = (DynaActionForm) form;
		
		session.setAttribute("groupType", "editRule");
		
		String groupOid=gForm.getStrings("groupOid")[0]; //通用Oid
		
		String groupRuleOid[]=gForm.getStrings("groupRuleOid");
		String schoolNo[]=gForm.getStrings("schoolNo");
		String major[]=gForm.getStrings("major");
		String minor[]=gForm.getStrings("minor");
		String groupRuleEnd[]=gForm.getStrings("groupRuleEnd");
		String outdept[]=gForm.getStrings("outdept");
		
		
		
		
		for(int i=0; i<groupRuleOid.length; i++){
			
			if(groupRuleOid[i].equals("")){//檢查新建
				if(!schoolNo[i].equals("")&&
						!major[i].equals("")&&
						!minor[i].equals("")&&
						!outdept[i].equals("")){//資料齊全立即建立				
					CsGroupRule csGroupRule=new CsGroupRule();
					csGroupRule.setGroupOid(Integer.parseInt(groupOid));
					csGroupRule.setMajor(Float.parseFloat(major[i]));
					csGroupRule.setMinor(Float.parseFloat(minor[i]));
					csGroupRule.setSchoolNo(schoolNo[i]);
					csGroupRule.setOutdept(Float.parseFloat(outdept[i]));
					manager.updateObject(csGroupRule);
				}
				
			}else{//若是原有的
				if(schoolNo[i].equals("")){//若學制空白立即刪除
					manager.executeSql("DELETE FROM CsGroupRule WHERE Oid="+groupRuleOid[i]);
				}else{//全部更新
					CsGroupRule cr=(CsGroupRule) manager.hqlGetBy("FROM CsGroupRule WHERE Oid="+groupRuleOid[i]).get(0);
					
					if(!groupRuleEnd[i].trim().equals("")){
						cr.setEntrance(groupRuleEnd[i].trim());
					}					
					cr.setMajor(Float.parseFloat(major[i]));
					cr.setMinor(Float.parseFloat(minor[i]));					
					cr.setOutdept(Float.parseFloat(outdept[i]));
					
					cr.setSchoolNo(schoolNo[i]);
					
					manager.updateObject(cr);
				}
				
				
			}
			
		}
		
		
		return modify(mapping, form, request, response);

	}
	
	/**
	 * 修改課程組合
	 */
	public ActionForward editGroupSet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm gForm = (DynaActionForm) form;

		session.setAttribute("groupType", "editCs");
		
		String groupOid=gForm.getStrings("groupOid")[0]; //通用Oid
		
		String groupSetOid[]=gForm.getStrings("groupSetOid");
		String groupSetGroup[]=gForm.getStrings("groupSetGroup");
		String cscode[]=gForm.getStrings("cscode");
		String groupSetDeptNo[]=gForm.getStrings("groupSetDeptNo");
		String credit[]=gForm.getStrings("credit");
		String groupSetEnd[]=gForm.getStrings("groupSetEnd");
		String opt[]=gForm.getStrings("opt");
		
		for(int i=0; i<groupSetOid.length; i++){
			
			
			if(groupSetOid[i].equals("")){//檢查新增
				
				if(!cscode[i].equals("")&& !credit[i].equals("")&& !groupSetDeptNo[i].equals("")){
					
					CsGroupSet gs=new CsGroupSet();
					gs.setCredit(Float.parseFloat(credit[i]));
					gs.setCscode(cscode[i]);
					gs.setDeptNo(groupSetDeptNo[i]);
					gs.setGroupOid(Integer.parseInt(groupOid));
					gs.setOpt(opt[i]);
					try{
						manager.updateObject(gs);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}else{//檢查刪除or修改
				
				if(groupSetDeptNo[i].equals("")){
					manager.executeSql("DELETE FROM CsGroupSet WHERE Oid='"+groupSetOid[i]+"'");					
				}else{
					CsGroupSet gs=(CsGroupSet) manager.hqlGetBy("FROM CsGroupSet WHERE Oid='"+groupSetOid[i]+"'").get(0);
					gs.setCredit(Float.parseFloat(credit[i]));
					gs.setCscode(cscode[i]);
					gs.setDeptNo(groupSetDeptNo[i]);
					gs.setGroupOid(Integer.parseInt(groupSetGroup[i]));
					
					if(!groupSetEnd[i].trim().equals("")){
						gs.setEntrance(groupSetEnd[i]);
					}					
					gs.setOpt(opt[i]);
					try{
						manager.updateObject(gs);
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
			}
		}
		
		return modify(mapping, form, request, response);

	}
	
	/**
	 * 完成
	 */
	public ActionForward complete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {	
		return unspecified(mapping, form, request, response);

	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Modify", "modify");
		
		map.put("EditGroup", "editGroup");
		map.put("EditGroupRule", "editGroupRule");
		map.put("EditGroupSet", "editGroupSet");
		map.put("Complete", "complete");
		
		return map;
	}

}
