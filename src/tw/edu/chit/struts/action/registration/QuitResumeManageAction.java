package tw.edu.chit.struts.action.registration;

import java.text.SimpleDateFormat;
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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.QuitResume;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class QuitResumeManageAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		session.removeAttribute("students");		
		setContentPage(request.getSession(false), "registration/QuitResumeManage.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		DynaActionForm sForm = (DynaActionForm) form;
		String student_no=sForm.getString("student_no");
		String occur_type=sForm.getString("occur_type");
		String year=sForm.getString("year");
		String term=sForm.getString("term");
		String classLess=sForm.getString("classLess");		
		
		if(occur_type.equals("")&&year.trim().equals("")&&term.equals("")&&classLess.equals("")&&student_no.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查詢結果過多影響效率, 請指定1種條件"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		if(year.trim().equals("")&&classLess.equals("")&&student_no.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查詢結果過多影響效率, 請至少指定1個學年或1個學生, 或是班級代碼的條件範圍"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}		
		
		StringBuilder sb=new StringBuilder("SELECT s.student_name, c.ClassName, q.* FROM " +
				"(QuitResume q JOIN Gstmd s ON s.student_no LIKE '"+student_no+"%' AND s.student_no=q.student_no AND q.depart_class LIKE '"+classLess+"%') " +
				"LEFT OUTER JOIN Class c ON q.depart_class=c.ClassNo WHERE s.student_no=q.student_no ");		
		
		StringBuilder sb1=new StringBuilder();
		if(occur_type.equals("occur")){
			sb1.append("AND q.occur_year LIKE '"+year+"%' AND q.occur_term LIKE '"+term+"%'");
			sb1.append("ORDER BY q.occur_year, q.occur_term, q.student_no ");
		}
		
		if(occur_type.equals("recov")){
			sb1.append("AND q.recov_year LIKE '"+year+"%' AND q.recov_term LIKE '"+term+"%'");
			sb1.append("ORDER BY q.recov_year, q.recov_term, q.student_no");
		}
		
		
		if(occur_type.equals("")){
			sb1.append("AND (q.recov_year LIKE '"+year+"%' AND q.recov_term LIKE'"+term+"%')");
			sb1.append("OR (q.occur_year LIKE '"+year+"%' AND q.occur_term LIKE'"+term+"%')");
			sb1.append("ORDER BY q.occur_year, q.occur_term, q.recov_year, q.recov_term, q.student_no");
		}
		
		sb.append(sb1);
		//System.out.println(sb);
		List students=manager.ezGetBy(sb.toString());
		sb=new StringBuilder("SELECT s.student_name, c.ClassName, q.* FROM " +
				"(QuitResume q JOIN stmd s ON s.student_no LIKE '"+student_no+"%' AND s.student_no=q.student_no AND q.depart_class LIKE '"+classLess+"%') " +
				"LEFT OUTER JOIN Class c ON q.depart_class=c.ClassNo WHERE s.student_no=q.student_no ");	
		sb.append(sb1);
		//System.out.println(sb);
		students.addAll(manager.ezGetBy(sb.toString()));
		
		for(int i=0; i<students.size(); i++){
			try{
				((Map)students.get(i)).put("occur_date", manager.convertDate(((Map)students.get(i)).get("occur_date").toString()));
			}catch(Exception e){
				((Map)students.get(i)).put("occur_date", "");
			}
			try{
				((Map)students.get(i)).put("recov_date", manager.convertDate(((Map)students.get(i)).get("recov_date").toString()));
			}catch(Exception e){
				((Map)students.get(i)).put("recov_date", "");
			}
		}
		
		request.setAttribute("students", students);		
		return mapping.findForward("Main");
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm sForm = (DynaActionForm) form;
		String student_no=sForm.getString("student_no");
		String student_name=sForm.getString("student_name");
		String occur_type=sForm.getString("occur_type");
		String year=sForm.getString("year");
		String term=sForm.getString("term");		
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息		
		if(student_name.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請指定同學<br>"));
		}
		if(occur_type.equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請指定辦理項目<br>"));
		}
		if(year.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請指定學年<br>"));
		}
		if(term.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請指定學期<br>"));
		}
		if(occur_type.equals("recov")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "復學需要搭配休學記錄, 請查出學生休學記錄, 將該記錄加上復學資料<br>如無休學記錄請先建立休學記錄<br>"));
		}
		
		if(!error.isEmpty()){
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		if(checkMyStudentNo(student_no, request)){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "無權限新增"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		String classLess=manager.ezGetString("SELECT depart_class FROM stmd WHERE student_no='"+student_no+"'");
		if(classLess==null){
			classLess=manager.ezGetString("SELECT depart_class FROM Gstmd WHERE student_no='"+student_no+"'");
		}
		
		try{
			QuitResume quit=new QuitResume();
			quit.setDepartClass(classLess);
			quit.setStudentNo(student_no);
			if(occur_type.equals("occur")){
				//休學
				quit.setOccurDate(new Date());
				quit.setOccurYear(Short.parseShort(year));
				quit.setOccurTerm(term);			
			}
			/*
			if(occur_type.equals("recov")){
				quit.setRecovDate(new Date());
				quit.setRecovYear(Short.parseShort(year));
				quit.setRecovTerm(term);			
			}
			*/
			manager.updateObject(quit);			
		}catch(Exception e){
			e.printStackTrace();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立失敗, 請檢查欄位輸入格式"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}		
		
		List students=manager.ezGetBy("SELECT s.student_name, c.ClassName, q.* FROM QuitResume q, stmd s, Class c WHERE " +
				"s.student_no=q.student_no AND s.depart_class=c.ClassNo AND q.student_no='"+student_no+"'");
		students.addAll(manager.ezGetBy("SELECT s.student_name, c.ClassName, q.* FROM QuitResume q, Gstmd s, Class c WHERE " +
				"s.student_no=q.student_no AND s.depart_class=c.ClassNo AND q.student_no='"+student_no+"'"));
		
		request.setAttribute("students", students);
		
		return mapping.findForward("Main");
	}
	
	public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm sForm = (DynaActionForm) form;
		String Oid[]=sForm.getStrings("Oid");
		String occur_year[]=sForm.getStrings("occur_year");
		String occur_term[]=sForm.getStrings("occur_term");
		String occur_date[]=sForm.getStrings("occur_date");
		String occur_docno[]=sForm.getStrings("occur_docno");
		String recov_year[]=sForm.getStrings("recov_year");
		String recov_term[]=sForm.getStrings("recov_term");
		String recov_date[]=sForm.getStrings("recov_date");
		String recov_docno[]=sForm.getStrings("recov_docno");
		String depart_class[]=sForm.getStrings("depart_class");
		
		String CheckOid[]=sForm.getStrings("CheckOid");
		String CheckChange[]=sForm.getStrings("CheckChange");
		String StudentNo[]=sForm.getStrings("StudentNo");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		for(int i=0; i<Oid.length; i++){
			//有更動記錄的		
			if(!CheckChange[i].trim().equals("")){				
				//有權更動的
				if(!checkMyStudentNo(StudentNo[i], request)){
					
					if(CheckOid[i].equals("D")){
						//刪掉
						manager.executeSql("DELETE FROM QuitResume WHERE Oid='"+Oid[i]+"'");
					}else{
						//更新
						QuitResume quit=(QuitResume) manager.hqlGetBy("FROM QuitResume WHERE Oid='"+Oid[i]+"'").get(0);				
						quit.setDepartClass(depart_class[i]);
						
						if(!occur_date[i].trim().equals("")){
							quit.setOccurDate(sf.parse(manager.convertDate(occur_date[i])));
						}
						if(!occur_year[i].trim().equals("")){
							quit.setOccurYear(Short.parseShort(occur_year[i]));
						}				
						if(!recov_date[i].trim().equals("")){
							quit.setRecovDate(sf.parse(manager.convertDate(recov_date[i])));
						}				
						if(!recov_year[i].trim().equals("")){
							quit.setRecovYear(Short.parseShort(recov_year[i]));
						}
						
						quit.setOccurDocno(occur_docno[i]);
						quit.setOccurTerm(occur_term[i]);
						quit.setRecovDocno(recov_docno[i]);
						quit.setRecovTerm(recov_term[i]);					
						try{
							manager.updateObject(quit);	
						}catch(Exception e){
							error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", StudentNo[i]+"儲存時發生錯誤<br>"));							
						}					
					}					
				}else{					
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "無權限修改"+StudentNo[i]+"<br>"));	
				}
			}			
		}
		
		if(!error.isEmpty()){
			saveErrors(request, error);
		}		
		return query(mapping, form, request, response);
	}
	
	/**
	 * 查詢修改權限
	 * @param studentNo
	 * @param request
	 * @return
	 */
	private boolean checkMyStudentNo(String studentNo, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		String departClass=manager.ezGetString("SELECT depart_class FROM stmd WHERE student_no='"+studentNo+"'");
		
		if(departClass==null){
			departClass=manager.ezGetString("SELECT depart_class FROM Gstmd WHERE student_no='"+studentNo+"'");
		}
		
		UserCredential credential = (UserCredential) session.getAttribute("Credential");
		Clazz[] classes = credential.getClassInChargeAry();
		for(int i=0; i<classes.length; i++){
			if(classes[i].getClassNo().equals(departClass)){
				return false;
			}
		}
		return true;
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Create", "create");
		map.put("Modify", "modify");
		map.put("Delete", "delete");
		return map;
	}

}
