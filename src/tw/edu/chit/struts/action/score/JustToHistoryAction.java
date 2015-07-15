package tw.edu.chit.struts.action.score;

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

import tw.edu.chit.model.Member;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 歷年操行
 * @author LeoHuang
 */
public class JustToHistoryAction  extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		request.setAttribute("camps", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='Campus'"));
		//code5旳SchoolType不可參考..
		request.setAttribute("type", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='SchoolType_Short'"));
		
		
		HttpSession session = request.getSession(false);	
		setContentPage(request.getSession(false), "score/JustToHistory.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward ok(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		DynaActionForm aForm = (DynaActionForm)form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String CampuseNo = aForm.getString("CampuseNo");
		String SchoolType = aForm.getString("SchoolType");		
		String term=manager.getSchoolTerm().toString();
		String year=manager.getSchoolYear().toString();		
		
		
		ActionMessages error = new ActionMessages();	
		//先紀錄要處理的筆數
		
		
		
		Integer CountNum = manager.ezGetInt("Select count(DISTINCT j.student_no) From Just j,Class c, stmd s Where " +
		"s.student_no=j.student_no AND s.depart_class=c.ClassNo And c.SchoolType = '"+SchoolType+"'");
		
		try{						
			//刪除該部制班級之歷史操行成績資料	
			manager.executeSql(  
				" DELETE FROM ScoreHist " +
				" WHERE student_no IN" +
				"     (SELECT DISTINCT j.student_no FROM Just j, Class c, stmd s WHERE s.student_no=j.student_no AND s.depart_class=c.ClassNo " +
				"         AND c.CampusNo='"+CampuseNo+"' AND c.SchoolType='"+SchoolType+"' ) " +
				"   AND cscode='99999'" +  // 歷史成績表   99999 >> 操行
				"   AND school_year='"+year+"'" +
				"   AND school_term='"+term+"'");			
			manager.executeSql(  //cond:操行及全勤
				" DELETE FROM cond " +
				" WHERE student_no IN" +
				"     (SELECT DISTINCT j.student_no FROM Just as j, Class as c, stmd s WHERE s.student_no=j.student_no AND s.depart_class=c.ClassNo " +
				"         AND c.CampusNo='"+CampuseNo+"' AND c.SchoolType='"+SchoolType+"' ) " +
				"   AND school_year='"+year+"'" +
				"   AND school_term='"+term+"'"); 
		    //新增新學年該部制班級之歷史操行成績資料
			
			
			
			
			manager.executeSql(
		        " INSERT INTO ScoreHist(student_no,school_year,school_term,evgr_type,cscode,opt,credit,score) " +
				" Select j.student_no as student_no,'"+year+"' as school_year, " +
				"		'"+term+"' as school_term,'1' as evgr_type,'99999' as cscode, " +
				"   	'1' as opt,'0' as credit,j.total_score as score " +
				" 	From Just j,Class c, stmd s WHERE s.student_no=j.student_no AND s.depart_class=c.ClassNo " +
				"  		And c.CampusNo = '"+CampuseNo+"' " +
				"  		And c.SchoolType = '"+SchoolType+"' " +
				" 	GROUP BY j.student_no ");
			manager.executeSql(
			    " INSERT INTO cond(student_no, school_year, school_term, depart_class, score, noabsent) " +
				" Select j.student_no as student_no,'"+year+"' as school_year, " +
				"		 '"+term+"' as school_term,j.depart_class as depart_class , " +
				"        j.total_score as score,(CASE WHEN j.dilg_score=3.00 THEN 'Y' ELSE 'N' END) as noabsent" +
				" 	From Just as j,Class as c, stmd s WHERE s.student_no=j.student_no AND s.depart_class=c.ClassNo " +
				"  		And c.CampusNo = '"+CampuseNo+"' " +
				"  		And c.SchoolType = '"+SchoolType+"' " +
				" 	GROUP BY j.student_no ");			
		}catch(Exception e){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "ScoreHist And cond Table Data 建立失敗<br>"));			
		}	
		
		if(!error.isEmpty()){
			saveErrors(request, error);
		}else{
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",CountNum.toString()+"筆成績全部匯入完成"));
			saveMessages(request, msg);
		}
		/* 20120607 Modi BY yichen  ============end=========================*/
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward myComb1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		DynaActionForm aForm = (DynaActionForm)form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String CampuseNo = aForm.getString("CampuseNo");
		String SchoolType = aForm.getString("SchoolType");		
		String term=manager.getSchoolTerm().toString();
		String year=manager.getSchoolYear().toString();
		
		ActionMessages error = new ActionMessages();
		//先紀錄要處理的筆數
		Integer CountNum = manager.ezGetInt(
				"Select count(DISTINCT j.student_no) From Just as j,Class as c, stmd s Where s.student_no=j.student_no AND s.depart_class=c.ClassNo " +
				"And c.CampusNo = '"+CampuseNo+"' And c.SchoolType = '"+SchoolType+"' ");
		try{
			//comb1:評語及體育軍訓成績
			//Leo測試的時候將comb1換成comb1_Leo20120203這個table但不知道其他相關程式(ex:報表)抓取的table是否有一起置換，所以只好先兩個都處理，待確定後可刪掉一個，以節省程式作業時間。
			//刪除該部制班級之評語及體育軍訓成績		
			
			manager.executeSql(  
				" DELETE FROM comb1 " +
				" WHERE student_no IN" +
				"     (SELECT DISTINCT j.student_no FROM Just j, Class c, stmd s Where s.student_no=j.student_no AND s.depart_class=c.ClassNo" +
				"         AND c.CampusNo='"+CampuseNo+"' AND c.SchoolType='"+SchoolType+"' ) " +
				"   AND school_year='"+year+"'" +
				"   AND school_term='"+term+"'"); 
			//新增新學年該部制班級之評語及體育軍訓成績
			manager.executeSql(
				    " INSERT INTO comb1(school_year, school_term, depart_class, student_no, com_name, physical_score,military_score)" +
				    "Select '101' as school_year, '2' as school_term, c.ClassNo as depart_class, j.student_no,concat((SELECT name FROM code1 WHERE no=j.com_code1)," +
				    "(SELECT name FROM code1 WHERE no=j.com_code2),(SELECT name FROM code1 WHERE no=j.com_code3))as com_name," +
				    "(SELECT se.score FROM Seld se, Dtime d, Csno cs WHERE cs.cscode=d.cscode AND cs.chi_name LIKE'體育%' AND " +
				    "d.Oid=se.Dtime_oid AND d.Sterm='2'AND se.student_no=s.student_no GROUP By se.student_no)as physical_score," +
				    "(SELECT se.score FROM Seld se, Dtime d, Csno cs WHERE cs.cscode=d.cscode AND cs.chi_name LIKE'軍訓%' AND " +
				    "d.Oid=se.Dtime_oid AND d.Sterm='2' AND se.student_no=s.student_no GROUP By se.student_no)as military_score FROM " +
				    "Just j,Class c, stmd s WHERE s.student_no=j.student_no AND s.depart_class=c.ClassNo And c.CampusNo='"+CampuseNo+"' And c.SchoolType='"+SchoolType+"'");	
		}catch(Exception e){
			e.printStackTrace();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "comb1 Table Data 建立失敗<br>"));			
		}

		if(!error.isEmpty()){
			saveErrors(request, error);
		}else{
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",CountNum.toString()+"筆成績全部匯入comb1完成"));
			saveMessages(request, msg);
		}
		/* 20120607 Modi BY yichen  ============end=========================*/		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward myComb2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		DynaActionForm aForm = (DynaActionForm)form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String CampuseNo = aForm.getString("CampuseNo");
		String SchoolType = aForm.getString("SchoolType");		
		String term=manager.getSchoolTerm().toString();
		String year=manager.getSchoolYear().toString();				
		ActionMessages error = new ActionMessages();
		//先紀錄要處理的筆數
		Integer CountNum = manager.ezGetInt(
				"Select count(DISTINCT j.student_no) From desd as j,Class as c Where j.depart_class = c.ClassNo " +
				"  And c.CampusNo = '"+CampuseNo+"' And c.SchoolType = '"+SchoolType+"' ");
		try{
			//comb2:獎懲資料
			//Leo測試的時候將comb2換成comb2_Leo20120203這個table但不知道其他相關程式(ex:報表)抓取的table是否有一起置換，所以只好先兩個都處理，待確定後可刪掉一個，以節省程式作業時間。
			//刪除該部制班級之評語及體育軍訓成績	
			
			manager.executeSql(  
				" DELETE FROM comb2 " +
				" WHERE student_no IN" +
				"     (SELECT DISTINCT j.student_no FROM Just as j, Class as c, stmd s Where s.student_no=j.student_no AND s.depart_class=c.ClassNo " +
				"         AND c.CampusNo='"+CampuseNo+"' AND c.SchoolType='"+SchoolType+"' ) " +
				"   AND school_year='"+year+"'" +
				"   AND school_term='"+term+"'"); 
			//新增新學年該部制班級之評語及體育軍訓成績
			
			manager.executeSql(
				    " INSERT INTO comb2(school_year, school_term, depart_class, student_no, ddate, no, reason, kind1, cnt1, kind2, cnt2) " +
					" Select '"+year+"' as school_year,'"+term+"' as school_term,j.depart_class as depart_class, " +
				    " 			 j.student_no as student_no,j.ddate as ddate,j.no as no,j.reason as reason, "+						
				    " 			 j.kind1 as kind1,j.cnt1 as cnt1,j.kind2 as kind2,j.cnt2 as cnt2 "+						
					" 	From desd as j,Class as c " +
					" 	Where j.depart_class = c.ClassNo " +
					"  		And c.CampusNo = '"+CampuseNo+"' " +
					"  		And c.SchoolType = '"+SchoolType+"' " +
					" 	GROUP BY j.student_no ");	
		}catch(Exception e){			
			e.printStackTrace();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "comb2 Table Data 建立失敗<br>"));			
		}
		if(!error.isEmpty()){
			saveErrors(request, error);
		}else{
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",CountNum.toString()+"筆成績全部匯入comb2完成"));
			saveMessages(request, msg);
		}
		/* 20120607 Modi BY yichen  ============end=========================*/
		return unspecified(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "ok");
		map.put("ToComb1", "myComb1");
		map.put("ToComb2", "myComb2");
		return map;
	}
}
