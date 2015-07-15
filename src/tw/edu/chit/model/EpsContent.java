package tw.edu.chit.model;

/**
 * EpsContent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EpsContent implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String uid;
	private Integer tableOid;
	private Integer fieldOid;
	private String content;
	private Integer sequence;

	// Constructors

	/** default constructor */
	public EpsContent() {
	}

	/** minimal constructor */
	public EpsContent(Integer sequence) {
		this.sequence = sequence;
	}

	/** full constructor */
	public EpsContent(String uid, Integer tableOid, Integer fieldOid,
			String content, Integer sequence) {
		this.uid = uid;
		this.tableOid = tableOid;
		this.fieldOid = fieldOid;
		this.content = content;
		this.sequence = sequence;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getTableOid() {
		return this.tableOid;
	}

	public void setTableOid(Integer tableOid) {
		this.tableOid = tableOid;
	}

	public Integer getFieldOid() {
		return this.fieldOid;
	}

	public void setFieldOid(Integer fieldOid) {
		this.fieldOid = fieldOid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

}