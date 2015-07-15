package tw.edu.chit.web;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.Toolket;

public class ContextSetupListener implements ServletContextListener {

	private static Log log = LogFactory.getLog(ContextSetupListener.class);
	
	public void contextInitialized(ServletContextEvent event) {
		
		//有用的變數
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());		
		CourseManager manager = (CourseManager) context.getBean("courseManager");
		ServletContext servletContext = event.getServletContext();
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c=Calendar.getInstance();
		try {
			c.setTime(sf.parse(manager.ezGetString("SELECT wdate FROM week LIMIT 1")));
		} catch (ParseException e1) {
			c.setTime(new Date());
		}
		servletContext.setAttribute("school_term_begin", c.getTime());//開學日期
		c.add(Calendar.DAY_OF_YEAR, 126);
		servletContext.setAttribute("school_term_end", c.getTime());//學期結束，未有資料記載以18週日數定
		
		servletContext.setAttribute("school_year", manager.getSchoolYear());//現在學年
		
		servletContext.setAttribute("school_term", manager.getSchoolTerm());//現在學期
		
		//以下未知的變數，無法確認哪些地方在用、裡面存著什麼
		Global.context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		log.info("Initial/Refresh Context Information.");
		Toolket.refreshSchoolMap();
		Toolket.refreshDeptMap();
		Toolket.refreshMasterDeptMap();
		Toolket.refreshClassMap();
		Toolket.refreshStatusMap();
		Toolket.refreshStatusCauseMap();
		Toolket.refreshIdentityMap();
		Toolket.refreshGroupMap();
		Toolket.refreshEmpStatusMap();
		Toolket.refreshEmpStatusCauseMap();
		Toolket.refreshEmpCategoryMap();
		Toolket.refreshEmpUnitMap();
		Toolket.refreshEmpRoleMap();
		Toolket.refreshEmpDegreeMap();
		Toolket.refreshEmpTutorMap();
		Toolket.refreshEmpDirectorMap();
		Toolket.refreshCourseOptMap();
		Toolket.refreshCourseTypeMap();
		Toolket.refreshSystemParameters();
		Toolket.refreshTimeOffMap();
		Toolket.refreshBonusPenaltyMap();
		Toolket.refreshSchoolNameZH();
		Toolket.refreshSchoolNameEN();
		Toolket.refreshAborigineMap();
		Toolket.refreshFeeCodeMap();
		Toolket.refreshLiteracyType();
		Toolket.refreshAmsAskLeave();
		//Toolket.refreshPageList();
		
		servletContext.setAttribute("AllCampuses",   	Global.CampusList);
		servletContext.setAttribute("AllSchools",   	Global.SchoolList);
		servletContext.setAttribute("AllDepts",   		Global.DeptList);
		servletContext.setAttribute("StudentStatus",   	Global.StatusList);
		servletContext.setAttribute("StudentIdentity", 	Global.IdentityList);
		servletContext.setAttribute("StudentGroup",    	Global.GroupList);
		servletContext.setAttribute("StatusCause",     	Global.StatusCauseList);
		servletContext.setAttribute("EmployeeStatus",   Global.EmpStatusList);
		servletContext.setAttribute("EmployeeStatusCause",   Global.EmpStatusCauseList);
		servletContext.setAttribute("EmployeeCategory", Global.EmpCategoryList);
		servletContext.setAttribute("EmployeeUnit", 	Global.EmpUnitList);
		servletContext.setAttribute("EmployeeRole", 	Global.EmpRoleList);
		servletContext.setAttribute("EmployeeDegree", 	Global.EmpDegreeList);
		servletContext.setAttribute("EmployeeTutor", 	Global.EmpTutorList);
		servletContext.setAttribute("EmployeeDirector", Global.EmpDirectorList);
		servletContext.setAttribute("CourseOpt",       	Global.CourseOptList);
		servletContext.setAttribute("CourseType",      	Global.CourseTypeList);
		//servletContext.setAttribute("coursesOp",   	Global.Courses);
		//servletContext.setAttribute("employees",   	Global.Employees);
		//servletContext.setAttribute("classes",   		Global.Classes);
		servletContext.setAttribute("SysParameter",   	Global.SysParameters);
		servletContext.setAttribute("Timeoff",   		Global.TimeOffList);
		servletContext.setAttribute("BonusPenalty",   	Global.BonusPenaltyCodeList);
		servletContext.setAttribute("AllClassesJsArray",Global.AllClassJavaScriptArray);
		servletContext.setAttribute("SchoolName_ZH",   	Global.SchoolNameZH);
		servletContext.setAttribute("SchoolName_EN",	Global.SchoolNameEN);
		
		
		/*
		try {
			InetAddress iaddr = InetAddress.getLocalHost();
			String host = iaddr.getCanonicalHostName();
			//log.error(host);
			//if(host.indexOf("chit.seastar") >= 0){
			Calendar now = Calendar.getInstance();
			Calendar[] termDate = Toolket.getDateBetweenWeeks(1, 18);
			termDate[0].add(Calendar.DATE, -7);
			termDate[1].add(Calendar.DATE, 7);
			//TODO:測試
			//StudAbsenceApplyNotConfirmEmail();
			if(host.indexOf("ap2") >= 0 && 
					now.compareTo(termDate[0]) >= 0 && 
					now.compareTo(termDate[1]) <= 0 ){
				TeacherTimeOffEmail();
				TeacherScoreNotUploadEmail();
				StudAbsenceApplyNotConfirmEmail();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//TimeOffNotUploadEmailTask fttask = new TimeOffNotUploadEmailTask();
		//fttask.run();
	}

	public void contextDestroyed(ServletContextEvent event) {}
	
	private void TeacherTimeOffEmail(){
		Timer timer = new Timer();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 0);
		timer.scheduleAtFixedRate(new TimeOffNotUploadEmailTask(), cal.getTime(),
		(long) 60 * 60 * 24 * 1000); // 每隔一天
		//		(long) 30 * 60 * 1000);
		String message = "Teacher not Upload TimeOff record Email server Restarted at: "
				+ new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
						.format(new Date()) + File.separator;
		log.info(message);		
	}

	//學生假單位審核
	private void StudAbsenceApplyNotConfirmEmail(){
		Timer timer = new Timer();
		Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.MINUTE, +3);
		cal.add(Calendar.DATE, +1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 50);
		cal.set(Calendar.SECOND, 0);
		timer.scheduleAtFixedRate(new StudAbsenceApplyNotConfirm(), cal.getTime(),
		(long) 60 * 60 * 24 * 1000); // 每隔一天
		//		(long) 30 * 60 * 1000);
		String message = "Teacher not Upload TimeOff record Email server Restarted at: "
				+ new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
						.format(new Date()) + File.separator;
		log.info(message);		
	}


	private void AMSWorkDateNotSetEmail(){
		Timer timer = new Timer();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 10);
		cal.set(Calendar.SECOND, 0);
		Calendar sCal = Calendar.getInstance();
		sCal.add(Calendar.DAY_OF_MONTH, 7);
		sCal.set(Calendar.HOUR_OF_DAY, 0);
		sCal.set(Calendar.MINUTE, 0);
		sCal.set(Calendar.SECOND, 0);
		sCal.set(Calendar.MILLISECOND, 0);
		
		AMSWorkDateNotSetEmailTask task = new AMSWorkDateNotSetEmailTask(sCal);
		
		timer.scheduleAtFixedRate(task, cal.getTime(),
		(long) 60 * 60 * 24 * 1000); // 每隔一天
		//		(long) 30 * 60 * 1000);
		String message = "AMSWorkDateNotSet Email server Restarted at: "
				+ new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
						.format(new Date()) + File.separator;
		log.info(message);		
	}


	@SuppressWarnings("unchecked")

	private void TeacherScoreNotUploadEmail(){
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		Timer timer = new Timer();
		ScoreJdbcDAO jdbcDao = (ScoreJdbcDAO) Global.context.getBean("scoreJdbcDAO");
		String sql = "select * from optime1 o order by level";
		List tmplist = jdbcDao.findAnyThing(sql);
		String end_date = "";
		String end_time = "";
		int[] idate = new int[3];
		int[] itime = new int[3];
		Calendar taskCal = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		for(Object optime:tmplist){
			String level = "";
			String scope = "";
			String depart = "";
			Calendar endCal = Calendar.getInstance();
			depart = ((Map)optime).get("depart").toString();
			level = ((Map)optime).get("level").toString();
			if(level.equals("1") || level.equals("2") ||(level.equals("3") && sterm.equals("2"))){
				end_date = ((Map)optime).get("end_date").toString();
				end_time = ((Map)optime).get("end_time").toString();
				String[] tDate = null;
				if (end_date.indexOf('/') >= 0) {
					tDate = end_date.split("/");
				} else if (end_date.indexOf('-') >= 0) {
					tDate = end_date.split("-");
				}

				idate[0] = Integer.parseInt(tDate[0]) + 1911;
				idate[1] = Integer.parseInt(tDate[1]) - 1;
				idate[2] = Integer.parseInt(tDate[2]);
				itime[0] = Integer.parseInt(end_time.substring(0, 2));
				itime[1] = Integer.parseInt(end_time.substring(3, 5));
				itime[2] = Integer.parseInt(end_time.substring(6));
				if(itime[0]==24){
					itime[0]=23;
					itime[1]=59;
					itime[2]=59;
				}
				endCal.set(idate[0], idate[1], idate[2], itime[0], itime[1], itime[2]);
				taskCal.set(idate[0], idate[1], idate[2], 23, 59, 59);
				
				if(level.equals("1")){
					//TODO:Modify when test over
					taskCal.add(Calendar.DATE, -3);	
					//taskCal.add(Calendar.DATE, -3);
					scope = "1";
				}else if(level.equals("2")){
					taskCal.add(Calendar.DATE, -1);
					if(sterm.equals("2")){
						scope = "3";	//畢業班除外
					}else{
						scope = "1";	//全部
					}
				}else if(level.equals("3") && sterm.equals("2")){
					level = "2";	//調整為期末畢業班
					taskCal.add(Calendar.DATE, -1);
					scope = "2";		//只有畢業班
				}
				taskCal.set(Calendar.HOUR_OF_DAY, 0);
				taskCal.set(Calendar.MINUTE, 20);
				taskCal.set(Calendar.SECOND, 0);
				//假設 Server 每天都會重新起動,否則會失效!!!
				//if(taskCal.compareTo(now) <=0 && taskCal.compareTo(yesterday) >=0){
				if(taskCal.compareTo(now) >=0){
					ScoreNotUploadEmailTask task = new ScoreNotUploadEmailTask(level, scope, endCal, depart);
					timer.schedule(task, taskCal.getTime());
				}

			}
		}
		*/
	}

	public void contextDestroyed(ServletContextEvent event) {
		
	}
	
	
}
