package tw.edu.chit.model;

import java.util.Date;

/**
 * Rcconf entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Rcconf implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private Short schoolYear;
	private String projno;
	private String title;
	private String authorno;
	private String jname;
	private String nation;
	private String city;
	private String bdate;
	private String edate;
	private Short pyear;
	private Date lastModified;
	private String intor;
	private String comAuthorno;
	private String approve;
	private String approveTemp;

	// Constructors

	/** default constructor */
	public Rcconf() {
	}

	/** minimal constructor */
	public Rcconf(String idno, Date lastModified) {
		this.idno = idno;
		this.lastModified = lastModified;
	}

	/** full constructor */
	public Rcconf(String idno, Short schoolYear, String projno, String title,
			String authorno, String jname, String nation, String city,
			String bdate, String edate, Short pyear, Date lastModified,
			String intor, String comAuthorno, String approve, String approveTemp) {
		this.idno = idno;
		this.schoolYear = schoolYear;
		this.projno = projno;
		this.title = title;
		this.authorno = authorno;
		this.jname = jname;
		this.nation = nation;
		this.city = city;
		this.bdate = bdate;
		this.edate = edate;
		this.pyear = pyear;
		this.lastModified = lastModified;
		this.intor = intor;
		this.comAuthorno = comAuthorno;
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

	public String getJname() {
		return this.jname;
	}

	public void setJname(String jname) {
		this.jname = jname;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public Short getPyear() {
		return this.pyear;
	}

	public void setPyear(Short pyear) {
		this.pyear = pyear;
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

	public String getComAuthorno() {
		return this.comAuthorno;
	}

	public void setComAuthorno(String comAuthorno) {
		this.comAuthorno = comAuthorno;
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