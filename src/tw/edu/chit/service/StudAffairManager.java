package tw.edu.chit.service;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Clean;
import tw.edu.chit.model.Code1;
import tw.edu.chit.model.Code2;
import tw.edu.chit.model.CounselingCode;
import tw.edu.chit.model.Desd;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Dipost;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.DtimeStudaffair;
import tw.edu.chit.model.EmplOpinionSuggestion;
import tw.edu.chit.model.ExamineRule;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.Keep;
import tw.edu.chit.model.Progress;
import tw.edu.chit.model.RuleTran;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.StdOpinionSuggestion;
import tw.edu.chit.model.StudCounseling;
import tw.edu.chit.model.StudDocApply;
import tw.edu.chit.model.StudDocExamine;
import tw.edu.chit.model.StudPublicDocExam;
import tw.edu.chit.model.StudPublicDocUpload;
import tw.edu.chit.model.StudPublicLeave;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.Subs;
import tw.edu.chit.model.domain.Dtimes;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.model.StudDocUpload;
import tw.edu.chit.model.StudDocDetail;


public interface StudAffairManager {
	
	/**
	 * 設定process 處理進度
	 * 
	 * @param methodName
	 * @param step
	 * @param rcount
	 * @param total
	 * @param percentage
	 * @param isComplete
	 */
	public void setRunStatus(String methodName, String step, int rcount, int total, double percentage, String isComplete);
	
	/**
	 * 取得process 處理進度
	 * 
	 * @return List<Map> (methodName, step, percentage)
	 */
	public List chkstatus();
	
	/**
	 * 以學號查詢在學學生
	 * 
	 * @param String studentNo
	 * 
	 * @return Student or null
	 */
	public Student findStudentByStudentNo(String studentNo);
	
	/**
	 * 以學號查詢非在學學生
	 * 
	 * @param String studentNo
	 * 
	 * @return Student or null
	 */
	public Graduate findGraduateByStudentNo(String studentNo);
	
	/**
	 * 修改因學生曠缺資料變更導致操行成績異動
	 * 
	 * @param studentNo 學生學號
	 * @param dilgScore 曠缺加減分
	 * 
	 * @return ActionMessages 成功:empty, 失敗:!empty
	 */
	public ActionMessages modifyJustDilgScore(String studentNo, double dilgScore);
	
	/**
	 * 修改因學生獎懲資料變更導致操行成績異動
	 * 
	 * @param studentNo 學生學號
	 * @param desdScore 獎懲加減分
	 * @return ActionMessages 成功:empty, 失敗:!empty
	 */
	public ActionMessages modifyJustDesdScore(String studentNo, double desdScore);
	
	/**
	 * 重新計算該學生ddate日期之修課扣考曠缺節數資料
	 * 
	 * @param studentNo 學生學號
	 * @param ddate 日期
	 * @return ActionMessages 成功:empty, 失敗:!empty
	 */
	public ActionMessages modifySeldDilgPeriod(String studentNo, Date ddate);
	
	/**
	 * 查詢學生請假資料
	 * 
	 * @param String ddate
	 * @param String studentNo
	 * @param String classInCharge 負責班級權限檢查
	 * @return List<Dilg>
	 */
	public List<Dilg> findDilgByTimeOffInfo(String ddate, String studentNo, String classInCharge);
	
	/**
	 * 查詢學生當日請假資料
	 * @param studentNo 學號
	 * @param tdate 日期 yyyy-mm-dd
	 * 
	 * @return null或請假資料
	 */
	public Dilg findDilgByStdDate(String studentNo, String tdate);
	
	/**
	 * 刪除學生請假資料
	 * 
	 * @param List<Dilg> selDilgs
	 * @param Action.ResourceBundle bundle
	 * @return List<Dilg> 不能被刪除的請假資料
	 */
	public List<Dilg> delStudTimeOffs(List<Dilg> selDilgs, ResourceBundle bundle);
	
	/**
	 * 新增學生請假資料前,檢查學生請假資料是否已經存在
	 * 
	 * @param DynaActionForm.getMap() formMap
	 * @param errors
	 * @param bundle
	 * @return ActionMessages errors
	 */
	public ActionMessages validateStudTimeOffCreate(Map formMap, ActionMessages errors, ResourceBundle bundle);

	/**
	 * 修改學生請假資料前,檢查欲修改之學生請假資料是否已經存在
	 * 
	 * @param DynaActionForm.getMap() formMap
	 * @param Dilg dilg
	 * @param ActionMessages errors
	 * @param ResourceBundle bundle
	 * @return ActionMessages errors
	 */
	public ActionMessages validateStudTimeOffModify(Map formMap, Dilg dilg, ActionMessages errors, ResourceBundle bundle);
	
	
	/**
	 * 新增學生請假資
	 * 
	 * @param DynaActionForm.getMap() formMap
	 * @param String departClass
	 * 
	 * @return void
	 */
	public void createStudTimeOff(Map formMap, String departClass);

	/**
	 * 修改學生請假資,不可以修改學生學號班級等資料,只能修改日期及缺曠資料.
	 * 
	 * @param DynaActionForm.getMap() formMap -->新資料
	 * @param Dilg dilg -->舊資料
	 * @param String departClass
	 * 
	 * @return void
	 */
	public void modifyStudTimeOff(Map formMap, Dilg dilg);

	/**
	 * 修改學生請假資,不可以修改學生學號班級及日期等資料,只能修改缺曠資料.
	 * 
	 * @param DynaActionForm.getMap() formMap -->新資料
	 * @param List<Dilg> dilgList -->舊資料
	 * 
	 * @return void
	 */
	public void modifyStudTimeOffList(Map formMap, List<Dilg> dilgList);
	
	/**
	 * 搜尋某班級在指定日期(星期?)所開的課程
	 * 
	 * @param String departClass
	 * @param String iweek (星期?)
	 * @param mode -->"":ALL ByClass:不包含開放選修課程, BySubject:只有開放選修課程
	 * 
	 * @return List
	 */
	public List findCscodeByClassAtDate(String departClass, String iweek, String mode);
	
	/**
	 * 查出該班級所有的學生
	 * @param departClass
	 * @return List<Student>
	 */
	public List<Student> findStudentsByClass(String departClass);
	
	/**
	 * 查詢該課程之所有選課學生
	 * 
	 * @param Oid -- Dtime_oid
	 * @param departClass -- 開課班級
	 * 
	 * @param group --> 0:all 1:該開課班級之選修學生 2:非該開課班級之選修學生
	 * @return List<Seld>
	 */
	public List<Seld> findSeldForClassBook(int Oid, String departClass, int group);
	
	/**
	 * 查詢該課程之所有加或退選課學生資料
	 * 
	 * @param oid --> dtimeOid
	 * @param adddraw --> 'A':Add 'D':Draw
	 * @return List<Adcd>
	 */
	public List<Adcd> findAdcdForClassBook(int oid,  String adddraw);
		
	/**
	 * 計算學生某課程之曠缺課節數
	 * 
	 * @param List<Map> classBook (studentNo, studentName, status)
	 * @param oid --> Dtime_oid
	 * @return int[] timeOff 
	 */
	public int[] calTimeOffBySubject(List<Map> classBook, int oid);
	
	/**
	 * 
	 * @param List<Map> classBook (studentNo, studentName, status)
	 * @param dateBegin 開始日期
	 * @param dateEnd 結束日期
	 * @param oid Dtime_oid
	 * 
	 * @return int[] timeOff
	 */
	public int[] calPeriodTimeOffBySubject(List classBook, String dateBegin, String dateEnd, int oid);
	
	/**
	 * 將點名簿中學生曠缺資料寫入Dilg中,如果該筆資料已經存在則直接Update
	 * 
	 * @param Map StudTimeoffInit (Include tfYear,tfMonth,tfDay,tfWeek)
	 * @param List classBook (Include studentNo, status0,...,status15)
	 * @param int begin (subject begin)
	 * @param int end (subject end)
	 * @return ActionMessages
	 */
	public ActionMessages createOrUpdateDilgBatch(Map StudTimeoffInit, List classBook, int begin, int end);
	
	/**
	 * 點名簿批次更新曠缺後更新Seld之扣考節數
	 * 
	 * @param sddate 曠缺日期
	 * @param classBook : List of 點名簿
	 * 
	 * @return ActionMessages empty:更新成功, !empty:更新有問題
	 */
	public ActionMessages updateSeldDilgPeriodBatch(Date sddate, List classBook);
	
	/**
	 * 點名簿批次更新曠缺後更新操行曠缺加減分及總分
	 * 
	 * @param classBook : List of 點名簿
	 * 
	 * @return ActionMessages empty:更新成功, !empty:更新有問題
	 */
	public ActionMessages updateJustDilgScoreBatch(List classBook);
	
	/**
	 * 取得每一位學生在點名簿中之缺曠課資料
	 * @param List classBook
	 * @param String sddate
	 * @param int dtime_begin
	 * @param int dtime_end
	 * @return List<Map> <studentNo, clazz, status>
	 */
	public List<Map> findDilg4ClassBook(List classBook, String sddate, int dtime_begin, int dtime_end);
	
	/**
	 * 取得學生曠缺課資料
	 * 
	 * @param studentNo
	 * @param mode "all":所有曠缺資料, "subject":科目曠缺記錄. "elearn":遠距教學曠缺 
	 * 
	 * @return [all:List of Dilg but not include elearn],
	 * 			 [subject:List of Map], [elearn:List of Seld only seld.elearnDilg!=null]
	 */
	public List findDilgByStudentNo(String studentNo, String mode);
	
	/**
	 * 取得學生某一期間內曠缺課資料
	 * @param studentNo
	 * @param dateBegin
	 * @param dateEnd
	 * @param mode "all":所有曠缺資料 "subject":科目曠缺記錄
	 * @return List
	 */
	public List findPeriodDilgByStudentNo(String studentNo, String dateBegin, String dateEnd, String mode);
	
	/**
	 * 取得學生某一課程曠缺課資料
	 * 
	 * @param studentNo 學號
	 * @param cscode 課程代碼
	 * 
	 * @return	List<Map> {cscode,dtimeOid,departClass,subjectName,period,tfLimit,timeOff,warnning}
	 */
	public List findDilgByStudentNoAndCscode(String studentNo, String cscode);
	
	/**
	 * 檢查學生所屬部制為日間(1),夜間(2)或進修學院專校(3)
	 * @param studentNo
	 * @return String "1":日間, "2":夜間, "3":進修學院專校
	 */
	public String chkStudentDepart(String studentNo);
	
	/**
	 * 取得獎懲原因代碼
	 * 
	 * @return List<Code2>
	 */
	public List<Code2> findBonusPenaltyReason(String code);
	
	/**
	 * 建立獎懲紀錄(Multi records used by OneCase)(Table->desd)
	 * 
	 * @param formMap
	 * @return ActionMessages
	 */
	public ActionMessages createDesdByForm(Map formMap);
	
	/**
	 * 建立獎懲紀錄(Single record)(Table->desd)
	 * 
	 * @param propMap
	 * @return ActionMessages
	 */
	public ActionMessages createDesdByProperties(Map propMap);
	
	/**
	 * 建立獎懲紀錄(Multi records used by OneClass)(Table->desd)
	 * @param formMap
	 * @param students List of selected students in class 
	 * @return ActionMessages
	 */
	public ActionMessages createDesdByStudents(Map formMap, List<Student> students);
	
	/**
	 * 查詢學生獎懲紀錄
	 * 
	 * @param ddate (YY/MM/DD)
	 * @param studentNo
	 * @param classInCharge
	 * @return List<Desd>
	 */
	public List<Desd> findDesdByFormInfo(String ddate, String studentNo, String classInCharge);
	
	/**
	 * 刪除學生獎懲紀錄
	 * 
	 * @param List<Desd> selDesds
	 * @param bundle
	 * @return List<Desd> Undelete records
	 */
	public List<Desd> delStudBonusPenalty(List<Desd> selDesds, ResourceBundle bundle);
	
	/**
	 * 修改學生獎懲紀錄
	 * 
	 * @param desdInEdit 編輯中的獎懲紀錄
	 * @param formMap DynaActionForm
	 * @return ActionMessages
	 */
	public ActionMessages modifyDesdByForm(List<Desd> desdInEdit, Map formMap);
	
	/**
	 * 計算學生曠缺獎懲之操行加減分
	 * 
	 * @param studentNo
	 * @return double[2] double[0]:Dilg->曠缺 double[1]:Desd->獎懲
	 */
	public double[] calConductScoreOfDilgDesd(String studentNo);

	/**
	 * 計算多位學生曠缺獎懲之操行加減分
	 * @param studentNos 學生們的學號
	 * 
	 * @return double[n][2] double[n][0]:Dilg->曠缺 double[n][1]:Desd->獎懲
	 */
	public double[][] calConductScoreBatch(List studentNos);
	
	/**
	 * 計算學生某一期間曠缺獎懲之操行加減分
	 * @param studentNo 學號
	 * @param dateBegin 起始日期 (yyyy-mm-dd)
	 * @param dateEnd 結束日期	(yyyy-mm-dd)
	 * @param Mode 1:曠缺 2:獎懲 3:曠缺及獎懲
	 * @return double[] 加減分
	 */
	public double[] calPeriodConductScoreOfDilgDesd(String studentNo, String dateBegin, String dateEnd, String Mode);
	
	/**
	 * 計算一學生之曠缺加減分
	 * 
	 * @param dilgList 該學生之曠缺記錄
	 * @param mode 0:排除扣考科目曠缺 1:包含扣考科目曠缺
	 * @return score 加減分
	 */
	public double calDilgScore(List<Dilg> dilgList, String mode);

	/**
	 * 依據學生學號計算其曠缺加減分
	 * 
	 * @param studentNo 學號
	 * @param mode 0:排除扣考科目曠缺 1:包含扣考科目曠缺
	 * 
	 * @return score 加減分
	 */
	public double calDilgScoreByStudent(String studentNo, String mode);
	
	/**
	 * 計算一學生之獎懲加減分
	 * 
	 * @param desdList 該學生之獎懲記錄
	 * 
	 * @return score 加減分
	 */
	public double calDesdScore(List<Desd> desdList);
	
	/**
	 * 依據學生學號計算其獎懲加減分
	 * 
	 * @param studentNo 學號
	 * 
	 * @return score 加減分
	 */
	public double calDesdScoreByStudent(String studentNo);
	
	/**
	 * 取得單一學生獎懲資料
	 * 
	 * @param studentNo 學號
	 * 
	 * @return List<Desd>
	 */
	public List<Desd> findDesdByStudentNo(String studentNo);
	
	/**
	 * 取得操行評語代碼資料 Code1
	 * 
	 * @param no -- 代碼
	 * @return List<Code1>
	 */
	public List<Code1> findConductMark(String no);

	/**
	 * 取得獎懲加扣分標準資料
	 * @param no 獎懲種類代碼 '':全部 1:大功 2:小功 3:嘉獎 4:大過 5:小過 6:申誡
	 * @return List<Subs>
	 */
	public List<Subs> findSubs(String no);
	
	/**
	 * 取得單一學生操行成績資料
	 * 
	 * @param studentNo
	 * @param classInCharge
	 * @return List<Just>
	 */
	public List<Just> findJustByStudentNo(String studentNo, String classInCharge);
	
	/**
	 * 刪除學生操行成績
	 * 
	 * @param selJusts 
	 * @param Bundle
	 * @return ActionMessages
	 */
	public ActionMessages delStudConduct(List<Just> selJusts, ResourceBundle Bundle);
	
	/**
	 * 建立學生操行成績資料
	 * 
	 * @param DynaActionForm form 
	 * @param classInCharge
	 * @return ActionMessages
	 */
	public ActionMessages createJustByFormProp(DynaActionForm form, String classInCharge);
	
	/**
	 * 修改學生操行成績資料
	 * @param form DynaActionForm
	 * @param just Just
	 * @param classInCharge
	 * @return ActionMessages
	 */
	public ActionMessages modifyJustByFormProp(DynaActionForm form, Just just, String classInCharge);
	
	/**
	 * 取得全班學生操行成績資料
	 * 
	 * @param departClass
	 * @return List<Just>
	 */
	public List<Just> findJustByClass(String departClass);
	
	/**
	 * 建立或更新全校學生操行成績
	 * 
	 * @param campus 11:台北日間, 12:台北進修(夜間), 13:台北學院, 21:新竹日間, 22:新竹進修(夜間), 23:新竹學院
	 * @param mode create:刪除並重新建立操行成績, update:重新計算操行成績
	 * @return ActionMessages
	 */
	public ActionMessages studCreateOrUpdateJust(String campus, String mode);

	/**
	 * 更新全校學生操行成績
	 * 
	 * @param campus
	 * @return ActionMessages
	 */
	public ActionMessages conductUpdate(String campus);
	
	/**
	 * 建立全校學生操行成績
	 * 
	 * @param campus
	 * @return ActionMessages
	 */
	public ActionMessages conductCreate(String campus);
	
	/**
	 * 修改全班學生操行成績資料
	 * @param form DynaActionForm
	 * @param justList
	 * @param classInCharge
	 * @return ActionMessages
	 */
	public ActionMessages modifyClassJustByFormProp(DynaActionForm form, List justList, String classInCharge);
	
	/**
	 * 修改上傳全班學生操行成績資料
	 * 
	 * @param form
	 * @param justList
	 * @param classInCharge
	 * @param uploadMode Teacher:導師, Chairman:系主任, Drillmaster:教官
	 * @return ActionMessages
	 */
	public ActionMessages modifyUploadJustByFormProp(DynaActionForm form, List justList, String classInCharge, String uploadMode);
	
	/**
	 * 取得學校現有部制
	 * Ex: 11台北日間, 22新竹進修...
	 * @return List of department name
	 */
	public List<String> findCampusDepartment();
	
	/**
	 * 取得該學生所修課程缺曠課及扣考狀況
	 * 
	 * @param studentNo 學號
	 * @param range -> 差幾節課就會扣考.
	 * @return List {cscode,departClass,dtimeOid,subjectName
	 * 			,period,tfLimit,timeOff,warnning{'yes','no'}}
	 */
	public List findDilgRangeByStudentNo(String studentNo, int range);
	
	/**
	 * 取得某一課程所有選修學生之缺曠課及扣考狀況
	 * 
	 * @param dtime 開課資料Dtime
	 * @param range 差幾節課就會扣考.
	 * 
	 * @return List {studentNo,cscode,departClass,dtimeOid,subjectName
	 * 			,period,tfLimit,timeOff,warnning{'yes','no'}}
	 */
	public List findDilgRangeBySubject(Dtime dtime, int range);
	
	/**
	 * 扣考學生查詢
	 * 
	 * @param departClass -> wild class like '164%'
	 * @param range -> 差幾節課就會扣考.
	 * @param scope -> 0:全部, 1:畢業班, 2:除畢業班
	 * @param sorttype -> 0:課程為主, 1:學生為主
	 * @return List {studentNo,studentName,stDeptClass,stDeptClassName,cscode,
	 * 			departClass,dtimeOid,subjectName,period,tfLimit,timeOff,warnning{'yes','no'}}
	 */
	public List findCantExamStudByDepartClass(String departClass, int range, String scope, String sorttype);
	
	/**
	 * 根據操行成績建立定察學生
	 * 
	 * @param campus 部制
	 * @param sYear 學年
	 * @param sTerm 學期
	 * @return List<Keep>
	 */
	public List createInspectedStudents(String campus, String sYear, String sTerm);
	
	/**
	 * 依據 Form 輸入資料查詢定察學生資料
	 * @param formMap (studentNo, studentName, class, downYear)
	 * @return List<Keep>
	 */
	public List<Keep> findStudInspectedByForm(Map formMap);

	/**
	 * 刪除定察學生資料
	 * 
	 * @param selKeeps
	 * @param bundle
	 * @return ActionMessages
	 */
	public ActionMessages delStudInspected(List<Keep> selKeeps, ResourceBundle bundle);
	
	/**
	 * 建立學生定察資料
	 * 
	 * @param dForm
	 * @return ActionMessages
	 */
	public ActionMessages createInspectedByForm(Map dForm);
	
	/**
	 * 修改學生定察資料
	 * 
	 * @param dForm
	 * @return ActionMessages
	 */
	public ActionMessages updateInspectedByForm(Keep keep, Map dForm);

	/**
	 * 以學號查詢操行資料
	 * 
	 * @param studentNo 學號
	 * @return tw.edu.chit.model.Just Object
	 */
	public Just findJustByStudentNo(String studentNo);
	
	/**
	 * 儲存StdOpinionSuggestion物件
	 * 
	 * @param sos tw.edu.chit.model.StdOpinionSuggestion Object
	 */
	public void txAddStdOpinionSuggestion(StdOpinionSuggestion sos);
	
	/**
	 * 儲存EmplOpinionSuggestion物件
	 * 
	 * @param eos tw.edu.chit.model.EmplOpinionSuggestion Object
	 */
	public void txAddEmplOpinionSuggestion(EmplOpinionSuggestion eos);
	
	/**
	 * 取得每週曠缺統計表資料
	 * 
	 * @param DateStart 起始日期
	 * @param DateEnd	結束日期
	 * @param clazzFilter 搜尋班級
	 * @param ClassInChargeSAF 權限班級
	 * @return List
	 */
	public List findDilg4Summary(String DateStart, String DateEnd, String clazzFilter,
			String ClassInChargeSAF);
	/**
	 * 取得學期曠缺人次統計表資料
	 * 
	 * @param clazzFilter 搜尋班級
	 * @param pmode 0:全校 1:畢業班
	 * @return List
	 */
	public List findDilg4SummaryT(String clazzFilter, String pmode);
	
	/**
	 *取得學生曠缺家長通知單統計表資料 
	 * 
	 * @param DateStart 起始日期
	 * @param DateEnd 結束日期
	 * @param clazzFilter 搜尋班級
	 * @param threshold1 節數1
	 * @param threshold2 節數2
	 * @param threshold3 節數3
	 * @param pmode 操作模式：0:第一次列印(更新通知紀錄) 1:再次列印(不更新通知紀錄)
	 * @return List
	 */
	public List findDilgAlert4P(String DateStart, String DateEnd, String clazzFilter,
			String threshold1,String threshold2,String threshold3,String pmode);
	
	/**
	 * 取得學生曠缺超過30節導師聯絡資料
	 * 
	 * @param DateStart 起始日期
	 * @param DateEnd 結束日期
	 * @param clazzFilter 搜尋班級
	 * @param pmode 操作模式：0:第一次列印(更新聯絡紀錄) 1:再次列印(不更新聯絡紀錄)
	 * @return List
	 */
	public List findDilgAlert4T(String DateStart, String DateEnd, String clazzFilter,
			String pmode);
	/**
	 * 
	 * @param dmList
	 * @return
	 */
	public ActionMessages createOrModifyDilgMail(List dmList);
		
	/**
	 * 取得曠缺學生曠缺統計資料
	 * @param departClass 班級
	 * @param DateEnd 結束日期
	 * @param period 超過節數
	 * @param qscope 查詢範圍 0:全部, 1:畢業班
	 * @return List<Map>
	 */
	public List findTimeOffSummaryExt(String departClass, String DateEnd, 
			String period, String qscope);
	
	/**
	 * 取得嚴重曠缺學生名單
	 * 
	 * @param departClass 班級
	 * @param DateEnd 結束日期
	 * @param period 超過節數
	 * @param qscope 查詢範圍 0:全部, 1:畢業班
	 * @return List<Map>
	 */	
	public List findTimeOffSerious(String departClass, String DateStart, String DateEnd, String period, String qscope);
	
	/**
	 * 取得全勤學生名單
	 * 
	 * @param clazzFilter 
	 * @return List {deptClassName, studentNo, studentName}
	 */
	public List findNoTimeOffStudents(String clazzFilter);
	
	/**
	 * 取得本學期操行成績報表資料
	 * 
	 * @param clazzFilter 班級
	 * @param cmode 0:代碼, 1:評語
	 * @return Map {justList, sumList}
	 */
	public Map<String, List> findJustThisTerm(String clazzFilter, String cmode, String totalint) throws Exception;
	
	/**
	 * 操行等第名單
	 * 
	 * @param clazzFilter
	 * @param level 0:優等, 1:丙等, 4:丁等, 5:定察, A:95分以上, S:統計
	 * @return List
	 */
	public List findJustLevel(String clazzFilter, String level) throws Exception;
	
	/**
	 * 取得定察學生相關名冊
	 * 
	 * @param clazzFilter 班級
	 * @param syear 學年
	 * @param sterm 學期
	 * @return List 
	 */
	public List findKeepStudents(String clazzFilter, String syear, String sterm);
	
	/**
	 * 獎懲統計資料
	 * 
	 * @param clazzFilter 班級
	 * @param mode 0:一般, 1:教育部
	 * @return List
	 */
	public List BonusPenaltySum(String clazzFilter, String mode);
	
	/**
	 * 使用 sql 查詢取得資料
	 * 
	 * @param sql
	 * @return
	 */
	public List findAnyThing(String sql);
	
	public ActionMessages seldExamUpdate(String depart);
	
	/**
	 * 更新課程主檔扣考節數
	 * 
	 * @return ActionMessages empty:success, not empty: fail
	 */
	public ActionMessages UpdateDtimeDilgPeriod();
	
	/**
	 * 取得生活教育競賽[規定傳達]成績資料
	 * 
	 * @return List
	 */
	public List<RuleTran> findRuleTran();
	
	/**
	 * 重建生活教育競賽[規定傳達]成績資料
	 *
	 * @return List
	 */
	public List<RuleTran> createRuleTran();
	
	/**
	 * 更新生活教育競賽[規定傳達,幹部會議未到扣分]成績資料
	 * 
	 * @param clazzes
	 * @param scores 規定傳達成績
	 * @param mscores 幹部會議未到扣分
	 * 
	 * @return ActionMessages
	 */
	public ActionMessages updateRuleTrans(String[] clazzes, String[] scores, String[] mscores);

	/**
	 * 取得參加整潔競賽的班級
	 * 
	 * @return List<Clazz>
	 */
	public List<Clazz> findClass4Racing();
	
	/**
	 * 建立整潔競賽成績
	 * 
	 * @param weekNo 週次
	 * @param clazzes 班級 [陣列]
	 * @param scores 成績 [陣列]
	 * @return 信息 ActionMessages
	 */
	public ActionMessages createCleans(String weekNo, String[] clazzes, String[] scores);
	
	/**
	 * 取得該週之各班整潔競賽成績
	 * 
	 * @param weekNo 週次
	 * @return List<Clean>
	 */
	public List<Clean> findClean(String weekNo);
	
	/**
	 * 更新整潔競賽成績
	 * 
	 * @param weekNos 週次 [陣列]
	 * @param clazzes 班級 [陣列]
	 * @param scores 成績 [陣列]
	 * 
	 * @return 信息 ActionMessages
	 */
	public ActionMessages updateCleans(String[] weekNos, String[] clazzes, String[] scores);
	
	/**
	 * 獎懲資料查詢
	 * 
	 * @param startDate 起始日期(yy/mm/dd)
	 * @param endDate 結束日期(yy/mm/dd)
	 * 
	 * @return List<Desd>
	 */
	public List<Desd> findDesdByDateRange(String startDate, String endDate);
	
	/**
	 * 更新生活教育競賽獎懲資料
	 * 
	 * @param desdList 欲更新之獎懲記錄
	 * @param sels 對應獎懲記錄之是否納入計算資料[y/n]
	 * 
	 * @return 更新結果 ActionMessages
	 */
	public ActionMessages updateDesd4Racing(List<Desd> desdList, String[] sels);
	
	/**
	 * 查詢轉帳資料
	 * 
	 * @param dipost tw.edu.chit.models.Dipost Object
	 * @return java.util.List List of tw.edu.chit.models.Dipost Objects
	 */
	public List<Dipost> findDipostsBy(Dipost dipost);
	
	/**
	 * 將操行及獎懲資料轉至歷史檔(cond, comb1, comb2
	 * )
	 * @param schoolYear 學年
	 * @param schoolTerm 學期
	 * @param clazzFilter 班級
	 * 
	 * @return ActionMessages
	 */
	public ActionMessages txJustScore2History(String schoolYear, String schoolTerm, String clazzFilter);
	
	/**
	 * 畢業生歷年成績排名及全勤名單
	 * 
	 * @param clazzFilter 班級
	 * @param number 前n名
	 * @param haveLast 是否計算最後一學期成績
	 * 
	 * @return Map(List<SortCond> List<noAbsent>
	 * List<Map> {departClass, deptClassName, studentNo, studentName, score, noAbsent}
	 */
	public Map findGraduateJustSort(String clazzFilter, int number, boolean haveLast);
	
	/**
	 * 取得生活競賽成績資料
	 * 
	 * @param GroupNo 組別, 1:日四技大一, 2:日四技大二, 3:日四技大三, 4:日四技大四
	 * @param WeekStart 起始週次
	 * @param WeekEnd 結束週次
	 * 
	 * @return List {deptClassName,ruleTranScore,lifeScore,cleanScore,healthScore,timeOff10,
	 * timeOff20,timeOff30,timeOff40,timeOffmuch,absent,learnScore,bigBonus,
	 * smallBonus,bigPenalty,smallPenalty,publicScore,SumScore,order}
	 */
	public List findRacingScore(String GroupNo, String WeekStart, String WeekEnd);

	/**
	 * 取得學生綜合資料表之資料
	 * 
	 * @param schoolYear 學年
	 * @param schoolTerm 學期
	 * @param departClass 班級
	 * @param studentNo 學號
	 * 
	 * @return List
	 */
	public List getStudentsCompistData(String schoolYear, String schoolTerm, String departClass, String studentNo);
	
	/**
	 * 取得生活教育獎懲資料
	 * 
	 * @param clazzFilter 班級
	 * 
	 * @return List
	 */
	public List findBP4Racing(String clazzFilter);
	
	/**
	 * 將遠距教學曠缺資料轉入Seld選課檔並更新操行成績
	 * 
	 * @param edilgs List<Map>
	 * 
	 * @return ActionMessages
	 */
	public ActionMessages modifySeldElearnDilg(List<Map> edilgs);
	
	/**
	 * (廢除不用)統計並計算該學生曠缺之假別及加減分資料
	 * 
	 * @param dilgList List<Dilg> 曠缺資料
	 * @param mode "0":排除扣考科目之曠缺, "1":包含扣考科目之曠缺
	 * 
	 * @return double[0]:曠缺加減分, double[1~tflen]:各假別之加總資料
	 */
	public double[] calDilgSummary(List<Dilg> dilgList, String mode);
	
	/**
	 * 統計並計算該學生曠缺之假別及加減分資料(必須先將曠缺資料轉入Dilg_One)
	 * @param studentNo 學號
	 * @param mode 0:包含扣考科目之曠缺(不管no_exam), 1:排除扣考科目之曠缺(只計算no_exam=0)
	 * @return double[0]:曠缺加減分, double[1~tflen]:各假別之加總資料
	 */
	public double[] calDilgOneSummary(String studentNo, String mode);
	
	/**
	 * 統計學生純屬曠課節數資料
	 * 
	 * @param dilgList List<Dilg> 曠缺資料
	 * @param mode "0":排除扣考科目之曠缺, "1":包含扣考科目之曠缺
	 * 
	 * @return int[0]:升旗未到次數, int[1]:曠課節數
	 */
	public int[] dilgSummary(List<Dilg> dilgList, String mode);
	
	/**
	 * 取得執行過及正在執行的Progress records
	 * 
	 * @param name progress name
	 * 
	 * @return List<Progress>
	 */
	public List<Progress> findProgress(String name);
	
	/**
	 * 設定 TimeOffAlert4P Progress 執行紀錄
	 * 
	 * @param name Progress name
	 * @param parameter 執行參數
	 * 
	 * @return ActionMessages
	 */
	public Progress setTF4PProgress(String name, String parameter);
	
	/**
	 * 清除 TimeOffAlert4P Progress 執行紀錄
	 * 
	 * @param prgs 已經在進行的Progress
	 * 
	 * @return ActionMessages
	 */
	public ActionMessages resetTF4PProgress(Progress prgs);
	
	/**
	 * 取得操行評語資料
	 * 
	 * @param code 評語代碼
	 * 
	 * @return List<Code1>
	 */
	public List<Code1> findConductMarkCode(String code);
	
	/**
	 * 修改操行評語
	 * 
	 * @param code Code1
	 * @param formMap Map
	 * 
	 * @return ActionMessages
	 */
	public ActionMessages modifyConductMarkCode(Code1 code, Map formMap);
	
	/**
	 * 新增操行評語
	 * @param formMap Map
	 * @return ActionMessages
	 */
	public ActionMessages createConductMarkCode(Map formMap);
	
	/**
	 * 刪除操行評語
	 * @param selCodes List<Code1>
	 * @param bundle ResourceBundle
	 * @return ActionMessages
	 */
	public ActionMessages delConductMarkCode(List<Code1> selCodes, ResourceBundle bundle);
	
	/**
	 * 取得獎懲原因資料
	 * 
	 * @param code 評語代碼
	 * 
	 * @return List<Code1>
	 */
	public List<Code2> findBonusPenaltyReasonCode(String code);
	
	/**
	 * 修改獎懲原因
	 * 
	 * @param code Code1
	 * @param formMap Map
	 * 
	 * @return ActionMessages
	 */
	public ActionMessages modifyBonusPenaltyReasonCode(Code2 code, Map formMap);
	
	/**
	 * 新增獎懲原因
	 * @param formMap Map
	 * @return ActionMessages
	 */
	public ActionMessages createBonusPenaltyReasonCode(Map formMap);
	
	/**
	 * 刪除獎懲原因
	 * @param selCodes List<Code1>
	 * @param bundle ResourceBundle
	 * @return ActionMessages
	 */
	public ActionMessages delBonusPenaltyReasonCode(List<Code2> selCodes, ResourceBundle bundle);
	
	/**
	 * 將曠缺紀錄轉換至 DB Dilg_One,並註記是否扣考
	 * @param depart 部制
	 * @param DateEnd 曠缺資料結束日期
	 * @param pmode 0:不註記扣考, 1:註記扣考
	 * @return ActionMessages
	 */
	public ActionMessages timeOffDataExchange(String depart, String DateEnd, String pmode);
	
	/**
	 * 取得教師授課課程資料(包含導師負責班級之班會'50000'及系時間'T0001')
	 * @param credential UserCredential
	 * @return List<Dtimes> Dtimes:課程相關資料
	 */
	public List<Dtimes> findTeachSubjectByTeacherIdno(UserCredential credential);
	
	/**
	 *  取得教師授課課程資料 for 列印點名單(特別處理沒有排上課時間的課程)
	 * 
	 * @param credential UserCredential
	 * @return List<Dtimes> Dtimes:課程相關資料
	 */
	public List<Dtimes> findTeachSubject4TchClassbook(UserCredential credential);
	
	/**
	 * 取得該班級節次時間
	 * @param clazz 班級代碼
	 * @param weekd 星期幾
	 * @param period 第幾節
	 * @return Time[0]:開始時間, Time[1]:結束時間
	 */
	public Time[] getDtimeStamp(String clazz, int weekd, int period);
	
	/**
	 * 取得開課主檔資料
	 * @param dtOid Oid Of Dtime
	 * @return Dtime or null
	 */
	public Dtime getDtimeByOid(int dtOid);
	
	/**
	 * 取得課程時間表
	 * @param dtOid 開課主檔Oid
	 * @param iweek 星期:1~7, not(1~7): 全部
	 * @return List<DtimeClass>
	 */
	public List<DtimeClass> getDtimeClassByDtimeOid(int dtOid, int iweek);
	
	/**
	 * 教師上課點名之新增或修改 Dilg
	 * @param formMap DynaActionForm
	 * @return ActionMessages
	 */
	public Map createOrUpdateDilg4Tch(Map formMap);
	
	/**
	 * 查詢Dilg物件
	 * 
	 * @param dilg tw.edu.chit.models.Dilg Objects
	 * @return java.util.List List of tw.edu.chit.models.Dilg Objects
	 */
	public List<Dilg> findDilgBy(Dilg dilg);
	
	public List<StdOpinionSuggestion> findStdOpinionSuggestionsBy(
			StdOpinionSuggestion sos);
	
	public List<StdOpinionSuggestion> findStdOpinionSuggestionsBy(
			StdOpinionSuggestion sos, Date d) throws DataAccessException;
	
	/**
	 * 
	 * @param studCounseling
	 * @return
	 */
	public List<StudCounseling> findStudCounselingBy(
			StudCounseling studCounseling) throws DataAccessException;
	
	/**
	 * 儲存Dilg物件
	 * 
	 * @param dilg tw.edu.chit.models.Dilg Objects
	 */
	public void txSaveDilg(Dilg dilg);
	
	/**
	 * 新增或修改老師點名上傳紀錄
	 * @param teachId 教師身分證號
	 * @param dtOid 課程Oid
	 * @param tdate 課程日期
	 * @param modifierId 修改者Id
	 * @return ActionMessages
	 */
	public ActionMessages createOrUpdateTimeOffUpload(String teachId, int dtOid, String tdate,  String modifierId);
	
	/**
	 * 取得老師未點名詳細資料
	 * @param credential UserCredential
	 * @return Map
	 */
	public Map findTimeOffUploadRecord(UserCredential credential);
	
	/**
	 *取得學生曠缺家長通知單統計表資料(From CIS.Dilg_One table) 
	 * 
	 * @param DateStart 起始日期
	 * @param DateEnd 結束日期
	 * @param clazzFilter 搜尋班級
	 * @param threshold1 節數1
	 * @param threshold2 節數2
	 * @param threshold3 節數3
	 * @param pmode 操作模式：0:第一次列印(更新通知紀錄) 1:再次列印(不更新通知紀錄)
	 * @return List
	 */
	public List findDilgOneAlert4P(String DateStart, String DateEnd, String clazzFilter,
			String threshold1,String threshold2,String threshold3,String pmode);
	
	/**
	 *取得學生曠缺家長通知單統計表資料(From CIS.Dilg_One table) 
	 * 
	 * @param DateStart 起始日期
	 * @param DateEnd 結束日期
	 * @param clazzFilter 搜尋班級
	 * @param threshold1 節數1
	 * @param threshold2 節數2
	 * @param threshold3 節數3
	 * @param letterType1 寄件別1, 0:平信, 1:掛號
	 * @param letterType2 寄件別2
	 * @param letterType3 寄件別3
	 * @param pmode 操作模式：0:第一次列印(更新通知紀錄) 1:再次列印(不更新通知紀錄)
	 * @return List
	 */
	public List findDilgOneAlert4P(String DateStart, String DateEnd, String clazzFilter,
			String threshold1,String threshold2,String threshold3,String letterType1,
			String letterType2, String letterType3, String pmode);
	/**
	 * 取得老師未點名記錄
	 * @param clazzFilter 班級過濾
	 * @param teacherId 教師ID
	 * @param start 起始日期
	 * @param end 結束日期
	 * @param pmode 報表別 1:明細表, 2:統計表
	 * @param sortBy 排序: 0:依日期, 1:依老師身分證字號
	 * @return List<Map>
	 */
	public List<Map> getTeacherTimeOffNotUpload(String clazzFilter, String teacherId, Calendar start, Calendar end, String pmode, String sortBy);
	
	/**
	 * 依據 Dtime Oid 取得相關課程資料
	 * 
	 * @param dtOid : Dtime Oid
	 * 
	 * @return List<Dtimes>
	 */
	public List<Dtimes> findDtimesByDtimeOid(int dtOid);
	
	/**
	 * 取得學生曠缺資料(詳細版)
	 * @param studentNo 學號
	 * @param mode subject:科目, all:全部
	 * @return List
	 */
	public List getDilgByStudentNo(String studentNo, String mode);
	
	/**
	 * 取得該學期某課程之授課日期等點名資訊
	 * 
	 * @param myclass 開課班級
	 * @param cscode 課程代碼
	 * @return 授課點名資訊
	 */
	public List<DtimeStudaffair> getTermRollCallInfo(String myclass, String cscode);
	
	/**
	 * 更改不點名課程資訊
	 * 
	 * @param selDS 不點名課程資料
	 * @param idno 修改者身份證字號
	 * 
	 * @return 結果信息
	 */
	public ActionMessages txModifyRollCall(List<DtimeStudaffair> selDS, String idno);
	
	/**
	 * 取得不點名課程資訊
	 * 
	 * @param dtimeOid 課程Oid
	 * 
	 * @return 不點名課程資訊
	 */
	public List<DtimeStudaffair> getDtimeStudaffair(int dtimeOid);
	
	/**
	 * 取得輔導項目代碼
	 * 
	 * @param type 類別, T:職涯輔導 L:學習輔導
	 * 
	 * @return List<CounselingCode>
	 */
	public List<CounselingCode> findCounselingCode(String type);
	
	/**
	 * 取得輔導紀錄
	 * 
	 * @param type T:導師職涯輔導 U:導師學習輔導 L:老師學習輔導
	 * @param idno 身分證字號
	 * 
	 * @return 輔導紀錄
	 */
	public List<StudCounseling> findCounselingByIdno(String type, String idno);
	
	/**
	 * 依據學生學號取得輔導紀錄
	 * 
	 * @param studentNo 學生學號
	 * 
	 * @return 輔導紀錄
	 */
	public List<StudCounseling> findCounselingByStudentNo(String studentNo);
	
	/**
	 * 查詢輔導紀錄
	 * @param schoolYear: 學年(必須輸入,否則表示所有學年學期)
	 * @param schoolTerm: 學期
	 * @param type T:導師職涯輔導 U:導師學習輔導 L:老師學習輔導
	 * @param idno 身分證字號
	 * @param studentNo 學生學號
	 * @param departClass 班級代碼, 空白表示所有班級
	 * @param sCal 起始日期, null表示無起始日期
	 * @param eCal 結束日期, null表示無結束日期
	 * 
	 * @return 輔導紀錄
	 */
	public List<StudCounseling> findCounselingByInput(String schoolYear, String schoolTerm, String type, String idno, 
			String studentNo, String departClass, Calendar sCal, Calendar eCal);
	
	/**
	 * 查詢輔導統計報表資料
	 * @param schoolYear 學年
	 * @param schoolTerm 學期
	 * @param type 類別:T:導師輔導 L:老師學習輔導
	 * @param departClass 班級範圍
	 * @param teacherId 老師idno
	 * @return List<Map 統計報表資料
	 */
	public List<Map> findCounselingReport(String schoolYear, String schoolTerm, String type,  
			String departClass, String teacherId);
	
	/**
	 * 刪除輔導紀錄
	 * 
	 * @param counselList 輔導紀錄
	 * 
	 * @return error message
	 */
	public ActionMessages delStudCounselings(List<StudCounseling> counselList);
	
	/**
	 * 儲存輔導紀錄(新增)
	 * 
	 * @param aForm ActionForm 的資料
	 * @param idno 身分證字號
	 * @param ctype T:導師職涯輔導 U:導師學習輔導 L:老師學習輔導
	 * 
	 * @return result message
	 */
	public ActionMessages saveStudCounselingByForm(String idno, DynaActionForm aForm, String ctype);
	
	/**
	 * 儲存輔導紀錄(批次新增)
	 * @param idno 老師身分證字號
	 * @param students 學生們
	 * @param aForm ActionForm 的資料
	 * @param ctype  T:導師職涯輔導 U:導師學習輔導 L:老師學習輔導
	 * @return result message
	 */
	public ActionMessages saveStudCounselingBatchByForm(String idno, List<Student> students, 
			DynaActionForm aForm, String ctype);
	
	
	/**
	 * 儲存學習輔導紀錄(批次新增)
	 * @param idno 老師身分證字號
	 * @param stEcts 學生及課程相關資料
	 * @param aForm ActionForm 的資料
	 * @return result message
	 */
	public ActionMessages saveStudCounselingBatchLByForm(String idno, String stEcts, 
			DynaActionForm aForm);
	
	/**
	 * 儲存輔導紀錄(修改)
	 * 
	 * @param counsel 修改前的輔導紀錄
	 * @param aForm ActionForm 的資料
	 * 
	 * @return result message
	 */
	public ActionMessages saveStudCounselingModify(StudCounseling counsel, DynaActionForm aForm);
	
	/**
	 * 查詢導師或老師(任教班級)全班輔導紀錄
	 * 
	 * @param ctype T:導師職涯學習輔導(含T:職涯輔導, U:學習輔導) L:老師學習輔導
	 * @param credential UserCredential
	 * 
	 * @return Map{subject:班級課程相關資料, students:受輔導學生相關資料List<Map>}
	 */
	public List<Map> findCounselingStudentInClass(String ctype, UserCredential credential);
	
	/**
	 * 查詢學生請假單(學號)
	 * 
	 * @param studentNo 學號
	 * 
	 * @return 假單(含公假)
	 */
	public List<StudDocApply> findStudDocApplyByNo(String studentNo);
	
	/**
	 * 查詢學生請假單(假單Oid)
	 * @param docOid
	 * @return 假單
	 */
	public StudDocApply findStudDocApplyByOid(int docOid);
	
	/**
	 * 取得學生上課資訊
	 * 
	 * @param studentNo 學號
	 * @param startDate 起始日期
	 * @param endDate 結束日期
	 * @return Map{String studentNo, int oid, String date, int week, String cscode, String begin, String end}
	 */
	public List<Map> findSeldInPeriod(String studentNo, Calendar startDate, Calendar endDate);

	/**
	 * 依據請假單Oid取得學生請假單附加檔案
	 * @param oid 請假單Oid
	 * @return 附加檔案
	 */
	public List<StudDocUpload> getAbsenceDocAttach(int oid);
	
	/**
	 * 依據附加檔案的oid取得學生請假單附加檔案
	 * @param oid 附加檔案的oid
	 * @return StudDocUpload附加檔案
	 */
	public StudDocUpload getAbsenceDocUpload(int oid);
	
	/**
	 * 刪除學生請假單附加檔案
	 * @param upload 附加檔案
	 */
	public void delAbsenceDocAttach(StudDocUpload upload);
	
	/**
	 * 儲存新增或修改的學生假單
	 * 
	 * @param form DynaActionForm
	 * @param days 請假日數
	 * 
	 * @return 處理訊息
	 * @throws Exception 
	 */
	public ActionMessages saveStudAbsenceApplyByForm(DynaActionForm form, int days) throws Exception;
	
	/**
	 * 刪除學生假單(只是將status設為D,並未真正刪除該筆資料)
	 * 
	 * @param selDocs 欲刪除的假單
	 * 
	 * @return 無法刪除的假單
	 */
	public List<StudDocApply> delStudDocApply(List<StudDocApply> selDocs);
	
	/**
	 * 取得學生假單最後的處理狀態
	 * @param docOid 假單的Oid
	 * @return 最後的處理狀態
	 */
	public StudDocExamine getStudDocLastExamine(int docOid);
	
	/**
	 * 取得學生假單的處理狀態(依照線上簽核日期由近而遠排序)
	 * @param docOid 假單的Oid
	 * @return 處理狀態
	 */
	public List<StudDocExamine> getStudDocExamines(int docOid);
	
	/**
	 * 依據處理狀態碼,取得狀態名稱
	 * @param statusCode 狀態碼
	 * @return 狀態名稱
	 */
	public String getExamineStatusName(String statusCode);

	/**
	 * 取得學生請假日期節次詳細資料
	 * 
	 * @param studentNo 學號
	 * @param dDate 日期(yyyy-mm-dd)
	 * @return 請假節次資料
	 */
	public List<StudDocDetail> getStudDocDetailByDate(String studentNo,
			String dDate);
	
	/**
	 * 檢查新增或修改假單時是否有請假日期節次重疊的假單
	 * 
	 * @param studentNo 學號
	 * @param docds 籲請假之日期節次資料(以 List<StudDocDetail> 封裝)
	 * @param oid 修改時要忽略原來的假單oid(新增時設定為0)
	 * 
	 * @return 是否有請假日期節次重疊(Map(String:msg, boolean:isDupl))
	 */
	public Map checkAbsenceApplyDuplicate(String studentNo, List<StudDocDetail> docds, int oid);
	
	/**
	 * 取得該學生日期區間內的所有假單(包含註記為已刪除的假單)
	 * @param studentNo 學號
	 * @param startDate 起始日期
	 * @param endDate 結束日期
	 * @return 假單
	 */
	public List<StudDocApply> getAbsenceApplyByPeriod(String studentNo, Date startDate, Date endDate);
	
	/**
	 * 取得該學生指定日期節次的請假節次詳細資料
	 * 
	 * @param studentNo 學號
	 * @param adate 日期
	 * @param period 節次 0:表示當日所有節次詳細資料
	 * @para incDel 包含已刪除的假單 true:包含, false:排除
	 * @return 請假節次詳細資料
	 */
	public List<StudDocDetail> getAbsenceDocDetailBy(String studentNo, Date adate, short period, boolean incDel);
	
	/**
	 * 學生假單送核
	 * @param doc 假單
	 * @return ActionMessages 信息
	 */
	public ActionMessages setStudDoc4Examine(StudDocApply doc);
	
	
	/**
	 * 學生假單取消送核
	 * @param doc 假單
	 * @return ActionMessages 信息
	 */
	public ActionMessages resetStudDoc4Examine(StudDocApply doc);
	
	/**
	 * 依班級與審核規則取得應審核的假單資料
	 * 
	 * @param classNo 班級代號
	 * @param examMode 審核規則
	 * 
	 * @return 應審核的假單資料
	 */
	public List<StudDocApply> getAbsenceApply4ExamByClazz(String classNo, String examMode);
	
	
	/**
	 * 取得假單最後一次送出後的審核狀態
	 * 
	 * @param docOid 假單Oid
	 * 
	 * @return 審核狀態資料
	 */
	public List<StudDocExamine> getStudDocExamNearest(int docOid);
	
	/**
	 * 依審核規則Oid取得規則資料
	 * 
	 * @param myOid 審核規則Oid
	 * 
	 * @return 審核規則
	 */
	public ExamineRule getExamRuleByOid(int myOid);
	
	/**
	 * 依審核規則編號取得規則資料
	 * 
	 * @param ruleNo 編號
	 * 
	 * @return 審核規則
	 */
	public ExamineRule getExamRuleByRuleNo(String ruleNo);
	
	/**
	 * 依據驗證編號取得驗證序列(經由 parent oid 取得父驗證,一直到 parent oid等於null)
	 * @param ruleNo 驗證編號
	 * @return 驗證序列(包含自己)
	 */
	public List<ExamineRule> getExamRuleSequence(String ruleNo);
	
	/**
	 * 檢查假單是否可以執行此審查規則
	 * @param doc 假單
	 * @param ruleNo 審查規則
	 * @return 是否可以執行
	 */
	public boolean checkIsInExamStep(StudDocApply doc, String ruleNo);
	
	/**
	 * 依據審核規則及(或)審核人身分證號取得審核資料
	 * 
	 * @param ruleNo 審核規則
	 * @param idno 審核人身分證號
	 * 
	 * @return 審核資料
	 */
	public List<StudDocApply> getAbsenceApplyExamedByRule(String ruleNo, String idno);
	
	/**
	 * 設定假單審核狀態
	 * @param doc 假單
	 * @param ruleNo 正在進行的審核規則編號
	 * @param status 審核結果
	 * @param description 說明
	 * @param idno 審核者身分證號
	 * @return 設定審核結果訊息
	 */
	public ActionMessages setStudDocExamStatus(StudDocApply doc, String ruleNo, 
			String status, String description, String idno);
	
	/**
	 * 依公假單Oid取得公假單資料
	 * @param docOid 公假單Oid
	 * @return 公假單資料
	 */
	public StudPublicLeave findStudPublicLeaveByOid(int docOid);
	
	/**
	 * 取得公假單資料,依申請人ID
	 * @param idno 申請人ID
	 * @return 公假單資料
	 */
	public List<StudPublicLeave> findStudPublicLeavesByIdno(String idno);
	
	/**
	 * 取得公假單最後的審核狀態
	 * @param docOid 公假單oid
	 * @return 公假單最後的審核狀態
	 */
	public StudPublicDocExam getStudPublicDocLastExam(int docOid);
	
	/**
	 * 取得公假單上傳附加檔案
	 * @param oid 公假單oid
	 * @return 附加檔案
	 */
	public List<StudPublicDocUpload> getPublicDocAttach(int oid);
	
	/**
	 * 依據附加檔案的oid取得公假單附加檔案
	 * @param oid 附加檔案的oid
	 * @return StudDocUpload附加檔案
	 */
	public StudPublicDocUpload getPublicDocUpload(int oid);
	
	/**
	 * 刪除公假單附加檔 
	 * @param upload 附加檔
	 */
	public void delPublicDocAttach(StudPublicDocUpload upload);
	
	/**
	 * 儲存新增或修改公假單
	 * 
	 * @param idno 填寫者id
	 * @param form 
	 * @param days 公假日數
	 * @return 訊息
	 * @throws Exception
	 */
	public ActionMessages saveStudPublicDocApplyByForm(String idno, DynaActionForm form, int days) throws Exception;

	/**
	 * 依據公假單Oid取得請學生假單
	 * @param docOid 公假單Oid
	 * @return 學生假單
	 */
	public List<StudDocApply> getPublicDocStuds(int docOid);
	
	/**
	 * 刪除指定的公假單及其相關的學生假單紀錄(僅註記status=D,沒有真的刪除)
	 * @param selDocs 公假單
	 * @return 無法刪除的公假單
	 */
	public List<StudPublicLeave> delStudPublicDocApply(List<StudPublicLeave> selDocs);
	
	/**
	 * 公假單送核
	 * @param doc 公假單
	 * @return 訊息
	 */
	public ActionMessages setStudPublicDoc4Examine(StudPublicLeave doc);
	
	/**
	 * 取消公假單送核
	 * @param doc 公假單
	 * @return 訊息
	 */
	public ActionMessages resetStudPublicDoc4Examine(StudPublicLeave doc);
	
	/**
	 * 依班級與審核規則取得應審查的公假單
	 * 
	 * @param classNo 班級代號
	 * @param examMode 審核規則
	 * 
	 * @return 應審查的公假單
	 */
	public List<StudPublicLeave> getPublicDoc4ExamByClazz(String classNo, String examMode);
	
	/**
	 * 檢查公假單可否執行該審查規則步驟
	 * 
	 * @param doc 公假單
 	 * @param ruleNo 審查規則
 	 * 
	 * @return 可否執行審查
	 */
	public boolean checkIsInPublicExamStep(StudPublicLeave doc, String ruleNo);
	
	/**
	 * 取得該公假單最後一次送出後之各審查步驟狀態
	 * 
	 * @param docOid 公假單Oid
	 * @return 審查狀態
	 */
	public List<StudPublicDocExam> getStudPublicDocExamNearest(int docOid);
	
	/**
	 * 取得該公假單所有審查步驟狀態
	 * @param docOid 公假單Oid
	 * @return 審查狀態
	 */
	public List<StudPublicDocExam> getStudPublicDocExamines(int docOid);
	
	/**
	 * 查詢系主任或系助理之負責系所
	 * 
	 * @param empOid 教職員Oid
	 * 
	 * @return List<Map> {name, idno}
	 */
	public List<Map> findDeptInCharge(int empOid);
	
	/**
	 * 依審核規則,取得個人審核過的公假單
	 * 
	 * @param ruleNo 審核規則
	 * @param idno 身分id
	 * 
	 * @return 審核過的公假單
	 */
	public List<StudPublicLeave> getPublicDocExamedByRule(String ruleNo, String idno);
	
	/**
	 * 設定公假單審核狀態
	 * 
	 * @param doc 公假單
	 * @param ruleNo 審核規則
	 * @param status 欲設定之審查狀態
	 * @param description 審查結果說明
	 * @param idno 審核者身分id
	 * 
	 * @return 審核結果
	 */
	public ActionMessages setStudPublicDocExamStatus(StudPublicLeave doc, String ruleNo, String status, String description, String idno);
	
	/**
	 * 取得導師未審核假單列表
	 * @param clazzFilter 班級
	 * @param teacherId 導師id,空白:全部導師
	 * @param mystart 起始日期(假單送出)
	 * @param myend 結束日期
	 * @param pmode 1:明細表, 2:統計表
	 * @return 未審核假單資料
	 */
	public List<Map> getAbsenceApplyNotConfirm(String clazzFilter, String teacherId, Calendar mystart, Calendar myend, String pmode);
	
	/**
	 * For 教育部獎懲人數統計
	 * @param schoolYear 學年
	 * @param schoolTerm 學期
	 * @return List<int[][]> int[0]:台北日間, int[1]:台北夜間, int[2]:新竹日間, int[3]:新竹夜間
	 */
	public List<int[][]> findBonusPenalty4edu(String schoolYear, String schoolTerm);
}

