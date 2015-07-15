package tw.edu.chit.model;

/**
 * CsGroupRule entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CsGroupRule implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String schoolNo;
	private Float major;
	private Float minor;
	private Float outdept;
	private Integer groupOid;
	private String entrance;

	// Constructors

	/** default constructor */
	public CsGroupRule() {
	}

	/** minimal constructor */
	public CsGroupRule(String schoolNo, Float major, Float minor,
			Float outdept, Integer groupOid) {
		this.schoolNo = schoolNo;
		this.major = major;
		this.minor = minor;
		this.outdept = outdept;
		this.groupOid = groupOid;
	}

	/** full constructor */
	public CsGroupRule(String schoolNo, Float major, Float minor,
			Float outdept, Integer groupOid, String entrance) {
		this.schoolNo = schoolNo;
		this.major = major;
		this.minor = minor;
		this.outdept = outdept;
		this.groupOid = groupOid;
		this.entrance = entrance;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getSchoolNo() {
		return this.schoolNo;
	}

	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
	}

	public Float getMajor() {
		return this.major;
	}

	public void setMajor(Float major) {
		this.major = major;
	}

	public Float getMinor() {
		return this.minor;
	}

	public void setMinor(Float minor) {
		this.minor = minor;
	}

	public Float getOutdept() {
		return this.outdept;
	}

	public void setOutdept(Float outdept) {
		this.outdept = outdept;
	}

	public Integer getGroupOid() {
		return this.groupOid;
	}

	public void setGroupOid(Integer groupOid) {
		this.groupOid = groupOid;
	}

	public String getEntrance() {
		return this.entrance;
	}

	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}

}