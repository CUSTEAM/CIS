package tw.edu.chit.struts.action.score;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

/**
 * 查核教師未輸入成績
 * @author john
 *
 */
public class ScoreNotUploadAction  extends BaseLookupDispatchAction {
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Query", "query");
		map.put("Save", "save");
		return map;
	}
	
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if(request.getParameter("Oid")!=null){
			CourseManager manager = (CourseManager) getBean("courseManager");
			request.setAttribute("detail", manager.ezGetBy(
			"SELECT s.score, s.score2, s.score3, st.student_name, st.student_no, s.Oid FROM " +
			"Seld s, stmd st WHERE s.student_no=st.student_no AND s.Dtime_oid='"+request.getParameter("Oid")+"' ORDER BY s.student_no"));			
		}
		
		HttpSession session = request.getSession(false);	
		setContentPage(request.getSession(false), "score/ScoreNotUpload.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages messages = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm)form;
		
		String Oid[] = aForm.getStrings("Oid");
		String score2[] = aForm.getStrings("score2");
		String score3[] = aForm.getStrings("score3");
		String score[] = aForm.getStrings("score");
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		String s2=null;
		String s3=null;
		String s=null;
		for(int i=0; i<Oid.length; i++){
			
			try{				
				manager.executeSql("UPDATE Seld SET score2="+score2[i]+" WHERE Oid="+Oid[i]);
			}catch(Exception e){
				manager.executeSql("UPDATE Seld SET score2=null WHERE Oid="+Oid[i]);
			}
			try{
				manager.executeSql("UPDATE Seld SET score3="+score3[i]+" WHERE Oid="+Oid[i]);
			}catch(Exception e){
				manager.executeSql("UPDATE Seld SET score3=null WHERE Oid="+Oid[i]);
			}
			try{
				manager.executeSql("UPDATE Seld SET score="+score[i]+" WHERE Oid="+Oid[i]);
			}catch(Exception e){
				manager.executeSql("UPDATE Seld SET score=null WHERE Oid="+Oid[i]);
			}
			
		}
		
		
		request.setAttribute("detail", manager.ezGetBy(
		"SELECT s.score, s.score2, s.score3, st.student_name, st.student_no, s.Oid, s.Dtime_oid as dtimeOid FROM " +
		"Seld s, stmd st WHERE s.student_no=st.student_no AND s.Dtime_oid='"+manager.ezGetString("SELECT dtime_oid FROM Seld WHERE Oid='"+Oid[0]+"' LIMIT 1")+"'"));
		
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已儲存，可以繼續編輯或&nbsp;<input type='button' class='gGreenSmall' value='返回' onClick='history.back();'>"));
		saveMessages(request, msg);
		return mapping.findForward("Main");

	}
	
	
	

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionMessages messages = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm)form;
		
		String classLess = aForm.getString("classLess");
		String type = aForm.getString("type");
		String scope = aForm.getString("scope");
		String target = aForm.getString("target");
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		
		StringBuilder sb=new StringBuilder("SELECT d.Oid, cl.ClassName, c.chi_name, e.cname, e.cellPhone  FROM " +
		"Class cl, Dtime d LEFT OUTER JOIN empl e ON e.idno=d.techid, Csno c " +
		"WHERE d.Sterm='"+manager.getSchoolTerm()+"' AND c.cscode NOT " +
		"IN('50000', 'T0002', 'T0001') AND cl.ClassNo=d.depart_class AND c.cscode=d.cscode AND " +
		"d.depart_class LIKE'"+classLess+"%'");
		
		if(scope.equals("gra")){
			sb.append("AND SUBSTRING(cl.ClassNo, 5, 1)=SUBSTRING(cl.ClassNo, 3, 1) ");
		}
		if(scope.equals("non")){
			sb.append("AND SUBSTRING(cl.ClassNo, 5, 1)!=SUBSTRING(cl.ClassNo, 3, 1) ");
		}
		sb.append("ORDER BY cl.ClassNo, d.techid");
		
		
		
		List cslist=manager.ezGetBy(sb.toString());		
		List result=new ArrayList();
		int pa=60;
		if(target.equals("non")){			
			//只查未輸
			for(int i=0; i<cslist.size(); i++){			
				if(manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE "+//無成績的人大於0
					type+" IS NULL AND Dtime_oid='"+((Map)cslist.get(i)).get("Oid")+"'")>0){					
					//裝載資訊
					if(((Map)cslist.get(i)).get("ClassName").toString().indexOf("碩")>1){
						pa=70;
					}
					((Map)cslist.get(i)).put("total", //全班人數
							manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE Dtime_oid='"+((Map)cslist.get(i)).get("Oid")+"'"));
					((Map)cslist.get(i)).put("less", //無成績人數
							manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE "+type+" IS NULL AND Dtime_oid='"+((Map)cslist.get(i)).get("Oid")+"'"));
					//result.add(cslist.get(i));
					((Map)cslist.get(i)).put("nopa", //不及格人數
							manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE "+type+"<+"+pa+" AND Dtime_oid='"+((Map)cslist.get(i)).get("Oid")+"'"));
					result.add(cslist.get(i));
				}
			}
		}
		
		
		
		if(target.equals("all")){
			//查全部
			
			for(int i=0; i<cslist.size(); i++){
				if(((Map)cslist.get(i)).get("ClassName").toString().indexOf("碩")>1){
					pa=70;
				}
				((Map)cslist.get(i)).put("total", //全班人數
						manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE Dtime_oid='"+((Map)cslist.get(i)).get("Oid")+"'"));
				((Map)cslist.get(i)).put("less", //無成績人數
						manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE "+type+" IS NULL AND Dtime_oid='"+((Map)cslist.get(i)).get("Oid")+"'"));
				result.add(cslist.get(i));
				((Map)cslist.get(i)).put("nopa", //不及格人數
						manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE "+type+"<+"+pa+" AND Dtime_oid='"+((Map)cslist.get(i)).get("Oid")+"'"));
				result.add(cslist.get(i));
				
			}
		}
		
		
		cslist=null;
		request.setAttribute("cslist", result);
		result=null;		
		return mapping.findForward("Main");
	}

	


}
