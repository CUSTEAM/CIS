package tw.edu.chit.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.dao.AdminDAO;
import tw.edu.chit.dao.CourseJdbcDAO;
import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.BankFeePay;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.FeeCode;
import tw.edu.chit.model.FeePay;
import tw.edu.chit.model.LicenseCode;
import tw.edu.chit.model.LicenseCode961;
import tw.edu.chit.model.Parameter;
import tw.edu.chit.model.RegistrationCard;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.StdImage4t;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.SuggestionDept;
import tw.edu.chit.model.SuggestionEmail;

import tw.edu.chit.model.TempStmd;
import tw.edu.chit.model.WorkDuty;
import tw.edu.chit.model.WorkNature;
import tw.edu.chit.model.WwPass;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import static tw.edu.chit.util.IConstants.FEE_TYPE;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.util.ServiceException;

public class AdminManagerImpl extends BaseManager implements AdminManager {

	private AdminDAO dao;
	private MemberDAO memberDao;
	private CourseJdbcDAO jdbcDao;

	public void setAdminDAO(AdminDAO dao) {
		this.dao = dao;
	}

	public void setMemberDAO(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}

	public void setJdbcDAO(CourseJdbcDAO jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	@SuppressWarnings("unchecked")
	public Object getObject(Class clazz, Serializable id) {
		return dao.getObject(clazz, id);
	}

	public void saveObject(Object o) {
		dao.saveObject(o);
	}

	@SuppressWarnings("unchecked")
	public void removeObject(Class clazz, Serializable id) {
		dao.removeObject(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection entities) throws DataAccessException {
		dao.saveOrUpdateAll(entities);
	}

	@SuppressWarnings("unchecked")
	public void deleteAll(Collection entities) throws DataAccessException {
		dao.deleteAll(entities);
	}

	@SuppressWarnings("unchecked")
	public List find(String hql, Object[] values) throws DataAccessException {
		return dao.executeSQL(hql, values);
	}

	@SuppressWarnings("unchecked")
	public List find(String hql, Object[] values, int maxsize)
			throws DataAccessException {
		return dao.executeSQL(hql, values, maxsize);
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
			Projection projection, List<Order> orders, Criterion... criterion)
			throws DataAccessException {

		return findSQLWithCriteria(entity, example, projection, orders, -1,
				criterion);
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
			Projection projection, List<Order> orders, List<Criterion> criterion)
			throws DataAccessException {

		return findSQLWithCriteria(entity, example, projection, orders, -1,
				criterion);
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
			Projection projection, List<Order> orders, int limit,
			Criterion... criterion) throws DataAccessException {
		return dao.getSQLWithCriteria(entity, example, projection, orders,
				limit, criterion);
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
			Projection projection, List<Order> orders, int limit,
			List<Criterion> criterion) throws DataAccessException {
		return dao.getSQLWithCriteria(entity, example, projection, orders,
				limit, criterion);
	}

	@SuppressWarnings("unchecked")
	public List<Map> findBySQL(String sql, Object[] params) {
		return jdbcDao.StandardSqlQuery(sql, params);
	}

	/**
	 * 新的訊息系統
	 */
	public List findMessagesByCategory(String category,
			HttpServletRequest request) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sf.format(new Date());

		request.setAttribute("ActioName", jdbcDao
				.ezGetString("SELECT Label FROM Module WHERE Name='" + category
						+ "'"));

		List message = jdbcDao
				.StandardSqlQuery("SELECT * FROM Message WHERE (Category='"
						+ category + "'OR Category='All')"
						+ " AND StartDate<='" + date + "' AND DueDate>='"
						+ date + "' ORDER BY Oid DESC");

		List messages = new ArrayList();
		Map m;
		for (int i = 0; i < message.size(); i++) {

			m = new HashMap();
			m.put("Oid", ((Map) message.get(i)).get("Oid"));
			m.put("Category", ((Map) message.get(i)).get("Category"));
			m.put("Sender", ((Map) message.get(i)).get("Sender"));
			m.put("Content", ((Map) message.get(i)).get("Content"));
			m.put("StartDate", ((Map) message.get(i)).get("StartDate"));
			m.put("DueDate", ((Map) message.get(i)).get("DueDate"));

			m
					.put(
							"sub_message",
							jdbcDao
									.StandardSqlQuery("SELECT * FROM Message_feedback WHERE MessageOid='"
											+ ((Map) message.get(i)).get("Oid")
											+ "'"));

			m.put("title", ((Map) message.get(i)).get("title"));
			m.put("receiver", ((Map) message.get(i)).get("receiver"));
			m.put("unit", ((Map) message.get(i)).get("unit"));
			m.put("open", ((Map) message.get(i)).get("open"));

			messages.add(m);
		}
		return messages;
	}

	/**
	 * 舊的訊息系統
	 */
	public List findMessagesByCategory(String category) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sf.format(new Date());
		// request.setAttribute("ActioName",
		// jdbcDao.ezGetString("SELECT Label FROM Module WHERE Category='"+category+"'"));

		List message = jdbcDao
				.StandardSqlQuery("SELECT * FROM Message WHERE (Category='"
						+ category + "'OR Category='All')"
						+ " AND StartDate<='" + date + "' AND DueDate>='"
						+ date + "' ORDER BY Oid DESC");

		List messages = new ArrayList();
		Map m;
		for (int i = 0; i < message.size(); i++) {

			m = new HashMap();
			m.put("Oid", ((Map) message.get(i)).get("Oid"));
			m.put("Category", ((Map) message.get(i)).get("Category"));
			m.put("Sender", ((Map) message.get(i)).get("Sender"));
			m.put("Content", ((Map) message.get(i)).get("Content"));
			m.put("StartDate", ((Map) message.get(i)).get("StartDate"));
			m.put("DueDate", ((Map) message.get(i)).get("DueDate"));

			m
					.put(
							"sub_message",
							jdbcDao
									.StandardSqlQuery("SELECT * FROM Message_feedback WHERE MessageOid='"
											+ ((Map) message.get(i)).get("Oid")
											+ "'"));

			m.put("title", ((Map) message.get(i)).get("title"));
			m.put("receiver", ((Map) message.get(i)).get("receiver"));
			m.put("unit", ((Map) message.get(i)).get("unit"));
			m.put("open", ((Map) message.get(i)).get("open"));

			messages.add(m);
		}
		return messages;
	}

	/**
	 * 以關鍵字查詢學期資料之Service方法
	 * 
	 * @param keyword The keyword
	 * @return The keyword's value
	 */
	public String findTermBy(String keyword) {
		Parameter parameter = dao.getParameterBy(keyword);
		if (parameter != null)
			return parameter.getValue();
		else
			return null;
	}

	public String findParameterValueByName(String name) {
		Parameter parameter = dao.getParameterBy(name);
		if (parameter != null)
			return parameter.getValue();
		else
			return "";
	}

	public void regenAdcdBySeld(String term, String campusNo, String schoolNo,
			String deptNo, String classNo) {

		CourseManager cm = (CourseManager) Global.context
				.getBean("courseManager");
		List<Clazz> classes = null;

		if (!"All".equals(classNo) && !"".equals(classNo)) {
			cm.txRegenerateAdcdByClassTerm(classNo, term);
			return;
		} else if (!"All".equals(deptNo) && !"".equals(deptNo)) {
			classes = memberDao.findClassesByCampusSchoolDeptNo(campusNo,
					schoolNo, deptNo);
		} else if (!"All".equals(schoolNo) && !"".equals(schoolNo)) {
			classes = memberDao.findClassesByCampusSchoolNo(campusNo, schoolNo);
		} else if (!"All".equals(campusNo)) {
			classes = memberDao.findClassesByCampusNo(campusNo);
		} else {
			classes = memberDao.findAllClasses();
		}

		for (Clazz clazz : classes) {
			try {
				cm.txRegenerateAdcdByClassTerm(clazz.getClassNo(), term);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查詢意見反應單位資料
	 * 
	 * @param sd
	 * @return
	 */
	public List<SuggestionDept> findSuggestionDeptBy(SuggestionDept sd) {
		return dao.getSuggestionDept(sd);
	}

	/**
	 * 
	 */
	public SuggestionDept findSuggestionDeptBy(Integer oid) {
		return (SuggestionDept) dao.getObject(SuggestionDept.class, oid);
	}
	

	/**
	 * 
	 * @param workNature
	 * @return
	 */
	public List<WorkNature> findWorkNatureBy(WorkNature workNature) {
		return dao.getWorkNatureBy(workNature);
	}

	/**
	 * 
	 * @param workDuty
	 * @return
	 */
	public List<WorkDuty> findWorkDutyBy(WorkDuty workDuty) {
		return dao.getWorkDutyBy(workDuty);
	}

	/**
	 * 
	 * @param feeCode
	 * @return
	 */
	public List<FeeCode> findFeeCodeBy(FeeCode feeCode) {
		return dao.getFeeCodeBy(feeCode);
	}

	/**
	 * 
	 * @param feePay
	 * @return
	 */
	public List<FeePay> findFeePayBy(FeePay feePay) {
		return dao.getFeePayBy(feePay);
	}

	/**
	 * 
	 * @param bankFeePay
	 * @return
	 */
	public List<BankFeePay> findBankFeePayBy(BankFeePay bankFeePay) {
		return dao.getBankFeePayBy(bankFeePay);
	}

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
			DateTime from, DateTime to) throws ServiceException, IOException {

		URL eventFeedUrl = new URL(IConstants.GOOGLE_METAFEED_URL_BASE
				+ IConstants.GOOGLE_EMAIL_USERNAME
				+ IConstants.GOOGLE_EVENT_FEED_URL_SUFFIX);
		CalendarQuery query = new CalendarQuery(eventFeedUrl);
		query.setMinimumStartTime(from);
		query.setMaximumStartTime(to);
		query.setMaxResults(1000);
		CalendarEventFeed resultFeed = service.query(query,
				CalendarEventFeed.class);

		CalendarEventEntry[] entries = resultFeed.getEntries().toArray(
				new CalendarEventEntry[0]);
		Arrays.sort(entries, new CalendarEventEntryComparator());

		return entries;
	}

	/**
	 * 
	 * @param licenseCode
	 * @return
	 */
	public List<LicenseCode> findLicenseCodesBy(LicenseCode licenseCode) {
		return dao.getLicenseCodesBy(licenseCode);
	}

	/**
	 * 
	 * @param licenseCode961
	 * @return
	 */
	public List<LicenseCode961> findLicenseCode961sBy(
			LicenseCode961 licenseCode961) {
		return dao.getLicenseCode961sBy(licenseCode961);
	}
	
	/**
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	public int txBulkUpdate(String hql, Object[] values)
			throws DataAccessException {
		return dao.sqlUpdate(hql, values);
	}

	public Map<String, Object> txRegisterUpdate(List<DynaBean> beans,
			FEE_TYPE feeType) throws DataAccessException {

		String sql = "";
		switch (feeType) {
			case TUITION:
				/*
				 * 新生：(繳費單上一定不會有身分證字號)
				 * 日間：未編班編學號以流水號當作繳費單上的學號
				 * 進修：已編班編學號且以學號當作繳費單上的學號
				 * 學院：已編班編學號但是以流水號當作繳費單上的學號
				 * 結論：日間及學院以流水號當作學號匯入繳費資料，進修部則以真實學號直接更新註冊檔繳費資料
				 */
				sql = "UPDATE Register SET TuitionAccountNo = ?, TuitionAmount = ?, TuitionDate = ? "
						+ "WHERE SchoolYear = ? AND SchoolTerm = ? AND (Idno = ? Or ((SerialNo=? Or RealStudentNo=?) And Type='N'))";
				break;

			case AGENCY:
				sql = "UPDATE Register SET AgencyAccountNo = ?, AgencyAmount = ?, AgencyDate = ? "
						+ "WHERE SchoolYear = ? AND SchoolTerm = ? AND (Idno = ? Or ((SerialNo=? Or RealStudentNo=?) And Type='N'))";
				break;

			case RELIEFTUITION:
				sql = "UPDATE Register SET ReliefTuitionAmount = ? "
						+ "WHERE SchoolYear = ? AND SchoolTerm = ? AND Idno = ?";
				break;

			case LOAN:
				sql = "UPDATE Register SET LoanAmount = ? "
						+ "WHERE SchoolYear = ? AND SchoolTerm = ? AND Idno = ?";
				break;

			case VULNERABLE:
				sql = "UPDATE Register SET VulnerableAmount = ? "
						+ "WHERE SchoolYear = ? AND SchoolTerm = ? AND Idno = ?";
				break;

			default:
				break;
		}

		int counts = 0;
		StringBuilder builder = new StringBuilder();
		Map<String, Object> ret = new HashMap<String, Object>();
		for (DynaBean bean : beans) {
			switch (feeType) {
				case TUITION:
				case AGENCY:
					if (dao.sqlUpdate(sql, new Object[] {
							(String) bean.get("accountNo"),
							(Integer) bean.get("amount"),
							(Date) bean.get("payDate"),
							(String) bean.get("year"),
							(String) bean.get("term"),
							(String) bean.get("idno"),
							(String) bean.get("studentNo"),
							(String) bean.get("studentNo") }) == 0) {
						builder.append((String) bean.get("studentNo")).append(
								",");
						System.out.println((String) bean.get("idno"));
					} else
						counts++;
//					counts += dao.sqlUpdate(sql, new Object[] {
//							(String) bean.get("accountNo"),
//							(Integer) bean.get("amount"),
//							(Date) bean.get("payDate"),
//							(String) bean.get("year"),
//							(String) bean.get("term"),
//							(String) bean.get("idno") });
					break;

				case RELIEFTUITION:
				case LOAN:
				case VULNERABLE:
					if (dao.sqlUpdate(sql, new Object[] {
							(Integer) bean.get("amount"),
							(String) bean.get("year"),
							(String) bean.get("term"),
							(String) bean.get("idno") }) == 0) {
						builder.append((String) bean.get("studentNo")).append(
								",");
						System.out.println((String) bean.get("idno"));
					} else
						counts++;
//					counts += dao.sqlUpdate(sql, new Object[] {
//							(Integer) bean.get("amount"),
//							(String) bean.get("year"),
//							(String) bean.get("term"),
//							(String) bean.get("idno") });
					break;
			}
		}
		
		ret.put("counts", counts);
		ret.put("idnos", StringUtils.substringBeforeLast(builder.toString(), ","));
		return ret;
	}
	
	public Map<String, String> txImportNewStudent(List<TempStmd> students) throws DataAccessException {
		Map ret = new HashMap<String, String>();
		String hql = "From Student Where studentNo=?";
		String hql_1 = "From RegistrationCard Where Oid=?";
		String hql_2 = "From WwPass Where username=?";
		String hql_3 = "From StdImage4t Where Oid=?";
		String hql_0 = "From StdImage Where studentNo=?";
		
		List<RegistrationCard> RegCards = new LinkedList<RegistrationCard>();
		List<WwPass> Passes = new LinkedList<WwPass>();
		List<StdImage4t> StdImg4t = new LinkedList<StdImage4t>();
		List<StdImage> StdImg = new LinkedList<StdImage>();
		List<Student> studs = new ArrayList<Student>();
		List<String> nonImport = new ArrayList<String>();
		Student student = null;
		Integer imgOid = null;
		int icnt = 0;
		String studentNo = "";
		
		for(TempStmd stmd:students){
			try{
				studentNo = stmd.getStudentNo();
				
				//step 0.搬移照片影像檔 Stdimage4t-> StdImage
				StdImg = find(hql_0, new Object[]{studentNo});
				if(StdImg.isEmpty()){
					if(stmd.getImageOid()!=null){
						StdImg4t = find(hql_3, new Object[]{stmd.getImageOid()});
						if(!StdImg4t.isEmpty()){
							StdImage4t img4t = StdImg4t.get(0);
							StdImage img = new StdImage();
							img.setStudentNo(studentNo);
							img.setImage(img4t.getImage());
							dao.saveObject(img);
							
							dao.reload(img);
							imgOid = img.getOid();
						}
					}
				}

				//step 1.建立學籍檔
				studs = (List<Student>)find(hql, new Object[]{studentNo});
				
				if(studs.isEmpty()){
					student = new Student();
					student.setDepartClass(stmd.getDepartClass());
					student.setStudentNo(studentNo);
					student.setStudentName(stmd.getStudentName());
					student.setIdno(stmd.getIdno());
					student.setSex(stmd.getSex());
					student.setBirthday(stmd.getBirthday());
					student.setEntrance(stmd.getEntrance());
					student.setIdent(stmd.getIdent());
					student.setSchlName(stmd.getSchlName()==null?"":stmd.getSchlName());
					student.setSchlCode(stmd.getSchlCode()==null?"":stmd.getSchlCode());
					student.setGradDept(stmd.getGradDept()==null?"":stmd.getGradDept());
					student.setGradyear(stmd.getGradyear()==null?null:stmd.getGradyear());
					student.setGraduStatus(stmd.getGraduStatus()==null?"":stmd.getGraduStatus());
					student.setCurrPost(stmd.getCurrPost()==null?"":stmd.getCurrPost());
					student.setCurrAddr(stmd.getCurrAddr()==null?"":stmd.getCurrAddr());
					student.setTelephone(stmd.getTelephone()==null?"":stmd.getTelephone());
					student.setCellPhone(stmd.getCellPhone()==null?"":stmd.getCellPhone());
					student.setParentName(stmd.getParentName()==null?"":stmd.getParentName());
					student.setEmail(stmd.getEmail()==null?"":stmd.getEmail());
					student.setPermPost(stmd.getPermPost()==null?"":stmd.getPermPost());
					student.setPermAddr(stmd.getPermAddr()==null?"":stmd.getPermAddr());
					student.setBirthCounty(stmd.getBirthCounty());
					student.setBirthProvince(stmd.getBirthProvince());
					student.setDivi(stmd.getDivi());
					student.setStudentEname(stmd.getStudentEname());
					dao.saveObject(student);
					
					//step 2.設定RegistrationCard的studentNo
					RegCards = find(hql_1, new Object[]{stmd.getRegistrationCardOid()});
					if(!RegCards.isEmpty()){
						RegistrationCard rc = RegCards.get(0);
						rc.setStudentNo(studentNo);
						dao.saveObject(rc);
					}else{
						//Error?!
					}
					
					//setp 3.建立帳號密碼
					Passes = find(hql_2, new Object[]{studentNo});
					if(Passes.isEmpty()){
						WwPass pass = new WwPass();
						pass.setUsername(stmd.getStudentNo());
						pass.setPassword(stmd.getIdno());
						pass.setInformixPass(stmd.getIdno());
						pass.setPriority("C");
						pass.setUpdated(Calendar.getInstance().getTime());
						dao.saveObject(pass);
					}
					icnt++;
					
				}else{
					nonImport.add(studentNo);
					
					//建立帳號密碼
					Passes = find(hql_2, new Object[]{studentNo});
					if(Passes.isEmpty()){
						WwPass pass = new WwPass();
						pass.setUsername(stmd.getStudentNo());
						pass.setPassword(stmd.getIdno());
						pass.setInformixPass(stmd.getIdno());
						pass.setPriority("C");
						pass.setUpdated(Calendar.getInstance().getTime());
						dao.saveObject(pass);
					}
				}
				
			}catch(DataAccessException e){
				log.error(e.toString());
				//ret.put("error", stmd.getStudentNo());
				//return ret;
				throw e; 
			}
		}
		ret.put("importCount", icnt);
		ret.put("nonImport", nonImport);
		return ret;
	}

	/**
	 * 
	 * @author Oscar Wei
	 * 
	 */
	private class CalendarEventEntryComparator implements
			Comparator<CalendarEventEntry> {

		@Override
		public int compare(CalendarEventEntry entry1, CalendarEventEntry entry2) {
			DateTime starttime1 = entry1.getTimes().get(0).getStartTime();
			DateTime starttime2 = entry2.getTimes().get(0).getStartTime();
			return starttime1.compareTo(starttime2);
		}

	}



}
