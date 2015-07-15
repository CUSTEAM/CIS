package tw.edu.chit.model;

/**
 * Entrno generated by MyEclipse - Hibernate Tools
 */

public class Entrno implements java.io.Serializable {

	private static final long serialVersionUID = 8224715671464642789L;

	private Integer oid;
	private String firstStno;
	private String secondStno;
	private String permissionNo;

	// Constructors

	/** default constructor */
	public Entrno() {
	}

	/** minimal constructor */
	public Entrno(String firstStno, String secondStno) {
		this.firstStno = firstStno;
		this.secondStno = secondStno;
	}

	/** full constructor */
	public Entrno(String firstStno, String secondStno, String permissionNo) {
		this.firstStno = firstStno;
		this.secondStno = secondStno;
		this.permissionNo = permissionNo;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getFirstStno() {
		return this.firstStno;
	}

	public void setFirstStno(String firstStno) {
		this.firstStno = firstStno;
	}

	public String getSecondStno() {
		return this.secondStno;
	}

	public void setSecondStno(String secondStno) {
		this.secondStno = secondStno;
	}

	public String getPermissionNo() {
		return this.permissionNo;
	}

	public void setPermissionNo(String permissionNo) {
		this.permissionNo = permissionNo;
	}

}