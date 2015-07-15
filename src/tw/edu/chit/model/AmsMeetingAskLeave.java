package tw.edu.chit.model;

import java.util.Date;

/**
 * AmsMeetingAskLeave entity. @author MyEclipse Persistence Tools
 */

public class AmsMeetingAskLeave extends AmsMeetingAskLeaveBase implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer meetingOid;
	private String userIdno;
	private String askleaveId;
	private String temp;
	private String status;
	private String askCode;
	private Date processDate;

	// Constructors

	/** default constructor */
	public AmsMeetingAskLeave() {
	}

	/** minimal constructor */
	public AmsMeetingAskLeave(Integer meetingOid, String userIdno,
			String askleaveId, String askCode) {
		this.meetingOid = meetingOid;
		this.userIdno = userIdno;
		this.askleaveId = askleaveId;
		this.askCode = askCode;
	}

	/** full constructor */
	public AmsMeetingAskLeave(Integer meetingOid, String userIdno,
			String askleaveId, String temp, String status, String askCode,
			Date processDate) {
		this.meetingOid = meetingOid;
		this.userIdno = userIdno;
		this.askleaveId = askleaveId;
		this.temp = temp;
		this.status = status;
		this.askCode = askCode;
		this.processDate = processDate;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getMeetingOid() {
		return this.meetingOid;
	}

	public void setMeetingOid(Integer meetingOid) {
		this.meetingOid = meetingOid;
	}

	public String getUserIdno() {
		return this.userIdno;
	}

	public void setUserIdno(String userIdno) {
		this.userIdno = userIdno;
	}

	public String getAskleaveId() {
		return this.askleaveId;
	}

	public void setAskleaveId(String askleaveId) {
		this.askleaveId = askleaveId;
	}

	public String getTemp() {
		return this.temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAskCode() {
		return this.askCode;
	}

	public void setAskCode(String askCode) {
		this.askCode = askCode;
	}

	public Date getProcessDate() {
		return this.processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

}