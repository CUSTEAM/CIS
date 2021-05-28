package tw.edu.chit.util;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.struts.Globals;
import org.hibernate.criterion.Example;

import tw.edu.chit.dao.AdminDAO;
import tw.edu.chit.dao.CourseJdbcDAO;
import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.dao.StudAffairJdbcDAO;
import tw.edu.chit.gui.Menu;
import tw.edu.chit.gui.MenuItem;
import tw.edu.chit.model.Aborigine;
import tw.edu.chit.model.AmsAskLeave;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.CodeEmpl;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Entrno;
import tw.edu.chit.model.FeeCode;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.GraduateType;
import tw.edu.chit.model.InSchoolType;
import tw.edu.chit.model.LiteracyType;
import tw.edu.chit.model.MasterData;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.SysParameter;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;

import com.google.gdata.data.DateTime;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public class Toolket {

	protected static Hashtable<String, String> properties = null;

	private static Log log = LogFactory.getLog(Toolket.class);

	static {
		//TODO 這啥小?
		properties = new Hashtable<String, String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.exolab.jms.jndi.InitialContextFactory");
		properties.put(Context.PROVIDER_URL, IConstants.JMS_SERVER);
	}

	public static final String EncryptPassword(String clearText) {
		return clearText;
	}

	@SuppressWarnings("unchecked")
	public static final boolean TriggerMenuCollapse(HttpSession session,
			String menuItemName) {
		Menu menu = (Menu) session.getAttribute("Menu");
		if (menu != null) {
			List items = menu.getItems();
			MenuItem item;
			for (int i = 0; i < items.size(); i++) {
				item = (MenuItem) items.get(i);
				if (menuItemName.equals(item.getModule().getName())) {
					item.setCollapse(!item.isCollapse());
					if (item.getSubMenu() == null
							|| item.getSubMenu().getItems().size() == 0) {
						return true;
					} else {
						return item.isCollapse();
					}
				}
			}
		}
		return true;
	}

	public static final UserCredential GetUser(HttpSession session) {
		return (UserCredential) session.getAttribute("Credential");
	}

	/*
	 * public static String[] getSelectedIndexArrayFromCookie(HttpServletRequest
	 * request, String cookieName) { String[] index = null; Cookie[] cookies =
	 * request.getCookies(); for (int i=0; i < cookies.length; i++) { if
	 * (cookies[i].getName().equals(cookieName)) { String value =
	 * cookies[i].getValue(); index = value.substring(1,
	 * value.length()-1).split("\\|"); break; } } return index; }
	 */

	/*
	 * public static final List findMessages(String category) {
	 * 
	 * List messages = new ArrayList(); Map msg = new HashMap(); if
	 * ("Registration".equals(category)) { msg.put("startDate", "95-11-15");
	 * msg.put("sender", "[電算中心]"); msg.put("content", "學籍系統自即日起開放試用, 使用說明影音檔置於
	 * --" + "<BR>" + "<font
	 * class='blue_13'>\\\\192.192.230.244\\user\\各單位交換資料\\電算中心\\校務系統使用說明</font>"
	 * + "<BR>" + "請各位同仁自行透過網路芳鄰下載, 以 Media Player 收看"); messages.add(msg); } if
	 * ("Score".equals(category)) { msg.put("startDate", "95-11-30");
	 * msg.put("sender", "[電算中心]"); msg.put("content", "成績系統自即日起試用, 初期僅開放以下功能
	 * --" + "<BR>" + "<font class='blue_13'>" +
	 * "&nbsp;&nbsp;&nbsp;&nbsp;成績資料維護" + "<BR>" +
	 * "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期中成績輸入" + "<BR>" +
	 * "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期末成績輸入" + "<BR>" +
	 * "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;碩士班論文成績輸入" + "<BR>" +
	 * "&nbsp;&nbsp;&nbsp;&nbsp;歷史資料維護" + "<BR>" +
	 * "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;歷年成績維護" + "<BR>" +
	 * "</font>" + "<BR>"); messages.add(msg); } return messages; }
	 */
	public static String getCookie(HttpServletRequest request, String cookieName) {

		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(cookieName)) {
				// log.debug("=====> cookie " + cookieName + ":" +
				// cookies[i].getValue());
				return cookies[i].getValue();
			}
		}
		return "";
	}

	public static String getSelectedIndexFromCookie(HttpServletRequest request,
			String cookieName) {
		return getCookie(request, cookieName);
	}

	public static boolean isValueInCookie(String value, String cookie) {

		if (cookie.startsWith("all-")) {
			return (cookie.indexOf("-" + value + "-") == -1);
		} else {
			return (cookie.indexOf("|" + value + "|") != -1);
		}
	}

	/*
	 * Upgrade to support cookie name, see below public static void
	 * resetCheckboxCookie(HttpServletResponse response) { Cookie aCookie;
	 * aCookie = new Cookie("checkedIndex", "|"); aCookie.setPath("/");
	 * response.addCookie(aCookie); aCookie = new Cookie("checkCount", "0");
	 * aCookie.setPath("/"); response.addCookie(aCookie); }
	 */

	public static void setCookie(HttpServletResponse response,
			String cookieName, String value) {
		Cookie aCookie = new Cookie(cookieName, value);
		aCookie.setPath("/");
		response.addCookie(aCookie);
	}

	public static void setCheckboxCookie(HttpServletResponse response,
			String cookieName, String value, int ttlCount) {

		setCookie(response, cookieName + "Count", String.valueOf(ttlCount));
		if (value.length() <= 4000) {
			setCookie(response, cookieName, value);
		} else {
			setCookie(response, cookieName, value.substring(0, 4000));
		}

		/*
		 * // Handle cookie value larger than 500 setCookie(response,
		 * cookieName, value.substring(0, 500));
		 * 
		 * int extra = value.length() / 500 - 1; if (value.length() % 500 > 0)
		 * extra++; setCookie(response, cookieName + "Extra",
		 * String.valueOf(extra)); for (int i=1; i <= extra; i++) { int bound =
		 * Math.min(value.length(), 500 * (i+1)); setCookie(response, cookieName
		 * + String.valueOf(i), value.substring(500 * i, bound)); }
		 */
		/*
		 * Cookie aCookie; aCookie = new Cookie(cookieName, value);
		 * aCookie.setPath("/"); response.addCookie(aCookie); aCookie = new
		 * Cookie(cookieName + "Count", String.valueOf(ttlCount));
		 * aCookie.setPath("/"); response.addCookie(aCookie);
		 */
	}

	public static void resetCheckboxCookie(HttpServletResponse response,
			String cookieName) {

		Cookie aCookie;
		aCookie = new Cookie(cookieName, "|");
		aCookie.setPath("/");
		response.addCookie(aCookie);
		aCookie = new Cookie(cookieName + "Count", "0");
		aCookie.setPath("/");
		response.addCookie(aCookie);
	}

	/**
	 * Used to reset checkbox cookie with value length larger than 4000 bytes
	 * 
	 * @param response
	 * @param cookieName
	 * @param request
	 */
	public static void resetCheckboxCookie(HttpServletResponse response,
			String cookieName, HttpServletRequest request) {

		// setCheckboxCookie(response, cookieName, "|", 0);

		Cookie aCookie;
		aCookie = new Cookie(cookieName, "|");
		aCookie.setPath("/");
		response.addCookie(aCookie);
		aCookie = new Cookie(cookieName + "Count", "0");
		aCookie.setPath("/");
		response.addCookie(aCookie);

		if (!"".equals(getCookie(request, cookieName + "Extra"))) {
			setCookie(response, cookieName + "Extra", "");
			try {
				int extra = Integer.parseInt(getCookie(request, cookieName
						+ "Extra"));
				for (int i = 1; i <= extra; i++) {
					setCookie(response, cookieName + String.valueOf(i), "");
				}
			} catch (Exception e) {
			}
		}

	}

	public static void setAllCheckboxCookie(HttpServletResponse response,
			String cookieName, int ttlCount) {

		setCheckboxCookie(response, cookieName, "all-", ttlCount);

		/*
		 * Cookie aCookie; aCookie = new Cookie(cookieName, "all-");
		 * aCookie.setPath("/"); response.addCookie(aCookie); aCookie = new
		 * Cookie(cookieName + "Count", String.valueOf(ttlCount));
		 * aCookie.setPath("/"); response.addCookie(aCookie);
		 */
	}

	/*
	 * public static void setDisplayTable(HttpSession session, List table) {
	 * session.setAttribute("DisplayTable", table); }
	 */

	/*
	 * public static void setDisplayTable(HttpServletRequest request, List
	 * table) { request.setAttribute("DisplayTable", table); }
	 */
	public static void setPageAnchor(HttpServletRequest request,
			String anchorName) {
		request.setAttribute("anchor", anchorName);
	}

	public static boolean noContentPageExists(HttpSession session,
			HttpServletRequest request) {
		return (session.getAttribute("contentPage") == null)
				&& (request.getAttribute("contentPage") == null);
	}

	public static ResourceBundle getBundle(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
			if (locale != null) {
				// log.debug("======> locale=" + locale);
				// ResourceBundle bundle = ResourceBundle.getBundle("Messages",
				// locale);
				// log.debug("======> bundle=" + bundle.getLocale());
				return ResourceBundle.getBundle("messages.GLOBAL", locale);
			}
		}
		return ResourceBundle.getBundle("messages.GLOBAL", request.getLocale());
	}

	public static ResourceBundle getBundle(HttpServletRequest request,
			String resource) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
			if (locale != null) {
				// log.debug("======> locale=" + locale);
				// ResourceBundle bundle = ResourceBundle.getBundle("Messages",
				// locale);
				// log.debug("======> bundle=" + bundle.getLocale());
				return ResourceBundle.getBundle(resource, locale);
			}
		}
		return ResourceBundle.getBundle(resource, request.getLocale());
	}

	public static String printNativeDate(Date aDate, DateFormat dateFormat) {
		Calendar retDate = Calendar.getInstance();
		if (aDate == null)
			return "";
		if (Global.NativeYearBase == 0) {
			return dateFormat.format(aDate);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(aDate);
		/*
		 * Modify by Jason ; because of 02-29 problem if (dateFormat == null)
		 * dateFormat = Global.defaultDateFormat; calendar.add(Calendar.YEAR,
		 * -Global.NativeYearBase); String ret =
		 * dateFormat.format(calendar.getTime());
		 * 
		 * if (ret.startsWith("0")) ret = ret.substring(1); if
		 * (ret.startsWith("0")) ret = ret.substring(1);
		 */
		if (dateFormat != null) {
			retDate.set(calendar.get(Calendar.YEAR) - Global.NativeYearBase,
					calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
			// 原本是下面的Code,但會造成月份多加一,導致顯示錯誤(Oscar)
			// retDate.set(calendar.get(Calendar.YEAR)-Global.NativeYearBase,
			// calendar.get(Calendar.MONTH )+ 1, calendar.get(Calendar.DATE));
			return dateFormat.format(retDate.getTime());
		}
		String ret = (calendar.get(Calendar.YEAR) - Global.NativeYearBase)
				+ "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DATE);
		return ret;
	}

	public static String printNativeDate(Date aDate) {
		return printNativeDate(aDate, null);
	}

	public static String printMaskedNativeDate(Date aDate) {
		return printNativeDate(aDate, new SimpleDateFormat("yyyy'-XX-XX'"));
	}

	public static String printDate(Date aDate) {
		if (aDate == null)
			return "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(aDate);
		return printDate(calendar);
		/*
		 * String ret = ""; ret += String.valueOf(calendar.get(Calendar.YEAR) -
		 * Global.NativeYearBase); ret += "/"; ret +=
		 * String.valueOf(calendar.get(Calendar.MONTH) + 1); ret += "/"; ret +=
		 * String.valueOf(calendar.get(Calendar.DATE)); return ret;
		 */
	}

	public static String printDate(Calendar aDate) {
		if (aDate == null)
			return "";
		String ret = "";
		ret += String.valueOf(aDate.get(Calendar.YEAR) - Global.NativeYearBase);
		ret += "-";
		ret += String.valueOf(aDate.get(Calendar.MONTH) + 1);
		ret += "-";
		ret += String.valueOf(aDate.get(Calendar.DATE));
		// log.debug("========> date=" + ret);
		return ret;
	}

	/**
	 * Date Object轉成民國年
	 * 
	 * @param uDate
	 * @return
	 */
	public static String Date2Str(Date uDate) {
		if (uDate == null)
			return "";
		String ret = "";
		Calendar aDate = Calendar.getInstance();
		aDate.setTime(uDate);
		ret += String.valueOf(aDate.get(Calendar.YEAR) - Global.NativeYearBase);
		ret += "-";
		ret += String.valueOf(aDate.get(Calendar.MONTH) + 1);
		ret += "-";
		ret += String.valueOf(aDate.get(Calendar.DATE));
		// log.debug("========> date=" + ret);
		return ret;
	}

	/**
	 * Date Object轉成民國年
	 * 
	 * @commend 會捕0,無-
	 * @param uDate
	 * @return
	 */
	public static String Date2Str1(Date uDate) {
		if (uDate == null)
			return "";
		String ret = "";
		Calendar aDate = Calendar.getInstance();
		aDate.setTime(uDate);
		ret += String.valueOf(aDate.get(Calendar.YEAR) - Global.NativeYearBase);
		ret += (aDate.get(Calendar.MONTH) + 1) < 10 ? "0"
				+ (aDate.get(Calendar.MONTH) + 1)
				: aDate.get(Calendar.MONTH) + 1;
		ret += aDate.get(Calendar.DATE) >= 10 ? aDate.get(Calendar.DATE) : "0"
				+ aDate.get(Calendar.DATE);
		return ret;
	}

	public static String FullDate2Str(Date uDate) {
		if (uDate == null)
			return "";
		String ret = "";
		Calendar aDate = Calendar.getInstance();
		aDate.setTime(uDate);
		ret += String.valueOf(aDate.get(Calendar.YEAR));
		ret += "-";
		ret += String.valueOf(aDate.get(Calendar.MONTH) + 1);
		ret += "-";
		ret += String.valueOf(aDate.get(Calendar.DATE));
		// log.debug("========> date=" + ret);
		return ret;
	}

	public static String printTime(Date aDate, DateFormat dateFormat) {
		if (aDate == null)
			return "";
		if (dateFormat == null)
			dateFormat = Global.defaultDateFormat;
		return dateFormat.format(aDate);
	}

	public static Long date2Serial(Date aDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(aDate);
		return date2Serial(calendar);
	}

	public static Long date2Serial(Calendar aDate) {
		return (long) aDate.get(Calendar.YEAR) * 10000
				+ (aDate.get(Calendar.MONTH) + 1) * 100
				+ aDate.get(Calendar.DATE);
	}

	public static String Serial2YearMonth(Short yearMonth) {

		int year = yearMonth / 100;
		int month = yearMonth % 100;
		return String.valueOf(year) + "-" + String.valueOf(month);
	}

	/**
	 * 
	 * @param aDateString a date string in the format "yy-MM-dd" in which "yy"
	 *            is a native year
	 * @return a Date instance with year based on Global.NativeYearBase and time
	 *         truncated to 0:00AM
	 */
	public static Date parseDate(String aDateString) {

		aDateString = aDateString.trim();
		int year = 0, month = 0, day = 0;
		String[] date = aDateString.split("-");
		try {
			year = Integer.parseInt(date[0]);
			year += Global.NativeYearBase;
			month = Integer.parseInt(date[1]);
			day = Integer.parseInt(date[2]);
			if (month <= 0 || day <= 0)
				return null;
			if (month > 12 || day > 31)
				return null;
			Calendar aDate = Calendar.getInstance();
			aDate.set(year, month - 1, day, 0, 0, 0);
			return aDate.getTime();
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * 民國年轉成Date Object
	 * 
	 * @param str Format yy-mm-dd(民國年月日)
	 * @return
	 */
	public static java.sql.Date parseNativeDate(String str) {
		String tmp = parseNativeString(str);
		//fix java 1.6.x error for parsing java.sql.Date(String)
		//in java 1.6.x String formate must be yyyy-mm-dd but not yyyy-[m]m-[d]d
		if(tmp == null)	return null;
		String[] date = tmp.trim().split("-");
		tmp = date[0] + "-";
		if(date[1].length()==1){
			tmp += "0" + date[1] + "-";
		}else{
			tmp += date[1] + "-";
		}
		if(date[2].length()==1){
			tmp += "0" + date[2];
		}else{
			tmp += date[2];
		}
		return java.sql.Date.valueOf(tmp);
	}

	/**
	 * 民國年轉成西元年String
	 * 
	 * @param str Format yy-mm-dd(民國年月日)
	 * @return
	 */
	public static String parseNativeString(String str) {
		int index = StringUtils.indexOf(str, "-");
		String tmp = StringUtils.isBlank(str) ? null
				: (Integer.parseInt(StringUtils.substring(str, 0, index)) + Global.NativeYearBase)
						+ StringUtils.substring(str, index);
		return tmp;
	}

	public static DateTime parseDateToGoogleDateTime(Date d) {
		return DateTime.parseDate(new SimpleDateFormat("yyyy-MM-dd").format(d));
	}

	public static Date parseGoogleDateTimeToDate(DateTime dt) {
		// DateTime Format: 2010-04-06 06:05
		String dtString = StringUtils.substringBefore(dt.toUiString(), " ");
		return java.sql.Date.valueOf(dtString);
	}

	public static String parseGoogleDateTimeToString(DateTime dt) {
		Date d = parseGoogleDateTimeToDate(dt);
		return Date2Str(d);
	}

	/**
	 * 
	 * @param aDateString a date string in the format "yyMMdd" in which "yy" is
	 *            a native year
	 * @return a Date instance with year based on Global.NativeYearBase and time
	 *         truncated to 0:00AM
	 */
	public static Date parseDateSerial(String aDateString) {

		aDateString = aDateString.trim();
		int year = 0, month = 0, day = 0;
		// String[] date = aDateString.split("-");
		try {
			year = Integer.parseInt(aDateString.substring(0, aDateString
					.length() - 4));
			year += Global.NativeYearBase;
			month = Integer.parseInt(aDateString.substring(
					aDateString.length() - 4, aDateString.length() - 2));
			day = Integer.parseInt(aDateString
					.substring(aDateString.length() - 2));
			if (month <= 0 || day <= 0)
				return null;
			if (month > 12 || day > 31)
				return null;
			Calendar aDate = Calendar.getInstance();
			aDate.set(year, month - 1, day, 0, 0, 0);
			return aDate.getTime();
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	public static Short parseYearMonth(String input) {

		String[] date = input.trim().split("-");
		short year = 0, month = 0;
		try {
			year = Short.parseShort(date[0]);
			month = Short.parseShort(date[1]);
			if (month <= 0 || month > 12)
				return null;
			return (short) (year * 100 + month);
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	public static String printNumber(Object aNumber, int totalSpace) {

		String ret = aNumber.toString();
		for (int i = 1; i <= totalSpace - ret.length(); i++) {
			ret = "&nbsp;" + ret;
		}
		// log.debug("========> number='" + ret + "'");
		return ret;
	}

	public static boolean isSameTime(Date date1, Date date2) {

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE)
				&& cal1.get(Calendar.HOUR_OF_DAY) == cal2
						.get(Calendar.HOUR_OF_DAY)
				&& cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}

	public static String getSex(String code, HttpServletRequest request) {
		if ("1".equals(code) || "m".equalsIgnoreCase(code)) {
			return getBundle(request).getString("Male");
		} else if ("2".equals(code) || "f".equalsIgnoreCase(code)) {
			return getBundle(request).getString("Female");
		}
		return "";
	}

	public static String getSex(String code) {
		if ("1".equals(code) || "m".equalsIgnoreCase(code)) {
			return "男";
		} else if ("2".equals(code) || "f".equalsIgnoreCase(code)) {
			return "女";
		}
		return "";
	}

	public static void refreshCampusMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> campuses = dao.findAllCampuses();
		synchronized (Global.Campus) {
			Global.Campus.clear();
			Global.CampusList.clear();
			for (Code5 campus : campuses) {
				Global.Campus.put(campus.getIdno(), campus.getName());
				Global.CampusList.add(campus);
			}
		}
	}

	public static void refreshSchoolMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> schools = dao.findAllSchools();
		synchronized (Global.School) {
			Global.School.clear();
			Global.SchoolList.clear();
			for (Code5 school : schools) {
				Global.School.put(school.getIdno(), school.getName());
				Global.SchoolList.add(school);
			}
		}
	}

	public static void refreshDeptMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> depts = dao.findAllDepts();
		synchronized (Global.Dept) {
			Global.Dept.clear();
			Global.DeptList.clear();
			for (Code5 dept : depts) {
				Global.Dept.put(dept.getIdno(), dept.getName());
				Global.DeptList.add(dept);
			}
		}
	}

	/**
	 * 取得所有碩士班正式系所名稱
	 */
	public static void refreshMasterDeptMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> depts = dao.findAllMasterDepts();
		synchronized (Global.MasterDept) {
			Global.MasterDept.clear();
			for (Code5 dept : depts) {
				Global.MasterDept.put(dept.getIdno(), dept.getName());
			}

		}
	}

	public static String getCampus(String campusNo) {
		if (campusNo == null)
			return "";
		return Global.Campus.getProperty(campusNo, "");
	}

	public static String getSchool(String schoolNo) {
		if (schoolNo == null)
			return "";
		return Global.School.getProperty(schoolNo, "");
	}

	public static String getSchoolName(String departClass) {
		if (departClass == null)
			return "";
		return Global.School.getProperty(StringUtils.substring(departClass, 1,
				3), "");
	}

	public static String getSchoolFormalName(String departClass) {
		if (StringUtils.isBlank(departClass))
			return "";
		if (departClass.startsWith("172"))
			return "附設"
					+ Global.School.getProperty(StringUtils.substring(
							departClass, 1, 3), "");
		else if (departClass.startsWith("132"))
			return "附設專科進修學校";
		return Global.School.getProperty(StringUtils.substring(departClass, 1,
				3), "");
	}

	/**
	 * 取得一般系所名稱
	 */
	public static String getDept(String deptNo) {
		if (deptNo == null)
			return "";
		return Global.Dept.getProperty(deptNo, "");
	}

	public static String getDepartName(String departClass) {
		if (isMasterClass(departClass))
			return getMasterDepartName(departClass);

		String[] except = { "12", "15", "22", "92", "32", "A2", "B2" };
		if (StringUtils.isBlank(departClass))
			return "";
		if ("212".equals(StringUtils.substring(departClass, 0, 3))) {
			if ("A".equals(StringUtils.substring(departClass, 3, 4)))
				return "航空機械科";
			else if ("B".equals(StringUtils.substring(departClass, 3, 4)))
				return "航空電子科";
			else if ("C".equals(StringUtils.substring(departClass, 3, 4)))
				return "航空管理科";
		}
		String name;
		if(departClass.length()==6)
		name = Global.Dept.getProperty(StringUtils.substring(
				departClass, 3, 4), "");
		else
		name = Global.Dept.getProperty(StringUtils.substring(
				departClass, 3, 5), "");
		if (!"1220".equals(departClass)
				&& ArrayUtils.contains(except, StringUtils.substring(
						departClass, 1, 3)))
			return StringUtils.substring(name, 0, name.length() - 1) + "科";
		return name;
	}

	/**
	 * 取得碩士班正式系所名稱
	 */
	public static String getMasterDepartName(String departClass) {
		if (StringUtils.isBlank(departClass))
			return "";
		//System.out.println(departClass+"--"+departClass.length());
		if(departClass.length()=='7')
		return Global.MasterDept.getProperty(StringUtils.substring(departClass,
				4, 5), "");
		else
		return Global.MasterDept.getProperty(StringUtils.substring(departClass,
				3, 4), "");
	}

	public static void refreshClassMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Clazz> classes = dao.findAllClasses();
		refreshCampusMap();
		synchronized (Global.ClassFullName) {
			Global.ClassFullName.clear();
			for (Clazz clazz : classes) {
				Global.ClassFullName.put(clazz.getClassNo(), Global.Campus
						.getProperty(clazz.getCampusNo(), "")
						+ clazz.getClassName());
			}
		}
		refreshAllClassJavaScriptArray(classes.toArray(new Clazz[0]));
	}

	/**
	 * 將班級代碼轉為完整名稱
	 * 
	 * @param classNo 班級代碼
	 * @return java.lang.String 完整名稱
	 */
	public static String getClassFullName(String classNo) {
		if (classNo == null)
			return "";
		return Global.ClassFullName.getProperty(classNo, "");
	}

	public static String getFeeKind(String kind) {
		if (StringUtils.isBlank(kind))
			return null;

		switch (Integer.parseInt(kind)) {
			case 1:
				return "學雜費";

			case 2:
				return "代辦費";

			default:
				return null;

		}
	}

	public static String getTransferKind(String kind) {
		if (StringUtils.isBlank(kind))
			return null;

		switch (Integer.parseInt(kind)) {
			case 1:
				return "助學貸款";

			case 2:
				return "學雜費";

			case 3:
				return "工讀費";

			case 4:
				return "退費";

			case 5:
				return "其他";
				
			case 6:
				return "獎學金";
				
			case 7:
				return "網路選課退費";
				
			case 8:
				return "住宿生保證金退費";
				
			case 9:
				return "新生獎學金發放";
				
			case 10:
				return "原住民獎學金";
			
			case 11:
				return "學產助學金";
				
			case 12:
				return "生活助學金";
				
			case 13:
				return "土地銀行獎學金";

			default:
				return null;
		}
	}

	/**
	 * Build a map Global.AllClassJavaScriptArray with keys "schoolName",
	 * "schoolId", "deptName", "deptId", "className", "classId" and the
	 * correspondent string of JavaScript array initialization statement to
	 * support dynamic update of HTML <SELECT> quadruple of campuse, school,
	 * dept and class for all classes
	 */
	private static void refreshAllClassJavaScriptArray(Clazz[] classAry) {

		// MemberDAO dao = (MemberDAO)Global.context.getBean("memberDAO");
		// Clazz[] classAry = dao.findAllClasses().toArray(new Clazz[0]);

		List<Code5> campuses = new ArrayList<Code5>();
		List<Code5[]> schools = new ArrayList<Code5[]>();
		List<Code5[][]> depts = new ArrayList<Code5[][]>();
		List<Clazz[][][]> classes = new ArrayList<Clazz[][][]>();

		MemberManager manager = (MemberManager) Global.context
				.getBean("memberManager");
		manager.createClassInChargeDataStructure(classAry, campuses, schools,
				depts, classes);
		synchronized (Global.AllClassJavaScriptArray) {
			Global.AllClassJavaScriptArray.clear();
			UserCredential.buildJsArraySchools(Global.AllClassJavaScriptArray,
					schools.toArray(new Code5[0][0]));
			UserCredential.buildJsArrayDepts(Global.AllClassJavaScriptArray,
					depts.toArray(new Code5[0][0][0]));
			UserCredential.buildJsArrayClasses(Global.AllClassJavaScriptArray,
					classes.toArray(new Clazz[0][0][0][0]));
		}
	}

	public static void refreshStatusMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> status = dao.findCode5ByCategory("Status");
		Code5 code5;
		synchronized (Global.Status) {
			Global.Status.clear();
			Global.StatusList.clear();
			for (Iterator<Code5> statusIter = status.iterator(); statusIter
					.hasNext();) {
				code5 = statusIter.next();
				Global.Status.put(code5.getIdno(), code5.getName());
				Global.StatusList.add(code5);
			}
		}
	}

	public static String getStatus(String statusNo, boolean skipNormal) {
		if (statusNo == null)
			return "";
		if (skipNormal && statusNo.equals(""))
			return "";
		return Global.Status.getProperty(statusNo, "");
	}

	public static void refreshIdentityMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> ident = dao.findCode5ByCategory("Identity");
		Code5 code5;
		synchronized (Global.Identity) {
			Global.Identity.clear();
			Global.IdentityList.clear();
			for (Iterator<Code5> identIter = ident.iterator(); identIter
					.hasNext();) {
				code5 = identIter.next();
				Global.Identity.put(code5.getIdno(), code5.getName());
				Global.IdentityList.add(code5);
			}
		}
	}

	public static String getIdentity(String identNo) {
		if (identNo == null)
			return "";
		return Global.Identity.getProperty(identNo, "");
	}

	public static void refreshGroupMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> group = dao.findCode5ByCategory("Group");
		Code5 code5;
		synchronized (Global.Group) {
			Global.Group.clear();
			Global.GroupList.clear();
			for (Iterator<Code5> groupIter = group.iterator(); groupIter
					.hasNext();) {
				code5 = groupIter.next();
				Global.Group.put(code5.getIdno(), code5.getName());
				Global.GroupList.add(code5);
			}
		}
	}

	public static String getGroup(String groupNo) {
		if (groupNo == null)
			return "";
		return Global.Group.getProperty(groupNo, "");
	}

	public static void refreshStatusCauseMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> cause = dao.findCode5ByCategory("Cause");
		Code5 code5;
		synchronized (Global.StatusCause) {
			Global.StatusCause.clear();
			Global.StatusCauseList.clear();
			for (Iterator<Code5> causeIter = cause.iterator(); causeIter
					.hasNext();) {
				code5 = causeIter.next();
				Global.StatusCause.put(code5.getIdno(), code5.getName());
				Global.StatusCauseList.add(code5);
			}
		}
	}

	public static String getStatusCause(String causeNo) {
		if (causeNo == null)
			return "";
		return Global.StatusCause.getProperty(causeNo, "");
	}

	public static void refreshEmpStatusMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> status = dao.findCode5ByCategory("EmpStatus");
		synchronized (Global.EmpStatus) {
			Global.EmpStatus.clear();
			Global.EmpStatusList.clear();
			for (Code5 code5 : status) {
				Global.EmpStatus.put(code5.getIdno(), code5.getName());
				Global.EmpStatusList.add(code5);
			}
		}
	}

	public static String getEmpStatus(String statusNo, boolean skipNormal) {
		if (statusNo == null)
			return "";
		if (skipNormal && statusNo.equals(""))
			return "";
		return Global.EmpStatus.getProperty(statusNo, "");
	}

	public static void refreshEmpStatusCauseMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<CodeEmpl> causes = dao.findCodeEmplByCategory("StatusCause");
		synchronized (Global.EmpStatusCause) {
			Global.EmpStatusCause.clear();
			Global.EmpStatusCauseList.clear();
			for (CodeEmpl code : causes) {
				Global.EmpStatusCause.put(code.getIdno(), code.getName());
				Global.EmpStatusCauseList.add(code);
			}
		}
	}

	public static String getEmpStatusCause(String causeNo, boolean skipNormal) {
		if (causeNo == null)
			return "";
		if (skipNormal && causeNo.equals(""))
			return "";
		return Global.EmpStatusCause.getProperty(causeNo, "");
	}

	public static void refreshEmpCategoryMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> status = dao.findCode5ByCategory("EmpCategory");
		synchronized (Global.EmpCategory) {
			Global.EmpCategory.clear();
			Global.EmpCategoryList.clear();
			for (Code5 code5 : status) {
				Global.EmpCategory.put(code5.getIdno(), code5.getName());
				Global.EmpCategoryList.add(code5);
			}
		}
	}

	public static String getEmpCategory(String categoryNo) {
		if (categoryNo == null)
			return "";
		return Global.EmpCategory.getProperty(categoryNo, "");
	}

	public static void refreshEmpUnitMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<CodeEmpl> units = dao.findCodeEmplByCategory2("Unit");
		synchronized (Global.EmpUnit) {
			Global.EmpUnit.clear();
			Global.EmpUnitList.clear();
			for (CodeEmpl code : units) {
				Global.EmpUnit.put(code.getIdno(), code.getName());
				Global.EmpUnitList.add(code);
			}
		}
	}

	/**
	 * 取得聘僱單位中文名稱
	 * 
	 * @param unitNo 聘僱單位代碼
	 * @return java.lang.String 聘僱單位中文名稱
	 * 
	 */
	public static String getEmpUnit(String unitNo) {
		if (unitNo == null)
			return "";
		return Global.EmpUnit.getProperty(unitNo, "");
	}

	public static void refreshEmpRoleMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		// List<CodeEmpl> roles = dao.findCodeEmplByCategory2("Role");
		List<CodeEmpl> roles = dao.findCodeEmplByCategory("TeacherRole");
		roles.addAll(dao.findCodeEmplByCategory("EmplRoleDirector"));
		roles.addAll(dao.findCodeEmplByCategory("EmplRoleStaff"));
		synchronized (Global.EmpRole) {
			Global.EmpRole.clear();
			Global.EmpRoleList.clear();
			for (CodeEmpl code : roles) {
				Global.EmpRole.put(code.getIdno(), code.getName());
				Global.EmpRoleList.add(code);
			}
		}
	}

	public static String getEmpRole(String unitNo) {
		if (unitNo == null)
			return "";
		return Global.EmpRole.getProperty(unitNo, "");
	}

	public static void refreshEmpDegreeMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<CodeEmpl> roles = dao.findCodeEmplByCategory("Degree");
		synchronized (Global.EmpDegree) {
			Global.EmpDegree.clear();
			Global.EmpDegreeList.clear();
			for (CodeEmpl code : roles) {
				Global.EmpDegree.put(code.getIdno(), code.getName());
				Global.EmpDegreeList.add(code);
			}
		}
	}

	public static String getEmpDegree(String degreeNo) {
		if (degreeNo == null)
			return "";
		return Global.EmpDegree.getProperty(degreeNo, "");
	}

	public static void refreshEmpTutorMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<CodeEmpl> roles = dao.findCodeEmplByCategory("Tutor");
		synchronized (Global.EmpTutor) {
			Global.EmpTutor.clear();
			Global.EmpTutorList.clear();
			for (CodeEmpl code : roles) {
				Global.EmpTutor.put(code.getIdno(), code.getName());
				Global.EmpTutorList.add(code);
			}
		}
	}

	public static String getEmpTutor(String tutorNo) {
		if (tutorNo == null)
			return "";
		return Global.EmpTutor.getProperty(tutorNo, "");
	}

	public static void refreshEmpDirectorMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<CodeEmpl> roles = dao.findCodeEmplByCategory("EmplRoleDirector");
		synchronized (Global.EmpDirector) {
			Global.EmpDirector.clear();
			Global.EmpDirectorList.clear();
			for (CodeEmpl code : roles) {
				Global.EmpDirector.put(code.getIdno(), code.getName());
				Global.EmpDirectorList.add(code);
			}
		}
	}

	public static String getEmpDirector(String directorNo) {
		if (directorNo == null)
			return "";
		return Global.EmpDirector.getProperty(directorNo, "");
	}

	public static void refreshCourseOptMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> courseopt = dao.findCode5ByCategory("CourseOpt");
		Code5 code5;
		synchronized (Global.CourseOpt) {
			Global.CourseOpt.clear();
			Global.CourseOptList.clear();
			for (Iterator<Code5> courseoptIter = courseopt.iterator(); courseoptIter
					.hasNext();) {
				code5 = courseoptIter.next();
				Global.CourseOpt.put(code5.getIdno(), code5.getName());
				Global.CourseOptList.add(code5);
			}
		}
	}

	public static String getCourseOpt(String optNo) {
		if (optNo == null)
			return "";
		return Global.CourseOpt.getProperty(optNo, "");
	}

	public static void refreshCourseTypeMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> coursetype = dao.findCode5ByCategory("CourseType");
		Code5 code5;
		synchronized (Global.CourseType) {
			Global.CourseType.clear();
			Global.CourseTypeList.clear();
			for (Iterator<Code5> coursetypeIter = coursetype.iterator(); coursetypeIter
					.hasNext();) {
				code5 = coursetypeIter.next();
				Global.CourseType.put(code5.getIdno(), code5.getName());
				Global.CourseTypeList.add(code5);
			}
		}

	}

	public static String getCourseType(String courseType) {
		if (courseType == null)
			return "";
		return Global.CourseType.getProperty(courseType, "");
	}

	/*
	 * public static void refreshCourses(){ CourseDAO dao =
	 * (CourseDAO)Global.context.getBean("courseDAO"); Global.Courses.clear();
	 * 
	 * List<Csno> csnos=dao.getCsnoForOpenCs(); Global.Courses.addAll(csnos); }
	 */

	/*
	 * public static String getEmplType(String emplType){ if (emplType == null)
	 * return ""; return Global.EmplType.getProperty(emplType, ""); }
	 */

	/*
	 * public static void refreshEmpls(){ CourseDAO dao =
	 * (CourseDAO)Global.context.getBean("courseDAO"); Global.Employees.clear();
	 * List<Empl> empls=dao.getEmployeeForOpenCS();
	 * Global.Employees.addAll(empls);
	 * 
	 * System.out.println("hi! "+Global.Employees.size()); }
	 */
	/*
	 * public static String getClassesType(String Cleasses){ if (Cleasses ==
	 * null) return ""; return Global.EmplType.getProperty(Cleasses, ""); }
	 */
	/*
	 * public static void refreshClasses(){ CourseDAO dao =
	 * (CourseDAO)Global.context.getBean("courseDAO"); Global.Classes.clear();
	 * List<Clazz> classes=dao.getClassForOpenCs();
	 * Global.Classes.addAll(classes); }
	 */

	@SuppressWarnings("unchecked")
	public static void refreshSystemParameters() {
		ScoreDAO dao = (ScoreDAO) Global.context.getBean("scoreDAO");
		List<SysParameter> parameters = dao
				.submitQuery("select s From SysParameter s");
		log.debug("========> parametersize='" + parameters.size() + "'");
		SysParameter para;
		synchronized (Global.SysParameters) {
			Global.SysParameters.clear();
			for (Iterator<SysParameter> paraIter = parameters.iterator(); paraIter
					.hasNext();) {
				para = paraIter.next();
				log.debug("========> Name&Value='" + para.getName() + "' , '"
						+ para.getValue() + "'");
				Global.SysParameters.put(para.getName(), para.getValue());
			}
		}
	}

	public static String getSysParameter(String paraName) {
		if (paraName == null)
			return "";
		// log.debug("========> getparameter='" + paraName + " , " +
		// Global.SysParameters.getProperty(paraName, "") + "'");
		return Global.SysParameters.getProperty(paraName, "");
	}

	public static void refreshTimeOffMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> timeoff = dao.findCode5ByCategory("TimeOff");
		Code5 code5;
		synchronized (Global.TimeOff) {
			Global.TimeOff.clear();
			Global.TimeOffList.clear();
			for (Iterator<Code5> timeoffIter = timeoff.iterator(); timeoffIter
					.hasNext();) {
				code5 = timeoffIter.next();
				Global.TimeOff.put(code5.getIdno(), code5.getName());
				Global.TimeOffList.add(code5);
			}
		}
	}

	public static String getTimeOff(String idno) {
		if (idno == null || idno.equals(""))
			return "";
		return Global.TimeOff.getProperty(idno, "");
	}

	public static void refreshAborigineMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Aborigine> abors = dao.getAborigineBy(new Aborigine());
		synchronized (Global.Aborigine) {
			Global.Aborigine.clear();
			Global.AborigineList.clear();
			for (Aborigine abor : abors) {
				Global.Aborigine.put(abor.getCode(), abor.getName());
				Global.AborigineList.add(abor);
			}
		}
	}

	public static String getAborigine(String code) {
		if (StringUtils.isBlank(code))
			return "";
		return Global.Aborigine.getProperty(code, "");
	}

	public static void refreshFeeCodeMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<FeeCode> codes = dao.getFeeCodeBy(new FeeCode());
		synchronized (Global.FeeCode) {
			Global.FeeCode.clear();
			Global.FeeCodeList.clear();
			for (FeeCode code : codes) {
				Global.FeeCode.put(code.getNo(), code.getName().trim());
				Global.FeeCodeList.add(code);
			}
		}
	}

	public static void refreshLiteracyType() {
		CourseManager cm = (CourseManager) Global.context
				.getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		List<LiteracyType> lts = cm.findLiteracyTypesBy(new LiteracyType());
		synchronized (Global.LiteracyType) {
			Global.LiteracyType.clear();
			Global.LiteracyTypeList.clear();
			for (LiteracyType lt : lts) {
				Global.LiteracyType.put(lt.getCode(), lt.getName());
				Global.LiteracyTypeList.add(lt);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void refreshAmsAskLeave() {
		AdminManager am = (AdminManager) Global.context
				.getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		AmsAskLeave leave = new AmsAskLeave();
		Example example = Example.create(leave);
		List<AmsAskLeave> leaves = (List<AmsAskLeave>) am.findSQLWithCriteria(
				AmsAskLeave.class, example, null, null);
		synchronized (Global.AmsAskLeaveList) {
			Global.AmsAskLeave.clear();
			Global.AmsAskLeaveList.clear();
			for (AmsAskLeave l : leaves) {
				Global.AmsAskLeave.put(l.getId(), l.getName());
				Global.AmsAskLeaveList.add(l);
			}
		}
	}
	
	public static String getAmsAskLeaveXx(String id) {
		if (StringUtils.isBlank(id))
			return "";
		return Global.AmsAskLeave.getProperty(id, "");
	}

	public static String getLiteracyType(String literacyType) {
		if (StringUtils.isBlank(literacyType))
			return "";
		return Global.LiteracyType.getProperty(literacyType, "");
	}

	public static String getFeeCode(String code) {
		if (StringUtils.isBlank(code))
			return "";
		return Global.FeeCode.getProperty(code, "");
	}

	public static InSchoolType processInWay(String inWay) {
		switch (inWay.charAt(0)) {
			case '0':
				return InSchoolType.TO_APPLY;
			case '1':
				return InSchoolType.TO_COMMEND;
			case '2':
				return InSchoolType.TO_UNITE;
			case '3':
				return InSchoolType.TO_INDEPENDENT;
			default:
				return null;
		}
	}

	public static GraduateType processGradeType(String gradeType) {
		switch (gradeType.charAt(0)) {
			case '0':
				return GraduateType.TO_GRADUATE;
			case '1':
				return GraduateType.TO_STUDY_IN_SCHOOL;
			case '2':
				return GraduateType.TO_COMPLETE_STUDY;
			default:
				return null;
		}
	}

	public static String getDocProcess(String status) {

		if (StringUtils.isBlank(status))
			return IConstants.AMSDocProcessNone;

		switch (status.charAt(0)) {
			case '0':
				return IConstants.AMSDocProcessReject;

			case '1':
				return IConstants.AMSDocProcessOK;

			case '2':
				return IConstants.AMSDocRevokeOK;

			case '3':
				return IConstants.AMSDocProcessForceOK;

			case '8':
				return IConstants.AMSDocProcessAllRest;

			case '9':
				return IConstants.AMSDocProcessHalfRest;

			default:
				return "";
		}
	}

	public static String getAmsDocType(String docType) {
		if (StringUtils.isBlank(docType))
			return null;

		switch (Integer.parseInt(docType)) {
			case 1:
				return "請假";

			case 2:
				return "加班";

			case 3:
				return "補登";

			case 4:
				return "請假撤銷";

			default:
				return "";
		}
	}

	public static void refreshBonusPenaltyMap() {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		List<Code5> BonusPenalty = dao.findCode5ByCategory("BonusPenalty");
		Code5 code5;
		synchronized (Global.BonusPenalty) {
			Global.BonusPenalty.clear();
			Global.BonusPenaltyCodeList.clear();
			for (Iterator<Code5> bpIter = BonusPenalty.iterator(); bpIter
					.hasNext();) {
				code5 = bpIter.next();
				Global.BonusPenalty.put(code5.getIdno(), code5.getName());
				Global.BonusPenaltyCodeList.add(code5);
			}
		}
	}

	public static String getBonusPenalty(String idno) {
		if (idno == null || idno.equals(""))
			return "";
		return Global.BonusPenalty.getProperty(idno, "");
	}

	/**
	 * 將課程英文名稱做字首大寫轉換,不包括英文之Stop Words
	 * 
	 * @param courseEname Course English Name
	 * @return java.util.String 轉換完之課程英文名稱
	 */
	public static String processEnglishCourseName(String courseEname) {
		StringBuffer buffer = new StringBuffer();
		if (StringUtils.isBlank(courseEname))
			return "";
		else {
			int index = 0;
			String[] words = StringUtils.split(courseEname, " ");
			for (String word : words) {
				if (!NumberUtils.isNumber(word)) {
					if (ArrayUtils.contains(IConstants.ENGLISH_STOP_WORD, word)
							&& index != 0) {
						buffer.append(word).append(" ");
					} else {
						buffer.append(StringUtils.capitalize(word)).append(" ");
					}
				} else {
					buffer.append(word).append(" ");
				}
				index++;
			}
			return StringUtils.trim(buffer.toString());
		}

	}

	public static class Random {

		List<Integer> list = null;

		/**
		 * 建立不重覆之亂數值
		 * 
		 * @author Oscar Wei
		 * @param randomSize 需產生亂數之數量
		 */
		@SuppressWarnings("unchecked")
		public Random(int randomSize) {
			list = new ArrayList<Integer>();
			for (int i = 1; i <= randomSize; i++) {
				list.add(new Integer(i));
			}
		}

		public int getRandom() {
			int currentRandomSize = list.size();
			if (currentRandomSize > 0) {
				return ((Integer) list.remove((int) (currentRandomSize * Math
						.random()))).intValue();
			}
			return -1;
		}

	}

	/**
	 * 將班級代碼年級減一
	 * 
	 * @commend 此Method是為建立基本選課時學生未升級所使用
	 * @param classNo 班級代碼
	 * @return 年級減一之班級代碼
	 */
	public static String processClassNoDown(String classNo) {
		if (StringUtils.isBlank(classNo))
			return "";
		int grade = Integer.parseInt(StringUtils.substring(classNo, 4, 5)) - 1;
		return StringUtils.substring(classNo, 0, 4) + grade
				+ StringUtils.substring(classNo, 5);
	}

	/**
	 * 將班級代碼年級加一
	 * 
	 * @commend 此Method是為建立基本選課時學生未升級所使用
	 * @param classNo 班級代碼
	 * @return 年級加一之班級代碼
	 */
	public static String processClassNoUp(String classNo) {
		/*
		if (StringUtils.isBlank(classNo))
			return "";
		int grade = Integer.parseInt(StringUtils.substring(classNo, 4, 5)) + 1;
		return StringUtils.substring(classNo, 0, 4) + grade
				+ StringUtils.substring(classNo, 5);
		*/
		if (StringUtils.isBlank(classNo))
			return "";
		int grade=0;
		if(classNo.length()==7) {
			grade = Integer.parseInt(StringUtils.substring(classNo, 5, 6)) + 1;
			return StringUtils.substring(classNo, 0, 5) + grade + StringUtils.substring(classNo, 6);
		}
		if(classNo.length()==6) {
			grade = Integer.parseInt(StringUtils.substring(classNo, 4, 5)) + 1;
			return StringUtils.substring(classNo, 0, 4) + grade + StringUtils.substring(classNo, 5);
		}
		return "";
	}

	public static void sleeping(int msec) {
		long startTime = System.currentTimeMillis();
		for (;;) {
			if ((System.currentTimeMillis() - startTime) > msec)
				break;
		}
		return;
	}

	/**
	 * 判斷是否為新竹之學生?
	 * 
	 * @param departClass 學生班級代碼
	 * @return boolean 新竹學生傳回true
	 */
	public static boolean isHsinChuStudent(String departClass) {
		return "2".equals(departClass.substring(0, 1));
	}

	/**
	 * 取得學生預設Email Address
	 * 
	 * @param student tw.edu.chit.model.Student Object
	 * @param schoolType D or N or H
	 * @return String Email Address
	 */
	public static String getDefaultStudentEmail(Student student,
			String schoolType) {
		StringBuffer studentEmail = new StringBuffer("s"
				+ student.getStudentNo().toLowerCase());
		// if ("96".equals(StringUtils.substring(student.getStudentNo(), 0, 2)))
		// {
		// return isHsinChuStudent(student.getDepartClass()) ? studentEmail
		// .append("@ccs.hc.cust.edu.tw").toString() : studentEmail
		// .append("@ccs.cust.edu.tw").toString();
		// } else
		return isHsinChuStudent(student.getDepartClass()) ? studentEmail
				.append("@ccs.hc.cust.edu.tw").toString() : studentEmail
				.append("@ccs.cust.edu.tw").toString();
	}

	@SuppressWarnings("unchecked")
	/**
	 * 判斷是否為畢業班(不包含學期部分,延修班不納入應屆畢)
	 * 
	 * @param departClass String
	 * 
	 * @return boolean true or false
	 */
	public static boolean chkIsGraduateClass(String departClass) {
		boolean isGraduateClass = false;

		// 例外狀況 132D23 進專資管二丙, 1B2N21 半導體產業專班
		if (departClass.length() < 6) {
			return false;
		} else if (!Toolket.isNumeric(departClass.substring(4, 5))) {
			return false;
		} else if (departClass.equalsIgnoreCase("132D23"))
			return false;
		else if (departClass.equalsIgnoreCase("132D33"))
			return true;
		else if (departClass.equalsIgnoreCase("1B2N21"))
			return false;
		else if (departClass.equalsIgnoreCase("1B2N31"))
			return true;		
		/* 20120703 Add BY yichen  ===========begin========================*/
		//增加例外狀況 152025、152026、152027、152028 夜二技通識
		//目前是同時開給夜二技1-3年皆可選修，故不可列入畢業班
		//潘阿姨說101學年下學期可能會重新調整故要再重新確認
		else if (departClass.equalsIgnoreCase("152025"))
			return false;
		else if (departClass.equalsIgnoreCase("152026"))
			return false;
		else if (departClass.equalsIgnoreCase("152027"))
			return false;
		else if (departClass.equalsIgnoreCase("152028"))
			return false;
		/* 20120703 Add BY yichen  ============end=========================*/	

		int gradYear = 2;
		StudAffairDAO dao = (StudAffairDAO) Global.context
				.getBean("studAffairDAO");

		/*
		 * 決定該班級之畢業年級 特例:日二專建築為三年
		 */
		// String sterm = Toolket.getSysParameter("School_term");
		// if(sterm.equals("2")) {
		String gradept = departClass.substring(1, 3);
		String hql = "select c5 from Code5 c5 Where category='GradYear' And idno like '"
				+ gradept + "'";
		List<Code5> gyearlist = dao.submitQuery(hql);
		if (gyearlist.size() > 0) {
			if (gyearlist.size() > 1) {
				for (Iterator<Code5> gyearIter = gyearlist.iterator(); gyearIter
						.hasNext();) {
					Code5 code5 = gyearIter.next();
					if (code5.getIdno().trim().equals(
							departClass.substring(1, 4))) {
						gradYear = Integer.parseInt(code5.getName());
						break;
					} else if (code5.getIdno().trim().equals(
							departClass.substring(1, 3))) {
						gradYear = Integer.parseInt(code5.getName());
						break;
					}
				}
			} else {
				gradYear = Integer.parseInt(gyearlist.get(0).getName());
			}
		} else {
			return false;
		}

		if (Integer.parseInt(departClass.substring(4, 5)) == gradYear) {
			isGraduateClass = true;
			// log.debug("IsGraduate:" + isGraduateClass);
		}
		return isGraduateClass;

	}

	/**
	 * 判斷是否為應屆畢業班?
	 * 
	 * @commend 延修班不納入應屆畢業
	 * @param departClass Depart Class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isGraduateClass(String departClass) {
		boolean isGraduateClass = false;
		int gradYear = 2;
		StudAffairDAO dao = (StudAffairDAO) Global.context
				.getBean("studAffairDAO");

		/*
		 * 決定該班級之畢業年級 特例:日二專建築為三年, 夜二專半導體產攜為三年
		 */

		String gradept = "";
		try {
			gradept = departClass.substring(1, 3);
		} catch (Exception e) {
			// 發生Exception應該就不是畢業班了
			return false;
		}
		String hql = "select c5 from Code5 c5 Where category='GradYear' And idno like '%"
				+ gradept + "%'";
		List<Code5> gyearlist = dao.submitQuery(hql);
		if (gyearlist.size() > 0) {
			if (gyearlist.size() > 1) {
				for (Iterator<Code5> gyearIter = gyearlist.iterator(); gyearIter
						.hasNext();) {
					Code5 code5 = gyearIter.next();
					if (code5.getIdno().trim().equals(
							departClass.substring(1, 4))) {
						gradYear = Integer.parseInt(code5.getName());
						break;
					} else if (code5.getIdno().trim().equals(
							departClass.substring(1, 3))) {
						gradYear = Integer.parseInt(code5.getName());
						break;
					}
				}
			} else {
				gradYear = Integer.parseInt(gyearlist.get(0).getName());
			}
		} else {
			return false;
		}

		if (departClass.length() == 6
				&& Integer.parseInt(departClass.substring(4, 5)) == gradYear)
			isGraduateClass = true;
		else
			isGraduateClass = false;

		return isGraduateClass;
	}

	/**
	 * 判斷是否為新生班級?
	 * 
	 * @param departClass Depart Class
	 * @return
	 */
	public static boolean isNewStudentClass(String departClass) {
		return departClass.length() >= 5
				&& "1".equals(StringUtils.substring(departClass, 4, 5));
	}

	@SuppressWarnings("unchecked")
	public static Code5 findBuildingBy(String buildingId) {
		StudAffairDAO dao = (StudAffairDAO) Global.context
				.getBean("studAffairDAO");
		String hql = "SELECT c5 FROM Code5 c5 WHERE category = 'building' AND idno = '"
				+ buildingId + "'";
		List<Code5> code5s = dao.submitQuery(hql);
		if (!code5s.isEmpty())
			return (Code5) code5s.get(0);
		else
			return null;
	}

	/**
	 * 以FTP取得學生之大頭照
	 * 
	 * @param studentNo 學號
	 * @return java.io.InputStream 大頭照
	 * @throws IOException
	 */
	public static InputStream getStudentgImageByFTP(String studentNo)
			throws IOException {

		InputStream is = null;
		FTPClient ftp = null;
		StopWatch watch = new StopWatch();
		watch.start();
		try { // 
			ftp = new FTPClient();
			ftp.connect(IConstants.FTP_SERVER_DOMAIN_NAME_1);
			// ftp.setDataTimeout(2 * 60 * 1000);
			// ftp.setSoTimeout(2 * 60 * 1000);
			int timeOut = ftp.getDefaultTimeout();
			ftp.setDataTimeout(timeOut + (5 * 1000));
			int replyCode = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftp.disconnect();
				throw new IOException("Reply Code:" + replyCode);
			}
			if (!ftp.login("informix", "trevor5k")) {
				ftp.disconnect();
				throw new IOException("FTP登入錯誤,請稍候再行登入...");
			}
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			ftp.changeWorkingDirectory(IConstants.STUDENT_IMAGE_PATH);
			String[] imageNames = ftp.listNames();
			boolean bFlag = ArrayUtils.contains(imageNames, studentNo + ".jpg");
			if (imageNames != null && bFlag) {
				is = ftp.retrieveFileStream(studentNo + ".jpg");
				try {
					Thread.sleep(2 * 1000);
				} catch (Exception e) {
				}
			} else {
				throw new IOException("FTP內查無學生影像檔...");
			}
		} finally {
			if (ftp != null && ftp.isConnected())
				ftp.disconnect();
		}
		watch.stop();
		log.info("FTP Download Time : " + watch.getTime());
		return is;
	}

	/**
	 * 以FTP上傳檔案
	 * 
	 * @param destFolder 檔案置於FTP的目的地
	 * @param file 上傳的檔案
	 * @throws IOException
	 */
	public static void uploadFileByFtp(String destFolder, File file)
			throws IOException {
		FTPClient ftp = null;
		StopWatch watch = new StopWatch();
		watch.start();
		try {
			ftp = new FTPClient();
			ftp.connect(IConstants.FTP_SERVER_DOMAIN_NAME_1);
			// ftp.setDataTimeout(2 * 60 * 1000);
			// ftp.setSoTimeout(2 * 60 * 1000);
			int timeOut = ftp.getDefaultTimeout();
			ftp.setDataTimeout(timeOut + (5 * 1000));
			int replyCode = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftp.disconnect();
				throw new IOException("Reply Code:" + replyCode);
			}
			if (!ftp.login("informix", "trevor5k")) {
				ftp.disconnect();
				throw new IOException("FTP登入錯誤,請稍候再行登入...");
			}
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();

			if (!ftp.changeWorkingDirectory(destFolder)) {
				ftp.disconnect();
				throw new IOException("變更目錄錯誤,請檢查目錄位置是否正確...");
			}

			FileInputStream fis = new FileInputStream(file);
			boolean isOk = ftp.storeFile(file.getName(), fis);
			fis.close();
			log.info("FTP " + file.getName() + " is OK ? " + isOk);

		} finally {
			if (ftp != null && ftp.isConnected())
				ftp.disconnect();
		}
		watch.stop();
		log.info("FTP Upload Time : " + watch.getTime());
	}

	/**
	 * 將學生資訊訊息發至JMS Server Queue(CISQueue)內
	 * 
	 * @param student Student Object
	 * @param mode SQL Command Mode
	 */
	public static void sendStudentInfoByQueue(Student student, String mode) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Context context = new InitialContext(properties);
			ConnectionFactory factory = (ConnectionFactory) context
					.lookup(IConstants.JMS_CONNECTION_FACTORY_NAME);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false,
					Session.DUPS_OK_ACKNOWLEDGE); // 訊息可重複送出
			Destination destination = (Destination) context
					.lookup(IConstants.JMS_QUEUE_NAME);
			connection.start();
			MessageProducer sender = session.createProducer(destination);

			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("Type", "STD");
			mapMessage.setString("Mode", mode);
			mapMessage.setString("DepartClass", student.getDepartClass());
			mapMessage.setString("StudentNo", student.getStudentNo());
			mapMessage.setString("StudentName", student.getStudentName());
			mapMessage.setString("Sex", student.getSex());
			mapMessage.setString("Birthday", student.getBirthday() == null ? ""
					: df.format(student.getBirthday()));
			mapMessage.setString("Idno", student.getIdno());
			mapMessage.setString("Entrance", student.getEntrance() == null ? ""
					: student.getEntrance().toString());
			mapMessage.setString("Gradyear", student.getGradyear() == null ? ""
					: student.getGradyear().toString());
			mapMessage.setString("Ident", student.getIdent());
			mapMessage.setString("Divi", student.getDivi());
			mapMessage.setString("BirthProvince", student.getBirthProvince());
			mapMessage.setString("BirthCounty", student.getBirthCounty());
			mapMessage.setString("CurrPost", student.getCurrPost());
			mapMessage.setString("CurrAddr", student.getCurrAddr());
			mapMessage.setString("PermPost", student.getPermPost());
			mapMessage.setString("PermAddr", student.getPermAddr());
			mapMessage.setString("GraduStatus", student.getGraduStatus());
			mapMessage.setString("ParentName", student.getParentName());
			mapMessage.setString("Telephone", student.getTelephone());
			mapMessage.setString("OccurCause", student.getOccurCause());
			mapMessage.setString("OccurDocno", student.getOccurDocno());
			mapMessage.setString("OccurGraduateNo", student
					.getOccurGraduateNo());
			mapMessage.setString("OccurStatus", student.getOccurStatus());
			mapMessage.setString("OccurTerm", student.getOccurTerm());
			mapMessage.setString("OccurDate",
					student.getOccurDate() == null ? "" : df.format(student
							.getOccurDate()));
			mapMessage.setString("OccurYear",
					student.getOccurYear() == null ? "" : student
							.getOccurYear().toString());
			mapMessage.setString("SchlCode", student.getSchlCode());
			
			// Message Selector,類似Filter用
			// mapMessage.setStringProperty("MessageFormat", "Version 1.1");
			
			sender.send(mapMessage);
			connection.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 將教職員資訊訊息發至JMS Server Queue(CISQueue)內
	 * 
	 * @param empl Empl Object
	 * @param mode SQL Command Mode
	 */
	public static void sendEmployeeInfoByQueue(Empl empl, String mode) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Context context = new InitialContext(properties);
			ConnectionFactory factory = (ConnectionFactory) context
					.lookup(IConstants.JMS_CONNECTION_FACTORY_NAME);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false,
					Session.DUPS_OK_ACKNOWLEDGE); // 訊息可重複送出
			Destination destination = (Destination) context
					.lookup(IConstants.JMS_QUEUE_NAME);
			connection.start();
			MessageProducer sender = session.createProducer(destination);

			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("Type", "EMPL");
			mapMessage.setString("Mode", mode);
			mapMessage.setString("Idno", empl.getIdno());
			mapMessage.setString("Insno", empl.getInsno()); // 保險證號
			mapMessage.setString("Cname", empl.getCname());
			mapMessage.setString("Sex", empl.getSex());
			mapMessage.setString("Birthday", empl.getBdate() == null ? "" : df
					.format(empl.getBdate()));
			mapMessage.setString("Ename", empl.getEname());
			mapMessage.setString("Unit", empl.getUnit());
			mapMessage.setString("Fulltime", empl.getCategory());
			mapMessage.setString("Pcode", empl.getPcode());
			mapMessage.setString("Sname", empl.getSname());
			mapMessage.setString("Telephone", empl.getTelephone());
			mapMessage.setString("PermZip", empl.getPzip());
			mapMessage.setString("PermAddr", empl.getPaddr());
			mapMessage.setString("CurrZip", empl.getCzip());
			mapMessage.setString("CurrAddr", empl.getCaddr());
			mapMessage.setString("StartDate", empl.getStartDate() == null ? ""
					: df.format(empl.getStartDate()));
			mapMessage.setString("EndDate", empl.getEndDate() == null ? "" : df
					.format(empl.getEndDate()));
			sender.send(mapMessage);
			connection.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 將教職員資訊訊息發至JMS Server Queue(CISQueue)內做離職資料更新
	 * 
	 * @param dempl DEmpl Object
	 * @param mode SQL Command Mode
	 */
	public static void sendDEmplInfoByQueue(DEmpl dempl, String mode) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Context context = new InitialContext(properties);
			ConnectionFactory factory = (ConnectionFactory) context
					.lookup(IConstants.JMS_CONNECTION_FACTORY_NAME);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false,
					Session.DUPS_OK_ACKNOWLEDGE); // 訊息可重複送出
			Destination destination = (Destination) context
					.lookup(IConstants.JMS_QUEUE_NAME);
			connection.start();
			MessageProducer sender = session.createProducer(destination);
			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("Type", "DEMPL");
			mapMessage.setString("Mode", mode);
			mapMessage.setString("Idno", dempl.getIdno());
			mapMessage.setString("Insno", dempl.getInsno());
			mapMessage.setString("Cname", dempl.getCname());
			mapMessage.setString("Sex", dempl.getSex());
			mapMessage.setString("Birthday", dempl.getBdate() == null ? "" : df
					.format(dempl.getBdate()));
			mapMessage.setString("Ename", dempl.getEname());
			mapMessage.setString("Unit", dempl.getUnit());
			mapMessage.setString("Fulltime", dempl.getCategory());
			mapMessage.setString("Pcode", dempl.getPcode());
			mapMessage.setString("Sname", dempl.getSname());
			mapMessage.setString("Telephone", dempl.getTelephone());
			mapMessage.setString("PermZip", dempl.getPzip());
			mapMessage.setString("PermAddr", dempl.getPaddr());
			mapMessage.setString("CurrZip", dempl.getCzip());
			mapMessage.setString("CurrAddr", dempl.getCaddr());
			mapMessage.setString("StartDate", dempl.getStartDate() == null ? ""
					: df.format(dempl.getStartDate()));
			mapMessage.setString("EndDate", dempl.getEndDate() == null ? ""
					: df.format(dempl.getEndDate()));
			mapMessage.setString("Reason", dempl.getStatusCause());
			sender.send(mapMessage);
			connection.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 將學生/教職員密碼資訊訊息發至JMS Server Queue(CISQueue)內
	 * 
	 * @param username Student/Employee Username
	 * @param password Student/Employee Password
	 * @param priority Student/Employee Priority
	 * @param seqno Student/Employee SeqNo
	 * @param mode SQL Command Mode
	 */
	public static void sendWWPassInfoByQueue(String username, String password,
			String priority, String seqno, String mode) {

		try {
			Context context = new InitialContext(properties);
			ConnectionFactory factory = (ConnectionFactory) context
					.lookup(IConstants.JMS_CONNECTION_FACTORY_NAME);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false,
					Session.DUPS_OK_ACKNOWLEDGE); // 訊息可重複送出
			Destination destination = (Destination) context
					.lookup(IConstants.JMS_QUEUE_NAME);
			connection.start();
			MessageProducer sender = session.createProducer(destination);
			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("Type", "PASS");
			mapMessage.setString("Mode", mode);
			mapMessage.setString("Username", username);
			mapMessage.setString("Password", password);
			mapMessage.setString("Priority", priority);
			mapMessage.setString("Seqno", seqno);
			sender.send(mapMessage);
			connection.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 將學生資訊訊息發至JMS Server Queue(CISQueue)內做畢業生資料更新
	 * 
	 * @param graduate Graduate Object
	 * @param mode SQL Command Mode
	 */
	public static void sendGraduateInfoByQueue(Graduate graduate, String mode) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Context context = new InitialContext(properties);
			ConnectionFactory factory = (ConnectionFactory) context
					.lookup(IConstants.JMS_CONNECTION_FACTORY_NAME);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false,
					Session.DUPS_OK_ACKNOWLEDGE); // 訊息可重複送出
			Destination destination = (Destination) context
					.lookup(IConstants.JMS_QUEUE_NAME);
			connection.start();
			MessageProducer sender = session.createProducer(destination);

			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("Type", "TRAN");
			mapMessage.setString("Mode", mode);
			mapMessage.setString("DepartClass", graduate.getDepartClass());
			mapMessage.setString("StudentNo", graduate.getStudentNo());
			mapMessage.setString("StudentName", graduate.getStudentName());
			mapMessage.setString("Sex", graduate.getSex());
			mapMessage.setString("Birthday",
					graduate.getBirthday() == null ? "" : df.format(graduate
							.getBirthday()));
			mapMessage.setString("Idno", graduate.getIdno());
			mapMessage.setString("Entrance",
					graduate.getEntrance() == null ? "" : graduate
							.getEntrance().toString());
			mapMessage.setString("Gradyear",
					graduate.getGradyear() == null ? "" : graduate
							.getGradyear().toString());
			mapMessage.setString("Ident", graduate.getIdent());
			mapMessage.setString("Divi", graduate.getDivi());
			mapMessage.setString("BirthProvince", graduate.getBirthProvince());
			mapMessage.setString("BirthCounty", graduate.getBirthCounty());
			mapMessage.setString("CurrPost", graduate.getCurrPost());
			mapMessage.setString("CurrAddr", graduate.getCurrAddr());
			mapMessage.setString("PermPost", graduate.getPermPost());
			mapMessage.setString("PermAddr", graduate.getPermAddr());
			mapMessage.setString("GraduStatus", graduate.getGraduStatus());
			mapMessage.setString("ParentName", graduate.getParentName());
			mapMessage.setString("Telephone", graduate.getTelephone());
			mapMessage.setString("OccurCause", graduate.getOccurCause());
			mapMessage.setString("OccurDocno", graduate.getOccurDocno());
			mapMessage.setString("OccurGraduateNo", graduate
					.getOccurGraduateNo());
			mapMessage.setString("OccurStatus",
					graduate.getOccurStatus() == null ? "" : graduate
							.getOccurStatus());
			mapMessage.setString("OccurTerm", graduate.getOccurTerm());
			mapMessage.setString("OccurDate",
					graduate.getOccurDate() == null ? "" : df.format(graduate
							.getOccurDate()));
			mapMessage.setString("OccurYear",
					graduate.getOccurYear() == null ? "" : graduate
							.getOccurYear().toString());
			mapMessage.setString("SchlCode", graduate.getSchlCode());
			sender.send(mapMessage);
			connection.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 將學生歷年成績訊息發至JMS Server Queue(CISQueue)內做資料更新
	 * 
	 * @param scoreHist ScoreHist Object
	 * @param mode SQL Command Mode
	 */
	public static void sendScoreHistotyInfoByQueue(ScoreHist scoreHist,
			String mode) {

		try {
			Context context = new InitialContext(properties);
			ConnectionFactory factory = (ConnectionFactory) context
					.lookup(IConstants.JMS_CONNECTION_FACTORY_NAME);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false,
					Session.DUPS_OK_ACKNOWLEDGE); // 訊息可重複送出
			Destination destination = (Destination) context
					.lookup(IConstants.JMS_QUEUE_SCORE_HISTORY_QUEUE);
			connection.start();
			MessageProducer sender = session.createProducer(destination);

			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("Type", "SCRHIST");
			mapMessage.setString("Mode", mode);
			mapMessage.setString("StudentNo", scoreHist.getStudentNo());
			mapMessage.setShort("SchoolYear", scoreHist.getSchoolYear());
			mapMessage.setString("SchoolTerm", scoreHist.getSchoolTerm());
			mapMessage.setString("StudentDepartClass", scoreHist
					.getStdepartClass());
			mapMessage.setString("EvgrType", scoreHist.getEvgrType());
			mapMessage.setString("Cscode", scoreHist.getCscode());
			mapMessage.setString("Opt", scoreHist.getOpt());
			mapMessage.setFloat("Credit", scoreHist.getCredit());
			mapMessage.setFloat("Score", scoreHist.getScore() == null ? 0.0F
					: scoreHist.getScore());
			sender.send(mapMessage);
			connection.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 將修改學生組別資訊訊息發至JMS Server Queue(CISQueue)內做資料更新
	 * 
	 * @param departClass 班級代碼
	 * @param diviNo 組別代碼
	 * @param mode SQL Command Mode
	 */
	public static void sendDiviInfoByQueue(String departClass, String diviNo,
			String mode) {

		try {
			Context context = new InitialContext(properties);
			ConnectionFactory factory = (ConnectionFactory) context
					.lookup(IConstants.JMS_CONNECTION_FACTORY_NAME);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false,
					Session.DUPS_OK_ACKNOWLEDGE); // 訊息可重複送出
			Destination destination = (Destination) context
					.lookup(IConstants.JMS_QUEUE_NAME);
			connection.start();
			MessageProducer sender = session.createProducer(destination);

			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("Type", "DIVI");
			mapMessage.setString("Mode", mode);
			mapMessage.setString("DepartClass", departClass);
			mapMessage.setString("DiviNo", diviNo);
			sender.send(mapMessage);
			connection.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 將修改學生入學資格核准文號資訊訊息發至JMS Server Queue(CISQueue)內做資料更新
	 * 
	 * @param entrno Entrno Object
	 * @param mode SQL Command Mode
	 */
	public static void sendEntrnoInfoByQueue(Entrno entrno, String mode) {

		try {
			Context context = new InitialContext(properties);
			ConnectionFactory factory = (ConnectionFactory) context
					.lookup(IConstants.JMS_CONNECTION_FACTORY_NAME);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false,
					Session.DUPS_OK_ACKNOWLEDGE); // 訊息可重複送出
			Destination destination = (Destination) context
					.lookup(IConstants.JMS_QUEUE_NAME);
			connection.start();
			MessageProducer sender = session.createProducer(destination);

			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("Type", "ENTRNO");
			mapMessage.setString("Mode", mode);
			mapMessage.setString("StartNo", entrno.getFirstStno());
			mapMessage.setString("EndNo", entrno.getSecondStno());
			mapMessage.setString("DocNo", entrno.getPermissionNo());
			sender.send(mapMessage);
			connection.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 將碩士學生成績訊息發至JMS Server Queue(CISQueue)內做資料更新
	 * 
	 * @param md MasterData Object
	 * @param mode SQL Command Mode
	 */
	public static void sendMasterScoreInfoByQueue(MasterData md, String mode) {

		try {
			Context context = new InitialContext(properties);
			ConnectionFactory factory = (ConnectionFactory) context
					.lookup(IConstants.JMS_CONNECTION_FACTORY_NAME);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false,
					Session.DUPS_OK_ACKNOWLEDGE); // 訊息可重複送出
			Destination destination = (Destination) context
					.lookup(IConstants.JMS_QUEUE_SCORE_HISTORY_QUEUE);
			connection.start();
			MessageProducer sender = session.createProducer(destination);

			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("Type", "MASTER");
			mapMessage.setString("Mode", mode);
			mapMessage.setString("StudentNo", md.getStudentNo());
			mapMessage.setShort("SchoolYear", md.getSchoolYear());
			mapMessage.setShort("SchoolTerm", md.getSchoolTerm());
			mapMessage.setString("ThesesChiname", md.getThesesChiname());
			mapMessage.setString("ThesesEngname", md.getThesesEngname());
			mapMessage.setFloat("GraduateScore", md.getGraduateScore());
			mapMessage.setFloat("Evgr1Score", md.getEvgr1Score());
			mapMessage.setFloat("ThesesScore", md.getThesesScore());
			mapMessage.setString("Remark", md.getRemark());
			mapMessage.setString("OnlineFileDate",
					md.getOnlineFileDate() == null ? null
							: new SimpleDateFormat("yyyy-MM-dd").format(md
									.getOnlineFileDate()));
			sender.send(mapMessage);
			connection.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static String replace(String c) {
		String ret = "";
		switch (c.charAt(0)) {
			case 'A':
				ret = "01";
				break;
			case 'B':
				ret = "02";
				break;
			case 'C':
				ret = "03";
				break;
			case 'D':
				ret = "04";
				break;
			case 'E':
				ret = "05";
				break;
			case 'F':
				ret = "06";
				break;
			case 'G':
				ret = "07";
				break;
			case 'H':
				ret = "08";
				break;
			case 'I':
				ret = "09";
				break;
			case 'J':
				ret = "10";
				break;
			case 'K':
				ret = "11";
				break;
			case 'L':
				ret = "12";
				break;
			case 'M':
				ret = "13";
				break;
			case 'N':
				ret = "14";
				break;
			case 'O':
				ret = "15";
				break;
			case 'P':
				ret = "16";
				break;
			case 'Q':
				ret = "17";
				break;
			case 'R':
				ret = "18";
				break;
			case 'S':
				ret = "19";
				break;
			case 'T':
				ret = "20";
				break;
			case 'U':
				ret = "21";
				break;
			case 'V':
				ret = "22";
				break;
			case 'W':
				ret = "23";
				break;
			case 'X':
				ret = "24";
				break;
			case 'Y':
				ret = "25";
				break;
			case 'Z':
				ret = "26";
				break;
			default:
				ret = "00";
				break;
		}
		return ret;
	}

	/**
	 * 將訊息回傳給MIDlet
	 * 
	 * @param request
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	public static void sendPlainMessageForMIDP(HttpServletRequest request,
			HttpServletResponse response, String message) throws IOException {
		request.setCharacterEncoding(IConstants.MIDP_CHARACTER_ENCODING);
		response.setContentType(IConstants.MIDP_PLAIN_CONTENT_TYPE);
		DataOutputStream dos = new DataOutputStream(response.getOutputStream());
		dos.writeUTF(message);
		dos.flush();
		dos.close();
	}

	/**
	 * 將訊息回傳給MIDlet
	 * 
	 * @param request
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	public static void sendStreamMessageForMIDP(HttpServletRequest request,
			HttpServletResponse response, String message) throws IOException {
		response.setContentType(IConstants.MIDP_STREAM_CONTENT_TYPE);
		DataOutputStream dos = new DataOutputStream(response.getOutputStream());
		dos.writeUTF(message);
		dos.flush();
		dos.close();
	}

	/**
	 * 將訊息回傳給MIDlet
	 * 
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	public static void sendMessageForMIDP(HttpServletResponse response,
			String message) throws IOException {
		response.setContentType(IConstants.MIDP_PLAIN_CONTENT_TYPE);
		PrintWriter writer = response.getWriter();
		writer.print(message);
		writer.close();
	}

	/**
	 * 取得及格最低分
	 * 
	 * @param departClass DepartClass
	 * @return java.lang.float Pass Score
	 */
	public static float getPassScoreByDepartClass(String departClass) {
		return StringUtils.indexOf(departClass, "1G") != StringUtils.INDEX_NOT_FOUND
				|| StringUtils.indexOf(departClass, "8G") != StringUtils.INDEX_NOT_FOUND
				|| StringUtils.indexOf(departClass, "CG") != StringUtils.INDEX_NOT_FOUND
				|| StringUtils.indexOf(departClass, "DG") != StringUtils.INDEX_NOT_FOUND
				|| StringUtils.indexOf(departClass, "HG") != StringUtils.INDEX_NOT_FOUND
				|| StringUtils.indexOf(departClass, "FG") != StringUtils.INDEX_NOT_FOUND ? 70.0f
				: 60.0f;
	}

	/**
	 * 判斷是否為通識班級?
	 * 
	 * @param departClass Depart Class
	 * @return boolean true if
	 */
	public static boolean isLiteracyClass(String departClass) {
		if (departClass.length() < 5)
			return false;
		return "0".equals(departClass.substring(3, 4));
	}

	/**
	 * 判斷是否為專科班級?
	 * 
	 * @param departClass Depart Class
	 * @return boolean true if
	 */
	public static boolean isAssocaiteClass(String departClass) {
		if (StringUtils.isBlank(departClass) || departClass.length() < 3)
			return false;
		return Arrays.binarySearch(IConstants.ASSOCIATE_CLASS, StringUtils
				.substring(departClass, 1, 3)) >= 0;
	}

	/**
	 * 判斷是否為碩士班班級?
	 * 
	 * @param departClass Depart Class
	 * @return boolean true if
	 */
	public static boolean isMasterClass(String departClass) {
		if (StringUtils.isBlank(departClass) || departClass.length() < 3)
			return false;
		return "G".equalsIgnoreCase(departClass.substring(2, 3));
	}

	/**
	 * 判斷是否為碩士班學生?
	 * 
	 * @param studentNo
	 * 
	 * @return true or false
	 */
	public static boolean isMasterStudent(String studentNo) {
		if (StringUtils.isBlank(studentNo) || studentNo.length() <= 7)
			return false;
		return "G".equalsIgnoreCase(studentNo.substring(3, 4));
	}

	/**
	 * 取得學位中文名稱
	 * 
	 * @param d 系所別代碼
	 * @param isMaster 是否為碩士生?
	 * @param isAssociate 是否為專科生?
	 * @return
	 */
	public static String getFormalAcademicDegreeName(String d,
			boolean isMaster, boolean isAssociate) {
		String ret = "";
		if (Arrays.binarySearch(IConstants.SCIENCE, d) >= 0) {
			ret = "工學";
		} else if (Arrays.binarySearch(IConstants.BUSINESS, d) >= 0) {
			ret = "商學";
		} else if (Arrays.binarySearch(IConstants.MANAGER, d) >= 0) {
			ret = "管理學";
		} else if (Arrays.binarySearch(IConstants.AQRICULTURE, d) >= 0) {
			ret = "農學";
		} else
			ret = "";
		ret = isMaster ? ret + "碩士" : (isAssociate ? ret + "副學士" : ret + "學士");
		return ret;
	}

	/**
	 * 判斷傳入的字串是否為數字(包含帶負號之整數及浮點小數,不含科學記號數字)
	 * 
	 * @param String doublStr
	 * @return boolean
	 */
	public static boolean isNumeric(String doublStr) {
		String[] splitStr = StringUtils.split(doublStr, ".");
		// log.debug("isNumeric->splitStr.length" + splitStr.length);
		if (splitStr.length > 2)
			return false;
		for (int i = 0; i < splitStr.length; i++) {
			if (i == 0) {
				if (splitStr[0].trim().indexOf("-") == 0) {
					splitStr[0] = splitStr[0].trim().substring(1);
				}
			}
			if (!StringUtils.isNumeric(splitStr[i]))
				return false;
		}
		return true;
	}

	/**
	 * 判斷傳入的字串是否為有效的中華民國日期
	 * 
	 * @param String date of format[?yy/mm/dd] or [?yy-mm-dd]
	 * @return boolean
	 */
	public static boolean isValidDate(String Tdate) {
		if (Tdate.trim().equals(""))
			return false;
		String[] tDate = null;
		if (Tdate.indexOf('/') >= 0) {
			tDate = Tdate.split("/");
		} else if (Tdate.indexOf('-') >= 0) {
			tDate = Tdate.split("-");
		}

		if (tDate.length != 3) {
			return false;
		} else {
			if (!Toolket.isNumeric(tDate[0]) || !Toolket.isNumeric(tDate[1])
					|| !Toolket.isNumeric(tDate[2])) {
				return false;
			}
			tDate[0] = "" + (Integer.parseInt(tDate[0]) + 1911);
		}

		if (tDate[0].equals("") || tDate[1].equals("") || tDate[2].equals("")) {
			return false;
		} else if (!(tDate[0].equals("") || tDate[1].equals("") || tDate[2]
				.equals(""))) {

			int itfYear = Integer.parseInt(tDate[0].trim());
			int itfMonth = Integer.parseInt(tDate[1].trim()) - 1;
			int itfDay = Integer.parseInt(tDate[2].trim());

			Calendar tfdate = Calendar.getInstance();
			tfdate.clear();
			tfdate.set(itfYear, itfMonth, itfDay, 0, 0, 0);

			if (tfdate.get(Calendar.YEAR) != itfYear
					|| tfdate.get(Calendar.MONTH) != itfMonth
					|| tfdate.get(Calendar.DAY_OF_MONTH) != itfDay) {

				return false;
			} else
				return true;
		}
		return false;
	}

	/**
	 * 判斷傳入的字串是否為有效的西元日期
	 * 
	 * @param String date of format[yyyy/mm/dd] or[yyyy-mm-dd]
	 * @return boolean
	 */
	public static boolean isValidFullDate(String Tdate) {
		if (Tdate.trim().equals(""))
			return false;
		String[] tDate = null;
		if (Tdate.indexOf('/') >= 0) {
			tDate = Tdate.split("/");
		} else if (Tdate.indexOf('-') >= 0) {
			tDate = Tdate.split("-");
		}
		if (tDate.length != 3) {
			return false;
		} else {
			if (!Toolket.isNumeric(tDate[0]) || !Toolket.isNumeric(tDate[1])
					|| !Toolket.isNumeric(tDate[2])) {
				return false;
			}
		}

		if (tDate[0].equals("") || tDate[1].equals("") || tDate[2].equals("")) {
			return false;
		} else if (!(tDate[0].equals("") || tDate[1].equals("") || tDate[2]
				.equals(""))) {

			int itfYear = Integer.parseInt(tDate[0].trim());
			int itfMonth = Integer.parseInt(tDate[1].trim()) - 1;
			int itfDay = Integer.parseInt(tDate[2].trim());

			Calendar tfdate = Calendar.getInstance();
			tfdate.clear();
			tfdate.set(itfYear, itfMonth, itfDay, 0, 0, 0);

			if (tfdate.get(Calendar.YEAR) != itfYear
					|| tfdate.get(Calendar.MONTH) != itfMonth
					|| tfdate.get(Calendar.DAY_OF_MONTH) != itfDay) {

				return false;
			} else
				return true;
		}
		return false;
	}

	public static Student getStudentByNo(String studentNo) {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		Student student = dao.findStudentByStudentNo(studentNo.toUpperCase());
		return student;
	}

	public static Graduate getGraduateByNo(String studentNo) {
		MemberDAO dao = (MemberDAO) Global.context.getBean("memberDAO");
		Graduate graduate = dao
				.findGraduateByStudentNo(studentNo.toUpperCase());
		return graduate;
	}

	public static String getStudentName(String studentNo) {
		String studentName = "";
		Student stud = getStudentByNo(studentNo.toUpperCase());
		if (stud == null) {
			Graduate grdu = getGraduateByNo(studentNo.toUpperCase());
			if (grdu != null) {
				studentName = grdu.getStudentName();
			}
		} else {
			studentName = stud.getStudentName();
		}
		return studentName;
	}

	/**
	 * 取得某"班級"的畢業年限
	 * 
	 * @param departClass 班級代碼
	 * @throws NumberFormatException
	 * @return java.lang.Integer
	 */
	public static Integer getMaxyear(String departClass) {
		if (departClass.length() == 6) {

			String tmp = departClass.substring(2, 3);
			if (tmp.equals("G")) {
				return 2;
			} else {
				try {
					return Integer.parseInt(tmp);
				} catch (NumberFormatException e) {
					return 999;
				}
			}
		} else {
			// TODO 班級代碼非6碼的處理方法
			return 999;
		}
	}

	public static String getDeptCode4Ntnu(String departClass) {
		if (StringUtils.isBlank(departClass))return "";
		
		String deptCode;
		if(departClass.length()==6){
			deptCode = StringUtils.substring(departClass, 3, 4);
			switch (deptCode.charAt(0)) {
			case '0':
				return "999901";
			case '1':
				return "520201";
			case '2':
				return "520101";
			case '3':
				return "520103";
			case '4':
				return "520601";
			case '5':
				return "580101";			
			case '6':
				return "520301";
			case '7':
				return "340301";
			case '8':
				return "";
			case '9':
				return "340501";
			case 'A':
				return "520208";
			case 'B':
				return "520112";
			case 'C':
				return "840202";
			case 'D':
				return "349902";
			case 'E':
				return "520114";
			case 'F':
				return "620601";
			case 'G':
				return "";
			case 'H':
				return "420302";
			case 'I':
				return "810103";
			case 'J':
				return "810202";
			case 'K':
				return "340307";
			case 'U':
				return "810224";
			case 'V':
				return "580101";
			case 'W':
				return "239904";
			case 'X':
				return "340120";
			default:
				return "";
			}
		}else{
			deptCode = StringUtils.substring(departClass, 3, 5);
			if(deptCode.equals("KA")){
				return"340824";
			}			
			return"";
		}
		
		
		
		
	}

	public static String getCampCode4Ntnu(String departClass) {
		if (StringUtils.isBlank(departClass))
			return "";

		String campCode = StringUtils.substring(departClass, 1, 3);
		if ("1G".equals(campCode))
			return "7";
		else if ("8G".equals(campCode))
			return "20";
		else if ("CG".equals(campCode) || "DG".equals(campCode)
				|| "FG".equals(campCode) || "HG".equals(campCode))
			return "21";
		else if ("12".equals(campCode))
			return "3";
		else if ("15".equals(campCode))
			return "4";
		else if ("42".equals(campCode))
			return "5";
		else if ("64".equals(campCode) || "A4".equals(campCode))
			return "6";
		else if ("22".equals(campCode))
			return "12";
		else if ("52".equals(campCode))
			return "15";
		else if ("54".equals(campCode))
			return "17";
		else if ("82".equals(campCode))
			return "16";
		else if ("92".equals(campCode))
			return "13";
		else
			return "";
	}
	
	public static String get4IdentNtnu(String ident, boolean isMaster) {
		if (StringUtils.isBlank(ident))
			return "";

		if (!isMaster) {
			if ("1".equals(ident))
				return "1"; // 考試分發入學
			else if ("2".equals(ident))
				return "3"; // 申請入學
			else if ("6".equals(ident))
				return "7"; // 保送入學
			else if ("B".equalsIgnoreCase(ident))
				return "6"; // 技優甄保
			else if ("H".equalsIgnoreCase(ident))
				return "2"; // 推薦甄選
			else if ("I".equals(ident))
				return "5"; // 各校單招
			else
				return "9 (" + ident + ")"; // 其他
		} else {
			if ("1".equals(ident))
				return "11"; // 碩士班入學考試
			else if ("H".equalsIgnoreCase(ident))
				return "12"; // 碩士班推薦甄試
			else
				return "14 (" + ident + ")"; // 其他
		}
	}
	
	// 技職課程填報欄位	
	public static String getCourseStyleBy(String elearning) {
		if (StringUtils.isBlank(elearning))
			return "";
		
		switch (Integer.parseInt(elearning)) {
			case 0:
			case 3:
				return "1"; // 一般

			case 1:
				return "4"; // 遠距

			case 2:
				return "8"; // 輔助

			default:
				return "";
		}
	}
	
	// 技職課程填報欄位
	public static String getTeacherSourceBy(String unit) {
		if (StringUtils.isBlank(unit))
			return "";

		if ("68".equals(unit))
			return "2"; // 通識教育中心

		return "1";
	}

	// 技職課程填報欄位
	public static String getSchoolNoBy(Clazz clazz) {
		if (clazz == null || StringUtils.isBlank(clazz.getSchoolType()))
			return "";

		if (Toolket.isMasterClass(clazz.getClassNo()))
			return "1006";
		else if ("32".equals(clazz.getSchoolNo()))
			return "1020";
		else if ("72".equals(clazz.getSchoolNo()))
			return "1021";
		else if (StringUtils.contains(clazz.getClassName(), "二技"))
			return "1001";
		else if (StringUtils.contains(clazz.getClassName(), "四技"))
			return "1005";
		else
			return "";

	}

	/**
	 * 判斷是否為延修班? 有1220, 122A, 1152A怪怪班 132D33進專資管三丙與1B2N21非畢業班
	 * 
	 * @param departClass
	 * @return True if is Delay Class
	 */
	public static boolean isDelayClass(String departClass) {
		/*
		if ("132D33".equals(departClass) || "1B2N21".equals(departClass)
				|| "1B2N31".equals(departClass))
			return false;
		String year = StringUtils.substring(departClass, 4, 5);
		if (StringUtils.isBlank(year)
				|| !StringUtils.isNumeric(StringUtils.substring(departClass,
						departClass.length() - 1)))
			return false;
		return Integer.valueOf(StringUtils.substring(departClass, 4, 5)) > getMaxyear(departClass);
		*/
		if ("132D33".equals(departClass) || "1B2N21".equals(departClass)
				|| "1B2N31".equals(departClass))
			return false;
		String year = StringUtils.substring(departClass, 4, 5);
		if(departClass.length()==7) {year = StringUtils.substring(departClass, 5, 6); System.out.println(departClass+"--"+year);}
		if (StringUtils.isBlank(year)
				|| !StringUtils.isNumeric(StringUtils.substring(departClass,
						departClass.length() - 1)))
			return false;
		if(departClass.length()==6 && Integer.valueOf(StringUtils.substring(departClass, 4, 5)) > getMaxyear(departClass)) 
			return true;
		if(departClass.length()==7 && Integer.valueOf(StringUtils.substring(departClass, 5, 6)) > getMaxyear(departClass)) 
			return true;
		return false;
	}

	/**
	 * 身份證檢查
	 * 
	 * @param IDNO 身份證 A=65...Z=90
	 * @return java.lang.Boolean
	 */
	public static boolean checkIdno(String id) {

		boolean rule = false;
		if (StringUtils.isBlank(id))
			return false;

		if (id.length() == 10) {

			id = id.toUpperCase();
			byte s[] = id.getBytes();

			if (s[0] >= 65 && s[0] <= 90) {
				int[] c = new int[11];

				int a[] = { 10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21,
						22, 35, 23, 24, 25, 26, 27, 28, 29, 32, 30, 31, 33 };

				c[0] = a[(s[0]) - 65] / 10;
				c[1] = a[(s[0]) - 65] % 10;
				for (int i = 1; i <= 9; i++) {
					c[i + 1] = s[i] - 48;
				}

				int count = c[0];
				for (int i = 1; i <= 9; i++) {
					count += c[i] * (10 - i);
				}

				/*
				 * if (((count%10)+c[10])==10) { rule=true; }
				 */

				if (((count % 10) + c[10]) % 10 == 0) {
					rule = true;
				}
			}
		}
		return (rule);
	}

	/**
	 * 取得HSSFWorkbook物件
	 * 
	 * @param filename XLS檔名
	 * @return org.apache.poi.hssf.usermodel.HSSFWorkbook Object
	 * @throws Exception
	 */
	public static HSSFWorkbook getHSSFWorkbook(String filename)
			throws Exception {
		return getHSSFWorkbook(new File(filename));
	}

	/**
	 * 取得HSSFWorkbook物件
	 * 
	 * @param filename XLS檔案
	 * @return org.apache.poi.hssf.usermodel.HSSFWorkbook Object
	 * @throws Exception
	 */
	public static HSSFWorkbook getHSSFWorkbook(File file) throws Exception {
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
		return new HSSFWorkbook(fs);
	}
	
	/**
	 * 取得HSSFWorkbook物件
	 * 
	 * @param filename XLS檔案
	 * @return org.apache.poi.hssf.usermodel.HSSFWorkbook Object
	 * @throws Exception
	 */
	public static HSSFWorkbook getHSSFWorkbook(InputStream is) throws Exception {
		return new HSSFWorkbook(is);
	}
	
	public static Workbook getWorkbookJXL(File file) throws Exception {
		return Workbook.getWorkbook(file);
	}

	public static Workbook getWorkbookJXL(InputStream is) throws Exception {
		return Workbook.getWorkbook(is);
	}

	public static Workbook getWorkbookJXL(InputStream is, WorkbookSettings wbs) throws Exception {
		return Workbook.getWorkbook(is, wbs);
	}
	
	/**
	 * 將資料置於Sheet指定位置內
	 * 
	 * @param sheet Excel Sheet
	 * @param row Row
	 * @param col Column
	 * @param value Value
	 */
	public static void setCellValue(HSSFSheet sheet, int row, int col,
			String value) {
		HSSFRow rowData = sheet.getRow(row);
		if (rowData == null)
			rowData = sheet.createRow((short) row);
		col = col < 0 ? 0 : col;
		HSSFCell cell = rowData.getCell(col);
		if (cell == null)
			cell = rowData.createCell(col);
		cell.setCellValue(new HSSFRichTextString(
				(StringUtils.isBlank(value) ? "" : value)));
	}

	/**
	 * 將資料置於Sheet指定位置內
	 * 
	 * @param sheet Excel Sheet
	 * @param row Row
	 * @param col Column
	 * @param value Value
	 */
	public static void setCellValueInt(HSSFSheet sheet, int row, int col,
			Integer value) {
		HSSFRow rowData = sheet.getRow(row);
		if (rowData == null)
			rowData = sheet.createRow((short) row);
		col = col < 0 ? 0 : col;
		HSSFCell cell = rowData.getCell(col);
		if (cell == null)
			cell = rowData.createCell(col);
		cell.setCellValue(value);
	}

	public static void setCellValueDbl(HSSFSheet sheet, int row, int col,
			Double value) {
		HSSFRow rowData = sheet.getRow(row);
		if (rowData == null)
			rowData = sheet.createRow((short) row);
		col = col < 0 ? 0 : col;
		HSSFCell cell = rowData.getCell(col);
		if (cell == null)
			cell = rowData.createCell(col);
		cell.setCellValue(value);
	}

	/**
	 * 將資料置於Sheet指定位置內
	 * 
	 * @param wb Excel WorkBook
	 * @param sheet Excel Sheet
	 * @param row Row
	 * @param col Column
	 * @param value Value
	 * @param font HSSFFont
	 */
	public static void setCellValue(HSSFWorkbook wb, HSSFSheet sheet, int row,
			int col, String value, HSSFCellStyle style) {
		HSSFRow rowData = sheet.getRow(row);
		if (rowData == null)
			rowData = sheet.createRow((short) row);
		HSSFCell cell = rowData.getCell(col);
		if (cell == null)
			cell = rowData.createCell(col);
		// cell.setCellType(HSSFCell.CELL_TYPE_STRING);

		if (style != null)
			cell.setCellStyle(style);

		cell.setCellValue(new HSSFRichTextString(
				(StringUtils.isBlank(value) ? "" : value)));
	}

	/**
	 * 將資料置於Sheet指定位置內
	 * 
	 * @param wb Excel WorkBook
	 * @param sheet Excel Sheet
	 * @param row Row
	 * @param col Column
	 * @param value Value
	 * @param font HSSFFont
	 * @param align Align
	 * @param hasBorder Has Border?
	 * @param fillColor Fill Color
	 */
	public static void setCellValue(HSSFWorkbook wb, HSSFSheet sheet, int row,
			int col, String value, HSSFFont font, short align,
			boolean hasBorder, Short fillColor) {
		HSSFRow rowData = sheet.getRow(row);
		if (rowData == null)
			rowData = sheet.createRow((short) row);
		HSSFCell cell = rowData.getCell(col);
		if (cell == null)
			cell = rowData.createCell(col);
		// cell.setCellType(HSSFCell.CELL_TYPE_STRING);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(align);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		if (fillColor != null) {
			cellStyle.setFillBackgroundColor(fillColor);
			cellStyle.setFillForegroundColor(fillColor);
			cellStyle.setFillPattern(HSSFCellStyle.SPARSE_DOTS);
		}
		if (hasBorder) {
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		}

		if (font != null)
			cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString(
				(StringUtils.isBlank(value) ? "" : value)));
	}

	/**
	 * 將資料置於Sheet指定位置內
	 * 
	 * @param wb Excel WorkBook
	 * @param sheet Excel Sheet
	 * @param row Row
	 * @param col Column
	 * @param value Value
	 * @param font HSSFFont
	 * @param align Align
	 * @param hasBorder Has Border?
	 * @param fillColor Fill Color
	 */
	public static void setCellValueForEngName(HSSFWorkbook wb, HSSFSheet sheet,
			int row, int col, String value, HSSFFont font, short align,
			boolean hasBorder, Short fillColor) {
		HSSFRow rowData = sheet.getRow(row);
		if (rowData == null)
			rowData = sheet.createRow((short) row);
		HSSFCell cell = rowData.getCell(col);
		if (cell == null)
			cell = rowData.createCell(col);
		// cell.setCellType(HSSFCell.CELL_TYPE_STRING);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(align);
		if (fillColor != null) {
			cellStyle.setFillBackgroundColor(fillColor);
			cellStyle.setFillForegroundColor(fillColor);
			cellStyle.setFillPattern(HSSFCellStyle.SPARSE_DOTS);
		}
		if (hasBorder) {
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		}

		if (font != null)
			cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString(
				(StringUtils.isBlank(value) ? "" : value)));
	}

	/**
	 * 將資料置於Sheet指定位置內
	 * 
	 * @param wb Excel WorkBook
	 * @param sheet Excel Sheet
	 * @param row Row
	 * @param col Column
	 * @param value Value
	 * @param font HSSFFont
	 * @param align Align
	 * @param hasBorder Has Border?
	 * @param rowHeight Row Height
	 * @param colWidth Column Width
	 */
	public static void setCellValue(HSSFWorkbook wb, HSSFSheet sheet, int row,
			int col, String value, HSSFFont font, short align,
			boolean hasBorder, Float rowHeight, Short colWidth) {
		HSSFRow rowData = sheet.getRow(row);
		if (rowData == null)
			rowData = sheet.createRow((short) row);
		if (rowHeight != null)
			rowData.setHeightInPoints(rowHeight);
		HSSFCell cell = rowData.getCell(col);
		if (cell == null)
			cell = rowData.createCell(col);
		// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(align);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		if (hasBorder) {
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		}

		if (font != null)
			cellStyle.setFont(font);

		if (colWidth != null)
			sheet.setColumnWidth(col, colWidth);

		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString(
				(StringUtils.isBlank(value) ? "" : value)));
	}

	/**
	 * 將資料置於Sheet指定位置內
	 * 
	 * @param wb Excel WorkBook
	 * @param sheet Excel Sheet
	 * @param row Row
	 * @param col Column
	 * @param value Value
	 * @param font HSSFFont
	 * @param align Align
	 * @param hasBorder Has Border?
	 * @param thin is Thin Border?
	 * @param rowHeight Row Height
	 * @param colWidth Column Width
	 */
	public static void setCellValue(HSSFWorkbook wb, HSSFSheet sheet, int row,
			int col, String value, HSSFFont font, short align,
			boolean hasBorder, boolean thin, Float rowHeight, Short colWidth) {
		HSSFRow rowData = sheet.getRow(row);
		if (rowData == null)
			rowData = sheet.createRow((short) row);
		if (rowHeight != null)
			rowData.setHeightInPoints(rowHeight);
		HSSFCell cell = rowData.getCell(col);
		if (cell == null)
			cell = rowData.createCell(col);
		// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(align);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		if (hasBorder) {
			if (thin) {
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			} else {
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
			}
		}

		if (font != null)
			cellStyle.setFont(font);

		if (colWidth != null)
			sheet.setColumnWidth(col, colWidth);

		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString(
				(StringUtils.isBlank(value) ? "" : value)));
	}

	/**
	 * 取得Sheet內指定位置的值
	 * 
	 * @param sheet Excel Sheet
	 * @param row Row
	 * @param col Column
	 * @return java.lang.String Value
	 */
	public static String getCellValue(HSSFSheet sheet, int row, int col) {
		HSSFRow rowData = sheet.getRow(row);
		HSSFCell cell = rowData.getCell(col);
		String ret = cell == null ? "" : cell.getRichStringCellValue()
				.getString();
		return (StringUtils.isBlank(ret) ? "" : cell.getRichStringCellValue()
				.getString());
	}

	/**
	 * 移除Row
	 * 
	 * @param sheet Excel Sheet
	 * @param start Start Index
	 * @param length Length for Delete
	 */
	public static void removeRow(HSSFSheet sheet, int start, int length) {
		HSSFRow row = null;
		for (int i = start; i <= start + length; i++) {
			row = sheet.getRow(i);
			if (row != null)
				sheet.removeRow(row);
		}
	}

	public static Calendar beginOfCalendar(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Calendar endOfCalendar(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal;
	}

	public static Image getBarCodeImage(String code) {

		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		Image image = null;
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("barcodes.pdf"));
			document.open();
			PdfContentByte cb = writer.getDirectContent();
			Barcode39 code39 = new Barcode39();
			code39.setCode(code);
			code39.setStartStopText(false);
			image = code39.createImageWithBarcode(cb, null, Color.blue);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}

		document.close();
		return image;
	}

	public static byte[] getBarCodeArray(String code) {

		byte[] ret = null;
		try {
			Image image = getBarCodeImage(code);
			ret = image.getOriginalData();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}

		return ret;
	}

	/**
	 * 取得部學制英文正式名稱
	 * 
	 * @param departClass Depart Class No
	 * @return
	 */
	public static String getEnglishSchoolFormalName(String departClass) {
		if (StringUtils.isBlank(departClass))
			return "";

		String schoolNo = StringUtils.substring(departClass, 1, 3);
		String ret = "";
		if ("54".equals(schoolNo) || "64".equals(schoolNo))
			ret = "4-Year College";
		else if ("42".equals(schoolNo) || "52".equals(schoolNo)
				|| "82".equals(schoolNo))
			ret = "2-Year Senior College";
		else if ("12".equals(schoolNo) || "22".equals(schoolNo)
				|| "92".equals(schoolNo))
			ret = "2-Year Junior College";
		else if ("15".equals(schoolNo))
			ret = "5-Year Junior College";
		else if ("32".equals(schoolNo) || "72".equals(schoolNo))
			ret = "";
		else if ("1G".equals(schoolNo) || "8G".equals(schoolNo))
			ret = "Graduate School";
		else
			ret = "";

		return ret;
	}

	/**
	 * 找出傳入參數之相關所有班級
	 * 
	 * @param clazzFilter 參數like 164
	 * @return List<Clazz>
	 */
	public static List<Clazz> findAllClasses(String clazzFilter) {
		ScoreManager dao = (ScoreManager) Global.context
				.getBean("scoreManager");
		return dao.findAllClasses(clazzFilter);

	}

	/**
	 * 取得下學年學期資訊
	 * 
	 * @return
	 */
	public static Map<String, Integer> getNextYearTerm() {
		Map<String, Integer> ret = new HashMap<String, Integer>();
		AdminManager dao = (AdminManager) Global.context
				.getBean("adminManager");
		Integer schoolYear = Integer.valueOf(dao
				.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR));
		Integer schoolTerm = Integer.valueOf(dao
				.findTermBy(IConstants.PARAMETER_SCHOOL_TERM));
		if (schoolTerm == 2) {
			schoolTerm = 1;
			schoolYear++;
		} else
			schoolTerm = 2;

		ret.put(IConstants.PARAMETER_SCHOOL_YEAR, schoolYear);
		ret.put(IConstants.PARAMETER_SCHOOL_TERM, schoolTerm);
		return ret;
	}

	/**
	 * 取得上學年學期資訊
	 * 
	 * @return
	 */
	public static Map<String, Integer> getPreviousYearTerm() {
		Map<String, Integer> ret = new HashMap<String, Integer>();
		AdminDAO dao = (AdminDAO) Global.context.getBean("adminDAO");
		Integer schoolYear = Integer.valueOf(dao.getParameterBy(
				IConstants.PARAMETER_SCHOOL_YEAR).getValue());
		Integer schoolTerm = Integer.valueOf(dao.getParameterBy(
				IConstants.PARAMETER_SCHOOL_TERM).getValue());
		if (schoolTerm == 1) {
			schoolTerm = 2;
			schoolYear--;
		} else
			schoolTerm = 1;

		ret.put(IConstants.PARAMETER_SCHOOL_YEAR, schoolYear);
		ret.put(IConstants.PARAMETER_SCHOOL_TERM, schoolTerm);
		return ret;
	}

	/**
	 * 取得課程選項
	 * 
	 * @param elearning
	 * @return
	 */
	public static String getElearning(char elearning) {
		switch (elearning) {
			case '0':
				return "一般課程";
			case '1':
				return "遠距課程";
			case '2':
				return "輔助課程";
			case '3':
				return "多媒體";
			default:
				return "";
		}
	}

	public static String getDocType(String code) {
		if (StringUtils.isBlank(code))
			return "";

		switch (Integer.parseInt(code)) {
			case 1:
				return "請假";

			case 2:
				return "加班";

			case 3:
				return "補登";

			case 4:
				return "銷假";

			default:
				return "";
		}
	}
	
	public static String getCollegeCode(String deptCode) {
		if (StringUtils.isBlank(deptCode))
			return "";

		if (ArrayUtils.contains(IConstants.COLLEGE_ENGINEERING, deptCode))
			return "E";
		else if (ArrayUtils.contains(IConstants.COLLEGE_COMMERCE, deptCode))
			return "C";
		else if (ArrayUtils.contains(IConstants.COLLEGE_HEALTH, deptCode))
			return "H";
		else if (ArrayUtils.contains(IConstants.COLLEGE_AIR, deptCode))
			return "A";
		else
			return "";
	}

	public static String getAmsAskLeave(String code) {

		if (StringUtils.isBlank(code))
			return "";

		switch (Integer.parseInt(code)) {
			case 1:
				return "事假";

			case 2:
				return "病假";

			case 3:
				return "公差假";

			case 4:
				return "婚假";

			case 5:
				return "產假";

			case 6:
				return "喪假";

			case 7:
				return "年假";

			case 9:
				return "補休假";

			case 10:
				return "陪產假";

			case 12:
				return "特休";

			case 13:
				return "產前假";

			case 14:
				return "生理假";

			case 15:
				return "公假";

			case 16:
				return "校外加班補休";

			default:
				return "";
		}
	}

	public static String getWorkNature(String workNatureCode) {
		if (StringUtils.isBlank(workNatureCode))
			return "";

		switch (workNatureCode.charAt(0)) {
			case '1':
				return "製造";

			case '2':
				return "工程營造";

			case '3':
				return "金融及保險";

			case '4':
				return "公共行政";

			case '5':
				return "社會服務";

			case '6':
				return "工商服務";

			case '7':
				return "其他";

			default:
				return "";

		}
	}

	public static String getWorkDuty(String workDutyCode) {
		if (StringUtils.isBlank(workDutyCode))
			return "";

		switch (workDutyCode.charAt(0)) {
			case '1':
				return "專業主管";

			case '2':
				return "專業技術人員";

			case '3':
				return "監督管理";

			case '4':
				return "設計企劃";

			case '5':
				return "營業銷售";

			case '6':
				return "教育訓練";

			case '7':
				return "其他";

			default:
				return "";
		}
	}

	public static String getSalaryRange(String salaryRangeCode) {
		if (StringUtils.isBlank(salaryRangeCode))
			return "";

		switch (salaryRangeCode.charAt(0)) {
			case '0':
				return "30萬以下";

			case '1':
				return "31萬~60萬";

			case '2':
				return "61萬~90萬";

			case '3':
				return "91萬~120萬";

			case '4':
				return "121萬~150萬";

			case '5':
				return "151萬以上";

			default:
				return "";
		}
	}

	public static String getArmyType(String armyTypeCode) {
		if (StringUtils.isBlank(armyTypeCode))
			return "";

		switch (armyTypeCode.charAt(0)) {
			case '0':
				return "義務役";

			case '1':
				return "自願役";

			case '2':
				return "替代役";

			default:
				return "";
		}
	}

	public static String getAmountType(String amountType) {
		if (StringUtils.isBlank(amountType))
			return "";

		switch (Integer.parseInt(amountType)) {
			case 1:
				return "專業證照報名費";

			case 2:
				return "特種獎學金";

			case 3:
				return "無補助";

			default:
				return "";
		}
	}

	public static String getCustomNo(String customNo) {
		if (StringUtils.isBlank(customNo))
			return "";

		switch (Integer.parseInt(customNo)) {
		 	
			case 1:
				return "公職考試";

			case 2:
				return "語文證照-英文";

			case 3:
				return "語文證照-非英文";
			
			case 4:
				return "國際認證";
			
			case 5:
				return "政府機關";
			
			case 6:
				return "其他";

			default:
				return "";
			
		 	//Leo 20120311 
		 	/*
			case 1:
				return "語文類";

			case 2:
				return "資訊類";

			case 3:
				return "專業類";

			case 4:
				return "其他";

			default:
				return "";
			*/
		}
	}
	
	// 爛方法,怎樣
	public static String getPass(String pass) {
		if (StringUtils.isBlank(pass))
			return "";

		if ("1".equals(pass.trim()))
			return "合格";
		else
			return "";
	}
	
	public static String getApplyType(String applyType) {
		if (StringUtils.isBlank(applyType))
			return "";

		if ("0".equals(applyType.trim()))
			return "單獨";
		else if ("1".equals(applyType))
			return "團體";
		else
			return "";
	}

	/**
	 * 將時數轉為天數.時數
	 * 
	 * @param hours
	 * @return
	 */
	public static Float calHoursToDate(Integer hours) {
		int days = hours / 8;
		float hour = (hours % 8) / 8.0f;
		return (float) days + hour;
	}

	/**
	 * 將分鐘轉為時數
	 * 
	 * @param mins
	 * @return
	 */
	public static Float calMinsToHours(Integer mins) {
		int hours = mins / 60;
		float min = (mins % 60) / 60.0f;
		return (float) hours + min;
	}

	/**
	 * 因為Dilg內的Sunday為1,Saturday為7...等跟DtimeClass內的week不符,所以需要調整
	 * 
	 * @param week
	 */
	public static int processWeek(int week) {
		switch (week) {
			case 1:
				return 7;

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				return week - 1;

			default:
				throw new IllegalArgumentException("Wrong week argument...");
		}
	}

	public static boolean isYearTermTooOver(Integer year, Integer term) {
		boolean isOver = false;
		Map<String, Integer> nextYearTerm = getNextYearTerm();
		if (year > nextYearTerm.get(IConstants.PARAMETER_SCHOOL_YEAR)) {
			isOver = true;
		} else if (year >= nextYearTerm.get(IConstants.PARAMETER_SCHOOL_YEAR)
				&& term > nextYearTerm.get(IConstants.PARAMETER_SCHOOL_TERM)) {
			isOver = true;
		}
		return isOver;
	}

	public static Map<String, Integer> getYearTermInfo(Integer type) {
		Map<String, Integer> ret = new HashMap<String, Integer>();
		AdminDAO dao = (AdminDAO) Global.context.getBean("adminDAO");
		Integer schoolYear = Integer.valueOf(dao.getParameterBy(
				IConstants.PARAMETER_SCHOOL_YEAR).getValue());
		Integer schoolTerm = Integer.valueOf(dao.getParameterBy(
				IConstants.PARAMETER_SCHOOL_TERM).getValue());
		if (IConstants.LAST_YEAR == type) {
			if (schoolTerm == 1) {
				schoolTerm = 2;
				schoolYear--;
			} else
				schoolTerm = 1;
		} else if (IConstants.THIS_YEAR == type) {

		} else if (IConstants.NEXT_YEAR == type) {
			if (schoolTerm == 2) {
				schoolTerm = 1;
				schoolYear++;
			} else
				schoolTerm = 2;
		}

		ret.put(IConstants.PARAMETER_SCHOOL_YEAR, schoolYear);
		ret.put(IConstants.PARAMETER_SCHOOL_TERM, schoolTerm);
		return ret;
	}

	public static String getWeekInfo(Date d) {
		Calendar cal = Calendar.getInstance(Locale.TAIWAN);
		cal.setTime(d);
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY:
				return "日";

			case Calendar.MONDAY:
				return "一";

			case Calendar.TUESDAY:
				return "二";

			case Calendar.WEDNESDAY:
				return "三";

			case Calendar.THURSDAY:
				return "四";

			case Calendar.FRIDAY:
				return "五";

			case Calendar.SATURDAY:
				return "六";

			default:
				return "";
		}
	}
	
	public static String getOpinionDeptName(String code) {
		if (StringUtils.isBlank(code))
			return "";

		switch (Integer.parseInt(code)) {
			case 1:
				return "日間部教務處";

			case 2:
				return "日間部學務處";

			case 3:
				return "總務處";

			case 4:
				return "進修部教務組";

			case 5:
				return "進修部學務組";

			case 6:
				return "進修學院教務組";

			case 7:
				return "進修學院學務組";

			case 8:
				return "會計室";

			case 9:
				return "秘書室";

			case 10:
				return "人事室";

			case 11:
				return "電算中心";

			case 12:
				return "新竹分部主任";

			case 13:
				return "新竹分部教務組";

			case 14:
				return "新竹分部學務組";
			case 15:
				return "新竹分部總務組";

			case 16:
				return "新竹分部會計";

			case 17:
				return "校本部秘書室";

			case 18:
				return "校本部人事室";

			case 19:
				return "新竹分部電算中心";

			case 20:
				return "圖書館";

			case 21:
				return "新竹分部圖書館";

			default:
				return "";
		}
	}

	public static String getDateDistance(Date d1, Date d2) {
		long distance = d2.getTime() - d1.getTime();
		long hours = distance % 60;
		return String.valueOf(hours);
	}

	public static String getTimeDistance(Time t1, Time t2) {
		DateFormat dt = new SimpleDateFormat("kk:mm", Locale.TAIWAN);
		String tt1 = dt.format(t1);
		String tt2 = dt.format(t2);
		StringBuffer buffer = new StringBuffer();
		if (Integer.parseInt(StringUtils.substring(tt2, 3)) >= Integer
				.parseInt(StringUtils.substring(tt1, 3))) {
			buffer.append(StringUtils.leftPad(String.valueOf(Integer
					.parseInt(StringUtils.substring(tt2, 0, 2))
					- Integer.parseInt(StringUtils.substring(tt1, 0, 2))), 2,
					'0'));
			buffer.append(":");
			buffer
					.append(StringUtils.leftPad(String.valueOf(Integer
							.parseInt(StringUtils.substring(tt2, 3))
							- Integer.parseInt(StringUtils.substring(tt1, 3))),
							2, '0'));
		} else {
			buffer.append(StringUtils.leftPad(String.valueOf(Integer
					.parseInt(StringUtils.substring(tt2, 0, 2))
					- Integer.parseInt(StringUtils.substring(tt1, 0, 2)) - 1),
					2, '0'));
			buffer.append(":");
			buffer.append(StringUtils.leftPad(String.valueOf(Integer
					.parseInt(StringUtils.substring(tt2, 3))
					+ 60 - Integer.parseInt(StringUtils.substring(tt1, 3))), 2,
					'0'));
		}

		return buffer.toString();
	}

	public static String[] getYearArray(String schoolYear, int yearCal) {
		int year = Integer.parseInt(schoolYear);
		String[] years = new String[yearCal];
		years[yearCal - 1] = schoolYear;
		for (int i = yearCal - 2; i > 0; i--)
			years[i] = String.valueOf((year = year - 1));

		return years;
	}

	/**
	 * 
	 * @param schoolYear
	 * @param yearCal
	 * @return schoolYear=2010, yearCal=3, return={2009,2010,2011}
	 */
	public static String[] getYearArray1(String schoolYear, int yearCal) {
		int year = Integer.parseInt(schoolYear);
		String[] years = new String[yearCal];
		years[0] = String.valueOf(year - 1);
		years[1] = String.valueOf(year);
		for (int i = 2; i < years.length; i++)
			years[i] = String.valueOf(++year);

		return years;
	}

	public static String[] getDayOfMonthArray(Calendar cal) {
		int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		max = 31; // 只能選31了,免得查不到
		String[] ret = new String[max];
		for (int i = 1; i <= max; i++)
			ret[i - 1] = String.valueOf(i);

		return ret;
	}

	public static String getRoundFormat(DecimalFormat df, double num,
			double range) {
		return df.format(num + range);
	}

	/**
	 * 取得該班級之畢業年級(定義在 Code5 category='GradYear')
	 * 
	 * @param departClass 班級代碼
	 * 
	 * @return int 畢業年級
	 */
	public static int getGraduateYear(String departClass) {
		int gradYear = -1;
		StudAffairDAO dao = (StudAffairDAO) Global.context
				.getBean("studAffairDAO");

		/*
		 * 決定該班級之畢業年級 特例:日二專建築為三年
		 */
		if (departClass.length() == 6) {
			String gradept = departClass.substring(1, 3);
			String hql = "select c5 from Code5 c5 Where category='GradYear' And idno like '"
					+ gradept + "%'";
			List<Code5> gyearlist = dao.submitQuery(hql);
			if (gyearlist.size() > 0) {
				if (gyearlist.size() > 1) {
					for (Iterator<Code5> gyearIter = gyearlist.iterator(); gyearIter
							.hasNext();) {
						Code5 code5 = gyearIter.next();
						if (code5.getIdno().trim().equals(
								departClass.substring(1, 4))) {
							gradYear = Integer.parseInt(code5.getName());
							break;
						} else if (code5.getIdno().trim().equals(
								departClass.substring(1, 3))) {
							gradYear = Integer.parseInt(code5.getName());
							// break;
						}
					}
				} else {
					gradYear = Integer.parseInt(gyearlist.get(0).getName());
				}
			}

		}
		return gradYear;
	}

	/*
	 * public static Map getPageList() { PageLink pl; return Global.PageLinkMap;
	 * }
	 * 
	 * public static void refreshPageList() { CourseManager manager =
	 * (CourseManager) Global.context.getBean("courseManager"); synchronized
	 * (Global.PageLink) { Global.PageLinkMap.clear();
	 * //Global.PageLinkMap.put("a", manager.getPageList("a"));
	 * //Global.PageLinkMap.put("p", manager.getPageList("p"));
	 * //Global.PageLinkMap.put("out", manager.getPageList("o"));
	 * Global.PageLinkMap.put("s", manager.getPageList("s"));
	 * Global.PageLinkMap.put("t", manager.getPageList("t"));
	 * Global.PageLinkMap.put("stf", manager.getPageList("stf")); } }
	 */

	/**
	 * 取得日四技本學期第startWeek週至endWeek週之日期
	 * 
	 * @param startWeek 起始週次
	 * @param endWeek 結束週次
	 * 
	 * @return Calendar[startDate, endDate]
	 */
	public static Calendar[] getDateBetweenWeeks(int startWeek, int endWeek) {
		Calendar[] cals = new Calendar[2];
		Calendar first = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		first.clear();
		StudAffairJdbcDAO jdbcDao = (StudAffairJdbcDAO) Global.context
				.getBean("studAffairJdbcDAO");

		String sql = "Select * From week Where daynite='16'";
		List tmpList = jdbcDao.findAnyThing(sql);
		String sdate = ((Map) tmpList.get(0)).get("wdate").toString();
		// log.debug(sdate);

		String[] dates = sdate.split("-");
		first.set(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1,
				Integer.parseInt(dates[2]), 0, 0, 0);
		end.set(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1,
				Integer.parseInt(dates[2]), 0, 0, 0);
		cals[0] = first;
		if (startWeek > 1) {
			cals[0].add(Calendar.DAY_OF_MONTH, (startWeek - 1) * 7);
		}
		cals[1] = end;
		cals[1].add(Calendar.DAY_OF_MONTH, (endWeek) * 7 - 1);

		return cals;
	}

	/**
	 * 取得某學制技本學期第startWeek週之起始及結束日期;找不到該部制開學時間就用日四技的
	 * 
	 * @param dept 學制 String[2],例如16:台北日四技...
	 * @param startWeek 週次
	 * 
	 * @return Calendar[startDate, endDate]
	 */
	public static Calendar[] getDateOfWeek(String dept, int startWeek) {
		Calendar[] cals = new Calendar[2];
		Calendar first = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		first.clear();
		StudAffairJdbcDAO jdbcDao = (StudAffairJdbcDAO) Global.context
				.getBean("studAffairJdbcDAO");

		String sql = "Select * From week Where daynite='" + dept + "'";
		List tmpList = jdbcDao.findAnyThing(sql);
		if(tmpList.isEmpty()){
			if(dept.charAt(0)=='2'){
				sql = "Select * From week Where daynite='26'";
			}else{
				sql = "Select * From week Where daynite='16'";
			}
			tmpList = jdbcDao.findAnyThing(sql);
		}
		String sdate = ((Map) tmpList.get(0)).get("wdate").toString();
		// log.debug(sdate);

		String[] dates = sdate.split("-");
		first.set(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1,
				Integer.parseInt(dates[2]), 0, 0, 0);
		end.set(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1,
				Integer.parseInt(dates[2]), 23, 59, 59);
		cals[0] = first;
		if (startWeek > 1) {
			cals[0].add(Calendar.DAY_OF_MONTH, (startWeek - 1) * 7);
		}
		cals[1] = end;
		cals[1].add(Calendar.DAY_OF_MONTH, (startWeek) * 7 - 1);
		log.debug("start:" + sdate + ", " + cals[0].getTime() + ","
				+ cals[1].getTime());
		return cals;
	}

	public static void deleteDIR(File tempdir) {
		File[] files = tempdir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				deleteDIR(file);
			} else if (file.isFile()) {
				file.delete();
			}
		}
		tempdir.delete();
	}

	/**
	 * 檢查班級所屬部制為 1:日間, 2:進修, 3:學院
	 * 
	 * @param classNo 班級代碼
	 * @return 1:日間, 2:進修, 3:學院, 0:不知道
	 */
	public static String chkDayNite(String classNo) {
		// 由班級代碼判斷部制為:日間部,進修部(夜間),進修學院專校
		if (classNo.length() < 4)
			return "0";
		String dept = classNo.substring(0, 2).toUpperCase();
		StudAffairDAO dao = (StudAffairDAO) Global.context
				.getBean("studAffairDAO");
		String daynite = "0";
		String hql = "select c5 from Code5 c5 Where category='SchoolType' And idno='"
				+ dept + "'";
		List<Code5> deptlist = dao.submitQuery(hql);
		if (deptlist.size() > 0) {
			daynite = deptlist.get(0).getName().substring(1, 2);
		}
		return daynite;
	}

	public static String readJSONStringFromRequestBody(
			HttpServletRequest request) {
		StringBuffer json = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			line = reader.readLine();
			// log.debug(line);
			while (line != null) {
				json.append(line);
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	public static boolean isValidEmail(String email) {
		boolean ismatch = email.matches("^\\S+@\\S+\\.\\S+$");
		return ismatch;
	}

	public static boolean isHoliday(Calendar hcal) {
		boolean isHoliday = false;
		StudAffairJdbcDAO jdbcDao = (StudAffairJdbcDAO) Global.context
				.getBean("studAffairJdbcDAO");

		String sql = "Select * From holiday Where hdate='"
				+ Toolket.FullDate2Str(hcal.getTime()) + "'";
		List tmpList = jdbcDao.findAnyThing(sql);

		if (!tmpList.isEmpty()) {
			isHoliday = true;
		}
		return isHoliday;

	}

	/**
	 * 
	 * @param aDateString a date string in the format "yyyy-MM-dd" in which
	 *            "yyyy" is a centry year
	 * @return a Date instance that time truncated to 0:00AM
	 * 
	 */
	public static Date parseFullDate(String aDateString) {

		aDateString = aDateString.trim();
		int year = 0, month = 0, day = 0;
		String[] tDate = null;
		if (aDateString.indexOf('/') >= 0) {
			tDate = aDateString.split("/");
		} else if (aDateString.indexOf('-') >= 0) {
			tDate = aDateString.split("-");
		}
		try {
			year = Integer.parseInt(tDate[0]);
			month = Integer.parseInt(tDate[1]);
			day = Integer.parseInt(tDate[2]);
			if (month <= 0 || day <= 0)
				return null;
			if (month > 12 || day > 31)
				return null;
			Calendar aDate = Calendar.getInstance();
			aDate.set(year, month - 1, day, 0, 0, 0);
			return aDate.getTime();
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * 取得所有的單位
	 * 
	 * @return
	 */
	public static List allUnit() {
		StudAffairJdbcDAO jdbcDao = (StudAffairJdbcDAO) Global.context
				.getBean("studAffairJdbcDAO");
		List list = jdbcDao
				.findAnyThing("SELECT idno, name FROM CodeEmpl WHERE category='Unit'");
		list
				.addAll(jdbcDao
						.findAnyThing("SELECT idno, name FROM CodeEmpl WHERE category='UnitTeach'"));
		return list;
	}

	/**
	 * 取得所有的單位
	 * 
	 * @return
	 */
	public static List allModule() {
		StudAffairJdbcDAO jdbcDao = (StudAffairJdbcDAO) Global.context
				.getBean("studAffairJdbcDAO");
		List list = jdbcDao
				.findAnyThing("SELECT Name, Label FROM Module WHERE ParentOid='0'");
		return list;
	}

	public static void refreshSchoolNameZH() {
		CourseJdbcDAO dao = (CourseJdbcDAO) Global.context
				.getBean("courseJdbcDAO");
		synchronized (Global.SchoolNameZH) {
			try {
				Global.SchoolNameZH = dao
						.ezGetString("SELECT Value FROM Parameter WHERE Name='SchoolName' AND Category='ZH'");
			} catch (Exception e) {
				Global.SchoolNameZH = "";
			}

		}
	}

	public static void refreshSchoolNameEN() {
		CourseJdbcDAO dao = (CourseJdbcDAO) Global.context
				.getBean("courseJdbcDAO");
		synchronized (Global.SchoolNameZH) {
			try {
				Global.SchoolNameEN = dao
						.ezGetString("SELECT Value FROM Parameter WHERE Name='SchoolName' AND Category='EN'");
			} catch (Exception e) {
				Global.SchoolNameEN = "";
			}
		}
	}

	/**
	 * 查看這個人是不是老師(由本學期授課)
	 * 
	 * @param idno 身分證號
	 * @return boolean
	 */
	public static boolean isTeaching(String idno) {
		boolean isTeacher = false;
		StudAffairJdbcDAO jdbcDao = (StudAffairJdbcDAO) Global.context
				.getBean("studAffairJdbcDAO");
		String sterm = getSysParameter("School_term");

		String sql = "Select techid From Dtime Where techid='" + idno
				+ "' And Sterm='" + sterm + "'";
		List tmpList = jdbcDao.findAnyThing(sql);
		if (!tmpList.isEmpty()) {
			isTeacher = true;
		}
		sql = "Select t.teach_id From Dtime_teacher t, Dtime d Where t.Dtime_oid=d.Oid And d.Sterm='"
				+ sterm + "' And ";
		sql += " t.teach_id='" + idno + "'";
		tmpList = jdbcDao.findAnyThing(sql);
		if (!tmpList.isEmpty()) {
			isTeacher = true;
		}

		return isTeacher;
	}

	/**
	 * 查看這個人是不是單純老師(無兼任行政職務) (檢查empl檔,專任職務pcode及兼任職務Director)
	 * 
	 * @param idno 身分證號
	 * @return boolean
	 */
	public static boolean isPureTeacher(String idno) {
		boolean isTeacher = false;
		StudAffairJdbcDAO jdbcDao = (StudAffairJdbcDAO) Global.context
				.getBean("studAffairJdbcDAO");

		String sql = "Select * From empl Where idno='" + idno
				+ "' And pcode in (";
		sql += "select idno From CodeEmpl Where category like 'Teacher%' or category like 'Tutor%' or (category like 'EmplRoleStaff%' and idno='19' or idno='20'))";
		// sql += " And Director<>'' And Director is not null";
		List tmpList = jdbcDao.findAnyThing(sql);
		if (!tmpList.isEmpty()) {
			Map empl = (Map) (tmpList.get(0));
			if (empl.get("Director") != null) {
				//if (empl.get("Director").toString().trim().equals("")) {
					isTeacher = true;
				//}
			} else {
				isTeacher = true;
			}
		}

		return isTeacher;
	}

	/**
	 * 計算兩個日期的差距
	 * 
	 * @param start 開始日期
	 * @param end 結束日期
	 * 
	 * @return int[0]:幾日, int[1]:幾小時, int[2]:幾分, int[3]:幾秒
	 */
	public static int[] timeTransfer(Calendar start, Calendar end) {
		int[] timediff = { 0, 0, 0, 0 };
		long dist = end.getTimeInMillis() - start.getTimeInMillis();

		dist = (dist - dist % 1000) / 1000;
		timediff[0] = (int) (dist - dist % 86400) / 86400;
		dist = dist - 86400 * timediff[0];
		timediff[1] = (int) (dist - dist % 3600) / 3600;
		dist = dist - timediff[1] * 3600;
		timediff[2] = (int) (dist - dist % 60) / 60;
		timediff[3] = (int) (dist - timediff[2] * 60);

		return timediff;
	}

	/**
	 * 檢查是否為同一天(忽略[時][分][秒])
	 * 
	 * @param cal1 第一個日期
	 * @param cal2 第二個日期
	 * 
	 * @return 是否?
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {

		return cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}

	/**
	 * 取得教職員工姓名(含已離職)
	 * 
	 * @param idno 身分證字號
	 * @return 姓名
	 */
	public static String getEmplName(String idno) {
		MemberManager mm = (MemberManager) Global.context
				.getBean("memberManager");
		String CName = "";
		Empl empl = mm.findEmplByIdno(idno);
		if (empl != null) {
			CName = empl.getCname();
		} else {
			DEmpl dempl = mm.findDEmplByIdno(idno);
			if (dempl != null) {
				CName = dempl.getCname();
			}
		}
		return CName;
	}

	/**
	 * 取得全校有哪些系所
	 * 
	 * @param type true:含通識, false:不含通識
	 * 
	 * @return List<Map> {name, idno}
	 */
	public static List<Map> getCollegeDepartment(boolean type) {
		StudAffairJdbcDAO jdbcDao = (StudAffairJdbcDAO) Global.context
				.getBean("studAffairJdbcDAO");

		String sql = "";
		if (type) {
			sql = "Select name, idno FROM code5 WHERE category='Dept' ORDER BY sequence";
		} else {
			sql = "Select name, idno FROM code5 WHERE category='Dept' AND idno <>'0' ORDER BY sequence";
		}
		List tmpList = jdbcDao.findAnyThing(sql);
		return tmpList;
	}

	/**
	 * 取得老師點名截止週次
	 * 
	 * @return 週次
	 */
	public static int getTimeOffUploadDeadline() {
		StudAffairJdbcDAO jdbcDao = (StudAffairJdbcDAO) Global.context
				.getBean("studAffairJdbcDAO");

		String sql = "Select idno FROM code5 WHERE category='TFUploadDeadline'";
		List tmpList = jdbcDao.findAnyThing(sql);

		return Integer.parseInt(((Map) tmpList.get(0)).get("idno").toString());
	}

	/**
	 * 取得班導師資料
	 * 
	 * @param classNo 班級代碼
	 * @return Empl 導師資料
	 */
	public static Empl findTutorOfClass(String classNo) {
		Empl tutor = null;
		StudAffairJdbcDAO jdbcDao = (StudAffairJdbcDAO) Global.context
				.getBean("studAffairJdbcDAO");

		StudAffairDAO dao = (StudAffairDAO) Global.context
				.getBean("studAffairDAO");

		String sql = "Select EmpOid FROM ClassInCharge WHERE ClassNo='"
				+ classNo + "' And ModuleOids like '%86%'";
		List tmpList = jdbcDao.findAnyThing(sql);

		if (!tmpList.isEmpty()) {
			String hql = "From Empl Where oid="
					+ Integer.parseInt(((Map) tmpList.get(0)).get("EmpOid")
							.toString());

			tmpList = dao.submitQuery(hql);
			if (!tmpList.isEmpty()) {
				tutor = (Empl) tmpList.get(0);
			}
		}
		return tutor;

	}

	/**
	 * 取得該班級所屬部制 : 11台北日間, 12台北進修, 13台北學院, 21新竹日間, .....
	 * 
	 * @param clazz 班級代碼,長度至少為2,例如:16, 164, 1643,...
	 * @return 部制, 11台北日間, 12台北進修, 13台北學院, 21新竹日間, .....
	 */
	public static String getDepartNo(String clazz) {
		String dept = null;
		StudAffairJdbcDAO jdbcDao = (StudAffairJdbcDAO) Global.context
				.getBean("studAffairJdbcDAO");

		if (clazz.trim().length() >= 2) {
			String sql = "SELECT name FROM code5 c Where c.category='SchoolType' And idno='"
					+ clazz.substring(0, 2) + "'";
			List tmpList = jdbcDao.findAnyThing(sql);
			if (!tmpList.isEmpty()) {
				dept = ((Map) tmpList.get(0)).get("name").toString();
			}
		}
		return dept;
	}
	
	/**
	 * 取得職員工所屬單位名稱
	 * @param unitNo 單位代號
	 * @return 單位名稱或空白
	 */
	public static String getUnitName(String unitNo){
		String unitName = "";
		StudAffairJdbcDAO jdbcDao = (StudAffairJdbcDAO) Global.context
				.getBean("studAffairJdbcDAO");

		if (!unitNo.trim().equals("")) {
			String sql = "SELECT name FROM CodeEmpl C Where category like 'Unit%' And idno='"
					+ unitNo + "'";
			List tmpList = jdbcDao.findAnyThing(sql);
			if (!tmpList.isEmpty()) {
				unitName = ((Map) tmpList.get(0)).get("name").toString();
			}
		}
		return unitName;
		
	}

}
