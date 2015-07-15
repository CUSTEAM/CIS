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

public interface BlobDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List submitQuery(String hql);

	@SuppressWarnings("unchecked")
	public List submitQueryP(String hql, Object[] parameters);

	public int removeAnyThing(String hql);

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

	//public void removeDoc(AmsDocApply doc);
	

}
