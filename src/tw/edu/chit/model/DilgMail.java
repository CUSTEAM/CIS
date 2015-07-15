package tw.edu.chit.model;

import java.util.Date;

/**
 * DilgMail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DilgMail implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String studentNo;
	private Integer period;
	private Integer threshold;
	private Date reportDate;
	private Integer raiseflag;
	private Integer prevPeriod;
	private Integer prevRaiseflag;
	private Integer tutorContact;
	private Integer prevTutor;

	// Constructors

	/** default constructor */
	public DilgMail() {
	}

	/** minimal constructor */
	public DilgMail(String studentNo, Date reportDate) {
		this.studentNo = studentNo;
		this.reportDate = reportDate;
	}

	/** full constructor */
	public DilgMail(String studentNo, Integer period, Integer threshold,
			Date reportDate, Integer raiseflag, Integer prevPeriod,
			Integer prevRaiseflag, Integer tutorContact, Integer prevTutor) {
		this.studentNo = studentNo;
		this.period = period;
		this.threshold = threshold;
		this.reportDate = reportDate;
		this.raiseflag = raiseflag;
		this.prevPeriod = prevPeriod;
		this.prevRaiseflag = prevRaiseflag;
		this.tutorContact = tutorContact;
		this.prevTutor = prevTutor;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Integer getPeriod() {
		return this.period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getThreshold() {
		return this.threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public Date getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Integer getRaiseflag() {
		return this.raiseflag;
	}

	public void setRaiseflag(Integer raiseflag) {
		this.raiseflag = raiseflag;
	}

	public Integer getPrevPeriod() {
		return this.prevPeriod;
	}

	public void setPrevPeriod(Integer prevPeriod) {
		this.prevPeriod = prevPeriod;
	}

	public Integer getPrevRaiseflag() {
		return this.prevRaiseflag;
	}

	public void setPrevRaiseflag(Integer prevRaiseflag) {
		this.prevRaiseflag = prevRaiseflag;
	}

	public Integer getTutorContact() {
		return this.tutorContact;
	}

	public void setTutorContact(Integer tutorContact) {
		this.tutorContact = tutorContact;
	}

	public Integer getPrevTutor() {
		return this.prevTutor;
	}

	public void setPrevTutor(Integer prevTutor) {
		this.prevTutor = prevTutor;
	}

}