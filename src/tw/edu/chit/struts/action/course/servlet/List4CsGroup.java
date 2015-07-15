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

/**
 * 學程報表
 * @author JOHN
 *
 */
public class List4CsGroup extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;Filename=Excel.xls");
		
		PrintWriter out=response.getWriter();
		String type=request.getParameter("type");
		
		//學程清單
		if(type.equals("group")){
			List allGroup=(List)session.getAttribute("allGroup");
			out.println("<table border='1'>");
			
			out.println("<tr>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>學程中文名稱</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>學程英文名稱</td>");			
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後適用入學年</td>");
			out.println("	</tr>");
			for(int i=0; i<allGroup.size(); i++){
				if(i%2==0){
					out.println("<tr bgcolor='#f0fcd7'>");
				}else{
					out.println("<tr>");
				}
				
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)allGroup.get(i)).get("cname")+"</td>");
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)allGroup.get(i)).get("ename")+"</td>");			
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)allGroup.get(i)).get("entrance")+"</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
		}
		
		
		
		
		//課程報表
		if(type.equals("groupSet")){
			List aGroupSet=(List)session.getAttribute("aGroupSet");
			Map aGroup=(Map)session.getAttribute("aGroup");
			
			out.println("<table border='1'>");
			/*
			out.println("<tr>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>系所</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>課程代碼</td>");			
			out.println("<td align='center' style='mso-number-format:\\@' width='100%' nowrap>課程名稱</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>選別</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>學分</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>適用學年</td>");
			out.println("	</tr>");
			*/
			for(int i=0; i<aGroupSet.size(); i++){
				
				if(i%42==0){
					out.println("<tr>");
					out.println("<td colspan='6' align='center' style='mso-number-format:\\@' nowrap><font face='標楷體'>"+aGroup.get("cname")+"跨領域學程</font></td>");
					out.println("	</tr>");
					out.println("<tr>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>系所</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>課程代碼</td>");			
					out.println("<td align='center' style='mso-number-format:\\@' width='100%' nowrap>課程名稱</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>選別</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>學分</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>適用學年</td>");
					out.println("	</tr>");
				}
				
				
				if(i%2==0){
					out.println("<tr bgcolor='#f0fcd7'>");
				}else{
					out.println("<tr>");
				}
				
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)aGroupSet.get(i)).get("deptName")+"</td>");
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)aGroupSet.get(i)).get("cscode")+"</td>");			
				out.println("<td align='left' style='mso-number-format:\\@' width='100%' nowrap>"+((Map)aGroupSet.get(i)).get("chi_name")+"</td>");
				
				if(((Map)aGroupSet.get(i)).get("opt").equals("1")){
					out.println("<td align='left' style='mso-number-format:\\@' nowrap>核心必修</td>");
				}else{
					out.println("<td align='left' style='mso-number-format:\\@' nowrap>核心選修</td>");
				}
				
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)aGroupSet.get(i)).get("credit")+"</td>");			
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+((Map)aGroupSet.get(i)).get("entrance")+"</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
		}
		
		out.close();
	}
}