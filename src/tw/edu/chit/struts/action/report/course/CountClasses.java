package tw.edu.chit.struts.action.report.course;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

public class CountClasses extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename=CountClasses.xls");
		
		List classes=(List)session.getAttribute("classes");
		
		PrintWriter out=response.getWriter();
		
		out.println("<html><head><style>td{font-size:18px;}</style></head>");
		out.println("<table width='100%' border='1'>  ");
		
		out.println("<tr>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>型態</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級代碼</td>");		
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級名稱</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>校區</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>部制</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>學制</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>科系所</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>年級</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>男</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>女</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>人數</td>");
		out.println("	</tr>");
		
		
		if(request.getParameter("filter")==null){
			
			
			for(int i=0; i<classes.size(); i++){
				
				try{
					
					out.println("<tr>");
									
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("ClassTypeName")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("depart_class")+"</td>");		
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("ClassName")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("CampusName")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("SchoolTypeName")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("SchoolName")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("DeptName")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("Grade")+"</td>");
					out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("ShortName")+"</td>");
					out.println("<td align='center' nowrap>"+((Map)classes.get(i)).get("m")+"</td>");	
					out.println("<td align='center' nowrap>"+(Integer.parseInt(((Map)classes.get(i)).get("cnt").toString())-Integer.parseInt(((Map)classes.get(i)).get("m").toString()))+"</td>");
					out.println("<td align='center' nowrap>"+((Map)classes.get(i)).get("cnt")+"</td>");
					out.println("	</tr>");
				}catch(Exception e){
					
				}				
			}
			
		}else{
			
			int x=0;
			for(int i=0; i<classes.size(); i++){
				//System.out.println(classes.get(i));
				try{
					
					if(Integer.parseInt(((Map)classes.get(i)).get("cnt").toString())>0){
						
						
						out.println("<tr>");
						
						
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("ClassTypeName")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("depart_class")+"</td>");		
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("ClassName")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("CampusName")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("SchoolTypeName")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("SchoolName")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("DeptName")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("Grade")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("ShortName")+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("m")+"</td>");	
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+(Integer.parseInt(((Map)classes.get(i)).get("cnt").toString())-Integer.parseInt(((Map)classes.get(i)).get("m").toString()))+"</td>");
						out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+((Map)classes.get(i)).get("cnt")+"</td>");
						out.println("	</tr>");
					}
					
					
					
				}catch(Exception e){
					
				}
			}			
		}		
		out.println("	</table>");
		out.close();
		return null;
	}

}
