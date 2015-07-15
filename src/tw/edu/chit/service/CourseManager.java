package tw.edu.chit.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.upload.FormFile;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.gui.Menu;
import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Coansw;
import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.CourseSyllabus;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.DepartmentInfo;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.DtimeExam;
import tw.edu.chit.model.DtimeTeacher;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.EmplSave;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Gmark;
import tw.edu.chit.model.LiteracyType;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Message;
import tw.edu.chit.model.Nabbr;
import tw.edu.chit.model.Opencs;
import tw.edu.chit.model.PubCalendar;
import tw.edu.chit.model.Rcact;
import tw.edu.chit.model.Regs;
import tw.edu.chit.model.Savedtime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.StdAdcd;
import tw.edu.chit.model.StdLoan;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.WwPass;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.impl.exception.SeldException;
import tw.edu.chit.struts.action.course.exception.NoClassDefinedException;

public interface CourseManager {

	public List<Csno> findCoursesBy(String csno, String csname, String csename);

	public void createCourseNameBy(Csno csno);

	public void deleteCourseNameBy(Csno csno);
	
	/**
	 * 處理刪除StdLoan資料表之方法
	 * 
	 * @param stdLoan tw.edu.chit.model.StdLoan object
	 */
	public void deleteStdLoan(StdLoan stdLoan);

	public List<Dtime> getDtimeForDeleteCourse(String cscode);

	public void updateCourseNameBy(Csno csno);

	public List<Employee> getEmployeeForOpenCs();

	public List<Csno> findCourseForOpenCs();

	public List<Clazz> getClassForOpenCs();
	
	public List hqlGetBy(String hql);

	public List getDtimeForOpenCs(Clazz[] classes, String campusInCharge2,
			String schoolInCharge, String deptInCharge2, String departClass,
			String cscode, String techid, String term, String choseType, String open, String elearning);

	public List getDtimeForOpenCsAll(Clazz[] classes, String cscode,
			String techid, String sterm, String choseType, String open, String elearning);

	public List getDtimeForEditDtime(String oid);

	public List getEmplForOpenCourse(String techid);

	public List getDtimeClassListForOpenCourse(String oid);

	public List getDtimeExamListForOpenCourse(String oid);

	public List checkEmplForOpenCourse(String idno);

	public List checkCourseForOpenCourse(String cscode);

	public List checkRushCourseForOpenCourse(String departClass, String cscode,
			String term, String dtimeOid);

	public List getAclassNameForCreateDtime(String departClass);
	
	public String getNowBy(String termORyear);
	
	String getOpen(boolean open);
	
	public String getCross(String DtimeOid);
	
	public String getExtrapay(String extrapay);

	public void updateDtime(Dtime dtime);

	public List checkSeldForCourseName(String cscode);

	public List getModifyResult(String cscode, String departClass, String sterm);

	public List checkDtimeClassForOpenCourse(String week, String beginOrEnd,
			String place, String sterm);

	public List checkNabbrForOpenCs(String roomId);

	public List getDtimeClassByOid(String oid);

	/**
	 * 定義以學號查詢Seld物件之方法
	 *
	 * @param studentNo
	 * @param term 學期
	 * @return java.util.List List of Objects
	 */
	public List getSeldDataByStudentNo(String studentNo, String term);
	
	/**
	 * 處理以學號查詢Seld選課清單之方法
	 * 
	 * @comment 不包括Dtime_class資料
	 * @param studentNo 學號
	 * @param term 學期
	 * @return java.util.List List of Seld objects
	 */
	public List getSeldDataByStudentNo1(String studentNo, String term);

	public List checkEmplForReOpenCourse(String idno, String week,
			String beginOrEnd, String sterm, String dtimeOid);

	public List checkReOpenRoom(String place, String week, String beginOrEnd,
			String sterm, String dtimeOid);

	/**
	 * 定義以Oid取得Seld物件之方法
	 *
	 * @param oid Seld object oid
	 * @return Seld tw.edu.chit.model.Seld object
	 */
	public Seld getSeldByOid(Integer oid);

	/**
	 * 定義新增或更新Seld物件之方法
	 *
	 * @param seld tw.edu.chit.model.Seld object
	 */
	public void saveOrUpdateSeld(Seld seld);

	public void saveDtimeClass(DtimeClass dtimcClass);

	public void removeDtimeClass(String dtimeOid);

	public List checkDtimExamIdno(String dtimeOid, String examDate,
			String idno, String begin);

	public List checkDtimExamPlace(String dtimeOid, String examDate,
			String place, String begin);

	public void removeDtimeExam(String dtimeOid);

	public void saveDtimeExam(DtimeExam dtimeExam);

	public List getDtimeOidForNewDtimeClass(String departClass, String cscode,
			String sterm);

	/**
	 * 定義學生選課加選時之Service方法
	 *
	 * @param seld tw.edu.chit.model.Seld object
	 * @param std tw.edu.chit.model.Student object
	 * @param term 學期
	 * @param checkConflict 是否檢查衝堂?
	 * @throw SeldException 發生選課資料重複問題
	 */
	public void txAddSelectedSeld(Seld seld, Student std, String term,
			boolean checkConflict) throws SeldException;
	
	/**
	 * 負責儲存加選時衝堂相關資訊
	 * 
	 * @param seld tw.edu.chit.model.Seld object
	 * @param member tw.edu.chit.model.Member object
	 * @param student tw.edu.chit.model.Student object
	 * @param term Term
	 */
	public void txAddSeldConflictInfo(Seld seld, Member member,
			Student student, String term);

	/**
	 * 處理單一學生選課加選時之方法, 包括新增Seld資料表,更新Dtime資料表選課人數
	 *
	 * @commend 會一併新增一筆Adcd
	 * @commend 會一併新增一筆Regs
	 * @commend 不考慮跨選問題
	 * @see CourseManagerImpl.txBatchAddSelectedSeld()
	 * @param seld tw.edu.chit.model.Seld object
	 * @param student tw.edu.chit.model.Student object
	 * @param term 學期
	 * @param isStudent 是否為學生
	 * @param phase 梯次
	 * @throws SeldException 發生選課資料重複或不可以被跨選時
	 */
	public void txAddSelectedSeldForOneStudent(Seld seld, Student student,
			String term, boolean isStudent, int phase) throws SeldException;

	/**
	 * 定義批次學生選課加選時之Service方法
	 *
	 * @param selds List of Seld objects
	 * @param term 學期
	 * @return 衝堂學生清單
	 * @throws SeldException 發生選課資料重複或不可以被跨選時
	 */
	public String txBatchAddSelectedSeld(List<Seld> selds, String term)
			throws SeldException;

	/**
	 * 定義學生選課退選時之Service方法
	 *
	 * @param stdNo 學生學號
	 * @param classNo 班級代碼
	 * @param pahse Phase
	 * @param seldOids 前端選取之Seld資料表Oid列表
	 * @param std 是否為學生?
	 * @exception SeldException 選課人數超過上限時
	 */
	public void txRemoveSelectedSeld(String stdNo, String classNo, int phase,
			String seldOids, boolean std) throws SeldException;

	/**
	 * A non-transactional version of txRemoveSelectedSeld()
	 * 嗨!!! 魏組長
	 * Hi!!! Oscar! IF YOU MODIFIED txRemoveSelectedSeld() PLEASE DO THE SAME THING TO THIS METHOD
	 *
	 * @param stdNo
	 * @param classNo
	 * @param seldOids
	 * @param isStudent TODO
	 */
	public void removeSelectedSeld(String stdNo, String classNo, String seldOids, boolean isStudent);

	/**
	 * 以dtimeOids清單取得先前已新增且Adddraw為A或D之Adcd
	 *
	 * @param studentNo 學生學號
	 * @param classNo 班級代碼
	 * @param dtimeOids Dtime Oids
	 * @param type Adddraw Type
	 * @return java.util.List List of Adcd objects
	 */
	public List<Adcd> findExistedAdcd(String studentNo, String classNo,
			String dtimeOids, String type);

	public List getSeldForDeleteDtime(String[] dtimeOids);

	/**
	 * 定義以cscode取得Csno物件之Service方法
	 *
	 * @param cscode 科目代碼
	 * @return Csno tw.edu.chit.model.Csno object
	 */
	public Csno findCourseInfoByCscode(String cscode);

	/**
	 * 定義依據科目代碼與班級代碼查詢開課基本資料之Service方法
	 *
	 * @param classNo 班級代碼
	 * @param csCode 科目代碼
	 * @param sterm 學期
	 * @return Dtime seld tw.edu.chit.model.Dtime object
	 * @exception NoClassDefinedException 當查無開課紀錄時
	 */
	public Dtime findDtimeInfoByCsCodeAndClassNo(String classNo, String csCode,
			String sterm) throws NoClassDefinedException;

	public int countDtimeClassBy(String dtimeOid);

	public List getDtimeTeacherBy(String dtimeOid);
	
	public List<DtimeClass> findDtimeClassInfo(Seld seld);

	/**
	 * 定義以學生班級資料物件查詢開課資料之Service方法
	 *
	 * @param student tw.edu.chit.model.Clazz object
	 * @param dtimeOid Dtime Oid
	 * @return java.util.List List of Object
	 */
	public List<Object[]> getOpencsByStudentClass(Clazz clazz, Integer dtimeOid);

	public List getOpencs(String dtimeOid);

	/**
	 * 定義以依據老師取得開課主檔資料之Service方法
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
	public List<Object[]> findDtimeTeacherByTeacher(Member member);
	
	/**
	 * 實作以依據老師取得開課主檔資料之Service方法
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
			Integer term, String departClass, String cscode);

	/**
	 * 定義更新Dtime資料表內Open欄位之Service方法
	 *
	 * @param dtimeList List of Map objects
	 * @param open Dtime資料表欄位open值
	 */
	public void txUpdateOpenByDtimeList(List<Map> dtimeList, Byte open);

	/**
	 * 定義建立某班學生Dtime選課資料之Service方法
	 *
	 * @param dtime java.util.Map object
	 * @param stdList List of Student objects
	 */
	public void txCreateBaseSelectedForStudents(Map dtime,
			List<Student> stdList);

	/**
	 * 實作產生一個學生基本選課資料之Service方法
	 * Hi Oscar, this method is analog to your counterpart -- txCreateBaseSelectedForStudents,
	 * but only apply to one student, in respond to handle 轉學生
	 *
	 * For each Dtime-not-open-for-select:
	 * If the corresponding Seld is not existing, create one and delete associate Adcd.
	 * If the corresponding Regs is not existing, create one.
	 * Also, update select_count of Dtime
	 *
	 * @param students List of Student object
	 * @author James Chiang
	 */
	public void txCreateBaseSelectedForStudent(Student student, String term);

	/**
	 * 定義刪除某班學生Dtime選課資料之Service方法
	 *
	 * @param dtime java.uti.Map object
	 * @param stdList List of Student objects
	 */
	public void txDeleteBaseSelectedForStudents(Map dtime,
			List<Student> stdList);

	public void RemoveDtimeTeacherBy(String dtimeOid);

	public void SaveDtimeTeacher(DtimeTeacher dtimeTeacher);

	public void RemoveOpencsBy(String dtimeOid);

	public void saveOpencsBy(Opencs Opencs);

	public void RemoveDtimeExamBy(String ditmeOid);

	/**
	 * Get Dtime table listing for OpenCourse search(1) bar.
	 *
	 * @param classes Array of Clazz objects
	 * @param campusInCharge2 校區代碼
	 * @param schoolInCharge2 學制代碼
	 * @param deptInCharge2 科系代碼
	 * @param departClass 班級代碼
	 * @param term 學期
	 * @param opt 選別
	 * @return java.util.List List of Object array
	 */
	public List<Map> getDtimeForOpenCs(Clazz[] classes,
			String campusInCharge2, String schoolInCharge2,
			String deptInCharge2, String departClass, String term, String opt, Byte open);
	/**
	 * 定義刪除課程之前顯示選課記錄的方法
	 * @param dtimeOid
	 * @return
	 */
	public List getSeldBy(String dtimeOid[]);

	/**
	 * 定義刪除課程時同時刪除選課記錄的方法
	 * @param dtimeOid
	 * @return
	 */
	public void RevoveSeldBy(String dtimeOid[]);
	/**
	 * 定義刪除課程的方法
	 */
	public void removeDtimeBy(String dtimeOid);

	/**
	 * 實作新增教師任教中英文課程簡介之Service方法
	 *
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @param departClass Depart Class
	 * @param cscode Cscode
	 * @param engName Course English Name
	 * @param chiIntro 中文課程簡介
	 * @param engIntro 英文課程簡介
	 */
	public void txAddCourseIntro(Integer dtimeOid, Integer year, Integer term,
			String departClass, String cscode, String engName, String chiIntro,
			String engIntro);

	/**
	 * 實作新增教師任教建議書單之Service方法
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @param departClass Depart Class
	 * @param cscode Cscode
	 * @param bookSuggest 建議書單
	 */
	public void txAddCourseBookSuggest(Integer dtimeOid, Integer year,
			Integer term, String departClass, String cscode, String bookSuggest);

	/**
	 * 定義刪除課程之前顯示授課記錄的方法
	 */
	public List getTeachersBy(String dtimeOid[]);

	/**
	 * 定義更新CourseIntroduction資料表
	 *
	 * @param ci tw.edu.chit.model.CourseIntroduction object
	 */
	public void txUpdateCourseIntro(CourseIntroduction ci);

	/**
	 * 定義以Dtime Oid及學年與學期取得CourseIntroduction清單資料之Service方法
	 * 
	 * @param dtimeOid Oid
	 * @param year School Year
	 * @param term School Term
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public List<CourseIntroduction> getCourseIntroByDtimeOid(Integer dtimeOid,
			Integer year, Integer term);

	/**
	 * 定義以Oid取得CourseIntroduction清單資料
	 *
	 * @param oid CourseIntroduction Oid
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public CourseIntroduction getCourseIntroByOid(Integer oid);

	/**
	 * 定義新增/更新CourseIntroduction清單資料之Service方法
	 *
	 * @param regs tw.edu.chit.model.Regs object
	 */
	public void txSaveRegs(Regs regs);

	/**
	 * 定義刪除課程時同時刪除加選記錄的方法
	 */
	public void removeAdcdBy(String dtimeOid);

	/**
	 * 定義刪除課程時同時刪除已存成績記錄的方法
	 */
	public void removeRegsBy(String dtimeOid);

	/**
	 * 定義由姓名或id查詢員工的方法
	 */
	public List getEmplBy(String idno, String cname);
	
	/**
	 * 實作以Dtime Oid及學年與學期取得CourseIntroduction清單資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public CourseIntroduction getCourseIntrosByDtimeOid(Integer dtimeOid,
			Integer year, Integer term);
	
	/**
	 * 實作以CourseIntroduction取得CourseIntroduction資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public CourseIntroduction getCourseIntroBy(CourseIntroduction ci);

	/**
	 * 定義以Dtime Oid及學年與學期取得CourseSyllabus清單資料之Service方法
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus getCourseSyllabusByDtimeOid(Integer dtimeOid,
			Integer year, Integer term);
	
	/**
	 * 實作以Dtime Oid及學年與學期取得CourseSyllabus清單資料
	 * 
	 * @param departClass Depart Class
	 * @param cscode Cscode
	 * @param year School Year
	 * @param term School Term
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus getCourseSyllabusBy(String departClass,
			String cscode, Integer year, Integer term);
	
	/**
	 * 實作以Oid取得CourseSyllabus清單資料
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
	 * 定義以Oid取得CourseSyllabus清單資料
	 *
	 * @param oid CourseSyllabus Oid
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus findCourseSyllabusByOid(Integer oid);
	
	/**
	 * 以findCourseSyllabusByOid取得資料
	 *
	 * @param courseSyllabus CourseSyllabus Object
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus findCourseSyllabusBy(CourseSyllabus courseSyllabus);
	
	/**
	 * 以CourseSyllabus取得資料
	 * 
	 * @param courseSyllabus CourseSyllabus Object
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus findCourseSyllabusBy1(CourseSyllabus courseSyllabus);

	/**
	 * 定義新增教師任教課程大綱之Service方法
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @param departClass Depart Class
	 * @param cscode Cscode
	 * @param officeHour 輔導時間
	 * @param requisites 先修科目或先備能力
	 * @param objectives 課程目標
	 * @param syllabus 教學大綱
	 * @param topics 章節主題
	 * @param contents 內容綱要
	 * @param hours 教學時數
	 * @param weekNos 週次
	 * @param remarks 備註
	 */
	public void txAddCourseSyllabus(Integer dtimeOid, Integer year,
			Integer term, String departClass, String cscode, String officeHour,
			String requisites, String objectives, String syllabus,
			String[] topics, String[] contents, String[] hours,
			String[] weekNos, String[] remarks);

	/**
	 * 定義更新CourseSyllabus資料表
	 *
	 * @param oid CourseSyllabus Oid
	 * @param year School Year
	 * @param term School Term
	 * @param departClass Depart Class
	 * @param cscode Cscode
	 * @param officeHour 輔導時間
	 * @param requisites 先修科目或先備能力
	 * @param objectives 課程目標
	 * @param syllabus 教學大綱
	 * @param topics 章節主題
	 * @param contents 內容綱要
	 * @param hours 教學時數
	 * @param weekNos 週次
	 * @param remarks 備註
	 */
	public void txUpdateCourseSyllabus(Integer oid, Integer year, Integer term,
			String departClass, String cscode, String officeHour,
			String requisites, String objectives, String syllabus,
			String[] topics, String[] contents, String[] hours,
			String[] weekNos, String[] remarks);

	/**
	 * 定義以發送人查詢訊息的方法
	 */
	public List getMessageBy(String sender);

	/**
	 * 定義儲存訊息的方法
	 */
	public void saveMessageBy(Message message);

	/**
	 * 定義刪除訊息的方法
	 */
	public void removeMessageBy(String oid);

	public List getDtimeForOpenCsAll(Clazz[] classes, String cscode,
			String techid, String sterm, String choseType);

	public List getDtimeForOpenCs(Clazz[] classes, String campusInCharge2,
			String schoolInCharge, String deptInCharge2, String departClass,
			String cscode, String techid, String term, String choseType);

	/**
	 * search for openCourse 'All'
	 *
	 * @param classes Array of Clazz objects
	 * @param term 學期
	 * @param opt 選別
	 * @param open 開放與否
	 * @return java.util.List List of Map objects
	 */
	public List<Map> getDtimeForOpenCsAll(Clazz[] classes, String term,
			String opt, Byte open);

	/**
	 * 定義取得使用者登入資訊
	 */
	public List getWwpassBy(String username);

	/**
	 * 定義更改使用者密碼
	 */
	public void updateWwpass(WwPass wwpass);

	/**
	 * 定義取得教師上課時間資料
	 *
	 * @param member tw.edu.chit.model.Member object
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> findTeachInfoByMember(Member member);

	public boolean getOpencSize(String dtimeOId);

	/**
	 *
	 * @param dtimeOid
	 * @param selectLimit
	 */
	public void txUpdateDtimeSelLimit(String dtimeOid, Short selectLimit);

	/**
	 * 以查詢介面取得Dtime
	 * @return List of Map
	 */
	public List getDtimeBy(Clazz[] classes, String cscode, String techid, String term, String choseType, String open, String elearning, String classLess, String chi_name, String cname);
	/**
	 * 以勾選介面取得Dtime
	 * @return List of Map
	 */
	public List getDtimeBy(String oid);

	/**
	 * 定義以班級代碼與學期查詢班級課表之Service方法
	 *
	 * @param classNo 班級代碼
	 * @param term 學期
	 * @return java.util.List List of Object arrays
	 */
	public List findCourseByClassNoAndTerm(String classNo, String term);

	/**
	 * 定義以教師ID與學期查詢班級課表
	 *
	 * @param idno 教師ID
	 * @param term 學期
	 * @return java.util.List List of Map objects
	 */
	public List<Map> findCourseByTeacherTermWeekdaySched(String idno, String term);

	public List checkReopenClass(String departClass, String week, String beginOrEnd, String sterm, String dtimeOid);

	public List getBuilding();

	public List getRoom(String roomid, String no1);

	public void saveNabbr(Nabbr nabbr);
	
	/**
	 * 儲存StdLoan物件
	 * 
	 * @param stdLoan
	 */
	public void saveStdLoan(StdLoan stdLoan);

	/**
	 * 定義以Class No查詢部制代碼之Service方法
	 *
	 * @param classNo 班級代碼
	 * @return 部制代碼,D為日間,N為夜間,H為學院
	 */
	public String findSchoolTypeByClassNo(String classNo);

	/**
	 * 定義以Dtime Oid查詢選課學生清單之Service方法
	 *
	 * @param dtimeOid Dtime Oid
	 * @return java.util.List List of Student objects
	 */
	public List<Student> findSeldStudentByDtimeOid(Integer dtimeOid);

	/**
	 * 檢查教室名稱重複
	 * @param roomId
	 * @return
	 */

	public List checkReRoom(String roomId);

	/**
	 * 定義以班級代碼取得學生
	 * @param departClass
	 * @return
	 *
	public List getStmdBy(String departClass);

	/**
	 * 定義以學號取得選課清單
	 * @param studentNo
	 * @return
	 *
	public List getSeldBy(String studentNo, String sterm);

	*/

	/**
	 * 定義檢查同時段重複選課
	 * @param departClass
	 * @return
	 */
	public List checkReSeld(String departClass, String stmd);

	/**
	 * 依校區,學制,課程名稱查詢所開之課程
	 *
	 * @param campus 校區
	 * @param school 學制
	 * @param courseName 課程名稱
	 * @return List of course information
	 */
	public List findCourseByName(String campus, String school, String courseName);

	/**
	 * 依校區,學制,星期,節次查詢所開之課程
	 *
	 * @param campus
	 * @param school
	 * @param fweek
	 * @param period
	 * @return List of course information
	 */
	public List findCourseByWeekTime(String campus, String school, String fweek, String period);

	public List getTeacherBy(String cname);

	/**
	 * 以班級代碼取得校區代碼之Service方法
	 *
	 * @param classNo 班級代碼
	 * @return 校區代碼
	 */
	public String findCampusNoByClassNo(String classNo);

	/**
	 * 以Dtime Oid取得Seld目前選課人數之Service方法
	 *
	 * @param dtimeOid Dtime Oid
	 * @return int 目前選課人數
	 */
	public int findSeldCountByDtimeOid(Integer dtimeOid);

	/**
	 * 以Dtime Oid取得加退選紀錄之Service方法
	 *
	 * @param dtimeOid Dtime Oid
	 * @return java.util.List List of Adcd objects
	 */
	public List<Adcd> findAdcdByDtimeOid(Integer dtimeOid);

	/**
	 * 以學生學號取得加退選紀錄之Service方法
	 *
	 * @param studentNo 學生學號
	 * @param term 學期
	 * @return java.util.List List of Adcd objects
	 */
	public List<Adcd> findAdcdByStudentNo(String studentNo, String term);

	/**
	 * 以學生學號取得加退選紀錄之Service方法
	 *
	 * @param studentNo 學生學號
	 * @param term 學期
	 * @return java.util.List List of Adcd objects
	 */
	public List<StdAdcd> findStudentAdcdByStudentNo(String studentNo,
			String term);

	public Dtime findDtimeBy(Integer oid);

	/**
	 * 以學生學號與學期查詢選課資料之Service方法
	 *
	 * @param studentNo 學生學號
	 * @param term 學期
	 * @return java.util.List List of objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findStudentSeldCourse(String studentNo, String term);
	
	/**
	 * 以學生學號與學期查詢選課資料之Service方法
	 *
	 * @param studentNo 學生學號
	 * @param term 學期
	 * @return java.util.List List of objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findStudentSeldCourse1(String studentNo, String term);

	/**
	 * 定義以中文名稱取得課程代碼提供即時查詢
	 */
	@SuppressWarnings("unchecked")
	public List getCsnoBy(String chiName);

	/**
	 * 定義查詢開課選修資料
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findOpencsByCondition(String campus, String school,
			String dept, String grade, String clazz, String term);
	
	/**
	 * 查詢開課選修資料
	 * 
	 * @commend 第二,三階段無跨選規則
	 * @param campusNo Campus
	 * @param schoolNo School
	 * @param deptNo Department
	 * @param grade Grade
	 * @param classNo Student Class No
	 * @param term Term
	 * @return java.util.List List of Map array
	 */
	public List<Map> findOpencsByCondition4Phase2And3(String campusNo,
			String schoolNo, String deptNo, String grade, String classNo,
			String term);

	/**
	 * Delete and re-generate a student's AddDelCouseData based on his Seld
	 */
	public void txRegenerateAdcdByStudentTerm(Student student, String term);

	/**
	 * 定義取得學生本學年成績
	 * @return
	 */
	public List getBothStermSeldBy(String studentNo, String sterm, String studentClass);

	/**
	 * Delete and re-generate all students' AddDelCouseData based on their individual Seld within a class
	 *
	 */
	public void txRegenerateAdcdByClassTerm(String classNo, String term);

	/**
	 * 定義以學號取得學生共讀幾年,有哪幾年
	 */
	public List getSchoolYearListBy(String studentNo);

	/**
	 * 定義取得學生學年學期成績
	 */
	public List getScoreHistBy(String studentNo, String schoolYear);

	/**
	 * 以學生學號與學期查詢選課資料之Service方法
	 *
	 * @param studentNo 學生學號
	 * @param term 學期
	 * @return java.util.List List of objects
	 */
	public int findMinCountBySchoolNo(String schoolNo);

	/**
	 * 定義以學生與學期查詢班級課表
	 *
	 * @param student tw.edu.chit.model.Student object
	 * @param term 學期
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findCourseByStudentTermWeekdaySched(Student student,
			String term);

	/**
	 * 定義以學生與學期查詢所屬班會與系時間
	 *
	 * @param student tw.edu.chit.model.Student object
	 * @param term 學期
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findT0001And50000ByStudent(Student student, String term);
	
	/**
	 * 取得特定時間之班會與系時間作衝堂檢查
	 * 
	 * @param student tw.edu.chit.model.Student object
	 * @param term 學期
	 * @param week 星期
	 * @param node 節次
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findT0001And50000ByStudentAndWeek(Student student,
			String term, Integer week, String node);

	@SuppressWarnings("unchecked")
	public List<Map> findCourseOverlay(Student student, String term,
			Integer week, String node);

	/**
	 * 定義取得學生本學期書單
	 */
	public List getMyBookBy(String studentNo, String year, String sterm);

	public List getCsnameBy(String cscode);

	/**
	 * 以部制代碼與學期查詢通識課程資訊之Service方法
	 *
	 * @param schoolNo 部制代碼
	 * @param term 學期
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findLiteracyClass(String schoolNo, String term);

	/**
	 * 以班級代碼與學期查詢開放選修課程資訊之Service方法
	 *
	 * @param departClass 班級代碼(只包括校區+部制代碼)
	 * @param term 學期
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findOpenCourse(String departClass, String term);

	/**
	 * 定義課程大綱列表及比例取得
	 */
	public List getCheckGist(String departClass, String sterm);

	/**
	 * 定義教師排課批次查核
	 */
	public List getReOpTechBy(String departClass, String sterm);

	/**
	 * 定義簡易取得選課結果
	 */
	public List EzGetStuSel(String sterm, String departClass, String cscode);

	/**
	 * 以category查詢科系資料之Service方法
	 *
	 * @param category 分類
	 * @return tw.edu.chit.model.DepartmentInfo objects
	 */
	public DepartmentInfo findDepartmentInfoByCategory(String category);

	public List CountDepartClass(String departClass, String grade);

	public List CountDepartClass();

	public List getCheckCredit(String departClass, String sterm, String minimum);

	public List getExamDate();

	public List getSeldBy(String dtimeOid);

	/**
	 * 定義取得報部系所代碼
	 */
	public List getPecode9();

	/**
	 * 定義取得報部學制代碼
	 */
	public List getPecode11();

	public List CountStudentBy(String departClass);

	public List getJustBy(String departClass, String studentNo, String studentName, String docNo, String begin, String end);

	/**
	 * 定義取得最近一次成績轉檔學年學期
	 */
	public Map getFiledRecord();

	/**
	 * 最得某生的上一學期成績
	 */
	public List getFiledStermSeldBy(String studentNo, String school_year, String school_term, String studentClass);

	/**
	 * 取得學生當學年/歷年成績資料
	 *
	 * @param studentNo 學號
	 * @param year 學年
	 * @param term 學期
	 * @return java.util.List 歷年成績
	 */
	public List getScoreHistBy(String studentNo, String year, String term);

	/**
	 * 查選爆的課
	 */
	public List getOverSelect(String departClass, String term);

	/**
	 * 批次開課用的查詢方法
	 */
	public List getDtimForBatch(Clazz[] classes, String cscode,
			String techid, String term,String choseType, String open, String elearning, String classLess);

	/**
	 * 查詢開課主檔
	 * 無使用者權限
	 */
	public List getDtimeBy(String cscode,
			String techid, String term,String choseType, String open, String elearning, String classLess, String chi_name, String cname);

	/**
	 * 取得選課人數
	 */
	public Integer countSelect(String DtimeOid);

	public List getDtimeByHql(String oid);

	/**
	 * 取得開放成績查詢
	 * @return
	 */
	//TODO 將設計正規格式的資料表
	public Map getOpenViewTimeForScore(String schoolId, String type);

	/**
	 * 取得未做的期中教學評量
	 */
	public List getCoAnswBy(String studentNo, String DtimeOid, String Sterm);

	public List getQuestionBy(String type);

	public void saveCoansw(Coansw coansw);
	
	public void getTutorQuestion(HttpSession session, UserCredential user);

	/**
	 * sql 查詢字串
	 */
	public List ezGetBy(String sql);

	public void doFilter(List chair, String dtimeOid, String type);

	public List getCouFilter(Clazz[] classes, String cscode, String techid,
			String term, String choseType, String open, String elearning, String classLess);

	/**
	 * 以勾選介面取得篩選後的課程
	 */
	public List SeldCouFilter(String oid);

	public List getSeldStuFilterBy(String dtimeOid[], boolean b);

	/**
	 * 處理非篩選作業刪Dtime
	 */
	public void removeDtime(String dtimeOid);

	/**
	 * 篩選復原
	 */
	public void rollbackDtime(String dtimeOid);

	/**
	 * 取得教師任教課程
	 */
	public List getDtimeBy(String techId, String sterm);

	/**
	 * 取得題目
	 * (申論題)
	 */
	public Map getQuestColumn(String type);

	public List getQuestCount(String type);

	/**
	 * 取得某科的所有問卷
	 */
	public List getCoanswBy(String DtimeOid);

	/**
	 * 取得答案區間
	 */
	public List getRat();

	/**
	 * 取得教師的所有問卷
	 */
	public List getCoansw4TechBy(String techid);

	/**
	 * 取得任教的體育課程
	 */
	public List getSport(String techid, String sterm);

	/**
	 * 體育課程學生
	 */
	public List myStudents(List myDtime);

	public void updateObject(Object obj);

	public List getSeld4SportBy(String dtimeOid);

	public Map getdtimeBy(String dtimeOid);

	public List getFiltDep(List list);

	public List getFiltStu(String departClass);

	public Integer countCoansw(String dtimeOid);
	
	/**
	 * 定義教務處列印(部制)教學評量報表
	 * @param sidnos
	 * @return
	 */
	public List getCoansw4Empl(String techid, String classNo, String term);
	
	/**
	 * 不及格人數統計表
	 */
	public List geTtriposBy(String departClass, String sterm);
	
	/**
	 * 暑修評分
	 * @param mySdtime
	 * @return
	 */
	public List mySunStudents(List mySdtime);
	
	/**
	 * 以學號查詢選課資料
	 * 
	 * @param studentNo 學號
	 * @param term 學期
	 * @return java.util.List List of Seld objects
	 */
	public List<Seld> findSeldByStudentNoAndTerm(String studentNo, String term);
	
	public void executeSql(String sql);
	
	/**
	 * 學生查詢
	 * @param type: stmd or Gstmd
	 * @param stForm DynaActionForm
	 * @return stmd or Gstmd List
	 */
	public List getStudentInfo(String type, Map stForm);
	
	/**
	 * 取得學籍異動歷史
	 * 舊資訊沒有occ_status, cause 新資訊以此2欄位取代remark, 要拿全部欄位並且全部顯示
	 * @param studentNo 學號
	 * @return 學生歷年異動
	 */
	public List getGmark(String studentNo);
	
	/**
	 * 現在學年
	 * @return
	 */
	public Integer getSchoolYear();
	
	/**
	 * 現在學期
	 * @return
	 */
	public Integer getSchoolTerm();
	
	/**
	 * 新增加退選紀錄
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param studentNo Student No
	 * @param idno Employee IDNO
	 */
	public void txSaveAdcdHistory(Integer dtimeOid, String studentNo,
			String idno, String adddraw);
	
	public Map ezGetMap(String sql);
	
	public String ezGetString(String sql);
	
	public String convertDate (String someday);
	
	public Integer ezGetInt(String sql);
	
	public void setCoansFoRm(Date now, HttpSession session, 
			UserCredential user, Date coanswStart, Date coanswEnd);
	
	public List getCoans(List DtimeOid, String elearning);
	
	public float roundOff(float f, int n);
	
	/**
	 * 查詢Dtime物件
	 * 
	 * @param d tw.edu.chit.model.Dtime Object
	 * @param orderField Order Field
	 * @return java.util.List List of tw.edu.chit.model.Dtime Objects
	 */
	public List<Dtime> findDtimeBy(Dtime d, String orderField);
	
	/**
	 * 查詢Dtime物件
	 * 
	 * @param d tw.edu.chit.model.Dtime Object
	 * @param orderField Order Field
	 * @return java.util.List List of tw.edu.chit.model.Dtime Objects
	 */
	public List<Object> findDtimeCsnoBy(Dtime d, String orderField);
	
	public List checkReopenClass(String departClass, String week[], String begin[], 
								 String end[], String sterm, String dtimeOid);
	
	public List checkReOpenRoom(String place[], String week[], String begin[],
			 					String end[], String sterm, String dtimeOid);
	
	public List checkEmplReOpen(String techid, String week[], String begin[],
			 String end[], String sterm, String dtimeOid);
	
	/**
	 * 取某部制的班
	 * @param schoolType
	 * @return
	 */
	public List getDepartClassByType(String schoolType);
	
	public List getAllSchoolType();
	
	public List getCoansw4Empl(List departClasses, String term);
	
	/**
	 * 查詢Nabbr物件
	 * 
	 * @param d tw.edu.chit.model.Nabbr Object
	 * @return tw.edu.chit.model.Nabbr Object
	 */
	public Nabbr findNabbrBy(Nabbr n);
	
	public String getClassInfo(String departClass, char type);
	
	public String numtochinese(String input, boolean sim);
	
	public int getArandom(int length);
	
	public boolean getExtrapay4Adcd(String studentNO, String sterm, char type);
	
	public boolean getExtrapay4Seld(String studentNO, String sterm);
	
	public List getStudyPath(String ClassNo, int SchoolYear);
	
	public List getSaveDtime4One(String ClassNo, List classes, int SchoolYear, boolean filed);
	
	public List getMyBadIdea(List allDtimes, String classNo, String studentNo, String schoolYear, String schoolTerm, boolean filed);
	
	public List getEmpl(Map map, String table, String type);
	
	public void removeObj(Object po);
	
	public boolean checkIdno(String idno);
	
	public void saveRcact(Rcact rcact);
	
	public boolean syncEmpl(Empl empl, String mode);
	
	public boolean syncDEmpl(DEmpl empl, String mode);
	
	public boolean syncTest();
	
	public String getLocalHost();
	
	public Savedtime findSavedtimeBy(Integer oid);
	
	public boolean testOnlineServer();
	
	public List getPageList(String type);
	
	public List getContract(Map form);
	
	public List getNewContract(Map form);
	
	public Map getADorCD(Map map);
	
	public EmplSave getEmplSave(Map map, boolean newEmplSave);
	
	public Date getADtoAD(String ad);
	
	public Date getROCYtoAD(String rocy);
	
	/**
	 * 實做刪除教師研究成果資料
	 */
	public void removeRcTableBy(String Table, String RcactOid);
	
	/**
	 * 查詢Gmark物件
	 * 
	 * @param gmark
	 * @return
	 */
	public List<Gmark> findGMarkBy(Gmark gmark);
	
	/**
	 * 查詢StdLoan物件
	 * 
	 * @param stdLoan 
	 * @return List of StdLoan Objects
	 */
	public List<StdLoan> findStdLoanBy(StdLoan stdLoan);
	
	public List<Dtime> findDtimesBy(Dtime dtime);
	
	public List<LiteracyType> findLiteracyTypesBy(LiteracyType literacyType)
			throws DataAccessException;
	
	public List findSQLWithCriteria(Class entity,
			SimpleExpression... expression) throws DataAccessException;
	
	public int getPassScore(String classNo, String studentNo);
	
	public void saveMail(String real_from, String send_from, String subject, InternetAddress[] addressr, String content, Date send_date, 
			String real_address);
	
	public boolean sendMail(String username, String password, String smtpServer, String dsplEmail, String dsplName, Date dsplDate, 
			String subject, String content, InternetAddress address[], FileDataSource files[]);
	
	public boolean sendMailSimple(String smtp_account, String smtp_password, String smtp_server, 
			String from_email, String from_name, String subject, String content, String members, FileDataSource files[]);
	
	public boolean isValidEmail(String email);
	
	public List getEmpl(String student_no);
	
	public List getMyOnlineWork(String idno);
	
	public Map getCISMailServerInfo();
	
	public Menu putPrivateMenu(String idno, Menu menu);
	
	public Menu putUniteMenu(String priority, Menu menu);
	
	public String getSchInfo(String student_no);
	
	public List checkDelStd(List students);
	
	public void checkNewPortfolio(String Uid, String siteName, String siteDescript, String path);
	
	public List sortListByKey(List list, String key);
	
	public String myPortfolioUrl(String Uid);
	
	public String myPortfolioFtp();
	
	public String myPortfolioFtpClient();
	
	public String FTPServerPath();
	
	public String getUid(UserCredential c);
	
	public boolean portfolioExist(String Uid);
	
	public List CsGroup4One(String student_no, Map aStudent, boolean staff);
	
	public boolean SSHChangeMailPassword4One(String host, String account, String password, String newAccount, String newPassword, String ChAccount, String ChPassword);
	
	public void clearPo(Object po);
	
	public List getCs4Std(String student_no, String Sterm);
	
	public Map getEmplOrDempl(String idno);
	
	public boolean uploadImage2APServer(FormFile file, String baseDir, String idno);
	
	public boolean uploadImage2FTPServer(String baseDir, String idno);
	
	public boolean uploadFileFTPServer(FormFile uplFile, String baseDir, String idno, String FN_Name, String ftpDir);  //會議/法規資料
	
	public boolean DownloadFileFTPServer(String fileName, String DataName, String baseDir);   //會議/法規資料下載
	
	public int getCreditPa(String StudentNo);
	
	//public Map StdLeave(String studentNo, String occur_graduate_no, String status, String schoolYear, String schoolTerm, Calendar c);
	
	//public Student StdChange(String studentNo, String occur_graduate_no, String status, String schoolYear, String schoolTerm, Calendar c);
	
	String ChWeekOfDay(int w, String front);
	
	public List changeListDate(List list, String key[]);
	
	public Map getADiplomaInfo(String student_no);

	public boolean validateEmail(String address);
	
	public Menu putPriorityUniteMenu(String priority, Menu menu);
	
	public List getSelfCalendar(Date start, Date end, String account)throws ParseException;
	
	public FileDataSource FormFile2FileDataSource(FormFile file);
	
	public String WeekOfDay(int w);
	
	public void saveNewCalendar(String account, PubCalendar c);
	
	public Map createPubCalendar(String members, String account, String beginDate, String beginTime, String place, String name, String note, PubCalendar c, FormFile addFile);
	
	public Map deleteCalendar(String members, String account, String date, String time, String place, String name, String note);
	
	public List checkReSelected(String classLess);
	
	public List checkReSelectedNow(String classLess, String year, String term);
	
	public boolean checkPassUpdate(String username);
	
	public int sumDtimeReserve(String Oid, String idno, String year, String term);
	
	public int sumEmplLimit(String idno);
	
	public boolean checkDtimeReserve(String Oid, String idno, String year, String term, float hours, float credit, int selimit, String type);
	
	public List checkRoom(String place, String week, String beginOrEnd, String sterm, String reserveOid, String year, String term);
	
	public List checkTeacher(String techid, String week, String beginOrEnd, String sterm, String reserveOid, String year, String term);
	
	public List checkClass(String techid, String week, String beginOrEnd, String sterm, String reserveOid, String year, String term);
	
	List getMyCs(String studentNo, String term);
	
	public String getElearningName(String elearning);
	
	public String getWeekChar(int index);
	
	public List checkReSelds(String studentNo, String DtimeOid, String week, String begin, String end, String sterm);
	
	public void saveWwpass(String username, String password, String priority);
	
	public Map getStdCredit(String student_no);
	
	//public String[] parseIntr(String str);
	
	//public String[] parseSyl(String str);
	
	public Map parseIntr(String str);
	
	public Map parseSyl(String str);
	
	public List parseSyls(String str);
	
	public String replaceChar4XML(String str);
	
	public String getEntrNo(String student_no);
	
	public List getDtime(String techid, String sterm);
	
	public void SetAqForm(HttpSession session, HttpServletRequest request);
	
	public List getDtimeByAssistant(String techid, String sterm);
	
	public Map analyseEmpl(String members);
	
	public List analyseEmplToList(String members);
	
	public List sortListByKeyDESC(List list, final String key);
}
