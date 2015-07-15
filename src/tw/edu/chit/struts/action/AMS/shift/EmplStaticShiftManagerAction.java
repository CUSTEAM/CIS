package tw.edu.chit.struts.action.AMS.shift;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

/**
 * 人員常態管理
 * @author JOHN
 *
 */
public class EmplStaticShiftManagerAction extends BaseLookupDispatchAction{
	
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("allUnit", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE (category='Unit' OR category='UnitTeach') ORDER BY sequence"));
		session.setAttribute("allShift", manager.ezGetBy("SELECT id, name FROM AMS_ShiftTime GROUP BY id"));
		setContentPage(request.getSession(false), "AMS/shift/EmplStaticShiftManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String sid=eForm.getString("sid");
		String sidno=eForm.getString("sidno");
		String sunit=eForm.getString("sunit");
		String fscname=eForm.getString("fscname");
		
		// Leo_20120301 人事室_許小姐  增加搜尋條件-固定班別中的選項<無須刷卡>		
		String Sel_sid=" AND WorkShift ='"+sid+"'";
		if(sid.equals("all")){
			Sel_sid="";
		}/*else if(sid.equals("")){
			sid="='"+sid+"'";
		}*/	
		
		if(sid.trim().equals("all")&&sunit.trim().equals("")&&sidno.trim().equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請至少指定 1 種查詢條件"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}
		
		request.setAttribute("empls", manager.ezGetBy(" SELECT * FROM empl " +
				                                      " WHERE idno LIKE'"+sidno+"%' " +
				                                      "   AND unit LIKE'"+sunit+"%' " + Sel_sid +     /*"%' AND WorkShift "+sid+*/
				                                      "   AND cname LIKE'"+fscname+"%' ORDER BY unit"));
		//=============================================================================
		
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm eForm = (DynaActionForm) form;		
		String id[]=eForm.getStrings("id");
		String idno[]=eForm.getStrings("idno");
		String startDate[]=eForm.getStrings("startDate");
		String endDate[]=eForm.getStrings("endDate");
		
		Calendar sdate;
		Calendar edate;			
		Map shift;
		
		for(int i=1; i<idno.length; i++){			
			//if(id[i].trim().equals("") && startDate[i].trim().equals("") && endDate[i].trim().equals("")){
			if(startDate[i].trim().equals("") && endDate[i].trim().equals("")){
				continue;
			}	
			
			if(id[i].trim().equals("")){
				//刪除應刷
				manager.executeSql("DELETE FROM AMS_Workdate WHERE idno='"+idno[i]+"' AND wdate>='"+manager.convertDate(startDate[i])+"' AND wdate<='"+manager.convertDate(endDate[i])+"'");				
			}else{
				//建立應刷
				shift=manager.ezGetMap("SELECT * FROM AMS_ShiftTime WHERE id='"+id[i]+"'");			
				//變為西元開始日期			
				sdate=Calendar.getInstance();
				sdate.setTime(sf.parse(manager.convertDate(startDate[i])));
				edate=Calendar.getInstance();
				edate.setTime(sf.parse(manager.convertDate(endDate[i])));
				edate.add(Calendar.DAY_OF_MONTH, 1);//做到設定日, 如果他們不爽時只要改這邊	
				while(edate.after(sdate)){				
					saveDate(idno[i], sdate.getTime(), sdate.get(Calendar.DAY_OF_WEEK)-1, shift);
					sdate.add(Calendar.DAY_OF_MONTH, 1);				
				}				
			}						
		}		
		return query(mapping, form, request, response);
	}
	
	private boolean saveDate(String idno, Date wdate, int weekday, Map shift){
		CourseManager manager = (CourseManager) getBean("courseManager");
		String category=manager.ezGetString("SELECT category FROM empl WHERE idno='"+idno+"'");//員工身份
		if(weekday==0){
			weekday=7;
		}
		
		String in="null";
		String out="null";
		String work="w";//上班型態預設為w
		//若已有設定工作日
		//System.out.println("in"+weekday+"="+shift.get("in"+weekday));
		if(shift.get("in"+weekday)!=null){
			in="'"+shift.get("in"+weekday).toString()+"'";
			out="'"+shift.get("out"+weekday).toString()+"'";
		}else{
			work="h";
		}
		
		String date=sf.format(wdate);
		Map holiday=manager.ezGetMap("SELECT * FROM AMS_Holiday WHERE Date='"+date+"'");		
		String shiftId=shift.get("id").toString();
		Map someday;
		if(holiday!=null){
			//當天有設為特別工作日		
			if(holiday.get("Type").equals("W")){
				//如果員工身份符合
				if(holiday.get("EmplType").equals(category)||holiday.get("EmplType").equals("A")){
					in="'"+holiday.get("StartTime").toString()+"'";
					out="'"+holiday.get("EndTime").toString()+"'";
					work="w";
				}else{
					//仍然是休假	
					work="h";
				}				
			}			
			//當天有設為假日
			if(holiday.get("Type").equals("H")){
				work="h";			
			}			
			someday=manager.ezGetMap("SELECT extra FROM AMS_Workdate WHERE idno='"+idno+"' AND wdate='"+date+"'");
			//儲存
			if(someday!=null){
				//更新
				if(someday.get("extra")==null || someday.get("")=="")//沒做記號的隨便改
				update(work, in, out, shiftId, idno, date);				
			}else{
				//先刪
				manager.executeSql("DELETE FROM AMS_Workdate WHERE idno='"+idno+"' AND wdate='"+date+"';");
				//新增
				insert(work, in, out, shiftId, idno, date);
			}
			
		}else{
			//若不是特別的日子則以一般設定對待			
			if(manager.ezGetInt("SELECT COUNT(*)FROM AMS_Workdate WHERE idno='"+idno+"' AND wdate='"+date+"'")>0){
				//更新
				update(work, in, out, shiftId, idno, date);
			}else{
				//新增
				insert(work, in, out, shiftId, idno, date);
			}
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
		map.put("Query", "query");
		map.put("Save", "save");
		return map;
	}
}
