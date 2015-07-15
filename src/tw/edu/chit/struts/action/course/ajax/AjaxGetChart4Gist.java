package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AjaxGetChart4Gist extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		
		
		List list;
		list = (List)session.getAttribute("CheckGist");
		
		Object obj[]=list.toArray();
		Map map;
		float finish=0;
		float unfinish=0;
		for(int i=0; i<obj.length; i++){
			if(((Map)obj[i]).get("box").equals("")){
				unfinish=unfinish+1;
			}else{
				finish=finish+1;
			}
		}
		
		if(finish>0){
			
			if(finish<unfinish){
				finish=finish*100/(unfinish+finish);
				unfinish=100-finish;
			}
		}else{
			unfinish=100;
		}
		
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		out.println("<chart>");
		out.println("<chart_data>");
		out.println("<row>");
		out.println("<null/>");
		out.println("<string>未完成</string>");
		out.println("<string>已完成</string>");
		out.println("</row>");
		out.println("<row>");
		out.println("<string></string>");
		out.println("<number>"+finish+"</number>");
		out.println("<number>"+unfinish+"</number>");
		out.println("</row>");
		out.println("</chart_data>");
		out.println("<chart_grid_h thickness='0' />");
		out.println("<chart_pref rotation_x='60' />");
		out.println("<chart_rect x='50' y='50' width='300' height='200' positive_alpha='0' />");
		out.println("<chart_transition type='spin' delay='.5' duration='0.75' order='category' />");
		out.println("<chart_type>3d pie</chart_type>");
		out.println("<chart_value color='000000' alpha='65' font='arial' bold='true' size='10' position='inside' prefix='' suffix='' decimals='0' separator='' as_percentage='true' />");

		out.println("<draw>");
		out.println("<text color='000000' alpha='65' size='40' x='-50' y='260' width='500' height='50' h_align='center' v_align='middle'></text>");
		out.println("</draw>");

		out.println("<legend_label layout='horizontal' bullet='circle' font='新細明體' bold='true' size='12' color='cccccc' alpha='85' />");
		out.println("<legend_rect x='0' y='45' width='50' height='210' margin='10' fill_color='ffffff' fill_alpha='10' line_color='000000' line_alpha='0' line_thickness='0' />");
		out.println("<legend_transition type='dissolve' delay='0' duration='1' />");

		out.println("<series_color>");
		out.println("<color>ffffff</color>");
		out.println("<color>cfe69f</color>");
		out.println("</series_color>");
		out.println("<series_explode>");
		out.println("<number>0</number>");
		out.println("<number>0</number>");
		out.println("</series_explode>");
		out.println("</chart>");
		out.close();
	}
}
