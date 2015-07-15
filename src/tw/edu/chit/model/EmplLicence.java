package tw.edu.chit.model;

/**
 * EmplLicence entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EmplLicence implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private String deptNo;
	private Short schoolYear;
	private Short schoolTerm;
	private String licenceType;
	private String licenceName;
	private String organ;
	private String licenceNo;

	// Constructors

	/** default constructor */
	public EmplLicence() {
	}

	/** full constructor */
	public EmplLicence(String idno, String deptNo, Short schoolYear,
			Short schoolTerm, String licenceType, String licenceName,
			String organ, String licenceNo) {
		this.idno = idno;
		this.deptNo = deptNo;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.licenceType = licenceType;
		this.licenceName = licenceName;
		this.organ = organ;
		this.licenceNo = licenceNo;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
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

	public String getLicenceType() {
		return this.licenceType;
	}

	public void setLicenceType(String licenceType) {
		this.licenceType = licenceType;
	}

	public String getLicenceName() {
		return this.licenceName;
	}

	public void setLicenceName(String licenceName) {
		this.licenceName = licenceName;
	}

	public String getOrgan() {
		return this.organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getLicenceNo() {
		return this.licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

}