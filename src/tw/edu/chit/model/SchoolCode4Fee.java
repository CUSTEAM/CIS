package tw.edu.chit.model;

/**
 * SchoolCode4fee entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SchoolCode4Fee implements java.io.Serializable {

	private static final long serialVersionUID = -4817661476511229893L;

	private Integer oid;
	private String code;
	private String name;

	public SchoolCode4Fee() {
	}

	public SchoolCode4Fee(Integer oid, String code, String name) {
		this.oid = oid;
		this.code = code;
		this.name = name;
	}

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