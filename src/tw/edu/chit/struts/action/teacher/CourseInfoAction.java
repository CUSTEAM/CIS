package tw.edu.chit.struts.action.teacher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 大綱簡介管理
 * @author JOHN
 *
 */
public class CourseInfoAction extends BaseLookupDispatchAction{
	
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		UserCredential c = (UserCredential) session.getAttribute("Credential");		
		
		//外部進入
		if(request.getParameter("iOid")==null && request.getParameter("sOid")==null){
			
			
			if(session.getAttribute("cshist")==null){
				
				session.setAttribute("cshist", manager.ezGetBy("SELECT d.school_year, d.school_term, c.cscode, c.chi_name, " +
				"d.Oid FROM Savedtime d, Csno c WHERE d.school_year>=97 AND " +
				"d.cscode=c.cscode AND d.techid='"+c.getMember().getIdno()+"' ORDER BY d.school_year, d.school_term DESC"));
			}
			
			request.setAttribute("dtime1", manager.getDtime(c.getMember().getIdno(), "1"));
			request.setAttribute("dtime2", manager.getDtime(c.getMember().getIdno(), "2"));		
			request.setAttribute("now", manager.getSchoolTerm());
			setContentPage(request.getSession(false), "teacher/CourseInfo.jsp");
			return mapping.findForward("Main");
		}		
		
		////歷年
		request.setAttribute("old", manager.ezGetBy("SELECT d.school_year, d.school_term, d.Oid, cs.chi_name, c.ClassName FROM Savedtime d, Csno cs, Class c WHERE " +
				"c.ClassNo=d.depart_class AND cs.cscode=d.cscode AND d.techid='"+c.getMember().getIdno()+"' ORDER BY d.school_year, d.school_term, d.depart_class DESC"));
		
		//簡介進入
		if(request.getParameter("iOid")!=null){			
			Map map=manager.ezGetMap("SELECT d.Oid, d.Introduction, cs.chi_name, c.ClassName FROM Dtime d, Csno cs, Class c WHERE " +
			"c.ClassNo=d.depart_class AND cs.cscode=d.cscode AND d.Oid="+request.getParameter("iOid"));			
			map.put("Introduction", manager.parseIntr((String)map.get("Introduction")));
			//s== Leo20120330 新增科目英文名稱欄位 =========================================== >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			request.setAttribute("CsEnglishName", manager.ezGetString(
					"Select eng_name From Csno c, Dtime d Where c.cscode=d.cscode And d.Oid='"+request.getParameter("iOid")+"'"));
			//e========================================================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			request.setAttribute("aDtime", map);
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請點選空白處開始編輯<br>或選擇歷年資料範本套用<br>完成後請點選儲存"));			
			saveMessages(request, msg);
		}
		
		//大綱進入
		if(request.getParameter("sOid")!=null){
			Map map=manager.ezGetMap("SELECT d.Oid, d.Syllabi, d.Syllabi_sub, cs.chi_name, c.ClassName FROM Dtime d, Csno cs, Class c WHERE " +
			"c.ClassNo=d.depart_class AND cs.cscode=d.cscode AND d.Oid="+request.getParameter("sOid"));			
			
			map.put("Syllabi", manager.parseSyl((String)map.get("Syllabi")));
			map.put("Syllabi_sub", manager.parseSyls((String)map.get("Syllabi_sub")));			
			request.setAttribute("bDtime", map);
			
			
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請點選空白處開始編輯<br>或選擇歷年資料範本套用<br>完成後請點選儲存"));			
			saveMessages(request, msg);
		}
		
		return mapping.findForward("Main");
	}
	
	public ActionForward saveInt(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		
		DynaActionForm f = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		
		String dtimeoid=f.getString("dtimeoid");
		String chi=f.getString("chi");
		String eng=f.getString("eng");
		String book=f.getString("book");
		
		//s== Leo20120330 新增科目英文名稱欄位 =========================================== >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		String eng_name=f.getString("CsEnglishName");
		String CsnoOid=manager.ezGetString("Select c.Oid From Csno c, Dtime d Where c.cscode=d.cscode And d.Oid='"+dtimeoid+"'");
		//e========================================================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		Dtime d=(Dtime) manager.hqlGetBy("FROM Dtime WHERE Oid="+dtimeoid).get(0);
		d.setIntroduction(chi+"#ln;"+eng+"#ln;"+book);
		manager.updateObject(d);
		
		//s========================================================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		manager.executeSql("Update Csno Set eng_name='"+eng_name+"' Where Oid='"+CsnoOid+"'");
		//e========================================================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		Map map=manager.ezGetMap("SELECT d.Oid, d.Introduction, cs.chi_name, c.ClassName FROM Dtime d, Csno cs, Class c WHERE " +
		"c.ClassNo=d.depart_class AND cs.cscode=d.cscode AND d.Oid="+dtimeoid);	
		
		map.put("Introduction", manager.parseIntr((String)map.get("Introduction")));
		request.setAttribute("aDtime", map);
				
		request.setAttribute("old", manager.ezGetBy("SELECT d.school_year, d.school_term, d.Oid, cs.chi_name, c.ClassName FROM Savedtime d, Csno cs, Class c WHERE " +
		"c.ClassNo=d.depart_class AND cs.cscode=d.cscode AND d.techid='"+c.getMember().getIdno()+"' ORDER BY d.school_year, d.school_term, d.depart_class DESC"));
		
		//s========================================================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		request.setAttribute("CsEnglishName", manager.ezGetString(
				"Select eng_name From Csno c, Dtime d Where c.cscode=d.cscode And d.Oid='"+dtimeoid+"'"));
		//e========================================================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		ActionMessages msg = new ActionMessages();		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存完成，若不再修改請點選下方返回列表"));			
		saveMessages(request, msg);	
		return mapping.findForward("Main");
	}
	
	public ActionForward saveSyl(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {	
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm f = (DynaActionForm) form;
		
		String dtimeoid=f.getString("dtimeoid");		
		String objectives=f.getString("objectives");
		String syllabus=f.getString("syllabus");
		String prerequisites=f.getString("prerequisites");
		
		String week[]=f.getStrings("week");
		String topic[]=f.getStrings("topic");
		String content[]=f.getStrings("content");
		String hours[]=f.getStrings("hours");
		
		Dtime d=(Dtime) manager.hqlGetBy("FROM Dtime WHERE Oid="+dtimeoid).get(0);
		d.setSyllabi(objectives+"#ln;"+prerequisites+"#ln;"+syllabus);
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<week.length; i++){			
			if(!week[i].equals("")||!topic[i].equals("")||!content[i].equals("")||!hours[i].equals("")){
				sb.append(week[i]+"#ln;");
				sb.append(topic[i]+"#ln;");
				sb.append(content[i]+"#ln;");
				sb.append(hours[i]+"#ln;");
			}
		}
		d.setSyllabiSub(sb.toString());
		manager.updateObject(d);
		
		Map map=manager.ezGetMap("SELECT d.Oid, d.Syllabi, d.Syllabi_sub, cs.chi_name, c.ClassName FROM Dtime d, Csno cs, Class c WHERE " +
		"c.ClassNo=d.depart_class AND cs.cscode=d.cscode AND d.Oid="+dtimeoid);			
		
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		request.setAttribute("old", manager.ezGetBy("SELECT d.school_year, d.school_term, d.Oid, cs.chi_name, c.ClassName FROM Savedtime d, Csno cs, Class c WHERE " +
				"c.ClassNo=d.depart_class AND cs.cscode=d.cscode AND d.techid='"+c.getMember().getIdno()+"' ORDER BY d.school_year, d.school_term, d.depart_class DESC"));
		map.put("Syllabi", manager.parseSyl((String)map.get("Syllabi")));
		map.put("Syllabi_sub", manager.parseSyls((String)map.get("Syllabi_sub")));			
		request.setAttribute("bDtime", map);		
		
		ActionMessages msg = new ActionMessages();		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存完成，若不再修改請點選下方返回列表"));			
		saveMessages(request, msg);			
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("SaveInt", "saveInt");
		map.put("SaveSyl", "saveSyl");
		return map;
	}
}
