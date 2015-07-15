package tw.edu.chit.model;

/**
 * NewStmdSno entity. @author MyEclipse Persistence Tools
 */

public class NewStmdSno implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String classNo;
	private String rule;
	private String campusNo;
	private String schoolType;
	private String schoolNo;

	// Constructors

	/** default constructor */
	public NewStmdSno() {
	}

	/** minimal constructor */
	public NewStmdSno(String classNo, String rule, String campusNo,
			String schoolType) {
		this.classNo = classNo;
		this.rule = rule;
		this.campusNo = campusNo;
		this.schoolType = schoolType;
	}

	/** full constructor */
	public NewStmdSno(String classNo, String rule, String campusNo,
			String schoolType, String schoolNo) {
		this.classNo = classNo;
		this.rule = rule;
		this.campusNo = campusNo;
		this.schoolType = schoolType;
		this.schoolNo = schoolNo;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getClassNo() {
		return this.classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getCampusNo() {
		return this.campusNo;
	}

	public void setCampusNo(String campusNo) {
		this.campusNo = campusNo;
	}

	public String getSchoolType() {
		return this.schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	public String getSchoolNo() {
		return this.schoolNo;
	}

	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
	}

}