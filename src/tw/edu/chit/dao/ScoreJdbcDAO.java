package tw.edu.chit.dao;

import java.util.Date;
import java.util.List;

import tw.edu.chit.model.Student;
import tw.edu.chit.util.Toolket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class ScoreJdbcDAO extends JdbcDaoSupport {
	private static Log log = LogFactory.getLog(MemberJdbcDAO.class);

	public List findScoreHistByStudentno(String name, Integer orgOid, int positionOid) {

		return getJdbcTemplate().queryForList(
				"select u.Name as UnitName, u.Grade as UnitGrade from Member m, Memberunit mu, Unit u, Memberrole mr, Role r "
				+ "where mu.memberoid = m.oid"
				+ " and  mu.unitoid   = u.oid"
				+ " and  u.organizationoid = ?"
				+ " and  mr.memberoid = m.oid"
				+ " and  mr.roleoid   = r.oid"
				+ " and  r.positionoid = " + positionOid
				+ " and  m.name = ?",
				new Object[] {orgOid, name});
	}
	
	public List findAnyThing(String sql) {
		return getJdbcTemplate().queryForList(sql);
	}

	public List<Student> findStudentsByStudentNO(String student_no) {
		return getJdbcTemplate().queryForList("select * from stmd s where student_no = ?", new Object[] {student_no});
	}
	
	public String findClassNameByCode(String deptclass) {
		return (String)getJdbcTemplate().queryForObject("select c.ClassName from class where c.ClassNo = ?",new Object[] {deptclass} , String.class);
	}
	
	public int getScoreHistCount(String studentNo, String cscode, String schoolYear, String schoolTerm) {
		return getJdbcTemplate().queryForInt(
				"select count(*) from ScoreHist sc "
				+ "where sc.student_no = ? and cscode = ? and school_year = ? "
				+"and school_term = ?",
				new Object[] {studentNo, cscode, schoolYear, schoolTerm} , 
				new int[] {java.sql.Types.CHAR,java.sql.Types.CHAR,java.sql.Types.CHAR,java.sql.Types.CHAR});
	}
	
	public int getScoreHistAvgCount(String studentNo, String schoolYear, String schoolTerm) {
		return getJdbcTemplate().queryForInt(
				"select count(*) from Stavg sc "
				+ "where sc.student_no = ? and school_year = ? "
				+"and school_term = ?",
				new Object[] {studentNo, schoolYear, schoolTerm} , 
				new int[] {java.sql.Types.CHAR,java.sql.Types.CHAR,java.sql.Types.CHAR});
	}
	
	public int updateAnyThing(String sql) {
		return getJdbcTemplate().update(sql);
	}
	
	public int updateSeld (String scoretype, double score, long oid, String studentNo) {
		String prestatement = "";
		Object[] obj = {score, oid, studentNo};
		
		if(scoretype.equals("1"))
			prestatement = "Update Seld set score2=? Where Dtime_oid=? and student_no=?";
		else if (scoretype.equals("2")||scoretype.equals("3"))
			//注意:直接更動學期成績而不是期末成績
			//教師上傳成績不會用到這個功能
			prestatement = "Update Seld set score=? Where Dtime_oid=? and student_no=?";
		else return -1;
		
		return getJdbcTemplate().update(prestatement, obj);
	}

	/*
	 * 修改Regs資料
	 * @param String scoretype ; 1:期中   2:學期   3:期末
	 * @param double score
	 * @param long oid ;  Dtime_oid
	 * @param String studentNo
	 */
	public int updateRegs (String scoretype, double score, long oid, String studentNo) {
		String prestatement = "";
		double percent_score = 0;
		if(scoretype.equals("1")) {
			percent_score = Math.round(score * 0.3 * 10) / 10.0;
		}
		else if(scoretype.equals("3")) {
			percent_score = Math.round(score * 0.4 * 10) / 10.0;
		}
		
		
		if(scoretype.equals("1")) {
			Object[] obj = {score, percent_score, oid, studentNo};
			prestatement = "Update Regs set score19=?, score20=? Where Dtime_oid=? and student_no=?";
			return getJdbcTemplate().update(prestatement, obj);
		}
		else if (scoretype.equals("2")) {
			Object[] obj = {score, oid, studentNo};
			prestatement = "Update Regs set score23=? Where Dtime_oid=? and student_no=?";
			return getJdbcTemplate().update(prestatement, obj);
		}
		else if (scoretype.equals("3")){
			Object[] obj = {score, percent_score, oid, studentNo};
			prestatement = "Update Regs set score21=?, score22=? Where Dtime_oid=? and student_no=?";
			return getJdbcTemplate().update(prestatement, obj);
		} else return -1;

	}
	
	public int updateRegstime(int dtimeoid, String departClass, String cscode, String teacherId, String scoretype, String now) {
		String prestatement = "";
		Object[] obj = {dtimeoid, now, teacherId, departClass, cscode,  scoretype};
		
		prestatement = "Update Regstime set dtime_oid=?, ttime=?, idno=?  Where depart_class=? and cscode=? and ind=?";
		
		return getJdbcTemplate().update(prestatement, obj);
		
	}

	
}
