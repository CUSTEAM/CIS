package tw.edu.chit.model;

/**
 * EpsField entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EpsField implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer type;
	private Integer tableOid;
	private String name;
	private Integer sequence;
	private Integer size;

	// Constructors

	/** default constructor */
	public EpsField() {
	}

	/** full constructor */
	public EpsField(Integer type, Integer tableOid, String name,
			Integer sequence, Integer size) {
		this.type = type;
		this.tableOid = tableOid;
		this.name = name;
		this.sequence = sequence;
		this.size = size;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTableOid() {
		return this.tableOid;
	}

	public void setTableOid(Integer tableOid) {
		this.tableOid = tableOid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

}