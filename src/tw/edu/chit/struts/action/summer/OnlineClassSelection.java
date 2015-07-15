package tw.edu.chit.struts.action.summer;

import java.util.ArrayList;
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

import tw.edu.chit.model.Sseld;
import tw.edu.chit.model.SseldReject;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class OnlineClassSelection extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		//SummerManager summerManager = (SummerManager) getBean("summerManager");		
		//CourseManager manager=(CourseManager)getBean("courseManager");
		Toolket.resetCheckboxCookie(response, "sselds");
		session.setAttribute("mode", "");
		
		setContentPage(request.getSession(false), "summer/OnlineClassSelection.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 加選
	 */
	public ActionForward addCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		
		session.removeAttribute("scoreHist");
		session.removeAttribute("selected");
		session.removeAttribute("sselds");
		SummerManager summerManager = (SummerManager) getBean("summerManager");	
		CourseManager manager=(CourseManager)getBean("courseManager");
		Toolket.resetCheckboxCookie(response, "sselds");
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		DynaActionForm sform = (DynaActionForm) form;
		String studentId = (String) sform.get("studentId");
		String studentName = (String) sform.get("studentName");
		String departClass = (String) sform.get("departClass");
		String classLess = (String) sform.get("classLess");
		String courseNumber = (String) sform.get("courseNumber");
		String courseName = (String) sform.get("courseName");
		
		if(studentName.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","誰要加選?"));
			saveErrors(request, error);	
		}
		
		if(!error.isEmpty()){
			return mapping.findForward("Main");
		}
		
		//不及格歷史
		session.setAttribute("scoreHist", summerManager.ezGetList("SELECT sc.school_year, sc.school_term, sc.student_no, " +
				"st.student_name, c.chi_name, sc.cscode, sc.opt, sc.credit, sc.score FROM ScoreHist sc, stmd st, Csno c WHERE " +
				"st.student_no=sc.student_no AND c.cscode=sc.cscode AND sc.student_no='"+studentId+"' AND sc.score<60"));
		
		//已選課程
		session.setAttribute("selected", summerManager.ezGetList("SELECT s.student_no, st.student_name, c.chi_name, c.cscode, cl.ClassName," +
				"d.opt, d.credit, d.thour, d.day1, d.day2, d.day3, d.day4, d.day5, d.day6, d.day7, d.seqno, d.status " +
				"FROM Sseld s, Sdtime d, Csno c, stmd st, Class cl WHERE d.cscode=s.cscode AND d.depart_class=cl.ClassNo AND " +
				"s.csdepart_class=d.depart_class AND d.cscode=c.cscode AND st.student_no=s.student_no AND " +
				"s.student_no='"+studentId+"'"));
		
		
		//若確定只選一門課
		if(!classLess.equals("")&&!courseName.equals("")){
			
			if(summerManager.ezGetInt("SELECT COUNT(*) FROM Sdtime WHERE depart_class='"+departClass+"' AND cscode='"+courseNumber+"'")==1){
				try{
					Sseld sseld=new Sseld();
					sseld.setCscode(courseNumber);
					sseld.setCsdepartClass(departClass);
					
					String seqno=manager.ezGetString("SELECT seqno FROM Sdtime WHERE depart_class='"+departClass+"' AND cscode='"+courseNumber+"'");
					sseld.setSeqno(Short.parseShort(seqno.toString()));
					String stdepartClass=(manager.ezGetString("SELECT depart_class FROM stmd WHERE student_no='"+studentId+"'"));
					sseld.setStdepartClass(stdepartClass.toString());
					sseld.setStudentNo(studentId);
					sseld.setWhich("1");
					
					summerManager.saveObj(sseld);

					//重新取得選課結果
					/*
					session.setAttribute("selected", summerManager.ezGetList("SELECT s.student_no, st.student_name, c.chi_name, c.cscode, " +
							"d.opt, d.credit, d.thour, d.day1, d.day2, d.day3, d.day4, d.day5, d.day6, d.day7, d.seqno, s.Oid " +
							"FROM Sseld s, Sdtime d, Csno c, stmd st WHERE d.cscode=s.cscode AND " +
							"s.csdepart_class=d.depart_class AND d.cscode=c.cscode AND st.student_no=s.student_no AND " +
							"s.student_no='"+studentId+"'"));	
					*/
					session.setAttribute("selected", summerManager.ezGetList("SELECT s.student_no, st.student_name, c.chi_name, c.cscode, " +
							"d.opt, d.credit, d.thour, d.day1, d.day2, d.day3, d.day4, d.day5, d.day6, d.day7, d.seqno, d.status " +
							"FROM Sseld s, Sdtime d, Csno c, stmd st WHERE d.cscode=s.cscode AND " +
							"s.csdepart_class=d.depart_class AND d.cscode=c.cscode AND st.student_no=s.student_no AND " +
							"s.student_no='"+studentId+"'"));
					
				//TODO 查選課衝堂, 包括: 先修班和正常學期衝堂、暑修各梯次重疊時間衝堂、梯次中的衝堂...
				}catch(Exception e){
					e.printStackTrace();
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","加選失敗"));
					saveErrors(request, error);
					
					session.setAttribute("student_no", studentId);		
					session.setAttribute("mode", "add");
					return mapping.findForward("Main");
				}
				
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","確認課程存在, 並且為"+studentName+"同學加選"+courseName));
				saveMessages(request, msg);				
				
				session.setAttribute("student_no", studentId);		
				session.setAttribute("mode", "add");
				return mapping.findForward("Main");
			}			
		}
		
		//如果沒開課
		if(summerManager.ezGetInt("SELECT COUNT(*) FROM Sdtime WHERE depart_class LIKE '"+departClass+"%' AND cscode LIKE'"+courseNumber+"%'")==0){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","加選失敗! &nbsp;&nbsp; 因為"+classLess+"沒有開"+courseName));
			saveErrors(request, error);
			
			session.setAttribute("student_no", studentId);		
			session.setAttribute("mode", "add");
			return mapping.findForward("Main");
		}else{
			//查到多筆
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","加選條件模糊，已列出相關課程"));
			saveMessages(request, msg);			
			
		}
		
		
		//待選課程
		session.setAttribute("sselds", summerManager.ezGetList("SELECT d.Oid, cl.no, cl.name, c.chi_name, c.cscode, d.opt, d.credit, d.thour, " +
				"d.day1, d.day2, d.day3, d.day4, d.day5, d.day6, d.day7, d.seqno FROM Sdtime d, Csno c, Sabbr cl " +
				"WHERE c.cscode=d.cscode AND cl.no=d.depart_class AND " +
				"d.depart_class LIKE '"+departClass+"%' AND d.cscode LIKE '"+courseNumber+"%' AND d.status='0' ORDER BY d.depart_class"));
		
		session.setAttribute("student_no", studentId);		
		session.setAttribute("mode", "add");
		return mapping.findForward("Main");
	}
	
	/**
	 * 退選
	 */
	public ActionForward removeCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		Toolket.resetCheckboxCookie(response, "selected");
		
		DynaActionForm sform = (DynaActionForm) form;
		String studentId = (String) sform.get("studentId");
		String studentName = (String) sform.get("studentName");
		String departClass = (String) sform.get("departClass");
		String classLess = (String) sform.get("classLess");
		String courseNumber = (String) sform.get("courseNumber");
		String courseName = (String) sform.get("courseName");
		
		if(studentName.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","誰要退選?"));
			saveErrors(request, error);	
		}
		
		if(!error.isEmpty()){
			return mapping.findForward("Main");
		}
		
		session.setAttribute("selected", summerManager.ezGetList("SELECT s.student_no, st.student_name, c.chi_name, c.cscode, " +
				"d.opt, d.credit, d.thour, d.day1, d.day2, d.day3, d.day4, d.day5, d.day6, d.day7, d.seqno, s.Oid, d.status " +
				"FROM Sseld s, Sdtime d, Csno c, stmd st WHERE d.cscode=s.cscode AND " +
				"s.csdepart_class=d.depart_class AND d.cscode=c.cscode AND st.student_no=s.student_no AND " +
				"s.student_no='"+studentId+"'"));
		
		session.setAttribute("student_no", studentId);
		session.setAttribute("mode", "remove");
		return mapping.findForward("Main");
	}
	
	/**
	 * 確定加選
	 */
	public ActionForward addConfirmed(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		//CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		List selected=getCheckBox(request);	//勾選盒
		
		String student_no=session.getAttribute("student_no").toString(); //承接學號
		List tmplist=summerManager.ezGetList("SELECT depart_class FROM stmd WHERE student_no='"+student_no+"'");
		String stdepart_class=((Map)summerManager.ezGetList("SELECT depart_class FROM stmd WHERE student_no='"+
				student_no+"'").get(0)).get("depart_class").toString();//找到學生班級
		
		if(selected.size()<1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","同學想選哪門課?"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		Map map;
		Sseld sseld;
		try{
			for(int i=0; i<selected.size(); i++){
				
				if(summerManager.checkReOptionForSummer(student_no, ((Map)selected.get(i)).get("Oid").toString())){
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",((Map)selected.get(i)).get("cscode")+"很有可能對正常學期選課發生衝堂"));
				}
				
				sseld=new Sseld();
				sseld.setCscode(((Map)selected.get(i)).get("cscode").toString());
				sseld.setCsdepartClass(((Map)selected.get(i)).get("depart_class").toString());
				sseld.setSeqno(Short.parseShort(((Map)selected.get(i)).get("seqno").toString()));
				sseld.setStdepartClass(stdepart_class);
				sseld.setStudentNo(student_no);
				sseld.setWhich("1");
				
				summerManager.saveObj(sseld);
			}
		}catch(Exception e){
			e.printStackTrace();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","重複加選?"));
			saveErrors(request, error);	
		}	
		
		//重新取得選課結果
		session.setAttribute("selected", summerManager.ezGetList("SELECT s.student_no, st.student_name, c.chi_name, c.cscode, " +
				"d.opt, d.credit, d.thour, d.day1, d.day2, d.day3, d.day4, d.day5, d.day6, d.day7, d.seqno, s.Oid " +
				"FROM Sseld s, Sdtime d, Csno c, stmd st WHERE d.cscode=s.cscode AND " +
				"s.csdepart_class=d.depart_class AND d.cscode=c.cscode AND st.student_no=s.student_no AND " +
				"s.student_no='"+student_no+"'"));
		
		session.removeAttribute("sselds");
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","加選完成!"));
		saveMessages(request, msg);
		Toolket.resetCheckboxCookie(response, "sselds");
		return mapping.findForward("Main");
	}
	
	/**
	 * 確定退選
	 */
	public ActionForward removeConfirmed(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		List selectBox=getCheckBox4Remove(request);	//勾選盒
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		String student_no=session.getAttribute("student_no").toString(); //承接學號
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		if(selectBox.size()<1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","想退哪門課"));
			saveErrors(request, error);	
		}
		
		if(!error.isEmpty()){
			return mapping.findForward("Main");
		}
		
		//開始退
		try{
			SseldReject sseldReject;
			for(int i=0; i<selectBox.size(); i++){
				
				
				
				sseldReject=new SseldReject();
				sseldReject.setCscode(((Map)selectBox.get(i)).get("cscode").toString());
				sseldReject.setCsdepartClass(((Map)selectBox.get(i)).get("csdepart_class").toString());
				sseldReject.setSeqno(Short.parseShort(((Map)selectBox.get(i)).get("seqno").toString()));
				sseldReject.setStdepartClass(((Map)selectBox.get(i)).get("stdepart_class").toString());
				sseldReject.setStudentNo(student_no);
				sseldReject.setWhich("1");
				summerManager.saveObj(sseldReject);
				
				summerManager.removeObject("Sseld", ((Map)selectBox.get(i)).get("Oid").toString());				
			}
			
			
			
		}catch(Exception e){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","殺的時候出問題"));
			saveErrors(request, error);	
			
		}
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","退選完成!"));
		saveMessages(request, msg);
		session.setAttribute("selected", summerManager.ezGetList("SELECT s.student_no, st.student_name, c.chi_name, c.cscode, " +
				"d.opt, d.credit, d.thour, d.day1, d.day2, d.day3, d.day4, d.day5, d.day6, d.day7, d.seqno, s.Oid " +
				"FROM Sseld s, Sdtime d, Csno c, stmd st WHERE d.cscode=s.cscode AND " +
				"s.csdepart_class=d.depart_class AND d.cscode=c.cscode AND st.student_no=s.student_no AND " +
				"s.student_no='"+student_no+"'"));
		
		
		Toolket.resetCheckboxCookie(response, "sselds");
		session.setAttribute("mode", "remove");
		return mapping.findForward("Main");
	}
	
	/**
	 * 取消
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		
		
		
		return mapping.findForward("Main");
	}
	
	/**
	 * 取加選checkbox
	 */
	private List getCheckBox(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "sselds");
		List dtimes = (List) session.getAttribute("sselds");
		SummerManager manager = (SummerManager) getBean("summerManager");
		List selDtimes=new ArrayList();
		Map map;
		for(int i=0; i<dtimes.size(); i++){
			map = (Map)dtimes.get(i);
			if (Toolket.isValueInCookie(map.get("Oid").toString(), oids)) {
				selDtimes.addAll(manager.ezGetList("SELECT Oid, depart_class, cscode, seqno FROM Sdtime WHERE Oid='"+
						map.get("Oid").toString()+"'"));
			}
		}

		return selDtimes;
	}
	
	/**
	 * 取退選checkbox
	 */
	private List getCheckBox4Remove(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "selected");
		List dtimes = (List) session.getAttribute("selected");
		SummerManager manager = (SummerManager) getBean("summerManager");
		List selDtimes=new ArrayList();
		Map map;
		for(int i=0; i<dtimes.size(); i++){
			map = (Map)dtimes.get(i);
			if (Toolket.isValueInCookie(map.get("Oid").toString(), oids)) {
				selDtimes.addAll(manager.ezGetList("SELECT seqno, stdepart_class, student_no, csdepart_class, cscode, " +
						"which, Oid FROM Sseld WHERE Oid='"+map.get("Oid").toString()+"'"));
			}
		}

		return selDtimes;
	}
	
	
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		
		map.put("AddCourse", "addCourse");
		map.put("RemoveCourse", "removeCourse");
		map.put("AddConfirmed", "addConfirmed");
		map.put("RemoveConfirmed", "removeConfirmed");
		map.put("Cancel", "cancel");
		
		return map;
	}

}
