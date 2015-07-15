package tw.edu.chit.struts.action.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ScoreManagerAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("allDept", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='Dept' AND idno <>'0' ORDER BY sequence"));
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","..."));
		//saveMessages(request, msg);
		setContentPage(request.getSession(false), "registration/ScoreManager.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 查詢
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
		HttpServletResponse response)throws Exception {
		DynaActionForm sForm = (DynaActionForm) form;
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String student_no=sForm.getString("student_no");
		
		//現在學期
		List list=manager.ezGetBy("SELECT s.Oid, s.score, s.score1, s.score2, s.score3, st.student_no, st.student_name, " +
		"d.opt, d.credit, d.thour, cs.cscode, cs.chi_name FROM Seld s, Dtime d, stmd st, Csno cs WHERE " +
		"cs.cscode=d.cscode AND s.student_no=st.student_no AND s.Dtime_oid=d.Oid AND " +
		"st.student_no = '"+student_no+"' AND d.Sterm='"+manager.getSchoolTerm()+"'");
		list.addAll(manager.ezGetBy("SELECT s.Oid, s.score, s.score1, s.score2, s.score3, st.student_no, st.student_name, " +
		"d.opt, d.credit, d.thour, cs.cscode, cs.chi_name FROM Seld s, Dtime d, Gstmd st, Csno cs WHERE " +
		"cs.cscode=d.cscode AND s.student_no=st.student_no AND s.Dtime_oid=d.Oid AND " +
		"st.student_no = '"+student_no+"' AND d.Sterm='"+manager.getSchoolTerm()+"'"));
				
		request.setAttribute("selds", list);
		
		//過去學期
		list=manager.ezGetBy("SELECT s.Oid, s.score, st.student_no, st.student_name, s.school_year, s.school_term, s.evgr_type, " +
		"s.opt, s.credit, cs.cscode, cs.chi_name FROM ScoreHist s, stmd st, Csno cs WHERE s.student_no=st.student_no AND " +
		"s.cscode=cs.cscode AND st.student_no = '"+student_no+"' ORDER BY s.Oid DESC");
		list.addAll(manager.ezGetBy("SELECT s.Oid, s.score, st.student_no, st.student_name, s.school_year, s.school_term, s.evgr_type, " +
				"s.opt, s.credit, cs.cscode, cs.chi_name FROM ScoreHist s, Gstmd st, Csno cs WHERE s.student_no=st.student_no AND " +
				"s.cscode=cs.cscode AND st.student_no = '"+student_no+"' ORDER BY s.Oid DESC"));
				
		request.setAttribute("scorehist", list);
		request.setAttribute("CourseType", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='CourseType'"));
		
		
		return mapping.findForward("Main");

	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		
		DynaActionForm sForm = (DynaActionForm) form;
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String student_no=sForm.getString("student_no");
		
		String school_year[]=sForm.getStrings("school_year");
		String school_term[]=sForm.getStrings("school_term");
		String cscode[]=sForm.getStrings("cscode");
		String opt[]=sForm.getStrings("opt");
		String credit[]=sForm.getStrings("credit");
		String evgr_type[]=sForm.getStrings("evgr_type");
		String score[]=sForm.getStrings("score");
		String stdepart_class[]=sForm.getStrings("stdepart_class");
		
		
		try{
			ScoreHist s=new ScoreHist();
			s.setStudentNo(student_no);
			s.setCredit(Float.parseFloat(credit[0]));
			s.setCscode(cscode[0]);
			s.setEvgrType(evgr_type[0]);
			s.setOpt(opt[0]);
			s.setSchoolTerm(school_term[0]);
			s.setSchoolYear(Short.valueOf(school_year[0]));
			s.setStdepartClass(stdepart_class[0]);
			if(!score[0].equals("")){
				s.setScore(Float.parseFloat(score[0]));
			}
			manager.updateObject(s);
			
		}catch(Exception e){
			e.printStackTrace();
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "請檢查欄位"));
			saveErrors(request, error);
			return query(mapping, form, request, response);
		}
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","新增完成!"));
		saveMessages(request, msg);	
		
		//更新歷年成績
		List list=manager.ezGetBy("SELECT student_no,school_year,school_term,ROUND(SUM(score*credit)/SUM(credit),4)as score,SUM(credit)as credit "+
				"FROM ScoreHist  WHERE student_no='"+student_no+"' AND cscode!='99999' AND score!='' GROUP BY student_no,school_year,school_term");	
		float scoresum=0;
		int k=0;
		for(int i=0; i<list.size(); i++){
			if(((Map)list.get(i)).get("score")!=null){
				manager.executeSql("UPDATE Stavg SET score='"+((Map)list.get(i)).get("score")+"',total_credit='"+((Map)list.get(i)).get("credit")+"' where student_no='"+((Map)list.get(i)).get("student_no")+"' and school_year='"+((Map)list.get(i)).get("school_year")+"' and school_term='"+((Map)list.get(i)).get("school_term")+"'");
				//System.out.println("UPDATE Stavg SET score='"+((Map)list.get(i)).get("score")+"',total_credit='"+((Map)list.get(i)).get("credit")+"' where student_no='"+((Map)list.get(i)).get("student_no")+"' and school_year='"+((Map)list.get(i)).get("school_year")+"' and school_term='"+((Map)list.get(i)).get("school_term")+"'");
				String scoreOid = manager.ezGetString("SELECT Oid FROM MasterData WHERE student_no='"+student_no+"'");
				if (!scoreOid.equals("")) {
					scoresum=scoresum+Float.parseFloat(((Map)list.get(i)).get("score").toString());
					k++;
				}
			}
		}
		String theses = manager.ezGetString("SELECT theses_score FROM MasterData WHERE student_no='"+student_no+"'");
		if (!theses.equals("")) {
			float theses_score=Float.parseFloat(theses); //碩士論文
			float scoreavg=(float)(Math.round(scoresum/(k)*10))/10; //全部學期平均成績
			float sum=(theses_score+scoreavg)/2; //畢業成績
			manager.executeSql("UPDATE MasterData SET evgr1_score='"+scoreavg+"', graduate_score='"+sum+"' WHERE student_no='"+student_no+"' AND theses_score='"+theses_score+"'");
			//System.out.println("UPDATE MasterData SET evgr1_score='"+scoreavg+"', graduate_score='"+sum+"' WHERE student_no='"+student_no+"' AND theses_score='"+theses_score+"'");
		}
		
		return query(mapping, form, request, response);
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm sForm = (DynaActionForm) form;
		String student_no=sForm.getString("student_no");
		
		String Oid[]=sForm.getStrings("Oid");
		String school_year[]=sForm.getStrings("school_year");
		String school_term[]=sForm.getStrings("school_term");
		String cscode[]=sForm.getStrings("cscode");
		String opt[]=sForm.getStrings("opt");
		String credit[]=sForm.getStrings("credit");
		String evgr_type[]=sForm.getStrings("evgr_type");
		String score[]=sForm.getStrings("score");
		
		for(int i=1; i<Oid.length; i++){
			
			if(!Oid[i].equals("")){				
				try{
					ScoreHist s=(ScoreHist)manager.hqlGetBy("FROM ScoreHist WHERE Oid="+Oid[i]).get(0);
					s.setStudentNo(student_no);
					s.setCredit(Float.parseFloat(credit[i]));
					s.setCscode(cscode[i]);
					s.setEvgrType(evgr_type[i]);
					s.setOpt(opt[i]);
					s.setSchoolTerm(school_term[i]);
					s.setSchoolYear(Short.valueOf(school_year[i]));
					if(!score[i].equals("")){
						s.setScore(Float.parseFloat(score[i]));
					}
					manager.updateObject(s);
					
				}catch(Exception e){
					e.printStackTrace();
					ActionMessages error = new ActionMessages();
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "請檢查欄位"));
					saveErrors(request, error);
					return query(mapping, form, request, response);
				}
				
			}
			
		}		

		//更新歷年成績
		List list=manager.ezGetBy("SELECT student_no,school_year,school_term,ROUND(SUM(score*credit)/SUM(credit),4)as score,SUM(credit)as credit "+
				"FROM ScoreHist  WHERE student_no='"+student_no+"' AND cscode!='99999' AND score!='' GROUP BY student_no,school_year,school_term");	
		float scoresum=0;
		int k=0;
		for(int i=0; i<list.size(); i++){
			if(((Map)list.get(i)).get("score")!=null){
				manager.executeSql("UPDATE Stavg SET score='"+((Map)list.get(i)).get("score")+"',total_credit='"+((Map)list.get(i)).get("credit")+"' where student_no='"+((Map)list.get(i)).get("student_no")+"' and school_year='"+((Map)list.get(i)).get("school_year")+"' and school_term='"+((Map)list.get(i)).get("school_term")+"'");
				//System.out.println("UPDATE Stavg SET score='"+((Map)list.get(i)).get("score")+"',total_credit='"+((Map)list.get(i)).get("credit")+"' where student_no='"+((Map)list.get(i)).get("student_no")+"' and school_year='"+((Map)list.get(i)).get("school_year")+"' and school_term='"+((Map)list.get(i)).get("school_term")+"'");
				String scoreOid = manager.ezGetString("SELECT Oid FROM MasterData WHERE student_no='"+student_no+"'");
				if (!scoreOid.equals("")) {
					scoresum=scoresum+Float.parseFloat(((Map)list.get(i)).get("score").toString());
					k++;
				}
			}
		}
		String theses = manager.ezGetString("SELECT theses_score FROM MasterData WHERE student_no='"+student_no+"'");
		if (!theses.equals("")) {
			float theses_score=Float.parseFloat(theses); //碩士論文
			float scoreavg=(float)(Math.round(scoresum/(k)*10))/10; //全部學期平均成績
			float sum=(theses_score+scoreavg)/2; //畢業成績
			manager.executeSql("UPDATE MasterData SET evgr1_score='"+scoreavg+"', graduate_score='"+sum+"' WHERE student_no='"+student_no+"' AND theses_score='"+theses_score+"'");
			//System.out.println("UPDATE MasterData SET evgr1_score='"+scoreavg+"', graduate_score='"+sum+"' WHERE student_no='"+student_no+"' AND theses_score='"+theses_score+"'");
		}
		
		return query(mapping, form, request, response);
	}
	
	public ActionForward del(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		//session.setAttribute("allDept", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='Dept' AND idno <>'0' ORDER BY sequence"));
		DynaActionForm sForm = (DynaActionForm) form;
		String student_no=sForm.getString("student_no");		
		String Oid[]=sForm.getStrings("Oid");
		
		for(int i=1; i<Oid.length; i++){			
			if(!Oid[i].equals("")){								
				manager.executeSql("DELETE FROM ScoreHist WHERE Oid="+Oid[i]);
			}
		}
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","已刪除"));
		saveMessages(request, msg);
		
		//更新歷年成績
		List list=manager.ezGetBy("SELECT student_no,school_year,school_term,ROUND(SUM(score*credit)/SUM(credit),4)as score,SUM(credit)as credit "+
				"FROM ScoreHist  WHERE student_no='"+student_no+"' AND cscode!='99999' AND score!='' GROUP BY student_no,school_year,school_term");	
		float scoresum=0;
		int k=0;
		for(int i=0; i<list.size(); i++){
			if(((Map)list.get(i)).get("score")!=null){
				manager.executeSql("UPDATE Stavg SET score='"+((Map)list.get(i)).get("score")+"',total_credit='"+((Map)list.get(i)).get("credit")+"' where student_no='"+((Map)list.get(i)).get("student_no")+"' and school_year='"+((Map)list.get(i)).get("school_year")+"' and school_term='"+((Map)list.get(i)).get("school_term")+"'");
				//System.out.println("UPDATE Stavg SET score='"+((Map)list.get(i)).get("score")+"',total_credit='"+((Map)list.get(i)).get("credit")+"' where student_no='"+((Map)list.get(i)).get("student_no")+"' and school_year='"+((Map)list.get(i)).get("school_year")+"' and school_term='"+((Map)list.get(i)).get("school_term")+"'");
				String scoreOid = manager.ezGetString("SELECT Oid FROM MasterData WHERE student_no='"+student_no+"'");
				if (!scoreOid.equals("")) {
					scoresum=scoresum+Float.parseFloat(((Map)list.get(i)).get("score").toString());
					k++;
				}
			}
		}
		String theses = manager.ezGetString("SELECT theses_score FROM MasterData WHERE student_no='"+student_no+"'");
		if (!theses.equals("")) {
			float theses_score=Float.parseFloat(theses); //碩士論文
			float scoreavg=(float)(Math.round(scoresum/(k)*10))/10; //全部學期平均成績
			float sum=(theses_score+scoreavg)/2; //畢業成績
			manager.executeSql("UPDATE MasterData SET evgr1_score='"+scoreavg+"', graduate_score='"+sum+"' WHERE student_no='"+student_no+"' AND theses_score='"+theses_score+"'");
			//System.out.println("UPDATE MasterData SET evgr1_score='"+scoreavg+"', graduate_score='"+sum+"' WHERE student_no='"+student_no+"' AND theses_score='"+theses_score+"'");
		}
		
		return query(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Add", "add");
		
		map.put("Delete", "del");
		map.put("Modify", "edit");
		return map;
	}
}