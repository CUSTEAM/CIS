package tw.edu.chit.model;

import java.util.Date;

/**
 * StudDocApply entity. @author MyEclipse Persistence Tools
 */

public class StudDocApply extends StudDocApplyBase implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String studentNo;
	private String departClass;
	private String studentName;
	private String askLeaveType;
	private String reason;
	private Date startDate;
	private Date endDate;
	private String ruleNo;
	private Integer publicDocOid;
	private String status;
	private Short totalDay;
	private Date createDate;
	private String memo;
	
	// Constructors

	/** default constructor */
	public StudDocApply() {
	}

	/** minimal constructor */
	public StudDocApply(String studentNo, String departClass,
			String studentName, String askLeaveType, String reason,
			Date startDate, Date endDate, String ruleNo,
			String status, Short totalDay, Date createDate) {
		this.studentNo = studentNo;
		this.departClass = departClass;
		this.studentName = studentName;
		this.askLeaveType = askLeaveType;
		this.reason = reason;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ruleNo = ruleNo;
		this.status = status;
		this.totalDay = totalDay;
		this.createDate = createDate;
	}

	/** full constructor */
	public StudDocApply(String studentNo, String departClass,
			String studentName, String askLeaveType, String reason,
			Date startDate, Date endDate, String ruleNo,
			Integer publicDocOid, String status, Short totalDay,
			Date createDate, String memo) {
		this.studentNo = studentNo;
		this.departClass = departClass;
		this.studentName = studentName;
		this.askLeaveType = askLeaveType;
		this.reason = reason;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ruleNo = ruleNo;
		this.publicDocOid = publicDocOid;
		this.status = status;
		this.totalDay = totalDay;
		this.createDate = createDate;
		this.memo = memo;
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

	public String getDepartClass() {
		return this.departClass;
	}

	public void setDepartClass(String departClass) {
		this.departClass = departClass;
	}

	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getAskLeaveType() {
		return this.askLeaveType;
	}

	public void setAskLeaveType(String askLeaveType) {
		this.askLeaveType = askLeaveType;
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

	public Integer getPublicDocOid() {
		return this.publicDocOid;
	}

	public void setPublicDocOid(Integer publicDocOid) {
		this.publicDocOid = publicDocOid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Short getTotalDay() {
		return this.totalDay;
	}

	public void setTotalDay(Short totalDay) {
		this.totalDay = totalDay;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}


}