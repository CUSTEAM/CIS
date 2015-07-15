package tw.edu.chit.struts.action.personnel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Csno;
import tw.edu.chit.service.CourseManager;

public class CardReader extends HttpServlet{
	
	//CourseManager manager = (CourseManager)getBean("courseManager");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml; charset=UTF-8");
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");		
		String CardNo=request.getParameter("CardNo");
		String day=request.getParameter("day");
		String time=request.getParameter("time");
		//String name1=new String(name.getBytes("iso-8859-1"),"utf-8"); 		
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PrintWriter out=response.getWriter();	
		Map today=null;
		//如果資料庫查詢失敗時，直接回傳錯誤碼，不再繼續。
		try{
			today=manager.ezGetMap("SELECT * FROM AMS_Workdate WHERE wdate='"+day+"' AND idno='"+CardNo+"'");		
		}catch(Exception e){
			response.setStatus(400);
			out.close();
			return;
		}		
		
		boolean somebody=false;		
		String real_in_day;
		String real_in_time;			
		//回傳資訊
			
		out.println("<pront>");		
		List empl=manager.ezGetBy("SELECT cname, idno FROM empl WHERE idno='"+CardNo+"'");		
		//若沒這個職員的話
		if(empl.size()<1||today==null){			
			error(response, day, time);			
		}else{
			//若有職員
			somebody=true;
			//如果有職員才進行刷卡流程
			if(today.get("real_in")==null){
				//當天第1次刷立即寫入上班時間
				out.println("<info>上班刷卡</info>");
				try{
					manager.executeSql("UPDATE AMS_Workdate SET real_in='"+time+"' WHERE idno='"+CardNo+"' AND wdate='"+day+"'");
				}catch(Exception e){
					response.setStatus(400);
				}
				
			}else{
				//驗證重複刷卡
				real_in_day=today.get("wdate").toString();
				real_in_time=today.get("real_in").toString();
				int nt=0;
				int ot=0;
				try {				
					Date otime=sf.parse(real_in_day+" "+real_in_time);
					Date ntime=sf.parse(day+" "+time);				
					Calendar o=Calendar.getInstance();
					o.setTime(otime);
					Calendar n=Calendar.getInstance();
					n.setTime(ntime);				
					ot=o.get(Calendar.HOUR_OF_DAY);
					nt=n.get(Calendar.HOUR_OF_DAY);				
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//1小時以上不限分鐘				
				if(nt-ot>1){
					//才記錄下班
					manager.executeSql("UPDATE AMS_Workdate SET real_out='"+time+"' WHERE idno='"+CardNo+"' AND wdate='"+day+"'");
					out.println("<info>下班刷卡</info>");
				}else{
					out.println("<info>重複刷卡</info>");
				}				
			}
			out.println("<idno>"+((Map)empl.get(0)).get("idno")+"</idno>");
			out.println("<cname>"+((Map)empl.get(0)).get("cname")+"同仁已於 </cname>");
			out.println("<day>"+day+"</day>");
			out.println("<time>"+time+"</time>");			
		}		
		//如果有職員就開始找他前7天的刷卡記錄
		if(somebody){
			success(response, CardNo, ((Map)empl.get(0)).get("cname").toString(), day, time);			
		}		
		
		
		out.println("</pront>");
		
		
		out.close();		
	}
	
	
	//刷失敗
	private void error(HttpServletResponse response, String day, String time) throws IOException{		
		PrintWriter out=response.getWriter();		
		out.println("<cname>none</cname>");
		out.println("<day>"+day+"</day>");
		out.println("<time>"+time+"</time>");		
		out.println("<info>無法刷卡</info>");
		out.println("<idno></idno>");		
	}
	
	
	private void success(HttpServletResponse response, String CardNo, String cname, String day, String time) throws IOException{
		
		PrintWriter out=response.getWriter();
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		List w=manager.ezGetBy("SELeCT * FROM AMS_Workdate WHERE idno='"+CardNo+"' AND wdate<'"+day+"' ORDER BY wdate DESC LIMIT 3");
		String real_in = "";
		String real_out = "";
		for(int i=0; i<w.size(); i++){
			if(((Map)w.get(i)).get("real_in")!=null){
				real_in=((Map)w.get(i)).get("real_in").toString();
			}else{
				real_in = "未刷";
			}
			if(((Map)w.get(i)).get("real_out")!=null){
				real_out=((Map)w.get(i)).get("real_out").toString();
			}else{
				real_out = "未刷";
			}
			
			out.println("<cname>"+cname+"</cname>");
			out.println("<day>"+day+"</day>");
			out.println("<time>" +
			((Map)w.get(i)).get("wdate")+", 應上"+
			((Map)w.get(i)).get("set_in")+", 實上" +
			real_in+", 應下"+
			((Map)w.get(i)).get("set_out")+", 實下" +
			real_out+"</time>");
			
		}
		
	}
	
	
}
