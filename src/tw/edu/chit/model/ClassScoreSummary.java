package tw.edu.chit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ClassScoreSummary implements java.io.Serializable {

	private static final long serialVersionUID = -1333756218055471504L;

	private Integer oid;
	private String schoolYear;
	private String schoolTerm;
	private Examine midFinal;
	private String departClass;
	private Float average; // 期中或期末平均
	private Date lastModified;
	private Integer summary1; // 期中1/2不及格人數
	private Integer summary2; // 期中2/3不及格人數
	//private Integer version;

	// 1/2不及格學生
	private Set<FailStudents1> failStudents1Set = new HashSet<FailStudents1>();

	// 2/3不及格學生
	private Set<FailStudents2> failStudents2Set = new HashSet<FailStudents2>();
	
	private Set<StdScore> stdScoreSet = new HashSet<StdScore>();

	// 各科成績統計
	private Set<ScoreStatistic> scoreStatisticSet = new HashSet<ScoreStatistic>();

	// Top 3學生
	private List<TopStudent> topStudentSet = new ArrayList<TopStudent>(0);

	/** default constructor */
	public ClassScoreSummary() {
	}

	public ClassScoreSummary(String schoolYear, String schoolTerm,
			String departClass) {
		super();
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.departClass = departClass;
	}
	
	public ClassScoreSummary(String schoolYear, String schoolTerm,
			String departClass, Examine midFinal) {
		super();
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.departClass = departClass;
		this.midFinal = midFinal;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSchoolTerm() {
		return schoolTerm;
	}

	public void setSchoolTerm(String schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public Examine getMidFinal() {
		return midFinal;
	}

	public void setMidFinal(Examine midFinal) {
		this.midFinal = midFinal;
	}

	public String getDepartClass() {
		return departClass;
	}

	public void setDepartClass(String departClass) {
		this.departClass = departClass;
	}

	public Float getAverage() {
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Integer getSummary1() {
		return summary1;
	}

	public void setSummary1(Integer summary1) {
		this.summary1 = summary1;
	}

	public Integer getSummary2() {
		return summary2;
	}

	public void setSummary2(Integer summary2) {
		this.summary2 = summary2;
	}

//	public Integer getVersion() {
//		return version;
//	}
//
//	public void setVersion(Integer version) {
//		this.version = version;
//	}

	public Set<FailStudents1> getFailStudents1Set() {
		return failStudents1Set;
	}

	public void setFailStudents1Set(Set<FailStudents1> failStudents1Set) {
		this.failStudents1Set = failStudents1Set;
	}

	public Set<FailStudents2> getFailStudents2Set() {
		return failStudents2Set;
	}

	public void setFailStudents2Set(Set<FailStudents2> failStudents2Set) {
		this.failStudents2Set = failStudents2Set;
	}

	public Set<ScoreStatistic> getScoreStatisticSet() {
		return scoreStatisticSet;
	}

	public void setScoreStatisticSet(Set<ScoreStatistic> scoreStatisticSet) {
		this.scoreStatisticSet = scoreStatisticSet;
	}

	public List<TopStudent> getTopStudentSet() {
		return topStudentSet;
	}

	public void setTopStudentSet(List<TopStudent> topStudentSet) {
		this.topStudentSet = topStudentSet;
	}

	public Set<StdScore> getStdScoreSet() {
		return stdScoreSet;
	}

	public void setStdScoreSet(Set<StdScore> stdScoreSet) {
		this.stdScoreSet = stdScoreSet;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}