package tw.edu.chit.struts.action.AMS;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.ibm.icu.text.DateFormat;


import tw.edu.chit.model.AmsAskLeave;
import tw.edu.chit.model.AmsWorkdateData;
import tw.edu.chit.model.Rcproj;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class HolidayAction extends BaseLookupDispatchAction{

	SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd");
	/**
	 * 初始資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "holiday");			
		
		setContentPage(request.getSession(false), "AMS/Holiday.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 查詢資料
	 * @throws ParseException 
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws ParseException{

		Toolket.resetCheckboxCookie(response, "holiday");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;				
		
		//Date thisD = new Date();
		//System.out.println(thisD);
		
		String sdate = " Where Date >= '"+aForm.getString("sdate")+"'";
		String edate = " And Date <= '"+aForm.getString("edate")+"'";
		
		if(aForm.getString("sdate").equals("")){
			sdate = "";
		}
		if(aForm.getString("edate").equals("")){
			edate = "";
		}else if(aForm.getString("sdate").equals("") && !aForm.getString("edate").equals("")){
			edate = " Where Date >= '"+aForm.getString("edate")+"'";
		}
		/* 2012/04/30 LEO 修改顯示方式
		List<AmsAskLeave> holiday = manager.ezGetBy(
				"Select Oid, Date, Name, Type, Substr(StartTime,1,5) StartTime, Substr(EndTime,1,5) EndTime, EmplType " +
                                                    "From AMS_Holiday" + sdate + edate +" Order By Date");	*/	
		List<AmsAskLeave> holiday = manager.ezGetBy(
				"Select Oid, Date, Name, Type, StartTime StartTime, EndTime EndTime, EmplType " +
                                                    "From AMS_Holiday" + sdate + edate +" Order By Date");	
		
		session.setAttribute("HD_List", holiday);

		setContentPage(request.getSession(false), "AMS/Holiday.jsp");
		return mapping.findForward("Main");
	}	
	
	private Object D(Date isEDate) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 啟動新增
	 */
	public ActionForward Create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
				
		session.setAttribute("myOpen", "open");	
		session.setAttribute("saveType", "create");
		
		session.setAttribute("date", "");
		session.setAttribute("name", "");
		session.setAttribute("type", "");
		session.setAttribute("startTime", "");
		session.setAttribute("endTime", "");
		session.setAttribute("emplType", "");
		
		setContentPage(request.getSession(false), "AMS/Holiday.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 啟動修改
	 * 特殊日期設定不提供修改功能
	 */
	public ActionForward Modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Calendar calendar = new GregorianCalendar();
		//Toolket.resetCheckboxCookie(response, "rcact");		
		
		String teacherId = user.getMember().getAccount();		
		String ids = Toolket.getSelectedIndexFromCookie(request, "holiday");
		String id_s = ids.substring(1, ids.length()-1);	
		String myYear = manager.ezGetString("Select Substr(Date,1,4) From AMS_Holiday Where Oid='"+id_s+"'");
		String nowYear = Integer.toString( calendar.get(calendar.YEAR));			
		
		session.setAttribute("myOpen", "open");
		session.setAttribute("saveType", "modify");
		
		session.setAttribute("date", manager.ezGetString("Select Date From AMS_Holiday Where Oid='"+id_s+"'"));
		session.setAttribute("name", manager.ezGetString("Select Name From AMS_Holiday Where Oid='"+id_s+"'"));
		session.setAttribute("type", manager.ezGetString("Select Type From AMS_Holiday Where Oid='"+id_s+"'"));
		session.setAttribute("startTime", manager.ezGetString("Select Substr(StartTime,1,5) From AMS_Holiday Where Oid='"+id_s+"'"));
		session.setAttribute("endTime", manager.ezGetString("Select Substr(EndTime,1,5) From AMS_Holiday Where Oid='"+id_s+"'"));
		session.setAttribute("emplType", manager.ezGetString("Select EmplType From AMS_Holiday Where Oid='"+id_s+"'"));
				
		setContentPage(request.getSession(false), "AMS/Holiday.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 儲存資料
	 */
	public ActionForward Save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();			
		
		String idno = user.getMember().getAccount();
		session.setAttribute("myOpen", "close");
		
		String date_s = aForm.getString("date_s");
		String date_e = aForm.getString("date_e");
		String name = aForm.getString("name");
		String type = aForm.getString("type");	
		String startTime = aForm.getString("startTime");	
		String endTime = aForm.getString("endTime");	
		String emplType = aForm.getString("emplType");
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		if(date_s.trim().equals("") && date_e.trim().equals("")){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請指定日期區間, "));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		if(date_s.trim().equals("")){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請指定日期起始日!!, "));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}		
		/*2010-01-12
	    List myList = manager.ezGetBy("Select E.idno From empl E Where WorkShift <> 'NO'");    //取得需要刷卡人數清單
		if(emplType.equals("1")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where WorkShift <> 'NO' And category= '1'");
		}else if(emplType.equals("2")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where WorkShift <> 'NO' And category= '2'");
		}else if(emplType.equals("3")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where WorkShift <> 'NO' And category= '3'");
		}else if(emplType.equals("4")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where WorkShift <> 'NO' And category= '4'");
		}
		*/
		List myList = manager.ezGetBy("Select E.idno From empl E");    //取得需要刷卡人數清單
		if(emplType.equals("1")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where category= '1'");
		}else if(emplType.equals("2")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where category= '2'");
		}else if(emplType.equals("3")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where category= '3'");
		}else if(emplType.equals("4")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where category= '4'");
		}
		
		//轉換格式		
		Calendar mySDate = Calendar.getInstance();
		Calendar myEDate = Calendar.getInstance();		
				
		mySDate.setTime(sf.parse(date_s));
		myEDate.setTime(sf.parse(date_e));
		myEDate.add(Calendar.DAY_OF_MONTH, 1);
		//**************
		
		if(mySDate.after(myEDate)){			//判定起迄日期有無錯誤,並提供錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "日期起訖指定錯誤!!, "));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}	
				
		while(myEDate.after(mySDate)){	//日期回圈			
			
			int isDD = mySDate.get(Calendar.DATE);
			int isMM = mySDate.get(Calendar.MONTH)+1;
			int isYY = mySDate.get(Calendar.YEAR);
			String isDate = String.valueOf(isYY)+"/"+String.valueOf(isMM)+"/"+String.valueOf(isDD);  //拼湊日期字串
			//System.out.println(isDate);
			
			List sList = manager.ezGetBy("Select idno From AMS_Workdate Where shift = 'S' And wdate = '"+isDate+"'");  //取得排班人員清單
		
			for(int s=0; s<sList.size(); s++){  //從須刷卡清單,排除排班人員
				myList.remove(sList.get(s));
			}		
		
			if(type.equals("W")){  //該日期為上班狀況下的執行
				
				manager.executeSql(    //執行AMS_Holiday資料表的新增
						"Insert Into AMS_Holiday(Date,Name,Type,StartTime,EndTime,EmplType) " +  
						"Values('" +isDate+"','"+name+"','"+type+"','"+startTime+"','"+endTime+"','"+emplType+"')");
				
				if(aForm.getString("emplType").equals("A") || aForm.getString("emplType").equals("3")){
					manager.executeSql(    //清除該日期除了排班人員之外在AMS_Workdate資料表的資料
							"DELETE FROM AMS_Workdate Where wdate='"+isDate+"' And shift <> 'S'");
				}
				
				for(int i=0; i<myList.size(); i++){    //依序將該日須刷卡清單新增到AMS_Workdate資料表中				
					
					String myIdno = manager.ezGetString(
							"Select idno From AMS_Workdate " +
							"Where wdate='"+isDate+"' And idno = '"+myList.get(i).toString().substring(6,16)+"'");
					
					if(myIdno.equals("")){
						manager.executeSql("Insert Into AMS_Workdate(idno, wdate, date_type, set_in, set_out) " +
						          " Values('"+myList.get(i).toString().substring(6,16)+"','"+isDate+"','w','"+startTime+"','"+endTime+"')");
					}
									
				}
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "新增完成"));
				
			}else if(type.equals("H")){    //該日期為休假狀況下的執行
				
				manager.executeSql(    //執行AMS_Holiday資料表的新增
						"Insert Into AMS_Holiday(Date,Name,Type,EmplType) " +
						"Values('" +isDate+"','"+name+"','"+type+"','"+emplType+"')");
				
				manager.executeSql(    //更新AMS_Workdate資料表,除了排班人員之外
						"Update `AMS_Workdate` SET date_type = 'h' Where wdate = '"+isDate+"' And shift <> 'S'");
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "新增完成"));
				
			}
			
			mySDate.add(Calendar.DAY_OF_MONTH, 1);				
		}
		
		saveMessages(request, messages);
		Toolket.resetCheckboxCookie(response, "holiday");
		setContentPage(request.getSession(false), "AMS/Holiday.jsp");
		return unspecified(mapping, form, request, response);
		//return mapping.findForward("Main");	
	}
	
	/**
	 * 刪除資料
	 */
	public ActionForward Delete(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	       throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();			
		
		String teacherId = user.getMember().getAccount();		
		String ids = Toolket.getSelectedIndexFromCookie(request, "holiday");
		String id_s = ids.substring(1, ids.length()-1);	
		
		String type = manager.ezGetString("Select Type From AMS_Holiday Where Oid='"+id_s+"'");
		String date = manager.ezGetString("Select Date From AMS_Holiday Where Oid='"+id_s+"'");
		
		//****處理過期的設定*****
		String D = date.substring(0, 4)+"/"+date.substring(5,7)+"/"+date.substring(8,10);
		//System.out.println(D);
		Calendar cDate = Calendar.getInstance();
		Calendar thisD = Calendar.getInstance();
		cDate.setTime(sf.parse(D));
		thisD.setTime(new Date());
		thisD.add(Calendar.DAY_OF_MONTH, 1);
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		if(thisD.after(cDate)){			//資料日期是否已經超過今日,並提供錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "要刪除日期不可超過今日!!, "));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		//************************
		
		String emplType = manager.ezGetString("Select EmplType From AMS_Holiday Where Oid='"+id_s+"'");
		
		manager.executeSql("DELETE FROM AMS_Holiday Where Oid='"+id_s+"'");	  //刪除AMS_Holiday該日期資料
		/*2010-01-12
		List myList = manager.ezGetBy("Select E.idno From empl E Where WorkShift <> 'NO'");    //取得需要刷卡人數清單,排除未指定班別的人,包含有教師與二級以上主管
		if(emplType.equals("1")){                                                            //建議將教師身分與主管身份有所區分 
			 myList = manager.ezGetBy("Select E.idno From empl E Where WorkShift <> 'NO' And category= '1'");
		}else if(emplType.equals("2")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where WorkShift <> 'NO' And category= '2'");
		}else if(emplType.equals("3")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where WorkShift <> 'NO' And category= '3'");
		}else if(emplType.equals("4")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where WorkShift <> 'NO' And category= '4'");
		}
		*/
		List myList = manager.ezGetBy("Select E.idno From empl E");    //取得需要刷卡人數清單,排除未指定班別的人,包含有教師與二級以上主管
		if(emplType.equals("1")){                                                            //建議將教師身分與主管身份有所區分 
			 myList = manager.ezGetBy("Select E.idno From empl E Where category= '1'");
		}else if(emplType.equals("2")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where category= '2'");
		}else if(emplType.equals("3")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where category= '3'");
		}else if(emplType.equals("4")){
			 myList = manager.ezGetBy("Select E.idno From empl E Where category= '4'");
		}
		
		List sList = manager.ezGetBy("Select idno From AMS_Workdate Where shift = 'S' And wdate = '"+date+"'");  //取得排班人員清單
		
		for(int s=0; s<sList.size(); s++){  //從須刷卡清單,排除排班人員
			myList.remove(sList.get(s));
		}		
		
		if(type.equals("W")){
			for(int i=0; i<myList.size(); i++){ 
				manager.executeSql(    //修改該日期AMS_Workdate資料表
						"Update `AMS_Workdate` SET date_type = 'h' " +
						"Where wdate = '"+date+"' " +
								"And idno = +'"+myList.get(i).toString().substring(6,16)+"'" +
								"And shift <> 'S'");
			}
			
		}else if(type.equals("H")){
			for(int i=0; i<myList.size(); i++){ 
				manager.executeSql(    //修改該日期AMS_Workdate資料表
						"Update `AMS_Workdate` SET date_type = 'w' " +
						"Where wdate = '"+date+"' " +
								"And idno = +'"+myList.get(i).toString().substring(6,16)+"'" +
								"And shift <> 'S'");
			}
			
		}
		
		Toolket.resetCheckboxCookie(response, "holiday");
		setContentPage(request.getSession(false), "AMS/Holiday.jsp");
		return unspecified(mapping, form, request, response);
		//return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create", "Create");		
		map.put("Modify", "Modify");
		map.put("Delete", "Delete");
		map.put("Save", "Save");
		map.put("Query", "Query");
		return map;
	}

}
