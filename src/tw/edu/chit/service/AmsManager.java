package tw.edu.chit.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.dao.AmsDAO;
import tw.edu.chit.model.AmsAskLeave;
import tw.edu.chit.model.AmsDocApply;
import tw.edu.chit.model.AmsHoliday;
import tw.edu.chit.model.AmsMeeting;
import tw.edu.chit.model.AmsMeetingAskLeave;
import tw.edu.chit.model.AmsPersonalVacation;
import tw.edu.chit.model.AmsRevokedDoc;
import tw.edu.chit.model.AmsShiftGroup;
import tw.edu.chit.model.AmsShiftTime;
import tw.edu.chit.model.AmsWorkdateData;
import tw.edu.chit.model.AmsWorkdateInfo;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.domain.UserCredential;

public interface AmsManager {

	public void setAmsDAO(AmsDAO dao);
	
	@SuppressWarnings("unchecked")
	public Object findObject(Class clazz, Serializable id);
	
	@SuppressWarnings("unchecked")
	public List findAnyThing(String sql);
	
	/**
	 * 
	 * @param entity
	 * @param expression
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List findSQLWithCriteria(Class entity,
			SimpleExpression... expression) throws DataAccessException;
	
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
			Criterion... criterion) throws DataAccessException;

	/**
	 * 取得個人申請單據資料
	 * 
	 * @param idno 身分證字號
	 * @return 單據資料 List<AmsDocApply>
	 */
	public List<AmsDocApply> getDocApplyByIdno(String idno, String type);
	
	/**
	 * 刪除請假加班補登銷假等單據資料
	 * 
	 * @param selDocs 單據資料
	 * @return 未刪資料
	 */
	public List<AmsDocApply> delDocApply(List<AmsDocApply> selDocs);
		
	/**
	 * 查詢AmsAskLeave物件(假別)
	 * 
	 * @param aal AmsAskLeave Object
	 * @return List of AmsAskLeave Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsAskLeave> findAmsAskLeaveBy(AmsAskLeave aal)
			throws DataAccessException;
	
	/**
	 * 查詢AmsDocApply物件(請假)
	 * 
	 * @param ada AmsDocApply Object
	 * @return List of AmsDocApply Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsDocApply> findAmsDocApplyBy(AmsDocApply ada)
			throws DataAccessException;
	
	/**
	 * 查詢AmsDocApply物件(某日請假)
	 * 
	 * @param ads AmsDocApply Object
	 * @param d Some Date
	 * @return List of AmsDocApply Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsDocApply> findAmsDocApplyBy(AmsDocApply ads, Date d)
			throws DataAccessException;
	
	/**
	 * 
	 * @param ads
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 */
	public List<AmsDocApply> findAmsDocApplyBy(Date from, Date to)
			throws DataAccessException;
	
	/**
	 * 
	 * @param ads
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 */
	public List<AmsDocApply> findAmsDocApplyBy(AmsDocApply ads, Date from,
			Date to) throws DataAccessException;

	/**
	 * 
	 * @param ads
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 */
	public List<AmsMeetingAskLeave> findAmsMeetingAskLeaveBy(Date from, Date to)
			throws DataAccessException;
	
	public List<AmsMeetingAskLeave> findAmsMeetingAskLeavesBy(
			AmsMeetingAskLeave amal) throws DataAccessException;
	
	/**
	 * 查詢AmsHoliday物件(假日)
	 * 
	 * @param ah AmsHoliday Object
	 * @return List of AmsHoliday Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsHoliday> findAmsHolidayBy(AmsHoliday ah)
			throws DataAccessException;
	
	/**
	 * 查詢AmsShiftGroup物件
	 * 
	 * @param asg AmsShiftGroup Object
	 * @return List of AmsShiftGroup Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsShiftGroup> findAmsShiftGroupBy(AmsShiftGroup asg)
			throws DataAccessException;
	
	/**
	 * 查詢AmsShiftTime物件
	 * 
	 * @param ast AmsShiftTime Object
	 * @return List of AmsShiftTime Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsShiftTime> findAmsShiftTimeBy(AmsShiftTime ast)
			throws DataAccessException;
	
	/**
	 * 查詢AmsPersonalVacation物件
	 * 
	 * @param apv AmsPersonalVacation Object
	 * @return List of AmsPersonalVacation Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsPersonalVacation> findAmsPersonalVacationBy(
			AmsPersonalVacation apv) throws DataAccessException;
	
	/**
	 * 查詢AmsMeeting物件
	 * 
	 * @param amsMeeting
	 * @return
	 * @throws DataAccessException
	 */
	public List<AmsMeeting> findAmsMeetingBy(AmsMeeting amsMeeting)
			throws DataAccessException;
	
	/**
	 * 
	 * @param empl
	 * @return
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	public List<AmsWorkdateInfo> findAmsWorkdateDataBy(Empl empl,
			String schoolYear) throws DataAccessException, SQLException;

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
	public Map<String, Object> findAmsWorkdateDataBy(Empl empl,
			AmsWorkdateData aw, Date from, Date to, Integer schoolYear)
			throws DataAccessException, SQLException;
	
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
			Date to) throws DataAccessException, SQLException, Exception;
	
	/**
	 * 
	 * @param amsMeeting
	 * @param empls
	 * @throws DataAccessException
	 */
	public void txSaveAmsMeeting(AmsMeeting amsMeeting, List<Empl> empls)
			throws DataAccessException;
	
	/**
	 * 
	 * @param oid
	 * @throws DataAccessException
	 */
	public void txDeleteAmsMeeting(Integer oid) throws DataAccessException;
	
	/**
	 * 
	 * @param oid
	 * @return
	 * @throws DataAccessException
	 */
	public AmsMeeting findAmsMeetingBy(Integer oid) throws DataAccessException;
	
	/**
	 * 取得申請單據資料 By Oid
	 * 
	 * @param Oid : Doc Oid
	 * @return AmsDocApply
	 */
	public AmsDocApply getDocApplyByOid(int Oid);
	
	/**
	 * 儲存新增或修改的申請單
	 * 
	 * @param form 輸入表單(DynaActionForm)
	 * @return ActionMessages
	 */
	public ActionMessages saveDocByForm(DynaActionForm form, UserCredential user);
	
	/**
	 * 依請假型別取得假別資料
	 * 
	 * @param askLeaveType 假別
	 * @return AmsAskLeave 假別資料
	 */
	public AmsAskLeave getAskLeaveName(String askLeaveType);
	
	/**
	 * 取得未刷卡補登之次數
	 * 
	 * @param idno 身分證號
	 * @param startCal 補登日期
	 * 
	 * @return 次數
	 */
	public int getRepairDocCount(String idno, Calendar startCal);
	
	/**
	 * 取得所有的假別
	 * 
	 * @return 假別資料
	 */
	public List<AmsAskLeave> getAllAskLeave();
	
	/**
	 * 取得可休假日數
	 * 
	 * @param idno 身分證字號
	 * @param askLeaveType 假別(年休:7,補休:9,特休:12)
	 * @return 可休假日數
	 */
	public float checkHaveVacation(String idno, String askLeaveType, Calendar startCal, Calendar endCal);
	
	public void setDocExtraData(AmsDocApply doc);
	
	/**
	 * 取得銷假單紀錄
	 * @param oid 銷假單Oid
	 * @return 銷假單紀錄
	 */
	public AmsRevokedDoc getRevokedDoc(int oid);
	
	/**
	 * 取得銷假單紀錄
	 * 
	 * @param oid 請假單Oid
	 * @return 銷假單紀錄
	 */
	public AmsRevokedDoc getRevokedDocByAskLeaveDoc(int oid);
	
	/**
	 * 檢查單據是否已超過列印或人事審核等期限
	 * @param doc 單據
	 * @return 是否已過期
	 */
	public boolean isExpireDoc(AmsDocApply doc);
	
	/**
	 * 取得申請單據資料 By Serial No.
	 * @param sn 條碼
	 * @return 申請單
	 */
	public AmsDocApply getDocApplyBySn(String sn);
	
	/**
	 * 申請單審查(人事單位使用)
	 * 
	 * @param doc 申請單
	 * @param force 強迫核准
	 * 
	 * @return 審查結果信息
	 */
	public ActionMessages DocExamine(AmsDocApply doc, boolean force);
	
	/**
	 * 查詢申請單(依處理狀態)
	 * 
	 * @param status 依處理狀態
	 * @param startCal 開始日期時間
	 * @param endCal 結束日期時間
	 * 
	 * @return 申請單
	 */
	public List<AmsDocApply> getDocApplyByStatus(String status, Calendar startCal, Calendar endCal);
	
	/**
	 * 取得申請單
	 * 
	 * @param idno 身分證號
	 * @param docType 申請單類別
	 * @param startCal 起始日期時間
	 * @param endCal 結束日期時間
	 * @return 申請單
	 */
	public List<AmsDocApply> getDocApplyByDateRange( String idno, String docType, Calendar startCal, Calendar endCal);
	
	/**
	 * 計算請假日數
	 * 
	 * @param idno 請假人身分證號
	 * @param askLeaveType 請假類別
	 * @param startCal 開始日期時間
	 * @param endCal 結束日期時間
	 * 
	 * @return Map(包含 String err?:錯誤訊息 或 int[] days:請假日時分)
	 */
	public Map calAskLeaveDays(String idno, String askLeaveType, Calendar startCal, Calendar endCal);
	
	/**
	 * 計算並檢查加班日數
	 * 
	 * @param idno 身分證號
	 * @param startCal 開始日期時間
	 * @param endCal 結束日期時間
	 * @return Map(包含 String err?:錯誤訊息 或 int[] days:加班日時分)
	 */
	public Map calOverTimeDays(String idno, Calendar startCal, Calendar endCal);
	
	/**
	 * 以單號取得重大會議請假資料
	 * 
	 * @param sn 單號
	 * 
	 * @return 重大會議假單資料
	 */
	public AmsMeetingAskLeave getDocMeetingApplyBySn(String sn);
	
	/**
	 * 重大會議請假資料審核
	 * 
	 * @param doc 重大會議請假單
	 * 
	 * @return 處理訊息
	 */
	public ActionMessages DocMeetingExamine(AmsMeetingAskLeave doc);
	
	/**
	 * 設定重大會議請假單相關資料
	 * 
	 * @param doc 重大會議請假單
	 */
	public void setDocMeetingAskLeaveExtraData(AmsMeetingAskLeave doc);
	
	/**
	 * 取得重大會議假單資料
	 * 
	 * @param status 假單狀態 0:未核 1:已核准
	 * @param startCal 起始日期
	 * @param endCal 結束日期
	 * 
	 * @return 重大會議假單資料
	 */
	public List<AmsMeetingAskLeave> getDocMeetingAskLeaveByStatus(String status, 
			Calendar startCal, Calendar endCal);
	
	/**
	 * 檢查是否有員工刷卡資料未設定
	 * 
	 * @param startCal 起始日期
	 * @param endCal 結束日期
	 * 
	 * @return 未設定員工之基本資料
	 */
	public List<Empl> checkNoWorkDate(Calendar startCal, Calendar endCal);
	
	/**
	 * 取得日期區間之重大會議資料
	 * @param startCal 起始日期
	 * @param endCal 結束日期
	 * @return 會議資料
	 */
	public List<AmsMeeting> findAmsMeetingBy(Calendar startCal, Calendar endCal);
	
	public List<AmsMeeting> findAmsMeetingsBy(Date from, Date to)
			throws DataAccessException;
	
}
