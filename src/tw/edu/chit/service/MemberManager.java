package tw.edu.chit.service;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.gui.Menu;
import tw.edu.chit.model.AbilityExamine;
import tw.edu.chit.model.Aborigine;
import tw.edu.chit.model.AssessPaper;
import tw.edu.chit.model.ClassCadre;
import tw.edu.chit.model.ClassInCharge;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.CodeEmpl;
import tw.edu.chit.model.ContractTeacher;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.DeptCode4Yun;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Entrno;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Investigation;
import tw.edu.chit.model.InvestigationG;
import tw.edu.chit.model.LifeCounseling;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Rcact;
import tw.edu.chit.model.Rcbook;
import tw.edu.chit.model.Rcconf;
import tw.edu.chit.model.Rchono;
import tw.edu.chit.model.Rcjour;
import tw.edu.chit.model.Rcpet;
import tw.edu.chit.model.Rcproj;
import tw.edu.chit.model.RecruitSchool;
import tw.edu.chit.model.Register;
import tw.edu.chit.model.RegistrationCard;
import tw.edu.chit.model.StdAbility;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.StdSkill;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.TeacherStayTime;
import tw.edu.chit.model.TempStmd;
import tw.edu.chit.model.domain.UserCredential;

public interface MemberManager {

    public void setMemberDAO(MemberDAO dao);

    public UserCredential createUserCredential(String account, String password) throws InvalidAccountException;
    
    public UserCredential createInetUserCredential(String account, String password) throws InvalidAccountException;
    
    public UserCredential createInetUserCredential4Parent(String idno, String birthdate, String parentName) throws InvalidAccountException;
    
    public void createClassInChargeDataStructure(Clazz[] classAry, List<Code5> campuses, List<Code5[]> schools, List<Code5[][]> depts, List<Clazz[][][]> classes);

    public Menu createMenuByMember(Member member);
    
    public Menu createInetMenuByMember(Member member);

    public Menu createMenuForStudent();
    
    public Menu createMenuForParent();
    
    public Menu createMenuForStudent(String schoolType);
    
    public Member findMemberByAccount(String account);
    
    //public List findModulesByParent(String parentName);

    public List<Clazz> findClassInChargeByMemberAuthority(Integer memberOid, String authorityTarget);
    
	/**
	 * This method differs from findClassInChargeByMemberAuthority() only with additional user-friendly
	 * info has been looked up and feed into Clazz (actually base class ClazzBase)
	 * @param memberOid
	 * @param authorityTarget
	 * @return
	 
	public List<Clazz> findClassInChargeByMemberAuthority2(Integer memberOid, String authorityTarget);
	*/
    public List<Student> findStudentsInChargeByUnits(String campusNo, String schoolNo, String deptNo, String classNo, String classInCharge);

    @SuppressWarnings("unchecked")
    public List<Student> findStudentsByStudentInfoForm(Map formProperties);
    
    @SuppressWarnings("unchecked")
    public List<Student> findStudentsInChargeByStudentInfoForm(Map formProperties, String classInCharge);
    
    /**
	 * 
	 * @param entity
	 * @param expression
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List findSQLWithCriteria(Class entity, SimpleExpression... expression)
			throws DataAccessException;
	
	/**
	 * 
	 * @param entity
	 * @param expression
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List findSQLWithCriteria(Class entity, Example example,
			Criterion... expression) throws DataAccessException;
    
    /**
     * 查詢學生資料
     * 
     * @commend 包括全校學生
     * @param formProperties Request Parameters
     * @return java.util.List List of Student objects
     */
    @SuppressWarnings("unchecked")
    public List<Student> findStudentsInChargeByStudentInfo(Map formProperties);

    @SuppressWarnings("unchecked")
    public List<Graduate> findGraduatesInChargeByGraduateInfoForm(Map formProperties, String classInCharge);

    public List<Student> findStudentsInChargeByStudentInfoFormT(Map formProperties, String classInCharge);
    
    public List<Student> findStudentsInChargeByStudentInfoFormC(Map formProperties, String classInCharge);

    public Graduate findGraduateByStudentNo(String studentNo);
    
    public Graduate findGraduateByIdno(String idno);

	public List<Student> deleteStudents(List<Student> students, ResourceBundle bundle);

	public List<Graduate> deleteGraduates(List<Graduate> students, ResourceBundle bundle);
	
	public void deleteEmployees(List<Empl> employees, ResourceBundle bundle);
	
	public void deleteQuitEmployees(List<DEmpl> employees, ResourceBundle bundle);

	public void validateStudentCreateForm(Map formProperties, ActionMessages errors, ResourceBundle bundle);

	public void validateGraduateCreateForm(Map formProperties, ActionMessages errors, ResourceBundle bundle);
	
	public void validateEmployeeCreateForm(Map formProperties, ActionMessages errors, ResourceBundle bundle);

	public void validateStudentModifyForm(Map formProperties, Student student, ActionMessages errors, ResourceBundle bundle);

	public void validateGraduateModifyForm(Map formProperties, Graduate student, ActionMessages errors, ResourceBundle bundle);

	public void validateEmployeeModifyForm(Map formProperties, Empl employee, ActionMessages errors, ResourceBundle bundle);
	
	public void validateQuitEmployeeModifyForm(Map formProperties, DEmpl employee, ActionMessages errors, ResourceBundle bundle);
	
	public Student createStudent(Map formProperties);

	public Graduate createGraduate(Map formProperties);
	
	public Empl createEmployee(Map formProperties);

	public void txModifyStudent(Map formProperties, Student student, boolean autoDeselect);

	public void txModifyGraduate(Map formProperties, Graduate student);
	
	public void txModifyEmployee(Map formProperties, Empl employee);
	
	public void txModifyQuitEmployee(Map formProperties, DEmpl employee);
	
	//public void txModifyStudentWithAutoClear(Map formProperties, Student student);
	
	public void modifyEmployeePhoneAndAddress(Map formProperties, Employee employee);

	public List<Entrno> findEntrnoByStartEndNoDocNo(String startNo, String endNo, String docNo);

	public void deleteEntrnos(List<Entrno> entrnos);

	public Entrno createEntrno(String startNo, String endNo, String docNo);

	public void modifyEntrno(String startNo, String endNo, String docNo, Entrno entrno);

	public void setupClassGroup(String classNo, String groupId);

	//public Graduate moveStudentToGraduate(Student student);

	//public Student moveGraduateToStudent(Graduate graduate);

	public void processQuitResume(Graduate graduate, Map formProperties);

	public Employee findEmployeeByIdno(String idno);
	
	public Empl findEmplByIdno(String idno);
	public DEmpl findDEmplByIdno(String idno);

	public List<Clazz> findAllPhysicalClasses();

	public List<Clazz> findAllClasses();

	public void txResetClassInCharge(Integer empOid, String classOids, String authorityTarget);
	
	public void batchInsertClassInCharge(Integer empOid, String classNo, String authorityTarget);
	
	public void txResetStudAffairClassInCharge(Integer empOid, String classOids, String authorityTarget, UserCredential operator);

	/**
	 * 定義以學生姓名關鍵字查詢學生姓名清單之Service方法
	 *
	 * @param keyword 學生姓名關鍵字
	 * @return java.util.List List of String objects
	 */
	public List<String> findStudentNameListByKeyword(String keyword);

	public List<Empl> findEmplsByEmployeeInfoForm(Map formProperties);
	
	//public List<DEmpl> findDEmplsByQuitEmployeeInfoForm(Map formProperties);

	/**
	 * 定義以班級代碼查詢班級所有學生
	 *
	 * @param classNo 班級代碼
	 * @return java.util.List List of Student objects
	 */
	public List<Student> findStudentsByClassNo(String classNo);
	
	/**
	 * 實作以班級代碼查詢休退畢所有學生
	 * 
	 * @param classNo 班級代碼
	 * @return java.util.List List of Student objects
	 */
	public List<Graduate> findGraduatesByClassNo(String classNo);
	
	public List<Code5> findAllUnits();
	
	public List<Code5> findStayTime();
	
	/**
	 * 更新留校時間維護起迄日期及統計起始日期
	 * @param code5s 留校時間維護起迄日期
	 * @param code5d 統計起始日期
	 */
	public void updatStayTime(List<Code5> code5s, List<Code5> code5d);
	
	public List<Code5> findStayTimeDead();
	
	public List<Empl> findEmplByGroup(String groupNo);
	
	public void addEmployeeToGroup(Integer empOid, String unitNo);
	
	public void removeEmployeeFromGroup(Integer empOid, String unitNo);
	
	/**
	 * 定義更新學生聯絡電話與住址之Service方法
	 * 
	 * @param formProps Form properties
	 * @param student tw.edu.chit.model.Student object
	 * @return tw.edu.chit.model.Student object
	 */
	public Student updateStudentPhoneAndAddress(Map formProps, Student student);
	
	/**
	 * 定義新增/更新教師辦公室分機與留校時間資料之Service方法
	 * 
	 * @param empl tw.edu.chit.model.Empl
	 * @param formProps HTML Form properties
	 * @param stayTimes 留校資料
	 */
	@SuppressWarnings("unchecked")
	public void txUpdateTeacherStayTime(Empl empl, Map formProps,
			List<TeacherStayTime> stayTimes, String year, String term,
			boolean needRecord);
	
	public void txSaveStayTime(List<TeacherStayTime> stayTimes);
	
	/**
	 * 儲存生活輔導時間
	 * 
	 * @param lcs
	 */
	public void txSaveLifeCounseling(List<LifeCounseling> lcs);
	
	/**
	 * 實作刪除教師辦公室分機與留校時間資料之Service方法
	 * 
	 * @param empl tw.edu.chit.model.Empl
	 * @param formProps HTML Form properties
	 * @param stayTimes 留校資料
	 */
	public void txDeleteTeacherStayTime(List<TeacherStayTime> stayTimes,
			String year, String term);
	
	/**
	 * 實作刪除導師生活輔導時間資料之Service方法
	 * 
	 * @param lcs 生活輔導時間
	 */
	public void txDeleteLifeCounseling(List<LifeCounseling> lcs);
	
	/**
	 * 實作以Oid取得Empl物件
	 * 
	 * @param oid Empl Oid
	 * @return tw.edu.chit.model.Empl object
	 */
	public Empl findEmplByOid(Integer oid);
	
	public Empl findEmplFilterBy(Integer oid, String year);
	
	public Member resetMemberPassword(String account);
	
	public Member resetMemberInformixPassword(String account);
	
	public void changeMemberPassword(Member member, String password);
	
	/**
	 * 定義以Empl Oid取得老師留校時間表之Service方法
	 * 
	 * @param oid Empl Oid
	 * @return java.util.List List of TeacherStayTime objects
	 */
	public List<TeacherStayTime> findStayTimeByEmplOid(Integer oid,
			String year, String term);
	
	/**
	 * 實作以Empl Oid取得導師生活輔導時間表之Service方法
	 * 
	 * @param oid
	 * @return
	 */
	public List<LifeCounseling> findLifeCounselingByEmplOid(Integer oid);
	
	/**
	 * 定義以學號取得學生資料之Service方法
	 * 
	 * @param no Student NO
	 * @return tw.edu.chit.model.Student object
	 */
	public Student findStudentByNo(String no);
	
	/**
	 * 定義新增/更新導師班級幹部聯繫資料之Service方法
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 * @param classCadres List of ClassCadre objects
	 */
	public void txUpdateClassCadre(Empl empl, List<ClassCadre> classCadres);

	/**
	 * 定義以Empl導取得導師班級幹部聯繫資料之Service方法
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 * @return java.util.List List of ClassCadre objects
	 */
	public List<ClassCadre> findClassCadreByEmpl(Empl empl);
	
	/**
	 * 定義以Oid取得Clazz資料之Service方法
	 * 
	 * @param oid Clazz Oid
	 * @return tw.edu.chit.model.Clazz object
	 */
	public Clazz findClassByOid(Integer oid);
	
	/**
	 * 定義以Empl Oid與班級代碼取得ClassCadre清單之Service方法
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 * @param classNo 班級代碼
	 * @return java.util.List List of ClassCadre objects
	 */
	public List<ClassCadre> findClassCadreByClassNo(Empl empl, String classNo);
	
	/**
	 * 定義以Student Oid取得Student物件之Service方法
	 * 
	 * @param oid Student Oid
	 * @return tw.edu.chit.model.Student object
	 */
	public Student findStudentByOid(Integer oid);
	
	/**
	 * 定義刪除TeacherStayTime清單之Service方法
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 */
	public void deleteStayTimeByEmpl(List<TeacherStayTime> tst);
	
	/**
	 * 依據Unit代碼取得Empl物件清單
	 * 
	 * @param unit Unit代碼
	 * @return java.util.List List of Empl objects
	 */
	public List<Empl> findTeacherByUnit(String unit);
	
	/**
	 * 儲存Student物件
	 * 
	 * @param student tw.edu.chit.model.Student object
	 */
	public void txUpdateStudent(Student student);
	
	/**
	 * 查詢Student大頭照物件
	 * 
	 * @param image
	 * @return
	 */
	public StdImage findStdImageBy(StdImage image);
	
	public List<ContractTeacher> findContractTeacherBy(
			ContractTeacher contractTeacher) throws DataAccessException;
	
	public List<ClassInCharge> findClassInChargeBy(ClassInCharge classInCharge)
			throws DataAccessException;
	
	public List<DeptCode4Yun> findDeptCode4YunBy(DeptCode4Yun deptCode4Yun)
			throws DataAccessException;
	
	public List<Aborigine> findAborigineBy(Aborigine aborigine)
			throws DataAccessException;
	
	public List<CodeEmpl> findAllUnit() throws DataAccessException;
	
	public List<CodeEmpl> findCodeEmplByCategory(String category);
	
	/**
	 * 儲存Student大頭照物件
	 * 
	 * @param image tw.edu.chit.model.StdImage object
	 */
	public void txUpdateStudentImage(StdImage image);
	
	/**
	 * 查詢某年學生之技能檢定項目
	 * 
	 * @param year 學年
	 * @return java.util.List List of AbilityExamine Objects
	 */
	public List<AbilityExamine> findAbilityExamineBySchoolYear(String year);
	
	/**
	 * 以代碼取得技能檢定資料
	 * 
	 * @param no 技能檢定代碼
	 * @return AbilityExamine tw.edu.chit.model.AbilityExamine Object
	 */
	public AbilityExamine findAbilityExamineByNo(Integer no);
	
	/**
	 * 以學號取得學生所有技能檢定資料
	 * 
	 * @param studentNo 學號
	 * @param abilityCode 技能代碼
	 * @return java.util.List List of StdAbility Objects
	 */
	public List<StdAbility> findStudentAbilityByStudentNoAndAbilityNo(
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
	public List<StdSkill> findStdSkillsBy(String schoolYear, String schoolTerm,
			String studentNo, String skillCode);
	
	/**
	 * 
	 * @param recruitSchool
	 * @return
	 */
	public List<RecruitSchool> findRecruitSchoolsBy(RecruitSchool recruitSchool);
	
	/**
	 * 新增學生技能檢定資料
	 * 
	 * @param sas List of tw.edu.chit.model.StdAbility Object
	 */
	public void saveStudentAbility(List<StdAbility> sas);
	
	/**
	 * 刪除學生技能檢定資料
	 * 
	 * @param sas List of tw.edu.chit.model.StdAbility Object
	 */
	public void deleteStdAbility(List<StdAbility> sas);
	
	/**
	 * 查詢離職教職員工
	 * 
	 * @param dempl tw.edu.chit.model.DEmpl Object
	 * @return java.util.List List of tw.edu.chit.model.DEmpl Object
	 */
	public List<DEmpl> findDEmplBy(DEmpl dempl);
	
	public List<Rcact> findRcactsBy(Rcact rcact) throws DataAccessException;
	
	public List<Rcproj> findRcprojsBy(Rcproj rcproj) throws DataAccessException;
	
	public List<Rcjour> findRcjoursBy(Rcjour rcjour) throws DataAccessException;
	
	public List<Rcconf> findRcconfsBy(Rcconf rcconf) throws DataAccessException;
	
	public List<Rcbook> findRcbooksBy(Rcbook rcbook) throws DataAccessException;
	
	public List<Rcpet> findRcpetsBy(Rcpet rcpet) throws DataAccessException;
	
	public List<Rchono> findRchonosBy(Rchono rchono) throws DataAccessException;
	
	/**
	 * 儲存應屆畢業生出路與通訊資料
	 * 
	 * @param image tw.edu.chit.model.Investigation object
	 */
	public void txUpdateInvestigation(Investigation investigation);
	
	/**
	 * 儲存應屆畢業生出路與通訊資料
	 * 
	 * @param investigation
	 */
	public void txUpdateInvestigationG(InvestigationG investigationG);
	
	/**
	 * 刪除應屆畢業生出路與通訊資料
	 * 
	 * @param investigation
	 */
	public void txDeleteInvestigation(Investigation investigation);
	
	/**
	 * 刪除應屆畢業生出路與通訊資料
	 * 
	 * @param investigation
	 */
	public void txDeleteInvestigationG(InvestigationG investigationG);
	
	/**
	 * 儲存新生學籍卡資料
	 * 
	 * @param student
	 */
	public void txUpdateRegistrationCard(Student student);
	
	/**
	 * 刪除新生學籍卡資料
	 * 
	 * @param registrationCard
	 */
	public void txDeleteRegistrationCard(RegistrationCard registrationCard);
	
	/**
	 * 查詢在校生清單資料
	 * 
	 * @param student tw.edu.chit.model.Student
	 * @return java.util.List List of Student Objects
	 */
	public List<Student> findStudentsBy(Student student);
	
	/**
	 * 查詢離校校生清單資料
	 * 
	 * @param student tw.edu.chit.model.Graduate
	 * @return java.util.List List of Graduate Objects
	 */
	public List<Graduate> findGraduatesBy(Graduate graduate);
	
	/**
	 * 更新新生學籍卡
	 * 
	 * @param student
	 * @param rc
	 */
	public void updateRegistrationCard(Student student, RegistrationCard rc);
	
	public UserCredential createUserCredential(String account);
	
	public List<Empl> findEmplByNameLike(String sname);
	
	public Empl findEmplByName(String sname);
	
	/**
	 * 
	 * @param term
	 * @return
	 * @throws DataAccessException
	 */
	public Criterion findAllTeacherCriterion(String term)
			throws DataAccessException;
	
	/**
	 * 
	 * @param term
	 * @return
	 * @throws DataAccessException
	 */
	public List<Empl> findAllTeacher(String term) throws DataAccessException;
	
	/**
	 * 
	 * @param empl
	 * @return
	 * @throws DataAccessException
	 */
	public List<Empl> findEmplsBy(Empl empl) throws DataAccessException;
	
	/**
	 * 
	 * @param workShifts
	 * @return
	 */
	public List<Empl> findEmplByWorkShift(String[] workShifts)
			throws DataAccessException;
	
	/**
	 * 
	 * 
	 * @param regs
	 * @param tempStmds
	 * @throws DataAccessException
	 */
	public void txSaveRegister(List<Register> regs, List<TempStmd> tempStmds)
			throws DataAccessException;
	
	/**
	 * 取得現任教職員工所屬單位名稱
	 * @param idno 身分證號
	 * @return 單位名稱或空白字串
	 */
	public String findEmplUnitByIdno(String idno);
	
	/**
	 * 新增行政人員服務滿意度調查表列印資料
	 * @param idno 行政人員idno
	 * @param codes 服務流水編號
	 * @return 儲存結果
	 */
	public ActionMessages addNewAssessPaper(String idno, String[] codes);
	
	/**
	 * 依據服務編號取得服務滿意度調查表
	 * @param serviceNo 服務編號
	 * @return 服務滿意度調查表 or null
	 */
	public AssessPaper findAssessPaperByNo(String serviceNo);
	
	/**
	 * 儲存回覆的服務滿意度調查表
	 * @param user 使用者UserCredential
	 * @param paper 調查表
	 * @param form 輸入表單資料
	 * @return 儲存結果
	 */
	public ActionMessages saveAssessPaperReply(UserCredential user, AssessPaper paper, DynaActionForm form);
	
	/**
	 * 取得個別行政人員的服務滿意度調查表
	 * @param idno 身分證號
	 * @return Map{papers:滿意度調查表, total:已回應份數, avgScore:平均分數}
	 */
	public Map getAssessPaperByIdno(String idno);
	
	/**
	 * 取得現有一級單位(有在職教職員的單位)
	 * 
	 * @return 單位
	 */
	
	public List<CodeEmpl> getEmplUnits();
	
	/**
	 * 取得行政人員的服務滿意度調查報表
	 * @param form DynaActionForm傳入參數
	 * 	reportType : 1:統計， 2:優劣
	 *	unit : 單位
	 *	startDate : 起始日期
	 *	endDate : 結束日期
	 *
	 * @return 報表結果
	 */
	public List getAssessPaperReportbyForm(DynaActionForm form);
	
}