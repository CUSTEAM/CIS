package tw.edu.chit.dao.hibernate;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.model.ClassScoreSummary;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.FailStudents1;
import tw.edu.chit.model.FailStudents2;
import tw.edu.chit.model.FailStudentsHist;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.MasterData;
import tw.edu.chit.model.MidtermExcluded;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Stavg;
import tw.edu.chit.model.StdScore;
import tw.edu.chit.model.Student;

public class ScoreDAOImpl extends BaseDAOHibernate implements ScoreDAO {

	@SuppressWarnings("unchecked")
	public List StandardHqlQuery(String hql) {
		return getHibernateTemplate().find(hql);
	}

	@SuppressWarnings("unchecked")
	public List StandardHqlQuery(String hql, Object[] params) {
		return getHibernateTemplate().find(hql, params);
	}

	@SuppressWarnings("unchecked")
	public List<ScoreHist> findScoreHistByStudentNo(String student_no) {
		return getHibernateTemplate().find(
				"from ScoreHist sc " + "where sc.studentNo = ? where sc.cscod!='50000' "
						+ "order by sc.schoolYear, sc.schoolTerm", student_no);

	}

	@SuppressWarnings("unchecked")
	public List submitQuery(String hql) {
		return getHibernateTemplate().find(hql);
	}

	@SuppressWarnings("unchecked")
	public List<Student> findStudentByStudentNO(String student_no) {
		return getHibernateTemplate().find(
				"select s from Student s " + "where s.studentNo = ? ",
				student_no);
	}

	@SuppressWarnings("unchecked")
	public List<Clazz> findClassByCode(String classcode) {
		return getHibernateTemplate().find(
				"select c from Clazz c " + "where c.classNo = ? ", classcode);
	}

	@SuppressWarnings("unchecked")
	public List<Csno> findCourseByCode(String cscode) {
		return getHibernateTemplate().find(
				"select c from Csno c " + "where c.cscode = ? ", cscode);
	}

	public void saveScoreHist(ScoreHist score) {
		saveObject(score);
	}

	public void removeScoreHist(ScoreHist score) {
		removeObject(score);
	}

	public int removeAnyThing(String hql) {
		return getHibernateTemplate().bulkUpdate(hql);
	}
	
	public int removeAnyThing1(String hql) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Query q = session.createQuery(hql);
		return q.executeUpdate();
	}

	/**
	 * 取得以部制查到的學生操行成績
	 * 
	 * @param departclass
	 * @return List<Just>, list of Just
	 */
	@SuppressWarnings("unchecked")
	public List<Just> findJustByDepart(String departclass) {
		return getHibernateTemplate().find(
				"select j from Just j " + "where j.departClass like '"
						+ departclass + "%'");
	}
	
	/**
	 * 
	 * @param md
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MasterData> findMasterDataBy(MasterData md) {
		return getHibernateTemplate().findByExample(md);
	}

	/**
	 * 負責以學號取得碩士成績資料之方法
	 * 
	 * @param student_no 學號
	 * @return java.util.List List of MasterData objects
	 */
	@SuppressWarnings("unchecked")
	public List<MasterData> findMasterByStudentNO(String student_no) {
		return getHibernateTemplate().find(
				"FROM MasterData md WHERE md.studentNo = ?", student_no);
	}

	/**
	 * 負責以ID取得MasterData
	 * 
	 * @param id Identity
	 * @return tw.edu.chit.model.MasterData object
	 */
	public MasterData findMasterDataById(Integer id) {
		return (MasterData) getHibernateTemplate().load(MasterData.class, id);
	}

	/**
	 * 負責更新MasterData物件
	 * 
	 * @param tw.edu.chit.model.MasterData object
	 */
	public void modifyMasterData(MasterData md) {
		getHibernateTemplate().update(md);
	}

	/**
	 * 負責刪除MasterData物件
	 * 
	 * @param tw.edu.chit.model.MasterData object
	 */
	public void deleteMasterData(MasterData md) {
		getHibernateTemplate().delete(md);
	}

	/**
	 * 負責新增MasterData物件
	 * 
	 * @param tw.edu.chit.model.MasterData object
	 */
	public void addMasterData(MasterData md) {
		getHibernateTemplate().save(md);
	}

	/**
	 * 以班級代碼查詢期中/期末不及格與平均分數資料
	 * 
	 * @param departClass Depart Class
	 * @return ClassScoreSummary Objects
	 */
	@SuppressWarnings("unchecked")
	public ClassScoreSummary findClassScoreSummaryByDepartClass(
			String departClass) {
		String hql = "FROM ClassScoreSummary c WHERE c.departClass = ?";
		List<ClassScoreSummary> css = getHibernateTemplate().find(hql,
				new Object[] { departClass });
		if (css != null && !css.isEmpty())
			return css.get(0);
		else
			return null;
	}

	/**
	 * 以班級代碼查詢期中/期末不及格與平均分數資料
	 * 
	 * @param css tw.edu.chit.model.ClassScoreSummary Objects
	 * @return tw.edu.chit.model.ClassScoreSummary Objects
	 */
	@SuppressWarnings("unchecked")
	public ClassScoreSummary findClssScoreSummaryBy(ClassScoreSummary css) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Criteria criteria = session.createCriteria(ClassScoreSummary.class);
		criteria.add(Example.create(css));
		List<ClassScoreSummary> lists = criteria.list();
		if (!lists.isEmpty())
			return lists.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<ClassScoreSummary> findClassScoreSummary() {
		return getHibernateTemplate().find(
				"FROM ClassScoreSummary ORDER BY departClass");
	}

	/**
	 * 查詢MidtermExcluded物件
	 * 
	 * @param me tw.edu.chit.models.MidtermExcluded Object
	 * @return tw.edu.chit.models.MidtermExcluded Object
	 */
	@SuppressWarnings("unchecked")
	public MidtermExcluded findMidtermExcludedBy(MidtermExcluded me) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Criteria criteria = session.createCriteria(MidtermExcluded.class);
		criteria.add(Example.create(me));
		List<MidtermExcluded> lists = criteria.list();
		if (!lists.isEmpty())
			return lists.get(0);
		else
			return null;
	}
	
	/**
	 * 查詢MidtermExcluded物件
	 * 
	 * @param me tw.edu.chit.models.MidtermExcluded Object
	 * @return tw.edu.chit.models.MidtermExcluded Object
	 */
	@SuppressWarnings("unchecked")
	public List<MidtermExcluded> findMidtermExcludedBy1(MidtermExcluded me) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Criteria criteria = session.createCriteria(MidtermExcluded.class);
		criteria.add(Example.create(me));
		List<MidtermExcluded> ret = criteria.list();
		return ret;
	}

	/**
	 * 查詢ClassScoreSummary物件
	 * 
	 * @param css tw.edu.chit.models.ClassScoreSummary Object
	 * @return tw.edu.chit.models.ClassScoreSummary Object
	 */
	@SuppressWarnings("unchecked")
	public ClassScoreSummary findClassScoreSummaryBy(ClassScoreSummary css) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		List<ClassScoreSummary> lists = session.createCriteria(
				ClassScoreSummary.class).add(Example.create(css)).list();
		if (!lists.isEmpty())
			return lists.get(0);
		else
			return css;
	}

	/**
	 * 查詢ClassScoreSummary物件
	 * 
	 * @comment 包括Like Expression
	 * @param css tw.edu.chit.models.ClassScoreSummary Object
	 * @return java.util.List List of tw.edu.chit.models.ClassScoreSummary
	 *         Objects
	 */
	@SuppressWarnings("unchecked")
	public List<ClassScoreSummary> findClassScoreSummaryByLikeExpression(
			ClassScoreSummary css) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return session.createCriteria(ClassScoreSummary.class).add(
				Example.create(css).enableLike(MatchMode.START)).addOrder(
				Order.asc("departClass")).list();
	}

	/**
	 * 查詢Stavg物件
	 * 
	 * @comment 包括Like Expression
	 * @param css tw.edu.chit.models.Stavg Object
	 * @return java.util.List List of tw.edu.chit.models.Stavg Objects
	 */
	@SuppressWarnings("unchecked")
	public List<Stavg> findStavgBy(Stavg stavg) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return session.createCriteria(Stavg.class).add(
				Example.create(stavg).enableLike(MatchMode.START)).addOrder(
				Order.asc("schoolYear")).addOrder(Order.asc("schoolTerm"))
				.list();
	}

	/**
	 * 查詢所有1/2不及格學生
	 * 
	 * @param fs tw.edu.chit.models.FailStudents1 Object
	 * @return java.util.List List of tw.edu.chit.models.FailStudents1 Objects
	 */
	@SuppressWarnings("unchecked")
	public List<FailStudents1> findAllFailStudents1(FailStudents1 fs) {
		return getHibernateTemplate().find(
				"FROM FailStudent1 fs1 WHERE fs1.studentNo = ?",
				fs.getStudentNo());
		// Session session = getHibernateTemplate().getSessionFactory()
		// .getCurrentSession();
		// return session.createCriteria(FailStudents1.class).add(
		// Example.create(fs)).addOrder(Order.asc("studentNo")).list();
	}

	/**
	 * 查詢所有2/3不及格學生
	 * 
	 * @param fs tw.edu.chit.models.FailStudents1 Object
	 * @return java.util.List List of tw.edu.chit.models.FailStudents1 Objects
	 */
	@SuppressWarnings("unchecked")
	public List<FailStudents2> findAllFailStudents2(FailStudents2 fs) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return session.createCriteria(FailStudents2.class).add(
				Example.create(fs)).addOrder(Order.asc("studentNo")).list();
	}

	/**
	 * 查詢學生歷年成績
	 * 
	 * @param fs tw.edu.chit.models.ScoreHist Object
	 * @return java.util.List List of tw.edu.chit.models.ScoreHist Objects
	 */
	@SuppressWarnings("unchecked")
	public List<ScoreHist> findScoreHistBy(ScoreHist scoreHist) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		
		
		return session.createCriteria(ScoreHist.class).add(Example.create(scoreHist)).addOrder(Order.asc("schoolYear")).list();
	}

	/**
	 * 查詢95.2之1/2不及格學生清單
	 * 
	 * @param failStudentsHist tw.edu.chit.models.FailStudentsHist Object
	 * @return java.util.List List of tw.edu.chit.models.FailStudentsHist
	 *         Objects
	 */
	@SuppressWarnings("unchecked")
	public List<FailStudentsHist> findFailStudentsHistBy(
			FailStudentsHist failStudentsHist) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return session.createCriteria(FailStudentsHist.class).add(
				Example.create(failStudentsHist)).addOrder(
				Order.asc("studentNo")).list();
	}
	
	/**
	 * 查詢學生期中/學期成績
	 * 
	 * @commend 以名次做排序
	 * @param failStudentsHist tw.edu.chit.models.StdScore Object
	 * @return java.util.List List of tw.edu.chit.models.StdScore Objects
	 */
	@SuppressWarnings("unchecked")
	public List<StdScore> findStdScoreBy(StdScore stdScore) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return session.createCriteria(StdScore.class).add(
				Example.create(stdScore)).addOrder(Order.asc("pos")).list();

	}
	
	/**
	 * 尋找班級
	 * 
	 * @commend 排除通識課程
	 * @param clazz tw.edu.chit.models.Clazz Object
	 * @param restrictionClass Restriction's Class Array
	 * @param includeLiteracy Is include Literacy Class
	 * @return java.util.List List of Clazz Objects
	 */
	@SuppressWarnings("unchecked")
	public List<Clazz> findClassBy(Clazz clazz, Clazz[] restrictionClass,
			boolean includeLiteracy) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Criteria criteria = session.createCriteria(Clazz.class);
		if (!ArrayUtils.isEmpty(restrictionClass)) {
			String[] classAry = new String[restrictionClass.length];
			int i = 0;
			for (Clazz clazzInCharge : restrictionClass) {
				classAry[i++] = clazzInCharge.getClassNo();
			}
			criteria.add(Restrictions.in("classNo", classAry));
		}

		if (includeLiteracy)
			return criteria.add(
					Example.create(clazz).enableLike(MatchMode.START)).list();
		else
			return criteria.add(
					Example.create(clazz).enableLike(MatchMode.START)).add(
					Restrictions.ne("deptNo", "0")).addOrder(
					Order.asc("classNo")).list();
	}

}
