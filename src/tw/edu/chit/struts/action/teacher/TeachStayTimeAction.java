package tw.edu.chit.struts.action.teacher;

import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Code5;
import tw.edu.chit.model.ContractTeacher;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.EmplBase;

import tw.edu.chit.model.Member;
import tw.edu.chit.model.TeacherStayTime;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class TeachStayTimeAction extends BaseLookupDispatchAction {

	public static final String ORDER_INFO = "orderInfo";
	public static final String ORDER_INFO_HOLIDAY = "orderInfoHoliday";
	public static final String MEMBER_INFO = "memberInfo";
	public static final String LOCATION_INFO = "locationInfo";
	public static final String STAY_TIME_INFO = "stayTimeInfo";

	/**
	 * 依據老師登入資訊取得留校時間,個人資料與辦公室位置等資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute(ORDER_INFO, getOrderInfo());
		session.setAttribute(ORDER_INFO_HOLIDAY, getOrderInfo4Holiday());

		Member member = (Member) getUserCredential(session).getMember();
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);

		String year = cm.getNowBy("School_year");
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		session.setAttribute("year", year);
		session.setAttribute("term", term);
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
		DateFormat dt = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss",
				Locale.TAIWAN);
		List<Code5> code5s = mm.findStayTime();
		Code5 deadline = mm.findStayTimeDead().get(0);
		String tmp = String.valueOf(Integer.parseInt(StringUtils.substring(
				deadline.getName(), 0, 3)) + 1911)
				+ StringUtils.substring(deadline.getName(), 3);
		session.setAttribute("STAYDEADLINE", df.format(java.sql.Date
				.valueOf(tmp)));
		
		Empl empl = mm.findEmplByOid(member.getOid());
		empl.setUnit2(Toolket.getEmpUnit(empl.getUnit()));
		session.setAttribute(MEMBER_INFO, empl);
		
		ContractTeacher contractTeacher = new ContractTeacher();
		contractTeacher.setSchoolYear(year);
		contractTeacher.setSchoolTerm(term);
		List<ContractTeacher> contractTeachers = mm
				.findContractTeacherBy(contractTeacher);
		String[] ctTmp = new String[0];
		for (ContractTeacher ct : contractTeachers)
			ctTmp = (String[]) ArrayUtils.add(ctTmp, ct.getIdno());
		Arrays.sort(ctTmp);

		int snameCounts = 0;
		if (StringUtils.contains(empl.getSname(), "講師")) {
			snameCounts = 11;
		} else if (StringUtils.contains(empl.getSname(), "助理教授")) {
			snameCounts = 10;
		} else if (StringUtils.contains(empl.getSname(), "副教授")) {
			snameCounts = 9;
		} else if (StringUtils.contains(empl.getSname(), "教授")) {
			snameCounts = 8;
		}

		if (StringUtils.contains(empl.getSname(), "副校長")) {
			snameCounts = 3;
		} else if (StringUtils.contains(empl.getSname(), "校長")) {
			snameCounts = 0;
		} else if (StringUtils.contains(empl.getSname(), "教務長")) {
			snameCounts = 3;
		} else if (StringUtils.contains(empl.getSname(), "學務長")) {
			snameCounts = 3;
		} else if (StringUtils.contains(empl.getSname(), "總務長")) {
			snameCounts = 3;
		} else if (StringUtils.contains(empl.getSname(), "研發長")) {
			snameCounts = 3;
		} else if (StringUtils.contains(empl.getSname(), "進修部主任")) { //原本:進修推廣部暨進修學院主任
			snameCounts = 3;
		} else if (StringUtils.contains(empl.getSname(), "進修學院校務主任")) {
			snameCounts = 3;
		} else if (StringUtils.contains(empl.getSname(), "雲林分部主任")) {
			snameCounts = 3;
		} else if (StringUtils.contains(empl.getSname(), "院長")) {
			snameCounts -= 4;
		} else if (StringUtils.contains(empl.getSname(), "主任")) {
			snameCounts -= 4;
		} else if (StringUtils.contains(empl.getSname(), "組長")) {
			snameCounts -= 4;
		} else if (StringUtils.contains(empl.getSname(), "主秘")) {
			snameCounts -= 4;
		} else if (StringUtils.contains(empl.getSname(), "*")) {
			snameCounts -= 4;
		}
		
		if (StringUtils.contains(empl.getSname(), "簽約進修中")) {
			snameCounts -= 7;
		}
		/*
		if (ArrayUtils.indexOf(ctTmp, empl.getIdno()) != ArrayUtils.INDEX_NOT_FOUND) {
			snameCounts -= 7;
			empl
					.setSname(empl.getSname()
							+ (StringUtils.indexOf(empl.getSname(), "簽約進修中") == StringUtils.INDEX_NOT_FOUND ? " (簽約進修中)"
									: ""));
		}
		*/
		//目前更新總次數
		String cnt = manager.ezGetString("SELECT COUNT(*) FROM TeacherStayTimeModify WHERE ParentOid='"+member.getOid()+"' AND SchoolYear='"+year+"' AND SchoolTerm='"+term+"' AND LastModified>'"+df.format(java.sql.Date.valueOf(tmp))+"'");
		if(cnt!=null){
			session.setAttribute("modifyCounts", cnt);
		}
		//最後一次更新時間
		String lastdate = manager.ezGetString("SELECT LastModified FROM TeacherStayTimeModify WHERE ParentOid='"+member.getOid()+"' AND SchoolYear='"+year+"' AND SchoolTerm='"+term+"' ORDER BY LastModified DESC LIMIT 1");
		if(lastdate!=null){
			session.setAttribute("lastModified", lastdate);
		}
		
		session.setAttribute("emplSnameCounts", snameCounts < 0 ? 0
				: snameCounts);

		session.setAttribute(LOCATION_INFO, empl.getLocation());
		session.setAttribute(STAY_TIME_INFO, null);
		List<String[]> stays = processStayTime(mm.findStayTimeByEmplOid(member
				.getOid(), year, term));
		
		//System.out.println(empl);
		
		session.setAttribute(STAY_TIME_INFO, stays);
		session.setAttribute("modifyMode", "0");
		// 程式啟動時，抓不到 empl.getModify()，導致無法運行。  Leo 20120215 將其註解掉
		//session.setAttribute("modifyCounts", empl.getModify().size());
		//session.setAttribute("lastModified", empl.getModify().isEmpty() ? ""
				//: dt.format(empl.getModify().iterator().next()
		                //.getLastModified()));   

		List<Map> courseList = cm.findCourseByTeacherTermWeekdaySched(empl
				.getIdno(), term);
		session.setAttribute("courseList", courseList);

		// 開學後n Weeks要關閉
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		Calendar c = null;
		for (Code5 code5 : code5s) {
			if ("E".equalsIgnoreCase(code5.getIdno())) {
				tmp = code5.getName();
				tmp = String.valueOf(Integer.parseInt(StringUtils.substring(
						tmp, 0, 3)) + 1911)
						+ StringUtils.substring(tmp, 3);
				session.setAttribute("STAYEND", df.format(java.sql.Date
						.valueOf(tmp)));
				c = Calendar.getInstance();
				c.setTime(java.sql.Date.valueOf(tmp));
				c = Toolket.endOfCalendar(c);
				end.setTime(c.getTime());
			} else if ("B".equalsIgnoreCase(code5.getIdno())) {
				tmp = code5.getName();
				tmp = String.valueOf(Integer.parseInt(StringUtils.substring(
						tmp, 0, 3)) + 1911)
						+ StringUtils.substring(tmp, 3);
				session.setAttribute("STAYBEGIN", df.format(java.sql.Date
						.valueOf(tmp)));
				begin.setTime(java.sql.Date.valueOf(tmp));
			}
		}

		Calendar now = Calendar.getInstance();
		
		if (now.before(begin)|| now.after(end)) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "留校時間修改未在開放的時限內,謝謝."));
			saveMessages(request, messages);
			request.setAttribute("modifyMode", "1");
		}
		

		setContentPage(session, "teacher/TeachStayTime.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 新增/更新留校時間時處理之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String year = cm.getNowBy("School_year");
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		//System.out.println(am+","+mm+","+cm);
		
		// 時間過後才會紀錄
		Code5 deadline = mm.findStayTimeDead().get(0);
		String tmp = String.valueOf(Integer.parseInt(StringUtils.substring(
				deadline.getName(), 0, 3)) + 1911)
				+ StringUtils.substring(deadline.getName(), 3);
		Calendar cal = Calendar.getInstance();
		cal.setTime(java.sql.Date.valueOf(tmp));
		boolean needRecord = new Date().after(Toolket.endOfCalendar(cal)
				.getTime());

		Member member = getUserCredential(session).getMember();
		Map formProps = ((DynaActionForm) form).getMap();
		ActionMessages messages = new ActionMessages();
		
		try {
			Empl empl = mm.findEmplByOid(member.getOid());
			// 253行 造成程式無法執行 tw.edu.chit.model.Empl.stayTime, no session or session was closed
			// Leo 20120215 將其註解
			//empl.getStayTime().clear();   
			mm.txDeleteTeacherStayTime(mm.findStayTimeByEmplOid(
					member.getOid(), year, term), year, term);
			List<TeacherStayTime> tsts = processRequest(request, empl, year,
					term);
			System.out.println(empl+","+ formProps+","+ tsts+","+ year+","+ term+","+ needRecord);
			mm.txUpdateTeacherStayTime(empl, formProps, tsts, year, term, needRecord);
			if(needRecord==true){ // 258行無法執行，遮蔽後以259~263取代之 Leo20120320
				cm.executeSql(
						"Insert Into TeacherStayTimeModify (ParentOid, SchoolYear, SchoolTerm) "+
						"Values ('"+empl.getOid()+"','"+year+"','"+term+"')");
			}
			mm.txSaveStayTime(tsts);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.ModifySuccessful"));
			saveMessages(request, messages);
			return unspecified(mapping, form, request, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", "資料更新時程式發生錯誤,請稍後再試或洽電算中心,謝謝."));
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

	}

	/**
	 * 處理點選取消之方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("teacher.stayTime.btn.update", "update");
		map.put("Cancel", "cancel");
		return map;
	}

	public static List<String> getOrderInfo() {
		List<String> orders = new ArrayList<String>();
		for (int i = 0; i < 14; i++)
			orders.add(getOrderString(i));
		return orders;
	}

	private static String getOrderString(int index) {
		switch (index) {
			case 0:
				return "日 一";
			case 1:
				return "日 二";
			case 2:
				return "日 三";
			case 3:
				return "日 四";
			case 4:
				return "日 五";
			case 5:
				return "日 六";
			case 6:
				return "日 七";
			case 7:
				return "日 八";
			case 8:
				return "日 九";
			case 9:
				return "日 十";
			case 10:
				return "夜 一";
			case 11:
				return "夜 二";
			case 12:
				return "夜 三";
			case 13:
				return "夜 四";
			default:
				return "";
		}
	}
	
	public static List<String> getOrderInfo4Holiday() {
		List<String> orders = new ArrayList<String>();
		for (int i = 0; i < 14; i++)
			orders.add(getOrderString4Holiday(i));
		return orders;
	}
	
	private static String getOrderString4Holiday(int index) {
		switch (index) {
			case 0:
				return "日 一";
			case 1:
				return "日 二";
			case 2:
				return "日 三";
			case 3:
				return "日 四";
			case 4:
				return "日 五";
			case 5:
				return "日 六";
			case 6:
				return "日 七";
			case 7:
				return "日 八";
			case 8:
				return "日 九";
			case 9:
				return "日 十";
			case 10:
				return "日 十一";
			case 11:
				return "日 十二";
			case 12:
				return "日 十三";
			case 13:
				return "日 十四";
			default:
				return "";
		}
	}

	public static List<String> getTimeInfo() {
		List<String> times = new ArrayList<String>();
		for (int i = 0; i < 14; i++)
			times.add(getTimeInfoString(i));
		return times;
	}

	private static String getTimeInfoString(int index) {
		switch (index) {
			case 0:
				return "08：20 至 09：10";
			case 1:
				return "09：20 至10：10";
			case 2:
				return "10：20 至 11：10";
			case 3:
				return "11：20 至 12：10";
			case 4:
				return "12：10 至 13：20";
			case 5:
				return "13：20 至 14：10";
			case 6:
				return "14：20 至15：10";
			case 7:
				return "15：20 至16：10";
			case 8:
				return "16：20 至17：10";
			case 9:
				return "17：20 至18：30";
			case 10:
				return "18：30 至19：15";
			case 11:
				return "19：20 至 20：05";
			case 12:
				return "20：15 至 21：00";
			case 13:
				return "21：05 至21：50";
			default:
				return "";
		}
	}

	public static List<String> getTimeWeekendInfo() {
		List<String> times = new ArrayList<String>();
		for (int i = 0; i < 14; i++)
			times.add(getTimeWeekendInfoString(i));
		return times;
	}

	private static String getTimeWeekendInfoString(int index) {
		switch (index) {
			case 0:
				return "08：30 至 09：15";
			case 1:
				return "09：20 至10：05";
			case 2:
				return "10：15 至 11：00";
			case 3:
				return "11：05 至 11：50";
			case 4:
				return "12：55至 13：40";
			case 5:
				return "13：45 至 14：30";
			case 6:
				return "14：40 至15：25";
			case 7:
				return "15：30 至16：15";
			case 8:
				return "16：25 至17：10";
			case 9:
				return "17：15 至18：00";
			case 10:
				return "18：05 至18：50";
			case 11:
				return "&nbsp;";
			case 12:
				return "&nbsp;";
			case 13:
				return "&nbsp;";
			default:
				return "";
		}
	}

	// 將ResultSet轉成Array,方便前端顯示使用
	public static List<String[]> processStayTime(List<TeacherStayTime> tsts) {
		String[][] ret = new String[14][7];
		for (String[] tmp : ret) {
			Arrays.fill(tmp, String.valueOf("0"));
		}
		for (TeacherStayTime tst : tsts) {
			for (int i = 0; i < 14; i++) {
				ret[i][tst.getWeek() - (short) 1] = getNodeValue(tst, i)
						.toString();
			}
		}
		return Arrays.asList(ret);
	}

	private static Short getNodeValue(TeacherStayTime tst, int index) {
		switch (index) {
			case 0:
				return tst.getNode1();
			case 1:
				return tst.getNode2();
			case 2:
				return tst.getNode3();
			case 3:
				return tst.getNode4();
			case 4:
				return tst.getNode5();
			case 5:
				return tst.getNode6();
			case 6:
				return tst.getNode7();
			case 7:
				return tst.getNode8();
			case 8:
				return tst.getNode9();
			case 9:
				return tst.getNode10();
			case 10:
				return tst.getNode11();
			case 11:
				return tst.getNode12();
			case 12:
				return tst.getNode13();
			case 13:
				return tst.getNode14();
			default:
				return (short) 0;
		}
	}

	// 處理Request勾選資料
	private List<TeacherStayTime> processRequest(HttpServletRequest request,
			Empl empl, String year, String term) {

		int week = 7;
		String[] weekArr = { "mon", "tue", "wed", "thu", "fri", "sat", "sun" };
		List<TeacherStayTime> tsts = new LinkedList<TeacherStayTime>();
		TeacherStayTime tst = null;
		for (int i = 0; i < week; i++) { // start of Week
			tst = new TeacherStayTime();
			tst.setSchoolYear(year);
			tst.setSchoolTerm(term);
			tst.setWeek(Short.valueOf(String.valueOf(i + 1)));
			tst.setPos(Integer.valueOf(i));
			tst.setNode1(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 0)) ? "1" : "0"));
			tst.setNode2(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 1)) ? "1" : "0"));
			tst.setNode3(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 2)) ? "1" : "0"));
			tst.setNode4(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 3)) ? "1" : "0"));
			tst.setNode5(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 4)) ? "1" : "0"));
			tst.setNode6(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 5)) ? "1" : "0"));
			tst.setNode7(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 6)) ? "1" : "0"));
			tst.setNode8(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 7)) ? "1" : "0"));
			tst.setNode9(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 8)) ? "1" : "0"));
			tst.setNode10(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 9)) ? "1" : "0"));
			tst.setNode11(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 10)) ? "1" : "0"));
			tst.setNode12(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 11)) ? "1" : "0"));
			tst.setNode13(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 12)) ? "1" : "0"));
			tst.setNode14(Short.valueOf(StringUtils.isNotBlank(request
					.getParameter(weekArr[i] + 13)) ? "1" : "0"));
			tsts.add(tst);
		} // end of Week

		return processRemove(tsts, empl);
	}

	// 將各星期值均為'0'剔除掉,並與原資料做比對做增減
	private List<TeacherStayTime> processRemove(List<TeacherStayTime> datas,
			Empl empl) {

		List<TeacherStayTime> ret = new LinkedList<TeacherStayTime>();
		int i = 0;
		for (TeacherStayTime tst : datas) {
			if (tst.getNode1().toString().equals("1")
					|| tst.getNode2().toString().equals("1")
					|| tst.getNode3().toString().equals("1")
					|| tst.getNode4().toString().equals("1")
					|| tst.getNode5().toString().equals("1")
					|| tst.getNode6().toString().equals("1")
					|| tst.getNode7().toString().equals("1")
					|| tst.getNode8().toString().equals("1")
					|| tst.getNode9().toString().equals("1")
					|| tst.getNode10().toString().equals("1")
					|| tst.getNode11().toString().equals("1")
					|| tst.getNode12().toString().equals("1")
					|| tst.getNode13().toString().equals("1")
					|| tst.getNode14().toString().equals("1")) {
				tst.setEmpl(empl);
				tst.setPos(Integer.valueOf(i++));
				ret.add(tst);
			}
		}
		return ret;
	}

}
