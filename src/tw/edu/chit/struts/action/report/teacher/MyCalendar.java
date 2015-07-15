package tw.edu.chit.struts.action.report.teacher;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Member;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

public class MyCalendar extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		Member me = getUserCredential(request.getSession(false)).getMember();		
		
		ServletContext servletContext = session.getServletContext();		
		Date school_term_begin=(Date) servletContext.getAttribute("school_term_begin");//取得開學日期
		Date school_term_end=(Date) servletContext.getAttribute("school_term_end");//取得學期結束日期			
		Date now=new Date();
		//可用變數
		char week;		
		String start;
		String end;
		Date vstart;
		Date vend;
		SimpleDateFormat sf1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<data>");				
		
		//課程部份
		List list=manager.ezGetBy("SELECT cl.CampusNo,  cl.SchoolNo, " +
		"dc.*, cl.ClassName, cs.chi_name FROM Dtime d, Csno cs, Class cl, Dtime_class dc " +
		"WHERE d.cscode=cs.cscode AND d.depart_class=cl.ClassNo AND d.Oid=dc.Dtime_oid AND " +
		"d.Sterm='"+servletContext.getAttribute("school_term")+"' AND d.techid='"+me.getAccount()+"'");
		
		Calendar begin=Calendar.getInstance();
		Calendar show;
		begin.setTime(school_term_begin);
		for(int i=0; i<list.size(); i++){
			show=Calendar.getInstance();			
			show.setTime(begin.getTime());
			week=((Map)list.get(i)).get("week").toString().charAt(0);			
			
			switch(week){
	            case '1':show.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); break;
	            case '2':show.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY); break;
	            case '3':show.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY); break;
	            case '4':show.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY); break;
	            case '5':show.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY); break;
	            case '6':show.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY); break;
	            case '7':show.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); break;
            }
			
			if(show.getTime().before(begin.getTime())){
				continue;
			}
			//為計算起迄時間差
			start=manager.ezGetString("SELECT d.DSbegin FROM Dtimestamp d WHERE " +
			"d.Cidno='"+((Map)list.get(i)).get("CampusNo")+"' AND d.Sidno='"+((Map)list.get(i)).get("SchoolNo")+"' AND " +
			"d.DSweek='"+week+"' AND d.DSreal="+((Map)list.get(i)).get("begin"));
			
			if(start.equals("")){continue;}
			
			end=manager.ezGetString("SELECT d.DSend FROM Dtimestamp d WHERE " +
			"d.Cidno='"+((Map)list.get(i)).get("CampusNo")+"' AND d.Sidno='"+((Map)list.get(i)).get("SchoolNo")+"' AND " +
			"d.DSweek='"+((Map)list.get(i)).get("week")+"' AND d.DSreal="+((Map)list.get(i)).get("end"));
			
			//TODO 是否有比較帥氣的方法可用來計算2個時間差? 18:05減12:30等於?分?秒?時?
			//有人瑪的沒排時間
			try{
				vstart=sf1.parse("2012-12-21 "+start);
				vend=sf1.parse("2012-12-21 "+end);
			}catch(Exception e){
				vstart=sf1.parse("2012-12-21 00:00");
				vend=sf1.parse("2012-12-21 01:50");
			}
			
			
			
			
			out.println("<event>");
			out.println("<id><![CDATA["+(i+1)+"]]></id>");
			out.println("<text><![CDATA["+((Map)list.get(i)).get("ClassName")+", "+((Map)list.get(i)).get("chi_name")+"]]></text>");
			out.println("<start_date><![CDATA["+sf.format(show.getTime())+" "+start+"]]></start_date>");
			out.println("<end_date><![CDATA["+sf.format(school_term_end)+" "+end+"]]></end_date>");
			out.println("<rec_type><![CDATA[week_1___"+week+"#no]]></rec_type>");
			out.println("<event_length><![CDATA["+(vend.getTime()-vstart.getTime())/1000+"]]></event_length>");
			out.println("<event_pid><![CDATA[0]]></event_pid>");
			out.println("<color><![CDATA[#555555]]></color>");
			out.println("<textColor><![CDATA[#FFFFFF]]></textColor>");
			out.println("<details><![CDATA["+((Map)list.get(i)).get("ClassName")+"]]></details>");//參與者
			out.println("<members><![CDATA["+((Map)list.get(i)).get("ClassName")+"選課同學]]></members>");
			out.println("</event>");			
		}
		
		
		//個人行事曆
		begin.add(Calendar.MONTH, -3);//提前3個月
		list=manager.ezGetBy("SELECT * FROM Calendar WHERE (account='"+me.getAccount()+"'||account='all')AND " +
		""+"begin>='"+sf.format(begin.getTime())+"'");
		
		for(int i=0; i<list.size(); i++){	
			
			out.println("<event>");
			out.println("<id><![CDATA["+((Map)list.get(i)).get("no")+"]]></id>");
			out.println("<text><![CDATA["+manager.replaceChar4XML(((Map)list.get(i)).get("name").toString())+"]]></text>");
			out.println("<start_date><![CDATA["+((Map)list.get(i)).get("begin")+"]]></start_date>");
			out.println("<end_date><![CDATA["+((Map)list.get(i)).get("end")+"]]></end_date>");
			
			//循環
			if(((Map)list.get(i)).get("rec_type")!=null){
				//若為循環							
				out.println("<rec_type><![CDATA["+((Map)list.get(i)).get("rec_type")+"]]></rec_type>");
				out.println("<event_length><![CDATA["+((Map)list.get(i)).get("rec_event_length")+"]]></event_length>");
				out.println("<event_pid><![CDATA["+((Map)list.get(i)).get("no")+"]]></event_pid>");
				//色彩
				out.println("<color><![CDATA[#555555]]></color>");
				out.println("<textColor><![CDATA[#FFFFFF]]></textColor>");
			}else{
				//非循環				
				if(((Map)list.get(i)).get("sender").equals(me.getAccount())){
					out.println("<color><![CDATA[#5484ED]]></color>");
					out.println("<textColor><![CDATA[#FFFFFF]]></textColor>");
				}else{
					out.println("<color><![CDATA[#FFAD46]]></color>");
					out.println("<textColor><![CDATA[#555555]]></textColor>");
				}			
			}
			
			out.println("<details><![CDATA["+manager.replaceChar4XML(((Map)list.get(i)).get("note").toString())+"]]></details>");//參與者
			out.println("<members><![CDATA["+manager.replaceChar4XML(((Map)list.get(i)).get("members").toString())+"]]></members>");			
			out.println("</event>");
			
		}
		
		out.println("</data>");		
		out.close();		
		return null;
	}

}
