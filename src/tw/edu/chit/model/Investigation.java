package tw.edu.chit.model;

import java.util.Date;

/**
 * Inventigation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Investigation implements java.io.Serializable {

	private static final long serialVersionUID = -2209263683157220140L;

	private Integer oid;
	private String schoolYear;
	private String studentNo;
	private String tutorIdno;
	private String post;
	private String address;
	private String phone;
	private String cellPhone;
	private String email;
	private String invesType;
	private String foreignOrNot;
	private String schoolName;
	private String canpus;
	private String level;
	private String company;
	private String companyType;
	private String workNature;
	private String workDuty;
	private String companyPhone;
	private String workTitle;
	private String bossName;
	private String bossEmail;
	private String bossAddr;
	private Date workStart;
	private Date workEnd;
	private String salaryRange;
	private String armyType;
	private String armyName;
	private String armyLevel;
	// private String foreignSchool;
	private Military military = Military.NO;
	private Exam exam = Exam.NO;
	private Other other = Other.NO;
	private Date lastModified;

	// private Student student;

	public Investigation() {
	}

	public Investigation(Integer oid) {
		this.oid = oid;
	}

	public Investigation(Integer oid, String post, String address,
			String phone, String cellPhone, String email, String schoolName,
			String company, Other other, Date lastModified) {
		this.oid = oid;
		this.post = post;
		this.address = address;
		this.phone = phone;
		this.cellPhone = cellPhone;
		this.email = email;
		this.schoolName = schoolName;
		this.company = company;
		this.other = other;
		this.lastModified = lastModified;
	}

	// public Student getStudent() {
	// return student;
	// }
	//
	// public void setStudent(Student student) {
	// this.student = student;
	// }

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getTutorIdno() {
		return tutorIdno;
	}

	public void setTutorIdno(String tutorIdno) {
		this.tutorIdno = tutorIdno;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getForeignOrNot() {
		return foreignOrNot;
	}

	public void setForeignOrNot(String foreignOrNot) {
		this.foreignOrNot = foreignOrNot;
	}

	public String getCanpus() {
		return canpus;
	}

	public void setCanpus(String canpus) {
		this.canpus = canpus;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getWorkNature() {
		return workNature;
	}

	public void setWorkNature(String workNature) {
		this.workNature = workNature;
	}

	public String getWorkDuty() {
		return workDuty;
	}

	public void setWorkDuty(String workDuty) {
		this.workDuty = workDuty;
	}

	public String getWorkTitle() {
		return workTitle;
	}

	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
	}

	public Date getWorkStart() {
		return workStart;
	}

	public void setWorkStart(Date workStart) {
		this.workStart = workStart;
	}

	public Date getWorkEnd() {
		return workEnd;
	}

	public void setWorkEnd(Date workEnd) {
		this.workEnd = workEnd;
	}

	public String getSchoolName() {
		return this.schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getInvesType() {
		return invesType;
	}

	public void setInvesType(String invesType) {
		this.invesType = invesType;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public String getBossEmail() {
		return bossEmail;
	}

	public void setBossEmail(String bossEmail) {
		this.bossEmail = bossEmail;
	}

	public String getBossAddr() {
		return bossAddr;
	}

	public void setBossAddr(String bossAddr) {
		this.bossAddr = bossAddr;
	}

	public String getSalaryRange() {
		return salaryRange;
	}

	public void setSalaryRange(String salaryRange) {
		this.salaryRange = salaryRange;
	}

	public String getArmyType() {
		return armyType;
	}

	public void setArmyType(String armyType) {
		this.armyType = armyType;
	}

	public String getArmyName() {
		return armyName;
	}

	public void setArmyName(String armyName) {
		this.armyName = armyName;
	}

	public String getArmyLevel() {
		return armyLevel;
	}

	public void setArmyLevel(String armyLevel) {
		this.armyLevel = armyLevel;
	}

	public Other getOther() {
		return other;
	}

	public void setOther(Other other) {
		this.other = other;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Military getMilitary() {
		return military;
	}

	public void setMilitary(Military military) {
		this.military = military;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

}