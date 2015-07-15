package tw.edu.chit.struts.action.report.student;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.Global;
/**
 * 全部可選課程列表 ajax
 * @author shawn
 *
 */
public class MyCourseSearh extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		
		UserCredential u = (UserCredential) session.getAttribute("Credential");
		String name=new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");
		
		Clazz c=(Clazz) session.getAttribute("myGrade");//初入時已計算的實體或虛擬年級資訊
		
		//跨選課程		
		List list=manager.ezGetBy("SELECT Select_Limit, (SELECT COUNT(*)FROM Seld WHERE Dtime_oid=d.Oid) as seled, " +
		"cl.ClassName, d.opt, d.credit, d.thour, d.elearning, " +
		"d.Oid, c.chi_name, dc.week, dc.begin, dc.end, e.cname FROM " +
		"Dtime d, empl e, Dtime_class dc, Opencs o, Csno c, Class cl " +
		"WHERE cl.ClassNo=d.depart_class AND c.cscode=d.cscode AND e.idno=d.techid AND " +
		"d.Oid=dc.Dtime_oid AND o.Dtime_oid=d.Oid AND " +
		"d.Sterm='"+request.getParameter("term")+"' AND " +
		"(o.Cidno='"+c.getCampusNo()+"' OR o.Cidno='*') AND " +
		"(o.Sidno='"+c.getSchoolNo()+"' OR o.Sidno='*') AND " +
		"(o.Didno='"+c.getDeptNo()+"' OR o.Didno='*') AND " +
		"(o.Grade<="+c.getGrade()+" OR o.Grade='*')AND " +
		"d.cscode NOT IN(SELECT cscode FROM ScoreHist WHERE student_no='" +u.getStudent().getStudentNo()+"' AND score>=60) AND "+
		"c.chi_name LIKE'"+name+"%' AND d.cscode!='50000' GROUP BY d.Oid ORDER BY dc.week, d.opt");

		//重修低年級課程
		list.addAll(manager.ezGetBy("SELECT Select_Limit, (SELECT COUNT(*)FROM Seld WHERE Dtime_oid=d.Oid) as seled, " +
				"cl.ClassName, d.opt, d.credit, d.thour, d.elearning, " +
				"d.Oid, c.chi_name, dc.week, dc.begin, dc.end, e.cname FROM Dtime d, empl e, " +
				"Dtime_class dc, Csno c, Class cl WHERE d.Sterm='"+request.getParameter("term")+"' AND " +
				"cl.ClassNo=d.depart_class AND e.idno=d.techid AND " +
				"c.cscode=d.cscode AND d.Oid=dc.Dtime_oid AND " +
				"(cl.CampusNo='"+c.getCampusNo()+"')AND " +
				"(cl.SchoolNo='"+c.getSchoolNo()+"')AND " +
				"(cl.DeptNo='"+c.getDeptNo()+"')AND " +
				"(cl.Grade<="+c.getGrade()+")AND " +
				"d.cscode NOT IN(SELECT cscode FROM ScoreHist WHERE student_no='" +u.getStudent().getStudentNo()+"' AND score>=60) AND "+
				"c.chi_name LIKE'" +name+"%' AND "+
				"d.cscode!='50000' GROUP BY d.Oid ORDER BY dc.week, d.opt"));
		
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		out.println("<pront>");
		if(list.size()==0){
			out.println("<error>e</error>");
		}else{			
			List dc;
			try{				
				if(request.getParameter("level").equals("1")){//第1階段
					
					for(int i=0; i<list.size(); i++){					
						out.println("<Oid>"+((Map)list.get(i)).get("Oid")+"</Oid>");
						out.println("<limit>"+((Map)list.get(i)).get("seled")+"/"+((Map)list.get(i)).get("Select_Limit")+"</limit>");
						out.println("<cname>"+((Map)list.get(i)).get("cname")+"</cname>");
						out.println("<ClassName>"+((Map)list.get(i)).get("ClassName")+"</ClassName>");					
						out.println("<opt>"+Global.CourseOpt.getProperty(String.valueOf(((Map)list.get(i)).get("opt")))+"</opt>");					
						out.println("<credit>"+((Map)list.get(i)).get("credit")+"</credit>");
						out.println("<thour>"+((Map)list.get(i)).get("thour")+"</thour>");					
						if(((Map)list.get(i)).get("elearning").equals("0")){
							out.println("<elearning>一般</elearning>");
						}else if(((Map)list.get(i)).get("elearning").equals("1")){
							out.println("<elearning>遠距</elearning>");
						}else if(((Map)list.get(i)).get("elearning").equals("2")){
							out.println("<elearning>輔助</elearning>");
						}else if(((Map)list.get(i)).get("elearning").equals("3")){
							out.println("<elearning>多媒</elearning>");
						}
						
						dc=manager.ezGetBy("SELECT week, begin, end FROM Dtime_class WHERE Dtime_oid="+((Map)list.get(i)).get("Oid"));
						if(dc.size()>1){
							out.println("<week0>"+((Map)dc.get(0)).get("week")+"</week0>");
							out.println("<begin0>"+((Map)dc.get(0)).get("begin")+"</begin0>");
							out.println("<end0>"+((Map)dc.get(0)).get("end")+"</end0>");
							out.println("<week1>"+((Map)dc.get(1)).get("week")+"</week1>");
							out.println("<begin1>"+((Map)dc.get(1)).get("begin")+"</begin1>");
							out.println("<end1>"+((Map)dc.get(1)).get("end")+"</end1>");							
						}else{							
							out.println("<week0>"+((Map)dc.get(0)).get("week")+"</week0>");
							out.println("<begin0>"+((Map)dc.get(0)).get("begin")+"</begin0>");
							out.println("<end0>"+((Map)dc.get(0)).get("end")+"</end0>");
							out.println("<week1>null</week1>");
							out.println("<begin1>null</begin1>");
							out.println("<end1>null</end1>");							
						}
						out.println("<chi_name>"+((Map)list.get(i)).get("chi_name")+"</chi_name>");
						out.println("<week>"+((Map)list.get(i)).get("week")+"</week>");
						out.println("<begin>"+((Map)list.get(i)).get("begin")+"</begin>");
						out.println("<end>"+((Map)list.get(i)).get("end")+"</end>");
					}
					
				}else{//第2階段
					
					int limit;
					int seled;
					for(int i=0; i<list.size(); i++){
						limit=Integer.parseInt(((Map)list.get(i)).get("Select_Limit").toString());
						seled=Integer.parseInt(((Map)list.get(i)).get("seled").toString());
						if(seled>=limit){continue;}
						
						out.println("<Oid>"+((Map)list.get(i)).get("Oid")+"</Oid>");
						out.println("<limit>"+seled+"/"+limit+"</limit>");
						out.println("<cname>"+((Map)list.get(i)).get("cname")+"</cname>");
						out.println("<ClassName>"+((Map)list.get(i)).get("ClassName")+"</ClassName>");					
						out.println("<opt>"+Global.CourseOpt.getProperty(String.valueOf(((Map)list.get(i)).get("opt")))+"</opt>");					
						out.println("<credit>"+((Map)list.get(i)).get("credit")+"</credit>");
						out.println("<thour>"+((Map)list.get(i)).get("thour")+"</thour>");					
						if(((Map)list.get(i)).get("elearning").equals("0")){
							out.println("<elearning>一般</elearning>");
						}else if(((Map)list.get(i)).get("elearning").equals("1")){
							out.println("<elearning>遠距</elearning>");
						}else if(((Map)list.get(i)).get("elearning").equals("2")){
							out.println("<elearning>輔助</elearning>");
						}else if(((Map)list.get(i)).get("elearning").equals("3")){
							out.println("<elearning>多媒</elearning>");
						}
						
						out.println("<chi_name>"+((Map)list.get(i)).get("chi_name")+"</chi_name>");
						dc=manager.ezGetBy("SELECT week, begin, end FROM Dtime_class WHERE Dtime_oid="+((Map)list.get(i)).get("Oid"));
						if(dc.size()>1){
							out.println("<week0>"+((Map)dc.get(0)).get("week")+"</week0>");
							out.println("<begin0>"+((Map)dc.get(0)).get("begin")+"</begin0>");
							out.println("<end0>"+((Map)dc.get(0)).get("end")+"</end0>");
							out.println("<week1>"+((Map)dc.get(1)).get("week")+"</week1>");
							out.println("<begin1>"+((Map)dc.get(1)).get("begin")+"</begin1>");
							out.println("<end1>"+((Map)dc.get(1)).get("end")+"</end1>");							
						}else{							
							out.println("<week0>"+((Map)dc.get(0)).get("week")+"</week0>");
							out.println("<begin0>"+((Map)dc.get(0)).get("begin")+"</begin0>");
							out.println("<end0>"+((Map)dc.get(0)).get("end")+"</end0>");
							out.println("<week1>null</week1>");
							out.println("<begin1>null</begin1>");
							out.println("<end1>null</end1>");							
						}						
						out.println("<week>"+((Map)list.get(i)).get("week")+"</week>");
						out.println("<begin>"+((Map)list.get(i)).get("begin")+"</begin>");
						out.println("<end>"+((Map)list.get(i)).get("end")+"</end>");
					}
					
					
				}
				
				
				
			}catch(Exception e){				
				e.printStackTrace();
				out.println("<error>e</error>");				
			}
		}	
		out.println("</pront>");
		out.close();
		
		return null;
	}

}
