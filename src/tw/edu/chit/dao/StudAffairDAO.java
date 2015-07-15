package tw.edu.chit.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.model.Code2;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.Desd;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Dipost;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.StdOpinionSuggestion;
import tw.edu.chit.model.StudCounseling;
import tw.edu.chit.model.StudDocUpload;
import tw.edu.chit.model.StudPublicDocUpload;

public interface StudAffairDAO extends DAO {
	
	@SuppressWarnings("unchecked")
	public List submitQuery(String hql);
	
	@SuppressWarnings("unchecked")
	public List submitQuery(String hql, Object[] parameters);
	
	public void removeDilg(Dilg dilg);
	
	public void removeDesd(Desd desd);
	
	public void removeJust(Just just);
	
	public int removeAnyThing(String hql);
	
	public List<Dilg> findDilgByStudentNo(String studentNo);
	
	/**
	 * 取得由sterm指定學期之學生選課資料
	 * 
	 * @param studentNo 學號
	 * @param sterm 學期
	 * @return List<Seld>
	 */
	
	public List<Seld> findSeldByStudentNo(String studentNo, String sterm);
	
	public List<Seld> findSeldByStudentNoAndCscode(String studentNo,
			String cscode, String sterm);
	
	public Csno findNameOfCourse(String cscode);
	
	/**
	 * 取得獎懲原因
	 * 
	 * @return List<Code2>
	 */
	public List<Code2> findCode2List(String code);
	
	/**
	 * 查詢轉帳資料
	 * 
	 * @param dipost tw.edu.chit.models.Dipost Object
	 * @return java.util.List List of tw.edu.chit.models.Dipost Objects
	 */
	public List<Dipost> getDipostsBy(Dipost dipost);
	
	/**
	 * 查詢Dilg物件
	 * 
	 * @param dilg tw.edu.chit.models.Dilg Objects
	 * @return java.util.List List of tw.edu.chit.models.Dilg Objects
	 */
	public List<Dilg> getDilgBy(Dilg dilg);
	
	/**
	 * 
	 * @param studCounseling
	 * @return
	 */
	public List<StudCounseling> getStudCounselingBy(
			StudCounseling studCounseling) throws DataAccessException;
	
	public List<StdOpinionSuggestion> getStdOpinionSuggestionsBy(
			StdOpinionSuggestion sos) throws DataAccessException;
	
	public List<StdOpinionSuggestion> getStdOpinionSuggestionsBy(
			StdOpinionSuggestion sos, Date d) throws DataAccessException;
	
	public Session getHibernateSession();
	
	/**
	 * 取得 CISBLOB 資料庫的 Hibernate Session,使用完畢後一定要呼叫 closeCISBLOBSession
	 * @param resources 在 tw/edu/chit/model/的hbm.xml定義檔完整路徑
	 * @return Hibernate Session
	 */
	public Session getCISBLOBSession(String[] resources);
	
	/**
	 * 關閉 CISBLOB 資料庫的 Hibernate Session
	 * @param session
	 */
	public void closeCISBLOBSession(Session session);
	
	/**
	 * 儲存或更新StudDocUpload(學生請假單附檔)
	 * @param upload StudDocUpload(學生請假單附檔)
	 * @throws Exception 
	 */
	public void saveOrUpdateStudDocUpload(StudDocUpload upload) throws Exception;
	
	/**
	 * 刪除StudDocUpload(學生請假單附檔)
	 * @param upload 學生請假單附檔
	 * @throws Exception
	 */
	 public void removeStudDocUpload(StudDocUpload upload) throws Exception;
	 
	 /**
	  * 刪除StudPublicDocUpload(公假單附檔)
	  * @param upload 公假單附檔
	  * @throws Exception
	  */
	 public void removeStudPublicDocUpload(StudPublicDocUpload upload) throws Exception;
}
