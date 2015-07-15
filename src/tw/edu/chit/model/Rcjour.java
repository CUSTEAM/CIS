package tw.edu.chit.model;

import java.util.Date;

/**
 * Rcjour entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Rcjour implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private Short schoolYear;
	private String projno;
	private String title;
	private String authorno;
	private String kindid;
	private String jname;
	private String volume;
	private String period;
	private Short pmonth;
	private Short pyear;
	private Date lastModified;
	private String intor;
	private String comAuthorno;
	private String type;
	private String place;
	private String approve;
	private String approveTemp;

	// Constructors

	/** default constructor */
	public Rcjour() {
	}

	/** minimal constructor */
	public Rcjour(String idno, Date lastModified) {
		this.idno = idno;
		this.lastModified = lastModified;
	}

	/** full constructor */
	public Rcjour(String idno, Short schoolYear, String projno, String title,
			String authorno, String kindid, String jname, String volume,
			String period, Short pmonth, Short pyear, Date lastModified,
			String intor, String comAuthorno, String type, String place,
			String approve, String approveTemp) {
		this.idno = idno;
		this.schoolYear = schoolYear;
		this.projno = projno;
		this.title = title;
		this.authorno = authorno;
		this.kindid = kindid;
		this.jname = jname;
		this.volume = volume;
		this.period = period;
		this.pmonth = pmonth;
		this.pyear = pyear;
		this.lastModified = lastModified;
		this.intor = intor;
		this.comAuthorno = comAuthorno;
		this.type = type;
		this.place = place;
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

	public String getKindid() {
		return this.kindid;
	}

	public void setKindid(String kindid) {
		this.kindid = kindid;
	}

	public String getJname() {
		return this.jname;
	}

	public void setJname(String jname) {
		this.jname = jname;
	}

	public String getVolume() {
		return this.volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getPeriod() {
		return this.period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Short getPmonth() {
		return this.pmonth;
	}

	public void setPmonth(Short pmonth) {
		this.pmonth = pmonth;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
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