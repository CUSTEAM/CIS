package tw.edu.chit.model;

import java.util.Date;

/**
 * Rcbook entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Rcbook implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private Short schoolYear;
	private String projno;
	private String title;
	private String authorno;
	private String language;
	private String pdate;
	private String publisher;
	private String isbn;
	private Date lastModified;
	private String intor;
	private String comAuthorno;
	private String type;
	private String approve;
	private String approveTemp;

	// Constructors

	/** default constructor */
	public Rcbook() {
	}

	/** minimal constructor */
	public Rcbook(String idno, Date lastModified) {
		this.idno = idno;
		this.lastModified = lastModified;
	}

	/** full constructor */
	public Rcbook(String idno, Short schoolYear, String projno, String title,
			String authorno, String language, String pdate, String publisher,
			String isbn, Date lastModified, String intor, String comAuthorno,
			String type, String approve, String approveTemp) {
		this.idno = idno;
		this.schoolYear = schoolYear;
		this.projno = projno;
		this.title = title;
		this.authorno = authorno;
		this.language = language;
		this.pdate = pdate;
		this.publisher = publisher;
		this.isbn = isbn;
		this.lastModified = lastModified;
		this.intor = intor;
		this.comAuthorno = comAuthorno;
		this.type = type;
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

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPdate() {
		return this.pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
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