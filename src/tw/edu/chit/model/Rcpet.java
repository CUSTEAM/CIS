package tw.edu.chit.model;

import java.util.Date;

/**
 * Rcpet entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Rcpet implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private Short schoolYear;
	private String projno;
	private String title;
	private String authorno;
	private String area;
	private String petType;
	private String schedule;
	private String bdate;
	private String edate;
	private String inst;
	private String certno;
	private String depute;
	private Date lastModified;
	private String intor;
	private String proposer;
	private String score;
	private String proposerType;
	private String deputeMoney;
	private String approve;
	private String approveTemp;
	private String deputeSdate;
	private String deputeEdate;
	private String deputeBusiness;

	// Constructors

	/** default constructor */
	public Rcpet() {
	}

	/** minimal constructor */
	public Rcpet(Date lastModified) {
		this.lastModified = lastModified;
	}

	/** full constructor */
	public Rcpet(String idno, Short schoolYear, String projno, String title,
			String authorno, String area, String petType, String schedule,
			String bdate, String edate, String inst, String certno,
			String depute, Date lastModified, String intor, String proposer,
			String score, String proposerType, String deputeMoney,
			String approve, String approveTemp, String deputeSdate,
			String deputeEdate, String deputeBusiness) {
		this.idno = idno;
		this.schoolYear = schoolYear;
		this.projno = projno;
		this.title = title;
		this.authorno = authorno;
		this.area = area;
		this.petType = petType;
		this.schedule = schedule;
		this.bdate = bdate;
		this.edate = edate;
		this.inst = inst;
		this.certno = certno;
		this.depute = depute;
		this.lastModified = lastModified;
		this.intor = intor;
		this.proposer = proposer;
		this.score = score;
		this.proposerType = proposerType;
		this.deputeMoney = deputeMoney;
		this.approve = approve;
		this.approveTemp = approveTemp;
		this.deputeSdate = deputeSdate;
		this.deputeEdate = deputeEdate;
		this.deputeBusiness = deputeBusiness;
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

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPetType() {
		return this.petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public String getSchedule() {
		return this.schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
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

	public String getInst() {
		return this.inst;
	}

	public void setInst(String inst) {
		this.inst = inst;
	}

	public String getCertno() {
		return this.certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}

	public String getDepute() {
		return this.depute;
	}

	public void setDepute(String depute) {
		this.depute = depute;
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

	public String getProposer() {
		return this.proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getProposerType() {
		return this.proposerType;
	}

	public void setProposerType(String proposerType) {
		this.proposerType = proposerType;
	}

	public String getDeputeMoney() {
		return this.deputeMoney;
	}

	public void setDeputeMoney(String deputeMoney) {
		this.deputeMoney = deputeMoney;
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

	public String getDeputeSdate() {
		return this.deputeSdate;
	}

	public void setDeputeSdate(String deputeSdate) {
		this.deputeSdate = deputeSdate;
	}

	public String getDeputeEdate() {
		return this.deputeEdate;
	}

	public void setDeputeEdate(String deputeEdate) {
		this.deputeEdate = deputeEdate;
	}

	public String getDeputeBusiness() {
		return this.deputeBusiness;
	}

	public void setDeputeBusiness(String deputeBusiness) {
		this.deputeBusiness = deputeBusiness;
	}

}