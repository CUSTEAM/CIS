package tw.edu.chit.model;

import java.io.Serializable;
import java.util.Date;

public class TeacherStayTimeModify implements Serializable {

	private static final long serialVersionUID = 7129165438110545401L;

	private String schoolYear;
	private String schoolTerm;
	private Date lastModified;

	private Empl empl;

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSchoolTerm() {
		return schoolTerm;
	}

	public void setSchoolTerm(String schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Empl getEmpl() {
		return empl;
	}

	public void setEmpl(Empl empl) {
		this.empl = empl;
	}

}
