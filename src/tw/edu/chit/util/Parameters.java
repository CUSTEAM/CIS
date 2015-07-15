package tw.edu.chit.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class Parameters {

	static Logger LOG = Logger.getLogger(Parameters.class);

	public static Calendar PHASE_1_BEGIN;
	public static Calendar PHASE_1_DEADLINE;

	public static Calendar PHASE_2_BEGIN;
	public static Calendar PHASE_2_DEADLINE;

	public static Calendar PHASE_3_BEGIN;
	public static Calendar PHASE_3_DEADLINE;

	public static Calendar DAY_PHASE_1_BEGIN;
	public static Calendar DAY_PHASE_1_DEADLINE;
	public static Calendar NIGHT_PHASE_1_BEGIN;
	public static Calendar NIGHT_PHASE_1_DEADLINE;
	public static Calendar HSIN_CHU_PHASE_1_BEGIN;
	public static Calendar HSIN_CHU_PHASE_1_DEADLINE;
	public static Calendar HOLIDAY_PHASE_1_BEGIN;
	public static Calendar HOLIDAY_PHASE_1_DEADLINE;

	public static Calendar DAY_PHASE_2_BEGIN;
	public static Calendar DAY_PHASE_2_DEADLINE;
	public static Calendar NIGHT_PHASE_2_BEGIN;
	public static Calendar NIGHT_PHASE_2_DEADLINE;
	public static Calendar HSIN_CHU_PHASE_2_BEGIN;
	public static Calendar HSIN_CHU_PHASE_2_DEADLINE;
	public static Calendar HOLIDAY_PHASE_2_BEGIN;
	public static Calendar HOLIDAY_PHASE_2_DEADLINE;

	public static Calendar DAY_PHASE_3_BEGIN;
	public static Calendar DAY_PHASE_3_DEADLINE;
	public static Calendar NIGHT_PHASE_3_BEGIN;
	public static Calendar NIGHT_PHASE_3_DEADLINE;
	public static Calendar HSIN_CHU_PHASE_3_BEGIN;
	public static Calendar HSIN_CHU_PHASE_3_DEADLINE;
	public static Calendar HOLIDAY_PHASE_3_BEGIN;
	public static Calendar HOLIDAY_PHASE_3_DEADLINE;

	public static Calendar TEACH_INTRO_BEGIN;
	public static Calendar TEACH_INTRO_END;

	public static Calendar DAY_NEW_TERM_COURSES_BEGIN;
	public static Calendar DAY_NEW_TERM_COURSES_END;

	public static Calendar NIGHT_NEW_TERM_COURSES_BEGIN;
	public static Calendar NIGHT_NEW_TERM_COURSES_END;

	public static Calendar DAY_UP_TERM;
	public static Calendar DAY_DOWN_TERM;

	public static URL GOOGLE_EVENT_FEED_URL;

	static {

		// 96上學期起始日2007.08.01
		Calendar calUpTerm = Calendar.getInstance();
		calUpTerm.set(Calendar.YEAR, 2007);
		calUpTerm.set(Calendar.MONTH, Calendar.AUGUST);
		calUpTerm.set(Calendar.DAY_OF_MONTH, 1);
		DAY_UP_TERM = calUpTerm;

		// 96下學期起始日2008.02.01
		Calendar calDownTerm = Calendar.getInstance();
		calDownTerm.set(Calendar.YEAR, 2008);
		calDownTerm.set(Calendar.MONTH, Calendar.FEBRUARY);
		calDownTerm.set(Calendar.DAY_OF_MONTH, 1);
		DAY_DOWN_TERM = calDownTerm;

		// -----------第一階段選課----------------------
		// 2009.11.30 09:00:00:000
		Calendar calendar1B = Calendar.getInstance();
		calendar1B.set(Calendar.YEAR, 2009);
		calendar1B.set(Calendar.MONTH, Calendar.NOVEMBER);
		calendar1B.set(Calendar.DAY_OF_MONTH, 30);
		calendar1B.set(Calendar.HOUR_OF_DAY, 9);
		calendar1B.set(Calendar.MINUTE, 0);
		calendar1B.set(Calendar.SECOND, 0);
		calendar1B.set(Calendar.MILLISECOND, 0);
		PHASE_1_BEGIN = calendar1B;

		// 2009.12.11 23:59:59:999
		Calendar calendar1E = Calendar.getInstance();
		calendar1E.set(Calendar.YEAR, 2009);
		calendar1E.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar1E.set(Calendar.DAY_OF_MONTH, 11);
		calendar1E.set(Calendar.HOUR_OF_DAY, 23);
		calendar1E.set(Calendar.MINUTE, 59);
		calendar1E.set(Calendar.SECOND, 59);
		calendar1E.set(Calendar.MILLISECOND, 999);
		PHASE_1_DEADLINE = calendar1E;

		// -----------第二階段選課----------------------
		// 2009.12.21 09:00:00:000
		Calendar calendar2B = Calendar.getInstance();
		calendar2B.set(Calendar.YEAR, 2009);
		calendar2B.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar2B.set(Calendar.DAY_OF_MONTH, 21);
		calendar2B.set(Calendar.HOUR_OF_DAY, 9);
		calendar2B.set(Calendar.MINUTE, 0);
		calendar2B.set(Calendar.SECOND, 0);
		calendar2B.set(Calendar.MILLISECOND, 0);
		PHASE_2_BEGIN = calendar2B;

		// 2009.12.25 23:59:59:999
		Calendar calendar2E = Calendar.getInstance();
		calendar2E.set(Calendar.YEAR, 2009);
		calendar2E.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar2E.set(Calendar.DAY_OF_MONTH, 25);
		calendar2E.set(Calendar.HOUR_OF_DAY, 23);
		calendar2E.set(Calendar.MINUTE, 59);
		calendar2E.set(Calendar.SECOND, 59);
		calendar2E.set(Calendar.MILLISECOND, 999);
		PHASE_2_DEADLINE = calendar2E;

		// -----------第三階段選課----------------------
		// 2009.09.14 09:00:00:000
		Calendar calendar3B = Calendar.getInstance();
		calendar3B.set(Calendar.YEAR, 2009);
		calendar3B.set(Calendar.MONTH, Calendar.SEPTEMBER);
		calendar3B.set(Calendar.DAY_OF_MONTH, 11);
		calendar3B.set(Calendar.HOUR_OF_DAY, 9);
		calendar3B.set(Calendar.MINUTE, 0);
		calendar3B.set(Calendar.SECOND, 0);
		calendar3B.set(Calendar.MILLISECOND, 0);
		PHASE_3_BEGIN = calendar3B;

		// 2009.09.22 23:59:59:999
		Calendar calendar3E = Calendar.getInstance();
		calendar3E.set(Calendar.YEAR, 2009);
		calendar3E.set(Calendar.MONTH, Calendar.SEPTEMBER);
		calendar3E.set(Calendar.DAY_OF_MONTH, 22);
		calendar3E.set(Calendar.HOUR_OF_DAY, 23);
		calendar3E.set(Calendar.MINUTE, 59);
		calendar3E.set(Calendar.SECOND, 59);
		calendar3E.set(Calendar.MILLISECOND, 999);
		PHASE_3_DEADLINE = calendar3E;

		// -----------第一階段選課(OLD)----------------------*************
		// D:Day, N:Night, H: Hsin-Chu B:Begin, E:End
		// 定義日間部與新竹分部第1階段網路選課時間限制範圍
		// 2010.11.29 09:00:00:000
		Calendar calendar1DB = Calendar.getInstance();
		calendar1DB.set(Calendar.YEAR, 2011);
		calendar1DB.set(Calendar.MONTH, Calendar.MAY);
		calendar1DB.set(Calendar.DAY_OF_MONTH, 30);
		calendar1DB.set(Calendar.HOUR_OF_DAY, 9);
		calendar1DB.set(Calendar.MINUTE, 0);
		calendar1DB.set(Calendar.SECOND, 0);
		calendar1DB.set(Calendar.MILLISECOND, 0);
		DAY_PHASE_1_BEGIN = HSIN_CHU_PHASE_1_BEGIN = calendar1DB;

		// 2010.12.10 23:59:59:999
		Calendar calendar1DE = Calendar.getInstance();
		calendar1DE.set(Calendar.YEAR, 2011);
		calendar1DE.set(Calendar.MONTH, Calendar.JUNE);
		calendar1DE.set(Calendar.DAY_OF_MONTH, 10);
		calendar1DE.set(Calendar.HOUR_OF_DAY, 23);
		calendar1DE.set(Calendar.MINUTE, 59);
		calendar1DE.set(Calendar.SECOND, 59);
		calendar1DE.set(Calendar.MILLISECOND, 999);
		DAY_PHASE_1_DEADLINE = HSIN_CHU_PHASE_1_DEADLINE = calendar1DE;

		// 定義新竹第1階段網路選課時間限制範圍
		// 2009.11.30 09:00:00:000
		// Calendar calendar1HB = Calendar.getInstance();
		// calendar1HB.set(Calendar.YEAR, 2009);
		// calendar1HB.set(Calendar.MONTH, Calendar.NOVEMBER);
		// calendar1HB.set(Calendar.DAY_OF_MONTH, 30);
		// calendar1HB.set(Calendar.HOUR_OF_DAY, 9);
		// calendar1HB.set(Calendar.MINUTE, 0);
		// calendar1HB.set(Calendar.SECOND, 0);
		// calendar1HB.set(Calendar.MILLISECOND, 0);
		// HSIN_CHU_PHASE_1_BEGIN = calendar1HB;

		// 定義新竹第1階段網路選課時間限制範圍
		// 2009.12.11 23:59:59:999
		// Calendar calendar1HE = Calendar.getInstance();
		// calendar1HE.set(Calendar.YEAR, 2009);
		// calendar1HE.set(Calendar.MONTH, Calendar.DECEMBER);
		// calendar1HE.set(Calendar.DAY_OF_MONTH, 11);
		// calendar1HE.set(Calendar.HOUR_OF_DAY, 23);
		// calendar1HE.set(Calendar.MINUTE, 59);
		// calendar1HE.set(Calendar.SECOND, 59);
		// calendar1HE.set(Calendar.MILLISECOND, 999);
		// HSIN_CHU_PHASE_1_DEADLINE = calendar1HE;

		// 定義進修部第1階段網路選課時間限制範圍
		// 2010.12.06 15:00:00:000
		Calendar calendar1NB = Calendar.getInstance();
		calendar1NB.set(Calendar.YEAR, 2011);
		calendar1NB.set(Calendar.MONTH, Calendar.MAY);
		calendar1NB.set(Calendar.DAY_OF_MONTH, 30);
		calendar1NB.set(Calendar.HOUR_OF_DAY, 15);
		calendar1NB.set(Calendar.MINUTE, 0);
		calendar1NB.set(Calendar.SECOND, 0);
		calendar1NB.set(Calendar.MILLISECOND, 0);
		NIGHT_PHASE_1_BEGIN = HOLIDAY_PHASE_1_BEGIN = calendar1NB;

		// 2010.12.18 23:59:59:999
		Calendar calendar1NE = Calendar.getInstance();
		calendar1NE.set(Calendar.YEAR, 2011);
		calendar1NE.set(Calendar.MONTH, Calendar.JUNE);
		calendar1NE.set(Calendar.DAY_OF_MONTH, 10);
		calendar1NE.set(Calendar.HOUR_OF_DAY, 23);
		calendar1NE.set(Calendar.MINUTE, 59);
		calendar1NE.set(Calendar.SECOND, 59);
		calendar1NE.set(Calendar.MILLISECOND, 999);
		NIGHT_PHASE_1_DEADLINE = HOLIDAY_PHASE_1_DEADLINE = calendar1NE;

		// 定義學院第1階段網路選課時間限制範圍
		// 2010.05.31 09:00:00:000
		// Calendar calendar1HB = Calendar.getInstance();
		// calendar1HB.set(Calendar.YEAR, 2010);
		// calendar1HB.set(Calendar.MONTH, Calendar.MAY);
		// calendar1HB.set(Calendar.DAY_OF_MONTH, 31);
		// calendar1HB.set(Calendar.HOUR_OF_DAY, 9);
		// calendar1HB.set(Calendar.MINUTE, 0);
		// calendar1HB.set(Calendar.SECOND, 0);
		// calendar1HB.set(Calendar.MILLISECOND, 0);
		// HOLIDAY_PHASE_1_BEGIN = calendar1HB;
		//
		// // 2010.06.11 23:59:59:999
		// Calendar calendar1HE = Calendar.getInstance();
		// calendar1HE.set(Calendar.YEAR, 2010);
		// calendar1HE.set(Calendar.MONTH, Calendar.JUNE);
		// calendar1HE.set(Calendar.DAY_OF_MONTH, 11);
		// calendar1HE.set(Calendar.HOUR_OF_DAY, 23);
		// calendar1HE.set(Calendar.MINUTE, 59);
		// calendar1HE.set(Calendar.SECOND, 59);
		// calendar1HE.set(Calendar.MILLISECOND, 999);
		// HOLIDAY_PHASE_1_DEADLINE = calendar1HE;

		// -----------第二階段選課----------------------
		// 定義日間部與新竹分部第2階段網路選課時間限制範圍
		// 2010.12.20 09:00:00:000
		Calendar calendar2DB = Calendar.getInstance();
		calendar2DB.set(Calendar.YEAR, 2010);
		calendar2DB.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar2DB.set(Calendar.DAY_OF_MONTH, 20);
		calendar2DB.set(Calendar.HOUR_OF_DAY, 9);
		calendar2DB.set(Calendar.MINUTE, 0);
		calendar2DB.set(Calendar.SECOND, 0);
		calendar2DB.set(Calendar.MILLISECOND, 0);
		// DAY_PHASE_2_BEGIN = calendar2DB;
		DAY_PHASE_2_BEGIN = HSIN_CHU_PHASE_2_BEGIN = calendar2DB;

		// 2010.12.24 23:59:59:999
		Calendar calendar2DE = Calendar.getInstance();
		calendar2DE.set(Calendar.YEAR, 2010);
		calendar2DE.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar2DE.set(Calendar.DAY_OF_MONTH, 24);
		calendar2DE.set(Calendar.HOUR_OF_DAY, 23);
		calendar2DE.set(Calendar.MINUTE, 59);
		calendar2DE.set(Calendar.SECOND, 59);
		calendar2DE.set(Calendar.MILLISECOND, 999);
		DAY_PHASE_2_DEADLINE = HSIN_CHU_PHASE_2_DEADLINE = calendar2DE;

		// 定義新竹第2階段網路選課時間限制範圍
		// 2008.05.23 09:00:00:000
		// Calendar calendar2HB = Calendar.getInstance();
		// calendar2HB.set(Calendar.YEAR, 2008);
		// calendar2HB.set(Calendar.MONTH, Calendar.MAY);
		// calendar2HB.set(Calendar.DAY_OF_MONTH, 23);
		// calendar2HB.set(Calendar.HOUR_OF_DAY, 9);
		// calendar2HB.set(Calendar.MINUTE, 0);
		// calendar2HB.set(Calendar.SECOND, 0);
		// calendar2HB.set(Calendar.MILLISECOND, 0);
		// HSIN_CHU_PHASE_2_BEGIN = calendar2HB;

		// 定義新竹第2階段網路選課時間限制範圍
		// 2008.05.23 23:59:59:999
		// Calendar calendar2HE = Calendar.getInstance();
		// calendar2HE.set(Calendar.YEAR, 2008);
		// calendar2HE.set(Calendar.MONTH, Calendar.MAY);
		// calendar2HE.set(Calendar.DAY_OF_MONTH, 23);
		// calendar2HE.set(Calendar.HOUR_OF_DAY, 23);
		// calendar2HE.set(Calendar.MINUTE, 59);
		// calendar2HE.set(Calendar.SECOND, 59);
		// calendar2HE.set(Calendar.MILLISECOND, 999);
		// HSIN_CHU_PHASE_2_DEADLINE = calendar2HE;
		// calendar2DE.set(Calendar.DAY_OF_MONTH, 9);
		// HSIN_CHU_PHASE_2_DEADLINE = calendar2DE;

		// 定義夜間部第2階段網路選課時間限制範圍
		// 2010.12.27 15:00:00:000
		Calendar calendar2NB = Calendar.getInstance();
		calendar2NB.set(Calendar.YEAR, 2010);
		calendar2NB.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar2NB.set(Calendar.DAY_OF_MONTH, 27);
		calendar2NB.set(Calendar.HOUR_OF_DAY, 15);
		calendar2NB.set(Calendar.MINUTE, 0);
		calendar2NB.set(Calendar.SECOND, 0);
		calendar2NB.set(Calendar.MILLISECOND, 0);
		NIGHT_PHASE_2_BEGIN = HOLIDAY_PHASE_2_BEGIN = calendar2NB;

		// 2011.01.01 23:59:59:999
		Calendar calendar2NE = Calendar.getInstance();
		calendar2NE.set(Calendar.YEAR, 2011);
		calendar2NE.set(Calendar.MONTH, Calendar.JANUARY);
		calendar2NE.set(Calendar.DAY_OF_MONTH, 1);
		calendar2NE.set(Calendar.HOUR_OF_DAY, 23);
		calendar2NE.set(Calendar.MINUTE, 59);
		calendar2NE.set(Calendar.SECOND, 59);
		calendar2NE.set(Calendar.MILLISECOND, 999);
		NIGHT_PHASE_2_DEADLINE = HOLIDAY_PHASE_2_DEADLINE = calendar2NE;

		// -----------第三階段選課----------------------
		// 定義日間部與新竹分部第3階段網路選課時間限制範圍
		// 學院無第三階段選課
		// 2011.02.21 09:00:00:000
		Calendar calendar3DB = Calendar.getInstance();
		calendar3DB.set(Calendar.YEAR, 2011);
		calendar3DB.set(Calendar.MONTH, Calendar.SEPTEMBER);//SEPTEMBER
		calendar3DB.set(Calendar.DAY_OF_MONTH, 1);
		calendar3DB.set(Calendar.HOUR_OF_DAY, 9);
		calendar3DB.set(Calendar.MINUTE, 0);
		calendar3DB.set(Calendar.SECOND, 0);
		calendar3DB.set(Calendar.MILLISECOND, 0);
		DAY_PHASE_3_BEGIN = HSIN_CHU_PHASE_3_BEGIN = calendar3DB;

		// 2011.03.01 23:59:59:000
		Calendar calendar3DHE = Calendar.getInstance();
		calendar3DHE.set(Calendar.YEAR, 2011);
		calendar3DHE.set(Calendar.MONTH, Calendar.SEPTEMBER);
		calendar3DHE.set(Calendar.DAY_OF_MONTH, 13);
		calendar3DHE.set(Calendar.HOUR_OF_DAY, 23);
		calendar3DHE.set(Calendar.MINUTE, 59);
		calendar3DHE.set(Calendar.SECOND, 59);
		calendar3DHE.set(Calendar.MILLISECOND, 000);
		DAY_PHASE_3_DEADLINE = HSIN_CHU_PHASE_3_DEADLINE = calendar3DHE;

		// 定義第3階段夜間部網路選課時間限制範圍
		// 2011.02.21 15:00:00:000
		Calendar calendar3NB = Calendar.getInstance();
		calendar3NB.set(Calendar.YEAR, 2011);
		calendar3NB.set(Calendar.MONTH, Calendar.SEPTEMBER);
		calendar3NB.set(Calendar.DAY_OF_MONTH, 5);
		calendar3NB.set(Calendar.HOUR_OF_DAY, 15);
		calendar3NB.set(Calendar.MINUTE, 0);
		calendar3NB.set(Calendar.SECOND, 0);
		calendar3NB.set(Calendar.MILLISECOND, 0);
		NIGHT_PHASE_3_BEGIN = calendar3NB;

		// 2011.03.01 23:59:59:000
		Calendar calendar3NE = Calendar.getInstance();
		calendar3NE.set(Calendar.YEAR, 2011);
		calendar3NE.set(Calendar.MONTH, Calendar.SEPTEMBER);
		calendar3NE.set(Calendar.DAY_OF_MONTH, 13);
		calendar3NE.set(Calendar.HOUR_OF_DAY, 23);
		calendar3NE.set(Calendar.MINUTE, 59);
		calendar3NE.set(Calendar.SECOND, 59);
		calendar3NE.set(Calendar.MILLISECOND, 000);
		NIGHT_PHASE_3_DEADLINE = calendar3NE;

		// 教師輸入中英文簡介與大綱
		// 2007.10.29 00:00:00:000
		Calendar calendarTIB = Calendar.getInstance();
		calendarTIB.set(Calendar.YEAR, 2007);
		calendarTIB.set(Calendar.MONTH, Calendar.OCTOBER);
		calendarTIB.set(Calendar.DAY_OF_MONTH, 29);
		calendarTIB.set(Calendar.HOUR_OF_DAY, 0);
		calendarTIB.set(Calendar.MINUTE, 0);
		calendarTIB.set(Calendar.SECOND, 0);
		calendarTIB.set(Calendar.MILLISECOND, 0);
		TEACH_INTRO_BEGIN = calendarTIB;

		// 2099.03.16 23:59:59:999
		// Forever
		Calendar calendarTIE = Calendar.getInstance();
		calendarTIE.set(Calendar.YEAR, 2099);
		calendarTIE.set(Calendar.MONTH, Calendar.MARCH);
		calendarTIE.set(Calendar.DAY_OF_MONTH, 16);
		calendarTIE.set(Calendar.HOUR_OF_DAY, 23);
		calendarTIE.set(Calendar.MINUTE, 59);
		calendarTIE.set(Calendar.SECOND, 59);
		calendarTIE.set(Calendar.MILLISECOND, 999);
		TEACH_INTRO_END = calendarTIE;

		// 日間部與新竹-我的新學期課表(New Term Course)
		// 新竹第二階段結束後第1天
		Calendar calendarNTCB = (Calendar) DAY_PHASE_2_DEADLINE.clone();
		calendarNTCB.roll(Calendar.DAY_OF_MONTH, 1);
		calendarNTCB.set(Calendar.HOUR_OF_DAY, 0);
		calendarNTCB.set(Calendar.MINUTE, 0);
		calendarNTCB.set(Calendar.SECOND, 0);
		calendarNTCB.set(Calendar.MILLISECOND, 0);
		DAY_NEW_TERM_COURSES_BEGIN = calendarNTCB;

		// 夜間部-我的新學期課表(New Term Course)
		// 夜間部第二階段結束後第1天
		Calendar calendarHNTCB = (Calendar) DAY_PHASE_2_DEADLINE.clone();
		calendarHNTCB.roll(Calendar.DAY_OF_MONTH, 1);
		calendarHNTCB.set(Calendar.HOUR_OF_DAY, 0);
		calendarHNTCB.set(Calendar.MINUTE, 0);
		calendarHNTCB.set(Calendar.SECOND, 0);
		calendarHNTCB.set(Calendar.MILLISECOND, 0);
		NIGHT_NEW_TERM_COURSES_BEGIN = calendarHNTCB;

		// 2011.02.15 23:59:59:9999
		Calendar calendarNTCE = Calendar.getInstance();
		calendarNTCE.set(Calendar.YEAR, 2011);
		calendarNTCE.set(Calendar.MONTH, Calendar.FEBRUARY);
		calendarNTCE.set(Calendar.DAY_OF_MONTH, 15);
		calendarNTCE.set(Calendar.HOUR_OF_DAY, 23);
		calendarNTCE.set(Calendar.MINUTE, 59);
		calendarNTCE.set(Calendar.SECOND, 59);
		calendarNTCE.set(Calendar.MILLISECOND, 999);
		DAY_NEW_TERM_COURSES_END = NIGHT_NEW_TERM_COURSES_END = calendarNTCE;

		try {
			GOOGLE_EVENT_FEED_URL = new URL(IConstants.GOOGLE_METAFEED_URL_BASE
					+ IConstants.GOOGLE_EMAIL_USERNAME
					+ IConstants.GOOGLE_EVENT_FEED_URL_SUFFIX);
		} catch (MalformedURLException me) {
			LOG.error(me.getMessage(), me);
		}
	}

}
