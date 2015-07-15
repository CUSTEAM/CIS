package tw.edu.chit.struts.action.deptassist.reserve;

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

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeReserve;
import tw.edu.chit.model.DtimeReserveClass;
import tw.edu.chit.model.DtimeReserveReady;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

/**
 * 排課管理
 * @author shawn
 *
 */
public class FestTimetablingAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		CourseManager manager = (CourseManager) getBean("courseManager");
		setContentPage(request.getSession(false), "assistant/FestTimetabling.jsp");		
		return mapping.findForward("Main");
	}
	
	/**
	 * 查詢
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm dForm = (DynaActionForm) form;
		String year=dForm.getString("year");	
		String term=dForm.getString("term");	
		String depart_class=dForm.getString("classLess");
		
		
		
		//System.out.println(dForm.getString("greades"));
		
		
		if(year.trim().equals("")||depart_class.length()<5){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定入學年及科系"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		request.setAttribute("allClass", manager.ezGetBy("SELECT dc.Oid as dcOid, d.Oid as dtOid, d.techid, e.cname, c.cscode, c.chi_name, dc.* " +
				"FROM (Dtime_reserve_ready d LEFT OUTER JOIN empl e ON d.techid=e.idno), " +
				"Dtime_reserve_class dc, Csno c WHERE d.year='" +year+"' AND "+
				"c.cscode=d.cscode AND d.Oid=dc.Dtime_reserve_ready_oid AND " +
				"d.term='"+term+"' AND d.depart_class='"+depart_class+"' ORDER BY dc.week, dc.begin"));
		
		
		request.setAttribute("allCs", manager.ezGetBy("SELECT d.Oid, d.cscode, c.chi_name FROM " +
				"Dtime_reserve_ready d, Csno c " +
				"WHERE d.cscode=c.cscode AND " +
				"d.term='"+term+"' AND " +
				"d.year='"+year+"' AND " +
				"d.depart_class='"+depart_class+"' ORDER BY d.cscode"));
		
		return mapping.findForward("Main");
	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm dForm = (DynaActionForm) form;
		
		String Oid[]=dForm.getStrings("Oid");
		String Dtime_reserve_oid[]=dForm.getStrings("Dtime_reserve_oid");
		String techid[]=dForm.getStrings("techid");
		String week[]=dForm.getStrings("week");
		String begin[]=dForm.getStrings("begin");
		String end[]=dForm.getStrings("end");
		String place[]=dForm.getStrings("place");
		String year=dForm.getString("year");	
		String term=dForm.getString("term");
		DtimeReserveClass dc;
		DtimeReserveReady rd;

		//新增
		if(!Dtime_reserve_oid[0].equals("")){
			if(Integer.parseInt(begin[0])>Integer.parseInt(end[0])){
				ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "時序倒置"));
				saveErrors(request, error);
				return query(mapping, form, request, response);
			}			
			rd=(DtimeReserveReady)manager.hqlGetBy("FROM DtimeReserveReady WHERE Oid="+Dtime_reserve_oid[0]).get(0);						
			
			dc=new DtimeReserveClass();
			rd=(DtimeReserveReady)manager.hqlGetBy("FROM DtimeReserveReady WHERE Oid="+Dtime_reserve_oid[0]).get(0);
			
			dc.setBegin(begin[0]);
			dc.setDtimeReserveReadyOid(Integer.parseInt(Dtime_reserve_oid[0]));
			dc.setEnd(end[0]);
			
			dc.setWeek(Integer.parseInt(week[0]));
			
			if(!techid[0].trim().equals("")){
				
				if(!manager.checkDtimeReserve(rd.getOid().toString(), techid[0], rd.getYear().toString(), rd.getTerm(), 
						rd.getThour(), rd.getCredit(), rd.getSelectLimit(), rd.getAdditionType())){
					
					ActionMessages error = new ActionMessages();
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", 
					manager.ezGetString("SELECT cname FROM empl WHERE idno='"+techid+"'")+
					"同仁上限為 "+manager.sumEmplLimit(techid[0])+"時數，已規劃 "+
					manager.sumDtimeReserve(null, techid[0], rd.getYear().toString(), rd.getTerm())+
					"時數, 不足以增加本次規劃 "+rd.getThour()+"時數"));	
					saveErrors(request, error);
					return query(mapping, form, request, response);
				}
				
				//測試教師衝堂
				List retechs=manager.checkTeacher(techid[0], week[0], begin[0], rd.getTerm(), rd.getOid().toString(), year, term);
				retechs.addAll(manager.checkTeacher(techid[0], week[0], end[0], rd.getTerm(), rd.getOid().toString(), year, term));
				if(retechs.size()>0){
					ActionMessages error = new ActionMessages();
					String cname=manager.ezGetString("SELECT cname FROM empl WHERE idno='"+rd.getTechid()+"'");
					for(int j=0; j<retechs.size(); j++){
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", cname+"老師, 星期"+((Map)retechs.get(j)).get("week")+", 第"+((Map)retechs.get(j)).get("begin")+"節已有排定課程。"));
					}
					saveErrors(request, error);
					return query(mapping, form, request, response);
				}
			}

			//測試教室衝堂
			if(!place[0].trim().equals("")){
				List reooms=manager.checkRoom(place[0], week[0], begin[0], rd.getTerm(), rd.getOid().toString(), year, term);
				reooms.addAll(manager.checkRoom(place[0], week[0], end[0], rd.getTerm(), rd.getOid().toString(), year, term));
				if(reooms.size()>0){			
					ActionMessages error = new ActionMessages();			
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", 
							place[0]+"教室, 星期"+week[0]+", 第"+begin[0]+"節已有排定課程。"));
					saveErrors(request, error);
					dc.setPlace(null);
					return query(mapping, form, request, response);
				}
			}
			
			//無誤儲存			
			rd.setTechid(techid[0]);
			manager.updateObject(rd);
			manager.updateObject(dc);
			rd=null;
			dc=null;
		}
		
		return query(mapping, form, request, response);
	}
	
	/**
	 * 儲存
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm dForm = (DynaActionForm) form;
		
		String Oid[]=dForm.getStrings("Oid");
		String Dtime_reserve_oid[]=dForm.getStrings("Dtime_reserve_oid");
		String techid[]=dForm.getStrings("techid");
		String week[]=dForm.getStrings("week");
		String begin[]=dForm.getStrings("begin");
		String end[]=dForm.getStrings("end");
		String place[]=dForm.getStrings("place");
		String year=dForm.getString("year");	
		String term=dForm.getString("term");
		DtimeReserveClass dc;
		DtimeReserveReady rd;
		
		//修改
		for(int i=1; i<Oid.length; i++){
			
			if(Integer.parseInt(begin[i])>Integer.parseInt(end[i])){
				ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "時序倒置"));
				saveErrors(request, error);
				return query(mapping, form, request, response);
			}
			
			
			
			if(Dtime_reserve_oid[i].equals("")){
				
				String tmp=manager.ezGetString("SELECT dr.Oid FROM Dtime_reserve_ready dr, " +
				"Dtime_reserve_class dc WHERE dr.Oid=dc.Dtime_reserve_ready_oid AND dc.Oid='"+Oid[i]+"'");
				
				manager.executeSql("UPDATE Dtime_reserve_ready SET techid=null WHERE Oid="+tmp);
				manager.executeSql("DELETE FROM Dtime_reserve_class WHERE Oid="+Oid[i]);				
			}else{
				//修改
				rd=(DtimeReserveReady)manager.hqlGetBy("FROM DtimeReserveReady WHERE Oid="+Dtime_reserve_oid[i]).get(0);
				dc=(DtimeReserveClass)manager.hqlGetBy("FROM DtimeReserveClass WHERE Oid="+Oid[i]).get(0);
				
				
				dc.setBegin(begin[i]);
				dc.setDtimeReserveReadyOid(Integer.parseInt(Dtime_reserve_oid[i]));
				dc.setEnd(end[i]);
				dc.setPlace(place[i]);
				dc.setWeek(Integer.parseInt(week[i]));
				//err=false;
				if(!techid[i].trim().equals("")){
					
					if(!manager.checkDtimeReserve(rd.getOid().toString(), techid[i], rd.getYear().toString(), rd.getTerm(), 
							rd.getThour(), rd.getCredit(), rd.getSelectLimit(), rd.getAdditionType())){
						
						ActionMessages error = new ActionMessages();
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", 
						manager.ezGetString("SELECT cname FROM empl WHERE idno='"+techid+"'")+
						"同仁上限為 "+manager.sumEmplLimit(techid[i])+"時數，已規劃 "+
						manager.sumDtimeReserve(null, techid[i], rd.getYear().toString(), rd.getTerm())+
						"時數, 不足以增加本次規劃 "+rd.getThour()+"時數"));	
						saveErrors(request, error);
						//break;
						return query(mapping, form, request, response);
					}
					
					//測試教師衝堂
					List retechs=manager.checkTeacher(techid[i], week[i], begin[i], rd.getTerm(), rd.getOid().toString(), year, term);
					retechs.addAll(manager.checkTeacher(techid[i], week[i], end[i], rd.getTerm(), rd.getOid().toString(), year, term));
					if(retechs.size()>0){
						ActionMessages error = new ActionMessages();
						String cname=manager.ezGetString("SELECT cname FROM empl WHERE idno='"+rd.getTechid()+"'");
						for(int j=0; j<retechs.size(); j++){
							error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", cname+"老師, 星期"+((Map)retechs.get(j)).get("week")+", 第"+((Map)retechs.get(j)).get("begin")+"節已有排定課程。"));
						}
						saveErrors(request, error);
						return query(mapping, form, request, response);
					}
				}

				//測試教室衝堂
				if(!place[i].trim().equals("")){
					List reooms=manager.checkRoom(place[i], week[i], begin[i], rd.getTerm(), rd.getOid().toString(), year, term);
					reooms.addAll(manager.checkRoom(place[i], week[i], end[i], rd.getTerm(), rd.getOid().toString(), year, term));
					//System.out.println(reooms.size());
					if(reooms.size()>0){			
						ActionMessages error = new ActionMessages();			
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", 
								place[i]+"教室, 星期"+week[i]+", 第"+begin[i]+"節已有排定課程。"));
						saveErrors(request, error);
						dc.setPlace(null);
						return query(mapping, form, request, response);
					}
				}
				
				//無誤儲存				
				rd.setTechid(techid[i]);					
				manager.updateObject(rd);
				manager.updateObject(dc);
			}
		}
				
		return query(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");		
		map.put("Save", "save");
		map.put("Add", "add");
		return map;
	}
}
