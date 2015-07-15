package tw.edu.chit.struts.action.summer;

import java.text.DateFormat;
import java.text.ParseException;
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

import tw.edu.chit.model.Sweek;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

/**
 * @author JOHN
 */
public class SetSummerVactionAction extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		//SummerManager summerManager = (SummerManager) getBean("summerManager");		
		CourseManager manager=(CourseManager)getBean("courseManager");
		
		
		
		
		
		
		List list=manager.ezGetBy("SELECT s.Oid, s.daynite, c.name, s.seqno, wdate FROM Sweek s, code5 c WHERE " +
				"c.category='Summer' AND c.idno=s.daynite");

		List table=new ArrayList();
		Map map;
		//DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		//Date date;
		for(int i=0; i<list.size(); i++){

			map=new HashMap();
			map.put("Oid", ((Map)list.get(i)).get("Oid"));
			map.put("daynite", ((Map)list.get(i)).get("daynite"));
			map.put("name", ((Map)list.get(i)).get("name"));
			map.put("seqno", ((Map)list.get(i)).get("seqno"));			
			map.put("wdate", manager.convertDate(((Map)list.get(i)).get("wdate").toString()));
			table.add(map);
		}
		
		session.setAttribute("sweek", table);
		
		
		
		
		
		
		//session.setAttribute("sweek", summerManager.getSweekBy("", "", ""));
		session.setAttribute("allSchool", manager.ezGetBy("SELECT idno as no, name as name FROM code5 WHERE category='Summer'"));
		
		setContentPage(request.getSession(false), "summer/SetSummerVaction.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 儲存
	 */
	//TODO 此oid不被任何表參考，此oid無意義。如果強行參考可能會造成錯誤!
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		DynaActionForm sform = (DynaActionForm) form;
		SummerManager summerManager = (SummerManager) getBean("summerManager");		
		CourseManager manager=(CourseManager)getBean("courseManager");
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		String Oid[] = (String[]) sform.get("Oid"); 
		String daynite[] = (String[]) sform.get("daynite"); 
		String seqno[] = (String[]) sform.get("seqno"); 
		String wdate[] = (String[]) sform.get("wdate"); 
		String dayniteName[] = (String[]) sform.get("dayniteName"); 
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		try{
			Sweek sweek;
			for(int i=0; i<wdate.length; i++){
				if(!Oid[i].trim().equals("")){
					//summerManager.delAnything("Sweek", Oid[i]);
					manager.executeSql("DELETE FROM Sweek WHERE Oid='"+Oid[i]+"'");
				}
				
				if((!daynite[i].trim().equals("")&& 
						!seqno[i].trim().equals("")&& 
						!wdate[i].trim().equals("")&&
						!dayniteName[i].trim().equals(""))){
					
					sweek=new Sweek();
					sweek.setDaynite(daynite[i]);
					sweek.setSeqno(Short.parseShort(seqno[i]));
					sweek.setWdate(sf.parse(manager.convertDate(wdate[i])));
					summerManager.saveObj(sweek);
				}	
				
			}
		}catch(Exception e){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","學制/梯次 重複建立!"));
			saveErrors(request, error);
		}
		
		
		
		//session.setAttribute("sweek", summerManager.getSweekBy("", "", ""));
		//setContentPage(request.getSession(false), "summer/SetSummerVaction.jsp");
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 重設
	 */
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		setContentPage(request.getSession(false), "summer/SetSummerVaction.jsp");
		//return mapping.findForward("Main");
		return unspecified(mapping, form, request, response);
	}

	
	protected Map getKeyMethodMap() {
		
		Map map = new HashMap();
		map.put("Clear", "clear");
		map.put("Save", "save");
		return map;
	}

}
