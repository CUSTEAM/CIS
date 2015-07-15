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
import tw.edu.chit.dao.BlobDAO;
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

public class BlobDAOImpl extends BaseDAOHibernate implements BlobDAO {

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

	/*
	public void removeDoc(AmsDocApply doc) {
		removeObject(doc);
	}
	*/

}
