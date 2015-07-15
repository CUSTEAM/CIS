package tw.edu.chit.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.springframework.dao.DataAccessException;

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

public interface MemberDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List submitQuery(String hql);

	@SuppressWarnings("unchecked")
	public List submitQueryByCriteria(DetachedCriteria criteria);

	public Employee getEmployee(Integer oid);

	public void saveEmployee(Employee employee);
	
	public void saveEmpl(Empl emp);
	
	public void saveDEmpl(DEmpl emp);

	public void removeEmpl(Empl emp);
	
	public void removeDEmpl(DEmpl emp);

	/**
	 * 查詢離職教職員工
	 * 
	 * @param dempl
	 * @return
	 */
	public List<DEmpl> findDEmplBy(DEmpl dempl);

	public void saveStudent(Student student);

	public void removeStudent(Student student);

	public void saveGraduate(Graduate graduate);

	public void removeGraduate(Graduate graduate);

	public void removeEntrno(Entrno entrno);

	public void removeClassInCharge(ClassInCharge classInCharge);

	public Clazz findClassByClassNo(String ClassNo);
	
	public WwPass findWWPassByAccount(String account);

	public WwPass findWWPassByAccountPassword(String account, String password);
	
	public void removeWWPass(WwPass pass);

	public List<Clazz> findClassesInChargeByMemberModuleOids(Integer memberOid, String moduleOids);

	public List<ClassInCharge> findClassesInChargesByEmployeeModuleOids(Integer empOid, String moduleOids);

	public Code5 findCode5ByCategoryIdno(String category, String no);

	public List<Module> findModulesByMember(Integer memberOid, int level);
	
	public List<Module> findModulesByMemberInetUseEnable(Integer memberOid, int level, byte inetUse, byte inetEnable);

	public List<Module> findModulesByParentOidMember(int parentOid, Integer memberOid);
	
	public List<Module> findModulesByParentOidMemberInetUseEnable(int parentOid, Integer memberOid, byte inetUse, byte inetEnable);

	public List<Module> findModulesByParentName(String parentName);

	public List<Module> findModulesByParentNameMember(String parentName, Integer memberOid);

	public Module findModulesByName(String name);

	public List<Student> findStudentsByClazz(String classNo);
	
	public List<Graduate> findGraduatesByClazz(String classNo);

	public List<Student> findStudentsInChargeByDept(String deptNo, String classInCharge);

	public List<Student> findStudentsInChargeByDept(String campusNo, String schoolNo, String deptNo, String classInCharge);

	public List<Student> findStudentsInChargeBySchool(String schoolNo, String classInCharge);

	public List<Student> findStudentsInChargeBySchool(String campusNo, String schoolNo, String classInCharge) ;

	public List<Student> findStudentsInChargeByCampus(String campusNo, String classInCharge);

	public List<Student> findAllStudentsInCharge(String classInCharge);

	public Graduate findGraduateByStudentNo(String studentNo);
	
	public Graduate findGraduateByIdno(String idno);

	public List<Clazz> findAllClasses();

	public List<Clazz> findClassesByType(String type);
	
	public List<Clazz> findClassByClassNoPattern(String classNoPattern);

	public List<Code5> findAllCampuses();

	public List<Code5> findAllSchools();

	public List<Code5> findAllDepts();
	
	public List<Code5> findAllMasterDepts();

	public List<Code5> findCode5ByCategory(String category) ;
	
	public List<CodeEmpl> findCodeEmplByCategory(String category) ;
	
	/**
	 * find CodeEmpl by Category using LIKE '%pattern%'
	 * @param category
	 * @return
	 */
	public List<CodeEmpl> findCodeEmplByCategory2(String category) ;

	//public List<Code5> findAllStatus();

	//public List<Code5> findAllIdentity();

	public Employee findEmployeeByIdno(String idno);
	
	public Empl findEmplByIdno(String idno);
	public DEmpl findDEmplByIdno(String idno);

	public Student findStudentByStudentNo(String studentNo);

	//public List getCampusInChargeByEmpl(String account);

	/**
	 * 定義以學生姓名關鍵字查尋學生資料之方法
	 *
	 * @param key 學生姓名關鍵字
	 * @return java.util.List List of Student objects
	 */
	public List<Student> getStudentsByNameKeyword(String key);
	
	public List<Empl> findEmplFromUnitBelongByUnitNo(String unitNo);
	
	/**
	 * 定義以Oid取得Empl物件
	 * 
	 * @param oid Empl Oid
	 * @return tw.edu.chit.model.Empl object
	 */
	public Empl getEmplByOid(Integer oid);
	
	public Empl getEmplFilterBy(Integer oid, String year);
	
	/**
	 * 以Empl Oid取得TeacherStayTime清單物件
	 * 
	 * @param oid Empl Oid
	 * @return tw.edu.chit.model.Empl object
	 */
	public List<TeacherStayTime> getStayTimeByEmplOid(Integer oid, String year,
			String term);
	
	/**
	 * 以Empl Oid取得LifeCounseling清單物件
	 * 
	 * @param oid Empl Oid
	 * @return List of tw.edu.chit.model.Empl Object
	 */
	public List<LifeCounseling> getLifeCounselingByEmplOid(Integer oid);

	public void removeWWPassByUsername(String username);
	
	public void updateWWPassUsername(String oldUsername, String newUsername);
	
	public void updateWWPassPassword(String username, String password);
	
	public void updateWWPassInformixPassword(String username, String password);
	
	public void createWWPass(String username, String password, String priority);
	
	public void updateStudentDiviByClass(String classNo, String groupId);
	
	public void deleteClassInChargeByEmpOid(Integer empOid);
	
	public void deleteClassInChargeByEmpOidClassNoPattern(Integer empOid, String classNoPattern, String authorityTarget);
	
	public void deleteUnitBelongByEmpOidUnitNo(Integer empOid, String unitNo);
	
	/**
	 * 定義以Empl取得ClassCadre清單物件
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 * @return java.util.List List of ClassCadre objects
	 */
	public List<ClassCadre> findClassCadreByEmpl(Empl empl);
	
	/**
	 * 定義以Oid取得Clazz物件
	 * 
	 * @param oid Clazz Oid
	 * @return tw.edu.chit.model.Clazz object
	 */
	public Clazz findClassByOid(Integer oid);
	
	/**
	 * 定義以Empl Oid與班級代碼取得ClassCadre清單
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 * @param classNo 班級代碼
	 * @return java.util.List List of ClassCadre objects
	 */
	public List<ClassCadre> findClassCadreByClassNo(Empl empl, String classNo);
	
	/**
	 * 定義以Oid取得Student物件
	 * 
	 * @param oid Student Oid
	 * @return tw.edu.chit.model.Student object
	 */
	public Student findStudentByOid(Integer oid);
	
	/**
	 * 定義刪除TeacherStayTime清單
	 * 
	 * @param tsts tw.edu.chit.model.Empl object
	 */
	public void deleteStayTimeByEmpl(List<TeacherStayTime> tsts);
	
	public List<Clazz> findClassesByCampusSchoolDeptNo(String campusNo, String schoolNo, String deptNo);
	
	public List<Clazz> findClassesByCampusSchoolNo(String campusNo, String schoolNo);
	
	public List<Clazz> findClassesByCampusNo(String compusNo);
	
	/**
	 * Used to authenticate parent login
	 * @param idno student's 身分證 ID
	 * @param birthday a serial like "780329" which means 1989 Mar 29th
	 * @param parentName
	 * @return
	 */
	public Student findStudentByIdnoBirthdayParentName(String idno, Date birthday, String parentName);
	
	/**
	 * 依據Unit代碼取得CodeEmpl物件
	 * 
	 * @param unit Unit代碼
	 * @return tw.edu.chit.model.CodeEmpl object
	 */
	public CodeEmpl getCodeEmplBy(String unit);
	
	/**
	 * 依據Unit代碼取得Empl物件清單
	 * 
	 * @param unit Unit代碼
	 * @return java.util.List List of Empl objects
	 */
	public List<Empl> getTeacherByUnit(String unit);
	
	/**
	 * 查詢某年學生之技能檢定項目
	 * 
	 * @param year 學年
	 * @return java.util.List List of AbilityExamine Objects
	 */
	public List<AbilityExamine> getAbilityExamineBySchoolYear(String year);
	
	/**
	 * 以代碼取得技能檢定資料
	 * 
	 * @param no 技能檢定代碼
	 * @return AbilityExamine tw.edu.chit.model.AbilityExamine Object
	 */
	public AbilityExamine getAbilityExamineByNo(Integer no);
	
	/**
	 * 以學號取得學生所有技能檢定資料
	 * 
	 * @param studentNo 學號
	 * @param abilityNo 技能代碼
	 * @return java.util.List List of StdAbility Objects
	 */
	public List<StdAbility> getStudentAbilityByStudentNoAndAbilityNo(
			String studentNo, String abilityNo);
	
	/**
	 * 取得學生報部證照資料
	 * 
	 * @param schoolYear 學年
	 * @param schoolTerm 學期
	 * @param studentNo 學號
	 * @param skillCode 證照代碼
	 * @return
	 */
	public List<StdSkill> getStdSkillsBy(String schoolYear, String schoolTerm,
			String studentNo, String skillCode);
	
	/**
	 * 
	 * @param recruitSchool
	 * @return
	 */
	public List<RecruitSchool> getRecruitSchoolsBy(RecruitSchool recruitSchool);
	
	public List<DtimeClass> getEmplCourseInfo(String term, String idno,
			int weekday);

	public List<DtimeClass> getEmplCourseInfo4DtimeTeacher(String term,
			String idno, int weekday);
	
	/**
	 * 新增學生技能檢定資料
	 * 
	 * @param sa tw.edu.chit.model.StdAbility Object
	 */
	public void saveStudentAbility(StdAbility sa);
	
	/**
	 * 任意取資料
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List query(String hql);
	
	/**
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List query(String hql, Object[] values) throws DataAccessException;
	
	/**
	 * 
	 * @param entity
	 * @param criterion
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List getSQLWithCriteria(Class entity, Criterion... criterion)
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
	
	/**
	 * 任意殺資料
	 * @param o
	 */
	public void deleteBy(Object o);
	
	/**
	 * 任意存資料
	 * @param o
	 */
	public void save(Object o);
	
	/**
	 * 查詢在校生清單資料
	 * 
	 * @param student tw.edu.chit.model.Student
	 * @return java.util.List List of Student Objects
	 */
	public List<Student> getStudentsBy(Student student);
	
	/**
	 * 查詢離校校生清單資料
	 * 
	 * @param student tw.edu.chit.model.Graduate
	 * @return java.util.List List of Graduate Objects
	 */
	public List<Graduate> getGraduatesBy(Graduate graduate);
	
	/**
	 * 
	 * @param empl
	 * @return
	 * @throws DataAccessException
	 */
	public List<Empl> getEmplsBy(Empl empl) throws DataAccessException;
	
	/**
	 * 
	 * @param workShifts
	 * @return
	 */
	public List<Empl> getEmplByWorkShift(String[] workShifts)
			throws DataAccessException;
	
	/**
	 * 查詢Student大頭照物件
	 * 
	 * @param image
	 * @return
	 */
	public StdImage getStdImageBy(StdImage image);
	
	public List<ContractTeacher> getContractTeacherBy(
			ContractTeacher contractTeacher) throws DataAccessException;
	
	public List<DeptCode4Yun> getDeptCode4YunBy(DeptCode4Yun deptCode4Yun)
			throws DataAccessException;
	
	public List<Aborigine> getAborigineBy(Aborigine aborigine)
			throws DataAccessException;
	
	public List<FeeCode> getFeeCodeBy(FeeCode feeCode)
			throws DataAccessException;
	
	public List<ClassInCharge> getClassInChargeBy(ClassInCharge classInCharge)
			throws DataAccessException;
	
	public List<CodeEmpl> getAllUnit() throws DataAccessException;
	
	public List<Rcact> getRcactsBy(Rcact rcact) throws DataAccessException;

	public List<Rcproj> getRcprojsBy(Rcproj rcproj) throws DataAccessException;
	
	public List<Rcjour> getRcjoursBy(Rcjour rcjour) throws DataAccessException;
	
	public List<Rcconf> getRcconfsBy(Rcconf rcconf) throws DataAccessException;
	
	public List<Rcbook> getRcbooksBy(Rcbook rcbook) throws DataAccessException;
	
	public List<Rcpet> getRcpetsBy(Rcpet rcpet) throws DataAccessException;
	
	public List<Rchono> getRchonosBy(Rchono rchono) throws DataAccessException;
	
	public Graduate findGstudentByStudentNo(String studentNo);
	
	public WwPass findWWPassByAccountPassword(String account);
	
}