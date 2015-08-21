package tw.edu.chit.struts.action.personnel.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class CardReaderOffline extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml; charset=UTF-8");
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");		
		StringBuilder line=new StringBuilder(request.getParameter("line"));		
		int dot=line.indexOf(",");
		String idno=line.substring(0, dot);
		//若以RFID讀入則轉換為username
		String inco=manager.ezGetString("SELECT username FROM wwpass WHERE inco='"+idno+"'");
		if(inco.length()>0){
			idno=inco;
		}		
		line.delete(0, dot+1);		
		dot=line.indexOf(",");
		String date=line.substring(0, dot);
		line.delete(0, dot+1);		
		String time=line.toString();		
		Map today=manager.ezGetMap("SELECT * FROM AMS_Workdate WHERE wdate='"+date+"' AND idno='"+idno+"'");		
		//System.out.println("work!");		
		if(today!=null){
			//應刷
			if(today.get("real_in")==null){
				//上班卡
				manager.executeSql("UPDATE AMS_Workdate SET real_in='"+time+"' WHERE idno='"+idno+"' AND wdate='"+date+"'");
				//System.out.println("已復原上班資料");
			}else{
				manager.executeSql("UPDATE AMS_Workdate SET real_out='"+time+"' WHERE idno='"+idno+"' AND wdate='"+date+"'");
				//System.out.println("已復原下班資料");
			}			
		}		
	}

}
