package tw.edu.chit.model;

/**
 * MidtermExcluded generated by MyEclipse Persistence Tools
 */

public class MidtermExcluded implements java.io.Serializable {

	private static final long serialVersionUID = -6948690612063001303L;

	private Integer oid;
	private String departClass;
	private String cscode;

	/** default constructor */
	public MidtermExcluded() {
	}

	/** minimal constructor */
	public MidtermExcluded(Integer oid, String departClass) {
		this.oid = oid;
		this.departClass = departClass;
	}

	/** full constructor */
	public MidtermExcluded(Integer oid, String departClass, String cscode) {
		this.oid = oid;
		this.departClass = departClass;
		this.cscode = cscode;
	}

	public MidtermExcluded(String departClass, String cscode) {
		super();
		this.departClass = departClass;
		this.cscode = cscode;
	}
	
	public MidtermExcluded(String departClass) {
		super();
		this.departClass = departClass;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getDepartClass() {
		return this.departClass;
	}

	public void setDepartClass(String departClass) {
		this.departClass = departClass;
	}

	public String getCscode() {
		return this.cscode;
	}

	public void setCscode(String cscode) {
		this.cscode = cscode;
	}

}