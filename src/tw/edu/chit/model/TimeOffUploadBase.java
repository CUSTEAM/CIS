package tw.edu.chit.model;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TimeOffUploadBase {
	// Fields

	private String subjectName;		//課程名稱
	private String deptClassName;	//開課班級名稱
	private String sddate;			//日期
	// Constructors

	// Property accessors

	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getDeptClassName() {
		return this.deptClassName;
	}

	public void setDeptClassName(String deptClassName) {
		this.deptClassName = deptClassName;
	}

	public String getSddate() {
		return this.sddate;
	}

	public void setSddate(String sddate) {
		this.sddate = sddate;
	}
	
}
