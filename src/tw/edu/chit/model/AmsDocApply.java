package tw.edu.chit.model;

import java.util.Date;

/**
 * AmsDocApply entity. @author MyEclipse Persistence Tools
 */

public class AmsDocApply extends AmsDocApplyBase implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private String docType;
	private String sn;
	private String reason;
	private String askLeaveType;
	private Date startDate;
	private Date endDate;
	private Short totalDay;
	private Short totalHour;
	private Short totalMinute;
	private Short teachPeriod;
	private String agent;
	private Date createDate;
	private Date processDate;
	private String status;
	private String memo;

	// Constructors

	/** default constructor */
	public AmsDocApply() {
	}

	/** minimal constructor */
	public AmsDocApply(String idno, String docType, String sn, Short totalDay,
			Short totalHour, Short totalMinute, Date createDate) {
		this.idno = idno;
		this.docType = docType;
		this.sn = sn;
		this.totalDay = totalDay;
		this.totalHour = totalHour;
		this.totalMinute = totalMinute;
		this.createDate = createDate;
	}

	/** full constructor */
	public AmsDocApply(String idno, String docType, String sn, String reason,
			String askLeaveType, Date startDate, Date endDate,
			Short totalDay, Short totalHour, Short totalMinute,
			Short teachPeriod, String agent, Date createDate,
			Date processDate, String status, String memo) {
		this.idno = idno;
		this.docType = docType;
		this.sn = sn;
		this.reason = reason;
		this.askLeaveType = askLeaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalDay = totalDay;
		this.totalHour = totalHour;
		this.totalMinute = totalMinute;
		this.teachPeriod = teachPeriod;
		this.agent = agent;
		this.createDate = createDate;
		this.processDate = processDate;
		this.status = status;
		this.memo = memo;
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

	public String getDocType() {
		return this.docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAskLeaveType() {
		return this.askLeaveType;
	}

	public void setAskLeaveType(String askLeaveType) {
		this.askLeaveType = askLeaveType;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Short getTotalDay() {
		return this.totalDay;
	}

	public void setTotalDay(Short totalDay) {
		this.totalDay = totalDay;
	}

	public Short getTotalHour() {
		return this.totalHour;
	}

	public void setTotalHour(Short totalHour) {
		this.totalHour = totalHour;
	}

	public Short getTotalMinute() {
		return this.totalMinute;
	}

	public void setTotalMinute(Short totalMinute) {
		this.totalMinute = totalMinute;
	}

	public Short getTeachPeriod() {
		return this.teachPeriod;
	}

	public void setTeachPeriod(Short teachPeriod) {
		this.teachPeriod = teachPeriod;
	}

	public String getAgent() {
		return this.agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getProcessDate() {
		return this.processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}