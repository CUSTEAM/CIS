package tw.edu.chit.model;

/**
 * Stavg generated by MyEclipse - Hibernate Tools
 */
public class Stavg extends StavgBase implements java.io.Serializable {

	private static final long serialVersionUID = 2956409518866209449L;

	private Integer oid;
	private String departClass;
	private String studentNo;
	private Short schoolYear;
	private String schoolTerm;
	private Float score;
	private Float totalCredit;

	// Constructors

	/** default constructor */
	public Stavg() {
	}

	public Stavg(String studentNo) {
		this.studentNo = studentNo;
	}

	public Stavg(String studentNo, Short schoolYear, String schoolTerm) {
		this.studentNo = studentNo;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
	}

	/** full constructor */
	public Stavg(String departClass, String studentNo, Short schoolYear,
			String schoolTerm, Float score, Float totalCredit) {
		this.departClass = departClass;
		this.studentNo = studentNo;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.score = score;
		this.totalCredit = totalCredit;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getDepartClass() {
		return this.departClass;
	}

	public void setDepartClass(String departClass) {
		this.departClass = departClass;
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

	public String getSchoolTerm() {
		return this.schoolTerm;
	}

	public void setSchoolTerm(String schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Float getTotalCredit() {
		return this.totalCredit;
	}

	public void setTotalCredit(Float totalCredit) {
		this.totalCredit = totalCredit;
	}

}