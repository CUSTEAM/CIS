package tw.edu.chit.model;

import java.util.Date;

/**
 * StdSkill entity. @author MyEclipse Persistence Tools
 */
public class StdSkill extends StdSkillBase implements java.io.Serializable {

	private static final long serialVersionUID = 400764482265009553L;

	private Integer oid;
	private String schoolYear;
	private String schoolTerm;
	private String studentNo;
	private String licenseCode;
	private Integer amount = 0;
	private String amountType;
	private Date amountDate;
	private String licenseNo;
	private Date licenseValidDate;
	private String deptNo;
	private String cscode;
	private String techIdno;
	private String serialNo;
	private String customNo;
	private String applyType;
	private String pass;
	private String reason;
	private Date lastModified;

	public StdSkill() {
	}

	public StdSkill(String schoolYear, String schoolTerm, String studentNo,
			String licenseCode, Integer amount, String amountType,
			Date licenseValidDate, String deptNo, String customNo,
			Date lastModified) {
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.studentNo = studentNo;
		this.licenseCode = licenseCode;
		this.amount = amount;
		this.amountType = amountType;
		this.licenseValidDate = licenseValidDate;
		this.deptNo = deptNo;
		this.customNo = customNo;
		this.lastModified = lastModified;
	}

	public StdSkill(String schoolYear, String schoolTerm, String studentNo,
			String licenseCode, Integer amount, String amountType,
			Date amountDate, String licenseNo, Date licenseValidDate,
			String deptNo, String cscode, String techIdno, String serialNo,
			String customNo, Date lastModified) {
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.studentNo = studentNo;
		this.licenseCode = licenseCode;
		this.amount = amount;
		this.amountType = amountType;
		this.amountDate = amountDate;
		this.licenseNo = licenseNo;
		this.licenseValidDate = licenseValidDate;
		this.deptNo = deptNo;
		this.cscode = cscode;
		this.techIdno = techIdno;
		this.serialNo = serialNo;
		this.customNo = customNo;
		this.lastModified = lastModified;
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

	public String getLicenseCode() {
		return this.licenseCode;
	}

	public void setLicenseCode(String licenseCode) {
		this.licenseCode = licenseCode;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getAmountType() {
		return this.amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public Date getAmountDate() {
		return this.amountDate;
	}

	public void setAmountDate(Date amountDate) {
		this.amountDate = amountDate;
	}

	public String getLicenseNo() {
		return this.licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public Date getLicenseValidDate() {
		return this.licenseValidDate;
	}

	public void setLicenseValidDate(Date licenseValidDate) {
		this.licenseValidDate = licenseValidDate;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getCscode() {
		return this.cscode;
	}

	public void setCscode(String cscode) {
		this.cscode = cscode;
	}

	public String getTechIdno() {
		return this.techIdno;
	}

	public void setTechIdno(String techIdno) {
		this.techIdno = techIdno;
	}

	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCustomNo() {
		return this.customNo;
	}

	public void setCustomNo(String customNo) {
		this.customNo = customNo;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}