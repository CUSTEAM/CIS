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

public class CardReaderSimple extends HttpServlet{
	
	//CourseManager manager = (CourseManager)getBean("courseManager");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml; charset=UTF-8");
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		//System.out.println("work");
		String CardNo=request.getParameter("CardNo");
		String day=request.getParameter("day");
		String time=request.getParameter("time");
		//若以RFID讀入則轉換為username
		String inco=manager.ezGetString("SELECT username FROM wwpass WHERE username='"+CardNo+"'");
		if(inco!=null){
			CardNo=inco;
		}
		String cname=manager.ezGetString("SELECT cname FROM empl WHERE idno='"+CardNo+"'");
		PrintWriter out=response.getWriter();
		//Map today=manager.ezGetMap("SELECT * FROM AMS_Workdate WHERE wdate='"+day+"' AND idno='"+CardNo+"'");
		Map today=null;
		//如果資料庫查詢失敗時，直接回傳錯誤碼，不再繼續。
		try{//TODO 判斷資料庫伺服器是否存活
			today=manager.ezGetMap("SELECT * FROM AMS_Workdate WHERE wdate='"+day+"' AND idno='"+CardNo+"'");
		}catch(Exception e){
			response.setStatus(400);
			out.close();
			return;
		}		
		String real_in_day;
		
		//回傳資訊
		//System.out.println("work2");
		out.println("<pront>");		
		if(today!=null){
			//若能找到應刷資訊
			real_in_day=today.get("wdate").toString();			
			out.println("<idno>"+CardNo+"</idno>");
			out.println("<cname>"+cname+"同仁 </cname>");
			out.println("<day>"+day+"</day>");
			out.println("<time>"+time+"</time>");
			if(today.get("real_in")==null){
				//當天第1次刷立即寫入上班時間
				out.println("<info>上班刷卡</info>");
				try{
					manager.executeSql("UPDATE AMS_Workdate SET real_in='"+time+"' WHERE idno='"+CardNo+"' AND wdate='"+day+"'");
				}catch(Exception e){
					out.println("<info>儲存失敗</info>");
				}			
			}else{				
				checkReSign(CardNo, real_in_day, today.get("real_in").toString(), day, time, out);				
			}			
			success(out, CardNo, cname, day, time);
			todayInfo(day, today, out);
			
		}else{			
			if(cname.equals("")){				
				cname=manager.ezGetString("SELECT cname FROM empl WHERE idno='"+CardNo+"'");
				//out.println("<cname>"+cname+"同仁</cname>");				
				if(cname.equals("")){
					cname=manager.ezGetString("SELECT student_name FROM stmd WHERE student_no='"+CardNo+"'");
					out.println("<cname>"+cname+"同學</cname>");
				}else{
					out.println("<cname>"+cname+"同仁</cname>");
				}				
				
				if(cname.equals("")){					
					out.println("<cname>陌生人</cname>");
				}
			}else{
				out.println("<cname>"+cname+"同仁</cname>");
			}			
			
			out.println("<info>無需刷卡</info>");
			out.println("<idno>"+CardNo+"</idno>");			
			out.println("<day>"+day+"</day>");
			out.println("<time>"+time+"</time>");			
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
	
	private void todayInfo(String day, Map today, PrintWriter out){
		out.println("<time>今天, 應上"+	today.get("set_in")+", 實上" +
				today.get("real_in")+", 應下"+today.get("set_out")+", 實下" +
				today.get("real_out")+"</time>");//本日	
	}
	
	private void success(PrintWriter out, String CardNo, String cname, String day, String time) throws IOException{
		
		//PrintWriter out=response.getWriter();
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");		
		List w=manager.ezGetBy("SELeCT * FROM AMS_Workdate WHERE " +
				"idno='"+CardNo+"' AND wdate<'"+day+"' AND set_in is not null ORDER BY wdate DESC LIMIT 3");
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
			/*
			out.println("<time>"+((Map)w.get(i)).get("wdate").toString().substring(5)+", " +
			"應上"+	((Map)w.get(i)).get("set_in")+", 上班時間 " +
			real_in+", 應下"+((Map)w.get(i)).get("set_out")+", 下班時間" +
			real_out+"</time>");
			*/
			out.println("<time>"+((Map)w.get(i)).get("wdate")+", " +
					" 上班時間 " +real_in+", 下班時間" +real_out+"</time>");
				
		}
		
		if(w.size()<3){			
			for(int i=w.size(); i<3; i++){				
				out.println("<time>查不到第"+(i+1)+"筆出勤資料</time>");	
			}
		}		
	}	
	
	private void checkReSign(String CardNo, String real_in_day, String real_in_time, String day, String time, PrintWriter out){
		//real_in_time=today.get("real_in").toString();
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());		
		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
	
}
