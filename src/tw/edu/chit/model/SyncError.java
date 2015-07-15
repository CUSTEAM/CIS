package tw.edu.chit.model;

import java.util.Date;

/**
 * SyncError entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SyncError implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String errorMsg;
	private Date realTime;

	// Constructors

	/** default constructor */
	public SyncError() {
	}

	/** full constructor */
	public SyncError(String errorMsg, Date realTime) {
		this.errorMsg = errorMsg;
		this.realTime = realTime;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Date getRealTime() {
		return this.realTime;
	}

	public void setRealTime(Date realTime) {
		this.realTime = realTime;
	}

}