package tw.edu.chit.dao.hibernate;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.beanutils.ResultSetDynaClass;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
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
import tw.edu.chit.model.AmsShiftGroup;
import tw.edu.chit.model.AmsShiftTime;
import tw.edu.chit.model.AmsWorkdateData;
import tw.edu.chit.model.AmsWorkdateInfo;
import tw.edu.chit.util.Toolket;

public class AmsDAOImpl extends BaseDAOHibernate implements AmsDAO {

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List submitQuery(String hql) {
		return getHibernateTemplate().find(hql);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List submitQueryP(String hql, Object[] parameters) {
		return getHibernateTemplate().find(hql, parameters);
	}

	/**
	 * 
	 */
	public int removeAnyThing(String hql) {
		return getHibernateTemplate().bulkUpdate(hql);
	}

	public void removeDoc(AmsDocApply doc) {
		removeObject(doc);
	}
	
	/**
	 * 
	 * @param entity
	 * @param expression
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List getSQLWithCriteria(Class entity, SimpleExpression... expression)
			throws DataAccessException {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entity);
		for (Criterion c : expression)
			detachedCriteria.add(c);
		return getHibernateTemplate().findByCriteria(detachedCriteria);
	}
	
	public Session getHibernateSession() {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
		} catch (Exception e) {
			session = getHibernateTemplate().getSessionFactory().openSession();
		}
		return session;
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
	public List getSQLWithCriteria(Class entity, Example example,
			Criterion... criterion) throws DataAccessException {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entity)
				.add(example);
		for (Criterion c : criterion)
			detachedCriteria.add(c);
		return getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	/**
	 * 查詢AmsAskLeave物件(假別)
	 * 
	 * @param aal AmsAskLeave Object
	 * @return List of AmsAskLeave Objects
	 * @throws DataAccessException If any exception occurred
	 */
	@SuppressWarnings("unchecked")
	public List<AmsAskLeave> getAmsAskLeaveBy(AmsAskLeave aal)
			throws DataAccessException {
		return getHibernateTemplate().findByExample(aal);
	}

	/**
	 * 查詢AmsDocApply物件(請假)
	 * 
	 * @param ada AmsDocApply Object
	 * @return List of AmsDocApply Objects
	 * @throws DataAccessException If any exception occurred
	 */
	@SuppressWarnings("unchecked")
	public List<AmsDocApply> getAmsDocApplyBy(AmsDocApply ada)
			throws DataAccessException {
		return getHibernateTemplate().findByExample(ada);
	}

	/**
	 * 查詢AmsDocApply物件(某日請假)
	 * 
	 * @param ads AmsDocApply Object
	 * @param d Some Date
	 * @return List of AmsDocApply Objects
	 * @throws DataAccessException If any exception occurred
	 */
	@SuppressWarnings("unchecked")
	public List<AmsDocApply> getAmsDocApplyBy(AmsDocApply ads, Date d)
			throws DataAccessException {

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		Calendar cal1 = (Calendar) cal.clone();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal1.set(Calendar.HOUR_OF_DAY, 23);
		cal1.set(Calendar.MINUTE, 59);
		cal1.set(Calendar.SECOND, 59);
		cal1.set(Calendar.MILLISECOND, 999);
		String hql = "FROM AmsDocApply a WHERE a.idno = ? "
				+ "AND (a.startDate BETWEEN ? AND ? "
				+ "OR a.endDate BETWEEN ? AND ?) ORDER BY a.startDate";

		List<AmsDocApply> applies = getHibernateTemplate().find(
				hql,
				new Object[] { ads.getIdno(), cal.getTime(), cal1.getTime(),
						cal.getTime(), cal1.getTime() });
		if (!applies.isEmpty())
			return applies;
		else {
			hql = "FROM AmsDocApply a WHERE a.idno = ? "
					+ "AND (a.startDate < ? AND a.endDate > ?) ORDER BY a.startDate";
			return getHibernateTemplate().find(hql,
					new Object[] { ads.getIdno(), d, d });
		}
	}

	/**
	 * 
	 * @param ads
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<AmsDocApply> getAmsDocApplyBy(AmsDocApply ads, Date from,
			Date to) throws DataAccessException {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = (Calendar) cal.clone();
		Calendar cal2 = (Calendar) cal.clone();

		cal1.setTime(from);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);

		cal2.setTime(to);
		cal2.set(Calendar.HOUR_OF_DAY, 23);
		cal2.set(Calendar.MINUTE, 59);
		cal2.set(Calendar.SECOND, 59);
		cal2.set(Calendar.MILLISECOND, 999);

		String hql = "FROM AmsDocApply a WHERE a.idno = ? "
				+ "AND a.startDate BETWEEN ? AND ? ";
		Object[] params = { ads.getIdno(), cal1.getTime(), cal2.getTime() };

		if (StringUtils.isNotBlank(ads.getDocType())) {
			hql += "AND a.docType = ? ";
			params = (Object[]) ArrayUtils.add(params, ads.getDocType());
		}

		if (StringUtils.isNotBlank(ads.getStatus())) {
			hql += "AND a.status = ? ";
			params = (Object[]) ArrayUtils.add(params, ads.getStatus());
		}

		hql += "ORDER BY a.startDate";
		return getHibernateTemplate().find(hql, params);
	}
	
	/**
	 * 
	 * @param ads
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<AmsDocApply> getAmsDocApplyBy(Date from, Date to)
			throws DataAccessException {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = (Calendar) cal.clone();
		Calendar cal2 = (Calendar) cal.clone();

		cal1.setTime(from);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);

		cal2.setTime(to);
		cal2.set(Calendar.HOUR_OF_DAY, 23);
		cal2.set(Calendar.MINUTE, 59);
		cal2.set(Calendar.SECOND, 59);
		cal2.set(Calendar.MILLISECOND, 999);

		String hql = "FROM AmsDocApply a WHERE a.startDate "
				+ "BETWEEN ? AND ? ORDER BY a.startDate";

		return getHibernateTemplate().find(hql,
				new Object[] { cal1.getTime(), cal2.getTime() });
	}
	
	/**
	 * 
	 * @param ads
	 * @param from
	 * @param to
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<AmsMeetingAskLeave> getAmsMeetingAskLeaveBy(Date from, Date to)
			throws DataAccessException {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = (Calendar) cal.clone();
		Calendar cal2 = (Calendar) cal.clone();

		cal1.setTime(from);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);

		cal2.setTime(to);
		cal2.set(Calendar.HOUR_OF_DAY, 23);
		cal2.set(Calendar.MINUTE, 59);
		cal2.set(Calendar.SECOND, 59);
		cal2.set(Calendar.MILLISECOND, 999);

		String hql = "FROM AmsMeetingAskLeave a WHERE a.processDate "
				+ "BETWEEN ? AND ? ORDER BY a.processDate";

		return getHibernateTemplate().find(hql,
				new Object[] { cal1.getTime(), cal2.getTime() });
	}
	
	@SuppressWarnings("unchecked")
	public List<AmsMeetingAskLeave> getAmsMeetingAskLeavesBy(
			AmsMeetingAskLeave amal) throws DataAccessException {
		return getHibernateTemplate().findByExample(amal);
	}

	/**
	 * 查詢AmsHoliday物件(假日或工作日)
	 * 
	 * @param ah AmsHoliday Object
	 * @return List of AmsHoliday Objects
	 * @throws DataAccessException If any exception occurred
	 */
	@SuppressWarnings("unchecked")
	public List<AmsHoliday> getAmsHolidayBy(AmsHoliday ah)
			throws DataAccessException {
		return getHibernateTemplate().findByExample(ah);
	}

	/**
	 * 查詢AmsShiftGroup物件
	 * 
	 * @param asg AmsShiftGroup Object
	 * @return List of AmsShiftGroup Objects
	 * @throws DataAccessException If any exception occurred
	 */
	@SuppressWarnings("unchecked")
	public List<AmsShiftGroup> getAmsShiftGroupBy(AmsShiftGroup asg)
			throws DataAccessException {
		return getHibernateTemplate().findByExample(asg);
	}

	/**
	 * 查詢AmsShiftTime物件
	 * 
	 * @param ast AmsShiftTime Object
	 * @return List of AmsShiftTime Objects
	 * @throws DataAccessException If any exception occurred
	 */
	@SuppressWarnings("unchecked")
	public List<AmsShiftTime> getAmsShiftTimeBy(AmsShiftTime ast)
			throws DataAccessException {
		return getHibernateTemplate().findByExample(ast);
	}

	/**
	 * 查詢AmsPersonalVacation物件
	 * 
	 * @param apv AmsPersonalVacation Object
	 * @return List of AmsPersonalVacation Objects
	 * @throws DataAccessException If any exception occurred
	 */
	@SuppressWarnings("unchecked")
	public List<AmsPersonalVacation> getAmsPersonalVacationBy(
			AmsPersonalVacation apv) throws DataAccessException {
		return getHibernateTemplate().findByExample(apv);
	}

	/**
	 * 查詢AmsMeeting物件
	 * 
	 * @param amsMeeting
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<AmsMeeting> getAmsMeetingBy(AmsMeeting amsMeeting)
			throws DataAccessException {
		return getHibernateTemplate().findByExample(amsMeeting);
	}
	
	@SuppressWarnings("unchecked")
	public List<AmsMeeting> getAmsMeetingsBy(Date from, Date to)
			throws DataAccessException {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = (Calendar) cal.clone();
		Calendar cal2 = (Calendar) cal.clone();

		cal1.setTime(from);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);

		cal2.setTime(to);
		cal2.set(Calendar.HOUR_OF_DAY, 23);
		cal2.set(Calendar.MINUTE, 59);
		cal2.set(Calendar.SECOND, 59);
		cal2.set(Calendar.MILLISECOND, 999);

		String hql = "FROM AmsMeeting a WHERE a.meetingDate "
				+ "BETWEEN ? AND ? ORDER BY a.meetingDate";

		return getHibernateTemplate().find(hql,
				new Object[] { cal1.getTime(), cal2.getTime() });
	}

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
			SQLException {

		Connection conn = getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
		DateFormat dt = new SimpleDateFormat("kk:mm", Locale.TAIWAN);
		StringBuffer buffer = new StringBuffer(
				"SELECT * FROM AMS_Workdate WHERE idno = '").append(
				aw.getIdno()).append("' ");
		if (aw.getWdate() != null)
			buffer.append("AND wdate = '").append(df.format(aw.getWdate()))
					.append("' ");
		else if (from != null && to != null)
			buffer.append("AND wdate BETWEEN '").append(df.format(from))
					.append("' AND '").append(df.format(to)).append("' ");

		if (StringUtils.isNotBlank(aw.getDateType()))
			buffer.append("AND date_type = '").append(aw.getDateType()).append(
					"' ");
		buffer.append("ORDER BY idno, wdate");

		Statement stmd = conn.createStatement();
		ResultSet rs = stmd.executeQuery(buffer.toString());
		List<AmsWorkdateInfo> list = new LinkedList<AmsWorkdateInfo>();
		AmsWorkdateInfo awi = null;
		AmsDocApply amsDocApply = null;

		// 查詢年假與特休假
		AmsPersonalVacation apv = new AmsPersonalVacation();
		apv.setIdno(aw.getIdno());
		apv.setVyear(Integer.valueOf(schoolYear));

		while (rs.next()) {
			try{
				awi = new AmsWorkdateInfo();
				awi.setIdno(rs.getString("idno")); // IDNO
				awi.setWdate(rs.getDate("wdate")); // Work Date

				amsDocApply = new AmsDocApply();
				amsDocApply.setIdno(aw.getIdno());

				// Work Date Info
				awi.setWdateInfo(df.format(rs.getDate("wdate")) + " ("
						+ Toolket.getWeekInfo(rs.getDate("wdate")) + ")");
				awi.setDateType(rs.getString("date_type")); // w:Work h:Holiday
				awi.setType(rs.getString("type")); // ??
				awi.setSetIn(rs.getTime("set_in")); // 正常上班時間
				awi.setSetInInfo(rs.getTime("set_in") == null ? null : dt.format(rs
						.getTime("set_in"))); // 正常上班時間資訊
				awi.setSetOut(rs.getTime("set_out")); // 正常下班時間
				awi.setSetOutInfo(rs.getTime("set_out") == null ? null : dt
						.format(rs.getTime("set_out"))); // 正常下班時間資訊
				awi.setRealIn(rs.getTime("real_in")); // 上班刷卡
				awi.setRealInInfo(rs.getTime("real_in") != null ? dt.format(rs
						.getTime("real_in")) : null); // 遲到
				awi.setRealOut(rs.getTime("real_out")); // 下班刷卡
				awi.setRealOutInfo(rs.getTime("real_out") != null ? dt.format(rs
						.getTime("real_out")) : null); // 加班/早退
				awi.setShift(rs.getString("shift")); // Shift Group
				// awi.setExtra(rs.getString("extra")); // ??
				list.add(awi);
			}catch(Exception e){
				
			}
			

			
		}

		return list;
	}

	/**
	 * 查詢AmsWorkdateData物件
	 * 
	 * @param aw AmsWorkdateData Object
	 * @param from Search from
	 * @param to Search to
	 * @return List of AmsWorkdateInfo Objects
	 * @throws DataAccessException If any exception occurred
	 * @throws SQLException If any create statement command exception occurred
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<DynaBean> getAmsWorkdateBy(AmsWorkdateData aw, Date from,
			Date to) throws DataAccessException, SQLException, Exception {

		Connection conn = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().connection();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
		DateFormat dt = new SimpleDateFormat("kk:mm", Locale.TAIWAN);
		StringBuffer buffer = new StringBuffer(
				"SELECT * FROM AMS_Workdate WHERE ");
		if (from != null && to != null)
			buffer.append("wdate BETWEEN '").append(df.format(from)).append(
					"' AND '").append(df.format(to)).append("' ");

		if (StringUtils.isNotBlank(aw.getDateType()))
			buffer.append("AND date_type = '").append(aw.getDateType()).append(
					"' ");
		buffer.append("ORDER BY idno, wdate");

		Statement stmd = conn.createStatement();
		ResultSet rs = stmd.executeQuery(buffer.toString());
		// CachedRowSetImpl cache = new CachedRowSetImpl();
		// cache.populate(rs);

		Iterator<DynaBean> rows = (new ResultSetDynaClass(rs)).iterator();
		List<DynaBean> list = new LinkedList<DynaBean>();
		DynaBean bean = null, row = null;

		while (rows.hasNext()) {
			row = (DynaBean) rows.next();
			bean = new LazyDynaBean();
			bean.set("idno", row.get("idno"));
			bean.set("wdate", row.get("wdate"));
			bean.set("date_type", row.get("date_type"));
			bean.set("aitype", row.get("type") == null ? "" : row.get("type"));
			bean.set("set_in", row.get("set_in") == null ? "" : dt
					.format((Time) row.get("set_in")));
			bean.set("set_out", row.get("set_out") == null ? "" : dt
					.format((Time) row.get("set_out")));
			bean.set("real_in", row.get("real_in") == null ? "" : dt
					.format((Time) row.get("real_in")));
			bean.set("real_out", row.get("real_out") == null ? "" : dt
					.format((Time) row.get("real_out")));
			// BeanUtils.copyProperties(bean, (DynaBean) rows.next());
			list.add(bean);
		}

		return list;
	}

}
