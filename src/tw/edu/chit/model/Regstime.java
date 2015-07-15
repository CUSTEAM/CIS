package tw.edu.chit.model;

/**
 * Regstime entity. @author MyEclipse Persistence Tools
 */

public class Regstime implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String departClass;
	private String cscode;
	private String ind;
	private String idno;
	private String ttime;
	private Integer dtimeOid;

	// Constructors

	/** default constructor */
	public Regstime() {
	}

	/** minimal constructor */
	public Regstime(Integer dtimeOid) {
		this.dtimeOid = dtimeOid;
	}

	/** full constructor */
	public Regstime(String departClass, String cscode, String ind, String idno,
			String ttime, Integer dtimeOid) {
		this.departClass = departClass;
		this.cscode = cscode;
		this.ind = ind;
		this.idno = idno;
		this.ttime = ttime;
		this.dtimeOid = dtimeOid;
	}

	// Property accessors

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

	public String getInd() {
		return this.ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getTtime() {
		return this.ttime;
	}

	public void setTtime(String ttime) {
		this.ttime = ttime;
	}

	public Integer getDtimeOid() {
		return this.dtimeOid;
	}

	public void setDtimeOid(Integer dtimeOid) {
		this.dtimeOid = dtimeOid;
	}

}