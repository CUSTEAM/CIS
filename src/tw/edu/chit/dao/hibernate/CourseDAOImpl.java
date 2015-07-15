package tw.edu.chit.dao.hibernate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import tw.edu.chit.dao.CourseDAO;
import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.CourseSyllabus;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DepartmentInfo;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.DtimeExam;
import tw.edu.chit.model.DtimeTeacher;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Gmark;
import tw.edu.chit.model.LiteracyType;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Nabbr;
import tw.edu.chit.model.Opencs;
import tw.edu.chit.model.Regs;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.StdAdcd;
import tw.edu.chit.model.StdLoan;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.Syllabus;

public class CourseDAOImpl extends BaseDAOHibernate implements CourseDAO {

	protected static final Logger log = Logger.getLogger(CourseDAOImpl.class);

	/**
	 * 標準HQL查詢
	 */
	public List StandardHqlQuery(String hql) {

		return getHibernateTemplate().find((hql));
	}

	public List getCourse(String no, String name, String ename) {

		DetachedCriteria Dcriteria = DetachedCriteria.forClass(Csno.class);
		Dcriteria.add(Expression.like("cscode", "%" + no + "%"));
		Dcriteria.add(Expression.like("chiName", "%" + name + "%"));
		if (ename != null && ename.length() > 1) {
			Dcriteria.add(Expression.like("engName", "%" + ename + "%"));
		}

		// Dcriteria.add(Expression.isNull("engName"));

		Criteria criteria = Dcriteria.getExecutableCriteria(getSession());

		return criteria.list();
	}

	public void removeCsno(Csno csno) {

		removeObject(csno);
	}

	public void saveCsno(Csno csno) {
		saveObject(csno);
	}

	public List getDtimeForDeleteCourseName(String cscode) {

		DetachedCriteria Dcriteria = DetachedCriteria.forClass(Dtime.class);
		Dcriteria.add(Expression.like("cscode", cscode));
		Criteria criteria = Dcriteria.getExecutableCriteria(getSession());

		return criteria.list();
	}

	/**
	 * get full Csno for OpenCourse.jsp's JavaScript
	 */
	public List getCsnoForOpenCs() {

		DetachedCriteria Dcriteria = DetachedCriteria.forClass(Csno.class);
		Criteria criteria = Dcriteria.getExecutableCriteria(getSession());

		return criteria.list();
	}

	/**
	 * get full Empl for OpenCourse.jsp's JavaScript
	 */
	public List getEmployeeForOpenCS() {

		DetachedCriteria Dcriteria = DetachedCriteria.forClass(Empl.class);
		Criteria criteria = Dcriteria.getExecutableCriteria(getSession());

		return criteria.list();
	}

	/**
	 * get full Class for OpenCourse.jsp's JavaScript
	 */
	public List getClassForOpenCs() {

		DetachedCriteria Dcriteria = DetachedCriteria.forClass(Clazz.class);
		Criteria criteria = Dcriteria.getExecutableCriteria(getSession());

		return criteria.list();
	}

	public List getDtimeForOpenCs(String sql) {

		return getHibernateTemplate().find((sql));
	}

	/**
	 * get a dtime for OpenCourse.jsp==>edit.
	 */
	public List getDtimeForEditDtime(String sql) {

		return getHibernateTemplate().find((sql));
	}

	/**
	 * get a full teacher information for OpenCourse.jsp
	 */
	public List getEmplForOpenCourse(String sql) {

		return getHibernateTemplate().find((sql));
	}

	/**
	 * get Dtime-time for OpenCourse.jsp view
	 */
	public List getDtimeClassListForOpenCourse(String sql) {

		return getHibernateTemplate().find((sql));
	}

	/**
	 * get DtimeExam-teme for OpenCourse.jsp view/edit
	 */
	public List getDtimeExamListForOpenCourse(String sql) {

		return getHibernateTemplate().find((sql));
	}

	/**
	 * check course
	 */
	public List checkCourseForOpenCourse(String sql) {

		return getHibernateTemplate().find((sql));
	}

	/**
	 * check empl
	 */
	public List checkEmplForOpenCourse(String sql) {

		return getHibernateTemplate().find((sql));
	}

	/**
	 * check reopen course
	 */
	public List checkRushCourseForOpenCourse(String sql) {

		return getHibernateTemplate().find((sql));
	}

	/**
	 * get now school term.
	 */
	public List getNowTerm(String sql) {

		return getHibernateTemplate().find((sql));
	}

	/**
	 * only for openCourse.jsp examEmpl Cname.
	 */
	public List getExamTeacher(String sql1) {

		return getHibernateTemplate().find((sql1));
	}

	public List checkSeldForCourseName(String cscode) {

		return getHibernateTemplate().find((cscode));
	}

	public List getModifyResult(String sql) {

		return getHibernateTemplate().find((sql));
	}

	public List checkDtimeClassForOpenCourse(String sql) {

		return getHibernateTemplate().find((sql));
	}

	public List checkNabbrForOpenCs(String sql) {

		return getHibernateTemplate().find((sql));
	}

	public List getDtimeByOid(String sql) {

		return getHibernateTemplate().find((sql));
	}

	/**
	 * 處理以學號查詢學生已選課資訊
	 * 
	 * @param studentNo 學號
	 * @param term 學期
	 * @return java.util.List List of Objects
	 */
	@SuppressWarnings("unchecked")
	public List getSeldDataByStudentNo(String studentNo, String term) {
		String hql = "SELECT c.classNo, c.className, cs.cscode, "
				+ "cs.chiName, d.stuSelect, d.selectLimit, "
				+ "d.thour, d.credit, s.oid, d.opt, d.Oid, d.sterm, "
				+ "dc.week, dc.begin, dc.end, d.elearning "
				+ "FROM Seld s, Dtime d, Clazz c, Csno cs, DtimeClass dc "
				+ "WHERE s.dtimeOid = d.Oid AND d.Oid = dc.dtimeOid "
				+ "AND d.departClass = c.classNo "
				+ "AND d.cscode = cs.cscode AND s.studentNo = ? "
				+ "AND d.sterm = ? ORDER BY d.Oid, dc.week";
		return getHibernateTemplate().find(hql,
				new Object[] { studentNo, term });
	}
	
	/**
	 * 新增或修改一門課程
	 */
	public void saveDtime(Dtime dtime) {

		saveObject(dtime);
	}

	/**
	 * 檢查教師同時段重複開課
	 */
	public List checkEmplForReOpenCourse(String sql) {

		return getHibernateTemplate().find((sql));
	}

	/**
	 * 檢查教室同時段重複使用
	 */
	public List checkReOpenRoom(String sql) {
		return getHibernateTemplate().find((sql));
	}

	/**
	 * 新增或修改一門課的上課區段
	 */
	public void saveDtimeClass(DtimeClass dtimeClass) {
		saveObject(dtimeClass);
	}

	/**
	 * 儲存考試時間資料
	 */
	public void saveDtimeExam(DtimeExam dtimeExam) {

		saveObject(dtimeExam);
	}

	/**
	 * 實作以Oid取得Seld物件
	 * 
	 * @param oid Seld Oid
	 * @return Seld tw.edu.chit.model.Seld object
	 */
	public Seld getSeldByOid(Integer oid) {
		return (Seld) this.getObject(Seld.class, oid);
	}

	/**
	 * 實作新增或更新Seld物件
	 * 
	 * @param seld tw.edu.chit.model.Seld object
	 */
	public void saveOrUpdateSeld(Seld seld) {
		saveObject(seld);
	}

	/**
	 * 處理退選課程,以Seld Oid列表刪除學生選課資料
	 * 
	 * @param seldOids Seld Oid Listing(SQL IN SYNTAX)
	 * @return String 所有刪除Seld資料表之Dtime_oid列表
	 */
	@SuppressWarnings("unchecked")
	public String deleteSeldByOids(String seldOids) {
		StringBuffer hql = new StringBuffer("FROM Seld s WHERE s.oid IN (");
		hql.append(seldOids).append(")");
		log.info(hql.toString());
		List<Seld> selds = (List<Seld>) getHibernateTemplate().find(
				hql.toString());
		StringBuffer dtimeOids = new StringBuffer();
		// 收集Seld資料表內Dtime_oid,作為更新Dtime資料表選課人數依據
		if (!selds.isEmpty()) {
			// dtimeOids = new StringBuffer();
			HibernateTemplate ht = getHibernateTemplate();
			for (Seld seld : selds) {
				if (seld.getDtimeOid() != null) {
					dtimeOids.append(seld.getDtimeOid()).append(",");
				}
				ht.delete(seld);
			}
		}
		
		return StringUtils.substringBeforeLast(dtimeOids.toString(), ",");
	}

	/**
	 * 實作以Seld物件查詢Seld物件
	 * 
	 * @commend 此方法主要為新增Seld€時檢查是否有無重複資料之判斷
	 * @param seld tw.edu.chit.model.Seld object
	 * @return java.util.List List of Seld objects
	 */
	@SuppressWarnings("unchecked")
	public List<Seld> findSeldBy(Seld seld) {
		StringBuffer hql = new StringBuffer("from Seld s ");
		hql.append("where s.dtimeOid = ? ");
		hql.append("and s.studentNo = ?");
		return getHibernateTemplate().find(hql.toString(),
				new Object[] { seld.getDtimeOid(), seld.getStudentNo() });
	}

	/**
	 * 處理以Dtime資料表Oid列表,更新Dtime資料表學生選課stu_select人數
	 * 
	 * @commend 以悲觀Locking鎖定Dtime物件
	 * @param dtimeOids Seld資料表所取得之Dtime_oid列表
	 * @param count 加/減人數
	 */
	@SuppressWarnings("unchecked")
	public void updateDtimeStuSelectByOids(String dtimeOids, Short count) {
		StringBuffer hql = new StringBuffer("from Dtime d where d.oid in (");
		hql.append(dtimeOids).append(")");
		log.info(hql.toString());
		HibernateTemplate ht = getHibernateTemplate();
		Session session = getSession();
		List<Dtime> dtimes = ht.find(hql.toString());
		// 選課人數加/減
		for (Dtime dtime : dtimes) {
			// Added by James to filter out null stuSelect
			if (!(dtime.getStuSelect() == null || (count < 0 && dtime
					.getStuSelect().intValue() == 0))) {
				Dtime d = (Dtime) session.load(Dtime.class, dtime.getOid(),
						LockMode.UPGRADE);
				d.setStuSelect(Short.valueOf((short) (dtime.getStuSelect()
						.shortValue() + count.shortValue())));
				saveDtime(d);
				session.lock(d, LockMode.NONE);
			}
		}
		session.close();
	}

	/**
	 * 處理刪除Adcd資料表之方法
	 * 
	 * @param adcd tw.edu.chit.model.Adcd object
	 */
	public void deleteAdcd(Adcd adcd) {
		getHibernateTemplate().delete(adcd);
	}
	
	/**
	 * 處理刪除StdLoan資料表之方法
	 * 
	 * @param stdLoan tw.edu.chit.model.StdLoan object
	 */
	public void deleteStdLoan(StdLoan stdLoan) {
		getHibernateTemplate().delete(stdLoan);
	}

	/**
	 * 處理新增Adcd資料表之方法
	 * 
	 * @param adcd tw.edu.chit.model.Adcd object
	 */
	public void saveOrUpdateAdcd(Adcd adcd) {
		saveObject(adcd);
	}

	/**
	 * 實作以cscode取得Csno物件之方法
	 * 
	 * @param cscode 科目代碼
	 * @return Csno tw.edu.chit.model.Csno object
	 */
	public Csno getCsnoByCscode(final String cscode) {
		return (Csno) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session
						.createQuery("from Csno c where c.cscode = :cscode");
				query.setString("cscode", cscode);
				return query.uniqueResult();
			}
		});
	}

	/**
	 * 實作依據科目代碼與班級代碼查詢開課基本資料
	 * 
	 * @param classNo 班級代碼
	 * @param csCode 科目代碼
	 * @param sterm 學期
	 * @return Dtime seld tw.edu.chit.model.Dtime object
	 */
	public Dtime getDtimeByCsCodeAndClassNo(final String classNo,
			final String csCode, final String sterm) {
		return (Dtime) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "from Dtime d "
						+ "where d.departClass = :dc and d.cscode = :cc "
						+ "and d.sterm = :sterm";
				Query query = session.createQuery(hql);
				query.setString("dc", classNo);
				query.setString("cc", csCode);
				query.setString("sterm", sterm);
				return query.uniqueResult();
			}
		});
	}

	/**
	 * 實作以學生資料物件查詢開課資料
	 * 
	 * @param student tw.edu.chit.model.Student object
	 * @return java.util.List List of Object array
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getOpencsByStudnet(Student student) {
		String hql = "select o.departClass, o.cscode, c.chiName, e.cname, "
				+ "dc.week, dc.begin, dc.end, d.opt, d.credit, "
				+ "d.thour, d.stuSelect, d.selectLimit, o.oid, cl.className "
				+ "from Opencs o, Csno c, Dtime d, DtimeClass dc, "
				+ "Empl e, Clazz cl "
				+ "where o.cscode = c.cscode and o.dtimeOid = d.oid "
				+ "and d.oid = dc.dtimeOid and d.techid = e.idno "
				+ "and o.departClass = cl.classNo " + "and o.departClass = ? "
				+ "order by d.opt, dc.week";
		return (List<Object[]>) getHibernateTemplate().find(hql,
				student.getDepartClass());

	}

	/**
	 * 實作以學生班級資料物件查詢開課資料
	 * 
	 * @commend 包括低年級課程資料
	 * @param clazz tw.edu.chit.model.Clazz object
	 * @param dtimeOid Dtime Oid
	 * @return java.util.List List of Object array
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getOpencsByStudnetClass(Clazz clazz, Integer dtimeOid) {
		Object[] params = null;
		/*
		 * String hql = "SELECT o.departClass, o.cscode, c.chiName, e.cname, " +
		 * "dc.week, dc.begin, dc.end, d.opt, d.credit, " + "d.thour,
		 * d.stuSelect, d.selectLimit, o.oid, cl.className, " + "d.oid,
		 * c.engName, e.ename, cl.grade, cl.deptNo " + "FROM Opencs o, Csno c,
		 * Dtime d, DtimeClass dc, " + "Empl e, Clazz cl " + "WHERE o.cscode =
		 * c.cscode AND o.dtimeOid = d.oid " + "AND d.oid = dc.dtimeOid AND
		 * d.techid = e.idno " + "AND o.departClass = cl.classNo " + "AND
		 * (o.cidno = '*' OR o.cidno = ?) " + "AND (o.sidno = '*' OR o.sidno = ?) " +
		 * "AND (o.didno = '*' OR o.didno = ?) " + "AND (o.grade = '*' OR
		 * o.grade <= ?) "; + "and (o.classNo = ? and d.opt = '1') ";
		 */
		String sql = "SELECT d.oid, cl.ClassName, c.chi_name, e.cname, d.opt, "
				+ "d.credit, d.thour, d.stu_select, dc.week, dc.begin, "
				+ "dc.end, n.room_id, n.name2 "
				+ "FROM Dtime d, Opencs op, Csno c, Dtime_class dc, Nabbr n, "
				+ "Class cl LEFT JOIN empl e on d.techid = e.idno "
				+ "WHERE d.cscode = c.cscode " + "AND d.oid = op.Dtime_oid "
				+ "AND d.oid = dc.Dtime_oid "
				+ "AND d.depart_class = cl.ClassNo "
				+ "AND dc.place = n.room_id " + "AND d.sterm = ? "
				+ "AND (op.`Cidno` = '*' OR op.`Cidno` = ?) "
				+ "AND (op.`Sidno` = '*' OR op.`Sidno` = ?) "
				+ "AND (op.`Didno` = '*' OR op.`Didno` = ?) "
				+ "AND (op.`Grade` = '*' OR op.`Grade` <= ?)"
				+ "ORDER BY d.opt, dc.week";
		if (dtimeOid != null) {
			sql += "and o.dtimeOid = ? ";
			params = new Object[] { clazz.getCampusNo(), clazz.getSchoolNo(),
					clazz.getDeptNo(), clazz.getGrade(), dtimeOid };
		} else {
			params = new Object[] { clazz.getCampusNo(), clazz.getSchoolNo(),
					clazz.getDeptNo(), clazz.getGrade() };
		}
		return (List<Object[]>) getHibernateTemplate().find(sql, params);
	}

	/**
	 * 實作以Dtime Oid取得Opencs清單資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @return java.util.List List of Opencs objects
	 */
	@SuppressWarnings("unchecked")
	public List<Opencs> findOpencsByDtimeOid(Integer dtimeOid) {
		String hql = "from Opencs cs where cs.dtimeOid = ?";
		return getHibernateTemplate().find(hql, new Object[] { dtimeOid });
	}

	/**
	 * 實作以依據老師取得開課主檔資料
	 * 
	 * @param member tw.edu.chit.model.Member object
	 * @return java.util.List List of Object array
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getDtimeByTeacher(Member member) {
		String hql = "select cl.className, cs.chiName, d.oid, cs.cscode, "
				+ "cl.classNo, d.sterm, cs.engName "
				+ "from Dtime d, Csno cs, Clazz cl "
				+ "where d.cscode = cs.cscode and d.departClass = cl.classNo "
				+ "and d.techid = ? order by d.departClass";
		return getHibernateTemplate().find(hql, member.getAccount());
	}
	
	/**
	 * 實作以依據老師取得一科目多教師資料
	 * 
	 * @param member tw.edu.chit.model.Member object
	 * @return java.util.List List of Object array
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getDtimeTeacherByTeacher(Member member) {
		String hql = "SELECT cl.className, cs.chiName, d.oid, cs.cscode, "
				+ "cl.classNo, d.sterm, cs.engName "
				+ "FROM Dtime d, DtimeTeacher dt, Csno cs, Clazz cl "
				+ "WHERE d.oid = dt.dtimeOid AND d.cscode = cs.cscode AND "
				+ "d.departClass = cl.classNo "
				+ "AND dt.teachId = ? ORDER BY d.departClass";
		return getHibernateTemplate().find(hql, member.getAccount());
	}

	/**
	 * 實作以依據老師取得開課主檔資料
	 * 
	 * @param member tw.edu.chit.model.Member object
	 * @param term School Term
	 * @return java.util.List List of Object array
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getDtimeByTeacher(Member member, String term) {
		String hql = "SELECT cl.className, cs.chiName, d.oid, cs.cscode, "
				+ "cl.classNo, d.sterm, cs.engName, d.credit, d.thour, d.stuSelect "
				+ "FROM Dtime d, Csno cs, Clazz cl "
				+ "WHERE d.cscode = cs.cscode AND d.departClass = cl.classNo "
				+ "AND d.techid = ? AND d.sterm = ? ORDER BY d.departClass";
		return getHibernateTemplate().find(hql,
				new Object[] { member.getAccount(), term });
	}

	/**
	 * 實作以依據老師取得歷年開課主檔資料
	 * 
	 * @param member tw.edu.chit.model.Member object
	 * @param term School Term
	 * @return java.util.List List of Object array
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getDtimeHistByTeacher(Member member, String year,
			String term) {
		String hql = "SELECT cl.className, cs.chiName, d.oid, cs.cscode, "
				+ "cl.classNo, d.schoolTerm, cs.engName "
				+ "FROM Savedtime d, Csno cs, Clazz cl "
				+ "WHERE d.cscode = cs.cscode AND d.departClass = cl.classNo "
				+ "AND d.techid = ? AND d.schoolYear = ? AND d.schoolTerm = ? "
				+ "ORDER BY d.departClass";
		return getHibernateTemplate().find(
				hql,
				new Object[] { member.getAccount(), Short.valueOf(year),
						Short.valueOf(term) });
	}

	/**
	 * 實作以依據老師取得開課主檔資料
	 * 
	 * @param member tw.edu.chit.model.Member object
	 * @return java.util.List List of Object array
	 */
	@SuppressWarnings("unchecked")
	public List findDtimeByTeacher(Member member) {
		String hql = "from Dtime d, Csno cs, Clazz cl "
				+ "where d.cscode = cs.cscode and d.departClass = cl.classNo "
				+ "and d.techid = ? order by d.departClass";
		return getHibernateTemplate().find(hql, member.getAccount());
	}

	/**
	 * 實作更新Dtime資料表目前選課人數
	 * 
	 * @param dtimeOid Dtime物件之Oid
	 * @param stdSel 欲更新Dtime資料表目前選課人數欄位之值
	 */
	public void updateDtimeStdSelect(Integer dtimeOid, Short stdSel) {
		try {
			Dtime dtime = (Dtime) getObject(Dtime.class, dtimeOid);
			dtime.setStuSelect(stdSel);
			saveDtime(dtime);
		} catch (Exception e) {
		}
	}

	/**
	 * 實作儲存一科目多教師
	 */
	public void SaveDtimeTeacher(DtimeTeacher dtimeTeacher) {
		getHibernateTemplate().save(dtimeTeacher);
	}

	/**
	 * 實作將班級學生之基本選課資料刪除
	 * 
	 * @param dtime java.util.Map object
	 * @param students List of Student objects
	 * @return int 回傳刪除個數
	 */
	@SuppressWarnings("unchecked")
	public int delSeldByDtimeAndStudents(Map dtime, List<Student> students) {
		StringBuffer hql = new StringBuffer("from Seld s where ");
		hql.append("s.dtimeOid = '").append(dtime.get("oid")).append("' ");
		hql.append("and s.studentNo in (").append(processInSyntax(students))
				.append(")");
		List<Seld> selds = getHibernateTemplate().find(hql.toString());
		HibernateTemplate ht = getHibernateTemplate();
		for (Seld seld : selds)
			ht.delete(seld);
		return selds.size();
	}

	/**
	 * 實作刪除學生清單在Adcd中之基本選課紀錄資料
	 * 
	 * @param dtime java.util.Map object
	 * @param students List of Student objects
	 * @return int 回傳刪除個數
	 */
	@SuppressWarnings("unchecked")
	public int delAdcdByDtimeAndStudents(Map dtime, List<Student> students) {
		StringBuffer hql = new StringBuffer("from Adcd a where ");
		hql.append("a.dtimeOid in (").append(dtime.get("oid")).append(") ");
		hql.append("and a.studentNo in (").append(processInSyntax(students))
				.append(")");
		List<Adcd> adcds = getHibernateTemplate().find(hql.toString());
		HibernateTemplate ht = getHibernateTemplate();
		for (Adcd adcd : adcds)
			ht.delete(adcd);
		return adcds.size();
	}

	/**
	 * 以Dtime Oid取得加退選紀錄
	 * 
	 * @param dtimeOid Dtime OId
	 * @return java.util.List List of Adcd objects
	 */
	@SuppressWarnings("unchecked")
	public List<Adcd> getAdcdByDtimeOid(Integer dtimeOid) {
		String hql = "FROM Adcd a WHERE a.dtimeOid = ? ORDER BY a.adddraw DESC";
		return getHibernateTemplate().find(hql, dtimeOid);
	}

	/**
	 * 以學生學號取得加退選紀錄
	 * 
	 * @param studentNo 學生學號
	 * @param term 學期
	 * @return java.util.List List of Adcd objects
	 */
	@SuppressWarnings("unchecked")
	public List<StdAdcd> getStudentAdcdByStudentNo(String studentNo, String term) {
		String hql = "SELECT a FROM StdAdcd a, Dtime d WHERE d.oid = a.dtimeOid "
				+ "AND d.sterm = ? AND a.studentNo = ? "
				+ "ORDER BY a.lastModified, a.adddraw DESC";
		return getHibernateTemplate().find(hql,
				new Object[] { term, studentNo });
	}

	/**
	 * 實作刪除學生清單在Regs中之基本資料
	 * 
	 * @param dtime java.util.Map object
	 * @param students List of Student objects
	 * @return int 回傳刪除個數
	 */
	@SuppressWarnings("unchecked")
	public int delRegsByDtimeAndStudents(Map dtime, List<Student> students) {
		StringBuffer hql = new StringBuffer("from Regs r where ");
		hql.append("r.dtimeOid = '").append(dtime.get("oid")).append("' ");
		hql.append("and r.studentNo in (").append(processInSyntax(students))
				.append(")");
		List<Regs> regs = getHibernateTemplate().find(hql.toString());
		HibernateTemplate ht = getHibernateTemplate();
		for (Regs reg : regs)
			ht.delete(reg);
		return regs.size();
	}

	/**
	 * 實作刪除某學生在Regs中之基本選課紀錄資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param studentNo 學號
	 */
	@SuppressWarnings("unchecked")
	public void delRegsByDtimeAndStdNo(String dtimeOid, String studentNo) {
		StringBuffer hql = new StringBuffer("from Regs r where ");
		hql.append("r.dtimeOid = '").append(dtimeOid).append("' ");
		hql.append("and r.studentNo = '").append(studentNo).append("'");
		List<Regs> regs = getHibernateTemplate().find(hql.toString());
		if (!regs.isEmpty()) {
			for (Regs reg : regs)
				removeObject(reg);
		}
	}

	/**
	 * 實作新增/更新CourseIntroduction資料表資料
	 * 
	 * @param ci tw.edu.chit.model.CourseIntroduction object
	 */
	public void saveCourseIntro(CourseIntroduction ci) {
		getHibernateTemplate().saveOrUpdate(ci);
	}

	public void txsaveOpencsBy(Opencs opencs) {
		getHibernateTemplate().save(opencs);
	}

	/**
	 * 實作以Dtime Oid及學年與學期取得CourseIntroduction清單資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	@SuppressWarnings("unchecked")
	public List<CourseIntroduction> getCourseIntroByDtimeOid(Integer dtimeOid,
			Integer year, Integer term) {
		String hql = "FROM CourseIntroduction ci WHERE ci.dtimeOid = ? "
				+ "AND ci.schoolYear = ? AND ci.schoolTerm = ?";
		return getHibernateTemplate().find(hql,
				new Object[] { dtimeOid, year, term });
	}

	/**
	 * 取得CourseIntroduction歷年清單資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @param departClass Depart Class
	 * @param cscode Cscode
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	@SuppressWarnings("unchecked")
	public List<CourseIntroduction> getCourseIntroHistBy(Integer year,
			Integer term, String departClass, String cscode) {
		String hql = "FROM CourseIntroduction ci WHERE ci.schoolYear = ? "
				+ "AND ci.schoolTerm = ? AND ci.departClass = ? "
				+ "AND ci.cscode = ?";
		return getHibernateTemplate().find(hql,
				new Object[] { year, term, departClass, cscode });
	}

	/**
	 * 實作以Oid取得CourseIntroduction清單資料
	 * 
	 * @param oid CourseIntroduction Oid
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public CourseIntroduction getCourseIntroByOid(Integer oid) {
		return (CourseIntroduction) getHibernateTemplate().get(
				CourseIntroduction.class, oid);
	}

	/**
	 * 實作新增/更新Regs資料表資料
	 * 
	 * @param regs tw.edu.chit.model.Regs object
	 */
	public void saveOrUpdateRegs(Regs regs) {
		getHibernateTemplate().saveOrUpdate(regs);
	}

	/**
	 * 實作以Dtime Oid及學年與學期取得CourseIntroduction清單資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	@SuppressWarnings("unchecked")
	public CourseIntroduction getCourseIntrosByDtimeOid(Integer dtimeOid,
			Integer year, Integer term) {
		String hql = "FROM CourseIntroduction ci WHERE ci.dtimeOid = ? "
				+ "AND ci.schoolYear = ? AND ci.schoolTerm = ?";
		List<CourseIntroduction> list = getHibernateTemplate().find(hql,
				new Object[] { dtimeOid, year, term });
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	/**
	 * 以CourseIntroduction取得資料
	 * 
	 * @param ci
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CourseIntroduction getCourseIntroBy(CourseIntroduction ci) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
		} catch (Exception e) {
			session = getHibernateTemplate().getSessionFactory().openSession();
		}
		List<CourseIntroduction> cis = session.createCriteria(
				CourseIntroduction.class).add(Example.create(ci)).list();
		if (!cis.isEmpty()) {
			return cis.get(0);
		} else
			return null;
	}

	/**
	 * 實作以Dtime Oid及學年與學期取得CourseSyllabus清單資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	@SuppressWarnings("unchecked")
	public CourseSyllabus getCourseSyllabusByDtimeOid(Integer dtimeOid,
			Integer year, Integer term) {
		String hql = "FROM CourseSyllabus ci WHERE ci.dtimeOid = ? "
				+ "AND ci.schoolYear = ? AND ci.schoolTerm = ?";
		List<CourseSyllabus> list = getHibernateTemplate().find(hql,
				new Object[] { dtimeOid, year, term });
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}
	
	/**
	 * 實作以Dtime Oid及學年與學期取得CourseSyllabus清單資料
	 * 
	 * @param departClass Depart Class
	 * @param cscode Cscode
	 * @param year School Year
	 * @param term School Term
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	@SuppressWarnings("unchecked")
	public CourseSyllabus getCourseSyllabusByDtimeOid(String departClass, String cscode,
			Integer year, Integer term) {
		String hql = "FROM CourseSyllabus ci WHERE ci.departClass = ? AND ci.cscode = ? "
				+ "AND ci.schoolYear = ? AND ci.schoolTerm = ?";
		List<CourseSyllabus> list = getHibernateTemplate().find(hql,
				new Object[] { departClass, cscode, year, term });
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	/**
	 * 實作以Oid取得CourseSyllabus清單資料
	 * 
	 * @param oid CourseSyllabus Oid
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus getCourseSyllabusByOid(Integer oid) {

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		CourseSyllabus cs = (CourseSyllabus) session.get(CourseSyllabus.class,
				oid);
		Hibernate.initialize(cs.getSyllabuses());
		return cs;
	}

	/**
	 * 以CourseSyllabus取得資料
	 * 
	 * @commend 該程式不適合連續呼叫使用
	 * @param courseSyllabus CourseSyllabus Object
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	@SuppressWarnings("unchecked")
	public CourseSyllabus getCourseSyllabusBy(CourseSyllabus courseSyllabus) {

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		List<CourseSyllabus> courseSyllabuses = session.createCriteria(
				CourseSyllabus.class).add(Example.create(courseSyllabus))
				.list();
		if (!courseSyllabuses.isEmpty()) {
			CourseSyllabus cs = courseSyllabuses.get(0);
			Hibernate.initialize(cs.getSyllabuses());
			session.close();
			return cs;
		} else
			return null;
	}
	
	/**
	 * 以CourseSyllabus取得資料
	 * 
	 * @param courseSyllabus CourseSyllabus Object
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	@SuppressWarnings("unchecked")
	public CourseSyllabus getCourseSyllabusBy1(CourseSyllabus courseSyllabus) {

		List<CourseSyllabus> courseSyllabuses = getHibernateTemplate().findByExample(courseSyllabus);
		if (!courseSyllabuses.isEmpty()) {
			CourseSyllabus cs = courseSyllabuses.get(0);
			Hibernate.initialize(cs.getSyllabuses());
			return cs;
		} else
			return null;
	}

	/**
	 * 實作以CourseSyllabus Oid取得教學大綱Syllabus清單資料
	 * 
	 * @param oid CourseSyllabus Oid
	 * @return java.util.List List of Syllabus object
	 */
	public List<Syllabus> getSyllabusListByOid(Integer oid) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
		} catch (Exception e) {
			session = getHibernateTemplate().getSessionFactory().openSession();
		}
		CourseSyllabus cs = (CourseSyllabus) session.get(CourseSyllabus.class,
				oid);
		// 強迫Load Association
		Hibernate.initialize(cs.getSyllabuses());
		session.close();
		return cs.getSyllabuses();
	}

	/**
	 * 實作新增CourseSyllabus資料表資料
	 * 
	 * @param ci tw.edu.chit.model.CourseSyllabus object
	 */
	public void saveCourseSyllabus(CourseSyllabus cs) {
		getHibernateTemplate().saveOrUpdate(cs);
	}

	/**
	 * 實作更新CourseSyllabus資料表資料
	 * 
	 * @param ci tw.edu.chit.model.CourseSyllabus object
	 */
	public void updateCourseSyllabus(final CourseSyllabus cs) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.update(cs);
				return null;
			}
		});
	}

	/**
	 * 實作取得教師上課時間資料
	 * 
	 * @param member tw.edu.chit.model.Member object
	 * @return java.util.List List of Object array
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findTeachInfoByMember(Member member) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Dtime d, DtimeClass dc, Csno cs ");
		hql.append("where d.oid = dc.dtimeOid and d.cscode = cs.cscode ");
		hql.append("and d.techid = ?");
		return getHibernateTemplate().find(hql.toString(), member.getAccount());
	}

	/**
	 * 實作取得Dtime資料表目前選課人數
	 * 
	 * @param dtimeOid Dtime物件之Oid
	 * @return Dtime資料表目前選課人數欄位之值
	 */
	public Short getDtimeStdSelect(Integer dtimeOid) {
		Dtime dtime = (Dtime) getObject(Dtime.class, dtimeOid);
		return dtime.getStuSelect();
	}

	/**
	 * 實作以Dtime Oid更新選課人數上限
	 * 
	 * @commend 以悲觀Locking鎖定Dtime物件
	 * @param dtimeOid Dtime Oid
	 * @param selectLimit 選課人數上限
	 */
	public void updateDtimeSelLimit(String dtimeOid, Short selectLimit) {
		Session session = getSession();
		Dtime dtime = (Dtime) session.load(Dtime.class, Integer
				.valueOf(dtimeOid), LockMode.UPGRADE);
		dtime.setSelectLimit(selectLimit);
		saveObject(dtime);
		session.lock(dtime, LockMode.NONE);
		session.close();
	}

	/**
	 * 實作以Dtime Oid查詢選課學生清單
	 * 
	 * @param dtimeOid Dtime Oid
	 * @return java.util.List List of Student objects
	 */
	@SuppressWarnings("unchecked")
	public List<Student> getSeldStudentByDtimeOid(Integer dtimeOid) {
		String hql = "SELECT std FROM Seld s, Dtime d, Student std "
				+ "WHERE d.oid = ? AND d.oid = s.dtimeOid "
				+ "AND s.studentNo = std.studentNo ORDER BY s.studentNo";
		return (List<Student>) getHibernateTemplate().find(hql, dtimeOid);
	}

	/**
	 * 實作以Dtime Oid查詢選課學生清單
	 * 
	 * @param dtimeOid Dtime Oid
	 * @return java.util.List List of Student objects
	 */
	@SuppressWarnings("unchecked")
	public List<Student> getSeldStudentByStudentNo(String studentNo) {
		String hql = "SELECT std FROM Seld s, Dtime d, Student std "
				+ "WHERE d.oid = ? AND d.oid = s.dtimeOid "
				+ "AND s.studentNo = std.studentNo";
		return (List<Student>) getHibernateTemplate().find(hql, studentNo);
	}

	/**
	 * 實作以Class No查詢部制代碼
	 * 
	 * @param classNo 班級代碼
	 * @return 部制代碼,D為日間,N為夜間,H為學院
	 */
	@SuppressWarnings("unchecked")
	public String getSchoolTypeByClassNo(String classNo) {
		String hql = "SELECT c.schoolNo FROM Clazz c WHERE c.classNo = ?";
		try {
			String schoolNo = ((List<String>) getHibernateTemplate().find(hql,
					classNo)).get(0);
			hql = "SELECT c.idno FROM Code5 c WHERE c.category = 'SchoolCate' "
					+ "AND c.name = ?";
			return ((List<String>) getHibernateTemplate().find(hql, schoolNo))
					.get(0);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 實作以School No查詢部制代碼
	 * 
	 * @param School 班級代碼
	 * @return 部制代碼,D為日間,N為夜間,H為學院
	 */
	@SuppressWarnings("unchecked")
	public String getSchoolTypeBySchoolNo(String schoolNo) {
		String hql = "SELECT c.idno FROM Code5 c WHERE c.category = 'SchoolCate' "
				+ "AND c.name = ?";
		return ((List<String>) getHibernateTemplate().find(hql, schoolNo))
				.get(0);
	}

	// 產生Student NO的SQL IN Syntax
	private String processInSyntax(List<Student> students) {
		StringBuffer buf = new StringBuffer();
		for (Student std : students) {
			buf.append("'").append(std.getStudentNo()).append("',");
		}
		return StringUtils.substringBeforeLast(buf.toString(), ",");
	}

	@SuppressWarnings("unchecked")
	public List<Seld> findSeldByStudentNo(String studentNo) {
		return getHibernateTemplate().find("FROM Seld s WHERE s.studentNo = ?",
				new Object[] { studentNo });
	}

	@SuppressWarnings("unchecked")
	public List<Seld> findSeldByStudentNoAndTerm(String studentNo, String term) {
		String hql = "SELECT s FROM Seld s, Dtime d WHERE d.oid = s.dtimeOid AND "
				+ "s.studentNo = ? AND d.sterm = ? AND s.cscode!='50000'";
		return getHibernateTemplate().find(hql,
				new Object[] { studentNo, term });
	}

	public void saveNabbr(Nabbr nabbr) {
		saveObject(nabbr);
	}
	
	/**
	 * 儲存StdLoan物件
	 * 
	 * @param stdLoan
	 */
	public void saveStdLoan(StdLoan stdLoan) {
		saveObject(stdLoan);
	}

	/**
	 * 以班級代碼取得校區代碼
	 * 
	 * @param classNo 班級代碼
	 * @return 校區代碼
	 */
	@SuppressWarnings("unchecked")
	public String getCampusNoByClassNo(String classNo) {
		String hql = "FROM Clazz c WHERE c.classNo = ?";
		List<Clazz> classes = getHibernateTemplate().find(hql, classNo);
		if (classes.isEmpty())
			return null;
		else
			return classes.get(0).getCampusNo();
	}

	/**
	 * 以Dtime Oid取得Seld目前選課人數
	 * 
	 * @param dtimeOid Dtime Oid
	 * @return int 目前選課人數
	 */
	public int getSeldCountByDtimeOid(Integer dtimeOid) {
		String hql = "SELECT COUNT(*) FROM Seld s WHERE s.dtimeOid = ?";
		return ((Integer) getHibernateTemplate().iterate(hql, dtimeOid).next())
				.intValue();
	}

	/**
	 * 以學生學號查詢學生加退選結果
	 * 
	 * @param studentNo 學生學號
	 * @param term 學期
	 * @return java.util.List List of Objects
	 */
	@SuppressWarnings("unchecked")
	public List<Adcd> getAdcdByStudentNo(String studentNo, String term) {
		String hql = "SELECT a FROM Adcd a, Dtime d WHERE d.oid = a.dtimeOid "
				+ "AND d.sterm = ? AND a.studentNo = ? "
				+ "ORDER BY a.oid, a.adddraw";
		return (List<Adcd>) getHibernateTemplate().find(hql,
				new Object[] { term, studentNo });
	}

	public Dtime getDtimeBy(Integer oid) {
		return (Dtime) getObject(Dtime.class, oid);
	}

	/**
	 * Find Dtimes by depart_class and open(開放選修) or not
	 * 
	 * @param classNo
	 * @param open
	 * @return list of Dtime
	 */
	@SuppressWarnings("unchecked")
	public List<Dtime> findDtimeByClassTermOpen(String classNo, String term,
			boolean open) {
		Byte byteOpen;
		if (open)
			byteOpen = Byte.valueOf((byte) 1);
		else
			byteOpen = Byte.valueOf((byte) 0);

		return getHibernateTemplate()
				.find(
						"FROM Dtime d WHERE d.departClass = ? AND d.sterm = ? AND d.open = ?",
						new Object[] { classNo, term, byteOpen });
	}

	/**
	 * 以School No查詢各部制選課人數下限
	 * 
	 * @param schoolNo School No
	 * @return 各部制選課人數下限
	 */
	@SuppressWarnings("unchecked")
	public int getMinCountBySchoolNo(String schoolNo) {
		String hql = "FROM Code5 c WHERE c.category = 'MinCount' AND c.name = ?";
		return Integer.parseInt(((List<Code5>) getHibernateTemplate().find(hql,
				schoolNo)).get(0).getIdno());
	}

	@SuppressWarnings("unchecked")
	public List<DtimeClass> getDtimeClassInfo(Seld seld) {
		String hql = "FROM DtimeClass dc WHERE dc.dtimeOid = ? ORDER BY dc.week";
		return getHibernateTemplate().find(hql, seld.getDtimeOid());
	}

	/**
	 * 以category查詢科系資料
	 * 
	 * @param category 分類
	 * @return tw.edu.chit.model.DepartmentInfo objects
	 */
	@SuppressWarnings("unchecked")
	public DepartmentInfo getDepartmentInfoByCategory(String category) {
		String hql = "FROM DepartmentInfo di WHERE di.category = ?";
		List<DepartmentInfo> dis = getHibernateTemplate().find(hql, category);
		if (!dis.isEmpty())
			return dis.get(0);
		else
			return null;
	}

	public void removeObject(Object o) {
		removeObject(o);
	}

	/**
	 * 查詢Dtime物件
	 * 
	 * @param d tw.edu.chit.model.Dtime Object
	 * @param orderField Order Field
	 * @return java.util.List List of tw.edu.chit.model.Dtime Objects
	 */
	@SuppressWarnings("unchecked")
	public List<Dtime> getDtimeBy(Dtime d, String orderField) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return session.createCriteria(Dtime.class).add(Example.create(d))
				.addOrder(Order.asc(orderField)).list();
	}

	/**
	 * 查詢Dtime與Cscode物件
	 * 
	 * @param d tw.edu.chit.model.Dtime Object
	 * @param orderField Order Field
	 * @return java.util.List List of tw.edu.chit.model.Dtime Objects
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getDtimeCsnoBy(Dtime d, String orderField) {
		String hql = "SELECT d, c FROM Dtime d, Csno c WHERE d.cscode = c.cscode "
				+ "AND d.departClass = ? AND d.sterm = ? ORDER BY d."
				+ orderField;
		return getHibernateTemplate().find(hql,
				new Object[] { d.getDepartClass(), d.getSterm() });
	}

	/**
	 * 查詢Nabbr物件
	 * 
	 * @param d tw.edu.chit.model.Nabbr Object
	 * @return tw.edu.chit.model.Nabbr Object
	 */
	@SuppressWarnings("unchecked")
	public Nabbr getNabbrBy(Nabbr n) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		List<Nabbr> nabbrs = session.createCriteria(Nabbr.class).add(
				Example.create(n)).list();
		if (!nabbrs.isEmpty())
			return nabbrs.get(0);
		else
			return n;
	}
	
	/**
	 * 查詢Gmark物件
	 * 
	 * @param gmark
	 * @return List of Gmark Objects
	 */
	@SuppressWarnings("unchecked")
	public List<Gmark> getGMarkBy(Gmark gmark) {
		return getHibernateTemplate().findByExample(gmark);
	}
	
	/**
	 * 查詢StdLoan物件
	 * 
	 * @param stdLoan 
	 * @return List of StdLoan Objects
	 */
	@SuppressWarnings("unchecked")
	public List<StdLoan> getStdLoanBy(StdLoan stdLoan) {
		return getHibernateTemplate().findByExample(stdLoan);
	}
	
	@SuppressWarnings("unchecked")
	public List<Dtime> getDtimesBy(Dtime dtime) {
		return getHibernateTemplate().findByExample(dtime);
	}
	
	@SuppressWarnings("unchecked")
	public List<LiteracyType> getLiteracyTypesBy(LiteracyType literacyType)
			throws DataAccessException {
		return getHibernateTemplate().findByExample(literacyType);
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
	
	/**
	 * 取一個物件
	 * @param hql
	 * @return
	 */
	public Object hqlGetOne(String hql){		
		return getHibernateTemplate().find(hql).get(0);
	}
	
	public void clearOne(Object po){
		getHibernateTemplate().refresh(po);
	}

}
