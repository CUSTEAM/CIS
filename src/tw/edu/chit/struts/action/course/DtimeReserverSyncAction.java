package tw.edu.chit.struts.action.course;

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

import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeReserveReady;
import tw.edu.chit.model.Seld;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 課程規劃轉檔
 * @author JOHN
 *
 */
public class DtimeReserverSyncAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		
		
		
		setContentPage(request.getSession(false), "course/DtimeReserverSync.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward ok(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm cForm = (DynaActionForm) form;
		String depart_class=cForm.getString("depart_class");
		String year=cForm.getString("year");
		String term=cForm.getString("term");
		
		
		if(year.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "資料完整才能繼續"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		//刪排課
		manager.executeSql("DELETE FROM Dtime_class WHERE Dtime_oid IN(SELECT Oid FROM Dtime WHERE depart_class LIKE '"+depart_class+"%' AND Sterm='"+term+"')");
		//刪多教師
		manager.executeSql("DELETE FROM Dtime_teacher WHERE Dtime_oid IN(SELECT Oid FROM Dtime WHERE depart_class LIKE '"+depart_class+"%' AND Sterm='"+term+"')");
		//刪選課規則
		manager.executeSql("DELETE FROM Opencs WHERE Dtime_oid IN(SELECT Oid FROM Dtime WHERE depart_class LIKE '"+depart_class+"%' AND Sterm='"+term+"')");
		//刪課程主檔
		manager.executeSql("DELETE FROM Dtime WHERE depart_class LIKE '"+depart_class+"%' AND Sterm='"+term+"'");
						
		//課程主檔
		manager.executeSql("INSERT INTO Dtime(Oid,Sterm, depart_class,cscode,techid,opt,credit,thour,Select_Limit,extrapay)" +
				"SELECT Oid+12000,term,depart_class,cscode,techid,opt,credit,thour,Select_Limit," +
				"cyber FROM Dtime_reserve_ready WHERE year='"+year+"' AND term='"+term+"' AND depart_class LIKE '"+depart_class+"%'");
		//排課
		manager.executeSql("INSERT INTO Dtime_class (Dtime_oid,begin,end,place,week)" +
				"SELECT Dtime_reserve_ready_oid+12000,begin,end,place,week FROM Dtime_reserve_class WHERE " +
				"Dtime_reserve_ready_oid IN(SELECT Oid FROM Dtime_reserve_ready WHERE " +
				"year='"+year+"' AND term='"+term+"' AND depart_class LIKE '"+depart_class+"%')");
		//多教師
		manager.executeSql("INSERT INTO Dtime_teacher(Dtime_oid,teach_id,hours,teach,fillscore)SELECT " +
				"Dtime_reserve_oid+12000,teach_id,hours,teach,fillscore FROM " +
				"Dtime_reserve_teacher WHERE Oid IN(SELECT Oid FROM " +
				"Dtime_reserve_ready WHERE year='"+year+"' AND term='"+term+"' AND depart_class LIKE '"+depart_class+"%')");
		//選課規則
		manager.executeSql("INSERT INTO Opencs(depart_class,cscode,Cidno,Sidno,Didno,Grade,Dtime_oid,ClassNo)" +
				"SELECT depart_class,cscode,Cidno,Sidno,Didno,Grade,Dtime_oid+12000, ClassNo FROM " +
				"Dtime_reserve_opencs WHERE Dtime_oid IN(SELECT Oid FROM Dtime_reserve_ready WHERE year='"+year+"' AND " +
				"term='"+term+"' AND depart_class LIKE '"+depart_class+"%')");
		
		//更正選別
		manager.executeSql("UPDATE Dtime SET opt='1' WHERE Sterm='"+term+"' AND depart_class LIKE'"+depart_class+"%' AND opt IN('1', '2', '3')");
		manager.executeSql("UPDATE Dtime SET opt='2' WHERE Sterm='"+term+"' AND depart_class LIKE'"+depart_class+"%' AND opt IN('4', '5', '6')");
		
		
		List list=manager.ezGetBy("SELECT c.ClassName, cs.chi_name, d.credit, d.thour, d.opt, e.cname, " +
				"(SELECT COUNT(*) FROM Dtime_class WHERE Dtime_class.Dtime_oid=d.Oid) as cnt, " +
				"(SELECT COUNT(*) FROM Opencs WHERE Opencs.Dtime_oid=d.Oid) as cnt1 FROM Dtime " +
				"d LEFT OUTER JOIN empl e ON d.techid=e.idno, Csno cs, Class c WHERE " +
				"d.cscode=cs.cscode AND c.ClassNo=d.depart_class AND d.Sterm='"+term+"' AND " +
				"d.depart_class LIKE'"+depart_class+"%' ORDER BY d.depart_class");
		
		session.setAttribute("reserve", list);		
		return mapping.findForward("Main");
	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("OK", "ok");
		return map;
	}

}
