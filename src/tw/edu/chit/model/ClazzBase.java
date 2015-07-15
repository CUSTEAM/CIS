package tw.edu.chit.model;

public class ClazzBase {
	
	private String campusName = "";
	private String schoolName = "";
	private String deptName = "";
	private String classFullName = "";
	
	public String getClassFullName() {
		return classFullName;
	}
	public void setClassFullName(String classFullName) {
		this.classFullName = classFullName;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
}
