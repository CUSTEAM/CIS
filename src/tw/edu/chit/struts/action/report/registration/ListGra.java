package tw.edu.chit.struts.action.report.registration;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

public class ListGra extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		HttpSession session = request.getSession(false);
		
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=List4Student.xls");
		CourseManager manager = (CourseManager) getBean("courseManager");
		//HttpSession session = request.getSession(false);
		PrintWriter out=response.getWriter();
		
		
		List list=(List) session.getAttribute("students");
		
		
		
		
		out.println("<table border='1'>");
		out.println("  <tr>");
		out.println("    <td>班級</td>");
		out.println("    <td>學號</td>");
		out.println("    <td>姓名</td>");
		out.println("    <td>電話</td>");
		out.println("    <td>手機</td>");
		out.println("    <td>戶籍地址</td>");
		out.println("    <td>通訊地址</td>");
		out.println("    <td>e-mail</td>");
		out.println("    <td>狀態</td>");
		out.println("    <td>後續入學</td>");
		out.println("  </tr>");
		
		Map map;
		Map map1;
		List hist;
		for(int i=0; i<list.size(); i++){
			map=new HashMap();
			try {
				map = (Map) manager
						.ezGetBy(
								"SELECT s.*, c.ClassName, c5.name, c51.name as team, c3.name as county, "
										+ "c31.name as province, c52.name as status_name, c53.name as caurse_name, "
										+ "c54.name as ident_name2 FROM "
										+ "(((((((stmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo)LEFT OUTER JOIN "
										+ "code5 c5 ON c5.idno=s.ident AND c5.category='Identity')LEFT OUTER JOIN "
										+ "code5 c51 ON c51.idno=s.divi AND c51.category='GROUP')LEFT OUTER JOIN "
										+ "code3 c3 ON s.birth_county=c3.no)LEFT OUTER JOIN "
										+ "code3 c31 ON c31.no=s.birth_province)LEFT OUTER JOIN "
										+ "code5 c52 ON s.occur_status=c52.idno AND c52.category='Status')LEFT OUTER JOIN "
										+ "code5 c53 ON s.occur_cause=c53.idno AND c53.category='Cause')LEFT OUTER JOIN "
										+ "code5 c54 ON s.ident_basic=c54.idno AND c53.category='Identity' WHERE student_no='"
										+ ((Map) list.get(i))
												.get("student_no") + "'")
						.get(0);
			} catch (Exception e) {
				map = (Map) manager
						.ezGetBy(
								"SELECT s.*, c.ClassName, c5.name, c51.name as team, c3.name as county, "
										+ "c31.name as province, c52.name as status_name, c53.name as caurse_name, "
										+ "c54.name as ident_name2 FROM "
										+ "(((((((Gstmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo)LEFT OUTER JOIN "
										+ "code5 c5 ON c5.idno=s.ident AND c5.category='Identity')LEFT OUTER JOIN "
										+ "code5 c51 ON c51.idno=s.divi AND c51.category='GROUP')LEFT OUTER JOIN "
										+ "code3 c3 ON s.birth_county=c3.no)LEFT OUTER JOIN "
										+ "code3 c31 ON c31.no=s.birth_province)LEFT OUTER JOIN "
										+ "code5 c52 ON s.occur_status=c52.idno AND c52.category='Status')LEFT OUTER JOIN "
										+ "code5 c53 ON s.occur_cause=c53.idno AND c53.category='Cause')LEFT OUTER JOIN "
										+ "code5 c54 ON s.ident_basic=c54.idno AND c53.category='Identity' WHERE student_no='"
										+ ((Map) list.get(i))
												.get("student_no") + "'")
						.get(0);
			}
			
			out.println("  <tr>");
			out.println("    <td>"+map.get("ClassName")+"</td>");
			out.println("    <td>"+map.get("student_no")+"</td>");
			out.println("    <td>"+map.get("student_name")+"</td>");
			out.println("    <td>"+map.get("telephone")+"</td>");
			out.println("    <td>"+map.get("CellPhone")+"</td>");
			out.println("    <td>"+map.get("perm_post")+", "+map.get("perm_addr")+"</td>");
			out.println("    <td>"+map.get("curr_post")+", "+map.get("curr_addr")+"</td>");
			out.println("    <td>"+map.get("Email")+"</td>");
			out.println("    <td>"+map.get("status_name")+", "+map.get("caurse_name")+"</td>");
			
			
			hist=manager.ezGetBy("SELECT s.*, c.ClassName, c5.name, c51.name as team, c3.name as county, "
							+ "c31.name as province, c52.name as status_name, c53.name as caurse_name, "
							+ "c54.name as ident_name2 FROM "
							+ "(((((((stmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo)LEFT OUTER JOIN "
							+ "code5 c5 ON c5.idno=s.ident AND c5.category='Identity')LEFT OUTER JOIN "
							+ "code5 c51 ON c51.idno=s.divi AND c51.category='GROUP')LEFT OUTER JOIN "
							+ "code3 c3 ON s.birth_county=c3.no)LEFT OUTER JOIN "
							+ "code3 c31 ON c31.no=s.birth_province)LEFT OUTER JOIN "
							+ "code5 c52 ON s.occur_status=c52.idno AND c52.category='Status')LEFT OUTER JOIN "
							+ "code5 c53 ON s.occur_cause=c53.idno AND c53.category='Cause')LEFT OUTER JOIN "
							+ "code5 c54 ON s.ident_basic=c54.idno AND c53.category='Identity' WHERE s.idno='"
							+ map.get("idno") + "' AND s.depart_class!='"+map.get("depart_class")+"'");
			
			hist.addAll(manager.ezGetBy("SELECT s.*, c.ClassName, c5.name, c51.name as team, c3.name as county, "
									+ "c31.name as province, c52.name as status_name, c53.name as caurse_name, "
									+ "c54.name as ident_name2 FROM "
									+ "(((((((Gstmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo)LEFT OUTER JOIN "
									+ "code5 c5 ON c5.idno=s.ident AND c5.category='Identity')LEFT OUTER JOIN "
									+ "code5 c51 ON c51.idno=s.divi AND c51.category='GROUP')LEFT OUTER JOIN "
									+ "code3 c3 ON s.birth_county=c3.no)LEFT OUTER JOIN "
									+ "code3 c31 ON c31.no=s.birth_province)LEFT OUTER JOIN "
									+ "code5 c52 ON s.occur_status=c52.idno AND c52.category='Status')LEFT OUTER JOIN "
									+ "code5 c53 ON s.occur_cause=c53.idno AND c53.category='Cause')LEFT OUTER JOIN "
									+ "code5 c54 ON s.ident_basic=c54.idno AND c53.category='Identity' WHERE s.idno='"
									+ map.get("idno") + "' AND s.depart_class!='"+map.get("depart_class")+"'"));
			
			out.println("    <td>");
			
			if(hist.size()>0){
				for(int j=0; j<hist.size(); j++){
					map1=((Map)hist.get(j));
					out.println(   map1.get("ClassName")+", "  +map1.get("occur_year")+", "+ map1.get("status_name")+", "+map1.get("caurse_name") );
					
				}
			}
			
			
			out.println("    </td>");
			
			out.println("  </tr>");
			
		}
		
		
		
		out.println("</table>");
		
		return null;
	}

}
