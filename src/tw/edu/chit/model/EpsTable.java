package tw.edu.chit.model;

/**
 * EpsTable entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EpsTable implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String name;
	private Byte sys;
	private Integer sequence;

	// Constructors

	/** default constructor */
	public EpsTable() {
	}

	/** minimal constructor */
	public EpsTable(Byte sys) {
		this.sys = sys;
	}

	/** full constructor */
	public EpsTable(String name, Byte sys, Integer sequence) {
		this.name = name;
		this.sys = sys;
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

	public Byte getSys() {
		return this.sys;
	}

	public void setSys(Byte sys) {
		this.sys = sys;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

}