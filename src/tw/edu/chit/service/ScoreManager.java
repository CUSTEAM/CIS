package tw.edu.chit.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.ClassScoreSummary;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.FailStudents1;
import tw.edu.chit.model.FailStudents2;
import tw.edu.chit.model.FailStudentsHist;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.MasterData;
import tw.edu.chit.model.MidtermExcluded;
import tw.edu.chit.model.Optime1;
import tw.edu.chit.model.Regs;
import tw.edu.chit.model.Regstime;
import tw.edu.chit.model.Rrate;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Stavg;
import tw.edu.chit.model.StdScore;
import tw.edu.chit.model.Student;

public interface ScoreManager {
    public List<ScoreHist> findScoreHistByStudentInfoForm(Map formProperties, String classInCharge);
    public List<Stavg> findScoreHistAvgByStudentInfoForm(Map formProperties,	String classInCharge);
    public List<Student> findStudentsByStudentInfoForm(Map formProperties, String classInCharge);
    public List<Graduate> findGraduateByHistInfoForm(Map formProperties, String classInCharge);

    /**
	 * 以學號查詢在學學生
	 * 
	 * @param String studentNo
	 * 
	 * @return Student or null
	 */
    public Student findStudentByStudentNo(String studentNo);
    
    public Student findStudentByStudentNoInCharge(String studentNo, String classInCharge);
    public Graduate findGraduateByStudentNo(String studentNo);
    public String findCourseName(String cscode);
    public String findClassName(String clazz);
    public ActionMessages createScoreHist(Map formProperties, String classInCharge);
    public ScoreHist modifyScoreHist(Map formProperties, ScoreHist score);
    public void validateScoreHistCreate(Map formProperties, ActionMessages errors, ResourceBundle bundle);
    public void validateScoreHistModify(Map formProperties, ScoreHist score, ActionMessages errors, ResourceBundle bundle);
    public List<ScoreHist> deleteScores(List<ScoreHist> scores, ResourceBundle bundle);
    public ActionMessages createScoreHistAvg(Map formProperties, String classInCharge);
    public Stavg modifyScoreHistAvg(Map formProperties, Stavg score);
    public void validateScoreHistAvgCreate(Map formProperties,
			ActionMessages errors, ResourceBundle bundle);
    public void validateScoreHistAvgModify(Map formProperties, Stavg score,
			ActionMessages errors, ResourceBundle bundle);
    public List<Stavg> deleteScoresAvg(List<Stavg> scores, ResourceBundle bundle);
    public ActionMessages recalAvgScore(String student_no, String school_year, String school_term);
	public List<Dtime> findCscodeByClass(String departClass);
	public List<Dtime> findDtimeByTeacher(String teacherId);	
	public List<Seld> findSeldScoreByInputForm(String departClass, String cscode);
	
	/**
	 * 以課程OID尋找選課學生成績資料Seld
	 * @param dtimeoid 課程OID
	 * @return 學生成績資料Seld
	 */
	public List<Seld> findSeldScoreByInputForm(String dtimeoid);
	
	//public List<Seld> findSeldByInputForm(String departClasss, String cscode);
	public List<Seld> findSeldsBy(Integer dtimeOid);
	public Seld findSeld(int oid, String studentNo);
	public List<Seld> findSeld(String studentNo, String term);
	public ActionMessages updateSeld(Seld seld, String scoretype, double[] score);
	
	/**
	 * 根據 學生選課檔 Seld 依(開課班級,課程代碼)修補教師上傳成績檔 Regs
	 * 
	 * @param selclass 開課班級
	 * @param cscode 課程代碼
	 * @return ActionMessages
	 */
	public ActionMessages patchSeldRegs(String selclass, String cscode);
	
	/**
	 * 根據 學生選課檔 Seld 修補學期教師上傳成績檔 Regs
	 * 
	 * @param sterm "1"或"2"
	 * @return ActionMessages
	 */
	public ActionMessages patchAllSeldRegs(String sterm);
	
	public List<Regs> findRegsScore(String departClass, String cscode);
	public ActionMessages updateScoreInput(Map formProperties, List<Seld> scoreslist);
	public ActionMessages updateRegsTime(int dtimeoid, String departClass, String cscode, String teacherId, String scoretype, String now);

	/**
	 * 取得老師上傳成績時間資料
	 * @param selclass
	 * @param cscode
	 * @param teacherId
	 * @param index , 1:期中,2:平時期末,3:學期
	 * @return List<Regstime>
	 */
	public List<Regstime> findRegstime(int dtimeoid, String selclass, String cscode, String teacherId, String index);
	
	public List<Rrate> findScoreRate(String departClass, String cscode);
	public List<Rrate> findScoreRate(int Oid);
	public Rrate createRrate(String selclass, String cscode);
	
	/**
	 * 建立成績比例
	 * @param dtimeoid 課程Oid
	 * @return 課程成績比例
	 */
	public Rrate createRrate(int dtimeoid);
	
	public void updateRrate(Rrate scorerate, float[] rrate);
	public List<Dtime> findDtimeTeacherByTeacher(String teacherId);	
	public ActionMessages updateTchScoreInput(Map formProperties, List<Regs> regs, Map calscore);
	
	/**
	 * 老師成績上傳更新成績
	 * 
	 * @param formProperties
	 * @param seld List of Seld
	 * @param calscore
	 * @return ActionMessages error if record count error!!!
	 */
	public ActionMessages updateScoreInputAll(Map formProperties, List<Seld> seld, Map calscore);
	
	public ActionMessages chkTchScoreUploadOpened(String scoretype, String selclass);
	
	/**
	 * 檢查該課程是否沒有期中成績
	 * 
	 * @param selclass 開課班級
	 * @param cscode 課程代碼
	 * @return true:沒有期中成績 , false:有期中成績
	 */
	public boolean chkNoMiddleScore(String selclass, String cscode);
	
	public void setRunStatus(String methodName, String step, int rcount, int total, double percentage, String isComplete);
	
	/**
	 * @param no_parameters_required
	 * 
	 * @return List<Map>
	 * @see ScoreManagerImpl.chkstatus, Maps' Attributes(methodName, step, percentage)
	 */
	public List chkstatus();
	
	/**
	 * 計算學生該學年學期之平均成績
	 * 
	 * @param String student_no
	 * @param String school_year
	 * @param String school_term
	 * 
	 * @return float[2]{avgscore, credit}
	 */
	public float[] calTermAvgScore(String studentNo, String school_year, String school_term);
	
	/**
	 * 新增或修改學生該學年學期之平均成績
	 * 
	 * @param String student_no
	 * @param String school_year
	 * @param String school_term
	 * @param String depart_class
	 * @param float score
	 * @param float credit
	 * 
	 * @return ActionMessages msgs
	 */
	public ActionMessages updateOrCreatAvgScore(String studentNo, String school_year,
			String school_term, String depart_class, float score, float credit);
	
	/**
	 * 重新計算並更新歷史平均成績
	 * 
	 * @param schoolYear
	 * @param schoolTerm
	 * @param clazzFilter
	 * @return ActionMessages
	 */
	public ActionMessages txUpdateAvgScore(String schoolYear, String schoolTerm, String clazzFilter);
	
	/**
	 * 將本學期之成績轉入歷史檔
	 */
	public ActionMessages txTermScore2ScoreHist(String schoolYear, String schoolTerm, String depart);
	
	/**
	 * 重新計算學業成績並更新MasterData
	 * 
	 * @param props
	 */
	public void updateMasterData(Map props);
	
	/**
	 * 查詢碩士成績基本資料
	 * 
	 * @param md
	 * @return
	 */
	public List<MasterData> findMasterDataBy(MasterData md);
	
	/**
	 * 定義以學號查詢碩士成績基本資料
	 * 
	 * @param studentNo 學號
	 * @return tw.edu.chit.model.MasterData object
	 */
	public MasterData findMasterByStudentNo(String studentNo);
	
	/**
	 * 定義以ID取得MasterData物件之法
	 * 
	 * @param oid identify
	 * @return tw.edu.chit.model.MasterData object
	 */
	public MasterData findMasterDataById(Integer oid);
	
	/**
	 * 定義新增MasterData物件之方法
	 * 
	 * @param md tw.edu.chit.model.MasterData object
	 */
	public void addMasterData(MasterData md);
	
	/**
	 * 定義更新MasterData物件之方法
	 * 
	 * @param md tw.edu.chit.model.MasterData object
	 */
	public void modifyMasterData(MasterData md);
	
	/**
	 * 定義刪除MasterData物件之方法
	 * 
	 * @param md tw.edu.chit.model.MasterData object
	 */
	public void deleteMasterData(MasterData md);
	
	/**
	 * 查詢Stavg物件
	 * 
	 * @param studentNo 學生學號
	 * @param year 學年
	 * @param term 學期
	 * @return tw.edu.chit.model.Stavg object
	 */
	public Stavg findStavgBy(String studentNo, String year, String term);
	
	/**
	 * 取得Stavg清單物件
	 * 
	 * @param departClass 學生班級
	 * @param year 學年
	 * @param term 學期
	 * @return java.util.List List of Stavg objects
	 */
	public List<Stavg> calStavgRank(String departClass, String year, String term);
	
	/**
	 * 查詢Stavg物件
	 * 
	 * @comment 包括Like Expression
	 * @param css tw.edu.chit.models.Stavg Object
	 * @return java.util.List List of tw.edu.chit.models.Stavg Objects
	 */
	public List<Stavg> findStavgBy(Stavg stavg);
	
	/**
	 * 查詢老師未上傳成績名單
	 * 
	 * @param campus	部制, 11:台北日間 12:台北進修 13:台北學院 21:新竹日間 22:新竹進修 23:新竹學院
	 * @param filtertype 1:期中, 2:學期期末
	 * @param scope 1:全部, 2:畢業班, 3:畢業班除外
	 * @return List
	 * @throws SQLException 
	 */
	public List findScoreNotUpload(String campus, String filtertype, String scope) throws SQLException;
	
	/**
	 * 查詢老師輸入平時及期末成績時,所有選課學生平時成績次數不一致
	 * @param scope 1:全部, 2:畢業班, 3:畢業班除外
	 * @return List
	 */
	public List findRegsScoreLoss(String scope);
	
	/**
	 * 查詢老師上傳成績時間 Optime1
	 * 
	 * @param level 0:期中期末及畢業, 1:期中, 2:期末, 3:畢業, 4:暑修, 5:操行, 6:教學評量
	 * @return List<Optime1>
	 */
	public List<Optime1> findOptime(String level);
	
	/**
	 * 刪除老師上傳成績時間 Optime1
	 * 
	 * @param List<Optime1> optimes
	 * @param bundle
	 * @return ActionMessages
	 */
	public ActionMessages delOptime(List<Optime1> optimes, ResourceBundle bundle);
	
	/**
	 * 取得學校現有部制
	 * Ex: Map("11", "台北日間"), Map("22", "新竹進修") ...
	 * @return List of Map(String no, String name)
	 */
	public List<Code5> findCampusDepartment();
	
	/**
	 * 取得部制名稱
	 * @param campus 部制代碼:11,12,....
	 * @return String Name
	 */
	public String findCampusName(String campus);
	
	/**
	 * 取得成績上傳日期設定之類別, 1:期中, 2:期末, 3:畢業, 4:暑修, 5:操行, 6:教學評量 ...
	 *  @param level
	 * @return List<Code5> Score Upload level
	 */
	public List<Code5> findSetDateLevel(String level);
	
	/**
	 * 建立老師上傳成績時間設定
	 * 
	 * @param form
	 * @return ActionMessages
	 */
	public ActionMessages createUploadDateByForm(DynaActionForm form);
	
	/**
	 * 修改老師上傳成績時間設定
	 * 
	 * @param inEditList
	 * @param form
	 * @return ActionMessages
	 */
	public ActionMessages modifyUploadDateByForm(List<Optime1> inEditList, DynaActionForm form);
	
	/**
	 * 儲存ClassScoreSummary物件
	 * 
	 * @param css tw.edu.chit.models.ClassScoreSummary Object
	 */
	public void txSaveClassScoreSummary(ClassScoreSummary css);
	
	public List<ClassScoreSummary> findClsssScoreSummary();
	
	/**
	 * 查詢ClassScoreSummary物件
	 * 
	 * @comment 包括Like Expression
	 * @param css tw.edu.chit.models.ClassScoreSummary Object
	 * @return java.util.List List of tw.edu.chit.models.ClassScoreSummary
	 *         Objects
	 */
	public List<ClassScoreSummary> findClassScoreSummaryByLikeExpression(
			ClassScoreSummary css);
	
	public void deleteClassScoreSummary(List<ClassScoreSummary> css);
	
	/**
	 * 儲存MidtermExcluded物件
	 * 
	 * @param mes java.util.List List of MidtermExcluded Objects
	 */
	public void txSaveMidtermExcluded(List<MidtermExcluded> mes);
	
	/**
	 * 查詢MidtermExcluded物件
	 * 
	 * @param me tw.edu.chit.models.MidtermExcluded Object
	 * @return tw.edu.chit.models.MidtermExcluded Object
	 */
	public MidtermExcluded findMidtermExcludedBy(MidtermExcluded me);
	
	/**
	 * 查詢MidtermExcluded物件
	 * 
	 * @param me tw.edu.chit.models.MidtermExcluded Object
	 * @return tw.edu.chit.models.MidtermExcluded Object
	 */
	public List<MidtermExcluded> findMidtermExcludedBy1(MidtermExcluded me);
	
	/**
	 * 刪除MidtermExcluded物件
	 * 
	 * @param me tw.edu.chit.models.MidtermExcluded Object
	 */
	public void txDeleteMidtermExcluded(MidtermExcluded me);
	
	/**
	 * 查詢所有1/2不及格學生
	 * 
	 * @param fs tw.edu.chit.models.FailStudents1 Object
	 * @return java.util.List List of tw.edu.chit.models.FailStudents1 Objects
	 */
	@SuppressWarnings("unchecked")
	public List findFailStudentsBy(FailStudents1 fs);
	
	/**
	 * 查詢所有2/3不及格學生
	 * 
	 * @param fs tw.edu.chit.models.FailStudents2 Object
	 * @return java.util.List List of tw.edu.chit.models.FailStudents2 Objects
	 */
	@SuppressWarnings("unchecked")
	public List findFailStudentsBy(FailStudents2 fs);
	
	/**
	 * 查詢學生歷年成績
	 * 
	 * @param fs tw.edu.chit.models.ScoreHist Object
	 * @return java.util.List List of tw.edu.chit.models.ScoreHist Objects
	 */
	public List<ScoreHist> findScoreHistBy(ScoreHist scoreHist);
	
	/**
	 * 刪除歷年成績
	 * 
	 * @param hists List of tw.edu.chit.models.ScoreHist Objects
	 */
	public void txDeleteScoreHist(List<ScoreHist> hists);
	
	/**
	 * 查詢95.2之1/2不及格學生清單
	 * 
	 * @param failStudentsHist tw.edu.chit.models.FailStudentsHist Object
	 * @return java.util.List List of tw.edu.chit.models.FailStudentsHist
	 *         Objects
	 */
	public List<FailStudentsHist> findFailStudentsHistBy(
			FailStudentsHist failStudentsHist);
	
	/**
	 * 查詢學生期中/學期成績
	 * 
	 * @param failStudentsHist tw.edu.chit.models.StdScore Object
	 * @return java.util.List List of tw.edu.chit.models.StdScore Objects
	 */
	public List<StdScore> findStdScoreBy(StdScore stdScore);
	
	/**
	 * 找出傳入參數之相關所有班級
	 * 
	 * @param clazzFilter 參數like 164
	 * @return List<Clazz>
	 */
	public List<Clazz> findAllClasses(String clazzFilter);
	
	/**
	 * 尋找班級
	 * 
	 * @param clazz tw.edu.chit.models.Clazz Object
	 * @param restrictionClass Restriction's Class Array
	 * @param includeLiteracy Is include Literacy Class
	 * @return java.util.List List of Clazz Objects
	 */
	public List<Clazz> findClassBy(Clazz clazz, Clazz[] restrictionClass,
			boolean isIncludeLiteracy);
		
	/**
	 * 查詢調整成機比例之教師課程
	 * 
	 * @param clazzFilter 班及範圍
	 * @param teachedId 教師身分證字號
	 * @return List<Rrate>
	 */
	public List<Rrate> findScoreRateAdjusted(String clazzFilter, String teachedId);
	
	public List<Csno> findCourseByName(String chiName);
	
	public List<Csno> findCourseByCode(String cscode);
}
