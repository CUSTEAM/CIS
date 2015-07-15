package tw.edu.chit.struts.action.registration;

import java.text.SimpleDateFormat;
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

import tw.edu.chit.model.QuitResume;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class QuitResumeManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward editQuitResume(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		DynaActionForm stForm=(DynaActionForm)form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		String studentNo=(String)session.getAttribute("NO");
		
		
		String depart_class[]=(String[])stForm.get("depart_class");
		//String student_no[]=(String[])stForm.get("student_no");
		String occur_year[]=(String[])stForm.get("occur_year");
		String occur_term[]=(String[])stForm.get("occur_term");
		String occur_date[]=(String[])stForm.get("occur_date");
		String occur_docno[]=(String[])stForm.get("occur_docno");
		String recov_year[]=(String[])stForm.get("recov_year");
		String recov_term[]=(String[])stForm.get("recov_term");
		String recov_date[]=(String[])stForm.get("recov_date");
		String recov_docno[]=(String[])stForm.get("recov_docno");
		String Oid[]=(String[])stForm.get("Oid");
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0; i<Oid.length; i++){
			
			try{
				QuitResume q=new QuitResume();
				q=(QuitResume) manager.hqlGetBy("FROM QuitResume WHERE Oid='"+Oid[i]+"'").get(0);
				q.setDepartClass(depart_class[i]);				
				if(!occur_year[i].trim().equals("")){
					q.setOccurYear(Short.parseShort(occur_year[i]));
				}else{
					q.setOccurYear(null);
				}
				q.setOccurTerm(occur_term[i]);				
				if(!occur_date[i].trim().equals("")){
					q.setOccurDate(sf.parse(manager.convertDate(occur_date[i])));
				}else{
					q.setOccurDate(null);
				}
				if(!recov_date[i].trim().equals("")){
					q.setRecovDate(sf.parse(manager.convertDate(recov_date[i])));
				}else{
					q.setRecovDate(null);
				}				
				q.setOccurDocno(occur_docno[i]);
				q.setRecovDocno(recov_docno[i]);
				q.setRecovTerm(recov_term[i]);				
				if(!recov_year[i].trim().equals("")){
					q.setRecovYear(Short.parseShort(recov_year[i]));
				}else{
					q.setRecovYear(null);
				}
				manager.updateObject(q);
				
			}catch(Exception e){
				e.printStackTrace();
				ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","儲存失敗, 請檢查輸入值"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}
		}
		
		List list=manager.ezGetBy("SELECT * FROM QuitResume WHERE student_no='"+studentNo+"'");
		for(int i=0; i<list.size(); i++){			
			if(((Map)list.get(i)).get("occur_date")!=null&&!((Map)list.get(i)).get("occur_date").equals("")){
				((Map)list.get(i)).put("occur_date", manager.convertDate(((Map)list.get(i)).get("occur_date").toString()));
			}
			if(((Map)list.get(i)).get("recov_date")!=null&&!((Map)list.get(i)).get("recov_date").equals("")){
				((Map)list.get(i)).put("recov_date", manager.convertDate(((Map)list.get(i)).get("recov_date").toString()));
			}
		}		
		session.setAttribute("myQuitResume", list); //存歷年休退記錄
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",studentNo+"資料修改完成"));
		saveMessages(request, msg);	//完成
		session.setAttribute("myGmark", manager.getGmark(studentNo)); //存歷年更變記錄
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("EditQuitResume", "editQuitResume");
		return map;
	}

}
