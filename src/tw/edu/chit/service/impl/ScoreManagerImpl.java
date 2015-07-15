package tw.edu.chit.service.impl;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.dao.ScoreJdbcDAO;
import tw.edu.chit.model.ClassScoreSummary;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeTeacher;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.FailStudents1;
import tw.edu.chit.model.FailStudents2;
import tw.edu.chit.model.FailStudentsHist;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.MasterData;
import tw.edu.chit.model.MidtermExcluded;
import tw.edu.chit.model.Optime1;
import tw.edu.chit.model.Regs;
import tw.edu.chit.model.Regstime;
import tw.edu.chit.model.Rrate;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.ScoreStatistic;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Stavg;
import tw.edu.chit.model.StdScore;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class ScoreManagerImpl extends BaseManager implements ScoreManager {

	private MemberDAO memberDao;

	private ScoreDAO dao;

	private ScoreJdbcDAO jdbcDao;

	private List<Map> scoreStatusList = new ArrayList();
		
	/**
	 * Set the DAO for communication with the data layer.
	 * 
	 * @param dao
	 */
	public void setScoreDAO(ScoreDAO dao) {
		this.dao = dao;
	}

	public void setJdbcDAO(ScoreJdbcDAO dao) {
		this.jdbcDao = dao;
	}

	public void setMemberDAO(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}

	public void setRunStatus(String methodName, String step, int rcount, int total, double percentage, String isComplete) {
		Map runstatus = new HashMap();
		runstatus.put("methodName", methodName);
		runstatus.put("step", step);
		runstatus.put("rcount", rcount);
		runstatus.put("total", total);
		runstatus.put("percentage", percentage);
		runstatus.put("complete", isComplete);
		//log.debug("SetRunningStatus->methodName:" + methodName + ", step:" + step + ", percentage:" + percentage);
		Map newMap;
		if(scoreStatusList.size() > 0) {
			for(Iterator statIter = scoreStatusList.listIterator(); statIter.hasNext();) {
				newMap = (Map)statIter.next();
				if(newMap.get("methodName").toString().equals(methodName)) {
					//log.debug("remove List element:" + newMap.get("step"));
					statIter.remove();
				}
			}
		}
		scoreStatusList.add(runstatus);
		//log.debug("after remove List element size:" + scoreStatusList.size());
	}
	
	public List chkstatus() {
		//log.debug("chkstatus List size:" + scoreStatusList.size());
		return scoreStatusList;
	}
	
	public List<ScoreHist> findScoreHistByStudentInfoForm(Map formProperties,
			String classInCharge) {

		String student_no = (String) formProperties.get("student_no");
		// String student_name = (String)formProperties.get("student_name");
		String school_year = (String) formProperties.get("school_year");
		String school_term = (String) formProperties.get("school_term");

		log.debug("=======> studno='" + student_no + "'");
		// log.debug("=======> name='" + student_name + "'");
		log.debug("=======> year='" + school_year + "'");
		log.debug("=======> term='" + school_term + "'");

		String hql = "select sc from ScoreHist sc Where ";

		if (!"".equals(student_no)) {
			hql += "sc.studentNo = '" + student_no + "' ";
		} else {
			return null;
		}

		if (!"".equals(school_year)) {
			hql += "and sc.schoolYear = '" + school_year + "' ";
		}
		if (!"".equals(school_term)) {
			hql += "and sc.schoolTerm = '" + school_term + "' ";
		}

		log.debug("=======> hql='" + hql + "'");

		/*
		 * mutilple return object from HQL String hql = "select sc,
		 * s.studentName As stuName, s.departClass from ScoreHist sc, Student s
		 * where ";
		 * 
		 * if (!"".equals(student_no) ) { hql += "sc.studentNo = '" + student_no + "' "; }
		 * 
		 * if (!"".equals(school_year)) { hql += "and sc.schoolYear = '" +
		 * school_year + "' "; } if (!"".equals(school_term)) { hql += "and
		 * sc.schoolTerm = '" + school_term + "' "; } hql += "and sc.studentNo =
		 * s.studentNo "; hql += " and s.departClass in " + classInCharge;
		 * 
		 * log.debug("=======> hql='" + hql + "'");
		 * 
		 * ScoreHist score; List sc = dao.submitQuery(hql); List tmplist;
		 * Object[] scArray; List<ScoreHist> scorelist = new ArrayList();
		 * Toolket.refreshCourseOptMap(); Toolket.refreshCourseTypeMap();
		 * 
		 * for (Iterator scIter = sc.iterator(); scIter.hasNext();) {
		 * 
		 * scArray = (Object[])scIter.next(); // log.debug("=======>
		 * ObjArrayBound='" + scArray.length + "'"); score =
		 * (ScoreHist)scArray[0];
		 * score.setOptName(Toolket.getCourseOpt(score.getOpt()));
		 * score.setEvgrTypeName(Toolket.getCourseType(score.getEvgrType()));
		 * 
		 * tmplist = dao.findClassNameByCode(score.getStdepartClass()); //
		 * log.debug("=======> ClassCode='" + score.getStdepartClass() + "'"); //
		 * log.debug("=======> tmpListBound='" + tmplist.size() + "'"); if
		 * (tmplist.size()>0) {
		 * score.setStDepClassName(dao.findClassNameByCode(score.getStdepartClass()).get(0).toString()); }
		 * else { score.setStDepClassName(""); }
		 * score.setCscodeName(findCourseName(score.getCscode()));
		 * 
		 * scorelist.add(score); }
		 * 
		 * return scorelist;
		 */
		ScoreHist score;
		List<Clazz> tmplist;

		List<ScoreHist> sc = dao.submitQuery(hql);
		// Toolket.refreshCourseOptMap();
		// Toolket.refreshCourseTypeMap();

		for (Iterator scIter = sc.iterator(); scIter.hasNext();) {
			score = (ScoreHist) scIter.next();
			score.setOptName(Toolket.getCourseOpt(score.getOpt().trim()));
			score.setEvgrTypeName(Toolket.getCourseType(score.getEvgrType().trim()));
			tmplist = dao.findClassByCode(score.getStdepartClass());
			// log.debug("=======> ClassCode='" + score.getStdepartClass() +
			// "'");
			// log.debug("=======> tmpListBound='" + tmplist.size() + "'");
			if (tmplist.size() > 0) {
				score.setStDepClassName(tmplist.get(0).getClassName());
			} else {
				score.setStDepClassName("");
			}
			// score.setStDepClassName(dao.findClassNameByCode(score.getStdepartClass()).get(0).toString());
			if (!findCourseName(score.getCscode()).equals("")) {
				score.setCscodeName(findCourseName(score.getCscode()));
			} else {
				score.setCscodeName(score.getCscode().trim());
			}
		}

		return sc;

	}

	public List<ScoreHist> findScoreHistByStudentInfo(String student_no,
			String school_year, String school_term, String classInCharge) {

		String student_name = "";
		String dept_class = "";
		String depclass_name = ""; // 學生所屬班級名稱
		String stdepclass_name = ""; // 開課班級名稱
		String cscode_name = "";

		log.debug("=======> studno='" + student_no + "'");
		log.debug("=======> year='" + school_year + "'");
		log.debug("=======> term='" + school_term + "'");

		String hql = "select sc from ScoreHist sc where ";

		if (!"".equals(student_no)) {
			hql += "sc.studentNo = '" + student_no + "' ";
		}

		if (!"".equals(school_year)) {
			hql += "and sc.schoolYear = '" + school_year + "' ";
		}
		if (!"".equals(school_term)) {
			hql += "and sc.schoolTerm = '" + school_term + "' ";
		}

		hql += " and sc.departClass in " + classInCharge;

		log.debug("=======> hql='" + hql + "'");

		ScoreHist score;

		List<ScoreHist> sc = dao.submitQuery(hql);
		// Toolket.refreshCourseOptMap();
		// Toolket.refreshCourseTypeMap();

		for (Iterator scIter = sc.iterator(); scIter.hasNext();) {
			score = (ScoreHist) scIter.next();
			score.setOptName(Toolket.getCourseOpt(score.getOpt()));
			score.setEvgrTypeName(Toolket.getCourseType(score.getEvgrType()));
			score.setStDepClassName(findClassName(score.getStdepartClass()));
			score.setCscodeName(findCourseName(score.getCscode()));
		}

		return sc;
	}

	public List<Stavg> findScoreHistAvgByStudentInfoForm(Map formProperties,
			String classInCharge) {

		String student_no = (String) formProperties.get("student_no");
		// String student_name = (String)formProperties.get("student_name");
		String school_year = (String) formProperties.get("school_year");
		String school_term = (String) formProperties.get("school_term");

		String hql = "select sc from Stavg sc Where ";

		if (!"".equals(student_no)) {
			hql += "sc.studentNo = '" + student_no + "' ";
		} else {
			return null;
		}

		if (!"".equals(school_year)) {
			hql += "and sc.schoolYear = '" + school_year + "' ";
		}
		if (!"".equals(school_term)) {
			hql += "and sc.schoolTerm = '" + school_term + "' ";
		}

		//log.debug("=======> hql='" + hql + "'");

		Stavg score;
		List<Clazz> tmplist;

		List<Stavg> sc = dao.submitQuery(hql);
		// Toolket.refreshCourseOptMap();
		// Toolket.refreshCourseTypeMap();

		for (Iterator scIter = sc.iterator(); scIter.hasNext();) {
			score = (Stavg) scIter.next();
			tmplist = dao.findClassByCode(score.getDepartClass());
			if (tmplist.size() > 0) {
				score.setStDepClassName(tmplist.get(0).getClassName());
			} else {
				score.setStDepClassName("");
			}
			// score.setStDepClassName(dao.findClassNameByCode(score.getStdepartClass()).get(0).toString());
		}

		return sc;

	}

	public List<Student> findStudentsByStudentInfoForm(Map formProperties,
			String classInCharge) {

		String student_no = (String) formProperties.get("student_no");
		String student_name = (String) formProperties.get("student_name");

		log.debug("=======> studentNo='" + student_no + "'");
		log.debug("=======> name='" + student_name + "'");
		String hql = "select s from Student s ";

		if (!"".equals(student_no)) {
			hql += "where s.studentNo = '" + student_no + "' ";
			hql += " and s.departClass in " + classInCharge;
		} else if (!"".equals(student_name)) {
			hql += "where s.studentName like '%" + student_name + "%' ";
			hql += " and s.departClass in " + classInCharge;
		} else {
			hql += " where s.departClass in " + classInCharge;
		}
		log.debug("=======> hql='" + hql + "'");

		// return jdbcDao.findStudentsByStudentNO(student_no);

		return dao.submitQuery(hql);
	}

	/**
	 * 以學號查詢在學學生
	 * 
	 * @param String studentNo
	 * 
	 * @return Student or null
	 */
	public Student findStudentByStudentNo(String studentNo) {

		String hql = "select s from Student s ";

		if (!"".equals(studentNo)) {
			hql += "where s.studentNo = '" + studentNo + "' ";
		}
		// log.debug("=======> hql='" + hql + "'");

		List<Student> students = dao.submitQuery(hql);
		if (students.size() > 0) {
			Student student = students.get(0);
			student.setDepartClass2(Toolket.getClassFullName(student.getDepartClass()));
			return student;
		}
		return null;
	}
	public Student findStudentByStudentNoInCharge(String studentNo, String classInCharge) {

		String hql = "select s from Student s ";

		if (!"".equals(studentNo)) {
			hql += "where s.studentNo = '" + studentNo + "' And s.departClass in " + classInCharge;
		}
		// log.debug("=======> hql='" + hql + "'");

		List<Student> students = dao.submitQuery(hql);
		if (students.size() > 0) {
			Student student = students.get(0);
			student.setDepartClass2(Toolket.getClassFullName(student.getDepartClass()));
			return student;
		}
		return null;
	}


	public Graduate findGraduateByStudentNoInCharge(String studentNo, String classInCharge) {

		String hql = "select g from Graduate g ";

		if (!"".equals(studentNo)) {
			hql += "where g.studentNo = '" + studentNo  + "' And g.departClass in " + classInCharge;
		}
		log.debug("=======> hql='" + hql + "'");
		List<Graduate> graduates = dao.submitQuery(hql);
		if (graduates.size() > 0) {
			Graduate graduate = graduates.get(0);
			graduate.setDepartClass2(findClassName(graduate.getDepartClass()));
			return graduate;
		}
		return null;
	}

	public List<Graduate> findGraduateByHistInfoForm(Map formProperties,
			String classInCharge) {

		String student_no = (String) formProperties.get("student_no");
		String student_name = (String) formProperties.get("student_name");

		log.debug("=======> studentNo='" + student_no + "'");
		log.debug("=======> name='" + student_name + "'");

		String hql = "select g from Graduate g ";

		if (!"".equals(student_no)) {
			hql += "where g.studentNo = '" + student_no + "' ";
			hql += " and g.departClass in " + classInCharge;
		} else if (!"".equals(student_name)) {
			hql += "where g.studentName like '%" + student_name + "%' ";
			hql += " and g.departClass in " + classInCharge;
		} else {
			hql += " where g.departClass in " + classInCharge;
		}
		log.debug("=======> hql='" + hql + "'");

		return dao.submitQuery(hql);
	}

	public Graduate findGraduateByStudentNo(String studentNo) {

		String hql = "select g from Graduate g ";

		if (!"".equals(studentNo)) {
			hql += "where g.studentNo = '" + studentNo + "' ";
		}
		log.debug("=======> hql='" + hql + "'");
		List<Graduate> graduates = dao.submitQuery(hql);
		if (graduates.size() > 0) {
			Graduate graduate = graduates.get(0);
			graduate.setDepartClass2(findClassName(graduate.getDepartClass()));
			return graduate;
		}
		return null;
	}

	public String findCourseName(String cscode) {
		List<Csno> tmplist;
		tmplist = dao.findCourseByCode(cscode);
		if (tmplist.size() > 0) {
			return tmplist.get(0).getChiName().trim();
		} else {
			return "";
		}
	}


    public String findStudentName(String studentNo) {
		String hql = "select s from Student s ";
		
		if (!"".equals(studentNo)) {
			hql += "where s.studentNo = '" + studentNo + "' ";
		} else {
			return "";
		}
		
		List<Student> tmplist = dao.submitQuery(hql);

    	if (tmplist.size()>0) {
        	return tmplist.get(0).getStudentName().trim();
    	} 
    	/*
    	else {
    		hql = "select g from Graduate g where g.studentNo = '" + studentNo + "' ";
    		List<Graduate> tmplist2 = dao.submitQuery(hql);
        	if (tmplist2.size()>0) {
            	return tmplist2.get(0).getStudentName().trim();
        	}
    	}
    	*/
    	return "";
    }
    

	public String findClassName(String clazz) {
		List<Clazz> tmplist;
		tmplist = dao.findClassByCode(clazz);
		if (!tmplist.isEmpty()) {
			return tmplist.get(0).getClassName().trim();
		}else{
			return "";
		}
		
		/*
		String classFullName = Global.ClassFullName.getProperty(clazz);
		if(!(classFullName.equals("") || classFullName == null)) return classFullName;
		else return "";
		*/
	}


	public ActionMessages createScoreHist(Map formProperties, String classInCharge) {

		String student_no = formProperties.get("student_no").toString().trim();
		String school_year = formProperties.get("school_year").toString().trim();
		String school_term = formProperties.get("school_term").toString().trim();
		String stdepart_class = formProperties.get("stdepart_class").toString().trim();
		
		ActionMessages error = new ActionMessages();

		float[] avgScore;
		
		Student stu = this.findStudentByStudentNoInCharge(student_no, classInCharge);
		Graduate grad = this.findGraduateByStudentNoInCharge(student_no, classInCharge);
		if(stu == null && grad == null) {
			error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.YouDontHavePermission"));
			return error;
		}
		ScoreHist score = new ScoreHist();
		setScoreHistProp(score, formProperties);
		dao.saveScoreHist(score);
		Toolket.sendScoreHistotyInfoByQueue(score,
				IConstants.SYNC_DO_TYPE_INSERT);
		
		avgScore = this.calTermAvgScore(student_no, school_year, school_term);
		ActionMessages msgs = new ActionMessages();
		
		msgs = this.updateOrCreatAvgScore(student_no, school_year, school_term, stdepart_class, avgScore[0], avgScore[1]);
		
		return msgs;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ScoreHist modifyScoreHist(Map formProperties, ScoreHist score) {
		String student_no = formProperties.get("student_no").toString().trim();
		String school_year = formProperties.get("school_year").toString().trim();
		String school_term = formProperties.get("school_term").toString().trim();
		String stdepart_class = formProperties.get("stdepart_class").toString().trim();

		float[] avgScore;
		
		setScoreHistProp(score, formProperties);
		dao.saveScoreHist(score);
		Toolket.sendScoreHistotyInfoByQueue(score,
				IConstants.SYNC_DO_TYPE_UPDATE);
		avgScore = calTermAvgScore(student_no, school_year, school_term);
		log.debug("========>calTermAvgScore:" + avgScore[0] + ", "
				+ avgScore[1]);
		ActionMessages msgs = new ActionMessages();
		
		msgs = updateOrCreatAvgScore(student_no, school_year, school_term, stdepart_class, avgScore[0], avgScore[1]);
		return score;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void validateScoreHistCreate(Map formProperties,
			ActionMessages errors, ResourceBundle bundle) {

		String studentNo = formProperties.get("student_no").toString().trim();
		String cscode = formProperties.get("cscode").toString().trim();
		String schoolYear = formProperties.get("school_year").toString().trim();
		String schoolTerm = formProperties.get("school_term").toString().trim();
		String evgrType = formProperties.get("evgr_type").toString().trim();
		String opt = formProperties.get("opt").toString().trim();
		
		String sql = "Select * From ScoreHist Where student_no='" + studentNo + "'";
		sql = sql + " and school_year=" + schoolYear;
		sql = sql + " and school_term='" + schoolTerm + "'";
		sql = sql + " and cscode='" + cscode + "'";
		sql = sql + " and evgr_type='" + evgrType + "'";
		List cntList = jdbcDao.findAnyThing(sql);
		
		if (!cntList.isEmpty()) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.ScoreHist.ScoreAlreadyExit"));
		}
	}

	public void validateScoreHistModify(Map formProperties, ScoreHist score,
			ActionMessages errors, ResourceBundle bundle) {

		String studentNo = formProperties.get("student_no").toString().trim();
		String cscode = formProperties.get("cscode").toString().trim();
		String schoolYear = formProperties.get("school_year").toString().trim();
		String schoolTerm = formProperties.get("school_term").toString().trim();

		log.debug("=======> ScoreHist Modify form ='" + studentNo + "','"
				+ cscode + "','" + schoolYear + "','" + schoolTerm + "'");
		log.debug("=======> ScoreHist Modify Reco ='" + score.getStudentNo()
				+ "','" + score.getCscode() + "','" + score.getSchoolYear()
				+ "','" + score.getSchoolTerm() + "'");

		if (!studentNo.equals(score.getStudentNo())) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.ScoreHist.FieldCantModify", bundle
							.getString("StudentNo")));
		} else {
			int count = jdbcDao.getScoreHistCount(studentNo, cscode,
					schoolYear, schoolTerm);
			if (cscode.equals(score.getCscode())
					&& schoolYear.equals(score.getSchoolYear())
					&& schoolTerm.equals(score.getSchoolTerm()) && count > 1) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.ScoreHist.ScoreDuplicate"));
			} else if ((!cscode.equals(score.getCscode().trim())
					|| !schoolYear.equals(score.getSchoolYear().toString()
							.trim()) || !schoolTerm.equals(score
					.getSchoolTerm().toString().trim()))
					&& count > 0) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.ScoreHist.ScoreAlreadyExit"));
			}
		}
	}

	public List<ScoreHist> deleteScores(List<ScoreHist> scores,
			ResourceBundle bundle) {
		
		List<ScoreHist> undeleted = new ArrayList<ScoreHist>();
		ScoreHist score;
		for (Iterator<ScoreHist> scrIter = scores.iterator(); scrIter.hasNext();) {
			score = scrIter.next();
			Toolket.sendScoreHistotyInfoByQueue(score,
					IConstants.SYNC_DO_TYPE_DELETE);
			dao.removeScoreHist(score);
		}
		return undeleted;
	}

	public ActionMessages createScoreHistAvg(Map formProperties, String classInCharge) {

		String student_no = formProperties.get("student_no").toString().trim();
		
		ActionMessages error = new ActionMessages();

		Student stu = this.findStudentByStudentNoInCharge(student_no, classInCharge);
		Graduate grad = this.findGraduateByStudentNoInCharge(student_no, classInCharge);
		if(stu == null && grad == null) {
			error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.YouDontHavePermission"));
			return error;
		}
		Stavg score = new Stavg();
		setScoreHistAvgProp(score, formProperties);
		dao.saveObject(score);
		return error;
	}

	public Stavg modifyScoreHistAvg(Map formProperties, Stavg score) {
		String student_no = formProperties.get("student_no").toString().trim();
		String school_year = formProperties.get("school_year").toString().trim();
		String school_term = formProperties.get("school_term").toString().trim();
		String stdepart_class = formProperties.get("stdepart_class").toString().trim();

		setScoreHistAvgProp(score, formProperties);
		dao.saveObject(score);
		return score;
	}

	public void validateScoreHistAvgCreate(Map formProperties,
			ActionMessages errors, ResourceBundle bundle) {

		String studentNo = formProperties.get("student_no").toString().trim();
		String schoolYear = formProperties.get("school_year").toString().trim();
		String schoolTerm = formProperties.get("school_term").toString().trim();
		
		String sql = "Select * From Stavg Where student_no='" + studentNo + "'";
		sql = sql + " and school_year=" + schoolYear;
		sql = sql + " and school_term='" + schoolTerm + "'";
		List cntList = jdbcDao.findAnyThing(sql);
		
		if (!cntList.isEmpty()) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.ScoreHist.ScoreAlreadyExit"));
		}
	}

	public void validateScoreHistAvgModify(Map formProperties, Stavg score,
			ActionMessages errors, ResourceBundle bundle) {

		String studentNo = formProperties.get("student_no").toString().trim();
		String schoolYear = formProperties.get("school_year").toString().trim();
		String schoolTerm = formProperties.get("school_term").toString().trim();

		if (!studentNo.equals(score.getStudentNo())) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.ScoreHist.FieldCantModify", bundle
							.getString("StudentNo")));
		} else {
			int count = jdbcDao.getScoreHistAvgCount(studentNo, schoolYear, schoolTerm);
			if (schoolYear.equals(score.getSchoolYear())
					&& schoolTerm.equals(score.getSchoolTerm()) && count > 1) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.ScoreHist.ScoreDuplicate"));
			} else if ((!schoolYear.equals(score.getSchoolYear().toString()
							.trim()) || !schoolTerm.equals(score
					.getSchoolTerm().toString().trim()))
					&& count > 0) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.ScoreHist.ScoreAlreadyExit"));
			}
		}
	}


	
	public List<Stavg> deleteScoresAvg(List<Stavg> scores,
			ResourceBundle bundle) {
		
		List<Stavg> undeleted = new ArrayList<Stavg>();
		Stavg score;
		for (Iterator<Stavg> scrIter = scores.iterator(); scrIter.hasNext();) {
			score = scrIter.next();
			dao.removeObject(score);
		}
		return undeleted;
	}

	private void setScoreHistProp(ScoreHist score, Map formProp) {
		score.setStudentNo(formProp.get("student_no").toString().trim());
		score.setSchoolYear(Short.parseShort(formProp.get("school_year").toString().trim()));
		score.setSchoolTerm((formProp.get("school_term")).toString().trim());
		score.setEvgrType(formProp.get("evgr_type").toString().trim());
		score.setCscode(formProp.get("cscode").toString().trim());
		score.setOpt(formProp.get("opt").toString().trim());
		score.setCredit(Float.parseFloat(formProp.get("credit").toString().trim()));
		if(!formProp.get("score").toString().equals(""))
			score.setScore(Float.parseFloat(formProp.get("score").toString().trim()));
		score.setStdepartClass(formProp.get("stdepart_class").toString().trim());
	}

	private void setScoreHistAvgProp(Stavg score, Map formProp) {
		score.setStudentNo(formProp.get("student_no").toString().trim());
		score.setSchoolYear(Short.parseShort(formProp.get("school_year").toString().trim()));
		score.setSchoolTerm((formProp.get("school_term")).toString().trim());
		score.setTotalCredit(Float.parseFloat(formProp.get("credit").toString().trim()));
		if(!formProp.get("score").toString().equals(""))
			score.setScore(Float.parseFloat(formProp.get("score").toString().trim()));
		score.setDepartClass(formProp.get("stdepart_class").toString().trim());
	}

	public ActionMessages recalAvgScore(String student_no, String school_year, String school_term){
		ActionMessages err = new ActionMessages();
		String hql = "";
		float[] avgScore;
		
		try{			
			avgScore = this.calTermAvgScore(student_no, school_year, school_term);
			if(!(avgScore[0]==-1f && avgScore[1]==-1f)){
				hql = "Select s From Stavg s Where s.schoolYear=" + school_year + 
				" and schoolTerm='" + school_term + "' and studentNo='" + student_no + "'";
				List<Stavg> stavgs = dao.submitQuery(hql);
				if(!stavgs.isEmpty()){
					Stavg stavg = stavgs.get(0);
					stavg.setScore(avgScore[0]);
					stavg.setTotalCredit(avgScore[1]);
					dao.saveObject(stavg);
				}
			}else{
				Stavg stavg = new Stavg();
				stavg.setStudentNo(student_no);
				stavg.setDepartClass("");
				stavg.setSchoolYear(Short.parseShort(school_year));
				stavg.setSchoolTerm(school_term);
				stavg.setScore(avgScore[0]);
				stavg.setTotalCredit(avgScore[1]);
				dao.saveObject(stavg);
			}
		}catch(Exception e){
			e.printStackTrace();
			err.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			"Course.errorN1", e.toString()));
		}
		return err;
	}
	
	public List<Dtime> findCscodeByClass(String departClass) {
		String sterm = Toolket.getSysParameter("School_term");
		Dtime dtime;
		List<Dtime> dtimes = dao
				.submitQuery("select d From Dtime d Where d.departClass='"
						+ departClass + "' And d.sterm='" + sterm + "'");
		for (Iterator<Dtime> dtimeIter = dtimes.iterator(); dtimeIter.hasNext();) {
			dtime = dtimeIter.next();
			dtime.setChiName2(findCourseName(dtime.getCscode()));
			dtime.setClassName2(findClassName(dtime.getDepartClass()));
			dtime.setOpt2(Toolket.getCourseOpt(dtime.getOpt()));
		}
		return dtimes;
	}

	public List<Dtime> findDtimeByTeacher(String teacherId) {
		String sterm = Toolket.getSysParameter("School_term");
		Dtime dtime;
		List<Dtime> dtimes = dao
				.submitQuery("select d From Dtime d Where d.techid='"
						+ teacherId + "' And d.sterm='" + sterm + "'");
		for (Iterator<Dtime> dtimeIter = dtimes.iterator(); dtimeIter.hasNext();) {
			dtime = dtimeIter.next();
			dtime.setChiName2(findCourseName(dtime.getCscode()));
			dtime.setClassName2(findClassName(dtime.getDepartClass()));
			dtime.setOpt2(Toolket.getCourseOpt(dtime.getOpt()));
		}
		log.debug("=======> Find Dtime by TeachedID ='" + teacherId + "', Count:" + dtimes.size());

		return dtimes;
	}

	public Dtime findDtimeByOid(int oid) {
		List<Dtime> dtimes = dao
				.submitQuery("select d From Dtime d Where d.oid="
						+ oid );
		log.debug("=======> Find Dtime by Oid ='" + oid + "', Count:" + dtimes.size());

		return dtimes.get(0);
	}

	
	public List<Dtime> findDtimeTeacherByTeacher(String teacherId) {
		String sterm = Toolket.getSysParameter("School_term");
		List<Dtime> dtimelist = new ArrayList();
		
		List dtimeoids = jdbcDao.findAnyThing("(select dt.Dtime_oid As oid From Dtime_teacher dt, Dtime d Where dt.teach_id='"
						+ teacherId + "' And d.Sterm='" + sterm 
						+ "' And dt.fillscore=1 And dt.Dtime_oid=d.Oid) UNION "
						+ "(select d.Oid As oid from Dtime d Where d.techid='"
						+ teacherId + "' And d.Sterm='" + sterm + "')");
		
		log.debug("=======> Find DtimeTeacher by TeachedID dtimeoids.size() ='" + dtimeoids.size());
		if(dtimeoids.size() > 0) {
			String hql = "select d from Dtime d Where d.oid In (";
			for (Iterator oids = dtimeoids.iterator(); oids.hasNext();) {
				hql = hql + ((Map)oids.next()).get("oid");
				if(oids.hasNext()) hql = hql + ", ";
			}
			hql = hql + ")";
			log.debug("=======> Find DtimeTeacher by TeachedID Hql ='" + hql);
		
			dtimelist = dao.submitQuery(hql);
			log.debug("=======> Find DtimeTeacher by TeachedID ='" + teacherId + "', Count:" + dtimelist.size());
		
			for (Iterator<Dtime> dtimeIter = dtimelist.iterator(); dtimeIter.hasNext();) {
				Dtime dtimet = dtimeIter.next();
			
				dtimet.setChiName2(findCourseName(dtimet.getCscode()));
				dtimet.setClassName2(findClassName(dtimet.getDepartClass()));
			}
		}
		
		return dtimelist;
		
	}
	
	public List<Seld> findSeldScoreByInputForm(String departClass, String cscode) {
		String sterm = Toolket.getSysParameter("School_term");
		String studentName = "";
		
		String subhql = "select d.oid from Dtime d Where d.departClass='"
				+ departClass + "' and d.cscode='" + cscode + "' and d.sterm='"
				+ sterm + "'";
		//String hql = "select s From Seld s Where s.cscode='" + cscode;
		//hql = hql + "' and s.sterm=" + sterm;
		String hql = "select s From Seld s Where ";
		hql = hql + " s.dtimeOid in (" + subhql + ") order by s.studentNo";
		log.debug("=======> findSeldScoreByInputForm =[" + hql + "]");
		List<Seld> seldlist = dao.submitQuery(hql);
		Seld seld;
		for(Iterator<Seld> seldIter=seldlist.iterator(); seldIter.hasNext();) {
			seld = seldIter.next();
			studentName = findStudentName(seld.getStudentNo());
			
			if(studentName.equals("")) {
				seldIter.remove();
				log.debug("=======> findSeldScore Remove Student =[" + seld.getStudentNo() + "]");
			} else {
				seld.setStudentName(studentName);
			}
		}
		
		return seldlist;
	}
	
	public List<Seld> findSeldScoreByInputForm(String dtimeoid) {
		String sterm = Toolket.getSysParameter("School_term");
		String studentName = "";
		
		String hql = "select s From Seld s Where ";
		hql = hql + " s.dtimeOid=" + dtimeoid + " order by s.studentNo";
		log.debug("=======> findSeldScoreByInputForm =[" + hql + "]");
		List<Seld> seldlist = dao.submitQuery(hql);
		Seld seld;
		for(Iterator<Seld> seldIter=seldlist.iterator(); seldIter.hasNext();) {
			seld = seldIter.next();
			studentName = findStudentName(seld.getStudentNo());
			
			if(studentName.equals("")) {
				seldIter.remove();
				log.debug("=======> findSeldScore Remove Student =[" + seld.getStudentNo() + "]");
			} else {
				seld.setStudentName(studentName);
			}
		}
		
		return seldlist;
	}
	
	/*
	@SuppressWarnings("unchecked")
	public List<Seld> findSeldByInputForm(String departClasss, String cscode) {
		String sterm = Toolket.getSysParameter("School_term");

		String subhql = "select d.oid from Dtime d Where d.departClass='"
				+ departClasss + "' and d.cscode='" + cscode
				+ "' and d.sterm='" + sterm + "'";
		String hql = "select s From Seld s Where ";
		hql = hql + " s.dtimeOid in (" + subhql + ") order by s.studentNo";
		log.debug("=======> findSeldScoreByInputForm =[" + hql + "]");
		return dao.submitQuery(hql);
	}
	*/
	
	@SuppressWarnings("unchecked")
	public List<Seld> findSeldsBy(Integer dtimeOid) {
		String hql = "SELECT s FROM Seld s WHERE s.dtimeOid = " + dtimeOid;
		return dao.submitQuery(hql);
	}

	@SuppressWarnings("unchecked")
	public Seld findSeld(int oid, String studentNo) {
		String hql = "select s From Seld s Where s.dtimeOid=" + oid
			+ " and s.studentNo='" + studentNo + "'";
		List<Seld> seldlist = dao.submitQuery(hql);
		if(seldlist.size() > 0) {
			return seldlist.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Seld> findSeld(String studentNo, String term) {
		String hql = "SELECT s FROM Seld s, Dtime d WHERE s.dtimeOid = d.oid "
				+ "AND d.sterm = '" + term + "' AND s.studentNo = '"
				+ studentNo + "' ORDER BY d.cscode"; // ORDER BY cscode不能改
		List<Seld> seldlist = dao.submitQuery(hql);
		return seldlist;
	}
	
	public ActionMessages updateSeld(Seld seld, String scoretype, double[] score) {
		ActionMessages errors = new ActionMessages();
		if(scoretype.equals("1")) {
			seld.setScore2(score[0]);
		}
		if(scoretype.equals("2")) {
			if(score.length == 2) {
				seld.setScore3(score[0]);
				seld.setScore(score[1]);
			} else {
				errors.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.updateSeldError"));
			}
		}
		dao.saveObject(seld);
		return errors;
	}
	
	public ActionMessages patchSeldRegs(String departClass, String cscode){
		ActionMessages errors = new ActionMessages();
		String sterm = Toolket.getSysParameter("School_term");
		String sql = "";
		double total = 0d;
		
		String subhql = "select d.oid from Dtime d Where d.departClass='"
			+ departClass + "' and d.cscode='" + cscode + "' and d.sterm='"
			+ sterm + "'";
		
		String hql = "select s From Seld s Where ";
		hql = hql + " s.dtimeOid in (" + subhql + ") order by s.studentNo";
		log.debug("=======> findRegsScore =[" + hql + "]");
		List<Seld> seldlist = dao.submitQuery(hql);
		
		hql = "select r From Regs r Where ";
		hql = hql + " r.dtimeOid in (" + subhql + ")";
		List<Regs> regslist = dao.submitQuery(hql);
		
		//if(regslist.size() != seldlist.size()) {
			Seld seld;
			Regs regs;
			String studentNo;
			for(Iterator<Seld> seldIter=seldlist.iterator(); seldIter.hasNext();) {
				seld = seldIter.next();
				studentNo = seld.getStudentNo();
				
				sql = "Select * From Regs r Where r.Dtime_oid=" + seld.getDtimeOid() + " And r.student_no='"+ studentNo + "'";
				regslist = jdbcDao.findAnyThing(sql);
				
				if(regslist.isEmpty()) {
					try {
					Regs newRegs = new Regs();
					newRegs.setDtimeOid(seld.getDtimeOid());
					newRegs.setStudentNo(studentNo);
					if(seld.getScore2() != null) {
						newRegs.setScore19(seld.getScore2());
						newRegs.setScore20(seld.getScore2()*0.3);
						total = total + (seld.getScore2()*0.3);
					}
					if(seld.getScore3() != null) {
						newRegs.setScore21(seld.getScore3());
						newRegs.setScore22(seld.getScore3()*0.4);
						total = total + (seld.getScore3()*0.4);
					}
					if(seld.getScore() != null){
						total = seld.getScore();
					}
					if(total > 0d){
						newRegs.setScore23(total);
					}
					dao.saveObject(newRegs);
					} catch (Exception e) {
						errors.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Message.patchSeldRegsError"));
						errors.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Exception.generic", e.toString()));
						return errors;
					}
				}
			}
			
			//Remove redundant Regs record
			/*
			for(Iterator<Regs> regsIter=regslist.iterator(); regsIter.hasNext();) {
				regs = regsIter.next();
				studentNo = regs.getStudentNo();
				
				sql = "Select * From Seld s Where s.Dtime_oid=" + regs.getDtimeOid() + " And s.student_no='"+ studentNo + "'";
				seldlist = jdbcDao.findAnyThing(sql);
				
				if(seldlist.isEmpty()) {
					try {
					dao.removeObject(regs);
					} catch (Exception e) {
						errors.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Message.patchSeldRegsError"));
						errors.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Exception.generic", e.toString()));
						return errors;
					}
				}
			}
			*/
		//}
		return errors;
	}

	public ActionMessages patchAllSeldRegs(String sterm){
		ActionMessages errors = new ActionMessages();
		if(sterm.equals("") || !sterm.equals("1") || !sterm.equals("2")){
			sterm = Toolket.getSysParameter("School_term");
		}
		String dupmsg = "";
		String overmsg = "";
		String sql = "";
		List<Regs> regslist;
		List<Seld> seldlist;
		
		String subhql = "select d.oid from Dtime d Where d.sterm='"
			+ sterm + "'";
		
		String hql = "";
		hql = "select s From Seld s Where ";
		hql = hql + " s.dtimeOid in (" + subhql + ") order by s.studentNo";
		log.debug("=======> findRegsScore =[" + hql + "]");
		seldlist = dao.submitQuery(hql);
		
		hql = "select r From Regs r Where ";
		hql = hql + " r.dtimeOid in (" + subhql + ")";
		//regslist = dao.submitQuery(hql);
		
		//第一階段:先補足Regs不足的資料
		//if(regslist.size() != seldlist.size()) {
			Seld seld;
			Regs regs;
			String studentNo;
			int psize = seldlist.size();
			int pcnt = 0;
			
			for(Iterator<Seld> seldIter=seldlist.iterator(); seldIter.hasNext();) {
				pcnt++;
				seld = seldIter.next();
				studentNo = seld.getStudentNo();
				
				sql = "Select * From Regs r Where r.Dtime_oid=" + seld.getDtimeOid() + " And r.student_no='"+ studentNo + "'";
				regslist = jdbcDao.findAnyThing(sql);
								
				if(regslist.isEmpty()) {
					try {
					Regs newRegs = new Regs();
					newRegs.setDtimeOid(seld.getDtimeOid());
					newRegs.setStudentNo(studentNo);
					dao.saveObject(newRegs);
					} catch (Exception e) {
						errors.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Message.patchSeldRegsError"));
						errors.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Exception.generic", e.toString()));
						return errors;
					}
				}else if(regslist.size() > 1) {
					dupmsg = "duplicate regs:";
						for(Regs regs2 : regslist){
							dupmsg = dupmsg + "<->" + regs2.getOid();
						}
						log.debug("duplicate regs:" + dupmsg);
					/*
					dupmsg = dupmsg + "|";
					for(Regs regs2 : regslist){
						dupmsg = dupmsg + "->" + regs2.getOid();
					}
					*/
				}
				if(pcnt % 100 == 0) {
					log.debug((double)(pcnt/psize));
					
					log.debug("SetRunningStatus->methodName:PatchAllRegs, step:1/2 patch:" + pcnt + "/" + psize +", percentage:" + (pcnt/psize));
					this.setRunStatus("PatchAllRegs", "1/2 patch:" , pcnt , psize, (double)((pcnt/psize)*100), "no");
				}
			}
		//}else{
		//	log.debug("PatchAllRegs -> 資料筆數相同,無需修補!");
		//}
			
		//第二階段:紀錄Regs多餘的資料
		//hql = "select s From Seld s Where ";
		//hql = hql + " s.dtimeOid in (" + subhql + ") order by s.studentNo";
		//log.debug("=======> findRegsScore =[" + hql + "]");
		//seldlist = dao.submitQuery(hql);
			
		hql = "select r From Regs r Where ";
		hql = hql + " r.dtimeOid in (" + subhql + ")";
		regslist = dao.submitQuery(hql);
		psize = regslist.size();
		pcnt = 0;
		
		for(Iterator<Regs> regsIter=regslist.iterator(); regsIter.hasNext();) {
			pcnt++;
			regs = regsIter.next();
			studentNo = regs.getStudentNo();
			
			sql = "Select * From Seld s Where s.Dtime_oid=" + regs.getDtimeOid() + " And s.student_no='"+ studentNo + "'";
			seldlist = jdbcDao.findAnyThing(sql);
							
			if(seldlist.isEmpty()) {
				//Regs有多餘的資料
				//overmsg = overmsg + "," + regs.getOid();
				log.debug("Regs有多餘的資料 : " + regs.getOid());
			}
			this.setRunStatus("PatchAllRegs", "2/2 patch:" , pcnt , psize, (double)((pcnt/psize)*100), "no");

		}
		this.setRunStatus("PatchAllRegs", "over!", pcnt, psize, 100d, "yes");
		
		/*
		if(!dupmsg.equals("")){
			log.debug("Regs重複的資料,請手動刪除:" + dupmsg);
			errors.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.generic", "Regs重複的資料,請手動刪除:" + dupmsg));
		}
		if(!dupmsg.equals("")){
			log.debug("Regs多餘的資料,請手動刪除:" + dupmsg);
			errors.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.generic", "Regs多餘的資料,請手動刪除:" + dupmsg));
		}
		*/
		
		return errors;
	}

	
	public List<Regs> findRegsScore(String departClass, String cscode) {
		String sterm = Toolket.getSysParameter("School_term");
		String studentName = "";
		
		String subhql = "select d.oid from Dtime d Where d.departClass='"
				+ departClass + "' and d.cscode='" + cscode + "' and d.sterm='"
				+ sterm + "'";
		
		String hql = "select r From Regs r Where ";
		hql = hql + " r.dtimeOid in (" + subhql + ") order by r.studentNo";
		log.debug("=======> findRegsScore =[" + hql + "]");
		List<Regs> regslist = dao.submitQuery(hql);
		Regs regs;
		for(Iterator<Regs> regsIter=regslist.iterator(); regsIter.hasNext();) {
			regs = regsIter.next();
			studentName = findStudentName(regs.getStudentNo());
			if(studentName.equals("")) {
				regsIter.remove();
				log.debug("=======> findRegsScore Remove Student =[" + regs.getStudentNo() + "]");
			} else {
				regs.setStudentName(studentName);
			}
		}
		
		return regslist;
	}

	
	public List<Rrate> findScoreRate(String departClass, String cscode) {
		String sterm = Toolket.getSysParameter("School_term");
		String subhql = "select d.oid from Dtime d Where d.departClass='"
				+ departClass + "' and d.cscode='" + cscode + "' and d.sterm='"
				+ sterm + "'";
		
		String hql = "select r From Rrate r Where ";
		hql = hql + " r.dtimeOid in (" + subhql + ")";
		log.debug("=======> findScoreRate =[" + hql + "]");
		List<Rrate> ratelist = dao.submitQuery(hql);
		Regs regs;
		
		return ratelist;
	}
	
	public List<Rrate> findScoreRate(int Oid){
		String hql = "select r From Rrate r Where ";
		hql = hql + " r.dtimeOid =" + Oid;
		List<Rrate> ratelist = dao.submitQuery(hql);
		return ratelist;
	}
	
	public Rrate createRrate(String selclass, String cscode) {
		final float zero = (float)(0.0);
		String sterm = Toolket.getSysParameter("School_term");
		String subhql = "select d from Dtime d Where d.departClass='"
				+ selclass + "' and d.cscode='" + cscode + "' and d.sterm='"
				+ sterm + "'";
		
		int doid = ((List<Dtime>)dao.submitQuery(subhql)).get(0).getOid();
		Rrate rrate = new Rrate();
		rrate.setDtimeOid(doid);
		rrate.setDepartClass(selclass);
		rrate.setCscode(cscode);
		rrate.setRate01(zero);
		rrate.setRate02(zero);
		rrate.setRate03(zero);
		rrate.setRate04(zero);
		rrate.setRate05(zero);
		rrate.setRate06(zero);
		rrate.setRate07(zero);
		rrate.setRate08(zero);
		rrate.setRate09(zero);
		rrate.setRate10(zero);
		rrate.setRate11(zero);
		rrate.setRate12(zero);
		rrate.setRate13(zero);
		rrate.setRate14(zero);
		rrate.setRate15(zero);
		rrate.setRateN(0.3f);
		rrate.setRateM(0.3f);
		rrate.setRateF(0.4f);
		rrate.setTotal((short)0);
		dao.saveObject(rrate);
		
		return rrate;
	}
	
	public Rrate createRrate(int dtimeoid) {
		final float zero = (float)(0.0);
		String sterm = Toolket.getSysParameter("School_term");
		String subhql = "select d from Dtime d Where d.oid="
				+ dtimeoid ;
		
		Dtime dtime = ((List<Dtime>)dao.submitQuery(subhql)).get(0);
		Rrate rrate = new Rrate();
		rrate.setDtimeOid(dtimeoid);
		rrate.setDepartClass(dtime.getDepartClass());
		rrate.setCscode(dtime.getCscode());
		rrate.setRate01(zero);
		rrate.setRate02(zero);
		rrate.setRate03(zero);
		rrate.setRate04(zero);
		rrate.setRate05(zero);
		rrate.setRate06(zero);
		rrate.setRate07(zero);
		rrate.setRate08(zero);
		rrate.setRate09(zero);
		rrate.setRate10(zero);
		rrate.setRate11(zero);
		rrate.setRate12(zero);
		rrate.setRate13(zero);
		rrate.setRate14(zero);
		rrate.setRate15(zero);
		rrate.setRateN(0.3f);
		rrate.setRateM(0.3f);
		rrate.setRateF(0.4f);
		rrate.setTotal((short)0);
		dao.saveObject(rrate);
		
		return rrate;
	}
	
	public void updateRrate(Rrate scorerate, float[] rrate) {
		scorerate.setRate01(rrate[0]);
		scorerate.setRate02(rrate[1]);
		scorerate.setRate03(rrate[2]);
		scorerate.setRate04(rrate[3]);
		scorerate.setRate05(rrate[4]);
		scorerate.setRate06(rrate[5]);
		scorerate.setRate07(rrate[6]);
		scorerate.setRate08(rrate[7]);
		scorerate.setRate09(rrate[8]);
		scorerate.setRate10(rrate[9]);
		scorerate.setRateN(rrate[10]);
		scorerate.setRateM(rrate[11]);
		scorerate.setRateF(rrate[12]);
		scorerate.setTotal(Short.parseShort("" + 
				Math.round(rrate[0]+rrate[1]+rrate[2]+rrate[3]+rrate[4]+
				rrate[5]+rrate[6]+rrate[7]+rrate[8]+rrate[9])));
		dao.saveObject(scorerate);
	}
	
	/* 
	 * 註冊組輸入期中期末成績時使用
	 * 資料將直接寫入Seld,如果選取寫入教師上傳成績檔,
	 * 則一併寫入Regs
	 * 
	 * @param Map formProperties
	 * @param List<Seld> scoreslist
	 * 
	 * @see tw.edu.chit.service.ScoreManager#updateScoreInput(java.util.Map, java.util.List)
	 */
	public ActionMessages updateScoreInput(Map formProperties,
			List<Seld> scoreslist) {
		ActionMessages errors = new ActionMessages();
		String[] score = (String[]) formProperties.get("scrinput");
		String[] students = (String[]) formProperties.get("studentNo");
		String scoretype = formProperties.get("scoretype").toString(); // 1:寫入期中,2:寫入期末

		double[] scores = new double[score.length];
		for (int i = 0; i < score.length; i++) {
			scores[i] = (double) Math.round(Float.parseFloat(score[i]));
		}
		
		List templist;
		int count = 0;
		int result = 0;
		try{
			for (Iterator scoreIter = scoreslist.iterator(); scoreIter.hasNext();) {
				Seld seld = (Seld) scoreIter.next();
				if (seld.getStudentNo().trim().equals(students[count])) {
					//log.debug("=======> SeldUpdate->scoretype =[" + scoretype + "]");
					if (scoretype.equals("1")) {
						if(seld.getScore2() == null || seld.getScore2() != scores[count]) {
							seld.setScore2(scores[count]);
							seld.setScore17(Math.round(scores[count] * 0.3 * 10) / 10.0);
							dao.saveObject(seld);
						}
					} else if (scoretype.equals("2")) {
						if(seld.getScore() == null || seld.getScore() != scores[count]){
							seld.setScore(scores[count]);
							dao.saveObject(seld);
							
						}
					}
				} else {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"Message.updateSeldError"));
					break;
				}
				count++;
			}
		}catch(Exception e){
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.generic", e.toString()));
			e.printStackTrace();
		}
		return errors;
	}
	
	/* 
	 * 教師上傳期中期末成績時使用
	 * 資料將直接寫入Regs,確認學期成績無誤時將寫入Seld,
	 * 
	 * @param Map formProperties
	 * @param List<Regs> regs
	 * @param Rrate rrate
	 * @param Map calscore 經過計算的成績
	 * 
	 */
	public ActionMessages updateTchScoreInput(Map formProperties, List<Regs> regs, Map calscore) {
		ActionMessages errors = new ActionMessages();
		
		String[] students = (String[]) formProperties.get("studentNo");
		//String myclass = formProperties.get("classInCharge2").toString().trim();
		//String cscode = formProperties.get("cscode").toString().trim();
		String scoretype = formProperties.get("scoretype").toString(); // 1:寫入期中,2:寫入期末
		String yn = formProperties.get("yn").toString(); // yes: 寫入Seld , no:不寫入Seld 
		
		double[] scr01f, scr02f, scr03f, scr04f, scr05f, scr06f, scr07f, scr08f;
		double[] scr09f, scr10f, scr16f, scr17f, scr19f, scr20f, scr21f, scr22f, scr23f;
		String[] scorerate,scr01,scr02,scr03,scr04,scr05,scr06,scr07,scr08;
		String[] scr09,scr10,scr16,scr17,scr19,scr20,scr21,scr22,scr23;
			scr01f = (double[])calscore.get("scr01f"); 
			scr02f = (double[])calscore.get("scr02f"); 
			scr03f = (double[])calscore.get("scr03f"); 
			scr04f = (double[])calscore.get("scr04f"); 
			scr05f = (double[])calscore.get("scr05f"); 
			scr06f = (double[])calscore.get("scr06f"); 
			scr07f = (double[])calscore.get("scr07f"); 
			scr08f = (double[])calscore.get("scr08f"); 
			scr09f = (double[])calscore.get("scr09f"); 
			scr10f = (double[])calscore.get("scr10f"); 
			scr16f = (double[])calscore.get("scr16f"); 
			scr17f = (double[])calscore.get("scr17f"); 
			scr19f = (double[])calscore.get("scr19f"); 
			scr20f = (double[])calscore.get("scr20f"); 
			scr21f = (double[])calscore.get("scr21f"); 
			scr22f = (double[])calscore.get("scr22f"); 
			scr23f = (double[])calscore.get("scr23f"); 
		
		List templist;
		int count = 0;
		int result = 0;
		
		for (Iterator scoreIter = regs.iterator(); scoreIter.hasNext();) {
			Regs myregs = new Regs();
			myregs = (Regs)scoreIter.next();
			if (myregs.getStudentNo().trim().equals(students[count])) {
				//log.debug("=======> SeldUpdate->scoretype =[" + scoretype + "]");
				if (scoretype.equals("1")) {
					//try{
					//xxxx if(myregs.getScore19() != scr19f[count]) {
						templist = jdbcDao.findAnyThing("select * from Seld where Dtime_oid="
							+ myregs.getDtimeOid()
							+ " and student_no='"
							+ myregs.getStudentNo() + "'");
						if (templist.size() > 0) {
							result = jdbcDao.updateSeld(scoretype, scr19f[count], myregs
									.getDtimeOid(), myregs.getStudentNo());
							//log.debug("=======> SeldUpdate->result =[" + result + "]");
							if (result != 1) {
								errors.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("Message.updateSeldError"));
								break;
							}
						}
					
						// update regs score19
						result = jdbcDao.updateRegs(scoretype,
							scr19f[count], myregs.getDtimeOid(), myregs.getStudentNo());
						//log.debug("=======> RegsUpdate->result =[" + result + ", " + myregs.getStudentNo() + "]" );
					//}
					//}catch(Exception e){
					//	e.printStackTrace();
					//}
				} else if (scoretype.equals("2")) {
					/* 上傳期末成績及平時成績只先更新Regs的資料,確認上傳整學期成績時再更新Seld資料
					 * 
					if (yn.equals("yes")) {
						//只更新Seld中的學期成績score欄位,不包含期末成績欄位score3
						templist = jdbcDao.findAnyThing("select * from Seld where Dtime_oid="
							+ myregs.getDtimeOid()
							+ " and student_no='"
							+ myregs.getStudentNo() + "'");
						if (templist.size() > 0) {
							result = jdbcDao.updateSeld(scoretype, scr23i[count], myregs
									.getDtimeOid(), myregs.getStudentNo());
							if (result != 1) {
								errors.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("Message.updateSeldError"));
								break;
							}
						}
					}
					*/
					// seld.setScore3(scores[count]);
					// update all regs scores about Teacher uploaded
					if(!(scr01f[count] == -1.0)) myregs.setScore01(scr01f[count]);
					else myregs.setScore01(null);
					if(!(scr02f[count] == -1.0)) myregs.setScore02(scr02f[count]);
					else myregs.setScore02(null);
					if(!(scr03f[count] == -1.0)) myregs.setScore03(scr03f[count]);
					else myregs.setScore03(null);
					if(!(scr04f[count] == -1.0)) myregs.setScore04(scr04f[count]);
					else myregs.setScore04(null);
					if(!(scr05f[count] == -1.0)) myregs.setScore05(scr05f[count]);
					else myregs.setScore05(null);
					if(!(scr06f[count] == -1.0)) myregs.setScore06(scr06f[count]);
					else myregs.setScore06(null);
					if(!(scr07f[count] == -1.0)) myregs.setScore07(scr07f[count]);
					else myregs.setScore07(null);
					if(!(scr08f[count] == -1.0)) myregs.setScore08(scr08f[count]);
					else myregs.setScore08(null);
					if(!(scr09f[count] == -1.0)) myregs.setScore09(scr09f[count]);
					else myregs.setScore09(null);
					if(!(scr10f[count] == -1.0)) myregs.setScore10(scr10f[count]);
					else myregs.setScore10(null);
					if(!(scr16f[count] == -1.0)) myregs.setScore16(scr16f[count]);
					else myregs.setScore16(null);
					if(!(scr17f[count] == -1.0)) myregs.setScore17(scr17f[count]);
					else myregs.setScore17(null);
					if(!(scr21f[count] == -1.0)) myregs.setScore21(scr21f[count]);
					else myregs.setScore21(null);
					if(!(scr22f[count] == -1.0)) myregs.setScore22(scr22f[count]);
					else myregs.setScore22(null);
					if(!(scr23f[count] == -1.0)) myregs.setScore23(scr23f[count]);
					else myregs.setScore23(null);
					dao.saveObject(myregs);
					// dao.saveSeld(seld);
				} else if(scoretype.equals("3")){
					templist = jdbcDao.findAnyThing("select * from Seld where Dtime_oid="
							+ myregs.getDtimeOid()
							+ " and student_no='"
							+ myregs.getStudentNo() + "'");
						if (templist.size() > 0) {
							result = jdbcDao.updateSeld(scoretype, scr23f[count], myregs
									.getDtimeOid(), myregs.getStudentNo());
							//log.debug("=======> SeldUpdate->result =[" + result + "]");
							if (result != 1) {
								errors.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("Message.updateSeldError"));
								break;
							}
						}
					
						// update regs score23,配合jdbcDao.updateRegs,scoretype參數改為"2"
						result = jdbcDao.updateRegs("2",
							scr23f[count], myregs.getDtimeOid(), myregs.getStudentNo());
						//log.debug("=======> RegsUpdate->result =[" + result + ", " + myregs.getStudentNo() + "]" );

				}
			} else {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.updateSeldError"));
				break;
			}
			count++;
			
		}

		
		return errors;
	}
	
	public ActionMessages updateScoreInputAll(Map formProperties, List<Seld> seld, Map calscore){
		ActionMessages errors = new ActionMessages();
		
		String[] students = (String[]) formProperties.get("studentNo");
		String openMode = formProperties.get("openMode").toString().trim();
		//String cscode = formProperties.get("cscode").toString().trim();
		String scoretype = formProperties.get("scoretype").toString(); // 1:寫入期中,2:寫入期末
		//String yn = formProperties.get("yn").toString(); // yes: 寫入Seld , no:不寫入Seld 
		
		double[] scr01f, scr02f, scr03f, scr04f, scr05f, scr06f, scr07f, scr08f;
		double[] scr09f, scr10f, scr16f, scr17f, scr18f, scrf, scr1f, scr2f, scr3f;
		String[] scorerate,scr01,scr02,scr03,scr04,scr05,scr06,scr07,scr08;
		String[] scr09,scr10,scr16,scr17,scr18,scr,scr1,scr2,scr3;
		scr01f = (double[])calscore.get("scr01f"); 
		scr02f = (double[])calscore.get("scr02f"); 
		scr03f = (double[])calscore.get("scr03f"); 
		scr04f = (double[])calscore.get("scr04f"); 
		scr05f = (double[])calscore.get("scr05f"); 
		scr06f = (double[])calscore.get("scr06f"); 
		scr07f = (double[])calscore.get("scr07f"); 
		scr08f = (double[])calscore.get("scr08f"); 
		scr09f = (double[])calscore.get("scr09f"); 
		scr10f = (double[])calscore.get("scr10f"); 
		scr16f = (double[])calscore.get("scr16f"); 
		scr17f = (double[])calscore.get("scr17f"); 
		scr18f = (double[])calscore.get("scr18f"); 
		scrf = (double[])calscore.get("scrf"); 
		scr1f = (double[])calscore.get("scr1f"); 
		scr2f = (double[])calscore.get("scr2f"); 
		scr3f = (double[])calscore.get("scr3f"); 
		
		List templist;
		int count = 0;
		int result = 0;
		
		for (Iterator scoreIter = seld.iterator(); scoreIter.hasNext();) {
			Seld myseld = new Seld();
			myseld = (Seld)scoreIter.next();
			if (myseld.getStudentNo().trim().equals(students[count])) {
				//log.debug("=======> SeldUpdate->scoretype =[" + scoretype + "]");
				if(openMode.equals("000")){
					if(!(scrf[count] == -1.0)) myseld.setScore((double)(Math.round(scrf[count])));
					else myseld.setScore(null);
					dao.saveObject(myseld);
					count++;
					continue;
				}
				if (openMode.substring(1, 2).equals("1")) {
					 //更新期中成績
					//log.debug("studentNo:" + myseld.getStudentNo().trim()+ " ;" + scr2f[count]);
					if(!(scr2f[count] == -1.0))
						myseld.setScore2(scr2f[count]);
					else myseld.setScore2(null);
					if(!(scr17f[count] == -1.0))
						myseld.setScore17(scr17f[count]);
					else
						myseld.setScore17(null);
					if(openMode.equals("010")){
						dao.saveObject(myseld);
						count++;
						continue;						
					}
				}
				if(openMode.substring(0, 1).equals("1")){
					 //更新期末成績
					if(!(scr3f[count] == -1.0)) myseld.setScore3(scr3f[count]);
					else myseld.setScore3(null);
					if(!(scr18f[count] == -1.0)) myseld.setScore18(scr18f[count]);
					else myseld.setScore18(null);
					myseld.setScore(scrf[count]);
					if(openMode.equals("100")){
						dao.saveObject(myseld);
						count++;
						continue;						
					}
				}
				if(openMode.substring(2, 3).equals("1")){
					if(!(scr01f[count] == -1.0)) myseld.setScore01(scr01f[count]);
					else myseld.setScore01(null);
					if(!(scr02f[count] == -1.0)) myseld.setScore02(scr02f[count]);
					else myseld.setScore02(null);
					if(!(scr03f[count] == -1.0)) myseld.setScore03(scr03f[count]);
					else myseld.setScore03(null);
					if(!(scr04f[count] == -1.0)) myseld.setScore04(scr04f[count]);
					else myseld.setScore04(null);
					if(!(scr05f[count] == -1.0)) myseld.setScore05(scr05f[count]);
					else myseld.setScore05(null);
					if(!(scr06f[count] == -1.0)) myseld.setScore06(scr06f[count]);
					else myseld.setScore06(null);
					if(!(scr07f[count] == -1.0)) myseld.setScore07(scr07f[count]);
					else myseld.setScore07(null);
					if(!(scr08f[count] == -1.0)) myseld.setScore08(scr08f[count]);
					else myseld.setScore08(null);
					if(!(scr09f[count] == -1.0)) myseld.setScore09(scr09f[count]);
					else myseld.setScore09(null);
					if(!(scr10f[count] == -1.0)) myseld.setScore10(scr10f[count]);
					else myseld.setScore10(null);
					if(!(scr16f[count] == -1.0)) myseld.setScore16(scr16f[count]);
					else myseld.setScore16(null);
					myseld.setScore1(scr1f[count]);
					// dao.saveSeld(seld);

				}

				dao.saveObject(myseld);

			} else {
				log.debug("studentNo:" + myseld.getStudentNo().trim()+ " ;" + students[count]);
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.updateSeldError"));
				return errors;
			}
			count++;
			
		}
		return errors;
	}
	
	public ActionMessages updateRegsTime(int dtimeoid, String departClass, String cscode, String teacherId, String scoretype, String now) {
		ActionMessages errors = new ActionMessages();
		int result;
		List<Regstime> templist;
		
		templist = jdbcDao
		.findAnyThing("select * from Regstime where dtime_oid="
				+ dtimeoid
				+ " and ind='" + scoretype + "'");
		if (templist.size() > 0) {
			result = jdbcDao.updateRegstime(dtimeoid, departClass, cscode, teacherId, scoretype, now);
			log.debug("=======> RegstimeUpdate->result =[" + result + "]" );
			if (result != 1) {
				errors.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.updateRegstimeError"));

			}
		} else if (templist.size() == 0) {
			Regstime regstime = new Regstime();
			regstime.setDtimeOid(dtimeoid);
			regstime.setCscode(cscode);
			regstime.setDepartClass(departClass);
			regstime.setIdno(teacherId);
			regstime.setInd(scoretype);
			regstime.setTtime(now);
			dao.saveObject(regstime);
		}
		return errors;
	}
	
	public List<Regstime> findRegstime(int dtimeoid, String selclass, String cscode, String teacherId, String index){
		List<Regstime> rtList = new ArrayList();
		String hql = "";
		if(selclass.trim().equals("")||cscode.trim().equals("")||
				index.trim().equals("")) {
			return rtList;
		}else {
			if(teacherId.trim().equals("")){
				hql = " Select r from Regstime r where dtimeOid=" + dtimeoid + " And departClass='" + selclass + 
				"' and cscode='" + cscode +
				"' and ind='" + index + "'";
			}else{
				hql = " Select r from Regstime r where dtimeOid=" + dtimeoid + " And departClass='" + selclass + 
				"' and cscode='" + cscode + "' and idno='" + teacherId +
				"' and ind='" + index + "'";
			}
			rtList = dao.submitQuery(hql);
		}
		return rtList;
	}
	
	/**
	 * 確認是否已開放老師可以上傳成績
	 */
	public ActionMessages chkTchScoreUploadOpened(String scoretype, String selclass) {
		String begin_date = "";
		String begin_time = "";
		String end_date = "";
		String end_time = "";
		String sterm = Toolket.getSysParameter("School_term");

		int begin_year, begin_month,begin_day,begin_hour,begin_minute,begin_second;
		
		int end_year,end_month,end_day,end_hour,end_minute,end_second;

		boolean graduated = false;
		boolean passed = false;
		String dept = "11";
		String gradept = "12";
		String level = "1";
		String hql,  sql;
		int gradYear = 2;
		Calendar beginCal =  Calendar.getInstance();
		Calendar endCal =  Calendar.getInstance();
		List tmplist = new ArrayList(); 
		
		ActionMessages messages = new ActionMessages();
		
		/*
		 * 決定欲輸入成績之班級屬於那個部制
		 * 11:台北日間 ，12台北進修，13台北學院，21新竹日間，22新竹進修，23新竹學院
		 */
		dept = selclass.substring(0, 2);
		hql = "select c5.name from Code5 c5 Where category='SchoolType' And idno='" + dept + "'";
		tmplist = dao.submitQuery(hql);
		if(tmplist.size() > 0)
			dept = tmplist.get(0).toString().substring(0, 2);
		else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.noSuchSchoolType"));
			return messages;
		}

		Calendar now = Calendar.getInstance();
		String nows = "" + (now.get(Calendar.YEAR)-1911) 
					+ "/" + (now.get(Calendar.MONTH) + 1) 
					+ "/" + now.get(Calendar.DATE) 
					+ " " + now.get(Calendar.HOUR_OF_DAY) 
					+ ":" + now.get(Calendar.MINUTE) 
					+ ":" + now.get(Calendar.SECOND);
		int mm = now.get(Calendar.MONTH) + 1;

		
		if(scoretype.equals("2")||scoretype.equals("3")||scoretype.equals("22")) {
		//scoretype =22 是為了平時及期末上傳時寫入期末成績時檢查用的特例,
		//暫存平時及期末成績使用scoretype=2
			
		/*
		 * 決定該班級之畢業年級
		 * 特例:日二專建築為三年
		 * 
		 * 畢業班之level會調整為3,檢查畢業班上傳期末及學期成績之時間 
		 */
			/*
			gradept = selclass.substring(1, 3);
			hql = "select c5 from Code5 c5 Where category='GradYear' And idno like '" + gradept + "'";
			List<Code5> gyearlist= dao.submitQuery(hql);
			if(gyearlist.size() > 0) {
				if(gyearlist.size() > 1) {
					for(Iterator<Code5> gyearIter = gyearlist.iterator(); gyearIter.hasNext(); ) {
						Code5 code5 = gyearIter.next();
						if(code5.getIdno().trim().equals(selclass.substring(1, 4))) {
							gradYear = Integer.parseInt(code5.getName());
							break;
						} else {
							gradYear = Integer.parseInt(code5.getName());
						}
					}
				} else {
					gradYear = Integer.parseInt(gyearlist.get(0).getName());
				}
			}
			else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.gradYearNotSet"));
				return messages;
			}
			
			if(Integer.parseInt(selclass.substring(4, 5)) == gradYear && sterm.equalsIgnoreCase("2")) graduated = true;
			if(graduated)level = "3";
			*/
			if(Toolket.chkIsGraduateClass(selclass) && sterm.equalsIgnoreCase("2")) level = "3";
			else level = "2";

			//4,5月直接以level 3畢業班的方式查核是否能上傳成績
			//if((mm == 4 || mm == 5) &&  graduated) level="3";
			//else level = "2";
			
			//6月份欲上傳成績則須先檢視是否為畢業班上傳成績時間
			//(level->1:期中成績 2:期末成績 3:畢業成績 4:暑修成績 5:操行成績 6:教學評量)
			/*
			if(mm == 6) {
				sql = "select * from optime1 o where level='3' And depart='" + dept + "'";
				tmplist = jdbcDao.findAnyThing(sql);
				if(tmplist.size() == 0) level = "2";
				else {
					Map timemap = (Map)tmplist.get(0);
					begin_date = timemap.get("begin_date").toString();
					begin_time = timemap.get("begin_time").toString();
					end_date = timemap.get("end_date").toString();
					end_time = timemap.get("end_time").toString();
					
					begin_year = Integer.parseInt(begin_date.substring(0, 2)) + 1911;
					begin_month = Integer.parseInt(begin_date.substring(3, 5)) - 1;
					begin_day = Integer.parseInt(begin_date.substring(6));
					begin_hour = Integer.parseInt(begin_time.substring(0, 2));
					begin_minute = Integer.parseInt(begin_time.substring(3, 5));
					begin_second = Integer.parseInt(begin_time.substring(6));
					
					end_year = Integer.parseInt(end_date.substring(0, 2)) + 1911;
					end_month = Integer.parseInt(end_date.substring(3, 5)) - 1;
					end_day = Integer.parseInt(end_date.substring(6));
					end_hour = Integer.parseInt(end_time.substring(0, 2));
					end_minute = Integer.parseInt(end_time.substring(3, 5));
					end_second = Integer.parseInt(end_time.substring(6));
					
					beginCal.set(begin_year, begin_month, begin_day, begin_hour, begin_minute, begin_second);
					endCal.set(end_year, end_month, end_day, end_hour, end_minute, end_second);
					//log.debug("beginCal: " + beginCal.toString());
					//log.debug("endCal: " + endCal.toString());
					
					if(now.compareTo(beginCal) < 0 || now.compareTo(endCal) > 0 ) {
						if(graduated) level = "3";
						else level = "2";
					}
					else {
						if(graduated)level = "3";
						else level = "2";
					}
				}
			}
			*/
		} else if(scoretype.equals("1")) {
			level = "1";
		} else if(scoretype.equals("5")) {
			level = "5";
		}
		
		
		sql = "select * from optime1 o where level='" + level + "' And depart='" + dept + "'";
		tmplist = jdbcDao.findAnyThing(sql);
		if(tmplist.size() == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.cantUploadScoreNow"));
			return messages;
		}else {
			Map timemap = (Map)tmplist.get(0);
			begin_date = timemap.get("begin_date").toString();
			begin_time = timemap.get("begin_time").toString();
			end_date = timemap.get("end_date").toString();
			end_time = timemap.get("end_time").toString();
			
			String[] begind = begin_date.split("/");
			String[] endd = end_date.split("/");
			String[] begint = begin_time.split(":");
			String[] endt = end_time.split(":");
			
			begin_year = Integer.parseInt(begind[0]) + 1911;
			begin_month = Integer.parseInt(begind[1]) - 1;
			begin_day = Integer.parseInt(begind[2]);
			begin_hour = Integer.parseInt(begint[0]);
			begin_minute = Integer.parseInt(begint[1]);
			begin_second = Integer.parseInt(begint[2]);
			
			end_year = Integer.parseInt(endd[0]) + 1911;
			end_month = Integer.parseInt(endd[1]) - 1;
			end_day = Integer.parseInt(endd[2]);
			end_hour = Integer.parseInt(endt[0]);
			end_minute = Integer.parseInt(endt[1]);
			end_second = Integer.parseInt(endt[2]);
			
			beginCal.set(begin_year, begin_month, begin_day, begin_hour, begin_minute, begin_second);
			endCal.set(end_year, end_month, end_day, end_hour, end_minute, end_second);
			
			log.debug("beginCal: " + beginCal.toString());
			log.debug("endCal: " + endCal.toString());
			
			//對於平時及期末成績上傳只檢查是否超過截止時間,其他的則一律檢查開始及截止時間
			if(scoretype.equals("2")) {
				if(now.compareTo(endCal) > 0) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.cantUploadScoreNow"));
				}
			}else {
				if(now.compareTo(beginCal) < 0 || now.compareTo(endCal) > 0 ) {
					log.debug("now: " + now.toString());
					log.debug("compare result : " + now.compareTo(beginCal) + " : " + now.compareTo(endCal));
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.cantUploadScoreNow"));
					return messages;
				}
				
			}
			
			return messages;
		}
	}
	
	public boolean chkNoMiddleScore(String selclass, String cscode){
		boolean result = false;
		String sql = "Select s.* From Seld s, Dtime d Where s.score2 is not null";
		sql = sql + " and s.Dtime_oid=d.Oid and d.depart_class='" + selclass + "'";
		sql = sql + " and d.cscode='" + cscode + "'";
		sql = sql + " and d.Sterm='" + Toolket.getSysParameter("School_term") + "'";
		
		List sList = jdbcDao.findAnyThing(sql);
		if(sList.isEmpty()){
			result = true;
		}else{
			result = false;
		}
		return result;
	}
	
	public float[] calTermAvgScore(String studentNo, String school_year, String school_term) {
		float score = 0f, totalScore = 0f, avgScore = 0f, totalCredit = 0f;
		String hql = "";

		if(!studentNo.equals("") && !school_year.equals("") && !school_term.equals("")) {
			hql = "Select s From ScoreHist s Where studentNo='" + 
			studentNo + "'";
			hql = hql + " And schoolYear=" + school_year ;
			hql = hql + " And schoolTerm='" + school_term + "'";
		} else {
			return new float[] {-1F, -1F};
		}
		//log.debug("calTermAvgScore->find ScoreHist:sql:[" + hql +"]");
		List<ScoreHist> scorehist = (List<ScoreHist>)dao.submitQuery(hql);
		
		//log.debug("calTermAvgScore->find ScoreHist:size:[" + scorehist.size() +"]");
		ScoreHist sch = new ScoreHist();
		
		if(scorehist.size() != 0) {
			for(Iterator<ScoreHist> schIter = scorehist.iterator(); schIter.hasNext();) {
				sch = schIter.next();
				//log.debug("calTermAvgScore->score:" + sch.getScore() +", credit:" + sch.getCredit());
				if(sch.getEvgrType().equals("6") || sch.getEvgrType().equals("5") || sch.getEvgrType().equals("4")) {
					if(sch.getScore() != null) {
						totalScore = totalScore + (sch.getScore() * sch.getCredit());
						totalCredit = totalCredit + sch.getCredit();
					}
				} else {
					if(Toolket.isMasterStudent(sch.getStudentNo())
						&& !Toolket.isMasterClass(sch.getStdepartClass())
						&& sch.getEvgrType().equals("2")){
						//研究所學生隨班附讀不計分
					}else if(sch.getCscode().equalsIgnoreCase("GA035") ||
							sch.getCscode().equalsIgnoreCase("GB041") ||
							sch.getCscode().equalsIgnoreCase("GB042")){
						//碩士論文不列入學期平均成績; ps.轉歷史成績時已排除
					}else if(sch.getScore() != null) {
						totalScore = totalScore + (sch.getScore() * sch.getCredit());
						totalCredit = totalCredit + sch.getCredit();
					}else{
						//該科目成績為零分
						totalCredit = totalCredit + sch.getCredit();
					}
				}
			}
			//log.debug("student_no:" + studentNo + ",totalScore:" + totalScore + ", totalCredit:" + totalCredit);
			if(totalCredit == 0f){
				return new float[] {(float)((Math.round(totalScore * 10000.0F))/10000F), 0f};
			}else{
				return new float[] {(float)((Math.round(totalScore/totalCredit * 10000.0F))/10000F), (float)totalCredit};
			}
		} else {
			return new float[] {-1F, -1F};
		}
	}
	
	public ActionMessages updateOrCreatAvgScore(String studentNo, String school_year,
			String school_term, String depart_class, float score, float credit) {
		ActionMessages msgs = new ActionMessages();
		List tmplist = new ArrayList();
		Stavg stavg = new Stavg();
		String sql = "";
		String hql = "Select s From Stavg s Where studentNo='" + studentNo;
		hql = hql + "' And schoolYear=" + school_year + " And schoolTerm='" + school_term + "'";
		
		tmplist = dao.submitQuery(hql);
		if(tmplist.size() > 0) {
			stavg = (Stavg)tmplist.get(0);
			stavg.setStudentNo(studentNo);
			stavg.setSchoolYear(Short.parseShort(school_year));
			stavg.setSchoolTerm(school_term);
			stavg.setDepartClass(depart_class);
			stavg.setScore(score);
			stavg.setTotalCredit(credit);
			dao.saveObject(stavg);
		} else {
			sql = "Insert into Stavg (student_no, school_year, school_term, depart_class, score, total_credit)";
			sql = sql + " Values('" + studentNo + "'," + school_year + ",'" + school_term + "','" + depart_class;
			sql = sql + "'," + score + "," + credit + ")";
			//log.debug(studentNo + ":" + score + ":" + credit);
			try {
				int result = jdbcDao.getJdbcTemplate().update(sql);
				if(result != 1) {
					msgs.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.creatAvgScoreFail"));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Exception.generic", e.getMessage()));
			}
		}
		
		return msgs;
	}
	
	public ActionMessages txUpdateAvgScore(String schoolYear, String schoolTerm, String clazzFilter){
		ActionMessages msgs = new ActionMessages();
		
		int count = 0, len = 0, progress = 0;
		String sql = "";
		String hql = "";
		float[] TermScore;
		List<Seld> seldlist = new ArrayList();
		List<Student> students = new ArrayList();
				
		hql = "Select s From Student s Where  s.departClass like '" + clazzFilter + "%'" + " Order By s.studentNo";
			
		students = dao.StandardHqlQuery(hql);
			
		if(students.isEmpty()){
			/* msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.Score2HistStuNotFound")); */
			return msgs;
		}else{
			// 重新計算每一位學生之學期成績並Update或Create 學期平均成績
			len = students.size();
			count = 0;
			ActionMessages error = new ActionMessages();
			
			for(Student stud:students) {
				TermScore = calTermAvgScore(stud.getStudentNo(), schoolYear, schoolTerm);
				
				//student = this.findStudentByStudentNo(studentNos[i]);
				if(TermScore[0]==-1f && TermScore[1]==-1f){
					error = updateOrCreatAvgScore(stud.getStudentNo(), schoolYear,
							schoolTerm, stud.getDepartClass(), 0f, 0f);
				}else{
					error = updateOrCreatAvgScore(stud.getStudentNo(), schoolYear,
							schoolTerm, stud.getDepartClass(), TermScore[0], TermScore[1]);
				}
				
				if(!error.isEmpty()) {
					msgs.add(error);
					return msgs;
				}
				/*
				if(++count%10 == 0){
					progress = 0 + Math.round(10 *(count/len));
					setRunStatus("TermScore2ScoreHist", "Update Avarage Score:", progress, 100,(double)progress, count + "/" + len);
				}
				*/
			}

			//setRunStatus("TermScore2ScoreHist", "Finished:", 100, 100, 100d, "yes");
		}
		return msgs;
	}
	
	/**
	 * 學期成績轉歷史檔
	 * 
	 * @param String schoolYear
	 * @param String schoolTerm
	 * @param String depart
	 * @param String tmode
	 * 
	 * @return org.apache.struts.action.ActionMessages msgs
	 * @see tw.edu.chit.service.ScoreManager#termScore2ScoreHist(java.lang.String, java.lang.String, java.lang.String)
	 */
	public ActionMessages txTermScore2ScoreHist(String schoolYear, String schoolTerm, String clazzFilter) {
		
		ActionMessages msgs = new ActionMessages();
		
		int i;
		String sql = "", subsql = "";
		String[] departs;
		String subhql = "";
		String hql = "";
		float[] TermScore;
		List<Seld> seldlist = new ArrayList();
		List studs = new ArrayList();
		List<Student> students = new ArrayList();
		
		/*
		sql = "Select idno From code5 Where category='SchoolType' And name like '" + depart + "%'";
		List tmplist = jdbcDao.findAnyThing(sql);
		if(tmplist.size() > 0) {
			departs = new String[tmplist.size()]; 
			for(i = 0; i < tmplist.size(); i++) {
				departs[i] =  ((Map)tmplist.get(i)).get("idno").toString().trim();
			}
		} else {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.Score2HistDepNotFound"));
			return msgs;
		}
		tmplist = new ArrayList();
		setRunStatus("TermScore2ScoreHist", "0->Start:", 0,0,0d, "no");
		//找出所有在Seld中屬於該部制的學生
		for(i = 0; i < departs.length; i++ ) {
			hql = "Select Distinct s From Student s, Seld x Where s.studentNo=x.studentNo" 
			+ " and s.departClass like '" + departs[i] + "%'" + " Order By s.studentNo";
			
			students.addAll(dao.StandardHqlQuery(hql));
		}
		*/
		
		/*
		sql = "Select Distinct depart_class From stmd Where depart_class like '" + clazzFilter + "%' order by depart_class";
		List phyClasses = jdbcDao.findAnyThing(sql);
		String cond = "";	//設定畢業班查詢子句
		String claz = "";
		for(Iterator cndIter=phyClasses.iterator(); cndIter.hasNext();){
			claz = ((Map)cndIter.next()).get("depart_class").toString();
			if(Toolket.isGraduateClass(claz)){
				cond = cond + "'" + claz + "',";
			}
		}
		if(!cond.equals("")){
			cond ="(" + cond.substring(0, cond.length()-1) + ")";
		}

		if(tmode.equals("0")){
			hql = "Select s From Student s Where s.departClass like '" + clazzFilter + "%'" + " Order By s.studentNo";
			students = dao.StandardHqlQuery(hql);
		}else{
			if(tmode.equals("1") && !cond.equals("")){
				hql = "Select s From Student s Where s.departClass in " + cond + " Order By s.studentNo";
				students = dao.StandardHqlQuery(hql);
			}else if(tmode.equals("2")){
				if(cond.equals("")){
					hql = "Select s From Student s Where s.departClass like '" + clazzFilter + "%'" + " Order By s.studentNo";
				}else{
					hql = "Select s From Student s Where s.departClass not in " + cond + " Order By s.studentNo";
				}
				students = dao.StandardHqlQuery(hql);
			}
		}
		*/
		
		hql = "Select s From Student s Where s.departClass like '" + clazzFilter + "%'" + " Order By s.studentNo";
		students = dao.StandardHqlQuery(hql);
		if(students.isEmpty()){
			/*msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.Score2HistStuNotFound")); */
			return msgs;
		}
		
		//setRunStatus("TermScore2ScoreHist", "1->Search Departs:", 10, 100, 10d, "no");

		//找出所有屬於該部制學生之所有選課資料
		List<Seld> selds;
		for(Student stud:students) {
			hql = "select s From Seld s, Dtime d Where s.studentNo='" + stud.getStudentNo() + "'" +
			" and s.dtimeOid=d.oid and d.sterm='" + schoolTerm +  "'"; 
			selds = dao.submitQuery(hql);
			if(!selds.isEmpty()){
				if(!seldlist.addAll(selds)) {
					//log.debug("Score2HistSrchSeldErr:" + stud.getStudentNo()+", size:" + selds.size());
					msgs.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.Score2HistSrchSeldErr"));
					return msgs;

				}
			}
			
			//刪除該部制該學期學生之歷史成績資料,但不包含暑修成績(evgrtype="3")
			sql = "Delete From ScoreHist Where school_year=" + schoolYear 
			+ " And school_term='" + schoolTerm +"' And student_no='"
			+ stud.getStudentNo() + "' And (evgr_type='1' or evgr_type='2')";
			
			//jdbcDao.getJdbcTemplate().execute(sql);
			jdbcDao.getJdbcTemplate().update(sql);
			//log.debug(sql);
			//jdbcDao.updateAnyThing(sql);

			/*
			hql = "Delete ScoreHist Where schoolYear=" + schoolYear 
			+ " And schoolTerm='" + schoolTerm +"' And studentNo='"
			+ stud.getStudentNo() + "' And (evgrType='1' or evgrType='2')";
			log.debug(hql);
			
			dao.removeAnyThing1(hql);
			*/
		}
		//setRunStatus("TermScore2ScoreHist", "3->Delete Score History:", 30, 100, 30d, "no");
			
		//將學生選課Seld資料新增至ScoreHist
		Seld seld = new Seld();
		Dtime dtime = new Dtime();
		Student student = new Student();
		Graduate graduate = new Graduate(); 
		String csdepartClass="", stdepartClass="";
		int progress = 0;
		int count = 0;
		int len = 0;
		if(!seldlist.isEmpty()) {
			len =  seldlist.size();
			// 確認該成績是屬於正常或隨班附讀,新增至ScoreHist
			for(Iterator<Seld> seldsIter = seldlist.iterator(); seldsIter.hasNext();) {
				seld = seldsIter.next();
				//碩士論文不納入學期及歷史成績計算
				if(seld.getCscode() != null && (seld.getCscode().equalsIgnoreCase("GA035") || 
						seld.getCscode().equalsIgnoreCase("GB041") ||
						seld.getCscode().equalsIgnoreCase("GB042"))){
					continue;
				}
				ScoreHist scorehist = new ScoreHist();
				hql = "Select d From Dtime d Where d.oid=" + seld.getDtimeOid();
				
				dtime = (Dtime)dao.submitQuery(hql).get(0);
				csdepartClass = dtime.getDepartClass().trim();
				seld.setDepartClass(csdepartClass);
				
				hql = "Select s From Student s Where studentNo='" + seld.getStudentNo() + "'";
				studs = dao.submitQuery(hql);
				if(!studs.isEmpty()){
					student = (Student)studs.get(0);
					stdepartClass = student.getDepartClass().trim();
				}else{
					continue;
					/*
					hql = "Select g From Graduate g Where studentNo='" + seld.getStudentNo() + "'";
					studs = dao.submitQuery(hql);
					if(!studs.isEmpty()){
						graduate = (Graduate)studs.get(0);
						stdepartClass = graduate.getDepartClass().trim();
					}
					*/					
				}
				
				seld.setStdepartClass(stdepartClass);
				
				if(!stdepartClass.equals(csdepartClass) && stdepartClass.length()==5) {
					if(stdepartClass.substring(0, 3).equals(csdepartClass.substring(0, 3)) && 
							stdepartClass.substring(4, 5).equals(csdepartClass.substring(4, 5))) {
						scorehist.setEvgrType("1");
					} else {
						scorehist.setEvgrType("2");
					}
				}else{
					scorehist.setEvgrType("1");
				}
				
				scorehist.setStudentNo(seld.getStudentNo());
				scorehist.setSchoolYear(Short.parseShort(schoolYear));
				scorehist.setSchoolTerm(schoolTerm);
				scorehist.setStdepartClass(csdepartClass);
				scorehist.setCscode(dtime.getCscode());
				scorehist.setOpt(dtime.getOpt());
				scorehist.setCredit(dtime.getCredit());
				if(seld.getScore() != null){
					scorehist.setScore((float)(Math.round(seld.getScore()+0.001d)));
				}else{
					scorehist.setScore(0f);
				}
					
				
				dao.saveObject(scorehist);
				/*
				if(++count%50 == 0){
					progress = 30 + Math.round(50 *(count/len));
					//setRunStatus("TermScore2ScoreHist", "4->Insert Into Score History:", progress, 100, (double)progress, count + "/" + len);
				}
				*/
			}
			
		}

		//將學生操行成績Just資料新增至ScoreHist
		progress = 0;
		count = 0;
		List<Just> justList = new ArrayList();
		/*
		for(i = 0; i < departs.length; i++ ) {
			justList.addAll(dao.findJustByDepart(departs[i]));
		}
		*/
		hql = "Select j From Just j, Student s Where j.studentNo=s.studentNo and s.departClass like '" +
		clazzFilter + "%' order by j.studentNo";
		
		justList= dao.submitQuery(hql);
		
		len = justList.size();
		long score = 0;
		for (Iterator<Just> justIter = justList.iterator(); justIter.hasNext();) {
			Just just = justIter.next();
			
			sql = "Delete From ScoreHist Where school_year=" + schoolYear 
			+ " And school_term='" + schoolTerm +"' And student_no='"
			+ just.getStudentNo() + "' And cscode='99999'";
			
			jdbcDao.getJdbcTemplate().update(sql);
			
			ScoreHist scorehist = new ScoreHist();

			scorehist.setStudentNo(just.getStudentNo());
			scorehist.setSchoolYear(Short.parseShort(schoolYear));
			scorehist.setSchoolTerm(schoolTerm);
			scorehist.setStdepartClass(just.getDepartClass());
			scorehist.setEvgrType("1");
			scorehist.setCscode("99999");
			scorehist.setOpt("1");
			scorehist.setCredit(0f);
			score = Math.round(just.getTotalScore()+0.001d);
			if(score > 95) score=95;
			scorehist.setScore((float)(score));

			dao.saveObject(scorehist);
			/*
			if(++count%10 == 0){
				progress = 70 + Math.round(20 * (count / len));
				setRunStatus("TermScore2ScoreHist",
						"5->Insert Just Score History:", progress, 100,
						(double) progress, count + "/" + len);
			}
			*/
		}
		
		return msgs;
	}
	
	/**
	 * 重新計算學業成績並更新MasterData
	 * 
	 * @param props
	 */
	public void updateMasterData(Map props) {
		String studentNo = props.get("student_no").toString().trim();
		String year = props.get("school_year").toString().trim();
		String term = props.get("school_term").toString().trim();

		List<Stavg> stavgs = findStavgBy(new Stavg(studentNo));
		float scoreTotal = 0.0F;
		int m = 0;
		for (Stavg stavg : stavgs) {
			if (stavg.getScore() == null || stavg.getScore() == 0.0F) {
				m++; // 避免有學期平均成績為0加總後總成績計算錯誤
				continue;
			}
			scoreTotal += stavg.getScore();
		}

		String average = new DecimalFormat(",##0.0").format((scoreTotal
				/ (stavgs.size() - m) + 0.001D));

		MasterData md = new MasterData();
		md.setStudentNo(studentNo);
		md.setSchoolYear(Short.valueOf(year));
		md.setSchoolTerm(Short.valueOf(term));
		List<MasterData> mds = findMasterDataBy(md);
		if (!mds.isEmpty() && mds.size() == 1) {
			// 前端是所有碩士生都會做更新,應該不會將非二年級生做更新
			// 因為二年級才會找到MasterData資料
			md = mds.get(0);
			md.setThesesScore(Float.valueOf(average));
			modifyMasterData(md);
			Toolket.sendMasterScoreInfoByQueue(md,
					IConstants.SYNC_DO_TYPE_UPDATE);
		}
	}

	/**
	 * 查詢碩士成績基本資料
	 * 
	 * @param md
	 * @return
	 */
	public List<MasterData> findMasterDataBy(MasterData md) {
		return dao.findMasterDataBy(md);
	}
	
	/**
	 * 負責以學號查詢碩士成績基本資料
	 * 
	 * @param studentNo 學號
	 * @return tw.edu.chit.model.MasterData object
	 */
	public MasterData findMasterByStudentNo(String studentNo) {
		List<MasterData> md = dao.findMasterByStudentNO(studentNo);
		if (!md.isEmpty())
			return md.get(0);
		else
			return null;
	}

	/**
	 * 負責以ID取得MasterData物件
	 * 
	 * @param oid identify
	 * @return tw.edu.chit.model.MasterData object
	 */
	public MasterData findMasterDataById(Integer id) {
		return dao.findMasterDataById(id);
	}

	/**
	 * 負責更新MasterData物件
	 * 
	 * @param tw.edu.chit.model.MasterData object
	 */
	public void modifyMasterData(MasterData md) {
		if (md != null) {
			dao.modifyMasterData(md);
			Toolket.sendMasterScoreInfoByQueue(md,
					IConstants.SYNC_DO_TYPE_UPDATE);
		} else
			throw new IllegalArgumentException(
					"MasterData object may not be NULL.");
	}

	/**
	 * 負責刪除MasterData物件
	 * 
	 * @param tw.edu.chit.model.MasterData object
	 */
	public void deleteMasterData(MasterData md) {
		if (md != null) {
			dao.deleteMasterData(md);
			Toolket.sendMasterScoreInfoByQueue(md,
					IConstants.SYNC_DO_TYPE_DELETE);
		} else
			throw new IllegalArgumentException(
					"MasterData object may not be NULL.");

	}

	/**
	 * 負責新增MasterData物件
	 * 
	 * @param tw.edu.chit.model.MasterData object
	 */
	public void addMasterData(MasterData md) {
		if (md != null) {
			dao.addMasterData(md);
			Toolket.sendMasterScoreInfoByQueue(md,
					IConstants.SYNC_DO_TYPE_INSERT);
		} else
			throw new IllegalArgumentException(
					"MasterData object may not be NULL.");
	}

	/**
	 * 查詢Stavg物件
	 * 
	 * @param studentNo 學生學號
	 * @param year 學年
	 * @param term 學期
	 * @return tw.edu.chit.model.Stavg object
	 */
	@SuppressWarnings("unchecked")
	public Stavg findStavgBy(String studentNo, String year, String term) {
		String hql = "FROM Stavg s WHERE s.studentNo = '" + studentNo
				+ "' AND s.schoolYear = " + year + " AND s.schoolTerm = "
				+ term;
		List<Stavg> stavgs = dao.StandardHqlQuery(hql);
		if (stavgs.isEmpty())
			return null;
		else
			return stavgs.get(0);
	}
	
	/**
	 * 查詢Stavg物件
	 * 
	 * @comment 包括Like Expression
	 * @param css tw.edu.chit.models.Stavg Object
	 * @return java.util.List List of tw.edu.chit.models.Stavg Objects
	 */
	public List<Stavg> findStavgBy(Stavg stavg) {
		return dao.findStavgBy(stavg);
	}
	
	/**
	 * 取得Stavg清單物件
	 * 
	 * @param departClass 學生班級
	 * @param year 學年
	 * @param term 學期
	 * @return java.util.List List of Stavg objects
	 */
	@SuppressWarnings("unchecked")
	public List<Stavg> calStavgRank(String departClass, String year, String term) {
		String hql = "FROM Stavg s WHERE s.departClass = '" + departClass
				+ "' AND s.schoolYear = " + year + " AND s.schoolTerm = "
				+ term + " ORDER BY s.score DESC";
		return dao.StandardHqlQuery(hql);
	}
	
	
	 /**	  
	 * 爛東西
	 */
	public List findScoreNotUpload(String campus, String filtertype, String scope) throws SQLException{
		/**
		 * 去他X的爛東西我把它廢了
		ActionMessages msgs = new ActionMessages();
		String hql = "";
		String campSchool = "";
		List<Clazz> clazzList = new ArrayList();
		List<Dtime> dtimeList = new ArrayList();
		List<Dtime> alldtimeList = new ArrayList();
		List tmplist = new ArrayList();
		List retlist = new ArrayList();
		String clazzes = "";
		String sql = "";
		boolean isNotUpload = false;
		Calendar beginCal =  Calendar.getInstance();
		Calendar endCal =  Calendar.getInstance();
		Calendar beginCal2 =  Calendar.getInstance();
		Calendar endCal2 =  Calendar.getInstance();
		Calendar uploadCal =  Calendar.getInstance();
		int[] idate = new int[3];
		int[] itime = new int[3];
		String begin_date = "";
		String begin_time = "";
		String end_date = "";
		String end_time = "";

		int level = 0;
		
		
		String sterm = Toolket.getSysParameter("School_term");

		Calendar now = Calendar.getInstance();
		String nows = "" + (now.get(Calendar.YEAR)-1911) 
					+ "/" + (now.get(Calendar.MONTH) + 1) 
					+ "/" + now.get(Calendar.DATE) 
					+ " " + now.get(Calendar.HOUR_OF_DAY) 
					+ ":" + now.get(Calendar.MINUTE) 
					+ ":" + now.get(Calendar.SECOND);
		
		if(filtertype.equals("1")){
			level = 1;
		}else if(filtertype.equals("2")) {
			if(scope.equals("1")) {
				level = 2;
			}else if(scope.equals("2")){
				level = 3;
			}else if(scope.equals("3")){
				level = 2;
			}
		}
		
		//TODO: filtertype=2 && scope=1 時需比較的時間有兩種,一般班及畢業班
		
		sql = "select * from optime1 o where level='" + level + "' And depart='" + campus + "'";
		tmplist = jdbcDao.findAnyThing(sql);
		if(tmplist.size() == 0) {
			throw new SQLException("Cant find records on optime1 about departClass=" + campus + " and level=" + level); 
			//beginCal.set(now.get(Calendar.YEAR), 0, 1, 0, 0, 0);
			//endCal.set(now.get(Calendar.YEAR), 11, 31, 23, 59, 59);
		}else {
			Map timemap = (Map)tmplist.get(0);
			begin_date = timemap.get("begin_date").toString();
			begin_time = timemap.get("begin_time").toString();
			end_date = timemap.get("end_date").toString();
			end_time = timemap.get("end_time").toString();
			
			String[] dates = new String[3];
			if(begin_date.indexOf("/")>0){
				dates = begin_date.split("/");
			}else{
				dates = begin_date.split("-");
			}
			idate[0] = Integer.parseInt(dates[0]) + 1911;
			idate[1] = Integer.parseInt(dates[1]) - 1;
			idate[2] = Integer.parseInt(dates[2]);
			itime[0] = Integer.parseInt(begin_time.substring(0, 2));
			itime[1] = Integer.parseInt(begin_time.substring(3, 5));
			itime[2] = Integer.parseInt(begin_time.substring(6));
			beginCal.set(idate[0], idate[1], idate[2], itime[0], itime[1], itime[2]);
			
			if(end_date.indexOf("/")>0){
				dates = end_date.split("/");
			}else{
				dates = end_date.split("-");
			}
			idate[0] = Integer.parseInt(dates[0]) + 1911;
			idate[1] = Integer.parseInt(dates[1]) - 1;
			idate[2] = Integer.parseInt(dates[2]);
			itime[0] = Integer.parseInt(end_time.substring(0, 2));
			itime[1] = Integer.parseInt(end_time.substring(3, 5));
			itime[2] = Integer.parseInt(end_time.substring(6));
			
			endCal.set(idate[0], idate[1], idate[2], itime[0], itime[1], itime[2]);
		}
		
		//查詢學期,期末全部(一般班及畢業班)未上傳資料
		if(filtertype.equals("2") && scope.equals("1")){
			sql = "select * from optime1 o where level=3 And depart='" + campus + "'";
			tmplist = jdbcDao.findAnyThing(sql);
			if(tmplist.size() == 0) {
				throw new SQLException("Cant find records on optime1 about departClass=" + campus + " and level=3"); 
			}else {
				Map timemap = (Map)tmplist.get(0);
				begin_date = timemap.get("begin_date").toString();
				begin_time = timemap.get("begin_time").toString();
				end_date = timemap.get("end_date").toString();
				end_time = timemap.get("end_time").toString();
				
				String[] dates = new String[3];
				if(begin_date.indexOf("/")>0){
					dates = begin_date.split("/");
				}else{
					dates = begin_date.split("-");
				}
				idate[0] = Integer.parseInt(dates[0]) + 1911;
				idate[1] = Integer.parseInt(dates[1]) - 1;
				idate[2] = Integer.parseInt(dates[2]);
				itime[0] = Integer.parseInt(begin_time.substring(0, 2));
				itime[1] = Integer.parseInt(begin_time.substring(3, 5));
				itime[2] = Integer.parseInt(begin_time.substring(6));
				beginCal2.set(idate[0], idate[1], idate[2], itime[0], itime[1], itime[2]);
				
				if(end_date.indexOf("/")>0){
					dates = end_date.split("/");
				}else{
					dates = end_date.split("-");
				}
				idate[0] = Integer.parseInt(dates[0]) + 1911;
				idate[1] = Integer.parseInt(dates[1]) - 1;
				idate[2] = Integer.parseInt(dates[2]);
				itime[0] = Integer.parseInt(end_time.substring(0, 2));
				itime[1] = Integer.parseInt(end_time.substring(3, 5));
				itime[2] = Integer.parseInt(end_time.substring(6));
				
				endCal2.set(idate[0], idate[1], idate[2], itime[0], itime[1], itime[2]);
			}
			
		}
		//log.debug("beginCal: " + beginCal.toString());
		//log.debug("endCal: " + endCal.toString());		
		
		hql = "select c From Code5 c Where category='SchoolType' And name like '" + campus + "%'";
		List campList = dao.submitQuery(hql);
		log.debug("findScoreNotUpload->campList:" + campus + " :" + campList.size());

		if(!campList.isEmpty()){
			for(Iterator<Code5> campIter=campList.iterator(); campIter.hasNext();){		
				campSchool = campIter.next().getIdno();
				
				hql = "select d from Dtime d Where d.sterm='" + sterm +
				"' and d.departClass like '" + campSchool + "%' order by d.departClass";	
				log.debug("hql:" + hql);
				dtimeList = dao.submitQuery(hql);
				alldtimeList.addAll(dtimeList);
				log.debug("findScoreNotUpload->dtimeList:" + dtimeList.size() + " alldtimeList:" + alldtimeList.size());
			}
				
			if(filtertype.equals("1")) {	//查詢期中成績上傳不分畢業班與否
				//do nothing
			}else if(filtertype.equals("2")){
				if(scope.equals("1")) {			//全部
					//nothing todo
				}else if((scope.equals("2") && sterm.equals("2"))||scope.equals("3")){	//畢業班 and 畢業班除外
					for(ListIterator<Dtime> dtIter=alldtimeList.listIterator(); dtIter.hasNext();){
						Dtime dtime = dtIter.next();
						if(scope.equals("2")){
							if(!Toolket.chkIsGraduateClass(dtime.getDepartClass())){
								dtIter.remove();	
							}								
						}else if(scope.equals("3")){
							if(Toolket.chkIsGraduateClass(dtime.getDepartClass())){
								dtIter.remove();	
							}																
						}
						
					}
					
				}
					
			}
			
		}

		//移除班會(50000)系時間(T0001),期中則不包括英語及體育
		List<Csno> csList = new ArrayList<Csno>();
		for(ListIterator<Dtime> dtIter=alldtimeList.listIterator(); dtIter.hasNext();){
			Dtime dtime = dtIter.next();
			//log.debug("findScoreNotUpload->dtimeList:" + dtime.getDepartClass());
			if(dtime.getCscode().trim().equals("T0001") ||
					dtime.getCscode().trim().equals("50000")){
					dtIter.remove();	
			}
			
			if(filtertype.equals("1")){
				csList = dao.findCourseByCode(dtime.getCscode());
				if(!csList.isEmpty()){
					String chiName = csList.get(0).getChiName();
					if(chiName.indexOf("英文") != -1 || chiName.indexOf("英語") != -1
							|| chiName.indexOf("體育") != -1){
						dtIter.remove();	
					}
				}
			}

		}
		
		int notupload = -1;
		for(Iterator<Dtime> dtimeIter=alldtimeList.iterator(); dtimeIter.hasNext();){
			notupload = -1;
			Dtime dtime=dtimeIter.next();
			isNotUpload = false;
			uploadCal =  Calendar.getInstance();
			//考慮一科目多教師
			hql = "Select d From DtimeTeacher d Where d.dtimeOid=" + dtime.getOid();
			List<DtimeTeacher> dtList = dao.submitQuery(hql);
			String teachers = "";
			String[] tchs = new String[dtList.size() + 1];
			for(int i = 0; i <= dtList.size(); i++){
				tchs[i] = "";
			}
			if(dtime.getTechid() != null){
				if(!dtime.getTechid().trim().equals("")) {
					teachers = teachers + ",'" + dtime.getTechid().trim() + "'";
					tchs[0] = dtime.getTechid().trim();
				}
			}else{
				tchs[0] = "";
			}

			int cnt = 1;
			for(DtimeTeacher dt:dtList){
				if(dt.getFillscore()==1){
					teachers = teachers + ",'" + dt.getTeachId().trim() + "'";
					tchs[cnt] = dt.getTeachId().trim();
				}
				cnt++;
			}
			if(!teachers.trim().equals("")){
				teachers = teachers.substring(1);
			}
			List<Regstime> rtList = new ArrayList<Regstime>();
			
			if(dtime.getDepartClass().equalsIgnoreCase("164211") && dtime.getCscode().equalsIgnoreCase("TC8I1")){
				log.debug("teachers:" + teachers);
			}
			
			if(!teachers.equals("")) {
				hql = "Select r From Regstime r Where r.departClass='" + dtime.getDepartClass() + 
				"' and r.cscode='" + dtime.getCscode();
				if(filtertype.equals("1")){
					hql = hql + "' and r.ind='1' and r.idno in (" + teachers + ")";
				}else if(filtertype.equals("2")){
					hql = hql + "' and r.ind in ('2','3') and r.idno in (" + teachers + ")";
				}
				
				rtList = dao.submitQuery(hql);
			}
			Regstime regstime = new Regstime();
			
			if(rtList.isEmpty()){	//沒有上傳的記錄
				//Special for English teaching course
				csList = dao.findCourseByCode(dtime.getCscode());
				if(!csList.isEmpty()){
					String chiName = csList.get(0).getChiName();
					sql = "Select * From Seld Where Dtime_oid=" + dtime.getOid();
					if(chiName.indexOf("英文") != -1 || chiName.indexOf("英語") != -1){
						if(filtertype.equals("1")){
							//在前面已經被排除了!!!  sql = sql + " And score2 is null";
						}else{
							sql = sql + " And score3 is null";
						}
						List seldList = jdbcDao.findAnyThing(sql);
						if(!seldList.isEmpty()){
							isNotUpload = true;
							notupload = seldList.size();
						}else{
							isNotUpload = false;
						}
					}
					//TODO: maybe removed ! double check for not upload
					else{
						if(filtertype.equals("1")){	//期中
							sql = sql + " And score2 is null"; 
						}else{
							sql = sql + " And score is null And score3 is null";
						}
						List seldList = jdbcDao.findAnyThing(sql);
						if(!seldList.isEmpty()){
							isNotUpload = true;
							notupload = seldList.size();
						}else{
							isNotUpload = false;
						}
						
					}
				}
				//最原始只有這一行 isNotUpload = true;

				//TODO: maybe removed ! double check for not upload
				if(isNotUpload && notupload < 1){
					sql = "Select * From Seld Where Dtime_oid=" + dtime.getOid();
					if(filtertype.equals("1")){	//期中
						sql = sql + " And score2 is null"; 
					}else{
						sql = sql + " And score is null And score3 is null";
					}
					List seldList = jdbcDao.findAnyThing(sql);
					if(!seldList.isEmpty()){
						isNotUpload = true;
						notupload = seldList.size();
					}else{
						isNotUpload = false;
					}

				}

				if(isNotUpload) {
					sql = "Select * From Seld Where Dtime_oid=" + dtime.getOid();
					int total = jdbcDao.findAnyThing(sql).size();
					
					for(int j=0; j<tchs.length; j++){
						if(!tchs[j].equals("")){
							Map notUpload = new HashMap();
							notUpload.put("departClass", dtime.getDepartClass());
							notUpload.put("deptClassName", Toolket.getClassFullName(dtime.getDepartClass()));
							notUpload.put("cscode", dtime.getCscode());
							notUpload.put("cscodeName", this.findCourseName(dtime.getCscode()));
							if(notupload > 0){
								notUpload.put("memo", notupload + "位學生成績空白(" + total + ")");
							}else{
								notUpload.put("memo", "");
							}
							if(!tchs[j].trim().equals("")){
								notUpload.put("teacherID", tchs[j]);
								hql = "Select e From Empl e Where e.idno='" + dtime.getTechid() + "'";
								List<Empl> emplList = dao.submitQuery(hql);
								if(!emplList.isEmpty()){
									notUpload.put("teacherName", emplList.get(0).getCname());
								}else{
									hql = "Select d From DEmpl d Where d.idno='" + dtime.getTechid() + "'";
									List<DEmpl> demplList = dao.submitQuery(hql);
									if(!demplList.isEmpty()){
										notUpload.put("teacherName", demplList.get(0).getCname());
									}else{
										notUpload.put("teacherName", "");
									}
								}
							}else{
								notUpload.put("teacherID", "");
								notUpload.put("teacherName", "");
							}
							retlist.add(notUpload);							
						}
					}
				}

			
			}else {
				//檢查上傳時間是否在開始及截止時間內,以確定資料有效與否
				int multiTch = rtList.size();
				for(Iterator<Regstime> rtIter=rtList.iterator(); rtIter.hasNext();) {
					regstime = rtIter.next();
					
					String teacherId = regstime.getIdno();
					String uploadTime = regstime.getTtime();
					String uploadType = regstime.getInd().trim();
					String[] upload_date = uploadTime.trim().split(" "); 
					if(upload_date.length != 2) {
						throw new SQLException("optime1 ttime record format error ! which departClass=" + campus + " and level=" + level); 
					}else{
						String[] ul_date = upload_date[0].trim().split("/");
						String[] ul_time = upload_date[1].trim().split(":");
						if(ul_date.length != 3 || ul_time.length != 3){
							throw new SQLException("optime1 ttime record format error ! which departClass=" + campus + " and level=" + level); 
						}else{
							idate[0] = Integer.parseInt(ul_date[0]) + 1911;
							idate[1] = Integer.parseInt(ul_date[1]) - 1;
							idate[2] = Integer.parseInt(ul_date[2]);
							itime[0] = Integer.parseInt(ul_time[0]);
							itime[1] = Integer.parseInt(ul_time[1]);
							itime[2] = Integer.parseInt(ul_time[2]);

							uploadCal.set(idate[0], idate[1], idate[2], itime[0], itime[1], itime[2]);
						}
					}
					if(filtertype.equals("2") && scope.equals("1") && sterm.equals("2") && Toolket.chkIsGraduateClass(regstime.getDepartClass())){
						//畢業班期末或學期成績
						if(uploadCal.compareTo(beginCal2) < 0 || uploadCal.compareTo(endCal2) > 0){
							isNotUpload = true;
						}else{
							isNotUpload = false;
							sql = "Select * From Seld Where Dtime_oid=" + dtime.getOid();
							if(uploadType.equals("2")){	//期末
								if(multiTch == 1){
									sql = sql + " And score3 is null";
								}else if(multiTch > 1){
									sql = sql + " And score is null";
								}
							}else if(uploadType.equals("3")){	//學期
								sql = sql + " And score is null";
							}
							List seldList = jdbcDao.findAnyThing(sql);
							if(!seldList.isEmpty()){
								isNotUpload = true;
								notupload = seldList.size();
							}
						}				
					}else{
						//期中及非畢業班之期末學期成績
						if(uploadCal.compareTo(beginCal) < 0 || uploadCal.compareTo(endCal) > 0){
							isNotUpload = true;
						}else{
							isNotUpload = false;
							sql = "Select * From Seld Where Dtime_oid=" + dtime.getOid();
							if(uploadType.equals("1")){	//期中
								sql = sql + " And score2 is null";
							}else if(uploadType.equals("2")){	//期末
								if(multiTch == 1){
									sql = sql + " And score3 is null";
								}else if(multiTch > 1){
									sql = sql + " And score is null";
								}
							}else if(uploadType.equals("3")){	//學期
								sql = sql + " And score is null";
							}
							List seldList = jdbcDao.findAnyThing(sql);
							if(!seldList.isEmpty()){
								isNotUpload = true;
								notupload = seldList.size();
							}
						}				
					}
					
					//TODO: maybe removed ! double check for not upload
					if(isNotUpload && notupload < 1){
						sql = "Select * From Seld Where Dtime_oid=" + dtime.getOid();
						if(filtertype.equals("1")){	//期中
							sql = sql + " And score2 is null"; 
						}else{
							sql = sql + " And score is null And score3 is null";
						}
						List seldList = jdbcDao.findAnyThing(sql);
						if(!seldList.isEmpty()){
							isNotUpload = true;
							notupload = seldList.size();
						}else{
							isNotUpload = false;
						}

					}

					if(isNotUpload) {
						sql = "Select * From Seld Where Dtime_oid=" + dtime.getOid();
						int total = jdbcDao.findAnyThing(sql).size();
						Map notUpload = new HashMap();
						notUpload.put("departClass", dtime.getDepartClass());
						notUpload.put("deptClassName", Toolket.getClassFullName(dtime.getDepartClass()));
						notUpload.put("cscode", dtime.getCscode());
						notUpload.put("cscodeName", this.findCourseName(dtime.getCscode()));
						if(notupload > 0){
							notUpload.put("memo", notupload + "位學生成績空白(" + total + ")");
						}else{
							notUpload.put("memo", "");
						}
						
						if(!teacherId.trim().equals("")){
							notUpload.put("teacherID", teacherId);
							hql = "Select e From Empl e Where e.idno='" + teacherId + "'";
							List<Empl> emplList = dao.submitQuery(hql);
							if(!emplList.isEmpty()){
								notUpload.put("teacherName", emplList.get(0).getCname());
							}else{
								hql = "Select d From DEmpl d Where d.idno='" + teacherId + "'";
								List<DEmpl> demplList = dao.submitQuery(hql);
								if(!demplList.isEmpty()){
									notUpload.put("teacherName", demplList.get(0).getCname());
								}else{
									notUpload.put("teacherName", "");
								}
							}
						}else{
							notUpload.put("teacherID", "");
							notUpload.put("teacherName", "");
						}
						retlist.add(notUpload);
					}

					
				}	//End for
				
			}	//end rtList.isEmpty()
			

		}	//End Main for loop

		return retlist;
		*/
		return null;
	}
	
	public List findRegsScoreLoss(String scope){
		String hql = "";
		int maxRcnt = 0, rcnt = 0;
		List retList = new ArrayList();
		String sterm = Toolket.getSysParameter("School_term");
		hql = "select d from Dtime d Where d.sterm='" + sterm + "'";
		List<Dtime> dtimeList = dao.submitQuery(hql);
		if(!dtimeList.isEmpty()){
			for(ListIterator<Dtime> dtimeIter=dtimeList.listIterator(); dtimeIter.hasNext();){
				Dtime dtime = dtimeIter.next();
				//log.debug("findCantExam->educate clazz " + clazz.getClassNo() + ":" + clazz.getClassFullName());
				if(scope.equals("2")){
					if(!Toolket.chkIsGraduateClass(dtime.getDepartClass())){
						dtimeIter.remove();	
					}								
				}else if(scope.equals("3")){
					if(Toolket.chkIsGraduateClass(dtime.getDepartClass())){
						dtimeIter.remove();		
					}																
				}
			}
		}
		
		for(ListIterator<Dtime> dtimeIter=dtimeList.listIterator(); dtimeIter.hasNext();){
			Dtime dtime = dtimeIter.next();
			String departClass = dtime.getDepartClass();
			String cscode = dtime.getCscode();
			String subjectName = this.findCourseName(cscode);
			String deptClassName = Toolket.getClassFullName(departClass);
			int DtimeOid = dtime.getOid();
			String teacherID = dtime.getTechid();
			maxRcnt = 0;
			
			hql = "Select r From Regs r Where r.dtimeOid=" + dtime.getOid() + " order by r.studentNo";
			List<Regs> regsList = dao.submitQuery(hql);
			for(Regs regs:regsList){
				rcnt = 0;
				if(regs.getScore01() != null) rcnt++;
				if(regs.getScore02() != null) rcnt++;
				if(regs.getScore03() != null) rcnt++;
				if(regs.getScore04() != null) rcnt++;
				if(regs.getScore05() != null) rcnt++;
				if(regs.getScore06() != null) rcnt++;
				if(regs.getScore07() != null) rcnt++;
				if(regs.getScore08() != null) rcnt++;
				if(regs.getScore09() != null) rcnt++;
				if(regs.getScore10() != null) rcnt++;
				if(maxRcnt < rcnt) maxRcnt = rcnt;
			}
			for(Regs regs:regsList){
				rcnt = 0;
				if(regs.getScore01() != null) rcnt++;
				if(regs.getScore02() != null) rcnt++;
				if(regs.getScore03() != null) rcnt++;
				if(regs.getScore04() != null) rcnt++;
				if(regs.getScore05() != null) rcnt++;
				if(regs.getScore06() != null) rcnt++;
				if(regs.getScore07() != null) rcnt++;
				if(regs.getScore08() != null) rcnt++;
				if(regs.getScore09() != null) rcnt++;
				if(regs.getScore10() != null) rcnt++;
				if(rcnt < maxRcnt) {
					Map retMap = new HashMap();
					retMap.put("DtimeOid", DtimeOid);
					retMap.put("teacherID", teacherID);
					retMap.put("departClass", departClass);
					retMap.put("deptClassName", deptClassName);
					retMap.put("cscode", cscode);
					retMap.put("subjectName", subjectName);
					retMap.put("studentNo", regs.getStudentNo());
					retList.add(retMap);
				}
			}
			
		}
		return retList;
	}
	
	public List<Optime1> findOptime(String level) {
		List<Optime1> opList = new ArrayList();
		String hql = "Select o From Optime1 o Where level";
		if(level.equals("0")) {
			hql = hql + " in ('1', '2', '3')";
		}else{
			hql = hql + "='" + level + "'";
		}
		hql = hql + " order by depart,level";
		log.debug("findOptime1->.hql:"+ hql);
		opList = dao.submitQuery(hql);
		for(Optime1 optime1:opList){
			optime1.setDepartName(this.findCampusName(optime1.getDepart()));
			//1:期中, 2:期末, 3:畢業, 4:暑修, 5:操行, 6:教學評量
			switch(optime1.getLevel().trim().charAt(0)){
			case '1':
				optime1.setLevelName("期中");
				break;
			case '2':
				optime1.setLevelName("期末");
				break;
			case '3':
				optime1.setLevelName("畢業");
				break;
			case '4':
				optime1.setLevelName("暑修");
				break;
			case '5':
				optime1.setLevelName("操行");
				break;
			case '6':
				optime1.setLevelName("教學評量");
				break;
			default:
				optime1.setLevelName("");
				
			}
		}
		return opList;
	}
	
	public ActionMessages delOptime(List<Optime1> optimes, ResourceBundle bundle){
		ActionMessages errs = new ActionMessages();
		try{
			for(Optime1 optime:optimes){
				dao.removeObject(optime);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			errs.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("Message.delOptimeFail"));
			return errs;
		}
		return errs;
	}
	
	public List<Code5> findCampusDepartment(){
		List<Code5> retList;
		String hql = "Select Distinct c5.name From Code5 c5 Where c5.category='SchoolType'";
		retList = dao.submitQuery(hql);
		
		List deptList = new ArrayList();
		
		String c5Name = "";
		for(Iterator c5Iter = retList.iterator(); c5Iter.hasNext();){
			c5Name = c5Iter.next().toString();
			Map deptMap = new HashMap();
			deptMap.put("no", c5Name.substring(0, 2));
			deptMap.put("name", c5Name);
			deptList.add(deptMap);
		}

		return deptList;
	}

	public String findCampusName(String campus){
		List<Code5> retList;
		String sql = "Select Distinct c5.name As name From code5 c5 Where c5.category='SchoolType'";
		sql = sql + " and c5.name like '" + campus + "%'";
		retList = jdbcDao.findAnyThing(sql);

		if(!retList.isEmpty()){
			//log.debug(((Map)retList.get(0)).toString());
			return ((Map)retList.get(0)).get("name").toString().substring(2);
		}
		else
			return "";
	}

	
	public List<Code5> findSetDateLevel(String level){
		List<Code5> retList;
		String hql = "Select c5 From Code5 c5 Where c5.category='UploadLevel'";
		if(level.equals("0")){
			hql = hql + " And (idno='1' Or idno='2' Or idno='3')"; 
		}else{
			hql = hql + " And idno='" + level + "'"; 
		}
		retList = dao.submitQuery(hql);
		
		return retList;
		
	}
	
	public ActionMessages createUploadDateByForm(DynaActionForm form){
		ActionMessages errs = new ActionMessages();
		
		String level = form.getString("level");
		String depart = form.getString("depart");
		String beginDate = form.getString("beginDate");
		String beginTime = form.getString("beginTime");
		String endDate = form.getString("endDate");
		String endTime = form.getString("endTime");

		String hql = "Select o From Optime1 o Where level='" + level + "' and depart='" + depart + "'";
		List<Optime1> otList = dao.submitQuery(hql);
		if(!otList.isEmpty()){
			errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Messages.Optime1Existed"));
		}else{
			Optime1 ot1 = new Optime1();
			ot1.setLevel(level);
			ot1.setDepart(depart);
			ot1.setBeginDate(beginDate);
			ot1.setBeginTime(beginTime);
			ot1.setEndDate(endDate);
			ot1.setEndTime(endTime);
			dao.saveObject(ot1);
		}
		
		return errs;
	}
	
	public ActionMessages modifyUploadDateByForm(List<Optime1> inEditList, DynaActionForm form){
		ActionMessages errs = new ActionMessages();
		
		int i = 0;
		String[] beginDate = form.getStrings("beginDate");
		String[] beginTime = form.getStrings("beginTime");
		String[] endDate = form.getStrings("endDate");
		String[] endTime = form.getStrings("endTime");
		try{
			for(Optime1 optime:inEditList){
				optime.setBeginDate(beginDate[i]);
				optime.setBeginTime(beginTime[i]);
				optime.setEndDate(endDate[i]);
				optime.setEndTime(endTime[i]);
				dao.saveObject(optime);
				i++;
			}
			
		}catch(Exception e){
			errs.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("Message.MoodifyOptimeFail"));
			errs.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage(e.toString()));
		}
		return errs;
	}
	
	/**
	 * 儲存ClassScoreSummary物件
	 * 
	 * @param css tw.edu.chit.models.ClassScoreSummary Object
	 */
	public void txSaveClassScoreSummary(ClassScoreSummary css) {
		ClassScoreSummary existCss = dao
				.findClssScoreSummaryBy(new ClassScoreSummary(css
						.getSchoolYear(), css.getSchoolTerm(), css
						.getDepartClass(), css.getMidFinal()));
		if (existCss != null) {
			existCss.setAverage(css.getAverage());
			existCss.setSummary1(css.getSummary1());
			existCss.setSummary2(css.getSummary2());
			int i = 0;

			/*
			Set<FailStudents1> fs1s = css.getFailStudents1Set();
			Iterator<FailStudents1> existFs1s = existCss.getFailStudents1Set()
					.iterator();
			Set<FailStudents1> addedFs1s = new HashSet<FailStudents1>();
			
			FailStudents1 existFs1 = null;
			for (FailStudents1 fs1 : fs1s) {
				try {
					existFs1 = existFs1s.next();
				} catch (Exception e) {
					existFs1 = new FailStudents1();
				}
				existFs1.setParentOid(existCss.getOid());
				existFs1.setStudentNo(fs1.getStudentNo());
				existFs1.setPassCredits(fs1.getPassCredits());
				existFs1.setTotalCredits(fs1.getTotalCredits());
				existFs1.setAverage(fs1.getAverage());
				addedFs1s.add(existFs1);
			}
			existCss.setFailStudents1Set(addedFs1s);

			Set<FailStudents2> fs2s = css.getFailStudents2Set();
			Iterator<FailStudents2> existFs2s = existCss.getFailStudents2Set()
					.iterator();
			Set<FailStudents2> addedFs2s = new HashSet<FailStudents2>();
			i = 0;
			FailStudents2 existFs2 = null;
			for (FailStudents2 fs2 : fs2s) {
				try {
					existFs2 = existFs2s.next();
				} catch (Exception e) {
					existFs2 = new FailStudents2();
				}
				existFs2.setParentOid(existCss.getOid());
				existFs2.setStudentNo(fs2.getStudentNo());
				existFs2.setPassCredits(fs2.getPassCredits());
				existFs2.setTotalCredits(fs2.getTotalCredits());
				existFs2.setAverage(fs2.getAverage());
				addedFs2s.add(existFs2);
			}
			existCss.setFailStudents2Set(addedFs2s);
			*/
			
			Set<StdScore> stdScores = css.getStdScoreSet();
			Iterator<StdScore> existSss = existCss.getStdScoreSet().iterator();
			Set<StdScore> addedSss = new HashSet<StdScore>();
			i = 0;
			StdScore existSs = null;
			for (StdScore ss : stdScores) {
				try {
					existSs = existSss.next();
				} catch (Exception e) {
					existSs = new StdScore();
				}
				existSs.setParentOid(existCss.getOid());
				existSs.setStudentNo(ss.getStudentNo());
				existSs.setTotalCredits(ss.getTotalCredits());
				existSs.setPassCredits(ss.getPassCredits());
				existSs.setAverage(ss.getAverage());
				existSs.setJust(ss.getJust());
				existSs.setStatus(ss.getStatus());
				existSs.setPos(ss.getPos());
				addedSss.add(existSs);
			}
			existCss.setStdScoreSet(addedSss);
			
			Set<ScoreStatistic> sss = css.getScoreStatisticSet();
			existCss.setScoreStatisticSet(sss);

			/*if (!Toolket.isMasterClass(existCss.getDepartClass())) {
				List<TopStudent> tss = css.getTopStudentSet();
				List<TopStudent> existTss = existCss.getTopStudentSet();
				if (existTss.isEmpty()) {
					for (TopStudent ts : tss) {
						ts.setClassScoreSummary(existCss);
					}
					existCss.setTopStudentSet(tss);
				} else {
					i = 0;
					for (TopStudent ts : existTss) {
						try {
							Integer oid = ts.getOid();
							BeanUtils.copyProperties(ts, tss.get(i++));
							ts.setClassScoreSummary(existCss);
							ts.setOid(oid);
							ts.setParentOid(existCss.getOid());
						} catch (IllegalAccessException iae) {
							log.error(existCss.getDepartClass());
							log.error(iae.getMessage(), iae);
						} catch (InvocationTargetException ite) {
							log.error(existCss.getDepartClass());
							log.error(ite.getMessage(), ite);
						}
					}
				}
			}*/
			
			existCss.setLastModified(new Date());
			dao.saveObject(existCss);
		} //else
			//dao.saveObject(css);
	}
	
	public List<ClassScoreSummary> findClsssScoreSummary() {
		return dao.findClassScoreSummary();
	}
	
	/**
	 * 查詢ClassScoreSummary物件
	 * 
	 * @comment 包括Like Expression
	 * @param css tw.edu.chit.models.ClassScoreSummary Object
	 * @return java.util.List List of tw.edu.chit.models.ClassScoreSummary
	 *         Objects
	 */
	public List<ClassScoreSummary> findClassScoreSummaryByLikeExpression(
			ClassScoreSummary css) {
		return dao.findClassScoreSummaryByLikeExpression(css);
	}
	
	public void deleteClassScoreSummary(List<ClassScoreSummary> css) {
		for (ClassScoreSummary c : css)
			dao.removeObject(c);
	}
	
	/**
	 * 儲存MidtermExcluded物件
	 * 
	 * @param mes java.util.List List of MidtermExcluded Objects
	 */
	public void txSaveMidtermExcluded(List<MidtermExcluded> mes) {
		for (MidtermExcluded me : mes)
			dao.saveObject(me);
	}
	
	/**
	 * 查詢MidtermExcluded物件
	 * 
	 * @param me tw.edu.chit.models.MidtermExcluded Object
	 * @return tw.edu.chit.models.MidtermExcluded Object
	 */
	public MidtermExcluded findMidtermExcludedBy(MidtermExcluded me) {
		return dao.findMidtermExcludedBy(me);
	}
	
	/**
	 * 查詢MidtermExcluded物件
	 * 
	 * @param me tw.edu.chit.models.MidtermExcluded Object
	 * @return tw.edu.chit.models.MidtermExcluded Object
	 */
	public List<MidtermExcluded> findMidtermExcludedBy1(MidtermExcluded me) {
		return dao.findMidtermExcludedBy1(me);
	}
	
	/**
	 * 刪除MidtermExcluded物件
	 * 
	 * @param me tw.edu.chit.models.MidtermExcluded Object
	 */
	public void txDeleteMidtermExcluded(MidtermExcluded me) {
		dao.removeObject(me);
	}
	
	/**
	 * 查詢所有1/2不及格學生
	 * 
	 * @param fs tw.edu.chit.models.FailStudents1 Object
	 * @return java.util.List List of tw.edu.chit.models.FailStudents1 Objects
	 */
	public List findFailStudentsBy(FailStudents1 fs) {
		return jdbcDao
				.findAnyThing("SELECT * FROM FailStudents1 fs1 WHERE fs1.studentNo = '"
						+ fs.getStudentNo() + "'");
		// return dao.findAllFailStudents1(fs);
	}
	
	/**
	 * 查詢所有2/3不及格學生
	 * 
	 * @param fs tw.edu.chit.models.FailStudents2 Object
	 * @return java.util.List List of tw.edu.chit.models.FailStudents2 Objects
	 */
	public List findFailStudentsBy(FailStudents2 fs) {
		return jdbcDao
				.findAnyThing("SELECT * FROM FailStudents2 fs2 WHERE fs2.studentNo = '"
						+ fs.getStudentNo() + "'");
		// return dao.findAllFailStudents2(fs);
	}
	
	/**
	 * 查詢學生歷年成績
	 * 
	 * @param fs tw.edu.chit.models.ScoreHist Object
	 * @return java.util.List List of tw.edu.chit.models.ScoreHist Objects
	 */
	public List<ScoreHist> findScoreHistBy(ScoreHist scoreHist) {
		return dao.findScoreHistBy(scoreHist);
	}
	
	/**
	 * 刪除歷年成績
	 * 
	 * @param hists List of tw.edu.chit.models.ScoreHist Objects
	 */
	public void txDeleteScoreHist(List<ScoreHist> hists) {
		for (ScoreHist hist : hists)
			dao.removeObject(hist);
	}
	
	/**
	 * 查詢95.2之1/2不及格學生清單
	 * 
	 * @param failStudentsHist tw.edu.chit.models.FailStudentsHist Object
	 * @return java.util.List List of tw.edu.chit.models.FailStudentsHist
	 *         Objects
	 */
	public List<FailStudentsHist> findFailStudentsHistBy(
			FailStudentsHist failStudentsHist) {
		return dao.findFailStudentsHistBy(failStudentsHist);
	}
	
	/**
	 * 查詢學生期中/學期成績
	 * 
	 * @param failStudentsHist tw.edu.chit.models.StdScore Object
	 * @return java.util.List List of tw.edu.chit.models.StdScore Objects
	 */
	public List<StdScore> findStdScoreBy(StdScore stdScore) {
		return dao.findStdScoreBy(stdScore);
	}
	
	public List<Clazz> findAllClasses(String clazzFilter){
		String cond = "";
		/* 這樣做會有盲點，當時體班級沒有學生但是其他table仍有資料時...
		 * 2011.10.28 Jason
		String sql = "Select Distinct depart_class From stmd Where depart_class like '" + clazzFilter + "%' order by depart_class";
		List phyClasses = jdbcDao.findAnyThing(sql);
		for(Iterator cndIter=phyClasses.iterator(); cndIter.hasNext();){
			cond = cond + "'" + ((Map)cndIter.next()).get("depart_class").toString() + "',";
		}
		*/
		String hql="select c From Clazz c ";
		if(!cond.equals("")){
			cond ="(" + cond.substring(0, cond.length()-1) + ")";
			hql = hql + "Where classNo in " + cond;
		}else{
			hql =  hql  + " Where classNo like '" + clazzFilter +"%'";
		}
		hql = hql + " order by classNo";
		
		return dao.submitQuery(hql);
	}

	/**
	 * 尋找班級
	 * 
	 * @commend 排除通識課程
	 * @param clazz tw.edu.chit.models.Clazz Object
	 * @param restrictionClass Restriction's Class Array
	 * @param includeLiteracy Is include Literacy Class
	 * @return java.util.List List of Clazz Objects
	 */
	public List<Clazz> findClassBy(Clazz clazz, Clazz[] restrictionClass,
			boolean isIncludeLiteracy) {
		return dao.findClassBy(clazz, restrictionClass, isIncludeLiteracy);
	}
		
	public List<Rrate> findScoreRateAdjusted(String clazzFilter, String teacherId){
		ActionMessages msgs = new ActionMessages();
		String hql = "";
		List<Rrate> retList = new ArrayList<Rrate>();
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		CourseManager manager = (CourseManager) Global.context.getBean("courseManager");
		StudAffairManager sam = (StudAffairManager) Global.context.getBean("studAffairManager");
		
		hql = "From Rrate Where ((rateN not between 0.29 and 0.31) OR (rateM not between 0.29 And 0.31) OR (rateF not between 0.39 And 0.41)) ";
		
		List<Object> oids = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		String sql = "", subsql = "";
		
		if(teacherId.trim().equals("")){
			sql = "Select Oid From Dtime Where depart_class like '" + clazzFilter + 
			"%' And Sterm='" + sterm + "'";
			
			oids = jdbcDao.findAnyThing(sql);
			for(Object oid:oids){
				buffer.append(",");
				buffer.append(((Map)oid).get("Oid").toString());
			}
		}else{
			subsql = " And d.depart_class like '" + clazzFilter + "%'";
			sql = "Select d.Oid From Dtime d Where d.techid='" + teacherId + 
							"' And d.Sterm='" + sterm + "'";
			if(!clazzFilter.equals("")){
				sql += subsql;
			}
			oids = jdbcDao.findAnyThing(sql);
			for(Object oid:oids){
				buffer.append(",");
				buffer.append(((Map)oid).get("Oid").toString());
			}
			sql = "Select dt.Dtime_oid As Oid From Dtime_teacher dt, Dtime d Where dt.Dtime_oid=d.Oid And dt.teach_id='" + 
					teacherId + "' And d.Sterm='" + sterm + "'";
			if(!clazzFilter.equals("")){
				sql += subsql;
			}
			oids = jdbcDao.findAnyThing(sql);
			for(Object oid:oids){
				buffer.append(",");
				buffer.append(((Map)oid).get("Oid").toString());
			}
		}
		if(buffer.length() > 0){
			hql += " And dtimeOid in " + "(" + buffer.toString().substring(1) + ")";
		}else{	//此次查詢未包含任何課程或老師沒有授課
			return retList;
		}
		hql += " order by departClass";
		retList = dao.submitQuery(hql);
	
		for(Rrate rate:retList){
			Dtime dt = manager.findDtimeBy(rate.getDtimeOid());
			rate.setDeptClassName(Toolket.getClassFullName(dt.getDepartClass()));
			//rate.setDeptClassName(dt.getDepartClass2());
			rate.setCscodeName(finCourseNameByCsno(dt.getCscode()));
			//rate.setCscodeName(dt.getChiName2());
			Empl empl = findTeacherByDtimeOid(rate.getDtimeOid());
			if(empl != null){
				rate.setTeacherName(empl.getCname());
				rate.setTeacherId(empl.getIdno());
			}else{
				rate.setTeacherName("");
				rate.setTeacherId("");
			}
		}
		return retList;
	}
	
	public Empl findTeacherByDtimeOid(int oid){
		log.debug("dtoid:"+ oid);
		Empl empl = null;
		List<Empl> empls = new ArrayList<Empl>();
		String hql = "Select e From Empl e, Dtime d Where d.oid=" + oid;
		hql += " And e.idno=d.techid";
		
		empls = dao.submitQuery(hql);
		if(!empls.isEmpty()){
			empl = empls.get(0);
		}else{
			hql = "Select e From Empl e, DtimeTeacher d Where d.dtimeOid=" + oid;
			hql += " And e.idno=d.teachId";
			empls = dao.submitQuery(hql);
			if(!empls.isEmpty()){
				empl = empls.get(0);
			}
		}
		
		return empl;
	}
	
	public String finCourseNameByCsno(String idno){
		String hql = "From Csno Where cscode='" + idno + "'";
		String cname = "";
		List<Csno> csList = dao.submitQuery(hql);
		if(!csList.isEmpty()) cname = csList.get(0).getChiName();
		return cname;
	}
	
	public List<Csno> findCourseByName(String chiName){
		String hql = "From Csno Where chiName like '" + chiName + "%'";
		List<Csno> csList = dao.submitQuery(hql);
		return csList;
	}
	
	public List<Csno> findCourseByCode(String cscode){
		String hql = "From Csno Where cscode like '" + cscode + "%'";
		List<Csno> csList = dao.submitQuery(hql);
		return csList;
	}

}
