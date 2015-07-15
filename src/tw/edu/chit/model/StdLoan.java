package tw.edu.chit.model;

import java.util.Date;

/**
 * StdLoan entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class StdLoan implements java.io.Serializable {

	private static final long serialVersionUID = 2961663539072580242L;

	private Integer oid;
	private String dayNight;
	private String schlCode;
	private String locationCode;
	private String idno;
	private String name;
	private String birth;
	private String merry;
	private String permPhoneCode;
	private String permPhone;
	private String permPhoneExt;
	private String phoneCode;
	private String phone;
	private String phoneExt;
	private String cellPhone;
	private String email;
	private String permAddrCode;
	private String permAddr;
	private String addrCode;
	private String addr;
	private String deptName;
	private String grade;
	private String classCode;
	private String studentNo;
	private String graduateYearTerm;
	private String schoolYear;
	private String schoolTerm;
	private String supportAmount;
	private String creditAmount;
	private String amount;
	private String fatherStatus;
	private String fatherIsSponsor;
	private String fatherName;
	private String fatherIdno;
	private String fatherPhoneCode;
	private String fatherPhone;
	private String fatherPhoneExt;
	private String fatherCellPhone;
	private String fatherPermAddrCode;
	private String fatherPermAddr;
	private String motherStatus;
	private String motherIsSponsor;
	private String motherName;
	private String motherIdno;
	private String motherPhoneCode;
	private String motherPhone;
	private String motherPhoneExt;
	private String motherCellPhone;
	private String motherPermAddrCode;
	private String motherPermAddr;
	private String spouseIsSponsor;
	private String spouseName;
	private String spouseIdno;
	private String spousePhoneCode;
	private String spousePhone;
	private String spousePhoneExt;
	private String spouseCellPhone;
	private String spousePermAddrCode;
	private String spousePermAddr;
	private String guardianRelational;
	private String guardianName;
	private String guardianIdno;
	private String guardianPhoneCode;
	private String guardianPhone;
	private String guardianPhoneExt;
	private String guardianCellPhone;
	private String guardianPermAddrCode;
	private String guardianPermAddr;
	private String sponsorRelational;
	private String sponsorName;
	private String sponsorIdno;
	private String sponsorPhoneCode;
	private String sponsorPhoneExt;
	private String sponsorPhone;
	private String sponsorCellPhone;
	private String sponsorPermAddrCode;
	private String sponsorPermAddr;
	private String bankNo;
	private Date lastModified;

	/** default constructor */
	public StdLoan() {
	}

	public StdLoan(Integer oid, String dayNight, String schlCode,
			String locationCode, String idno, String name, String birth,
			String merry, String schoolYear, String schoolTerm,
			String supportAmount, String creditAmount, String amount,
			String bankNo, Date lastModified) {
		this.oid = oid;
		this.dayNight = dayNight;
		this.schlCode = schlCode;
		this.locationCode = locationCode;
		this.idno = idno;
		this.name = name;
		this.birth = birth;
		this.merry = merry;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.supportAmount = supportAmount;
		this.creditAmount = creditAmount;
		this.amount = amount;
		this.bankNo = bankNo;
		this.lastModified = lastModified;
	}

	public StdLoan(Integer oid, String dayNight, String schlCode,
			String locationCode, String idno, String name, String birth,
			String merry, String permPhoneCode, String permPhone,
			String permPhoneExt, String phoneCode, String phone,
			String phoneExt, String cellPhone, String email,
			String permAddrCode, String permAddr, String addrCode, String addr,
			String deptName, String grade, String classCode, String studentNo,
			String graduateYearTerm, String schoolYear, String schoolTerm,
			String supportAmount, String creditAmount, String amount,
			String fatherStatus, String fatherIsSponsor, String fatherName,
			String fatherIdno, String fatherPhoneCode, String fatherPhone,
			String fatherPhoneExt, String fatherCellPhone,
			String fatherPermAddrCode, String fatherPermAddr,
			String motherStatus, String motherIsSponsor, String motherName,
			String motherIdno, String motherPhoneCode, String motherPhone,
			String motherPhoneExt, String motherCellPhone,
			String motherPermAddrCode, String motherPermAddr,
			String spouseIsSponsor, String spouseName, String spouseIdno,
			String spousePhoneCode, String spousePhone, String spousePhoneExt,
			String spouseCellPhone, String spousePermAddrCode,
			String spousePermAddr, String guardianRelational,
			String guardianName, String guardianIdno, String guardianPhoneCode,
			String guardianPhone, String guardianPhoneExt,
			String guardianCellPhone, String guardianPermAddrCode,
			String guardianPermAddr, String sponsorRelational,
			String sponsorName, String sponsorIdno, String sponsorPhoneCode,
			String sponsorPhoneExt, String sponsorPhone,
			String sponsorCellPhone, String sponsorPermAddrCode,
			String sponsorPermAddr, String bankNo, Date lastModified) {
		this.oid = oid;
		this.dayNight = dayNight;
		this.schlCode = schlCode;
		this.locationCode = locationCode;
		this.idno = idno;
		this.name = name;
		this.birth = birth;
		this.merry = merry;
		this.permPhoneCode = permPhoneCode;
		this.permPhone = permPhone;
		this.permPhoneExt = permPhoneExt;
		this.phoneCode = phoneCode;
		this.phone = phone;
		this.phoneExt = phoneExt;
		this.cellPhone = cellPhone;
		this.email = email;
		this.permAddrCode = permAddrCode;
		this.permAddr = permAddr;
		this.addrCode = addrCode;
		this.addr = addr;
		this.deptName = deptName;
		this.grade = grade;
		this.classCode = classCode;
		this.studentNo = studentNo;
		this.graduateYearTerm = graduateYearTerm;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.supportAmount = supportAmount;
		this.creditAmount = creditAmount;
		this.amount = amount;
		this.fatherStatus = fatherStatus;
		this.fatherIsSponsor = fatherIsSponsor;
		this.fatherName = fatherName;
		this.fatherIdno = fatherIdno;
		this.fatherPhoneCode = fatherPhoneCode;
		this.fatherPhone = fatherPhone;
		this.fatherPhoneExt = fatherPhoneExt;
		this.fatherCellPhone = fatherCellPhone;
		this.fatherPermAddrCode = fatherPermAddrCode;
		this.fatherPermAddr = fatherPermAddr;
		this.motherStatus = motherStatus;
		this.motherIsSponsor = motherIsSponsor;
		this.motherName = motherName;
		this.motherIdno = motherIdno;
		this.motherPhoneCode = motherPhoneCode;
		this.motherPhone = motherPhone;
		this.motherPhoneExt = motherPhoneExt;
		this.motherCellPhone = motherCellPhone;
		this.motherPermAddrCode = motherPermAddrCode;
		this.motherPermAddr = motherPermAddr;
		this.spouseIsSponsor = spouseIsSponsor;
		this.spouseName = spouseName;
		this.spouseIdno = spouseIdno;
		this.spousePhoneCode = spousePhoneCode;
		this.spousePhone = spousePhone;
		this.spousePhoneExt = spousePhoneExt;
		this.spouseCellPhone = spouseCellPhone;
		this.spousePermAddrCode = spousePermAddrCode;
		this.spousePermAddr = spousePermAddr;
		this.guardianRelational = guardianRelational;
		this.guardianName = guardianName;
		this.guardianIdno = guardianIdno;
		this.guardianPhoneCode = guardianPhoneCode;
		this.guardianPhone = guardianPhone;
		this.guardianPhoneExt = guardianPhoneExt;
		this.guardianCellPhone = guardianCellPhone;
		this.guardianPermAddrCode = guardianPermAddrCode;
		this.guardianPermAddr = guardianPermAddr;
		this.sponsorRelational = sponsorRelational;
		this.sponsorName = sponsorName;
		this.sponsorIdno = sponsorIdno;
		this.sponsorPhoneCode = sponsorPhoneCode;
		this.sponsorPhoneExt = sponsorPhoneExt;
		this.sponsorPhone = sponsorPhone;
		this.sponsorCellPhone = sponsorCellPhone;
		this.sponsorPermAddrCode = sponsorPermAddrCode;
		this.sponsorPermAddr = sponsorPermAddr;
		this.bankNo = bankNo;
		this.lastModified = lastModified;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getDayNight() {
		return this.dayNight;
	}

	public void setDayNight(String dayNight) {
		this.dayNight = dayNight;
	}

	public String getSchlCode() {
		return this.schlCode;
	}

	public void setSchlCode(String schlCode) {
		this.schlCode = schlCode;
	}

	public String getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return this.birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getMerry() {
		return this.merry;
	}

	public void setMerry(String merry) {
		this.merry = merry;
	}

	public String getPermPhoneCode() {
		return this.permPhoneCode;
	}

	public void setPermPhoneCode(String permPhoneCode) {
		this.permPhoneCode = permPhoneCode;
	}

	public String getPermPhone() {
		return this.permPhone;
	}

	public void setPermPhone(String permPhone) {
		this.permPhone = permPhone;
	}

	public String getPermPhoneExt() {
		return this.permPhoneExt;
	}

	public void setPermPhoneExt(String permPhoneExt) {
		this.permPhoneExt = permPhoneExt;
	}

	public String getPhoneCode() {
		return this.phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneExt() {
		return this.phoneExt;
	}

	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}

	public String getCellPhone() {
		return this.cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPermAddrCode() {
		return this.permAddrCode;
	}

	public void setPermAddrCode(String permAddrCode) {
		this.permAddrCode = permAddrCode;
	}

	public String getPermAddr() {
		return this.permAddr;
	}

	public void setPermAddr(String permAddr) {
		this.permAddr = permAddr;
	}

	public String getAddrCode() {
		return this.addrCode;
	}

	public void setAddrCode(String addrCode) {
		this.addrCode = addrCode;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getClassCode() {
		return this.classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getGraduateYearTerm() {
		return this.graduateYearTerm;
	}

	public void setGraduateYearTerm(String graduateYearTerm) {
		this.graduateYearTerm = graduateYearTerm;
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

	public String getSupportAmount() {
		return this.supportAmount;
	}

	public void setSupportAmount(String supportAmount) {
		this.supportAmount = supportAmount;
	}

	public String getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFatherStatus() {
		return this.fatherStatus;
	}

	public void setFatherStatus(String fatherStatus) {
		this.fatherStatus = fatherStatus;
	}

	public String getFatherIsSponsor() {
		return this.fatherIsSponsor;
	}

	public void setFatherIsSponsor(String fatherIsSponsor) {
		this.fatherIsSponsor = fatherIsSponsor;
	}

	public String getFatherName() {
		return this.fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFatherIdno() {
		return this.fatherIdno;
	}

	public void setFatherIdno(String fatherIdno) {
		this.fatherIdno = fatherIdno;
	}

	public String getFatherPhoneCode() {
		return this.fatherPhoneCode;
	}

	public void setFatherPhoneCode(String fatherPhoneCode) {
		this.fatherPhoneCode = fatherPhoneCode;
	}

	public String getFatherPhone() {
		return this.fatherPhone;
	}

	public void setFatherPhone(String fatherPhone) {
		this.fatherPhone = fatherPhone;
	}

	public String getFatherPhoneExt() {
		return this.fatherPhoneExt;
	}

	public void setFatherPhoneExt(String fatherPhoneExt) {
		this.fatherPhoneExt = fatherPhoneExt;
	}

	public String getFatherCellPhone() {
		return this.fatherCellPhone;
	}

	public void setFatherCellPhone(String fatherCellPhone) {
		this.fatherCellPhone = fatherCellPhone;
	}

	public String getFatherPermAddrCode() {
		return this.fatherPermAddrCode;
	}

	public void setFatherPermAddrCode(String fatherPermAddrCode) {
		this.fatherPermAddrCode = fatherPermAddrCode;
	}

	public String getFatherPermAddr() {
		return this.fatherPermAddr;
	}

	public void setFatherPermAddr(String fatherPermAddr) {
		this.fatherPermAddr = fatherPermAddr;
	}

	public String getMotherStatus() {
		return this.motherStatus;
	}

	public void setMotherStatus(String motherStatus) {
		this.motherStatus = motherStatus;
	}

	public String getMotherIsSponsor() {
		return this.motherIsSponsor;
	}

	public void setMotherIsSponsor(String motherIsSponsor) {
		this.motherIsSponsor = motherIsSponsor;
	}

	public String getMotherName() {
		return this.motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getMotherIdno() {
		return this.motherIdno;
	}

	public void setMotherIdno(String motherIdno) {
		this.motherIdno = motherIdno;
	}

	public String getMotherPhoneCode() {
		return this.motherPhoneCode;
	}

	public void setMotherPhoneCode(String motherPhoneCode) {
		this.motherPhoneCode = motherPhoneCode;
	}

	public String getMotherPhone() {
		return this.motherPhone;
	}

	public void setMotherPhone(String motherPhone) {
		this.motherPhone = motherPhone;
	}

	public String getMotherPhoneExt() {
		return this.motherPhoneExt;
	}

	public void setMotherPhoneExt(String motherPhoneExt) {
		this.motherPhoneExt = motherPhoneExt;
	}

	public String getMotherCellPhone() {
		return this.motherCellPhone;
	}

	public void setMotherCellPhone(String motherCellPhone) {
		this.motherCellPhone = motherCellPhone;
	}

	public String getMotherPermAddrCode() {
		return this.motherPermAddrCode;
	}

	public void setMotherPermAddrCode(String motherPermAddrCode) {
		this.motherPermAddrCode = motherPermAddrCode;
	}

	public String getMotherPermAddr() {
		return this.motherPermAddr;
	}

	public void setMotherPermAddr(String motherPermAddr) {
		this.motherPermAddr = motherPermAddr;
	}

	public String getSpouseIsSponsor() {
		return this.spouseIsSponsor;
	}

	public void setSpouseIsSponsor(String spouseIsSponsor) {
		this.spouseIsSponsor = spouseIsSponsor;
	}

	public String getSpouseName() {
		return this.spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getSpouseIdno() {
		return this.spouseIdno;
	}

	public void setSpouseIdno(String spouseIdno) {
		this.spouseIdno = spouseIdno;
	}

	public String getSpousePhoneCode() {
		return this.spousePhoneCode;
	}

	public void setSpousePhoneCode(String spousePhoneCode) {
		this.spousePhoneCode = spousePhoneCode;
	}

	public String getSpousePhone() {
		return this.spousePhone;
	}

	public void setSpousePhone(String spousePhone) {
		this.spousePhone = spousePhone;
	}

	public String getSpousePhoneExt() {
		return this.spousePhoneExt;
	}

	public void setSpousePhoneExt(String spousePhoneExt) {
		this.spousePhoneExt = spousePhoneExt;
	}

	public String getSpouseCellPhone() {
		return this.spouseCellPhone;
	}

	public void setSpouseCellPhone(String spouseCellPhone) {
		this.spouseCellPhone = spouseCellPhone;
	}

	public String getSpousePermAddrCode() {
		return this.spousePermAddrCode;
	}

	public void setSpousePermAddrCode(String spousePermAddrCode) {
		this.spousePermAddrCode = spousePermAddrCode;
	}

	public String getSpousePermAddr() {
		return this.spousePermAddr;
	}

	public void setSpousePermAddr(String spousePermAddr) {
		this.spousePermAddr = spousePermAddr;
	}

	public String getGuardianRelational() {
		return this.guardianRelational;
	}

	public void setGuardianRelational(String guardianRelational) {
		this.guardianRelational = guardianRelational;
	}

	public String getGuardianName() {
		return this.guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianIdno() {
		return this.guardianIdno;
	}

	public void setGuardianIdno(String guardianIdno) {
		this.guardianIdno = guardianIdno;
	}

	public String getGuardianPhoneCode() {
		return this.guardianPhoneCode;
	}

	public void setGuardianPhoneCode(String guardianPhoneCode) {
		this.guardianPhoneCode = guardianPhoneCode;
	}

	public String getGuardianPhone() {
		return this.guardianPhone;
	}

	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}

	public String getGuardianPhoneExt() {
		return this.guardianPhoneExt;
	}

	public void setGuardianPhoneExt(String guardianPhoneExt) {
		this.guardianPhoneExt = guardianPhoneExt;
	}

	public String getGuardianCellPhone() {
		return this.guardianCellPhone;
	}

	public void setGuardianCellPhone(String guardianCellPhone) {
		this.guardianCellPhone = guardianCellPhone;
	}

	public String getGuardianPermAddrCode() {
		return this.guardianPermAddrCode;
	}

	public void setGuardianPermAddrCode(String guardianPermAddrCode) {
		this.guardianPermAddrCode = guardianPermAddrCode;
	}

	public String getGuardianPermAddr() {
		return this.guardianPermAddr;
	}

	public void setGuardianPermAddr(String guardianPermAddr) {
		this.guardianPermAddr = guardianPermAddr;
	}

	public String getSponsorRelational() {
		return this.sponsorRelational;
	}

	public void setSponsorRelational(String sponsorRelational) {
		this.sponsorRelational = sponsorRelational;
	}

	public String getSponsorName() {
		return this.sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getSponsorIdno() {
		return this.sponsorIdno;
	}

	public void setSponsorIdno(String sponsorIdno) {
		this.sponsorIdno = sponsorIdno;
	}

	public String getSponsorPhoneCode() {
		return this.sponsorPhoneCode;
	}

	public void setSponsorPhoneCode(String sponsorPhoneCode) {
		this.sponsorPhoneCode = sponsorPhoneCode;
	}

	public String getSponsorPhoneExt() {
		return this.sponsorPhoneExt;
	}

	public void setSponsorPhoneExt(String sponsorPhoneExt) {
		this.sponsorPhoneExt = sponsorPhoneExt;
	}

	public String getSponsorPhone() {
		return this.sponsorPhone;
	}

	public void setSponsorPhone(String sponsorPhone) {
		this.sponsorPhone = sponsorPhone;
	}

	public String getSponsorCellPhone() {
		return this.sponsorCellPhone;
	}

	public void setSponsorCellPhone(String sponsorCellPhone) {
		this.sponsorCellPhone = sponsorCellPhone;
	}

	public String getSponsorPermAddrCode() {
		return this.sponsorPermAddrCode;
	}

	public void setSponsorPermAddrCode(String sponsorPermAddrCode) {
		this.sponsorPermAddrCode = sponsorPermAddrCode;
	}

	public String getSponsorPermAddr() {
		return this.sponsorPermAddr;
	}

	public void setSponsorPermAddr(String sponsorPermAddr) {
		this.sponsorPermAddr = sponsorPermAddr;
	}

	public String getBankNo() {
		return this.bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}