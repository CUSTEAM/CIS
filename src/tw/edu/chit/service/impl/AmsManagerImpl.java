package tw.edu.chit.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.dao.AmsDAO;
import tw.edu.chit.dao.AmsJdbcDAO;
import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.AmsAskLeave;
import tw.edu.chit.model.AmsDocApply;
import tw.edu.chit.model.AmsHoliday;
import tw.edu.chit.model.AmsMeeting;
import tw.edu.chit.model.AmsMeetingAskLeave;
import tw.edu.chit.model.AmsMeetingData;
import tw.edu.chit.model.AmsPersonalVacation;
import tw.edu.chit.model.AmsRevokedDoc;
import tw.edu.chit.model.AmsShiftGroup;
import tw.edu.chit.model.AmsShiftTime;
import tw.edu.chit.model.AmsWorkdate;
import tw.edu.chit.model.AmsWorkdateData;
import tw.edu.chit.model.AmsWorkdateInfo;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.AmsManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class AmsManagerImpl extends BaseManager implements AmsManager {

	private AmsDAO dao;
	private AmsJdbcDAO jdbcDao;
	private MemberDAO memberdao;

	public void setAmsDAO(AmsDAO dao) {
		this.dao = dao;
	}

	public void setJdbcDAO(AmsJdbcDAO jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public void setMemberDAO(MemberDAO dao) {
		this.memberdao = dao;
	}

	@SuppressWarnings("unchecked")
	public Object findObject(Class clazz, Serializable id) {
		return dao.getObject(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List findAnyThing(String sql) {
		return jdbcDao.findAnyThing(sql);
	}

	/**
	 * 
	 * @param entity
	 * @param expression
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List findSQLWithCriteria(Class entity,
			SimpleExpression... expression) throws DataAccessException {
		return dao.getSQLWithCriteria(entity, expression);
	}

	/**
	 * 
	 * @param entity
	 * @param example
	 * @param criterion
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List findSQLWithCriteria(Class entity, Example example,
			Criterion... criterion) throws DataAccessException {
		return dao.getSQLWithCriteria(entity, example, criterion);
	}

	@SuppressWarnings("unchecked")
	public List<AmsDocApply> getDocApplyByIdno(String idno, String docType) {
		List<AmsDocApply> docList = new ArrayList<AmsDocApply>();
		String hql = "From AmsDocApply Where idno=? And docType=? Order By startDate Desc";
		docList = dao.submitQueryP(hql, new Object[] { idno, docType });
		return docList;
	}

	public List<AmsDocApply> delDocApply(List<AmsDocApply> selDocs) {
		List<AmsDocApply> undelDocs = new ArrayList<AmsDocApply>();
		for (AmsDocApply doc : selDocs) {
			log.debug("====>Remove Docs:" + doc.getOid());
			if (doc.getStatus() == null) { // 已由人事單位處理的單據不能被刪除
				try {
					dao.removeDoc(doc);
					if (doc.getDocType().equals(IConstants.AMSDocRevoke)) {
						AmsRevokedDoc revoke = this.getRevokedDoc(doc.getOid());
						dao.removeObject(revoke);
					}
				} catch (Exception e) {
					e.printStackTrace();
					undelDocs.add(doc);
				}
			}
		}
		return undelDocs;
	}

	/**
	 * 查詢AmsAskLeave物件(假別)
	 * 
	 * @param aal AmsAskLeave Object
	 * @return List of AmsAskLeave Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsAskLeave> findAmsAskLeaveBy(AmsAskLeave aal)
			throws DataAccessException {
		return dao.getAmsAskLeaveBy(aal);
	}

	/**
	 * 查詢AmsDocApply物件(請假)
	 * 
	 * @param ada AmsDocApply Object
	 * @return List of AmsDocApply Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsDocApply> findAmsDocApplyBy(AmsDocApply ada)
			throws DataAccessException {
		return dao.getAmsDocApplyBy(ada);
	}

	/**
	 * 
	 * @param ads
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 */
	public List<AmsDocApply> findAmsDocApplyBy(Date from, Date to)
			throws DataAccessException {
		return dao.getAmsDocApplyBy(from, to);
	}

	/**
	 * 
	 * @param ads
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 */
	public List<AmsDocApply> findAmsDocApplyBy(AmsDocApply ads, Date from,
			Date to) throws DataAccessException {
		return dao.getAmsDocApplyBy(ads, from, to);
	}

	/**
	 * 
	 * @param ads
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 */
	public List<AmsMeetingAskLeave> findAmsMeetingAskLeaveBy(Date from, Date to)
			throws DataAccessException {
		return dao.getAmsMeetingAskLeaveBy(from, to);
	}

	public List<AmsMeetingAskLeave> findAmsMeetingAskLeavesBy(
			AmsMeetingAskLeave amal) throws DataAccessException {
		return dao.getAmsMeetingAskLeavesBy(amal);
	}

	/**
	 * 查詢AmsDocApply物件(某日請假)
	 * 
	 * @param ads AmsDocApply Object
	 * @param d Some Date
	 * @return List of AmsDocApply Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsDocApply> findAmsDocApplyBy(AmsDocApply ads, Date d)
			throws DataAccessException {
		return dao.getAmsDocApplyBy(ads, d);
	}

	/**
	 * 查詢AmsHoliday物件(假日)
	 * 
	 * @param ah AmsHoliday Object
	 * @return List of AmsHoliday Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsHoliday> findAmsHolidayBy(AmsHoliday ah)
			throws DataAccessException {
		return dao.getAmsHolidayBy(ah);
	}

	/**
	 * 查詢AmsShiftGroup物件
	 * 
	 * @param asg AmsShiftGroup Object
	 * @return List of AmsShiftGroup Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsShiftGroup> findAmsShiftGroupBy(AmsShiftGroup asg)
			throws DataAccessException {
		return dao.getAmsShiftGroupBy(asg);
	}

	/**
	 * 查詢AmsShiftTime物件
	 * 
	 * @param ast AmsShiftTime Object
	 * @return List of AmsShiftTime Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsShiftTime> findAmsShiftTimeBy(AmsShiftTime ast)
			throws DataAccessException {
		return dao.getAmsShiftTimeBy(ast);
	}

	/**
	 * 查詢AmsPersonalVacation物件
	 * 
	 * @param apv AmsPersonalVacation Object
	 * @return List of AmsPersonalVacation Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsPersonalVacation> findAmsPersonalVacationBy(
			AmsPersonalVacation apv) throws DataAccessException {
		return dao.getAmsPersonalVacationBy(apv);
	}

	/**
	 * 查詢AmsMeeting物件
	 * 
	 * @param amsMeeting
	 * @return
	 * @throws DataAccessException
	 */
	public List<AmsMeeting> findAmsMeetingBy(AmsMeeting amsMeeting)
			throws DataAccessException {
		return dao.getAmsMeetingBy(amsMeeting);
	}

	public List<AmsMeeting> findAmsMeetingsBy(Date from, Date to)
			throws DataAccessException {
		return dao.getAmsMeetingsBy(from, to);
	}

	public List<AmsWorkdateInfo> findAmsWorkdateDataBy(Empl empl,
			String schoolYear) throws DataAccessException, SQLException {
		AmsWorkdateData aw = new AmsWorkdateData();
		aw.setIdno(empl.getIdno());
		return dao.getAmsWorkdateDataBy(aw, schoolYear, null, null);
	}

	/**
	 * 
	 * @param aw
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	public List<DynaBean> findAmsWorkdateBy(AmsWorkdateData aw, Date from,
			Date to) throws DataAccessException, SQLException, Exception {
		return dao.getAmsWorkdateBy(aw, from, to);
	}

	/**
	 * 查詢AmsWorkdateData物件
	 * 
	 * @param empl Empl Object
	 * @param aw AmsWorkdateData Object
	 * @param from Search from
	 * @param to Search to
	 * @param schoolYear Just for Vacation
	 * @return Map Objects
	 * @throws DataAccessException If any exception occurred
	 * @throws SQLException If any create statement command exception occurred
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> findAmsWorkdateDataBy(Empl empl,
			AmsWorkdateData aw, Date from, Date to, Integer schoolYear)
			throws DataAccessException, SQLException {

		List<AmsWorkdateInfo> amsWorkdateDatas = dao.getAmsWorkdateDataBy(aw,
				String.valueOf(schoolYear), from, to);

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd kk:mm", Locale.TAIWAN);
		// DateFormat dt = new SimpleDateFormat("kk:mm", Locale.TAIWAN);

		DecimalFormat def = new DecimalFormat(",##0.0");
		Map<String, Object> ret = new HashMap<String, Object>();
		Time t = null;
		Time t1 = null;
		float totalScores = 0.0f, minsToHours = 0.0f, minutes = 0.0f;
		float totalDelayButNoRecordHours = 0.0f, totalEarlyButNoRecordHours = 0.0f;
		int businessCounts = 0, sickCounts = 0, womenSickCounts = 0;
		float businessTotalHours = 0.0f, sickTotalHours = 0.0f, womenSickTotalHours = 0.0f;
		int businessTotalMins = 0, sickTotalMins = 0, womenSickTotalMins = 0, leave3TotalMins = 0;
		int leave3 = 0, leave4 = 0, leave5 = 0, leave6 = 0, leave7 = 0;
		int leave9 = 0, leave10 = 0, leave12 = 0, leave13 = 0;
		int leave3Hours = 0, leave4Hours = 0, leave5Hours = 0, leave6Hours = 0;
		int leave7Hours = 0, leave9Hours = 0, leave10Hours = 0;
		int leave12Hours = 0, leave13Hours = 0, vacationNoStatus = 0;
		int delayCounts = 0, earlyCounts = 0, noRecordCounts = 0, workOnHolidayCounts = 0;

		int betweenCounts=0;  // 超過應上20分內視為遲到計數 -- 溫瑞烘		
		
		int meetingBusinessCounts = 0, meetingSickCounts = 0, meetingWomenSickCounts = 0;
		int meetingBusinessTotalHours = 0, meetingSickTotalHours = 0, meetingWomenSickTotalHours = 0;
		int meetingHours = 0, docRepair = 0;
		final long HOUR = 60 * 60 * 1000;
		final long HALF_HOUR = 30 * 60 * 1000;
		long distances = 0, hours = 0;
		
		// 檢查是否有實上或實下
		// 2010.04.13 秀旻說只要有一次實上或實下未刷,即算曠職
		boolean noRealIn = false, noRealOut = false;
		boolean hasRepair = false; // 有無補登資料?
		boolean isAfternoon = false, inStartEndRange = false;
		boolean isEndDelay = false; // 20120628 Add BY yichen 由請假截止時間看是否遲到
		boolean isStartEarly = false; // 20120628 Add BY yichen 由請假開始時間看是否早退
		boolean isNeedAddScore = true;
		boolean isNO = IConstants.AMS_NO.equals(empl == null ? "" : StringUtils
				.defaultIfEmpty(empl.getWorkShift(), ""));
		
		String repairStart = "", repairEnd = "";
		Calendar cal = null, cloneCal = null, cloneCal1 = null, c = null;

		List<AmsHoliday> holidayOrWorkings = null;
		List<AmsDocApply> docApplys = null;
		List<AmsMeeting> meetings = null;
		List<AmsMeetingAskLeave> meetingAskLeaves = null;
		List<AmsWorkdateData> list = new LinkedList<AmsWorkdateData>();
		// AmsWorkdateInfo info = null;
		AmsHoliday holidayOrWorking = null;
		AmsMeeting meeting = null;
		AmsMeetingAskLeave meetingAskLeave = new AmsMeetingAskLeave();
		meetingAskLeave.setUserIdno(empl.getIdno().toUpperCase().trim());
		
		AmsDocApply amsDocApply = new AmsDocApply();
		amsDocApply.setIdno(empl.getIdno());
		
		// amsDocApply.setDocType(IConstants.AMSDocRepair); // 補登
		// List<AmsDocApply> amsDocApplys4AMSDocRepair = findAmsDocApplyBy(
		// amsDocApply, from, to);

		amsDocApply.setDocType(IConstants.AMSDocOverTime); // 加班
		Example example = Example.create(amsDocApply).ignoreCase().enableLike(
				MatchMode.ANYWHERE);
		List<AmsDocApply> amsDocApplys4AMSDocOverTime = (List<AmsDocApply>) findSQLWithCriteria(
				AmsDocApply.class, example, Restrictions.in("status",
						new Object[] { IConstants.AMSCodeDocProcessOK,
								IConstants.AMSCodeDocProcessAllRest,
								IConstants.AMSCodeDocProcessHalfRest }),
				Restrictions.between("startDate", from, to));

		// 查詢年假與特休假
		AmsPersonalVacation apv = new AmsPersonalVacation();
		apv.setIdno(empl.getIdno());
		// apv.setVyear(schoolYear);
		List<AmsPersonalVacation> apvs = dao.getAmsPersonalVacationBy(apv);

		Calendar workDate = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		now.set(Calendar.MILLISECOND, 999);
		AmsWorkdateInfo awi1=null;
		for (AmsWorkdateInfo awi : amsWorkdateDatas) {
            awi1=awi;
			workDate.setTime(awi.getWdate());
			holidayOrWorking = new AmsHoliday();
			holidayOrWorking.setDate(awi.getWdate());
			holidayOrWorkings = dao.getAmsHolidayBy(holidayOrWorking);

			amsDocApply = new AmsDocApply();
			amsDocApply.setIdno(empl.getIdno());

			hasRepair = false; // 有無補登紀錄
			noRealIn = false;
			noRealOut = false;
			docApplys = dao.getAmsDocApplyBy(amsDocApply, awi.getWdate());
			
			if (IConstants.AMS_AI.equalsIgnoreCase(awi.getType()))
				docRepair++; // 有補登紀錄

			meeting = new AmsMeeting();
			meeting.setMeetingDate(awi.getWdate());
			meetings = findAmsMeetingBy(meeting);
			System.out.println(awi.getWdate());
			if (!meetings.isEmpty()) {
				awi.setMeeting("1");
				for (AmsMeeting am : meetings) {
					awi.setMeetingInfo(am.getName().trim() + "<br/>");
					// 計算重要集會扣分
					meetingHours = am.getEndNode() - am.getStartNode() + 1;
					meetingAskLeave.setMeetingOid(am.getOid());
					meetingAskLeaves = findAmsMeetingAskLeavesBy(meetingAskLeave);
					for (AmsMeetingAskLeave amal : meetingAskLeaves) {
						if (StringUtils.isNotBlank(amal.getAskleaveId())) {
							switch (Integer.parseInt(amal.getAskleaveId())) {
								case 1: // 事假
									meetingBusinessCounts++;
									meetingBusinessTotalHours += meetingHours;
									break;

								case 2: // 病假
									meetingSickCounts++;
									meetingSickTotalHours += meetingHours;
									break;

								case 3: // 公假
									leave3++;
									leave3Hours += meetingHours;
									break;

								case 4: // 婚假
									leave4++;
									leave4Hours += meetingHours;
									break;

								case 5: // 產假
									leave5++;
									leave5Hours += meetingHours;
									break;

								case 6: // 喪假
									leave6++;
									leave6Hours += meetingHours;
									break;

								case 7: // 年假
									leave7++;
									leave7Hours += meetingHours;
									break;

								case 9: // 補休假
									leave9++;
									leave9Hours += meetingHours;
									break;

								case 10: // 陪產假
									leave10++;
									leave10Hours += meetingHours;
									break;

								case 12: // 特休
									leave12++;
									leave12Hours += meetingHours;
									break;

								case 13: // 產前假
									leave13++;
									leave13Hours += meetingHours;
									break;

								case 14: // 生理假
									meetingWomenSickCounts++;
									meetingWomenSickTotalHours += meetingHours;

								default:
									break;
							}
						}
					}
				}
			}

			// 是否有補登資料?
			// 補登作業要取消
//			if (!docApplys.isEmpty()) {
//				for (AmsDocApply ada : docApplys) {
//					if (IConstants.AMSDocRepair.equalsIgnoreCase(ada
//							.getDocType())) {
//						hasRepair = true;
//						repairStart = ada.getStartDate() == null ? "" : dt
//								.format(ada.getStartDate());
//						repairEnd = ada.getEndDate() == null ? "" : dt
//								.format(ada.getEndDate());
//					}
//				}
//			}
			System.out.println(awi.getWdate());
			System.out.println(awi.getWdate());
			if ("w".equalsIgnoreCase(awi.getDateType())) {
				
				if (awi.getRealIn() == null) {
					if (docApplys.isEmpty() && workDate.before(now) && !isNO) {
						awi.setInDelay("無上班差勤紀錄");
						awi.setOnDelay("1");
						// noWorkOnRecord = true;
						noRealIn = true;
					} else if (hasRepair) {
						// awi.setInDelay("補登");
						if (StringUtils.isBlank(repairStart) && !isNO) {
							awi.setInDelay("無上班差勤紀錄");
							awi.setOnDelay("1");
							// noWorkOnRecord = true;
							noRealIn = true;
						} else {
							awi.setRealInInfo(repairStart);
							awi.setWorkHard("1");
						}
					}
				} else {
					
					t = (Time) awi.getSetIn().clone();
					t1 = (Time) awi1.getSetIn().clone();
					t1.setTime(t1.getTime()	+ (59 * 1000)); // 只要08:01就算不扣分遲到
					
					t.setTime(t.getTime()
							+ (IConstants.AMS_SET_IN_RANGE * 60 * 1000)
							+ (59 * 1000)); // 要以08:21才算遲到
/* 101-1 超過應上時間且20分鐘內刷卡者視為遲到 但不扣分 , 以下判斷 20分鐘內刷卡者 計數加 1 溫瑞烘 */					
					if (awi.getRealIn().after(t1) && awi.getRealIn().before(t)) {
						if (docApplys.isEmpty()) {
							betweenCounts++;
							awi.setInDelay("刷卡逾時");
							awi.setOnDelay("1");
						}
					}
				
					
					if (awi.getRealIn().after(t)) {
						if (docApplys.isEmpty()) {
							// 超過時間且沒有任何假單時...
							awi.setInDelay(Toolket.getTimeDistance(t, awi
									.getRealIn()));
							awi.setOnDelay("1");
							// delayCounts++;
							distances = awi.getRealIn().getTime()
									- awi.getSetIn().getTime();
							if ((distances / HOUR) > 1
									|| ((distances / HOUR) == 1 && (distances % HOUR) != 0)) {
								// 遲到超過1小時要算曠職時數
								hours = distances / HOUR;
								minutes = (distances % HOUR) / HALF_HOUR;
								totalDelayButNoRecordHours += minutes > 1 ? hours + 1
										: hours + 0.5;
							} else
								delayCounts++; // 遲到未超過1小時就算遲到
						} else {
							// 案例:A224433460 02.03請假早上半天,中午才刷卡
							cal = Calendar.getInstance();
							cal.setTime(awi.getWdate());
							cloneCal = (Calendar) cal.clone();
							cal.set(Calendar.HOUR_OF_DAY, 12);
							cal.set(Calendar.MINUTE, 0);
							cal.set(Calendar.SECOND, 0);
							cal.set(Calendar.MILLISECOND, 0);
							
							/* 20120628 Add BY yichen  ===========begin========================*/
							Calendar caltemp0628 = null;
							caltemp0628 = Calendar.getInstance();
							caltemp0628.setTime(awi.getWdate());
							caltemp0628.set(Calendar.HOUR_OF_DAY, 12);
							caltemp0628.set(Calendar.MINUTE, 50);
							caltemp0628.set(Calendar.SECOND, 59);
							caltemp0628.set(Calendar.MILLISECOND, 999);
							/* 20120628 Add BY yichen  ============end=========================*/	
							
							cloneCal.setTime(awi.getRealIn());
							cloneCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
							cloneCal.set(Calendar.MONTH, cal
									.get(Calendar.MONDAY));
							cloneCal.set(Calendar.DAY_OF_MONTH, cal
									.get(Calendar.DAY_OF_MONTH));

							isAfternoon = false;
							inStartEndRange = false;
							isEndDelay = false; // 20120628 Add BY yichen
							for (AmsDocApply apply : docApplys) {

								// 為避免請假結束時間剛好在實上時間(但秒數已超過),造成遲到假象
								// 案例: P220460123 99/03/04
								cloneCal1 = Calendar.getInstance();
								cloneCal1.setTime(apply.getEndDate());
								/* 20120622 Add BY yichen  ===========begin========================*/
								//請假一樣有允許彈性刷卡時間 EX:若彈性刷卡時間設定為20分鐘，請假到12:00則12:20:59以前刷卡不算遲到。
								cloneCal1.set(Calendar.MINUTE,cloneCal1.get(Calendar.MINUTE)+ IConstants.AMS_SET_IN_RANGE);
								/* 20120622 Add BY yichen  ============end=========================*/
								cloneCal1.set(Calendar.SECOND, 59);								
								// 假別有包括在刷卡時間內且要核可或強迫核可才算
								if ((IConstants.AMSCodeDocProcessOK
										.equals(apply.getStatus()) || IConstants.AMSCodeDocProcessForceOK
										.equals(apply.getStatus()))
										&& (cloneCal.getTime().after(
												apply.getStartDate()) && cloneCal
												.getTime().before(														
									                    cloneCal1.getTime()))) {
									inStartEndRange = true;
									break;
								}								
                                /* 20120622 Mark BY yichen  ===========begin========================*/
                                //此處導致遲到無正常判斷，故移除！
								/* // 刷卡在中午且要核可或強迫核可才算                                                                
								if ((IConstants.AMSCodeDocProcessOK
										.equals(apply.getStatus()) || IConstants.AMSCodeDocProcessForceOK
										.equals(apply.getStatus()))
										&& (apply.getEndDate().compareTo(
												cal.getTime()) == 0 || apply
												.getEndDate().after(
														cal.getTime()))) {
									isAfternoon = true;
									break;
								}*/
                                /* 20120622 Mark BY yichen  ============end========================*/
								/* 20120628 Add BY yichen  ===========begin========================*/
								//加入判斷 若 (請假截止時間為12:00:00~12:50:59) 且 (上班刷卡時間為12:00:00~12:50:59) 不算遲到
								//EX:若 請假截止時間為12:00 下班刷卡時間為12:47 不能算遲到
								if ((IConstants.AMSCodeDocProcessOK
										.equals(apply.getStatus()) || IConstants.AMSCodeDocProcessForceOK
										.equals(apply.getStatus()))
										&& (apply.getEndDate().compareTo(
												cal.getTime()) >= 0 && apply.getEndDate().compareTo(
														caltemp0628.getTime()) <= 0)
										&& (cloneCal.getTime().compareTo(
												cal.getTime()) >= 0 && cloneCal.getTime().compareTo(
														caltemp0628.getTime()) <= 0)) {
									isAfternoon = true;
									break;
								}
								//加入判斷 若 (請假截止時間為12:00:00~12:50:59) 且 (上班刷卡時間超過12:50:59) 重計下午遲到
								//EX:若 請假截止時間為12:00 下班刷卡時間為12:57 算遲到00:07
								if ((IConstants.AMSCodeDocProcessOK
										.equals(apply.getStatus()) || IConstants.AMSCodeDocProcessForceOK
										.equals(apply.getStatus()))
										&& (apply.getEndDate().compareTo(
												cal.getTime()) >= 0 && apply.getEndDate().compareTo(
														caltemp0628.getTime()) <= 0)
										&& (cloneCal.getTime().compareTo(
														caltemp0628.getTime()) > 0)) {
									isEndDelay = true;
									break;
								}
								/* 20120628 Add BY yichen  ============end=========================*/
							}

							if (!inStartEndRange && !isAfternoon) {
								awi.setInDelay(Toolket.getTimeDistance(t, awi
										.getRealIn()));
								awi.setOnDelay("1");
								// delayCounts++;								
								/* 20120625 Mark BY yichen  ===========begin========================*/
								/*distances = awi.getRealIn().getTime()
										- awi.getSetIn().getTime();*/								
								/* 20120625 Mark BY yichen  ============end========================*/
								/* 20120625 Add BY yichen  ===========begin========================*/
                                //此處為遲到有假單，故若為上午休假遲到不可由早上開始算！	
								Calendar TempCalA = Calendar.getInstance();
								if (!isEndDelay){// 20120628 Add BY yichen
								TempCalA.setTime(cloneCal1.getTime());
								TempCalA.set(Calendar.SECOND,0);
								/* 20120628 Add BY yichen  ===========begin========================*/
								}
								else //若 (請假截止時間為12:00:00~12:50:59) 且 (上班刷卡時間大於12:50:59) 重計下午遲到
								{
									TempCalA.setTime(caltemp0628.getTime());
									TempCalA.set(Calendar.SECOND,0);
								}									
								/* 20120628 Add BY yichen  ============end=========================*/
								Calendar TempCalB = Calendar.getInstance();
								TempCalB.setTime(cloneCal1.getTime());
								TempCalB.set(Calendar.HOUR_OF_DAY,awi.getRealIn().getHours());
								TempCalB.set(Calendar.MINUTE,awi.getRealIn().getMinutes());
								TempCalB.set(Calendar.SECOND,awi.getRealIn().getSeconds());
								if (TempCalB.getTime().getTime()>TempCalA.getTime().getTime()) {	
									distances = TempCalB.getTime().getTime() - TempCalA.getTime().getTime();
									StringBuffer buffertemp = new StringBuffer();
									if (distances/HOUR <= 9){									
										buffertemp.append("0");
										buffertemp.append(distances/HOUR);
									}
									else
										buffertemp.append(distances/HOUR);//計算遲到小時
									buffertemp.append(":");
									if (distances/1000/60 <= 9){
										buffertemp.append("0");
										buffertemp.append(distances/1000/60);
									}
									else
										buffertemp.append(distances/1000/60);//計算遲到分鐘
									awi.setInDelay(buffertemp.toString());
								}
								else{
									distances = awi.getRealIn().getTime() - awi.getSetIn().getTime();
								}	
                                /* 20120625 Add BY yichen  ============end========================*/
								if ((distances / HOUR) > 1
										|| ((distances / HOUR) == 1 && (distances % HOUR) != 0)) {
									// 遲到超過1小時要算曠職時數
									hours = distances / HOUR;
									minutes = (distances % HOUR) / HALF_HOUR;
									totalDelayButNoRecordHours += minutes > 1 ? hours + 1
											: hours + 0.5;
								} else
									delayCounts++; // 遲到未超過1小時就算遲到
								}
							}
						}
					}

				if (awi.getRealOut() == null) {
					if (docApplys.isEmpty() && workDate.before(now)) {
						if (!isNO) {
							awi.setOutEarly("無下班差勤紀錄");
							awi.setOnEarly("1");
							noRealOut = true;
							// if (noWorkOnRecord)
							// noRecordCounts++;
						}
					} else if (hasRepair) {
						// awi.setOutEarly("補登");
						if (StringUtils.isBlank(repairEnd) && !isNO) {
							awi.setOutEarly("無下班差勤紀錄");
							awi.setOnEarly("1");
							noRealOut = true;
							// if (noWorkOnRecord)
							// noRecordCounts++;
						} else {
							awi.setRealOutInfo(repairEnd);
							awi.setWorkHard("1");
						}
					} else {
						// 無實下但有假單時...
						cal = Calendar.getInstance();
						cal.setTime(awi.getWdate());
						cloneCal = (Calendar) cal.clone();
						System.out.println("壞在這:"+awi.getWdate());
						cloneCal.setTime(awi.getSetOut());
						cloneCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
						cloneCal.set(Calendar.MONTH, cal.get(Calendar.MONDAY));
						cloneCal.set(Calendar.DAY_OF_MONTH, cal
								.get(Calendar.DAY_OF_MONTH));
						inStartEndRange = false;
						for (AmsDocApply apply : docApplys) {

							// 假別有包括在刷卡時間內且要核可或強迫核可才算
							if ((cloneCal.getTime().after(apply.getStartDate()) && (cloneCal
									.getTime().compareTo(apply.getEndDate()) == 0 || cloneCal
									.getTime().before(apply.getEndDate())))) {
								inStartEndRange = true;
								break;
							}
						}

						if (!inStartEndRange && workDate.before(now)) {
							if (!isNO) {
								awi.setOutEarly("無下班差勤紀錄");
								awi.setOnEarly("1");
								noRealOut = true;
								// if (noWorkOnRecord)
								// noRecordCounts++;
							}
						}

					}
				} else {					
					t = (Time) awi.getSetOut().clone();
					t.setTime(t.getTime()
							+ (IConstants.AMS_SET_OUT_RANGE * 60 * 1000));
                    /* 20120622 Add BY yichen  ===========begin========================*/
                    //增加處理下班刷卡異常(下班刷卡時間=上班刷卡時間=>應算無下班差勤紀錄論)//主任說1分鐘內					
					//if (awi.getRealOut().toString().substring(0,5).equals(awi.getRealIn().toString().substring(0,5)))
					/* 20120625 Add BY yichen  ===========begin========================*/
					//主任說不要直接用等於改用1分鐘內判斷
					
					
					if(awi.getRealIn()==null || awi.getRealOut()==null){
						
						awi.setRealOutInfo(null);
						awi.setOutEarly("無下班差勤紀錄");
						awi.setOnEarly("1");
						noRealOut = true;
						// if (noWorkOnRecord)
						// noRecordCounts++;
						
						
					}else{
						
						
						Time TemptimeA = (Time) awi.getRealIn().clone();
						Time TemptimeB = (Time) awi.getRealIn().clone();
						TemptimeB.setTime(TemptimeB.getTime()+(1 * 60 * 1000));
						Time TemptimeC = (Time) awi.getRealOut().clone();	
						if ((TemptimeC.compareTo(TemptimeA)>=0) && (TemptimeC.compareTo(TemptimeB)<=0))
						/* 20120625 Add BY yichen  ============end========================*/	
						{
							/* 20120628 Mark BY yichen  ===========begin========================*/
							/*//僅處理刷卡上班時間=刷卡下班時間時(秒數不論)
							if (!isNO) {
								awi.setRealOut(null);
								awi.setRealOutInfo(null);
								awi.setOutEarly("無下班差勤紀錄");
								awi.setOnEarly("1");
								noRealOut = true;
							}*/
							/* 20120628 Mark BY yichen  ============end=========================*/
							/* 20120628 Add BY yichen  ===========begin========================*/
							// 被當無下班差勤紀錄論時，應該要同上方 if (awi.getRealOut() == null) 時的處理
							if (docApplys.isEmpty() && workDate.before(now)) {
								if (!isNO) {
									awi.setRealOutInfo(null);
									awi.setOutEarly("無下班差勤紀錄");
									awi.setOnEarly("1");
									noRealOut = true;
								}
							} else if (hasRepair) {
								// awi.setOutEarly("補登");
								if (StringUtils.isBlank(repairEnd) && !isNO) {
									awi.setRealOutInfo(null);
									awi.setOutEarly("無下班差勤紀錄");
									awi.setOnEarly("1");
									noRealOut = true;
									// if (noWorkOnRecord)
									// noRecordCounts++;
								} else {
									awi.setRealOutInfo(repairEnd);
									awi.setWorkHard("1");
								}
							} else {
								// 無實下但有假單時...
								cal = Calendar.getInstance();
								cal.setTime(awi.getWdate());
								cloneCal = (Calendar) cal.clone();

								cloneCal.setTime(awi.getSetOut());
								cloneCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
								cloneCal.set(Calendar.MONTH, cal.get(Calendar.MONDAY));
								cloneCal.set(Calendar.DAY_OF_MONTH, cal
										.get(Calendar.DAY_OF_MONTH));
								inStartEndRange = false;
								for (AmsDocApply apply : docApplys) {

									// 假別有包括在刷卡時間內且要核可或強迫核可才算
									if ((cloneCal.getTime().after(apply.getStartDate()) && (cloneCal
											.getTime().compareTo(apply.getEndDate()) == 0 || cloneCal
											.getTime().before(apply.getEndDate())))) {
										inStartEndRange = true;
										break;
									}
								}

								if (!inStartEndRange && workDate.before(now)) {
									if (!isNO) {
										awi.setRealOutInfo(null);
										awi.setOutEarly("無下班差勤紀錄");
										awi.setOnEarly("1");
										noRealOut = true;
										// if (noWorkOnRecord)
										// noRecordCounts++;
									}
								}

							}
							/* 20120628 Add BY yichen  ============end=========================*/							
						}
						else
	                    /* 20120622 Add BY yichen  ============end========================*/
	                      if (awi.getRealOut().before(t)) {
							// 案例:F223603472 03.05請假下上半天,中午刷下班卡
							cal = Calendar.getInstance();
							cal.setTime(awi.getWdate());
							cloneCal = (Calendar) cal.clone();
							cal.set(Calendar.HOUR_OF_DAY, 12);
							cal.set(Calendar.MINUTE, 0);
							cal.set(Calendar.SECOND, 0);
							cal.set(Calendar.MILLISECOND, 0);
							
							/* 20120628 Add BY yichen  ===========begin========================*/
							Calendar caltemp0628 = null;
							caltemp0628 = Calendar.getInstance();
							caltemp0628.setTime(awi.getWdate());
							caltemp0628.set(Calendar.HOUR_OF_DAY, 12);
							caltemp0628.set(Calendar.MINUTE, 50);
							caltemp0628.set(Calendar.SECOND, 59);
							caltemp0628.set(Calendar.MILLISECOND, 999);
							/* 20120628 Add BY yichen  ============end=========================*/	

							cloneCal.setTime(awi.getRealOut());
							cloneCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
							cloneCal.set(Calendar.MONTH, cal.get(Calendar.MONDAY));
							cloneCal.set(Calendar.DAY_OF_MONTH, cal
									.get(Calendar.DAY_OF_MONTH));

							isAfternoon = false;
							inStartEndRange = false;
							isStartEarly = false; // 20120628 Add BY yichen
							for (AmsDocApply apply : docApplys) {

								// 假別有包括在刷卡時間內且要核可或強迫核可才算
								if ((IConstants.AMSCodeDocProcessOK.equals(apply
										.getStatus()) || IConstants.AMSCodeDocProcessForceOK
										.equals(apply.getStatus()))
										&& (cloneCal.getTime().after(
												apply.getStartDate()) && cloneCal
												.getTime().before(
														apply.getEndDate()))) {
									inStartEndRange = true;
									break;
								}
	                            /* 20120622 Mark BY yichen  ===========begin========================*/
	                            //此處導致早退無正常判斷，故移除！
								/* // 刷卡在中午且要核可或強迫核可才算
								if ((IConstants.AMSCodeDocProcessOK.equals(apply
										.getStatus()) || IConstants.AMSCodeDocProcessForceOK
										.equals(apply.getStatus()))
										&& (apply.getEndDate().compareTo(
												cal.getTime()) == 0 || apply
												.getEndDate().after(cal.getTime()))) {
									isAfternoon = true;
									break;
								}*/
	                            /* 20120622 Mark BY yichen  ============end========================*/
								/* 20120628 Add BY yichen  ===========begin========================*/
								//加入判斷 若 (請假起始時間為12:00:00~12:50:59) 且 (下班刷卡時間為12:00:00~12:50:59) 不算早退
								//EX:若 請假起始時間為12:30 下班刷卡時間為12:03 不能算早退
								if ((IConstants.AMSCodeDocProcessOK
										.equals(apply.getStatus()) || IConstants.AMSCodeDocProcessForceOK
										.equals(apply.getStatus()))
										&& (apply.getStartDate().compareTo(
												cal.getTime()) >= 0 && apply.getStartDate().compareTo(
														caltemp0628.getTime()) <= 0)
										&& (cloneCal.getTime().compareTo(
												cal.getTime()) >= 0 && cloneCal.getTime().compareTo(
														caltemp0628.getTime()) <= 0)) {
									isAfternoon = true;
									break;
								}
								//加入判斷 若 (請假起始時間為12:00:00~12:50:59) 且 (下班刷卡時間小於12:00:00) 重計早退時間
								//EX:若 請假起始時間為12:00 下班刷卡時間為11:57 算早退00:03
								if ((IConstants.AMSCodeDocProcessOK
										.equals(apply.getStatus()) || IConstants.AMSCodeDocProcessForceOK
										.equals(apply.getStatus()))
										&& (apply.getStartDate().compareTo(
												cal.getTime()) >= 0 && apply.getStartDate().compareTo(
														caltemp0628.getTime()) <= 0)
										&& (cloneCal.getTime().compareTo(
												cal.getTime()) < 0)) {
									isStartEarly = true;
									//System.out.println("isStartEarly = true");
									break;
								}
								/* 20120628 Add BY yichen  ============end=========================*/
							}

							if (!inStartEndRange && !isAfternoon) {
								// 處理早退
								awi.setOutEarly("-"
										+ Toolket.getTimeDistance(awi.getRealOut(),
												t));
								awi.setOnEarly("1");
								// earlyCounts++;
								/* 20120628 Mark BY yichen  ===========begin========================*/
								/*distances = awi.getSetOut().getTime()
										- awi.getRealOut().getTime();*/								
								/* 20120628 Mark BY yichen  ============end========================*/
								/* 20120628 Add BY yichen  ===========begin========================*/
	                            //EX:若 請假起始時間為12:30 下班刷卡時間為11:57 只能算早退00:03
								if (isStartEarly){
									//System.out.println("if (isStartEarly) Start");
									Calendar TempCalA = Calendar.getInstance();
									TempCalA.setTime(cal.getTime());
									TempCalA.set(Calendar.SECOND,0);
									Calendar TempCalB = Calendar.getInstance();
									TempCalB.setTime(cloneCal.getTime());
									TempCalB.set(Calendar.HOUR_OF_DAY,awi.getRealOut().getHours());
									TempCalB.set(Calendar.MINUTE,awi.getRealOut().getMinutes());
									TempCalB.set(Calendar.SECOND,0);
									if (TempCalB.getTime().getTime()<TempCalA.getTime().getTime()) {	
										distances = TempCalA.getTime().getTime() - TempCalB.getTime().getTime();
										StringBuffer buffertemp = new StringBuffer();
										buffertemp.append("-");
										if (distances/HOUR <= 9){									
											buffertemp.append("0");
											buffertemp.append(distances/HOUR);
										}
										else
											buffertemp.append(distances/HOUR);//計算遲到小時
										buffertemp.append(":");
										if (distances/1000/60 <= 9){
											buffertemp.append("0");
											buffertemp.append(distances/1000/60);
										}
										else
											buffertemp.append(distances/1000/60);//計算遲到分鐘
										awi.setOutEarly(buffertemp.toString());
									}
								}
								else{
									distances = awi.getSetOut().getTime() - awi.getRealOut().getTime();
								}									
	                            /* 20120628 Add BY yichen  ============end========================*/
								if ((distances / HOUR) > 1
										|| ((distances / HOUR) == 1 && (distances % HOUR) != 0)) {
									// 早退超過1小時要算曠職時數
									hours = distances / HOUR;
									minutes = (distances % HOUR) / HALF_HOUR;
									totalEarlyButNoRecordHours += minutes > 1 ? hours + 1
											: hours + 0.5;
								} else
									earlyCounts++; // 早退未超過1小時就算遲到
							}
						} else if (awi.getRealOut().after(awi.getSetOut())) {
							// 處理加班
							awi.setOutEarly("+"
									+ Toolket.getTimeDistance(awi.getSetOut(), awi
											.getRealOut()));
						} else {
							// 處理剛剛好刷卡時間
							awi.setOutEarly("+" + "00:00");
						}
					}
						
						
					}
					
					
					

				if (!docApplys.isEmpty()) {
					// 會多筆嗎? YES
					for (AmsDocApply ada : docApplys) {
						awi
								.setCommend((StringUtils.isBlank(awi
										.getCommend()) ? "" : awi.getCommend()
										.trim())
										+ (IConstants.AMSDocRepair.equals(ada
												.getDocType()) ? "補登"
												: (IConstants.AMSDocOverTime
														.equals(ada
																.getDocType()) ? "加班"
														: Toolket
																.getAmsAskLeave(ada
																		.getAskLeaveType())))
										+ "&nbsp;");
						awi
								.setCommendDetail((awi.getCommendDetail() == null ? ""
										: awi.getCommendDetail().trim())
										+ (IConstants.AMSDocRepair.equals(ada
												.getDocType()) ? "補登"
												: (IConstants.AMSDocOverTime
														.equals(ada
																.getDocType()) ? "加班"
														: Toolket
																.getAmsAskLeave(ada
																		.getAskLeaveType())))
										+ ":"
										+ ada.getReason()
										+ " "
										+ (ada.getStartDate() == null ? "" : df
												.format(ada.getStartDate()))
										+ "~"
										+ (ada.getEndDate() == null ? "" : df
												.format(ada.getEndDate()))
										+ "("
										+ Toolket
												.getDocProcess(ada.getStatus())
										+ ")" + "<br/>");
						awi.setStatus(ada.getStatus());
					}
				}

				if (noRealIn || noRealOut)
					noRecordCounts++; // 只要有就曠職

				if (!holidayOrWorkings.isEmpty()) {
					holidayOrWorking = holidayOrWorkings.get(0);
					if ("A".equalsIgnoreCase(holidayOrWorking.getEmplType())
							|| empl.getCategory().equalsIgnoreCase(
									holidayOrWorking.getEmplType())) {
						awi.setHoliday("1");
						awi.setHolidayType(holidayOrWorking.getType());
						awi.setCommend(holidayOrWorking.getName() + " ");
						awi.setCommendDetail(holidayOrWorking.getName());
					}
				}

				list.add(awi);

			} else if ("h".equalsIgnoreCase(awi.getDateType())) {

				if (awi.getRealIn() != null || awi.getRealOut() != null) {
					// awi.setCommend("例假日上班 ");
					// awi.setCommendDetail("例假日上班");
					awi.setWorkhardInfo("例假日上班");
					awi.setWorkHard("1");
					workOnHolidayCounts++;
					if (awi.getRealIn() != null && awi.getRealOut() != null)
						// 處理加班
						awi.setOutEarly("+"
								+ Toolket.getTimeDistance(awi.getRealIn(), awi
										.getRealOut()));
					list.add(awi);
				} else {
					if (!holidayOrWorkings.isEmpty()) {

						holidayOrWorking = holidayOrWorkings.get(0);
						if ("A"
								.equalsIgnoreCase(holidayOrWorking
										.getEmplType())
								|| empl.getCategory().equalsIgnoreCase(
										holidayOrWorking.getEmplType())) {
							awi.setHoliday("1");
							awi.setHolidayType(holidayOrWorking.getType());
							awi.setCommend(holidayOrWorking.getName() + " ");
							awi.setCommendDetail(holidayOrWorking.getName());
							list.add(awi);
						}
					}
				}
				
				if (!docApplys.isEmpty()) {
					// 會多筆嗎? YES
					for (AmsDocApply ada : docApplys) {
						awi
								.setCommend((StringUtils.isBlank(awi
										.getCommend()) ? "" : awi.getCommend()
										.trim())
										+ (IConstants.AMSDocRepair.equals(ada
												.getDocType()) ? "補登"
												: (IConstants.AMSDocOverTime
														.equals(ada
																.getDocType()) ? "加班"
														: Toolket
																.getAmsAskLeave(ada
																		.getAskLeaveType())))
										+ "&nbsp;");
						awi
								.setCommendDetail((awi.getCommendDetail() == null ? ""
										: awi.getCommendDetail().trim())
										+ (IConstants.AMSDocRepair.equals(ada
												.getDocType()) ? "補登"
												: (IConstants.AMSDocOverTime
														.equals(ada
																.getDocType()) ? "加班"
														: Toolket
																.getAmsAskLeave(ada
																		.getAskLeaveType())))
										+ ":"
										+ ada.getReason()
										+ " "
										+ (ada.getStartDate() == null ? "" : df
												.format(ada.getStartDate()))
										+ "~"
										+ (ada.getEndDate() == null ? "" : df
												.format(ada.getEndDate()))
										+ "("
										+ Toolket
												.getDocProcess(ada.getStatus())
										+ ")" + "<br/>");
						awi.setStatus(ada.getStatus());
					}
				}
			}
		}

		// 重要集會資訊
		ret.put("meetingBusinessCounts", meetingBusinessCounts);
		ret.put("meetingBusinessTotalHours", meetingBusinessTotalHours);
		ret.put("meetingBusinessScore", meetingBusinessTotalHours * 0.2);
		totalScores += meetingBusinessTotalHours * 0.2;
		ret.put("meetingSickCounts", meetingSickCounts);
		ret.put("meetingSickTotalHours", meetingSickTotalHours);
		ret.put("meetingSickScore", meetingSickTotalHours * 0.1);
		totalScores += meetingSickTotalHours * 0.1;
		ret.put("meetingWomenSickCounts", meetingWomenSickCounts);
		ret.put("meetingWomenSickTotalHours", meetingWomenSickTotalHours);
		ret.put("meetingWomenSickScore", meetingWomenSickTotalHours * 0.1);
		totalScores += meetingWomenSickTotalHours * 0.1;

		if (!list.isEmpty()) {

			amsDocApply = new AmsDocApply();
			amsDocApply.setDocType(IConstants.AMSDocAskLeave);
			amsDocApply.setIdno(empl.getIdno());
			docApplys = findAmsDocApplyBy(amsDocApply, from, to);

			for (AmsDocApply ada : docApplys) {

				isNeedAddScore = true;
				// 請假但人事室還沒處理,要算曠職
				if (!IConstants.AMSCodeDocProcessOK.equals(ada.getStatus())
						&& !IConstants.AMSCodeDocRevokeOK.equals(ada
								.getStatus())
						&& !IConstants.AMSCodeDocProcessForceOK.equals(ada
								.getStatus())
						&& !IConstants.AMSCodeDocProcessAllRest.equals(ada
								.getStatus())
						&& !IConstants.AMSCodeDocProcessHalfRest.equals(ada
								.getStatus())) {

					// 找出某天是否有遲到或早退
					for (AmsWorkdateData dd : list) {
						c = Calendar.getInstance();
						c.setTime(ada.getStartDate());
						c.set(Calendar.HOUR_OF_DAY, 0);
						c.set(Calendar.MINUTE, 0);
						c.set(Calendar.SECOND, 0);
						c.set(Calendar.MILLISECOND, 0);
						if (dd.getWdate().compareTo(c.getTime()) == 0) {
							// info = (AmsWorkdateInfo) dd;
							if (dd.getRealIn() == null
									&& dd.getRealOut() == null) {
								// 要全天沒來才算8小時
								vacationNoStatus++;
								isNeedAddScore = true;
							} else {
								isNeedAddScore = false;
							}
							 
//							if ("1".equals(info.getOnDelay())
//									|| "1".equals(info.getOnEarly())) {
//								// 是遲到或早退就不需累加請假單無處理次數
//								isNeedAddScore = false;
//							} else {
//								
//								if (dd.getRealIn() == null
//										&& dd.getRealOut() == null) {
//									vacationNoStatus++;
//									isNeedAddScore = true;
//								} else {
//
//									isNeedAddScore = false; // ???
//								}
//							}

							break;
						}
					}
				}

				if (isNeedAddScore) {
					// 寒暑假1天也是扣8hrs
					if (StringUtils.isNotBlank(ada.getAskLeaveType())) {
						switch (Integer.parseInt(ada.getAskLeaveType())) {
							case 1: // 事假
								businessCounts++;
								businessTotalHours += ada.getTotalDay() * 8
										+ ada.getTotalHour();
								businessTotalMins += ada.getTotalMinute();
								break;

							case 2: // 病假
								sickCounts++;
								sickTotalHours += ada.getTotalDay() * 8
										+ ada.getTotalHour();
								sickTotalMins += ada.getTotalMinute();
								break;

							case 3: // 公假
								leave3++;
								leave3Hours += ada.getTotalDay() * 8
										+ ada.getTotalHour();
								leave3TotalMins += ada.getTotalMinute();
								break;

							case 4: // 婚假
								leave4++;
								leave4Hours += ada.getTotalDay() * 8
										+ ada.getTotalHour();
								break;

							case 5: // 產假
								leave5++;
								leave5Hours += ada.getTotalDay() * 8
										+ ada.getTotalHour();
								break;

							case 6: // 喪假
								leave6++;
								leave6Hours += ada.getTotalDay() * 8
										+ ada.getTotalHour();
								break;

							case 7: // 年假
								leave7++;
								leave7Hours += ada.getTotalDay() * 8
										+ ada.getTotalHour();
								break;

							case 9: // 補休假
								leave9++;
								leave9Hours += ada.getTotalDay() * 8
										+ ada.getTotalHour();
								break;

							case 10: // 陪產假
								leave10++;
								leave10Hours += ada.getTotalDay() * 8
										+ ada.getTotalHour();
								break;

							case 12: // 特休
								leave12++;
								leave12Hours += ada.getTotalDay() * 8
										+ ada.getTotalHour();
								break;

							case 13: // 產前假
								leave13++;
								leave13Hours += ada.getTotalDay() * 8
										+ ada.getTotalHour();
								break;

							case 14: // 生理假
								womenSickCounts++;
								womenSickTotalHours += ada.getTotalDay() * 8
										+ ada.getTotalHour();
								womenSickTotalMins += ada.getTotalMinute();

							default:
								break;
						}
					}
				}
			}
			
			ret.put("recordData", list);
			ret.put("betweenCounts", betweenCounts);
			ret.put("delayCounts", delayCounts);
			ret.put("earlyCounts", earlyCounts);
			ret.put("delayEarlysScore", Toolket.getRoundFormat(def,
					(delayCounts + earlyCounts) * 0.3d, 0.001D));
			totalScores += Float.parseFloat(Toolket.getRoundFormat(def,
					(delayCounts + earlyCounts) * 0.3d, 0.001D));
			ret.put("totalDelayButNoRecordHours", totalDelayButNoRecordHours);
			ret.put("totalDelayButNoRecordScore", Float.parseFloat(Toolket
					.getRoundFormat(def, totalDelayButNoRecordHours * 0.5,
							0.001D)));
			totalScores += Float.parseFloat(Toolket.getRoundFormat(def,
					totalDelayButNoRecordHours * 0.5, 0.001D));
			ret.put("totalEarlyButNoRecordHours", totalEarlyButNoRecordHours);
			ret.put("totalEarlyButNoRecordScore", Float.parseFloat(Toolket
					.getRoundFormat(def, totalEarlyButNoRecordHours * 0.5,
							0.001D)));
			totalScores += Float.parseFloat(Toolket.getRoundFormat(def,
					totalEarlyButNoRecordHours * 0.5, 0.001D));
			ret.put("noRecordCounts", noRecordCounts);
			ret.put("noRecordCountsScore", (noRecordCounts * 8) * 0.5);
			totalScores += (noRecordCounts * 8) * 0.5;
			ret.put("businessCounts", businessCounts);
			minsToHours = Toolket.calMinsToHours(businessTotalMins);
			ret.put("businessTotalHours", businessTotalHours + minsToHours);
			ret.put("businessCountsScore", Toolket.getRoundFormat(def,
					(businessTotalHours + minsToHours) * 0.2, 0.001D));
			totalScores += Float.parseFloat(Toolket.getRoundFormat(def,
					(businessTotalHours + minsToHours) * 0.2, 0.001D));
			ret.put("sickCounts", sickCounts);
			minsToHours = Toolket.calMinsToHours(sickTotalMins);
			ret.put("sickTotalHours", sickTotalHours + minsToHours);
			ret.put("sickCountsScore", Toolket.getRoundFormat(def,
					(sickTotalHours + minsToHours) * 0.1, 0.001D));
			totalScores += Float.parseFloat(Toolket.getRoundFormat(def,
					(sickTotalHours + minsToHours) * 0.1, 0.001D));
			ret.put("womenSickCounts", womenSickCounts);
			minsToHours = Toolket.calMinsToHours(womenSickTotalMins);
			ret.put("womenSickTotalHours", womenSickTotalHours + minsToHours);
			ret.put("womenSickCountsScore", Toolket.getRoundFormat(def,
					(womenSickTotalHours + minsToHours) * 0.1, 0.001D));
			totalScores += Float.parseFloat(Toolket.getRoundFormat(def,
					(womenSickTotalHours + minsToHours) * 0.1, 0.001D));
			ret.put("workOnHolidayCounts", workOnHolidayCounts);
			ret.put("vacationNoStatus", vacationNoStatus);
			ret.put("vacationNoStatusScore", vacationNoStatus * 8 * 0.5);
			totalScores += vacationNoStatus * 8 * 0.5;
			ret.put("totalScores", Toolket.getRoundFormat(def, totalScores,
					0.001D));

			ret.put("leave3", leave3);
			minsToHours = Toolket.calMinsToHours(leave3TotalMins);
			ret.put("leave3Hours", (float) (leave3Hours + minsToHours));
			ret.put("leave4", leave4);
			ret.put("leave4Hours", leave4Hours);
			ret.put("leave5", leave5);
			ret.put("leave5Hours", leave5Hours);
			ret.put("leave6", leave6);
			ret.put("leave6Hours", leave6Hours);
			ret.put("leave7", leave7);
			ret.put("leave7Hours", leave7Hours);
			ret.put("leave9", leave9);
			ret.put("leave9Hours", leave9Hours);
			ret.put("leave10", leave10);
			ret.put("leave10Hours", leave10Hours);
			ret.put("leave12", leave12);
			ret.put("leave12Hours", leave12Hours);
			ret.put("leave13", leave13);
			ret.put("leave13Hours", leave13Hours);
		} else {
			ret.put("recordData", list);
			ret.put("betweenCounts", 0);
			ret.put("delayCounts", 0);
			ret.put("earlyCounts", 0);
			ret.put("delayEarlysScore", 0.0);
			ret.put("totalDelayButNoRecordHours", 0.0);
			ret.put("totalDelayButNoRecordScore", 0.0);
			ret.put("totalEarlyButNoRecordHours", 0.0);
			ret.put("totalEarlyButNoRecordScore", 0.0);
			ret.put("noRecordCounts", 0);
			ret.put("noRecordCountsScore", 0.0);
			ret.put("businessCounts", 0);
			ret.put("businessTotalHours", 0.0f);
			ret.put("businessCountsScore", 0.0);
			ret.put("sickCounts", 0);
			ret.put("sickTotalHours", 0.0f);
			ret.put("sickCountsScore", 0.0);
			ret.put("womenSickCounts", 0);
			ret.put("womenSickTotalHours", 0.0f);
			ret.put("womenSickCountsScore", 0.0);
			ret.put("workOnHolidayCounts", 0);
			ret.put("vacationNoStatus", 0);
			ret.put("vacationNoStatusScore", 0.0);
			ret.put("totalScores", "0");

			ret.put("leave3", 0);
			ret.put("leave3Hours", 0.0f);
			ret.put("leave4", 0);
			ret.put("leave4Hours", 0);
			ret.put("leave5", 0);
			ret.put("leave5Hours", 0);
			ret.put("leave6", 0);
			ret.put("leave6Hours", 0);
			ret.put("leave7", 0);
			ret.put("leave7Hours", 0);
			ret.put("leave9", 0);
			ret.put("leave9Hours", 0);
			ret.put("leave10", 0);
			ret.put("leave10Hours", 0);
			ret.put("leave12", 0);
			ret.put("leave12Hours", 0);
			ret.put("leave13", 0);
			ret.put("leave13Hours", 0);
		}

		// 計算重要集會
		meetings = findAmsMeetingsBy(from, to);
		String sql = "SELECT * FROM AMS_MeetingData a WHERE a.idno = '"
				+ empl.getIdno().toUpperCase().trim()
				+ "' AND a.status = '99' " + "AND a.meetingOid = ";
		List<Map> data = null;
		int meetingNoVisits = 0;
		ret.put("meetingAbsentCounts", meetingNoVisits);
		ret.put("meetingAbsentScores", 0.0);
		if (!meetings.isEmpty()) {
			for (AmsMeeting m : meetings) {
				data = jdbcDao.findAnyThing(sql + m.getOid());
				if (!data.isEmpty())
					meetingNoVisits++;
			}
		}
		ret.put("meetingAbsentCounts", meetingNoVisits);
		ret.put("meetingAbsentScores", meetingNoVisits * 0.5);
		totalScores += Float.parseFloat(Toolket.getRoundFormat(def,
				meetingNoVisits * 0.5, 0.001D));

		// 先給預設值,因為不一定有特休假
		ret.put("yearVacation", 0);
		ret.put("yearVacationRemain", 0.0);
		ret.put("specialVacation", 0);
		ret.put("specialVacationRemain", 0.0);
		float specialVacation=0,specialVacationRemain=0;
		if (!apvs.isEmpty()) {
			for (AmsPersonalVacation apvTemp : apvs) {
				Calendar vFrom = Calendar.getInstance();
				vFrom.setTime(apvTemp.getValidFrom());
				vFrom.set(Calendar.HOUR_OF_DAY, 0);
				vFrom.set(Calendar.MINUTE, 0);
				vFrom.set(Calendar.SECOND, 0);
				vFrom.set(Calendar.MILLISECOND, 0);

				Calendar vTo = Calendar.getInstance();
				vTo.setTime(apvTemp.getValidTo());
				vTo.set(Calendar.HOUR_OF_DAY, 23);
				vTo.set(Calendar.MINUTE, 59);
				vTo.set(Calendar.SECOND, 59);
				vTo.set(Calendar.MILLISECOND, 999);
				if (Calendar.getInstance().after(vFrom)
						&& Calendar.getInstance().before(vTo)) {
					if ("1".equals(apvTemp.getVtype())) {
						ret.put("yearVacation", apvTemp.getDays());
						ret.put("yearVacationRemain", apvTemp.getRemain());
					} else if ("2".equals(apvTemp.getVtype())) {
						specialVacation += apvTemp.getDays();
						specialVacationRemain += apvTemp.getRemain();
						ret.put("specialVacation", specialVacation);
						ret.put("specialVacationRemain", specialVacationRemain);
						//ret.put("specialVacation", apvTemp.getDays());
						//ret.put("specialVacationRemain", apvTemp.getRemain());
					}
				}
			}
		}

		// if (!amsDocApplys4AMSDocRepair.isEmpty())
		// ret.put("amsDocApplyCounts", amsDocApplys4AMSDocRepair.size());
		// else
		// ret.put("amsDocApplyCounts", 0);
		ret.put("amsDocApplyCounts", docRepair);

		if (!amsDocApplys4AMSDocOverTime.isEmpty()) {
			ret.put("amsDocOverTimeCounts", amsDocApplys4AMSDocOverTime.size());
			short totalDays = 0, totalHours = 0;
			for (AmsDocApply ada : amsDocApplys4AMSDocOverTime) {
				if (IConstants.AMSCodeDocProcessOK.equals(ada.getStatus())) {
					totalDays += ada.getTotalDay();
					totalHours += ada.getTotalHour();
				} else if (IConstants.AMSCodeDocProcessHalfRest.equals(ada
						.getStatus())) {
					totalHours += 4; // 已補休半天
				}
			}
			
			//補償時數
			int Redeem_hour,DocApply_hour;
			String Redeem = "SELECT hours FROM AMS_Redeem WHERE idno='"+empl.getIdno()+"' AND expiry>=now()";
			List<Map> Redeems = jdbcDao.findAnyThing(Redeem);
			if(Redeems.isEmpty()) Redeem_hour=0;
			else Redeem_hour=Integer.valueOf(((Map) Redeems.get(0)).get("hours").toString());
			//已休時數
			String DocApply = "SELECT IFNULL(((SUM(totalDay)*8)+SUM(totalHour)),0)as hours FROM AMS_DocApply WHERE (status!='0' AND status!='2' OR status IS NULL) AND " +
			"askLeaveType='09' AND startDate>='"+from+"' AND idno='"+empl.getIdno()+"'";
			List<Map> DocApplys = jdbcDao.findAnyThing(DocApply);
			if(DocApplys.isEmpty()) DocApply_hour=0;
			else DocApply_hour=Integer.valueOf(((Map) DocApplys.get(0)).get("hours").toString());
			ret.put("amsDocOverTimeTimes", (totalHours+Redeem_hour-DocApply_hour));
			
			//ret.put("amsDocOverTimeTimes", (totalDays + (totalHours / 8)) + "."
			//		+ Math.round(((float) (totalHours % 8) / 8f) * 10));
		} else {
			ret.put("amsDocOverTimeCounts", 0);
			ret.put("amsDocOverTimeTimes", 0);
		}

		meetings = null;
		amsWorkdateDatas = null;
		holidayOrWorkings = null;
		docApplys = null;
		list = null;
		holidayOrWorking = null;
		amsDocApply = null;
		// amsDocApplys4AMSDocRepair = null;
		meetingAskLeaves = null;
		meetingAskLeave = null;
		apv = null;
		apvs = null;

		return ret;
	}

	public void txSaveAmsMeeting(AmsMeeting amsMeeting, List<Empl> empls)
			throws DataAccessException {

		AmsMeetingData meetingData = null;
		List<DtimeClass> dcs = null;
		boolean bFlag = false;

		// 換算成課務系統的星期n
		Calendar cal = Calendar.getInstance();
		cal.setTime(amsMeeting.getMeetingDate());
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		if (weekday == 1)
			weekday = 7;
		else
			weekday--;

		Integer[] nodes = new Integer[0];
		for (int i = amsMeeting.getStartNode(); i <= amsMeeting.getEndNode(); i++)
			nodes = (Integer[]) ArrayUtils.add(nodes, i);
		
		amsMeeting.getMeetingData().clear(); // 清除之前的資料

		for (Empl empl : empls) {
			
			//System.out.println(empl.getCname()+empl.getUnit());

			bFlag = false;
			meetingData = new AmsMeetingData();
			meetingData.setIdno(empl.getIdno().toUpperCase().trim());
			meetingData.setEmplName(empl.getCname().trim());
			meetingData.setCategory(empl.getCategory().trim());
			meetingData.setUnit(empl.getUnit().trim());
			// 查Dtime開課資料
			dcs = memberdao.getEmplCourseInfo(amsMeeting.getSchoolTerm(), empl
					.getIdno(), weekday);
			if (!dcs.isEmpty()) {
				DC: {
					for (DtimeClass dc : dcs) {
						for (int i = Integer.parseInt(dc.getBegin()); i <= Integer
								.parseInt(dc.getEnd()); i++) {
							if (ArrayUtils.contains(nodes, i)) {
								bFlag = true;
								break DC;
							}
						}
					}
				}

				// 查不到Dtime還是要查Dtime_Teacher
				if (!bFlag) {
					dcs = memberdao.getEmplCourseInfo4DtimeTeacher(amsMeeting
							.getSchoolTerm(), empl.getIdno(), weekday);
					DC: {
						for (DtimeClass dc : dcs) {
							for (int i = Integer.parseInt(dc.getBegin()); i <= Integer
									.parseInt(dc.getEnd()); i++) {
								if (ArrayUtils.contains(nodes, i)) {
									bFlag = true;
									break DC;
								}
							}
						}
					}
				}
			} else {
				// 查Dtime_teacher開課資料
				dcs = memberdao.getEmplCourseInfo4DtimeTeacher(amsMeeting
						.getSchoolTerm(), empl.getIdno(), weekday);
				if (!dcs.isEmpty()) {
					DC: {
						for (DtimeClass dc : dcs) {
							for (int i = Integer.parseInt(dc.getBegin()); i <= Integer
									.parseInt(dc.getEnd()); i++) {
								if (ArrayUtils.contains(nodes, i)) {
									bFlag = true;
									break DC;
								}
							}
						}
					}
				}
			}

			if (bFlag) {
				meetingData.setStatus("A"); // 上課
			}
			amsMeeting.getMeetingData().add(meetingData);
		}

		amsMeeting.setLastModified(new Date());
		dao.saveObject(amsMeeting);
	}

	/**
	 * 
	 * @param oid
	 * @throws DataAccessException
	 */
	public void txDeleteAmsMeeting(Integer oid) throws DataAccessException {
		dao.removeObject(AmsMeeting.class, oid);
	}

	/**
	 * 
	 * @param oid
	 * @return
	 * @throws DataAccessException
	 */
	public AmsMeeting findAmsMeetingBy(Integer oid) throws DataAccessException {
		return (AmsMeeting) dao.getObject(AmsMeeting.class, oid);
	}

	public AmsDocApply getDocApplyByOid(int Oid) {
		AmsDocApply doc = null;

		String hql = "From AmsDocApply Where oid=" + Oid;
		List<AmsDocApply> docList = dao.submitQuery(hql);
		if (!docList.isEmpty()) {
			doc = docList.get(0);
		}
		return doc;
	}

	public ActionMessages saveDocByForm(DynaActionForm form, UserCredential user) {
		ActionMessages msgs = new ActionMessages();

		String idno = user.getMember().getIdno();
		boolean isTeacher = Toolket.isPureTeacher(idno);
		String opmode = form.getString("opmode");
		String docType = form.getString("docType").trim();
		String reason = form.getString("reason").trim();

		String startYear = form.getString("startYear").trim();
		String startMonth = form.getString("startMonth").trim();
		String startDay = form.getString("startDay").trim();
		String startHour = form.getString("startHour").trim();
		String startMinute = form.getString("startMinute").trim();

		String endYear = form.getString("endYear").trim();
		String endMonth = form.getString("endMonth").trim();
		String endDay = form.getString("endDay").trim();
		String endHour = form.getString("endHour").trim();
		String endMinute = form.getString("endMinute").trim();
		String memo = form.getString("memo");

		Calendar today = Calendar.getInstance();

		Calendar startCal = Calendar.getInstance();
		startCal.clear();
		if (!(startYear.equals("") || startMonth.equals("")
				|| startDay.equals("") || startHour.equals("") || startMinute
				.equals(""))) {
			startCal.set(Integer.parseInt(startYear), Integer
					.parseInt(startMonth) - 1, Integer.parseInt(startDay),
					Integer.parseInt(startHour), Integer.parseInt(startMinute),
					0);
		}

		Calendar endCal = Calendar.getInstance();
		endCal.clear();
		if (!(endYear.equals("") || endMonth.equals("") || endDay.equals("")
				|| endHour.equals("") || endMinute.equals(""))) {
			endCal.set(Integer.parseInt(endYear),
					Integer.parseInt(endMonth) - 1, Integer.parseInt(endDay),
					Integer.parseInt(endHour), Integer.parseInt(endMinute), 0);
		}

		AmsDocApply doc = new AmsDocApply();
		if (opmode.equals("revoke")) {
			doc.setMemo(memo);
			doc.setIdno(idno);
			doc.setDocType(docType);
			doc.setCreateDate(today.getTime());
			doc.setReason(reason);
			doc.setTotalDay((short) 0);
			doc.setTotalHour((short) 0);
			doc.setTotalMinute((short) 0);
			String sn = getNewDOcSN(docType, idno);
			doc.setSn(sn);
			dao.saveObject(doc);

			dao.reload(doc);
			int oid = Integer.parseInt(form.getString("oid"));
			AmsRevokedDoc revoked = new AmsRevokedDoc();
			revoked.setDocOid(doc.getOid());
			revoked.setRevokedOid(oid);
			dao.saveObject(revoked);

		} else if (opmode.equals("modify")) {
			doc = getDocApplyByOid(Integer.parseInt(form.getString("oid")
					.trim()));
			if (doc.getStatus() != null) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "該申請單已經人事室處理過了!無法再修改!"));
				return msgs;
			} else if ((docType.equals(IConstants.AMSDocAskLeave) || docType
					.equals(IConstants.AMSDocOverTime))
					&& (!startCal.isSet(Calendar.MONTH) || !endCal
							.isSet(Calendar.MONTH))) {
				if (!startCal.isSet(Calendar.MONTH)) {
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "起始日期錯誤!"));
				}
				if (!endCal.isSet(Calendar.MONTH)) {
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "結束日期錯誤!"));
				}
				return msgs;

			} else {
				if (docType.equals(IConstants.AMSDocAskLeave)) {
					// 請假單
					String askLeaveType = form.getString("askLeaveType").trim();
					String agent = form.getString("agent").trim();
					String agentName = form.getString("fscname").trim();
					String teachPeriod = form.getString("teachPeriod").trim();
					String totalDay = form.getString("totalDay").trim();
					String totalHour = form.getString("totalHour").trim();
					String totalMinute = form.getString("totalMinute").trim();
					int days[] = new int[4];

					if (agent.equals("")) {
						msgs.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("MessageN1",
										"請假期間職務代理人不得為空白,請重新輸入代理人!"));
						return msgs;
					}
					if (!isTeacher) {
						Map chkMap = calAskLeaveDays(
								user.getMember().getIdno(), askLeaveType,
								startCal, endCal);
						StringBuffer errors = new StringBuffer();
						if (chkMap.get("err1") != null
								|| chkMap.get("err2") != null
								|| chkMap.get("err3") != null
								|| chkMap.get("err4") != null) {
							if (chkMap.get("err1") != null) {
								errors.append((String) chkMap.get("err1"));
							}
							if (chkMap.get("err2") != null) {
								errors.append((String) chkMap.get("err2"));
							}
							if (chkMap.get("err3") != null) {
								errors.append((String) chkMap.get("err3"));
							}
							if (chkMap.get("err4") != null) {
								errors.append((String) chkMap.get("err4"));
							}

						}
						if (errors.length() > 0) {
							msgs.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("MessageN1", errors
											.toString()));
							return msgs;
						}
						if (chkMap.get("days") != null) {
							days = (int[]) chkMap.get("days");
						}
						doc.setTotalDay((short) days[0]);
						doc.setTotalHour((short) days[1]);
						doc.setTotalMinute((short) days[2]);

					} else {
						if (!totalDay.equals("")) {
							doc.setTotalDay((short) Integer.parseInt(totalDay));
						} else {
							doc.setTotalDay((short) 0);
						}
						if (!totalHour.equals("")) {
							doc.setTotalHour((short) Integer
									.parseInt(totalHour));
						} else {
							doc.setTotalHour((short) 0);
						}
						if (!totalMinute.equals("")) {
							doc.setTotalMinute((short) Integer
									.parseInt(totalMinute));
						} else {
							doc.setTotalMinute((short) 0);
						}
					}

					doc.setStartDate(startCal.getTime());
					doc.setEndDate(endCal.getTime());
					doc.setAgent(agent);
					doc.setAskLeaveType(askLeaveType);
					if (!teachPeriod.equals("")) {
						doc.setTeachPeriod(Short.parseShort(teachPeriod));
					}
					// int[] timediff = Toolket.timeTransfer(startCal, endCal);
					/*
					*/

				} else if (docType.equals(IConstants.AMSDocOverTime)) {
					// 加班
					String totalDay = form.getString("totalDay").trim();
					String totalHour = form.getString("totalHour").trim();
					String totalMinute = form.getString("totalMinute").trim();

					doc.setStartDate(startCal.getTime());
					doc.setEndDate(endCal.getTime());
					// int[] timediff = Toolket.timeTransfer(startCal, endCal);
					if (!totalDay.equals("")) {
						if (totalDay.equals("0.5")) {
							doc.setTotalHour((short) 4);
							doc.setTotalDay((short) 0);
						} else if (totalDay.equals("1")) {
							doc.setTotalHour((short) 0);
							doc.setTotalDay((short) 1);
						}
						doc.setTotalMinute((short) 0);
					}

				} else if (docType.equals(IConstants.AMSDocRepair)) {
					// 未刷卡補登
					if (startCal.isSet(Calendar.MONTH)) {
						doc.setStartDate(startCal.getTime());
					}
					if (endCal.isSet(Calendar.MONTH)) {
						doc.setEndDate(endCal.getTime());
					}
					doc.setTotalDay((short) 0);
					doc.setTotalHour((short) 0);
					doc.setTotalMinute((short) 0);
				} else if (docType.equals(IConstants.AMSDocRevoke)) {
					// 銷假 nothing to revoke special (only save revoke reason)
				}

				doc.setMemo(memo);
				doc.setCreateDate(today.getTime());
				doc.setReason(reason);
				String sn = getNewDOcSN(docType, idno);
				doc.setSn(sn);
				dao.saveObject(doc);
			}
		} else if (opmode.equals("add")) {
			if ((docType.equals(IConstants.AMSDocAskLeave) || docType
					.equals(IConstants.AMSDocOverTime))
					&& (!startCal.isSet(Calendar.MONTH) || !endCal
							.isSet(Calendar.MONTH))) {
				if (!startCal.isSet(Calendar.MONTH)) {
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "起始日期錯誤!"));
				}
				if (!endCal.isSet(Calendar.MONTH)) {
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "結束日期錯誤!"));
				}
				return msgs;

			} else {

				if (docType.equals(IConstants.AMSDocAskLeave)) {
					// 請假單
					String askLeaveType = form.getString("askLeaveType").trim();
					String agent = form.getString("agent").trim();
					String teachPeriod = form.getString("teachPeriod").trim();
					String totalDay = form.getString("totalDay").trim();
					String totalHour = form.getString("totalHour").trim();
					String totalMinute = form.getString("totalMinute").trim();

					if (agent.equals("")) {
						msgs.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("MessageN1",
										"請假期間職務代理人不得為空白,請重新輸入代理人!"));
						return msgs;
					}

					int days[] = { 0, 0, 0, 0 };
					if (!isTeacher) {
						Map chkMap = calAskLeaveDays(
								user.getMember().getIdno(), askLeaveType,
								startCal, endCal);
						StringBuffer errors = new StringBuffer();
						if (chkMap.get("err1") != null
								|| chkMap.get("err2") != null
								|| chkMap.get("err3") != null
								|| chkMap.get("err4") != null) {
							if (chkMap.get("err1") != null) {
								errors.append((String) chkMap.get("err1"));
							}
							if (chkMap.get("err2") != null) {
								errors.append((String) chkMap.get("err2"));
							}
							if (chkMap.get("err3") != null) {
								errors.append((String) chkMap.get("err3"));
							}
							if (chkMap.get("err4") != null) {
								errors.append((String) chkMap.get("err4"));
							}

						}
						if (errors.length() > 0) {
							msgs.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("MessageN1", errors
											.toString()));
							return msgs;
						}
						if (chkMap.get("days") != null) {
							days = (int[]) chkMap.get("days");
						}
						doc.setTotalDay((short) days[0]);
						doc.setTotalHour((short) days[1]);
						doc.setTotalMinute((short) days[2]);
					} else {
						if (!totalDay.equals("")) {
							doc.setTotalDay((short) Integer.parseInt(totalDay));
						} else {
							doc.setTotalDay((short) 0);
						}
						if (!totalHour.equals("")) {
							doc.setTotalHour((short) Integer
									.parseInt(totalHour));
						} else {
							doc.setTotalHour((short) 0);
						}
						if (!totalMinute.equals("")) {
							doc.setTotalMinute((short) Integer
									.parseInt(totalMinute));
						} else {
							doc.setTotalMinute((short) 0);
						}
					}

					doc.setStartDate(startCal.getTime());
					doc.setEndDate(endCal.getTime());
					doc.setAgent(agent);
					doc.setAskLeaveType(askLeaveType);
					// if(Toolket.isTeaching(idno)){
					if (!teachPeriod.equals("")) {
						doc.setTeachPeriod(Short.parseShort(teachPeriod));
					}

					// int[] timediff = Toolket.timeTransfer(startCal, endCal);
					/*
					*/

					doc.setMemo(memo);
				} else if (docType.equals(IConstants.AMSDocOverTime)) {
					// 加班
					String totalDay = form.getString("totalDay").trim();
					String totalHour = form.getString("totalHour").trim();
					String totalMinute = form.getString("totalMinute").trim();

					doc.setStartDate(startCal.getTime());
					doc.setEndDate(endCal.getTime());
					// int[] timediff = Toolket.timeTransfer(startCal, endCal);
					if (!totalDay.equals("")) {
						if (totalDay.equals("0.5")) {
							doc.setTotalHour((short) 4);
							doc.setTotalDay((short) 0);
						} else if (totalDay.equals("1")) {
							doc.setTotalHour((short) 0);
							doc.setTotalDay((short) 1);
						} else {
							doc.setTotalHour((short) 0);
							doc.setTotalDay((short) 0);
						}
					} else {
						doc.setTotalHour((short) 0);
						doc.setTotalDay((short) 1);
					}
					doc.setTotalMinute((short) 0);

				} else if (docType.equals(IConstants.AMSDocRepair)) {
					// 未刷卡補登
					if (startCal.isSet(Calendar.MONTH)) {
						doc.setStartDate(startCal.getTime());
					}
					if (endCal.isSet(Calendar.MONTH)) {
						doc.setEndDate(endCal.getTime());
					}
					doc.setTotalDay((short) 0);
					doc.setTotalHour((short) 0);
					doc.setTotalMinute((short) 0);
				} else if (docType.equals(IConstants.AMSDocRevoke)) {
					// 銷假
					doc.setTotalDay((short) 0);
					doc.setTotalHour((short) 0);
					doc.setTotalMinute((short) 0);
				}

				doc.setMemo(memo);
				doc.setIdno(idno);
				doc.setDocType(docType);
				doc.setCreateDate(today.getTime());
				doc.setReason(reason);
				String sn = getNewDOcSN(docType, idno);
				doc.setSn(sn);
				dao.saveObject(doc);

				if (docType.equals(IConstants.AMSDocRevoke)) {
					dao.reload(doc);
					int oid = Integer.parseInt(form.getString("oid"));
					AmsRevokedDoc revoked = new AmsRevokedDoc();
					revoked.setDocOid(doc.getOid());
					revoked.setRevokedOid(oid);
					dao.saveObject(revoked);
				}
			}
		}
		return msgs;
	}

	public AmsAskLeave getAskLeaveName(String askLeaveType) {
		AmsAskLeave ret = null;
		String hql = "From AmsAskLeave Where id='" + askLeaveType + "'";
		List<AmsAskLeave> asList = dao.submitQuery(hql);
		if (!asList.isEmpty()) {
			ret = asList.get(0);
		}
		return ret;
	}

	public int getRepairDocCount(String idno, Calendar startCal) {
		Calendar uYear = Calendar.getInstance();
		Calendar lYear = Calendar.getInstance();
		int years = startCal.get(Calendar.YEAR);
		int months = startCal.get(Calendar.MONTH);
		if (months == 0) {
			lYear.set(years - 1, 7, 1, 0, 0, 0);
			uYear.set(years, 0, 31, 23, 59, 59);
		} else if (months <= 6) {
			lYear.set(years, 1, 1, 0, 0, 0);
			uYear.set(years, 6, 31, 23, 59, 59);
		} else {
			lYear.set(years, 7, 1, 0, 0, 0);
			uYear.set(years + 1, 0, 31, 23, 59, 59);
		}

		int cnt = 0;
		String hql = "From AmsDocApply Where docType='"
				+ IConstants.AMSDocRepair + "' And idno='" + idno
				+ "' And (status='" + IConstants.AMSCodeDocProcessOK
				+ "' Or status='" + IConstants.AMSCodeDocProcessForceOK
				+ "') And startDate>=? And endDate<=?";
		Object[] parameters = new Object[] { lYear.getTime(), uYear.getTime() };
		List<AmsDocApply> docList = dao.submitQueryP(hql, parameters);
		if (!docList.isEmpty()) {
			for (AmsDocApply repair : docList) {
				if (repair.getStartDate() != null) {
					cnt++;
				}
				if (repair.getEndDate() != null) {
					cnt++;
				}
			}
		}
		return cnt;
	}

	public List<AmsAskLeave> getAllAskLeave() {
		String hql = "From AmsAskLeave Order by id";
		return dao.submitQuery(hql);
	}

	public float checkHaveVacation(String idno, String askLeaveType,
			Calendar startCal, Calendar endCal) {
		// 假別(年休:07,補休:09,特休:12)
		String hql = "";
		float days = 0f;

		if (askLeaveType.equals("07") || askLeaveType.equals("12")) {
			startCal.set(Calendar.HOUR, 0);
			startCal.set(Calendar.MINUTE, 0);
			startCal.set(Calendar.SECOND, 0);
			endCal.set(Calendar.HOUR, 0);
			endCal.set(Calendar.MINUTE, 0);
			endCal.set(Calendar.SECOND, 0);

			List<AmsPersonalVacation> vaList = new ArrayList<AmsPersonalVacation>();
			if (askLeaveType.equals("07")) {
				hql = "From AmsPersonalVacation Where idno=? And vType='1' And validFrom <= ? And validTo >= ?";
			} else if (askLeaveType.equals("12")) {
				hql = "From AmsPersonalVacation Where idno=? And vType='2' And validFrom <= ? And validTo >= ?";
			}
			vaList = dao.submitQueryP(hql, new Object[] { idno,
					startCal.getTime(), endCal.getTime() });
			if (!vaList.isEmpty()) {
				for (AmsPersonalVacation va : vaList) {
					days += va.getRemain();
				}
			}
			log.debug(hql + " , " + startCal.getTime() + " , "
					+ endCal.getTime() + " -->result:" + days);
		} else if (askLeaveType.equals("09")) { // 補休
			// Calendar today = Calendar.getInstance();
			//2010/07/27 因應人事室需求，加班可補休期限改為動態調整，由人事室設定
			int years = startCal.get(Calendar.YEAR);
			Calendar learnYear = Calendar.getInstance();
			learnYear.set(years, 6, 31, 23, 59, 59);
			Calendar uYear = Calendar.getInstance();
			Calendar lYear = Calendar.getInstance();
			/*
			if (startCal.compareTo(learnYear) > 0) {
				lYear.set(years, 7, 1, 0, 0, 0);
				uYear.set(years + 1, 6, 31, 23, 59, 59);
			} else {
				lYear.set(years - 1, 7, 1, 0, 0, 0);
				uYear.set(years, 6, 31, 23, 59, 59);
			}
			*/
			String[] sDate = Toolket.getSysParameter("AMSOverAskLeaveStart").split("-");
			String[] eDate = Toolket.getSysParameter("AMSOverAskLeaveEnd").split("-");
			lYear.set(Integer.parseInt(sDate[0]), Integer.parseInt(sDate[1]) -1, Integer.parseInt(sDate[2]), 0, 0, 0);
			uYear.set(Integer.parseInt(eDate[0]), Integer.parseInt(eDate[1]) -1, Integer.parseInt(eDate[2]), 23, 59, 59);
			
			hql = "From AmsDocApply Where idno=? And startDate>=? And endDate<=? And docType=? And (status=? OR status=? OR status=?) Order By startDate";

			List<AmsDocApply> oneList = dao.submitQueryP(hql, new Object[] {
					idno, lYear.getTime(), uYear.getTime(),
					IConstants.AMSDocOverTime, IConstants.AMSCodeDocProcessOK,
					IConstants.AMSCodeDocProcessForceOK,
					IConstants.AMSCodeDocProcessHalfRest });

			if (!oneList.isEmpty()) {
				Calendar wCal = Calendar.getInstance();
				for (AmsDocApply doc : oneList) {
					if (doc.getTotalDay() != (short) 0
							|| doc.getTotalHour() != (short) 0) {
						wCal.setTimeInMillis(doc.getStartDate().getTime());
						String sdate = "'" + wCal.get(Calendar.YEAR) + "-"
								+ (wCal.get(Calendar.MONTH) + 1) + "-"
								+ wCal.get(Calendar.DATE) + "'";
						String stime = "'" + wCal.get(Calendar.HOUR_OF_DAY)
								+ ":" + wCal.get(Calendar.MINUTE) + ":59'";
						wCal.setTimeInMillis(doc.getEndDate().getTime());
						String etime = "'" + wCal.get(Calendar.HOUR_OF_DAY)
								+ ":" + wCal.get(Calendar.MINUTE) + ":"
								+ wCal.get(Calendar.SECOND) + "'";
						hql = "From AmsWorkdate Where id.idno='" + idno
								+ "' And id.wdate=" + sdate + " And realIn<="
								+ stime + " And realOut>=" + etime;
						log.debug(hql);
						// Object[] parameters = new Object[]{idno ,
						// wCal.getTime()};
						List<AmsWorkdate> workList = dao.submitQuery(hql);
						if (!workList.isEmpty()) {
							if (doc.getStatus().equals(
									IConstants.AMSCodeDocProcessOK)
									|| doc
											.getStatus()
											.equals(
													IConstants.AMSCodeDocProcessForceOK)) {
								if (doc.getTotalDay() > 0) {
									days += doc.getTotalDay();
								}
								if (doc.getTotalHour() == 4) {
									days += 0.5f;
								}
							} else if (doc.getStatus().equals(
									IConstants.AMSCodeDocProcessHalfRest)) {
								days += 0.5f;
							}

						}
					}
				}
			}

		}

		log.debug("vacation:" + days);

		return days;
	}

	public List<Object> getPersonalVacation(String idno, String askLeaveType,
			Calendar startCal, Calendar endCal) {
		// 假別(年休:07,補休:09,特休:12)
		String hql = "";

		List<Object> retList = new ArrayList<Object>();
		if (askLeaveType.equals("07") || askLeaveType.equals("12")) {
			startCal.set(Calendar.HOUR, 0);
			startCal.set(Calendar.MINUTE, 0);
			startCal.set(Calendar.SECOND, 0);
			endCal.set(Calendar.HOUR, 0);
			endCal.set(Calendar.MINUTE, 0);
			endCal.set(Calendar.SECOND, 0);

			// List<AmsPersonalVacation> vaList = new
			// ArrayList<AmsPersonalVacation>();
			if (askLeaveType.equals("07")) {
				hql = "From AmsPersonalVacation Where idno=? And vType='1' And validFrom <= ? And validTo >= ? Order By validFrom";
			} else if (askLeaveType.equals("12")) {
				hql = "From AmsPersonalVacation Where idno=? And vType='2' And validFrom <= ? And validTo >= ? Order By validFrom";
			}
			retList = dao.submitQueryP(hql, new Object[] { idno,
					startCal.getTime(), endCal.getTime() });
			/*
			 * if(!vaList.isEmpty()){ for(AmsPersonalVacation va:vaList){ days
			 * += va.getRemain(); } } log.debug(hql + " , " + startCal.getTime()
			 * + " , " + endCal.getTime()+ " -->result:" + days);
			 */

		} else if (askLeaveType.equals("09")) { // 補休
			// Calendar today = Calendar.getInstance();
			int years = startCal.get(Calendar.YEAR);
			Calendar learnYear = Calendar.getInstance();
			learnYear.set(years, 6, 31, 23, 59, 59);
			Calendar uYear = Calendar.getInstance();
			Calendar lYear = Calendar.getInstance();
			/*
			if (startCal.compareTo(learnYear) > 0) {
				lYear.set(years, 7, 1, 0, 0, 0);
				uYear.set(years + 1, 6, 31, 23, 59, 59);
			} else {
				lYear.set(years - 1, 7, 1, 0, 0, 0);
				uYear.set(years, 6, 31, 23, 59, 59);
			}
			*/
			String[] sDate = Toolket.getSysParameter("AMSOverAskLeaveStart").split("-");
			String[] eDate = Toolket.getSysParameter("AMSOverAskLeaveEnd").split("-");
			/* 20120618 Mark BY yichen  ===========begin========================*/
			//lYear.set(Integer.parseInt(sDate[0]), Integer.parseInt(sDate[1]), Integer.parseInt(sDate[2]), 0, 0, 0);
			//uYear.set(Integer.parseInt(eDate[0]), Integer.parseInt(eDate[1]), Integer.parseInt(eDate[2]), 23, 59, 59);
			/* 20120618 Mark BY yichen  ============end========================*/
			/* 20120618 Modi BY yichen  ===========begin========================*/
			lYear.set(Integer.parseInt(sDate[0]), Integer.parseInt(sDate[1]) -1, Integer.parseInt(sDate[2]), 0, 0, 0);
			uYear.set(Integer.parseInt(eDate[0]), Integer.parseInt(eDate[1]) -1, Integer.parseInt(eDate[2]), 23, 59, 59);
			/* 20120618 Modi BY yichen  ============end========================*/

			hql = "From AmsDocApply Where idno=? And startDate>=? And endDate<=? And docType=? And (status=? OR status=? OR status=?) Order By startDate";

			List<AmsDocApply> oneList = dao.submitQueryP(hql, new Object[] {
					idno, lYear.getTime(), uYear.getTime(),
					IConstants.AMSDocOverTime, IConstants.AMSCodeDocProcessOK,
					IConstants.AMSCodeDocProcessForceOK,
					IConstants.AMSCodeDocProcessHalfRest });

			if (!oneList.isEmpty()) {
				Calendar wCal = Calendar.getInstance();
				for (AmsDocApply doc : oneList) {
					if (doc.getTotalDay() != (short) 0
							|| doc.getTotalHour() != (short) 0) {
						wCal.setTimeInMillis(doc.getStartDate().getTime());
						String sdate = "'" + wCal.get(Calendar.YEAR) + "-"
								+ (wCal.get(Calendar.MONTH) + 1) + "-"
								+ wCal.get(Calendar.DATE) + "'";
						String stime = "'" + wCal.get(Calendar.HOUR_OF_DAY)
								+ ":" + wCal.get(Calendar.MINUTE) + ":"
								+ wCal.get(Calendar.SECOND) + "'";
						wCal.setTimeInMillis(doc.getEndDate().getTime());
						String etime = "'" + wCal.get(Calendar.HOUR_OF_DAY)
								+ ":" + wCal.get(Calendar.MINUTE) + ":"
								+ wCal.get(Calendar.SECOND) + "'";
						hql = "From AmsWorkdate Where id.idno='" + idno
								+ "' And id.wdate=" + sdate + " And realIn<="
								+ stime + " And realOut>=" + etime;
						log.debug(hql);
						List<AmsWorkdate> workList = dao.submitQuery(hql);
						if (!workList.isEmpty()) {
							retList.add(doc);
						}
					}
				}
			}

		}

		return retList;
	}

	public List<AmsDocApply> getOverTimeRested(String idno, Calendar startCal,
			Calendar endCal) {
		int years = startCal.get(Calendar.YEAR);
		Calendar learnYear = Calendar.getInstance();
		learnYear.set(years, 6, 31, 23, 59, 59);
		Calendar uYear = Calendar.getInstance();
		Calendar lYear = Calendar.getInstance();
		if (startCal.compareTo(learnYear) > 0) {
			lYear.set(years, 7, 1, 0, 0, 0);
			uYear.set(years + 1, 6, 31, 23, 59, 59);
		} else {
			lYear.set(years - 1, 7, 1, 0, 0, 0);
			uYear.set(years, 6, 31, 23, 59, 59);
		}

		String hql = "From AmsDocApply Where idno=? And startDate>=? And endDate<=? And docType=? And (status=? OR status=?) Order By endDate DESC";

		List<AmsDocApply> oneList = dao.submitQueryP(hql, new Object[] { idno,
				lYear.getTime(), uYear.getTime(), IConstants.AMSDocOverTime,
				IConstants.AMSCodeDocProcessAllRest,
				IConstants.AMSCodeDocProcessHalfRest });

		return oneList;
	}

	public void setDocExtraData(AmsDocApply doc) {
		if (!doc.getDocType().equals(IConstants.AMSDocRevoke)) {
			Calendar cal = Calendar.getInstance();
			if (doc.getStartDate() != null) {
				cal.setTimeInMillis(doc.getStartDate().getTime());
				doc.setStartYear("" + cal.get(Calendar.YEAR));
				doc.setStartMonth("" + (cal.get(Calendar.MONTH) + 1));
				doc.setStartDay("" + cal.get(Calendar.DATE));
				doc.setStartHour("" + cal.get(Calendar.HOUR_OF_DAY));
				doc.setStartMinute("" + cal.get(Calendar.MINUTE));
				doc.setStartSecond("" + cal.get(Calendar.SECOND));
			}
			if (doc.getEndDate() != null) {
				cal.setTimeInMillis(doc.getEndDate().getTime());
				doc.setEndYear("" + cal.get(Calendar.YEAR));
				doc.setEndMonth("" + (cal.get(Calendar.MONTH) + 1));
				doc.setEndDay("" + cal.get(Calendar.DATE));
				doc.setEndHour("" + cal.get(Calendar.HOUR_OF_DAY));
				doc.setEndMinute("" + cal.get(Calendar.MINUTE));
				doc.setEndSecond("" + cal.get(Calendar.SECOND));
			}
		}

		List<AmsAskLeave> askList = getAllAskLeave();
		doc.setStatusName("未處理");
		if (doc.getStatus() != null) {
			for (int i = 0; i < IConstants.AMSDocProcess.length; i++) {
				if (doc.getStatus().equals(IConstants.AMSDocProcess[i][0])) {
					doc.setStatusName(IConstants.AMSDocProcess[i][1]);
					break;
				}
			}
		}
		doc.setAskLeaveName("");
		if (doc.getAskLeaveType() != null) {
			for (AmsAskLeave al : askList) {
				if (doc.getAskLeaveType().equals(al.getId())) {
					doc.setAskLeaveName(al.getName());
					break;
				}
			}
		}
		Employee agent = memberdao.findEmployeeByIdno(doc.getAgent());
		if (agent != null) {
			doc.setFscname(agent.getName());
		} else {
			doc.setFscname("");
			doc.setAgent("");
		}

		Employee user = memberdao.findEmployeeByIdno(doc.getIdno());
		if (user != null) {
			doc.setCname(user.getName());
		} else {
			doc.setCname("");
		}

		if (doc.getDocType().equals(IConstants.AMSDocAskLeave)) {
			doc.setDocTypeName("請假");
		} else if (doc.getDocType().equals(IConstants.AMSDocOverTime)) {
			doc.setDocTypeName("加班");
		} else if (doc.getDocType().equals(IConstants.AMSDocRepair)) {
			doc.setDocTypeName("補登");
		} else if (doc.getDocType().equals(IConstants.AMSDocRevoke)) {
			doc.setDocTypeName("銷假");
			AmsRevokedDoc revoke = this.getRevokedDoc(doc.getOid());
			if (revoke != null) {
				doc
						.setRevokedDoc(this.getDocApplyByOid(revoke
								.getRevokedOid()));
			} else {
				doc.setRevokedDoc(null);
			}
		}

		try{
			doc.setIsExpired(this.isExpireDoc(doc));
		}catch(Exception e){
			
		}
	}

	public AmsRevokedDoc getRevokedDoc(int oid) {
		AmsRevokedDoc ret = null;
		String hql = "From AmsRevokedDoc Where docOid=" + oid;
		List<AmsRevokedDoc> revokedList = dao.submitQuery(hql);
		if (!revokedList.isEmpty()) {
			ret = revokedList.get(0);
		}
		return ret;
	}

	public AmsRevokedDoc getRevokedDocByAskLeaveDoc(int oid) {
		AmsRevokedDoc ret = null;
		String hql = "From AmsRevokedDoc Where revokedOid=" + oid;
		List<AmsRevokedDoc> revokedList = dao.submitQuery(hql);
		if (!revokedList.isEmpty()) {
			ret = revokedList.get(0);
		}
		return ret;
	}

	public boolean isExpireDoc(AmsDocApply doc) {
		boolean isExpire = false;
		String docType = doc.getDocType();
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.set(Calendar.SECOND, 59);
		today.set(Calendar.MILLISECOND, 999);

		if (doc.getDocType().equals(IConstants.AMSDocAskLeave)) {
			endCal.setTimeInMillis(doc.getEndDate().getTime());
			endCal.set(Calendar.HOUR_OF_DAY, 23);
			endCal.set(Calendar.MINUTE, 59);
			endCal.set(Calendar.SECOND, 59);
			endCal.set(Calendar.MILLISECOND, 999);
			endCal.add(Calendar.DAY_OF_MONTH, 7);
			if (endCal.compareTo(today) < 0) {
				isExpire = true;
			}
		} else if (doc.getDocType().equals(IConstants.AMSDocOverTime)) {
			startCal.setTimeInMillis(doc.getStartDate().getTime());
			startCal.set(Calendar.HOUR_OF_DAY, 23);
			startCal.set(Calendar.MINUTE, 59);
			startCal.set(Calendar.SECOND, 59);
			startCal.set(Calendar.MILLISECOND, 999);
			int week = startCal.get(Calendar.DAY_OF_WEEK)-1;
			//System.out.println(startCal.get(Calendar.DAY_OF_MONTH)+"日 星期 "+week);
			if(week==3||week==4||week==5){//跨週休二日-列印日期加二天
				startCal.add(Calendar.DAY_OF_MONTH, 2);
			}
			if(week==6){//星期六加班-列印日期加一天
				startCal.add(Calendar.DAY_OF_MONTH, 1);
			}
			startCal.add(Calendar.DAY_OF_MONTH, 3);
			if (startCal.compareTo(today) < 0) {
				isExpire = true;
			}
		} else if (doc.getDocType().equals(IConstants.AMSDocRepair)) {
			if (doc.getEndDate() != null) {
				endCal.setTimeInMillis(doc.getEndDate().getTime());
				endCal.set(Calendar.HOUR_OF_DAY, 23);
				endCal.set(Calendar.MINUTE, 59);
				endCal.set(Calendar.SECOND, 59);
				endCal.set(Calendar.MILLISECOND, 999);
				endCal.add(Calendar.DAY_OF_MONTH, 7);
				if (endCal.compareTo(today) < 0) {
					isExpire = true;
				}
			} else if (doc.getStartDate() != null) {
				startCal.setTimeInMillis(doc.getStartDate().getTime());
				startCal.set(Calendar.HOUR_OF_DAY, 23);
				startCal.set(Calendar.MINUTE, 59);
				startCal.set(Calendar.SECOND, 59);
				startCal.set(Calendar.MILLISECOND, 999);
				startCal.add(Calendar.DAY_OF_MONTH, 7);
				if (startCal.compareTo(today) < 0) {
					isExpire = true;
				}
			} else {
				isExpire = true;
			}
		} else if (doc.getDocType().equals(IConstants.AMSDocRevoke)) {
			// this.setDocExtraData(doc);
			AmsDocApply revoked = this.getDocApplyByOid(this.getRevokedDoc(
					doc.getOid()).getRevokedOid());
			endCal.setTimeInMillis(revoked.getEndDate().getTime());
			endCal.set(Calendar.HOUR_OF_DAY, 23);
			endCal.set(Calendar.MINUTE, 59);
			endCal.set(Calendar.SECOND, 59);
			endCal.set(Calendar.MILLISECOND, 999);
			endCal.add(Calendar.DAY_OF_MONTH, 7);
			if (endCal.compareTo(today) < 0) {
				isExpire = true;
			}
		} else {
			isExpire = true;
		}
		return isExpire;
	}

	public AmsDocApply getDocApplyBySn(String sn) {
		AmsDocApply doc = null;

		List<AmsDocApply> docList = dao
				.submitQuery("From AmsDocApply Where sn='" + sn + "'");
		if (!docList.isEmpty()) {
			doc = docList.get(0);
		}
		return doc;
	}

	public AmsWorkdate findAmsWorkdateBy(String idno, Date wdate) {
		AmsWorkdate work = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(wdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		List<AmsWorkdate> workList = dao.submitQueryP(
				"From AmsWorkdate Where id.idno=? And id.wdate=?",
				new Object[] { idno, cal.getTime() });
		if (!workList.isEmpty()) {
			work = workList.get(0);
		}
		return work;
	}

	public ActionMessages DocExamine(AmsDocApply doc, boolean force) {
		ActionMessages msgs = new ActionMessages();

		String docType = doc.getDocType();
		Calendar today = Calendar.getInstance();
		Date tdate = today.getTime();

		String askLeaveType = doc.getAskLeaveType();
		String idno = doc.getIdno();
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();

		if (docType.equals(IConstants.AMSDocAskLeave)) {
			startCal.setTimeInMillis(doc.getStartDate().getTime());
			endCal.setTimeInMillis(doc.getEndDate().getTime());

			float total = 0f, remain = 0f;

			total = doc.getTotalDay();
			if (doc.getTotalHour() == (short) 4) {
				total += 0.5f;
			}

			remain = this.checkHaveVacation(idno, askLeaveType, startCal, endCal);

			if (askLeaveType.equals("07")) {
				// 年假
				if (remain < total) {
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "欲申請休假日數:" + total + "日, 超過剩餘日數:"
									+ remain + "日! "));
				} else {
					List<Object> vacations = this.getPersonalVacation(idno,
							askLeaveType, startCal, endCal);
					AmsPersonalVacation vacation = (AmsPersonalVacation) vacations
							.get(0);
					if (vacation.getRemain() >= total) {
						doc.setStatus(IConstants.AMSCodeDocProcessOK);
						doc.setProcessDate(tdate);
						dao.saveObject(doc);
						vacation.setRemain(vacation.getRemain() - total);
						dao.saveObject(vacation);
					} else {
						msgs.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("MessageN1", "欲申請休年假日數:"
										+ total + "日, 超過年假剩餘日數:"
										+ vacation.getRemain() + "日! "));
					}
				}
			} else if (askLeaveType.equals("09")) {
				// 補休假
				doc.setStatus("1");
				doc.setProcessDate(new Date());
				dao.saveObject(doc);
				
				/*
				if (remain < total) {
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "欲申請休假日數:" + total + "日, 超過剩餘日數:"
									+ remain + "日! "));
				} else {
					List<Object> overtimes = this.getPersonalVacation(idno,
							askLeaveType, startCal, endCal);
					AmsDocApply overtime = new AmsDocApply();
					List<AmsDocApply> resets = new ArrayList<AmsDocApply>();
					float balance = total;
					float ovt = 0f;
					for (Object obj : overtimes) {
						overtime = (AmsDocApply) obj;
						if (overtime.getStatus().equals(
								IConstants.AMSCodeDocProcessOK)
								|| overtime.getStatus().equals(
										IConstants.AMSCodeDocProcessForceOK)) {
							ovt = overtime.getTotalDay();
							if (overtime.getTotalHour() == (short) 4) {
								ovt += 0.5f;
							}
						} else if (overtime.getStatus().equals(
								IConstants.AMSCodeDocProcessHalfRest)) {
							ovt += 0.5f;
						}

						if (ovt >= balance) {
							doc.setStatus(IConstants.AMSCodeDocProcessOK);
							doc.setProcessDate(tdate);
							dao.saveObject(doc);

							if (balance > 0.5f) {
								balance = balance - (int) balance;
								overtime
										.setStatus(IConstants.AMSCodeDocProcessAllRest);
								
							} else {
								balance -= 0.5f;
								if (overtime.getTotalHour() == (short) 0) {
									overtime
											.setStatus(IConstants.AMSCodeDocProcessHalfRest);
								} else {
									overtime
											.setStatus(IConstants.AMSCodeDocProcessAllRest);
								}
							}
							dao.saveObject(overtime);
							for (AmsDocApply over : resets) {
								over
										.setStatus(IConstants.AMSCodeDocProcessAllRest);
								dao.saveObject(over);
							}
							break;
						} else {
							balance -= ovt;
							resets.add(overtime);
						}
					}
					if (balance > 0f) {
						remain = this.checkHaveVacation(idno, askLeaveType,
								startCal, endCal);
						msgs
								.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("MessageN1",
												"欲申請休假日數:" + total
														+ "日, 超過剩餘日數:" + remain
														+ "日! "));
					}
				}
			
			*/
			} else if (askLeaveType.equals("12")) {
				// 特休
				if (remain < total) {
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "欲申請休假日數:" + total + "日, 超過剩餘日數:"
									+ remain + "日! "));
				} else {
					List<Object> vacations = this.getPersonalVacation(idno,
							askLeaveType, startCal, endCal);
					AmsPersonalVacation vacation = new AmsPersonalVacation();
					float balance = total;
					List<AmsPersonalVacation> resets = new ArrayList<AmsPersonalVacation>();

					for (Object obj : vacations) {
						vacation = (AmsPersonalVacation) obj;
						if (vacation.getRemain() >= balance) {
							doc.setStatus(IConstants.AMSCodeDocProcessOK);
							doc.setProcessDate(tdate);
							dao.saveObject(doc);

							vacation.setRemain(vacation.getRemain() - balance);
							dao.saveObject(vacation);
							balance = 0f;

							for (AmsPersonalVacation vac : resets) {
								vac.setRemain(0f);
								dao.saveObject(vac);
							}
							break;
						} else {
							balance -= vacation.getRemain();
							resets.add(vacation);
						}
					}
					if (balance > 0f) {
						remain = this.checkHaveVacation(idno, askLeaveType,
								startCal, endCal);
						msgs
								.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("MessageN1",
												"欲申請休假日數:" + total
														+ "日, 超過剩餘日數:" + remain
														+ "日! "));
					}
				}
				
			} else {
				// 一般請假
				doc.setStatus(IConstants.AMSCodeDocProcessOK);
				doc.setProcessDate(tdate);
				dao.saveObject(doc);
			}
		} else if (docType.equals(IConstants.AMSDocOverTime)) {
			startCal.setTimeInMillis(doc.getStartDate().getTime());
			endCal.setTimeInMillis(doc.getEndDate().getTime());

			doc.setStatus(IConstants.AMSCodeDocProcessOK);
			doc.setProcessDate(tdate);
			dao.saveObject(doc);
		} else if (docType.equals(IConstants.AMSDocRevoke)) {
			AmsDocApply revoked = this.getDocApplyByOid((this.getRevokedDoc(doc
					.getOid())).getRevokedOid());

			revoked.setStatus(IConstants.AMSCodeDocRevokeOK);
			revoked.setProcessDate(tdate);
			dao.saveObject(revoked);
			doc.setStatus(IConstants.AMSCodeDocProcessOK);
			doc.setProcessDate(tdate);
			dao.saveObject(doc);

			// 回補已扣除休假天數
			float total = 0f, remain = 0f;

			total = revoked.getTotalDay();
			if (revoked.getTotalHour() == (short) 4) {
				total += 0.5f;
			}

			askLeaveType = revoked.getAskLeaveType();
			if (askLeaveType.equals("07")) {
				// 年假
				List<Object> vacations = this.getPersonalVacation(idno,
						askLeaveType, startCal, endCal);
				AmsPersonalVacation vacation = (AmsPersonalVacation) vacations
						.get(0);
				remain = vacation.getRemain();
				if ((remain + total) > vacation.getDays()) {
					vacation.setRemain((float) vacation.getDays());
				} else {
					vacation.setRemain(remain + total);
				}
				dao.saveObject(vacation);
			} else if (askLeaveType.equals("09")) {
				// 補休假
				List<AmsDocApply> overtimes = this.getOverTimeRested(idno,
						startCal, endCal);
				List<AmsDocApply> resets = new ArrayList<AmsDocApply>();
				float balance = total;
				float ovt = 0f;
				for (AmsDocApply overtime : overtimes) {
					if (overtime.getStatus().equals(
							IConstants.AMSCodeDocProcessAllRest)) {
						ovt = overtime.getTotalDay();
						if (overtime.getTotalHour() == (short) 4) {
							ovt += 0.5f;
						}
					} else if (overtime.getStatus().equals(
							IConstants.AMSCodeDocProcessHalfRest)) {
						ovt += 0.5f;
					}

					if (ovt >= balance) {
						if (balance > 0.5f) {
							balance = balance - (int) balance;
							overtime.setStatus(IConstants.AMSCodeDocProcessOK);
							/*
							 * if(balance > 0f){ balance -= 0.5f;
							 * if(overtime.getTotalHour() == (short)0){
							 * overtime.
							 * setStatus(IConstants.AMSCodeDocProcessHalfRest);
							 * }else{overtime.setStatus(IConstants.
							 * AMSCodeDocProcessAllRest); } }
							 */
						} else { // 半天
							balance -= 0.5f;
							if (overtime.getTotalHour() == (short) 0) { // 加班一天
								overtime
										.setStatus(IConstants.AMSCodeDocProcessHalfRest);
							} else { // 加班半天,完全補回
								overtime
										.setStatus(IConstants.AMSCodeDocProcessOK);
							}
						}
						dao.saveObject(overtime);
						for (AmsDocApply over : resets) {
							over.setStatus(IConstants.AMSCodeDocProcessOK);
							dao.saveObject(over);
						}
						break;
					} else {
						balance -= ovt;
						resets.add(overtime);
					}
				}

			} else if (askLeaveType.equals("12")) {
				// 特休
				List<Object> vacations = this.getPersonalVacation(idno,
						askLeaveType, startCal, endCal);
				Object[] vacs = vacations.toArray();
				AmsPersonalVacation vacation = new AmsPersonalVacation();
				float balance = total;
				List<AmsPersonalVacation> resets = new ArrayList<AmsPersonalVacation>();

				for (int j = vacs.length - 1; j >= 0; j--) {
					vacation = (AmsPersonalVacation) vacs[j];
					if (vacation.getDays().floatValue() != vacation.getRemain()
							.floatValue()) {
						if ((vacation.getDays() - vacation.getRemain()) >= balance) {
							if ((vacation.getRemain() + balance) >= vacation
									.getDays()) {
								vacation.setRemain((float) vacation.getDays());
							} else {
								vacation.setRemain(vacation.getRemain()
										+ balance);
							}
							dao.saveObject(vacation);
							balance = 0f;

							for (AmsPersonalVacation vac : resets) {
								vac.setRemain((float) vac.getDays());
								dao.saveObject(vac);
							}
							break;
						} else {
							balance -= vacation.getRemain();
							resets.add(vacation);
						}
					}
				}
			} else {
				// 一般請假不做任何回補
			}

		} else if (docType.equals(IConstants.AMSDocRepair)) {
			int cnt = 0, knt = 0;
			if (doc.getStartDate() != null) {
				cnt++;
			}
			if (doc.getEndDate() != null) {
				cnt++;
			}
			java.sql.Time jsqlT = null;
			Calendar cal = Calendar.getInstance();

			if (doc.getStartDate() != null) {
				cal.setTime(doc.getStartDate());
				knt = this.getRepairDocCount(doc.getIdno(), cal);
				knt += cnt;
				if (knt < 3 || force) {
					AmsWorkdate work = this.findAmsWorkdateBy(doc.getIdno(),
							doc.getStartDate());
					if (work != null) {
						jsqlT = java.sql.Time.valueOf(cal
								.get(Calendar.HOUR_OF_DAY)
								+ ":"
								+ cal.get(Calendar.MINUTE)
								+ ":"
								+ cal.get(Calendar.SECOND));

						work.setRealIn(jsqlT);
						work.setType("p");
						dao.saveObject(work);
					}
				} else {
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "未刷卡補登次數超過3次!"));
				}
			}
			if (doc.getEndDate() != null && msgs.isEmpty()) {
				cal.setTime(doc.getEndDate());
				knt = this.getRepairDocCount(doc.getIdno(), cal);
				knt += cnt;

				if (knt < 3 || force) {
					AmsWorkdate work = this.findAmsWorkdateBy(doc.getIdno(),
							doc.getEndDate());
					if (work != null) {
						jsqlT = java.sql.Time.valueOf(cal
								.get(Calendar.HOUR_OF_DAY)
								+ ":"
								+ cal.get(Calendar.MINUTE)
								+ ":"
								+ cal.get(Calendar.SECOND));

						work.setRealOut(jsqlT);
						dao.saveObject(work);
					}
				} else {
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "未刷卡補登次數超過3次!"));
				}
			}
			if (msgs.isEmpty()) {
				doc.setStatus(IConstants.AMSCodeDocProcessOK);
				doc.setProcessDate(tdate);
				dao.saveObject(doc);
			}
		}

		if (!msgs.isEmpty()) {
			doc.setStatus(IConstants.AMSCodeDocProcessReject);
			doc.setProcessDate(tdate);
			dao.saveObject(doc);
		}

		return msgs;
	}

	public List<AmsDocApply> getDocApplyByStatus(String status,
			Calendar startCal, Calendar endCal) {
		String hql = "From AmsDocApply Where status=? And processDate Between ? And ? Order By processDate";
		List<AmsDocApply> docList = dao.submitQueryP(hql, new Object[] {
				status, startCal.getTime(), endCal.getTime() });
		return docList;
	}
/*
 * 查是否有重覆的請單
 * @param idno 身份證
 * @param docType 假單類型
 * @param startCal 開始日期
 * @param endCal 結束日期
 * @return 列出假單
 */
	public List<AmsDocApply> getDocApplyByDateRange(String idno, String docType,
			Calendar startCal, Calendar endCal) {
		AmsDocApply doc = null;
		List<AmsDocApply> docList = new ArrayList<AmsDocApply>();

		String hql = "From AmsDocApply Where idno=? And docType=? And ";
		if (docType.equals(IConstants.AMSDocRepair)) {
			Date start = null;
			Date end = null;
			Calendar cal = Calendar.getInstance();

			if (startCal != null) {
				cal.setTimeInMillis(startCal.getTimeInMillis());
			} else if (endCal != null) {
				cal.setTimeInMillis(endCal.getTimeInMillis());
			} else {
				return null;
			}
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			start = cal.getTime();
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			end = cal.getTime();
			hql += "((startDate Between ? And ?) OR (endDate Between ? And ?))";

			Object[] parameters = new Object[] { idno, docType, start, end,
					start, end };

			docList = dao.submitQueryP(hql, parameters);
		} else {
			Date start = startCal.getTime();
			Date end = endCal.getTime();

			hql += "((startDate Between ? And ?) OR (endDate Between ? And ?))";
			//hql += "((? Between startDate And endDate) OR (? Between startDate And endDate) OR (? <= startDate And ?>=endDate))";

			Object[] parameters = new Object[] { idno, docType, start, end,
					start, end };

			docList = dao.submitQueryP(hql, parameters);

		}

		if (!docList.isEmpty()) {
			doc = docList.get(0);
		}

		return docList;
	}

	public Map calAskLeaveDays(String idno, String askLeaveType,
			Calendar startCal, Calendar endCal) {
		Map retMap = new HashMap();
		int[] days = { 0, 0, 0, 0 };
		startCal.set(Calendar.MILLISECOND, 0);
		endCal.set(Calendar.MILLISECOND, 0);
		startCal.set(Calendar.SECOND, 0);
		endCal.set(Calendar.SECOND, 0);
		if (startCal.compareTo(endCal) == 0) {
			retMap.put("days", days);
			return retMap;
		} else if (startCal.compareTo(endCal) > 0) {
			retMap.put("err1", "請假開始時間應小於結束時間!");
			return retMap;
		}
		int startHour = startCal.get(Calendar.HOUR_OF_DAY);
		int startMinute = startCal.get(Calendar.MINUTE);
		int endHour = endCal.get(Calendar.HOUR_OF_DAY);
		int endMinute = endCal.get(Calendar.MINUTE);

		String hql = "From AmsWorkdate Where ";
		String sql = "";
		int eat = 0;

		Calendar sCal = Calendar.getInstance();
		Calendar eCal = Calendar.getInstance();
		Calendar tCal = Calendar.getInstance();
		sCal.setTimeInMillis(startCal.getTimeInMillis());
		eCal.setTimeInMillis(endCal.getTimeInMillis());
		tCal.setTimeInMillis(sCal.getTimeInMillis());
		sCal.set(Calendar.HOUR_OF_DAY, 0);
		sCal.set(Calendar.MINUTE, 0);
		sCal.set(Calendar.SECOND, 0);
		eCal.set(Calendar.HOUR_OF_DAY, 0);
		eCal.set(Calendar.MINUTE, 0);
		eCal.set(Calendar.SECOND, 0);

		List<AmsWorkdate> workList = dao
				.submitQueryP(
						"From AmsWorkdate Where id.idno=? And id.wdate between ? And ? Order by id",
						new Object[] { idno, sCal.getTime(), eCal.getTime() });

		eCal.set(Calendar.HOUR_OF_DAY, 23);
		int[] period = Toolket.timeTransfer(sCal, eCal);
		int rcnt = period[0] + 1;

		if (rcnt != workList.size()) {
			retMap.put("err1", "請通知人事室設定你的排班班表!");
			/*retMap.put("err1", "差勤紀錄檔AmsWorkDate筆數有誤:" + workList.size()
					+ "筆,應有:" + (period[0] + 1) + "筆!");*/
			return retMap;
		} else {
			Calendar[][] cals = new Calendar[rcnt][2];
			String[] dateType = new String[rcnt];
			String[] shiftType = new String[rcnt];

			int cnt = 0;
			for (AmsWorkdate wd : workList) {
				cals[cnt][0] = Calendar.getInstance();
				cals[cnt][1] = Calendar.getInstance();
				cals[cnt][0].setTime(wd.getId().getWdate());
				cals[cnt][1].setTime(wd.getId().getWdate());
				if (wd.getSetIn() != null) {
					cals[cnt][0].set(Calendar.HOUR_OF_DAY, wd.getSetIn()
							.getHours());
					cals[cnt][0].set(Calendar.MINUTE, wd.getSetIn()
							.getMinutes());
					cals[cnt][0].set(Calendar.SECOND, wd.getSetIn()
							.getSeconds());
					cals[cnt][0].set(Calendar.MILLISECOND, 0);
				}
				if (wd.getSetOut() != null) {
					cals[cnt][1].set(Calendar.HOUR_OF_DAY, wd.getSetOut()
							.getHours());
					cals[cnt][1].set(Calendar.MINUTE, wd.getSetOut()
							.getMinutes());
					cals[cnt][1].set(Calendar.SECOND, wd.getSetOut()
							.getSeconds());
					cals[cnt][1].set(Calendar.MILLISECOND, 0);
				}
				dateType[cnt] = wd.getDateType();
				shiftType[cnt] = wd.getShift();
				cnt++;
			}

			if (dateType[0].equalsIgnoreCase("h")) {
				retMap.put("err4", "請假開始日為休假日,請由下一上班日開始請假!");
			} else if (cals[0][0].compareTo(startCal) > 0) {
				retMap.put("err2", "請假開始時間必須大於上班開始時間!請假首日上班開始時間為:"
						+ cals[0][0].get(Calendar.HOUR_OF_DAY) + "時"
						+ cals[0][0].get(Calendar.MINUTE) + "分");
			}
			if (cals.length > 1 && dateType[cnt - 1].equalsIgnoreCase("h")) {
				retMap.put("err4", "請假結束日為休假日,該日無須請假!");
			} else if (cals[cnt - 1][1].compareTo(endCal) < 0) {
				retMap.put("err3", "請假結束時間必須小於上班結束時間!請假最後一日上班結束時間為:"
						+ cals[cnt - 1][1].get(Calendar.HOUR_OF_DAY) + "時"
						+ cals[cnt - 1][1].get(Calendar.MINUTE) + "分");
			}
			// 請假資料有問題
			if (!retMap.isEmpty()) {
				return retMap;
			}

			if (cnt == 1) { // 請假在一日以內
				days = Toolket.timeTransfer(startCal, endCal);
				if (startHour == cals[0][0].get(Calendar.HOUR_OF_DAY)
						&& startMinute == cals[0][0].get(Calendar.MINUTE)
						&& endHour == cals[0][1].get(Calendar.HOUR_OF_DAY)
						&& endMinute == cals[0][1].get(Calendar.MINUTE)) {
					// 請假時間等於應上應下
					days[0] = 1;
					days[1] = 0;
					days[2] = 0;
					days[3] = 0;
				} else {
					try{
						eat = getWorkShiftEat(shiftType[0]);//去他媽的等於英字母z是什麼意思
					}catch(Exception e){
						eat = 1;//去他媽的英文字母當請他媽半天
					}
					
					//if(eat)
					if (eat == 1
							&& ((startHour == 12 && startMinute == 0) || (startHour < 12 && (endHour > 12 || (endHour == 12 && endMinute == 30))))) {
						// 小於中午12:00或等於12:00,扣除午休時間
						if (days[2] >= 30) {
							days[2] -= 30 * eat;
						} else {
							days[1] -= 1;
							days[2] = 30;
						}
					}
					// 修正寒暑假請休年假或特休假.補休之時數,由3小時調整為4小時
					if (days[1] == 3
							&& endHour == cals[0][1].get(Calendar.HOUR_OF_DAY)
							&& endMinute == cals[0][1].get(Calendar.MINUTE)
							&& (askLeaveType.equals("07")
									|| askLeaveType.equals("09") || askLeaveType
									.equals("12"))
							&& (shiftType[0].equals("D1") || shiftType[0]
									.equals("N1"))) {
						days[1] = 4;
					}
				}
			} else {
				for (int i = 0; i < cnt; i++) {
					// 請假一日以上
					if (i == 0) {
						if (startHour == cals[0][0].get(Calendar.HOUR_OF_DAY)
								&& startMinute == cals[0][0]
										.get(Calendar.MINUTE)) {
							days[0] = 1;
						} else {
							period = Toolket.timeTransfer(startCal, cals[0][1]);
							eat = getWorkShiftEat(shiftType[0]);
							if (eat == 1
									&& (startHour < 12 || (startHour == 12 && startMinute == 0))) {
								// 小於中午12:00或等於12:00,扣除午休時間
								if (period[2] >= 30) {
									period[2] -= 30 * eat;
								} else {
									period[1] -= 1;
									period[2] = 30;
								}
							}
							days[1] = period[1];
							days[2] = period[2];

							// 修正寒暑假請休年假或特休假.補休之時數,由3小時調整為4小時
							if (days[1] == 3
									&& (askLeaveType.equals("07")
											|| askLeaveType.equals("09") || askLeaveType
											.equals("12"))
									&& (shiftType[0].equals("D1") || shiftType[0]
											.equals("N1"))) {
								days[1] = 4;
							}

						}
					} else if (i == cnt - 1) {
						if (endHour == cals[cnt - 1][1]
								.get(Calendar.HOUR_OF_DAY)
								&& endMinute == cals[cnt - 1][1]
										.get(Calendar.MINUTE)) {
							days[0] += 1;
						} else {
							period = Toolket.timeTransfer(cals[cnt - 1][0],
									endCal);
							eat = getWorkShiftEat(shiftType[cnt - 1]);
							if (eat == 1
									&& (endHour > 12 || (endHour == 12 && endMinute == 30))) {
								// 過中午12:00或等於12:30,扣除午休時間
								if (period[2] >= 30) {
									period[2] -= 30 * eat;
								} else {
									period[1] -= 1;
									period[2] = 30;
								}
							}
							days[1] += period[1];
							days[2] += period[2];
						}
					} else {
						if (dateType[i].equalsIgnoreCase("w")) {
							days[0] += 1;
						}
					}

				}
				if (days[2] >= 60) {
					int k = days[2] % 60;
					days[1] += (days[2] - k) / 60;
					days[2] = k;
				}
				if (days[1] >= 8) {
					int k = days[1] % 8;
					days[0] += (days[1] - k) / 8;
					days[1] = k;
				}
			}

			retMap.put("days", days);
		} // end if((period[0] + 1) != workList.size())

		return retMap;
	}

	public Map calOverTimeDays(String idno, Calendar startCal, Calendar endCal) {
		Map retMap = new HashMap();
		int[] days = { 0, 0, 0, 0 };
		startCal.set(Calendar.MILLISECOND, 0);
		endCal.set(Calendar.MILLISECOND, 0);
		startCal.set(Calendar.SECOND, 0);
		endCal.set(Calendar.SECOND, 0);
		if (startCal.compareTo(endCal) == 0) {
			retMap.put("days", days);
			return retMap;
		} else if (startCal.compareTo(endCal) > 0) {
			retMap.put("err1", "加班開始時間應大於結束時間!");
			return retMap;
		}

		int startHour = startCal.get(Calendar.HOUR_OF_DAY);
		int startMinute = startCal.get(Calendar.MINUTE);
		int endHour = endCal.get(Calendar.HOUR_OF_DAY);
		int endMinute = endCal.get(Calendar.MINUTE);

		int eat = 0;

		Calendar sCal = Calendar.getInstance();
		Calendar eCal = Calendar.getInstance();
		Calendar tCal = Calendar.getInstance();
		sCal.setTimeInMillis(startCal.getTimeInMillis());
		eCal.setTimeInMillis(endCal.getTimeInMillis());
		tCal.setTimeInMillis(sCal.getTimeInMillis());
		sCal.set(Calendar.HOUR_OF_DAY, 0);
		sCal.set(Calendar.MINUTE, 0);
		sCal.set(Calendar.SECOND, 0);
		sCal.set(Calendar.MILLISECOND, 0);
		eCal.set(Calendar.HOUR_OF_DAY, 0);
		eCal.set(Calendar.MINUTE, 0);
		eCal.set(Calendar.SECOND, 0);
		eCal.set(Calendar.MILLISECOND, 0);

		// 加班不跨日
		if (sCal.compareTo(eCal) != 0) {
			retMap.put("err1", "加班起迄日期應為同一日,或請逐日填寫加班單!");
			return retMap;
		}
		List<AmsWorkdate> workList = dao.submitQueryP(
		"From AmsWorkdate Where id.idno=? And id.wdate=? Order by id",
		new Object[] { idno, sCal.getTime() });

		eCal.set(Calendar.HOUR_OF_DAY, 23);
		
		
		
		if (workList.isEmpty()) {
			retMap.put("err1", "找不到 " + startCal.get(Calendar.YEAR) + "-"
					+ (startCal.get(Calendar.MONTH)+1) + "-"
					+ startCal.get(Calendar.DATE) + " 之差勤紀錄檔,請洽人事室,分機112!");
			return retMap;
		} else {
			Calendar[] cals = new Calendar[2];
			String dateType = "";
			String shiftType = "";

			AmsWorkdate wd = workList.get(0);
			cals[0] = Calendar.getInstance();
			cals[1] = Calendar.getInstance();
			cals[0].setTime(wd.getId().getWdate());
			cals[1].setTime(wd.getId().getWdate());
			if (wd.getSetIn() != null) {
				cals[0].set(Calendar.HOUR_OF_DAY, wd.getSetIn().getHours());
				cals[0].set(Calendar.MINUTE, wd.getSetIn().getMinutes());
				cals[0].set(Calendar.SECOND, wd.getSetIn().getSeconds());
				cals[0].set(Calendar.MILLISECOND, 0);
			}
			if (wd.getSetOut() != null) {
				cals[1].set(Calendar.HOUR_OF_DAY, wd.getSetOut().getHours());
				cals[1].set(Calendar.MINUTE, wd.getSetOut().getMinutes());
				cals[1].set(Calendar.SECOND, wd.getSetOut().getSeconds());
				cals[1].set(Calendar.MILLISECOND, 0);
			}
			dateType = wd.getDateType();
			shiftType = wd.getShift();

			if (dateType.equalsIgnoreCase("w")) {
				if (startCal.compareTo(cals[0]) >= 0
						&& startCal.compareTo(cals[1]) < 0) {
					retMap.put("err2", "加班開始時間必須為非上班時間!");
				}
				if (endCal.compareTo(cals[0]) > 0
						&& endCal.compareTo(cals[1]) <= 0) {
					retMap.put("err3", "加班結束時間必須為非上班時間!");
				}

			}
			// 請加班資料有問題
			if (!retMap.isEmpty()) {
				return retMap;
			}

			days = Toolket.timeTransfer(startCal, endCal);
			int limit = 8;
			/*
			 * 2010/02/10 人事室主任表示加班一日均為8小時
			 */
			// if (shiftType.equalsIgnoreCase("D1") ||
			// shiftType.equalsIgnoreCase("N1")) {
			// 寒暑假加班一日為7小時
			// limit = 7;
			// }
			if (days[1] >= limit) {
				days[0] = 1;
				days[1] = 0;
				days[2] = 0;
				days[3] = 0;
			} else if (days[1] >= 4) {
				days[0] = 0;
				days[1] = 4;
				days[2] = 0;
				days[3] = 0;
			} else {
				days[0] = 0;
				days[1] = 0;
				days[2] = 0;
				days[3] = 0;
			}
		}

		retMap.put("days", days);

		return retMap;
	}

	public AmsMeetingAskLeave getDocMeetingApplyBySn(String sn) {
		AmsMeetingAskLeave doc = null;

		List<AmsMeetingAskLeave> docList = dao
				.submitQuery("From AmsMeetingAskLeave Where askCode='" + sn
						+ "'");
		if (!docList.isEmpty()) {
			doc = docList.get(0);
		}
		return doc;

	}

	public ActionMessages DocMeetingExamine(AmsMeetingAskLeave doc) {
		ActionMessages errs = new ActionMessages();
		Calendar now = Calendar.getInstance();
		String sql = "Select * From AMS_MeetingData Where MeetingOid="
				+ doc.getMeetingOid();
		sql += " And Idno='" + doc.getUserIdno() + "'";

		List<Map> docs = jdbcDao.findAnyThing(sql);

		if (!docs.isEmpty()) {
			try {
				sql = "Update AMS_MeetingData set Sn='" + doc.getAskCode()
						+ "'";
				sql += ", Status='" + doc.getAskleaveId() + "'";
				sql += " Where MeetingOid=" + doc.getMeetingOid()
						+ " And Idno='" + doc.getUserIdno() + "'";
				jdbcDao.updateAnyThing(sql);

				doc.setStatus("1");
				doc.setProcessDate(now.getTime());
				dao.saveObject(doc);
			} catch (Exception e) {
				errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", e.toString()));
			}
		} else {
			errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "找不到重大集會資料!"));
		}

		return errs;
	}

	public void setDocMeetingAskLeaveExtraData(AmsMeetingAskLeave doc) {
		doc.setEmplName(Toolket.getEmplName(doc.getUserIdno()));
		AmsMeeting meeting = this.findAmsMeetingBy(doc.getMeetingOid());
		doc.setMeetingName(meeting.getName());
		doc.setMeetingDate(meeting.getMeetingDate());
		doc.setAskleaveName("");
		if (doc.getAskleaveId() != null) {
			AmsAskLeave askleave = getAskLeaveName(doc.getAskleaveId());
			if (askleave != null) {
				doc.setAskleaveName(askleave.getName());
			}
		}
	}

	public List<AmsMeetingAskLeave> getDocMeetingAskLeaveByStatus(
			String status, Calendar startCal, Calendar endCal) {
		String hql = "From AmsMeetingAskLeave Where status=? And processDate Between ? And ? Order By processDate";
		List<AmsMeetingAskLeave> docList = dao.submitQueryP(hql, new Object[] {
				status, startCal.getTime(), endCal.getTime() });
		return docList;
	}

	public List<Empl> checkNoWorkDate(Calendar startCal, Calendar endCal) {
		int sYear = startCal.get(Calendar.YEAR);
		int sMonth = startCal.get(Calendar.MONTH);
		int sDay = startCal.get(Calendar.DATE);
		int eYear = endCal.get(Calendar.YEAR);
		int eMonth = endCal.get(Calendar.MONTH);
		int eDay = endCal.get(Calendar.DATE);

		Session session = dao.getHibernateSession();
		String sql = "Select e.* From Empl e Where e.category='3' And e.idno not in ";
		sql += "(Select idno From AmsWorkDate Where wdate between '" + sYear
				+ "-" + sMonth + "-" + sDay + "' And '" + eYear + "-" + eMonth
				+ "-" + eDay + "' group by idno)";

		List<Empl> empls = session.createSQLQuery(sql).addEntity(Empl.class)
				.list();

		return empls;
	}

	public List<AmsMeeting> findAmsMeetingBy(Calendar startCal, Calendar endCal) {
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.SECOND, 0);
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);

		String hql = "From AmsMeeting Where meetingDate between ? And ? Order by meetingDate";

		List<AmsMeeting> meetings = dao.submitQueryP(hql, new Object[] {
				startCal.getTime(), endCal.getTime() });

		return meetings;
	}

	private int getWorkShiftEat(String shiftId) {
		int eat = 0;
		String sql = "Select eat From AMS_ShiftTime Where id='" + shiftId + "'";
		List eats = jdbcDao.findAnyThing(sql);
		if (!eats.isEmpty()) {
			eat = Integer.parseInt(((Map) eats.get(0)).get("eat").toString());
		}
		return eat;
	}

	/**
	 * 取得下一個申請單的序號 {識別碼(1) + 身分證後5碼 + 西元年後2碼 + 流水號(5)}
	 * 
	 * @param docType 申請單類別
	 * @param idno 身分證號
	 * 
	 * @return 新的序號
	 */
	private String getNewDOcSN(String docType, String idno) {
		int number = 1;
		String YY = ("" + Calendar.getInstance().get(Calendar.YEAR))
				.substring(2);
		String prefix = docType + idno.substring(5) + YY;
		String sql = "Select sn From AMS_DocApply Where sn like '" + prefix
				+ "%' Order by sn DESC";
		List retList = jdbcDao.findAnyThing(sql);

		if (!retList.isEmpty()) {
			String lastSn = ((Map) retList.get(0)).get("sn").toString();
			number = Integer.parseInt(lastSn.substring(8)) + 1;			
		}

		String snum = "" + number;
		String rets = snum;
		for (int i = 1; i < 6 - snum.length(); i++) {
			rets = "0" + rets;
		}

		return prefix + rets;
	}

}