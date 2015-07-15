package tw.edu.chit.model;

/**
 * AmsRevokedDoc entity. @author MyEclipse Persistence Tools
 */

public class AmsRevokedDoc implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer docOid;
	private Integer revokedOid;

	// Constructors

	/** default constructor */
	public AmsRevokedDoc() {
	}

	/** full constructor */
	public AmsRevokedDoc(Integer docOid, Integer revokedOid) {
		this.docOid = docOid;
		this.revokedOid = revokedOid;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getDocOid() {
		return this.docOid;
	}

	public void setDocOid(Integer docOid) {
		this.docOid = docOid;
	}

	public Integer getRevokedOid() {
		return this.revokedOid;
	}

	public void setRevokedOid(Integer revokedOid) {
		this.revokedOid = revokedOid;
	}

}