package tw.edu.chit.struts.action.ex.Leo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.domain.UserCredential;

import tw.edu.chit.model.ClassEx;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.impl.DefaultManagerImpl;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class SelectStmd_leoAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		
		HttpSession session = request.getSession(false);
		session.setAttribute("myOpen", "close");
		session.setAttribute("myTest", "");
		setContentPage(request.getSession(false), "ex/SelectStmd_leo.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		
		//DynaActionForm eForm = (DynaActionForm) form;
		//DefaultManagerImpl manager = (DefaultManagerImpl) getBean("defaultManager");		
		
		String SqlStmdNo = "   And s.student_no like '"+aForm.getString("stmd_no")+"%'";
		String SqlStmdName = "   And s.student_name like '%"+aForm.getString("stmd_name")+"%'";
		String SqlClassNo = "   And s.depart_class like '"+aForm.getString("class_no")+"%'";
		String SqlClassName = "   And c.ClassName like '%"+aForm.getString("class_name")+"%'";
		
		if(aForm.getString("stmd_no").equals("")){
			SqlStmdNo="";
		}
		if(aForm.getString("stmd_name").equals("")){
			SqlStmdName="";
		}
		if(aForm.getString("class_no").equals("")){
			SqlClassNo="";
		}
		if(aForm.getString("class_name").equals("")){
			SqlClassName="";
		}		
		
		List list=manager.ezGetBy(
				" SELECT s.Oid, s.student_no Stmd_No, s.student_name Stmd_Name, c.ClassName ClassName, s.depart_class" +
				" FROM Text_Stmd s, Text_Class c Where s.depart_class=c.ClassNo " +	
				     SqlStmdNo+SqlStmdName+SqlClassNo+SqlClassName+
				" Order By s.depart_class LIMIT 100");
		
		request.setAttribute("Fn", list);				
		return mapping.findForward("Main");
	}
	
	
	public ActionForward Modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "FN");
		Toolket.resetCheckboxCookie(response, "FNList");
		
		String teacherId = user.getMember().getAccount();		
		String Fids = Toolket.getSelectedIndexFromCookie(request, "FNList");
		String FL_id = Fids.substring(1, Fids.length()-1);
		String FN_id = manager.ezGetString("Select FN_Oid From File_DataList Where Oid = '"+FL_id+"'");
		
		session.setAttribute("ShareType", manager.ezGetBy("SELECT Oid, name FROM code5 Where category = 'FileUpload' Order By Oid"));  //取得分享型態選項		
		session.setAttribute("myOpen", "open");
		session.setAttribute("showType", "Modify");
		session.setAttribute("FL_Oid", FL_id);
		session.setAttribute("FN_Oid", FN_id);		
		
		
		//setContentPage(request.getSession(false), "FILE/File_Upload.jsp");
		return mapping.findForward("Main");
	}
	
	

	
	
	
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Modify", "Modify");
		map.put("Save", "save");
		return map;
	}
}
