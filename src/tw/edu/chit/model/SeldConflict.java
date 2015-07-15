package tw.edu.chit.model;

import java.util.Date;

public class SeldConflict implements java.io.Serializable {

	private static final long serialVersionUID = -3749699302010012009L;

	private Integer oid;
	private Integer emplOid;
	private Integer dtimeOid;
	private String studentNo;
	private String term;
	private Date lastModified;

	/** default constructor */
	public SeldConflict() {
	}

	public SeldConflict(Integer oid, Integer emplOid, Integer dtimeOid,
			String studentNo, String term, Date lastModified) {
		super();
		this.oid = oid;
		this.emplOid = emplOid;
		this.dtimeOid = dtimeOid;
		this.studentNo = studentNo;
		this.term = term;
		this.lastModified = lastModified;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getEmplOid() {
		return this.emplOid;
	}

	public void setEmplOid(Integer emplOid) {
		this.emplOid = emplOid;
	}

	public Integer getDtimeOid() {
		return dtimeOid;
	}

	public void setDtimeOid(Integer dtimeOid) {
		this.dtimeOid = dtimeOid;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}