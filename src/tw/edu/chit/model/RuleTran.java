package tw.edu.chit.model;

/**
 * RuleTran entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RuleTran extends RuleTranBase implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String departClass;
	private Float score;
	private Float mscore;

	// Constructors

	/** default constructor */
	public RuleTran() {
	}

	/** minimal constructor */
	public RuleTran(Integer oid, String departClass, Float mscore) {
		this.oid = oid;
		this.departClass = departClass;
		this.mscore = mscore;
	}

	/** full constructor */
	public RuleTran(Integer oid, String departClass, Float score, Float mscore) {
		this.oid = oid;
		this.departClass = departClass;
		this.score = score;
		this.mscore = mscore;
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

	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}
	
	public Float getMscore() {
		return mscore;
	}

	public void setMscore(Float mscore) {
		this.mscore = mscore;
	}


}