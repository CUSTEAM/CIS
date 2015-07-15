package tw.edu.chit.model;

public class StudCounselingBase {
	private String className;
	private String itemName;
	private String studentName;
	private String simpleCdate;
	private String teacherName;
	
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getSimpleCdate() {
		return simpleCdate;
	}
	public void setSimpleCdate(String simpleCdate) {
		this.simpleCdate = simpleCdate;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

}
