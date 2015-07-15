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
/**
 * 休退比例統計表
 * @author JOHN
 */
public class LeaveAvg extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=List4Course35.xls");
		HttpSession session = request.getSession(false);
		
		//int x=manager.getSchoolYear()-2;		
		
		PrintWriter out=response.getWriter();
		//List idiots;
		
		//取頁面學年學期
		String occur_year="";
		String occur_term="";
		if(!session.getAttribute("occur_year").equals("")){
			occur_year=session.getAttribute("occur_year").toString();
		}else{
			occur_year=manager.getSchoolYear().toString();
		}
		
		if(!session.getAttribute("occur_term").equals("")){
			occur_term=session.getAttribute("occur_term").toString();
		}
		//取校區
		String ClassNo=String.valueOf(session.getAttribute("classNo"));
		if(ClassNo.trim().equals("")){
			ClassNo="164";
		}
		String CampusNo=ClassNo.substring(0, 1);
		//取列表出來的系所		
		List list=manager.ezGetBy("SELECT c.ClassNo, c51.name as schoolName, c5.name as deptName, " +
        		"c.* FROM Class c, code5 c5, code5 c51 WHERE c51.idno=c.SchoolNo AND c51.category='School' AND " +
        		"c5.idno=c.DeptNo AND c5.category='Dept' AND c.ClassNo LIKE '"+ClassNo+"%' AND c.deptNo<>'0'" +
        		"GROUP BY c.schoolNo, c.DeptNo ORDER BY c.SchoolNo, c.DeptNo");        
        Map map;
        List students=new ArrayList();
        int grade;
        String school;
        
        String className="";
        String classNo="";
        
        for(int i=0; i<list.size(); i++){
        	school=((Map)list.get(i)).get("SchoolNo").toString().substring(1, 2).toString();
        	if(school.equals("G")){
        		school="2";
        	}
        	grade=Integer.parseInt(school);
        	for(int j=1; j<=grade+2; j++){
        		map=new HashMap();
        		
        		map.put("schoolName", ((Map)list.get(i)).get("schoolName"));
        		
        		try{
        			classNo=((Map)list.get(i)).get("ClassNo").toString();
        			className=manager.ezGetString("SELECT fname FROM dept WHERE no LIKE '"+classNo.substring(0 , 4)+"'");
        		}catch(Exception e){
        			e.printStackTrace();
        			className=((Map)list.get(i)).get("deptName").toString();
        		}
        		
        		if(j>grade){
        			map.put("deptName", className+"延修"+manager.numtochinese(String.valueOf(j-grade), true));
        		}else{
        			map.put("deptName", className+manager.numtochinese(String.valueOf(j), true)+"年級");
        		}
            	        	
            	map.put("DeptNo", ((Map)list.get(i)).get("DeptNo"));
            	map.put("SchoolNo", ((Map)list.get(i)).get("SchoolNo"));
            	map.put("grade", j);
     
            	students.add(map);
        	}
        	
        }
        out.println("<style type='text/css'>td{FONT-SIZE: 9px;}</style>");
		out.println("			<table border='1'>");
		
		out.println("				<tr>");
		out.println("					<td colspan='2'></td>");
		out.println("					<td colspan='13'>退學人數</td>");
		out.println("					<td colspan='9'>休學人數</td>");
		out.println("				</tr>");
		out.println("				<tr>");
		out.println("					<td colspan='2'></td>");
		
		out.println("					<td colspan='3'>總計</td>");
		out.println("					<td colspan='2'>學業</td>");
		out.println("					<td colspan='2'>操行</td>");
		out.println("					<td colspan='2'>志趣</td>");
		out.println("					<td colspan='2'>經濟</td>");
		out.println("					<td colspan='2'>其它</td>");
		
		out.println("					<td colspan='3'>總計</td>");
		out.println("					<td colspan='2'>因病</td>");
		out.println("					<td colspan='2'>經濟</td>");
		out.println("					<td colspan='2'>其它</td>");
		
		out.println("				</tr>");
		
		out.println("				<tr>");
		out.println("					<td align='center'>學制</td>");
		out.println("					<td align='center'>系所</td>");
		
		out.println("					<td align='center'>總</td>");
		out.println("					<td align='center'>男</td>");
		out.println("					<td align='center'>女</td>");					
		
		out.println("					<td align='center'>男</td>");
		out.println("					<td align='center'>女</td>");
		
		out.println("					<td align='center'>男</td>");
		out.println("					<td align='center'>女</td>");
		
		out.println("					<td align='center'>男</td>");
		out.println("					<td align='center'>女</td>");
		
		out.println("					<td align='center'>男</td>");
		out.println("					<td align='center'>女</td>");
		
		out.println("					<td align='center'>男</td>");
		out.println("					<td align='center'>女</td>");
		
		//休學
		out.println("					<td align='center'>總</td>");
		out.println("					<td align='center'>男</td>");
		out.println("					<td align='center'>女</td>");		
		
		out.println("					<td align='center'>男</td>");
		out.println("					<td align='center'>女</td>");
		
		out.println("					<td align='center'>男</td>");
		out.println("					<td align='center'>女</td>");
		
		out.println("					<td align='center'>男</td>");
		out.println("					<td align='center'>女</td>");
		
		out.println("				</tr>");			
		
		
		for(int i=0; i<students.size(); i++){
			
			String SchoolNo=((Map)students.get(i)).get("SchoolNo").toString();
			String DeptNo=((Map)students.get(i)).get("DeptNo").toString();
			String agrade=((Map)students.get(i)).get("grade").toString();
			//System.out.println(SchoolNo+", "+DeptNo);
			
			int jm1=countStu(CampusNo, SchoolNo, DeptNo, "'001', '007', '010'", "2", "1",  occur_year, occur_term, agrade);
			int jf1=countStu(CampusNo, SchoolNo, DeptNo, "'001', '007', '010'", "2", "2", occur_year, occur_term, agrade);
			
			int jm2=countStu(CampusNo, SchoolNo, DeptNo, "'011', '012', '013', '014'", "2", "1",  occur_year, occur_term, agrade);
			int jf2=countStu(CampusNo, SchoolNo, DeptNo, "'011', '012', '013', '014'", "2", "2",  occur_year, occur_term, agrade);
			
			int jm3=countStu(CampusNo, SchoolNo, DeptNo, "'003', '016', '017', '018', '019'", "2", "1",  occur_year, occur_term, agrade);
			int jf3=countStu(CampusNo, SchoolNo, DeptNo, "'003', '016', '017', '018', '019'", "2", "2",  occur_year, occur_term, agrade);
			
			//int jm4=countStu(SchoolNo, DeptNo, "025", "2", "1",  occur_year, occur_term);
			//int jf4=countStu(SchoolNo, DeptNo, "025", "2", "2",  occur_year, occur_term);
			
			int jm5=countStuOther(CampusNo, SchoolNo, DeptNo, "'001', '007', '010', '014', '011', '012', '013', '003', '016', '017', '018', '019'", "2", "1", occur_year, occur_term, agrade);
			int jf5=countStuOther(CampusNo, SchoolNo, DeptNo, "'001', '007', '010', '014', '011', '012', '013', '003', '016', '017', '018', '019'", "2", "2", occur_year, occur_term, agrade);
			
			//int jmTotal=jm1+jm2+jm3+jm4+jm5;
			//int jfTotal=jf1+jf2+jf3+jf4+jf5;
			
			int jmTotal=jm1+jm2+jm3+jm5;
			int jfTotal=jf1+jf2+jf3+jf5;
			
			int rm1=countStu(CampusNo, SchoolNo, DeptNo, "022", "1", "1",  occur_year, occur_term, agrade);
			int rf1=countStu(CampusNo, SchoolNo, DeptNo, "022", "1", "2",  occur_year, occur_term, agrade);
			
			int rm2=countStu(CampusNo, SchoolNo, DeptNo, "025", "1", "1",  occur_year, occur_term, agrade);
			int rf2=countStu(CampusNo, SchoolNo, DeptNo, "025", "1", "2",  occur_year, occur_term, agrade);
			
			int rm3=countStuOther(CampusNo, SchoolNo, DeptNo, "'025', '022'", "1", "1", occur_year, occur_term, agrade);
			int rf3=countStuOther(CampusNo, SchoolNo, DeptNo, "'025', '022'", "1", "2", occur_year, occur_term, agrade);
			
			int rmTotal=rm1+rm2+rm3;
			int rfTotal=rf1+rf2+rf3;
			
			out.println("				<tr>");
			out.println("					<td align='left'>"+((Map)students.get(i)).get("schoolName")+"</td>");
			out.println("					<td align='left'>"+((Map)students.get(i)).get("deptName")+"</td>");
			
			out.println("					<td align='center'>"+(jmTotal+jfTotal)+"</td>");
			out.println("					<td align='center'>"+jmTotal+"</td>");
			out.println("					<td align='center'>"+jfTotal+"</td>");
			
			out.println("					<td align='center'>"+jm1+"</td>");
			out.println("					<td align='center'>"+jf1+"</td>");
			
			out.println("					<td align='center'>"+jm2+"</td>");
			out.println("					<td align='center'>"+jf2+"</td>");
			
			out.println("					<td align='center'>"+jm3+"</td>");
			out.println("					<td align='center'>"+jf3+"</td>");
			
			out.println("					<td align='center'>-</td>");
			out.println("					<td align='center'>-</td>");
			
			out.println("					<td align='center'>"+jm5+"</td>");
			out.println("					<td align='center'>"+jf5+"</td>");
			
			//休學
			out.println("					<td align='center'>"+(rmTotal+rfTotal)+"</td>");
			out.println("					<td align='center'>"+rmTotal+"</td>");
			out.println("					<td align='center'>"+rfTotal+"</td>");
						
			out.println("					<td align='center'>"+rm1+"</td>");
			out.println("					<td align='center'>"+rf1+"</td>");
			
			out.println("					<td align='center'>"+rm2+"</td>");
			out.println("					<td align='center'>"+rf2+"</td>");
			
			out.println("					<td align='center'>"+rm3+"</td>");
			out.println("					<td align='center'>"+rf3+"</td>");
			
			out.println("				</tr>");
			
			
			
			
			
		}
		out.println("			</table>");
		out.close();
		
	}
	
	/**
	 * 抓人數
	 * @param schoolNo
	 * @param deptNo
	 * @param course
	 * @param status
	 * @param sex
	 * @param year
	 * @param term
	 * @return
	 */
	private int countStu(String CampusNo, String schoolNo, String deptNo, String cause, String status, String sex, String year, String term, String grade){
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");		
		
		return manager.ezGetInt("SELECT DISTINCT COUNT(*)FROM Gstmd s, Class c " +
				"WHERE " +
				"s.depart_class=c.ClassNo AND " +
				"c.SchoolNo='"+schoolNo+"' AND " +
				"c.DeptNo='"+deptNo+"' AND " +
				"c.CampusNo='"+CampusNo+"' AND " +
				"c.Grade='"+grade+"' AND " +
				"s.occur_cause IN("+cause+") AND " +
				"s.sex='"+sex+"' AND " +
				"s.occur_status='"+status+"' AND " +				
				"s.occur_year='"+year+"' AND " +
				"s.occur_term LIKE'"+term+"%'");
	}
	
	/**
	 * 抓其它
	 * @param schoolNo
	 * @param deptNo
	 * @param course
	 * @param status
	 * @param sex
	 * @param year
	 * @param term
	 * @return
	 */
	private int countStuOther(String CampusNo, String schoolNo, String deptNo, String cause, String status, String sex, String year, String term, String grade){
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		return manager.ezGetInt("SELECT DISTINCT COUNT(*)FROM Gstmd s, Class c " +
				"WHERE " +
				"s.depart_class=c.ClassNo AND " +
				"c.SchoolNo='"+schoolNo+"' AND " +
				"c.DeptNo='"+deptNo+"' AND " +
				"c.Grade='"+grade+"' AND " +
				"c.CampusNo='"+CampusNo+"' AND " +
				"s.occur_cause NOT IN ("+cause+") AND " +//原因不為5種且空白
				"s.sex='"+sex+"' AND " +
				"s.occur_status='"+status+"' AND " +				
				"s.occur_year='"+year+"' AND " +
				"s.occur_term LIKE'"+term+"%'");
	}
}
