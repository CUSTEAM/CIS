package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

/**
 * 處理empl、dempl的混合自動完成
 * @author JOHN
 *
 */
public class shMember extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		String tmp;
		List members=new ArrayList();
		Map map;
		
		String type=request.getParameter("type");
		
		if(type.equals("s")){			
			String stNameNo=new String(request.getParameter("stNameNo").getBytes("iso-8859-1"),"utf-8");
			String ClassNo=request.getParameter("Cidno")+request.getParameter("Sidno")+
			request.getParameter("Didno")+request.getParameter("Grade")+request.getParameter("ClassNo");						
			if(!stNameNo.equals("")){
				members.addAll(manager.ezGetBy("SELECT c.ClassName as uname, s.student_name as cname, s.Email FROM stmd s, Class c WHERE (s.student_no LIKE'%"+stNameNo+"%' " +
				"OR s.student_name LIKE'%"+stNameNo+"%') AND c.ClassNo=s.depart_class AND s.Email IS NOT NULL LIMIT 500"));
			}else{
				members.addAll(manager.ezGetBy("SELECT c.ClassName as uname, s.student_name as cname, s.Email FROM stmd s, Class c WHERE " +
				"s.depart_class LIKE'"+ClassNo+"%' AND c.ClassNo=s.depart_class AND s.Email IS NOT NULL LIMIT 500"));
			}			
		}
		
		//查詢教職員
		if(type.equals("e")){
			String group=request.getParameter("group");	
			String myGroup=request.getParameter("myGroup");			
			String cname=new String(request.getParameter("cname").getBytes("iso-8859-1"),"utf-8");
			String sname=new String(request.getParameter("sname").getBytes("iso-8859-1"),"utf-8");		
			String unit=request.getParameter("unit");	
			String category=request.getParameter("category");	
			String Tutor=request.getParameter("Tutor");
			String pcode=request.getParameter("pcode");	
			String Director=request.getParameter("Director");
						
			if(!group.equals("")){
				tmp=tmp=manager.ezGetString("SELECT members FROM TxtGroup WHERE Oid="+group);
				map=manager.analyseEmpl(tmp);		
				members.addAll((List)map.get("empls"));
			}
			
			if(!myGroup.equals("")){
				tmp=tmp=manager.ezGetString("SELECT members FROM TxtGroup WHERE Oid="+myGroup);
				map=manager.analyseEmpl(tmp);		
				members.addAll((List)map.get("empls"));
			}
			
			if(!cname.equals("")){
				members.addAll(manager.ezGetBy("SELECT e.idno, e.cname, e.Email, c.name as uname FROM empl e LEFT OUTER JOIN CodeEmpl c ON e.unit=c.idno AND " +
				"(c.category='UnitTeach' OR c.category='Unit') WHERE e.cname LIKE'%"+cname+"%' AND Email!='' AND Email IS NOT NULL"));
			}
			
			if(!sname.equals("")){
				members.addAll(manager.ezGetBy("SELECT e.idno, e.cname, e.Email, c.name as uname FROM empl e LEFT OUTER JOIN CodeEmpl c ON e.unit=c.idno AND " +
				"(c.category='UnitTeach' OR c.category='Unit') WHERE e.sname LIKE'%"+sname+"%' AND Email!='' AND Email IS NOT NULL"));
			}
			
			if(!unit.equals("")){
				members.addAll(manager.ezGetBy("SELECT e.idno, e.cname, e.Email, c.name as uname FROM empl e LEFT OUTER JOIN CodeEmpl c ON e.unit=c.idno AND " +
				"(c.category='UnitTeach' OR c.category='Unit') WHERE e.unit='"+unit+"' AND Email!='' AND Email IS NOT NULL"));
			}
			
			if(!category.equals("")){
				members.addAll(manager.ezGetBy("SELECT e.idno, e.cname, e.Email, c.name as uname FROM empl e LEFT OUTER JOIN CodeEmpl c ON e.unit=c.idno AND " +
				"(c.category='UnitTeach' OR c.category='Unit') WHERE e.category='"+category+"' AND Email!='' AND Email IS NOT NULL"));
			}
			
			if(!Tutor.equals("")){
				members.addAll(manager.ezGetBy("SELECT e.idno, e.cname, e.Email, c.name as uname FROM empl e LEFT OUTER JOIN CodeEmpl c ON e.unit=c.idno AND " +
				"(c.category='UnitTeach' OR c.category='Unit') WHERE e.Tutor='"+Tutor+"' AND Email!='' AND Email IS NOT NULL"));
			}
			
			if(!pcode.equals("")){
				members.addAll(manager.ezGetBy("SELECT e.idno, e.cname, e.Email, c.name as uname FROM empl e LEFT OUTER JOIN CodeEmpl c ON e.unit=c.idno AND " +
				"(c.category='UnitTeach' OR c.category='Unit') WHERE e.pcode='"+pcode+"' AND Email!='' AND Email IS NOT NULL"));
			}
			
			if(!Director.equals("")){
				members.addAll(manager.ezGetBy("SELECT e.idno, e.cname, e.Email, c.name as uname FROM empl e LEFT OUTER JOIN CodeEmpl c ON e.unit=c.idno AND " +
				"(c.category='UnitTeach' OR c.category='Unit') WHERE e.Director='"+Director+"' AND Email!='' AND Email IS NOT NULL"));
			}
		}
		
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();		
		out.println("<pront>");		
		if(members.size()>0){
			for(int i=0; i<members.size(); i++){
				out.println("<name>"+((Map)members.get(i)).get("cname")+"</name>");
				out.println("<email>"+((Map)members.get(i)).get("Email")+"</email>");
				out.println("<uname>"+((Map)members.get(i)).get("uname")+"</uname>");
			}
		}else{
			out.println("<name>查無名稱</name>");
			out.println("<email>無對應電子郵件</email>");
			out.println("<email>無對應單位</email>");
		}			
		out.println("</pront>");
		out.close();
		
	}

}
