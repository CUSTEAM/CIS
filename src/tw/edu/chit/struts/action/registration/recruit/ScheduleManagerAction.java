package tw.edu.chit.struts.action.registration.recruit;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.icu.util.Calendar;

import tw.edu.chit.model.PubCalendar;
import tw.edu.chit.model.RecruitSchedule;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class ScheduleManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		getSelection(request);	
		
		//已選
		if(request.getParameter("Oid")!=null || request.getAttribute("aSchedule")!=null){
			CourseManager manager = (CourseManager) getBean("courseManager");
			if(request.getParameter("Oid")!=null){				
				request.setAttribute("aSchedule", manager.ezGetMap("SELECT r.*, rc.name as school_name FROM Recruit_schedule r, Recruit_school rc " +
				"WHERE r.school_code=rc.no AND r.Oid="+request.getParameter("Oid")));
				
				request.setAttribute("multUnits", manager.ezGetBy("SELECT rsu.schedule_oid as Oid, rsu.unit_id as id, c.* FROM Recruit_schedule_unit rsu, Recruit_schedule rs, code5 c WHERE " +
						"(c.category='Dept' OR c.category='DeptGroup') AND c.idno=rsu.unit_id AND rs.Oid=rsu.schedule_oid AND rs.Oid="+request.getParameter("Oid")+" ORDER BY c.sequence"));
			}else{
				request.setAttribute("aSchedule", request.getAttribute("aSchedule"));
				request.setAttribute("units", request.getAttribute("units"));
			}			
		}
		
		setContentPage(request.getSession(false), "registration/recruit/ScheduleManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm sForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String name=sForm.getString("name");
		String school_year=sForm.getString("school_year");
		String someday=sForm.getString("someday");
		//String DeptNo=sForm.getString("DeptNo");
		//String CodeEmpl_unit=sForm.getString("CodeEmpl_unit");
		String school_code=sForm.getString("school_code");
		String item=sForm.getString("item");
		
		session.setAttribute("schedules", manager.ezGetBy("SELECT rs.*, ri.name as itemName, s.name as schoolName FROM " +
		"Recruit_schedule rs LEFT OUTER JOIN Recruit_item ri ON rs.item=ri.id, Recruit_school s WHERE s.no=rs.school_code AND rs.name LIKE '"+name+"%' AND " +
		"rs.school_year LIKE '"+school_year+"%' AND rs.someday LIKE'"+someday+"%' AND " +
		"rs.item LIKE '"+item+"%' AND rs.school_code LIKE '"+school_code+"%' ORDER BY rs.someday DESC"));
		
		getSelection(request);
		return mapping.findForward("Main");
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		DynaActionForm sForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String name=sForm.getString("name");
		String school_year=sForm.getString("school_year");
		String someday=sForm.getString("someday");
		String CodeEmpl_unit=sForm.getString("CodeEmpl_unit");
		String school_code=sForm.getString("school_code");
		String item=sForm.getString("item");
		String sometime=sForm.getString("sometime");
		String check=sForm.getString("checkMail");
		
		if(school_code.trim().equals("")){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必須指定學校"));
			saveErrors(request, error);
			return mapping.findForward("Main");	
		}
		
		if(someday.trim().equals("")||school_year.trim().equals("")){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必須指定學年度和預定日期"));
			saveErrors(request, error);
			return mapping.findForward("Main");	
		}
		
		if(name.trim().equals("")){
			name=manager.ezGetString("SELECT name FrOM Recruit_school WHERE no='"+school_code+"'")+"招生活動";
		}
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		RecruitSchedule r=new RecruitSchedule();
		r.setLeader(CodeEmpl_unit);
		//r.setDeptNo(DeptNo);
		r.setEditor(c.getMember().getAccount());
		r.setItem(item);
		r.setName(name);
		r.setSchoolCode(school_code);
		r.setSchoolYear(Integer.parseInt(school_year));
		r.setSomeday(sf.parse(someday));
		r.setSometime(sometime);
		manager.updateObject(r);
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立完成"));
		
		request.setAttribute("aSchedule", manager.ezGetMap("SELECT r.*, rc.name as school_name FROM Recruit_schedule r, Recruit_school rc " +
				"WHERE r.school_code=rc.no AND r.Oid="+r.getOid()+" LIMIT 1"));
		
		//通知
		try{
			sendMail(r, request, check.charAt(0));
		}catch(Exception e){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", ", E-01"));
		}
		saveMessages(request, msg);
				
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Create", "create");
		map.put("Save", "save");
		return map;
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {		
		
		HttpSession session = request.getSession(false);
		DynaActionForm sForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String name=sForm.getString("name");
		String school_year=sForm.getString("school_year");
		String someday=sForm.getString("someday");
		
		String CodeEmpl_unit=sForm.getString("CodeEmpl_unit");
		String school_code=sForm.getString("school_code");
		String item=sForm.getString("item");
		String Oid=sForm.getString("Oid");
		String sometime=sForm.getString("sometime");
		
		String check=sForm.getString("checkMail");
		
		String url=sForm.getString("url");
		String result_descript=sForm.getString("result_descript");
		String work_descript=sForm.getString("work_descript");
		String staff=sForm.getString("staff");
		String feedback=sForm.getString("feedback");
		
		String unit[]=sForm.getStrings("unit");
		
		if(school_code.trim().equals("")){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必須指定學校"));
			saveErrors(request, error);
			return mapping.findForward("Main");	
		}
		
		if(someday.trim().equals("")||school_year.trim().equals("")){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必須指定學年度和預定日期"));
			saveErrors(request, error);
			return mapping.findForward("Main");	
		}
		
		if(name.trim().equals("")){
			name=manager.ezGetString("SELECT name FrOM Recruit_school WHERE no='"+school_code+"'")+"招生活動";
		}
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		RecruitSchedule r=new RecruitSchedule();
		r.setOid(Integer.parseInt(Oid));		
		r.setLeader(CodeEmpl_unit);
		//r.setDeptNo(DeptNo);
		r.setEditor(c.getMember().getAccount());
		r.setItem(item);
		r.setName(name);
		r.setSchoolCode(school_code);
		r.setSchoolYear(Integer.parseInt(school_year));
		r.setSomeday(sf.parse(someday));
		r.setSometime(sometime);
		r.setUrl(url);
		r.setResultDescript(result_descript);
		r.setWorkDescript(work_descript);
		r.setStaff(staff);
		r.setFeedback(feedback);
		manager.updateObject(r);
		
		manager.executeSql("DELETE FROM Recruit_schedule_unit WHERE schedule_oid="+r.getOid());
		for(int i=0; i<unit.length; i++){			
			if(!unit[i].trim().equals("")){
				try{
					manager.executeSql("INSERT INTO Recruit_schedule_unit(schedule_oid, unit_id)VALUES('"+r.getOid()+"', '"+unit[i]+"')");
				}catch(Exception e){
					
				}				
			}
		}
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存完成"));
		
		
		
		
		/*
		if(!check.equals("")){
			sendMail(r, request);

			String sender=c.getMember().getAccount();
			String no=String.valueOf(manager.ezGetInt("SELECT no FROM PubCalendar ORDER BY Oid DESC LIMIT 1")+1);
			
			//連結個人行事曆
			StringBuilder sb=new StringBuilder();
			List group=manager.ezGetBy("SELECT name FROM MailGroup WHERE ModuleOid=6314");
			for(int i=0; i<group.size(); i++){
				sb.append( ((Map)group.get(i)).get("name")+", ");				
			}
			sb.delete(sb.length()-2, sb.length());
			String schoolName=manager.ezGetString("SELECT name FROM Recruit_school " +
					"WHERE no='"+school_code+"'");
			//System.out.println(sb.toString());
			PubCalendar p=new PubCalendar();
			p.setAccount(sender);
			p.setDate(r.getSomeday());
			try{
				p.setTime(r.getSometime());
			}catch(Exception e){
				p.setTime("00");
			}
			
			p.setMembers(sb.toString());
			p.setName(schoolName+"招生活動");
			p.setNo(no);
			p.setNote(r.getWorkDescript());
			p.setPlace(schoolName);
			p.setSender(sender);
			p.setTime(r.getSometime());
			p.setType("0");
			
			manager.createPubCalendar(sb.toString(), sender, someday, sometime, schoolName, schoolName+"招生活動", r.getWorkDescript(), p, null);			
		}
		*/ 
		
		//通知
		try{
			sendMail(r, request, check.charAt(0));
		}catch(Exception e){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", ", E-01"));
		}
		saveMessages(request, msg);
		
		getSelection(request);
		session.removeAttribute("aSchedule");
		return query(mapping, form, request, response);
	}
	
	private void getSelection(HttpServletRequest request){
		CourseManager manager = (CourseManager) getBean("courseManager");
		request.setAttribute("items", manager.ezGetBy("SELECT * FROM Recruit_item"));
		request.setAttribute("depts", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Dept' OR category='DeptGroup' ORDER BY sequence"));
		request.setAttribute("units", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='Unit'"));
	}
	
	/**
	 * 寄送郵件
	 * @param r
	 */
	private void sendMail(RecruitSchedule r, HttpServletRequest request, char c){
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		List list=new ArrayList();
		List empl;
		Map mail;
		
		switch(c) {
	        case '0': 
	            break;
	            
	        case '1': //全部
	        	list=manager.ezGetBy("SELECT * FROM MailGroup WHERE ModuleOid=6314");
	            empl=manager.ezGetBy("SELECT * FROM empl");	            
	            for(int i=0; i<empl.size(); i++){
	            	if(r.getStaff().indexOf(((Map)empl.get(i)).get("cname").toString())>-1){
	            		mail=new HashMap();
	            		mail.put("name", ((Map)empl.get(i)).get("cname"));
	            		mail.put("mailaddress", ((Map)empl.get(i)).get("Email"));
	            		list.add(mail);
	            	}
	            }	        	
	        	break;
	        	
	        case '2': 
	        	list=manager.ezGetBy("SELECT * FROM MailGroup WHERE ModuleOid=6314");
	            break;
	            
	        case '3': 	        	
	        	empl=manager.ezGetBy("SELECT cname, Email FROM empl");	            
	            for(int i=0; i<empl.size(); i++){
	            	
	            	if(r.getStaff().indexOf(((Map)empl.get(i)).get("cname").toString())>-1){
	            		
	            		//System.out.println(r.getStaff());
	            		
	            		mail=new HashMap();
	            		mail.put("name", ((Map)empl.get(i)).get("cname"));
	            		mail.put("mailaddress", ((Map)empl.get(i)).get("Email"));
	            		list.add(mail);
	            	}
	            }
	            break; 
		}
		
		if(list.size()<1){			
			throw new RuntimeException();
		}
		
		addCalendar(r, request);
		
		//System.out.println("list.size="+list.size());
		InternetAddress address[] = new InternetAddress[list.size()];
		InternetAddress addr;
		for(int i=0; i<address.length; i++){
			//System.out.println(((Map)list.get(i)).get("mailaddress").toString()+", "+((Map)list.get(i)).get("name").toString());
			try {
				
				addr=new InternetAddress(((Map)list.get(i)).get("mailaddress").toString(), ((Map)list.get(i)).get("name").toString(), "BIG5");
				address[i]=addr;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		StringBuilder sb=new StringBuilder("各位師長您好:<br><br>由"+
				//TODO manager.ezGetString("SELECT name FROM code5 WHERE idno='"+r.getDeptNo()+"' AND category='Dept'")+"主辦至"+
				manager.ezGetString("SELECT name FROM Recruit_school WHERE no='"+r.getSchoolCode()+"'")+"招生活動<br>");
					sb.append("活動名稱: "+r.getName()+"<br>");
					
					if(r.getSomeday()!=null){
						SimpleDateFormat sf=new SimpleDateFormat("yyyy年MM月dd日");
						sb.append("活動日期: "+sf.format(r.getSomeday())+", "+r.getSometime()+"<br>");
					}
					
					if(r.getLeader()!=null && !r.getLeader().equals("")){
						sb.append("連絡人: "+r.getLeader()+"<br>");
					}					
					sb.append("活動項目: "+manager.ezGetString("SELeCT name FROM Recruit_item WHERE id='"+r.getItem()+"'")+"<br>");					
					
					if(r.getUrl()!=null && !r.getUrl().equals("")){						
						sb.append("相關連結: "+r.getUrl()+"<br>");						
					}					
					if(r.getStaff()!=null && !r.getStaff().equals("")){
						sb.append("參與者: "+r.getStaff()+"<br>");
					}					
					if(r.getWorkDescript()!=null && !r.getWorkDescript().equals("")){
						sb.append("工作說明: "+r.getWorkDescript()+"<br>");
					}
					if(r.getResultDescript()!=null && !r.getResultDescript().equals("")){
						sb.append("執行結果說明: "+r.getResultDescript()+"<br>");
					}
					if(r.getFeedback()!=null && !r.getFeedback().equals("")){						
						sb.append("高職建議事項: "+r.getUrl()+"<br>");						
					}					
					sb.append("<p>預祝活動順利</p>");		
		Map map=manager.getCISMailServerInfo();		
		ServletContext context = request.getSession().getServletContext();
		
		manager.sendMail(
		map.get("username").toString(), 
		map.get("password").toString(), 
		map.get("mailServer").toString(), 
		map.get("mailAddress").toString(), 
		(String) context.getAttribute("SchoolName_ZH")+"招生規劃系統", 
		new Date(), 
		"招生公告", 
		sb.toString(), 
		address, 
		null);
	}
	
	/**
	 * 加入行事曆
	 * @param r
	 * @param request
	 * TODO 是否可以不用存行事曆直接去招生活動讀, 減少資料量
	 */
	private void addCalendar(RecruitSchedule r, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		CourseManager manager = (CourseManager) getBean("courseManager");

		String sender=c.getMember().getAccount();
		String no=String.valueOf(manager.ezGetInt("SELECT no FROM PubCalendar ORDER BY Oid DESC LIMIT 1")+1);
		
		//連結個人行事曆
		StringBuilder sb=new StringBuilder();		
		List group=new ArrayList();//=manager.ezGetBy("SELECT name FROM MailGroup WHERE ModuleOid=6314");
		for(int i=0; i<group.size(); i++){
			sb.append(((Map)group.get(i)).get("name")+", ");				
		}
		//sb.delete(sb.length()-2, sb.length());
		String schoolName=manager.ezGetString("SELECT name FROM Recruit_school " +
				"WHERE no='"+r.getSchoolCode()+"'");
		
		sb.append(r.getStaff()+", "+r.getLeader());
		
		PubCalendar p=new PubCalendar();
		p.setAccount(sender);
		
		//p.setDate(r.getSomeday());		
		//try{
			//p.setTime(r.getSometime());
		//}catch(Exception e){
			//p.setTime("00");
		//}		
		//p.setTime(r.getSometime());
		
		Calendar ca=Calendar.getInstance();
		ca.setTime(r.getSomeday());
		p.setBegin(r.getSomeday());
		ca.add(Calendar.DAY_OF_YEAR, 1);
		p.setEnd(ca.getTime());		
		p.setMembers(sb.toString());
		p.setName(schoolName+"招生活動");
		p.setNo(no);
		p.setNote(r.getWorkDescript());
		p.setPlace(schoolName);
		p.setSender(sender);		
		p.setType("0");
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd");
		manager.createPubCalendar(sb.toString(), sender, manager.convertDate(sf.format(r.getSomeday())), r.getSometime(), schoolName, schoolName+"招生活動", r.getWorkDescript(), p, null);
		
	}
}
