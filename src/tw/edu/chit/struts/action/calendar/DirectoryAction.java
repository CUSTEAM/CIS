package tw.edu.chit.struts.action.calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.struts.action.BaseAction;

public class DirectoryAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession(false);		
		setContentPage(session, "calendar/Directory.jsp");		
		
		/*
		 * 
		UserCredential cr=(UserCredential) session.getAttribute("Credential");
		session.setAttribute("CISAccount", cr.getMember().getAccount());
		String account=cr.getMember().getAccount();
		if (Toolket.TriggerMenuCollapse(session, "Calendar")) {			
			session.setAttribute("directory", "Calendar");
		}
		
		Date date=new Date();
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		
		int w;
		if(session.getAttribute("cweek")==null){
			w=c.get(Calendar.WEEK_OF_YEAR);
		}else{
			w=Integer.parseInt(session.getAttribute("cweek").toString());
		}
		
		//週期判斷
		if(request.getParameter("cycle")!=null){
			//減少週		
			if(request.getParameter("cycle").equals("lastweek")){
				w=w-1;
			}
			//增加週
			if(request.getParameter("cycle").equals("nextweek")){
				w=w+1;
			}
			
			//減少月
			if(request.getParameter("cycle").equals("lastmonth")){
				w=w-4;
			}
			//增加月
			if(request.getParameter("cycle").equals("nextmonth")){
				w=w+4;
			}
		}
		
		//載入月份
		//為了跨年
		if(c.get(Calendar.MONTH)==11&&c.get(Calendar.WEEK_OF_MONTH)==5){			
			session.setAttribute("cweek", 5);//存下這次的週數		
			//c.set(Calendar.WEEK_OF_YEAR, w);
			
			c.set(Calendar.DAY_OF_WEEK, 7);		
			Date end=c.getTime();
			
			c.set(Calendar.DAY_OF_WEEK, 1);
			Date start=c.getTime();		
			
			CourseManager manager = (CourseManager) getBean("courseManager");		
			List list=manager.getSelfCalendar(start, end, account);
			
			c.setTime(start);
			request.setAttribute("myMonth", c.get(Calendar.MONTH)+1);
			request.setAttribute("myWeek", c.get(Calendar.WEEK_OF_MONTH));
			request.setAttribute("myYear", c.get(Calendar.YEAR)-1911);	
			
			c.setTime(date);
			request.setAttribute("weekDay", c.get(Calendar.DAY_OF_WEEK));
			
			//載入群組
			request.setAttribute("myGroup", manager.ezGetBy("SELECT * FROM TxtGroup WHERE account='"+cr.getMember().getAccount()+"' OR account='All'"));
			session.setAttribute("myCalendar", list);
		}else{
			
			session.setAttribute("cweek", w);//存下這次的週數		
			c.set(Calendar.WEEK_OF_YEAR, w);
			
			c.set(Calendar.DAY_OF_WEEK, 7);		
			Date end=c.getTime();
			
			c.set(Calendar.DAY_OF_WEEK, 1);
			Date start=c.getTime();		
			
			CourseManager manager = (CourseManager) getBean("courseManager");		
			List list=manager.getSelfCalendar(start, end, account);
			
			c.setTime(start);
			request.setAttribute("myMonth", c.get(Calendar.MONTH)+1);
			request.setAttribute("myWeek", c.get(Calendar.WEEK_OF_MONTH));
			request.setAttribute("myYear", c.get(Calendar.YEAR)-1911);
			
			c.setTime(date);
			request.setAttribute("weekDay", c.get(Calendar.DAY_OF_WEEK));
			
			//載入群組
			request.setAttribute("myGroup", manager.ezGetBy("SELECT * FROM TxtGroup WHERE account='"+cr.getMember().getAccount()+"' OR account='All'"));
			session.setAttribute("myCalendar", list);
		}
		*/
				
		return mapping.findForward("Main");
	}
}
