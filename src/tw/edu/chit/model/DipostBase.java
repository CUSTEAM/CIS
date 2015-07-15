package tw.edu.chit.model;

import java.io.Serializable;

public class DipostBase implements Serializable {

	private static final long serialVersionUID = -1422399195677583542L;

	private String studentName;
	private String departClass;
	private String kindName;

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

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

}
