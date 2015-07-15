package tw.edu.chit.model;

/**
 * FileUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FileUser implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer fnOid;
	private Integer emplOid;
	private String fuType;

	// Constructors

	/** default constructor */
	public FileUser() {
	}

	/** full constructor */
	public FileUser(Integer fnOid, Integer emplOid, String fuType) {
		this.fnOid = fnOid;
		this.emplOid = emplOid;
		this.fuType = fuType;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getFnOid() {
		return this.fnOid;
	}

	public void setFnOid(Integer fnOid) {
		this.fnOid = fnOid;
	}

	public Integer getEmplOid() {
		return this.emplOid;
	}

	public void setEmplOid(Integer emplOid) {
		this.emplOid = emplOid;
	}

	public String getFuType() {
		return this.fuType;
	}

	public void setFuType(String fuType) {
		this.fuType = fuType;
	}

}