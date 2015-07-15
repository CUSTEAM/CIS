package tw.edu.chit.struts.action.score;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import tw.edu.chit.model.Optime1;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 成績開放查詢設定
 * @author JOHN
 *
 */
public class SetViewDateAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","各種型態的開放日期均分為: 日間部、夜間部、進修學院"));
		saveMessages(request, msg);
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		
		List group=manager.ezGetBy("SELECT depart FrOM optime1 GROUP BY depart");		
		List myTime=new ArrayList();
		
		for(int i=0; i<group.size(); i++){
			
			List list=manager.ezGetBy("SELECT SUBSTRING(c.name, 3) as c5name, o.* FROM optime1 o, code5 c " +
			"WHERE o.depart=c.idno AND c.category='SchoolType' AND o.depart='"+((Map)group.get(i)).get("depart")+"'");			
			
			Map map;
			List allDate=new ArrayList();
			for(int j=0; j<list.size(); j++){
				map=new HashMap();
				map.put("c5name", ((Map)list.get(j)).get("c5name"));				
				
				map.put("level", chLevel(((Map)list.get(j)).get("level").toString().charAt(0)));
				map.put("depart", ((Map)list.get(j)).get("depart"));
				map.put("begin_date", ((Map)list.get(j)).get("begin_date"));
				map.put("begin_time", ((Map)list.get(j)).get("begin_time"));
				map.put("end_date", ((Map)list.get(j)).get("end_date"));
				map.put("end_time", ((Map)list.get(j)).get("end_time"));
				map.put("Oid", ((Map)list.get(j)).get("Oid"));
				if(((Map)list.get(j)).get("view_date")!=null){
					map.put("view_date", manager.convertDate(((Map)list.get(j)).get("view_date").toString()));
				}else{
					map.put("view_date", "");
				}
				
				allDate.add(map);
			}
			
			map=new HashMap();
			//System.out.println("SELECT name FROM code5 WHERE category='School' AND idno LIKE '"+((Map)group.get(i)).get("depart").toString().subSequence(1, 2)+"%'");
			
			/*
			map.put("depart", manager.ezGetBy("SELECT c2.name as name FROM code5 as c1, code5 as c2 " +
					"WHERE SUBSTRING(c2.idno, 1, 1)=SUBSTRING(c1.idno, 2, 2) AND c2.category='School' AND " +
					"c1.category='SchoolType' AND c1.idno LIKE '"+((Map)group.get(i)).get("depart")+"%'"));
			
			*/
			
			map.put("depart", manager.ezGetBy("SELECT name FROM code5 WHERE category='School' AND idno LIKE '"+((Map)group.get(i)).get("depart").toString().subSequence(1, 2)+"%'"));
			map.put("departName", ((Map)allDate.get(0)).get("c5name"));
			map.put("departNo", ((Map)group.get(i)).get("depart"));
			map.put("list", allDate);
			
			myTime.add(map);
			
		}		
		
		session.setAttribute("myTime", myTime);
		
		
		
		
		
		setContentPage(request.getSession(false), "score/SetViewDate.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 新增學制
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//TODO 要殺頭了來不及
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm setForm = (DynaActionForm) form;
		
		
		//String createSeldType = (String) setForm.get("createSeldType");
		
		
		
		return mapping.findForward("Main");
		
	}
	
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm setForm = (DynaActionForm) form;
		
		//String level[] = (String[]) setForm.get("level");
		//String depart[] = (String[]) setForm.get("depart");
		//String begin_date[] = (String[]) setForm.get("begin_date");
		//String begin_time[] = (String[]) setForm.get("begin_time");
		//String end_date[] = (String[]) setForm.get("end_date");
		//String end_time[] = (String[]) setForm.get("end_time");
		String Oid[] = (String[]) setForm.get("Oid");
		String view_date[] = (String[]) setForm.get("view_date");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		
		for(int i=0; i<Oid.length; i++){
			
			try{
				Optime1 optime=(Optime1)manager.hqlGetBy("FROM Optime1 WHERE Oid="+Oid[i]).get(0);
				Date date=sf.parse(manager.convertDate(view_date[i]));
				optime.setViewDate(date);
				manager.updateObject(optime);
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return unspecified(mapping, form, request, response);		
	}
	
	private String chLevel(char c){
		String level="";
		
		switch(c){
			case '1':
				level="期中考";
				return level;
				
			case '2':
				level="期末考";
				return level;
				
			case '3':
				level="畢業考";
				return level;
				
			case '4':
				level="暑修";
				return level;
				
			case '5':
				level="操性";
				return level;
				
			case '6':
				level="教學評量";
				return level;
				
			default:
				return "這是什麼?";
		}
	}
	
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Save", "save");
		return map;
	}

}
