package tw.edu.chit.dao.hibernate;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.AbilityExamine;
import tw.edu.chit.model.Aborigine;
import tw.edu.chit.model.ClassCadre;
import tw.edu.chit.model.ClassInCharge;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.CodeEmpl;
import tw.edu.chit.model.ContractTeacher;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.DeptCode4Yun;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Entrno;
import tw.edu.chit.model.FeeCode;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.LifeCounseling;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.Rcact;
import tw.edu.chit.model.Rcbook;
import tw.edu.chit.model.Rcconf;
import tw.edu.chit.model.Rchono;
import tw.edu.chit.model.Rcjour;
import tw.edu.chit.model.Rcpet;
import tw.edu.chit.model.Rcproj;
import tw.edu.chit.model.RecruitSchool;
import tw.edu.chit.model.StdAbility;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.StdSkill;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.TeacherStayTime;
import tw.edu.chit.model.WwPass;

public class MemberDAOImpl extends BaseDAOHibernate implements MemberDAO {

	@SuppressWarnings("unchecked")
	public List submitQueryByCriteria(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(getSession()).list();
	}

	public Employee getEmployee(Integer oid) {
		return (Employee) getObject(Employee.class, oid);
	}

	public void saveEmployee(Employee employee) {
		saveObject(employee);
	}

	public void saveEmpl(Empl employee) {
		saveObject(employee);
	}

	public void removeEmpl(Empl emp) {
		removeObject(emp);
	}

	public void saveDEmpl(DEmpl employee) {
		saveObject(employee);
	}

	public void removeDEmpl(DEmpl emp) {
		removeObject(emp);
	}

	/**
	 * 查詢離職教職員工
	 * 
	 * @param dempl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DEmpl> findDEmplBy(DEmpl dempl) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return session.createCriteria(DEmpl.class).add(
				Example.create(dempl).enableLike(MatchMode.START)).addOrder(
				Order.asc("idno")).list();
	}

	public void saveStudent(Student student) {
		saveObject(student);
	}

	public void removeStudent(Student student) {
		removeObject(student);
	}

	public void saveGraduate(Graduate graduate) {
		saveObject(graduate);
	}

	public void removeGraduate(Graduate graduate) {
		removeObject(graduate);
	}

	public void removeEntrno(Entrno entrno) {
		removeObject(entrno);
	}

	public void removeClassInCharge(ClassInCharge classInCharge) {
		removeObject(classInCharge);
	}

	@SuppressWarnings("unchecked")
	public Clazz findClassByClassNo(String classNo) {

		List classes = getHibernateTemplate().find(
				"from Clazz where classNo = ?", classNo);
		if (classes.size() == 0) {
			return null;
		} else {
			return (Clazz) classes.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public WwPass findWWPassByAccount(String account) {

		List users = getHibernateTemplate().find(
				"from WwPass w where w.username = '" + account + "'");
		if (users.size() == 0) {
			return null;
		} else {
			return (WwPass) users.get(0);
		}
	}
	
	public WwPass findWWPassByAccountPassword(String account, String password) {		
		List users=getHibernateTemplate().find("FROM WwPass WHERE username='"+account+"' AND password='"+password+"'");
		if(users.size()<1){
			users=getHibernateTemplate().find("FROM WwPass WHERE secondName='"+account+"' AND password='"+password+"'");
		}else{
			return (WwPass)users.get(0);
		}
		if (users.size()<1) {
			return null;
		} else {
			return (WwPass) users.get(0);
		}
	}

	public void removeWWPass(WwPass pass) {
		removeObject(pass);
	}

	@SuppressWarnings("unchecked")
	//Add 'group by c.classNo' for avoid duplicate classNo -- 2010/08/25 Jason
	public List<Clazz> findClassesInChargeByMemberModuleOids(Integer memberOid,
			String moduleOids) {
		return getHibernateTemplate()
				.find(
						" select c from Clazz c, ClassInCharge cic "
								+ "where c.classNo = cic.classNo "
								+ "  and cic.empOid = ? "
								+ "  and cic.moduleOids like ? "
								+ " group by c.classNo "
								+ "order by c.campusNo, c.schoolNo, c.deptNo, c.classNo",
						new Object[] { memberOid, "%|" + moduleOids + "|%" });
	}

	@SuppressWarnings("unchecked")
	public List<ClassInCharge> findClassesInChargesByEmployeeModuleOids(
			Integer empOid, String moduleOids) {

		return getHibernateTemplate().find(
				"from ClassInCharge where empOid = ? and moduleOids like ?",
				new Object[] { empOid, "%|" + moduleOids + "|%" });
	}

	@SuppressWarnings("unchecked")
	public List<Code5> findCampusInChargeByMemberModuleOids(Integer memberOid,
			String moduleOids) {

		return getHibernateTemplate()
				.find(
						" select c5 from Code5 c5, Class, c, ClassInCharge cic "
								+ "where c5.idno in "
								+ "(select distinct c.campusNo from from Class, c, ClassInCharge cic"
								+ " where  c.classNo = cic.classNo"
								+ " and    cic.empOid = ?"
								+ " and    cic.moduleOids like ?)"
								+ " order  by c5.sequence",
						new Object[] { memberOid, "%|" + moduleOids + "|%" });
	}

	public Code5 findCode5ByCategoryIdno(String category, String no) {

		List ret = getHibernateTemplate().find(
				"from Code5 c5 where c5.category = ? and c5.idno = ?",
				new Object[] { category, no });
		if (ret.size() == 0) {
			return null;
		} else {
			return (Code5) ret.get(0);
		}
	}

	public Employee findEmployeeByIdno(String idno) {

		List ret = getHibernateTemplate().find(
				"from Employee e where e.account = ?", idno);
		if (ret.size() == 0) {
			return null;
		} else {
			return (Employee) ret.get(0);
		}
	}

	public Empl findEmplByIdno(String idno) {

		List ret = getHibernateTemplate().find(
				"from Empl e where e.idno = '" + idno + "'");
		if (ret.size() == 0) {
			return null;
		} else {
			return (Empl) ret.get(0);
		}
	}

	public DEmpl findDEmplByIdno(String idno) {

		List ret = getHibernateTemplate().find(
				"from DEmpl e where e.idno = '" + idno + "'");
		if (ret.size() == 0) {
			return null;
		} else {
			return (DEmpl) ret.get(0);
		}
	}

	public Student findStudentByStudentNo(String studentNo) {

		List ret = getHibernateTemplate().find(
				"from Student s where s.studentNo = ?", studentNo);
		if (ret.size() == 0) {
			return null;
		} else {
			return (Student) ret.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Module> findModulesByMember(Integer memberOid, int level) {
		return getHibernateTemplate().find(
				" select distinct m from Module m, UnitModule um, UnitBelong ub "
						+ "where m.oid = um.moduleOid "
						+ "and   um.unitNo = ub.unitNo "
						+ "and   ub.empOid = ? " + "and   m.level = ? "
						+ "order by m.sequence",
				new Object[] { memberOid, level });
	}

	@SuppressWarnings("unchecked")
	public List<Module> findModulesByMemberInetUseEnable(Integer memberOid,
			int level, byte inetUse, byte inetEnable) {

		return getHibernateTemplate().find(
				" select distinct m from Module m, UnitModule um, UnitBelong ub "
						+ "where m.oid = um.moduleOid "
						+ "and   um.unitNo = ub.unitNo "
						+ "and   ub.empOid = ? " + "and   m.level = ? "
						+ "and   m.inetUse = ? " + "and   m.inetEnable = ? "
						+ "order by m.sequence",
				new Object[] { memberOid, level, inetUse, inetEnable });
	}

	@SuppressWarnings("unchecked")
	public List<Module> findModulesByParentOidMember(int parentOid,
			Integer memberOid) {

		return getHibernateTemplate().find(
				" select distinct m from Module m, UnitModule um, UnitBelong ub "
						+ "where m.oid = um.moduleOid "
						+ "and   um.unitNo = ub.unitNo "
						+ "and   m.parentOid = ? " + "and   ub.empOid = ? "
						+ "order by m.sequence",
				new Object[] { parentOid, memberOid });
	}

	@SuppressWarnings("unchecked")
	public List<Module> findModulesByParentOidMemberInetUseEnable(
			int parentOid, Integer memberOid, byte inetUse, byte inetEnable) {

		return getHibernateTemplate().find(
				" select distinct m from Module m, UnitModule um, UnitBelong ub "
						+ "where m.oid = um.moduleOid "
						+ "and   um.unitNo = ub.unitNo "
						+ "and   m.parentOid = ? " + "and   ub.empOid = ? "
						+ "and   m.inetUse = ? " + "and   m.inetEnable = ? "
						+ "order by m.sequence",
				new Object[] { parentOid, memberOid, inetUse, inetEnable });
	}

	@SuppressWarnings("unchecked")
	public List<Module> findModulesByParentName(String parentName) {

		return getHibernateTemplate().find(
				" select m from Module m, Module p "
						+ "where m.parentOid = p.oid " + "and   p.name = ? "
						+ "order by m.sequence", new Object[] { parentName });
	}

	@SuppressWarnings("unchecked")
	public List<Module> findModulesByParentNameMember(String parentName,
			Integer memberOid) {

		return getHibernateTemplate().find(
				" select m from Module m, UnitModule um, UnitBelong ub, Module p "
						+ "where m.oid = um.moduleOid "
						+ "and   um.unitNo = ub.unitNo "
						+ "and   m.parentOid = p.oid " + "and   p.name = ? "
						+ "and   ub.empOid = ? "
						+ " group by m.oid order by m.sequence",
				new Object[] { parentName, memberOid });
	}

	public Module findModulesByName(String name) {

		List ret = getHibernateTemplate().find(
				"from Module m where m.name = ?", name);
		if (ret.size() == 0) {
			return null;
		} else {
			return (Module) ret.get(0);
		}
	}

	/**
	 * 依據班級代碼查詢所有學生清單
	 * 
	 * @param classNo 班級代碼
	 * @return java.util.List List of Student objects
	 */
	@SuppressWarnings("unchecked")
	public List<Student> findStudentsByClazz(String classNo) {
		return getHibernateTemplate().find(
				"FROM Student s WHERE s.departClass = ? ORDER BY s.studentNo", classNo);
	}

	/**
	 * 依據班級代碼查詢所有休退畢學生清單
	 * 
	 * @param classNo 班級代碼
	 * @return java.util.List List of Student objects
	 */
	@SuppressWarnings("unchecked")
	public List<Graduate> findGraduatesByClazz(String classNo) {
		return getHibernateTemplate().find(
				"FROM Graduate g WHERE g.departClass = ?", classNo);
	}

	@SuppressWarnings("unchecked")
	public List<Student> findStudentsInChargeByDept(String deptNo,
			String classInCharge) {

		return getHibernateTemplate().find(
				" select s from Student s, Clazz c "
						+ "where s.departClass = c.classNo "
						+ " and  c.deptNo = '" + deptNo + "' "
						+ " and  s.departClass in " + classInCharge);
	}

	@SuppressWarnings("unchecked")
	public List<Student> findStudentsInChargeByDept(String campusNo,
			String schoolNo, String deptNo, String classInCharge) {

		return getHibernateTemplate().find(
				" select s from Student s, Clazz c "
						+ "where s.departClass = c.classNo "
						+ " and  c.campusNo = '" + campusNo + "' "
						+ " and  c.schoolNo = '" + schoolNo + "' "
						+ " and  c.deptNo = '" + deptNo + "' "
						+ " and  s.departClass in " + classInCharge);
	}

	@SuppressWarnings("unchecked")
	public List<Student> findStudentsInChargeBySchool(String schoolNo,
			String classInCharge) {

		return getHibernateTemplate().find(
				" select s from Student s, Clazz c "
						+ "where s.departClass = c.classNo "
						+ " and  c.schoolNo = '" + schoolNo + "' "
						+ " and  s.departClass in " + classInCharge);
	}

	@SuppressWarnings("unchecked")
	public List<Student> findStudentsInChargeBySchool(String campusNo,
			String schoolNo, String classInCharge) {

		return getHibernateTemplate().find(
				" select s from Student s, Clazz c "
						+ "where s.departClass = c.classNo "
						+ " and  c.campusNo = '" + campusNo + "' "
						+ " and  c.schoolNo = '" + schoolNo + "' "
						+ " and  s.departClass in " + classInCharge);
	}

	@SuppressWarnings("unchecked")
	public List<Student> findStudentsInChargeByCampus(String campusNo,
			String classInCharge) {

		return getHibernateTemplate().find(
				" select s from Student s, Clazz c "
						+ "where s.departClass = c.classNo "
						+ " and  c.campusNo = '" + campusNo + "' "
						+ " and  s.departClass in " + classInCharge);
	}

	@SuppressWarnings("unchecked")
	public Graduate findGraduateByStudentNo(String studentNo) {

		List<Graduate> graduates = getHibernateTemplate().find(
				"from Graduate g " + "where g.studentNo = ?", studentNo);
		if (graduates.size() == 0) {
			return null;
		} else {
			return graduates.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Graduate findGraduateByIdno(String idno) {

		List<Graduate> graduates = getHibernateTemplate().find(
				"from Graduate g " + "where g.idno = ?", idno);
		if (graduates.size() == 0) {
			return null;
		} else {
			return graduates.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Student> findAllStudentsInCharge(String classInCharge) {

		return getHibernateTemplate().find(
				"from Student s " + "where s.departClass in " + classInCharge);
	}

	@SuppressWarnings("unchecked")
	public List<Clazz> findAllClasses() {

		return getHibernateTemplate()
				.find(
						"from Clazz order by campusNo, schoolNo, deptNo, grade, classNo");
	}

	@SuppressWarnings("unchecked")
	public List<Clazz> findClassesByType(String type) {

		return getHibernateTemplate()
				.find(
						"from Clazz "
								+ "where type = '"
								+ type
								+ "' "
								+ "order by campusNo, schoolNo, deptNo, grade, classNo");
	}

	@SuppressWarnings("unchecked")
	public List<Clazz> findClassByClassNoPattern(String classNoPattern) {

		return getHibernateTemplate().find(
				"from Clazz " + "where classNo like '" + classNoPattern + "'");
	}

	@SuppressWarnings("unchecked")
	public List<Code5> findCode5ByCategory(String category) {

		return getHibernateTemplate()
				.find(
						"from Code5 c "
								+ "where c.category = ? AND idno IS NOT NULL AND name IS NOT NULL "
								+ "order by c.sequence", category);
	}

	@SuppressWarnings("unchecked")
	public List<CodeEmpl> findCodeEmplByCategory(String category) {

		return getHibernateTemplate().find(
				"from CodeEmpl c " + "where c.category = ? "
						+ "order by c.sequence, c.idno", category);
	}

	/**
	 * find CodeEmpl by Category using LIKE '%pattern%'
	 * 
	 * @param category
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CodeEmpl> findCodeEmplByCategory2(String category) {

		return getHibernateTemplate().find(
				"from CodeEmpl c " + "where c.category like '%" + category
						+ "%' " + "order by c.sequence, c.idno");
	}

	public List<Code5> findAllCampuses() {
		/*
		 * return getHibernateTemplate().find( "from Code5 c " +
		 * "where c.category = 'Campus' " + "order by c.sequence");
		 */
		return findCode5ByCategory("Campus");
	}

	public List<Code5> findAllSchools() {

		return findCode5ByCategory("School");
	}

	public List<Code5> findAllDepts() {

		return findCode5ByCategory("Dept");
	}

	public List<Code5> findAllMasterDepts() {
		return findCode5ByCategory("MasterDept");
	}

	/**
	 * 實作以學生姓名關鍵字查尋學生資料之方法
	 * 
	 * @param key 學生姓名關鍵字
	 * @return java.util.List List of Student objects
	 */
	@SuppressWarnings("unchecked")
	public List<Student> getStudentsByNameKeyword(String key) {
		Object[] param = { "%" + key + "%" };
		return getHibernateTemplate().find(
				"from Student s where s.studentName like ?", param);
	}

	/*
	 * public List<Code5> findAllStatus() {
	 * 
	 * return getHibernateTemplate().find( "from Code5 c " +
	 * "where c.category = 'Status' " + "order by c.sequence"); }
	 * 
	 * public List<Code5> findAllIdentity() {
	 * 
	 * return getHibernateTemplate().find( "from Code5 c " +
	 * "where c.category = 'Identity' " + "order by c.sequence"); }
	 */

	/**
	 * Get Empl within the same unit (NOT Empl.unit) defined in Code5 with
	 * category 'Unit'. The empl/unit multi-to-multi mapping is stored in table
	 * UnitBelong
	 * 
	 * @param unitNo the unit code defined in Code5, NOT Empl.unit
	 * @return java.util.List<Empl> employees within that unit
	 */
	@SuppressWarnings("unchecked")
	public List<Empl> findEmplFromUnitBelongByUnitNo(String unitNo) {

		return getHibernateTemplate().find(
				"select e from Empl e, UnitBelong u "
						+ "where e.oid = u.empOid " + " and  u.unitNo = '"
						+ unitNo + "' " + "order by u.oid desc");
	}

	/**
	 * 以Oid取得Empl物件
	 * 
	 * @param oid Empl Oid
	 * @return tw.edu.chit.model.Empl object
	 */
	public Empl getEmplByOid(Integer oid) {
		return (Empl) getObject(Empl.class, oid);
	}

	public Empl getEmplFilterBy(Integer oid, String year) {
		getHibernateTemplate().enableFilter("yearFilterDef").setParameter(
				"yearCondition", year);
		return (Empl) getObject(Empl.class, oid);
	}

	/**
	 * 以Empl Oid取得TeacherStayTime清單物件
	 * 
	 * @param oid Empl Oid
	 * @return List of tw.edu.chit.model.Empl Object
	 */
	public List<TeacherStayTime> getStayTimeByEmplOid(Integer oid, String year,
			String term) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
		} catch (Exception e) {
			session = getHibernateTemplate().getSessionFactory().openSession();
		}

		Empl empl = (Empl) session.get(Empl.class, oid);
		Hibernate.initialize(empl.getStayTime());
		List<TeacherStayTime> ret = new LinkedList<TeacherStayTime>();
		for (TeacherStayTime tst : empl.getStayTime()) {
			if (year.equalsIgnoreCase(tst.getSchoolYear())
					&& term.equalsIgnoreCase(tst.getSchoolTerm()))
				ret.add(tst);
		}
		return ret;
	}

	/**
	 * 以Empl Oid取得LifeCounseling清單物件
	 * 
	 * @param oid Empl Oid
	 * @return List of tw.edu.chit.model.Empl Object
	 */
	public List<LifeCounseling> getLifeCounselingByEmplOid(Integer oid) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Empl empl = (Empl) session.get(Empl.class, oid);
		Hibernate.initialize(empl.getLifeCounseling());
		return empl.getLifeCounseling();
	}

	public void removeWWPassByUsername(String username) {
		executeSQL("delete from WwPass where username = '" + username + "'");
	}

	public void updateWWPassUsername(String oldUsername, String newUsername) {
		executeSQL("update wwpass set username = '" + newUsername
				+ "' where username = '" + oldUsername + "'");
	}

	public void updateWWPassPassword(String username, String password) {
		executeSQL("update wwpass set password = '"
				+ password.replaceAll("'", "\'") + "' where username = '"
				+ username + "'");
	}

	public void updateWWPassInformixPassword(String username, String password) {
		executeSQL("update wwpass set informixPass = '"
				+ password.replaceAll("'", "\'") + "' where username = '"
				+ username + "'");
	}

	public void createWWPass(String username, String password, String priority) {
		// TODO 這裡必須先證明帳號不存在
		executeSQL("insert into wwpass (username, password, priority, informixPass) "
				+ "values ('"
				+ username
				+ "', '"
				+ password
				+ "', '"
				+ priority + "', '" + password + "')");
	}

	public void updateStudentDiviByClass(String classNo, String groupId) {
		executeSQL("UPDATE stmd SET divi = '" + groupId
				+ "' WHERE depart_class  = '" + classNo + "'");
	}

	public void deleteClassInChargeByEmpOid(Integer empOid) {
		executeSQL("DELETE FROM ClassInCharge WHERE EmpOid = "
				+ empOid.toString());
	}

	public void deleteClassInChargeByEmpOidClassNoPattern(Integer empOid,
			String classNoPattern, String authorityTarget) {
		executeSQL("DELETE FROM ClassInCharge WHERE EmpOid = "
				+ empOid.toString() + " AND ClassNo LIKE '" + classNoPattern
				+ "' AND ModuleOids = '" + authorityTarget + "'");
	}

	public void deleteUnitBelongByEmpOidUnitNo(Integer empOid, String unitNo) {
		executeSQL("DELETE FROM UnitBelong WHERE EmpOid = " + empOid.toString()
				+ " AND  UnitNo = '" + unitNo + "'");
	}

	/**
	 * 實作以Empl取得ClassCadre清單物件
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 * @return java.util.List List of ClassCadre objects
	 */
	public List<ClassCadre> findClassCadreByEmpl(Empl empl) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		// Back to Persistent stauts
		session.update(empl);
		Hibernate.initialize(empl.getClassCadre());
		// session.close();
		return empl.getClassCadre();
	}

	/**
	 * 實作以Oid取得Clazz物件
	 * 
	 * @param oid Clazz Oid
	 * @return tw.edu.chit.model.Clazz object
	 */
	public Clazz findClassByOid(Integer oid) {
		return (Clazz) getHibernateTemplate().get(Clazz.class, oid);
	}

	/**
	 * 實作以Empl Oid與班級代碼取得ClassCadre清單
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 * @param classNo 班級代碼
	 * @return java.util.List List of ClassCadre objects
	 */
	@SuppressWarnings("unchecked")
	public List<ClassCadre> findClassCadreByClassNo(Empl empl, String classNo) {
		String hql = "from ClassCadre cc where cc.empl = ? and cc.classNo = ?";
		return getHibernateTemplate().find(hql, new Object[] { empl, classNo });
	}

	/**
	 * 實作以Oid取得Student物件
	 * 
	 * @param oid Student Oid
	 * @return tw.edu.chit.model.Student object
	 */
	public Student findStudentByOid(Integer oid) {
		return (Student) getHibernateTemplate().get(Student.class, oid);
	}

	/**
	 * 實作刪除TeacherStayTime清單
	 * 
	 * @param tsts tw.edu.chit.model.Empl object
	 */
	public void deleteStayTimeByEmpl(List<TeacherStayTime> tsts) {
		HibernateTemplate ht = getHibernateTemplate();
		for (TeacherStayTime tst : tsts)
			ht.delete(tst);
		// Empl empl = tsts.get(0).getEmpl();
		// empl.setStayTime(null);
	}

	public List<Clazz> findClassesByCampusSchoolDeptNo(String campusNo,
			String schoolNo, String deptNo) {

		return getHibernateTemplate().find(
				"from Clazz c " + "where c.campusNo = '" + campusNo + "'"
						+ "  and c.schoolNo = '" + schoolNo + "'"
						+ "  and c.deptNo   = '" + deptNo + "'");
	}

	public List<Clazz> findClassesByCampusSchoolNo(String campusNo,
			String schoolNo) {

		return getHibernateTemplate().find(
				"from Clazz c " + "where c.campusNo = '" + campusNo + "'"
						+ "  and c.schoolNo = '" + schoolNo + "'");
	}

	public List<Clazz> findClassesByCampusNo(String campusNo) {

		return getHibernateTemplate().find(
				"from Clazz c where c.campusNo = '" + campusNo + "'");
	}

	/**
	 * Used to authenticate parent login
	 * 
	 * @param idno student's 身分證 ID
	 * @param birthday a serial like "780329" which means 1989 Mar 29th
	 * @param parentName
	 * @return
	 */
	public Student findStudentByIdnoBirthdayParentName(String idno,
			Date birthday, String parentName) {
		List<Student> students = getHibernateTemplate()
				.find(
						"from Student s where s.idno = ? and s.birthday = ?",
						new Object[] { idno, birthday });
		if (students.size() == 0) {
			return null;
		} else {
			return students.get(0);
		}
	}

	/**
	 * 依據Unit代碼取得CodeEmpl物件
	 * 
	 * @param unit Unit代碼
	 * @return tw.edu.chit.model.CodeEmpl object
	 */
	@SuppressWarnings("unchecked")
	public CodeEmpl getCodeEmplBy(String unit) {
		String hql = "FROM CodeEmpl ce WHERE ce.category = 'UnitTeach' "
				+ "AND ce.idno2 = ?";
		List<CodeEmpl> codeEmpls = getHibernateTemplate().find(hql,
				new Object[] { unit });
		if (!codeEmpls.isEmpty())
			return codeEmpls.get(0);
		else
			return null;
	}

	/**
	 * 依據Unit代碼取得Empl物件清單
	 * 
	 * @param unit Unit代碼
	 * @return java.util.List List of Empl objects
	 */
	@SuppressWarnings("unchecked")
	public List<Empl> getTeacherByUnit(String unit) {
		String hql = "FROM Empl e WHERE e.unit = ?";
		return getHibernateTemplate().find(hql, new Object[] { unit });
	}

	/**
	 * 查詢某年學生之技能檢定項目
	 * 
	 * @param year 學年
	 * @return java.util.List List of AbilityExamine Objects
	 */
	@SuppressWarnings("unchecked")
	public List<AbilityExamine> getAbilityExamineBySchoolYear(String year) {
		String hql = "FROM AbilityExamine WHERE schoolYear = ?";
		return getHibernateTemplate().find(hql,
				new Object[] { new Integer(year) });
	}

	/**
	 * 以代碼取得技能檢定資料
	 * 
	 * @param no 技能檢定代碼
	 * @return AbilityExamine tw.edu.chit.model.AbilityExamine Object
	 */
	@SuppressWarnings("unchecked")
	public AbilityExamine getAbilityExamineByNo(Integer no) {
		String hql = "FROM AbilityExamine WHERE no = ?";
		List<AbilityExamine> aes = getHibernateTemplate().find(hql,
				new Object[] { no });
		if (!aes.isEmpty())
			return aes.get(0);
		else
			return null;
	}

	/**
	 * 以學號取得學生所有技能檢定資料
	 * 
	 * @param studentNo 學號
	 * @param abilityNo 技能代碼
	 * @return java.util.List List of StdAbility Objects
	 */
	@SuppressWarnings("unchecked")
	public List<StdAbility> getStudentAbilityByStudentNoAndAbilityNo(
			String studentNo, String abilityNo) {
		
		String hql = "FROM StdAbility WHERE studentNo = ?";
		Object[] params = null;
		if (StringUtils.isNotBlank(abilityNo)) {
			hql += " AND abilityNo = ?";
			params = new Object[] { studentNo, Integer.valueOf(abilityNo) };
		} else
			params = new Object[] { studentNo };

		return getHibernateTemplate().find(hql, params);
	}

	/**
	 * 取得學生報部證照資料
	 * 
	 * @param schoolYear 學年
	 * @param schoolTerm 學期
	 * @param studentNo 學號
	 * @param skillCode 證照代碼
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StdSkill> getStdSkillsBy(String schoolYear, String schoolTerm,
			String studentNo, String skillCode) {

		String hql = "FROM StdSkill WHERE studentNo = ? AND schoolYear = ? "
				+ "AND schoolTerm = ?";
		Object[] params = null;
		if (StringUtils.isNotBlank(skillCode)) {
			hql += " AND licenseCode = ? ORDER BY studentNo";
			params = new Object[] { studentNo, schoolYear, schoolTerm,
					skillCode };
		} else {
			hql += " ORDER BY studentNo";
			params = new Object[] { studentNo, schoolYear, schoolTerm };
		}

		return getHibernateTemplate().find(hql, params);
	}
	
	/**
	 * 
	 * @param recruitSchool
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RecruitSchool> getRecruitSchoolsBy(RecruitSchool recruitSchool) {
		return getHibernateTemplate().findByExample(recruitSchool);
	}

	@SuppressWarnings("unchecked")
	public List<DtimeClass> getEmplCourseInfo(String term, String idno,
			int weekday) {
		String hql = "SELECT dc FROM Dtime d, DtimeClass dc "
				+ "WHERE d.oid = dc.dtimeOid "
				+ "AND d.sterm = ? AND d.techid = ? AND dc.week = ?";
		return getHibernateTemplate().find(hql,
				new Object[] { term, idno, weekday });
	}

	@SuppressWarnings("unchecked")
	public List<DtimeClass> getEmplCourseInfo4DtimeTeacher(String term,
			String idno, int weekday) {
		String hql = "SELECT dc FROM Dtime d, DtimeClass dc, DtimeTeacher dt "
				+ "WHERE d.oid = dc.dtimeOid AND d.oid = dt.dtimeOid "
				+ "AND d.sterm = ? AND dt.teachId = ? AND dc.week = ?";
		return getHibernateTemplate().find(hql,
				new Object[] { term, idno, weekday });
	}

	/**
	 * 新增學生技能檢定資料
	 * 
	 * @param sa tw.edu.chit.model.StdAbility Object
	 */
	public void saveStudentAbility(StdAbility sa) {
		saveObject(sa);
	}

	/**
	 * 直接取回任一串資料
	 */
	public List query(String hql) {
		return getHibernateTemplate().find(hql);
	}

	/**
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List query(String hql, Object[] values) throws DataAccessException {
		if (values != null)
			return getHibernateTemplate().find(hql, values);
		else
			return query(hql);
	}

	/**
	 * 
	 * @param entity
	 * @param criterion
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List getSQLWithCriteria(Class entity, Criterion... criterion)
			throws DataAccessException {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entity);
		for (Criterion c : criterion)
			detachedCriteria.add(c);

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
			Criterion... criterion) throws DataAccessException {

		DetachedCriteria criteria = DetachedCriteria.forClass(entity).add(
				example);
		for (Criterion c : criterion)
			criteria.add(c);

		return getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * 任意殺
	 */
	public void deleteBy(Object o) {
		getHibernateTemplate().delete(o);
	}

	/**
	 * 任意存
	 */
	public void save(Object o) {
		getHibernateTemplate().save(o);
	}

	/**
	 * 查詢在校生清單資料
	 * 
	 * @param student tw.edu.chit.model.Student
	 * @return java.util.List List of Student Objects
	 */
	@SuppressWarnings("unchecked")
	public List<Student> getStudentsBy(Student student) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return session.createCriteria(Student.class).add(
				Example.create(student).enableLike(MatchMode.START)).addOrder(
				Order.asc("departClass")).list();
	}

	/**
	 * 查詢離校校生清單資料
	 * 
	 * @param student tw.edu.chit.model.Graduate
	 * @return java.util.List List of Graduate Objects
	 */
	@SuppressWarnings("unchecked")
	public List<Graduate> getGraduatesBy(Graduate graduate) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return session.createCriteria(Graduate.class).add(
				Example.create(graduate).enableLike(MatchMode.START)).addOrder(
				Order.asc("departClass")).list();
	}

	/**
	 * 
	 * @param empl
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<Empl> getEmplsBy(Empl empl) throws DataAccessException {
		return getHibernateTemplate().findByExample(empl);
	}

	/**
	 * 
	 * @param workShifts
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Empl> getEmplByWorkShift(String[] workShifts)
			throws DataAccessException {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		List<Empl> empls = session.createCriteria(Empl.class).add(
				Restrictions.isNotNull("workShift")).add(
				Restrictions.in("workShift", workShifts)).list();
		return empls;
	}

	/**
	 * 查詢Student大頭照物件
	 * 
	 * @param image
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public StdImage getStdImageBy(StdImage image) {
		List<StdImage> list = getHibernateTemplate().findByExample(image);
		if (!list.isEmpty() && list.size() == 1)
			return list.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<ContractTeacher> getContractTeacherBy(
			ContractTeacher contractTeacher) throws DataAccessException {
		return getHibernateTemplate().findByExample(contractTeacher);
	}

	@SuppressWarnings("unchecked")
	public List<ClassInCharge> getClassInChargeBy(ClassInCharge classInCharge)
			throws DataAccessException {
		return getHibernateTemplate().findByExample(classInCharge);
	}

	@SuppressWarnings("unchecked")
	public List<DeptCode4Yun> getDeptCode4YunBy(DeptCode4Yun deptCode4Yun)
			throws DataAccessException {
		return getHibernateTemplate().findByExample(deptCode4Yun);
	}

	@SuppressWarnings("unchecked")
	public List<Aborigine> getAborigineBy(Aborigine aborigine)
			throws DataAccessException {
		return getHibernateTemplate().findByExample(aborigine);
	}

	@SuppressWarnings("unchecked")
	public List<FeeCode> getFeeCodeBy(FeeCode feeCode)
			throws DataAccessException {
		return getHibernateTemplate().findByExample(feeCode);
	}

	public List<CodeEmpl> getAllUnit() throws DataAccessException {
		List<CodeEmpl> ret = findCodeEmplByCategory("Unit");
		ret.addAll(findCodeEmplByCategory("UnitTeach"));
		return ret;
	}

	@SuppressWarnings("unchecked")
	public List<Rcact> getRcactsBy(Rcact rcact) throws DataAccessException {
		return getHibernateTemplate().findByExample(rcact);
	}

	@SuppressWarnings("unchecked")
	public List<Rcproj> getRcprojsBy(Rcproj rcproj) throws DataAccessException {
		return getHibernateTemplate().findByExample(rcproj);
	}

	@SuppressWarnings("unchecked")
	public List<Rcjour> getRcjoursBy(Rcjour rcjour) throws DataAccessException {
		return getHibernateTemplate().findByExample(rcjour);
	}

	@SuppressWarnings("unchecked")
	public List<Rcconf> getRcconfsBy(Rcconf rcconf) throws DataAccessException {
		return getHibernateTemplate().findByExample(rcconf);
	}

	@SuppressWarnings("unchecked")
	public List<Rcbook> getRcbooksBy(Rcbook rcbook) throws DataAccessException {
		return getHibernateTemplate().findByExample(rcbook);
	}

	@SuppressWarnings("unchecked")
	public List<Rcpet> getRcpetsBy(Rcpet rcpet) throws DataAccessException {
		return getHibernateTemplate().findByExample(rcpet);
	}

	@SuppressWarnings("unchecked")
	public List<Rchono> getRchonosBy(Rchono rchono) throws DataAccessException {
		return getHibernateTemplate().findByExample(rchono);
	}

	/**
	 * 校友登入服務
	 * 
	 * @param studentNo
	 * @return
	 */
	public Graduate findGstudentByStudentNo(String studentNo) {
		return (Graduate)getHibernateTemplate().find("FROM Graduate WHERE studentNo='"+ studentNo+"'").get(0);		
	}

	/**
	 * 卡片登入
	 */
	public WwPass findWWPassByAccountPassword(String account) {

		List users = getHibernateTemplate()
				.find("from WwPass w where w.username = ? ",
						new Object[] { account });
		if (users.size() == 0) {
			return null;
		} else {
			return (WwPass) users.get(0);
		}
	}

}