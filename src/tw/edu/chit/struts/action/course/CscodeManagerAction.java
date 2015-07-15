package tw.edu.chit.struts.action.course;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Csno;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class CscodeManagerAction extends BaseLookupDispatchAction {
	
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		setContentPage(request.getSession(false), "course/CscodeManager.jsp");
		return mapping.findForward("Main");
	}
	
	//新增
	public ActionForward create(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm f = (DynaActionForm) form;
		
		String cscode[]=f.getStrings("cscode");
		String chi_name[]=f.getStrings("chi_name");
		String eng_name[]=f.getStrings("eng_name");
		
		ActionMessages error = new ActionMessages();	
		if(cscode[0].trim().equals("")||chi_name[0].trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "科目代碼與中文名稱不得空白"));
			saveErrors(request, error);
			return query(mapping, form, request, response);
		}
		
		Csno csno=new Csno();
		csno.setChiName(chi_name[0]);
		csno.setCscode(cscode[0]);
		csno.setEngName(eng_name[0]);
		
		try{
			manager.updateObject(csno);
		}catch(Exception e){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立失敗，請檢查重複"));
			saveErrors(request, error);
		}		
		return query(mapping, form, request, response);
	}
	
	//修改
	public ActionForward modify(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm f = (DynaActionForm) form;
		
		String Oid[]=f.getStrings("Oid");
		String cscode[]=f.getStrings("cscode");
		String chi_name[]=f.getStrings("chi_name");
		String eng_name[]=f.getStrings("eng_name");
		
		ActionMessages error = new ActionMessages();
		
		for(int i=1; i<Oid.length; i++){
			
			if(!Oid[i].equals("")){
				Csno csno=(Csno)manager.hqlGetBy("FROM Csno WHERE Oid='"+Oid[i]+"'").get(0);
				if(cscode[i].trim().equals("")&&chi_name[i].trim().equals("")&&eng_name[i].trim().equals("")){					
					//刪除前確認					
					if(manager.ezGetInt("SELECT COUNT(*)FROM ScoreHist WHERE cscode='"+csno.getCscode()+"'")>0){						
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已有成績記載的科目代碼不得刪除"));						
					}else{
						manager.executeSql("DELETE FROM Csno WHERE Oid='"+Oid[i]+"'");						
					}
					
				}else{
					//修改
					if(manager.ezGetInt("SELECT COUNT(*)FROM ScoreHist WHERE cscode='"+csno.getCscode()+"'")>0){
						if(!cscode[i].equals(csno.getCscode()))
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已有成績記載的科目代碼不得修改代碼"));						
					}else{
						csno.setCscode(cscode[i].trim());						
					}
					csno.setChiName(chi_name[i].trim());
					csno.setEngName(eng_name[i].trim());
					manager.updateObject(csno);
				}				
			}
		}
		
		if(!error.isEmpty()){
			saveErrors(request, error);
		}
		
		return query(mapping, form, request, response);
	}
	
	//查詢
	public ActionForward query(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)throws Exception {		
			
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm f = (DynaActionForm) form;		
		
		String cscode=f.getStrings("cscode")[0];
		String chi_name=f.getStrings("chi_name")[0];
		String eng_name=f.getStrings("eng_name")[0];
		
		if(cscode.length()>2||chi_name.length()>1){
			request.setAttribute("css", manager.ezGetBy("SELECT * FROM Csno WHERE " +
					"cscode LIKE '"+cscode+"%' AND chi_name LIKE'"+chi_name+"%' AND eng_name LIKE'"+eng_name+"%' ORDER BY cscode"));
		}else{
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息	
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請至少輸入代碼3碼，或是1個中文字"));
			saveErrors(request, error);	
		}
		return mapping.findForward("Main");
	}
	
	protected Map<String, String> getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create","create");		
		map.put("Query","query");
		map.put("Modify", "modify");		
		return map;
	}
}
