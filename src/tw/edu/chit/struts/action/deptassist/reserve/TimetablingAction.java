package tw.edu.chit.struts.action.deptassist.reserve;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.DtimeReserveClass;
import tw.edu.chit.model.DtimeReserveReady;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 排課管理
 * @author shawn
 *
 */
public class TimetablingAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		setContentPage(request.getSession(false), "assistant/Timetabling.jsp");		
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
		/*
		String grade="";		
		int tmp;
		
		try{
			depart_class=dForm.getString("classLess");
			grade=manager.ezGetString("SELECT Grade FROM Class WHERE ClassNo='"+depart_class+"'");
			//若要規劃上期的課表，一定加1學年
			
			if(term.equals("1")){
				tmp=manager.getSchoolYear()+2;
			}else{
				tmp=manager.getSchoolYear()+1;
			}			
			tmp=tmp-Integer.parseInt(grade);		
			year=String.valueOf(tmp);
			
		}catch(Exception e){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "班級資訊無法找到相對應的入學年度"));
			saveErrors(request, error);
		}
		
		if(year.trim().equals("")||depart_class.length()<5){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定入學年及科系"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		*/
		
		request.setAttribute("allClass", manager.ezGetBy("SELECT dc.Oid as dcOid, d.Oid as dtOid, d.techid, e.cname, c.cscode, c.chi_name, dc.* " +
				"FROM (Dtime_reserve_ready d LEFT OUTER JOIN empl e ON d.techid=e.idno), " +
				"Dtime_reserve_class dc, Csno c WHERE d.year='" +year+"' AND "+
				"c.cscode=d.cscode AND d.Oid=dc.Dtime_reserve_ready_oid AND " +
				"d.term='"+term+"' AND d.depart_class='"+depart_class+"'"));
		
		request.setAttribute("allCs", manager.ezGetBy("SELECT d.Oid, d.cscode, c.chi_name FROM " +
				"Dtime_reserve_ready d, Csno c " +
				"WHERE d.cscode=c.cscode AND " +
				"d.term='"+term+"' AND " +
				"d.year='"+year+"' AND " +
				"d.depart_class='"+depart_class+"'"));
		
		return mapping.findForward("Main");
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
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm dForm = (DynaActionForm) form;
		String Oid=dForm.getString("Oid");
		String Dtime_reserve_oid=dForm.getString("Dtime_reserve_oid");
		String techid=dForm.getString("techid");
		String week=dForm.getString("week");
		String begin=dForm.getString("begin");
		String end=dForm.getString("end");
		String place=dForm.getString("place");
		
		String year=dForm.getString("year");
		String term=dForm.getString("term");
		
		//課程空白
		if(Dtime_reserve_oid.equals("")){
			
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請選擇課程"));
			saveErrors(request, error);
			return query(mapping, form, request, response);
		}
		
		//星期節次空白
		if(begin.trim().equals("")||end.trim().equals("")||week.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "星期節次請勿留空白"));
			saveErrors(request, error);
			return query(mapping, form, request, response);
		}
		
		//測試時數前後順序
		if(Integer.parseInt(begin)>Integer.parseInt(end)){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "起始時間設定有誤"));
			saveErrors(request, error);
			return query(mapping, form, request, response);
		}
		
		
		
		DtimeReserveReady rd=(DtimeReserveReady)manager.hqlGetBy("FROM DtimeReserveReady WHERE Oid="+Dtime_reserve_oid).get(0);
		
		
		int n=(Integer.parseInt(end)-Integer.parseInt(begin))+1;//本次排入時數
		int s=0;//已排時數
		
		DtimeReserveClass dc;
		if(Oid.trim().equals("")){
			//新1個排課
			dc=new DtimeReserveClass();
		}else{
			//舊有的排課
			dc=(DtimeReserveClass)manager.hqlGetBy("FROM DtimeReserveClass WHERE Oid="+Oid).get(0);
			//已排入時間			
			List list=manager.ezGetBy("SELECT begin, end FROM Dtime_reserve_class WHERE Dtime_reserve_ready_oid="+dc.getDtimeReserveReadyOid());
			for(int i=0; i<list.size(); i++){
				s=s+(Integer.parseInt(((Map)list.get(i)).get("end").toString())-Integer.parseInt(((Map)list.get(i)).get("begin").toString()))+1;			
			}
		}
		/*
		if((n+s)>rd.getThour()){
			
			//System.out.println(n+s +", "+rd.getThour());
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "排入時數大於開課時數"));
			saveErrors(request, error);
			return query(mapping, form, request, response);
		}
		*/
		
		
		
		dc.setBegin(begin);
		dc.setDtimeReserveReadyOid(Integer.parseInt(Dtime_reserve_oid));
		dc.setEnd(end);
		dc.setPlace(place);
		dc.setWeek(Integer.parseInt(week));	
		
		
		
		//有排定教師要進行檢查
		if(!techid.trim().equals("")){
			/*測試教師時數上限
			if(!manager.checkDtimeReserve(rd.getOid().toString(), techid, rd.getYear().toString(), rd.getTerm(), 
					rd.getThour(), rd.getCredit(), rd.getSelectLimit(), rd.getAdditionType())){
				
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", 
				manager.ezGetString("SELECT cname FROM empl WHERE idno='"+techid+"'")+
				"同仁上限為 "+manager.sumEmplLimit(techid)+"時數，已規劃 "+
				manager.sumDtimeReserve(null, techid, rd.getYear().toString(), rd.getTerm())+
				"時數, 不足以增加本次規劃 "+rd.getThour()+"時數"));	
				saveErrors(request, error);
				dc=null;
				return query(mapping, form, request, response);
			}
			*/
			
			//測試教師衝堂
			List retechs=manager.checkTeacher(techid, week, begin, rd.getTerm(), rd.getOid().toString(), year, term);
			retechs.addAll(manager.checkTeacher(techid, week, end, rd.getTerm(), rd.getOid().toString(), year, term));
			if(retechs.size()>0){			
				ActionMessages error = new ActionMessages();
				String cname=manager.ezGetString("SELECT cname FROM empl WHERE idno='"+techid+"'");
				for(int i=0; i<retechs.size(); i++){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", cname+"老師, 星期"+((Map)retechs.get(i)).get("week")+", 第"+((Map)retechs.get(i)).get("begin")+"節已有排定課程。"));
				}
				saveErrors(request, error);
				return query(mapping, form, request, response);
			}
		}
		
		//有排定教室要進行檢查
		if(!place.trim().equals("")){
			List reooms=manager.checkRoom(place, week, begin, rd.getTerm(), rd.getOid().toString(), year, term);
			reooms.addAll(manager.checkRoom(place, week, end, rd.getTerm(), rd.getOid().toString(), year, term));
			if(reooms.size()>0){			
				ActionMessages error = new ActionMessages();			
				for(int i=0; i<reooms.size(); i++){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", 
							((Map)reooms.get(i)).get("place")+"教室, 星期"+((Map)reooms.get(i)).get("week")+", 第"+((Map)reooms.get(i)).get("begin")+"節已有排定課程。"));
				}
				saveErrors(request, error);
				dc.setPlace(null);
				return query(mapping, form, request, response);
			}
		}
		
		//無誤儲存
		rd.setTechid(techid);
		manager.updateObject(rd);
		manager.updateObject(dc);		
		return query(mapping, form, request, response);
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm dForm = (DynaActionForm) form;
		String Oid=dForm.getString("Oid");
		manager.executeSql("DELETE FROM Dtime_reserve_class WHERE Oid="+Oid);
		
		
		return query(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");		
		map.put("Save", "save");
		map.put("Delete", "delete");
		return map;
	}
}
