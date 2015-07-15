package tw.edu.chit.model;

/**
 * Clean entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Clean extends CleanBase implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String departClass;
	private Short weekNo;
	private Float score;

	// Constructors

	/** default constructor */
	public Clean() {
	}

	/** full constructor */
	public Clean(Integer oid, String departClass, Short weekNo, Float score) {
		this.oid = oid;
		this.departClass = departClass;
		this.weekNo = weekNo;
		this.score = score;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getDepartClass() {
		return this.departClass;
	}

	public void setDepartClass(String departClass) {
		this.departClass = departClass;
	}

	public Short getWeekNo() {
		return this.weekNo;
	}

	public void setWeekNo(Short weekNo) {
		this.weekNo = weekNo;
	}

	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

}