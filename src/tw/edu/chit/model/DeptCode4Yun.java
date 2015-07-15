package tw.edu.chit.model;

/**
 * DeptCode4yun entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class DeptCode4Yun implements java.io.Serializable {

	private static final long serialVersionUID = 7098935200040611163L;

	private Integer oid;
	private String classNo;
	private String deptCode;
	private String deptName;
	private String campusCode;
	private String campusName;
	private Integer gradeYear;

	public DeptCode4Yun() {
	}

	public DeptCode4Yun(Integer oid, String classNo, String deptCode,
			String deptName, String campusCode, String campusName,
			Integer gradeYear) {
		this.oid = oid;
		this.classNo = classNo;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.campusCode = campusCode;
		this.campusName = campusName;
		this.gradeYear = gradeYear;
	}

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

	public String getDeptCode() {
		return this.deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCampusCode() {
		return this.campusCode;
	}

	public void setCampusCode(String campusCode) {
		this.campusCode = campusCode;
	}

	public String getCampusName() {
		return this.campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}

	public Integer getGradeYear() {
		return this.gradeYear;
	}

	public void setGradeYear(Integer gradeYear) {
		this.gradeYear = gradeYear;
	}

}