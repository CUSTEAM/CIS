package tw.edu.chit.model;

import java.util.Date;

/**
 * StudPublicLeave entity. @author MyEclipse Persistence Tools
 */

public class StudPublicLeave extends StudPublicLeaveBase implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String department;	//store ClassNo
	private String applyId;
	private String reason;
	private Date startDate;
	private Date endDate;
	private String ruleNo;
	private String status;
	private String memo;
	private Date createDate;
	private Short startPeriod;
	private Short endPeriod;

	// Constructors

	/** default constructor */
	public StudPublicLeave() {
	}

	/** minimal constructor */
	public StudPublicLeave(String department, String applyId, String reason,
			Date startDate, Date endDate, String ruleNo,
			String status, Date createDate, Short startPeriod,
			Short endPeriod) {
		this.department = department;
		this.applyId = applyId;
		this.reason = reason;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ruleNo = ruleNo;
		this.status = status;
		this.createDate = createDate;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
	}

	/** full constructor */
	public StudPublicLeave(String department, String applyId, String reason,
			Date startDate, Date endDate, String ruleNo,
			String status, String memo, Date createDate,
			Short startPeriod, Short endPeriod) {
		this.department = department;
		this.applyId = applyId;
		this.reason = reason;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ruleNo = ruleNo;
		this.status = status;
		this.memo = memo;
		this.createDate = createDate;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getApplyId() {
		return this.applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getRuleNo() {
		return this.ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
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

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Short getStartPeriod() {
		return this.startPeriod;
	}

	public void setStartPeriod(Short startPeriod) {
		this.startPeriod = startPeriod;
	}

	public Short getEndPeriod() {
		return this.endPeriod;
	}

	public void setEndPeriod(Short endPeriod) {
		this.endPeriod = endPeriod;
	}

}