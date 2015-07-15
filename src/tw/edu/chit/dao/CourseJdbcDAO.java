package tw.edu.chit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class CourseJdbcDAO extends JdbcDaoSupport {

	/**
	 * @param sql
	 * @return 標準SQL查詢結果
	 */
	@SuppressWarnings("unchecked")
	public List StandardSqlQuery(String sql) {

		return getJdbcTemplate().queryForList(sql);
	}

	@SuppressWarnings("unchecked")
	public List StandardSqlQuery(String sql, Object[] args) {
		return getJdbcTemplate().queryForList(sql, args);
	}

	public void StandardSqlRemove(String sql) {
		getJdbcTemplate().execute(sql);
	}

	public void removeDtimeClass(String sql) {
		getJdbcTemplate().execute(sql);
	}

	public void removeDtimeExam(String sql) {
		getJdbcTemplate().execute(sql);
	}
	
	public void delAdcdByStudentTerm(String studentNo, String term) {
		
		getJdbcTemplate().update(
				" DELETE a.* FROM AddDelCourseData a, Dtime d "
				+ "WHERE a.Dtime_oid = d.Oid"
				+ "  AND d.Sterm = ?"
				+ "  AND a.Student_no = ?", new Object[] {term, studentNo});
	}
	
	public int StandardSqlQueryForInt(String sql) {
		return getJdbcTemplate().queryForInt(sql);
	}
	
	public boolean isSeldExistingByDtimeStudent(Integer dtimeOid, String studentNo) {
		return getJdbcTemplate().queryForInt(
				"SELECT COUNT(*) FROM Seld s WHERE s.Dtime_oid = ? AND s.student_no = ?",
				new Object[] {dtimeOid, studentNo}) > 0;
	}
	
	public boolean isRegsExistingByDtimeStudent(Integer dtimeOid, String studentNo) {
		return getJdbcTemplate().queryForInt(
				"SELECT COUNT(*) FROM Regs s WHERE s.Dtime_oid = ? AND s.student_no = ?",
				new Object[] {dtimeOid, studentNo}) > 0;
	}
	
	public void removeAdcdByDtimeStudent(Integer dtimeOid, String studentNo) {
		getJdbcTemplate().update("DELETE FROM AddDelCourseData "
							   	+ "WHERE Dtime_oid = ? "
							   	+ "  AND Student_no = ?",
							   	new Object[] {dtimeOid, studentNo});
	}
	
	/**
	 * 處理以學號查詢學生已選課資訊
	 * 
	 * @param studentNo 學號
	 * @param term 學期
	 * @return java.util.List List of Objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getSeldDataByStudentNo(String studentNo, String term) {
		String hql = "SELECT c.classNo, c.className, cs.cscode, "
				+ "cs.chi_Name, d.stu_Select, d.select_Limit, "
				+ "d.thour, d.credit, s.oid AS soid, d.opt, d.Oid, d.sterm, "
				+ "dc.week, dc.begin, dc.end, d.elearning, cs.literacyType "
				+ "FROM Seld s, Class c, Csno cs, Dtime d "
				+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.dtime_Oid "
				+ "WHERE s.dtime_Oid = d.Oid "
				+ "AND d.depart_Class = c.classNo "
				+ "AND d.cscode = cs.cscode AND d.cscode NOT IN ('T0001', '50000') "
				+ "AND s.student_No = ? "
				+ "AND d.sterm = ? ORDER BY d.Oid, dc.week";
		return StandardSqlQuery(hql, new Object[] { studentNo, term });
	}
	
	/**
	 * 處理以學號查詢學生已選課資訊
	 * 
	 * @comment 不包括Dtime_class資料
	 * @param studentNo 學號
	 * @param term 學期
	 * @return java.util.List List of Objects
	 */
	@SuppressWarnings("unchecked")
	public List getSeldDataByStudentNo1(String studentNo, String term) {
		String hql = "SELECT c.classNo, c.className, cs.cscode, "
				+ "cs.chi_Name, d.stu_Select, d.select_Limit, "
				+ "d.thour, d.credit, s.oid AS soid, d.opt, d.Oid, d.sterm, "
				+ "d.elearning FROM Seld s, Dtime d, Class c, Csno cs "
				+ "WHERE s.dtime_Oid = d.Oid "
				+ "AND d.depart_Class = c.classNo "
				+ "AND d.cscode = cs.cscode AND s.student_No = ? "
				+ "AND d.sterm = ? ORDER BY d.opt";
		return StandardSqlQuery(hql, new Object[] { studentNo, term });
	}
	
	public void executeSql(String sql){
		getJdbcTemplate().execute(sql);
	}
	
	/**
	 * 隨便拿一筆資料
	 * @param sql
	 * @return 一筆資料
	 */
	@SuppressWarnings("unchecked")
	public Map ezGetMap(String sql){		
		return getJdbcTemplate().queryForMap(sql);
	}
	
	/**
	 * 隨便拿一個字段
	 * @param sql
	 * @return
	 */
	public String ezGetString(String sql){
		try{
			return (String)getJdbcTemplate().queryForObject(sql, java.lang.String.class);
		}catch(org.springframework.dao.IncorrectResultSizeDataAccessException e){
			return "";
		}
	}
	
}
