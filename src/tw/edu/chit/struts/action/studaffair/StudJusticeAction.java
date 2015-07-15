package tw.edu.chit.struts.action.studaffair;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import tw.edu.chit.dao.CourseDAO;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.DtimeExam;
import tw.edu.chit.model.DtimeTeacher;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Opencs;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class StudJusticeAction extends BaseLookupDispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("allSchoolType", manager.getAllSchoolType());
		
		
		
		
		setContentPage(request.getSession(false), "score/StudJustice.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Query", "list");
		map.put("Clear", "clear");
		return map;
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dynaform = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息

		String classLess = dynaform.getString("classLess");
		String stuNo = dynaform.getString("stuNo");
		String stuName = dynaform.getString("stuName");
		String docNo = dynaform.getString("docNo");
		String begin = dynaform.getString("begin");
		String end = dynaform.getString("end");
		String school = dynaform.getString("school");
		
		String a ="1961-01-01";
		String b ="9999-12-31";

		if(classLess.trim().equals("")){
			classLess="%";
		}
		if(stuNo.trim().equals("")){
			stuNo="%";
		}
		if(stuName.trim().equals("")){
			stuName="%";
		}
		if(docNo.trim().equals("")){
			docNo="%";
		}

		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		if(!begin.trim().equals("")){
			a=dateFormat.format(Toolket.parseDate(begin));
		}
		if(!end.trim().equals("")){
			b=dateFormat.format(Toolket.parseDate(end));
		}	
		
		StringBuilder sb;
		
		try{
			begin=manager.convertDate(begin);
		}catch(Exception e){
			begin=a;
		}
		try{
			end=manager.convertDate(end);
		}catch(Exception e){
			end=b;
		}
		
		
		if(!school.equals("")){
			
			sb=new StringBuilder("SELECT cl.ClassName, d.no, st.sex, st.student_name, d.student_no, c.name, s.name as n1, d.cnt1, s1.name as n2, d.cnt2, d.ddate " +
					"FROM Class cl, stmd st, subs s, code2 c, desd d LEFT OUTER JOIN subs s1 ON s1.no=d.kind2 WHERE cl.ClassNo=d.depart_class AND " +
					"st.student_no=d.student_no AND d.reason=c.no AND s.no=d.kind1 AND d.student_no LIKE '"+stuNo+"%' AND " +
					"st.student_no LIKE '"+stuNo+"%' AND " +
					"d.no LIKE '"+docNo+"%' AND " +
					"st.student_name LIKE '"+stuName+"%' AND d.ddate>='"+a+"' AND d.ddate<='"+b+"' AND (");
			List tmp=manager.ezGetBy("SELECT idno FROM code5 WHERE name LIKE '"+school+"%' AND category='SchoolType'");
			
			System.out.println(sb);
			for(int i=0; i<tmp.size(); i++){
				
				sb.append("d.depart_class LIKE'"+((Map)tmp.get(i)).get("idno")+"%'  OR " );
			}
			sb.delete(sb.length()-5, sb.length());
			sb.append(")");
			
			List obj=manager.ezGetBy(sb.toString());
			Map map;			
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			for(int i=0; i<obj.size(); i++){
				map=new HashMap();
				if(((Map)obj.get(i)).get("sex").equals("1")){
					map.put("sex", "男");
				}else{
					map.put("sex", "女");
				}				
				try {
					date = (Date)df.parse(((Map)obj.get(i)).get("ddate").toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				map.put("ddate", Toolket.printNativeDate(date));
				map.put("ClassName", ((Map)obj.get(i)).get("ClassName"));
				((Map)obj.get(i)).putAll(map);
			}
			session.setAttribute("jList", obj);
		}else{
			session.setAttribute("jList", manager.getJustBy(classLess, stuNo, stuName, docNo, a, b));
		}
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢完成"));
		saveMessages(request, msg);
		
		

		setContentPage(request.getSession(false), "score/StudJustice.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		DynaActionForm dynaform = (DynaActionForm) form;

		dynaform.set("classLess","");
		dynaform.set("stuNo","");
		dynaform.set("stuName","");
		dynaform.set("docNo","");
		dynaform.set("begin","");
		dynaform.set("end","");



		session.removeAttribute("jList");

		setContentPage(request.getSession(false), "score/StudJustice.jsp");
		return mapping.findForward("Main");
	}

}
