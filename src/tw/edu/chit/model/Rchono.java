package tw.edu.chit.model;

import java.util.Date;

/**
 * Rchono entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Rchono implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private Short schoolYear;
	private String projno;
	private String title;
	private String authorno;
	private String nation;
	private String inst;
	private String bdate;
	private Date lastModified;
	private String intor;
	private String approve;
	private String approveTemp;

	// Constructors

	/** default constructor */
	public Rchono() {
	}

	/** minimal constructor */
	public Rchono(String idno, Date lastModified) {
		this.idno = idno;
		this.lastModified = lastModified;
	}

	/** full constructor */
	public Rchono(String idno, Short schoolYear, String projno, String title,
			String authorno, String nation, String inst, String bdate,
			Date lastModified, String intor, String approve, String approveTemp) {
		this.idno = idno;
		this.schoolYear = schoolYear;
		this.projno = projno;
		this.title = title;
		this.authorno = authorno;
		this.nation = nation;
		this.inst = inst;
		this.bdate = bdate;
		this.lastModified = lastModified;
		this.intor = intor;
		this.approve = approve;
		this.approveTemp = approveTemp;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public Short getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(Short schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getProjno() {
		return this.projno;
	}

	public void setProjno(String projno) {
		this.projno = projno;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorno() {
		return this.authorno;
	}

	public void setAuthorno(String authorno) {
		this.authorno = authorno;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getInst() {
		return this.inst;
	}

	public void setInst(String inst) {
		this.inst = inst;
	}

	public String getBdate() {
		return this.bdate;
	}

	public void setBdate(String bdate) {
		this.bdate = bdate;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getIntor() {
		return this.intor;
	}

	public void setIntor(String intor) {
		this.intor = intor;
	}

	public String getApprove() {
		return this.approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public String getApproveTemp() {
		return this.approveTemp;
	}

	public void setApproveTemp(String approveTemp) {
		this.approveTemp = approveTemp;
	}

}