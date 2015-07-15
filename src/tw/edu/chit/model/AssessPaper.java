package tw.edu.chit.model;

import java.util.Date;

/**
 * AssessPaper entity. @author MyEclipse Persistence Tools
 */

public class AssessPaper extends AssessPaperBase implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private String serviceNo;
	private Date printDate;
	private Date serviceDate;
	private Short score;
	private String suggestion;
	private String reporter;
	private String srvTime;
	private String srvEvent;
	private String description;

	// Constructors

	/** default constructor */
	public AssessPaper() {
	}

	/** minimal constructor */
	public AssessPaper(String idno, String serviceNo, Date printDate) {
		this.idno = idno;
		this.serviceNo = serviceNo;
		this.printDate = printDate;
	}

	/** full constructor */
	public AssessPaper(String idno, String serviceNo, Date printDate,
			Date serviceDate, Short score, String suggestion,
			String reporter, String srvTime, String srvEvent, String description) {
		this.idno = idno;
		this.serviceNo = serviceNo;
		this.printDate = printDate;
		this.serviceDate = serviceDate;
		this.score = score;
		this.suggestion = suggestion;
		this.reporter = reporter;
		this.srvTime = srvTime;
		this.srvEvent = srvEvent;
		this.description = description;
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

	public String getServiceNo() {
		return this.serviceNo;
	}

	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}

	public Date getPrintDate() {
		return this.printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	public Date getServiceDate() {
		return this.serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public Short getScore() {
		return this.score;
	}

	public void setScore(Short score) {
		this.score = score;
	}

	public String getSuggestion() {
		return this.suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getReporter() {
		return this.reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getSrvTime() {
		return this.srvTime;
	}

	public void setSrvTime(String srvTime) {
		this.srvTime = srvTime;
	}

	public String getSrvEvent() {
		return this.srvEvent;
	}

	public void setSrvEvent(String srvEvent) {
		this.srvEvent = srvEvent;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}