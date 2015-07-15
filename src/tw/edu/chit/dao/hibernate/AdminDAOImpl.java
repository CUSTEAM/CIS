package tw.edu.chit.dao.hibernate;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.dao.AdminDAO;
import tw.edu.chit.model.BankFeePay;
import tw.edu.chit.model.FeeCode;
import tw.edu.chit.model.FeePay;
import tw.edu.chit.model.LicenseCode;
import tw.edu.chit.model.LicenseCode961;
import tw.edu.chit.model.Message;
import tw.edu.chit.model.Parameter;
import tw.edu.chit.model.SuggestionDept;
import tw.edu.chit.model.WorkDuty;
import tw.edu.chit.model.WorkNature;

public class AdminDAOImpl extends BaseDAOHibernate implements AdminDAO {

	@SuppressWarnings("unchecked")
	public List executeSQL(String hql, Object[] values)
			throws DataAccessException {
		if (values != null && values.length > 0) {
			getHibernateTemplate().setMaxResults(50000);
			return getHibernateTemplate().find(hql, values);
		} else {
			getHibernateTemplate().setMaxResults(50000);
			return getHibernateTemplate().find(hql);
		}
	}

	@SuppressWarnings("unchecked")
	public List executeSQL(String hql, Object[] values, int maxsize)
			throws DataAccessException {
		if (values != null && values.length > 0) {
			getHibernateTemplate().setMaxResults(maxsize);
			return getHibernateTemplate().find(hql, values);
		} else {
			getHibernateTemplate().setMaxResults(maxsize);
			return getHibernateTemplate().find(hql);
		}
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
			Projection projection, List<Order> orders, int limit,
			Criterion... criterion) throws DataAccessException {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entity)
				.add(example);
		for (Criterion c : criterion)
			detachedCriteria.add(c);

		if (projection != null)
			detachedCriteria.setProjection(projection);

		if (orders != null && !orders.isEmpty()) {
			for (Order o : orders)
				detachedCriteria.addOrder(o);
		}

		if (limit > -1) 
			getHibernateTemplate().setMaxResults(limit);
		else
			getHibernateTemplate().setMaxResults(100000);

		return getHibernateTemplate().findByCriteria(detachedCriteria);
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
			Projection projection, List<Order> orders, int limit,
			List<Criterion> criterion) throws DataAccessException {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entity)
				.add(example);
		for (Criterion c : criterion)
			detachedCriteria.add(c);

		if (projection != null)
			detachedCriteria.setProjection(projection);

		if (orders != null && !orders.isEmpty()) {
			for (Order o : orders)
				detachedCriteria.addOrder(o);
		}

		if (limit > -1) {
			getHibernateTemplate().setMaxResults(limit);
		}	

		return getHibernateTemplate().findByCriteria(detachedCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection entities) throws DataAccessException {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteAll(Collection entities) throws DataAccessException {
		getHibernateTemplate().deleteAll(entities);
	}
	
	public int sqlUpdate(String sql, Object[] values) {
		return getHibernateTemplate().bulkUpdate(sql, values);
	}

	@SuppressWarnings("unchecked")
	public List<Message> findMessagesByCategoryStartDateDueDate(
			String category, Date startDate, Date dueDate) {

		return getHibernateTemplate().find(
				"   from Message m " + "where m.category   = ?"
						+ "  and m.startDate <= ?" + "  and m.dueDate   >= ? "
						+ "order by m.startDate desc",
				new Object[] { category, startDate, dueDate });
	}

	/**
	 * 以關鍵字查詢Parameter資料表
	 * 
	 * @param keyword Keyword
	 * @return tw.edu.chit.model.Parameter object
	 */
	@SuppressWarnings("unchecked")
	public Parameter getParameterBy(String keyword) {
		String hql = "FROM Parameter p WHERE p.name = ?";
		List<Parameter> parameters = (List<Parameter>) getHibernateTemplate()
				.find(hql, keyword);
		if (parameters.isEmpty())
			return null;
		else
			return parameters.get(0);
	}

	/**
	 * 查詢意見反應單位資料
	 * 
	 * @param sd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SuggestionDept> getSuggestionDept(SuggestionDept sd) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
		} catch (Exception e) {
			session = getHibernateTemplate().getSessionFactory().openSession();
		}
		List<SuggestionDept> cis = session.createCriteria(SuggestionDept.class)
				.add(Example.create(sd)).list();
		return cis;
	}

	/**
	 * 
	 * @param workNature
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WorkNature> getWorkNatureBy(WorkNature workNature) {
		return getHibernateTemplate().findByExample(workNature);
	}

	/**
	 * 
	 * @param workDuty
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WorkDuty> getWorkDutyBy(WorkDuty workDuty) {
		return getHibernateTemplate().findByExample(workDuty);
	}

	/**
	 * 
	 * @param feeCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FeeCode> getFeeCodeBy(FeeCode feeCode) {
		getHibernateTemplate().setMaxResults(100000);
		return getHibernateTemplate().findByExample(feeCode);
	}

	/**
	 * 
	 * @param feePay
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FeePay> getFeePayBy(FeePay feePay) {
		getHibernateTemplate().setMaxResults(100000);
		return getHibernateTemplate().findByExample(feePay);
	}

	/**
	 * 
	 * @param bankFeePay
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BankFeePay> getBankFeePayBy(BankFeePay bankFeePay) {
		getHibernateTemplate().setMaxResults(100000);
		return getHibernateTemplate().findByExample(bankFeePay);
	}

	/**
	 * 
	 * @param licenseCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LicenseCode> getLicenseCodesBy(LicenseCode licenseCode) {
		getHibernateTemplate().setMaxResults(100000);
		return getHibernateTemplate().findByExample(licenseCode);
	}
	
	/**
	 * 
	 * @param licenseCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LicenseCode961> getLicenseCode961sBy(
			LicenseCode961 licenseCode961) {
		getHibernateTemplate().setMaxResults(100000);
		return getHibernateTemplate().findByExample(licenseCode961);
	}
	
}
