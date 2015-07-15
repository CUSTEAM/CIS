package tw.edu.chit.struts.action.registration;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class CountCreditAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		//session.setAttribute("allDept", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='Dept' AND idno <>'0' ORDER BY sequence"));
		
		setContentPage(request.getSession(false), "registration/CountCredit.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 查詢
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
	DynaActionForm cForm = (DynaActionForm) form;
	//String ClassNo=cForm.getString("ClassNo");
	//String ClassName=cForm.getString("ClassName");
	String type=cForm.getString("type");
	String classLess=cForm.getString("classLess");
	String year=cForm.getString("year");
	//String ShortName=cForm.getString("ShortName");
	
	
	//HttpSession session = request.getSession(false);
	
	response.setContentType("text/html; charset=UTF-8");
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition", "attachment;filename=List4Student.xls");
	CourseManager manager = (CourseManager) getBean("courseManager");
	String sterm=manager.getSchoolTerm().toString();
	PrintWriter out=response.getWriter();
	
	
	
	//畢業生
	List list;
	Map map;
	//List list1;
	
	if(type.equals("1")){
		
		list=manager.ezGetBy("SELECT s.student_no, s.student_name FROM Gstmd s WHERE " +
				"s.occur_status='6' AND s.occur_year='"+year+"' AND s.depart_class LIKE'"+classLess+"%'");
		
		
		
		
		
		for(int i=0; i<list.size(); i++){
			map=manager.ezGetMap("SELECT SUM(h.score) as score, SUM(h.credit) as credit, (SUM(h.score)/SUM(h.credit))as total FROM " +
					"ScoreHist h WHERE h.student_no='"+((Map)list.get(i)).get("student_no")+"'");
			
			
			((Map)list.get(i)).put("score", map.get("score"));
			((Map)list.get(i)).put("credit", map.get("credit"));
			((Map)list.get(i)).put("total", map.get("total"));
		}
		
		
	}else{
		
		
		
		
		list=manager.ezGetBy("SELECT s.student_no, s.student_name FROM stmd s WHERE " +
				"s.occur_status='6' AND s.occur_year='"+year+"' AND s.depart_class LIKE'"+classLess+"%'");
		
		
		
		
		
		for(int i=0; i<list.size(); i++){
			map=manager.ezGetMap("SELECT SUM(h.score) as score, SUM(h.credit) as credit, (SUM(h.score)/SUM(h.credit))as total FROM " +
					"ScoreHist h WHERE h.student_no='"+((Map)list.get(i)).get("student_no")+"'");
			
			
			((Map)list.get(i)).put("score", map.get("score"));
			((Map)list.get(i)).put("credit", map.get("credit"));
			((Map)list.get(i)).put("total", map.get("total"));
		}
		
		
	}
	
	
	list=manager.sortListByKey(list, "total");
	
	
	
	
	
	out.println("<style>td{font-size:18px;}</style>");
	
	out.println("<table border='1'>");	
	out.println("  <tr>");
	out.println("    <td>學年</td>");
	out.println("    <td>學號</td>");
	out.println("    <td>姓名</td>");
	out.println("    <td>學分數</td>");
	out.println("    <td>成績</td>");
	out.println("    <td>平均</td>");
	out.println("  </tr>");
	for(int i=0; i<list.size(); i++){
		out.println("  <tr>");
		out.println("    <td>"+year+"</td>");
		out.println("    <td>"+((Map)list.get(i)).get("student_no")+"</td>");
		out.println("    <td>"+((Map)list.get(i)).get("student_name")+"</td>");
		out.println("    <td>"+((Map)list.get(i)).get("credit")+"</td>");
		out.println("    <td>"+((Map)list.get(i)).get("score")+"</td>");
		out.println("    <td>"+((Map)list.get(i)).get("total")+"</td>");
		out.println("  </tr>");
	}
	
	out.println("  </table>");
	
	//<p style="page-break-before:always;"></p>
	return null;

	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		return map;
	}
}