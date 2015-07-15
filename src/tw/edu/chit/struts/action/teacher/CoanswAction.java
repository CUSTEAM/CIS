package tw.edu.chit.struts.action.teacher;

import java.math.BigDecimal;
import java.text.NumberFormat;
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

import tw.edu.chit.model.CoanswReview;
import tw.edu.chit.model.Member;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class CoanswAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {


		HttpSession session = request.getSession(false);
		//DynaActionForm coForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		Member me = getUserCredential(request.getSession(false)).getMember();
		
		String sterm=manager.getSchoolTerm().toString();
		// 原計數不考慮偵錯題
		//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "本學期共有 "+manager.ezGetString("SELECT COUNT(*) FROM " +
		//		"Dtime d, Coansw c WHERE d.Oid=c.Dtime_oid AND d.techid='"+me.getIdno()+"'")+"位學生填寫問卷"));
		
		// 新計數考慮偵錯題,不計入有效樣本 -- 溫瑞烘修改
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "本學期共有 "+
		  manager.ezGetString("SELECT COUNT(*) FROM " +
				"Dtime d, Coansw c WHERE SUBSTR(c.answer,1,1)<>SUBSTR(c.answer,15,1) AND "+
				"d.Oid=c.Dtime_oid AND d.techid='"+me.getIdno()+"'")+"位學生填寫問卷"));
				
		saveMessages(request, msg);
		
		session.setAttribute("techid", me.getIdno());
		session.setAttribute("sterm", manager.getNowBy("School_term"));				
		
		session.setAttribute("countMyM", manager.ezGetInt("SELECT COUNT(*)FROM Dtime WHERE (elearning='0'||elearning='3') AND techid='"+me.getIdno()+"'"));
		
		
		//得文字資訊
		session.setAttribute("myDtimeM", setMyDtime(me.getIdno(), "0"));//找正常課程	
		session.setAttribute("myDtimeE", setMyDtime(me.getIdno(), "1"));//找遠距課程
		
		//System.out.println("SELECT COUNT(*)FROM Dtime WHERE (elearning='1'||elearning='2') AND techid='"+me.getIdno()+"' AND Sterm='"+sterm+"'");
		session.setAttribute("countMyE", manager.ezGetInt("SELECT COUNT(*)FROM Dtime WHERE (elearning='1'||elearning='2') AND techid='"+me.getIdno()+"' AND Sterm='"+sterm+"'"));		
		
		List years=manager.ezGetBy("SELECT d.school_year FROM Savedtime d WHERE " +
				"d.techid='"+me.getIdno()+"' GROUP BY d.school_year ORDER BY d.school_year DESC");
		
		List coansw;
		float yearAvg;
		int samples;
		for(int i=0; i<years.size(); i++){
			yearAvg=0;
			samples=0;
			coansw=manager.ezGetBy(
					"SELECT d.school_term, c.ClassName, cs.chi_name, d.samples, d.avg " +
					"FROM Savedtime d, Class c, Csno cs WHERE d.depart_class=c.ClassNo AND " +
					"d.cscode=cs.cscode AND d.techid='"+me.getIdno()+"' AND d.avg IS NOT NULL AND " +
					"d.school_year='"+((Map)years.get(i)).get("school_year")+"' ORDER BY d.school_term");
			((Map)years.get(i)).put("coansw", coansw);
			for(int j=0; j<coansw.size(); j++){
				yearAvg=yearAvg+Float.parseFloat(((Map)coansw.get(j)).get("avg").toString());
				samples=samples+Integer.parseInt(((Map)coansw.get(j)).get("samples").toString());
			}
			if(coansw.size()>0){
				((Map)years.get(i)).put("yearAvg", manager.roundOff(((float)(yearAvg/coansw.size())), 2));
				((Map)years.get(i)).put("samples", samples);
			}
		}
		
		request.setAttribute("myConaswHist", years);		
		setContentPage(request.getSession(false), "teacher/Coansw.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 送交回應
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm reForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "dtimes");
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		ActionMessages msg = new ActionMessages();
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		
		String dtimeOid=reForm.get("dtimeOid").toString();
		String cscode=reForm.get("cscode").toString();
		String depart_class=(String)reForm.get("depart_class");
		String techid=(String)reForm.get("techid");
		String score=(String)reForm.get("score");
		String content_per=(String)reForm.get("content_per");
		
		if(content_per.trim().length()<5){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.errorN1", "沒有輸入內容"));
			saveErrors(request, error);
		}else{
			CoanswReview review=new CoanswReview();
			review.setSchoolYear(Short.parseShort(manager.getSchoolYear().toString()));
			review.setSchoolTerm(manager.getSchoolTerm().toString());
			review.setContentPer(content_per);
			review.setDepartClass(depart_class);
			review.setCscode(cscode);
			review.setScore(Float.parseFloat(score));
			review.setTechid(techid);
			manager.updateObject(review);
		}
		
		
		
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 塞東西
	 * @param myDtime
	 * @param idno
	 * @param type
	 * @return
	 */
	private List setMyDtime(String idno, String type){
		CourseManager manager = (CourseManager) getBean("courseManager");
		String sql;
		if(type.equals("0")){//表示為一般課程
			sql="SELECT cs.chi_name, d.Oid, d.cscode, d.techid, d.depart_class, c.ClassName FROM Dtime d, Class c, Csno cs " +
			"WHERE d.techid='"+idno+"' AND (d.elearning='0'||d.elearning='3') AND d.depart_class=c.ClassNo AND cs.cscode=d.cscode AND " +
			 "Sterm="+manager.getSchoolTerm();
		}else{
			sql="SELECT cs.chi_name, d.Oid, d.cscode, d.techid, d.depart_class, c.ClassName FROM Dtime d, Class c, Csno cs " +
			"WHERE d.techid='"+idno+"' AND (d.elearning='1'||d.elearning='2'||d.elearning='3') AND d.depart_class=c.ClassNo AND cs.cscode=d.cscode AND " +
			 "Sterm="+manager.getSchoolTerm();
		}
		
		return manager.getCoans(manager.ezGetBy(sql), type);
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Send", "send");
		return map;
	}
}