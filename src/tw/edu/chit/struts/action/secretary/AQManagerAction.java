package tw.edu.chit.struts.action.secretary;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Regstime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
/**
 * 成績管理
 * @author JOHN
 * TODO evgr_time
 */
public class AQManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			   HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			HttpSession session = request.getSession(false);
			CourseManager manager = (CourseManager) getBean("courseManager");
			
			try{
				request.setAttribute("AQStart", manager.convertDate(manager.ezGetString("SELECT Value FROM Parameter WHERE Name='AQStart'")));
				request.setAttribute("AQEnd", manager.convertDate(manager.ezGetString("SELECT Value FROM Parameter WHERE Name='AQEnd'")));
				
			}catch(Exception e){
				ActionMessages error=new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "日期尚未設定"));
				saveErrors(request, error);
			}
			
			//session.setAttribute("myClass", myClass);			
			setContentPage(request.getSession(false), "secretary/AQManager.jsp");
			return mapping.findForward("Main");
	}
	
	
	public ActionForward save(ActionMapping mapping, ActionForm form, 
			   HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			HttpSession session = request.getSession(false);
			CourseManager manager = (CourseManager) getBean("courseManager");
			
			DynaActionForm f=(DynaActionForm) form;
			String start=f.getString("AQStart");
			String end=f.getString("AQEnd");
			
			try{				
				manager.executeSql("UPDATE Parameter SET Value='"+manager.convertDate(start)+"' WHERE Name='AQStart'");
				manager.executeSql("UPDATE Parameter SET Value='"+manager.convertDate(end)+"' WHERE Name='AQEnd'");
				
			}catch(Exception e){
				ActionMessages error=new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "百分比欄位空白不利於辨識，請儘可能填入數字並且相加結果為100"));
				saveErrors(request, error);
			}
			
			return unspecified(mapping, form, request, response);
	}
	
	public ActionForward print(ActionMapping mapping, ActionForm form, 
			   HttpServletRequest request, HttpServletResponse response)throws Exception {
			
		CourseManager manager = (CourseManager) getBean("courseManager");		
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=SpoRat.xls");
		
		List list=manager.ezGetBy("SELECT e.cname, c.name, a.* FROM AQ_anser a, empl e, CodeEmpl c WHERE c.idno=e.unit AND a.idno=e.idno");
		PrintWriter out = response.getWriter();
		out.println("<table width='100%'>  ");
		out.println("  <tr>");
		out.println("		<td align='center'>姓名</td>");
		out.println("		<td align='center'>單位</td>");
		out.println("		<td align='center'>兼行政</td>");
		out.println("		<td align='center'>年資</td>");		
		out.println("		<td align='center'>問卷代碼</td>");
		out.println("		<td align='center'>系所代碼</td>");
		out.println("		<td align='center' colspan='20'>答案</td>");
		out.println("  </tr>");
		
		
		char[] ans;
		for(int i=0; i<list.size(); i++){
			//if(((Map)list.get(i)).get("anser").toString().trim().equals(""))continue;
			
			if(i%2==1){
				out.println("  <tr>");
			}else{
				out.println("  <tr bgColor='#dddddd'>");
			}
			
			out.println("		<td align='left'>"+((Map)list.get(i)).get("cname")+"</td>");
			out.println("		<td align='left'>"+((Map)list.get(i)).get("name")+"</td>");
			
			if(((Map)list.get(i)).get("cname").equals("0")){
				out.println("		<td align='center'></td>");
			}else{
				out.println("		<td align='center'>有</td>");
			}
			
			
			
			out.println("		<td align='right'>"+((Map)list.get(i)).get("years")+"</td>");		
			out.println("		<td align='right'>"+((Map)list.get(i)).get("uid")+"</td>");
			
			if(((Map)list.get(i)).get("uid2")==null){
				out.println("		<td align='center'></td>");
			}else{
				out.println("		<td align='center'>"+((Map)list.get(i)).get("uid2")+"</td>");
			}
			
			
			try{
				ans=((Map)list.get(i)).get("anser").toString().toCharArray();
				for(int j=0; j<ans.length; j++){
					out.println("		<td>"+ans[j]+"</td>");
				}
			}catch(Exception e){
				out.println("		<td></td>");
			}
			
			
			
			
			out.println("  </tr>");
			
		}
		
		
		out.println("</table>");
		out.println("</html>");
		out.close();
		
		return null;
	}
	

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("SetQuestDate", "save");
		map.put("Print", "print");
		return map;
	}

}