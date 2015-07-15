package tw.edu.chit.model;

/**
 * FileName entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FileName implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String name;
	private String fnType;

	// Constructors

	/** default constructor */
	public FileName() {
	}

	/** full constructor */
	public FileName(String name, String fnType) {
		this.name = name;
		this.fnType = fnType;
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

	public String getFnType() {
		return this.fnType;
	}

	public void setFnType(String fnType) {
		this.fnType = fnType;
	}

}