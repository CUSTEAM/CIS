package tw.edu.chit.service.quartz;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.AmsWorkdate;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.jdbc.Dao;


  
public class SendAskLeave extends QuartzJobBean {
    
	
    
    public void executeInternal(JobExecutionContext context){
    	
    	try{
    		getOverList();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
     
    private void getOverList() throws UnsupportedEncodingException, ParseException, AddressException, SQLException{
    	
    	StringBuilder path=new StringBuilder(this.getClass().getResource("/").toString());
    	path.delete(path.lastIndexOf("classes"), path.length());   	
    	
    	Dao dao=new Dao();
    	//System.out.println(new Date());
    	    	
    	
    	List list=dao.QueryForList("SELECT a.Name, e.cname, e.unit, d.Oid as dOid, d.* FROM AMS_DocApply d, AMS_AskLeave a, empl e WHERE e.idno=d.idno AND a.ID=d.askLeaveType AND d.sent='0'");
    	
    	
    	SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd");
    	SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss");
		Date today=new Date();
		Calendar c=Calendar.getInstance();
		
		c.setTime(today);		
		c.add(Calendar.WEEK_OF_YEAR, -1);
		
		
		//List list=manager.hqlGetBy("FROM AmsWorkdate WHERE wdate>'"+sf.format(c.getTime())+"' AND wdate<'"+sf.format(today)+"' AND set_in IS NOT NULL AND set_out IS NOT NULL");		

		//登入郵件伺服器帳號
		String username=dao.QueryForMap(("SELECT Value FROM Parameter WHERE Name='username' AND Category='smtp'")).get("Value").toString();
		//登入郵件伺服器密碼
		String password=dao.QueryForMap(("SELECT Value FROM Parameter WHERE Name='password' AND Category='smtp'")).get("Value").toString();
		//郵件伺服器
		String smtpServer=dao.QueryForMap(("SELECT Value FROM Parameter WHERE Name='mailServer' AND category='smtp'")).get("Value").toString();
		String myEmail=dao.QueryForMap(("SELECT Value FROM Parameter WHERE Name='mailAddress'")).get("Value").toString();
		
		InternetAddress[] address;
		Map empl;
		String content;
		String someday;
    	
    	
    	for(int i=0; i<list.size(); i++){
    		
    		
    		//System.out.println(list.get(i));
    		
    		Map map=dao.QueryForMap("SELECT e.cname, e.Email FROM AMS_AskHandler h, empl e WHERE " +
    				"h.empl_id=e.idno AND h.unit_id='"+((Map)list.get(i)).get("unit")+"'");
    		
    		
    		//System.out.println("SELECT e.cname, e.Email FROM AMS_AskHandler h, empl e WHERE " +
    				//"h.empl_id=e.idno AND h.unit_id='"+((Map)list.get(i)).get("unit")+"'");
    		
    		if(map==null){
    			continue;
    		}
    		
    		
    		content="您好<br><br>貴單位同仁 "+map.get("cname")+"因 "+map.get("reason")+"請假<br>自 " +map.get("startDate")+"起至 "+map.get("startDate")+"止<br>"+
					"共 "+map.get("totalDay")+"天, 期間代理人為 "+map.get("agent")+
			"<br>請點選「<a href=''>同意</a>」後完成審核。<br>若同仁已採書面申請，則可忽略此郵件。<br><br>附註:<br>" +
			
			//"1.<br>" +
			"1.相關規定與資料均以人事室最終公佈為主。<br>" +
			"2.此郵件為系統過濾記錄後(自動)發送，請勿直接回覆。<br>";			
			address=new InternetAddress[]{new InternetAddress(map.get("Email").toString(), map.get("cname").toString(),"Big5")};
			
			//System.out.println(content);
    		
			
			//manager.sendMail(username, password, smtpServer, myEmail, "中華科技大學資訊系統", today, "差勤記錄異常通知", content, address, null);
			//添加郵件備份
			//manager.saveMail("system", "中華科技大學資訊系統", "差勤記錄異常通知", address, content, today, myEmail);
    	
    	
    	
    	
			
			
		}	
    	
	}    
} 