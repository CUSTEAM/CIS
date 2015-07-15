package tw.edu.chit.model;

public class RrateBase {

	private String deptClassName="";
	private String cscodeName="";
	private String teacherName="";
	private String teacherId="";

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getCscodeName() {
		return cscodeName;
	}

	public void setCscodeName(String cscodeName) {
		this.cscodeName = cscodeName;
	}

	public String getDeptClassName() {
		return deptClassName;
	}

	public void setDeptClassName(String deptClassName) {
		this.deptClassName = deptClassName;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}


}
