package tw.edu.chit.model;

/**
 * CsGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CsGroup implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String cname;
	private String ename;
	private String note;
	private String entrance;

	// Constructors

	/** default constructor */
	public CsGroup() {
	}

	/** minimal constructor */
	public CsGroup(String cname) {
		this.cname = cname;
	}

	/** full constructor */
	public CsGroup(String cname, String ename, String note, String entrance) {
		this.cname = cname;
		this.ename = ename;
		this.note = note;
		this.entrance = entrance;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getEntrance() {
		return this.entrance;
	}

	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}

}