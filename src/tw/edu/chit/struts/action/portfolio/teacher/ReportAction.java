package tw.edu.chit.struts.action.portfolio.teacher;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class ReportAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		request.setAttribute("AllCampus", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Campus'"));
		request.setAttribute("AllSchool", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='School'"));
		request.setAttribute("AllDept", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Dept'"));
		request.setAttribute("AllSchoolType", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Stype'"));
		
		
		setContentPage(request.getSession(false), "portfolio/teacher/Report.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		//UserCredential c = (UserCredential) session.getAttribute("Credential");
		//String Uid=c.getMember().getAccount();
		
		
		DynaActionForm f = (DynaActionForm) form;
		String type=f.getString("type");		
		String classes=f.getString("CampusNo")+f.getString("SchoolNo")+
		f.getString("DeptNo")+f.getString("Grade")+f.getString("ClassNo");
		
		response.setContentType("text/html; charset=big5");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=List4Student.xls");
		PrintWriter out=response.getWriter();
		
		List list;
		if(type.equals("1")){
			list=manager.ezGetBy("SELECT c.ClassName, w.count, s.student_no, s.student_name, u.Oid as eps_oid, v.Oid as v_oid, " +
			"(SELECT COUNT(*)FROM Eps_feedback WHERE author=s.student_no)as act, " +
			"(SELECT COUNT(*)FROM Eps_pages WHERE Uid=s.student_no)as ppt," +
			"(SELECT COUNT(*)FROM Eps_content WHERE Uid=s.student_no)as pct " +
			"FROM Class c, wwpass w, (stmd s LEFT OUTER JOIN Eps_user u ON s.student_no=u.Uid)LEFT " +
			"OUTER JOIN Eps_vitae v ON v.student_no=s.student_no WHERE c.ClassNo=s.depart_class AND w.username=s.student_no " +
			"AND s.depart_class LIKE '"+classes+"%' ORDER BY s.depart_class, s.student_no");
			out.println("<table border='1'>");
			out.println("  <tr>");
			out.println("    <td>學號</td>");
			out.println("    <td>姓名</td>");
			out.println("    <td>班級</td>");			
			out.println("    <td>建置</td>");
			out.println("    <td>登入</td>");
			out.println("    <td>文章</td>");
			out.println("    <td>歷程</td>");
			out.println("    <td>屨歷</td>");
			out.println("    <td>互動</td>");
			out.println("  </tr>");
			for(int i=0; i<list.size(); i++){
				
				out.println("  <tr>");
				out.println("    <td>"+((Map)list.get(i)).get("student_no")+"</td>");
				out.println("    <td>"+((Map)list.get(i)).get("student_name")+"</td>");
				out.println("    <td>"+((Map)list.get(i)).get("ClassName")+"</td>");
				
				if(((Map)list.get(i)).get("eps_oid")!=null){
					out.println("    <td>是</td>");
				}else{
					out.println("    <td></td>");
				}
				
				
				out.println("    <td>"+((Map)list.get(i)).get("count")+"</td>");
				out.println("    <td>"+((Map)list.get(i)).get("ppt")+"</td>");
				out.println("    <td>"+((Map)list.get(i)).get("pct")+"</td>");
				if(((Map)list.get(i)).get("v_oid")!=null){
					out.println("    <td>是</td>");
				}else{
					out.println("    <td></td>");
				}
				out.println("    <td>"+((Map)list.get(i)).get("act")+"</td>");
				out.println("  </tr>");
				
				
			}
			
			out.println("</table>");
			return null;
		}
		
		if(type.equals("2")){
			List tmp=manager.ezGetBy("SELECT COUNT(*)FROM Class WHERE ClassNo LIKE '"+classes+"%' GROUP BY DeptNo");
			String dept="%";
			System.out.println("SELECT DeptNo FROM Class WHERE ClassNo LIKE'"+classes+"%'");
			if(tmp.size()==1){
				dept=manager.ezGetString("SELECT DeptNo FROM Class WHERE ClassNo LIKE'"+classes+"%' LIMIT 1");
			}
			//
			//if(dept==null){
				//dept="%";
			//}
			list=manager.ezGetBy("SELECT e.category, w.count, e.cname, c.name, u.Oid as eps_oid, " +
					"(SELECT COUNT(*)FROM Eps_pages WHERE Uid=e.idno)as ppt, " +
					"(SELECT COUNT(*)FROM Eps_content WHERE Uid=e.idno)as pct, " +
					"(SELECT COUNT(*)FROM Eps_feedback WHERE author=e.idno)as act " +
					"FROM wwpass w, (empl e LEFT OUTER JOIN CodeEmpl c ON e.unit=c.idno)LEFT " +
					"OUTER JOIN Eps_user u ON u.Uid=e.idno WHERE w.username=e.idno AND " +
					"(e.category='1'OR e.category='2') AND c.idno2 LIKE'"+dept+"%' ORDER BY e.unit");
			
			out.println("<table border='1'>");
			out.println("  <tr>");
			out.println("    <td>姓名</td>");
			out.println("    <td>專兼任</td>");
			out.println("    <td>單位</td>");			
			out.println("    <td>建置</td>");
			out.println("    <td>登入</td>");
			out.println("    <td>文章</td>");
			out.println("    <td>歷程</td>");
			out.println("    <td>互動</td>");
			out.println("  </tr>");
			for(int i=0; i<list.size(); i++){
				
				out.println("  <tr>");
				out.println("    <td>"+((Map)list.get(i)).get("cname")+"</td>");
				if(((Map)list.get(i)).get("category").equals("1")){
					out.println("    <td>專</td>");
				}else{
					out.println("    <td>兼</td>");
				}
				out.println("    <td>"+((Map)list.get(i)).get("name")+"</td>");			
				
				
				if(((Map)list.get(i)).get("eps_oid")!=null){
					out.println("    <td>是</td>");
				}else{
					out.println("    <td></td>");
				}
				
				
				
				out.println("    <td>"+((Map)list.get(i)).get("count")+"</td>");
				out.println("    <td>"+((Map)list.get(i)).get("ppt")+"</td>");
				out.println("    <td>"+((Map)list.get(i)).get("pct")+"</td>");
				out.println("    <td>"+((Map)list.get(i)).get("count")+"</td>");
				out.println("  </tr>");
				
				
			}
			
			out.println("</table>");
			
			return null;
		}
		
		if(type.equals("3")){			
			out.println("<table border='1'>");
			out.println("  <tr>");
			out.println("    <td>學號</td>");
			out.println("    <td>姓名</td>");
			out.println("    <td>班級</td>");			
			out.println("    <td>總次數</td>");
			out.println("    <td>UCAN登入</td>");
			out.println("    <td>UCAN輔導</td>");
			out.println("    <td>查詢核心能力指標</td>");
			out.println("    <td>觀看教師檔案次數</td>");
			out.println("    <td>接受輔導次數</td>");
			out.println("    <td>office hour輔導次數</td>");
			
			out.println("  </tr>");
			
			out.println("</table>");
			
			return null;
		}
		
		if(type.equals("4")){			
			out.println("<table border='1'>");
			out.println("  <tr>");
			out.println("    <td>姓名</td>");
			out.println("    <td>單位</td>");			
			out.println("    <td>總次數</td>");
			out.println("    <td>UCAN登入</td>");
			out.println("    <td>觀看學生檔案次數</td>");
			out.println("    <td>輔導次數</td>");
			out.println("    <td>office hour輔導次數</td>");			
			out.println("  </tr>");
			
			out.println("</table>");
			
			return null;
		}
		
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		return map;
	}

}
