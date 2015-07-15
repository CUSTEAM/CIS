package tw.edu.chit.struts.action.registration.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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

public class List4CheckCredit extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		HttpSession session = request.getSession(false);
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		
		response.setHeader("Content-disposition","attachment;filename=List4CheckCredit.xls");		
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'></head><body>");
		
		String SchoolNo=request.getParameter("SchoolNo");
		String DeptNo=request.getParameter("DeptNo");
		
		//String opt1=request.getParameter("opt1");
		//String opt2=request.getParameter("opt2");
		//String opt3=request.getParameter("opt3");
		
		String type=request.getParameter("type");
		
		int start_year;
		int end_year;
		try{
			start_year=Integer.parseInt(request.getParameter("start_year"));
		}catch(Exception e){
			start_year=manager.ezGetInt("SELECT start_year FROM StmdGrdeCredit ORDER BY start_year LIMIT 1");
		}
		
		try{
			end_year=Integer.parseInt(request.getParameter("end_year"));
		}catch(Exception e){
			end_year=manager.ezGetInt("SELECT start_year FROM StmdGrdeCredit ORDER BY end_year DESC LIMIT 1");
		}
		
		
		String student_no;		
		int pa;
		Map myCredit;
		Map paCredit;
		int myOpt;
		int paOpt;
		
		int myOpt1;
		int paOpt1;
		
		int x=0;
		List list=new ArrayList();
		//查學分不足的情況
		if(type.equals("not")){
			//如果沒有學制科系的 - 認定為大列表
			if(SchoolNo==null){				
				//如果是列表模式 - 開始結束年已取得
				List result=(List)session.getAttribute("result");
				for(int i=0; i<result.size(); i++){					
					list.addAll(getStudent(start_year+"", end_year+"", 
							(((Map)result.get(i)).get("SchoolNo")).toString(), 
							(((Map)result.get(i)).get("DeptNo")).toString()));										
				}				
			}
			
			
			list.addAll(getStudent(start_year+"", end_year+"", SchoolNo, DeptNo));			
			out.println("<table border='1' width='100%'>  ");
			out.println("<tr bgcolor='#f0fcd7'>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>入學年</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>學號</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");
			
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>應必</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>應選</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>應通</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>應修</td>");
			
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>實必</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>實選</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>實通</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>實修</td>");
			out.println("</tr'>");
			
			
			
			for(int i=0; i<list.size(); i++){					
				student_no=((Map)list.get(i)).get("student_no").toString();
				pa=manager.getPassScore(null, student_no);				
				myCredit=myCredit(student_no, pa);
				paCredit=(Map)((Map)list.get(i)).get("paCredit");
				
				myOpt=Integer.parseInt(myCredit.get("opt").toString());
				paOpt=Integer.parseInt(paCredit.get("opt").toString());
				
				myOpt1=Integer.parseInt(myCredit.get("opt1").toString());
				paOpt1=Integer.parseInt(paCredit.get("opt1").toString());
				if(myOpt<paOpt || myOpt1<paOpt1){
					x=x+1;
					if(x%2==0){
						out.println("<tr bgcolor='#f0fcd7'>");
					}else{
						out.println("<tr>");
					}					
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("entrance")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("ClassName")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+student_no+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("student_name")+"</td>");
					
					
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+paCredit.get("opt1")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+paCredit.get("opt2")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+paCredit.get("opt3")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+paCredit.get("opt")+"</td>");
					
					
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+myCredit.get("opt1")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+myCredit.get("opt2")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+myCredit.get("opt3")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+myCredit.get("opt")+"</td>");
					out.println("</tr'>");
				}
			}
			out.println("</table>");
		}		
		
		if(type.equals("pass")){			
			//如果沒有學制科系的 - 認定為大列表
			if(SchoolNo==null){				
				//如果是列表模式 - 開始結束年已取得
				List result=(List)session.getAttribute("result");
				for(int i=0; i<result.size(); i++){					
					list.addAll(getStudent(start_year+"", end_year+"", 
							(((Map)result.get(i)).get("SchoolNo")).toString(), 
							(((Map)result.get(i)).get("DeptNo")).toString()));										
				}				
			}
			
			
			list.addAll(getStudent(start_year+"", end_year+"", SchoolNo, DeptNo));
			
			
			
			out.println("<table border='1' width='100%'>  ");
			out.println("<tr bgcolor='#f0fcd7'>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>入學年</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>學號</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");
			
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>應必</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>應選</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>應通</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>應修</td>");
			
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>實必</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>實選</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>實通</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>實修</td>");
			out.println("</tr'>");
			
			for(int i=0; i<list.size(); i++){					
				student_no=((Map)list.get(i)).get("student_no").toString();
				pa=manager.getPassScore(null, student_no);				
				myCredit=myCredit(student_no, pa);
				paCredit=(Map)((Map)list.get(i)).get("paCredit");
				
				myOpt=Integer.parseInt(myCredit.get("opt").toString());
				paOpt=Integer.parseInt(paCredit.get("opt").toString());
				
				myOpt1=Integer.parseInt(myCredit.get("opt1").toString());
				paOpt1=Integer.parseInt(paCredit.get("opt1").toString());
				
				if(myOpt>=paOpt && myOpt1>=paOpt1){
					x=x+1;
					if(x%2==0){
						out.println("<tr bgcolor='#f0fcd7'>");
					}else{
						out.println("<tr>");
					}
					
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("entrance")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("ClassName")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+student_no+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("student_name")+"</td>");					
					
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+paCredit.get("opt1")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+paCredit.get("opt2")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+paCredit.get("opt3")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+paCredit.get("opt")+"</td>");
					
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+myCredit.get("opt1")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+myCredit.get("opt2")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+myCredit.get("opt3")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+myCredit.get("opt")+"</td>");
					out.println("</tr'>");
				}
			}
			out.println("</table>");
		}
		
		if(type.equals("book")){
			
			if(SchoolNo==null){				
				//如果是列表模式 - 開始結束年已取得
				List result=(List)session.getAttribute("result");
				for(int i=0; i<result.size(); i++){					
					list.addAll(getStudent(start_year+"", end_year+"", 
							(((Map)result.get(i)).get("SchoolNo")).toString(), 
							(((Map)result.get(i)).get("DeptNo")).toString()));										
				}				
			}
			
			
			list.addAll(getStudent(start_year+"", end_year+"", SchoolNo, DeptNo));
			
			out.println("<table border='1' width='100%'>  ");
			out.println("<tr bgcolor='#f0fcd7'>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>入學年</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>學號</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");
			
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>被當學期</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>被當科目代碼</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>被當科目名稱</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>選別</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>學分</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>被當分數</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>重修</td>");
			out.println("</tr'>");
			List book;			
			
			for(int i=0; i<list.size(); i++){					
				student_no=((Map)list.get(i)).get("student_no").toString();
				pa=manager.getPassScore(null, student_no);				
				myCredit=myCredit(student_no, pa);
				paCredit=(Map)((Map)list.get(i)).get("paCredit");
				
				myOpt=Integer.parseInt(myCredit.get("opt").toString());
				paOpt=Integer.parseInt(paCredit.get("opt").toString());
				
				myOpt1=Integer.parseInt(myCredit.get("opt1").toString());
				paOpt1=Integer.parseInt(paCredit.get("opt1").toString());
				
				if(myOpt<paOpt || myOpt1<paOpt1){
					
					
					book=manager.ezGetBy("SELECT s.*, c.chi_name FROM ScoreHist s, Csno c WHERE s.cscode=c.cscode AND s.student_no='"+student_no+"' AND score<"+pa);
					
					for(int j=0; j<book.size(); j++){
						x=x+1;
						if(x%2==0){
							out.println("<tr bgcolor='#f0fcd7'>");
						}else{
							out.println("<tr>");
						}
						
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("entrance")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("ClassName")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+student_no+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)list.get(i)).get("student_name")+"</td>");
						
						
						
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)book.get(j)).get("school_year")+"-"+((Map)book.get(j)).get("school_term")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)book.get(j)).get("cscode")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap align='left'>"+((Map)book.get(j)).get("chi_name")+"</td>");
						
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)book.get(j)).get("opt")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)book.get(j)).get("credit")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)book.get(j)).get("score")+"</td>");
						
						
						if(manager.ezGetInt("SELECT COUNT(*)FROM ScoreHist s, Csno c WHERE c.cscode=s.cscode AND s.student_no='"+student_no+"' AND (s.cscode='"+((Map)book.get(j)).get("cscode")+"'OR c.chi_name LIKE'"+((Map)book.get(j)).get("chi_name")+"%') AND score>="+pa)>0){
							out.println("<td align='center' style='mso-number-format:\\@' nowrap>已重修</td>");
						}else{
							out.println("<td align='center' style='mso-number-format:\\@' nowrap></td>");
						}
						
						out.println("</tr'>");
					}
					
					
					
					
					
										
				}
			}
			out.println("</table>");
			out.close();
			
		}
		out.flush();
		out.close();
	}
	
	private List getStudent(String start_year, String end_year, String SchoolNo, String DeptNo){
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		List list=new ArrayList();
		Map map;
		int s=Integer.parseInt(start_year);
		int e=Integer.parseInt(end_year);
		Map pa;
		List tmp;
		for(int i=s; i<=e; i++){
			
			map=manager.ezGetMap("SELeCT * FROM StmdGrdeCredit WHERE start_year>="+i+" AND end_year<="+e+" AND DeptNo='"+DeptNo+"' AND SchoolNo='"+SchoolNo+"' LIMIT 1");
			if(map!=null){
				pa=pa(map);
				tmp=manager.ezGetBy("SELeCT * FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND " +
						"c.SchoolNo='"+map.get("SchoolNo")+"' "+
						"AND c.DeptNo='" +map.get("DeptNo")+"' "+
						"AND s.entrance LIKE '"+i+"%' ORDER BY c.SchoolNo, c.DeptNo");
				for(int j=0; j<tmp.size(); j++){
					((Map)tmp.get(j)).put("paCredit", pa);					
				}
				list.addAll(tmp);
			}
		}		
		return list;
	}
	
	//取得應修學分數
	private Map pa(Map map){		
		int x=0;		
		try{
			x=Integer.parseInt(map.get("opt1").toString())+
			Integer.parseInt(map.get("opt2").toString())+
			Integer.parseInt(map.get("opt3").toString());			
			map.put("opt", x);
			return map;
		}catch(Exception e){
			map.put("opt", x);
			return map;
		}		
	}
	
	private Map myCredit(String student_no, int pa){
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		Map map=new HashMap();
		map.put("opt1", manager.ezGetInt("SELeCT SUM(credit) FROM ScoreHist WHERE student_no='"+student_no+"' AND opt='1' AND score>="+pa));
		map.put("opt2", manager.ezGetInt("SELeCT SUM(credit) FROM ScoreHist WHERE student_no='"+student_no+"' AND opt='2' AND score>="+pa));
		map.put("opt3", manager.ezGetInt("SELeCT SUM(credit) FROM ScoreHist WHERE student_no='"+student_no+"' AND opt='3' AND score>="+pa));
		map.put("opt", manager.ezGetInt("SELeCT SUM(credit) FROM ScoreHist WHERE student_no='"+student_no+"' AND score>="+pa));		
		return map;
	}

}
