package tw.edu.chit.struts.action.registration;

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

import tw.edu.chit.model.Gmark;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class GmarkManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward editGmark(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		DynaActionForm stForm=(DynaActionForm)form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		String studentNo=(String)session.getAttribute("NO");		
		
		String gmark_oid[]=stForm.getStrings("gmark_oid");
		String gmark_school_year[]=stForm.getStrings("gmark_school_year");
		String gmark_school_term[]=stForm.getStrings("gmark_school_term");
		String gmark_occur_status[]=stForm.getStrings("gmark_occur_status");
		String gmark_cause[]=stForm.getStrings("gmark_cause");
		String gmark_remark[]=stForm.getStrings("gmark_remark");
		String gmark_delete[]=stForm.getStrings("gmark_delete");
		
		//System.out.println(studentNo+"舊的長度為"+gmark_oid.length);
		
		Gmark gmark;
		for(int i=0; i<gmark_oid.length; i++){
			
			//修改已存的
			if(!gmark_oid[i].equals("")){	
				
				if(gmark_delete[i].trim().equals("")){//修改
					gmark=(Gmark)manager.hqlGetBy("FROM Gmark WHERE Oid='"+gmark_oid[i]+"'").get(0);
					gmark.setOccurCause(gmark_cause[i]);
					gmark.setOccurStatus(gmark_occur_status[i]);
					gmark.setRemark(gmark_remark[i]);
					gmark.setSchoolTerm(Short.parseShort(gmark_school_term[i]));
					gmark.setSchoolYear(Short.parseShort(gmark_school_year[i]));
					gmark.setStudentNo(studentNo);
					try{
						manager.updateObject(gmark);
					}catch(Exception e){
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","儲存失敗, 請檢查"));
					}
				}else{
					manager.executeSql("DELETE FROM Gmark WHERE Oid='"+gmark_oid[i]+"'");
				}
				
			}else{
				
				if(!gmark_school_year[i].trim().equals("")&& !gmark_school_term[i].equals("")){
					gmark=new Gmark();
					gmark.setOccurCause(gmark_cause[i]);
					gmark.setOccurStatus(gmark_occur_status[i]);
					gmark.setRemark(gmark_remark[i]);
					gmark.setSchoolTerm(Short.parseShort(gmark_school_term[i]));
					gmark.setSchoolYear(Short.parseShort(gmark_school_year[i]));
					gmark.setStudentNo(studentNo);
					try{
						manager.updateObject(gmark);
					}catch(Exception e){
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","儲存失敗, 請檢查"));
					}					
				}
			}
		}
		
		/*新增的gmark
		 Gmark gmark;
			if(gmark_delete[i].equals("")){				
				gmark=(Gmark)manager.hqlGetBy("FROM Gmark WHERE Oid='"+gmark_oid[i]+"'").get(0);
				gmark.setOccurCause(gmark_cause[i]);
				gmark.setOccurStatus(gmark_occur_status[i]);
				gmark.setRemark(gmark_remark[i]);
				gmark.setSchoolTerm(Short.parseShort(gmark_school_term[i]));
				gmark.setSchoolYear(Short.parseShort(gmark_school_year[i]));
				gmark.setStudentNo(studentNo);
				try{
					System.out.println(gmark.getStudentNo());
					//manager.updateObject(gmark);
				}catch(Exception e){
					
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","儲存失敗, 請檢查相同學期重複狀態 ")); 					
				}
				
			}else{
				manager.executeSql("DELETE FROM Gmark WHERE Oid='"+gmark_oid[i]+"'");
			}
		if(!a_gmark_school_year.trim().equals("")){
			Gmark gmark=new Gmark();
			gmark.setOccurCause(a_gmark_cause);
			gmark.setOccurStatus(a_gmark_occur_status);
			gmark.setRemark(a_gmark_remark);
			gmark.setSchoolTerm(Short.parseShort(a_gmark_school_term));
			gmark.setSchoolYear(Short.parseShort(a_gmark_school_year));
			gmark.setStudentNo(studentNo);
			try{
				manager.updateObject(gmark);
			}catch(Exception e){
				System.out.println(gmark.getStudentNo());
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","儲存失敗, 請檢查相同學期重複狀態 ")); 				
			}				
		}
		if(!error.isEmpty()){
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		*/
		
		//stForm.reset(mapping, request);		
		if(!error.isEmpty()){
			saveErrors(request, error);
			return mapping.findForward("Main");
		}else{
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",studentNo+"資料修改完成"));
			saveMessages(request, msg);	//完成
		}
		
		session.setAttribute("myGmark", manager.getGmark(studentNo)); //存歷年更變記錄
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("EditGmark", "editGmark");
		return map;
	}

}
