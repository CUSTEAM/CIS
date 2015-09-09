package tw.edu.chit.model;

/**
 * Gmark generated by MyEclipse Persistence Tools
 */

public class Gmark implements java.io.Serializable {

	// Fields

	private Integer oid;

	private Short schoolYear;

	private Short schoolTerm;

	private String studentNo;

	private String remark;

	private String occurStatus;

	private String occurCause;

	// Constructors

	/** default constructor */
	public Gmark() {
	}

	/** minimal constructor */
	public Gmark(String studentNo) {
		this.studentNo = studentNo;
	}

	/** full constructor */
	public Gmark(Short schoolYear, Short schoolTerm, String studentNo,
			String remark, String occurStatus, String occurCause) {
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.studentNo = studentNo;
		this.remark = remark;
		this.occurStatus = occurStatus;
		this.occurCause = occurCause;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
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

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOccurStatus() {
		return this.occurStatus;
	}

	public void setOccurStatus(String occurStatus) {
		this.occurStatus = occurStatus;
	}

	public String getOccurCause() {
		return this.occurCause;
	}

	public void setOccurCause(String occurCause) {
		this.occurCause = occurCause;
	}

}