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
 * 歷年平均
 * @author john
 */
public class ScoreAvgAction  extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		request.setAttribute("camps", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='Campus'"));
		//code5旳SchoolType不可參考..
		request.setAttribute("type", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='SchoolType_Short'"));
		
		
		HttpSession session = request.getSession(false);	
		setContentPage(request.getSession(false), "score/ScoreAvg.jsp");
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
		
		/*
		manager.executeSql("DELETE FROM Stavg WHERE student_no IN" +
		"(SELECT student_no FROM stmd, Class WHERE stmd.depart_class=Class.ClassNo AND " +
		"Class.CampusNo='"+CampuseNo+"' AND Class.SchoolType='"+SchoolType+"') AND " +"school_year='"+year+"' AND school_term='"+term+"'");
		*/
		
		
		//尋找範圍中的所有成績
		List list=manager.ezGetBy("SELECT s.student_no, st.depart_class FROM Dtime d, Seld s, stmd st, Class c WHERE " +
		"d.Sterm='"+term+"' AND d.Oid=s.Dtime_oid AND st.student_no=s.student_no AND " +
		"st.depart_class=c.ClassNo AND c.CampusNo='"+CampuseNo+"' AND c.SchoolType='"+
		SchoolType+"' AND d.cscode NOT IN('50000', 'T0001') GROUP BY s.student_no");//班會系時間不處理
		
		ActionMessages error = new ActionMessages();	
		Map map;
		for(int i=0; i<list.size(); i++){
			try{
				
				String myTest = manager.ezGetString("Select Oid From Stavg " +
						"Where student_no = '"+((Map)list.get(i)).get("student_no")+"' " +
						"  And school_year='"+year+"' And school_term='"+term+"' ");				
				
				if(!myTest.equals("")){   //判斷資料表是否已有資料，如果有就刪除 leo_20120206
					manager.executeSql("Delete From Stavg Where Oid='"+myTest+"' ");					
				}					
				
				//map=manager.ezGetMap("SELECT AVG(s.score) as score, SUM(s.credit)as credit FROM ScoreHist s WHERE " +
				//總平均計算方式 = SUM(科目得分*科目學分) / 總學分
				//抵免科目不算入平均  evgr_type<>'6'  Leo_20120206
				
				
				map=manager.ezGetMap("SELECT SUM(s.score*s.credit)/SUM(s.credit) as score, SUM(s.credit) as credit FROM ScoreHist s WHERE " +
				"s.student_no='"+((Map)list.get(i)).get("student_no")+"' And s.evgr_type<>'6' AND s.school_year='"+year+"' AND s.school_term='"+term+"'");
				manager.executeSql("INSERT INTO Stavg(depart_class, student_no, school_year, school_term, score, total_credit)VALUES" +
						"('"+((Map)list.get(i)).get("depart_class")+"', '"+((Map)list.get(i)).get("student_no")+"', '"+year+"', '"+term+"', '"+map.get("score")+"', '"+map.get("credit")+"')");
			}catch(Exception e){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", ((Map)list.get(i)).get("student_no")+"建立失敗<br>"));
			}			
		}
		if(!error.isEmpty()){
			saveErrors(request, error);
		}else{
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",list.size()+"筆成績全部匯入完成"));
			saveMessages(request, msg);
		}
		return unspecified(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "ok");
		return map;
	}
}
