package tw.edu.chit.struts.action.AMS.shift;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class Empl7ShiftManagerAction extends BaseLookupDispatchAction{
	
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		//session.setAttribute("allUnit", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE (category='Unit' OR category='UnitTeach') ORDER BY sequence"));
		//session.setAttribute("allShift", manager.ezGetBy("SELECT id, name FROM AMS_ShiftTime GROUP BY id"));
		
		session.removeAttribute("aEmpl");
		//session.setAttribute("DirectoryPath", manager.ezGetString("SELECT Action FROM Module m WHERE m.Oid=(SELECT ParentOid FROM Module WHERE Action='/AMS/EmplHolidayManager.do')"));
		setContentPage(request.getSession(false), "AMS/shift/Empl7ShiftManager.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward Continue(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;		
		String id=sForm.getString("id");
		String startDate=sForm.getString("startDate");
		String endDate=sForm.getString("endDate");		
		
		if(id.trim().equals("")||startDate.trim().equals("")||endDate.trim().equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "所有欄位必須輸入才能繼續"));
			saveErrors(request, error);
			return mapping.findForward("Main");			
		}		
		request.setAttribute("aEmpl", manager.ezGetMap("SELECT * FROM empl WHERE idno='"+id+"'"));		
		return mapping.findForward("Main");

	}
	
	public ActionForward ok(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;
		
		String id=sForm.getString("id");
		String startDate=sForm.getString("startDate");
		String endDate=sForm.getString("endDate");
		
		String in1=sForm.getString("in1");
		String in2=sForm.getString("in2");
		String in3=sForm.getString("in3");
		String in4=sForm.getString("in4");
		String in5=sForm.getString("in5");
		String in6=sForm.getString("in6");
		String in7=sForm.getString("in7");
		
		String out1=sForm.getString("out1");
		String out2=sForm.getString("out2");
		String out3=sForm.getString("out3");
		String out4=sForm.getString("out4");
		String out5=sForm.getString("out5");
		String out6=sForm.getString("out6");
		String out7=sForm.getString("out7");
		
		Calendar sdate;
		Calendar edate;			
		
		Calendar adate;
		
		Map shift=new HashMap();
		
		shift.put("in1", in1);
		shift.put("in2", in2);
		shift.put("in3", in3);
		shift.put("in4", in4);
		shift.put("in5", in5);
		shift.put("in6", in6);
		shift.put("in7", in7);		
		shift.put("out1", out1);
		shift.put("out2", out2);
		shift.put("out3", out3);
		shift.put("out4", out4);
		shift.put("out5", out5);
		shift.put("out6", out6);
		shift.put("out7", out7);		
		shift.put("id", "R");
		
		
		//變為西元開始日期			
		sdate=Calendar.getInstance();
		sdate.setTime(sf.parse(manager.convertDate(startDate)));
		edate=Calendar.getInstance();
		adate=Calendar.getInstance();
		edate.setTime(sf.parse(manager.convertDate(endDate)));
		edate.add(Calendar.DAY_OF_MONTH, 1);//添加班表直到設定結束日, 如果他們不爽時只要改這邊			
		while(edate.after(sdate)){			
			//System.out.println(sdate.get(Calendar.DAY_OF_WEEK)-1+", "+sdate.getTime());			
			saveDate(id, sdate.getTime(), sdate.get(Calendar.DAY_OF_WEEK)-1, shift);
			sdate.add(Calendar.DAY_OF_MONTH, 1);			
		}			
				
		List rightShift=manager.ezGetBy("SELECT * FROM AMS_Workdate WHERE wdate>='"+manager.convertDate(startDate)+"' AND wdate<='"+manager.convertDate(endDate)+"'");
		
		for(int i=0; i<rightShift.size(); i++){			
			adate.setTime(sf.parse(((Map)rightShift.get(i)).get("wdate").toString()));
			((Map)rightShift.get(i)).put("week", manager.ChWeekOfDay(adate.get(Calendar.DAY_OF_WEEK), ""));			
		}		
		request.setAttribute("rightShift", rightShift);
		return mapping.findForward("Main");

	}
	
	public ActionForward complete(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		//session.setAttribute("allUnit", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE (category='Unit' OR category='UnitTeach') ORDER BY sequence"));
		//session.setAttribute("allShift", manager.ezGetBy("SELECT id, name FROM AMS_ShiftTime GROUP BY id"));
		
		
		//session.setAttribute("DirectoryPath", manager.ezGetString("SELECT Action FROM Module m WHERE m.Oid=(SELECT ParentOid FROM Module WHERE Action='/AMS/EmplHolidayManager.do')"));
		//setContentPage(request.getSession(false), "AMS/shift/Empl7ShiftManager.jsp");
		return unspecified(mapping, form, request, response);

	}
	
	private boolean saveDate(String idno, Date wdate, int weekday, Map shift){
		CourseManager manager = (CourseManager) getBean("courseManager");
		//String category=manager.ezGetString("SELECT category FROM empl WHERE idno='"+idno+"'");//員工身份
		if(weekday==0){
			weekday=7;
		}
		
		String in="null";
		String out="null";
		String work="w";//上班型態預設為w
		//若已有設定工作日
		//System.out.println("in"+weekday+"="+shift.get("in"+weekday));
		if(shift.get("in"+weekday)!=null && !shift.get("in"+weekday).equals("")){
			in="'"+shift.get("in"+weekday).toString()+"'";
			out="'"+shift.get("out"+weekday).toString()+"'";
		}else{
			work="h";
		}
		
		
		String date=sf.format(wdate);
		//Map holiday=manager.ezGetMap("SELECT * FROM AMS_Holiday WHERE Date='"+date+"'");		
		String shiftId=shift.get("id").toString();
		//Map someday;		
		if(manager.ezGetInt("SELECT COUNT(*)FROM AMS_Workdate WHERE idno='"+idno+"' AND wdate='"+date+"'")>0){
			//更新
			update(work, in, out, shiftId, idno, date);
		}else{
			//新增
			insert(work, in, out, shiftId, idno, date);
		}		
		return true;
	}
	
	private void insert(String work, String in, String out, String shiftId, String idno, String date){
		CourseManager manager = (CourseManager) getBean("courseManager");
		//System.out.println(work);
		if(work.equals("w")){
			manager.executeSql("INSERT INTO AMS_Workdate(idno, wdate, date_type, set_in, set_out, shift)VALUES" +
					"('"+idno+"', '"+date+"', '"+work+"', "+in+", "+out+", '"+shiftId+"');");
		}else{
			manager.executeSql("INSERT INTO AMS_Workdate(idno, wdate, date_type, shift)VALUES" +
					"('"+idno+"', '"+date+"', '"+work+"', '"+shiftId+"');");
		}
		
		
	}
	
	private void update(String work, String in, String out, String shiftId, String idno, String date){
		CourseManager manager = (CourseManager) getBean("courseManager");
		if(work.equals("w")){
			manager.executeSql("UPDATE AMS_Workdate SET set_in="+in+", set_out="+out+", date_type='"+work+"', shift='"+shiftId+"' " +
					"WHERE idno='"+idno+"' AND wdate='"+date+"'");
		}else{
			manager.executeSql("UPDATE AMS_Workdate SET date_type='"+work+"', shift='"+shiftId+"' " +
					"WHERE idno='"+idno+"' AND wdate='"+date+"'");
		}
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Continue", "Continue");
		map.put("OK", "ok");
		map.put("Complete", "complete");	
		return map;
	}

}
