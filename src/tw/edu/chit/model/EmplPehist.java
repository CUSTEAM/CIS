package tw.edu.chit.model;

import java.util.Date;

/**
 * EmplPehist entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EmplPehist implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Short schoolYear;
	private Short schoolTerm;
	private String idno;
	private String inst;
	private String bdateCertname;
	private String edateCertno;
	private Date begin;
	private Date end;
	private String deptNo;

	// Constructors

	/** default constructor */
	public EmplPehist() {
	}

	/** full constructor */
	public EmplPehist(Short schoolYear, Short schoolTerm, String idno,
			String inst, String bdateCertname, String edateCertno, Date begin,
			Date end, String deptNo) {
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.idno = idno;
		this.inst = inst;
		this.bdateCertname = bdateCertname;
		this.edateCertno = edateCertno;
		this.begin = begin;
		this.end = end;
		this.deptNo = deptNo;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Short getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(Short schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Short getSchoolTerm() {
		return this.schoolTerm;
	}

	public void setSchoolTerm(Short schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getInst() {
		return this.inst;
	}

	public void setInst(String inst) {
		this.inst = inst;
	}

	public String getBdateCertname() {
		return this.bdateCertname;
	}

	public void setBdateCertname(String bdateCertname) {
		this.bdateCertname = bdateCertname;
	}

	public String getEdateCertno() {
		return this.edateCertno;
	}

	public void setEdateCertno(String edateCertno) {
		this.edateCertno = edateCertno;
	}

	public Date getBegin() {
		return this.begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return this.end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

}