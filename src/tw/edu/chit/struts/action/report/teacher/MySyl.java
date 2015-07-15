package tw.edu.chit.struts.action.report.teacher;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

public class MySyl extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		//HttpSession session = request.getSession(false);
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();	
		
		out.println("<pront>");
		
		try{
			String soid=request.getParameter("soid");
			Map dt=manager.ezGetMap("SELECT Syllabi, Syllabi_sub FROM Savedtime WHERE Oid="+soid);			
			Map map;
			if(dt.get("Syllabi")!=null&&!dt.get("Syllabi").equals("")){
				
				map=manager.parseSyl(manager.replaceChar4XML((String)dt.get("Syllabi")));
				
				if(!map.get("obj").equals("")){
					out.println("<obj>"+map.get("obj")+"</obj>");
				}else{
					out.println("<obj>無資料</obj>");
				}
				
				if(!map.get("syl").equals("")){
					out.println("<syl>"+map.get("syl")+"</syl>");
				}else{
					out.println("<pre>無資料</pre>");
				}
				
				if(!map.get("pre").equals("")){
					out.println("<pre>"+map.get("pre")+"</pre>");
				}else{
					out.println("<pre>無資料</pre>");
				}
				
				
			}else{
				out.println("<obj>無資料</obj>");
				out.println("<syl>無資料</syl>");
				out.println("<pre>無資料</pre>");
			}
			
			
			
			if(dt.get("Syllabi")!=null&&!dt.get("Syllabi").equals("")){
				List list=manager.parseSyls(manager.replaceChar4XML((String)dt.get("Syllabi_sub")));
				
				for(int i=0; i<list.size(); i++){
					try{
						
						
						if(((Map)list.get(i)).get("week").equals("")){
							out.println("<week>無資料</week>");
						}else{
							out.println("<week>"+((Map)list.get(i)).get("week")+"</week>");
						}
						
						if(((Map)list.get(i)).get("topic").equals("")){
							out.println("<topic>無資料</topic>");
						}else{
							out.println("<topic>"+((Map)list.get(i)).get("topic")+"</topic>");
						}
						
						if(((Map)list.get(i)).get("content").equals("")){
							out.println("<content>無資料</content>");
						}else{
							out.println("<content>"+((Map)list.get(i)).get("content")+"</content>");
						}
						
						if(((Map)list.get(i)).get("hours").equals("")){
							out.println("<hours>無資料</hours>");
						}else{
							out.println("<hours>"+((Map)list.get(i)).get("hours")+"</hours>");
						}						
						
					}catch(Exception e){
						out.println("<week>無資料</week>");
						out.println("<topic>無資料</topic>");
						out.println("<content>無資料</content>");
						out.println("<hours>無資料</hours>");
					}
				}
			}else{
				
				out.println("<week>無資料</week>");
				out.println("<topic>無資料</topic>");
				out.println("<content>無資料</content>");
				out.println("<hours>無資料</hours>");
			}
			
			
			
			
			
			
			//String Syllabi=manager.ezGetString("SELECT Introduction FROM Savedtime WHERE Oid="+soid);
			//System.out.println(str);
			//Map map=manager.parseSyl(manager.replaceChar4XML(str));
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			out.println("<obj>無資料</obj>");
			out.println("<syl>無資料</syl>");
			out.println("<pre>無資料</pre>");
			out.println("<week>無資料</week>");
			out.println("<topic>無資料</topic>");
			out.println("<content>無資料</content>");
			out.println("<hours>無資料</hours>");
		}
		out.println("</pront>");
		out.close();
		
		return null;
	}

}
