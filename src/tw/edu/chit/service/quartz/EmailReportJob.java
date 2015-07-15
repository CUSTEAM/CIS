package tw.edu.chit.service.quartz;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import tw.edu.chit.model.AmsWorkdate;
import tw.edu.chit.service.CourseManager;


  
public class EmailReportJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}
	/**
     * 寄送異常
     * todo 8:20, 16:30
     * 4台主機不可重複寄送
	private JobData jobData;

    public void setJobData(JobData jobData) {
        this.jobData = jobData;
    }
    
    public JobData getJobData() {
        return jobData;
    }
    
    public void executeInternal(JobExecutionContext context){
    	/*
    	try{
    		getOverList();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
    
    
     
    private void getOverList() throws UnsupportedEncodingException, ParseException, AddressException{
    	    	
    	StringBuilder path=new StringBuilder(this.getClass().getResource("/").toString());
    	path.delete(path.lastIndexOf("classes"), path.length());   	
    
    	ApplicationContext cxt=new FileSystemXmlApplicationContext(path+"/applicationContext.xml");
    	CourseManager manager = (CourseManager)cxt.getBean("courseManager");
    	
    	SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd");
    	SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss");
		Date today=new Date();
		Calendar c=Calendar.getInstance();
		
		c.setTime(today);		
		c.add(Calendar.WEEK_OF_YEAR, -1);
		
		System.out.println();
		List list=manager.hqlGetBy("FROM AmsWorkdate WHERE wdate>'"+sf.format(c.getTime())+"' AND wdate<'"+sf.format(today)+"' AND set_in IS NOT NULL AND set_out IS NOT NULL");		

		//登入郵件伺服器帳號
		String username=manager.ezGetString("SELECT Value FROM Parameter WHERE Name='username' AND Category='smtp'");
		//登入郵件伺服器密碼
		String password=manager.ezGetString("SELECT Value FROM Parameter WHERE Name='password' AND Category='smtp'");
		//郵件伺服器
		String smtpServer =manager.ezGetString("SELECT Value FROM Parameter WHERE Name='mailServer' AND category='smtp'");
		String myEmail=manager.ezGetString("SELECT Value FROM Parameter WHERE Name='mailAddress'");
		
		InternetAddress[] address;
		Map empl;
		String content;
		String someday;
		
		for(int i=0; i<list.size(); i++){			
			try{
				c.setTime(((AmsWorkdate)list.get(i)).getSetIn());
				c.add(Calendar.MINUTE, 21);
				((AmsWorkdate)list.get(i)).getSetIn().setTime(c.getTime().getTime());
				
				c.setTime(((AmsWorkdate)list.get(i)).getSetOut());
				c.add(Calendar.MINUTE, -10);
				((AmsWorkdate)list.get(i)).getSetOut().setTime(c.getTime().getTime());
				
				if(     ((AmsWorkdate)list.get(i)).getRealIn().after(((AmsWorkdate)list.get(i)).getSetIn()) ||     
						((AmsWorkdate)list.get(i)).getRealOut().before(((AmsWorkdate)list.get(i)).getSetOut())   ){					
					
					//System.out.println(((AmsWorkdate)list.get(i)).getRealIn().after(((AmsWorkdate)list.get(i)).getSetIn()));
					//System.out.println(((AmsWorkdate)list.get(i)).getRealOut().before(((AmsWorkdate)list.get(i)).getSetOut()));
					//((AmsWorkdate)list.get(i)).getRealOut().before(((AmsWorkdate)list.get(i)).getSetOut())
					someday=sf.format(((AmsWorkdate)list.get(i)).getId().getWdate());
					
					empl=manager.ezGetMap("SELECT Email, cname, idno FROM empl WHERE idno='"+((AmsWorkdate)list.get(i)).getId().getIdno()+"'");
					//若今天4台主機均尚未寄
					if(manager.ezGetInt("SELECT COUNT(*) FROM AMS_Workdate_notice WHERE " +
							//2011/6/7改成只寄1封
							//"idno='"+((AmsWorkdate)list.get(i)).getId().getIdno()+"' AND date='"+someday+"' AND sendate='"+sf.format(today)+"'")<1){
							"idno='"+((AmsWorkdate)list.get(i)).getId().getIdno()+"' AND date='"+someday+"'")<1){
						
						//添加已追蹤記錄
						manager.executeSql("INSERT INTO AMS_Workdate_notice(idno, date, sendate)VALUES('"+empl.get("idno")+"', '"+someday+"', '"+sf.format(today)+"')");
						//寄送
						content=empl.get("cname")+"同仁您好<br><br>您在"+someday+"的差勤記錄 出現異常: <br>" +
								"上班刷卡時間為"+df.format(((AmsWorkdate)list.get(i)).getRealIn())+", 下班刷卡時間為"+df.format(((AmsWorkdate)list.get(i)).getRealOut())+
						"<br>請至人事室提出相關申請。<br>若已提出申請，則可忽略此郵件。<br><br>附註:<br>" +
						"1.相關規定與資料均以人事室公佈為主。<br>" +
						"2.當日在各校區的最初刷卡記錄為上班時間 ，最後刷卡記錄為下班時間<br>" +
						"3.系統會重複檢查各台讀卡機資料，持續7天。<br>" +
						"4.此郵件為系統過濾記錄後(自動)發送，請勿直接回覆。<br>";			
						address=new InternetAddress[]{new InternetAddress(empl.get("Email").toString(), empl.get("cname").toString(),"Big5")};
						//System.out.println(content);
						manager.sendMail(username, password, smtpServer, myEmail, "中華科技大學資訊系統", today, "差勤記錄異常通知", content, address, null);
						//添加郵件備份
						manager.saveMail("system", "中華科技大學資訊系統", "差勤記錄異常通知", address, content, today, myEmail);
					}
				}				
			}catch(Exception e){
				//e.printStackTrace();
			}
			
		}			
	}
	*/
    
} 