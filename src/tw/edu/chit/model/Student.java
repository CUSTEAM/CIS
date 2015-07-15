package tw.edu.chit.model;

import java.util.Date;

/**
 * 
 * @author JOHN
 * 
 */
public class Student extends StudentBase implements java.io.Serializable {

	// 除了實體欄位，別再放其它	
	private Integer oid;
	private String departClass;
	private String studentNo;
	private String studentName;
	private String sex;
	private Date birthday;
	private String idno;
	private Short entrance;
	private Short gradyear;
	private String ident;
	private String divi;
	private String birthProvince;
	private String birthCounty;
	private String currPost;
	private String currAddr;
	private String schlCode;
	private String gradDept;
	private String graduStatus;
	private String parentName;
	private String telephone;
	private String permPost;
	private String permAddr;
	private String occurStatus;
	private Short occurYear;
	private String occurTerm;
	private Date occurDate;
	private String occurDocno;
	private String occurCause;
	private String occurGraduateNo;
	private String identBasic;
	private String extraStatus;
	private String extraDept;
	private String email;
	private String cellPhone;
	private String identRemark;
	private Short gradyearOrigin;
	private String studentEname;
	private String schlName;

	// Constructors

	/** default constructor */
	public Student() {
	}

	/** minimal constructor */
	public Student(String departClass, String studentNo, String studentName) {
		this.departClass = departClass;
		this.studentNo = studentNo;
		this.studentName = studentName;
	}

	/** full constructor */
	public Student(String departClass, String studentNo, String studentName,
			String sex, Date birthday, String idno, Short entrance,
			Short gradyear, String ident, String divi, String birthProvince,
			String birthCounty, String currPost, String currAddr,
			String schlCode, String gradDept, String graduStatus,
			String parentName, String telephone, String permPost,
			String permAddr, String occurStatus, Short occurYear,
			String occurTerm, Date occurDate, String occurDocno,
			String occurCause, String occurGraduateNo, String identBasic,
			String extraStatus, String extraDept, String email,
			String cellPhone, String identRemark, Short gradyearOrigin,
			String studentEname, String schlName) {
		this.departClass = departClass;
		this.studentNo = studentNo;
		this.studentName = studentName;
		this.sex = sex;
		this.birthday = birthday;
		this.idno = idno;
		this.entrance = entrance;
		this.gradyear = gradyear;
		this.ident = ident;
		this.divi = divi;
		this.birthProvince = birthProvince;
		this.birthCounty = birthCounty;
		this.currPost = currPost;
		this.currAddr = currAddr;
		this.schlCode = schlCode;
		this.gradDept = gradDept;
		this.graduStatus = graduStatus;
		this.parentName = parentName;
		this.telephone = telephone;
		this.permPost = permPost;
		this.permAddr = permAddr;
		this.occurStatus = occurStatus;
		this.occurYear = occurYear;
		this.occurTerm = occurTerm;
		this.occurDate = occurDate;
		this.occurDocno = occurDocno;
		this.occurCause = occurCause;
		this.occurGraduateNo = occurGraduateNo;
		this.identBasic = identBasic;
		this.extraStatus = extraStatus;
		this.extraDept = extraDept;
		this.email = email;
		this.cellPhone = cellPhone;
		this.identRemark = identRemark;
		this.gradyearOrigin = gradyearOrigin;
		this.studentEname = studentEname;
		this.schlName = schlName;
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

	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public Short getEntrance() {
		return this.entrance;
	}

	public void setEntrance(Short entrance) {
		this.entrance = entrance;
	}

	public Short getGradyear() {
		return this.gradyear;
	}

	public void setGradyear(Short gradyear) {
		this.gradyear = gradyear;
	}

	public String getIdent() {
		return this.ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getDivi() {
		return this.divi;
	}

	public void setDivi(String divi) {
		this.divi = divi;
	}

	public String getBirthProvince() {
		return this.birthProvince;
	}

	public void setBirthProvince(String birthProvince) {
		this.birthProvince = birthProvince;
	}

	public String getBirthCounty() {
		return this.birthCounty;
	}

	public void setBirthCounty(String birthCounty) {
		this.birthCounty = birthCounty;
	}

	public String getCurrPost() {
		return this.currPost;
	}

	public void setCurrPost(String currPost) {
		this.currPost = currPost;
	}

	public String getCurrAddr() {
		return this.currAddr;
	}

	public void setCurrAddr(String currAddr) {
		this.currAddr = currAddr;
	}

	public String getSchlCode() {
		return this.schlCode;
	}

	public void setSchlCode(String schlCode) {
		this.schlCode = schlCode;
	}

	public String getGradDept() {
		return this.gradDept;
	}

	public void setGradDept(String gradDept) {
		this.gradDept = gradDept;
	}

	public String getGraduStatus() {
		return this.graduStatus;
	}

	public void setGraduStatus(String graduStatus) {
		this.graduStatus = graduStatus;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPermPost() {
		return this.permPost;
	}

	public void setPermPost(String permPost) {
		this.permPost = permPost;
	}

	public String getPermAddr() {
		return this.permAddr;
	}

	public void setPermAddr(String permAddr) {
		this.permAddr = permAddr;
	}

	public String getOccurStatus() {
		return this.occurStatus;
	}

	public void setOccurStatus(String occurStatus) {
		this.occurStatus = occurStatus;
	}

	public Short getOccurYear() {
		return this.occurYear;
	}

	public void setOccurYear(Short occurYear) {
		this.occurYear = occurYear;
	}

	public String getOccurTerm() {
		return this.occurTerm;
	}

	public void setOccurTerm(String occurTerm) {
		this.occurTerm = occurTerm;
	}

	public Date getOccurDate() {
		return this.occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}

	public String getOccurDocno() {
		return this.occurDocno;
	}

	public void setOccurDocno(String occurDocno) {
		this.occurDocno = occurDocno;
	}

	public String getOccurCause() {
		return this.occurCause;
	}

	public void setOccurCause(String occurCause) {
		this.occurCause = occurCause;
	}

	public String getOccurGraduateNo() {
		return this.occurGraduateNo;
	}

	public void setOccurGraduateNo(String occurGraduateNo) {
		this.occurGraduateNo = occurGraduateNo;
	}

	public String getIdentBasic() {
		return this.identBasic;
	}

	public void setIdentBasic(String identBasic) {
		this.identBasic = identBasic;
	}

	public String getExtraStatus() {
		return this.extraStatus;
	}

	public void setExtraStatus(String extraStatus) {
		this.extraStatus = extraStatus;
	}

	public String getExtraDept() {
		return this.extraDept;
	}

	public void setExtraDept(String extraDept) {
		this.extraDept = extraDept;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellPhone() {
		return this.cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getIdentRemark() {
		return this.identRemark;
	}

	public void setIdentRemark(String identRemark) {
		this.identRemark = identRemark;
	}

	public Short getGradyearOrigin() {
		return this.gradyearOrigin;
	}

	public void setGradyearOrigin(Short gradyearOrigin) {
		this.gradyearOrigin = gradyearOrigin;
	}

	public String getStudentEname() {
		return this.studentEname;
	}

	public void setStudentEname(String studentEname) {
		this.studentEname = studentEname;
	}

	public String getSchlName() {
		return this.schlName;
	}

	public void setSchlName(String schlName) {
		this.schlName = schlName;
	}

}