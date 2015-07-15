package tw.edu.chit.struts.action.student;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
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
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 網路選課
 * @author John
 *
 */
public class MyCsTableAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		
		Date now=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		String student_no=c.getStudent().getStudentNo();
		
		//退選
		if(request.getParameter("delOid")!=null){
			
			Map dtime=manager.ezGetMap("SELECT credit, opt, depart_class FROM Dtime WHERE Oid="+request.getParameter("delOid"));
			float min=Float.parseFloat(((Map) session.getAttribute("schedule")).get("min").toString());
			float credit=Float.parseFloat(dtime.get("credit").toString());		
			int StdSelect = manager.ezGetInt("Select COUNT(e.`Oid`) From Seld e Where e.`Dtime_oid`='"+request.getParameter("delOid")+"' ");//取選課人數
			
			ActionMessages error = new ActionMessages();
			if(dtime.get("opt").equals("1")){//退本班必修					
				if(((Clazz)session.getAttribute("myGrade")).getClassNo().equals(dtime.get("depart_class"))){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "退選本班必修課程需由課務單位處理, "));
					saveErrors(request, error);
				}				
			}
			
			if(((manager.ezGetInt("SELECT SUM(d.credit)FROM Seld s, Dtime d WHERE s.student_no='"+c.getStudent().getStudentNo()+"' AND "+
				"d.Oid=s.Dtime_oid AND d.Sterm='"+((Map) session.getAttribute("schedule")).get("term")+"'")-credit)<min)){//退下限		
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "學分數需符合下限規定, "));				
				saveErrors(request, error);
			}
			
			//第1階段不卡下限
			if(!((Map)session.getAttribute("schedule")).get("level").equals("1")){
				if(!dtime.get("depart_class").toString().substring(2, 3).equals("G")){//
					if(StdSelect<=20){
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "選課人數過低需由課務單位處理, "));
						saveErrors(request, error);
					}				
				}else{
					if(StdSelect<=5){
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "選課人數過低需由課務單位處理, "));
						saveErrors(request, error);
					}
					
				}
			}
			
			if(error.isEmpty()){//無誤刪除
				//System.out.println("class="+dtime.get("depart_class").toString().substring(2, 3)+";select="+StdSelect);
				manager.executeSql("DELETE FROM Seld WHERE Dtime_oid='"+request.getParameter("delOid")+"' AND student_no='"+student_no+"'");
				ActionMessages msg = new ActionMessages();
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","退選完成"));
				saveMessages(request, msg);	
			}else{
				saveErrors(request, error);
			}
		}
		
		//重複用資訊,包含學生虛擬或實體年級、選課期間、「歷年」已選課程(本學期需動態取得)...等選課必要之固定資訊，只取一次存放。		
		Map schedule;
		
		if(session.getAttribute("schedule")==null){
			ServletContext cont=request.getServletContext();
			//一般生比照			
			if(cont.getAttribute("school_term").equals("2")){//下學期需虛擬升級
				
				schedule=manager.ezGetMap("SELECT s.* FROM Class c, Seld_schedule s, stmd st WHERE " +
				"s.grade=c.Grade+1 AND st.depart_class=c.ClassNo AND c.schoolType=s.depart AND " +
				"st.student_no='"+student_no+"' AND begin<'"+sf.format(now)+"' AND end>'"+sf.format(now)+"'");
				//坑爹的下學期選下學期的課
				if(schedule!=null)
				if(schedule.get("term").equals("2")){
					schedule=manager.ezGetMap("SELECT s.* FROM Class c, Seld_schedule s, stmd st WHERE " +
					"s.grade=c.Grade AND st.depart_class=c.ClassNo AND c.schoolType=s.depart AND " +
					"st.student_no='"+student_no+"' AND begin<'"+sf.format(now)+"' AND end>'"+sf.format(now)+"'");
				}				
			}else{
				schedule=manager.ezGetMap("SELECT s.* FROM Class c, Seld_schedule s, stmd st WHERE " +
				"s.grade=c.Grade AND st.depart_class=c.ClassNo AND c.schoolType=s.depart AND " +
				"st.student_no='"+student_no+"' AND begin<'"+sf.format(now)+"' AND end>'"+sf.format(now)+"'");
			}
			
			if(schedule!=null){				
				if(c.getStudent().getDepartClass().indexOf("G")>0){
					//TODO 研究生比照
					schedule.put("max", 22);
					schedule.put("min", 1);
					schedule.put("nor", 10);
					//level=階段
				}
				session.setAttribute("schedule", schedule);
				//歷年已修課程代碼
				session.setAttribute("cshist", manager.ezGetBy("SELECT cscode FROM ScoreHist WHERE student_no='"+
				c.getStudent().getStudentNo()+"' AND score>=60"));
			}
			
			//當下學期時虛擬升級
			Clazz clazz=(Clazz) manager.hqlGetBy("FROM Clazz WHERE ClassNo='"+c.getStudent().getDepartClass()+"'").get(0);			
			if(cont.getAttribute("school_term").equals("2")){
				clazz.setGrade(String.valueOf(Integer.parseInt(clazz.getGrade())+1));
			}
			session.setAttribute("myGrade", clazz);

			//坑爹的特殊班級加通識塔瑪的浪費計算流量
			List seldnor=manager.ezGetBy("SELECT depart_class, max FROM SeldMaxNor");
			for(int i=0; i<seldnor.size(); i++){				
				if(clazz.getClassNo().indexOf(((Map)seldnor.get(i)).get("depart_class").toString())>=0){
					schedule.put("nor", ((Map)seldnor.get(i)).get("max"));
				}
			}
		
		}else{
			//有資訊則重用
			schedule=(Map) session.getAttribute("schedule");
		}
		
		//不重複用資訊，需動態更新資訊
		if(schedule!=null){//若有規則給予選課
			//更新已選課程
			request.setAttribute("allClass", manager.ezGetBy("SELECT dc.Oid as dcOid, d.thour, d.credit, " +
			"d.Oid as dtOid, d.techid, e.cname, c.cscode, c.chi_name,dc.* FROM stmd st, " +
			"Seld s, (Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno), Dtime_class dc, " +
			"Csno c WHERE st.student_no=s.student_no AND s.Dtime_oid=d.Oid AND " +
			"c.cscode=d.cscode AND d.Oid=dc.Dtime_oid AND d.Sterm='"+schedule.get("term")+"' AND s.student_no='"+student_no+"'"));
			
			//更新已選資訊
			Map map=manager.ezGetMap("SELECT SUM(d.credit)as credit, SUM(d.thour)as thour FROM Dtime d, Seld s WHERE " +
			"d.Oid=s.Dtime_oid AND s.student_no='"+student_no+"' AND d.Sterm='"+schedule.get("term")+"'");
			
			request.setAttribute("mycredit", map.get("credit"));
			request.setAttribute("mythour", map.get("thour"));			
		}
		
		setContentPage(request.getSession(false), "student/MyCsTable.jsp");
		return mapping.findForward("Main");
	}
	
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;
		String DtimeOid=sForm.getString("DtimeOid");
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		Map dtime=manager.ezGetMap("SELECT cscode, credit, opt, depart_class FROM Dtime WHERE Oid="+DtimeOid);
		
		//第2階段要檢查人數上限「多人同時搶選、已在頁面上以「亂數延遲」解決，並且證明完全無誤
		if(!((Map)session.getAttribute("schedule")).get("level").equals("1")){
			if(manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE Dtime_oid='"+DtimeOid+"'")>=
				Integer.parseInt(manager.ezGetString("SELECT Select_Limit FROM Dtime WHERE Oid='"+DtimeOid+"'"))){
				ActionMessages error = new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "選課人數已滿"));
				saveErrors(request, error);
				return unspecified(mapping, form, request, response);
			}			
		}		

		//重複選課
		if(manager.ezGetInt("SELECT COUNT(*) FROM Dtime d, Seld s WHERE d.cscode='"+
		dtime.get("cscode")+"' AND s.student_no='"+c.getStudent().getStudentNo()+"'"+
		" AND d.Oid=s.Dtime_oid AND d.Sterm='"+((Map)session.getAttribute("schedule")).get("term")+"'")>0){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "本學期已選相同課程"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}

		//重複修課
		List cshist=(List) session.getAttribute("cshist");
		for(int i=0; i<cshist.size(); i++){
			if(((Map)cshist.get(i)).get("cscode").equals(dtime.get("cscode"))){
				ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "歷年成績已存在相同課程"));
				saveErrors(request, error);
				return unspecified(mapping, form, request, response);
			}
		}
		
		//衝堂
		List dclass=manager.ezGetBy("SELECT week, begin, end FROM Dtime_class WHERE Dtime_oid='"+DtimeOid+"'");
		for(int i=0; i<dclass.size(); i++){
			if(manager.ezGetBy("SELECT s.Oid, s.Dtime_Oid, d.cscode, cs1.ClassName, c.chi_name, cs2.className as className2, ds.week, ds.begin, ds.end, d.thour "+
				"FROM Seld s, Dtime_class ds, Dtime d, Csno c, Class cs1, Class cs2, stmd st "+
				"WHERE s.Dtime_oid=d.Oid AND ds.Dtime_oid=s.Dtime_oid AND s.student_no='"+c.getStudent().getStudentNo()+"' AND sterm='"+((Map)session.getAttribute("schedule")).get("term")+"' AND "+
				"c.cscode=d.cscode AND d.depart_class=cs1.ClassNo AND st.depart_class=cs2.ClassNo AND st.student_no=s.student_no AND " +
				"d.Oid<>"+DtimeOid+" AND ds.week='"+((Map)dclass.get(i)).get("week")+"' AND (ds.begin <="+((Map)dclass.get(i)).get("end")+" AND ds.end >="+((Map)dclass.get(i)).get("begin")+"" +")").size()>0){
				ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "上課時段重複"));
				saveErrors(request, error);
				return unspecified(mapping, form, request, response);
			}
		}
		
		//上限
		float credit=(Float)dtime.get("credit");	//Float.parseFloat(	
		try{
			float max=Float.parseFloat(((Map) session.getAttribute("schedule")).get("max").toString());				
			if((Float.parseFloat(manager.ezGetString("SELECT SUM(d.credit)FROM Seld s, Dtime d WHERE s.student_no='"+c.getStudent().getStudentNo()+"' AND "+
				"d.Oid=s.Dtime_oid AND d.Sterm='"+((Map) session.getAttribute("schedule")).get("term")+"'"))+credit)>max){
				ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "學分數超過上限"));
				saveErrors(request, error);
				return unspecified(mapping, form, request, response);
			}
		}catch(Exception e){
			//預選課程為0者
		}
		
		
		//通識上限
		if(dtime.get("opt").equals("3"))
		try{
			
			float max=Float.parseFloat(((Map) session.getAttribute("schedule")).get("nor").toString());				
			//float credit=(Float)dtime.get("credit");	//Float.parseFloat(	
			if((Float.parseFloat(manager.ezGetString("SELECT SUM(d.credit)FROM Seld s, Dtime d WHERE " +
				"s.student_no='"+c.getStudent().getStudentNo()+"' AND d.opt='3' AND "+
				"d.Oid=s.Dtime_oid AND d.Sterm='"+((Map) session.getAttribute("schedule")).get("term")+"'"))+credit)>max){
				ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "通識課程學分數超過上限"));
				saveErrors(request, error);
				return unspecified(mapping, form, request, response);
			}
		}catch(Exception e){
			//預選課程為0者
		}
		
		//儲存
		try{
			manager.executeSql("INSERT INTO Seld(Dtime_oid, student_no)VALUES('"+DtimeOid+"', '"+c.getStudent().getStudentNo()+"')");
		}catch(Exception e){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "選課不成功"));
			saveErrors(request, error);
		}
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","加選完成"));
		saveMessages(request, msg);	
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward clear(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)throws Exception {				
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請點選正確的按鈕"));
		saveMessages(request, msg);
		return mapping.findForward("Main");
	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("AddCourse", "add");
		map.put("Clear", "clear");		
		return map;
	}
	
	
}
