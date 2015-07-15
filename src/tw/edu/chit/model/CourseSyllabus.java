package tw.edu.chit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseSyllabus implements java.io.Serializable {

	private static final long serialVersionUID = -1333756218055471504L;

	private Integer oid;
	private Integer dtimeOid;
	private Integer schoolYear;
	private Integer schoolTerm;
	private String departClass;
	private String cscode;
	private String officeHours;
	private String prerequisites;
	private String objectives;
	private String syllabus;
	private Date lastModified;
	private List<Syllabus> syllabuses = new ArrayList<Syllabus>(0);

	/** default constructor */
	public CourseSyllabus() {
	}	

	/** full constructor */
	public CourseSyllabus(Integer dtimeOid, Integer schoolYear,
			Integer schoolTerm, String officeHours, String prerequisites,
			String objectives, String syllabus, Date lastModified,
			List<Syllabus> syllabuses) {
		this.dtimeOid = dtimeOid;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.officeHours = officeHours;
		this.prerequisites = prerequisites;
		this.objectives = objectives;
		this.syllabus = syllabus;
		this.lastModified = lastModified;
		this.syllabuses = syllabuses;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getDtimeOid() {
		return this.dtimeOid;
	}

	public void setDtimeOid(Integer dtimeOid) {
		this.dtimeOid = dtimeOid;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Integer getSchoolTerm() {
		return schoolTerm;
	}

	public void setSchoolTerm(Integer schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public String getDepartClass() {
		return departClass;
	}

	public void setDepartClass(String departClass) {
		this.departClass = departClass;
	}

	public String getCscode() {
		return cscode;
	}

	public void setCscode(String cscode) {
		this.cscode = cscode;
	}

	public String getOfficeHours() {
		return this.officeHours;
	}

	public void setOfficeHours(String officeHours) {
		this.officeHours = officeHours;
	}

	public String getPrerequisites() {
		return this.prerequisites;
	}

	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

	public String getObjectives() {
		return this.objectives;
	}

	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	public String getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public List<Syllabus> getSyllabuses() {
		return this.syllabuses;
	}

	public void setSyllabuses(List<Syllabus> syllabuses) {
		this.syllabuses = syllabuses;
	}

}