package tw.edu.chit.dao;

import java.util.List;

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

public interface ScoreDAO extends DAO {
	
	@SuppressWarnings("unchecked")
	public List StandardHqlQuery(String hql);
	
	@SuppressWarnings("unchecked")
	public List StandardHqlQuery(String hql, Object[] params);
	
	@SuppressWarnings("unchecked")
	public List<ScoreHist> findScoreHistByStudentNo(String student_no);

	@SuppressWarnings("unchecked")
	public List submitQuery(String hql);

	@SuppressWarnings("unchecked")
	public List findStudentByStudentNO(String student_no);

	public List<Csno> findCourseByCode(String cscode);

	public List<Clazz> findClassByCode(String classcode);

	public void saveScoreHist(ScoreHist score);

	public void removeScoreHist(ScoreHist score);
	
	public int removeAnyThing(String hql);
	
	public int removeAnyThing1(String hql);

	public List<Just> findJustByDepart(String departclass);
	
	/**
	 * 
	 * @param md
	 * @return
	 */
	public List<MasterData> findMasterDataBy(MasterData md);
	
	/**
	 * 定義以學號取得碩士成績資料之方法
	 * 
	 * @param student_no 學號
	 * @return java.util.List List of MasterData objects
	 */
	public List<MasterData> findMasterByStudentNO(String student_no);
	
	/**
	 * 定義以ID取得MasterData
	 * 
	 * @param id Identity
	 * @return tw.edu.chit.model.MasterData object
	 */
	public MasterData findMasterDataById(Integer id);
	
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
	 * 以班級代碼查詢期中/期末不及格與平均分數資料
	 * 
	 * @param css tw.edu.chit.model.ClassScoreSummary Objects
	 * @return tw.edu.chit.model.ClassScoreSummary Objects
	 */
	public ClassScoreSummary findClssScoreSummaryBy(ClassScoreSummary css);
	
	public List<ClassScoreSummary> findClassScoreSummary();
	
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
	 * 查詢ClassScoreSummary物件
	 * 
	 * @param css tw.edu.chit.models.ClassScoreSummary Object
	 * @return tw.edu.chit.models.ClassScoreSummary Object
	 */
	public ClassScoreSummary findClassScoreSummaryBy(ClassScoreSummary css);
	
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
	
	/**
	 * 查詢Stavg物件
	 * 
	 * @comment 包括Like Expression
	 * @param css tw.edu.chit.models.Stavg Object
	 * @return java.util.List List of tw.edu.chit.models.Stavg Objects
	 */
	public List<Stavg> findStavgBy(Stavg stavg);
	
	/**
	 * 查詢所有1/2不及格學生
	 * 
	 * @param fs tw.edu.chit.models.FailStudents1 Object
	 * @return java.util.List List of tw.edu.chit.models.FailStudents1 Objects
	 */
	public List<FailStudents1> findAllFailStudents1(FailStudents1 fs);

	/**
	 * 查詢所有2/3不及格學生
	 * 
	 * @param fs tw.edu.chit.models.FailStudents1 Object
	 * @return java.util.List List of tw.edu.chit.models.FailStudents1 Objects
	 */
	public List<FailStudents2> findAllFailStudents2(FailStudents2 fs);
	
	/**
	 * 查詢學生歷年成績
	 * 
	 * @param fs tw.edu.chit.models.ScoreHist Object
	 * @return java.util.List List of tw.edu.chit.models.ScoreHist Objects
	 */
	public List<ScoreHist> findScoreHistBy(ScoreHist scoreHist);
	
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
	 * @commend 以名次做排序
	 * @param failStudentsHist tw.edu.chit.models.StdScore Object
	 * @return java.util.List List of tw.edu.chit.models.StdScore Objects
	 */
	public List<StdScore> findStdScoreBy(StdScore stdScore);
	
	/**
	 * 尋找班級
	 * 
	 * @param clazz tw.edu.chit.models.Clazz Object
	 * @param restrictionClass Restriction's Class Array
	 * @param includeLiteracy Is include Literacy Class
	 * @return java.util.List List of Clazz Objects
	 */
	public List<Clazz> findClassBy(Clazz clazz, Clazz[] restrictionClass,
			boolean includeLiteracy);
	
}
