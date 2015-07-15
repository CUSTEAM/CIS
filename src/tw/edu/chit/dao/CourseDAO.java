package tw.edu.chit.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.SimpleExpression;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.CourseSyllabus;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DepartmentInfo;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.DtimeExam;
import tw.edu.chit.model.DtimeTeacher;
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

public interface CourseDAO extends DAO {

	public List StandardHqlQuery(String hql);

	public List getCourse(String no, String name, String ename);

	public void saveCsno(Csno csno);

	public void removeCsno(Csno csno);

	public List getDtimeForDeleteCourseName(String cscode);

	public List getEmployeeForOpenCS();

	public List getCsnoForOpenCs();

	public List getClassForOpenCs();

	public List getDtimeForOpenCs(String sql);

	public List getDtimeForEditDtime(String oid);

	public List getEmplForOpenCourse(String sql);

	public List getDtimeClassListForOpenCourse(String sql);

	public List getDtimeExamListForOpenCourse(String sql);

	public List checkCourseForOpenCourse(String sql);

	public List checkEmplForOpenCourse(String sql);

	public List checkEmplForReOpenCourse(String sql);

	public List checkRushCourseForOpenCourse(String sql);

	public List getNowTerm(String sql);

	public List getExamTeacher(String sql1);

	public void saveDtime(Dtime dtime);

	public List checkSeldForCourseName(String cscode);

	public List getModifyResult(String sql);

	public List checkDtimeClassForOpenCourse(String sql);

	public List checkNabbrForOpenCs(String sql);

	public List getDtimeByOid(String sql);

	/**
	 * 定義以學號查詢選課資訊之方法
	 *
	 * @param studentNo 學號
	 * @param term 學期
	 * @return java.util.List List of Seld objects
	 */
	public List<Seld> getSeldDataByStudentNo(String studentNo, String term);
	
	public List checkReOpenRoom(String sql);

	/**
	 * 定義以Oid取得Seld物件之方法
	 *
	 * @param oid Seld object Oid
	 * @return Seld tw.edu.chit.model.Seld object
	 */
	public Seld getSeldByOid(Integer oid);

	/**
	 * 定義新增或更新Seld物件之方法
	 *
	 * @param seld tw.edu.chit.model.Seld object
	 */
	public void saveOrUpdateSeld(Seld seld);

	public void saveDtimeClass(DtimeClass dtimeClass);

	public void saveDtimeExam(DtimeExam dtimeExam);

	/**
	 * 定義處理學生退選,以Seld Oid列表刪除學生選課資料
	 *
	 * @param seldOid Seld資料表之Oid列表
	 * @return String 所有刪除Seld資料表之Dtime_oid列表
	 */
	public String deleteSeldByOids(String seldOid);

	/**
	 * 定義以Seld物件查詢Seld物件
	 *
	 * @commend 此方法主要為新增Seld€時檢查是否有無重複資料之判斷
	 * @param seld tw.edu.chit.model.Seld object
	 * @return java.util.List List of Seld objects
	 */
	public List<Seld> findSeldBy(Seld seld);

	/**
	 * 定義以Seld資料表Oid列表,更新Dtime資料表學生選課stu_select人數
	 *
	 * @param dtimeOids Seld資料表之Dtime_oid列表
	 * @param count 加/減人數
	 */
	public void updateDtimeStuSelectByOids(String dtimeOids, Short count);

	/**
	 * 定義新增Adcd資料表方法
	 *
	 * @param adcd tw.edu.chit.model.Adcd object
	 */
	public void saveOrUpdateAdcd(Adcd adcd);

	/**
	 * 定義刪除Adcd資料表方法
	 *
	 * @param adcd tw.edu.chit.model.Adcd object
	 */
	public void deleteAdcd(Adcd adcd);
	
	/**
	 * 處理刪除StdLoan資料表之方法
	 * 
	 * @param stdLoan tw.edu.chit.model.StdLoan object
	 */
	public void deleteStdLoan(StdLoan stdLoan);

	/**
	 * 定義以cscode取得Csno物件之方法
	 *
	 * @param cscode 科目代碼
	 * @return Csno tw.edu.chit.model.Csno object
	 */
	public Csno getCsnoByCscode(String cscode);

	/**
	 * 定義依據科目代碼與班級代碼查詢開課基本資料
	 *
	 * @param classNo 班級代碼
	 * @param csCode 科目代碼
	 * @param sterm 學期
	 * @return Dtime seld tw.edu.chit.model.Dtime object
	 */
	public Dtime getDtimeByCsCodeAndClassNo(String classNo, String csCode,
			String sterm);

	/**
	 * 定義以學生資料物件查詢開課資料
	 *
	 * @param student tw.edu.chit.model.Student object
	 * @return java.util.List List of Object Array
	 */
	public List<Object[]> getOpencsByStudnet(Student student);

	/**
	 * 定義以學生班級資料物件查詢開課資料
	 *
	 * @param student tw.edu.chit.model.Clazz object
	 * @param dtimeOid Dtime Oid
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> getOpencsByStudnetClass(Clazz clazz, Integer dtimeOid);

	/**
	 * 定義以Dtime Oid取得Opencs清單資料
	 *
	 * @param dtimeOid Dtime Oid
	 * @return java.util.List List of Opencs objects
	 */
	public List<Opencs> findOpencsByDtimeOid(Integer dtimeOid);

	/**
	 * 定義以依據老師取得開課主檔資料
	 *
	 * @param member tw.edu.chit.model.Member object
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> getDtimeByTeacher(Member member);
	
	/**
	 * 實作以依據老師取得一科目多教師資料
	 * 
	 * @param member tw.edu.chit.model.Member object
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> getDtimeTeacherByTeacher(Member member);
	
	/**
	 * 實作以依據老師取得開課主檔資料
	 * 
	 * @param member tw.edu.chit.model.Member object
	 * @param term School Term
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> getDtimeByTeacher(Member member, String term);
	
	/**
	 * 實作以依據老師取得歷年開課主檔資料
	 * 
	 * @param member tw.edu.chit.model.Member object
	 * @param term School Term
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> getDtimeHistByTeacher(Member member, String year,
			String term);

	/**
	 * 定義以Dtime Oid更新Dtime資料表之目前選課人數
	 *
	 * @param dtimeOid Dtime object Oid
	 * @param stdSel 選課人數
	 */
	public void updateDtimeStdSelect(Integer dtimeOid, Short stdSel);

	/**
	 * 儲存一科目多教師
	 * @param dtimeTeacher
	 */
	public void SaveDtimeTeacher(DtimeTeacher dtimeTeacher);

	/**
	 * 定義將班級學生之基本選課資料刪除
	 *
	 * @param dtime java.util.Map object
	 * @param students List of Student objects
	 * @return int 回傳刪除個數
	 */
	public int delSeldByDtimeAndStudents(Map dtime, List<Student> students);

	/**
	 * 定義刪除Adcd中學生之基本選課資料
	 *
	 * @param dtime java.util.Map object
	 * @param students List of Student objects
	 * @return int 回傳刪除個數
	 */
	public int delAdcdByDtimeAndStudents(Map dtime, List<Student> students);

	public void txsaveOpencsBy(Opencs opencs);

	/**
	 * 定義新增/更新CourseIntroduction資料表資料
	 *
	 * @param ci tw.edu.chit.model.CourseIntroduction object
	 */
	public void saveCourseIntro(CourseIntroduction ci);

	/**
	 * 定義以Dtime Oid及學年與學期取得CourseIntroduction清單資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public List<CourseIntroduction> getCourseIntroByDtimeOid(Integer dtimeOid,
			Integer year, Integer term);
	
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
	public List<CourseIntroduction> getCourseIntroHistBy(Integer year,
			Integer term, String departClass, String cscode);

	/**
	 * 定義以Oid取得CourseIntroduction清單資料
	 *
	 * @param oid CourseIntroduction Oid
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public CourseIntroduction getCourseIntroByOid(Integer oid);

	/**
	 * 定義新增/更新Regs資料表資料
	 *
	 * @param regs tw.edu.chit.model.Regs object
	 */
	public void saveOrUpdateRegs(Regs regs);

	/**
	 * 定義刪除Regs中學生之基本選課資料
	 *
	 * @param dtime java.util.Map object
	 * @param students List of Student objects
	 * @return int 回傳刪除個數
	 */
	public int delRegsByDtimeAndStudents(Map dtime, List<Student> students);

	/**
	 * 定義刪除某學生在Regs中之基本選課紀錄資料
	 *
	 * @param dtime tw.edu.chit.model.Dtime object
	 * @param stdNo 學號
	 */
	public void delRegsByDtimeAndStdNo(String dtimeOid, String stdNo);
	
	/**
	 * 實作以Dtime Oid及學年與學期取得CourseIntroduction清單資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public CourseIntroduction getCourseIntrosByDtimeOid(Integer dtimeOid,
			Integer year, Integer term);
	
	public CourseIntroduction getCourseIntroBy(CourseIntroduction ci);

	/**
	 * 定義以Dtime Oid及學年與學期取得CourseSyllabus清單資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus getCourseSyllabusByDtimeOid(Integer dtimeOid, Integer yaer, Integer term);
	
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
	public CourseSyllabus getCourseSyllabusByDtimeOid(String departClass,
			String cscode, Integer year, Integer term);

	/**
	 * 定義以Oid取得CourseSyllabus清單資料
	 *
	 * @param oid CourseSyllabus Oid
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus getCourseSyllabusByOid(Integer oid);
	
	/**
	 * 以CourseSyllabus取得資料
	 * 
	 * @param courseSyllabus CourseSyllabus Object
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus getCourseSyllabusBy(CourseSyllabus courseSyllabus);
	
	/**
	 * 以CourseSyllabus取得資料
	 * 
	 * @param courseSyllabus CourseSyllabus Object
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus getCourseSyllabusBy1(CourseSyllabus courseSyllabus);

	/**
	 * 定義新增/更新CourseSyllabus資料表資料
	 *
	 * @param ci tw.edu.chit.model.CourseSyllabus object
	 */
	public void saveCourseSyllabus(CourseSyllabus cs);

	/**
	 * 定義更新CourseSyllabus資料表資料
	 *
	 * @param ci tw.edu.chit.model.CourseSyllabus object
	 */
	public void updateCourseSyllabus(CourseSyllabus cs);

	/**
	 * 定義取得教師上課時間資料
	 *
	 * @param member tw.edu.chit.model.Member object
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> findTeachInfoByMember(Member member);

	/**
	 * 定義取得Dtime資料表目前選課人數
	 *
	 * @param dtimeOid Dtime物件之Oid
	 * @return Dtime資料表目前選課人數欄位之值
	 */
	public Short getDtimeStdSelect(Integer dtimeOid);

	/**
	 *
	 * @param dtimeOid
	 * @param selectLimit
	 */
	public void updateDtimeSelLimit(String dtimeOid, Short selectLimit);

	public List<Seld> findSeldByStudentNo(String studentNo);
	
	public List<Seld> findSeldByStudentNoAndTerm(String studentNo, String term);

	/**
	 * 儲存Nabbr物件
	 * 
	 * @param nabbr
	 */
	public void saveNabbr(Nabbr nabbr);
	
	/**
	 * 儲存StdLoan物件
	 * 
	 * @param stdLoan
	 */
	public void saveStdLoan(StdLoan stdLoan);

	/**
	 * 定義以Dtime Oid查詢選課學生清單
	 *
	 * @param dtimeOid Dtime Oid
	 * @return java.util.List List of Student objects
	 */
	public List<Student> getSeldStudentByDtimeOid(Integer dtimeOid);

	/**
	 * 定義以Class No查詢部制代碼
	 *
	 * @param classNo 班級代碼
	 * @return 部制代碼,D為日間,N為夜間,H為學院
	 */
	public String getSchoolTypeByClassNo(String classNo);

	/**
	 * 實作以School No查詢部制代碼
	 *
	 * @param School 班級代碼
	 * @return 部制代碼,D為日間,N為夜間,H為學院
	 */
	public String getSchoolTypeBySchoolNo(String schoolNo);

	/**
	 * 以班級代碼取得校區代碼
	 *
	 * @param classNo 班級代碼
	 * @return 校區代碼
	 */
	public String getCampusNoByClassNo(String classNo);

	/**
	 * 以Dtime Oid取得Seld目前選課人數
	 *
	 * @param dtimeOid Dtime Oid
	 * @return int 目前選課人數
	 */
	public int getSeldCountByDtimeOid(Integer dtimeOid);

	/**
	 * 以Dtime Oid取得加退選紀錄
	 *
	 * @param dtimeOid Dtime OId
	 * @return java.util.List List of Adcd objects
	 */
	public List<Adcd> getAdcdByDtimeOid(Integer dtimeOid);

	/**
	 * 以學生學號取得加退選結果
	 *
	 * @param studentNo 學生學號
	 * @param term 學期
	 * @return java.util.List List of Adcd objects
	 */
	public List<Adcd> getAdcdByStudentNo(String studentNo, String term);

	/**
	 * 以學生學號查詢學生加退選歷程資料
	 *
	 * @param studentNo 學生學號
	 * @param term 學期
	 * @return java.util.List List of Objects
	 */
	public List<StdAdcd> getStudentAdcdByStudentNo(String studentNo, String term);

	public Dtime getDtimeBy(Integer oid);

	/**
	 * Find Dtimes by depart_class and open(開放選修) or not
	 *
	 * @param classNo
	 * @param open
	 * @return
	 */
	public List<Dtime> findDtimeByClassTermOpen(String classNo, String term, boolean open);

	/**
	 * 以School No查詢選課人數下限
	 *
	 * @param schoolNo School No
	 * @return 選課人數下限
	 */
	public int getMinCountBySchoolNo(String schoolNo);

	public List<DtimeClass> getDtimeClassInfo(Seld seld);

	/**
	 * 以category查詢科系資料
	 *
	 * @param category 分類
	 * @return tw.edu.chit.model.DepartmentInfo objects
	 */
	public DepartmentInfo getDepartmentInfoByCategory(String category);

	public void removeObject(Object o);
	
	/**
	 * 查詢Dtime物件
	 * 
	 * @param d tw.edu.chit.model.Dtime Object
	 * @param orderField Order Field
	 * @return java.util.List List of tw.edu.chit.model.Dtime Objects
	 */
	public List<Dtime> getDtimeBy(Dtime d, String orderField);
	
	/**
	 * 查詢Dtime與Cscode物件
	 * 
	 * @param d tw.edu.chit.model.Dtime Object
	 * @param orderField Order Field
	 * @return java.util.List List of tw.edu.chit.model.Dtime Objects
	 */
	public List<Object> getDtimeCsnoBy(Dtime d, String orderField);
	
	/**
	 * 查詢Nabbr物件
	 * 
	 * @param d tw.edu.chit.model.Nabbr Object
	 * @return tw.edu.chit.model.Nabbr Object
	 */
	public Nabbr getNabbrBy(Nabbr n);
	
	/**
	 * 查詢Gmark物件
	 * 
	 * @param gmark
	 * @return List of Gmark Objects
	 */
	public List<Gmark> getGMarkBy(Gmark gmark);
	
	/**
	 * 查詢StdLoan物件
	 * 
	 * @param stdLoan
	 * @return List of StdLoan Objects
	 */
	public List<StdLoan> getStdLoanBy(StdLoan stdLoan);
	
	public List<Dtime> getDtimesBy(Dtime dtime);
	
	public List<LiteracyType> getLiteracyTypesBy(LiteracyType literacyType)
			throws DataAccessException;
	
	@SuppressWarnings("unchecked")
	public List getSQLWithCriteria(Class entity, SimpleExpression... expression)
			throws DataAccessException;
	
	public void clearOne(Object po);
}
