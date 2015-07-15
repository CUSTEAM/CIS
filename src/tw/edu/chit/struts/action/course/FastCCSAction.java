package tw.edu.chit.struts.action.course;

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

import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 快速加退選
 * @author JOHN
 *
 */
public class FastCCSAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		session.setAttribute("allGroup", manager.ezGetBy("SELECT * FROM CsGroup"));		
				
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","學程管理"));
		//saveMessages(request, msg);

		setContentPage(request.getSession(false), "course/FastCCS.jsp");
		return mapping.findForward("Main");
	}
	
	
	/**
	 * 加選
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm af = (DynaActionForm) form;
		
		String student_no=af.getString("student_no");
		String Dtime_oid=af.getString("Dtime_oid");
		
		
		
		//int Sterm=manager.getSchoolTerm();
		int Sterm=manager.ezGetInt("SELECT Sterm FROM Dtime WHERE Oid="+Dtime_oid);
		
		if(Dtime_oid.trim().equals("")||student_no.trim().equals("")){			
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "學號、課程編號不能為空白"));
			saveErrors(request, error);
			
			return unspecified(mapping, form, request, response);
		}
		
		Dtime d=(Dtime) manager.hqlGetBy("FROM Dtime d WHERE Oid="+Dtime_oid).get(0);
		
		String chi_name=manager.ezGetString("SELECT c.chi_name FROM Csno c, Dtime d WHERE c.cscode=d.cscode AND d.Oid='"+Dtime_oid+"'");
		if(chi_name.equals("")){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查無課程"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}
		
		//重複chi_name選課
		if(manager.ezGetInt("SELECT COUNT(*) FROM ScoreHist s, Csno c WHERE " +
				"s.cscode=c.cscode AND c.chi_name='"+chi_name+"' AND s.student_no='"+student_no+"' AND " +
				"(s.score>=60 || s.score IS NULL)")>0){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "加選失敗, 課程名稱已修得"));
			saveErrors(request, error);
			return query(mapping, form, request, response);			
		}		
		
		//重複cscode選課
		if(manager.ezGetInt("SELECT COUNT(*) FROM ScoreHist s, Dtime d WHERE d.cscode=s.cscode AND " +
		"d.Oid="+Dtime_oid+" AND s.student_no='"+student_no+"' AND (s.score>=60 || s.score IS NULL)")>0||		
		manager.ezGetInt("SELECT COUNT(*)FROM Seld s, Dtime d WHERE s.Dtime_oid=d.Oid AND " +
		"d.cscode='"+d.getCscode()+"' AND s.student_no='"+student_no+"' AND d.Sterm='"+Sterm+"'")>0){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "加選失敗, 課程代碼已修得, 或本學期重複修課"));
			saveErrors(request, error);
			return query(mapping, form, request, response);			
		}
		
		
		
		//超過選課上限 TODO 操他媽是多少？
		if(manager.ezGetInt("SELECT SUM(d.credit) FROM Dtime d, Seld s WHERE " +
				"d.Sterm='"+Sterm+"' AND d.Oid=s.Dtime_oid AND s.student_no='"+student_no+"'")>22){			
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "加選失敗, 超過規定選課上限(22)"));
			saveErrors(request, error);
			return query(mapping, form, request, response);	
		}
		
		//衝堂		
		List thisTime=manager.ezGetBy("SELECT week, begin, end FROM Dtime_class  WHERE Dtime_oid="+Dtime_oid);
		List tmp;
		for(int i=0; i<thisTime.size(); i++){
			try{
				tmp=manager.checkReSelds(student_no, Dtime_oid, ((Map)thisTime.get(i)).get("week").toString(), 
						((Map)thisTime.get(i)).get("begin").toString(), ((Map)thisTime.get(i)).get("begin").toString(), String.valueOf(Sterm));
				
				if(tmp.size()>0){
					ActionMessages error = new ActionMessages();
					
					for(int j=0; j<tmp.size(); j++){
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "加選失敗, " +
								"上課時間重複: 星期"+  ((Map)tmp.get(j)).get("week")+"第"+((Map)tmp.get(j)).get("begin")
								+"至"+((Map)tmp.get(j)).get("end")+"節, "+((Map)tmp.get(j)).get("ClassName")+", "+((Map)tmp.get(j)).get("chi_name")));					
					}
					saveErrors(request, error);
					return query(mapping, form, request, response);
				}
			}catch(Exception e){
				//上課時間設定有誤
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "加選失敗, 課程設定有問題"));
				saveErrors(request, error);
				return query(mapping, form, request, response);
			}
		}
		
		//上限
		try{
			int selected=manager.ezGetInt("SELeCT COUNT(*) FROM Seld WHERE Dtime_oid="+d.getOid());
			if((selected+1)>d.getSelectLimit()){
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "加選失敗, 超過人數上限(已選"+selected+")"));
				saveErrors(request, error);
				return query(mapping, form, request, response);	
			}			
		}catch(Exception e){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "加選失敗, 課程上限設定有問題"));
			saveErrors(request, error);
			return query(mapping, form, request, response);	
		}
		
		try{
			Seld seld=new Seld();
			seld.setDtimeOid(Integer.parseInt(Dtime_oid));
			seld.setStudentNo(student_no);
			manager.updateObject(seld);
			
			try{
				d.setStuSelect(Short.valueOf(String.valueOf(d.getStuSelect()+1)));
				manager.updateObject(d);
			}catch(Exception e){
				//
			}
			
			
		}catch(Exception e){
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","加選錯誤，請檢查衝堂、重複加選等問題"));
			saveMessages(request, msg);
			return unspecified(mapping, form, request, response);
		}
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","加選完成"));
		saveMessages(request, msg);
		return query(mapping, form, request, response);

	}
	
	public ActionForward rob(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm af = (DynaActionForm) form;
		
		String student_no=af.getString("student_no");
		String Dtime_oid=af.getString("Dtime_oid");
		//int Sterm=manager.getSchoolTerm();
		
		if(Dtime_oid.trim().equals("")||student_no.trim().equals("")){			
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "學號、課程編號不能為空白"));
			saveErrors(request, error);
			
			return unspecified(mapping, form, request, response);
		}
		
		if(manager.ezGetInt("SELECT COUNT(*)FROM Dtime WHERE Oid="+Dtime_oid)<1){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查無課程"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}
		
		
		try{
			Seld seld=new Seld();
			seld.setDtimeOid(Integer.parseInt(Dtime_oid));
			seld.setStudentNo(student_no);
			manager.updateObject(seld);
		}catch(Exception e){
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","加選錯誤，請檢查衝堂、重複加選等問題"));
			saveMessages(request, msg);
			return unspecified(mapping, form, request, response);
		}
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","加選完成"));
		saveMessages(request, msg);
		return query(mapping, form, request, response);

	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm af = (DynaActionForm) form;
		
		String student_no=af.getString("student_no");
		String Sterm=af.getString("Sterm");
		try{
			request.setAttribute("myCs", manager.getMyCs(student_no, Sterm));
		}catch(Exception e){
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢錯誤，學生資料有問題"));
			saveMessages(request, msg);
			return unspecified(mapping, form, request, response);
		}
		//ActionMessages msg = new ActionMessages();
		//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢完成"));
		//saveMessages(request, msg);
		return unspecified(mapping, form, request, response);

	}	
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {	
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm af = (DynaActionForm) form;
		String dDtime_oid[]=af.getStrings("dDtime_oid");		
		try{
			for(int i=0; i<dDtime_oid.length; i++){
				if(!dDtime_oid[i].equals(""))
				manager.executeSql("DELETE FROM Seld WHERE Oid="+dDtime_oid[i]);
			}
		}catch(Exception e){
			e.printStackTrace();
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","退選錯誤，已退選或其他問題"));
			saveMessages(request, msg);
			return unspecified(mapping, form, request, response);
		}
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","退選完成"));
		saveMessages(request, msg);
		return query(mapping, form, request, response);
	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("AddCourse", "add");
		map.put("Query", "query");
		map.put("RemoveCourse", "delete");
		map.put("Rob", "rob");
		return map;
	}

}
