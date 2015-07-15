package tw.edu.chit.struts.action;

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

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;

public class ClassCourseSearchAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		request.setAttribute("AllCampus", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Campus'"));
		request.setAttribute("AllSchool", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='School'"));
		request.setAttribute("AllDept", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Dept'"));
		request.setAttribute("AllSchoolType", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Stype'"));
		setContentPage(request.getSession(false), "publicAccess/ListCourse.jsp");
		
		
		return mapping.findForward("index");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm cForm = (DynaActionForm) form;
		
		String CampusNo=cForm.getString("CampusNo");
		String SchoolNo=cForm.getString("SchoolNo");
		String DeptNo=cForm.getString("DeptNo");
		String Grade=cForm.getString("Grade");
		String ClassNo=cForm.getString("ClassNo");
		String cname=cForm.getString("cname");
		String week=cForm.getString("week");
		String begin=cForm.getString("begin");
		String end=cForm.getString("end");
		String chi_name=cForm.getString("chi_name");
		String opt=cForm.getString("opt");
		String open=cForm.getString("open");
		String elearning=cForm.getString("elearning");
		
		StringBuilder depart_class=new StringBuilder();
		if(!CampusNo.equals("")){depart_class.append(CampusNo);}else{depart_class.append("_");}
		if(!SchoolNo.equals("")){depart_class.append(SchoolNo);}else{depart_class.append("__");}
		if(!DeptNo.equals("")){depart_class.append(DeptNo);}else{depart_class.append("_");}
		if(!Grade.equals("")){depart_class.append(Grade);}else{depart_class.append("_");}
		if(!ClassNo.equals("")){depart_class.append(ClassNo);}else{depart_class.append("_");}
		
		StringBuilder sql=new StringBuilder("SELECT (SELECT COUNT(*)FROM Seld WHERE Dtime_oid=d.Oid)as count, cl.ClassName, e.cname, c.chi_name, d.Oid, d.credit, d.opt, " +
		"d.open, d.thour, d.elearning, d.techid, cl.ClassNo FROM " +
		"((Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno) " +
		"LEFT OUTER JOIN Dtime_class dc ON d.Oid=dc.Dtime_oid), Csno c, Class cl " +
		"WHERE cl.ClassNo=d.depart_class AND d.cscode=c.cscode AND d.Sterm='"+manager.getSchoolTerm()+"' ");
		
		if(!depart_class.equals("")){sql.append("AND d.depart_class LIKE'"+depart_class+"%' ");}
		if(!chi_name.equals("")){sql.append("AND c.chi_name LIKE '%"+chi_name+"%' ");}
		if(!cname.equals("")){sql.append("AND e.cname LIKE'%"+cname+"%' ");}
		if(!week.equals("")){sql.append("AND dc.week='"+week+"' ");}
		if(!begin.equals("")){sql.append("AND dc.begin>='"+begin+"' ");}
		if(!end.equals("")){sql.append("AND dc.end<='"+end+"' ");}
		if(!opt.equals("")){sql.append("AND d.opt='"+opt+"' ");}
		if(!open.equals("")){sql.append("AND d.open='"+open+"' ");}
		if(!elearning.equals("")){sql.append("AND d.elearning='"+elearning+"' ");}
		
		sql.append(" GROUP BY d.Oid");
		
		
		
		List list=manager.ezGetBy(sql.toString());
		List tmp;
		for(int i=0; i<list.size(); i++){			
			sql=new StringBuilder();
			tmp=manager.ezGetBy("SELECT week, begin, end, place FROM Dtime_class WHERE Dtime_oid="+((Map)list.get(i)).get("Oid"));
			
			for(int j=0; j<tmp.size(); j++){
				sql.append(manager.getWeekChar(Integer.parseInt(((Map)tmp.get(j)).get("week").toString()))+
				((Map)tmp.get(j)).get("begin")+"~"+((Map)tmp.get(j)).get("end")+", "+((Map)tmp.get(j)).get("place")+
				" <a href='javascript:;' onClick=\"techTable('"+((Map)tmp.get(j)).get("place")+"', this.id, 'room')\" id=\"r"+((Map)list.get(i)).get("Oid")+"\">課表</a>, ");
			}
			if(sql.length()>2){sql.delete(sql.length()-2, sql.length());}
			
			if(((Map)list.get(i)).get("cname")!=null){
				if(((Map)list.get(i)).get("cname").equals("")){
					((Map)list.get(i)).put("cname", "未指定");					
				}else{
					((Map)list.get(i)).put("cname", ((Map)list.get(i)).get("cname")+
					" <a href='javascript:;'  onClick=\"techTable('"+((Map)list.get(i)).get("techid")+"', this.id, 'tech')\" id=\"t"+((Map)list.get(i)).get("Oid")+"\">課表</a>");	
				}
			}else{
				((Map)list.get(i)).put("cname", "未指定");
			}
			
			
			((Map)list.get(i)).put("className", ((Map)list.get(i)).get("className")+
			" <a href='javascript:;' onClick=\"techTable('"+((Map)list.get(i)).get("ClassNo")+"', this.id, 'class')\" id=\"t"+((Map)list.get(i)).get("Oid")+"\">課表</a>");
			((Map)list.get(i)).put("time", sql);
			
			
			//((Map)list.get(i)).put("Select_Limit", ((Map)list.get(i)).get("count")+"/"+((Map)list.get(i)).get("Select_Limit"));
			((Map)list.get(i)).put("Select_Limit", ((Map)list.get(i)).get("count"));
			
			
			((Map)list.get(i)).put("opt", Global.CourseOpt.getProperty(((Map)list.get(i)).get("opt").toString()));
			if(((Map)list.get(i)).get("open").equals(true)){((Map)list.get(i)).put("open", "開放");}else{((Map)list.get(i)).put("open", "");}			
			((Map)list.get(i)).put("Syl", "<a href='/CIS/Print/teacher/SylDoc.do?Oid="+((Map)list.get(i)).get("Oid")+"' target='_blank'>下載</a>");
			((Map)list.get(i)).put("Int", "<a href='/CIS/Print/teacher/IntorDoc.do?Oid="+((Map)list.get(i)).get("Oid")+"' target='_blank'>下載</a>");
		}		
		request.setAttribute("csses", list);		
		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm cForm = (DynaActionForm) form;		
		cForm.set("CampusNo", "");
		cForm.set("SchoolNo", "");
		cForm.set("DeptNo", "");
		cForm.set("Grade", "");
		cForm.set("ClassNo", "");
		cForm.set("cname", "");
		cForm.set("week", "");
		cForm.set("begin", "");
		cForm.set("end", "");
		cForm.set("opt", "");
		cForm.set("elearning", "");
		cForm.set("chi_name", "");
		HttpSession session = request.getSession(false);
		session.removeAttribute("csses");
		
		return unspecified(mapping, form, request, response);
	}
	
	

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Clear", "clear");
		return map;
	}

}
