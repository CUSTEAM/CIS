package tw.edu.chit.model;

/**
 * EpsActEvaluator entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EpsActEvaluator implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer actOid;
	private String techid;

	// Constructors

	/** default constructor */
	public EpsActEvaluator() {
	}

	/** full constructor */
	public EpsActEvaluator(Integer actOid, String techid) {
		this.actOid = actOid;
		this.techid = techid;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getActOid() {
		return this.actOid;
	}

	public void setActOid(Integer actOid) {
		this.actOid = actOid;
	}

	public String getTechid() {
		return this.techid;
	}

	public void setTechid(String techid) {
		this.techid = techid;
	}

}