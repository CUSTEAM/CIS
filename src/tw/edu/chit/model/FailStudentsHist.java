package tw.edu.chit.model;

/**
 * FailStudentsHist entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class FailStudentsHist implements java.io.Serializable {

	private static final long serialVersionUID = -8766553485273867633L;

	private Integer oid;
	private String schoolYear;
	private String schoolTerm;
	private String studentNo;
	private Float totalCredits;
	private Float downCredits;

	/** default constructor */
	public FailStudentsHist() {
	}

	/** minimal constructor */
	public FailStudentsHist(Integer oid, String schoolYear, String schoolTerm,
			String studentNo) {
		this.oid = oid;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.studentNo = studentNo;
	}

	/** full constructor */
	public FailStudentsHist(Integer oid, String schoolYear, String schoolTerm,
			String studentNo, Float totalCredits, Float downCredits) {
		this.oid = oid;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.studentNo = studentNo;
		this.totalCredits = totalCredits;
		this.downCredits = downCredits;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSchoolTerm() {
		return this.schoolTerm;
	}

	public void setSchoolTerm(String schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Float getTotalCredits() {
		return this.totalCredits;
	}

	public void setTotalCredits(Float totalCredits) {
		this.totalCredits = totalCredits;
	}

	public Float getDownCredits() {
		return this.downCredits;
	}

	public void setDownCredits(Float downCredits) {
		this.downCredits = downCredits;
	}

}