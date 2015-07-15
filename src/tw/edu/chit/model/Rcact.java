package tw.edu.chit.model;

import java.util.Date;

/**
 * Rcact entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Rcact implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private Short schoolYear;
	private String actname;
	private String sponoff;
	private String kindid;
	private String typeid;
	private String placeid;
	private String joinid;
	private String bdate;
	private String edate;
	private String hour;
	private String certno;
	private String schspon;
	private String certyn;
	private Date lastModified;
	private String intor;
	private String approve;
	private String approveTemp;

	// Constructors

	/** default constructor */
	public Rcact() {
	}

	/** minimal constructor */
	public Rcact(Date lastModified) {
		this.lastModified = lastModified;
	}

	/** full constructor */
	public Rcact(String idno, Short schoolYear, String actname, String sponoff,
			String kindid, String typeid, String placeid, String joinid,
			String bdate, String edate, String hour, String certno,
			String schspon, String certyn, Date lastModified, String intor,
			String approve, String approveTemp) {
		this.idno = idno;
		this.schoolYear = schoolYear;
		this.actname = actname;
		this.sponoff = sponoff;
		this.kindid = kindid;
		this.typeid = typeid;
		this.placeid = placeid;
		this.joinid = joinid;
		this.bdate = bdate;
		this.edate = edate;
		this.hour = hour;
		this.certno = certno;
		this.schspon = schspon;
		this.certyn = certyn;
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

	public String getActname() {
		return this.actname;
	}

	public void setActname(String actname) {
		this.actname = actname;
	}

	public String getSponoff() {
		return this.sponoff;
	}

	public void setSponoff(String sponoff) {
		this.sponoff = sponoff;
	}

	public String getKindid() {
		return this.kindid;
	}

	public void setKindid(String kindid) {
		this.kindid = kindid;
	}

	public String getTypeid() {
		return this.typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getPlaceid() {
		return this.placeid;
	}

	public void setPlaceid(String placeid) {
		this.placeid = placeid;
	}

	public String getJoinid() {
		return this.joinid;
	}

	public void setJoinid(String joinid) {
		this.joinid = joinid;
	}

	public String getBdate() {
		return this.bdate;
	}

	public void setBdate(String bdate) {
		this.bdate = bdate;
	}

	public String getEdate() {
		return this.edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getHour() {
		return this.hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getCertno() {
		return this.certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}

	public String getSchspon() {
		return this.schspon;
	}

	public void setSchspon(String schspon) {
		this.schspon = schspon;
	}

	public String getCertyn() {
		return this.certyn;
	}

	public void setCertyn(String certyn) {
		this.certyn = certyn;
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