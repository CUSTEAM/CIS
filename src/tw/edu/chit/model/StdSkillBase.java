package tw.edu.chit.model;

import java.io.Serializable;

public class StdSkillBase implements Serializable {

	private static final long serialVersionUID = 400764482265009555L;

	private String studentName;
	private String departClass;
	private String licenseCodeName;
	private String amountTypeName;
	private String amountDateValue;
	private String licenseValidDateValue;
	private String cscodeName;
	private String techName;
	private String customNoName;

	private LicenseCode license;

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getDepartClass() {
		return departClass;
	}

	public void setDepartClass(String departClass) {
		this.departClass = departClass;
	}

	public String getLicenseCodeName() {
		return licenseCodeName;
	}

	public void setLicenseCodeName(String licenseCodeName) {
		this.licenseCodeName = licenseCodeName;
	}

	public String getAmountTypeName() {
		return amountTypeName;
	}

	public void setAmountTypeName(String amountTypeName) {
		this.amountTypeName = amountTypeName;
	}

	public String getAmountDateValue() {
		return amountDateValue;
	}

	public void setAmountDateValue(String amountDateValue) {
		this.amountDateValue = amountDateValue;
	}

	public String getLicenseValidDateValue() {
		return licenseValidDateValue;
	}

	public void setLicenseValidDateValue(String licenseValidDateValue) {
		this.licenseValidDateValue = licenseValidDateValue;
	}

	public String getCscodeName() {
		return cscodeName;
	}

	public void setCscodeName(String cscodeName) {
		this.cscodeName = cscodeName;
	}

	public String getTechName() {
		return techName;
	}

	public void setTechName(String techName) {
		this.techName = techName;
	}

	public String getCustomNoName() {
		return customNoName;
	}

	public void setCustomNoName(String customNoName) {
		this.customNoName = customNoName;
	}

	public LicenseCode getLicense() {
		return license;
	}

	public void setLicense(LicenseCode license) {
		this.license = license;
	}

}
