package tw.edu.chit.service;

import java.util.List;
import java.util.Map;

public interface SummerManager {

	/**
	 * 定義課程查詢
	 */
	public List getSdtimeBy(String seqno, String opt, String departClass, String cscode, String techId, String status);

	/**
	 * 定義暑修日程查詢
	 */
	public List getSweekBy(String daynite, String seqno, String wdate);

	/**
	 * 定義取得梯次資訊
	 */
	public List getSsterm();

	/**
	 * 定義隨便殺
	 */
	public void delAnything(String tableName, String Oid);

	/**
	 * 定義隨便存
	 */
	public void saveObj(Object po);

	/**
	 * 定義查詢缺曠的方法
	 */
	public List getSdilgBy(String seqno, String csDepartClass, String stDepartClass, String studentNo, String ddate, String daynite, String cscode);

	/**
	 * JDBC隨便查
	 */
	public List ezGetList(String sql);

	/**
	 * JDBC隨便查
	 */
	public Integer ezGetInt(String sql);
	
	/**
	 * 隨便殺
	 */
	public void removeObject(String table, String oid);
	
	/**
	 * 隨便改
	 */
	public void saveOrUpdate(String sql);
	
	/**
	 * 取學生缺曠
	 */
	public Integer getSdilgBy(String studentNo, String csdepartClass, String cscode, String seqno);
	
	/**
	 * 取曠課型態
	 */
	public String getTimeOffType(String abs);
	
	public Integer getSdilgsBy(String studentNo, String csdepartClass, String cscode, String seqno);
	
	public List getScoreBy(String seqno, String departClass, String cscode);
	
	public List hqlGetList(String hql);
	
	boolean checkReOptionForSummer(String studentNo, String sDtimeOid);
	
	public Map checkReOption4Summer(String studentNo, String sDtimeOid);
}
