package tw.edu.chit.model;

import java.sql.Timestamp;

/**
 * ClassEx entity. @author MyEclipse Persistence Tools
 */

public class ClassEx implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String classNo;
	private String className;
	private String campusNo;
	private String schoolNo;
	private String schoolType;
	private String deptNo;
	private String grade;
	private String type;
	private String shortName;
	private String dept;
	private String editor;
	private Timestamp editime;
	private String instNo;
	private String schNo;

	// Constructors

	/** default constructor */
	public ClassEx() {
	}

	/** minimal constructor */
	public ClassEx(String classNo, String className, String campusNo,
			String schoolNo, String schoolType, String deptNo, String grade,
			String type, String shortName, String dept, Timestamp editime) {
		this.classNo = classNo;
		this.className = className;
		this.campusNo = campusNo;
		this.schoolNo = schoolNo;
		this.schoolType = schoolType;
		this.deptNo = deptNo;
		this.grade = grade;
		this.type = type;
		this.shortName = shortName;
		this.dept = dept;
		this.editime = editime;
	}

	/** full constructor */
	public ClassEx(String classNo, String className, String campusNo,
			String schoolNo, String schoolType, String deptNo, String grade,
			String type, String shortName, String dept, String editor,
			Timestamp editime, String instNo, String schNo) {
		this.classNo = classNo;
		this.className = className;
		this.campusNo = campusNo;
		this.schoolNo = schoolNo;
		this.schoolType = schoolType;
		this.deptNo = deptNo;
		this.grade = grade;
		this.type = type;
		this.shortName = shortName;
		this.dept = dept;
		this.editor = editor;
		this.editime = editime;
		this.instNo = instNo;
		this.schNo = schNo;
	}

	// Property accessors

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

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCampusNo() {
		return this.campusNo;
	}

	public void setCampusNo(String campusNo) {
		this.campusNo = campusNo;
	}

	public String getSchoolNo() {
		return this.schoolNo;
	}

	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
	}

	public String getSchoolType() {
		return this.schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Timestamp getEditime() {
		return this.editime;
	}

	public void setEditime(Timestamp editime) {
		this.editime = editime;
	}

	public String getInstNo() {
		return this.instNo;
	}

	public void setInstNo(String instNo) {
		this.instNo = instNo;
	}

	public String getSchNo() {
		return this.schNo;
	}

	public void setSchNo(String schNo) {
		this.schNo = schNo;
	}

}