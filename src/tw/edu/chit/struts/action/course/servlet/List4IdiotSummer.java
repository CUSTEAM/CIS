package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class List4IdiotSummer extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=List4Course35.xls");
		HttpSession session = request.getSession(false);
		
		int x=manager.getSchoolYear()-1;		
		int y=manager.getSchoolYear()-3;
		int z=manager.getSchoolYear()-3;
		PrintWriter out=response.getWriter();
		List list=(List)session.getAttribute("fail");
		List idiots;
		
		
		
		String classLess=(String)session.getAttribute("classLess");
		
		//out.println("			<table>");
		for(int i=0; i<list.size(); i++){
			
			/*
			if(i%3==0||i==0){
				out.println("			<tr>");
				out.println("			<td>");
			}else{
				out.println("			<td>");
			}
			*/			
			
			out.println("			<table border='1' align='left' bgcolor='#CFE69F' width='100%'>");
			out.println("				<tr>");
			out.println("					<td align='center' bgcolor='#f0fcd7'>"+((Map)list.get(i)).get("cscode")+"</td>");
			out.println("					<td align='center' bgcolor='#f0fcd7'>"+((Map)list.get(i)).get("chi_name")+"</td>");
			out.println("					<td align='center' bgcolor='#f0fcd7'>"+((Map)list.get(i)).get("credit")+"學分</td>");
			out.println("					<td align='center' bgcolor='#f0fcd7'>學生班級</td>");
			
			out.println("					<td align='center' bgcolor='#f0fcd7'>選別</td>");
			out.println("					<td align='center' bgcolor='#f0fcd7'>課程名稱</td>");
			out.println("				</tr>");			
			out.println("			</table>");
			
			//4年或2年
			try{
				if(((Map)list.get(i)).get("stdepart_class").toString().charAt(2)=='2'){
					z=x;
				}else{
					z=y;
				}
			}catch(Exception e){
				z=y;
			}
			
			
			
			idiots=manager.ezGetBy("SELECT s.opt, c.ClassName, st.student_no, st.student_name, s.score FROM ScoreHist s LEFT OUTER JOIN stmd st " +
					"ON st.student_no=s.student_no, Class c WHERE s.score<60 AND s.cscode='"+((Map)list.get(i)).get("cscode")+"' AND s.stdepart_class LIKE '"+((Map)list.get(i)).get("stdepart_class")+"%' AND  " +
					"c.ClassNo=st.depart_class AND s.school_year>="+z+" ORDER BY s.cscode");
			
			/*
			idiots.addAll(manager.ezGetBy("SELECT s.opt, c.ClassName, st.student_no, st.student_name, s.score FROM ScoreHist s LEFT OUTER JOIN Gstmd st " +
					"ON st.student_no=s.student_no, Class c WHERE s.score<60 AND s.cscode='"+((Map)list.get(i)).get("cscode")+"' AND s.stdepart_class LIKE '"+classLess+"%' AND  " +
					"c.ClassNo=st.depart_class AND s.school_year>="+z));
			*/
			
			for(int j=0;j<idiots.size(); j++){
				
				if(((Map)idiots.get(j)).get("student_no")!=null){
					
					
					out.println("			<table border='1' align='left' width='100%'>");
					//if(((Map)idiots.get(j)).get("student_no")!=null){
						out.println("				<tr>");
						out.println("					<td align='center'>"+((Map)idiots.get(j)).get("student_no")+"</td>");
						out.println("					<td align='center'>"+((Map)idiots.get(j)).get("student_name")+"</td>");
						out.println("					<td align='center'>"+((Map)idiots.get(j)).get("score")+"</td>");
						out.println("					<td align='center'>"+((Map)idiots.get(j)).get("ClassName")+"</td>");
						if(((Map)idiots.get(j)).get("opt").equals("1")){
							out.println("					<td align='center'>必修</td>");
						}else{
							out.println("					<td align='center'>選修</td>");
						}
						
						out.println("					<td align='center'>"+((Map)list.get(i)).get("chi_name")+"</td>");
						out.println("				</tr>");					
					//}
					out.println("			</table>");
					
					out.close();
					
				}
				
				
			}
			
			
			
			
			/*
			if(i%3==0||i==0){
				out.println("			</td>");
				out.println("			</tr>");
			}else{
				out.println("			</td>");
			}
			*/
		}
		//out.println("			</table>");
		
		
		
	}
}
