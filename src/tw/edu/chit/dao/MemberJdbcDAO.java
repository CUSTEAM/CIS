package tw.edu.chit.dao;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import tw.edu.chit.model.ClassCadre;
import tw.edu.chit.model.Empl;

public class MemberJdbcDAO extends JdbcDaoSupport {
	
	/*
	public List findRolesByMember(Integer memberOid) {
		return getJdbcTemplate().queryForList(
				"SELECT RoleNo FROM Empl_Role WHERE EmpOid = ?",
				new Object[] {memberOid},
				String.class);
	}
	*/
	public int getLibboroCountByReaderNo(Object readerNo) {
		return getJdbcTemplate().queryForInt(
				" SELECT COUNT(*) FROM libboro "
				+ "WHERE reader_no = ? AND rdate IS NULL",
				new Object[] {readerNo},
				new int[] {java.sql.Types.CHAR});
	}
	
	/* Moved to MemberDAO to participate in Spring-managed transaction
	public void removeWWPassByUsername(String username) {
		getJdbcTemplate().update(
				"delete from wwpass where username = ?",
				new Object[] {username},
				new int[] {java.sql.Types.CHAR});
	}
	*/
	
	/* Moved to MemberDAO to participate in Spring-managed transaction
	public void updateWWPassUsername(String oldUsername, String newUsername) {
		getJdbcTemplate().update(
				"update wwpass set username = ? where username = ?",
				new Object[] {newUsername, oldUsername},
				new int[] {java.sql.Types.CHAR, java.sql.Types.CHAR});		
	}
	*/
	
	/* Moved to MemberDAO to participate in Spring-managed transaction
	public void updateWWPassPassword(String username, String password) {
		getJdbcTemplate().update(
				"update wwpass set password = ? where username = ?",
				new Object[] {password, username},
				new int[] {java.sql.Types.CHAR, java.sql.Types.CHAR});		
	}
	*/
	
	/* Moved to MemberDAO to participate in Spring-managed transaction
	public void createWWPass(String username, String password, String priority) {
		getJdbcTemplate().update(
				"insert into wwpass (username, password, priority) values (?, ?, ?)",
				new Object[] {username, password, priority},
				new int[] {java.sql.Types.CHAR, java.sql.Types.CHAR, java.sql.Types.CHAR});				
	}
	*/
	
	public int getScoreHistCountByStudentNo(Object studentNo) {
		return getJdbcTemplate().queryForInt(
				" SELECT COUNT(*) FROM ScoreHist "
				+ "WHERE student_no = ?",
				new Object[] {studentNo},
				new int[] {java.sql.Types.CHAR});
	}

	public int getSeldCountByStudentNo(String studentNo) {
		return getJdbcTemplate().queryForInt(
				" SELECT COUNT(*) FROM Seld "
				+ "WHERE student_no = ?",
				new Object[] {studentNo},
				new int[] {java.sql.Types.CHAR});
	}	
	
	public int getStudentCountByStudentNo(String studentNo) {
		return getJdbcTemplate().queryForInt(
				" SELECT COUNT(*) FROM stmd "
				+ "WHERE student_no = ?",
				new Object[] {studentNo},
				new int[] {java.sql.Types.CHAR});
	}
	
	public int getGraduateCountByStudentNo(String studentNo) {
		return getJdbcTemplate().queryForInt(
				" SELECT COUNT(*) FROM Gstmd "
				+ "WHERE student_no = ?",
				new Object[] {studentNo},
				new int[] {java.sql.Types.CHAR});
	}

	public int getEmplCountByIdno(String idno) {
		return getJdbcTemplate().queryForInt(
				" SELECT COUNT(*) FROM empl "
				+ "WHERE idno = ?",
				new Object[] {idno},
				new int[] {java.sql.Types.CHAR});
	}
	
	public int getDEmplCountByIdno(String idno) {
		return getJdbcTemplate().queryForInt(
				" SELECT COUNT(*) FROM dempl "
				+ "WHERE idno = ?",
				new Object[] {idno},
				new int[] {java.sql.Types.CHAR});
	}
	
	/**
	 * 實作以Empl Oid與班級代碼取得ClassCadre清單
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 * @param classNo 班級代碼
	 * @return java.util.List List of ClassCadre objects
	 */
	@SuppressWarnings("unchecked")
	public List<ClassCadre> findClassCadreByClassNo(Empl empl, String classNo) {
		String hql = "from ClassCadre cc where cc.parentOid = ? and cc.classNo = ?";
		return getJdbcTemplate().queryForList(hql,
				new Object[] { empl.getOid(), classNo },
				new int[] { java.sql.Types.INTEGER, java.sql.Types.VARCHAR });
	}
	
	/* Moved to MemberDAO to participate in Spring-managed transaction
	public void updateStudentDiviByClass(String classNo, String groupId) {
		getJdbcTemplate().execute(
				" UPDATE stmd SET divi = '" + groupId + "' "
				+ "WHERE depart_class  = '" + classNo + "'");
	}
	*/
	
	/* Moved to MemberDAO to participate in Spring-managed transaction
	public void deleteClassInChargeByEmpOid(Integer empOid) {
		getJdbcTemplate().execute(
				" DELETE FROM ClassInCharge "
				+ "WHERE EmpOid = " + empOid.toString());
	}
	*/
	
	/* Moved to MemberDAO to accompany a transaction
	public void deleteUnitBelongByEmpOidUnitNo(Integer empOid, String unitNo) {
		getJdbcTemplate().execute(
				" DELETE FROM UnitBelong "
				+ "WHERE EmpOid = " + empOid.toString()
				+ " AND  UnitNo = '" + unitNo + "'");
		
	}
	*/
}
