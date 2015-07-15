package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Student;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;

/**
 * 年齡統計表
 * @author JOHN
 *
 */
public class CountStudentAge extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		HttpSession session = request.getSession(false);
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		SummerManager summerManager=(SummerManager) ctx.getBean("summerManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=CountStudents4StudentManager.xls");
		PrintWriter out=response.getWriter();
		
		List students=(List)session.getAttribute("students");
		Calendar now=Calendar.getInstance();
		Date date=new Date();
		now.setTime(date);
		int thisYear=now.get(Calendar.YEAR);		

		/*30歲以上的
		int up30men0=0;
		int up30girls0=0;
		
		int up30men1=0;
		int up30girls1=0;
		
		int up30men2=0;
		int up30girls2=0;
		
		int up30men3=0;
		int up30girls3=0;
		
		int up30men4=0;
		int up30girls4=0;
		
		int up30men5=0;
		int up30girls5=0;
		*/
		
		StringBuilder sb=new StringBuilder("SELECT s.birthday, s.sex, c.Grade FROM stmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo WHERE s.student_no IN(");
		for(int i=0; i<students.size(); i++){
			sb.append("'"+((Map)students.get(i)).get("student_no")+"',");
		}
		sb.delete(sb.length()-1, sb.length());
		//sb.append(")");//2010/12/5要求122A, 1220不列
		sb.append(")AND(s.depart_class<>'122A' AND s.depart_class<>'1220')");
		//System.out.println(sb);
		students=manager.ezGetBy(sb.toString());		
		out.println("<table width='100%' border='0'>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<table width='100%' border='1'>");
		out.println("<tr bgcolor='#f0fcd7'>");
		out.println("<td align=center>年齡</td>");
		out.println("<td align=center>1年級男</td>");
		out.println("<td align=center>1年級女</td>");
		out.println("<td align=center>2年級男</td>");
		out.println("<td align=center>2年級女</td>");
		out.println("<td align=center>3年級男</td>");
		out.println("<td align=center>3年級女</td>");
		out.println("<td align=center>4年級男</td>");
		out.println("<td align=center>4年級女</td>");
		out.println("<td align=center>延畢男</td>");
		out.println("<td align=center>延畢女</td>");
		out.println("<td align=center>跨校男</td>");
		out.println("<td align=center>跨校女</td>");
		out.println("<td align=center>男合計</td>");
		out.println("<td align=center>女合計</td>");
		out.println("<td align=center>總計</td>");
		out.println("</tr>");
		
		for(int i=15; i<=99; i++){
			//30歲以下的
			int men0=0;
			int girls0=0;
			
			int men1=0;
			int girls1=0;
			
			int men2=0;
			int girls2=0;
			
			int men3=0;
			int girls3=0;
			
			int men4=0;
			int girls4=0;
			
			int men5=0;
			int girls5=0;			
	
			for(int j=0; j<students.size(); j++){
				
				Calendar c=Calendar.getInstance();
				SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
				Date my;
				
				try {
					my = sf.parse(((Map)students.get(j)).get("birthday").toString());
				} catch (Exception e) {
					my=new Date();
				}
				c.setTime(my);
				int myAge=c.get(Calendar.YEAR);
				//System.out.println("我的年紀="+(thisYear-myAge)+"我是"+((Map)students.get(j)).get("Grade")+"年級");				
				int age=thisYear-myAge;
				//System.out.println(i+"歲, 要比較我的年級"+age+"歲, 結果應為:"+(age==i));
				
				//if(age<=30){
					
					if(age==i){
						
						if(((Map)students.get(j)).get("Grade")!=null){
							
							//System.out.println(i+"歲應多1人");
							if(((Map)students.get(j)).get("Grade").equals("1")){
								if(((Map)students.get(j)).get("sex").equals("1")){
									men1=men1+1;
								}else{
									girls1=girls1+1;
								}
							}
							
							if(((Map)students.get(j)).get("Grade").equals("2")){
								if(((Map)students.get(j)).get("sex").equals("1")){
									men2=men2+1;
								}else{
									girls2=girls2+1;
								}
							}
							
							if(((Map)students.get(j)).get("Grade").equals("3")){
								if(((Map)students.get(j)).get("sex").equals("1")){
									men3=men3+1;
								}else{
									girls3=girls3+1;
								}
							}
							
							if(((Map)students.get(j)).get("Grade").equals("4")){
								if(((Map)students.get(j)).get("sex").equals("1")){
									men4=men4+1;
								}else{
									girls4=girls4+1;
								}
							}
							
							if(((Map)students.get(j)).get("Grade").equals("5")){
								if(((Map)students.get(j)).get("sex").equals("1")){
									men5=men5+1;
								}else{
									girls5=girls5+1;
								}
							}
							
							if(((Map)students.get(j)).get("Grade").equals("0")){
								if(((Map)students.get(j)).get("sex").equals("1")){
									men0=men0+1;
								}else{
									girls0=girls0+1;
								}
							}
						}
						
						
					}
				/*	
				}else{
					
					if(age==i){
						//System.out.println(i+"歲應多1人");
						if(((Map)students.get(j)).get("Grade").equals("1")){
							if(((Map)students.get(j)).get("sex").equals("1")){
								up30men1=up30men1+1;
							}else{
								up30girls1=up30girls1+1;
							}
						}
						
						if(((Map)students.get(j)).get("Grade").equals("2")){
							if(((Map)students.get(j)).get("sex").equals("1")){
								up30men2=up30men2+1;
							}else{
								up30girls2=up30girls2+1;
							}
						}
						
						if(((Map)students.get(j)).get("Grade").equals("3")){
							if(((Map)students.get(j)).get("sex").equals("1")){
								up30men3=up30men3+1;
							}else{
								up30girls3=up30girls3+1;
							}
						}
						
						if(((Map)students.get(j)).get("Grade").equals("4")){
							if(((Map)students.get(j)).get("sex").equals("1")){
								up30men4=up30men4+1;
							}else{
								up30girls4=up30girls4+1;
							}
						}
						
						if(((Map)students.get(j)).get("Grade").equals("5")){
							if(((Map)students.get(j)).get("sex").equals("1")){
								up30men5=up30men5+1;
							}else{
								up30girls5=up30girls5+1;
							}
						}
						
						if(((Map)students.get(j)).get("Grade").equals("0")){
							if(((Map)students.get(j)).get("sex").equals("1")){
								up30men0=up30men0+1;
							}else{
								up30girls0=up30girls0+1;
							}
						}
					}
					
					
				}
				*/
			}
			
			int men=men1+men2+men3+men4+men5+men0;
			int girls=girls1+girls2+girls3+girls4+girls5+girls0;
			//int totalMen=men+girls;			
			
			//if(i<=30){
				out.println("<tr>");
				out.println("<td align=center>"+i+"歲</td>");
				out.println("<td>"+men1+"</td>");
				out.println("<td>"+girls1+"</td>");
				out.println("<td>"+men2+"</td>");
				out.println("<td>"+girls2+"</td>");
				out.println("<td>"+men3+"</td>");
				out.println("<td>"+girls3+"</td>");
				out.println("<td>"+men4+"</td>");
				out.println("<td>"+girls4+"</td>");
				out.println("<td>"+men5+"</td>");
				out.println("<td>"+girls5+"</td>");
				out.println("<td>"+men0+"</td>");
				out.println("<td>"+girls0+"</td>");
				out.println("<td>"+men+"</td>");
				out.println("<td>"+girls+"</td>");
				out.println("<td>"+(men+girls)+"</td>");
				out.println("</tr>");
			//}
			
		}
		
		
		/*
		int up30men=up30men1+up30men2+up30men3+up30men4+up30men5+up30men0;
		int up30girl=up30girls1+up30girls2+up30girls3+up30girls4+up30girls5+up30girls0;
		
		out.println("<tr>");
		out.println("<td align=center>30+</td>");
		out.println("<td>"+up30men1+"</td>");
		out.println("<td>"+up30girls1+"</td>");
		out.println("<td>"+up30men2+"</td>");
		out.println("<td>"+up30girls2+"</td>");
		out.println("<td>"+up30men3+"</td>");
		out.println("<td>"+up30girls3+"</td>");
		out.println("<td>"+up30men4+"</td>");
		out.println("<td>"+up30girls4+"</td>");
		out.println("<td>"+up30men5+"</td>");
		out.println("<td>"+up30girls5+"</td>");
		out.println("<td>"+up30men0+"</td>");
		out.println("<td>"+up30girls0+"</td>");
		out.println("<td>"+up30men+"</td>");
		out.println("<td>"+up30girl+"</td>");
		out.println("<td>"+(up30girl+up30men)+"</td>");
		out.println("</tr>");
		*/
		
		
		
		
		
		
		
		out.println("</table>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
		
		out.close();
		
		
		
		

	}

}
