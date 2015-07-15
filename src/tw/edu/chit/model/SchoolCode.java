package tw.edu.chit.model;

public class SchoolCode implements java.io.Serializable {

	private static final long serialVersionUID = 6767896466693416102L;

	private Integer oid;
	private String code;
	private String name;

	/** default constructor */
	public SchoolCode() {
	}

	/** full constructor */
	public SchoolCode(Integer oid, String code, String name) {
		this.oid = oid;
		this.code = code;
		this.name = name;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}