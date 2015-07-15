package tw.edu.chit.model;

/**
 * Rccode entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Rccode implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String name;
	private String type;
	private String id;
	private Integer sequence;

	// Constructors

	/** default constructor */
	public Rccode() {
	}

	/** minimal constructor */
	public Rccode(String id) {
		this.id = id;
	}

	/** full constructor */
	public Rccode(String name, String type, String id, Integer sequence) {
		this.name = name;
		this.type = type;
		this.id = id;
		this.sequence = sequence;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

}