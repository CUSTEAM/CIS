package tw.edu.chit.struts.action.onlineService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.Toolket;

public class AppointmentViewAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(false);
		UserCredential user = (UserCredential) session.getAttribute("Credential");		
		CourseManager manager = (CourseManager) getBean("courseManager");			
		request.setAttribute("myAccount", user.getMember().getAccount());
		
		Calendar c=Calendar.getInstance();		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sft=new SimpleDateFormat("HH:mm");		
		int count=0;		
		List table=new ArrayList();
		Map map;
		String tmpDate;
		
		//取最近2周(共4次符合)
		while(count<=4){			
			c.add(Calendar.DAY_OF_MONTH, 1);
			tmpDate=sf.format(c.getTime());
			map=new HashMap();
			//目前開放預約的是周一:3，周五:6
			if(c.get(Calendar.DAY_OF_WEEK)==6 || c.get(Calendar.DAY_OF_WEEK)==3){
				map.put("date4tw", manager.convertDate(tmpDate));
				map.put("date4ad", sf.format(c.getTime()));
				//探測是否為假日
				if(manager.ezGetInt("SELECT COUNT(*)FROM AMS_Holiday WHERE Date='"+tmpDate+"'")<1){
					count=count+1;//計數器+1
					table.add(map);
				}
			}			
		}
		
		List tmpList;
		Map tmpMap;
		Map member;
		for(int i=0; i<table.size(); i++){
			
			c.set(Calendar.HOUR_OF_DAY, 12);
			c.set(Calendar.MINUTE, 30);
			
			tmpDate=((Map)table.get(i)).get("date4ad").toString();			
			tmpList=new ArrayList();
			for(int j=1; j<=6; j++){				
				tmpMap=new HashMap();
				tmpMap.put("time", sft.format(c.getTime()));//裝入時間				
				try{
					//已預約的傢伙
					member=manager.ezGetMap("SELECT * FROM ClinicService WHERE cdate='"+tmpDate+"' AND sequence="+j);				
					member.put("name", manager.ezGetString("SELECT cname FROM empl WHERE idno='"+member.get("patient")+"'"));
					member.put("unit", manager.ezGetString("SELECT c.name as unit FROM empl e, CodeEmpl c WHERE c.idno=e.unit AND e.idno='"+member.get("patient")+"'"));
					
					if(member.get("name")==""){
						//System.out.println("SELECT student_name FROM stmd WHERE idno='"+member.get("patient")+"'");
						member.put("name", manager.ezGetString("SELECT student_name FROM stmd WHERE student_no='"+member.get("patient")+"'"));						
						member.put("unit", manager.ezGetString("SELECT c.ClassName FROM stmd s, Class c WHERE c.ClassNo=s.depart_class AND s.student_no='"+member.get("patient")+"'"));
					}
					
					tmpMap.putAll(member);				
				}catch(Exception e){
					
				}
				
				tmpMap.put("sequence", j);
				tmpList.add(tmpMap);
				c.add(Calendar.MINUTE, 10);//間隔10分鐘
			}			
			((Map)table.get(i)).put("list", tmpList);			
		}	
		
		request.setAttribute("table", table);		
		setContentPage(request.getSession(false), "onlineService/AppointmentView.jsp");
		return mapping.findForward("Main");
	}
	
}

