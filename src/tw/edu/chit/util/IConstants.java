package tw.edu.chit.util;

import org.apache.lucene.analysis.StopAnalyzer;

import com.google.gdata.client.calendar.CalendarService;

public interface IConstants {

	// Spring Manager Bean Names
	String ADMIN_MANAGER_BEAN_NAME = "adminManager";
	String COURSE_MANAGER_BEAN_NAME = "courseManager";
	String SCORE_MANAGER_BEAN_NAME = "scoreManager";
	String MEMBER_MANAGER_BEAN_NAME = "memberManager";
	String STUD_AFFAIR_MANAGER_BEAN_NAME = "studAffairManager";
	String SUMMER_MANAGER_BEAN_NAME = "summerManager";
	String AMS_MANAGER_BEAN_NAME = "amsManager";
	String MEMBER_DAO_BEAN_NAME = "memberDAO";
	
	String SHEET_PASSWORD = "CUST1234";

	// Parameter資料表下學期查詢之關鍵字
	String PARAMETER_NEXT_TERM = "NextTerm";

	// Parameter資料表本學期查詢之關鍵字
	String PARAMETER_SCHOOL_YEAR = "School_year";
	String PARAMETER_SCHOOL_TERM = "School_term";

	// code5內category各部制選課人數下限之關鍵字
	String CODE5_MIN_COUNT = "MinCount";

	// Struts Global Forward Name
	String ACTION_MAIN_NAME = "Main";
	String ACTION_SUCCESS_NAME = "success";
	String ACTION_FAILURE_NAME = "failure";
	
	String ACTION_MODULE_LIST_NAME = "ModuleList";

	// 學制選課人數下限
	Integer COLLEGE_MIN_COUNT = 20; // 大學
	Integer VOCATIONAL_SCHOOL_MIN_COUNT = 25; // 專科
	Integer RESEARCH_INSTITUTE_MIN_COUNT = 5; // 研究所
	Integer LITERACY_CLASS = 20; // 通識課程

	// 英文語系之Stop Words陣列
	String[] ENGLISH_STOP_WORD = StopAnalyzer.ENGLISH_STOP_WORDS;

	// 英文一, 英文二, 英語聽講練習, 英語寫作練習, 英文實習（一）（二）等不可以退選
	String[] ENGLISH_EXCLUDED_COURSES = { "T0351", "T0352", "T0360", "T0090",
			"T0941", "T0942" };

	// Mail Server Domain Name
	String MAILSERVER_DOMAIN_NAME_CC = "cc.cust.edu.tw";
	String MAILSERVER_DOMAIN_NAME_WWW = "www.cust.edu.tw";
	String MAILSERVER_DOMAIN_NAME_NO_AUTHEN = ""; // 無須帳密驗證

	// FTP Server Domain Name(IP Address)
	String FTP_SERVER_DOMAIN_NAME = ""; // 正式用
	String FTP_SERVER_DOMAIN_NAME_1 = ""; // 測試用
	String STUDENT_IMAGE_PATH = "/home1/image";
	
	String CIS_FTP_URL = "";
	String CIS_FTP_USERNAME = "";
	String CIS_FTP_PASSWORD = "";

	// 各部制代碼
	String DAY = "D";
	String NIGHT = "N";
	String HOLIDAY = "H";
	String HSIN_CHU = "S";
	String HSIN_CHU_CAMPUS = "2";

	String[] VALID_IMAGE_TYPE = { "jpg", "jpeg", "gif", "png" };

	// 單位Email
	String EMAIL_PERSONNEL = "person@www.cust.edu.tw"; // 人事室
	// String EMAIL_ELECTRIC_COMPUTER = "oscarwei168@cc.cust.edu.tw"; // 電算中心

	// JMS Server Configuration
	String JMS_SERVER_LOCAL = "tcp://:3035/";
	String JMS_SERVER = "tcp://:3035/";
	String JMS_CONNECTION_FACTORY_NAME = "ConnectionFactory";
	String JMS_QUEUE_NAME = "CISQueue";
	String JMS_QUEUE_SCORE_HISTORY_QUEUE = "CISScoreHistoryQueue";

	// 定義同步資料時工作內容
	String SYNC_DO_TYPE_INSERT = "I";
	String SYNC_DO_TYPE_UPDATE = "U";
	String SYNC_DO_TYPE_DELETE = "D";
	String SYNC_DO_TYPE_PASSWORD = "P"; // 變更密碼
	String SYNC_DO_TYPE_TRANS = "T"; // 學生轉退畢
	String SYNC_DO_TYPE_OTHER = "O"; // 處理非上述事項

	// 學年學期判斷用
	Integer THIS_YEAR = 0;
	Integer LAST_YEAR = -1;
	Integer NEXT_YEAR = 1;

	// 差勤刷卡應上應下範圍
	Integer AMS_SET_IN_RANGE = 20;
	Integer AMS_SET_OUT_RANGE = -10;

	// 教職員單位代碼
	String UNIT_LITERACY = "68";

	String[] DEPT_DAY = { "12", "15", "42", "1G", "CG", "FG", "64", "A4", "A2",
			"A3", "C2", "F2" };
	String[] DEPT_NIGHT = { "22", "52", "54", "82", "92", "8G", "B4", "B2",
			"B3", "D2", "H2", "DG", "HG" };
	String[] DEPT_HOLIDAY = { "32", "72", "K1", "X1" };

	String[] ASSOCIATE_CLASS = { "12", "15", "22", "32", "92", "A2", "B2" }; // 專科
	String[] MASTER_CLASS = { "1G", "8G", "CG", "DG", "FG", "HG" }; // 碩士
	String[] BACHELOR_2_CLASS = { "42", "52", "C2", "D2", "E2", "F2" }; // 二技

	String[] SCIENCE = { "1", "2", "3", "5", "6", "A", "B", "E", "H", "L", "M",
			"N", "P", "R" }; // 工學
	String[] BUSINESS = { "7", "8", "9", "C", "D", "I", "J", "K" }; // 商學
	String[] MANAGER = { "4" }; // 管理學
	String[] AQRICULTURE = { "F" }; // 農學

	// 軍訓課程不用輸入科目英文名稱資料
	String[] COURSE_ARMY = { "T0V60", "T0V50", "T0K90", "T0V70", "T0J60",
			"T0V80", "T0M10", "T0BA0", "T0Z05", "T0Z06", "T0Z07", "T0Z08",
			"T0Z09", "T0DK0", "T0BW0", "T0CA0", "T0BX0", "T0BY0", "T0BZ0" };

	// 通識課程與體育興趣選項,有必修與選修
	String[] COURSE_SPORT = { "T0T90", "T0U20", "T0860", "T0U10", "T0S20",
			"T0S10", "T0850", "T0870", "T0T70", "T0U00", "T0T80", "T0002" };

	String[] COURSE_ARMY_DENIED_1 = { "T0BA0", "T0Z05", "T0Z06", "T0Z07",
			"T0Z08", "T0Z09", "T0DK0" }; // 軍訓上學期不可重複選科目清單
	// 資料更新時要加入到COURSE_ARMY,作為科目English Name判斷之用
	String[] COURSE_ARMY_DENIED_2 = { "T0EA0", "T0CA0", "T0EB0", "T0EC0",
			"T0BY0", "T0EA0", "T0BY0" }; // 軍訓下學期不可重複選
	
	// 第二三階段時要拒絕選修(因為要繳場地費與保險費)
	String[] COURSE_SPORT_DENIED = { "T0851", "T0852", "T0853", "T0854",
			"T0S11", "T0S12", "T0S13", "T0S14", "T0CU1", "T0CU2", "T0EF1",
			"T0EF2" };

	// 不需上傳大綱簡介之科目代碼
	String[] COURSE_SYLLABUS_INTRO = { "TA620", "TB071", "TB072", "TA622",
			"TA621", "TCAM1", "TCAM2", "TM121", "TM122", "TN0G1", "TN0G2",
			"TG991", "TG992", "GE009", "GE008", "GB042", "GB041", "GB015",
			"GB016", "GB017", "GB018", "GC009", "GC010", "GC011", "GC012",
			"GA035", "TL100", "TL101", "TL102", "TL103", "TL104", "TL105",
			"TL106", "TL1G0", "TL1G1", "TL1G2", "TQ120", "TQ121", "TQ122",
			"TL0G0", "TL0G1", "TL0G2", "TA623" };

	enum COURSE_INTRO_SYLLABUS {
		TA620, TB071, TB072, TA622, TA621, TCAM1, TCAM2, TM121, TM122, TN0G1, 
		TN0G2, TG991, TG992, GE009, GE008, GB042, GB041, GB015, GB016, GB017, 
		GB018, GC009, GC010, GC011, GC012, GA035, TL100, TL101, TL102, TL103, 
		TL104, TL105, TL106, TL1G0, TL1G1, TL1G2, TQ120, TQ121, TQ122, TL0G0, 
		TL0G1, TL0G2
	};
	
	enum FEE_TYPE {
		TUITION, AGENCY, RELIEFTUITION, LOAN, VULNERABLE
	}
	
	String[] COLLEGE_ENGINEERING = { "1", "2", "3", "5", "6", "E" }; // 工程學院
	String[] COLLEGE_COMMERCE = { "4", "7", "8", "9", "D", "K" }; // 商管學院
	String[] COLLEGE_HEALTH = { "F", "H", "I" }; // 健康學院
	String[] COLLEGE_AIR = { "A", "B", "C", "J", "U" }; // 航空學院

	// 科目代碼
	String CSCODE_BEHAVIOR = "99999"; // 操行

	String MIDP_PLAIN_CONTENT_TYPE = "text/plain;charset=UTF-8";
	String MIDP_STREAM_CONTENT_TYPE = "application/octet-stream";
	String MIDP_CHARACTER_ENCODING = "UTF-8";

	CalendarService GOOGLE_SERVICES = new CalendarService("CUST-CalendarFeed");

	String GOOGLE_EMAIL_USERNAME = "cc@www.cust.edu.tw";
	String GOOGLE_EMAIL_PASSWORD = "chit1688";

	String GOOGLE_EMAIL_STD_USERNAME = "CIS@www.cust.edu.tw";
	String GOOGLE_EMAIL_STD_PASSWORD = "";

	String GOOGLE_METAFEED_URL_BASE = "http://www.google.com/calendar/feeds/";
	String GOOGLE_ALLCALENDARS_FEED_URL_SUFFIX = "/allcalendars/full";
	String GOOGLE_OWNCALENDARS_FEED_URL_SUFFIX = "/owncalendars/full";
	String GOOGLE_EVENT_FEED_URL_SUFFIX = "/private/full";

	String TimeOffPatchUsers = "A120862034,H120364585"; // 有權限幫老師(過期未點名)補點名紀錄的同仁

	// 出差勤系統
	String AMSDocAskLeave = "1"; // 請假
	String AMSDocOverTime = "2"; // 加班
	String AMSDocRepair = "3"; // 補登
	String AMSDocRevoke = "4"; // 請假撤銷
	
 	String AMS_NO = "NO"; // 無需刷卡代碼
	String AMS_AI = "ai"; // 補登代碼

	String AMSCodeDocProcessReject = "0"; // 未核准,退回
	String AMSCodeDocProcessOK = "1"; // 已核准
	String AMSCodeDocRevokeOK = "2"; // 已銷假
	String AMSCodeDocProcessForceOK = "3"; // 強迫核准
	String AMSCodeDocProcessAllRest = "8"; // 已補休
	String AMSCodeDocProcessHalfRest = "9"; // 已補休半天

	String AMSDocProcessReject = "未核准,退回"; // 未核准,退回
	String AMSDocProcessOK = "已核准"; // 已核准
	String AMSDocRevokeOK = "已銷假"; // 已銷假
	String AMSDocProcessForceOK = "!已核准"; // 強迫核准
	String AMSDocProcessAllRest = "已補休"; // 已補休
	String AMSDocProcessHalfRest = "已補休半天"; // 已補休半天
	String AMSDocProcessNone = "未處理"; // 未處理

	String[][] AMSDocProcess = new String[][] { { "0", AMSDocProcessReject },
			{ "1", AMSDocProcessOK }, { "2", AMSDocRevokeOK },
			{ "3", AMSDocProcessForceOK }, { "8", AMSDocProcessAllRest },
			{ "9", AMSDocProcessHalfRest } };

	// 學務系統管理者E-mail
	String SAFAdminEmail = "hsiao@cc.cust.edu.tw";
	// 台北日間學務E-mail收件者
	String[] SAF11Emails = {"mianneu@cc.cust.edu.tw", "huan@cc.cust.edu.tw"};
	// 台北進修部學務E-mail收件者
	String[] SAF12Emails = {"tung@cc.cust.edu.tw", "shw@cc.cust.edu.tw"};
	// 台北進院學務E-mail收件者
	String[] SAF13Emails = {"wang@cc.cust.edu.tw", "yunying@cc.cust.edu.tw"};
	// 新竹日間學務E-mail收件者
	String[] SAF21Emails = {"kenny55@cc.hc.cust.edu.tw", "ytchung@cc.hc.cust.edu.tw"};
	// 新竹進修學務E-mail收件者
	String[] SAF22Emails = {"kenny55@cc.hc.cust.edu.tw", "ytchung@cc.hc.cust.edu.tw"};
	// 新竹進院學務E-mail收件者
	String[] SAF23Emails = {"kenny55@cc.hc.cust.edu.tw", "ytchung@cc.hc.cust.edu.tw"};

	String STUDDocCodeProcessReject = "0"; // 未核准,退回
	String STUDDocCodeProcessOK = "1"; // 已核准
	String STUDDocCodeTutorOK = "2"; // 導師核准
	String STUDDocCodeDrillmasterOK = "3"; // 教官核准
	String STUDDocCodeStudaffairOK = "4"; // 學務組長核准
	String STUDDocCodeSAFChiefOK = "5"; // 學務長(主任)核准
	String STUDDocCodeTutorReject = "6"; // 導師未核准
	String STUDDocCodeDrillmasterReject = "7"; // 教官未核准
	String STUDDocCodeStudaffairReject = "8"; // 學務組長未核准
	String STUDDocCodeSAFChiefReject = "9"; // 學務長(主任)未核准
	String STUDDocCodeSendOut = "A"; // 送核中
	String STUDDocCodeNotSend = "N"; // 未送核
	String STUDDocCodeModify = "M"; // 修改
	String STUDDocCodeDeleted = "D"; // 已刪除

	String STUDDocProcessReject = "未核准,退回"; // 未核准,退回
	String STUDDocProcessOK = "已核准"; // 已核准
	String STUDDocTutorOK = "導師核准";
	String STUDDocDrillmasterOK = "系教官核准";
	String STUDDocStudaffairOK = "學務組長核准";
	String STUDDocSAFChiefOK = "學務長、主任核准";
	String STUDDocTutorReject = "導師未核准";
	String STUDDocDrillmasterReject = "系教官未核准"; // 教官未核准
	String STUDDocStudaffairReject = "學務組長未核准"; // 學務組長未核准
	String STUDDocSAFChiefReject = "學務長、主任未核准"; // 學務長(主任)未核准
	String STUDDocSendOut = "送核中"; // 送核中
	String STUDDocNotSend = "未送核"; // 未送核
	String STUDDocModify = "修改"; // 修改
	String STUDDocDeleted = "已刪除"; // 已刪除

	String STUDDocCodeChairmanOK = "B"; // 系主任核准(公假)
	String STUDDocCodeChairmanReject = "C"; // 系主任未核准(公假)
	String STUDDocChairmanOK = "系主任核准"; // 系主任核准(公假)
	String STUDDocChairmanReject = "系主任未核准"; // 系主任未核准(公假)

	String[][] STUDDocProcess = new String[][] { { "0", STUDDocProcessReject },
			{ "1", STUDDocProcessOK }, 			{ "2", STUDDocTutorOK },
			{ "3", STUDDocDrillmasterOK }, 		{ "4", STUDDocStudaffairOK },
			{ "5", STUDDocSAFChiefOK } , 		{ "6", STUDDocTutorReject },
			{ "7", STUDDocDrillmasterReject } , { "8", STUDDocStudaffairReject },
			{ "9", STUDDocSAFChiefReject } , 	{ "A", STUDDocSendOut },
			{ "N", STUDDocNotSend } , 			{ "M", STUDDocModify },
			{ "D", STUDDocDeleted },			{ "B", STUDDocChairmanOK } ,
			{ "C", STUDDocChairmanReject }};

	
	String photoDB = "jdbc:mysql://:3306/CISBLOB?useUnicode=true&amp;characterEncoding=utf-8";
	

}
