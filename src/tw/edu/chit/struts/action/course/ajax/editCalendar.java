package tw.edu.chit.struts.action.course.ajax;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Member;
import tw.edu.chit.model.PubCalendar;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

/**
 * 
 * @author JOHN
 *
 */
public class editCalendar extends BaseAction{
		
		public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
			CourseManager manager = (CourseManager) getBean("courseManager");
			HttpSession session = request.getSession(false);
			Member me = getUserCredential(request.getSession(false)).getMember();
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
		String method=request.getParameter("method");
		String start_date=request.getParameter("start_date");
		String end_date=request.getParameter("end_date");
		String details=new String(request.getParameter("details").getBytes("iso-8859-1"),"utf-8");
		String members=new String(request.getParameter("members").getBytes("iso-8859-1"),"utf-8");
		String rec_type=new String(request.getParameter("rec_type").getBytes("iso-8859-1"),"utf-8");
		String name=new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");
		String event_length=request.getParameter("event_length");
		String id=request.getParameter("id");
		if(id.indexOf("#")>0){
			id=id.substring(0 ,request.getParameter("id").indexOf("#"));
		}
		
		PubCalendar c;
		
		//修改
		if(method.equals("changeEvent")){
			try{
				System.out.println(id);
				//擁有者，砍了自己和所有人，再塞一份新的給自己和所有人
				c=(PubCalendar) manager.hqlGetBy("FROM PubCalendar WHERE no='"+id+"' AND sender='"+me.getAccount()+"'").get(0);//
				c.setOid(null);
				c.setBegin(sf.parse(start_date));
				c.setEnd(sf.parse(end_date));
				c.setNote(details);
				c.setMembers(members);
				c.setName(name);
				
				if(!rec_type.equals("")&&!rec_type.equals("undefined")){
					//若為重複型態
					c.setRec_type(rec_type);
					c.setRec_event_length(Integer.parseInt(event_length));
				}
				
				manager.executeSql("DELETE FROM Calendar WHERE no='"+id+"'");
				if(!members.trim().equals("")){
					List list=manager.analyseEmplToList(members);
					for(int i=0; i<list.size(); i++){
						c.setAccount(((Map)list.get(i)).get("idno").toString());
						System.out.println(c.getNo());
						manager.updateObject(c);
						c.setOid(null);
					}
				}
				c.setAccount(me.getAccount());//自己
				manager.updateObject(c);
				
			}catch(Exception e){
				e.printStackTrace();
				//非擁有者，砍了自己，再塞一份給自己
				try{
					c=(PubCalendar) manager.hqlGetBy("FROM PubCalendar WHERE no='"+id+"' AND account='"+me.getAccount()+"'").get(0);
					c.setOid(null);
					manager.executeSql("DELETE FROM Calendar WHERE no='"+id+"' AND account='"+me.getAccount()+"'");
					c.setBegin(sf.parse(start_date));
					c.setEnd(sf.parse(end_date));
					c.setNote(details);
					c.setMembers(members);
					c.setName(name);
					//if(!rec_type.equals("")&&!rec_type.equals("undefined")){c.setRec_type(rec_type);}//重複型態					
					manager.updateObject(c);
				}catch(Exception e1){
					
				}
				
			}
			
		}
		
		//新增
		if(method.equals("addEvent")){
			c=new PubCalendar();
			c.setBegin(sf.parse(start_date));
			c.setEnd(sf.parse(end_date));
			c.setNote(details);
			c.setBegin(sf.parse(start_date));
			c.setEnd(sf.parse(end_date));
			c.setNote(details);
			c.setMembers(members);
			c.setName(name);
			c.setSender(me.getAccount());
			//c.setNo(String.valueOf(manager.ezGetInt("SELECT no FROM Calendar ORDER BY no DESC LIMIT 1")+1));
			c.setNo(id);
			
			if(!rec_type.equals("")&&!rec_type.equals("undefined")){
				//若為重複型態要設定時間長度
				c.setRec_type(rec_type);
				c.setRec_event_length(Integer.parseInt(event_length));
			}
			
			List list=manager.analyseEmplToList(members);
			if(list.size()>0)
			for(int i=0; i<list.size(); i++){
				c.setAccount(((Map)list.get(i)).get("idno").toString());
				manager.updateObject(c);
				c.setOid(null);
			}
			
			c.setAccount(me.getAccount());//自己
			manager.updateObject(c);
		}
		
		
		
		if(method.equals("deleteEvent")){
			manager.executeSql("DELETE FROM Calendar WHERE no='"+id+"' AND account='"+me.getAccount()+"'");			
		}
		
		PrintWriter out=response.getWriter();
		out.println("<data>");
		out.println("<msg>finish</msg>");
		out.println("</data>");
		out.close();
		
		return null;
	}
}
