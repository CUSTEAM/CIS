package tw.edu.chit.model;

import java.util.Date;

/**
 * StudDocDetail entity. @author MyEclipse Persistence Tools
 */

public class StudDocDetail implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer docOid;
	private Date adate;
	private Short period;

	// Constructors

	/** default constructor */
	public StudDocDetail() {
	}

	/** full constructor */
	public StudDocDetail(Integer docOid, Date adate, Short period) {
		this.docOid = docOid;
		this.adate = adate;
		this.period = period;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getDocOid() {
		return this.docOid;
	}

	public void setDocOid(Integer docOid) {
		this.docOid = docOid;
	}

	public Date getAdate() {
		return this.adate;
	}

	public void setAdate(Date adate) {
		this.adate = adate;
	}

	public Short getPeriod() {
		return this.period;
	}

	public void setPeriod(Short period) {
		this.period = period;
	}

}