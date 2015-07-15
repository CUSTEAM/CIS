package tw.edu.chit.struts.action.student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 線上請假
 * @author John
 *
 */
public class AbseManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		String student_no=c.getStudent().getStudentNo();
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		//String 
		
		//處理日期
		Date now;
		if(request.getParameter("weekday")==null){
			now=new Date();
		}else{			
			now=sf.parse(request.getParameter("weekday"));			
		}		
		Calendar ca=Calendar.getInstance();		
		ca.setTime(now);
		if(ca.get(Calendar.DAY_OF_WEEK)==1){
			ca.add(Calendar.DAY_OF_YEAR, -1);
		}
		
		ca.set(Calendar.DAY_OF_WEEK, 1);		
		String weekday[]=new String[9];
		for(int i=0; i<weekday.length; i++){
			weekday[i]=sf.format(ca.getTime());
			ca.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		request.setAttribute("weekday", weekday);
		
		//此次作業中不會變動的常態資訊
		if(session.getAttribute("allClass")==null){
			session.setAttribute("allClass", manager.ezGetBy("SELECT d.Oid as dOid, d.thour, d.credit, " +
			"d.Oid as dtOid, d.techid, e.cname, c.cscode, c.chi_name,dc.* FROM stmd st, " +
			"Seld s, (Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno), Dtime_class dc, " +
			"Csno c WHERE st.student_no=s.student_no AND s.Dtime_oid=d.Oid AND " +
			"c.cscode=d.cscode AND d.Oid=dc.Dtime_oid AND d.Sterm='"+manager.getSchoolTerm()+"' AND s.student_no='"+student_no+"'"));
		}
		
		String bs[];
		List abs=manager.ezGetBy("SELECT * FROM Dilg WHERE student_no='"+student_no+"' AND " +
				"ddate<='"+weekday[7]+"' AND ddate>='"+weekday[1]+"'");
		
		//boolean isNew;
		for(int i=0; i<abs.size(); i++){
			
			bs=new String[16];
			for(int j=1; j<=15; j++){				
				if(((Map)abs.get(i)).get("abs"+j)!=null){
										
					bs[j]=((Map)abs.get(i)).get("abs"+j).toString();
				}else{
					bs[j]="";
				}
			}			
			((Map)abs.get(i)).put("bs", bs);			
		}
		
		request.setAttribute("setNow", now);
		request.setAttribute("abs", abs);
		
		//已請假
		
				
				
		
		
		setContentPage(request.getSession(false), "student/AbseManager.jsp");
		return mapping.findForward("Main");
	}
	
	
	public ActionForward creatAbs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;
		
		/*
		Cookie cookies[]=request.getCookies();
		Cookie cookie;
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			//if (cookieName.equals(cookie.getName())) {
				//return (cookie.getValue());
			//}
			System.out.println("name="+cookie.getName()+
					", value="+cookie.getValue()+
					", domain="+cookie.getDomain()+
					", path="+cookie.getPath()+
					", comment="+cookie.getComment()+
					", maxAge="+cookie.getMaxAge()+
					", version="+cookie.getVersion()
					
			);
		}
		*/
		
		
		/*
		List list=manager.ezGetBy("SELECT * FROM TxtGroup");		
		List mabers;
		Map map;
		List tmp;
		for(int i=0; i<list.size(); i++){
			map=manager.analyseEmpl(((Map)list.get(i)).get("members").toString());
			
			tmp=(List)map.get("empls");
			System.out.println(((Map)list.get(i)).get("name"));
			for(int j=0; j<tmp.size(); j++){
				System.out.print(((Map)tmp.get(j)).get("cname")+", ");
			}
			System.out.println();
		}
		*/
		
		
		List list=manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Unit'");
		List tmp;
		StringBuilder sb;
		for(int i=0; i<list.size(); i++){
			//System.out.println( ((Map)list.get(i)).get("name")  );			
			tmp=manager.ezGetBy("SELECT e.cname FROM UnitBelong u, empl e WHERE u.EmpOid=e.Oid AND u.UnitNo='"+((Map)list.get(i)).get("idno")+"'");
			sb=new StringBuilder();
			for(int j=0; j<tmp.size(); j++){
				sb.append(  ((Map)tmp.get(j)).get("cname")+", ");
			}
			manager.executeSql("INSERT INTO TxtGroup(name, members)VALUES('"+((Map)list.get(i)).get("name")+"', '"+sb+"')");
		}
		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward clear(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)throws Exception {				
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請點選正確的按鈕"));
		saveMessages(request, msg);
		
		
		return mapping.findForward("Main");
	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("CreatAbs", "creatAbs");
		map.put("Clear", "clear");		
		return map;
	}
	
	
}
