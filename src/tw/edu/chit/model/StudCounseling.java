package tw.edu.chit.model;

import java.util.Date;
/**
 * StudCounseling entity. @author MyEclipse Persistence Tools
 */

public class StudCounseling extends StudCounselingBase implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Short schoolYear;
	private Short schoolTerm;
	private String teacherId;
	private String ctype;
	private String cscode;
	private String courseName;
	private String courseClass;
	private String studentNo;
	private String departClass;
	private Date cdate;
	private Integer itemNo;
	private String content;
	private Date mdate;

	// Constructors

	/** default constructor */
	public StudCounseling() {
	}

	/** minimal constructor */
	public StudCounseling(Short schoolYear, Short schoolTerm, String teacherId,
			String ctype, String studentNo, String departClass,
			Date cdate, Integer itemNo, Date mdate) {
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.teacherId = teacherId;
		this.ctype = ctype;
		this.studentNo = studentNo;
		this.departClass = departClass;
		this.cdate = cdate;
		this.itemNo = itemNo;
		this.mdate = mdate;
	}

	/** full constructor */
	public StudCounseling(Short schoolYear, Short schoolTerm, String teacherId,
			String ctype, String courseClass, String cscode, String courseName, String studentNo,
			String departClass, Date cdate, Integer itemNo,
			String content, Date mdate) {
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.teacherId = teacherId;
		this.ctype = ctype;
		this.courseClass = courseClass;
		this.cscode = cscode;
		this.courseName = courseName;
		this.studentNo = studentNo;
		this.departClass = departClass;
		this.cdate = cdate;
		this.itemNo = itemNo;
		this.content = content;
		this.mdate = mdate;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Short getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(Short schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Short getSchoolTerm() {
		return this.schoolTerm;
	}

	public void setSchoolTerm(Short schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public String getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getCtype() {
		return this.ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public String getCourseClass() {
		return courseClass;
	}

	public void setCourseClass(String courseClass) {
		this.courseClass = courseClass;
	}

	public String getCscode() {
		return this.cscode;
	}

	public void setCscode(String cscode) {
		this.cscode = cscode;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getDepartClass() {
		return this.departClass;
	}

	public void setDepartClass(String departClass) {
		this.departClass = departClass;
	}

	public Date getCdate() {
		return this.cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public Integer getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getMdate() {
		return this.mdate;
	}

	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

}