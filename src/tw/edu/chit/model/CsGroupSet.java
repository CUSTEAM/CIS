package tw.edu.chit.model;

/**
 * CsGroupSet entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CsGroupSet implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer groupOid;
	private String cscode;
	private String deptNo;
	private String opt;
	private Float credit;
	private String entrance;

	// Constructors

	/** default constructor */
	public CsGroupSet() {
	}

	/** minimal constructor */
	public CsGroupSet(Integer groupOid, String cscode, String opt) {
		this.groupOid = groupOid;
		this.cscode = cscode;
		this.opt = opt;
	}

	/** full constructor */
	public CsGroupSet(Integer groupOid, String cscode, String deptNo,
			String opt, Float credit, String entrance) {
		this.groupOid = groupOid;
		this.cscode = cscode;
		this.deptNo = deptNo;
		this.opt = opt;
		this.credit = credit;
		this.entrance = entrance;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getGroupOid() {
		return this.groupOid;
	}

	public void setGroupOid(Integer groupOid) {
		this.groupOid = groupOid;
	}

	public String getCscode() {
		return this.cscode;
	}

	public void setCscode(String cscode) {
		this.cscode = cscode;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getOpt() {
		return this.opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public Float getCredit() {
		return this.credit;
	}

	public void setCredit(Float credit) {
		this.credit = credit;
	}

	public String getEntrance() {
		return this.entrance;
	}

	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}

}