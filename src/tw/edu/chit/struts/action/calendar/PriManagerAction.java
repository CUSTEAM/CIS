package tw.edu.chit.struts.action.calendar;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
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

import tw.edu.chit.model.PubCalendar;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

/**
 * 個人行事曆管理
 * 
 * @author JOHN
 */
public class PriManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		//準備刪除的功能
		HttpSession session = request.getSession(false);
		UserCredential cr=(UserCredential) session.getAttribute("Credential");
		session.setAttribute("CISAccount", cr.getMember().getAccount());//確保無失
		String account=cr.getMember().getAccount();		
		
		setContentPage(session, "calendar/PriManager.jsp");		
		
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
		
		session.setAttribute("cweek", w);//存下這次增加數
		c.set(Calendar.WEEK_OF_YEAR, w);
		c.getTime();
		int myWeek=c.get(Calendar.WEEK_OF_MONTH);
		
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DATE));
		Date end=c.getTime();		
		
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DATE));
		Date start=c.getTime();
		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		List list=manager.getSelfCalendar(start, end, account);
		
		//載入月份
		c.setTime(end);
		request.setAttribute("myMonth", c.get(Calendar.MONTH)+1);
		request.setAttribute("myWeek", myWeek);
		request.setAttribute("myYear", c.get(Calendar.YEAR)-1911);
		c.setTime(date);
		request.setAttribute("weekDay", c.get(Calendar.DAY_OF_WEEK));
		
		//載入群組
		request.setAttribute("myGroup", manager.ezGetBy("SELECT * FROM TxtGroup WHERE account='"+cr.getMember().getAccount()+"' OR account='All'"));
		session.setAttribute("myCalendar", list);
		
		return mapping.findForward("Main");
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession(false);
		UserCredential cr=(UserCredential) session.getAttribute("Credential");
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm f=(DynaActionForm) form;
		
		String[] name=f.getStrings("name");
		String[] place=f.getStrings("place");
		
		
		String[] beginDate=f.getStrings("beginDate");
		String[] beginTime=f.getStrings("beginTime");
		//String[] endDate=f.getStrings("endDate");
		String[] endTime=f.getStrings("endTime");
		
		String[] members=f.getStrings("members");
		String[] note=f.getStrings("note");
		
		String[] color=f.getStrings("color");
		String[] type=f.getStrings("type");
		
		FormFile addFile=(FormFile)f.get("addFile");
		
		PubCalendar c;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		String sender=cr.getMember().getAccount();
		Date date=new Date();
		String no=String.valueOf(date.getTime());
		date=null;
		for(int i=0; i<name.length; i++){
			
			if(!name[i].equals("")&&!beginDate[i].equals("")){
				c=new PubCalendar();
				c.setAccount(cr.getMember().getAccount());
				
				c.setBegin(sf.parse(beginDate[i]+" "+beginTime[i]));
				c.setEnd(sf.parse(beginDate[i]+" "+endTime[i]));
				
				c.setMembers(members[i]);
				c.setName(name[i]);
				c.setNote(note[i]);
				c.setPlace(place[i]);
				
				c.setColor(color[i]);
				c.setType(type[i]);
				c.setSender(sender);
				c.setNo(no);
				
				try{
					manager.updateObject(c);					
					Map map=manager.createPubCalendar(members[i], cr.getMember().getAccount(), beginDate[i], beginTime[i], place[i], name[i], note[i], c, addFile);
					int x=Integer.parseInt(map.get("count").toString());
					String lost="";
					try{
						lost=map.get("lost").toString();
					}catch(Exception e){
						e.printStackTrace();
					}
					
					if(x>0){
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已儲存並且通知 "+x+"位同仁(包含建立者)"));
						if(lost.length()>2){
							msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "<br>分析後無法識別的文字為: "+lost));
						}
					}else{
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已儲存並且通知 "+x+"位同仁(包含建立者)"));
						if(lost.length()>2){
							msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "<br>分析後無法識別的文字為: "+lost));
						}
					}								
				}catch(Exception e){
					e.printStackTrace();
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存失敗"));					
				}
			}
		}		
		if(!msg.isEmpty()){
			saveMessages(request, msg);
		}
		if(!error.isEmpty()){
			saveErrors(request, error);
		}		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {		
		//HttpSession session = request.getSession(false);
		//System.out.println("sessionCount"+session.getAttribute("sessionCount"));
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm f=(DynaActionForm) form;
		String delOid[]=f.getStrings("delOid");
		HttpSession session = request.getSession(false);
		UserCredential cr=(UserCredential) session.getAttribute("Credential");
		
		Map pub=null;
		
		for(int i=0; i<delOid.length; i++){
			if(!delOid[i].equals("")){
				pub=manager.ezGetMap("SELECT * FROM Calendar WHERE Oid="+delOid[i]);
				manager.executeSql("DELETE FROM Calendar WHERE Oid="+pub.get("Oid"));
				//manager.executeSql("DELETE FROM PubCalendar WHERE no="+pub.get("no"));
			}			
		}
		
		ActionMessages msg = new ActionMessages();		
		if(!pub.get("sender").equals(cr.getMember().getAccount())){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已刪除"));
			saveMessages(request, msg);	
			return unspecified(mapping, form, request, response);
		}
		
		manager.executeSql("DELETE FROM Calendar WHERE no="+pub.get("no"));//刪除所有相同編號的
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		try{								
			Map map=manager.deleteCalendar(pub.get("members").toString(), cr.getMember().getAccount(), pub.get("date").toString(), pub.get("time").toString(), 
			pub.get("place").toString(), pub.get("name").toString(), pub.get("note").toString());
			
			int x=Integer.parseInt(map.get("count").toString());
			String lost="";
			try{
				lost=map.get("lost").toString();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(x>0){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已刪除並且通知 "+x+"位同仁(包含建立者)"));
				if(lost.length()>2){
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "<br>分析後無法識別的文字為: "+lost));
				}
			}else{
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已刪除並且通知 "+x+"位同仁(包含建立者)"));
				if(lost.length()>2){
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "<br>分析後無法識別的文字為: "+lost));
				}
			}					
			
		}catch(Exception e){
			e.printStackTrace();
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存失敗"));					
		}
		//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已刪除"));
		saveMessages(request, msg);		
		return unspecified(mapping, form, request, response);
	}

	
	
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Delete", "delete");
		return map;
	}

}
