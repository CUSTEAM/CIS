package tw.edu.chit.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;

import tw.edu.chit.model.Aborigine;
import tw.edu.chit.model.AmsAskLeave;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.CodeEmpl;
import tw.edu.chit.model.FeeCode;
import tw.edu.chit.model.LiteracyType;

public class Global {
	
	/**
	 * Spring's root application context
	 */
	public static ApplicationContext context = null;
	
	//public static Parameter accountNum = null;
	
	//public static MiscDAO miscDao = null;
	
	/**
	 * Default resource bundle
	 */
	//public static ResourceBundle defaultBundle = null;
	
	/**
	 * Base of native year, eg. 1911 for ROC's calendar
	 */
	public static final int NativeYearBase = 1911;
	
	
	public static final DateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat TWDateFormat = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss", Locale.TAIWAN);
	
	public static final String NewLine = "\r\n";
	
	public static final Pattern AccountPattern  = Pattern.compile("[a-zA-Z0-9_.]+");
	public static final Pattern PasswordPattern = Pattern.compile("[a-zA-Z0-9_.]+");
	
	//public static List<Csno> Courses = new ArrayList<Csno>();
	//public static List<Empl> Employees= new ArrayList<Empl>();
	//public static List<Clazz> Classes= new ArrayList<Clazz>();
	
	/**
	 * ClassNo to class full name map
	 * key: ClassNo
	 * value: class full name, eg. 台北二專機械一甲
	 */
	public static final Properties ClassFullName = new Properties();
	
	public static final Map<String, String> AllClassJavaScriptArray = new HashMap<String,String>();
	
	public static final Properties 	Campus = new Properties();
	public static final List<Code5>	CampusList = new ArrayList<Code5>();
	
	public static final Properties	School = new Properties();
	public static final List<Code5>	SchoolList = new ArrayList<Code5>();
	
	public static final Properties	Dept   = new Properties();
	public static final Properties  MasterDept = new Properties();
	public static final List<Code5>	DeptList = new ArrayList<Code5>();
	
	public static final Properties	Status     = new Properties();
	public static final List<Code5>	StatusList = new ArrayList<Code5>();

	public static final Properties	StatusCause     = new Properties();
	public static final List<Code5>	StatusCauseList = new ArrayList<Code5>();

	public static final Properties	Identity     = new Properties();
	public static final List<Code5>	IdentityList = new ArrayList<Code5>();

	public static final Properties	Group     = new Properties();
	public static final List<Code5>	GroupList = new ArrayList<Code5>();

	public static final Properties	EmpStatus     = new Properties();
	public static final List<Code5>	EmpStatusList = new ArrayList<Code5>();

	public static final Properties	EmpStatusCause     = new Properties();
	public static final List<CodeEmpl>	EmpStatusCauseList = new ArrayList<CodeEmpl>();

	public static final Properties	EmpCategory     = new Properties();
	public static final List<Code5>	EmpCategoryList = new ArrayList<Code5>();

	public static final Properties		EmpUnit     = new Properties();
	public static final List<CodeEmpl>	EmpUnitList = new ArrayList<CodeEmpl>();

	public static final Properties		EmpRole     = new Properties();
	public static final List<CodeEmpl>	EmpRoleList = new ArrayList<CodeEmpl>();

	public static final Properties		EmpDegree     = new Properties();
	public static final List<CodeEmpl>	EmpDegreeList = new ArrayList<CodeEmpl>();

	public static final Properties		EmpTutor     = new Properties();
	public static final List<CodeEmpl>	EmpTutorList = new ArrayList<CodeEmpl>();

	public static final Properties		EmpDirector     = new Properties();
	public static final List<CodeEmpl>	EmpDirectorList = new ArrayList<CodeEmpl>();

	
	public static final Properties	CourseOpt     = new Properties();
	public static final List<Code5>	CourseOptList = new ArrayList<Code5>();
	
	public static final Properties	CourseType     = new Properties();
	public static final List<Code5>	CourseTypeList = new ArrayList<Code5>();
	
	public static final Properties SysParameters	= new Properties();

	public static final Properties	TimeOff     = new Properties();
	public static final List<Code5>	TimeOffList = new ArrayList<Code5>();
	
	public static final Properties	BonusPenalty     = new Properties();
	public static final List<Code5>	BonusPenaltyCodeList = new ArrayList<Code5>();
	
	public static final Properties Aborigine = new Properties();
	public static final List<Aborigine> AborigineList = new ArrayList<Aborigine>();
	
	public static final Properties FeeCode = new Properties();
	public static final List<FeeCode> FeeCodeList = new ArrayList<FeeCode>();
	
	public static final Properties LiteracyType = new Properties();
	public static final List<LiteracyType> LiteracyTypeList = new ArrayList<LiteracyType>();
	
	public static final Properties AmsAskLeave = new Properties();
	public static final List<AmsAskLeave> AmsAskLeaveList = new ArrayList<AmsAskLeave>();
	
	public static String SchoolNameZH = new String();
	
	public static String SchoolNameEN = new String();
	
}
