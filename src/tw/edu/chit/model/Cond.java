package tw.edu.chit.model;

/**
 * Cond entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Cond implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String studentNo;
	private Short schoolYear;
	private Short schoolTerm;
	private String departClass;
	private Float score;
	private String noabsent;

	// Constructors

	/** default constructor */
	public Cond() {
	}

	/** full constructor */
	public Cond(String studentNo, Short schoolYear, Short schoolTerm,
			String departClass, Float score, String noabsent) {
		this.studentNo = studentNo;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.departClass = departClass;
		this.score = score;
		this.noabsent = noabsent;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Short getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(Short schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Short getSchoolTerm() {
		return this.schoolTerm;
	}

	public void setSchoolTerm(Short schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public String getDepartClass() {
		return this.departClass;
	}

	public void setDepartClass(String departClass) {
		this.departClass = departClass;
	}

	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getNoabsent() {
		return this.noabsent;
	}

	public void setNoabsent(String noabsent) {
		this.noabsent = noabsent;
	}

}