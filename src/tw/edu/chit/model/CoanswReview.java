package tw.edu.chit.model;

/**
 * CoanswReview entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CoanswReview implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String cscode;
	private Short schoolYear;
	private String schoolTerm;
	private String departClass;
	private String techid;
	private String contentPer;
	private Float score;
	private String contentMst;

	// Constructors

	/** default constructor */
	public CoanswReview() {
	}

	/** minimal constructor */
	public CoanswReview(String cscode, Short schoolYear, String schoolTerm,
			String departClass, Float score) {
		this.cscode = cscode;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.departClass = departClass;
		this.score = score;
	}

	/** full constructor */
	public CoanswReview(String cscode, Short schoolYear, String schoolTerm,
			String departClass, String techid, String contentPer, Float score,
			String contentMst) {
		this.cscode = cscode;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.departClass = departClass;
		this.techid = techid;
		this.contentPer = contentPer;
		this.score = score;
		this.contentMst = contentMst;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCscode() {
		return this.cscode;
	}

	public void setCscode(String cscode) {
		this.cscode = cscode;
	}

	public Short getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(Short schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSchoolTerm() {
		return this.schoolTerm;
	}

	public void setSchoolTerm(String schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public String getDepartClass() {
		return this.departClass;
	}

	public void setDepartClass(String departClass) {
		this.departClass = departClass;
	}

	public String getTechid() {
		return this.techid;
	}

	public void setTechid(String techid) {
		this.techid = techid;
	}

	public String getContentPer() {
		return this.contentPer;
	}

	public void setContentPer(String contentPer) {
		this.contentPer = contentPer;
	}

	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getContentMst() {
		return this.contentMst;
	}

	public void setContentMst(String contentMst) {
		this.contentMst = contentMst;
	}

}