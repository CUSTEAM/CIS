package tw.edu.chit.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.springframework.dao.DataAccessException;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.util.ServiceException;

import tw.edu.chit.dao.AdminDAO;
import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.BankFeePay;
import tw.edu.chit.model.FeeCode;
import tw.edu.chit.model.FeePay;
import tw.edu.chit.model.LicenseCode;
import tw.edu.chit.model.LicenseCode961;
import tw.edu.chit.model.SuggestionDept;
import tw.edu.chit.model.SuggestionEmail;
import tw.edu.chit.model.TempStmd;
import tw.edu.chit.model.WorkDuty;
import tw.edu.chit.model.WorkNature;
import tw.edu.chit.util.IConstants.FEE_TYPE;

public interface AdminManager {

	public void setAdminDAO(AdminDAO dao);

	public void setMemberDAO(MemberDAO memberDao);

	@SuppressWarnings("unchecked")
	public Object getObject(Class clazz, Serializable id);

	public void saveObject(Object o);

	@SuppressWarnings("unchecked")
	public void removeObject(Class clazz, Serializable id);

	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection entities) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public void deleteAll(Collection entities) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public List find(String hql, Object[] values) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public List find(String hql, Object[] values, int maxsize)
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
	public List findSQLWithCriteria(Class entity, Example example,
			Projection projection, List<Order> orders, Criterion... criterion)
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
	public List findSQLWithCriteria(Class entity, Example example,
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
	public List findSQLWithCriteria(Class entity, Example example,
			Projection projection, List<Order> orders, List<Criterion> criterion)
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
	public List findSQLWithCriteria(Class entity, Example example,
			Projection projection, List<Order> orders, int limit,
			List<Criterion> criterion) throws DataAccessException;

	@SuppressWarnings("unchecked")
	public List<Map> findBySQL(String sql, Object[] params);

	@SuppressWarnings("unchecked")
	public List findMessagesByCategory(String category,
			HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List findMessagesByCategory(String category);

	/**
	 * 以關鍵字查詢學期資料之Service方法
	 * 
	 * @param keyword The keyword
	 * @return The keyword's value
	 */
	public String findTermBy(String keyword);

	public String findParameterValueByName(String name);

	public void regenAdcdBySeld(String term, String campusNo, String schoolNo,
			String deptNo, String classNo);

	/**
	 * 查詢意見反應單位資料
	 * 
	 * @param sd
	 * @return
	 */
	public List<SuggestionDept> findSuggestionDeptBy(SuggestionDept sd);

	public SuggestionDept findSuggestionDeptBy(Integer oid);
	


	/**
	 * 
	 * @param workNature
	 * @return
	 */
	public List<WorkNature> findWorkNatureBy(WorkNature workNature);

	/**
	 * 
	 * @param workDuty
	 * @return
	 */
	public List<WorkDuty> findWorkDutyBy(WorkDuty workDuty);

	/**
	 * 
	 * @param feeCode
	 * @return
	 */
	public List<FeeCode> findFeeCodeBy(FeeCode feeCode);

	/**
	 * 
	 * @param feePay
	 * @return
	 */
	public List<FeePay> findFeePayBy(FeePay feePay);

	/**
	 * 
	 * @param bankFeePay
	 * @return
	 */
	public List<BankFeePay> findBankFeePayBy(BankFeePay bankFeePay);

	/**
	 * 
	 * @param licenseCode
	 * @return
	 */
	public List<LicenseCode> findLicenseCodesBy(LicenseCode licenseCode);

	/**
	 * 
	 * @param licenseCode961
	 * @return
	 */
	public List<LicenseCode961> findLicenseCode961sBy(
			LicenseCode961 licenseCode961);

	/**
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	public int txBulkUpdate(String hql, Object[] values)
			throws DataAccessException;

	/**
	 * 
	 * @param service
	 * @param from
	 * @param to
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 */
	public CalendarEventEntry[] findCalendarEventBy(CalendarService service,
			DateTime from, DateTime to) throws ServiceException, IOException;

	public Map<String, Object> txRegisterUpdate(List<DynaBean> beans,
			FEE_TYPE feeType) throws DataAccessException;
	
	/**
	 * 新學年由TempStmd匯入可編班編學號的新生(需請各部制先產生可編班編學號的新生的報表)
	 * @param students
	 * @return 錯誤信息
	 */
	public Map<String, String> txImportNewStudent(List<TempStmd> students);

}
