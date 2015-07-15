package tw.edu.chit.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.model.AmsAskLeave;
import tw.edu.chit.model.AmsDocApply;
import tw.edu.chit.model.AmsHoliday;
import tw.edu.chit.model.AmsMeeting;
import tw.edu.chit.model.AmsMeetingAskLeave;
import tw.edu.chit.model.AmsPersonalVacation;
import tw.edu.chit.model.AmsShiftGroup;
import tw.edu.chit.model.AmsShiftTime;
import tw.edu.chit.model.AmsWorkdateData;
import tw.edu.chit.model.AmsWorkdateInfo;

public interface AmsDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List submitQuery(String hql);

	@SuppressWarnings("unchecked")
	public List submitQueryP(String hql, Object[] parameters);

	public int removeAnyThing(String hql);

	public void removeDoc(AmsDocApply doc);
	
	/**
	 * 
	 * @param entity
	 * @param expression
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List getSQLWithCriteria(Class entity, SimpleExpression... expression)
			throws DataAccessException;
	
	/**
	 * 
	 * @param entity
	 * @param example
	 * @param criterion
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List getSQLWithCriteria(Class entity, Example example,
			Criterion... criterion) throws DataAccessException;

	public Session getHibernateSession();
	
	/**
	 * 查詢AmsAskLeave物件(假別)
	 * 
	 * @param aal AmsAskLeave Object
	 * @return List of AmsAskLeave Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsAskLeave> getAmsAskLeaveBy(AmsAskLeave aal)
			throws DataAccessException;

	/**
	 * 查詢AmsDocApply物件(請假)
	 * 
	 * @param ada AmsDocApply Object
	 * @return List of AmsDocApply Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsDocApply> getAmsDocApplyBy(AmsDocApply ada)
			throws DataAccessException;

	/**
	 * 查詢AmsDocApply物件(某日請假)
	 * 
	 * @param ads AmsDocApply Object
	 * @param d Some Date
	 * @return List of AmsDocApply Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsDocApply> getAmsDocApplyBy(AmsDocApply ads, Date d)
			throws DataAccessException;

	/**
	 * 
	 * @param ads
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 */
	public List<AmsDocApply> getAmsDocApplyBy(AmsDocApply ads, Date from,
			Date to) throws DataAccessException;
	
	/**
	 * 
	 * @param ads
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 */
	public List<AmsDocApply> getAmsDocApplyBy(Date from, Date to)
			throws DataAccessException;
	
	/**
	 * 
	 * @param ads
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 */
	public List<AmsMeetingAskLeave> getAmsMeetingAskLeaveBy(Date from, Date to)
			throws DataAccessException;
	
	public List<AmsMeetingAskLeave> getAmsMeetingAskLeavesBy(
			AmsMeetingAskLeave amal) throws DataAccessException;

	/**
	 * 查詢AmsHoliday物件(假日)
	 * 
	 * @param ah AmsHoliday
	 * @return List of AmsHoliday Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsHoliday> getAmsHolidayBy(AmsHoliday ah)
			throws DataAccessException;

	/**
	 * 查詢AmsShiftGroup物件
	 * 
	 * @param asg AmsShiftGroup Object
	 * @return List of AmsShiftGroup Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsShiftGroup> getAmsShiftGroupBy(AmsShiftGroup asg)
			throws DataAccessException;

	/**
	 * 查詢AmsShiftTime物件
	 * 
	 * @param ast AmsShiftTime Object
	 * @return List of AmsShiftTime Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsShiftTime> getAmsShiftTimeBy(AmsShiftTime ast)
			throws DataAccessException;

	/**
	 * 查詢AmsPersonalVacation物件
	 * 
	 * @param apv AmsPersonalVacation Object
	 * @return List of AmsPersonalVacation Objects
	 * @throws DataAccessException If any exception occurred
	 */
	public List<AmsPersonalVacation> getAmsPersonalVacationBy(
			AmsPersonalVacation apv) throws DataAccessException;

	/**
	 * 查詢AmsMeeting物件
	 * 
	 * @param amsMeeting
	 * @return
	 * @throws DataAccessException
	 */
	public List<AmsMeeting> getAmsMeetingBy(AmsMeeting amsMeeting)
			throws DataAccessException;
	
	public List<AmsMeeting> getAmsMeetingsBy(Date from, Date to)
			throws DataAccessException;

	/**
	 * 查詢AmsWorkdateData物件
	 * 
	 * @param aw AmsWorkdateData Object
	 * @param from Search from
	 * @param to Search to
	 * @return List of AmsWorkdateInfo Objects
	 * @throws DataAccessException If any exception occurred
	 * @throws SQLException If any create statement command exception occurred
	 */
	public List<AmsWorkdateInfo> getAmsWorkdateDataBy(AmsWorkdateData aw,
			String schoolYear, Date from, Date to) throws DataAccessException,
			SQLException;

	/**
	 * 查詢AmsWorkdateData物件
	 * 
	 * @param aw AmsWorkdateData Object
	 * @param from Search from
	 * @param to Search to
	 * @return List of AmsWorkdateInfo Objects
	 * @throws DataAccessException If any exception occurred
	 * @throws SQLException If any create statement command exception occurred
	 */
	public List<DynaBean> getAmsWorkdateBy(AmsWorkdateData aw, Date from,
			Date to) throws DataAccessException, SQLException, Exception;

}
