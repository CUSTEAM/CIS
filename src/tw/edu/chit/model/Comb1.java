package tw.edu.chit.model;

/**
 * Comb1 entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Comb1 implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Short schoolYear;
	private Short schoolTerm;
	private String departClass;
	private String studentNo;
	private String comName;
	private Float physicalScore;
	private Float militaryScore;

	// Constructors

	/** default constructor */
	public Comb1() {
	}

	/** minimal constructor */
	public Comb1(Short schoolYear, Short schoolTerm, String departClass,
			String studentNo) {
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.departClass = departClass;
		this.studentNo = studentNo;
	}

	/** full constructor */
	public Comb1(Short schoolYear, Short schoolTerm, String departClass,
			String studentNo, String comName, Float physicalScore,
			Float militaryScore) {
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.departClass = departClass;
		this.studentNo = studentNo;
		this.comName = comName;
		this.physicalScore = physicalScore;
		this.militaryScore = militaryScore;
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

	public String getComName() {
		return this.comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public Float getPhysicalScore() {
		return this.physicalScore;
	}

	public void setPhysicalScore(Float physicalScore) {
		this.physicalScore = physicalScore;
	}

	public Float getMilitaryScore() {
		return this.militaryScore;
	}

	public void setMilitaryScore(Float militaryScore) {
		this.militaryScore = militaryScore;
	}

}