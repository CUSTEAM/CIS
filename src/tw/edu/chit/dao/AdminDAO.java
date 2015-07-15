package tw.edu.chit.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.springframework.dao.DataAccessException;

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

public interface AdminDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List executeSQL(String hql, Object[] values)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public List executeSQL(String hql, Object[] values, int mexsize)
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
			Projection projection, List<Order> orders, int limit,
			Criterion... criterion) throws DataAccessException;

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
			List<Criterion> criterion) throws DataAccessException;
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection entities) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public void deleteAll(Collection entities) throws DataAccessException;
	
	public int sqlUpdate(String sql, Object[] values);

	public List<Message> findMessagesByCategoryStartDateDueDate(
			String category, Date startDate, Date dueDate);

	/**
	 * 以關鍵字查詢Parameter資料表
	 * 
	 * @param keyword Keyword
	 * @return tw.edu.chit.model.Parameter object
	 */
	public Parameter getParameterBy(String keyword);

	/**
	 * 查詢意見反應單位資料
	 * 
	 * @param sd
	 * @return
	 */
	public List<SuggestionDept> getSuggestionDept(SuggestionDept sd);

	/**
	 * 
	 * @param workNature
	 * @return
	 */
	public List<WorkNature> getWorkNatureBy(WorkNature workNature);

	/**
	 * 
	 * @param workDuty
	 * @return
	 */
	public List<WorkDuty> getWorkDutyBy(WorkDuty workDuty);

	/**
	 * 
	 * @param feeCode
	 * @return
	 */
	public List<FeeCode> getFeeCodeBy(FeeCode feeCode);

	/**
	 * 
	 * @param feePay
	 * @return
	 */
	public List<FeePay> getFeePayBy(FeePay feePay);

	/**
	 * 
	 * @param bankFeePay
	 * @return
	 */
	public List<BankFeePay> getBankFeePayBy(BankFeePay bankFeePay);

	/**
	 * 
	 * @param licenseCode
	 * @return
	 */
	public List<LicenseCode> getLicenseCodesBy(LicenseCode licenseCode);

	/**
	 * 
	 * @param licenseCode961
	 * @return
	 */
	public List<LicenseCode961> getLicenseCode961sBy(
			LicenseCode961 licenseCode961);

}
