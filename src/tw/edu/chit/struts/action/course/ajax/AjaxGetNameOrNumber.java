package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.DynaActionForm;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Csno;
import tw.edu.chit.service.CourseManager;

/**
 * 產生動態下拉選單
 * @author JOHN
 */
public class AjaxGetNameOrNumber extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		List list=genSource(request.getParameter("table"), request.getParameter("type"), request.getParameter("query"));		
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();		
		out.println("<pront>");		
		if(list.size()<1){			
			out.println("<name>無對應資料                           </name>");
			out.println("<no>無對應資料                            </no>");
			}else{		
				for(int i=0; i<list.size(); i++){	
					out.println("<name>"+((Map)list.get(i)).get("name")+"</name>");
					out.println("<no>"+((Map)list.get(i)).get("no")+"</no>");
					
				}			
		}		
		out.println("</pront>");
		out.close();		
	}
	
	private List genSource(String table, String type, String nameOrNumber) throws UnsupportedEncodingException{
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		StringBuffer sql=new StringBuffer();
		List list;
		//查班級
		if(table.trim().equals("Class")){
			sql.append("SELECT ClassNo as no, ClassName as name FROM Class WHERE ");
			
			if(type.trim().equals("name")){
				sql.append("ClassName LIKE '"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%'");
			}else{
				sql.append("ClassNo LIKE '"+nameOrNumber+"%' limit 10");
			}
			list=manager.ezGetBy(sql.toString());			
			return list;
		}
		//查暑修班級
		if(table.trim().equals("Sabbr")){
			sql.append("SELECT no, name FROM Sabbr WHERE ");
			
			if(type.trim().equals("name")){
				sql.append("name LIKE '"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%' limit 10");
			}else{
				sql.append("no LIKE '"+nameOrNumber+"%' limit 10");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}
		
		//課程
		if(table.trim().equals("Csno")){
			sql.append("SELECT cscode as no, chi_name as name FROM Csno WHERE ");
			
			if(type.trim().equals("name")){
				sql.append("chi_name LIKE '"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%' limit 10");
			}else{
				sql.append("cscode LIKE '"+nameOrNumber+"%' limit 10");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}

		//人員與單位對應	
		if(table.trim().equals("empl_noid")){
			sql.append("SELECT e.cname as name, c.name as no FROM empl e LEFT OUTER JOIN CodeEmpl c ON e.unit=c.idno AND " +
					"(c.category='Unit' OR c.category='UnitTeach')WHERE ");			
			if(type.trim().equals("name")){
				sql.append("cname LIKE '"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%' limit 10");
			}else{
				sql.append("idno LIKE '"+nameOrNumber+"%' limit 10");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}
		
		//人員與身分證字號對應
		if(table.trim().equals("empl")){
			sql.append("SELECT idno as no, cname as name FROM empl WHERE ");
			
			if(type.trim().equals("name")){
				sql.append("cname LIKE '"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%' limit 10");
			}else{
				sql.append("idno LIKE '"+nameOrNumber+"%' limit 10");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}
		
		//學生
		if(table.trim().equals("stmd")){
			sql.append("SELECT student_no as no, student_name as name FROM stmd WHERE ");
			
			if(type.trim().equals("name")){
				sql.append("student_name LIKE '"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%' limit 10");
			}else{
				sql.append("student_no LIKE '"+nameOrNumber+"%' limit 10");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}		
		
		//學生 - 身分證號或學號
		if(table.trim().equals("stmid")){
			sql.append("SELECT student_no as no, student_name as name FROM stmd WHERE ");			
			if(type.trim().equals("name")){
				sql.append("student_name LIKE '"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%' limit 10");
			}else{
				
				sql.append("(idno LIKE '"+nameOrNumber+"%' OR student_no LIKE'"+nameOrNumber+"%') limit 10");
			}
			list=manager.ezGetBy(sql.toString());//取得在校生
			
			
			sql=new StringBuffer("SELECT student_no as no, student_name as name FROM Gstmd WHERE ");			
			if(type.trim().equals("name")){
				sql.append("student_name LIKE '"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%' limit 10");
			}else{
				
				sql.append("(idno LIKE '"+nameOrNumber+"%' OR student_no LIKE'"+nameOrNumber+"%') limit 10");
			}
			list.addAll(manager.ezGetBy(sql.toString()));//畢業生			
			return list;
		}		

		//證照
		if(table.trim().equals("license")){
			sql.append("SELECT Code as no, Name as name FROM LicenseCode WHERE ");
			
			if(type.trim().equals("name")){
				sql.append("name LIKE '"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%' limit 10");
			}else{
				sql.append("Code LIKE '"+nameOrNumber+"%' limit 10");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}
		
		//休退畢生
		if(table.trim().equals("gstmd")){
			sql.append("SELECT student_no as no, student_name as name FROM Gstmd WHERE ");
			
			if(type.trim().equals("name")){
				sql.append("student_name LIKE '"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%' limit 10");
			}else{
				sql.append("student_no LIKE '"+nameOrNumber+"%' limit 10");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}
		
		//快速加退選
		if(table.trim().equals("dtimeOid")){
			sql.append("SELECT d.Oid as no, c.chi_name as name FROM Dtime d, Csno c WHERE d.cscode=c.cscode AND ");
			
			if(type.trim().equals("name")){
				sql.append("d.Oid LIKE '"+nameOrNumber+"%' limit 10");
			}else{
				sql.append("d.Oid LIKE '"+nameOrNumber+"%' limit 10");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}
		
		//暑修學制
		if(table.trim().equals("summerSchool")){
			sql.append("SELECT idno as no, name as name FROM code5 WHERE category='Summer' AND");
			
			if(type.trim().equals("name")){
				sql.append("name LIKE '"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%' limit 10");
			}else{
				sql.append("idno LIKE '"+nameOrNumber+"%' limit 10");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}

		//畢業學校
		if(table.trim().equals("gradSchool")){
			sql.append("SELECT no, name FROM Recruit_school WHERE ");
			
			if(type.trim().equals("name")){
				sql.append("name LIKE '%"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%'");
			}else{
				sql.append("no LIKE '"+nameOrNumber+"%'");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}
		
		//教室
		if(table.trim().equals("Nabbr")){			
			sql.append("SELECT room_id as no, name2 as name FROM Nabbr WHERE ");
			
			if(type.trim().equals("name")){
				sql.append("name2 LIKE '"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%' limit 10");
			}else{
				sql.append("room_id LIKE '"+nameOrNumber+"%' limit 10");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}

		//郵遞區號
		if(table.trim().equals("Zcode")){
			sql.append("SELECT no , name FROM Zcode WHERE ");
			
			if(type.trim().equals("name")){
				sql.append("name LIKE '%"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%' limit 10");
			}else{
				sql.append("no LIKE '"+nameOrNumber+"%' limit 10");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}
		
		//新畢業學校代碼
		if(table.trim().equals("Recruitschool")){
			sql.append("SELECT no, name FROM Recruit_school WHERE ");
			
			if(type.trim().equals("name")){
				sql.append("name LIKE '%"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%'");
			}else{
				sql.append("no LIKE '"+nameOrNumber+"%'");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}
		
		//在職人員姓名和電子郵件
		if(table.trim().equals("cnamEmail")){
			sql.append("SELECT Email as no, cname as name FROM empl WHERE ");
			
			if(type.trim().equals("name")){
				sql.append("cname LIKE '%"+new String(nameOrNumber.getBytes("iso-8859-1"),"utf-8")+"%'");
			}else{
				sql.append("Email LIKE '"+nameOrNumber+"%'");
			}
			list=manager.ezGetBy(sql.toString());
			return list;
		}
		
		return new ArrayList();
		
	}
	
	
	
}