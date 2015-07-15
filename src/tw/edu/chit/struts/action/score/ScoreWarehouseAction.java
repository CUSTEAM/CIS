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
 * 成績轉歷年
 * @author john
 */
public class ScoreWarehouseAction  extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		request.setAttribute("camps", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='Campus'"));
		//code5旳SchoolType不可參考..
		request.setAttribute("type", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='SchoolType_Short'"));		
		request.setAttribute("checklist", manager.ezGetBy("SELECT * FROM ScoreHistLog ORDER BY Oid DESC"));		
		
		HttpSession session = request.getSession(false);	
		Member me = getUserCredential(request.getSession(false)).getMember();	
		
		System.out.println(me.getName());
		
		setContentPage(request.getSession(false), "score/ScoreWarehouse.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward ok(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages error = new ActionMessages();	
		DynaActionForm aForm = (DynaActionForm)form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String CampuseNo = aForm.getString("CampuseNo");
		String SchoolType = aForm.getString("SchoolType");		
		String term=manager.getSchoolTerm().toString();
		String year=manager.getSchoolYear().toString();
		
		String score;
		String evgrType;
		
		manager.executeSql(//刪除ScoreHist範圍中所有evgr_type=1的成績, 不包含操行99999
		"DELETE FROM ScoreHist WHERE cscode!='99999' AND school_year='"+year+"' AND " +
		"school_term='"+term+"' AND evgr_type IN('1','7') AND " +
		"student_no IN(SELECT student_no FROM stmd s, Class c WHERE " +
		"s.depart_class=c.ClassNo AND c.CampusNo='"+CampuseNo+"' AND " +
		"c.SchoolType='"+SchoolType+"')");
		
		//尋找範圍中的所有成績
		List<Map>list=manager.ezGetBy("SELECT s.status, s.student_no, d.depart_class, d.cscode, d.opt, d.credit, IF(s.score IS NULL,0, s.score)as score, d.Oid " +
		"FROM Dtime d, Seld s, stmd st, Class c WHERE " +
		"d.Sterm='"+term+"' AND d.Oid=s.Dtime_oid AND st.student_no=s.student_no AND " +
		"st.depart_class=c.ClassNo AND c.CampusNo='"+CampuseNo+"' AND c.SchoolType='"+
		SchoolType+"' AND d.cscode NOT IN('50000', 'T0001')");//班會系時間不處理
		
		//處理學生成績 TODO 效率仍需觀查, 過慢則改用一次性的SQL查詢匯入
		StringBuilder sb=new StringBuilder("INSERT INTO ScoreHist(student_no, school_year, school_" +
		"term, stdepart_class, evgr_type, cscode, opt, credit, score)VALUES");//將insert資料以LOAD的方式匯入ScoreHist
		for(int i=0; i<list.size(); i++){	
			
			if(list.get(i).get("status")==null){				
				score=String.valueOf(list.get(i).get("score"));
				evgrType="1";
			}else{
				score="0";
				evgrType="7";
			}
			
			try{
				sb.append("('"+list.get(i).get("student_no")+"', "+year+", '"+term+
				"',  '"+list.get(i).get("depart_class")+"', '"+evgrType+"',  '"+list.get(i).get("cscode")+"', '"+list.get(i).get("opt")+
				"',  '"+list.get(i).get("credit")+"',  "+score+"),");
			}catch(Exception e){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", ((Map)list.get(i)).get("student_no")+", "));
			}			
		}
		sb.delete(sb.length()-1, sb.length());
		try{
			manager.executeSql(sb.toString());
		}catch(Exception e){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立失敗"));
			saveErrors(request, error);
		}		
		
		if(!error.isEmpty()){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", list.size()+"筆成績全部匯入完成, 但上列同學匯入中出現錯誤"));
			saveErrors(request, error);
		}else{
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",list.size()+"筆成績全部匯入完成"));
			saveMessages(request, msg);
		}
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Member m = getUserCredential(request.getSession(false)).getMember();
		manager.executeSql("INSERT INTO ScoreHistLog(school_year, school_term, CampuseNo, SchoolType, checkDate, cname)VALUES" +
		"('"+year+"', '"+term+"', '"+CampuseNo+"', '"+SchoolType+"', '"+sf.format(new Date())+"', '"+m.getName()+"')");
		return unspecified(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "ok");
		return map;
	}
}
